package com.infolion.sapss.investment.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.infolion.platform.core.annotation.ValidateRule;
import com.infolion.platform.core.domain.ProcessObject;

/**
 * TInvestmentInfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_INVESTMENT_INFO")
public class TInvestmentInfo extends ProcessObject implements java.io.Serializable {

	// Fields

	private String infoId;
	private String deptName;
	private String deptId;
	private String creator;
	private String creatorId;
	private String createTime;
	private String isAvailable;
	private String status;
	@ValidateRule(required=true,label="投资类型")
	private String investmentType;
	private String examineType;
	@ValidateRule(required=true,label="子流程")
	private String subFlow;
	private String approvedTime;
	private String availableTime;
	private String cmd;
	@ValidateRule(required=true,label="审批子类")
	private String childType;

	// Constructors

	/** default constructor */
	public TInvestmentInfo() {
	}

	/** minimal constructor */
	public TInvestmentInfo(String infoId) {
		this.infoId = infoId;
	}

	/** full constructor */
	public TInvestmentInfo(String infoId, String deptName, String deptId,
			String creator, String creatorId, String createTime,
			String isAvailable, String status, String investmentType,
			String examineType, String subFlow, String approvedTime,
			String endTime, String cmd) {
		this.infoId = infoId;
		this.deptName = deptName;
		this.deptId = deptId;
		this.creator = creator;
		this.creatorId = creatorId;
		this.createTime = createTime;
		this.isAvailable = isAvailable;
		this.status = status;
		this.investmentType = investmentType;
		this.examineType = examineType;
		this.subFlow = subFlow;
		this.approvedTime = approvedTime;
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

	@Column(name = "INVESTMENT_TYPE", length = 2)
	public String getInvestmentType() {
		return this.investmentType;
	}

	public void setInvestmentType(String investmentType) {
		this.investmentType = investmentType;
	}

	@Column(name = "EXAMINE_TYPE", length = 2)
	public String getExamineType() {
		return this.examineType;
	}

	public void setExamineType(String examineType) {
		this.examineType = examineType;
	}

	@Column(name = "SUB_FLOW", length = 2)
	public String getSubFlow() {
		return this.subFlow;
	}

	public void setSubFlow(String subFlow) {
		this.subFlow = subFlow;
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
		this.workflowModelId="2";
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
	@Column(name = "CHILD_TYPE", length = 2)
	public String getChildType() {
		return childType;
	}

	public void setChildType(String childType) {
		this.childType = childType;
	}
	@Column(name = "APPROVED_TIME", length = 2)
	public String getApprovedTime() {
		return approvedTime;
	}

	public void setApprovedTime(String approvedTime) {
		this.approvedTime = approvedTime;
	}

}