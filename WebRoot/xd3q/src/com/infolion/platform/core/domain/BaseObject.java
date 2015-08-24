/*
 * @(#)BaseObject.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：张崇镇
 *  时　间：2008-9-9
 *  描　述：创建
 */
package com.infolion.platform.core.domain;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.infolion.platform.core.validation.CommonValidator;

/**
 * 
 * <pre>
 * 所有领域对象的基类
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
public class BaseObject implements Serializable {
	/**
	 * 校验实体对象
	 * 
	 * @return 校验信息
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	public String validateMe() throws SecurityException,
			IllegalAccessException, InvocationTargetException,
			NoSuchMethodException {
		return CommonValidator.validate(this);
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE, false, this.getClass());
	}

	public boolean equals(Object o) {
		return EqualsBuilder.reflectionEquals(this, o);
	}
}