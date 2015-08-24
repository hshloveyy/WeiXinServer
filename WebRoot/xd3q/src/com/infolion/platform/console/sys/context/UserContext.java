/*
 * @(#)UserContext.java
 * 版权声明： 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：张崇镇
 *  时　间：2008-12-4
 *  描　述：创建
 */

package com.infolion.platform.console.sys.context;

import java.util.List;

import com.infolion.platform.console.menu.domain.SysResource;
import com.infolion.platform.console.org.domain.SysDept;
import com.infolion.platform.console.org.domain.SysRole;
import com.infolion.platform.console.org.domain.SysUser;

/**
 * 
 * <pre>
 * XDSS,用户信息上下文，与用户对象关联的对象信息持有者
 * </pre>
 * 
 * <br>
 * JDK版本:1.5
 * 
 * @author 张崇镇
 * @version 1.0
 * @see The author for more details
 * @since 1.0
 */
public class UserContext
{
	// 用户对象
	private SysUser sysUser;
	// 所属部门
	private SysDept sysDept;
	// 已授权角色
	private List<SysRole> grantedRoles;
	// 已授权系统资源
	private List<SysResource> grantedResources;
	// 已授权的部门数据范围
	private String grantedDepartmentsId;

	public UserContext(SysUser sysUser, SysDept sysDept, List<SysRole> grantedRoles, List<SysResource> grantedResources, String grantedDepartmentsId)
	{
		this.sysUser = sysUser;
		this.sysDept = sysDept;
		this.grantedRoles = grantedRoles;
		this.grantedResources = grantedResources;
		this.grantedDepartmentsId = grantedDepartmentsId;
	}

	public SysUser getSysUser()
	{
		return sysUser;
	}

	public void setSysUser(SysUser sysUser)
	{
		this.sysUser = sysUser;
	}

	public SysDept getSysDept()
	{
		return sysDept;
	}

	public void setSysDept(SysDept sysDept)
	{
		this.sysDept = sysDept;
	}

	public List getGrantedRoles()
	{
		return grantedRoles;
	}

	public void setGrantedRoles(List<SysRole> grantedRoles)
	{
		this.grantedRoles = grantedRoles;
	}

	public List<SysResource> getGrantedResources()
	{
		return grantedResources;
	}

	public void setGrantedResources(List<SysResource> grantedResources)
	{
		this.grantedResources = grantedResources;
	}

	public String getGrantedDepartmentsId()
	{
		return grantedDepartmentsId;
	}

	public void setGrantedDepartmentsId(String grantedDepartmentsId)
	{
		this.grantedDepartmentsId = grantedDepartmentsId;
	}
}
