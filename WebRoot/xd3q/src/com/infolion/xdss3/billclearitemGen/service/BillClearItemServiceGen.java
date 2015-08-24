/*
 * @(#)BillClearItemServiceGen.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年09月16日 09点35分06秒
 *  描　述：创建
 */
package com.infolion.xdss3.billclearitemGen.service;

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
import com.infolion.xdss3.billclearitem.domain.BillClearItem;
import com.infolion.xdss3.billclearitem.service.BillClearItemService;
import com.infolion.xdss3.billclearitem.dao.BillClearItemHibernateDao;

/**
 * <pre>
 * 发票清帐(BillClearItem)服务类
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
public class BillClearItemServiceGen extends BaseService
{
	protected Log log = LogFactory.getFactory().getLog(this.getClass());

	@Autowired
	protected BillClearItemHibernateDao billClearItemHibernateDao;
	
	public void setBillClearItemHibernateDao(BillClearItemHibernateDao billClearItemHibernateDao)
	{
		this.billClearItemHibernateDao = billClearItemHibernateDao;
	}
	
     /**
	 * 注入扩展服务类
	 */
	 @Autowired(required = false)
	 public void setAdvanceService(@Qualifier("billClearItemAdvanceService") AdvanceService advanceService)
	 {
	  	super.setAdvanceService(advanceService);
	 }   
	 
	   
	/**
	 * 根据主键ID,取得发票清帐实例
	 * @param id
	 * @return
	 */
	public BillClearItem _getDetached(String id)
	{		
	    BillClearItem billClearItem = new BillClearItem();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  billClearItem = billClearItemHibernateDao.getDetached(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(billClearItem);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    billClearItem.setOperationType(operationType);
	    
		return billClearItem;	
	}
	
	/**
	 * 根据主键ID,取得发票清帐实例
	 * @param id
	 * @return
	 */
	public BillClearItem _get(String id)
	{		
	    BillClearItem billClearItem = new BillClearItem();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  billClearItem = billClearItemHibernateDao.get(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(billClearItem);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    billClearItem.setOperationType(operationType);
	    
		return billClearItem;	
	}
	
	/**
	 * 根据主键ID,取得发票清帐实例
	 * @param id
	 * @return
	 */
	public BillClearItem _getForEdit(String id)
	{		
	    BillClearItem billClearItem = _get(id);
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = billClearItem.getOperationType();
	    if(OperationType.READONLY.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotEdit);
	    }

		return billClearItem;	
	}
	
	/**
	 * 根据主键ID,取得发票清帐实例副本
	 * @param id
	 * @return
	 */
	public BillClearItem _getEntityCopy(String id)
	{		
	    BillClearItem billClearItem = new BillClearItem();
		BillClearItem billClearItemOld = this._get(id);
		try
		{
			BeanUtils.copyProperties(billClearItem, billClearItemOld);
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (InvocationTargetException e)
		{
			e.printStackTrace();
		}
		//billClearItem.setBillclearitemid(null); 
		return billClearItem;	
	}
	
	/**
	 * 删除  
	 *   
	 * @param billClearItem
	 */
	public void _delete(BillClearItem billClearItem)
	{
		if (null != advanceService)
			advanceService.preDelete(billClearItem);
	
 		LockService.isBoInstanceLocked(billClearItem,BillClearItem.class);
		billClearItemHibernateDao.remove(billClearItem);

		if (null != advanceService)
			advanceService.postDelete(billClearItem);
	}

	/**
	 * 根据主键删除
	 * 
	 * @param billClearItemId
	 */
	public void _delete(String billClearItemId,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(billClearItemId))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("billclearitemid"));
		BillClearItem billClearItem = this.billClearItemHibernateDao.load(billClearItemId);
		_delete(billClearItem);
	}
	
	/**
	 * 删除  
	 *   
	 * @param Set<BillClearItem> billClearItems
	 */
	public void _deletes(Set<BillClearItem> billClearItems,BusinessObject businessObject)
	{
		if (null == billClearItems || billClearItems.size() < 1)
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoText());
		Iterator<BillClearItem> it = billClearItems.iterator();
		while (it.hasNext())
		{
			BillClearItem billClearItem = (BillClearItem) it.next();
			_delete(billClearItem);
		}
	}
	
	/**
	 * 根据多个主键(由，分割)删除多个对象
	 * 
	 * @param billClearItemIds
	 */
	public void _deletes(String billClearItemIds,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(billClearItemIds))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("billclearitemid"));
		String[] ids = StringUtils.splitString(billClearItemIds);
		for (int i = 0; i < ids.length; i++)
		{
			_delete(ids[i],businessObject);
		}
	}
	/**
	 * 提交工作流
	 * 
	 * @param billClearItem
	 */
	public void _submitProcess(BillClearItem billClearItem
	,BusinessObject businessObject)
	{
		String id = billClearItem.getBillclearitemid();
        /**
		if (StringUtils.isNullBlank(id))
		{
			_save(billClearItem);
		}
		else
		{
			_update(billClearItem
			, businessObject);
		}**/
		
		String taskId = billClearItem.getWorkflowTaskId();
		id = billClearItem.getBillclearitemid();
		if (null == taskId || "".equals(taskId))
			WorkflowService.createAndSignalProcessInstance(billClearItem, id);
		else
			WorkflowService.signalProcessInstance(billClearItem, id, null);
	}

	/**
	 * 保存或更新发票清帐
	 * 保存  
	 *  
	 * @param billClearItem
	 */
	public void _update(BillClearItem billClearItem
,BusinessObject businessObject	
	)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(billClearItem);
		billClearItemHibernateDao.saveOrUpdate(billClearItem);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(billClearItem);
	}
	
	/**
	 * 保存  
	 *   
	 * @param billClearItem
	 */
	public void _save(BillClearItem billClearItem)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(billClearItem);
		// 对id赋空值，使之执行insert操作(适应复制创建场景)
		billClearItem.setBillclearitemid(null);
                                          		billClearItemHibernateDao.saveOrUpdate(billClearItem);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(billClearItem);
	}
	
	/**
	 *
	 * 保存  
	 *   
	 * @param billClearItem
	 */
	public void _saveOrUpdate(BillClearItem billClearItem
,BusinessObject businessObject		
	)
	{
		if (StringUtils.isNullBlank(billClearItem.getBillclearitemid()))
		{	
			_save(billClearItem);
		}
		else
		{
			_update(billClearItem
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