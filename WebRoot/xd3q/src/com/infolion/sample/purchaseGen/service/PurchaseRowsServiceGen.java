/*
 * @(#)PurchaseRowsServiceGen.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年01月09日 11点03分54秒
 *  描　述：创建
 */
package com.infolion.sample.purchaseGen.service;

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
import com.infolion.sample.purchase.domain.PurchaseRows;
import com.infolion.sample.purchase.service.PurchaseRowsService;
import com.infolion.sample.purchase.dao.PurchaseRowsHibernateDao;

/**
 * <pre>
 * 采购订单行项目信息(SAP)(PurchaseRows)服务类
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
public class PurchaseRowsServiceGen extends BaseService
{
	protected Log log = LogFactory.getFactory().getLog(this.getClass());

	@Autowired
	protected PurchaseRowsHibernateDao purchaseRowsHibernateDao;
	
	public void setPurchaseRowsHibernateDao(PurchaseRowsHibernateDao purchaseRowsHibernateDao)
	{
		this.purchaseRowsHibernateDao = purchaseRowsHibernateDao;
	}
	
     /**
	 * 注入扩展服务类
	 */
	 @Autowired(required = false)
	 public void setAdvanceService(@Qualifier("purchaseRowsAdvanceService") AdvanceService advanceService)
	 {
	  	super.setAdvanceService(advanceService);
	 }   
	 
	   
	/**
	 * 根据主键ID,取得采购订单行项目信息(SAP)实例
	 * @param id
	 * @return
	 */
	public PurchaseRows _get(String id)
	{		
	    PurchaseRows purchaseRows = new PurchaseRows();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  purchaseRows = purchaseRowsHibernateDao.get(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(purchaseRows);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    purchaseRows.setOperationType(operationType);
	    
		return purchaseRows;	
	}
	
		/**
	 * 根据主键ID,取得采购订单行项目信息(SAP)实例
	 * @param id
	 * @return
	 */
	public PurchaseRows _getForEdit(String id)
	{		
	    PurchaseRows purchaseRows = _get(id);
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = purchaseRows.getOperationType();
	    if(OperationType.READONLY.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotEdit);
	    }

		return purchaseRows;	
	}
	
	/**
	 * 根据主键ID,取得采购订单行项目信息(SAP)实例副本
	 * @param id
	 * @return
	 */
	public PurchaseRows _getEntityCopy(String id)
	{		
	    PurchaseRows purchaseRows = new PurchaseRows();
		PurchaseRows purchaseRowsOld = this._get(id);
		try
		{
			BeanUtils.copyProperties(purchaseRows, purchaseRowsOld);
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (InvocationTargetException e)
		{
			e.printStackTrace();
		}
		purchaseRows.setPurchaserowsId(null); 
		return purchaseRows;	
	}
	
	/**
	 * 删除  
	 *   
	 * @param purchaseRows
	 */
	public void _delete(PurchaseRows purchaseRows)
	{
		if (null != advanceService)
			advanceService.preDelete(purchaseRows);
	
 		LockService.isBoInstanceLocked(purchaseRows,PurchaseRows.class);
		purchaseRowsHibernateDao.remove(purchaseRows);

		if (null != advanceService)
			advanceService.postDelete(purchaseRows);
	}

	/**
	 * 根据主键删除
	 * 
	 * @param purchaseRowsId
	 */
	public void _delete(String purchaseRowsId,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(purchaseRowsId))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("purchaserowsId"));
		PurchaseRows purchaseRows = this.purchaseRowsHibernateDao.load(purchaseRowsId);
		_delete(purchaseRows);
	}
	
	/**
	 * 删除  
	 *   
	 * @param Set<PurchaseRows> purchaseRowss
	 */
	public void _deletes(Set<PurchaseRows> purchaseRowss,BusinessObject businessObject)
	{
		if (null == purchaseRowss || purchaseRowss.size() < 1)
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoText());
		Iterator<PurchaseRows> it = purchaseRowss.iterator();
		while (it.hasNext())
		{
			PurchaseRows purchaseRows = (PurchaseRows) it.next();
			_delete(purchaseRows);
		}
	}
	
	/**
	 * 根据多个主键(由，分割)删除多个对象
	 * 
	 * @param purchaseRowsIds
	 */
	public void _deletes(String purchaseRowsIds,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(purchaseRowsIds))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("purchaserowsId"));
		String[] ids = StringUtils.splitString(purchaseRowsIds);
		for (int i = 0; i < ids.length; i++)
		{
			_delete(ids[i],businessObject);
		}
	}
	/**
	 * 提交工作流
	 * 
	 * @param purchaseRows
	 */
	public void _submitProcess(PurchaseRows purchaseRows
	,BusinessObject businessObject)
	{
		String id = purchaseRows.getPurchaserowsId();
		if (StringUtils.isNullBlank(id))
		{
			_save(purchaseRows);
		}
		else
		{
			_update(purchaseRows
			, businessObject);
		}
		String taskId = purchaseRows.getWorkflowTaskId();
		if (null == taskId || "".equals(taskId))
			WorkflowService.createAndSignalProcessInstance(purchaseRows, id);
		else
			WorkflowService.signalProcessInstance(purchaseRows, id, null);
	}

	/**
	 * 保存或更新采购订单行项目信息(SAP)
	 * 保存  
	 *  
	 * @param purchaseRows
	 */
	public void _update(PurchaseRows purchaseRows
,BusinessObject businessObject	
	)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(purchaseRows);
		purchaseRowsHibernateDao.saveOrUpdate(purchaseRows);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(purchaseRows);
	}
	
	/**
	 * 保存  
	 *   
	 * @param purchaseRows
	 */
	public void _save(PurchaseRows purchaseRows)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(purchaseRows);
		// 对id赋空值，使之执行insert操作(适应复制创建场景)
		purchaseRows.setPurchaserowsId(null);
                                                      		purchaseRowsHibernateDao.saveOrUpdate(purchaseRows);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(purchaseRows);
	}
	
	/**
	 *
	 * 保存  
	 *   
	 * @param purchaseRows
	 */
	public void _saveOrUpdate(PurchaseRows purchaseRows
,BusinessObject businessObject		
	)
	{
		if (StringUtils.isNullBlank(purchaseRows.getPurchaserowsId()))
		{	
			_save(purchaseRows);
		}
		else
		{
			_update(purchaseRows
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