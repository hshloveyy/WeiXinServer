package com.infolion.xdss3.payment.service;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infolion.xdss3.payment.dao.ParticularWorkflowXD3QJdbcDao;

@Service
public class ParticularWorkflowXD3QService {
	
	@Autowired
	private ParticularWorkflowXD3QJdbcDao particularWorkflowXD3QJdbcDao;
	public void setParticularWorkflowXD3QJdbcDao(
			ParticularWorkflowXD3QJdbcDao particularWorkflowXD3QJdbcDao) {
		this.particularWorkflowXD3QJdbcDao = particularWorkflowXD3QJdbcDao;
	}
	
	public String getNearestNoPassNodeName(String bizId) {
		if(StringUtils.isBlank(bizId))
			return null;
		return this.particularWorkflowXD3QJdbcDao.getNearestNoPassNodeName(bizId);
	}

}
