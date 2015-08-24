/*
 * @(#)OverCollectServiceGen.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年06月01日 06点24分20秒
 *  描　述：创建
 */
package com.infolion.xdss3.overCollectGen.service;

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
import com.infolion.xdss3.overCollect.domain.OverCollect;
import com.infolion.xdss3.overCollect.service.OverCollectService;
import com.infolion.xdss3.overCollect.dao.OverCollectHibernateDao;

/**
 * <pre>
 * 多收款表(OverCollect)服务类
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
public class OverCollectServiceGen extends BaseService
{
	protected Log log = LogFactory.getFactory().getLog(this.getClass());

	@Autowired
	protected OverCollectHibernateDao overCollectHibernateDao;
	
	public void setOverCollectHibernateDao(OverCollectHibernateDao overCollectHibernateDao)
	{
		this.overCollectHibernateDao = overCollectHibernateDao;
	}
	
     /**
	 * 注入扩展服务类
	 */
	 @Autowired(required = false)
	 public void setAdvanceService(@Qualifier("overCollectAdvanceService") AdvanceService advanceService)
	 {
	  	super.setAdvanceService(advanceService);
	 }   
	 
	   
	/**
	 * 根据主键ID,取得多收款表实例
	 * @param id
	 * @return
	 */
	public OverCollect _getDetached(String id)
	{		
	    OverCollect overCollect = new OverCollect();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  overCollect = overCollectHibernateDao.getDetached(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(overCollect);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    overCollect.setOperationType(operationType);
	    
		return overCollect;	
	}
	
	/**
	 * 根据主键ID,取得多收款表实例
	 * @param id
	 * @return
	 */
	public OverCollect _get(String id)
	{		
	    OverCollect overCollect = new OverCollect();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  overCollect = overCollectHibernateDao.get(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(overCollect);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    overCollect.setOperationType(operationType);
	    
		return overCollect;	
	}
	
	/**
	 * 根据主键ID,取得多收款表实例
	 * @param id
	 * @return
	 */
	public OverCollect _getForEdit(String id)
	{		
	    OverCollect overCollect = _get(id);
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = overCollect.getOperationType();
	    if(OperationType.READONLY.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotEdit);
	    }

		return overCollect;	
	}
	
	/**
	 * 根据主键ID,取得多收款表实例副本
	 * @param id
	 * @return
	 */
	public OverCollect _getEntityCopy(String id)
	{		
	    OverCollect overCollect = new OverCollect();
		OverCollect overCollectOld = this._get(id);
		try
		{
			BeanUtils.copyProperties(overCollect, overCollectOld);
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (InvocationTargetException e)
		{
			e.printStackTrace();
		}
		//overCollect.setOvercollectid(null); 
		return overCollect;	
	}
	/**
	 * 提交工作流
	 * 
	 * @param overCollect
	 */
	public void _submitProcess(OverCollect overCollect
	,BusinessObject businessObject)
	{
		String id = overCollect.getOvercollectid();
		if (StringUtils.isNullBlank(id))
		{
			_save(overCollect);
		}
		else
		{
			_update(overCollect
			, businessObject);
		}
		String taskId = overCollect.getWorkflowTaskId();
		id = overCollect.getOvercollectid();
		if (null == taskId || "".equals(taskId))
			WorkflowService.createAndSignalProcessInstance(overCollect, id);
		else
			WorkflowService.signalProcessInstance(overCollect, id, null);
	}

	/**
	 * 保存或更新多收款表
	 * 保存  
	 *  
	 * @param overCollect
	 */
	public void _update(OverCollect overCollect
,BusinessObject businessObject	
	)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(overCollect);
		overCollectHibernateDao.saveOrUpdate(overCollect);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(overCollect);
	}
	
	/**
	 * 保存  
	 *   
	 * @param overCollect
	 */
	public void _save(OverCollect overCollect)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(overCollect);
		// 对id赋空值，使之执行insert操作(适应复制创建场景)
		overCollect.setOvercollectid(null);
                              		overCollectHibernateDao.saveOrUpdate(overCollect);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(overCollect);
	}
	
	/**
	 *
	 * 保存  
	 *   
	 * @param overCollect
	 */
	public void _saveOrUpdate(OverCollect overCollect
,BusinessObject businessObject		
	)
	{
		if (StringUtils.isNullBlank(overCollect.getOvercollectid()))
		{	
			_save(overCollect);
		}
		else
		{
			_update(overCollect
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
	
	/**
	 * 删除  
	 *   
	 * @param overCollect
	 */
	public void _delete(OverCollect overCollect)
	{
		if (null != advanceService)
			advanceService.preDelete(overCollect);
	
 		LockService.isBoInstanceLocked(overCollect,OverCollect.class);
		overCollectHibernateDao.remove(overCollect);

		if (null != advanceService)
			advanceService.postDelete(overCollect);
	}

	/**
	 * 根据主键删除
	 * 
	 * @param overCollectId
	 */
	public void _delete(String overCollectId,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(overCollectId))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("overcollectid"));
		OverCollect overCollect = this.overCollectHibernateDao.load(overCollectId);
		_delete(overCollect);
	}
	
	/**
	 * 删除  
	 *   
	 * @param Set<OverCollect> overCollects
	 */
	public void _deletes(Set<OverCollect> overCollects,BusinessObject businessObject)
	{
		if (null == overCollects || overCollects.size() < 1)
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoText());
		Iterator<OverCollect> it = overCollects.iterator();
		while (it.hasNext())
		{
			OverCollect overCollect = (OverCollect) it.next();
			_delete(overCollect);
		}
	}
	
	/**
	 * 根据多个主键(由，分割)删除多个对象
	 * 
	 * @param overCollectIds
	 */
	public void _deletes(String overCollectIds,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(overCollectIds))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("overcollectid"));
		String[] ids = StringUtils.splitString(overCollectIds);
		for (int i = 0; i < ids.length; i++)
		{
			_delete(ids[i],businessObject);
		}
	}
}