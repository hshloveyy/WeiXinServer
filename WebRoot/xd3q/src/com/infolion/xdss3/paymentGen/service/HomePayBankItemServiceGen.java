/*
 * @(#)HomePayBankItemServiceGen.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年07月25日 09点58分41秒
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
import com.infolion.xdss3.payment.domain.HomePayBankItem;
import com.infolion.xdss3.payment.service.HomePayBankItemService;
import com.infolion.xdss3.payment.dao.HomePayBankItemHibernateDao;

/**
 * <pre>
 * 内贸付款银行(HomePayBankItem)服务类
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
public class HomePayBankItemServiceGen extends BaseService
{
	protected Log log = LogFactory.getFactory().getLog(this.getClass());

	@Autowired
	protected HomePayBankItemHibernateDao homePayBankItemHibernateDao;
	
	public void setHomePayBankItemHibernateDao(HomePayBankItemHibernateDao homePayBankItemHibernateDao)
	{
		this.homePayBankItemHibernateDao = homePayBankItemHibernateDao;
	}
	
     /**
	 * 注入扩展服务类
	 */
	 @Autowired(required = false)
	 public void setAdvanceService(@Qualifier("homePayBankItemAdvanceService") AdvanceService advanceService)
	 {
	  	super.setAdvanceService(advanceService);
	 }   
	 
	   
	/**
	 * 根据主键ID,取得内贸付款银行实例
	 * @param id
	 * @return
	 */
	public HomePayBankItem _getDetached(String id)
	{		
	    HomePayBankItem homePayBankItem = new HomePayBankItem();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  homePayBankItem = homePayBankItemHibernateDao.getDetached(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(homePayBankItem);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    homePayBankItem.setOperationType(operationType);
	    
		return homePayBankItem;	
	}
	
	/**
	 * 根据主键ID,取得内贸付款银行实例
	 * @param id
	 * @return
	 */
	public HomePayBankItem _get(String id)
	{		
	    HomePayBankItem homePayBankItem = new HomePayBankItem();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  homePayBankItem = homePayBankItemHibernateDao.get(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(homePayBankItem);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    homePayBankItem.setOperationType(operationType);
	    
		return homePayBankItem;	
	}
	
	/**
	 * 根据主键ID,取得内贸付款银行实例
	 * @param id
	 * @return
	 */
	public HomePayBankItem _getForEdit(String id)
	{		
	    HomePayBankItem homePayBankItem = _get(id);
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = homePayBankItem.getOperationType();
	    if(OperationType.READONLY.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotEdit);
	    }

		return homePayBankItem;	
	}
	
	/**
	 * 根据主键ID,取得内贸付款银行实例副本
	 * @param id
	 * @return
	 */
	public HomePayBankItem _getEntityCopy(String id)
	{		
	    HomePayBankItem homePayBankItem = new HomePayBankItem();
		HomePayBankItem homePayBankItemOld = this._get(id);
		try
		{
			BeanUtils.copyProperties(homePayBankItem, homePayBankItemOld);
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (InvocationTargetException e)
		{
			e.printStackTrace();
		}
		//homePayBankItem.setPaybankitemid(null); 
		return homePayBankItem;	
	}
	
	/**
	 * 删除  
	 *   
	 * @param homePayBankItem
	 */
	public void _delete(HomePayBankItem homePayBankItem)
	{
		if (null != advanceService)
			advanceService.preDelete(homePayBankItem);
	
 		LockService.isBoInstanceLocked(homePayBankItem,HomePayBankItem.class);
		homePayBankItemHibernateDao.remove(homePayBankItem);

		if (null != advanceService)
			advanceService.postDelete(homePayBankItem);
	}

	/**
	 * 根据主键删除
	 * 
	 * @param homePayBankItemId
	 */
	public void _delete(String homePayBankItemId,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(homePayBankItemId))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("paybankitemid"));
		HomePayBankItem homePayBankItem = this.homePayBankItemHibernateDao.load(homePayBankItemId);
		_delete(homePayBankItem);
	}
	
	/**
	 * 删除  
	 *   
	 * @param Set<HomePayBankItem> homePayBankItems
	 */
	public void _deletes(Set<HomePayBankItem> homePayBankItems,BusinessObject businessObject)
	{
		if (null == homePayBankItems || homePayBankItems.size() < 1)
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoText());
		Iterator<HomePayBankItem> it = homePayBankItems.iterator();
		while (it.hasNext())
		{
			HomePayBankItem homePayBankItem = (HomePayBankItem) it.next();
			_delete(homePayBankItem);
		}
	}
	
	/**
	 * 根据多个主键(由，分割)删除多个对象
	 * 
	 * @param homePayBankItemIds
	 */
	public void _deletes(String homePayBankItemIds,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(homePayBankItemIds))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("paybankitemid"));
		String[] ids = StringUtils.splitString(homePayBankItemIds);
		for (int i = 0; i < ids.length; i++)
		{
			_delete(ids[i],businessObject);
		}
	}
	/**
	 * 提交工作流
	 * 
	 * @param homePayBankItem
	 */
	public void _submitProcess(HomePayBankItem homePayBankItem
	,BusinessObject businessObject)
	{
		String id = homePayBankItem.getPaybankitemid();
        /**
		if (StringUtils.isNullBlank(id))
		{
			_save(homePayBankItem);
		}
		else
		{
			_update(homePayBankItem
			, businessObject);
		}**/
		
		String taskId = homePayBankItem.getWorkflowTaskId();
		id = homePayBankItem.getPaybankitemid();
		if (null == taskId || "".equals(taskId))
			WorkflowService.createAndSignalProcessInstance(homePayBankItem, id);
		else
			WorkflowService.signalProcessInstance(homePayBankItem, id, null);
	}

	/**
	 * 保存或更新内贸付款银行
	 * 保存  
	 *  
	 * @param homePayBankItem
	 */
	public void _update(HomePayBankItem homePayBankItem
,BusinessObject businessObject	
	)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(homePayBankItem);
		homePayBankItemHibernateDao.saveOrUpdate(homePayBankItem);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(homePayBankItem);
	}
	
	/**
	 * 保存  
	 *   
	 * @param homePayBankItem
	 */
	public void _save(HomePayBankItem homePayBankItem)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(homePayBankItem);
		// 对id赋空值，使之执行insert操作(适应复制创建场景)
		homePayBankItem.setPaybankitemid(null);
                    		homePayBankItemHibernateDao.saveOrUpdate(homePayBankItem);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(homePayBankItem);
	}
	
	/**
	 *
	 * 保存  
	 *   
	 * @param homePayBankItem
	 */
	public void _saveOrUpdate(HomePayBankItem homePayBankItem
,BusinessObject businessObject		
	)
	{
		if (StringUtils.isNullBlank(homePayBankItem.getPaybankitemid()))
		{	
			_save(homePayBankItem);
		}
		else
		{
			_update(homePayBankItem
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