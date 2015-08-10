package com.hy.wxserver.web.service;

import javax.servlet.http.HttpServletRequest;

import com.hy.wxserver.message.response.RespBaseMessage;


/**
 * @author heshaohua E-mail:386184368@qq.com
 * @version 创建时间：2014-4-8 下午06:13:26
 * 类说明
 */
public interface ICustomerService {
	
	public abstract RespBaseMessage processRequest(HttpServletRequest request);
	
}