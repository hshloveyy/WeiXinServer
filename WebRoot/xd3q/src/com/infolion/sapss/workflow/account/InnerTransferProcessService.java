package com.infolion.sapss.workflow.account;

import org.springframework.stereotype.Service;

import com.infolion.platform.component.workflow.WorkflowService;
import com.infolion.platform.core.service.BaseHibernateService;
import com.infolion.platform.util.EasyApplicationContextUtils;
import com.infolion.sapss.account.service.InnerTransferHibernateService;
import com.infolion.sapss.workflow.ProcessCallBack;

@Service
public class InnerTransferProcessService extends BaseHibernateService {
	public void processSucessful(String businessRecordId, long processId) {
		WorkflowService.updateProcessInstanceEndState(processId, "利润中心申请通过");
		InnerTransferHibernateService service = (InnerTransferHibernateService) EasyApplicationContextUtils
				.getBeanByName("innerTransferHibernateService");
		ProcessCallBack callBack = (ProcessCallBack) service;
		callBack.updateBusinessRecord(businessRecordId,
				ProcessCallBack.EXAMINE_SUCCESSFUL, null);
	}

	public void processFaile(String businessRecordId, long processId) {
		WorkflowService.updateProcessInstanceEndState(processId, "利润中心申请未通过");
		InnerTransferHibernateService service = (InnerTransferHibernateService) EasyApplicationContextUtils
				.getBeanByName("innerTransferHibernateService");
		ProcessCallBack callBack = (ProcessCallBack) service;
		callBack.updateBusinessRecord(businessRecordId,
				ProcessCallBack.EXAMINE_FAILE, null);
	}
}
