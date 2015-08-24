/*
 * @(#)AuthItemServiceGen.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年03月15日 19点35分40秒
 *  描　述：创建
 */
package com.infolion.sample.authGen.service;

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
import com.infolion.sample.auth.domain.AuthItem;
import com.infolion.sample.auth.service.AuthItemService;
import com.infolion.sample.auth.dao.AuthItemHibernateDao;

/**
 * <pre>
 * AuthInfo(AuthItem)服务类
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
public class AuthItemServiceGen extends BaseService
{
	protected Log log = LogFactory.getFactory().getLog(this.getClass());

	@Autowired
	protected AuthItemHibernateDao authItemHibernateDao;
	
	public void setAuthItemHibernateDao(AuthItemHibernateDao authItemHibernateDao)
	{
		this.authItemHibernateDao = authItemHibernateDao;
	}
	
     /**
	 * 注入扩展服务类
	 */
	 @Autowired(required = false)
	 public void setAdvanceService(@Qualifier("authItemAdvanceService") AdvanceService advanceService)
	 {
	  	super.setAdvanceService(advanceService);
	 }   
	 
	   
	/**
	 * 根据主键ID,取得AuthInfo实例
	 * @param id
	 * @return
	 */
	public AuthItem _getDetached(String id)
	{		
	    AuthItem authItem = new AuthItem();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  authItem = authItemHibernateDao.getDetached(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(authItem);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    authItem.setOperationType(operationType);
	    
		return authItem;	
	}
	
	/**
	 * 根据主键ID,取得AuthInfo实例
	 * @param id
	 * @return
	 */
	public AuthItem _get(String id)
	{		
	    AuthItem authItem = new AuthItem();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  authItem = authItemHibernateDao.get(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(authItem);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    authItem.setOperationType(operationType);
	    
		return authItem;	
	}
	
	/**
	 * 根据主键ID,取得AuthInfo实例
	 * @param id
	 * @return
	 */
	public AuthItem _getForEdit(String id)
	{		
	    AuthItem authItem = _get(id);
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = authItem.getOperationType();
	    if(OperationType.READONLY.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotEdit);
	    }

		return authItem;	
	}
	
	/**
	 * 根据主键ID,取得AuthInfo实例副本
	 * @param id
	 * @return
	 */
	public AuthItem _getEntityCopy(String id)
	{		
	    AuthItem authItem = new AuthItem();
		AuthItem authItemOld = this._get(id);
		try
		{
			BeanUtils.copyProperties(authItem, authItemOld);
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (InvocationTargetException e)
		{
			e.printStackTrace();
		}
		authItem.setAuthinfoid(null); 
		return authItem;	
	}
	
	/**
	 * 删除  
	 *   
	 * @param authItem
	 */
	public void _delete(AuthItem authItem)
	{
		if (null != advanceService)
			advanceService.preDelete(authItem);
	
 		LockService.isBoInstanceLocked(authItem,AuthItem.class);
		authItemHibernateDao.remove(authItem);

		if (null != advanceService)
			advanceService.postDelete(authItem);
	}

	/**
	 * 根据主键删除
	 * 
	 * @param authItemId
	 */
	public void _delete(String authItemId,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(authItemId))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("authinfoid"));
		AuthItem authItem = this.authItemHibernateDao.load(authItemId);
		_delete(authItem);
	}
	
	/**
	 * 删除  
	 *   
	 * @param Set<AuthItem> authItems
	 */
	public void _deletes(Set<AuthItem> authItems,BusinessObject businessObject)
	{
		if (null == authItems || authItems.size() < 1)
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoText());
		Iterator<AuthItem> it = authItems.iterator();
		while (it.hasNext())
		{
			AuthItem authItem = (AuthItem) it.next();
			_delete(authItem);
		}
	}
	
	/**
	 * 根据多个主键(由，分割)删除多个对象
	 * 
	 * @param authItemIds
	 */
	public void _deletes(String authItemIds,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(authItemIds))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("authinfoid"));
		String[] ids = StringUtils.splitString(authItemIds);
		for (int i = 0; i < ids.length; i++)
		{
			_delete(ids[i],businessObject);
		}
	}
	/**
	 * 提交工作流
	 * 
	 * @param authItem
	 */
	public void _submitProcess(AuthItem authItem
	,BusinessObject businessObject)
	{
		String id = authItem.getAuthinfoid();
		if (StringUtils.isNullBlank(id))
		{
			_save(authItem);
		}
		else
		{
			_update(authItem
			, businessObject);
		}
		String taskId = authItem.getWorkflowTaskId();
		if (null == taskId || "".equals(taskId))
			WorkflowService.createAndSignalProcessInstance(authItem, id);
		else
			WorkflowService.signalProcessInstance(authItem, id, null);
	}

	/**
	 * 保存或更新AuthInfo
	 * 保存  
	 *  
	 * @param authItem
	 */
	public void _update(AuthItem authItem
,BusinessObject businessObject	
	)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(authItem);
		authItemHibernateDao.saveOrUpdate(authItem);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(authItem);
	}
	
	/**
	 * 保存  
	 *   
	 * @param authItem
	 */
	public void _save(AuthItem authItem)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(authItem);
		// 对id赋空值，使之执行insert操作(适应复制创建场景)
		authItem.setAuthinfoid(null);
      		authItemHibernateDao.saveOrUpdate(authItem);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(authItem);
	}
	
	/**
	 *
	 * 保存  
	 *   
	 * @param authItem
	 */
	public void _saveOrUpdate(AuthItem authItem
,BusinessObject businessObject		
	)
	{
		if (StringUtils.isNullBlank(authItem.getAuthinfoid()))
		{	
			_save(authItem);
		}
		else
		{
			_update(authItem
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