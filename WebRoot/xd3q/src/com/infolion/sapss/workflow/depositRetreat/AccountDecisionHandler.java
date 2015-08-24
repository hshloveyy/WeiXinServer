package com.infolion.sapss.workflow.depositRetreat;

import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.node.DecisionHandler;

public class AccountDecisionHandler implements DecisionHandler {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String decide(ExecutionContext executionContext) throws Exception {
		//Object applyAccount = executionContext.getVariable("applyAccount");
		Object receiptAccount = executionContext.getVariable("receiptAccount");

	    if("1".equals(receiptAccount)){
	    	return "股份公司账户";
	    }
	    else return "信达诺账户";
	}
}