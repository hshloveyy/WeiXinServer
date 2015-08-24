/*
 * @(#)loginService.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：傅渊源
 *  时　间：2008-12-5
 *  描　述：创建
 */

package com.infolion.platform.console.login.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infolion.platform.console.login.dao.LoginJdbcDao;
import com.infolion.platform.console.login.domain.LoginDept;
import com.infolion.platform.console.menu.domain.SysResource;
import com.infolion.platform.console.org.domain.SysRole;
import com.infolion.platform.console.org.domain.SysUser;
import com.infolion.platform.core.service.BaseJdbcService;
@Service
public class LoginService extends BaseJdbcService {
	@Autowired
	private LoginJdbcDao loginJdbcDao;

	public void setLoginJdbcDao(LoginJdbcDao loginJdbcDao) {
		this.loginJdbcDao = loginJdbcDao;
	}
	
	public List<SysUser> queryUserIsExist(String in_strUserName,String in_strPassword){
		return loginJdbcDao.queryUserIsExist(in_strUserName, in_strPassword);
	}
	
	public List<LoginDept> queryLoginDept(String in_strUserId){
		return loginJdbcDao.queryLoginDept(in_strUserId);
	}
	
	public List<SysResource> queryUserResource(String in_strDeptUserId,String in_strResourceParentId){
		return loginJdbcDao.queryUserResource(in_strDeptUserId, in_strResourceParentId);
	}
	
	public List<SysResource> queryTransMenu(String in_strParentId){
		return loginJdbcDao.queryTransMenu(in_strParentId);
	}
	
	public List<SysResource> queryUserResourceForUserConext(String in_strDeptUserId){
		return loginJdbcDao.queryUserResourceForUserConext(in_strDeptUserId);
	}
	
	public List<SysRole> queryUserRoleForUserConext(String in_strDeptUserId){
		return loginJdbcDao.queryUserRoleForUserConext(in_strDeptUserId);
	}
	public String queryUserManagerDepartments(String userId)
	{
		return loginJdbcDao.queryUserManagerDepartment(userId);
	}
	
	public List<SysUser> queryUserExist(String in_strUserName,String in_strPassword){
		return loginJdbcDao.queryUserExist(in_strUserName, in_strPassword);
	}
}
