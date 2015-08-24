/*
 * @(#)BudgetSortServiceGen.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年03月04日 13点22分31秒
 *  描　述：创建
 */
package com.infolion.XDSS.budget.maindata.BudgetSortGen.service;

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
import com.infolion.XDSS.budget.maindata.BudgetSort.domain.BudgetSort;
import com.infolion.XDSS.budget.maindata.BudgetSort.service.BudgetSortService;
import com.infolion.XDSS.budget.maindata.BudgetSort.dao.BudgetSortHibernateDao;
          
import com.infolion.XDSS.budget.maindata.BudgetTemplate.domain.BudgetTemplate;
import com.infolion.XDSS.budget.maindata.BudgetTemplate.service.BudgetTemplateService;

/**
 * <pre>
 * 预算分类(BudgetSort)服务类
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
public class BudgetSortServiceGen extends BaseService
{
	protected Log log = LogFactory.getFactory().getLog(this.getClass());

	@Autowired
	protected BudgetSortHibernateDao budgetSortHibernateDao;
	
	public void setBudgetSortHibernateDao(BudgetSortHibernateDao budgetSortHibernateDao)
	{
		this.budgetSortHibernateDao = budgetSortHibernateDao;
	}
	
     /**
	 * 注入扩展服务类
	 */
	 @Autowired(required = false)
	 public void setAdvanceService(@Qualifier("budgetSortAdvanceService") AdvanceService advanceService)
	 {
	  	super.setAdvanceService(advanceService);
	 }   
	 
          

	@Autowired
	protected BudgetTemplateService budgetTemplateService;
	
	public void setBudgetTemplateService(BudgetTemplateService budgetTemplateService)
	{
		this.budgetTemplateService = budgetTemplateService;
	}
	
	   
	/**
	 * 根据主键ID,取得预算分类实例
	 * @param id
	 * @return
	 */
	public BudgetSort _get(String id)
	{		
	    BudgetSort budgetSort = new BudgetSort();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  budgetSort = budgetSortHibernateDao.get(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
		// String operationType = LockService.lockBOData(budgetSort);
		// if(OperationType.UNVISIABLE.equals(operationType))
		// {
		// throw new LockException(SysMsgConstants.MSG_CLASS_DP,
		// SysMsgConstants.DataLockCanNotView);
		// }
		// budgetSort.setOperationType(operationType);
	    
		return budgetSort;	
	}
	
		/**
	 * 根据主键ID,取得预算分类实例
	 * @param id
	 * @return
	 */
	public BudgetSort _getForEdit(String id)
	{		
	    BudgetSort budgetSort = _get(id);
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = budgetSort.getOperationType();
	    if(OperationType.READONLY.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotEdit);
	    }

		return budgetSort;	
	}
	
	/**
	 * 根据主键ID,取得预算分类实例副本
	 * @param id
	 * @return
	 */
	public BudgetSort _getEntityCopy(String id)
	{		
	    BudgetSort budgetSort = new BudgetSort();
		BudgetSort budgetSortOld = this._get(id);
		try
		{
			BeanUtils.copyProperties(budgetSort, budgetSortOld);
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (InvocationTargetException e)
		{
			e.printStackTrace();
		}
		budgetSort.setBudsortid(null); 
		return budgetSort;	
	}
	
	/**
	 * 删除  
	 *   
	 * @param budgetSort
	 */
	public void _delete(BudgetSort budgetSort)
	{
		if (null != advanceService)
			advanceService.preDelete(budgetSort);
	
 		LockService.isBoInstanceLocked(budgetSort,BudgetSort.class);
		budgetSortHibernateDao.remove(budgetSort);

		if (null != advanceService)
			advanceService.postDelete(budgetSort);
	}

	/**
	 * 根据主键删除
	 * 
	 * @param budgetSortId
	 */
	public void _delete(String budgetSortId,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(budgetSortId))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("budsortid"));
		BudgetSort budgetSort = this.budgetSortHibernateDao.load(budgetSortId);
		_delete(budgetSort);
	}
	
	/**
	 * 删除  
	 *   
	 * @param Set<BudgetSort> budgetSorts
	 */
	public void _deletes(Set<BudgetSort> budgetSorts,BusinessObject businessObject)
	{
		if (null == budgetSorts || budgetSorts.size() < 1)
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoText());
		Iterator<BudgetSort> it = budgetSorts.iterator();
		while (it.hasNext())
		{
			BudgetSort budgetSort = (BudgetSort) it.next();
			_delete(budgetSort);
		}
	}
	
	/**
	 * 根据多个主键(由，分割)删除多个对象
	 * 
	 * @param budgetSortIds
	 */
	public void _deletes(String budgetSortIds,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(budgetSortIds))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("budsortid"));
		String[] ids = StringUtils.splitString(budgetSortIds);
		for (int i = 0; i < ids.length; i++)
		{
			_delete(ids[i],businessObject);
		}
	}
	/**
	 * 提交工作流
	 * 
	 * @param budgetSort
	 */
	public void _submitProcess(BudgetSort budgetSort
,Set<BudgetTemplate> deletedBudgetTemplateSet	,BusinessObject businessObject)
	{
		String id = budgetSort.getBudsortid();
		if (StringUtils.isNullBlank(id))
		{
			_save(budgetSort);
		}
		else
		{
			_update(budgetSort
,deletedBudgetTemplateSet			, businessObject);
		}
		String taskId = budgetSort.getWorkflowTaskId();
		if (null == taskId || "".equals(taskId))
			WorkflowService.createAndSignalProcessInstance(budgetSort, id);
		else
			WorkflowService.signalProcessInstance(budgetSort, id, null);
	}

	/**
	 * 保存或更新预算分类
	 * 保存  
	 *  
	 * @param budgetSort
	 */
	public void _update(BudgetSort budgetSort
,Set<BudgetTemplate> deletedBudgetTemplateSet,BusinessObject businessObject	
	)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(budgetSort);
		budgetSortHibernateDao.saveOrUpdate(budgetSort);
// 删除关联子业务对象数据
if(deletedBudgetTemplateSet!=null && deletedBudgetTemplateSet.size()>0)
{
budgetTemplateService._deletes(deletedBudgetTemplateSet,businessObject);
}		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(budgetSort);
	}
	
	/**
	 * 保存  
	 *   
	 * @param budgetSort
	 */
	public void _save(BudgetSort budgetSort)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(budgetSort);
		// 对id赋空值，使之执行insert操作(适应复制创建场景)
		budgetSort.setBudsortid(null);
       
		Set<BudgetTemplate> budgetTemplateSet = budgetSort.getBudgetTemplate();
		Set<BudgetTemplate> newBudgetTemplateSet = null;
		if (null != budgetTemplateSet)
		{
			newBudgetTemplateSet = new HashSet();
			Iterator<BudgetTemplate> itBudgetTemplate = budgetTemplateSet.iterator();
			while (itBudgetTemplate.hasNext())
			{
				BudgetTemplate budgetTemplate = (BudgetTemplate) itBudgetTemplate.next();
				budgetTemplate.setBudtemid(null);
				newBudgetTemplateSet.add(budgetTemplate);
			}
		}
		budgetSort.setBudgetTemplate(newBudgetTemplateSet);
       
       
       
       
       
       
       
       
       
       
       
		budgetSortHibernateDao.saveOrUpdate(budgetSort);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(budgetSort);
	}
	
	/**
	 *
	 * 保存  
	 *   
	 * @param budgetSort
	 */
	public void _saveOrUpdate(BudgetSort budgetSort
,Set<BudgetTemplate> deletedBudgetTemplateSet,BusinessObject businessObject		
	)
	{
		if (StringUtils.isNullBlank(budgetSort.getBudsortid()))
		{	
			_save(budgetSort);
		}
		else
		{
			_update(budgetSort
,deletedBudgetTemplateSet
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