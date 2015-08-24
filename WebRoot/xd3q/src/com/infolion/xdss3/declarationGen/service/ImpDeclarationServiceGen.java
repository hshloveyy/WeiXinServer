/*
 * @(#)ImpDeclarationServiceGen.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2011年06月23日 17点57分23秒
 *  描　述：创建
 */
package com.infolion.xdss3.declarationGen.service;

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
import com.infolion.xdss3.declaration.domain.ImpDeclaration;
import com.infolion.xdss3.declaration.service.ImpDeclarationService;
import com.infolion.xdss3.declaration.dao.ImpDeclarationHibernateDao;

/**
 * <pre>
 * 进口报关单(ImpDeclaration)服务类
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
public class ImpDeclarationServiceGen extends BaseService
{
	protected Log log = LogFactory.getFactory().getLog(this.getClass());

	@Autowired
	protected ImpDeclarationHibernateDao impDeclarationHibernateDao;
	
	public void setImpDeclarationHibernateDao(ImpDeclarationHibernateDao impDeclarationHibernateDao)
	{
		this.impDeclarationHibernateDao = impDeclarationHibernateDao;
	}
	
     /**
	 * 注入扩展服务类
	 */
	 @Autowired(required = false)
	 public void setAdvanceService(@Qualifier("impDeclarationAdvanceService") AdvanceService advanceService)
	 {
	  	super.setAdvanceService(advanceService);
	 }   
	 
	   
	/**
	 * 根据主键ID,取得进口报关单实例
	 * @param id
	 * @return
	 */
	public ImpDeclaration _getDetached(String id)
	{		
	    ImpDeclaration impDeclaration = new ImpDeclaration();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  impDeclaration = impDeclarationHibernateDao.getDetached(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(impDeclaration);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    impDeclaration.setOperationType(operationType);
	    
		return impDeclaration;	
	}
	
	/**
	 * 根据主键ID,取得进口报关单实例
	 * @param id
	 * @return
	 */
	public ImpDeclaration _get(String id)
	{		
	    ImpDeclaration impDeclaration = new ImpDeclaration();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  impDeclaration = impDeclarationHibernateDao.get(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(impDeclaration);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    impDeclaration.setOperationType(operationType);
	    
		return impDeclaration;	
	}
	
	/**
	 * 根据主键ID,取得进口报关单实例
	 * @param id
	 * @return
	 */
	public ImpDeclaration _getForEdit(String id)
	{		
	    ImpDeclaration impDeclaration = _get(id);
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = impDeclaration.getOperationType();
	    if(OperationType.READONLY.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotEdit);
	    }

		return impDeclaration;	
	}
	
	/**
	 * 根据主键ID,取得进口报关单实例副本
	 * @param id
	 * @return
	 */
	public ImpDeclaration _getEntityCopy(String id)
	{		
	    ImpDeclaration impDeclaration = new ImpDeclaration();
		ImpDeclaration impDeclarationOld = this._get(id);
		try
		{
			BeanUtils.copyProperties(impDeclaration, impDeclarationOld);
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (InvocationTargetException e)
		{
			e.printStackTrace();
		}
		//impDeclaration.setDeclarationsid(null); 
		return impDeclaration;	
	}
	
	/**
	 * 删除  
	 *   
	 * @param impDeclaration
	 */
	public void _delete(ImpDeclaration impDeclaration)
	{
		if (null != advanceService)
			advanceService.preDelete(impDeclaration);
	
 		LockService.isBoInstanceLocked(impDeclaration,ImpDeclaration.class);
		impDeclarationHibernateDao.remove(impDeclaration);

		if (null != advanceService)
			advanceService.postDelete(impDeclaration);
	}

	/**
	 * 根据主键删除
	 * 
	 * @param impDeclarationId
	 */
	public void _delete(String impDeclarationId,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(impDeclarationId))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("declarationsid"));
		ImpDeclaration impDeclaration = this.impDeclarationHibernateDao.load(impDeclarationId);
		_delete(impDeclaration);
	}
	
	/**
	 * 删除  
	 *   
	 * @param Set<ImpDeclaration> impDeclarations
	 */
	public void _deletes(Set<ImpDeclaration> impDeclarations,BusinessObject businessObject)
	{
		if (null == impDeclarations || impDeclarations.size() < 1)
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoText());
		Iterator<ImpDeclaration> it = impDeclarations.iterator();
		while (it.hasNext())
		{
			ImpDeclaration impDeclaration = (ImpDeclaration) it.next();
			_delete(impDeclaration);
		}
	}
	
	/**
	 * 根据多个主键(由，分割)删除多个对象
	 * 
	 * @param impDeclarationIds
	 */
	public void _deletes(String impDeclarationIds,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(impDeclarationIds))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("declarationsid"));
		String[] ids = StringUtils.splitString(impDeclarationIds);
		for (int i = 0; i < ids.length; i++)
		{
			_delete(ids[i],businessObject);
		}
	}
	/**
	 * 提交工作流
	 * 
	 * @param impDeclaration
	 */
	public void _submitProcess(ImpDeclaration impDeclaration
	,BusinessObject businessObject)
	{
		String id = impDeclaration.getDeclarationsid();
        /**
		if (StringUtils.isNullBlank(id))
		{
			_save(impDeclaration);
		}
		else
		{
			_update(impDeclaration
			, businessObject);
		}**/
		
		String taskId = impDeclaration.getWorkflowTaskId();
		id = impDeclaration.getDeclarationsid();
		if (null == taskId || "".equals(taskId))
			WorkflowService.createAndSignalProcessInstance(impDeclaration, id);
		else
			WorkflowService.signalProcessInstance(impDeclaration, id, null);
	}

	/**
	 * 保存或更新进口报关单
	 * 保存  
	 *  
	 * @param impDeclaration
	 */
	public void _update(ImpDeclaration impDeclaration
,BusinessObject businessObject	
	)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(impDeclaration);
		impDeclarationHibernateDao.saveOrUpdate(impDeclaration);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(impDeclaration);
	}
	
	/**
	 * 保存  
	 *   
	 * @param impDeclaration
	 */
	public void _save(ImpDeclaration impDeclaration)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(impDeclaration);
		// 对id赋空值，使之执行insert操作(适应复制创建场景)
		impDeclaration.setDeclarationsid(null);
                          		impDeclarationHibernateDao.saveOrUpdate(impDeclaration);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(impDeclaration);
	}
	
	/**
	 *
	 * 保存  
	 *   
	 * @param impDeclaration
	 */
	public void _saveOrUpdate(ImpDeclaration impDeclaration
,BusinessObject businessObject		
	)
	{
		if (StringUtils.isNullBlank(impDeclaration.getDeclarationsid()))
		{	
			_save(impDeclaration);
		}
		else
		{
			_update(impDeclaration
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