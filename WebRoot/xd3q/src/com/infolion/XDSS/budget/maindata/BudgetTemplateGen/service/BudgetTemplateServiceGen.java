/*
 * @(#)BudgetTemplateServiceGen.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年03月05日 09点37分57秒
 *  描　述：创建
 */
package com.infolion.XDSS.budget.maindata.BudgetTemplateGen.service;

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
import com.infolion.XDSS.budget.maindata.BudgetTemplate.domain.BudgetTemplate;
import com.infolion.XDSS.budget.maindata.BudgetTemplate.service.BudgetTemplateService;
import com.infolion.XDSS.budget.maindata.BudgetTemplate.dao.BudgetTemplateHibernateDao;
          
import com.infolion.XDSS.budget.maindata.BudgetTemplateItem.domain.BudgetTemplateItem;
import com.infolion.XDSS.budget.maindata.BudgetTemplateItem.service.BudgetTemplateItemService;

/**
 * <pre>
 * 预算模版(BudgetTemplate)服务类
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
public class BudgetTemplateServiceGen extends BaseService
{
	protected Log log = LogFactory.getFactory().getLog(this.getClass());

	@Autowired
	protected BudgetTemplateHibernateDao budgetTemplateHibernateDao;
	
	public void setBudgetTemplateHibernateDao(BudgetTemplateHibernateDao budgetTemplateHibernateDao)
	{
		this.budgetTemplateHibernateDao = budgetTemplateHibernateDao;
	}
	
     /**
	 * 注入扩展服务类
	 */
	 @Autowired(required = false)
	 public void setAdvanceService(@Qualifier("budgetTemplateAdvanceService") AdvanceService advanceService)
	 {
	  	super.setAdvanceService(advanceService);
	 }   
	 
          

	@Autowired
	protected BudgetTemplateItemService budgetTemplateItemService;
	
	public void setBudgetTemplateItemService(BudgetTemplateItemService budgetTemplateItemService)
	{
		this.budgetTemplateItemService = budgetTemplateItemService;
	}
	
	   
	/**
	 * 根据主键ID,取得预算模版实例
	 * @param id
	 * @return
	 */
	public BudgetTemplate _get(String id)
	{		
	    BudgetTemplate budgetTemplate = new BudgetTemplate();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  budgetTemplate = budgetTemplateHibernateDao.get(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(budgetTemplate);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    budgetTemplate.setOperationType(operationType);
	    
		return budgetTemplate;	
	}
	
		/**
	 * 根据主键ID,取得预算模版实例
	 * @param id
	 * @return
	 */
	public BudgetTemplate _getForEdit(String id)
	{		
	    BudgetTemplate budgetTemplate = _get(id);
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = budgetTemplate.getOperationType();
	    if(OperationType.READONLY.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotEdit);
	    }

		return budgetTemplate;	
	}
	
	/**
	 * 根据主键ID,取得预算模版实例副本
	 * @param id
	 * @return
	 */
	public BudgetTemplate _getEntityCopy(String id)
	{		
	    BudgetTemplate budgetTemplate = new BudgetTemplate();
		BudgetTemplate budgetTemplateOld = this._get(id);
		try
		{
			BeanUtils.copyProperties(budgetTemplate, budgetTemplateOld);
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (InvocationTargetException e)
		{
			e.printStackTrace();
		}
		budgetTemplate.setBudtemid(null); 
		return budgetTemplate;	
	}
	
	/**
	 * 删除  
	 *   
	 * @param budgetTemplate
	 */
	public void _delete(BudgetTemplate budgetTemplate)
	{
		if (null != advanceService)
			advanceService.preDelete(budgetTemplate);
	
 		LockService.isBoInstanceLocked(budgetTemplate,BudgetTemplate.class);
		budgetTemplateHibernateDao.remove(budgetTemplate);

		if (null != advanceService)
			advanceService.postDelete(budgetTemplate);
	}

	/**
	 * 根据主键删除
	 * 
	 * @param budgetTemplateId
	 */
	public void _delete(String budgetTemplateId,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(budgetTemplateId))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("budtemid"));
		BudgetTemplate budgetTemplate = this.budgetTemplateHibernateDao.load(budgetTemplateId);
		_delete(budgetTemplate);
	}
	
	/**
	 * 删除  
	 *   
	 * @param Set<BudgetTemplate> budgetTemplates
	 */
	public void _deletes(Set<BudgetTemplate> budgetTemplates,BusinessObject businessObject)
	{
		if (null == budgetTemplates || budgetTemplates.size() < 1)
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoText());
		Iterator<BudgetTemplate> it = budgetTemplates.iterator();
		while (it.hasNext())
		{
			BudgetTemplate budgetTemplate = (BudgetTemplate) it.next();
			_delete(budgetTemplate);
		}
	}
	
	/**
	 * 根据多个主键(由，分割)删除多个对象
	 * 
	 * @param budgetTemplateIds
	 */
	public void _deletes(String budgetTemplateIds,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(budgetTemplateIds))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("budtemid"));
		String[] ids = StringUtils.splitString(budgetTemplateIds);
		for (int i = 0; i < ids.length; i++)
		{
			_delete(ids[i],businessObject);
		}
	}
	/**
	 * 提交工作流
	 * 
	 * @param budgetTemplate
	 */
	public void _submitProcess(BudgetTemplate budgetTemplate
,Set<BudgetTemplateItem> deletedBudgetTemplateItemSet	,BusinessObject businessObject)
	{
		String id = budgetTemplate.getBudtemid();
		if (StringUtils.isNullBlank(id))
		{
			_save(budgetTemplate);
		}
		else
		{
			_update(budgetTemplate
,deletedBudgetTemplateItemSet			, businessObject);
		}
		String taskId = budgetTemplate.getWorkflowTaskId();
		if (null == taskId || "".equals(taskId))
			WorkflowService.createAndSignalProcessInstance(budgetTemplate, id);
		else
			WorkflowService.signalProcessInstance(budgetTemplate, id, null);
	}

	/**
	 * 保存或更新预算模版
	 * 保存  
	 *  
	 * @param budgetTemplate
	 */
	public void _update(BudgetTemplate budgetTemplate
,Set<BudgetTemplateItem> deletedBudgetTemplateItemSet,BusinessObject businessObject	
	)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(budgetTemplate);
		budgetTemplateHibernateDao.saveOrUpdate(budgetTemplate);
// 删除关联子业务对象数据
if(deletedBudgetTemplateItemSet!=null && deletedBudgetTemplateItemSet.size()>0)
{
budgetTemplateItemService._deletes(deletedBudgetTemplateItemSet,businessObject);
}		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(budgetTemplate);
	}
	
	/**
	 * 保存  
	 *   
	 * @param budgetTemplate
	 */
	public void _save(BudgetTemplate budgetTemplate)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(budgetTemplate);
		// 对id赋空值，使之执行insert操作(适应复制创建场景)
		budgetTemplate.setBudtemid(null);
       
		Set<BudgetTemplateItem> budgetTemplateItemSet = budgetTemplate.getBudgetTemplateItem();
		Set<BudgetTemplateItem> newBudgetTemplateItemSet = null;
		if (null != budgetTemplateItemSet)
		{
			newBudgetTemplateItemSet = new HashSet();
			Iterator<BudgetTemplateItem> itBudgetTemplateItem = budgetTemplateItemSet.iterator();
			while (itBudgetTemplateItem.hasNext())
			{
				BudgetTemplateItem budgetTemplateItem = (BudgetTemplateItem) itBudgetTemplateItem.next();
				budgetTemplateItem.setBudtemitemid(null);
				newBudgetTemplateItemSet.add(budgetTemplateItem);
			}
		}
		budgetTemplate.setBudgetTemplateItem(newBudgetTemplateItemSet);
       
       
       
       
       
       
       
       
       
       
       
		budgetTemplateHibernateDao.saveOrUpdate(budgetTemplate);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(budgetTemplate);
	}
	
	/**
	 *
	 * 保存  
	 *   
	 * @param budgetTemplate
	 */
	public void _saveOrUpdate(BudgetTemplate budgetTemplate
,Set<BudgetTemplateItem> deletedBudgetTemplateItemSet,BusinessObject businessObject		
	)
	{
		if (StringUtils.isNullBlank(budgetTemplate.getBudtemid()))
		{	
			_save(budgetTemplate);
		}
		else
		{
			_update(budgetTemplate
,deletedBudgetTemplateItemSet
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