/*
 * @(#)CreditConfService.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：傅渊源
 *  时　间：2010-5-18
 *  描　述：创建
 */

package com.infolion.xdss3.ceditValueControl.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infolion.platform.core.service.BaseService;
import com.infolion.xdss3.ceditValueControl.dao.CreditConfJdbcDao;

@Service
public class CreditConfService extends BaseService
{
private Log log = LogFactory.getFactory().getLog(this.getClass());
	
	@Autowired
	private CreditConfJdbcDao creditConfJdbcDao;

	public void setCreditConfJdbcDao(CreditConfJdbcDao creditConfJdbcDao)
	{
		this.creditConfJdbcDao = creditConfJdbcDao;
	}
	
	/**
	 * 得到立项所对应的受信类型
	 * @param strProjectId
	 * @return
	 */
	public String getProjectCreditType(String strProjectId){
		return this.creditConfJdbcDao.getProjectCreditType(strProjectId);
	}
}
