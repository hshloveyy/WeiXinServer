package com.infolion.platform.webservice;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import com.infolion.platform.core.dao.BaseDao;

@Repository
public class XindecoClientService extends BaseDao{
	
	/**
	 * 校验用户名，密码
	 */
	public boolean checkValidateUser(String userName,String passWord){
		String sql = "select count(*) from t_sys_user where user_Name='"+userName+"' and passWord='"+passWord+"' and deleted=1";
		int count = getJdbcTemplate().queryForInt(sql);
		return count>0;
	}
	
	public String queryWaitForWork(String userName,String passWord){
		String authSqlQuery ="select t.from_user_id,t.to_id from t_sys_user_auth t where is_effect=1 and  t.to_user_id in " +
        "(select t.dept_user_id from t_sys_dept_user t,t_sys_user b  where t.user_id=b.user_id and b.user_name='"+userName+
        "' and b.password='"+passWord+"')";
		String authSql ="";
		List list = getJdbcTemplate().queryForList(authSqlQuery);
		if(list!=null&&list.size()>0){
			for(int i=0;i<list.size();i++){
				Map map = (Map)list.get(i);
				String fromUserId = (String)map.get("from_user_id");
				String roleId = (String)map.get("to_id");
				authSql +=" or (ACTOR_ID ='"+roleId+"' and Department_Id in (select dept_id from t_sys_user_manager_dept where user_id='"+fromUserId+"'))";
			}
		}
		String sql = "select t.task_id from V_SYS_WF_PROCESS_USER t where (ACTOR_ID in " +
					"(select g1.user_id  from t_sys_user g1 where g1.user_name='"+userName+"' and g1.password='"+passWord+"' union all " +
					"select g3.role_id from t_sys_user_role g3 where g3.dept_user_id in" +
					"(select t.dept_user_id from t_sys_dept_user t,t_sys_user b  where t.user_id=b.user_id and b.user_name='"+userName+"' and b.password='"+passWord+"')) " +
					"and Department_Id in (select g4.dept_id from t_sys_user_manager_dept g4 where " +
					"g4.user_id in (select t.dept_user_id from t_sys_dept_user t,t_sys_user b  where t.user_id=b.user_id and b.user_name='"+userName+"' and b.password='"+passWord+"')))";
        sql +=authSql;
        final StringBuffer bf = new StringBuffer("");
        getJdbcTemplate().query(sql, new Object[]{},new RowCallbackHandler(){
        	public void processRow(ResultSet rs) throws SQLException{
        		bf.append(rs.getString("task_id")+",");
        	}
		});
        return bf.toString();
	}
}
