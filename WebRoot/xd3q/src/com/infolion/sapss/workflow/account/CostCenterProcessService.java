package com.infolion.sapss.workflow.account;

import org.springframework.stereotype.Service;

import com.infolion.platform.component.workflow.WorkflowService;
import com.infolion.platform.core.service.BaseHibernateService;
import com.infolion.platform.util.EasyApplicationContextUtils;
import com.infolion.sapss.account.service.CostCenterHibernateService;
import com.infolion.sapss.workflow.ProcessCallBack;

@Service
public class CostCenterProcessService extends BaseHibernateService {
	public void processSucessful(String businessRecordId, long processId) {
		WorkflowService.updateProcessInstanceEndState(processId, "成本中心申请通过");
		CostCenterHibernateService service = (CostCenterHibernateService) EasyApplicationContextUtils
				.getBeanByName("costCenterHibernateService");
		ProcessCallBack callBack = (ProcessCallBack) service;
		callBack.updateBusinessRecord(businessRecordId,
				ProcessCallBack.EXAMINE_SUCCESSFUL, null);
	}

	public void processFaile(String businessRecordId, long processId) {
		WorkflowService.updateProcessInstanceEndState(processId, "成本中心申请未通过");
		CostCenterHibernateService service = (CostCenterHibernateService) EasyApplicationContextUtils
				.getBeanByName("costCenterHibernateService");
		ProcessCallBack callBack = (ProcessCallBack) service;
		callBack.updateBusinessRecord(businessRecordId,
				ProcessCallBack.EXAMINE_FAILE, null);
	}
}
