/*
 * @(#)SysRole.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：傅渊源
 *  时　间：2008-12-5
 *  描　述：创建
 */

package com.infolion.platform.console.org.domain;

import com.infolion.platform.core.annotation.ValidateRule;
import com.infolion.platform.core.domain.BaseObject;
import com.infolion.platform.core.validation.DataType;

public class SysRole extends BaseObject {
	private String roleId;
	private String roleType;
	@ValidateRule(dataType = DataType.STRING, label = "角色名称", maxLength = 100,required = true)
	private String roleName;
	private String roleCmd;
	private String roleScope;
	private String parentRole;
	private String creator;
	private String createTime;
	private String deleted;
	private String pRoleName;
	private int childcount;
	
	public SysRole() {
		super();
	}
	
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getRoleType() {
		return roleType;
	}
	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getRoleCmd() {
		return roleCmd;
	}
	public void setRoleCmd(String roleCmd) {
		this.roleCmd = roleCmd;
	}
	public String getRoleScope() {
		return roleScope;
	}
	public void setRoleScope(String roleScope) {
		this.roleScope = roleScope;
	}
	public String getParentRole() {
		return parentRole;
	}
	public void setParentRole(String parentRole) {
		this.parentRole = parentRole;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getDeleted() {
		return deleted;
	}
	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}

	public String getPRoleName() {
		return pRoleName;
	}

	public void setPRoleName(String roleName) {
		pRoleName = roleName;
	}

	public int getChildcount() {
		return childcount;
	}

	public void setChildcount(int childcount) {
		this.childcount = childcount;
	}
}
