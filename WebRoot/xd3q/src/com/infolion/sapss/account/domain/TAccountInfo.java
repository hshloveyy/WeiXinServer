package com.infolion.sapss.account.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.infolion.platform.core.annotation.ValidateRule;
import com.infolion.platform.core.domain.ProcessObject;
import com.infolion.platform.core.validation.DataType;

@Entity
@Table(name = "T_ACCOUNT_INFO")
public class TAccountInfo extends ProcessObject {
	private static final long serialVersionUID = -6857697401763630876L;
	private String accountID;// 主键
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
	@ValidateRule(dataType = DataType.STRING, label = "银行名称", maxLength = 25, required = true)
	private String headOfficeName;// 银行名称
	@ValidateRule(dataType = DataType.STRING, label = "国家", maxLength = 20, required = true)
	private String country;// 国家
	@ValidateRule(dataType = DataType.STRING, label = "地区/省份", maxLength = 20, required = true)
	private String province;// 地区/省份
	@ValidateRule(dataType = DataType.STRING, label = "城市", maxLength = 10, required = true)
	private String city;// 城市
	private String street;// 街道
	@ValidateRule(dataType = DataType.STRING, label = "开户行名称", maxLength = 15, required = true)
	private String bankName;// 开户行名称
	@ValidateRule(dataType = DataType.STRING, label = "帐号", maxLength = 25, required = true)
	private String accountCode;// 帐号
	@ValidateRule(dataType = DataType.STRING, label = "币别", maxLength = 3, required = true)
	private String currency;// 币别
	@ValidateRule(dataType = DataType.STRING, label = "是否待核查帐户", maxLength = 1, required = true)
	private String isPVA;// 是否待核查帐户
	@ValidateRule(dataType = DataType.STRING, label = "期初会计余额", maxLength = 30, required = true)
	private String balance;// 期初会计余额
	private String purpose;// 用途
	private String headOfficeNO;// 银行编号
	private String headOfficeCode;// 银行代码
	private String bankCode;// 开户行代码
	private String accountFlag;// 帐户标识
	private String subjectNO;// 总帐科目代码
	private String shortDescription;// 短文本
	private String longDescription;// 长文本
	private String accountTitle;//会计科目
	private String balanceMaintain;//银行科目期初余额维护
	private String cashierAccount;//出纳日记账
	private String memo;//备注

	@Id
	@Column(name = "ACCOUNT_ID", unique = true, nullable = false, length = 36)
	public String getAccountID() {
		return accountID;
	}

	public void setAccountID(String accountID) {
		this.accountID = accountID;
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

	@Column(name = "HEADOFFICE_NAME", length = 50)
	public String getHeadOfficeName() {
		return headOfficeName;
	}

	public void setHeadOfficeName(String headOfficeName) {
		this.headOfficeName = headOfficeName;
	}

	@Column(name = "COUNTRY", length = 20)
	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	@Column(name = "PROVINCE", length = 20)
	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	@Column(name = "CITY", length = 20)
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Column(name = "STREET", length = 20)
	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	@Column(name = "BANK_NAME", length = 30)
	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	@Column(name = "ACCOUNT_CODE", length = 25)
	public String getAccountCode() {
		return accountCode;
	}

	public void setAccountCode(String accountCode) {
		this.accountCode = accountCode;
	}

	@Column(name = "CURRENCY", length = 3)
	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	@Column(name = "IS_PVA", length = 1)
	public String getIsPVA() {
		return isPVA;
	}

	public void setIsPVA(String isPVA) {
		this.isPVA = isPVA;
	}

	@Column(name = "BALANCE", length = 30)
	public String getBalance() {
		return balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}

	@Column(name = "PURPOSE", length = 50)
	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	@Column(name = "HEADOFFICE_NO", length = 2)
	public String getHeadOfficeNO() {
		return headOfficeNO;
	}

	public void setHeadOfficeNO(String headOfficeNO) {
		this.headOfficeNO = headOfficeNO;
	}

	@Column(name = "HEADOFFICE_CODE", length = 8)
	public String getHeadOfficeCode() {
		return headOfficeCode;
	}

	public void setHeadOfficeCode(String headOfficeCode) {
		this.headOfficeCode = headOfficeCode;
	}

	@Column(name = "BANK_CODE", length = 5)
	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	@Column(name = "ACCOUNT_FLAG", length = 5)
	public String getAccountFlag() {
		return accountFlag;
	}

	public void setAccountFlag(String accountFlag) {
		this.accountFlag = accountFlag;
	}

	@Column(name = "SUBJECT_NO", length = 10)
	public String getSubjectNO() {
		return subjectNO;
	}

	public void setSubjectNO(String subjectNO) {
		this.subjectNO = subjectNO;
	}

	@Column(name = "SHORT_DESCRIPTION", length = 40)
	public String getShortDescription() {
		return shortDescription;
	}

	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}
	@Column(name="ACCOUNT_TITLE",length = 2)
	public String getAccountTitle() {
		return accountTitle;
	}

	public void setAccountTitle(String accountTitle) {
		this.accountTitle = accountTitle;
	}
	@Column(name="BALANCE_MAINTAIN",length = 2)
	public String getBalanceMaintain() {
		return balanceMaintain;
	}

	public void setBalanceMaintain(String balanceMaintain) {
		this.balanceMaintain = balanceMaintain;
	}
	@Column(name="CASHIER_ACCOUNT",length = 2)
	public String getCashierAccount() {
		return cashierAccount;
	}

	public void setCashierAccount(String cashierAccount) {
		this.cashierAccount = cashierAccount;
	}
	@Column(name = "LONG_DESCRIPTION", length = 80)
	public String getLongDescription() {
		return longDescription;
	}

	public void setLongDescription(String longDescription) {
		this.longDescription = longDescription;
	}
	@Column(name = "MEMO", length = 50)
	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}
	@Override
	public void setWorkflowProcessUrl() {
		this.workflowProcessUrl = "accountController.spr?action=accountExamine";
	}

	@Override
	public void setWorkflowModelId() {
		this.workflowModelId = "1032";
	}

	@Override
	public void setWorkflowModelName() {
		this.workflowModelName = "银行主数据申请";
	}

	@Override
	public void setWorkflowProcessName() {
		this.workflowProcessName = "account_approve_v1";
	}
}
