/*
 * @(#)SettleSubjectServiceGen.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年08月10日 02点20分33秒
 *  描　述：创建
 */
package com.infolion.xdss3.financeSquareGen.service;

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
import com.infolion.xdss3.financeSquare.domain.SettleSubject;
import com.infolion.xdss3.financeSquare.service.SettleSubjectService;
import com.infolion.xdss3.financeSquare.dao.SettleSubjectHibernateDao;

/**
 * <pre>
 * 结算科目(SettleSubject)服务类
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
public class SettleSubjectServiceGen extends BaseService
{
	protected Log log = LogFactory.getFactory().getLog(this.getClass());

	@Autowired
	protected SettleSubjectHibernateDao settleSubjectHibernateDao;
	
	public void setSettleSubjectHibernateDao(SettleSubjectHibernateDao settleSubjectHibernateDao)
	{
		this.settleSubjectHibernateDao = settleSubjectHibernateDao;
	}
	
     /**
	 * 注入扩展服务类
	 */
	 @Autowired(required = false)
	 public void setAdvanceService(@Qualifier("settleSubjectAdvanceService") AdvanceService advanceService)
	 {
	  	super.setAdvanceService(advanceService);
	 }   
	 
	   
	/**
	 * 根据主键ID,取得结算科目实例
	 * @param id
	 * @return
	 */
	public SettleSubject _getDetached(String id)
	{		
	    SettleSubject settleSubject = new SettleSubject();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  settleSubject = settleSubjectHibernateDao.getDetached(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(settleSubject);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    settleSubject.setOperationType(operationType);
	    
		return settleSubject;	
	}
	
	/**
	 * 根据主键ID,取得结算科目实例
	 * @param id
	 * @return
	 */
	public SettleSubject _get(String id)
	{		
	    SettleSubject settleSubject = new SettleSubject();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  settleSubject = settleSubjectHibernateDao.get(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(settleSubject);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    settleSubject.setOperationType(operationType);
	    
		return settleSubject;	
	}
	
	/**
	 * 根据主键ID,取得结算科目实例
	 * @param id
	 * @return
	 */
	public SettleSubject _getForEdit(String id)
	{		
	    SettleSubject settleSubject = _get(id);
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = settleSubject.getOperationType();
	    if(OperationType.READONLY.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotEdit);
	    }

		return settleSubject;	
	}
	
	/**
	 * 根据主键ID,取得结算科目实例副本
	 * @param id
	 * @return
	 */
	public SettleSubject _getEntityCopy(String id)
	{		
	    SettleSubject settleSubject = new SettleSubject();
		SettleSubject settleSubjectOld = this._get(id);
		try
		{
			BeanUtils.copyProperties(settleSubject, settleSubjectOld);
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (InvocationTargetException e)
		{
			e.printStackTrace();
		}
		//settleSubject.setSettlesubjectid(null); 
		return settleSubject;	
	}
	
	/**
	 * 删除  
	 *   
	 * @param settleSubject
	 */
	public void _delete(SettleSubject settleSubject)
	{
		if (null != advanceService)
			advanceService.preDelete(settleSubject);
	
 		LockService.isBoInstanceLocked(settleSubject,SettleSubject.class);
		settleSubjectHibernateDao.remove(settleSubject);

		if (null != advanceService)
			advanceService.postDelete(settleSubject);
	}

	/**
	 * 根据主键删除
	 * 
	 * @param settleSubjectId
	 */
	public void _delete(String settleSubjectId,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(settleSubjectId))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("settlesubjectid"));
		SettleSubject settleSubject = this.settleSubjectHibernateDao.load(settleSubjectId);
		_delete(settleSubject);
	}
	
	/**
	 * 删除  
	 *   
	 * @param Set<SettleSubject> settleSubjects
	 */
	public void _deletes(Set<SettleSubject> settleSubjects,BusinessObject businessObject)
	{
		if (null == settleSubjects || settleSubjects.size() < 1)
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoText());
		Iterator<SettleSubject> it = settleSubjects.iterator();
		while (it.hasNext())
		{
			SettleSubject settleSubject = (SettleSubject) it.next();
			_delete(settleSubject);
		}
	}
	
	/**
	 * 根据多个主键(由，分割)删除多个对象
	 * 
	 * @param settleSubjectIds
	 */
	public void _deletes(String settleSubjectIds,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(settleSubjectIds))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("settlesubjectid"));
		String[] ids = StringUtils.splitString(settleSubjectIds);
		for (int i = 0; i < ids.length; i++)
		{
			_delete(ids[i],businessObject);
		}
	}
	/**
	 * 提交工作流
	 * 
	 * @param settleSubject
	 */
	public void _submitProcess(SettleSubject settleSubject
	,BusinessObject businessObject)
	{
		String id = settleSubject.getSettlesubjectid();
        /**
		if (StringUtils.isNullBlank(id))
		{
			_save(settleSubject);
		}
		else
		{
			_update(settleSubject
			, businessObject);
		}**/
		
		String taskId = settleSubject.getWorkflowTaskId();
		id = settleSubject.getSettlesubjectid();
		if (null == taskId || "".equals(taskId))
			WorkflowService.createAndSignalProcessInstance(settleSubject, id);
		else
			WorkflowService.signalProcessInstance(settleSubject, id, null);
	}

	/**
	 * 保存或更新结算科目
	 * 保存  
	 *  
	 * @param settleSubject
	 */
	public void _update(SettleSubject settleSubject
,BusinessObject businessObject	
	)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(settleSubject);
		settleSubjectHibernateDao.saveOrUpdate(settleSubject);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(settleSubject);
	}
	
	/**
	 * 保存  
	 *   
	 * @param settleSubject
	 */
	public void _save(SettleSubject settleSubject)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(settleSubject);
		// 对id赋空值，使之执行insert操作(适应复制创建场景)
		settleSubject.setSettlesubjectid(null);
                                                                                      		settleSubjectHibernateDao.saveOrUpdate(settleSubject);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(settleSubject);
	}
	
	/**
	 *
	 * 保存  
	 *   
	 * @param settleSubject
	 */
	public void _saveOrUpdate(SettleSubject settleSubject
,BusinessObject businessObject		
	)
	{
		if (StringUtils.isNullBlank(settleSubject.getSettlesubjectid()))
		{	
			_save(settleSubject);
		}
		else
		{
			_update(settleSubject
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