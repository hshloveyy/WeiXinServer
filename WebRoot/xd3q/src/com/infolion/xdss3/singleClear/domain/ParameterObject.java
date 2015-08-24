package com.infolion.xdss3.singleClear.domain;

import java.math.BigDecimal;

import com.infolion.xdss3.financeSquare.domain.FundFlow;
import com.infolion.xdss3.financeSquare.domain.SettleSubject;
import com.infolion.xdss3.voucher.domain.Voucher;

/**
 * * <pre>
 * (客户全面清帐(ParameterObject),参数类
 * </pre>
 * 
 * @author zhongzh
 * @version 1.0
 * @see The author for more details
 * @since 1.0
 */
public class ParameterObject implements IParameter{	
	private String businesstype;//业务类型 客户08 供应商09
	private String vouchertype;//凭证类型SA
	private String voucherclass;//区别其他凭证 1
	private String businessid;//原始单据的ID
	private String bukrs;//公司代码
	private String accountdate;//过账日期
	private String currencytext;//币别（2600为HKD,其他为CNY）
	private String gsber; //业务范围
	private String voucherdate;//凭证日期
	private String text;//凭证抬头文本	
	private String customer; //客户号
	private String custom_htext;	//客户文本	
	private BigDecimal sumAdjustamount;//本位币相差的金额(汇损，调整金)	
	private String taxCode; //用来区别汇损（调整）和 其他凭证(存立项号或合同号)
	private String strType; //
	private String rownumber;//行项目
	private BigDecimal subtractVlaue;//本位币相差的金额
	private String  supplier;//供应商
	private String supplier_htext; //供应商文本
	private String checkCode; //记账码1
	private String checkCode2;//记账码2
	private String debitcredit;//借贷方标识
	private String voucherid;//凭证ID
	/**
	 * 业务类型 客户08 供应商09
	 * @return
	 */
	public String getBusinesstype() {
		return businesstype;
	}
	/**业务类型 客户08 供应商09
	 * 
	 * @param businesstype
	 */
	public void setBusinesstype(String businesstype) {
		this.businesstype = businesstype;
	}
	/**
	 * 凭证类型SA
	 * @return
	 */
	public String getVouchertype() {
		return vouchertype;
	}
	/**凭证类型SA
	 * 
	 * @param vouchertype
	 */
	public void setVouchertype(String vouchertype) {
		this.vouchertype = vouchertype;
	}
	/**
	 * 区别其他凭证 1
	 * @return
	 */
	public String getVoucherclass() {
		return voucherclass;
	}
	/**区别其他凭证 1
	 * 
	 * @param voucherclass
	 */
	public void setVoucherclass(String voucherclass) {
		this.voucherclass = voucherclass;
	}
	/**原始单据的ID
	 * 
	 * @return
	 */
	public String getBusinessid() {
		return businessid;
	}
	/**原始单据的ID
	 * 
	 * @param businessid
	 */
	public void setBusinessid(String businessid) {
		this.businessid = businessid;
	}
	/**
	 * 公司代码
	 * @return
	 */
	public String getBukrs() {
		return bukrs;
	}
	/**
	 * 公司代码
	 * @param bukrs
	 */
	public void setBukrs(String bukrs) {
		this.bukrs = bukrs;
	}
	/**
	 * 过账日期
	 * @return
	 */
	public String getAccountdate() {
		return accountdate;
	}
	/**
	 * 过账日期
	 * @param accountdate
	 */
	public void setAccountdate(String accountdate) {
		this.accountdate = accountdate;
	}
	/**
	 * 币别（2600为HKD,其他为CNY）
	 * @return
	 */
	public String getCurrencytext() {
		return currencytext;
	}
	/**
	 * 币别（2600为HKD,其他为CNY）
	 * @param currencytext
	 */
	public void setCurrencytext(String currencytext) {
		this.currencytext = currencytext;
	}
	/**
	 * 业务范围
	 * @return
	 */
	public String getGsber() {
		return gsber;
	}
	/**
	 * 业务范围
	 * @param gsber
	 */
	public void setGsber(String gsber) {
		this.gsber = gsber;
	}
	/**
	 * 凭证日期
	 * @return
	 */
	public String getVoucherdate() {
		return voucherdate;
	}
	/**
	 * 凭证日期
	 * @param voucherdate
	 */
	public void setVoucherdate(String voucherdate) {
		this.voucherdate = voucherdate;
	}
	/**
	 * 凭证抬头文本	
	 * @return
	 */
	public String getText() {
		return text;
	}
	/**
	 * 凭证抬头文本	
	 * @param text
	 */
	public void setText(String text) {
		this.text = text;
	}
	/**
	 * 客户号
	 * @return
	 */
	public String getCustomer() {
		return customer;
	}
	/**
	 * 客户号
	 * @param customer
	 */
	public void setCustomer(String customer) {
		this.customer = customer;
	}
	/**
	 * 客户文本
	 * @return
	 */
	public String getCustom_htext() {
		return custom_htext;
	}
	/**
	 * 客户文本
	 * @param custom_htext
	 */
	public void setCustom_htext(String custom_htext) {
		this.custom_htext = custom_htext;
	}
	/***
	 * 本位币相差的金额(汇损，调整金)
	 * @return
	 */
	public BigDecimal getSumAdjustamount() {
		return sumAdjustamount;
	}
	/***
	 * 本位币相差的金额(汇损，调整金)
	 * @param sumAdjustamount
	 */
	public void setSumAdjustamount(BigDecimal sumAdjustamount) {
		this.sumAdjustamount = sumAdjustamount;
	}
	
	/***
	 * 用来区别汇损（调整）和 其他凭证 (存立项号或合同号)
	 * @return
	 */
	public String getTaxCode() {
		return taxCode;
	}
	/****
	 * 用来区别汇损（调整）和 其他凭证(存立项号或合同号)
	 * @param taxCode
	 */
	public void setTaxCode(String taxCode) {
		this.taxCode = taxCode;
	}
	/***
	 * 
	 * @return
	 */
	public String getStrType() {
		return strType;
	}
	/***
	 * 
	 * @param strType
	 */
	public void setStrType(String strType) {
		this.strType = strType;
	}
	/***
	 * 行项目
	 * @return
	 */
	public String getRownumber() {
		return rownumber;
	}
	/***
	 * 行项目
	 * @param rownumber
	 */
	public void setRownumber(String rownumber) {
		this.rownumber = rownumber;
	}
	/**
	 * 本位币相差的金额
	 * @return
	 */
	public BigDecimal getSubtractVlaue() {
		return subtractVlaue;
	}
	/**
	 * 本位币相差的金额
	 * @param subtractVlaue
	 */
	public void setSubtractVlaue(BigDecimal subtractVlaue) {
		this.subtractVlaue = subtractVlaue;
	}
	/**
	 * 供应商
	 * @return
	 */
	public String getSupplier() {
		return supplier;
	}
	/**
	 * 供应商
	 * @param supplier
	 */
	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}
	/**
	 * 供应商文本
	 * @return
	 */
	public String getSupplier_htext() {
		return supplier_htext;
	}
	/**
	 * 供应商文本
	 * @param supplier_htext
	 */
	public void setSupplier_htext(String supplier_htext) {
		this.supplier_htext = supplier_htext;
	}
	/**记账码1
	 * @return the checkCode
	 */
	public String getCheckCode() {
		return checkCode;
	}
	/**记账码1
	 * @param checkCode the checkCode to set
	 */
	public void setCheckCode(String checkCode) {
		this.checkCode = checkCode;
	}
	/**记账码2
	 * @return the checkCode2
	 */
	public String getCheckCode2() {
		return checkCode2;
	}
	/**记账码2
	 * @param checkCode2 the checkCode2 to set
	 */
	public void setCheckCode2(String checkCode2) {
		this.checkCode2 = checkCode2;
	}
	/**借贷方标识
	 * @return the debitcredit
	 */
	public String getDebitcredit() {
		return debitcredit;
	}
	/**借贷方标识
	 * @param debitcredit the debitcredit to set
	 */
	public void setDebitcredit(String debitcredit) {
		this.debitcredit = debitcredit;
	}
	/**
	 * @return the voucherid
	 */
	public String getVoucherid() {
		return voucherid;
	}
	/**
	 * @param voucherid the voucherid to set
	 */
	public void setVoucherid(String voucherid) {
		this.voucherid = voucherid;
	}
	
	
}
