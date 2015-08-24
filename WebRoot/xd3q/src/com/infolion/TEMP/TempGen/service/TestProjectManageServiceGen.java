/*
 * @(#)TestProjectManageServiceGen.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年04月09日 17点07分38秒
 *  描　述：创建
 */
package com.infolion.TEMP.TempGen.service;

import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.infolion.TEMP.Temp.dao.TestProjectManageHibernateDao;
import com.infolion.TEMP.Temp.domain.TestProjectManage;
import com.infolion.platform.basicApp.authManage.domain.OperationType;
import com.infolion.platform.bo.domain.BusinessObject;
import com.infolion.platform.dpframework.core.BusinessException;
import com.infolion.platform.dpframework.core.service.AdvanceService;
import com.infolion.platform.dpframework.core.service.BaseService;
import com.infolion.platform.dpframework.uicomponent.lock.LockException;
import com.infolion.platform.dpframework.uicomponent.lock.LockService;
import com.infolion.platform.dpframework.uicomponent.systemMessage.SysMsgConstants;
import com.infolion.platform.dpframework.util.StringUtils;
import com.infolion.platform.workflow.engine.WorkflowService;

/**
 * <pre>
 * 项目管理（测试）(TestProjectManage)服务类
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
public class TestProjectManageServiceGen extends BaseService
{
	protected Log log = LogFactory.getFactory().getLog(this.getClass());

	@Autowired
	protected TestProjectManageHibernateDao testProjectManageHibernateDao;

	public void setTestProjectManageHibernateDao(TestProjectManageHibernateDao testProjectManageHibernateDao)
	{
		this.testProjectManageHibernateDao = testProjectManageHibernateDao;
	}

	/**
	 * 注入扩展服务类
	 */
	@Autowired(required = false)
	public void setAdvanceService(@Qualifier("testProjectManageAdvanceService") AdvanceService advanceService)
	{
		super.setAdvanceService(advanceService);
	}

	/**
	 * 根据主键ID,取得项目管理（测试）实例
	 * 
	 * @param id
	 * @return
	 */
	public TestProjectManage _getDetached(String id)
	{
		TestProjectManage testProjectManage = new TestProjectManage();
		if (StringUtils.isNotBlank(id))
		{
			testProjectManage = testProjectManageHibernateDao.getDetached(id);
		}
		// 需要转移到service服务里，要不存在事务问题。
		String operationType = LockService.lockBOData(testProjectManage);
		if (OperationType.UNVISIABLE.equals(operationType))
		{
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
		}
		testProjectManage.setOperationType(operationType);

		return testProjectManage;
	}

	/**
	 * 根据主键ID,取得项目管理（测试）实例
	 * 
	 * @param id
	 * @return
	 */
	public TestProjectManage _get(String id)
	{
		TestProjectManage testProjectManage = new TestProjectManage();
		if (StringUtils.isNotBlank(id))
		{
			testProjectManage = testProjectManageHibernateDao.get(id);
		}
		// 需要转移到service服务里，要不存在事务问题。
		String operationType = LockService.lockBOData(testProjectManage);
		if (OperationType.UNVISIABLE.equals(operationType))
		{
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
		}
		testProjectManage.setOperationType(operationType);

		return testProjectManage;
	}

	/**
	 * 根据主键ID,取得项目管理（测试）实例
	 * 
	 * @param id
	 * @return
	 */
	public TestProjectManage _getForEdit(String id)
	{
		TestProjectManage testProjectManage = _get(id);
		// 需要转移到service服务里，要不存在事务问题。
		String operationType = testProjectManage.getOperationType();
		if (OperationType.READONLY.equals(operationType))
		{
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotEdit);
		}

		return testProjectManage;
	}

	/**
	 * 根据主键ID,取得项目管理（测试）实例副本
	 * 
	 * @param id
	 * @return
	 */
	public TestProjectManage _getEntityCopy(String id)
	{
		TestProjectManage testProjectManage = new TestProjectManage();
		TestProjectManage testProjectManageOld = this._get(id);
		try
		{
			BeanUtils.copyProperties(testProjectManage, testProjectManageOld);
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (InvocationTargetException e)
		{
			e.printStackTrace();
		}
		// testProjectManage.setProjectid(null);
		testProjectManage.setProcessstate(" ");
		return testProjectManage;
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
	 * @param testProjectManage
	 */
	public void _delete(TestProjectManage testProjectManage)
	{
		if (null != advanceService)
			advanceService.preDelete(testProjectManage);

		// 流程状态
		String processState = testProjectManage.getProcessstate();
		if (!StringUtils.isNullBlank(processState))
		{
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.CannotDeleteApproveData);
		}
		LockService.isBoInstanceLocked(testProjectManage, TestProjectManage.class);
		testProjectManageHibernateDao.remove(testProjectManage);

		if (null != advanceService)
			advanceService.postDelete(testProjectManage);
	}

	/**
	 * 根据主键删除
	 * 
	 * @param testProjectManageId
	 */
	public void _delete(String testProjectManageId, BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(testProjectManageId))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("projectid"));
		TestProjectManage testProjectManage = this.testProjectManageHibernateDao.load(testProjectManageId);
		_delete(testProjectManage);
	}

	/**
	 * 删除
	 * 
	 * @param Set
	 *            <TestProjectManage> testProjectManages
	 */
	public void _deletes(Set<TestProjectManage> testProjectManages, BusinessObject businessObject)
	{
		if (null == testProjectManages || testProjectManages.size() < 1)
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoText());
		Iterator<TestProjectManage> it = testProjectManages.iterator();
		while (it.hasNext())
		{
			TestProjectManage testProjectManage = (TestProjectManage) it.next();
			_delete(testProjectManage);
		}
	}

	/**
	 * 根据多个主键(由，分割)删除多个对象
	 * 
	 * @param testProjectManageIds
	 */
	public void _deletes(String testProjectManageIds, BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(testProjectManageIds))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("projectid"));
		String[] ids = StringUtils.splitString(testProjectManageIds);
		for (int i = 0; i < ids.length; i++)
		{
			_delete(ids[i], businessObject);
		}
	}

	/**
	 * 提交工作流
	 * 
	 * @param testProjectManage
	 */
	public void _submitProcess(TestProjectManage testProjectManage, BusinessObject businessObject)
	{
		String id = testProjectManage.getProjectid();
		if (StringUtils.isNullBlank(id))
		{
			_save(testProjectManage);
		}
		else
		{
			_update(testProjectManage, businessObject);
		}
		String taskId = testProjectManage.getWorkflowTaskId();
		if (null == taskId || "".equals(taskId))
			WorkflowService.createAndSignalProcessInstance(testProjectManage, testProjectManage.getProjectid());
		else
			WorkflowService.signalProcessInstance(testProjectManage, testProjectManage.getProjectid(), null);
	}

	/**
	 * 保存或更新项目管理（测试） 保存
	 * 
	 * @param testProjectManage
	 */
	public void _update(TestProjectManage testProjectManage, BusinessObject businessObject)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(testProjectManage);
		testProjectManageHibernateDao.saveOrUpdate(testProjectManage);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(testProjectManage);
	}

	/**
	 * 保存
	 * 
	 * @param testProjectManage
	 */
	public void _save(TestProjectManage testProjectManage)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(testProjectManage);
		// 对id赋空值，使之执行insert操作(适应复制创建场景)
		testProjectManage.setProjectid(null);
		testProjectManageHibernateDao.saveOrUpdate(testProjectManage);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(testProjectManage);
	}

	/**
	 * 
	 * 保存
	 * 
	 * @param testProjectManage
	 */
	public void _saveOrUpdate(TestProjectManage testProjectManage, BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(testProjectManage.getProjectid()))
		{
			_save(testProjectManage);
		}
		else
		{
			_update(testProjectManage, businessObject);
		}
	}

}