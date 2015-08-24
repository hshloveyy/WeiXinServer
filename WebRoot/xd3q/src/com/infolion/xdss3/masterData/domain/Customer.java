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
 * 客戶(Customer)实体类
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
@Table(name = "YGETKUNNR")
@IdClass(CustomerKey.class)
@org.hibernate.annotations.Entity(dynamicInsert = true, dynamicUpdate = true)
public class Customer extends BaseObject
{
	/*
	 * 客户编号1
	 */
	@Id
	@Column(name = "KUNNR")
	private String kunnr;

	/*
	 * 公司代码
	 */
	@Id
	@Column(name = "BUKRS")
	@ValidateRule(dataType = 9, label = "公司代码", maxLength = 4, required = false)
	private String bukrs;

	/**
	 * @return the kunnr
	 */
	public String getKunnr()
	{
		return kunnr;
	}

	/**
	 * @param kunnr
	 *            the kunnr to set
	 */
	public void setKunnr(String kunnr)
	{
		this.kunnr = kunnr;
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
	@ValidateRule(dataType = 9, label = "名称 1", maxLength = 35, required = false)
	private String name1;

	/*
	 * 排序字段
	 */
	@Column(name = "SORTL")
	@ValidateRule(dataType = 9, label = "排序字段", maxLength = 10, required = false)
	private String sortl;
	
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
	 * @return the name1
	 */
	public String getName1()
	{
		return name1;
	}

	/**
	 * @param name1
	 *            the name1 to set
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

	/*
	 * 主记录的集中删除标志
	 */
	@Column(name = "LOEVM")
	@ValidateRule(dataType = 9, label = "主记录的集中删除标志", maxLength = 1, required = false)
	private String loevm;

	public String getLoevm()
	{
		return loevm;
	}

	public void setLoevm(String loevm)
	{
		this.loevm = loevm;
	}

	/**
	 * 默认构造器
	 */
	public Customer()
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
		result = prime * result + ((kunnr == null) ? 0 : kunnr.hashCode());
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
		Customer other = (Customer) obj;
		if (bukrs == null)
		{
			if (other.bukrs != null)
				return false;
		}
		else if (!bukrs.equals(other.bukrs))
			return false;
		if (kunnr == null)
		{
			if (other.kunnr != null)
				return false;
		}
		else if (!kunnr.equals(other.kunnr))
			return false;
		return true;
	}

}
