/*
 * @(#)CollectBankItemServiceGen.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年08月10日 02点20分34秒
 *  描　述：创建
 */
package com.infolion.xdss3.collectbankitemGen.service;

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
import com.infolion.xdss3.collectbankitem.domain.CollectBankItem;
import com.infolion.xdss3.collectbankitem.service.CollectBankItemService;
import com.infolion.xdss3.collectbankitem.dao.CollectBankItemHibernateDao;

/**
 * <pre>
 * 收款银行行项(CollectBankItem)服务类
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
public class CollectBankItemServiceGen extends BaseService
{
	protected Log log = LogFactory.getFactory().getLog(this.getClass());

	@Autowired
	protected CollectBankItemHibernateDao collectBankItemHibernateDao;
	
	public void setCollectBankItemHibernateDao(CollectBankItemHibernateDao collectBankItemHibernateDao)
	{
		this.collectBankItemHibernateDao = collectBankItemHibernateDao;
	}
	
     /**
	 * 注入扩展服务类
	 */
	 @Autowired(required = false)
	 public void setAdvanceService(@Qualifier("collectBankItemAdvanceService") AdvanceService advanceService)
	 {
	  	super.setAdvanceService(advanceService);
	 }   
	 
	   
	/**
	 * 根据主键ID,取得收款银行行项实例
	 * @param id
	 * @return
	 */
	public CollectBankItem _getDetached(String id)
	{		
	    CollectBankItem collectBankItem = new CollectBankItem();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  collectBankItem = collectBankItemHibernateDao.getDetached(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(collectBankItem);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    collectBankItem.setOperationType(operationType);
	    
		return collectBankItem;	
	}
	
	/**
	 * 根据主键ID,取得收款银行行项实例
	 * @param id
	 * @return
	 */
	public CollectBankItem _get(String id)
	{		
	    CollectBankItem collectBankItem = new CollectBankItem();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  collectBankItem = collectBankItemHibernateDao.get(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(collectBankItem);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    collectBankItem.setOperationType(operationType);
	    
		return collectBankItem;	
	}
	
	/**
	 * 根据主键ID,取得收款银行行项实例
	 * @param id
	 * @return
	 */
	public CollectBankItem _getForEdit(String id)
	{		
	    CollectBankItem collectBankItem = _get(id);
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = collectBankItem.getOperationType();
	    if(OperationType.READONLY.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotEdit);
	    }

		return collectBankItem;	
	}
	
	/**
	 * 根据主键ID,取得收款银行行项实例副本
	 * @param id
	 * @return
	 */
	public CollectBankItem _getEntityCopy(String id)
	{		
	    CollectBankItem collectBankItem = new CollectBankItem();
		CollectBankItem collectBankItemOld = this._get(id);
		try
		{
			BeanUtils.copyProperties(collectBankItem, collectBankItemOld);
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (InvocationTargetException e)
		{
			e.printStackTrace();
		}
		//collectBankItem.setColbankitemid(null); 
		return collectBankItem;	
	}
	
	/**
	 * 删除  
	 *   
	 * @param collectBankItem
	 */
	public void _delete(CollectBankItem collectBankItem)
	{
		if (null != advanceService)
			advanceService.preDelete(collectBankItem);
	
 		LockService.isBoInstanceLocked(collectBankItem,CollectBankItem.class);
		collectBankItemHibernateDao.remove(collectBankItem);

		if (null != advanceService)
			advanceService.postDelete(collectBankItem);
	}

	/**
	 * 根据主键删除
	 * 
	 * @param collectBankItemId
	 */
	public void _delete(String collectBankItemId,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(collectBankItemId))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("colbankitemid"));
		CollectBankItem collectBankItem = this.collectBankItemHibernateDao.load(collectBankItemId);
		_delete(collectBankItem);
	}
	
	/**
	 * 删除  
	 *   
	 * @param Set<CollectBankItem> collectBankItems
	 */
	public void _deletes(Set<CollectBankItem> collectBankItems,BusinessObject businessObject)
	{
		if (null == collectBankItems || collectBankItems.size() < 1)
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoText());
		Iterator<CollectBankItem> it = collectBankItems.iterator();
		while (it.hasNext())
		{
			CollectBankItem collectBankItem = (CollectBankItem) it.next();
			_delete(collectBankItem);
		}
	}
	
	/**
	 * 根据多个主键(由，分割)删除多个对象
	 * 
	 * @param collectBankItemIds
	 */
	public void _deletes(String collectBankItemIds,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(collectBankItemIds))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("colbankitemid"));
		String[] ids = StringUtils.splitString(collectBankItemIds);
		for (int i = 0; i < ids.length; i++)
		{
			_delete(ids[i],businessObject);
		}
	}
	/**
	 * 提交工作流
	 * 
	 * @param collectBankItem
	 */
	public void _submitProcess(CollectBankItem collectBankItem
	,BusinessObject businessObject)
	{
		String id = collectBankItem.getColbankitemid();
        /**
		if (StringUtils.isNullBlank(id))
		{
			_save(collectBankItem);
		}
		else
		{
			_update(collectBankItem
			, businessObject);
		}**/
		
		String taskId = collectBankItem.getWorkflowTaskId();
		id = collectBankItem.getColbankitemid();
		if (null == taskId || "".equals(taskId))
			WorkflowService.createAndSignalProcessInstance(collectBankItem, id);
		else
			WorkflowService.signalProcessInstance(collectBankItem, id, null);
	}

	/**
	 * 保存或更新收款银行行项
	 * 保存  
	 *  
	 * @param collectBankItem
	 */
	public void _update(CollectBankItem collectBankItem
,BusinessObject businessObject	
	)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(collectBankItem);
		collectBankItemHibernateDao.saveOrUpdate(collectBankItem);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(collectBankItem);
	}
	
	/**
	 * 保存  
	 *   
	 * @param collectBankItem
	 */
	public void _save(CollectBankItem collectBankItem)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(collectBankItem);
		// 对id赋空值，使之执行insert操作(适应复制创建场景)
		collectBankItem.setColbankitemid(null);
                  		collectBankItemHibernateDao.saveOrUpdate(collectBankItem);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(collectBankItem);
	}
	
	/**
	 *
	 * 保存  
	 *   
	 * @param collectBankItem
	 */
	public void _saveOrUpdate(CollectBankItem collectBankItem
,BusinessObject businessObject		
	)
	{
		if (StringUtils.isNullBlank(collectBankItem.getColbankitemid()))
		{	
			_save(collectBankItem);
		}
		else
		{
			_update(collectBankItem
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