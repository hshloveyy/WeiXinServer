/*
 * @(#)ViewTestServiceGen.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年03月22日 15点04分14秒
 *  描　述：创建
 */
package com.infolion.sample.b1.viewGen.service;

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
import com.infolion.sample.b1.view.dao.ViewTestHibernateDao;
import com.infolion.sample.b1.view.domain.ViewTest;

/**
 * <pre>
 * 视图测试(ViewTest)服务类
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
public class ViewTestServiceGen extends BaseService
{
	protected Log log = LogFactory.getFactory().getLog(this.getClass());

	@Autowired
	protected ViewTestHibernateDao viewTestHibernateDao;

	public void setViewTestHibernateDao(ViewTestHibernateDao viewTestHibernateDao)
	{
		this.viewTestHibernateDao = viewTestHibernateDao;
	}

	/**
	 * 注入扩展服务类
	 */
	@Autowired(required = false)
	public void setAdvanceService(@Qualifier("viewTestAdvanceService") AdvanceService advanceService)
	{
		super.setAdvanceService(advanceService);
	}

	/**
	 * 根据主键ID,取得视图测试实例
	 * 
	 * @param id
	 * @return
	 */
	public ViewTest _getDetached(String id)
	{
		ViewTest viewTest = new ViewTest();
		if (StringUtils.isNotBlank(id))
		{
			viewTest = viewTestHibernateDao.getDetached(id);
		}
		// 需要转移到service服务里，要不存在事务问题。
		String operationType = LockService.lockBOData(viewTest);
		if (OperationType.UNVISIABLE.equals(operationType))
		{
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
		}
		viewTest.setOperationType(operationType);

		return viewTest;
	}

	/**
	 * 根据主键ID,取得视图测试实例
	 * 
	 * @param id
	 * @return
	 */
	public ViewTest _get(String id)
	{
		ViewTest viewTest = new ViewTest();
		if (StringUtils.isNotBlank(id))
		{
			viewTest = viewTestHibernateDao.get(id);
		}
		// 需要转移到service服务里，要不存在事务问题。
		String operationType = LockService.lockBOData(viewTest);
		if (OperationType.UNVISIABLE.equals(operationType))
		{
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
		}
		viewTest.setOperationType(operationType);

		return viewTest;
	}

	/**
	 * 根据主键ID,取得视图测试实例
	 * 
	 * @param id
	 * @return
	 */
	public ViewTest _getForEdit(String id)
	{
		ViewTest viewTest = _get(id);
		// 需要转移到service服务里，要不存在事务问题。
		String operationType = viewTest.getOperationType();
		if (OperationType.READONLY.equals(operationType))
		{
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotEdit);
		}

		return viewTest;
	}

	/**
	 * 根据主键ID,取得视图测试实例副本
	 * 
	 * @param id
	 * @return
	 */
	public ViewTest _getEntityCopy(String id)
	{
		ViewTest viewTest = new ViewTest();
		ViewTest viewTestOld = this._get(id);
		try
		{
			BeanUtils.copyProperties(viewTest, viewTestOld);
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (InvocationTargetException e)
		{
			e.printStackTrace();
		}
		viewTest.setYeuserid(null);
		return viewTest;
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
	 * @param viewTest
	 */
	public void _delete(ViewTest viewTest)
	{
		if (null != advanceService)
			advanceService.preDelete(viewTest);

		LockService.isBoInstanceLocked(viewTest, ViewTest.class);
		viewTestHibernateDao.remove(viewTest);

		if (null != advanceService)
			advanceService.postDelete(viewTest);
	}

	/**
	 * 根据主键删除
	 * 
	 * @param viewTestId
	 */
	public void _delete(String viewTestId, BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(viewTestId))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("yeuserid"));
		ViewTest viewTest = this.viewTestHibernateDao.load(viewTestId);
		_delete(viewTest);
	}

	/**
	 * 删除
	 * 
	 * @param Set
	 *            <ViewTest> viewTests
	 */
	public void _deletes(Set<ViewTest> viewTests, BusinessObject businessObject)
	{
		if (null == viewTests || viewTests.size() < 1)
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoText());
		Iterator<ViewTest> it = viewTests.iterator();
		while (it.hasNext())
		{
			ViewTest viewTest = (ViewTest) it.next();
			_delete(viewTest);
		}
	}

	/**
	 * 根据多个主键(由，分割)删除多个对象
	 * 
	 * @param viewTestIds
	 */
	public void _deletes(String viewTestIds, BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(viewTestIds))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("yeuserid"));
		String[] ids = StringUtils.splitString(viewTestIds);
		for (int i = 0; i < ids.length; i++)
		{
			_delete(ids[i], businessObject);
		}
	}

	/**
	 * 提交工作流
	 * 
	 * @param viewTest
	 */
	public void _submitProcess(ViewTest viewTest, BusinessObject businessObject)
	{
		String id = viewTest.getYeuserid();
		if (StringUtils.isNullBlank(id))
		{
			_save(viewTest);
		}
		else
		{
			_update(viewTest, businessObject);
		}
		String taskId = viewTest.getWorkflowTaskId();
		if (null == taskId || "".equals(taskId))
			WorkflowService.createAndSignalProcessInstance(viewTest, id);
		else
			WorkflowService.signalProcessInstance(viewTest, id, null);
	}

	/**
	 * 保存或更新视图测试 保存
	 * 
	 * @param viewTest
	 */
	public void _update(ViewTest viewTest, BusinessObject businessObject)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(viewTest);
		viewTestHibernateDao.saveOrUpdate(viewTest);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(viewTest);
	}

	/**
	 * 保存
	 * 
	 * @param viewTest
	 */
	public void _save(ViewTest viewTest)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(viewTest);
		// 对id赋空值，使之执行insert操作(适应复制创建场景)
		viewTest.setYeuserid(null);
		viewTestHibernateDao.saveOrUpdate(viewTest);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(viewTest);
	}

	/**
	 * 
	 * 保存
	 * 
	 * @param viewTest
	 */
	public void _saveOrUpdate(ViewTest viewTest, BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(viewTest.getYeuserid()))
		{
			_save(viewTest);
		}
		else
		{
			_update(viewTest, businessObject);
		}
	}

}