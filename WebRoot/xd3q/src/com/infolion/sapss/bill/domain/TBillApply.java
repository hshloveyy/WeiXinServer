package com.infolion.sapss.bill.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.infolion.platform.core.domain.ProcessObject;

/**
 * TBillApply entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_BILL_APPLY")
public class TBillApply extends ProcessObject implements java.io.Serializable {

	// Fields

	private String billApplyId;
	private String billApplyNo;
	private String contractSalesId;
	private String contractSalesNo;
	private String taxNo;
	private String billType;
	private String billTime;
	private Double quantityTotal;
	private Double taxTotal;
	private Double loanTotal;
	private Double priceTotal;
	private String cmd;
	private String deptId;
	private String examineState;
	private String applyTime;
	private String approvedTime;
	private String isAvailable;
	private String creatorTime;
	private String creator;
	private String exportApplyNo;
	private String sapBillNo;
	private String sapOrderNo;
	private String sapReturnNo;
	private String billToParty;
	private String billToPartyName;
	private String tradeType;
	private String paperContractNo;
	private String billToPartyNo;//客户税务登记号
	private String isPrint;//是否已打印
	private String receiptTime;//应收日

	// Constructors

	/** default constructor */
	public TBillApply() {
	}

	/** minimal constructor */
	public TBillApply(String billApplyId) {
		this.billApplyId = billApplyId;
	}

	/** full constructor */
	public TBillApply(String billApplyId, String billApplyNo,
			String contractSalesId, String contractSalesNo, String taxNo,
			String billType, String billTime, Double quantityTotal,
			Double taxTotal, Double loanTotal, Double priceTotal, String cmd,
			String deptId, String examineState, String applyTime,
			String approvedTime, String isAvailable, String creatorTime,
			String creator, String exportApplyNo, String sapBillNo,
			String sapOrderNo, String sapReturnNo, String billToParty,
			String billToPartyName,String tradeType) {
		this.billApplyId = billApplyId;
		this.billApplyNo = billApplyNo;
		this.contractSalesId = contractSalesId;
		this.contractSalesNo = contractSalesNo;
		this.taxNo = taxNo;
		this.billType = billType;
		this.billTime = billTime;
		this.quantityTotal = quantityTotal;
		this.taxTotal = taxTotal;
		this.loanTotal = loanTotal;
		this.priceTotal = priceTotal;
		this.cmd = cmd;
		this.deptId = deptId;
		this.examineState = examineState;
		this.applyTime = applyTime;
		this.approvedTime = approvedTime;
		this.isAvailable = isAvailable;
		this.creatorTime = creatorTime;
		this.creator = creator;
		this.exportApplyNo = exportApplyNo;
		this.sapBillNo = sapBillNo;
		this.sapOrderNo = sapOrderNo;
		this.sapReturnNo = sapReturnNo;
		this.billToParty = billToParty;
		this.billToPartyName = billToPartyName;
		this.tradeType=tradeType;
	}

	// Property accessors
	@Id
	@Column(name = "BILL_APPLY_ID", unique = true, nullable = false, length = 36)
	public String getBillApplyId() {
		return this.billApplyId;
	}

	public void setBillApplyId(String billApplyId) {
		this.billApplyId = billApplyId;
	}

	@Column(name = "BILL_APPLY_NO", length = 50)
	public String getBillApplyNo() {
		return this.billApplyNo;
	}

	public void setBillApplyNo(String billApplyNo) {
		this.billApplyNo = billApplyNo;
	}

	@Column(name = "CONTRACT_SALES_ID", length = 36)
	public String getContractSalesId() {
		return this.contractSalesId;
	}

	public void setContractSalesId(String contractSalesId) {
		this.contractSalesId = contractSalesId;
	}
	
	@Column(name = "CONTRACT_SALES_NO", length = 2000)
	public String getContractSalesNo() {
		return this.contractSalesNo;
	}

	public void setContractSalesNo(String contractSalesNo) {
		this.contractSalesNo = contractSalesNo;
	}

	@Column(name = "TAX_NO", length = 2000)
	public String getTaxNo() {
		return this.taxNo;
	}

	public void setTaxNo(String taxNo) {
		this.taxNo = taxNo;
	}

	@Column(name = "BILL_TYPE", length = 4)
	public String getBillType() {
		return this.billType;
	}

	public void setBillType(String billType) {
		this.billType = billType;
	}

	@Column(name = "BILL_TIME", length = 20)
	public String getBillTime() {
		return this.billTime;
	}

	public void setBillTime(String billTime) {
		this.billTime = billTime;
	}

	@Column(name = "QUANTITY_TOTAL", precision = 16)
	public Double getQuantityTotal() {
		return this.quantityTotal;
	}

	public void setQuantityTotal(Double quantityTotal) {
		this.quantityTotal = quantityTotal;
	}

	@Column(name = "TAX_TOTAL", precision = 16)
	public Double getTaxTotal() {
		return this.taxTotal;
	}

	public void setTaxTotal(Double taxTotal) {
		this.taxTotal = taxTotal;
	}

	@Column(name = "LOAN_TOTAL", precision = 16)
	public Double getLoanTotal() {
		return this.loanTotal;
	}

	public void setLoanTotal(Double loanTotal) {
		this.loanTotal = loanTotal;
	}

	@Column(name = "PRICE_TOTAL", precision = 16)
	public Double getPriceTotal() {
		return this.priceTotal;
	}

	public void setPriceTotal(Double priceTotal) {
		this.priceTotal = priceTotal;
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

	@Column(name = "EXPORT_APPLY_NO", length = 2000)
	public String getExportApplyNo() {
		return this.exportApplyNo;
	}

	public void setExportApplyNo(String exportApplyNo) {
		this.exportApplyNo = exportApplyNo;
	}

	@Column(name = "SAP_BILL_NO", length = 50)
	public String getSapBillNo() {
		return sapBillNo;
	}

	public void setSapBillNo(String sapBillNo) {
		this.sapBillNo = sapBillNo;
	}

	@Column(name = "SAP_ORDER_NO", length = 2000)
	public String getSapOrderNo() {
		return sapOrderNo;
	}

	public void setSapOrderNo(String sapOrderNo) {
		this.sapOrderNo = sapOrderNo;
	}

	@Column(name = "SAP_RETURN_NO", length = 2000)
	public String getSapReturnNo() {
		return sapReturnNo;
	}

	public void setSapReturnNo(String sapReturnNo) {
		this.sapReturnNo = sapReturnNo;
	}

	@Column(name = "BILL_TO_PARTY", length = 10)
	public String getBillToParty() {
		return billToParty;
	}

	public void setBillToParty(String billToParty) {
		this.billToParty = billToParty;
	}

	@Column(name = "BILL_TO_PARTY_NAME", length = 100)
	public String getBillToPartyName() {
		return billToPartyName;
	}

	public void setBillToPartyName(String billToPartyName) {
		this.billToPartyName = billToPartyName;
	}
	
	@Column(name = "TRADE_TYPE", length = 2)
	public String getTradeType() {
		return tradeType;
	}

	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}
	
	@Override
	public void setWorkflowModelId() {
		// TODO Auto-generated method stub
		this.workflowModelId = "1009";
	}

	@Override
	public void setWorkflowModelName() {
		this.workflowModelName = "开票申请";
	}

	@Override
	public void setWorkflowProcessName() {
		// this.workflowProcessName = "import_invoice_v1";
	}

	@Override
	public void setWorkflowProcessUrl() {
		this.workflowProcessUrl = "billApplyController.spr?action=billApplyExamine";
	}
	@Column(name = "PAPER_CONTRACT_NO", length = 100)
	public String getPaperContractNo() {
		return paperContractNo;
	}

	public void setPaperContractNo(String paperContractNo) {
		this.paperContractNo = paperContractNo;
	}
	@Column(name = "BILL_TO_PARTY_NO", length = 100)
	public String getBillToPartyNo() {
		return billToPartyNo;
	} 
	
	public void setBillToPartyNo(String billToPartyNo) {
		this.billToPartyNo = billToPartyNo;
	}
	@Column(name = "is_print", length = 2)
	public String getIsPrint() {
		return isPrint;
	}

	public void setIsPrint(String isPrint) {
		this.isPrint = isPrint;
	}
	@Column(name = "receiptTime", length = 2)
	public String getReceiptTime() {
		return receiptTime;
	}

	public void setReceiptTime(String receiptTime) {
		this.receiptTime = receiptTime;
	}
}