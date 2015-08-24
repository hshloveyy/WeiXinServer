/*
 * @(#)CustomerCreditProjServiceGen.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年05月20日 12点33分44秒
 *  描　述：创建
 */
package com.infolion.xdss3.ceditValueControlGen.service;

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
import com.infolion.xdss3.ceditValueControl.domain.CustomerCreditProj;
import com.infolion.xdss3.ceditValueControl.service.CustomerCreditProjService;
import com.infolion.xdss3.ceditValueControl.dao.CustomerCreditProjHibernateDao;

/**
 * <pre>
 * 客户信用额度下挂立项配置表(CustomerCreditProj)服务类
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
public class CustomerCreditProjServiceGen extends BaseService
{
	protected Log log = LogFactory.getFactory().getLog(this.getClass());

	@Autowired
	protected CustomerCreditProjHibernateDao customerCreditProjHibernateDao;
	
	public void setCustomerCreditProjHibernateDao(CustomerCreditProjHibernateDao customerCreditProjHibernateDao)
	{
		this.customerCreditProjHibernateDao = customerCreditProjHibernateDao;
	}
	
     /**
	 * 注入扩展服务类
	 */
	 @Autowired(required = false)
	 public void setAdvanceService(@Qualifier("customerCreditProjAdvanceService") AdvanceService advanceService)
	 {
	  	super.setAdvanceService(advanceService);
	 }   
	 
	   
	/**
	 * 根据主键ID,取得客户信用额度下挂立项配置表实例
	 * @param id
	 * @return
	 */
	public CustomerCreditProj _getDetached(String id)
	{		
	    CustomerCreditProj customerCreditProj = new CustomerCreditProj();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  customerCreditProj = customerCreditProjHibernateDao.getDetached(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(customerCreditProj);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    customerCreditProj.setOperationType(operationType);
	    
		return customerCreditProj;	
	}
	
	/**
	 * 根据主键ID,取得客户信用额度下挂立项配置表实例
	 * @param id
	 * @return
	 */
	public CustomerCreditProj _get(String id)
	{		
	    CustomerCreditProj customerCreditProj = new CustomerCreditProj();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  customerCreditProj = customerCreditProjHibernateDao.get(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(customerCreditProj);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    customerCreditProj.setOperationType(operationType);
	    
		return customerCreditProj;	
	}
	
	/**
	 * 根据主键ID,取得客户信用额度下挂立项配置表实例
	 * @param id
	 * @return
	 */
	public CustomerCreditProj _getForEdit(String id)
	{		
	    CustomerCreditProj customerCreditProj = _get(id);
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = customerCreditProj.getOperationType();
	    if(OperationType.READONLY.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotEdit);
	    }

		return customerCreditProj;	
	}
	
	/**
	 * 根据主键ID,取得客户信用额度下挂立项配置表实例副本
	 * @param id
	 * @return
	 */
	public CustomerCreditProj _getEntityCopy(String id)
	{		
	    CustomerCreditProj customerCreditProj = new CustomerCreditProj();
		CustomerCreditProj customerCreditProjOld = this._get(id);
		try
		{
			BeanUtils.copyProperties(customerCreditProj, customerCreditProjOld);
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (InvocationTargetException e)
		{
			e.printStackTrace();
		}
		//customerCreditProj.setConfigprojectid(null); 
		return customerCreditProj;	
	}
	
	/**
	 * 删除  
	 *   
	 * @param customerCreditProj
	 */
	public void _delete(CustomerCreditProj customerCreditProj)
	{
		if (null != advanceService)
			advanceService.preDelete(customerCreditProj);
	
 		LockService.isBoInstanceLocked(customerCreditProj,CustomerCreditProj.class);
		customerCreditProjHibernateDao.remove(customerCreditProj);

		if (null != advanceService)
			advanceService.postDelete(customerCreditProj);
	}

	/**
	 * 根据主键删除
	 * 
	 * @param customerCreditProjId
	 */
	public void _delete(String customerCreditProjId,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(customerCreditProjId))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("configprojectid"));
		CustomerCreditProj customerCreditProj = this.customerCreditProjHibernateDao.load(customerCreditProjId);
		_delete(customerCreditProj);
	}
	
	/**
	 * 删除  
	 *   
	 * @param Set<CustomerCreditProj> customerCreditProjs
	 */
	public void _deletes(Set<CustomerCreditProj> customerCreditProjs,BusinessObject businessObject)
	{
		if (null == customerCreditProjs || customerCreditProjs.size() < 1)
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoText());
		Iterator<CustomerCreditProj> it = customerCreditProjs.iterator();
		while (it.hasNext())
		{
			CustomerCreditProj customerCreditProj = (CustomerCreditProj) it.next();
			_delete(customerCreditProj);
		}
	}
	
	/**
	 * 根据多个主键(由，分割)删除多个对象
	 * 
	 * @param customerCreditProjIds
	 */
	public void _deletes(String customerCreditProjIds,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(customerCreditProjIds))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("configprojectid"));
		String[] ids = StringUtils.splitString(customerCreditProjIds);
		for (int i = 0; i < ids.length; i++)
		{
			_delete(ids[i],businessObject);
		}
	}
	/**
	 * 提交工作流
	 * 
	 * @param customerCreditProj
	 */
	public void _submitProcess(CustomerCreditProj customerCreditProj
	,BusinessObject businessObject)
	{
		String id = customerCreditProj.getConfigprojectid();
		if (StringUtils.isNullBlank(id))
		{
			_save(customerCreditProj);
		}
		else
		{
			_update(customerCreditProj
			, businessObject);
		}
		String taskId = customerCreditProj.getWorkflowTaskId();
		id = customerCreditProj.getConfigprojectid();
		if (null == taskId || "".equals(taskId))
			WorkflowService.createAndSignalProcessInstance(customerCreditProj, id);
		else
			WorkflowService.signalProcessInstance(customerCreditProj, id, null);
	}

	/**
	 * 保存或更新客户信用额度下挂立项配置表
	 * 保存  
	 *  
	 * @param customerCreditProj
	 */
	public void _update(CustomerCreditProj customerCreditProj
,BusinessObject businessObject	
	)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(customerCreditProj);
		customerCreditProjHibernateDao.saveOrUpdate(customerCreditProj);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(customerCreditProj);
	}
	
	/**
	 * 保存  
	 *   
	 * @param customerCreditProj
	 */
	public void _save(CustomerCreditProj customerCreditProj)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(customerCreditProj);
		// 对id赋空值，使之执行insert操作(适应复制创建场景)
		customerCreditProj.setConfigprojectid(null);
                		customerCreditProjHibernateDao.saveOrUpdate(customerCreditProj);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(customerCreditProj);
	}
	
	/**
	 *
	 * 保存  
	 *   
	 * @param customerCreditProj
	 */
	public void _saveOrUpdate(CustomerCreditProj customerCreditProj
,BusinessObject businessObject		
	)
	{
		if (StringUtils.isNullBlank(customerCreditProj.getConfigprojectid()))
		{	
			_save(customerCreditProj);
		}
		else
		{
			_update(customerCreditProj
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