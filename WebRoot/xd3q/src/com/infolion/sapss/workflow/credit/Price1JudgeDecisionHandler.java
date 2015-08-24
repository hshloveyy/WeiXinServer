/*
 * @(#)Price1JudgeDecisionHandler.java
 * 版权声明： 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：林进旭
 *  时　间：2009-3-25
 *  描　述：创建
 */

package com.infolion.sapss.workflow.credit;

import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.node.DecisionHandler;

/**
 * 
 * <pre>
 * 金额500万美金判断
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
public class Price1JudgeDecisionHandler implements DecisionHandler
{

	public String decide(ExecutionContext executionContext) throws Exception
	{
		// 金额
		Object value = executionContext.getVariable("_workflow_total_value");
		// 币别
		Object currency = executionContext.getVariable("_workflow_currency");

		if (value == null)
			return "小于500万美金";

		Double totalValue = 0.0;
		if (value instanceof String)
		{
			value = ("".equals(value)) ? "0" : value;
			totalValue = Double.parseDouble(value.toString());
		}
		else if (value instanceof Double)
		{
			totalValue = (Double) executionContext.getVariable("_workflow_total_value");
			totalValue = (totalValue == null) ? 0 : totalValue;
		}

		if ("USD".equals(currency))
		{// 如果是美金
			if (totalValue > 500)
				return "大于500万美金";
			else
				return "小于500万美金";
		}
		else
		{// 其它币种
			if (totalValue > 3500)
				return "大于500万美金";
			else
				return "小于500万美金";
		}
	}

}
