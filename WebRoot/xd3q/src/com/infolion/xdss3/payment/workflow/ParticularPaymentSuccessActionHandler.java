package com.infolion.xdss3.payment.workflow;

import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.exe.ExecutionContext;

public class ParticularPaymentSuccessActionHandler implements ActionHandler{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void execute(ExecutionContext context) throws Exception {
		context.getProcessInstance().getContextInstance().setVariable("particular_payment_result", "pass");
		context.getProcessInstance().signal();
	}

}
