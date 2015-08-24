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
@Table(name = "T_PLEDGERECEIPTS_INFO")
public class PledgeReceiptsInfo extends ProcessObject {

	// Fields

	private String pledgeReceiptsInfoId; // 进仓ID	
	private String projectId; // 立项ID
	@ValidateRule(dataType = DataType.STRING, label = "立项号",required = true)
	private String projectNo; // 立项号
	private String projectName; // 立项名称
	private String pledgeReceiptsInfoNo; // 进仓单号
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
	@ValidateRule(dataType = DataType.STRING, label = "进仓时间",required = true)
	private String receiptTime;//进仓时间
	private String memo;//备注
	
	@Id
	@Column(name = "PLEDGERECEIPTS_INFO_ID", unique = true, nullable = false, length = 36)
	public String getPledgeReceiptsInfoId() {
		return pledgeReceiptsInfoId;
	}
	@Column(name = "APPLY_TIME", length = 20)
	public String getApplyTime() {
		return applyTime;
	}
	@Column(name = "APPROVED_TIME", length = 20)
	public String getApprovedTime() {
		return approvedTime;
	}
	@Column(name = "CREATOR", length = 36)
	public String getCreator() {
		return creator;
	}
	@Column(name = "CREATOR_TIME", length = 20)
	public String getCreatorTime() {
		return creatorTime;
	}
	@Column(name = "DEPT_ID", length = 36)
	public String getDeptId() {
		return deptId;
	}
	@Column(name = "EXAMINE_STATE", length = 2)
	public String getExamineState() {
		return examineState;
	}
	@Column(name = "IS_AVAILABLE", length = 1)
	public String getIsAvailable() {
		return isAvailable;
	}
	@Column(name = "MEMO", length = 2000)
	public String getMemo() {
		return memo;
	}
	@Column(name = "PLEDGERECEIPTS_INFO_NO", length = 50)
	public String getPledgeReceiptsInfoNo() {
		return pledgeReceiptsInfoNo;
	}
	@Column(name = "PROJECT_ID", length = 36)
	public String getProjectId() {
		return projectId;
	}
	@Column(name = "PROJECT_NAME", length = 200)
	public String getProjectName() {
		return projectName;
	}
	@Column(name = "PROJECT_NO", length = 50)
	public String getProjectNo() {
		return projectNo;
	}
	@Column(name = "RECEIPT_TIME", length = 20)
	public String getReceiptTime() {
		return receiptTime;
	}
	@Column(name = "UNIT_NAME", length = 50)
	public String getUnitName() {
		return unitName;
	}
	@Column(name = "WAREHOUSE", length = 50)
	public String getWarehouse() {
		return warehouse;
	}
	@Column(name = "WAREHOUSE_ADD", length = 100)
	public String getWarehouseAdd() {
		return warehouseAdd;
	}

	public void setApplyTime(String applyTime) {
		this.applyTime = applyTime;
	}
	public void setApprovedTime(String approvedTime) {
		this.approvedTime = approvedTime;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public void setCreatorTime(String creatorTime) {
		this.creatorTime = creatorTime;
	}
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	public void setExamineState(String examineState) {
		this.examineState = examineState;
	}
	public void setIsAvailable(String isAvailable) {
		this.isAvailable = isAvailable;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public void setPledgeReceiptsInfoId(String pledgeReceiptsInfoId) {
		this.pledgeReceiptsInfoId = pledgeReceiptsInfoId;
	}
	public void setPledgeReceiptsInfoNo(String pledgeReceiptsInfoNo) {
		this.pledgeReceiptsInfoNo = pledgeReceiptsInfoNo;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public void setProjectNo(String projectNo) {
		this.projectNo = projectNo;
	}
	public void setReceiptTime(String receiptTime) {
		this.receiptTime = receiptTime;
	}
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	public void setWarehouse(String warehouse) {
		this.warehouse = warehouse;
	}
	public void setWarehouseAdd(String warehouseAdd) {
		this.warehouseAdd = warehouseAdd;
	}
	
	
	
	@Override
	public void setWorkflowModelId() {
		// TODO Auto-generated method stub
		this.workflowModelId = "1007";
	}

	@Override
	public void setWorkflowModelName() {
		this.workflowModelName = "质押物入仓申请";

	}

	@Override
	public void setWorkflowProcessName() {
		// this.workflowProcessName = processName;
		// this.workflowProcessName = WorkflowUtils.chooseWorkflowName("pledge_receipt");
	}

	@Override
	public void setWorkflowProcessUrl() {
		this.workflowProcessUrl = "pledgeReceiptsController.spr?action=receiptsExamine";

	}

}
