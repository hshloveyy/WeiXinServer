package com.infolion.sapss.workflow;

import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.exe.ExecutionContext;

import com.infolion.platform.util.EasyApplicationContextUtils;
import com.infolion.sapss.payment.dao.PaymentImportsInfoJdbcDao;

public class XinDaAnMgBbActionHandler implements ActionHandler {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2164892978806856009L;

	public void execute(ExecutionContext context) throws Exception {
		String pdName = context.getProcessDefinition().getName();
		String businessid = (String)context.getVariable("_workflow_user_key_value");
		PaymentImportsInfoJdbcDao paymentImportsInfoJdbcDao = (PaymentImportsInfoJdbcDao)EasyApplicationContextUtils.
		getBeanByType(PaymentImportsInfoJdbcDao.class);
		paymentImportsInfoJdbcDao.updateChenduState(pdName, businessid);
	}

}
