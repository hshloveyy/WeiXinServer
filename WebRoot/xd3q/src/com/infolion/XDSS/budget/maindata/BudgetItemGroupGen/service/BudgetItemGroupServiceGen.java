/*
 * @(#)BudgetItemGroupServiceGen.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年03月05日 17点03分34秒
 *  描　述：创建
 */
package com.infolion.XDSS.budget.maindata.BudgetItemGroupGen.service;

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
import com.infolion.XDSS.budget.maindata.BudgetItemGroup.domain.BudgetItemGroup;
import com.infolion.XDSS.budget.maindata.BudgetItemGroup.service.BudgetItemGroupService;
import com.infolion.XDSS.budget.maindata.BudgetItemGroup.dao.BudgetItemGroupHibernateDao;
          
import com.infolion.XDSS.budget.maindata.BudgetItem.domain.BudgetItem;
import com.infolion.XDSS.budget.maindata.BudgetItem.service.BudgetItemService;

/**
 * <pre>
 * 预算项分组(BudgetItemGroup)服务类
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
public class BudgetItemGroupServiceGen extends BaseService
{
	protected Log log = LogFactory.getFactory().getLog(this.getClass());

	@Autowired
	protected BudgetItemGroupHibernateDao budgetItemGroupHibernateDao;
	
	public void setBudgetItemGroupHibernateDao(BudgetItemGroupHibernateDao budgetItemGroupHibernateDao)
	{
		this.budgetItemGroupHibernateDao = budgetItemGroupHibernateDao;
	}
	
     /**
	 * 注入扩展服务类
	 */
	 @Autowired(required = false)
	 public void setAdvanceService(@Qualifier("budgetItemGroupAdvanceService") AdvanceService advanceService)
	 {
	  	super.setAdvanceService(advanceService);
	 }   
	 
          

	@Autowired
	protected BudgetItemService budgetItemService;
	
	public void setBudgetItemService(BudgetItemService budgetItemService)
	{
		this.budgetItemService = budgetItemService;
	}
	
	   
	/**
	 * 根据主键ID,取得预算项分组实例
	 * @param id
	 * @return
	 */
	public BudgetItemGroup _get(String id)
	{		
	    BudgetItemGroup budgetItemGroup = new BudgetItemGroup();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  budgetItemGroup = budgetItemGroupHibernateDao.get(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(budgetItemGroup);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    budgetItemGroup.setOperationType(operationType);
	    
		return budgetItemGroup;	
	}
	
		/**
	 * 根据主键ID,取得预算项分组实例
	 * @param id
	 * @return
	 */
	public BudgetItemGroup _getForEdit(String id)
	{		
	    BudgetItemGroup budgetItemGroup = _get(id);
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = budgetItemGroup.getOperationType();
	    if(OperationType.READONLY.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotEdit);
	    }

		return budgetItemGroup;	
	}
	
	/**
	 * 根据主键ID,取得预算项分组实例副本
	 * @param id
	 * @return
	 */
	public BudgetItemGroup _getEntityCopy(String id)
	{		
	    BudgetItemGroup budgetItemGroup = new BudgetItemGroup();
		BudgetItemGroup budgetItemGroupOld = this._get(id);
		try
		{
			BeanUtils.copyProperties(budgetItemGroup, budgetItemGroupOld);
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (InvocationTargetException e)
		{
			e.printStackTrace();
		}
		budgetItemGroup.setBudgroupid(null); 
		return budgetItemGroup;	
	}
	
	/**
	 * 删除  
	 *   
	 * @param budgetItemGroup
	 */
	public void _delete(BudgetItemGroup budgetItemGroup)
	{
		if (null != advanceService)
			advanceService.preDelete(budgetItemGroup);
	
 		LockService.isBoInstanceLocked(budgetItemGroup,BudgetItemGroup.class);
		budgetItemGroupHibernateDao.remove(budgetItemGroup);

		if (null != advanceService)
			advanceService.postDelete(budgetItemGroup);
	}

	/**
	 * 根据主键删除
	 * 
	 * @param budgetItemGroupId
	 */
	public void _delete(String budgetItemGroupId,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(budgetItemGroupId))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("budgroupid"));
		BudgetItemGroup budgetItemGroup = this.budgetItemGroupHibernateDao.load(budgetItemGroupId);
		_delete(budgetItemGroup);
	}
	
	/**
	 * 删除  
	 *   
	 * @param Set<BudgetItemGroup> budgetItemGroups
	 */
	public void _deletes(Set<BudgetItemGroup> budgetItemGroups,BusinessObject businessObject)
	{
		if (null == budgetItemGroups || budgetItemGroups.size() < 1)
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoText());
		Iterator<BudgetItemGroup> it = budgetItemGroups.iterator();
		while (it.hasNext())
		{
			BudgetItemGroup budgetItemGroup = (BudgetItemGroup) it.next();
			_delete(budgetItemGroup);
		}
	}
	
	/**
	 * 根据多个主键(由，分割)删除多个对象
	 * 
	 * @param budgetItemGroupIds
	 */
	public void _deletes(String budgetItemGroupIds,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(budgetItemGroupIds))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("budgroupid"));
		String[] ids = StringUtils.splitString(budgetItemGroupIds);
		for (int i = 0; i < ids.length; i++)
		{
			_delete(ids[i],businessObject);
		}
	}
	/**
	 * 提交工作流
	 * 
	 * @param budgetItemGroup
	 */
	public void _submitProcess(BudgetItemGroup budgetItemGroup
,Set<BudgetItem> deletedBudgetItemSet	,BusinessObject businessObject)
	{
		String id = budgetItemGroup.getBudgroupid();
		if (StringUtils.isNullBlank(id))
		{
			_save(budgetItemGroup);
		}
		else
		{
			_update(budgetItemGroup
,deletedBudgetItemSet			, businessObject);
		}
		String taskId = budgetItemGroup.getWorkflowTaskId();
		if (null == taskId || "".equals(taskId))
			WorkflowService.createAndSignalProcessInstance(budgetItemGroup, id);
		else
			WorkflowService.signalProcessInstance(budgetItemGroup, id, null);
	}

	/**
	 * 保存或更新预算项分组
	 * 保存  
	 *  
	 * @param budgetItemGroup
	 */
	public void _update(BudgetItemGroup budgetItemGroup
,Set<BudgetItem> deletedBudgetItemSet,BusinessObject businessObject	
	)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(budgetItemGroup);
		budgetItemGroupHibernateDao.saveOrUpdate(budgetItemGroup);
// 删除关联子业务对象数据
if(deletedBudgetItemSet!=null && deletedBudgetItemSet.size()>0)
{
budgetItemService._deletes(deletedBudgetItemSet,businessObject);
}		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(budgetItemGroup);
	}
	
	/**
	 * 保存  
	 *   
	 * @param budgetItemGroup
	 */
	public void _save(BudgetItemGroup budgetItemGroup)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(budgetItemGroup);
		// 对id赋空值，使之执行insert操作(适应复制创建场景)
		budgetItemGroup.setBudgroupid(null);
       
		Set<BudgetItem> budgetItemSet = budgetItemGroup.getBudgetItem();
		Set<BudgetItem> newBudgetItemSet = null;
		if (null != budgetItemSet)
		{
			newBudgetItemSet = new HashSet();
			Iterator<BudgetItem> itBudgetItem = budgetItemSet.iterator();
			while (itBudgetItem.hasNext())
			{
				BudgetItem budgetItem = (BudgetItem) itBudgetItem.next();
				budgetItem.setBuditemid(null);
				newBudgetItemSet.add(budgetItem);
			}
		}
		budgetItemGroup.setBudgetItem(newBudgetItemSet);
       
       
       
       
       
       
       
       
       
		budgetItemGroupHibernateDao.saveOrUpdate(budgetItemGroup);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(budgetItemGroup);
	}
	
	/**
	 *
	 * 保存  
	 *   
	 * @param budgetItemGroup
	 */
	public void _saveOrUpdate(BudgetItemGroup budgetItemGroup
,Set<BudgetItem> deletedBudgetItemSet,BusinessObject businessObject		
	)
	{
		if (StringUtils.isNullBlank(budgetItemGroup.getBudgroupid()))
		{	
			_save(budgetItemGroup);
		}
		else
		{
			_update(budgetItemGroup
,deletedBudgetItemSet
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