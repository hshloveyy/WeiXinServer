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
 * ��������ַ�������
 * @author heshaohua E-mail:386184368@qq.com
 * @version ����ʱ�䣺2014-4-1 ����05:20:26 
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
	 *            ΢�ż���ǩ����signature����˿�������д��token�����������е�timestamp������nonce������
	 * @param timestamp
	 *            ʱ���
	 * @param nonce
	 *            �����
	 * @param echostr
	 *            ����ַ���
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
	 * ��Ӧ�ͻ���
	 *@author heshaohua	
	 * @param request
	 * @param response
	 * @throws IOException
	 */
//	@RequestMapping(method = RequestMethod.POST, value = "/core")
//	public void response(HttpServletRequest request, HttpServletResponse response) throws IOException{
//		// ��������Ӧ�ı��������ΪUTF-8����ֹ�������룩  
//        request.setCharacterEncoding("UTF-8");  
//        response.setCharacterEncoding("UTF-8");  
//        
//     // ���ú���ҵ���������Ϣ��������Ϣ  
////        String respMessage = CoreService.processRequest(request);  
//        RespBaseMessage respMessage = CoreService.processRequest(request);
//        // ��Ӧ��Ϣ  
//        PrintWriter out = response.getWriter();  
//		out.print(respMessage.parseToXML());  
//        out.close();  
//	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/core")
	public void response(HttpServletRequest request, HttpServletResponse response) throws IOException{
		// ��������Ӧ�ı��������ΪUTF-8����ֹ�������룩  
        request.setCharacterEncoding("UTF-8");  
        response.setCharacterEncoding("UTF-8");  
        log.debug(request.getParameterMap());
        request.getSession().getServletContext().setAttribute("req", request);
     // ���ú���ҵ���������Ϣ��������Ϣ  
//        String respMessage = CoreService.processRequest(request);  
        RespBaseMessage respMessage = customerService.processRequest(request);
        // ��Ӧ��Ϣ  xStreamMarshallingView
        PrintWriter out = response.getWriter();  
		out.print(respMessage.parseXML());  
        out.close(); 
	}
}
