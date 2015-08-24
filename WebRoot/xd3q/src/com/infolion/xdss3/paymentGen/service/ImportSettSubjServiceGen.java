/*
 * @(#)ImportSettSubjServiceGen.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年07月25日 09点52分23秒
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
import com.infolion.xdss3.payment.domain.ImportSettSubj;
import com.infolion.xdss3.payment.service.ImportSettSubjService;
import com.infolion.xdss3.payment.dao.ImportSettSubjHibernateDao;

/**
 * <pre>
 * 付款结算科目(ImportSettSubj)服务类
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
public class ImportSettSubjServiceGen extends BaseService
{
	protected Log log = LogFactory.getFactory().getLog(this.getClass());

	@Autowired
	protected ImportSettSubjHibernateDao importSettSubjHibernateDao;
	
	public void setImportSettSubjHibernateDao(ImportSettSubjHibernateDao importSettSubjHibernateDao)
	{
		this.importSettSubjHibernateDao = importSettSubjHibernateDao;
	}
	
     /**
	 * 注入扩展服务类
	 */
	 @Autowired(required = false)
	 public void setAdvanceService(@Qualifier("importSettSubjAdvanceService") AdvanceService advanceService)
	 {
	  	super.setAdvanceService(advanceService);
	 }   
	 
	   
	/**
	 * 根据主键ID,取得付款结算科目实例
	 * @param id
	 * @return
	 */
	public ImportSettSubj _getDetached(String id)
	{		
	    ImportSettSubj importSettSubj = new ImportSettSubj();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  importSettSubj = importSettSubjHibernateDao.getDetached(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(importSettSubj);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    importSettSubj.setOperationType(operationType);
	    
		return importSettSubj;	
	}
	
	/**
	 * 根据主键ID,取得付款结算科目实例
	 * @param id
	 * @return
	 */
	public ImportSettSubj _get(String id)
	{		
	    ImportSettSubj importSettSubj = new ImportSettSubj();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  importSettSubj = importSettSubjHibernateDao.get(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(importSettSubj);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    importSettSubj.setOperationType(operationType);
	    
		return importSettSubj;	
	}
	
	/**
	 * 根据主键ID,取得付款结算科目实例
	 * @param id
	 * @return
	 */
	public ImportSettSubj _getForEdit(String id)
	{		
	    ImportSettSubj importSettSubj = _get(id);
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = importSettSubj.getOperationType();
	    if(OperationType.READONLY.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotEdit);
	    }

		return importSettSubj;	
	}
	
	/**
	 * 根据主键ID,取得付款结算科目实例副本
	 * @param id
	 * @return
	 */
	public ImportSettSubj _getEntityCopy(String id)
	{		
	    ImportSettSubj importSettSubj = new ImportSettSubj();
		ImportSettSubj importSettSubjOld = this._get(id);
		try
		{
			BeanUtils.copyProperties(importSettSubj, importSettSubjOld);
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (InvocationTargetException e)
		{
			e.printStackTrace();
		}
		//importSettSubj.setSettlesubjectid(null); 
		return importSettSubj;	
	}
	
	/**
	 * 删除  
	 *   
	 * @param importSettSubj
	 */
	public void _delete(ImportSettSubj importSettSubj)
	{
		if (null != advanceService)
			advanceService.preDelete(importSettSubj);
	
 		LockService.isBoInstanceLocked(importSettSubj,ImportSettSubj.class);
		importSettSubjHibernateDao.remove(importSettSubj);

		if (null != advanceService)
			advanceService.postDelete(importSettSubj);
	}

	/**
	 * 根据主键删除
	 * 
	 * @param importSettSubjId
	 */
	public void _delete(String importSettSubjId,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(importSettSubjId))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("settlesubjectid"));
		ImportSettSubj importSettSubj = this.importSettSubjHibernateDao.load(importSettSubjId);
		_delete(importSettSubj);
	}
	
	/**
	 * 删除  
	 *   
	 * @param Set<ImportSettSubj> importSettSubjs
	 */
	public void _deletes(Set<ImportSettSubj> importSettSubjs,BusinessObject businessObject)
	{
		if (null == importSettSubjs || importSettSubjs.size() < 1)
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoText());
		Iterator<ImportSettSubj> it = importSettSubjs.iterator();
		while (it.hasNext())
		{
			ImportSettSubj importSettSubj = (ImportSettSubj) it.next();
			_delete(importSettSubj);
		}
	}
	
	/**
	 * 根据多个主键(由，分割)删除多个对象
	 * 
	 * @param importSettSubjIds
	 */
	public void _deletes(String importSettSubjIds,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(importSettSubjIds))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("settlesubjectid"));
		String[] ids = StringUtils.splitString(importSettSubjIds);
		for (int i = 0; i < ids.length; i++)
		{
			_delete(ids[i],businessObject);
		}
	}
	/**
	 * 提交工作流
	 * 
	 * @param importSettSubj
	 */
	public void _submitProcess(ImportSettSubj importSettSubj
	,BusinessObject businessObject)
	{
		String id = importSettSubj.getSettlesubjectid();
        /**
		if (StringUtils.isNullBlank(id))
		{
			_save(importSettSubj);
		}
		else
		{
			_update(importSettSubj
			, businessObject);
		}**/
		
		String taskId = importSettSubj.getWorkflowTaskId();
		id = importSettSubj.getSettlesubjectid();
		if (null == taskId || "".equals(taskId))
			WorkflowService.createAndSignalProcessInstance(importSettSubj, id);
		else
			WorkflowService.signalProcessInstance(importSettSubj, id, null);
	}

	/**
	 * 保存或更新付款结算科目
	 * 保存  
	 *  
	 * @param importSettSubj
	 */
	public void _update(ImportSettSubj importSettSubj
,BusinessObject businessObject	
	)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(importSettSubj);
		importSettSubjHibernateDao.saveOrUpdate(importSettSubj);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(importSettSubj);
	}
	
	/**
	 * 保存  
	 *   
	 * @param importSettSubj
	 */
	public void _save(ImportSettSubj importSettSubj)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(importSettSubj);
		// 对id赋空值，使之执行insert操作(适应复制创建场景)
		importSettSubj.setSettlesubjectid(null);
                                                                                      		importSettSubjHibernateDao.saveOrUpdate(importSettSubj);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(importSettSubj);
	}
	
	/**
	 *
	 * 保存  
	 *   
	 * @param importSettSubj
	 */
	public void _saveOrUpdate(ImportSettSubj importSettSubj
,BusinessObject businessObject		
	)
	{
		if (StringUtils.isNullBlank(importSettSubj.getSettlesubjectid()))
		{	
			_save(importSettSubj);
		}
		else
		{
			_update(importSettSubj
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