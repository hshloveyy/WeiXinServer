/*
 * @(#)PasswordValidator.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：张崇镇
 *  时　间：2008-10-9
 *  描　述：创建
 */

package com.infolion.platform.core.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.infolion.platform.core.DataFormatException;

/**
 * 
 * <pre>
 * 密码校验器
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
public class PasswordValidator extends Validator
{

	@Override
	protected String checkField(String fieldName, String fieldValue,
			String fieldLabel) throws DataFormatException
	{
		String parStr = "^[a-zA-Z0-9_]+$";
		Pattern pattern = Pattern.compile(parStr, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(fieldValue);
		boolean result = matcher.matches();
		
		String errMsg = null;
		if (!result)
		{
			errMsg = fieldName + "|[" + fieldLabel + "]只能由数字，英文字符和“_”组成。";
			throw new DataFormatException(errMsg);
		}
		return errMsg;
	}

}
