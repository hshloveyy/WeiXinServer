package com.infolion.xdss3.masterData.dao;

import org.springframework.stereotype.Repository;

import com.infolion.platform.dpframework.core.dao.BaseJdbcDao;
import com.infolion.xdss3.masterData.domain.CashFlowItem;
import com.infolion.xdss3.masterData.domain.Company;
import com.infolion.xdss3.masterData.domain.Dept;
import com.infolion.xdss3.masterData.domain.Hkont;

@Repository
public class DeptJdbcDao extends BaseJdbcDao{
	
	/**
	 * 删除部门信息
	 */
	public void delete()
	{
		String sql = "delete from ydeptinfo";
		this.getJdbcTemplate().execute(sql);	
	}
	
	/**
	 * 插入部门数据
	 * @param dept
	 */
	public void insert(Dept dept)
	{
		String sql = "insert into ydeptinfo(MANDT,DEPTID,DEPTNAME,COMPANYID) values( '800', '"
			 + dept.getDeptid() + "', '" + dept.getDeptName() + "', '" + dept.getCompanyid()  + "')";
		
		this.getJdbcTemplate().execute(sql);
	}
}
