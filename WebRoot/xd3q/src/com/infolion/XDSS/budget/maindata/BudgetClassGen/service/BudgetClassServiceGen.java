/*
 * @(#)BudgetClassServiceGen.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年03月11日 12点13分08秒
 *  描　述：创建
 */
package com.infolion.XDSS.budget.maindata.BudgetClassGen.service;

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
import com.infolion.XDSS.budget.maindata.BudgetClass.domain.BudgetClass;
import com.infolion.XDSS.budget.maindata.BudgetClass.service.BudgetClassService;
import com.infolion.XDSS.budget.maindata.BudgetClass.dao.BudgetClassHibernateDao;
          
import com.infolion.XDSS.budget.maindata.BudgetTemplate.domain.BudgetTemplate;
import com.infolion.XDSS.budget.maindata.BudgetTemplate.service.BudgetTemplateService;

/**
 * <pre>
 * 预算分类(BudgetClass)服务类
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
/**
 * @author fuyuanyuan
 *
 */
@Service
public class BudgetClassServiceGen extends BaseService
{
	protected Log log = LogFactory.getFactory().getLog(this.getClass());

	@Autowired
	protected BudgetClassHibernateDao budgetClassHibernateDao;
	
	public void setBudgetClassHibernateDao(BudgetClassHibernateDao budgetClassHibernateDao)
	{
		this.budgetClassHibernateDao = budgetClassHibernateDao;
	}
	
     /**
	 * 注入扩展服务类
	 */
	 @Autowired(required = false)
	 public void setAdvanceService(@Qualifier("budgetClassAdvanceService") AdvanceService advanceService)
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
	public BudgetClass _get(String id)
	{		
	    BudgetClass budgetClass = new BudgetClass();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  budgetClass = budgetClassHibernateDao.get(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(budgetClass);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    budgetClass.setOperationType(operationType);
	    
		return budgetClass;	
	}
	
		/**
	 * 根据主键ID,取得预算分类实例
	 * @param id
	 * @return
	 */
	public BudgetClass _getForEdit(String id)
	{		
	    BudgetClass budgetClass = _get(id);
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = budgetClass.getOperationType();
	    if(OperationType.READONLY.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotEdit);
	    }

		return budgetClass;	
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public BudgetClass _getDetached(String id)
	{
		return this.budgetClassHibernateDao.getDetached(id);
	}
	/**
	 * 根据主键ID,取得预算分类实例副本
	 * @param id
	 * @return
	 */
	public BudgetClass _getEntityCopy(String id)
	{		
	    BudgetClass budgetClass = new BudgetClass();
		BudgetClass budgetClassOld = this._get(id);
		try
		{
			BeanUtils.copyProperties(budgetClass, budgetClassOld);
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (InvocationTargetException e)
		{
			e.printStackTrace();
		}
		budgetClass.setBudclassid(null); 
		return budgetClass;	
	}
	
	/**
	 * 删除  
	 *   
	 * @param budgetClass
	 */
	public void _delete(BudgetClass budgetClass)
	{
		if (null != advanceService)
			advanceService.preDelete(budgetClass);
	
 		LockService.isBoInstanceLocked(budgetClass,BudgetClass.class);
		budgetClassHibernateDao.remove(budgetClass);

		if (null != advanceService)
			advanceService.postDelete(budgetClass);
	}

	/**
	 * 根据主键删除
	 * 
	 * @param budgetClassId
	 */
	public void _delete(String budgetClassId,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(budgetClassId))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("budclassid"));
		BudgetClass budgetClass = this.budgetClassHibernateDao.load(budgetClassId);
		_delete(budgetClass);
	}
	
	/**
	 * 删除  
	 *   
	 * @param Set<BudgetClass> budgetClasss
	 */
	public void _deletes(Set<BudgetClass> budgetClasss,BusinessObject businessObject)
	{
		if (null == budgetClasss || budgetClasss.size() < 1)
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoText());
		Iterator<BudgetClass> it = budgetClasss.iterator();
		while (it.hasNext())
		{
			BudgetClass budgetClass = (BudgetClass) it.next();
			_delete(budgetClass);
		}
	}
	
	/**
	 * 根据多个主键(由，分割)删除多个对象
	 * 
	 * @param budgetClassIds
	 */
	public void _deletes(String budgetClassIds,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(budgetClassIds))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("budclassid"));
		String[] ids = StringUtils.splitString(budgetClassIds);
		for (int i = 0; i < ids.length; i++)
		{
			_delete(ids[i],businessObject);
		}
	}
	/**
	 * 提交工作流
	 * 
	 * @param budgetClass
	 */
	public void _submitProcess(BudgetClass budgetClass
,Set<BudgetTemplate> deletedBudgetTemplateSet	,BusinessObject businessObject)
	{
		String id = budgetClass.getBudclassid();
		if (StringUtils.isNullBlank(id))
		{
			_save(budgetClass);
		}
		else
		{
			_update(budgetClass
,deletedBudgetTemplateSet			, businessObject);
		}
		String taskId = budgetClass.getWorkflowTaskId();
		if (null == taskId || "".equals(taskId))
			WorkflowService.createAndSignalProcessInstance(budgetClass, id);
		else
			WorkflowService.signalProcessInstance(budgetClass, id, null);
	}

	/**
	 * 保存或更新预算分类
	 * 保存  
	 *  
	 * @param budgetClass
	 */
	public void _update(BudgetClass budgetClass
,Set<BudgetTemplate> deletedBudgetTemplateSet,BusinessObject businessObject	
	)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(budgetClass);
		budgetClassHibernateDao.saveOrUpdate(budgetClass);
// 删除关联子业务对象数据
if(deletedBudgetTemplateSet!=null && deletedBudgetTemplateSet.size()>0)
{
budgetTemplateService._deletes(deletedBudgetTemplateSet,businessObject);
}		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(budgetClass);
	}
	
	/**
	 * 保存  
	 *   
	 * @param budgetClass
	 */
	public void _save(BudgetClass budgetClass)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(budgetClass);
		// 对id赋空值，使之执行insert操作(适应复制创建场景)
		budgetClass.setBudclassid(null);
       
		Set<BudgetTemplate> budgetTemplateSet = budgetClass.getBudgetTemplate();
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
		budgetClass.setBudgetTemplate(newBudgetTemplateSet);
       
       
       
       
       
       
       
       
       
       
       
		budgetClassHibernateDao.saveOrUpdate(budgetClass);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(budgetClass);
	}
	
	/**
	 *
	 * 保存  
	 *   
	 * @param budgetClass
	 */
	public void _saveOrUpdate(BudgetClass budgetClass
,Set<BudgetTemplate> deletedBudgetTemplateSet,BusinessObject businessObject		
	)
	{
		if (StringUtils.isNullBlank(budgetClass.getBudclassid()))
		{	
			_save(budgetClass);
		}
		else
		{
			_update(budgetClass
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