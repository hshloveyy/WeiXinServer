/*
 * @(#)PurchasingDocItemServiceGen.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2009年12月01日 19点57分06秒
 *  描　述：创建
 */
package com.infolion.sample.purchaseGen.service;

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
import com.infolion.sample.purchase.dao.PurchasingDocItemHibernateDao;
import com.infolion.sample.purchase.domain.PurchasingDocItem;
import com.infolion.sample.purchase.service.PurchasingDocItemService;

/**
 * <pre>
 * SAP采购凭证行项目(PurchasingDocItem)服务类
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
public class PurchasingDocItemServiceGen extends BaseService
{
	private Log log = LogFactory.getFactory().getLog(this.getClass());

	@Autowired
	private PurchasingDocItemHibernateDao purchasingDocItemHibernateDao;
	
	public void setPurchasingDocItemHibernateDao(PurchasingDocItemHibernateDao purchasingDocItemHibernateDao)
	{
		this.purchasingDocItemHibernateDao = purchasingDocItemHibernateDao;
	}
	
     /**
	 * 注入扩展服务类
	 */
	 @Autowired(required = false)
	 public void setAdvanceService(@Qualifier("purchasingDocItemAdvanceService") AdvanceService advanceService)
	 {
	  	super.setAdvanceService(advanceService);
	 }   
	 
	   
	/**
	 * 根据主键ID,取得SAP采购凭证行项目实例
	 * @param id
	 * @return
	 */
	public PurchasingDocItem _get(String id)
	{		
	    PurchasingDocItem purchasingDocItem = new PurchasingDocItem();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  purchasingDocItem = purchasingDocItemHibernateDao.get(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(purchasingDocItem);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
		  throw new LockException("记录已被锁定，您无权查看该记录！");
	    }
	    purchasingDocItem.setOperationType(operationType);
	    
		return purchasingDocItem;	
	}
	
		/**
	 * 根据主键ID,取得SAP采购凭证行项目实例
	 * @param id
	 * @return
	 */
	public PurchasingDocItem _getForEdit(String id)
	{		
	    PurchasingDocItem purchasingDocItem = _get(id);
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = purchasingDocItem.getOperationType();
	    if(OperationType.READONLY.equals(operationType))
	    {
		  throw new LockException("记录已被锁定，您无权编辑该记录！");
	    }

		return purchasingDocItem;	
	}
	
	/**
	 * 根据主键ID,取得SAP采购凭证行项目实例副本
	 * @param id
	 * @return
	 */
	public PurchasingDocItem _getEntityCopy(String id)
	{		
	    PurchasingDocItem purchasingDocItem = new PurchasingDocItem();
		PurchasingDocItem purchasingDocItemOld = this._get(id);
		try
		{
			BeanUtils.copyProperties(purchasingDocItem, purchasingDocItemOld);
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (InvocationTargetException e)
		{
			e.printStackTrace();
		}
		purchasingDocItem.setEbeln(null); 
	    
		return purchasingDocItem;	
	}
	
	/**
	 * 查询  
	 *   
	 * @param queryCondition
	 * @return
	 */
	public List _query(String queryCondition)
	{
		return null;
	}
	
	/**
	 * 删除  
	 *   
	 * @param purchasingDocItem
	 */
	public void _delete(PurchasingDocItem purchasingDocItem)
	{
		if (null != advanceService)
			advanceService.preDelete(purchasingDocItem);
	
 		LockService.isBoInstanceLocked(purchasingDocItem,PurchasingDocItem.class);
		purchasingDocItemHibernateDao.remove(purchasingDocItem);

		if (null != advanceService)
			advanceService.postDelete(purchasingDocItem);
	}

	/**
	 * 根据主键删除
	 * 
	 * @param purchasingDocItemId
	 */
	public void _delete(String purchasingDocItemId)
	{
		if (StringUtils.isNullBlank(purchasingDocItemId))
			throw new BusinessException("不存在需要删除的SAP采购凭证行项目。");
		PurchasingDocItem purchasingDocItem = this.purchasingDocItemHibernateDao.load(purchasingDocItemId);
		_delete(purchasingDocItem);
	}
	
	/**
	 * 删除  
	 *   
	 * @param Set<PurchasingDocItem> purchasingDocItems
	 */
	public void _deletes(Set<PurchasingDocItem> purchasingDocItems)
	{
		if (null == purchasingDocItems || purchasingDocItems.size() < 1)
			throw new BusinessException("不存在需要删除的SAP采购凭证行项目。");
		Iterator<PurchasingDocItem> it = purchasingDocItems.iterator();
		while (it.hasNext())
		{
			PurchasingDocItem purchasingDocItem = (PurchasingDocItem) it.next();
			_delete(purchasingDocItem);
		}
	}
	
	/**
	 * 根据多个主键(由，分割)删除多个对象
	 * 
	 * @param purchasingDocItemIds
	 */
	public void _deletes(String purchasingDocItemIds)
	{
		if (StringUtils.isNullBlank(purchasingDocItemIds))
			throw new BusinessException("不存在需要删除的SAP采购凭证行项目。");
		String[] ids = StringUtils.splitString(purchasingDocItemIds);
		for (int i = 0; i < ids.length; i++)
		{
			_delete(ids[i]);
		}
	}
	/**
	 * 提交工作流
	 * 
	 * @param purchasingDocItem
	 */
	public void _submitProcess(PurchasingDocItem purchasingDocItem
	)
	{
		String id = purchasingDocItem.getEbeln();
		if (StringUtils.isNullBlank(id))
		{
			_save(purchasingDocItem);
		}
		else
		{
			_update(purchasingDocItem
			);
		}
		String taskId = purchasingDocItem.getWorkflowTaskId();
		if (null == taskId || "".equals(taskId))
			WorkflowService.createAndSignalProcessInstance(purchasingDocItem, id);
		else
			WorkflowService.signalProcessInstance(purchasingDocItem, id, null);
	}

	/**
	 * 保存或更新SAP采购凭证行项目
	 * 保存  
	 *  
	 * @param purchasingDocItem
	 */
	public void _update(PurchasingDocItem purchasingDocItem
	)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(purchasingDocItem);
		purchasingDocItemHibernateDao.saveOrUpdate(purchasingDocItem);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(purchasingDocItem);
	}
	
	/**
	 * 保存  
	 *   
	 * @param purchasingDocItem
	 */
	public void _save(PurchasingDocItem purchasingDocItem)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(purchasingDocItem);
		// 对id赋空值，使之执行insert操作(适应复制创建场景)
		purchasingDocItem.setEbeln(null);
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                  		purchasingDocItemHibernateDao.saveOrUpdate(purchasingDocItem);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(purchasingDocItem);
	}
	
	/**
	 *
	 * 保存  
	 *   
	 * @param purchasingDocItem
	 */
	public void _saveOrUpdate(PurchasingDocItem purchasingDocItem
	)
	{
		if (StringUtils.isNullBlank(purchasingDocItem.getEbeln()))
		{	
			_save(purchasingDocItem);
		}
		else
		{
			_update(purchasingDocItem
);
}	}
	
}