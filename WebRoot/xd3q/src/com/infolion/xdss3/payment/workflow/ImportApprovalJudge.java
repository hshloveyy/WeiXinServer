package com.infolion.xdss3.payment.workflow;

import java.util.List;

import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.exe.ExecutionContext;

import com.infolion.platform.dpframework.core.Constants;
import com.infolion.platform.util.EasyApplicationContextUtils;
import com.infolion.xdss3.payment.dao.ImportPaymentHibernateDao;
import com.infolion.xdss3.payment.domain.ImportPayment;
import com.infolion.xdss3.payment.service.ImportPaymentService;

public class ImportApprovalJudge implements ActionHandler
{

	/**
	 * 判断经过"更新状态为审批通过"节点的进口付款路径上是否存在特批子流程，如果有则更新business为4，否则更新为7
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
			if (!"".equals(result))
			{
				impPayment.setBusinessstate("7");
			}
			else
				impPayment.setBusinessstate("4");
			;
			dao.update(impPayment);
			dao.flush();
		}

		ImportPaymentService importPaymentService = (ImportPaymentService) EasyApplicationContextUtils.getBeanByName("importPaymentService");
		importPaymentService.settlePayment(businessId);
		context.getProcessInstance().signal();

	}
}
