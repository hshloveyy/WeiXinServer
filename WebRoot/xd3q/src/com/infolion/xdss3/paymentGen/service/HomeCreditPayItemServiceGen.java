/*
 * @(#)HomeCreditPayItemServiceGen.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2013年11月19日 11点37分13秒
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
import com.infolion.xdss3.payment.domain.HomeCreditPayItem;
import com.infolion.xdss3.payment.service.HomeCreditPayItemService;
import com.infolion.xdss3.payment.dao.HomeCreditPayItemHibernateDao;

/**
 * <pre>
 * 国内证付款行项目(HomeCreditPayItem)服务类
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
public class HomeCreditPayItemServiceGen extends BaseService
{
	protected Log log = LogFactory.getFactory().getLog(this.getClass());

	@Autowired
	protected HomeCreditPayItemHibernateDao homeCreditPayItemHibernateDao;
	
	public void setHomeCreditPayItemHibernateDao(HomeCreditPayItemHibernateDao homeCreditPayItemHibernateDao)
	{
		this.homeCreditPayItemHibernateDao = homeCreditPayItemHibernateDao;
	}
	
     /**
	 * 注入扩展服务类
	 */
	 @Autowired(required = false)
	 public void setAdvanceService(@Qualifier("homeCreditPayItemAdvanceService") AdvanceService advanceService)
	 {
	  	super.setAdvanceService(advanceService);
	 }   
	 
	   
	/**
	 * 根据主键ID,取得国内证付款行项目实例
	 * @param id
	 * @return
	 */
	public HomeCreditPayItem _getDetached(String id)
	{		
	    HomeCreditPayItem homeCreditPayItem = new HomeCreditPayItem();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  homeCreditPayItem = homeCreditPayItemHibernateDao.getDetached(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(homeCreditPayItem);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    homeCreditPayItem.setOperationType(operationType);
	    
		return homeCreditPayItem;	
	}
	
	/**
	 * 根据主键ID,取得国内证付款行项目实例
	 * @param id
	 * @return
	 */
	public HomeCreditPayItem _get(String id)
	{		
	    HomeCreditPayItem homeCreditPayItem = new HomeCreditPayItem();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  homeCreditPayItem = homeCreditPayItemHibernateDao.get(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(homeCreditPayItem);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    homeCreditPayItem.setOperationType(operationType);
	    
		return homeCreditPayItem;	
	}
	
	/**
	 * 根据主键ID,取得国内证付款行项目实例
	 * @param id
	 * @return
	 */
	public HomeCreditPayItem _getForEdit(String id)
	{		
	    HomeCreditPayItem homeCreditPayItem = _get(id);
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = homeCreditPayItem.getOperationType();
	    if(OperationType.READONLY.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotEdit);
	    }

		return homeCreditPayItem;	
	}
	
	/**
	 * 根据主键ID,取得国内证付款行项目实例副本
	 * @param id
	 * @return
	 */
	public HomeCreditPayItem _getEntityCopy(String id)
	{		
	    HomeCreditPayItem homeCreditPayItem = new HomeCreditPayItem();
		HomeCreditPayItem homeCreditPayItemOld = this._get(id);
		try
		{
			BeanUtils.copyProperties(homeCreditPayItem, homeCreditPayItemOld);
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (InvocationTargetException e)
		{
			e.printStackTrace();
		}
		//homeCreditPayItem.setPaymentitemid(null); 
		return homeCreditPayItem;	
	}
	
	/**
	 * 删除  
	 *   
	 * @param homeCreditPayItem
	 */
	public void _delete(HomeCreditPayItem homeCreditPayItem)
	{
		if (null != advanceService)
			advanceService.preDelete(homeCreditPayItem);
	
 		LockService.isBoInstanceLocked(homeCreditPayItem,HomeCreditPayItem.class);
		homeCreditPayItemHibernateDao.remove(homeCreditPayItem);

		if (null != advanceService)
			advanceService.postDelete(homeCreditPayItem);
	}

	/**
	 * 根据主键删除
	 * 
	 * @param homeCreditPayItemId
	 */
	public void _delete(String homeCreditPayItemId,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(homeCreditPayItemId))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("paymentitemid"));
		HomeCreditPayItem homeCreditPayItem = this.homeCreditPayItemHibernateDao.load(homeCreditPayItemId);
		_delete(homeCreditPayItem);
	}
	
	/**
	 * 删除  
	 *   
	 * @param Set<HomeCreditPayItem> homeCreditPayItems
	 */
	public void _deletes(Set<HomeCreditPayItem> homeCreditPayItems,BusinessObject businessObject)
	{
		if (null == homeCreditPayItems || homeCreditPayItems.size() < 1)
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoText());
		Iterator<HomeCreditPayItem> it = homeCreditPayItems.iterator();
		while (it.hasNext())
		{
			HomeCreditPayItem homeCreditPayItem = (HomeCreditPayItem) it.next();
			_delete(homeCreditPayItem);
		}
	}
	
	/**
	 * 根据多个主键(由，分割)删除多个对象
	 * 
	 * @param homeCreditPayItemIds
	 */
	public void _deletes(String homeCreditPayItemIds,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(homeCreditPayItemIds))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("paymentitemid"));
		String[] ids = StringUtils.splitString(homeCreditPayItemIds);
		for (int i = 0; i < ids.length; i++)
		{
			_delete(ids[i],businessObject);
		}
	}
	/**
	 * 提交工作流
	 * 
	 * @param homeCreditPayItem
	 */
	public void _submitProcess(HomeCreditPayItem homeCreditPayItem
	,BusinessObject businessObject)
	{
		String id = homeCreditPayItem.getPaymentitemid();
        /**
		if (StringUtils.isNullBlank(id))
		{
			_save(homeCreditPayItem);
		}
		else
		{
			_update(homeCreditPayItem
			, businessObject);
		}**/
		
		String taskId = homeCreditPayItem.getWorkflowTaskId();
		id = homeCreditPayItem.getPaymentitemid();
		if (null == taskId || "".equals(taskId))
			WorkflowService.createAndSignalProcessInstance(homeCreditPayItem, id);
		else
			WorkflowService.signalProcessInstance(homeCreditPayItem, id, null);
	}

	/**
	 * 保存或更新国内证付款行项目
	 * 保存  
	 *  
	 * @param homeCreditPayItem
	 */
	public void _update(HomeCreditPayItem homeCreditPayItem
,BusinessObject businessObject	
	)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(homeCreditPayItem);
		homeCreditPayItemHibernateDao.saveOrUpdate(homeCreditPayItem);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(homeCreditPayItem);
	}
	
	/**
	 * 保存  
	 *   
	 * @param homeCreditPayItem
	 */
	public void _save(HomeCreditPayItem homeCreditPayItem)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(homeCreditPayItem);
		// 对id赋空值，使之执行insert操作(适应复制创建场景)
		homeCreditPayItem.setPaymentitemid(null);
                              		homeCreditPayItemHibernateDao.saveOrUpdate(homeCreditPayItem);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(homeCreditPayItem);
	}
	
	/**
	 *
	 * 保存  
	 *   
	 * @param homeCreditPayItem
	 */
	public void _saveOrUpdate(HomeCreditPayItem homeCreditPayItem
,BusinessObject businessObject		
	)
	{
		if (StringUtils.isNullBlank(homeCreditPayItem.getPaymentitemid()))
		{	
			_save(homeCreditPayItem);
		}
		else
		{
			_update(homeCreditPayItem
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