/*
 * @(#)BillPayReBankItemServiceGen.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2013年02月27日 16点43分43秒
 *  描　述：创建
 */
package com.infolion.xdss3.billPayReBankItemGen.service;

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
import com.infolion.xdss3.billPayReBankItem.domain.BillPayReBankItem;
import com.infolion.xdss3.billPayReBankItem.service.BillPayReBankItemService;
import com.infolion.xdss3.billPayReBankItem.dao.BillPayReBankItemHibernateDao;

/**
 * <pre>
 * 进口押汇还押汇银行(BillPayReBankItem)服务类
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
public class BillPayReBankItemServiceGen extends BaseService
{
	protected Log log = LogFactory.getFactory().getLog(this.getClass());

	@Autowired
	protected BillPayReBankItemHibernateDao billPayReBankItemHibernateDao;
	
	public void setBillPayReBankItemHibernateDao(BillPayReBankItemHibernateDao billPayReBankItemHibernateDao)
	{
		this.billPayReBankItemHibernateDao = billPayReBankItemHibernateDao;
	}
	
     /**
	 * 注入扩展服务类
	 */
	 @Autowired(required = false)
	 public void setAdvanceService(@Qualifier("billPayReBankItemAdvanceService") AdvanceService advanceService)
	 {
	  	super.setAdvanceService(advanceService);
	 }   
	 
	   
	/**
	 * 根据主键ID,取得进口押汇还押汇银行实例
	 * @param id
	 * @return
	 */
	public BillPayReBankItem _getDetached(String id)
	{		
	    BillPayReBankItem billPayReBankItem = new BillPayReBankItem();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  billPayReBankItem = billPayReBankItemHibernateDao.getDetached(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(billPayReBankItem);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    billPayReBankItem.setOperationType(operationType);
	    
		return billPayReBankItem;	
	}
	
	/**
	 * 根据主键ID,取得进口押汇还押汇银行实例
	 * @param id
	 * @return
	 */
	public BillPayReBankItem _get(String id)
	{		
	    BillPayReBankItem billPayReBankItem = new BillPayReBankItem();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  billPayReBankItem = billPayReBankItemHibernateDao.get(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(billPayReBankItem);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    billPayReBankItem.setOperationType(operationType);
	    
		return billPayReBankItem;	
	}
	
	/**
	 * 根据主键ID,取得进口押汇还押汇银行实例
	 * @param id
	 * @return
	 */
	public BillPayReBankItem _getForEdit(String id)
	{		
	    BillPayReBankItem billPayReBankItem = _get(id);
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = billPayReBankItem.getOperationType();
	    if(OperationType.READONLY.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotEdit);
	    }

		return billPayReBankItem;	
	}
	
	/**
	 * 根据主键ID,取得进口押汇还押汇银行实例副本
	 * @param id
	 * @return
	 */
	public BillPayReBankItem _getEntityCopy(String id)
	{		
	    BillPayReBankItem billPayReBankItem = new BillPayReBankItem();
		BillPayReBankItem billPayReBankItemOld = this._get(id);
		try
		{
			BeanUtils.copyProperties(billPayReBankItem, billPayReBankItemOld);
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (InvocationTargetException e)
		{
			e.printStackTrace();
		}
		//billPayReBankItem.setBankitemid(null); 
		return billPayReBankItem;	
	}
	
	/**
	 * 删除  
	 *   
	 * @param billPayReBankItem
	 */
	public void _delete(BillPayReBankItem billPayReBankItem)
	{
		if (null != advanceService)
			advanceService.preDelete(billPayReBankItem);
	
 		LockService.isBoInstanceLocked(billPayReBankItem,BillPayReBankItem.class);
		billPayReBankItemHibernateDao.remove(billPayReBankItem);

		if (null != advanceService)
			advanceService.postDelete(billPayReBankItem);
	}

	/**
	 * 根据主键删除
	 * 
	 * @param billPayReBankItemId
	 */
	public void _delete(String billPayReBankItemId,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(billPayReBankItemId))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("bankitemid"));
		BillPayReBankItem billPayReBankItem = this.billPayReBankItemHibernateDao.load(billPayReBankItemId);
		_delete(billPayReBankItem);
	}
	
	/**
	 * 删除  
	 *   
	 * @param Set<BillPayReBankItem> billPayReBankItems
	 */
	public void _deletes(Set<BillPayReBankItem> billPayReBankItems,BusinessObject businessObject)
	{
		if (null == billPayReBankItems || billPayReBankItems.size() < 1)
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoText());
		Iterator<BillPayReBankItem> it = billPayReBankItems.iterator();
		while (it.hasNext())
		{
			BillPayReBankItem billPayReBankItem = (BillPayReBankItem) it.next();
			_delete(billPayReBankItem);
		}
	}
	
	/**
	 * 根据多个主键(由，分割)删除多个对象
	 * 
	 * @param billPayReBankItemIds
	 */
	public void _deletes(String billPayReBankItemIds,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(billPayReBankItemIds))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("bankitemid"));
		String[] ids = StringUtils.splitString(billPayReBankItemIds);
		for (int i = 0; i < ids.length; i++)
		{
			_delete(ids[i],businessObject);
		}
	}
	/**
	 * 提交工作流
	 * 
	 * @param billPayReBankItem
	 */
	public void _submitProcess(BillPayReBankItem billPayReBankItem
	,BusinessObject businessObject)
	{
		String id = billPayReBankItem.getBankitemid();
        /**
		if (StringUtils.isNullBlank(id))
		{
			_save(billPayReBankItem);
		}
		else
		{
			_update(billPayReBankItem
			, businessObject);
		}**/
		
		String taskId = billPayReBankItem.getWorkflowTaskId();
		id = billPayReBankItem.getBankitemid();
		if (null == taskId || "".equals(taskId))
			WorkflowService.createAndSignalProcessInstance(billPayReBankItem, id);
		else
			WorkflowService.signalProcessInstance(billPayReBankItem, id, null);
	}

	/**
	 * 保存或更新进口押汇还押汇银行
	 * 保存  
	 *  
	 * @param billPayReBankItem
	 */
	public void _update(BillPayReBankItem billPayReBankItem
,BusinessObject businessObject	
	)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(billPayReBankItem);
		billPayReBankItemHibernateDao.saveOrUpdate(billPayReBankItem);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(billPayReBankItem);
	}
	
	/**
	 * 保存  
	 *   
	 * @param billPayReBankItem
	 */
	public void _save(BillPayReBankItem billPayReBankItem)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(billPayReBankItem);
		// 对id赋空值，使之执行insert操作(适应复制创建场景)
		billPayReBankItem.setBankitemid(null);
                                            		billPayReBankItemHibernateDao.saveOrUpdate(billPayReBankItem);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(billPayReBankItem);
	}
	
	/**
	 *
	 * 保存  
	 *   
	 * @param billPayReBankItem
	 */
	public void _saveOrUpdate(BillPayReBankItem billPayReBankItem
,BusinessObject businessObject		
	)
	{
		if (StringUtils.isNullBlank(billPayReBankItem.getBankitemid()))
		{	
			_save(billPayReBankItem);
		}
		else
		{
			_update(billPayReBankItem
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