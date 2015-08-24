/*
 * @(#)FundFlowPay.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年07月01日 15点05分53秒
 *  描　述：创建
 */
package com.infolion.xdss3.billClear.domain;

import javax.persistence.Transient;

import com.infolion.platform.dpframework.core.domain.BaseObject;

/**
 * <pre>
 * 纯资金往来(FundFlow)实体类
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
public class FundFlow extends BaseObject
{

	/*
	 * 客户
	 */
	@Transient
	private String customer1_htext;

	/*
	 * 客户
	 */
	@Transient
	private String customer2_htext;

	/*
	 * 客户
	 */
	@Transient
	private String customer3_htext;

	/*
	 * 业务范围
	 */
	@Transient
	private String businessscope1_htext;

	/*
	 * 业务范围
	 */
	@Transient
	private String businessscope2_htext;

	/*
	 * 业务范围
	 */
	@Transient
	private String businessscope3_htext;

	/**
	 * @return the customer1_htext
	 */
	public String getCustomer1_htext()
	{
		return customer1_htext;
	}

	/**
	 * @param customer1_htext
	 *            the customer1_htext to set
	 */
	public void setCustomer1_htext(String customer1_htext)
	{
		this.customer1_htext = customer1_htext;
	}

	/**
	 * @return the customer2_htext
	 */
	public String getCustomer2_htext()
	{
		return customer2_htext;
	}

	/**
	 * @param customer2_htext
	 *            the customer2_htext to set
	 */
	public void setCustomer2_htext(String customer2_htext)
	{
		this.customer2_htext = customer2_htext;
	}

	/**
	 * @return the customer3_htext
	 */
	public String getCustomer3_htext()
	{
		return customer3_htext;
	}

	/**
	 * @param customer3_htext
	 *            the customer3_htext to set
	 */
	public void setCustomer3_htext(String customer3_htext)
	{
		this.customer3_htext = customer3_htext;
	}

	/**
	 * @return the businessscope1_htext
	 */
	public String getBusinessscope1_htext()
	{
		return businessscope1_htext;
	}

	/**
	 * @param businessscope1_htext
	 *            the businessscope1_htext to set
	 */
	public void setBusinessscope1_htext(String businessscope1_htext)
	{
		this.businessscope1_htext = businessscope1_htext;
	}

	/**
	 * @return the businessscope2_htext
	 */
	public String getBusinessscope2_htext()
	{
		return businessscope2_htext;
	}

	/**
	 * @param businessscope2_htext
	 *            the businessscope2_htext to set
	 */
	public void setBusinessscope2_htext(String businessscope2_htext)
	{
		this.businessscope2_htext = businessscope2_htext;
	}

	/**
	 * @return the businessscope3_htext
	 */
	public String getBusinessscope3_htext()
	{
		return businessscope3_htext;
	}

	/**
	 * @param businessscope3_htext
	 *            the businessscope3_htext to set
	 */
	public void setBusinessscope3_htext(String businessscope3_htext)
	{
		this.businessscope3_htext = businessscope3_htext;
	}

	/**
	 * 默认构造器
	 */
	public FundFlow()
	{
		super();
	}
}
