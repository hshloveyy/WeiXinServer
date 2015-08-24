package com.infolion.sapss.purchaseBill.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.infolion.platform.core.domain.ProcessObject;

/**
 * TPurchaseBill entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_PURCHASE_BILL")
public class TPurchaseBill extends ProcessObject implements java.io.Serializable {

	// Fields

	private String purchaseBillId;//采购票ID
	private String purchaseBillNo;//采购票号码
	private String contractPurchaseId;//采购订单ID
	private String contractPurchaseNo;//采购订单编码
	private String paperContractNo;//纸质合同号
	private String sapOrderNo;//SAP订单号
	private String cardNo;//身份证号
	private String taxNo;//发票号
	private Double quantityTotal;//数量
	private Double priceTotal;//开票总计
	private String billTime;//开票日期
	private String billToParty;//供应商编码
	private String billToPartyName;//供应商名称
	private String payTime;//应付款日
	private String cmd;
	private String deptId;
	private String examineState;
	private String applyTime;
	private String approvedTime;
	private String isAvailable;
	private String creatorTime;
	private String creator;


	// Constructors

	
	// Property accessors
	@Id
	@Column(name = "PURCHASE_BILL_ID", unique = true, nullable = false, length = 36)
	public String getPurchaseBillId() {
		return this.purchaseBillId;
	}

	public void setPurchaseBillId(String purchaseBillId) {
		this.purchaseBillId = purchaseBillId;
	}

	@Column(name = "PURCHASE_BILL_NO", length = 50)
	public String getPurchaseBillNo() {
		return this.purchaseBillNo;
	}

	public void setPurchaseBillNo(String purchaseBillNo) {
		this.purchaseBillNo = purchaseBillNo;
	}


	@Column(name = "TAX_NO", length = 2000)
	public String getTaxNo() {
		return this.taxNo;
	}

	public void setTaxNo(String taxNo) {
		this.taxNo = taxNo;
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

	@Column(name = "SAP_ORDER_NO", length = 2000)
	public String getSapOrderNo() {
		return sapOrderNo;
	}

	public void setSapOrderNo(String sapOrderNo) {
		this.sapOrderNo = sapOrderNo;
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
	
	@Override
	public void setWorkflowModelId() {
		// TODO Auto-generated method stub
		this.workflowModelId = "1009";
	}

	@Override
	public void setWorkflowModelName() {
		this.workflowModelName = "采购开票申请";
	}

	@Override
	public void setWorkflowProcessName() {
		// this.workflowProcessName = "import_invoice_v1";
	}

	@Override
	public void setWorkflowProcessUrl() {
		this.workflowProcessUrl = "purchaseBillController.spr?action=purchaseBillExamine";
	}
	@Column(name = "PAPER_CONTRACT_NO", length = 100)
	public String getPaperContractNo() {
		return paperContractNo;
	}

	public void setPaperContractNo(String paperContractNo) {
		this.paperContractNo = paperContractNo;
	}
	@Column(name = "contract_Purchase_Id", length = 50)
	public String getContractPurchaseId() {
		return contractPurchaseId;
	}

	public void setContractPurchaseId(String contractPurchaseId) {
		this.contractPurchaseId = contractPurchaseId;
	}
	@Column(name = "contract_Purchase_no", length = 50)
	public String getContractPurchaseNo() {
		return contractPurchaseNo;
	}

	public void setContractPurchaseNo(String contractPurchaseNo) {
		this.contractPurchaseNo = contractPurchaseNo;
	}
	@Column(name = "card_no", length = 50)
	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	@Column(name = "pay_time", length = 50)
	public String getPayTime() {
		return payTime;
	}

	public void setPayTime(String payTime) {
		this.payTime = payTime;
	}
}