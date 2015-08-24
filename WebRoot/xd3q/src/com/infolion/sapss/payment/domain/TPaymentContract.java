package com.infolion.sapss.payment.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TPaymentContract entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_PAYMENT_CONTRACT")
public class TPaymentContract implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7814486030662905744L;
	// Fields

	private String paymentContractId;
	private String paymentId;
	private String contractPurchaseId;
	private String contractNo;
	private String sapOrderNo;
	private String contractValue;
	private String deptId;
	private String cmd;
	private String isAvailable;
	private String creatorTime;
	private String creator;
	private String payValue;
	private String paperNo;

	// Constructors

	/** default constructor */
	public TPaymentContract() {
	}

	/** minimal constructor */
	public TPaymentContract(String paymentContractId) {
		this.paymentContractId = paymentContractId;
	}

	/** full constructor */
	public TPaymentContract(String paymentContractId, String paymentId,
			String contractPurchaseId, String contractNo, String sapOrderNo,
			String contractValue, String deptId, String cmd,
			String isAvailable, String creatorTime, String creator) {
		this.paymentContractId = paymentContractId;
		this.paymentId = paymentId;
		this.contractPurchaseId = contractPurchaseId;
		this.contractNo = contractNo;
		this.sapOrderNo = sapOrderNo;
		this.contractValue = contractValue;
		this.deptId = deptId;
		this.cmd = cmd;
		this.isAvailable = isAvailable;
		this.creatorTime = creatorTime;
		this.creator = creator;
	}

	// Property accessors
	@Id
	@Column(name = "PAYMENT_CONTRACT_ID", unique = true, nullable = false, length = 36)
	public String getPaymentContractId() {
		return this.paymentContractId;
	}

	public void setPaymentContractId(String paymentContractId) {
		this.paymentContractId = paymentContractId;
	}

	@Column(name = "PAYMENT_ID", length = 36)
	public String getPaymentId() {
		return this.paymentId;
	}

	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}

	@Column(name = "CONTRACT_PURCHASE_ID", length = 36)
	public String getContractPurchaseId() {
		return this.contractPurchaseId;
	}

	public void setContractPurchaseId(String contractPurchaseId) {
		this.contractPurchaseId = contractPurchaseId;
	}

	@Column(name = "CONTRACT_NO", length = 30)
	public String getContractNo() {
		return this.contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	@Column(name = "SAP_ORDER_NO", length = 30)
	public String getSapOrderNo() {
		return this.sapOrderNo;
	}

	public void setSapOrderNo(String sapOrderNo) {
		this.sapOrderNo = sapOrderNo;
	}

	@Column(name = "CONTRACT_VALUE", length = 20)
	public String getContractValue() {
		return this.contractValue;
	}

	public void setContractValue(String contractValue) {
		this.contractValue = contractValue;
	}

	@Column(name = "DEPT_ID", length = 36)
	public String getDeptId() {
		return this.deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	@Column(name = "CMD", length = 2000)
	public String getCmd() {
		return this.cmd;
	}

	public void setCmd(String cmd) {
		this.cmd = cmd;
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
	@Column(name = "PAY_VALUE", length = 20)
	public String getPayValue() {
		return payValue;
	}

	public void setPayValue(String payValue) {
		this.payValue = payValue;
	}
	@Column(name = "PAPER_NO", length = 20)
	public String getPaperNo() {
		return paperNo;
	}

	public void setPaperNo(String paperNo) {
		this.paperNo = paperNo;
	}

}