/*
 * @(#)ProviderCreditConfServiceGen.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年05月20日 12点34分50秒
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
import com.infolion.xdss3.ceditValueControl.domain.ProviderCreditConf;
import com.infolion.xdss3.ceditValueControl.service.ProviderCreditConfService;
import com.infolion.xdss3.ceditValueControl.dao.ProviderCreditConfHibernateDao;
          
import com.infolion.xdss3.ceditValueControl.domain.ProviderCreditProj;
import com.infolion.xdss3.ceditValueControl.service.ProviderCreditProjService;

/**
 * <pre>
 * 供应商信用额度配置(ProviderCreditConf)服务类
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
public class ProviderCreditConfServiceGen extends BaseService
{
	protected Log log = LogFactory.getFactory().getLog(this.getClass());

	@Autowired
	protected ProviderCreditConfHibernateDao providerCreditConfHibernateDao;
	
	public void setProviderCreditConfHibernateDao(ProviderCreditConfHibernateDao providerCreditConfHibernateDao)
	{
		this.providerCreditConfHibernateDao = providerCreditConfHibernateDao;
	}
	
     /**
	 * 注入扩展服务类
	 */
	 @Autowired(required = false)
	 public void setAdvanceService(@Qualifier("providerCreditConfAdvanceService") AdvanceService advanceService)
	 {
	  	super.setAdvanceService(advanceService);
	 }   
	 
          

	@Autowired
	protected ProviderCreditProjService providerCreditProjService;
	
	public void setProviderCreditProjService(ProviderCreditProjService providerCreditProjService)
	{
		this.providerCreditProjService = providerCreditProjService;
	}
	
	   
	/**
	 * 根据主键ID,取得供应商信用额度配置实例
	 * @param id
	 * @return
	 */
	public ProviderCreditConf _getDetached(String id)
	{		
	    ProviderCreditConf providerCreditConf = new ProviderCreditConf();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  providerCreditConf = providerCreditConfHibernateDao.getDetached(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(providerCreditConf);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    providerCreditConf.setOperationType(operationType);
	    
		return providerCreditConf;	
	}
	
	/**
	 * 根据主键ID,取得供应商信用额度配置实例
	 * @param id
	 * @return
	 */
	public ProviderCreditConf _get(String id)
	{		
	    ProviderCreditConf providerCreditConf = new ProviderCreditConf();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  providerCreditConf = providerCreditConfHibernateDao.get(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(providerCreditConf);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    providerCreditConf.setOperationType(operationType);
	    
		return providerCreditConf;	
	}
	
	/**
	 * 根据主键ID,取得供应商信用额度配置实例
	 * @param id
	 * @return
	 */
	public ProviderCreditConf _getForEdit(String id)
	{		
	    ProviderCreditConf providerCreditConf = _get(id);
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = providerCreditConf.getOperationType();
	    if(OperationType.READONLY.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotEdit);
	    }

		return providerCreditConf;	
	}
	
	/**
	 * 根据主键ID,取得供应商信用额度配置实例副本
	 * @param id
	 * @return
	 */
	public ProviderCreditConf _getEntityCopy(String id)
	{		
	    ProviderCreditConf providerCreditConf = new ProviderCreditConf();
		ProviderCreditConf providerCreditConfOld = this._get(id);
		try
		{
			BeanUtils.copyProperties(providerCreditConf, providerCreditConfOld);
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (InvocationTargetException e)
		{
			e.printStackTrace();
		}
		//providerCreditConf.setConfigid(null); 
		return providerCreditConf;	
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
	 * @param providerCreditConf
	 */
	public void _delete(ProviderCreditConf providerCreditConf)
	{
		if (null != advanceService)
			advanceService.preDelete(providerCreditConf);
	
 		LockService.isBoInstanceLocked(providerCreditConf,ProviderCreditConf.class);
		providerCreditConfHibernateDao.remove(providerCreditConf);

		if (null != advanceService)
			advanceService.postDelete(providerCreditConf);
	}

	/**
	 * 根据主键删除
	 * 
	 * @param providerCreditConfId
	 */
	public void _delete(String providerCreditConfId,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(providerCreditConfId))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("configid"));
		ProviderCreditConf providerCreditConf = this.providerCreditConfHibernateDao.load(providerCreditConfId);
		_delete(providerCreditConf);
	}
	
	/**
	 * 删除  
	 *   
	 * @param Set<ProviderCreditConf> providerCreditConfs
	 */
	public void _deletes(Set<ProviderCreditConf> providerCreditConfs,BusinessObject businessObject)
	{
		if (null == providerCreditConfs || providerCreditConfs.size() < 1)
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoText());
		Iterator<ProviderCreditConf> it = providerCreditConfs.iterator();
		while (it.hasNext())
		{
			ProviderCreditConf providerCreditConf = (ProviderCreditConf) it.next();
			_delete(providerCreditConf);
		}
	}
	
	/**
	 * 根据多个主键(由，分割)删除多个对象
	 * 
	 * @param providerCreditConfIds
	 */
	public void _deletes(String providerCreditConfIds,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(providerCreditConfIds))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("configid"));
		String[] ids = StringUtils.splitString(providerCreditConfIds);
		for (int i = 0; i < ids.length; i++)
		{
			_delete(ids[i],businessObject);
		}
	}
	/**
	 * 提交工作流
	 * 
	 * @param providerCreditConf
	 */
	public void _submitProcess(ProviderCreditConf providerCreditConf
,Set<ProviderCreditProj> deletedProviderCreditProjectSet	,BusinessObject businessObject)
	{
		String id = providerCreditConf.getConfigid();
		if (StringUtils.isNullBlank(id))
		{
			_save(providerCreditConf);
		}
		else
		{
			_update(providerCreditConf
,deletedProviderCreditProjectSet			, businessObject);
		}
		String taskId = providerCreditConf.getWorkflowTaskId();
		id = providerCreditConf.getConfigid();
		if (null == taskId || "".equals(taskId))
			WorkflowService.createAndSignalProcessInstance(providerCreditConf, id);
		else
			WorkflowService.signalProcessInstance(providerCreditConf, id, null);
	}

	/**
	 * 保存或更新供应商信用额度配置
	 * 保存  
	 *  
	 * @param providerCreditConf
	 */
	public void _update(ProviderCreditConf providerCreditConf
,Set<ProviderCreditProj> deletedProviderCreditProjSet,BusinessObject businessObject	
	)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(providerCreditConf);
		providerCreditConfHibernateDao.saveOrUpdate(providerCreditConf);
// 删除关联子业务对象数据
if(deletedProviderCreditProjSet!=null && deletedProviderCreditProjSet.size()>0)
{
providerCreditProjService._deletes(deletedProviderCreditProjSet,businessObject);
}		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(providerCreditConf);
	}
	
	/**
	 * 保存  
	 *   
	 * @param providerCreditConf
	 */
	public void _save(ProviderCreditConf providerCreditConf)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(providerCreditConf);
		// 对id赋空值，使之执行insert操作(适应复制创建场景)
		providerCreditConf.setConfigid(null);
       
		Set<ProviderCreditProj> providerCreditProjectSet = providerCreditConf.getProviderCreditProject();
		Set<ProviderCreditProj> newProviderCreditProjSet = null;
		if (null != providerCreditProjectSet)
		{
			newProviderCreditProjSet = new HashSet();
			Iterator<ProviderCreditProj> itProviderCreditProj = providerCreditProjectSet.iterator();
			while (itProviderCreditProj.hasNext())
			{
				ProviderCreditProj providerCreditProject = (ProviderCreditProj) itProviderCreditProj.next();
				providerCreditProject.setConfigprojectid(null);
				newProviderCreditProjSet.add(providerCreditProject);
			}
		}
		providerCreditConf.setProviderCreditProject(newProviderCreditProjSet);
       
       
       
       
       
       
       
       
       
       
       
       
       
		providerCreditConfHibernateDao.saveOrUpdate(providerCreditConf);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(providerCreditConf);
	}
	
	/**
	 *
	 * 保存  
	 *   
	 * @param providerCreditConf
	 */
	public void _saveOrUpdate(ProviderCreditConf providerCreditConf
,Set<ProviderCreditProj> deletedProviderCreditProjSet,BusinessObject businessObject		
	)
	{
		if (StringUtils.isNullBlank(providerCreditConf.getConfigid()))
		{	
			_save(providerCreditConf);
		}
		else
		{
			_update(providerCreditConf
,deletedProviderCreditProjSet
, businessObject);
}	}
	
}