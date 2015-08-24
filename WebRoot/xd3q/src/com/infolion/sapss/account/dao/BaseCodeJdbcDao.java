package com.infolion.sapss.account.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import com.infolion.platform.core.dao.BaseDao;

@Repository
public class BaseCodeJdbcDao extends BaseDao {
	public String findCMD(String id) {
		StringBuffer sb = new StringBuffer();
		sb
				.append("select cmd from bm_function_range where is_available='1' and id=?");
		ResultSetExtractor rse = new ResultSetExtractor() {
			public Object extractData(ResultSet rs) throws SQLException,
					DataAccessException {
				if (rs.next()) {
					return rs.getString("cmd");
				}
				return null;
			}
		};
		String cmd = (String) this.getJdbcTemplate().query(sb.toString(),
				new Object[] { id }, rse);
		return cmd;
	}
}
