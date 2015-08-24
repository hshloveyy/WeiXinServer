package com.infolion.xdss3.payment.workflow;

import java.util.List;

import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.exe.ExecutionContext;

import com.infolion.platform.dpframework.core.Constants;
import com.infolion.platform.util.EasyApplicationContextUtils;
import com.infolion.xdss3.payment.dao.HomePaymentHibernateDao;
import com.infolion.xdss3.payment.domain.HomePayment;
import com.infolion.xdss3.payment.service.HomePaymentService;

public class HomeApprovalJudge implements ActionHandler
{

	public void execute(ExecutionContext context) throws Exception
	{
		HomePaymentHibernateDao dao = (HomePaymentHibernateDao) EasyApplicationContextUtils.getBeanByType(HomePaymentHibernateDao.class);
		String result = "";
		String businessId = (String) context.getContextInstance().getVariable(Constants.WORKFLOW_USER_KEY_VALUE);
		if (context.getSubProcessInstance() != null)
		{
			Object o = context.getSubProcessInstance().getContextInstance().getVariable("particular_payment_result");
			if (o != null)
				result = (String) o;
		}
		List<HomePayment> hpPaymentLst = dao.find("from HomePayment p where p.paymentid = '" + businessId + "'");
		if (hpPaymentLst != null && !hpPaymentLst.isEmpty())
		{
			HomePayment hpPayment = (HomePayment) hpPaymentLst.get(0);
			if (!"".equals(result))
			{
				hpPayment.setBusinessstate("7");
			}
			else
				hpPayment.setBusinessstate("4");
			;
			dao.update(hpPayment);
			// 增加手工提交事务
			// by zhangchzh 20101215
			dao.flush();
		}

		HomePaymentService homePaymentService = (HomePaymentService) EasyApplicationContextUtils.getBeanByName("homePaymentService");
		homePaymentService.settlePayment(businessId);
		context.getProcessInstance().signal();
	}
}
