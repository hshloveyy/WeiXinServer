/*
 * @(#)SettleSubjectOtherServiceGen.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2012年06月08日 11点58分15秒
 *  描　述：创建
 */
package com.infolion.xdss3.singleClearOtherGen.service;

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
import com.infolion.xdss3.singleClearOther.domain.SettleSubjectOther;
import com.infolion.xdss3.singleClearOther.service.SettleSubjectOtherService;
import com.infolion.xdss3.singleClearOther.dao.SettleSubjectOtherHibernateDao;

/**
 * <pre>
 * 其他公司结算科目(SettleSubjectOther)服务类
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
public class SettleSubjectOtherServiceGen extends BaseService
{
	protected Log log = LogFactory.getFactory().getLog(this.getClass());

	@Autowired
	protected SettleSubjectOtherHibernateDao settleSubjectOtherHibernateDao;
	
	public void setSettleSubjectOtherHibernateDao(SettleSubjectOtherHibernateDao settleSubjectOtherHibernateDao)
	{
		this.settleSubjectOtherHibernateDao = settleSubjectOtherHibernateDao;
	}
	
     /**
	 * 注入扩展服务类
	 */
	 @Autowired(required = false)
	 public void setAdvanceService(@Qualifier("settleSubjectOtherAdvanceService") AdvanceService advanceService)
	 {
	  	super.setAdvanceService(advanceService);
	 }   
	 
	   
	/**
	 * 根据主键ID,取得其他公司结算科目实例
	 * @param id
	 * @return
	 */
	public SettleSubjectOther _getDetached(String id)
	{		
	    SettleSubjectOther settleSubjectOther = new SettleSubjectOther();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  settleSubjectOther = settleSubjectOtherHibernateDao.getDetached(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(settleSubjectOther);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    settleSubjectOther.setOperationType(operationType);
	    
		return settleSubjectOther;	
	}
	
	/**
	 * 根据主键ID,取得其他公司结算科目实例
	 * @param id
	 * @return
	 */
	public SettleSubjectOther _get(String id)
	{		
	    SettleSubjectOther settleSubjectOther = new SettleSubjectOther();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  settleSubjectOther = settleSubjectOtherHibernateDao.get(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(settleSubjectOther);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    settleSubjectOther.setOperationType(operationType);
	    
		return settleSubjectOther;	
	}
	
	/**
	 * 根据主键ID,取得其他公司结算科目实例
	 * @param id
	 * @return
	 */
	public SettleSubjectOther _getForEdit(String id)
	{		
	    SettleSubjectOther settleSubjectOther = _get(id);
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = settleSubjectOther.getOperationType();
	    if(OperationType.READONLY.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotEdit);
	    }

		return settleSubjectOther;	
	}
	
	/**
	 * 根据主键ID,取得其他公司结算科目实例副本
	 * @param id
	 * @return
	 */
	public SettleSubjectOther _getEntityCopy(String id)
	{		
	    SettleSubjectOther settleSubjectOther = new SettleSubjectOther();
		SettleSubjectOther settleSubjectOtherOld = this._get(id);
		try
		{
			BeanUtils.copyProperties(settleSubjectOther, settleSubjectOtherOld);
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (InvocationTargetException e)
		{
			e.printStackTrace();
		}
		//settleSubjectOther.setSettlesubjectid(null); 
		return settleSubjectOther;	
	}
	
	/**
	 * 删除  
	 *   
	 * @param settleSubjectOther
	 */
	public void _delete(SettleSubjectOther settleSubjectOther)
	{
		if (null != advanceService)
			advanceService.preDelete(settleSubjectOther);
	
 		LockService.isBoInstanceLocked(settleSubjectOther,SettleSubjectOther.class);
		settleSubjectOtherHibernateDao.remove(settleSubjectOther);

		if (null != advanceService)
			advanceService.postDelete(settleSubjectOther);
	}

	/**
	 * 根据主键删除
	 * 
	 * @param settleSubjectOtherId
	 */
	public void _delete(String settleSubjectOtherId,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(settleSubjectOtherId))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("settlesubjectid"));
		SettleSubjectOther settleSubjectOther = this.settleSubjectOtherHibernateDao.load(settleSubjectOtherId);
		_delete(settleSubjectOther);
	}
	
	/**
	 * 删除  
	 *   
	 * @param Set<SettleSubjectOther> settleSubjectOthers
	 */
	public void _deletes(Set<SettleSubjectOther> settleSubjectOthers,BusinessObject businessObject)
	{
		if (null == settleSubjectOthers || settleSubjectOthers.size() < 1)
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoText());
		Iterator<SettleSubjectOther> it = settleSubjectOthers.iterator();
		while (it.hasNext())
		{
			SettleSubjectOther settleSubjectOther = (SettleSubjectOther) it.next();
			_delete(settleSubjectOther);
		}
	}
	
	/**
	 * 根据多个主键(由，分割)删除多个对象
	 * 
	 * @param settleSubjectOtherIds
	 */
	public void _deletes(String settleSubjectOtherIds,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(settleSubjectOtherIds))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("settlesubjectid"));
		String[] ids = StringUtils.splitString(settleSubjectOtherIds);
		for (int i = 0; i < ids.length; i++)
		{
			_delete(ids[i],businessObject);
		}
	}
	/**
	 * 提交工作流
	 * 
	 * @param settleSubjectOther
	 */
	public void _submitProcess(SettleSubjectOther settleSubjectOther
	,BusinessObject businessObject)
	{
		String id = settleSubjectOther.getSettlesubjectid();
        /**
		if (StringUtils.isNullBlank(id))
		{
			_save(settleSubjectOther);
		}
		else
		{
			_update(settleSubjectOther
			, businessObject);
		}**/
		
		String taskId = settleSubjectOther.getWorkflowTaskId();
		id = settleSubjectOther.getSettlesubjectid();
		if (null == taskId || "".equals(taskId))
			WorkflowService.createAndSignalProcessInstance(settleSubjectOther, id);
		else
			WorkflowService.signalProcessInstance(settleSubjectOther, id, null);
	}

	/**
	 * 保存或更新其他公司结算科目
	 * 保存  
	 *  
	 * @param settleSubjectOther
	 */
	public void _update(SettleSubjectOther settleSubjectOther
,BusinessObject businessObject	
	)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(settleSubjectOther);
		settleSubjectOtherHibernateDao.saveOrUpdate(settleSubjectOther);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(settleSubjectOther);
	}
	
	/**
	 * 保存  
	 *   
	 * @param settleSubjectOther
	 */
	public void _save(SettleSubjectOther settleSubjectOther)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(settleSubjectOther);
		// 对id赋空值，使之执行insert操作(适应复制创建场景)
		settleSubjectOther.setSettlesubjectid(null);
                                                                                                		settleSubjectOtherHibernateDao.saveOrUpdate(settleSubjectOther);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(settleSubjectOther);
	}
	
	/**
	 *
	 * 保存  
	 *   
	 * @param settleSubjectOther
	 */
	public void _saveOrUpdate(SettleSubjectOther settleSubjectOther
,BusinessObject businessObject		
	)
	{
		if (StringUtils.isNullBlank(settleSubjectOther.getSettlesubjectid()))
		{	
			_save(settleSubjectOther);
		}
		else
		{
			_update(settleSubjectOther
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