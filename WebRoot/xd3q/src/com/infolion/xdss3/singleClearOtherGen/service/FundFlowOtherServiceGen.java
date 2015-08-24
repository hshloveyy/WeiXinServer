/*
 * @(#)FundFlowOtherServiceGen.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2012年06月08日 11点57分49秒
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
import com.infolion.xdss3.singleClearOther.domain.FundFlowOther;
import com.infolion.xdss3.singleClearOther.service.FundFlowOtherService;
import com.infolion.xdss3.singleClearOther.dao.FundFlowOtherHibernateDao;

/**
 * <pre>
 * 其他公司纯资金往来(FundFlowOther)服务类
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
public class FundFlowOtherServiceGen extends BaseService
{
	protected Log log = LogFactory.getFactory().getLog(this.getClass());

	@Autowired
	protected FundFlowOtherHibernateDao fundFlowOtherHibernateDao;
	
	public void setFundFlowOtherHibernateDao(FundFlowOtherHibernateDao fundFlowOtherHibernateDao)
	{
		this.fundFlowOtherHibernateDao = fundFlowOtherHibernateDao;
	}
	
     /**
	 * 注入扩展服务类
	 */
	 @Autowired(required = false)
	 public void setAdvanceService(@Qualifier("fundFlowOtherAdvanceService") AdvanceService advanceService)
	 {
	  	super.setAdvanceService(advanceService);
	 }   
	 
	   
	/**
	 * 根据主键ID,取得其他公司纯资金往来实例
	 * @param id
	 * @return
	 */
	public FundFlowOther _getDetached(String id)
	{		
	    FundFlowOther fundFlowOther = new FundFlowOther();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  fundFlowOther = fundFlowOtherHibernateDao.getDetached(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(fundFlowOther);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    fundFlowOther.setOperationType(operationType);
	    
		return fundFlowOther;	
	}
	
	/**
	 * 根据主键ID,取得其他公司纯资金往来实例
	 * @param id
	 * @return
	 */
	public FundFlowOther _get(String id)
	{		
	    FundFlowOther fundFlowOther = new FundFlowOther();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  fundFlowOther = fundFlowOtherHibernateDao.get(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(fundFlowOther);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    fundFlowOther.setOperationType(operationType);
	    
		return fundFlowOther;	
	}
	
	/**
	 * 根据主键ID,取得其他公司纯资金往来实例
	 * @param id
	 * @return
	 */
	public FundFlowOther _getForEdit(String id)
	{		
	    FundFlowOther fundFlowOther = _get(id);
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = fundFlowOther.getOperationType();
	    if(OperationType.READONLY.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotEdit);
	    }

		return fundFlowOther;	
	}
	
	/**
	 * 根据主键ID,取得其他公司纯资金往来实例副本
	 * @param id
	 * @return
	 */
	public FundFlowOther _getEntityCopy(String id)
	{		
	    FundFlowOther fundFlowOther = new FundFlowOther();
		FundFlowOther fundFlowOtherOld = this._get(id);
		try
		{
			BeanUtils.copyProperties(fundFlowOther, fundFlowOtherOld);
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (InvocationTargetException e)
		{
			e.printStackTrace();
		}
		//fundFlowOther.setFundflowid(null); 
		return fundFlowOther;	
	}
	
	/**
	 * 删除  
	 *   
	 * @param fundFlowOther
	 */
	public void _delete(FundFlowOther fundFlowOther)
	{
		if (null != advanceService)
			advanceService.preDelete(fundFlowOther);
	
 		LockService.isBoInstanceLocked(fundFlowOther,FundFlowOther.class);
		fundFlowOtherHibernateDao.remove(fundFlowOther);

		if (null != advanceService)
			advanceService.postDelete(fundFlowOther);
	}

	/**
	 * 根据主键删除
	 * 
	 * @param fundFlowOtherId
	 */
	public void _delete(String fundFlowOtherId,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(fundFlowOtherId))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("fundflowid"));
		FundFlowOther fundFlowOther = this.fundFlowOtherHibernateDao.load(fundFlowOtherId);
		_delete(fundFlowOther);
	}
	
	/**
	 * 删除  
	 *   
	 * @param Set<FundFlowOther> fundFlowOthers
	 */
	public void _deletes(Set<FundFlowOther> fundFlowOthers,BusinessObject businessObject)
	{
		if (null == fundFlowOthers || fundFlowOthers.size() < 1)
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoText());
		Iterator<FundFlowOther> it = fundFlowOthers.iterator();
		while (it.hasNext())
		{
			FundFlowOther fundFlowOther = (FundFlowOther) it.next();
			_delete(fundFlowOther);
		}
	}
	
	/**
	 * 根据多个主键(由，分割)删除多个对象
	 * 
	 * @param fundFlowOtherIds
	 */
	public void _deletes(String fundFlowOtherIds,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(fundFlowOtherIds))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("fundflowid"));
		String[] ids = StringUtils.splitString(fundFlowOtherIds);
		for (int i = 0; i < ids.length; i++)
		{
			_delete(ids[i],businessObject);
		}
	}
	/**
	 * 提交工作流
	 * 
	 * @param fundFlowOther
	 */
	public void _submitProcess(FundFlowOther fundFlowOther
	,BusinessObject businessObject)
	{
		String id = fundFlowOther.getFundflowid();
        /**
		if (StringUtils.isNullBlank(id))
		{
			_save(fundFlowOther);
		}
		else
		{
			_update(fundFlowOther
			, businessObject);
		}**/
		
		String taskId = fundFlowOther.getWorkflowTaskId();
		id = fundFlowOther.getFundflowid();
		if (null == taskId || "".equals(taskId))
			WorkflowService.createAndSignalProcessInstance(fundFlowOther, id);
		else
			WorkflowService.signalProcessInstance(fundFlowOther, id, null);
	}

	/**
	 * 保存或更新其他公司纯资金往来
	 * 保存  
	 *  
	 * @param fundFlowOther
	 */
	public void _update(FundFlowOther fundFlowOther
,BusinessObject businessObject	
	)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(fundFlowOther);
		fundFlowOtherHibernateDao.saveOrUpdate(fundFlowOther);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(fundFlowOther);
	}
	
	/**
	 * 保存  
	 *   
	 * @param fundFlowOther
	 */
	public void _save(FundFlowOther fundFlowOther)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(fundFlowOther);
		// 对id赋空值，使之执行insert操作(适应复制创建场景)
		fundFlowOther.setFundflowid(null);
                                                                        		fundFlowOtherHibernateDao.saveOrUpdate(fundFlowOther);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(fundFlowOther);
	}
	
	/**
	 *
	 * 保存  
	 *   
	 * @param fundFlowOther
	 */
	public void _saveOrUpdate(FundFlowOther fundFlowOther
,BusinessObject businessObject		
	)
	{
		if (StringUtils.isNullBlank(fundFlowOther.getFundflowid()))
		{	
			_save(fundFlowOther);
		}
		else
		{
			_update(fundFlowOther
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