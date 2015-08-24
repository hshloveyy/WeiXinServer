package com.infolion.sapss.account.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infolion.platform.component.workflow.WorkflowService;
import com.infolion.platform.core.service.BaseHibernateService;
import com.infolion.platform.util.CodeGenerator;
import com.infolion.platform.util.DateUtils;
import com.infolion.sapss.account.dao.AccountHibernateDao;
import com.infolion.sapss.account.domain.TAccountInfo;
import com.infolion.sapss.workflow.ProcessCallBack;

@Service
public class AccountHibernateService extends BaseHibernateService implements
		ProcessCallBack {
	@Autowired
	private AccountHibernateDao accountHibernateDao;

	public void setAccountHibernateDao(AccountHibernateDao accountHibernateDao) {
		this.accountHibernateDao = accountHibernateDao;
	}

	public TAccountInfo getTAccountInfo(String accountID) {
		return this.accountHibernateDao.get(accountID);
	}

	public void updateAccountInfo(TAccountInfo info) {
		this.accountHibernateDao.update(info);
	}

	public void updateBusinessRecord(String businessRecordId,
			String examineResult, String sapOrderNo) {
		String result = "";
		if ("pass".equals(examineResult))
			result = "3";
		else
			result = "4";
		TAccountInfo info = this.getTAccountInfo(businessRecordId);
		info.setApproveState(result);
		info.setApprovedTime(DateUtils
				.getCurrTime(DateUtils.HYPHEN_DISPLAY_DATE));
		this.updateAccountInfo(info);
	}

	public String saveAccountInfo(TAccountInfo info) {
		String id = CodeGenerator.getUUID();
		info.setAccountID(id);
		this.accountHibernateDao.save(info);
		return id;
	}

	public boolean deleteAccountInfo(String accountID) {
		TAccountInfo info = this.getTAccountInfo(accountID);
		if (info.getApproveState().equals("1")) {
			if (info.getIsAvailable().equals("1")) {
				info.setIsAvailable("0");
				this.updateAccountInfo(info);
				return true;
			}
		}
		return false;
	}

	public void submitWorkflow(String taskId, TAccountInfo info) {
		if (null == taskId || "".equals(taskId)) {
			WorkflowService.createAndSignalProcessInstance(info, info
					.getAccountID());
			info.setApplyTime(DateUtils
					.getCurrTime(DateUtils.HYPHEN_DISPLAY_DATE));
			info.setApproveState("2");
			this.updateAccountInfo(info);
		} else {
			WorkflowService.signalProcessInstance(info, info.getAccountID(),
					null);
		}
	}
}
