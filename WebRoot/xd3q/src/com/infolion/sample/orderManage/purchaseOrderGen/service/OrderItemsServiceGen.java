/*
 * @(#)OrderItemsServiceGen.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年03月31日 15点00分58秒
 *  描　述：创建
 */
package com.infolion.sample.orderManage.purchaseOrderGen.service;

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
import com.infolion.sample.orderManage.purchaseOrder.domain.OrderItems;
import com.infolion.sample.orderManage.purchaseOrder.service.OrderItemsService;
import com.infolion.sample.orderManage.purchaseOrder.dao.OrderItemsHibernateDao;

/**
 * <pre>
 * 采购行项目(OrderItems)服务类
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
public class OrderItemsServiceGen extends BaseService
{
	protected Log log = LogFactory.getFactory().getLog(this.getClass());

	@Autowired
	protected OrderItemsHibernateDao orderItemsHibernateDao;
	
	public void setOrderItemsHibernateDao(OrderItemsHibernateDao orderItemsHibernateDao)
	{
		this.orderItemsHibernateDao = orderItemsHibernateDao;
	}
	
     /**
	 * 注入扩展服务类
	 */
	 @Autowired(required = false)
	 public void setAdvanceService(@Qualifier("orderItemsAdvanceService") AdvanceService advanceService)
	 {
	  	super.setAdvanceService(advanceService);
	 }   
	 
	   
	/**
	 * 根据主键ID,取得采购行项目实例
	 * @param id
	 * @return
	 */
	public OrderItems _getDetached(String id)
	{		
	    OrderItems orderItems = new OrderItems();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  orderItems = orderItemsHibernateDao.getDetached(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(orderItems);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    orderItems.setOperationType(operationType);
	    
		return orderItems;	
	}
	
	/**
	 * 根据主键ID,取得采购行项目实例
	 * @param id
	 * @return
	 */
	public OrderItems _get(String id)
	{		
	    OrderItems orderItems = new OrderItems();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  orderItems = orderItemsHibernateDao.get(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(orderItems);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    orderItems.setOperationType(operationType);
	    
		return orderItems;	
	}
	
	/**
	 * 根据主键ID,取得采购行项目实例
	 * @param id
	 * @return
	 */
	public OrderItems _getForEdit(String id)
	{		
	    OrderItems orderItems = _get(id);
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = orderItems.getOperationType();
	    if(OperationType.READONLY.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotEdit);
	    }

		return orderItems;	
	}
	
	/**
	 * 根据主键ID,取得采购行项目实例副本
	 * @param id
	 * @return
	 */
	public OrderItems _getEntityCopy(String id)
	{		
	    OrderItems orderItems = new OrderItems();
		OrderItems orderItemsOld = this._get(id);
		try
		{
			BeanUtils.copyProperties(orderItems, orderItemsOld);
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (InvocationTargetException e)
		{
			e.printStackTrace();
		}
		//orderItems.setOrderItemsId(null); 
		return orderItems;	
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
	 * 保存或更新采购行项目
	 * 保存  
	 *  
	 * @param orderItems
	 */
	public void _update(OrderItems orderItems
,BusinessObject businessObject	
	)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(orderItems);
		orderItemsHibernateDao.saveOrUpdate(orderItems);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(orderItems);
	}
	
	/**
	 * 保存  
	 *   
	 * @param orderItems
	 */
	public void _save(OrderItems orderItems)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(orderItems);
		// 对id赋空值，使之执行insert操作(适应复制创建场景)
		orderItems.setOrderItemsId(null);
                  		orderItemsHibernateDao.saveOrUpdate(orderItems);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(orderItems);
	}
	
	/**
	 *
	 * 保存  
	 *   
	 * @param orderItems
	 */
	public void _saveOrUpdate(OrderItems orderItems
,BusinessObject businessObject		
	)
	{
		if (StringUtils.isNullBlank(orderItems.getOrderItemsId()))
		{	
			_save(orderItems);
		}
		else
		{
			_update(orderItems
, businessObject);
}	}
	
	
	/**
	 * 删除  
	 *   
	 * @param orderItems
	 */
	public void _delete(OrderItems orderItems)
	{
		if (null != advanceService)
			advanceService.preDelete(orderItems);
	
 		LockService.isBoInstanceLocked(orderItems,OrderItems.class);
		orderItemsHibernateDao.remove(orderItems);

		if (null != advanceService)
			advanceService.postDelete(orderItems);
	}

	/**
	 * 根据主键删除
	 * 
	 * @param orderItemsId
	 */
	public void _delete(String orderItemsId,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(orderItemsId))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("orderItemsId"));
		OrderItems orderItems = this.orderItemsHibernateDao.load(orderItemsId);
		_delete(orderItems);
	}
	
	/**
	 * 删除  
	 *   
	 * @param Set<OrderItems> orderItemss
	 */
	public void _deletes(Set<OrderItems> orderItemss,BusinessObject businessObject)
	{
		if (null == orderItemss || orderItemss.size() < 1)
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoText());
		Iterator<OrderItems> it = orderItemss.iterator();
		while (it.hasNext())
		{
			OrderItems orderItems = (OrderItems) it.next();
			_delete(orderItems);
		}
	}
	
	/**
	 * 根据多个主键(由，分割)删除多个对象
	 * 
	 * @param orderItemsIds
	 */
	public void _deletes(String orderItemsIds,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(orderItemsIds))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("orderItemsId"));
		String[] ids = StringUtils.splitString(orderItemsIds);
		for (int i = 0; i < ids.length; i++)
		{
			_delete(ids[i],businessObject);
		}
	}
	/**
	 * 提交工作流
	 * 
	 * @param orderItems
	 */
	public void _submitProcess(OrderItems orderItems
	,BusinessObject businessObject)
	{
		String id = orderItems.getOrderItemsId();
		if (StringUtils.isNullBlank(id))
		{
			_save(orderItems);
		}
		else
		{
			_update(orderItems
			, businessObject);
		}
		String taskId = orderItems.getWorkflowTaskId();
		if (null == taskId || "".equals(taskId))
			WorkflowService.createAndSignalProcessInstance(orderItems, id);
		else
			WorkflowService.signalProcessInstance(orderItems, id, null);
	}
}