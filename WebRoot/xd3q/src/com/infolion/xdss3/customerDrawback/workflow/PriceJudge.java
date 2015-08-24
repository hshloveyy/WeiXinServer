package com.infolion.xdss3.customerDrawback.workflow;

import java.math.BigDecimal;

import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.node.DecisionHandler;

import com.infolion.platform.dpframework.core.Constants;
import com.infolion.platform.dpframework.util.EasyApplicationContextUtils;
import com.infolion.xdss3.customerDrawback.domain.CustomerRefundItem;
import com.infolion.xdss3.customerDrawback.domain.CustomerRefundment;
import com.infolion.xdss3.customerDrawback.service.CustomerRefundmentService;

public class PriceJudge implements DecisionHandler{
	
	public String decide(ExecutionContext context) throws Exception {
		String businessId = (String)context.getContextInstance().getVariable(Constants.WORKFLOW_USER_KEY_VALUE);
		CustomerRefundmentService customerRefundmentService = (CustomerRefundmentService)EasyApplicationContextUtils.getBeanByName("customerRefundmentService");
		CustomerRefundment customerRefundment = customerRefundmentService._get(businessId);
		String strRet = "";
		BigDecimal totalRefundValue = new BigDecimal(0);		// 退款行项的退款金额（本位币）总和
		BigDecimal compRefundAmount = new BigDecimal(700000);
		for(CustomerRefundItem customerRefundItem: customerRefundment.getCustomerRefundItem()){
			totalRefundValue = totalRefundValue.add(customerRefundItem.getRefundmentvalue());
		}
		// 若总退款金额大于70万
		if(totalRefundValue.compareTo(compRefundAmount) == 1){
			strRet = "金额大于10万USD";
		}else{
			strRet = "金额小于10万USD";
		}
		return strRet;
	}
}
