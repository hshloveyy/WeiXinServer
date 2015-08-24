package com.infolion.xdss3.payment.workflow;

import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.node.DecisionHandler;

public class ParticularPaymentDecisionHandler implements DecisionHandler{
	private static final long serialVersionUID = -3903979789097678893L;

	public String decide(ExecutionContext context) throws Exception {
		
		String result = (String)context.getSubProcessInstance().getContextInstance().getVariable("particular_payment_result");
		if("pass".equals(result)){
			return "特批通过";
		}
		return "特批不通过";
	}

}
