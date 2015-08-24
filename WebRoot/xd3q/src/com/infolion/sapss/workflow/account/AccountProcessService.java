package com.infolion.sapss.workflow.account;

import org.springframework.stereotype.Service;

import com.infolion.platform.component.workflow.WorkflowService;
import com.infolion.platform.core.service.BaseHibernateService;
import com.infolion.platform.util.EasyApplicationContextUtils;
import com.infolion.sapss.account.service.AccountHibernateService;
import com.infolion.sapss.workflow.ProcessCallBack;

@Service
public class AccountProcessService extends BaseHibernateService {
	public void processSucessful(String businessRecordId, long processId) {
		WorkflowService.updateProcessInstanceEndState(processId, "银行主数据申请通过");
		AccountHibernateService service = (AccountHibernateService) EasyApplicationContextUtils
				.getBeanByName("accountHibernateService");
		ProcessCallBack callBack = (ProcessCallBack) service;
		callBack.updateBusinessRecord(businessRecordId,
				ProcessCallBack.EXAMINE_SUCCESSFUL, null);
	}

	public void processFaile(String businessRecordId, long processId) {
		WorkflowService.updateProcessInstanceEndState(processId, "银行主数据申请未通过");
		AccountHibernateService service = (AccountHibernateService) EasyApplicationContextUtils
				.getBeanByName("accountHibernateService");
		ProcessCallBack callBack = (ProcessCallBack) service;
		callBack.updateBusinessRecord(businessRecordId,
				ProcessCallBack.EXAMINE_FAILE, null);
	}
}
