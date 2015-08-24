/*
 * @(#)ProfitLossmngServiceGen.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年05月21日 11点46分24秒
 *  描　述：创建
 */
package com.infolion.xdss3.profitLossGen.service;

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
import com.infolion.xdss3.profitLoss.domain.ProfitLoss;
import com.infolion.xdss3.profitLoss.domain.ProfitLossmng;
import com.infolion.xdss3.profitLoss.service.ProfitLossmngService;
import com.infolion.xdss3.profitLoss.dao.ProfitLossmngHibernateDao;

/**
 * <pre>
 * 存货浮动盈亏调查表(ProfitLossmng)服务类
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
public class ProfitLossmngServiceGen extends BaseService
{
	protected Log log = LogFactory.getFactory().getLog(this.getClass());

	@Autowired
	protected ProfitLossmngHibernateDao profitLossmngHibernateDao;
	
	public void setProfitLossmngHibernateDao(ProfitLossmngHibernateDao profitLossmngHibernateDao)
	{
		this.profitLossmngHibernateDao = profitLossmngHibernateDao;
	}
	
     /**
	 * 注入扩展服务类
	 */
	 @Autowired(required = false)
	 public void setAdvanceService(@Qualifier("profitLossmngAdvanceService") AdvanceService advanceService)
	 {
	  	super.setAdvanceService(advanceService);
	 }   
	 
	   
	/**
	 * 根据主键ID,取得存货浮动盈亏调查表实例
	 * @param id
	 * @return
	 */
	public ProfitLossmng _getDetached(String id)
	{		
	    ProfitLossmng profitLossmng = new ProfitLossmng();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  profitLossmng = profitLossmngHibernateDao.getDetached(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(profitLossmng);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    profitLossmng.setOperationType(operationType);
	    
		return profitLossmng;	
	}
	
	/**
	 * 根据主键ID,取得存货浮动盈亏调查表实例
	 * @param id
	 * @return
	 */
	public ProfitLossmng _get(String id)
	{		
	    ProfitLossmng profitLossmng = new ProfitLossmng();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  profitLossmng = profitLossmngHibernateDao.get(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(profitLossmng);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    profitLossmng.setOperationType(operationType);
	    
		return profitLossmng;	
	}
	
	/**
	 * 根据主键ID,取得存货浮动盈亏调查表实例
	 * @param id
	 * @return
	 */
	public ProfitLossmng _getForEdit(String id)
	{		
	    ProfitLossmng profitLossmng = _get(id);
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = profitLossmng.getOperationType();
	    if(OperationType.READONLY.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotEdit);
	    }

		return profitLossmng;	
	}
	
	/**
	 * 根据主键ID,取得存货浮动盈亏调查表实例副本
	 * @param id
	 * @return
	 */
	public ProfitLossmng _getEntityCopy(String id)
	{		
	    ProfitLossmng profitLossmng = new ProfitLossmng();
		ProfitLossmng profitLossmngOld = this._get(id);
		try
		{
			BeanUtils.copyProperties(profitLossmng, profitLossmngOld);
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (InvocationTargetException e)
		{
			e.printStackTrace();
		}
		//profitLossmng.setProfitlossid(null); 
		return profitLossmng;	
	}
	
	/**
	 * 删除  
	 *   
	 * @param profitLossmng
	 */
	public void _delete(ProfitLossmng profitLossmng)
	{
		if (null != advanceService)
			advanceService.preDelete(profitLossmng);
	
 		LockService.isBoInstanceLocked(profitLossmng,ProfitLossmng.class);
		profitLossmngHibernateDao.remove(profitLossmng);

		if (null != advanceService)
			advanceService.postDelete(profitLossmng);
	}

	/**
	 * 根据主键删除
	 * 
	 * @param profitLossmngId
	 */
	public void _delete(String profitLossmngId,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(profitLossmngId))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("profitlossid"));
		ProfitLossmng profitLossmng = this.profitLossmngHibernateDao.load(profitLossmngId);
		_delete(profitLossmng);
	}
	
	/**
	 * 删除  
	 *   
	 * @param Set<ProfitLossmng> profitLossmngs
	 */
	public void _deletes(Set<ProfitLossmng> profitLossmngs,BusinessObject businessObject)
	{
		if (null == profitLossmngs || profitLossmngs.size() < 1)
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoText());
		Iterator<ProfitLossmng> it = profitLossmngs.iterator();
		while (it.hasNext())
		{
			ProfitLossmng profitLossmng = (ProfitLossmng) it.next();
			_delete(profitLossmng);
		}
	}
	
	/**
	 * 根据多个主键(由，分割)删除多个对象
	 * 
	 * @param profitLossmngIds
	 */
	public void _deletes(String profitLossmngIds,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(profitLossmngIds))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("profitlossid"));
		String[] ids = StringUtils.splitString(profitLossmngIds);
		for (int i = 0; i < ids.length; i++)
		{
			_delete(ids[i],businessObject);
		}
	}
	/**
	 * 提交工作流
	 * 
	 * @param profitLossmng
	 */
	public void _submitProcess(ProfitLossmng profitLossmng
	,BusinessObject businessObject)
	{
		String id = profitLossmng.getProfitlossid();
		if (StringUtils.isNullBlank(id))
		{
			_save(profitLossmng);
		}
		else
		{
			_update(profitLossmng
			, businessObject);
		}
		String taskId = profitLossmng.getWorkflowTaskId();
		id = profitLossmng.getProfitlossid();
		if (null == taskId || "".equals(taskId))
			WorkflowService.createAndSignalProcessInstance(profitLossmng, id);
		else
			WorkflowService.signalProcessInstance(profitLossmng, id, null);
	}

	/**
	 * 保存或更新存货浮动盈亏调查表
	 * 保存  
	 *  
	 * @param profitLossmng
	 */
	public void _update(ProfitLossmng profitLossmng
,BusinessObject businessObject	
	)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(profitLossmng);
		profitLossmngHibernateDao.saveOrUpdate(profitLossmng);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(profitLossmng);
	}
	
	/**
	 * 保存  
	 *   
	 * @param profitLossmng
	 */
	public void _save(ProfitLossmng profitLossmng)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(profitLossmng);
		// 对id赋空值，使之执行insert操作(适应复制创建场景)
		profitLossmng.setProfitlossid(null);
                                                                		profitLossmngHibernateDao.saveOrUpdate(profitLossmng);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(profitLossmng);
	}
	
	/**
	 *
	 * 保存  
	 *   
	 * @param profitLossmng
	 */
	public void _saveOrUpdate(ProfitLossmng profitLossmng
,BusinessObject businessObject		
	)
	{
		if (StringUtils.isNullBlank(profitLossmng.getProfitlossid()))
		{	
			_save(profitLossmng);
		}
		else
		{
			_update(profitLossmng
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