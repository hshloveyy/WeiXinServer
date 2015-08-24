package com.infolion.sapss.bapi.web;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

import com.infolion.platform.core.BusinessException;
import com.infolion.platform.core.web.BaseMultiActionController;
import com.infolion.sapss.bapi.BapiConstant;
import com.infolion.sapss.bapi.dao.BapiJdbcDao;
import com.infolion.sapss.bapi.service.BapiHibernateService;
import com.infolion.sapss.bapi.service.BapiJdbcService;
import com.infolion.sapss.project.service.ProjectHibernateService;
import com.infolion.sapss.project.service.ProjectJdbcService;
/**
 * 
 * <pre>字典表同步控制器</pre>
 *
 * <br>
 * JDK版本:1.5
 *
 * @version 1.0
 * @see The author for more details
 * @since 1.0
 */
public class DictionarySyncController extends BaseMultiActionController{
	
	// 服务类注入
	@Autowired
	private BapiJdbcService bapiJdbcService;

	
	public void syncAll(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		bapiJdbcService.synchronizeAllData();
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		JSONObject jo = new JSONObject();
		jo.put("returnMessage", "操作成功！");
		this.operateSuccessfullyWithString(response, jo.toString());
	}
	
	public ModelAndView find(HttpServletRequest req,
			HttpServletResponse resp) throws Exception {
		//projectJdbcService.insertProjectMemo();
		String pathChar = Thread.currentThread().getContextClassLoader().getResource("").getPath();
		File file = new File(pathChar.substring(1,(pathChar.length()-1))+BapiConstant.DICTIONARY_PATH);
        String[] fileNames = file.list();
        req.setAttribute("files", Arrays.asList(fileNames));
		return new ModelAndView("sapss/bapi/dictionaryManager");
	}
	
	public void sysByName(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String file = request.getParameter("file_name");
		if(file==null||"".equals(file))
			 throw new BusinessException("文件名为空");
		String name = file.substring(0, file.indexOf("."));
		bapiJdbcService.synchronizeData(file.substring(0, file.indexOf(".")), null);
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		JSONObject jo = new JSONObject();
		jo.put("returnMessage", "操作成功！");
		this.operateSuccessfullyWithString(response, jo.toString());
	}
	
	
}
