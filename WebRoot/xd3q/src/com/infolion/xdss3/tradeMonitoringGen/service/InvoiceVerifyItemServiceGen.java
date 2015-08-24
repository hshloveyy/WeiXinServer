/*
 * @(#)InvoiceVerifyItemServiceGen.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年07月06日 09点42分49秒
 *  描　述：创建
 */
package com.infolion.xdss3.tradeMonitoringGen.service;

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
import com.infolion.xdss3.tradeMonitoring.domain.InvoiceVerifyItem;
import com.infolion.xdss3.tradeMonitoring.service.InvoiceVerifyItemService;
import com.infolion.xdss3.tradeMonitoring.dao.InvoiceVerifyItemHibernateDao;

/**
 * <pre>
 * 发票校验行项目(InvoiceVerifyItem)服务类
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
public class InvoiceVerifyItemServiceGen extends BaseService
{
	protected Log log = LogFactory.getFactory().getLog(this.getClass());

	@Autowired
	protected InvoiceVerifyItemHibernateDao invoiceVerifyItemHibernateDao;
	
	public void setInvoiceVerifyItemHibernateDao(InvoiceVerifyItemHibernateDao invoiceVerifyItemHibernateDao)
	{
		this.invoiceVerifyItemHibernateDao = invoiceVerifyItemHibernateDao;
	}
	
     /**
	 * 注入扩展服务类
	 */
	 @Autowired(required = false)
	 public void setAdvanceService(@Qualifier("invoiceVerifyItemAdvanceService") AdvanceService advanceService)
	 {
	  	super.setAdvanceService(advanceService);
	 }   
	 
	   
	/**
	 * 根据主键ID,取得发票校验行项目实例
	 * @param id
	 * @return
	 */
	public InvoiceVerifyItem _getDetached(String id)
	{		
	    InvoiceVerifyItem invoiceVerifyItem = new InvoiceVerifyItem();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  invoiceVerifyItem = invoiceVerifyItemHibernateDao.getDetached(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(invoiceVerifyItem);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    invoiceVerifyItem.setOperationType(operationType);
	    
		return invoiceVerifyItem;	
	}
	
	/**
	 * 根据主键ID,取得发票校验行项目实例
	 * @param id
	 * @return
	 */
	public InvoiceVerifyItem _get(String id)
	{		
	    InvoiceVerifyItem invoiceVerifyItem = new InvoiceVerifyItem();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  invoiceVerifyItem = invoiceVerifyItemHibernateDao.get(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(invoiceVerifyItem);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    invoiceVerifyItem.setOperationType(operationType);
	    
		return invoiceVerifyItem;	
	}
	
	/**
	 * 根据主键ID,取得发票校验行项目实例
	 * @param id
	 * @return
	 */
	public InvoiceVerifyItem _getForEdit(String id)
	{		
	    InvoiceVerifyItem invoiceVerifyItem = _get(id);
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = invoiceVerifyItem.getOperationType();
	    if(OperationType.READONLY.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotEdit);
	    }

		return invoiceVerifyItem;	
	}
	
	/**
	 * 根据主键ID,取得发票校验行项目实例副本
	 * @param id
	 * @return
	 */
	public InvoiceVerifyItem _getEntityCopy(String id)
	{		
	    InvoiceVerifyItem invoiceVerifyItem = new InvoiceVerifyItem();
		InvoiceVerifyItem invoiceVerifyItemOld = this._get(id);
		try
		{
			BeanUtils.copyProperties(invoiceVerifyItem, invoiceVerifyItemOld);
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (InvocationTargetException e)
		{
			e.printStackTrace();
		}
		//invoiceVerifyItem.setCode(null); 
		return invoiceVerifyItem;	
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
	 * @param invoiceVerifyItem
	 */
	public void _delete(InvoiceVerifyItem invoiceVerifyItem)
	{
		if (null != advanceService)
			advanceService.preDelete(invoiceVerifyItem);
	
 		LockService.isBoInstanceLocked(invoiceVerifyItem,InvoiceVerifyItem.class);
		invoiceVerifyItemHibernateDao.remove(invoiceVerifyItem);

		if (null != advanceService)
			advanceService.postDelete(invoiceVerifyItem);
	}

	/**
	 * 根据主键删除
	 * 
	 * @param invoiceVerifyItemId
	 */
	public void _delete(String invoiceVerifyItemId,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(invoiceVerifyItemId))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("code"));
		InvoiceVerifyItem invoiceVerifyItem = this.invoiceVerifyItemHibernateDao.load(invoiceVerifyItemId);
		_delete(invoiceVerifyItem);
	}
	
	/**
	 * 删除  
	 *   
	 * @param Set<InvoiceVerifyItem> invoiceVerifyItems
	 */
	public void _deletes(Set<InvoiceVerifyItem> invoiceVerifyItems,BusinessObject businessObject)
	{
		if (null == invoiceVerifyItems || invoiceVerifyItems.size() < 1)
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoText());
		Iterator<InvoiceVerifyItem> it = invoiceVerifyItems.iterator();
		while (it.hasNext())
		{
			InvoiceVerifyItem invoiceVerifyItem = (InvoiceVerifyItem) it.next();
			_delete(invoiceVerifyItem);
		}
	}
	
	/**
	 * 根据多个主键(由，分割)删除多个对象
	 * 
	 * @param invoiceVerifyItemIds
	 */
	public void _deletes(String invoiceVerifyItemIds,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(invoiceVerifyItemIds))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("code"));
		String[] ids = StringUtils.splitString(invoiceVerifyItemIds);
		for (int i = 0; i < ids.length; i++)
		{
			_delete(ids[i],businessObject);
		}
	}
	/**
	 * 提交工作流
	 * 
	 * @param invoiceVerifyItem
	 */
	public void _submitProcess(InvoiceVerifyItem invoiceVerifyItem
	,BusinessObject businessObject)
	{
		String id = invoiceVerifyItem.getCode();
        /**
		if (StringUtils.isNullBlank(id))
		{
			_save(invoiceVerifyItem);
		}
		else
		{
			_update(invoiceVerifyItem
			, businessObject);
		}**/
		
		String taskId = invoiceVerifyItem.getWorkflowTaskId();
		id = invoiceVerifyItem.getCode();
		if (null == taskId || "".equals(taskId))
			WorkflowService.createAndSignalProcessInstance(invoiceVerifyItem, id);
		else
			WorkflowService.signalProcessInstance(invoiceVerifyItem, id, null);
	}

	/**
	 * 保存或更新发票校验行项目
	 * 保存  
	 *  
	 * @param invoiceVerifyItem
	 */
	public void _update(InvoiceVerifyItem invoiceVerifyItem
,BusinessObject businessObject	
	)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(invoiceVerifyItem);
		invoiceVerifyItemHibernateDao.saveOrUpdate(invoiceVerifyItem);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(invoiceVerifyItem);
	}
	
	/**
	 * 保存  
	 *   
	 * @param invoiceVerifyItem
	 */
	public void _save(InvoiceVerifyItem invoiceVerifyItem)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(invoiceVerifyItem);
		// 对id赋空值，使之执行insert操作(适应复制创建场景)
		invoiceVerifyItem.setCode(null);
                                		invoiceVerifyItemHibernateDao.saveOrUpdate(invoiceVerifyItem);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(invoiceVerifyItem);
	}
	
	/**
	 *
	 * 保存  
	 *   
	 * @param invoiceVerifyItem
	 */
	public void _saveOrUpdate(InvoiceVerifyItem invoiceVerifyItem
,BusinessObject businessObject		
	)
	{
		if (StringUtils.isNullBlank(invoiceVerifyItem.getCode()))
		{	
			_save(invoiceVerifyItem);
		}
		else
		{
			_update(invoiceVerifyItem
, businessObject);
}	}
	
}