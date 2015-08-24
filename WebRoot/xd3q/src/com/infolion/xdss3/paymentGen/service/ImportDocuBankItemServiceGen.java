/*
 * @(#)ImportDocuBankItemServiceGen.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年07月25日 09点52分23秒
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
import com.infolion.xdss3.payment.domain.ImportDocuBankItem;
import com.infolion.xdss3.payment.service.ImportDocuBankItemService;
import com.infolion.xdss3.payment.dao.ImportDocuBankItemHibernateDao;

/**
 * <pre>
 * 押汇/海外代付银行(ImportDocuBankItem)服务类
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
public class ImportDocuBankItemServiceGen extends BaseService
{
	protected Log log = LogFactory.getFactory().getLog(this.getClass());

	@Autowired
	protected ImportDocuBankItemHibernateDao importDocuBankItemHibernateDao;
	
	public void setImportDocuBankItemHibernateDao(ImportDocuBankItemHibernateDao importDocuBankItemHibernateDao)
	{
		this.importDocuBankItemHibernateDao = importDocuBankItemHibernateDao;
	}
	
     /**
	 * 注入扩展服务类
	 */
	 @Autowired(required = false)
	 public void setAdvanceService(@Qualifier("importDocuBankItemAdvanceService") AdvanceService advanceService)
	 {
	  	super.setAdvanceService(advanceService);
	 }   
	 
	   
	/**
	 * 根据主键ID,取得押汇/海外代付银行实例
	 * @param id
	 * @return
	 */
	public ImportDocuBankItem _getDetached(String id)
	{		
	    ImportDocuBankItem importDocuBankItem = new ImportDocuBankItem();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  importDocuBankItem = importDocuBankItemHibernateDao.getDetached(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(importDocuBankItem);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    importDocuBankItem.setOperationType(operationType);
	    
		return importDocuBankItem;	
	}
	
	/**
	 * 根据主键ID,取得押汇/海外代付银行实例
	 * @param id
	 * @return
	 */
	public ImportDocuBankItem _get(String id)
	{		
	    ImportDocuBankItem importDocuBankItem = new ImportDocuBankItem();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  importDocuBankItem = importDocuBankItemHibernateDao.get(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(importDocuBankItem);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    importDocuBankItem.setOperationType(operationType);
	    
		return importDocuBankItem;	
	}
	
	/**
	 * 根据主键ID,取得押汇/海外代付银行实例
	 * @param id
	 * @return
	 */
	public ImportDocuBankItem _getForEdit(String id)
	{		
	    ImportDocuBankItem importDocuBankItem = _get(id);
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = importDocuBankItem.getOperationType();
	    if(OperationType.READONLY.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotEdit);
	    }

		return importDocuBankItem;	
	}
	
	/**
	 * 根据主键ID,取得押汇/海外代付银行实例副本
	 * @param id
	 * @return
	 */
	public ImportDocuBankItem _getEntityCopy(String id)
	{		
	    ImportDocuBankItem importDocuBankItem = new ImportDocuBankItem();
		ImportDocuBankItem importDocuBankItemOld = this._get(id);
		try
		{
			BeanUtils.copyProperties(importDocuBankItem, importDocuBankItemOld);
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (InvocationTargetException e)
		{
			e.printStackTrace();
		}
		//importDocuBankItem.setDocuaryitemid(null); 
		return importDocuBankItem;	
	}
	
	/**
	 * 删除  
	 *   
	 * @param importDocuBankItem
	 */
	public void _delete(ImportDocuBankItem importDocuBankItem)
	{
		if (null != advanceService)
			advanceService.preDelete(importDocuBankItem);
	
 		LockService.isBoInstanceLocked(importDocuBankItem,ImportDocuBankItem.class);
		importDocuBankItemHibernateDao.remove(importDocuBankItem);

		if (null != advanceService)
			advanceService.postDelete(importDocuBankItem);
	}

	/**
	 * 根据主键删除
	 * 
	 * @param importDocuBankItemId
	 */
	public void _delete(String importDocuBankItemId,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(importDocuBankItemId))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("docuaryitemid"));
		ImportDocuBankItem importDocuBankItem = this.importDocuBankItemHibernateDao.load(importDocuBankItemId);
		_delete(importDocuBankItem);
	}
	
	/**
	 * 删除  
	 *   
	 * @param Set<ImportDocuBankItem> importDocuBankItems
	 */
	public void _deletes(Set<ImportDocuBankItem> importDocuBankItems,BusinessObject businessObject)
	{
		if (null == importDocuBankItems || importDocuBankItems.size() < 1)
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoText());
		Iterator<ImportDocuBankItem> it = importDocuBankItems.iterator();
		while (it.hasNext())
		{
			ImportDocuBankItem importDocuBankItem = (ImportDocuBankItem) it.next();
			_delete(importDocuBankItem);
		}
	}
	
	/**
	 * 根据多个主键(由，分割)删除多个对象
	 * 
	 * @param importDocuBankItemIds
	 */
	public void _deletes(String importDocuBankItemIds,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(importDocuBankItemIds))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("docuaryitemid"));
		String[] ids = StringUtils.splitString(importDocuBankItemIds);
		for (int i = 0; i < ids.length; i++)
		{
			_delete(ids[i],businessObject);
		}
	}
	/**
	 * 提交工作流
	 * 
	 * @param importDocuBankItem
	 */
	public void _submitProcess(ImportDocuBankItem importDocuBankItem
	,BusinessObject businessObject)
	{
		String id = importDocuBankItem.getDocuaryitemid();
        /**
		if (StringUtils.isNullBlank(id))
		{
			_save(importDocuBankItem);
		}
		else
		{
			_update(importDocuBankItem
			, businessObject);
		}**/
		
		String taskId = importDocuBankItem.getWorkflowTaskId();
		id = importDocuBankItem.getDocuaryitemid();
		if (null == taskId || "".equals(taskId))
			WorkflowService.createAndSignalProcessInstance(importDocuBankItem, id);
		else
			WorkflowService.signalProcessInstance(importDocuBankItem, id, null);
	}

	/**
	 * 保存或更新押汇/海外代付银行
	 * 保存  
	 *  
	 * @param importDocuBankItem
	 */
	public void _update(ImportDocuBankItem importDocuBankItem
,BusinessObject businessObject	
	)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(importDocuBankItem);
		importDocuBankItemHibernateDao.saveOrUpdate(importDocuBankItem);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(importDocuBankItem);
	}
	
	/**
	 * 保存  
	 *   
	 * @param importDocuBankItem
	 */
	public void _save(ImportDocuBankItem importDocuBankItem)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(importDocuBankItem);
		// 对id赋空值，使之执行insert操作(适应复制创建场景)
		importDocuBankItem.setDocuaryitemid(null);
                    		importDocuBankItemHibernateDao.saveOrUpdate(importDocuBankItem);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(importDocuBankItem);
	}
	
	/**
	 *
	 * 保存  
	 *   
	 * @param importDocuBankItem
	 */
	public void _saveOrUpdate(ImportDocuBankItem importDocuBankItem
,BusinessObject businessObject		
	)
	{
		if (StringUtils.isNullBlank(importDocuBankItem.getDocuaryitemid()))
		{	
			_save(importDocuBankItem);
		}
		else
		{
			_update(importDocuBankItem
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