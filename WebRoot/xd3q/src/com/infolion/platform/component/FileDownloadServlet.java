package com.infolion.platform.component;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class FileDownloadServlet extends HttpServlet{
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException{
		String dir ="/files";
		String fileName = req.getParameter("fileName");
		String fileServerPath = "http://"+req.getServerName()+":"+req.getServerPort()+req.getContextPath()+dir+"/"+fileName;
		requestFile(fileServerPath,resp,fileName);
	}
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException{
		doPost(req,resp);
	}
	
	public void requestFile(String url,HttpServletResponse resp,String fileName) throws IOException{
		URL httpUrl = new URL(url);
		URLConnection conn = httpUrl.openConnection();
		conn.setDoOutput(true);
		conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
	    InputStream	inStream = conn.getInputStream();
		String contentType = conn.getContentType();
		download(inStream, fileName, resp);
	}
	public void download(InputStream inStream, String filename,	HttpServletResponse response) throws IOException{
		byte[] b = new byte[10 * 1024];
		int len = 0;
		try {
			// 设置输出的格式
			response.reset();
			response.setCharacterEncoding("utf-8");
			// 开始解析
			response.setContentType("application/x-download;charset=utf-8");
			response.setHeader("Content-Disposition", "attachment;filename="+ URLEncoder.encode(filename, "utf-8"));
			// 循环取出流中的数据
			while ((len = inStream.read(b)) > 0) {
				response.getOutputStream().write(b, 0, len);
			}
			response.getOutputStream().close();
			inStream.close();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}finally{
			response.flushBuffer();
		}
	}
}
