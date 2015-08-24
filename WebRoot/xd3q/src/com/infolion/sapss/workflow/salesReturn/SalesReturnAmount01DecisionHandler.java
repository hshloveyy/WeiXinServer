/*
 * @(#)TTPrice1JudgeDecisionHandler.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：陈喜平
 *  时　间：2009-4-10
 *  描　述：创建
 */

package com.infolion.sapss.workflow.salesReturn;

import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.node.DecisionHandler;

public class SalesReturnAmount01DecisionHandler implements DecisionHandler{

	public String decide(ExecutionContext executionContext) throws Exception {
		Object value = executionContext.getVariable("totalMoney");
		if(value==null) return "金额小于100USD或700万CNY";
		Double totalValue=0.0;
		totalValue = Double.parseDouble(value.toString())/10000;
		if(totalValue>700)
			return "金额大于100USD或700万CNY";
		else
			return "金额小于100USD或700万CNY";
	}

}
