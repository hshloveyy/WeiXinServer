/*
 * @(#)CodeGenerator.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：张崇镇
 *  时　间：2008-9-9
 *  描　述：创建
 */
package com.infolion.platform.util;

import java.security.MessageDigest;
import java.util.UUID;

import org.springframework.util.Assert;

/**
 * 
 * <pre>
 * 生成各种编码
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
public class CodeGenerator
{
	/**
	 * 数英字符串
	 */
	private static String ALPHANUMERIC_STR;
	static
	{
		String numberStr = "0123456789";
		String aphaStr = "abcdefghijklmnopqrstuvwxyz";
		ALPHANUMERIC_STR = numberStr + aphaStr + aphaStr.toUpperCase();
	}

	/**
	 * 生成36个字符长度的UUID编码串，所有的字母转换为大写的格式。
	 * 
	 * @return 36个字符长度的UUID。
	 */
	public static String getUUID()
	{
		UUID uuid = UUID.randomUUID();
		return uuid.toString().toUpperCase();
	}

	/**
	 * 获取srcStr的MD5编码（十六进制表示）
	 * 
	 * @param srcStr
	 *            需要获取MD5的字符串，不能为null
	 * @return srcStr的SHA代码（40个字符）
	 */
	public static String getSHADigest(String srcStr)
	{
		return getDigest(srcStr, "SHA-1");
	}

	/**
	 * 获取srcStr的MD5编码（十六进制表示）
	 * 
	 * @param srcStr
	 *            需要获取MD5的字符串，不能为null
	 * @return srcStr的MD5代码（32个字符）
	 */
	public static String getMD5Digest(String srcStr)
	{
		return getDigest(srcStr, "MD5");
	}

	/**
	 * 产生6位英数随机数,区分大小写
	 * 
	 * @return
	 */
	public static String getUpdateKey()
	{
		return getRandomStr(6);
	}

	/**
	 * 产生一个随机英数字符串，区分大小定
	 * 
	 * @param length
	 *            随机字符串的长度
	 * @return
	 */
	public static String getRandomStr(int length)
	{
		int srcStrLen = ALPHANUMERIC_STR.length();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++)
		{
			int maxnum = (int) (Math.random() * 1000);
			int result = maxnum % srcStrLen;
			char temp = ALPHANUMERIC_STR.charAt(result);
			sb.append(temp);
		}
		return sb.toString();
	}

	private static String getDigest(String srcStr, String alg)
	{
		Assert.notNull(srcStr);
		Assert.notNull(alg);
		try
		{
			java.security.MessageDigest alga = MessageDigest.getInstance(alg);
			alga.update(srcStr.getBytes());
			byte[] digesta = alga.digest();
			return byte2hex(digesta);
		} catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}

	/**
	 * 二进制转十六进制字符串
	 * 
	 * @param b
	 * @return
	 */
	private static String byte2hex(byte[] b)
	{
		StringBuffer hs = new StringBuffer();
		String stmp = "";
		for (int n = 0; n < b.length; n++)
		{
			stmp = (Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1)
			{
				hs.append("0");
			}
			hs.append(stmp);
		}
		return hs.toString().toUpperCase();
	}

	public static void main(String[] args)
	{
		System.out.println(getUUID());
		System.out.println("---"+getMD5Digest("1"));
		// System.out.println(getSHADigest("111111"));
		System.out.println(getRandomStr(10));
		System.out.println(getRandomStr(8));
		System.out.println(getUpdateKey());
	}
}
