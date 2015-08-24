/*
 * @(#)FundFlowBccServiceGen.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年09月16日 09点35分06秒
 *  描　述：创建
 */
package com.infolion.xdss3.fundflowBccGen.service;

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
import com.infolion.xdss3.fundflowBcc.domain.FundFlowBcc;
import com.infolion.xdss3.fundflowBcc.service.FundFlowBccService;
import com.infolion.xdss3.fundflowBcc.dao.FundFlowBccHibernateDao;

/**
 * <pre>
 * 纯资金往来(FundFlowBcc)服务类
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
public class FundFlowBccServiceGen extends BaseService
{
	protected Log log = LogFactory.getFactory().getLog(this.getClass());

	@Autowired
	protected FundFlowBccHibernateDao fundFlowBccHibernateDao;
	
	public void setFundFlowBccHibernateDao(FundFlowBccHibernateDao fundFlowBccHibernateDao)
	{
		this.fundFlowBccHibernateDao = fundFlowBccHibernateDao;
	}
	
     /**
	 * 注入扩展服务类
	 */
	 @Autowired(required = false)
	 public void setAdvanceService(@Qualifier("fundFlowBccAdvanceService") AdvanceService advanceService)
	 {
	  	super.setAdvanceService(advanceService);
	 }   
	 
	   
	/**
	 * 根据主键ID,取得纯资金往来实例
	 * @param id
	 * @return
	 */
	public FundFlowBcc _getDetached(String id)
	{		
	    FundFlowBcc fundFlowBcc = new FundFlowBcc();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  fundFlowBcc = fundFlowBccHibernateDao.getDetached(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(fundFlowBcc);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    fundFlowBcc.setOperationType(operationType);
	    
		return fundFlowBcc;	
	}
	
	/**
	 * 根据主键ID,取得纯资金往来实例
	 * @param id
	 * @return
	 */
	public FundFlowBcc _get(String id)
	{		
	    FundFlowBcc fundFlowBcc = new FundFlowBcc();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  fundFlowBcc = fundFlowBccHibernateDao.get(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(fundFlowBcc);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    fundFlowBcc.setOperationType(operationType);
	    
		return fundFlowBcc;	
	}
	
	/**
	 * 根据主键ID,取得纯资金往来实例
	 * @param id
	 * @return
	 */
	public FundFlowBcc _getForEdit(String id)
	{		
	    FundFlowBcc fundFlowBcc = _get(id);
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = fundFlowBcc.getOperationType();
	    if(OperationType.READONLY.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotEdit);
	    }

		return fundFlowBcc;	
	}
	
	/**
	 * 根据主键ID,取得纯资金往来实例副本
	 * @param id
	 * @return
	 */
	public FundFlowBcc _getEntityCopy(String id)
	{		
	    FundFlowBcc fundFlowBcc = new FundFlowBcc();
		FundFlowBcc fundFlowBccOld = this._get(id);
		try
		{
			BeanUtils.copyProperties(fundFlowBcc, fundFlowBccOld);
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (InvocationTargetException e)
		{
			e.printStackTrace();
		}
		//fundFlowBcc.setFundflowid(null); 
		return fundFlowBcc;	
	}
	
	/**
	 * 删除  
	 *   
	 * @param fundFlowBcc
	 */
	public void _delete(FundFlowBcc fundFlowBcc)
	{
		if (null != advanceService)
			advanceService.preDelete(fundFlowBcc);
	
 		LockService.isBoInstanceLocked(fundFlowBcc,FundFlowBcc.class);
		fundFlowBccHibernateDao.remove(fundFlowBcc);

		if (null != advanceService)
			advanceService.postDelete(fundFlowBcc);
	}

	/**
	 * 根据主键删除
	 * 
	 * @param fundFlowBccId
	 */
	public void _delete(String fundFlowBccId,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(fundFlowBccId))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("fundflowid"));
		FundFlowBcc fundFlowBcc = this.fundFlowBccHibernateDao.load(fundFlowBccId);
		_delete(fundFlowBcc);
	}
	
	/**
	 * 删除  
	 *   
	 * @param Set<FundFlowBcc> fundFlowBccs
	 */
	public void _deletes(Set<FundFlowBcc> fundFlowBccs,BusinessObject businessObject)
	{
		if (null == fundFlowBccs || fundFlowBccs.size() < 1)
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoText());
		Iterator<FundFlowBcc> it = fundFlowBccs.iterator();
		while (it.hasNext())
		{
			FundFlowBcc fundFlowBcc = (FundFlowBcc) it.next();
			_delete(fundFlowBcc);
		}
	}
	
	/**
	 * 根据多个主键(由，分割)删除多个对象
	 * 
	 * @param fundFlowBccIds
	 */
	public void _deletes(String fundFlowBccIds,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(fundFlowBccIds))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("fundflowid"));
		String[] ids = StringUtils.splitString(fundFlowBccIds);
		for (int i = 0; i < ids.length; i++)
		{
			_delete(ids[i],businessObject);
		}
	}
	/**
	 * 提交工作流
	 * 
	 * @param fundFlowBcc
	 */
	public void _submitProcess(FundFlowBcc fundFlowBcc
	,BusinessObject businessObject)
	{
		String id = fundFlowBcc.getFundflowid();
        /**
		if (StringUtils.isNullBlank(id))
		{
			_save(fundFlowBcc);
		}
		else
		{
			_update(fundFlowBcc
			, businessObject);
		}**/
		
		String taskId = fundFlowBcc.getWorkflowTaskId();
		id = fundFlowBcc.getFundflowid();
		if (null == taskId || "".equals(taskId))
			WorkflowService.createAndSignalProcessInstance(fundFlowBcc, id);
		else
			WorkflowService.signalProcessInstance(fundFlowBcc, id, null);
	}

	/**
	 * 保存或更新纯资金往来
	 * 保存  
	 *  
	 * @param fundFlowBcc
	 */
	public void _update(FundFlowBcc fundFlowBcc
,BusinessObject businessObject	
	)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(fundFlowBcc);
		fundFlowBccHibernateDao.saveOrUpdate(fundFlowBcc);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(fundFlowBcc);
	}
	
	/**
	 * 保存  
	 *   
	 * @param fundFlowBcc
	 */
	public void _save(FundFlowBcc fundFlowBcc)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(fundFlowBcc);
		// 对id赋空值，使之执行insert操作(适应复制创建场景)
		fundFlowBcc.setFundflowid(null);
                                                    		fundFlowBccHibernateDao.saveOrUpdate(fundFlowBcc);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(fundFlowBcc);
	}
	
	/**
	 *
	 * 保存  
	 *   
	 * @param fundFlowBcc
	 */
	public void _saveOrUpdate(FundFlowBcc fundFlowBcc
,BusinessObject businessObject		
	)
	{
		if (StringUtils.isNullBlank(fundFlowBcc.getFundflowid()))
		{	
			_save(fundFlowBcc);
		}
		else
		{
			_update(fundFlowBcc
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