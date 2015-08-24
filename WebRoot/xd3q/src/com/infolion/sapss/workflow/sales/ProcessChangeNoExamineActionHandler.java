/*
 * @(#)ProcessChangeNoExamineActionHandler.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：陈喜平
 *  时　间：2009-3-17
 *  描　述：创建
 */

package com.infolion.sapss.workflow.sales;

import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.exe.ExecutionContext;

import com.infolion.platform.sys.Constants;
import com.infolion.platform.util.EasyApplicationContextUtils;

public class ProcessChangeNoExamineActionHandler implements ActionHandler {

	public void execute(ExecutionContext context) throws Exception {
		String businessRecordId = (String) context.getContextInstance()
				.getVariable(Constants.WORKFLOW_USER_KEY_VALUE);
		long processId = context.getProcessInstance().getId();
		SalesProcessService salesProcessService = (SalesProcessService) EasyApplicationContextUtils
				.getBeanByName("salesProcessService");
		salesProcessService.processChangeNoExamine(businessRecordId, processId);
		context.getProcessInstance().signal();
	}

}
