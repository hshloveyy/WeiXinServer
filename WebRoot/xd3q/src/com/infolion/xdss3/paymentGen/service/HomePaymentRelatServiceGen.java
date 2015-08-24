/*
 * @(#)HomePaymentRelatServiceGen.java
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
import com.infolion.xdss3.payment.domain.HomePaymentRelat;
import com.infolion.xdss3.payment.service.HomePaymentRelatService;
import com.infolion.xdss3.payment.dao.HomePaymentRelatHibernateDao;

/**
 * <pre>
 * 付款相关单据(HomePaymentRelat)服务类
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
public class HomePaymentRelatServiceGen extends BaseService
{
	protected Log log = LogFactory.getFactory().getLog(this.getClass());

	@Autowired
	protected HomePaymentRelatHibernateDao homePaymentRelatHibernateDao;
	
	public void setHomePaymentRelatHibernateDao(HomePaymentRelatHibernateDao homePaymentRelatHibernateDao)
	{
		this.homePaymentRelatHibernateDao = homePaymentRelatHibernateDao;
	}
	
     /**
	 * 注入扩展服务类
	 */
	 @Autowired(required = false)
	 public void setAdvanceService(@Qualifier("homePaymentRelatAdvanceService") AdvanceService advanceService)
	 {
	  	super.setAdvanceService(advanceService);
	 }   
	 
	   
	/**
	 * 根据主键ID,取得付款相关单据实例
	 * @param id
	 * @return
	 */
	public HomePaymentRelat _getDetached(String id)
	{		
	    HomePaymentRelat homePaymentRelat = new HomePaymentRelat();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  homePaymentRelat = homePaymentRelatHibernateDao.getDetached(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(homePaymentRelat);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    homePaymentRelat.setOperationType(operationType);
	    
		return homePaymentRelat;	
	}
	
	/**
	 * 根据主键ID,取得付款相关单据实例
	 * @param id
	 * @return
	 */
	public HomePaymentRelat _get(String id)
	{		
	    HomePaymentRelat homePaymentRelat = new HomePaymentRelat();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  homePaymentRelat = homePaymentRelatHibernateDao.get(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(homePaymentRelat);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    homePaymentRelat.setOperationType(operationType);
	    
		return homePaymentRelat;	
	}
	
	/**
	 * 根据主键ID,取得付款相关单据实例
	 * @param id
	 * @return
	 */
	public HomePaymentRelat _getForEdit(String id)
	{		
	    HomePaymentRelat homePaymentRelat = _get(id);
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = homePaymentRelat.getOperationType();
	    if(OperationType.READONLY.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotEdit);
	    }

		return homePaymentRelat;	
	}
	
	/**
	 * 根据主键ID,取得付款相关单据实例副本
	 * @param id
	 * @return
	 */
	public HomePaymentRelat _getEntityCopy(String id)
	{		
	    HomePaymentRelat homePaymentRelat = new HomePaymentRelat();
		HomePaymentRelat homePaymentRelatOld = this._get(id);
		try
		{
			BeanUtils.copyProperties(homePaymentRelat, homePaymentRelatOld);
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (InvocationTargetException e)
		{
			e.printStackTrace();
		}
		//homePaymentRelat.setRelatedpaymentid(null); 
		return homePaymentRelat;	
	}
	
	/**
	 * 删除  
	 *   
	 * @param homePaymentRelat
	 */
	public void _delete(HomePaymentRelat homePaymentRelat)
	{
		if (null != advanceService)
			advanceService.preDelete(homePaymentRelat);
	
 		LockService.isBoInstanceLocked(homePaymentRelat,HomePaymentRelat.class);
		homePaymentRelatHibernateDao.remove(homePaymentRelat);

		if (null != advanceService)
			advanceService.postDelete(homePaymentRelat);
	}

	/**
	 * 根据主键删除
	 * 
	 * @param homePaymentRelatId
	 */
	public void _delete(String homePaymentRelatId,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(homePaymentRelatId))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("relatedpaymentid"));
		HomePaymentRelat homePaymentRelat = this.homePaymentRelatHibernateDao.load(homePaymentRelatId);
		_delete(homePaymentRelat);
	}
	
	/**
	 * 删除  
	 *   
	 * @param Set<HomePaymentRelat> homePaymentRelats
	 */
	public void _deletes(Set<HomePaymentRelat> homePaymentRelats,BusinessObject businessObject)
	{
		if (null == homePaymentRelats || homePaymentRelats.size() < 1)
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoText());
		Iterator<HomePaymentRelat> it = homePaymentRelats.iterator();
		while (it.hasNext())
		{
			HomePaymentRelat homePaymentRelat = (HomePaymentRelat) it.next();
			_delete(homePaymentRelat);
		}
	}
	
	/**
	 * 根据多个主键(由，分割)删除多个对象
	 * 
	 * @param homePaymentRelatIds
	 */
	public void _deletes(String homePaymentRelatIds,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(homePaymentRelatIds))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("relatedpaymentid"));
		String[] ids = StringUtils.splitString(homePaymentRelatIds);
		for (int i = 0; i < ids.length; i++)
		{
			_delete(ids[i],businessObject);
		}
	}
	/**
	 * 提交工作流
	 * 
	 * @param homePaymentRelat
	 */
	public void _submitProcess(HomePaymentRelat homePaymentRelat
	,BusinessObject businessObject)
	{
		String id = homePaymentRelat.getRelatedpaymentid();
        /**
		if (StringUtils.isNullBlank(id))
		{
			_save(homePaymentRelat);
		}
		else
		{
			_update(homePaymentRelat
			, businessObject);
		}**/
		
		String taskId = homePaymentRelat.getWorkflowTaskId();
		id = homePaymentRelat.getRelatedpaymentid();
		if (null == taskId || "".equals(taskId))
			WorkflowService.createAndSignalProcessInstance(homePaymentRelat, id);
		else
			WorkflowService.signalProcessInstance(homePaymentRelat, id, null);
	}

	/**
	 * 保存或更新付款相关单据
	 * 保存  
	 *  
	 * @param homePaymentRelat
	 */
	public void _update(HomePaymentRelat homePaymentRelat
,BusinessObject businessObject	
	)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(homePaymentRelat);
		homePaymentRelatHibernateDao.saveOrUpdate(homePaymentRelat);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(homePaymentRelat);
	}
	
	/**
	 * 保存  
	 *   
	 * @param homePaymentRelat
	 */
	public void _save(HomePaymentRelat homePaymentRelat)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(homePaymentRelat);
		// 对id赋空值，使之执行insert操作(适应复制创建场景)
		homePaymentRelat.setRelatedpaymentid(null);
                		homePaymentRelatHibernateDao.saveOrUpdate(homePaymentRelat);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(homePaymentRelat);
	}
	
	/**
	 *
	 * 保存  
	 *   
	 * @param homePaymentRelat
	 */
	public void _saveOrUpdate(HomePaymentRelat homePaymentRelat
,BusinessObject businessObject		
	)
	{
		if (StringUtils.isNullBlank(homePaymentRelat.getRelatedpaymentid()))
		{	
			_save(homePaymentRelat);
		}
		else
		{
			_update(homePaymentRelat
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