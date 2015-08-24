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
 * 客户供应商账龄分析
 * @author zhongzh
 *
 */
@Entity
@Table(name = "YAGEANALYSIS")
@org.hibernate.annotations.Entity(dynamicInsert = true, dynamicUpdate = true)
public class AgeAnalysis extends BaseObject{

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
	@Column(name = "AGEANALYSISID")
	private String ageanalysisId;
	/*
	 * --客户代码
	 */
	@Column(name = "CUSTOMERNO")
	@ValidateRule(dataType = 9, label = "客户代码", maxLength = 36, required = false)	
	private String  customerNo ;
	/*
	 * 客户名称
	 */
	@Column(name = "CUSTOMERNAME")
	@ValidateRule(dataType = 9, label = "客户名称", maxLength = 60, required = false)
	private String  customerName ;
	/*
	 * 客户类型1为客户，2为供应商
	 */
	@Column(name = "CUSTOMERTYPE")
	@ValidateRule(dataType = 9, label = "客户类型", maxLength = 1, required = false)
	private String customerType ;
	/*
	 * 公司代码
	 */
	@Column(name = "BUKRS")
	@ValidateRule(dataType = 9, label = "公司代码", maxLength = 10, required = false)
	private String  bukrs;
	/*
	 * 业务范围
	 */
	@Column(name = "GSBER")
	@ValidateRule(dataType = 9, label = "业务范围", maxLength = 20, required = false)
	private String  gsber;
	/*
	 * 分析日期
	 */
	@Column(name = "AUGDT")
	@ValidateRule(dataType = 8, label = "分析日期", required = false)
	private String  augdt;
	/*
	 * 分析项目
	 */
	@Column(name = "HKONT")
	@ValidateRule(dataType = 9, label = "分析项目", maxLength = 36, required = false)
	private String  hkont;
	/*
	 * 总账科目代码
	 */
	@Column(name = "SUBJECTCODE")
	@ValidateRule(dataType = 9, label = "总账科目代码", maxLength = 30, required = false)
	private String subjectCode ;
	/*
	 * --总账科目名称
	 */
	@Column(name = "SUBJECTNAME")
	@ValidateRule(dataType = 9, label = "总账科目名称", maxLength = 300, required = false)
	private String subjectName ;
	/*
	 * --1~3月
	 */
	@Column(name = "DATE1_3")
	@ValidateRule(dataType = 0, label = "1~3月", required = false)
	private BigDecimal date1_3 ;
	/*
	 * --4~6月
	 */
	@Column(name = "DATE4_6")
	@ValidateRule(dataType = 0, label = "4~6月", required = false)
	private BigDecimal date4_6 ;
	/*
	 * ,--7~12月
	 */
	@Column(name = "DATE7_12")
	@ValidateRule(dataType = 0, label = "7~12月", required = false)
	private BigDecimal date7_12 ;
	/*
	 * ,--1~2年
	 */
	@Column(name = "YEAR1_2")
	@ValidateRule(dataType = 0, label = "1~2年", required = false)
	private BigDecimal year1_2 ;
	/*
	 * --2~3年
	 */
	@Column(name = "YEAR2_3")
	@ValidateRule(dataType = 0, label = "2~3年", required = false)
	private BigDecimal year2_3 ;
	/*
	 *  --3~4年
	 */
	@Column(name = "YEAR3_4")
	@ValidateRule(dataType = 0, label = "3~4年", required = false)
	private BigDecimal year3_4 ;
	/*
	 * --4~5年
	 */
	@Column(name = "YEAR4_5")
	@ValidateRule(dataType = 0, label = "4~5年", required = false)
	private BigDecimal year4_5 ;
	/*
	 * --5年以上
	 */
	@Column(name = "YEAR5_ABOVE")
	@ValidateRule(dataType = 0, label = "5年以上", required = false)
	private BigDecimal year5_above ;
	/*
	 * --是否含逾期
	 */
	@Column(name = "ISEXCEED")
	@ValidateRule(dataType = 9, label = "是否含逾期", maxLength = 1, required = false)
	private String isExceed ;
	
	/*
     * --业务状态
     */
    @Column(name = "VBELTYPE")
    @ValidateRule(dataType = 9, label = "业务状态", required = false)
    private String vbeltype ;
	
    /*
     * --用户名
     */
    @Column(name = "USERNAME")
    @ValidateRule(dataType = 9, label = "用户名", required = false)
    private String username ;
    
    /**
	 * 合计
	 */
    private BigDecimal total;
    
    
	public AgeAnalysis(){
		this.customerNo=" ";
		this.customerName =" ";
		this.customerType =" ";
		this.bukrs=" ";
		this.gsber =" ";
		this.augdt ="0";
		this.hkont =" ";
		this.subjectCode =" ";
		this.subjectName =" ";
		this.date1_3=new BigDecimal("0");
		this.date4_6 =new BigDecimal("0");
		this.date7_12 =new BigDecimal("0");
		this.year1_2 =new BigDecimal("0");
		this.year2_3 =new BigDecimal("0");
		this.year3_4 =new BigDecimal("0");
		this.year4_5 =new BigDecimal("0");
		this.year5_above=new BigDecimal("0");
		this.total  =new BigDecimal("0");
		this.isExceed ="1";
		this.vbeltype = " ";
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
	 * @return the ageanalysisId
	 */
	public String getAgeanalysisId() {
		return ageanalysisId;
	}
	/**
	 * @param ageanalysisId the ageanalysisId to set
	 */
	public void setAgeanalysisId(String ageanalysisId) {
		this.ageanalysisId = ageanalysisId;
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
	 * 客户名称
	 * @return the customerName
	 */
	public String getCustomerName() {
		return customerName;
	}
	/**
	 * 客户名称
	 * @param customerName the customerName to set
	 */
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
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
	 * 取得公司代码
	 * @return the bukrs
	 */
	public String getBukrs() {
		return bukrs;
	}
	/**
	 * 设置公司代码
	 * @param bukrs the bukrs to set
	 */
	public void setBukrs(String bukrs) {
		this.bukrs = bukrs;
	}
	/**
	 * 业务范围
	 * @return the gsber
	 */
	public String getGsber() {
		return gsber;
	}
	/**
	 * 业务范围
	 * @param gsber the gsber to set
	 */
	public void setGsber(String gsber) {
		this.gsber = gsber;
	}
	/**
	 * 分析日期
	 * @return the augdt
	 */
	public String getAugdt() {
		return augdt;
	}
	/**分析日期
	 * @param augdt the augdt to set
	 */
	public void setAugdt(String augdt) {
		this.augdt = augdt;
	}
	/**
	 * 分析项目
	 * @return the hkont
	 */
	public String getHkont() {
		return hkont;
	}
	/**
	 * 分析项目
	 * @param hkont the hkont to set
	 */
	public void setHkont(String hkont) {
		this.hkont = hkont;
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
	 * 1~3月金额合计
	 * @return the date1_3
	 */
	public BigDecimal getDate1_3() {
		return date1_3;
	}
	/**
	 * 1~3月金额合计
	 * @param date1_3 the date1_3 to set
	 */
	public void setDate1_3(BigDecimal date1_3) {
		this.date1_3 = date1_3;
	}
	/**
	 * 4~6月金额合计
	 * @return the date4_6
	 */
	public BigDecimal getDate4_6() {
		return date4_6;
	}
	/**
	 * 4~6月金额合计
	 * @param date4_6 the date4_6 to set
	 */
	public void setDate4_6(BigDecimal date4_6) {
		this.date4_6 = date4_6;
	}
	/**7~12月金额合计
	 * @return the date7_12
	 */
	public BigDecimal getDate7_12() {
		return date7_12;
	}
	/**7~12月金额合计
	 * @param date7_12 the date7_12 to set
	 */
	public void setDate7_12(BigDecimal date7_12) {
		this.date7_12 = date7_12;
	}
	/**
	 * 1~2年金额合计
	 * @return the year1_2
	 */
	public BigDecimal getYear1_2() {
		return year1_2;
	}
	/**
	 * 1~2年金额合计
	 * @param year1_2 the year1_2 to set
	 */
	public void setYear1_2(BigDecimal year1_2) {
		this.year1_2 = year1_2;
	}
	/**
	 * 2~3年金额合计
	 * @return the year2_3
	 */
	public BigDecimal getYear2_3() {
		return year2_3;
	}
	/**
	 * 2~3年金额合计
	 * @param year2_3 the year2_3 to set
	 */
	public void setYear2_3(BigDecimal year2_3) {
		this.year2_3 = year2_3;
	}
	/**
	 * 3~4年金额合计
	 * @return the year3_4
	 */
	public BigDecimal getYear3_4() {
		return year3_4;
	}
	/**
	 * 3~4年金额合计
	 * @param year3_4 the year3_4 to set
	 */
	public void setYear3_4(BigDecimal year3_4) {
		this.year3_4 = year3_4;
	}
	/**
	 * 4~5年金额合计
	 * @return the year4_5
	 */
	public BigDecimal getYear4_5() {
		return year4_5;
	}
	/**
	 * 4~5年金额合计
	 * @param year4_5 the year4_5 to set
	 */
	public void setYear4_5(BigDecimal year4_5) {
		this.year4_5 = year4_5;
	}
	/**
	 * 5年以上金额合计
	 * @return the year5_above
	 */
	public BigDecimal getYear5_above() {
		return year5_above;
	}
	/**
	 * 5年以上金额合计
	 * @param year5_above the year5_above to set
	 */
	public void setYear5_above(BigDecimal year5_above) {
		this.year5_above = year5_above;
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
	
    public String getVbeltype(){
        return vbeltype;
    }
    
    public void setVbeltype(String vbeltype){
        this.vbeltype = vbeltype;
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
	
}
