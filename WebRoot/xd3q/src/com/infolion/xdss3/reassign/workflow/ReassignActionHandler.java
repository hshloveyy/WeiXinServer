package com.infolion.xdss3.reassign.workflow;

import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.exe.ExecutionContext;

import com.infolion.platform.dpframework.core.Constants;
import com.infolion.platform.dpframework.util.EasyApplicationContextUtils;
import com.infolion.xdss3.BusinessState;
import com.infolion.xdss3.reassign.ReassignConstant;
import com.infolion.xdss3.reassign.domain.Reassign;
import com.infolion.xdss3.reassign.service.ReassignService;

public class ReassignActionHandler implements ActionHandler {

	public void execute(ExecutionContext context) throws Exception {
		String businessId = (String)context.getContextInstance().getVariable(Constants.WORKFLOW_USER_KEY_VALUE);
		ReassignService reassignService = (ReassignService)EasyApplicationContextUtils.getBeanByName("reassignService");
				
		Reassign reassign = reassignService.getReassignById(businessId);
		
		/*
		 * 设置重分配状态为审核通过
		 */
		reassignService.updateReassignState(businessId, BusinessState.SUBMITNOTPASS);

		/*
		 * 自动判断并更新重分配对应单据的表的状态（"客户单清"、"供应商单清"不会进来）
		 */
		reassignService.rejectProcess(reassign);
		
		context.getProcessInstance().signal();
	}

}
