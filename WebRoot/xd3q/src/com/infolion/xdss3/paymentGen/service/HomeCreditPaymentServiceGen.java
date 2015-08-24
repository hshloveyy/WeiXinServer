/*
 * @(#)HomeCreditPaymentServiceGen.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2013年11月19日 15点22分20秒
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
import java.math.BigDecimal;

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
import com.infolion.xdss3.payment.domain.HomeCreditPayment;
import com.infolion.xdss3.payment.service.HomeCreditPaymentService;
import com.infolion.xdss3.payment.dao.HomeCreditPaymentHibernateDao;
import com.infolion.xdss3.payment.dao.PaymentCurrencyJdbcDao;
          
import com.infolion.xdss3.payment.domain.HomeCreditPayItem;
import com.infolion.xdss3.payment.service.HomeCreditPayItemService;
          
import com.infolion.xdss3.payment.domain.HomeCreditPayCbill;
import com.infolion.xdss3.payment.service.HomeCreditPayCbillService;
          
import com.infolion.xdss3.payment.domain.HomeCreditBankItem;
import com.infolion.xdss3.payment.service.HomeCreditBankItemService;
          
import com.infolion.xdss3.payment.domain.HomeCreditDocuBank;
import com.infolion.xdss3.payment.service.HomeCreditDocuBankService;
          
import com.infolion.xdss3.financeSquare.domain.SettleSubject;
import com.infolion.xdss3.financeSquare.service.SettleSubjectService;
          
import com.infolion.xdss3.financeSquare.domain.FundFlow;
import com.infolion.xdss3.financeSquare.service.FundFlowService;
          
import com.infolion.platform.dpframework.uicomponent.attachement.domain.Attachement;
import com.infolion.platform.dpframework.uicomponent.attachement.service.AttachementService;
import com.infolion.platform.dpframework.uicomponent.attachement.dao.AttachementJdbcDao;
          
import com.infolion.xdss3.payment.domain.HomeCreditRebank;
import com.infolion.xdss3.payment.service.HomeCreditRebankService;
          
import com.infolion.xdss3.payment.domain.HomeCreditRelatPay;
import com.infolion.xdss3.payment.service.HomeCreditRelatPayService;

/**
 * <pre>
 * 国内信用证(HomeCreditPayment)服务类
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
public class HomeCreditPaymentServiceGen extends BaseService
{
	protected Log log = LogFactory.getFactory().getLog(this.getClass());

	@Autowired
	protected HomeCreditPaymentHibernateDao homeCreditPaymentHibernateDao;
	
	public void setHomeCreditPaymentHibernateDao(HomeCreditPaymentHibernateDao homeCreditPaymentHibernateDao)
	{
		this.homeCreditPaymentHibernateDao = homeCreditPaymentHibernateDao;
	}
	
     /**
	 * 注入扩展服务类
	 */
	 @Autowired(required = false)
	 public void setAdvanceService(@Qualifier("homeCreditPaymentAdvanceService") AdvanceService advanceService)
	 {
	  	super.setAdvanceService(advanceService);
	 }   
	 
	 @Autowired
		private PaymentCurrencyJdbcDao paymentCurrencyJdbcDao;
	 

	@Autowired
	protected HomeCreditPayItemService homeCreditPayItemService;
	
	public void setHomeCreditPayItemService(HomeCreditPayItemService homeCreditPayItemService)
	{
		this.homeCreditPayItemService = homeCreditPayItemService;
	}
          

	@Autowired
	protected HomeCreditPayCbillService homeCreditPayCbillService;
	
	public void setHomeCreditPayCbillService(HomeCreditPayCbillService homeCreditPayCbillService)
	{
		this.homeCreditPayCbillService = homeCreditPayCbillService;
	}
          

	@Autowired
	protected HomeCreditBankItemService homeCreditBankItemService;
	
	public void setHomeCreditBankItemService(HomeCreditBankItemService homeCreditBankItemService)
	{
		this.homeCreditBankItemService = homeCreditBankItemService;
	}
          

	@Autowired
	protected HomeCreditDocuBankService homeCreditDocuBankService;
	
	public void setHomeCreditDocuBankService(HomeCreditDocuBankService homeCreditDocuBankService)
	{
		this.homeCreditDocuBankService = homeCreditDocuBankService;
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
          
          

	@Autowired
	protected HomeCreditRebankService homeCreditRebankService;
	
	public void setHomeCreditRebankService(HomeCreditRebankService homeCreditRebankService)
	{
		this.homeCreditRebankService = homeCreditRebankService;
	}
          

	@Autowired
	protected HomeCreditRelatPayService homeCreditRelatPayService;
	
	public void setHomeCreditRelatPayService(HomeCreditRelatPayService homeCreditRelatPayService)
	{
		this.homeCreditRelatPayService = homeCreditRelatPayService;
	}

          
          
          
          
          
          
          

	@Autowired
	protected AttachementService attachementService;
	
	public void setAttachementService(AttachementService attachementService)
	{
		this.attachementService = attachementService;
	}

	@Autowired
	protected AttachementJdbcDao attachementJdbcDao;
	
	public void setAttachementJdbcDao(AttachementJdbcDao attachementJdbcDao)
	{
		this.attachementJdbcDao = attachementJdbcDao;
	}

	   
	/**
	 * 根据主键ID,取得国内信用证实例
	 * @param id
	 * @return
	 */
	public HomeCreditPayment _getDetached(String id)
	{		
	    HomeCreditPayment homeCreditPayment = new HomeCreditPayment();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  homeCreditPayment = homeCreditPaymentHibernateDao.getDetached(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(homeCreditPayment);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    homeCreditPayment.setOperationType(operationType);
	    
		return homeCreditPayment;	
	}
	
	/**
	 * 根据主键ID,取得国内信用证实例
	 * @param id
	 * @return
	 */
	public HomeCreditPayment _get(String id)
	{		
	    HomeCreditPayment homeCreditPayment = new HomeCreditPayment();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  homeCreditPayment = homeCreditPaymentHibernateDao.get(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(homeCreditPayment);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    homeCreditPayment.setOperationType(operationType);
	    
		return homeCreditPayment;	
	}
	
	/**
	 * 根据主键ID,取得国内信用证实例
	 * @param id
	 * @return
	 */
	public HomeCreditPayment _getForEdit(String id)
	{		
	    HomeCreditPayment homeCreditPayment = _get(id);
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = homeCreditPayment.getOperationType();
	    if(OperationType.READONLY.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotEdit);
	    }

		return homeCreditPayment;	
	}
	
	/**
	 * 根据主键ID,取得国内信用证实例副本
	 * @param id
	 * @return
	 */
	public HomeCreditPayment _getEntityCopy(String id)
	{		
	    HomeCreditPayment homeCreditPayment = new HomeCreditPayment();
		HomeCreditPayment homeCreditPaymentOld = this._get(id);
		try
		{
			BeanUtils.copyProperties(homeCreditPayment, homeCreditPaymentOld);
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (InvocationTargetException e)
		{
			e.printStackTrace();
		}
		//homeCreditPayment.setPaymentid(null); 
		homeCreditPayment.setProcessstate(" ");
		return homeCreditPayment;	
	}
	
	/**
	 * 删除  
	 *   
	 * @param homeCreditPayment
	 */
	public void _delete(HomeCreditPayment homeCreditPayment)
	{
		if (null != advanceService)
			advanceService.preDelete(homeCreditPayment);
	
		//流程状态
		String processState =homeCreditPayment.getProcessstate();
		if(!StringUtils.isNullBlank(processState))
		{
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.CannotDeleteApproveData);
		}
 		LockService.isBoInstanceLocked(homeCreditPayment,HomeCreditPayment.class);
		homeCreditPaymentHibernateDao.remove(homeCreditPayment);
//删除业务附件
String paymentid = homeCreditPayment.getPaymentid();
attachementService.deleteByBusinessId(paymentid);
		if (null != advanceService)
			advanceService.postDelete(homeCreditPayment);
	}

	/**
	 * 根据主键删除
	 * 
	 * @param homeCreditPaymentId
	 */
	public void _delete(String homeCreditPaymentId,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(homeCreditPaymentId))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("paymentid"));
		HomeCreditPayment homeCreditPayment = this.homeCreditPaymentHibernateDao.load(homeCreditPaymentId);
		_delete(homeCreditPayment);
	}
	
	/**
	 * 删除  
	 *   
	 * @param Set<HomeCreditPayment> homeCreditPayments
	 */
	public void _deletes(Set<HomeCreditPayment> homeCreditPayments,BusinessObject businessObject)
	{
		if (null == homeCreditPayments || homeCreditPayments.size() < 1)
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoText());
		Iterator<HomeCreditPayment> it = homeCreditPayments.iterator();
		while (it.hasNext())
		{
			HomeCreditPayment homeCreditPayment = (HomeCreditPayment) it.next();
			_delete(homeCreditPayment);
		}
	}
	
	/**
	 * 根据多个主键(由，分割)删除多个对象
	 * 
	 * @param homeCreditPaymentIds
	 */
	public void _deletes(String homeCreditPaymentIds,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(homeCreditPaymentIds))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("paymentid"));
		String[] ids = StringUtils.splitString(homeCreditPaymentIds);
		for (int i = 0; i < ids.length; i++)
		{
			_delete(ids[i],businessObject);
		}
	}
	/**
	 * 提交工作流
	 * 
	 * @param homeCreditPayment
	 */
	public void _submitProcess(HomeCreditPayment homeCreditPayment
,Set<HomeCreditPayItem> deletedHomeCreditPayItemSet
,Set<HomeCreditPayCbill> deletedHomeCreditPayCbillSet
,Set<HomeCreditBankItem> deletedHomeCreditBankItemSet
,Set<HomeCreditDocuBank> deletedHomeCreditDocuBankSet
,Set<HomeCreditRebank> deletedHomeCreditRebankSet
,Set<HomeCreditRelatPay> deletedHomeCreditRelatPaySet	,BusinessObject businessObject)
	{
		String id = homeCreditPayment.getPaymentid();
        /**
		if (StringUtils.isNullBlank(id))
		{
			_save(homeCreditPayment);
		}
		else
		{
			_update(homeCreditPayment
,deletedHomeCreditPayItemSet
,deletedHomeCreditPayCbillSet
,deletedHomeCreditBankItemSet
,deletedHomeCreditDocuBankSet
,deletedHomeCreditRebankSet
,deletedHomeCreditRelatPaySet			, businessObject);
		}**/
		
		String taskId = homeCreditPayment.getWorkflowTaskId();
		id = homeCreditPayment.getPaymentid();
		if (null == taskId || "".equals(taskId))
			WorkflowService.createAndSignalProcessInstance(homeCreditPayment, id);
		else
			WorkflowService.signalProcessInstance(homeCreditPayment, id, null);
	}

	/**
	 * 保存或更新国内信用证
	 * 保存  
	 *  
	 * @param homeCreditPayment
	 */
	public void _update(HomeCreditPayment homeCreditPayment
,Set<HomeCreditPayItem> deletedHomeCreditPayItemSet
,Set<HomeCreditPayCbill> deletedHomeCreditPayCbillSet
,Set<HomeCreditBankItem> deletedHomeCreditBankItemSet
,Set<HomeCreditDocuBank> deletedHomeCreditDocuBankSet
,Set<HomeCreditRebank> deletedHomeCreditRebankSet
,Set<HomeCreditRelatPay> deletedHomeCreditRelatPaySet,BusinessObject businessObject	
	)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(homeCreditPayment);
		homeCreditPaymentHibernateDao.saveOrUpdate(homeCreditPayment);
// 删除关联子业务对象数据
if(deletedHomeCreditPayItemSet!=null && deletedHomeCreditPayItemSet.size()>0)
{
homeCreditPayItemService._deletes(deletedHomeCreditPayItemSet,businessObject);
}
// 删除关联子业务对象数据
if(deletedHomeCreditPayCbillSet!=null && deletedHomeCreditPayCbillSet.size()>0)
{
homeCreditPayCbillService._deletes(deletedHomeCreditPayCbillSet,businessObject);
}
// 删除关联子业务对象数据
if(deletedHomeCreditBankItemSet!=null && deletedHomeCreditBankItemSet.size()>0)
{
homeCreditBankItemService._deletes(deletedHomeCreditBankItemSet,businessObject);
}
// 删除关联子业务对象数据
if(deletedHomeCreditDocuBankSet!=null && deletedHomeCreditDocuBankSet.size()>0)
{
homeCreditDocuBankService._deletes(deletedHomeCreditDocuBankSet,businessObject);
}
// 删除关联子业务对象数据
if(deletedHomeCreditRebankSet!=null && deletedHomeCreditRebankSet.size()>0)
{
homeCreditRebankService._deletes(deletedHomeCreditRebankSet,businessObject);
}
// 删除关联子业务对象数据
if(deletedHomeCreditRelatPaySet!=null && deletedHomeCreditRelatPaySet.size()>0)
{
homeCreditRelatPayService._deletes(deletedHomeCreditRelatPaySet,businessObject);
}		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(homeCreditPayment);
	}
	
	/**
	 * 保存  
	 *   
	 * @param homeCreditPayment
	 */
	public void _save(HomeCreditPayment homeCreditPayment)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(homeCreditPayment);
		// 对id赋空值，使之执行insert操作(适应复制创建场景)
		homeCreditPayment.setPaymentid(null);
       
     
     
     
     
     
     
     
     
       
     
     
     
     
     
     
     
     
       
     
     
     
     
     
     
     
     
       
     
     
     
     
     
     
     
     
       
     
     
     
     
     
     
     
     
       
     
     
     
     
     
     
     
     
       
     
     
     
     
     
     
     
     
       
     
     
     
     
     
     
     
     
       
     
     
     
     
     
     
     
     
       
     
     
     
     
     
     
     
     
       
     
     
     
     
     
     
     
     
       
     
     
     
     
     
     
     
     
       
     
     
     
     
     
     
     
     
       
     
     
     
     
     
     
     
     
       
     
     
     
     
     
     
     
     
       
     
     
     
     
     
     
     
     
       
     
     
     
     
     
     
     
     
       
     
     
     
     
     
     
     
     
       
     
     
     
     
     
     
     
     
       
     
     
     
     
     
     
     
     
       
     
     
     
     
     
     
     
     
       
     
     
     
     
     
     
     
     
       
     
     
     
     
     
     
     
     
       
     
     
     
     
     
     
     
     
       
     
     
     
     
     
     
     
     
       
     
     
     
     
     
     
     
     
       
     
     
     
     
     
     
     
     
       
     
     
     
     
     
     
     
     
       
     
     
     
     
     
     
     
     
       
     
     
     
     
     
     
     
     
       
     
     
     
     
     
     
     
     
       
     
     
     
     
     
     
     
     
       
     
     
     
     
     
     
     
     
       
     
     
     
     
     
     
     
     
       
     
     
     
     
     
     
     
     
       
     
     
     
     
     
     
     
     
       
     
     
     
     
     
     
     
     
       
     
     
     
     
     
     
     
     
       
     
     
     
     
     
     
     
     
       
     
     
     
     
     
     
     
     
       
     
     
     
     
     
     
     
     
       
     
     
     
     
     
     
     
     
       
     
     
     
     
     
     
     
     
       
     
     
     
     
     
     
     
     
       
     
     
     
     
     
     
     
     
       
     
     
     
     
     
     
     
     
       
     
     
     
     
     
     
     
     
       
     
     
     
     
     
     
     
     
       
     
     
     
     
     
     
     
     
       
     
     
     
     
     
     
     
     
       
     
     
     
     
     
     
     
     
       
     
     
     
     
     
     
     
     
       
     
     
     
     
     
     
     
     
       
     
     
     
     
     
     
     
     
       
     
     
     
     
     
     
     
     
       
     
     
     
     
     
     
     
     
       
     
     
     
     
     
     
     
     
       
     
     
     
     
     
     
     
     
       
     
     
     
     
     
     
     
     
       
     
     
     
     
     
     
     
     
       
     
     
     
     
     
     
     
     
       
     
     
     
     
     
     
     
     
       
     
     
     
     
     
     
     
     
       
		Set<HomeCreditPayItem> homeCreditPayItemSet = homeCreditPayment.getHomeCreditPayItem();
		Set<HomeCreditPayItem> newHomeCreditPayItemSet = null;
		if (null != homeCreditPayItemSet)
		{
			newHomeCreditPayItemSet = new HashSet();
			Iterator<HomeCreditPayItem> itHomeCreditPayItem = homeCreditPayItemSet.iterator();
			while (itHomeCreditPayItem.hasNext())
			{
				HomeCreditPayItem homeCreditPayItem = (HomeCreditPayItem) itHomeCreditPayItem.next();
				homeCreditPayItem.setPaymentitemid(null);
				newHomeCreditPayItemSet.add(homeCreditPayItem);
			}
		}
		homeCreditPayment.setHomeCreditPayItem(newHomeCreditPayItemSet);
     
     
     
     
     
     
     
     
       
     
		Set<HomeCreditPayCbill> homeCreditPayCbillSet = homeCreditPayment.getHomeCreditPayCbill();
		Set<HomeCreditPayCbill> newHomeCreditPayCbillSet = null;
		if (null != homeCreditPayCbillSet)
		{
			newHomeCreditPayCbillSet = new HashSet();
			Iterator<HomeCreditPayCbill> itHomeCreditPayCbill = homeCreditPayCbillSet.iterator();
			while (itHomeCreditPayCbill.hasNext())
			{
				HomeCreditPayCbill homeCreditPayCbill = (HomeCreditPayCbill) itHomeCreditPayCbill.next();
				homeCreditPayCbill.setPaymentcbillid(null);
				newHomeCreditPayCbillSet.add(homeCreditPayCbill);
			}
		}
		homeCreditPayment.setHomeCreditPayCbill(newHomeCreditPayCbillSet);
     
     
     
     
     
     
     
       
     
     
		Set<HomeCreditBankItem> homeCreditBankItemSet = homeCreditPayment.getHomeCreditBankItem();
		Set<HomeCreditBankItem> newHomeCreditBankItemSet = null;
		if (null != homeCreditBankItemSet)
		{
			newHomeCreditBankItemSet = new HashSet();
			Iterator<HomeCreditBankItem> itHomeCreditBankItem = homeCreditBankItemSet.iterator();
			while (itHomeCreditBankItem.hasNext())
			{
				HomeCreditBankItem homeCreditBankItem = (HomeCreditBankItem) itHomeCreditBankItem.next();
				homeCreditBankItem.setPaybankitemid(null);
				newHomeCreditBankItemSet.add(homeCreditBankItem);
			}
		}
		homeCreditPayment.setHomeCreditBankItem(newHomeCreditBankItemSet);
     
     
     
     
     
     
       
     
     
     
		Set<HomeCreditDocuBank> homeCreditDocuBankSet = homeCreditPayment.getHomeCreditDocuBank();
		Set<HomeCreditDocuBank> newHomeCreditDocuBankSet = null;
		if (null != homeCreditDocuBankSet)
		{
			newHomeCreditDocuBankSet = new HashSet();
			Iterator<HomeCreditDocuBank> itHomeCreditDocuBank = homeCreditDocuBankSet.iterator();
			while (itHomeCreditDocuBank.hasNext())
			{
				HomeCreditDocuBank homeCreditDocuBank = (HomeCreditDocuBank) itHomeCreditDocuBank.next();
				homeCreditDocuBank.setDocuaryitemid(null);
				newHomeCreditDocuBankSet.add(homeCreditDocuBank);
			}
		}
		homeCreditPayment.setHomeCreditDocuBank(newHomeCreditDocuBankSet);
     
     
     
     
     
       
     
     
     
     
         SettleSubject settleSubject = homeCreditPayment.getSettleSubject();
         if (null != settleSubject)
		 {
		    settleSubject.setSettlesubjectid(null);
		 }
		 homeCreditPayment.setSettleSubject(settleSubject);
     
     
     
     
       
     
     
     
     
     
         FundFlow fundFlow = homeCreditPayment.getFundFlow();
         if (null != fundFlow)
		 {
		    fundFlow.setFundflowid(null);
		 }
		 homeCreditPayment.setFundFlow(fundFlow);
     
     
     
       
     
     
     
     
     
     
     
     
       
     
     
     
     
     
     
     
		Set<HomeCreditRebank> homeCreditRebankSet = homeCreditPayment.getHomeCreditRebank();
		Set<HomeCreditRebank> newHomeCreditRebankSet = null;
		if (null != homeCreditRebankSet)
		{
			newHomeCreditRebankSet = new HashSet();
			Iterator<HomeCreditRebank> itHomeCreditRebank = homeCreditRebankSet.iterator();
			while (itHomeCreditRebank.hasNext())
			{
				HomeCreditRebank homeCreditRebank = (HomeCreditRebank) itHomeCreditRebank.next();
				homeCreditRebank.setBankitemid(null);
				newHomeCreditRebankSet.add(homeCreditRebank);
			}
		}
		homeCreditPayment.setHomeCreditRebank(newHomeCreditRebankSet);
     
       
     
     
     
     
     
     
     
     
		Set<HomeCreditRelatPay> homeCreditRelatPaySet = homeCreditPayment.getHomeCreditRelatPay();
		Set<HomeCreditRelatPay> newHomeCreditRelatPaySet = null;
		if (null != homeCreditRelatPaySet)
		{
			newHomeCreditRelatPaySet = new HashSet();
			Iterator<HomeCreditRelatPay> itHomeCreditRelatPay = homeCreditRelatPaySet.iterator();
			while (itHomeCreditRelatPay.hasNext())
			{
				HomeCreditRelatPay homeCreditRelatPay = (HomeCreditRelatPay) itHomeCreditRelatPay.next();
				homeCreditRelatPay.setRelatedpaymentid(null);
				newHomeCreditRelatPaySet.add(homeCreditRelatPay);
			}
		}
		homeCreditPayment.setHomeCreditRelatPay(newHomeCreditRelatPaySet);
       
     
     
     
     
     
     
     
     
		homeCreditPaymentHibernateDao.saveOrUpdate(homeCreditPayment);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(homeCreditPayment);
	}
	
	/**
	 *
	 * 保存  
	 *   
	 * @param homeCreditPayment
	 */
	public void _saveOrUpdate(HomeCreditPayment homeCreditPayment
,Set<HomeCreditPayItem> deletedHomeCreditPayItemSet
,Set<HomeCreditPayCbill> deletedHomeCreditPayCbillSet
,Set<HomeCreditBankItem> deletedHomeCreditBankItemSet
,Set<HomeCreditDocuBank> deletedHomeCreditDocuBankSet
,Set<HomeCreditRebank> deletedHomeCreditRebankSet
,Set<HomeCreditRelatPay> deletedHomeCreditRelatPaySet
//取得业务附件，业务ID
,Set<Attachement> attachements,BusinessObject businessObject		
	)
	{
		if (StringUtils.isNullBlank(homeCreditPayment.getPaymentno())) {
			String paymentno = NumberService.getNextObjectNumber(
					"HomeCreditPaymentNo", homeCreditPayment);
			homeCreditPayment.setPaymentno(paymentno);
		}

		// 判断CURRENCY为"CNY"时直接将APPLYAMOUNT的值存到这个字段，为其他币别时APPLYAMOUNT*CLOSEEXCHANGERAT的值存入。
		List<String> currencyCodes = this.getPaymentCurrencyJdbcDao()
				.getAllCurrencyCode();
		if (!"CNY".equals(homeCreditPayment.getCurrency())) {
			BigDecimal applyAmount = homeCreditPayment.getApplyamount();
			BigDecimal closeExchangeGreat = homeCreditPayment.getCloseexchangerat();
			BigDecimal result = applyAmount.multiply(closeExchangeGreat);
			homeCreditPayment.setConvertamount(result);
		} else {
			homeCreditPayment.setConvertamount(homeCreditPayment.getApplyamount());
		}
		
		if (StringUtils.isNullBlank(homeCreditPayment.getPaymentid()))
		{	
			_save(homeCreditPayment);
		}
		else
		{
			_update(homeCreditPayment
,deletedHomeCreditPayItemSet
,deletedHomeCreditPayCbillSet
,deletedHomeCreditBankItemSet
,deletedHomeCreditDocuBankSet
,deletedHomeCreditRebankSet
,deletedHomeCreditRelatPaySet
, businessObject);
}
//保存附件业务ID
String paymentid = homeCreditPayment.getPaymentid();
attachementJdbcDao.update(attachements,paymentid);	}
	
	
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
	 * @param paymentCurrencyJdbcDao the paymentCurrencyJdbcDao to set
	 */
	public void setPaymentCurrencyJdbcDao(
			PaymentCurrencyJdbcDao paymentCurrencyJdbcDao) {
		this.paymentCurrencyJdbcDao = paymentCurrencyJdbcDao;
	}

	/**
	 * @return the paymentCurrencyJdbcDao
	 */
	public PaymentCurrencyJdbcDao getPaymentCurrencyJdbcDao() {
		return paymentCurrencyJdbcDao;
	}
}