/*
 * @(#)ApplyFaileActionHandler.java
 * 版权声明： 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：张崇镇
 *  时　间：2009-3-27
 *  描　述：创建
 */

package com.infolion.sample.workflow;

import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.exe.ExecutionContext;

import com.infolion.platform.workflow.engine.WorkflowService;

/**
 * 
 * <pre>
 * 请假流程审批失败
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
public class ApplyFaileActionHandler implements ActionHandler
{

	public void execute(ExecutionContext context) throws Exception
	{
		long processId = context.getProcessInstance().getId();
		context.getTask().getName();
		WorkflowService.updateProcessInstanceEndState_Deprecated(processId, "请假审批未通过");
		context.getProcessInstance().signal();
	}

}
