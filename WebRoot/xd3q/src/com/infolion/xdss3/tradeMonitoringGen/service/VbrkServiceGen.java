/*
 * @(#)VbrkServiceGen.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年06月06日 13点53分43秒
 *  描　述：创建
 */
package com.infolion.xdss3.tradeMonitoringGen.service;

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
import com.infolion.xdss3.tradeMonitoring.domain.Vbrk;
import com.infolion.xdss3.tradeMonitoring.service.VbrkService;
import com.infolion.xdss3.tradeMonitoring.dao.VbrkHibernateDao;
          
import com.infolion.xdss3.tradeMonitoring.domain.Vbrp;
import com.infolion.xdss3.tradeMonitoring.service.VbrpService;

/**
 * <pre>
 * 开票数据抬头(Vbrk)服务类
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
public class VbrkServiceGen extends BaseService
{
	protected Log log = LogFactory.getFactory().getLog(this.getClass());

	@Autowired
	protected VbrkHibernateDao vbrkHibernateDao;
	
	public void setVbrkHibernateDao(VbrkHibernateDao vbrkHibernateDao)
	{
		this.vbrkHibernateDao = vbrkHibernateDao;
	}
	
     /**
	 * 注入扩展服务类
	 */
	 @Autowired(required = false)
	 public void setAdvanceService(@Qualifier("vbrkAdvanceService") AdvanceService advanceService)
	 {
	  	super.setAdvanceService(advanceService);
	 }   
	 
          

	@Autowired
	protected VbrpService vbrpService;
	
	public void setVbrpService(VbrpService vbrpService)
	{
		this.vbrpService = vbrpService;
	}

          

	   
	/**
	 * 根据主键ID,取得开票数据抬头实例
	 * @param id
	 * @return
	 */
	public Vbrk _getDetached(String id)
	{		
	    Vbrk vbrk = new Vbrk();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  vbrk = vbrkHibernateDao.getDetached(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(vbrk);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    vbrk.setOperationType(operationType);
	    
		return vbrk;	
	}
	
	/**
	 * 根据主键ID,取得开票数据抬头实例
	 * @param id
	 * @return
	 */
	public Vbrk _get(String id)
	{		
	    Vbrk vbrk = new Vbrk();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  vbrk = vbrkHibernateDao.get(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(vbrk);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    vbrk.setOperationType(operationType);
	    
		return vbrk;	
	}
	
	/**
	 * 根据主键ID,取得开票数据抬头实例
	 * @param id
	 * @return
	 */
	public Vbrk _getForEdit(String id)
	{		
	    Vbrk vbrk = _get(id);
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = vbrk.getOperationType();
	    if(OperationType.READONLY.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotEdit);
	    }

		return vbrk;	
	}
	
	/**
	 * 根据主键ID,取得开票数据抬头实例副本
	 * @param id
	 * @return
	 */
	public Vbrk _getEntityCopy(String id)
	{		
	    Vbrk vbrk = new Vbrk();
		Vbrk vbrkOld = this._get(id);
		try
		{
			BeanUtils.copyProperties(vbrk, vbrkOld);
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (InvocationTargetException e)
		{
			e.printStackTrace();
		}
		//vbrk.setVbeln(null); 
		return vbrk;	
	}
	
	/**
	 * 删除  
	 *   
	 * @param vbrk
	 */
	public void _delete(Vbrk vbrk)
	{
		if (null != advanceService)
			advanceService.preDelete(vbrk);
	
 		LockService.isBoInstanceLocked(vbrk,Vbrk.class);
		vbrkHibernateDao.remove(vbrk);

		if (null != advanceService)
			advanceService.postDelete(vbrk);
	}

	/**
	 * 根据主键删除
	 * 
	 * @param vbrkId
	 */
	public void _delete(String vbrkId,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(vbrkId))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("vbeln"));
		Vbrk vbrk = this.vbrkHibernateDao.load(vbrkId);
		_delete(vbrk);
	}
	
	/**
	 * 删除  
	 *   
	 * @param Set<Vbrk> vbrks
	 */
	public void _deletes(Set<Vbrk> vbrks,BusinessObject businessObject)
	{
		if (null == vbrks || vbrks.size() < 1)
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoText());
		Iterator<Vbrk> it = vbrks.iterator();
		while (it.hasNext())
		{
			Vbrk vbrk = (Vbrk) it.next();
			_delete(vbrk);
		}
	}
	
	/**
	 * 根据多个主键(由，分割)删除多个对象
	 * 
	 * @param vbrkIds
	 */
	public void _deletes(String vbrkIds,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(vbrkIds))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("vbeln"));
		String[] ids = StringUtils.splitString(vbrkIds);
		for (int i = 0; i < ids.length; i++)
		{
			_delete(ids[i],businessObject);
		}
	}
	/**
	 * 提交工作流
	 * 
	 * @param vbrk
	 */
	public void _submitProcess(Vbrk vbrk
,Set<Vbrp> deletedVbrpSet	,BusinessObject businessObject)
	{
		String id = vbrk.getVbeln();
		if (StringUtils.isNullBlank(id))
		{
			_save(vbrk);
		}
		else
		{
			_update(vbrk
,deletedVbrpSet			, businessObject);
		}
		String taskId = vbrk.getWorkflowTaskId();
		id = vbrk.getVbeln();
		if (null == taskId || "".equals(taskId))
			WorkflowService.createAndSignalProcessInstance(vbrk, id);
		else
			WorkflowService.signalProcessInstance(vbrk, id, null);
	}

	/**
	 * 保存或更新开票数据抬头
	 * 保存  
	 *  
	 * @param vbrk
	 */
	public void _update(Vbrk vbrk
,Set<Vbrp> deletedVbrpSet,BusinessObject businessObject	
	)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(vbrk);
		vbrkHibernateDao.saveOrUpdate(vbrk);
// 删除关联子业务对象数据
if(deletedVbrpSet!=null && deletedVbrpSet.size()>0)
{
vbrpService._deletes(deletedVbrpSet,businessObject);
}		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(vbrk);
	}
	
	/**
	 * 保存  
	 *   
	 * @param vbrk
	 */
	public void _save(Vbrk vbrk)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(vbrk);
		// 对id赋空值，使之执行insert操作(适应复制创建场景)
		vbrk.setVbeln(null);
       
		Set<Vbrp> vbrpSet = vbrk.getVbrp();
		Set<Vbrp> newVbrpSet = null;
		if (null != vbrpSet)
		{
			newVbrpSet = new HashSet();
			Iterator<Vbrp> itVbrp = vbrpSet.iterator();
			while (itVbrp.hasNext())
			{
				Vbrp vbrp = (Vbrp) itVbrp.next();
				vbrp.setVbrpid(null);
				newVbrpSet.add(vbrp);
			}
		}
		vbrk.setVbrp(newVbrpSet);
       
       
       
       
       
       
       
       
       
       
       
       
       
		vbrkHibernateDao.saveOrUpdate(vbrk);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(vbrk);
	}
	
	/**
	 *
	 * 保存  
	 *   
	 * @param vbrk
	 */
	public void _saveOrUpdate(Vbrk vbrk
,Set<Vbrp> deletedVbrpSet,BusinessObject businessObject		
	)
	{
		if (StringUtils.isNullBlank(vbrk.getVbeln()))
		{	
			_save(vbrk);
		}
		else
		{
			_update(vbrk
,deletedVbrpSet
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