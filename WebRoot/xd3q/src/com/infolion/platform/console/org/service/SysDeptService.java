/*
 * @(#)TSysDeptService.java
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

import com.infolion.platform.console.org.dao.SysDeptJdbcDao;
import com.infolion.platform.console.org.domain.SysDept;
import com.infolion.platform.console.workflow.domain.ComboBoxFormat;
import com.infolion.platform.core.service.BaseJdbcService;
@Service
public class SysDeptService extends BaseJdbcService {
	@Autowired
	private SysDeptJdbcDao tSysDeptJdbcDao;
	
	public void setTSysDeptJdbcDao(SysDeptJdbcDao sysDeptJdbcDao) {
		tSysDeptJdbcDao = sysDeptJdbcDao;
	}
	
	public List<SysDept> queryDeptByParentId(String in_strParentId){
		return tSysDeptJdbcDao.queryDeptByParentId(in_strParentId);
	}
	
	public SysDept queryDeptById(String in_strDeptId){
		return tSysDeptJdbcDao.queryDeptById(in_strDeptId);
	}
	
	public void addDept(SysDept sysDept){
		tSysDeptJdbcDao.addDept(sysDept);
	}
	
	public void updateDept(SysDept sysDept){
		tSysDeptJdbcDao.updateDept(sysDept);
	}
	
	public void deleteDept(String in_strDeptId){
		tSysDeptJdbcDao.deleteDept(in_strDeptId);
	}
	
	/**
	 * 得到公司代码
	 * @param strDeptId
	 * @return
	 */
	public String getCompanyCode(String strDeptId){
		return this.tSysDeptJdbcDao.getCompanyCode(strDeptId);
	}
}
