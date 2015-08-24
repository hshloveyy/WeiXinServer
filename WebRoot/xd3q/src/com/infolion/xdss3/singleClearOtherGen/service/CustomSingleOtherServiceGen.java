/*
 * @(#)CustomSingleOtherServiceGen.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2012年06月08日 11点48分54秒
 *  描　述：创建
 */
package com.infolion.xdss3.singleClearOtherGen.service;

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
import com.infolion.xdss3.singleClearOther.domain.CustomSingleOther;
import com.infolion.xdss3.singleClearOther.service.CustomSingleOtherService;
import com.infolion.xdss3.singleClearOther.dao.CustomSingleOtherHibernateDao;
          
import com.infolion.xdss3.singleClearOther.domain.SettleSubjectOther;
import com.infolion.xdss3.singleClearOther.service.SettleSubjectOtherService;
          
import com.infolion.xdss3.singleClearOther.domain.FundFlowOther;
import com.infolion.xdss3.singleClearOther.service.FundFlowOtherService;
          
import com.infolion.xdss3.singleClearOther.domain.UnCleaCollectOther;
import com.infolion.xdss3.singleClearOther.service.UnCleaCollectOtherService;
          
import com.infolion.xdss3.singleClearOther.domain.UnCustomBillOther;
import com.infolion.xdss3.singleClearOther.service.UnCustomBillOtherService;

/**
 * <pre>
 * 其他公司客户单清帐(CustomSingleOther)服务类
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
public class CustomSingleOtherServiceGen extends BaseService
{
	protected Log log = LogFactory.getFactory().getLog(this.getClass());

	@Autowired
	protected CustomSingleOtherHibernateDao customSingleOtherHibernateDao;
	
	public void setCustomSingleOtherHibernateDao(CustomSingleOtherHibernateDao customSingleOtherHibernateDao)
	{
		this.customSingleOtherHibernateDao = customSingleOtherHibernateDao;
	}
	
     /**
	 * 注入扩展服务类
	 */
	 @Autowired(required = false)
	 public void setAdvanceService(@Qualifier("customSingleOtherAdvanceService") AdvanceService advanceService)
	 {
	  	super.setAdvanceService(advanceService);
	 }   
	 
          

	@Autowired
	protected SettleSubjectOtherService settleSubjectOtherService;
	
	public void setSettleSubjectOtherService(SettleSubjectOtherService settleSubjectOtherService)
	{
		this.settleSubjectOtherService = settleSubjectOtherService;
	}
          

	@Autowired
	protected FundFlowOtherService fundFlowOtherService;
	
	public void setFundFlowOtherService(FundFlowOtherService fundFlowOtherService)
	{
		this.fundFlowOtherService = fundFlowOtherService;
	}
          

	@Autowired
	protected UnCleaCollectOtherService unCleaCollectOtherService;
	
	public void setUnCleaCollectOtherService(UnCleaCollectOtherService unCleaCollectOtherService)
	{
		this.unCleaCollectOtherService = unCleaCollectOtherService;
	}
          

	@Autowired
	protected UnCustomBillOtherService unCustomBillOtherService;
	
	public void setUnCustomBillOtherService(UnCustomBillOtherService unCustomBillOtherService)
	{
		this.unCustomBillOtherService = unCustomBillOtherService;
	}

          
          
          
          

	   
	/**
	 * 根据主键ID,取得其他公司客户单清帐实例
	 * @param id
	 * @return
	 */
	public CustomSingleOther _getDetached(String id)
	{		
	    CustomSingleOther customSingleOther = new CustomSingleOther();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  customSingleOther = customSingleOtherHibernateDao.getDetached(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(customSingleOther);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    customSingleOther.setOperationType(operationType);
	    
		return customSingleOther;	
	}
	
	/**
	 * 根据主键ID,取得其他公司客户单清帐实例
	 * @param id
	 * @return
	 */
	public CustomSingleOther _get(String id)
	{		
	    CustomSingleOther customSingleOther = new CustomSingleOther();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  customSingleOther = customSingleOtherHibernateDao.get(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(customSingleOther);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    customSingleOther.setOperationType(operationType);
	    
		return customSingleOther;	
	}
	
	/**
	 * 根据主键ID,取得其他公司客户单清帐实例
	 * @param id
	 * @return
	 */
	public CustomSingleOther _getForEdit(String id)
	{		
	    CustomSingleOther customSingleOther = _get(id);
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = customSingleOther.getOperationType();
	    if(OperationType.READONLY.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotEdit);
	    }

		return customSingleOther;	
	}
	
	/**
	 * 根据主键ID,取得其他公司客户单清帐实例副本
	 * @param id
	 * @return
	 */
	public CustomSingleOther _getEntityCopy(String id)
	{		
	    CustomSingleOther customSingleOther = new CustomSingleOther();
		CustomSingleOther customSingleOtherOld = this._get(id);
		try
		{
			BeanUtils.copyProperties(customSingleOther, customSingleOtherOld);
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (InvocationTargetException e)
		{
			e.printStackTrace();
		}
		customSingleOther.setCustomclearno(null); 
		//customSingleOther.setCustomsclearid(null); 
		customSingleOther.setProcessstate(" ");
		return customSingleOther;	
	}
	
	/**
	 * 删除  
	 *   
	 * @param customSingleOther
	 */
	public void _delete(CustomSingleOther customSingleOther)
	{
		if (null != advanceService)
			advanceService.preDelete(customSingleOther);
	
		//流程状态
		String processState =customSingleOther.getProcessstate();
		if(!StringUtils.isNullBlank(processState))
		{
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.CannotDeleteApproveData);
		}
 		LockService.isBoInstanceLocked(customSingleOther,CustomSingleOther.class);
		customSingleOtherHibernateDao.remove(customSingleOther);

		if (null != advanceService)
			advanceService.postDelete(customSingleOther);
	}

	/**
	 * 根据主键删除
	 * 
	 * @param customSingleOtherId
	 */
	public void _delete(String customSingleOtherId,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(customSingleOtherId))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("customsclearid"));
		CustomSingleOther customSingleOther = this.customSingleOtherHibernateDao.load(customSingleOtherId);
		_delete(customSingleOther);
	}
	
	/**
	 * 删除  
	 *   
	 * @param Set<CustomSingleOther> customSingleOthers
	 */
	public void _deletes(Set<CustomSingleOther> customSingleOthers,BusinessObject businessObject)
	{
		if (null == customSingleOthers || customSingleOthers.size() < 1)
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoText());
		Iterator<CustomSingleOther> it = customSingleOthers.iterator();
		while (it.hasNext())
		{
			CustomSingleOther customSingleOther = (CustomSingleOther) it.next();
			_delete(customSingleOther);
		}
	}
	
	/**
	 * 根据多个主键(由，分割)删除多个对象
	 * 
	 * @param customSingleOtherIds
	 */
	public void _deletes(String customSingleOtherIds,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(customSingleOtherIds))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("customsclearid"));
		String[] ids = StringUtils.splitString(customSingleOtherIds);
		for (int i = 0; i < ids.length; i++)
		{
			_delete(ids[i],businessObject);
		}
	}
	/**
	 * 提交工作流
	 * 
	 * @param customSingleOther
	 */
	public void _submitProcess(CustomSingleOther customSingleOther
,Set<UnCleaCollectOther> deletedUnCleaCollectOtherSet
,Set<UnCustomBillOther> deletedUnCustomBillOtherSet	,BusinessObject businessObject)
	{
		String id = customSingleOther.getCustomsclearid();
        /**
		if (StringUtils.isNullBlank(id))
		{
			_save(customSingleOther);
		}
		else
		{
			_update(customSingleOther
,deletedUnCleaCollectOtherSet
,deletedUnCustomBillOtherSet			, businessObject);
		}**/
		
		String taskId = customSingleOther.getWorkflowTaskId();
		id = customSingleOther.getCustomsclearid();
		if (null == taskId || "".equals(taskId))
			WorkflowService.createAndSignalProcessInstance(customSingleOther, id);
		else
			WorkflowService.signalProcessInstance(customSingleOther, id, null);
	}

	/**
	 * 保存或更新其他公司客户单清帐
	 * 保存  
	 *  
	 * @param customSingleOther
	 */
	public void _update(CustomSingleOther customSingleOther
,Set<UnCleaCollectOther> deletedUnCleaCollectOtherSet
,Set<UnCustomBillOther> deletedUnCustomBillOtherSet,BusinessObject businessObject	
	)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(customSingleOther);
		customSingleOtherHibernateDao.saveOrUpdate(customSingleOther);
// 删除关联子业务对象数据
if(deletedUnCleaCollectOtherSet!=null && deletedUnCleaCollectOtherSet.size()>0)
{
unCleaCollectOtherService._deletes(deletedUnCleaCollectOtherSet,businessObject);
}
// 删除关联子业务对象数据
if(deletedUnCustomBillOtherSet!=null && deletedUnCustomBillOtherSet.size()>0)
{
unCustomBillOtherService._deletes(deletedUnCustomBillOtherSet,businessObject);
}		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(customSingleOther);
	}
	
	/**
	 * 保存  
	 *   
	 * @param customSingleOther
	 */
	public void _save(CustomSingleOther customSingleOther)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(customSingleOther);
		// 对id赋空值，使之执行insert操作(适应复制创建场景)
		customSingleOther.setCustomsclearid(null);
       
     
     
     
       
     
     
     
       
     
     
     
       
     
     
     
       
     
     
     
       
     
     
     
       
     
     
     
       
     
     
     
       
     
     
     
       
     
     
     
       
     
     
     
       
     
     
     
       
     
     
     
       
     
     
     
       
     
     
     
       
     
     
     
       
     
     
     
       
     
     
     
       
     
     
     
       
     
     
     
       
         SettleSubjectOther SettleSubjectOther = customSingleOther.getSettleSubjectOther();
         if (null != SettleSubjectOther)
		 {
		    SettleSubjectOther.setSettlesubjectid(null);
		 }
		 customSingleOther.setSettleSubjectOther(SettleSubjectOther);
     
     
     
       
     
         FundFlowOther FundFlowOther = customSingleOther.getFundFlowOther();
         if (null != FundFlowOther)
		 {
		    FundFlowOther.setFundflowid(null);
		 }
		 customSingleOther.setFundFlowOther(FundFlowOther);
     
     
       
     
     
		Set<UnCleaCollectOther> UnCleaCollectOtherSet = customSingleOther.getUnCleaCollectOther();
		Set<UnCleaCollectOther> newUnCleaCollectOtherSet = null;
		if (null != UnCleaCollectOtherSet)
		{
			newUnCleaCollectOtherSet = new HashSet();
			Iterator<UnCleaCollectOther> itUnCleaCollectOther = UnCleaCollectOtherSet.iterator();
			while (itUnCleaCollectOther.hasNext())
			{
				UnCleaCollectOther UnCleaCollectOther = (UnCleaCollectOther) itUnCleaCollectOther.next();
				UnCleaCollectOther.setUnclearcollectid(null);
				newUnCleaCollectOtherSet.add(UnCleaCollectOther);
			}
		}
		customSingleOther.setUnCleaCollectOther(newUnCleaCollectOtherSet);
     
       
     
     
     
		Set<UnCustomBillOther> UnCustomBillOtherSet = customSingleOther.getUnCustomBillOther();
		Set<UnCustomBillOther> newUnCustomBillOtherSet = null;
		if (null != UnCustomBillOtherSet)
		{
			newUnCustomBillOtherSet = new HashSet();
			Iterator<UnCustomBillOther> itUnCustomBillOther = UnCustomBillOtherSet.iterator();
			while (itUnCustomBillOther.hasNext())
			{
				UnCustomBillOther UnCustomBillOther = (UnCustomBillOther) itUnCustomBillOther.next();
				UnCustomBillOther.setUnclearcusbillid(null);
				newUnCustomBillOtherSet.add(UnCustomBillOther);
			}
		}
		customSingleOther.setUnCustomBillOther(newUnCustomBillOtherSet);
		customSingleOtherHibernateDao.saveOrUpdate(customSingleOther);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(customSingleOther);
	}
	
	/**
	 *
	 * 保存  
	 *   
	 * @param customSingleOther
	 */
	public void _saveOrUpdate(CustomSingleOther customSingleOther
,Set<UnCleaCollectOther> deletedUnCleaCollectOtherSet
,Set<UnCustomBillOther> deletedUnCustomBillOtherSet,BusinessObject businessObject		
	)
	{
if (StringUtils.isNullBlank(customSingleOther.getCustomclearno()))
{
String customclearno = NumberService.getNextObjectNumber("customSingleOther", customSingleOther);
customSingleOther.setCustomclearno(customclearno);
}		if (StringUtils.isNullBlank(customSingleOther.getCustomsclearid()))
		{	
			_save(customSingleOther);
		}
		else
		{
			_update(customSingleOther
,deletedUnCleaCollectOtherSet
,deletedUnCustomBillOtherSet
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