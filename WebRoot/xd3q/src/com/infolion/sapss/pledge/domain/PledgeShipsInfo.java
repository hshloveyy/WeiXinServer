package com.infolion.sapss.pledge.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.infolion.platform.core.annotation.ValidateRule;
import com.infolion.platform.core.domain.ProcessObject;
import com.infolion.platform.core.validation.DataType;
import com.infolion.sapss.common.WorkflowUtils;

@Entity
@Table(name = "T_PLEDGESHIPS_INFO")
public class PledgeShipsInfo extends ProcessObject {
	// Fields

	private String pledgeShipsInfoId; // 出仓ID	
	private String projectId; // 立项ID
	@ValidateRule(dataType = DataType.STRING, label = "立项号",required = true)
	private String projectNo; // 立项号
	private String projectName; // 立项名称
	private String pledgeShipsInfoNo; // 出仓单号
	@ValidateRule(dataType = DataType.STRING, label = "仓库",required = true)
	private String warehouse; // 仓库
	private String warehouseAdd; // 仓库地址
	private String deptId; // 部门编号
	private String examineState; // 审批状态
	private String applyTime; // 申报时间
	private String approvedTime; // 审批通过时间
	private String isAvailable; // 是否有效
	private String creatorTime; // 创建时间
	private String creator; // 创建人
	@ValidateRule(dataType = DataType.STRING, label = "销货单位",required = true)
	private String unitName;// 销货单位
	@ValidateRule(dataType = DataType.STRING, label = "出仓时间",required = true)
	private String shipsTime;//出仓时间
	private String memo;//备注
	
	@Id
	@Column(name = "PLEDGESHIPS_INFO_ID", unique = true, length = 36)
	public String getPledgeShipsInfoId() {
		return pledgeShipsInfoId;
	}

	public void setPledgeShipsInfoId(String pledgeShipsInfoId) {
		this.pledgeShipsInfoId = pledgeShipsInfoId;
	}
	@Column(name = "PROJECT_ID", length = 36)
	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	@Column(name = "PROJECT_NO", length = 50)
	public String getProjectNo() {
		return projectNo;
	}

	public void setProjectNo(String projectNo) {
		this.projectNo = projectNo;
	}
	@Column(name = "PROJECT_NAME", length = 200)
	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	@Column(name = "PLEDGESHIPS_INFO_NO", length = 50)
	public String getPledgeShipsInfoNo() {
		return pledgeShipsInfoNo;
	}

	public void setPledgeShipsInfoNo(String pledgeShipsInfoNo) {
		this.pledgeShipsInfoNo = pledgeShipsInfoNo;
	}
	@Column(name = "WAREHOUSE", length = 50)
	public String getWarehouse() {
		return warehouse;
	}

	public void setWarehouse(String warehouse) {
		this.warehouse = warehouse;
	}
	@Column(name = "WAREHOUSE_ADD", length = 100)
	public String getWarehouseAdd() {
		return warehouseAdd;
	}

	public void setWarehouseAdd(String warehouseAdd) {
		this.warehouseAdd = warehouseAdd;
	}
	@Column(name = "DEPT_ID", length = 36)
	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	@Column(name = "EXAMINE_STATE", length = 2)
	public String getExamineState() {
		return examineState;
	}

	public void setExamineState(String examineState) {
		this.examineState = examineState;
	}
	@Column(name = "APPLY_TIME", length = 20)
	public String getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(String applyTime) {
		this.applyTime = applyTime;
	}
	@Column(name = "APPROVED_TIME", length = 20)
	public String getApprovedTime() {
		return approvedTime;
	}

	public void setApprovedTime(String approvedTime) {
		this.approvedTime = approvedTime;
	}
	@Column(name = "IS_AVAILABLE", length = 1)
	public String getIsAvailable() {
		return isAvailable;
	}

	public void setIsAvailable(String isAvailable) {
		this.isAvailable = isAvailable;
	}
	@Column(name = "CREATOR_TIME", length = 20)
	public String getCreatorTime() {
		return creatorTime;
	}

	public void setCreatorTime(String creatorTime) {
		this.creatorTime = creatorTime;
	}
	@Column(name = "CREATOR", length = 36)
	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}
	@Column(name = "UNIT_NAME", length = 50)
	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	@Column(name = "SHIP_TIME", length = 20)
	public String getShipsTime() {
		return shipsTime;
	}

	public void setShipsTime(String shipsTime) {
		this.shipsTime = shipsTime;
	}
	@Column(name = "MEMO", length = 2000)
	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}	
	
	
	@Override
	public void setWorkflowModelId() {
		// TODO Auto-generated method stub
		this.workflowModelId = "1007";
	}

	@Override
	public void setWorkflowModelName() {
		this.workflowModelName = "质押物出仓申请";

	}

	@Override
	public void setWorkflowProcessName() {
		// this.workflowProcessName = processName;
		 this.workflowProcessName = WorkflowUtils.chooseWorkflowName("pledge_export");
	}

	@Override
	public void setWorkflowProcessUrl() {
		this.workflowProcessUrl = "pledgeShipController.spr?action=shipsExamine";

	}

}
