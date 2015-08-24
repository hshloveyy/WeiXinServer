/*
 * @(#)SupplierJdbcDao.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：林进旭
 *  时　间：2010-7-2
 *  描　述：创建
 */

package com.infolion.xdss3.masterData.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.infolion.platform.dpframework.core.dao.BaseJdbcDao;

/**
 * <pre>
 * 供应商数(Supplier)JDBC
 * </pre>
 * 
 * <br>
 * JDK版本:1.5
 * 
 * @author 林进旭
 * @version 1.0
 * @see The author for more details
 * @since 1.0
 */
@Repository
public class SupplierJdbcDao extends BaseJdbcDao
{

	/**
	 *取得供应名称
	 * 
	 * @param user
	 * @return
	 */
	public String getSupplierName(String supplier, String bukrs)
	{
		String sql = "select YGETLIFNR.NAME1 from YGETLIFNR WHERE YGETLIFNR.LIFNR='" + supplier + "'and YGETLIFNR.Bukrs='" + bukrs + "'";
		return (String) this.getJdbcTemplate().queryForObject(sql, String.class);
	}

	/**
	 *取得供应名称
	 * 
	 * @param user
	 * @return
	 */
	public String getSupplierName(String supplier)
	{
		String sql = "select YGETLIFNR.NAME1 as SUPPLIERNAME from YGETLIFNR WHERE YGETLIFNR.LIFNR='" + supplier + "'";
		List list = this.getJdbcTemplate().queryForList(sql);

		if (null != list && list.size() > 0)
			return (String) ((Map) list.get(0)).get("SUPPLIERNAME");
		else
			return "";
	}
}
