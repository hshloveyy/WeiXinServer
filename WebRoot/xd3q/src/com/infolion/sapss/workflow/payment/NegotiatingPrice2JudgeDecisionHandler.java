/*
 * @(#)NegotiatingPrice2JudgeDecisionHandler.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：陈喜平
 *  时　间：2009-4-24
 *  描　述：创建
 */

package com.infolion.sapss.workflow.payment;

import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.node.DecisionHandler;

public class NegotiatingPrice2JudgeDecisionHandler implements DecisionHandler{

	public String decide(ExecutionContext executionContext) throws Exception {
		//如果是二次押汇(承兑)(走子流程)
		String negotiating =(String)executionContext.getVariable("_negotiating_workflow_process_name");
		if("foreign_trade_LC_DP_DA_pay".equals(negotiating)){
			return "小于500万USD或3500万CNY";
		}
		Object value = executionContext.getVariable("_workflow_total_value");
		String currency =(String)executionContext.getVariable("_workflow_currency");
		if(value==null) return "小于500万USD或3500万CNY";
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
			if(totalValue>500)
				return "大于500万USD或3500万CNY";
			else
				return "小于500万USD或3500万CNY";
		} else {//其它币种
			if (totalValue >3500)
				return "大于500万USD或3500万CNY";
			else
				return "小于500万USD或3500万CNY";
		}
	}

}
