/*
 * @(#)FundAdjustmentServiceGen.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2011年11月03日 14点45分24秒
 *  描　述：创建
 */
package com.infolion.xdss3.fundadjustmentGen.service;

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
import com.infolion.xdss3.fundadjustment.domain.FundAdjustment;
import com.infolion.xdss3.fundadjustment.service.FundAdjustmentService;
import com.infolion.xdss3.fundadjustment.dao.FundAdjustmentHibernateDao;

/**
 * <pre>
 * 资金占用调整(FundAdjustment)服务类
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
public class FundAdjustmentServiceGen extends BaseService
{
	protected Log log = LogFactory.getFactory().getLog(this.getClass());

	@Autowired
	protected FundAdjustmentHibernateDao fundAdjustmentHibernateDao;
	
	public void setFundAdjustmentHibernateDao(FundAdjustmentHibernateDao fundAdjustmentHibernateDao)
	{
		this.fundAdjustmentHibernateDao = fundAdjustmentHibernateDao;
	}
	
     /**
	 * 注入扩展服务类
	 */
	 @Autowired(required = false)
	 public void setAdvanceService(@Qualifier("fundAdjustmentAdvanceService") AdvanceService advanceService)
	 {
	  	super.setAdvanceService(advanceService);
	 }   
	 
	   
	/**
	 * 根据主键ID,取得资金占用调整实例
	 * @param id
	 * @return
	 */
	public FundAdjustment _getDetached(String id)
	{		
	    FundAdjustment fundAdjustment = new FundAdjustment();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  fundAdjustment = fundAdjustmentHibernateDao.getDetached(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(fundAdjustment);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    fundAdjustment.setOperationType(operationType);
	    
		return fundAdjustment;	
	}
	
	/**
	 * 根据主键ID,取得资金占用调整实例
	 * @param id
	 * @return
	 */
	public FundAdjustment _get(String id)
	{		
	    FundAdjustment fundAdjustment = new FundAdjustment();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  fundAdjustment = fundAdjustmentHibernateDao.get(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(fundAdjustment);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    fundAdjustment.setOperationType(operationType);
	    
		return fundAdjustment;	
	}
	
	/**
	 * 根据主键ID,取得资金占用调整实例
	 * @param id
	 * @return
	 */
	public FundAdjustment _getForEdit(String id)
	{		
	    FundAdjustment fundAdjustment = _get(id);
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = fundAdjustment.getOperationType();
	    if(OperationType.READONLY.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotEdit);
	    }

		return fundAdjustment;	
	}
	
	/**
	 * 根据主键ID,取得资金占用调整实例副本
	 * @param id
	 * @return
	 */
	public FundAdjustment _getEntityCopy(String id)
	{		
	    FundAdjustment fundAdjustment = new FundAdjustment();
		FundAdjustment fundAdjustmentOld = this._get(id);
		try
		{
			BeanUtils.copyProperties(fundAdjustment, fundAdjustmentOld);
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (InvocationTargetException e)
		{
			e.printStackTrace();
		}
		fundAdjustment.setFundno(null); 
		//fundAdjustment.setFundid(null); 
		return fundAdjustment;	
	}
	
	/**
	 * 删除  
	 *   
	 * @param fundAdjustment
	 */
	public void _delete(FundAdjustment fundAdjustment)
	{
		if (null != advanceService)
			advanceService.preDelete(fundAdjustment);
	
 		LockService.isBoInstanceLocked(fundAdjustment,FundAdjustment.class);
		fundAdjustmentHibernateDao.remove(fundAdjustment);

		if (null != advanceService)
			advanceService.postDelete(fundAdjustment);
	}

	/**
	 * 根据主键删除
	 * 
	 * @param fundAdjustmentId
	 */
	public void _delete(String fundAdjustmentId,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(fundAdjustmentId))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("fundid"));
		FundAdjustment fundAdjustment = this.fundAdjustmentHibernateDao.load(fundAdjustmentId);
		_delete(fundAdjustment);
	}
	
	/**
	 * 删除  
	 *   
	 * @param Set<FundAdjustment> fundAdjustments
	 */
	public void _deletes(Set<FundAdjustment> fundAdjustments,BusinessObject businessObject)
	{
		if (null == fundAdjustments || fundAdjustments.size() < 1)
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoText());
		Iterator<FundAdjustment> it = fundAdjustments.iterator();
		while (it.hasNext())
		{
			FundAdjustment fundAdjustment = (FundAdjustment) it.next();
			_delete(fundAdjustment);
		}
	}
	
	/**
	 * 根据多个主键(由，分割)删除多个对象
	 * 
	 * @param fundAdjustmentIds
	 */
	public void _deletes(String fundAdjustmentIds,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(fundAdjustmentIds))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("fundid"));
		String[] ids = StringUtils.splitString(fundAdjustmentIds);
		for (int i = 0; i < ids.length; i++)
		{
			_delete(ids[i],businessObject);
		}
	}
	/**
	 * 提交工作流
	 * 
	 * @param fundAdjustment
	 */
	public void _submitProcess(FundAdjustment fundAdjustment
	,BusinessObject businessObject)
	{
		String id = fundAdjustment.getFundid();
        /**
		if (StringUtils.isNullBlank(id))
		{
			_save(fundAdjustment);
		}
		else
		{
			_update(fundAdjustment
			, businessObject);
		}**/
		
		String taskId = fundAdjustment.getWorkflowTaskId();
		id = fundAdjustment.getFundid();
		if (null == taskId || "".equals(taskId))
			WorkflowService.createAndSignalProcessInstance(fundAdjustment, id);
		else
			WorkflowService.signalProcessInstance(fundAdjustment, id, null);
	}

	/**
	 * 保存或更新资金占用调整
	 * 保存  
	 *  
	 * @param fundAdjustment
	 */
	public void _update(FundAdjustment fundAdjustment
,BusinessObject businessObject	
	)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(fundAdjustment);
		fundAdjustmentHibernateDao.saveOrUpdate(fundAdjustment);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(fundAdjustment);
	}
	
	/**
	 * 保存  
	 *   
	 * @param fundAdjustment
	 */
	public void _save(FundAdjustment fundAdjustment)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(fundAdjustment);
		// 对id赋空值，使之执行insert操作(适应复制创建场景)
		fundAdjustment.setFundid(null);
                                  		fundAdjustmentHibernateDao.saveOrUpdate(fundAdjustment);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(fundAdjustment);
	}
	
	/**
	 *
	 * 保存  
	 *   
	 * @param fundAdjustment
	 */
	public void _saveOrUpdate(FundAdjustment fundAdjustment
,BusinessObject businessObject		
	)
	{
if (StringUtils.isNullBlank(fundAdjustment.getFundno()))
{
String fundno = NumberService.getNextObjectNumber("FundNo", fundAdjustment);
fundAdjustment.setFundno(fundno);
}		if (StringUtils.isNullBlank(fundAdjustment.getFundid()))
		{	
			_save(fundAdjustment);
		}
		else
		{
			_update(fundAdjustment
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