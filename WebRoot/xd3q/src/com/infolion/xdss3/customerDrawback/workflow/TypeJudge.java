package com.infolion.xdss3.customerDrawback.workflow;

import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.node.DecisionHandler;

import com.infolion.platform.dpframework.core.Constants;
import com.infolion.platform.dpframework.util.EasyApplicationContextUtils;
import com.infolion.xdss3.customerDrawback.domain.CustomerDbBankItem;
import com.infolion.xdss3.customerDrawback.domain.CustomerRefundItem;
import com.infolion.xdss3.customerDrawback.domain.CustomerRefundment;
import com.infolion.xdss3.customerDrawback.service.CustomerRefundmentService;

public class TypeJudge implements DecisionHandler{
	
	public String decide(ExecutionContext context) throws Exception {
		String businessId = (String)context.getContextInstance().getVariable(Constants.WORKFLOW_USER_KEY_VALUE);
		CustomerRefundmentService customerRefundmentService = (CustomerRefundmentService)EasyApplicationContextUtils.getBeanByName("customerRefundmentService");
		CustomerRefundment customerRefundment = customerRefundmentService._get(businessId);
		String strRet = "";
		CustomerRefundItem customerRefundItem = new CustomerRefundItem();
		CustomerDbBankItem customerDbBankItem = new CustomerDbBankItem();
		// 获取第一条"客户退款行项目"
		if(customerRefundment.getCustomerRefundItem().iterator().hasNext()){
			customerRefundItem = customerRefundment.getCustomerRefundItem().iterator().next();
		}
		// 获取第一条"客户退款银行"
		if(customerRefundment.getCustomerDbBankItem().iterator().hasNext()){
			customerDbBankItem = customerRefundment.getCustomerDbBankItem().iterator().next();
		}
		if("310066399018010033466".equals(customerDbBankItem.getRefundbankacount())){
			strRet = "本币上海账户";		// 若为上海账户，则走上海账户分支 
		}else if("CNY".equals(customerRefundment.getRefundcurrency())){ // 取退款表
			strRet = "本币";				// 若为人民币，则走资金部出纳审核分支 
		}else{
			strRet = "外币";				// 若为外币，则走综合二部分支
		}
		return strRet;
	}
}
