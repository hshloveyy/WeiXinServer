/*
 * @(#)OporDocItemServiceGen.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年03月25日 19点56分39秒
 *  描　述：创建
 */
package com.infolion.sample.b1.oporGen.service;

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
import com.infolion.sample.b1.opor.domain.OporDocItem;
import com.infolion.sample.b1.opor.service.OporDocItemService;
import com.infolion.sample.b1.opor.dao.OporDocItemHibernateDao;

/**
 * <pre>
 * 采购订单明细(OporDocItem)服务类
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
public class OporDocItemServiceGen extends BaseService
{
	protected Log log = LogFactory.getFactory().getLog(this.getClass());

	@Autowired
	protected OporDocItemHibernateDao oporDocItemHibernateDao;
	
	public void setOporDocItemHibernateDao(OporDocItemHibernateDao oporDocItemHibernateDao)
	{
		this.oporDocItemHibernateDao = oporDocItemHibernateDao;
	}
	
     /**
	 * 注入扩展服务类
	 */
	 @Autowired(required = false)
	 public void setAdvanceService(@Qualifier("oporDocItemAdvanceService") AdvanceService advanceService)
	 {
	  	super.setAdvanceService(advanceService);
	 }   
	 
	   
	/**
	 * 根据主键ID,取得采购订单明细实例
	 * @param id
	 * @return
	 */
	public OporDocItem _getDetached(String id)
	{		
	    OporDocItem oporDocItem = new OporDocItem();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  oporDocItem = oporDocItemHibernateDao.getDetached(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(oporDocItem);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    oporDocItem.setOperationType(operationType);
	    
		return oporDocItem;	
	}
	
	/**
	 * 根据主键ID,取得采购订单明细实例
	 * @param id
	 * @return
	 */
	public OporDocItem _get(String id)
	{		
	    OporDocItem oporDocItem = new OporDocItem();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  oporDocItem = oporDocItemHibernateDao.get(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(oporDocItem);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    oporDocItem.setOperationType(operationType);
	    
		return oporDocItem;	
	}
	
	/**
	 * 根据主键ID,取得采购订单明细实例
	 * @param id
	 * @return
	 */
	public OporDocItem _getForEdit(String id)
	{		
	    OporDocItem oporDocItem = _get(id);
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = oporDocItem.getOperationType();
	    if(OperationType.READONLY.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotEdit);
	    }

		return oporDocItem;	
	}
	
	/**
	 * 根据主键ID,取得采购订单明细实例副本
	 * @param id
	 * @return
	 */
	public OporDocItem _getEntityCopy(String id)
	{		
	    OporDocItem oporDocItem = new OporDocItem();
		OporDocItem oporDocItemOld = this._get(id);
		try
		{
			BeanUtils.copyProperties(oporDocItem, oporDocItemOld);
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (InvocationTargetException e)
		{
			e.printStackTrace();
		}
		//oporDocItem.setOporDocItemId(null); 
		return oporDocItem;	
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
	 * @param oporDocItem
	 */
	public void _delete(OporDocItem oporDocItem)
	{
		if (null != advanceService)
			advanceService.preDelete(oporDocItem);
	
 		LockService.isBoInstanceLocked(oporDocItem,OporDocItem.class);
		oporDocItemHibernateDao.remove(oporDocItem);

		if (null != advanceService)
			advanceService.postDelete(oporDocItem);
	}

	/**
	 * 根据主键删除
	 * 
	 * @param oporDocItemId
	 */
	public void _delete(String oporDocItemId,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(oporDocItemId))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("oporDocItemId"));
		OporDocItem oporDocItem = this.oporDocItemHibernateDao.load(oporDocItemId);
		_delete(oporDocItem);
	}
	
	/**
	 * 删除  
	 *   
	 * @param Set<OporDocItem> oporDocItems
	 */
	public void _deletes(Set<OporDocItem> oporDocItems,BusinessObject businessObject)
	{
		if (null == oporDocItems || oporDocItems.size() < 1)
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoText());
		Iterator<OporDocItem> it = oporDocItems.iterator();
		while (it.hasNext())
		{
			OporDocItem oporDocItem = (OporDocItem) it.next();
			_delete(oporDocItem);
		}
	}
	
	/**
	 * 根据多个主键(由，分割)删除多个对象
	 * 
	 * @param oporDocItemIds
	 */
	public void _deletes(String oporDocItemIds,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(oporDocItemIds))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("oporDocItemId"));
		String[] ids = StringUtils.splitString(oporDocItemIds);
		for (int i = 0; i < ids.length; i++)
		{
			_delete(ids[i],businessObject);
		}
	}
	/**
	 * 提交工作流
	 * 
	 * @param oporDocItem
	 */
	public void _submitProcess(OporDocItem oporDocItem
	,BusinessObject businessObject)
	{
		String id = oporDocItem.getOporDocItemId();
		if (StringUtils.isNullBlank(id))
		{
			_save(oporDocItem);
		}
		else
		{
			_update(oporDocItem
			, businessObject);
		}
		String taskId = oporDocItem.getWorkflowTaskId();
		if (null == taskId || "".equals(taskId))
			WorkflowService.createAndSignalProcessInstance(oporDocItem, id);
		else
			WorkflowService.signalProcessInstance(oporDocItem, id, null);
	}

	/**
	 * 保存或更新采购订单明细
	 * 保存  
	 *  
	 * @param oporDocItem
	 */
	public void _update(OporDocItem oporDocItem
,BusinessObject businessObject	
	)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(oporDocItem);
		oporDocItemHibernateDao.saveOrUpdate(oporDocItem);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(oporDocItem);
	}
	
	/**
	 * 保存  
	 *   
	 * @param oporDocItem
	 */
	public void _save(OporDocItem oporDocItem)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(oporDocItem);
		// 对id赋空值，使之执行insert操作(适应复制创建场景)
		oporDocItem.setOporDocItemId(null);
                    		oporDocItemHibernateDao.saveOrUpdate(oporDocItem);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(oporDocItem);
	}
	
	/**
	 *
	 * 保存  
	 *   
	 * @param oporDocItem
	 */
	public void _saveOrUpdate(OporDocItem oporDocItem
,BusinessObject businessObject		
	)
	{
		if (StringUtils.isNullBlank(oporDocItem.getOporDocItemId()))
		{	
			_save(oporDocItem);
		}
		else
		{
			_update(oporDocItem
, businessObject);
}	}
	
}