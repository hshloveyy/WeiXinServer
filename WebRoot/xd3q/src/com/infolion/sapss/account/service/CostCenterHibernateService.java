package com.infolion.sapss.account.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infolion.platform.component.dictionary.DictionaryRow;
import com.infolion.platform.component.workflow.WorkflowService;
import com.infolion.platform.core.service.BaseHibernateService;
import com.infolion.platform.util.DateUtils;
import com.infolion.sapss.account.dao.BaseCodeJdbcDao;
import com.infolion.sapss.account.dao.CostCenterHibernateDao;
import com.infolion.sapss.account.domain.TCostCenterInfo;
import com.infolion.sapss.workflow.ProcessCallBack;

@Service
public class CostCenterHibernateService extends BaseHibernateService implements
		ProcessCallBack {
	@Autowired
	private CostCenterHibernateDao costCenterHibernateDao;
	@Autowired
	private BaseCodeJdbcDao baseCodeJdbcDao;

	public void setCostCenterHibernateDao(
			CostCenterHibernateDao costCenterHibernateDao) {
		this.costCenterHibernateDao = costCenterHibernateDao;
	}

	public void setBaseCodeJdbcDao(BaseCodeJdbcDao baseCodeJdbcDao) {
		this.baseCodeJdbcDao = baseCodeJdbcDao;
	}

	public TCostCenterInfo getTCostCenterInfo(String costID) {
		return this.costCenterHibernateDao.get(costID);
	}

	public void updateTCostCenterInfo(TCostCenterInfo info) {
		this.costCenterHibernateDao.update(info);
	}

	public void updateBusinessRecord(String businessRecordId,
			String examineResult, String sapOrderNo) {
		String result = "";
		if ("pass".equals(examineResult))
			result = "3";
		else
			result = "4";
		TCostCenterInfo info = this.getTCostCenterInfo(businessRecordId);
		info.setApproveState(result);
		info.setApprovedTime(DateUtils
				.getCurrTime(DateUtils.HYPHEN_DISPLAY_DATE));
		this.updateTCostCenterInfo(info);
	}

	public void saveTCostCenterInfo(TCostCenterInfo info) {
		this.costCenterHibernateDao.save(info);
	}

	public boolean deleteTCostCenterInfo(String costID) {
		TCostCenterInfo info = this.getTCostCenterInfo(costID);
		if (info.getApproveState().equals("1")) {
			if (info.getIsAvailable().equals("1")) {
				info.setIsAvailable("0");
				this.updateTCostCenterInfo(info);
				return true;
			}
		}
		return false;
	}

	public void submitWorkflow(String taskId, TCostCenterInfo info) {
		Map parameters = new HashMap();
		parameters.put("_workflow_iscs", info.getIsChangeStandard());
		info.setWorkflowUserDefineTaskVariable(parameters);
		if (null == taskId || "".equals(taskId)) {
			info.setApplyTime(DateUtils
					.getCurrTime(DateUtils.HYPHEN_DISPLAY_DATE));
			info.setApproveState("2");
			// info.setWorkflowUserDefineProcessVariable(parameters);
			WorkflowService.createAndSignalProcessInstance(info, info
					.getCostID());
			this.updateTCostCenterInfo(info);
		} else {
			WorkflowService.signalProcessInstance(info, info.getCostID(), null);
		}
	}

	public String findCostCenterType(String id) {
		return this.baseCodeJdbcDao.findCMD(id);
	}
}
