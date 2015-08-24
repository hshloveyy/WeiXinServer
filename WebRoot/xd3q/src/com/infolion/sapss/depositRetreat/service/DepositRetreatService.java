package com.infolion.sapss.depositRetreat.service;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infolion.platform.component.workflow.WorkflowService;
import com.infolion.platform.console.sys.context.UserContext;
import com.infolion.platform.console.sys.context.UserContextHolder;
import com.infolion.platform.core.service.BaseJdbcService;
import com.infolion.platform.util.DateUtils;
import com.infolion.platform.util.EasyApplicationContextUtils;
import com.infolion.sapss.common.WorkflowUtils;
import com.infolion.sapss.depositRetreat.dao.DepositRetreatHibernateDao;
import com.infolion.sapss.depositRetreat.dao.DepositRetreatJdbcDao;
import com.infolion.sapss.depositRetreat.domain.TDepositRetreat;
import com.infolion.sapss.export.domain.TBaleLoan;
import com.infolion.sapss.export.domain.TExportBillExam;
import com.infolion.sapss.export.domain.TExportBills;
import com.infolion.sapss.export.service.BaleLoanService;
import com.infolion.sapss.workflow.ProcessCallBack;

@Service
public class DepositRetreatService extends BaseJdbcService implements ProcessCallBack{
	@Autowired
	private DepositRetreatJdbcDao depositRetreatJdbcDao;
	
	@Autowired
	private DepositRetreatHibernateDao depositRetreatHibernateDao;

	public void setDepositRetreatJdbcDao(DepositRetreatJdbcDao depositRetreatJdbcDao)
	{
		this.depositRetreatJdbcDao = depositRetreatJdbcDao;
	}

	public void setDepositRetreatHibernateDao(
			DepositRetreatHibernateDao depositRetreatHibernateDao) {
		this.depositRetreatHibernateDao = depositRetreatHibernateDao;
	}
	
	
	public TDepositRetreat getDepositRetreat(String depositRetreatId){
		return depositRetreatHibernateDao.get(depositRetreatId);
	}
	
	public void saveDepositRetreat(TDepositRetreat depositRetreat){
	    this.depositRetreatHibernateDao.saveOrUpdate(depositRetreat);
	}
	
	public void submitAndSaveDepositRetreat(String taskId,
			TDepositRetreat depositRetreat) {
		depositRetreat.setWorkflowProcessName("deposit_retreat_v1");	
		UserContext userContext = UserContextHolder.getLocalUserContext()
				.getUserContext();
		depositRetreat.setExamState("2");
		depositRetreat.setApplyTime(DateUtils.getCurrDate(2));
		this.saveDepositRetreat(depositRetreat);
		
		String workflowBusinessNote = userContext.getSysDept().getDeptname()
				+ "|" + userContext.getSysUser().getRealName() + "|";
		workflowBusinessNote = workflowBusinessNote + "保证金退回";
		
		depositRetreat.setWorkflowBusinessNote(workflowBusinessNote);

		this.submitDepositRetreat(taskId, depositRetreat);
	}
	
	public void submitDepositRetreat(String taskId, TDepositRetreat depositRetreat) {
		Map parameters = new HashMap();
		depositRetreat.setWorkflowProcessName("deposit_retreat_v1");
		parameters.put("applyAccount",depositRetreat.getApplyAccount());
		parameters.put("receiptAccount", depositRetreat.getReceiptAccount());
		
		depositRetreat.setWorkflowUserDefineTaskVariable(parameters);

		if (null == taskId || "".equals(taskId)) {
			WorkflowService.createAndSignalProcessInstance(depositRetreat,
					depositRetreat.getDepositRetreatId());
			depositRetreat.setExamState("2");
			this.saveDepositRetreat(depositRetreat);
		} else {
			WorkflowService.signalProcessInstance(depositRetreat, depositRetreat
					.getDepositRetreatId(), null);
		}
	}
	
	public JSONObject submitDepositRetreatWorkflow(TDepositRetreat depositRetreat, String taskId) {
		JSONObject jo = new JSONObject();
		String msg = "";
		try {
			submitDepositRetreat(taskId, depositRetreat);
		} catch (Exception e) {
			msg = "异常类:" + e.getClass().getName() + "<br>异常信息:"
					+ e.getMessage();
			e.printStackTrace();
		}

		if ("".equals(msg))
			jo.put("returnMessage", "提交审批成功！  ");
		else
			jo.put("returnMessage", msg);

		return jo;
	}
	
	public void processDepositRetreatSucessful(String businessRecordId, long processId)
	{
		WorkflowService.updateProcessInstanceEndState(processId, "申请通过");
		DepositRetreatService depositRetreatService = (DepositRetreatService) EasyApplicationContextUtils
				.getBeanByName("depositRetreatService");
		ProcessCallBack callBack = (ProcessCallBack) depositRetreatService;
		callBack.updateBusinessRecord(businessRecordId,
				ProcessCallBack.EXAMINE_SUCCESSFUL, null);
	}

	/**
	 * 打包贷款流程审批不通过
	 * 
	 * @param businessRecordId
	 * @param processId
	 */
	public void processDepositRetreatFaile(String businessRecordId, long processId)
	{
		WorkflowService.updateProcessInstanceEndState(processId, "申请未通过");
		DepositRetreatService depositRetreatService = (DepositRetreatService) EasyApplicationContextUtils
				.getBeanByName("depositRetreatService");
		ProcessCallBack callBack = (ProcessCallBack) depositRetreatService;
		callBack.updateBusinessRecord(businessRecordId,
				ProcessCallBack.EXAMINE_FAILE, null);
	}

	public void updateBusinessRecord(String businessRecordId,
			String examineResult, String sapOrderNo) {
		TDepositRetreat depositRetreat = this.getDepositRetreat(businessRecordId);
		if (examineResult.equals(this.EXAMINE_SUCCESSFUL))
		{
			depositRetreat.setExamState("3");

		}
		else if (examineResult.equals(this.EXAMINE_FAILE))
		{
			depositRetreat.setExamState("4");
		}
		depositRetreat.setApplyedTime(DateUtils.getCurrTime(2));
		this.saveDepositRetreat(depositRetreat);
		
	}
}
