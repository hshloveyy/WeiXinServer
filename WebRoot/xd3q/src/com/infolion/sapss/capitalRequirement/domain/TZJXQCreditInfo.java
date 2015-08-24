package com.infolion.sapss.capitalRequirement.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.infolion.platform.core.annotation.ValidateRule;
import com.infolion.platform.core.domain.BaseObject;
import com.infolion.platform.core.validation.DataType;

@Entity
@Table(name = "T_ZJXQ_CREDIT")
public class TZJXQCreditInfo extends BaseObject {

	private static final long serialVersionUID = -8592408111432953656L;
	private String creditID;
	private String orgID; // 申请单位ID
	private String orgName; // 申请单位
	private String creatorID; // 创建人ID
	private String creatorName; // 创建人
	private String createTime; // 创建时间
	private String isAvailable;// 0无效,1有效
	@ValidateRule(dataType = DataType.STRING, label = "公司代码", maxLength = 4, required = true)
	private String companyCode; // 公司代码
	private String type; // 1银行承兑汇票,2国内信用证
	private String bank; // 出票行/开证行
	private String beginDate; // 出票日/开证日
	private String endDate; // 到期日
	private String amount; // 金额
	private String receiver; // 收款人名称/收益人名称
	private String marginsRate; // 保证金比例
	private String marginsAmount; // 保证金金额
	private String handlingCharge; // 手续费
	private String otherFee; // 承诺等其他费用
	@ValidateRule(dataType = DataType.STRING, label = "是否付款", maxLength = 1, required = true)
	private String isPay; // 0未还款,1已还款

	@Id
	@Column(name = "CREDIT_ID", unique = true, nullable = false, length = 36)
	public String getCreditID() {
		return creditID;
	}

	public void setCreditID(String creditID) {
		this.creditID = creditID;
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

	@Column(name = "TYPE", length = 1)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(name = "BANK", length = 50)
	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
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

	@Column(name = "AMOUNT", length = 30)
	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	@Column(name = "RECEIVER", length = 50)
	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	@Column(name = "MARGINS_RATE", length = 10)
	public String getMarginsRate() {
		return marginsRate;
	}

	public void setMarginsRate(String marginsRate) {
		this.marginsRate = marginsRate;
	}

	@Column(name = "MARGINS_AMOUNT", length = 30)
	public String getMarginsAmount() {
		return marginsAmount;
	}

	public void setMarginsAmount(String marginsAmount) {
		this.marginsAmount = marginsAmount;
	}

	@Column(name = "HANDLING_CHARGE", length = 30)
	public String getHandlingCharge() {
		return handlingCharge;
	}

	public void setHandlingCharge(String handlingCharge) {
		this.handlingCharge = handlingCharge;
	}

	@Column(name = "OTHER_FEE", length = 30)
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

}
