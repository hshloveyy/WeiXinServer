package com.infolion.xdss3.ageAnalysis.domain;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.infolion.platform.dpframework.core.annotation.ValidateRule;
import com.infolion.platform.dpframework.core.domain.BaseObject;

/**
 * 未清明细
 * @author zhongzh
 *
 */
@Entity
@Table(name = "YUNCLEARDETAILED")
@org.hibernate.annotations.Entity(dynamicInsert = true, dynamicUpdate = true)
public class UnclearDetailed extends BaseObject{
	// fields
	/*
	 * 客户端
	 */
	@Column(name = "MANDT")
	private String client;

	/*
	 *ID
	 */
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid.hex")
	@Column(name = "UNCLEAR_DEAILED_ID")
	private String unclear_deailed_id;
	/*
	 * --客户代码
	 */
	@Column(name = "CUSTOMERNO")
	@ValidateRule(dataType = 9, label = "客户代码", maxLength = 36, required = false)	
	private String  customerNo ;
	/*
	 * --公司代码
	 */
	@Column(name = "COMPANYNO")
	@ValidateRule(dataType = 9, label = "公司代码", maxLength = 60, required = false)	
	private String  companyno ;
	/*
	 * --部门id
	 */
	@Column(name = "DEPTID")
	@ValidateRule(dataType = 9, label = "部门id", maxLength = 36, required = false)	
	private String  deptId ;
	/*
	 * --部门
	 */
	@Column(name = "DEPTNAME")
	@ValidateRule(dataType = 9, label = "部门", maxLength = 36, required = false)	
	private String  deptName ;
	/*
	 * --清帐状态,1为部分清，0为未清
	 */
	@Column(name = "ISCLEAR")
	@ValidateRule(dataType = 9, label = "清帐状态", maxLength = 1, required = false)	
	private String  isclear ;
	/*
	 *项目代码
	 */
	@Column(name = "PROJECTNO")
	@ValidateRule(dataType = 9, label = "项目代码", maxLength = 30, required = false)
	private String  projectNo ;
	/*
	 * 项目名称
	 */
	@Column(name = "PROJECTNAME")
	@ValidateRule(dataType = 9, label = "项目名称", maxLength = 300, required = false)
	private String  projectName;
	/*
	 * 总账科目代码
	 */
	@Column(name = "SUBJECTCODE")
	@ValidateRule(dataType = 9, label = "总账科目代码", maxLength = 30, required = false)
	private String  subjectCode ;
	/*
	 * --总账科目名称
	 */
	@Column(name = "SUBJECTNAME")
	@ValidateRule(dataType = 9, label = "总账科目名称", maxLength = 300, required = false)
	private String  subjectName ;
	/*
	 * 分析日期
	 */
	@Column(name = "ANALYSISDATE")
	@ValidateRule(dataType = 8, label = "分析日期", required = false)
	private String  analysisDate ;
	/*
	 * 记账日期
	 */
	@Column(name = "ACCOUNTDATE")
	@ValidateRule(dataType = 8, label = "记账日期", required = false)
	private String  accountDate ;
	/*
	 * 财务凭证号
	 */
	@Column(name = "VOUCHERNO")
	@ValidateRule(dataType = 9, label = "财务凭证号", maxLength = 36, required = false)
	private String  voucherNo ;
	/*
	 * 凭证金额
	 */
    @Column(name="VOUCHER_AMOUNT")
    @ValidateRule(dataType=0,label="凭证金额",required=false)  
	private BigDecimal  voucher_amount ;
    
    /*
	 * 凭证金额(本位币)
	 */
    @Column(name="VOUCHER_BWBJE")
    @ValidateRule(dataType=0,label="凭证金额(本位币)",required=false)  
	private BigDecimal  voucher_bwbje ;
    
	/*
	 * 文本
	 */
	@Column(name = "BKTXT")
	@ValidateRule(dataType = 9, label = "文本", maxLength = 500, required = false)
	private String  bktxt ;
	/*
	 * 已清金额
	 */
	@Column(name = "OFFAMOUNT")
	@ValidateRule(dataType = 0, label = "已清金额", required = false)
	private BigDecimal offAmount ;
	
	/*
	 * 已清金额（本位币）
	 */
	@Column(name = "OFFAMOUNT_BWBJE")
	@ValidateRule(dataType = 0, label = "已清金额(本位币)", required = false)
	private BigDecimal offAmountBwbje ;
	
	/*
	 *项目代码
	 */
	@Column(name = "PROJECTNO2")
	@ValidateRule(dataType = 9, label = "项目代码", maxLength = 30, required = false)
	private String  projectNo2 ;
	/*
	 * 项目名称
	 */
	@Column(name = "PROJECTNAME2")
	@ValidateRule(dataType = 9, label = "项目名称", maxLength = 300, required = false)
	private String  projectName2;
	
	/*
	 * 纸质合同号
	 */
	@Column(name = "OLD_CONTRACT_NO")
	@ValidateRule(dataType = 9, label = "纸质合同号", maxLength = 36, required = false)
	private String  old_contract_no ;
	/*
	 * 订单号
	 */
	@Column(name = "ORDERNO")
	@ValidateRule(dataType = 9, label = "订单号", maxLength = 60, required = false)
	private String  orderNo ;
	/*
	 * 业务状态
	 */
	@Column(name = "BUSINESSSTATE")
	@ValidateRule(dataType = 9, label = "业务状态", maxLength = 20, required = false)
	private String  businessstate ;
	/*
	 * 经济内容
	 */
	@Column(name = "CONTENT")
	@ValidateRule(dataType = 9, label = "经济内容", maxLength = 1000, required = false)
	private String  content ;
	/*
	 * 凭证货币
	 */
    @Column(name="VOUCHER_CURRENCY")
    @ValidateRule(dataType=9,label="凭证货币",maxLength= 20,required=false)  
	private String  voucher_currency ;
	/*
	 * --1~3月外币
	 */
	@Column(name = "WBJE1_3_DATE")
	@ValidateRule(dataType = 0, label = "1~3月外币", required = false)
	private BigDecimal wbje1_3_date ;
	/*
	 * --1~3月本币
	 */
	@Column(name = "BWBJE1_3_DATE")
	@ValidateRule(dataType = 0, label = "1~3月本币", required = false)
	private BigDecimal bwbje1_3_date ;
	/*
	 * --4~6月外币
	 */
	@Column(name = "WBJE4_6_DATE")
	@ValidateRule(dataType = 0, label = "4~6月外币", required = false)
	private BigDecimal wbje4_6_date ;
	/*
	 * --4~6月本币
	 */
	@Column(name = "BWBJE4_6_DATE")
	@ValidateRule(dataType = 0, label = "4~6月本币", required = false)
	private BigDecimal bwbje4_6_date ;
	/*
	 * ,--7~12月外币
	 */
	@Column(name = "WBJE7_12_DATE")
	@ValidateRule(dataType = 0, label = "7~12月外币", required = false)
	private BigDecimal wbje7_12_date ;
	/*
	 * ,--7~12月本币
	 */
	@Column(name = "BWBJE7_12_DATE")
	@ValidateRule(dataType = 0, label = "7~12月本币", required = false)
	private BigDecimal bwbje7_12_date ;
	/*
	 * ,--1~2年外币
	 */
	@Column(name = "WBJE1_2_YEAR")
	@ValidateRule(dataType = 0, label = "1~2年外币", required = false)
	private BigDecimal wbje1_2_year ;
	/*
	 * ,--1~2年本币
	 */
	@Column(name = "BWBJE1_2_YEAR")
	@ValidateRule(dataType = 0, label = "1~2年本币", required = false)
	private BigDecimal bwbje1_2_year ;
	/*
	 * --2~3年外币
	 */
	@Column(name = "WBJE2_3_YEAR")
	@ValidateRule(dataType = 0, label = "2~3年外币", required = false)
	private BigDecimal wbje2_3_year ;
	/*
	 * --2~3年本币
	 */
	@Column(name = "BWBJE2_3_YEAR")
	@ValidateRule(dataType = 0, label = "2~3年本币", required = false)
	private BigDecimal bwbje2_3_year ;
	/*
	 *  --3~4年外币
	 */
	@Column(name = "WBJE3_4_YEAR")
	@ValidateRule(dataType = 0, label = "3~4年外币", required = false)
	private BigDecimal wbje3_4_year ;
	/*
	 *  --3~4年本币
	 */
	@Column(name = "BWBJE3_4_YEAR")
	@ValidateRule(dataType = 0, label = "3~4年本币", required = false)
	private BigDecimal bwbje3_4_year;
	/*
	 * --4~5年外币
	 */
	@Column(name = "WBJE4_5_YEAR")
	@ValidateRule(dataType = 0, label = "4~5年外币", required = false)
	private BigDecimal wbje4_5_year ;
	/*
	 * --4~5年本币
	 */
	@Column(name = "BWBJE4_5_YEAR")
	@ValidateRule(dataType = 0, label = "4~5年本币", required = false)
	private BigDecimal bwbje4_5_year ;
	/*
	 * --5年以上外币
	 */
	@Column(name = "WBJE5_YEAR_ABOVE")
	@ValidateRule(dataType = 0, label = "5年以上外币", required = false)
	private BigDecimal wbje5_year_above ;
	/*
	 * --5年以上本币
	 */
	@Column(name = "BWBJE5_YEAR_ABOVE")
	@ValidateRule(dataType = 0, label = "5年以上本币", required = false)
	private BigDecimal bwbje5_year_above ;
	/*
	 * --到期日
	 */
	@Column(name = "EXPIRES_DATE")
	@ValidateRule(dataType = 9, label = "到期日", maxLength = 30, required = false)
	private String  expires_date ;
	/*
	 * --是否含逾期
	 */
	@Column(name = "ISEXCEED")
	@ValidateRule(dataType = 9, label = "是否含逾期", maxLength = 1, required = false)
	private String  isExceed ;
	/*
	 * --逾期时间
	 */
	@Column(name = "EXCEED_TIME")
	@ValidateRule(dataType = 9, label = "逾期时间", maxLength = 30, required = false)
	private String  exceed_time ;
	
	/*
	 * 会计年度
	 */
	 @Column(name="GJAHR")
	 @ValidateRule(dataType=9,label="会计年度",maxLength= 20,required=false)
	private String gjahr;
	/*
	 * 会计凭证中的行项目数
	 */
	 @Column(name="BUZEI")
	 @ValidateRule(dataType=9,label="会计凭证中的行项目数",maxLength= 20,required=false)
	private String buzei;
	/*
	 * 合同号
	 */
	 @Column(name="IHREZ")
	 @ValidateRule(dataType=9,label="合同号",maxLength= 20,required=false)
	private String ihrez;
	/*
	 * 业务处理类型
	 */
	 @Column(name="VBELTYPE")
	 @ValidateRule(dataType=9,label="业务处理类型",maxLength= 20,required=false)
	private String vbeltype;
	/*
	 * 订单类型（采购）
	 */
	 @Column(name="BSART")
	 @ValidateRule(dataType=9,label="订单类型采购",maxLength= 20,required=false)
	private String bsart;
	/*
	 * 物料号
	 */
	 @Column(name="MATNR")
	 @ValidateRule(dataType=9,label="物料号",maxLength= 20,required=false)
	private String matnr;
	 /*
     * --用户名
     */
    @Column(name = "USERNAME")
    @ValidateRule(dataType = 9, label = "用户名", required = false)
    private String username ;
	/**
	 * 合计外币小计
	 */
    private BigDecimal total;
    /**
	 * 合计本位币小计
	 */
    private BigDecimal total2;
    
	public UnclearDetailed(){
		this.customerNo =" ";
		this.deptId  =" ";
		this.deptName =" ";
		this.isclear  =" ";
		this.projectNo =" ";
		this.projectName  =" ";
		this.subjectCode  =" ";
		this.subjectName  =" ";
		this.analysisDate  =" ";
		this.accountDate  =" ";
		this.voucherNo  =" ";
		this.bktxt =" ";
		this.voucher_amount =new BigDecimal("0");
		this.voucher_bwbje = new BigDecimal("0");
		this.offAmount =new BigDecimal("0");
		this.offAmountBwbje  =new BigDecimal("0");
		this.old_contract_no =" ";
		this.orderNo  =" ";
		this.businessstate  =" ";
		this.content =" ";
		this.voucher_currency =" ";
		this.projectNo2 =" ";
		this.projectName2 = " ";
		this.wbje1_3_date  =new BigDecimal("0");
		this.bwbje1_3_date  =new BigDecimal("0");
		this.wbje4_6_date  =new BigDecimal("0");
		this.bwbje4_6_date  =new BigDecimal("0");
		this.wbje7_12_date  =new BigDecimal("0");
		this.bwbje7_12_date  =new BigDecimal("0");
		this.wbje1_2_year =new BigDecimal("0");
		this.bwbje1_2_year  =new BigDecimal("0");
		this.wbje2_3_year  =new BigDecimal("0");
		this.bwbje2_3_year  =new BigDecimal("0");
		this.wbje3_4_year  =new BigDecimal("0");
		this.bwbje3_4_year  =new BigDecimal("0");
		this.wbje4_5_year  =new BigDecimal("0");
		this.bwbje4_5_year  =new BigDecimal("0");
		this.wbje5_year_above  =new BigDecimal("0");
		this.bwbje5_year_above  =new BigDecimal("0");
		this.total  =new BigDecimal("0");
		this.total2  =new BigDecimal("0");
		this.expires_date  =" ";
		this.isExceed  ="0";
		this.companyno  =" ";		
		this.bsart  =" ";
		this.buzei  =" ";
		this.gjahr  =" ";
		this.ihrez  =" ";
		this.matnr  =" ";
		this.vbeltype  =" ";
	}
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
	 * @return the unclear_deailed_id
	 */
	public String getUnclear_deailed_id() {
		return unclear_deailed_id;
	}
	/**
	 * @param unclear_deailed_id the unclear_deailed_id to set
	 */
	public void setUnclear_deailed_id(String unclear_deailed_id) {
		this.unclear_deailed_id = unclear_deailed_id;
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
	 * 部门id
	 * @return the deptId
	 */
	public String getDeptId() {
		return deptId;
	}
	/**
	 * 部门id
	 * @param deptId the deptId to set
	 */
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	/**
	 * 部门
	 * @return the deptName
	 */
	public String getDeptName() {
		return deptName;
	}
	/**
	 * 部门
	 * @param deptName the deptName to set
	 */
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	/**
	 * 清帐状态 1为部分清，0为未清
	 * @return the isclear
	 */
	public String getIsclear() {
		return isclear;
	}
	/**
	 * 清帐状态 1为部分清，0为未清
	 * @param isclear the isclear to set
	 */
	public void setIsclear(String isclear) {
		this.isclear = isclear;
	}
	/**
	 * 项目代码
	 * @return the projectNo
	 */
	public String getProjectNo() {
		return projectNo;
	}
	/**
	 * 项目代码
	 * @param projectNo the projectNo to set
	 */
	public void setProjectNo(String projectNo) {
		this.projectNo = projectNo;
	}
	/**
	 * 项目名称
	 * @return the projectName
	 */
	public String getProjectName() {
		return projectName;
	}
	/**
	 * 项目名称
	 * @param projectName the projectName to set
	 */
	public void setProjectName(String projectName) {
		this.projectName = projectName;
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
	/**
	 * 总账科目名称
	 * @return the subjectName
	 */
	public String getSubjectName() {
		return subjectName;
	}
	/**
	 * 总账科目名称
	 * @param subjectName the subjectName to set
	 */
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
	/**
	 * 分析日期
	 * @return the analysisDate
	 */
	public String getAnalysisDate() {
		return analysisDate;
	}
	/**
	 * 分析日期
	 * @param analysisDate the analysisDate to set
	 */
	public void setAnalysisDate(String analysisDate) {
		this.analysisDate = analysisDate;
	}
	/**
	 * 记账日期
	 * @return the accountDate
	 */
	public String getAccountDate() {
		return accountDate;
	}
	/**
	 * 记账日期
	 * @param accountDate the accountDate to set
	 */
	public void setAccountDate(String accountDate) {
		this.accountDate = accountDate;
	}
	/**
	 * 财务凭证号
	 * @return the voucherNo
	 */
	public String getVoucherNo() {
		return voucherNo;
	}
	/**
	 * 财务凭证号
	 * @param voucherNo the voucherNo to set
	 */
	public void setVoucherNo(String voucherNo) {
		this.voucherNo = voucherNo;
	}
	/**
	 * 文本
	 * @return the bktxt
	 */
	public String getBktxt() {
		return bktxt;
	}
	/**
	 * 文本
	 * @param bktxt the bktxt to set
	 */
	public void setBktxt(String bktxt) {
		this.bktxt = bktxt;
	}
	/**
	 * 已清金额
	 * @return the offAmount
	 */
	public BigDecimal getOffAmount() {
		return offAmount;
	}
	/**
	 * 已清金额
	 * @param offAmount the offAmount to set
	 */
	public void setOffAmount(BigDecimal offAmount) {
		this.offAmount = offAmount;
	}
	/**
	 * 纸质合同号
	 * @return the old_contract_no
	 */
	public String getOld_contract_no() {
		return old_contract_no;
	}
	/**
	 * 纸质合同号
	 * @param old_contract_no the old_contract_no to set
	 */
	public void setOld_contract_no(String old_contract_no) {
		this.old_contract_no = old_contract_no;
	}
	/**
	 * 订单号
	 * @return the orderNo
	 */
	public String getOrderNo() {
		return orderNo;
	}
	/**
	 * 订单号
	 * @param orderNo the orderNo to set
	 */
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	/**
	 * 业务状态
	 * @return the businessstate
	 */
	public String getBusinessstate() {
		return businessstate;
	}
	/**
	 * 业务状态
	 * @param businessstate the businessstate to set
	 */
	public void setBusinessstate(String businessstate) {
		this.businessstate = businessstate;
	}
	/**
	 * 经济内容
	 * @return the content
	 */
	public String getContent() {
		return content;
	}
	/**
	 * 经济内容
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * 凭证货币
	 * @return the voucher_currency
	 */
	public String getVoucher_currency() {
		return voucher_currency;
	}
	/**
	 * 凭证货币
	 * @param voucher_currency the voucher_currency to set
	 */
	public void setVoucher_currency(String voucher_currency) {
		this.voucher_currency = voucher_currency;
	}
	/**
	 * 1~3月外币
	 * @return the wbje1_3_date
	 */
	public BigDecimal getWbje1_3_date() {
		return wbje1_3_date;
	}
	/**
	 * 1~3月外币
	 * @param wbje1_3_date the wbje1_3_date to set
	 */
	public void setWbje1_3_date(BigDecimal wbje1_3_date) {
		this.wbje1_3_date = wbje1_3_date;
	}
	/**
	 * 1~3月本币
	 * @return the bwbje1_3_date
	 */
	public BigDecimal getBwbje1_3_date() {
		return bwbje1_3_date;
	}
	/**
	 * 1~3月本币
	 * @param bwbje1_3_date the bwbje1_3_date to set
	 */
	public void setBwbje1_3_date(BigDecimal bwbje1_3_date) {
		this.bwbje1_3_date = bwbje1_3_date;
	}
	/**
	 * 4~6月外币
	 * @return the wbje4_6_date
	 */
	public BigDecimal getWbje4_6_date() {
		return wbje4_6_date;
	}
	/**
	 * 4~6月外币
	 * @param wbje4_6_date the wbje4_6_date to set
	 */
	public void setWbje4_6_date(BigDecimal wbje4_6_date) {
		this.wbje4_6_date = wbje4_6_date;
	}
	/**
	 * 4~6月本币
	 * @return the bwbje4_6_date
	 */
	public BigDecimal getBwbje4_6_date() {
		return bwbje4_6_date;
	}
	/**
	 * 4~6月本币
	 * @param bwbje4_6_date the bwbje4_6_date to set
	 */
	public void setBwbje4_6_date(BigDecimal bwbje4_6_date) {
		this.bwbje4_6_date = bwbje4_6_date;
	}
	/**
	 * 7~12月外币
	 * @return the wbje7_12_date
	 */
	public BigDecimal getWbje7_12_date() {
		return wbje7_12_date;
	}
	/**
	 * 7~12月外币
	 * @param wbje7_12_date the wbje7_12_date to set
	 */
	public void setWbje7_12_date(BigDecimal wbje7_12_date) {
		this.wbje7_12_date = wbje7_12_date;
	}
	/**
	 * 7~12月本币
	 * @return the bwbje7_12_date
	 */
	public BigDecimal getBwbje7_12_date() {
		return bwbje7_12_date;
	}
	/**
	 * 7~12月本币
	 * @param bwbje7_12_date the bwbje7_12_date to set
	 */
	public void setBwbje7_12_date(BigDecimal bwbje7_12_date) {
		this.bwbje7_12_date = bwbje7_12_date;
	}
	/**
	 * 1~2年外币
	 * @return the wbje1_2_year
	 */
	public BigDecimal getWbje1_2_year() {
		return wbje1_2_year;
	}
	/**
	 * 1~2年外币
	 * @param wbje1_2_year the wbje1_2_year to set
	 */
	public void setWbje1_2_year(BigDecimal wbje1_2_year) {
		this.wbje1_2_year = wbje1_2_year;
	}
	/**
	 * 1~2年本币
	 * @return the bwbje1_2_year
	 */
	public BigDecimal getBwbje1_2_year() {
		return bwbje1_2_year;
	}
	/**
	 * 1~2年本币
	 * @param bwbje1_2_year the bwbje1_2_year to set
	 */
	public void setBwbje1_2_year(BigDecimal bwbje1_2_year) {
		this.bwbje1_2_year = bwbje1_2_year;
	}
	/**
	 * "2~3年外币
	 * @return the wbje2_3_year
	 */
	public BigDecimal getWbje2_3_year() {
		return wbje2_3_year;
	}
	/**
	 * "2~3年外币
	 * @param wbje2_3_year the wbje2_3_year to set
	 */
	public void setWbje2_3_year(BigDecimal wbje2_3_year) {
		this.wbje2_3_year = wbje2_3_year;
	}
	/**
	 * 2~3年本币
	 * @return the bwbje2_3_year
	 */
	public BigDecimal getBwbje2_3_year() {
		return bwbje2_3_year;
	}
	/**
	 * 2~3年本币
	 * @param bwbje2_3_year the bwbje2_3_year to set
	 */
	public void setBwbje2_3_year(BigDecimal bwbje2_3_year) {
		this.bwbje2_3_year = bwbje2_3_year;
	}
	/**
	 * 3~4年外币
	 * @return the wbje3_4_year
	 */
	public BigDecimal getWbje3_4_year() {
		return wbje3_4_year;
	}
	/**
	 * 3~4年外币
	 * @param wbje3_4_year the wbje3_4_year to set
	 */
	public void setWbje3_4_year(BigDecimal wbje3_4_year) {
		this.wbje3_4_year = wbje3_4_year;
	}
	/**
	 * 3~4年本币
	 * @return the bwbje3_4_year
	 */
	public BigDecimal getBwbje3_4_year() {
		return bwbje3_4_year;
	}
	/**
	 * 3~4年本币
	 * @param bwbje3_4_year the bwbje3_4_year to set
	 */
	public void setBwbje3_4_year(BigDecimal bwbje3_4_year) {
		this.bwbje3_4_year = bwbje3_4_year;
	}
	/**
	 * 4~5年外币
	 * @return the wbje4_5_year
	 */
	public BigDecimal getWbje4_5_year() {
		return wbje4_5_year;
	}
	/**
	 * 4~5年外币
	 * @param wbje4_5_year the wbje4_5_year to set
	 */
	public void setWbje4_5_year(BigDecimal wbje4_5_year) {
		this.wbje4_5_year = wbje4_5_year;
	}
	/**
	 * 4~5年本币
	 * @return the bwbje4_5_year
	 */
	public BigDecimal getBwbje4_5_year() {
		return bwbje4_5_year;
	}
	/**
	 * 4~5年本币
	 * @param bwbje4_5_year the bwbje4_5_year to set
	 */
	public void setBwbje4_5_year(BigDecimal bwbje4_5_year) {
		this.bwbje4_5_year = bwbje4_5_year;
	}
	/**
	 * 5年以上外币
	 * @return the wbje5_year_above
	 */
	public BigDecimal getWbje5_year_above() {
		return wbje5_year_above;
	}
	/**
	 * 5年以上外币
	 * @param wbje5_year_above the wbje5_year_above to set
	 */
	public void setWbje5_year_above(BigDecimal wbje5_year_above) {
		this.wbje5_year_above = wbje5_year_above;
	}
	/**
	 * 5年以上本币
	 * @return the bwbje5_year_above
	 */
	public BigDecimal getBwbje5_year_above() {
		return bwbje5_year_above;
	}
	/**
	 * 5年以上本币
	 * @param bwbje5_year_above the bwbje5_year_above to set
	 */
	public void setBwbje5_year_above(BigDecimal bwbje5_year_above) {
		this.bwbje5_year_above = bwbje5_year_above;
	}
	/**
	 * 到期日
	 * @return the expires_date
	 */
	public String getExpires_date() {
		return expires_date;
	}
	/**
	 * 到期日
	 * @param expires_date the expires_date to set
	 */
	public void setExpires_date(String expires_date) {
		this.expires_date = expires_date;
	}
	/**
	 * 是否含逾期
	 * @return the isExceed
	 */
	public String getIsExceed() {
		return isExceed;
	}
	/**
	 * 是否含逾期
	 * @param isExceed the isExceed to set
	 */
	public void setIsExceed(String isExceed) {
		this.isExceed = isExceed;
	}
	/**
	 * 逾期时间
	 * @return the exceed_time
	 */
	public String getExceed_time() {
		return exceed_time;
	}
	/**
	 * 逾期时间
	 * @param exceed_time the exceed_time to set
	 */
	public void setExceed_time(String exceed_time) {
		this.exceed_time = exceed_time;
	}
	
	/**
	 * 凭证金额
	 * @param voucher_amount the voucher_amount to set
	 */	
	public void setVoucher_amount(BigDecimal voucher_amount) {
		this.voucher_amount = voucher_amount;
	}
	/**
	 * 凭证金额(本位币)
	 * @return the voucher_bwbje
	 */
	public void setVoucher_bwbje(BigDecimal voucher_bwbje) {
		this.voucher_bwbje = voucher_bwbje;
	}
	/**
	 * 凭证金额
	 * @return the voucher_amount
	 */
	public BigDecimal getVoucher_amount() {
		return voucher_amount;
	}
	/**
	 * 凭证金额(本位币)
	 * @return the voucher_bwbje
	 */
	public BigDecimal getVoucher_bwbje() {
		return voucher_bwbje;
	}	
	
	
	/**
	 * 已清金额（本位币）
	 * @return the offAmountBwbje
	 */
	public BigDecimal getOffAmountBwbje() {
		return offAmountBwbje;
	}
	/**
	 * 已清金额（本位币）
	 * @param offAmountBwbje the offAmountBwbje to set
	 */
	public void setOffAmountBwbje(BigDecimal offAmountBwbje) {
		this.offAmountBwbje = offAmountBwbje;
	}
	/**
	 * 项目号
	 * @return the projectNo2
	 */
	public String getProjectNo2() {
		return projectNo2;
	}
	/**
	 * 项目号
	 * @param projectNo2 the projectNo2 to set
	 */
	public void setProjectNo2(String projectNo2) {
		this.projectNo2 = projectNo2;
	}
	/**
	 * 项目名称
	 * @return the projectName2
	 */
	public String getProjectName2() {
		return projectName2;
	}
	/**
	 * 项目名称
	 * @param projectName2 the projectName2 to set
	 */
	public void setProjectName2(String projectName2) {
		this.projectName2 = projectName2;
	}
	/**
	 * 公司代码
	 * @return
	 */
	public String getCompanyno() {
		return companyno;
	}
	/**
	 * 公司代码
	 * @param companyno
	 */
	public void setCompanyno(String companyno) {
		this.companyno = companyno;
	}
	public String getGjahr() {
		return gjahr;
	}
	public void setGjahr(String gjahr) {
		this.gjahr = gjahr;
	}
	public String getBuzei() {
		return buzei;
	}
	public void setBuzei(String buzei) {
		this.buzei = buzei;
	}
	public String getIhrez() {
		return ihrez;
	}
	public void setIhrez(String ihrez) {
		this.ihrez = ihrez;
	}
	public String getVbeltype() {
		return vbeltype;
	}
	public void setVbeltype(String vbeltype) {
		this.vbeltype = vbeltype;
	}
	public String getBsart() {
		return bsart;
	}
	public void setBsart(String bsart) {
		this.bsart = bsart;
	}
	public String getMatnr() {
		return matnr;
	}
	public void setMatnr(String matnr) {
		this.matnr = matnr;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public BigDecimal getTotal() {
		return total;
	}
	public void setTotal(BigDecimal total) {
		this.total = total;
	}
	public BigDecimal getTotal2() {
		return total2;
	}
	public void setTotal2(BigDecimal total2) {
		this.total2 = total2;
	}
	
	
	
}
