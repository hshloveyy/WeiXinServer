package com.infolion.sapss.asset.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "T_ASSET_USERHIS")
public class AssetUserHis {
	
	private String assetUserHisId;//主键
	private String assetInfoId;//主资产号
	private String deptId;//使用部门
	private String deptName;//使用部门
	private String userMan;//使用人
	private String startDate;//开始时间
	private String endDate;//结束时间
	private String createrTime;//
	
	@Id
	@Column(name = "assetUserHisId", unique = true, nullable = false, length = 36)
	public String getAssetUserHisId() {
		return assetUserHisId;
	}
	public void setAssetUserHisId(String assetUserHisId) {
		this.assetUserHisId = assetUserHisId;
	}
	@Column(name = "assetInfoId", length = 50)
	public String getAssetInfoId() {
		return assetInfoId;
	}
	public void setAssetInfoId(String assetInfoId) {
		this.assetInfoId = assetInfoId;
	}
	@Column(name = "deptId", length = 50)
	public String getDeptId() {
		return deptId;
	}
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	@Column(name = "userMan", length = 50)
	public String getUserMan() {
		return userMan;
	}
	public void setUserMan(String userMan) {
		this.userMan = userMan;
	}
	@Column(name = "startDate", length = 50)
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	@Column(name = "endDate", length = 50)
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	@Column(name = "createrTime", length = 20)
	public String getCreaterTime() {
		return createrTime;
	}
	public void setCreaterTime(String createrTime) {
		this.createrTime = createrTime;
	}
	@Column(name = "deptName", length = 50)
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
}
