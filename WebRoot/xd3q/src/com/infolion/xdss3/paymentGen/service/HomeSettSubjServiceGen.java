/*
 * @(#)HomeSettSubjServiceGen.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年07月25日 09点58分42秒
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
import com.infolion.xdss3.payment.domain.HomeSettSubj;
import com.infolion.xdss3.payment.service.HomeSettSubjService;
import com.infolion.xdss3.payment.dao.HomeSettSubjHibernateDao;

/**
 * <pre>
 * 内贸付款结算科目(HomeSettSubj)服务类
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
public class HomeSettSubjServiceGen extends BaseService
{
	protected Log log = LogFactory.getFactory().getLog(this.getClass());

	@Autowired
	protected HomeSettSubjHibernateDao homeSettSubjHibernateDao;
	
	public void setHomeSettSubjHibernateDao(HomeSettSubjHibernateDao homeSettSubjHibernateDao)
	{
		this.homeSettSubjHibernateDao = homeSettSubjHibernateDao;
	}
	
     /**
	 * 注入扩展服务类
	 */
	 @Autowired(required = false)
	 public void setAdvanceService(@Qualifier("homeSettSubjAdvanceService") AdvanceService advanceService)
	 {
	  	super.setAdvanceService(advanceService);
	 }   
	 
	   
	/**
	 * 根据主键ID,取得内贸付款结算科目实例
	 * @param id
	 * @return
	 */
	public HomeSettSubj _getDetached(String id)
	{		
	    HomeSettSubj homeSettSubj = new HomeSettSubj();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  homeSettSubj = homeSettSubjHibernateDao.getDetached(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(homeSettSubj);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    homeSettSubj.setOperationType(operationType);
	    
		return homeSettSubj;	
	}
	
	/**
	 * 根据主键ID,取得内贸付款结算科目实例
	 * @param id
	 * @return
	 */
	public HomeSettSubj _get(String id)
	{		
	    HomeSettSubj homeSettSubj = new HomeSettSubj();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  homeSettSubj = homeSettSubjHibernateDao.get(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(homeSettSubj);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    homeSettSubj.setOperationType(operationType);
	    
		return homeSettSubj;	
	}
	
	/**
	 * 根据主键ID,取得内贸付款结算科目实例
	 * @param id
	 * @return
	 */
	public HomeSettSubj _getForEdit(String id)
	{		
	    HomeSettSubj homeSettSubj = _get(id);
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = homeSettSubj.getOperationType();
	    if(OperationType.READONLY.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotEdit);
	    }

		return homeSettSubj;	
	}
	
	/**
	 * 根据主键ID,取得内贸付款结算科目实例副本
	 * @param id
	 * @return
	 */
	public HomeSettSubj _getEntityCopy(String id)
	{		
	    HomeSettSubj homeSettSubj = new HomeSettSubj();
		HomeSettSubj homeSettSubjOld = this._get(id);
		try
		{
			BeanUtils.copyProperties(homeSettSubj, homeSettSubjOld);
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (InvocationTargetException e)
		{
			e.printStackTrace();
		}
		//homeSettSubj.setSettlesubjectid(null); 
		return homeSettSubj;	
	}
	
	/**
	 * 删除  
	 *   
	 * @param homeSettSubj
	 */
	public void _delete(HomeSettSubj homeSettSubj)
	{
		if (null != advanceService)
			advanceService.preDelete(homeSettSubj);
	
 		LockService.isBoInstanceLocked(homeSettSubj,HomeSettSubj.class);
		homeSettSubjHibernateDao.remove(homeSettSubj);

		if (null != advanceService)
			advanceService.postDelete(homeSettSubj);
	}

	/**
	 * 根据主键删除
	 * 
	 * @param homeSettSubjId
	 */
	public void _delete(String homeSettSubjId,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(homeSettSubjId))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("settlesubjectid"));
		HomeSettSubj homeSettSubj = this.homeSettSubjHibernateDao.load(homeSettSubjId);
		_delete(homeSettSubj);
	}
	
	/**
	 * 删除  
	 *   
	 * @param Set<HomeSettSubj> homeSettSubjs
	 */
	public void _deletes(Set<HomeSettSubj> homeSettSubjs,BusinessObject businessObject)
	{
		if (null == homeSettSubjs || homeSettSubjs.size() < 1)
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoText());
		Iterator<HomeSettSubj> it = homeSettSubjs.iterator();
		while (it.hasNext())
		{
			HomeSettSubj homeSettSubj = (HomeSettSubj) it.next();
			_delete(homeSettSubj);
		}
	}
	
	/**
	 * 根据多个主键(由，分割)删除多个对象
	 * 
	 * @param homeSettSubjIds
	 */
	public void _deletes(String homeSettSubjIds,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(homeSettSubjIds))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("settlesubjectid"));
		String[] ids = StringUtils.splitString(homeSettSubjIds);
		for (int i = 0; i < ids.length; i++)
		{
			_delete(ids[i],businessObject);
		}
	}
	/**
	 * 提交工作流
	 * 
	 * @param homeSettSubj
	 */
	public void _submitProcess(HomeSettSubj homeSettSubj
	,BusinessObject businessObject)
	{
		String id = homeSettSubj.getSettlesubjectid();
        /**
		if (StringUtils.isNullBlank(id))
		{
			_save(homeSettSubj);
		}
		else
		{
			_update(homeSettSubj
			, businessObject);
		}**/
		
		String taskId = homeSettSubj.getWorkflowTaskId();
		id = homeSettSubj.getSettlesubjectid();
		if (null == taskId || "".equals(taskId))
			WorkflowService.createAndSignalProcessInstance(homeSettSubj, id);
		else
			WorkflowService.signalProcessInstance(homeSettSubj, id, null);
	}

	/**
	 * 保存或更新内贸付款结算科目
	 * 保存  
	 *  
	 * @param homeSettSubj
	 */
	public void _update(HomeSettSubj homeSettSubj
,BusinessObject businessObject	
	)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(homeSettSubj);
		homeSettSubjHibernateDao.saveOrUpdate(homeSettSubj);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(homeSettSubj);
	}
	
	/**
	 * 保存  
	 *   
	 * @param homeSettSubj
	 */
	public void _save(HomeSettSubj homeSettSubj)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(homeSettSubj);
		// 对id赋空值，使之执行insert操作(适应复制创建场景)
		homeSettSubj.setSettlesubjectid(null);
                                                                                      		homeSettSubjHibernateDao.saveOrUpdate(homeSettSubj);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(homeSettSubj);
	}
	
	/**
	 *
	 * 保存  
	 *   
	 * @param homeSettSubj
	 */
	public void _saveOrUpdate(HomeSettSubj homeSettSubj
,BusinessObject businessObject		
	)
	{
		if (StringUtils.isNullBlank(homeSettSubj.getSettlesubjectid()))
		{	
			_save(homeSettSubj);
		}
		else
		{
			_update(homeSettSubj
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