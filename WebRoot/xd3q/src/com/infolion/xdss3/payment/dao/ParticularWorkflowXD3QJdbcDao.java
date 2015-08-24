package com.infolion.xdss3.payment.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.infolion.platform.dpframework.core.dao.BaseJdbcDao;
@Repository
public class ParticularWorkflowXD3QJdbcDao extends BaseJdbcDao{
	/**
	 * 根据流转ID查找最近不同意提交的审批节点名称
	 * @param bizId
	 * @return
	 */
	public String getNearestNoPassNodeName(String bizId) {
		String sql="select t.taskname from (select * from WF_TASKINS union select * From WF_TASKINS_O ) t where t.businessid=? and t.examineresult like '%不同意%' order by t.taskendtime desc";
		List list = this.getJdbcTemplate().queryForList(sql,new Object[]{bizId});
		if(list!=null && list.size()>0){
			Map map = (Map)list.get(0);
			return (String)map.get("taskname");
		}
		return "";
	}

}
