package com.infolion.sapss.workflow.decision;

import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.node.DecisionHandler;

public class IsRelateCompanyDecisionHandler implements DecisionHandler {

	public String decide(ExecutionContext executionContext) throws Exception {
		Object value = executionContext.getVariable("is_Relate_Company");
		if(value==null) return "否";
		if("true".equals(value)){
			return "是";
		}
		else return "否";
	}
}
