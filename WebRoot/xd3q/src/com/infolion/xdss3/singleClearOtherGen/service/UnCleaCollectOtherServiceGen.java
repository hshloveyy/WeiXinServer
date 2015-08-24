/*
 * @(#)UnCleaCollectOtherServiceGen.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2012年06月08日 11点59分30秒
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
import com.infolion.xdss3.singleClearOther.domain.UnCleaCollectOther;
import com.infolion.xdss3.singleClearOther.service.UnCleaCollectOtherService;
import com.infolion.xdss3.singleClearOther.dao.UnCleaCollectOtherHibernateDao;

/**
 * <pre>
 * 其他公司未清收款(贷方)(UnCleaCollectOther)服务类
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
public class UnCleaCollectOtherServiceGen extends BaseService
{
	protected Log log = LogFactory.getFactory().getLog(this.getClass());

	@Autowired
	protected UnCleaCollectOtherHibernateDao unCleaCollectOtherHibernateDao;
	
	public void setUnCleaCollectOtherHibernateDao(UnCleaCollectOtherHibernateDao unCleaCollectOtherHibernateDao)
	{
		this.unCleaCollectOtherHibernateDao = unCleaCollectOtherHibernateDao;
	}
	
     /**
	 * 注入扩展服务类
	 */
	 @Autowired(required = false)
	 public void setAdvanceService(@Qualifier("unCleaCollectOtherAdvanceService") AdvanceService advanceService)
	 {
	  	super.setAdvanceService(advanceService);
	 }   
	 
	   
	/**
	 * 根据主键ID,取得其他公司未清收款(贷方)实例
	 * @param id
	 * @return
	 */
	public UnCleaCollectOther _getDetached(String id)
	{		
	    UnCleaCollectOther unCleaCollectOther = new UnCleaCollectOther();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  unCleaCollectOther = unCleaCollectOtherHibernateDao.getDetached(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(unCleaCollectOther);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    unCleaCollectOther.setOperationType(operationType);
	    
		return unCleaCollectOther;	
	}
	
	/**
	 * 根据主键ID,取得其他公司未清收款(贷方)实例
	 * @param id
	 * @return
	 */
	public UnCleaCollectOther _get(String id)
	{		
	    UnCleaCollectOther unCleaCollectOther = new UnCleaCollectOther();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  unCleaCollectOther = unCleaCollectOtherHibernateDao.get(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(unCleaCollectOther);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    unCleaCollectOther.setOperationType(operationType);
	    
		return unCleaCollectOther;	
	}
	
	/**
	 * 根据主键ID,取得其他公司未清收款(贷方)实例
	 * @param id
	 * @return
	 */
	public UnCleaCollectOther _getForEdit(String id)
	{		
	    UnCleaCollectOther unCleaCollectOther = _get(id);
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = unCleaCollectOther.getOperationType();
	    if(OperationType.READONLY.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotEdit);
	    }

		return unCleaCollectOther;	
	}
	
	/**
	 * 根据主键ID,取得其他公司未清收款(贷方)实例副本
	 * @param id
	 * @return
	 */
	public UnCleaCollectOther _getEntityCopy(String id)
	{		
	    UnCleaCollectOther unCleaCollectOther = new UnCleaCollectOther();
		UnCleaCollectOther unCleaCollectOtherOld = this._get(id);
		try
		{
			BeanUtils.copyProperties(unCleaCollectOther, unCleaCollectOtherOld);
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (InvocationTargetException e)
		{
			e.printStackTrace();
		}
		//unCleaCollectOther.setUnclearcollectid(null); 
		return unCleaCollectOther;	
	}
	
	/**
	 * 删除  
	 *   
	 * @param unCleaCollectOther
	 */
	public void _delete(UnCleaCollectOther unCleaCollectOther)
	{
		if (null != advanceService)
			advanceService.preDelete(unCleaCollectOther);
	
 		LockService.isBoInstanceLocked(unCleaCollectOther,UnCleaCollectOther.class);
		unCleaCollectOtherHibernateDao.remove(unCleaCollectOther);

		if (null != advanceService)
			advanceService.postDelete(unCleaCollectOther);
	}

	/**
	 * 根据主键删除
	 * 
	 * @param unCleaCollectOtherId
	 */
	public void _delete(String unCleaCollectOtherId,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(unCleaCollectOtherId))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("unclearcollectid"));
		UnCleaCollectOther unCleaCollectOther = this.unCleaCollectOtherHibernateDao.load(unCleaCollectOtherId);
		_delete(unCleaCollectOther);
	}
	
	/**
	 * 删除  
	 *   
	 * @param Set<UnCleaCollectOther> unCleaCollectOthers
	 */
	public void _deletes(Set<UnCleaCollectOther> unCleaCollectOthers,BusinessObject businessObject)
	{
		if (null == unCleaCollectOthers || unCleaCollectOthers.size() < 1)
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoText());
		Iterator<UnCleaCollectOther> it = unCleaCollectOthers.iterator();
		while (it.hasNext())
		{
			UnCleaCollectOther unCleaCollectOther = (UnCleaCollectOther) it.next();
			_delete(unCleaCollectOther);
		}
	}
	
	/**
	 * 根据多个主键(由，分割)删除多个对象
	 * 
	 * @param unCleaCollectOtherIds
	 */
	public void _deletes(String unCleaCollectOtherIds,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(unCleaCollectOtherIds))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("unclearcollectid"));
		String[] ids = StringUtils.splitString(unCleaCollectOtherIds);
		for (int i = 0; i < ids.length; i++)
		{
			_delete(ids[i],businessObject);
		}
	}
	/**
	 * 提交工作流
	 * 
	 * @param unCleaCollectOther
	 */
	public void _submitProcess(UnCleaCollectOther unCleaCollectOther
	,BusinessObject businessObject)
	{
		String id = unCleaCollectOther.getUnclearcollectid();
        /**
		if (StringUtils.isNullBlank(id))
		{
			_save(unCleaCollectOther);
		}
		else
		{
			_update(unCleaCollectOther
			, businessObject);
		}**/
		
		String taskId = unCleaCollectOther.getWorkflowTaskId();
		id = unCleaCollectOther.getUnclearcollectid();
		if (null == taskId || "".equals(taskId))
			WorkflowService.createAndSignalProcessInstance(unCleaCollectOther, id);
		else
			WorkflowService.signalProcessInstance(unCleaCollectOther, id, null);
	}

	/**
	 * 保存或更新其他公司未清收款(贷方)
	 * 保存  
	 *  
	 * @param unCleaCollectOther
	 */
	public void _update(UnCleaCollectOther unCleaCollectOther
,BusinessObject businessObject	
	)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(unCleaCollectOther);
		unCleaCollectOtherHibernateDao.saveOrUpdate(unCleaCollectOther);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(unCleaCollectOther);
	}
	
	/**
	 * 保存  
	 *   
	 * @param unCleaCollectOther
	 */
	public void _save(UnCleaCollectOther unCleaCollectOther)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(unCleaCollectOther);
		// 对id赋空值，使之执行insert操作(适应复制创建场景)
		unCleaCollectOther.setUnclearcollectid(null);
                                              		unCleaCollectOtherHibernateDao.saveOrUpdate(unCleaCollectOther);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(unCleaCollectOther);
	}
	
	/**
	 *
	 * 保存  
	 *   
	 * @param unCleaCollectOther
	 */
	public void _saveOrUpdate(UnCleaCollectOther unCleaCollectOther
,BusinessObject businessObject		
	)
	{
		if (StringUtils.isNullBlank(unCleaCollectOther.getUnclearcollectid()))
		{	
			_save(unCleaCollectOther);
		}
		else
		{
			_update(unCleaCollectOther
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