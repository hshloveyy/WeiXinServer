/*
 * @(#)SubProcessFaileActionHandler.java
 * 版权声明： 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：张崇镇
 *  时　间：2009-3-27
 *  描　述：创建
 */

package com.infolion.sapss.workflow.payment;

import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.exe.ExecutionContext;

import com.infolion.platform.component.workflow.WorkflowService;
import com.infolion.platform.sys.Constants;
import com.infolion.platform.util.EasyApplicationContextUtils;

/**
 * 
 * <pre>
 * 子流程审批失败
 * </pre>
 * 
 * <br>
 * JDK版本:1.5
 * 
 * @author 张崇镇
 * @version 1.0
 * @see The author for more details
 * @since 1.0
 */
public class SubProcessFailureActionHandler implements ActionHandler {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7853213107197537592L;

	public void execute(ExecutionContext context) throws Exception {
		long processId = context.getProcessInstance().getId();
		PaymentInnerInfoProcessService paymentInnerInfoProcessService = (PaymentInnerInfoProcessService) EasyApplicationContextUtils
				.getBeanByName("paymentInnerInfoProcessService");
		// String businessRecordId = (String) context.getContextInstance()
		// .getVariable(Constants.WORKFLOW_USER_KEY_VALUE);
		String businessRecordId = (String) context.getContextInstance()
				.getProcessInstance().getSuperProcessToken()
				.getProcessInstance().getContextInstance().getVariable(
						Constants.WORKFLOW_USER_KEY_VALUE);
		paymentInnerInfoProcessService.processFailure(businessRecordId,
				processId);
		WorkflowService.updateProcessInstanceEndState(processId, "银行承兑汇票审批不通过");
		context.setVariable("_workflow_sub_process_result", "failure");
		context.getProcessInstance().signal();
	}

}
