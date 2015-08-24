/*
 * @(#)ImportPaymentCbillServiceGen.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年07月25日 09点52分22秒
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
import com.infolion.xdss3.payment.domain.ImportPaymentCbill;
import com.infolion.xdss3.payment.service.ImportPaymentCbillService;
import com.infolion.xdss3.payment.dao.ImportPaymentCbillHibernateDao;

/**
 * <pre>
 * 发票清帐(ImportPaymentCbill)服务类
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
public class ImportPaymentCbillServiceGen extends BaseService
{
	protected Log log = LogFactory.getFactory().getLog(this.getClass());

	@Autowired
	protected ImportPaymentCbillHibernateDao importPaymentCbillHibernateDao;
	
	public void setImportPaymentCbillHibernateDao(ImportPaymentCbillHibernateDao importPaymentCbillHibernateDao)
	{
		this.importPaymentCbillHibernateDao = importPaymentCbillHibernateDao;
	}
	
     /**
	 * 注入扩展服务类
	 */
	 @Autowired(required = false)
	 public void setAdvanceService(@Qualifier("importPaymentCbillAdvanceService") AdvanceService advanceService)
	 {
	  	super.setAdvanceService(advanceService);
	 }   
	 
	   
	/**
	 * 根据主键ID,取得发票清帐实例
	 * @param id
	 * @return
	 */
	public ImportPaymentCbill _getDetached(String id)
	{		
	    ImportPaymentCbill importPaymentCbill = new ImportPaymentCbill();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  importPaymentCbill = importPaymentCbillHibernateDao.getDetached(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(importPaymentCbill);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    importPaymentCbill.setOperationType(operationType);
	    
		return importPaymentCbill;	
	}
	
	/**
	 * 根据主键ID,取得发票清帐实例
	 * @param id
	 * @return
	 */
	public ImportPaymentCbill _get(String id)
	{		
	    ImportPaymentCbill importPaymentCbill = new ImportPaymentCbill();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  importPaymentCbill = importPaymentCbillHibernateDao.get(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(importPaymentCbill);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    importPaymentCbill.setOperationType(operationType);
	    
		return importPaymentCbill;	
	}
	
	/**
	 * 根据主键ID,取得发票清帐实例
	 * @param id
	 * @return
	 */
	public ImportPaymentCbill _getForEdit(String id)
	{		
	    ImportPaymentCbill importPaymentCbill = _get(id);
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = importPaymentCbill.getOperationType();
	    if(OperationType.READONLY.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotEdit);
	    }

		return importPaymentCbill;	
	}
	
	/**
	 * 根据主键ID,取得发票清帐实例副本
	 * @param id
	 * @return
	 */
	public ImportPaymentCbill _getEntityCopy(String id)
	{		
	    ImportPaymentCbill importPaymentCbill = new ImportPaymentCbill();
		ImportPaymentCbill importPaymentCbillOld = this._get(id);
		try
		{
			BeanUtils.copyProperties(importPaymentCbill, importPaymentCbillOld);
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (InvocationTargetException e)
		{
			e.printStackTrace();
		}
		//importPaymentCbill.setPaymentcbillid(null); 
		return importPaymentCbill;	
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
	 * @param importPaymentCbill
	 */
	public void _delete(ImportPaymentCbill importPaymentCbill)
	{
		if (null != advanceService)
			advanceService.preDelete(importPaymentCbill);
	
 		LockService.isBoInstanceLocked(importPaymentCbill,ImportPaymentCbill.class);
		importPaymentCbillHibernateDao.remove(importPaymentCbill);

		if (null != advanceService)
			advanceService.postDelete(importPaymentCbill);
	}

	/**
	 * 根据主键删除
	 * 
	 * @param importPaymentCbillId
	 */
	public void _delete(String importPaymentCbillId,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(importPaymentCbillId))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("paymentcbillid"));
		ImportPaymentCbill importPaymentCbill = this.importPaymentCbillHibernateDao.load(importPaymentCbillId);
		_delete(importPaymentCbill);
	}
	
	/**
	 * 删除  
	 *   
	 * @param Set<ImportPaymentCbill> importPaymentCbills
	 */
	public void _deletes(Set<ImportPaymentCbill> importPaymentCbills,BusinessObject businessObject)
	{
		if (null == importPaymentCbills || importPaymentCbills.size() < 1)
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoText());
		Iterator<ImportPaymentCbill> it = importPaymentCbills.iterator();
		while (it.hasNext())
		{
			ImportPaymentCbill importPaymentCbill = (ImportPaymentCbill) it.next();
			_delete(importPaymentCbill);
		}
	}
	
	/**
	 * 根据多个主键(由，分割)删除多个对象
	 * 
	 * @param importPaymentCbillIds
	 */
	public void _deletes(String importPaymentCbillIds,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(importPaymentCbillIds))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("paymentcbillid"));
		String[] ids = StringUtils.splitString(importPaymentCbillIds);
		for (int i = 0; i < ids.length; i++)
		{
			_delete(ids[i],businessObject);
		}
	}
	/**
	 * 提交工作流
	 * 
	 * @param importPaymentCbill
	 */
	public void _submitProcess(ImportPaymentCbill importPaymentCbill
	,BusinessObject businessObject)
	{
		String id = importPaymentCbill.getPaymentcbillid();
        /**
		if (StringUtils.isNullBlank(id))
		{
			_save(importPaymentCbill);
		}
		else
		{
			_update(importPaymentCbill
			, businessObject);
		}**/
		
		String taskId = importPaymentCbill.getWorkflowTaskId();
		id = importPaymentCbill.getPaymentcbillid();
		if (null == taskId || "".equals(taskId))
			WorkflowService.createAndSignalProcessInstance(importPaymentCbill, id);
		else
			WorkflowService.signalProcessInstance(importPaymentCbill, id, null);
	}

	/**
	 * 保存或更新发票清帐
	 * 保存  
	 *  
	 * @param importPaymentCbill
	 */
	public void _update(ImportPaymentCbill importPaymentCbill
,BusinessObject businessObject	
	)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(importPaymentCbill);
		importPaymentCbillHibernateDao.saveOrUpdate(importPaymentCbill);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(importPaymentCbill);
	}
	
	/**
	 * 保存  
	 *   
	 * @param importPaymentCbill
	 */
	public void _save(ImportPaymentCbill importPaymentCbill)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(importPaymentCbill);
		// 对id赋空值，使之执行insert操作(适应复制创建场景)
		importPaymentCbill.setPaymentcbillid(null);
                                      		importPaymentCbillHibernateDao.saveOrUpdate(importPaymentCbill);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(importPaymentCbill);
	}
	
	/**
	 *
	 * 保存  
	 *   
	 * @param importPaymentCbill
	 */
	public void _saveOrUpdate(ImportPaymentCbill importPaymentCbill
,BusinessObject businessObject		
	)
	{
		if (StringUtils.isNullBlank(importPaymentCbill.getPaymentcbillid()))
		{	
			_save(importPaymentCbill);
		}
		else
		{
			_update(importPaymentCbill
, businessObject);
}	}
	
}