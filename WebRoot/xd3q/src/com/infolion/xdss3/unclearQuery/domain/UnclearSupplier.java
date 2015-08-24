package com.infolion.xdss3.unclearQuery.domain;

import java.math.BigDecimal;

public class UnclearSupplier {
	private String  mandt;           // 客户端       
	private String  customerTitleId;   // ID
	private String  iscleared;         // 清账标识    
	private String  lifnr;      // 供应商代码  	        
	private String  bukrs;           // 公司代码        
	private String  gsber;           // 业务范围    
	private String  budat;           // 过账日期
	private String  belnr;           // 凭证号     
	private String  buzei;     // 行项目
	private String  gjahr;     // 会计年度
	private String  shkzg;         // 借贷方
	private String  waers;         // 币别
	private String  bktxt;        // 摘要
	private BigDecimal  dmbtr;         // 金额   
	private BigDecimal  bwbje;         // 本位币
	private BigDecimal rat;     //汇率
	private String  saknr;        // 总账科目 	  
	private BigDecimal unAmount; //未清金额
	private BigDecimal unbwbje; //未清本位币金额
	public String getMandt() {
		return mandt;
	}
	public void setMandt(String mandt) {
		this.mandt = mandt;
	}
	public String getCustomerTitleId() {
		return customerTitleId;
	}
	public void setCustomerTitleId(String customerTitleId) {
		this.customerTitleId = customerTitleId;
	}
	public String getIscleared() {
		return iscleared;
	}
	public void setIscleared(String iscleared) {
		this.iscleared = iscleared;
	}
	
	public String getLifnr() {
		return lifnr;
	}
	public void setLifnr(String lifnr) {
		this.lifnr = lifnr;
	}
	public String getBukrs() {
		return bukrs;
	}
	public void setBukrs(String bukrs) {
		this.bukrs = bukrs;
	}
	public String getGsber() {
		return gsber;
	}
	public void setGsber(String gsber) {
		this.gsber = gsber;
	}
	public String getBudat() {
		return budat;
	}
	public void setBudat(String budat) {
		this.budat = budat;
	}
	public String getBelnr() {
		return belnr;
	}
	public void setBelnr(String belnr) {
		this.belnr = belnr;
	}
	public String getBuzei() {
		return buzei;
	}
	public void setBuzei(String buzei) {
		this.buzei = buzei;
	}
	public String getGjahr() {
		return gjahr;
	}
	public void setGjahr(String gjahr) {
		this.gjahr = gjahr;
	}
	public String getShkzg() {
		return shkzg;
	}
	public void setShkzg(String shkzg) {
		this.shkzg = shkzg;
	}
	public String getWaers() {
		return waers;
	}
	public void setWaers(String waers) {
		this.waers = waers;
	}
	public String getBktxt() {
		return bktxt;
	}
	public void setBktxt(String bktxt) {
		this.bktxt = bktxt;
	}
	
	public String getSaknr() {
		return saknr;
	}
	public void setSaknr(String saknr) {
		this.saknr = saknr;
	}
	public BigDecimal getDmbtr() {
		return dmbtr;
	}
	public void setDmbtr(BigDecimal dmbtr) {
		this.dmbtr = dmbtr;
	}
	public BigDecimal getBwbje() {
		return bwbje;
	}
	public void setBwbje(BigDecimal bwbje) {
		this.bwbje = bwbje;
	}
	public BigDecimal getUnAmount() {
		return unAmount;
	}
	public void setUnAmount(BigDecimal unAmount) {
		this.unAmount = unAmount;
	}
	public BigDecimal getUnbwbje() {
		return unbwbje;
	}
	public void setUnbwbje(BigDecimal unbwbje) {
		this.unbwbje = unbwbje;
	}
	public BigDecimal getRat() {
		return rat;
	}
	public void setRat(BigDecimal rat) {
		this.rat = rat;
	}
	
	
}
