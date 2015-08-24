/*
 * @(#)UserContextHolder.java
 * 版权声明： 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：张崇镇
 *  时　间：2008-12-4
 *  描　述：创建
 */

package com.infolion.platform.basicApp.authManage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.infolion.platform.basicApp.authManage.domain.UserContext;

/**
 * 
 * <pre>
 * 用户上下文持有者
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
public class UserContextHolder
{
	private static Log log = LogFactory.getFactory().getLog(UserContextHolder.class);
	private static final ThreadLocal threadLocal = new ThreadLocal();

	private UserContext userContext;

	public UserContextHolder(UserContext userContext)
	{
		this.userContext = userContext;
	}

	/**
	 * 在局部线程变量中设置当前用户上下文
	 * 
	 * @param userContext
	 */
	public static void setCurrentContext(UserContext userContext)
	{
		UserContextHolder context = getLocalUserContext();
		if (context == null)
		{
			final UserContextHolder context1 = new UserContextHolder(userContext);
			threadLocal.set(context1);
		}
		else
		{
			// if (userContext == null)
			// threadLocal.set(null);
			// else
			context.setUserContext(userContext);
		}
	}

	/**
	 * 取得用户上下文
	 * 
	 * @return
	 */
	public UserContext getUserContext()
	{
		return userContext;
	}

	public void setUserContext(UserContext userContext)
	{
		this.userContext = userContext;
	}

	/**
	 * 取得持有者实例
	 * 
	 * @return
	 */
	public static UserContextHolder getLocalUserContext()
	{
		UserContextHolder uh = (UserContextHolder) threadLocal.get();
		if (null == uh)
		{
			log.error("出错了，系统Session失效！");
		}
		return uh;
	}

	/**
	 * 返回当前线程的用户上下文，如果当前线程没有用户上下文，返回空
	 * 
	 * @return
	 */
	public static UserContext saveGetUserContext()
	{
		UserContext userContext = null;
		UserContextHolder userContextHolder = getLocalUserContext();
		if (userContextHolder != null)
		{
			userContext = userContextHolder.getUserContext();
		}
		return userContext;
	}
}
