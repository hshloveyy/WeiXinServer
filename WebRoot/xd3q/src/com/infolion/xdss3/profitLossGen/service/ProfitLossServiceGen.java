/*
 * @(#)ProfitLossServiceGen.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年05月21日 11点48分46秒
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

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;
import java.util.Map.Entry;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;

import com.ctc.wstx.util.StringUtil;
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
import com.infolion.sapss.bapi.ExtractSapData;
import com.infolion.sapss.bapi.dto.MasterDataDto;
import com.infolion.xdss3.profitLoss.domain.ProfitLoss;
import com.infolion.xdss3.profitLoss.service.ProfitLossService;
import com.infolion.xdss3.profitLoss.dao.ProfitLossHibernateDao;

/**
 * <pre>
 * 合同信息及市场单价维护(ProfitLoss)服务类
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
public class ProfitLossServiceGen extends BaseService
{
	protected Log log = LogFactory.getFactory().getLog(this.getClass());

	@Autowired
	protected ProfitLossHibernateDao profitLossHibernateDao;
	
	public void setProfitLossHibernateDao(ProfitLossHibernateDao profitLossHibernateDao)
	{
		this.profitLossHibernateDao = profitLossHibernateDao;
	}
	
     /**
	 * 注入扩展服务类
	 */
	 @Autowired(required = false)
	 public void setAdvanceService(@Qualifier("profitLossAdvanceService") AdvanceService advanceService)
	 {
	  	super.setAdvanceService(advanceService);
	 }   
	 
	   
	/**
	 * 根据主键ID,取得合同信息及市场单价维护实例
	 * @param id
	 * @return
	 */
	public ProfitLoss _getDetached(String id)
	{		
	    ProfitLoss profitLoss = new ProfitLoss();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  profitLoss = profitLossHibernateDao.getDetached(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(profitLoss);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    profitLoss.setOperationType(operationType);
	    
		return profitLoss;	
	}
	
	/**
	 * 根据主键ID,取得合同信息及市场单价维护实例
	 * @param id
	 * @return
	 */
	public ProfitLoss _get(String id)
	{		
	    ProfitLoss profitLoss = new ProfitLoss();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  profitLoss = profitLossHibernateDao.get(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(profitLoss);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    profitLoss.setOperationType(operationType);
	    
		return profitLoss;	
	}
	
	/**
	 * 根据主键ID,取得合同信息及市场单价维护实例
	 * @param id
	 * @return
	 */
	public ProfitLoss _getForEdit(String id)
	{		
	    ProfitLoss profitLoss = _get(id);
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = profitLoss.getOperationType();
	    if(OperationType.READONLY.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotEdit);
	    }

		return profitLoss;	
	}
	
	/**
	 * 根据主键ID,取得合同信息及市场单价维护实例副本
	 * @param id
	 * @return
	 */
	public ProfitLoss _getEntityCopy(String id)
	{		
	    ProfitLoss profitLoss = new ProfitLoss();
		ProfitLoss profitLossOld = this._get(id);
		try
		{
			BeanUtils.copyProperties(profitLoss, profitLossOld);
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (InvocationTargetException e)
		{
			e.printStackTrace();
		}
		//profitLoss.setProfitlossid(null); 
		return profitLoss;	
	}
	
	/**
	 * 删除  
	 *   
	 * @param profitLoss
	 */
	public void _delete(ProfitLoss profitLoss)
	{
		if (null != advanceService)
			advanceService.preDelete(profitLoss);
	
 		LockService.isBoInstanceLocked(profitLoss,ProfitLoss.class);
		profitLossHibernateDao.remove(profitLoss);

		if (null != advanceService)
			advanceService.postDelete(profitLoss);
	}

	/**
	 * 根据主键删除
	 * 
	 * @param profitLossId
	 */
	public void _delete(String profitLossId,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(profitLossId))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("profitlossid"));
		ProfitLoss profitLoss = this.profitLossHibernateDao.load(profitLossId);
		_delete(profitLoss);
	}
	
	/**
	 * 删除  
	 *   
	 * @param Set<ProfitLoss> profitLosss
	 */
	public void _deletes(Set<ProfitLoss> profitLosss,BusinessObject businessObject)
	{
		if (null == profitLosss || profitLosss.size() < 1)
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoText());
		Iterator<ProfitLoss> it = profitLosss.iterator();
		while (it.hasNext())
		{
			ProfitLoss profitLoss = (ProfitLoss) it.next();
			_delete(profitLoss);
		}
	}
	
	/**
	 * 根据多个主键(由，分割)删除多个对象
	 * 
	 * @param profitLossIds
	 */
	public void _deletes(String profitLossIds,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(profitLossIds))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("profitlossid"));
		String[] ids = StringUtils.splitString(profitLossIds);
		for (int i = 0; i < ids.length; i++)
		{
			_delete(ids[i],businessObject);
		}
	}
	/**
	 * 提交工作流
	 * 
	 * @param profitLoss
	 */
	public void _submitProcess(ProfitLoss profitLoss
	,BusinessObject businessObject)
	{
		String id = profitLoss.getProfitlossid();
		if (StringUtils.isNullBlank(id))
		{
			_save(profitLoss);
		}
		else
		{
			_update(profitLoss
			, businessObject);
		}
		String taskId = profitLoss.getWorkflowTaskId();
		id = profitLoss.getProfitlossid();
		if (null == taskId || "".equals(taskId))
			WorkflowService.createAndSignalProcessInstance(profitLoss, id);
		else
			WorkflowService.signalProcessInstance(profitLoss, id, null);
	}

	/**
	 * 保存或更新合同信息及市场单价维护
	 * 保存  
	 *  
	 * @param profitLoss
	 */
	public void _update(ProfitLoss profitLoss
,BusinessObject businessObject	
	)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(profitLoss);
		profitLossHibernateDao.saveOrUpdate(profitLoss);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(profitLoss);
	}
	
	/**
	 * 保存  
	 *   
	 * @param profitLoss
	 */
	public void _save(ProfitLoss profitLoss)
	{		
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(profitLoss);
		// 对id赋空值，使之执行insert操作(适应复制创建场景)
		profitLoss.setProfitlossid(null);
                                                                		profitLossHibernateDao.saveOrUpdate(profitLoss);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(profitLoss);
	}
	
	/**
	 *
	 * 保存  
	 *   
	 * @param profitLoss
	 */
	public void _saveOrUpdate(ProfitLoss profitLoss
,BusinessObject businessObject		
	)
	{
		if (StringUtils.isNullBlank(profitLoss.getProfitlossid()))
		{	
			_save(profitLoss);
		}
		else
		{
			_update(profitLoss
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