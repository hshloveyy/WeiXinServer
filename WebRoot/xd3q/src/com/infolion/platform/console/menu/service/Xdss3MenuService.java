/*
 * @(#)Xdss3MenuService.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：林进旭
 *  时　间：2010-4-27
 *  描　述：创建
 */

package com.infolion.platform.console.menu.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import net.sf.json.JSONArray;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infolion.platform.basicApp.authManage.dao.UserAuthMoveJdbcDao;
import com.infolion.platform.basicApp.authManage.domain.AsyncTreeNode;
import com.infolion.platform.basicApp.authManage.domain.Menu;
import com.infolion.platform.basicApp.authManage.domain.MenuTree;
import com.infolion.platform.basicApp.authManage.domain.User;
import com.infolion.platform.basicApp.authManage.service.MenuService;
import com.infolion.platform.basicApp.authManage.service.UserService;
import com.infolion.platform.console.login.service.LoginXdss3Service;
import com.infolion.platform.dpframework.core.service.BaseService;
import com.infolion.platform.dpframework.util.StringUtils;

/**
 * <pre>
 * 菜单处理服务类
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
@Service
public class Xdss3MenuService extends BaseService
{
	/**
	 * Log4J
	 */
	private Log log = LogFactory.getFactory().getLog(this.getClass());

	@Autowired
	private UserService userService;

	public void setUserService(UserService userService)
	{
		this.userService = userService;
	}

	@Autowired
	private MenuService menuService;

	/**
	 * @param menuService
	 *            the menuService to set
	 */
	public void setMenuService(MenuService menuService)
	{
		this.menuService = menuService;
	}

	@Autowired
	private LoginXdss3Service loginXdss3Service;

	/**
	 * @param loginXdss3Service
	 *            the loginXdss3Service to set
	 */
	public void setLoginXdss3Service(LoginXdss3Service loginXdss3Service)
	{
		this.loginXdss3Service = loginXdss3Service;
	}

	@Autowired
	private UserAuthMoveJdbcDao userAuthMoveJdbcDao;

	/**
	 * 
	 * @param userAuthMoveJdbcDao
	 *            the userAuthMoveJdbcDao to set
	 */
	public void setUserAuthMoveJdbcDao(UserAuthMoveJdbcDao userAuthMoveJdbcDao)
	{
		this.userAuthMoveJdbcDao = userAuthMoveJdbcDao;
	}

	/**
	 * 取得系统个人菜单树,JSON字符串
	 * 
	 * @param strNodeId
	 * @param strMenuId
	 * @param treeNodelist
	 * @param user
	 * @return
	 */
	public String getSystemPersonalMenu(String strNodeId, String strMenuId, String deptUserId, User user) throws IOException
	{
		String jsontext;
		Set<AsyncTreeNode> treeNodelist = new LinkedHashSet<AsyncTreeNode>();
		List<AsyncTreeNode> xdssTreeNodeList = new ArrayList<AsyncTreeNode>();
		// TODO LJX
		// LJX 20100427 Modify 加入信达旧系统，系统菜单。
		if (!StringUtils.isNullBlank(strNodeId) && !StringUtils.isNullBlank(deptUserId))
		{
			xdssTreeNodeList = this.loginXdss3Service.getXdssSysMenuTreeNodeList(deptUserId, strNodeId);
		}

		if (strNodeId.equals("-1"))
		{
			treeNodelist = this.getSystemPersonalRootMenu(user, strNodeId);
		}
		else if (StringUtils.isNotBlank(strMenuId) && strMenuId.equals("-1") && !strNodeId.equals("OtherRemoveMenu"))
		{
			Set<MenuTree> menuTrees = this.userService.getUserMenuTrees(user, strNodeId, strMenuId);
			treeNodelist = getSystemPersonalMenuJSONString(strNodeId, menuTrees);
		}
		else if (StringUtils.isNotBlank(strMenuId) && strMenuId.equals("-1") && strNodeId.equals("OtherRemoveMenu"))
		{
			Set<MenuTree> menuTrees = this.userAuthMoveJdbcDao.getOtherUserAuthRemoveMenu(user.getUserId());
			treeNodelist = getSystemPersonalMenuJSONString(strNodeId, menuTrees);
		}
		else
		{
			Set<MenuTree> menuTrees = this.userService.getUserMenuTrees(user, strMenuId, strNodeId);
			Iterator<MenuTree> itMenuTrees = menuTrees.iterator();
			while (itMenuTrees.hasNext())
			{
				AsyncTreeNode asynTreeNode = new AsyncTreeNode();
				MenuTree menuTree = itMenuTrees.next();
				String parentNodeId = menuTree.getParentNodeId() + "";
				String menuId = menuTree.getMenuId();
				String url = "";
				String nodeType = menuTree.getNodeType();

				if (parentNodeId.equals(strNodeId) && menuId.equals(strMenuId))
				{
					url = menuTree.getUrl();// this.userService.getUrlByAuthResourceid(menuTree.getAuthResourceId());

					asynTreeNode.setId(menuTree.getNodeId());
					asynTreeNode.setText(menuTree.getMenuTreeTextByLanguage());
					// asynTreeNode.setCls("folder"); // getSystemMenu
					asynTreeNode.setCls("folder");
					asynTreeNode.setIconCls(menuTree.getIcon());
					asynTreeNode.setMenuId(menuId);
					asynTreeNode.setOrderNo(menuTree.getOrderNo());
					asynTreeNode.setHrefTarget(url.trim());

					// 如果孩子结点数为０则为叶结点
					if (strNodeId.equals("-1"))
					{
						asynTreeNode.setLeaf(false);
					}
					else
					{
						if (StringUtils.isNotBlank(nodeType))
						{
							if (nodeType.equals("1"))
							{
								asynTreeNode.setLeaf(false);
							}
							else
							{
								asynTreeNode.setLeaf(true);
							}
						}
						else
						{
							asynTreeNode.setLeaf(false);
						}
					}

					treeNodelist.add(asynTreeNode);
				}
			}

		}

		// TODO LJX
		// LJX 20100427 Modify 加入信达旧系统，系统菜单。
		if (xdssTreeNodeList != null && xdssTreeNodeList.size() > 0)
			treeNodelist.addAll(xdssTreeNodeList);

		JSONArray ja = JSONArray.fromObject(treeNodelist);
		jsontext = ja.toString();

		return jsontext;
	}

	/**
	 * 取得系统标准菜单树
	 * 
	 * @param strNodeId
	 * @param strMenuId
	 * @param user
	 * @return
	 * @throws IOException
	 */
	public String getSystemStandardMenu(String strNodeId, String strMenuId, String deptUserId, User user) throws IOException
	{
		List<AsyncTreeNode> treeNodelist = new ArrayList<AsyncTreeNode>();
		List<AsyncTreeNode> xdssTreeNodeList = new ArrayList<AsyncTreeNode>();
		String jsontext;
		// TODO LJX
		// LJX 20100427 Modify 加入信达旧系统，系统菜单。
		if (!StringUtils.isNullBlank(strNodeId) && !StringUtils.isNullBlank(deptUserId))
		{
			xdssTreeNodeList = this.loginXdss3Service.getXdssSysMenuTreeNodeList(deptUserId, strNodeId);
		}

		if (strNodeId.equals("-1"))
		{
			treeNodelist = this.menuService.getSystemStandardMenuRoot();
		}
		else if (StringUtils.isNotBlank(strMenuId) && strMenuId.equals("-1"))
		{
			List<MenuTree> menuTrees = this.menuService.getUserStandardMenuList(strNodeId, "-1");
			Iterator<MenuTree> itMenuTrees = menuTrees.iterator();
			while (itMenuTrees.hasNext())
			{
				AsyncTreeNode asyncTreeNode = new AsyncTreeNode();
				MenuTree menuTree = itMenuTrees.next();
				String parentNodeId = menuTree.getParentNodeId() + "";
				String menuId = strNodeId;
				String url = "";
				// 节点类型
				String nodeType = menuTree.getNodeType();

				url = menuTree.getUrl();// this.userService.getUrlByAuthResourceid(menuTree.getAuthResourceId());
				asyncTreeNode.setId(menuTree.getNodeId());
				asyncTreeNode.setText(menuTree.getMenuTreeTextByLanguage());
				// asyncTreeNode.setCls("tree_icon");
				// asyncTreeNode.setIconCls("icon-config");
				asyncTreeNode.setCls("folder");
				asyncTreeNode.setIconCls(menuTree.getIcon());
				asyncTreeNode.setMenuId(menuId);
				asyncTreeNode.setHrefTarget(url.trim());
				// asyncTreeNode.setChecked(true);
				if (StringUtils.isNotBlank(nodeType))
				{
					if (nodeType.equals("1"))
					{
						asyncTreeNode.setLeaf(false);
					}
					else
					{
						asyncTreeNode.setLeaf(true);
					}
				}
				else
				{
					asyncTreeNode.setLeaf(false);
				}

				treeNodelist.add(asyncTreeNode);
			}
		}
		else
		{
			List<MenuTree> menuTrees = this.menuService.getUserStandardMenuList(strMenuId, strNodeId);
			Iterator<MenuTree> itMenuTrees = menuTrees.iterator();
			while (itMenuTrees.hasNext())
			{
				AsyncTreeNode asynTreeNode = new AsyncTreeNode();
				MenuTree menuTree = itMenuTrees.next();
				String parentNodeId = menuTree.getParentNodeId() + "";
				String menuId = strMenuId;
				String url = "";
				// 节点类型
				String nodeType = menuTree.getNodeType();

				url = menuTree.getUrl();// this.userService.getUrlByAuthResourceid(menuTree.getAuthResourceId());

				asynTreeNode.setId(menuTree.getNodeId());
				asynTreeNode.setText(menuTree.getMenuTreeTextByLanguage());
				// asynTreeNode.setCls("folder"); // getSystemMenu
				// asynTreeNode.setHref(strMenuId);
				asynTreeNode.setCls("folder");
				asynTreeNode.setIconCls(menuTree.getIcon());
				asynTreeNode.setMenuId(menuId);
				asynTreeNode.setHrefTarget(url.trim());

				// 如果孩子结点数为０则为叶结点
				if (strNodeId.equals("-1"))
				{
					asynTreeNode.setLeaf(false);
				}
				else
				{
					if (StringUtils.isNotBlank(nodeType))
					{
						if (nodeType.equals("1"))
						{
							asynTreeNode.setLeaf(false);
						}
						else
						{
							asynTreeNode.setLeaf(true);
						}
					}
					else
					{
						asynTreeNode.setLeaf(false);
					}
				}

				treeNodelist.add(asynTreeNode);
			}
		}

		// TODO LJX
		// LJX 20100427 Modify 加入信达旧系统，系统菜单。
		if (xdssTreeNodeList != null && xdssTreeNodeList.size() > 0)
			treeNodelist.addAll(xdssTreeNodeList);

		JSONArray ja = JSONArray.fromObject(treeNodelist);
		jsontext = ja.toString();

		return jsontext;
	}

	/**
	 * 取得系统个人菜单根目录节点
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public Set<AsyncTreeNode> getSystemPersonalRootMenu(User user, String strNodeId) throws IOException
	{
		Set<AsyncTreeNode> treeNodelist = new LinkedHashSet<AsyncTreeNode>();
		// TreeSet<AsyncTreeNode> asyncTreeNodeSet = getTreeSet();
		// Set<Menu> menus = this.userService.getUserMenuList(user);
		Set<Menu> menus = this.userService.getUserMenus(user.getUserId());
		Iterator<Menu> itMenus = menus.iterator();
		Integer i = 0;
		while (itMenus.hasNext())
		{
			AsyncTreeNode asyncTreeNode = new AsyncTreeNode();
			Menu menu = itMenus.next();

			asyncTreeNode.setId(menu.getMenuId());
			asyncTreeNode.setText(menu.getMenuNameByLanguage());
			asyncTreeNode.setCls("folder");
			asyncTreeNode.setIconCls(asyncTreeNode.getIconCls());
			asyncTreeNode.setHrefTarget("");
			asyncTreeNode.setMenuId("-1");
			asyncTreeNode.setLeaf(false);
			asyncTreeNode.setOrderNo(i.toString());

			treeNodelist.add(asyncTreeNode);
			i = i + 1;
		}

		// 加载用户被授权菜单树
		if (this.userAuthMoveJdbcDao.isUserAuthRemoveMenu(user.getUserId()))
		{
			AsyncTreeNode asyncTreeNode = new AsyncTreeNode();

			asyncTreeNode.setId("OtherRemoveMenu");
			asyncTreeNode.setText("被授权菜单");
			asyncTreeNode.setCls("folder");
			asyncTreeNode.setIconCls(asyncTreeNode.getIconCls());
			asyncTreeNode.setHrefTarget("");
			asyncTreeNode.setMenuId("-1");
			asyncTreeNode.setLeaf(false);
			asyncTreeNode.setOrderNo(i.toString());
			treeNodelist.add(asyncTreeNode);
		}

		// JSONArray ja = JSONArray.fromObject(treeNodelist);
		// String jsontext = ja.toString();
		// log.debug("取得个人菜单树，根节点树菜单JSON:" + jsontext);

		return treeNodelist;
	}

	/**
	 * 取得系统个人菜单树,第2级菜单JSON字符串
	 * 
	 * @param strNodeId
	 * @param treeNodelist
	 * @param menuTrees
	 * @return
	 */
	public Set<AsyncTreeNode> getSystemPersonalMenuJSONString(String strNodeId, Set<MenuTree> menuTrees)
	{
		Set<AsyncTreeNode> treeNodelist = new LinkedHashSet<AsyncTreeNode>();

		// JSONArray ja = new JSONArray();
		Iterator<MenuTree> itMenuTrees = menuTrees.iterator();
		while (itMenuTrees.hasNext())
		{
			AsyncTreeNode asyncTreeNode = new AsyncTreeNode();
			MenuTree menuTree = itMenuTrees.next();
			String parentNodeId = menuTree.getParentNodeId() + "";
			String menuId = menuTree.getMenuId();
			String url = "";
			// 节点类型
			String nodeType = menuTree.getNodeType();

			if ((parentNodeId.equals("-1") && menuId.equals(strNodeId)) || strNodeId.equals("OtherRemoveMenu"))
			{
				url = menuTree.getUrl();// this.userService.getUrlByAuthResourceid(menuTree.getAuthResourceId());
				asyncTreeNode.setId(menuTree.getNodeId());
				asyncTreeNode.setText(menuTree.getMenuTreeTextByLanguage());
				asyncTreeNode.setCls("folder");
				asyncTreeNode.setIconCls(menuTree.getIcon());
				asyncTreeNode.setMenuId(menuId);
				asyncTreeNode.setOrderNo(menuTree.getOrderNo());
				asyncTreeNode.setHrefTarget(url.trim());

				// 如果孩子结点数为０则为叶结点
				if (strNodeId.equals("-1"))
				{
					asyncTreeNode.setLeaf(false);
				}
				else
				{
					if (StringUtils.isNotBlank(nodeType))
					{
						if (nodeType.equals("1"))
						{
							asyncTreeNode.setLeaf(false);
						}
						else
						{
							asyncTreeNode.setLeaf(true);
						}
					}
					else
					{
						asyncTreeNode.setLeaf(false);
					}
				}

				if (strNodeId.equals("OtherRemoveMenu"))
				{
					asyncTreeNode.setLeaf(true);
				}

				treeNodelist.add(asyncTreeNode);
			}
		}
		// ja = JSONArray.fromObject(treeNodelist);

		return treeNodelist;
	}

}
