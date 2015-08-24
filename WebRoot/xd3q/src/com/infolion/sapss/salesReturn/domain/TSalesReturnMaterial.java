package com.infolion.sapss.salesReturn.domain;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TSalesReturnMaterial entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_SALES_RETURN_MATERIAL")
public class TSalesReturnMaterial implements java.io.Serializable {

	// Fields

	private String returnMaterialId;
	private String returnId;
	private String materialCode;
	private String materialName;
	private String materialUnit;
	private BigDecimal quantity;
	private String batchNo;
	private String currency;
	private String calcUnit;
	private String priceUnit;
	private String factory;
	private String deliveryTime;
	private Double price;
	private Double rate;
	private Double money;
	private Double taxes;
	private Double totalMoney;
	private String cmd;

	// Constructors

	/** default constructor */
	public TSalesReturnMaterial() {
	}

	/** minimal constructor */
	public TSalesReturnMaterial(String returnMaterialId, String returnId) {
		this.returnMaterialId = returnMaterialId;
		this.returnId = returnId;
	}

	/** full constructor */
	public TSalesReturnMaterial(String returnMaterialId, String returnId,
			String materialCode, String materialName, String materialUnit,
			BigDecimal quantity, String batchNo, String currency, String calcUnit,
			String priceUnit, String factory, String deliveryTime,
			Double price, Double rate, Double money, Double taxes,
			Double totalMoney) {
		this.returnMaterialId = returnMaterialId;
		this.returnId = returnId;
		this.materialCode = materialCode;
		this.materialName = materialName;
		this.materialUnit = materialUnit;
		this.quantity = quantity;
		this.batchNo = batchNo;
		this.currency = currency;
		this.calcUnit = calcUnit;
		this.priceUnit = priceUnit;
		this.factory = factory;
		this.deliveryTime = deliveryTime;
		this.price = price;
		this.rate = rate;
		this.money = money;
		this.taxes = taxes;
		this.totalMoney = totalMoney;
	}

	// Property accessors
	@Id
	@Column(name = "RETURN_MATERIAL_ID", unique = true, nullable = false, length = 36)
	public String getReturnMaterialId() {
		return this.returnMaterialId;
	}

	public void setReturnMaterialId(String returnMaterialId) {
		this.returnMaterialId = returnMaterialId;
	}

	@Column(name = "RETURN_ID", nullable = false, length = 36)
	public String getReturnId() {
		return this.returnId;
	}

	public void setReturnId(String returnId) {
		this.returnId = returnId;
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

	@Column(name = "QUANTITY", precision = 16, scale = 3)
	public BigDecimal getQuantity() {
		return this.quantity;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	@Column(name = "BATCH_NO", length = 50)
	public String getBatchNo() {
		return this.batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	@Column(name = "CURRENCY", length = 50)
	public String getCurrency() {
		return this.currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	@Column(name = "CALC_UNIT", length = 50)
	public String getCalcUnit() {
		return this.calcUnit;
	}

	public void setCalcUnit(String calcUnit) {
		this.calcUnit = calcUnit;
	}

	@Column(name = "PRICE_UNIT", length = 50)
	public String getPriceUnit() {
		return this.priceUnit;
	}

	public void setPriceUnit(String priceUnit) {
		this.priceUnit = priceUnit;
	}

	@Column(name = "FACTORY", length = 50)
	public String getFactory() {
		return this.factory;
	}

	public void setFactory(String factory) {
		this.factory = factory;
	}

	@Column(name = "DELIVERY_TIME", length = 20)
	public String getDeliveryTime() {
		return this.deliveryTime;
	}

	public void setDeliveryTime(String deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

	@Column(name = "PRICE", precision = 16, scale = 3)
	public Double getPrice() {
		return this.price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	@Column(name = "RATE", precision = 16)
	public Double getRate() {
		return this.rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

	@Column(name = "MONEY", precision = 24, scale = 6)
	public Double getMoney() {
		return this.money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	@Column(name = "TAXES", precision = 24, scale = 6)
	public Double getTaxes() {
		return this.taxes;
	}

	public void setTaxes(Double taxes) {
		this.taxes = taxes;
	}

	@Column(name = "TOTAL_MONEY", precision = 16)
	public Double getTotalMoney() {
		return this.totalMoney;
	}

	public void setTotalMoney(Double totalMoney) {
		this.totalMoney = totalMoney;
	}

	@Column(name = "CMD", length = 2000)	
	public String getCmd()
	{
		return cmd;
	}

	public void setCmd(String cmd)
	{
		this.cmd = cmd;
	}

}