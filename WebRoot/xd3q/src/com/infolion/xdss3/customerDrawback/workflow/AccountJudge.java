package com.infolion.xdss3.customerDrawback.workflow;

import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.node.DecisionHandler;

import com.infolion.platform.dpframework.core.Constants;
import com.infolion.platform.dpframework.util.EasyApplicationContextUtils;
import com.infolion.xdss3.customerDrawback.domain.CustomerDbBankItem;
import com.infolion.xdss3.customerDrawback.domain.CustomerRefundItem;
import com.infolion.xdss3.customerDrawback.domain.CustomerRefundment;
import com.infolion.xdss3.customerDrawback.service.CustomerRefundmentService;

public class AccountJudge implements DecisionHandler{
	
	public String decide(ExecutionContext context) throws Exception {
		String businessId = (String)context.getContextInstance().getVariable(Constants.WORKFLOW_USER_KEY_VALUE);
		CustomerRefundmentService customerRefundmentService = (CustomerRefundmentService)EasyApplicationContextUtils.getBeanByName("customerRefundmentService");
		CustomerRefundment customerRefundment = customerRefundmentService._get(businessId);
		String strRet = "";
		CustomerDbBankItem customerDbBankItem = new CustomerDbBankItem();
		// 获取第一条"客户退款银行"
		if(customerRefundment.getCustomerDbBankItem().iterator().hasNext()){
			customerDbBankItem = customerRefundment.getCustomerDbBankItem().iterator().next();
		}
		if("310066399018010033466".equals(customerDbBankItem.getRefundbankacount())){
			strRet = "上海信达诺账号";		 
		}else{
			strRet = "普通账号";			
		}
		return strRet;
	}
}
