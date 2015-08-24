/*
 * @(#)InvoiceVerify.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年07月06日 09点42分49秒
 *  描　述：创建
 */
package com.infolion.xdss3.tradeMonitoring.domain;

import java.math.BigDecimal;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.infolion.platform.dpframework.core.annotation.ValidateRule;
import com.infolion.platform.dpframework.core.domain.BaseObject;

/**
 * <pre>
 * 发票校验(InvoiceVerify)实体类
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
@Table(name = "YGETBELNR")
@org.hibernate.annotations.Entity(dynamicInsert = true, dynamicUpdate = true)
public class InvoiceVerify extends BaseObject
{
	// fields
	/*
	 * 客户端
	 */
	@Column(name = "MANDT")
	private String client;

	/*
	 * 唯一标识
	 */
	@Id
	@Column(name = "MAINCODE")
	private String maincode;

	/*
	 * 会计年度
	 */
	@Column(name = "GJAHR")
	@ValidateRule(dataType = 0, label = "会计年度", required = false)
	private String gjahr;

	/*
	 * 凭证号码
	 */
	@Column(name = "BELNR")
	@ValidateRule(dataType = 9, label = "凭证号码", maxLength = 10, required = false)
	private String belnr;

	/*
	 * 过账日期
	 */
	@Column(name = "BUDAT")
	@ValidateRule(dataType = 9, label = "过账日期", maxLength = 14, required = false)
	private String budat;

	/*
	 * 开票日期
	 */
	@Column(name = "BLDAT")
	@ValidateRule(dataType = 9, label = "开票日期", maxLength = 14, required = false)
	private String bldat;

	/*
	 * 抬头文本
	 */
	@Column(name = "BKTXT")
	@ValidateRule(dataType = 9, label = "抬头文本", maxLength = 30, required = false)
	private String bktxt;

	/*
	 * 币别
	 */
	@Column(name = "WAERS")
	@ValidateRule(dataType = 9, label = "币别", maxLength = 5, required = false)
	private String waers;

	/*
	 * 汇率
	 */
	@Column(name = "KURSF")
	@ValidateRule(dataType = 0, label = "汇率", required = false)
	private BigDecimal kursf;

	/*
	 * 含税金额
	 */
	@Column(name = "TAXIN_DMBTR")
	@ValidateRule(dataType = 0, label = "含税金额", required = false)
	private BigDecimal taxin_dmbtr;

	/*
	 * 税额
	 */
	@Column(name = "TAX")
	@ValidateRule(dataType = 0, label = "税额", required = false)
	private BigDecimal tax;

	/*
	 * 不含税金额
	 */
	@Column(name = "TAXOUT_DMBTR")
	@ValidateRule(dataType = 0, label = "不含税金额", required = false)
	private BigDecimal taxout_dmbtr;

	/*
	 * 冲销凭证号
	 */
	@Column(name = "STBLG")
	@ValidateRule(dataType = 9, label = "冲销凭证号", maxLength = 10, required = false)
	private String stblg;

	/*
	 * 冲销凭证会计年度
	 */
	@Column(name = "STJAH")
	@ValidateRule(dataType = 0, label = "冲销凭证会计年度", required = false)
	private String stjah;

	/*
	 * 应付款日
	 */
	@Column(name = "YFKR")
	@ValidateRule(dataType = 9, label = "应付款日", maxLength = 14, required = false)
	private String yfkr;

	/*
	 * 应到货日
	 */
	@Column(name = "YDHR")
	@ValidateRule(dataType = 9, label = "应到货日", maxLength = 14, required = false)
	private String ydhr;

	/*
	 * 采购订单号
	 */
	@Column(name = "EBELN")
	@ValidateRule(dataType = 9, label = "采购订单号", maxLength = 10, required = false)
	private String ebeln;

	/*
	 * 采购合同号
	 */
	@Column(name = "VERKF")
	@ValidateRule(dataType = 9, label = "采购合同号", maxLength = 30, required = false)
	private String verkf;

	/*
	 * 发票校验行项目
	 */
	@OneToMany(mappedBy = "invoiceVerify", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@NotFound(action = NotFoundAction.IGNORE)
	private Set<InvoiceVerifyItem> invoiceVerifyItem;

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
	 * 功能描述: 获得唯一标识
	 * 
	 * @return 唯一标识 : String
	 */
	public String getMaincode()
	{
		return this.maincode;
	}

	/**
	 * 功能描述: 设置唯一标识
	 * 
	 * @param maincode
	 *            : String
	 */
	public void setMaincode(String maincode)
	{
		this.maincode = maincode;
	}

	/**
	 * 功能描述: 获得会计年度
	 * 
	 * @return 会计年度 : String
	 */
	public String getGjahr()
	{
		return this.gjahr;
	}

	/**
	 * 功能描述: 设置会计年度
	 * 
	 * @param gjahr
	 *            : String
	 */
	public void setGjahr(String gjahr)
	{
		this.gjahr = gjahr;
	}

	/**
	 * 功能描述: 获得凭证号码
	 * 
	 * @return 凭证号码 : String
	 */
	public String getBelnr()
	{
		return this.belnr;
	}

	/**
	 * 功能描述: 设置凭证号码
	 * 
	 * @param belnr
	 *            : String
	 */
	public void setBelnr(String belnr)
	{
		this.belnr = belnr;
	}

	/**
	 * 功能描述: 获得过账日期
	 * 
	 * @return 过账日期 : String
	 */
	public String getBudat()
	{
		return this.budat;
	}

	/**
	 * 功能描述: 设置过账日期
	 * 
	 * @param budat
	 *            : String
	 */
	public void setBudat(String budat)
	{
		this.budat = budat;
	}

	/**
	 * 功能描述: 获得开票日期
	 * 
	 * @return 开票日期 : String
	 */
	public String getBldat()
	{
		return this.bldat;
	}

	/**
	 * 功能描述: 设置开票日期
	 * 
	 * @param bldat
	 *            : String
	 */
	public void setBldat(String bldat)
	{
		this.bldat = bldat;
	}

	/**
	 * 功能描述: 获得抬头文本
	 * 
	 * @return 抬头文本 : String
	 */
	public String getBktxt()
	{
		return this.bktxt;
	}

	/**
	 * 功能描述: 设置抬头文本
	 * 
	 * @param bktxt
	 *            : String
	 */
	public void setBktxt(String bktxt)
	{
		this.bktxt = bktxt;
	}

	/**
	 * 功能描述: 获得币别
	 * 
	 * @return 币别 : String
	 */
	public String getWaers()
	{
		return this.waers;
	}

	/**
	 * 功能描述: 设置币别
	 * 
	 * @param waers
	 *            : String
	 */
	public void setWaers(String waers)
	{
		this.waers = waers;
	}

	/**
	 * 功能描述: 获得汇率
	 * 
	 * @return 汇率 : BigDecimal
	 */
	public BigDecimal getKursf()
	{
		return this.kursf;
	}

	/**
	 * 功能描述: 设置汇率
	 * 
	 * @param kursf
	 *            : BigDecimal
	 */
	public void setKursf(BigDecimal kursf)
	{
		this.kursf = kursf;
	}

	/**
	 * 功能描述: 获得含税金额
	 * 
	 * @return 含税金额 : BigDecimal
	 */
	public BigDecimal getTaxin_dmbtr()
	{
		return this.taxin_dmbtr;
	}

	/**
	 * 功能描述: 设置含税金额
	 * 
	 * @param taxin_dmbtr
	 *            : BigDecimal
	 */
	public void setTaxin_dmbtr(BigDecimal taxin_dmbtr)
	{
		this.taxin_dmbtr = taxin_dmbtr;
	}

	/**
	 * 功能描述: 获得税额
	 * 
	 * @return 税额 : BigDecimal
	 */
	public BigDecimal getTax()
	{
		return this.tax;
	}

	/**
	 * 功能描述: 设置税额
	 * 
	 * @param tax
	 *            : BigDecimal
	 */
	public void setTax(BigDecimal tax)
	{
		this.tax = tax;
	}

	/**
	 * 功能描述: 获得不含税金额
	 * 
	 * @return 不含税金额 : BigDecimal
	 */
	public BigDecimal getTaxout_dmbtr()
	{
		return this.taxout_dmbtr;
	}

	/**
	 * 功能描述: 设置不含税金额
	 * 
	 * @param taxout_dmbtr
	 *            : BigDecimal
	 */
	public void setTaxout_dmbtr(BigDecimal taxout_dmbtr)
	{
		this.taxout_dmbtr = taxout_dmbtr;
	}

	/**
	 * 功能描述: 获得冲销凭证号
	 * 
	 * @return 冲销凭证号 : String
	 */
	public String getStblg()
	{
		return this.stblg;
	}

	/**
	 * 功能描述: 设置冲销凭证号
	 * 
	 * @param stblg
	 *            : String
	 */
	public void setStblg(String stblg)
	{
		this.stblg = stblg;
	}

	/**
	 * 功能描述: 获得冲销凭证会计年度
	 * 
	 * @return 冲销凭证会计年度 : String
	 */
	public String getStjah()
	{
		return this.stjah;
	}

	/**
	 * 功能描述: 设置冲销凭证会计年度
	 * 
	 * @param stjah
	 *            : String
	 */
	public void setStjah(String stjah)
	{
		this.stjah = stjah;
	}

	/**
	 * 功能描述: 获得应付款日
	 * 
	 * @return 应付款日 : String
	 */
	public String getYfkr()
	{
		return this.yfkr;
	}

	/**
	 * 功能描述: 设置应付款日
	 * 
	 * @param yfkr
	 *            : String
	 */
	public void setYfkr(String yfkr)
	{
		this.yfkr = yfkr;
	}

	/**
	 * 功能描述: 获得应到货日
	 * 
	 * @return 应到货日 : String
	 */
	public String getYdhr()
	{
		return this.ydhr;
	}

	/**
	 * 功能描述: 设置应到货日
	 * 
	 * @param ydhr
	 *            : String
	 */
	public void setYdhr(String ydhr)
	{
		this.ydhr = ydhr;
	}

	/**
	 * 功能描述: 获得采购订单号
	 * 
	 * @return 采购订单号 : String
	 */
	public String getEbeln()
	{
		return this.ebeln;
	}

	/**
	 * 功能描述: 设置采购订单号
	 * 
	 * @param ebeln
	 *            : String
	 */
	public void setEbeln(String ebeln)
	{
		this.ebeln = ebeln;
	}

	/**
	 * 功能描述: 获得采购合同号
	 * 
	 * @return 采购合同号 : String
	 */
	public String getVerkf()
	{
		return this.verkf;
	}

	/**
	 * 功能描述: 设置采购合同号
	 * 
	 * @param verkf
	 *            : String
	 */
	public void setVerkf(String verkf)
	{
		this.verkf = verkf;
	}

	/**
	 * 功能描述: 获得发票校验行项目
	 * 
	 * @return 发票校验行项目 : Set<InvoiceVerifyItem>
	 */
	public Set<InvoiceVerifyItem> getInvoiceVerifyItem()
	{
		return this.invoiceVerifyItem;
	}

	/**
	 * 功能描述: 设置发票校验行项目
	 * 
	 * @param invoiceVerifyItem
	 *            : Set<InvoiceVerifyItem>
	 */
	public void setInvoiceVerifyItem(Set<InvoiceVerifyItem> invoiceVerifyItem)
	{
		this.invoiceVerifyItem = invoiceVerifyItem;
	}

	/**
	 * 默认构造器
	 */
	public InvoiceVerify()
	{
		super();
	}
}
