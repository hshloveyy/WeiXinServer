package com.infolion.sapss.jdPurchase.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.infolion.platform.core.annotation.ValidateRule;
import com.infolion.platform.core.domain.ProcessObject;
import com.infolion.platform.core.validation.DataType;

/**
 * TScrapped entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_JINDA_PURCHASE")
public class TJindaPurchase extends ProcessObject implements java.io.Serializable {

	// Fields

	private String purchaseId;
	private String cmd;
	private String applyTime;
	private String approvedTime;
	private String isAvailable;
	private String creatorTime;
	private String creator;
	@ValidateRule(dataType = DataType.STRING, label = "申请者", required = true)
	private String applier;
	private String examineStatus;
	@ValidateRule(dataType = DataType.STRING, label = "供应商", required = true)
	private String supplier;
	@ValidateRule(dataType = DataType.NUMBER, label = "申请金额", required = true)
	private String applyAccount;
	@ValidateRule(dataType = DataType.STRING, label = "SAP采购合同号", required = true)
	private String sapPurchaseNo;
	// Property accessors
	@Id
	@Column(name = "PURCHASE_ID", unique = true, nullable = false, length = 36)
	public String getPurchaseId() {
		return this.purchaseId;
	}

	public void setPurchaseId(String purchaseId) {
		this.purchaseId = purchaseId;
	}


	@Column(name = "CMD", length = 2000)
	public String getCmd() {
		return this.cmd;
	}

	public void setCmd(String cmd) {
		this.cmd = cmd;
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

	@Column(name = "CREATE_TIME", length = 20)
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
		this.workflowModelId="00003";
	}

	@Override
	public void setWorkflowModelName() {
	}

	@Override
	public void setWorkflowProcessName() {
	}

	@Override
	public void setWorkflowProcessUrl() {
	}
	@Column(name = "applier", length = 36)
	public String getApplier() {
		return applier;
	}

	public void setApplier(String applier) {
		this.applier = applier;
	}
	@Column(name = "examine_status")
	public String getExamineStatus() {
		return examineStatus;
	}

	public void setExamineStatus(String examineStatus) {
		this.examineStatus = examineStatus;
	}
	@Column(name = "supplier")
	public String getSupplier() {
		return supplier;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}
	@Column(name = "apply_account")
	public String getApplyAccount() {
		return applyAccount;
	}

	public void setApplyAccount(String applyAccount) {
		this.applyAccount = applyAccount;
	}
	@Column(name = "sap_purchase_no")
	public String getSapPurchaseNo() {
		return sapPurchaseNo;
	}

	public void setSapPurchaseNo(String sapPurchaseNo) {
		this.sapPurchaseNo = sapPurchaseNo;
	}

}