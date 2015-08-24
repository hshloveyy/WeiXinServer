/*
 * @(#)ImportFundFlowServiceGen.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年07月25日 09点52分24秒
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
import com.infolion.xdss3.payment.domain.ImportFundFlow;
import com.infolion.xdss3.payment.service.ImportFundFlowService;
import com.infolion.xdss3.payment.dao.ImportFundFlowHibernateDao;

/**
 * <pre>
 * 付款纯资金(ImportFundFlow)服务类
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
public class ImportFundFlowServiceGen extends BaseService
{
	protected Log log = LogFactory.getFactory().getLog(this.getClass());

	@Autowired
	protected ImportFundFlowHibernateDao importFundFlowHibernateDao;
	
	public void setImportFundFlowHibernateDao(ImportFundFlowHibernateDao importFundFlowHibernateDao)
	{
		this.importFundFlowHibernateDao = importFundFlowHibernateDao;
	}
	
     /**
	 * 注入扩展服务类
	 */
	 @Autowired(required = false)
	 public void setAdvanceService(@Qualifier("importFundFlowAdvanceService") AdvanceService advanceService)
	 {
	  	super.setAdvanceService(advanceService);
	 }   
	 
	   
	/**
	 * 根据主键ID,取得付款纯资金实例
	 * @param id
	 * @return
	 */
	public ImportFundFlow _getDetached(String id)
	{		
	    ImportFundFlow importFundFlow = new ImportFundFlow();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  importFundFlow = importFundFlowHibernateDao.getDetached(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(importFundFlow);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    importFundFlow.setOperationType(operationType);
	    
		return importFundFlow;	
	}
	
	/**
	 * 根据主键ID,取得付款纯资金实例
	 * @param id
	 * @return
	 */
	public ImportFundFlow _get(String id)
	{		
	    ImportFundFlow importFundFlow = new ImportFundFlow();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  importFundFlow = importFundFlowHibernateDao.get(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(importFundFlow);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    importFundFlow.setOperationType(operationType);
	    
		return importFundFlow;	
	}
	
	/**
	 * 根据主键ID,取得付款纯资金实例
	 * @param id
	 * @return
	 */
	public ImportFundFlow _getForEdit(String id)
	{		
	    ImportFundFlow importFundFlow = _get(id);
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = importFundFlow.getOperationType();
	    if(OperationType.READONLY.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotEdit);
	    }

		return importFundFlow;	
	}
	
	/**
	 * 根据主键ID,取得付款纯资金实例副本
	 * @param id
	 * @return
	 */
	public ImportFundFlow _getEntityCopy(String id)
	{		
	    ImportFundFlow importFundFlow = new ImportFundFlow();
		ImportFundFlow importFundFlowOld = this._get(id);
		try
		{
			BeanUtils.copyProperties(importFundFlow, importFundFlowOld);
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (InvocationTargetException e)
		{
			e.printStackTrace();
		}
		//importFundFlow.setFundflowid(null); 
		return importFundFlow;	
	}
	
	/**
	 * 删除  
	 *   
	 * @param importFundFlow
	 */
	public void _delete(ImportFundFlow importFundFlow)
	{
		if (null != advanceService)
			advanceService.preDelete(importFundFlow);
	
 		LockService.isBoInstanceLocked(importFundFlow,ImportFundFlow.class);
		importFundFlowHibernateDao.remove(importFundFlow);

		if (null != advanceService)
			advanceService.postDelete(importFundFlow);
	}

	/**
	 * 根据主键删除
	 * 
	 * @param importFundFlowId
	 */
	public void _delete(String importFundFlowId,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(importFundFlowId))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("fundflowid"));
		ImportFundFlow importFundFlow = this.importFundFlowHibernateDao.load(importFundFlowId);
		_delete(importFundFlow);
	}
	
	/**
	 * 删除  
	 *   
	 * @param Set<ImportFundFlow> importFundFlows
	 */
	public void _deletes(Set<ImportFundFlow> importFundFlows,BusinessObject businessObject)
	{
		if (null == importFundFlows || importFundFlows.size() < 1)
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoText());
		Iterator<ImportFundFlow> it = importFundFlows.iterator();
		while (it.hasNext())
		{
			ImportFundFlow importFundFlow = (ImportFundFlow) it.next();
			_delete(importFundFlow);
		}
	}
	
	/**
	 * 根据多个主键(由，分割)删除多个对象
	 * 
	 * @param importFundFlowIds
	 */
	public void _deletes(String importFundFlowIds,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(importFundFlowIds))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("fundflowid"));
		String[] ids = StringUtils.splitString(importFundFlowIds);
		for (int i = 0; i < ids.length; i++)
		{
			_delete(ids[i],businessObject);
		}
	}
	/**
	 * 提交工作流
	 * 
	 * @param importFundFlow
	 */
	public void _submitProcess(ImportFundFlow importFundFlow
	,BusinessObject businessObject)
	{
		String id = importFundFlow.getFundflowid();
        /**
		if (StringUtils.isNullBlank(id))
		{
			_save(importFundFlow);
		}
		else
		{
			_update(importFundFlow
			, businessObject);
		}**/
		
		String taskId = importFundFlow.getWorkflowTaskId();
		id = importFundFlow.getFundflowid();
		if (null == taskId || "".equals(taskId))
			WorkflowService.createAndSignalProcessInstance(importFundFlow, id);
		else
			WorkflowService.signalProcessInstance(importFundFlow, id, null);
	}

	/**
	 * 保存或更新付款纯资金
	 * 保存  
	 *  
	 * @param importFundFlow
	 */
	public void _update(ImportFundFlow importFundFlow
,BusinessObject businessObject	
	)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(importFundFlow);
		importFundFlowHibernateDao.saveOrUpdate(importFundFlow);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(importFundFlow);
	}
	
	/**
	 * 保存  
	 *   
	 * @param importFundFlow
	 */
	public void _save(ImportFundFlow importFundFlow)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(importFundFlow);
		// 对id赋空值，使之执行insert操作(适应复制创建场景)
		importFundFlow.setFundflowid(null);
                                                        		importFundFlowHibernateDao.saveOrUpdate(importFundFlow);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(importFundFlow);
	}
	
	/**
	 *
	 * 保存  
	 *   
	 * @param importFundFlow
	 */
	public void _saveOrUpdate(ImportFundFlow importFundFlow
,BusinessObject businessObject		
	)
	{
		if (StringUtils.isNullBlank(importFundFlow.getFundflowid()))
		{	
			_save(importFundFlow);
		}
		else
		{
			_update(importFundFlow
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