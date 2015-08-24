/*
 * @(#)TradeType.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：林进旭
 *  时　间：2010-5-7
 *  描　述：创建
 */

package com.infolion.xdss3.tradeMonitoring.domain;

import javax.persistence.Column;

/**
 * <pre>
 * 贸易类别
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
public class TradeType
{

	@Column(name = "ID")
	private String tradeTypeId;

	@Column(name = "TITLE")
	private String title;

	@Column(name = "CMD")
	private String cmd;

	@Column(name = "IS_AVAILABLE")
	private String is_available;

	/**
	 * @return the tradeTypeId
	 */
	public String getTradeTypeId()
	{
		return tradeTypeId;
	}

	/**
	 * @param tradeTypeId
	 *            the tradeTypeId to set
	 */
	public void setTradeTypeId(String tradeTypeId)
	{
		this.tradeTypeId = tradeTypeId;
	}

	/**
	 * @return the title
	 */
	public String getTitle()
	{
		return title;
	}

	/**
	 * @param title
	 *            the title to set
	 */
	public void setTitle(String title)
	{
		this.title = title;
	}

	/**
	 * @return the cmd
	 */
	public String getCmd()
	{
		return cmd;
	}

	/**
	 * @param cmd
	 *            the cmd to set
	 */
	public void setCmd(String cmd)
	{
		this.cmd = cmd;
	}

	/**
	 * @return the is_available
	 */
	public String getIs_available()
	{
		return is_available;
	}

	/**
	 * @param is_available
	 *            the is_available to set
	 */
	public void setIs_available(String is_available)
	{
		this.is_available = is_available;
	}

}
