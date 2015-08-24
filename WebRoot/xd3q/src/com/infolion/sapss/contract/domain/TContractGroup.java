package com.infolion.sapss.contract.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.infolion.platform.core.domain.BaseObject;
import com.infolion.sapss.Constants;

/**
 * TContractGroup entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_CONTRACT_GROUP")
public class TContractGroup extends BaseObject {

	// Fields

	private String contractGroupId;
	private String projectId;
	private String contractGroupName;
	private String contractGroupNo;
	private String projectName;
	private String cmd;
	private String isAvailable;
	private String deptId;
	private String createTime;
	private String creator;
	private Integer tradeType;

	// Property accessors
	@Id
	@Column(name = "CONTRACT_GROUP_ID", unique = true,  length = 36)
	public String getContractGroupId() {
		return this.contractGroupId;
	}

	public void setContractGroupId(String contractGroupId) {
		this.contractGroupId = contractGroupId;
	}

	@Column(name = "PROJECT_ID", length = 36)
	public String getProjectId() {
		return this.projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	@Column(name = "CONTRACT_GROUP_NAME",  length = 200)
	public String getContractGroupName() {
		return this.contractGroupName;
	}

	public void setContractGroupName(String contractGroupName) {
		this.contractGroupName = contractGroupName;
	}

	@Column(name = "CONTRACT_GROUP_NO", length = 100)
	public String getContractGroupNo() {
		return this.contractGroupNo;
	}

	public void setContractGroupNo(String contractGroupNo) {
		this.contractGroupNo = contractGroupNo;
	}

	@Column(name = "PROJECT_NAME", length = 200)
	public String getProjectName() {
		return this.projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	@Column(name = "CMD", length = 200)
	public String getCmd() {
		return this.cmd;
	}

	public void setCmd(String cmd) {
		this.cmd = cmd;
	}

	@Column(name = "IS_AVAILABLE", length = 1)
	public String getIsAvailable() {
		return this.isAvailable;
	}

	public void setIsAvailable(String isAvailable) {
		this.isAvailable = isAvailable;
	}

	@Column(name = "DEPT_ID", length = 36)
	public String getDeptId() {
		return this.deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	@Column(name = "CREATE_TIME", length = 14)
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
	
	@Column(name = "TRADE_TYPE", precision = 22, scale = 0)
	public Integer getTradeType() {
		return this.tradeType;
	}

	public void setTradeType(Integer tradeType) {
		this.tradeType = tradeType;
	}

}