package com.infolion.sapss.export.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.infolion.platform.core.domain.BaseObject;

/**
 * 出口核销单申领表
 * @author Administrator
 *
 */
@Entity
@Table(name = "T_Export_Verification_Receipt")
public class TVerificationReceipt extends BaseObject{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String verificationReceiptId;
	private String receiptDeptName;//领单部门
	private String receiptDeptId;//领单部门ID
	private String receiptMan;//领单人
	private String exportApplyId;//五联单ID
	private String exportApplyNo;//五联单号
	private String receiptDate;//领单日期
	private String backDate;//回单日期
	private String writeNo;//核销单号
	private String taxNo;//发票号
	private String creator;
	private String deptId;
	private String createrTime;
	private String isAvailable;//
	private String mark;
	
	@Id
	@Column(name = "verification_Receipt_Id",unique = true, nullable = false, length = 36)
	public String getVerificationReceiptId() {
		return verificationReceiptId;
	}
	public void setVerificationReceiptId(String verificationReceiptId) {
		this.verificationReceiptId = verificationReceiptId;
	}
	@Column(name = "receipt_Dept_Name", length = 50)
	public String getReceiptDeptName() {
		return receiptDeptName;
	}
	public void setReceiptDeptName(String receiptDeptName) {
		this.receiptDeptName = receiptDeptName;
	}
	@Column(name = "receipt_Dept_Id", length = 36)
	public String getReceiptDeptId() {
		return receiptDeptId;
	}
	public void setReceiptDeptId(String receiptDeptId) {
		this.receiptDeptId = receiptDeptId;
	}
	@Column(name = "receipt_Man", length = 50)
	public String getReceiptMan() {
		return receiptMan;
	}
	public void setReceiptMan(String receiptMan) {
		this.receiptMan = receiptMan;
	}
	@Column(name = "export_Apply_Id", length = 36)
	public String getExportApplyId() {
		return exportApplyId;
	}
	public void setExportApplyId(String exportApplyId) {
		this.exportApplyId = exportApplyId;
	}
	@Column(name = "export_Apply_No", length = 50)
	public String getExportApplyNo() {
		return exportApplyNo;
	}
	public void setExportApplyNo(String exportApplyNo) {
		this.exportApplyNo = exportApplyNo;
	}
	@Column(name = "receipt_Date", length = 20)
	public String getReceiptDate() {
		return receiptDate;
	}
	public void setReceiptDate(String receiptDate) {
		this.receiptDate = receiptDate;
	}
	@Column(name = "back_Date", length = 20)
	public String getBackDate() {
		return backDate;
	}
	public void setBackDate(String backDate) {
		this.backDate = backDate;
	}
	@Column(name = "write_No", length = 50)
	public String getWriteNo() {
		return writeNo;
	}
	public void setWriteNo(String writeNo) {
		this.writeNo = writeNo;
	}
	@Column(name = "tax_No", length = 50)
	public String getTaxNo() {
		return taxNo;
	}
	public void setTaxNo(String taxNo) {
		this.taxNo = taxNo;
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
	@Column(name = "is_Available", length = 2)
	public String getIsAvailable() {
		return isAvailable;
	}
	public void setIsAvailable(String isAvailable) {
		this.isAvailable = isAvailable;
	}
	@Column(name = "mark", length = 2)
	public String getMark() {
		return mark;
	}
	public void setMark(String mark) {
		this.mark = mark;
	}

}
