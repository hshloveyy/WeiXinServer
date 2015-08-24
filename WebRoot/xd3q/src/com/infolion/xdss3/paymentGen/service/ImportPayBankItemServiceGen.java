/*
 * @(#)ImportPayBankItemServiceGen.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年07月25日 09点52分22秒
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
import com.infolion.xdss3.payment.domain.ImportPayBankItem;
import com.infolion.xdss3.payment.service.ImportPayBankItemService;
import com.infolion.xdss3.payment.dao.ImportPayBankItemHibernateDao;

/**
 * <pre>
 * 付款银行(ImportPayBankItem)服务类
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
public class ImportPayBankItemServiceGen extends BaseService
{
	protected Log log = LogFactory.getFactory().getLog(this.getClass());

	@Autowired
	protected ImportPayBankItemHibernateDao importPayBankItemHibernateDao;
	
	public void setImportPayBankItemHibernateDao(ImportPayBankItemHibernateDao importPayBankItemHibernateDao)
	{
		this.importPayBankItemHibernateDao = importPayBankItemHibernateDao;
	}
	
     /**
	 * 注入扩展服务类
	 */
	 @Autowired(required = false)
	 public void setAdvanceService(@Qualifier("importPayBankItemAdvanceService") AdvanceService advanceService)
	 {
	  	super.setAdvanceService(advanceService);
	 }   
	 
	   
	/**
	 * 根据主键ID,取得付款银行实例
	 * @param id
	 * @return
	 */
	public ImportPayBankItem _getDetached(String id)
	{		
	    ImportPayBankItem importPayBankItem = new ImportPayBankItem();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  importPayBankItem = importPayBankItemHibernateDao.getDetached(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(importPayBankItem);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    importPayBankItem.setOperationType(operationType);
	    
		return importPayBankItem;	
	}
	
	/**
	 * 根据主键ID,取得付款银行实例
	 * @param id
	 * @return
	 */
	public ImportPayBankItem _get(String id)
	{		
	    ImportPayBankItem importPayBankItem = new ImportPayBankItem();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  importPayBankItem = importPayBankItemHibernateDao.get(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(importPayBankItem);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    importPayBankItem.setOperationType(operationType);
	    
		return importPayBankItem;	
	}
	
	/**
	 * 根据主键ID,取得付款银行实例
	 * @param id
	 * @return
	 */
	public ImportPayBankItem _getForEdit(String id)
	{		
	    ImportPayBankItem importPayBankItem = _get(id);
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = importPayBankItem.getOperationType();
	    if(OperationType.READONLY.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotEdit);
	    }

		return importPayBankItem;	
	}
	
	/**
	 * 根据主键ID,取得付款银行实例副本
	 * @param id
	 * @return
	 */
	public ImportPayBankItem _getEntityCopy(String id)
	{		
	    ImportPayBankItem importPayBankItem = new ImportPayBankItem();
		ImportPayBankItem importPayBankItemOld = this._get(id);
		try
		{
			BeanUtils.copyProperties(importPayBankItem, importPayBankItemOld);
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (InvocationTargetException e)
		{
			e.printStackTrace();
		}
		//importPayBankItem.setPaybankitemid(null); 
		return importPayBankItem;	
	}
	
	/**
	 * 删除  
	 *   
	 * @param importPayBankItem
	 */
	public void _delete(ImportPayBankItem importPayBankItem)
	{
		if (null != advanceService)
			advanceService.preDelete(importPayBankItem);
	
 		LockService.isBoInstanceLocked(importPayBankItem,ImportPayBankItem.class);
		importPayBankItemHibernateDao.remove(importPayBankItem);

		if (null != advanceService)
			advanceService.postDelete(importPayBankItem);
	}

	/**
	 * 根据主键删除
	 * 
	 * @param importPayBankItemId
	 */
	public void _delete(String importPayBankItemId,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(importPayBankItemId))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("paybankitemid"));
		ImportPayBankItem importPayBankItem = this.importPayBankItemHibernateDao.load(importPayBankItemId);
		_delete(importPayBankItem);
	}
	
	/**
	 * 删除  
	 *   
	 * @param Set<ImportPayBankItem> importPayBankItems
	 */
	public void _deletes(Set<ImportPayBankItem> importPayBankItems,BusinessObject businessObject)
	{
		if (null == importPayBankItems || importPayBankItems.size() < 1)
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoText());
		Iterator<ImportPayBankItem> it = importPayBankItems.iterator();
		while (it.hasNext())
		{
			ImportPayBankItem importPayBankItem = (ImportPayBankItem) it.next();
			_delete(importPayBankItem);
		}
	}
	
	/**
	 * 根据多个主键(由，分割)删除多个对象
	 * 
	 * @param importPayBankItemIds
	 */
	public void _deletes(String importPayBankItemIds,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(importPayBankItemIds))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("paybankitemid"));
		String[] ids = StringUtils.splitString(importPayBankItemIds);
		for (int i = 0; i < ids.length; i++)
		{
			_delete(ids[i],businessObject);
		}
	}
	/**
	 * 提交工作流
	 * 
	 * @param importPayBankItem
	 */
	public void _submitProcess(ImportPayBankItem importPayBankItem
	,BusinessObject businessObject)
	{
		String id = importPayBankItem.getPaybankitemid();
        /**
		if (StringUtils.isNullBlank(id))
		{
			_save(importPayBankItem);
		}
		else
		{
			_update(importPayBankItem
			, businessObject);
		}**/
		
		String taskId = importPayBankItem.getWorkflowTaskId();
		id = importPayBankItem.getPaybankitemid();
		if (null == taskId || "".equals(taskId))
			WorkflowService.createAndSignalProcessInstance(importPayBankItem, id);
		else
			WorkflowService.signalProcessInstance(importPayBankItem, id, null);
	}

	/**
	 * 保存或更新付款银行
	 * 保存  
	 *  
	 * @param importPayBankItem
	 */
	public void _update(ImportPayBankItem importPayBankItem
,BusinessObject businessObject	
	)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(importPayBankItem);
		importPayBankItemHibernateDao.saveOrUpdate(importPayBankItem);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(importPayBankItem);
	}
	
	/**
	 * 保存  
	 *   
	 * @param importPayBankItem
	 */
	public void _save(ImportPayBankItem importPayBankItem)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(importPayBankItem);
		// 对id赋空值，使之执行insert操作(适应复制创建场景)
		importPayBankItem.setPaybankitemid(null);
                    		importPayBankItemHibernateDao.saveOrUpdate(importPayBankItem);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(importPayBankItem);
	}
	
	/**
	 *
	 * 保存  
	 *   
	 * @param importPayBankItem
	 */
	public void _saveOrUpdate(ImportPayBankItem importPayBankItem
,BusinessObject businessObject		
	)
	{
		if (StringUtils.isNullBlank(importPayBankItem.getPaybankitemid()))
		{	
			_save(importPayBankItem);
		}
		else
		{
			_update(importPayBankItem
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