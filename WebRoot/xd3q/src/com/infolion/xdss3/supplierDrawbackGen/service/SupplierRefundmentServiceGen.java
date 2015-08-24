/*
 * @(#)SupplierRefundmentServiceGen.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年07月07日 17点58分09秒
 *  描　述：创建
 */
package com.infolion.xdss3.supplierDrawbackGen.service;

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
import com.infolion.xdss3.financeSquare.domain.SettleSubject;
import com.infolion.xdss3.supplierDrawback.domain.SupplierRefundment;
import com.infolion.xdss3.supplierDrawback.service.SupplierRefundmentService;
import com.infolion.xdss3.supplierDrawback.dao.SupplierRefundmentHibernateDao;
          
import com.infolion.xdss3.supplierDrawback.domain.SupplierDbBankItem;
import com.infolion.xdss3.supplierDrawback.service.SupplierDbBankItemService;
          
import com.infolion.xdss3.supplierDrawback.domain.SupplierRefundItem;
import com.infolion.xdss3.supplierDrawback.service.SupplierRefundItemService;

/**
 * <pre>
 * 供应商退款(SupplierRefundment)服务类
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
public class SupplierRefundmentServiceGen extends BaseService
{
	protected Log log = LogFactory.getFactory().getLog(this.getClass());

	@Autowired
	protected SupplierRefundmentHibernateDao supplierRefundmentHibernateDao;
	
	public void setSupplierRefundmentHibernateDao(SupplierRefundmentHibernateDao supplierRefundmentHibernateDao)
	{
		this.supplierRefundmentHibernateDao = supplierRefundmentHibernateDao;
	}
	
     /**
	 * 注入扩展服务类
	 */
	 @Autowired(required = false)
	 public void setAdvanceService(@Qualifier("supplierRefundmentAdvanceService") AdvanceService advanceService)
	 {
	  	super.setAdvanceService(advanceService);
	 }   
	 
          

	@Autowired
	protected SupplierDbBankItemService supplierDbBankItemService;
	
	public void setSupplierDbBankItemService(SupplierDbBankItemService supplierDbBankItemService)
	{
		this.supplierDbBankItemService = supplierDbBankItemService;
	}
          

	@Autowired
	protected SupplierRefundItemService supplierRefundItemService;
	
	public void setSupplierRefundItemService(SupplierRefundItemService supplierRefundItemService)
	{
		this.supplierRefundItemService = supplierRefundItemService;
	}

          
          

	   
	/**
	 * 根据主键ID,取得供应商退款实例
	 * @param id
	 * @return
	 */
	public SupplierRefundment _getDetached(String id)
	{		
	    SupplierRefundment supplierRefundment = new SupplierRefundment();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  supplierRefundment = supplierRefundmentHibernateDao.getDetached(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(supplierRefundment);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    supplierRefundment.setOperationType(operationType);
	    
		return supplierRefundment;	
	}
	
	/**
	 * 根据主键ID,取得供应商退款实例
	 * @param id
	 * @return
	 */
	public SupplierRefundment _get(String id)
	{		
	    SupplierRefundment supplierRefundment = new SupplierRefundment();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  supplierRefundment = supplierRefundmentHibernateDao.get(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(supplierRefundment);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    supplierRefundment.setOperationType(operationType);
	    
		return supplierRefundment;	
	}
	
	/**
	 * 根据主键ID,取得供应商退款实例
	 * @param id
	 * @return
	 */
	public SupplierRefundment _getForEdit(String id)
	{		
	    SupplierRefundment supplierRefundment = _get(id);
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = supplierRefundment.getOperationType();
	    if(OperationType.READONLY.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotEdit);
	    }

		return supplierRefundment;	
	}
	
	/**
	 * 根据主键ID,取得供应商退款实例副本
	 * @param id
	 * @return
	 */
	public SupplierRefundment _getEntityCopy(String id)
	{		
	    SupplierRefundment supplierRefundment = new SupplierRefundment();
		SupplierRefundment supplierRefundmentOld = this._get(id);
		try
		{
			BeanUtils.copyProperties(supplierRefundment, supplierRefundmentOld);
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (InvocationTargetException e)
		{
			e.printStackTrace();
		}
		supplierRefundment.setRefundmentno(null); 
		//supplierRefundment.setRefundmentid(null); 
		supplierRefundment.setProcessstate(" ");
		return supplierRefundment;	
	}
	
	/**
	 * 删除  
	 *   
	 * @param Set<SupplierRefundment> supplierRefundments
	 */
	public void _deletes(Set<SupplierRefundment> supplierRefundments,BusinessObject businessObject)
	{
		if (null == supplierRefundments || supplierRefundments.size() < 1)
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoText());
		Iterator<SupplierRefundment> it = supplierRefundments.iterator();
		while (it.hasNext())
		{
			SupplierRefundment supplierRefundment = (SupplierRefundment) it.next();
			_delete(supplierRefundment);
		}
	}
	
	/**
	 * 根据多个主键(由，分割)删除多个对象
	 * 
	 * @param supplierRefundmentIds
	 */
	public void _deletes(String supplierRefundmentIds,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(supplierRefundmentIds))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("refundmentid"));
		String[] ids = StringUtils.splitString(supplierRefundmentIds);
		for (int i = 0; i < ids.length; i++)
		{
			_delete(ids[i],businessObject);
		}
	}
	/**
	 * 提交工作流
	 * 
	 * @param supplierRefundment
	 */
	public void _submitProcess(SupplierRefundment supplierRefundment
,Set<SupplierDbBankItem> deletedSupplierDbBankItemSet
,Set<SupplierRefundItem> deletedSupplierRefundItemSet	,BusinessObject businessObject)
	{
		String id = supplierRefundment.getRefundmentid();
        /**
		if (StringUtils.isNullBlank(id))
		{
			_save(supplierRefundment);
		}
		else
		{
			_update(supplierRefundment
,deletedSupplierDbBankItemSet
,deletedSupplierRefundItemSet			, businessObject);
		}**/
		
		String taskId = supplierRefundment.getWorkflowTaskId();
		id = supplierRefundment.getRefundmentid();
		if (null == taskId || "".equals(taskId))
			WorkflowService.createAndSignalProcessInstance(supplierRefundment, id);
		else
			WorkflowService.signalProcessInstance(supplierRefundment, id, null);
	}

	/**
	 * 保存或更新供应商退款
	 * 保存  
	 *  
	 * @param supplierRefundment
	 */
	public void _update(SupplierRefundment supplierRefundment
,Set<SupplierDbBankItem> deletedSupplierDbBankItemSet
,Set<SupplierRefundItem> deletedSupplierRefundItemSet,BusinessObject businessObject	
	)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(supplierRefundment);
		supplierRefundmentHibernateDao.saveOrUpdate(supplierRefundment);
// 删除关联子业务对象数据
		//hjj结算科目
        if (supplierRefundment.getSettleSubject() == null) {
            this.supplierRefundmentHibernateDao.deleteById("YSETTLESUBJECT", "REFUNDMENTID",
                    supplierRefundment.getRefundmentid());
        }
if(deletedSupplierDbBankItemSet!=null && deletedSupplierDbBankItemSet.size()>0)
{
supplierDbBankItemService._deletes(deletedSupplierDbBankItemSet,businessObject);
}
// 删除关联子业务对象数据
if(deletedSupplierRefundItemSet!=null && deletedSupplierRefundItemSet.size()>0)
{
supplierRefundItemService._deletes(deletedSupplierRefundItemSet,businessObject);
}		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(supplierRefundment);
	}
	
	/**
	 * 保存  
	 *   
	 * @param supplierRefundment
	 */
	public void _save(SupplierRefundment supplierRefundment)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(supplierRefundment);
		// 对id赋空值，使之执行insert操作(适应复制创建场景)
		supplierRefundment.setRefundmentid(null);
       
		Set<SupplierDbBankItem> supplierDbBankItemSet = supplierRefundment.getSupplierDbBankItem();
		Set<SupplierDbBankItem> newSupplierDbBankItemSet = null;
		if (null != supplierDbBankItemSet)
		{
			newSupplierDbBankItemSet = new HashSet();
			Iterator<SupplierDbBankItem> itSupplierDbBankItem = supplierDbBankItemSet.iterator();
			while (itSupplierDbBankItem.hasNext())
			{
				SupplierDbBankItem supplierDbBankItem = (SupplierDbBankItem) itSupplierDbBankItem.next();
				supplierDbBankItem.setRefundbankitemid(null);
				newSupplierDbBankItemSet.add(supplierDbBankItem);
			}
		}
		supplierRefundment.setSupplierDbBankItem(newSupplierDbBankItemSet);
     
       
     
		Set<SupplierRefundItem> supplierRefundItemSet = supplierRefundment.getSupplierRefundItem();
		Set<SupplierRefundItem> newSupplierRefundItemSet = null;
		if (null != supplierRefundItemSet)
		{
			newSupplierRefundItemSet = new HashSet();
			Iterator<SupplierRefundItem> itSupplierRefundItem = supplierRefundItemSet.iterator();
			while (itSupplierRefundItem.hasNext())
			{
				SupplierRefundItem supplierRefundItem = (SupplierRefundItem) itSupplierRefundItem.next();
				supplierRefundItem.setRefundmentitemid(null);
				newSupplierRefundItemSet.add(supplierRefundItem);
			}
		}
		supplierRefundment.setSupplierRefundItem(newSupplierRefundItemSet);
       
		//hjj结算科目
		SettleSubject settleSubject = supplierRefundment.getSettleSubject();
        if (null != settleSubject)
        {
            settleSubject.setSettlesubjectid(null);
        }
        supplierRefundment.setSettleSubject(settleSubject);
       
     
       
     
       
     
       
     
       
     
       
     
       
     
       
     
       
     
       
     
       
     
       
     
       
     
       
     
       
     
       
     
		supplierRefundmentHibernateDao.saveOrUpdate(supplierRefundment);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(supplierRefundment);
	}
	
	/**
	 *
	 * 保存  
	 *   
	 * @param supplierRefundment
	 */
	public void _saveOrUpdate(SupplierRefundment supplierRefundment
,Set<SupplierDbBankItem> deletedSupplierDbBankItemSet
,Set<SupplierRefundItem> deletedSupplierRefundItemSet,BusinessObject businessObject		
	)
	{
if (StringUtils.isNullBlank(supplierRefundment.getRefundmentno()))
{
String refundmentno = NumberService.getNextObjectNumber("SupplierRefundmentNo", supplierRefundment);
supplierRefundment.setRefundmentno(refundmentno);
}		if (StringUtils.isNullBlank(supplierRefundment.getRefundmentid()))
		{	
			_save(supplierRefundment);
		}
		else
		{
			_update(supplierRefundment
,deletedSupplierDbBankItemSet
,deletedSupplierRefundItemSet
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
	
	/**
	 * 删除  
	 *   
	 * @param supplierRefundment
	 */
	public void _delete(SupplierRefundment supplierRefundment)
	{
		if (null != advanceService)
			advanceService.preDelete(supplierRefundment);
	
		//流程状态
		String processState =supplierRefundment.getProcessstate();
		if(!StringUtils.isNullBlank(processState))
		{
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.CannotDeleteApproveData);
		}
 		LockService.isBoInstanceLocked(supplierRefundment,SupplierRefundment.class);
		supplierRefundmentHibernateDao.remove(supplierRefundment);

		if (null != advanceService)
			advanceService.postDelete(supplierRefundment);
	}

	/**
	 * 根据主键删除
	 * 
	 * @param supplierRefundmentId
	 */
	public void _delete(String supplierRefundmentId,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(supplierRefundmentId))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("refundmentid"));
		SupplierRefundment supplierRefundment = this.supplierRefundmentHibernateDao.load(supplierRefundmentId);
		_delete(supplierRefundment);
	}
}