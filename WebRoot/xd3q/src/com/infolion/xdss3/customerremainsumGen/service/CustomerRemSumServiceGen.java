/*
 * @(#)CustomerRemSumServiceGen.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年09月13日 09点53分55秒
 *  描　述：创建
 */
package com.infolion.xdss3.customerremainsumGen.service;

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
import com.infolion.xdss3.customerremainsum.domain.CustomerRemSum;
import com.infolion.xdss3.customerremainsum.service.CustomerRemSumService;
import com.infolion.xdss3.customerremainsum.dao.CustomerRemSumHibernateDao;

/**
 * <pre>
 * 客户项目余额(CustomerRemSum)服务类
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
public class CustomerRemSumServiceGen extends BaseService
{
	protected Log log = LogFactory.getFactory().getLog(this.getClass());

	@Autowired
	protected CustomerRemSumHibernateDao customerRemSumHibernateDao;
	
	public void setCustomerRemSumHibernateDao(CustomerRemSumHibernateDao customerRemSumHibernateDao)
	{
		this.customerRemSumHibernateDao = customerRemSumHibernateDao;
	}
	
     /**
	 * 注入扩展服务类
	 */
	 @Autowired(required = false)
	 public void setAdvanceService(@Qualifier("customerRemSumAdvanceService") AdvanceService advanceService)
	 {
	  	super.setAdvanceService(advanceService);
	 }   
	 
	   
	/**
	 * 根据主键ID,取得客户项目余额实例
	 * @param id
	 * @return
	 */
	public CustomerRemSum _getDetached(String id)
	{		
	    CustomerRemSum customerRemSum = new CustomerRemSum();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  customerRemSum = customerRemSumHibernateDao.getDetached(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(customerRemSum);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    customerRemSum.setOperationType(operationType);
	    
		return customerRemSum;	
	}
	
	/**
	 * 根据主键ID,取得客户项目余额实例
	 * @param id
	 * @return
	 */
	public CustomerRemSum _get(String id)
	{		
	    CustomerRemSum customerRemSum = new CustomerRemSum();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  customerRemSum = customerRemSumHibernateDao.get(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(customerRemSum);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    customerRemSum.setOperationType(operationType);
	    
		return customerRemSum;	
	}
	
	/**
	 * 根据主键ID,取得客户项目余额实例
	 * @param id
	 * @return
	 */
	public CustomerRemSum _getForEdit(String id)
	{		
	    CustomerRemSum customerRemSum = _get(id);
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = customerRemSum.getOperationType();
	    if(OperationType.READONLY.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotEdit);
	    }

		return customerRemSum;	
	}
	
	/**
	 * 根据主键ID,取得客户项目余额实例副本
	 * @param id
	 * @return
	 */
	public CustomerRemSum _getEntityCopy(String id)
	{		
	    CustomerRemSum customerRemSum = new CustomerRemSum();
		CustomerRemSum customerRemSumOld = this._get(id);
		try
		{
			BeanUtils.copyProperties(customerRemSum, customerRemSumOld);
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (InvocationTargetException e)
		{
			e.printStackTrace();
		}
		//customerRemSum.setCprid(null); 
		return customerRemSum;	
	}
	
	/**
	 * 删除  
	 *   
	 * @param customerRemSum
	 */
	public void _delete(CustomerRemSum customerRemSum)
	{
		if (null != advanceService)
			advanceService.preDelete(customerRemSum);
	
 		LockService.isBoInstanceLocked(customerRemSum,CustomerRemSum.class);
		customerRemSumHibernateDao.remove(customerRemSum);

		if (null != advanceService)
			advanceService.postDelete(customerRemSum);
	}

	/**
	 * 根据主键删除
	 * 
	 * @param customerRemSumId
	 */
	public void _delete(String customerRemSumId,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(customerRemSumId))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("cprid"));
		CustomerRemSum customerRemSum = this.customerRemSumHibernateDao.load(customerRemSumId);
		_delete(customerRemSum);
	}
	
	/**
	 * 删除  
	 *   
	 * @param Set<CustomerRemSum> customerRemSums
	 */
	public void _deletes(Set<CustomerRemSum> customerRemSums,BusinessObject businessObject)
	{
		if (null == customerRemSums || customerRemSums.size() < 1)
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoText());
		Iterator<CustomerRemSum> it = customerRemSums.iterator();
		while (it.hasNext())
		{
			CustomerRemSum customerRemSum = (CustomerRemSum) it.next();
			_delete(customerRemSum);
		}
	}
	
	/**
	 * 根据多个主键(由，分割)删除多个对象
	 * 
	 * @param customerRemSumIds
	 */
	public void _deletes(String customerRemSumIds,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(customerRemSumIds))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("cprid"));
		String[] ids = StringUtils.splitString(customerRemSumIds);
		for (int i = 0; i < ids.length; i++)
		{
			_delete(ids[i],businessObject);
		}
	}
	/**
	 * 提交工作流
	 * 
	 * @param customerRemSum
	 */
	public void _submitProcess(CustomerRemSum customerRemSum
	,BusinessObject businessObject)
	{
		String id = customerRemSum.getCprid();
        /**
		if (StringUtils.isNullBlank(id))
		{
			_save(customerRemSum);
		}
		else
		{
			_update(customerRemSum
			, businessObject);
		}**/
		
		String taskId = customerRemSum.getWorkflowTaskId();
		id = customerRemSum.getCprid();
		if (null == taskId || "".equals(taskId))
			WorkflowService.createAndSignalProcessInstance(customerRemSum, id);
		else
			WorkflowService.signalProcessInstance(customerRemSum, id, null);
	}

	/**
	 * 保存或更新客户项目余额
	 * 保存  
	 *  
	 * @param customerRemSum
	 */
	public void _update(CustomerRemSum customerRemSum
,BusinessObject businessObject	
	)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(customerRemSum);
		customerRemSumHibernateDao.saveOrUpdate(customerRemSum);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(customerRemSum);
	}
	
	/**
	 * 保存  
	 *   
	 * @param customerRemSum
	 */
	public void _save(CustomerRemSum customerRemSum)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(customerRemSum);
		// 对id赋空值，使之执行insert操作(适应复制创建场景)
		customerRemSum.setCprid(null);
              		customerRemSumHibernateDao.saveOrUpdate(customerRemSum);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(customerRemSum);
	}
	
	/**
	 *
	 * 保存  
	 *   
	 * @param customerRemSum
	 */
	public void _saveOrUpdate(CustomerRemSum customerRemSum
,BusinessObject businessObject		
	)
	{
		if (StringUtils.isNullBlank(customerRemSum.getCprid()))
		{	
			_save(customerRemSum);
		}
		else
		{
			_update(customerRemSum
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