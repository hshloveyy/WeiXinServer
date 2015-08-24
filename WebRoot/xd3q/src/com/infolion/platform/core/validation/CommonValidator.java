/*
 * @(#)CommonValidator.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：张崇镇
 *  时　间：2008-10-8
 *  描　述：创建
 */

package com.infolion.platform.core.validation;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

import com.infolion.platform.core.DataFormatException;
import com.infolion.platform.core.annotation.ValidateRule;

/**
 * 
 * <pre>
 * 通用校验器
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
public class CommonValidator
{
	/**
	 * 校验javabean
	 * 
	 * @param obj
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 */
	public static String validate(Object obj) throws IllegalAccessException,
			InvocationTargetException, NoSuchMethodException, SecurityException
	{
		String errMsg = null;

		Map fields = BeanUtils.describe(obj);
		for (Iterator iterator = fields.keySet().iterator(); iterator.hasNext();)
		{
			String fieldName = (String) iterator.next();
			String fieldValue = (String) fields.get(fieldName);
			Field field = null;
			try
			{
				field = obj.getClass().getDeclaredField(fieldName);
			} catch (NoSuchFieldException e)
			{
				continue;
			}
			ValidateRule rule = field.getAnnotation(ValidateRule.class);
			if (null != rule)
			{
				if ((fieldValue == null||"".equals(fieldValue)) && rule.required())
				{
					errMsg = fieldName + "|[" + rule.label() + "]不允许为空。";
					throw new DataFormatException(errMsg);
				} else if (fieldValue == null && !rule.required())
				{
					continue;
				} else if (fieldValue != null
						&& rule.maxLength() < fieldValue.length())
				{
					errMsg = fieldName + "|[" + rule.label() + "]的最大长度为"
							+ rule.maxLength() + "，实际长度已超过。";
					throw new DataFormatException(errMsg);
				} else if (fieldValue != null
						&& rule.minLength() > fieldValue.length())
				{
					errMsg = fieldName + "|[" + rule.label() + "]的最小长度为"
							+ rule.maxLength() + "，实际长度太小。";
					throw new DataFormatException(errMsg);
				} else if (fieldValue != null && !rule.maxValue().equals("max"))
				{
					float maxvalue = 0;
					try
					{
						maxvalue = Float.parseFloat(rule.maxValue());
					} catch (NumberFormatException ex)
					{
						errMsg = fieldName + "|[" + fieldName
								+ "]设置了最大值限制，但该值包含了非数字字符，请检查。";
						throw new DataFormatException(errMsg);
					}
					float fvalue = 0;
					try
					{
						fvalue = Float.parseFloat(fieldValue);
					} catch (NumberFormatException ex)
					{
						errMsg = fieldName + "|[" + rule.label()
								+ "]包含了非数字字符，请检查。";
						throw new DataFormatException(errMsg);
					}
					if (maxvalue < fvalue)
					{
						errMsg = fieldName + "|[" + rule.label() + "]的值比规定的最大值"
								+ rule.maxValue() + "大。";
						throw new DataFormatException(errMsg);
					}
				} else if (fieldValue != null && !rule.minValue().equals("min"))
				{
					float minvalue = 0;
					try
					{
						minvalue = Float.parseFloat(rule.minValue());
					} catch (NumberFormatException ex)
					{
						errMsg = fieldName + "|[" + fieldName
								+ "]设置了最小值限制，但该值包含了非数字字符，请检查。";
						throw new DataFormatException(errMsg);
					}
					float fvalue = 0;
					try
					{
						fvalue = Float.parseFloat(fieldValue);
					} catch (NumberFormatException ex)
					{
						errMsg = fieldName + "|[" + rule.label()
								+ "]包含了非数字字符，请检查。";
						throw new DataFormatException(errMsg);
					}
					if (minvalue > fvalue)
					{
						errMsg = fieldName + "|[" + rule.label() + "]的值比规定的最小值"
								+ rule.minValue() + "小。";
						throw new DataFormatException(errMsg);
					}
				} else if (fieldValue != null
						&& rule.minLength() > fieldValue.length())
				{
					errMsg = fieldName + "|[" + rule.label() + "]的最小长度为"
							+ rule.maxLength() + "，实际长度太小。";
					throw new DataFormatException(errMsg);
				}

				Validator validator = ValidatorFactory.buildValidator(rule
						.dataType());
				if (null == validator){
					errMsg = fieldName + "|配置的数据类型不存在校验器，请检查。";
					throw new DataFormatException(errMsg);
				}
				errMsg = validator.checkField(fieldName, fieldValue, rule
						.label());

			}
		}
		return errMsg;
	}
}
