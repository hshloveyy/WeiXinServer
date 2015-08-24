/*
 * @(#)UnClearSupplieBillServiceGen.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年07月09日 10点02分17秒
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
import com.infolion.xdss3.singleClear.domain.UnClearSupplieBill;
import com.infolion.xdss3.singleClear.service.UnClearSupplieBillService;
import com.infolion.xdss3.singleClear.dao.UnClearSupplieBillHibernateDao;

/**
 * <pre>
 * 未清应付（贷方）(UnClearSupplieBill)服务类
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
public class UnClearSupplieBillServiceGen extends BaseService
{
	protected Log log = LogFactory.getFactory().getLog(this.getClass());

	@Autowired
	protected UnClearSupplieBillHibernateDao unClearSupplieBillHibernateDao;
	
	public void setUnClearSupplieBillHibernateDao(UnClearSupplieBillHibernateDao unClearSupplieBillHibernateDao)
	{
		this.unClearSupplieBillHibernateDao = unClearSupplieBillHibernateDao;
	}
	
     /**
	 * 注入扩展服务类
	 */
	 @Autowired(required = false)
	 public void setAdvanceService(@Qualifier("unClearSupplieBillAdvanceService") AdvanceService advanceService)
	 {
	  	super.setAdvanceService(advanceService);
	 }   
	 
	   
	/**
	 * 根据主键ID,取得未清应付（贷方）实例
	 * @param id
	 * @return
	 */
	public UnClearSupplieBill _getDetached(String id)
	{		
	    UnClearSupplieBill unClearSupplieBill = new UnClearSupplieBill();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  unClearSupplieBill = unClearSupplieBillHibernateDao.getDetached(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(unClearSupplieBill);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    unClearSupplieBill.setOperationType(operationType);
	    
		return unClearSupplieBill;	
	}
	
	/**
	 * 根据主键ID,取得未清应付（贷方）实例
	 * @param id
	 * @return
	 */
	public UnClearSupplieBill _get(String id)
	{		
	    UnClearSupplieBill unClearSupplieBill = new UnClearSupplieBill();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  unClearSupplieBill = unClearSupplieBillHibernateDao.get(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(unClearSupplieBill);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    unClearSupplieBill.setOperationType(operationType);
	    
		return unClearSupplieBill;	
	}
	
	/**
	 * 根据主键ID,取得未清应付（贷方）实例
	 * @param id
	 * @return
	 */
	public UnClearSupplieBill _getForEdit(String id)
	{		
	    UnClearSupplieBill unClearSupplieBill = _get(id);
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = unClearSupplieBill.getOperationType();
	    if(OperationType.READONLY.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotEdit);
	    }

		return unClearSupplieBill;	
	}
	
	/**
	 * 根据主键ID,取得未清应付（贷方）实例副本
	 * @param id
	 * @return
	 */
	public UnClearSupplieBill _getEntityCopy(String id)
	{		
	    UnClearSupplieBill unClearSupplieBill = new UnClearSupplieBill();
		UnClearSupplieBill unClearSupplieBillOld = this._get(id);
		try
		{
			BeanUtils.copyProperties(unClearSupplieBill, unClearSupplieBillOld);
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (InvocationTargetException e)
		{
			e.printStackTrace();
		}
		//unClearSupplieBill.setUnclearsbillid(null); 
		return unClearSupplieBill;	
	}
	
	/**
	 * 删除  
	 *   
	 * @param unClearSupplieBill
	 */
	public void _delete(UnClearSupplieBill unClearSupplieBill)
	{
		if (null != advanceService)
			advanceService.preDelete(unClearSupplieBill);
	
 		LockService.isBoInstanceLocked(unClearSupplieBill,UnClearSupplieBill.class);
		unClearSupplieBillHibernateDao.remove(unClearSupplieBill);

		if (null != advanceService)
			advanceService.postDelete(unClearSupplieBill);
	}

	/**
	 * 根据主键删除
	 * 
	 * @param unClearSupplieBillId
	 */
	public void _delete(String unClearSupplieBillId,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(unClearSupplieBillId))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("unclearsbillid"));
		UnClearSupplieBill unClearSupplieBill = this.unClearSupplieBillHibernateDao.load(unClearSupplieBillId);
		_delete(unClearSupplieBill);
	}
	
	/**
	 * 删除  
	 *   
	 * @param Set<UnClearSupplieBill> unClearSupplieBills
	 */
	public void _deletes(Set<UnClearSupplieBill> unClearSupplieBills,BusinessObject businessObject)
	{
		if (null == unClearSupplieBills || unClearSupplieBills.size() < 1)
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoText());
		Iterator<UnClearSupplieBill> it = unClearSupplieBills.iterator();
		while (it.hasNext())
		{
			UnClearSupplieBill unClearSupplieBill = (UnClearSupplieBill) it.next();
			_delete(unClearSupplieBill);
		}
	}
	
	/**
	 * 根据多个主键(由，分割)删除多个对象
	 * 
	 * @param unClearSupplieBillIds
	 */
	public void _deletes(String unClearSupplieBillIds,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(unClearSupplieBillIds))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("unclearsbillid"));
		String[] ids = StringUtils.splitString(unClearSupplieBillIds);
		for (int i = 0; i < ids.length; i++)
		{
			_delete(ids[i],businessObject);
		}
	}
	/**
	 * 提交工作流
	 * 
	 * @param unClearSupplieBill
	 */
	public void _submitProcess(UnClearSupplieBill unClearSupplieBill
	,BusinessObject businessObject)
	{
		String id = unClearSupplieBill.getUnclearsbillid();
        /**
		if (StringUtils.isNullBlank(id))
		{
			_save(unClearSupplieBill);
		}
		else
		{
			_update(unClearSupplieBill
			, businessObject);
		}**/
		
		String taskId = unClearSupplieBill.getWorkflowTaskId();
		id = unClearSupplieBill.getUnclearsbillid();
		if (null == taskId || "".equals(taskId))
			WorkflowService.createAndSignalProcessInstance(unClearSupplieBill, id);
		else
			WorkflowService.signalProcessInstance(unClearSupplieBill, id, null);
	}

	/**
	 * 保存或更新未清应付（贷方）
	 * 保存  
	 *  
	 * @param unClearSupplieBill
	 */
	public void _update(UnClearSupplieBill unClearSupplieBill
,BusinessObject businessObject	
	)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(unClearSupplieBill);
		unClearSupplieBillHibernateDao.saveOrUpdate(unClearSupplieBill);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(unClearSupplieBill);
	}
	
	/**
	 * 保存  
	 *   
	 * @param unClearSupplieBill
	 */
	public void _save(UnClearSupplieBill unClearSupplieBill)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(unClearSupplieBill);
		// 对id赋空值，使之执行insert操作(适应复制创建场景)
		unClearSupplieBill.setUnclearsbillid(null);
                                        		unClearSupplieBillHibernateDao.saveOrUpdate(unClearSupplieBill);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(unClearSupplieBill);
	}
	
	/**
	 *
	 * 保存  
	 *   
	 * @param unClearSupplieBill
	 */
	public void _saveOrUpdate(UnClearSupplieBill unClearSupplieBill
,BusinessObject businessObject		
	)
	{
		if (StringUtils.isNullBlank(unClearSupplieBill.getUnclearsbillid()))
		{	
			_save(unClearSupplieBill);
		}
		else
		{
			_update(unClearSupplieBill
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