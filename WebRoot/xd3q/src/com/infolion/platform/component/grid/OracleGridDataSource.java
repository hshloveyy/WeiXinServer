package com.infolion.platform.component.grid;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.jdbc.core.JdbcTemplate;

import com.infolion.platform.sys.cache.SysCachePoolUtils;
import com.infolion.platform.util.EasyApplicationContextUtils;

public class OracleGridDataSource implements GridDataSource {
	private JdbcTemplate jdbc;

	public OracleGridDataSource() {
		if (jdbc == null)
			jdbc = (JdbcTemplate) EasyApplicationContextUtils
					.getBeanByName("jdbcTemplate");
	}

	public int countTotalRecords(String sql) {
		int cnt = jdbc.queryForInt("select count(*) as cnt from (" + sql + ")");
		return cnt;
	}

	public List getRecords(String sql, int start, int limit, String columns) {
		String s = "select * from (select ROWNUM rowNo, nest1.* from (" + sql
				+ " )nest1)nest2 where nest2.rowNo>" + (start - 1)
				+ " and nest2.rowNo<" + (limit + 1);
		s = "select " + columns + " from (" + s + ")";
		// 字典表临时解决方案
		List result = jdbc.queryForList(s);
		for (Iterator iterator = result.iterator(); iterator.hasNext();) {
			Map rowMap = (Map) iterator.next();
			Set rowColumnNames = rowMap.keySet();
			for (Iterator iterator2 = rowColumnNames.iterator(); iterator2
					.hasNext();) {
				String columnName = (String) iterator2.next();
				columnName = columnName.toUpperCase();
				if (columnName.indexOf("_D_") != -1) {
					int beginIndex = columnName.indexOf("_D_");
					String dictName = columnName.substring(beginIndex + 3);
					Object dictOldValue =  rowMap.get(columnName);
					String dictValue="";
					if (dictOldValue instanceof java.math.BigDecimal) {
						java.math.BigDecimal new_name = (java.math.BigDecimal) dictOldValue;
						dictValue = new_name.longValue()+"";
					}else
						dictValue = (String)dictOldValue;
					String dictCnValue = SysCachePoolUtils.getDictDataValue(
							"BM_" + dictName.toUpperCase(), dictValue);
					// rowMap.remove(columnName);
//					System.out.println(columnName+"----"+dictCnValue);
					rowMap.put(columnName, dictCnValue);
				}
			}
		}
		return result;
	}

}
