/*
 * @(#)TTPriceJudgeDecisionHandler.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：陈喜平
 *  时　间：2009-4-10
 *  描　述：创建
 */

package com.infolion.sapss.workflow.payment;

import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.node.DecisionHandler;

public class TTPriceJudgeDecisionHandler implements DecisionHandler{

	public String decide(ExecutionContext executionContext) throws Exception {
		Object value = executionContext.getVariable("_workflow_total_value");
		String currency =(String)executionContext.getVariable("_workflow_currency");
		if(value==null) return "金额小于500万CNY";
		Double totalValue=0.0;
		if(value instanceof String){
			value = ("".equals(value))?"0":value;
			totalValue = Double.parseDouble(value.toString());
		}
		else if(value instanceof Double){
			totalValue= (Double)executionContext.getVariable("_workflow_total_value");
			totalValue =(totalValue==null)?0:totalValue;
		}
		if("USD".equals(currency)){//如果是美金
			if(totalValue>=70)
				return "金额大于500万CNY";
			else
				return "金额小于500万CNY";
		} else {//其它币种
			if (totalValue >= 500)
				return "金额大于500万CNY";
			else
				return "金额小于500万CNY";
		}
	}

}
