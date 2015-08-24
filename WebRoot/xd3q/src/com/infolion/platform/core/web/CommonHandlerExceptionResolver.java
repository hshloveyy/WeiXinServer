/*
 * @(#)CommonHandlerExceptionResolver.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：张崇镇
 *  时　间：2008-10-9
 *  描　述：创建
 */

package com.infolion.platform.core.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.infolion.platform.dpframework.core.BusinessException;
import com.infolion.platform.dpframework.core.CanRedirectedException;
import com.infolion.platform.dpframework.core.DataFormatException;
import com.infolion.platform.dpframework.core.ExceptionPostHandle;
import com.infolion.platform.dpframework.language.LangConstants;
import com.infolion.platform.dpframework.language.LanguageService;
import com.infolion.platform.dpframework.uicomponent.systemMessage.SysMsgConstants;
import com.infolion.platform.dpframework.util.StringUtils;

/**
 * 
 * <pre>
 * 异常统一处理器
 * 根据不同的异常类型，向客户端输出不同的错误展现方式
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
public class CommonHandlerExceptionResolver implements HandlerExceptionResolver
{
	private Log log = LogFactory.getLog(getClass());

	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
	{
		log.warn("Handle exception : " + ex.getClass().getName());
		ex.printStackTrace();
		String message;

		// 数据格式校验异常
		if (ex instanceof DataFormatException
				|| ex instanceof com.infolion.platform.core.DataFormatException)
		{
			message = ex.getLocalizedMessage();
			String fieldName = "";
			if (StringUtils.isNullBlank(message))
			{
				fieldName = "";
				message = "";
				ExceptionPostHandle.generateErrorMessage(response, message);
			}
			else
			{
				fieldName = message.substring(0, message.indexOf("|"));
				message = message.substring(message.indexOf("|") + 1, message.length());
				ExceptionPostHandle.generateInfoFocusMessage(response, fieldName, message);
			}
			// ExceptionPostHandle.generateInfoMessage(response, message);
		}
		else if (ex instanceof BusinessException)
		{
			BusinessException be = (BusinessException) ex;
			String messageLevel = SysMsgConstants.WARN;
			if (be.getSystemMessage() != null)
			{
				message = be.getSystemMessage().getMessageInfo();
				messageLevel = be.getSystemMessage().getMessageLevel();
			}
			else
			{
				message = ex.getLocalizedMessage();
				messageLevel = SysMsgConstants.ERROR;
			}
			if (be.getCause() != null)
			{
				message += "  "
						+ LanguageService.getText(LangConstants.SYS_CAUSE)
						+ "：" + be.getCause().getMessage();
			}
			// 如果是可重定向的异常，则直接输出错误信息
			if (ex instanceof CanRedirectedException)
			{
				request.setAttribute("message", message);
				request.setAttribute("messageLevel", messageLevel);
				return new ModelAndView("error");
			}
			else
			{
				if (SysMsgConstants.INFO.equals(messageLevel))
				{
					ExceptionPostHandle.generateInfoMessage(response, message);
				}
				else if (SysMsgConstants.ERROR.equals(messageLevel))
				{
					ExceptionPostHandle.generateErrorMessage(response, message);
				}
				else
				{
					ExceptionPostHandle.generateWarnMessage(response, message);
				}
			}
		}
		else if (ex instanceof com.infolion.platform.core.BusinessException)
		{
			message = ex.getLocalizedMessage();
			ExceptionPostHandle.generateErrorMessage(response, message);
		}
		// else if (ex instanceof SessionTimeOutException)
		// {
		// message = ex.getLocalizedMessage();
		// request.setAttribute("message", message);
		// request.setAttribute("messageLevel", SysMsgConstants.ERROR);
		// return new ModelAndView("error");
		// }
		else
		{
			message = "系统错误！错误消息：(" + ex.getLocalizedMessage() + "),请联系管理员。";
			ExceptionPostHandle.generateErrorMessage(response, message);
			// request.setAttribute("message", message);
			// request.setAttribute("messageLevel", SysMsgConstants.ERROR);
			// return new ModelAndView("error");
		}

		return null;
	}

}
