/*
 * @(#)SalesDoc.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2009年12月02日 17点06分42秒
 *  描　述：创建
 */
package com.infolion.sample.sales.domain;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.infolion.platform.dpframework.core.annotation.ValidateRule;
import com.infolion.platform.dpframework.core.domain.BaseObject;
import com.infolion.platform.dpframework.util.DateUtils;

/**
 * <pre>
 * SAP销售订单(SalesDoc)实体类
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
@Table(name = "YVSOHEAD")
@IdClass(SalesDocKey.class)
@org.hibernate.annotations.Entity(dynamicInsert = true, dynamicUpdate = true)
public class SalesDoc extends BaseObject implements Cloneable
{
	// fields
	/*
	 * 客户端
	 */
	@Id
	@Column(name = "MANDT")
	private String client;

	/*
	 * 销售凭证
	 */
	@Id
	@Column(name = "VBELN")
	private String vbeln;

	/*
	 * SAP销售凭证行项目
	 */
	@OneToMany(mappedBy = "salesDoc", cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
	@JoinColumns( { @JoinColumn(name = "MANDT"), @JoinColumn(name = "VBELN") })
	@NotFound(action = NotFoundAction.IGNORE)
	// @Transient
	private Set<SalesDocItem> salesDocItems;

	/*
	 * 销售订单日期
	 */
	@Column(name = "ERDAT")
	@ValidateRule(dataType = 9, label = "销售订单日期", maxLength = 10, required = false)
	private String erdat;

	/*
	 * 客户
	 */
	@Column(name = "KUNNR")
	@ValidateRule(dataType = 9, label = "客户", maxLength = 10, required = false)
	private String kunnr;

	/*
	 * 最后审批人
	 */
	@Column(name = "LASTAPPNAME")
	@ValidateRule(dataType = 9, label = "最后审批人", maxLength = 64, required = false)
	private String lastappname;

	/*
	 * 最后审批时间
	 */
	@Column(name = "LASTAPPTIME")
	@ValidateRule(dataType = 9, label = "最后审批时间", maxLength = 10, required = false)
	private String lastapptime;

	/*
	 * 流程状态
	 */
	@Column(name = "PROCESSSTATE")
	@ValidateRule(dataType = 9, label = "流程状态", maxLength = 30, required = false)
	private String processstate;

	/*
	 * 付款条件
	 */
	@Column(name = "ZTERM")
	@ValidateRule(dataType = 9, label = "付款条件", maxLength = 4, required = false)
	private String zterm;

	@Column(name = "endorsestate")
	private String endorsestate;

	/**
	 * @return the endorsestate
	 */
	public String getEndorsestate()
	{
		return endorsestate;
	}

	/**
	 * @param endorsestate
	 *            the endorsestate to set
	 */
	public void setEndorsestate(String endorsestate)
	{
		this.endorsestate = endorsestate;
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
	 * 功能描述: 获得销售凭证
	 * 
	 * @return 销售凭证 : String
	 */
	public String getVbeln()
	{
		return this.vbeln;
	}

	/**
	 * 功能描述: 设置销售凭证
	 * 
	 * @param vbeln
	 *            : String
	 */
	public void setVbeln(String vbeln)
	{
		this.vbeln = vbeln;
	}

	/**
	 * 功能描述: 获得销售订单日期
	 * 
	 * @return 销售订单日期 : String
	 */
	public String getErdat()
	{
		return DateUtils.toDisplayStr(this.erdat, DateUtils.HYPHEN_DISPLAY_DATE);
	}

	/**
	 * 功能描述: 设置销售订单日期
	 * 
	 * @param erdat
	 *            : String
	 */
	public void setErdat(String erdat)
	{
		erdat = DateUtils.toStoreStr(erdat);
		this.erdat = erdat;
	}

	/**
	 * 功能描述: 获得客户
	 * 
	 * @return 客户 : String
	 */
	public String getKunnr()
	{
		return this.kunnr;
	}

	/**
	 * 功能描述: 设置客户
	 * 
	 * @param kunnr
	 *            : String
	 */
	public void setKunnr(String kunnr)
	{
		this.kunnr = kunnr;
	}

	/**
	 * 功能描述: 获得SAP销售凭证行项目
	 * 
	 * @return SAP销售凭证行项目 : Set<SalesDocItem>
	 */
	public Set<SalesDocItem> getSalesDocItems()
	{
		return this.salesDocItems;
	}

	/**
	 * 功能描述: 设置SAP销售凭证行项目
	 * 
	 * @param salesDocItems
	 *            : Set<SalesDocItem>
	 */
	public void setSalesDocItems(Set<SalesDocItem> salesDocItems)
	{
		this.salesDocItems = salesDocItems;
	}

	/**
	 * 功能描述: 获得最后审批人
	 * 
	 * @return 最后审批人 : String
	 */
	public String getLastappname()
	{
		return this.lastappname;
	}

	/**
	 * 功能描述: 设置最后审批人
	 * 
	 * @param lastappname
	 *            : String
	 */
	public void setLastappname(String lastappname)
	{
		this.lastappname = lastappname;
	}

	/**
	 * 功能描述: 获得最后审批时间
	 * 
	 * @return 最后审批时间 : String
	 */
	public String getLastapptime()
	{
		return DateUtils.toDisplayStr(this.lastapptime, DateUtils.HYPHEN_DISPLAY_DATE);
	}

	/**
	 * 功能描述: 设置最后审批时间
	 * 
	 * @param lastapptime
	 *            : String
	 */
	public void setLastapptime(String lastapptime)
	{
		lastapptime = DateUtils.toStoreStr(lastapptime);
		this.lastapptime = lastapptime;
	}

	/**
	 * 功能描述: 获得流程状态
	 * 
	 * @return 流程状态 : String
	 */
	public String getProcessstate()
	{
		return this.processstate;
	}

	/**
	 * 功能描述: 设置流程状态
	 * 
	 * @param processstate
	 *            : String
	 */
	public void setProcessstate(String processstate)
	{
		this.processstate = processstate;
	}

	/**
	 * 功能描述: 获得付款条件
	 * 
	 * @return 付款条件 : String
	 */
	public String getZterm()
	{
		return this.zterm;
	}

	/**
	 * 功能描述: 设置付款条件
	 * 
	 * @param zterm
	 *            : String
	 */
	public void setZterm(String zterm)
	{
		this.zterm = zterm;
	}

	/**
	 * 默认构造器
	 */
	public SalesDoc()
	{
		super();
	}

	public SalesDoc copy()
	{
		try
		{
			return (SalesDoc) this.clone();
		}
		catch (CloneNotSupportedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
