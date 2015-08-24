/*
 * @(#)SysInitException.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：张崇镇
 *  时　间：2008-9-11
 *  描　述：创建
 */

package com.infolion.platform.sys;

/**
 * 
 * <pre>
 * 系统初始化异常
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
public class SysInitException extends RuntimeException
{

	public SysInitException()
	{
	}

	public SysInitException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public SysInitException(String message)
	{
		super(message);
	}

	public SysInitException(Throwable cause)
	{
		super(cause);
	}

}
