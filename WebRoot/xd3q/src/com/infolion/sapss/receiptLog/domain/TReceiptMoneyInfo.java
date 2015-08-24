package com.infolion.sapss.receiptLog.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.infolion.platform.core.domain.ProcessObject;
import com.infolion.sapss.common.WorkflowUtils;

/**
 * TReceiptMoneyInfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_RECEIPT_MONEY_INFO")
public class TReceiptMoneyInfo extends ProcessObject {

	// Fields

	private String infoId;
	private String contractId;
	private String contractNo;
	private String receiptDate;
	private String realReceiptDate;
	private String receiptTotal;
	private String realReceiptTotal;
	private String receiptBalance;
	private String saleContractStaus;
	private String innerReceiptTotal;
	private String innerReceiptBalance;
	private String innerReceiptDuty;
	private String innerReceiptFee;
	private String createTime;
	private String creator;
	private String isAvailable;
	private String deptId;
	private String remark;
	private String tradeType;
	private String examineState;
	private String applyTime;
	private String approvedTime; // 审批通过时间
	private String projectId;
	private String projectNo;
	private String use;//用途

	// Constructors

	/** default constructor */
	public TReceiptMoneyInfo() {
	}

	/** minimal constructor */
	public TReceiptMoneyInfo(String infoId) {
		this.infoId = infoId;
	}

	/** full constructor */
	public TReceiptMoneyInfo(String infoId, String contractId,
			String contractNo, String receiptDate, String realReceiptDate,
			String receiptTotal, String realReceiptTotal,
			String receiptBalance, String saleContractStaus,
			String innerReceiptTotal, String innerReceiptBalance,
			String innerReceiptDuty, String innerReceiptFee, String createTime,
			String creator, String isAvailable, String deptId) {
		this.infoId = infoId;
		this.contractId = contractId;
		this.contractNo = contractNo;
		this.receiptDate = receiptDate;
		this.realReceiptDate = realReceiptDate;
		this.receiptTotal = receiptTotal;
		this.realReceiptTotal = realReceiptTotal;
		this.receiptBalance = receiptBalance;
		this.saleContractStaus = saleContractStaus;
		this.innerReceiptTotal = innerReceiptTotal;
		this.innerReceiptBalance = innerReceiptBalance;
		this.innerReceiptDuty = innerReceiptDuty;
		this.innerReceiptFee = innerReceiptFee;
		this.createTime = createTime;
		this.creator = creator;
		this.isAvailable = isAvailable;
		this.deptId = deptId;
	}

	// Property accessors
	@Id
	@Column(name = "INFO_ID", unique = true, nullable = false, length = 36)
	public String getInfoId() {
		return this.infoId;
	}

	public void setInfoId(String infoId) {
		this.infoId = infoId;
	}

	@Column(name = "CONTRACT_ID", length = 36)
	public String getContractId() {
		return this.contractId;
	}

	public void setContractId(String contractId) {
		this.contractId = contractId;
	}

	@Column(name = "CONTRACT_NO", length = 35)
	public String getContractNo() {
		return this.contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	@Column(name = "RECEIPT_DATE", length = 20)
	public String getReceiptDate() {
		return this.receiptDate;
	}

	public void setReceiptDate(String receiptDate) {
		this.receiptDate = receiptDate;
	}

	@Column(name = "REAL_RECEIPT_DATE", length = 20)
	public String getRealReceiptDate() {
		return this.realReceiptDate;
	}

	public void setRealReceiptDate(String realReceiptDate) {
		this.realReceiptDate = realReceiptDate;
	}

	@Column(name = "RECEIPT_TOTAL", length = 36)
	public String getReceiptTotal() {
		return this.receiptTotal;
	}

	public void setReceiptTotal(String receiptTotal) {
		this.receiptTotal = receiptTotal;
	}

	@Column(name = "REAL_RECEIPT_TOTAL", length = 36)
	public String getRealReceiptTotal() {
		return this.realReceiptTotal;
	}

	public void setRealReceiptTotal(String realReceiptTotal) {
		this.realReceiptTotal = realReceiptTotal;
	}

	@Column(name = "RECEIPT_BALANCE", length = 36)
	public String getReceiptBalance() {
		return this.receiptBalance;
	}

	public void setReceiptBalance(String receiptBalance) {
		this.receiptBalance = receiptBalance;
	}

	@Column(name = "SALE_CONTRACT_STAUS", length = 36)
	public String getSaleContractStaus() {
		return this.saleContractStaus;
	}

	public void setSaleContractStaus(String saleContractStaus) {
		this.saleContractStaus = saleContractStaus;
	}

	@Column(name = "INNER_RECEIPT_TOTAL", length = 20)
	public String getInnerReceiptTotal() {
		return this.innerReceiptTotal;
	}

	public void setInnerReceiptTotal(String innerReceiptTotal) {
		this.innerReceiptTotal = innerReceiptTotal;
	}

	@Column(name = "INNER_RECEIPT_BALANCE", length = 20)
	public String getInnerReceiptBalance() {
		return this.innerReceiptBalance;
	}

	public void setInnerReceiptBalance(String innerReceiptBalance) {
		this.innerReceiptBalance = innerReceiptBalance;
	}

	@Column(name = "INNER_RECEIPT_DUTY", length = 20)
	public String getInnerReceiptDuty() {
		return this.innerReceiptDuty;
	}

	public void setInnerReceiptDuty(String innerReceiptDuty) {
		this.innerReceiptDuty = innerReceiptDuty;
	}

	@Column(name = "INNER_RECEIPT_FEE", length = 20)
	public String getInnerReceiptFee() {
		return this.innerReceiptFee;
	}

	public void setInnerReceiptFee(String innerReceiptFee) {
		this.innerReceiptFee = innerReceiptFee;
	}

	@Column(name = "CREATE_TIME", length = 20)
	public String getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	@Column(name = "CREATOR", length = 36)
	public String getCreator() {
		return this.creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	@Column(name = "IS_AVAILABLE", length = 1)
	public String getIsAvailable() {
		return this.isAvailable;
	}

	public void setIsAvailable(String isAvailable) {
		this.isAvailable = isAvailable;
	}

	@Column(name = "DEPT_ID", length = 36)
	public String getDeptId() {
		return this.deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	@Column(name = "REMARK", length = 36)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Column(name = "TRADE_TYPE", length = 2)
	public String getTradeType() {
		return tradeType;
	}

	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}
	@Column(name = "EXAMINE_STATE", length = 2)
	public String getExamineState() {
		return examineState;
	}

	public void setExamineState(String examineState) {
		this.examineState = examineState;
	}
	@Column(name = "Apply_Time", length = 20)
	public String getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(String applyTime) {
		this.applyTime = applyTime;
	}
	@Column(name = "approved_Time", length = 20)
	public String getApprovedTime() {
		return approvedTime;
	}

	public void setApprovedTime(String approvedTime) {
		this.approvedTime = approvedTime;
	}
	@Override
	public void setWorkflowModelId() {
		// TODO Auto-generated method stub
		this.workflowModelId = "1007";
	}

	@Override
	public void setWorkflowModelName() {
		this.workflowModelName = "收款审核";

	}

	@Override
	public void setWorkflowProcessName() {
		// this.workflowProcessName = processName;
		 this.workflowProcessName = WorkflowUtils.chooseWorkflowName("receiver_v1");
	}

	@Override
	public void setWorkflowProcessUrl() {
		this.workflowProcessUrl = "receiptMoneyController.spr?action=receiptsExamine";

	}
	@Column(name = "project_id", length = 36)
	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	@Column(name = "project_no", length = 100)
	public String getProjectNo() {
		return projectNo;
	}

	public void setProjectNo(String projectNo) {
		this.projectNo = projectNo;
	}
	@Column(name = "use", length = 500)
	public String getUse() {
		return use;
	}

	public void setUse(String use) {
		this.use = use;
	}
	


}