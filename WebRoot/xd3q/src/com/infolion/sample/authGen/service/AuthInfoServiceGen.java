/*
 * @(#)AuthInfoServiceGen.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年03月15日 19点35分41秒
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
import com.infolion.sample.auth.domain.AuthInfo;
import com.infolion.sample.auth.service.AuthInfoService;
import com.infolion.sample.auth.dao.AuthInfoHibernateDao;

/**
 * <pre>
 * AuthInfo(AuthInfo)服务类
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
public class AuthInfoServiceGen extends BaseService
{
	protected Log log = LogFactory.getFactory().getLog(this.getClass());

	@Autowired
	protected AuthInfoHibernateDao authInfoHibernateDao;
	
	public void setAuthInfoHibernateDao(AuthInfoHibernateDao authInfoHibernateDao)
	{
		this.authInfoHibernateDao = authInfoHibernateDao;
	}
	
     /**
	 * 注入扩展服务类
	 */
	 @Autowired(required = false)
	 public void setAdvanceService(@Qualifier("authInfoAdvanceService") AdvanceService advanceService)
	 {
	  	super.setAdvanceService(advanceService);
	 }   
	 
	   
	/**
	 * 根据主键ID,取得AuthInfo实例
	 * @param id
	 * @return
	 */
	public AuthInfo _getDetached(String id)
	{		
	    AuthInfo authInfo = new AuthInfo();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  authInfo = authInfoHibernateDao.getDetached(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(authInfo);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    authInfo.setOperationType(operationType);
	    
		return authInfo;	
	}
	
	/**
	 * 根据主键ID,取得AuthInfo实例
	 * @param id
	 * @return
	 */
	public AuthInfo _get(String id)
	{		
	    AuthInfo authInfo = new AuthInfo();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  authInfo = authInfoHibernateDao.get(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(authInfo);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    authInfo.setOperationType(operationType);
	    
		return authInfo;	
	}
	
	/**
	 * 根据主键ID,取得AuthInfo实例
	 * @param id
	 * @return
	 */
	public AuthInfo _getForEdit(String id)
	{		
	    AuthInfo authInfo = _get(id);
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = authInfo.getOperationType();
	    if(OperationType.READONLY.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotEdit);
	    }

		return authInfo;	
	}
	
	/**
	 * 根据主键ID,取得AuthInfo实例副本
	 * @param id
	 * @return
	 */
	public AuthInfo _getEntityCopy(String id)
	{		
	    AuthInfo authInfo = new AuthInfo();
		AuthInfo authInfoOld = this._get(id);
		try
		{
			BeanUtils.copyProperties(authInfo, authInfoOld);
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (InvocationTargetException e)
		{
			e.printStackTrace();
		}
		authInfo.setAuthinfoid(null); 
		return authInfo;	
	}
	
	/**
	 * 删除  
	 *   
	 * @param Set<AuthInfo> authInfos
	 */
	public void _deletes(Set<AuthInfo> authInfos,BusinessObject businessObject)
	{
		if (null == authInfos || authInfos.size() < 1)
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoText());
		Iterator<AuthInfo> it = authInfos.iterator();
		while (it.hasNext())
		{
			AuthInfo authInfo = (AuthInfo) it.next();
			_delete(authInfo);
		}
	}
	
	/**
	 * 根据多个主键(由，分割)删除多个对象
	 * 
	 * @param authInfoIds
	 */
	public void _deletes(String authInfoIds,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(authInfoIds))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("authinfoid"));
		String[] ids = StringUtils.splitString(authInfoIds);
		for (int i = 0; i < ids.length; i++)
		{
			_delete(ids[i],businessObject);
		}
	}
	/**
	 * 提交工作流
	 * 
	 * @param authInfo
	 */
	public void _submitProcess(AuthInfo authInfo
	,BusinessObject businessObject)
	{
		String id = authInfo.getAuthinfoid();
		if (StringUtils.isNullBlank(id))
		{
			_save(authInfo);
		}
		else
		{
			_update(authInfo
			, businessObject);
		}
		String taskId = authInfo.getWorkflowTaskId();
		if (null == taskId || "".equals(taskId))
			WorkflowService.createAndSignalProcessInstance(authInfo, id);
		else
			WorkflowService.signalProcessInstance(authInfo, id, null);
	}

	/**
	 * 保存或更新AuthInfo
	 * 保存  
	 *  
	 * @param authInfo
	 */
	public void _update(AuthInfo authInfo
,BusinessObject businessObject	
	)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(authInfo);
		authInfoHibernateDao.saveOrUpdate(authInfo);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(authInfo);
	}
	
	/**
	 * 保存  
	 *   
	 * @param authInfo
	 */
	public void _save(AuthInfo authInfo)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(authInfo);
		// 对id赋空值，使之执行insert操作(适应复制创建场景)
		authInfo.setAuthinfoid(null);
      		authInfoHibernateDao.saveOrUpdate(authInfo);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(authInfo);
	}
	
	/**
	 *
	 * 保存  
	 *   
	 * @param authInfo
	 */
	public void _saveOrUpdate(AuthInfo authInfo
,BusinessObject businessObject		
	)
	{
		if (StringUtils.isNullBlank(authInfo.getAuthinfoid()))
		{	
			_save(authInfo);
		}
		else
		{
			_update(authInfo
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
	
	/**
	 * 删除  
	 *   
	 * @param authInfo
	 */
	public void _delete(AuthInfo authInfo)
	{
		if (null != advanceService)
			advanceService.preDelete(authInfo);
	
 		LockService.isBoInstanceLocked(authInfo,AuthInfo.class);
		authInfoHibernateDao.remove(authInfo);

		if (null != advanceService)
			advanceService.postDelete(authInfo);
	}

	/**
	 * 根据主键删除
	 * 
	 * @param authInfoId
	 */
	public void _delete(String authInfoId,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(authInfoId))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("authinfoid"));
		AuthInfo authInfo = this.authInfoHibernateDao.load(authInfoId);
		_delete(authInfo);
	}
}