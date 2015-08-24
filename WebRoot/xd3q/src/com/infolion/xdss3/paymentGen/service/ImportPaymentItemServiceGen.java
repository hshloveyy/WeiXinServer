/*
 * @(#)ImportPaymentItemServiceGen.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年07月25日 09点52分21秒
 *  描　述：创建
 */
package com.infolion.xdss3.paymentGen.service;

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
import com.infolion.xdss3.payment.domain.ImportPaymentItem;
import com.infolion.xdss3.payment.service.ImportPaymentItemService;
import com.infolion.xdss3.payment.dao.ImportPaymentItemHibernateDao;

/**
 * <pre>
 * 付款金额分配(ImportPaymentItem)服务类
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
public class ImportPaymentItemServiceGen extends BaseService
{
	protected Log log = LogFactory.getFactory().getLog(this.getClass());

	@Autowired
	protected ImportPaymentItemHibernateDao importPaymentItemHibernateDao;
	
	public void setImportPaymentItemHibernateDao(ImportPaymentItemHibernateDao importPaymentItemHibernateDao)
	{
		this.importPaymentItemHibernateDao = importPaymentItemHibernateDao;
	}
	
     /**
	 * 注入扩展服务类
	 */
	 @Autowired(required = false)
	 public void setAdvanceService(@Qualifier("importPaymentItemAdvanceService") AdvanceService advanceService)
	 {
	  	super.setAdvanceService(advanceService);
	 }   
	 
	   
	/**
	 * 根据主键ID,取得付款金额分配实例
	 * @param id
	 * @return
	 */
	public ImportPaymentItem _getDetached(String id)
	{		
	    ImportPaymentItem importPaymentItem = new ImportPaymentItem();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  importPaymentItem = importPaymentItemHibernateDao.getDetached(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(importPaymentItem);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    importPaymentItem.setOperationType(operationType);
	    
		return importPaymentItem;	
	}
	
	/**
	 * 根据主键ID,取得付款金额分配实例
	 * @param id
	 * @return
	 */
	public ImportPaymentItem _get(String id)
	{		
	    ImportPaymentItem importPaymentItem = new ImportPaymentItem();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  importPaymentItem = importPaymentItemHibernateDao.get(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(importPaymentItem);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    importPaymentItem.setOperationType(operationType);
	    
		return importPaymentItem;	
	}
	
	/**
	 * 根据主键ID,取得付款金额分配实例
	 * @param id
	 * @return
	 */
	public ImportPaymentItem _getForEdit(String id)
	{		
	    ImportPaymentItem importPaymentItem = _get(id);
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = importPaymentItem.getOperationType();
	    if(OperationType.READONLY.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotEdit);
	    }

		return importPaymentItem;	
	}
	
	/**
	 * 根据主键ID,取得付款金额分配实例副本
	 * @param id
	 * @return
	 */
	public ImportPaymentItem _getEntityCopy(String id)
	{		
	    ImportPaymentItem importPaymentItem = new ImportPaymentItem();
		ImportPaymentItem importPaymentItemOld = this._get(id);
		try
		{
			BeanUtils.copyProperties(importPaymentItem, importPaymentItemOld);
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (InvocationTargetException e)
		{
			e.printStackTrace();
		}
		//importPaymentItem.setPaymentitemid(null); 
		return importPaymentItem;	
	}
	
	/**
	 * 删除  
	 *   
	 * @param importPaymentItem
	 */
	public void _delete(ImportPaymentItem importPaymentItem)
	{
		if (null != advanceService)
			advanceService.preDelete(importPaymentItem);
	
 		LockService.isBoInstanceLocked(importPaymentItem,ImportPaymentItem.class);
		importPaymentItemHibernateDao.remove(importPaymentItem);

		if (null != advanceService)
			advanceService.postDelete(importPaymentItem);
	}

	/**
	 * 根据主键删除
	 * 
	 * @param importPaymentItemId
	 */
	public void _delete(String importPaymentItemId,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(importPaymentItemId))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("paymentitemid"));
		ImportPaymentItem importPaymentItem = this.importPaymentItemHibernateDao.load(importPaymentItemId);
		_delete(importPaymentItem);
	}
	
	/**
	 * 删除  
	 *   
	 * @param Set<ImportPaymentItem> importPaymentItems
	 */
	public void _deletes(Set<ImportPaymentItem> importPaymentItems,BusinessObject businessObject)
	{
		if (null == importPaymentItems || importPaymentItems.size() < 1)
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoText());
		Iterator<ImportPaymentItem> it = importPaymentItems.iterator();
		while (it.hasNext())
		{
			ImportPaymentItem importPaymentItem = (ImportPaymentItem) it.next();
			_delete(importPaymentItem);
		}
	}
	
	/**
	 * 根据多个主键(由，分割)删除多个对象
	 * 
	 * @param importPaymentItemIds
	 */
	public void _deletes(String importPaymentItemIds,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(importPaymentItemIds))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("paymentitemid"));
		String[] ids = StringUtils.splitString(importPaymentItemIds);
		for (int i = 0; i < ids.length; i++)
		{
			_delete(ids[i],businessObject);
		}
	}
	/**
	 * 提交工作流
	 * 
	 * @param importPaymentItem
	 */
	public void _submitProcess(ImportPaymentItem importPaymentItem
	,BusinessObject businessObject)
	{
		String id = importPaymentItem.getPaymentitemid();
        /**
		if (StringUtils.isNullBlank(id))
		{
			_save(importPaymentItem);
		}
		else
		{
			_update(importPaymentItem
			, businessObject);
		}**/
		
		String taskId = importPaymentItem.getWorkflowTaskId();
		id = importPaymentItem.getPaymentitemid();
		if (null == taskId || "".equals(taskId))
			WorkflowService.createAndSignalProcessInstance(importPaymentItem, id);
		else
			WorkflowService.signalProcessInstance(importPaymentItem, id, null);
	}

	/**
	 * 保存或更新付款金额分配
	 * 保存  
	 *  
	 * @param importPaymentItem
	 */
	public void _update(ImportPaymentItem importPaymentItem
,BusinessObject businessObject	
	)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(importPaymentItem);
		importPaymentItemHibernateDao.saveOrUpdate(importPaymentItem);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(importPaymentItem);
	}
	
	/**
	 * 保存  
	 *   
	 * @param importPaymentItem
	 */
	public void _save(ImportPaymentItem importPaymentItem)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(importPaymentItem);
		// 对id赋空值，使之执行insert操作(适应复制创建场景)
		importPaymentItem.setPaymentitemid(null);
                            		importPaymentItemHibernateDao.saveOrUpdate(importPaymentItem);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(importPaymentItem);
	}
	
	/**
	 *
	 * 保存  
	 *   
	 * @param importPaymentItem
	 */
	public void _saveOrUpdate(ImportPaymentItem importPaymentItem
,BusinessObject businessObject		
	)
	{
		if (StringUtils.isNullBlank(importPaymentItem.getPaymentitemid()))
		{	
			_save(importPaymentItem);
		}
		else
		{
			_update(importPaymentItem
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