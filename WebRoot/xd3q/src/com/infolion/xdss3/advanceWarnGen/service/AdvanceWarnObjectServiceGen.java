/*
 * @(#)AdvanceWarnObjectServiceGen.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年05月19日 11点29分37秒
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
import com.infolion.xdss3.advanceWarn.domain.AdvanceWarnObject;
import com.infolion.xdss3.advanceWarn.service.AdvanceWarnObjectService;
import com.infolion.xdss3.advanceWarn.dao.AdvanceWarnObjectHibernateDao;
          
import com.infolion.xdss3.advanceWarn.domain.AdvanceWarnRecv;
import com.infolion.xdss3.advanceWarn.service.AdvanceWarnRecvService;
          
import com.infolion.xdss3.advanceWarn.domain.AdvanceWarnCond;
import com.infolion.xdss3.advanceWarn.service.AdvanceWarnCondService;

/**
 * <pre>
 * 预警对像配置(AdvanceWarnObject)服务类
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
public class AdvanceWarnObjectServiceGen extends BaseService
{
	protected Log log = LogFactory.getFactory().getLog(this.getClass());

	@Autowired
	protected AdvanceWarnObjectHibernateDao advanceWarnObjectHibernateDao;
	
	public void setAdvanceWarnObjectHibernateDao(AdvanceWarnObjectHibernateDao advanceWarnObjectHibernateDao)
	{
		this.advanceWarnObjectHibernateDao = advanceWarnObjectHibernateDao;
	}
	
     /**
	 * 注入扩展服务类
	 */
	 @Autowired(required = false)
	 public void setAdvanceService(@Qualifier("advanceWarnObjectAdvanceService") AdvanceService advanceService)
	 {
	  	super.setAdvanceService(advanceService);
	 }   
	 
          

	@Autowired
	protected AdvanceWarnRecvService advanceWarnRecvService;
	
	public void setAdvanceWarnRecvService(AdvanceWarnRecvService advanceWarnRecvService)
	{
		this.advanceWarnRecvService = advanceWarnRecvService;
	}
	
          

	@Autowired
	protected AdvanceWarnCondService advanceWarnCondService;
	
	public void setAdvanceWarnCondService(AdvanceWarnCondService advanceWarnCondService)
	{
		this.advanceWarnCondService = advanceWarnCondService;
	}
	
	   
	/**
	 * 根据主键ID,取得预警对像配置实例
	 * @param id
	 * @return
	 */
	public AdvanceWarnObject _getDetached(String id)
	{		
	    AdvanceWarnObject advanceWarnObject = new AdvanceWarnObject();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  advanceWarnObject = advanceWarnObjectHibernateDao.getDetached(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(advanceWarnObject);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    advanceWarnObject.setOperationType(operationType);
	    
		return advanceWarnObject;	
	}
	
	/**
	 * 根据主键ID,取得预警对像配置实例
	 * @param id
	 * @return
	 */
	public AdvanceWarnObject _get(String id)
	{		
	    AdvanceWarnObject advanceWarnObject = new AdvanceWarnObject();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  advanceWarnObject = advanceWarnObjectHibernateDao.get(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(advanceWarnObject);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    advanceWarnObject.setOperationType(operationType);
	    
		return advanceWarnObject;	
	}
	
	/**
	 * 根据主键ID,取得预警对像配置实例
	 * @param id
	 * @return
	 */
	public AdvanceWarnObject _getForEdit(String id)
	{		
	    AdvanceWarnObject advanceWarnObject = _get(id);
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = advanceWarnObject.getOperationType();
	    if(OperationType.READONLY.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotEdit);
	    }

		return advanceWarnObject;	
	}
	
	/**
	 * 根据主键ID,取得预警对像配置实例副本
	 * @param id
	 * @return
	 */
	public AdvanceWarnObject _getEntityCopy(String id)
	{		
	    AdvanceWarnObject advanceWarnObject = new AdvanceWarnObject();
		AdvanceWarnObject advanceWarnObjectOld = this._get(id);
		try
		{
			BeanUtils.copyProperties(advanceWarnObject, advanceWarnObjectOld);
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (InvocationTargetException e)
		{
			e.printStackTrace();
		}
		//advanceWarnObject.setWarnid(null); 
		return advanceWarnObject;	
	}
	
	/**
	 * 删除  
	 *   
	 * @param advanceWarnObject
	 */
	public void _delete(AdvanceWarnObject advanceWarnObject)
	{
		if (null != advanceService)
			advanceService.preDelete(advanceWarnObject);
	
 		LockService.isBoInstanceLocked(advanceWarnObject,AdvanceWarnObject.class);
		advanceWarnObjectHibernateDao.remove(advanceWarnObject);

		if (null != advanceService)
			advanceService.postDelete(advanceWarnObject);
	}

	/**
	 * 根据主键删除
	 * 
	 * @param advanceWarnObjectId
	 */
	public void _delete(String advanceWarnObjectId,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(advanceWarnObjectId))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("warnid"));
		AdvanceWarnObject advanceWarnObject = this.advanceWarnObjectHibernateDao.load(advanceWarnObjectId);
		_delete(advanceWarnObject);
	}
	
	/**
	 * 删除  
	 *   
	 * @param Set<AdvanceWarnObject> advanceWarnObjects
	 */
	public void _deletes(Set<AdvanceWarnObject> advanceWarnObjects,BusinessObject businessObject)
	{
		if (null == advanceWarnObjects || advanceWarnObjects.size() < 1)
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoText());
		Iterator<AdvanceWarnObject> it = advanceWarnObjects.iterator();
		while (it.hasNext())
		{
			AdvanceWarnObject advanceWarnObject = (AdvanceWarnObject) it.next();
			_delete(advanceWarnObject);
		}
	}
	
	/**
	 * 根据多个主键(由，分割)删除多个对象
	 * 
	 * @param advanceWarnObjectIds
	 */
	public void _deletes(String advanceWarnObjectIds,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(advanceWarnObjectIds))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("warnid"));
		String[] ids = StringUtils.splitString(advanceWarnObjectIds);
		for (int i = 0; i < ids.length; i++)
		{
			_delete(ids[i],businessObject);
		}
	}
	/**
	 * 提交工作流
	 * 
	 * @param advanceWarnObject
	 */
	public void _submitProcess(AdvanceWarnObject advanceWarnObject
,Set<AdvanceWarnRecv> deletedAdvanceWarnReceiverSet
,Set<AdvanceWarnCond> deletedAdvanceWarnConditionSet	,BusinessObject businessObject)
	{
		String id = advanceWarnObject.getWarnid();
		if (StringUtils.isNullBlank(id))
		{
			_save(advanceWarnObject);
		}
		else
		{
			_update(advanceWarnObject
,deletedAdvanceWarnReceiverSet
,deletedAdvanceWarnConditionSet			, businessObject);
		}
		String taskId = advanceWarnObject.getWorkflowTaskId();
		id = advanceWarnObject.getWarnid();
		if (null == taskId || "".equals(taskId))
			WorkflowService.createAndSignalProcessInstance(advanceWarnObject, id);
		else
			WorkflowService.signalProcessInstance(advanceWarnObject, id, null);
	}

	/**
	 * 保存或更新预警对像配置
	 * 保存  
	 *  
	 * @param advanceWarnObject
	 */
	public void _update(AdvanceWarnObject advanceWarnObject
,Set<AdvanceWarnRecv> deletedAdvanceWarnRecvSet
,Set<AdvanceWarnCond> deletedAdvanceWarnCondSet,BusinessObject businessObject	
	)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(advanceWarnObject);
		advanceWarnObjectHibernateDao.saveOrUpdate(advanceWarnObject);
// 删除关联子业务对象数据
if(deletedAdvanceWarnRecvSet!=null && deletedAdvanceWarnRecvSet.size()>0)
{
advanceWarnRecvService._deletes(deletedAdvanceWarnRecvSet,businessObject);
}
// 删除关联子业务对象数据
if(deletedAdvanceWarnCondSet!=null && deletedAdvanceWarnCondSet.size()>0)
{
advanceWarnCondService._deletes(deletedAdvanceWarnCondSet,businessObject);
}		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(advanceWarnObject);
	}
	
	/**
	 * 保存  
	 *   
	 * @param advanceWarnObject
	 */
	public void _save(AdvanceWarnObject advanceWarnObject)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(advanceWarnObject);
		// 对id赋空值，使之执行insert操作(适应复制创建场景)
		advanceWarnObject.setWarnid(null);
       
		Set<AdvanceWarnRecv> advanceWarnReceiverSet = advanceWarnObject.getAdvanceWarnReceiver();
		Set<AdvanceWarnRecv> newAdvanceWarnRecvSet = null;
		if (null != advanceWarnReceiverSet)
		{
			newAdvanceWarnRecvSet = new HashSet();
			Iterator<AdvanceWarnRecv> itAdvanceWarnRecv = advanceWarnReceiverSet.iterator();
			while (itAdvanceWarnRecv.hasNext())
			{
				AdvanceWarnRecv advanceWarnReceiver = (AdvanceWarnRecv) itAdvanceWarnRecv.next();
				advanceWarnReceiver.setReceiveid(null);
				newAdvanceWarnRecvSet.add(advanceWarnReceiver);
			}
		}
		advanceWarnObject.setAdvanceWarnReceiver(newAdvanceWarnRecvSet);
     
       
     
		Set<AdvanceWarnCond> advanceWarnConditionSet = advanceWarnObject.getAdvanceWarnCondition();
		Set<AdvanceWarnCond> newAdvanceWarnCondSet = null;
		if (null != advanceWarnConditionSet)
		{
			newAdvanceWarnCondSet = new HashSet();
			Iterator<AdvanceWarnCond> itAdvanceWarnCond = advanceWarnConditionSet.iterator();
			while (itAdvanceWarnCond.hasNext())
			{
				AdvanceWarnCond advanceWarnCondition = (AdvanceWarnCond) itAdvanceWarnCond.next();
				advanceWarnCondition.setConditionid(null);
				newAdvanceWarnCondSet.add(advanceWarnCondition);
			}
		}
		advanceWarnObject.setAdvanceWarnCondition(newAdvanceWarnCondSet);
       
     
       
     
       
     
       
     
       
     
       
     
       
     
       
     
       
     
       
     
       
     
       
     
       
     
       
     
       
     
       
     
       
     
		advanceWarnObjectHibernateDao.saveOrUpdate(advanceWarnObject);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(advanceWarnObject);
	}
	
	/**
	 *
	 * 保存  
	 *   
	 * @param advanceWarnObject
	 */
	public void _saveOrUpdate(AdvanceWarnObject advanceWarnObject
,Set<AdvanceWarnRecv> deletedAdvanceWarnRecvSet
,Set<AdvanceWarnCond> deletedAdvanceWarnCondSet,BusinessObject businessObject		
	)
	{
		if (StringUtils.isNullBlank(advanceWarnObject.getWarnid()))
		{	
			_save(advanceWarnObject);
		}
		else
		{
			_update(advanceWarnObject
,deletedAdvanceWarnRecvSet
,deletedAdvanceWarnCondSet
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