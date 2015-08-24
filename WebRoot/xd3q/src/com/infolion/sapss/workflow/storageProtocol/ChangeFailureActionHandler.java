package com.infolion.sapss.workflow.storageProtocol;

import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.exe.ExecutionContext;

import com.infolion.platform.sys.Constants;
import com.infolion.platform.util.EasyApplicationContextUtils;
import com.infolion.sapss.storageProtocol.service.StorageProtocolService;

public class ChangeFailureActionHandler implements ActionHandler {
	public void execute(ExecutionContext context) throws Exception {
		String businessRecordId = (String) context.getContextInstance()
				.getVariable(Constants.WORKFLOW_USER_KEY_VALUE);
		long processId = context.getProcessInstance().getId();
		StorageProtocolService projectProcessService = (StorageProtocolService) EasyApplicationContextUtils
				.getBeanByName("storageProtocolService");
		projectProcessService.processChangeFailure(businessRecordId, processId);
		context.getProcessInstance().signal();
	}

}
