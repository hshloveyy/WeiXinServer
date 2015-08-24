/*
 * @(#)ExtBeanUtils.java
 * 版权声明： 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：张崇镇
 *  时　间：2009-2-18
 *  描　述：创建
 */

package com.infolion.platform.util;

import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.PropertyUtils;

/**
 * 
 * <pre>
 * 扩展的bean工具
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
public class ExtBeanUtils {
	/**
	 * 对bean中采用js escape编码的值进行解码
	 * 
	 * @param bean
	 */
	public static void beanUrlValueEncode(Object bean) {
		try {
			Map properitsMap = BeanUtils.describe(bean);
			Set properitsSet = properitsMap.keySet();
			for (Iterator iterator = properitsSet.iterator(); iterator
					.hasNext();) {
				String properite = (String) iterator.next();				
				Class propertyType = PropertyUtils.getPropertyType(bean, properite);
				if (propertyType.getName().equals("java.lang.String")) {
					String properiteValue = (String)properitsMap.get(properite);
					if (null != properiteValue && !"".equals(properiteValue)) {
						properiteValue = StringUtils.urlEncode(properiteValue);						
						BeanUtils.setProperty(bean, properite, properiteValue);
					}
				}
			}
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
