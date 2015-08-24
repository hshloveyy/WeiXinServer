package com.infolion.sapss.scrapped.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.infolion.platform.core.domain.ProcessObject;

/**
 * TScrapped entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_SCRAPPED")
public class TScrapped extends ProcessObject implements java.io.Serializable {

	// Fields

	private String scrappedId;
	private String scrappedNo;
	private String cmd;
	private String deptId;
	private String examineState;
	private String applyTime;
	private String approvedTime;
	private String isAvailable;
	private String creatorTime;
	private String creator;

	// Constructors

	/** default constructor */
	public TScrapped() {
	}

	/** minimal constructor */
	public TScrapped(String scrappedId) {
		this.scrappedId = scrappedId;
	}

	/** full constructor */
	public TScrapped(String scrappedId, String scrappedNo, String cmd,
			String deptId, String examineState, String applyTime,
			String approvedTime, String isAvailable, String creatorTime,
			String creator) {
		this.scrappedId = scrappedId;
		this.scrappedNo = scrappedNo;
		this.cmd = cmd;
		this.deptId = deptId;
		this.examineState = examineState;
		this.applyTime = applyTime;
		this.approvedTime = approvedTime;
		this.isAvailable = isAvailable;
		this.creatorTime = creatorTime;
		this.creator = creator;
	}

	// Property accessors
	@Id
	@Column(name = "SCRAPPED_ID", unique = true, nullable = false, length = 36)
	public String getScrappedId() {
		return this.scrappedId;
	}

	public void setScrappedId(String scrappedId) {
		this.scrappedId = scrappedId;
	}

	@Column(name = "SCRAPPED_NO", length = 50)
	public String getScrappedNo() {
		return this.scrappedNo;
	}

	public void setScrappedNo(String scrappedNo) {
		this.scrappedNo = scrappedNo;
	}

	@Column(name = "CMD", length = 2000)
	public String getCmd() {
		return this.cmd;
	}

	public void setCmd(String cmd) {
		this.cmd = cmd;
	}

	@Column(name = "DEPT_ID", length = 36)
	public String getDeptId() {
		return this.deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	@Column(name = "EXAMINE_STATE", length = 2)
	public String getExamineState() {
		return this.examineState;
	}

	public void setExamineState(String examineState) {
		this.examineState = examineState;
	}

	@Column(name = "APPLY_TIME", length = 20)
	public String getApplyTime() {
		return this.applyTime;
	}

	public void setApplyTime(String applyTime) {
		this.applyTime = applyTime;
	}

	@Column(name = "APPROVED_TIME", length = 20)
	public String getApprovedTime() {
		return this.approvedTime;
	}

	public void setApprovedTime(String approvedTime) {
		this.approvedTime = approvedTime;
	}

	@Column(name = "IS_AVAILABLE", length = 1)
	public String getIsAvailable() {
		return this.isAvailable;
	}

	public void setIsAvailable(String isAvailable) {
		this.isAvailable = isAvailable;
	}

	@Column(name = "CREATOR_TIME", length = 20)
	public String getCreatorTime() {
		return this.creatorTime;
	}

	public void setCreatorTime(String creatorTime) {
		this.creatorTime = creatorTime;
	}

	@Column(name = "CREATOR", length = 36)
	public String getCreator() {
		return this.creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	@Override
	public void setWorkflowModelId() {
		this.workflowModelId="00003";
	}

	@Override
	public void setWorkflowModelName() {
		this.workflowModelName="报废申请";
	}

	@Override
	public void setWorkflowProcessName() {
		//this.workflowProcessName="storage_scrap_v1";
	}

	@Override
	public void setWorkflowProcessUrl() {
		this.workflowProcessUrl="scrappedController.spr?action=link";
	}

}