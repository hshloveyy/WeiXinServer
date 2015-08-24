/*
 * @(#)UnCleaPaymentOtherServiceGen.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2012年06月08日 11点58分34秒
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
import com.infolion.xdss3.singleClearOther.domain.UnCleaPaymentOther;
import com.infolion.xdss3.singleClearOther.service.UnCleaPaymentOtherService;
import com.infolion.xdss3.singleClearOther.dao.UnCleaPaymentOtherHibernateDao;

/**
 * <pre>
 * 其他公司未清付款（借方）(UnCleaPaymentOther)服务类
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
public class UnCleaPaymentOtherServiceGen extends BaseService
{
	protected Log log = LogFactory.getFactory().getLog(this.getClass());

	@Autowired
	protected UnCleaPaymentOtherHibernateDao unCleaPaymentOtherHibernateDao;
	
	public void setUnCleaPaymentOtherHibernateDao(UnCleaPaymentOtherHibernateDao unCleaPaymentOtherHibernateDao)
	{
		this.unCleaPaymentOtherHibernateDao = unCleaPaymentOtherHibernateDao;
	}
	
     /**
	 * 注入扩展服务类
	 */
	 @Autowired(required = false)
	 public void setAdvanceService(@Qualifier("unCleaPaymentOtherAdvanceService") AdvanceService advanceService)
	 {
	  	super.setAdvanceService(advanceService);
	 }   
	 
	   
	/**
	 * 根据主键ID,取得其他公司未清付款（借方）实例
	 * @param id
	 * @return
	 */
	public UnCleaPaymentOther _getDetached(String id)
	{		
	    UnCleaPaymentOther unCleaPaymentOther = new UnCleaPaymentOther();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  unCleaPaymentOther = unCleaPaymentOtherHibernateDao.getDetached(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(unCleaPaymentOther);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    unCleaPaymentOther.setOperationType(operationType);
	    
		return unCleaPaymentOther;	
	}
	
	/**
	 * 根据主键ID,取得其他公司未清付款（借方）实例
	 * @param id
	 * @return
	 */
	public UnCleaPaymentOther _get(String id)
	{		
	    UnCleaPaymentOther unCleaPaymentOther = new UnCleaPaymentOther();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  unCleaPaymentOther = unCleaPaymentOtherHibernateDao.get(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(unCleaPaymentOther);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    unCleaPaymentOther.setOperationType(operationType);
	    
		return unCleaPaymentOther;	
	}
	
	/**
	 * 根据主键ID,取得其他公司未清付款（借方）实例
	 * @param id
	 * @return
	 */
	public UnCleaPaymentOther _getForEdit(String id)
	{		
	    UnCleaPaymentOther unCleaPaymentOther = _get(id);
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = unCleaPaymentOther.getOperationType();
	    if(OperationType.READONLY.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotEdit);
	    }

		return unCleaPaymentOther;	
	}
	
	/**
	 * 根据主键ID,取得其他公司未清付款（借方）实例副本
	 * @param id
	 * @return
	 */
	public UnCleaPaymentOther _getEntityCopy(String id)
	{		
	    UnCleaPaymentOther unCleaPaymentOther = new UnCleaPaymentOther();
		UnCleaPaymentOther unCleaPaymentOtherOld = this._get(id);
		try
		{
			BeanUtils.copyProperties(unCleaPaymentOther, unCleaPaymentOtherOld);
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (InvocationTargetException e)
		{
			e.printStackTrace();
		}
		//unCleaPaymentOther.setUnclearpaymentid(null); 
		return unCleaPaymentOther;	
	}
	
	/**
	 * 删除  
	 *   
	 * @param unCleaPaymentOther
	 */
	public void _delete(UnCleaPaymentOther unCleaPaymentOther)
	{
		if (null != advanceService)
			advanceService.preDelete(unCleaPaymentOther);
	
 		LockService.isBoInstanceLocked(unCleaPaymentOther,UnCleaPaymentOther.class);
		unCleaPaymentOtherHibernateDao.remove(unCleaPaymentOther);

		if (null != advanceService)
			advanceService.postDelete(unCleaPaymentOther);
	}

	/**
	 * 根据主键删除
	 * 
	 * @param unCleaPaymentOtherId
	 */
	public void _delete(String unCleaPaymentOtherId,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(unCleaPaymentOtherId))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("unclearpaymentid"));
		UnCleaPaymentOther unCleaPaymentOther = this.unCleaPaymentOtherHibernateDao.load(unCleaPaymentOtherId);
		_delete(unCleaPaymentOther);
	}
	
	/**
	 * 删除  
	 *   
	 * @param Set<UnCleaPaymentOther> unCleaPaymentOthers
	 */
	public void _deletes(Set<UnCleaPaymentOther> unCleaPaymentOthers,BusinessObject businessObject)
	{
		if (null == unCleaPaymentOthers || unCleaPaymentOthers.size() < 1)
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoText());
		Iterator<UnCleaPaymentOther> it = unCleaPaymentOthers.iterator();
		while (it.hasNext())
		{
			UnCleaPaymentOther unCleaPaymentOther = (UnCleaPaymentOther) it.next();
			_delete(unCleaPaymentOther);
		}
	}
	
	/**
	 * 根据多个主键(由，分割)删除多个对象
	 * 
	 * @param unCleaPaymentOtherIds
	 */
	public void _deletes(String unCleaPaymentOtherIds,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(unCleaPaymentOtherIds))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("unclearpaymentid"));
		String[] ids = StringUtils.splitString(unCleaPaymentOtherIds);
		for (int i = 0; i < ids.length; i++)
		{
			_delete(ids[i],businessObject);
		}
	}
	/**
	 * 提交工作流
	 * 
	 * @param unCleaPaymentOther
	 */
	public void _submitProcess(UnCleaPaymentOther unCleaPaymentOther
	,BusinessObject businessObject)
	{
		String id = unCleaPaymentOther.getUnclearpaymentid();
        /**
		if (StringUtils.isNullBlank(id))
		{
			_save(unCleaPaymentOther);
		}
		else
		{
			_update(unCleaPaymentOther
			, businessObject);
		}**/
		
		String taskId = unCleaPaymentOther.getWorkflowTaskId();
		id = unCleaPaymentOther.getUnclearpaymentid();
		if (null == taskId || "".equals(taskId))
			WorkflowService.createAndSignalProcessInstance(unCleaPaymentOther, id);
		else
			WorkflowService.signalProcessInstance(unCleaPaymentOther, id, null);
	}

	/**
	 * 保存或更新其他公司未清付款（借方）
	 * 保存  
	 *  
	 * @param unCleaPaymentOther
	 */
	public void _update(UnCleaPaymentOther unCleaPaymentOther
,BusinessObject businessObject	
	)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(unCleaPaymentOther);
		unCleaPaymentOtherHibernateDao.saveOrUpdate(unCleaPaymentOther);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(unCleaPaymentOther);
	}
	
	/**
	 * 保存  
	 *   
	 * @param unCleaPaymentOther
	 */
	public void _save(UnCleaPaymentOther unCleaPaymentOther)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(unCleaPaymentOther);
		// 对id赋空值，使之执行insert操作(适应复制创建场景)
		unCleaPaymentOther.setUnclearpaymentid(null);
                                            		unCleaPaymentOtherHibernateDao.saveOrUpdate(unCleaPaymentOther);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(unCleaPaymentOther);
	}
	
	/**
	 *
	 * 保存  
	 *   
	 * @param unCleaPaymentOther
	 */
	public void _saveOrUpdate(UnCleaPaymentOther unCleaPaymentOther
,BusinessObject businessObject		
	)
	{
		if (StringUtils.isNullBlank(unCleaPaymentOther.getUnclearpaymentid()))
		{	
			_save(unCleaPaymentOther);
		}
		else
		{
			_update(unCleaPaymentOther
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