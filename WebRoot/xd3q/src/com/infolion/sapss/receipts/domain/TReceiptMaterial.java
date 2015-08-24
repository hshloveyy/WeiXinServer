/*
 * @(#)TShipMaterial.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：王懿璞
 *  时　间：Mar 13, 2009
 *  描　述：创建
 */

package com.infolion.sapss.receipts.domain;

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
 * @author 王懿璞 
 * @version 1.0
 * @see The author for more details
 * @since 1.0
 */
@Entity
@Table(name = "T_RECEIPT_MATERIAL")
public class TReceiptMaterial
{
	private String ReceiptDetailId;
	private String ReceiptId;
	private String purchaseRowsId;
	private String materialCode;
	private String materialName;
	private String materialUnit;
	private Double quantity;
	private String batchNo;
	private Double price;
	private String ekpoPeinh;
	private String currency;
	private Double total;
	private String cmd;
	private String isAvailabel;
	private String creatorTime;
	private String creator;
	private String sapRowNo;
	private String sendGoodsDate;
	private String payAbleDate;
	private String receiveBillDate;
	private String receiptTime;
	private String salesPrice;
	private String currentClearCharge;
	private BigDecimal costPrice;
	private BigDecimal costTotal;

	@Id
	@Column(name = "RECEIPT_DETAIL_ID", unique = true, length = 36)
	public String getReceiptDetailId()
	{
		return ReceiptDetailId;
	}

	public void setReceiptDetailId(String ReceiptDetailId)
	{
		this.ReceiptDetailId = ReceiptDetailId;
	}

	@Column(name = "RECEIPT_ID", length = 36)
	public String getReceiptId()
	{
		return ReceiptId;
	}
	
	public void setReceiptId(String ReceiptId)
	{
		this.ReceiptId = ReceiptId;
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
	public Double getQuantity()
	{
		return quantity;
	}

	public void setQuantity(double quantity)
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
	
	@Column(name = "ekpo_Peinh", length=10)
	public String getEkpoPeinh()
	{
		return ekpoPeinh;
	}

	public void setEkpoPeinh(String ekpoPeinh)
	{
		this.ekpoPeinh = ekpoPeinh;
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
	public Double getTotal()
	{
		return total;
	}

	public void setTotal(Double total)
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
	@Column(name = "SAP_ROW_NO", length = 36)
	public String getSapRowNo() {
		return sapRowNo;
	}

	public void setSapRowNo(String sapRowNo) {
		this.sapRowNo = sapRowNo;
	}
	
	@Column(name = "RECEIPTTIME", length = 20)
	public String getReceiptTime() {
		return receiptTime;
	}

	public void setReceiptTime(String receiptTime) {
		this.receiptTime = receiptTime;
	}
	
	@Column(name = "PAYABLEDATE", length = 20)
	public String getPayAbleDate()
	{
		return payAbleDate;
	}

	public void setPayAbleDate(String payAbleDate)
	{
		this.payAbleDate = payAbleDate;
	}
	
	@Column(name = "RECEIVEBILLDATE", length = 20)
	public String getReceiveBillDate()
	{
		return receiveBillDate;
	}

	public void setReceiveBillDate(String receiveBillDate)
	{
		this.receiveBillDate = receiveBillDate;
	}
	
	@Column(name = "SENDGOODSDATE", length = 20)
	public String getSendGoodsDate()
	{
		return sendGoodsDate;
	}

	public void setSendGoodsDate(String sendGoodsDate)
	{
		this.sendGoodsDate = sendGoodsDate;
	}
	
	@Column(name = "SALESPRICE", length = 20)
	public String getSalesPrice()
	{
		return salesPrice;
	}

	public void setSalesPrice(String salesPrice)
	{
		this.salesPrice = salesPrice;
	}
	
	@Column(name = "CURRENT_CLEAR_CHARGE", length = 20)
	public String getCurrentClearCharge()
	{
		return currentClearCharge;
	}

	public void setCurrentClearCharge(String currentClearCharge)
	{
		this.currentClearCharge = currentClearCharge;
	}
	@Column(name = "COST_PRICE", length = 20)
	public BigDecimal getCostPrice() {
		return costPrice;
	}

	public void setCostPrice(BigDecimal costPrice) {
		this.costPrice = costPrice;
	}
	@Column(name = "COST_TOTAL", length = 20)
	public BigDecimal getCostTotal() {
		return costTotal;
	}

	public void setCostTotal(BigDecimal costTotal) {
		this.costTotal = costTotal;
	}
}
