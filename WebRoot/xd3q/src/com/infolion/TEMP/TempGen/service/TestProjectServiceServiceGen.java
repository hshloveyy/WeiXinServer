/*
 * @(#)TestProjectServiceServiceGen.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年04月21日 15点06分42秒
 *  描　述：创建
 */
package com.infolion.TEMP.TempGen.service;

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
import com.infolion.TEMP.Temp.domain.TestProjectService;
import com.infolion.TEMP.Temp.service.TestProjectServiceService;
import com.infolion.TEMP.Temp.dao.TestProjectServiceHibernateDao;

/**
 * <pre>
 * 项目服务（测试）(TestProjectService)服务类
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
public class TestProjectServiceServiceGen extends BaseService
{
	protected Log log = LogFactory.getFactory().getLog(this.getClass());

	@Autowired
	protected TestProjectServiceHibernateDao testProjectServiceHibernateDao;
	
	public void setTestProjectServiceHibernateDao(TestProjectServiceHibernateDao testProjectServiceHibernateDao)
	{
		this.testProjectServiceHibernateDao = testProjectServiceHibernateDao;
	}
	
     /**
	 * 注入扩展服务类
	 */
	 @Autowired(required = false)
	 public void setAdvanceService(@Qualifier("testProjectServiceAdvanceService") AdvanceService advanceService)
	 {
	  	super.setAdvanceService(advanceService);
	 }   
	 
	   
	/**
	 * 根据主键ID,取得项目服务（测试）实例
	 * @param id
	 * @return
	 */
	public TestProjectService _getDetached(String id)
	{		
	    TestProjectService testProjectService = new TestProjectService();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  testProjectService = testProjectServiceHibernateDao.getDetached(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(testProjectService);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    testProjectService.setOperationType(operationType);
	    
		return testProjectService;	
	}
	
	/**
	 * 根据主键ID,取得项目服务（测试）实例
	 * @param id
	 * @return
	 */
	public TestProjectService _get(String id)
	{		
	    TestProjectService testProjectService = new TestProjectService();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  testProjectService = testProjectServiceHibernateDao.get(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(testProjectService);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    testProjectService.setOperationType(operationType);
	    
		return testProjectService;	
	}
	
	/**
	 * 根据主键ID,取得项目服务（测试）实例
	 * @param id
	 * @return
	 */
	public TestProjectService _getForEdit(String id)
	{		
	    TestProjectService testProjectService = _get(id);
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = testProjectService.getOperationType();
	    if(OperationType.READONLY.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotEdit);
	    }

		return testProjectService;	
	}
	
	/**
	 * 根据主键ID,取得项目服务（测试）实例副本
	 * @param id
	 * @return
	 */
	public TestProjectService _getEntityCopy(String id)
	{		
	    TestProjectService testProjectService = new TestProjectService();
		TestProjectService testProjectServiceOld = this._get(id);
		try
		{
			BeanUtils.copyProperties(testProjectService, testProjectServiceOld);
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (InvocationTargetException e)
		{
			e.printStackTrace();
		}
		//testProjectService.setTsid(null); 
		testProjectService.setProcessstate(" ");
		return testProjectService;	
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
	 * @param testProjectService
	 */
	public void _delete(TestProjectService testProjectService)
	{
		if (null != advanceService)
			advanceService.preDelete(testProjectService);
	
		//流程状态
		String processState =testProjectService.getProcessstate();
		if(!StringUtils.isNullBlank(processState))
		{
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.CannotDeleteApproveData);
		}
 		LockService.isBoInstanceLocked(testProjectService,TestProjectService.class);
		testProjectServiceHibernateDao.remove(testProjectService);

		if (null != advanceService)
			advanceService.postDelete(testProjectService);
	}

	/**
	 * 根据主键删除
	 * 
	 * @param testProjectServiceId
	 */
	public void _delete(String testProjectServiceId,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(testProjectServiceId))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("tsid"));
		TestProjectService testProjectService = this.testProjectServiceHibernateDao.load(testProjectServiceId);
		_delete(testProjectService);
	}
	
	/**
	 * 删除  
	 *   
	 * @param Set<TestProjectService> testProjectServices
	 */
	public void _deletes(Set<TestProjectService> testProjectServices,BusinessObject businessObject)
	{
		if (null == testProjectServices || testProjectServices.size() < 1)
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoText());
		Iterator<TestProjectService> it = testProjectServices.iterator();
		while (it.hasNext())
		{
			TestProjectService testProjectService = (TestProjectService) it.next();
			_delete(testProjectService);
		}
	}
	
	/**
	 * 根据多个主键(由，分割)删除多个对象
	 * 
	 * @param testProjectServiceIds
	 */
	public void _deletes(String testProjectServiceIds,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(testProjectServiceIds))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("tsid"));
		String[] ids = StringUtils.splitString(testProjectServiceIds);
		for (int i = 0; i < ids.length; i++)
		{
			_delete(ids[i],businessObject);
		}
	}
	/**
	 * 根据方法参数,取得项目服务（测试）实例
	 * @param id
	 * @return 项目服务（测试）实例
	 */
     public TestProjectService _view(
String projectid
)     {
       TestProjectService testProjectService = testProjectServiceHibernateDao. _view(
projectid
);	   if(testProjectService!=null)
	   {	
		   //以下代码需要放在service服务同一方法，要不存在事务问题。
		   String operationType = LockService.lockBOData(testProjectService);
		   if(OperationType.UNVISIABLE.equals(operationType))
		   {
			  throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
		   }
		   testProjectService.setOperationType(operationType);
	   }
	   
       return  testProjectService ;
     }
	/**
	 * 提交工作流
	 * 
	 * @param testProjectService
	 */
	public void _submitProcess(TestProjectService testProjectService
	,BusinessObject businessObject)
	{
		String id = testProjectService.getTsid();
		if (StringUtils.isNullBlank(id))
		{
			_save(testProjectService);
		}
		else
		{
			_update(testProjectService
			, businessObject);
		}
		String taskId = testProjectService.getWorkflowTaskId();
		id = testProjectService.getTsid();
		if (null == taskId || "".equals(taskId))
			WorkflowService.createAndSignalProcessInstance(testProjectService, id);
		else
			WorkflowService.signalProcessInstance(testProjectService, id, null);
	}

	/**
	 * 保存或更新项目服务（测试）
	 * 保存  
	 *  
	 * @param testProjectService
	 */
	public void _update(TestProjectService testProjectService
,BusinessObject businessObject	
	)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(testProjectService);
		testProjectServiceHibernateDao.saveOrUpdate(testProjectService);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(testProjectService);
	}
	
	/**
	 * 保存  
	 *   
	 * @param testProjectService
	 */
	public void _save(TestProjectService testProjectService)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(testProjectService);
		// 对id赋空值，使之执行insert操作(适应复制创建场景)
		testProjectService.setTsid(null);
                		testProjectServiceHibernateDao.saveOrUpdate(testProjectService);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(testProjectService);
	}
	
	/**
	 *
	 * 保存  
	 *   
	 * @param testProjectService
	 */
	public void _saveOrUpdate(TestProjectService testProjectService
,BusinessObject businessObject		
	)
	{
		if (StringUtils.isNullBlank(testProjectService.getTsid()))
		{	
			_save(testProjectService);
		}
		else
		{
			_update(testProjectService
, businessObject);
}	}
	
}