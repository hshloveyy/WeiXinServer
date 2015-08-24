/*
 * @(#)BudgetTemplateItemServiceGen.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年03月05日 09点37分58秒
 *  描　述：创建
 */
package com.infolion.XDSS.budget.maindata.BudgetTemplateItemGen.service;

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
import com.infolion.XDSS.budget.maindata.BudgetTemplateItem.domain.BudgetTemplateItem;
import com.infolion.XDSS.budget.maindata.BudgetTemplateItem.service.BudgetTemplateItemService;
import com.infolion.XDSS.budget.maindata.BudgetTemplateItem.dao.BudgetTemplateItemHibernateDao;

/**
 * <pre>
 * 模版预算项(BudgetTemplateItem)服务类
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
public class BudgetTemplateItemServiceGen extends BaseService
{
	protected Log log = LogFactory.getFactory().getLog(this.getClass());

	@Autowired
	protected BudgetTemplateItemHibernateDao budgetTemplateItemHibernateDao;
	
	public void setBudgetTemplateItemHibernateDao(BudgetTemplateItemHibernateDao budgetTemplateItemHibernateDao)
	{
		this.budgetTemplateItemHibernateDao = budgetTemplateItemHibernateDao;
	}
	
     /**
	 * 注入扩展服务类
	 */
	 @Autowired(required = false)
	 public void setAdvanceService(@Qualifier("budgetTemplateItemAdvanceService") AdvanceService advanceService)
	 {
	  	super.setAdvanceService(advanceService);
	 }   
	 
	   
	/**
	 * 根据主键ID,取得模版预算项实例
	 * @param id
	 * @return
	 */
	public BudgetTemplateItem _get(String id)
	{		
	    BudgetTemplateItem budgetTemplateItem = new BudgetTemplateItem();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  budgetTemplateItem = budgetTemplateItemHibernateDao.get(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(budgetTemplateItem);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    budgetTemplateItem.setOperationType(operationType);
	    
		return budgetTemplateItem;	
	}
	
		/**
	 * 根据主键ID,取得模版预算项实例
	 * @param id
	 * @return
	 */
	public BudgetTemplateItem _getForEdit(String id)
	{		
	    BudgetTemplateItem budgetTemplateItem = _get(id);
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = budgetTemplateItem.getOperationType();
	    if(OperationType.READONLY.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotEdit);
	    }

		return budgetTemplateItem;	
	}
	
	/**
	 * 根据主键ID,取得模版预算项实例副本
	 * @param id
	 * @return
	 */
	public BudgetTemplateItem _getEntityCopy(String id)
	{		
	    BudgetTemplateItem budgetTemplateItem = new BudgetTemplateItem();
		BudgetTemplateItem budgetTemplateItemOld = this._get(id);
		try
		{
			BeanUtils.copyProperties(budgetTemplateItem, budgetTemplateItemOld);
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (InvocationTargetException e)
		{
			e.printStackTrace();
		}
		budgetTemplateItem.setBudtemitemid(null); 
		return budgetTemplateItem;	
	}
	
	/**
	 * 删除  
	 *   
	 * @param budgetTemplateItem
	 */
	public void _delete(BudgetTemplateItem budgetTemplateItem)
	{
		if (null != advanceService)
			advanceService.preDelete(budgetTemplateItem);
	
 		LockService.isBoInstanceLocked(budgetTemplateItem,BudgetTemplateItem.class);
		budgetTemplateItemHibernateDao.remove(budgetTemplateItem);

		if (null != advanceService)
			advanceService.postDelete(budgetTemplateItem);
	}

	/**
	 * 根据主键删除
	 * 
	 * @param budgetTemplateItemId
	 */
	public void _delete(String budgetTemplateItemId,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(budgetTemplateItemId))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("budtemitemid"));
		BudgetTemplateItem budgetTemplateItem = this.budgetTemplateItemHibernateDao.load(budgetTemplateItemId);
		_delete(budgetTemplateItem);
	}
	
	/**
	 * 删除  
	 *   
	 * @param Set<BudgetTemplateItem> budgetTemplateItems
	 */
	public void _deletes(Set<BudgetTemplateItem> budgetTemplateItems,BusinessObject businessObject)
	{
		if (null == budgetTemplateItems || budgetTemplateItems.size() < 1)
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoText());
		Iterator<BudgetTemplateItem> it = budgetTemplateItems.iterator();
		while (it.hasNext())
		{
			BudgetTemplateItem budgetTemplateItem = (BudgetTemplateItem) it.next();
			_delete(budgetTemplateItem);
		}
	}
	
	/**
	 * 根据多个主键(由，分割)删除多个对象
	 * 
	 * @param budgetTemplateItemIds
	 */
	public void _deletes(String budgetTemplateItemIds,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(budgetTemplateItemIds))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("budtemitemid"));
		String[] ids = StringUtils.splitString(budgetTemplateItemIds);
		for (int i = 0; i < ids.length; i++)
		{
			_delete(ids[i],businessObject);
		}
	}
	/**
	 * 提交工作流
	 * 
	 * @param budgetTemplateItem
	 */
	public void _submitProcess(BudgetTemplateItem budgetTemplateItem
	,BusinessObject businessObject)
	{
		String id = budgetTemplateItem.getBudtemitemid();
		if (StringUtils.isNullBlank(id))
		{
			_save(budgetTemplateItem);
		}
		else
		{
			_update(budgetTemplateItem
			, businessObject);
		}
		String taskId = budgetTemplateItem.getWorkflowTaskId();
		if (null == taskId || "".equals(taskId))
			WorkflowService.createAndSignalProcessInstance(budgetTemplateItem, id);
		else
			WorkflowService.signalProcessInstance(budgetTemplateItem, id, null);
	}

	/**
	 * 保存或更新模版预算项
	 * 保存  
	 *  
	 * @param budgetTemplateItem
	 */
	public void _update(BudgetTemplateItem budgetTemplateItem
,BusinessObject businessObject	
	)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(budgetTemplateItem);
		budgetTemplateItemHibernateDao.saveOrUpdate(budgetTemplateItem);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(budgetTemplateItem);
	}
	
	/**
	 * 保存  
	 *   
	 * @param budgetTemplateItem
	 */
	public void _save(BudgetTemplateItem budgetTemplateItem)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(budgetTemplateItem);
		// 对id赋空值，使之执行insert操作(适应复制创建场景)
		budgetTemplateItem.setBudtemitemid(null);
                        		budgetTemplateItemHibernateDao.saveOrUpdate(budgetTemplateItem);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(budgetTemplateItem);
	}
	
	/**
	 *
	 * 保存  
	 *   
	 * @param budgetTemplateItem
	 */
	public void _saveOrUpdate(BudgetTemplateItem budgetTemplateItem
,BusinessObject businessObject		
	)
	{
		if (StringUtils.isNullBlank(budgetTemplateItem.getBudtemitemid()))
		{	
			_save(budgetTemplateItem);
		}
		else
		{
			_update(budgetTemplateItem
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