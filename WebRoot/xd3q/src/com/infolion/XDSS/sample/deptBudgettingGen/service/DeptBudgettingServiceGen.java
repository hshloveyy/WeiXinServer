/*
 * @(#)DeptBudgettingServiceGen.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年04月12日 11点34分35秒
 *  描　述：创建
 */
package com.infolion.XDSS.sample.deptBudgettingGen.service;

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
import com.infolion.XDSS.sample.deptBudgetting.domain.DeptBudgetting;
import com.infolion.XDSS.sample.deptBudgetting.service.DeptBudgettingService;
import com.infolion.XDSS.sample.deptBudgetting.dao.DeptBudgettingHibernateDao;

/**
 * <pre>
 * 部门预算编制(DeptBudgetting)服务类
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
public class DeptBudgettingServiceGen extends BaseService
{
	protected Log log = LogFactory.getFactory().getLog(this.getClass());

	@Autowired
	protected DeptBudgettingHibernateDao deptBudgettingHibernateDao;
	
	public void setDeptBudgettingHibernateDao(DeptBudgettingHibernateDao deptBudgettingHibernateDao)
	{
		this.deptBudgettingHibernateDao = deptBudgettingHibernateDao;
	}
	
     /**
	 * 注入扩展服务类
	 */
	 @Autowired(required = false)
	 public void setAdvanceService(@Qualifier("deptBudgettingAdvanceService") AdvanceService advanceService)
	 {
	  	super.setAdvanceService(advanceService);
	 }   
	 
	   
	/**
	 * 根据主键ID,取得部门预算编制实例
	 * @param id
	 * @return
	 */
	public DeptBudgetting _getDetached(String id)
	{		
	    DeptBudgetting deptBudgetting = new DeptBudgetting();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  deptBudgetting = deptBudgettingHibernateDao.getDetached(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(deptBudgetting);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    deptBudgetting.setOperationType(operationType);
	    
		return deptBudgetting;	
	}
	
	/**
	 * 根据主键ID,取得部门预算编制实例
	 * @param id
	 * @return
	 */
	public DeptBudgetting _get(String id)
	{		
	    DeptBudgetting deptBudgetting = new DeptBudgetting();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  deptBudgetting = deptBudgettingHibernateDao.get(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(deptBudgetting);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    deptBudgetting.setOperationType(operationType);
	    
		return deptBudgetting;	
	}
	
	/**
	 * 根据主键ID,取得部门预算编制实例
	 * @param id
	 * @return
	 */
	public DeptBudgetting _getForEdit(String id)
	{		
	    DeptBudgetting deptBudgetting = _get(id);
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = deptBudgetting.getOperationType();
	    if(OperationType.READONLY.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotEdit);
	    }

		return deptBudgetting;	
	}
	
	/**
	 * 根据主键ID,取得部门预算编制实例副本
	 * @param id
	 * @return
	 */
	public DeptBudgetting _getEntityCopy(String id)
	{		
	    DeptBudgetting deptBudgetting = new DeptBudgetting();
		DeptBudgetting deptBudgettingOld = this._get(id);
		try
		{
			BeanUtils.copyProperties(deptBudgetting, deptBudgettingOld);
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (InvocationTargetException e)
		{
			e.printStackTrace();
		}
		//deptBudgetting.setDeptbudgettingid(null); 
		deptBudgetting.setProcessstate(" ");
		return deptBudgetting;	
	}
	
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
	 * @param deptBudgetting
	 */
	public void _delete(DeptBudgetting deptBudgetting)
	{
		if (null != advanceService)
			advanceService.preDelete(deptBudgetting);
	
		//流程状态
		String processState =deptBudgetting.getProcessstate();
		if(!StringUtils.isNullBlank(processState))
		{
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.CannotDeleteApproveData);
		}
 		LockService.isBoInstanceLocked(deptBudgetting,DeptBudgetting.class);
		deptBudgettingHibernateDao.remove(deptBudgetting);

		if (null != advanceService)
			advanceService.postDelete(deptBudgetting);
	}

	/**
	 * 根据主键删除
	 * 
	 * @param deptBudgettingId
	 */
	public void _delete(String deptBudgettingId,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(deptBudgettingId))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("deptbudgettingid"));
		DeptBudgetting deptBudgetting = this.deptBudgettingHibernateDao.load(deptBudgettingId);
		_delete(deptBudgetting);
	}
	
	/**
	 * 删除  
	 *   
	 * @param Set<DeptBudgetting> deptBudgettings
	 */
	public void _deletes(Set<DeptBudgetting> deptBudgettings,BusinessObject businessObject)
	{
		if (null == deptBudgettings || deptBudgettings.size() < 1)
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoText());
		Iterator<DeptBudgetting> it = deptBudgettings.iterator();
		while (it.hasNext())
		{
			DeptBudgetting deptBudgetting = (DeptBudgetting) it.next();
			_delete(deptBudgetting);
		}
	}
	
	/**
	 * 根据多个主键(由，分割)删除多个对象
	 * 
	 * @param deptBudgettingIds
	 */
	public void _deletes(String deptBudgettingIds,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(deptBudgettingIds))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("deptbudgettingid"));
		String[] ids = StringUtils.splitString(deptBudgettingIds);
		for (int i = 0; i < ids.length; i++)
		{
			_delete(ids[i],businessObject);
		}
	}
	/**
	 * 根据方法参数,取得部门预算编制实例
	 * @param id
	 * @return 部门预算编制实例
	 */
     public DeptBudgetting _view(
String budorgid,
String ayear
)     {
       DeptBudgetting deptBudgetting = deptBudgettingHibernateDao. _view(
budorgid,
ayear
);	   if(deptBudgetting!=null)
	   {	
		   //以下代码需要放在service服务同一方法，要不存在事务问题。
		   String operationType = LockService.lockBOData(deptBudgetting);
		   if(OperationType.UNVISIABLE.equals(operationType))
		   {
			  throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
		   }
		   deptBudgetting.setOperationType(operationType);
	   }
	   
       return  deptBudgetting ;
     }
	/**
	 * 提交工作流
	 * 
	 * @param deptBudgetting
	 */
	public void _submitProcess(DeptBudgetting deptBudgetting
	,BusinessObject businessObject)
	{
		String id = deptBudgetting.getDeptbudgettingid();
		if (StringUtils.isNullBlank(id))
		{
			_save(deptBudgetting);
		}
		else
		{
			_update(deptBudgetting
			, businessObject);
		}
		String taskId = deptBudgetting.getWorkflowTaskId();
		id = deptBudgetting.getDeptbudgettingid();
		if (null == taskId || "".equals(taskId))
			WorkflowService.createAndSignalProcessInstance(deptBudgetting, id);
		else
			WorkflowService.signalProcessInstance(deptBudgetting, id, null);
	}

	/**
	 * 保存或更新部门预算编制
	 * 保存  
	 *  
	 * @param deptBudgetting
	 */
	public void _update(DeptBudgetting deptBudgetting
,BusinessObject businessObject	
	)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(deptBudgetting);
		deptBudgettingHibernateDao.saveOrUpdate(deptBudgetting);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(deptBudgetting);
	}
	
	/**
	 * 保存  
	 *   
	 * @param deptBudgetting
	 */
	public void _save(DeptBudgetting deptBudgetting)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(deptBudgetting);
		// 对id赋空值，使之执行insert操作(适应复制创建场景)
		deptBudgetting.setDeptbudgettingid(null);
              		deptBudgettingHibernateDao.saveOrUpdate(deptBudgetting);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(deptBudgetting);
	}
	
	/**
	 *
	 * 保存  
	 *   
	 * @param deptBudgetting
	 */
	public void _saveOrUpdate(DeptBudgetting deptBudgetting
,BusinessObject businessObject		
	)
	{
		if (StringUtils.isNullBlank(deptBudgetting.getDeptbudgettingid()))
		{	
			_save(deptBudgetting);
		}
		else
		{
			_update(deptBudgetting
, businessObject);
}	}
	
}