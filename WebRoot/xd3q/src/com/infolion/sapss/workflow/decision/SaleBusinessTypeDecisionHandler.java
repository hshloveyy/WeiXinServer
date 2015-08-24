package com.infolion.sapss.workflow.decision;

import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.node.DecisionHandler;

public class SaleBusinessTypeDecisionHandler  implements DecisionHandler {

	public String decide(ExecutionContext executionContext) throws Exception {
		Object value = executionContext.getVariable("_trade_type");
		if(value==null) return "非出口";
		if("2".equals(value)||"5".equals(value)||"12".equals(value)) return "出口";
		else return "非出口";		
	}
}