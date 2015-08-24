package com.infolion.xdss3.masterData.dao;

import org.springframework.stereotype.Repository;

import com.infolion.platform.dpframework.core.dao.BaseJdbcDao;
import com.infolion.xdss3.masterData.domain.CashFlowItem;
import com.infolion.xdss3.masterData.domain.Company;
import com.infolion.xdss3.masterData.domain.Hkont;

@Repository
public class CompanyJdbcDao extends BaseJdbcDao{
	
	/**
	 * 删除公司信息
	 */
	public void delete()
	{
		String sql = "delete from ycompanyinfo";
		this.getJdbcTemplate().execute(sql);	
	}
	
	/**
	 * 插入公司数据
	 * @param hkont
	 */
	public void insert(Company company)
	{
		String sql = "insert into ycompanyinfo(MANDT,COMPANYID,COMANYNAME) values( '800', '"
			 + company.getCompanyid() + "', '" + company.getCompanyName()  + "')";
		
		this.getJdbcTemplate().execute(sql);
	}
}
