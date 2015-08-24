package com.infolion.sapss.salesReturn.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.infolion.platform.core.annotation.ValidateRule;
import com.infolion.platform.core.domain.ProcessObject;
import com.infolion.platform.core.validation.DataType;

/**
 * TSalesReturn entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_SALES_RETURN")
public class TSalesReturn extends ProcessObject{

	// FieldsBaleLoanHibernateDao.java

	private String returnId;
	private String returnNo;
	@ValidateRule(dataType = DataType.STRING, label = "合同编号", maxLength = 36,required = true)	
	private String contractSalesId;
	private String returnType;
	private String shipId;
	private String netMoney;
	private String taxMoney;
	private String totalMoney;
	private String sapOrderNo;
	private String sapDeliveryNo;
	private String sapBillingNo;
	private String invoiceNo;
	@ValidateRule(dataType = DataType.STRING, label = "仓库", maxLength = 36,required = true)	
	private String warehouse;
	private String rate;
	private String cmd;
	private String deptId;
	private String examineState;
	private String applyTime;
	private String approvedTime;
	private String isAvailable;
	private String creatorTime;
	private String creator;
	private String paperContractNo;

	// Constructors

	/** default constructor */
	public TSalesReturn() {
	}

	/** minimal constructor */
	public TSalesReturn(String returnId) {
		this.returnId = returnId;
	}

	/** full constructor */
	public TSalesReturn(String returnId, String returnNo,
			String contractSalesId, String returnType, String shipId,
			String netMoney, String taxMoney, String totalMoney,
			String sapOrderNo, String sapDeliveryNo, String sapBillingNo,
			String invoiceNo, String warehouse, String rate, String cmd,
			String deptId, String examineState, String applyTime,
			String approvedTime, String isAvailable, String creatorTime,
			String creator) {
		this.returnId = returnId;
		this.returnNo = returnNo;
		this.contractSalesId = contractSalesId;
		this.returnType = returnType;
		this.shipId = shipId;
		this.netMoney = netMoney;
		this.taxMoney = taxMoney;
		this.totalMoney = totalMoney;
		this.sapOrderNo = sapOrderNo;
		this.sapDeliveryNo = sapDeliveryNo;
		this.sapBillingNo = sapBillingNo;
		this.invoiceNo = invoiceNo;
		this.warehouse = warehouse;
		this.rate = rate;
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
	@Column(name = "RETURN_ID", unique = true, nullable = false, length = 36)
	public String getReturnId() {
		return this.returnId;
	}

	public void setReturnId(String returnId) {
		this.returnId = returnId;
	}

	@Column(name = "RETURN_NO", length = 50)
	public String getReturnNo() {
		return this.returnNo;
	}

	public void setReturnNo(String returnNo) {
		this.returnNo = returnNo;
	}

	@Column(name = "CONTRACT_SALES_ID", length = 36)
	public String getContractSalesId() {
		return this.contractSalesId;
	}

	public void setContractSalesId(String contractSalesId) {
		this.contractSalesId = contractSalesId;
	}

	@Column(name = "RETURN_TYPE", length = 1)
	public String getReturnType() {
		return this.returnType;
	}

	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}

	@Column(name = "SHIP_ID", length = 36)
	public String getShipId() {
		return this.shipId;
	}

	public void setShipId(String shipId) {
		this.shipId = shipId;
	}

	@Column(name = "NET_MONEY", precision = 24, scale = 6)
	public String getNetMoney() {
		return this.netMoney;
	}

	public void setNetMoney(String netMoney) {
		this.netMoney = netMoney;
	}

	@Column(name = "TAX_MONEY", precision = 24, scale = 6)
	public String getTaxMoney() {
		return this.taxMoney;
	}

	public void setTaxMoney(String taxMoney) {
		this.taxMoney = taxMoney;
	}

	@Column(name = "TOTAL_MONEY", precision = 24)
	public String getTotalMoney() {
		return this.totalMoney;
	}

	public void setTotalMoney(String totalMoney) {
		this.totalMoney = totalMoney;
	}

	@Column(name = "SAP_ORDER_NO", length = 50)
	public String getSapOrderNo() {
		return this.sapOrderNo;
	}

	public void setSapOrderNo(String sapOrderNo) {
		this.sapOrderNo = sapOrderNo;
	}

	@Column(name = "SAP_DELIVERY_NO", length = 50)
	public String getSapDeliveryNo() {
		return this.sapDeliveryNo;
	}

	public void setSapDeliveryNo(String sapDeliveryNo) {
		this.sapDeliveryNo = sapDeliveryNo;
	}

	@Column(name = "SAP_BILLING_NO", length = 50)
	public String getSapBillingNo() {
		return this.sapBillingNo;
	}

	public void setSapBillingNo(String sapBillingNo) {
		this.sapBillingNo = sapBillingNo;
	}

	@Column(name = "INVOICE_NO", length = 50)
	public String getInvoiceNo() {
		return this.invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	@Column(name = "WAREHOUSE", length = 50)
	public String getWarehouse() {
		return this.warehouse;
	}

	public void setWarehouse(String warehouse) {
		this.warehouse = warehouse;
	}

	@Column(name = "RATE", precision = 24, scale = 6)
	public String getRate() {
		return this.rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
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
	
	@Override
	public void setWorkflowModelId()
	{
		this.workflowModelId = "1002";
	}

	@Override
	public void setWorkflowModelName()
	{
		this.workflowModelName = "销售退货";
	}

	@Override
	public void setWorkflowProcessName()
	{
		//this.workflowProcessName = "sales_retract_v1";
	}

	@Override
	public void setWorkflowProcessUrl()
	{
		this.workflowProcessUrl = "salesReturnController.spr?action=examine";
	}
	@Column(name = "PAPER_CONTRACT_NO", length =100)
	public String getPaperContractNo() {
		return paperContractNo;
	}

	public void setPaperContractNo(String paperContractNo) {
		this.paperContractNo = paperContractNo;
	}	

}