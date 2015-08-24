/*
 * @(#)HomeCreditPayCbillServiceGen.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2013年11月19日 11点36分16秒
 *  描　述：创建
 */
package com.infolion.xdss3.paymentGen.service;

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
import com.infolion.xdss3.payment.domain.HomeCreditPayCbill;
import com.infolion.xdss3.payment.service.HomeCreditPayCbillService;
import com.infolion.xdss3.payment.dao.HomeCreditPayCbillHibernateDao;

/**
 * <pre>
 * 发票清帐(HomeCreditPayCbill)服务类
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
public class HomeCreditPayCbillServiceGen extends BaseService
{
	protected Log log = LogFactory.getFactory().getLog(this.getClass());

	@Autowired
	protected HomeCreditPayCbillHibernateDao homeCreditPayCbillHibernateDao;
	
	public void setHomeCreditPayCbillHibernateDao(HomeCreditPayCbillHibernateDao homeCreditPayCbillHibernateDao)
	{
		this.homeCreditPayCbillHibernateDao = homeCreditPayCbillHibernateDao;
	}
	
     /**
	 * 注入扩展服务类
	 */
	 @Autowired(required = false)
	 public void setAdvanceService(@Qualifier("homeCreditPayCbillAdvanceService") AdvanceService advanceService)
	 {
	  	super.setAdvanceService(advanceService);
	 }   
	 
	   
	/**
	 * 根据主键ID,取得发票清帐实例
	 * @param id
	 * @return
	 */
	public HomeCreditPayCbill _getDetached(String id)
	{		
	    HomeCreditPayCbill homeCreditPayCbill = new HomeCreditPayCbill();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  homeCreditPayCbill = homeCreditPayCbillHibernateDao.getDetached(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(homeCreditPayCbill);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    homeCreditPayCbill.setOperationType(operationType);
	    
		return homeCreditPayCbill;	
	}
	
	/**
	 * 根据主键ID,取得发票清帐实例
	 * @param id
	 * @return
	 */
	public HomeCreditPayCbill _get(String id)
	{		
	    HomeCreditPayCbill homeCreditPayCbill = new HomeCreditPayCbill();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  homeCreditPayCbill = homeCreditPayCbillHibernateDao.get(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(homeCreditPayCbill);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    homeCreditPayCbill.setOperationType(operationType);
	    
		return homeCreditPayCbill;	
	}
	
	/**
	 * 根据主键ID,取得发票清帐实例
	 * @param id
	 * @return
	 */
	public HomeCreditPayCbill _getForEdit(String id)
	{		
	    HomeCreditPayCbill homeCreditPayCbill = _get(id);
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = homeCreditPayCbill.getOperationType();
	    if(OperationType.READONLY.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotEdit);
	    }

		return homeCreditPayCbill;	
	}
	
	/**
	 * 根据主键ID,取得发票清帐实例副本
	 * @param id
	 * @return
	 */
	public HomeCreditPayCbill _getEntityCopy(String id)
	{		
	    HomeCreditPayCbill homeCreditPayCbill = new HomeCreditPayCbill();
		HomeCreditPayCbill homeCreditPayCbillOld = this._get(id);
		try
		{
			BeanUtils.copyProperties(homeCreditPayCbill, homeCreditPayCbillOld);
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (InvocationTargetException e)
		{
			e.printStackTrace();
		}
		//homeCreditPayCbill.setPaymentcbillid(null); 
		return homeCreditPayCbill;	
	}
	
	/**
	 * 删除  
	 *   
	 * @param homeCreditPayCbill
	 */
	public void _delete(HomeCreditPayCbill homeCreditPayCbill)
	{
		if (null != advanceService)
			advanceService.preDelete(homeCreditPayCbill);
	
 		LockService.isBoInstanceLocked(homeCreditPayCbill,HomeCreditPayCbill.class);
		homeCreditPayCbillHibernateDao.remove(homeCreditPayCbill);

		if (null != advanceService)
			advanceService.postDelete(homeCreditPayCbill);
	}

	/**
	 * 根据主键删除
	 * 
	 * @param homeCreditPayCbillId
	 */
	public void _delete(String homeCreditPayCbillId,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(homeCreditPayCbillId))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("paymentcbillid"));
		HomeCreditPayCbill homeCreditPayCbill = this.homeCreditPayCbillHibernateDao.load(homeCreditPayCbillId);
		_delete(homeCreditPayCbill);
	}
	
	/**
	 * 删除  
	 *   
	 * @param Set<HomeCreditPayCbill> homeCreditPayCbills
	 */
	public void _deletes(Set<HomeCreditPayCbill> homeCreditPayCbills,BusinessObject businessObject)
	{
		if (null == homeCreditPayCbills || homeCreditPayCbills.size() < 1)
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoText());
		Iterator<HomeCreditPayCbill> it = homeCreditPayCbills.iterator();
		while (it.hasNext())
		{
			HomeCreditPayCbill homeCreditPayCbill = (HomeCreditPayCbill) it.next();
			_delete(homeCreditPayCbill);
		}
	}
	
	/**
	 * 根据多个主键(由，分割)删除多个对象
	 * 
	 * @param homeCreditPayCbillIds
	 */
	public void _deletes(String homeCreditPayCbillIds,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(homeCreditPayCbillIds))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("paymentcbillid"));
		String[] ids = StringUtils.splitString(homeCreditPayCbillIds);
		for (int i = 0; i < ids.length; i++)
		{
			_delete(ids[i],businessObject);
		}
	}
	/**
	 * 提交工作流
	 * 
	 * @param homeCreditPayCbill
	 */
	public void _submitProcess(HomeCreditPayCbill homeCreditPayCbill
	,BusinessObject businessObject)
	{
		String id = homeCreditPayCbill.getPaymentcbillid();
        /**
		if (StringUtils.isNullBlank(id))
		{
			_save(homeCreditPayCbill);
		}
		else
		{
			_update(homeCreditPayCbill
			, businessObject);
		}**/
		
		String taskId = homeCreditPayCbill.getWorkflowTaskId();
		id = homeCreditPayCbill.getPaymentcbillid();
		if (null == taskId || "".equals(taskId))
			WorkflowService.createAndSignalProcessInstance(homeCreditPayCbill, id);
		else
			WorkflowService.signalProcessInstance(homeCreditPayCbill, id, null);
	}

	/**
	 * 保存或更新发票清帐
	 * 保存  
	 *  
	 * @param homeCreditPayCbill
	 */
	public void _update(HomeCreditPayCbill homeCreditPayCbill
,BusinessObject businessObject	
	)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(homeCreditPayCbill);
		homeCreditPayCbillHibernateDao.saveOrUpdate(homeCreditPayCbill);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(homeCreditPayCbill);
	}
	
	/**
	 * 保存  
	 *   
	 * @param homeCreditPayCbill
	 */
	public void _save(HomeCreditPayCbill homeCreditPayCbill)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(homeCreditPayCbill);
		// 对id赋空值，使之执行insert操作(适应复制创建场景)
		homeCreditPayCbill.setPaymentcbillid(null);
                                      		homeCreditPayCbillHibernateDao.saveOrUpdate(homeCreditPayCbill);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(homeCreditPayCbill);
	}
	
	/**
	 *
	 * 保存  
	 *   
	 * @param homeCreditPayCbill
	 */
	public void _saveOrUpdate(HomeCreditPayCbill homeCreditPayCbill
,BusinessObject businessObject		
	)
	{
		if (StringUtils.isNullBlank(homeCreditPayCbill.getPaymentcbillid()))
		{	
			_save(homeCreditPayCbill);
		}
		else
		{
			_update(homeCreditPayCbill
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