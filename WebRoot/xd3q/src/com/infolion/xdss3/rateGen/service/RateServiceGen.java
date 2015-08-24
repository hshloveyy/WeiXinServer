/*
 * @(#)RateServiceGen.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2011年11月25日 11点58分55秒
 *  描　述：创建
 */
package com.infolion.xdss3.rateGen.service;

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
import com.infolion.xdss3.rate.domain.Rate;
import com.infolion.xdss3.rate.service.RateService;
import com.infolion.xdss3.rate.dao.RateHibernateDao;

/**
 * <pre>
 * 利率(Rate)服务类
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
public class RateServiceGen extends BaseService
{
	protected Log log = LogFactory.getFactory().getLog(this.getClass());

	@Autowired
	protected RateHibernateDao rateHibernateDao;
	
	public void setRateHibernateDao(RateHibernateDao rateHibernateDao)
	{
		this.rateHibernateDao = rateHibernateDao;
	}
	
     /**
	 * 注入扩展服务类
	 */
	 @Autowired(required = false)
	 public void setAdvanceService(@Qualifier("rateAdvanceService") AdvanceService advanceService)
	 {
	  	super.setAdvanceService(advanceService);
	 }   
	 
	   
	/**
	 * 根据主键ID,取得利率实例
	 * @param id
	 * @return
	 */
	public Rate _getDetached(String id)
	{		
	    Rate rate = new Rate();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  rate = rateHibernateDao.getDetached(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(rate);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    rate.setOperationType(operationType);
	    
		return rate;	
	}
	
	/**
	 * 根据主键ID,取得利率实例
	 * @param id
	 * @return
	 */
	public Rate _get(String id)
	{		
	    Rate rate = new Rate();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  rate = rateHibernateDao.get(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(rate);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    rate.setOperationType(operationType);
	    
		return rate;	
	}
	
	/**
	 * 根据主键ID,取得利率实例
	 * @param id
	 * @return
	 */
	public Rate _getForEdit(String id)
	{		
	    Rate rate = _get(id);
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = rate.getOperationType();
	    if(OperationType.READONLY.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotEdit);
	    }

		return rate;	
	}
	
	/**
	 * 根据主键ID,取得利率实例副本
	 * @param id
	 * @return
	 */
	public Rate _getEntityCopy(String id)
	{		
	    Rate rate = new Rate();
		Rate rateOld = this._get(id);
		try
		{
			BeanUtils.copyProperties(rate, rateOld);
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (InvocationTargetException e)
		{
			e.printStackTrace();
		}
		//rate.setRateid(null); 
		return rate;	
	}
	
	/**
	 * 删除  
	 *   
	 * @param rate
	 */
	public void _delete(Rate rate)
	{
		if (null != advanceService)
			advanceService.preDelete(rate);
	
 		LockService.isBoInstanceLocked(rate,Rate.class);
		rateHibernateDao.remove(rate);

		if (null != advanceService)
			advanceService.postDelete(rate);
	}

	/**
	 * 根据主键删除
	 * 
	 * @param rateId
	 */
	public void _delete(String rateId,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(rateId))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("rateid"));
		Rate rate = this.rateHibernateDao.load(rateId);
		_delete(rate);
	}
	
	/**
	 * 删除  
	 *   
	 * @param Set<Rate> rates
	 */
	public void _deletes(Set<Rate> rates,BusinessObject businessObject)
	{
		if (null == rates || rates.size() < 1)
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoText());
		Iterator<Rate> it = rates.iterator();
		while (it.hasNext())
		{
			Rate rate = (Rate) it.next();
			_delete(rate);
		}
	}
	
	/**
	 * 根据多个主键(由，分割)删除多个对象
	 * 
	 * @param rateIds
	 */
	public void _deletes(String rateIds,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(rateIds))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("rateid"));
		String[] ids = StringUtils.splitString(rateIds);
		for (int i = 0; i < ids.length; i++)
		{
			_delete(ids[i],businessObject);
		}
	}
	/**
	 * 提交工作流
	 * 
	 * @param rate
	 */
	public void _submitProcess(Rate rate
	,BusinessObject businessObject)
	{
		String id = rate.getRateid();
        /**
		if (StringUtils.isNullBlank(id))
		{
			_save(rate);
		}
		else
		{
			_update(rate
			, businessObject);
		}**/
		
		String taskId = rate.getWorkflowTaskId();
		id = rate.getRateid();
		if (null == taskId || "".equals(taskId))
			WorkflowService.createAndSignalProcessInstance(rate, id);
		else
			WorkflowService.signalProcessInstance(rate, id, null);
	}

	/**
	 * 保存或更新利率
	 * 保存  
	 *  
	 * @param rate
	 */
	public void _update(Rate rate
,BusinessObject businessObject	
	)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(rate);
		rateHibernateDao.saveOrUpdate(rate);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(rate);
	}
	
	/**
	 * 保存  
	 *   
	 * @param rate
	 */
	public void _save(Rate rate)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(rate);
		// 对id赋空值，使之执行insert操作(适应复制创建场景)
		rate.setRateid(null);
                    		rateHibernateDao.saveOrUpdate(rate);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(rate);
	}
	
	/**
	 *
	 * 保存  
	 *   
	 * @param rate
	 */
	public void _saveOrUpdate(Rate rate
,BusinessObject businessObject		
	)
	{
		if (StringUtils.isNullBlank(rate.getRateid()))
		{	
			_save(rate);
		}
		else
		{
			_update(rate
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