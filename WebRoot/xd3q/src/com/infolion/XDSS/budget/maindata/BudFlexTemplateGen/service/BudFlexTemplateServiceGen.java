/*
 * @(#)BudFlexTemplateServiceGen.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年03月08日 08点36分10秒
 *  描　述：创建
 */
package com.infolion.XDSS.budget.maindata.BudFlexTemplateGen.service;

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
import com.infolion.XDSS.budget.maindata.BudFlexTemplate.domain.BudFlexTemplate;
import com.infolion.XDSS.budget.maindata.BudFlexTemplate.service.BudFlexTemplateService;
import com.infolion.XDSS.budget.maindata.BudFlexTemplate.dao.BudFlexTemplateHibernateDao;

/**
 * <pre>
 * 预算模版flex文件流(BudFlexTemplate)服务类
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
public class BudFlexTemplateServiceGen extends BaseService
{
	protected Log log = LogFactory.getFactory().getLog(this.getClass());

	@Autowired
	protected BudFlexTemplateHibernateDao budFlexTemplateHibernateDao;
	
	public void setBudFlexTemplateHibernateDao(BudFlexTemplateHibernateDao budFlexTemplateHibernateDao)
	{
		this.budFlexTemplateHibernateDao = budFlexTemplateHibernateDao;
	}
	
     /**
	 * 注入扩展服务类
	 */
	 @Autowired(required = false)
	 public void setAdvanceService(@Qualifier("budFlexTemplateAdvanceService") AdvanceService advanceService)
	 {
	  	super.setAdvanceService(advanceService);
	 }   
	 
	   
	/**
	 * 根据主键ID,取得预算模版flex文件流实例
	 * @param id
	 * @return
	 */
	public BudFlexTemplate _get(String id)
	{		
	    BudFlexTemplate budFlexTemplate = new BudFlexTemplate();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  budFlexTemplate = budFlexTemplateHibernateDao.get(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(budFlexTemplate);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    budFlexTemplate.setOperationType(operationType);
	    
		return budFlexTemplate;	
	}
	
		/**
	 * 根据主键ID,取得预算模版flex文件流实例
	 * @param id
	 * @return
	 */
	public BudFlexTemplate _getForEdit(String id)
	{		
	    BudFlexTemplate budFlexTemplate = _get(id);
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = budFlexTemplate.getOperationType();
	    if(OperationType.READONLY.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotEdit);
	    }

		return budFlexTemplate;	
	}
	
	/**
	 * 根据主键ID,取得预算模版flex文件流实例副本
	 * @param id
	 * @return
	 */
	public BudFlexTemplate _getEntityCopy(String id)
	{		
	    BudFlexTemplate budFlexTemplate = new BudFlexTemplate();
		BudFlexTemplate budFlexTemplateOld = this._get(id);
		try
		{
			BeanUtils.copyProperties(budFlexTemplate, budFlexTemplateOld);
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (InvocationTargetException e)
		{
			e.printStackTrace();
		}
		budFlexTemplate.setBudsortid(null); 
		return budFlexTemplate;	
	}
	
	/**
	 * 删除  
	 *   
	 * @param budFlexTemplate
	 */
	public void _delete(BudFlexTemplate budFlexTemplate)
	{
		if (null != advanceService)
			advanceService.preDelete(budFlexTemplate);
	
 		LockService.isBoInstanceLocked(budFlexTemplate,BudFlexTemplate.class);
		budFlexTemplateHibernateDao.remove(budFlexTemplate);

		if (null != advanceService)
			advanceService.postDelete(budFlexTemplate);
	}

	/**
	 * 根据主键删除
	 * 
	 * @param budFlexTemplateId
	 */
	public void _delete(String budFlexTemplateId,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(budFlexTemplateId))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("budsortid"));
		BudFlexTemplate budFlexTemplate = this.budFlexTemplateHibernateDao.load(budFlexTemplateId);
		_delete(budFlexTemplate);
	}
	
	/**
	 * 删除  
	 *   
	 * @param Set<BudFlexTemplate> budFlexTemplates
	 */
	public void _deletes(Set<BudFlexTemplate> budFlexTemplates,BusinessObject businessObject)
	{
		if (null == budFlexTemplates || budFlexTemplates.size() < 1)
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoText());
		Iterator<BudFlexTemplate> it = budFlexTemplates.iterator();
		while (it.hasNext())
		{
			BudFlexTemplate budFlexTemplate = (BudFlexTemplate) it.next();
			_delete(budFlexTemplate);
		}
	}
	
	/**
	 * 根据多个主键(由，分割)删除多个对象
	 * 
	 * @param budFlexTemplateIds
	 */
	public void _deletes(String budFlexTemplateIds,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(budFlexTemplateIds))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("budsortid"));
		String[] ids = StringUtils.splitString(budFlexTemplateIds);
		for (int i = 0; i < ids.length; i++)
		{
			_delete(ids[i],businessObject);
		}
	}
	/**
	 * 提交工作流
	 * 
	 * @param budFlexTemplate
	 */
	public void _submitProcess(BudFlexTemplate budFlexTemplate
	,BusinessObject businessObject)
	{
		String id = budFlexTemplate.getBudsortid();
		if (StringUtils.isNullBlank(id))
		{
			_save(budFlexTemplate);
		}
		else
		{
			_update(budFlexTemplate
			, businessObject);
		}
		String taskId = budFlexTemplate.getWorkflowTaskId();
		if (null == taskId || "".equals(taskId))
			WorkflowService.createAndSignalProcessInstance(budFlexTemplate, id);
		else
			WorkflowService.signalProcessInstance(budFlexTemplate, id, null);
	}

	/**
	 * 保存或更新预算模版flex文件流
	 * 保存  
	 *  
	 * @param budFlexTemplate
	 */
	public void _update(BudFlexTemplate budFlexTemplate
,BusinessObject businessObject	
	)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(budFlexTemplate);
		budFlexTemplateHibernateDao.saveOrUpdate(budFlexTemplate);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(budFlexTemplate);
	}
	
	/**
	 * 保存  
	 *   
	 * @param budFlexTemplate
	 */
	public void _save(BudFlexTemplate budFlexTemplate)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(budFlexTemplate);
		// 对id赋空值，使之执行insert操作(适应复制创建场景)
		budFlexTemplate.setBudsortid(null);
            		budFlexTemplateHibernateDao.saveOrUpdate(budFlexTemplate);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(budFlexTemplate);
	}
	
	/**
	 *
	 * 保存  
	 *   
	 * @param budFlexTemplate
	 */
	public void _saveOrUpdate(BudFlexTemplate budFlexTemplate
,BusinessObject businessObject		
	)
	{
		if (StringUtils.isNullBlank(budFlexTemplate.getBudsortid()))
		{	
			_save(budFlexTemplate);
		}
		else
		{
			_update(budFlexTemplate
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