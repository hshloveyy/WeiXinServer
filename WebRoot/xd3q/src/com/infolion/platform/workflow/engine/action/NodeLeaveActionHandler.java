/*
 * @(#)NodeLeaveActionHandler.java
 * 版权声明： 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：张崇镇
 *  时　间：2009-7-23
 *  描　述：创建
 */

package com.infolion.platform.workflow.engine.action;

import java.util.Set;

import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.def.Node;
import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.graph.node.EndState;
import org.jbpm.graph.node.StartState;
import org.jbpm.graph.node.TaskNode;

import com.infolion.platform.dpframework.core.Constants;
import com.infolion.platform.workflow.engine.WorkflowService;
import com.infolion.platform.workflow.engine.extend.ExtendProcessService;

/**
 * <pre>
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
public class NodeLeaveActionHandler implements ActionHandler
{
	public void execute(ExecutionContext executionContext) throws Exception
	{
		Node node = executionContext.getNode();

		System.out.println("----------------离开节点：" + executionContext.getNode().getName());

		ProcessInstance processInstance = executionContext.getProcessInstance();
		String businessId = (String) executionContext.getVariable(Constants.WORKFLOW_USER_KEY_VALUE);
		String subBusinessId = null;
		String extProcessId = (String) WorkflowService.getRootWorkFlowVariableValue(executionContext.getContextInstance(), Constants.WORKFLOW_EXTPROCESS_ID);
		if (WorkflowService.isSubProcess(processInstance))
		{
			String processDefinitionName = processInstance.getProcessDefinition().getName();
			subBusinessId = (String) executionContext.getVariable(WorkflowService.getSubWorkFolwBusinessIdVariableName(processDefinitionName));
		}

		if (null != subBusinessId)
		{
			businessId = subBusinessId;
		}

		if (node instanceof TaskNode)
		{
			
			ExtendProcessService.deleteDynaTaskActor(businessId);
			ExtendProcessService.executeLogic(extProcessId, node.getId(), businessId, "4");
		}
		else if (node instanceof EndState || node instanceof StartState)
		{
			ExtendProcessService.executeLogic(extProcessId, node.getId(), businessId, "4");
		}
	}
}
