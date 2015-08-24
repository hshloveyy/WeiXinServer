/*
 * @(#)HomePaymentQueryServiceGen.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年10月01日 05点46分02秒
 *  描　述：创建
 */
package com.infolion.xdss3.homepaymentqueryGen.service;

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
import com.infolion.xdss3.homepaymentquery.domain.HomePaymentQuery;
import com.infolion.xdss3.homepaymentquery.service.HomePaymentQueryService;
import com.infolion.xdss3.homepaymentquery.dao.HomePaymentQueryHibernateDao;

/**
 * <pre>
 * 内贸付款查询(HomePaymentQuery)服务类
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
public class HomePaymentQueryServiceGen extends BaseService
{
	protected Log log = LogFactory.getFactory().getLog(this.getClass());

	@Autowired
	protected HomePaymentQueryHibernateDao homePaymentQueryHibernateDao;
	
	public void setHomePaymentQueryHibernateDao(HomePaymentQueryHibernateDao homePaymentQueryHibernateDao)
	{
		this.homePaymentQueryHibernateDao = homePaymentQueryHibernateDao;
	}
	
     /**
	 * 注入扩展服务类
	 */
	 @Autowired(required = false)
	 public void setAdvanceService(@Qualifier("homePaymentQueryAdvanceService") AdvanceService advanceService)
	 {
	  	super.setAdvanceService(advanceService);
	 }   
	 
	   
	/**
	 * 根据主键ID,取得内贸付款查询实例
	 * @param id
	 * @return
	 */
	public HomePaymentQuery _getDetached(String id)
	{		
	    HomePaymentQuery homePaymentQuery = new HomePaymentQuery();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  homePaymentQuery = homePaymentQueryHibernateDao.getDetached(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(homePaymentQuery);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    homePaymentQuery.setOperationType(operationType);
	    
		return homePaymentQuery;	
	}
	
	/**
	 * 根据主键ID,取得内贸付款查询实例
	 * @param id
	 * @return
	 */
	public HomePaymentQuery _get(String id)
	{		
	    HomePaymentQuery homePaymentQuery = new HomePaymentQuery();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  homePaymentQuery = homePaymentQueryHibernateDao.get(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(homePaymentQuery);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    homePaymentQuery.setOperationType(operationType);
	    
		return homePaymentQuery;	
	}
	
	/**
	 * 根据主键ID,取得内贸付款查询实例
	 * @param id
	 * @return
	 */
	public HomePaymentQuery _getForEdit(String id)
	{		
	    HomePaymentQuery homePaymentQuery = _get(id);
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = homePaymentQuery.getOperationType();
	    if(OperationType.READONLY.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotEdit);
	    }

		return homePaymentQuery;	
	}
	
	/**
	 * 根据主键ID,取得内贸付款查询实例副本
	 * @param id
	 * @return
	 */
	public HomePaymentQuery _getEntityCopy(String id)
	{		
	    HomePaymentQuery homePaymentQuery = new HomePaymentQuery();
		HomePaymentQuery homePaymentQueryOld = this._get(id);
		try
		{
			BeanUtils.copyProperties(homePaymentQuery, homePaymentQueryOld);
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (InvocationTargetException e)
		{
			e.printStackTrace();
		}
		//homePaymentQuery.setPaymentid(null); 
		homePaymentQuery.setProcessstate(" ");
		return homePaymentQuery;	
	}
	
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
	 * @param homePaymentQuery
	 */
	public void _delete(HomePaymentQuery homePaymentQuery)
	{
		if (null != advanceService)
			advanceService.preDelete(homePaymentQuery);
	
		//流程状态
		String processState =homePaymentQuery.getProcessstate();
		if(!StringUtils.isNullBlank(processState))
		{
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.CannotDeleteApproveData);
		}
 		LockService.isBoInstanceLocked(homePaymentQuery,HomePaymentQuery.class);
		homePaymentQueryHibernateDao.remove(homePaymentQuery);

		if (null != advanceService)
			advanceService.postDelete(homePaymentQuery);
	}

	/**
	 * 根据主键删除
	 * 
	 * @param homePaymentQueryId
	 */
	public void _delete(String homePaymentQueryId,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(homePaymentQueryId))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("paymentid"));
		HomePaymentQuery homePaymentQuery = this.homePaymentQueryHibernateDao.load(homePaymentQueryId);
		_delete(homePaymentQuery);
	}
	
	/**
	 * 删除  
	 *   
	 * @param Set<HomePaymentQuery> homePaymentQuerys
	 */
	public void _deletes(Set<HomePaymentQuery> homePaymentQuerys,BusinessObject businessObject)
	{
		if (null == homePaymentQuerys || homePaymentQuerys.size() < 1)
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoText());
		Iterator<HomePaymentQuery> it = homePaymentQuerys.iterator();
		while (it.hasNext())
		{
			HomePaymentQuery homePaymentQuery = (HomePaymentQuery) it.next();
			_delete(homePaymentQuery);
		}
	}
	
	/**
	 * 根据多个主键(由，分割)删除多个对象
	 * 
	 * @param homePaymentQueryIds
	 */
	public void _deletes(String homePaymentQueryIds,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(homePaymentQueryIds))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("paymentid"));
		String[] ids = StringUtils.splitString(homePaymentQueryIds);
		for (int i = 0; i < ids.length; i++)
		{
			_delete(ids[i],businessObject);
		}
	}
	/**
	 * 提交工作流
	 * 
	 * @param homePaymentQuery
	 */
	public void _submitProcess(HomePaymentQuery homePaymentQuery
	,BusinessObject businessObject)
	{
		String id = homePaymentQuery.getPaymentid();
        /**
		if (StringUtils.isNullBlank(id))
		{
			_save(homePaymentQuery);
		}
		else
		{
			_update(homePaymentQuery
			, businessObject);
		}**/
		
		String taskId = homePaymentQuery.getWorkflowTaskId();
		id = homePaymentQuery.getPaymentid();
		if (null == taskId || "".equals(taskId))
			WorkflowService.createAndSignalProcessInstance(homePaymentQuery, id);
		else
			WorkflowService.signalProcessInstance(homePaymentQuery, id, null);
	}

	/**
	 * 保存或更新内贸付款查询
	 * 保存  
	 *  
	 * @param homePaymentQuery
	 */
	public void _update(HomePaymentQuery homePaymentQuery
,BusinessObject businessObject	
	)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(homePaymentQuery);
		homePaymentQueryHibernateDao.saveOrUpdate(homePaymentQuery);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(homePaymentQuery);
	}
	
	/**
	 * 保存  
	 *   
	 * @param homePaymentQuery
	 */
	public void _save(HomePaymentQuery homePaymentQuery)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(homePaymentQuery);
		// 对id赋空值，使之执行insert操作(适应复制创建场景)
		homePaymentQuery.setPaymentid(null);
                                                                                                                              		homePaymentQueryHibernateDao.saveOrUpdate(homePaymentQuery);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(homePaymentQuery);
	}
	
	/**
	 *
	 * 保存  
	 *   
	 * @param homePaymentQuery
	 */
	public void _saveOrUpdate(HomePaymentQuery homePaymentQuery
,BusinessObject businessObject		
	)
	{
		if (StringUtils.isNullBlank(homePaymentQuery.getPaymentid()))
		{	
			_save(homePaymentQuery);
		}
		else
		{
			_update(homePaymentQuery
, businessObject);
}	}
	
}