/*
 * @(#)TSysResourceService.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：傅渊源
 *  时　间：2008-11-19
 *  描　述：创建
 */

package com.infolion.platform.console.menu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infolion.platform.console.menu.dao.SysResourceJdbcDao;
import com.infolion.platform.console.menu.domain.SysResource;
import com.infolion.platform.core.service.BaseJdbcService;
@Service
public class SysResourceService extends BaseJdbcService{
	@Autowired
	private SysResourceJdbcDao tSysResourceJdbcDao;
	
	public void setTSysResourceJdbcDao(SysResourceJdbcDao sysResourceJdbcDao) {
		tSysResourceJdbcDao = sysResourceJdbcDao;
	}
	
	public List<SysResource> getResourceByParentId(String in_strParentId){
		return tSysResourceJdbcDao.getResourceByParentId(in_strParentId);
	}
	
	public SysResource getResourceById(String in_strId){
		return tSysResourceJdbcDao.getResourceById(in_strId);
	}
	
	public void addResource(SysResource tSysResource){
		tSysResourceJdbcDao.addResource(tSysResource);
	}
	
	public void updateResource(SysResource tSysResource){
		tSysResourceJdbcDao.updateResource(tSysResource);
	}
	
	public void deleteResource(String in_strId){
		tSysResourceJdbcDao.deleteResource(in_strId);
	}
}
