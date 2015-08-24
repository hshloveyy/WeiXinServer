package com.infolion.sapss.account.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.infolion.platform.core.annotation.ValidateRule;
import com.infolion.platform.core.domain.ProcessObject;
import com.infolion.platform.core.validation.DataType;

/**
 * TSubjectInfo entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_SUBJECT_INFO")
public class TSubjectInfo extends ProcessObject {

	private String subjectID; // 科目ID
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
	@ValidateRule(dataType = DataType.STRING, label = "SAP科目代码", maxLength = 10, required = true)
	private String subjectNO_SAP; // SAP科目代码
	@ValidateRule(dataType = DataType.STRING, label = "帐户组", required = true)
	private String accountGroup; // 帐户组
	@ValidateRule(dataType = DataType.STRING, label = "科目短文本", maxLength = 40, required = true)
	private String shortDescription;// 科目短文本
	private String longDescription;// 科目长文本
	private String remark; // 备注
	private String currency; // 科目货币
	private String isBS; // BS,资产负债表科目
	@ValidateRule(dataType = DataType.STRING, label = "IS")
	private String isIS; // IS，损益表科目
	private String isOnlyCNY;// 是否仅以本位币显示余额
	private String taxType;// 税类别
	private String isAllowNoTax;// 允许无税过帐
	private String controlType;// 统驭科目类型
	private String isNotClear;// 未清项管理
	private String isShowRows;// 行项目显示
	private String orderNO;// 排序码
	private String colStateGroup;// 字段状态群组
	private String isOnlyAuto;// 只允许自动过帐
	private String planLayer;// 计划层次
	private String isCashRelated;// 与现金流动有关
	private String isNew;// 是否新增
	private String isAvailable;// 0无效,1有效
	private String approveState;// 审批状态
	private String applyTime;// 申报时间
	private String approvedTime;// 审批通过时间

	@Id
	@Column(name = "SUBJECT_ID", unique = true, nullable = false, length = 36)
	public String getSubjectID() {
		return subjectID;
	}

	public void setSubjectID(String subjectID) {
		this.subjectID = subjectID;
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

	@Column(name = "SUBJECT_NO_SAP", length = 10)
	public String getSubjectNO_SAP() {
		return subjectNO_SAP;
	}

	public void setSubjectNO_SAP(String subjectNO_SAP) {
		this.subjectNO_SAP = subjectNO_SAP;
	}

	@Column(name = "ACCOUNT_GROUP", length = 4)
	public String getAccountGroup() {
		return accountGroup;
	}

	public void setAccountGroup(String accountGroup) {
		this.accountGroup = accountGroup;
	}

	@Column(name = "SHORT_DESCRIPTION", length = 40)
	public String getShortDescription() {
		return shortDescription;
	}

	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}

	@Column(name = "LONG_DESCRIPTION", length = 80)
	public String getLongDescription() {
		return longDescription;
	}

	public void setLongDescription(String longDescription) {
		this.longDescription = longDescription;
	}

	@Column(name = "REMARK", length = 100)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "CURRENCY", length = 3)
	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	@Column(name = "IS_BS", length = 1)
	public String getIsBS() {
		return isBS;
	}

	public void setIsBS(String isBS) {
		this.isBS = isBS;
	}

	@Column(name = "IS_IS", length = 1)
	public String getIsIS() {
		return isIS;
	}

	public void setIsIS(String isIS) {
		this.isIS = isIS;
	}

	@Column(name = "IS_ONLY_CNY", length = 1)
	public String getIsOnlyCNY() {
		return isOnlyCNY;
	}

	public void setIsOnlyCNY(String isOnlyCNY) {
		this.isOnlyCNY = isOnlyCNY;
	}

	@Column(name = "TAX_TYPE", length = 1)
	public String getTaxType() {
		return taxType;
	}

	public void setTaxType(String taxType) {
		this.taxType = taxType;
	}

	@Column(name = "IS_ALLOW_NOTAX", length = 1)
	public String getIsAllowNoTax() {
		return isAllowNoTax;
	}

	public void setIsAllowNoTax(String isAllowNoTax) {
		this.isAllowNoTax = isAllowNoTax;
	}

	@Column(name = "CONTROL_TYPE", length = 1)
	public String getControlType() {
		return controlType;
	}

	public void setControlType(String controlType) {
		this.controlType = controlType;
	}

	@Column(name = "IS_NOT_CLEAR", length = 1)
	public String getIsNotClear() {
		return isNotClear;
	}

	public void setIsNotClear(String isNotClear) {
		this.isNotClear = isNotClear;
	}

	@Column(name = "IS_SHOW_ROWS", length = 1)
	public String getIsShowRows() {
		return isShowRows;
	}

	public void setIsShowRows(String isShowRows) {
		this.isShowRows = isShowRows;
	}

	@Column(name = "ORDER_NO", length = 3)
	public String getOrderNO() {
		return orderNO;
	}

	public void setOrderNO(String orderNO) {
		this.orderNO = orderNO;
	}

	@Column(name = "COL_STATE_GROUP", length = 4)
	public String getColStateGroup() {
		return colStateGroup;
	}

	public void setColStateGroup(String colStateGroup) {
		this.colStateGroup = colStateGroup;
	}

	@Column(name = "IS_ONLY_AUTO", length = 1)
	public String getIsOnlyAuto() {
		return isOnlyAuto;
	}

	public void setIsOnlyAuto(String isOnlyAuto) {
		this.isOnlyAuto = isOnlyAuto;
	}

	@Column(name = "PLAN_LAYER", length = 2)
	public String getPlanLayer() {
		return planLayer;
	}

	public void setPlanLayer(String planLayer) {
		this.planLayer = planLayer;
	}

	@Column(name = "IS_CASH_RELATED", length = 1)
	public String getIsCashRelated() {
		return isCashRelated;
	}

	public void setIsCashRelated(String isCashRelated) {
		this.isCashRelated = isCashRelated;
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

	@Override
	public void setWorkflowProcessUrl() {
		this.workflowProcessUrl = "subjectController.spr?action=subjectExamine";
	}

	@Override
	public void setWorkflowModelId() {
		this.workflowModelId = "1031";
	}

	@Override
	public void setWorkflowModelName() {
		this.workflowModelName = "会计科目申请";
	}

	@Override
	public void setWorkflowProcessName() {
		this.workflowProcessName = "subject_approve_v1";
	}

	@Column(name = "IS_NEW", length = 1)
	public String getIsNew() {
		return isNew;
	}

	public void setIsNew(String isNew) {
		this.isNew = isNew;
	}
}
