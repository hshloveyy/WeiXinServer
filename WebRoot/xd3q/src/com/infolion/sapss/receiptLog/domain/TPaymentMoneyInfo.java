package com.infolion.sapss.receiptLog.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TReceiptMoneyInfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_PAYMENT_MONEY_INFO")
public class TPaymentMoneyInfo implements java.io.Serializable {

	// Fields

	private String infoId;
	private String contractId;
	private String contractNo;
	private String payDate;
	private String realPayDate;
	private String payTotal;
	private String realPayTotal;
	private String payBalance;
	private String purchaseContractStaus;
	private String createTime;
	private String creator;
	private String isAvailable;
	private String deptId;
	private String remark;


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

	@Column(name = "PAY_DATE", length = 20)
	public String getPayDate() {
		return this.payDate;
	}

	public void setPayDate(String payDate) {
		this.payDate = payDate;
	}

	@Column(name = "REAL_PAY_DATE", length = 20)
	public String getRealPayDate() {
		return this.realPayDate;
	}

	public void setRealPayDate(String realPayDate) {
		this.realPayDate = realPayDate;
	}

	@Column(name = "PAY_TOTAL", length = 36)
	public String getPayTotal() {
		return this.payTotal;
	}

	public void setPayTotal(String payTotal) {
		this.payTotal = payTotal;
	}

	@Column(name = "REAL_PAY_TOTAL", length = 36)
	public String getRealPayTotal() {
		return this.realPayTotal;
	}

	public void setRealPayTotal(String realReceiptTotal) {
		this.realPayTotal = realReceiptTotal;
	}

	@Column(name = "PAY_BALANCE", length = 36)
	public String getPayBalance() {
		return this.payBalance;
	}

	public void setPayBalance(String payBalance) {
		this.payBalance = payBalance;
	}

	@Column(name = "PURCHASE_CONTRACT_STAUS", length = 36)
	public String getPurchaseContractStaus() {
		return this.purchaseContractStaus;
	}

	public void setPurchaseContractStaus(String purchaseContractStaus) {
		this.purchaseContractStaus = purchaseContractStaus;
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

}