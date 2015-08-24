package com.infolion.sapss.sqlReport.dao;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import com.infolion.platform.core.BusinessException;
import com.infolion.platform.core.dao.BaseDao;
import com.infolion.sapss.common.ExcelObject;

@Repository
public class SqlElementJdbcDao extends BaseDao{
	
	public List<String> analySqlFields(String sql){
		List<String> list = new ArrayList<String>();
		try {
			Statement stmt = getJdbcTemplate().getDataSource().getConnection().createStatement();
			ResultSet rs = stmt.executeQuery("select * from ("+sql+") where ROWNUM=1");  
			ResultSetMetaData rsmd = rs.getMetaData();
			while (rs.next()) {  
				for (int i = 1; i <= rsmd.getColumnCount(); i++) {
					list.add(rsmd.getColumnName(i));
				}  
			}
		} catch (SQLException e) {
			throw new BusinessException(e);
		}
		return list;
	}
	
	
	public void updateSqlField(String sqlFieldId, String colName, String colValue){
		String sql = " update t_sql_fielddf " + "set " + colName + "='" + colValue + "' where sqlFieldDfId = ?";
		getJdbcTemplate().update(sql, new Object[] { sqlFieldId });
	}
	
	public void dealOutToExcel(String sql ,final ExcelObject excel,final List<String> fields){
		
		getJdbcTemplate().query(sql,new Object[]{}, new RowCallbackHandler(){
			public void processRow(ResultSet rs) throws SQLException{
				String[] values = new String[fields.size()];
				for(int i = 0 ;i<fields.size();i++){
					values[i] = rs.getString(fields.get(i));
				}
				try{
					excel.addRow(values);
				}
				catch (Exception e) {
                    e.printStackTrace();
				}
			}});
	}

}
