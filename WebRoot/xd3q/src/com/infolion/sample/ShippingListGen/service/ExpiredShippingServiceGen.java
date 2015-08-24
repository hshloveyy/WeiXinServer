/*
 * @(#)ExpiredShippingServiceGen.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2009年12月04日 11点00分12秒
 *  描　述：创建
 */
package com.infolion.sample.ShippingListGen.service;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.infolion.platform.basicApp.authManage.domain.OperationType;
import com.infolion.platform.dpframework.core.BusinessException;
import com.infolion.platform.dpframework.core.service.AdvanceService;
import com.infolion.platform.dpframework.core.service.BaseService;
import com.infolion.platform.dpframework.uicomponent.lock.LockException;
import com.infolion.platform.dpframework.util.StringUtils;
import com.infolion.sample.ShippingList.dao.ExpiredShippingHibernateDao;
import com.infolion.sample.ShippingList.domain.ExpiredShipping;
import com.infolion.sample.ShippingList.domain.ExpiredShippingKey;

/**
 * <pre>
 * 过期出货清单(ExpiredShipping)服务类
 * </pre>
 * 
 * <br>
 * JDK版本:1.5
 * 
 * @author java业务平台代码生成工具
 * @version 1.0
 * @see The author for more details
 * @since 1.0
 */
@Service
public class ExpiredShippingServiceGen extends BaseService
{
	private Log log = LogFactory.getFactory().getLog(this.getClass());

	@Autowired
	private ExpiredShippingHibernateDao expiredShippingHibernateDao;
	
	public void setExpiredShippingHibernateDao(ExpiredShippingHibernateDao expiredShippingHibernateDao)
	{
		this.expiredShippingHibernateDao = expiredShippingHibernateDao;
	}
	
     /**
	 * 注入扩展服务类
	 */
	 @Autowired(required = false)
	 public void setAdvanceService(@Qualifier("expiredShippingAdvanceService") AdvanceService advanceService)
	 {
	  	super.setAdvanceService(advanceService);
	 }   
	 
	   
	/**
	 * 根据主键ID,取得过期出货清单实例
	 * @param id
	 * @return
	 */
	public ExpiredShipping _get(ExpiredShippingKey key)
	{		
	    ExpiredShipping expiredShipping = new ExpiredShipping();
	    if(StringUtils.isNullBlank(key.getVbeln()) || StringUtils.isNullBlank(key.getPosnr()) || StringUtils.isNullBlank(key.getClient()))
	    {
	   	    throw new BusinessException("ExpiredShippingKey，不能为空！");
	    }
	    
	    expiredShipping = expiredShippingHibernateDao.get(key);
	    
		return expiredShipping;	
	}
	
	/**
	 * 根据主键ID,取得过期出货清单实例
	 * @param id
	 * @return
	 */
	public ExpiredShipping _getForEdit(ExpiredShippingKey key)
	{		
	    ExpiredShipping expiredShipping = _get(key);
		return expiredShipping;	
	}
}