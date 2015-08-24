/*
 * @(#)Vbrk.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年06月06日 13点53分40秒
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
 * 开票数据抬头(Vbrk)实体类
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
@Table(name = "YGETVBRK")
@org.hibernate.annotations.Entity(dynamicInsert = true, dynamicUpdate = true)
public class Vbrk extends BaseObject
{
	// fields
	/*
	 * 开票明细
	 */
	@OneToMany(mappedBy = "vbrk", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@NotFound(action = NotFoundAction.IGNORE)
	private Set<Vbrp> vbrp;

	/*
	 * 客户端
	 */
	@Column(name = "MANDT")
	private String client;

	/*
	 * 发票号
	 */
	@Id
	@Column(name = "VBELN")
	private String vbeln;

	/*
	 * 发票类型
	 */
	@Column(name = "FKART")
	@ValidateRule(dataType = 9, label = "发票类型", maxLength = 4, required = false)
	private String fkart;

	/*
	 * 币别
	 */
	@Column(name = "WAERK")
	@ValidateRule(dataType = 9, label = "币别", maxLength = 5, required = false)
	private String waerk;

	/*
	 * 开票日期
	 */
	@Column(name = "FKDAT")
	@ValidateRule(dataType = 9, label = "开票日期", maxLength = 14, required = false)
	private String fkdat;

	/*
	 * 抬头文本
	 */
	@Column(name = "VTEXT")
	@ValidateRule(dataType = 9, label = "抬头文本", maxLength = 30, required = false)
	private String vtext;

	/*
	 * 客户
	 */
	@Column(name = "KUNRG")
	@ValidateRule(dataType = 9, label = "客户", maxLength = 10, required = false)
	private String kunrg;

	/*
	 * 客户名称
	 */
	@Column(name = "NAME1")
	@ValidateRule(dataType = 9, label = "客户名称", maxLength = 35, required = false)
	private String name1;

	/*
	 * 过账日期
	 */
	@Column(name = "BUDAT")
	@ValidateRule(dataType = 9, label = "过账日期", maxLength = 14, required = false)
	private String budat;

	/*
	 * 会计汇率
	 */
	@Column(name = "KURRF")
	@ValidateRule(dataType = 0, label = "会计汇率", required = false)
	private BigDecimal kurrf;

	/*
	 * 含税金额
	 */
	@Column(name = "TAXIN")
	@ValidateRule(dataType = 0, label = "含税金额", required = false)
	private BigDecimal taxin;

	/*
	 * 不含税金额
	 */
	@Column(name = "TAXOUT")
	@ValidateRule(dataType = 0, label = "不含税金额", required = false)
	private BigDecimal taxout;

	/*
	 * 税额
	 */
	@Column(name = "TAX")
	@ValidateRule(dataType = 0, label = "税额", required = false)
	private BigDecimal tax;
	
	/*
	 * 被冲销发票号
	 */
	@Column(name = "SFAKN")
	@ValidateRule(dataType = 9, label = "被冲销发票号", maxLength = 35, required = false)
	private String sfakn;

	/**
	 * 功能描述: 获得开票明细
	 * 
	 * @return 开票明细 : Set<Vbrp>
	 */
	public Set<Vbrp> getVbrp()
	{
		return this.vbrp;
	}

	/**
	 * 功能描述: 设置开票明细
	 * 
	 * @param vbrp
	 *            : Set<Vbrp>
	 */
	public void setVbrp(Set<Vbrp> vbrp)
	{
		this.vbrp = vbrp;
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
	 * 功能描述: 获得发票号
	 * 
	 * @return 发票号 : String
	 */
	public String getVbeln()
	{
		return this.vbeln;
	}

	/**
	 * 功能描述: 设置发票号
	 * 
	 * @param vbeln
	 *            : String
	 */
	public void setVbeln(String vbeln)
	{
		this.vbeln = vbeln;
	}

	/**
	 * 功能描述: 获得发票类型
	 * 
	 * @return 发票类型 : String
	 */
	public String getFkart()
	{
		return this.fkart;
	}

	/**
	 * 功能描述: 设置发票类型
	 * 
	 * @param fkart
	 *            : String
	 */
	public void setFkart(String fkart)
	{
		this.fkart = fkart;
	}

	/**
	 * 功能描述: 获得币别
	 * 
	 * @return 币别 : String
	 */
	public String getWaerk()
	{
		return this.waerk;
	}

	/**
	 * 功能描述: 设置币别
	 * 
	 * @param waerk
	 *            : String
	 */
	public void setWaerk(String waerk)
	{
		this.waerk = waerk;
	}

	/**
	 * 功能描述: 获得开票日期
	 * 
	 * @return 开票日期 : String
	 */
	public String getFkdat()
	{
		return this.fkdat;
	}

	/**
	 * 功能描述: 设置开票日期
	 * 
	 * @param fkdat
	 *            : String
	 */
	public void setFkdat(String fkdat)
	{
		this.fkdat = fkdat;
	}

	/**
	 * 功能描述: 获得抬头文本
	 * 
	 * @return 抬头文本 : String
	 */
	public String getVtext()
	{
		return this.vtext;
	}

	/**
	 * 功能描述: 设置抬头文本
	 * 
	 * @param vtext
	 *            : String
	 */
	public void setVtext(String vtext)
	{
		this.vtext = vtext;
	}

	/**
	 * 功能描述: 获得客户
	 * 
	 * @return 客户 : String
	 */
	public String getKunrg()
	{
		return this.kunrg;
	}

	/**
	 * 功能描述: 设置客户
	 * 
	 * @param kunrg
	 *            : String
	 */
	public void setKunrg(String kunrg)
	{
		this.kunrg = kunrg;
	}

	/**
	 * 功能描述: 获得客户名称
	 * 
	 * @return 客户名称 : String
	 */
	public String getName1()
	{
		return this.name1;
	}

	/**
	 * 功能描述: 设置客户名称
	 * 
	 * @param name1
	 *            : String
	 */
	public void setName1(String name1)
	{
		this.name1 = name1;
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
	 * 功能描述: 获得会计汇率
	 * 
	 * @return 会计汇率 : BigDecimal
	 */
	public BigDecimal getKurrf()
	{
		return this.kurrf;
	}

	/**
	 * 功能描述: 设置会计汇率
	 * 
	 * @param kurrf
	 *            : BigDecimal
	 */
	public void setKurrf(BigDecimal kurrf)
	{
		this.kurrf = kurrf;
	}

	/**
	 * 功能描述: 获得含税金额
	 * 
	 * @return 含税金额 : BigDecimal
	 */
	public BigDecimal getTaxin()
	{
		return this.taxin;
	}

	/**
	 * 功能描述: 设置含税金额
	 * 
	 * @param taxin
	 *            : BigDecimal
	 */
	public void setTaxin(BigDecimal taxin)
	{
		this.taxin = taxin;
	}

	/**
	 * 功能描述: 获得不含税金额
	 * 
	 * @return 不含税金额 : BigDecimal
	 */
	public BigDecimal getTaxout()
	{
		return this.taxout;
	}

	/**
	 * 功能描述: 设置不含税金额
	 * 
	 * @param taxout
	 *            : BigDecimal
	 */
	public void setTaxout(BigDecimal taxout)
	{
		this.taxout = taxout;
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
	 * 默认构造器
	 */
	public Vbrk()
	{
		super();
		
	}

	public String getSfakn() {
		return sfakn;
	}

	public void setSfakn(String sfakn) {
		this.sfakn = sfakn;
	}
}
