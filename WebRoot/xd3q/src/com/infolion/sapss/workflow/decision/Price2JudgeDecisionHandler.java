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

import java.math.BigDecimal;

import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.node.DecisionHandler;

import com.infolion.platform.util.EasyApplicationContextUtils;
import com.infolion.sapss.payment.dao.PaymentImportsInfoJdbcDao;
/**
 * 
 * <pre>金额700万判断</pre>
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
		String currency =(String)executionContext.getVariable("_workflow_currency");
		if(value==null) return "金额小于100万美金或700万人民币";
		Double totalValue=0.0;
		if(value instanceof String){
			value = ("".equals(value))?"0":value;
			totalValue = Double.parseDouble(value.toString());
		}
		else if(value instanceof Double){
			totalValue= (Double)value;
			totalValue =(totalValue==null)?0:totalValue;
		}
		else if(value instanceof BigDecimal){
			totalValue= ((BigDecimal)value).doubleValue();
			totalValue =(totalValue==null)?0:totalValue;
		}
		PaymentImportsInfoJdbcDao paymentImportsInfoJdbcDao = (PaymentImportsInfoJdbcDao)EasyApplicationContextUtils.
		getBeanByType(PaymentImportsInfoJdbcDao.class);
		String rate = paymentImportsInfoJdbcDao.getCurrentRate(currency);
		if("USD".equals(currency)){//如果是美金
			if(totalValue>100)
				return "金额大于100万美金或700万人民币";
			else
				return "金额小于100万美金或700万人民币";
		} else {//其它币种
			if (totalValue*Double.parseDouble(rate) > 700)
				return "金额大于100万美金或700万人民币";
			else
				return "金额小于100万美金或700万人民币";
		}
	}

}
