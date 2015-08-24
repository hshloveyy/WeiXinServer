package com.infolion.sapss.workflow.decision;

import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.node.DecisionHandler;

public class CreditValueDecisionHandler implements DecisionHandler {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String decide(ExecutionContext executionContext) throws Exception {
		Object value = executionContext.getVariable("_is_credit");
		if("Y".equals(value)){
			return "授信项目";
		}
		else return "非授信项目";
	}

}
