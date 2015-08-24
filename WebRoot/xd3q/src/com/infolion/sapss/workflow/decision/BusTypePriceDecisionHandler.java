package com.infolion.sapss.workflow.decision;

import java.math.BigDecimal;

import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.node.DecisionHandler;

import com.infolion.platform.util.EasyApplicationContextUtils;
import com.infolion.sapss.payment.dao.PaymentImportsInfoJdbcDao;

public class BusTypePriceDecisionHandler  implements DecisionHandler {

	public String decide(ExecutionContext executionContext) throws Exception {
		Object tradeType = executionContext.getVariable("_trade_type");
		if(tradeType==null) return "非出口或出口金额大于100万CNY";
		if("2".equals(tradeType)||"4".equals(tradeType)||"5".equals(tradeType)||"12".equals(tradeType)){		
			Object value = executionContext.getVariable("_workflow_total_value");
			String currency =(String)executionContext.getVariable("_workflow_currency");
			if(value==null) return "非出口或出口金额大于100万CNY";
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
			if("USD".equals(currency)){//如果是美金
				if(totalValue>15)
					return "非出口或出口金额大于100万CNY";
				else
					return "出口且金额小于100万CNY";
			} else {//其它币种
				if (totalValue*Double.parseDouble(rate) > 100)
					return "非出口或出口金额大于100万CNY";
				else
					return "出口且金额小于100万CNY";
			}
		}
		return "非出口或出口金额大于100万CNY";
	}
}
