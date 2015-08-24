package com.infolion.sapss.account.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infolion.platform.component.workflow.WorkflowService;
import com.infolion.platform.core.service.BaseHibernateService;
import com.infolion.platform.util.DateUtils;
import com.infolion.sapss.account.dao.ProfitCenterHibernateDao;
import com.infolion.sapss.account.domain.TProfitCenterInfo;
import com.infolion.sapss.workflow.ProcessCallBack;

@Service
public class ProfitCenterHibernateService extends BaseHibernateService
		implements ProcessCallBack {
	@Autowired
	private ProfitCenterHibernateDao profitCenterHibernateDao;

	public void setProfitCenterHibernateDao(
			ProfitCenterHibernateDao profitCenterHibernateDao) {
		this.profitCenterHibernateDao = profitCenterHibernateDao;
	}

	public TProfitCenterInfo getTProfitCenterInfo(String profitID) {
		return this.profitCenterHibernateDao.get(profitID);
	}

	public void updateTProfitCenterInfo(TProfitCenterInfo info) {
		this.profitCenterHibernateDao.update(info);
	}

	public void updateBusinessRecord(String businessRecordId,
			String examineResult, String sapOrderNo) {
		String result = "";
		if ("pass".equals(examineResult))
			result = "3";
		else
			result = "4";
		TProfitCenterInfo info = this.getTProfitCenterInfo(businessRecordId);
		info.setApproveState(result);
		info.setApprovedTime(DateUtils
				.getCurrTime(DateUtils.HYPHEN_DISPLAY_DATE));
		this.updateTProfitCenterInfo(info);
	}

	public void saveTProfitCenterInfo(TProfitCenterInfo info) {
		this.profitCenterHibernateDao.save(info);
	}

	public boolean deleteTProfitCenterInfo(String profitID) {
		TProfitCenterInfo info = this.getTProfitCenterInfo(profitID);
		if (info.getApproveState().equals("1")) {
			if (info.getIsAvailable().equals("1")) {
				info.setIsAvailable("0");
				this.updateTProfitCenterInfo(info);
				return true;
			}
		}
		return false;
	}

	public void submitWorkflow(String taskId, TProfitCenterInfo info) {
		Map parameters = new HashMap();
		parameters
				.put("_workflow_iscs", info.getIsChangeStandard());
		info.setWorkflowUserDefineTaskVariable(parameters);
		if (null == taskId || "".equals(taskId)) {
			info.setApplyTime(DateUtils
					.getCurrTime(DateUtils.HYPHEN_DISPLAY_DATE));
			info.setApproveState("2");
			info.setWorkflowUserDefineProcessVariable(parameters);
			WorkflowService.createAndSignalProcessInstance(info, info
					.getProfitID());
			this.updateTProfitCenterInfo(info);
		} else {
			WorkflowService.signalProcessInstance(info, info.getProfitID(),
					null);
		}
	}
}
