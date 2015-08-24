package com.infolion.sapss.bill.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TBillApplyExport entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_BILL_APPLY_EXPORT")
public class TBillApplyExport implements java.io.Serializable {

	// Fields

	private String applyExportId;
	private String billApplyId;
	private String contractSalesId;
	private String exportApplyId;
	private String noticeNo;
	private String sapOrderNo;

	// Constructors

	/** default constructor */
	public TBillApplyExport() {
	}

	/** minimal constructor */
	public TBillApplyExport(String billApplyId) {
		this.billApplyId = billApplyId;
	}

	/** full constructor */
	public TBillApplyExport(String applyExportId,String billApplyId, String contractSalesId,
			String exportApplyId, String noticeNo, String sapOrderNo) {
		this.applyExportId = applyExportId;
		this.billApplyId = billApplyId;
		this.contractSalesId = contractSalesId;
		this.exportApplyId = exportApplyId;
		this.noticeNo = noticeNo;
		this.sapOrderNo = sapOrderNo;
	}

	// Property accessors
	@Id
	@Column(name = "APPLY_EXPORT_ID", unique = true, nullable = false, length = 36)
	public String getApplyExportId() {
		return this.applyExportId;
	}

	public void setApplyExportId(String applyExportId) {
		this.applyExportId = applyExportId;
	}
	
	@Column(name = "BILL_APPLY_ID", unique = true, nullable = false, length = 36)
	public String getBillApplyId() {
		return this.billApplyId;
	}

	public void setBillApplyId(String billApplyId) {
		this.billApplyId = billApplyId;
	}
	@Column(name = "CONTRACT_SALES_ID", length = 36)
	public String getContractSalesId() {
		return this.contractSalesId;
	}

	public void setContractSalesId(String contractSalesId) {
		this.contractSalesId = contractSalesId;
	}

	@Column(name = "EXPORT_APPLY_ID", length = 36)
	public String getExportApplyId() {
		return this.exportApplyId;
	}

	public void setExportApplyId(String exportApplyId) {
		this.exportApplyId = exportApplyId;
	}

	@Column(name = "NOTICE_NO", length = 50)
	public String getNoticeNo() {
		return this.noticeNo;
	}

	public void setNoticeNo(String noticeNo) {
		this.noticeNo = noticeNo;
	}

	@Column(name = "SAP_ORDER_NO", length = 50)
	public String getSapOrderNo() {
		return this.sapOrderNo;
	}

	public void setSapOrderNo(String sapOrderNo) {
		this.sapOrderNo = sapOrderNo;
	}

}