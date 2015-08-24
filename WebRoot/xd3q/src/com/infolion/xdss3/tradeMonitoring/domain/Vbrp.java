/*
 * @(#)Vbrp.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年06月06日 13点53分47秒
 *  描　述：创建
 */
package com.infolion.xdss3.tradeMonitoring.domain;

import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.infolion.platform.dpframework.core.annotation.ValidateRule;
import com.infolion.platform.dpframework.core.domain.BaseObject;

/**
 * <pre>
 * 开票明细(Vbrp)实体类
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
@Table(name = "YGETVBRP")
@org.hibernate.annotations.Entity(dynamicInsert = true, dynamicUpdate = true)
public class Vbrp extends BaseObject
{
	// fields
	/*
	 * 客户端
	 */
	@Column(name = "MANDT")
	private String client;

	/*
	 * SAP销售订单号
	 */
	@Column(name = "AUBEL")
	private String aubel;

	/**
	 * @return the aubel
	 */
	public String getAubel()
	{
		return aubel;
	}

	/**
	 * @param aubel
	 *            the aubel to set
	 */
	public void setAubel(String aubel)
	{
		this.aubel = aubel;
	}

	/*
	 * 开票凭证明细ID
	 */
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid.hex")
	@Column(name = "VBRPID")
	private String vbrpid;

	/*
	 * 行项目号
	 */
	@Column(name = "POSNR")
	@ValidateRule(dataType = 0, label = "行项目号", required = false)
	private String posnr;

	/*
	 * 物料号
	 */
	@Column(name = "MATNR")
	@ValidateRule(dataType = 9, label = "物料号", maxLength = 18, required = false)
	private String matnr;

	/*
	 * 物料描述
	 */
	@Column(name = "ARKTX")
	@ValidateRule(dataType = 9, label = "物料描述", maxLength = 40, required = false)
	private String arktx;

	/*
	 * 外部纸质合同号
	 */
	@Column(name = "BSTKD")
	@ValidateRule(dataType = 9, label = "外部纸质合同号", maxLength = 40, required = false)
	private String bstkd;

	/*
	 * 数量
	 */
	@Column(name = "FKIMG")
	@ValidateRule(dataType = 0, label = "数量", required = false)
	private BigDecimal fkimg;

	/*
	 * 单位
	 */
	@Column(name = "VRKME")
	@ValidateRule(dataType = 9, label = "单位", maxLength = 3, required = false)
	private String vrkme;

	/*
	 * 含税金额
	 */
	@Column(name = "TAXIN")
	@ValidateRule(dataType = 0, label = "含税金额", required = false)
	private BigDecimal taxin;

	/*
	 * 税额
	 */
	@Column(name = "TAX")
	@ValidateRule(dataType = 0, label = "税额", required = false)
	private BigDecimal tax;

	/*
	 * 不含税金额
	 */
	@Column(name = "TAXOUT")
	@ValidateRule(dataType = 0, label = "不含税金额", required = false)
	private BigDecimal taxout;

	/*
	 * 应收款日期
	 */
	@Column(name = "YSKR")
	@ValidateRule(dataType = 9, label = "应收款日期", maxLength = 14, required = false)
	private String yskr;

	/*
	 * 税率
	 */
	@Column(name = "SHUILV")
	@ValidateRule(dataType = 9, label = "税率", maxLength = 10, required = false)
	private String shuilv;

	/*
	 * 实收款日期
	 */
	@Column(name = "REALCOLLECTDAY")
	@ValidateRule(dataType = 9, label = "实收款日期", maxLength = 14, required = false)
	private String realcollectday;

	/*
	 * 开票数据抬头
	 */
	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
	@JoinColumn(name = "VBELN")
	@NotFound(action = NotFoundAction.IGNORE)
	private Vbrk vbrk;
	
	/*
	 * 参考单据的单据编号,800打头物料凭证号
	 */
	@Column(name = "VGBEL")
	@ValidateRule(dataType = 9, label = "参考单据的单据编号", maxLength = 10, required = false)
	private String vgbel;

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
	 * 功能描述: 获得开票凭证明细ID
	 * 
	 * @return 开票凭证明细ID : String
	 */
	public String getVbrpid()
	{
		return this.vbrpid;
	}

	/**
	 * 功能描述: 设置开票凭证明细ID
	 * 
	 * @param vbrpid
	 *            : String
	 */
	public void setVbrpid(String vbrpid)
	{
		this.vbrpid = vbrpid;
	}

	/**
	 * 功能描述: 获得行项目号
	 * 
	 * @return 行项目号 : String
	 */
	public String getPosnr()
	{
		return this.posnr;
	}

	/**
	 * 功能描述: 设置行项目号
	 * 
	 * @param posnr
	 *            : String
	 */
	public void setPosnr(String posnr)
	{
		this.posnr = posnr;
	}

	/**
	 * 功能描述: 获得物料号
	 * 
	 * @return 物料号 : String
	 */
	public String getMatnr()
	{
		return this.matnr;
	}

	/**
	 * 功能描述: 设置物料号
	 * 
	 * @param matnr
	 *            : String
	 */
	public void setMatnr(String matnr)
	{
		this.matnr = matnr;
	}

	/**
	 * 功能描述: 获得物料描述
	 * 
	 * @return 物料描述 : String
	 */
	public String getArktx()
	{
		return this.arktx;
	}

	/**
	 * 功能描述: 设置物料描述
	 * 
	 * @param arktx
	 *            : String
	 */
	public void setArktx(String arktx)
	{
		this.arktx = arktx;
	}

	/**
	 * 功能描述: 获得外部纸质合同号
	 * 
	 * @return 外部纸质合同号 : String
	 */
	public String getBstkd()
	{
		return this.bstkd;
	}

	/**
	 * 功能描述: 设置外部纸质合同号
	 * 
	 * @param bstkd
	 *            : String
	 */
	public void setBstkd(String bstkd)
	{
		this.bstkd = bstkd;
	}

	/**
	 * 功能描述: 获得数量
	 * 
	 * @return 数量 : BigDecimal
	 */
	public BigDecimal getFkimg()
	{
		return this.fkimg;
	}

	/**
	 * 功能描述: 设置数量
	 * 
	 * @param fkimg
	 *            : BigDecimal
	 */
	public void setFkimg(BigDecimal fkimg)
	{
		this.fkimg = fkimg;
	}

	/**
	 * 功能描述: 获得单位
	 * 
	 * @return 单位 : String
	 */
	public String getVrkme()
	{
		return this.vrkme;
	}

	/**
	 * 功能描述: 设置单位
	 * 
	 * @param vrkme
	 *            : String
	 */
	public void setVrkme(String vrkme)
	{
		this.vrkme = vrkme;
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
	 * 功能描述: 获得应收款日期
	 * 
	 * @return 应收款日期 : String
	 */
	public String getYskr()
	{
		return this.yskr;
	}

	/**
	 * 功能描述: 设置应收款日期
	 * 
	 * @param yskr
	 *            : String
	 */
	public void setYskr(String yskr)
	{
		this.yskr = yskr;
	}

	/**
	 * 功能描述: 获得税率
	 * 
	 * @return 税率 : String
	 */
	public String getShuilv()
	{
		return this.shuilv;
	}

	/**
	 * 功能描述: 设置税率
	 * 
	 * @param shuilv
	 *            : String
	 */
	public void setShuilv(String shuilv)
	{
		this.shuilv = shuilv;
	}

	/**
	 * 功能描述: 获得实收款日期
	 * 
	 * @return 实收款日期 : String
	 */
	public String getRealcollectday()
	{
		return this.realcollectday;
	}

	/**
	 * 功能描述: 设置实收款日期
	 * 
	 * @param realcollectday
	 *            : String
	 */
	public void setRealcollectday(String realcollectday)
	{
		this.realcollectday = realcollectday;
	}

	/**
	 * 功能描述: 获得开票数据抬头
	 * 
	 * @return 开票数据抬头 : Vbrk
	 */
	public Vbrk getVbrk()
	{
		return this.vbrk;
	}

	/**
	 * 功能描述: 设置开票数据抬头
	 * 
	 * @param vbrk
	 *            : Vbrk
	 */
	public void setVbrk(Vbrk vbrk)
	{
		this.vbrk = vbrk;
	}

	/**
	 * 默认构造器
	 */
	public Vbrp()
	{
		super();
	}

	public String getVgbel() {
		return vgbel;
	}

	public void setVgbel(String vgbel) {
		this.vgbel = vgbel;
	}
}
