/*
 * @(#)BillInPaymentServiceGen.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年07月07日 17点42分28秒
 *  描　述：创建
 */
package com.infolion.xdss3.billClearGen.service;

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
import com.infolion.xdss3.billClear.domain.BillInPayment;
import com.infolion.xdss3.billClear.service.BillInPaymentService;
import com.infolion.xdss3.billClear.dao.BillInPaymentHibernateDao;

/**
 * <pre>
 * 未清预付款表(BillInPayment)服务类
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
public class BillInPaymentServiceGen extends BaseService
{
	protected Log log = LogFactory.getFactory().getLog(this.getClass());

	@Autowired
	protected BillInPaymentHibernateDao billInPaymentHibernateDao;
	
	public void setBillInPaymentHibernateDao(BillInPaymentHibernateDao billInPaymentHibernateDao)
	{
		this.billInPaymentHibernateDao = billInPaymentHibernateDao;
	}
	
     /**
	 * 注入扩展服务类
	 */
	 @Autowired(required = false)
	 public void setAdvanceService(@Qualifier("billInPaymentAdvanceService") AdvanceService advanceService)
	 {
	  	super.setAdvanceService(advanceService);
	 }   
	 
	   
	/**
	 * 根据主键ID,取得未清预付款表实例
	 * @param id
	 * @return
	 */
	public BillInPayment _getDetached(String id)
	{		
	    BillInPayment billInPayment = new BillInPayment();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  billInPayment = billInPaymentHibernateDao.getDetached(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(billInPayment);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    billInPayment.setOperationType(operationType);
	    
		return billInPayment;	
	}
	
	/**
	 * 根据主键ID,取得未清预付款表实例
	 * @param id
	 * @return
	 */
	public BillInPayment _get(String id)
	{		
	    BillInPayment billInPayment = new BillInPayment();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  billInPayment = billInPaymentHibernateDao.get(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(billInPayment);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    billInPayment.setOperationType(operationType);
	    
		return billInPayment;	
	}
	
	/**
	 * 根据主键ID,取得未清预付款表实例
	 * @param id
	 * @return
	 */
	public BillInPayment _getForEdit(String id)
	{		
	    BillInPayment billInPayment = _get(id);
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = billInPayment.getOperationType();
	    if(OperationType.READONLY.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotEdit);
	    }

		return billInPayment;	
	}
	
	/**
	 * 根据主键ID,取得未清预付款表实例副本
	 * @param id
	 * @return
	 */
	public BillInPayment _getEntityCopy(String id)
	{		
	    BillInPayment billInPayment = new BillInPayment();
		BillInPayment billInPaymentOld = this._get(id);
		try
		{
			BeanUtils.copyProperties(billInPayment, billInPaymentOld);
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (InvocationTargetException e)
		{
			e.printStackTrace();
		}
		//billInPayment.setBillnpaymentid(null); 
		return billInPayment;	
	}
	
	/**
	 * 删除  
	 *   
	 * @param billInPayment
	 */
	public void _delete(BillInPayment billInPayment)
	{
		if (null != advanceService)
			advanceService.preDelete(billInPayment);
	
 		LockService.isBoInstanceLocked(billInPayment,BillInPayment.class);
		billInPaymentHibernateDao.remove(billInPayment);

		if (null != advanceService)
			advanceService.postDelete(billInPayment);
	}

	/**
	 * 根据主键删除
	 * 
	 * @param billInPaymentId
	 */
	public void _delete(String billInPaymentId,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(billInPaymentId))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("billnpaymentid"));
		BillInPayment billInPayment = this.billInPaymentHibernateDao.load(billInPaymentId);
		_delete(billInPayment);
	}
	
	/**
	 * 删除  
	 *   
	 * @param Set<BillInPayment> billInPayments
	 */
	public void _deletes(Set<BillInPayment> billInPayments,BusinessObject businessObject)
	{
		if (null == billInPayments || billInPayments.size() < 1)
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoText());
		Iterator<BillInPayment> it = billInPayments.iterator();
		while (it.hasNext())
		{
			BillInPayment billInPayment = (BillInPayment) it.next();
			_delete(billInPayment);
		}
	}
	
	/**
	 * 根据多个主键(由，分割)删除多个对象
	 * 
	 * @param billInPaymentIds
	 */
	public void _deletes(String billInPaymentIds,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(billInPaymentIds))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("billnpaymentid"));
		String[] ids = StringUtils.splitString(billInPaymentIds);
		for (int i = 0; i < ids.length; i++)
		{
			_delete(ids[i],businessObject);
		}
	}
	/**
	 * 提交工作流
	 * 
	 * @param billInPayment
	 */
	public void _submitProcess(BillInPayment billInPayment
	,BusinessObject businessObject)
	{
		String id = billInPayment.getBillnpaymentid();
        /**
		if (StringUtils.isNullBlank(id))
		{
			_save(billInPayment);
		}
		else
		{
			_update(billInPayment
			, businessObject);
		}**/
		
		String taskId = billInPayment.getWorkflowTaskId();
		id = billInPayment.getBillnpaymentid();
		if (null == taskId || "".equals(taskId))
			WorkflowService.createAndSignalProcessInstance(billInPayment, id);
		else
			WorkflowService.signalProcessInstance(billInPayment, id, null);
	}

	/**
	 * 保存或更新未清预付款表
	 * 保存  
	 *  
	 * @param billInPayment
	 */
	public void _update(BillInPayment billInPayment
,BusinessObject businessObject	
	)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(billInPayment);
		billInPaymentHibernateDao.saveOrUpdate(billInPayment);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(billInPayment);
	}
	
	/**
	 * 保存  
	 *   
	 * @param billInPayment
	 */
	public void _save(BillInPayment billInPayment)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(billInPayment);
		// 对id赋空值，使之执行insert操作(适应复制创建场景)
		billInPayment.setBillnpaymentid(null);
                                		billInPaymentHibernateDao.saveOrUpdate(billInPayment);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(billInPayment);
	}
	
	/**
	 *
	 * 保存  
	 *   
	 * @param billInPayment
	 */
	public void _saveOrUpdate(BillInPayment billInPayment
,BusinessObject businessObject		
	)
	{
		if (StringUtils.isNullBlank(billInPayment.getBillnpaymentid()))
		{	
			_save(billInPayment);
		}
		else
		{
			_update(billInPayment
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