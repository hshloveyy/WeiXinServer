/**
 * 
 */
package com.infolion.xdss3.ageAnalysis.domain;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.infolion.platform.dpframework.core.annotation.ValidateRule;
import com.infolion.platform.dpframework.core.domain.BaseObject;

/**
 * @author zzh
 *
 */
@Entity
@Table(name = "YCLEARITEM")
@org.hibernate.annotations.Entity(dynamicInsert = true, dynamicUpdate = true)
public class ClearItem  extends BaseObject{
	// fields
	/*
	 * 客户端
	 */
	@Column(name = "MANDT")
	private String client;
	
	@Id
	@Column(name = "CLEARITEMID")	
	@ValidateRule(dataType = 9, label = "id", maxLength = 60, required = false)
	private String clearitemid;
	
	/*
	 * 业务ID
	 */
	@Column(name = "BUSINESSID")	
	@ValidateRule(dataType = 9, label = "业务ID", maxLength = 360, required = false)
	private String businessid;
	/*
	 * 业务明细ID
	 */
	
	@Column(name = "BUSINESSITEMID")	
	@ValidateRule(dataType = 9, label = "业务ID", maxLength = 360, required = false)
	private String businessitemid;
	/*
	 * 业务类型
	 */
	@Column(name = "BUSINESSTYPE")
	@ValidateRule(dataType = 9, label = "业务ID", maxLength = 360, required = false)
	private String businesstype;
	/*
	 * 外币金额
	 */
	@Column(name = "DMBTR")	
	@ValidateRule(dataType = 0, label = "外币金额", required = false)
	private BigDecimal dmbtr;
	/*
	 * 本位币金额
	 */
	@Column(name = "WRBTR")	
	@ValidateRule(dataType = 0, label = "本位币金额", required = false)
	private BigDecimal wrbtr;
	/*
	 * 业务发生人
	 */
	@Column(name = "CLEAROR")	
	@ValidateRule(dataType = 9, label = "业务发生人", maxLength = 360, required = false)
	private String clearor;
	/*
	 * 业务发生时间
	 */
	@Column(name = "CLEARTIME")	
	@ValidateRule(dataType = 9, label = "业务发生时间", maxLength = 360, required = false)
	private String cleartime;
	
	/*
	 * 公司代码
	 */
	@Column(name = "BUKRS")	
	@ValidateRule(dataType = 9, label = "公司代码", maxLength = 36, required = false)
	private String bukrs;
	
	/*
	 * 会计年度
	 */
	@Column(name = "GJAHR")	
	@ValidateRule(dataType = 9, label = "会计年度", maxLength = 36, required = false)
	private String gjahr;
	/*
	 * 会计凭证号
	 */
	@Column(name = "BELNR")	
	@ValidateRule(dataType = 9, label = "会计凭证号", maxLength = 36, required = false)
	private String belnr;
	/*
	 *凭证行项目号
	 */
	@Column(name = "BUZEI")	
	@ValidateRule(dataType = 9, label = "凭证行项目号", maxLength = 36, required = false)
	private String buzei;	
	
	@Column(name = "CUSTOMERNO")	
	@ValidateRule(dataType = 9, label = "客户代码", maxLength = 36, required = false)
	private String customerNo;	
	
	@Column(name = "CUSTOMERTYPE")	
	@ValidateRule(dataType = 9, label = "客户类型", maxLength = 10, required = false)
	private String customerType;	
	
	@Column(name = "SUBJECTCODE")	
	@ValidateRule(dataType = 9, label = "总账科目代码", maxLength = 80, required = false)
	private String subjectCode;	

	/*
	 * 借方/贷方标识
	 */
    @Column(name="SHKZG")
    @ValidateRule(dataType=9,label="借方/贷方标识",maxLength= 1,required=false)  
    private String shkzg;   
    
    /*
	 * 凭证中的过帐日期
	 */
    @Column(name="BUDAT")
    @ValidateRule(dataType=8,label="凭证中的过帐日期",required=false)  
    private String budat;   
    

    /*
     * --用户名
     */
    @Column(name = "USERNAME")
    @ValidateRule(dataType = 9, label = "用户名", required = false)
    private String username ;
    
    
	/**
	 * @return the client
	 */
	public String getClient() {
		return client;
	}
	/**
	 * @param client the client to set
	 */
	public void setClient(String client) {
		this.client = client;
	}
	/**
	 * 业务ID
	 * @return the businessid
	 */
	public String getBusinessid() {
		return businessid;
	}
	/**
	 * 业务ID
	 * @param businessid the businessid to set
	 */
	public void setBusinessid(String businessid) {
		this.businessid = businessid;
	}
	/**
	 * 业务明细ID
	 * @return the businessitemid
	 */
	public String getBusinessitemid() {
		return businessitemid;
	}
	/**
	 * 业务明细ID
	 * @param businessitemid the businessitemid to set
	 */
	public void setBusinessitemid(String businessitemid) {
		this.businessitemid = businessitemid;
	}
	/**
	 * 业务类型
	 * @return the businesstype
	 */
	public String getBusinesstype() {
		return businesstype;
	}
	/**
	 * 业务类型
	 * @param businesstype the businesstype to set
	 */
	public void setBusinesstype(String businesstype) {
		this.businesstype = businesstype;
	}
	/**
	 * 本位币金额
	 * @return the dmbtr
	 */
	public BigDecimal getDmbtr() {
		return dmbtr;
	}
	/**
	 * 本位币金额
	 * @param dmbtr the dmbtr to set
	 */
	public void setDmbtr(BigDecimal dmbtr) {
		this.dmbtr = dmbtr;
	}
	/**
	 * 外币金额
	 * @return the wrbtr
	 */
	public BigDecimal getWrbtr() {
		return wrbtr;
	}
	/**
	 * 外币金额
	 * @param wrbtr the wrbtr to set
	 */
	public void setWrbtr(BigDecimal wrbtr) {
		this.wrbtr = wrbtr;
	}
	/**
	 * 业务发生人
	 * @return the clearor
	 */
	public String getClearor() {
		return clearor;
	}
	/**
	 * 业务发生人
	 * @param clearor the clearor to set
	 */
	public void setClearor(String clearor) {
		this.clearor = clearor;
	}
	/**
	 * 业务发生时间
	 * @return the cleartime
	 */
	public String getCleartime() {
		return cleartime;
	}
	/**
	 * 业务发生时间
	 * @param cleartime the cleartime to set
	 */
	public void setCleartime(String cleartime) {
		this.cleartime = cleartime;
	}
	/**
	 * 公司代码
	 * @return the bukrs
	 */
	public String getBukrs() {
		return bukrs;
	}
	/**
	 * 公司代码
	 * @param bukrs the bukrs to set
	 */
	public void setBukrs(String bukrs) {
		this.bukrs = bukrs;
	}
	/**
	 * 会计年度
	 * @return the gjahr
	 */
	public String getGjahr() {
		return gjahr;
	}
	/**
	 * 会计年度
	 * @param gjahr the gjahr to set
	 */
	public void setGjahr(String gjahr) {
		this.gjahr = gjahr;
	}
	/**
	 * 会计凭证号
	 * @return the belnr
	 */
	public String getBelnr() {
		return belnr;
	}
	/**
	 * 会计凭证号
	 * @param belnr the belnr to set
	 */
	public void setBelnr(String belnr) {
		this.belnr = belnr;
	}
	/**
	 * 凭证行项目号
	 * @return the buzei
	 */
	public String getBuzei() {
		return buzei;
	}
	/**
	 * 凭证行项目号
	 * @param buzei the buzei to set
	 */
	public void setBuzei(String buzei) {
		this.buzei = buzei;
	}
	/**
	 * @return the clearitemid
	 */
	public String getClearitemid() {
		return clearitemid;
	}
	/**
	 * @param clearitemid the clearitemid to set
	 */
	public void setClearitemid(String clearitemid) {
		this.clearitemid = clearitemid;
	}
	/**
	 * 客户代码
	 * @return the customerNo
	 */
	public String getCustomerNo() {
		return customerNo;
	}
	/**
	 * 客户代码
	 * @param customerNo the customerNo to set
	 */
	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
	}
	/**
	 * 客户类型
	 * @return the customerType
	 */
	public String getCustomerType() {
		return customerType;
	}
	/**
	 * 客户类型
	 * @param customerType the customerType to set
	 */
	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}
	/**
	 * 总账科目代码
	 * @return the subjectCode
	 */
	public String getSubjectCode() {
		return subjectCode;
	}
	/**
	 * 总账科目代码
	 * @param subjectCode the subjectCode to set
	 */
	public void setSubjectCode(String subjectCode) {
		this.subjectCode = subjectCode;
	}
	public String getShkzg() {
		return shkzg;
	}
	public void setShkzg(String shkzg) {
		this.shkzg = shkzg;
	}
	public String getBudat() {
		return budat;
	}
	public void setBudat(String budat) {
		this.budat = budat;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}

	
	
}
