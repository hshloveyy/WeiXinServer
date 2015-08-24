/*
 * @(#)HomeCreditRebankServiceGen.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2013年11月19日 11点36分50秒
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
import com.infolion.xdss3.payment.domain.HomeCreditRebank;
import com.infolion.xdss3.payment.service.HomeCreditRebankService;
import com.infolion.xdss3.payment.dao.HomeCreditRebankHibernateDao;

/**
 * <pre>
 * 国内证还押汇银行(HomeCreditRebank)服务类
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
public class HomeCreditRebankServiceGen extends BaseService
{
	protected Log log = LogFactory.getFactory().getLog(this.getClass());

	@Autowired
	protected HomeCreditRebankHibernateDao homeCreditRebankHibernateDao;
	
	public void setHomeCreditRebankHibernateDao(HomeCreditRebankHibernateDao homeCreditRebankHibernateDao)
	{
		this.homeCreditRebankHibernateDao = homeCreditRebankHibernateDao;
	}
	
     /**
	 * 注入扩展服务类
	 */
	 @Autowired(required = false)
	 public void setAdvanceService(@Qualifier("homeCreditRebankAdvanceService") AdvanceService advanceService)
	 {
	  	super.setAdvanceService(advanceService);
	 }   
	 
	   
	/**
	 * 根据主键ID,取得国内证还押汇银行实例
	 * @param id
	 * @return
	 */
	public HomeCreditRebank _getDetached(String id)
	{		
	    HomeCreditRebank homeCreditRebank = new HomeCreditRebank();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  homeCreditRebank = homeCreditRebankHibernateDao.getDetached(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(homeCreditRebank);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    homeCreditRebank.setOperationType(operationType);
	    
		return homeCreditRebank;	
	}
	
	/**
	 * 根据主键ID,取得国内证还押汇银行实例
	 * @param id
	 * @return
	 */
	public HomeCreditRebank _get(String id)
	{		
	    HomeCreditRebank homeCreditRebank = new HomeCreditRebank();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  homeCreditRebank = homeCreditRebankHibernateDao.get(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(homeCreditRebank);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    homeCreditRebank.setOperationType(operationType);
	    
		return homeCreditRebank;	
	}
	
	/**
	 * 根据主键ID,取得国内证还押汇银行实例
	 * @param id
	 * @return
	 */
	public HomeCreditRebank _getForEdit(String id)
	{		
	    HomeCreditRebank homeCreditRebank = _get(id);
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = homeCreditRebank.getOperationType();
	    if(OperationType.READONLY.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotEdit);
	    }

		return homeCreditRebank;	
	}
	
	/**
	 * 根据主键ID,取得国内证还押汇银行实例副本
	 * @param id
	 * @return
	 */
	public HomeCreditRebank _getEntityCopy(String id)
	{		
	    HomeCreditRebank homeCreditRebank = new HomeCreditRebank();
		HomeCreditRebank homeCreditRebankOld = this._get(id);
		try
		{
			BeanUtils.copyProperties(homeCreditRebank, homeCreditRebankOld);
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (InvocationTargetException e)
		{
			e.printStackTrace();
		}
		//homeCreditRebank.setBankitemid(null); 
		return homeCreditRebank;	
	}
	
	/**
	 * 删除  
	 *   
	 * @param homeCreditRebank
	 */
	public void _delete(HomeCreditRebank homeCreditRebank)
	{
		if (null != advanceService)
			advanceService.preDelete(homeCreditRebank);
	
 		LockService.isBoInstanceLocked(homeCreditRebank,HomeCreditRebank.class);
		homeCreditRebankHibernateDao.remove(homeCreditRebank);

		if (null != advanceService)
			advanceService.postDelete(homeCreditRebank);
	}

	/**
	 * 根据主键删除
	 * 
	 * @param homeCreditRebankId
	 */
	public void _delete(String homeCreditRebankId,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(homeCreditRebankId))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("bankitemid"));
		HomeCreditRebank homeCreditRebank = this.homeCreditRebankHibernateDao.load(homeCreditRebankId);
		_delete(homeCreditRebank);
	}
	
	/**
	 * 删除  
	 *   
	 * @param Set<HomeCreditRebank> homeCreditRebanks
	 */
	public void _deletes(Set<HomeCreditRebank> homeCreditRebanks,BusinessObject businessObject)
	{
		if (null == homeCreditRebanks || homeCreditRebanks.size() < 1)
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoText());
		Iterator<HomeCreditRebank> it = homeCreditRebanks.iterator();
		while (it.hasNext())
		{
			HomeCreditRebank homeCreditRebank = (HomeCreditRebank) it.next();
			_delete(homeCreditRebank);
		}
	}
	
	/**
	 * 根据多个主键(由，分割)删除多个对象
	 * 
	 * @param homeCreditRebankIds
	 */
	public void _deletes(String homeCreditRebankIds,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(homeCreditRebankIds))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("bankitemid"));
		String[] ids = StringUtils.splitString(homeCreditRebankIds);
		for (int i = 0; i < ids.length; i++)
		{
			_delete(ids[i],businessObject);
		}
	}
	/**
	 * 提交工作流
	 * 
	 * @param homeCreditRebank
	 */
	public void _submitProcess(HomeCreditRebank homeCreditRebank
	,BusinessObject businessObject)
	{
		String id = homeCreditRebank.getBankitemid();
        /**
		if (StringUtils.isNullBlank(id))
		{
			_save(homeCreditRebank);
		}
		else
		{
			_update(homeCreditRebank
			, businessObject);
		}**/
		
		String taskId = homeCreditRebank.getWorkflowTaskId();
		id = homeCreditRebank.getBankitemid();
		if (null == taskId || "".equals(taskId))
			WorkflowService.createAndSignalProcessInstance(homeCreditRebank, id);
		else
			WorkflowService.signalProcessInstance(homeCreditRebank, id, null);
	}

	/**
	 * 保存或更新国内证还押汇银行
	 * 保存  
	 *  
	 * @param homeCreditRebank
	 */
	public void _update(HomeCreditRebank homeCreditRebank
,BusinessObject businessObject	
	)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(homeCreditRebank);
		homeCreditRebankHibernateDao.saveOrUpdate(homeCreditRebank);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(homeCreditRebank);
	}
	
	/**
	 * 保存  
	 *   
	 * @param homeCreditRebank
	 */
	public void _save(HomeCreditRebank homeCreditRebank)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(homeCreditRebank);
		// 对id赋空值，使之执行insert操作(适应复制创建场景)
		homeCreditRebank.setBankitemid(null);
                                            		homeCreditRebankHibernateDao.saveOrUpdate(homeCreditRebank);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(homeCreditRebank);
	}
	
	/**
	 *
	 * 保存  
	 *   
	 * @param homeCreditRebank
	 */
	public void _saveOrUpdate(HomeCreditRebank homeCreditRebank
,BusinessObject businessObject		
	)
	{
		if (StringUtils.isNullBlank(homeCreditRebank.getBankitemid()))
		{	
			_save(homeCreditRebank);
		}
		else
		{
			_update(homeCreditRebank
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