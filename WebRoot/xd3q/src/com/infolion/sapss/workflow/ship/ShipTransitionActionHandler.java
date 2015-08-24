package com.infolion.sapss.workflow.ship;

import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.exe.ExecutionContext;

import com.infolion.platform.sys.Constants;
import com.infolion.platform.util.EasyApplicationContextUtils;
import com.infolion.sapss.ship.service.ShipJdbcService;

public class ShipTransitionActionHandler implements ActionHandler
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void execute(ExecutionContext context) throws Exception{
		String businessRecordId = (String) context.getContextInstance()
				.getVariable(Constants.WORKFLOW_USER_KEY_VALUE);
		//long processId = context.getProcessInstance().getId();
		String transistionName = context.getTransition().getName();
		ShipJdbcService shipJdbcService = (ShipJdbcService) EasyApplicationContextUtils
				.getBeanByName("shipJdbcService");
		shipJdbcService.updateWithTransition(businessRecordId, transistionName);

	}

}
