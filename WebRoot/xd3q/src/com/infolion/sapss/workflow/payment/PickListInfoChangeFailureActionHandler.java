/*
 * @(#)PickListInfoChangeFailureSucessfulActionHandler.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：张崇镇
 *  时　间：Apr 10, 2009
 *  描　述：创建
 */

package com.infolion.sapss.workflow.payment;

import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.exe.ExecutionContext;

import com.infolion.platform.sys.Constants;
import com.infolion.platform.util.EasyApplicationContextUtils;

public class PickListInfoChangeFailureActionHandler implements ActionHandler
{
	public void execute(ExecutionContext context) throws Exception {
		String businessRecordId = (String) context.getContextInstance()
				.getVariable(Constants.WORKFLOW_USER_KEY_VALUE);
		long processId = context.getProcessInstance().getId();
		PickListInfoProcessService pickListInfoProcessService = (PickListInfoProcessService) EasyApplicationContextUtils
				.getBeanByName("pickListInfoProcessService");
		pickListInfoProcessService.processFaile(businessRecordId, processId);
		context.getProcessInstance().signal();
	}
}
