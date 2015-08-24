/*
 * @(#)CreditlogServiceGen.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年09月17日 07点32分24秒
 *  描　述：创建
 */
package com.infolion.xdss3.creditlogGen.service;

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
import com.infolion.xdss3.creditlog.domain.Creditlog;
import com.infolion.xdss3.creditlog.service.CreditlogService;
import com.infolion.xdss3.creditlog.dao.CreditlogHibernateDao;

/**
 * <pre>
 * 授信日志表(Creditlog)服务类
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
public class CreditlogServiceGen extends BaseService
{
	protected Log log = LogFactory.getFactory().getLog(this.getClass());

	@Autowired
	protected CreditlogHibernateDao creditlogHibernateDao;
	
	public void setCreditlogHibernateDao(CreditlogHibernateDao creditlogHibernateDao)
	{
		this.creditlogHibernateDao = creditlogHibernateDao;
	}
	
     /**
	 * 注入扩展服务类
	 */
	 @Autowired(required = false)
	 public void setAdvanceService(@Qualifier("creditlogAdvanceService") AdvanceService advanceService)
	 {
	  	super.setAdvanceService(advanceService);
	 }   
	 
	   
	/**
	 * 根据主键ID,取得授信日志表实例
	 * @param id
	 * @return
	 */
	public Creditlog _getDetached(String id)
	{		
	    Creditlog creditlog = new Creditlog();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  creditlog = creditlogHibernateDao.getDetached(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(creditlog);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    creditlog.setOperationType(operationType);
	    
		return creditlog;	
	}
	
	/**
	 * 根据主键ID,取得授信日志表实例
	 * @param id
	 * @return
	 */
	public Creditlog _get(String id)
	{		
	    Creditlog creditlog = new Creditlog();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  creditlog = creditlogHibernateDao.get(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(creditlog);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    creditlog.setOperationType(operationType);
	    
		return creditlog;	
	}
	
	/**
	 * 根据主键ID,取得授信日志表实例
	 * @param id
	 * @return
	 */
	public Creditlog _getForEdit(String id)
	{		
	    Creditlog creditlog = _get(id);
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = creditlog.getOperationType();
	    if(OperationType.READONLY.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotEdit);
	    }

		return creditlog;	
	}
	
	/**
	 * 根据主键ID,取得授信日志表实例副本
	 * @param id
	 * @return
	 */
	public Creditlog _getEntityCopy(String id)
	{		
	    Creditlog creditlog = new Creditlog();
		Creditlog creditlogOld = this._get(id);
		try
		{
			BeanUtils.copyProperties(creditlog, creditlogOld);
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (InvocationTargetException e)
		{
			e.printStackTrace();
		}
		//creditlog.setYcreditlogid(null); 
		return creditlog;	
	}
	
	/**
	 * 删除  
	 *   
	 * @param creditlog
	 */
	public void _delete(Creditlog creditlog)
	{
		if (null != advanceService)
			advanceService.preDelete(creditlog);
	
 		LockService.isBoInstanceLocked(creditlog,Creditlog.class);
		creditlogHibernateDao.remove(creditlog);

		if (null != advanceService)
			advanceService.postDelete(creditlog);
	}

	/**
	 * 根据主键删除
	 * 
	 * @param creditlogId
	 */
	public void _delete(String creditlogId,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(creditlogId))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("ycreditlogid"));
		Creditlog creditlog = this.creditlogHibernateDao.load(creditlogId);
		_delete(creditlog);
	}
	
	/**
	 * 删除  
	 *   
	 * @param Set<Creditlog> creditlogs
	 */
	public void _deletes(Set<Creditlog> creditlogs,BusinessObject businessObject)
	{
		if (null == creditlogs || creditlogs.size() < 1)
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoText());
		Iterator<Creditlog> it = creditlogs.iterator();
		while (it.hasNext())
		{
			Creditlog creditlog = (Creditlog) it.next();
			_delete(creditlog);
		}
	}
	
	/**
	 * 根据多个主键(由，分割)删除多个对象
	 * 
	 * @param creditlogIds
	 */
	public void _deletes(String creditlogIds,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(creditlogIds))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("ycreditlogid"));
		String[] ids = StringUtils.splitString(creditlogIds);
		for (int i = 0; i < ids.length; i++)
		{
			_delete(ids[i],businessObject);
		}
	}
	/**
	 * 提交工作流
	 * 
	 * @param creditlog
	 */
	public void _submitProcess(Creditlog creditlog
	,BusinessObject businessObject)
	{
		String id = creditlog.getYcreditlogid();
        /**
		if (StringUtils.isNullBlank(id))
		{
			_save(creditlog);
		}
		else
		{
			_update(creditlog
			, businessObject);
		}**/
		
		String taskId = creditlog.getWorkflowTaskId();
		id = creditlog.getYcreditlogid();
		if (null == taskId || "".equals(taskId))
			WorkflowService.createAndSignalProcessInstance(creditlog, id);
		else
			WorkflowService.signalProcessInstance(creditlog, id, null);
	}

	/**
	 * 保存或更新授信日志表
	 * 保存  
	 *  
	 * @param creditlog
	 */
	public void _update(Creditlog creditlog
,BusinessObject businessObject	
	)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(creditlog);
		creditlogHibernateDao.saveOrUpdate(creditlog);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(creditlog);
	}
	
	/**
	 * 保存  
	 *   
	 * @param creditlog
	 */
	public void _save(Creditlog creditlog)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(creditlog);
		// 对id赋空值，使之执行insert操作(适应复制创建场景)
		creditlog.setYcreditlogid(null);
                        		creditlogHibernateDao.saveOrUpdate(creditlog);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(creditlog);
	}
	
	/**
	 *
	 * 保存  
	 *   
	 * @param creditlog
	 */
	public void _saveOrUpdate(Creditlog creditlog
,BusinessObject businessObject		
	)
	{
		if (StringUtils.isNullBlank(creditlog.getYcreditlogid()))
		{	
			_save(creditlog);
		}
		else
		{
			_update(creditlog
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