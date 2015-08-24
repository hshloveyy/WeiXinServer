package com.infolion.xdss3.reassign.workflow;

import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.node.DecisionHandler;

import com.infolion.platform.dpframework.core.Constants;
import com.infolion.platform.dpframework.util.EasyApplicationContextUtils;
import com.infolion.xdss3.reassign.ReassignConstant;
import com.infolion.xdss3.reassign.domain.Reassign;
import com.infolion.xdss3.reassign.service.ReassignService;

public class ReassignMethodJudge implements DecisionHandler {
	
	
	
	
	public String decide(ExecutionContext context) throws Exception {
		// TODO Auto-generated method stub
		
		String businessId = (String)context.getContextInstance().getVariable(Constants.WORKFLOW_USER_KEY_VALUE);
		ReassignService reassignService = (ReassignService)EasyApplicationContextUtils.getBeanByName("reassignService");
		Reassign reassign = reassignService.getReassignById(businessId);
		
		/**
		 * 作废原有单据
		 */
		if((ReassignConstant.COLLECT.equals(reassign.getReassigntype()) || ReassignConstant.PAYMENT.equals(reassign.getReassigntype())) && ReassignConstant.RESET_TO_FI.equals(reassign.getReassigntmethod())){
			
		}else{
			reassignService.abolishOldNo(reassign.getReassigntype(), reassign.getReassignboid());
		}
		String strPath = "";
		/**
		 * 判断重分配方式
		 */
		String reassignMethod = reassign.getReassigntmethod();
		
		//重置（到业务部门重新分配）或重置并冲销（到业务部门重新分配）：需提交流程
		if (reassignMethod.equals(ReassignConstant.RESET_TO_BS)
			|| reassignMethod.equals(ReassignConstant.RESET_AND_CLEAR) ) 
		{
			strPath = "提交财务部审批";
		}
		// 重置（财务部直接解除分配关系)、财务部冲销: 不需要提交流程。
		else if (reassignMethod.equals(ReassignConstant.RESET_TO_FI)
				|| reassignMethod.equals(ReassignConstant.FI_CLEAR) ) 
		{
			strPath = "不需提交业务部";
		}
		
		return strPath;
	}


}
