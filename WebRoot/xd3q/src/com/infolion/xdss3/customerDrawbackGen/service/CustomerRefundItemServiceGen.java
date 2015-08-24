/*
 * @(#)CustomerRefundItemServiceGen.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年07月07日 17点57分49秒
 *  描　述：创建
 */
package com.infolion.xdss3.customerDrawbackGen.service;

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
import com.infolion.xdss3.customerDrawback.domain.CustomerRefundItem;
import com.infolion.xdss3.customerDrawback.service.CustomerRefundItemService;
import com.infolion.xdss3.customerDrawback.dao.CustomerRefundItemHibernateDao;

/**
 * <pre>
 * 客户退款行项目(CustomerRefundItem)服务类
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
public class CustomerRefundItemServiceGen extends BaseService
{
	protected Log log = LogFactory.getFactory().getLog(this.getClass());

	@Autowired
	protected CustomerRefundItemHibernateDao customerRefundItemHibernateDao;
	
	public void setCustomerRefundItemHibernateDao(CustomerRefundItemHibernateDao customerRefundItemHibernateDao)
	{
		this.customerRefundItemHibernateDao = customerRefundItemHibernateDao;
	}
	
     /**
	 * 注入扩展服务类
	 */
	 @Autowired(required = false)
	 public void setAdvanceService(@Qualifier("customerRefundItemAdvanceService") AdvanceService advanceService)
	 {
	  	super.setAdvanceService(advanceService);
	 }   
	 
	   
	/**
	 * 根据主键ID,取得客户退款行项目实例
	 * @param id
	 * @return
	 */
	public CustomerRefundItem _getDetached(String id)
	{		
	    CustomerRefundItem customerRefundItem = new CustomerRefundItem();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  customerRefundItem = customerRefundItemHibernateDao.getDetached(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(customerRefundItem);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    customerRefundItem.setOperationType(operationType);
	    
		return customerRefundItem;	
	}
	
	/**
	 * 根据主键ID,取得客户退款行项目实例
	 * @param id
	 * @return
	 */
	public CustomerRefundItem _get(String id)
	{		
	    CustomerRefundItem customerRefundItem = new CustomerRefundItem();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  customerRefundItem = customerRefundItemHibernateDao.get(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(customerRefundItem);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    customerRefundItem.setOperationType(operationType);
	    
		return customerRefundItem;	
	}
	
	/**
	 * 根据主键ID,取得客户退款行项目实例
	 * @param id
	 * @return
	 */
	public CustomerRefundItem _getForEdit(String id)
	{		
	    CustomerRefundItem customerRefundItem = _get(id);
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = customerRefundItem.getOperationType();
	    if(OperationType.READONLY.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotEdit);
	    }

		return customerRefundItem;	
	}
	
	/**
	 * 根据主键ID,取得客户退款行项目实例副本
	 * @param id
	 * @return
	 */
	public CustomerRefundItem _getEntityCopy(String id)
	{		
	    CustomerRefundItem customerRefundItem = new CustomerRefundItem();
		CustomerRefundItem customerRefundItemOld = this._get(id);
		try
		{
			BeanUtils.copyProperties(customerRefundItem, customerRefundItemOld);
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (InvocationTargetException e)
		{
			e.printStackTrace();
		}
		//customerRefundItem.setRefundmentitemid(null); 
		return customerRefundItem;	
	}
	
	/**
	 * 删除  
	 *   
	 * @param customerRefundItem
	 */
	public void _delete(CustomerRefundItem customerRefundItem)
	{
		if (null != advanceService)
			advanceService.preDelete(customerRefundItem);
	
 		LockService.isBoInstanceLocked(customerRefundItem,CustomerRefundItem.class);
		customerRefundItemHibernateDao.remove(customerRefundItem);

		if (null != advanceService)
			advanceService.postDelete(customerRefundItem);
	}

	/**
	 * 根据主键删除
	 * 
	 * @param customerRefundItemId
	 */
	public void _delete(String customerRefundItemId,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(customerRefundItemId))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("refundmentitemid"));
		CustomerRefundItem customerRefundItem = this.customerRefundItemHibernateDao.load(customerRefundItemId);
		_delete(customerRefundItem);
	}
	
	/**
	 * 删除  
	 *   
	 * @param Set<CustomerRefundItem> customerRefundItems
	 */
	public void _deletes(Set<CustomerRefundItem> customerRefundItems,BusinessObject businessObject)
	{
		if (null == customerRefundItems || customerRefundItems.size() < 1)
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoText());
		Iterator<CustomerRefundItem> it = customerRefundItems.iterator();
		while (it.hasNext())
		{
			CustomerRefundItem customerRefundItem = (CustomerRefundItem) it.next();
			_delete(customerRefundItem);
		}
	}
	
	/**
	 * 根据多个主键(由，分割)删除多个对象
	 * 
	 * @param customerRefundItemIds
	 */
	public void _deletes(String customerRefundItemIds,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(customerRefundItemIds))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("refundmentitemid"));
		String[] ids = StringUtils.splitString(customerRefundItemIds);
		for (int i = 0; i < ids.length; i++)
		{
			_delete(ids[i],businessObject);
		}
	}
	/**
	 * 提交工作流
	 * 
	 * @param customerRefundItem
	 */
	public void _submitProcess(CustomerRefundItem customerRefundItem
	,BusinessObject businessObject)
	{
		String id = customerRefundItem.getRefundmentitemid();
        /**
		if (StringUtils.isNullBlank(id))
		{
			_save(customerRefundItem);
		}
		else
		{
			_update(customerRefundItem
			, businessObject);
		}**/
		
		String taskId = customerRefundItem.getWorkflowTaskId();
		id = customerRefundItem.getRefundmentitemid();
		if (null == taskId || "".equals(taskId))
			WorkflowService.createAndSignalProcessInstance(customerRefundItem, id);
		else
			WorkflowService.signalProcessInstance(customerRefundItem, id, null);
	}

	/**
	 * 保存或更新客户退款行项目
	 * 保存  
	 *  
	 * @param customerRefundItem
	 */
	public void _update(CustomerRefundItem customerRefundItem
,BusinessObject businessObject	
	)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(customerRefundItem);
		customerRefundItemHibernateDao.saveOrUpdate(customerRefundItem);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(customerRefundItem);
	}
	
	/**
	 * 保存  
	 *   
	 * @param customerRefundItem
	 */
	public void _save(CustomerRefundItem customerRefundItem)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(customerRefundItem);
		// 对id赋空值，使之执行insert操作(适应复制创建场景)
		customerRefundItem.setRefundmentitemid(null);
                                          		customerRefundItemHibernateDao.saveOrUpdate(customerRefundItem);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(customerRefundItem);
	}
	
	/**
	 *
	 * 保存  
	 *   
	 * @param customerRefundItem
	 */
	public void _saveOrUpdate(CustomerRefundItem customerRefundItem
,BusinessObject businessObject		
	)
	{
		if (StringUtils.isNullBlank(customerRefundItem.getRefundmentitemid()))
		{	
			_save(customerRefundItem);
		}
		else
		{
			_update(customerRefundItem
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