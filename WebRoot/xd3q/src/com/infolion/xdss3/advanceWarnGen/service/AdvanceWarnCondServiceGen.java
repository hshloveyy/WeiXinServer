/*
 * @(#)AdvanceWarnCondServiceGen.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年05月19日 11点29分43秒
 *  描　述：创建
 */
package com.infolion.xdss3.advanceWarnGen.service;

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
import com.infolion.xdss3.advanceWarn.domain.AdvanceWarnCond;
import com.infolion.xdss3.advanceWarn.service.AdvanceWarnCondService;
import com.infolion.xdss3.advanceWarn.dao.AdvanceWarnCondHibernateDao;

/**
 * <pre>
 * 预警对像条件(AdvanceWarnCond)服务类
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
public class AdvanceWarnCondServiceGen extends BaseService
{
	protected Log log = LogFactory.getFactory().getLog(this.getClass());

	@Autowired
	protected AdvanceWarnCondHibernateDao advanceWarnCondHibernateDao;
	
	public void setAdvanceWarnCondHibernateDao(AdvanceWarnCondHibernateDao advanceWarnCondHibernateDao)
	{
		this.advanceWarnCondHibernateDao = advanceWarnCondHibernateDao;
	}
	
     /**
	 * 注入扩展服务类
	 */
	 @Autowired(required = false)
	 public void setAdvanceService(@Qualifier("advanceWarnCondAdvanceService") AdvanceService advanceService)
	 {
	  	super.setAdvanceService(advanceService);
	 }   
	 
	   
	/**
	 * 根据主键ID,取得预警对像条件实例
	 * @param id
	 * @return
	 */
	public AdvanceWarnCond _getDetached(String id)
	{		
	    AdvanceWarnCond advanceWarnCond = new AdvanceWarnCond();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  advanceWarnCond = advanceWarnCondHibernateDao.getDetached(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(advanceWarnCond);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    advanceWarnCond.setOperationType(operationType);
	    
		return advanceWarnCond;	
	}
	
	/**
	 * 根据主键ID,取得预警对像条件实例
	 * @param id
	 * @return
	 */
	public AdvanceWarnCond _get(String id)
	{		
	    AdvanceWarnCond advanceWarnCond = new AdvanceWarnCond();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  advanceWarnCond = advanceWarnCondHibernateDao.get(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(advanceWarnCond);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    advanceWarnCond.setOperationType(operationType);
	    
		return advanceWarnCond;	
	}
	
	/**
	 * 根据主键ID,取得预警对像条件实例
	 * @param id
	 * @return
	 */
	public AdvanceWarnCond _getForEdit(String id)
	{		
	    AdvanceWarnCond advanceWarnCond = _get(id);
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = advanceWarnCond.getOperationType();
	    if(OperationType.READONLY.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotEdit);
	    }

		return advanceWarnCond;	
	}
	
	/**
	 * 根据主键ID,取得预警对像条件实例副本
	 * @param id
	 * @return
	 */
	public AdvanceWarnCond _getEntityCopy(String id)
	{		
	    AdvanceWarnCond advanceWarnCond = new AdvanceWarnCond();
		AdvanceWarnCond advanceWarnCondOld = this._get(id);
		try
		{
			BeanUtils.copyProperties(advanceWarnCond, advanceWarnCondOld);
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (InvocationTargetException e)
		{
			e.printStackTrace();
		}
		//advanceWarnCond.setConditionid(null); 
		return advanceWarnCond;	
	}
	
	/**
	 * 删除  
	 *   
	 * @param advanceWarnCond
	 */
	public void _delete(AdvanceWarnCond advanceWarnCond)
	{
		if (null != advanceService)
			advanceService.preDelete(advanceWarnCond);
	
 		LockService.isBoInstanceLocked(advanceWarnCond,AdvanceWarnCond.class);
		advanceWarnCondHibernateDao.remove(advanceWarnCond);

		if (null != advanceService)
			advanceService.postDelete(advanceWarnCond);
	}

	/**
	 * 根据主键删除
	 * 
	 * @param advanceWarnCondId
	 */
	public void _delete(String advanceWarnCondId,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(advanceWarnCondId))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("conditionid"));
		AdvanceWarnCond advanceWarnCond = this.advanceWarnCondHibernateDao.load(advanceWarnCondId);
		_delete(advanceWarnCond);
	}
	
	/**
	 * 删除  
	 *   
	 * @param Set<AdvanceWarnCond> advanceWarnConds
	 */
	public void _deletes(Set<AdvanceWarnCond> advanceWarnConds,BusinessObject businessObject)
	{
		if (null == advanceWarnConds || advanceWarnConds.size() < 1)
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoText());
		Iterator<AdvanceWarnCond> it = advanceWarnConds.iterator();
		while (it.hasNext())
		{
			AdvanceWarnCond advanceWarnCond = (AdvanceWarnCond) it.next();
			_delete(advanceWarnCond);
		}
	}
	
	/**
	 * 根据多个主键(由，分割)删除多个对象
	 * 
	 * @param advanceWarnCondIds
	 */
	public void _deletes(String advanceWarnCondIds,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(advanceWarnCondIds))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("conditionid"));
		String[] ids = StringUtils.splitString(advanceWarnCondIds);
		for (int i = 0; i < ids.length; i++)
		{
			_delete(ids[i],businessObject);
		}
	}
	/**
	 * 提交工作流
	 * 
	 * @param advanceWarnCond
	 */
	public void _submitProcess(AdvanceWarnCond advanceWarnCond
	,BusinessObject businessObject)
	{
		String id = advanceWarnCond.getConditionid();
		if (StringUtils.isNullBlank(id))
		{
			_save(advanceWarnCond);
		}
		else
		{
			_update(advanceWarnCond
			, businessObject);
		}
		String taskId = advanceWarnCond.getWorkflowTaskId();
		id = advanceWarnCond.getConditionid();
		if (null == taskId || "".equals(taskId))
			WorkflowService.createAndSignalProcessInstance(advanceWarnCond, id);
		else
			WorkflowService.signalProcessInstance(advanceWarnCond, id, null);
	}

	/**
	 * 保存或更新预警对像条件
	 * 保存  
	 *  
	 * @param advanceWarnCond
	 */
	public void _update(AdvanceWarnCond advanceWarnCond
,BusinessObject businessObject	
	)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(advanceWarnCond);
		advanceWarnCondHibernateDao.saveOrUpdate(advanceWarnCond);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(advanceWarnCond);
	}
	
	/**
	 * 保存  
	 *   
	 * @param advanceWarnCond
	 */
	public void _save(AdvanceWarnCond advanceWarnCond)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(advanceWarnCond);
		// 对id赋空值，使之执行insert操作(适应复制创建场景)
		advanceWarnCond.setConditionid(null);
                          		advanceWarnCondHibernateDao.saveOrUpdate(advanceWarnCond);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(advanceWarnCond);
	}
	
	/**
	 *
	 * 保存  
	 *   
	 * @param advanceWarnCond
	 */
	public void _saveOrUpdate(AdvanceWarnCond advanceWarnCond
,BusinessObject businessObject		
	)
	{
		if (StringUtils.isNullBlank(advanceWarnCond.getConditionid()))
		{	
			_save(advanceWarnCond);
		}
		else
		{
			_update(advanceWarnCond
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