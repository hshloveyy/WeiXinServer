package com.infolion.sapss.account.web;

import com.infolion.platform.core.annotation.ValidateRule;
import com.infolion.platform.core.domain.BaseObject;
import com.infolion.platform.core.validation.DataType;

public class SubjectAdditionalVO extends BaseObject {

	private static final long serialVersionUID = 1858570761495901164L;
	private String subjectID;
	private String remark; // 备注
	@ValidateRule(dataType = DataType.STRING, label = "科目货币", required = true)
	private String currency; // 科目货币
	@ValidateRule(dataType = DataType.STRING, label = "资产负债表科目")
	private String isBS; // BS,资产负债表科目
	@ValidateRule(dataType = DataType.STRING, label = "损益表科目")
	private String isIS; // IS，损益表科目
	@ValidateRule(dataType = DataType.STRING, label = "是否仅以本位币显示余额", required = true)
	private String isOnlyCNY;// 是否仅以本位币显示余额
	@ValidateRule(dataType = DataType.STRING, label = "税类别", maxLength = 1)
	private String taxType;// 税类别
	@ValidateRule(dataType = DataType.STRING, label = "允许无税过帐", required = true)
	private String isAllowNoTax;// 允许无税过帐
	private String controlType;// 统驭科目类型
	@ValidateRule(dataType = DataType.STRING, label = "未清项管理", required = true)
	private String isNotClear;// 未清项管理
	@ValidateRule(dataType = DataType.STRING, label = "行项目显示", required = true)
	private String isShowRows;// 行项目显示
	@ValidateRule(dataType = DataType.STRING, label = "排序码", maxLength = 3, required = true)
	private String orderNO;// 排序码
	@ValidateRule(dataType = DataType.STRING, label = "只允许自动过帐", required = true)
	private String isOnlyAuto;// 只允许自动过帐
	@ValidateRule(dataType = DataType.STRING, label = "与现金流动有关", required = true)
	private String isCashRelated;// 与现金流动有关
	@ValidateRule(dataType = DataType.STRING, label = "是否新增")
	private String isNew;// 是否新增

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getIsBS() {
		return isBS;
	}

	public void setIsBS(String isBS) {
		this.isBS = isBS;
	}

	public String getIsIS() {
		return isIS;
	}

	public void setIsIS(String isIS) {
		this.isIS = isIS;
	}

	public String getIsOnlyCNY() {
		return isOnlyCNY;
	}

	public void setIsOnlyCNY(String isOnlyCNY) {
		this.isOnlyCNY = isOnlyCNY;
	}

	public String getTaxType() {
		return taxType;
	}

	public void setTaxType(String taxType) {
		this.taxType = taxType;
	}

	public String getIsAllowNoTax() {
		return isAllowNoTax;
	}

	public void setIsAllowNoTax(String isAllowNoTax) {
		this.isAllowNoTax = isAllowNoTax;
	}

	public String getControlType() {
		return controlType;
	}

	public void setControlType(String controlType) {
		this.controlType = controlType;
	}

	public String getIsNotClear() {
		return isNotClear;
	}

	public void setIsNotClear(String isNotClear) {
		this.isNotClear = isNotClear;
	}

	public String getIsShowRows() {
		return isShowRows;
	}

	public void setIsShowRows(String isShowRows) {
		this.isShowRows = isShowRows;
	}

	public String getOrderNO() {
		return orderNO;
	}

	public void setOrderNO(String orderNO) {
		this.orderNO = orderNO;
	}

	public String getIsOnlyAuto() {
		return isOnlyAuto;
	}

	public void setIsOnlyAuto(String isOnlyAuto) {
		this.isOnlyAuto = isOnlyAuto;
	}

	public String getIsCashRelated() {
		return isCashRelated;
	}

	public void setIsCashRelated(String isCashRelated) {
		this.isCashRelated = isCashRelated;
	}

	public String getIsNew() {
		return isNew;
	}

	public void setIsNew(String isNew) {
		this.isNew = isNew;
	}

	public String getSubjectID() {
		return subjectID;
	}

	public void setSubjectID(String subjectID) {
		this.subjectID = subjectID;
	}
}
