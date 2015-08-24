/*
 * @(#)UnClearCustomBillServiceGen.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年07月19日 11点44分19秒
 *  描　述：创建
 */
package com.infolion.xdss3.singleClearGen.service;

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
import com.infolion.xdss3.singleClear.domain.UnClearCustomBill;
import com.infolion.xdss3.singleClear.service.UnClearCustomBillService;
import com.infolion.xdss3.singleClear.dao.UnClearCustomBillHibernateDao;

/**
 * <pre>
 * 未清应收(借方)(UnClearCustomBill)服务类
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
public class UnClearCustomBillServiceGen extends BaseService
{
	protected Log log = LogFactory.getFactory().getLog(this.getClass());

	@Autowired
	protected UnClearCustomBillHibernateDao unClearCustomBillHibernateDao;
	
	public void setUnClearCustomBillHibernateDao(UnClearCustomBillHibernateDao unClearCustomBillHibernateDao)
	{
		this.unClearCustomBillHibernateDao = unClearCustomBillHibernateDao;
	}
	
     /**
	 * 注入扩展服务类
	 */
	 @Autowired(required = false)
	 public void setAdvanceService(@Qualifier("unClearCustomBillAdvanceService") AdvanceService advanceService)
	 {
	  	super.setAdvanceService(advanceService);
	 }   
	 
	   
	/**
	 * 根据主键ID,取得未清应收(借方)实例
	 * @param id
	 * @return
	 */
	public UnClearCustomBill _getDetached(String id)
	{		
	    UnClearCustomBill unClearCustomBill = new UnClearCustomBill();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  unClearCustomBill = unClearCustomBillHibernateDao.getDetached(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(unClearCustomBill);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    unClearCustomBill.setOperationType(operationType);
	    
		return unClearCustomBill;	
	}
	
	/**
	 * 根据主键ID,取得未清应收(借方)实例
	 * @param id
	 * @return
	 */
	public UnClearCustomBill _get(String id)
	{		
	    UnClearCustomBill unClearCustomBill = new UnClearCustomBill();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  unClearCustomBill = unClearCustomBillHibernateDao.get(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(unClearCustomBill);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    unClearCustomBill.setOperationType(operationType);
	    
		return unClearCustomBill;	
	}
	
	/**
	 * 根据主键ID,取得未清应收(借方)实例
	 * @param id
	 * @return
	 */
	public UnClearCustomBill _getForEdit(String id)
	{		
	    UnClearCustomBill unClearCustomBill = _get(id);
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = unClearCustomBill.getOperationType();
	    if(OperationType.READONLY.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotEdit);
	    }

		return unClearCustomBill;	
	}
	
	/**
	 * 根据主键ID,取得未清应收(借方)实例副本
	 * @param id
	 * @return
	 */
	public UnClearCustomBill _getEntityCopy(String id)
	{		
	    UnClearCustomBill unClearCustomBill = new UnClearCustomBill();
		UnClearCustomBill unClearCustomBillOld = this._get(id);
		try
		{
			BeanUtils.copyProperties(unClearCustomBill, unClearCustomBillOld);
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (InvocationTargetException e)
		{
			e.printStackTrace();
		}
		//unClearCustomBill.setUnclearcusbillid(null); 
		return unClearCustomBill;	
	}
	
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
	
	/**
	 * 删除  
	 *   
	 * @param unClearCustomBill
	 */
	public void _delete(UnClearCustomBill unClearCustomBill)
	{
		if (null != advanceService)
			advanceService.preDelete(unClearCustomBill);
	
 		LockService.isBoInstanceLocked(unClearCustomBill,UnClearCustomBill.class);
		unClearCustomBillHibernateDao.remove(unClearCustomBill);

		if (null != advanceService)
			advanceService.postDelete(unClearCustomBill);
	}

	/**
	 * 根据主键删除
	 * 
	 * @param unClearCustomBillId
	 */
	public void _delete(String unClearCustomBillId,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(unClearCustomBillId))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("unclearcusbillid"));
		UnClearCustomBill unClearCustomBill = this.unClearCustomBillHibernateDao.load(unClearCustomBillId);
		_delete(unClearCustomBill);
	}
	
	/**
	 * 删除  
	 *   
	 * @param Set<UnClearCustomBill> unClearCustomBills
	 */
	public void _deletes(Set<UnClearCustomBill> unClearCustomBills,BusinessObject businessObject)
	{
		if (null == unClearCustomBills || unClearCustomBills.size() < 1)
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoText());
		Iterator<UnClearCustomBill> it = unClearCustomBills.iterator();
		while (it.hasNext())
		{
			UnClearCustomBill unClearCustomBill = (UnClearCustomBill) it.next();
			_delete(unClearCustomBill);
		}
	}
	
	/**
	 * 根据多个主键(由，分割)删除多个对象
	 * 
	 * @param unClearCustomBillIds
	 */
	public void _deletes(String unClearCustomBillIds,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(unClearCustomBillIds))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("unclearcusbillid"));
		String[] ids = StringUtils.splitString(unClearCustomBillIds);
		for (int i = 0; i < ids.length; i++)
		{
			_delete(ids[i],businessObject);
		}
	}
	/**
	 * 提交工作流
	 * 
	 * @param unClearCustomBill
	 */
	public void _submitProcess(UnClearCustomBill unClearCustomBill
	,BusinessObject businessObject)
	{
		String id = unClearCustomBill.getUnclearcusbillid();
        /**
		if (StringUtils.isNullBlank(id))
		{
			_save(unClearCustomBill);
		}
		else
		{
			_update(unClearCustomBill
			, businessObject);
		}**/
		
		String taskId = unClearCustomBill.getWorkflowTaskId();
		id = unClearCustomBill.getUnclearcusbillid();
		if (null == taskId || "".equals(taskId))
			WorkflowService.createAndSignalProcessInstance(unClearCustomBill, id);
		else
			WorkflowService.signalProcessInstance(unClearCustomBill, id, null);
	}

	/**
	 * 保存或更新未清应收(借方)
	 * 保存  
	 *  
	 * @param unClearCustomBill
	 */
	public void _update(UnClearCustomBill unClearCustomBill
,BusinessObject businessObject	
	)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(unClearCustomBill);
		unClearCustomBillHibernateDao.saveOrUpdate(unClearCustomBill);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(unClearCustomBill);
	}
	
	/**
	 * 保存  
	 *   
	 * @param unClearCustomBill
	 */
	public void _save(UnClearCustomBill unClearCustomBill)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(unClearCustomBill);
		// 对id赋空值，使之执行insert操作(适应复制创建场景)
		unClearCustomBill.setUnclearcusbillid(null);
                                                    		unClearCustomBillHibernateDao.saveOrUpdate(unClearCustomBill);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(unClearCustomBill);
	}
	
	/**
	 *
	 * 保存  
	 *   
	 * @param unClearCustomBill
	 */
	public void _saveOrUpdate(UnClearCustomBill unClearCustomBill
,BusinessObject businessObject		
	)
	{
		if (StringUtils.isNullBlank(unClearCustomBill.getUnclearcusbillid()))
		{	
			_save(unClearCustomBill);
		}
		else
		{
			_update(unClearCustomBill
, businessObject);
}	}
	
}