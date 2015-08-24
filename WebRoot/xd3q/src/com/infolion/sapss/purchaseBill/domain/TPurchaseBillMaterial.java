package com.infolion.sapss.purchaseBill.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TPurchaseBillMaterial entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_purchase_BILL_MATERIAL")
public class TPurchaseBillMaterial implements java.io.Serializable {

	// Fields

	private String purchaseBillMateId;		//开票申请物料ID
	private String purchaseBillId;			//开票申请ID
	private String materialCode;		//物料编码
	private String materialName;		//物料名称
	private String materialUnit;		//物料单位
	private String contractNo;          //合同号
	private Double quantity;			//数量
	private String currency;			//币别
	private Double totalMoney;			//价税合计
	private Double price;				//单价
	private String cmd;					//备注
	private String purchaseRowsId;
	
	// Property accessors
	@Id
	@Column(name = "purchase_Bill_Mate_Id", unique = true, nullable = false, length = 36)
	public String getPurchaseBillMateId() {
		return this.purchaseBillMateId;
	}

	public void setPurchaseBillMateId(String purchaseBillMateId) {
		this.purchaseBillMateId = purchaseBillMateId;
	}

	@Column(name = "purchase_Bill_Id", length = 36)
	public String getPurchaseBillId() {
		return this.purchaseBillId;
	}

	public void setPurchaseBillId(String purchaseBillId) {
		this.purchaseBillId = purchaseBillId;
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
	@Column(name = "purchase_Rows_Id", length = 36)
	public String getPurchaseRowsId() {
		return purchaseRowsId;
	}

	public void setPurchaseRowsId(String purchaseRowsId) {
		this.purchaseRowsId = purchaseRowsId;
	}

}