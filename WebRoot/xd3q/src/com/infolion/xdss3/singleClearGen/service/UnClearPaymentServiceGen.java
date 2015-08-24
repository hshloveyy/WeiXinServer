/*
 * @(#)UnClearPaymentServiceGen.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年07月09日 10点02分17秒
 *  描　述：创建
 */
package com.infolion.xdss3.singleClearGen.service;

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
import com.infolion.xdss3.singleClear.domain.UnClearPayment;
import com.infolion.xdss3.singleClear.service.UnClearPaymentService;
import com.infolion.xdss3.singleClear.dao.UnClearPaymentHibernateDao;

/**
 * <pre>
 * 未清付款（借方）(UnClearPayment)服务类
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
public class UnClearPaymentServiceGen extends BaseService
{
	protected Log log = LogFactory.getFactory().getLog(this.getClass());

	@Autowired
	protected UnClearPaymentHibernateDao unClearPaymentHibernateDao;
	
	public void setUnClearPaymentHibernateDao(UnClearPaymentHibernateDao unClearPaymentHibernateDao)
	{
		this.unClearPaymentHibernateDao = unClearPaymentHibernateDao;
	}
	
     /**
	 * 注入扩展服务类
	 */
	 @Autowired(required = false)
	 public void setAdvanceService(@Qualifier("unClearPaymentAdvanceService") AdvanceService advanceService)
	 {
	  	super.setAdvanceService(advanceService);
	 }   
	 
	   
	/**
	 * 根据主键ID,取得未清付款（借方）实例
	 * @param id
	 * @return
	 */
	public UnClearPayment _getDetached(String id)
	{		
	    UnClearPayment unClearPayment = new UnClearPayment();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  unClearPayment = unClearPaymentHibernateDao.getDetached(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(unClearPayment);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    unClearPayment.setOperationType(operationType);
	    
		return unClearPayment;	
	}
	
	/**
	 * 根据主键ID,取得未清付款（借方）实例
	 * @param id
	 * @return
	 */
	public UnClearPayment _get(String id)
	{		
	    UnClearPayment unClearPayment = new UnClearPayment();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  unClearPayment = unClearPaymentHibernateDao.get(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(unClearPayment);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    unClearPayment.setOperationType(operationType);
	    
		return unClearPayment;	
	}
	
	/**
	 * 根据主键ID,取得未清付款（借方）实例
	 * @param id
	 * @return
	 */
	public UnClearPayment _getForEdit(String id)
	{		
	    UnClearPayment unClearPayment = _get(id);
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = unClearPayment.getOperationType();
	    if(OperationType.READONLY.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotEdit);
	    }

		return unClearPayment;	
	}
	
	/**
	 * 根据主键ID,取得未清付款（借方）实例副本
	 * @param id
	 * @return
	 */
	public UnClearPayment _getEntityCopy(String id)
	{		
	    UnClearPayment unClearPayment = new UnClearPayment();
		UnClearPayment unClearPaymentOld = this._get(id);
		try
		{
			BeanUtils.copyProperties(unClearPayment, unClearPaymentOld);
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (InvocationTargetException e)
		{
			e.printStackTrace();
		}
		//unClearPayment.setUnclearpaymentid(null); 
		return unClearPayment;	
	}
	
	/**
	 * 删除  
	 *   
	 * @param unClearPayment
	 */
	public void _delete(UnClearPayment unClearPayment)
	{
		if (null != advanceService)
			advanceService.preDelete(unClearPayment);
	
 		LockService.isBoInstanceLocked(unClearPayment,UnClearPayment.class);
		unClearPaymentHibernateDao.remove(unClearPayment);

		if (null != advanceService)
			advanceService.postDelete(unClearPayment);
	}

	/**
	 * 根据主键删除
	 * 
	 * @param unClearPaymentId
	 */
	public void _delete(String unClearPaymentId,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(unClearPaymentId))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("unclearpaymentid"));
		UnClearPayment unClearPayment = this.unClearPaymentHibernateDao.load(unClearPaymentId);
		_delete(unClearPayment);
	}
	
	/**
	 * 删除  
	 *   
	 * @param Set<UnClearPayment> unClearPayments
	 */
	public void _deletes(Set<UnClearPayment> unClearPayments,BusinessObject businessObject)
	{
		if (null == unClearPayments || unClearPayments.size() < 1)
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoText());
		Iterator<UnClearPayment> it = unClearPayments.iterator();
		while (it.hasNext())
		{
			UnClearPayment unClearPayment = (UnClearPayment) it.next();
			_delete(unClearPayment);
		}
	}
	
	/**
	 * 根据多个主键(由，分割)删除多个对象
	 * 
	 * @param unClearPaymentIds
	 */
	public void _deletes(String unClearPaymentIds,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(unClearPaymentIds))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("unclearpaymentid"));
		String[] ids = StringUtils.splitString(unClearPaymentIds);
		for (int i = 0; i < ids.length; i++)
		{
			_delete(ids[i],businessObject);
		}
	}
	/**
	 * 提交工作流
	 * 
	 * @param unClearPayment
	 */
	public void _submitProcess(UnClearPayment unClearPayment
	,BusinessObject businessObject)
	{
		String id = unClearPayment.getUnclearpaymentid();
        /**
		if (StringUtils.isNullBlank(id))
		{
			_save(unClearPayment);
		}
		else
		{
			_update(unClearPayment
			, businessObject);
		}**/
		
		String taskId = unClearPayment.getWorkflowTaskId();
		id = unClearPayment.getUnclearpaymentid();
		if (null == taskId || "".equals(taskId))
			WorkflowService.createAndSignalProcessInstance(unClearPayment, id);
		else
			WorkflowService.signalProcessInstance(unClearPayment, id, null);
	}

	/**
	 * 保存或更新未清付款（借方）
	 * 保存  
	 *  
	 * @param unClearPayment
	 */
	public void _update(UnClearPayment unClearPayment
,BusinessObject businessObject	
	)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(unClearPayment);
		unClearPaymentHibernateDao.saveOrUpdate(unClearPayment);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(unClearPayment);
	}
	
	/**
	 * 保存  
	 *   
	 * @param unClearPayment
	 */
	public void _save(UnClearPayment unClearPayment)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(unClearPayment);
		// 对id赋空值，使之执行insert操作(适应复制创建场景)
		unClearPayment.setUnclearpaymentid(null);
                                      		unClearPaymentHibernateDao.saveOrUpdate(unClearPayment);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(unClearPayment);
	}
	
	/**
	 *
	 * 保存  
	 *   
	 * @param unClearPayment
	 */
	public void _saveOrUpdate(UnClearPayment unClearPayment
,BusinessObject businessObject		
	)
	{
		if (StringUtils.isNullBlank(unClearPayment.getUnclearpaymentid()))
		{	
			_save(unClearPayment);
		}
		else
		{
			_update(unClearPayment
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