package com.infolion.xdss3.payment.workflow;

import java.util.List;

import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.node.DecisionHandler;

import com.infolion.platform.dpframework.core.Constants;
import com.infolion.platform.util.EasyApplicationContextUtils;
import com.infolion.xdss3.payment.dao.HomePaymentHibernateDao;
import com.infolion.xdss3.payment.domain.HomePayment;

public class HomeDisapprovalJudge implements ActionHandler{

	public void execute(ExecutionContext context) throws Exception {
		HomePaymentHibernateDao dao = (HomePaymentHibernateDao)EasyApplicationContextUtils.getBeanByType(HomePaymentHibernateDao.class);
		String result = "";
		String businessId = (String)context.getContextInstance().getVariable(Constants.WORKFLOW_USER_KEY_VALUE);
		if(context.getSubProcessInstance() != null){
			Object o = context.getSubProcessInstance().getContextInstance().getVariable("particular_payment_result");
			if(o != null)
				result = (String)o;
		}
		List<HomePayment> hpPaymentLst = dao.find("from HomePayment p where p.paymentid = '" + businessId + "'");
		if(hpPaymentLst != null && !hpPaymentLst.isEmpty()){
			HomePayment hpPayment = (HomePayment)hpPaymentLst.get(0);
			/*
			 * 原本要对结果进行判断后再更新状态，现在直接将状态更新-1
			 */
//			if(!"".equals(result)){
//				hpPayment.setBusinessstate("8");
//			}
//			else
//				hpPayment.setBusinessstate("5");;
			hpPayment.setBusinessstate("-1");
			dao.update(hpPayment);
			dao.flush();
		}
		
		context.getProcessInstance().signal();
	}
}
