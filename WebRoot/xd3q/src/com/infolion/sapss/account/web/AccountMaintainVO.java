package com.infolion.sapss.account.web;

import com.infolion.platform.core.annotation.ValidateRule;
import com.infolion.platform.core.domain.BaseObject;
import com.infolion.platform.core.validation.DataType;

public class AccountMaintainVO extends BaseObject{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1581728026209587345L;
	private String accountID;// 主键
	@ValidateRule(dataType = DataType.STRING, label = "会计科目", maxLength = 2, required = true)
    private String accountTitle;// 会计科目
	@ValidateRule(dataType = DataType.STRING, label = "银行科目期初余额维护", maxLength = 2, required = true)
    private String balanceMaintain;//银行科目期初余额维护
	@ValidateRule(dataType = DataType.STRING, label = "出纳日记账", maxLength = 2, required = true)
    private String cashierAccount;//出纳日记账
	
	public String getAccountID() {
		return accountID;
	}
	public void setAccountID(String accountID) {
		this.accountID = accountID;
	}
	public String getAccountTitle() {
		return accountTitle;
	}
	public void setAccountTitle(String accountTitle) {
		this.accountTitle = accountTitle;
	}
	public String getBalanceMaintain() {
		return balanceMaintain;
	}
	public void setBalanceMaintain(String balanceMaintain) {
		this.balanceMaintain = balanceMaintain;
	}
	public String getCashierAccount() {
		return cashierAccount;
	}
	public void setCashierAccount(String cashierAccount) {
		this.cashierAccount = cashierAccount;
	}
	
	
}
