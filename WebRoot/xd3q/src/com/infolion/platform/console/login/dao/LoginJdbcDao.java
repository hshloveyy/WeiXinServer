/*
 * @(#)loginJdbcDao.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：傅渊源
 *  时　间：2008-12-5
 *  描　述：创建
 */

package com.infolion.platform.console.login.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import com.infolion.platform.console.login.domain.LoginDept;
import com.infolion.platform.console.menu.domain.SysResource;
import com.infolion.platform.console.org.domain.SysRole;
import com.infolion.platform.console.org.domain.SysUser;
import com.infolion.platform.console.sys.context.UserContext;
import com.infolion.platform.console.sys.context.UserContextHolder;
import com.infolion.platform.core.dao.BaseDao;
import com.infolion.platform.util.CodeGenerator;

@Repository
public class LoginJdbcDao extends BaseDao
{
	public List<SysUser> queryUserIsExist(String in_strUserName, String in_strPassword)
	{
		final List<SysUser> sysUserList = new ArrayList();
		String strPassWord = CodeGenerator.getMD5Digest(in_strPassword);
		String strSql = "select b.user_id, " + "b.user_name, b.password, " + "b.real_name, " + "b.sex, " + "b.address, " + "b.id_card, " + "b.e_mail, " + "b.creator, " + "b.create_time, " + "b.position_des, " + "b.cmd, " + "c.dept_id, " + "c.dept_name, " + "a.dept_user_id,  " + "b.is_init_user  " + "from t_sys_dept_user a, t_sys_user b, t_sys_dept c " + "where a.user_id = b.user_id " + "and b.deleted = '1' " + "and a.dept_id = c.dept_id " + "and c.deleted = '1' " + "and b.user_name = ? " + "and b.password = ?";
		getJdbcTemplate().query(strSql, new Object[] { in_strUserName, strPassWord }, new RowCallbackHandler()
		{
			public void processRow(ResultSet rs) throws SQLException
			{
				SysUser sysUser = new SysUser();
				sysUser.setUserId(rs.getString("user_id"));
				sysUser.setUserName(rs.getString("user_name"));
				sysUser.setPassword(rs.getString("password"));
				sysUser.setRealName(rs.getString("real_name"));
				sysUser.setSex(rs.getString("sex"));
				sysUser.setAddress(rs.getString("address"));
				sysUser.setIdCard(rs.getString("id_card"));
				sysUser.setEMail(rs.getString("e_mail"));
				sysUser.setCreator(rs.getString("creator"));
				sysUser.setCreateTime(rs.getString("create_time"));
				sysUser.setDeptId(rs.getString("dept_id"));
				sysUser.setDeptName(rs.getString("dept_name"));
				sysUser.setPositionDes(rs.getString("position_des"));
				sysUser.setCmd(rs.getString("cmd"));
				sysUser.setDeptUserId(rs.getString("dept_user_id"));
				sysUser.setIsInitUser(rs.getString("is_init_user") == null ? '0' : rs.getString("is_init_user").charAt(0));
				sysUserList.add(sysUser);
			}
		});
		return sysUserList;
	}

	public List<LoginDept> queryLoginDept(String in_strUserId)
	{
		final List<LoginDept> loginDeptList = new ArrayList();

		String strSql = "select b.dept_id,b.dept_name " + "from t_sys_dept_user a,t_sys_dept b " + "where a.dept_id = b.dept_id " + "and a.user_id = ? order by b.dept_name";

		getJdbcTemplate().query(strSql, new Object[] { in_strUserId }, new RowCallbackHandler()
		{
			public void processRow(ResultSet rs) throws SQLException
			{
				LoginDept loginDept = new LoginDept();
				loginDept.setDeptId(rs.getString("dept_id"));
				loginDept.setDeptName(rs.getString("dept_name"));
				loginDeptList.add(loginDept);
			}
		});

		return loginDeptList;
	}

	/**
	 * 查询用户已有权限给登陆用户
	 * 
	 * @param in_strDeptUserId
	 * @param in_strResourceParentId
	 * @return
	 */
	public List<SysResource> queryUserResource(String in_strDeptUserId, String in_strResourceParentId)
	{
		final List<SysResource> sysResourceList = new ArrayList();
		String strSql = "select distinct d.*," + "(select count(z.resource_id) from t_sys_resource z where z.parent_id = d.resource_id and z.deleted = '1' and z.type_id = '1') childcount " + " from " + "t_sys_user_role a, " + "t_sys_role b, " + "t_sys_role_resource c, " + "t_sys_resource d " + "where a.dept_user_id = ? " + "and a.role_id = b.role_id " + "and b.deleted = '1' " + "and b.role_id = c.role_id " + "and c.resource_id = d.resource_id " + "and d.parent_id = ? " + "and d.type_id = '1' " + "and d.deleted = '1' order by d.display_no";
		getJdbcTemplate().query(strSql, new Object[] { in_strDeptUserId, in_strResourceParentId }, new RowCallbackHandler()
		{
			public void processRow(ResultSet rs) throws SQLException
			{
				SysResource tSysResource = new SysResource();
				tSysResource.setResourceid(rs.getString("resource_id"));
				tSysResource.setResourcetitle(rs.getString("resource_title"));
				tSysResource.setResourceurl(rs.getString("resource_url"));
				tSysResource.setResourceicon(rs.getString("resource_icon"));
				tSysResource.setChildcount(rs.getInt("childcount"));
				sysResourceList.add(tSysResource);
			}
		});
		return sysResourceList;
	}

	/**
	 * 查询转移权限信息给登陆信息
	 * 
	 * @param in_strParentId
	 * @return
	 */
	public List<SysResource> queryTransMenu(String in_strParentId)
	{
		final List<SysResource> sysResourceList = new ArrayList();
		String strSql = "";
		UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
		String strDeptUserId = userContext.getSysUser().getDeptUserId();
		if (in_strParentId.equals("-1"))
		{
			strSql = "select distinct a.from_user_id, c.user_name || '转移给您的权限' user_name " + "from t_sys_user_auth a, " + "t_sys_dept_user b, " + "t_sys_user c " + "where a.to_user_id = ? " + "and a.is_effect = '1' " + "and a.from_user_id = b.dept_user_id " + "and b.user_id = c.user_id " + "and c.deleted  = '1'";

			getJdbcTemplate().query(strSql, new Object[] { strDeptUserId }, new RowCallbackHandler()
			{
				public void processRow(ResultSet rs) throws SQLException
				{
					SysResource tSysResource = new SysResource();
					tSysResource.setResourceid(rs.getString("from_user_id"));
					tSysResource.setResourcetitle(rs.getString("user_name"));
					tSysResource.setResourceurl("");
					tSysResource.setResourceicon("");
					tSysResource.setChildcount(2);
					sysResourceList.add(tSysResource);
				}
			});

		}
		else
		{
			strSql = "select b.resource_id resource_id,b.resource_title,b.resource_url " + "from t_sys_user_auth a, " + "t_sys_resource b " + "where a.to_user_id = ? " + "and a.from_user_id  = ? " + "and a.is_effect = '1' " + "and a.type_id = '2' " + "and a.to_id = b.resource_id " + "and b.deleted = '1' " + "union " + "select c.resource_id resource_id,c.resource_title,c.resource_url " + "from t_sys_user_auth a, " + "t_sys_role_resource b, " + "t_sys_resource c " + "where a.to_user_id = ? " + "and a.from_user_id  = ? " + "and a.is_effect = '1' " + "and a.type_id = '1' " + "and a.to_id = b.role_id " + "and b.resource_id = c.resource_id " + "and c.deleted  = '1' " + "and c.resource_url is not null";

			getJdbcTemplate().query(strSql, new Object[] { strDeptUserId, in_strParentId, strDeptUserId, in_strParentId }, new RowCallbackHandler()
			{
				public void processRow(ResultSet rs) throws SQLException
				{
					SysResource tSysResource = new SysResource();
					tSysResource.setResourceid(rs.getString("resource_id"));
					tSysResource.setResourcetitle(rs.getString("resource_title"));
					tSysResource.setResourceurl(rs.getString("resource_url"));
					tSysResource.setResourceicon("");
					tSysResource.setChildcount(0);
					sysResourceList.add(tSysResource);
				}
			});
		}

		return sysResourceList;
	}

	/**
	 * 查询用户自己有的资源权限和别人转移的权限的集合
	 * 
	 * @param in_strDeptUserId
	 * @return
	 */
	public List<SysResource> queryUserResourceForUserConext(String in_strDeptUserId)
	{
		final List<SysResource> sysResourceList = new ArrayList();

		String strSql = "select b.* " + "from t_sys_user_auth a, " + "t_sys_resource b " + "where a.to_user_id = ? " + "and a.is_effect = '1' " + "and a.type_id = '2' " + "and a.to_id = b.resource_id " + "and b.deleted = '1' " + "union " + "select c.* " + "from t_sys_user_auth a, " + "t_sys_role_resource b, " + "t_sys_resource c " + "where a.to_user_id = ? " + "and a.is_effect = '1' " + "and a.type_id = '1' " + "and a.to_id = b.role_id " + "and b.resource_id = c.resource_id " + "and c.deleted  = '1' " + "union " + "select c.* " + "from t_sys_user_role a, " + "t_sys_role_resource b, " + "t_sys_resource c " + "where a.dept_user_id = ? " + "and a.role_id = b.role_id " + "and b.resource_id  = c.resource_id " + "and c.deleted = '1'";

		getJdbcTemplate().query(strSql, new Object[] { in_strDeptUserId, in_strDeptUserId, in_strDeptUserId }, new RowCallbackHandler()
		{
			public void processRow(ResultSet rs) throws SQLException
			{
				SysResource tSysResource = new SysResource();
				tSysResource.setResourceid(rs.getString("resource_id"));
				tSysResource.setTypeid(rs.getString("type_id"));
				tSysResource.setResourcename(rs.getString("resource_name"));
				tSysResource.setResourcetitle(rs.getString("resource_title"));
				tSysResource.setResourceurl(rs.getString("resource_url"));
				tSysResource.setResourceicon(rs.getString("resource_icon"));
				tSysResource.setParentid(rs.getString("parent_id"));
				tSysResource.setCmd(rs.getString("cmd"));
				sysResourceList.add(tSysResource);
			}
		});

		return sysResourceList;
	}

	/**
	 * 查询用户自己有的角色权限和别人转移的角色的集合
	 * 
	 * @param in_strDeptUserId
	 * @return
	 */
	public List<SysRole> queryUserRoleForUserConext(String in_strDeptUserId)
	{
		final List<SysRole> sysRoleList = new ArrayList();

		String strSql = "select b.*, " + "(select z.role_name " + "from t_sys_role z " + "where z.role_id = b.parent_role " + "and z.deleted = '1') proleName " + "from t_sys_user_role a, t_sys_role b " + "where a.dept_user_id = ? " + "and a.role_id = b.role_id " + "and b.deleted = '1' " + "union " + "select b.*, " + "(select z.role_name " + "from t_sys_role z " + "where z.role_id = b.parent_role " + "and z.deleted = '1') proleName " + "from t_sys_user_auth a, t_sys_role b " + "where a.to_user_id = ? " + "and a.is_effect = '1' " + "and a.type_id = '1' " + "and a.to_id = b.role_id " + "and b.deleted = '1'";

		getJdbcTemplate().query(strSql, new Object[] { in_strDeptUserId, in_strDeptUserId }, new RowCallbackHandler()
		{
			public void processRow(ResultSet rs) throws SQLException
			{
				SysRole sysRole = new SysRole();
				sysRole.setRoleId(rs.getString("role_id"));
				// TODO LJX 先修改為可以启动。等待缓存整合完毕以后需要再改回来。
				// sysRole.setRoleType(SysCachePoolUtils.getDictDataValue("BM_SYS_ROLE_TYPE",
				// rs.getString("role_type")));
				sysRole.setRoleType("1");
				sysRole.setRoleName(rs.getString("role_name"));
				sysRole.setRoleCmd(rs.getString("role_cmd"));
				// sysRole.setRoleScope(SysCachePoolUtils.getDictDataValue("BM_SYS_ROLE_SCOPE",
				// rs.getString("role_scope")));
				sysRole.setRoleScope("2");
				sysRole.setParentRole(rs.getString("parent_role"));
				sysRole.setPRoleName(rs.getString("proleName"));
				sysRole.setCreator(rs.getString("creator"));
				sysRole.setCreateTime(rs.getString("create_time"));
				sysRoleList.add(sysRole);
			}
		});

		return sysRoleList;
	}

	public String queryUserManagerDepartment(String userId)
	{
		String strSql = "select t.dept_id from t_sys_user_manager_dept t where t.user_id=?";
		final StringBuffer sb = new StringBuffer();
		getJdbcTemplate().query(strSql, new Object[] { userId }, new RowCallbackHandler()
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

	public List<SysUser> queryUserExist(String in_strUserName, String in_strPassword)
	{
		final List<SysUser> sysUserList = new ArrayList();
		String strSql = "select b.user_id, " + "b.user_name, b.password, " + "b.real_name, " + "b.sex, " + "b.address, " + "b.id_card, " + "b.e_mail, " + "b.creator, " + "b.create_time, " + "b.position_des, " + "b.cmd, " + "c.dept_id, " + "c.dept_name, " + "a.dept_user_id,  " + "b.is_init_user  " + "from t_sys_dept_user a, t_sys_user b, t_sys_dept c " + "where a.user_id = b.user_id " + "and b.deleted = '1' " + "and a.dept_id = c.dept_id " + "and c.deleted = '1' " + "and b.user_name = ? " + "and b.password = ?";
		getJdbcTemplate().query(strSql, new Object[] { in_strUserName, in_strPassword }, new RowCallbackHandler()
		{
			public void processRow(ResultSet rs) throws SQLException
			{
				SysUser sysUser = new SysUser();
				sysUser.setUserId(rs.getString("user_id"));
				sysUser.setUserName(rs.getString("user_name"));
				sysUser.setPassword(rs.getString("password"));
				sysUser.setRealName(rs.getString("real_name"));
				sysUser.setSex(rs.getString("sex"));
				sysUser.setAddress(rs.getString("address"));
				sysUser.setIdCard(rs.getString("id_card"));
				sysUser.setEMail(rs.getString("e_mail"));
				sysUser.setCreator(rs.getString("creator"));
				sysUser.setCreateTime(rs.getString("create_time"));
				sysUser.setDeptId(rs.getString("dept_id"));
				sysUser.setDeptName(rs.getString("dept_name"));
				sysUser.setPositionDes(rs.getString("position_des"));
				sysUser.setCmd(rs.getString("cmd"));
				sysUser.setDeptUserId(rs.getString("dept_user_id"));
				sysUser.setIsInitUser(rs.getString("is_init_user") == null ? '0' : rs.getString("is_init_user").charAt(0));
				sysUserList.add(sysUser);
			}
		});
		return sysUserList;
	}

}
