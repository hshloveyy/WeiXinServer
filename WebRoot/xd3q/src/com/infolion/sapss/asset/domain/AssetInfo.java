package com.infolion.sapss.asset.domain;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.infolion.platform.core.annotation.ValidateRule;
import com.infolion.platform.core.domain.BaseObject;
import com.infolion.platform.core.validation.DataType;

@Entity
@Table(name = "T_ASSET_INFO")
public class AssetInfo extends BaseObject{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String assetInfoId;//外围资产号（主键）
	@ValidateRule(dataType = DataType.STRING, label = "公司代码", maxLength = 10, required = true)
	private String comCode;//公司代码
	@ValidateRule(dataType = DataType.STRING, label = "资产分类", maxLength = 10, required = true)
	private String assetType;//资产分类
	private String businessScope;//业务范围
	@ValidateRule(dataType = DataType.STRING, label = "成本中心", maxLength = 10, required = true)
	private String costCenter;//成本中心
	private String costCenterName;//成本中心
	@ValidateRule(dataType = DataType.STRING, label = "资产名称", maxLength = 200, required = true)
	private String assetName;//资产名称
	@ValidateRule(dataType = DataType.STRING, label = "型号配置", maxLength = 200, required = true)
	private String assetConfig;//型号配置
	@ValidateRule(dataType = DataType.STRING, label = "设备序列号", maxLength = 200, required = true)
	private String assetSerialNo;//设备序列号
	@ValidateRule(dataType = DataType.STRING, label = "外部编号", maxLength = 200, required = true)
	private String outsideNo;//外部编号
	@ValidateRule(dataType = DataType.STRING, label = "资产名称", maxLength = 200, required = true)
	private String location;//存放位置
	private int quality;//数量
	private String measure;//计量单位
	@ValidateRule(dataType = DataType.STRING, label = "购置时间", maxLength = 10, required = true)
	private String purchaseDate;//购置时间
	private String contractPuchaseNo;//采购合同号
	@ValidateRule(dataType = DataType.STRING, label = "采购金额", maxLength = 10, required = false)
	private BigDecimal cost;//采购金额
	private String supplierName;//供应商名称
	private String purchaseLinkMan;//采购联系人
	private String purchaseLinkTel;//采购联系电话
	private String guaranteeDate;//保修期
	private String maintainTel;//维修电话
	private String depreciationYear;//折旧年限
	private String scrapDate;//预计报废时间
	@ValidateRule(dataType = DataType.STRING, label = "资产使用状态", maxLength = 10, required = true)
	private String state;//资产使用状态 在用，拟报废，报废
	private String memo;//备注
	private String sapAssetNo;//SAP资产号	
	@ValidateRule(dataType = DataType.STRING, label = "资产类别", maxLength = 10, required = true)
	private String category;//资产类别（即四大分类）	固定资产、低值易耗品和低值耐用品，在用无值资产
	
	private String frozenMark;//卡片状态 1为冻结
	private String frozenReason;//冻结原因
	
	private String deptId;//
	private String creator;//
	private String createrTime;//
	private String lastModifyTime;//
	private String isAvailable;
	
	@Id
	@Column(name = "assetInfoId", unique = true, nullable = false, length = 36)
	public String getAssetInfoId() {
		return assetInfoId;
	}
	public void setAssetInfoId(String assetInfoId) {
		this.assetInfoId = assetInfoId;
	}
	@Column(name = "comCode", length = 50)
	public String getComCode() {
		return comCode;
	}
	public void setComCode(String comCode) {
		this.comCode = comCode;
	}
	@Column(name = "assetType", length = 50)
	public String getAssetType() {
		return assetType;
	}
	public void setAssetType(String assetType) {
		this.assetType = assetType;
	}
	@Column(name = "businessScope", length = 50)
	public String getBusinessScope() {
		return businessScope;
	}
	public void setBusinessScope(String businessScope) {
		this.businessScope = businessScope;
	}
	@Column(name = "costCenter", length = 50)
	public String getCostCenter() {
		return costCenter;
	}
	public void setCostCenter(String costCenter) {
		this.costCenter = costCenter;
	}
	@Column(name = "costCenterName", length = 50)
	public String getCostCenterName() {
		return costCenterName;
	}
	public void setCostCenterName(String costCenterName) {
		this.costCenterName = costCenterName;
	}
	@Column(name = "assetName", length = 50)
	public String getAssetName() {
		return assetName;
	}
	public void setAssetName(String assetName) {
		this.assetName = assetName;
	}
	@Column(name = "assetConfig", length = 50)
	public String getAssetConfig() {
		return assetConfig;
	}
	public void setAssetConfig(String assetConfig) {
		this.assetConfig = assetConfig;
	}
	@Column(name = "assetSerialNo", length = 50)
	public String getAssetSerialNo() {
		return assetSerialNo;
	}
	public void setAssetSerialNo(String assetSerialNo) {
		this.assetSerialNo = assetSerialNo;
	}
	@Column(name = "outsideNo", length = 50)
	public String getOutsideNo() {
		return outsideNo;
	}
	public void setOutsideNo(String outsideNo) {
		this.outsideNo = outsideNo;
	}
	@Column(name = "location", length = 50)
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	@Column(name = "quality", length = 50)
	public int getQuality() {
		return quality;
	}
	public void setQuality(int quality) {
		this.quality = quality;
	}
	@Column(name = "measure", length = 50)
	public String getMeasure() {
		return measure;
	}
	public void setMeasure(String measure) {
		this.measure = measure;
	}
	@Column(name = "purchaseDate", length = 50)
	public String getPurchaseDate() {
		return purchaseDate;
	}
	public void setPurchaseDate(String purchaseDate) {
		this.purchaseDate = purchaseDate;
	}
	@Column(name = "contractPuchaseNo", length = 50)
	public String getContractPuchaseNo() {
		return contractPuchaseNo;
	}
	public void setContractPuchaseNo(String contractPuchaseNo) {
		this.contractPuchaseNo = contractPuchaseNo;
	}
	@Column(name = "cost", length = 50)
	public BigDecimal getCost() {
		return cost;
	}
	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}
	@Column(name = "supplierName", length = 50)
	public String getSupplierName() {
		return supplierName;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	@Column(name = "purchaseLinkMan", length = 50)
	public String getPurchaseLinkMan() {
		return purchaseLinkMan;
	}
	public void setPurchaseLinkMan(String purchaseLinkMan) {
		this.purchaseLinkMan = purchaseLinkMan;
	}
	@Column(name = "purchaseLinkTel", length = 50)
	public String getPurchaseLinkTel() {
		return purchaseLinkTel;
	}
	public void setPurchaseLinkTel(String purchaseLinkTel) {
		this.purchaseLinkTel = purchaseLinkTel;
	}
	@Column(name = "guaranteeDate", length = 50)
	public String getGuaranteeDate() {
		return guaranteeDate;
	}
	public void setGuaranteeDate(String guaranteeDate) {
		this.guaranteeDate = guaranteeDate;
	}
	@Column(name = "maintainTel", length = 50)
	public String getMaintainTel() {
		return maintainTel;
	}
	public void setMaintainTel(String maintainTel) {
		this.maintainTel = maintainTel;
	}
	@Column(name = "depreciationYear", length = 50)
	public String getDepreciationYear() {
		return depreciationYear;
	}
	public void setDepreciationYear(String depreciationYear) {
		this.depreciationYear = depreciationYear;
	}
	@Column(name = "scrapDate", length = 50)
	public String getScrapDate() {
		return scrapDate;
	}
	public void setScrapDate(String scrapDate) {
		this.scrapDate = scrapDate;
	}
	@Column(name = "state", length = 50)
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	@Column(name = "memo", length = 50)
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	@Column(name = "sapAssetNo", length = 50)
	public String getSapAssetNo() {
		return sapAssetNo;
	}
	public void setSapAssetNo(String sapAssetNo) {
		this.sapAssetNo = sapAssetNo;
	}
	@Column(name = "category", length = 50)
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	@Column(name = "deptId", length = 50)
	public String getDeptId() {
		return deptId;
	}
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	@Column(name = "creator", length = 50)
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	@Column(name = "createrTime", length = 50)
	public String getCreaterTime() {
		return createrTime;
	}
	public void setCreaterTime(String createrTime) {
		this.createrTime = createrTime;
	}
	@Column(name = "lastModifyTime", length = 50)
	public String getLastModifyTime() {
		return lastModifyTime;
	}
	public void setLastModifyTime(String lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}
	@Column(name = "isAvailable", length = 50)
	public String getIsAvailable() {
		return isAvailable;
	}
	public void setIsAvailable(String isAvailable) {
		this.isAvailable = isAvailable;
	}
	@Column(name = "frozenMark", length = 50)
	public String getFrozenMark() {
		return frozenMark;
	}
	public void setFrozenMark(String frozenMark) {
		this.frozenMark = frozenMark;
	}
	@Column(name = "frozenReason", length = 50)
	public String getFrozenReason() {
		return frozenReason;
	}
	public void setFrozenReason(String frozenReason) {
		this.frozenReason = frozenReason;
	}
	



}
