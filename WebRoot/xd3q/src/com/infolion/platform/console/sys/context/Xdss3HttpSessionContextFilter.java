/*
 * @(#)HttpSessionContextFilter.java
 * 版权声明： 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：张崇镇
 *  时　间：2008-12-4
 *  描　述：创建
 */

package com.infolion.platform.console.sys.context;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.infolion.platform.basicApp.authManage.domain.UserContext;
import com.infolion.platform.dpframework.core.Constants;

/**
 * 
 * <pre>
 * HttpSessionContext过滤器
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
public class Xdss3HttpSessionContextFilter implements Filter
{

	private Log log = (Log) LogFactory.getLog(this.getClass());

	private static final String FILTER_APPLIED = "__session_context_filter_applied";

	public void destroy()
	{

	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException
	{
		// 保证该过滤器在一次请求中只被调用一次
		if (request != null && request.getAttribute(FILTER_APPLIED) != null)
		{
			chain.doFilter(request, response);
		}
		else
		{
			request.setAttribute(FILTER_APPLIED, Boolean.TRUE);
			HttpServletRequest httpServletRequest = (HttpServletRequest) request;

			// BDP 用户上下文
			UserContext userContext = (UserContext) httpServletRequest.getSession(true).getAttribute(Constants.USER_CONTEXT_NAME);
			// 信达旧系统 用户上下文
			com.infolion.platform.console.sys.context.UserContext xdssUserContext = (com.infolion.platform.console.sys.context.UserContext) httpServletRequest.getSession(true).getAttribute(com.infolion.platform.sys.Constants.USER_CONTEXT_NAME);
			if (userContext != null)
			{
				String path = "http://" + httpServletRequest.getServerName() + (httpServletRequest.getServerPort() == 80 ? "" : ":" + httpServletRequest.getServerPort()) + httpServletRequest.getContextPath();
				userContext.setUserDefineParameter(UserContext.WEB_APP_PATH, path);
			}
			com.infolion.platform.basicApp.authManage.UserContextHolder.setCurrentContext(userContext);
			com.infolion.platform.console.sys.context.UserContextHolder.setCurrentContext(xdssUserContext);
			chain.doFilter(request, response);
		}
	}

	public void init(FilterConfig chain) throws ServletException
	{

	}

}
