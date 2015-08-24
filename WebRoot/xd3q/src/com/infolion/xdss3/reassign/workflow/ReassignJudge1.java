package com.infolion.xdss3.reassign.workflow;

import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.node.DecisionHandler;

import com.infolion.platform.dpframework.core.Constants;
import com.infolion.platform.dpframework.util.EasyApplicationContextUtils;
import com.infolion.xdss3.reassign.ReassignConstant;
import com.infolion.xdss3.reassign.domain.Reassign;
import com.infolion.xdss3.reassign.service.ReassignService;

public class ReassignJudge1 implements DecisionHandler {
	
	public String decide(ExecutionContext context) throws Exception {
		String strRet = "";
		String businessId = (String)context.getContextInstance().getVariable(Constants.WORKFLOW_USER_KEY_VALUE);
		ReassignService reassignService = (ReassignService)EasyApplicationContextUtils.getBeanByName("reassignService");
		Reassign reassign = reassignService.getReassignById(businessId);
		// 若为"冲销（财务部冲销并作废）"，则直接将流程结束，否则走到财务部
		if (reassign.getReassigntmethod().equals(ReassignConstant.FI_CLEAR) && 
			(reassign.getReassigntype().equals(ReassignConstant.COLLECT) ||
			 reassign.getReassigntype().equals(ReassignConstant.PAYMENT) || 
			 reassign.getReassigntype().equals(ReassignConstant.CUSTOMERREFUNDMENT)|| 
             reassign.getReassigntype().equals(ReassignConstant.SUPPLIERREFUNDMENT))){
			strRet = "直接结束";
		}else{
			strRet = "到财务";
		}
		return strRet;
	}
}
