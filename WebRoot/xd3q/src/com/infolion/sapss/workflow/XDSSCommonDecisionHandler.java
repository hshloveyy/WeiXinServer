package com.infolion.sapss.workflow;

import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.node.DecisionHandler;

import com.infolion.platform.console.workflow.dao.SysWfUtilsJdbcDao;
import com.infolion.platform.sys.Constants;
import com.infolion.platform.util.EasyApplicationContextUtils;

public class XDSSCommonDecisionHandler implements DecisionHandler {

	public String decide(ExecutionContext executionContext) throws Exception {
		String businessid = (String)executionContext.getVariable(Constants.WORKFLOW_USER_KEY_VALUE);
		long nodeid = executionContext.getNode().getId();
		SysWfUtilsJdbcDao sysWfUtilsJdbcDao = (SysWfUtilsJdbcDao)EasyApplicationContextUtils.getBeanByType(SysWfUtilsJdbcDao.class);		
		return sysWfUtilsJdbcDao.queryTransitionName(businessid, nodeid);

	}

}
