/*
 * @(#)JsonUtils.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：张崇镇
 *  时　间：2008-10-9
 *  描　述：创建
 */

package com.infolion.platform.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.beanutils.BeanUtils;

/**
 * 
 * <pre>
 * json格式串工具类
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
public class JsonUtils
{
	public final static String JSON_ROOT_NODE = "infolion-json";
	public final static String JSON_MESSAGE_TYPE_NODE = "type";
	public final static String JSON_MESSAGE_NODE = "message";
	public final static String JSON_COUSTOM_NODE = "coustom";

	/**
	 * 向客户端发送json串
	 * 
	 * @param response
	 * @param message
	 * @throws IOException
	 */
	public static void sendJsonMessage(HttpServletResponse response,
			String msgType, String message) throws IOException
	{
		String result = generateMessageJson(msgType, message);
		flushResponse(response, result);
	}

	/**
	 * 向response输出字符串
	 * 
	 * @param response
	 * @param responseContent
	 * @throws IOException
	 */
	public static void flushResponse(HttpServletResponse response,
			String responseContent) throws IOException
	{
		response.reset();
		response.setCharacterEncoding("UTF-8");
		PrintWriter writer = response.getWriter();
		writer.write(responseContent);
		writer.flush();
		writer.close();
	}

	/**
	 * 生成以infolion-json为根的json格式串(转换javabean)
	 * 
	 * @param obj
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	public static String generateInfolionJson(Object obj)
			throws IllegalAccessException, InvocationTargetException,
			NoSuchMethodException
	{
		JSONObject jsObject = new JSONObject();
		Map custom = BeanUtils.describe(obj);
		return generateInfolionJson(custom);
	}

	/**
	 * 生成以infolion-json为根的json格式串
	 * 
	 * @param map
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	public static String generateInfolionJson(Map map)
			throws IllegalAccessException, InvocationTargetException,
			NoSuchMethodException
	{
		JSONObject jsObject = new JSONObject();
		jsObject.put(JSON_ROOT_NODE, map);

		return jsObject.toString();
	}

	/**
	 * 根据不同消息类型，生成infolion-json为根的消息json格式串
	 * 
	 * @param msgType
	 * @param message
	 * @return
	 */
	public static String generateMessageJson(String msgType, String message)
	{
		JSONObject jsObject = new JSONObject();
		jsObject.put(JSON_MESSAGE_TYPE_NODE, msgType);
		jsObject.put(JSON_MESSAGE_NODE, message);
		JSONObject rootObject = new JSONObject();
		rootObject.put(JSON_ROOT_NODE, jsObject);
		return rootObject.toString();
	}
	/**
	 * 附加自定义json
	 * @param msgType
	 * @param message
	 * @param cusJson
	 * @return
	 */
	public static String generateCustomMessageJson(String msgType, String message,String cusJson)
	{
		JSONObject jsObject = new JSONObject();
		jsObject.put(JSON_MESSAGE_TYPE_NODE, msgType);
		jsObject.put(JSON_MESSAGE_NODE, message);
		jsObject.put(JSON_COUSTOM_NODE, cusJson);
		JSONObject rootObject = new JSONObject();
		rootObject.put(JSON_ROOT_NODE, jsObject);
		return rootObject.toString();
	}
	/**
	 * 返回根json
	 * @param message
	 * @return
	 */
	public static String generateJson(String message)
	{
		JSONObject rootObject = new JSONObject();
		rootObject.put(JSON_ROOT_NODE, message);
		return rootObject.toString();
	}
}
