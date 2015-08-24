package com.infolion.platform.console.menu.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.stereotype.Repository;

import com.infolion.platform.core.dao.BaseDao;
@Repository
public class TestJdbcDao extends BaseDao{
	public TestJdbcDao() {
		super();
	}

	public boolean connectTest(String in_strUserCode,String in_strPassWord){
		boolean flag = false;
		
		Connection connect = null;
		PreparedStatement statement = null;
		String strStaffName = null;
		
		try {
			String strSql = "select a.* from staff a where a.staff_id =? and a.staff_password =?";
			connect = getJdbcTemplate().getDataSource().getConnection();
			statement = connect.prepareStatement(strSql);
			statement.setString(1, in_strUserCode);
			statement.setString(2, in_strPassWord);
			ResultSet rs = statement.executeQuery();
			System.out.println(rs.getRow());
			if (rs.next()){
				strStaffName = rs.getString("staff_name");
				System.out.println(strStaffName);
				flag = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally{
			if (statement != null){
				try {
					statement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
			if (connect != null){
				try {
					connect.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		/*String strProcedureSql = "call TestProcedure(?,?,?)";
		
		Object obj = getJdbcTemplate().execute(strProcedureSql,new CallableStatementCallback(){   
	        public Object doInCallableStatement(CallableStatement cs) throws SQLException, DataAccessException {   
	            cs.setString(1, "fuyy");
	            cs.setString(2, "fuyuanyuan");
	            cs.setString(3, "welcomefyy");
	            cs.execute();   
	            return true;   
	        }      
	    });*/
		/*System.out.println("1111");
		DataSource ds = super.getJdbcTemplate().getDataSource();
		
		if (ds != null){
			flag = true;
			System.out.println("success");
		}else{
			flag = false;
			System.out.println("faile");
		}*/
		return flag;
	}
}
