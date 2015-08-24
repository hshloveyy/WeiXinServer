package com.infolion.sapss.bill.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TBillApplyMaterial entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_BILL_APPLY_MATERIAL")
public class TBillApplyMaterial implements java.io.Serializable {

	// Fields

	private String exportMateId;		//开票申请物料ID
	private String billApplyId;			//开票申请ID
	private String materialCode;		//物料编码
	private String materialName;		//物料名称
	private String materialUnit;		//物料单位
	private Double quantity;			//数量
	private Double loanMoney;			//货款金额
	private Double tax;					//税金
	private String taxRate;				//税率
	private Double rate;				//税率
	private String currency;			//币别
	private Double totalMoney;			//价税合计
	private Double price;				//含税单价
	private String cmd;					//备注
	private String contractNo;          //合同号
	private String contractSalesId;   //合同ID
	private String receiptTime;

	// Constructors

	/** default constructor */
	public TBillApplyMaterial() {
	}

	/** minimal constructor */
	public TBillApplyMaterial(String exportMateId) {
		this.exportMateId = exportMateId;
	}

	/** full constructor */
	public TBillApplyMaterial(String exportMateId, String billApplyId,
			String materialCode, String materialName, String materialUnit,
			Double quantity, Double loanMoney, Double tax,Double rate, String taxRate,
			String currency, Double totalMoney, Double price, String cmd) {
		this.exportMateId = exportMateId;
		this.billApplyId = billApplyId;
		this.materialCode = materialCode;
		this.materialName = materialName;
		this.materialUnit = materialUnit;
		this.quantity = quantity;
		this.loanMoney = loanMoney;
		this.tax = tax;
		this.rate = rate;
		this.taxRate = taxRate;
		this.currency = currency;
		this.totalMoney = totalMoney;
		this.price = price;
		this.cmd = cmd;
	}

	// Property accessors
	@Id
	@Column(name = "EXPORT_MATE_ID", unique = true, nullable = false, length = 36)
	public String getExportMateId() {
		return this.exportMateId;
	}

	public void setExportMateId(String exportMateId) {
		this.exportMateId = exportMateId;
	}

	@Column(name = "BILL_APPLY_ID", length = 36)
	public String getBillApplyId() {
		return this.billApplyId;
	}

	public void setBillApplyId(String billApplyId) {
		this.billApplyId = billApplyId;
	}

	@Column(name = "MATERIAL_CODE", length = 50)
	public String getMaterialCode() {
		return this.materialCode;
	}

	public void setMaterialCode(String materialCode) {
		this.materialCode = materialCode;
	}

	@Column(name = "MATERIAL_NAME", length = 2000)
	public String getMaterialName() {
		return this.materialName;
	}

	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}

	@Column(name = "MATERIAL_UNIT", length = 50)
	public String getMaterialUnit() {
		return this.materialUnit;
	}

	public void setMaterialUnit(String materialUnit) {
		this.materialUnit = materialUnit;
	}

	@Column(name = "QUANTITY", precision = 24, scale = 6)
	public Double getQuantity() {
		return this.quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	@Column(name = "LOAN_MONEY", precision = 24, scale = 6)
	public Double getLoanMoney() {
		return this.loanMoney;
	}

	public void setLoanMoney(Double loanMoney) {
		this.loanMoney = loanMoney;
	}

	@Column(name = "TAX", precision = 24, scale = 6)
	public Double getTax() {
		return this.tax;
	}

	public void setTax(Double tax) {
		this.tax = tax;
	}

	@Column(name = "RATE", precision = 24, scale = 6)
	public Double getRate() {
		return this.rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}
	
	@Column(name = "TAX_RATE", length = 4)
	public String getTaxRate() {
		return this.taxRate;
	}

	public void setTaxRate(String taxRate) {
		this.taxRate = taxRate;
	}

	@Column(name = "CURRENCY", length = 6)
	public String getCurrency() {
		return this.currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	@Column(name = "TOTAL_MONEY", precision = 24, scale = 6)
	public Double getTotalMoney() {
		return this.totalMoney;
	}

	public void setTotalMoney(Double totalMoney) {
		this.totalMoney = totalMoney;
	}

	@Column(name = "PRICE", precision = 24, scale = 6)
	public Double getPrice() {
		return this.price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	@Column(name = "CMD", length = 2000)
	public String getCmd() {
		return this.cmd;
	}

	public void setCmd(String cmd) {
		this.cmd = cmd;
	}
	@Column(name = "CONTRACT_NO", length = 50)
	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
	@Column(name = "CONTRACT_SALES_ID", length = 36)
	public String getContractSalesId() {
		return contractSalesId;
	}

	public void setContractSalesId(String contractSalesId) {
		this.contractSalesId = contractSalesId;
	}
	
	@Column(name = "RECEIPTTIME", length = 20)
	public String getReceiptTime()
	{
		return receiptTime;
	}

	public void setReceiptTime(String receiptTime)
	{
		this.receiptTime = receiptTime;
	}

}