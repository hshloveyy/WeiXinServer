/*
 * @(#)BillPurchasedServiceGen.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年12月15日 16点10分17秒
 *  描　述：创建
 */
package com.infolion.xdss3.billpurchasedGen.service;

import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.infolion.platform.basicApp.authManage.domain.OperationType;
import com.infolion.platform.bo.domain.BusinessObject;
import com.infolion.platform.dpframework.core.BusinessException;
import com.infolion.platform.dpframework.core.service.AdvanceService;
import com.infolion.platform.dpframework.core.service.BaseService;
import com.infolion.platform.dpframework.uicomponent.lock.LockException;
import com.infolion.platform.dpframework.uicomponent.lock.LockService;
import com.infolion.platform.dpframework.uicomponent.number.service.NumberService;
import com.infolion.platform.dpframework.uicomponent.systemMessage.SysMsgConstants;
import com.infolion.platform.dpframework.util.StringUtils;
import com.infolion.platform.workflow.engine.WorkflowService;
import com.infolion.xdss3.billPurBankItem.domain.BillPurBankItem;
import com.infolion.xdss3.billPurBankItem.service.BillPurBankItemService;
import com.infolion.xdss3.billPurBillItem.domain.BillPurBillItem;
import com.infolion.xdss3.billPurBillItem.service.BillPurBillItemService;
import com.infolion.xdss3.billPurReBankItem.domain.BillPurReBankItem;
import com.infolion.xdss3.billPurReBankItem.domain.BillPurReBankTwo;
import com.infolion.xdss3.billPurReBankItem.service.BillPurReBankItemService;
import com.infolion.xdss3.billpurchased.dao.BillPurchasedHibernateDao;
import com.infolion.xdss3.billpurchased.domain.BillPurchased;
import com.infolion.xdss3.financeSquare.domain.FundFlow;
import com.infolion.xdss3.financeSquare.domain.SettleSubject;
import com.infolion.xdss3.financeSquare.service.FundFlowService;
import com.infolion.xdss3.financeSquare.service.SettleSubjectService;

/**
 * <pre>
 * 出口押汇(BillPurchased)服务类
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
public class BillPurchasedServiceGen extends BaseService
{
	protected Log log = LogFactory.getFactory().getLog(this.getClass());

	@Autowired
	protected BillPurchasedHibernateDao billPurchasedHibernateDao;
	
	public void setBillPurchasedHibernateDao(BillPurchasedHibernateDao billPurchasedHibernateDao)
	{
		this.billPurchasedHibernateDao = billPurchasedHibernateDao;
	}
	
     /**
	 * 注入扩展服务类
	 */
	 @Autowired(required = false)
	 public void setAdvanceService(@Qualifier("billPurchasedAdvanceService") AdvanceService advanceService)
	 {
	  	super.setAdvanceService(advanceService);
	 }   
	 
          

	@Autowired
	protected BillPurBillItemService billPurBillItemService;
	
	public void setBillPurBillItemService(BillPurBillItemService billPurBillItemService)
	{
		this.billPurBillItemService = billPurBillItemService;
	}
          

	@Autowired
	protected BillPurBankItemService billPurBankItemService;
	
	public void setBillPurBankItemService(BillPurBankItemService billPurBankItemService)
	{
		this.billPurBankItemService = billPurBankItemService;
	}
          

	@Autowired
	protected BillPurReBankItemService billPurReBankItemService;
	
	public void setBillPurReBankItemService(BillPurReBankItemService billPurReBankItemService)
	{
		this.billPurReBankItemService = billPurReBankItemService;
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
	 * 根据主键ID,取得出口押汇实例
	 * @param id
	 * @return
	 */
	public BillPurchased _getDetached(String id)
	{		
	    BillPurchased billPurchased = new BillPurchased();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  billPurchased = billPurchasedHibernateDao.getDetached(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(billPurchased);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    billPurchased.setOperationType(operationType);
	    
		return billPurchased;	
	}
	
	/**
	 * 根据主键ID,取得出口押汇实例
	 * @param id
	 * @return
	 */
	public BillPurchased _get(String id)
	{		
	    BillPurchased billPurchased = new BillPurchased();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  billPurchased = billPurchasedHibernateDao.get(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(billPurchased);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    billPurchased.setOperationType(operationType);
	    
		return billPurchased;	
	}
	
	/**
	 * 根据主键ID,取得出口押汇实例
	 * @param id
	 * @return
	 */
	public BillPurchased _getForEdit(String id)
	{		
	    BillPurchased billPurchased = _get(id);
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = billPurchased.getOperationType();
	    if(OperationType.READONLY.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotEdit);
	    }

		return billPurchased;	
	}
	
	/**
	 * 根据主键ID,取得出口押汇实例副本
	 * @param id
	 * @return
	 */
	public BillPurchased _getEntityCopy(String id)
	{		
	    BillPurchased billPurchased = new BillPurchased();
		BillPurchased billPurchasedOld = this._get(id);
		try
		{
			BeanUtils.copyProperties(billPurchased, billPurchasedOld);
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (InvocationTargetException e)
		{
			e.printStackTrace();
		}
		//billPurchased.setBillpurid(null); 
		billPurchased.setProcessstate(" ");
		return billPurchased;	
	}
	
	/**
	 * 删除  
	 *   
	 * @param billPurchased
	 */
	public void _delete(BillPurchased billPurchased)
	{
		if (null != advanceService)
			advanceService.preDelete(billPurchased);
	
		//流程状态
		String processState =billPurchased.getProcessstate();
		if(!StringUtils.isNullBlank(processState))
		{
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.CannotDeleteApproveData);
		}
 		LockService.isBoInstanceLocked(billPurchased,BillPurchased.class);
		billPurchasedHibernateDao.remove(billPurchased);

		if (null != advanceService)
			advanceService.postDelete(billPurchased);
	}

	/**
	 * 根据主键删除
	 * 
	 * @param billPurchasedId
	 */
	public void _delete(String billPurchasedId,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(billPurchasedId))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("billpurid"));
		BillPurchased billPurchased = this.billPurchasedHibernateDao.load(billPurchasedId);
		_delete(billPurchased);
	}
	
	/**
	 * 删除  
	 *   
	 * @param Set<BillPurchased> billPurchaseds
	 */
	public void _deletes(Set<BillPurchased> billPurchaseds,BusinessObject businessObject)
	{
		if (null == billPurchaseds || billPurchaseds.size() < 1)
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoText());
		Iterator<BillPurchased> it = billPurchaseds.iterator();
		while (it.hasNext())
		{
			BillPurchased billPurchased = (BillPurchased) it.next();
			_delete(billPurchased);
		}
	}
	
	/**
	 * 根据多个主键(由，分割)删除多个对象
	 * 
	 * @param billPurchasedIds
	 */
	public void _deletes(String billPurchasedIds,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(billPurchasedIds))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("billpurid"));
		String[] ids = StringUtils.splitString(billPurchasedIds);
		for (int i = 0; i < ids.length; i++)
		{
			_delete(ids[i],businessObject);
		}
	}
	/**
	 * 提交工作流
	 * 
	 * @param billPurchased
	 */
	public void _submitProcess(BillPurchased billPurchased
,Set<BillPurBillItem> deletedBillPurBillItemSet
,Set<BillPurBankItem> deletedBillPurBankItemSet
,Set<BillPurReBankItem> deletedBillPurReBankItemSet	,BusinessObject businessObject)
	{
		String id = billPurchased.getBillpurid();
        /**
		if (StringUtils.isNullBlank(id))
		{
			_save(billPurchased);
		}
		else
		{
			_update(billPurchased
,deletedBillPurBillItemSet
,deletedBillPurBankItemSet
,deletedBillPurReBankItemSet			, businessObject);
		}**/
		
		String taskId = billPurchased.getWorkflowTaskId();
		id = billPurchased.getBillpurid();
		if (null == taskId || "".equals(taskId))
			WorkflowService.createAndSignalProcessInstance(billPurchased, id);
		else
			WorkflowService.signalProcessInstance(billPurchased, id, null);
	}

	/**
	 * 保存或更新出口押汇
	 * 保存  
	 *  
	 * @param billPurchased
	 */
    public void _update(BillPurchased billPurchased,
            Set<BillPurBillItem> deletedBillPurBillItemSet,
            Set<BillPurBankItem> deletedBillPurBankItemSet,
            Set<BillPurReBankItem> deletedBillPurReBankItemSet, BusinessObject businessObject) {
        // 用户二次开发服务，保存前执行
        if (null != advanceService)
            advanceService.preSaveOrUpdate(billPurchased);
        billPurchasedHibernateDao.saveOrUpdate(billPurchased);
        // 删除关联子业务对象数据
        if (deletedBillPurBillItemSet != null && deletedBillPurBillItemSet.size() > 0) {
            billPurBillItemService._deletes(deletedBillPurBillItemSet, businessObject);
        }
        // 删除关联子业务对象数据
        if (deletedBillPurBankItemSet != null && deletedBillPurBankItemSet.size() > 0) {
            billPurBankItemService._deletes(deletedBillPurBankItemSet, businessObject);
        }
        // 删除关联子业务对象数据
        if (deletedBillPurReBankItemSet != null && deletedBillPurReBankItemSet.size() > 0) {
            billPurReBankItemService._deletes(deletedBillPurReBankItemSet, businessObject);
        } // 用户二次开发服务，保存后执行
        if (null != advanceService)
            advanceService.postSaveOrUpdate(billPurchased);
    }
	
	/**
	 * 保存  
	 *   
	 * @param billPurchased
	 */
	public void _save(BillPurchased billPurchased)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(billPurchased);
		// 对id赋空值，使之执行insert操作(适应复制创建场景)
		billPurchased.setBillpurid(null);
       
     
     
       
     
     
       
     
     
       
     
     
       
     
     
       
     
     
       
     
     
       
     
     
       
     
     
       
     
     
       
     
     
       
     
     
       
     
     
       
     
     
       
     
     
       
     
     
       
     
     
       
     
     
       
		Set<BillPurBillItem> billPurBillItemSet = billPurchased.getBillPurBillItem();
		Set<BillPurBillItem> newBillPurBillItemSet = null;
		if (null != billPurBillItemSet)
		{
			newBillPurBillItemSet = new HashSet();
			Iterator<BillPurBillItem> itBillPurBillItem = billPurBillItemSet.iterator();
			while (itBillPurBillItem.hasNext())
			{
				BillPurBillItem billPurBillItem = (BillPurBillItem) itBillPurBillItem.next();
				billPurBillItem.setBillitemid(null);
				newBillPurBillItemSet.add(billPurBillItem);
			}
		}
		billPurchased.setBillPurBillItem(newBillPurBillItemSet);
     
     
       
     
		Set<BillPurBankItem> billPurBankItemSet = billPurchased.getBillPurBankItem();
		Set<BillPurBankItem> newBillPurBankItemSet = null;
		if (null != billPurBankItemSet)
		{
			newBillPurBankItemSet = new HashSet();
			Iterator<BillPurBankItem> itBillPurBankItem = billPurBankItemSet.iterator();
			while (itBillPurBankItem.hasNext())
			{
				BillPurBankItem billPurBankItem = (BillPurBankItem) itBillPurBankItem.next();
				billPurBankItem.setBankitemid(null);
				newBillPurBankItemSet.add(billPurBankItem);
			}
		}
		billPurchased.setBillPurBankItem(newBillPurBankItemSet);
     
       
     
     
		Set<BillPurReBankItem> billPurReBankItemSet = billPurchased.getBillPurReBankItem();
		Set<BillPurReBankItem> newBillPurReBankItemSet = null;
		if (null != billPurReBankItemSet)
		{
			newBillPurReBankItemSet = new HashSet();
			Iterator<BillPurReBankItem> itBillPurReBankItem = billPurReBankItemSet.iterator();
			while (itBillPurReBankItem.hasNext())
			{
				BillPurReBankItem billPurReBankItem = (BillPurReBankItem) itBillPurReBankItem.next();
				billPurReBankItem.setBankitemid(null);
				newBillPurReBankItemSet.add(billPurReBankItem);
			}
		}
		billPurchased.setBillPurReBankItem(newBillPurReBankItemSet);
     
     
       
     
     
     
         SettleSubject settleSubject = billPurchased.getSettleSubject();
         if (null != settleSubject)
		 {
		    settleSubject.setSettlesubjectid(null);
		 }
		 billPurchased.setSettleSubject(settleSubject);
     
       
     
     
     
     
         FundFlow fundFlow = billPurchased.getFundFlow();
         if (null != fundFlow)
		 {
		    fundFlow.setFundflowid(null);
		 }
		 billPurchased.setFundFlow(fundFlow);
		billPurchasedHibernateDao.saveOrUpdate(billPurchased);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(billPurchased);
	}
	
    /**
     * 
     * 保存
     * 
     * @param billPurchased
     */
    public void _saveOrUpdate(BillPurchased billPurchased,
            Set<BillPurBillItem> deletedBillPurBillItemSet,
            Set<BillPurBankItem> deletedBillPurBankItemSet,
            Set<BillPurReBankItem> deletedBillPurReBankItemSet, BusinessObject businessObject) {
        if (StringUtils.isNullBlank(billPurchased.getBillpur_no())) {
            String billpur_no = NumberService.getNextObjectNumber("BillPurNO", billPurchased);
            billPurchased.setBillpur_no(billpur_no);
        }
        if (StringUtils.isNullBlank(billPurchased.getBillpurid())) {
            _save(billPurchased);
        } else {
            for (BillPurReBankItem reItem : billPurchased.getBillPurReBankItem()) {
                for (BillPurReBankTwo reTwo : billPurchased.getBillPurReBankTwo()) {
                    if (StringUtils.isNotEmpty(reItem.getBankitemid()) && reItem.getBankitemid().equals(reTwo.getBankitemid())) {
                        reTwo.setCurrency3(reItem.getCurrency());
                    }
                }
            }
            _update(billPurchased, deletedBillPurBillItemSet, deletedBillPurBankItemSet,
                    deletedBillPurReBankItemSet, businessObject);
        }
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
}