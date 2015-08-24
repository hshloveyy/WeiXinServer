/*
 * @(#)BudgetItemServiceGen.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年03月05日 17点03分36秒
 *  描　述：创建
 */
package com.infolion.XDSS.budget.maindata.BudgetItemGen.service;

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
import com.infolion.XDSS.budget.maindata.BudgetItem.domain.BudgetItem;
import com.infolion.XDSS.budget.maindata.BudgetItem.service.BudgetItemService;
import com.infolion.XDSS.budget.maindata.BudgetItem.dao.BudgetItemHibernateDao;

/**
 * <pre>
 * 预算项(BudgetItem)服务类
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
public class BudgetItemServiceGen extends BaseService
{
	protected Log log = LogFactory.getFactory().getLog(this.getClass());

	@Autowired
	protected BudgetItemHibernateDao budgetItemHibernateDao;
	
	public void setBudgetItemHibernateDao(BudgetItemHibernateDao budgetItemHibernateDao)
	{
		this.budgetItemHibernateDao = budgetItemHibernateDao;
	}
	
     /**
	 * 注入扩展服务类
	 */
	 @Autowired(required = false)
	 public void setAdvanceService(@Qualifier("budgetItemAdvanceService") AdvanceService advanceService)
	 {
	  	super.setAdvanceService(advanceService);
	 }   
	 
	   
	/**
	 * 根据主键ID,取得预算项实例
	 * @param id
	 * @return
	 */
	public BudgetItem _get(String id)
	{		
	    BudgetItem budgetItem = new BudgetItem();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  budgetItem = budgetItemHibernateDao.get(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(budgetItem);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    budgetItem.setOperationType(operationType);
	    
		return budgetItem;	
	}
	
		/**
	 * 根据主键ID,取得预算项实例
	 * @param id
	 * @return
	 */
	public BudgetItem _getForEdit(String id)
	{		
	    BudgetItem budgetItem = _get(id);
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = budgetItem.getOperationType();
	    if(OperationType.READONLY.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotEdit);
	    }

		return budgetItem;	
	}
	
	/**
	 * 根据主键ID,取得预算项实例副本
	 * @param id
	 * @return
	 */
	public BudgetItem _getEntityCopy(String id)
	{		
	    BudgetItem budgetItem = new BudgetItem();
		BudgetItem budgetItemOld = this._get(id);
		try
		{
			BeanUtils.copyProperties(budgetItem, budgetItemOld);
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (InvocationTargetException e)
		{
			e.printStackTrace();
		}
		budgetItem.setBuditemid(null); 
		return budgetItem;	
	}
	
	/**
	 * 删除  
	 *   
	 * @param budgetItem
	 */
	public void _delete(BudgetItem budgetItem)
	{
		if (null != advanceService)
			advanceService.preDelete(budgetItem);
	
 		LockService.isBoInstanceLocked(budgetItem,BudgetItem.class);
		budgetItemHibernateDao.remove(budgetItem);

		if (null != advanceService)
			advanceService.postDelete(budgetItem);
	}

	/**
	 * 根据主键删除
	 * 
	 * @param budgetItemId
	 */
	public void _delete(String budgetItemId,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(budgetItemId))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("buditemid"));
		BudgetItem budgetItem = this.budgetItemHibernateDao.load(budgetItemId);
		_delete(budgetItem);
	}
	
	/**
	 * 删除  
	 *   
	 * @param Set<BudgetItem> budgetItems
	 */
	public void _deletes(Set<BudgetItem> budgetItems,BusinessObject businessObject)
	{
		if (null == budgetItems || budgetItems.size() < 1)
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoText());
		Iterator<BudgetItem> it = budgetItems.iterator();
		while (it.hasNext())
		{
			BudgetItem budgetItem = (BudgetItem) it.next();
			_delete(budgetItem);
		}
	}
	
	/**
	 * 根据多个主键(由，分割)删除多个对象
	 * 
	 * @param budgetItemIds
	 */
	public void _deletes(String budgetItemIds,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(budgetItemIds))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("buditemid"));
		String[] ids = StringUtils.splitString(budgetItemIds);
		for (int i = 0; i < ids.length; i++)
		{
			_delete(ids[i],businessObject);
		}
	}
	/**
	 * 提交工作流
	 * 
	 * @param budgetItem
	 */
	public void _submitProcess(BudgetItem budgetItem
	,BusinessObject businessObject)
	{
		String id = budgetItem.getBuditemid();
		if (StringUtils.isNullBlank(id))
		{
			_save(budgetItem);
		}
		else
		{
			_update(budgetItem
			, businessObject);
		}
		String taskId = budgetItem.getWorkflowTaskId();
		if (null == taskId || "".equals(taskId))
			WorkflowService.createAndSignalProcessInstance(budgetItem, id);
		else
			WorkflowService.signalProcessInstance(budgetItem, id, null);
	}

	/**
	 * 保存或更新预算项
	 * 保存  
	 *  
	 * @param budgetItem
	 */
	public void _update(BudgetItem budgetItem
,BusinessObject businessObject	
	)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(budgetItem);
		budgetItemHibernateDao.saveOrUpdate(budgetItem);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(budgetItem);
	}
	
	/**
	 * 保存  
	 *   
	 * @param budgetItem
	 */
	public void _save(BudgetItem budgetItem)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(budgetItem);
		// 对id赋空值，使之执行insert操作(适应复制创建场景)
		budgetItem.setBuditemid(null);
                            		budgetItemHibernateDao.saveOrUpdate(budgetItem);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(budgetItem);
	}
	
	/**
	 *
	 * 保存  
	 *   
	 * @param budgetItem
	 */
	public void _saveOrUpdate(BudgetItem budgetItem
,BusinessObject businessObject		
	)
	{
		if (StringUtils.isNullBlank(budgetItem.getBuditemid()))
		{	
			_save(budgetItem);
		}
		else
		{
			_update(budgetItem
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