package com.infolion.sapss.scrapped.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TScrappedMaterial entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_SCRAPPED_MATERIAL")
public class TScrappedMaterial implements java.io.Serializable {

	// Fields

	private String scrappedMaterialId;
	private String scrappedId;
	private String materialCode;
	private String materialName;
	private String materialUnit;
	private String batchNo;
	private Double quantity;
	private Double money;
	private String warehouse;
	private String reason;
	private String currency;

	// Constructors

	/** default constructor */
	public TScrappedMaterial() {
	}

	/** minimal constructor */
	public TScrappedMaterial(String scrappedMaterialId) {
		this.scrappedMaterialId = scrappedMaterialId;
	}

	/** full constructor */
	public TScrappedMaterial(String scrappedMaterialId, String scrappedId,
			String materialCode, String materialName, String materialUnit,
			String batchNo, Double quantity, Double money, String warehouse,
			String reason) {
		this.scrappedMaterialId = scrappedMaterialId;
		this.scrappedId = scrappedId;
		this.materialCode = materialCode;
		this.materialName = materialName;
		this.materialUnit = materialUnit;
		this.batchNo = batchNo;
		this.quantity = quantity;
		this.money = money;
		this.warehouse = warehouse;
		this.reason = reason;
	}

	// Property accessors
	@Id
	@Column(name = "SCRAPPED_MATERIAL_ID", unique = true, nullable = false, length = 36)
	public String getScrappedMaterialId() {
		return this.scrappedMaterialId;
	}

	public void setScrappedMaterialId(String scrappedMaterialId) {
		this.scrappedMaterialId = scrappedMaterialId;
	}

	@Column(name = "SCRAPPED_ID", length = 36)
	public String getScrappedId() {
		return this.scrappedId;
	}

	public void setScrappedId(String scrappedId) {
		this.scrappedId = scrappedId;
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

	@Column(name = "BATCH_NO", length = 200)
	public String getBatchNo() {
		return this.batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	@Column(name = "QUANTITY", precision = 16)
	public Double getQuantity() {
		return this.quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	@Column(name = "MONEY", precision = 16)
	public Double getMoney() {
		return this.money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	@Column(name = "WAREHOUSE", length = 50)
	public String getWarehouse() {
		return this.warehouse;
	}

	public void setWarehouse(String warehouse) {
		this.warehouse = warehouse;
	}

	@Column(name = "REASON", length = 2000)
	public String getReason() {
		return this.reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
	@Column(name = "currency")
	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

}