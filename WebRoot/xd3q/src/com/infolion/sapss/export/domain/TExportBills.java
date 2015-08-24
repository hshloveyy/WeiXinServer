package com.infolion.sapss.export.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.infolion.platform.core.domain.ProcessObject;

/**
 * TExportBills entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_EXPORT_BILLS")
public class TExportBills extends ProcessObject {

	// Fields

	private String exportBillId;
	private String exportBillNo;
	private String exportBillExamId;
	private Double realMoney;
	private String cmd;
	private String deptId;
	private String examineState;
	private String applyTime;
	private String approvedTime;
	private String isAvailable;
	private String creatorTime;
	private String creator;
	private Double applyMoney;
	private String currency;
	private String bank;
	
	// Constructors

	/** default constructor */
	public TExportBills() {
	}

	/** minimal constructor */
	public TExportBills(String exportBillId) {
		this.exportBillId = exportBillId;
	}

	/** full constructor */
	public TExportBills(String exportBillId, String exportBillNo,
			String exportBillExamId, Double realMoney, String cmd,
			String deptId, String examineState, String applyTime,
			String approvedTime, String isAvailable, String creatorTime,
			String creator, Double applyMoney, String currency,String bank) {
		this.exportBillId = exportBillId;
		this.exportBillNo = exportBillNo;
		this.exportBillExamId = exportBillExamId;
		this.realMoney = realMoney;
		this.cmd = cmd;
		this.deptId = deptId;
		this.examineState = examineState;
		this.applyTime = applyTime;
		this.approvedTime = approvedTime;
		this.isAvailable = isAvailable;
		this.creatorTime = creatorTime;
		this.creator = creator;
		this.applyMoney = applyMoney;
		this.currency = currency;
		this.bank = bank;
		
	}

	// Property accessors
	@Id
	@Column(name = "EXPORT_BILL_ID", unique = true, nullable = false, length = 36)
	public String getExportBillId() {
		return this.exportBillId;
	}

	public void setExportBillId(String exportBillId) {
		this.exportBillId = exportBillId;
	}

	@Column(name = "EXPORT_BILL_NO", length = 36)
	public String getExportBillNo() {
		return this.exportBillNo;
	}

	public void setExportBillNo(String exportBillNo) {
		this.exportBillNo = exportBillNo;
	}

	@Column(name = "EXPORT_BILL_EXAM_ID", length = 36)
	public String getExportBillExamId() {
		return this.exportBillExamId;
	}

	public void setExportBillExamId(String exportBillExamId) {
		this.exportBillExamId = exportBillExamId;
	}

	@Column(name = "REAL_MONEY", precision = 20)
	public Double getRealMoney() {
		return this.realMoney;
	}

	public void setRealMoney(Double realMoney) {
		this.realMoney = realMoney;
	}
	
	@Column(name = "APPLY_MONEY", precision = 20)
	public Double getApplyMoney() {
		return this.applyMoney;
	}

	public void setApplyMoney(Double applyMoney) {
		this.applyMoney = applyMoney;
	}

	@Column(name = "CURRENCY", length = 50)
	public String getCurrency() {
		return this.currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}
	
	@Column(name = "BANK", length = 50)
	public String getBank() {
		return this.bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}
	
	@Column(name = "CMD", length = 2000)
	public String getCmd() {
		return this.cmd;
	}

	public void setCmd(String cmd) {
		this.cmd = cmd;
	}

	@Column(name = "DEPT_ID", length = 36)
	public String getDeptId() {
		return this.deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	@Column(name = "EXAMINE_STATE", length = 2)
	public String getExamineState() {
		return this.examineState;
	}

	public void setExamineState(String examineState) {
		this.examineState = examineState;
	}

	@Column(name = "APPLY_TIME", length = 20)
	public String getApplyTime() {
		return this.applyTime;
	}

	public void setApplyTime(String applyTime) {
		this.applyTime = applyTime;
	}

	@Column(name = "APPROVED_TIME", length = 20)
	public String getApprovedTime() {
		return this.approvedTime;
	}

	public void setApprovedTime(String approvedTime) {
		this.approvedTime = approvedTime;
	}

	@Column(name = "IS_AVAILABLE", length = 1)
	public String getIsAvailable() {
		return this.isAvailable;
	}

	public void setIsAvailable(String isAvailable) {
		this.isAvailable = isAvailable;
	}

	@Column(name = "CREATOR_TIME", length = 20)
	public String getCreatorTime() {
		return this.creatorTime;
	}

	public void setCreatorTime(String creatorTime) {
		this.creatorTime = creatorTime;
	}

	@Column(name = "CREATOR", length = 36)
	public String getCreator() {
		return this.creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}
	

	@Override
	public void setWorkflowModelId() {
		// TODO Auto-generated method stub
		this.workflowModelId = "1008";
	}

	@Override
	public void setWorkflowModelName() {
		this.workflowModelName = "出口押汇申请";
	}

	@Override
	public void setWorkflowProcessName() {
//		this.workflowProcessName = "export_negotiating_v1";
	}

	@Override
	public void setWorkflowProcessUrl() {
		this.workflowProcessUrl = "exportController.spr?action=exportBillsExamine";
	}

}