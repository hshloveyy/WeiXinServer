package com.infolion.sapss.workflow.decision;

import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.node.DecisionHandler;

public class BusinessTypeDecisionHandler implements DecisionHandler {

	public String decide(ExecutionContext executionContext) throws Exception {
		Object value = executionContext.getVariable("_trade_type");
		if(value==null) return "非自营*/代理出口";
		if("2".equals(value)||"5".equals(value)) return "自营*/代理出口";
		else return "非自营*/代理出口";		
	}
}
