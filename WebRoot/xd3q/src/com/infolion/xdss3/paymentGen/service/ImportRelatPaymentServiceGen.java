/*
 * @(#)ImportRelatPaymentServiceGen.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年07月25日 09点52分24秒
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
import com.infolion.xdss3.payment.domain.ImportRelatPayment;
import com.infolion.xdss3.payment.service.ImportRelatPaymentService;
import com.infolion.xdss3.payment.dao.ImportRelatPaymentHibernateDao;

/**
 * <pre>
 * 相关单据(ImportRelatPayment)服务类
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
public class ImportRelatPaymentServiceGen extends BaseService
{
	protected Log log = LogFactory.getFactory().getLog(this.getClass());

	@Autowired
	protected ImportRelatPaymentHibernateDao importRelatPaymentHibernateDao;
	
	public void setImportRelatPaymentHibernateDao(ImportRelatPaymentHibernateDao importRelatPaymentHibernateDao)
	{
		this.importRelatPaymentHibernateDao = importRelatPaymentHibernateDao;
	}
	
     /**
	 * 注入扩展服务类
	 */
	 @Autowired(required = false)
	 public void setAdvanceService(@Qualifier("importRelatPaymentAdvanceService") AdvanceService advanceService)
	 {
	  	super.setAdvanceService(advanceService);
	 }   
	 
	   
	/**
	 * 根据主键ID,取得相关单据实例
	 * @param id
	 * @return
	 */
	public ImportRelatPayment _getDetached(String id)
	{		
	    ImportRelatPayment importRelatPayment = new ImportRelatPayment();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  importRelatPayment = importRelatPaymentHibernateDao.getDetached(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(importRelatPayment);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    importRelatPayment.setOperationType(operationType);
	    
		return importRelatPayment;	
	}
	
	/**
	 * 根据主键ID,取得相关单据实例
	 * @param id
	 * @return
	 */
	public ImportRelatPayment _get(String id)
	{		
	    ImportRelatPayment importRelatPayment = new ImportRelatPayment();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  importRelatPayment = importRelatPaymentHibernateDao.get(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(importRelatPayment);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    importRelatPayment.setOperationType(operationType);
	    
		return importRelatPayment;	
	}
	
	/**
	 * 根据主键ID,取得相关单据实例
	 * @param id
	 * @return
	 */
	public ImportRelatPayment _getForEdit(String id)
	{		
	    ImportRelatPayment importRelatPayment = _get(id);
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = importRelatPayment.getOperationType();
	    if(OperationType.READONLY.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotEdit);
	    }

		return importRelatPayment;	
	}
	
	/**
	 * 根据主键ID,取得相关单据实例副本
	 * @param id
	 * @return
	 */
	public ImportRelatPayment _getEntityCopy(String id)
	{		
	    ImportRelatPayment importRelatPayment = new ImportRelatPayment();
		ImportRelatPayment importRelatPaymentOld = this._get(id);
		try
		{
			BeanUtils.copyProperties(importRelatPayment, importRelatPaymentOld);
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (InvocationTargetException e)
		{
			e.printStackTrace();
		}
		//importRelatPayment.setRelatedpaymentid(null); 
		return importRelatPayment;	
	}
	
	/**
	 * 删除  
	 *   
	 * @param importRelatPayment
	 */
	public void _delete(ImportRelatPayment importRelatPayment)
	{
		if (null != advanceService)
			advanceService.preDelete(importRelatPayment);
	
 		LockService.isBoInstanceLocked(importRelatPayment,ImportRelatPayment.class);
		importRelatPaymentHibernateDao.remove(importRelatPayment);

		if (null != advanceService)
			advanceService.postDelete(importRelatPayment);
	}

	/**
	 * 根据主键删除
	 * 
	 * @param importRelatPaymentId
	 */
	public void _delete(String importRelatPaymentId,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(importRelatPaymentId))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("relatedpaymentid"));
		ImportRelatPayment importRelatPayment = this.importRelatPaymentHibernateDao.load(importRelatPaymentId);
		_delete(importRelatPayment);
	}
	
	/**
	 * 删除  
	 *   
	 * @param Set<ImportRelatPayment> importRelatPayments
	 */
	public void _deletes(Set<ImportRelatPayment> importRelatPayments,BusinessObject businessObject)
	{
		if (null == importRelatPayments || importRelatPayments.size() < 1)
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoText());
		Iterator<ImportRelatPayment> it = importRelatPayments.iterator();
		while (it.hasNext())
		{
			ImportRelatPayment importRelatPayment = (ImportRelatPayment) it.next();
			_delete(importRelatPayment);
		}
	}
	
	/**
	 * 根据多个主键(由，分割)删除多个对象
	 * 
	 * @param importRelatPaymentIds
	 */
	public void _deletes(String importRelatPaymentIds,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(importRelatPaymentIds))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("relatedpaymentid"));
		String[] ids = StringUtils.splitString(importRelatPaymentIds);
		for (int i = 0; i < ids.length; i++)
		{
			_delete(ids[i],businessObject);
		}
	}
	/**
	 * 提交工作流
	 * 
	 * @param importRelatPayment
	 */
	public void _submitProcess(ImportRelatPayment importRelatPayment
	,BusinessObject businessObject)
	{
		String id = importRelatPayment.getRelatedpaymentid();
        /**
		if (StringUtils.isNullBlank(id))
		{
			_save(importRelatPayment);
		}
		else
		{
			_update(importRelatPayment
			, businessObject);
		}**/
		
		String taskId = importRelatPayment.getWorkflowTaskId();
		id = importRelatPayment.getRelatedpaymentid();
		if (null == taskId || "".equals(taskId))
			WorkflowService.createAndSignalProcessInstance(importRelatPayment, id);
		else
			WorkflowService.signalProcessInstance(importRelatPayment, id, null);
	}

	/**
	 * 保存或更新相关单据
	 * 保存  
	 *  
	 * @param importRelatPayment
	 */
	public void _update(ImportRelatPayment importRelatPayment
,BusinessObject businessObject	
	)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(importRelatPayment);
		importRelatPaymentHibernateDao.saveOrUpdate(importRelatPayment);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(importRelatPayment);
	}
	
	/**
	 * 保存  
	 *   
	 * @param importRelatPayment
	 */
	public void _save(ImportRelatPayment importRelatPayment)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(importRelatPayment);
		// 对id赋空值，使之执行insert操作(适应复制创建场景)
		importRelatPayment.setRelatedpaymentid(null);
                		importRelatPaymentHibernateDao.saveOrUpdate(importRelatPayment);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(importRelatPayment);
	}
	
	/**
	 *
	 * 保存  
	 *   
	 * @param importRelatPayment
	 */
	public void _saveOrUpdate(ImportRelatPayment importRelatPayment
,BusinessObject businessObject		
	)
	{
		if (StringUtils.isNullBlank(importRelatPayment.getRelatedpaymentid()))
		{	
			_save(importRelatPayment);
		}
		else
		{
			_update(importRelatPayment
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