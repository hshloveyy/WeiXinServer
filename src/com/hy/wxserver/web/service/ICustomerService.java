package com.hy.wxserver.web.service;

import javax.servlet.http.HttpServletRequest;

import com.hy.wxserver.message.response.RespBaseMessage;


/**
 * @author heshaohua E-mail:386184368@qq.com
 * @version ����ʱ�䣺2014-4-8 ����06:13:26
 * ��˵��
 */
public interface ICustomerService {
	
	public abstract RespBaseMessage processRequest(HttpServletRequest request);
	
}