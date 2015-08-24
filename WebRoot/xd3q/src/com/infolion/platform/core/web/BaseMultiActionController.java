/*
 * @(#)BaseMultiActionController.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：张崇镇
 *  时　间：2008-9-9
 *  描　述：创建
 */

package com.infolion.platform.core.web;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.infolion.platform.core.ExceptionPostHandle;
import com.infolion.platform.core.validation.CommonValidator;

/**
 * 
 * <pre>
 * 控制器基类
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
public class BaseMultiActionController extends MultiActionController
{

	protected Log log = (Log) LogFactory.getLog(this.getClass());

	 @Autowired
	private ResourceBundleMessageSource messageSource;
	private boolean needValidated = true;

	/**
	 * 重写父类方法，在绑定时进行数据校验
	 */
	@Override
	protected void bind(HttpServletRequest request, Object obj)
			throws Exception
	{
		super.bind(request, obj);
		if (needValidated)
			CommonValidator.validate(obj);
//		ExtBeanUtils.beanUrlValueEncode(obj);
	}

	/**
	 * 操作失败
	 * 
	 * @param response
	 * @throws IOException
	 */
	protected void operateFailly(HttpServletResponse response)
			throws IOException
	{
		ExceptionPostHandle.generateInfoMessage(response, "操作失败");
	}

	/**
	 * 操作成功信息
	 * 
	 * @param response
	 * @throws IOException
	 */
	protected void operateSuccessfully(HttpServletResponse response)
			throws IOException
	{
		ExceptionPostHandle.generateInfoMessage(response, "操作成功");
	}

	/**
	 * 包含json
	 * 
	 * @param response
	 * @param customStr
	 * @throws IOException
	 */
	protected void operateSuccessfullyWithString(HttpServletResponse response,String customStr)
			throws IOException
	{
		ExceptionPostHandle.generateCustomsMessage(response, "操作成功", customStr);
	}

	/**
	 * 操作成功div信息
	 * 
	 * @param response
	 * @throws IOException
	 */
	protected void operateSuccessfullyDivMsg(HttpServletResponse response)
			throws IOException
	{
		ExceptionPostHandle.generateInfoDivMessage(response, "操作成功");
	}

	public void setMessageSource(ResourceBundleMessageSource messageSource)
	{
		this.messageSource = messageSource;
	}

	public void setNeedValidated(boolean needValidated)
	{
		this.needValidated = needValidated;
	}
}
