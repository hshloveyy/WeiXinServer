/*
 * @(#)UnClearPayment.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年07月12日 15点46分20秒
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
 * 未清付款（借方）(UnClearPayment)实体类
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
@Table(name = "YUNCLEARPAYMENT")
@org.hibernate.annotations.Entity(dynamicInsert = true, dynamicUpdate = true)
public class UnClearPayment extends BaseObject
{
	// fields
	/*
	 * 客户端
	 */
	@Column(name = "MANDT")
	private String client;

	/*
	 * 未清付款ID
	 */
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid.hex")
	@Column(name = "UNCLEARPAYMENTID")
	private String unclearpaymentid;

	/*
	 * 合同编号
	 */
	@Column(name = "CONTRACT_NO")
	@ValidateRule(dataType = 9, label = "合同编号", maxLength = 50, required = false)
	private String contract_no;

	/*
	 * 到单号
	 */
	@Column(name = "PICK_LIST_NO")
	@ValidateRule(dataType = 9, label = "到单号", maxLength = 50, required = false)
	private String pick_list_no;

	/*
	 * 项目编号
	 */
	@Column(name = "PROJECT_NO")
	@ValidateRule(dataType = 9, label = "项目编号", maxLength = 36, required = false)
	private String project_no;

	/*
	 * 财务凭证号
	 */
	@Column(name = "VOUCHERNO")
	@ValidateRule(dataType = 9, label = "财务凭证号", maxLength = 30, required = false)
	private String voucherno;

	/*
	 * 供应商未清数据抬头ID
	 */
	@Column(name = "VENDORTITLEID")
	@ValidateRule(dataType = 9, label = "供应商未清数据抬头ID", maxLength = 36, required = false)
	private String vendortitleid;

	/*
	 * 过账日期
	 */
	@Column(name = "ACCOUNTDATE")
	@ValidateRule(dataType = 9, label = "过账日期", maxLength = 20, required = false)
	private String accountdate;

	/*
	 * 付款ID
	 */
	@Column(name = "PAYMENTID")
	@ValidateRule(dataType = 9, label = "付款ID", maxLength = 36, required = false)
	private String paymentid;

	/*
	 * 付款金额分配ID
	 */
	@Column(name = "PAYMENTITEMID")
	@ValidateRule(dataType = 9, label = "付款金额分配ID", maxLength = 36, required = false)
	private String paymentitemid;

	/*
	 * 原合同编号
	 */
	@Column(name = "OLD_CONTRACT_NO")
	@ValidateRule(dataType = 9, label = "原合同编号", maxLength = 200, required = false)
	private String old_contract_no;

	/*
	 * 金额
	 */
	@Column(name = "PAYMENTAMOUNT")
	@ValidateRule(dataType = 0, label = "金额", required = false)
	private BigDecimal paymentamount;

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
	 * 已清金额
	 */
	@Column(name = "OFFSETAMOUNT")
	@ValidateRule(dataType = 0, label = "已清金额", required = false)
	private BigDecimal offsetamount;

	/*
	 * 未清金额
	 */
	@Column(name = "UNOFFSETAMOUNT")
	@ValidateRule(dataType = 0, label = "未清金额", required = false)
	private BigDecimal unoffsetamount;

	/*
	 * 在批金额
	 */
	@Column(name = "ONROADAMOUNT")
	@ValidateRule(dataType = 0, label = "在批金额", required = false)
	private BigDecimal onroadamount;

	/*
	 * 本次抵消金额
	 */
	@Column(name = "NOWCLEARAMOUNT")
	@ValidateRule(dataType = 0, label = "本次抵消金额", required = false)
	private BigDecimal nowclearamount;

	/*
	 * 供应商单清帐
	 */
	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
	@JoinColumn(name = "SUPPLIERSCLEARID")
	@NotFound(action = NotFoundAction.IGNORE)
	private SupplierSinglClear supplierSinglClear;

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
	 * 凭证抬头文本
	 */
	@Column(name = "BKTXT")
	@ValidateRule(dataType = 9, label = "凭证抬头文本", required = false)
	private String bktxt;
	
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
	 * 功能描述: 获得未清付款ID
	 * 
	 * @return 未清付款ID : String
	 */
	public String getUnclearpaymentid()
	{
		return this.unclearpaymentid;
	}

	/**
	 * 功能描述: 设置未清付款ID
	 * 
	 * @param unclearpaymentid
	 *            : String
	 */
	public void setUnclearpaymentid(String unclearpaymentid)
	{
		this.unclearpaymentid = unclearpaymentid;
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
	 * 功能描述: 获得到单号
	 * 
	 * @return 到单号 : String
	 */
	public String getPick_list_no()
	{
		return this.pick_list_no;
	}

	/**
	 * 功能描述: 设置到单号
	 * 
	 * @param pick_list_no
	 *            : String
	 */
	public void setPick_list_no(String pick_list_no)
	{
		this.pick_list_no = pick_list_no;
	}

	/**
	 * 功能描述: 获得项目编号
	 * 
	 * @return 项目编号 : String
	 */
	public String getProject_no()
	{
		return this.project_no;
	}

	/**
	 * 功能描述: 设置项目编号
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
	 * 功能描述: 获得过账日期
	 * 
	 * @return 过账日期 : String
	 */
	public String getAccountdate()
	{
		return this.accountdate;
	}

	/**
	 * 功能描述: 设置过账日期
	 * 
	 * @param accountdate
	 *            : String
	 */
	public void setAccountdate(String accountdate)
	{
		this.accountdate = accountdate;
	}

	/**
	 * 功能描述: 获得付款ID
	 * 
	 * @return 付款ID : String
	 */
	public String getPaymentid()
	{
		return this.paymentid;
	}

	/**
	 * 功能描述: 设置付款ID
	 * 
	 * @param paymentid
	 *            : String
	 */
	public void setPaymentid(String paymentid)
	{
		this.paymentid = paymentid;
	}

	/**
	 * 功能描述: 获得付款金额分配ID
	 * 
	 * @return 付款金额分配ID : String
	 */
	public String getPaymentitemid()
	{
		return this.paymentitemid;
	}

	/**
	 * 功能描述: 设置付款金额分配ID
	 * 
	 * @param paymentitemid
	 *            : String
	 */
	public void setPaymentitemid(String paymentitemid)
	{
		this.paymentitemid = paymentitemid;
	}

	/**
	 * 功能描述: 获得原合同编号
	 * 
	 * @return 原合同编号 : String
	 */
	public String getOld_contract_no()
	{
		return this.old_contract_no;
	}

	/**
	 * 功能描述: 设置原合同编号
	 * 
	 * @param old_contract_no
	 *            : String
	 */
	public void setOld_contract_no(String old_contract_no)
	{
		this.old_contract_no = old_contract_no;
	}

	/**
	 * 功能描述: 获得金额
	 * 
	 * @return 金额 : BigDecimal
	 */
	public BigDecimal getPaymentamount()
	{
		return this.paymentamount;
	}

	/**
	 * 功能描述: 设置金额
	 * 
	 * @param paymentamount
	 *            : BigDecimal
	 */
	public void setPaymentamount(BigDecimal paymentamount)
	{
		this.paymentamount = paymentamount;
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
	 * 功能描述: 获得已清金额
	 * 
	 * @return 已清金额 : BigDecimal
	 */
	public BigDecimal getOffsetamount()
	{
		return this.offsetamount;
	}

	/**
	 * 功能描述: 设置已清金额
	 * 
	 * @param offsetamount
	 *            : BigDecimal
	 */
	public void setOffsetamount(BigDecimal offsetamount)
	{
		this.offsetamount = offsetamount;
	}

	/**
	 * 功能描述: 获得未清金额
	 * 
	 * @return 未清金额 : BigDecimal
	 */
	public BigDecimal getUnoffsetamount()
	{
		return this.unoffsetamount;
	}

	/**
	 * 功能描述: 设置未清金额
	 * 
	 * @param unoffsetamount
	 *            : BigDecimal
	 */
	public void setUnoffsetamount(BigDecimal unoffsetamount)
	{
		this.unoffsetamount = unoffsetamount;
	}

	/**
	 * 功能描述: 获得在批金额
	 * 
	 * @return 在批金额 : BigDecimal
	 */
	public BigDecimal getOnroadamount()
	{
		return this.onroadamount;
	}

	/**
	 * 功能描述: 设置在批金额
	 * 
	 * @param onroadamount
	 *            : BigDecimal
	 */
	public void setOnroadamount(BigDecimal onroadamount)
	{
		this.onroadamount = onroadamount;
	}

	/**
	 * 功能描述: 获得本次抵消金额
	 * 
	 * @return 本次抵消金额 : BigDecimal
	 */
	public BigDecimal getNowclearamount()
	{
		return this.nowclearamount;
	}

	/**
	 * 功能描述: 设置本次抵消金额
	 * 
	 * @param nowclearamount
	 *            : BigDecimal
	 */
	public void setNowclearamount(BigDecimal nowclearamount)
	{
		this.nowclearamount = nowclearamount;
	}

	/**
	 * 功能描述: 获得供应商单清帐
	 * 
	 * @return 供应商单清帐 : SupplierSinglClear
	 */
	public SupplierSinglClear getSupplierSinglClear()
	{
		return this.supplierSinglClear;
	}

	/**
	 * 功能描述: 设置供应商单清帐
	 * 
	 * @param supplierSinglClear
	 *            : SupplierSinglClear
	 */
	public void setSupplierSinglClear(SupplierSinglClear supplierSinglClear)
	{
		this.supplierSinglClear = supplierSinglClear;
	}

	/**
	 * @return the vendortitleid
	 */
	public String getVendortitleid()
	{
		return vendortitleid;
	}

	/**
	 * @param vendortitleid
	 *            the vendortitleid to set
	 */
	public void setVendortitleid(String vendortitleid)
	{
		this.vendortitleid = vendortitleid;
	}

	public BigDecimal getBwbje() {
		return bwbje;
	}

	public void setBwbje(BigDecimal bwbje) {
		this.bwbje = bwbje;
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
	 * 默认构造器
	 */
	public UnClearPayment()
	{
		super();
	}
}
