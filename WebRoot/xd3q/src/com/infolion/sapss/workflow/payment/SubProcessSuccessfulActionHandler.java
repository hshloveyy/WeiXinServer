/*
 * @(#)SubProcessSucessfulActionHandler.java
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
 * 子流程审批成功
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
public class SubProcessSuccessfulActionHandler implements ActionHandler {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9209646765040851809L;

	public void execute(ExecutionContext context) throws Exception {
		String maturityDate = (String) context.getProcessInstance()
				.getContextInstance().getVariable("maturityDate");
		long processId = context.getProcessInstance().getId();
		// String businessRecordId = (String) context.getContextInstance()
		// .getVariable(Constants.WORKFLOW_USER_KEY_VALUE);
		String businessRecordId = (String) context.getContextInstance()
				.getProcessInstance().getSuperProcessToken()
				.getProcessInstance().getContextInstance().getVariable(
						Constants.WORKFLOW_USER_KEY_VALUE);
		PaymentInnerInfoProcessService paymentInnerInfoProcessService = (PaymentInnerInfoProcessService) EasyApplicationContextUtils
				.getBeanByName("paymentInnerInfoProcessService");
		paymentInnerInfoProcessService.processSuccessful(businessRecordId,
				processId, maturityDate);
		WorkflowService.updateProcessInstanceEndState(processId, "银行承兑汇票审批通过");
		context.setVariable("_workflow_sub_process_result", "pass");
		context.getProcessInstance().signal();
	}

}
