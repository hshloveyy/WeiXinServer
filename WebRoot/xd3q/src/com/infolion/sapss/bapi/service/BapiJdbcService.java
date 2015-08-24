package com.infolion.sapss.bapi.service;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infolion.platform.core.service.BaseJdbcService;
import com.infolion.sapss.bapi.dao.BapiJdbcDao;
@Service
public class BapiJdbcService extends BaseJdbcService{

	@Autowired
	private BapiJdbcDao bapiJdbcDao;
	
	
	public void synchronizeData(String srcTableName,Map<String,Object> para) throws IOException{
		bapiJdbcDao.synchronizeData(srcTableName,para);		
	}
	public void synchronizeAllData() throws IOException{
		bapiJdbcDao.synchronizeAllData();		
	}
	
	
}
