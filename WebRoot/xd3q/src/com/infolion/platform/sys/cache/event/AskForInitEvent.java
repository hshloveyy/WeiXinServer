/*
 * @(#)AskForInitEvent.java
 * 版权声明  福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：张崇镇
 *  时　间：2008-12-1
 *  描　述：创建
 */
package com.infolion.platform.sys.cache.event;

import org.springframework.context.ApplicationEvent;

import com.infolion.platform.sys.cache.core.CacheItemType;

/**
 * 
 * <pre>
 * 请求执行初始化动作事件
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
public class AskForInitEvent extends ApplicationEvent {
	private int cacheItemType;
	private String cacheManagerName;

	public String getCacheManagerName() {
		return cacheManagerName;
	}

	/**
	 * 
	 * 
	 * @param source
	 *            发起事件的源
	 * @param cacheItemType
	 *            缓存项目类型，参见{@link CacheItemType}，一般情况下 只为
	 *            {@link CacheItemType#CUSTOM_BEAN_ITEM}或者
	 *            {@link CacheItemType#CUSTOM_CLASS_ITEM}
	 */
	public AskForInitEvent(Object source, int cacheItemType,
			String cacheManagerName) {
		super(source);
		this.cacheItemType = cacheItemType;
		this.cacheManagerName = cacheManagerName;
	}

	/**
	 * 获取需要初始化的缓存项目类型
	 * 
	 * @return
	 */
	public int getCacheItemType() {
		return cacheItemType;
	}

}
