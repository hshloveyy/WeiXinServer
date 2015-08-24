/*
 * @(#)BillClearCollectServiceGen.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年09月16日 09点35分03秒
 *  描　述：创建
 */
package com.infolion.xdss3.billclearcollectGen.service;

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
import com.infolion.xdss3.billclearcollect.domain.BillClearCollect;
import com.infolion.xdss3.billclearcollect.service.BillClearCollectService;
import com.infolion.xdss3.billclearcollect.dao.BillClearCollectHibernateDao;
          
import com.infolion.xdss3.settlesubjectbcc.domain.SettleSubjectBcc;
import com.infolion.xdss3.settlesubjectbcc.service.SettleSubjectBccService;
          
import com.infolion.xdss3.billclearitem.domain.BillClearItem;
import com.infolion.xdss3.billclearitem.service.BillClearItemService;
          
import com.infolion.xdss3.fundflowBcc.domain.FundFlowBcc;
import com.infolion.xdss3.fundflowBcc.service.FundFlowBccService;
          
import com.infolion.xdss3.billincollect.domain.BillInCollect;
import com.infolion.xdss3.billincollect.service.BillInCollectService;

/**
 * <pre>
 * 票清收款(BillClearCollect)服务类
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
public class BillClearCollectServiceGen extends BaseService
{
	protected Log log = LogFactory.getFactory().getLog(this.getClass());

	@Autowired
	protected BillClearCollectHibernateDao billClearCollectHibernateDao;
	
	public void setBillClearCollectHibernateDao(BillClearCollectHibernateDao billClearCollectHibernateDao)
	{
		this.billClearCollectHibernateDao = billClearCollectHibernateDao;
	}
	
     /**
	 * 注入扩展服务类
	 */
	 @Autowired(required = false)
	 public void setAdvanceService(@Qualifier("billClearCollectAdvanceService") AdvanceService advanceService)
	 {
	  	super.setAdvanceService(advanceService);
	 }   
	 
          

	@Autowired
	protected SettleSubjectBccService settleSubjectBccService;
	
	public void setSettleSubjectBccService(SettleSubjectBccService settleSubjectBccService)
	{
		this.settleSubjectBccService = settleSubjectBccService;
	}
          

	@Autowired
	protected BillClearItemService billClearItemService;
	
	public void setBillClearItemService(BillClearItemService billClearItemService)
	{
		this.billClearItemService = billClearItemService;
	}
          

	@Autowired
	protected FundFlowBccService fundFlowBccService;
	
	public void setFundFlowBccService(FundFlowBccService fundFlowBccService)
	{
		this.fundFlowBccService = fundFlowBccService;
	}
          

	@Autowired
	protected BillInCollectService billInCollectService;
	
	public void setBillInCollectService(BillInCollectService billInCollectService)
	{
		this.billInCollectService = billInCollectService;
	}

          
          
          
          

	   
	/**
	 * 根据主键ID,取得票清收款实例
	 * @param id
	 * @return
	 */
	public BillClearCollect _getDetached(String id)
	{		
	    BillClearCollect billClearCollect = new BillClearCollect();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  billClearCollect = billClearCollectHibernateDao.getDetached(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(billClearCollect);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    billClearCollect.setOperationType(operationType);
	    
		return billClearCollect;	
	}
	
	/**
	 * 根据主键ID,取得票清收款实例
	 * @param id
	 * @return
	 */
	public BillClearCollect _get(String id)
	{		
	    BillClearCollect billClearCollect = new BillClearCollect();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  billClearCollect = billClearCollectHibernateDao.get(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(billClearCollect);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    billClearCollect.setOperationType(operationType);
	    
		return billClearCollect;	
	}
	
	/**
	 * 根据主键ID,取得票清收款实例
	 * @param id
	 * @return
	 */
	public BillClearCollect _getForEdit(String id)
	{		
	    BillClearCollect billClearCollect = _get(id);
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = billClearCollect.getOperationType();
	    if(OperationType.READONLY.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotEdit);
	    }

		return billClearCollect;	
	}
	
	/**
	 * 根据主键ID,取得票清收款实例副本
	 * @param id
	 * @return
	 */
	public BillClearCollect _getEntityCopy(String id)
	{		
	    BillClearCollect billClearCollect = new BillClearCollect();
		BillClearCollect billClearCollectOld = this._get(id);
		try
		{
			BeanUtils.copyProperties(billClearCollect, billClearCollectOld);
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (InvocationTargetException e)
		{
			e.printStackTrace();
		}
		billClearCollect.setBillclearno(null); 
		billClearCollect.setBillclearid(null); 
		billClearCollect.setProcessstate(" ");
		return billClearCollect;	
	}
	
	/**
	 * 删除  
	 *   
	 * @param billClearCollect
	 */
	public void _delete(BillClearCollect billClearCollect)
	{
		if (null != advanceService)
			advanceService.preDelete(billClearCollect);
	
		//流程状态
		String processState =billClearCollect.getProcessstate();
		if(!StringUtils.isNullBlank(processState))
		{
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.CannotDeleteApproveData);
		}
 		LockService.isBoInstanceLocked(billClearCollect,BillClearCollect.class);
		billClearCollectHibernateDao.remove(billClearCollect);

		if (null != advanceService)
			advanceService.postDelete(billClearCollect);
	}

	/**
	 * 根据主键删除
	 * 
	 * @param billClearCollectId
	 */
	public void _delete(String billClearCollectId,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(billClearCollectId))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("billclearid"));
		BillClearCollect billClearCollect = this.billClearCollectHibernateDao.load(billClearCollectId);
		_delete(billClearCollect);
	}
	
	/**
	 * 删除  
	 *   
	 * @param Set<BillClearCollect> billClearCollects
	 */
	public void _deletes(Set<BillClearCollect> billClearCollects,BusinessObject businessObject)
	{
		if (null == billClearCollects || billClearCollects.size() < 1)
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoText());
		Iterator<BillClearCollect> it = billClearCollects.iterator();
		while (it.hasNext())
		{
			BillClearCollect billClearCollect = (BillClearCollect) it.next();
			_delete(billClearCollect);
		}
	}
	
	/**
	 * 根据多个主键(由，分割)删除多个对象
	 * 
	 * @param billClearCollectIds
	 */
	public void _deletes(String billClearCollectIds,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(billClearCollectIds))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("billclearid"));
		String[] ids = StringUtils.splitString(billClearCollectIds);
		for (int i = 0; i < ids.length; i++)
		{
			_delete(ids[i],businessObject);
		}
	}
	/**
	 * 提交工作流
	 * 
	 * @param billClearCollect
	 */
	public void _submitProcess(BillClearCollect billClearCollect
,Set<BillClearItem> deletedBillclearitemSet
,Set<BillInCollect> deletedBillincollectSet	,BusinessObject businessObject)
	{
		String id = billClearCollect.getBillclearid();
        /**
		if (StringUtils.isNullBlank(id))
		{
			_save(billClearCollect);
		}
		else
		{
			_update(billClearCollect
,deletedBillclearitemSet
,deletedBillincollectSet			, businessObject);
		}**/
		
		String taskId = billClearCollect.getWorkflowTaskId();
		id = billClearCollect.getBillclearid();
		if (null == taskId || "".equals(taskId))
			WorkflowService.createAndSignalProcessInstance(billClearCollect, id);
		else
			WorkflowService.signalProcessInstance(billClearCollect, id, null);
	}

	/**
	 * 保存或更新票清收款
	 * 保存  
	 *  
	 * @param billClearCollect
	 */
	public void _update(BillClearCollect billClearCollect
,Set<BillClearItem> deletedBillClearItemSet
,Set<BillInCollect> deletedBillInCollectSet,BusinessObject businessObject	
	)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(billClearCollect);
		billClearCollectHibernateDao.saveOrUpdate(billClearCollect);
// 删除关联子业务对象数据
if(deletedBillClearItemSet!=null && deletedBillClearItemSet.size()>0)
{
billClearItemService._deletes(deletedBillClearItemSet,businessObject);
}
// 删除关联子业务对象数据
if(deletedBillInCollectSet!=null && deletedBillInCollectSet.size()>0)
{
billInCollectService._deletes(deletedBillInCollectSet,businessObject);
}		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(billClearCollect);
	}
	
	/**
	 * 保存  
	 *   
	 * @param billClearCollect
	 */
	public void _save(BillClearCollect billClearCollect)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(billClearCollect);
		// 对id赋空值，使之执行insert操作(适应复制创建场景)
		billClearCollect.setBillclearid(null);
       
     
     
     
       
     
     
     
       
     
     
     
       
     
     
     
       
     
     
     
       
     
     
     
       
     
     
     
       
     
     
     
       
     
     
     
       
     
     
     
       
     
     
     
       
     
     
     
       
     
     
     
       
     
     
     
       
     
     
     
       
     
     
     
       
     
     
     
       
     
     
     
       
     
     
     
       
         SettleSubjectBcc settleSubjectBcc = billClearCollect.getSettleSubjectBcc();
         if (null != settleSubjectBcc)
		 {
		    settleSubjectBcc.setSettlesubjectid(null);
		 }
		 billClearCollect.setSettleSubjectBcc(settleSubjectBcc);
     
     
     
       
     
		Set<BillClearItem> billclearitemSet = billClearCollect.getBillclearitem();
		Set<BillClearItem> newBillClearItemSet = null;
		if (null != billclearitemSet)
		{
			newBillClearItemSet = new HashSet();
			Iterator<BillClearItem> itBillClearItem = billclearitemSet.iterator();
			while (itBillClearItem.hasNext())
			{
				BillClearItem billclearitem = (BillClearItem) itBillClearItem.next();
				billclearitem.setBillclearitemid(null);
				newBillClearItemSet.add(billclearitem);
			}
		}
		billClearCollect.setBillclearitem(newBillClearItemSet);
     
     
       
     
     
         FundFlowBcc fundFlowBcc = billClearCollect.getFundFlowBcc();
         if (null != fundFlowBcc)
		 {
		    fundFlowBcc.setFundflowid(null);
		 }
		 billClearCollect.setFundFlowBcc(fundFlowBcc);
     
       
     
     
     
		Set<BillInCollect> billincollectSet = billClearCollect.getBillincollect();
		Set<BillInCollect> newBillInCollectSet = null;
		if (null != billincollectSet)
		{
			newBillInCollectSet = new HashSet();
			Iterator<BillInCollect> itBillInCollect = billincollectSet.iterator();
			while (itBillInCollect.hasNext())
			{
				BillInCollect billincollect = (BillInCollect) itBillInCollect.next();
				billincollect.setBillincollectid(null);
				newBillInCollectSet.add(billincollect);
			}
		}
		billClearCollect.setBillincollect(newBillInCollectSet);
		billClearCollectHibernateDao.saveOrUpdate(billClearCollect);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(billClearCollect);
	}
	
	/**
	 *
	 * 保存  
	 *   
	 * @param billClearCollect
	 */
	public void _saveOrUpdate(BillClearCollect billClearCollect
,Set<BillClearItem> deletedBillClearItemSet
,Set<BillInCollect> deletedBillInCollectSet,BusinessObject businessObject		
	)
	{
if (StringUtils.isNullBlank(billClearCollect.getBillclearno()))
{
String billclearno = NumberService.getNextObjectNumber("billclearnoCollect", billClearCollect);
billClearCollect.setBillclearno(billclearno);
}		if (StringUtils.isNullBlank(billClearCollect.getBillclearid()))
		{	
			_save(billClearCollect);
		}
		else
		{
			_update(billClearCollect
,deletedBillClearItemSet
,deletedBillInCollectSet
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