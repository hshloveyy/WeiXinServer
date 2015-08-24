/*
 * @(#)ExceptionPostHandle.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：张崇镇
 *  时　间：2008-10-10
 *  描　述：创建
 */

package com.infolion.platform.core;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.infolion.platform.util.JsonUtils;

/**
 * 
 * <pre>
 * 异常分发处理器
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
public class ExceptionPostHandle
{
	/**
	 * 提示
	 */
	public final static String INFO = "info";
	/**
	 * 提示，并移动焦点
	 */
	public final static String INFO_FOCUS_FIELD = "info_focus";
	/**
	 * 提示，并移动焦点
	 */
	public final static String INFO_DIV_MSG = "info_div_msg";
	/**
	 * 警告
	 */
	public final static String WARN = "warn";
	/**
	 * 错误
	 */
	public final static String ERROR = "error";

	/**
	 * 输出提示消息
	 * 
	 * @param response
	 * @param message
	 * @throws IOException
	 */
	public static void generateInfoMessage(HttpServletResponse response,
			String message) throws IOException
	{
		JsonUtils.sendJsonMessage(response, INFO, message);
	}
	/**
	 * 输出用户定义json
	 * @param response
	 * @param message
	 * @param customStr
	 * @throws IOException
	 */
	public static void generateCustomsMessage(HttpServletResponse response,
			String message,String customStr) throws IOException
	{
		String result = JsonUtils.generateCustomMessageJson(INFO, message,customStr);
		JsonUtils.flushResponse(response, result);
	}

	/**
	 * 输出提示消息,并把焦点移到相应元素
	 * 
	 * @param response
	 * @param message
	 * @throws IOException
	 */
	public static void generateInfoFocusMessage(HttpServletResponse response,
			String focusField, String message) throws IOException
	{
		JSONObject jsObject = new JSONObject();
		jsObject.put(JsonUtils.JSON_MESSAGE_TYPE_NODE, INFO_FOCUS_FIELD);
		jsObject.put(JsonUtils.JSON_MESSAGE_NODE, message);
		jsObject.put("field", focusField);
		JSONObject rootObject = new JSONObject();
		rootObject.put(JsonUtils.JSON_ROOT_NODE, jsObject);
		message = rootObject.toString();
		JsonUtils.flushResponse(response, message);
	}
	/**
	 * div消息输出格式　
	 * @param response
	 * @param message
	 * @throws IOException
	 */
	public static void generateInfoDivMessage(HttpServletResponse response,
			String message) throws IOException
	{
				JsonUtils.sendJsonMessage(response, INFO_DIV_MSG, message);
	}
	/**
	 * 输出警告消息
	 * 
	 * @param response
	 * @param message
	 * @throws IOException
	 */
	public static void generateWarnMessage(HttpServletResponse response,
			String message) throws IOException
	{
		JsonUtils.sendJsonMessage(response, WARN, message);
	}

	/**
	 * 输出错误消息
	 * 
	 * @param response
	 * @param message
	 * @throws IOException
	 */
	public static void generateErrorMessage(HttpServletResponse response,
			String message) throws IOException
	{
		JsonUtils.sendJsonMessage(response, ERROR, message);
	}
}
