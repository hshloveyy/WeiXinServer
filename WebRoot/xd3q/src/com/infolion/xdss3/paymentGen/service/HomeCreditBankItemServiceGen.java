/*
 * @(#)HomeCreditBankItemServiceGen.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2013年11月19日 11点36分30秒
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
import com.infolion.xdss3.payment.domain.HomeCreditBankItem;
import com.infolion.xdss3.payment.service.HomeCreditBankItemService;
import com.infolion.xdss3.payment.dao.HomeCreditBankItemHibernateDao;

/**
 * <pre>
 * 付款银行(HomeCreditBankItem)服务类
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
public class HomeCreditBankItemServiceGen extends BaseService
{
	protected Log log = LogFactory.getFactory().getLog(this.getClass());

	@Autowired
	protected HomeCreditBankItemHibernateDao homeCreditBankItemHibernateDao;
	
	public void setHomeCreditBankItemHibernateDao(HomeCreditBankItemHibernateDao homeCreditBankItemHibernateDao)
	{
		this.homeCreditBankItemHibernateDao = homeCreditBankItemHibernateDao;
	}
	
     /**
	 * 注入扩展服务类
	 */
	 @Autowired(required = false)
	 public void setAdvanceService(@Qualifier("homeCreditBankItemAdvanceService") AdvanceService advanceService)
	 {
	  	super.setAdvanceService(advanceService);
	 }   
	 
	   
	/**
	 * 根据主键ID,取得付款银行实例
	 * @param id
	 * @return
	 */
	public HomeCreditBankItem _getDetached(String id)
	{		
	    HomeCreditBankItem homeCreditBankItem = new HomeCreditBankItem();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  homeCreditBankItem = homeCreditBankItemHibernateDao.getDetached(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(homeCreditBankItem);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    homeCreditBankItem.setOperationType(operationType);
	    
		return homeCreditBankItem;	
	}
	
	/**
	 * 根据主键ID,取得付款银行实例
	 * @param id
	 * @return
	 */
	public HomeCreditBankItem _get(String id)
	{		
	    HomeCreditBankItem homeCreditBankItem = new HomeCreditBankItem();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  homeCreditBankItem = homeCreditBankItemHibernateDao.get(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(homeCreditBankItem);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    homeCreditBankItem.setOperationType(operationType);
	    
		return homeCreditBankItem;	
	}
	
	/**
	 * 根据主键ID,取得付款银行实例
	 * @param id
	 * @return
	 */
	public HomeCreditBankItem _getForEdit(String id)
	{		
	    HomeCreditBankItem homeCreditBankItem = _get(id);
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = homeCreditBankItem.getOperationType();
	    if(OperationType.READONLY.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotEdit);
	    }

		return homeCreditBankItem;	
	}
	
	/**
	 * 根据主键ID,取得付款银行实例副本
	 * @param id
	 * @return
	 */
	public HomeCreditBankItem _getEntityCopy(String id)
	{		
	    HomeCreditBankItem homeCreditBankItem = new HomeCreditBankItem();
		HomeCreditBankItem homeCreditBankItemOld = this._get(id);
		try
		{
			BeanUtils.copyProperties(homeCreditBankItem, homeCreditBankItemOld);
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (InvocationTargetException e)
		{
			e.printStackTrace();
		}
		//homeCreditBankItem.setPaybankitemid(null); 
		return homeCreditBankItem;	
	}
	
	/**
	 * 删除  
	 *   
	 * @param homeCreditBankItem
	 */
	public void _delete(HomeCreditBankItem homeCreditBankItem)
	{
		if (null != advanceService)
			advanceService.preDelete(homeCreditBankItem);
	
 		LockService.isBoInstanceLocked(homeCreditBankItem,HomeCreditBankItem.class);
		homeCreditBankItemHibernateDao.remove(homeCreditBankItem);

		if (null != advanceService)
			advanceService.postDelete(homeCreditBankItem);
	}

	/**
	 * 根据主键删除
	 * 
	 * @param homeCreditBankItemId
	 */
	public void _delete(String homeCreditBankItemId,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(homeCreditBankItemId))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("paybankitemid"));
		HomeCreditBankItem homeCreditBankItem = this.homeCreditBankItemHibernateDao.load(homeCreditBankItemId);
		_delete(homeCreditBankItem);
	}
	
	/**
	 * 删除  
	 *   
	 * @param Set<HomeCreditBankItem> homeCreditBankItems
	 */
	public void _deletes(Set<HomeCreditBankItem> homeCreditBankItems,BusinessObject businessObject)
	{
		if (null == homeCreditBankItems || homeCreditBankItems.size() < 1)
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoText());
		Iterator<HomeCreditBankItem> it = homeCreditBankItems.iterator();
		while (it.hasNext())
		{
			HomeCreditBankItem homeCreditBankItem = (HomeCreditBankItem) it.next();
			_delete(homeCreditBankItem);
		}
	}
	
	/**
	 * 根据多个主键(由，分割)删除多个对象
	 * 
	 * @param homeCreditBankItemIds
	 */
	public void _deletes(String homeCreditBankItemIds,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(homeCreditBankItemIds))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("paybankitemid"));
		String[] ids = StringUtils.splitString(homeCreditBankItemIds);
		for (int i = 0; i < ids.length; i++)
		{
			_delete(ids[i],businessObject);
		}
	}
	/**
	 * 提交工作流
	 * 
	 * @param homeCreditBankItem
	 */
	public void _submitProcess(HomeCreditBankItem homeCreditBankItem
	,BusinessObject businessObject)
	{
		String id = homeCreditBankItem.getPaybankitemid();
        /**
		if (StringUtils.isNullBlank(id))
		{
			_save(homeCreditBankItem);
		}
		else
		{
			_update(homeCreditBankItem
			, businessObject);
		}**/
		
		String taskId = homeCreditBankItem.getWorkflowTaskId();
		id = homeCreditBankItem.getPaybankitemid();
		if (null == taskId || "".equals(taskId))
			WorkflowService.createAndSignalProcessInstance(homeCreditBankItem, id);
		else
			WorkflowService.signalProcessInstance(homeCreditBankItem, id, null);
	}

	/**
	 * 保存或更新付款银行
	 * 保存  
	 *  
	 * @param homeCreditBankItem
	 */
	public void _update(HomeCreditBankItem homeCreditBankItem
,BusinessObject businessObject	
	)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(homeCreditBankItem);
		homeCreditBankItemHibernateDao.saveOrUpdate(homeCreditBankItem);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(homeCreditBankItem);
	}
	
	/**
	 * 保存  
	 *   
	 * @param homeCreditBankItem
	 */
	public void _save(HomeCreditBankItem homeCreditBankItem)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(homeCreditBankItem);
		// 对id赋空值，使之执行insert操作(适应复制创建场景)
		homeCreditBankItem.setPaybankitemid(null);
                    		homeCreditBankItemHibernateDao.saveOrUpdate(homeCreditBankItem);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(homeCreditBankItem);
	}
	
	/**
	 *
	 * 保存  
	 *   
	 * @param homeCreditBankItem
	 */
	public void _saveOrUpdate(HomeCreditBankItem homeCreditBankItem
,BusinessObject businessObject		
	)
	{
		if (StringUtils.isNullBlank(homeCreditBankItem.getPaybankitemid()))
		{	
			_save(homeCreditBankItem);
		}
		else
		{
			_update(homeCreditBankItem
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