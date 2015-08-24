/*
 * @(#)ParticularWorkflowJdbcDao.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：陈喜平
 *  时　间：2009-6-10
 *  描　述：创建
 */

package com.infolion.sapss.workflow.particular.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.infolion.platform.core.dao.BaseDao;
@Repository
public class ParticularWorkflowJdbcDao extends BaseDao{
	/**
	 * 根据流转ID查找原业务ID
	 * @param particularId
	 * @return
	 */
	public String find(String particularId){
		String sql="select * from t_particular_workflow where particular_id=?";
		List list = this.getJdbcTemplate().queryForList(sql,new Object[]{particularId});
		if(list!=null && list.size()>0){
			Map map = (Map)list.get(0);
			return (String)map.get("original_biz_id");
		}
		return "";
	}
	/**
	 * 根据流转ID查找不同意提交审批的次数
	 * @param bizId
	 */
	public int getNoPassTimes(String bizId) {
		String sql="select count(*) from (SELECT * fROM t_sys_wf_task_history union SELECT * FROM t_sys_wf_task_history_O) t where t.business_record_id=? and t.examine_result like '%不同意%'";
		return this.getJdbcTemplate().queryForInt(sql, new Object[]{bizId});
	}
	/**
	 * 根据流转ID查找最近不同意提交的审批节点名称
	 * @param bizId
	 * @return
	 */
	public String getNearestNoPassNodeName(String bizId) {
		String sql="select t.task_name from (SELECT * fROM t_sys_wf_task_history union SELECT * FROM t_sys_wf_task_history_O) t where t.business_record_id=? and t.examine_result like '%不同意%' order by t.TASK_END_TIME desc";
		List list = this.getJdbcTemplate().queryForList(sql,new Object[]{bizId});
		if(list!=null && list.size()>0){
			Map map = (Map)list.get(0);
			return (String)map.get("task_name");
		}
		return "";
	}
	/**
	 * 根据原业务ID取得流转ID
	 * @param originalId
	 * @return
	 */
	public String getParticularIdByOriginalId(String originalId) {
		String sql="select * from t_particular_workflow where original_biz_id=?";
		List list = this.getJdbcTemplate().queryForList(sql,new Object[]{originalId});
		if(list!=null && list.size()>0){
			Map map = (Map)list.get(0);
			return (String)map.get("particular_id");
		}
		return "";
	}
}
