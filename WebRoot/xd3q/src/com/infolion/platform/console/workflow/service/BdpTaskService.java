/*
 * @(#)taskService.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：黄登辉
 *  时　间：2009-7-8
 *  描　述：创建
 */

package com.infolion.platform.console.workflow.service;

import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.infolion.platform.basicApp.authManage.UserContextHolder;
import com.infolion.platform.basicApp.authManage.domain.User;
import com.infolion.platform.basicApp.authManage.domain.UserContext;
import com.infolion.platform.basicApp.authManage.domain.UserGroupUser;
import com.infolion.platform.basicApp.authManage.domain.UserRole;
import com.infolion.platform.console.org.domain.SysRole;

/**
 * <pre>
 * 
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
public class BdpTaskService
{
	/**
	 * 得到当前的UserContext
	 * 
	 * @return
	 */
	private static UserContext getUserContext()
	{
		UserContextHolder userContextHolder = UserContextHolder.getLocalUserContext();
		UserContext userContext = userContextHolder.getUserContext();
		return userContext;
	}

	/**
	 * 取得BDP，工作流待办查询条件。
	 * 
	 * @return
	 */
	public static String getWhereSql()
	{
		com.infolion.platform.console.sys.context.UserContext userContext = 
			com.infolion.platform.console.sys.context.UserContextHolder.getLocalUserContext().getUserContext();
		User user = getUserContext().getUser();
		 
		//Set<UserRole> userRoles = user.getUserRoles();
		List<SysRole> userRoles = userContext.getGrantedRoles();
		Set<UserGroupUser> userGroupUsers = user.getUserGroupUsers();
		String actorIds = "";
		String tag = "";
		for (UserGroupUser userGroupUser : userGroupUsers)
		{
			actorIds += tag + "'" + userGroupUser.getUserGroup().getUserGroupId() + "'";
			tag = ",";
		}
		for (SysRole userRole : userRoles)
		{
			actorIds += tag + "'" + userRole.getRoleId() + "'";
			tag = ",";
		}

		// 去除参与者类型限制
		String whereSql = "";
		if (StringUtils.isNotBlank(actorIds))
			whereSql += " ACTOR_ID in (" + actorIds + ",'" + user.getUserId() + "')";
		else
			whereSql += " ACTOR_ID in ('" + user.getUserId() + "')";

		return whereSql;
	}
}
