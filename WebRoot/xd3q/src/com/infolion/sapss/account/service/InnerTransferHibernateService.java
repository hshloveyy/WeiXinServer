package com.infolion.sapss.account.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infolion.platform.component.workflow.WorkflowService;
import com.infolion.platform.core.service.BaseHibernateService;
import com.infolion.platform.util.CodeGenerator;
import com.infolion.platform.util.DateUtils;
import com.infolion.sapss.account.dao.InnerTransferDetailHibernateDao;
import com.infolion.sapss.account.dao.InnerTransferHibernateDao;
import com.infolion.sapss.account.domain.TInnerTransferDetail;
import com.infolion.sapss.account.domain.TInnerTransferInfo;
import com.infolion.sapss.workflow.ProcessCallBack;

@Service
public class InnerTransferHibernateService extends BaseHibernateService
		implements ProcessCallBack {
	@Autowired
	private InnerTransferHibernateDao innerTransferHibernateDao;

	public void setInnerTransferHibernateDao(
			InnerTransferHibernateDao innerTransferHibernateDao) {
		this.innerTransferHibernateDao = innerTransferHibernateDao;
	}

	@Autowired
	private InnerTransferDetailHibernateDao innerTransferDetailHibernateDao;

	public void setInnerTransferDetailHibernateDao(
			InnerTransferDetailHibernateDao innerTransferDetailHibernateDao) {
		this.innerTransferDetailHibernateDao = innerTransferDetailHibernateDao;
	}

	public TInnerTransferInfo getTInnerTransferInfo(String transferID) {
		return this.innerTransferHibernateDao.get(transferID);
	}

	public void updateTInnerTransferInfo(TInnerTransferInfo info) {
		this.innerTransferHibernateDao.update(info);
	}

	public void updateBusinessRecord(String businessRecordId,
			String examineResult, String sapOrderNo) {
		String result = "";
		if ("pass".equals(examineResult))
			result = "3";
		else
			result = "4";
		TInnerTransferInfo info = this.getTInnerTransferInfo(businessRecordId);
		info.setApproveState(result);
		info.setApprovedTime(DateUtils
				.getCurrTime(DateUtils.HYPHEN_DISPLAY_DATE));
		this.updateTInnerTransferInfo(info);
	}

	public String saveTInnerTransferInfo(TInnerTransferInfo info) {
		// String id = CodeGenerator.getUUID();
		// info.setTransferID(id);
		this.innerTransferHibernateDao.save(info);
		// return id;
		return info.getTransferID();
	}

	public boolean deleteTInnerTransferInfo(String transferID) {
		TInnerTransferInfo info = this.getTInnerTransferInfo(transferID);
		if (info.getApproveState().equals("1")) {
			if (info.getIsAvailable().equals("1")) {
				info.setIsAvailable("0");
				this.updateTInnerTransferInfo(info);
				return true;
			}
		}
		return false;
	}

	public void submitWorkflow(String taskId, TInnerTransferInfo info) {
		if (null == taskId || "".equals(taskId)) {
			WorkflowService.createAndSignalProcessInstance(info, info
					.getTransferID());
			info.setApplyTime(DateUtils
					.getCurrTime(DateUtils.HYPHEN_DISPLAY_DATE));
			info.setApproveState("2");
			this.updateTInnerTransferInfo(info);
		} else {
			WorkflowService.signalProcessInstance(info, info.getTransferID(),
					null);
		}
	}

	public void saveTInnerTransferDetail(TInnerTransferDetail info) {
		this.innerTransferDetailHibernateDao.save(info);
	}

	public void updateTInnerTransferDetail(TInnerTransferDetail info) {
		this.innerTransferDetailHibernateDao.update(info);
	}

	public TInnerTransferDetail getTInnerTransferDetail(String detailID) {
		return this.innerTransferDetailHibernateDao.get(detailID);
	}

	public boolean deleteTInnerTransferDetail(String idList, String transferID) {
		TInnerTransferInfo info = this.getTInnerTransferInfo(transferID);
		if (info.getApproveState().equals("1")) {
			String[] idArr = idList.split("\\|");
			int len = idArr.length;
			for (int i = 0; i < len; i++) {
				TInnerTransferDetail detail = this
						.getTInnerTransferDetail(idArr[i]);
				if (detail.getIsAvailable().equals("1")) {
					detail.setIsAvailable("0");
					this.updateTInnerTransferDetail(detail);
				}
			}
			return true;
		}
		return false;
	}
}
