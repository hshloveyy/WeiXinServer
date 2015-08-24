/*
 * @(#)SupplierSinglOtherServiceGen.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2012年06月08日 11点51分23秒
 *  描　述：创建
 */
package com.infolion.xdss3.singleClearOtherGen.service;

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
import com.infolion.xdss3.singleClearOther.domain.SupplierSinglOther;
import com.infolion.xdss3.singleClearOther.service.SupplierSinglOtherService;
import com.infolion.xdss3.singleClearOther.dao.SupplierSinglOtherHibernateDao;
          
import com.infolion.xdss3.singleClearOther.domain.UnCleaPaymentOther;
import com.infolion.xdss3.singleClearOther.service.UnCleaPaymentOtherService;
          
import com.infolion.xdss3.singleClearOther.domain.UnSupplieBillOther;
import com.infolion.xdss3.singleClearOther.service.UnSupplieBillOtherService;
          
import com.infolion.xdss3.singleClearOther.domain.SettleSubjectOther;
import com.infolion.xdss3.singleClearOther.service.SettleSubjectOtherService;
          
import com.infolion.xdss3.singleClearOther.domain.FundFlowOther;
import com.infolion.xdss3.singleClearOther.service.FundFlowOtherService;

/**
 * <pre>
 * 其他公司供应商单清帐(SupplierSinglOther)服务类
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
public class SupplierSinglOtherServiceGen extends BaseService
{
	protected Log log = LogFactory.getFactory().getLog(this.getClass());

	@Autowired
	protected SupplierSinglOtherHibernateDao supplierSinglOtherHibernateDao;
	
	public void setSupplierSinglOtherHibernateDao(SupplierSinglOtherHibernateDao supplierSinglOtherHibernateDao)
	{
		this.supplierSinglOtherHibernateDao = supplierSinglOtherHibernateDao;
	}
	
     /**
	 * 注入扩展服务类
	 */
	 @Autowired(required = false)
	 public void setAdvanceService(@Qualifier("supplierSinglOtherAdvanceService") AdvanceService advanceService)
	 {
	  	super.setAdvanceService(advanceService);
	 }   
	 
          

	@Autowired
	protected UnCleaPaymentOtherService unCleaPaymentOtherService;
	
	public void setUnCleaPaymentOtherService(UnCleaPaymentOtherService unCleaPaymentOtherService)
	{
		this.unCleaPaymentOtherService = unCleaPaymentOtherService;
	}
          

	@Autowired
	protected UnSupplieBillOtherService unSupplieBillOtherService;
	
	public void setUnSupplieBillOtherService(UnSupplieBillOtherService unSupplieBillOtherService)
	{
		this.unSupplieBillOtherService = unSupplieBillOtherService;
	}
          

	@Autowired
	protected SettleSubjectOtherService settleSubjectOtherService;
	
	public void setSettleSubjectOtherService(SettleSubjectOtherService settleSubjectOtherService)
	{
		this.settleSubjectOtherService = settleSubjectOtherService;
	}
          

	@Autowired
	protected FundFlowOtherService fundFlowOtherService;
	
	public void setFundFlowOtherService(FundFlowOtherService fundFlowOtherService)
	{
		this.fundFlowOtherService = fundFlowOtherService;
	}

          
          
          
          

	   
	/**
	 * 根据主键ID,取得其他公司供应商单清帐实例
	 * @param id
	 * @return
	 */
	public SupplierSinglOther _getDetached(String id)
	{		
	    SupplierSinglOther supplierSinglOther = new SupplierSinglOther();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  supplierSinglOther = supplierSinglOtherHibernateDao.getDetached(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(supplierSinglOther);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    supplierSinglOther.setOperationType(operationType);
	    
		return supplierSinglOther;	
	}
	
	/**
	 * 根据主键ID,取得其他公司供应商单清帐实例
	 * @param id
	 * @return
	 */
	public SupplierSinglOther _get(String id)
	{		
	    SupplierSinglOther supplierSinglOther = new SupplierSinglOther();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  supplierSinglOther = supplierSinglOtherHibernateDao.get(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(supplierSinglOther);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    supplierSinglOther.setOperationType(operationType);
	    
		return supplierSinglOther;	
	}
	
	/**
	 * 根据主键ID,取得其他公司供应商单清帐实例
	 * @param id
	 * @return
	 */
	public SupplierSinglOther _getForEdit(String id)
	{		
	    SupplierSinglOther supplierSinglOther = _get(id);
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = supplierSinglOther.getOperationType();
	    if(OperationType.READONLY.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotEdit);
	    }

		return supplierSinglOther;	
	}
	
	/**
	 * 根据主键ID,取得其他公司供应商单清帐实例副本
	 * @param id
	 * @return
	 */
	public SupplierSinglOther _getEntityCopy(String id)
	{		
	    SupplierSinglOther supplierSinglOther = new SupplierSinglOther();
		SupplierSinglOther supplierSinglOtherOld = this._get(id);
		try
		{
			BeanUtils.copyProperties(supplierSinglOther, supplierSinglOtherOld);
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (InvocationTargetException e)
		{
			e.printStackTrace();
		}
		supplierSinglOther.setSupplierclearno(null); 
		//supplierSinglOther.setSuppliersclearid(null); 
		supplierSinglOther.setProcessstate(" ");
		return supplierSinglOther;	
	}
	
	/**
	 * 删除  
	 *   
	 * @param supplierSinglOther
	 */
	public void _delete(SupplierSinglOther supplierSinglOther)
	{
		if (null != advanceService)
			advanceService.preDelete(supplierSinglOther);
	
		//流程状态
		String processState =supplierSinglOther.getProcessstate();
		if(!StringUtils.isNullBlank(processState))
		{
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.CannotDeleteApproveData);
		}
 		LockService.isBoInstanceLocked(supplierSinglOther,SupplierSinglOther.class);
		supplierSinglOtherHibernateDao.remove(supplierSinglOther);

		if (null != advanceService)
			advanceService.postDelete(supplierSinglOther);
	}

	/**
	 * 根据主键删除
	 * 
	 * @param supplierSinglOtherId
	 */
	public void _delete(String supplierSinglOtherId,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(supplierSinglOtherId))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("suppliersclearid"));
		SupplierSinglOther supplierSinglOther = this.supplierSinglOtherHibernateDao.load(supplierSinglOtherId);
		_delete(supplierSinglOther);
	}
	
	/**
	 * 删除  
	 *   
	 * @param Set<SupplierSinglOther> supplierSinglOthers
	 */
	public void _deletes(Set<SupplierSinglOther> supplierSinglOthers,BusinessObject businessObject)
	{
		if (null == supplierSinglOthers || supplierSinglOthers.size() < 1)
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoText());
		Iterator<SupplierSinglOther> it = supplierSinglOthers.iterator();
		while (it.hasNext())
		{
			SupplierSinglOther supplierSinglOther = (SupplierSinglOther) it.next();
			_delete(supplierSinglOther);
		}
	}
	
	/**
	 * 根据多个主键(由，分割)删除多个对象
	 * 
	 * @param supplierSinglOtherIds
	 */
	public void _deletes(String supplierSinglOtherIds,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(supplierSinglOtherIds))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("suppliersclearid"));
		String[] ids = StringUtils.splitString(supplierSinglOtherIds);
		for (int i = 0; i < ids.length; i++)
		{
			_delete(ids[i],businessObject);
		}
	}
	/**
	 * 提交工作流
	 * 
	 * @param supplierSinglOther
	 */
	public void _submitProcess(SupplierSinglOther supplierSinglOther
,Set<UnCleaPaymentOther> deletedUnCleaPaymentOtherSet
,Set<UnSupplieBillOther> deletedUnSupplieBillOtherSet	,BusinessObject businessObject)
	{
		String id = supplierSinglOther.getSuppliersclearid();
        /**
		if (StringUtils.isNullBlank(id))
		{
			_save(supplierSinglOther);
		}
		else
		{
			_update(supplierSinglOther
,deletedUnCleaPaymentOtherSet
,deletedUnSupplieBillOtherSet			, businessObject);
		}**/
		
		String taskId = supplierSinglOther.getWorkflowTaskId();
		id = supplierSinglOther.getSuppliersclearid();
		if (null == taskId || "".equals(taskId))
			WorkflowService.createAndSignalProcessInstance(supplierSinglOther, id);
		else
			WorkflowService.signalProcessInstance(supplierSinglOther, id, null);
	}

	/**
	 * 保存或更新其他公司供应商单清帐
	 * 保存  
	 *  
	 * @param supplierSinglOther
	 */
	public void _update(SupplierSinglOther supplierSinglOther
,Set<UnCleaPaymentOther> deletedUnCleaPaymentOtherSet
,Set<UnSupplieBillOther> deletedUnSupplieBillOtherSet,BusinessObject businessObject	
	)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(supplierSinglOther);
		supplierSinglOtherHibernateDao.saveOrUpdate(supplierSinglOther);
// 删除关联子业务对象数据
if(deletedUnCleaPaymentOtherSet!=null && deletedUnCleaPaymentOtherSet.size()>0)
{
unCleaPaymentOtherService._deletes(deletedUnCleaPaymentOtherSet,businessObject);
}
// 删除关联子业务对象数据
if(deletedUnSupplieBillOtherSet!=null && deletedUnSupplieBillOtherSet.size()>0)
{
unSupplieBillOtherService._deletes(deletedUnSupplieBillOtherSet,businessObject);
}		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(supplierSinglOther);
	}
	
	/**
	 * 保存  
	 *   
	 * @param supplierSinglOther
	 */
	public void _save(SupplierSinglOther supplierSinglOther)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(supplierSinglOther);
		// 对id赋空值，使之执行insert操作(适应复制创建场景)
		supplierSinglOther.setSuppliersclearid(null);
       
     
     
     
       
     
     
     
       
     
     
     
       
     
     
     
       
     
     
     
       
     
     
     
       
     
     
     
       
     
     
     
       
     
     
     
       
     
     
     
       
     
     
     
       
     
     
     
       
     
     
     
       
     
     
     
       
     
     
     
       
     
     
     
       
     
     
     
       
     
     
     
       
     
     
     
       
     
     
     
       
		Set<UnCleaPaymentOther> UnCleaPaymentOtherSet = supplierSinglOther.getUnCleaPaymentOther();
		Set<UnCleaPaymentOther> newUnCleaPaymentOtherSet = null;
		if (null != UnCleaPaymentOtherSet)
		{
			newUnCleaPaymentOtherSet = new HashSet();
			Iterator<UnCleaPaymentOther> itUnCleaPaymentOther = UnCleaPaymentOtherSet.iterator();
			while (itUnCleaPaymentOther.hasNext())
			{
				UnCleaPaymentOther UnCleaPaymentOther = (UnCleaPaymentOther) itUnCleaPaymentOther.next();
				UnCleaPaymentOther.setUnclearpaymentid(null);
				newUnCleaPaymentOtherSet.add(UnCleaPaymentOther);
			}
		}
		supplierSinglOther.setUnCleaPaymentOther(newUnCleaPaymentOtherSet);
     
     
     
       
     
		Set<UnSupplieBillOther> UnSupplieBillOtherSet = supplierSinglOther.getUnSupplieBillOther();
		Set<UnSupplieBillOther> newUnSupplieBillOtherSet = null;
		if (null != UnSupplieBillOtherSet)
		{
			newUnSupplieBillOtherSet = new HashSet();
			Iterator<UnSupplieBillOther> itUnSupplieBillOther = UnSupplieBillOtherSet.iterator();
			while (itUnSupplieBillOther.hasNext())
			{
				UnSupplieBillOther UnSupplieBillOther = (UnSupplieBillOther) itUnSupplieBillOther.next();
				UnSupplieBillOther.setUnclearsbillid(null);
				newUnSupplieBillOtherSet.add(UnSupplieBillOther);
			}
		}
		supplierSinglOther.setUnSupplieBillOther(newUnSupplieBillOtherSet);
     
     
       
     
     
         SettleSubjectOther SettleSubjectOther = supplierSinglOther.getSettleSubjectOther();
         if (null != SettleSubjectOther)
		 {
		    SettleSubjectOther.setSettlesubjectid(null);
		 }
		 supplierSinglOther.setSettleSubjectOther(SettleSubjectOther);
     
       
     
     
     
         FundFlowOther FundFlowOther = supplierSinglOther.getFundFlowOther();
         if (null != FundFlowOther)
		 {
		    FundFlowOther.setFundflowid(null);
		 }
		 supplierSinglOther.setFundFlowOther(FundFlowOther);
		supplierSinglOtherHibernateDao.saveOrUpdate(supplierSinglOther);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(supplierSinglOther);
	}
	
	/**
	 *
	 * 保存  
	 *   
	 * @param supplierSinglOther
	 */
	public void _saveOrUpdate(SupplierSinglOther supplierSinglOther
,Set<UnCleaPaymentOther> deletedUnCleaPaymentOtherSet
,Set<UnSupplieBillOther> deletedUnSupplieBillOtherSet,BusinessObject businessObject		
	)
	{
if (StringUtils.isNullBlank(supplierSinglOther.getSupplierclearno()))
{
String supplierclearno = NumberService.getNextObjectNumber("supplierSingleOther", supplierSinglOther);
supplierSinglOther.setSupplierclearno(supplierclearno);
}		if (StringUtils.isNullBlank(supplierSinglOther.getSuppliersclearid()))
		{	
			_save(supplierSinglOther);
		}
		else
		{
			_update(supplierSinglOther
,deletedUnCleaPaymentOtherSet
,deletedUnSupplieBillOtherSet
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