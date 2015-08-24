/*
 * @(#)Price1JudgeDecisionHandler.java
 * 版权声明： 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：张崇镇
 *  时　间：2009-2-27
 *  描　述：创建
 */

package com.infolion.sapss.workflow.ship;

import java.math.BigDecimal;

import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.node.DecisionHandler;
/**
 * 
 * <pre>金额200万判断</pre>
 *
 * <br>
 * JDK版本:1.5
 *
 * @author 张崇镇
 * @version 1.0
 * @see The author for more details
 * @since 1.0
 */
public class Price2JudgeDecisionHandler implements DecisionHandler {

	public String decide(ExecutionContext executionContext) throws Exception {
		Object value = executionContext.getVariable("_workflow_total_value");
		//币别
		String currency =(String)executionContext.getVariable("_workflow_currency");
		if(value==null) return "金额小于200万美金";
		double totalValue = ((BigDecimal)value).doubleValue();
		//如果币别为美元
		if("USD".equals(currency))
		{		
			if(totalValue>200)
				return "金额大于200万美金";
			else
				return "金额小于200万美金";
		}
		else  //人民币
		{
			if(totalValue>1400)
				return "金额大于200万美金";
			else
				return "金额小于200万美金";
		}
	}

}
