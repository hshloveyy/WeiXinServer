/*
 * @(#)ExportCcollectServiceGen.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年05月27日 08点04分08秒
 *  描　述：创建
 */
package com.infolion.xdss3.exportCcollectGen.service;

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
import com.infolion.xdss3.exportCcollect.domain.ExportCcollect;
import com.infolion.xdss3.exportCcollect.service.ExportCcollectService;
import com.infolion.xdss3.exportCcollect.dao.ExportCcollectHibernateDao;

/**
 * <pre>
 * 出单清款表(ExportCcollect)服务类
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
public class ExportCcollectServiceGen extends BaseService
{
	protected Log log = LogFactory.getFactory().getLog(this.getClass());

	@Autowired
	protected ExportCcollectHibernateDao exportCcollectHibernateDao;
	
	public void setExportCcollectHibernateDao(ExportCcollectHibernateDao exportCcollectHibernateDao)
	{
		this.exportCcollectHibernateDao = exportCcollectHibernateDao;
	}
	
     /**
	 * 注入扩展服务类
	 */
	 @Autowired(required = false)
	 public void setAdvanceService(@Qualifier("exportCcollectAdvanceService") AdvanceService advanceService)
	 {
	  	super.setAdvanceService(advanceService);
	 }   
	 
	   
	/**
	 * 根据主键ID,取得出单清款表实例
	 * @param id
	 * @return
	 */
	public ExportCcollect _getDetached(String id)
	{		
	    ExportCcollect exportCcollect = new ExportCcollect();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  exportCcollect = exportCcollectHibernateDao.getDetached(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(exportCcollect);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    exportCcollect.setOperationType(operationType);
	    
		return exportCcollect;	
	}
	
	/**
	 * 根据主键ID,取得出单清款表实例
	 * @param id
	 * @return
	 */
	public ExportCcollect _get(String id)
	{		
	    ExportCcollect exportCcollect = new ExportCcollect();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  exportCcollect = exportCcollectHibernateDao.get(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(exportCcollect);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    exportCcollect.setOperationType(operationType);
	    
		return exportCcollect;	
	}
	
	/**
	 * 根据主键ID,取得出单清款表实例
	 * @param id
	 * @return
	 */
	public ExportCcollect _getForEdit(String id)
	{		
	    ExportCcollect exportCcollect = _get(id);
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = exportCcollect.getOperationType();
	    if(OperationType.READONLY.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotEdit);
	    }

		return exportCcollect;	
	}
	
	/**
	 * 根据主键ID,取得出单清款表实例副本
	 * @param id
	 * @return
	 */
	public ExportCcollect _getEntityCopy(String id)
	{		
	    ExportCcollect exportCcollect = new ExportCcollect();
		ExportCcollect exportCcollectOld = this._get(id);
		try
		{
			BeanUtils.copyProperties(exportCcollect, exportCcollectOld);
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (InvocationTargetException e)
		{
			e.printStackTrace();
		}
		//exportCcollect.setExportccollectid(null); 
		return exportCcollect;	
	}
	
	/**
	 * 删除  
	 *   
	 * @param exportCcollect
	 */
	public void _delete(ExportCcollect exportCcollect)
	{
		if (null != advanceService)
			advanceService.preDelete(exportCcollect);
	
 		LockService.isBoInstanceLocked(exportCcollect,ExportCcollect.class);
		exportCcollectHibernateDao.remove(exportCcollect);

		if (null != advanceService)
			advanceService.postDelete(exportCcollect);
	}

	/**
	 * 根据主键删除
	 * 
	 * @param exportCcollectId
	 */
	public void _delete(String exportCcollectId,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(exportCcollectId))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("exportccollectid"));
		ExportCcollect exportCcollect = this.exportCcollectHibernateDao.load(exportCcollectId);
		_delete(exportCcollect);
	}
	
	/**
	 * 删除  
	 *   
	 * @param Set<ExportCcollect> exportCcollects
	 */
	public void _deletes(Set<ExportCcollect> exportCcollects,BusinessObject businessObject)
	{
		if (null == exportCcollects || exportCcollects.size() < 1)
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoText());
		Iterator<ExportCcollect> it = exportCcollects.iterator();
		while (it.hasNext())
		{
			ExportCcollect exportCcollect = (ExportCcollect) it.next();
			_delete(exportCcollect);
		}
	}
	
	/**
	 * 根据多个主键(由，分割)删除多个对象
	 * 
	 * @param exportCcollectIds
	 */
	public void _deletes(String exportCcollectIds,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(exportCcollectIds))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("exportccollectid"));
		String[] ids = StringUtils.splitString(exportCcollectIds);
		for (int i = 0; i < ids.length; i++)
		{
			_delete(ids[i],businessObject);
		}
	}
	/**
	 * 提交工作流
	 * 
	 * @param exportCcollect
	 */
	public void _submitProcess(ExportCcollect exportCcollect
	,BusinessObject businessObject)
	{
		String id = exportCcollect.getExportccollectid();
		if (StringUtils.isNullBlank(id))
		{
			_save(exportCcollect);
		}
		else
		{
			_update(exportCcollect
			, businessObject);
		}
		String taskId = exportCcollect.getWorkflowTaskId();
		id = exportCcollect.getExportccollectid();
		if (null == taskId || "".equals(taskId))
			WorkflowService.createAndSignalProcessInstance(exportCcollect, id);
		else
			WorkflowService.signalProcessInstance(exportCcollect, id, null);
	}

	/**
	 * 保存或更新出单清款表
	 * 保存  
	 *  
	 * @param exportCcollect
	 */
	public void _update(ExportCcollect exportCcollect
,BusinessObject businessObject	
	)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(exportCcollect);
		exportCcollectHibernateDao.saveOrUpdate(exportCcollect);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(exportCcollect);
	}
	
	/**
	 * 保存  
	 *   
	 * @param exportCcollect
	 */
	public void _save(ExportCcollect exportCcollect)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(exportCcollect);
		// 对id赋空值，使之执行insert操作(适应复制创建场景)
		exportCcollect.setExportccollectid(null);
                                      		exportCcollectHibernateDao.saveOrUpdate(exportCcollect);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(exportCcollect);
	}
	
	/**
	 *
	 * 保存  
	 *   
	 * @param exportCcollect
	 */
	public void _saveOrUpdate(ExportCcollect exportCcollect
,BusinessObject businessObject		
	)
	{
		if (StringUtils.isNullBlank(exportCcollect.getExportccollectid()))
		{	
			_save(exportCcollect);
		}
		else
		{
			_update(exportCcollect
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