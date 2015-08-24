/*
 * @(#)SalesDocItemServiceGen.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2009年12月02日 17点06分52秒
 *  描　述：创建
 */
package com.infolion.sample.salesGen.service;

import net.sf.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.lang.reflect.InvocationTargetException;
import com.infolion.platform.basicApp.authManage.UserContextHolder;
import com.infolion.platform.dpframework.util.DateUtils;
import com.infolion.platform.dpframework.util.StringUtils;
import com.infolion.platform.dpframework.core.BusinessException;
import com.infolion.platform.dpframework.core.Constants;
import com.infolion.platform.dpframework.core.service.BaseService;
import com.infolion.platform.dpframework.uicomponent.number.service.NumberService;
import com.infolion.platform.workflow.engine.WorkflowService;
import com.infolion.platform.dpframework.uicomponent.lock.LockService;
import com.infolion.platform.dpframework.uicomponent.lock.LockException;
import com.infolion.platform.basicApp.authManage.domain.OperationType;
import com.infolion.platform.dpframework.core.service.AdvanceService;
import com.infolion.sample.sales.dao.SalesDocItemHibernateDao;
import com.infolion.sample.sales.domain.SalesDocItem;
import com.infolion.sample.sales.service.SalesDocItemService;

/**
 * <pre>
 * SAP销售凭证行项目(SalesDocItem)服务类
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
public class SalesDocItemServiceGen extends BaseService
{
	private Log log = LogFactory.getFactory().getLog(this.getClass());

	@Autowired
	private SalesDocItemHibernateDao salesDocItemHibernateDao;
	
	public void setSalesDocItemHibernateDao(SalesDocItemHibernateDao salesDocItemHibernateDao)
	{
		this.salesDocItemHibernateDao = salesDocItemHibernateDao;
	}
	
     /**
	 * 注入扩展服务类
	 */
	 @Autowired(required = false)
	 public void setAdvanceService(@Qualifier("salesDocItemAdvanceService") AdvanceService advanceService)
	 {
	  	super.setAdvanceService(advanceService);
	 }   
	 
	   
	/**
	 * 根据主键ID,取得SAP销售凭证行项目实例
	 * @param id
	 * @return
	 */
	public SalesDocItem _get(String id)
	{		
	    SalesDocItem salesDocItem = new SalesDocItem();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  salesDocItem = salesDocItemHibernateDao.get(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(salesDocItem);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
		  throw new LockException("记录已被锁定，您无权查看该记录！");
	    }
	    salesDocItem.setOperationType(operationType);
	    
		return salesDocItem;	
	}
	
		/**
	 * 根据主键ID,取得SAP销售凭证行项目实例
	 * @param id
	 * @return
	 */
	public SalesDocItem _getForEdit(String id)
	{		
	    SalesDocItem salesDocItem = _get(id);
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = salesDocItem.getOperationType();
	    if(OperationType.READONLY.equals(operationType))
	    {
		  throw new LockException("记录已被锁定，您无权编辑该记录！");
	    }

		return salesDocItem;	
	}
	
	/**
	 * 根据主键ID,取得SAP销售凭证行项目实例副本
	 * @param id
	 * @return
	 */
	public SalesDocItem _getEntityCopy(String id)
	{		
	    SalesDocItem salesDocItem = new SalesDocItem();
		SalesDocItem salesDocItemOld = this._get(id);
		try
		{
			BeanUtils.copyProperties(salesDocItem, salesDocItemOld);
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (InvocationTargetException e)
		{
			e.printStackTrace();
		}
		salesDocItem.setVbeln(null); 
	    
		return salesDocItem;	
	}
}