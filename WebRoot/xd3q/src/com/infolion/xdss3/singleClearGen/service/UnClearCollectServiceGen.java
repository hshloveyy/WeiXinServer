/*
 * @(#)UnClearCollectServiceGen.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年07月19日 11点44分19秒
 *  描　述：创建
 */
package com.infolion.xdss3.singleClearGen.service;

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
import com.infolion.xdss3.singleClear.domain.UnClearCollect;
import com.infolion.xdss3.singleClear.service.UnClearCollectService;
import com.infolion.xdss3.singleClear.dao.UnClearCollectHibernateDao;

/**
 * <pre>
 * 未清收款(贷方)(UnClearCollect)服务类
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
public class UnClearCollectServiceGen extends BaseService
{
	protected Log log = LogFactory.getFactory().getLog(this.getClass());

	@Autowired
	protected UnClearCollectHibernateDao unClearCollectHibernateDao;
	
	public void setUnClearCollectHibernateDao(UnClearCollectHibernateDao unClearCollectHibernateDao)
	{
		this.unClearCollectHibernateDao = unClearCollectHibernateDao;
	}
	
     /**
	 * 注入扩展服务类
	 */
	 @Autowired(required = false)
	 public void setAdvanceService(@Qualifier("unClearCollectAdvanceService") AdvanceService advanceService)
	 {
	  	super.setAdvanceService(advanceService);
	 }   
	 
	   
	/**
	 * 根据主键ID,取得未清收款(贷方)实例
	 * @param id
	 * @return
	 */
	public UnClearCollect _getDetached(String id)
	{		
	    UnClearCollect unClearCollect = new UnClearCollect();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  unClearCollect = unClearCollectHibernateDao.getDetached(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(unClearCollect);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    unClearCollect.setOperationType(operationType);
	    
		return unClearCollect;	
	}
	
	/**
	 * 根据主键ID,取得未清收款(贷方)实例
	 * @param id
	 * @return
	 */
	public UnClearCollect _get(String id)
	{		
	    UnClearCollect unClearCollect = new UnClearCollect();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  unClearCollect = unClearCollectHibernateDao.get(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(unClearCollect);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    unClearCollect.setOperationType(operationType);
	    
		return unClearCollect;	
	}
	
	/**
	 * 根据主键ID,取得未清收款(贷方)实例
	 * @param id
	 * @return
	 */
	public UnClearCollect _getForEdit(String id)
	{		
	    UnClearCollect unClearCollect = _get(id);
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = unClearCollect.getOperationType();
	    if(OperationType.READONLY.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotEdit);
	    }

		return unClearCollect;	
	}
	
	/**
	 * 根据主键ID,取得未清收款(贷方)实例副本
	 * @param id
	 * @return
	 */
	public UnClearCollect _getEntityCopy(String id)
	{		
	    UnClearCollect unClearCollect = new UnClearCollect();
		UnClearCollect unClearCollectOld = this._get(id);
		try
		{
			BeanUtils.copyProperties(unClearCollect, unClearCollectOld);
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (InvocationTargetException e)
		{
			e.printStackTrace();
		}
		//unClearCollect.setUnclearcollectid(null); 
		return unClearCollect;	
	}
	
	/**
	 * 删除  
	 *   
	 * @param unClearCollect
	 */
	public void _delete(UnClearCollect unClearCollect)
	{
		if (null != advanceService)
			advanceService.preDelete(unClearCollect);
	
 		LockService.isBoInstanceLocked(unClearCollect,UnClearCollect.class);
		unClearCollectHibernateDao.remove(unClearCollect);

		if (null != advanceService)
			advanceService.postDelete(unClearCollect);
	}

	/**
	 * 根据主键删除
	 * 
	 * @param unClearCollectId
	 */
	public void _delete(String unClearCollectId,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(unClearCollectId))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("unclearcollectid"));
		UnClearCollect unClearCollect = this.unClearCollectHibernateDao.load(unClearCollectId);
		_delete(unClearCollect);
	}
	
	/**
	 * 删除  
	 *   
	 * @param Set<UnClearCollect> unClearCollects
	 */
	public void _deletes(Set<UnClearCollect> unClearCollects,BusinessObject businessObject)
	{
		if (null == unClearCollects || unClearCollects.size() < 1)
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoText());
		Iterator<UnClearCollect> it = unClearCollects.iterator();
		while (it.hasNext())
		{
			UnClearCollect unClearCollect = (UnClearCollect) it.next();
			_delete(unClearCollect);
		}
	}
	
	/**
	 * 根据多个主键(由，分割)删除多个对象
	 * 
	 * @param unClearCollectIds
	 */
	public void _deletes(String unClearCollectIds,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(unClearCollectIds))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("unclearcollectid"));
		String[] ids = StringUtils.splitString(unClearCollectIds);
		for (int i = 0; i < ids.length; i++)
		{
			_delete(ids[i],businessObject);
		}
	}
	/**
	 * 提交工作流
	 * 
	 * @param unClearCollect
	 */
	public void _submitProcess(UnClearCollect unClearCollect
	,BusinessObject businessObject)
	{
		String id = unClearCollect.getUnclearcollectid();
        /**
		if (StringUtils.isNullBlank(id))
		{
			_save(unClearCollect);
		}
		else
		{
			_update(unClearCollect
			, businessObject);
		}**/
		
		String taskId = unClearCollect.getWorkflowTaskId();
		id = unClearCollect.getUnclearcollectid();
		if (null == taskId || "".equals(taskId))
			WorkflowService.createAndSignalProcessInstance(unClearCollect, id);
		else
			WorkflowService.signalProcessInstance(unClearCollect, id, null);
	}

	/**
	 * 保存或更新未清收款(贷方)
	 * 保存  
	 *  
	 * @param unClearCollect
	 */
	public void _update(UnClearCollect unClearCollect
,BusinessObject businessObject	
	)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(unClearCollect);
		unClearCollectHibernateDao.saveOrUpdate(unClearCollect);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(unClearCollect);
	}
	
	/**
	 * 保存  
	 *   
	 * @param unClearCollect
	 */
	public void _save(UnClearCollect unClearCollect)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(unClearCollect);
		// 对id赋空值，使之执行insert操作(适应复制创建场景)
		unClearCollect.setUnclearcollectid(null);
                                    		unClearCollectHibernateDao.saveOrUpdate(unClearCollect);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(unClearCollect);
	}
	
	/**
	 *
	 * 保存  
	 *   
	 * @param unClearCollect
	 */
	public void _saveOrUpdate(UnClearCollect unClearCollect
,BusinessObject businessObject		
	)
	{
		if (StringUtils.isNullBlank(unClearCollect.getUnclearcollectid()))
		{	
			_save(unClearCollect);
		}
		else
		{
			_update(unClearCollect
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