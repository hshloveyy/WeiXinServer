package com.infolion.sapss.workflow.decision;

import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.node.DecisionHandler;

//判断是否改变标准层次结构
public class ChangeStandardDecisionHandler implements DecisionHandler {
	public String decide(ExecutionContext executionContext) throws Exception {
		String value = (String) executionContext
				.getVariable("_workflow_iscs");
		if ("1".equals(value))
			return "修改标准层次结构";
		else
			return "不修改标准层次结构";
	}
}
