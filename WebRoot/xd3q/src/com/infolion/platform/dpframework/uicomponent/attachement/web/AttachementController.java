/**
 * @(#) AttachementController.java
 * 版权声明： 福建讯盟软件有限公司, 版权所有 违者必究
 * 修订记录:
 *  1)更改者：MagicDraw V16
 *    时　间：May 14, 2009 1:04:05 PM
 *    描　述：创建
 */

package com.infolion.platform.dpframework.uicomponent.attachement.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.infolion.platform.component.fileProcess.services.AttachementBusinessJdbcService;
import com.infolion.platform.console.sys.context.UserContext;
import com.infolion.platform.console.sys.context.UserContextHolder;
import com.infolion.platform.dpframework.core.BusinessException;
import com.infolion.platform.dpframework.core.web.BaseMultiActionController;
import com.infolion.platform.dpframework.uicomponent.Constants;
import com.infolion.platform.dpframework.uicomponent.attachement.domain.Attachement;
import com.infolion.platform.dpframework.uicomponent.attachement.service.AttachementService;
import com.infolion.platform.dpframework.uicomponent.systemMessage.SysMsgConstants;
import com.infolion.platform.dpframework.uicomponent.systemMessage.SysMsgHelper;
import com.infolion.platform.util.CodeGenerator;
import com.infolion.platform.util.DateUtils;
import com.infolion.platform.util.EasyApplicationContextUtils;

/**
 * <pre>
 * 业务附件控制器
 * </pre>
 * 
 * <br>
 * JDK版本:1.5
 * 
 * @author 林进旭
 * @version 1.0
 * @see The author for more details
 * @since 1.0
 */
public class AttachementController extends BaseMultiActionController
{
	private static String propertiesPah ="\\config\\config.properties";
	private static Properties properties = null;
	void init() {
		properties = new Properties();
		try{
			properties = PropertiesLoaderUtils.loadAllProperties(propertiesPah);
		}
		catch (Exception e) {
			LogFactory.getLog(this.getClass()).error("加载配置文件失败:"+e.getMessage());
		}
	}
	@Autowired
	private AttachementService attachementService;

	public void setAttachementService(AttachementService attachementService)
	{
		this.attachementService = attachementService;
	}

	/**
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void upload(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		response.setContentType("text/html; charset=UTF-8");
		JSONObject jo = new JSONObject();
		String strResponseWrite = "";
		// 业务对象名称
		String boName = java.net.URLDecoder.decode(request.getParameter("boName"), "utf-8");
		// 业务对象属性名
		String boProperty = java.net.URLDecoder.decode(request.getParameter("boProperty"), "utf-8");
		// 业务对象ID
		String boId = java.net.URLDecoder.decode(request.getParameter("boId"), "utf-8");
		// 上传文件路径
		String filepath = java.net.URLDecoder.decode(request.getParameter("filepath"), "utf-8");
		// 附件描述
		String description = java.net.URLDecoder.decode(request.getParameter("description"), "utf-8");

		log.debug("boName: " + request.getParameter("boName") + "boName:" + boName);

		// 判断request是不是multipart请求:
		if (request instanceof MultipartHttpServletRequest)
		{}
		else
		{
			// 业务附件上传失敗，当前request不是multipart请求！
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, Constants.UpLoadFailure);
		}

		// 判断request是不是multipart请求:
		// MultipartResolver resolver = new CommonsMultipartResolver();
		// boolean isMultipart = resolver.isMultipart(request);
		// System.out.println("isMultipart:" + isMultipart);

		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		// 取得上传的文件流对象
		MultipartFile multipartFile = multipartRequest.getFile("upload");
		// InputStream in = multipartRequest.getFile("upload").getInputStream();

		// 文件大小
		long fileSize = multipartFile.getSize();
		log.debug("上传文件大小:" + fileSize);
		// Object oo = (Object) fileSize;

		if (multipartFile != null && !multipartFile.isEmpty())
		{
			jo = this.attachementService.uploadAttachement(filepath, description, boName, boProperty, boId, multipartFile);
		}
		else
		{
			jo.put("success", "false");
			// 文件&gt;" + multipartFile.getOriginalFilename() + "&lt;上传失败!或者文件大小为0字节!"
			String msg = SysMsgHelper.getDPMessage(Constants.FileUpLoadFailure, multipartFile.getOriginalFilename());
			jo.put("msg", msg);
			// throw new BusinessException("业务附件:文件文件&gt;" +
			// multipartFile.getOriginalFilename() + "&lt;上传失败!或者文件大小为0字节!");
			// strResponseWrite = "{success:false,msg:'文件>" +
			// multipartFile.getOriginalFilename() + "<上传失败!或者文件大小为0字节'}";
		}

		response.getWriter().write(jo.toString());
		// log.debug(jo.toString());
	}

	/**
	 * 下载业务附件。(根据attachementId下载对应的业务附件)
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void download2(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String attachementId = request.getParameter("attachementId");

		Attachement attachement;
		attachement = this.attachementService.download(attachementId);
		byte[] file = attachement.getFileStore();
		String fileName = attachement.getFileName();

		try
		{
			response.setContentType("application/x-msdownload");
			response.setHeader("Content-Disposition", "attachment;" + " filename=" + new String(fileName.getBytes("iso-8859-1"), "UTF8"));
			response.getOutputStream().write(file);
			response.getOutputStream().flush();
		}
		catch (Exception e)
		{
			log.debug("业务附件: 附件下载出错! " + e.getStackTrace().toString());
			// "业务附件下载出错！"
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, Constants.DownLoadErr);
		}
	}

	public void download(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String attachementId = request.getParameter("attachementId");
		Attachement attachement = this.attachementService.download(attachementId);
		String fileName = attachement.getFileName();
		try {
			String currentPath="/UserFiles/File/";
			String currentDirPath=getServletContext().getRealPath(currentPath);
			File file = new File(currentDirPath,fileName);
			
			//if(!file.isAbsolute()){
				String fileServerPath = "http://" + request.getServerName() + ":"+ request.getServerPort() + request.getContextPath() + currentPath + fileName;
			//}
			requestFile(fileServerPath, response, fileName);
		}catch(FileNotFoundException e){
			response.setContentType("text/html; charset=UTF-8");
			response.getWriter().write("<script>window.alert('文件"+fileName+"不存在,请联系系统管理员')</script>");
		}
	}
	
	public void requestFile(String url,HttpServletResponse resp,String fileName) throws IOException{
		File file = new File(url);
		if(!file.isDirectory()){
			URL httpUrl = new URL(url);
			URLConnection conn = httpUrl.openConnection();
			conn.setDoOutput(true);
			conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
			InputStream	inStream = conn.getInputStream();
			output(inStream, fileName, resp);
		}else{
			InputStream in = new FileInputStream(url+"/"+fileName);
			output(in, fileName, resp);
		}
	}
	
	private void output(InputStream inStream, String filename,HttpServletResponse response) throws IOException{
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
	
	/**
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void upload2(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		response.setContentType("text/html; charset=UTF-8");
		response.setHeader("Cache-Control","no-cache");
		PrintWriter out = response.getWriter();
		
		String retVal="0";
		String errorMessage="";
		
		JSONObject jo = new JSONObject();
		String strResponseWrite = "";
		// 业务对象名称
		String boName = java.net.URLDecoder.decode(request.getParameter("boName"), "utf-8");
		// 业务对象属性名
		String boProperty = java.net.URLDecoder.decode(request.getParameter("boProperty"), "utf-8");
		// 业务对象ID
		String boId = java.net.URLDecoder.decode(request.getParameter("boId"), "utf-8");
		// 上传文件路径
		String filepath = java.net.URLDecoder.decode(request.getParameter("filepath"), "utf-8");
		// 附件描述
		String description = java.net.URLDecoder.decode(request.getParameter("description"), "utf-8");
		
		String userId = request.getParameter("userId");

		log.debug("boName: " + request.getParameter("boName") + "boName:" + boName);

		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		// 取得上传的文件流对象
		MultipartFile multipartFile = multipartRequest.getFile("upload");
		String fileName = "";
		String newName="";
		
		// 判断request是不是multipart请求:
		if (request instanceof MultipartHttpServletRequest)
		{}
		else
		{
			// 业务附件上传失敗，当前request不是multipart请求！
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, Constants.UpLoadFailure);
		}
			
		if (multipartFile != null && !multipartFile.isEmpty()){
			fileName = multipartFile.getOriginalFilename();
			newName = multipartFile.getOriginalFilename();
			long fileSize = multipartFile.getSize();
			String file_size = (String)properties.get("file_size");
			if(fileSize>Long.parseLong(file_size)*1024)
				throw new BusinessException("文件不能超过"+file_size+"KB");
			try {
				//String typeStr = "File";
				String currentPath=properties.getProperty("file_path");//"/UserFiles/"+typeStr;
				String sonDir = DateUtils.getYearMonth(DateUtils.HYPHEN_DISPLAY_DATE);
				File file = new File(currentPath+File.separator+sonDir);
			    if (!file.exists()&&!file.isDirectory()) 
		             file.mkdirs();
				//String currentDirPath=getServletContext().getRealPath(currentPath);
				//currentPath=request.getContextPath()+currentPath;
				
				String nameWithoutExt=getNameWithoutExtension(fileName);
				String ext=getExtension(fileName);
				File pathToSave=new File(currentPath+File.separator+sonDir,fileName);
				String fileUrl=currentPath+File.separator+sonDir+"/"+fileName;
				
				int counter=1;
				while(pathToSave.exists()){
					newName=nameWithoutExt+"("+counter+")"+"."+ext;
					fileUrl=currentPath+"/"+newName;
					pathToSave=new File(currentPath+File.separator+sonDir,newName);
					counter++;
				}
				multipartFile.transferTo(pathToSave);
				newName=sonDir+File.separator+newName;
			
			}catch (Exception ex) {
				throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, ex.toString());
			}
		}
		jo = this.attachementService.uploadAttachement(filepath, newName, description, boName, boProperty, boId, multipartFile,userId);

		response.getWriter().write(jo.toString());
	}
	
	/*
	 * This method was fixed after Kris Barnhoorn (kurioskronic) submitted SF bug #991489
	 */
  	private static String getNameWithoutExtension(String fileName) {
    		return fileName.substring(0, fileName.lastIndexOf("."));
    	}
    	
	/*
	 * This method was fixed after Kris Barnhoorn (kurioskronic) submitted SF bug #991489
	 */
	private String getExtension(String fileName) {
		return fileName.substring(fileName.lastIndexOf(".")+1);
	}
	
	/**
	 * 下载附件
	 * 
	 * @param request
	 * @param response
	 */
	public void downloadByBusinessId(HttpServletRequest request, HttpServletResponse response)
	{
		String businessId = request.getParameter("businessId");

		Attachement attachement = this.attachementService.getAttachement(businessId);
		if (attachement != null)
		{
			byte[] file = attachement.getFileStore();
			String fileName = attachement.getFileName();

			try
			{
				response.setContentType("application/x-msdownload");
				response.setHeader("Content-Disposition", "attachment;" + " filename=" + new String(fileName.getBytes("iso-8859-1"), "UTF8"));
				response.getOutputStream().write(file);
				response.getOutputStream().flush();
			}
			catch (Exception e)
			{
				log.debug("业务附件: 附件下载出错! " + e.getStackTrace().toString());
				// "业务附件下载出错！"
				throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, Constants.DownLoadErr);
			}
		}
	}

	/**
	 * 删除动作，删除指定的业务附件(根据attachementId删除)。
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void delete(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		String attachementIdList = request.getParameter("attachementIdList");
		String userId= request.getParameter("userId");
		String callBack = request.getParameter("callback");
		boolean isPass = false;

		isPass = this.attachementService.deleteAttachement(attachementIdList,userId);

		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		String msg = "";
		if (isPass)
		{
			msg = SysMsgHelper.getDPMessage(Constants.DeleteSuccess);
			response.getWriter().write(callBack + "('"+msg+"')");
			//response.getWriter().write("{success:true,msg:'  " + msg + "  '}");
		}
		else
		{
			msg = SysMsgHelper.getDPMessage(Constants.DeleteFailure);
			response.getWriter().write(callBack + "('"+msg+"')");
			//response.getWriter().write("{success:false,msg:'  " + msg + "   '}");
		}

	}
	
	public void fileUploadCheck(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		resp.setContentType("text/html; charset=UTF-8");
		String originalFile = req.getParameter("filepath");
		if(originalFile.indexOf("\\")!=-1){
			originalFile =originalFile.substring(originalFile.lastIndexOf("\\")+1);
		}else if(originalFile.indexOf("/")!=-1){
			originalFile =originalFile.substring(originalFile.lastIndexOf("/")+1);
		}
		String userId = req.getParameter("userId");
		AttachementBusinessJdbcService abs = (AttachementBusinessJdbcService)EasyApplicationContextUtils.getBeanByName("attachementBusinessJdbcService");
		int i = abs.fileUploadCheck(originalFile, userId,2);
		if(i>0)
		   resp.getWriter().write("{success:true,msg:'文件:"+originalFile+"上传成功'}");
		else resp.getWriter().write("{success:true,msg:'文件"+originalFile+"上传失败'}");

	}

	/**
	 * 单个上传文件（customerMasterDetail.jsp）可以跨域上传
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void upload3(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		response.setContentType("text/html; charset=UTF-8");
		response.setHeader("Cache-Control","no-cache");
		String boId = request.getParameter("id");
		String boname = request.getParameter("boname");
		String cburl = request.getParameter("cburl");
		String callback = request.getParameter("callback");
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		// 取得上传的文件流对象
		MultipartFile multipartFile = multipartRequest.getFile("uploadFile");
		String fileName = "";
		String newName="";
		
		// 判断request是不是multipart请求:
		if (request instanceof MultipartHttpServletRequest)
		{}
		else
		{
			// 业务附件上传失敗，当前request不是multipart请求！
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, Constants.UpLoadFailure);
		}
			
		if (multipartFile != null && !multipartFile.isEmpty()){
//			p = PropertiesLoaderUtils.loadAllProperties(propertiesPah);
			fileName = multipartFile.getOriginalFilename();
			fileName = CodeGenerator.getUUID() + "_" + fileName;
			newName = multipartFile.getOriginalFilename();
			long fileSize = multipartFile.getSize();
			String file_size = (String)properties.get("file_size");
			if(fileSize>Long.parseLong(file_size)*1024)
				throw new BusinessException("文件不能超过"+file_size+"KB");
			try {
				//String typeStr = "File";
				String currentPath=properties.getProperty("file_path");//"/UserFiles/"+typeStr;
//				String sonDir = DateUtils.getYearMonth(DateUtils.HYPHEN_DISPLAY_DATE);
				String sonDir =boname;
				File file = new File(currentPath+File.separator+sonDir);
			    if (!file.exists()&&!file.isDirectory()) 
		             file.mkdirs();
				//String currentDirPath=getServletContext().getRealPath(currentPath);
				//currentPath=request.getContextPath()+currentPath;
				
				String nameWithoutExt=getNameWithoutExtension(fileName);
				String ext=getExtension(fileName);
				File pathToSave=new File(currentPath+File.separator+sonDir,fileName);
				String fileUrl=currentPath+File.separator+sonDir+"/"+fileName;
				
				int counter=1;
				while(pathToSave.exists()){
					newName=nameWithoutExt+"("+counter+")"+"."+ext;
					fileUrl=currentPath+"/"+newName;
					pathToSave=new File(currentPath+File.separator+sonDir,newName);
					counter++;
				}
				multipartFile.transferTo(pathToSave);
				newName=sonDir+File.separator+newName;
			
			}catch (Exception ex) {
				throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, ex.toString());
				
			}
			String userId = request.getParameter("userId");
			JSONObject js = new JSONObject();
			Attachement attachement = this.attachementService.getAttachement(boId, boname);
			if(null != attachement){
				this.attachementService.deleteAttachement("'" +attachement.getAttachementId()+"',", attachement.getCreator()); 
			}
			js = this.attachementService.uploadAttachement("", fileName, null, boname, null, boId, multipartFile,userId);
			
			
//			js.put("success", "true");
//			js.put("msg", "上传成功！");
//			js.put("path", fileName);
//			js.put("id", id);
//			this.operateSuccessfullyWithString(response, js.toString());
			//cburl ="http://172.16.18.238:8080/xd3q/attachementController.spr";
//			cburl="http://172.16.18.238:8080/xd3q/";
			//callback="uploadManger";
			System.out.print("域名：" + request.getServerName() + "端口号：" + request.getServerPort() + "cburl:" + cburl);
//			判断是否是同一个域
			if(cburl.indexOf(request.getServerName()) != -1 && cburl.indexOf(String.valueOf(request.getServerPort())) != -1){
				System.out.print("同域");
				response.getWriter().write(js.toString());
			}else{
				System.out.print("不同域");
			String callUrl = cburl + "?action=" + callback;			
			String msg="";
			//中文转二次码
			msg = java.net.URLEncoder.encode(msg,"UTF-8");
			fileName = java.net.URLEncoder.encode(fileName,"UTF-8");
			msg = java.net.URLEncoder.encode(msg,"UTF-8");
			fileName = java.net.URLEncoder.encode(fileName,"UTF-8");
	        response.setHeader("Cache-Control", "no-cache");  
	        response.addHeader("Access-Control-Allow-Origin", "*");  
	        response.addHeader("P3P","CP=CAO PSA OUR");
	        response.addHeader("Access-Control-Allow-Headers", "x-requested-with");  
	        response.addHeader("Location", callUrl + "?success=" + js.get("success" ) + "&msg=" + msg + "&path=" + fileName);   
	        response.sendRedirect(response.encodeURL(callUrl + "&success="+ js.get("success" ) +"&msg="+ msg + "&path="+ fileName));  
			}
		}
	}
	
	public void getFilePath(HttpServletRequest request, HttpServletResponse response){
		String boId = request.getParameter("id");
		String boname = request.getParameter("boname");
		Attachement attachement = this.attachementService.getAttachement(boId, boname);
		JSONObject js = new JSONObject();
		js.put("path", attachement.getFileName());
		this.operateSuccessfullyWithString(response, js.toString());
	}
	/**
	 * 单个下载文件（customerMasterDetail.jsp）
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	public void proceeFileDownload(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException{
		String fileName = java.net.URLDecoder.decode(req.getParameter("fileName"),"UTF-8");
		String path = java.net.URLDecoder.decode(req.getParameter("path"),"UTF-8");
		fileName = path +"/" + fileName;
		JSONObject js = new JSONObject();
		try {
//			PropertiesUtil p = new PropertiesUtil(path);
//			p = PropertiesLoaderUtils.loadAllProperties(propertiesPah);
			String dir = properties.getProperty("file_path");
			File file = new File(dir);
			String fileServerPath = dir;
			if(!file.isAbsolute()){
				fileServerPath = "http://" + req.getServerName() + ":"+ req.getServerPort() + req.getContextPath() + fileServerPath+"/"+fileName;
			}
			requestFile(fileServerPath, resp, fileName);
		}catch(FileNotFoundException e){
//			resp.setContentType("text/html; charset=UTF-8");
//			resp.getWriter().write("<script>window.alert('文件"+fileName+"不存在,请联系系统管理员')</script>");
			js.put("success", false);
			js.put("msg", "'文件"+fileName+"不存在,请联系系统管理员！！");
			this.operateSuccessfullyWithString(resp, js.toString());
		}
	}
	
	/**
	 * 删除文件单个文件（customerMasterDetail.jsp）
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	public void proceeFileDelete(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException{
		String callBack = req.getParameter("callback");
//		PropertiesUtil p = new PropertiesUtil(path);
//		p = PropertiesLoaderUtils.loadAllProperties(propertiesPah);
		String dir =properties.getProperty("file_path");
		String fileName = req.getParameter("fileName");
		String path = java.net.URLDecoder.decode(req.getParameter("path"),"UTF-8");
		fileName = path +"/" + fileName;
		String creator = req.getParameter("creator");
		String name = fileName;
		File file = new File(dir);
		fileName = dir+"/"+name;
		if(!file.isAbsolute()){
			fileName = req.getRealPath(dir+"/"+name);
		}
		file = new File(fileName);
		resp.setContentType("text/html; charset=UTF-8");
		JSONObject js = new JSONObject();
		if(file.exists()){
			
			String userid = "";
			String boId =req.getParameter("masterid");
			String boname = path;
			Attachement attachement = this.attachementService.getAttachement(boId, boname);
			if(null != attachement){
				userid = attachement.getCreator();
			}
			if(userid.equals(creator)){
				file.delete();
				this.attachementService.deleteAttachement("'" +attachement.getAttachementId()+"',", attachement.getCreator()); 
//				resp.getWriter().write(callBack + "('删除成功')");					
				js.put("success", true);
				js.put("msg", "删除成功！");
				this.operateSuccessfullyWithString(resp, js.toString());
			}else{
//				resp.getWriter().write(callBack + "('您不是文件创建者，不允许删除该文件！')");
				js.put("success", false);
				js.put("msg", "您不是文件创建者，不允许删除该文件！！");
				this.operateSuccessfullyWithString(resp, js.toString());
			}
		}else 
			js.put("success", false);
			js.put("msg", "该文件不存在！！");
//			resp.getWriter().write(js.toString());
			this.operateSuccessfullyWithString(resp, js.toString());
//		    resp.getWriter().write(callBack + "('"+name+"不存在!')");
	}
	
	 public ModelAndView uploadManger(HttpServletRequest request,
	            HttpServletResponse response)  {
		 	String id = request.getParameter("id");	
		 	String userId = request.getParameter("userId");
			String success = request.getParameter("success");
			String boname = request.getParameter("boname");
			String msg = request.getParameter("msg");
			String path = request.getParameter("path");
			
			try {
				if(StringUtils.isNotBlank(msg)){
				msg =java.net.URLDecoder.decode(msg,"UTF-8");
				}
				if(StringUtils.isNotBlank(path)){
				path = java.net.URLDecoder.decode(path,"UTF-8");
				}
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			request.setAttribute("id", id);
			request.setAttribute("userId", userId);
			request.setAttribute("boname", boname);
			request.setAttribute("success", success);
			request.setAttribute("msg", msg);
			request.setAttribute("path", path);
	        return new ModelAndView("upload");
	 }
	 
	 
	 /**
		 * @param request
		 * @param response
		 * @throws IOException
		 */
		public void upload2Modify(HttpServletRequest request, HttpServletResponse response) throws IOException
		{
			response.setContentType("text/html; charset=UTF-8");
			response.setHeader("Cache-Control","no-cache");
			PrintWriter out = response.getWriter();
			
			String retVal="0";
			String errorMessage="";
			
			JSONObject jo = new JSONObject();
			String strResponseWrite = "";
			// 业务对象名称
			String boName = java.net.URLDecoder.decode(request.getParameter("boName"), "utf-8");
			// 业务对象属性名
			String boProperty = java.net.URLDecoder.decode(request.getParameter("boProperty"), "utf-8");
			// 业务对象ID
			String boId = java.net.URLDecoder.decode(request.getParameter("boId"), "utf-8");
			// 上传文件路径
			String filepath = java.net.URLDecoder.decode(request.getParameter("filepath"), "utf-8");
			// 附件描述
			String description = java.net.URLDecoder.decode(request.getParameter("description"), "utf-8");
			
			String userId = request.getParameter("userId");
			
			String modifyAddTime = request.getParameter("modifyAddTime");
			modifyAddTime = modifyAddTime.replaceAll("-", "").replaceAll(" ", "").replaceAll(":", "");

			log.debug("boName: " + request.getParameter("boName") + "boName:" + boName);

			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			// 取得上传的文件流对象
			MultipartFile multipartFile = multipartRequest.getFile("uploadM");
			String fileName = "";
			String newName="";
			
			// 判断request是不是multipart请求:
			if (request instanceof MultipartHttpServletRequest)
			{}
			else
			{
				// 业务附件上传失敗，当前request不是multipart请求！
				throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, Constants.UpLoadFailure);
			}
				
			if (multipartFile != null && !multipartFile.isEmpty()){
				fileName = multipartFile.getOriginalFilename();
				newName = multipartFile.getOriginalFilename();
				long fileSize = multipartFile.getSize();
				String file_size = (String)properties.get("file_size");
				if(fileSize>Long.parseLong(file_size)*1024)
					throw new BusinessException("文件不能超过"+file_size+"KB");
				try {
					//String typeStr = "File";
					String currentPath=properties.getProperty("file_path");//"/UserFiles/"+typeStr;
					
					String sonDir = "2099-12";
					try{
					    sonDir = DateUtils.getDateFormat(modifyAddTime,"yyyyMMddHHmmss","yyyy-MM"); 
					}
					catch (ParseException e) {
						response.getWriter().write("{success:false,msg:'上传时间格式错误'}");
						return;
					}
					
					File file = new File(currentPath+File.separator+sonDir);
				    if (!file.exists()&&!file.isDirectory()) 
			             file.mkdirs();
					//String currentDirPath=getServletContext().getRealPath(currentPath);
					//currentPath=request.getContextPath()+currentPath;
					
					String nameWithoutExt=getNameWithoutExtension(fileName);
					String ext=getExtension(fileName);
					File pathToSave=new File(currentPath+File.separator+sonDir,fileName);
					String fileUrl=currentPath+File.separator+sonDir+"/"+fileName;
					
					int counter=1;
					while(pathToSave.exists()){
						newName=nameWithoutExt+"("+counter+")"+"."+ext;
						fileUrl=currentPath+"/"+newName;
						pathToSave=new File(currentPath+File.separator+sonDir,newName);
						counter++;
					}
					multipartFile.transferTo(pathToSave);
					newName=sonDir+File.separator+newName;
				
				}catch (Exception ex) {
					throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, ex.toString());
				}
			}
			jo = this.attachementService.uploadAttachementModify(filepath, newName, description, boName, boProperty, boId, multipartFile,userId,modifyAddTime);
			response.getWriter().write(jo.toString());
		}
		
		public void upload2ModifyReplace(HttpServletRequest request, HttpServletResponse response) throws IOException
		{
			response.setContentType("text/html; charset=UTF-8");
			response.setHeader("Cache-Control","no-cache");
			PrintWriter out = response.getWriter();
			
			String retVal="0";
			String errorMessage="";
			
			JSONObject jo = new JSONObject();
			String strResponseWrite = "";
			// 业务对象名称
			String boName = java.net.URLDecoder.decode(request.getParameter("boName"), "utf-8");
			// 业务对象属性名
			String boProperty = java.net.URLDecoder.decode(request.getParameter("boProperty"), "utf-8");
			// 业务对象ID
			String boId = java.net.URLDecoder.decode(request.getParameter("boId"), "utf-8");
			// 上传文件路径
			String filepath = java.net.URLDecoder.decode(request.getParameter("filepath"), "utf-8");
			// 附件描述
			String description = java.net.URLDecoder.decode(request.getParameter("description"), "utf-8");

            String attachementId = java.net.URLDecoder.decode(request.getParameter("attachementId"), "utf-8");
            
            String userId = request.getParameter("userId");

			log.debug("boName: " + request.getParameter("boName") + "boName:" + boName);

			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			// 取得上传的文件流对象
			MultipartFile multipartFile = multipartRequest.getFile("uploadMR");
			String fileName = "";
			String newName="";
			
			// 判断request是不是multipart请求:
			if (request instanceof MultipartHttpServletRequest)
			{}
			else
			{
				// 业务附件上传失敗，当前request不是multipart请求！
				throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, Constants.UpLoadFailure);
			}
			Attachement at = attachementService.getAttachementById(attachementId);	
			if (multipartFile != null && !multipartFile.isEmpty()){
				fileName = multipartFile.getOriginalFilename();
				newName = multipartFile.getOriginalFilename();
				long fileSize = multipartFile.getSize();
				String file_size = (String)properties.get("file_size");
				if(fileSize>Long.parseLong(file_size)*1024)
					throw new BusinessException("文件不能超过"+file_size+"KB");
				try {
					
					//String typeStr = "File";
					String currentPath=properties.getProperty("file_path");//"/UserFiles/"+typeStr;
					//String sonDir = DateUtils.getYearMonth(DateUtils.HYPHEN_DISPLAY_DATE);
					String oldFilePath = at.getFileName();
					String sonDir = oldFilePath.substring(0,oldFilePath.indexOf("\\"));
					
					File file = new File(currentPath+File.separator+sonDir);
				    if (!file.exists()&&!file.isDirectory()) 
			             file.mkdirs();
					//String currentDirPath=getServletContext().getRealPath(currentPath);
					//currentPath=request.getContextPath()+currentPath;
					
					String nameWithoutExt=getNameWithoutExtension(fileName);
					String ext=getExtension(fileName);
					File pathToSave=new File(currentPath+File.separator+sonDir,fileName);
					String fileUrl=currentPath+File.separator+sonDir+"/"+fileName;
					
					int counter=1;
					while(pathToSave.exists()){
						newName=nameWithoutExt+"("+counter+")"+"."+ext;
						fileUrl=currentPath+"/"+newName;
						pathToSave=new File(currentPath+File.separator+sonDir,newName);
						counter++;
					}
					multipartFile.transferTo(pathToSave);
					newName=sonDir+File.separator+newName;
				
				}catch (Exception ex) {
					throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, ex.toString());
				}
			}
			jo = this.attachementService.uploadAttachementReplace(at, newName, description, userId);

			response.getWriter().write(jo.toString());
		}
		
		public void deleteModify(HttpServletRequest request, HttpServletResponse response) throws IOException
		{
			String attachementIdList = request.getParameter("attachementIdList");
			String userId= request.getParameter("userId");
			String callBack = request.getParameter("callback");
			boolean isPass = false;

			isPass = this.attachementService.deleteAttachementModify(attachementIdList,userId);

			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=UTF-8");
			String msg = "";
			if (isPass)
			{
				msg = SysMsgHelper.getDPMessage(Constants.DeleteSuccess);
				response.getWriter().write(callBack + "('"+msg+"')");
				//response.getWriter().write("{success:true,msg:'  " + msg + "  '}");
			}
			else
			{
				msg = SysMsgHelper.getDPMessage(Constants.DeleteFailure);
				response.getWriter().write(callBack + "('"+msg+"')");
				//response.getWriter().write("{success:false,msg:'  " + msg + "   '}");
			}

		}
}
