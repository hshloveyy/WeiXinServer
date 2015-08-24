/*
 * @(#)UnnamerCollectServiceGen.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年07月06日 10点45分06秒
 *  描　述：创建
 */
package com.infolion.xdss3.unnameCollectGen.service;

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
import com.infolion.xdss3.unnameCollect.domain.UnnamerCollect;
import com.infolion.xdss3.unnameCollect.service.UnnamerCollectService;
import com.infolion.xdss3.unnameCollect.dao.UnnamerCollectHibernateDao;

/**
 * <pre>
 * 未明户收款(UnnamerCollect)服务类
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
public class UnnamerCollectServiceGen extends BaseService
{
	protected Log log = LogFactory.getFactory().getLog(this.getClass());

	@Autowired
	protected UnnamerCollectHibernateDao unnamerCollectHibernateDao;
	
	public void setUnnamerCollectHibernateDao(UnnamerCollectHibernateDao unnamerCollectHibernateDao)
	{
		this.unnamerCollectHibernateDao = unnamerCollectHibernateDao;
	}
	
     /**
	 * 注入扩展服务类
	 */
	 @Autowired(required = false)
	 public void setAdvanceService(@Qualifier("unnamerCollectAdvanceService") AdvanceService advanceService)
	 {
	  	super.setAdvanceService(advanceService);
	 }   
	 
	   
	/**
	 * 根据主键ID,取得未明户收款实例
	 * @param id
	 * @return
	 */
	public UnnamerCollect _getDetached(String id)
	{		
	    UnnamerCollect unnamerCollect = new UnnamerCollect();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  unnamerCollect = unnamerCollectHibernateDao.getDetached(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(unnamerCollect);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    unnamerCollect.setOperationType(operationType);
	    
		return unnamerCollect;	
	}
	
	/**
	 * 根据主键ID,取得未明户收款实例
	 * @param id
	 * @return
	 */
	public UnnamerCollect _get(String id)
	{		
	    UnnamerCollect unnamerCollect = new UnnamerCollect();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  unnamerCollect = unnamerCollectHibernateDao.get(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(unnamerCollect);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    unnamerCollect.setOperationType(operationType);
	    
		return unnamerCollect;	
	}
	
	/**
	 * 根据主键ID,取得未明户收款实例
	 * @param id
	 * @return
	 */
	public UnnamerCollect _getForEdit(String id)
	{		
	    UnnamerCollect unnamerCollect = _get(id);
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = unnamerCollect.getOperationType();
	    if(OperationType.READONLY.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotEdit);
	    }

		return unnamerCollect;	
	}
	
	/**
	 * 根据主键ID,取得未明户收款实例副本
	 * @param id
	 * @return
	 */
	public UnnamerCollect _getEntityCopy(String id)
	{		
	    UnnamerCollect unnamerCollect = new UnnamerCollect();
		UnnamerCollect unnamerCollectOld = this._get(id);
		try
		{
			BeanUtils.copyProperties(unnamerCollect, unnamerCollectOld);
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (InvocationTargetException e)
		{
			e.printStackTrace();
		}
		unnamerCollect.setUnnamercollectno(null); 
		//unnamerCollect.setUnnamercollectid(null); 
		unnamerCollect.setProcessstate(" ");
		return unnamerCollect;	
	}
	
	/**
	 * 删除  
	 *   
	 * @param unnamerCollect
	 */
	public void _delete(UnnamerCollect unnamerCollect)
	{
		if (null != advanceService)
			advanceService.preDelete(unnamerCollect);
	
		//流程状态
		String processState =unnamerCollect.getProcessstate();
		if(!StringUtils.isNullBlank(processState))
		{
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.CannotDeleteApproveData);
		}
 		LockService.isBoInstanceLocked(unnamerCollect,UnnamerCollect.class);
		unnamerCollectHibernateDao.remove(unnamerCollect);

		if (null != advanceService)
			advanceService.postDelete(unnamerCollect);
	}

	/**
	 * 根据主键删除
	 * 
	 * @param unnamerCollectId
	 */
	public void _delete(String unnamerCollectId,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(unnamerCollectId))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("unnamercollectid"));
		UnnamerCollect unnamerCollect = this.unnamerCollectHibernateDao.load(unnamerCollectId);
		_delete(unnamerCollect);
	}
	
	/**
	 * 删除  
	 *   
	 * @param Set<UnnamerCollect> unnamerCollects
	 */
	public void _deletes(Set<UnnamerCollect> unnamerCollects,BusinessObject businessObject)
	{
		if (null == unnamerCollects || unnamerCollects.size() < 1)
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoText());
		Iterator<UnnamerCollect> it = unnamerCollects.iterator();
		while (it.hasNext())
		{
			UnnamerCollect unnamerCollect = (UnnamerCollect) it.next();
			_delete(unnamerCollect);
		}
	}
	
	/**
	 * 根据多个主键(由，分割)删除多个对象
	 * 
	 * @param unnamerCollectIds
	 */
	public void _deletes(String unnamerCollectIds,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(unnamerCollectIds))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("unnamercollectid"));
		String[] ids = StringUtils.splitString(unnamerCollectIds);
		for (int i = 0; i < ids.length; i++)
		{
			_delete(ids[i],businessObject);
		}
	}
	/**
	 * 提交工作流
	 * 
	 * @param unnamerCollect
	 */
	public void _submitProcess(UnnamerCollect unnamerCollect
	,BusinessObject businessObject)
	{
		String id = unnamerCollect.getUnnamercollectid();
        /**
		if (StringUtils.isNullBlank(id))
		{
			_save(unnamerCollect);
		}
		else
		{
			_update(unnamerCollect
			, businessObject);
		}**/
		
		String taskId = unnamerCollect.getWorkflowTaskId();
		id = unnamerCollect.getUnnamercollectid();
		if (null == taskId || "".equals(taskId))
			WorkflowService.createAndSignalProcessInstance(unnamerCollect, id);
		else
			WorkflowService.signalProcessInstance(unnamerCollect, id, null);
	}

	/**
	 * 保存或更新未明户收款
	 * 保存  
	 *  
	 * @param unnamerCollect
	 */
	public void _update(UnnamerCollect unnamerCollect
,BusinessObject businessObject	
	)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(unnamerCollect);
		unnamerCollectHibernateDao.saveOrUpdate(unnamerCollect);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(unnamerCollect);
	}
	
	/**
	 * 保存  
	 *   
	 * @param unnamerCollect
	 */
	public void _save(UnnamerCollect unnamerCollect)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(unnamerCollect);
		// 对id赋空值，使之执行insert操作(适应复制创建场景)
		unnamerCollect.setUnnamercollectid(null);
                                              		unnamerCollectHibernateDao.saveOrUpdate(unnamerCollect);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(unnamerCollect);
	}
	
	/**
	 *
	 * 保存  
	 *   
	 * @param unnamerCollect
	 */
	public void _saveOrUpdate(UnnamerCollect unnamerCollect
,BusinessObject businessObject		
	)
	{
if (StringUtils.isNullBlank(unnamerCollect.getUnnamercollectno()))
{
String unnamercollectno = NumberService.getNextObjectNumber("UnnamerCollectNo", unnamerCollect);
unnamerCollect.setUnnamercollectno(unnamercollectno);
}		if (StringUtils.isNullBlank(unnamerCollect.getUnnamercollectid()))
		{	
			_save(unnamerCollect);
		}
		else
		{
			_update(unnamerCollect
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