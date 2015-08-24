/*
 * @(#)IndexMainController.java
 * 版权声明： 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：张崇镇
 *  时　间：2009-6-12
 *  描　述：创建
 */

package com.infolion.platform.console.login.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.infolion.platform.basicApp.authManage.UserContextHolder;
import com.infolion.platform.basicApp.index.domain.PersonalInf;
import com.infolion.platform.basicApp.index.web.IndexMainController;
import com.infolion.platform.dpframework.core.Constants;

/**
 * 
 * <pre>
 * XDSS3首页控制器
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
public class Xdss3IndexMainController extends IndexMainController
{

	/**
	 * 进入主页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 * @throws IOException
	 * @throws ServletException
	 */
	public ModelAndView defaultMethod(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		Object returnUrl = request.getSession().getAttribute(Constants.RE_LONGIN_URL);
		request.getSession().removeAttribute(Constants.RE_LONGIN_URL);
		// 如果是从访问一个url进入的登录界面，则返回原来访问的url视图
		log.debug("上次访问的URL地址:" + returnUrl);

		String isOuterUrl = (String) request.getSession().getAttribute(Constants.IS_OUTER_URL);

		if (null != returnUrl && null == isOuterUrl)
		{
			request.getSession().removeAttribute(Constants.RE_LONGIN_URL);
			return new ModelAndView(new RedirectView((String) returnUrl));
		}
		if (null != isOuterUrl)
		{
			request.getSession().removeAttribute(Constants.IS_OUTER_URL);
			request.setAttribute("returnUrl", (String) returnUrl);
		}
		else
		{
			request.setAttribute("returnUrl", "");
		}

		// 取得用户个人参数设置
		String userId = UserContextHolder.getLocalUserContext().getUserContext().getUser().getUserId();
		PersonalInf personalInf = this.personalInfService.getPersonalInf(userId);
		// 1 、收藏 2、个人 3、标准
		String startMenu = StringUtils.isBlank(personalInf.getStartMenu()) ? "" : personalInf.getStartMenu().trim();
		String startTcode = StringUtils.isBlank(personalInf.getStartTcode()) ? "" : personalInf.getStartTcode().trim();

		com.infolion.platform.console.sys.context.UserContext xdssUserContext = com.infolion.platform.console.sys.context.UserContextHolder.getLocalUserContext().getUserContext();
		if (null != xdssUserContext)
			request.setAttribute("xdssUser", xdssUserContext.getSysUser());

		request.setAttribute("startMenu", startMenu);
		request.setAttribute("startTcode", startTcode);

		return new ModelAndView("main");

	}

}
