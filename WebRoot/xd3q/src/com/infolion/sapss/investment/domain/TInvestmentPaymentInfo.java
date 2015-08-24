package com.infolion.sapss.investment.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TInvestmentPaymentInfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_INVESTMENT_PAYMENT_INFO")
public class TInvestmentPaymentInfo implements java.io.Serializable {

	// Fields

	private String ipId;
	private String infoId;
	private String deptName;
	private String deptId;
	private String creator;
	private String creatorId;
	private String createTime;
	private String isAvailable;
	private String status;
	private String approvedTime;
	private String cmd;
	private String applyTime;
	private String accountCode;
	private String applyDept;
	private String applier;
	private String paymentAccount;
	private String expireDate;
	private String applyCount;
	private String paymentUse;
	private String incomeCompany;
	private String openBank;
	private String incomeAccount;
	private String payer;
	private String paymentDate;
	private String paymentTotal;

	// Constructors

	/** default constructor */
	public TInvestmentPaymentInfo() {
	}

	/** minimal constructor */
	public TInvestmentPaymentInfo(String ipId) {
		this.ipId = ipId;
	}

	/** full constructor */
	public TInvestmentPaymentInfo(String ipId, String infoId, String deptName,
			String deptId, String creator, String creatorId, String createTime,
			String isAvailable, String status, String approvedTime, String cmd,
			String applyTime, String accountCode, String applyDept,
			String applier, String paymentAccount, String expireDate,
			String applyCount, String paymentUse, String incomeCompany,
			String openBank, String incomeAccount, String payer,
			String paymentDate) {
		this.ipId = ipId;
		this.infoId = infoId;
		this.deptName = deptName;
		this.deptId = deptId;
		this.creator = creator;
		this.creatorId = creatorId;
		this.createTime = createTime;
		this.isAvailable = isAvailable;
		this.status = status;
		this.approvedTime = approvedTime;
		this.cmd = cmd;
		this.applyTime = applyTime;
		this.accountCode = accountCode;
		this.applyDept = applyDept;
		this.applier = applier;
		this.paymentAccount = paymentAccount;
		this.expireDate = expireDate;
		this.applyCount = applyCount;
		this.paymentUse = paymentUse;
		this.incomeCompany = incomeCompany;
		this.openBank = openBank;
		this.incomeAccount = incomeAccount;
		this.payer = payer;
		this.paymentDate = paymentDate;
	}

	// Property accessors
	@Id
	@Column(name = "IP_ID", unique = true, nullable = false, length = 36)
	public String getIpId() {
		return this.ipId;
	}

	public void setIpId(String ipId) {
		this.ipId = ipId;
	}

	@Column(name = "INFO_ID", length = 36)
	public String getInfoId() {
		return this.infoId;
	}

	public void setInfoId(String infoId) {
		this.infoId = infoId;
	}

	@Column(name = "DEPT_NAME", length = 100)
	public String getDeptName() {
		return this.deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	@Column(name = "DEPT_ID", length = 36)
	public String getDeptId() {
		return this.deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	@Column(name = "CREATOR", length = 100)
	public String getCreator() {
		return this.creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	@Column(name = "CREATOR_ID", length = 36)
	public String getCreatorId() {
		return this.creatorId;
	}

	public void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
	}

	@Column(name = "CREATE_TIME", length = 20)
	public String getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	@Column(name = "IS_AVAILABLE", length = 1)
	public String getIsAvailable() {
		return this.isAvailable;
	}

	public void setIsAvailable(String isAvailable) {
		this.isAvailable = isAvailable;
	}

	@Column(name = "STATUS", length = 2)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "APPROVED_TIME", length = 20)
	public String getApprovedTime() {
		return this.approvedTime;
	}

	public void setApprovedTime(String approvedTime) {
		this.approvedTime = approvedTime;
	}

	@Column(name = "CMD", length = 1000)
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

	@Column(name = "ACCOUNT_CODE", length = 20)
	public String getAccountCode() {
		return this.accountCode;
	}

	public void setAccountCode(String accountCode) {
		this.accountCode = accountCode;
	}

	@Column(name = "APPLY_DEPT", length = 36)
	public String getApplyDept() {
		return this.applyDept;
	}

	public void setApplyDept(String applyDept) {
		this.applyDept = applyDept;
	}

	@Column(name = "APPLIER", length = 36)
	public String getApplier() {
		return this.applier;
	}

	public void setApplier(String applier) {
		this.applier = applier;
	}

	@Column(name = "PAYMENT_ACCOUNT", length = 36)
	public String getPaymentAccount() {
		return this.paymentAccount;
	}

	public void setPaymentAccount(String paymentAccount) {
		this.paymentAccount = paymentAccount;
	}

	@Column(name = "EXPIRE_DATE", length = 20)
	public String getExpireDate() {
		return this.expireDate;
	}

	public void setExpireDate(String expireDate) {
		this.expireDate = expireDate;
	}

	@Column(name = "APPLY_COUNT", length = 36)
	public String getApplyCount() {
		return this.applyCount;
	}

	public void setApplyCount(String applyCount) {
		this.applyCount = applyCount;
	}

	@Column(name = "PAYMENT_USE", length = 1000)
	public String getPaymentUse() {
		return this.paymentUse;
	}

	public void setPaymentUse(String paymentUse) {
		this.paymentUse = paymentUse;
	}

	@Column(name = "INCOME_COMPANY", length = 100)
	public String getIncomeCompany() {
		return this.incomeCompany;
	}

	public void setIncomeCompany(String incomeCompany) {
		this.incomeCompany = incomeCompany;
	}

	@Column(name = "OPEN_BANK", length = 36)
	public String getOpenBank() {
		return this.openBank;
	}

	public void setOpenBank(String openBank) {
		this.openBank = openBank;
	}

	@Column(name = "INCOME_ACCOUNT", length = 36)
	public String getIncomeAccount() {
		return this.incomeAccount;
	}

	public void setIncomeAccount(String incomeAccount) {
		this.incomeAccount = incomeAccount;
	}

	@Column(name = "PAYER", length = 36)
	public String getPayer() {
		return this.payer;
	}

	public void setPayer(String payer) {
		this.payer = payer;
	}

	@Column(name = "PAYMENT_DATE", length = 20)
	public String getPaymentDate() {
		return this.paymentDate;
	}

	public void setPaymentDate(String paymentDate) {
		this.paymentDate = paymentDate;
	}
	@Column(name = "PAYMENT_TOTAL", length = 20)
	public String getPaymentTotal() {
		return paymentTotal;
	}

	public void setPaymentTotal(String paymentTotal) {
		this.paymentTotal = paymentTotal;
	}

}