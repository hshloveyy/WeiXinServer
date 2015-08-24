package com.infolion.sapss.export.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "T_Export_Bill_Exam_D")
public class TExportBillExamD implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String exportBillExamDId;
	private String exportBillExamId;
	private String exportApplyId;
	private String noticeNo;
	private String contractNo;
	private String writeNo;
	private String lcdpdaNo;
	private String creator;
	private String creatorTime;
	
	@Id
	@Column(name = "EXPORT_BILL_EXAM_D_ID", unique = true, nullable = false, length = 36)
	public String getExportBillExamDId() {
		return exportBillExamDId;
	}
	public void setExportBillExamDId(String exportBillExamDId) {
		this.exportBillExamDId = exportBillExamDId;
	}
	@Column(name = "EXPORT_BILL_EXAM_ID", length = 36)
	public String getExportBillExamId() {
		return exportBillExamId;
	}
	public void setExportBillExamId(String exportBillExamId) {
		this.exportBillExamId = exportBillExamId;
	}
	@Column(name = "EXPORT_APPLY_ID", length = 36)
	public String getExportApplyId() {
		return exportApplyId;
	}
	public void setExportApplyId(String exportApplyId) {
		this.exportApplyId = exportApplyId;
	}
	@Column(name = "CONTRACT_NO", length = 36)
	public String getContractNo() {
		return contractNo;
	}
	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
	@Column(name = "WRITE_NO", length = 36)
	public String getWriteNo() {
		return writeNo;
	}
	public void setWriteNo(String writeNo) {
		this.writeNo = writeNo;
	}
	@Column(name = "CREATOR", length = 36)
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	@Column(name = "CREATOR_TIME", length = 36)
	public String getCreatorTime() {
		return creatorTime;
	}
	public void setCreatorTime(String creatorTime) {
		this.creatorTime = creatorTime;
	}
	@Column(name = "NOTICE_NO", length = 36)
	public String getNoticeNo() {
		return noticeNo;
	}
	public void setNoticeNo(String noticeNo) {
		this.noticeNo = noticeNo;
	}
	@Column(name = "LCDPDA_NO", length = 36)
	public String getLcdpdaNo() {
		return lcdpdaNo;
	}
	public void setLcdpdaNo(String lcdpdaNo) {
		this.lcdpdaNo = lcdpdaNo;
	}

}
