/*
 * @(#)DataFormatException.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：张崇镇
 *  时　间：2008-10-8
 *  描　述：创建
 */

package com.infolion.platform.core;

/**
 * 
 * <pre>
 * 数据格式异常
 * </pre>
 * 
 * <br>
 * JDK版本:1.5
 * 
 * @author 张崇镇
 * @version 1.0
 * @see The author for more details
 * @since 1.0
 */
public class DataFormatException extends RuntimeException
{

	public DataFormatException()
	{
		super();
		// TODO Auto-generated constructor stub
	}

	public DataFormatException(String message, Throwable cause)
	{
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public DataFormatException(String message)
	{
		super(message);
		// TODO Auto-generated constructor stub
	}

	public DataFormatException(Throwable cause)
	{
		super(cause);
		// TODO Auto-generated constructor stub
	}

}
