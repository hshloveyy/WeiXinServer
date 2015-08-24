package com.infolion.sapss.workflow.payment;

import java.math.BigDecimal;

import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.node.DecisionHandler;

import com.infolion.platform.util.EasyApplicationContextUtils;
import com.infolion.sapss.payment.dao.PaymentImportsInfoJdbcDao;

public class IsRelationDecisionHandler implements DecisionHandler {
	private static final long serialVersionUID = 1L;

	public String decide(ExecutionContext txt) throws Exception {
		Object unitNo = txt.getVariable("unit_no");
		if(unitNo!=null&&unitNo.toString().startsWith("002"))
			return "关联交易";
		/**20150326新增 单项合同超过6亿 需经过证券部审批***/
		/**非货款不需要判断金额*/
		if(txt.getProcessDefinition().getName().indexOf("contract")<0)
			return "非关联交易";
		Object value = txt.getVariable("_workflow_total_value");
		String currency =(String)txt.getVariable("_workflow_currency");
		if(value==null) return "非关联交易";
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
		if (totalValue*Double.parseDouble(rate) > 60000)
			return "关联交易";
		/*****/
		return "非关联交易";
	}


}
