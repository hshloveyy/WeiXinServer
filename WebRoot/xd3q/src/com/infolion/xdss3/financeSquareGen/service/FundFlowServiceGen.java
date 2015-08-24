/*
 * @(#)FundFlowServiceGen.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年08月10日 02点20分34秒
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
import com.infolion.xdss3.financeSquare.domain.FundFlow;
import com.infolion.xdss3.financeSquare.service.FundFlowService;
import com.infolion.xdss3.financeSquare.dao.FundFlowHibernateDao;

/**
 * <pre>
 * 纯资金往来(FundFlow)服务类
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
public class FundFlowServiceGen extends BaseService
{
	protected Log log = LogFactory.getFactory().getLog(this.getClass());

	@Autowired
	protected FundFlowHibernateDao fundFlowHibernateDao;
	
	public void setFundFlowHibernateDao(FundFlowHibernateDao fundFlowHibernateDao)
	{
		this.fundFlowHibernateDao = fundFlowHibernateDao;
	}
	
     /**
	 * 注入扩展服务类
	 */
	 @Autowired(required = false)
	 public void setAdvanceService(@Qualifier("fundFlowAdvanceService") AdvanceService advanceService)
	 {
	  	super.setAdvanceService(advanceService);
	 }   
	 
	   
	/**
	 * 根据主键ID,取得纯资金往来实例
	 * @param id
	 * @return
	 */
	public FundFlow _getDetached(String id)
	{		
	    FundFlow fundFlow = new FundFlow();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  fundFlow = fundFlowHibernateDao.getDetached(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(fundFlow);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    fundFlow.setOperationType(operationType);
	    
		return fundFlow;	
	}
	
	/**
	 * 根据主键ID,取得纯资金往来实例
	 * @param id
	 * @return
	 */
	public FundFlow _get(String id)
	{		
	    FundFlow fundFlow = new FundFlow();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  fundFlow = fundFlowHibernateDao.get(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(fundFlow);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    fundFlow.setOperationType(operationType);
	    
		return fundFlow;	
	}
	
	/**
	 * 根据主键ID,取得纯资金往来实例
	 * @param id
	 * @return
	 */
	public FundFlow _getForEdit(String id)
	{		
	    FundFlow fundFlow = _get(id);
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = fundFlow.getOperationType();
	    if(OperationType.READONLY.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotEdit);
	    }

		return fundFlow;	
	}
	
	/**
	 * 根据主键ID,取得纯资金往来实例副本
	 * @param id
	 * @return
	 */
	public FundFlow _getEntityCopy(String id)
	{		
	    FundFlow fundFlow = new FundFlow();
		FundFlow fundFlowOld = this._get(id);
		try
		{
			BeanUtils.copyProperties(fundFlow, fundFlowOld);
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (InvocationTargetException e)
		{
			e.printStackTrace();
		}
		//fundFlow.setFundflowid(null); 
		return fundFlow;	
	}

	/**
	 * 保存或更新纯资金往来
	 * 保存  
	 *  
	 * @param fundFlow
	 */
	public void _update(FundFlow fundFlow
,BusinessObject businessObject	
	)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(fundFlow);
		fundFlowHibernateDao.saveOrUpdate(fundFlow);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(fundFlow);
	}
	
	/**
	 * 保存  
	 *   
	 * @param fundFlow
	 */
	public void _save(FundFlow fundFlow)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(fundFlow);
		// 对id赋空值，使之执行insert操作(适应复制创建场景)
		fundFlow.setFundflowid(null);
                                                        		fundFlowHibernateDao.saveOrUpdate(fundFlow);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(fundFlow);
	}
	
	/**
	 *
	 * 保存  
	 *   
	 * @param fundFlow
	 */
	public void _saveOrUpdate(FundFlow fundFlow
,BusinessObject businessObject		
	)
	{
		if (StringUtils.isNullBlank(fundFlow.getFundflowid()))
		{	
			_save(fundFlow);
		}
		else
		{
			_update(fundFlow
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
	
	/**
	 * 删除  
	 *   
	 * @param fundFlow
	 */
	public void _delete(FundFlow fundFlow)
	{
		if (null != advanceService)
			advanceService.preDelete(fundFlow);
	
 		LockService.isBoInstanceLocked(fundFlow,FundFlow.class);
		fundFlowHibernateDao.remove(fundFlow);

		if (null != advanceService)
			advanceService.postDelete(fundFlow);
	}

	/**
	 * 根据主键删除
	 * 
	 * @param fundFlowId
	 */
	public void _delete(String fundFlowId,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(fundFlowId))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("fundflowid"));
		FundFlow fundFlow = this.fundFlowHibernateDao.load(fundFlowId);
		_delete(fundFlow);
	}
	
	/**
	 * 删除  
	 *   
	 * @param Set<FundFlow> fundFlows
	 */
	public void _deletes(Set<FundFlow> fundFlows,BusinessObject businessObject)
	{
		if (null == fundFlows || fundFlows.size() < 1)
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoText());
		Iterator<FundFlow> it = fundFlows.iterator();
		while (it.hasNext())
		{
			FundFlow fundFlow = (FundFlow) it.next();
			_delete(fundFlow);
		}
	}
	
	/**
	 * 根据多个主键(由，分割)删除多个对象
	 * 
	 * @param fundFlowIds
	 */
	public void _deletes(String fundFlowIds,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(fundFlowIds))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("fundflowid"));
		String[] ids = StringUtils.splitString(fundFlowIds);
		for (int i = 0; i < ids.length; i++)
		{
			_delete(ids[i],businessObject);
		}
	}
	/**
	 * 提交工作流
	 * 
	 * @param fundFlow
	 */
	public void _submitProcess(FundFlow fundFlow
	,BusinessObject businessObject)
	{
		String id = fundFlow.getFundflowid();
        /**
		if (StringUtils.isNullBlank(id))
		{
			_save(fundFlow);
		}
		else
		{
			_update(fundFlow
			, businessObject);
		}**/
		
		String taskId = fundFlow.getWorkflowTaskId();
		id = fundFlow.getFundflowid();
		if (null == taskId || "".equals(taskId))
			WorkflowService.createAndSignalProcessInstance(fundFlow, id);
		else
			WorkflowService.signalProcessInstance(fundFlow, id, null);
	}
}