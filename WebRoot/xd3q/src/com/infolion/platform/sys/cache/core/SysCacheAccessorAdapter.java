/*
 * @(#)SysCacheAccessorAdapter.java
 * 版权声明 ：福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：张崇镇
 *  时　间：2008-11-30
 *  描　述：创建
 */
package com.infolion.platform.sys.cache.core;

import org.springframework.beans.factory.BeanNameAware;

/**
 * 
 * <pre>
 * 系统缓存访问适配器
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
public abstract class SysCacheAccessorAdapter implements SysCachePoolAccessor,
		BeanNameAware {
	/**
	 * 该缓存器访问器在Spring中配置的Bean名称
	 */
	protected String beanName;

	public abstract void addCustomCachedObj(SysCachePoolXd sysCachePool);

	public abstract void removeCustomCacheObj(SysCachePoolXd sysCachePool);

	/**
	 * 将缓存器访问器的Bean名字自动设置进来
	 */
	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}

	/**
	 * 获取Bean的名称
	 * 
	 * @return
	 */
	public String getBeanName() {
		return beanName;
	}
}
