/*
 * @(#)ChangeProjectJdbcDao.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：陈喜平
 *  时　间：2009-3-6
 *  描　述：创建
 */

package com.infolion.sapss.project.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.infolion.platform.core.dao.BaseDao;
import com.infolion.platform.util.DateUtils;
import com.infolion.sapss.project.domain.TProjectChange;
@Repository
public class ChangeProjectJdbcDao extends BaseDao{
	/**
	 * 删除变更
	 * @param changeId
	 */
	public int deleteChange(String changeId) {
		String sql = "update T_PROJECT_CHANGE set is_available='0' where change_id=?";
		return this.getJdbcTemplate().update(sql, new Object[]{changeId});
	}
	/**
	 * 更新变更信息
	 * @param tc
	 * @return
	 */
	public int updateChange(TProjectChange tc) {
		String sql = "update T_PROJECT_CHANGE set change_desc=?,currency=?,amount=? where change_id=?";
		return this.getJdbcTemplate().update(sql,new Object[]{tc.getChangeDesc(),tc.getCurrency(),tc.getAmount(),tc.getChangeId()});
	}
	/**
	 * 提交审批时更新记录状态
	 * @param changeId
	 */
	public int submitUpdateState(String changeId,String state) {
		String sql = "update T_PROJECT_CHANGE set change_state=? where change_id=?";
		return this.getJdbcTemplate().update(sql,new Object[]{state,changeId});
		
	}
	/**
	 * 保存信息中心执行信息
	 * @param tpc
	 * @return
	 */
	public int saveChanged(TProjectChange tpc) {
		String sql ="update T_PROJECT_CHANGE set change_operator=?,operate_time=?,is_changed=?,change_note=? where change_id=?";
		return this.getJdbcTemplate().update(sql, new Object[]{tpc.getChangeOperator(),
				tpc.getOperateTime(),tpc.getIsChanged(),tpc.getChangeNote(),tpc.getChangeId()});
	}
	/**
	 * 返回批次号
	 * @param projectId
	 * @return
	 */
	public int getChangeNo(String projectId,String changeNo) {
		String sql = "select max(change_no) from T_PROJECT_CHANGE where project_id=? and change_state=?";
		return this.getJdbcTemplate().queryForInt(sql, new Object[]{projectId,changeNo});
	}
	/**
	 * 更新业务记录状态
	 * @param businessRecordId
	 * @param examineResult
	 */
	public int workflowUpdateState(String businessRecordId,String examineResult) {
		String sql = "update T_PROJECT_CHANGE set change_state=? , approved_time=? where change_id=?";
		String result="";
		if("pass".equals(examineResult))
			result="4";
		else
			result="5";
		return this.getJdbcTemplate().update(sql, new Object[]{result,DateUtils.getCurrTime(DateUtils.DB_STORE_DATE),businessRecordId});
		
	}
	/**
	 * 取得立项id
	 * @param businessRecordId
	 */
	public String getProjectId(String businessRecordId) {
		String sql ="select project_id from T_PROJECT_CHANGE where change_id=?";
		List list = this.getJdbcTemplate().queryForList(sql,new Object[]{businessRecordId});
		if(list!=null && list.size()>0){
			return (String)((Map)list.get(0)).get("project_id");
		}else
			return "";
		
	}
	
	public void copyFile(String projectId,String businessRecordId){
		String sql = "update   t_attachement t set t.attachement_business_id = (select f.attachement_business_id from t_attachement_business f " +
				     "where f.record_id=?) where t.attachement_business_id =(select f.attachement_business_id from t_attachement_business f " +
				     "where f.record_id=?)";
		getJdbcTemplate().update(sql, new Object[]{projectId,businessRecordId});
		
		getJdbcTemplate().update("delete from t_attachement_business f where f.record_id='"+businessRecordId+"'");
	}
}
