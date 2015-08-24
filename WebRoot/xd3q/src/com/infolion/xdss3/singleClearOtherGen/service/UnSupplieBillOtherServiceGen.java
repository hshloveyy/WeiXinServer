/*
 * @(#)UnSupplieBillOtherServiceGen.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2012年06月08日 11点58分52秒
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
import com.infolion.xdss3.singleClearOther.domain.UnSupplieBillOther;
import com.infolion.xdss3.singleClearOther.service.UnSupplieBillOtherService;
import com.infolion.xdss3.singleClearOther.dao.UnSupplieBillOtherHibernateDao;

/**
 * <pre>
 * 其他公司未清应付（贷方）(UnSupplieBillOther)服务类
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
public class UnSupplieBillOtherServiceGen extends BaseService
{
	protected Log log = LogFactory.getFactory().getLog(this.getClass());

	@Autowired
	protected UnSupplieBillOtherHibernateDao unSupplieBillOtherHibernateDao;
	
	public void setUnSupplieBillOtherHibernateDao(UnSupplieBillOtherHibernateDao unSupplieBillOtherHibernateDao)
	{
		this.unSupplieBillOtherHibernateDao = unSupplieBillOtherHibernateDao;
	}
	
     /**
	 * 注入扩展服务类
	 */
	 @Autowired(required = false)
	 public void setAdvanceService(@Qualifier("unSupplieBillOtherAdvanceService") AdvanceService advanceService)
	 {
	  	super.setAdvanceService(advanceService);
	 }   
	 
	   
	/**
	 * 根据主键ID,取得其他公司未清应付（贷方）实例
	 * @param id
	 * @return
	 */
	public UnSupplieBillOther _getDetached(String id)
	{		
	    UnSupplieBillOther unSupplieBillOther = new UnSupplieBillOther();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  unSupplieBillOther = unSupplieBillOtherHibernateDao.getDetached(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(unSupplieBillOther);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    unSupplieBillOther.setOperationType(operationType);
	    
		return unSupplieBillOther;	
	}
	
	/**
	 * 根据主键ID,取得其他公司未清应付（贷方）实例
	 * @param id
	 * @return
	 */
	public UnSupplieBillOther _get(String id)
	{		
	    UnSupplieBillOther unSupplieBillOther = new UnSupplieBillOther();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  unSupplieBillOther = unSupplieBillOtherHibernateDao.get(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(unSupplieBillOther);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    unSupplieBillOther.setOperationType(operationType);
	    
		return unSupplieBillOther;	
	}
	
	/**
	 * 根据主键ID,取得其他公司未清应付（贷方）实例
	 * @param id
	 * @return
	 */
	public UnSupplieBillOther _getForEdit(String id)
	{		
	    UnSupplieBillOther unSupplieBillOther = _get(id);
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = unSupplieBillOther.getOperationType();
	    if(OperationType.READONLY.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotEdit);
	    }

		return unSupplieBillOther;	
	}
	
	/**
	 * 根据主键ID,取得其他公司未清应付（贷方）实例副本
	 * @param id
	 * @return
	 */
	public UnSupplieBillOther _getEntityCopy(String id)
	{		
	    UnSupplieBillOther unSupplieBillOther = new UnSupplieBillOther();
		UnSupplieBillOther unSupplieBillOtherOld = this._get(id);
		try
		{
			BeanUtils.copyProperties(unSupplieBillOther, unSupplieBillOtherOld);
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (InvocationTargetException e)
		{
			e.printStackTrace();
		}
		//unSupplieBillOther.setUnclearsbillid(null); 
		return unSupplieBillOther;	
	}
	
	/**
	 * 删除  
	 *   
	 * @param unSupplieBillOther
	 */
	public void _delete(UnSupplieBillOther unSupplieBillOther)
	{
		if (null != advanceService)
			advanceService.preDelete(unSupplieBillOther);
	
 		LockService.isBoInstanceLocked(unSupplieBillOther,UnSupplieBillOther.class);
		unSupplieBillOtherHibernateDao.remove(unSupplieBillOther);

		if (null != advanceService)
			advanceService.postDelete(unSupplieBillOther);
	}

	/**
	 * 根据主键删除
	 * 
	 * @param unSupplieBillOtherId
	 */
	public void _delete(String unSupplieBillOtherId,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(unSupplieBillOtherId))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("unclearsbillid"));
		UnSupplieBillOther unSupplieBillOther = this.unSupplieBillOtherHibernateDao.load(unSupplieBillOtherId);
		_delete(unSupplieBillOther);
	}
	
	/**
	 * 删除  
	 *   
	 * @param Set<UnSupplieBillOther> unSupplieBillOthers
	 */
	public void _deletes(Set<UnSupplieBillOther> unSupplieBillOthers,BusinessObject businessObject)
	{
		if (null == unSupplieBillOthers || unSupplieBillOthers.size() < 1)
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoText());
		Iterator<UnSupplieBillOther> it = unSupplieBillOthers.iterator();
		while (it.hasNext())
		{
			UnSupplieBillOther unSupplieBillOther = (UnSupplieBillOther) it.next();
			_delete(unSupplieBillOther);
		}
	}
	
	/**
	 * 根据多个主键(由，分割)删除多个对象
	 * 
	 * @param unSupplieBillOtherIds
	 */
	public void _deletes(String unSupplieBillOtherIds,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(unSupplieBillOtherIds))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("unclearsbillid"));
		String[] ids = StringUtils.splitString(unSupplieBillOtherIds);
		for (int i = 0; i < ids.length; i++)
		{
			_delete(ids[i],businessObject);
		}
	}
	/**
	 * 提交工作流
	 * 
	 * @param unSupplieBillOther
	 */
	public void _submitProcess(UnSupplieBillOther unSupplieBillOther
	,BusinessObject businessObject)
	{
		String id = unSupplieBillOther.getUnclearsbillid();
        /**
		if (StringUtils.isNullBlank(id))
		{
			_save(unSupplieBillOther);
		}
		else
		{
			_update(unSupplieBillOther
			, businessObject);
		}**/
		
		String taskId = unSupplieBillOther.getWorkflowTaskId();
		id = unSupplieBillOther.getUnclearsbillid();
		if (null == taskId || "".equals(taskId))
			WorkflowService.createAndSignalProcessInstance(unSupplieBillOther, id);
		else
			WorkflowService.signalProcessInstance(unSupplieBillOther, id, null);
	}

	/**
	 * 保存或更新其他公司未清应付（贷方）
	 * 保存  
	 *  
	 * @param unSupplieBillOther
	 */
	public void _update(UnSupplieBillOther unSupplieBillOther
,BusinessObject businessObject	
	)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(unSupplieBillOther);
		unSupplieBillOtherHibernateDao.saveOrUpdate(unSupplieBillOther);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(unSupplieBillOther);
	}
	
	/**
	 * 保存  
	 *   
	 * @param unSupplieBillOther
	 */
	public void _save(UnSupplieBillOther unSupplieBillOther)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(unSupplieBillOther);
		// 对id赋空值，使之执行insert操作(适应复制创建场景)
		unSupplieBillOther.setUnclearsbillid(null);
                                              		unSupplieBillOtherHibernateDao.saveOrUpdate(unSupplieBillOther);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(unSupplieBillOther);
	}
	
	/**
	 *
	 * 保存  
	 *   
	 * @param unSupplieBillOther
	 */
	public void _saveOrUpdate(UnSupplieBillOther unSupplieBillOther
,BusinessObject businessObject		
	)
	{
		if (StringUtils.isNullBlank(unSupplieBillOther.getUnclearsbillid()))
		{	
			_save(unSupplieBillOther);
		}
		else
		{
			_update(unSupplieBillOther
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