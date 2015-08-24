/*
 * @(#)ReassignServiceGen.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年07月15日 09点01分38秒
 *  描　述：创建
 */
package com.infolion.xdss3.reassignGen.service;

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
import com.infolion.xdss3.reassign.domain.Reassign;
import com.infolion.xdss3.reassign.service.ReassignService;
import com.infolion.xdss3.reassign.dao.ReassignHibernateDao;

/**
 * <pre>
 * 重分配(Reassign)服务类
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
public class ReassignServiceGen extends BaseService
{
	protected Log log = LogFactory.getFactory().getLog(this.getClass());

	@Autowired
	protected ReassignHibernateDao reassignHibernateDao;
	
	public void setReassignHibernateDao(ReassignHibernateDao reassignHibernateDao)
	{
		this.reassignHibernateDao = reassignHibernateDao;
	}
	
     /**
	 * 注入扩展服务类
	 */
	 @Autowired(required = false)
	 public void setAdvanceService(@Qualifier("reassignAdvanceService") AdvanceService advanceService)
	 {
	  	super.setAdvanceService(advanceService);
	 }   
	 
	   
	/**
	 * 根据主键ID,取得重分配实例
	 * @param id
	 * @return
	 */
	public Reassign _getDetached(String id)
	{		
	    Reassign reassign = new Reassign();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  reassign = reassignHibernateDao.getDetached(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(reassign);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    reassign.setOperationType(operationType);
	    
		return reassign;	
	}
	
	/**
	 * 根据主键ID,取得重分配实例
	 * @param id
	 * @return
	 */
	public Reassign _get(String id)
	{		
	    Reassign reassign = new Reassign();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  reassign = reassignHibernateDao.get(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(reassign);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    reassign.setOperationType(operationType);
	    
		return reassign;	
	}
	
	/**
	 * 根据主键ID,取得重分配实例
	 * @param id
	 * @return
	 */
	public Reassign _getForEdit(String id)
	{		
	    Reassign reassign = _get(id);
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = reassign.getOperationType();
	    if(OperationType.READONLY.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotEdit);
	    }

		return reassign;	
	}
	
	/**
	 * 根据主键ID,取得重分配实例副本
	 * @param id
	 * @return
	 */
	public Reassign _getEntityCopy(String id)
	{		
	    Reassign reassign = new Reassign();
		Reassign reassignOld = this._get(id);
		try
		{
			BeanUtils.copyProperties(reassign, reassignOld);
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (InvocationTargetException e)
		{
			e.printStackTrace();
		}
		//reassign.setReassignid(null); 
		reassign.setProcessstate(" ");
		return reassign;	
	}
	
	/**
	 * 删除  
	 *   
	 * @param reassign
	 */
	public void _delete(Reassign reassign)
	{
		if (null != advanceService)
			advanceService.preDelete(reassign);
	
		//流程状态
		String processState =reassign.getProcessstate();
		if(!StringUtils.isNullBlank(processState))
		{
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.CannotDeleteApproveData);
		}
 		LockService.isBoInstanceLocked(reassign,Reassign.class);
		reassignHibernateDao.remove(reassign);

		if (null != advanceService)
			advanceService.postDelete(reassign);
	}

	/**
	 * 根据主键删除
	 * 
	 * @param reassignId
	 */
	public void _delete(String reassignId,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(reassignId))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("reassignid"));
		Reassign reassign = this.reassignHibernateDao.load(reassignId);
		_delete(reassign);
	}
	
	/**
	 * 删除  
	 *   
	 * @param Set<Reassign> reassigns
	 */
	public void _deletes(Set<Reassign> reassigns,BusinessObject businessObject)
	{
		if (null == reassigns || reassigns.size() < 1)
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoText());
		Iterator<Reassign> it = reassigns.iterator();
		while (it.hasNext())
		{
			Reassign reassign = (Reassign) it.next();
			_delete(reassign);
		}
	}
	
	/**
	 * 根据多个主键(由，分割)删除多个对象
	 * 
	 * @param reassignIds
	 */
	public void _deletes(String reassignIds,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(reassignIds))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("reassignid"));
		String[] ids = StringUtils.splitString(reassignIds);
		for (int i = 0; i < ids.length; i++)
		{
			_delete(ids[i],businessObject);
		}
	}
	/**
	 * 提交工作流
	 * 
	 * @param reassign
	 */
	public void _submitProcess(Reassign reassign
	,BusinessObject businessObject)
	{
		String id = reassign.getReassignid();
        /**
		if (StringUtils.isNullBlank(id))
		{
			_save(reassign);
		}
		else
		{
			_update(reassign
			, businessObject);
		}**/
		
		String taskId = reassign.getWorkflowTaskId();
		id = reassign.getReassignid();
		if (null == taskId || "".equals(taskId))
			WorkflowService.createAndSignalProcessInstance(reassign, id);
		else
			WorkflowService.signalProcessInstance(reassign, id, null);
	}

	/**
	 * 保存或更新重分配
	 * 保存  
	 *  
	 * @param reassign
	 */
	public void _update(Reassign reassign
,BusinessObject businessObject	
	)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(reassign);
		reassignHibernateDao.saveOrUpdate(reassign);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(reassign);
	}
	
	/**
	 * 保存  
	 *   
	 * @param reassign
	 */
	public void _save(Reassign reassign)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(reassign);
		// 对id赋空值，使之执行insert操作(适应复制创建场景)
		reassign.setReassignid(null);
                        		reassignHibernateDao.saveOrUpdate(reassign);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(reassign);
	}
	
	/**
	 *
	 * 保存  
	 *   
	 * @param reassign
	 */
	public void _saveOrUpdate(Reassign reassign
,BusinessObject businessObject		
	)
	{
		if (StringUtils.isNullBlank(reassign.getReassignid()))
		{	
			_save(reassign);
		}
		else
		{
			_update(reassign
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