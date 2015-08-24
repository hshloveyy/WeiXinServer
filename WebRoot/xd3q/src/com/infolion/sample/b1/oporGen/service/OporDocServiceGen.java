/*
 * @(#)OporDocServiceGen.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年03月25日 19点56分06秒
 *  描　述：创建
 */
package com.infolion.sample.b1.oporGen.service;

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
import com.infolion.sample.b1.opor.domain.OporDoc;
import com.infolion.sample.b1.opor.service.OporDocService;
import com.infolion.sample.b1.opor.dao.OporDocHibernateDao;
          
import com.infolion.sample.b1.opor.domain.OporDocItem;
import com.infolion.sample.b1.opor.service.OporDocItemService;

/**
 * <pre>
 * 采购订单(OporDoc)服务类
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
public class OporDocServiceGen extends BaseService
{
	protected Log log = LogFactory.getFactory().getLog(this.getClass());

	@Autowired
	protected OporDocHibernateDao oporDocHibernateDao;
	
	public void setOporDocHibernateDao(OporDocHibernateDao oporDocHibernateDao)
	{
		this.oporDocHibernateDao = oporDocHibernateDao;
	}
	
     /**
	 * 注入扩展服务类
	 */
	 @Autowired(required = false)
	 public void setAdvanceService(@Qualifier("oporDocAdvanceService") AdvanceService advanceService)
	 {
	  	super.setAdvanceService(advanceService);
	 }   
	 
          

	@Autowired
	protected OporDocItemService oporDocItemService;
	
	public void setOporDocItemService(OporDocItemService oporDocItemService)
	{
		this.oporDocItemService = oporDocItemService;
	}
	
	   
	/**
	 * 根据主键ID,取得采购订单实例
	 * @param id
	 * @return
	 */
	public OporDoc _getDetached(String id)
	{		
	    OporDoc oporDoc = new OporDoc();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  oporDoc = oporDocHibernateDao.getDetached(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(oporDoc);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    oporDoc.setOperationType(operationType);
	    
		return oporDoc;	
	}
	
	/**
	 * 根据主键ID,取得采购订单实例
	 * @param id
	 * @return
	 */
	public OporDoc _get(String id)
	{		
	    OporDoc oporDoc = new OporDoc();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  oporDoc = oporDocHibernateDao.get(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(oporDoc);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    oporDoc.setOperationType(operationType);
	    
		return oporDoc;	
	}
	
	/**
	 * 根据主键ID,取得采购订单实例
	 * @param id
	 * @return
	 */
	public OporDoc _getForEdit(String id)
	{		
	    OporDoc oporDoc = _get(id);
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = oporDoc.getOperationType();
	    if(OperationType.READONLY.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotEdit);
	    }

		return oporDoc;	
	}
	
	/**
	 * 根据主键ID,取得采购订单实例副本
	 * @param id
	 * @return
	 */
	public OporDoc _getEntityCopy(String id)
	{		
	    OporDoc oporDoc = new OporDoc();
		OporDoc oporDocOld = this._get(id);
		try
		{
			BeanUtils.copyProperties(oporDoc, oporDocOld);
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (InvocationTargetException e)
		{
			e.printStackTrace();
		}
		//oporDoc.setOporDocId(null); 
		oporDoc.setProcessstate(" ");
		return oporDoc;	
	}
	
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
	
	/**
	 * 删除  
	 *   
	 * @param oporDoc
	 */
	public void _delete(OporDoc oporDoc)
	{
		if (null != advanceService)
			advanceService.preDelete(oporDoc);
	
		//流程状态
		String processState =oporDoc.getProcessstate();
		if(!StringUtils.isNullBlank(processState))
		{
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.CannotDeleteApproveData);
		}
 		LockService.isBoInstanceLocked(oporDoc,OporDoc.class);
		oporDocHibernateDao.remove(oporDoc);

		if (null != advanceService)
			advanceService.postDelete(oporDoc);
	}

	/**
	 * 根据主键删除
	 * 
	 * @param oporDocId
	 */
	public void _delete(String oporDocId,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(oporDocId))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("oporDocId"));
		OporDoc oporDoc = this.oporDocHibernateDao.load(oporDocId);
		_delete(oporDoc);
	}
	
	/**
	 * 删除  
	 *   
	 * @param Set<OporDoc> oporDocs
	 */
	public void _deletes(Set<OporDoc> oporDocs,BusinessObject businessObject)
	{
		if (null == oporDocs || oporDocs.size() < 1)
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoText());
		Iterator<OporDoc> it = oporDocs.iterator();
		while (it.hasNext())
		{
			OporDoc oporDoc = (OporDoc) it.next();
			_delete(oporDoc);
		}
	}
	
	/**
	 * 根据多个主键(由，分割)删除多个对象
	 * 
	 * @param oporDocIds
	 */
	public void _deletes(String oporDocIds,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(oporDocIds))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("oporDocId"));
		String[] ids = StringUtils.splitString(oporDocIds);
		for (int i = 0; i < ids.length; i++)
		{
			_delete(ids[i],businessObject);
		}
	}
	/**
	 * 提交工作流
	 * 
	 * @param oporDoc
	 */
	public void _submitProcess(OporDoc oporDoc
,Set<OporDocItem> deletedOporDocItemSet	,BusinessObject businessObject)
	{
		String id = oporDoc.getOporDocId();
		if (StringUtils.isNullBlank(id))
		{
			_save(oporDoc);
		}
		else
		{
			_update(oporDoc
,deletedOporDocItemSet			, businessObject);
		}
		String taskId = oporDoc.getWorkflowTaskId();
		if (null == taskId || "".equals(taskId))
			WorkflowService.createAndSignalProcessInstance(oporDoc, id);
		else
			WorkflowService.signalProcessInstance(oporDoc, id, null);
	}

	/**
	 * 保存或更新采购订单
	 * 保存  
	 *  
	 * @param oporDoc
	 */
	public void _update(OporDoc oporDoc
,Set<OporDocItem> deletedOporDocItemSet,BusinessObject businessObject	
	)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(oporDoc);
		oporDocHibernateDao.saveOrUpdate(oporDoc);
// 删除关联子业务对象数据
if(deletedOporDocItemSet!=null && deletedOporDocItemSet.size()>0)
{
oporDocItemService._deletes(deletedOporDocItemSet,businessObject);
}		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(oporDoc);
	}
	
	/**
	 * 保存  
	 *   
	 * @param oporDoc
	 */
	public void _save(OporDoc oporDoc)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(oporDoc);
		// 对id赋空值，使之执行insert操作(适应复制创建场景)
		oporDoc.setOporDocId(null);
       
		Set<OporDocItem> oporDocItemSet = oporDoc.getOporDocItem();
		Set<OporDocItem> newOporDocItemSet = null;
		if (null != oporDocItemSet)
		{
			newOporDocItemSet = new HashSet();
			Iterator<OporDocItem> itOporDocItem = oporDocItemSet.iterator();
			while (itOporDocItem.hasNext())
			{
				OporDocItem oporDocItem = (OporDocItem) itOporDocItem.next();
				oporDocItem.setOporDocItemId(null);
				newOporDocItemSet.add(oporDocItem);
			}
		}
		oporDoc.setOporDocItem(newOporDocItemSet);
       
       
       
       
       
       
       
       
       
       
       
       
		oporDocHibernateDao.saveOrUpdate(oporDoc);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(oporDoc);
	}
	
	/**
	 *
	 * 保存  
	 *   
	 * @param oporDoc
	 */
	public void _saveOrUpdate(OporDoc oporDoc
,Set<OporDocItem> deletedOporDocItemSet,BusinessObject businessObject		
	)
	{
		if (StringUtils.isNullBlank(oporDoc.getOporDocId()))
		{	
			_save(oporDoc);
		}
		else
		{
			_update(oporDoc
,deletedOporDocItemSet
, businessObject);
}	}
	
}