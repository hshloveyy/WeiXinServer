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
 * 客户(Customer)主键
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
public class CustomerKey implements java.io.Serializable
{
	/*
	 * 客户编号1
	 */
	@Column(name = "KUNNR")
	private String kunnr;

	/*
	 * 公司代码
	 */
	@Column(name = "BUKRS")
	@ValidateRule(dataType = 9, label = "公司代码", maxLength = 4, required = false)
	private String bukrs;

	/**
	 * 客户编号1
	 * 
	 * @return the kunnr
	 */
	public String getKunnr()
	{
		return kunnr;
	}

	/**
	 * 
	 * 客户编号1 
	 * @param kunnr
	 *            the kunnr to set
	 */
	public void setKunnr(String kunnr)
	{
		this.kunnr = kunnr;
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

	public CustomerKey(String kunnr, String bukrs) {
		super();
		this.kunnr = kunnr;
		this.bukrs = bukrs;
	}

	public CustomerKey() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bukrs == null) ? 0 : bukrs.hashCode());
		result = prime * result + ((kunnr == null) ? 0 : kunnr.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CustomerKey other = (CustomerKey) obj;
		if (bukrs == null) {
			if (other.bukrs != null)
				return false;
		} else if (!bukrs.equals(other.bukrs))
			return false;
		if (kunnr == null) {
			if (other.kunnr != null)
				return false;
		} else if (!kunnr.equals(other.kunnr))
			return false;
		return true;
	}

	

}
