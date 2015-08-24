package com.infolion.sapss.account.web;

import com.infolion.platform.core.annotation.ValidateRule;
import com.infolion.platform.core.domain.BaseObject;
import com.infolion.platform.core.validation.DataType;

public class SubjectFinalVO extends BaseObject {

	private static final long serialVersionUID = -1385754967374748530L;
	private String subjectID;
	@ValidateRule(dataType = DataType.STRING, label = "计划层次", required = true)
	private String planLayer;// 计划层次
	@ValidateRule(dataType = DataType.STRING, label = "字段状态群组", required = true)
	private String colStateGroup;// 字段状态群组

	public String getSubjectID() {
		return subjectID;
	}

	public void setSubjectID(String subjectID) {
		this.subjectID = subjectID;
	}

	public String getPlanLayer() {
		return planLayer;
	}

	public void setPlanLayer(String planLayer) {
		this.planLayer = planLayer;
	}

	public String getColStateGroup() {
		return colStateGroup;
	}

	public void setColStateGroup(String colStateGroup) {
		this.colStateGroup = colStateGroup;
	}

}
