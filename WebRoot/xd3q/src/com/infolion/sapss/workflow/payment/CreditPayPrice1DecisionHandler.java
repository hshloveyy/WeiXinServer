/*
 * @(#)Price1JudgeDecisionHandler.java
 * 版权声明： 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：张崇镇
 *  时　间：2009-2-27
 *  描　述：创建
 */

package com.infolion.sapss.workflow.payment;

import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.node.DecisionHandler;

import com.infolion.platform.util.EasyApplicationContextUtils;
/**
 * 
 * <pre>金额判断</pre>
 *
 * <br>
 * JDK版本:1.5
 *
 * @author 黄登辉
 * @version 1.0
 * @see The author for more details
 * @since 1.0
 */
public class CreditPayPrice1DecisionHandler implements DecisionHandler {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1390235958846800752L;

	public String decide(ExecutionContext executionContext) throws Exception {
		//String currency = (String)executionContext.getContextInstance().getVariable("currency");
		PaymentInnerInfoProcessService paymentInnerInfoProcessService = (PaymentInnerInfoProcessService) EasyApplicationContextUtils
				.getBeanByName("paymentInnerInfoProcessService");
		Double totalValue=paymentInnerInfoProcessService.getPrice(executionContext);
		if(totalValue>3500)
			return "大于3500万CNY";
		else
			return "小于3500万CNY";
	}

}
