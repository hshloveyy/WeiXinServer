package com.infolion.xdss3.customerDrawback.workflow;

import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.exe.ExecutionContext;

import com.infolion.platform.dpframework.core.Constants;
import com.infolion.platform.dpframework.util.EasyApplicationContextUtils;
import com.infolion.xdss3.customerDrawback.service.RelatedCollectService;
import com.infolion.xdss3.supplierDrawback.service.RelatedPaymentService;

public class DrawbackActionHandler implements ActionHandler {

	public void execute(ExecutionContext context) throws Exception {
		String businessId = (String)context.getContextInstance().getVariable(Constants.WORKFLOW_USER_KEY_VALUE);
		RelatedCollectService relatedCollectService = (RelatedCollectService)EasyApplicationContextUtils.getBeanByName("relatedCollectService");
		relatedCollectService.saveRelatedCollect(businessId);
		context.getProcessInstance().signal();
	}

}
