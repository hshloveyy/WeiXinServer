/*
 * @(#)SysUserAuthService.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：傅渊源
 *  时　间：2008-12-16
 *  描　述：创建
 */

package com.infolion.platform.console.org.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infolion.platform.console.menu.domain.SysResource;
import com.infolion.platform.console.org.dao.SysUserAuthJdbcDao;
import com.infolion.platform.console.org.domain.SysRole;
import com.infolion.platform.console.org.domain.SysUserAuth;
import com.infolion.platform.core.service.BaseJdbcService;
@Service
public class SysUserAuthService extends BaseJdbcService {
	@Autowired
	private SysUserAuthJdbcDao sysUserAuthJdbcDao;

	public void setSysUserAuthJdbcDao(SysUserAuthJdbcDao sysUserAuthJdbcDao) {
		this.sysUserAuthJdbcDao = sysUserAuthJdbcDao;
	}
	
	public List<SysUserAuth> queryAuthByCondition(SysUserAuth sysUserAuth){
		return sysUserAuthJdbcDao.queryAuthByCondition(sysUserAuth);
	}
	
	public void addUserAuth(SysUserAuth in_sysUserAuth){
		sysUserAuthJdbcDao.addUserAuth(in_sysUserAuth);
	}
	
	public List<SysRole> queryRoleForTransferManage(String in_strDeptUserId){
		return sysUserAuthJdbcDao.queryRoleForTransferManage(in_strDeptUserId);
	}
	
	public List<SysResource> queryResourceForTransferManage(String in_strDeptUserId){
		return sysUserAuthJdbcDao.queryResourceForTransferManage(in_strDeptUserId);
	}
	
	public void deleteUserAuth(String in_strAuthIdList){
		sysUserAuthJdbcDao.deleteUserAuth(in_strAuthIdList);
	}
}
