/*
 * @(#)BillPurReBankItemServiceGen.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年12月15日 16点10分19秒
 *  描　述：创建
 */
package com.infolion.xdss3.billPurReBankItemGen.service;

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
import com.infolion.xdss3.billPurReBankItem.domain.BillPurReBankItem;
import com.infolion.xdss3.billPurReBankItem.service.BillPurReBankItemService;
import com.infolion.xdss3.billPurReBankItem.dao.BillPurReBankItemHibernateDao;

/**
 * <pre>
 * 还押汇银行(BillPurReBankItem)服务类
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
public class BillPurReBankItemServiceGen extends BaseService
{
	protected Log log = LogFactory.getFactory().getLog(this.getClass());

	@Autowired
	protected BillPurReBankItemHibernateDao billPurReBankItemHibernateDao;
	
	public void setBillPurReBankItemHibernateDao(BillPurReBankItemHibernateDao billPurReBankItemHibernateDao)
	{
		this.billPurReBankItemHibernateDao = billPurReBankItemHibernateDao;
	}
	
     /**
	 * 注入扩展服务类
	 */
	 @Autowired(required = false)
	 public void setAdvanceService(@Qualifier("billPurReBankItemAdvanceService") AdvanceService advanceService)
	 {
	  	super.setAdvanceService(advanceService);
	 }   
	 
	   
	/**
	 * 根据主键ID,取得还押汇银行实例
	 * @param id
	 * @return
	 */
	public BillPurReBankItem _getDetached(String id)
	{		
	    BillPurReBankItem billPurReBankItem = new BillPurReBankItem();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  billPurReBankItem = billPurReBankItemHibernateDao.getDetached(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(billPurReBankItem);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    billPurReBankItem.setOperationType(operationType);
	    
		return billPurReBankItem;	
	}
	
	/**
	 * 根据主键ID,取得还押汇银行实例
	 * @param id
	 * @return
	 */
	public BillPurReBankItem _get(String id)
	{		
	    BillPurReBankItem billPurReBankItem = new BillPurReBankItem();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  billPurReBankItem = billPurReBankItemHibernateDao.get(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(billPurReBankItem);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    billPurReBankItem.setOperationType(operationType);
	    
		return billPurReBankItem;	
	}
	
	/**
	 * 根据主键ID,取得还押汇银行实例
	 * @param id
	 * @return
	 */
	public BillPurReBankItem _getForEdit(String id)
	{		
	    BillPurReBankItem billPurReBankItem = _get(id);
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = billPurReBankItem.getOperationType();
	    if(OperationType.READONLY.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotEdit);
	    }

		return billPurReBankItem;	
	}
	
	/**
	 * 根据主键ID,取得还押汇银行实例副本
	 * @param id
	 * @return
	 */
	public BillPurReBankItem _getEntityCopy(String id)
	{		
	    BillPurReBankItem billPurReBankItem = new BillPurReBankItem();
		BillPurReBankItem billPurReBankItemOld = this._get(id);
		try
		{
			BeanUtils.copyProperties(billPurReBankItem, billPurReBankItemOld);
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (InvocationTargetException e)
		{
			e.printStackTrace();
		}
		//billPurReBankItem.setBankitemid(null); 
		return billPurReBankItem;	
	}
	
	/**
	 * 删除  
	 *   
	 * @param billPurReBankItem
	 */
	public void _delete(BillPurReBankItem billPurReBankItem)
	{
		if (null != advanceService)
			advanceService.preDelete(billPurReBankItem);
	
 		LockService.isBoInstanceLocked(billPurReBankItem,BillPurReBankItem.class);
		billPurReBankItemHibernateDao.remove(billPurReBankItem);

		if (null != advanceService)
			advanceService.postDelete(billPurReBankItem);
	}

	/**
	 * 根据主键删除
	 * 
	 * @param billPurReBankItemId
	 */
	public void _delete(String billPurReBankItemId,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(billPurReBankItemId))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("bankitemid"));
		BillPurReBankItem billPurReBankItem = this.billPurReBankItemHibernateDao.load(billPurReBankItemId);
		_delete(billPurReBankItem);
	}
	
	/**
	 * 删除  
	 *   
	 * @param Set<BillPurReBankItem> billPurReBankItems
	 */
	public void _deletes(Set<BillPurReBankItem> billPurReBankItems,BusinessObject businessObject)
	{
		if (null == billPurReBankItems || billPurReBankItems.size() < 1)
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoText());
		Iterator<BillPurReBankItem> it = billPurReBankItems.iterator();
		while (it.hasNext())
		{
			BillPurReBankItem billPurReBankItem = (BillPurReBankItem) it.next();
			_delete(billPurReBankItem);
		}
	}
	
	/**
	 * 根据多个主键(由，分割)删除多个对象
	 * 
	 * @param billPurReBankItemIds
	 */
	public void _deletes(String billPurReBankItemIds,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(billPurReBankItemIds))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("bankitemid"));
		String[] ids = StringUtils.splitString(billPurReBankItemIds);
		for (int i = 0; i < ids.length; i++)
		{
			_delete(ids[i],businessObject);
		}
	}
	/**
	 * 提交工作流
	 * 
	 * @param billPurReBankItem
	 */
	public void _submitProcess(BillPurReBankItem billPurReBankItem
	,BusinessObject businessObject)
	{
		String id = billPurReBankItem.getBankitemid();
        /**
		if (StringUtils.isNullBlank(id))
		{
			_save(billPurReBankItem);
		}
		else
		{
			_update(billPurReBankItem
			, businessObject);
		}**/
		
		String taskId = billPurReBankItem.getWorkflowTaskId();
		id = billPurReBankItem.getBankitemid();
		if (null == taskId || "".equals(taskId))
			WorkflowService.createAndSignalProcessInstance(billPurReBankItem, id);
		else
			WorkflowService.signalProcessInstance(billPurReBankItem, id, null);
	}

	/**
	 * 保存或更新还押汇银行
	 * 保存  
	 *  
	 * @param billPurReBankItem
	 */
	public void _update(BillPurReBankItem billPurReBankItem
,BusinessObject businessObject	
	)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(billPurReBankItem);
		billPurReBankItemHibernateDao.saveOrUpdate(billPurReBankItem);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(billPurReBankItem);
	}
	
	/**
	 * 保存  
	 *   
	 * @param billPurReBankItem
	 */
	public void _save(BillPurReBankItem billPurReBankItem)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(billPurReBankItem);
		// 对id赋空值，使之执行insert操作(适应复制创建场景)
		billPurReBankItem.setBankitemid(null);
                        		billPurReBankItemHibernateDao.saveOrUpdate(billPurReBankItem);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(billPurReBankItem);
	}
	
	/**
	 *
	 * 保存  
	 *   
	 * @param billPurReBankItem
	 */
	public void _saveOrUpdate(BillPurReBankItem billPurReBankItem
,BusinessObject businessObject		
	)
	{
		if (StringUtils.isNullBlank(billPurReBankItem.getBankitemid()))
		{	
			_save(billPurReBankItem);
		}
		else
		{
			_update(billPurReBankItem
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