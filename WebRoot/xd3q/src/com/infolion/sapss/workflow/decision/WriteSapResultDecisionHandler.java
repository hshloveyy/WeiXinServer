/*
 * @(#)Price1JudgeDecisionHandler.java
 * 版权声明： 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：张崇镇
 *  时　间：2009-2-27
 *  描　述：创建
 */

package com.infolion.sapss.workflow.decision;

import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.node.DecisionHandler;
/**
 * 
 * <pre>写入SAP结果判断</pre>
 *
 * <br>
 * JDK版本:1.5
 *
 * @author 张崇镇
 * @version 1.0
 * @see The author for more details
 * @since 1.0
 */
public class WriteSapResultDecisionHandler implements DecisionHandler {

	public String decide(ExecutionContext executionContext) throws Exception {
		String result = (String)executionContext.getVariable("_workflow_is_allow");
		if("yes".equals(result))
			return "生成SAP订单成功";
		else
			return "产生错误";
	}

}
