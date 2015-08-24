/*
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：傅渊源
 *  时　间：2008-12-1
 *  描　述：创建
 */

package com.infolion.platform.console.org.web;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

import com.infolion.platform.component.workflow.ext.CommonProcessInstance;
import com.infolion.platform.console.menu.domain.AsyncTreeCheckNode;
import com.infolion.platform.console.menu.domain.AsyncTreeNode;
import com.infolion.platform.console.menu.domain.SysResource;
import com.infolion.platform.console.menu.service.SysResourceService;
import com.infolion.platform.console.org.domain.SysDept;
import com.infolion.platform.console.org.domain.SysRole;
import com.infolion.platform.console.org.domain.SysUser;
import com.infolion.platform.console.org.domain.SysUserAuth;
import com.infolion.platform.console.org.service.SysDeptService;
import com.infolion.platform.console.org.service.SysRoleService;
import com.infolion.platform.console.org.service.SysUserAuthService;
import com.infolion.platform.console.org.service.SysUserService;
import com.infolion.platform.console.sys.context.UserContext;
import com.infolion.platform.console.sys.context.UserContextHolder;
import com.infolion.platform.console.workflow.domain.ComboBoxFormat;
import com.infolion.platform.console.workflow.service.SysWfCommonalityService;
import com.infolion.platform.console.workflow.service.SysWfTaskActorService;
import com.infolion.platform.core.BusinessException;
import com.infolion.platform.core.web.BaseMultiActionController;
import com.infolion.platform.sys.Constants;
import com.infolion.platform.util.CodeGenerator;
import com.infolion.platform.util.DateUtils;

public class OrgController extends BaseMultiActionController {
	@Autowired
	private SysDeptService sysDeptService;
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SysRoleService sysRoleService;
	@Autowired
	private SysResourceService sysResourceService;
	@Autowired
	private SysUserAuthService sysUserAuthService;
	@Autowired
	private SysWfCommonalityService sysWfCommonalityService;
	
	public void setSysWfCommonalityService(
			SysWfCommonalityService sysWfCommonalityService) {
		this.sysWfCommonalityService = sysWfCommonalityService;
	}
	@Autowired
	private SysWfTaskActorService sysWfTaskActorService;

	public void setSysWfTaskActorService(SysWfTaskActorService sysWfTaskActorService) {
		this.sysWfTaskActorService = sysWfTaskActorService;
	}
	
	public void setSysDeptService(SysDeptService sysDeptService) {
		this.sysDeptService = sysDeptService;
	}
	
	public void setSysUserService(SysUserService sysUserService) {
		this.sysUserService = sysUserService;
	}
	
	public void setSysRoleService(SysRoleService sysRoleService) {
		this.sysRoleService = sysRoleService;
	}
	
	public void setSysResourceService(SysResourceService sysResourceService) {
		this.sysResourceService = sysResourceService;
	}
	
	public void setSysUserAuthService(SysUserAuthService sysUserAuthService) {
		this.sysUserAuthService = sysUserAuthService;
	}
	
	public ModelAndView orgManage(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		return new ModelAndView("console/org/orgManage");
	}
	//该方法包括增加和修改两个功能，如果传入的部门编号有值的话就为修改
	//如果传入的部门编号为空或为new则为增加
	public void addDept(HttpServletRequest request,
			HttpServletResponse response, SysDept sysDept)
	throws IOException {
		String strId = sysDept.getDeptid();

		if (strId == null || "new".equals(strId)) {
			strId = CodeGenerator.getUUID();
			UserContext userContext = (UserContext)request.getSession().getAttribute(Constants.USER_CONTEXT_NAME);
			sysDept.setCreator(userContext.getSysUser().getUserId());
			sysDept.setDeptid(strId);

			sysDeptService.addDept(sysDept);
		} else {

			sysDeptService.updateDept(sysDept);
		}
		this.operateSuccessfully(response);
	}
	
	public void deleteDept(HttpServletRequest request,
			HttpServletResponse response){
		String strDeptId = request.getParameter("deptid");
		sysDeptService.deleteDept(strDeptId);
	}
	
	/**
	 * 通过部门编号查询该部门的信息
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	
	public void queryDeptById(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String strDeptId = request.getParameter("deptid");

		SysDept tSysDept = new SysDept();
		
		tSysDept = sysDeptService.queryDeptById(strDeptId);
		
		JSONObject jo = JSONObject.fromObject(tSysDept);
		String jsontext = jo.toString();
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		response.getWriter().print(jsontext);
	}
	
	/**
	 * 通过父编号信息查询下挂的部门信息
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void queryDeptByParentId(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String strParentId = request.getParameter("id");

		List<SysDept> tSysDeptList = sysDeptService.queryDeptByParentId(strParentId);
		List<AsyncTreeNode> treeNodelist = new ArrayList();
		
		SysDept tSysDept = new SysDept();
		for (int i = 0; i < tSysDeptList.size(); i++) {
			tSysDept = tSysDeptList.get(i);
			
			AsyncTreeNode asynTreeNode = new AsyncTreeNode();
			asynTreeNode.setId(tSysDept.getDeptid());
			asynTreeNode.setText(tSysDept.getDeptname());
			asynTreeNode.setCls("folder");
			asynTreeNode.setHrefTarget("");
			// 如果孩子结点数为０则为叶结点
			if (tSysDept.getChildcount() == 0)
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
	
	/**
	 * 通过登陆的人员查询该人员的部门信息情况
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	
	public void queryDeptByLoginUser(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String strParentId = request.getParameter("id");
		
		UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
		String strDeptId = userContext.getSysDept().getDeptid();
		
		List<AsyncTreeNode> treeNodelist = new ArrayList();
		SysDept tSysDept = new SysDept();
		if (strParentId.equals("-1")){
			AsyncTreeNode asynTreeNode = new AsyncTreeNode();
			tSysDept = sysDeptService.queryDeptById(strDeptId);
			
			asynTreeNode.setId(tSysDept.getDeptid());
			asynTreeNode.setText(tSysDept.getDeptname());
			asynTreeNode.setCls("folder");
			asynTreeNode.setHrefTarget("");
			// 如果孩子结点数为０则为叶结点
			if (tSysDept.getChildcount() == 0)
				asynTreeNode.setLeaf(true);
			else
				asynTreeNode.setLeaf(false);
			
			treeNodelist.add(asynTreeNode);
		}else{
			List<SysDept> tSysDeptList = sysDeptService.queryDeptByParentId(strParentId);
			for (int i = 0; i < tSysDeptList.size(); i++) {
				tSysDept = tSysDeptList.get(i);
				
				AsyncTreeNode asynTreeNode = new AsyncTreeNode();
				asynTreeNode.setId(tSysDept.getDeptid());
				asynTreeNode.setText(tSysDept.getDeptname());
				asynTreeNode.setCls("folder");
				asynTreeNode.setHrefTarget("");
				// 如果孩子结点数为０则为叶结点
				if (tSysDept.getChildcount() == 0)
					asynTreeNode.setLeaf(true);
				else
					asynTreeNode.setLeaf(false);
				
				treeNodelist.add(asynTreeNode);
			}
		}
		
		JSONArray ja = JSONArray.fromObject(treeNodelist);

		String jsontext = ja.toString();
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		response.getWriter().print(jsontext);
	}
	
	/**
	 * 通过门的父编号查询其下挂的部门信息给前台的grid
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void queryDeptByParentIdToGrid(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String strParentId = request.getParameter("id");

		List<SysDept> tSysDeptList = sysDeptService.queryDeptByParentId(strParentId);
		
		JSONArray ja = JSONArray.fromObject(tSysDeptList);

		JSONObject jo = new JSONObject();
		jo.put("totalProperty", tSysDeptList.size());
		jo.put("root", ja);

		String jsontext = jo.toString();

		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		response.getWriter().print(jsontext);
	}

	/*****************员工实例表操作************************/
	public void queryUserByDeptIdToGrid(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String strDeptId = request.getParameter("deptid");
		
		List<SysUser> sysUserList = sysUserService.queryUserByDeptId(strDeptId);
		
		JSONArray ja = JSONArray.fromObject(sysUserList);

		JSONObject jo = new JSONObject();
		jo.put("totalProperty", sysUserList.size());
		jo.put("root", ja);

		String jsontext = jo.toString();

		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		response.getWriter().print(jsontext);
	}
	
	/**
	 *  通过用户编号查询用户信息
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void queryUserByUserId(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String strUserId = request.getParameter("userid");
		
		List<SysUser> sysUserList = sysUserService.queryUserByUserId(strUserId);
		
		JSONArray ja = JSONArray.fromObject(sysUserList);

		JSONObject jo = new JSONObject();
		jo.put("totalProperty", sysUserList.size());
		jo.put("root", ja);

		String jsontext = jo.toString();

		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		response.getWriter().print(jsontext);
	}
	
	/**
	 * 通过条件查询用户信息
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void queryUserByCondition(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		SysUser sysUser = new SysUser();
		sysUser.setUserName(request.getParameter("userName"));
		sysUser.setRealName(request.getParameter("realName"));
		sysUser.setSex(request.getParameter("sex"));
		sysUser.setIdCard(request.getParameter("idCard"));
		
		List<SysUser> sysUserList = sysUserService.queryUserByCondition(sysUser);
		
		JSONArray ja = JSONArray.fromObject(sysUserList);

		JSONObject jo = new JSONObject();
		jo.put("totalProperty", sysUserList.size());
		jo.put("root", ja);

		String jsontext = jo.toString();

		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		response.getWriter().print(jsontext);
	}
	
	/**
	 * 进入增加与修改用户信息的页面
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ModelAndView addUserView(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String strUserId = request.getParameter("userid");
		String strDeptId = request.getParameter("deptid");
		SysUser sysUser = new SysUser();

		if (strUserId !=  null && !"".equals(strUserId)){
			sysUser = sysUserService.queryUserById(strUserId,strDeptId);
		}
		else{
			String strDeptName = sysDeptService.queryDeptById(strDeptId).getDeptname();
			
			sysUser.setDeptId(strDeptId);
			sysUser.setDeptName(strDeptName);
		}
		request.setCharacterEncoding("GBK");
		request.setAttribute("user", sysUser);
		
		return new ModelAndView("console/org/addUser");
	}
	
	/**
	 * 进入员工信息修改界面
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ModelAndView modiUserInfoView(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String strUserId = request.getParameter("userid");
		String strDeptId = request.getParameter("deptid");
		SysUser sysUser = new SysUser();


		if (strUserId !=  null && !"".equals(strUserId)){
			sysUser = sysUserService.queryUserById(strUserId,strDeptId);
		}
		else{
			UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
			strUserId = userContext.getSysUser().getUserId();
			strDeptId = userContext.getSysDept().getDeptid();
			sysUser = sysUserService.queryUserById(strUserId,strDeptId);
		}
		
		request.setCharacterEncoding("GBK");
		request.setAttribute("user", sysUser);
		return new ModelAndView("console/org/modiUserInfo");
	}
	
	/**
	 * 进入员工所属部门页面
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ModelAndView userBelong(HttpServletRequest request,
			HttpServletResponse response) throws IOException {	
		request.setAttribute("userid", request.getParameter("userid"));
		return new ModelAndView("console/org/userBelong");
	}
	
	/**
	 * 进入选择员工页面
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ModelAndView addDeptUserView(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		
		request.setAttribute("deptid", request.getParameter("deptid"));
		return new ModelAndView("console/org/addDeptUser");
	}
	
	/**
	 * 从员工管理界面进入员工角色授权界面
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ModelAndView addUserRoleView(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		request.setAttribute("deptuserid", request.getParameter("deptuserid"));
		return new ModelAndView("console/org/addUserRole");
	}
	
	/**
	 * 员工管理界面
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ModelAndView userManage(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		return new ModelAndView("console/org/userManage");
	}
	
	/**
	 * 判断员工是否存在
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void queryUserIsExist(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String strUserName = request.getParameter("username");
		int recordCount = sysUserService.queryUserIsExist(strUserName);
		JSONObject jo = new JSONObject();
		jo.put("count", recordCount);
		String jsontext = jo.toString();
		response.getWriter().print(jsontext);
	}
	
	/**
	 * 增加和修改员工信息
	 * @param request
	 * @param response
	 * @param sysUser
	 * @throws IOException
	 */
	public void addUser(HttpServletRequest request,
			HttpServletResponse response, SysUser sysUser)
	throws IOException {
		String strId = sysUser.getUserId();
		String strPassword = sysUser.getPassword();
		sysUser.setPassword(CodeGenerator.getMD5Digest(strPassword));
		if (strId == null || "new".equals(strId) || "".equals(strId)) {
			strId = CodeGenerator.getUUID();
			UserContext userContext = (UserContext)request.getSession().getAttribute(Constants.USER_CONTEXT_NAME);
			sysUser.setCreator(userContext.getSysUser().getUserId());
			sysUser.setUserId(strId);
			sysUser.setDeptUserId(strId);
			

			sysUserService.addUser(sysUser);
			this.operateSuccessfullyWithString(response, strId);
		} else {

			sysUserService.updateUser(sysUser);
			this.operateSuccessfullyWithString(response, strId);
		}
	}
	
	public void saveNo(HttpServletRequest request,
			HttpServletResponse response, SysUser sysUser)
	throws IOException {
		String strId = sysUser.getUserId();
		if(strId==null) throw new BusinessException("请选保存用户！");

		sysUserService.updateUserNo(sysUser);
		this.operateSuccessfullyWithString(response, strId);
		
	}
	
	/**
	 * 删除员工信息
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void deleteUser(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String UserIdList = request.getParameter("userlist");
		String strDeptId = request.getParameter("deptid");
		sysUserService.deleteUser(UserIdList, strDeptId);
		this.operateSuccessfully(response);
	}
	
	/**
	 * 修改登陆员工的密码
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void updateUserPassword(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String strOldPass = request.getParameter("oldpass");
		String strNewPass = request.getParameter("newpass");

		int rst = sysUserService.updateUserPassword(strNewPass, strOldPass);
		
		JSONObject jo = new JSONObject();
		if(rst==1){
			jo.put("success", "更改密码成功");
		}else if(rst==0){
			jo.put("success", "更改密码失败");
		}
			
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		response.getWriter().print(jo.toString());
		
	}
	
	public ModelAndView modiPasswordView(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		return new ModelAndView("console/login/modiPassword");
	}
	
	/**
	 * 增加部门员工信息
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void addDeptUser(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String UserIdList = request.getParameter("userlist");
		String strDeptId = request.getParameter("deptid");
		UserContext userContext = (UserContext)request.getSession().getAttribute(Constants.USER_CONTEXT_NAME);
		
		sysUserService.addDeptUser(UserIdList, strDeptId, userContext.getSysUser().getUserId());
		
		this.operateSuccessfully(response);
	}
	
	/*****************角色实例操作部份************************/
	
	/**
	 * 通过父编号查询该父编下的子角色信息
	 */
	public void queryRoleByParentId(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String strParentId = request.getParameter("id");

		List<SysRole> tSysRoleList = sysRoleService.queryRoleByParentId(strParentId);
		List<AsyncTreeNode> treeNodelist = new ArrayList();
		
		SysRole sysRole = new SysRole();
		for (int i = 0; i < tSysRoleList.size(); i++) {
			sysRole = tSysRoleList.get(i);
			
			AsyncTreeNode asynTreeNode = new AsyncTreeNode();
			asynTreeNode.setId(sysRole.getRoleId());
			asynTreeNode.setText(sysRole.getRoleName());
			asynTreeNode.setCls("folder");
			asynTreeNode.setHrefTarget("");
			// 如果孩子结点数为０则为叶结点
			if (sysRole.getChildcount() == 0)
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
	
	/**
	 * 通过父编号查询角色信息给grid
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void queryRoleByParentIdToGrid(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String strParentId = request.getParameter("id");
		
		List<SysRole> tSysRoleList = sysRoleService.queryRoleByParentId(strParentId);
		
		JSONArray ja = JSONArray.fromObject(tSysRoleList);

		JSONObject jo = new JSONObject();
		jo.put("totalProperty", tSysRoleList.size());
		jo.put("root", ja);

		String jsontext = jo.toString();

		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		response.getWriter().print(jsontext);
	}

	/**
	 * 通过角色编号查询该编号的角色信息
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void queryRoleByRoleId(HttpServletRequest request,
	HttpServletResponse response) throws IOException {
		String strRoleId = request.getParameter("roleid");

		SysRole sysRole = new SysRole();
		
		sysRole = sysRoleService.queryRoleByRoleId(strRoleId);
		
		JSONObject jo = JSONObject.fromObject(sysRole);
		String jsontext = jo.toString();

		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		response.getWriter().print(jsontext);
	}
	
	/**
	 * 通过角色编号查询资源信息
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void queryResourceByRoleId(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String strRoleId = request.getParameter("roleid");
		
		List<SysResource> resourceObjectList = sysRoleService.queryResourceByRoleId(strRoleId);

		JSONArray ja = JSONArray.fromObject(resourceObjectList);
		
		JSONObject jo = new JSONObject();
		jo.put("totalProperty", resourceObjectList.size());
		jo.put("root", ja);
		
		String jsontext = jo.toString();

		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		response.getWriter().print(jsontext);
	}
	
	/**
	 * 通过角色编号查询该角色的下挂资源给树
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void queryResourceForTree(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String strParentId = request.getParameter("id");
		String strRoleId = request.getParameter("roleid");
		String strUpRoleId = request.getParameter("uproleid");

		List<SysResource> resourceObjectList = new ArrayList();

		resourceObjectList = sysRoleService.queryResourceForTree(strRoleId, strParentId,strUpRoleId);
		
		List<AsyncTreeCheckNode> treeNodelist = new ArrayList();

		SysResource resourceObject = null;
		for (int i = 0; i < resourceObjectList.size(); i++) {
			resourceObject = resourceObjectList.get(i);
			AsyncTreeCheckNode asynTreeNode = new AsyncTreeCheckNode();
			asynTreeNode.setId(resourceObject.getResourceid());
			asynTreeNode.setText(resourceObject.getResourcetitle());
			asynTreeNode.setCls("folder");
			asynTreeNode.setHrefTarget("");
			// 如果孩子结点数为０则为叶结点
			if (resourceObject.getChildcount() == 0)
				asynTreeNode.setLeaf(true);
			else
				asynTreeNode.setLeaf(false);
			
			//asynTreeNode.setChecked(true);
			if (resourceObject.getIscheck() == 0)
				asynTreeNode.setChecked(false);
			else
				asynTreeNode.setChecked(true);

			treeNodelist.add(asynTreeNode);
		}

		JSONArray ja = JSONArray.fromObject(treeNodelist);

		String jsontext = ja.toString();
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		response.getWriter().print(jsontext);
	}
	
	/**
	 * 通过登陆的用户查询该用户的角色信息
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void queryRoleByLoginUser(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String strNodeId = request.getParameter("id");
		
		UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
		String strDeptUserId = userContext.getSysUser().getDeptUserId();
		
		List<AsyncTreeNode> treeNodelist = new ArrayList();
		if (strNodeId.equals("-1")){
			List<String> roleList = sysRoleService.queryUserRoleByDeptUserId(strDeptUserId);
			
			for (int i=0;i<roleList.size();i++){
				AsyncTreeNode asynTreeNode = new AsyncTreeNode();
				SysRole sysRole = new SysRole();
				String roleId = (String) roleList.get(i);
				sysRole = sysRoleService.queryRoleByRoleId(roleId);
				
				asynTreeNode.setId(sysRole.getRoleId());
				asynTreeNode.setText(sysRole.getRoleName());
				asynTreeNode.setCls("folder");
				asynTreeNode.setHrefTarget("");
				// 如果孩子结点数为０则为叶结点
				if (sysRole.getChildcount() == 0)
					asynTreeNode.setLeaf(true);
				else
					asynTreeNode.setLeaf(false);
				
				treeNodelist.add(asynTreeNode);
			}
		}else{
			List<SysRole> sysRoleList = sysRoleService.queryRoleByParentId(strNodeId);

			for (int i = 0; i < sysRoleList.size(); i++) {
				AsyncTreeNode asynTreeNode = new AsyncTreeNode();
				SysRole sysRole = new SysRole();
				
				sysRole = sysRoleList.get(i);
				asynTreeNode.setId(sysRole.getRoleId());
				asynTreeNode.setText(sysRole.getRoleName());
				asynTreeNode.setCls("folder");
				asynTreeNode.setHrefTarget("");
				// 如果孩子结点数为０则为叶结点
				if (sysRole.getChildcount() == 0)
					asynTreeNode.setLeaf(true);
				else
					asynTreeNode.setLeaf(false);
				
				treeNodelist.add(asynTreeNode);
			}
		}

		JSONArray ja = JSONArray.fromObject(treeNodelist);
		String jsontext = ja.toString();
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		response.getWriter().print(jsontext);
	}
	
	/**
	 * 通过部门员工编号查询该员工的角色信息
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	
	public void queryRoleByDeptUserId(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String strDeptUserId = request.getParameter("deptuserid");

		List<SysRole> sysRoleList = sysRoleService.queryRoleByDeptUserId(strDeptUserId);
		
		JSONArray ja = JSONArray.fromObject(sysRoleList);

		JSONObject jo = new JSONObject();
		jo.put("totalProperty", sysRoleList.size());
		jo.put("root", ja);

		String jsontext = jo.toString();

		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		response.getWriter().print(jsontext);
	}
	
	/**
	 * 通过角色编号查询该拥有该角色的员工信息
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void queryUserByRoleId(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String strRoleId = request.getParameter("roleid");
		
		List<SysUser> sysUserList = sysUserService.queryUserByRoleId(strRoleId);
		
		JSONArray ja = JSONArray.fromObject(sysUserList);

		JSONObject jo = new JSONObject();
		jo.put("totalProperty", sysUserList.size());
		jo.put("root", ja);

		String jsontext = jo.toString();

		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		response.getWriter().print(jsontext);
	}
	
	/**
	 * 查询接收用户信息
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void receiveUserStaff(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String strRoleName = request.getParameter("roleName");
		String strDeptId = request.getParameter("AdvanceWarnRecv.createdeptid");
		
		List<SysUser> sysUserList = sysUserService.receiveUserStaff(strRoleName,strDeptId);
		
		JSONArray ja = JSONArray.fromObject(sysUserList);

		JSONObject jo = new JSONObject();
		jo.put("totalProperty", sysUserList.size());
		jo.put("root", ja);

		String jsontext = jo.toString();

		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		response.getWriter().print(jsontext);
	}
	
	/**
	 * 增加和修改角色信息，如果传入的编号为空或者为new则为新增
	 * 如果传入的编号已经为存在的了就为修改
	 * @param request
	 * @param response
	 * @param sysRole
	 * @throws IOException
	 */
	public void addRole(HttpServletRequest request,
			HttpServletResponse response, SysRole sysRole)
	throws IOException {
		String strId = sysRole.getRoleId();

		if (strId == null || "new".equals(strId) || "".equals(strId)) {
			sysRoleService.addRole(sysRole);
			this.operateSuccessfully(response);
		} else {

			sysRoleService.updateRole(sysRole);
			this.operateSuccessfully(response);
		}
	}
	
	/**
	 * 删除角色信息，只做逻辑上的删除
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void deleteRole(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String strRoleId = request.getParameter("roleid");
		
		sysRoleService.deleterRole(strRoleId);
		this.operateSuccessfully(response);
	}
	
	/**
	 * 增加该角色的资源
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void addRoleResource(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String strRoleId = request.getParameter("roleid");
		String strResourceList  = request.getParameter("resourcelist");
		
		sysRoleService.addRoleResource(strResourceList, strRoleId);
		this.operateSuccessfully(response);
	}
	
	/**
	 * 删除角色下的资源
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void deleteRoleResource(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String strRoleId = request.getParameter("roleid");
		String strResourceList  = request.getParameter("resourcelist");
		
		sysRoleService.deleteRoleResource(strResourceList, strRoleId);
		this.operateSuccessfully(response);
	}
	
	/**
	 * 删除该角色下的所有资源
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void deleteAllRoleResource(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String strRoleId = request.getParameter("roleid");
		
		sysRoleService.deleteAllRoleResource(strRoleId);
		this.operateSuccessfully(response);
	}
	
	/**
	 * 增加员工角色信息
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void addUserRole(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String strRoleIdList = request.getParameter("roleidlist");
		String strDeptUserId = request.getParameter("deptuserid");
		sysRoleService.addUserRole(strRoleIdList, strDeptUserId);
		this.operateSuccessfully(response);
	}
	
	/**
	 * 增加员工角色信息
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void addRoleUser(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String strDeptUserList = request.getParameter("deptuserlist");
		String strRoleId = request.getParameter("roleid");
		sysRoleService.addRoleUser(strDeptUserList, strRoleId);
		this.operateSuccessfully(response);
	}
	
	/**
	 * 删除员工角色信息
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void deleteUserRole(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String strRoleIdList = request.getParameter("roleidlist");
		String strDeptUserId = request.getParameter("deptuserid");
		sysRoleService.deleteUserRole(strRoleIdList, strDeptUserId);
		this.operateSuccessfully(response);
	}
	
	/**
	 * 进入角色管理界面的控制器
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ModelAndView roleManage(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		return new ModelAndView("console/org/roleManage");
	}
	
	/**
	 * 进入增加角色资源窗体的控制器
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ModelAndView addRoleResourceView(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		request.setAttribute("roleid", request.getParameter("roleid"));
		request.setAttribute("uproleid", request.getParameter("uproleid"));
		return new ModelAndView("console/org/addRoleResource");
	}
	
	/**
	 * 通过角色管理进入增加员工角色页面
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ModelAndView addRoleUserView(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		request.setAttribute("roleid", request.getParameter("roleid"));
		return new ModelAndView("console/org/addRoleUser");
	}
	
	/**
	 * 进入角色所拥有员工界面
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ModelAndView roleUser(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		request.setAttribute("roleid", request.getParameter("roleid"));
		return new ModelAndView("console/org/roleUser");
	}
	
	/**
	 * 进入选择员工界面
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ModelAndView receiveUser(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		return new ModelAndView("xdss3/advanceWarn/receiveUser");
	}
	
	/*************权限转移管理部份***********************/
	/**
	 * 进入权限转移操作界面
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ModelAndView transManage(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		return new ModelAndView("console/org/menuTransferManage");
	}
	
	/**
	 * 进入权限转移查询界面
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ModelAndView transSelecte(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		return new ModelAndView("console/org/menuTransferSelect");
	}
	/**
	 * 进入权限审批查看界面
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ModelAndView viewApproved(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		request.setAttribute("toUserId", request.getParameter("toUserId"));
		request.setAttribute("startTime", request.getParameter("startTime"));
		request.setAttribute("endTime", request.getParameter("endTime"));
		request.setAttribute("toId", request.getParameter("toId"));
		return new ModelAndView("console/org/approvedView");
	}
	
	private Map<String,String> extractFR(HttpServletRequest req) {
		try {
			String wait = java.net.URLDecoder.decode(req.getQueryString(),"UTF-8");
			String ar1[] = wait.split("&");
			Map<String,String> map = new HashMap<String,String>();
			String str = "";
			String ar2[];
			for (int i = 0; i < ar1.length; i++) {
				str = ar1[i];
				ar2 = str.split("=");
				if (ar2.length == 1)
					continue;
				map.put(ar2[0], ar2[1]);
			}
			return map;
		} catch (UnsupportedEncodingException e) {
		}
		return Collections.EMPTY_MAP;
	}
	
	public void getAllProcessInstancesHistory(HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		String strStart = request.getParameter("start");
		String strLimit = request.getParameter("limit");
		int start = Integer.valueOf(strStart);
		int size = Integer.valueOf(strLimit);
		Map map = extractFR(request);
		String modalName=(String)map.get("modalName");
		String businessInfo=(String)map.get("businessInfo");
			
		String toUserId = request.getParameter("toUserId");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		String toId = request.getParameter("toId");
		
		StringBuffer sb = new StringBuffer();
		if(modalName!=null && !"".equals(modalName.trim()))
			sb.append(" and t.model_name like '%").append(modalName).append("%'");
		if(businessInfo!=null && !"".equals(businessInfo.trim()))
			sb.append(" and t.business_note like '%").append(businessInfo).append("%'");
		
				
		String appendSQL="";
		if(!"".equals(endTime)&&endTime!=null){
			appendSQL = " and th.create_time<'"+endTime+"' ";
		}
		
		String strSql ="select * from (select rownum rnum,his.* from (" +
				"select * from v_sys_wf_process_instance t where t.process_id in (select procinst_  from jbpm_taskinstance where id_  in(" +
				"select task_id from v_sys_wf_task_history1 th inner join t_sys_user u on th.creator=u.user_name " +
				"where u.user_id='"+toUserId+"' and th.create_time>'"+startTime+"' "+appendSQL+") and task_ in(" +
				"select task_def_id from v_sys_wf_task_actor where actor_id='"+toId+"')) " 
				+sb.toString()+" order by t.create_time desc) his ) where rnum>"+start+" and rnum<"+(start+size+1);
		
		String strCountSql = "select count(*) from (" +
				"select * from v_sys_wf_process_instance t where t.process_id in (select procinst_  from jbpm_taskinstance where id_  in(" +
				"select task_id from v_sys_wf_task_history1 th inner join t_sys_user u on th.creator=u.user_name " +
				"where u.user_id='"+toUserId+"' and th.create_time>'"+startTime+"' "+appendSQL+") and task_ in(" +
				"select task_def_id from v_sys_wf_task_actor where actor_id='"+toId+"')) " 
				+sb.toString()+")";
		
		
		List<CommonProcessInstance> instanceList = 
			sysWfTaskActorService.getMyProcessInstancesHistory(strSql);
		
		int iResultCount = sysWfCommonalityService.getResultCount(strCountSql);
		
		JSONArray ja = JSONArray.fromObject(instanceList);

		JSONObject jo = new JSONObject();
		jo.put("totalProperty", iResultCount);
		jo.put("root", ja);

		String jsontext = jo.toString();
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		response.getWriter().print(jsontext);
	}

	/**
	 * 根据条件查询用户的权限转移情况
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void queryAuthByCondition(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		SysUserAuth sysUserAuth = new SysUserAuth();

		//判断是要用授权者或是被授权者进行查询
		UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
		if (request.getParameter("authObject").equals("1")){
			sysUserAuth.setFromUserId(userContext.getSysUser().getDeptUserId());
		}else{
			sysUserAuth.setToUserId(userContext.getSysUser().getDeptUserId());
		}
		
		//判断传入的是否有效标志位
		if (request.getParameter("isEffect") != null && request.getParameter("isEffect").equals("on")){
			sysUserAuth.setIsEffect("1");
		}else{
			sysUserAuth.setIsEffect("0");
		}
		
		//判断授权起启时间
		if (request.getParameter("startdate") != null && !"".equals(request.getParameter("startdate"))){
			sysUserAuth.setAuthTime(request.getParameter("startdate"));
		}
		//判断授权终启时间
		if (request.getParameter("enddate") != null && !"".equals(request.getParameter("enddate"))){
			sysUserAuth.setUnAuthTime(request.getParameter("enddate"));
		}
		
		//判断回收起启时间
		if (request.getParameter("callbackstartdate") != null && !"".equals(request.getParameter("callbackstartdate"))){
			sysUserAuth.setLastTime(request.getParameter("callbackstartdate"));
		}
		//判断回收终启时间
		if (request.getParameter("callbackenddate") != null && !"".equals(request.getParameter("callbackenddate"))){
			sysUserAuth.setCreateTime(request.getParameter("callbackenddate"));
		}
		
		List<SysUserAuth> sysUserAuthList = sysUserAuthService.queryAuthByCondition(sysUserAuth);
		
		
		JSONArray ja = JSONArray.fromObject(sysUserAuthList);

		JSONObject jo = new JSONObject();
		jo.put("totalProperty", sysUserAuthList.size());
		jo.put("root", ja);
		
		String jsontext = jo.toString();

		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		response.getWriter().print(jsontext);
	}
	
	/**
	 * 查询用户的角色给权限转移使用
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void queryRoleForTransferManage(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
		String strDeptUserId = userContext.getSysUser().getDeptUserId();

		List<SysRole> roleObjectList = sysUserAuthService.queryRoleForTransferManage(strDeptUserId);

		List<AsyncTreeNode> treeNodelist = new ArrayList();

		SysRole roleObject = null;
		for (int i = 0; i < roleObjectList.size(); i++) {
			roleObject = roleObjectList.get(i);
			AsyncTreeNode asynTreeNode = new AsyncTreeNode();
			asynTreeNode.setId(roleObject.getRoleId());
			asynTreeNode.setText(roleObject.getRoleName());
			asynTreeNode.setCls("folder");
			asynTreeNode.setHrefTarget("");
			asynTreeNode.setLeaf(true);

			treeNodelist.add(asynTreeNode);
		}

		JSONArray ja = JSONArray.fromObject(treeNodelist);

		String jsontext = ja.toString();
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		response.getWriter().print(jsontext);
	}
	
	/**
	 * 查询用户的资源给权限转移使用
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void queryResourceForTransferManage(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
		String strDeptUserId = userContext.getSysUser().getDeptUserId();

		List<SysResource> resourceObjectList = sysUserAuthService.queryResourceForTransferManage(strDeptUserId);

		List<AsyncTreeNode> treeNodelist = new ArrayList();

		SysResource resourceObject = null;
		for (int i = 0; i < resourceObjectList.size(); i++) {
			resourceObject = resourceObjectList.get(i);
			AsyncTreeNode asynTreeNode = new AsyncTreeNode();
			asynTreeNode.setId(resourceObject.getResourceid());
			asynTreeNode.setText(resourceObject.getResourcetitle());
			asynTreeNode.setCls("folder");
			asynTreeNode.setHrefTarget("");
			asynTreeNode.setLeaf(true);

			treeNodelist.add(asynTreeNode);
		}

		JSONArray ja = JSONArray.fromObject(treeNodelist);

		String jsontext = ja.toString();
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		response.getWriter().print(jsontext);
	}
	
	/**
	 * 增加用户权限转移信息
	 * @param request
	 * @param response
	 * @param sysUserAuth
	 * @throws IOException
	 */
	public void addUserAuth(HttpServletRequest request,
			HttpServletResponse response,SysUserAuth sysUserAuth) throws IOException {
		sysUserAuthService.addUserAuth(sysUserAuth);
		this.operateSuccessfully(response);
	}
	
	/**
	 * 回收转移的权限
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void deleteUserAuth(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String strAuthIdList = request.getParameter("authidlist");
		sysUserAuthService.deleteUserAuth(strAuthIdList);
		this.operateSuccessfully(response);
	}
	
	/**
	 * 进入增加用户权限转移界面
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ModelAndView addUserAuthView(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		return new ModelAndView("console/org/addUserAuth");
	}
	
	/**
	 * 根据等陆人查询该人员的部门是否是职能部门，如果是就返回所有部门，如果不是就只返回本部门
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void querySelectDeptInfo(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String strParentId = request.getParameter("id");
		UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
		List<AsyncTreeCheckNode> treeNodelist = new ArrayList();
		
		//如果是非职能部门的就只能取本部门的
		if (userContext.getSysDept().getIsFuncDept().equals("1")){
			List<SysDept> tSysDeptList = sysDeptService.queryDeptByParentId(strParentId);
			
			SysDept tSysDept = new SysDept();
			for (int i = 0; i < tSysDeptList.size(); i++) {
				tSysDept = tSysDeptList.get(i);
				
				AsyncTreeCheckNode asynTreeNode = new AsyncTreeCheckNode();
				asynTreeNode.setId(tSysDept.getDeptid());
				asynTreeNode.setText(tSysDept.getDeptname());
				asynTreeNode.setCls("folder");
				asynTreeNode.setHrefTarget("");
				// 如果孩子结点数为０则为叶结点
				if (tSysDept.getChildcount() == 0)
					asynTreeNode.setLeaf(true);
				else
					asynTreeNode.setLeaf(false);
				
				asynTreeNode.setChecked(false);
				
				treeNodelist.add(asynTreeNode);
			}
		}
		else{
			if (strParentId.equals("-1")){
				strParentId = userContext.getSysDept().getDeptid();
				
				SysDept tSysDept = new SysDept();
				tSysDept = sysDeptService.queryDeptById(strParentId);
					
				AsyncTreeCheckNode asynTreeNode = new AsyncTreeCheckNode();
				asynTreeNode.setId(tSysDept.getDeptid());
				asynTreeNode.setText(tSysDept.getDeptname());
				asynTreeNode.setCls("folder");
				asynTreeNode.setHrefTarget("");
				// 如果孩子结点数为０则为叶结点
				if (tSysDept.getChildcount() == 0)
					asynTreeNode.setLeaf(true);
				else
					asynTreeNode.setLeaf(false);
				
				asynTreeNode.setChecked(false);
				
				treeNodelist.add(asynTreeNode);
			}else{
				List<SysDept> tSysDeptList = sysDeptService.queryDeptByParentId(strParentId);
				
				SysDept tSysDept = new SysDept();
				for (int i = 0; i < tSysDeptList.size(); i++) {
					tSysDept = tSysDeptList.get(i);
					
					AsyncTreeCheckNode asynTreeNode = new AsyncTreeCheckNode();
					asynTreeNode.setId(tSysDept.getDeptid());
					asynTreeNode.setText(tSysDept.getDeptname());
					asynTreeNode.setCls("folder");
					asynTreeNode.setHrefTarget("");
					// 如果孩子结点数为０则为叶结点
					if (tSysDept.getChildcount() == 0)
						asynTreeNode.setLeaf(true);
					else
						asynTreeNode.setLeaf(false);
					
					asynTreeNode.setChecked(false);
					
					treeNodelist.add(asynTreeNode);
				}
			}
		}
		
		JSONArray ja = JSONArray.fromObject(treeNodelist);

		String jsontext = ja.toString();
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		response.getWriter().print(jsontext);
	}
	/**
	 *  从员工管理界面进入员工角色授权界面
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView addUserPopedomView(HttpServletRequest request,
			HttpServletResponse response) {
		request.setAttribute("deptuserid", request.getParameter("deptuserid"));
		return new ModelAndView("console/org/addUserPopedom");
	}
	/**
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void queryDeptForTree(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String strParentId = request.getParameter("id");
		String deptuserid = request.getParameter("deptuserid");
		List<AsyncTreeCheckNode> treeNodelist = new ArrayList<AsyncTreeCheckNode>();
		List<SysDept> tSysDeptList = sysDeptService.queryDeptByParentId(strParentId);
/*		Collections.sort(tSysDeptList, new Comparator<SysDept>(){
			public int compare(SysDept o1, SysDept o2) {
				return o1.getCreatetime().compareTo(o2.getCreatetime());
			}
		});*/
        List<String> managerDept = sysUserService.queryManagerDeptByUserId(deptuserid);
		SysDept tSysDept;
		for (int i = 0; i < tSysDeptList.size(); i++) {
			tSysDept = tSysDeptList.get(i);

			AsyncTreeCheckNode asynTreeNode = new AsyncTreeCheckNode();
			asynTreeNode.setId(tSysDept.getDeptid());
			asynTreeNode.setText(tSysDept.getDeptname());
			asynTreeNode.setCls("folder");
			asynTreeNode.setHrefTarget("");
			// 如果孩子结点数为０则为叶结点
			if (tSysDept.getChildcount() == 0)
				asynTreeNode.setLeaf(true);
			else
				asynTreeNode.setLeaf(false);
			if(managerDept.contains(tSysDept.getDeptid()))
				    asynTreeNode.setChecked(true);

           
			treeNodelist.add(asynTreeNode);
		}
		JSONArray ja = JSONArray.fromObject(treeNodelist);

		String jsontext = ja.toString();
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		response.getWriter().print(jsontext);
	}
	
	public void addUserManagerDept(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String deptuserid = request.getParameter("deptuserid");
		String deptList  = request.getParameter("deptList");
		
		sysUserService.addManagerDept(deptList, deptuserid);
		this.operateSuccessfully(response);
	}
	/**登录现金日记账系统**/
	public ModelAndView loginXjrj(HttpServletRequest request,
			HttpServletResponse response) {
		UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
		request.setAttribute("userName", userContext.getSysUser().getUserName());
		request.setAttribute("password", userContext.getSysUser().getPassword());
		return new ModelAndView("xjrj");
	}
	/**
	 * 根据userName取得该用户下所有权限下的子部门
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void getAllDeptCodeByUserName(HttpServletRequest request,
			HttpServletResponse response)throws IOException{
		JSONArray ja = new JSONArray();
		UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
		String deptCodes = sysUserService.getAllDeptCodeByUserName(userContext.getSysUser().getUserName());
//		ja.add(deptCodes);
//		String jsontext = ja.toString();		
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		response.getWriter().print(deptCodes);
	}
}
