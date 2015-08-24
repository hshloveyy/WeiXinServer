package com.infolion.sapss.payment.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TPaymentImportsMaterial entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_PAYMENT_IMPORTS_MATERIAL")
public class TPaymentImportsMaterial implements java.io.Serializable {

	// Fields

	private String purchaseRowsId;
	private String pickListId;
	private String ekpoMatnr;
	private String ekpoTxz01;
	private String ekpoMeins;
	private Double ekpoMenge;
	private Double ekpoNetpr;
	private String ekpoBprme;
	private String ekpoPeinh;
	private String ekpoWerks;
	private String ekpoWerksName;
	private String eketEindt;
	private String ekpoWebre;
	private String ekpoMwskz;
	private String ekpoMwskzName;
	private String factoryLocal;
	private String flowNo;
	private String totalValue;
	private String price;
	private String taxes;
	private String totalTaxes;
	private String materielUnit;
	private String createTime;
	private String creator;
	private String purRowId;//采购行项目ID
	private String tax;//关税率

	// Constructors

	/** default constructor */
	public TPaymentImportsMaterial() {
	}

	/** minimal constructor */
	public TPaymentImportsMaterial(String purchaseRowsId) {
		this.purchaseRowsId = purchaseRowsId;
	}

	/** full constructor */
	public TPaymentImportsMaterial(String purchaseRowsId, String pickListId,
			String ekpoMatnr, String ekpoTxz01, String ekpoMeins,
			Double ekpoMenge, Double ekpoNetpr, String ekpoBprme,
			String ekpoPeinh, String ekpoWerks, String ekpoWerksName,
			String eketEindt, String ekpoWebre, String ekpoMwskz,
			String ekpoMwskzName, String factoryLocal, String flowNo,
			String totalValue, String price, String taxes, String totalTaxes,
			String materielUnit, String createTime, String creator) {
		this.purchaseRowsId = purchaseRowsId;
		this.pickListId = pickListId;
		this.ekpoMatnr = ekpoMatnr;
		this.ekpoTxz01 = ekpoTxz01;
		this.ekpoMeins = ekpoMeins;
		this.ekpoMenge = ekpoMenge;
		this.ekpoNetpr = ekpoNetpr;
		this.ekpoBprme = ekpoBprme;
		this.ekpoPeinh = ekpoPeinh;
		this.ekpoWerks = ekpoWerks;
		this.ekpoWerksName = ekpoWerksName;
		this.eketEindt = eketEindt;
		this.ekpoWebre = ekpoWebre;
		this.ekpoMwskz = ekpoMwskz;
		this.ekpoMwskzName = ekpoMwskzName;
		this.factoryLocal = factoryLocal;
		this.flowNo = flowNo;
		this.totalValue = totalValue;
		this.price = price;
		this.taxes = taxes;
		this.totalTaxes = totalTaxes;
		this.materielUnit = materielUnit;
		this.createTime = createTime;
		this.creator = creator;
	}

	// Property accessors
	@Id
	@Column(name = "PURCHASE_ROWS_ID", unique = true, nullable = false, length = 36)
	public String getPurchaseRowsId() {
		return this.purchaseRowsId;
	}

	public void setPurchaseRowsId(String purchaseRowsId) {
		this.purchaseRowsId = purchaseRowsId;
	}

	@Column(name = "PICK_LIST_ID", length = 36)
	public String getPickListId() {
		return this.pickListId;
	}

	public void setPickListId(String pickListId) {
		this.pickListId = pickListId;
	}

	@Column(name = "EKPO_MATNR", length = 18)
	public String getEkpoMatnr() {
		return this.ekpoMatnr;
	}

	public void setEkpoMatnr(String ekpoMatnr) {
		this.ekpoMatnr = ekpoMatnr;
	}

	@Column(name = "EKPO_TXZ01", length = 40)
	public String getEkpoTxz01() {
		return this.ekpoTxz01;
	}

	public void setEkpoTxz01(String ekpoTxz01) {
		this.ekpoTxz01 = ekpoTxz01;
	}

	@Column(name = "EKPO_MEINS", length = 3)
	public String getEkpoMeins() {
		return this.ekpoMeins;
	}

	public void setEkpoMeins(String ekpoMeins) {
		this.ekpoMeins = ekpoMeins;
	}

	@Column(name = "EKPO_MENGE", precision = 13, scale = 3)
	public Double getEkpoMenge() {
		return this.ekpoMenge;
	}

	public void setEkpoMenge(Double ekpoMenge) {
		this.ekpoMenge = ekpoMenge;
	}

	@Column(name = "EKPO_NETPR", precision = 11)
	public Double getEkpoNetpr() {
		return this.ekpoNetpr;
	}

	public void setEkpoNetpr(Double ekpoNetpr) {
		this.ekpoNetpr = ekpoNetpr;
	}

	@Column(name = "EKPO_BPRME", length = 10)
	public String getEkpoBprme() {
		return this.ekpoBprme;
	}

	public void setEkpoBprme(String ekpoBprme) {
		this.ekpoBprme = ekpoBprme;
	}

	@Column(name = "EKPO_PEINH", length = 10)
	public String getEkpoPeinh() {
		return this.ekpoPeinh;
	}

	public void setEkpoPeinh(String ekpoPeinh) {
		this.ekpoPeinh = ekpoPeinh;
	}

	@Column(name = "EKPO_WERKS", length = 4)
	public String getEkpoWerks() {
		return this.ekpoWerks;
	}

	public void setEkpoWerks(String ekpoWerks) {
		this.ekpoWerks = ekpoWerks;
	}

	@Column(name = "EKPO_WERKS_NAME", length = 100)
	public String getEkpoWerksName() {
		return this.ekpoWerksName;
	}

	public void setEkpoWerksName(String ekpoWerksName) {
		this.ekpoWerksName = ekpoWerksName;
	}

	@Column(name = "EKET_EINDT", length = 8)
	public String getEketEindt() {
		return this.eketEindt;
	}

	public void setEketEindt(String eketEindt) {
		this.eketEindt = eketEindt;
	}

	@Column(name = "EKPO_WEBRE", length = 1)
	public String getEkpoWebre() {
		return this.ekpoWebre;
	}

	public void setEkpoWebre(String ekpoWebre) {
		this.ekpoWebre = ekpoWebre;
	}

	@Column(name = "EKPO_MWSKZ", length = 2)
	public String getEkpoMwskz() {
		return this.ekpoMwskz;
	}

	public void setEkpoMwskz(String ekpoMwskz) {
		this.ekpoMwskz = ekpoMwskz;
	}

	@Column(name = "EKPO_MWSKZ_NAME", length = 100)
	public String getEkpoMwskzName() {
		return this.ekpoMwskzName;
	}

	public void setEkpoMwskzName(String ekpoMwskzName) {
		this.ekpoMwskzName = ekpoMwskzName;
	}

	@Column(name = "FACTORY_LOCAL", length = 30)
	public String getFactoryLocal() {
		return this.factoryLocal;
	}

	public void setFactoryLocal(String factoryLocal) {
		this.factoryLocal = factoryLocal;
	}

	@Column(name = "FLOW_NO", length = 30)
	public String getFlowNo() {
		return this.flowNo;
	}

	public void setFlowNo(String flowNo) {
		this.flowNo = flowNo;
	}

	@Column(name = "TOTAL_VALUE", length = 30)
	public String getTotalValue() {
		return this.totalValue;
	}

	public void setTotalValue(String totalValue) {
		this.totalValue = totalValue;
	}

	@Column(name = "PRICE", length = 30)
	public String getPrice() {
		return this.price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	@Column(name = "TAXES", length = 30)
	public String getTaxes() {
		return this.taxes;
	}

	public void setTaxes(String taxes) {
		this.taxes = taxes;
	}

	@Column(name = "TOTAL_TAXES", length = 30)
	public String getTotalTaxes() {
		return this.totalTaxes;
	}

	public void setTotalTaxes(String totalTaxes) {
		this.totalTaxes = totalTaxes;
	}

	@Column(name = "MATERIEL_UNIT", length = 30)
	public String getMaterielUnit() {
		return this.materielUnit;
	}

	public void setMaterielUnit(String materielUnit) {
		this.materielUnit = materielUnit;
	}

	@Column(name = "CREATE_TIME", length = 20)
	public String getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	@Column(name = "CREATOR", length = 36)
	public String getCreator() {
		return this.creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}
	@Column(name = "pur_row_id", length = 36)
	public String getPurRowId() {
		return purRowId;
	}

	public void setPurRowId(String purRowId) {
		this.purRowId = purRowId;
	}
	@Column(name = "tax", length = 40)
	public String getTax() {
		return tax;
	}

	public void setTax(String tax) {
		this.tax = tax;
	}

}