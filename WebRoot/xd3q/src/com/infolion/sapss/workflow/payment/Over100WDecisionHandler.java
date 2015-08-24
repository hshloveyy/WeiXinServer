/*
 * @(#)Over100WDecisionHandler.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：陈喜平
 *  时　间：2009-6-22
 *  描　述：创建
 */

package com.infolion.sapss.workflow.payment;

import java.math.BigDecimal;

import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.node.DecisionHandler;

import com.infolion.platform.util.EasyApplicationContextUtils;
import com.infolion.sapss.payment.dao.PaymentImportsInfoJdbcDao;

public class Over100WDecisionHandler implements DecisionHandler {
	private static final long serialVersionUID = 156809983581966242L;

	public String decide(ExecutionContext txt) throws Exception {
		Object value = txt.getVariable("_workflow_total_value");
		String currency =(String)txt.getVariable("_workflow_currency");
		if(value==null) return "否";
		Double totalValue=0.0;
		if(value instanceof String){
			value = ("".equals(value))?"0":value;
			totalValue = Double.parseDouble(value.toString());
		}
		else if(value instanceof Double){
			totalValue= (Double)value;
			totalValue =(totalValue==null)?0:totalValue;
		}
		 PaymentImportsInfoJdbcDao paymentImportsInfoJdbcDao = (PaymentImportsInfoJdbcDao)EasyApplicationContextUtils.
			getBeanByType(PaymentImportsInfoJdbcDao.class);
		 String rate = paymentImportsInfoJdbcDao.getCurrentRate(currency);
		 BigDecimal bvalue = new BigDecimal(totalValue);
		 BigDecimal r = new BigDecimal(rate);
		 bvalue = bvalue.multiply(r);
		 
		 if(bvalue.divide(new BigDecimal(1)).floatValue()>100){
			 return "是";
		 }else
			 return "否";
		 
	}

}
