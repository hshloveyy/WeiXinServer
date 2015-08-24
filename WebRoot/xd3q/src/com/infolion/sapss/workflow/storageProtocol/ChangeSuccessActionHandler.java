package com.infolion.sapss.workflow.storageProtocol;

import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.exe.ExecutionContext;

import com.infolion.platform.sys.Constants;
import com.infolion.platform.util.EasyApplicationContextUtils;
import com.infolion.sapss.storageProtocol.service.StorageProtocolService;

public class ChangeSuccessActionHandler  implements ActionHandler {
	public void execute(ExecutionContext context) throws Exception {
		String businessRecordId = (String) context.getContextInstance()
		.getVariable(Constants.WORKFLOW_USER_KEY_VALUE);
		long processId = context.getProcessInstance().getId();
		StorageProtocolService projectProcessService = (StorageProtocolService) EasyApplicationContextUtils
		.getBeanByName("storageProtocolService");
		projectProcessService.processChangeSucessful(businessRecordId, processId);
		context.getProcessInstance().signal();
	}

}
