/*
 * @(#)DictController.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：傅渊源
 *  时　间：2008-11-26
 *  描　述：创建
 */

package com.infolion.platform.console.dictionary.web;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

import com.infolion.platform.console.dictionary.domain.SysDict;
import com.infolion.platform.console.dictionary.service.SysDictService;
import com.infolion.platform.console.sys.context.UserContext;
import com.infolion.platform.console.sys.context.UserContextHolder;
import com.infolion.platform.core.web.BaseMultiActionController;
import com.infolion.platform.sys.Constants;
import com.infolion.platform.util.CodeGenerator;

public class DictController extends BaseMultiActionController {
	@Autowired
	private SysDictService tSysDictService;
	
	public void setTSysDictService(SysDictService sysDictService) {
		tSysDictService = sysDictService;
	}
	
	//从主界面的左边树弹出右边的标签页
	public ModelAndView dictManage(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		return new ModelAndView("console/dictionary/dictManage");
	}
	
	//进入增加与修改的页面
	public ModelAndView addDictionacyView(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String strDictId = request.getParameter("dictid");
		SysDict tSysDict = new SysDict();

		if (strDictId !=  null && !"".equals(strDictId)){
			tSysDict = tSysDictService.queryDictionaryById(strDictId);
		}
		request.setAttribute("dict", tSysDict);
		
		return new ModelAndView("console/dictionary/addDictionary");
	}
	//在增加修改页面按确认按键提交的动作
	public void addDictionary(HttpServletRequest request,
			HttpServletResponse response, SysDict tSysDict)
			throws IOException {
		String strId= tSysDict.getDictid();
		
		if (tSysDict.getDictkeycolumn() == null){
			tSysDict.setDictkeycolumn("");
		}
		
		if (tSysDict.getDictparentcolumn() == null){
			tSysDict.setDictparentcolumn("");
		}
		
		if (tSysDict.getCmd() == null){
			tSysDict.setCmd("");
		}
		
		if (strId == null || "".equals(strId)) {
			strId = CodeGenerator.getUUID();
			tSysDict.setDictid(strId);
//			UserContext userContext = (UserContext)request.getSession().getAttribute(Constants.USER_CONTEXT_NAME);
			UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
			tSysDict.setCreator(userContext.getSysUser().getUserId());
			
			tSysDictService.addDictionary(tSysDict);
			this.operateSuccessfully(response);
		}else{
			tSysDictService.updateDictionary(tSysDict);
			this.operateSuccessfully(response);
		}
	}
	//删除动作
	public void deleteDictionary(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String strDictList = request.getParameter("dictid");

		tSysDictService.deleteDictionary(strDictList);
		this.operateSuccessfully(response);
	}

	public void queryDictionaryInfo(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		SysDict tSysDict = new SysDict();
		tSysDict.setDicttable(request.getParameter("dicttable"));
		tSysDict.setDictcodecolumn(request.getParameter("dictcodecolumn"));
		tSysDict.setDictnamecolumn(request.getParameter("dictnamecolumn"));
		tSysDict.setDicttype(request.getParameter("dicttype"));
		tSysDict.setDictkeycolumn(request.getParameter("dictkeycolumn"));
		tSysDict.setDictparentcolumn(request.getParameter("dictparentcolumn"));
		
		List<SysDict> tSysDictList = tSysDictService.queryDictionaryInfo(tSysDict);
		JSONArray ja = JSONArray.fromObject(tSysDictList);

		JSONObject jo = new JSONObject();
		jo.put("totalProperty", tSysDictList.size());
		jo.put("root", ja);

		String jsontext = jo.toString();
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		response.getWriter().print(jsontext);		
	}		
}
