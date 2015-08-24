package com.infolion.sapss.workflow.payment;

import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.node.DecisionHandler;

import com.infolion.sapss.payment.PaymentContants;

public class Dept5DecisionHandler implements DecisionHandler{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String decide(ExecutionContext context) throws Exception {
		String dept_id = (String)context.getVariable("_dept_id");
		if(PaymentContants.DEPT_ID_METAL.equals(dept_id)){
			return "是";
		}
		return "否";
	}
}
