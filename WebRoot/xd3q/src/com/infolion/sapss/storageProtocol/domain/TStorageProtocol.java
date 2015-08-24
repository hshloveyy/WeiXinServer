package com.infolion.sapss.storageProtocol.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.infolion.platform.core.annotation.ValidateRule;
import com.infolion.platform.core.domain.ProcessObject;
import com.infolion.platform.core.validation.DataType;

/**
 * TInvestmentInfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_STORAGE_PROTOCOL")
public class TStorageProtocol extends ProcessObject implements java.io.Serializable {

	// Fields

	private String infoId;
	private String deptName;
	private String deptId;
	private String creator;
	private String creatorId;
	private String createTime;
	private String isAvailable;
	private String status;
	@ValidateRule(dataType=DataType.STRING,label="申请类型",required=true)
	private String applyType;
	private String applyTime;
	private String availableTime;
	private String approvedTime;
	private String cmd;
	private String projectNo;
	private String projectId;
	
	private String com;//企业
	private String goods;//货物名称
	private String protocolNo;//协议号
	private String comNo;//客户或供应商编码
	private String comType;//客户或供应商：1为客户，2为供应商

	// Constructors

	/** default constructor */
	public TStorageProtocol() {
	}

	/** minimal constructor */
	public TStorageProtocol(String infoId) {
		this.infoId = infoId;
	}

	/** full constructor */
	public TStorageProtocol(String infoId, String deptName, String deptId,
			String creator, String creatorId, String createTime,
			String isAvailable, String status,
			String applyType, String applyTime,
			String availableTime, String cmd) {
		this.infoId = infoId;
		this.deptName = deptName;
		this.deptId = deptId;
		this.creator = creator;
		this.creatorId = creatorId;
		this.createTime = createTime;
		this.isAvailable = isAvailable;
		this.status = status;
		this.applyType = applyType;
		this.applyTime = applyTime;
		this.availableTime = availableTime;
		this.cmd = cmd;
	}

	// Property accessors
	@Id
	@Column(name = "INFO_ID", unique = true, nullable = false, length = 36)
	public String getInfoId() {
		return this.infoId;
	}

	public void setInfoId(String infoId) {
		this.infoId = infoId;
	}

	@Column(name = "DEPT_NAME", length = 100)
	public String getDeptName() {
		return this.deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	@Column(name = "DEPT_ID", length = 36)
	public String getDeptId() {
		return this.deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	@Column(name = "CREATOR", length = 100)
	public String getCreator() {
		return this.creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	@Column(name = "CREATOR_ID", length = 36)
	public String getCreatorId() {
		return this.creatorId;
	}

	public void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
	}

	@Column(name = "CREATE_TIME", length = 20)
	public String getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	@Column(name = "IS_AVAILABLE", length = 1)
	public String getIsAvailable() {
		return this.isAvailable;
	}

	public void setIsAvailable(String isAvailable) {
		this.isAvailable = isAvailable;
	}

	@Column(name = "STATUS", length = 2)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "APPLY_TYPE", length = 2)
	public String getApplyType() {
		return this.applyType;
	}

	public void setApplyType(String applyType) {
		this.applyType = applyType;
	}


	@Column(name = "apply_time", length = 20)
	public String getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(String applyTime) {
		this.applyTime = applyTime;
	}
	@Column(name = "available_time", length = 20)
	public String getAvailableTime() {
		return availableTime;
	}

	public void setAvailableTime(String availableTime) {
		this.availableTime = availableTime;
	}

	@Column(name = "CMD", length = 1000)
	public String getCmd() {
		return this.cmd;
	}

	public void setCmd(String cmd) {
		this.cmd = cmd;
	}

	@Override
	public void setWorkflowModelId() {
		this.workflowModelId="1";
		
	}

	@Override
	public void setWorkflowModelName() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setWorkflowProcessName() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setWorkflowProcessUrl() {
		// TODO Auto-generated method stub
		
	}
	@Column(name = "approved_time", length = 20)
	public String getApprovedTime() {
		return approvedTime;
	}

	public void setApprovedTime(String approvedTime) {
		this.approvedTime = approvedTime;
	}
	@Column(name = "project_No", length = 20)
	public String getProjectNo() {
		return projectNo;
	}

	public void setProjectNo(String projectNo) {
		this.projectNo = projectNo;
	}
	@Column(name = "project_Id", length = 36)
	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	@Column(name = "com", length = 300)
	public String getCom() {
		return com;
	}

	public void setCom(String com) {
		this.com = com;
	}
	@Column(name = "goods", length = 300)
	public String getGoods() {
		return goods;
	}

	public void setGoods(String goods) {
		this.goods = goods;
	}
	@Column(name = "protocol_No", length = 300)
	public String getProtocolNo() {
		return protocolNo;
	}

	public void setProtocolNo(String protocolNo) {
		this.protocolNo = protocolNo;
	}
	@Column(name = "comNo", length = 30)
	public String getComNo() {
		return comNo;
	}

	public void setComNo(String comNo) {
		this.comNo = comNo;
	}
	@Column(name = "comType", length = 2)
	public String getComType() {
		return comType;
	}

	public void setComType(String comType) {
		this.comType = comType;
	}

}