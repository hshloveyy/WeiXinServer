/*
 * @(#)CollectItemServiceGen.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年08月10日 02点20分24秒
 *  描　述：创建
 */
package com.infolion.xdss3.collectitemGen.service;

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
import com.infolion.xdss3.collectitem.domain.CollectItem;
import com.infolion.xdss3.collectitem.service.CollectItemService;
import com.infolion.xdss3.collectitem.dao.CollectItemHibernateDao;

/**
 * <pre>
 * 收款金额分配(CollectItem)服务类
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
public class CollectItemServiceGen extends BaseService
{
	protected Log log = LogFactory.getFactory().getLog(this.getClass());

	@Autowired
	protected CollectItemHibernateDao collectItemHibernateDao;
	
	public void setCollectItemHibernateDao(CollectItemHibernateDao collectItemHibernateDao)
	{
		this.collectItemHibernateDao = collectItemHibernateDao;
	}
	
     /**
	 * 注入扩展服务类
	 */
	 @Autowired(required = false)
	 public void setAdvanceService(@Qualifier("collectItemAdvanceService") AdvanceService advanceService)
	 {
	  	super.setAdvanceService(advanceService);
	 }   
	 
	   
	/**
	 * 根据主键ID,取得收款金额分配实例
	 * @param id
	 * @return
	 */
	public CollectItem _getDetached(String id)
	{		
	    CollectItem collectItem = new CollectItem();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  collectItem = collectItemHibernateDao.getDetached(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(collectItem);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    collectItem.setOperationType(operationType);
	    
		return collectItem;	
	}
	
	/**
	 * 根据主键ID,取得收款金额分配实例
	 * @param id
	 * @return
	 */
	public CollectItem _get(String id)
	{		
	    CollectItem collectItem = new CollectItem();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  collectItem = collectItemHibernateDao.get(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(collectItem);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    collectItem.setOperationType(operationType);
	    
		return collectItem;	
	}
	
	/**
	 * 根据主键ID,取得收款金额分配实例
	 * @param id
	 * @return
	 */
	public CollectItem _getForEdit(String id)
	{		
	    CollectItem collectItem = _get(id);
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = collectItem.getOperationType();
	    if(OperationType.READONLY.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotEdit);
	    }

		return collectItem;	
	}
	
	/**
	 * 根据主键ID,取得收款金额分配实例副本
	 * @param id
	 * @return
	 */
	public CollectItem _getEntityCopy(String id)
	{		
	    CollectItem collectItem = new CollectItem();
		CollectItem collectItemOld = this._get(id);
		try
		{
			BeanUtils.copyProperties(collectItem, collectItemOld);
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (InvocationTargetException e)
		{
			e.printStackTrace();
		}
		//collectItem.setCollectitemid(null); 
		return collectItem;	
	}
	
	/**
	 * 删除  
	 *   
	 * @param collectItem
	 */
	public void _delete(CollectItem collectItem)
	{
		if (null != advanceService)
			advanceService.preDelete(collectItem);
	
 		LockService.isBoInstanceLocked(collectItem,CollectItem.class);
		collectItemHibernateDao.remove(collectItem);

		if (null != advanceService)
			advanceService.postDelete(collectItem);
	}

	/**
	 * 根据主键删除
	 * 
	 * @param collectItemId
	 */
	public void _delete(String collectItemId,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(collectItemId))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("collectitemid"));
		CollectItem collectItem = this.collectItemHibernateDao.load(collectItemId);
		_delete(collectItem);
	}
	
	/**
	 * 删除  
	 *   
	 * @param Set<CollectItem> collectItems
	 */
	public void _deletes(Set<CollectItem> collectItems,BusinessObject businessObject)
	{
		if (null == collectItems || collectItems.size() < 1)
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoText());
		Iterator<CollectItem> it = collectItems.iterator();
		while (it.hasNext())
		{
			CollectItem collectItem = (CollectItem) it.next();
			_delete(collectItem);
		}
	}
	
	/**
	 * 根据多个主键(由，分割)删除多个对象
	 * 
	 * @param collectItemIds
	 */
	public void _deletes(String collectItemIds,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(collectItemIds))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("collectitemid"));
		String[] ids = StringUtils.splitString(collectItemIds);
		for (int i = 0; i < ids.length; i++)
		{
			_delete(ids[i],businessObject);
		}
	}
	/**
	 * 提交工作流
	 * 
	 * @param collectItem
	 */
	public void _submitProcess(CollectItem collectItem
	,BusinessObject businessObject)
	{
		String id = collectItem.getCollectitemid();
        /**
		if (StringUtils.isNullBlank(id))
		{
			_save(collectItem);
		}
		else
		{
			_update(collectItem
			, businessObject);
		}**/
		
		String taskId = collectItem.getWorkflowTaskId();
		id = collectItem.getCollectitemid();
		if (null == taskId || "".equals(taskId))
			WorkflowService.createAndSignalProcessInstance(collectItem, id);
		else
			WorkflowService.signalProcessInstance(collectItem, id, null);
	}

	/**
	 * 保存或更新收款金额分配
	 * 保存  
	 *  
	 * @param collectItem
	 */
	public void _update(CollectItem collectItem
,BusinessObject businessObject	
	)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(collectItem);
		collectItemHibernateDao.saveOrUpdate(collectItem);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(collectItem);
		collectItemHibernateDao.flush();
	}
	
	/**
	 * 保存  
	 *   
	 * @param collectItem
	 */
    public void _save(CollectItem collectItem){
        // 用户二次开发服务，保存前执行
        if(null != advanceService)
            advanceService.preSaveOrUpdate(collectItem);
        // 对id赋空值，使之执行insert操作(适应复制创建场景)
        collectItem.setCollectitemid(null);
        collectItemHibernateDao.saveOrUpdate(collectItem);
        // 用户二次开发服务，保存后执行
        if(null != advanceService)
            advanceService.postSaveOrUpdate(collectItem);
        collectItemHibernateDao.flush();
    }
	
	/**
	 *
	 * 保存  
	 *   
	 * @param collectItem
	 */
	public void _saveOrUpdate(CollectItem collectItem
,BusinessObject businessObject		
	)
	{
		if (StringUtils.isNullBlank(collectItem.getCollectitemid()))
		{	
			_save(collectItem);
		}
		else
		{
			_update(collectItem
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