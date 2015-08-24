/*
 * @(#)DataType.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：张崇镇
 *  时　间：2008-10-7
 *  描　述：创建
 */

package com.infolion.platform.core.validation;

/**
 * 
 * <pre>
 * 数据类型
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
public class DataType
{
	// 数值类型，支持标准数据表示形式，如number(4,2),number(4)等。
	public final static int NUMBER = 0;
	// email
	public final static int EMAIL = 1;
	// 电话号码
	public final static int TELEPHONE = 2;
	// 邮编
	public final static int POSTCODE = 3;
	// url
	public final static int URL = 4;
	// 英文字符
	public final static int ENGLISH_CHAR = 5;
	// 中文字符
	public final static int CHINESS_CHAR = 6;
	// 整数
	public final static int INT = 7;
	// 日期
	public final static int DATE = 8;
	// 字符串
	public final static int STRING = 9;
	// 密码
	public final static int PASSWORD = 10;

	// 数值类型，支持标准数据表示形式，如number(4,2),number(4)等。
	public final static String NUMBER_LABEL = "数值类型";
	// email
	public final static String EMAIL_LABEL = "email";
	// 电话号码
	public final static String TELEPHONE_LABEL = "电话号码";
	// 邮编
	public final static String POSTCODE_LABEL = "邮编";
	// url
	public final static String URL_LABEL = "url";
	// 英文字符
	public final static String ENGLISH_CHAR_LABEL = "英文字符";
	// 中文字符
	public final static String CHINESS_CHAR_LABEL = "中文字符";
	// 整数
	public final static String INT_LABEL = "整数";
	// 日期
	public final static String DATE_LABEL = "日期";
	// 字符串
	public final static String STRING_LABEL = "字符串";
	// 密码
	public final static String PASSWORD_LABEL = "密码";
}
