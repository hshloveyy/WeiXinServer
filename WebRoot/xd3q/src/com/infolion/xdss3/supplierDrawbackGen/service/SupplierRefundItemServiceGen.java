/*
 * @(#)SupplierRefundItemServiceGen.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年07月07日 17点58分12秒
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
import com.infolion.xdss3.supplierDrawback.domain.SupplierRefundItem;
import com.infolion.xdss3.supplierDrawback.service.SupplierRefundItemService;
import com.infolion.xdss3.supplierDrawback.dao.SupplierRefundItemHibernateDao;

/**
 * <pre>
 * 供应商退款行项目(SupplierRefundItem)服务类
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
public class SupplierRefundItemServiceGen extends BaseService
{
	protected Log log = LogFactory.getFactory().getLog(this.getClass());

	@Autowired
	protected SupplierRefundItemHibernateDao supplierRefundItemHibernateDao;
	
	public void setSupplierRefundItemHibernateDao(SupplierRefundItemHibernateDao supplierRefundItemHibernateDao)
	{
		this.supplierRefundItemHibernateDao = supplierRefundItemHibernateDao;
	}
	
     /**
	 * 注入扩展服务类
	 */
	 @Autowired(required = false)
	 public void setAdvanceService(@Qualifier("supplierRefundItemAdvanceService") AdvanceService advanceService)
	 {
	  	super.setAdvanceService(advanceService);
	 }   
	 
	   
	/**
	 * 根据主键ID,取得供应商退款行项目实例
	 * @param id
	 * @return
	 */
	public SupplierRefundItem _getDetached(String id)
	{		
	    SupplierRefundItem supplierRefundItem = new SupplierRefundItem();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  supplierRefundItem = supplierRefundItemHibernateDao.getDetached(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(supplierRefundItem);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    supplierRefundItem.setOperationType(operationType);
	    
		return supplierRefundItem;	
	}
	
	/**
	 * 根据主键ID,取得供应商退款行项目实例
	 * @param id
	 * @return
	 */
	public SupplierRefundItem _get(String id)
	{		
	    SupplierRefundItem supplierRefundItem = new SupplierRefundItem();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  supplierRefundItem = supplierRefundItemHibernateDao.get(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(supplierRefundItem);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    supplierRefundItem.setOperationType(operationType);
	    
		return supplierRefundItem;	
	}
	
	/**
	 * 根据主键ID,取得供应商退款行项目实例
	 * @param id
	 * @return
	 */
	public SupplierRefundItem _getForEdit(String id)
	{		
	    SupplierRefundItem supplierRefundItem = _get(id);
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = supplierRefundItem.getOperationType();
	    if(OperationType.READONLY.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotEdit);
	    }

		return supplierRefundItem;	
	}
	
	/**
	 * 根据主键ID,取得供应商退款行项目实例副本
	 * @param id
	 * @return
	 */
	public SupplierRefundItem _getEntityCopy(String id)
	{		
	    SupplierRefundItem supplierRefundItem = new SupplierRefundItem();
		SupplierRefundItem supplierRefundItemOld = this._get(id);
		try
		{
			BeanUtils.copyProperties(supplierRefundItem, supplierRefundItemOld);
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (InvocationTargetException e)
		{
			e.printStackTrace();
		}
		//supplierRefundItem.setRefundmentitemid(null); 
		return supplierRefundItem;	
	}
	
	/**
	 * 删除  
	 *   
	 * @param supplierRefundItem
	 */
	public void _delete(SupplierRefundItem supplierRefundItem)
	{
		if (null != advanceService)
			advanceService.preDelete(supplierRefundItem);
	
 		LockService.isBoInstanceLocked(supplierRefundItem,SupplierRefundItem.class);
		supplierRefundItemHibernateDao.remove(supplierRefundItem);

		if (null != advanceService)
			advanceService.postDelete(supplierRefundItem);
	}

	/**
	 * 根据主键删除
	 * 
	 * @param supplierRefundItemId
	 */
	public void _delete(String supplierRefundItemId,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(supplierRefundItemId))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("refundmentitemid"));
		SupplierRefundItem supplierRefundItem = this.supplierRefundItemHibernateDao.load(supplierRefundItemId);
		_delete(supplierRefundItem);
	}
	
	/**
	 * 删除  
	 *   
	 * @param Set<SupplierRefundItem> supplierRefundItems
	 */
	public void _deletes(Set<SupplierRefundItem> supplierRefundItems,BusinessObject businessObject)
	{
		if (null == supplierRefundItems || supplierRefundItems.size() < 1)
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoText());
		Iterator<SupplierRefundItem> it = supplierRefundItems.iterator();
		while (it.hasNext())
		{
			SupplierRefundItem supplierRefundItem = (SupplierRefundItem) it.next();
			_delete(supplierRefundItem);
		}
	}
	
	/**
	 * 根据多个主键(由，分割)删除多个对象
	 * 
	 * @param supplierRefundItemIds
	 */
	public void _deletes(String supplierRefundItemIds,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(supplierRefundItemIds))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("refundmentitemid"));
		String[] ids = StringUtils.splitString(supplierRefundItemIds);
		for (int i = 0; i < ids.length; i++)
		{
			_delete(ids[i],businessObject);
		}
	}
	/**
	 * 提交工作流
	 * 
	 * @param supplierRefundItem
	 */
	public void _submitProcess(SupplierRefundItem supplierRefundItem
	,BusinessObject businessObject)
	{
		String id = supplierRefundItem.getRefundmentitemid();
        /**
		if (StringUtils.isNullBlank(id))
		{
			_save(supplierRefundItem);
		}
		else
		{
			_update(supplierRefundItem
			, businessObject);
		}**/
		
		String taskId = supplierRefundItem.getWorkflowTaskId();
		id = supplierRefundItem.getRefundmentitemid();
		if (null == taskId || "".equals(taskId))
			WorkflowService.createAndSignalProcessInstance(supplierRefundItem, id);
		else
			WorkflowService.signalProcessInstance(supplierRefundItem, id, null);
	}

	/**
	 * 保存或更新供应商退款行项目
	 * 保存  
	 *  
	 * @param supplierRefundItem
	 */
	public void _update(SupplierRefundItem supplierRefundItem
,BusinessObject businessObject	
	)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(supplierRefundItem);
		supplierRefundItemHibernateDao.saveOrUpdate(supplierRefundItem);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(supplierRefundItem);
	}
	
	/**
	 * 保存  
	 *   
	 * @param supplierRefundItem
	 */
	public void _save(SupplierRefundItem supplierRefundItem)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(supplierRefundItem);
		// 对id赋空值，使之执行insert操作(适应复制创建场景)
		supplierRefundItem.setRefundmentitemid(null);
                                          		supplierRefundItemHibernateDao.saveOrUpdate(supplierRefundItem);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(supplierRefundItem);
	}
	
	/**
	 *
	 * 保存  
	 *   
	 * @param supplierRefundItem
	 */
	public void _saveOrUpdate(SupplierRefundItem supplierRefundItem
,BusinessObject businessObject		
	)
	{
		if (StringUtils.isNullBlank(supplierRefundItem.getRefundmentitemid()))
		{	
			_save(supplierRefundItem);
		}
		else
		{
			_update(supplierRefundItem
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