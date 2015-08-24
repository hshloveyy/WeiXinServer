/*
 * @(#)LoginXdss3Service.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：林进旭
 *  时　间：2010-4-27
 *  描　述：创建
 */

package com.infolion.platform.console.login.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infolion.platform.basicApp.authManage.dao.UserJdbcDao;
import com.infolion.platform.basicApp.authManage.domain.AsyncTreeNode;
import com.infolion.platform.basicApp.authManage.service.UserService;
import com.infolion.platform.console.menu.domain.SysResource;
import com.infolion.platform.dpframework.core.service.BaseService;

/**
 * <pre>
 * * 信达旧系统与BDP平台整合登陆服务
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
public class LoginXdss3Service extends BaseService
{

	@Autowired
	private UserJdbcDao bdpUserJdbcDao;

	public void setUserJdbcDao(UserJdbcDao userJdbcDao)
	{
		this.bdpUserJdbcDao = userJdbcDao;
	}

	@Autowired
	private UserService bdpUserService;

	/**
	 * @param bdpUserService
	 *            the bdpUserService to set
	 */
	public void setBdpUserService(UserService bdpUserService)
	{
		this.bdpUserService = bdpUserService;
	}

	@Autowired
	private LoginService xdssLoginService;

	/**
	 * @param xdssLoginService
	 *            the xdssLoginService to set
	 */
	public void setXdssLoginService(LoginService xdssLoginService)
	{
		this.xdssLoginService = xdssLoginService;
	}

	/**
	 * 根据信达用用户名称，取得Client：800、的BDP用户信息。 如果找不到对应的用户则返回NULL.
	 * 
	 * @param xdssUserName
	 * @return
	 */
	public Map<String, String> getBDPUserBaseInfo(String xdssUserName)
	{
		Map<String, String> map = bdpUserJdbcDao.getUserBaseInfo(xdssUserName, "800");
		return map;
	}

	/**
	 * 登陆BDP成功后，执行相对应的操作。
	 * 
	 * @param request
	 * @param client
	 * @param languageIso
	 * @param strUserId
	 */
	public void loginBdpSucessAction(HttpServletRequest request, String client, String languageIso, String strUserId)
	{
		bdpUserService.loginSuccessAction(request, client, languageIso, strUserId);
	}

	/**
	 * 取得XDSS菜单树
	 * 
	 * @param deptUserId
	 * @param nodeId
	 * @return
	 */
	public List<AsyncTreeNode> getXdssSysMenuTreeNodeList(String deptUserId, String nodeId)
	{
		List<SysResource> sysResourceList = xdssLoginService.queryUserResource(deptUserId, nodeId);
		SysResource sysResource = new SysResource();
		List<AsyncTreeNode> treeNodelist = new ArrayList<AsyncTreeNode>();

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

		return treeNodelist;
	}

	/**
	 * 取得XDSS權限轉移菜單
	 * 
	 * @param nodeId
	 * @return
	 */
	public List<AsyncTreeNode> getXdssAuthRemoveTreeNodeList(String nodeId)
	{
		List<SysResource> sysResourceList = xdssLoginService.queryTransMenu(nodeId);
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
			if (nodeId.equals("-1"))
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

		return treeNodelist;
	}

}
