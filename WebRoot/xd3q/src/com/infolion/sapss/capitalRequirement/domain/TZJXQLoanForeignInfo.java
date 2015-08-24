package com.infolion.sapss.capitalRequirement.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.infolion.platform.core.annotation.ValidateRule;
import com.infolion.platform.core.domain.BaseObject;
import com.infolion.platform.core.validation.DataType;

@Entity
@Table(name = "T_ZJXQ_LOAN_FOREIGN")
public class TZJXQLoanForeignInfo extends BaseObject {

	private static final long serialVersionUID = 3082279410946816961L;
	private String loanID;
	private String orgID; // 申请单位ID
	private String orgName; // 申请单位
	private String creatorID; // 创建人ID
	private String creatorName; // 创建人
	private String createTime; // 创建时间
	private String isAvailable;// 0无效,1有效
	@ValidateRule(dataType = DataType.STRING, label = "公司代码", maxLength = 4, required = true)
	private String companyCode; // 公司代码
	private String beginDate; // 贷款日
	private String endDate; // 到期日
	private String amountForeign; // 金额
	private String amountCNY; // 金额
	private String otherFee; // 承诺等其他费用
	private String isPay; // 0未还款,1已还款
	private String rate; // 利率
	private String exRate; // 贷款日汇率
	private String currency; // 币别
	private String institution; // 金融机构
	private String balanceForeign; // 外币余额
	private String balanceCNY; // 人民币余额

	@Id
	@Column(name = "LOAN_ID", unique = true, nullable = false, length = 36)
	public String getLoanID() {
		return loanID;
	}

	public void setLoanID(String loanID) {
		this.loanID = loanID;
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

	@Column(name = "CREATE_TIME", length = 20)
	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	@Column(name = "IS_AVAILABLE", length = 1)
	public String getIsAvailable() {
		return isAvailable;
	}

	public void setIsAvailable(String isAvailable) {
		this.isAvailable = isAvailable;
	}

	@Column(name = "COMPANY_CODE", length = 4)
	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	@Column(name = "BEGIN_DATE", length = 10)
	public String getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	@Column(name = "END_DATE", length = 10)
	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	@Column(name = "OTHER_FEE", precision = 9, scale = 3)
	public String getOtherFee() {
		return otherFee;
	}

	public void setOtherFee(String otherFee) {
		this.otherFee = otherFee;
	}

	@Column(name = "IS_PAY", length = 1)
	public String getIsPay() {
		return isPay;
	}

	public void setIsPay(String isPay) {
		this.isPay = isPay;
	}

	@Column(name = "RATE", length=10)
	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	@Column(name = "INSTITUTION", length = 50)
	public String getInstitution() {
		return institution;
	}

	public void setInstitution(String institution) {
		this.institution = institution;
	}

	@Column(name = "AMOUNT_FOREIGN", precision = 9, scale = 3)
	public String getAmountForeign() {
		return amountForeign;
	}

	public void setAmountForeign(String amountForeign) {
		this.amountForeign = amountForeign;
	}

	@Column(name = "AMOUNT_CNY", precision = 9, scale = 3)
	public String getAmountCNY() {
		return amountCNY;
	}

	public void setAmountCNY(String amountCNY) {
		this.amountCNY = amountCNY;
	}

	@Column(name = "EX_RATE", precision = 9, scale = 3)
	public String getExRate() {
		return exRate;
	}

	public void setExRate(String exRate) {
		this.exRate = exRate;
	}

	@Column(name = "CURRENCY", length = 3)
	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	@Column(name = "BALANCE_FOREIGN", precision = 9, scale = 3)
	public String getBalanceForeign() {
		return balanceForeign;
	}

	public void setBalanceForeign(String balanceForeign) {
		this.balanceForeign = balanceForeign;
	}

	@Column(name = "BALANCE_CNY", precision = 9, scale = 3)
	public String getBalanceCNY() {
		return balanceCNY;
	}

	public void setBalanceCNY(String balanceCNY) {
		this.balanceCNY = balanceCNY;
	}
}
