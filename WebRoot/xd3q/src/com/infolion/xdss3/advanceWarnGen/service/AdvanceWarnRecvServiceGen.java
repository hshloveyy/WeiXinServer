/*
 * @(#)AdvanceWarnRecvServiceGen.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年05月19日 11点29分42秒
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
import com.infolion.xdss3.advanceWarn.domain.AdvanceWarnRecv;
import com.infolion.xdss3.advanceWarn.service.AdvanceWarnRecvService;
import com.infolion.xdss3.advanceWarn.dao.AdvanceWarnRecvHibernateDao;

/**
 * <pre>
 * 预警对像接收人(AdvanceWarnRecv)服务类
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
public class AdvanceWarnRecvServiceGen extends BaseService
{
	protected Log log = LogFactory.getFactory().getLog(this.getClass());

	@Autowired
	protected AdvanceWarnRecvHibernateDao advanceWarnRecvHibernateDao;
	
	public void setAdvanceWarnRecvHibernateDao(AdvanceWarnRecvHibernateDao advanceWarnRecvHibernateDao)
	{
		this.advanceWarnRecvHibernateDao = advanceWarnRecvHibernateDao;
	}
	
     /**
	 * 注入扩展服务类
	 */
	 @Autowired(required = false)
	 public void setAdvanceService(@Qualifier("advanceWarnRecvAdvanceService") AdvanceService advanceService)
	 {
	  	super.setAdvanceService(advanceService);
	 }   
	 
	   
	/**
	 * 根据主键ID,取得预警对像接收人实例
	 * @param id
	 * @return
	 */
	public AdvanceWarnRecv _getDetached(String id)
	{		
	    AdvanceWarnRecv advanceWarnRecv = new AdvanceWarnRecv();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  advanceWarnRecv = advanceWarnRecvHibernateDao.getDetached(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(advanceWarnRecv);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    advanceWarnRecv.setOperationType(operationType);
	    
		return advanceWarnRecv;	
	}
	
	/**
	 * 根据主键ID,取得预警对像接收人实例
	 * @param id
	 * @return
	 */
	public AdvanceWarnRecv _get(String id)
	{		
	    AdvanceWarnRecv advanceWarnRecv = new AdvanceWarnRecv();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  advanceWarnRecv = advanceWarnRecvHibernateDao.get(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(advanceWarnRecv);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    advanceWarnRecv.setOperationType(operationType);
	    
		return advanceWarnRecv;	
	}
	
	/**
	 * 根据主键ID,取得预警对像接收人实例
	 * @param id
	 * @return
	 */
	public AdvanceWarnRecv _getForEdit(String id)
	{		
	    AdvanceWarnRecv advanceWarnRecv = _get(id);
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = advanceWarnRecv.getOperationType();
	    if(OperationType.READONLY.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotEdit);
	    }

		return advanceWarnRecv;	
	}
	
	/**
	 * 根据主键ID,取得预警对像接收人实例副本
	 * @param id
	 * @return
	 */
	public AdvanceWarnRecv _getEntityCopy(String id)
	{		
	    AdvanceWarnRecv advanceWarnRecv = new AdvanceWarnRecv();
		AdvanceWarnRecv advanceWarnRecvOld = this._get(id);
		try
		{
			BeanUtils.copyProperties(advanceWarnRecv, advanceWarnRecvOld);
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (InvocationTargetException e)
		{
			e.printStackTrace();
		}
		//advanceWarnRecv.setReceiveid(null); 
		return advanceWarnRecv;	
	}
	
	/**
	 * 删除  
	 *   
	 * @param advanceWarnRecv
	 */
	public void _delete(AdvanceWarnRecv advanceWarnRecv)
	{
		if (null != advanceService)
			advanceService.preDelete(advanceWarnRecv);
	
 		LockService.isBoInstanceLocked(advanceWarnRecv,AdvanceWarnRecv.class);
		advanceWarnRecvHibernateDao.remove(advanceWarnRecv);

		if (null != advanceService)
			advanceService.postDelete(advanceWarnRecv);
	}

	/**
	 * 根据主键删除
	 * 
	 * @param advanceWarnRecvId
	 */
	public void _delete(String advanceWarnRecvId,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(advanceWarnRecvId))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("receiveid"));
		AdvanceWarnRecv advanceWarnRecv = this.advanceWarnRecvHibernateDao.load(advanceWarnRecvId);
		_delete(advanceWarnRecv);
	}
	
	/**
	 * 删除  
	 *   
	 * @param Set<AdvanceWarnRecv> advanceWarnRecvs
	 */
	public void _deletes(Set<AdvanceWarnRecv> advanceWarnRecvs,BusinessObject businessObject)
	{
		if (null == advanceWarnRecvs || advanceWarnRecvs.size() < 1)
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoText());
		Iterator<AdvanceWarnRecv> it = advanceWarnRecvs.iterator();
		while (it.hasNext())
		{
			AdvanceWarnRecv advanceWarnRecv = (AdvanceWarnRecv) it.next();
			_delete(advanceWarnRecv);
		}
	}
	
	/**
	 * 根据多个主键(由，分割)删除多个对象
	 * 
	 * @param advanceWarnRecvIds
	 */
	public void _deletes(String advanceWarnRecvIds,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(advanceWarnRecvIds))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("receiveid"));
		String[] ids = StringUtils.splitString(advanceWarnRecvIds);
		for (int i = 0; i < ids.length; i++)
		{
			_delete(ids[i],businessObject);
		}
	}
	/**
	 * 提交工作流
	 * 
	 * @param advanceWarnRecv
	 */
	public void _submitProcess(AdvanceWarnRecv advanceWarnRecv
	,BusinessObject businessObject)
	{
		String id = advanceWarnRecv.getReceiveid();
		if (StringUtils.isNullBlank(id))
		{
			_save(advanceWarnRecv);
		}
		else
		{
			_update(advanceWarnRecv
			, businessObject);
		}
		String taskId = advanceWarnRecv.getWorkflowTaskId();
		id = advanceWarnRecv.getReceiveid();
		if (null == taskId || "".equals(taskId))
			WorkflowService.createAndSignalProcessInstance(advanceWarnRecv, id);
		else
			WorkflowService.signalProcessInstance(advanceWarnRecv, id, null);
	}

	/**
	 * 保存或更新预警对像接收人
	 * 保存  
	 *  
	 * @param advanceWarnRecv
	 */
	public void _update(AdvanceWarnRecv advanceWarnRecv
,BusinessObject businessObject	
	)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(advanceWarnRecv);
		advanceWarnRecvHibernateDao.saveOrUpdate(advanceWarnRecv);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(advanceWarnRecv);
	}
	
	/**
	 * 保存  
	 *   
	 * @param advanceWarnRecv
	 */
	public void _save(AdvanceWarnRecv advanceWarnRecv)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(advanceWarnRecv);
		// 对id赋空值，使之执行insert操作(适应复制创建场景)
		advanceWarnRecv.setReceiveid(null);
                      		advanceWarnRecvHibernateDao.saveOrUpdate(advanceWarnRecv);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(advanceWarnRecv);
	}
	
	/**
	 *
	 * 保存  
	 *   
	 * @param advanceWarnRecv
	 */
	public void _saveOrUpdate(AdvanceWarnRecv advanceWarnRecv
,BusinessObject businessObject		
	)
	{
		if (StringUtils.isNullBlank(advanceWarnRecv.getReceiveid()))
		{	
			_save(advanceWarnRecv);
		}
		else
		{
			_update(advanceWarnRecv
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