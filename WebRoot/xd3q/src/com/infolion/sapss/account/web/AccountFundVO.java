package com.infolion.sapss.account.web;

import com.infolion.platform.core.annotation.ValidateRule;
import com.infolion.platform.core.domain.BaseObject;
import com.infolion.platform.core.validation.DataType;

public class AccountFundVO extends BaseObject {

	private static final long serialVersionUID = 7704083593848327275L;
	private String accountID;// 主键
	@ValidateRule(dataType = DataType.STRING, label = "银行编号", maxLength = 2, required = true)
	private String headOfficeNO;// 银行编号
	@ValidateRule(dataType = DataType.STRING, label = "银行代码", maxLength = 8, required = true)
	private String headOfficeCode;// 银行代码
	@ValidateRule(dataType = DataType.STRING, label = "开户行代码", maxLength = 5, required = true)
	private String bankCode;// 开户行代码
	@ValidateRule(dataType = DataType.STRING, label = "帐户标识", maxLength = 5, required = false)
	private String accountFlag;// 帐户标识

	public String getHeadOfficeNO() {
		return headOfficeNO;
	}

	public void setHeadOfficeNO(String headOfficeNO) {
		this.headOfficeNO = headOfficeNO;
	}

	public String getHeadOfficeCode() {
		return headOfficeCode;
	}

	public void setHeadOfficeCode(String headOfficeCode) {
		this.headOfficeCode = headOfficeCode;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getAccountFlag() {
		return accountFlag;
	}

	public void setAccountFlag(String accountFlag) {
		this.accountFlag = accountFlag;
	}

	public String getAccountID() {
		return accountID;
	}

	public void setAccountID(String accountID) {
		this.accountID = accountID;
	}
}
