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
import com.infolion.sapss.Constants;
import com.infolion.sapss.payment.dao.PaymentImportsInfoJdbcDao;

/**
 * 
 * <pre>
 * 金额1400万判断
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
public class UnitJudgeDecisionHandler implements DecisionHandler {
	
	
	/**
	 * 关联单位，或立项金额大于6亿，或立项净利润超过股份公司上年度归属母公司净利润5%以上需报证券部批，路径名称已经有误
	 */

	public String decide(ExecutionContext executionContext) throws Exception {
		Object value = executionContext.getVariable("_workflow_total_value");
		String currency =(String)executionContext.getVariable("_workflow_currency");
		Object clearProfit4Cny = executionContext.getVariable("_clear_profit_4cny");
		if(value==null) return "非关联单位";
		Double totalValue=0.0;
		if(value instanceof String){
			value = ("".equals(value))?"0":value;
			totalValue = Double.parseDouble(value.toString());
		}
		else if(value instanceof Double){
			totalValue= (Double)executionContext.getVariable("_workflow_total_value");
			totalValue =(totalValue==null)?0:totalValue;
		}
		else if(value instanceof BigDecimal){
			totalValue= ((BigDecimal)value).doubleValue();
			totalValue =(totalValue==null)?0:totalValue;
		}
		PaymentImportsInfoJdbcDao paymentImportsInfoJdbcDao = (PaymentImportsInfoJdbcDao)EasyApplicationContextUtils.
		getBeanByType(PaymentImportsInfoJdbcDao.class);
		String rate = paymentImportsInfoJdbcDao.getCurrentRate(currency);

		String unit = (String) executionContext.getVariable("_workflow_is_unit");
		 if ("yes".equals(unit)) {//&&totalValue*Double.parseDouble(rate) > 300
			 return "关联单位并且金额大于300万或金额大于6亿";
		 }else if(totalValue*Double.parseDouble(rate) > 60000){
			 return "关联单位并且金额大于300万或金额大于6亿";
		 }else if(clearProfit4Cny!=null&&Double.parseDouble((String)clearProfit4Cny)>Constants.GFGSSNDGSMGSJLR){
			 return "关联单位并且金额大于300万或金额大于6亿";
		 }
	     else
			return "非关联单位";
	}

}
