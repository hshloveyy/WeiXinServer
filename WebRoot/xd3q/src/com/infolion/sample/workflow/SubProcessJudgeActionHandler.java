/*
 * @(#)SubProcessJudgeActionHandler.java
 * 版权声明： 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：张崇镇
 *  时　间：2009-3-27
 *  描　述：创建
 */

package com.infolion.sample.workflow;

import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.node.DecisionHandler;

/**
 * 
 * <pre>
 * 子流程审批结果判断
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
public class SubProcessJudgeActionHandler implements DecisionHandler
{
	public String decide(ExecutionContext context) throws Exception
	{
		String subProcessResult = (String) context.getSubProcessInstance().getContextInstance().getVariable("_workflow_sub_process_result");
		if ("pass".equals(subProcessResult))
		{
			return "表决通过";
		}
		else if ("faile".equals(subProcessResult))
		{
			return "表决不通过";
		}
		return null;
	}

}
