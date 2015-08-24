/*
 * @(#)CollectCbillServiceGen.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年08月10日 02点20分25秒
 *  描　述：创建
 */
package com.infolion.xdss3.collectcbillGen.service;

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
import com.infolion.xdss3.collectcbill.domain.CollectCbill;
import com.infolion.xdss3.collectcbill.service.CollectCbillService;
import com.infolion.xdss3.collectcbill.dao.CollectCbillHibernateDao;

/**
 * <pre>
 * 收款清票(CollectCbill)服务类
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
public class CollectCbillServiceGen extends BaseService
{
	protected Log log = LogFactory.getFactory().getLog(this.getClass());

	@Autowired
	protected CollectCbillHibernateDao collectCbillHibernateDao;
	
	public void setCollectCbillHibernateDao(CollectCbillHibernateDao collectCbillHibernateDao)
	{
		this.collectCbillHibernateDao = collectCbillHibernateDao;
	}
	
     /**
	 * 注入扩展服务类
	 */
	 @Autowired(required = false)
	 public void setAdvanceService(@Qualifier("collectCbillAdvanceService") AdvanceService advanceService)
	 {
	  	super.setAdvanceService(advanceService);
	 }   
	 
	   
	/**
	 * 根据主键ID,取得收款清票实例
	 * @param id
	 * @return
	 */
	public CollectCbill _getDetached(String id)
	{		
	    CollectCbill collectCbill = new CollectCbill();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  collectCbill = collectCbillHibernateDao.getDetached(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(collectCbill);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    collectCbill.setOperationType(operationType);
	    
		return collectCbill;	
	}
	
	/**
	 * 根据主键ID,取得收款清票实例
	 * @param id
	 * @return
	 */
	public CollectCbill _get(String id)
	{		
	    CollectCbill collectCbill = new CollectCbill();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  collectCbill = collectCbillHibernateDao.get(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(collectCbill);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    collectCbill.setOperationType(operationType);
	    
		return collectCbill;	
	}
	
	/**
	 * 根据主键ID,取得收款清票实例
	 * @param id
	 * @return
	 */
	public CollectCbill _getForEdit(String id)
	{		
	    CollectCbill collectCbill = _get(id);
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = collectCbill.getOperationType();
	    if(OperationType.READONLY.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotEdit);
	    }

		return collectCbill;	
	}
	
	/**
	 * 根据主键ID,取得收款清票实例副本
	 * @param id
	 * @return
	 */
	public CollectCbill _getEntityCopy(String id)
	{		
	    CollectCbill collectCbill = new CollectCbill();
		CollectCbill collectCbillOld = this._get(id);
		try
		{
			BeanUtils.copyProperties(collectCbill, collectCbillOld);
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (InvocationTargetException e)
		{
			e.printStackTrace();
		}
		//collectCbill.setCollectcbillid(null); 
		return collectCbill;	
	}
	
	/**
	 * 删除  
	 *   
	 * @param collectCbill
	 */
	public void _delete(CollectCbill collectCbill)
	{
		if (null != advanceService)
			advanceService.preDelete(collectCbill);
	
 		LockService.isBoInstanceLocked(collectCbill,CollectCbill.class);
		collectCbillHibernateDao.remove(collectCbill);

		if (null != advanceService)
			advanceService.postDelete(collectCbill);
	}

	/**
	 * 根据主键删除
	 * 
	 * @param collectCbillId
	 */
	public void _delete(String collectCbillId,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(collectCbillId))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("collectcbillid"));
		CollectCbill collectCbill = this.collectCbillHibernateDao.load(collectCbillId);
		_delete(collectCbill);
	}
	
	/**
	 * 删除  
	 *   
	 * @param Set<CollectCbill> collectCbills
	 */
	public void _deletes(Set<CollectCbill> collectCbills,BusinessObject businessObject)
	{
		if (null == collectCbills || collectCbills.size() < 1)
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoText());
		Iterator<CollectCbill> it = collectCbills.iterator();
		while (it.hasNext())
		{
			CollectCbill collectCbill = (CollectCbill) it.next();
			_delete(collectCbill);
		}
	}
	
	/**
	 * 根据多个主键(由，分割)删除多个对象
	 * 
	 * @param collectCbillIds
	 */
	public void _deletes(String collectCbillIds,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(collectCbillIds))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("collectcbillid"));
		String[] ids = StringUtils.splitString(collectCbillIds);
		for (int i = 0; i < ids.length; i++)
		{
			_delete(ids[i],businessObject);
		}
	}
	/**
	 * 提交工作流
	 * 
	 * @param collectCbill
	 */
	public void _submitProcess(CollectCbill collectCbill
	,BusinessObject businessObject)
	{
		String id = collectCbill.getCollectcbillid();
        /**
		if (StringUtils.isNullBlank(id))
		{
			_save(collectCbill);
		}
		else
		{
			_update(collectCbill
			, businessObject);
		}**/
		
		String taskId = collectCbill.getWorkflowTaskId();
		id = collectCbill.getCollectcbillid();
		if (null == taskId || "".equals(taskId))
			WorkflowService.createAndSignalProcessInstance(collectCbill, id);
		else
			WorkflowService.signalProcessInstance(collectCbill, id, null);
	}

	/**
	 * 保存或更新收款清票
	 * 保存  
	 *  
	 * @param collectCbill
	 */
	public void _update(CollectCbill collectCbill
,BusinessObject businessObject	
	)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(collectCbill);
		collectCbillHibernateDao.saveOrUpdate(collectCbill);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(collectCbill);
	}
	
	/**
	 * 保存  
	 *   
	 * @param collectCbill
	 */
	public void _save(CollectCbill collectCbill)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(collectCbill);
		// 对id赋空值，使之执行insert操作(适应复制创建场景)
		collectCbill.setCollectcbillid(null);
                                    		collectCbillHibernateDao.saveOrUpdate(collectCbill);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(collectCbill);
	}
	
	/**
	 *
	 * 保存  
	 *   
	 * @param collectCbill
	 */
	public void _saveOrUpdate(CollectCbill collectCbill
,BusinessObject businessObject		
	)
	{
		if (StringUtils.isNullBlank(collectCbill.getCollectcbillid()))
		{	
			_save(collectCbill);
		}
		else
		{
			_update(collectCbill
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