package com.infolion.sapss.export.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TExportShipmentStat entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_EXPORT_SHIPMENT_STAT")
public class TExportShipmentStat implements java.io.Serializable {

	// Fields

	private String exportShipmentStatId;
	private String exportApplyId;
	private String exportApplyNo;
	private String invNo;
	private String batchNo;
	private String exportDate;
	private String prsNum;
	private String unit;
	private Double total;
	private String currency;
	private String factoryName;
	private String addedTaxInvNo;
	private String addedTaxInvValue;
	private String addedTaxValue;
	private String writeNo;
	private String catchNo;
	private String port;
	private String customeName;
	private String rate;
	private String drawbackDate;
	private String drawbackEndDate;
	private String declarationNo;
	private String writeBackDate;
	private String shipName;
	private String shipInsureNo;
	private String isInsure;
	private String shipInsureDate;
	private String insureDate;
	private String insurePayDate;
	private Double incomeRate;
	private String mark;
	private String creatorTime;
	private String creator;
	private String creatorDept;
	private String isAvailable;
	private String receiptDate;
	private String huodai;

	// Constructors

	/** default constructor */
	public TExportShipmentStat() {
	}

	/** minimal constructor */
	public TExportShipmentStat(String exportShipmentStatId) {
		this.exportShipmentStatId = exportShipmentStatId;
	}

	/** full constructor */
	public TExportShipmentStat(String exportShipmentStatId,
			String exportApplyId, String exportApplyNo, String invNo,
			String batchNo, String exportDate, String prsNum, String unit,
			Double total, String currency, String factoryName,
			String addedTaxInvNo, String addedTaxInvValue,
			String addedTaxValue, String writeNo, String catchNo, String port,
			String customeName, String rate, String drawbackDate,
			String drawbackEndDate, String declarationNo, String writeBackDate,
			String shipName, String shipInsureNo, String isInsure,
			String shipInsureDate, String insureDate, String insurePayDate,
			Double incomeRate, String mark, String creatorTime, String creator,
			String creatorDept) {
		this.exportShipmentStatId = exportShipmentStatId;
		this.exportApplyId = exportApplyId;
		this.exportApplyNo = exportApplyNo;
		this.invNo = invNo;
		this.batchNo = batchNo;
		this.exportDate = exportDate;
		this.prsNum = prsNum;
		this.unit = unit;
		this.total = total;
		this.currency = currency;
		this.factoryName = factoryName;
		this.addedTaxInvNo = addedTaxInvNo;
		this.addedTaxInvValue = addedTaxInvValue;
		this.addedTaxValue = addedTaxValue;
		this.writeNo = writeNo;
		this.catchNo = catchNo;
		this.port = port;
		this.customeName = customeName;
		this.rate = rate;
		this.drawbackDate = drawbackDate;
		this.drawbackEndDate = drawbackEndDate;
		this.declarationNo = declarationNo;
		this.writeBackDate = writeBackDate;
		this.shipName = shipName;
		this.shipInsureNo = shipInsureNo;
		this.isInsure = isInsure;
		this.shipInsureDate = shipInsureDate;
		this.insureDate = insureDate;
		this.insurePayDate = insurePayDate;
		this.incomeRate = incomeRate;
		this.mark = mark;
		this.creatorTime = creatorTime;
		this.creator = creator;
		this.creatorDept = creatorDept;
	}

	// Property accessors
	@Id
	@Column(name = "EXPORT_SHIPMENT_STAT_ID", unique = true, nullable = false, length = 36)
	public String getExportShipmentStatId() {
		return this.exportShipmentStatId;
	}

	public void setExportShipmentStatId(String exportShipmentStatId) {
		this.exportShipmentStatId = exportShipmentStatId;
	}

	@Column(name = "EXPORT_APPLY_ID", length = 36)
	public String getExportApplyId() {
		return this.exportApplyId;
	}

	public void setExportApplyId(String exportApplyId) {
		this.exportApplyId = exportApplyId;
	}

	@Column(name = "EXPORT_APPLY_NO", length = 50)
	public String getExportApplyNo() {
		return this.exportApplyNo;
	}

	public void setExportApplyNo(String exportApplyNo) {
		this.exportApplyNo = exportApplyNo;
	}

	@Column(name = "INV_NO", length = 100)
	public String getInvNo() {
		return this.invNo;
	}

	public void setInvNo(String invNo) {
		this.invNo = invNo;
	}

	@Column(name = "BATCH_NO", length = 50)
	public String getBatchNo() {
		return this.batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	@Column(name = "EXPORT_DATE", length = 20)
	public String getExportDate() {
		return this.exportDate;
	}

	public void setExportDate(String exportDate) {
		this.exportDate = exportDate;
	}

	@Column(name = "PRS_NUM", length = 20)
	public String getPrsNum() {
		return this.prsNum;
	}

	public void setPrsNum(String prsNum) {
		this.prsNum = prsNum;
	}

	@Column(name = "UNIT", length = 20)
	public String getUnit() {
		return this.unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	@Column(name = "TOTAL", precision = 20, scale = 3)
	public Double getTotal() {
		return this.total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	@Column(name = "CURRENCY", length = 10)
	public String getCurrency() {
		return this.currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	@Column(name = "FACTORY_NAME", length = 100)
	public String getFactoryName() {
		return this.factoryName;
	}

	public void setFactoryName(String factoryName) {
		this.factoryName = factoryName;
	}

	@Column(name = "ADDED_TAX_INV_NO", length = 100)
	public String getAddedTaxInvNo() {
		return this.addedTaxInvNo;
	}

	public void setAddedTaxInvNo(String addedTaxInvNo) {
		this.addedTaxInvNo = addedTaxInvNo;
	}

	@Column(name = "ADDED_TAX_INV_VALUE", length = 20)
	public String getAddedTaxInvValue() {
		return this.addedTaxInvValue;
	}

	public void setAddedTaxInvValue(String addedTaxInvValue) {
		this.addedTaxInvValue = addedTaxInvValue;
	}

	@Column(name = "ADDED_TAX_VALUE", length = 20)
	public String getAddedTaxValue() {
		return this.addedTaxValue;
	}

	public void setAddedTaxValue(String addedTaxValue) {
		this.addedTaxValue = addedTaxValue;
	}

	@Column(name = "WRITE_NO", length = 50)
	public String getWriteNo() {
		return this.writeNo;
	}

	public void setWriteNo(String writeNo) {
		this.writeNo = writeNo;
	}

	@Column(name = "CATCH_NO", length = 50)
	public String getCatchNo() {
		return this.catchNo;
	}

	public void setCatchNo(String catchNo) {
		this.catchNo = catchNo;
	}

	@Column(name = "PORT", length = 100)
	public String getPort() {
		return this.port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	@Column(name = "CUSTOME_NAME", length = 200)
	public String getCustomeName() {
		return this.customeName;
	}

	public void setCustomeName(String customeName) {
		this.customeName = customeName;
	}

	@Column(name = "RATE", length = 10)
	public String getRate() {
		return this.rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	@Column(name = "DRAWBACK_DATE", length = 20)
	public String getDrawbackDate() {
		return this.drawbackDate;
	}

	public void setDrawbackDate(String drawbackDate) {
		this.drawbackDate = drawbackDate;
	}

	@Column(name = "DRAWBACK_END_DATE", length = 20)
	public String getDrawbackEndDate() {
		return this.drawbackEndDate;
	}

	public void setDrawbackEndDate(String drawbackEndDate) {
		this.drawbackEndDate = drawbackEndDate;
	}

	@Column(name = "DECLARATION_NO", length = 50)
	public String getDeclarationNo() {
		return this.declarationNo;
	}

	public void setDeclarationNo(String declarationNo) {
		this.declarationNo = declarationNo;
	}

	@Column(name = "WRITE_BACK_DATE", length = 20)
	public String getWriteBackDate() {
		return this.writeBackDate;
	}

	public void setWriteBackDate(String writeBackDate) {
		this.writeBackDate = writeBackDate;
	}

	@Column(name = "SHIP_NAME", length = 100)
	public String getShipName() {
		return this.shipName;
	}

	public void setShipName(String shipName) {
		this.shipName = shipName;
	}

	@Column(name = "SHIP_INSURE_NO", length = 50)
	public String getShipInsureNo() {
		return this.shipInsureNo;
	}

	public void setShipInsureNo(String shipInsureNo) {
		this.shipInsureNo = shipInsureNo;
	}

	@Column(name = "IS_INSURE", length = 1)
	public String getIsInsure() {
		return this.isInsure;
	}

	public void setIsInsure(String isInsure) {
		this.isInsure = isInsure;
	}

	@Column(name = "SHIP_INSURE_DATE", length = 20)
	public String getShipInsureDate() {
		return this.shipInsureDate;
	}

	public void setShipInsureDate(String shipInsureDate) {
		this.shipInsureDate = shipInsureDate;
	}

	@Column(name = "INSURE_DATE", length = 20)
	public String getInsureDate() {
		return this.insureDate;
	}

	public void setInsureDate(String insureDate) {
		this.insureDate = insureDate;
	}

	@Column(name = "INSURE_PAY_DATE", length = 20)
	public String getInsurePayDate() {
		return this.insurePayDate;
	}

	public void setInsurePayDate(String insurePayDate) {
		this.insurePayDate = insurePayDate;
	}

	@Column(name = "INCOME_RATE", precision = 10, scale = 6)
	public Double getIncomeRate() {
		return this.incomeRate;
	}

	public void setIncomeRate(Double incomeRate) {
		this.incomeRate = incomeRate;
	}

	@Column(name = "MARK", length = 4000)
	public String getMark() {
		return this.mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
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

	@Column(name = "CREATOR_DEPT", length = 36)
	public String getCreatorDept() {
		return this.creatorDept;
	}

	public void setCreatorDept(String creatorDept) {
		this.creatorDept = creatorDept;
	}
	@Column(name = "is_Available",length=2)
	public String getIsAvailable() {
		return isAvailable;
	}

	public void setIsAvailable(String isAvailable) {
		this.isAvailable = isAvailable;
	}
	@Column(name = "receipt_date",length=20)
	public String getReceiptDate() {
		return receiptDate;
	}

	public void setReceiptDate(String receiptDate) {
		this.receiptDate = receiptDate;
	}
	@Column(name = "huodai",length=200)
	public String getHuodai() {
		return huodai;
	}

	public void setHuodai(String huodai) {
		this.huodai = huodai;
	}

}