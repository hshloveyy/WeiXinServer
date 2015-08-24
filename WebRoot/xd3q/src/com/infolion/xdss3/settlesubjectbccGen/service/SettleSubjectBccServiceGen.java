/*
 * @(#)SettleSubjectBccServiceGen.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年09月16日 09点35分06秒
 *  描　述：创建
 */
package com.infolion.xdss3.settlesubjectbccGen.service;

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
import com.infolion.xdss3.settlesubjectbcc.domain.SettleSubjectBcc;
import com.infolion.xdss3.settlesubjectbcc.service.SettleSubjectBccService;
import com.infolion.xdss3.settlesubjectbcc.dao.SettleSubjectBccHibernateDao;

/**
 * <pre>
 * 结算科目(SettleSubjectBcc)服务类
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
public class SettleSubjectBccServiceGen extends BaseService
{
	protected Log log = LogFactory.getFactory().getLog(this.getClass());

	@Autowired
	protected SettleSubjectBccHibernateDao settleSubjectBccHibernateDao;
	
	public void setSettleSubjectBccHibernateDao(SettleSubjectBccHibernateDao settleSubjectBccHibernateDao)
	{
		this.settleSubjectBccHibernateDao = settleSubjectBccHibernateDao;
	}
	
     /**
	 * 注入扩展服务类
	 */
	 @Autowired(required = false)
	 public void setAdvanceService(@Qualifier("settleSubjectBccAdvanceService") AdvanceService advanceService)
	 {
	  	super.setAdvanceService(advanceService);
	 }   
	 
	   
	/**
	 * 根据主键ID,取得结算科目实例
	 * @param id
	 * @return
	 */
	public SettleSubjectBcc _getDetached(String id)
	{		
	    SettleSubjectBcc settleSubjectBcc = new SettleSubjectBcc();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  settleSubjectBcc = settleSubjectBccHibernateDao.getDetached(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(settleSubjectBcc);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    settleSubjectBcc.setOperationType(operationType);
	    
		return settleSubjectBcc;	
	}
	
	/**
	 * 根据主键ID,取得结算科目实例
	 * @param id
	 * @return
	 */
	public SettleSubjectBcc _get(String id)
	{		
	    SettleSubjectBcc settleSubjectBcc = new SettleSubjectBcc();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  settleSubjectBcc = settleSubjectBccHibernateDao.get(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(settleSubjectBcc);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    settleSubjectBcc.setOperationType(operationType);
	    
		return settleSubjectBcc;	
	}
	
	/**
	 * 根据主键ID,取得结算科目实例
	 * @param id
	 * @return
	 */
	public SettleSubjectBcc _getForEdit(String id)
	{		
	    SettleSubjectBcc settleSubjectBcc = _get(id);
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = settleSubjectBcc.getOperationType();
	    if(OperationType.READONLY.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotEdit);
	    }

		return settleSubjectBcc;	
	}
	
	/**
	 * 根据主键ID,取得结算科目实例副本
	 * @param id
	 * @return
	 */
	public SettleSubjectBcc _getEntityCopy(String id)
	{		
	    SettleSubjectBcc settleSubjectBcc = new SettleSubjectBcc();
		SettleSubjectBcc settleSubjectBccOld = this._get(id);
		try
		{
			BeanUtils.copyProperties(settleSubjectBcc, settleSubjectBccOld);
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (InvocationTargetException e)
		{
			e.printStackTrace();
		}
		//settleSubjectBcc.setSettlesubjectid(null); 
		return settleSubjectBcc;	
	}
	
	/**
	 * 删除  
	 *   
	 * @param settleSubjectBcc
	 */
	public void _delete(SettleSubjectBcc settleSubjectBcc)
	{
		if (null != advanceService)
			advanceService.preDelete(settleSubjectBcc);
	
 		LockService.isBoInstanceLocked(settleSubjectBcc,SettleSubjectBcc.class);
		settleSubjectBccHibernateDao.remove(settleSubjectBcc);

		if (null != advanceService)
			advanceService.postDelete(settleSubjectBcc);
	}

	/**
	 * 根据主键删除
	 * 
	 * @param settleSubjectBccId
	 */
	public void _delete(String settleSubjectBccId,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(settleSubjectBccId))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("settlesubjectid"));
		SettleSubjectBcc settleSubjectBcc = this.settleSubjectBccHibernateDao.load(settleSubjectBccId);
		_delete(settleSubjectBcc);
	}
	
	/**
	 * 删除  
	 *   
	 * @param Set<SettleSubjectBcc> settleSubjectBccs
	 */
	public void _deletes(Set<SettleSubjectBcc> settleSubjectBccs,BusinessObject businessObject)
	{
		if (null == settleSubjectBccs || settleSubjectBccs.size() < 1)
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoText());
		Iterator<SettleSubjectBcc> it = settleSubjectBccs.iterator();
		while (it.hasNext())
		{
			SettleSubjectBcc settleSubjectBcc = (SettleSubjectBcc) it.next();
			_delete(settleSubjectBcc);
		}
	}
	
	/**
	 * 根据多个主键(由，分割)删除多个对象
	 * 
	 * @param settleSubjectBccIds
	 */
	public void _deletes(String settleSubjectBccIds,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(settleSubjectBccIds))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("settlesubjectid"));
		String[] ids = StringUtils.splitString(settleSubjectBccIds);
		for (int i = 0; i < ids.length; i++)
		{
			_delete(ids[i],businessObject);
		}
	}
	/**
	 * 提交工作流
	 * 
	 * @param settleSubjectBcc
	 */
	public void _submitProcess(SettleSubjectBcc settleSubjectBcc
	,BusinessObject businessObject)
	{
		String id = settleSubjectBcc.getSettlesubjectid();
        /**
		if (StringUtils.isNullBlank(id))
		{
			_save(settleSubjectBcc);
		}
		else
		{
			_update(settleSubjectBcc
			, businessObject);
		}**/
		
		String taskId = settleSubjectBcc.getWorkflowTaskId();
		id = settleSubjectBcc.getSettlesubjectid();
		if (null == taskId || "".equals(taskId))
			WorkflowService.createAndSignalProcessInstance(settleSubjectBcc, id);
		else
			WorkflowService.signalProcessInstance(settleSubjectBcc, id, null);
	}

	/**
	 * 保存或更新结算科目
	 * 保存  
	 *  
	 * @param settleSubjectBcc
	 */
	public void _update(SettleSubjectBcc settleSubjectBcc
,BusinessObject businessObject	
	)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(settleSubjectBcc);
		settleSubjectBccHibernateDao.saveOrUpdate(settleSubjectBcc);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(settleSubjectBcc);
	}
	
	/**
	 * 保存  
	 *   
	 * @param settleSubjectBcc
	 */
	public void _save(SettleSubjectBcc settleSubjectBcc)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(settleSubjectBcc);
		// 对id赋空值，使之执行insert操作(适应复制创建场景)
		settleSubjectBcc.setSettlesubjectid(null);
                                                                                  		settleSubjectBccHibernateDao.saveOrUpdate(settleSubjectBcc);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(settleSubjectBcc);
	}
	
	/**
	 *
	 * 保存  
	 *   
	 * @param settleSubjectBcc
	 */
	public void _saveOrUpdate(SettleSubjectBcc settleSubjectBcc
,BusinessObject businessObject		
	)
	{
		if (StringUtils.isNullBlank(settleSubjectBcc.getSettlesubjectid()))
		{	
			_save(settleSubjectBcc);
		}
		else
		{
			_update(settleSubjectBcc
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