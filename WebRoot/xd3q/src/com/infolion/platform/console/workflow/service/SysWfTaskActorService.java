/*
 * @(#)SysWfTaskActorService.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：傅渊源
 *  时　间：2008-12-24
 *  描　述：创建
 */

package com.infolion.platform.console.workflow.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infolion.platform.component.workflow.ext.CommonProcessInstance;
import com.infolion.platform.console.workflow.dao.SysWfTaskActorJdbcDao;
import com.infolion.platform.console.workflow.domain.ComboBoxFormat;
import com.infolion.platform.console.workflow.domain.JbpmTask;
import com.infolion.platform.console.workflow.domain.SysWfTaskActor;
import com.infolion.platform.core.service.BaseJdbcService;
@Service
public class SysWfTaskActorService extends BaseJdbcService {
	@Autowired
	private SysWfTaskActorJdbcDao sysWfTaskActorJdbcDao;

	public void setSysWfTaskActorJdbcDao(SysWfTaskActorJdbcDao sysWfTaskActorJdbcDao) {
		this.sysWfTaskActorJdbcDao = sysWfTaskActorJdbcDao;
	}
	
	public List<ComboBoxFormat> queryProcessForComboBox(){
		return sysWfTaskActorJdbcDao.queryProcessForComboBox();
	}
	
	public List<ComboBoxFormat> queryProcessNodeForComboBox(String in_strProcessId){
		return sysWfTaskActorJdbcDao.queryProcessNodeForComboBox(in_strProcessId);
	}
	
	public List<ComboBoxFormat> queryProcessVerForComboBox(String in_strProcessId){
		return sysWfTaskActorJdbcDao.queryProcessVerForComboBox(in_strProcessId);
	}
	
	public List<JbpmTask> queryProcessNodeForGrid(String in_strProcessId, String in_strTaskId){
		return sysWfTaskActorJdbcDao.queryProcessNodeForGrid(in_strProcessId, in_strTaskId);
	}
	
	public void addTaskActor(SysWfTaskActor in_sysWfTaskActor){
		sysWfTaskActorJdbcDao.addTaskActor(in_sysWfTaskActor);
	}
	
	public List<SysWfTaskActor> queryTaskActorForGrid(String in_strProcessId,
			String in_strTackId){
		return sysWfTaskActorJdbcDao.queryTaskActorForGrid(in_strProcessId, in_strTackId);
	}
	
	public void deleteTaskActor(String in_strTaskActorIdList){
		sysWfTaskActorJdbcDao.deleteTaskActor(in_strTaskActorIdList);
	}
	
	public List<CommonProcessInstance> getMyProcessInstances(String in_strSql) {
		return sysWfTaskActorJdbcDao.getMyProcessInstances(in_strSql);
	}
	
	public List<CommonProcessInstance> getMyProcessInstancesHistory(String in_strSql) {
		return sysWfTaskActorJdbcDao.getMyProcessInstancesHistory(in_strSql);
	}
	public List getLeadTaskIns(String in_strSql){
		return sysWfTaskActorJdbcDao.getLeadTaskIns(in_strSql);
	}
	/**
	 * 
	 * @param businessRecordId
	 * @return
	 */
	public String findWorkflowPictureTaskId(String businessRecordId){
		
		return sysWfTaskActorJdbcDao.findWorkflowPictureTaskId(businessRecordId);
	}
	
	public void copyProcessConfig(String poid,String copoid){
		sysWfTaskActorJdbcDao.copyProcessConfig(poid,copoid);
	}
	
	public void reloadTransition(String nodeid){
		sysWfTaskActorJdbcDao.reloadTransition(nodeid);
	}
	
	public void deleteTransition(String tids){
		String[] toIdList = tids.split("\\|");
		for (int i = 0; i < toIdList.length; i++) {
			sysWfTaskActorJdbcDao.deleteTransition(toIdList[i]);
		}
	}
	public void updateTransition(String transitionId,
			String strColName, String strColValue) {
		sysWfTaskActorJdbcDao.updateTransition(transitionId,
				strColName, strColValue);
	}
	public List<Map> queryForAction(String nodeid){
		return sysWfTaskActorJdbcDao.queryForAction(nodeid);
	}
	public void saveOrUpdateAction(String processid,String nodeid,String actionid,String actionSQL, String actionType){
		sysWfTaskActorJdbcDao.saveOrUpdateAction(processid, nodeid, actionid, actionSQL, actionType);
	}
}
