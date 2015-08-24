/*
 * @(#)BillInCollectServiceGen.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年09月16日 09点35分06秒
 *  描　述：创建
 */
package com.infolion.xdss3.billincollectGen.service;

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
import com.infolion.xdss3.billincollect.domain.BillInCollect;
import com.infolion.xdss3.billincollect.service.BillInCollectService;
import com.infolion.xdss3.billincollect.dao.BillInCollectHibernateDao;

/**
 * <pre>
 * 未清收款(BillInCollect)服务类
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
public class BillInCollectServiceGen extends BaseService
{
	protected Log log = LogFactory.getFactory().getLog(this.getClass());

	@Autowired
	protected BillInCollectHibernateDao billInCollectHibernateDao;
	
	public void setBillInCollectHibernateDao(BillInCollectHibernateDao billInCollectHibernateDao)
	{
		this.billInCollectHibernateDao = billInCollectHibernateDao;
	}
	
     /**
	 * 注入扩展服务类
	 */
	 @Autowired(required = false)
	 public void setAdvanceService(@Qualifier("billInCollectAdvanceService") AdvanceService advanceService)
	 {
	  	super.setAdvanceService(advanceService);
	 }   
	 
	   
	/**
	 * 根据主键ID,取得未清收款实例
	 * @param id
	 * @return
	 */
	public BillInCollect _getDetached(String id)
	{		
	    BillInCollect billInCollect = new BillInCollect();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  billInCollect = billInCollectHibernateDao.getDetached(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(billInCollect);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    billInCollect.setOperationType(operationType);
	    
		return billInCollect;	
	}
	
	/**
	 * 根据主键ID,取得未清收款实例
	 * @param id
	 * @return
	 */
	public BillInCollect _get(String id)
	{		
	    BillInCollect billInCollect = new BillInCollect();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  billInCollect = billInCollectHibernateDao.get(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(billInCollect);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    billInCollect.setOperationType(operationType);
	    
		return billInCollect;	
	}
	
	/**
	 * 根据主键ID,取得未清收款实例
	 * @param id
	 * @return
	 */
	public BillInCollect _getForEdit(String id)
	{		
	    BillInCollect billInCollect = _get(id);
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = billInCollect.getOperationType();
	    if(OperationType.READONLY.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotEdit);
	    }

		return billInCollect;	
	}
	
	/**
	 * 根据主键ID,取得未清收款实例副本
	 * @param id
	 * @return
	 */
	public BillInCollect _getEntityCopy(String id)
	{		
	    BillInCollect billInCollect = new BillInCollect();
		BillInCollect billInCollectOld = this._get(id);
		try
		{
			BeanUtils.copyProperties(billInCollect, billInCollectOld);
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (InvocationTargetException e)
		{
			e.printStackTrace();
		}
		//billInCollect.setBillincollectid(null); 
		return billInCollect;	
	}
	
	/**
	 * 删除  
	 *   
	 * @param billInCollect
	 */
	public void _delete(BillInCollect billInCollect)
	{
		if (null != advanceService)
			advanceService.preDelete(billInCollect);
	
 		LockService.isBoInstanceLocked(billInCollect,BillInCollect.class);
		billInCollectHibernateDao.remove(billInCollect);

		if (null != advanceService)
			advanceService.postDelete(billInCollect);
	}

	/**
	 * 根据主键删除
	 * 
	 * @param billInCollectId
	 */
	public void _delete(String billInCollectId,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(billInCollectId))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("billincollectid"));
		BillInCollect billInCollect = this.billInCollectHibernateDao.load(billInCollectId);
		_delete(billInCollect);
	}
	
	/**
	 * 删除  
	 *   
	 * @param Set<BillInCollect> billInCollects
	 */
	public void _deletes(Set<BillInCollect> billInCollects,BusinessObject businessObject)
	{
		if (null == billInCollects || billInCollects.size() < 1)
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoText());
		Iterator<BillInCollect> it = billInCollects.iterator();
		while (it.hasNext())
		{
			BillInCollect billInCollect = (BillInCollect) it.next();
			_delete(billInCollect);
		}
	}
	
	/**
	 * 根据多个主键(由，分割)删除多个对象
	 * 
	 * @param billInCollectIds
	 */
	public void _deletes(String billInCollectIds,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(billInCollectIds))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("billincollectid"));
		String[] ids = StringUtils.splitString(billInCollectIds);
		for (int i = 0; i < ids.length; i++)
		{
			_delete(ids[i],businessObject);
		}
	}
	/**
	 * 提交工作流
	 * 
	 * @param billInCollect
	 */
	public void _submitProcess(BillInCollect billInCollect
	,BusinessObject businessObject)
	{
		String id = billInCollect.getBillincollectid();
        /**
		if (StringUtils.isNullBlank(id))
		{
			_save(billInCollect);
		}
		else
		{
			_update(billInCollect
			, businessObject);
		}**/
		
		String taskId = billInCollect.getWorkflowTaskId();
		id = billInCollect.getBillincollectid();
		if (null == taskId || "".equals(taskId))
			WorkflowService.createAndSignalProcessInstance(billInCollect, id);
		else
			WorkflowService.signalProcessInstance(billInCollect, id, null);
	}

	/**
	 * 保存或更新未清收款
	 * 保存  
	 *  
	 * @param billInCollect
	 */
	public void _update(BillInCollect billInCollect
,BusinessObject businessObject	
	)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(billInCollect);
		billInCollectHibernateDao.saveOrUpdate(billInCollect);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(billInCollect);
	}
	
	/**
	 * 保存  
	 *   
	 * @param billInCollect
	 */
	public void _save(BillInCollect billInCollect)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(billInCollect);
		// 对id赋空值，使之执行insert操作(适应复制创建场景)
		billInCollect.setBillincollectid(null);
                                		billInCollectHibernateDao.saveOrUpdate(billInCollect);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(billInCollect);
	}
	
	/**
	 *
	 * 保存  
	 *   
	 * @param billInCollect
	 */
	public void _saveOrUpdate(BillInCollect billInCollect
,BusinessObject businessObject		
	)
	{
		if (StringUtils.isNullBlank(billInCollect.getBillincollectid()))
		{	
			_save(billInCollect);
		}
		else
		{
			_update(billInCollect
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