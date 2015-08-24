/*
 * @(#)EarnestJudgeDecisionHandler.java
 * 版权声明： 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：林哲文
 *  时　间：2009-3-25
 *  描　述：创建
 */

package com.infolion.sapss.workflow.decision;

import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.node.DecisionHandler;
/**
 * 
 * <pre>判断是否为出货前TT或定金</pre>
 *
 * <br>
 * JDK版本:1.5
 *
 * @author 林哲文
 * @version 1.0
 * @see The author for more details
 * @since 1.0
 */
public class EarnestJudgeDecisionHandler implements DecisionHandler {

	public String decide(ExecutionContext executionContext) throws Exception {
		String value = (String)executionContext.getVariable("_workflow_ispay");
		if("1".equals(value))
			return "是";
		else
			return "否";
	}

}
