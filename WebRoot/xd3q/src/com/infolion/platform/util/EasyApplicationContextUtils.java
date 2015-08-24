/*
 * @(#)DBConnectionUtils.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：张崇镇
 *  时　间：2008-9-9
 *  描　述：创建
 */
package com.infolion.platform.util;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.util.Assert;

/**
 * 
 * <pre>
 * 在Spring容器启动时，框架会将ApplicationContext的引用设置到EasyApplicationContextUtils中
 * 这样就可以直接使用该工具类从Spring中获取Bean了。
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
public class EasyApplicationContextUtils
{

	private static ApplicationContext ctx;
	private static final Log logger = LogFactory
			.getLog(EasyApplicationContextUtils.class);

	public static void setApplicationContext(ApplicationContext ctx)
	{
		EasyApplicationContextUtils.ctx = ctx;
	}

	/**
	 * 从Spring容器中获取类型为clazz的Bean。 Spring容器中必须包含且只包含一个该类型的Bean， 否则返回null
	 * 
	 * @param clazz
	 *            Bean的类型
	 * @return Bean实例
	 */
	public static Object getBeanByType(Class clazz)
	{
		Assert.notNull(ctx, "必须在初始化Spring容器时设置好ApplicationContext");
		Map map = ctx.getBeansOfType(clazz);
		if (map.size() == 1)
		{
			Object obj = null;
			for (Object o : map.keySet())
			{
				obj = map.get(o);
			}
			return obj;
		} else
		{
			if (map.size() == 0)
			{
				logger.error("在Spring容器中没有类型为" + clazz.getName() + "的Bean");
			} else
			{
				logger.error("在Spring容器中有多个类型为" + clazz.getName() + "的Bean");
			}
			return null;
		}
	}

	/**
	 * 从Spring容器中获取Bean
	 * 
	 * @param beanName
	 *            Bean的名称
	 * @return Bean
	 */
	public static Object getBeanByName(String beanName)
	{

		if (StringUtils.isEmpty(beanName))
			return null;

		Assert.notNull(ctx, "必须在初始化Spring容器时设置好ApplicationContext");

		try
		{
			return ctx.getBean(beanName);
		} catch (BeansException e)
		{
			return null;
		}
	}

	/**
	 * 获取Spring容器的引用
	 * 
	 * @return
	 */
	public static ApplicationContext getApplicationContext()
	{
		return ctx;
	}
}
