/*
 * @(#)PurchaseInfoServiceGen.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年01月09日 11点03分53秒
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
import com.infolion.sample.purchase.domain.PurchaseInfo;
import com.infolion.sample.purchase.service.PurchaseInfoService;
import com.infolion.sample.purchase.dao.PurchaseInfoHibernateDao;
          
import com.infolion.sample.purchase.domain.PurchaseRows;
import com.infolion.sample.purchase.service.PurchaseRowsService;

/**
 * <pre>
 * 采购订单(SAP)(PurchaseInfo)服务类
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
public class PurchaseInfoServiceGen extends BaseService
{
	protected Log log = LogFactory.getFactory().getLog(this.getClass());

	@Autowired
	protected PurchaseInfoHibernateDao purchaseInfoHibernateDao;
	
	public void setPurchaseInfoHibernateDao(PurchaseInfoHibernateDao purchaseInfoHibernateDao)
	{
		this.purchaseInfoHibernateDao = purchaseInfoHibernateDao;
	}
	
     /**
	 * 注入扩展服务类
	 */
	 @Autowired(required = false)
	 public void setAdvanceService(@Qualifier("purchaseInfoAdvanceService") AdvanceService advanceService)
	 {
	  	super.setAdvanceService(advanceService);
	 }   
	 
          

	@Autowired
	protected PurchaseRowsService purchaseRowsService;
	
	public void setPurchaseRowsService(PurchaseRowsService purchaseRowsService)
	{
		this.purchaseRowsService = purchaseRowsService;
	}
	
	   
	/**
	 * 根据主键ID,取得采购订单(SAP)实例
	 * @param id
	 * @return
	 */
	public PurchaseInfo _get(String id)
	{		
	    PurchaseInfo purchaseInfo = new PurchaseInfo();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  purchaseInfo = purchaseInfoHibernateDao.get(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(purchaseInfo);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    purchaseInfo.setOperationType(operationType);
	    
		return purchaseInfo;	
	}
	
		/**
	 * 根据主键ID,取得采购订单(SAP)实例
	 * @param id
	 * @return
	 */
	public PurchaseInfo _getForEdit(String id)
	{		
	    PurchaseInfo purchaseInfo = _get(id);
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = purchaseInfo.getOperationType();
	    if(OperationType.READONLY.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotEdit);
	    }

		return purchaseInfo;	
	}
	
	/**
	 * 根据主键ID,取得采购订单(SAP)实例副本
	 * @param id
	 * @return
	 */
	public PurchaseInfo _getEntityCopy(String id)
	{		
	    PurchaseInfo purchaseInfo = new PurchaseInfo();
		PurchaseInfo purchaseInfoOld = this._get(id);
		try
		{
			BeanUtils.copyProperties(purchaseInfo, purchaseInfoOld);
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (InvocationTargetException e)
		{
			e.printStackTrace();
		}
		purchaseInfo.setPurchaseinfoId(null); 
		purchaseInfo.setProcessState(" ");
		return purchaseInfo;	
	}
	
	/**
	 * 删除  
	 *   
	 * @param purchaseInfo
	 */
	public void _delete(PurchaseInfo purchaseInfo)
	{
		if (null != advanceService)
			advanceService.preDelete(purchaseInfo);
	
		//流程状态
		String processState =purchaseInfo.getProcessState();
		if(!StringUtils.isNullBlank(processState))
		{
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.CannotDeleteApproveData);
		}
 		LockService.isBoInstanceLocked(purchaseInfo,PurchaseInfo.class);
		purchaseInfoHibernateDao.remove(purchaseInfo);

		if (null != advanceService)
			advanceService.postDelete(purchaseInfo);
	}

	/**
	 * 根据主键删除
	 * 
	 * @param purchaseInfoId
	 */
	public void _delete(String purchaseInfoId,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(purchaseInfoId))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("purchaseinfoId"));
		PurchaseInfo purchaseInfo = this.purchaseInfoHibernateDao.load(purchaseInfoId);
		_delete(purchaseInfo);
	}
	
	/**
	 * 删除  
	 *   
	 * @param Set<PurchaseInfo> purchaseInfos
	 */
	public void _deletes(Set<PurchaseInfo> purchaseInfos,BusinessObject businessObject)
	{
		if (null == purchaseInfos || purchaseInfos.size() < 1)
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoText());
		Iterator<PurchaseInfo> it = purchaseInfos.iterator();
		while (it.hasNext())
		{
			PurchaseInfo purchaseInfo = (PurchaseInfo) it.next();
			_delete(purchaseInfo);
		}
	}
	
	/**
	 * 根据多个主键(由，分割)删除多个对象
	 * 
	 * @param purchaseInfoIds
	 */
	public void _deletes(String purchaseInfoIds,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(purchaseInfoIds))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("purchaseinfoId"));
		String[] ids = StringUtils.splitString(purchaseInfoIds);
		for (int i = 0; i < ids.length; i++)
		{
			_delete(ids[i],businessObject);
		}
	}
	/**
	 * 提交工作流
	 * 
	 * @param purchaseInfo
	 */
	public void _submitProcess(PurchaseInfo purchaseInfo
,Set<PurchaseRows> deletedPurchaseRowsSet	,BusinessObject businessObject)
	{
		String id = purchaseInfo.getPurchaseinfoId();
		if (StringUtils.isNullBlank(id))
		{
			_save(purchaseInfo);
		}
		else
		{
			_update(purchaseInfo
,deletedPurchaseRowsSet			, businessObject);
		}
		String taskId = purchaseInfo.getWorkflowTaskId();
		if (null == taskId || "".equals(taskId))
			WorkflowService.createAndSignalProcessInstance(purchaseInfo, id);
		else
			WorkflowService.signalProcessInstance(purchaseInfo, id, null);
	}

	/**
	 * 保存或更新采购订单(SAP)
	 * 保存  
	 *  
	 * @param purchaseInfo
	 */
	public void _update(PurchaseInfo purchaseInfo
,Set<PurchaseRows> deletedPurchaseRowsSet,BusinessObject businessObject	
	)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(purchaseInfo);
		purchaseInfoHibernateDao.saveOrUpdate(purchaseInfo);
// 删除关联子业务对象数据
if(deletedPurchaseRowsSet!=null && deletedPurchaseRowsSet.size()>0)
{
purchaseRowsService._deletes(deletedPurchaseRowsSet,businessObject);
}		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(purchaseInfo);
	}
	
	/**
	 * 保存  
	 *   
	 * @param purchaseInfo
	 */
	public void _save(PurchaseInfo purchaseInfo)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(purchaseInfo);
		// 对id赋空值，使之执行insert操作(适应复制创建场景)
		purchaseInfo.setPurchaseinfoId(null);
       
		Set<PurchaseRows> purchaseRowsSet = purchaseInfo.getPurchaseRows();
		Set<PurchaseRows> newPurchaseRowsSet = null;
		if (null != purchaseRowsSet)
		{
			newPurchaseRowsSet = new HashSet();
			Iterator<PurchaseRows> itPurchaseRows = purchaseRowsSet.iterator();
			while (itPurchaseRows.hasNext())
			{
				PurchaseRows purchaseRows = (PurchaseRows) itPurchaseRows.next();
				purchaseRows.setPurchaserowsId(null);
				newPurchaseRowsSet.add(purchaseRows);
			}
		}
		purchaseInfo.setPurchaseRows(newPurchaseRowsSet);
       
       
       
       
       
       
       
       
       
       
       
       
       
       
       
       
       
       
       
       
       
       
       
       
       
       
       
       
       
       
       
       
       
       
       
       
       
       
       
       
       
		purchaseInfoHibernateDao.saveOrUpdate(purchaseInfo);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(purchaseInfo);
	}
	
	/**
	 *
	 * 保存  
	 *   
	 * @param purchaseInfo
	 */
	public void _saveOrUpdate(PurchaseInfo purchaseInfo
,Set<PurchaseRows> deletedPurchaseRowsSet,BusinessObject businessObject		
	)
	{
		if (StringUtils.isNullBlank(purchaseInfo.getPurchaseinfoId()))
		{	
			_save(purchaseInfo);
		}
		else
		{
			_update(purchaseInfo
,deletedPurchaseRowsSet
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