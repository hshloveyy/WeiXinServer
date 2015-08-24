/*
 * @(#)LoginDept.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：傅渊源
 *  时　间：2008-12-5
 *  描　述：创建
 */

package com.infolion.platform.console.login.domain;

import com.infolion.platform.core.domain.BaseObject;

public class LoginDept extends BaseObject {
	private String deptId;
	private String deptName;
	
	public LoginDept() {
		super();
	}
	
	public String getDeptId() {
		return deptId;
	}
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
}
