package com.infolion.xdss3.customerSupplierMaster.domain;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.infolion.platform.core.domain.ProcessObject;
import com.infolion.platform.dpframework.core.annotation.ValidateRule;
import com.infolion.platform.dpframework.core.domain.BaseObject;

/**
 * TSuppliers entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "YSUPPLIERMASTER")
public class SupplierMaster extends BaseObject {

	// Fields

	private String suppliersId;
	private String accountGroup;
	private String companyCode;
	private String purchaseOrg;
	private String title;
	private String suppliersCode;
	private String suppliersName1;
	private String suppliersName2;
	private String street;
	private String region;
	private String searchTerm;
	private String city;
	private String country;
	private String area;
	private String zipCode;
	private String phone;
	private String phoneExt;
	private String fax;
	private String contactMan;
	private String email;
	private String sapGuestCode;
	private String sapGuestName;
	private String currency;
	private String subjects;
	private String cashGroup;
	private String payWay;
	private String payTerm;

	private String cmd;
	private String deptId;
	private String examineState;
	private String applyTime;
	private String approvedTime;
	private String isAvailable;
	private String creatorTime;
	private String creator;
	private String addType;
	private String longText;
	private String businessNo;
	private String vatNum;
	 @Column(name="TYPE")
	 @ValidateRule(dataType=9,label="类型",maxLength= 20,required=false) 
	private String type;
	 @Column(name="CODE")
	 @ValidateRule(dataType=9,label="组织机构代码",maxLength= 20,required=false) 
	private String code;
	 @Column(name="FORMED")
	 @ValidateRule(dataType=9,label="成立时间",maxLength= 20,required=false) 
	private String formed;
	 @Column(name="CAPITAL")
	 @ValidateRule(dataType=9,label="注册资本",maxLength= 20,required=false) 
	private String capital;
	 @Column(name="NATURE")
	 @ValidateRule(dataType=9,label="公司性质",maxLength= 20,required=false) 
	private String nature;
	 @Column(name="COMPANYTYPE")
	 @ValidateRule(dataType=9,label="公司类型",maxLength= 200,required=false) 
	private String companytype;
	 @Column(name="PERIODSTART")
	 @ValidateRule(dataType=9,label="经营期限开始",maxLength= 20,required=false) 
	private String periodStart;
	 @Column(name="PERIODEND")
	 @ValidateRule(dataType=9,label="经营期限结束",maxLength= 20,required=false) 
	private String periodEnd;
	 @Column(name="ANNUALINSPECTION")
	 @ValidateRule(dataType=9,label="营业执照最新年检年度",maxLength= 20,required=false) 
	private String annualInspection;
	 @Column(name="OTHERINFO")
	 @ValidateRule(dataType=9,label="其他信息",maxLength= 2000,required=false) 
	private String otherinfo;
	 @Column(name="CAPITALCURRENCY")
	 @ValidateRule(dataType=9,label="注册资本币别",maxLength= 4,required=false) 
	private String capitalCurrency;
	 
	 @Column(name="UPDATETIME")
	 @ValidateRule(dataType=9,label="更改时间",maxLength= 20,required=false) 
	private String updateTime;
	 @Column(name="CREATEDEPT")
	 @ValidateRule(dataType=9,label="创建部门",maxLength= 20,required=false) 
	private String createDept;
	 @Column(name="CREATEOR")
	 @ValidateRule(dataType=9,label="创建人",maxLength= 20,required=false) 
	private String createor;
	 @Column(name="SUPPLIER_ID")
	 @ValidateRule(dataType=9,label="旧的主供应商的ID",maxLength= 40,required=false) 
	private String oldSupplierid; //旧的主客户的ID
	 
	 
	 @Column(name="LICENSEPATH")
	 @ValidateRule(dataType=9,label="企业法人营业执照上传路径",maxLength= 200,required=false) 
	private String licensePath;
	 
	 @Column(name="ORGANIZATIONPATH")
	 @ValidateRule(dataType=9,label="组织机构代码证上传路径",maxLength= 200,required=false) 
	private String organizationPath;
	 
	 @Column(name="TAXATIONPATH")
	 @ValidateRule(dataType=9,label="税务登记证上传路径",maxLength= 200,required=false) 
	private String taxationPath;
	 
	 @Column(name="SURVEYPATH")
	 @ValidateRule(dataType=9,label="资信调查报告上传路径",maxLength= 200,required=false) 
	private String surveyPath;
	 
	 @Column(name="FINANCIALPATH")
	 @ValidateRule(dataType=9,label="财务报表上传路径",maxLength= 200,required=false) 
	private String financialPath;
	 
	 @Column(name="CREDITPATH")
	 @ValidateRule(dataType=9,label="授信额度申请表上传路径",maxLength= 200,required=false) 
	private String creditPath;
	 
	 @Column(name="OTHERPATH")
	 @ValidateRule(dataType=9,label="其他上传路径",maxLength= 200,required=false) 
	private String otherPath;
	 
	 @Column(name="INSIDE")
	 @ValidateRule(dataType=9,label="是否内部客户",maxLength= 200,required=false) 
	private String inside;
	 
	// Constructors

	/** default constructor */
	public SupplierMaster() {
	}

	/** minimal constructor */
	public SupplierMaster(String suppliersId) {
		this.suppliersId = suppliersId;
	}

	/** full constructor */
//	public SupplierMaster(String suppliersId, String accountGroup,
//			String companyCode, String purchaseOrg, String title,
//			String suppliersCode, String suppliersName1, String suppliersName2,
//			String street, String region, String searchTerm, String city,
//			String country, String area, String zipCode, String phone,
//			String phoneExt, String fax, String contactMan, String email,
//			String sapGuestCode, String sapGuestName, String currency,
//			String subjects, String cashGroup, String payWay, String payTerm,
//			String totalMoney, String cmd, String deptId, String examineState,
//			String applyTime, String approvedTime, String isAvailable,
//			String creatorTime, String creator, String longText) {
//		this.suppliersId = suppliersId;
//		this.accountGroup = accountGroup;
//		this.companyCode = companyCode;
//		this.purchaseOrg = purchaseOrg;
//		this.title = title;
//		this.suppliersCode = suppliersCode;
//		this.suppliersName1 = suppliersName1;
//		this.suppliersName2 = suppliersName2;
//		this.street = street;
//		this.region = region;
//		this.searchTerm = searchTerm;
//		this.city = city;
//		this.country = country;
//		this.area = area;
//		this.zipCode = zipCode;
//		this.phone = phone;
//		this.phoneExt = phoneExt;
//		this.fax = fax;
//		this.contactMan = contactMan;
//		this.email = email;
//		this.sapGuestCode = sapGuestCode;
//		this.sapGuestName = sapGuestName;
//		this.currency = currency;
//		this.subjects = subjects;
//		this.cashGroup = cashGroup;
//		this.payWay = payWay;
//		this.payTerm = payTerm;
//		this.totalMoney = totalMoney;
//		this.cmd = cmd;
//		this.deptId = deptId;
//		this.examineState = examineState;
//		this.applyTime = applyTime;
//		this.approvedTime = approvedTime;
//		this.isAvailable = isAvailable;
//		this.creatorTime = creatorTime;
//		this.creator = creator;
//		this.longText = longText;
//	}

	// Property accessors
	@Id
	@Column(name = "SUPPLIERMASTER_ID", unique = true, nullable = false, length = 36)
	public String getSuppliersId() {
		return this.suppliersId;
	}

	public void setSuppliersId(String suppliersId) {
		this.suppliersId = suppliersId;
	}

	@Column(name = "ACCOUNT_GROUP", length = 4)
	public String getAccountGroup() {
		return this.accountGroup;
	}

	public void setAccountGroup(String accountGroup) {
		this.accountGroup = accountGroup;
	}

	@Column(name = "COMPANY_CODE", length = 4)
	public String getCompanyCode() {
		return this.companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	@Column(name = "PURCHASE_ORG", length = 4)
	public String getPurchaseOrg() {
		return this.purchaseOrg;
	}

	public void setPurchaseOrg(String purchaseOrg) {
		this.purchaseOrg = purchaseOrg;
	}

	@Column(name = "TITLE", length = 2000)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "SUPPLIERS_CODE", length = 8)
	public String getSuppliersCode() {
		return this.suppliersCode;
	}

	public void setSuppliersCode(String suppliersCode) {
		this.suppliersCode = suppliersCode;
	}

	@Column(name = "SUPPLIERS_NAME1", length = 2000)
	public String getSuppliersName1() {
		return this.suppliersName1;
	}

	public void setSuppliersName1(String suppliersName1) {
		this.suppliersName1 = suppliersName1;
	}

	@Column(name = "SUPPLIERS_NAME2", length = 2000)
	public String getSuppliersName2() {
		return this.suppliersName2;
	}

	public void setSuppliersName2(String suppliersName2) {
		this.suppliersName2 = suppliersName2;
	}

	@Column(name = "STREET", length = 2000)
	public String getStreet() {
		return this.street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	@Column(name = "REGION", length = 2000)
	public String getRegion() {
		return this.region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	@Column(name = "SEARCH_TERM", length = 2000)
	public String getSearchTerm() {
		return this.searchTerm;
	}

	public void setSearchTerm(String searchTerm) {
		this.searchTerm = searchTerm;
	}

	@Column(name = "CITY", length = 2000)
	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Column(name = "COUNTRY", length = 40)
	public String getCountry() {
		return this.country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	@Column(name = "AREA", length = 40)
	public String getArea() {
		return this.area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	@Column(name = "ZIP_CODE", length = 2000)
	public String getZipCode() {
		return this.zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	@Column(name = "PHONE", length = 2000)
	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(name = "PHONE_EXT", length = 2000)
	public String getPhoneExt() {
		return this.phoneExt;
	}

	public void setPhoneExt(String phoneExt) {
		this.phoneExt = phoneExt;
	}

	@Column(name = "FAX", length = 2000)
	public String getFax() {
		return this.fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	@Column(name = "CONTACT_MAN", length = 2000)
	public String getContactMan() {
		return this.contactMan;
	}

	public void setContactMan(String contactMan) {
		this.contactMan = contactMan;
	}

	@Column(name = "EMAIL", length = 2000)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "SAP_GUEST_CODE", length = 10)
	public String getSapGuestCode() {
		return this.sapGuestCode;
	}

	public void setSapGuestCode(String sapGuestCode) {
		this.sapGuestCode = sapGuestCode;
	}

	@Column(name = "SAP_GUEST_NAME", length = 2000)
	public String getSapGuestName() {
		return this.sapGuestName;
	}

	public void setSapGuestName(String sapGuestName) {
		this.sapGuestName = sapGuestName;
	}

	@Column(name = "CURRENCY", length = 4)
	public String getCurrency() {
		return this.currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	@Column(name = "SUBJECTS", length = 4)
	public String getSubjects() {
		return this.subjects;
	}

	public void setSubjects(String subjects) {
		this.subjects = subjects;
	}

	@Column(name = "CASH_GROUP", length = 4)
	public String getCashGroup() {
		return this.cashGroup;
	}

	public void setCashGroup(String cashGroup) {
		this.cashGroup = cashGroup;
	}

	@Column(name = "PAY_WAY", length = 4)
	public String getPayWay() {
		return this.payWay;
	}

	public void setPayWay(String payWay) {
		this.payWay = payWay;
	}

	@Column(name = "PAY_TERMS", length = 2000)
	public String getPayTerm() {
		return this.payTerm;
	}

	public void setPayTerm(String payTerm) {
		this.payTerm = payTerm;
	}

	

	@Column(name = "CMD", length = 2000)
	public String getCmd() {
		return this.cmd;
	}

	public void setCmd(String cmd) {
		this.cmd = cmd;
	}

	@Column(name = "DEPT_ID", length = 36)
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

	
	@Column(name = "add_type", length = 20)
	public String getAddType() {
		return addType;
	}

	public void setAddType(String addType) {
		this.addType = addType;
	}

	@Column(name = "long_text", length = 100)
	public String getLongText() {
		return longText;
	}

	public void setLongText(String longText) {
		this.longText = longText;
	}

	@Column(name = "BUSINESS_NO", length = 2000)
	public String getBusinessNo() {
		return this.businessNo;
	}

	public void setBusinessNo(String businessNo) {
		this.businessNo = businessNo;
	}
	
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the formed
	 */
	public String getFormed() {
		return formed;
	}

	/**
	 * @param formed the formed to set
	 */
	public void setFormed(String formed) {
		this.formed = formed;
	}

	/**
	 * @return the capital
	 */
	public String getCapital() {
		return capital;
	}

	/**
	 * @param capital the capital to set
	 */
	public void setCapital(String capital) {
		this.capital = capital;
	}

	/**
	 * @return the nature
	 */
	public String getNature() {
		return nature;
	}

	/**
	 * @param nature the nature to set
	 */
	public void setNature(String nature) {
		this.nature = nature;
	}

	/**
	 * @return the companytype
	 */
	public String getCompanytype() {
		return companytype;
	}

	/**
	 * @param companytype the companytype to set
	 */
	public void setCompanytype(String companytype) {
		this.companytype = companytype;
	}

	

	

	/**
	 * @return the periodStart
	 */
	public String getPeriodStart() {
		return periodStart;
	}

	/**
	 * @param periodStart the periodStart to set
	 */
	public void setPeriodStart(String periodStart) {
		this.periodStart = periodStart;
	}

	/**
	 * @return the periodEnd
	 */
	public String getPeriodEnd() {
		return periodEnd;
	}

	/**
	 * @param periodEnd the periodEnd to set
	 */
	public void setPeriodEnd(String periodEnd) {
		this.periodEnd = periodEnd;
	}

	/**
	 * @return the annualInspection
	 */
	public String getAnnualInspection() {
		return annualInspection;
	}

	/**
	 * @param annualInspection the annualInspection to set
	 */
	public void setAnnualInspection(String annualInspection) {
		this.annualInspection = annualInspection;
	}

	

	
	

	/**
	 * @return the otherinfo
	 */
	public String getOtherinfo() {
		return otherinfo;
	}

	/**
	 * @param otherinfo the otherinfo to set
	 */
	public void setOtherinfo(String otherinfo) {
		this.otherinfo = otherinfo;
	}

	/**
	 * @return the updateTime
	 */
	public String getUpdateTime() {
		return updateTime;
	}

	/**
	 * @param updateTime the updateTime to set
	 */
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	/**
	 * @return the createDept
	 */
	public String getCreateDept() {
		return createDept;
	}

	/**
	 * @param createDept the createDept to set
	 */
	public void setCreateDept(String createDept) {
		this.createDept = createDept;
	}

	/**
	 * @return the createor
	 */
	public String getCreateor() {
		return createor;
	}

	/**
	 * @param createor the createor to set
	 */
	public void setCreateor(String createor) {
		this.createor = createor;
	}

	/**
	 * @return the oldSupplierid
	 */
	public String getOldSupplierid() {
		return oldSupplierid;
	}

	/**
	 * @param oldSupplierid the oldSupplierid to set
	 */
	public void setOldSupplierid(String oldSupplierid) {
		this.oldSupplierid = oldSupplierid;
	}

	/**
	 * @return the licensePath
	 */
	public String getLicensePath() {
		return licensePath;
	}

	/**
	 * @param licensePath the licensePath to set
	 */
	public void setLicensePath(String licensePath) {
		this.licensePath = licensePath;
	}

	/**
	 * @return the organizationPath
	 */
	public String getOrganizationPath() {
		return organizationPath;
	}

	/**
	 * @param organizationPath the organizationPath to set
	 */
	public void setOrganizationPath(String organizationPath) {
		this.organizationPath = organizationPath;
	}

	/**
	 * @return the taxationPath
	 */
	public String getTaxationPath() {
		return taxationPath;
	}

	/**
	 * @param taxationPath the taxationPath to set
	 */
	public void setTaxationPath(String taxationPath) {
		this.taxationPath = taxationPath;
	}

	/**
	 * @return the surveyPath
	 */
	public String getSurveyPath() {
		return surveyPath;
	}

	/**
	 * @param surveyPath the surveyPath to set
	 */
	public void setSurveyPath(String surveyPath) {
		this.surveyPath = surveyPath;
	}

	/**
	 * @return the financialPath
	 */
	public String getFinancialPath() {
		return financialPath;
	}

	/**
	 * @param financialPath the financialPath to set
	 */
	public void setFinancialPath(String financialPath) {
		this.financialPath = financialPath;
	}

	/**
	 * @return the otherPath
	 */
	public String getOtherPath() {
		return otherPath;
	}

	/**
	 * @param otherPath the otherPath to set
	 */
	public void setOtherPath(String otherPath) {
		this.otherPath = otherPath;
	}
	@Column(name = "VAT_NUM", length = 2000)
	public String getVatNum() {
		return this.vatNum;
	}

	public void setVatNum(String vatNum) {
		this.vatNum = vatNum;
	}

	/**
	 * @return the inside
	 */
	public String getInside() {
		return inside;
	}

	/**
	 * @param inside the inside to set
	 */
	public void setInside(String inside) {
		this.inside = inside;
	}

	/**
	 * @return the capitalCurrency
	 */
	public String getCapitalCurrency() {
		return capitalCurrency;
	}

	/**
	 * @param capitalCurrency the capitalCurrency to set
	 */
	public void setCapitalCurrency(String capitalCurrency) {
		this.capitalCurrency = capitalCurrency;
	}

	/**
	 * @return the creditPath
	 */
	public String getCreditPath() {
		return creditPath;
	}

	/**
	 * @param creditPath the creditPath to set
	 */
	public void setCreditPath(String creditPath) {
		this.creditPath = creditPath;
	}
	
}