/*
 * @(#)PurchaseOrderServiceGen.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年03月31日 15点00分57秒
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
import com.infolion.sample.orderManage.purchaseOrder.domain.PurchaseOrder;
import com.infolion.sample.orderManage.purchaseOrder.service.PurchaseOrderService;
import com.infolion.sample.orderManage.purchaseOrder.dao.PurchaseOrderHibernateDao;
          
import com.infolion.sample.orderManage.purchaseOrder.domain.OrderItems;
import com.infolion.sample.orderManage.purchaseOrder.service.OrderItemsService;
          
import com.infolion.platform.dpframework.uicomponent.attachement.domain.Attachement;
import com.infolion.platform.dpframework.uicomponent.attachement.service.AttachementService;
import com.infolion.platform.dpframework.uicomponent.attachement.dao.AttachementJdbcDao;

/**
 * <pre>
 * 采购订单(PurchaseOrder)服务类
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
public class PurchaseOrderServiceGen extends BaseService
{
	protected Log log = LogFactory.getFactory().getLog(this.getClass());

	@Autowired
	protected PurchaseOrderHibernateDao purchaseOrderHibernateDao;
	
	public void setPurchaseOrderHibernateDao(PurchaseOrderHibernateDao purchaseOrderHibernateDao)
	{
		this.purchaseOrderHibernateDao = purchaseOrderHibernateDao;
	}
	
     /**
	 * 注入扩展服务类
	 */
	 @Autowired(required = false)
	 public void setAdvanceService(@Qualifier("purchaseOrderAdvanceService") AdvanceService advanceService)
	 {
	  	super.setAdvanceService(advanceService);
	 }   
	 
          

	@Autowired
	protected OrderItemsService orderItemsService;
	
	public void setOrderItemsService(OrderItemsService orderItemsService)
	{
		this.orderItemsService = orderItemsService;
	}
	
          

	@Autowired
	protected AttachementService attachementService;
	
	public void setAttachementService(AttachementService attachementService)
	{
		this.attachementService = attachementService;
	}
	
	@Autowired
	protected AttachementJdbcDao attachementJdbcDao;
	
	public void setAttachementJdbcDao(AttachementJdbcDao attachementJdbcDao)
	{
		this.attachementJdbcDao = attachementJdbcDao;
	}
	   
	/**
	 * 根据主键ID,取得采购订单实例
	 * @param id
	 * @return
	 */
	public PurchaseOrder _getDetached(String id)
	{		
	    PurchaseOrder purchaseOrder = new PurchaseOrder();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  purchaseOrder = purchaseOrderHibernateDao.getDetached(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(purchaseOrder);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    purchaseOrder.setOperationType(operationType);
	    
		return purchaseOrder;	
	}
	
	/**
	 * 根据主键ID,取得采购订单实例
	 * @param id
	 * @return
	 */
	public PurchaseOrder _get(String id)
	{		
	    PurchaseOrder purchaseOrder = new PurchaseOrder();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  purchaseOrder = purchaseOrderHibernateDao.get(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(purchaseOrder);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    purchaseOrder.setOperationType(operationType);
	    
		return purchaseOrder;	
	}
	
	/**
	 * 根据主键ID,取得采购订单实例
	 * @param id
	 * @return
	 */
	public PurchaseOrder _getForEdit(String id)
	{		
	    PurchaseOrder purchaseOrder = _get(id);
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = purchaseOrder.getOperationType();
	    if(OperationType.READONLY.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotEdit);
	    }

		return purchaseOrder;	
	}
	
	/**
	 * 根据主键ID,取得采购订单实例副本
	 * @param id
	 * @return
	 */
	public PurchaseOrder _getEntityCopy(String id)
	{		
	    PurchaseOrder purchaseOrder = new PurchaseOrder();
		PurchaseOrder purchaseOrderOld = this._get(id);
		try
		{
			BeanUtils.copyProperties(purchaseOrder, purchaseOrderOld);
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (InvocationTargetException e)
		{
			e.printStackTrace();
		}
		purchaseOrder.setPurchaseOrderNo(null); 
		//purchaseOrder.setPurchaseOrderId(null); 
		purchaseOrder.setProcessState(" ");
		return purchaseOrder;	
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
	 * @param purchaseOrder
	 */
	public void _delete(PurchaseOrder purchaseOrder)
	{
		if (null != advanceService)
			advanceService.preDelete(purchaseOrder);
	
		//流程状态
		String processState =purchaseOrder.getProcessState();
		if(!StringUtils.isNullBlank(processState))
		{
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.CannotDeleteApproveData);
		}
 		LockService.isBoInstanceLocked(purchaseOrder,PurchaseOrder.class);
		purchaseOrderHibernateDao.remove(purchaseOrder);
//删除业务附件
String purchaseOrderId = purchaseOrder.getPurchaseOrderId();
attachementService.deleteByBusinessId(purchaseOrderId);
		if (null != advanceService)
			advanceService.postDelete(purchaseOrder);
	}

	/**
	 * 根据主键删除
	 * 
	 * @param purchaseOrderId
	 */
	public void _delete(String purchaseOrderId,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(purchaseOrderId))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("purchaseOrderId"));
		PurchaseOrder purchaseOrder = this.purchaseOrderHibernateDao.load(purchaseOrderId);
		_delete(purchaseOrder);
	}
	
	/**
	 * 删除  
	 *   
	 * @param Set<PurchaseOrder> purchaseOrders
	 */
	public void _deletes(Set<PurchaseOrder> purchaseOrders,BusinessObject businessObject)
	{
		if (null == purchaseOrders || purchaseOrders.size() < 1)
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoText());
		Iterator<PurchaseOrder> it = purchaseOrders.iterator();
		while (it.hasNext())
		{
			PurchaseOrder purchaseOrder = (PurchaseOrder) it.next();
			_delete(purchaseOrder);
		}
	}
	
	/**
	 * 根据多个主键(由，分割)删除多个对象
	 * 
	 * @param purchaseOrderIds
	 */
	public void _deletes(String purchaseOrderIds,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(purchaseOrderIds))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("purchaseOrderId"));
		String[] ids = StringUtils.splitString(purchaseOrderIds);
		for (int i = 0; i < ids.length; i++)
		{
			_delete(ids[i],businessObject);
		}
	}
	/**
	 * 提交工作流
	 * 
	 * @param purchaseOrder
	 */
	public void _submitProcess(PurchaseOrder purchaseOrder
,Set<OrderItems> deletedOrderItemsSet	,BusinessObject businessObject)
	{
		String id = purchaseOrder.getPurchaseOrderId();
		if (StringUtils.isNullBlank(id))
		{
			_save(purchaseOrder);
		}
		else
		{
			_update(purchaseOrder
,deletedOrderItemsSet			, businessObject);
		}
		String taskId = purchaseOrder.getWorkflowTaskId();
		if (null == taskId || "".equals(taskId))
			WorkflowService.createAndSignalProcessInstance(purchaseOrder, id);
		else
			WorkflowService.signalProcessInstance(purchaseOrder, id, null);
	}

	/**
	 * 保存或更新采购订单
	 * 保存  
	 *  
	 * @param purchaseOrder
	 */
	public void _update(PurchaseOrder purchaseOrder
,Set<OrderItems> deletedOrderItemsSet,BusinessObject businessObject	
	)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(purchaseOrder);
		purchaseOrderHibernateDao.saveOrUpdate(purchaseOrder);
// 删除关联子业务对象数据
if(deletedOrderItemsSet!=null && deletedOrderItemsSet.size()>0)
{
orderItemsService._deletes(deletedOrderItemsSet,businessObject);
}		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(purchaseOrder);
	}
	
	/**
	 * 保存  
	 *   
	 * @param purchaseOrder
	 */
	public void _save(PurchaseOrder purchaseOrder)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(purchaseOrder);
		// 对id赋空值，使之执行insert操作(适应复制创建场景)
		purchaseOrder.setPurchaseOrderId(null);
       
     
       
     
       
     
       
     
       
     
       
     
       
     
       
     
       
     
       
     
       
     
       
     
       
     
       
     
       
		Set<OrderItems> orderItemsSet = purchaseOrder.getOrderItems();
		Set<OrderItems> newOrderItemsSet = null;
		if (null != orderItemsSet)
		{
			newOrderItemsSet = new HashSet();
			Iterator<OrderItems> itOrderItems = orderItemsSet.iterator();
			while (itOrderItems.hasNext())
			{
				OrderItems orderItems = (OrderItems) itOrderItems.next();
				orderItems.setOrderItemsId(null);
				newOrderItemsSet.add(orderItems);
			}
		}
		purchaseOrder.setOrderItems(newOrderItemsSet);
     
       
     
		purchaseOrderHibernateDao.saveOrUpdate(purchaseOrder);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(purchaseOrder);
	}
	
	/**
	 *
	 * 保存  
	 *   
	 * @param purchaseOrder
	 */
	public void _saveOrUpdate(PurchaseOrder purchaseOrder
,Set<OrderItems> deletedOrderItemsSet
,Set<Attachement> attachements,BusinessObject businessObject		
	)
	{
if (StringUtils.isNullBlank(purchaseOrder.getPurchaseOrderNo()))
{
String purchaseOrderNo = NumberService.getNextObjectNumber("purchaseOrderNo_01", purchaseOrder);
purchaseOrder.setPurchaseOrderNo(purchaseOrderNo);
}		if (StringUtils.isNullBlank(purchaseOrder.getPurchaseOrderId()))
		{	
			_save(purchaseOrder);
		}
		else
		{
			_update(purchaseOrder
,deletedOrderItemsSet
, businessObject);
}
//保存附件业务ID
String purchaseOrderId = purchaseOrder.getPurchaseOrderId();
attachementJdbcDao.update(attachements,purchaseOrderId);	}
	
}