package com.infolion.sapss.mainData.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.infolion.platform.core.annotation.ValidateRule;
import com.infolion.platform.core.domain.ProcessObject;
import com.infolion.platform.core.validation.DataType;

/**
 * TMaterial entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_MATERIAL")
public class TMaterial extends ProcessObject {

	// Fields

	private String materialId;
	//@ValidateRule(dataType = DataType.STRING, label = "物料编号", required = true)		
	private String materialCode;
	private String materialName;
	//@ValidateRule(dataType = DataType.STRING, label = "更改物料", required = true)	
	private String alertMaterialCode;
//	@ValidateRule(dataType = DataType.STRING, label = "更改物料描述", required = true)	
	private String alertMaterialName;
	private String materialUnit;
	private String materialGroup;
	private String materialGroupName;
	private String materialSaleTax;
	//@ValidateRule(dataType = DataType.STRING, label = "是否既有出口又有内销", required = true)	
	private String isExpImp;
	//@ValidateRule(dataType = DataType.STRING, label = "出口订单销项税", required = true)	
	private String expSaleTax;
	//@ValidateRule(dataType = DataType.STRING, label = "进口订单销项税", required = true)	
	private String impSaleTax;
	//@ValidateRule(dataType = DataType.STRING, label = "内销订单销项税", required = true)
	private String withinSaleTax;
	private String cmd;
	private String deptId;
	private String examineState;
	private String applyTime;
	private String approvedTime;
	private String isAvailable;
	private String creatorTime;
	private String creator;
    private String saleOrg;
    private String addType;
	// Constructors

	/** default constructor */
	public TMaterial() {
	}

	/** minimal constructor */
	public TMaterial(String materialId) {
		this.materialId = materialId;
	}

	/** full constructor */
	public TMaterial(String materialId, String materialCode,
			String materialName, String alertMaterialCode,
			String alertMaterialName, String materialUnit,
			String materialGroup, String materialGroupName,
			String materialSaleTax, String isExpImp, String expSaleTax,
			String impSaleTax, String withinSaleTax, String cmd, String deptId,
			String examineState, String applyTime, String approvedTime,
			String isAvailable, String creatorTime, String creator,String saleOrg) {
		this.materialId = materialId;
		this.materialCode = materialCode;
		this.materialName = materialName;
		this.alertMaterialCode = alertMaterialCode;
		this.alertMaterialName = alertMaterialName;
		this.materialUnit = materialUnit;
		this.materialGroup = materialGroup;
		this.materialGroupName = materialGroupName;
		this.materialSaleTax = materialSaleTax;
		this.isExpImp = isExpImp;
		this.expSaleTax = expSaleTax;
		this.impSaleTax = impSaleTax;
		this.withinSaleTax = withinSaleTax;
		this.cmd = cmd;
		this.deptId = deptId;
		this.examineState = examineState;
		this.applyTime = applyTime;
		this.approvedTime = approvedTime;
		this.isAvailable = isAvailable;
		this.creatorTime = creatorTime;
		this.creator = creator;
		this.saleOrg = saleOrg;
	}

	// Property accessors
	@Id
	@Column(name = "MATERIAL_ID", unique = true, nullable = false, length = 36)
	public String getMaterialId() {
		return this.materialId;
	}

	public void setMaterialId(String materialId) {
		this.materialId = materialId;
	}

	@Column(name = "MATERIAL_CODE", length = 50)
	public String getMaterialCode() {
		return this.materialCode;
	}

	public void setMaterialCode(String materialCode) {
		this.materialCode = materialCode;
	}

	@Column(name = "MATERIAL_NAME", length = 100)
	public String getMaterialName() {
		return this.materialName;
	}

	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}

	@Column(name = "ALERT_MATERIAL_CODE", length = 50)
	public String getAlertMaterialCode() {
		return this.alertMaterialCode;
	}

	public void setAlertMaterialCode(String alertMaterialCode) {
		this.alertMaterialCode = alertMaterialCode;
	}

	@Column(name = "ALERT_MATERIAL_NAME", length = 100)
	public String getAlertMaterialName() {
		return this.alertMaterialName;
	}

	public void setAlertMaterialName(String alertMaterialName) {
		this.alertMaterialName = alertMaterialName;
	}

	@Column(name = "MATERIAL_UNIT", length = 50)
	public String getMaterialUnit() {
		return this.materialUnit;
	}

	public void setMaterialUnit(String materialUnit) {
		this.materialUnit = materialUnit;
	}

	@Column(name = "MATERIAL_GROUP", length = 50)
	public String getMaterialGroup() {
		return this.materialGroup;
	}

	public void setMaterialGroup(String materialGroup) {
		this.materialGroup = materialGroup;
	}

	@Column(name = "MATERIAL_GROUP_NAME", length = 100)
	public String getMaterialGroupName() {
		return this.materialGroupName;
	}

	public void setMaterialGroupName(String materialGroupName) {
		this.materialGroupName = materialGroupName;
	}

	@Column(name = "MATERIAL_SALE_TAX", length = 50)
	public String getMaterialSaleTax() {
		return this.materialSaleTax;
	}

	public void setMaterialSaleTax(String materialSaleTax) {
		this.materialSaleTax = materialSaleTax;
	}

	@Column(name = "IS_EXP_IMP", length = 50)
	public String getIsExpImp() {
		return this.isExpImp;
	}

	public void setIsExpImp(String isExpImp) {
		this.isExpImp = isExpImp;
	}

	@Column(name = "EXP_SALE_TAX", length = 50)
	public String getExpSaleTax() {
		return this.expSaleTax;
	}

	public void setExpSaleTax(String expSaleTax) {
		this.expSaleTax = expSaleTax;
	}

	@Column(name = "IMP_SALE_TAX", length = 50)
	public String getImpSaleTax() {
		return this.impSaleTax;
	}

	public void setImpSaleTax(String impSaleTax) {
		this.impSaleTax = impSaleTax;
	}

	@Column(name = "WITHIN_SALE_TAX", length = 50)
	public String getWithinSaleTax() {
		return this.withinSaleTax;
	}

	public void setWithinSaleTax(String withinSaleTax) {
		this.withinSaleTax = withinSaleTax;
	}

	@Column(name = "CMD", length = 4000)
	public String getCmd() {
		return this.cmd;
	}

	public void setCmd(String cmd) {
		this.cmd = cmd;
	}

	@Column(name = "DEPT_ID", length = 50)
	public String getDeptId() {
		return this.deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	@Column(name = "EXAMINE_STATE", length = 2)
	public String getExamineState() {
		return this.examineState;
	}

	public void setExamineState(String examineState) {
		this.examineState = examineState;
	}

	@Column(name = "APPLY_TIME", length = 20)
	public String getApplyTime() {
		return this.applyTime;
	}

	public void setApplyTime(String applyTime) {
		this.applyTime = applyTime;
	}

	@Column(name = "APPROVED_TIME", length = 20)
	public String getApprovedTime() {
		return this.approvedTime;
	}

	public void setApprovedTime(String approvedTime) {
		this.approvedTime = approvedTime;
	}

	@Column(name = "IS_AVAILABLE", length = 1)
	public String getIsAvailable() {
		return this.isAvailable;
	}

	public void setIsAvailable(String isAvailable) {
		this.isAvailable = isAvailable;
	}

	@Column(name = "CREATOR_TIME", length = 20)
	public String getCreatorTime() {
		return this.creatorTime;
	}

	public void setCreatorTime(String creatorTime) {
		this.creatorTime = creatorTime;
	}

	@Column(name = "CREATOR", length = 36)
	public String getCreator() {
		return this.creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}
	
	@Column(name = "SALE_ORG", length = 10)
	public String getSaleOrg() {
		return this.saleOrg;
	}

	public void setSaleOrg(String saleOrg) {
		this.saleOrg = saleOrg;
	}
	@Override
	public void setWorkflowModelId()
	{
		this.workflowModelId = "1002";
	}

	@Override
	public void setWorkflowModelName()
	{
		this.workflowModelName = "物料主数据";
	}

	@Override
	public void setWorkflowProcessName()
	{
	//	this.workflowProcessName = "materiel_data_v1";
	}

	@Override
	public void setWorkflowProcessUrl()
	{
		this.workflowProcessUrl = "materialController.spr?action=examine";
	}
	@Column(name = "add_type", length = 10)
	public String getAddType() {
		return addType;
	}

	public void setAddType(String addType) {
		this.addType = addType;
	}
}