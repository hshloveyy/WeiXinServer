/*
 * @(#)BillPurBankItemServiceGen.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年12月15日 16点10分18秒
 *  描　述：创建
 */
package com.infolion.xdss3.billPurBankItemGen.service;

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
import com.infolion.xdss3.billPurBankItem.domain.BillPurBankItem;
import com.infolion.xdss3.billPurBankItem.service.BillPurBankItemService;
import com.infolion.xdss3.billPurBankItem.dao.BillPurBankItemHibernateDao;

/**
 * <pre>
 * 押汇银行(BillPurBankItem)服务类
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
public class BillPurBankItemServiceGen extends BaseService
{
	protected Log log = LogFactory.getFactory().getLog(this.getClass());

	@Autowired
	protected BillPurBankItemHibernateDao billPurBankItemHibernateDao;
	
	public void setBillPurBankItemHibernateDao(BillPurBankItemHibernateDao billPurBankItemHibernateDao)
	{
		this.billPurBankItemHibernateDao = billPurBankItemHibernateDao;
	}
	
     /**
	 * 注入扩展服务类
	 */
	 @Autowired(required = false)
	 public void setAdvanceService(@Qualifier("billPurBankItemAdvanceService") AdvanceService advanceService)
	 {
	  	super.setAdvanceService(advanceService);
	 }   
	 
	   
	/**
	 * 根据主键ID,取得押汇银行实例
	 * @param id
	 * @return
	 */
	public BillPurBankItem _getDetached(String id)
	{		
	    BillPurBankItem billPurBankItem = new BillPurBankItem();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  billPurBankItem = billPurBankItemHibernateDao.getDetached(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(billPurBankItem);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    billPurBankItem.setOperationType(operationType);
	    
		return billPurBankItem;	
	}
	
	/**
	 * 根据主键ID,取得押汇银行实例
	 * @param id
	 * @return
	 */
	public BillPurBankItem _get(String id)
	{		
	    BillPurBankItem billPurBankItem = new BillPurBankItem();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  billPurBankItem = billPurBankItemHibernateDao.get(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(billPurBankItem);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    billPurBankItem.setOperationType(operationType);
	    
		return billPurBankItem;	
	}
	
	/**
	 * 根据主键ID,取得押汇银行实例
	 * @param id
	 * @return
	 */
	public BillPurBankItem _getForEdit(String id)
	{		
	    BillPurBankItem billPurBankItem = _get(id);
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = billPurBankItem.getOperationType();
	    if(OperationType.READONLY.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotEdit);
	    }

		return billPurBankItem;	
	}
	
	/**
	 * 根据主键ID,取得押汇银行实例副本
	 * @param id
	 * @return
	 */
	public BillPurBankItem _getEntityCopy(String id)
	{		
	    BillPurBankItem billPurBankItem = new BillPurBankItem();
		BillPurBankItem billPurBankItemOld = this._get(id);
		try
		{
			BeanUtils.copyProperties(billPurBankItem, billPurBankItemOld);
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (InvocationTargetException e)
		{
			e.printStackTrace();
		}
		//billPurBankItem.setBankitemid(null); 
		return billPurBankItem;	
	}
	
	/**
	 * 删除  
	 *   
	 * @param billPurBankItem
	 */
	public void _delete(BillPurBankItem billPurBankItem)
	{
		if (null != advanceService)
			advanceService.preDelete(billPurBankItem);
	
 		LockService.isBoInstanceLocked(billPurBankItem,BillPurBankItem.class);
		billPurBankItemHibernateDao.remove(billPurBankItem);

		if (null != advanceService)
			advanceService.postDelete(billPurBankItem);
	}

	/**
	 * 根据主键删除
	 * 
	 * @param billPurBankItemId
	 */
	public void _delete(String billPurBankItemId,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(billPurBankItemId))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("bankitemid"));
		BillPurBankItem billPurBankItem = this.billPurBankItemHibernateDao.load(billPurBankItemId);
		_delete(billPurBankItem);
	}
	
	/**
	 * 删除  
	 *   
	 * @param Set<BillPurBankItem> billPurBankItems
	 */
	public void _deletes(Set<BillPurBankItem> billPurBankItems,BusinessObject businessObject)
	{
		if (null == billPurBankItems || billPurBankItems.size() < 1)
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoText());
		Iterator<BillPurBankItem> it = billPurBankItems.iterator();
		while (it.hasNext())
		{
			BillPurBankItem billPurBankItem = (BillPurBankItem) it.next();
			_delete(billPurBankItem);
		}
	}
	
	/**
	 * 根据多个主键(由，分割)删除多个对象
	 * 
	 * @param billPurBankItemIds
	 */
	public void _deletes(String billPurBankItemIds,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(billPurBankItemIds))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("bankitemid"));
		String[] ids = StringUtils.splitString(billPurBankItemIds);
		for (int i = 0; i < ids.length; i++)
		{
			_delete(ids[i],businessObject);
		}
	}
	/**
	 * 提交工作流
	 * 
	 * @param billPurBankItem
	 */
	public void _submitProcess(BillPurBankItem billPurBankItem
	,BusinessObject businessObject)
	{
		String id = billPurBankItem.getBankitemid();
        /**
		if (StringUtils.isNullBlank(id))
		{
			_save(billPurBankItem);
		}
		else
		{
			_update(billPurBankItem
			, businessObject);
		}**/
		
		String taskId = billPurBankItem.getWorkflowTaskId();
		id = billPurBankItem.getBankitemid();
		if (null == taskId || "".equals(taskId))
			WorkflowService.createAndSignalProcessInstance(billPurBankItem, id);
		else
			WorkflowService.signalProcessInstance(billPurBankItem, id, null);
	}

	/**
	 * 保存或更新押汇银行
	 * 保存  
	 *  
	 * @param billPurBankItem
	 */
	public void _update(BillPurBankItem billPurBankItem
,BusinessObject businessObject	
	)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(billPurBankItem);
		billPurBankItemHibernateDao.saveOrUpdate(billPurBankItem);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(billPurBankItem);
	}
	
	/**
	 * 保存  
	 *   
	 * @param billPurBankItem
	 */
	public void _save(BillPurBankItem billPurBankItem)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(billPurBankItem);
		// 对id赋空值，使之执行insert操作(适应复制创建场景)
		billPurBankItem.setBankitemid(null);
                      		billPurBankItemHibernateDao.saveOrUpdate(billPurBankItem);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(billPurBankItem);
	}
	
	/**
	 *
	 * 保存  
	 *   
	 * @param billPurBankItem
	 */
	public void _saveOrUpdate(BillPurBankItem billPurBankItem
,BusinessObject businessObject		
	)
	{
		if (StringUtils.isNullBlank(billPurBankItem.getBankitemid()))
		{	
			_save(billPurBankItem);
		}
		else
		{
			_update(billPurBankItem
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