/*
 * @(#)TShipMaterial.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：张崇镇
 *  时　间：Mar 13, 2009
 *  描　述：创建
 */

package com.infolion.sapss.ship.domain;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * <pre></pre>
 * 
 * <br>
 * JDK版本:1.5
 * 
 * @author 张崇镇
 * @version 1.0
 * @see The author for more details
 * @since 1.0
 */
@Entity
@Table(name = "T_SHIP_MATERIAL")
public class TShipMaterial
{
	private String shipDetailId;
	private String shipId;
	private String materialCode;
	private String materialName;
	private String materialUnit;
	private BigDecimal quantity;
	private String batchNo;
	private Double price;
	private String currency;
	private BigDecimal total;
	private String cmd;
	private String isAvailabel;
	private String creatorTime;
	private String creator;
	private String salesRowsId;
	private String exportMateId;
	private String purchaseRowsId;
	private String bomId;
	private String ekpoPeinh;
	private String sapRowNo;
    private String currentClearCharge;//本次清帐金额
	private String isClearFinish;//是否已清
	private Double salePrice;//销售含税单价
	private BigDecimal saleTotal;//销售总价
	private String intendGatherTime;
	private String makeInvoiceTime;
	private String vbapKpein;//销售条件定价单位 VBAP_KPEIN
	private String saleCurrency;//销售币别 SALE_CURRENCY
	
	@Id
	@Column(name = "SHIP_DETAIL_ID", unique = true, length = 36)
	public String getShipDetailId()
	{
		return shipDetailId;
	}

	public void setShipDetailId(String shipDetailId)
	{
		this.shipDetailId = shipDetailId;
	}

	@Column(name = "SHIP_ID", length = 36)
	public String getShipId()
	{
		return shipId;
	}

	public void setShipId(String shipId)
	{
		this.shipId = shipId;
	}

	@Column(name = "MATERIAL_CODE", length = 50)
	public String getMaterialCode()
	{
		return materialCode;
	}

	public void setMaterialCode(String materialCode)
	{
		this.materialCode = materialCode;
	}

	@Column(name = "MATERIAL_NAME", length = 200)
	public String getMaterialName()
	{
		return materialName;
	}

	public void setMaterialName(String materialName)
	{
		this.materialName = materialName;
	}

	@Column(name = "MATERIAL_UNIT", length = 50)
	public String getMaterialUnit()
	{
		return materialUnit;
	}

	public void setMaterialUnit(String materialUnit)
	{
		this.materialUnit = materialUnit;
	}

	@Column(name = "QUANTITY", precision = 22, scale = 3)
	public BigDecimal getQuantity()
	{
		return quantity;
	}

	public void setQuantity(BigDecimal quantity)
	{
		this.quantity = quantity;
	}

	@Column(name = "BATCH_NO", length = 20)
	public String getBatchNo()
	{
		return batchNo;
	}

	public void setBatchNo(String batchNo)
	{
		this.batchNo = batchNo;
	}

	@Column(name = "PRICE", precision = 22, scale = 3)
	public Double getPrice()
	{
		return price;
	}

	public void setPrice(Double price)
	{
		this.price = price;
	}

	@Column(name = "CURRENCY", length = 50)
	public String getCurrency()
	{
		return currency;
	}

	public void setCurrency(String currency)
	{
		this.currency = currency;
	}

	@Column(name = "TOTAL", precision = 22, scale = 3)
	public BigDecimal getTotal()
	{
		return total;
	}

	public void setTotal(BigDecimal total)
	{
		this.total = total;
	}

	@Column(name = "CMD", length = 200)
	public String getCmd()
	{
		return cmd;
	}

	public void setCmd(String cmd)
	{
		this.cmd = cmd;
	}

	@Column(name = "IS_AVAILABLE", length = 1)
	public String getIsAvailabel()
	{
		return isAvailabel;
	}

	public void setIsAvailabel(String isAvailabel)
	{
		this.isAvailabel = isAvailabel;
	}

	@Column(name = "CREATOR_TIME", length = 20)
	public String getCreatorTime()
	{
		return creatorTime;
	}

	public void setCreatorTime(String creatorTime)
	{
		this.creatorTime = creatorTime;
	}

	@Column(name = "CREATOR", length = 36)
	public String getCreator()
	{
		return creator;
	}
	public void setCreator(String creator)
	{
		this.creator = creator;
	}
	
	@Column(name = "SALES_ROWS_ID", length = 36)
	public String getSalesRowsId()
	{
		return salesRowsId;
	}
	public void setSalesRowsId(String salesRowsId)
	{
		this.salesRowsId = salesRowsId;
	}
	
	@Column(name = "EXPORT_MATE_ID", length = 36)
	public String getExportMateId()
	{
		return exportMateId;
	}
	public void setExportMateId(String exportMateId)
	{
		this.exportMateId = exportMateId;
	}

	@Column(name = "PURCHASE_ROWS_ID", length = 36)
	public String getPurchaseRowsId()
	{
		return purchaseRowsId;
	}
	public void setPurchaseRowsId(String purchaseRowsId)
	{
		this.purchaseRowsId = purchaseRowsId;
	}
	
	@Column(name = "BOM_ID", length = 36)
	public String getBomId()
	{
		return bomId;
	}
	public void setBomId(String bomId)
	{
		this.bomId = bomId;
	}	
	@Column(name = "EKPO_PEINH", length = 10)	
	public String getEkpoPeinh()
	{
		return ekpoPeinh;
	}
	
	public void setEkpoPeinh(String ekpoPeinh)
	{
		this.ekpoPeinh = ekpoPeinh;
	}
	@Column(name = "SAP_ROW_NO",  length = 20)	
	public String getSapRowNo() {
		return sapRowNo;
	}

	public void setSapRowNo(String sapRowNo) {
		this.sapRowNo = sapRowNo;
	}

	@Column(name = "CURRENT_CLEAR_CHARGE",  precision = 14, scale = 2)
	public String getCurrentClearCharge()
	{
		return currentClearCharge;
	}

	public void setCurrentClearCharge(String currentClearCharge)
	{
		this.currentClearCharge = currentClearCharge;
	}
	
	@Column(name = "IS_CLEAR_FINISH", length = 2)	
	public String getIsClearFinish()
	{
		return isClearFinish;
	}

	public void setIsClearFinish(String isClearFinish)
	{
		this.isClearFinish = isClearFinish;
	}
	
	@Column(name = "SALE_PRICE",  precision = 14, scale = 2)
	public Double getSalePrice()
	{
		return salePrice;
	}

	public void setSalePrice(Double salePrice)
	{
		this.salePrice = salePrice;
	}
	
	@Column(name = "SALE_TOTAL",  precision = 14, scale = 2)
	public BigDecimal getSaleTotal()
	{
		return saleTotal;
	}

	public void setSaleTotal(BigDecimal saleTotal)
	{
		this.saleTotal = saleTotal;
	}
	
	@Column(name = "INTEND_GATHER_TIME", length=20)
	public String getIntendGatherTime()
	{
		return intendGatherTime;
	}

	public void setIntendGatherTime(String intendGatherTime)
	{
		this.intendGatherTime = intendGatherTime;
	}
	
	@Column(name = "MAKE_INVOICE_TIME", length=20)
	public String getMakeInvoiceTime()
	{
		return makeInvoiceTime;
	}

	public void setMakeInvoiceTime(String makeInvoiceTime)
	{
		this.makeInvoiceTime = makeInvoiceTime;
	}
	@Column(name = "VBAP_KPEIN", length=10)
	public String getVbapKpein() {
		return vbapKpein;
	}

	public void setVbapKpein(String vbapKpein) {
		this.vbapKpein = vbapKpein;
	}
	@Column(name = "SALE_CURRENCY", length=5)
	public String getSaleCurrency() {
		return saleCurrency;
	}

	public void setSaleCurrency(String saleCurrency) {
		this.saleCurrency = saleCurrency;
	}
}
