/*
 * @(#)NegotiatingJudgeDecisionHandler.java
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

import com.infolion.platform.sys.Constants;
import com.infolion.platform.util.EasyApplicationContextUtils;
import com.infolion.sapss.payment.dao.PaymentImportsInfoJdbcDao;

public class NegotiatingJudgeDecisionHandler implements DecisionHandler{

	public String decide(ExecutionContext ctxt) throws Exception {
		PaymentImportsInfoJdbcDao dao = (PaymentImportsInfoJdbcDao)EasyApplicationContextUtils.getBeanByName("paymentImportsInfoJdbcDao");
		String paymentId = (String)ctxt.getContextInstance().getVariable(Constants.WORKFLOW_USER_KEY_VALUE);
		String result = dao.getExamineSate(paymentId);
		if("5".equals(result))
			return "押汇申请通过";
		return "押汇申请不通过";
	}

}
