/*
 * @(#)TExportApplyMaterial.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：张崇镇
 *  时　间：Mar 9, 2009
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
 * JDK版本:1.4
 *
 * @author 张崇镇
 * @version 1.0
 * @see The author for more details
 * @since 1.0
 */
@Entity
@Table(name = "T_EXPORT_APPLY_MATERIAL")
public class TExportApplyMaterial
{
	private String exportMateId;
	private String exportApplyId;
	private String materialCode;
	private String materialName;
	private String materialUnit;
	private BigDecimal quantity;
	private BigDecimal grossWeight;
	private BigDecimal netWeight;
	private Double rate;
	private Double price;
	private String currency;
	private BigDecimal total;
	private String cmd;
	private String isAvailabel;
	private String creatorTime;
	private String creator;
	private String peinh;
	private String salesRowsId;
	private String agentMaterialId;
	private String sapRowNo;

	@Id
	@Column(name = "EXPORT_MATE_ID", unique = true, length = 36)
	public String getExportMateId()
	{
		return exportMateId;
	}

	public void setExportMateId(String exportMateId)
	{
		this.exportMateId = exportMateId;
	}

	@Column(name = "EXPORT_APPLY_ID",  length = 36)	
	public String getExportApplyId()
	{
		return exportApplyId;
	}

	public void setExportApplyId(String exportApplyId)
	{
		this.exportApplyId = exportApplyId;
	}
	@Column(name = "MATERIAL_CODE",  length = 50)	
	public String getMaterialCode()
	{
		return materialCode;
	}

	public void setMaterialCode(String materialCode)
	{
		this.materialCode = materialCode;
	}
	@Column(name = "MATERIAL_NAME",  length = 200)	
	public String getMaterialName()
	{
		return materialName;
	}

	public void setMaterialName(String materialName)
	{
		this.materialName = materialName;
	}
	@Column(name = "MATERIAL_UNIT",  length = 50)	
	public String getMaterialUnit()
	{
		return materialUnit;
	}

	public void setMaterialUnit(String materialUnit)
	{
		this.materialUnit = materialUnit;
	}
	@Column(name = "QUANTITY",   precision = 22, scale = 3)	
	public BigDecimal getQuantity()
	{
		return quantity;
	}

	public void setQuantity(BigDecimal quantity)
	{
		this.quantity = quantity;
	}

	@Column(name = "GROSS_WEIGHT",   precision = 22, scale = 3)		
	public BigDecimal getGrossWeight()
	{
		return grossWeight;
	}

	public void setGrossWeight(BigDecimal grossWeight)
	{
		this.grossWeight = grossWeight;
	}
	@Column(name = "NET_WEIGHT",   precision = 22, scale = 3)		
	public BigDecimal getNetWeight()
	{
		return netWeight;
	}

	public void setNetWeight(BigDecimal netWeight)
	{
		this.netWeight = netWeight;
	}
	@Column(name = "RATE",   precision = 22, scale = 3)	
	public Double getRate()
	{
		return rate;
	}

	public void setRate(Double rate)
	{
		this.rate = rate;
	}
	@Column(name = "PRICE",   precision = 22, scale = 3)	
	public Double getPrice()
	{
		return price;
	}

	public void setPrice(Double price)
	{
		this.price = price;
	}
	@Column(name = "CURRENCY",  length = 50)
	public String getCurrency()
	{
		return currency;
	}

	public void setCurrency(String currency)
	{
		this.currency = currency;
	}
	@Column(name = "TOTAL",   precision = 22, scale = 3)	
	public BigDecimal getTotal()
	{
		return total;
	}

	public void setTotal(BigDecimal total)
	{
		this.total = total;
	}
	@Column(name = "CMD",  length = 200)
	public String getCmd()
	{
		return cmd;
	}

	public void setCmd(String cmd)
	{
		this.cmd = cmd;
	}
	@Column(name = "IS_AVAILABLE",  length = 1)
	public String getIsAvailabel()
	{
		return isAvailabel;
	}

	public void setIsAvailabel(String isAvailabel)
	{
		this.isAvailabel = isAvailabel;
	}
	@Column(name = "CREATOR_TIME",  length = 20)
	public String getCreatorTime()
	{
		return creatorTime;
	}

	public void setCreatorTime(String creatorTime)
	{
		this.creatorTime = creatorTime;
	}
	@Column(name = "CREATOR",  length = 36)
	public String getCreator()
	{
		return creator;
	}

	public void setCreator(String creator)
	{
		this.creator = creator;
	}

	@Column(name = "PEINH",  length = 36)	
	public String getPeinh()
	{
		return peinh;
	}

	public void setPeinh(String peinh)
	{
		this.peinh = peinh;
	}
	@Column(name = "SALES_ROWS_ID",  length = 36)	
	public String getSalesRowsId()
	{
		return salesRowsId;
	}

	public void setSalesRowsId(String salesRowsId)
	{
		this.salesRowsId = salesRowsId;
	}
	@Column(name = "AGENT_MATERIAL_ID",  length = 36)	
	public String getAgentMaterialId()
	{
		return agentMaterialId;
	}

	public void setAgentMaterialId(String agentMaterialId)
	{
		this.agentMaterialId = agentMaterialId;
	}
	@Column(name = "SAP_ROW_NO",  length = 20)	
	public String getSapRowNo() {
		return sapRowNo;
	}

	public void setSapRowNo(String sapRowNo) {
		this.sapRowNo = sapRowNo;
	}

	
}
