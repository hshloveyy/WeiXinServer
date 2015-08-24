package com.infolion.sapss.workflow.depositRetreat;

import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.exe.ExecutionContext;

import com.infolion.platform.sys.Constants;
import com.infolion.platform.util.EasyApplicationContextUtils;
import com.infolion.sapss.depositRetreat.service.DepositRetreatService;

public class ProcessChangeFailureActionHandler implements ActionHandler{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void execute(ExecutionContext context) throws Exception{
		String businessRecordId = (String) context.getContextInstance()
				.getVariable(Constants.WORKFLOW_USER_KEY_VALUE);
		long processId = context.getProcessInstance().getId();
		DepositRetreatService depositRetreatService = (DepositRetreatService) EasyApplicationContextUtils
				.getBeanByName("depositRetreatService");
	
		depositRetreatService.processDepositRetreatFaile(businessRecordId,
					processId);

		context.getProcessInstance().signal();
	}

}
