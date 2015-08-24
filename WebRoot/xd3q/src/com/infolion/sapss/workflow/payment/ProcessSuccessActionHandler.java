/*
 * @(#)TTProcessSuccessActionHandler.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：陈喜平
 *  时　间：2009-4-10
 *  描　述：创建
 */

package com.infolion.sapss.workflow.payment;

import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.exe.ExecutionContext;

import com.infolion.platform.component.workflow.WorkflowService;
import com.infolion.platform.sys.Constants;
import com.infolion.platform.util.EasyApplicationContextUtils;
import com.infolion.sapss.payment.service.PaymentImportsInfoJdbcService;
import com.infolion.sapss.payment.service.PaymentInnerInfoHibernateService;
import com.infolion.sapss.workflow.ProcessCallBack;

public class ProcessSuccessActionHandler implements ActionHandler{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2755709979418688527L;

	public void execute(ExecutionContext context) throws Exception {
		String businessRecordId = (String) context.getContextInstance().getVariable(Constants.WORKFLOW_USER_KEY_VALUE);
		long processId = context.getProcessInstance().getId();
		Object isPaymentInnerInfo = context.getContextInstance().getVariable("paymentInnerInfo");
		if(isPaymentInnerInfo!=null&&((String)isPaymentInnerInfo).length()>0){
			String payType = (String) context.getContextInstance().getVariable("payType");
			String payMethod = (String) context.getContextInstance().getVariable("payMethod");
			if("1".equals(payType)){
				if("7".equals(payMethod)){
					String maturityDate = (String) context.getProcessInstance()
							.getContextInstance().getVariable("maturityDate");
					PaymentInnerInfoProcessService paymentInnerInfoProcessService = (PaymentInnerInfoProcessService) EasyApplicationContextUtils
							.getBeanByName("paymentInnerInfoProcessService");
					paymentInnerInfoProcessService.processSuccessful(businessRecordId,
							processId, maturityDate);
					WorkflowService.updateProcessInstanceEndState(processId, "银行承兑汇票审批通过");
				}else{
					WorkflowService.updateProcessInstanceEndState(processId,"内贸付款申请通过");
				}
			}
			else if("2".equals(payType)){
				WorkflowService.updateProcessInstanceEndState(processId,"非货款支付申请通过");
			}
			PaymentInnerInfoHibernateService service =(PaymentInnerInfoHibernateService)
					EasyApplicationContextUtils.getBeanByName("paymentInnerInfoHibernateService");
			service.updateBusinessRecord(businessRecordId, ProcessCallBack.EXAMINE_SUCCESSFUL, null);
		}else{
			WorkflowService.updateProcessInstanceEndState(processId,"TT进口货物付款申请通过");
			PaymentImportsInfoJdbcService servie =(PaymentImportsInfoJdbcService)EasyApplicationContextUtils.
					getBeanByName("paymentImportsInfoJdbcService");
			String negotiating = (String) context.getContextInstance().getVariable("_negotiating_workflow_process_name");
			String flag = "false";
			if("foreign_trade_LC_DP_DA_pay".equals(negotiating)){
				flag = "true";
			}
			servie.updateBusinessRecord(businessRecordId, ProcessCallBack.EXAMINE_SUCCESSFUL, flag);
		}
		context.getProcessInstance().signal();
	}

}
