/*
 * @(#)ProcessSucessfulActionHandler.java
 * 版权声明： 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：林进旭
 *  时　间：2009-3-17
 *  描　述：创建
 */

package com.infolion.sapss.workflow.credit;

import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.exe.ExecutionContext;

import com.infolion.platform.sys.Constants;
import com.infolion.platform.util.EasyApplicationContextUtils;

/**
 * 
 * <pre>
 * 立项审批通过动作
 * </pre>
 * 
 * <br>
 * JDK版本:1.5
 * 
 * @author 林进旭
 * @version 1.0
 * @see The author for more details
 * @since 1.0
 */
public class CreditArriveHisProcessSucessfulActionHandler implements ActionHandler {
	public void execute(ExecutionContext context) throws Exception {

		String businessRecordId = (String) context.getContextInstance().getVariable(Constants.WORKFLOW_USER_KEY_VALUE);
		String creditState = (String) context.getContextInstance().getVariable("_workflow_credit_state");
		
		long processId = context.getProcessInstance().getId();
		CreditArriveHisProcessService creditHisArriveProcessService = (CreditArriveHisProcessService) EasyApplicationContextUtils
				.getBeanByName("creditArriveHisProcessService");
		creditHisArriveProcessService.processSucessful(businessRecordId, processId,creditState);
		context.getProcessInstance().signal();
	}

}
