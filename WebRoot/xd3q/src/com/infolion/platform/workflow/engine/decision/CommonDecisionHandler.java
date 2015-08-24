/*
 * @(#)CommonDecisionHandler.java
 * 版权声明： 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：张崇镇
 *  时　间：2010-8-25
 *  描　述：创建
 */

package com.infolion.platform.workflow.engine.decision;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.node.DecisionHandler;

import com.infolion.platform.dpframework.core.Constants;
import com.infolion.platform.workflow.engine.WorkflowException;
import com.infolion.platform.workflow.engine.WorkflowService;
import com.infolion.platform.workflow.engine.domain.ExtendTransition;
import com.infolion.platform.workflow.engine.extend.ExtendProcessService;

/**
 * 
 * <pre>
 * 通用路径选择节点，在使用这个类作为选择处理类后，在其后的路径上可以动态配置路径条件
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
public class CommonDecisionHandler implements DecisionHandler
{

	public String decide(ExecutionContext executionContext) throws Exception
	{
		Log log = LogFactory.getLog("com.infolion.platform.workflow.engine.decision.CommonDecisionHandler");
		log.debug("--------------------------进入通用路径选择节点判断类:");
		// 取得当前节点的所有离开路径
		String extProcessId = (String) WorkflowService.getRootWorkFlowVariableValue(executionContext.getContextInstance(), Constants.WORKFLOW_EXTPROCESS_ID);
		List<ExtendTransition> extendTransitions = ExtendProcessService.getAllExtendTransitions(extProcessId, executionContext.getNode().getId());
		executionContext.getVariable("applyamount");
		Map map = executionContext.getProcessInstance().getContextInstance().getVariables();

		log.debug("--------------------------当前流程实例下所有流程变量:");
		for (Object keyName : map.keySet())
		{
			Object value = map.get(keyName);
			log.debug("变量" + keyName.toString() + "=" + value.toString());
		}
		log.debug("共有" + extendTransitions.size() + "条路径！");
		for (ExtendTransition extendTransition : extendTransitions)
		{
			log.debug("路径：" + extendTransition.getTransitionName());
		}

		String autoLeaveTransitionName = ExtendProcessService.getVariableTransition(executionContext, extendTransitions);
		if (null == autoLeaveTransitionName)
		{
			throw new WorkflowException("不存在一个有效的“下一步操作”，请选择“下一步操作”，或者修改您的流程模型配置。");
			// taskInstance.getProcessInstance().signal();
		}

		log.debug("系统选择路径：" + autoLeaveTransitionName);
		return autoLeaveTransitionName;
	}
}
