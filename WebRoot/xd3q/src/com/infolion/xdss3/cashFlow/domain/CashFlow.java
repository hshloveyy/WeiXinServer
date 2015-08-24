/**
 * 
 */
package com.infolion.xdss3.cashFlow.domain;

import java.math.BigDecimal;

import javax.persistence.Column;

import com.infolion.platform.dpframework.core.annotation.ValidateRule;

/**
 * @author 钟志华
 *
 */
public class CashFlow {
	
	 /**
     * 公司代码
     */
	@Column(name = "BUKRS")
	@ValidateRule(dataType = 9, label = "公司代码", maxLength = 200, required = false)
    private String bukrs;
    
    /**
     * 立项号 
     */
	@Column(name = "BNAME")
	@ValidateRule(dataType = 9, label = "立项号", maxLength = 200, required = false)
    private String bname;
	
	 /**
     * 业务范围
     */
	@Column(name = "GSBER")
	@ValidateRule(dataType = 9, label = "业务范围", maxLength = 200, required = false)
    private String gsber;
    
    
    /**
     * 客户编号
     */
	@Column(name = "KUNNR")
	@ValidateRule(dataType = 9, label = "客户编号", maxLength = 200, required = false)
    private String kunnr;
    
    /**
     * 客户名称
     */
	@Column(name = "CUSTOMERNAME")
	@ValidateRule(dataType = 9, label = "客户名称", maxLength = 200, required = false)
	private String customerName;
	
	 /**
     * 收款单号
     */
	@Column(name = "COLLECTNO")
	@ValidateRule(dataType = 9, label = "收款单号", maxLength = 200, required = false)
    private String collectno;
    /**
     * 付款单号
     */
	@Column(name = "PAYMENTNO")
	@ValidateRule(dataType = 9, label = "付款单号", maxLength = 200, required = false)
    private String paymentno;
    
    /**
     * 退款单号
     */
	@Column(name = "REFMENTNO")
	@ValidateRule(dataType = 9, label = "退款单号", maxLength = 200, required = false)
    private String refmentno;
    /**
     * 供应商或债权人的帐号
     */
	@Column(name = "LIFNR")
	@ValidateRule(dataType = 9, label = "供应商或债权人的帐号", maxLength = 200, required = false)
    private String lifnr;
    /**
     * 供应商名称
     */
	@Column(name = "SUPPLIERNAME")
	@ValidateRule(dataType = 9, label = "供应商名称", maxLength = 200, required = false)
    private String supplierName;
    /**
     * 会计凭证编号 
     */
	@Column(name = "BELNR")
	@ValidateRule(dataType = 9, label = "会计凭证编号", maxLength = 200, required = false)
    private String belnr;
    
    /**
     * 凭证中的过帐日期
     */
	@Column(name = "BUDAT")
	@ValidateRule(dataType = 9, label = "凭证中的过帐日期", maxLength = 200, required = false)
    private String budat;
    
    
    /**
     * 申请日期)
     */
	@Column(name = "AUGDT")
	@ValidateRule(dataType = 9, label = "申请日期", maxLength = 200, required = false)
    private String augdt;
    /**
     * 币别
     */
	@Column(name = "CURRENCY")
	@ValidateRule(dataType = 9, label = "币别", maxLength = 200, required = false)
    private String currency;
    /**
     * 按本位币计的金额
     */
	@Column(name = "DMBTR")
	@ValidateRule(dataType = 9, label = "按本位币计的金额", maxLength = 200, required = false)
    private BigDecimal dmbtr;
     
    /**
     * 凭证贷币金额
     */
	@Column(name = "WRBTR")
	@ValidateRule(dataType = 9, label = "凭证贷币金额", maxLength = 200, required = false)
    private BigDecimal wrbtr;
    
    /**
     * 在批金额
     */
	@Column(name = "ONROADAMOUNT")
	@ValidateRule(dataType = 9, label = "在批金额", maxLength = 200, required = false)
    private String onroadamount;
    /**
     * 在批本位金额
     */
	@Column(name = "ONROADAMOUNTBWB")
	@ValidateRule(dataType = 9, label = "在批本位金额", maxLength = 200, required = false)
    private String onroadamountBwb;
   
   
    /**
     * 项目文本
     */
	@Column(name = "SGTXT")
	@ValidateRule(dataType = 9, label = "项目文本", maxLength = 200, required = false)
    private String sgtxt; 
    
  
    
    /**
     * 原因代码
     */
	@Column(name = "RSTGR")
	@ValidateRule(dataType = 9, label = "原因代码", maxLength = 200, required = false)
    private String rstgr;
    
    /**
     * 原因代码（名称）
     */
	@Column(name = "TXT40")
	@ValidateRule(dataType = 9, label = "原因代码（名称）", maxLength = 200, required = false)
    private String txt40;
    
    
    /**
     * 总分类帐帐目
     */
	@Column(name = "HKONT")
	@ValidateRule(dataType = 9, label = "总分类帐帐目", maxLength = 200, required = false)
    private String hkont;
    
   
   /**
    * 借贷标识
    */
	@Column(name = "SHKZG")
	@ValidateRule(dataType = 9, label = "借贷标识", maxLength = 200, required = false)
    private String shkzg;
    /**
     * 对方科目
     */
	@Column(name = "SUBJECTCODE")
	@ValidateRule(dataType = 9, label = "对方科目", maxLength = 200, required = false)
    private String subjectCode;
    /**
     * 业务类型（进口，出口，内贸）
     */
	@Column(name = "BUSSINESSTYPE")
	@ValidateRule(dataType = 9, label = "业务类型（进口，出口，内贸）", maxLength = 200, required = false)
    private String bussinessType;
	
	@Column(name = "BUSSINESSID")
	@ValidateRule(dataType = 9, label = "id", maxLength = 200, required = false)
    private String bussinessid;
	
	@Column(name = "GJAHR")
	@ValidateRule(dataType = 9, label = "gjahr", maxLength = 200, required = false)
    private String gjahr ;
	
	@Column(name = "HKONT2")
	@ValidateRule(dataType = 9, label = "对方科目", maxLength = 200, required = false)
    private String hkont2;
	
	@Column(name = "BSART")
	@ValidateRule(dataType = 9, label = "BSART", maxLength = 200, required = false)
    private String BSART ;
	
	@Column(name = "VBELTYPE")
	@ValidateRule(dataType = 9, label = "业务范围", maxLength = 200, required = false)
    private String VBELTYPE;
	
	@Column(name = "BUZEI")
	@ValidateRule(dataType = 9, label = "行项目", maxLength = 200, required = false)	
    private String buzei;
	
	@Column(name = "USERNAME")
	@ValidateRule(dataType = 9, label = "用户名", maxLength = 200, required = false)
    private String username;
	
	public String getBussinessid() {
		return bussinessid;
	}

	public void setBussinessid(String bussinessid) {
		this.bussinessid = bussinessid;
	}

	public String getBukrs() {
		return bukrs;
	}

	public void setBukrs(String bukrs) {
		this.bukrs = bukrs;
	}

	public String getBelnr() {
		return belnr;
	}

	public void setBelnr(String belnr) {
		this.belnr = belnr;
	}



	public String getBudat() {
		return budat;
	}

	public void setBudat(String budat) {
		this.budat = budat;
	}

	public String getAugdt() {
		return augdt;
	}

	public void setAugdt(String augdt) {
		this.augdt = augdt;
	}


	public String getGsber() {
		return gsber;
	}

	public void setGsber(String gsber) {
		this.gsber = gsber;
	}

	public BigDecimal getDmbtr() {
		return dmbtr;
	}

	public void setDmbtr(BigDecimal dmbtr) {
		this.dmbtr = dmbtr;
	}

	public BigDecimal getWrbtr() {
		return wrbtr;
	}

	public void setWrbtr(BigDecimal wrbtr) {
		this.wrbtr = wrbtr;
	}

	

	public String getKunnr() {
		return kunnr;
	}

	public void setKunnr(String kunnr) {
		this.kunnr = kunnr;
	}

	public String getLifnr() {
		return lifnr;
	}

	public void setLifnr(String lifnr) {
		this.lifnr = lifnr;
	}

	


	public String getSgtxt() {
		return sgtxt;
	}

	public void setSgtxt(String sgtxt) {
		this.sgtxt = sgtxt;
	}



	public String getBname() {
		return bname;
	}

	public void setBname(String bname) {
		this.bname = bname;
	}

	public String getCollectno() {
		return collectno;
	}

	public void setCollectno(String collectno) {
		this.collectno = collectno;
	}

	public String getPaymentno() {
		return paymentno;
	}

	public void setPaymentno(String paymentno) {
		this.paymentno = paymentno;
	}

	public String getOnroadamount() {
		return onroadamount;
	}

	public void setOnroadamount(String onroadamount) {
		this.onroadamount = onroadamount;
	}

	public String getOnroadamountBwb() {
		return onroadamountBwb;
	}

	public void setOnroadamountBwb(String onroadamountBwb) {
		this.onroadamountBwb = onroadamountBwb;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getRstgr() {
		return rstgr;
	}

	public void setRstgr(String rstgr) {
		this.rstgr = rstgr;
	}

	public String getTxt40() {
		return txt40;
	}

	public void setTxt40(String txt40) {
		this.txt40 = txt40;
	}

	public String getHkont() {
		return hkont;
	}

	public void setHkont(String hkont) {
		this.hkont = hkont;
	}

	

	public String getRefmentno() {
		return refmentno;
	}

	public void setRefmentno(String refmentno) {
		this.refmentno = refmentno;
	}

	public String getShkzg() {
		return shkzg;
	}

	public void setShkzg(String shkzg) {
		this.shkzg = shkzg;
	}

	public String getSubjectCode() {
		return subjectCode;
	}

	public void setSubjectCode(String subjectCode) {
		this.subjectCode = subjectCode;
	}

	public String getBussinessType() {
		return bussinessType;
	}

	public void setBussinessType(String bussinessType) {
		this.bussinessType = bussinessType;
	}

	public String getGjahr() {
		return gjahr;
	}

	public void setGjahr(String gjahr) {
		this.gjahr = gjahr;
	}

	public String getHkont2() {
		return hkont2;
	}

	public void setHkont2(String hkont2) {
		this.hkont2 = hkont2;
	}

	public String getBSART() {
		return BSART;
	}

	public void setBSART(String bsart) {
		BSART = bsart;
	}

	public String getVBELTYPE() {
		return VBELTYPE;
	}

	public void setVBELTYPE(String vbeltype) {
		VBELTYPE = vbeltype;
	}

	public String getBuzei() {
		return buzei;
	}

	public void setBuzei(String buzei) {
		this.buzei = buzei;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
    
    
}
