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
public class NegotiationJdbcDao extends BaseDao
{

	/**
	 * 获得议付编号
	 * 
	 * @return
	 */
	public String getNegotiationNo()
	{
		final List<String> returnFalg = new ArrayList();
		String sql = "select lpad(nvl(max(a.negotiation_no) + 1,1),10,'0') discount_no  from t_negotiation a";

		getJdbcTemplate().query(sql, new RowCallbackHandler()
		{
			public void processRow(ResultSet rs) throws SQLException
			{
				returnFalg.add(rs.getString("discount_no"));
			}
		});

		if (returnFalg.size() == 0)
			return "";
		else
			return returnFalg.get(0);
	}

}
