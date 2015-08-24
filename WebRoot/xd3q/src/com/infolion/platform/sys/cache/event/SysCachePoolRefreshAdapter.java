/*
 * @(#)SysCachePoolRefreshAdapter.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：张崇镇
 *  时　间：2008-12-1
 *  描　述：创建
 */
package com.infolion.platform.sys.cache.event;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

/**
 * 
 * <pre>
 * 系统缓存池监听适配器
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
public abstract class SysCachePoolRefreshAdapter implements
		SysCachePoolListener, ApplicationListener {
	protected Log log = LogFactory.getLog(this.getClass());

	/**
	 *处理缓存池需要的事件，可以继承该函数，以便刷新系统缓存池中的缓存信息（部分或全部）。
	 */
	public abstract void onAskForRefreshEvent(AskForRefreshEvent cre);

	/**
	 *处理缓存池需要的事件，可以继承该函数，以便刷新系统缓存池中的缓存信息（部分或全部）。
	 */
	public abstract void onAskForInitEvent(AskForInitEvent afie);

	/**
	 * 利用Spring的事件响应机制响应系统缓存池的事件
	 */
	public void onApplicationEvent(ApplicationEvent ae) {
		if (ae instanceof AskForRefreshEvent) {// 响应请求刷新的事件
			onAskForRefreshEvent((AskForRefreshEvent) ae);
		} else if (ae instanceof AskForInitEvent) {
			onAskForInitEvent((AskForInitEvent) ae);
		}
	}
}
