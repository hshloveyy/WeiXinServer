/*
 * @(#)AdvanceWarnInfoServiceGen.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年06月01日 14点38分11秒
 *  描　述：创建
 */
package com.infolion.xdss3.advanceWarnGen.service;

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
import com.infolion.xdss3.advanceWarn.domain.AdvanceWarnInfo;
import com.infolion.xdss3.advanceWarn.service.AdvanceWarnInfoService;
import com.infolion.xdss3.advanceWarn.dao.AdvanceWarnInfoHibernateDao;

/**
 * <pre>
 * 业务预警信息(AdvanceWarnInfo)服务类
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
public class AdvanceWarnInfoServiceGen extends BaseService
{
	protected Log log = LogFactory.getFactory().getLog(this.getClass());

	@Autowired
	protected AdvanceWarnInfoHibernateDao advanceWarnInfoHibernateDao;
	
	public void setAdvanceWarnInfoHibernateDao(AdvanceWarnInfoHibernateDao advanceWarnInfoHibernateDao)
	{
		this.advanceWarnInfoHibernateDao = advanceWarnInfoHibernateDao;
	}
	
     /**
	 * 注入扩展服务类
	 */
	 @Autowired(required = false)
	 public void setAdvanceService(@Qualifier("advanceWarnInfoAdvanceService") AdvanceService advanceService)
	 {
	  	super.setAdvanceService(advanceService);
	 }   
	 
	   
	/**
	 * 根据主键ID,取得业务预警信息实例
	 * @param id
	 * @return
	 */
	public AdvanceWarnInfo _getDetached(String id)
	{		
	    AdvanceWarnInfo advanceWarnInfo = new AdvanceWarnInfo();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  advanceWarnInfo = advanceWarnInfoHibernateDao.getDetached(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(advanceWarnInfo);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    advanceWarnInfo.setOperationType(operationType);
	    
		return advanceWarnInfo;	
	}
	
	/**
	 * 根据主键ID,取得业务预警信息实例
	 * @param id
	 * @return
	 */
	public AdvanceWarnInfo _get(String id)
	{		
	    AdvanceWarnInfo advanceWarnInfo = new AdvanceWarnInfo();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  advanceWarnInfo = advanceWarnInfoHibernateDao.get(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(advanceWarnInfo);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    advanceWarnInfo.setOperationType(operationType);
	    
		return advanceWarnInfo;	
	}
	
	/**
	 * 根据主键ID,取得业务预警信息实例
	 * @param id
	 * @return
	 */
	public AdvanceWarnInfo _getForEdit(String id)
	{		
	    AdvanceWarnInfo advanceWarnInfo = _get(id);
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = advanceWarnInfo.getOperationType();
	    if(OperationType.READONLY.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotEdit);
	    }

		return advanceWarnInfo;	
	}
	
	/**
	 * 根据主键ID,取得业务预警信息实例副本
	 * @param id
	 * @return
	 */
	public AdvanceWarnInfo _getEntityCopy(String id)
	{		
	    AdvanceWarnInfo advanceWarnInfo = new AdvanceWarnInfo();
		AdvanceWarnInfo advanceWarnInfoOld = this._get(id);
		try
		{
			BeanUtils.copyProperties(advanceWarnInfo, advanceWarnInfoOld);
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (InvocationTargetException e)
		{
			e.printStackTrace();
		}
		//advanceWarnInfo.setWarninfoid(null); 
		return advanceWarnInfo;	
	}
	
	/**
	 * 删除  
	 *   
	 * @param advanceWarnInfo
	 */
	public void _delete(AdvanceWarnInfo advanceWarnInfo)
	{
		if (null != advanceService)
			advanceService.preDelete(advanceWarnInfo);
	
 		LockService.isBoInstanceLocked(advanceWarnInfo,AdvanceWarnInfo.class);
		advanceWarnInfoHibernateDao.remove(advanceWarnInfo);

		if (null != advanceService)
			advanceService.postDelete(advanceWarnInfo);
	}

	/**
	 * 根据主键删除
	 * 
	 * @param advanceWarnInfoId
	 */
	public void _delete(String advanceWarnInfoId,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(advanceWarnInfoId))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("warninfoid"));
		AdvanceWarnInfo advanceWarnInfo = this.advanceWarnInfoHibernateDao.load(advanceWarnInfoId);
		_delete(advanceWarnInfo);
	}
	
	/**
	 * 删除  
	 *   
	 * @param Set<AdvanceWarnInfo> advanceWarnInfos
	 */
	public void _deletes(Set<AdvanceWarnInfo> advanceWarnInfos,BusinessObject businessObject)
	{
		if (null == advanceWarnInfos || advanceWarnInfos.size() < 1)
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoText());
		Iterator<AdvanceWarnInfo> it = advanceWarnInfos.iterator();
		while (it.hasNext())
		{
			AdvanceWarnInfo advanceWarnInfo = (AdvanceWarnInfo) it.next();
			_delete(advanceWarnInfo);
		}
	}
	
	/**
	 * 根据多个主键(由，分割)删除多个对象
	 * 
	 * @param advanceWarnInfoIds
	 */
	public void _deletes(String advanceWarnInfoIds,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(advanceWarnInfoIds))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("warninfoid"));
		String[] ids = StringUtils.splitString(advanceWarnInfoIds);
		for (int i = 0; i < ids.length; i++)
		{
			_delete(ids[i],businessObject);
		}
	}
	/**
	 * 提交工作流
	 * 
	 * @param advanceWarnInfo
	 */
	public void _submitProcess(AdvanceWarnInfo advanceWarnInfo
	,BusinessObject businessObject)
	{
		String id = advanceWarnInfo.getWarninfoid();
		if (StringUtils.isNullBlank(id))
		{
			_save(advanceWarnInfo);
		}
		else
		{
			_update(advanceWarnInfo
			, businessObject);
		}
		String taskId = advanceWarnInfo.getWorkflowTaskId();
		id = advanceWarnInfo.getWarninfoid();
		if (null == taskId || "".equals(taskId))
			WorkflowService.createAndSignalProcessInstance(advanceWarnInfo, id);
		else
			WorkflowService.signalProcessInstance(advanceWarnInfo, id, null);
	}

	/**
	 * 保存或更新业务预警信息
	 * 保存  
	 *  
	 * @param advanceWarnInfo
	 */
	public void _update(AdvanceWarnInfo advanceWarnInfo
,BusinessObject businessObject	
	)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(advanceWarnInfo);
		advanceWarnInfoHibernateDao.saveOrUpdate(advanceWarnInfo);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(advanceWarnInfo);
	}
	
	/**
	 * 保存  
	 *   
	 * @param advanceWarnInfo
	 */
	public void _save(AdvanceWarnInfo advanceWarnInfo)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(advanceWarnInfo);
		// 对id赋空值，使之执行insert操作(适应复制创建场景)
		advanceWarnInfo.setWarninfoid(null);
                                		advanceWarnInfoHibernateDao.saveOrUpdate(advanceWarnInfo);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(advanceWarnInfo);
	}
	
	/**
	 *
	 * 保存  
	 *   
	 * @param advanceWarnInfo
	 */
	public void _saveOrUpdate(AdvanceWarnInfo advanceWarnInfo
,BusinessObject businessObject		
	)
	{
		if (StringUtils.isNullBlank(advanceWarnInfo.getWarninfoid()))
		{	
			_save(advanceWarnInfo);
		}
		else
		{
			_update(advanceWarnInfo
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