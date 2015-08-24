/*
 * @(#)SupplyRemSumServiceGen.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年09月10日 02点06分05秒
 *  描　述：创建
 */
package com.infolion.xdss3.supplyremainsumGen.service;

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
import com.infolion.xdss3.supplyremainsum.domain.SupplyRemSum;
import com.infolion.xdss3.supplyremainsum.service.SupplyRemSumService;
import com.infolion.xdss3.supplyremainsum.dao.SupplyRemSumHibernateDao;

/**
 * <pre>
 * 供应商项目余额查询(SupplyRemSum)服务类
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
public class SupplyRemSumServiceGen extends BaseService
{
	protected Log log = LogFactory.getFactory().getLog(this.getClass());

	@Autowired
	protected SupplyRemSumHibernateDao supplyRemSumHibernateDao;
	
	public void setSupplyRemSumHibernateDao(SupplyRemSumHibernateDao supplyRemSumHibernateDao)
	{
		this.supplyRemSumHibernateDao = supplyRemSumHibernateDao;
	}
	
     /**
	 * 注入扩展服务类
	 */
	 @Autowired(required = false)
	 public void setAdvanceService(@Qualifier("supplyRemSumAdvanceService") AdvanceService advanceService)
	 {
	  	super.setAdvanceService(advanceService);
	 }   
	 
	   
	/**
	 * 根据主键ID,取得供应商项目余额查询实例
	 * @param id
	 * @return
	 */
	public SupplyRemSum _getDetached(String id)
	{		
	    SupplyRemSum supplyRemSum = new SupplyRemSum();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  supplyRemSum = supplyRemSumHibernateDao.getDetached(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(supplyRemSum);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    supplyRemSum.setOperationType(operationType);
	    
		return supplyRemSum;	
	}
	
	/**
	 * 根据主键ID,取得供应商项目余额查询实例
	 * @param id
	 * @return
	 */
	public SupplyRemSum _get(String id)
	{		
	    SupplyRemSum supplyRemSum = new SupplyRemSum();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  supplyRemSum = supplyRemSumHibernateDao.get(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(supplyRemSum);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    supplyRemSum.setOperationType(operationType);
	    
		return supplyRemSum;	
	}
	
	/**
	 * 根据主键ID,取得供应商项目余额查询实例
	 * @param id
	 * @return
	 */
	public SupplyRemSum _getForEdit(String id)
	{		
	    SupplyRemSum supplyRemSum = _get(id);
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = supplyRemSum.getOperationType();
	    if(OperationType.READONLY.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotEdit);
	    }

		return supplyRemSum;	
	}
	
	/**
	 * 根据主键ID,取得供应商项目余额查询实例副本
	 * @param id
	 * @return
	 */
	public SupplyRemSum _getEntityCopy(String id)
	{		
	    SupplyRemSum supplyRemSum = new SupplyRemSum();
		SupplyRemSum supplyRemSumOld = this._get(id);
		try
		{
			BeanUtils.copyProperties(supplyRemSum, supplyRemSumOld);
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (InvocationTargetException e)
		{
			e.printStackTrace();
		}
		//supplyRemSum.setSrsid(null); 
		return supplyRemSum;	
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
	 * @param supplyRemSum
	 */
	public void _delete(SupplyRemSum supplyRemSum)
	{
		if (null != advanceService)
			advanceService.preDelete(supplyRemSum);
	
 		LockService.isBoInstanceLocked(supplyRemSum,SupplyRemSum.class);
		supplyRemSumHibernateDao.remove(supplyRemSum);

		if (null != advanceService)
			advanceService.postDelete(supplyRemSum);
	}

	/**
	 * 根据主键删除
	 * 
	 * @param supplyRemSumId
	 */
	public void _delete(String supplyRemSumId,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(supplyRemSumId))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("srsid"));
		SupplyRemSum supplyRemSum = this.supplyRemSumHibernateDao.load(supplyRemSumId);
		_delete(supplyRemSum);
	}
	
	/**
	 * 删除  
	 *   
	 * @param Set<SupplyRemSum> supplyRemSums
	 */
	public void _deletes(Set<SupplyRemSum> supplyRemSums,BusinessObject businessObject)
	{
		if (null == supplyRemSums || supplyRemSums.size() < 1)
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoText());
		Iterator<SupplyRemSum> it = supplyRemSums.iterator();
		while (it.hasNext())
		{
			SupplyRemSum supplyRemSum = (SupplyRemSum) it.next();
			_delete(supplyRemSum);
		}
	}
	
	/**
	 * 根据多个主键(由，分割)删除多个对象
	 * 
	 * @param supplyRemSumIds
	 */
	public void _deletes(String supplyRemSumIds,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(supplyRemSumIds))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("srsid"));
		String[] ids = StringUtils.splitString(supplyRemSumIds);
		for (int i = 0; i < ids.length; i++)
		{
			_delete(ids[i],businessObject);
		}
	}
	/**
	 * 提交工作流
	 * 
	 * @param supplyRemSum
	 */
	public void _submitProcess(SupplyRemSum supplyRemSum
	,BusinessObject businessObject)
	{
		String id = supplyRemSum.getSrsid();
        /**
		if (StringUtils.isNullBlank(id))
		{
			_save(supplyRemSum);
		}
		else
		{
			_update(supplyRemSum
			, businessObject);
		}**/
		
		String taskId = supplyRemSum.getWorkflowTaskId();
		id = supplyRemSum.getSrsid();
		if (null == taskId || "".equals(taskId))
			WorkflowService.createAndSignalProcessInstance(supplyRemSum, id);
		else
			WorkflowService.signalProcessInstance(supplyRemSum, id, null);
	}

	/**
	 * 保存或更新供应商项目余额查询
	 * 保存  
	 *  
	 * @param supplyRemSum
	 */
	public void _update(SupplyRemSum supplyRemSum
,BusinessObject businessObject	
	)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(supplyRemSum);
		supplyRemSumHibernateDao.saveOrUpdate(supplyRemSum);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(supplyRemSum);
	}
	
	/**
	 * 保存  
	 *   
	 * @param supplyRemSum
	 */
	public void _save(SupplyRemSum supplyRemSum)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(supplyRemSum);
		// 对id赋空值，使之执行insert操作(适应复制创建场景)
		supplyRemSum.setSrsid(null);
          		supplyRemSumHibernateDao.saveOrUpdate(supplyRemSum);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(supplyRemSum);
	}
	
	/**
	 *
	 * 保存  
	 *   
	 * @param supplyRemSum
	 */
	public void _saveOrUpdate(SupplyRemSum supplyRemSum
,BusinessObject businessObject		
	)
	{
		if (StringUtils.isNullBlank(supplyRemSum.getSrsid()))
		{	
			_save(supplyRemSum);
		}
		else
		{
			_update(supplyRemSum
, businessObject);
}	}
	
}