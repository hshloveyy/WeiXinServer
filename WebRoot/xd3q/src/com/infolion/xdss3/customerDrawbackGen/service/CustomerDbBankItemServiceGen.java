/*
 * @(#)CustomerDbBankItemServiceGen.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年07月07日 17点57分47秒
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
import com.infolion.xdss3.customerDrawback.domain.CustomerDbBankItem;
import com.infolion.xdss3.customerDrawback.service.CustomerDbBankItemService;
import com.infolion.xdss3.customerDrawback.dao.CustomerDbBankItemHibernateDao;

/**
 * <pre>
 * 客户退款银行(CustomerDbBankItem)服务类
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
public class CustomerDbBankItemServiceGen extends BaseService
{
	protected Log log = LogFactory.getFactory().getLog(this.getClass());

	@Autowired
	protected CustomerDbBankItemHibernateDao customerDbBankItemHibernateDao;
	
	public void setCustomerDbBankItemHibernateDao(CustomerDbBankItemHibernateDao customerDbBankItemHibernateDao)
	{
		this.customerDbBankItemHibernateDao = customerDbBankItemHibernateDao;
	}
	
     /**
	 * 注入扩展服务类
	 */
	 @Autowired(required = false)
	 public void setAdvanceService(@Qualifier("customerDbBankItemAdvanceService") AdvanceService advanceService)
	 {
	  	super.setAdvanceService(advanceService);
	 }   
	 
	   
	/**
	 * 根据主键ID,取得客户退款银行实例
	 * @param id
	 * @return
	 */
	public CustomerDbBankItem _getDetached(String id)
	{		
	    CustomerDbBankItem customerDbBankItem = new CustomerDbBankItem();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  customerDbBankItem = customerDbBankItemHibernateDao.getDetached(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(customerDbBankItem);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    customerDbBankItem.setOperationType(operationType);
	    
		return customerDbBankItem;	
	}
	
	/**
	 * 根据主键ID,取得客户退款银行实例
	 * @param id
	 * @return
	 */
	public CustomerDbBankItem _get(String id)
	{		
	    CustomerDbBankItem customerDbBankItem = new CustomerDbBankItem();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  customerDbBankItem = customerDbBankItemHibernateDao.get(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(customerDbBankItem);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    customerDbBankItem.setOperationType(operationType);
	    
		return customerDbBankItem;	
	}
	
	/**
	 * 根据主键ID,取得客户退款银行实例
	 * @param id
	 * @return
	 */
	public CustomerDbBankItem _getForEdit(String id)
	{		
	    CustomerDbBankItem customerDbBankItem = _get(id);
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = customerDbBankItem.getOperationType();
	    if(OperationType.READONLY.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotEdit);
	    }

		return customerDbBankItem;	
	}
	
	/**
	 * 根据主键ID,取得客户退款银行实例副本
	 * @param id
	 * @return
	 */
	public CustomerDbBankItem _getEntityCopy(String id)
	{		
	    CustomerDbBankItem customerDbBankItem = new CustomerDbBankItem();
		CustomerDbBankItem customerDbBankItemOld = this._get(id);
		try
		{
			BeanUtils.copyProperties(customerDbBankItem, customerDbBankItemOld);
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (InvocationTargetException e)
		{
			e.printStackTrace();
		}
		//customerDbBankItem.setRefundbankitemid(null); 
		return customerDbBankItem;	
	}
	
	/**
	 * 删除  
	 *   
	 * @param customerDbBankItem
	 */
	public void _delete(CustomerDbBankItem customerDbBankItem)
	{
		if (null != advanceService)
			advanceService.preDelete(customerDbBankItem);
	
 		LockService.isBoInstanceLocked(customerDbBankItem,CustomerDbBankItem.class);
		customerDbBankItemHibernateDao.remove(customerDbBankItem);

		if (null != advanceService)
			advanceService.postDelete(customerDbBankItem);
	}

	/**
	 * 根据主键删除
	 * 
	 * @param customerDbBankItemId
	 */
	public void _delete(String customerDbBankItemId,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(customerDbBankItemId))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("refundbankitemid"));
		CustomerDbBankItem customerDbBankItem = this.customerDbBankItemHibernateDao.load(customerDbBankItemId);
		_delete(customerDbBankItem);
	}
	
	/**
	 * 删除  
	 *   
	 * @param Set<CustomerDbBankItem> customerDbBankItems
	 */
	public void _deletes(Set<CustomerDbBankItem> customerDbBankItems,BusinessObject businessObject)
	{
		if (null == customerDbBankItems || customerDbBankItems.size() < 1)
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoText());
		Iterator<CustomerDbBankItem> it = customerDbBankItems.iterator();
		while (it.hasNext())
		{
			CustomerDbBankItem customerDbBankItem = (CustomerDbBankItem) it.next();
			_delete(customerDbBankItem);
		}
	}
	
	/**
	 * 根据多个主键(由，分割)删除多个对象
	 * 
	 * @param customerDbBankItemIds
	 */
	public void _deletes(String customerDbBankItemIds,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(customerDbBankItemIds))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("refundbankitemid"));
		String[] ids = StringUtils.splitString(customerDbBankItemIds);
		for (int i = 0; i < ids.length; i++)
		{
			_delete(ids[i],businessObject);
		}
	}
	/**
	 * 提交工作流
	 * 
	 * @param customerDbBankItem
	 */
	public void _submitProcess(CustomerDbBankItem customerDbBankItem
	,BusinessObject businessObject)
	{
		String id = customerDbBankItem.getRefundbankitemid();
        /**
		if (StringUtils.isNullBlank(id))
		{
			_save(customerDbBankItem);
		}
		else
		{
			_update(customerDbBankItem
			, businessObject);
		}**/
		
		String taskId = customerDbBankItem.getWorkflowTaskId();
		id = customerDbBankItem.getRefundbankitemid();
		if (null == taskId || "".equals(taskId))
			WorkflowService.createAndSignalProcessInstance(customerDbBankItem, id);
		else
			WorkflowService.signalProcessInstance(customerDbBankItem, id, null);
	}

	/**
	 * 保存或更新客户退款银行
	 * 保存  
	 *  
	 * @param customerDbBankItem
	 */
	public void _update(CustomerDbBankItem customerDbBankItem
,BusinessObject businessObject	
	)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(customerDbBankItem);
		customerDbBankItemHibernateDao.saveOrUpdate(customerDbBankItem);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(customerDbBankItem);
	}
	
	/**
	 * 保存  
	 *   
	 * @param customerDbBankItem
	 */
	public void _save(CustomerDbBankItem customerDbBankItem)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(customerDbBankItem);
		// 对id赋空值，使之执行insert操作(适应复制创建场景)
		customerDbBankItem.setRefundbankitemid(null);
                      		customerDbBankItemHibernateDao.saveOrUpdate(customerDbBankItem);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(customerDbBankItem);
	}
	
	/**
	 *
	 * 保存  
	 *   
	 * @param customerDbBankItem
	 */
	public void _saveOrUpdate(CustomerDbBankItem customerDbBankItem
,BusinessObject businessObject		
	)
	{
		if (StringUtils.isNullBlank(customerDbBankItem.getRefundbankitemid()))
		{	
			_save(customerDbBankItem);
		}
		else
		{
			_update(customerDbBankItem
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