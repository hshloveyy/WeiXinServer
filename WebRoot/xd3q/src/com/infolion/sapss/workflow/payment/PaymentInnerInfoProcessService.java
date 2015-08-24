/*
 * @(#)SalesProcessService.java
 * 版权声明： 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：张崇镇
 *  时　间：2009-2-20
 *  描　述：创建
 */

package com.infolion.sapss.workflow.payment;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;
import org.jbpm.bytes.ByteArray;
import org.jbpm.graph.exe.ExecutionContext;
import org.springframework.stereotype.Service;

import com.infolion.platform.component.workflow.WorkflowService;
import com.infolion.platform.core.BusinessException;
import com.infolion.platform.core.service.BaseHibernateService;
import com.infolion.platform.util.EasyApplicationContextUtils;
import com.infolion.sapss.payment.service.PaymentInnerInfoHibernateService;
import com.infolion.sapss.workflow.ProcessCallBack;

/**
 * 
 * <pre>
 * 内贸付款流程推进回调服务
 * </pre>
 * 
 * <br>
 * JDK版本:1.5
 * 
 * @author 黄登辉
 * @version 1.0
 * @see The author for more details
 * @since 1.0
 */
@Service
public class PaymentInnerInfoProcessService extends BaseHibernateService {
	/**
	 * 出纳填写付款银行相关信息校验
	 * 
	 * @param businessRecordId
	 * @param processId
	 */
	public void processTellerCheck(String businessRecordId,String payBank, 
			String payAccount,String payer,String payRealTime) {
		String error = "";
		if (StringUtils.isBlank(payBank)) {
			error = "付款银行没有填写!";
		}
		if (StringUtils.isBlank(payAccount)) {
			error += "付款帐号没有填写!";
		}
		if (StringUtils.isBlank(payRealTime)) {
			error += "付款日没有填写!";
		}
		if (StringUtils.isBlank(payer)) {
			error += "付款人没有填写!";
		}
		if (!"".equals(error)) {
			throw new BusinessException(error);
		}
		PaymentInnerInfoHibernateService paymentInnerInfoHibernateService = (PaymentInnerInfoHibernateService) EasyApplicationContextUtils
				.getBeanByName("paymentInnerInfoHibernateService");
		paymentInnerInfoHibernateService.updateWithTellerInfo(businessRecordId, payBank, payAccount, payer, payRealTime);
	}
	
	/**
	 * 资金部经理审核（填写开证行，是否需要保证金）
	 * 
	 * @param businessRecordId
	 * @param processId
	 */
	public void processCreateBankCheck(String businessRecordId,String createBank,String preSecurity,Double deposit,String payMethod) {
		String error = "";
		if ("1".equals(payMethod)&&StringUtils.isBlank(createBank)) {
			error += "开证行没有填写!";
		}
		if (("7".equals(payMethod)||"1".equals(payMethod))&&StringUtils.isBlank(preSecurity)) {
			error += "是否需要保证金没有选择!";
		}
		if ("1".equals(preSecurity)&&
				(deposit==null||deposit.isNaN()||deposit.equals(0.0))) {
			error += "保证金金额没有填写!";
		}
		if (!"".equals(error)) {
			throw new BusinessException(error);
		}
		PaymentInnerInfoHibernateService paymentInnerInfoHibernateService = (PaymentInnerInfoHibernateService) EasyApplicationContextUtils
				.getBeanByName("paymentInnerInfoHibernateService");
		paymentInnerInfoHibernateService.updateWithCreateBank(businessRecordId, createBank, preSecurity,deposit);
	}
	
	/**
	 * 子流程资金部到期日期校验
	 * 
	 * @param businessRecordId
	 * @param processId
	 */
	public void subProcessMaturityDate(String businessRecordId,String maturityDate) {
		String error = "";
		if (StringUtils.isBlank(maturityDate)) {
			error = "到期日期没有填写!";
		}
		if (!"".equals(error)) {
			throw new BusinessException(error);
		}
		PaymentInnerInfoHibernateService paymentInnerInfoHibernateService = (PaymentInnerInfoHibernateService) EasyApplicationContextUtils
				.getBeanByName("paymentInnerInfoHibernateService");
		paymentInnerInfoHibernateService.updateSuccessfulRecord(
				businessRecordId, ProcessCallBack.EXAMINE_SUCCESSFUL,
				maturityDate);
	}
	/**
	 * 流程通过
	 * 
	 * @param businessRecordId
	 * @param processId
	 */
	public void processSuccessful(String businessRecordId, long processId,
			String maturityDate) {
		String error = "";
		if (StringUtils.isBlank(maturityDate)) {
			error += "到期日期没有填写!";
		}
		if (!"".equals(error)) {
			throw new BusinessException(error);
		}
		WorkflowService.updateProcessInstanceEndState(processId, "更新状态为办理完成");
		PaymentInnerInfoHibernateService paymentInnerInfoHibernateService = (PaymentInnerInfoHibernateService) EasyApplicationContextUtils
				.getBeanByName("paymentInnerInfoHibernateService");
		paymentInnerInfoHibernateService.updateSuccessfulRecord(
				businessRecordId, ProcessCallBack.EXAMINE_SUCCESSFUL,
				maturityDate);
		// shipHibernateService.updateBusinessRecord(businessRecordId,
		// ProcessCallBack.EXAMINE_SUCCESSFUL, null);
	}

	/**
	 * 申请未通过
	 * 
	 * @param businessRecordId
	 * @param processId
	 */
	public void processFailure(String businessRecordId, long processId) {
		WorkflowService.updateProcessInstanceEndState(processId, "更新状态为不通过");
		PaymentInnerInfoHibernateService paymentInnerInfoHibernateService = (PaymentInnerInfoHibernateService) EasyApplicationContextUtils
				.getBeanByName("paymentInnerInfoHibernateService");
		paymentInnerInfoHibernateService.updateBusinessRecord(businessRecordId,
				ProcessCallBack.EXAMINE_FAILE, null);
	}

	public Double getPrice(ExecutionContext executionContext) {
		return getPrice(executionContext,null);
	}
	
	public Double getPrice(ExecutionContext executionContext,String variableName) {
		Object value = new Object();
		if(variableName==null||variableName.length()<1)
			value = executionContext.getVariable("_workflow_total_value");
		else
			value = executionContext.getVariable(variableName);
		Double totalValue = 0.0;
		if (value == null)
			return totalValue;
		if (value instanceof ByteArray) {
			ByteArray byteArr = (ByteArray) value;
			ObjectInputStream ois = null;
			try {
				ois = new ObjectInputStream(new ByteArrayInputStream(byteArr
						.getBytes()));
				value = ois.readObject();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		if (value instanceof String) {
			value = ("".equals(value)) ? "0" : value;
			totalValue = Double.parseDouble(value.toString());
		} else if (value instanceof Double) {
			totalValue = (Double) value;
			totalValue = (totalValue == null) ? 0.0 : totalValue;
		} else if (value instanceof BigDecimal){
			totalValue = ((BigDecimal) value).doubleValue();
		}
		return totalValue;
	}
}
