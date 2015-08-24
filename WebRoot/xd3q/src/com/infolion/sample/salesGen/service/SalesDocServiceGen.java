/*
 * @(#)SalesDocServiceGen.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2009年12月02日 17点06分43秒
 *  描　述：创建
 */
package com.infolion.sample.salesGen.service;

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
import com.infolion.sample.sales.dao.SalesDocHibernateDao;
import com.infolion.sample.sales.domain.SalesDoc;
import com.infolion.sample.sales.domain.SalesDocItem;
import com.infolion.sample.sales.domain.SalesDocKey;
import com.infolion.sample.sales.service.SalesDocItemService;
import com.infolion.sample.sales.service.SalesDocService;

/**
 * <pre>
 * SAP销售订单(SalesDoc)服务类
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
public class SalesDocServiceGen extends BaseService
{
	private Log log = LogFactory.getFactory().getLog(this.getClass());

	@Autowired
	private SalesDocHibernateDao salesDocHibernateDao;

	public void setSalesDocHibernateDao(SalesDocHibernateDao salesDocHibernateDao)
	{
		this.salesDocHibernateDao = salesDocHibernateDao;
	}

	/**
	 * 注入扩展服务类
	 */
	@Autowired(required = false)
	public void setAdvanceService(@Qualifier("salesDocAdvanceService") AdvanceService advanceService)
	{
		super.setAdvanceService(advanceService);
	}

	@Autowired
	private SalesDocItemService salesDocItemService;

	public void setSalesDocItemService(SalesDocItemService salesDocItemService)
	{
		this.salesDocItemService = salesDocItemService;
	}

	/**
	 * 根据主键ID,取得SAP销售订单实例
	 * 
	 * @param id
	 * @return
	 */
	public SalesDoc _get(SalesDocKey key)
	{
		SalesDoc salesDoc = new SalesDoc();
		if (StringUtils.isNotBlank(key.getClient()) || StringUtils.isNotBlank(key.getVbeln()))
		{
			salesDoc = salesDocHibernateDao.get(key);
			salesDocHibernateDao.clear();
		}
		// // 需要转移到service服务里，要不存在事务问题。
		// String operationType = LockService.lockBOData(salesDoc);
		// if (OperationType.UNVISIABLE.equals(operationType))
		// {
		// throw new LockException("记录已被锁定，您无权查看该记录！");
		// }
		// salesDoc.setOperationType(operationType);

		return salesDoc;
	}

	/**
	 * 根据主键ID,取得SAP销售订单实例
	 * 
	 * @param id
	 * @return
	 */
	public SalesDoc _getForEdit(SalesDocKey key)
	{
		SalesDoc salesDoc = _get(key);
		// // 需要转移到service服务里，要不存在事务问题。
		// String operationType = salesDoc.getOperationType();
		// if (OperationType.READONLY.equals(operationType))
		// {
		// throw new LockException("记录已被锁定，您无权编辑该记录！");
		// }

		return salesDoc;
	}

	/**
	 * 根据主键ID,取得SAP销售订单实例副本
	 * 
	 * @param id
	 * @return
	 */
	public SalesDoc _getEntityCopy(SalesDocKey key)
	{
		SalesDoc salesDoc = new SalesDoc();
		SalesDoc salesDocOld = this._get(key);
		try
		{
			BeanUtils.copyProperties(salesDoc, salesDocOld);
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (InvocationTargetException e)
		{
			e.printStackTrace();
		}
		salesDoc.setVbeln(null);

		return salesDoc;
	}

	/**
	 * 提交工作流
	 * 
	 * @param salesDoc
	 */
	public void _submitProcess(SalesDoc salesDoc, Set<SalesDocItem> deletedSalesDocItemsSet)
	{
		String id = salesDoc.getVbeln();

		String taskId = salesDoc.getWorkflowTaskId();
		if (null == taskId || "".equals(taskId))
			WorkflowService.createAndSignalProcessInstance(salesDoc, id);
		else
			WorkflowService.signalProcessInstance(salesDoc, id, null);
	}
}