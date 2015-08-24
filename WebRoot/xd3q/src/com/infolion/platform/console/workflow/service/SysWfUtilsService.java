package com.infolion.platform.console.workflow.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infolion.platform.console.workflow.dao.SysWfUtilsJdbcDao;
import com.infolion.platform.console.workflow.dto.TaskHisDto;
import com.infolion.platform.core.service.BaseJdbcService;
@Service
public class SysWfUtilsService extends BaseJdbcService{
	
	@Autowired
	private SysWfUtilsJdbcDao sysWfUtilsJdbcDao;

	public void setSysWfUtilsJdbcDao(SysWfUtilsJdbcDao sysWfUtilsJdbcDao) {
		this.sysWfUtilsJdbcDao = sysWfUtilsJdbcDao;
	}
	
	
	public List<TaskHisDto> queryTaskHisList(String businessRecorderId){
		return sysWfUtilsJdbcDao.queryTaskHistList(businessRecorderId);
	}



}
