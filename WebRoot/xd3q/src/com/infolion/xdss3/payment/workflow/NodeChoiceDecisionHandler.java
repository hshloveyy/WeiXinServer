package com.infolion.xdss3.payment.workflow;

import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.node.DecisionHandler;

import com.infolion.platform.dpframework.core.Constants;
import com.infolion.platform.util.EasyApplicationContextUtils;
import com.infolion.xdss3.payment.service.ParticularWorkflowXD3QService;

public class NodeChoiceDecisionHandler implements DecisionHandler{
	private static final long serialVersionUID = -3903979789097678893L;

	public String decide(ExecutionContext context) throws Exception {
		String businessId = (String)context.getContextInstance().getVariable(Constants.WORKFLOW_USER_KEY_VALUE);
		
		ParticularWorkflowXD3QService service = (ParticularWorkflowXD3QService)EasyApplicationContextUtils.getBeanByName("particularWorkflowXD3QService");
		String nodeName = service.getNearestNoPassNodeName(businessId);
		if(nodeName!=null && !"".equals(nodeName)){
			if(nodeName.indexOf("股份公司副总审批特批付款")!=-1)
				return "由副总退回，提交股份公司常务副总";
			else if(nodeName.indexOf("股份公司常务副总审批特批付款")!=-1)
				return "由股份公司常务副总退回，提交股份总经理";
			else if(nodeName.indexOf("总经理")!=-1)
				return "由总经理退回，提交董事长";
		}
		return null;
	}

}
