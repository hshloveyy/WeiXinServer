package com.infolion.sapss.contract.domain;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.infolion.sapss.Constants;

/**
 * TContractBom entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_CONTRACT_BOM")
public class TContractBom implements java.io.Serializable {

	// Fields

	private String bomId;
	private String purchaseRowsId;
	private BigDecimal itemNo;
	private String sapRowNo;
	private String materiel;
	private String materielName;
	private String entryQuantity;
	private String entryUom;
	private String plant;
	private String cmd;
	private String createTime;
	private String creator;

	// Constructors

	/** default constructor */
	public TContractBom() {
	}

	/** minimal constructor */
	public TContractBom(String bomId) {
		this.bomId = bomId;
	}

	/** full constructor */
	public TContractBom(String bomId, String purchaseRowsId, BigDecimal itemNo,
			String sapRowNo, String materiel, String materielName,
			String entryQuantity, String entryUom, String plant, String cmd,
			String createTime, String creator) {
		this.bomId = bomId;
		this.purchaseRowsId = purchaseRowsId;
		this.itemNo = itemNo;
		this.sapRowNo = sapRowNo;
		this.materiel = materiel;
		this.materielName = materielName;
		this.entryQuantity = entryQuantity;
		this.entryUom = entryUom;
		this.plant = plant;
		this.cmd = cmd;
		this.createTime = createTime;
		this.creator = creator;
	}

	// Property accessors
	@Id
	@Column(name = "BOM_ID", unique = true,  length = 36)
	public String getBomId() {
		return this.bomId;
	}

	public void setBomId(String bomId) {
		this.bomId = bomId;
	}

	@Column(name = "PURCHASE_ROWS_ID", length = 36)
	public String getPurchaseRowsId() {
		return this.purchaseRowsId;
	}

	public void setPurchaseRowsId(String purchaseRowsId) {
		this.purchaseRowsId = purchaseRowsId;
	}

	@Column(name = "ITEM_NO", precision = 22, scale = 0)
	public BigDecimal getItemNo() {
		return this.itemNo;
	}

	public void setItemNo(BigDecimal itemNo) {
		this.itemNo = itemNo;
	}

	@Column(name = "SAP_ROW_NO", length = 6)
	public String getSapRowNo() {
		return this.sapRowNo;
	}

	public void setSapRowNo(String sapRowNo) {
		this.sapRowNo = sapRowNo;
	}

	@Column(name = "MATERIEL", length = 30)
	public String getMateriel() {
		return this.materiel;
	}

	public void setMateriel(String materiel) {
		this.materiel = materiel;
	}

	@Column(name = "MATERIEL_NAME", length = 100)
	public String getMaterielName() {
		return this.materielName;
	}

	public void setMaterielName(String materielName) {
		this.materielName = materielName;
	}

	@Column(name = "ENTRY_QUANTITY", length = 10)
	public String getEntryQuantity() {
		return this.entryQuantity;
	}

	public void setEntryQuantity(String entryQuantity) {
		this.entryQuantity = entryQuantity;
	}

	@Column(name = "ENTRY_UOM", length = 10)
	public String getEntryUom() {
		return this.entryUom;
	}

	public void setEntryUom(String entryUom) {
		this.entryUom = entryUom;
	}

	@Column(name = "PLANT", length = 4)
	public String getPlant() {
		return this.plant;
	}

	public void setPlant(String plant) {
		this.plant = plant;
	}

	@Column(name = "CMD", length = 200)
	public String getCmd() {
		return this.cmd;
	}

	public void setCmd(String cmd) {
		this.cmd = cmd;
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

}