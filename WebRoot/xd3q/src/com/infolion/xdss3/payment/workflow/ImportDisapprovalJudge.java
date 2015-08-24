package com.infolion.xdss3.payment.workflow;

import java.util.List;

import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.exe.ExecutionContext;

import com.infolion.platform.dpframework.core.Constants;
import com.infolion.platform.util.EasyApplicationContextUtils;
import com.infolion.xdss3.payment.dao.ImportPaymentHibernateDao;
import com.infolion.xdss3.payment.domain.ImportPayment;

public class ImportDisapprovalJudge implements ActionHandler
{

	/**
	 * 判断经过"更新状态为不通过"节点的进口付款路径上是否存在特批子流程，如果有则更新business为4，否则更新为7
	 */
	public void execute(ExecutionContext context) throws Exception
	{
		ImportPaymentHibernateDao dao = (ImportPaymentHibernateDao) EasyApplicationContextUtils.getBeanByType(ImportPaymentHibernateDao.class);
		String result = "";
		String businessId = (String) context.getContextInstance().getVariable(Constants.WORKFLOW_USER_KEY_VALUE);
		if (context.getSubProcessInstance() != null)
		{
			Object o = context.getSubProcessInstance().getContextInstance().getVariable("particular_payment_result");
			if (o != null)
				result = (String) o;
		}
		List<ImportPayment> impPaymentLst = dao.find("from ImportPayment p where p.paymentid = '" + businessId + "'");
		if (impPaymentLst != null && !impPaymentLst.isEmpty())
		{
			ImportPayment impPayment = (ImportPayment) impPaymentLst.get(0);
			/*
			 * 原本要对结果进行判断后再更新状态，现在直接将状态更新-1
			 */
//			if (!"".equals(result))
//			{
//				impPayment.setBusinessstate("8");
//			}
//			else
//				impPayment.setBusinessstate("5");
//			;
			impPayment.setBusinessstate("-1");
			dao.update(impPayment);
			dao.flush();
		}

		context.getProcessInstance().signal();
	}

}
