/*
 * @(#)SupplierDbBankItemServiceGen.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年07月07日 17点58分11秒
 *  描　述：创建
 */
package com.infolion.xdss3.supplierDrawbackGen.service;

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
import com.infolion.platform.dpframework.uicomponent.systemMessage.SysMsgConstants;
import com.infolion.platform.bo.domain.BusinessObject;
import com.infolion.xdss3.supplierDrawback.domain.SupplierDbBankItem;
import com.infolion.xdss3.supplierDrawback.service.SupplierDbBankItemService;
import com.infolion.xdss3.supplierDrawback.dao.SupplierDbBankItemHibernateDao;

/**
 * <pre>
 * 供应商退款银行(SupplierDbBankItem)服务类
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
public class SupplierDbBankItemServiceGen extends BaseService
{
	protected Log log = LogFactory.getFactory().getLog(this.getClass());

	@Autowired
	protected SupplierDbBankItemHibernateDao supplierDbBankItemHibernateDao;
	
	public void setSupplierDbBankItemHibernateDao(SupplierDbBankItemHibernateDao supplierDbBankItemHibernateDao)
	{
		this.supplierDbBankItemHibernateDao = supplierDbBankItemHibernateDao;
	}
	
     /**
	 * 注入扩展服务类
	 */
	 @Autowired(required = false)
	 public void setAdvanceService(@Qualifier("supplierDbBankItemAdvanceService") AdvanceService advanceService)
	 {
	  	super.setAdvanceService(advanceService);
	 }   
	 
	   
	/**
	 * 根据主键ID,取得供应商退款银行实例
	 * @param id
	 * @return
	 */
	public SupplierDbBankItem _getDetached(String id)
	{		
	    SupplierDbBankItem supplierDbBankItem = new SupplierDbBankItem();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  supplierDbBankItem = supplierDbBankItemHibernateDao.getDetached(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(supplierDbBankItem);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    supplierDbBankItem.setOperationType(operationType);
	    
		return supplierDbBankItem;	
	}
	
	/**
	 * 根据主键ID,取得供应商退款银行实例
	 * @param id
	 * @return
	 */
	public SupplierDbBankItem _get(String id)
	{		
	    SupplierDbBankItem supplierDbBankItem = new SupplierDbBankItem();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  supplierDbBankItem = supplierDbBankItemHibernateDao.get(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(supplierDbBankItem);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    supplierDbBankItem.setOperationType(operationType);
	    
		return supplierDbBankItem;	
	}
	
	/**
	 * 根据主键ID,取得供应商退款银行实例
	 * @param id
	 * @return
	 */
	public SupplierDbBankItem _getForEdit(String id)
	{		
	    SupplierDbBankItem supplierDbBankItem = _get(id);
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = supplierDbBankItem.getOperationType();
	    if(OperationType.READONLY.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotEdit);
	    }

		return supplierDbBankItem;	
	}
	
	/**
	 * 根据主键ID,取得供应商退款银行实例副本
	 * @param id
	 * @return
	 */
	public SupplierDbBankItem _getEntityCopy(String id)
	{		
	    SupplierDbBankItem supplierDbBankItem = new SupplierDbBankItem();
		SupplierDbBankItem supplierDbBankItemOld = this._get(id);
		try
		{
			BeanUtils.copyProperties(supplierDbBankItem, supplierDbBankItemOld);
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (InvocationTargetException e)
		{
			e.printStackTrace();
		}
		//supplierDbBankItem.setRefundbankitemid(null); 
		return supplierDbBankItem;	
	}
	
	/**
	 * 删除  
	 *   
	 * @param supplierDbBankItem
	 */
	public void _delete(SupplierDbBankItem supplierDbBankItem)
	{
		if (null != advanceService)
			advanceService.preDelete(supplierDbBankItem);
	
 		LockService.isBoInstanceLocked(supplierDbBankItem,SupplierDbBankItem.class);
		supplierDbBankItemHibernateDao.remove(supplierDbBankItem);

		if (null != advanceService)
			advanceService.postDelete(supplierDbBankItem);
	}

	/**
	 * 根据主键删除
	 * 
	 * @param supplierDbBankItemId
	 */
	public void _delete(String supplierDbBankItemId,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(supplierDbBankItemId))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("refundbankitemid"));
		SupplierDbBankItem supplierDbBankItem = this.supplierDbBankItemHibernateDao.load(supplierDbBankItemId);
		_delete(supplierDbBankItem);
	}
	
	/**
	 * 删除  
	 *   
	 * @param Set<SupplierDbBankItem> supplierDbBankItems
	 */
	public void _deletes(Set<SupplierDbBankItem> supplierDbBankItems,BusinessObject businessObject)
	{
		if (null == supplierDbBankItems || supplierDbBankItems.size() < 1)
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoText());
		Iterator<SupplierDbBankItem> it = supplierDbBankItems.iterator();
		while (it.hasNext())
		{
			SupplierDbBankItem supplierDbBankItem = (SupplierDbBankItem) it.next();
			_delete(supplierDbBankItem);
		}
	}
	
	/**
	 * 根据多个主键(由，分割)删除多个对象
	 * 
	 * @param supplierDbBankItemIds
	 */
	public void _deletes(String supplierDbBankItemIds,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(supplierDbBankItemIds))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("refundbankitemid"));
		String[] ids = StringUtils.splitString(supplierDbBankItemIds);
		for (int i = 0; i < ids.length; i++)
		{
			_delete(ids[i],businessObject);
		}
	}
	/**
	 * 提交工作流
	 * 
	 * @param supplierDbBankItem
	 */
	public void _submitProcess(SupplierDbBankItem supplierDbBankItem
	,BusinessObject businessObject)
	{
		String id = supplierDbBankItem.getRefundbankitemid();
        /**
		if (StringUtils.isNullBlank(id))
		{
			_save(supplierDbBankItem);
		}
		else
		{
			_update(supplierDbBankItem
			, businessObject);
		}**/
		
		String taskId = supplierDbBankItem.getWorkflowTaskId();
		id = supplierDbBankItem.getRefundbankitemid();
		if (null == taskId || "".equals(taskId))
			WorkflowService.createAndSignalProcessInstance(supplierDbBankItem, id);
		else
			WorkflowService.signalProcessInstance(supplierDbBankItem, id, null);
	}

	/**
	 * 保存或更新供应商退款银行
	 * 保存  
	 *  
	 * @param supplierDbBankItem
	 */
	public void _update(SupplierDbBankItem supplierDbBankItem
,BusinessObject businessObject	
	)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(supplierDbBankItem);
		supplierDbBankItemHibernateDao.saveOrUpdate(supplierDbBankItem);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(supplierDbBankItem);
	}
	
	/**
	 * 保存  
	 *   
	 * @param supplierDbBankItem
	 */
	public void _save(SupplierDbBankItem supplierDbBankItem)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(supplierDbBankItem);
		// 对id赋空值，使之执行insert操作(适应复制创建场景)
		supplierDbBankItem.setRefundbankitemid(null);
                      		supplierDbBankItemHibernateDao.saveOrUpdate(supplierDbBankItem);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(supplierDbBankItem);
	}
	
	/**
	 *
	 * 保存  
	 *   
	 * @param supplierDbBankItem
	 */
	public void _saveOrUpdate(SupplierDbBankItem supplierDbBankItem
,BusinessObject businessObject		
	)
	{
		if (StringUtils.isNullBlank(supplierDbBankItem.getRefundbankitemid()))
		{	
			_save(supplierDbBankItem);
		}
		else
		{
			_update(supplierDbBankItem
, businessObject);
}	}
	
	
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
}