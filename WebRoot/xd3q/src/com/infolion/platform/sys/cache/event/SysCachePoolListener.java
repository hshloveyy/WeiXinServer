/*
 * @(#)SysCachePoolListener.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：张崇镇
 *  时　间：2008-12-1
 *  描　述：创建
 */
package com.infolion.platform.sys.cache.event;

/**
 * 
 * <pre>
 * 系统缓存池监听器
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
public interface SysCachePoolListener {
	/**
	 * 响应AskForRefreshEvent事件，并完成刷新相应缓存项目的动作
	 * 
	 * @param rre
	 *            请求缓存刷新事件
	 */
	void onAskForRefreshEvent(AskForRefreshEvent cre);

	/**
	 * 响应AskForInitEvent事件，并完成初始化相应缓存项目的动作
	 * 
	 * @param afie
	 *            请求初始化事件
	 */
	void onAskForInitEvent(AskForInitEvent afie);
}
