package com.infolion.sapss.account.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.infolion.platform.core.annotation.ValidateRule;
import com.infolion.platform.core.validation.DataType;

@Entity
@Table(name = "T_INNER_TRANSFER_DETAIL")
public class TInnerTransferDetail {
	private String detailID;
	private String transferID;
	private String creatorID; // 创建人ID
	private String creatorName; // 创建人
	private String createTime; // 创建时间
	@ValidateRule(dataType = DataType.STRING, label = "付款行", maxLength = 50)
	private String payBank;// 付款行
	@ValidateRule(dataType = DataType.STRING, label = "付款帐号", maxLength = 25)
	private String payAccount;// 付款帐号
	@ValidateRule(dataType = DataType.STRING, label = "收款行", maxLength = 50)
	private String receiveBank;// 收款行
	@ValidateRule(dataType = DataType.STRING, label = "收款帐号", maxLength = 25)
	private String receiveAccount;// 收款帐号
	@ValidateRule(dataType = DataType.STRING, label = "金额", maxLength = 30)
	private String sum;// 金额
	private String isAvailable;// 0无效,1有效
	@ManyToOne
	@JoinColumn(name = "TRANSFER_ID", nullable = false)
	private TInnerTransferInfo innerTransferInfo;

	@Transient
	public TInnerTransferInfo getInnerTransferInfo() {
		return innerTransferInfo;
	}

	public void setInnerTransferInfo(TInnerTransferInfo innerTransferInfo) {
		this.innerTransferInfo = innerTransferInfo;
	}

	@Id
	@Column(name = "DETAIL_ID", unique = true, nullable = false, length = 36)
	public String getDetailID() {
		return detailID;
	}

	public void setDetailID(String detailID) {
		this.detailID = detailID;
	}

	@Column(name = "TRANSFER_ID", nullable = false, length = 36)
	public String getTransferID() {
		return transferID;
	}

	public void setTransferID(String transferID) {
		this.transferID = transferID;
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

	@Column(name = "PAY_BANK", length = 50)
	public String getPayBank() {
		return payBank;
	}

	public void setPayBank(String payBank) {
		this.payBank = payBank;
	}

	@Column(name = "PAY_ACCOUNT", length = 25)
	public String getPayAccount() {
		return payAccount;
	}

	public void setPayAccount(String payAccount) {
		this.payAccount = payAccount;
	}

	@Column(name = "RECEIVE_BANK", length = 50)
	public String getReceiveBank() {
		return receiveBank;
	}

	public void setReceiveBank(String receiveBank) {
		this.receiveBank = receiveBank;
	}

	@Column(name = "RECEIVE_ACCOUNT", length = 25)
	public String getReceiveAccount() {
		return receiveAccount;
	}

	public void setReceiveAccount(String receiveAccount) {
		this.receiveAccount = receiveAccount;
	}

	@Column(name = "SUM", length = 30)
	public String getSum() {
		return sum;
	}

	public void setSum(String sum) {
		this.sum = sum;
	}

	@Column(name = "IS_AVAILABLE", length = 1)
	public String getIsAvailable() {
		return isAvailable;
	}

	public void setIsAvailable(String isAvailable) {
		this.isAvailable = isAvailable;
	}
}
