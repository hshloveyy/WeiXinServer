package com.infolion.sapss.workflow.interestBill;

import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.exe.ExecutionContext;

import com.infolion.platform.sys.Constants;
import com.infolion.platform.util.EasyApplicationContextUtils;
import com.infolion.sapss.interestBill.service.InterestBillService;

public class InterestBillSucessfulActionHandler implements ActionHandler {
	public void execute(ExecutionContext context) throws Exception {
		String businessRecordId = (String) context.getContextInstance()
				.getVariable(Constants.WORKFLOW_USER_KEY_VALUE);

		long processId = context.getProcessInstance().getId();
		InterestBillService interestBillService = (InterestBillService) EasyApplicationContextUtils
				.getBeanByName("interestBillService");
		
		interestBillService.processBillApplySucessful(businessRecordId,
				processId);
		
		context.getProcessInstance().signal();
	}

}
