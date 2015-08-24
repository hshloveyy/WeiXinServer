/*
 * @(#)CacheItemManagerAdapter.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：张崇镇
 *  时　间：2008-11-30
 *  描　述：创建
 */
package com.infolion.platform.sys.cache.core;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * 
 * <pre>
 * 缓存项目管理适配器
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
public class CacheItemManagerAdapter implements CacheItemManager {
	protected SysCachePoolXd sysCachePoolXd;

	@Autowired
	protected void setSysCachePool(SysCachePoolXd sysCachePoolXd)
	{
		this.sysCachePoolXd = sysCachePoolXd;
	}

	public void init() {
	}

	public void refresh(Set<String> items) {
	}

	public void refreshAll() {
	}

	public void init(Set<String> items) {
	}
}
