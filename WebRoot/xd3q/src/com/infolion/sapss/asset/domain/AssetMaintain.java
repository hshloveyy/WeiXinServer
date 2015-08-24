package com.infolion.sapss.asset.domain;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.infolion.platform.core.domain.BaseObject;
@Entity
@Table(name = "T_ASSET_MAINTAIN")
public class AssetMaintain extends BaseObject{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String assetMaintainId;//主键
	private String assetInfoId;//主资产号
	private String maintainDate;//维修日期
	private String maintainComName;//维修公司名称
	private BigDecimal maintainMoney;//维修费用
	private String memo;//备注
	private String createrTime;//创建时间
	
	@Id
	@Column(name = "assetMaintainId", unique = true, nullable = false, length = 36)
	public String getAssetMaintainId() {
		return assetMaintainId;
	}
	public void setAssetMaintainId(String assetMaintainId) {
		this.assetMaintainId = assetMaintainId;
	}
	@Column(name = "assetInfoId", length = 50)
	public String getAssetInfoId() {
		return assetInfoId;
	}
	public void setAssetInfoId(String assetInfoId) {
		this.assetInfoId = assetInfoId;
	}
	@Column(name = "maintainDate", length = 50)
	public String getMaintainDate() {
		return maintainDate;
	}
	public void setMaintainDate(String maintainDate) {
		this.maintainDate = maintainDate;
	}
	@Column(name = "maintainComName", length = 50)
	public String getMaintainComName() {
		return maintainComName;
	}
	public void setMaintainComName(String maintainComName) {
		this.maintainComName = maintainComName;
	}
	@Column(name = "maintainMoney", length = 50)
	public BigDecimal getMaintainMoney() {
		return maintainMoney;
	}
	public void setMaintainMoney(BigDecimal maintainMoney) {
		this.maintainMoney = maintainMoney;
	}
	@Column(name = "memo", length = 50)
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	@Column(name = "createrTime", length = 20)
	public String getCreaterTime() {
		return createrTime;
	}
	public void setCreaterTime(String createrTime) {
		this.createrTime = createrTime;
	}

}
