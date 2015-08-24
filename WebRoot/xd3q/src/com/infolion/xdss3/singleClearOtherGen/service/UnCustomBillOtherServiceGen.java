/*
 * @(#)UnCustomBillOtherServiceGen.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2012年06月08日 11点59分11秒
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
import com.infolion.xdss3.singleClearOther.domain.UnCustomBillOther;
import com.infolion.xdss3.singleClearOther.service.UnCustomBillOtherService;
import com.infolion.xdss3.singleClearOther.dao.UnCustomBillOtherHibernateDao;

/**
 * <pre>
 * 其他公司未清应收(借方)(UnCustomBillOther)服务类
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
public class UnCustomBillOtherServiceGen extends BaseService
{
	protected Log log = LogFactory.getFactory().getLog(this.getClass());

	@Autowired
	protected UnCustomBillOtherHibernateDao unCustomBillOtherHibernateDao;
	
	public void setUnCustomBillOtherHibernateDao(UnCustomBillOtherHibernateDao unCustomBillOtherHibernateDao)
	{
		this.unCustomBillOtherHibernateDao = unCustomBillOtherHibernateDao;
	}
	
     /**
	 * 注入扩展服务类
	 */
	 @Autowired(required = false)
	 public void setAdvanceService(@Qualifier("unCustomBillOtherAdvanceService") AdvanceService advanceService)
	 {
	  	super.setAdvanceService(advanceService);
	 }   
	 
	   
	/**
	 * 根据主键ID,取得其他公司未清应收(借方)实例
	 * @param id
	 * @return
	 */
	public UnCustomBillOther _getDetached(String id)
	{		
	    UnCustomBillOther unCustomBillOther = new UnCustomBillOther();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  unCustomBillOther = unCustomBillOtherHibernateDao.getDetached(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(unCustomBillOther);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    unCustomBillOther.setOperationType(operationType);
	    
		return unCustomBillOther;	
	}
	
	/**
	 * 根据主键ID,取得其他公司未清应收(借方)实例
	 * @param id
	 * @return
	 */
	public UnCustomBillOther _get(String id)
	{		
	    UnCustomBillOther unCustomBillOther = new UnCustomBillOther();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  unCustomBillOther = unCustomBillOtherHibernateDao.get(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(unCustomBillOther);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    unCustomBillOther.setOperationType(operationType);
	    
		return unCustomBillOther;	
	}
	
	/**
	 * 根据主键ID,取得其他公司未清应收(借方)实例
	 * @param id
	 * @return
	 */
	public UnCustomBillOther _getForEdit(String id)
	{		
	    UnCustomBillOther unCustomBillOther = _get(id);
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = unCustomBillOther.getOperationType();
	    if(OperationType.READONLY.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotEdit);
	    }

		return unCustomBillOther;	
	}
	
	/**
	 * 根据主键ID,取得其他公司未清应收(借方)实例副本
	 * @param id
	 * @return
	 */
	public UnCustomBillOther _getEntityCopy(String id)
	{		
	    UnCustomBillOther unCustomBillOther = new UnCustomBillOther();
		UnCustomBillOther unCustomBillOtherOld = this._get(id);
		try
		{
			BeanUtils.copyProperties(unCustomBillOther, unCustomBillOtherOld);
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (InvocationTargetException e)
		{
			e.printStackTrace();
		}
		//unCustomBillOther.setUnclearcusbillid(null); 
		return unCustomBillOther;	
	}
	
	/**
	 * 删除  
	 *   
	 * @param unCustomBillOther
	 */
	public void _delete(UnCustomBillOther unCustomBillOther)
	{
		if (null != advanceService)
			advanceService.preDelete(unCustomBillOther);
	
 		LockService.isBoInstanceLocked(unCustomBillOther,UnCustomBillOther.class);
		unCustomBillOtherHibernateDao.remove(unCustomBillOther);

		if (null != advanceService)
			advanceService.postDelete(unCustomBillOther);
	}

	/**
	 * 根据主键删除
	 * 
	 * @param unCustomBillOtherId
	 */
	public void _delete(String unCustomBillOtherId,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(unCustomBillOtherId))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("unclearcusbillid"));
		UnCustomBillOther unCustomBillOther = this.unCustomBillOtherHibernateDao.load(unCustomBillOtherId);
		_delete(unCustomBillOther);
	}
	
	/**
	 * 删除  
	 *   
	 * @param Set<UnCustomBillOther> unCustomBillOthers
	 */
	public void _deletes(Set<UnCustomBillOther> unCustomBillOthers,BusinessObject businessObject)
	{
		if (null == unCustomBillOthers || unCustomBillOthers.size() < 1)
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoText());
		Iterator<UnCustomBillOther> it = unCustomBillOthers.iterator();
		while (it.hasNext())
		{
			UnCustomBillOther unCustomBillOther = (UnCustomBillOther) it.next();
			_delete(unCustomBillOther);
		}
	}
	
	/**
	 * 根据多个主键(由，分割)删除多个对象
	 * 
	 * @param unCustomBillOtherIds
	 */
	public void _deletes(String unCustomBillOtherIds,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(unCustomBillOtherIds))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("unclearcusbillid"));
		String[] ids = StringUtils.splitString(unCustomBillOtherIds);
		for (int i = 0; i < ids.length; i++)
		{
			_delete(ids[i],businessObject);
		}
	}
	/**
	 * 提交工作流
	 * 
	 * @param unCustomBillOther
	 */
	public void _submitProcess(UnCustomBillOther unCustomBillOther
	,BusinessObject businessObject)
	{
		String id = unCustomBillOther.getUnclearcusbillid();
        /**
		if (StringUtils.isNullBlank(id))
		{
			_save(unCustomBillOther);
		}
		else
		{
			_update(unCustomBillOther
			, businessObject);
		}**/
		
		String taskId = unCustomBillOther.getWorkflowTaskId();
		id = unCustomBillOther.getUnclearcusbillid();
		if (null == taskId || "".equals(taskId))
			WorkflowService.createAndSignalProcessInstance(unCustomBillOther, id);
		else
			WorkflowService.signalProcessInstance(unCustomBillOther, id, null);
	}

	/**
	 * 保存或更新其他公司未清应收(借方)
	 * 保存  
	 *  
	 * @param unCustomBillOther
	 */
	public void _update(UnCustomBillOther unCustomBillOther
,BusinessObject businessObject	
	)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(unCustomBillOther);
		unCustomBillOtherHibernateDao.saveOrUpdate(unCustomBillOther);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(unCustomBillOther);
	}
	
	/**
	 * 保存  
	 *   
	 * @param unCustomBillOther
	 */
	public void _save(UnCustomBillOther unCustomBillOther)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(unCustomBillOther);
		// 对id赋空值，使之执行insert操作(适应复制创建场景)
		unCustomBillOther.setUnclearcusbillid(null);
                                                    		unCustomBillOtherHibernateDao.saveOrUpdate(unCustomBillOther);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(unCustomBillOther);
	}
	
	/**
	 *
	 * 保存  
	 *   
	 * @param unCustomBillOther
	 */
	public void _saveOrUpdate(UnCustomBillOther unCustomBillOther
,BusinessObject businessObject		
	)
	{
		if (StringUtils.isNullBlank(unCustomBillOther.getUnclearcusbillid()))
		{	
			_save(unCustomBillOther);
		}
		else
		{
			_update(unCustomBillOther
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