/*
 * @(#)ValidatorFactory.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：张崇镇
 *  时　间：2008-10-8
 *  描　述：创建
 */

package com.infolion.platform.core.validation;

/**
 * 
 * <pre>
 * 校验器工厂
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
public class ValidatorFactory
{
	public static Validator buildValidator(int dataType)
	{
		switch (dataType)
		{
		case DataType.CHINESS_CHAR:
			return new ChinessCharValidator();
		case DataType.DATE:
			return new DateValidator();
		case DataType.EMAIL:
			return new EmailValidator();
		case DataType.ENGLISH_CHAR:
			return new EnglishCharValidator();
		case DataType.INT:
			return new IntValidator();
		case DataType.NUMBER:
			return new NumberValidator();
		case DataType.POSTCODE:
			return new PostcodeValidator();
		case DataType.TELEPHONE:
			return new TelephoneValidator();
		case DataType.URL:
			return new UrlValidator();
		case DataType.PASSWORD:
			return new PasswordValidator();
		case DataType.STRING:
			return new StringValidator();
		default:
			return new StringValidator();
		}

	}
}
