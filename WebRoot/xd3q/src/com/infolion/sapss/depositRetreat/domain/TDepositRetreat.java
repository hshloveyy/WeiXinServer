package com.infolion.sapss.depositRetreat.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.infolion.platform.core.annotation.ValidateRule;
import com.infolion.platform.core.domain.ProcessObject;
import com.infolion.platform.core.validation.DataType;

@Entity
@Table(name = "T_Deposit_Retreat")
public class TDepositRetreat extends ProcessObject{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String depositRetreatId;
	private String applyer;//申请人
	@ValidateRule(dataType = DataType.STRING, label = "申请金额", maxLength = 50,required = true)	
	private String applySun;//申请金额
	@ValidateRule(dataType = DataType.STRING, label = "申请期货账户", maxLength = 50,required = true)	
	private String applyAccount;//申请期货账户
	@ValidateRule(dataType = DataType.STRING, label = "收款账户", maxLength = 50,required = true)	
	private String receiptAccount;//收款账户
	@ValidateRule(dataType = DataType.STRING, label = "申请时间", maxLength = 10,required = true)	
	private String applyDate;//申请时间
	private String applyTime;//上报时间
	private String applyedTime;//审批通过时间
	private String examState;//
	private String creator;//创建者
	private String deptId;//创建部门
	private String createrTime;//创建时间
	private String isValid;//
	private String cmd;
	

	@Override
	public void setWorkflowModelId() {
		// TODO Auto-generated method stub
		this.workflowModelId = "1008";
	}

	@Override
	public void setWorkflowModelName() {
		this.workflowModelName = "保证金退回申请";
	}

	@Override
	public void setWorkflowProcessName() {
 		this.workflowProcessName = "deposit_retreat_v1";
	}

	@Override
	public void setWorkflowProcessUrl() {
		this.workflowProcessUrl = "depositRetreatController.spr?action=depositRetreatExamine";
	}
	
	@Id
	@Column(name = "deposit_Retreat_Id", unique = true, nullable = false, length = 36)
	public String getDepositRetreatId() {
		return depositRetreatId;
	}

	public void setDepositRetreatId(String depositRetreatId) {
		this.depositRetreatId = depositRetreatId;
	}
	
	@Column(name = "applyer", length = 50)
	public String getApplyer() {
		return applyer;
	}

	public void setApplyer(String applyer) {
		this.applyer = applyer;
	}
	@Column(name = "apply_Sun", length = 50)
	public String getApplySun() {
		return applySun;
	}

	public void setApplySun(String applySun) {
		this.applySun = applySun;
	}
	@Column(name = "apply_Account", length = 50)
	public String getApplyAccount() {
		return applyAccount;
	}

	public void setApplyAccount(String applyAccount) {
		this.applyAccount = applyAccount;
	}
	@Column(name = "receipt_Account", length = 50)
	public String getReceiptAccount() {
		return receiptAccount;
	}

	public void setReceiptAccount(String receiptAccount) {
		this.receiptAccount = receiptAccount;
	}
	@Column(name = "apply_Date", length = 20)
	public String getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(String applyDate) {
		this.applyDate = applyDate;
	}
	@Column(name = "apply_Time", length = 20)
	public String getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(String applyTime) {
		this.applyTime = applyTime;
	}
	@Column(name = "applyed_Time", length = 20)
	public String getApplyedTime() {
		return applyedTime;
	}

	public void setApplyedTime(String applyedTime) {
		this.applyedTime = applyedTime;
	}
	@Column(name = "creator", length = 36)
	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}
	@Column(name = "dept_Id", length = 36)
	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	@Column(name = "creater_Time", length = 20)
	public String getCreaterTime() {
		return createrTime;
	}

	public void setCreaterTime(String createrTime) {
		this.createrTime = createrTime;
	}
	@Column(name = "is_Valid", length = 2)
	public String getIsValid() {
		return isValid;
	}

	public void setIsValid(String isValid) {
		this.isValid = isValid;
	}
	@Column(name = "exam_State", length = 2)
	public String getExamState() {
		return examState;
	}

	public void setExamState(String examState) {
		this.examState = examState;
	}
	@Column(name = "cmd", length = 1000)
	public String getCmd() {
		return cmd;
	}

	public void setCmd(String cmd) {
		this.cmd = cmd;
	}



}
