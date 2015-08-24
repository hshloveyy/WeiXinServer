/*
 * @(#)SysRoleService.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：傅渊源
 *  时　间：2008-12-7
 *  描　述：创建
 */

package com.infolion.platform.console.org.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infolion.platform.console.menu.domain.SysResource;
import com.infolion.platform.console.org.dao.SysRoleJdbcDao;
import com.infolion.platform.console.org.domain.SysRole;
import com.infolion.platform.core.service.BaseJdbcService;
@Service
public class SysRoleService extends BaseJdbcService {
	@Autowired
	private SysRoleJdbcDao sysRoleJdbcDao;

	public void setSysRoleJdbcDao(SysRoleJdbcDao sysRoleJdbcDao) {
		this.sysRoleJdbcDao = sysRoleJdbcDao;
	}
	
	public List<SysRole> queryRoleByParentId(String in_strParentId){
		return sysRoleJdbcDao.queryRoleByParentId(in_strParentId);
	}

	public SysRole queryRoleByRoleId(String in_strRoleId){
		return sysRoleJdbcDao.queryRoleByRoleId(in_strRoleId);
	}
	
	public void addRole(SysRole sysRole){
		sysRoleJdbcDao.addRole(sysRole);
	}
	
	public void updateRole(SysRole sysRole){
		sysRoleJdbcDao.updateRole(sysRole);
	}
	
	public void deleterRole(String in_strRoleId){
		sysRoleJdbcDao.deleteRole(in_strRoleId);
	}
	
	public List<SysResource> queryResourceByRoleId(String in_strRoleId){
		return sysRoleJdbcDao.queryResourceByRoleId(in_strRoleId);
	}
	
	public void addRoleResource(String in_strResourceIdList, String in_strRoleId){
		sysRoleJdbcDao.addRoleResource(in_strResourceIdList, in_strRoleId);
	}
	
	public void deleteRoleResource(String in_strResourceIdList, String in_strRoleId){
		sysRoleJdbcDao.deleteRoleResource(in_strResourceIdList, in_strRoleId);
	}
	
	public void deleteAllRoleResource(String in_strRoleId){
		sysRoleJdbcDao.deleteAllRoleResource(in_strRoleId);
	}
	
	public List<SysResource> queryResourceForTree(String in_strRoleId, String in_strResourceParentId,String in_strUpRoleId){
		return sysRoleJdbcDao.queryResourceForTree(in_strRoleId, in_strResourceParentId,in_strUpRoleId);
	}
	
	public List<SysRole> queryRoleByLoginUser(String in_strDeptUserId,String in_strRoleParentId){
		return sysRoleJdbcDao.queryRoleByLoginUser(in_strDeptUserId, in_strRoleParentId);
	}
	
	public List<SysRole> queryRoleByDeptUserId(String in_strDeptUserId){
		return sysRoleJdbcDao.queryRoleByDeptUserId(in_strDeptUserId);
	}
	
	public void addUserRole(String in_strRoleList, String in_strDeptUserId){
		sysRoleJdbcDao.addUserRole(in_strRoleList, in_strDeptUserId);
	}
	
	public void addRoleUser(String in_strDeptUserList, String in_strRoleId){
		sysRoleJdbcDao.addRoleUser(in_strDeptUserList, in_strRoleId);
	}
	
	public void deleteUserRole(String in_strRoleList, String in_strDeptUserId){
		sysRoleJdbcDao.deleteUserRole(in_strRoleList, in_strDeptUserId);
	}
	
	public List<String> queryUserRoleByDeptUserId(String in_strDeptUserId){
		return sysRoleJdbcDao.queryUserRoleByDeptUserId(in_strDeptUserId);
	}
}
