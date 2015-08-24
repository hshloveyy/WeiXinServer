package com.infolion.sapss.account.web;

import com.infolion.platform.core.annotation.ValidateRule;
import com.infolion.platform.core.domain.BaseObject;
import com.infolion.platform.core.validation.DataType;

public class AccountGeneralVO extends BaseObject {

	private static final long serialVersionUID = 6945615151552589158L;
	private String accountID;// 主键
	@ValidateRule(dataType = DataType.STRING, label = "总帐科目代码", maxLength = 10, required = true)
	private String subjectNO;// 总帐科目代码
	@ValidateRule(dataType = DataType.STRING, label = "短文本", maxLength = 20, required = true)
	private String shortDescription;// 短文本
	@ValidateRule(dataType = DataType.STRING, label = "长文本", maxLength = 200, required = true)
	private String longDescription;// 长文本

	public String getSubjectNO() {
		return subjectNO;
	}

	public void setSubjectNO(String subjectNO) {
		this.subjectNO = subjectNO;
	}

	public String getShortDescription() {
		return shortDescription;
	}

	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}

	public String getLongDescription() {
		return longDescription;
	}

	public void setLongDescription(String longDescription) {
		this.longDescription = longDescription;
	}

	public String getAccountID() {
		return accountID;
	}

	public void setAccountID(String accountID) {
		this.accountID = accountID;
	}
}
