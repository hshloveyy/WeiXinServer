/*
 * @(#)NegotiatingProcessFailureActionHandler.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：陈喜平
 *  时　间：2009-4-24
 *  描　述：创建
 */

package com.infolion.sapss.workflow.payment;

import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.exe.ExecutionContext;

import com.infolion.platform.component.workflow.WorkflowService;
import com.infolion.platform.sys.Constants;
import com.infolion.platform.util.EasyApplicationContextUtils;
import com.infolion.sapss.payment.service.PaymentImportsInfoJdbcService;
import com.infolion.sapss.workflow.ProcessCallBack;

public class NegotiatingProcessFailureActionHandler implements ActionHandler{

	public void execute(ExecutionContext context) throws Exception {
		String businessRecordId = (String) context.getContextInstance()	.getVariable(Constants.WORKFLOW_USER_KEY_VALUE);
		long processId = context.getProcessInstance().getId();
		WorkflowService.updateProcessInstanceEndState(processId,"押汇申请未通过");
		PaymentImportsInfoJdbcService servie =(PaymentImportsInfoJdbcService)EasyApplicationContextUtils.
											getBeanByName("paymentImportsInfoJdbcService");
		//是子流程:取主流程名来比较
		String negotiating = (String) context.getContextInstance().getVariable("_negotiating_workflow_process_name");
		String flag = "false";
		if("foreign_trade_LC_DP_DA_pay".equals(negotiating)){
			flag = "true";
		}
		servie.updateBusinessRecord(businessRecordId, ProcessCallBack.EXAMINE_FAILE, flag);
		context.getProcessInstance().signal();
	}

}
