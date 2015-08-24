/*
 * @(#)SupplierKey.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：林进旭
 *  时　间：2010-5-25
 *  描　述：创建
 */

package com.infolion.xdss3.masterData.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import com.infolion.platform.dpframework.core.annotation.ValidateRule;

/**
 * 
 * <pre>
 * 供应商(Supplier)主键
 * </pre>
 * 
 * <br>
 * JDK版本:1.5
 * 
 * @author 林进旭
 * @version 1.0
 * @see The author for more details
 * @since 1.0
 */
@Embeddable
public class SupplierKey implements java.io.Serializable
{
	/*
	 * 供应商或债权人的帐号
	 */
	@Column(name = "LIFNR")
	private String lifnr;

	/*
	 * 公司代码
	 */
	@Column(name = "BUKRS")
	@ValidateRule(dataType = 9, label = "公司代码", maxLength = 4, required = false)
	private String bukrs;

	/**
	 * 供应商或债权人的帐号
	 * 
	 * @return the lifnr
	 */
	public String getLifnr()
	{
		return lifnr;
	}

	/**
	 * 供应商或债权人的帐号
	 * 
	 * @param lifnr
	 *            the lifnr to set
	 */
	public void setLifnr(String lifnr)
	{
		this.lifnr = lifnr;
	}

	/**
	 * 功能描述: 获得公司代码
	 * 
	 * @return 公司代码 : String
	 */
	public String getBukrs()
	{
		return this.bukrs;
	}

	/**
	 * 功能描述: 设置公司代码
	 * 
	 * @param bukrs
	 *            : String
	 */
	public void setBukrs(String bukrs)
	{
		this.bukrs = bukrs;
	}

	/**
	 * 
	 */
	public SupplierKey()
	{
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param lifnr
	 * @param bukrs
	 */
	public SupplierKey(String lifnr, String bukrs)
	{
		super();
		this.lifnr = lifnr;
		this.bukrs = bukrs;
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
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SupplierKey other = (SupplierKey) obj;
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
