/*
 * @(#)LogonStateInterceptor.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：张崇镇
 *  时　间：2008-9-12
 *  描　述：创建
 */

package com.infolion.platform.console.sys;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.infolion.platform.console.login.web.LoginController;
import com.infolion.platform.console.sys.context.UserContext;
import com.infolion.platform.console.sys.context.UserContextHolder;
import com.infolion.platform.dpframework.uicomponent.attachement.web.AttachementController;
import com.infolion.platform.dpframework.util.StringUtils;
import com.infolion.platform.sys.Constants;

/**
 * 
 * <pre>
 * 登录状态拦截器，判断用户是否登录
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
public class LogonStateInterceptor extends HandlerInterceptorAdapter
{
	/**
	 * 方法执行之前
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception
	{
		// TODO Auto-generated method stub
		if (!handler.getClass().equals(LoginController.class)&&!handler.getClass().equals(AttachementController.class))
		{
			UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
			String queryUrl = StringUtils.isNullBlank(request.getQueryString()) ? "" : "?" + request.getQueryString();
			String requestUrl = request.getRequestURI().toString() + queryUrl;

			if (null == userContext)
			{
				request.getSession().setAttribute(Constants.RE_LONGIN_URL, requestUrl);
				request.getRequestDispatcher("/login.jsp").forward(request, response);
				return false;
			}
		}
		// 加入用户管理之前，返回true
		return true;
	}
}
