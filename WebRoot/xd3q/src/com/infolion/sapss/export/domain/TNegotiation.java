package com.infolion.sapss.export.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.infolion.platform.core.domain.ProcessObject;

/**
 * TNegotiation entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_NEGOTIATION")
public class TNegotiation extends ProcessObject {

	// Fields

	private String negotiationId;
	private String contractSalesId;
	private String currency;
	private String applyMoney;
	private String realMoney;
	private String bank;
	private String cmd;
	private String deptId;
	private String examineState;
	private String applyTime;
	private String approvedTime;
	private String isAvailable;
	private String creatorTime;
	private String creator;
	private String negotiationNo;

	// Constructors

	/** default constructor */
	public TNegotiation() {
	}

	/** minimal constructor */
	public TNegotiation(String negotiationId) {
		this.negotiationId = negotiationId;
	}

	/** full constructor */
	public TNegotiation(String negotiationId, String contractSalesId,
			String currency, String applyMoney, String realMoney, String bank,
			String cmd, String deptId, String examineState, String applyTime,
			String approvedTime, String isAvailable, String creatorTime,
			String creator) {
		this.negotiationId = negotiationId;
		this.contractSalesId = contractSalesId;
		this.currency = currency;
		this.applyMoney = applyMoney;
		this.realMoney = realMoney;
		this.bank = bank;
		this.cmd = cmd;
		this.deptId = deptId;
		this.examineState = examineState;
		this.applyTime = applyTime;
		this.approvedTime = approvedTime;
		this.isAvailable = isAvailable;
		this.creatorTime = creatorTime;
		this.creator = creator;
	}

	// Property accessors
	@Id
	@Column(name = "NEGOTIATION_ID", unique = true, nullable = false, length = 36)
	public String getNegotiationId() {
		return this.negotiationId;
	}

	public void setNegotiationId(String negotiationId) {
		this.negotiationId = negotiationId;
	}

	@Column(name = "CONTRACT_SALES_ID", length = 36)
	public String getContractSalesId() {
		return this.contractSalesId;
	}

	public void setContractSalesId(String contractSalesId) {
		this.contractSalesId = contractSalesId;
	}

	@Column(name = "CURRENCY", length = 4)
	public String getCurrency() {
		return this.currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	@Column(name = "APPLY_MONEY", precision = 16)
	public String getApplyMoney() {
		return this.applyMoney;
	}

	public void setApplyMoney(String applyMoney) {
		this.applyMoney = applyMoney;
	}

	@Column(name = "REAL_MONEY", precision = 16)
	public String getRealMoney() {
		return this.realMoney;
	}

	public void setRealMoney(String realMoney) {
		this.realMoney = realMoney;
	}

	@Column(name = "BANK", length = 2000)
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

	@Column(name = "NEGOTIATION_NO", length = 50)	
	public String getNegotiationNo()
	{
		return negotiationNo;
	}

	public void setNegotiationNo(String negotiationNo)
	{
		this.negotiationNo = negotiationNo;
	}
	
	@Override
	public void setWorkflowModelId()
	{
		this.workflowModelId = "1002";
	}

	@Override
	public void setWorkflowModelName()
	{
		this.workflowModelName = "议付";
	}

	@Override
	public void setWorkflowProcessName()
	{
		//this.workflowProcessName = "export_lend_v1";
	}

	@Override
	public void setWorkflowProcessUrl()
	{
		this.workflowProcessUrl = "negotiationController.spr?action=examine";
	}

}