package com.infolion.xdss3.billpurchased.workflow;

import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.node.DecisionHandler;

import com.infolion.platform.dpframework.core.Constants;
import com.infolion.platform.dpframework.util.EasyApplicationContextUtils;
import com.infolion.xdss3.billpurchased.domain.BillPurchased;
import com.infolion.xdss3.billpurchased.service.BillPurchasedService;

/**
 * @author HONG
 * 判断发票－票据类型是否为TT押汇
 */
public class BillpurchasedBillTypeJudge implements DecisionHandler {

	public String decide(ExecutionContext context) throws Exception {
	    String businessId = (String)context.getContextInstance().getVariable(Constants.WORKFLOW_USER_KEY_VALUE);
        BillPurchasedService billPurchasedService = (BillPurchasedService)EasyApplicationContextUtils.getBeanByName("billPurchasedService");
        BillPurchased billPurchased = billPurchasedService._get(businessId);
        if ("TT".equals(billPurchased.getBillPurBillItem().iterator().next().getBilltype())) {
            return "是";
        } else {
            return "否";
        }
	}
}
