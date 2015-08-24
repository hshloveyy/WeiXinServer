package com.infolion.sapss.account.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.infolion.platform.core.annotation.ValidateRule;
import com.infolion.platform.core.domain.ProcessObject;
import com.infolion.platform.core.validation.DataType;

@Entity
@Table(name = "T_PROFIT_CENTER_INFO")
public class TProfitCenterInfo extends ProcessObject {
	//private static final long serialVersionUID = -1115886251371927723L;
	private String profitID;// 主键
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
	@ValidateRule(dataType = DataType.STRING, label = "利润中心组编号", maxLength = 10, required = true)
	private String profitGroupNO;// 利润中心组编号
	@ValidateRule(dataType = DataType.STRING, label = "利润中心组名称", maxLength = 50, required = true)
	private String profitGroupName;// 利润中心组名称
	@ValidateRule(dataType = DataType.STRING, label = "利润中心编号", maxLength = 20, required = true)
	private String profitCenterNO;// 利润中心编号
	@ValidateRule(dataType = DataType.STRING, label = "利润中心名称", maxLength = 50, required = true)
	private String profitCenterName;// 利润中心名称
	@ValidateRule(dataType = DataType.STRING, label = "上层利润中心组", maxLength = 50, required = true)
	private String upProfitGroup;// 上层利润中心组
	@ValidateRule(dataType = DataType.STRING, label = "负责人", maxLength = 10, required = true)
	private String personInCharge;// 负责人
	private String description;// 描述
	private String effectiveBeginDate;// 生效起始日
	private String effectiveEndDate;// 生效截止日

	@Id
	@Column(name = "PROFIT_ID", unique = true, nullable = false, length = 36)
	public String getProfitID() {
		return profitID;
	}

	public void setProfitID(String profitID) {
		this.profitID = profitID;
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

	@Column(name = "PROFIT_GROUP_NO", length = 10)
	public String getProfitGroupNO() {
		return profitGroupNO;
	}

	public void setProfitGroupNO(String profitGroupNO) {
		this.profitGroupNO = profitGroupNO;
	}

	@Column(name = "PROFIT_GROUP_NAME", length = 50)
	public String getProfitGroupName() {
		return profitGroupName;
	}

	public void setProfitGroupName(String profitGroupName) {
		this.profitGroupName = profitGroupName;
	}

	@Column(name = "PROFIT_CENTER_NO", length = 20)
	public String getProfitCenterNO() {
		return profitCenterNO;
	}

	public void setProfitCenterNO(String profitCenterNO) {
		this.profitCenterNO = profitCenterNO;
	}

	@Column(name = "PROFIT_CENTER_NAME", length = 50)
	public String getProfitCenterName() {
		return profitCenterName;
	}

	public void setProfitCenterName(String profitCenterName) {
		this.profitCenterName = profitCenterName;
	}

	@Column(name = "UP_PROFIT_GROUP", length = 50)
	public String getUpProfitGroup() {
		return upProfitGroup;
	}

	public void setUpProfitGroup(String upProfitGroup) {
		this.upProfitGroup = upProfitGroup;
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

	@Override
	public void setWorkflowProcessUrl() {
		this.workflowProcessUrl = "profitCenterController.spr?action=profitExamine";
	}

	@Override
	public void setWorkflowModelId() {
		this.workflowModelId = "1033";
	}

	@Override
	public void setWorkflowModelName() {
		this.workflowModelName = "利润中心申请";
	}

	@Override
	public void setWorkflowProcessName() {
		this.workflowProcessName = "profit_center_approve_v1";
	}
}
