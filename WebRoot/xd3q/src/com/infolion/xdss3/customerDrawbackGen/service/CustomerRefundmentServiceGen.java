/*
 * @(#)CustomerRefundmentServiceGen.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年07月07日 17点57分41秒
 *  描　述：创建
 */
package com.infolion.xdss3.customerDrawbackGen.service;

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
import com.infolion.xdss3.customerDrawback.domain.CustomerRefundment;
import com.infolion.xdss3.customerDrawback.service.CustomerRefundmentService;
import com.infolion.xdss3.customerDrawback.dao.CustomerRefundmentHibernateDao;
          
import com.infolion.xdss3.customerDrawback.domain.CustomerDbBankItem;
import com.infolion.xdss3.customerDrawback.service.CustomerDbBankItemService;
          
import com.infolion.xdss3.customerDrawback.domain.CustomerRefundItem;
import com.infolion.xdss3.customerDrawback.service.CustomerRefundItemService;
import com.infolion.platform.dpframework.uicomponent.attachement.dao.AttachementJdbcDao;
import com.infolion.platform.dpframework.uicomponent.attachement.domain.Attachement;
import com.infolion.platform.dpframework.uicomponent.attachement.service.AttachementService;
          

/**
 * <pre>
 * 客户退款(CustomerRefundment)服务类
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
public class CustomerRefundmentServiceGen extends BaseService
{
	protected Log log = LogFactory.getFactory().getLog(this.getClass());

	@Autowired
	protected CustomerRefundmentHibernateDao customerRefundmentHibernateDao;
	
	public void setCustomerRefundmentHibernateDao(CustomerRefundmentHibernateDao customerRefundmentHibernateDao)
	{
		this.customerRefundmentHibernateDao = customerRefundmentHibernateDao;
	}
	
     /**
	 * 注入扩展服务类
	 */
	 @Autowired(required = false)
	 public void setAdvanceService(@Qualifier("customerRefundmentAdvanceService") AdvanceService advanceService)
	 {
	  	super.setAdvanceService(advanceService);
	 }   
	 
	@Autowired
	private AttachementService attachementService;
	
	public void setAttachementService(AttachementService attachementService) {
		this.attachementService = attachementService;
	}

	@Autowired
	protected CustomerDbBankItemService customerDbBankItemService;
	
	public void setCustomerDbBankItemService(CustomerDbBankItemService customerDbBankItemService)
	{
		this.customerDbBankItemService = customerDbBankItemService;
	}
          

	@Autowired
	protected CustomerRefundItemService customerRefundItemService;
	
	public void setCustomerRefundItemService(CustomerRefundItemService customerRefundItemService)
	{
		this.customerRefundItemService = customerRefundItemService;
	}

          
          


	@Autowired
	protected AttachementJdbcDao attachementJdbcDao;
	
	public void setAttachementJdbcDao(AttachementJdbcDao attachementJdbcDao)
	{
		this.attachementJdbcDao = attachementJdbcDao;
	}
	   
	/**
	 * 根据主键ID,取得客户退款实例
	 * @param id
	 * @return
	 */
	public CustomerRefundment _getDetached(String id)
	{		
	    CustomerRefundment customerRefundment = new CustomerRefundment();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  customerRefundment = customerRefundmentHibernateDao.getDetached(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(customerRefundment);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    customerRefundment.setOperationType(operationType);
	    
		return customerRefundment;	
	}
	
	/**
	 * 根据主键ID,取得客户退款实例
	 * @param id
	 * @return
	 */
	public CustomerRefundment _get(String id)
	{		
	    CustomerRefundment customerRefundment = new CustomerRefundment();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  customerRefundment = customerRefundmentHibernateDao.get(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(customerRefundment);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    customerRefundment.setOperationType(operationType);
	    
		return customerRefundment;	
	}
	
	/**
	 * 根据主键ID,取得客户退款实例
	 * @param id
	 * @return
	 */
	public CustomerRefundment _getForEdit(String id)
	{		
	    CustomerRefundment customerRefundment = _get(id);
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = customerRefundment.getOperationType();
	    if(OperationType.READONLY.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotEdit);
	    }

		return customerRefundment;	
	}
	
	/**
	 * 根据主键ID,取得客户退款实例副本
	 * @param id
	 * @return
	 */
	public CustomerRefundment _getEntityCopy(String id)
	{		
	    CustomerRefundment customerRefundment = new CustomerRefundment();
		CustomerRefundment customerRefundmentOld = this._get(id);
		try
		{
			BeanUtils.copyProperties(customerRefundment, customerRefundmentOld);
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (InvocationTargetException e)
		{
			e.printStackTrace();
		}
		customerRefundment.setRefundmentno(null); 
		//customerRefundment.setRefundmentid(null); 
		customerRefundment.setProcessstate(" ");
		return customerRefundment;	
	}
	
	/**
	 * 删除  
	 *   
	 * @param customerRefundment
	 */
	public void _delete(CustomerRefundment customerRefundment)
	{
		if (null != advanceService)
			advanceService.preDelete(customerRefundment);
	
		//流程状态
		String processState =customerRefundment.getProcessstate();
		if(!StringUtils.isNullBlank(processState))
		{
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.CannotDeleteApproveData);
		}
 		LockService.isBoInstanceLocked(customerRefundment,CustomerRefundment.class);
		customerRefundmentHibernateDao.remove(customerRefundment);
//删除业务附件
String refundmentid = customerRefundment.getRefundmentid();
attachementService.deleteByBusinessId(refundmentid);
		if (null != advanceService)
			advanceService.postDelete(customerRefundment);
	}

	/**
	 * 根据主键删除
	 * 
	 * @param customerRefundmentId
	 */
	public void _delete(String customerRefundmentId,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(customerRefundmentId))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("refundmentid"));
		CustomerRefundment customerRefundment = this.customerRefundmentHibernateDao.load(customerRefundmentId);
		_delete(customerRefundment);
	}
	
	/**
	 * 删除  
	 *   
	 * @param Set<CustomerRefundment> customerRefundments
	 */
	public void _deletes(Set<CustomerRefundment> customerRefundments,BusinessObject businessObject)
	{
		if (null == customerRefundments || customerRefundments.size() < 1)
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoText());
		Iterator<CustomerRefundment> it = customerRefundments.iterator();
		while (it.hasNext())
		{
			CustomerRefundment customerRefundment = (CustomerRefundment) it.next();
			_delete(customerRefundment);
		}
	}
	
	/**
	 * 根据多个主键(由，分割)删除多个对象
	 * 
	 * @param customerRefundmentIds
	 */
	public void _deletes(String customerRefundmentIds,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(customerRefundmentIds))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("refundmentid"));
		String[] ids = StringUtils.splitString(customerRefundmentIds);
		for (int i = 0; i < ids.length; i++)
		{
			_delete(ids[i],businessObject);
		}
	}
	/**
	 * 提交工作流
	 * 
	 * @param customerRefundment
	 */
	public void _submitProcess(CustomerRefundment customerRefundment,
			Set<CustomerDbBankItem> deletedCustomerDbBankItemSet,
			Set<CustomerRefundItem> deletedCustomerRefundItemSet,
			BusinessObject businessObject) {
		String id = customerRefundment.getRefundmentid();
		/**
		 * if (StringUtils.isNullBlank(id)) { _save(customerRefundment); } else
		 * { _update(customerRefundment ,deletedCustomerDbBankItemSet
		 * ,deletedCustomerRefundItemSet , businessObject); }
		 **/

		String taskId = customerRefundment.getWorkflowTaskId();
		id = customerRefundment.getRefundmentid();
		if (null == taskId || "".equals(taskId))
			WorkflowService.createAndSignalProcessInstance(customerRefundment,
					id);
		else
			WorkflowService.signalProcessInstance(customerRefundment, id, null);
	}

	/**
	 * 保存或更新客户退款
	 * 保存  
	 *  
	 * @param customerRefundment
	 */
	public void _update(CustomerRefundment customerRefundment
,Set<CustomerDbBankItem> deletedCustomerDbBankItemSet
,Set<CustomerRefundItem> deletedCustomerRefundItemSet,BusinessObject businessObject	
	)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(customerRefundment);
		customerRefundmentHibernateDao.saveOrUpdate(customerRefundment);
// 删除关联子业务对象数据
if(deletedCustomerDbBankItemSet!=null && deletedCustomerDbBankItemSet.size()>0)
{
customerDbBankItemService._deletes(deletedCustomerDbBankItemSet,businessObject);
}
// 删除关联子业务对象数据
if(deletedCustomerRefundItemSet!=null && deletedCustomerRefundItemSet.size()>0)
{
customerRefundItemService._deletes(deletedCustomerRefundItemSet,businessObject);
}		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(customerRefundment);
	}
	
	/**
	 * 保存  
	 *   
	 * @param customerRefundment
	 */
	public void _save(CustomerRefundment customerRefundment)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(customerRefundment);
		// 对id赋空值，使之执行insert操作(适应复制创建场景)
		customerRefundment.setRefundmentid(null);
       
		Set<CustomerDbBankItem> customerDbBankItemSet = customerRefundment.getCustomerDbBankItem();
		Set<CustomerDbBankItem> newCustomerDbBankItemSet = null;
		if (null != customerDbBankItemSet)
		{
			newCustomerDbBankItemSet = new HashSet();
			Iterator<CustomerDbBankItem> itCustomerDbBankItem = customerDbBankItemSet.iterator();
			while (itCustomerDbBankItem.hasNext())
			{
				CustomerDbBankItem customerDbBankItem = (CustomerDbBankItem) itCustomerDbBankItem.next();
				customerDbBankItem.setRefundbankitemid(null);
				newCustomerDbBankItemSet.add(customerDbBankItem);
			}
		}
		customerRefundment.setCustomerDbBankItem(newCustomerDbBankItemSet);
     
       
     
		Set<CustomerRefundItem> customerRefundItemSet = customerRefundment.getCustomerRefundItem();
		Set<CustomerRefundItem> newCustomerRefundItemSet = null;
		if (null != customerRefundItemSet)
		{
			newCustomerRefundItemSet = new HashSet();
			Iterator<CustomerRefundItem> itCustomerRefundItem = customerRefundItemSet.iterator();
			while (itCustomerRefundItem.hasNext())
			{
				CustomerRefundItem customerRefundItem = (CustomerRefundItem) itCustomerRefundItem.next();
				customerRefundItem.setRefundmentitemid(null);
				newCustomerRefundItemSet.add(customerRefundItem);
			}
		}
		customerRefundment.setCustomerRefundItem(newCustomerRefundItemSet);
       
     
       
     
       
     
       
     
       
     
       
     
       
     
       
     
       
     
       
     
       
     
       
     
       
     
       
     
       
     
       
     
       
     
		customerRefundmentHibernateDao.saveOrUpdate(customerRefundment);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(customerRefundment);
	}
	
	/**
	 *
	 * 保存  
	 *   
	 * @param customerRefundment
	 */
	public void _saveOrUpdate(CustomerRefundment customerRefundment
,Set<CustomerDbBankItem> deletedCustomerDbBankItemSet
,Set<CustomerRefundItem> deletedCustomerRefundItemSet
//取得业务附件，业务ID
,Set<Attachement> attachements,BusinessObject businessObject		
	)
	{
if (StringUtils.isNullBlank(customerRefundment.getRefundmentno()))
{
String refundmentno = NumberService.getNextObjectNumber("CustomerRefundmentNo", customerRefundment);
customerRefundment.setRefundmentno(refundmentno);
}		if (StringUtils.isNullBlank(customerRefundment.getRefundmentid()))
		{	
			_save(customerRefundment);
		}
		else
		{
			_update(customerRefundment
,deletedCustomerDbBankItemSet
,deletedCustomerRefundItemSet
, businessObject);
}
//保存附件业务ID
String refundmentid = customerRefundment.getRefundmentid();
attachementJdbcDao.update(attachements,refundmentid);	}
	
	
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