/*
 * @(#)TestProjectServiceGen.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年04月21日 15点06分40秒
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
import com.infolion.TEMP.Temp.domain.TestProject;
import com.infolion.TEMP.Temp.service.TestProjectService;
import com.infolion.TEMP.Temp.dao.TestProjectHibernateDao;

/**
 * <pre>
 * 项目（测试）(TestProject)服务类
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
public class TestProjectServiceGen extends BaseService
{
	protected Log log = LogFactory.getFactory().getLog(this.getClass());

	@Autowired
	protected TestProjectHibernateDao testProjectHibernateDao;
	
	public void setTestProjectHibernateDao(TestProjectHibernateDao testProjectHibernateDao)
	{
		this.testProjectHibernateDao = testProjectHibernateDao;
	}
	
     /**
	 * 注入扩展服务类
	 */
	 @Autowired(required = false)
	 public void setAdvanceService(@Qualifier("testProjectAdvanceService") AdvanceService advanceService)
	 {
	  	super.setAdvanceService(advanceService);
	 }   
	 
	   
	/**
	 * 根据主键ID,取得项目（测试）实例
	 * @param id
	 * @return
	 */
	public TestProject _getDetached(String id)
	{		
	    TestProject testProject = new TestProject();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  testProject = testProjectHibernateDao.getDetached(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(testProject);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    testProject.setOperationType(operationType);
	    
		return testProject;	
	}
	
	/**
	 * 根据主键ID,取得项目（测试）实例
	 * @param id
	 * @return
	 */
	public TestProject _get(String id)
	{		
	    TestProject testProject = new TestProject();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  testProject = testProjectHibernateDao.get(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(testProject);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    testProject.setOperationType(operationType);
	    
		return testProject;	
	}
	
	/**
	 * 根据主键ID,取得项目（测试）实例
	 * @param id
	 * @return
	 */
	public TestProject _getForEdit(String id)
	{		
	    TestProject testProject = _get(id);
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = testProject.getOperationType();
	    if(OperationType.READONLY.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotEdit);
	    }

		return testProject;	
	}
	
	/**
	 * 根据主键ID,取得项目（测试）实例副本
	 * @param id
	 * @return
	 */
	public TestProject _getEntityCopy(String id)
	{		
	    TestProject testProject = new TestProject();
		TestProject testProjectOld = this._get(id);
		try
		{
			BeanUtils.copyProperties(testProject, testProjectOld);
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (InvocationTargetException e)
		{
			e.printStackTrace();
		}
		//testProject.setProjectid(null); 
		testProject.setProcessstate(" ");
		return testProject;	
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
	 * @param testProject
	 */
	public void _delete(TestProject testProject)
	{
		if (null != advanceService)
			advanceService.preDelete(testProject);
	
		//流程状态
		String processState =testProject.getProcessstate();
		if(!StringUtils.isNullBlank(processState))
		{
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.CannotDeleteApproveData);
		}
 		LockService.isBoInstanceLocked(testProject,TestProject.class);
		testProjectHibernateDao.remove(testProject);

		if (null != advanceService)
			advanceService.postDelete(testProject);
	}

	/**
	 * 根据主键删除
	 * 
	 * @param testProjectId
	 */
	public void _delete(String testProjectId,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(testProjectId))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("projectid"));
		TestProject testProject = this.testProjectHibernateDao.load(testProjectId);
		_delete(testProject);
	}
	
	/**
	 * 删除  
	 *   
	 * @param Set<TestProject> testProjects
	 */
	public void _deletes(Set<TestProject> testProjects,BusinessObject businessObject)
	{
		if (null == testProjects || testProjects.size() < 1)
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoText());
		Iterator<TestProject> it = testProjects.iterator();
		while (it.hasNext())
		{
			TestProject testProject = (TestProject) it.next();
			_delete(testProject);
		}
	}
	
	/**
	 * 根据多个主键(由，分割)删除多个对象
	 * 
	 * @param testProjectIds
	 */
	public void _deletes(String testProjectIds,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(testProjectIds))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("projectid"));
		String[] ids = StringUtils.splitString(testProjectIds);
		for (int i = 0; i < ids.length; i++)
		{
			_delete(ids[i],businessObject);
		}
	}
	/**
	 * 根据方法参数,取得项目（测试）实例
	 * @param id
	 * @return 项目（测试）实例
	 */
     public TestProject _view(
String projectid
)     {
       TestProject testProject = testProjectHibernateDao. _view(
projectid
);	   if(testProject!=null)
	   {	
		   //以下代码需要放在service服务同一方法，要不存在事务问题。
		   String operationType = LockService.lockBOData(testProject);
		   if(OperationType.UNVISIABLE.equals(operationType))
		   {
			  throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
		   }
		   testProject.setOperationType(operationType);
	   }
	   
       return  testProject ;
     }
	/**
	 * 提交工作流
	 * 
	 * @param testProject
	 */
	public void _submitProcess(TestProject testProject
	,BusinessObject businessObject)
	{
		String id = testProject.getProjectid();
		if (StringUtils.isNullBlank(id))
		{
			_save(testProject);
		}
		else
		{
			_update(testProject
			, businessObject);
		}
		String taskId = testProject.getWorkflowTaskId();
		id = testProject.getProjectid();
		if (null == taskId || "".equals(taskId))
			WorkflowService.createAndSignalProcessInstance(testProject, id);
		else
			WorkflowService.signalProcessInstance(testProject, id, null);
	}

	/**
	 * 保存或更新项目（测试）
	 * 保存  
	 *  
	 * @param testProject
	 */
	public void _update(TestProject testProject
,BusinessObject businessObject	
	)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(testProject);
		testProjectHibernateDao.saveOrUpdate(testProject);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(testProject);
	}
	
	/**
	 * 保存  
	 *   
	 * @param testProject
	 */
	public void _save(TestProject testProject)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(testProject);
		// 对id赋空值，使之执行insert操作(适应复制创建场景)
		testProject.setProjectid(null);
              		testProjectHibernateDao.saveOrUpdate(testProject);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(testProject);
	}
	
	/**
	 *
	 * 保存  
	 *   
	 * @param testProject
	 */
	public void _saveOrUpdate(TestProject testProject
,BusinessObject businessObject		
	)
	{
		if (StringUtils.isNullBlank(testProject.getProjectid()))
		{	
			_save(testProject);
		}
		else
		{
			_update(testProject
, businessObject);
}	}
	
}