/*
 * @(#)HomePaymentCbillServiceGen.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年07月25日 09点58分41秒
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
import com.infolion.xdss3.payment.domain.HomePaymentCbill;
import com.infolion.xdss3.payment.service.HomePaymentCbillService;
import com.infolion.xdss3.payment.dao.HomePaymentCbillHibernateDao;

/**
 * <pre>
 * 内贸付款清票(HomePaymentCbill)服务类
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
public class HomePaymentCbillServiceGen extends BaseService
{
	protected Log log = LogFactory.getFactory().getLog(this.getClass());

	@Autowired
	protected HomePaymentCbillHibernateDao homePaymentCbillHibernateDao;
	
	public void setHomePaymentCbillHibernateDao(HomePaymentCbillHibernateDao homePaymentCbillHibernateDao)
	{
		this.homePaymentCbillHibernateDao = homePaymentCbillHibernateDao;
	}
	
     /**
	 * 注入扩展服务类
	 */
	 @Autowired(required = false)
	 public void setAdvanceService(@Qualifier("homePaymentCbillAdvanceService") AdvanceService advanceService)
	 {
	  	super.setAdvanceService(advanceService);
	 }   
	 
	   
	/**
	 * 根据主键ID,取得内贸付款清票实例
	 * @param id
	 * @return
	 */
	public HomePaymentCbill _getDetached(String id)
	{		
	    HomePaymentCbill homePaymentCbill = new HomePaymentCbill();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  homePaymentCbill = homePaymentCbillHibernateDao.getDetached(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(homePaymentCbill);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    homePaymentCbill.setOperationType(operationType);
	    
		return homePaymentCbill;	
	}
	
	/**
	 * 根据主键ID,取得内贸付款清票实例
	 * @param id
	 * @return
	 */
	public HomePaymentCbill _get(String id)
	{		
	    HomePaymentCbill homePaymentCbill = new HomePaymentCbill();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  homePaymentCbill = homePaymentCbillHibernateDao.get(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(homePaymentCbill);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    homePaymentCbill.setOperationType(operationType);
	    
		return homePaymentCbill;	
	}
	
	/**
	 * 根据主键ID,取得内贸付款清票实例
	 * @param id
	 * @return
	 */
	public HomePaymentCbill _getForEdit(String id)
	{		
	    HomePaymentCbill homePaymentCbill = _get(id);
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = homePaymentCbill.getOperationType();
	    if(OperationType.READONLY.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotEdit);
	    }

		return homePaymentCbill;	
	}
	
	/**
	 * 根据主键ID,取得内贸付款清票实例副本
	 * @param id
	 * @return
	 */
	public HomePaymentCbill _getEntityCopy(String id)
	{		
	    HomePaymentCbill homePaymentCbill = new HomePaymentCbill();
		HomePaymentCbill homePaymentCbillOld = this._get(id);
		try
		{
			BeanUtils.copyProperties(homePaymentCbill, homePaymentCbillOld);
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (InvocationTargetException e)
		{
			e.printStackTrace();
		}
		//homePaymentCbill.setPaymentcbillid(null); 
		return homePaymentCbill;	
	}
	
	/**
	 * 删除  
	 *   
	 * @param homePaymentCbill
	 */
	public void _delete(HomePaymentCbill homePaymentCbill)
	{
		if (null != advanceService)
			advanceService.preDelete(homePaymentCbill);
	
 		LockService.isBoInstanceLocked(homePaymentCbill,HomePaymentCbill.class);
		homePaymentCbillHibernateDao.remove(homePaymentCbill);

		if (null != advanceService)
			advanceService.postDelete(homePaymentCbill);
	}

	/**
	 * 根据主键删除
	 * 
	 * @param homePaymentCbillId
	 */
	public void _delete(String homePaymentCbillId,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(homePaymentCbillId))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("paymentcbillid"));
		HomePaymentCbill homePaymentCbill = this.homePaymentCbillHibernateDao.load(homePaymentCbillId);
		_delete(homePaymentCbill);
	}
	
	/**
	 * 删除  
	 *   
	 * @param Set<HomePaymentCbill> homePaymentCbills
	 */
	public void _deletes(Set<HomePaymentCbill> homePaymentCbills,BusinessObject businessObject)
	{
		if (null == homePaymentCbills || homePaymentCbills.size() < 1)
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoText());
		Iterator<HomePaymentCbill> it = homePaymentCbills.iterator();
		while (it.hasNext())
		{
			HomePaymentCbill homePaymentCbill = (HomePaymentCbill) it.next();
			_delete(homePaymentCbill);
		}
	}
	
	/**
	 * 根据多个主键(由，分割)删除多个对象
	 * 
	 * @param homePaymentCbillIds
	 */
	public void _deletes(String homePaymentCbillIds,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(homePaymentCbillIds))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("paymentcbillid"));
		String[] ids = StringUtils.splitString(homePaymentCbillIds);
		for (int i = 0; i < ids.length; i++)
		{
			_delete(ids[i],businessObject);
		}
	}
	/**
	 * 提交工作流
	 * 
	 * @param homePaymentCbill
	 */
	public void _submitProcess(HomePaymentCbill homePaymentCbill
	,BusinessObject businessObject)
	{
		String id = homePaymentCbill.getPaymentcbillid();
        /**
		if (StringUtils.isNullBlank(id))
		{
			_save(homePaymentCbill);
		}
		else
		{
			_update(homePaymentCbill
			, businessObject);
		}**/
		
		String taskId = homePaymentCbill.getWorkflowTaskId();
		id = homePaymentCbill.getPaymentcbillid();
		if (null == taskId || "".equals(taskId))
			WorkflowService.createAndSignalProcessInstance(homePaymentCbill, id);
		else
			WorkflowService.signalProcessInstance(homePaymentCbill, id, null);
	}

	/**
	 * 保存或更新内贸付款清票
	 * 保存  
	 *  
	 * @param homePaymentCbill
	 */
	public void _update(HomePaymentCbill homePaymentCbill
,BusinessObject businessObject	
	)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(homePaymentCbill);
		homePaymentCbillHibernateDao.saveOrUpdate(homePaymentCbill);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(homePaymentCbill);
	}
	
	/**
	 * 保存  
	 *   
	 * @param homePaymentCbill
	 */
	public void _save(HomePaymentCbill homePaymentCbill)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(homePaymentCbill);
		// 对id赋空值，使之执行insert操作(适应复制创建场景)
		homePaymentCbill.setPaymentcbillid(null);
                                      		homePaymentCbillHibernateDao.saveOrUpdate(homePaymentCbill);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(homePaymentCbill);
	}
	
	/**
	 *
	 * 保存  
	 *   
	 * @param homePaymentCbill
	 */
	public void _saveOrUpdate(HomePaymentCbill homePaymentCbill
,BusinessObject businessObject		
	)
	{
		if (StringUtils.isNullBlank(homePaymentCbill.getPaymentcbillid()))
		{	
			_save(homePaymentCbill);
		}
		else
		{
			_update(homePaymentCbill
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