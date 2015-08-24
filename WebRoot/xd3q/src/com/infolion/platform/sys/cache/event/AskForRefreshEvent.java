/*
 * @(#)AskForRefreshEvent.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：张崇镇
 *  时　间：2008-12-1
 *  描　述：创建
 */
package com.infolion.platform.sys.cache.event;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.context.ApplicationEvent;

import com.infolion.platform.sys.cache.core.CacheItemType;

/**
 * 
 * <pre>
 * 请求刷新系统缓存的事件，只要发布该事件，SysCachePool就会进行相应的刷新
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
public class AskForRefreshEvent extends ApplicationEvent {

	/**
	 * 需要刷新的项目,如果为空时，表示刷新所有 先加入的项目先刷新。
	 */
	protected Map<Integer, Set<String>> refreshItems = new LinkedHashMap<Integer, Set<String>>();

	public AskForRefreshEvent(Object source) {
		super(source);
	}

	/**
	 * 获取需要刷新的缓存项目，如果为空，表示要刷新所有的缓存信息
	 * 
	 * @return
	 */
	public Map<Integer, Set<String>> getRefreshItems() {
		return refreshItems;
	}

	/**
	 * 添加需要刷新的缓存信息
	 * 
	 * @param itemType
	 *            只能为{@link CacheItemType}
	 * @param itemKeys
	 *            如果是{@link CacheItemType#SYS_PARAM}，则指定要刷新的系统参数 如果是
	 *            {@link CacheItemType#DICT_TABLE}，则指定要刷新的字典表ID 如果是
	 *            {@link CacheItemType#CUSTOM_BEAN_ITEM}，则指定要刷新的Bean名称
	 */
	public void addRefreshItems(Integer itemType, Set<String> itemKeys) {
		this.refreshItems.put(itemType, itemKeys);
	}

}
