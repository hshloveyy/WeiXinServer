/*
 * @(#)SysCachePool.java
 * 版权声明 ：福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：张崇镇
 *  时　间：2008-11-30
 *  描　述：创建
 */
package com.infolion.platform.sys.cache.core;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.infolion.platform.sys.cache.SysCachePoolUtils;
import com.infolion.platform.sys.cache.event.AskForInitEvent;
import com.infolion.platform.sys.cache.event.AskForRefreshEvent;
import com.infolion.platform.sys.cache.event.SysCachePoolRefreshAdapter;

/**
 * 
 * <pre>
 * 系统缓存池
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
 @Component
public class SysCachePoolXd extends SysCachePoolRefreshAdapter {
	/**
	 * 核心缓存的缓存名
	 */
	private static final String CORE_CACHE_NAME = "coreCache";

	/**
	 * 自定义缓存的缓存名
	 */
	private static final String CUSTOM_CACHE_NAME = "customCache";

	private Cache coreCache; // 自定义缓存

	private Cache customCache; // 自定义缓存

	/**
	 * 键为缓存项目的类型，参见{@link  }
	 */
	protected Map<Integer, CacheItemManager> cacheItemManagers = new HashMap<Integer, CacheItemManager>();

	protected Log log = LogFactory.getLog(this.getClass());

	/**
	 * 注入缓存器，并获取核心缓存和自定义缓存
	 * 
	 * @param cacheManager
	 */
	@Autowired
	public void setCacheManager(CacheManager cacheManager) {
		this.coreCache = cacheManager.getCache(CORE_CACHE_NAME);
		this.customCache = cacheManager.getCache(CUSTOM_CACHE_NAME);
	}

	@Autowired
	public void setSysParameterCacheManager(
			SysParameterCacheManagerXd sysParameterCacheManager) {
		sysParameterCacheManager.setSysCachePool(this);
		cacheItemManagers
				.put(CacheItemType.SYS_PARAM, sysParameterCacheManager);
	}

	@Autowired
	public void setDictTableCacheManager(
			DictTableCacheManagerXd dictTableCacheManager) {
		dictTableCacheManager.setSysCachePool(this);
		cacheItemManagers.put(CacheItemType.DICT_TABLE, dictTableCacheManager);
	}

	public void setCustomCacheBeanManager(
			CustomCacheBeanManager customCacheBeanManager) {
		customCacheBeanManager.setSysCachePool(this);
		cacheItemManagers.put(CacheItemType.CUSTOM_BEAN_ITEM,
				customCacheBeanManager);
	}

	/**
	 * 在SysCachePool实例化就绪后，自动调用这个方法，以初始化缓存池的数据
	 */
	public void init() {
		// 首先初始化系统参数缓存
		cacheItemManagers.get(CacheItemType.SYS_PARAM).init();
		cacheItemManagers.get(CacheItemType.DICT_TABLE).init();
		CacheItemManager customCacheItemManager = cacheItemManagers.get(CacheItemType.CUSTOM_BEAN_ITEM);
		if (null != customCacheItemManager)
			customCacheItemManager.init();
		SysCachePoolUtils.setSysCachePool(this);
	}

	/**
	 * 刷新所有的缓存
	 */
	public void refresh() {
		// 首先初始化系统参数缓存
		cacheItemManagers.get(CacheItemType.SYS_PARAM).refreshAll();

		// 接着初始化其它的缓存
		Set<Integer> keys = cacheItemManagers.keySet();
		for (Integer key : keys) {
			if (!key.equals(CacheItemType.SYS_PARAM)) {
				cacheItemManagers.get(key).refreshAll();
			}
		}
	}

	/**
	 * 添加自定义缓存数据
	 * 
	 * @param key
	 * @param value
	 */
	public void putCustomValue(String key, Object value) {
		Element elem = new Element(key, value);
		this.customCache.put(elem);
	}

	/**
	 * 删除自定义缓存数据
	 * 
	 * @param key
	 */
	public void removeCustomValue(String key) {
		this.customCache.remove(key);
	}

	/**
	 * 向核心缓存添加缓存对象
	 * 
	 * @param key
	 * @param value
	 */
	public void putCoreCacheElem(String key, Object value) {
		Element elem = new Element(key, value);
		this.coreCache.put(elem);
	}

	/**
	 * 从核心缓存删除一个缓存对象
	 * 
	 * @param key
	 */
	public void removeCoreCacheElem(String key) {
		this.coreCache.remove(key);
	}

	/**
	 * 获取用于存放自定义数据的缓存对象
	 * 
	 * @return
	 */
	public Cache getCustomCache() {
		return customCache;
	}

	/**
	 * 获取核心缓存对象
	 * 
	 * @return
	 */
	public Cache getCoreCache() {
		return coreCache;
	}

	/**
	 * 响应{@link AskForRefreshEvent}事件，对特定的缓存内容进行刷新
	 */
	@Override
	public void onAskForRefreshEvent(AskForRefreshEvent afre) {
		Map<Integer, Set<String>> items = afre.getRefreshItems();
		synchronized (this)// 防止多线程同时操作缓存
		{
			if (items == null || items.size() == 0)// 刷新所有的缓存
			{
				refresh();
			} else {
				// 有针对性地刷新特定的缓存
				Set<Integer> keys = items.keySet();
				for (Integer key : keys) {
					cacheItemManagers.get(key).refresh(items.get(key));
				}
			}
		}
	}

	/**
	 * 响应{@link AskForRefreshEvent}事件，对特定的缓存内容进行刷新
	 */
	@Override
	public void onAskForInitEvent(AskForInitEvent afie) {
		Set<String> items = new HashSet<String>();
		items.add(afie.getCacheManagerName());
		cacheItemManagers.get(afie.getCacheItemType()).init(items);
	}

	public void setCacheItemManagers(
			Map<Integer, CacheItemManager> cacheItemManagers) {
		this.cacheItemManagers = cacheItemManagers;
	}
}
