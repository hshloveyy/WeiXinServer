package com.hy.wxserver;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hy.wxserver.message.response.RespBaseMessage;
import com.hy.wxserver.utils.SignUtils;

public class CoreServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6823580963898863156L;

	/**
	 * Constructor of the object.
	 */
	public CoreServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String signature = request.getParameter("signature");	 //΢�ż���ǩ����signature����˿�������д��token�����������е�timestamp������nonce������
		String timestamp = request.getParameter("timestamp");	// ʱ���
		String nonce = request.getParameter("nonce");	 		//�����
		String echostr = request.getParameter("echostr");	 	//����ַ���
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		//��Ӧ���ͻ���
		if(SignUtils.checkSignature(signature, timestamp, nonce)){
			out.print(echostr);
		}
		out.flush();
		out.close();
		
		out = null;
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// ��������Ӧ�ı��������ΪUTF-8����ֹ�������룩  
        request.setCharacterEncoding("UTF-8");  
        response.setCharacterEncoding("UTF-8");  
        
     // ���ú���ҵ���������Ϣ��������Ϣ  
//        String respMessage = CoreService.processRequest(request);  
//        RespBaseMessage respMessage = CoreService.processRequest(request);
//        // ��Ӧ��Ϣ  
//        PrintWriter out = response.getWriter();  
//		out.print(respMessage.parseToXML());  
//        out.close();  
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
