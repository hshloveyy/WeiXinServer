/*
 * @(#)BankInfoServiceGen.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年06月29日 11点38分27秒
 *  描　述：创建
 */
package com.infolion.xdss3.bankInfoGen.service;

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
import com.infolion.xdss3.bankInfo.domain.BankInfo;
import com.infolion.xdss3.bankInfo.service.BankInfoService;
import com.infolion.xdss3.bankInfo.dao.BankInfoHibernateDao;

/**
 * <pre>
 * 银行信息(BankInfo)服务类
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
public class BankInfoServiceGen extends BaseService
{
	protected Log log = LogFactory.getFactory().getLog(this.getClass());

	@Autowired
	protected BankInfoHibernateDao bankInfoHibernateDao;
	
	public void setBankInfoHibernateDao(BankInfoHibernateDao bankInfoHibernateDao)
	{
		this.bankInfoHibernateDao = bankInfoHibernateDao;
	}
	
     /**
	 * 注入扩展服务类
	 */
	 @Autowired(required = false)
	 public void setAdvanceService(@Qualifier("bankInfoAdvanceService") AdvanceService advanceService)
	 {
	  	super.setAdvanceService(advanceService);
	 }   
	 
	   
	/**
	 * 根据主键ID,取得银行信息实例
	 * @param id
	 * @return
	 */
	public BankInfo _getDetached(String id)
	{		
	    BankInfo bankInfo = new BankInfo();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  bankInfo = bankInfoHibernateDao.getDetached(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(bankInfo);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    bankInfo.setOperationType(operationType);
	    
		return bankInfo;	
	}
	
	/**
	 * 根据主键ID,取得银行信息实例
	 * @param id
	 * @return
	 */
	public BankInfo _get(String id)
	{		
	    BankInfo bankInfo = new BankInfo();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  bankInfo = bankInfoHibernateDao.get(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(bankInfo);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    bankInfo.setOperationType(operationType);
	    
		return bankInfo;	
	}
	
	/**
	 * 根据主键ID,取得银行信息实例
	 * @param id
	 * @return
	 */
	public BankInfo _getForEdit(String id)
	{		
	    BankInfo bankInfo = _get(id);
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = bankInfo.getOperationType();
	    if(OperationType.READONLY.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotEdit);
	    }

		return bankInfo;	
	}
	
	/**
	 * 根据主键ID,取得银行信息实例副本
	 * @param id
	 * @return
	 */
	public BankInfo _getEntityCopy(String id)
	{		
	    BankInfo bankInfo = new BankInfo();
		BankInfo bankInfoOld = this._get(id);
		try
		{
			BeanUtils.copyProperties(bankInfo, bankInfoOld);
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (InvocationTargetException e)
		{
			e.printStackTrace();
		}
		//bankInfo.setBank_id(null); 
		return bankInfo;	
	}
	
	/**
	 * 删除  
	 *   
	 * @param bankInfo
	 */
	public void _delete(BankInfo bankInfo)
	{
		if (null != advanceService)
			advanceService.preDelete(bankInfo);
	
 		LockService.isBoInstanceLocked(bankInfo,BankInfo.class);
		bankInfoHibernateDao.remove(bankInfo);

		if (null != advanceService)
			advanceService.postDelete(bankInfo);
	}

	/**
	 * 根据主键删除
	 * 
	 * @param bankInfoId
	 */
	public void _delete(String bankInfoId,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(bankInfoId))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("bank_id"));
		BankInfo bankInfo = this.bankInfoHibernateDao.load(bankInfoId);
		_delete(bankInfo);
	}
	
	/**
	 * 删除  
	 *   
	 * @param Set<BankInfo> bankInfos
	 */
	public void _deletes(Set<BankInfo> bankInfos,BusinessObject businessObject)
	{
		if (null == bankInfos || bankInfos.size() < 1)
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoText());
		Iterator<BankInfo> it = bankInfos.iterator();
		while (it.hasNext())
		{
			BankInfo bankInfo = (BankInfo) it.next();
			_delete(bankInfo);
		}
	}
	
	/**
	 * 根据多个主键(由，分割)删除多个对象
	 * 
	 * @param bankInfoIds
	 */
	public void _deletes(String bankInfoIds,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(bankInfoIds))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("bank_id"));
		String[] ids = StringUtils.splitString(bankInfoIds);
		for (int i = 0; i < ids.length; i++)
		{
			_delete(ids[i],businessObject);
		}
	}
	/**
	 * 提交工作流
	 * 
	 * @param bankInfo
	 */
	public void _submitProcess(BankInfo bankInfo
	,BusinessObject businessObject)
	{
		String id = bankInfo.getBank_id();
        /**
		if (StringUtils.isNullBlank(id))
		{
			_save(bankInfo);
		}
		else
		{
			_update(bankInfo
			, businessObject);
		}**/
		
		String taskId = bankInfo.getWorkflowTaskId();
		id = bankInfo.getBank_id();
		if (null == taskId || "".equals(taskId))
			WorkflowService.createAndSignalProcessInstance(bankInfo, id);
		else
			WorkflowService.signalProcessInstance(bankInfo, id, null);
	}

	/**
	 * 保存或更新银行信息
	 * 保存  
	 *  
	 * @param bankInfo
	 */
	public void _update(BankInfo bankInfo
,BusinessObject businessObject	
	)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(bankInfo);
		bankInfoHibernateDao.saveOrUpdate(bankInfo);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(bankInfo);
	}
	
	/**
	 * 保存  
	 *   
	 * @param bankInfo
	 */
	public void _save(BankInfo bankInfo)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(bankInfo);
		// 对id赋空值，使之执行insert操作(适应复制创建场景)
		bankInfo.setBank_id(null);
            		bankInfoHibernateDao.saveOrUpdate(bankInfo);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(bankInfo);
	}
	
	/**
	 *
	 * 保存  
	 *   
	 * @param bankInfo
	 */
	public void _saveOrUpdate(BankInfo bankInfo
,BusinessObject businessObject		
	)
	{
		if (StringUtils.isNullBlank(bankInfo.getBank_id()))
		{	
			_save(bankInfo);
		}
		else
		{
			_update(bankInfo
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