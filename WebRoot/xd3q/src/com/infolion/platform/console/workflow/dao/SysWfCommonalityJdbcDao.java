/*
 * @(#)SysWfCommonalityJdbcDao.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：傅渊源
 *  时　间：2009-1-5
 *  描　述：创建
 */

package com.infolion.platform.console.workflow.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import com.infolion.platform.console.org.domain.SysRole;
import com.infolion.platform.console.sys.context.UserContext;
import com.infolion.platform.console.sys.context.UserContextHolder;
import com.infolion.platform.core.dao.BaseDao;

@Repository
public class SysWfCommonalityJdbcDao extends BaseDao
{
	/**
	 * 取得用户所拥有的ActorId(包括个人id，和角色id)
	 * 
	 * @return
	 */
	public String getUserAllActorId()
	{
		UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
		if (null == userContext)
			return null;
		List<SysRole> list = userContext.getGrantedRoles();
		StringBuffer sb = new StringBuffer();
		for (Iterator iterator = list.iterator(); iterator.hasNext();)
		{
			SysRole sysRole = (SysRole) iterator.next();
			sb.append("'");
			sb.append(sysRole.getRoleId());
			sb.append("'");
			sb.append(",");
		}
		sb.append("'");
		sb.append(userContext.getSysUser().getUserId());
		sb.append("'");
		return sb.toString();
	}

	public String getUserProcessSql()
	{
		UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
		if (null == userContext)
			return null;

		final StringBuffer sb = new StringBuffer();
		String strSql = "select f.role_id from t_sys_user_role f where f.dept_user_id=?";
		getJdbcTemplate().query(strSql, new Object[] { userContext.getSysUser().getDeptUserId() }, new RowCallbackHandler()
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
		sb.append(userContext.getSysUser().getUserId());
		sb.append("'");

		return " (ACTOR_ID in (" + sb.toString() + ") and Department_Id in (" + userContext.getGrantedDepartmentsId() + "))";
	}

	public String getUserAuthProcessSql()
	{
		UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
		if (null == userContext)
			return null;

		final StringBuffer sb = new StringBuffer();
		final List<String> list = new ArrayList<String>();
		String strSql = "select t.from_user_id,t.to_id from t_sys_user_auth t where t.to_user_id=? and is_effect=1";
		getJdbcTemplate().query(strSql, new Object[] { userContext.getSysUser().getDeptUserId() }, new RowCallbackHandler()
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
}
