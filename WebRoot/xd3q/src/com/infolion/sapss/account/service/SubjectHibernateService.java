package com.infolion.sapss.account.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.infolion.platform.component.workflow.WorkflowService;
import com.infolion.platform.core.service.BaseHibernateService;
import com.infolion.platform.util.CodeGenerator;
import com.infolion.platform.util.DateUtils;
import com.infolion.sapss.account.dao.SubjectInfoHibernateDao;
import com.infolion.sapss.account.domain.TSubjectInfo;
import com.infolion.sapss.workflow.ProcessCallBack;

@Service
public class SubjectHibernateService extends BaseHibernateService implements
		ProcessCallBack {
	@Autowired
	private SubjectInfoHibernateDao subjectInfoHibernate;

	public void setSubjectInfoHibernate(
			SubjectInfoHibernateDao subjectInfoHibernate) {
		this.subjectInfoHibernate = subjectInfoHibernate;
	}

	@Transactional(readOnly = true)
	public String saveSubjectInfo(TSubjectInfo info) {
		String id = CodeGenerator.getUUID();
		info.setSubjectID(id);
		this.subjectInfoHibernate.save(info);
		return id;
	}

	public void updateSubjectInfo(TSubjectInfo info) {
		this.subjectInfoHibernate.update(info);
	}

	public TSubjectInfo getTSubjectInfo(String subjectID) {
		return this.subjectInfoHibernate.get(subjectID);
	}

	public boolean deleteSubjectInfo(String subjectID) {
		TSubjectInfo info = this.getTSubjectInfo(subjectID);
		if (info.getApproveState().equals("1")) {
			if (info.getIsAvailable().equals("1")) {
				info.setIsAvailable("0");
				this.updateSubjectInfo(info);
				return true;
			}
		}
		return false;
	}

	public void updateBusinessRecord(String businessRecordId,
			String examineResult, String sapOrderNo) {
		String result = "";
		if ("pass".equals(examineResult))
			result = "3";
		else
			result = "4";
		TSubjectInfo info = this.getTSubjectInfo(businessRecordId);
		info.setApproveState(result);
		info.setApprovedTime(DateUtils
				.getCurrTime(DateUtils.HYPHEN_DISPLAY_DATE));
		this.updateSubjectInfo(info);
	}

	public void submitWorkflow(String taskId, TSubjectInfo info) {
		if (null == taskId || "".equals(taskId)) {
			WorkflowService.createAndSignalProcessInstance(info, info
					.getSubjectID());
			info.setApplyTime(DateUtils
					.getCurrTime(DateUtils.HYPHEN_DISPLAY_DATE));
			info.setApproveState("2");
			this.updateSubjectInfo(info);
		} else {
			WorkflowService.signalProcessInstance(info, info.getSubjectID(),
					null);
		}
	}
}
