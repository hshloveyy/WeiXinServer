/*
 * @(#)Supplier.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年05月25日 14点51分13秒
 *  描　述：创建
 */
package com.infolion.xdss3.masterData.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import com.infolion.platform.dpframework.core.annotation.ValidateRule;
import com.infolion.platform.dpframework.core.domain.BaseObject;

/**
 * <pre>
 * 供应商(Supplier)实体类
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
@Table(name = "YGETLIFNR")
@IdClass(SupplierKey.class)
@org.hibernate.annotations.Entity(dynamicInsert = true, dynamicUpdate = true)
public class Supplier extends BaseObject
{
	/*
	 * 供应商或债权人的帐号
	 */
	@Id
	@Column(name = "LIFNR")
	private String lifnr;

	/*
	 * 公司代码
	 */
	@Id
	@Column(name = "BUKRS")
	@ValidateRule(dataType = 9, label = "公司代码", maxLength = 4, required = false)
	private String bukrs;

	/**
	 * @return the lifnr
	 */
	public String getLifnr()
	{
		return lifnr;
	}

	/**
	 * @param lifnr
	 *            the lifnr to set
	 */
	public void setLifnr(String lifnr)
	{
		this.lifnr = lifnr;
	}

	/**
	 * @return the bukrs
	 */
	public String getBukrs()
	{
		return bukrs;
	}

	/**
	 * @param bukrs
	 *            the bukrs to set
	 */
	public void setBukrs(String bukrs)
	{
		this.bukrs = bukrs;
	}

	// fields
	/*
	 * 客户端
	 */
	@Column(name = "MANDT")
	private String client;

	/*
	 * 名称 1
	 */
	@Column(name = "NAME1")
	@ValidateRule(dataType = 9, label = "名称 1", maxLength = 40, required = false)
	private String name1;

	/*
	 * 排序字段
	 */
	@Column(name = "SORTL")
	@ValidateRule(dataType = 9, label = "排序字段", maxLength = 10, required = false)
	private String sortl;

	/*
	 * 删除标识
	 */
	@Column(name = "LOEVM")
	@ValidateRule(dataType = 9, label = "删除标识", required = false)
	private String loevm;
	
	/*
	 * 国家代码
	 */
	@Column(name = "LAND1")
	@ValidateRule(dataType = 9, label = "国家代码", maxLength = 3, required = false)
	private String land1;
	
	/*
	 * 总帐中的统驭科目
	 */
	@Column(name = "AKONT")
	@ValidateRule(dataType = 9, label = "总帐中的统驭科目", maxLength = 10, required = false)
	private String akont;

	/**
	 * 删除标识
	 * 
	 * @return the loevm
	 */
	public String getLoevm()
	{
		return loevm;
	}

	/**
	 * 删除标识
	 * 
	 * @param loevm
	 *            the loevm to set
	 */
	public void setLoevm(String loevm)
	{
		this.loevm = loevm;
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
	 * 功能描述: 获得名称 1
	 * 
	 * @return 名称 1 : String
	 */
	public String getName1()
	{
		return this.name1;
	}

	/**
	 * 功能描述: 设置名称 1
	 * 
	 * @param name1
	 *            : String
	 */
	public void setName1(String name1)
	{
		this.name1 = name1;
	}

	/**
	 * 功能描述: 获得排序字段
	 * 
	 * @return 排序字段 : String
	 */
	public String getSortl()
	{
		return this.sortl;
	}

	/**
	 * 功能描述: 设置排序字段
	 * 
	 * @param sortl
	 *            : String
	 */
	public void setSortl(String sortl)
	{
		this.sortl = sortl;
	}

	// /**
	// * 功能描述: 获得采购组织
	// *
	// * @return 采购组织 : String
	// */
	// public String getEkorg()
	// {
	// return this.ekorg;
	// }
	//
	// /**
	// * 功能描述: 设置采购组织
	// *
	// * @param ekorg
	// * : String
	// */
	// public void setEkorg(String ekorg)
	// {
	// this.ekorg = ekorg;
	// }

	public String getLand1() {
		return land1;
	}

	public void setLand1(String land1) {
		this.land1 = land1;
	}

	public String getAkont() {
		return akont;
	}

	public void setAkont(String akont) {
		this.akont = akont;
	}

	/**
	 * 默认构造器
	 */
	public Supplier()
	{
		super();
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bukrs == null) ? 0 : bukrs.hashCode());
		result = prime * result + ((lifnr == null) ? 0 : lifnr.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Supplier other = (Supplier) obj;
		if (bukrs == null)
		{
			if (other.bukrs != null)
				return false;
		}
		else if (!bukrs.equals(other.bukrs))
			return false;
		if (lifnr == null)
		{
			if (other.lifnr != null)
				return false;
		}
		else if (!lifnr.equals(other.lifnr))
			return false;
		return true;
	}

}
