/*
 * @(#)BudgetOrgTempServiceGen.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年03月05日 13点59分06秒
 *  描　述：创建
 */
package com.infolion.XDSS.budget.maindata.BudgetOrgTempGen.service;

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
import com.infolion.XDSS.budget.maindata.BudgetOrgTemp.domain.BudgetOrgTemp;
import com.infolion.XDSS.budget.maindata.BudgetOrgTemp.service.BudgetOrgTempService;
import com.infolion.XDSS.budget.maindata.BudgetOrgTemp.dao.BudgetOrgTempHibernateDao;

/**
 * <pre>
 * 预算组织模版(BudgetOrgTemp)服务类
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
public class BudgetOrgTempServiceGen extends BaseService
{
	protected Log log = LogFactory.getFactory().getLog(this.getClass());

	@Autowired
	protected BudgetOrgTempHibernateDao budgetOrgTempHibernateDao;
	
	public void setBudgetOrgTempHibernateDao(BudgetOrgTempHibernateDao budgetOrgTempHibernateDao)
	{
		this.budgetOrgTempHibernateDao = budgetOrgTempHibernateDao;
	}
	
     /**
	 * 注入扩展服务类
	 */
	 @Autowired(required = false)
	 public void setAdvanceService(@Qualifier("budgetOrgTempAdvanceService") AdvanceService advanceService)
	 {
	  	super.setAdvanceService(advanceService);
	 }   
	 
	   
	/**
	 * 根据主键ID,取得预算组织模版实例
	 * @param id
	 * @return
	 */
	public BudgetOrgTemp _get(String id)
	{		
	    BudgetOrgTemp budgetOrgTemp = new BudgetOrgTemp();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  budgetOrgTemp = budgetOrgTempHibernateDao.get(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(budgetOrgTemp);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    budgetOrgTemp.setOperationType(operationType);
	    
		return budgetOrgTemp;	
	}
	
		/**
	 * 根据主键ID,取得预算组织模版实例
	 * @param id
	 * @return
	 */
	public BudgetOrgTemp _getForEdit(String id)
	{		
	    BudgetOrgTemp budgetOrgTemp = _get(id);
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = budgetOrgTemp.getOperationType();
	    if(OperationType.READONLY.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotEdit);
	    }

		return budgetOrgTemp;	
	}
	
	/**
	 * 根据主键ID,取得预算组织模版实例副本
	 * @param id
	 * @return
	 */
	public BudgetOrgTemp _getEntityCopy(String id)
	{		
	    BudgetOrgTemp budgetOrgTemp = new BudgetOrgTemp();
		BudgetOrgTemp budgetOrgTempOld = this._get(id);
		try
		{
			BeanUtils.copyProperties(budgetOrgTemp, budgetOrgTempOld);
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (InvocationTargetException e)
		{
			e.printStackTrace();
		}
		budgetOrgTemp.setBudorgtemid(null); 
		return budgetOrgTemp;	
	}
	
	/**
	 * 删除  
	 *   
	 * @param budgetOrgTemp
	 */
	public void _delete(BudgetOrgTemp budgetOrgTemp)
	{
		if (null != advanceService)
			advanceService.preDelete(budgetOrgTemp);
	
 		LockService.isBoInstanceLocked(budgetOrgTemp,BudgetOrgTemp.class);
		budgetOrgTempHibernateDao.remove(budgetOrgTemp);

		if (null != advanceService)
			advanceService.postDelete(budgetOrgTemp);
	}

	/**
	 * 根据主键删除
	 * 
	 * @param budgetOrgTempId
	 */
	public void _delete(String budgetOrgTempId,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(budgetOrgTempId))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("budorgtemid"));
		BudgetOrgTemp budgetOrgTemp = this.budgetOrgTempHibernateDao.load(budgetOrgTempId);
		_delete(budgetOrgTemp);
	}
	
	/**
	 * 删除  
	 *   
	 * @param Set<BudgetOrgTemp> budgetOrgTemps
	 */
	public void _deletes(Set<BudgetOrgTemp> budgetOrgTemps,BusinessObject businessObject)
	{
		if (null == budgetOrgTemps || budgetOrgTemps.size() < 1)
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoText());
		Iterator<BudgetOrgTemp> it = budgetOrgTemps.iterator();
		while (it.hasNext())
		{
			BudgetOrgTemp budgetOrgTemp = (BudgetOrgTemp) it.next();
			_delete(budgetOrgTemp);
		}
	}
	
	/**
	 * 根据多个主键(由，分割)删除多个对象
	 * 
	 * @param budgetOrgTempIds
	 */
	public void _deletes(String budgetOrgTempIds,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(budgetOrgTempIds))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("budorgtemid"));
		String[] ids = StringUtils.splitString(budgetOrgTempIds);
		for (int i = 0; i < ids.length; i++)
		{
			_delete(ids[i],businessObject);
		}
	}
	/**
	 * 提交工作流
	 * 
	 * @param budgetOrgTemp
	 */
	public void _submitProcess(BudgetOrgTemp budgetOrgTemp
	,BusinessObject businessObject)
	{
		String id = budgetOrgTemp.getBudorgtemid();
		if (StringUtils.isNullBlank(id))
		{
			_save(budgetOrgTemp);
		}
		else
		{
			_update(budgetOrgTemp
			, businessObject);
		}
		String taskId = budgetOrgTemp.getWorkflowTaskId();
		if (null == taskId || "".equals(taskId))
			WorkflowService.createAndSignalProcessInstance(budgetOrgTemp, id);
		else
			WorkflowService.signalProcessInstance(budgetOrgTemp, id, null);
	}

	/**
	 * 保存或更新预算组织模版
	 * 保存  
	 *  
	 * @param budgetOrgTemp
	 */
	public void _update(BudgetOrgTemp budgetOrgTemp
,BusinessObject businessObject	
	)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(budgetOrgTemp);
		budgetOrgTempHibernateDao.saveOrUpdate(budgetOrgTemp);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(budgetOrgTemp);
	}
	
	/**
	 * 保存  
	 *   
	 * @param budgetOrgTemp
	 */
	public void _save(BudgetOrgTemp budgetOrgTemp)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(budgetOrgTemp);
		// 对id赋空值，使之执行insert操作(适应复制创建场景)
		budgetOrgTemp.setBudorgtemid(null);
                    		budgetOrgTempHibernateDao.saveOrUpdate(budgetOrgTemp);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(budgetOrgTemp);
	}
	
	/**
	 *
	 * 保存  
	 *   
	 * @param budgetOrgTemp
	 */
	public void _saveOrUpdate(BudgetOrgTemp budgetOrgTemp
,BusinessObject businessObject		
	)
	{
		if (StringUtils.isNullBlank(budgetOrgTemp.getBudorgtemid()))
		{	
			_save(budgetOrgTemp);
		}
		else
		{
			_update(budgetOrgTemp
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