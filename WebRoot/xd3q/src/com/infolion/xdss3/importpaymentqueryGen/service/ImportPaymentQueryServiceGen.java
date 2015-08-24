/*
 * @(#)ImportPaymentQueryServiceGen.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年10月01日 05点46分26秒
 *  描　述：创建
 */
package com.infolion.xdss3.importpaymentqueryGen.service;

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
import com.infolion.xdss3.importpaymentquery.domain.ImportPaymentQuery;
import com.infolion.xdss3.importpaymentquery.service.ImportPaymentQueryService;
import com.infolion.xdss3.importpaymentquery.dao.ImportPaymentQueryHibernateDao;

/**
 * <pre>
 * 进口付款查询(ImportPaymentQuery)服务类
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
public class ImportPaymentQueryServiceGen extends BaseService
{
	protected Log log = LogFactory.getFactory().getLog(this.getClass());

	@Autowired
	protected ImportPaymentQueryHibernateDao importPaymentQueryHibernateDao;
	
	public void setImportPaymentQueryHibernateDao(ImportPaymentQueryHibernateDao importPaymentQueryHibernateDao)
	{
		this.importPaymentQueryHibernateDao = importPaymentQueryHibernateDao;
	}
	
     /**
	 * 注入扩展服务类
	 */
	 @Autowired(required = false)
	 public void setAdvanceService(@Qualifier("importPaymentQueryAdvanceService") AdvanceService advanceService)
	 {
	  	super.setAdvanceService(advanceService);
	 }   
	 
	   
	/**
	 * 根据主键ID,取得进口付款查询实例
	 * @param id
	 * @return
	 */
	public ImportPaymentQuery _getDetached(String id)
	{		
	    ImportPaymentQuery importPaymentQuery = new ImportPaymentQuery();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  importPaymentQuery = importPaymentQueryHibernateDao.getDetached(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(importPaymentQuery);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    importPaymentQuery.setOperationType(operationType);
	    
		return importPaymentQuery;	
	}
	
	/**
	 * 根据主键ID,取得进口付款查询实例
	 * @param id
	 * @return
	 */
	public ImportPaymentQuery _get(String id)
	{		
	    ImportPaymentQuery importPaymentQuery = new ImportPaymentQuery();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  importPaymentQuery = importPaymentQueryHibernateDao.get(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(importPaymentQuery);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    importPaymentQuery.setOperationType(operationType);
	    
		return importPaymentQuery;	
	}
	
	/**
	 * 根据主键ID,取得进口付款查询实例
	 * @param id
	 * @return
	 */
	public ImportPaymentQuery _getForEdit(String id)
	{		
	    ImportPaymentQuery importPaymentQuery = _get(id);
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = importPaymentQuery.getOperationType();
	    if(OperationType.READONLY.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotEdit);
	    }

		return importPaymentQuery;	
	}
	
	/**
	 * 根据主键ID,取得进口付款查询实例副本
	 * @param id
	 * @return
	 */
	public ImportPaymentQuery _getEntityCopy(String id)
	{		
	    ImportPaymentQuery importPaymentQuery = new ImportPaymentQuery();
		ImportPaymentQuery importPaymentQueryOld = this._get(id);
		try
		{
			BeanUtils.copyProperties(importPaymentQuery, importPaymentQueryOld);
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (InvocationTargetException e)
		{
			e.printStackTrace();
		}
		//importPaymentQuery.setPaymentid(null); 
		importPaymentQuery.setProcessstate(" ");
		return importPaymentQuery;	
	}
	
	/**
	 * 删除  
	 *   
	 * @param importPaymentQuery
	 */
	public void _delete(ImportPaymentQuery importPaymentQuery)
	{
		if (null != advanceService)
			advanceService.preDelete(importPaymentQuery);
	
		//流程状态
		String processState =importPaymentQuery.getProcessstate();
		if(!StringUtils.isNullBlank(processState))
		{
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.CannotDeleteApproveData);
		}
 		LockService.isBoInstanceLocked(importPaymentQuery,ImportPaymentQuery.class);
		importPaymentQueryHibernateDao.remove(importPaymentQuery);

		if (null != advanceService)
			advanceService.postDelete(importPaymentQuery);
	}

	/**
	 * 根据主键删除
	 * 
	 * @param importPaymentQueryId
	 */
	public void _delete(String importPaymentQueryId,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(importPaymentQueryId))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("paymentid"));
		ImportPaymentQuery importPaymentQuery = this.importPaymentQueryHibernateDao.load(importPaymentQueryId);
		_delete(importPaymentQuery);
	}
	
	/**
	 * 删除  
	 *   
	 * @param Set<ImportPaymentQuery> importPaymentQuerys
	 */
	public void _deletes(Set<ImportPaymentQuery> importPaymentQuerys,BusinessObject businessObject)
	{
		if (null == importPaymentQuerys || importPaymentQuerys.size() < 1)
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoText());
		Iterator<ImportPaymentQuery> it = importPaymentQuerys.iterator();
		while (it.hasNext())
		{
			ImportPaymentQuery importPaymentQuery = (ImportPaymentQuery) it.next();
			_delete(importPaymentQuery);
		}
	}
	
	/**
	 * 根据多个主键(由，分割)删除多个对象
	 * 
	 * @param importPaymentQueryIds
	 */
	public void _deletes(String importPaymentQueryIds,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(importPaymentQueryIds))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("paymentid"));
		String[] ids = StringUtils.splitString(importPaymentQueryIds);
		for (int i = 0; i < ids.length; i++)
		{
			_delete(ids[i],businessObject);
		}
	}
	/**
	 * 提交工作流
	 * 
	 * @param importPaymentQuery
	 */
	public void _submitProcess(ImportPaymentQuery importPaymentQuery
	,BusinessObject businessObject)
	{
		String id = importPaymentQuery.getPaymentid();
        /**
		if (StringUtils.isNullBlank(id))
		{
			_save(importPaymentQuery);
		}
		else
		{
			_update(importPaymentQuery
			, businessObject);
		}**/
		
		String taskId = importPaymentQuery.getWorkflowTaskId();
		id = importPaymentQuery.getPaymentid();
		if (null == taskId || "".equals(taskId))
			WorkflowService.createAndSignalProcessInstance(importPaymentQuery, id);
		else
			WorkflowService.signalProcessInstance(importPaymentQuery, id, null);
	}

	/**
	 * 保存或更新进口付款查询
	 * 保存  
	 *  
	 * @param importPaymentQuery
	 */
	public void _update(ImportPaymentQuery importPaymentQuery
,BusinessObject businessObject	
	)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(importPaymentQuery);
		importPaymentQueryHibernateDao.saveOrUpdate(importPaymentQuery);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(importPaymentQuery);
	}
	
	/**
	 * 保存  
	 *   
	 * @param importPaymentQuery
	 */
	public void _save(ImportPaymentQuery importPaymentQuery)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(importPaymentQuery);
		// 对id赋空值，使之执行insert操作(适应复制创建场景)
		importPaymentQuery.setPaymentid(null);
                                                                                                                              		importPaymentQueryHibernateDao.saveOrUpdate(importPaymentQuery);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(importPaymentQuery);
	}
	
	/**
	 *
	 * 保存  
	 *   
	 * @param importPaymentQuery
	 */
	public void _saveOrUpdate(ImportPaymentQuery importPaymentQuery
,BusinessObject businessObject		
	)
	{
		if (StringUtils.isNullBlank(importPaymentQuery.getPaymentid()))
		{	
			_save(importPaymentQuery);
		}
		else
		{
			_update(importPaymentQuery
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