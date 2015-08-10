package com.hy.wxserver.web.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hy.wxserver.message.response.RespBaseMessage;
import com.hy.wxserver.utils.SignUtils;
import com.hy.wxserver.web.service.ICustomerService;

/**
 * 核心请求分发控制器
 * @author heshaohua E-mail:386184368@qq.com
 * @version 创建时间：2014-4-1 下午05:20:26 
 */

@Controller("service")
public class DispatcherController {
	
	private Log log = LogFactory.getLog(DispatcherController.class);
	
	@Autowired
	private ICustomerService customerService;
	
	/**
	 * 
	 *@author heshaohua
	 * @param signature
	 *            微信加密签名，signature结合了开发者填写的token参数和请求中的timestamp参数、nonce参数。
	 * @param timestamp
	 *            时间戳
	 * @param nonce
	 *            随机数
	 * @param echostr
	 *            随机字符串
	 * @return
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET, value = "/core")
	public String verify(String signature, String timestamp, String nonce, String echostr) {
		if(SignUtils.checkSignature(signature, timestamp, nonce)){
			return echostr;
		}
		return null;
	}
	
	/**
	 * 响应客户端
	 *@author heshaohua	
	 * @param request
	 * @param response
	 * @throws IOException
	 */
//	@RequestMapping(method = RequestMethod.POST, value = "/core")
//	public void response(HttpServletRequest request, HttpServletResponse response) throws IOException{
//		// 将请求、响应的编码均设置为UTF-8（防止中文乱码）  
//        request.setCharacterEncoding("UTF-8");  
//        response.setCharacterEncoding("UTF-8");  
//        
//     // 调用核心业务类接收消息、处理消息  
////        String respMessage = CoreService.processRequest(request);  
//        RespBaseMessage respMessage = CoreService.processRequest(request);
//        // 响应消息  
//        PrintWriter out = response.getWriter();  
//		out.print(respMessage.parseToXML());  
//        out.close();  
//	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/core")
	public void response(HttpServletRequest request, HttpServletResponse response) throws IOException{
		// 将请求、响应的编码均设置为UTF-8（防止中文乱码）  
        request.setCharacterEncoding("UTF-8");  
        response.setCharacterEncoding("UTF-8");  
        log.debug(request.getParameterMap());
        request.getSession().getServletContext().setAttribute("req", request);
     // 调用核心业务类接收消息、处理消息  
//        String respMessage = CoreService.processRequest(request);  
        RespBaseMessage respMessage = customerService.processRequest(request);
        // 响应消息  xStreamMarshallingView
        PrintWriter out = response.getWriter();  
		out.print(respMessage.parseXML());  
        out.close(); 
	}
}
