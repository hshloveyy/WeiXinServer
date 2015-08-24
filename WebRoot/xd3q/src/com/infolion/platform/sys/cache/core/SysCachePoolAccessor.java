/*
 * @(#)SysCachePoolAccessor.java
 * 版权声明 ：福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：张崇镇
 *  时　间：2008-11-30
 *  描　述：创建
 */
package com.infolion.platform.sys.cache.core;

/**
 * 
 * <pre>
 * 系统缓存池访问接口
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
public interface SysCachePoolAccessor {
	void addCustomCachedObj(SysCachePoolXd sysCachePool);

	void removeCustomCacheObj(SysCachePoolXd sysCachePool);

	String getBeanName();
}
