/*
 * @(#)HomePaymentItemServiceGen.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年07月25日 09点58分40秒
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
import com.infolion.xdss3.payment.domain.HomePaymentItem;
import com.infolion.xdss3.payment.service.HomePaymentItemService;
import com.infolion.xdss3.payment.dao.HomePaymentItemHibernateDao;

/**
 * <pre>
 * 内贸付款分配(HomePaymentItem)服务类
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
public class HomePaymentItemServiceGen extends BaseService
{
	protected Log log = LogFactory.getFactory().getLog(this.getClass());

	@Autowired
	protected HomePaymentItemHibernateDao homePaymentItemHibernateDao;
	
	public void setHomePaymentItemHibernateDao(HomePaymentItemHibernateDao homePaymentItemHibernateDao)
	{
		this.homePaymentItemHibernateDao = homePaymentItemHibernateDao;
	}
	
     /**
	 * 注入扩展服务类
	 */
	 @Autowired(required = false)
	 public void setAdvanceService(@Qualifier("homePaymentItemAdvanceService") AdvanceService advanceService)
	 {
	  	super.setAdvanceService(advanceService);
	 }   
	 
	   
	/**
	 * 根据主键ID,取得内贸付款分配实例
	 * @param id
	 * @return
	 */
	public HomePaymentItem _getDetached(String id)
	{		
	    HomePaymentItem homePaymentItem = new HomePaymentItem();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  homePaymentItem = homePaymentItemHibernateDao.getDetached(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(homePaymentItem);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    homePaymentItem.setOperationType(operationType);
	    
		return homePaymentItem;	
	}
	
	/**
	 * 根据主键ID,取得内贸付款分配实例
	 * @param id
	 * @return
	 */
	public HomePaymentItem _get(String id)
	{		
	    HomePaymentItem homePaymentItem = new HomePaymentItem();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  homePaymentItem = homePaymentItemHibernateDao.get(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(homePaymentItem);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    homePaymentItem.setOperationType(operationType);
	    
		return homePaymentItem;	
	}
	
	/**
	 * 根据主键ID,取得内贸付款分配实例
	 * @param id
	 * @return
	 */
	public HomePaymentItem _getForEdit(String id)
	{		
	    HomePaymentItem homePaymentItem = _get(id);
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = homePaymentItem.getOperationType();
	    if(OperationType.READONLY.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotEdit);
	    }

		return homePaymentItem;	
	}
	
	/**
	 * 根据主键ID,取得内贸付款分配实例副本
	 * @param id
	 * @return
	 */
	public HomePaymentItem _getEntityCopy(String id)
	{		
	    HomePaymentItem homePaymentItem = new HomePaymentItem();
		HomePaymentItem homePaymentItemOld = this._get(id);
		try
		{
			BeanUtils.copyProperties(homePaymentItem, homePaymentItemOld);
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (InvocationTargetException e)
		{
			e.printStackTrace();
		}
		//homePaymentItem.setPaymentitemid(null); 
		return homePaymentItem;	
	}
	
	/**
	 * 删除  
	 *   
	 * @param homePaymentItem
	 */
	public void _delete(HomePaymentItem homePaymentItem)
	{
		if (null != advanceService)
			advanceService.preDelete(homePaymentItem);
	
 		LockService.isBoInstanceLocked(homePaymentItem,HomePaymentItem.class);
		homePaymentItemHibernateDao.remove(homePaymentItem);

		if (null != advanceService)
			advanceService.postDelete(homePaymentItem);
	}

	/**
	 * 根据主键删除
	 * 
	 * @param homePaymentItemId
	 */
	public void _delete(String homePaymentItemId,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(homePaymentItemId))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("paymentitemid"));
		HomePaymentItem homePaymentItem = this.homePaymentItemHibernateDao.load(homePaymentItemId);
		_delete(homePaymentItem);
	}
	
	/**
	 * 删除  
	 *   
	 * @param Set<HomePaymentItem> homePaymentItems
	 */
	public void _deletes(Set<HomePaymentItem> homePaymentItems,BusinessObject businessObject)
	{
		if (null == homePaymentItems || homePaymentItems.size() < 1)
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoText());
		Iterator<HomePaymentItem> it = homePaymentItems.iterator();
		while (it.hasNext())
		{
			HomePaymentItem homePaymentItem = (HomePaymentItem) it.next();
			_delete(homePaymentItem);
		}
	}
	
	/**
	 * 根据多个主键(由，分割)删除多个对象
	 * 
	 * @param homePaymentItemIds
	 */
	public void _deletes(String homePaymentItemIds,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(homePaymentItemIds))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("paymentitemid"));
		String[] ids = StringUtils.splitString(homePaymentItemIds);
		for (int i = 0; i < ids.length; i++)
		{
			_delete(ids[i],businessObject);
		}
	}
	/**
	 * 提交工作流
	 * 
	 * @param homePaymentItem
	 */
	public void _submitProcess(HomePaymentItem homePaymentItem
	,BusinessObject businessObject)
	{
		String id = homePaymentItem.getPaymentitemid();
        /**
		if (StringUtils.isNullBlank(id))
		{
			_save(homePaymentItem);
		}
		else
		{
			_update(homePaymentItem
			, businessObject);
		}**/
		
		String taskId = homePaymentItem.getWorkflowTaskId();
		id = homePaymentItem.getPaymentitemid();
		if (null == taskId || "".equals(taskId))
			WorkflowService.createAndSignalProcessInstance(homePaymentItem, id);
		else
			WorkflowService.signalProcessInstance(homePaymentItem, id, null);
	}

	/**
	 * 保存或更新内贸付款分配
	 * 保存  
	 *  
	 * @param homePaymentItem
	 */
	public void _update(HomePaymentItem homePaymentItem
,BusinessObject businessObject	
	)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(homePaymentItem);
		homePaymentItemHibernateDao.saveOrUpdate(homePaymentItem);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(homePaymentItem);
	}
	
	/**
	 * 保存  
	 *   
	 * @param homePaymentItem
	 */
	public void _save(HomePaymentItem homePaymentItem)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(homePaymentItem);
		// 对id赋空值，使之执行insert操作(适应复制创建场景)
		homePaymentItem.setPaymentitemid(null);
                            		homePaymentItemHibernateDao.saveOrUpdate(homePaymentItem);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(homePaymentItem);
	}
	
	/**
	 *
	 * 保存  
	 *   
	 * @param homePaymentItem
	 */
	public void _saveOrUpdate(HomePaymentItem homePaymentItem
,BusinessObject businessObject		
	)
	{
		if (StringUtils.isNullBlank(homePaymentItem.getPaymentitemid()))
		{	
			_save(homePaymentItem);
		}
		else
		{
			_update(homePaymentItem
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