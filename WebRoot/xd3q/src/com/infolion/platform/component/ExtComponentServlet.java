/*
 * @(#)ExtComponentServlet.java
 * 版权声明： 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：陈喜平
 *  时　间：2008-12-26
 *  描　述：创建
 */

package com.infolion.platform.component;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.infolion.platform.component.fileProcess.domain.TAttachement;
import com.infolion.platform.component.fileProcess.domain.TAttachementBusiness;
import com.infolion.platform.component.fileProcess.domain.TAttachementLog;
import com.infolion.platform.component.fileProcess.services.AttachementBusinessJdbcService;
import com.infolion.platform.component.fileProcess.services.AttachementHibernateService;
import com.infolion.platform.component.fileProcess.services.AttachementJdbcService;
import com.infolion.platform.component.grid.GridDataSource;
import com.infolion.platform.component.grid.OracleGridDataSource;
import com.infolion.platform.component.processor.FileRenamePolicyImpl;
import com.infolion.platform.component.tree.TreeDataSource;
import com.infolion.platform.dpframework.core.BusinessException;
import com.infolion.platform.util.CodeGenerator;
import com.infolion.platform.util.DateUtils;
import com.infolion.platform.util.EasyApplicationContextUtils;
import com.infolion.platform.util.PropertiesUtil;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.FileRenamePolicy;
/**
 * 
 * @author cxp
 *
 */

public class ExtComponentServlet extends HttpServlet {
	private String path;
	/**
	 * 初始化
	 */
	public void init(ServletConfig cfg) throws javax.servlet.ServletException {
		 super.init(cfg);
		 path = cfg.getInitParameter("config");
	}
	@Autowired
	private GridDataSource gridDS;
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		//UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();	
//		if(null==userContext){
//			req.getRequestDispatcher("/login.jsp").forward(req, resp);
//		} else {
			String type = req.getParameter("type");
			if (type.equals("tree")) {
				this.processTree(req, resp);
			} else if (type.equals("grid")) {
				this.processGrid(req, resp,false);
			} else if (type.equals("fileUpload")) {
				this.processFileUpload(req, resp);
			} else if (type.equals("fileDownload")) {
				this.proceeFileDownload(req, resp);
			} else if (type.equals("fileGrid")) {
				this.proceeFileGrid(req, resp);
			} else if (type.equals("fileDelete")) {
				this.proceeFileDelete(req, resp);
			} else if(type.equals("fileUploadCheck")){
				this.fileUploadCheck(req, resp);
			} else if(type.equals("fileUploadModifyCheck")){
				this.fileUploadModifyCheck(req, resp);
			} else if(type.equals("modifyAdd")){
				this.processModifyAdd(req, resp);
			} else if(type.equals("modifyDel")){
				this.processModifyDel(req, resp);
			} else if(type.equals("modifyReplace")){
				this.processModifyReplace(req, resp);
			}
//		}
	}
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}
	/**
	 * 处理显示文件列表
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	private void proceeFileGrid(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException{
		String resourceId  = req.getParameter("resourceId");
		String resourceName=req.getParameter("resourceName");
		String recordId	   =req.getParameter("recordId");

		String grid_sql="select * from t_attachement ta where ta.attachement_business_id in"+
						" (select tab.attachement_business_id from t_attachement_business tab where tab.resource_id='"+resourceId+
						"' and tab.resource_name='"+resourceName+"' and tab.record_id='"+recordId+"') order by ta.creator_time desc";
		//String grid_sql="select * from t_sys_wf_process_accessory";
		//String grid_columns="file_description,file_path,original_file_name,update_date,new_file_name,";
		String grid_columns="OLD_NAME,READ_NAME,ATTACHEMENT_CMD,UPLOAD_TIME,ATTACHEMENT_ID";
		String grid_size="5";
		req.setAttribute("grid_sql", grid_sql);
		req.setAttribute("grid_columns", grid_columns);
		req.setAttribute("grid_size", grid_size);
		processGrid(req,resp,true);
	}
	/**
	 * 删除文件
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	private void proceeFileDelete(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException{
		String callBack = req.getParameter("callback");
		PropertiesUtil p = new PropertiesUtil(path);
		String dir =p.getProperty("file_path");
		String fileName = req.getParameter("fileName");
		String userId = req.getParameter("userId");
		String name = fileName;
		File file = new File(dir);
		fileName = dir+"/"+name;
		if(!file.isAbsolute()){
			fileName = req.getRealPath(dir+"/"+name);
		}
		file = new File(fileName);
		resp.setContentType("text/html; charset=UTF-8");
		if(file.exists()){
			AttachementJdbcService service = (AttachementJdbcService)EasyApplicationContextUtils.getBeanByName("attachementJdbcService");
			int rtn = service.delAttachement(req.getParameter("attachementId"),userId);
			if(rtn>0){
				file.delete();
				resp.getWriter().write(callBack + "('删除成功')");
				//resp.getWriter().write("{success:false,msg:'删除成功'}");
			}else{
				resp.getWriter().write(callBack + "('您不是文件创建者，不允许删除该文件！')");
				//resp.getWriter().write("{success:false,msg:'您不是文件创建者，不允许删除该文件！'}");
			}
		}else 
			//resp.getWriter().write("{success:false,msg:'"+name+"不存在'}");
		    resp.getWriter().write(callBack + "('"+name+"不存在!')");
	}
	/**
	 * 
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	private void proceeFileDownload(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException{
		String fileName = java.net.URLDecoder.decode(req.getParameter("fileName"),"UTF-8");
		try {
			PropertiesUtil p = new PropertiesUtil(path);
			String dir = p.getProperty("file_path");
			File file = new File(dir);
			String fileServerPath = dir;
			if(!file.isAbsolute()){
				fileServerPath = "http://" + req.getServerName() + ":"+ req.getServerPort() + req.getContextPath() + fileServerPath+"/"+fileName;
			}
			requestFile(fileServerPath, resp, fileName);
		}catch(FileNotFoundException e){
			resp.setContentType("text/html; charset=UTF-8");
			resp.getWriter().write("<script>window.alert('文件"+fileName+"不存在,请联系系统管理员')</script>");
		}
	}
	/**
	 * 处理文件上传
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	private void processFileUpload(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException {
		resp.setContentType("text/html; charset=UTF-8");
		String query = req.getQueryString();
		query = java.net.URLDecoder.decode(query,"utf-8");
		PropertiesUtil p = new PropertiesUtil(path);
		String description = query.substring(query.lastIndexOf("&description=")+13, query.lastIndexOf("&filepath="));
		String originalFile = query.substring(query.lastIndexOf("&filepath=")+10,query.lastIndexOf("&recordId="));
		if(originalFile.indexOf("\\")!=-1){
			originalFile =originalFile.substring(originalFile.lastIndexOf("\\")+1);
		}else if(originalFile.indexOf("/")!=-1){
			originalFile =originalFile.substring(originalFile.lastIndexOf("/")+1);
		}
		String userId = req.getParameter("userId");
		//取得服务器存档文件夹路径
		String prefix = originalFile.substring(originalFile.lastIndexOf("."));
		String dir = p.getProperty("file_path");
		String sonDir = DateUtils.getYearMonth(DateUtils.HYPHEN_DISPLAY_DATE);
		File file = new File(dir+File.separator+sonDir);
	    if (!file.exists()&&!file.isDirectory()) 
             file.mkdirs();

/*		if(!file.isAbsolute()){
			dir = req.getRealPath(dir);
		}*/
		String file_size = p.getProperty("file_size");
		int size = Integer.valueOf(file_size)*1024;
		if(userId==null){
			resp.getWriter().write("{success:false,msg:'请重新登陆'}");
			return;
		}
		//http://192.168.39.158:8080/infolionPlatform/fileDownLoadServlet?fileName=56_11_20090108091009.pdf
		//String fileServerPath = "http://"+req.getServerName()+":"+req.getServerPort()+req.getContextPath()+"/extComponentServlet?type=fileDownload";
		MultipartRequest multi= null;
		int flag =0 ;
		String exception=null;
		try {
			String fileTime = DateUtils.getCurrTime(DateUtils.DB_STORE_DATE);
			FileRenamePolicy policy = new FileRenamePolicyImpl(dir+File.separator+sonDir,userId,fileTime,prefix);
			String newFileName = userId+"_"+fileTime+prefix;
			multi = new MultipartRequest(req,dir+File.separator+sonDir,size,"UTF-8",policy);
			 TAttachementBusiness tab = new TAttachementBusiness();
			 tab.setCreator(userId);
			 tab.setCreatorTime(fileTime);
			 tab.setRecordId(req.getParameter("recordId"));
			 tab.setResourceId(req.getParameter("resourceId"));
			 tab.setResourceName(req.getParameter("resourceName"));
			 AttachementBusinessJdbcService abs = (AttachementBusinessJdbcService)EasyApplicationContextUtils.getBeanByName("attachementBusinessJdbcService");
			 String attachementBusinessId = abs.saveAttachementBusiness(tab);

			
			 TAttachement ta = new TAttachement();
			 ta.setAttachementCmd(description);
			 ta.setOldName(originalFile);
			 ta.setReadName(sonDir+File.separator+newFileName);
			 ta.setAttachementBusinessId(attachementBusinessId);//
			 ta.setAttachementId(CodeGenerator.getUUID());
			 ta.setCreator(userId);
			 ta.setCreatorTime(fileTime);
			 ta.setUploadTime(fileTime);
			 AttachementHibernateService service = (AttachementHibernateService)EasyApplicationContextUtils.getBeanByName("attachementHibernateService");
			 service.saveAttachement(ta);
			 
			 
		} catch (Exception e) {
			e.printStackTrace();
			exception = e.toString();
		}
		if(exception==null)
			resp.getWriter().write("{success:true,msg:'文件>"+originalFile+"<上传成功'}");
		else if(exception.indexOf("Posted content length")!=-1)
			resp.getWriter().write("{success:false,msg:'文件>"+originalFile+"<大小超过"+file_size+"kb'}");
		else if(exception.indexOf("Not a directory:")!=-1){
			resp.getWriter().write("{success:false,msg:'服务器保存的路径未创建'}");
		}else
			resp.getWriter().write("{success:false,msg:'"+exception+"'}");

	}

	/**
	 * 处理文件上传
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	private void processModifyAdd(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException {
		resp.setContentType("text/html; charset=UTF-8");
		String query = req.getQueryString();
		query = java.net.URLDecoder.decode(query,"utf-8");
		PropertiesUtil p = new PropertiesUtil(path);
		String description = query.substring(query.lastIndexOf("&description=")+13, query.lastIndexOf("&filepath="));
		String originalFile = query.substring(query.lastIndexOf("&filepath=")+10,query.lastIndexOf("&recordId="));
		String modifyAddTime =req.getParameter("modifyAddTime");
		
		modifyAddTime = modifyAddTime.replaceAll("-", "").replaceAll(" ", "").replaceAll(":", "");
		if(originalFile.indexOf("\\")!=-1){
			originalFile =originalFile.substring(originalFile.lastIndexOf("\\")+1);
		}else if(originalFile.indexOf("/")!=-1){
			originalFile =originalFile.substring(originalFile.lastIndexOf("/")+1);
		}
		String userId = req.getParameter("userId");
		//取得服务器存档文件夹路径
		String prefix = originalFile.substring(originalFile.lastIndexOf("."));
		String dir = p.getProperty("file_path");
		String sonDir = "2099-12";
		try{
		    sonDir = DateUtils.getDateFormat(modifyAddTime,"yyyyMMddHHmmss","yyyy-MM"); 
		}
		catch (ParseException e) {
			resp.getWriter().write("{success:false,msg:'上传时间格式错误'}");
			return;
		}
		File file = new File(dir+File.separator+sonDir);
	    if (!file.exists()&&!file.isDirectory()) 
             file.mkdirs();

/*		if(!file.isAbsolute()){
			dir = req.getRealPath(dir);
		}*/
		String file_size = p.getProperty("file_size");
		int size = Integer.valueOf(file_size)*1024;
		if(userId==null){
			resp.getWriter().write("{success:false,msg:'请重新登陆'}");
			return;
		}
		MultipartRequest multi= null;
		int flag =0 ;
		String exception=null;
		try {
			String fileTime = modifyAddTime;
			FileRenamePolicy policy = new FileRenamePolicyImpl(dir+File.separator+sonDir,userId,fileTime,prefix);
			String newFileName = userId+"_"+fileTime+prefix;
			multi = new MultipartRequest(req,dir+File.separator+sonDir,size,"UTF-8",policy);
			 TAttachementBusiness tab = new TAttachementBusiness();
			 tab.setCreator(userId);
			 tab.setCreatorTime(fileTime);
			 tab.setRecordId(req.getParameter("recordId"));
			 tab.setResourceId(req.getParameter("resourceId"));
			 tab.setResourceName(req.getParameter("resourceName"));
			 AttachementBusinessJdbcService abs = (AttachementBusinessJdbcService)EasyApplicationContextUtils.getBeanByName("attachementBusinessJdbcService");
			 String attachementBusinessId = abs.saveAttachementBusiness(tab);

			
			 TAttachement ta = new TAttachement();
			 ta.setAttachementCmd(description);
			 ta.setOldName(originalFile);
			 ta.setReadName(sonDir+File.separator+newFileName);
			 ta.setAttachementBusinessId(attachementBusinessId);//
			 ta.setAttachementId(CodeGenerator.getUUID());
			 ta.setCreator(userId);
			 ta.setCreatorTime(DateUtils.getCurrTime(DateUtils.DB_STORE_DATE));
			 ta.setUploadTime(fileTime);
			 AttachementHibernateService service = (AttachementHibernateService)EasyApplicationContextUtils.getBeanByName("attachementHibernateService");
			 service.saveAttachement(ta);
			 //
			 TAttachementLog log = new TAttachementLog();
			 BeanUtils.copyProperties(log, ta);
			 log.setMack("1");
			 log.setCmd("二期");
			 log.setNewAttachementCmd(ta.getAttachementCmd());
			 log.setNewOldName(ta.getReadName());
			 log.setNewReadName(ta.getReadName());
			 log.setOperater(userId);
			 abs.attachementLogRecord(log);
			 //
			 
			 
		} catch (Exception e) {
			e.printStackTrace();
			exception = e.toString();
		}
		if(exception==null)
			resp.getWriter().write("{success:true,msg:'文件>"+originalFile+"<上传成功'}");
		else if(exception.indexOf("Posted content length")!=-1)
			resp.getWriter().write("{success:false,msg:'文件>"+originalFile+"<大小超过"+file_size+"kb'}");
		else if(exception.indexOf("Not a directory:")!=-1){
			resp.getWriter().write("{success:false,msg:'服务器保存的路径未创建'}");
		}else
			resp.getWriter().write("{success:false,msg:'"+exception+"'}");

	}

	private void processModifyReplace(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException {
		resp.setContentType("text/html; charset=UTF-8");
		String query = req.getQueryString();
		query = java.net.URLDecoder.decode(query,"utf-8");
		PropertiesUtil p = new PropertiesUtil(path);
		String description = query.substring(query.lastIndexOf("&description=")+13, query.lastIndexOf("&filepath="));
		String originalFile = query.substring(query.lastIndexOf("&filepath=")+10,query.lastIndexOf("&recordId="));
		String attachementId = req.getParameter("attachementId");
	    AttachementHibernateService service = (AttachementHibernateService)EasyApplicationContextUtils.getBeanByName("attachementHibernateService");
	    TAttachement old = service.getAttachement(attachementId);
        
		String oldFilePath = old.getReadName();
		
		if(originalFile.indexOf("\\")!=-1){
			originalFile =originalFile.substring(originalFile.lastIndexOf("\\")+1);
		}else if(originalFile.indexOf("/")!=-1){
			originalFile =originalFile.substring(originalFile.lastIndexOf("/")+1);
		}
		String userId = req.getParameter("userId");
		//取得服务器存档文件夹路径
		String prefix = originalFile.substring(originalFile.lastIndexOf("."));
		String dir = p.getProperty("file_path");
		String sonDir = oldFilePath.substring(0,oldFilePath.indexOf("\\"));

		File file = new File(dir+File.separator+sonDir);
	    if (!file.exists()&&!file.isDirectory()) 
             file.mkdirs();

		String file_size = p.getProperty("file_size");
		int size = Integer.valueOf(file_size)*1024;

		MultipartRequest multi= null;
		int flag =0 ;
		String exception=null;
		try {
			String fileTime = old.getUploadTime();
			FileRenamePolicy policy = new FileRenamePolicyImpl(dir+File.separator+sonDir,userId,fileTime,prefix);
			String newFileName = userId+"_"+fileTime+prefix;
			multi = new MultipartRequest(req,dir+File.separator+sonDir,size,"UTF-8",policy);
			 TAttachementBusiness tab = new TAttachementBusiness();
			 tab.setCreator(userId);
			 tab.setCreatorTime(DateUtils.getCurrTime(DateUtils.DB_STORE_DATE));
			 tab.setRecordId(req.getParameter("recordId"));
			 tab.setResourceId(req.getParameter("resourceId"));
			 tab.setResourceName(req.getParameter("resourceName"));
			 AttachementBusinessJdbcService abs = (AttachementBusinessJdbcService)EasyApplicationContextUtils.getBeanByName("attachementBusinessJdbcService");
			 abs.saveAttachementBusiness(tab);

			 //
			 TAttachementLog log = new TAttachementLog();
			 BeanUtils.copyProperties(log, old);
			 log.setMack("2");
			 log.setNewAttachementCmd(description);
			 log.setNewOldName(originalFile);
			 log.setNewReadName(sonDir+File.separator+newFileName);
			 log.setCmd("二期");
			 log.setOperater(userId);
			 abs.attachementLogRecord(log);
			 //
			 old.setReadName(sonDir+File.separator+newFileName);
			 old.setAttachementCmd(description);
			 old.setOldName(originalFile);
			 old.setCreator(userId);
			 old.setCreatorTime(DateUtils.getCurrTime(DateUtils.DB_STORE_DATE));
			 service.saveOrUpdateAttachement(old);
			 
			 
		} catch (Exception e) {
			e.printStackTrace();
			exception = e.toString();
		}
		if(exception==null)
			resp.getWriter().write("{success:true,msg:'文件>"+originalFile+"<替换成功'}");
		else if(exception.indexOf("Posted content length")!=-1)
			resp.getWriter().write("{success:false,msg:'文件>"+originalFile+"<大小超过"+file_size+"kb'}");
		else if(exception.indexOf("Not a directory:")!=-1){
			resp.getWriter().write("{success:false,msg:'服务器保存的路径未创建'}");
		}else
			resp.getWriter().write("{success:false,msg:'"+exception+"'}");

	}
	
	private void processModifyDel(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException{
		String attachementId = req.getParameter("attachementId");
		AttachementHibernateService service = (AttachementHibernateService)EasyApplicationContextUtils.getBeanByName("attachementHibernateService");
		AttachementBusinessJdbcService abs = (AttachementBusinessJdbcService)EasyApplicationContextUtils.getBeanByName("attachementBusinessJdbcService");

		TAttachement old = service.getAttachement(attachementId);
		String userId = req.getParameter("userId");
		String callBack = req.getParameter("callback");
		resp.setContentType("text/html; charset=UTF-8");
		//
		TAttachementLog log = new TAttachementLog();
		try{
			BeanUtils.copyProperties(log, old);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		log.setMack("3");
		log.setCmd("二期");
		log.setOperater(userId);
		abs.attachementLogRecord(log);		 
		service.delAttachement(old);
		resp.getWriter().write(callBack + "('删除成功')");
	}
	
	private void fileUploadCheck(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		resp.setContentType("text/html; charset=UTF-8");
		String originalFile = req.getParameter("filepath");
		if(originalFile.indexOf("\\")!=-1){
			originalFile =originalFile.substring(originalFile.lastIndexOf("\\")+1);
		}else if(originalFile.indexOf("/")!=-1){
			originalFile =originalFile.substring(originalFile.lastIndexOf("/")+1);
		}
		String userId = req.getParameter("userId");
		AttachementBusinessJdbcService abs = (AttachementBusinessJdbcService)EasyApplicationContextUtils.getBeanByName("attachementBusinessJdbcService");
		int i = abs.fileUploadCheck(originalFile, userId,1);
		if(i>0)
		   resp.getWriter().write("{success:true,msg:'文件:"+originalFile+"上传成功'}");
		else resp.getWriter().write("{success:true,msg:'文件"+originalFile+"上传失败'}");

	}
	
	private void fileUploadModifyCheck(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		resp.setContentType("text/html; charset=UTF-8");
		String recordId = req.getParameter("recordId");
		AttachementBusinessJdbcService abs = (AttachementBusinessJdbcService)EasyApplicationContextUtils.getBeanByName("attachementBusinessJdbcService");
		int i = abs.fileUploadModifyCheck(recordId);
		if(i==1)
		   resp.getWriter().write("{success:true,msg:'true'}");
		else resp.getWriter().write("{success:true,msg:'false'}");

	}
	/**
	 * 处理Extjs tree
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	private void processTree(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException {
		String sql = (String) req.getSession().getAttribute("tree_sql");
		String parentId = (String) req.getSession().getAttribute("tree_parentId");
		TreeDataSource tds = new TreeDataSource();
		String nodeId = req.getParameter("id");
		List rs = tds.getRecords(sql,parentId,nodeId);
		JSONArray ja = JSONArray.fromObject(rs);
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html; charset=UTF-8");
		resp.getWriter().print(ja.toString().toLowerCase());
	}
	/**
	 * 处理Extjs grid
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	public void processGrid(HttpServletRequest req, HttpServletResponse resp,boolean flag)throws ServletException, IOException {
		String sql="";
		String columns="";
		int size=0;
		if(!flag){
			sql = req.getParameter("grid_sql").toUpperCase();
			columns = req.getParameter("grid_columns").toUpperCase();
			size = Integer.valueOf(req.getParameter("grid_size"));
		 }else{
			sql =(String)req.getAttribute("grid_sql");
			columns =(String)req.getAttribute("grid_columns");
			size = Integer.valueOf((String)req.getAttribute("grid_size"));
		}
		String stt = req.getParameter("start");
		int start = stt==null?0:Integer.valueOf(stt);
		gridDS = new OracleGridDataSource();
		int totalProperty = gridDS.countTotalRecords(sql);
		List rs =  gridDS.getRecords(sql, start+1, start+size>totalProperty?totalProperty:start+size,columns);
		JSONArray ja = JSONArray.fromObject(rs);
		JSONObject jo = new JSONObject();
		jo.put("totalProperty",totalProperty);
		jo.put("root", ja);
		String jsontext = jo.toString();
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html; charset=UTF-8");
		resp.getWriter().print(jsontext);
	}
	/**
	 * 获取请求文件
	 * @param url
	 * @param resp
	 * @param fileName
	 * @throws IOException
	 */
	public void requestFile(String url,HttpServletResponse resp,String fileName) throws IOException{
		File file = new File(url);
		if(!file.isDirectory()){
			URL httpUrl = new URL(url);
			URLConnection conn = httpUrl.openConnection();
			conn.setDoOutput(true);
			conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
			InputStream	inStream = conn.getInputStream();
			download(inStream, fileName, resp);
		}else{
			InputStream in = new FileInputStream(url+"/"+fileName);
			download(in, fileName, resp);
		}
	}
	/**
	 * 下载主程序
	 * @param inStream
	 * @param filename
	 * @param response
	 * @throws IOException
	 */
	private void download(InputStream inStream, String filename,HttpServletResponse response) throws IOException{
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
