package com.infolion.sapss.export.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TExportIncomeInfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_EXPORT_INCOME_INFO")
public class TExportIncomeInfo implements java.io.Serializable {

	// Fields

	private String exportIncomeInfoId;
	private String projectId;
	private String projectNo;
	private String contractSalesId;
	private String contractNo;
	private String invNo;
	private String acceptDate;
	private Double total;
	private String currency;
	private Double cnyTotal;
	private String payBank;
	private String negotiatDate;
	private Double negotialIncome;
	private Double negotialRate;
	private String creator;
	private String creatorTime;
	private String creatorDept;
	private String isAvailable;
	private String cmd;//付款用途
	
	private String exportApplyId;//五联单Id
	private String exportApplyNo;//五联单号
	private String writeNo;//核销单号
	private String exportBillExamId;//出单审单ID
	// Constructors

	/** default constructor */
	public TExportIncomeInfo() {
	}

	/** minimal constructor */
	public TExportIncomeInfo(String exportIncomeInfoId) {
		this.exportIncomeInfoId = exportIncomeInfoId;
	}

	/** full constructor */
	public TExportIncomeInfo(String exportIncomeInfoId, String projectId,
			String projectNo, String contractSalesId, String contractNo,
			String invNo, String acceptDate, Double total, String currency,
			Double cnyTotal, String payBank, String negotiatDate,
			Double negotialIncome, Double negotialRate, String creator,
			String creatorTime, String creatorDept) {
		this.exportIncomeInfoId = exportIncomeInfoId;
		this.projectId = projectId;
		this.projectNo = projectNo;
		this.contractSalesId = contractSalesId;
		this.contractNo = contractNo;
		this.invNo = invNo;
		this.acceptDate = acceptDate;
		this.total = total;
		this.currency = currency;
		this.cnyTotal = cnyTotal;
		this.payBank = payBank;
		this.negotiatDate = negotiatDate;
		this.negotialIncome = negotialIncome;
		this.negotialRate = negotialRate;
		this.creator = creator;
		this.creatorTime = creatorTime;
		this.creatorDept = creatorDept;
	}

	// Property accessors
	@Id
	@Column(name = "EXPORT_INCOME_INFO_ID", unique = true, nullable = false, length = 36)
	public String getExportIncomeInfoId() {
		return this.exportIncomeInfoId;
	}

	public void setExportIncomeInfoId(String exportIncomeInfoId) {
		this.exportIncomeInfoId = exportIncomeInfoId;
	}

	@Column(name = "PROJECT_ID", length = 36)
	public String getProjectId() {
		return this.projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	@Column(name = "PROJECT_NO", length = 20)
	public String getProjectNo() {
		return this.projectNo;
	}

	public void setProjectNo(String projectNo) {
		this.projectNo = projectNo;
	}

	@Column(name = "CONTRACT_SALES_ID", length = 36)
	public String getContractSalesId() {
		return this.contractSalesId;
	}

	public void setContractSalesId(String contractSalesId) {
		this.contractSalesId = contractSalesId;
	}

	@Column(name = "CONTRACT_NO", length = 20)
	public String getContractNo() {
		return this.contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	@Column(name = "INV_NO", length = 50)
	public String getInvNo() {
		return this.invNo;
	}

	public void setInvNo(String invNo) {
		this.invNo = invNo;
	}

	@Column(name = "ACCEPT_DATE", length = 20)
	public String getAcceptDate() {
		return this.acceptDate;
	}

	public void setAcceptDate(String acceptDate) {
		this.acceptDate = acceptDate;
	}

	@Column(name = "TOTAL", precision = 12)
	public Double getTotal() {
		return this.total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	@Column(name = "CURRENCY", length = 10)
	public String getCurrency() {
		return this.currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	@Column(name = "CNY_TOTAL", precision = 12)
	public Double getCnyTotal() {
		return this.cnyTotal;
	}

	public void setCnyTotal(Double cnyTotal) {
		this.cnyTotal = cnyTotal;
	}

	@Column(name = "PAY_BANK", length = 50)
	public String getPayBank() {
		return this.payBank;
	}

	public void setPayBank(String payBank) {
		this.payBank = payBank;
	}

	@Column(name = "NEGOTIAT_DATE", length = 20)
	public String getNegotiatDate() {
		return this.negotiatDate;
	}

	public void setNegotiatDate(String negotiatDate) {
		this.negotiatDate = negotiatDate;
	}

	@Column(name = "NEGOTIAL_INCOME", precision = 12)
	public Double getNegotialIncome() {
		return this.negotialIncome;
	}

	public void setNegotialIncome(Double negotialIncome) {
		this.negotialIncome = negotialIncome;
	}

	@Column(name = "NEGOTIAL_RATE", precision = 10, scale = 4)
	public Double getNegotialRate() {
		return this.negotialRate;
	}

	public void setNegotialRate(Double negotialRate) {
		this.negotialRate = negotialRate;
	}

	@Column(name = "CREATOR", length = 36)
	public String getCreator() {
		return this.creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	@Column(name = "CREATOR_TIME", length = 36)
	public String getCreatorTime() {
		return this.creatorTime;
	}

	public void setCreatorTime(String creatorTime) {
		this.creatorTime = creatorTime;
	}

	@Column(name = "CREATOR_DEPT", length = 36)
	public String getCreatorDept() {
		return this.creatorDept;
	}

	public void setCreatorDept(String creatorDept) {
		this.creatorDept = creatorDept;
	}
	@Column(name = "is_Available",length=2)
	public String getIsAvailable() {
		return isAvailable;
	}

	public void setIsAvailable(String isAvailable) {
		this.isAvailable = isAvailable;
	}
	@Column(name = "cmd",length=500)
	public String getCmd() {
		return cmd;
	}

	public void setCmd(String cmd) {
		this.cmd = cmd;
	}
	@Column(name = "export_apply_id", length = 36)
	public String getExportApplyId() {
		return exportApplyId;
	}

	public void setExportApplyId(String exportApplyId) {
		this.exportApplyId = exportApplyId;
	}
	@Column(name = "export_apply_no", length = 36)
	public String getExportApplyNo() {
		return exportApplyNo;
	}

	public void setExportApplyNo(String exportApplyNo) {
		this.exportApplyNo = exportApplyNo;
	}
	@Column(name = "write_no", length = 36)
	public String getWriteNo() {
		return writeNo;
	}

	public void setWriteNo(String writeNo) {
		this.writeNo = writeNo;
	}
	@Column(name = "export_Bill_Exam_Id", length = 36)
	public String getExportBillExamId() {
		return exportBillExamId;
	}

	public void setExportBillExamId(String exportBillExamId) {
		this.exportBillExamId = exportBillExamId;
	}

}