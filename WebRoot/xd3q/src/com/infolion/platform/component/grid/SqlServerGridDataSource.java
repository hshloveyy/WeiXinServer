/*
 * @(#)SqlServerGridDataSource.java
 * 版权声明： 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：陈喜平
 *  时　间：2008-12-26
 *  描　述：创建
 */

package com.infolion.platform.component.grid;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;

import com.infolion.platform.util.EasyApplicationContextUtils;

public class SqlServerGridDataSource implements GridDataSource  {
	public SqlServerGridDataSource(){
		if(jdbc==null)
			jdbc = (JdbcTemplate)EasyApplicationContextUtils.getBeanByName("jdbcTemplate");
	}
	private JdbcTemplate jdbc;
	/**
	 * 获取记录数
	 */
	public int countTotalRecords(String sql) {
		int cnt = jdbc.queryForInt("select count(*) as cnt "+sql);
		return cnt;
	}
	/**
	 * 取得每行数据
	 */
	public List getRecords(String sql, int start, int limit,String primary) {
		String s = "select * from (select ROW_NUMBER() OVER(ORDER BY "+primary+" DESC) AS rank,"+sql+")t where t.rank BETWEEN "+start+" and "+limit;
		return jdbc.queryForList(s);
	}
}
