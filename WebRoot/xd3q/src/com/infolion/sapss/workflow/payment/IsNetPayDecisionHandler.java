package com.infolion.sapss.workflow.payment;

import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.node.DecisionHandler;

public class IsNetPayDecisionHandler implements DecisionHandler {
	private static final long serialVersionUID = 1L;

	public String decide(ExecutionContext txt) throws Exception {
		Object value = txt.getVariable("is_net_pay");
		if(value!=null&&"1".equals(value))
			return "是";
		return "否";
	}


}
