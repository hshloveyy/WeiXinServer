/*
 * @(#)VoucherItemServiceGen.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年07月27日 07点47分03秒
 *  描　述：创建
 */
package com.infolion.xdss3.voucheritemGen.service;

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
import com.infolion.xdss3.voucheritem.domain.VoucherItem;
import com.infolion.xdss3.voucheritem.service.VoucherItemService;
import com.infolion.xdss3.voucheritem.dao.VoucherItemHibernateDao;

/**
 * <pre>
 * 凭证预览明细(VoucherItem)服务类
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
public class VoucherItemServiceGen extends BaseService
{
	protected Log log = LogFactory.getFactory().getLog(this.getClass());

	@Autowired
	protected VoucherItemHibernateDao voucherItemHibernateDao;
	
	public void setVoucherItemHibernateDao(VoucherItemHibernateDao voucherItemHibernateDao)
	{
		this.voucherItemHibernateDao = voucherItemHibernateDao;
	}
	
     /**
	 * 注入扩展服务类
	 */
	 @Autowired(required = false)
	 public void setAdvanceService(@Qualifier("voucherItemAdvanceService") AdvanceService advanceService)
	 {
	  	super.setAdvanceService(advanceService);
	 }   
	 
	   
	/**
	 * 根据主键ID,取得凭证预览明细实例
	 * @param id
	 * @return
	 */
	public VoucherItem _getDetached(String id)
	{		
	    VoucherItem voucherItem = new VoucherItem();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  voucherItem = voucherItemHibernateDao.getDetached(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(voucherItem);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    voucherItem.setOperationType(operationType);
	    
		return voucherItem;	
	}
	
	/**
	 * 根据主键ID,取得凭证预览明细实例
	 * @param id
	 * @return
	 */
	public VoucherItem _get(String id)
	{		
	    VoucherItem voucherItem = new VoucherItem();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  voucherItem = voucherItemHibernateDao.get(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(voucherItem);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    voucherItem.setOperationType(operationType);
	    
		return voucherItem;	
	}
	
	/**
	 * 根据主键ID,取得凭证预览明细实例
	 * @param id
	 * @return
	 */
	public VoucherItem _getForEdit(String id)
	{		
	    VoucherItem voucherItem = _get(id);
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = voucherItem.getOperationType();
	    if(OperationType.READONLY.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotEdit);
	    }

		return voucherItem;	
	}
	
	/**
	 * 根据主键ID,取得凭证预览明细实例副本
	 * @param id
	 * @return
	 */
	public VoucherItem _getEntityCopy(String id)
	{		
	    VoucherItem voucherItem = new VoucherItem();
		VoucherItem voucherItemOld = this._get(id);
		try
		{
			BeanUtils.copyProperties(voucherItem, voucherItemOld);
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (InvocationTargetException e)
		{
			e.printStackTrace();
		}
		//voucherItem.setVoucheritemid(null); 
		return voucherItem;	
	}
	
	/**
	 * 删除  
	 *   
	 * @param voucherItem
	 */
	public void _delete(VoucherItem voucherItem)
	{
		if (null != advanceService)
			advanceService.preDelete(voucherItem);
	
 		LockService.isBoInstanceLocked(voucherItem,VoucherItem.class);
		voucherItemHibernateDao.remove(voucherItem);

		if (null != advanceService)
			advanceService.postDelete(voucherItem);
	}

	/**
	 * 根据主键删除
	 * 
	 * @param voucherItemId
	 */
	public void _delete(String voucherItemId,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(voucherItemId))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("voucheritemid"));
		VoucherItem voucherItem = this.voucherItemHibernateDao.load(voucherItemId);
		_delete(voucherItem);
	}
	
	/**
	 * 删除  
	 *   
	 * @param Set<VoucherItem> voucherItems
	 */
	public void _deletes(Set<VoucherItem> voucherItems,BusinessObject businessObject)
	{
		if (null == voucherItems || voucherItems.size() < 1)
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoText());
		Iterator<VoucherItem> it = voucherItems.iterator();
		while (it.hasNext())
		{
			VoucherItem voucherItem = (VoucherItem) it.next();
			_delete(voucherItem);
		}
	}
	
	/**
	 * 根据多个主键(由，分割)删除多个对象
	 * 
	 * @param voucherItemIds
	 */
	public void _deletes(String voucherItemIds,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(voucherItemIds))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("voucheritemid"));
		String[] ids = StringUtils.splitString(voucherItemIds);
		for (int i = 0; i < ids.length; i++)
		{
			_delete(ids[i],businessObject);
		}
	}
	/**
	 * 提交工作流
	 * 
	 * @param voucherItem
	 */
	public void _submitProcess(VoucherItem voucherItem
	,BusinessObject businessObject)
	{
		String id = voucherItem.getVoucheritemid();
        /**
		if (StringUtils.isNullBlank(id))
		{
			_save(voucherItem);
		}
		else
		{
			_update(voucherItem
			, businessObject);
		}**/
		
		String taskId = voucherItem.getWorkflowTaskId();
		id = voucherItem.getVoucheritemid();
		if (null == taskId || "".equals(taskId))
			WorkflowService.createAndSignalProcessInstance(voucherItem, id);
		else
			WorkflowService.signalProcessInstance(voucherItem, id, null);
	}

	/**
	 * 保存或更新凭证预览明细
	 * 保存  
	 *  
	 * @param voucherItem
	 */
	public void _update(VoucherItem voucherItem
,BusinessObject businessObject	
	)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(voucherItem);
		voucherItemHibernateDao.saveOrUpdate(voucherItem);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(voucherItem);
	}
	
	/**
	 * 保存  
	 *   
	 * @param voucherItem
	 */
	public void _save(VoucherItem voucherItem)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(voucherItem);
		// 对id赋空值，使之执行insert操作(适应复制创建场景)
		voucherItem.setVoucheritemid(null);
                                                          		voucherItemHibernateDao.saveOrUpdate(voucherItem);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(voucherItem);
	}
	
	/**
	 *
	 * 保存  
	 *   
	 * @param voucherItem
	 */
	public void _saveOrUpdate(VoucherItem voucherItem
,BusinessObject businessObject		
	)
	{
		if (StringUtils.isNullBlank(voucherItem.getVoucheritemid()))
		{	
			_save(voucherItem);
		}
		else
		{
			_update(voucherItem
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