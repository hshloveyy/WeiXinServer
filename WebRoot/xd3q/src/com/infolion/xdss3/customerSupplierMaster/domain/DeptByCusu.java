package com.infolion.xdss3.customerSupplierMaster.domain;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.infolion.platform.core.domain.BaseObject;
import com.infolion.platform.dpframework.core.annotation.ValidateRule;



/**
 * TGuest entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "YDEPTBYCUSU")
public class DeptByCusu extends BaseObject  {

	// Fields
	@Id
	@Column(name = "DEPTBYCUSUID", unique = true, nullable = false, length = 36)
	private String deptByCusuId;

	 @Column(name="GUESTCODE")
	 @ValidateRule(dataType=9,label="客户编号",maxLength= 20,required=false) 
	private String guestcode;
	 @Column(name="SUPPLIERCODE")
	 @ValidateRule(dataType=9,label="供应商编号",maxLength= 20,required=false) 
	private String suppliercode;
	 @Column(name="DEPTID")
	 @ValidateRule(dataType=9,label="部门ID",maxLength= 36 ,required=false) 
	private String deptid;
	 @Column(name="GSBER")
	 @ValidateRule(dataType=9,label="业务范围",maxLength= 20,required=false) 
	private String gsber;
	 @Column(name="COMPANY_CODE")
	 @ValidateRule(dataType=9,label="公司代码",maxLength= 20,required=false) 
	private String company_code;
	 
	 
	// Constructors

	/** default constructor */
	public DeptByCusu() {
	}

	/** minimal constructor */
	public DeptByCusu(String deptByCusuId) {
		this.deptByCusuId = deptByCusuId;
	}

	/**
	 * @return the deptByCusuId
	 */
	public String getDeptByCusuId() {
		return deptByCusuId;
	}

	/**
	 * @param deptByCusuId the deptByCusuId to set
	 */
	public void setDeptByCusuId(String deptByCusuId) {
		this.deptByCusuId = deptByCusuId;
	}

	/**
	 * @return the guestcode
	 */
	public String getGuestcode() {
		return guestcode;
	}

	/**
	 * @param guestcode the guestcode to set
	 */
	public void setGuestcode(String guestcode) {
		this.guestcode = guestcode;
	}

	/**
	 * @return the suppliercode
	 */
	public String getSuppliercode() {
		return suppliercode;
	}

	/**
	 * @param suppliercode the suppliercode to set
	 */
	public void setSuppliercode(String suppliercode) {
		this.suppliercode = suppliercode;
	}

	/**
	 * @return the deptid
	 */
	public String getDeptid() {
		return deptid;
	}

	/**
	 * @param deptid the deptid to set
	 */
	public void setDeptid(String deptid) {
		this.deptid = deptid;
	}

	/**
	 * @return the gsber
	 */
	public String getGsber() {
		return gsber;
	}

	/**
	 * @param gsber the gsber to set
	 */
	public void setGsber(String gsber) {
		this.gsber = gsber;
	}

	/**
	 * @return the company_code
	 */
	public String getCompany_code() {
		return company_code;
	}

	/**
	 * @param company_code the company_code to set
	 */
	public void setCompany_code(String company_code) {
		this.company_code = company_code;
	}



	
	
	
	
}