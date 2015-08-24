/*
 * @(#)CustomCacheBeanManager.java
 * 版权声明 ：福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：张崇镇
 *  时　间：2008-11-30
 *  描　述：创建
 */
package com.infolion.platform.sys.cache.core;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.Assert;

/**
 * 
 * <pre>
 * 自定义缓存 管理器
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
public class CustomCacheBeanManager extends CacheItemManagerAdapter {

	protected Map<String, SysCachePoolAccessor> sysCachePoolAccessorMap = new HashMap<String, SysCachePoolAccessor>();
	protected Log log = LogFactory.getLog(this.getClass());

	public void setSysCachePoolAccessors(
			List<SysCachePoolAccessor> sysCachePoolAccessors) {
		for (SysCachePoolAccessor accessor : sysCachePoolAccessors) {
			sysCachePoolAccessorMap.put(accessor.getBeanName(), accessor);
		}
	}

	/**
	 * 获取指定Bean名称的SysCachePoolAccessor
	 * 
	 * @param beanName
	 * @return
	 */
	public SysCachePoolAccessor getSysCachePoolAccessorByBeanName(
			String beanName) {
		return sysCachePoolAccessorMap.get(beanName);
	}

	/**
	 * 调用所有自定义缓存装置，初始化所有的自定义缓存数据
	 */
	public void init() {
		callAllPoolAccessor(false);
	}

	/**
	 * 调用特定的自定义缓存装置，初始化相应的缓存数据
	 */
	public void init(Set<String> beanNames) {
		callPoolAccessor(beanNames, false);
	}

	/**
	 * 刷新指定的自定义缓存访问器（以Bean的形式配置的）
	 * 
	 * @param beanNames
	 */
	public void refresh(Set<String> beanNames) {
		Assert.notNull(beanNames, "自定义缓存访问器的Bean名不能为空。");
		callPoolAccessor(beanNames, true);
	}

	/**
	 * 调用所有自定义缓存装置，刷新所有的自定义数据缓存
	 */
	public void refreshAll() {
		callAllPoolAccessor(true);
	}

	/**
	 * @param refresh
	 *            是否是刷新
	 */
	private void callAllPoolAccessor(boolean refresh) {
		String action = refresh ? "刷新" : "初始化";
		// 以Bean形式配置的自定义缓存访问器

		Set<String> beanNames = sysCachePoolAccessorMap.keySet();
		log.debug("开始执行信达一期Bean缓存管理器(" + action + ")...");
		log.debug("--------------------------------------------");
		if (beanNames.size() > 0) {
			callPoolAccessor(beanNames, refresh);
		}
		log.debug("--------------------------------------------");
		log.info("成功运行信达一期Bean缓存管理器.");
	}

	/**
	 * 调用Bean类型的缓存管理装置
	 * 
	 * @param beanNames
	 */
	private void callPoolAccessor(Set<String> beanNames, boolean isRefresh) {
		Assert.notNull(sysCachePoolXd, "必须提供系统缓存池！");
		SysCachePoolAccessor accessor = null;

		for (String beanName : beanNames) {
			try {
				System.out.println("执行Bean缓存管理器(" + beanName + ")...");
				accessor = sysCachePoolAccessorMap.get(beanName);
			} catch (Exception e) {
				log.error("在Spring容器中未发现名为" + beanName + "的Bean，请检查。");
				continue;
			}
			if (isRefresh) {
				accessor.removeCustomCacheObj(sysCachePoolXd);
			}
			accessor.addCustomCachedObj(sysCachePoolXd);
			System.out.println("done.");
		}
	}
}
