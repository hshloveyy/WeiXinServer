/*
 * @(#)ConsgmtImportSucessfulActionHandler.java
 * 版权声明： 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：张崇镇
 *  时　间：2009-3-24
 *  描　述：创建
 */

package com.infolion.sapss.workflow.payment;

import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.exe.ExecutionContext;

import com.infolion.platform.sys.Constants;
import com.infolion.platform.util.EasyApplicationContextUtils;

/**
 * 
 * <pre>
 * 资金部经理审核（填写开证行，是否需要保证金）
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
public class CreditPayCreateBankCheckActionHandler implements ActionHandler {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2164892978806856009L;

	public void execute(ExecutionContext context) throws Exception {
		Object isPaymentInnerInfo = context.getContextInstance().getVariable(
				"paymentInnerInfo");
		if (isPaymentInnerInfo != null
				&& ((String) isPaymentInnerInfo).length() > 0) {
			String createBank = (String) context.getVariable("createBank");
			String preSecurity = (String) context.getVariable("preSecurity");
			String payMethod = (String) context.getContextInstance()
					.getVariable("payMethod");
			String businessRecordId = (String) context.getContextInstance()
					.getVariable(Constants.WORKFLOW_USER_KEY_VALUE);
			PaymentInnerInfoProcessService paymentInnerInfoProcessService = (PaymentInnerInfoProcessService) EasyApplicationContextUtils
					.getBeanByName("paymentInnerInfoProcessService");
			Double deposit = paymentInnerInfoProcessService.getPrice(context,"deposit");
			paymentInnerInfoProcessService.processCreateBankCheck(
					businessRecordId, createBank, preSecurity, deposit,
					payMethod);
		}
		// context.getProcessInstance().signal();
	}
}