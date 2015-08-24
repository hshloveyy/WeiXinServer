package com.infolion.sapss.interestBill.service;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infolion.platform.component.workflow.WorkflowService;
import com.infolion.platform.component.workflow.ext.TaskInstanceContext;
import com.infolion.platform.console.sys.context.UserContext;
import com.infolion.platform.console.sys.context.UserContextHolder;
import com.infolion.platform.core.service.BaseService;
import com.infolion.platform.util.DateUtils;
import com.infolion.sapss.common.WorkflowUtils;
import com.infolion.sapss.interestBill.dao.InterestBillHibernateDao;
import com.infolion.sapss.interestBill.dao.InterestBillJdbcDao;
import com.infolion.sapss.interestBill.domain.TInterestBill;
import com.infolion.sapss.workflow.ProcessCallBack;

@Service
public class InterestBillService extends BaseService{
	
	@Autowired
	private InterestBillHibernateDao interestBillHibernateDao;
	@Autowired
	private InterestBillJdbcDao interestBillJdbcDao;
	public void setInterestBillHibernateDao(
			InterestBillHibernateDao interestBillHibernateDao) {
		this.interestBillHibernateDao = interestBillHibernateDao;
	}
	public void setInterestBillJdbcDao(InterestBillJdbcDao interestBillJdbcDao) {
		this.interestBillJdbcDao = interestBillJdbcDao;
	}
	
	
	public void saveOrUpdateInterestBill(TInterestBill bill){
		interestBillHibernateDao.saveOrUpdate(bill);
	}
	
	public TInterestBill getInterestBill(String id){
		return interestBillHibernateDao.get(id);
	}
	
	public String getInterestBillNo(){
		return interestBillJdbcDao.getBillApplyNo();
	}
	
	public void submitAndSaveBillApply(String taskId, TInterestBill bill) {
		bill.setExamState("2");
		bill.setApplyTime(DateUtils.getCurrTime(2));
		this.saveOrUpdateInterestBill(bill);
		this.submitBillApply(taskId, bill);
	}
	
	public void submitBillApply(String taskId, TInterestBill bill) {
		bill.setWorkflowProcessName(WorkflowUtils
				.chooseWorkflowName("interest_bill_v1"));

		UserContext userContext = UserContextHolder.getLocalUserContext()
		.getUserContext();
		String workflowBusinessNote = userContext.getSysDept().getDeptname()
		+ "|" + userContext.getSysUser().getRealName() + "|";
		workflowBusinessNote = workflowBusinessNote + "利息票开票申请单号："
		+ bill.getInterestBillNo();

		bill.setWorkflowBusinessNote(workflowBusinessNote);
		if (StringUtils.isBlank(taskId)) {
			WorkflowService.createAndSignalProcessInstance(bill,
					bill.getInterestBillId());
		} else {
			WorkflowService.signalProcessInstance(bill, bill.getInterestBillId(), null);
		}
	}
	
	public String verifyFilds(String taskId, TInterestBill tBillApply) {
		String taskName = "";
		String rtn = "";
		if (!StringUtils.isEmpty(taskId)) {
			TaskInstanceContext taskInstanceContext = WorkflowService
					.getTaskInstanceContext(taskId);
			taskName = taskInstanceContext.getTaskName();
		}
		if (taskName.indexOf("录入凭证号")>=0) {
			if (StringUtils.isBlank(tBillApply.getSapReturnNo())) {
				rtn = "请录入SAP开票凭证号！";
				return rtn;
			}
			else if (StringUtils.isBlank(tBillApply.getTaxNo())) {
				rtn = "请录入发票号！";
				return rtn;
			}
			TInterestBill bill = getInterestBill(tBillApply.getInterestBillId());
			bill.setSapReturnNo(tBillApply.getSapReturnNo());
			bill.setTaxNo(tBillApply.getTaxNo());
			saveOrUpdateInterestBill(bill);
		}
		return rtn;
	}
	
	public void processBillApplySucessful(String businessRecordId, long processId)
	{
		WorkflowService.updateProcessInstanceEndState(processId, "申请通过");
		this.updateBusinessRecord(businessRecordId,
				ProcessCallBack.EXAMINE_SUCCESSFUL, null);
	}

	/**
	 * 开票申请出口押汇流程审批不通过
	 * 
	 * @param businessRecordId
	 * @param processId
	 */
	public void processBillApplyFaile(String businessRecordId, long processId)
	{
		WorkflowService.updateProcessInstanceEndState(processId, "申请未通过");
		this.updateBusinessRecord(businessRecordId,
				ProcessCallBack.EXAMINE_FAILE, null);
	}
	
	public void updateBusinessRecord(String businessRecordId,
			String examineResult, String sapOrderNo) {
		String examineState = "";
		if (ProcessCallBack.EXAMINE_SUCCESSFUL.equals(examineResult)) {
			examineState = "3";
		} else if (ProcessCallBack.NO_EXAMINE.equals(examineResult)) {
			examineState = "5";
		} else {
			examineState = "4";
		}
		TInterestBill tBillApply = this.getInterestBill(businessRecordId);
		tBillApply.setExamState(examineState);
		tBillApply.setApprovedTime(DateUtils
				.getCurrTime(DateUtils.HYPHEN_DISPLAY_DATE));
		this.saveOrUpdateInterestBill(tBillApply);
	}
	
	public String checkValue(String unitNo,double currenValue){
		if(isInnerCompany(unitNo)) return null;
		double totalValue = interestBillJdbcDao.queryTotalValue();
		if(currenValue+totalValue>4500000)
			return "当年累计申请金额超过450万！可用余额为"+String.valueOf(4500000-totalValue);
		return null;
	}
	
	/**判断是否内部单位***/
	private boolean isInnerCompany(String id){
		boolean yes_no = false;
		if(id!=null&& !"".equals(id.trim())){
			String mark = id.trim().substring(2,3);
			if(mark.equals("1")) return true;
		}
		return yes_no;
	}

}
