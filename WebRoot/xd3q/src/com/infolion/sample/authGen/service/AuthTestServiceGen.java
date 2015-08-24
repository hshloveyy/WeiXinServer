/*
 * @(#)AuthTestServiceGen.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年03月16日 09点05分47秒
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
import com.infolion.sample.auth.domain.AuthTest;
import com.infolion.sample.auth.service.AuthTestService;
import com.infolion.sample.auth.dao.AuthTestHibernateDao;

/**
 * <pre>
 * AuthTest(AuthTest)服务类
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
public class AuthTestServiceGen extends BaseService
{
	protected Log log = LogFactory.getFactory().getLog(this.getClass());

	@Autowired
	protected AuthTestHibernateDao authTestHibernateDao;
	
	public void setAuthTestHibernateDao(AuthTestHibernateDao authTestHibernateDao)
	{
		this.authTestHibernateDao = authTestHibernateDao;
	}
	
     /**
	 * 注入扩展服务类
	 */
	 @Autowired(required = false)
	 public void setAdvanceService(@Qualifier("authTestAdvanceService") AdvanceService advanceService)
	 {
	  	super.setAdvanceService(advanceService);
	 }   
	 
	   
	/**
	 * 根据主键ID,取得AuthTest实例
	 * @param id
	 * @return
	 */
	public AuthTest _getDetached(String id)
	{		
	    AuthTest authTest = new AuthTest();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  authTest = authTestHibernateDao.getDetached(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(authTest);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    authTest.setOperationType(operationType);
	    
		return authTest;	
	}
	
	/**
	 * 根据主键ID,取得AuthTest实例
	 * @param id
	 * @return
	 */
	public AuthTest _get(String id)
	{		
	    AuthTest authTest = new AuthTest();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  authTest = authTestHibernateDao.get(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(authTest);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    authTest.setOperationType(operationType);
	    
		return authTest;	
	}
	
	/**
	 * 根据主键ID,取得AuthTest实例
	 * @param id
	 * @return
	 */
	public AuthTest _getForEdit(String id)
	{		
	    AuthTest authTest = _get(id);
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = authTest.getOperationType();
	    if(OperationType.READONLY.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotEdit);
	    }

		return authTest;	
	}
	
	/**
	 * 根据主键ID,取得AuthTest实例副本
	 * @param id
	 * @return
	 */
	public AuthTest _getEntityCopy(String id)
	{		
	    AuthTest authTest = new AuthTest();
		AuthTest authTestOld = this._get(id);
		try
		{
			BeanUtils.copyProperties(authTest, authTestOld);
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (InvocationTargetException e)
		{
			e.printStackTrace();
		}
		authTest.setAuthresourceid(null); 
		return authTest;	
	}
	
	/**
	 * 删除  
	 *   
	 * @param authTest
	 */
	public void _delete(AuthTest authTest)
	{
		if (null != advanceService)
			advanceService.preDelete(authTest);
	
 		LockService.isBoInstanceLocked(authTest,AuthTest.class);
		authTestHibernateDao.remove(authTest);

		if (null != advanceService)
			advanceService.postDelete(authTest);
	}

	/**
	 * 根据主键删除
	 * 
	 * @param authTestId
	 */
	public void _delete(String authTestId,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(authTestId))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("authresourceid"));
		AuthTest authTest = this.authTestHibernateDao.load(authTestId);
		_delete(authTest);
	}
	
	/**
	 * 删除  
	 *   
	 * @param Set<AuthTest> authTests
	 */
	public void _deletes(Set<AuthTest> authTests,BusinessObject businessObject)
	{
		if (null == authTests || authTests.size() < 1)
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoText());
		Iterator<AuthTest> it = authTests.iterator();
		while (it.hasNext())
		{
			AuthTest authTest = (AuthTest) it.next();
			_delete(authTest);
		}
	}
	
	/**
	 * 根据多个主键(由，分割)删除多个对象
	 * 
	 * @param authTestIds
	 */
	public void _deletes(String authTestIds,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(authTestIds))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("authresourceid"));
		String[] ids = StringUtils.splitString(authTestIds);
		for (int i = 0; i < ids.length; i++)
		{
			_delete(ids[i],businessObject);
		}
	}
	/**
	 * 提交工作流
	 * 
	 * @param authTest
	 */
	public void _submitProcess(AuthTest authTest
	,BusinessObject businessObject)
	{
		String id = authTest.getAuthresourceid();
		if (StringUtils.isNullBlank(id))
		{
			_save(authTest);
		}
		else
		{
			_update(authTest
			, businessObject);
		}
		String taskId = authTest.getWorkflowTaskId();
		if (null == taskId || "".equals(taskId))
			WorkflowService.createAndSignalProcessInstance(authTest, id);
		else
			WorkflowService.signalProcessInstance(authTest, id, null);
	}

	/**
	 * 保存或更新AuthTest
	 * 保存  
	 *  
	 * @param authTest
	 */
	public void _update(AuthTest authTest
,BusinessObject businessObject	
	)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(authTest);
		authTestHibernateDao.saveOrUpdate(authTest);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(authTest);
	}
	
	/**
	 * 保存  
	 *   
	 * @param authTest
	 */
	public void _save(AuthTest authTest)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(authTest);
		// 对id赋空值，使之执行insert操作(适应复制创建场景)
		authTest.setAuthresourceid(null);
                          		authTestHibernateDao.saveOrUpdate(authTest);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(authTest);
	}
	
	/**
	 *
	 * 保存  
	 *   
	 * @param authTest
	 */
	public void _saveOrUpdate(AuthTest authTest
,BusinessObject businessObject		
	)
	{
		if (StringUtils.isNullBlank(authTest.getAuthresourceid()))
		{	
			_save(authTest);
		}
		else
		{
			_update(authTest
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