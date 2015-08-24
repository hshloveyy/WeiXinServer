/*
 * @(#)HasRecWriteJudgeDecisionHandler.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：陈喜平
 *  时　间：2009-4-23
 *  描　述：创建
 */

package com.infolion.sapss.workflow.payment;

import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.node.DecisionHandler;

public class HasRecWriteJudgeDecisionHandler implements DecisionHandler{

	public String decide(ExecutionContext cxt) throws Exception {
		Object obj = cxt.getVariable("_has_rec_write");
		if(obj!=null &&"1".equals((String)obj))
			return "是";
		return "否";
	}

}
