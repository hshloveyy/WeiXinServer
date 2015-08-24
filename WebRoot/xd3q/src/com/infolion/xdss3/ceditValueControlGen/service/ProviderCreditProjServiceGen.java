/*
 * @(#)ProviderCreditProjServiceGen.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年05月20日 12点34分51秒
 *  描　述：创建
 */
package com.infolion.xdss3.ceditValueControlGen.service;

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
import com.infolion.xdss3.ceditValueControl.domain.ProviderCreditProj;
import com.infolion.xdss3.ceditValueControl.service.ProviderCreditProjService;
import com.infolion.xdss3.ceditValueControl.dao.ProviderCreditProjHibernateDao;

/**
 * <pre>
 * 供应商授限立项配置(ProviderCreditProj)服务类
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
public class ProviderCreditProjServiceGen extends BaseService
{
	protected Log log = LogFactory.getFactory().getLog(this.getClass());

	@Autowired
	protected ProviderCreditProjHibernateDao providerCreditProjHibernateDao;
	
	public void setProviderCreditProjHibernateDao(ProviderCreditProjHibernateDao providerCreditProjHibernateDao)
	{
		this.providerCreditProjHibernateDao = providerCreditProjHibernateDao;
	}
	
     /**
	 * 注入扩展服务类
	 */
	 @Autowired(required = false)
	 public void setAdvanceService(@Qualifier("providerCreditProjAdvanceService") AdvanceService advanceService)
	 {
	  	super.setAdvanceService(advanceService);
	 }   
	 
	   
	/**
	 * 根据主键ID,取得供应商授限立项配置实例
	 * @param id
	 * @return
	 */
	public ProviderCreditProj _getDetached(String id)
	{		
	    ProviderCreditProj providerCreditProj = new ProviderCreditProj();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  providerCreditProj = providerCreditProjHibernateDao.getDetached(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(providerCreditProj);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    providerCreditProj.setOperationType(operationType);
	    
		return providerCreditProj;	
	}
	
	/**
	 * 根据主键ID,取得供应商授限立项配置实例
	 * @param id
	 * @return
	 */
	public ProviderCreditProj _get(String id)
	{		
	    ProviderCreditProj providerCreditProj = new ProviderCreditProj();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  providerCreditProj = providerCreditProjHibernateDao.get(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(providerCreditProj);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    providerCreditProj.setOperationType(operationType);
	    
		return providerCreditProj;	
	}
	
	/**
	 * 根据主键ID,取得供应商授限立项配置实例
	 * @param id
	 * @return
	 */
	public ProviderCreditProj _getForEdit(String id)
	{		
	    ProviderCreditProj providerCreditProj = _get(id);
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = providerCreditProj.getOperationType();
	    if(OperationType.READONLY.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotEdit);
	    }

		return providerCreditProj;	
	}
	
	/**
	 * 根据主键ID,取得供应商授限立项配置实例副本
	 * @param id
	 * @return
	 */
	public ProviderCreditProj _getEntityCopy(String id)
	{		
	    ProviderCreditProj providerCreditProj = new ProviderCreditProj();
		ProviderCreditProj providerCreditProjOld = this._get(id);
		try
		{
			BeanUtils.copyProperties(providerCreditProj, providerCreditProjOld);
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (InvocationTargetException e)
		{
			e.printStackTrace();
		}
		//providerCreditProj.setConfigprojectid(null); 
		return providerCreditProj;	
	}
	
	/**
	 * 删除  
	 *   
	 * @param providerCreditProj
	 */
	public void _delete(ProviderCreditProj providerCreditProj)
	{
		if (null != advanceService)
			advanceService.preDelete(providerCreditProj);
	
 		LockService.isBoInstanceLocked(providerCreditProj,ProviderCreditProj.class);
		providerCreditProjHibernateDao.remove(providerCreditProj);

		if (null != advanceService)
			advanceService.postDelete(providerCreditProj);
	}

	/**
	 * 根据主键删除
	 * 
	 * @param providerCreditProjId
	 */
	public void _delete(String providerCreditProjId,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(providerCreditProjId))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("configprojectid"));
		ProviderCreditProj providerCreditProj = this.providerCreditProjHibernateDao.load(providerCreditProjId);
		_delete(providerCreditProj);
	}
	
	/**
	 * 删除  
	 *   
	 * @param Set<ProviderCreditProj> providerCreditProjs
	 */
	public void _deletes(Set<ProviderCreditProj> providerCreditProjs,BusinessObject businessObject)
	{
		if (null == providerCreditProjs || providerCreditProjs.size() < 1)
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoText());
		Iterator<ProviderCreditProj> it = providerCreditProjs.iterator();
		while (it.hasNext())
		{
			ProviderCreditProj providerCreditProj = (ProviderCreditProj) it.next();
			_delete(providerCreditProj);
		}
	}
	
	/**
	 * 根据多个主键(由，分割)删除多个对象
	 * 
	 * @param providerCreditProjIds
	 */
	public void _deletes(String providerCreditProjIds,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(providerCreditProjIds))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("configprojectid"));
		String[] ids = StringUtils.splitString(providerCreditProjIds);
		for (int i = 0; i < ids.length; i++)
		{
			_delete(ids[i],businessObject);
		}
	}
	/**
	 * 提交工作流
	 * 
	 * @param providerCreditProj
	 */
	public void _submitProcess(ProviderCreditProj providerCreditProj
	,BusinessObject businessObject)
	{
		String id = providerCreditProj.getConfigprojectid();
		if (StringUtils.isNullBlank(id))
		{
			_save(providerCreditProj);
		}
		else
		{
			_update(providerCreditProj
			, businessObject);
		}
		String taskId = providerCreditProj.getWorkflowTaskId();
		id = providerCreditProj.getConfigprojectid();
		if (null == taskId || "".equals(taskId))
			WorkflowService.createAndSignalProcessInstance(providerCreditProj, id);
		else
			WorkflowService.signalProcessInstance(providerCreditProj, id, null);
	}

	/**
	 * 保存或更新供应商授限立项配置
	 * 保存  
	 *  
	 * @param providerCreditProj
	 */
	public void _update(ProviderCreditProj providerCreditProj
,BusinessObject businessObject	
	)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(providerCreditProj);
		providerCreditProjHibernateDao.saveOrUpdate(providerCreditProj);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(providerCreditProj);
	}
	
	/**
	 * 保存  
	 *   
	 * @param providerCreditProj
	 */
	public void _save(ProviderCreditProj providerCreditProj)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(providerCreditProj);
		// 对id赋空值，使之执行insert操作(适应复制创建场景)
		providerCreditProj.setConfigprojectid(null);
                		providerCreditProjHibernateDao.saveOrUpdate(providerCreditProj);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(providerCreditProj);
	}
	
	/**
	 *
	 * 保存  
	 *   
	 * @param providerCreditProj
	 */
	public void _saveOrUpdate(ProviderCreditProj providerCreditProj
,BusinessObject businessObject		
	)
	{
		if (StringUtils.isNullBlank(providerCreditProj.getConfigprojectid()))
		{	
			_save(providerCreditProj);
		}
		else
		{
			_update(providerCreditProj
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