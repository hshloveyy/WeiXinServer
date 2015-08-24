package com.infolion.sapss.workflow.account;

import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.exe.ExecutionContext;

import com.infolion.platform.sys.Constants;
import com.infolion.platform.util.EasyApplicationContextUtils;

public class SubjectProcessSucessfulActionHandler implements ActionHandler {
	public void execute(ExecutionContext context) throws Exception {
		String businessRecordId = (String) context.getContextInstance()
				.getVariable(Constants.WORKFLOW_USER_KEY_VALUE);
		long processId = context.getProcessInstance().getId();
		SubjectProcessService subjectProcessService = (SubjectProcessService) EasyApplicationContextUtils
				.getBeanByName("subjectProcessService");
		subjectProcessService.processSucessful(businessRecordId, processId);
		context.getProcessInstance().signal();
	}
}
