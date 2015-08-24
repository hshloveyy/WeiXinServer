/*
 * @(#)SysWfProcessAccessoryService.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：傅渊源
 *  时　间：2009-1-5
 *  描　述：创建
 */

package com.infolion.platform.console.workflow.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infolion.platform.console.workflow.dao.SysWfProcessAccessoryJdbcDao;
import com.infolion.platform.console.workflow.domain.JbpmProcessdefinition;
import com.infolion.platform.console.workflow.domain.SysWfProcessAccessory;
import com.infolion.platform.core.service.BaseService;
@Service
public class SysWfProcessAccessoryService extends BaseService {
	@Autowired
	private SysWfProcessAccessoryJdbcDao sSysWfProcessAccessoryJdbcDao;

	public void setSSysWfProcessAccessoryJdbcDao(
			SysWfProcessAccessoryJdbcDao sysWfProcessAccessoryJdbcDao) {
		sSysWfProcessAccessoryJdbcDao = sysWfProcessAccessoryJdbcDao;
	}
	
	public List<SysWfProcessAccessory> queryProcessAccessory(String in_strProcessId){
		return sSysWfProcessAccessoryJdbcDao.queryProcessAccessory(in_strProcessId);
	}
	
	public void addProcessAccessory(SysWfProcessAccessory in_sysWfProcessAccessory){
		sSysWfProcessAccessoryJdbcDao.addProcessAccessory(in_sysWfProcessAccessory);
	}
	
	public void deleteProcessAccessory(String in_accessoryIdList){
		sSysWfProcessAccessoryJdbcDao.deleteProcessAccessory(in_accessoryIdList);
	}
	
	public List<JbpmProcessdefinition> queryProcessDefinition(){
		return sSysWfProcessAccessoryJdbcDao.queryProcessDefinition();
	}
	/**
	 * 删除附件记录(单条)
	 * @param fileName
	 * @return
	 */
	public int delProcessAccessory(String fileName){
		return sSysWfProcessAccessoryJdbcDao.delProcessAccessory(fileName);
	}
}
