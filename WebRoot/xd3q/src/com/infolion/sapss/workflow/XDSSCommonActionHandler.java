package com.infolion.sapss.workflow;

import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.exe.ExecutionContext;

import com.infolion.platform.console.workflow.dao.SysWfUtilsJdbcDao;
import com.infolion.platform.sys.Constants;
import com.infolion.platform.util.EasyApplicationContextUtils;
import com.infolion.sapss.payment.dao.PaymentImportsInfoJdbcDao;

public class XDSSCommonActionHandler  implements ActionHandler {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2164892978806856009L;

	public void execute(ExecutionContext context) throws Exception {
		String businessid = (String)context.getVariable(Constants.WORKFLOW_USER_KEY_VALUE);
		long nodeid = context.getNode().getId();
		SysWfUtilsJdbcDao sysWfUtilsJdbcDao = (SysWfUtilsJdbcDao)EasyApplicationContextUtils.getBeanByType(SysWfUtilsJdbcDao.class);		
		sysWfUtilsJdbcDao.dealActionHandler(businessid,nodeid);
	}

}