package com.infolion.sapss.interestBill.domain;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.infolion.platform.core.domain.ProcessObject;
@Entity
@Table(name = "T_INTEREST_BILL")
public class TInterestBill extends ProcessObject{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String interestBillId;
	private String projectNo;//立项号
	private String projectId;//立项ID
	private String interestBillNo;//开票申请单号
	private String deptId;//申请部门
	private String deptName;//部门名称
	private String unitName;//开票单位
	private String unitNo;//开票单位名称
	private String unitType;//单位类型，1：客户、2：供应商
	private String billPartyNo;//税务登记号
	private String applyDate;//申报日期
	private String sapReturnNo;//SAP开票凭证号
	private String taxNo;//发票号
	private String makeDate;//开票日期
	private String paperNo;//纸质合同号
	private String receiptDate;//应收日
	private BigDecimal value;//开票金额
	private String memo;//备注
	
	private String applyTime;
	private String approvedTime;
	private String createTime;
	private String examState;
	private String creator;
	private String isAvailable;
	


	@Override
	public void setWorkflowModelId()
	{
		// TODO Auto-generated method stub
		this.workflowModelId = "INTERESTBILL";
	}

	@Override
	public void setWorkflowModelName()
	{
		this.workflowModelName = "利息发票开票申请";

	}

	@Override
	public void setWorkflowProcessName()
	{
//		this.workflowProcessName = "credit_application_v1";
	}

	@Override
	public void setWorkflowProcessUrl()
	{
		this.workflowProcessUrl = "interestBillController.spr?action=entryExamine";

	}
	@Id
	@Column(name = "interest_Bill_Id", unique = true, nullable = false, length = 36)
	public String getInterestBillId() {
		return interestBillId;
	}

	public void setInterestBillId(String interestBillId) {
		this.interestBillId = interestBillId;
	}
	
	@Column(name = "project_No", length = 30)
	public String getProjectNo() {
		return projectNo;
	}

	public void setProjectNo(String projectNo) {
		this.projectNo = projectNo;
	}

	@Column(name = "project_Id", length = 36)
	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	@Column(name = "interest_Bill_No", length = 30)
	public String getInterestBillNo() {
		return interestBillNo;
	}

	public void setInterestBillNo(String interestBillNo) {
		this.interestBillNo = interestBillNo;
	}

	@Column(name = "dept_Id", length = 36)
	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	@Column(name = "dept_Name", length = 50)
	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	@Column(name = "unit_Name", length = 300)
	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	@Column(name = "unit_No", length = 30)
	public String getUnitNo() {
		return unitNo;
	}

	public void setUnitNo(String unitNo) {
		this.unitNo = unitNo;
	}

	@Column(name = "unit_Type", length = 2)
	public String getUnitType() {
		return unitType;
	}

	public void setUnitType(String unitType) {
		this.unitType = unitType;
	}

	@Column(name = "bill_Party_No", length = 50)
	public String getBillPartyNo() {
		return billPartyNo;
	}

	public void setBillPartyNo(String billPartyNo) {
		this.billPartyNo = billPartyNo;
	}

	@Column(name = "apply_Date", length = 20)
	public String getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(String applyDate) {
		this.applyDate = applyDate;
	}

	@Column(name = "sap_Return_No", length = 200)
	public String getSapReturnNo() {
		return sapReturnNo;
	}

	public void setSapReturnNo(String sapReturnNo) {
		this.sapReturnNo = sapReturnNo;
	}

	@Column(name = "tax_No", length = 200)
	public String getTaxNo() {
		return taxNo;
	}

	public void setTaxNo(String taxNo) {
		this.taxNo = taxNo;
	}

	@Column(name = "make_Date", length = 20)
	public String getMakeDate() {
		return makeDate;
	}

	public void setMakeDate(String makeDate) {
		this.makeDate = makeDate;
	}

	@Column(name = "paper_No", length = 300)
	public String getPaperNo() {
		return paperNo;
	}

	public void setPaperNo(String paperNo) {
		this.paperNo = paperNo;
	}

	@Column(name = "receipt_Date", length = 20)
	public String getReceiptDate() {
		return receiptDate;
	}

	public void setReceiptDate(String receiptDate) {
		this.receiptDate = receiptDate;
	}

	@Column(name = "value", length = 20)
	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	@Column(name = "memo", length = 500)
	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	@Column(name = "apply_Time", length = 20)
	public String getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(String applyTime) {
		this.applyTime = applyTime;
	}

	@Column(name = "approved_Time", length = 20)
	public String getApprovedTime() {
		return approvedTime;
	}

	public void setApprovedTime(String approvedTime) {
		this.approvedTime = approvedTime;
	}

	@Column(name = "create_Time", length = 20)
	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	@Column(name = "exam_State", length = 2)
	public String getExamState() {
		return examState;
	}

	public void setExamState(String examState) {
		this.examState = examState;
	}

	@Column(name = "creator", length = 36)
	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}
	@Column(name = "is_Available", length = 2)
	public String getIsAvailable() {
		return isAvailable;
	}

	public void setIsAvailable(String isAvailable) {
		this.isAvailable = isAvailable;
	}
	
	

}
