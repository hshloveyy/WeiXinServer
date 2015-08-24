package com.infolion.sapss.capitalRequirement.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.infolion.platform.core.domain.BaseObject;

@Entity
@Table(name = "T_ZJXQ_LOAN_FOREIGN_REPAY")
public class TZJXQLoanForeignRepayInfo extends BaseObject {

	private static final long serialVersionUID = 3901962333554424859L;
	private String repayID;
	private String loanID;
	private String amountForeign;// 提前还款外币金额
	private String amountCNY;// 提前还款人民币金额
	private String repayDate;// 提前还款日期
	private String exRate;// 还款日汇率
	private String remark;// 备注
	private String creatorID; // 创建人ID
	private String creatorName; // 创建人
	private String createTime; // 创建时间
	private String isAvailable;// 0无效,1有效

	@Id
	@Column(name = "REPAY_ID", unique = true, nullable = false, length = 36)
	public String getRepayID() {
		return repayID;
	}

	public void setRepayID(String repayID) {
		this.repayID = repayID;
	}

	@Column(name = "REPAY_DATE", length = 10)
	public String getRepayDate() {
		return repayDate;
	}

	public void setRepayDate(String repayDate) {
		this.repayDate = repayDate;
	}

	@Column(name = "REMARK", length = 200)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	@Column(name = "LOAN_ID", nullable = false, length = 36)
	public String getLoanID() {
		return loanID;
	}

	public void setLoanID(String loanID) {
		this.loanID = loanID;
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
}
