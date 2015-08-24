/*
 * @(#)ImportPaymentServiceGen.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年07月25日 09点52分16秒
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

import com.infolion.xdss3.billPayReBankItem.domain.BillPayReBankItem;
import com.infolion.xdss3.billPayReBankItem.service.BillPayReBankItemService;
import com.infolion.xdss3.payment.domain.ImportPayment;
import com.infolion.xdss3.payment.service.ImportPaymentService;
import com.infolion.xdss3.payment.dao.ImportPaymentHibernateDao;
          
import com.infolion.xdss3.payment.domain.ImportPaymentItem;
import com.infolion.xdss3.payment.service.ImportPaymentItemService;
          
import com.infolion.xdss3.payment.domain.ImportPaymentCbill;
import com.infolion.xdss3.payment.service.ImportPaymentCbillService;
          
import com.infolion.xdss3.payment.domain.ImportPayBankItem;
import com.infolion.xdss3.payment.service.ImportPayBankItemService;
          
import com.infolion.xdss3.payment.domain.ImportDocuBankItem;
import com.infolion.xdss3.payment.service.ImportDocuBankItemService;
          
import com.infolion.xdss3.payment.domain.ImportSettSubj;
import com.infolion.xdss3.payment.service.ImportSettSubjService;
          
import com.infolion.xdss3.payment.domain.ImportFundFlow;
import com.infolion.xdss3.payment.service.ImportFundFlowService;
          
import com.infolion.xdss3.payment.domain.ImportRelatPayment;
import com.infolion.xdss3.payment.service.ImportRelatPaymentService;

/**
 * <pre>
 * 进口付款(ImportPayment)服务类
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
public class ImportPaymentServiceGen extends BaseService
{
	protected Log log = LogFactory.getFactory().getLog(this.getClass());

	@Autowired
	protected ImportPaymentHibernateDao importPaymentHibernateDao;
	
	public void setImportPaymentHibernateDao(ImportPaymentHibernateDao importPaymentHibernateDao)
	{
		this.importPaymentHibernateDao = importPaymentHibernateDao;
	}
	
     /**
	 * 注入扩展服务类
	 */
	 @Autowired(required = false)
	 public void setAdvanceService(@Qualifier("importPaymentAdvanceService") AdvanceService advanceService)
	 {
	  	super.setAdvanceService(advanceService);
	 }   
	 
          

	@Autowired
	protected ImportPaymentItemService importPaymentItemService;
	
	public void setImportPaymentItemService(ImportPaymentItemService importPaymentItemService)
	{
		this.importPaymentItemService = importPaymentItemService;
	}
          

	@Autowired
	protected ImportPaymentCbillService importPaymentCbillService;
	
	public void setImportPaymentCbillService(ImportPaymentCbillService importPaymentCbillService)
	{
		this.importPaymentCbillService = importPaymentCbillService;
	}
          

	@Autowired
	protected ImportPayBankItemService importPayBankItemService;
	
	public void setImportPayBankItemService(ImportPayBankItemService importPayBankItemService)
	{
		this.importPayBankItemService = importPayBankItemService;
	}
          

	@Autowired
	protected ImportDocuBankItemService importDocuBankItemService;
	
	public void setImportDocuBankItemService(ImportDocuBankItemService importDocuBankItemService)
	{
		this.importDocuBankItemService = importDocuBankItemService;
	}
          
	@Autowired
	protected BillPayReBankItemService billPayReBankItemService;
	
	/**
	 * @param billPayReBankItemService the billPayReBankItemService to set
	 */
	public void setBillPayReBankItemService(
			BillPayReBankItemService billPayReBankItemService) {
		this.billPayReBankItemService = billPayReBankItemService;
	}
	
	@Autowired
	protected ImportSettSubjService importSettSubjService;
	
	public void setImportSettSubjService(ImportSettSubjService importSettSubjService)
	{
		this.importSettSubjService = importSettSubjService;
	}
          

	@Autowired
	protected ImportFundFlowService importFundFlowService;
	
	public void setImportFundFlowService(ImportFundFlowService importFundFlowService)
	{
		this.importFundFlowService = importFundFlowService;
	}
          

	@Autowired
	protected ImportRelatPaymentService importRelatPaymentService;
	
	public void setImportRelatPaymentService(ImportRelatPaymentService importRelatPaymentService)
	{
		this.importRelatPaymentService = importRelatPaymentService;
	}

          
          
          
          
          
          
          

	   
	/**
	 * 根据主键ID,取得进口付款实例
	 * @param id
	 * @return
	 */
	public ImportPayment _getDetached(String id)
	{		
	    ImportPayment importPayment = new ImportPayment();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  importPayment = importPaymentHibernateDao.getDetached(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(importPayment);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    importPayment.setOperationType(operationType);
	    
		return importPayment;	
	}
	
	/**
	 * 根据主键ID,取得进口付款实例
	 * @param id
	 * @return
	 */
	public ImportPayment _get(String id)
	{		
	    ImportPayment importPayment = new ImportPayment();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  importPayment = importPaymentHibernateDao.get(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(importPayment);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    importPayment.setOperationType(operationType);
	    
		return importPayment;	
	}
	
	/**
	 * 根据主键ID,取得进口付款实例
	 * @param id
	 * @return
	 */
	public ImportPayment _getForEdit(String id)
	{		
	    ImportPayment importPayment = _get(id);
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = importPayment.getOperationType();
	    if(OperationType.READONLY.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotEdit);
	    }

		return importPayment;	
	}
	
	/**
	 * 根据主键ID,取得进口付款实例副本
	 * @param id
	 * @return
	 */
	public ImportPayment _getEntityCopy(String id)
	{		
	    ImportPayment importPayment = new ImportPayment();
		ImportPayment importPaymentOld = this._get(id);
		try
		{
			BeanUtils.copyProperties(importPayment, importPaymentOld);
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (InvocationTargetException e)
		{
			e.printStackTrace();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		importPayment.setPaymentno(null); 
		//importPayment.setPaymentid(null); 
		importPayment.setProcessstate(" ");
		return importPayment;	
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
	 * @param importPayment
	 */
	public void _delete(ImportPayment importPayment)
	{
		if (null != advanceService)
			advanceService.preDelete(importPayment);
	
		//流程状态
		String processState =importPayment.getProcessstate();
		if(!StringUtils.isNullBlank(processState))
		{
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.CannotDeleteApproveData);
		}
 		LockService.isBoInstanceLocked(importPayment,ImportPayment.class);
		importPaymentHibernateDao.remove(importPayment);

		if (null != advanceService)
			advanceService.postDelete(importPayment);
	}

	/**
	 * 根据主键删除
	 * 
	 * @param importPaymentId
	 */
	public void _delete(String importPaymentId,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(importPaymentId))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("paymentid"));
		ImportPayment importPayment = this.importPaymentHibernateDao.load(importPaymentId);
		_delete(importPayment);
	}
	
	/**
	 * 删除  
	 *   
	 * @param Set<ImportPayment> importPayments
	 */
	public void _deletes(Set<ImportPayment> importPayments,BusinessObject businessObject)
	{
		if (null == importPayments || importPayments.size() < 1)
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoText());
		Iterator<ImportPayment> it = importPayments.iterator();
		while (it.hasNext())
		{
			ImportPayment importPayment = (ImportPayment) it.next();
			_delete(importPayment);
		}
	}
	
	/**
	 * 根据多个主键(由，分割)删除多个对象
	 * 
	 * @param importPaymentIds
	 */
	public void _deletes(String importPaymentIds,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(importPaymentIds))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("paymentid"));
		String[] ids = StringUtils.splitString(importPaymentIds);
		for (int i = 0; i < ids.length; i++)
		{
			_delete(ids[i],businessObject);
		}
	}
	/**
	 * 提交工作流
	 * 
	 * @param importPayment
	 */
	public void _submitProcess(ImportPayment importPayment
,Set<ImportPaymentItem> deletedImportPaymentItemSet
,Set<ImportPaymentCbill> deletedImportPaymentCbillSet
,Set<ImportPayBankItem> deletedImportPayBankItemSet
,Set<ImportDocuBankItem> deletedImportDocuBankItemSet
,Set<BillPayReBankItem> deletedBillPayReBankItemSet
,Set<ImportRelatPayment> deletedImportRelatPaymentSet	,BusinessObject businessObject)
	{
		String id = importPayment.getPaymentid();
        /**
		if (StringUtils.isNullBlank(id))
		{
			_save(importPayment);
		}
		else
		{
			_update(importPayment
,deletedImportPaymentItemSet
,deletedImportPaymentCbillSet
,deletedImportPayBankItemSet
,deletedImportDocuBankItemSet
,deletedImportRelatPaymentSet			, businessObject);
		}**/
		if("[object Object]".equals(id)){
			throw new BusinessException("业务ID错误，请联系管理员！");
		}
		String taskId = importPayment.getWorkflowTaskId();
		id = importPayment.getPaymentid();
		if (null == taskId || "".equals(taskId))
			WorkflowService.createAndSignalProcessInstance(importPayment, id);
		else
			WorkflowService.signalProcessInstance(importPayment, id, null);
	}

	/**
	 * 保存或更新进口付款
	 * 保存  
	 *  
	 * @param importPayment
	 */
	public void _update(ImportPayment importPayment
,Set<ImportPaymentItem> deletedImportPaymentItemSet
,Set<ImportPaymentCbill> deletedImportPaymentCbillSet
,Set<ImportPayBankItem> deletedImportPayBankItemSet
,Set<ImportDocuBankItem> deletedImportDocuBankItemSet
,Set<BillPayReBankItem> deletedBillPayReBankItemSet
,Set<ImportRelatPayment> deletedImportRelatPaymentSet,BusinessObject businessObject	
	)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(importPayment);
		importPaymentHibernateDao.saveOrUpdate(importPayment);
// 删除关联子业务对象数据
if(deletedImportPaymentItemSet!=null && deletedImportPaymentItemSet.size()>0)
{
importPaymentItemService._deletes(deletedImportPaymentItemSet,businessObject);
}
// 删除关联子业务对象数据
if(deletedImportPaymentCbillSet!=null && deletedImportPaymentCbillSet.size()>0)
{
importPaymentCbillService._deletes(deletedImportPaymentCbillSet,businessObject);
}
// 删除关联子业务对象数据
if(deletedImportPayBankItemSet!=null && deletedImportPayBankItemSet.size()>0)
{
importPayBankItemService._deletes(deletedImportPayBankItemSet,businessObject);
}
// 删除关联子业务对象数据
if(deletedImportDocuBankItemSet!=null && deletedImportDocuBankItemSet.size()>0)
{
importDocuBankItemService._deletes(deletedImportDocuBankItemSet,businessObject);
}
//删除关联子业务对象数据
if(deletedBillPayReBankItemSet!=null && deletedBillPayReBankItemSet.size()>0)
{
this.billPayReBankItemService._deletes(deletedBillPayReBankItemSet,businessObject);
}
// 删除关联子业务对象数据
if(deletedImportRelatPaymentSet!=null && deletedImportRelatPaymentSet.size()>0)
{
importRelatPaymentService._deletes(deletedImportRelatPaymentSet,businessObject);
}		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(importPayment);
	}
	
	/**
	 * 保存  
	 *   
	 * @param importPayment
	 */
	public void _save(ImportPayment importPayment)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(importPayment);
		// 对id赋空值，使之执行insert操作(适应复制创建场景)
		importPayment.setPaymentid(null);
       
     
     
     
     
     
     
       
     
     
     
     
     
     
       
     
     
     
     
     
     
       
     
     
     
     
     
     
       
     
     
     
     
     
     
       
     
     
     
     
     
     
       
     
     
     
     
     
     
       
     
     
     
     
     
     
       
     
     
     
     
     
     
       
     
     
     
     
     
     
       
     
     
     
     
     
     
       
     
     
     
     
     
     
       
     
     
     
     
     
     
       
     
     
     
     
     
     
       
     
     
     
     
     
     
       
     
     
     
     
     
     
       
     
     
     
     
     
     
       
     
     
     
     
     
     
       
     
     
     
     
     
     
       
     
     
     
     
     
     
       
     
     
     
     
     
     
       
     
     
     
     
     
     
       
     
     
     
     
     
     
       
     
     
     
     
     
     
       
     
     
     
     
     
     
       
     
     
     
     
     
     
       
     
     
     
     
     
     
       
     
     
     
     
     
     
       
     
     
     
     
     
     
       
     
     
     
     
     
     
       
     
     
     
     
     
     
       
     
     
     
     
     
     
       
     
     
     
     
     
     
       
     
     
     
     
     
     
       
     
     
     
     
     
     
       
     
     
     
     
     
     
       
     
     
     
     
     
     
       
     
     
     
     
     
     
       
     
     
     
     
     
     
       
     
     
     
     
     
     
       
     
     
     
     
     
     
       
     
     
     
     
     
     
       
     
     
     
     
     
     
       
     
     
     
     
     
     
       
     
     
     
     
     
     
       
     
     
     
     
     
     
       
     
     
     
     
     
     
       
     
     
     
     
     
     
       
     
     
     
     
     
     
       
     
     
     
     
     
     
       
     
     
     
     
     
     
       
     
     
     
     
     
     
       
     
     
     
     
     
     
       
     
     
     
     
     
     
       
     
     
     
     
     
     
       
     
     
     
     
     
     
       
     
     
     
     
     
     
       
     
     
     
     
     
     
       
     
     
     
     
     
     
       
     
     
     
     
     
     
       
     
     
     
     
     
     
       
     
     
     
     
     
     
       
		Set<ImportPaymentItem> importPaymentItemSet = importPayment.getImportPaymentItem();
		Set<ImportPaymentItem> newImportPaymentItemSet = null;
		if (null != importPaymentItemSet)
		{
			newImportPaymentItemSet = new HashSet();
			Iterator<ImportPaymentItem> itImportPaymentItem = importPaymentItemSet.iterator();
			while (itImportPaymentItem.hasNext())
			{
				ImportPaymentItem importPaymentItem = (ImportPaymentItem) itImportPaymentItem.next();
				importPaymentItem.setPaymentitemid(null);
				newImportPaymentItemSet.add(importPaymentItem);
			}
		}
		importPayment.setImportPaymentItem(newImportPaymentItemSet);
     
     
     
     
     
     
       
     
		Set<ImportPaymentCbill> importPaymentCbillSet = importPayment.getImportPaymentCbill();
		Set<ImportPaymentCbill> newImportPaymentCbillSet = null;
		if (null != importPaymentCbillSet)
		{
			newImportPaymentCbillSet = new HashSet();
			Iterator<ImportPaymentCbill> itImportPaymentCbill = importPaymentCbillSet.iterator();
			while (itImportPaymentCbill.hasNext())
			{
				ImportPaymentCbill importPaymentCbill = (ImportPaymentCbill) itImportPaymentCbill.next();
				importPaymentCbill.setPaymentcbillid(null);
				newImportPaymentCbillSet.add(importPaymentCbill);
			}
		}
		importPayment.setImportPaymentCbill(newImportPaymentCbillSet);
     
     
     
     
     
       
     
     
		Set<ImportPayBankItem> importPayBankItemSet = importPayment.getImportPayBankItem();
		Set<ImportPayBankItem> newImportPayBankItemSet = null;
		if (null != importPayBankItemSet)
		{
			newImportPayBankItemSet = new HashSet();
			Iterator<ImportPayBankItem> itImportPayBankItem = importPayBankItemSet.iterator();
			while (itImportPayBankItem.hasNext())
			{
				ImportPayBankItem importPayBankItem = (ImportPayBankItem) itImportPayBankItem.next();
				importPayBankItem.setPaybankitemid(null);
				newImportPayBankItemSet.add(importPayBankItem);
			}
		}
		importPayment.setImportPayBankItem(newImportPayBankItemSet);
     
     
     
     
       
     
     
     
		Set<ImportDocuBankItem> importDocuBankItemSet = importPayment.getImportDocuBankItem();
		Set<ImportDocuBankItem> newImportDocuBankItemSet = null;
		if (null != importDocuBankItemSet)
		{
			newImportDocuBankItemSet = new HashSet();
			Iterator<ImportDocuBankItem> itImportDocuBankItem = importDocuBankItemSet.iterator();
			while (itImportDocuBankItem.hasNext())
			{
				ImportDocuBankItem importDocuBankItem = (ImportDocuBankItem) itImportDocuBankItem.next();
				importDocuBankItem.setDocuaryitemid(null);
				newImportDocuBankItemSet.add(importDocuBankItem);
			}
		}
		importPayment.setImportDocuBankItem(newImportDocuBankItemSet);
     
     
     
		Set<BillPayReBankItem> billPayReBankItemSet = importPayment.getBillPayReBankItem();
		Set<BillPayReBankItem> newBillPayReBankItemSet = null;
		if (null != billPayReBankItemSet)
		{
			newBillPayReBankItemSet = new HashSet();
			Iterator<BillPayReBankItem> itBillPayReBankItem = billPayReBankItemSet.iterator();
			while (itBillPayReBankItem.hasNext())
			{
				BillPayReBankItem billPayReBankItem = (BillPayReBankItem) itBillPayReBankItem.next();
				billPayReBankItem.setBankitemid(null);
				newBillPayReBankItemSet.add(billPayReBankItem);
			}
		}
		importPayment.setBillPayReBankItem(newBillPayReBankItemSet);
     
     
     
     
         ImportSettSubj importSettSubj = importPayment.getImportSettSubj();
         if (null != importSettSubj)
		 {
		    importSettSubj.setSettlesubjectid(null);
		 }
		 importPayment.setImportSettSubj(importSettSubj);
     
     
       
     
     
     
     
     
         ImportFundFlow importFundFlow = importPayment.getImportFundFlow();
         if (null != importFundFlow)
		 {
		    importFundFlow.setFundflowid(null);
		 }
		 importPayment.setImportFundFlow(importFundFlow);
     
       
     
     
     
     
     
     
		Set<ImportRelatPayment> importRelatPaymentSet = importPayment.getImportRelatPayment();
		Set<ImportRelatPayment> newImportRelatPaymentSet = null;
		if (null != importRelatPaymentSet)
		{
			newImportRelatPaymentSet = new HashSet();
			Iterator<ImportRelatPayment> itImportRelatPayment = importRelatPaymentSet.iterator();
			while (itImportRelatPayment.hasNext())
			{
				ImportRelatPayment importRelatPayment = (ImportRelatPayment) itImportRelatPayment.next();
				importRelatPayment.setRelatedpaymentid(null);
				newImportRelatPaymentSet.add(importRelatPayment);
			}
		}
		importPayment.setImportRelatPayment(newImportRelatPaymentSet);
		importPaymentHibernateDao.saveOrUpdate(importPayment);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(importPayment);
	}
	
	/**
	 *
	 * 保存  
	 *   
	 * @param importPayment
	 */
	public void _saveOrUpdate(ImportPayment importPayment
,Set<ImportPaymentItem> deletedImportPaymentItemSet
,Set<ImportPaymentCbill> deletedImportPaymentCbillSet
,Set<ImportPayBankItem> deletedImportPayBankItemSet
,Set<ImportDocuBankItem> deletedImportDocuBankItemSet
,Set<BillPayReBankItem> deletedBillPayReBankItemSet
,Set<ImportRelatPayment> deletedImportRelatPaymentSet,BusinessObject businessObject		
	)
	{
if (StringUtils.isNullBlank(importPayment.getPaymentno()))
{
String paymentno = NumberService.getNextObjectNumber("importpaymentno", importPayment);
importPayment.setPaymentno(paymentno);
}		if (StringUtils.isNullBlank(importPayment.getPaymentid()))
		{	
			_save(importPayment);
		}
		else
		{
			_update(importPayment
,deletedImportPaymentItemSet
,deletedImportPaymentCbillSet
,deletedImportPayBankItemSet
,deletedImportDocuBankItemSet
,deletedBillPayReBankItemSet
,deletedImportRelatPaymentSet
, businessObject);
}	}

	
	
}