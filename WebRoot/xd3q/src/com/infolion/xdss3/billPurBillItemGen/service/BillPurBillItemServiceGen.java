/*
 * @(#)BillPurBillItemServiceGen.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年12月15日 16点10分18秒
 *  描　述：创建
 */
package com.infolion.xdss3.billPurBillItemGen.service;

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
import com.infolion.xdss3.billPurBillItem.domain.BillPurBillItem;
import com.infolion.xdss3.billPurBillItem.service.BillPurBillItemService;
import com.infolion.xdss3.billPurBillItem.dao.BillPurBillItemHibernateDao;

/**
 * <pre>
 * 发票(BillPurBillItem)服务类
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
public class BillPurBillItemServiceGen extends BaseService
{
	protected Log log = LogFactory.getFactory().getLog(this.getClass());

	@Autowired
	protected BillPurBillItemHibernateDao billPurBillItemHibernateDao;
	
	public void setBillPurBillItemHibernateDao(BillPurBillItemHibernateDao billPurBillItemHibernateDao)
	{
		this.billPurBillItemHibernateDao = billPurBillItemHibernateDao;
	}
	
     /**
	 * 注入扩展服务类
	 */
	 @Autowired(required = false)
	 public void setAdvanceService(@Qualifier("billPurBillItemAdvanceService") AdvanceService advanceService)
	 {
	  	super.setAdvanceService(advanceService);
	 }   
	 
	   
	/**
	 * 根据主键ID,取得发票实例
	 * @param id
	 * @return
	 */
	public BillPurBillItem _getDetached(String id)
	{		
	    BillPurBillItem billPurBillItem = new BillPurBillItem();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  billPurBillItem = billPurBillItemHibernateDao.getDetached(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(billPurBillItem);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    billPurBillItem.setOperationType(operationType);
	    
		return billPurBillItem;	
	}
	
	/**
	 * 根据主键ID,取得发票实例
	 * @param id
	 * @return
	 */
	public BillPurBillItem _get(String id)
	{		
	    BillPurBillItem billPurBillItem = new BillPurBillItem();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  billPurBillItem = billPurBillItemHibernateDao.get(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(billPurBillItem);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    billPurBillItem.setOperationType(operationType);
	    
		return billPurBillItem;	
	}
	
	/**
	 * 根据主键ID,取得发票实例
	 * @param id
	 * @return
	 */
	public BillPurBillItem _getForEdit(String id)
	{		
	    BillPurBillItem billPurBillItem = _get(id);
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = billPurBillItem.getOperationType();
	    if(OperationType.READONLY.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotEdit);
	    }

		return billPurBillItem;	
	}
	
	/**
	 * 根据主键ID,取得发票实例副本
	 * @param id
	 * @return
	 */
	public BillPurBillItem _getEntityCopy(String id)
	{		
	    BillPurBillItem billPurBillItem = new BillPurBillItem();
		BillPurBillItem billPurBillItemOld = this._get(id);
		try
		{
			BeanUtils.copyProperties(billPurBillItem, billPurBillItemOld);
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (InvocationTargetException e)
		{
			e.printStackTrace();
		}
		//billPurBillItem.setBillitemid(null); 
		return billPurBillItem;	
	}
	
	/**
	 * 删除  
	 *   
	 * @param billPurBillItem
	 */
	public void _delete(BillPurBillItem billPurBillItem)
	{
		if (null != advanceService)
			advanceService.preDelete(billPurBillItem);
	
 		LockService.isBoInstanceLocked(billPurBillItem,BillPurBillItem.class);
		billPurBillItemHibernateDao.remove(billPurBillItem);

		if (null != advanceService)
			advanceService.postDelete(billPurBillItem);
	}

	/**
	 * 根据主键删除
	 * 
	 * @param billPurBillItemId
	 */
	public void _delete(String billPurBillItemId,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(billPurBillItemId))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("billitemid"));
		BillPurBillItem billPurBillItem = this.billPurBillItemHibernateDao.load(billPurBillItemId);
		_delete(billPurBillItem);
	}
	
	/**
	 * 删除  
	 *   
	 * @param Set<BillPurBillItem> billPurBillItems
	 */
	public void _deletes(Set<BillPurBillItem> billPurBillItems,BusinessObject businessObject)
	{
		if (null == billPurBillItems || billPurBillItems.size() < 1)
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoText());
		Iterator<BillPurBillItem> it = billPurBillItems.iterator();
		while (it.hasNext())
		{
			BillPurBillItem billPurBillItem = (BillPurBillItem) it.next();
			_delete(billPurBillItem);
		}
	}
	
	/**
	 * 根据多个主键(由，分割)删除多个对象
	 * 
	 * @param billPurBillItemIds
	 */
	public void _deletes(String billPurBillItemIds,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(billPurBillItemIds))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("billitemid"));
		String[] ids = StringUtils.splitString(billPurBillItemIds);
		for (int i = 0; i < ids.length; i++)
		{
			_delete(ids[i],businessObject);
		}
	}
	/**
	 * 提交工作流
	 * 
	 * @param billPurBillItem
	 */
	public void _submitProcess(BillPurBillItem billPurBillItem
	,BusinessObject businessObject)
	{
		String id = billPurBillItem.getBillitemid();
        /**
		if (StringUtils.isNullBlank(id))
		{
			_save(billPurBillItem);
		}
		else
		{
			_update(billPurBillItem
			, businessObject);
		}**/
		
		String taskId = billPurBillItem.getWorkflowTaskId();
		id = billPurBillItem.getBillitemid();
		if (null == taskId || "".equals(taskId))
			WorkflowService.createAndSignalProcessInstance(billPurBillItem, id);
		else
			WorkflowService.signalProcessInstance(billPurBillItem, id, null);
	}

	/**
	 * 保存或更新发票
	 * 保存  
	 *  
	 * @param billPurBillItem
	 */
	public void _update(BillPurBillItem billPurBillItem
,BusinessObject businessObject	
	)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(billPurBillItem);
		billPurBillItemHibernateDao.saveOrUpdate(billPurBillItem);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(billPurBillItem);
	}
	
	/**
	 * 保存  
	 *   
	 * @param billPurBillItem
	 */
	public void _save(BillPurBillItem billPurBillItem)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(billPurBillItem);
		// 对id赋空值，使之执行insert操作(适应复制创建场景)
		billPurBillItem.setBillitemid(null);
                          		billPurBillItemHibernateDao.saveOrUpdate(billPurBillItem);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(billPurBillItem);
	}
	
	/**
	 *
	 * 保存  
	 *   
	 * @param billPurBillItem
	 */
	public void _saveOrUpdate(BillPurBillItem billPurBillItem
,BusinessObject businessObject		
	)
	{
		if (StringUtils.isNullBlank(billPurBillItem.getBillitemid()))
		{	
			_save(billPurBillItem);
		}
		else
		{
			_update(billPurBillItem
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