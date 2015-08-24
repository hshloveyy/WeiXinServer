package com.infolion.sapss.workflow.decision;

import java.math.BigDecimal;

import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.node.DecisionHandler;

import com.infolion.platform.util.EasyApplicationContextUtils;
import com.infolion.sapss.payment.dao.PaymentImportsInfoJdbcDao;

public class ChengduDecisionHandler implements DecisionHandler {

	public String decide(ExecutionContext executionContext) throws Exception {
		String pdName = executionContext.getProcessDefinition().getName();
		String businessid = (String)executionContext.getVariable("_workflow_user_key_value");
		Object value = executionContext.getVariable("_workflow_total_value");
		String currency =(String)executionContext.getVariable("_workflow_currency");
		if(value==null) return "金额小于200万USD或1500万CNY";
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
		//业务存量超2亿上报领导审批
		if(paymentImportsInfoJdbcDao.getChenduCunliang2Yi(businessid)==1) 
			return "金额大于200万USD或1500万CNY";
		
		if("USD".equals(currency)){//如果是美金
			if(totalValue>200){
				//paymentImportsInfoJdbcDao.updateChenduState(pdName, businessid);
				return "金额大于200万USD或1500万CNY";
			}
			else
				return "金额小于200万USD或1500万CNY";
		} else {//其它币种
			if (totalValue*Double.parseDouble(rate) > 1500){
				//paymentImportsInfoJdbcDao.updateChenduState(pdName, businessid);
				return "金额大于200万USD或1500万CNY";
			}
			else
				return "金额小于200万USD或1500万CNY";
		}
		
	}

}
