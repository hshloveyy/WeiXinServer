/*
 * @(#)BillClearPaymentServiceGen.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年07月07日 17点42分28秒
 *  描　述：创建
 */
package com.infolion.xdss3.billClearGen.service;

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
import com.infolion.xdss3.billClear.dao.BillClearPaymentHibernateDao;
import com.infolion.xdss3.billClear.domain.BillClearItemPay;
import com.infolion.xdss3.billClear.domain.BillClearPayment;
import com.infolion.xdss3.billClear.domain.BillInPayment;
import com.infolion.xdss3.billClear.service.BillClearItemPayService;
import com.infolion.xdss3.billClear.service.BillInPaymentService;
import com.infolion.xdss3.financeSquare.domain.FundFlow;
import com.infolion.xdss3.financeSquare.domain.SettleSubject;
import com.infolion.xdss3.financeSquare.service.FundFlowService;
import com.infolion.xdss3.financeSquare.service.SettleSubjectService;

/**
 * <pre>
 * 票清付款(BillClearPayment)服务类
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
public class BillClearPaymentServiceGen extends BaseService
{
	protected Log log = LogFactory.getFactory().getLog(this.getClass());

	@Autowired
	protected BillClearPaymentHibernateDao billClearPaymentHibernateDao;

	public void setBillClearPaymentHibernateDao(BillClearPaymentHibernateDao billClearPaymentHibernateDao)
	{
		this.billClearPaymentHibernateDao = billClearPaymentHibernateDao;
	}

	/**
	 * 注入扩展服务类
	 */
	@Autowired(required = false)
	public void setAdvanceService(@Qualifier("billClearPaymentAdvanceService") AdvanceService advanceService)
	{
		super.setAdvanceService(advanceService);
	}

	@Autowired
	protected BillClearItemPayService billClearItemPayService;

	public void setBillClearItemPayService(BillClearItemPayService billClearItemPayService)
	{
		this.billClearItemPayService = billClearItemPayService;
	}

	@Autowired
	protected BillInPaymentService billInPaymentService;

	public void setBillInPaymentService(BillInPaymentService billInPaymentService)
	{
		this.billInPaymentService = billInPaymentService;
	}

	@Autowired
	protected FundFlowService fundFlowService;

	public void setFundFlowService(FundFlowService fundFlowService)
	{
		this.fundFlowService = fundFlowService;
	}

	@Autowired
	protected SettleSubjectService settleSubjectService;

	public void setSettleSubjectService(SettleSubjectService settleSubjectService)
	{
		this.settleSubjectService = settleSubjectService;
	}

	/**
	 * 根据主键ID,取得票清付款实例
	 * 
	 * @param id
	 * @return
	 */
	public BillClearPayment _getDetached(String id)
	{
		BillClearPayment billClearPayment = new BillClearPayment();
		if (StringUtils.isNotBlank(id))
		{
			billClearPayment = billClearPaymentHibernateDao.getDetached(id);
		}
		// 需要转移到service服务里，要不存在事务问题。
		String operationType = LockService.lockBOData(billClearPayment);
		if (OperationType.UNVISIABLE.equals(operationType))
		{
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
		}
		billClearPayment.setOperationType(operationType);

		return billClearPayment;
	}

	/**
	 * 根据主键ID,取得票清付款实例
	 * 
	 * @param id
	 * @return
	 */
	public BillClearPayment _get(String id)
	{
		BillClearPayment billClearPayment = new BillClearPayment();
		if (StringUtils.isNotBlank(id))
		{
			billClearPayment = billClearPaymentHibernateDao.get(id);
		}
		// 需要转移到service服务里，要不存在事务问题。
		String operationType = LockService.lockBOData(billClearPayment);
		if (OperationType.UNVISIABLE.equals(operationType))
		{
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
		}
		billClearPayment.setOperationType(operationType);

		return billClearPayment;
	}

	/**
	 * 根据主键ID,取得票清付款实例
	 * 
	 * @param id
	 * @return
	 */
	public BillClearPayment _getForEdit(String id)
	{
		BillClearPayment billClearPayment = _get(id);
		// 需要转移到service服务里，要不存在事务问题。
		String operationType = billClearPayment.getOperationType();
		if (OperationType.READONLY.equals(operationType))
		{
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotEdit);
		}

		return billClearPayment;
	}

	/**
	 * 根据主键ID,取得票清付款实例副本
	 * 
	 * @param id
	 * @return
	 */
	public BillClearPayment _getEntityCopy(String id)
	{
		BillClearPayment billClearPayment = new BillClearPayment();
		BillClearPayment billClearPaymentOld = this._get(id);
		try
		{
			BeanUtils.copyProperties(billClearPayment, billClearPaymentOld);
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (InvocationTargetException e)
		{
			e.printStackTrace();
		}
		billClearPayment.setBillclearno(null);
		billClearPayment.setBillclearid(null);
		billClearPayment.setProcessstate(" ");
		return billClearPayment;
	}

	/**
	 * 删除
	 * 
	 * @param billClearPayment
	 */
	public void _delete(BillClearPayment billClearPayment)
	{
		if (null != advanceService)
			advanceService.preDelete(billClearPayment);

		// 流程状态
		String processState = billClearPayment.getProcessstate();
		if (!StringUtils.isNullBlank(processState))
		{
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.CannotDeleteApproveData);
		}
		LockService.isBoInstanceLocked(billClearPayment, BillClearPayment.class);
		billClearPaymentHibernateDao.remove(billClearPayment);

		if (null != advanceService)
			advanceService.postDelete(billClearPayment);
	}

	/**
	 * 根据主键删除
	 * 
	 * @param billClearPaymentId
	 */
	public void _delete(String billClearPaymentId, BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(billClearPaymentId))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("billclearid"));
		BillClearPayment billClearPayment = this.billClearPaymentHibernateDao.load(billClearPaymentId);
		_delete(billClearPayment);
	}

	/**
	 * 删除
	 * 
	 * @param Set
	 *            <BillClearPayment> billClearPayments
	 */
	public void _deletes(Set<BillClearPayment> billClearPayments, BusinessObject businessObject)
	{
		if (null == billClearPayments || billClearPayments.size() < 1)
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoText());
		Iterator<BillClearPayment> it = billClearPayments.iterator();
		while (it.hasNext())
		{
			BillClearPayment billClearPayment = (BillClearPayment) it.next();
			_delete(billClearPayment);
		}
	}

	/**
	 * 根据多个主键(由，分割)删除多个对象
	 * 
	 * @param billClearPaymentIds
	 */
	public void _deletes(String billClearPaymentIds, BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(billClearPaymentIds))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("billclearid"));
		String[] ids = StringUtils.splitString(billClearPaymentIds);
		for (int i = 0; i < ids.length; i++)
		{
			_delete(ids[i], businessObject);
		}
	}

	/**
	 * 提交工作流
	 * 
	 * @param billClearPayment
	 */
	public void _submitProcess(BillClearPayment billClearPayment, Set<BillClearItemPay> deletedBillClearItemPaySet, Set<BillInPayment> deletedBillInPaymentSet, BusinessObject businessObject)
	{
		String id = billClearPayment.getBillclearid();
		/**
		 * if (StringUtils.isNullBlank(id)) { _save(billClearPayment); } else {
		 * _update(billClearPayment ,deletedBillClearItemPaySet
		 * ,deletedBillInPaymentSet , businessObject); }
		 **/

		String taskId = billClearPayment.getWorkflowTaskId();
		id = billClearPayment.getBillclearid();
		if (null == taskId || "".equals(taskId))
			WorkflowService.createAndSignalProcessInstance(billClearPayment, id);
		else
			WorkflowService.signalProcessInstance(billClearPayment, id, null);
	}

	/**
	 * 保存或更新票清付款 保存
	 * 
	 * @param billClearPayment
	 */
	public void _update(BillClearPayment billClearPayment, Set<BillClearItemPay> deletedBillClearItemPaySet, Set<BillInPayment> deletedBillInPaymentSet, BusinessObject businessObject)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(billClearPayment);
		billClearPaymentHibernateDao.saveOrUpdate(billClearPayment);
		// 删除关联子业务对象数据
		if (deletedBillClearItemPaySet != null && deletedBillClearItemPaySet.size() > 0)
		{
			billClearItemPayService._deletes(deletedBillClearItemPaySet, businessObject);
		}
		// 删除关联子业务对象数据
		if (deletedBillInPaymentSet != null && deletedBillInPaymentSet.size() > 0)
		{
			billInPaymentService._deletes(deletedBillInPaymentSet, businessObject);
		} // 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(billClearPayment);
	}

	/**
	 * 保存
	 * 
	 * @param billClearPayment
	 */
	public void _save(BillClearPayment billClearPayment)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(billClearPayment);
		// 对id赋空值，使之执行insert操作(适应复制创建场景)
		billClearPayment.setBillclearid(null);

		Set<BillClearItemPay> billClearItemPaySet = billClearPayment.getBillClearItemPay();
		Set<BillClearItemPay> newBillClearItemPaySet = null;
		if (null != billClearItemPaySet)
		{
			newBillClearItemPaySet = new HashSet();
			Iterator<BillClearItemPay> itBillClearItemPay = billClearItemPaySet.iterator();
			while (itBillClearItemPay.hasNext())
			{
				BillClearItemPay billClearItemPay = (BillClearItemPay) itBillClearItemPay.next();
				billClearItemPay.setBillclearitemid(null);
				newBillClearItemPaySet.add(billClearItemPay);
			}
		}
		billClearPayment.setBillClearItemPay(newBillClearItemPaySet);

		Set<BillInPayment> billInPaymentSet = billClearPayment.getBillInPayment();
		Set<BillInPayment> newBillInPaymentSet = null;
		if (null != billInPaymentSet)
		{
			newBillInPaymentSet = new HashSet();
			Iterator<BillInPayment> itBillInPayment = billInPaymentSet.iterator();
			while (itBillInPayment.hasNext())
			{
				BillInPayment billInPayment = (BillInPayment) itBillInPayment.next();
				billInPayment.setBillnpaymentid(null);
				newBillInPaymentSet.add(billInPayment);
			}
		}
		billClearPayment.setBillInPayment(newBillInPaymentSet);

		FundFlow fundFlow = billClearPayment.getFundFlow();
		if (null != fundFlow)
		{
			fundFlow.setFundflowid(null);
		}
		billClearPayment.setFundFlow(fundFlow);

		SettleSubject settleSubject = billClearPayment.getSettleSubject();
		if (null != settleSubject)
		{
			settleSubject.setSettlesubjectid(null);
		}
		billClearPayment.setSettleSubject(settleSubject);
		billClearPaymentHibernateDao.saveOrUpdate(billClearPayment);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(billClearPayment);
	}

	/**
	 * 
	 * 保存
	 * 
	 * @param billClearPayment
	 */
	public void _saveOrUpdate(BillClearPayment billClearPayment, Set<BillClearItemPay> deletedBillClearItemPaySet, Set<BillInPayment> deletedBillInPaymentSet, BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(billClearPayment.getBillclearno()))
		{
			String billclearno = NumberService.getNextObjectNumber("billclearnoPayment", billClearPayment);
			billClearPayment.setBillclearno(billclearno);
		}
		if (StringUtils.isNullBlank(billClearPayment.getBillclearid()))
		{
			_save(billClearPayment);
		}
		else
		{
			_update(billClearPayment, deletedBillClearItemPaySet, deletedBillInPaymentSet, businessObject);
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