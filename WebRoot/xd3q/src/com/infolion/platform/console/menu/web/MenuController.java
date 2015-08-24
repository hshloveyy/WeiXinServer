/*
 * @(#)menuController.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：傅渊源
 *  时　间：2008-11-18
 *  描　述：创建
 */

package com.infolion.platform.console.menu.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

import com.infolion.platform.console.menu.domain.AsyncTreeNode;
import com.infolion.platform.console.menu.domain.SysResource;
import com.infolion.platform.console.menu.service.SysResourceService;
import com.infolion.platform.console.sys.context.UserContext;
import com.infolion.platform.core.web.BaseMultiActionController;
import com.infolion.platform.sys.Constants;
import com.infolion.platform.util.CodeGenerator;

public class MenuController extends BaseMultiActionController {
	@Autowired
	private SysResourceService tSysResourceService;

	public void setTSysResourceService(SysResourceService sysResourceService) {
		tSysResourceService = sysResourceService;
	}

	public ModelAndView menuManage(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		return new ModelAndView("console/menu/menuManage");
	}
	
	public ModelAndView testManage(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		return new ModelAndView("console/menu/test");
	}

	public void getResourceByParentId(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String strParentId = request.getParameter("id");

		List<SysResource> resourceObjectList = tSysResourceService
				.getResourceByParentId(strParentId);

		List<AsyncTreeNode> treeNodelist = new ArrayList();

		SysResource resourceObject = null;
		for (int i = 0; i < resourceObjectList.size(); i++) {
			resourceObject = resourceObjectList.get(i);
			AsyncTreeNode asynTreeNode = new AsyncTreeNode();
			asynTreeNode.setId(resourceObject.getResourceid());
			asynTreeNode.setText(resourceObject.getResourcetitle());
			asynTreeNode.setCls("folder");
			asynTreeNode.setHrefTarget("");
			// 如果孩子结点数为０则为叶结点
			if (resourceObject.getChildcount() == 0)
				asynTreeNode.setLeaf(true);
			else
				asynTreeNode.setLeaf(false);

			treeNodelist.add(asynTreeNode);
		}

		JSONArray ja = JSONArray.fromObject(treeNodelist);

		String jsontext = ja.toString();
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		response.getWriter().print(jsontext);
	}

	public void getResourceByParentIdToGrip(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String strParentId = request.getParameter("id");

		List<SysResource> resourceObjectList = tSysResourceService
				.getResourceByParentId(strParentId);

		JSONArray ja = JSONArray.fromObject(resourceObjectList);

		JSONObject jo = new JSONObject();
		jo.put("totalProperty", resourceObjectList.size());
		jo.put("root", ja);

		String jsontext = jo.toString();

		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		response.getWriter().print(jsontext);
	}

	public void getResourceById(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String strId = request.getParameter("id");

		SysResource tSysresource = tSysResourceService.getResourceById(strId);

		JSONObject jo = JSONObject.fromObject(tSysresource);

		String jsontext = jo.toString();
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		response.getWriter().print(jsontext);
	}

	public void addResource(HttpServletRequest request,
			HttpServletResponse response, SysResource tSysResource)
			throws IOException {
		String strId = tSysResource.getResourceid();

		if (strId == null || "new".equals(strId)) {
			strId = CodeGenerator.getUUID();
			UserContext userContext = (UserContext)request.getSession().getAttribute(Constants.USER_CONTEXT_NAME);
			tSysResource.setCreator(userContext.getSysUser().getUserId());
			tSysResource.setResourceid(strId);

			tSysResourceService.addResource(tSysResource);
		} else {

			tSysResourceService.updateResource(tSysResource);
		}
		this.operateSuccessfully(response);
	}

	public void deleteResource(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String strId = request.getParameter("id");
		tSysResourceService.deleteResource(strId);

		JSONObject jo = new JSONObject();
		jo.put("success", "true");
		jo.put("message", "删除资源成功！");
		String jsontext = jo.toString();
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		response.getWriter().print(jsontext);
	}
}
