/*
 * @(#)InvoiceVerifyServiceGen.java
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
import com.infolion.xdss3.tradeMonitoring.domain.InvoiceVerify;
import com.infolion.xdss3.tradeMonitoring.service.InvoiceVerifyService;
import com.infolion.xdss3.tradeMonitoring.dao.InvoiceVerifyHibernateDao;
          
import com.infolion.xdss3.tradeMonitoring.domain.InvoiceVerifyItem;
import com.infolion.xdss3.tradeMonitoring.service.InvoiceVerifyItemService;

/**
 * <pre>
 * 发票校验(InvoiceVerify)服务类
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
public class InvoiceVerifyServiceGen extends BaseService
{
	protected Log log = LogFactory.getFactory().getLog(this.getClass());

	@Autowired
	protected InvoiceVerifyHibernateDao invoiceVerifyHibernateDao;
	
	public InvoiceVerifyHibernateDao getInvoiceVerifyHibernateDao() {
		return invoiceVerifyHibernateDao;
	}

	public InvoiceVerifyItemService getInvoiceVerifyItemService() {
		return invoiceVerifyItemService;
	}

	public void setInvoiceVerifyHibernateDao(InvoiceVerifyHibernateDao invoiceVerifyHibernateDao)
	{
		this.invoiceVerifyHibernateDao = invoiceVerifyHibernateDao;
	}
	
     /**
	 * 注入扩展服务类
	 */
	 @Autowired(required = false)
	 public void setAdvanceService(@Qualifier("invoiceVerifyAdvanceService") AdvanceService advanceService)
	 {
	  	super.setAdvanceService(advanceService);
	 }   
	 
          

	@Autowired
	protected InvoiceVerifyItemService invoiceVerifyItemService;
	
	public void setInvoiceVerifyItemService(InvoiceVerifyItemService invoiceVerifyItemService)
	{
		this.invoiceVerifyItemService = invoiceVerifyItemService;
	}

          

	   
	/**
	 * 根据主键ID,取得发票校验实例
	 * @param id
	 * @return
	 */
	public InvoiceVerify _getDetached(String id)
	{		
	    InvoiceVerify invoiceVerify = new InvoiceVerify();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  invoiceVerify = invoiceVerifyHibernateDao.getDetached(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(invoiceVerify);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    invoiceVerify.setOperationType(operationType);
	    
		return invoiceVerify;	
	}
	
	/**
	 * 根据主键ID,取得发票校验实例
	 * @param id
	 * @return
	 */
	public InvoiceVerify _get(String id)
	{		
	    InvoiceVerify invoiceVerify = new InvoiceVerify();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  invoiceVerify = invoiceVerifyHibernateDao.get(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(invoiceVerify);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    invoiceVerify.setOperationType(operationType);
	    
		return invoiceVerify;	
	}
	
	/**
	 * 根据主键ID,取得发票校验实例
	 * @param id
	 * @return
	 */
	public InvoiceVerify _getForEdit(String id)
	{		
	    InvoiceVerify invoiceVerify = _get(id);
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = invoiceVerify.getOperationType();
	    if(OperationType.READONLY.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotEdit);
	    }

		return invoiceVerify;	
	}
	
	/**
	 * 根据主键ID,取得发票校验实例副本
	 * @param id
	 * @return
	 */
	public InvoiceVerify _getEntityCopy(String id)
	{		
	    InvoiceVerify invoiceVerify = new InvoiceVerify();
		InvoiceVerify invoiceVerifyOld = this._get(id);
		try
		{
			BeanUtils.copyProperties(invoiceVerify, invoiceVerifyOld);
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (InvocationTargetException e)
		{
			e.printStackTrace();
		}
		//invoiceVerify.setMaincode(null); 
		return invoiceVerify;	
	}
	
	/**
	 * 删除  
	 *   
	 * @param invoiceVerify
	 */
	public void _delete(InvoiceVerify invoiceVerify)
	{
		if (null != advanceService)
			advanceService.preDelete(invoiceVerify);
	
 		LockService.isBoInstanceLocked(invoiceVerify,InvoiceVerify.class);
		invoiceVerifyHibernateDao.remove(invoiceVerify);

		if (null != advanceService)
			advanceService.postDelete(invoiceVerify);
	}

	/**
	 * 根据主键删除
	 * 
	 * @param invoiceVerifyId
	 */
	public void _delete(String invoiceVerifyId,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(invoiceVerifyId))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("maincode"));
		InvoiceVerify invoiceVerify = this.invoiceVerifyHibernateDao.load(invoiceVerifyId);
		_delete(invoiceVerify);
	}
	
	/**
	 * 删除  
	 *   
	 * @param Set<InvoiceVerify> invoiceVerifys
	 */
	public void _deletes(Set<InvoiceVerify> invoiceVerifys,BusinessObject businessObject)
	{
		if (null == invoiceVerifys || invoiceVerifys.size() < 1)
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoText());
		Iterator<InvoiceVerify> it = invoiceVerifys.iterator();
		while (it.hasNext())
		{
			InvoiceVerify invoiceVerify = (InvoiceVerify) it.next();
			_delete(invoiceVerify);
		}
	}
	
	/**
	 * 根据多个主键(由，分割)删除多个对象
	 * 
	 * @param invoiceVerifyIds
	 */
	public void _deletes(String invoiceVerifyIds,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(invoiceVerifyIds))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("maincode"));
		String[] ids = StringUtils.splitString(invoiceVerifyIds);
		for (int i = 0; i < ids.length; i++)
		{
			_delete(ids[i],businessObject);
		}
	}
	/**
	 * 提交工作流
	 * 
	 * @param invoiceVerify
	 */
	public void _submitProcess(InvoiceVerify invoiceVerify
,Set<InvoiceVerifyItem> deletedInvoiceVerifyItemSet	,BusinessObject businessObject)
	{
		String id = invoiceVerify.getMaincode();
        /**
		if (StringUtils.isNullBlank(id))
		{
			_save(invoiceVerify);
		}
		else
		{
			_update(invoiceVerify
,deletedInvoiceVerifyItemSet			, businessObject);
		}**/
		
		String taskId = invoiceVerify.getWorkflowTaskId();
		id = invoiceVerify.getMaincode();
		if (null == taskId || "".equals(taskId))
			WorkflowService.createAndSignalProcessInstance(invoiceVerify, id);
		else
			WorkflowService.signalProcessInstance(invoiceVerify, id, null);
	}

	/**
	 * 保存或更新发票校验
	 * 保存  
	 *  
	 * @param invoiceVerify
	 */
	public void _update(InvoiceVerify invoiceVerify
,Set<InvoiceVerifyItem> deletedInvoiceVerifyItemSet,BusinessObject businessObject	
	)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(invoiceVerify);
		invoiceVerifyHibernateDao.saveOrUpdate(invoiceVerify);
// 删除关联子业务对象数据
if(deletedInvoiceVerifyItemSet!=null && deletedInvoiceVerifyItemSet.size()>0)
{
invoiceVerifyItemService._deletes(deletedInvoiceVerifyItemSet,businessObject);
}		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(invoiceVerify);
	}
	
	/**
	 * 保存  
	 *   
	 * @param invoiceVerify
	 */
	public void _save(InvoiceVerify invoiceVerify)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(invoiceVerify);
		// 对id赋空值，使之执行insert操作(适应复制创建场景)
		invoiceVerify.setMaincode(null);
       
       
       
       
       
       
       
       
       
       
       
       
       
       
       
       
       
       
       
		Set<InvoiceVerifyItem> invoiceVerifyItemSet = invoiceVerify.getInvoiceVerifyItem();
		Set<InvoiceVerifyItem> newInvoiceVerifyItemSet = null;
		if (null != invoiceVerifyItemSet)
		{
			newInvoiceVerifyItemSet = new HashSet();
			Iterator<InvoiceVerifyItem> itInvoiceVerifyItem = invoiceVerifyItemSet.iterator();
			while (itInvoiceVerifyItem.hasNext())
			{
				InvoiceVerifyItem invoiceVerifyItem = (InvoiceVerifyItem) itInvoiceVerifyItem.next();
				invoiceVerifyItem.setCode(null);
				newInvoiceVerifyItemSet.add(invoiceVerifyItem);
			}
		}
		invoiceVerify.setInvoiceVerifyItem(newInvoiceVerifyItemSet);
		invoiceVerifyHibernateDao.saveOrUpdate(invoiceVerify);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(invoiceVerify);
	}
	
	/**
	 *
	 * 保存  
	 *   
	 * @param invoiceVerify
	 */
	public void _saveOrUpdate(InvoiceVerify invoiceVerify
,Set<InvoiceVerifyItem> deletedInvoiceVerifyItemSet,BusinessObject businessObject		
	)
	{
		if (StringUtils.isNullBlank(invoiceVerify.getMaincode()))
		{	
			_save(invoiceVerify);
		}
		else
		{
			_update(invoiceVerify
,deletedInvoiceVerifyItemSet
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