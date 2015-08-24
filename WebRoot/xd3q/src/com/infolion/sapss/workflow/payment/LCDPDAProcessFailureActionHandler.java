/*
 * @(#)LCDPDAProcessFailureActionHandler.java
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

public class LCDPDAProcessFailureActionHandler implements ActionHandler {

	public void execute(ExecutionContext context) throws Exception {
		String businessRecordId = (String) context.getContextInstance()	.getVariable(Constants.WORKFLOW_USER_KEY_VALUE);
		long processId = context.getProcessInstance().getId();
		WorkflowService.updateProcessInstanceEndState(processId,"LC/DP/DA申请未通过");
		PaymentImportsInfoJdbcService servie =(PaymentImportsInfoJdbcService)EasyApplicationContextUtils.
											getBeanByName("paymentImportsInfoJdbcService");
		servie.updateBusinessRecord(businessRecordId, ProcessCallBack.EXAMINE_FAILE, null);
		context.getProcessInstance().signal();
		
	}

}
