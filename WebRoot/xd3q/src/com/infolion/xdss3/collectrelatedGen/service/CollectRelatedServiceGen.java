/*
 * @(#)CollectRelatedServiceGen.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年08月10日 02点20分25秒
 *  描　述：创建
 */
package com.infolion.xdss3.collectrelatedGen.service;

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
import com.infolion.xdss3.collectrelated.domain.CollectRelated;
import com.infolion.xdss3.collectrelated.service.CollectRelatedService;
import com.infolion.xdss3.collectrelated.dao.CollectRelatedHibernateDao;

/**
 * <pre>
 * 收款关联单据(CollectRelated)服务类
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
public class CollectRelatedServiceGen extends BaseService
{
	protected Log log = LogFactory.getFactory().getLog(this.getClass());

	@Autowired
	protected CollectRelatedHibernateDao collectRelatedHibernateDao;
	
	public void setCollectRelatedHibernateDao(CollectRelatedHibernateDao collectRelatedHibernateDao)
	{
		this.collectRelatedHibernateDao = collectRelatedHibernateDao;
	}
	
     /**
	 * 注入扩展服务类
	 */
	 @Autowired(required = false)
	 public void setAdvanceService(@Qualifier("collectRelatedAdvanceService") AdvanceService advanceService)
	 {
	  	super.setAdvanceService(advanceService);
	 }   
	 
	   
	/**
	 * 根据主键ID,取得收款关联单据实例
	 * @param id
	 * @return
	 */
	public CollectRelated _getDetached(String id)
	{		
	    CollectRelated collectRelated = new CollectRelated();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  collectRelated = collectRelatedHibernateDao.getDetached(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(collectRelated);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    collectRelated.setOperationType(operationType);
	    
		return collectRelated;	
	}
	
	/**
	 * 根据主键ID,取得收款关联单据实例
	 * @param id
	 * @return
	 */
	public CollectRelated _get(String id)
	{		
	    CollectRelated collectRelated = new CollectRelated();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  collectRelated = collectRelatedHibernateDao.get(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(collectRelated);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    collectRelated.setOperationType(operationType);
	    
		return collectRelated;	
	}
	
	/**
	 * 根据主键ID,取得收款关联单据实例
	 * @param id
	 * @return
	 */
	public CollectRelated _getForEdit(String id)
	{		
	    CollectRelated collectRelated = _get(id);
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = collectRelated.getOperationType();
	    if(OperationType.READONLY.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotEdit);
	    }

		return collectRelated;	
	}
	
	/**
	 * 根据主键ID,取得收款关联单据实例副本
	 * @param id
	 * @return
	 */
	public CollectRelated _getEntityCopy(String id)
	{		
	    CollectRelated collectRelated = new CollectRelated();
		CollectRelated collectRelatedOld = this._get(id);
		try
		{
			BeanUtils.copyProperties(collectRelated, collectRelatedOld);
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (InvocationTargetException e)
		{
			e.printStackTrace();
		}
		//collectRelated.setCollectrelatedid(null); 
		return collectRelated;	
	}
	
	/**
	 * 删除  
	 *   
	 * @param collectRelated
	 */
	public void _delete(CollectRelated collectRelated)
	{
		if (null != advanceService)
			advanceService.preDelete(collectRelated);
	
 		LockService.isBoInstanceLocked(collectRelated,CollectRelated.class);
		collectRelatedHibernateDao.remove(collectRelated);

		if (null != advanceService)
			advanceService.postDelete(collectRelated);
	}

	/**
	 * 根据主键删除
	 * 
	 * @param collectRelatedId
	 */
	public void _delete(String collectRelatedId,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(collectRelatedId))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("collectrelatedid"));
		CollectRelated collectRelated = this.collectRelatedHibernateDao.load(collectRelatedId);
		_delete(collectRelated);
	}
	
	/**
	 * 删除  
	 *   
	 * @param Set<CollectRelated> collectRelateds
	 */
	public void _deletes(Set<CollectRelated> collectRelateds,BusinessObject businessObject)
	{
		if (null == collectRelateds || collectRelateds.size() < 1)
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoText());
		Iterator<CollectRelated> it = collectRelateds.iterator();
		while (it.hasNext())
		{
			CollectRelated collectRelated = (CollectRelated) it.next();
			_delete(collectRelated);
		}
	}
	
	/**
	 * 根据多个主键(由，分割)删除多个对象
	 * 
	 * @param collectRelatedIds
	 */
	public void _deletes(String collectRelatedIds,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(collectRelatedIds))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("collectrelatedid"));
		String[] ids = StringUtils.splitString(collectRelatedIds);
		for (int i = 0; i < ids.length; i++)
		{
			_delete(ids[i],businessObject);
		}
	}
	/**
	 * 提交工作流
	 * 
	 * @param collectRelated
	 */
	public void _submitProcess(CollectRelated collectRelated
	,BusinessObject businessObject)
	{
		String id = collectRelated.getCollectrelatedid();
        /**
		if (StringUtils.isNullBlank(id))
		{
			_save(collectRelated);
		}
		else
		{
			_update(collectRelated
			, businessObject);
		}**/
		
		String taskId = collectRelated.getWorkflowTaskId();
		id = collectRelated.getCollectrelatedid();
		if (null == taskId || "".equals(taskId))
			WorkflowService.createAndSignalProcessInstance(collectRelated, id);
		else
			WorkflowService.signalProcessInstance(collectRelated, id, null);
	}

	/**
	 * 保存或更新收款关联单据
	 * 保存  
	 *  
	 * @param collectRelated
	 */
	public void _update(CollectRelated collectRelated
,BusinessObject businessObject	
	)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(collectRelated);
		collectRelatedHibernateDao.saveOrUpdate(collectRelated);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(collectRelated);
	}
	
	/**
	 * 保存  
	 *   
	 * @param collectRelated
	 */
	public void _save(CollectRelated collectRelated)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(collectRelated);
		// 对id赋空值，使之执行insert操作(适应复制创建场景)
		collectRelated.setCollectrelatedid(null);
                    		collectRelatedHibernateDao.saveOrUpdate(collectRelated);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(collectRelated);
	}
	
	/**
	 *
	 * 保存  
	 *   
	 * @param collectRelated
	 */
	public void _saveOrUpdate(CollectRelated collectRelated
,BusinessObject businessObject		
	)
	{
		if (StringUtils.isNullBlank(collectRelated.getCollectrelatedid()))
		{	
			_save(collectRelated);
		}
		else
		{
			_update(collectRelated
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