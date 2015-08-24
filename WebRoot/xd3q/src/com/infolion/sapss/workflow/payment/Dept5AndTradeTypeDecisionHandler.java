package com.infolion.sapss.workflow.payment;

import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.node.DecisionHandler;

import com.infolion.sapss.payment.PaymentContants;

public class Dept5AndTradeTypeDecisionHandler implements DecisionHandler{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String decide(ExecutionContext context) throws Exception {
//		Object tt = context.getVariable("_isTT");
		String dept_id = (String)context.getVariable("_dept_id");
//		String businessType = (String)context.getVariable("_business_Type");
		String taskName = context.getNode().getName();
		if("部门及业务判断".equals(taskName)){
			if(PaymentContants.DEPT_ID_METAL.equals(dept_id)){
				return "金属部TT自营业务或金属部非TT业务";
			}
			else{
  			    return "非金属部或金属部非自营TT付款";
			}
		}
		else {
			if(PaymentContants.DEPT_ID_METAL.equals(dept_id))
				return "是";
			else return "否";
		}
	}
}
