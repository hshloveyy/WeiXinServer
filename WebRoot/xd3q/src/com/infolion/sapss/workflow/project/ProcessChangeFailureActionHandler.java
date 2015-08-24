/*
 * @(#)ProcessFaileActionHandler.java
 * 版权声明： 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：张崇镇
 *  时　间：2009-2-20
 *  描　述：创建
 */

package com.infolion.sapss.workflow.project;

import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.exe.ExecutionContext;

import com.infolion.platform.sys.Constants;
import com.infolion.platform.util.EasyApplicationContextUtils;

/**
 * 
 * <pre>
 * 立项变更审批不通过动作
 * </pre>
 * 
 * <br>
 * JDK版本:1.5
 * 
 * @author 陈喜平
 * @version 1.0
 * @see The author for more details
 * @since 1.0
 */
public class ProcessChangeFailureActionHandler implements ActionHandler {
	public void execute(ExecutionContext context) throws Exception {
		String businessRecordId = (String) context.getContextInstance()
				.getVariable(Constants.WORKFLOW_USER_KEY_VALUE);
		long processId = context.getProcessInstance().getId();
		ProjectProcessService projectProcessService = (ProjectProcessService) EasyApplicationContextUtils
				.getBeanByName("projectProcessService");
		projectProcessService.processChangeFailure(businessRecordId, processId);
		context.getProcessInstance().signal();
	}

}
