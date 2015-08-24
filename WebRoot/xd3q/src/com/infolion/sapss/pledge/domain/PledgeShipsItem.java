package com.infolion.sapss.pledge.domain;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name = "T_PLEDGESHIPS_ITEM")
public class PledgeShipsItem {
		
	private String pledgeShipsItemId;
	private String pledgeShipsId;
	private String materialCode;
	private String materialName;
	private String materialUnit;
	private Double quantity;
	private String batchNo;
	private BigDecimal price;
	private String ekpoPeinh;
	private String currency;
	private BigDecimal total;
	private String cmd;
	private String creatorTime;
	private String creator;
	@Id
	@Column(name = "PLEDGESHIPS_ITEM_ID", unique = true, length = 36)
	public String getPledgeShipsItemId() {
		return pledgeShipsItemId;
	}
	public void setPledgeShipsItemId(String pledgeShipsItemId) {
		this.pledgeShipsItemId = pledgeShipsItemId;
	}
	@Column(name = "PLEDGESHIPS_ID", length = 36)
	public String getPledgeShipsId() {
		return pledgeShipsId;
	}
	public void setPledgeShipsId(String pledgeShipsId) {
		this.pledgeShipsId = pledgeShipsId;
	}
	@Column(name = "MATERIAL_CODE", length = 50)
	public String getMaterialCode() {
		return materialCode;
	}
	public void setMaterialCode(String materialCode) {
		this.materialCode = materialCode;
	}
	@Column(name = "MATERIAL_NAME", length = 200)
	public String getMaterialName() {
		return materialName;
	}
	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}
	@Column(name = "MATERIAL_UNIT", length = 50)
	public String getMaterialUnit() {
		return materialUnit;
	}
	public void setMaterialUnit(String materialUnit) {
		this.materialUnit = materialUnit;
	}
	@Column(name = "QUANTITY", precision = 22, scale = 3)
	public Double getQuantity() {
		return quantity;
	}
	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}
	@Column(name = "BATCH_NO", length = 20)
	public String getBatchNo() {
		return batchNo;
	}
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}
	@Column(name = "PRICE", precision = 22, scale = 3)
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	@Column(name="EKPO_PEINH",length=10)
	public String getEkpoPeinh() {
		return ekpoPeinh;
	}
	public void setEkpoPeinh(String ekpoPeinh) {
		this.ekpoPeinh = ekpoPeinh;
	}
	@Column(name = "CURRENCY", length = 50)
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	@Column(name = "TOTAL", precision = 22, scale = 3)
	public BigDecimal getTotal() {
		return total;
	}
	public void setTotal(BigDecimal total) {
		this.total = total;
	}
	@Column(name = "CMD", length = 200)
	public String getCmd() {
		return cmd;
	}
	public void setCmd(String cmd) {
		this.cmd = cmd;
	}
	@Column(name = "CREATOR_TIME", length = 20)
	public String getCreatorTime() {
		return creatorTime;
	}
	public void setCreatorTime(String creatorTime) {
		this.creatorTime = creatorTime;
	}
	@Column(name = "CREATOR", length = 36)
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
}
