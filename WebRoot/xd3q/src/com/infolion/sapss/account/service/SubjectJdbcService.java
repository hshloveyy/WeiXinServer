package com.infolion.sapss.account.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infolion.platform.core.service.BaseJdbcService;
import com.infolion.sapss.account.dao.SubjectInfoJdbcDao;
import com.infolion.sapss.account.web.SubjectAdditionalVO;
import com.infolion.sapss.account.web.SubjectFinalVO;

@Service
public class SubjectJdbcService extends BaseJdbcService {
	@Autowired
	private SubjectInfoJdbcDao jdbcDao;

	public void setJdbcDao(SubjectInfoJdbcDao jdbcDao) {
		this.jdbcDao = jdbcDao;
	}

	public int saveAdditionalInfo(SubjectAdditionalVO info) {
		return this.jdbcDao.saveAdditionalInfo(info);
	}
	
	public int saveFinalInfo(SubjectFinalVO info) {
		return this.jdbcDao.saveFinalInfo(info);
	}
}
