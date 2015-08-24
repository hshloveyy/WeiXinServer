/*
 * @(#)HomeFundFlowServiceGen.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年07月25日 09点58分43秒
 *  描　述：创建
 */
package com.infolion.xdss3.paymentGen.service;

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
import com.infolion.xdss3.payment.domain.HomeFundFlow;
import com.infolion.xdss3.payment.service.HomeFundFlowService;
import com.infolion.xdss3.payment.dao.HomeFundFlowHibernateDao;

/**
 * <pre>
 * 内贸纯资金往来(HomeFundFlow)服务类
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
public class HomeFundFlowServiceGen extends BaseService
{
	protected Log log = LogFactory.getFactory().getLog(this.getClass());

	@Autowired
	protected HomeFundFlowHibernateDao homeFundFlowHibernateDao;
	
	public void setHomeFundFlowHibernateDao(HomeFundFlowHibernateDao homeFundFlowHibernateDao)
	{
		this.homeFundFlowHibernateDao = homeFundFlowHibernateDao;
	}
	
     /**
	 * 注入扩展服务类
	 */
	 @Autowired(required = false)
	 public void setAdvanceService(@Qualifier("homeFundFlowAdvanceService") AdvanceService advanceService)
	 {
	  	super.setAdvanceService(advanceService);
	 }   
	 
	   
	/**
	 * 根据主键ID,取得内贸纯资金往来实例
	 * @param id
	 * @return
	 */
	public HomeFundFlow _getDetached(String id)
	{		
	    HomeFundFlow homeFundFlow = new HomeFundFlow();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  homeFundFlow = homeFundFlowHibernateDao.getDetached(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(homeFundFlow);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    homeFundFlow.setOperationType(operationType);
	    
		return homeFundFlow;	
	}
	
	/**
	 * 根据主键ID,取得内贸纯资金往来实例
	 * @param id
	 * @return
	 */
	public HomeFundFlow _get(String id)
	{		
	    HomeFundFlow homeFundFlow = new HomeFundFlow();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  homeFundFlow = homeFundFlowHibernateDao.get(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(homeFundFlow);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    homeFundFlow.setOperationType(operationType);
	    
		return homeFundFlow;	
	}
	
	/**
	 * 根据主键ID,取得内贸纯资金往来实例
	 * @param id
	 * @return
	 */
	public HomeFundFlow _getForEdit(String id)
	{		
	    HomeFundFlow homeFundFlow = _get(id);
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = homeFundFlow.getOperationType();
	    if(OperationType.READONLY.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotEdit);
	    }

		return homeFundFlow;	
	}
	
	/**
	 * 根据主键ID,取得内贸纯资金往来实例副本
	 * @param id
	 * @return
	 */
	public HomeFundFlow _getEntityCopy(String id)
	{		
	    HomeFundFlow homeFundFlow = new HomeFundFlow();
		HomeFundFlow homeFundFlowOld = this._get(id);
		try
		{
			BeanUtils.copyProperties(homeFundFlow, homeFundFlowOld);
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (InvocationTargetException e)
		{
			e.printStackTrace();
		}
		//homeFundFlow.setFundflowid(null); 
		return homeFundFlow;	
	}
	
	/**
	 * 删除  
	 *   
	 * @param homeFundFlow
	 */
	public void _delete(HomeFundFlow homeFundFlow)
	{
		if (null != advanceService)
			advanceService.preDelete(homeFundFlow);
	
 		LockService.isBoInstanceLocked(homeFundFlow,HomeFundFlow.class);
		homeFundFlowHibernateDao.remove(homeFundFlow);

		if (null != advanceService)
			advanceService.postDelete(homeFundFlow);
	}

	/**
	 * 根据主键删除
	 * 
	 * @param homeFundFlowId
	 */
	public void _delete(String homeFundFlowId,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(homeFundFlowId))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("fundflowid"));
		HomeFundFlow homeFundFlow = this.homeFundFlowHibernateDao.load(homeFundFlowId);
		_delete(homeFundFlow);
	}
	
	/**
	 * 删除  
	 *   
	 * @param Set<HomeFundFlow> homeFundFlows
	 */
	public void _deletes(Set<HomeFundFlow> homeFundFlows,BusinessObject businessObject)
	{
		if (null == homeFundFlows || homeFundFlows.size() < 1)
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoText());
		Iterator<HomeFundFlow> it = homeFundFlows.iterator();
		while (it.hasNext())
		{
			HomeFundFlow homeFundFlow = (HomeFundFlow) it.next();
			_delete(homeFundFlow);
		}
	}
	
	/**
	 * 根据多个主键(由，分割)删除多个对象
	 * 
	 * @param homeFundFlowIds
	 */
	public void _deletes(String homeFundFlowIds,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(homeFundFlowIds))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("fundflowid"));
		String[] ids = StringUtils.splitString(homeFundFlowIds);
		for (int i = 0; i < ids.length; i++)
		{
			_delete(ids[i],businessObject);
		}
	}
	/**
	 * 提交工作流
	 * 
	 * @param homeFundFlow
	 */
	public void _submitProcess(HomeFundFlow homeFundFlow
	,BusinessObject businessObject)
	{
		String id = homeFundFlow.getFundflowid();
        /**
		if (StringUtils.isNullBlank(id))
		{
			_save(homeFundFlow);
		}
		else
		{
			_update(homeFundFlow
			, businessObject);
		}**/
		
		String taskId = homeFundFlow.getWorkflowTaskId();
		id = homeFundFlow.getFundflowid();
		if (null == taskId || "".equals(taskId))
			WorkflowService.createAndSignalProcessInstance(homeFundFlow, id);
		else
			WorkflowService.signalProcessInstance(homeFundFlow, id, null);
	}

	/**
	 * 保存或更新内贸纯资金往来
	 * 保存  
	 *  
	 * @param homeFundFlow
	 */
	public void _update(HomeFundFlow homeFundFlow
,BusinessObject businessObject	
	)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(homeFundFlow);
		homeFundFlowHibernateDao.saveOrUpdate(homeFundFlow);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(homeFundFlow);
	}
	
	/**
	 * 保存  
	 *   
	 * @param homeFundFlow
	 */
	public void _save(HomeFundFlow homeFundFlow)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(homeFundFlow);
		// 对id赋空值，使之执行insert操作(适应复制创建场景)
		homeFundFlow.setFundflowid(null);
                                                        		homeFundFlowHibernateDao.saveOrUpdate(homeFundFlow);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(homeFundFlow);
	}
	
	/**
	 *
	 * 保存  
	 *   
	 * @param homeFundFlow
	 */
	public void _saveOrUpdate(HomeFundFlow homeFundFlow
,BusinessObject businessObject		
	)
	{
		if (StringUtils.isNullBlank(homeFundFlow.getFundflowid()))
		{	
			_save(homeFundFlow);
		}
		else
		{
			_update(homeFundFlow
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