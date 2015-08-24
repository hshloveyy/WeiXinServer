/*
 * @(#)ExportApplyJdbcDao.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：林哲文
 *  时　间：2009-06-11
 *  描　述：创建
 */

package com.infolion.sapss.export.dao;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import com.infolion.platform.core.dao.BaseDao;

@Repository
public class BaleLoanJdbcDao extends BaseDao
{

	/**
	 * 获得打包贷款编号
	 * 
	 * @return
	 */
	public String getBaleLoanNo()
	{
		final List<String> returnFalg = new ArrayList();
		String sql = "select lpad(nvl(max(a.bale_loan_no) + 1,1),10,'0') bale_loan_no  from t_bale_loan a";

		getJdbcTemplate().query(sql, new RowCallbackHandler()
		{
			public void processRow(ResultSet rs) throws SQLException
			{
				returnFalg.add(rs.getString("bale_loan_no"));
			}
		});

		if (returnFalg.size() == 0)
			return "";
		else
			return returnFalg.get(0);
	}

}
