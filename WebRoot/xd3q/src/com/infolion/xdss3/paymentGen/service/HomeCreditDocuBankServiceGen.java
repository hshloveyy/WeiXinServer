/*
 * @(#)HomeCreditDocuBankServiceGen.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2013年11月19日 11点36分39秒
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
import com.infolion.xdss3.payment.domain.HomeCreditDocuBank;
import com.infolion.xdss3.payment.service.HomeCreditDocuBankService;
import com.infolion.xdss3.payment.dao.HomeCreditDocuBankHibernateDao;

/**
 * <pre>
 * 押汇银行(HomeCreditDocuBank)服务类
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
public class HomeCreditDocuBankServiceGen extends BaseService
{
	protected Log log = LogFactory.getFactory().getLog(this.getClass());

	@Autowired
	protected HomeCreditDocuBankHibernateDao homeCreditDocuBankHibernateDao;
	
	public void setHomeCreditDocuBankHibernateDao(HomeCreditDocuBankHibernateDao homeCreditDocuBankHibernateDao)
	{
		this.homeCreditDocuBankHibernateDao = homeCreditDocuBankHibernateDao;
	}
	
     /**
	 * 注入扩展服务类
	 */
	 @Autowired(required = false)
	 public void setAdvanceService(@Qualifier("homeCreditDocuBankAdvanceService") AdvanceService advanceService)
	 {
	  	super.setAdvanceService(advanceService);
	 }   
	 
	   
	/**
	 * 根据主键ID,取得押汇银行实例
	 * @param id
	 * @return
	 */
	public HomeCreditDocuBank _getDetached(String id)
	{		
	    HomeCreditDocuBank homeCreditDocuBank = new HomeCreditDocuBank();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  homeCreditDocuBank = homeCreditDocuBankHibernateDao.getDetached(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(homeCreditDocuBank);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    homeCreditDocuBank.setOperationType(operationType);
	    
		return homeCreditDocuBank;	
	}
	
	/**
	 * 根据主键ID,取得押汇银行实例
	 * @param id
	 * @return
	 */
	public HomeCreditDocuBank _get(String id)
	{		
	    HomeCreditDocuBank homeCreditDocuBank = new HomeCreditDocuBank();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  homeCreditDocuBank = homeCreditDocuBankHibernateDao.get(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(homeCreditDocuBank);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    homeCreditDocuBank.setOperationType(operationType);
	    
		return homeCreditDocuBank;	
	}
	
	/**
	 * 根据主键ID,取得押汇银行实例
	 * @param id
	 * @return
	 */
	public HomeCreditDocuBank _getForEdit(String id)
	{		
	    HomeCreditDocuBank homeCreditDocuBank = _get(id);
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = homeCreditDocuBank.getOperationType();
	    if(OperationType.READONLY.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotEdit);
	    }

		return homeCreditDocuBank;	
	}
	
	/**
	 * 根据主键ID,取得押汇银行实例副本
	 * @param id
	 * @return
	 */
	public HomeCreditDocuBank _getEntityCopy(String id)
	{		
	    HomeCreditDocuBank homeCreditDocuBank = new HomeCreditDocuBank();
		HomeCreditDocuBank homeCreditDocuBankOld = this._get(id);
		try
		{
			BeanUtils.copyProperties(homeCreditDocuBank, homeCreditDocuBankOld);
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (InvocationTargetException e)
		{
			e.printStackTrace();
		}
		//homeCreditDocuBank.setDocuaryitemid(null); 
		return homeCreditDocuBank;	
	}
	
	/**
	 * 删除  
	 *   
	 * @param homeCreditDocuBank
	 */
	public void _delete(HomeCreditDocuBank homeCreditDocuBank)
	{
		if (null != advanceService)
			advanceService.preDelete(homeCreditDocuBank);
	
 		LockService.isBoInstanceLocked(homeCreditDocuBank,HomeCreditDocuBank.class);
		homeCreditDocuBankHibernateDao.remove(homeCreditDocuBank);

		if (null != advanceService)
			advanceService.postDelete(homeCreditDocuBank);
	}

	/**
	 * 根据主键删除
	 * 
	 * @param homeCreditDocuBankId
	 */
	public void _delete(String homeCreditDocuBankId,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(homeCreditDocuBankId))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("docuaryitemid"));
		HomeCreditDocuBank homeCreditDocuBank = this.homeCreditDocuBankHibernateDao.load(homeCreditDocuBankId);
		_delete(homeCreditDocuBank);
	}
	
	/**
	 * 删除  
	 *   
	 * @param Set<HomeCreditDocuBank> homeCreditDocuBanks
	 */
	public void _deletes(Set<HomeCreditDocuBank> homeCreditDocuBanks,BusinessObject businessObject)
	{
		if (null == homeCreditDocuBanks || homeCreditDocuBanks.size() < 1)
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoText());
		Iterator<HomeCreditDocuBank> it = homeCreditDocuBanks.iterator();
		while (it.hasNext())
		{
			HomeCreditDocuBank homeCreditDocuBank = (HomeCreditDocuBank) it.next();
			_delete(homeCreditDocuBank);
		}
	}
	
	/**
	 * 根据多个主键(由，分割)删除多个对象
	 * 
	 * @param homeCreditDocuBankIds
	 */
	public void _deletes(String homeCreditDocuBankIds,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(homeCreditDocuBankIds))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("docuaryitemid"));
		String[] ids = StringUtils.splitString(homeCreditDocuBankIds);
		for (int i = 0; i < ids.length; i++)
		{
			_delete(ids[i],businessObject);
		}
	}
	/**
	 * 提交工作流
	 * 
	 * @param homeCreditDocuBank
	 */
	public void _submitProcess(HomeCreditDocuBank homeCreditDocuBank
	,BusinessObject businessObject)
	{
		String id = homeCreditDocuBank.getDocuaryitemid();
        /**
		if (StringUtils.isNullBlank(id))
		{
			_save(homeCreditDocuBank);
		}
		else
		{
			_update(homeCreditDocuBank
			, businessObject);
		}**/
		
		String taskId = homeCreditDocuBank.getWorkflowTaskId();
		id = homeCreditDocuBank.getDocuaryitemid();
		if (null == taskId || "".equals(taskId))
			WorkflowService.createAndSignalProcessInstance(homeCreditDocuBank, id);
		else
			WorkflowService.signalProcessInstance(homeCreditDocuBank, id, null);
	}

	/**
	 * 保存或更新押汇银行
	 * 保存  
	 *  
	 * @param homeCreditDocuBank
	 */
	public void _update(HomeCreditDocuBank homeCreditDocuBank
,BusinessObject businessObject	
	)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(homeCreditDocuBank);
		homeCreditDocuBankHibernateDao.saveOrUpdate(homeCreditDocuBank);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(homeCreditDocuBank);
	}
	
	/**
	 * 保存  
	 *   
	 * @param homeCreditDocuBank
	 */
	public void _save(HomeCreditDocuBank homeCreditDocuBank)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(homeCreditDocuBank);
		// 对id赋空值，使之执行insert操作(适应复制创建场景)
		homeCreditDocuBank.setDocuaryitemid(null);
                    		homeCreditDocuBankHibernateDao.saveOrUpdate(homeCreditDocuBank);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(homeCreditDocuBank);
	}
	
	/**
	 *
	 * 保存  
	 *   
	 * @param homeCreditDocuBank
	 */
	public void _saveOrUpdate(HomeCreditDocuBank homeCreditDocuBank
,BusinessObject businessObject		
	)
	{
		if (StringUtils.isNullBlank(homeCreditDocuBank.getDocuaryitemid()))
		{	
			_save(homeCreditDocuBank);
		}
		else
		{
			_update(homeCreditDocuBank
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