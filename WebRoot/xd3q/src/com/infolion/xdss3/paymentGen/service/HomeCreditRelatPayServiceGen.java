/*
 * @(#)HomeCreditRelatPayServiceGen.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2013年11月19日 11点37分04秒
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
import com.infolion.xdss3.payment.domain.HomeCreditRelatPay;
import com.infolion.xdss3.payment.service.HomeCreditRelatPayService;
import com.infolion.xdss3.payment.dao.HomeCreditRelatPayHibernateDao;

/**
 * <pre>
 * 相关单据(HomeCreditRelatPay)服务类
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
public class HomeCreditRelatPayServiceGen extends BaseService
{
	protected Log log = LogFactory.getFactory().getLog(this.getClass());

	@Autowired
	protected HomeCreditRelatPayHibernateDao homeCreditRelatPayHibernateDao;
	
	public void setHomeCreditRelatPayHibernateDao(HomeCreditRelatPayHibernateDao homeCreditRelatPayHibernateDao)
	{
		this.homeCreditRelatPayHibernateDao = homeCreditRelatPayHibernateDao;
	}
	
     /**
	 * 注入扩展服务类
	 */
	 @Autowired(required = false)
	 public void setAdvanceService(@Qualifier("homeCreditRelatPayAdvanceService") AdvanceService advanceService)
	 {
	  	super.setAdvanceService(advanceService);
	 }   
	 
	   
	/**
	 * 根据主键ID,取得相关单据实例
	 * @param id
	 * @return
	 */
	public HomeCreditRelatPay _getDetached(String id)
	{		
	    HomeCreditRelatPay homeCreditRelatPay = new HomeCreditRelatPay();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  homeCreditRelatPay = homeCreditRelatPayHibernateDao.getDetached(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(homeCreditRelatPay);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    homeCreditRelatPay.setOperationType(operationType);
	    
		return homeCreditRelatPay;	
	}
	
	/**
	 * 根据主键ID,取得相关单据实例
	 * @param id
	 * @return
	 */
	public HomeCreditRelatPay _get(String id)
	{		
	    HomeCreditRelatPay homeCreditRelatPay = new HomeCreditRelatPay();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  homeCreditRelatPay = homeCreditRelatPayHibernateDao.get(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(homeCreditRelatPay);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    homeCreditRelatPay.setOperationType(operationType);
	    
		return homeCreditRelatPay;	
	}
	
	/**
	 * 根据主键ID,取得相关单据实例
	 * @param id
	 * @return
	 */
	public HomeCreditRelatPay _getForEdit(String id)
	{		
	    HomeCreditRelatPay homeCreditRelatPay = _get(id);
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = homeCreditRelatPay.getOperationType();
	    if(OperationType.READONLY.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotEdit);
	    }

		return homeCreditRelatPay;	
	}
	
	/**
	 * 根据主键ID,取得相关单据实例副本
	 * @param id
	 * @return
	 */
	public HomeCreditRelatPay _getEntityCopy(String id)
	{		
	    HomeCreditRelatPay homeCreditRelatPay = new HomeCreditRelatPay();
		HomeCreditRelatPay homeCreditRelatPayOld = this._get(id);
		try
		{
			BeanUtils.copyProperties(homeCreditRelatPay, homeCreditRelatPayOld);
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (InvocationTargetException e)
		{
			e.printStackTrace();
		}
		//homeCreditRelatPay.setRelatedpaymentid(null); 
		return homeCreditRelatPay;	
	}
	
	/**
	 * 删除  
	 *   
	 * @param homeCreditRelatPay
	 */
	public void _delete(HomeCreditRelatPay homeCreditRelatPay)
	{
		if (null != advanceService)
			advanceService.preDelete(homeCreditRelatPay);
	
 		LockService.isBoInstanceLocked(homeCreditRelatPay,HomeCreditRelatPay.class);
		homeCreditRelatPayHibernateDao.remove(homeCreditRelatPay);

		if (null != advanceService)
			advanceService.postDelete(homeCreditRelatPay);
	}

	/**
	 * 根据主键删除
	 * 
	 * @param homeCreditRelatPayId
	 */
	public void _delete(String homeCreditRelatPayId,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(homeCreditRelatPayId))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("relatedpaymentid"));
		HomeCreditRelatPay homeCreditRelatPay = this.homeCreditRelatPayHibernateDao.load(homeCreditRelatPayId);
		_delete(homeCreditRelatPay);
	}
	
	/**
	 * 删除  
	 *   
	 * @param Set<HomeCreditRelatPay> homeCreditRelatPays
	 */
	public void _deletes(Set<HomeCreditRelatPay> homeCreditRelatPays,BusinessObject businessObject)
	{
		if (null == homeCreditRelatPays || homeCreditRelatPays.size() < 1)
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoText());
		Iterator<HomeCreditRelatPay> it = homeCreditRelatPays.iterator();
		while (it.hasNext())
		{
			HomeCreditRelatPay homeCreditRelatPay = (HomeCreditRelatPay) it.next();
			_delete(homeCreditRelatPay);
		}
	}
	
	/**
	 * 根据多个主键(由，分割)删除多个对象
	 * 
	 * @param homeCreditRelatPayIds
	 */
	public void _deletes(String homeCreditRelatPayIds,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(homeCreditRelatPayIds))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("relatedpaymentid"));
		String[] ids = StringUtils.splitString(homeCreditRelatPayIds);
		for (int i = 0; i < ids.length; i++)
		{
			_delete(ids[i],businessObject);
		}
	}
	/**
	 * 提交工作流
	 * 
	 * @param homeCreditRelatPay
	 */
	public void _submitProcess(HomeCreditRelatPay homeCreditRelatPay
	,BusinessObject businessObject)
	{
		String id = homeCreditRelatPay.getRelatedpaymentid();
        /**
		if (StringUtils.isNullBlank(id))
		{
			_save(homeCreditRelatPay);
		}
		else
		{
			_update(homeCreditRelatPay
			, businessObject);
		}**/
		
		String taskId = homeCreditRelatPay.getWorkflowTaskId();
		id = homeCreditRelatPay.getRelatedpaymentid();
		if (null == taskId || "".equals(taskId))
			WorkflowService.createAndSignalProcessInstance(homeCreditRelatPay, id);
		else
			WorkflowService.signalProcessInstance(homeCreditRelatPay, id, null);
	}

	/**
	 * 保存或更新相关单据
	 * 保存  
	 *  
	 * @param homeCreditRelatPay
	 */
	public void _update(HomeCreditRelatPay homeCreditRelatPay
,BusinessObject businessObject	
	)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(homeCreditRelatPay);
		homeCreditRelatPayHibernateDao.saveOrUpdate(homeCreditRelatPay);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(homeCreditRelatPay);
	}
	
	/**
	 * 保存  
	 *   
	 * @param homeCreditRelatPay
	 */
	public void _save(HomeCreditRelatPay homeCreditRelatPay)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(homeCreditRelatPay);
		// 对id赋空值，使之执行insert操作(适应复制创建场景)
		homeCreditRelatPay.setRelatedpaymentid(null);
                		homeCreditRelatPayHibernateDao.saveOrUpdate(homeCreditRelatPay);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(homeCreditRelatPay);
	}
	
	/**
	 *
	 * 保存  
	 *   
	 * @param homeCreditRelatPay
	 */
	public void _saveOrUpdate(HomeCreditRelatPay homeCreditRelatPay
,BusinessObject businessObject		
	)
	{
		if (StringUtils.isNullBlank(homeCreditRelatPay.getRelatedpaymentid()))
		{	
			_save(homeCreditRelatPay);
		}
		else
		{
			_update(homeCreditRelatPay
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