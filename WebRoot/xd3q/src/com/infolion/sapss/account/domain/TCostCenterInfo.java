package com.infolion.sapss.account.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.infolion.platform.core.annotation.ValidateRule;
import com.infolion.platform.core.domain.ProcessObject;
import com.infolion.platform.core.validation.DataType;

@Entity
@Table(name = "T_COST_CENTER_INFO")
public class TCostCenterInfo extends ProcessObject {
	private static final long serialVersionUID = -3580905086675022752L;
	private String costID;// 主键
	private String orgID;// 申请单位ID
	private String orgName;// 申请单位
	private String creatorID; // 创建人ID
	private String creatorName; // 创建人
	private String createTime; // 创建时间
	@ValidateRule(dataType = DataType.STRING, label = "电话", maxLength = 20, required = true)
	private String tel; // 电话
	@ValidateRule(dataType = DataType.STRING, label = "邮箱地址", maxLength = 50, required = true)
	private String email; // 邮件地址
	@ValidateRule(dataType = DataType.STRING, label = "公司代码", maxLength = 4, required = true)
	private String companyCode; // 公司代码
	private String isAvailable;// 0无效,1有效
	private String approveState;// 审批状态
	private String applyTime;// 申报时间
	private String approvedTime;// 审批通过时间
	@ValidateRule(dataType = DataType.STRING, label = "是否改变标准层次结构", maxLength = 1, required = true)
	private String isChangeStandard;// 是否改变标准层次结构
	@ValidateRule(dataType = DataType.STRING, label = "负责人", maxLength = 10, required = true)
	private String personInCharge;// 负责人
	private String description;// 描述
	private String effectiveBeginDate;// 生效起始日
	private String effectiveEndDate;// 生效截止日
	@ValidateRule(dataType = DataType.STRING, label = "利润中心", maxLength = 50, required = true)
	private String profitCenter;// 利润中心
	@ValidateRule(dataType = DataType.STRING, label = "功能范围", maxLength = 4, required = true)
	private String functionRange;// 功能范围
	@ValidateRule(dataType = DataType.STRING, label = "业务范围", maxLength = 10, required = true)
	private String operationRange;// 业务范围
	@ValidateRule(dataType = DataType.STRING, label = "成本中心编号", maxLength = 10, required = true)
	private String costCenterNO;// 成本中心编号
	@ValidateRule(dataType = DataType.STRING, label = "成本中心名称", maxLength = 50, required = true)
	private String costCenterName;// 成本中心名称
	@ValidateRule(dataType = DataType.STRING, label = "成本中心类型", maxLength = 50, required = true)
	private String costCenterType;// 成本中心类型
	@ValidateRule(dataType = DataType.STRING, label = "上层成本中心组", maxLength = 50, required = true)
	private String upCostGroup;// 上层成本中心组
	private String costGroupNO_1;// 成本中心组编号(一级)
	private String costGroupName_1;// 成本中心组名称(一级)
	private String costGroupNO_2;// 成本中心组编号(二级)
	private String costGroupName_2;// 成本中心组名称(二级)

	@Id
	@Column(name = "COST_ID", unique = true, nullable = false, length = 36)
	public String getCostID() {
		return costID;
	}

	public void setCostID(String costID) {
		this.costID = costID;
	}

	@Column(name = "CREATE_TIME", length = 20)
	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	@Column(name = "TEL", length = 20)
	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	@Column(name = "EMAIL", length = 50)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "COMPANY_CODE", length = 4)
	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	@Column(name = "IS_AVAILABLE", length = 1)
	public String getIsAvailable() {
		return isAvailable;
	}

	public void setIsAvailable(String isAvailable) {
		this.isAvailable = isAvailable;
	}

	@Column(name = "APPROVE_STATE", length = 10)
	public String getApproveState() {
		return approveState;
	}

	public void setApproveState(String approveState) {
		this.approveState = approveState;
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

	@Column(name = "ORG_NAME", length = 200)
	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	@Column(name = "ORG_ID", length = 36)
	public String getOrgID() {
		return orgID;
	}

	public void setOrgID(String orgID) {
		this.orgID = orgID;
	}

	@Column(name = "CREATOR_ID", length = 36)
	public String getCreatorID() {
		return creatorID;
	}

	public void setCreatorID(String creatorID) {
		this.creatorID = creatorID;
	}

	@Column(name = "CREATOR_NAME", length = 20)
	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	@Column(name = "IS_CHANGE_STANDARD", length = 1)
	public String getIsChangeStandard() {
		return isChangeStandard;
	}

	public void setIsChangeStandard(String isChangeStandard) {
		this.isChangeStandard = isChangeStandard;
	}

	@Column(name = "PERSON_IN_CHARGE", length = 10)
	public String getPersonInCharge() {
		return personInCharge;
	}

	public void setPersonInCharge(String personInCharge) {
		this.personInCharge = personInCharge;
	}

	@Column(name = "DESCRIPTION", length = 100)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "EFFECTIVE_BEGIN_DATE", length = 10)
	public String getEffectiveBeginDate() {
		return effectiveBeginDate;
	}

	public void setEffectiveBeginDate(String effectiveBeginDate) {
		this.effectiveBeginDate = effectiveBeginDate;
	}

	@Column(name = "EFFECTIVE_END_DATE", length = 10)
	public String getEffectiveEndDate() {
		return effectiveEndDate;
	}

	public void setEffectiveEndDate(String effectiveEndDate) {
		this.effectiveEndDate = effectiveEndDate;
	}

	@Column(name = "PROFIT_CENTER", length = 10)
	public String getProfitCenter() {
		return profitCenter;
	}

	public void setProfitCenter(String profitCenter) {
		this.profitCenter = profitCenter;
	}

	@Column(name = "FUNCTION_RANGE", length = 4)
	public String getFunctionRange() {
		return functionRange;
	}

	public void setFunctionRange(String functionRange) {
		this.functionRange = functionRange;
	}

	@Column(name = "OPERATION_RANGE", length = 20)
	public String getOperationRange() {
		return operationRange;
	}

	public void setOperationRange(String operationRange) {
		this.operationRange = operationRange;
	}

	@Column(name = "COST_CENTER_NO", length = 10)
	public String getCostCenterNO() {
		return costCenterNO;
	}

	public void setCostCenterNO(String costCenterNO) {
		this.costCenterNO = costCenterNO;
	}

	@Column(name = "COST_CENTER_NAME", length = 50)
	public String getCostCenterName() {
		return costCenterName;
	}

	public void setCostCenterName(String costCenterName) {
		this.costCenterName = costCenterName;
	}

	@Column(name = "COST_CENTER_TYPE", length = 50)
	public String getCostCenterType() {
		return costCenterType;
	}

	public void setCostCenterType(String costCenterType) {
		this.costCenterType = costCenterType;
	}

	@Column(name = "UP_COST_GROUP", length = 50)
	public String getUpCostGroup() {
		return upCostGroup;
	}

	public void setUpCostGroup(String upCostGroup) {
		this.upCostGroup = upCostGroup;
	}

	@Column(name = "COST_GROUP_NO_1", length = 10)
	public String getCostGroupNO_1() {
		return costGroupNO_1;
	}

	public void setCostGroupNO_1(String costGroupNO_1) {
		this.costGroupNO_1 = costGroupNO_1;
	}

	@Column(name = "COST_GROUP_NAME_1", length = 50)
	public String getCostGroupName_1() {
		return costGroupName_1;
	}

	public void setCostGroupName_1(String costGroupName_1) {
		this.costGroupName_1 = costGroupName_1;
	}

	@Column(name = "COST_GROUP_NO_2", length = 10)
	public String getCostGroupNO_2() {
		return costGroupNO_2;
	}

	public void setCostGroupNO_2(String costGroupNO_2) {
		this.costGroupNO_2 = costGroupNO_2;
	}

	@Column(name = "COST_GROUP_NAME_2", length = 50)
	public String getCostGroupName_2() {
		return costGroupName_2;
	}

	public void setCostGroupName_2(String costGroupName_2) {
		this.costGroupName_2 = costGroupName_2;
	}

	@Override
	public void setWorkflowProcessUrl() {
		this.workflowProcessUrl = "costCenterController.spr?action=costExamine";
	}

	@Override
	public void setWorkflowModelId() {
		this.workflowModelId = "1034";
	}

	@Override
	public void setWorkflowModelName() {
		this.workflowModelName = "成本中心申请";
	}

	@Override
	public void setWorkflowProcessName() {
		this.workflowProcessName = "cost_center_approve_v1";
	}
}
