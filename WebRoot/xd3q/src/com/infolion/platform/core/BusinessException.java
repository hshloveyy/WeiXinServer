/*
 * @(#)BusinessException.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：张崇镇
 *  时　间：2008-9-11
 *  描　述：创建
 */

package com.infolion.platform.core;

/**
 * 
 * <pre>
 * 业务异常基类，所有业务异常都继承此类
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
public class BusinessException extends RuntimeException
{

	public BusinessException(String message, Throwable cause)
	{
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public BusinessException(String message)
	{
		super(message);
		// TODO Auto-generated constructor stub
	}

	public BusinessException(Throwable cause)
	{
		super(cause);
		// TODO Auto-generated constructor stub
	}

}
