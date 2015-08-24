package com.infolion.sapss.waitForTransactWork.dao;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import com.infolion.platform.console.org.domain.SysRole;
import com.infolion.platform.console.org.domain.SysUser;
import com.infolion.platform.dpframework.core.dao.BaseJdbcDao;

@Repository
public class WaitForTransactWorkJdbcDao extends BaseJdbcDao{
	
	/**
	 * 返回手机号非空的用户列表
	 */
	public List<SysUser> getSysUserListByMobile() 
	{		
      final List<SysUser> sysUserList = new ArrayList();
		
		String strSql ="select a.user_name,a.user_id,a.dept_id,a.real_name,a.mobile,b.is_func_dept,a.Position_Des from t_sys_user a,t_sys_dept b where a.dept_id=b.dept_id and a.mobile is not null" ;
		getJdbcTemplate().query(strSql, new RowCallbackHandler(){
			public void processRow(ResultSet rs) throws SQLException{
				SysUser sysUser = new SysUser();
				sysUser.setUserName(rs.getString("user_name"));
				sysUser.setUserId(rs.getString("user_id"));
				sysUser.setDeptId(rs.getString("dept_id"));
				sysUser.setMobile(rs.getString("mobile"));
				sysUser.setRealName(rs.getString("real_name"));
				sysUser.setIsFuncDept(rs.getString("is_func_dept"));
				sysUser.setPositionDes(rs.getString("Position_Des"));
				sysUserList.add(sysUser);
			}});
		
		return sysUserList;

	}
	/**
	 * 获得用户角色ID
	 */
	public List<SysRole> getSysUserRole(String deptUserId) 
	{		
		final List<SysRole> sysRoleList = new ArrayList();
		String strSql = "select a.role_id,b.role_name from t_sys_user_role a,t_sys_role b where a.role_id=b.role_id and dept_user_id=?";
		getJdbcTemplate().query(strSql, new Object[]{deptUserId},new RowCallbackHandler(){
			public void processRow(ResultSet rs) throws SQLException{
				SysRole sysRole = new SysRole();
				sysRole.setRoleId(rs.getString("role_id"));
				sysRole.setRoleName(rs.getString("role_name"));
				sysRoleList.add(sysRole);
			}});
		return sysRoleList;
	}	
	/**
	 * 判断用户是否属于多个部门
	 */
	public int checkUserDept(String userId)
	{
		String sql="select count(*) from (select * from t_sys_dept_user t where user_id='"+userId+"')";
		int iResultCount=this.getResultCount(sql);
		return iResultCount;
	}
	/**
	 * 取得用户dept_user_id
	 */
	public List<SysUser> getDeptUserId(String userId)
	{
		final List<SysUser> sysUserList = new ArrayList();
		String strSql ="select a.dept_user_id,a.dept_id,b.is_func_dept from t_sys_dept_user a,t_sys_dept b where a.dept_id=b.dept_id and user_id=?" ;
		getJdbcTemplate().query(strSql, new Object[]{userId},new RowCallbackHandler(){
			public void processRow(ResultSet rs) throws SQLException{
				SysUser sysUser = new SysUser();
				sysUser.setDeptUserId(rs.getString("dept_user_id"));
				sysUser.setDeptId(rs.getString("dept_id"));
				sysUser.setIsFuncDept(rs.getString("is_func_dept"));				
				sysUserList.add(sysUser);
			}});
		return sysUserList;
	}
	
	
	
	/**
	 * 判断用户是否有待办事项
	 */
	public int checkUserWaitWork(String userName,String deptId,String deptUserId,String userId,String isFuncDept)
	{
		//String bdpWorkTaskWhereSql = BdpTaskService.getWhereSql();
		String bdpDataAuthSql = "";
		String appendSQL = " and 1=1";
		StringBuffer whereSql = new StringBuffer();
		String actorId=this.getUserAllActorId(deptUserId,userId);
		// 非职能部门职能看到自己部门的数据
		if ("2".equals(isFuncDept)){
			// 部门经理可以看到所属部门下的业务记录，其他业务人员不允许看到
			if (this.isManager(deptUserId)){
				appendSQL = " and Department_Id='" + deptId + "' and ((t.ACTOR_NAME not like '%业务员%' or t.CREATOR='" + userName + "') ";
				appendSQL += "or " + "(Department_Id='" + deptId + "' and CREATOR not in (select u.user_name from t_sys_user u,t_sys_dept_user t where u.user_id=t.user_id and t.dept_id='" + deptId + "')))";

				bdpDataAuthSql = " and Department_Id='" + deptId + "' and ((t.ACTOR_NAME not like '%业务员%' or t.CREATOR='" + deptUserId + "') ";
				bdpDataAuthSql += "or " + "(Department_Id='" + deptId + "' and CREATOR not in (select u.user_id from t_sys_user u,t_sys_dept_user t where u.user_id=t.user_id and t.dept_id='" + deptId + "')))";
			}
			else{
		        appendSQL = " and((Department_Id='" + deptId + "' and CREATOR='" + userName + "') or " + "(Department_Id='" + deptId + "' and CREATOR not in (select u.user_name from t_sys_user u,t_sys_dept_user t where u.user_id=t.user_id and t.dept_id='" + deptId + "')))";
		        bdpDataAuthSql = " and((Department_Id='" + deptId + "' and CREATOR='" + deptUserId + "') or " + "(Department_Id='" + deptId + "' and CREATOR not in (select u.user_id from t_sys_user u,t_sys_dept_user t where u.user_id=t.user_id and t.dept_id='" + deptId + "')))";
			}
			whereSql.append(" ((PROCESSTYPE='1' and ACTOR_ID in (" + actorId + ")" + appendSQL + ")");
			whereSql.append(" or (PROCESSTYPE='2' and ACTOR_ID in (" + actorId + ")"  + bdpDataAuthSql + "))");
			}
		else
		{   String grantedDepartmentsId=this.queryUserManagerDepartment(deptUserId);
			String processSql = this.getUserProcessSql(deptUserId,userId,grantedDepartmentsId);
			String authSql = this.getAuthProcessSql(deptUserId);
			log.debug("转移权限：" + authSql);
		    whereSql.append(" ((processType = '1' and (" + processSql + authSql + ")) ");
		    whereSql.append(" or (PROCESSTYPE='2' and ((ACTOR_ID in (" + actorId + ") and Department_Id in (" + grantedDepartmentsId + ")) " + authSql + ") ))");}
		String sql = "select count(*) from (" +
				"select * from (select rownum rnum,a.*  from ( select distinct t.BUSINESS_RECORD_ID from V_SYS_WF_PROCESS_USER t where 1=1 and " +
				whereSql.toString()+")a))";
		log.debug("工作流待办SQL查询语句:" + sql.toString());
		int iResultCount=this.getResultCount(sql);
		return iResultCount ;
	
	}
	/**
	 * 获得用户管理的部门
	 */
	public String queryUserManagerDepartment(String deptUserId)
	{
		String strSql = "select t.dept_id from t_sys_user_manager_dept t where t.user_id=?";
		final StringBuffer sb = new StringBuffer();
		getJdbcTemplate().query(strSql, new Object[] { deptUserId }, new RowCallbackHandler()
		{
			public void processRow(ResultSet rs) throws SQLException
			{
				sb.append("'").append(rs.getString("dept_id")).append("'").append(",");
			}
		});
		String result = sb.toString();
		if (null != result && !"".equals(result))
		{
			result = result.substring(0, result.length() - 1);
		}
		if (null == result || "".equals(result))
			result = "''";
		return result;
	}
	
	
	public String getAuthProcessSql(String deptUserId)
	{
		final StringBuffer sb = new StringBuffer();
		final List<String> list = new ArrayList<String>();
		String strSql = "select t.from_user_id,t.to_id from t_sys_user_auth t where t.to_user_id=? and is_effect=1";
		getJdbcTemplate().query(strSql, new Object[] {deptUserId}, new RowCallbackHandler()
		{
			public void processRow(ResultSet rs) throws SQLException
			{
				list.add(rs.getString("from_user_id") + "," + rs.getString("to_id"));
			}
		});
		if (list.isEmpty())
			return "";
		String sql = "";
		for (Iterator<String> it = list.iterator(); it.hasNext();)
		{
			String[] s = it.next().split(",");
			String fromUserId = s[0];
			String roleId = s[1];
			sql += " or (ACTOR_ID ='" + roleId + "' and Department_Id in (select dept_id from t_sys_user_manager_dept where user_id='" + fromUserId + "'))";
		}

		return sql;
	}
	
	
	public String getUserProcessSql(String deptUserId,String userId,String grantedDepartmentsId)
	{
		final StringBuffer sb = new StringBuffer();
		String strSql = "select f.role_id from t_sys_user_role f where f.dept_user_id=?";
		getJdbcTemplate().query(strSql, new Object[] {deptUserId}, new RowCallbackHandler()
		{
			public void processRow(ResultSet rs) throws SQLException
			{
				sb.append("'");
				sb.append(rs.getString("role_id"));
				sb.append("'");
				sb.append(",");
			}
		});
		sb.append("'");
		sb.append(userId);
		sb.append("'");

		return " (ACTOR_ID in (" + sb.toString() + ") and Department_Id in (" + grantedDepartmentsId + "))";
	}
	
	/**
	 * 获得用户ActorId
	 */
	private String getUserAllActorId(String deptUserId,String userId){
		List<SysRole> sysUserRoleList=this.getSysUserRole(deptUserId);
		StringBuffer sb = new StringBuffer();
		for(int i=0; i<sysUserRoleList.size(); i++){
			SysRole sysRole = sysUserRoleList.get(i);
			String userRoleId=sysRole.getRoleId();
			sb.append("'");
			sb.append(userRoleId);
			sb.append("'");
			sb.append(",");
		}
		sb.append("'");
		sb.append(userId);
		sb.append("'");
		return sb.toString();
	}
	
	/**
	 *判断当前用户是否是管理人员，目前用角色名称判断
	 * 
	 * @param rolesList
	 * @return
	 */
	public boolean isManager(String deptUserId)
	{
		List<SysRole> grantedRoles = this.getSysUserRole(deptUserId);
		for (Iterator iterator = grantedRoles.iterator(); iterator.hasNext();)
		{
			SysRole sysRole = (SysRole) iterator.next();
			if (sysRole.getRoleName().indexOf("经理") != -1)
				return true;
		}
		return false;
	}
	/**
	 * 取的语句的结果集行数
	 * 
	 * @param in_strSql
	 * @return
	 */
	public int getResultCount(String in_strSql)
	{
		return getJdbcTemplate().queryForInt(in_strSql);
	}
	
	public String isOYZAutoSubmit(String userName,String businessId,String type){
		String sql = "";
		if("1".equals(type)) {
			sql = "select ts.name_,ts.id_ from t_sys_wf_process_instance ti left outer join jbpm_taskinstance ts on ti.process_id=ts.procinst_ " +
			"where ts.end_ is null and  ti.business_record_id='" + businessId + "'";
		}else if("2".equals(type)){
			sql = "select ts.name_,ts.id_ from wf_processins ti left outer join jbpm_taskinstance ts on ti.processid=ts.procinst_ " +
			"where ts.end_ is null and  ti.businessid='" + businessId + "'";
		}
		List list = getJdbcTemplate().queryForList(sql);
		if(list!=null&&list.size()>0){
			Map map = (Map)list.get(0);
			if(((String)map.get("name_")).indexOf("股份公司总经理")>-1){
				return ((BigDecimal)map.get("id_")).toString();
			}
		}
		return "";
	}
}
