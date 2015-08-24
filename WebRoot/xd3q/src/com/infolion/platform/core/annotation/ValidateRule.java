/*
 * @(#)ValudateRule.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：张崇镇
 *  时　间：2008-10-7
 *  描　述：创建
 */

package com.infolion.platform.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.infolion.platform.core.validation.DataType;

/**
 * 
 * <pre>
 * 属性校验规则元注释类
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
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD })
public @interface ValidateRule
{
	/**
	 * 属性标题
	 * 
	 * @return
	 */
	String label() default "";

	/**
	 * 允许的最大长度
	 * 
	 * @return
	 */
	int maxLength() default 100;

	/**
	 * 最小长度
	 * 
	 * @return
	 */
	int minLength() default 0;

	/**
	 * 最大值
	 * 
	 * @return
	 */
	String maxValue() default "max";

	/**
	 * 最小值
	 * 
	 * @return
	 */
	String minValue() default "min";

	/**
	 * 数据类型
	 * 
	 * @return
	 */
	int dataType() default DataType.STRING;

	/**
	 * 是否必须（不允许为空）
	 * 
	 * @return
	 */
	boolean required() default false;

	/**
	 * 正则表达式（日期类型可通过date(yyyy-mm-dd HH:Mi:SS)方式指定）
	 * 
	 * @return
	 */
	String pattern() default "";

}
