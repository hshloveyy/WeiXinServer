package com.hy.wxserver.web.service;

import javax.servlet.http.HttpServletRequest;

import com.hy.wxserver.message.response.RespBaseMessage;

/**
 * @author heshaohua E-mail:386184368@qq.com
 * @version 创建时间：2014-4-8 下午05:03:18
 * 类说明
 */
public interface ICoreService {

	/** 
	 * 处理微信发来的请求 
	 *  
	 * @param request 
	 * @return 
	 */
	public abstract RespBaseMessage processRequest(HttpServletRequest request);

}