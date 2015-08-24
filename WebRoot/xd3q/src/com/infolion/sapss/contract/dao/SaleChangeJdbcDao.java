/*
 * @(#)ChangeProjectJdbcDao.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：陈喜平
 *  时　间：2009-3-6
 *  描　述：创建
 */

package com.infolion.sapss.contract.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.infolion.platform.core.dao.BaseDao;
import com.infolion.platform.util.DateUtils;
import com.infolion.sapss.contract.domain.TSalesChange;
import com.infolion.sapss.project.domain.TProjectChange;
import com.infolion.sapss.workflow.ProcessCallBack;
@Repository
public class SaleChangeJdbcDao extends BaseDao{
	/**
	 * 删除变更
	 * @param changeId
	 */
	public int deleteChange(String changeId) {
		String sql = "update T_SALES_CHANGE set is_available='0' where change_id=?";
		return this.getJdbcTemplate().update(sql, new Object[]{changeId});
	}
	/**
	 * 更新变更信息
	 * @param tc
	 * @return
	 */
	public int updateChange(TSalesChange tc) {
		String sql = "update T_SALES_CHANGE set change_desc=?,currency=?,amount=? where change_id=?";
		return this.getJdbcTemplate().update(sql,new Object[]{tc.getChangeDesc(),tc.getCurrency(),tc.getAmount(),tc.getChangeId()});
	}
	/**
	 * 提交审批时更新记录状态
	 * @param changeId
	 */
	public int submitUpdateState(String changeId,String state) {
		String sql = "update T_SALES_CHANGE set change_state=? where change_id=?";
		return this.getJdbcTemplate().update(sql,new Object[]{state,changeId});
		
	}
	/**
	 * 保存信息中心执行信息
	 * @param tpc
	 * @return
	 */
	public int saveChanged(TSalesChange tpc) {
		String sql ="update T_SALES_CHANGE set change_operator=?,operate_time=?,is_changed=?,change_note=? where change_id=?";
		return this.getJdbcTemplate().update(sql, new Object[]{tpc.getChangeOperator(),
				tpc.getOperateTime(),tpc.getIsChanged(),tpc.getChangeNote(),tpc.getChangeId()});
	}
	/**
	 * 返回批次号
	 * @param projectId
	 * @return
	 */
	public int getChangeNo(String bizId,String changeNo) {
		String sql = "select change_no from T_SALES_CHANGE where contract_sales_id=? and change_state=?";
		List list = this.getJdbcTemplate().queryForList(sql, new Object[]{bizId,changeNo});
		if(list!=null ){
			return list.size();
		}	
		return 0;
	}
	/**
	 * 更新业务记录状态
	 * @param businessRecordId
	 * @param examineResult
	 */
	public int workflowChangeUpdateState(String businessRecordId,String examineResult) {
		String sql = "update T_SALES_CHANGE set change_state=? , approved_time=? where change_id=?";
		String result="";
		if(!ProcessCallBack.EXAMINE_FAILE.equals(examineResult))
			result="4";
		else
			result="5";
		return this.getJdbcTemplate().update(sql, new Object[]{result,DateUtils.getCurrTime(DateUtils.DB_STORE_DATE),businessRecordId});
		
	}
	/**
	 * 变更状态更新
	 * @param businessRecordId
	 * @param examineResult
	 */
	public int workflowUpdateState(String businessRecordId,String examineResult) {//, approved_time=? 取消更新原合同审批通过时间
		String sql = "update t_contract_sales_info set order_state=?  where contract_sales_id=?";
		String result="";
		if(ProcessCallBack.EXAMINE_SUCCESSFUL.equals(examineResult))
			result="7";//变更通过
		else if(ProcessCallBack.NO_EXAMINE.equals(examineResult))
			result="9";//未经审批变更通过
		else
			result="3";//审批通过,变更不通过,维持原状态
		return this.getJdbcTemplate().update(sql, new Object[]{result/**,DateUtils.getCurrTime(DateUtils.DB_STORE_DATE)*/,businessRecordId});
	}

	/**
	 * 取得合同id
	 * @param businessRecordId
	 */
	public String getContractId(String businessRecordId) {
		String sql ="select contract_sales_id from T_SALES_CHANGE where change_id=?";
		List list = this.getJdbcTemplate().queryForList(sql,new Object[]{businessRecordId});
		if(list!=null && list.size()>0){
			return (String)((Map)list.get(0)).get("contract_sales_id");
		}else
			return "";
		
	}
}
