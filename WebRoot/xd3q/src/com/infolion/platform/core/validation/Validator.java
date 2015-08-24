/*
 * @(#)Validator.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：张崇镇
 *  时　间：2008-10-8
 *  描　述：创建
 */

package com.infolion.platform.core.validation;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;

import com.infolion.platform.core.DataFormatException;

/**
 * 
 * <pre>
 * 通用校验器抽象类
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
public abstract class Validator
{
	public String checkForm(Object obj, String fieldName, String fieldLabel)
			throws IllegalAccessException, InvocationTargetException,
			NoSuchMethodException
	{
		String field = BeanUtils.getProperty(obj, fieldName);
		return checkField(null, field, fieldLabel);
	}

	protected abstract String checkField(String fieldName, String fieldValue,
			String fieldLabel) throws DataFormatException;
}
