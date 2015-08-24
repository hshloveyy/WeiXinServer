package com.infolion.xdss3.customerDrawback.workflow;

import java.math.BigDecimal;

import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.node.DecisionHandler;

import com.infolion.platform.dpframework.core.Constants;
import com.infolion.platform.dpframework.util.EasyApplicationContextUtils;
import com.infolion.xdss3.customerDrawback.domain.CustomerDbBankItem;
import com.infolion.xdss3.customerDrawback.domain.CustomerRefundItem;
import com.infolion.xdss3.customerDrawback.domain.CustomerRefundment;
import com.infolion.xdss3.customerDrawback.service.CustomerRefundmentService;

public class PriceJudge3 implements DecisionHandler{
	
	public String decide(ExecutionContext context) throws Exception {
		String businessId = (String)context.getContextInstance().getVariable(Constants.WORKFLOW_USER_KEY_VALUE);
		CustomerRefundmentService customerRefundmentService = (CustomerRefundmentService)EasyApplicationContextUtils.getBeanByName("customerRefundmentService");
		CustomerRefundment customerRefundment = customerRefundmentService._get(businessId);
		String strRet = "";
		BigDecimal totalRefundValue = new BigDecimal(0);		// 退款行项的退款金额（本位币）总和
		BigDecimal compRefundAmount = new BigDecimal(5000000);	// 500万 CNY
		for(CustomerRefundItem customerRefundItem: customerRefundment.getCustomerRefundItem()){
			totalRefundValue = totalRefundValue.add(customerRefundItem.getRefundmentvalue());
		}
		// 若总退款金额大于500万CNY
		if(totalRefundValue.compareTo(compRefundAmount) == 1){
			strRet = "金额大于500万CNY";
		}else{
			strRet = "金额小于500万CNY";
		}
		return strRet;
	}
}
