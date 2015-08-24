package com.infolion.sapss.workflow.payment;

import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.node.DecisionHandler;

import com.infolion.platform.console.org.domain.SysDept;
import com.infolion.platform.console.org.service.SysDeptService;
import com.infolion.platform.util.EasyApplicationContextUtils;

public class DeptIsFunDecisionHandler implements DecisionHandler{



	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String decide(ExecutionContext context) throws Exception {
		String dept_id = (String)context.getVariable("_dept_id");
		SysDeptService service = (SysDeptService)EasyApplicationContextUtils.getBeanByName("sysDeptService");
		SysDept dept = service.queryDeptById(dept_id);
		if("1".equals(dept.getIsFuncDept()))
			return "是";
		else return "否";
	}


}
