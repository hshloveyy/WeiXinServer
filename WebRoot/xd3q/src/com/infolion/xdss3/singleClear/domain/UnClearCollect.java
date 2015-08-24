/*
 * @(#)UnClearCollect.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年07月17日 11点12分32秒
 *  描　述：创建
 */
package com.infolion.xdss3.singleClear.domain;

import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.infolion.platform.dpframework.core.annotation.ValidateRule;
import com.infolion.platform.dpframework.core.domain.BaseObject;

/**
 * <pre>
 * 未清收款(贷方)(UnClearCollect)实体类
 * </pre>
 * 
 * <br>
 * JDK版本:1.5
 * 
 * @author java业务平台代码生成工具
 * @version 1.0
 * @see The author for more details
 * @since 1.0
 */
@Entity
@Table(name = "YUNCLEARCOLLECT")
@org.hibernate.annotations.Entity(dynamicInsert = true, dynamicUpdate = true)
public class UnClearCollect extends BaseObject
{
	// fields
	/*
	 * 客户端
	 */
	@Column(name = "MANDT")
	private String client;

	/*
	 * 未清收款ID
	 */
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid.hex")
	@Column(name = "UNCLEARCOLLECTID")
	private String unclearcollectid;

	/*
	 * 未清客户数据抬头ID
	 */
	@Column(name = "CUSTOMERTITLEID")
	@ValidateRule(dataType = 9, label = "未清客户数据抬头ID", maxLength = 36, required = false)
	private String customertitleid;

	/*
	 * 立项号
	 */
	@Column(name = "PROJECT_NO")
	@ValidateRule(dataType = 9, label = "立项号", maxLength = 100, required = false)
	private String project_no;

	/*
	 * 财务凭证号
	 */
	@Column(name = "VOUCHERNO")
	@ValidateRule(dataType = 9, label = "财务凭证号", maxLength = 30, required = false)
	private String voucherno;

	/*
	 * 记账日期
	 */
	@Column(name = "ACCOUNTDATE")
	@ValidateRule(dataType = 9, label = "记账日期", maxLength = 20, required = false)
	private String accountdate;

	/*
	 * 收款单ID
	 */
	@Column(name = "COLLECTID")
	@ValidateRule(dataType = 9, label = "收款单ID", maxLength = 36, required = false)
	private String collectid;

	/*
	 * 收款单号
	 */
	@Column(name = "COLLECTNO")
	@ValidateRule(dataType = 9, label = "收款单号", maxLength = 50, required = false)
	private String collectno;

	/*
	 * 收款金额分配ID
	 */
	@Column(name = "COLLECTITEMID")
	@ValidateRule(dataType = 9, label = "收款金额分配ID", maxLength = 36, required = false)
	private String collectitemid;

	/*
	 * 原合同号
	 */
	@Column(name = "OLD_CONTRACT_NO")
	@ValidateRule(dataType = 9, label = "原合同号", maxLength = 36, required = false)
	private String old_contract_no;

	/*
	 * 货币
	 */
	@Column(name = "CURRENCY")
	@ValidateRule(dataType = 9, label = "货币", maxLength = 5, required = false)
	private String currency;

	/*
	 * 押汇汇率
	 */
	@Column(name = "EXCHANGERATE")
	@ValidateRule(dataType = 0, label = "押汇汇率", required = false)
	private BigDecimal exchangerate;

	/*
	 * 已抵消收款
	 */
	@Column(name = "OFFSETAMOUNT")
	@ValidateRule(dataType = 0, label = "已抵消收款", required = false)
	private BigDecimal offsetamount;

	/*
	 * 未抵消收款
	 */
	@Column(name = "UNOFFSETAMOUNT")
	@ValidateRule(dataType = 0, label = "未抵消收款", required = false)
	private BigDecimal unoffsetamount;

	/*
	 * 本次已抵消收款
	 */
	@Column(name = "NOWCLEARAMOUNT")
	@ValidateRule(dataType = 0, label = "本次已抵消收款", required = false)
	private BigDecimal nowclearamount;

	/*
	 * 金额
	 */
	@Column(name = "AMOUNT")
	@ValidateRule(dataType = 0, label = "金额", required = false)
	private BigDecimal amount;

	/*
	 * 合同编号
	 */
	@Column(name = "CONTRACT_NO")
	@ValidateRule(dataType = 9, label = "合同编号", maxLength = 50, required = false)
	private String contract_no;

	/*
	 * 在批抵消收款
	 */
	@Column(name = "ONROADAMOUNT")
	@ValidateRule(dataType = 0, label = "在批抵消收款", required = false)
	private BigDecimal onroadamount;
	
	/*
	 * 本位币金额
	 */
	@Column(name = "BWBJE")
	@ValidateRule(dataType = 0, label = "本位币金额", required = false)
	private BigDecimal bwbje;
	
	/*
	 * 未清本位币金额
	 */
	@Column(name = "UNBWBJE")
	@ValidateRule(dataType = 0, label = "未清本位币金额", required = false)
	private BigDecimal unbwbje;
	
	/*
	 * 保证金
	 */
	@Column(name = "SURETYBOND")
	@ValidateRule(dataType = 0, label = "保证金", required = false)
	private BigDecimal suretybond;

	/*
	 * 凭证抬头文本
	 */
	@Column(name = "BKTXT")
	@ValidateRule(dataType = 9, label = "凭证抬头文本", required = false)
	private String bktxt;
	
	/*
	 * 客户单清帐
	 */
	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
	@JoinColumn(name = "CUSTOMSCLEARID")
	@NotFound(action = NotFoundAction.IGNORE)
	private CustomSingleClear customSingleClear;

	/*
	 * 本次已清金额
	 */
	@Transient
	private BigDecimal clearedamount;

	/*
	 * 本次已清金额
	 */
	public BigDecimal getClearedamount()
	{
		return clearedamount;
	}

	/*
	 * 本次已清金额
	 */
	public void setClearedamount(BigDecimal clearedamount)
	{
		this.clearedamount = clearedamount;
	}

	/**
	 * 功能描述: 获得客户端
	 * 
	 * @return 客户端 : String
	 */
	public String getClient()
	{
		return this.client;
	}

	/**
	 * 功能描述: 设置客户端
	 * 
	 * @param client
	 *            : String
	 */
	public void setClient(String client)
	{
		this.client = client;
	}

	/**
	 * 功能描述: 获得未清收款ID
	 * 
	 * @return 未清收款ID : String
	 */
	public String getUnclearcollectid()
	{
		return this.unclearcollectid;
	}

	/**
	 * 功能描述: 设置未清收款ID
	 * 
	 * @param unclearcollectid
	 *            : String
	 */
	public void setUnclearcollectid(String unclearcollectid)
	{
		this.unclearcollectid = unclearcollectid;
	}

	/**
	 * 功能描述: 获得立项号
	 * 
	 * @return 立项号 : String
	 */
	public String getProject_no()
	{
		return this.project_no;
	}

	/**
	 * 功能描述: 设置立项号
	 * 
	 * @param project_no
	 *            : String
	 */
	public void setProject_no(String project_no)
	{
		this.project_no = project_no;
	}

	/**
	 * 功能描述: 获得财务凭证号
	 * 
	 * @return 财务凭证号 : String
	 */
	public String getVoucherno()
	{
		return this.voucherno;
	}

	/**
	 * 功能描述: 设置财务凭证号
	 * 
	 * @param voucherno
	 *            : String
	 */
	public void setVoucherno(String voucherno)
	{
		this.voucherno = voucherno;
	}

	/**
	 * 功能描述: 获得记账日期
	 * 
	 * @return 记账日期 : String
	 */
	public String getAccountdate()
	{
		return this.accountdate;
	}

	/**
	 * 功能描述: 设置记账日期
	 * 
	 * @param accountdate
	 *            : String
	 */
	public void setAccountdate(String accountdate)
	{
		this.accountdate = accountdate;
	}

	/**
	 * 功能描述: 获得收款单号
	 * 
	 * @return 收款单号 : String
	 */
	public String getCollectno()
	{
		return this.collectno;
	}

	/**
	 * 功能描述: 设置收款单号
	 * 
	 * @param collectno
	 *            : String
	 */
	public void setCollectno(String collectno)
	{
		this.collectno = collectno;
	}

	/**
	 * 功能描述: 获得收款金额分配ID
	 * 
	 * @return 收款金额分配ID : String
	 */
	public String getCollectitemid()
	{
		return this.collectitemid;
	}

	/**
	 * 功能描述: 设置收款金额分配ID
	 * 
	 * @param collectitemid
	 *            : String
	 */
	public void setCollectitemid(String collectitemid)
	{
		this.collectitemid = collectitemid;
	}

	/**
	 * 功能描述: 获得原合同号
	 * 
	 * @return 原合同号 : String
	 */
	public String getOld_contract_no()
	{
		return this.old_contract_no;
	}

	/**
	 * 功能描述: 设置原合同号
	 * 
	 * @param old_contract_no
	 *            : String
	 */
	public void setOld_contract_no(String old_contract_no)
	{
		this.old_contract_no = old_contract_no;
	}

	/**
	 * 功能描述: 获得货币
	 * 
	 * @return 货币 : String
	 */
	public String getCurrency()
	{
		return this.currency;
	}

	/**
	 * 功能描述: 设置货币
	 * 
	 * @param currency
	 *            : String
	 */
	public void setCurrency(String currency)
	{
		this.currency = currency;
	}

	/**
	 * 功能描述: 获得押汇汇率
	 * 
	 * @return 押汇汇率 : BigDecimal
	 */
	public BigDecimal getExchangerate()
	{
		return this.exchangerate;
	}

	/**
	 * 功能描述: 设置押汇汇率
	 * 
	 * @param exchangerate
	 *            : BigDecimal
	 */
	public void setExchangerate(BigDecimal exchangerate)
	{
		this.exchangerate = exchangerate;
	}

	/**
	 * 功能描述: 获得已抵消收款
	 * 
	 * @return 已抵消收款 : BigDecimal
	 */
	public BigDecimal getOffsetamount()
	{
		return this.offsetamount;
	}

	/**
	 * 功能描述: 设置已抵消收款
	 * 
	 * @param offsetamount
	 *            : BigDecimal
	 */
	public void setOffsetamount(BigDecimal offsetamount)
	{
		this.offsetamount = offsetamount;
	}

	/**
	 * 功能描述: 获得未抵消收款
	 * 
	 * @return 未抵消收款 : BigDecimal
	 */
	public BigDecimal getUnoffsetamount()
	{
		return this.unoffsetamount;
	}

	/**
	 * 功能描述: 设置未抵消收款
	 * 
	 * @param unoffsetamount
	 *            : BigDecimal
	 */
	public void setUnoffsetamount(BigDecimal unoffsetamount)
	{
		this.unoffsetamount = unoffsetamount;
	}

	/**
	 * 功能描述: 获得本次已抵消收款
	 * 
	 * @return 本次已抵消收款 : BigDecimal
	 */
	public BigDecimal getNowclearamount()
	{
		return this.nowclearamount;
	}

	/**
	 * 功能描述: 设置本次已抵消收款
	 * 
	 * @param nowclearamount
	 *            : BigDecimal
	 */
	public void setNowclearamount(BigDecimal nowclearamount)
	{
		this.nowclearamount = nowclearamount;
	}

	/**
	 * 功能描述: 获得金额
	 * 
	 * @return 金额 : BigDecimal
	 */
	public BigDecimal getAmount()
	{
		return this.amount;
	}

	/**
	 * 功能描述: 设置金额
	 * 
	 * @param amount
	 *            : BigDecimal
	 */
	public void setAmount(BigDecimal amount)
	{
		this.amount = amount;
	}

	/**
	 * 功能描述: 获得合同编号
	 * 
	 * @return 合同编号 : String
	 */
	public String getContract_no()
	{
		return this.contract_no;
	}

	/**
	 * 功能描述: 设置合同编号
	 * 
	 * @param contract_no
	 *            : String
	 */
	public void setContract_no(String contract_no)
	{
		this.contract_no = contract_no;
	}

	/**
	 * 功能描述: 获得在批抵消收款
	 * 
	 * @return 在批抵消收款 : BigDecimal
	 */
	public BigDecimal getOnroadamount()
	{
		return this.onroadamount;
	}

	/**
	 * 功能描述: 设置在批抵消收款
	 * 
	 * @param onroadamount
	 *            : BigDecimal
	 */
	public void setOnroadamount(BigDecimal onroadamount)
	{
		this.onroadamount = onroadamount;
	}

	/**
	 * 功能描述: 获得客户单清帐
	 * 
	 * @return 客户单清帐 : CustomSingleClear
	 */
	public CustomSingleClear getCustomSingleClear()
	{
		return this.customSingleClear;
	}

	/**
	 * 功能描述: 设置客户单清帐
	 * 
	 * @param customSingleClear
	 *            : CustomSingleClear
	 */
	public void setCustomSingleClear(CustomSingleClear customSingleClear)
	{
		this.customSingleClear = customSingleClear;
	}
	/**
	 * 本位币
	 * @return
	 */
	public BigDecimal getBwbje() {
		return bwbje;
	}
	/**
	 * 本位币
	 * @return
	 */
	public void setBwbje(BigDecimal bwbje) {
		this.bwbje = bwbje;
	}
	/**
	 * 保证金
	 * @return
	 */
	public BigDecimal getSuretybond() {
		return suretybond;
	}
	/**
	 * 保证金
	 * @return
	 */
	public void setSuretybond(BigDecimal suretybond) {
		this.suretybond = suretybond;
	}

	public String getBktxt() {
		return bktxt;
	}

	public void setBktxt(String bktxt) {
		this.bktxt = bktxt;
	}
	
	/**
	 * 功能描述: 设置未清本位币金额
	 * @return
	 */
	public BigDecimal getUnbwbje() {
		return unbwbje;
	}
	/**
	 * 功能描述: 取得未清本位币金额
	 * @param unbwbje
	 */
	public void setUnbwbje(BigDecimal unbwbje) {
		this.unbwbje = unbwbje;
	}
	

	/**
	 * @return the customertitleid
	 */
	public String getCustomertitleid()
	{
		return customertitleid;
	}

	/**
	 * @param customertitleid
	 *            the customertitleid to set
	 */
	public void setCustomertitleid(String customertitleid)
	{
		this.customertitleid = customertitleid;
	}

	/**
	 * @return the collectid
	 */
	public String getCollectid()
	{
		return collectid;
	}

	/**
	 * @param collectid
	 *            the collectid to set
	 */
	public void setCollectid(String collectid)
	{
		this.collectid = collectid;
	}

	/**
	 * 默认构造器
	 */
	public UnClearCollect()
	{
		super();
	}
}
