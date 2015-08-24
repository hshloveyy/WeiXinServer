package com.infolion.sapss.workflow.account;

import org.springframework.stereotype.Service;

import com.infolion.platform.component.workflow.WorkflowService;
import com.infolion.platform.core.service.BaseHibernateService;
import com.infolion.platform.util.EasyApplicationContextUtils;
import com.infolion.sapss.account.service.SubjectHibernateService;
import com.infolion.sapss.workflow.ProcessCallBack;

@Service
public class SubjectProcessService extends BaseHibernateService {
	public void processSucessful(String businessRecordId, long processId) {
		WorkflowService.updateProcessInstanceEndState(processId, "会计科目申请通过");
		SubjectHibernateService subject = (SubjectHibernateService) EasyApplicationContextUtils
				.getBeanByName("subjectHibernateService");
		ProcessCallBack callBack = (ProcessCallBack) subject;
		callBack.updateBusinessRecord(businessRecordId,
				ProcessCallBack.EXAMINE_SUCCESSFUL, null);
	}

	public void processFaile(String businessRecordId, long processId) {
		WorkflowService.updateProcessInstanceEndState(processId, "会计科目申请未通过");
		SubjectHibernateService subject = (SubjectHibernateService) EasyApplicationContextUtils
				.getBeanByName("subjectHibernateService");
		ProcessCallBack callBack = (ProcessCallBack) subject;
		callBack.updateBusinessRecord(businessRecordId,
				ProcessCallBack.EXAMINE_FAILE, null);
	}
}
