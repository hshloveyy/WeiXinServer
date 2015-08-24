/*
 * @(#)CustomerCreditConfServiceGen.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年05月20日 12点33分42秒
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
import com.infolion.xdss3.ceditValueControl.domain.CustomerCreditConf;
import com.infolion.xdss3.ceditValueControl.service.CustomerCreditConfService;
import com.infolion.xdss3.ceditValueControl.dao.CustomerCreditConfHibernateDao;
          
import com.infolion.xdss3.ceditValueControl.domain.CustomerCreditProj;
import com.infolion.xdss3.ceditValueControl.service.CustomerCreditProjService;

/**
 * <pre>
 * 客户代垫额度和发货额度配置(CustomerCreditConf)服务类
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
public class CustomerCreditConfServiceGen extends BaseService
{
	protected Log log = LogFactory.getFactory().getLog(this.getClass());

	@Autowired
	protected CustomerCreditConfHibernateDao customerCreditConfHibernateDao;
	
	public void setCustomerCreditConfHibernateDao(CustomerCreditConfHibernateDao customerCreditConfHibernateDao)
	{
		this.customerCreditConfHibernateDao = customerCreditConfHibernateDao;
	}
	
     /**
	 * 注入扩展服务类
	 */
	 @Autowired(required = false)
	 public void setAdvanceService(@Qualifier("customerCreditConfAdvanceService") AdvanceService advanceService)
	 {
	  	super.setAdvanceService(advanceService);
	 }   
	 
          

	@Autowired
	protected CustomerCreditProjService customerCreditProjService;
	
	public void setCustomerCreditProjService(CustomerCreditProjService customerCreditProjService)
	{
		this.customerCreditProjService = customerCreditProjService;
	}
	
	   
	/**
	 * 根据主键ID,取得客户代垫额度和发货额度配置实例
	 * @param id
	 * @return
	 */
	public CustomerCreditConf _getDetached(String id)
	{		
	    CustomerCreditConf customerCreditConf = new CustomerCreditConf();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  customerCreditConf = customerCreditConfHibernateDao.getDetached(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(customerCreditConf);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    customerCreditConf.setOperationType(operationType);
	    
		return customerCreditConf;	
	}
	
	/**
	 * 根据主键ID,取得客户代垫额度和发货额度配置实例
	 * @param id
	 * @return
	 */
	public CustomerCreditConf _get(String id)
	{		
	    CustomerCreditConf customerCreditConf = new CustomerCreditConf();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  customerCreditConf = customerCreditConfHibernateDao.get(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(customerCreditConf);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    customerCreditConf.setOperationType(operationType);
	    
		return customerCreditConf;	
	}
	
	/**
	 * 根据主键ID,取得客户代垫额度和发货额度配置实例
	 * @param id
	 * @return
	 */
	public CustomerCreditConf _getForEdit(String id)
	{		
	    CustomerCreditConf customerCreditConf = _get(id);
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = customerCreditConf.getOperationType();
	    if(OperationType.READONLY.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotEdit);
	    }

		return customerCreditConf;	
	}
	
	/**
	 * 根据主键ID,取得客户代垫额度和发货额度配置实例副本
	 * @param id
	 * @return
	 */
	public CustomerCreditConf _getEntityCopy(String id)
	{		
	    CustomerCreditConf customerCreditConf = new CustomerCreditConf();
		CustomerCreditConf customerCreditConfOld = this._get(id);
		try
		{
			BeanUtils.copyProperties(customerCreditConf, customerCreditConfOld);
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (InvocationTargetException e)
		{
			e.printStackTrace();
		}
		//customerCreditConf.setConfigid(null); 
		return customerCreditConf;	
	}
	
	/**
	 * 删除  
	 *   
	 * @param customerCreditConf
	 */
	public void _delete(CustomerCreditConf customerCreditConf)
	{
		if (null != advanceService)
			advanceService.preDelete(customerCreditConf);
	
 		LockService.isBoInstanceLocked(customerCreditConf,CustomerCreditConf.class);
		customerCreditConfHibernateDao.remove(customerCreditConf);

		if (null != advanceService)
			advanceService.postDelete(customerCreditConf);
	}

	/**
	 * 根据主键删除
	 * 
	 * @param customerCreditConfId
	 */
	public void _delete(String customerCreditConfId,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(customerCreditConfId))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("configid"));
		CustomerCreditConf customerCreditConf = this.customerCreditConfHibernateDao.load(customerCreditConfId);
		_delete(customerCreditConf);
	}
	
	/**
	 * 删除  
	 *   
	 * @param Set<CustomerCreditConf> customerCreditConfs
	 */
	public void _deletes(Set<CustomerCreditConf> customerCreditConfs,BusinessObject businessObject)
	{
		if (null == customerCreditConfs || customerCreditConfs.size() < 1)
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoText());
		Iterator<CustomerCreditConf> it = customerCreditConfs.iterator();
		while (it.hasNext())
		{
			CustomerCreditConf customerCreditConf = (CustomerCreditConf) it.next();
			_delete(customerCreditConf);
		}
	}
	
	/**
	 * 根据多个主键(由，分割)删除多个对象
	 * 
	 * @param customerCreditConfIds
	 */
	public void _deletes(String customerCreditConfIds,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(customerCreditConfIds))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("configid"));
		String[] ids = StringUtils.splitString(customerCreditConfIds);
		for (int i = 0; i < ids.length; i++)
		{
			_delete(ids[i],businessObject);
		}
	}
	/**
	 * 提交工作流
	 * 
	 * @param customerCreditConf
	 */
	public void _submitProcess(CustomerCreditConf customerCreditConf
,Set<CustomerCreditProj> deletedCustomerCreditProjectSet	,BusinessObject businessObject)
	{
		String id = customerCreditConf.getConfigid();
		if (StringUtils.isNullBlank(id))
		{
			_save(customerCreditConf);
		}
		else
		{
			_update(customerCreditConf
,deletedCustomerCreditProjectSet			, businessObject);
		}
		String taskId = customerCreditConf.getWorkflowTaskId();
		id = customerCreditConf.getConfigid();
		if (null == taskId || "".equals(taskId))
			WorkflowService.createAndSignalProcessInstance(customerCreditConf, id);
		else
			WorkflowService.signalProcessInstance(customerCreditConf, id, null);
	}

	/**
	 * 保存或更新客户代垫额度和发货额度配置
	 * 保存  
	 *  
	 * @param customerCreditConf
	 */
	public void _update(CustomerCreditConf customerCreditConf
,Set<CustomerCreditProj> deletedCustomerCreditProjSet,BusinessObject businessObject	
	)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(customerCreditConf);
		customerCreditConfHibernateDao.saveOrUpdate(customerCreditConf);
// 删除关联子业务对象数据
if(deletedCustomerCreditProjSet!=null && deletedCustomerCreditProjSet.size()>0)
{
customerCreditProjService._deletes(deletedCustomerCreditProjSet,businessObject);
}		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(customerCreditConf);
	}
	
	/**
	 * 保存  
	 *   
	 * @param customerCreditConf
	 */
	public void _save(CustomerCreditConf customerCreditConf)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(customerCreditConf);
		// 对id赋空值，使之执行insert操作(适应复制创建场景)
		customerCreditConf.setConfigid(null);
       
		Set<CustomerCreditProj> customerCreditProjectSet = customerCreditConf.getCustomerCreditProject();
		Set<CustomerCreditProj> newCustomerCreditProjSet = null;
		if (null != customerCreditProjectSet)
		{
			newCustomerCreditProjSet = new HashSet();
			Iterator<CustomerCreditProj> itCustomerCreditProj = customerCreditProjectSet.iterator();
			while (itCustomerCreditProj.hasNext())
			{
				CustomerCreditProj customerCreditProject = (CustomerCreditProj) itCustomerCreditProj.next();
				customerCreditProject.setConfigprojectid(null);
				newCustomerCreditProjSet.add(customerCreditProject);
			}
		}
		customerCreditConf.setCustomerCreditProject(newCustomerCreditProjSet);
       
       
       
       
       
       
       
       
       
       
       
       
       
       
       
		customerCreditConfHibernateDao.saveOrUpdate(customerCreditConf);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(customerCreditConf);
	}
	
	/**
	 *
	 * 保存  
	 *   
	 * @param customerCreditConf
	 */
	public void _saveOrUpdate(CustomerCreditConf customerCreditConf
,Set<CustomerCreditProj> deletedCustomerCreditProjSet,BusinessObject businessObject		
	)
	{
		if (StringUtils.isNullBlank(customerCreditConf.getConfigid()))
		{	
			_save(customerCreditConf);
		}
		else
		{
			_update(customerCreditConf
,deletedCustomerCreditProjSet
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