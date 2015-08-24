/*
 * @(#)OrdrRepServiceGen.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年03月23日 14点32分44秒
 *  描　述：创建
 */
package com.infolion.sample.b1.ordrGen.service;

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
import com.infolion.sample.b1.ordr.domain.OrdrRep;
import com.infolion.sample.b1.ordr.service.OrdrRepService;
import com.infolion.sample.b1.ordr.dao.OrdrRepHibernateDao;

/**
 * <pre>
 * OrdrRep(OrdrRep)服务类
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
public class OrdrRepServiceGen extends BaseService
{
	protected Log log = LogFactory.getFactory().getLog(this.getClass());

	@Autowired
	protected OrdrRepHibernateDao ordrRepHibernateDao;
	
	public void setOrdrRepHibernateDao(OrdrRepHibernateDao ordrRepHibernateDao)
	{
		this.ordrRepHibernateDao = ordrRepHibernateDao;
	}
	
     /**
	 * 注入扩展服务类
	 */
	 @Autowired(required = false)
	 public void setAdvanceService(@Qualifier("ordrRepAdvanceService") AdvanceService advanceService)
	 {
	  	super.setAdvanceService(advanceService);
	 }   
	 
	   
	/**
	 * 根据主键ID,取得OrdrRep实例
	 * @param id
	 * @return
	 */
	public OrdrRep _getDetached(String id)
	{		
	    OrdrRep ordrRep = new OrdrRep();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  ordrRep = ordrRepHibernateDao.getDetached(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(ordrRep);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    ordrRep.setOperationType(operationType);
	    
		return ordrRep;	
	}
	
	/**
	 * 根据主键ID,取得OrdrRep实例
	 * @param id
	 * @return
	 */
	public OrdrRep _get(String id)
	{		
	    OrdrRep ordrRep = new OrdrRep();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  ordrRep = ordrRepHibernateDao.get(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(ordrRep);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    ordrRep.setOperationType(operationType);
	    
		return ordrRep;	
	}
	
	/**
	 * 根据主键ID,取得OrdrRep实例
	 * @param id
	 * @return
	 */
	public OrdrRep _getForEdit(String id)
	{		
	    OrdrRep ordrRep = _get(id);
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = ordrRep.getOperationType();
	    if(OperationType.READONLY.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotEdit);
	    }

		return ordrRep;	
	}
	
	/**
	 * 根据主键ID,取得OrdrRep实例副本
	 * @param id
	 * @return
	 */
	public OrdrRep _getEntityCopy(String id)
	{		
	    OrdrRep ordrRep = new OrdrRep();
		OrdrRep ordrRepOld = this._get(id);
		try
		{
			BeanUtils.copyProperties(ordrRep, ordrRepOld);
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (InvocationTargetException e)
		{
			e.printStackTrace();
		}
		ordrRep.setDocentry(null); 
		return ordrRep;	
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
	 * @param ordrRep
	 */
	public void _delete(OrdrRep ordrRep)
	{
		if (null != advanceService)
			advanceService.preDelete(ordrRep);
	
 		LockService.isBoInstanceLocked(ordrRep,OrdrRep.class);
		ordrRepHibernateDao.remove(ordrRep);

		if (null != advanceService)
			advanceService.postDelete(ordrRep);
	}

	/**
	 * 根据主键删除
	 * 
	 * @param ordrRepId
	 */
	public void _delete(String ordrRepId,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(ordrRepId))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("docentry"));
		OrdrRep ordrRep = this.ordrRepHibernateDao.load(ordrRepId);
		_delete(ordrRep);
	}
	
	/**
	 * 删除  
	 *   
	 * @param Set<OrdrRep> ordrReps
	 */
	public void _deletes(Set<OrdrRep> ordrReps,BusinessObject businessObject)
	{
		if (null == ordrReps || ordrReps.size() < 1)
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoText());
		Iterator<OrdrRep> it = ordrReps.iterator();
		while (it.hasNext())
		{
			OrdrRep ordrRep = (OrdrRep) it.next();
			_delete(ordrRep);
		}
	}
	
	/**
	 * 根据多个主键(由，分割)删除多个对象
	 * 
	 * @param ordrRepIds
	 */
	public void _deletes(String ordrRepIds,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(ordrRepIds))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("docentry"));
		String[] ids = StringUtils.splitString(ordrRepIds);
		for (int i = 0; i < ids.length; i++)
		{
			_delete(ids[i],businessObject);
		}
	}
	/**
	 * 提交工作流
	 * 
	 * @param ordrRep
	 */
	public void _submitProcess(OrdrRep ordrRep
	,BusinessObject businessObject)
	{
		String id = ordrRep.getDocentry();
		if (StringUtils.isNullBlank(id))
		{
			_save(ordrRep);
		}
		else
		{
			_update(ordrRep
			, businessObject);
		}
		String taskId = ordrRep.getWorkflowTaskId();
		if (null == taskId || "".equals(taskId))
			WorkflowService.createAndSignalProcessInstance(ordrRep, id);
		else
			WorkflowService.signalProcessInstance(ordrRep, id, null);
	}

	/**
	 * 保存或更新OrdrRep
	 * 保存  
	 *  
	 * @param ordrRep
	 */
	public void _update(OrdrRep ordrRep
,BusinessObject businessObject	
	)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(ordrRep);
		ordrRepHibernateDao.saveOrUpdate(ordrRep);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(ordrRep);
	}
	
	/**
	 * 保存  
	 *   
	 * @param ordrRep
	 */
	public void _save(OrdrRep ordrRep)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(ordrRep);
		// 对id赋空值，使之执行insert操作(适应复制创建场景)
		ordrRep.setDocentry(null);
                  		ordrRepHibernateDao.saveOrUpdate(ordrRep);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(ordrRep);
	}
	
	/**
	 *
	 * 保存  
	 *   
	 * @param ordrRep
	 */
	public void _saveOrUpdate(OrdrRep ordrRep
,BusinessObject businessObject		
	)
	{
		if (StringUtils.isNullBlank(ordrRep.getDocentry()))
		{	
			_save(ordrRep);
		}
		else
		{
			_update(ordrRep
, businessObject);
}	}
	
}