package com.infolion.sapss.export.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TExportApplyIncome entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_EXPORT_APPLY_INCOME")
public class TExportApplyIncome implements java.io.Serializable {

	// Fields

	private String id;
	private String exportApplyId;
	private String exportIncomeInfoId;
	private String exportApplyNo;
	private String creatorTime;
	private String creator;

	// Constructors

	/** default constructor */
	public TExportApplyIncome() {
	}

	/** minimal constructor */
	public TExportApplyIncome(String id) {
		this.id = id;
	}

	/** full constructor */
	public TExportApplyIncome(String id, String exportApplyId,
			String exportIncomeInfoId, String exportApplyNo,
			String creatorTime, String creator) {
		this.id = id;
		this.exportApplyId = exportApplyId;
		this.exportIncomeInfoId = exportIncomeInfoId;
		this.exportApplyNo = exportApplyNo;
		this.creatorTime = creatorTime;
		this.creator = creator;
	}

	// Property accessors
	@Id
	@Column(name = "ID", unique = true, nullable = false, length = 36)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "EXPORT_APPLY_ID", length = 36)
	public String getExportApplyId() {
		return this.exportApplyId;
	}

	public void setExportApplyId(String exportApplyId) {
		this.exportApplyId = exportApplyId;
	}

	@Column(name = "EXPORT_INCOME_INFO_ID", length = 36)
	public String getExportIncomeInfoId() {
		return this.exportIncomeInfoId;
	}

	public void setExportIncomeInfoId(String exportIncomeInfoId) {
		this.exportIncomeInfoId = exportIncomeInfoId;
	}

	@Column(name = "EXPORT_APPLY_NO", length = 50)
	public String getExportApplyNo() {
		return this.exportApplyNo;
	}

	public void setExportApplyNo(String exportApplyNo) {
		this.exportApplyNo = exportApplyNo;
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

}