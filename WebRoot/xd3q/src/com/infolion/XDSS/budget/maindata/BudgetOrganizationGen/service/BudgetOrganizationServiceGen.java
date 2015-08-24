/*
 * @(#)BudgetOrganizationServiceGen.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年03月05日 13点59分04秒
 *  描　述：创建
 */
package com.infolion.XDSS.budget.maindata.BudgetOrganizationGen.service;

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
import com.infolion.XDSS.budget.maindata.BudgetOrganization.domain.BudgetOrganization;
import com.infolion.XDSS.budget.maindata.BudgetOrganization.service.BudgetOrganizationService;
import com.infolion.XDSS.budget.maindata.BudgetOrganization.dao.BudgetOrganizationHibernateDao;
          
import com.infolion.XDSS.budget.maindata.BudgetOrgTemp.domain.BudgetOrgTemp;
import com.infolion.XDSS.budget.maindata.BudgetOrgTemp.service.BudgetOrgTempService;

/**
 * <pre>
 * 预算组织(BudgetOrganization)服务类
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
public class BudgetOrganizationServiceGen extends BaseService
{
	protected Log log = LogFactory.getFactory().getLog(this.getClass());

	@Autowired
	protected BudgetOrganizationHibernateDao budgetOrganizationHibernateDao;
	
	public void setBudgetOrganizationHibernateDao(BudgetOrganizationHibernateDao budgetOrganizationHibernateDao)
	{
		this.budgetOrganizationHibernateDao = budgetOrganizationHibernateDao;
	}
	
     /**
	 * 注入扩展服务类
	 */
	 @Autowired(required = false)
	 public void setAdvanceService(@Qualifier("budgetOrganizationAdvanceService") AdvanceService advanceService)
	 {
	  	super.setAdvanceService(advanceService);
	 }   
	 
          

	@Autowired
	protected BudgetOrgTempService budgetOrgTempService;
	
	public void setBudgetOrgTempService(BudgetOrgTempService budgetOrgTempService)
	{
		this.budgetOrgTempService = budgetOrgTempService;
	}
	
	   
	/**
	 * 根据主键ID,取得预算组织实例
	 * @param id
	 * @return
	 */
	public BudgetOrganization _get(String id)
	{		
	    BudgetOrganization budgetOrganization = new BudgetOrganization();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  budgetOrganization = budgetOrganizationHibernateDao.get(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(budgetOrganization);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    budgetOrganization.setOperationType(operationType);
	    
		return budgetOrganization;	
	}
	
		/**
	 * 根据主键ID,取得预算组织实例
	 * @param id
	 * @return
	 */
	public BudgetOrganization _getForEdit(String id)
	{		
	    BudgetOrganization budgetOrganization = _get(id);
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = budgetOrganization.getOperationType();
	    if(OperationType.READONLY.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotEdit);
	    }

		return budgetOrganization;	
	}
	
	/**
	 * 根据主键ID,取得预算组织实例副本
	 * @param id
	 * @return
	 */
	public BudgetOrganization _getEntityCopy(String id)
	{		
	    BudgetOrganization budgetOrganization = new BudgetOrganization();
		BudgetOrganization budgetOrganizationOld = this._get(id);
		try
		{
			BeanUtils.copyProperties(budgetOrganization, budgetOrganizationOld);
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (InvocationTargetException e)
		{
			e.printStackTrace();
		}
		budgetOrganization.setBudorgid(null); 
		return budgetOrganization;	
	}
	
	/**
	 * 删除  
	 *   
	 * @param budgetOrganization
	 */
	public void _delete(BudgetOrganization budgetOrganization)
	{
		if (null != advanceService)
			advanceService.preDelete(budgetOrganization);
	
 		LockService.isBoInstanceLocked(budgetOrganization,BudgetOrganization.class);
		budgetOrganizationHibernateDao.remove(budgetOrganization);

		if (null != advanceService)
			advanceService.postDelete(budgetOrganization);
	}

	/**
	 * 根据主键删除
	 * 
	 * @param budgetOrganizationId
	 */
	public void _delete(String budgetOrganizationId,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(budgetOrganizationId))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("budorgid"));
		BudgetOrganization budgetOrganization = this.budgetOrganizationHibernateDao.load(budgetOrganizationId);
		_delete(budgetOrganization);
	}
	
	/**
	 * 删除  
	 *   
	 * @param Set<BudgetOrganization> budgetOrganizations
	 */
	public void _deletes(Set<BudgetOrganization> budgetOrganizations,BusinessObject businessObject)
	{
		if (null == budgetOrganizations || budgetOrganizations.size() < 1)
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoText());
		Iterator<BudgetOrganization> it = budgetOrganizations.iterator();
		while (it.hasNext())
		{
			BudgetOrganization budgetOrganization = (BudgetOrganization) it.next();
			_delete(budgetOrganization);
		}
	}
	
	/**
	 * 根据多个主键(由，分割)删除多个对象
	 * 
	 * @param budgetOrganizationIds
	 */
	public void _deletes(String budgetOrganizationIds,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(budgetOrganizationIds))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("budorgid"));
		String[] ids = StringUtils.splitString(budgetOrganizationIds);
		for (int i = 0; i < ids.length; i++)
		{
			_delete(ids[i],businessObject);
		}
	}
	/**
	 * 提交工作流
	 * 
	 * @param budgetOrganization
	 */
	public void _submitProcess(BudgetOrganization budgetOrganization
,Set<BudgetOrgTemp> deletedBudgetOrgTempSet	,BusinessObject businessObject)
	{
		String id = budgetOrganization.getBudorgid();
		if (StringUtils.isNullBlank(id))
		{
			_save(budgetOrganization);
		}
		else
		{
			_update(budgetOrganization
,deletedBudgetOrgTempSet			, businessObject);
		}
		String taskId = budgetOrganization.getWorkflowTaskId();
		if (null == taskId || "".equals(taskId))
			WorkflowService.createAndSignalProcessInstance(budgetOrganization, id);
		else
			WorkflowService.signalProcessInstance(budgetOrganization, id, null);
	}

	/**
	 * 保存或更新预算组织
	 * 保存  
	 *  
	 * @param budgetOrganization
	 */
	public void _update(BudgetOrganization budgetOrganization
,Set<BudgetOrgTemp> deletedBudgetOrgTempSet,BusinessObject businessObject	
	)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(budgetOrganization);
		budgetOrganizationHibernateDao.saveOrUpdate(budgetOrganization);
// 删除关联子业务对象数据
if(deletedBudgetOrgTempSet!=null && deletedBudgetOrgTempSet.size()>0)
{
budgetOrgTempService._deletes(deletedBudgetOrgTempSet,businessObject);
}		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(budgetOrganization);
	}
	
	/**
	 * 保存  
	 *   
	 * @param budgetOrganization
	 */
	public void _save(BudgetOrganization budgetOrganization)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(budgetOrganization);
		// 对id赋空值，使之执行insert操作(适应复制创建场景)
		budgetOrganization.setBudorgid(null);
       
		Set<BudgetOrgTemp> budgetOrgTempSet = budgetOrganization.getBudgetOrgTemp();
		Set<BudgetOrgTemp> newBudgetOrgTempSet = null;
		if (null != budgetOrgTempSet)
		{
			newBudgetOrgTempSet = new HashSet();
			Iterator<BudgetOrgTemp> itBudgetOrgTemp = budgetOrgTempSet.iterator();
			while (itBudgetOrgTemp.hasNext())
			{
				BudgetOrgTemp budgetOrgTemp = (BudgetOrgTemp) itBudgetOrgTemp.next();
				budgetOrgTemp.setBudorgtemid(null);
				newBudgetOrgTempSet.add(budgetOrgTemp);
			}
		}
		budgetOrganization.setBudgetOrgTemp(newBudgetOrgTempSet);
       
       
       
       
       
       
       
       
       
       
       
       
       
       
		budgetOrganizationHibernateDao.saveOrUpdate(budgetOrganization);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(budgetOrganization);
	}
	
	/**
	 *
	 * 保存  
	 *   
	 * @param budgetOrganization
	 */
	public void _saveOrUpdate(BudgetOrganization budgetOrganization
,Set<BudgetOrgTemp> deletedBudgetOrgTempSet,BusinessObject businessObject		
	)
	{
		if (StringUtils.isNullBlank(budgetOrganization.getBudorgid()))
		{	
			_save(budgetOrganization);
		}
		else
		{
			_update(budgetOrganization
,deletedBudgetOrgTempSet
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