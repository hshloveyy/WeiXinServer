/*
 * @(#)CustomSingleClearServiceGen.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年07月19日 11点44分17秒
 *  描　述：创建
 */
package com.infolion.xdss3.singleClearGen.service;

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
import com.infolion.xdss3.singleClear.domain.CustomSingleClear;
import com.infolion.xdss3.singleClear.service.CustomSingleClearService;
import com.infolion.xdss3.singleClear.dao.CustomSingleClearHibernateDao;
          
import com.infolion.xdss3.singleClear.domain.UnClearCustomBill;
import com.infolion.xdss3.singleClear.service.UnClearCustomBillService;
          
import com.infolion.xdss3.singleClear.domain.UnClearCollect;
import com.infolion.xdss3.singleClear.service.UnClearCollectService;
          
import com.infolion.xdss3.financeSquare.domain.SettleSubject;
import com.infolion.xdss3.financeSquare.service.SettleSubjectService;
          
import com.infolion.xdss3.financeSquare.domain.FundFlow;
import com.infolion.xdss3.financeSquare.service.FundFlowService;

/**
 * <pre>
 * 客户单清帐(CustomSingleClear)服务类
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
public class CustomSingleClearServiceGen extends BaseService
{
	protected Log log = LogFactory.getFactory().getLog(this.getClass());

	@Autowired
	protected CustomSingleClearHibernateDao customSingleClearHibernateDao;
	
	public void setCustomSingleClearHibernateDao(CustomSingleClearHibernateDao customSingleClearHibernateDao)
	{
		this.customSingleClearHibernateDao = customSingleClearHibernateDao;
	}
	
     /**
	 * 注入扩展服务类
	 */
	 @Autowired(required = false)
	 public void setAdvanceService(@Qualifier("customSingleClearAdvanceService") AdvanceService advanceService)
	 {
	  	super.setAdvanceService(advanceService);
	 }   
	 
          

	@Autowired
	protected UnClearCustomBillService unClearCustomBillService;
	
	public void setUnClearCustomBillService(UnClearCustomBillService unClearCustomBillService)
	{
		this.unClearCustomBillService = unClearCustomBillService;
	}
          

	@Autowired
	protected UnClearCollectService unClearCollectService;
	
	public void setUnClearCollectService(UnClearCollectService unClearCollectService)
	{
		this.unClearCollectService = unClearCollectService;
	}
          

	@Autowired
	protected SettleSubjectService settleSubjectService;
	
	public void setSettleSubjectService(SettleSubjectService settleSubjectService)
	{
		this.settleSubjectService = settleSubjectService;
	}
          

	@Autowired
	protected FundFlowService fundFlowService;
	
	public void setFundFlowService(FundFlowService fundFlowService)
	{
		this.fundFlowService = fundFlowService;
	}

          
          
          
          

	   
	/**
	 * 根据主键ID,取得客户单清帐实例
	 * @param id
	 * @return
	 */
	public CustomSingleClear _getDetached(String id)
	{		
	    CustomSingleClear customSingleClear = new CustomSingleClear();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  customSingleClear = customSingleClearHibernateDao.getDetached(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(customSingleClear);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    customSingleClear.setOperationType(operationType);
	    
		return customSingleClear;	
	}
	
	/**
	 * 根据主键ID,取得客户单清帐实例
	 * @param id
	 * @return
	 */
	public CustomSingleClear _get(String id)
	{		
	    CustomSingleClear customSingleClear = new CustomSingleClear();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  customSingleClear = customSingleClearHibernateDao.get(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(customSingleClear);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    customSingleClear.setOperationType(operationType);
	    
		return customSingleClear;	
	}
	
	/**
	 * 根据主键ID,取得客户单清帐实例
	 * @param id
	 * @return
	 */
	public CustomSingleClear _getForEdit(String id)
	{		
	    CustomSingleClear customSingleClear = _get(id);
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = customSingleClear.getOperationType();
	    if(OperationType.READONLY.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotEdit);
	    }

		return customSingleClear;	
	}
	
	/**
	 * 根据主键ID,取得客户单清帐实例副本
	 * @param id
	 * @return
	 */
	public CustomSingleClear _getEntityCopy(String id)
	{		
	    CustomSingleClear customSingleClear = new CustomSingleClear();
		CustomSingleClear customSingleClearOld = this._get(id);
		try
		{
			BeanUtils.copyProperties(customSingleClear, customSingleClearOld);
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (InvocationTargetException e)
		{
			e.printStackTrace();
		}
		//customSingleClear.setCustomsclearid(null); 
		customSingleClear.setProcessstate(" ");
		return customSingleClear;	
	}
	
	/**
	 * 删除  
	 *   
	 * @param customSingleClear
	 */
	public void _delete(CustomSingleClear customSingleClear)
	{
		if (null != advanceService)
			advanceService.preDelete(customSingleClear);
	
		//流程状态
		String processState =customSingleClear.getProcessstate();
		if(!StringUtils.isNullBlank(processState))
		{
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.CannotDeleteApproveData);
		}
 		LockService.isBoInstanceLocked(customSingleClear,CustomSingleClear.class);
		customSingleClearHibernateDao.remove(customSingleClear);

		if (null != advanceService)
			advanceService.postDelete(customSingleClear);
	}

	/**
	 * 根据主键删除
	 * 
	 * @param customSingleClearId
	 */
	public void _delete(String customSingleClearId,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(customSingleClearId))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("customsclearid"));
		CustomSingleClear customSingleClear = this.customSingleClearHibernateDao.load(customSingleClearId);
		_delete(customSingleClear);
	}
	
	/**
	 * 删除  
	 *   
	 * @param Set<CustomSingleClear> customSingleClears
	 */
	public void _deletes(Set<CustomSingleClear> customSingleClears,BusinessObject businessObject)
	{
		if (null == customSingleClears || customSingleClears.size() < 1)
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoText());
		Iterator<CustomSingleClear> it = customSingleClears.iterator();
		while (it.hasNext())
		{
			CustomSingleClear customSingleClear = (CustomSingleClear) it.next();
			_delete(customSingleClear);
		}
	}
	
	/**
	 * 根据多个主键(由，分割)删除多个对象
	 * 
	 * @param customSingleClearIds
	 */
	public void _deletes(String customSingleClearIds,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(customSingleClearIds))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("customsclearid"));
		String[] ids = StringUtils.splitString(customSingleClearIds);
		for (int i = 0; i < ids.length; i++)
		{
			_delete(ids[i],businessObject);
		}
	}
	/**
	 * 提交工作流
	 * 
	 * @param customSingleClear
	 */
	public void _submitProcess(CustomSingleClear customSingleClear
,Set<UnClearCustomBill> deletedUnClearCustomBillSet
,Set<UnClearCollect> deletedUnClearCollectSet	,BusinessObject businessObject)
	{
		String id = customSingleClear.getCustomsclearid();
        /**
		if (StringUtils.isNullBlank(id))
		{
			_save(customSingleClear);
		}
		else
		{
			_update(customSingleClear
,deletedUnClearCustomBillSet
,deletedUnClearCollectSet			, businessObject);
		}**/
		
		String taskId = customSingleClear.getWorkflowTaskId();
		id = customSingleClear.getCustomsclearid();
		if (null == taskId || "".equals(taskId))
			WorkflowService.createAndSignalProcessInstance(customSingleClear, id);
		else
			WorkflowService.signalProcessInstance(customSingleClear, id, null);
	}

	/**
	 * 保存或更新客户单清帐
	 * 保存  
	 *  
	 * @param customSingleClear
	 */
	public void _update(CustomSingleClear customSingleClear
,Set<UnClearCustomBill> deletedUnClearCustomBillSet
,Set<UnClearCollect> deletedUnClearCollectSet,BusinessObject businessObject	
	)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(customSingleClear);
		customSingleClearHibernateDao.saveOrUpdate(customSingleClear);
// 删除关联子业务对象数据
if(deletedUnClearCustomBillSet!=null && deletedUnClearCustomBillSet.size()>0)
{
unClearCustomBillService._deletes(deletedUnClearCustomBillSet,businessObject);
}
// 删除关联子业务对象数据
if(deletedUnClearCollectSet!=null && deletedUnClearCollectSet.size()>0)
{
unClearCollectService._deletes(deletedUnClearCollectSet,businessObject);
}		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(customSingleClear);
	}
	
	/**
	 * 保存  
	 *   
	 * @param customSingleClear
	 */
	public void _save(CustomSingleClear customSingleClear)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(customSingleClear);
		// 对id赋空值，使之执行insert操作(适应复制创建场景)
		customSingleClear.setCustomsclearid(null);
       
     
     
     
       
     
     
     
       
     
     
     
       
     
     
     
       
     
     
     
       
     
     
     
       
     
     
     
       
     
     
     
       
     
     
     
       
     
     
     
       
     
     
     
       
     
     
     
       
     
     
     
       
     
     
     
       
     
     
     
       
     
     
     
       
		Set<UnClearCustomBill> unClearCustomBillSet = customSingleClear.getUnClearCustomBill();
		Set<UnClearCustomBill> newUnClearCustomBillSet = null;
		if (null != unClearCustomBillSet)
		{
			newUnClearCustomBillSet = new HashSet();
			Iterator<UnClearCustomBill> itUnClearCustomBill = unClearCustomBillSet.iterator();
			while (itUnClearCustomBill.hasNext())
			{
				UnClearCustomBill unClearCustomBill = (UnClearCustomBill) itUnClearCustomBill.next();
				unClearCustomBill.setUnclearcusbillid(null);
				newUnClearCustomBillSet.add(unClearCustomBill);
			}
		}
		customSingleClear.setUnClearCustomBill(newUnClearCustomBillSet);
     
     
     
       
     
		Set<UnClearCollect> unClearCollectSet = customSingleClear.getUnClearCollect();
		Set<UnClearCollect> newUnClearCollectSet = null;
		if (null != unClearCollectSet)
		{
			newUnClearCollectSet = new HashSet();
			Iterator<UnClearCollect> itUnClearCollect = unClearCollectSet.iterator();
			while (itUnClearCollect.hasNext())
			{
				UnClearCollect unClearCollect = (UnClearCollect) itUnClearCollect.next();
				unClearCollect.setUnclearcollectid(null);
				newUnClearCollectSet.add(unClearCollect);
			}
		}
		customSingleClear.setUnClearCollect(newUnClearCollectSet);
     
     
       
     
     
         SettleSubject settleSubject = customSingleClear.getSettleSubject();
         if (null != settleSubject)
		 {
		    settleSubject.setSettlesubjectid(null);
		 }
		 customSingleClear.setSettleSubject(settleSubject);
     
       
     
     
     
         FundFlow fundFlow = customSingleClear.getFundFlow();
         if (null != fundFlow)
		 {
		    fundFlow.setFundflowid(null);
		 }
		 customSingleClear.setFundFlow(fundFlow);
		customSingleClearHibernateDao.saveOrUpdate(customSingleClear);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(customSingleClear);
	}
	
	/**
	 *
	 * 保存  
	 *   
	 * @param customSingleClear
	 */
	public void _saveOrUpdate(CustomSingleClear customSingleClear
,Set<UnClearCustomBill> deletedUnClearCustomBillSet
,Set<UnClearCollect> deletedUnClearCollectSet,BusinessObject businessObject		
	)
	{
		if (StringUtils.isNullBlank(customSingleClear.getCustomsclearid()))
		{	
			_save(customSingleClear);
		}
		else
		{
			_update(customSingleClear
,deletedUnClearCustomBillSet
,deletedUnClearCollectSet
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