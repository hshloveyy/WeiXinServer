package com.infolion.sapss.workflow.payment;

import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.node.DecisionHandler;

import com.infolion.sapss.payment.PaymentContants;

public class Dept5AndPayTypeDecisionHandler implements DecisionHandler{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String decide(ExecutionContext context) throws Exception {
		Object obj = context.getVariable("paymentInnerInfo");
		if(obj!=null&&((String)obj).length()>0){
			String payMethod = (String)context.getVariable("payMethod");
			String payType = (String)context.getVariable("payType");
			String dept_id = (String)context.getVariable("_dept_id");
			if("2".equals(payMethod)&&"2".equals(payType)&&PaymentContants.DEPT_ID_METAL.equals(dept_id)){
				return "是";
			}
		}
		
		return "否";
	}

}

