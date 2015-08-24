/*
 * @(#)VbrpServiceGen.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年06月06日 13点53分47秒
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
import com.infolion.xdss3.tradeMonitoring.domain.Vbrp;
import com.infolion.xdss3.tradeMonitoring.service.VbrpService;
import com.infolion.xdss3.tradeMonitoring.dao.VbrpHibernateDao;

/**
 * <pre>
 * 开票明细(Vbrp)服务类
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
public class VbrpServiceGen extends BaseService
{
	protected Log log = LogFactory.getFactory().getLog(this.getClass());

	@Autowired
	protected VbrpHibernateDao vbrpHibernateDao;
	
	public void setVbrpHibernateDao(VbrpHibernateDao vbrpHibernateDao)
	{
		this.vbrpHibernateDao = vbrpHibernateDao;
	}
	
     /**
	 * 注入扩展服务类
	 */
	 @Autowired(required = false)
	 public void setAdvanceService(@Qualifier("vbrpAdvanceService") AdvanceService advanceService)
	 {
	  	super.setAdvanceService(advanceService);
	 }   
	 
	   
	/**
	 * 根据主键ID,取得开票明细实例
	 * @param id
	 * @return
	 */
	public Vbrp _getDetached(String id)
	{		
	    Vbrp vbrp = new Vbrp();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  vbrp = vbrpHibernateDao.getDetached(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(vbrp);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    vbrp.setOperationType(operationType);
	    
		return vbrp;	
	}
	
	/**
	 * 根据主键ID,取得开票明细实例
	 * @param id
	 * @return
	 */
	public Vbrp _get(String id)
	{		
	    Vbrp vbrp = new Vbrp();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  vbrp = vbrpHibernateDao.get(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(vbrp);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    vbrp.setOperationType(operationType);
	    
		return vbrp;	
	}
	
	/**
	 * 根据主键ID,取得开票明细实例
	 * @param id
	 * @return
	 */
	public Vbrp _getForEdit(String id)
	{		
	    Vbrp vbrp = _get(id);
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = vbrp.getOperationType();
	    if(OperationType.READONLY.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotEdit);
	    }

		return vbrp;	
	}
	
	/**
	 * 根据主键ID,取得开票明细实例副本
	 * @param id
	 * @return
	 */
	public Vbrp _getEntityCopy(String id)
	{		
	    Vbrp vbrp = new Vbrp();
		Vbrp vbrpOld = this._get(id);
		try
		{
			BeanUtils.copyProperties(vbrp, vbrpOld);
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (InvocationTargetException e)
		{
			e.printStackTrace();
		}
		//vbrp.setVbrpid(null); 
		return vbrp;	
	}
	
	/**
	 * 删除  
	 *   
	 * @param vbrp
	 */
	public void _delete(Vbrp vbrp)
	{
		if (null != advanceService)
			advanceService.preDelete(vbrp);
	
 		LockService.isBoInstanceLocked(vbrp,Vbrp.class);
		vbrpHibernateDao.remove(vbrp);

		if (null != advanceService)
			advanceService.postDelete(vbrp);
	}

	/**
	 * 根据主键删除
	 * 
	 * @param vbrpId
	 */
	public void _delete(String vbrpId,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(vbrpId))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("vbrpid"));
		Vbrp vbrp = this.vbrpHibernateDao.load(vbrpId);
		_delete(vbrp);
	}
	
	/**
	 * 删除  
	 *   
	 * @param Set<Vbrp> vbrps
	 */
	public void _deletes(Set<Vbrp> vbrps,BusinessObject businessObject)
	{
		if (null == vbrps || vbrps.size() < 1)
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoText());
		Iterator<Vbrp> it = vbrps.iterator();
		while (it.hasNext())
		{
			Vbrp vbrp = (Vbrp) it.next();
			_delete(vbrp);
		}
	}
	
	/**
	 * 根据多个主键(由，分割)删除多个对象
	 * 
	 * @param vbrpIds
	 */
	public void _deletes(String vbrpIds,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(vbrpIds))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("vbrpid"));
		String[] ids = StringUtils.splitString(vbrpIds);
		for (int i = 0; i < ids.length; i++)
		{
			_delete(ids[i],businessObject);
		}
	}
	/**
	 * 提交工作流
	 * 
	 * @param vbrp
	 */
	public void _submitProcess(Vbrp vbrp
	,BusinessObject businessObject)
	{
		String id = vbrp.getVbrpid();
		if (StringUtils.isNullBlank(id))
		{
			_save(vbrp);
		}
		else
		{
			_update(vbrp
			, businessObject);
		}
		String taskId = vbrp.getWorkflowTaskId();
		id = vbrp.getVbrpid();
		if (null == taskId || "".equals(taskId))
			WorkflowService.createAndSignalProcessInstance(vbrp, id);
		else
			WorkflowService.signalProcessInstance(vbrp, id, null);
	}

	/**
	 * 保存或更新开票明细
	 * 保存  
	 *  
	 * @param vbrp
	 */
	public void _update(Vbrp vbrp
,BusinessObject businessObject	
	)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(vbrp);
		vbrpHibernateDao.saveOrUpdate(vbrp);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(vbrp);
	}
	
	/**
	 * 保存  
	 *   
	 * @param vbrp
	 */
	public void _save(Vbrp vbrp)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(vbrp);
		// 对id赋空值，使之执行insert操作(适应复制创建场景)
		vbrp.setVbrpid(null);
                              		vbrpHibernateDao.saveOrUpdate(vbrp);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(vbrp);
	}
	
	/**
	 *
	 * 保存  
	 *   
	 * @param vbrp
	 */
	public void _saveOrUpdate(Vbrp vbrp
,BusinessObject businessObject		
	)
	{
		if (StringUtils.isNullBlank(vbrp.getVbrpid()))
		{	
			_save(vbrp);
		}
		else
		{
			_update(vbrp
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