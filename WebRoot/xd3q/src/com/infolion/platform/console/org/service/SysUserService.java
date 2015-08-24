/*
 * @(#)SysUserService.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：傅渊源
 *  时　间：2008-12-1
 *  描　述：创建
 */

package com.infolion.platform.console.org.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infolion.platform.console.org.dao.SysUserJdbcDao;
import com.infolion.platform.console.org.domain.SysUser;
import com.infolion.platform.core.service.BaseJdbcService;
@Service
public class SysUserService extends BaseJdbcService {
	@Autowired
	private SysUserJdbcDao sysUserJdbcDao;

	public void setSysUserJdbcDao(SysUserJdbcDao sysUserJdbcDao) {
		this.sysUserJdbcDao = sysUserJdbcDao;
	}
	
	public List<SysUser> queryUserByDeptId(String in_strDeptId){
		return sysUserJdbcDao.queryUserByDeptId(in_strDeptId);
	}
	
	public SysUser queryUserById(String in_strUserId,String in_strDeptId){
		return sysUserJdbcDao.queryUserById(in_strUserId,in_strDeptId);
	}
	
	public int queryUserIsExist(String in_strUserName){
		return sysUserJdbcDao.queryUserIsExist(in_strUserName);
	}
	
	public void addUser(SysUser sysUser){
		sysUserJdbcDao.addUser(sysUser);
	}
	
	public void updateUserNo(SysUser sysUser){
		sysUserJdbcDao.updateUserNo(sysUser);
	}
	
	public void updateUser(SysUser sysUser){
		sysUserJdbcDao.updateUser(sysUser);
	}
	
	public void deleteUser(String in_strUserList,String in_strDeptId){
		sysUserJdbcDao.deleteUser(in_strUserList, in_strDeptId);
	}
	
	public List<SysUser> queryUserByUserId(String in_strUserId){
		return sysUserJdbcDao.queryUserByUserId(in_strUserId);
	}
	
	public void addDeptUser(String in_strUserList, String in_strDeptId,String in_strCreate){
		sysUserJdbcDao.addDeptUser(in_strUserList, in_strDeptId,in_strCreate);
	}
	
	public List<SysUser> queryUserByCondition(SysUser sysUser){
		return sysUserJdbcDao.queryUserByCondition(sysUser);
	}
	
	public List<SysUser> queryUserByRoleId(String in_strRoleId){
		return sysUserJdbcDao.queryUserByRoleId(in_strRoleId);
	}
	
	public List<SysUser> receiveUserStaff(String strRoleName,String strDeptId){
		return sysUserJdbcDao.receiveUserStaff(strRoleName,strDeptId);
	}
	
	public int  updateUserPassword(String in_strNewPass, String in_strOldPass){
		return sysUserJdbcDao.updateUserPassword(in_strNewPass, in_strOldPass);
	}
	
	public void addManagerDept(String deptList,String userId){
		sysUserJdbcDao.updateMangerDept(deptList,userId);
	}
	
	public List<String> queryManagerDeptByUserId(String userId){
		return sysUserJdbcDao.queryManagerDeptByUserId(userId);
	}
	
	public String queryEmployeeNo(String userId){
		return sysUserJdbcDao.queryEmployeeNo(userId);
	}
	/**
	 * 根据userName取得该用户下所有权限下的子部门
	 * @param userName
	 * @return
	 */
	public String getAllDeptCodeByUserName(String userName){
		List<String> deptCodeList = sysUserJdbcDao.getAllDeptCodeByUserName(userName);
		String deptCodes="";
		for(String deptCode : deptCodeList){
			deptCodes = deptCodes + "[" + deptCode +"],";
		}
		if(!"".equals(deptCodes))deptCodes = deptCodes.substring(0, deptCodes.length()-1);
		return deptCodes;
	}
}
