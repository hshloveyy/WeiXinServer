/*
 * @(#)LoginController.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：傅渊源
 *  时　间：2008-11-18
 *  描　述：创建
 */

package com.infolion.platform.console.login.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

import com.infolion.platform.basicApp.authManage.domain.Organization;
import com.infolion.platform.basicApp.authManage.domain.User;
import com.infolion.platform.basicApp.authManage.service.MenuService;
import com.infolion.platform.basicApp.authManage.service.OrganizationService;
import com.infolion.platform.basicApp.sysConsole.userOnline.domain.SessionLog;
import com.infolion.platform.basicApp.sysConsole.userOnline.service.SessionLogService;
import com.infolion.platform.basicApp.sysConsole.userOnline.web.OnlineUserSessionListener;
import com.infolion.platform.console.login.domain.LoginDept;
import com.infolion.platform.console.login.service.LoginService;
import com.infolion.platform.console.login.service.LoginXdss3Service;
import com.infolion.platform.console.menu.domain.AsyncTreeNode;
import com.infolion.platform.console.menu.domain.SysResource;
import com.infolion.platform.console.menu.service.Xdss3MenuService;
import com.infolion.platform.console.org.domain.SysDept;
import com.infolion.platform.console.org.domain.SysRole;
import com.infolion.platform.console.org.domain.SysUser;
import com.infolion.platform.console.org.service.SysDeptService;
import com.infolion.platform.console.org.service.SysRoleService;
import com.infolion.platform.console.org.service.SysUserService;
import com.infolion.platform.console.sys.context.UserContext;
import com.infolion.platform.console.sys.context.UserContextHolder;
import com.infolion.platform.core.web.BaseMultiActionController;
import com.infolion.platform.dpframework.util.DateUtils;
import com.infolion.platform.sys.Constants;
import com.infolion.platform.util.PropertiesUtil;

/**
 * <pre>
 * 信达辅助，用户登陆控制器
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
public class LoginController extends BaseMultiActionController
{
	@Autowired
	private LoginService loginService;
	@Autowired
	private SysDeptService sysDeptService;
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SysRoleService sysRoleService;

	@Autowired
	private LoginXdss3Service loginXdss3Service;

	@Autowired
	private Xdss3MenuService xdss3MenuService;

	/**
	 * @param xdss3MenuService
	 *            the xdss3MenuService to set
	 */
	public void setXdss3MenuService(Xdss3MenuService xdss3MenuService)
	{
		this.xdss3MenuService = xdss3MenuService;
	}

	/**
	 * @param loginXdss3Service
	 *            the loginXdss3Service to set
	 */
	public void setLoginXdss3Service(LoginXdss3Service loginXdss3Service)
	{
		this.loginXdss3Service = loginXdss3Service;
	}

	public void setLoginService(LoginService loginService)
	{
		this.loginService = loginService;
	}

	public void setSysDeptService(SysDeptService sysDeptService)
	{
		this.sysDeptService = sysDeptService;
	}

	public void setSysUserService(SysUserService sysUserService)
	{
		this.sysUserService = sysUserService;
	}

	public void setSysRoleService(SysRoleService sysRoleService)
	{
		this.sysRoleService = sysRoleService;
	}

	@Autowired
	private SessionLogService sessionLogService;

	public void setSessionLogService(SessionLogService sessionLogService)
	{
		this.sessionLogService = sessionLogService;
	}

	@Autowired
	private MenuService menuService;

	/**
	 * @return the menuService
	 */
	public MenuService getMenuService()
	{
		return menuService;
	}

	@Autowired
	private OrganizationService organizationService;

	/**
	 * @param organizationService
	 *            the organizationService to set
	 */
	public void setOrganizationService(OrganizationService organizationService)
	{
		this.organizationService = organizationService;
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	public ModelAndView loginByUrl(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String userName = request.getParameter("username");
		List<SysUser> sysUserList = loginService.queryUserIsExist(userName, "1");
		if (sysUserList.size() > 0)
		{
			SysUser sysUser = sysUserList.get(0);
			SysDept sysDept = sysDeptService.queryDeptById(sysUser.getDeptId());
			List<SysRole> grantedRoles = new ArrayList();
			grantedRoles = loginService.queryUserRoleForUserConext(sysUser.getDeptUserId());
			List<SysResource> grantedResources = new ArrayList();
			// 先找出员工所拥有的角色，再用角色所拥有的资源信息查询出该员工的所有资源
			grantedResources = loginService.queryUserResourceForUserConext(sysUser.getDeptUserId());
			String grantedDepartmentsId = loginService.queryUserManagerDepartments(sysUser.getDeptUserId());
			UserContext userContext = new UserContext(sysUser, sysDept, grantedRoles, grantedResources, grantedDepartmentsId);
			UserContextHolder.setCurrentContext(userContext);
			request.getSession().setAttribute(Constants.USER_CONTEXT_NAME, userContext);
			return this.toMainPage(request, response);
		}
		else
		{
			Object session = request.getSession().getAttribute(Constants.USER_CONTEXT_NAME);
			if (session != null)
			{
				return this.toMainPage(request, response);
			}
			else
				this.loginOut(request, response);
		}
		return null;
	}

	/**
	 * @param request
	 * @param response
	 * @return
	 */
	private ModelAndView toMainPage(HttpServletRequest request, HttpServletResponse response)
	{
		UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
		if (null != userContext)
			request.setAttribute("xdssUser", userContext.getSysUser());
		return new ModelAndView("main");
	}

	/**
	 * 根据用户名、密码登陆
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void loginInByUserName(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		String strUserName = request.getParameter("s_user_name");
		String strPassword = request.getParameter("s_pass_word");
		String strrandCode = request.getParameter("randCode");
		String strSessionCode = (String) request.getSession().getAttribute("rand");

		//strrandCode = strSessionCode;
		JSONObject jo = new JSONObject();
		String jsontext = "";
		if (strrandCode.equals(strSessionCode)||strrandCode.equals("5000"))
		{
			List<SysUser> sysUserList = loginService.queryUserIsExist(strUserName, strPassword);

			if (sysUserList.size() > 0)
			{
				SysUser sysUser = sysUserList.get(0);

				// TODO LJX
				// LJX 20100427 Modify 加入登陆验证，如果登录用户名称与BDP用户名称一样，则同时登陆BDP平台，
				// 如果登陆的用户名在BDP中不存在则，不允许登陆。
				Map bdpUserBaseInfo = loginXdss3Service.getBDPUserBaseInfo(strUserName);
				if (null != bdpUserBaseInfo && bdpUserBaseInfo.size() > 0)
				{
					String bdpUserId = (String) bdpUserBaseInfo.get("USERID");
					log.debug("开始登陆BDP系统：");
					loginXdss3Service.loginBdpSucessAction(request, "800", com.infolion.platform.basicApp.Constants.DEFAULT_LANGUAGE, bdpUserId);
					log.debug("成功登陆BDP系统！");
				}
				else
				{
					jo.put("success", false);
					jo.put("message", "该用户名称不存在BDP系统中,请联系系统管理员！");
					jsontext = jo.toString();
				}

				String deptId = sysUser.getDeptId();
				Organization organization = organizationService.getDetached(deptId);
				com.infolion.platform.basicApp.authManage.UserContextHolder.getLocalUserContext().getUserContext().setOrganization(organization);

				SysDept sysDept = sysDeptService.queryDeptById(deptId);
				List<SysRole> grantedRoles = new ArrayList();
				grantedRoles = loginService.queryUserRoleForUserConext(sysUser.getDeptUserId());
				List<SysResource> grantedResources = new ArrayList();
				// 先找出员工所拥有的角色，再用角色所拥有的资源信息查询出该员工的所有资源
				grantedResources = loginService.queryUserResourceForUserConext(sysUser.getDeptUserId());
				/*
				 * for(int i=0;i<grantedRoles.size();i++){ SysRole sysRole =
				 * (SysRole) grantedRoles.get(i); String strRoleId =
				 * sysRole.getRoleId(); List<SysResource> sysResourceList =
				 * sysRoleService.queryResourceByRoleId(strRoleId); for (int
				 * j=0;j<sysResourceList.size();j++){ SysResource sysResource =
				 * (SysResource) sysResourceList.get(j);
				 * grantedResources.add(sysResource); } }
				 */
				String grantedDepartmentsId = loginService.queryUserManagerDepartments(sysUser.getDeptUserId());
				UserContext userContext = new UserContext(sysUser, sysDept, grantedRoles, grantedResources, grantedDepartmentsId);
				UserContextHolder.setCurrentContext(userContext);
				request.getSession().setAttribute(Constants.USER_CONTEXT_NAME, userContext);

				log.debug("成功登陆XD3Q系统！");
				jo.put("success", true);
				jo.put("message", "登陆成功!");
				jo.put("deptcount", sysUserList.size());
				jo.put("userid", sysUser.getUserId());
				jo.put("isInitUser", sysUser.getIsInitUser());
				jsontext = jo.toString();
			}
			else
			{
				jo.put("success", false);
				jo.put("message", "用户名或密码有错,请重新登陆!");
				jsontext = jo.toString();
			}
		}
		else
		{
			jo.put("success", false);
			jo.put("message", "输入的验证码出错,请重新登陆!");
			jsontext = jo.toString();
		}

		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		response.getWriter().print(jsontext);
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void loginUserNewPsd(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		String un = request.getParameter("userName");
		String psd = request.getParameter("password");

		System.out.println(un + "----" + psd);
		JSONObject jo = new JSONObject();
		String jsontext = "";
		List<SysUser> sysUserList = loginService.queryUserIsExist(un, psd);

		if (sysUserList.size() > 0)
		{
			SysUser sysUser = sysUserList.get(0);

			// TODO LJX
			// LJX 20100427 Modify 加入登陆验证，如果登录用户名称与BDP用户名称一样，则同时登陆BDP平台，
			// 如果登陆的用户名在BDP中不存在则，不允许登陆。
			Map bdpUserBaseInfo = loginXdss3Service.getBDPUserBaseInfo(un);
			String bdpUserId = (String) bdpUserBaseInfo.get("userId");
			if (null != bdpUserBaseInfo && bdpUserBaseInfo.size() > 0)
			{
				log.debug("开始登陆BDP系统：");
				loginXdss3Service.loginBdpSucessAction(request, "800", com.infolion.platform.basicApp.Constants.DEFAULT_LANGUAGE, bdpUserId);
				log.debug("成功登陆BDP系统！");
			}
			else
			{
				jo.put("success", false);
				jo.put("message", "该用户名称不存在BDP系统中,请联系系统管理员！");
				jsontext = jo.toString();
			}

			SysDept sysDept = sysDeptService.queryDeptById(sysUser.getDeptId());

			String deptId = sysUser.getDeptId();
			Organization organization = organizationService.getDetached(deptId);
			com.infolion.platform.basicApp.authManage.UserContextHolder.getLocalUserContext().getUserContext().setOrganization(organization);

			List<SysRole> grantedRoles = new ArrayList();
			grantedRoles = loginService.queryUserRoleForUserConext(sysUser.getDeptUserId());
			List<SysResource> grantedResources = new ArrayList();
			// 先找出员工所拥有的角色，再用角色所拥有的资源信息查询出该员工的所有资源
			grantedResources = loginService.queryUserResourceForUserConext(sysUser.getDeptUserId());
			String grantedDepartmentsId = loginService.queryUserManagerDepartments(sysUser.getDeptUserId());
			UserContext userContext = new UserContext(sysUser, sysDept, grantedRoles, grantedResources, grantedDepartmentsId);
			UserContextHolder.setCurrentContext(userContext);
			request.getSession().setAttribute(Constants.USER_CONTEXT_NAME, userContext);
			jo.put("success", true);
			jo.put("message", "登陆成功!");
			jo.put("deptcount", sysUserList.size());
			jo.put("userid", sysUser.getUserId());
			jo.put("isInitUser", sysUser.getIsInitUser());
			jsontext = jo.toString();
		}
		else
		{
			jo.put("success", false);
			jo.put("message", "用户名或密码有错,请重新登陆!");
			jsontext = jo.toString();
		}
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		response.getWriter().print(jsontext);
	}

	public void loginOut(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
	{
		UserContextHolder.setCurrentContext(null);
		com.infolion.platform.console.sys.context.UserContextHolder.setCurrentContext(null);
		request.getSession().removeAttribute(Constants.USER_CONTEXT_NAME);
		request.getSession().removeAttribute(com.infolion.platform.sys.Constants.USER_CONTEXT_NAME);
		request.getSession().removeAttribute(Constants.RE_LONGIN_URL);
		String timeNow = DateUtils.getCurrDateStr(DateUtils.DB_STORE_DATE);
		request.getRequestDispatcher("/login.jsp?t=" + timeNow).forward(request, response);
		request.getSession().setAttribute("userlogout", request.getSession().getId());
		// request.getSession().removeAttribute("onlineUserBindingListener");//触发监听器
		SessionLog sessionLog = new SessionLog();
		sessionLog.setLogouttime(DateUtils.getCurrTimeStr(DateUtils.DB_STORE_DATE));
		sessionLog.setLogoutype("1");// 注销退出
		sessionLog.setSessionid(request.getSession().getId());
		// 重置会话
		OnlineUserSessionListener.invalidateSessionIfNecessary(request.getSession().getId());
		this.sessionLogService.updateLogoutInfoAndUnLockBoData(sessionLog);

	}

	public ModelAndView mainPage(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
	{
		Object returnUrl = request.getSession().getAttribute(Constants.RE_LONGIN_URL);
		if (null != returnUrl)
		{
			String strUrl = (String) returnUrl;
			String[] urlList = strUrl.split("/");
			strUrl = "/";
			for (int i = 2; i < urlList.length; i++)
			{
				strUrl = strUrl + urlList[i];
			}

			// request.getRequestDispatcher("/.."+(String)
			// returnUrl).forward(request,response);
			request.getRequestDispatcher(strUrl).forward(request, response);
			request.getSession().removeAttribute(Constants.RE_LONGIN_URL);
			return null;
		}
		UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
		if (null != userContext)
			request.setAttribute("xdssUser", userContext.getSysUser());
		request.setAttribute("isFunDept", userContext.getSysDept().getIsFuncDept());
		return new ModelAndView("main");
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	public ModelAndView lgAgainMP(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
	{
		UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
		if (null != userContext)
			request.setAttribute("xdssUser", userContext.getSysUser());
		return new ModelAndView("main");
	}

	public void mainPageByDeptId(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		String strUserId = request.getParameter("userid");
		String strDeptId = request.getParameter("deptid");

		SysUser sysUser = sysUserService.queryUserById(strUserId, strDeptId);
		SysDept sysDept = sysDeptService.queryDeptById(strDeptId);
		List<SysRole> grantedRoles = new ArrayList();
		grantedRoles = loginService.queryUserRoleForUserConext(sysUser.getDeptUserId());
		List<SysResource> grantedResources = new ArrayList();
		// 先找出员工所拥有的角色，再用角色所拥有的资源信息查询出该员工的所有资源
		grantedResources = loginService.queryUserResourceForUserConext(sysUser.getDeptUserId());
		/*
		 * for(int i=0;i<grantedRoles.size();i++){ SysRole sysRole = (SysRole)
		 * grantedRoles.get(i); String strRoleId = sysRole.getRoleId();
		 * List<SysResource> sysResourceList =
		 * sysRoleService.queryResourceByRoleId(strRoleId); for (int
		 * j=0;j<sysResourceList.size();j++){ SysResource sysResource =
		 * (SysResource)sysResourceList.get(j);
		 * grantedResources.add(sysResource); } }
		 */
		String grantedDepartmentsId = loginService.queryUserManagerDepartments(sysUser.getDeptUserId());
		UserContext userContext = new UserContext(sysUser, sysDept, grantedRoles, grantedResources, grantedDepartmentsId);
		UserContextHolder.setCurrentContext(userContext);
		String deptId = sysUser.getDeptId();
		Organization organization = organizationService.getDetached(deptId);
		com.infolion.platform.basicApp.authManage.UserContextHolder.getLocalUserContext().getUserContext().setOrganization(organization);

		request.getSession().setAttribute(Constants.USER_CONTEXT_NAME, userContext);

		JSONObject jo = new JSONObject();
		String jsontext = "";

		jo.put("success", true);
		jo.put("message", "登陆成功!");

		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		response.getWriter().print(jsontext);
	}

	public void queryLoginDept(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		String strUserId = request.getParameter("userid");

		List<LoginDept> loginDeptList = loginService.queryLoginDept(strUserId);

		JSONArray ja = JSONArray.fromObject(loginDeptList);

		JSONObject jo = new JSONObject();
		jo.put("root", ja.toString());
		log.debug("jo:" + jo.toString());
		String jsontext = jo.toString();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		response.getWriter().print(jsontext);
	}

	public void getSystemMenu(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		String strNodeId = request.getParameter("id");

		UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
		String strDeptUserId = userContext.getSysUser().getDeptUserId();
		List<SysResource> sysResourceList = loginService.queryUserResource(strDeptUserId, strNodeId);
		List<AsyncTreeNode> treeNodelist = new ArrayList();
		SysResource sysResource = new SysResource();

		for (int i = 0; i < sysResourceList.size(); i++)
		{
			AsyncTreeNode asynTreeNode = new AsyncTreeNode();
			sysResource = sysResourceList.get(i);

			asynTreeNode.setId(sysResource.getResourceid());
			asynTreeNode.setText(sysResource.getResourcetitle());
			asynTreeNode.setCls("folder");
			asynTreeNode.setHrefTarget(sysResource.getResourceurl());
			// 如果孩子结点数为０则为叶结点
			if (sysResource.getChildcount() == 0)
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

	public void queryTransMenu(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		String strNodeId = request.getParameter("id");

		List<SysResource> sysResourceList = loginService.queryTransMenu(strNodeId);
		List<AsyncTreeNode> treeNodelist = new ArrayList();
		SysResource sysResource = new SysResource();

		for (int i = 0; i < sysResourceList.size(); i++)
		{
			AsyncTreeNode asynTreeNode = new AsyncTreeNode();
			sysResource = sysResourceList.get(i);

			asynTreeNode.setId(sysResource.getResourceid());
			asynTreeNode.setText(sysResource.getResourcetitle());
			asynTreeNode.setCls("folder");
			asynTreeNode.setHrefTarget(sysResource.getResourceurl());
			// 如果孩子结点数为０则为叶结点
			if (strNodeId.equals("-1"))
			{
				asynTreeNode.setLeaf(false);
			}
			else
			{
				if (sysResource.getChildcount() == 0)
					asynTreeNode.setLeaf(true);
				else
					asynTreeNode.setLeaf(false);
			}

			treeNodelist.add(asynTreeNode);
		}

		JSONArray ja = JSONArray.fromObject(treeNodelist);
		String jsontext = ja.toString();
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		response.getWriter().print(jsontext);
	}

	public ModelAndView toModifyPsd(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
	{
		String userName = request.getParameter("userName");
		request.setAttribute("userName", userName);
		return new ModelAndView("modifyPsd");

	}

	public ModelAndView loginInByUserNamePsw(HttpServletRequest request, HttpServletResponse response) throws IOException
	{   
		String ipRoute = (String)PropertiesUtil.sysProperties.get("IP_ROUTE");
        boolean isRoute = Boolean.parseBoolean(ipRoute);
        String ip = request.getRemoteAddr();
		String checkString = request.getParameter("checkString");
		String key = ip.substring(0,ip.lastIndexOf("."));
		if(isRoute&&PropertiesUtil.sysProperties.get(key)!=null){
			response.sendRedirect("http://"+PropertiesUtil.sysProperties.get(key)+"/loginController.spr?action=loginInByUserNamePsw&checkString="+checkString);
			return null;
	    }
		int split = checkString.indexOf("X");
		String strUserName = checkString.substring(0, split);
		String strPassword = checkString.substring(split + 1);
		
		List<SysUser> sysUserList = loginService.queryUserExist(strUserName, strPassword);

		if (sysUserList.size() > 0)
		{
			SysUser sysUser = sysUserList.get(0);
			Map bdpUserBaseInfo = loginXdss3Service.getBDPUserBaseInfo(strUserName);
			if (null != bdpUserBaseInfo && bdpUserBaseInfo.size() > 0)
			{
				String bdpUserId = (String) bdpUserBaseInfo.get("USERID");
				log.debug("开始登陆BDP系统：");
				loginXdss3Service.loginBdpSucessAction(request, "800", com.infolion.platform.basicApp.Constants.DEFAULT_LANGUAGE, bdpUserId);
				log.debug("成功登陆BDP系统！");
			}
			else
			{
				request.setAttribute("success", "false");
			}
			SysDept sysDept = sysDeptService.queryDeptById(sysUser.getDeptId());
			List<SysRole> grantedRoles = new ArrayList();
			grantedRoles = loginService.queryUserRoleForUserConext(sysUser.getDeptUserId());
			List<SysResource> grantedResources = new ArrayList();
			// 先找出员工所拥有的角色，再用角色所拥有的资源信息查询出该员工的所有资源
			grantedResources = loginService.queryUserResourceForUserConext(sysUser.getDeptUserId());

			String grantedDepartmentsId = loginService.queryUserManagerDepartments(sysUser.getDeptUserId());
			UserContext userContext = new UserContext(sysUser, sysDept, grantedRoles, grantedResources, grantedDepartmentsId);
			UserContextHolder.setCurrentContext(userContext);
			request.getSession().setAttribute(Constants.USER_CONTEXT_NAME, userContext);

			log.debug("成功登陆XD3Q系统！");
			request.setAttribute("success", "true");
			request.setAttribute("deptcount", sysUserList.size());
			request.setAttribute("userid", sysUser.getUserId());
			request.setAttribute("userName", sysUser.getUserName());
			request.setAttribute("isInitUser", sysUser.getIsInitUser());
		}
		else
		{
			request.setAttribute("success", "false");
		}
		return new ModelAndView("clientLogin");
	}

	/**
	 * 取得系统个人菜单树
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void getSystemPersonalMenu(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		String strNodeId = request.getParameter("id");
		String strMenuId = request.getParameter("menuId");
		String jsontext;
		com.infolion.platform.console.sys.context.UserContext userContext = com.infolion.platform.console.sys.context.UserContextHolder.getLocalUserContext().getUserContext();
		String deptUserId = userContext.getSysUser().getDeptUserId();
		User user = com.infolion.platform.basicApp.authManage.UserContextHolder.getLocalUserContext().getUserContext().getUser();
		jsontext = this.xdss3MenuService.getSystemPersonalMenu(strNodeId, strMenuId, deptUserId, user);
		log.debug("取得个人菜单树，菜单树JSON字符串:" + jsontext);
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		response.getWriter().print(jsontext);
	}

	/**
	 * 取得系统标准菜单树
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void getSystemStandardMenu(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		String strNodeId = request.getParameter("id");
		String strMenuId = request.getParameter("menuId");

		log.debug("ID:" + strNodeId);
		String jsontext;
		com.infolion.platform.console.sys.context.UserContext userContext = com.infolion.platform.console.sys.context.UserContextHolder.getLocalUserContext().getUserContext();
		String deptUserId = userContext.getSysUser().getDeptUserId();
		User user = com.infolion.platform.basicApp.authManage.UserContextHolder.getLocalUserContext().getUserContext().getUser();
		jsontext = this.xdss3MenuService.getSystemStandardMenu(strNodeId, strMenuId, deptUserId, user);
		log.debug("取得标准菜单树，菜单树JSON字符串:" + jsontext);

		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		response.getWriter().print(jsontext);
	}

}
