/*
 * @(#)SysRoleJdbcDao.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：傅渊源
 *  时　间：2008-12-5
 *  描　述：创建
 */

package com.infolion.platform.console.org.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import com.infolion.platform.console.menu.domain.SysResource;
import com.infolion.platform.console.org.domain.SysRole;
import com.infolion.platform.console.org.domain.SysUser;
import com.infolion.platform.console.sys.context.HttpSessionContextFilter;
import com.infolion.platform.console.sys.context.UserContext;
import com.infolion.platform.console.sys.context.UserContextHolder;
import com.infolion.platform.core.dao.BaseDao;
import com.infolion.platform.sys.cache.SysCachePoolUtils;
import com.infolion.platform.util.CodeGenerator;
import com.infolion.platform.util.DateUtils;
@Repository
public class SysRoleJdbcDao extends BaseDao {
	/**
	 * 通过角色父编号查询下一级角色信息
	*/
	public List<SysRole> queryRoleByParentId(String in_strParentId){
		final List<SysRole> sysRoleList = new ArrayList();
		String strSql ="select a.role_id, " +
					"a.role_type, " +
					"a.role_name, " +
					"a.role_cmd, " +
					"a.role_scope, " +
					"a.parent_role, " +
					"a.creator, " +
					"a.create_time, " +
					"(select b.role_name from t_sys_role b where b.role_id = a.parent_role and b.deleted = '1') proleName, " +
					"(select count(b.role_id) from t_sys_role b where b.parent_role = a.role_id and b.deleted = '1') childcount " +
					"from t_sys_role a " +
					"where a.deleted = '1' " +
					"and a.parent_role= ?";

		getJdbcTemplate().query(strSql, new Object[]{in_strParentId},new RowCallbackHandler(){
			public void processRow(ResultSet rs) throws SQLException{
				SysRole sysRole = new SysRole();
				sysRole.setRoleId(rs.getString("role_id"));
				sysRole.setRoleType(SysCachePoolUtils.getDictDataValue("BM_SYS_ROLE_TYPE", rs.getString("role_type")));
				sysRole.setRoleName(rs.getString("role_name"));
				sysRole.setRoleCmd(rs.getString("role_cmd"));
				sysRole.setRoleScope(SysCachePoolUtils.getDictDataValue("BM_SYS_ROLE_SCOPE", rs.getString("role_scope")));
				sysRole.setParentRole(rs.getString("parent_role"));
				sysRole.setPRoleName(rs.getString("proleName"));
				sysRole.setCreator(rs.getString("creator"));
				sysRole.setCreateTime(rs.getString("create_time"));
				sysRole.setChildcount(rs.getInt("childcount"));
				sysRoleList.add(sysRole);
			}});
		
		return sysRoleList;
	}
	
	/**
	 *通过角色编号查询角色信息 
	*/
	public SysRole queryRoleByRoleId(String in_strRoleId){
		final SysRole sysRole = new SysRole();
		String strSql ="select a.role_id, " +
			"a.role_type, " +
			"a.role_name, " +
			"a.role_cmd, " +
			"a.role_scope, " +
			"a.parent_role, " +
			"a.creator, " +
			"a.create_time, " +
			"(select b.role_name from t_sys_role b where b.role_id = a.parent_role and b.deleted = '1') proleName, " + 
			"(select count(b.role_id) from t_sys_role b where b.parent_role = a.role_id and b.deleted = '1') childcount " +
			"from t_sys_role a " +
			"where a.deleted = '1' " +
			"and a.role_id= ?";
		
		getJdbcTemplate().query(strSql, new Object[]{in_strRoleId},new RowCallbackHandler(){
			public void processRow(ResultSet rs) throws SQLException{
				sysRole.setRoleId(rs.getString("role_id"));
				sysRole.setRoleType(rs.getString("role_type"));
				sysRole.setRoleName(rs.getString("role_name"));
				sysRole.setRoleCmd(rs.getString("role_cmd"));
				sysRole.setRoleScope(rs.getString("role_scope"));
				sysRole.setParentRole(rs.getString("parent_role"));
				sysRole.setPRoleName(rs.getString("proleName"));
				sysRole.setCreator(rs.getString("creator"));
				sysRole.setCreateTime(rs.getString("create_time"));
				sysRole.setChildcount(rs.getInt("childcount"));
			}});
		
		return sysRole;
	}
	
	/**
	 * 通过角色ID查询此角色的下挂资源
	 * @param in_strRoleId
	 * @return
	 */
	public List<SysResource> queryResourceByRoleId(String in_strRoleId){
		final List<SysResource> sysResourceList = new ArrayList();
		String strSql ="select b.*, " +
					"(select z.resource_title from t_sys_resource z where z.resource_id = b.parent_id and z.deleted = '1') parenttitle " +
					"from " + 
					"t_sys_role_resource a, " +
					"t_sys_resource b, " +
					"t_sys_role c " +
					"where a.resource_id = b.resource_id " +
					"and a.role_id = c.role_id " +
					"and b.deleted = '1' " +
					"and c.deleted = '1' " +
					"and a.role_id = ?";
		
		getJdbcTemplate().query(strSql, new Object[]{in_strRoleId},new RowCallbackHandler(){
			public void processRow(ResultSet rs) throws SQLException{
				SysResource tSysResource = new SysResource();
				tSysResource.setResourceid(rs.getString("resource_id"));
				tSysResource.setTypeid(rs.getString("type_id"));
				tSysResource.setResourcename(rs.getString("resource_name"));
				tSysResource.setResourcetitle(rs.getString("resource_title"));
				tSysResource.setResourceurl(rs.getString("resource_url"));
				tSysResource.setResourceicon(rs.getString("resource_icon"));
				tSysResource.setParentid(rs.getString("parent_id"));
				tSysResource.setCmd(rs.getString("cmd"));
				tSysResource.setParenttitle(rs.getString("parenttitle"));
				tSysResource.setTypename(SysCachePoolUtils.getDictDataValue("BM_SYS_RES_TYPE", rs.getString("type_id")));
				sysResourceList.add(tSysResource);
			}});
		
		return sysResourceList;
	}
	
	/**
	 * 通过角色编号和资源父编号查询信息
	 * @param in_strRoleId
	 * @param in_strResourceParentId
	 * @return
	 */
	public List<SysResource> queryResourceForTree(String in_strRoleId,String in_strResourceParentId,String in_strUpRoleId){
		final List<SysResource> sysResourceList = new ArrayList();
		String strSql ="";
		
		if (in_strUpRoleId.equals("-1")){
			strSql = "select a.*, " +
					"(select count(z.role_rec_id) from t_sys_role_resource z where z.role_id = ? and z.resource_id = a.resource_id) ischeck, " +
					"(select count(b.resource_id) from t_sys_resource b where b.parent_id = a.resource_id and b.deleted = '1') childcount " +
					"from t_sys_resource a " +
					"where a.parent_id = ? and a.deleted = '1'";

			getJdbcTemplate().query(strSql, new Object[]{in_strRoleId,in_strResourceParentId},new RowCallbackHandler(){
				public void processRow(ResultSet rs) throws SQLException{
					SysResource tSysResource = new SysResource();
					tSysResource.setResourceid(rs.getString("resource_id"));
					tSysResource.setResourcetitle(rs.getString("resource_title"));
					tSysResource.setChildcount(rs.getInt("childcount"));
					tSysResource.setIscheck(rs.getInt("ischeck"));
					sysResourceList.add(tSysResource);
				}});
		}else{
			strSql = "select b.*, " +
					"(select count(z.role_rec_id) from t_sys_role_resource z where z.role_id = ? and z.resource_id = b.resource_id) ischeck, " +
					"(select count(z.resource_id) from t_sys_resource z where z.parent_id = b.resource_id and b.deleted = '1') childcount " +
					"from " +
					"t_sys_role_resource a, " +
					"t_sys_resource b, " +
					"t_sys_role c " +
					"where a.resource_id = b.resource_id " + 
					"and a.role_id = c.role_id " +
					"and b.deleted = '1' " +
					"and c.deleted = '1' " +
					"and a.role_id = ? " +
					"and b.parent_id = ?";

			getJdbcTemplate().query(strSql, new Object[]{in_strRoleId,in_strUpRoleId,in_strResourceParentId},new RowCallbackHandler(){
				public void processRow(ResultSet rs) throws SQLException{
					SysResource tSysResource = new SysResource();
					tSysResource.setResourceid(rs.getString("resource_id"));
					tSysResource.setResourcetitle(rs.getString("resource_title"));
					tSysResource.setChildcount(rs.getInt("childcount"));
					tSysResource.setIscheck(rs.getInt("ischeck"));
					sysResourceList.add(tSysResource);
				}});
		}
		
		return sysResourceList;
	}
	
	/**
	 * 查询登陆用户所有的角色信息形成树
	 * @param in_strDeptUserId
	 * @param in_strRoleParentId
	 * @return
	 */
	public List<SysRole> queryRoleByLoginUser(String in_strDeptUserId,String in_strRoleParentId){
		final List<SysRole> sysRoleList = new ArrayList();
		String strSql ="select b.*, " +
					"(select count(z.role_id) from t_sys_role z where z.parent_role = b.role_id and z.deleted = '1') childcount " +
					"from " +
					"t_sys_user_role a, " +
					"t_sys_role b " +
					"where a.dept_user_id = ? " +
					"and a.role_id = b.role_id " +
					"and b.deleted = '1' " +
					"and b.parent_role = ?";
		getJdbcTemplate().query(strSql, new Object[]{in_strDeptUserId,in_strRoleParentId},new RowCallbackHandler(){
			public void processRow(ResultSet rs) throws SQLException{
				SysRole sysRole = new SysRole();
				sysRole.setRoleId(rs.getString("role_id"));
				sysRole.setRoleName(rs.getString("role_name"));
				sysRole.setChildcount(rs.getInt("childcount"));
				sysRoleList.add(sysRole);
			}});
		
		return sysRoleList;
	}
	
	/**
	 * 通过部门员工编号查询员工的角色信息编号
	 * @param in_strDeptUserId
	 * @return
	 */
	public List<String> queryUserRoleByDeptUserId(String in_strDeptUserId){
		final List<String> roleList = new ArrayList();
		String strSql ="select a.role_id from t_sys_user_role a where a.dept_user_id = ?";
		
		getJdbcTemplate().query(strSql, new Object[]{in_strDeptUserId},new RowCallbackHandler(){
			public void processRow(ResultSet rs) throws SQLException{
				roleList.add(rs.getString("role_id"));
		}});
		
		return roleList;
	}
	
	
	/**
	 * 通过部门员工编号查询该员工的角色信息情况返回给前前的grid
	 * @param in_strDeptUserId
	 * @return
	 */
	
	public List<SysRole> queryRoleByDeptUserId(String in_strDeptUserId){
		final List<SysRole> sysRoleList = new ArrayList();
		String strSql ="select b.*, " +
					"(select z.role_name from t_sys_role z where z.role_id = b.parent_role and z.deleted = '1') proleName " +
					"from " +
					"t_sys_user_role a, " +
					"t_sys_role b " +
					"where a.dept_user_id = ? " +
					"and a.role_id = b.role_id " +
					"and b.deleted = '1' " ;

		getJdbcTemplate().query(strSql, new Object[]{in_strDeptUserId},new RowCallbackHandler(){
			public void processRow(ResultSet rs) throws SQLException{
				SysRole sysRole = new SysRole();
				sysRole.setRoleId(rs.getString("role_id"));
				sysRole.setRoleType(SysCachePoolUtils.getDictDataValue("BM_SYS_ROLE_TYPE", rs.getString("role_type")));
				sysRole.setRoleName(rs.getString("role_name"));
				sysRole.setRoleCmd(rs.getString("role_cmd"));
				sysRole.setRoleScope(SysCachePoolUtils.getDictDataValue("BM_SYS_ROLE_SCOPE", rs.getString("role_scope")));
				sysRole.setParentRole(rs.getString("parent_role"));
				sysRole.setPRoleName(rs.getString("proleName"));
				sysRole.setCreator(rs.getString("creator"));
				sysRole.setCreateTime(rs.getString("create_time"));
				sysRoleList.add(sysRole);
			}});
		
		return sysRoleList;
	}
	
	
	
	/**
	 * 增加角色信息
	*/
	public void addRole(SysRole sysRole){
		//HttpSessionContextFilter httpSessionContextFilter = new HttpSessionContextFilter();
		String strSql = "insert into t_sys_role( " +
							"role_id, " +
							"role_type, " +
							"role_name, " +
							"role_cmd, " +
							"role_scope, " +
							"parent_role, " +
							"creator, " +
							"create_time, " +
							"deleted " +
							")values( " +
							"?,?,?,?,?,?,?,?,'1')";
		UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
		Object[] params = new Object[]{
				CodeGenerator.getUUID(),
				sysRole.getRoleType(),
				sysRole.getRoleName(),
				sysRole.getRoleCmd(),
				sysRole.getRoleScope(),
				sysRole.getParentRole(),
				userContext.getSysUser().getUserId(),
				DateUtils.getCurrTime(DateUtils.DB_STORE_DATE)
		};
		
		getJdbcTemplate().update(strSql,params);
	}
	
	/**
	 * 修改角色信息
	*/
	public void updateRole(SysRole sysRole){
		String strSql = "update t_sys_role set role_type = ?, " +
								"role_name = ?, " +
								"role_cmd = ?, " +
								"role_scope = ? " +
								"where role_id = ?";
		Object[] params = new Object[]{
				sysRole.getRoleType(),
				sysRole.getRoleName(),
				sysRole.getRoleCmd(),
				sysRole.getRoleScope(),
				sysRole.getRoleId()
		};
		
		getJdbcTemplate().update(strSql,params);
	}
	
	/**
	 * 删除角色信息
	*/
	public void deleteRole(String in_strRoleId){
		String strSql = "update t_sys_role set deleted = '0' where role_id = ?";
			
		Object[] params = new Object[]{
				in_strRoleId
		};
		
		getJdbcTemplate().update(strSql,params);	
	}
	
	/**
	 * 给角色增加资源信息
	 * @param in_strResourceIdList
	 * @param in_strRoleId
	 */
	public void addRoleResource(String in_strResourceIdList,String in_strRoleId){
		deleteAllRoleResource(in_strRoleId);
		String strSql ="insert into t_sys_role_resource(role_rec_id, " +
						"resource_id, " +
						"role_id, " +
						"creator, " +
						"create_time) values(?,?,?,?,?)";
		UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
		String[] IdList = in_strResourceIdList.split("\\|");
		for (int i = 0; i < IdList.length; i++) {
			Object[] params = new Object[]{
				CodeGenerator.getUUID(),
				IdList[i],
				in_strRoleId,
				userContext.getSysUser().getUserId(),
				DateUtils.getCurrTime(DateUtils.DB_STORE_DATE)
			};
			
			getJdbcTemplate().update(strSql,params);
		}
	}
	
	/**
	 * 删除角色的资源信息
	 * @param in_strResourceIdList
	 * @param in_strRoleId
	 */
	public void deleteRoleResource(String in_strResourceIdList,String in_strRoleId){
		String strSql ="delete t_sys_role_resource where role_id = ? and resource_id = ?";
		
		String[] IdList = in_strResourceIdList.split("\\|");
		for (int i = 0; i < IdList.length; i++) {
			Object[] params = new Object[]{
				in_strRoleId,
				IdList[i]
			};
			
			getJdbcTemplate().update(strSql,params);
		}
	}
	
	/**
	 * 删除该角色下的所有资源
	 * @param in_strRoleId
	 */
	public void deleteAllRoleResource(String in_strRoleId){
		String strSql ="delete t_sys_role_resource where role_id = ?";
		
		Object[] params = new Object[]{in_strRoleId};
		
		getJdbcTemplate().update(strSql,params);
	}
	
	/**
	 * 增加用户的角色信息
	 * @param in_strRoleList
	 * @param in_strDeptUserId
	 */
	public void addUserRole(String in_strRoleList,String in_strDeptUserId){
		String strSql = "insert into t_sys_user_role( " +
						"user_role_id, " +
						"role_id, " +
						"dept_user_id, " +
						"creator, " +
						"create_time) " +
						"values(?,?,?,?,?)";
		
		String strSelectSql = "select count(a.user_role_id) " +
				"from t_sys_user_role a " +
				"where a.role_id = ? and a.dept_user_id = ?";
		
		UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
		String[] IdList = in_strRoleList.split("\\|");
		for (int i = 0; i < IdList.length; i++) {
			
			int recordCount = getJdbcTemplate().queryForInt(strSelectSql, new Object[]{IdList[i],in_strDeptUserId});
			
			if (recordCount <=0){
				Object[] params = new Object[]{
					CodeGenerator.getUUID(),
					IdList[i],
					in_strDeptUserId,
					userContext.getSysUser().getUserId(),
					DateUtils.getCurrTime(DateUtils.DB_STORE_DATE)
				};
				
				getJdbcTemplate().update(strSql,params);
			}
		}
	}
	
	/**
	 * 增加员工的角色信息
	 * @param in_DeptUserList
	 * @param in_RoleId
	 */
	public void addRoleUser(String in_DeptUserList,String in_RoleId){
		String strSql = "insert into t_sys_user_role( " +
				"user_role_id, " +
				"role_id, " +
				"dept_user_id, " +
				"creator, " +
				"create_time) " +
				"values(?,?,?,?,?)";
		
		String strSelectSql = "select count(a.user_role_id) " +
		"from t_sys_user_role a " +
		"where a.role_id = ? and a.dept_user_id = ?";

		UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
		String[] UserList = in_DeptUserList.split("\\|");
		for (int i = 0; i < UserList.length; i++) {
			int recordCount = getJdbcTemplate().queryForInt(strSelectSql, new Object[]{in_RoleId,UserList[i]});
			
			if (recordCount <=0){
				Object[] params = new Object[]{
					CodeGenerator.getUUID(),
					in_RoleId,
					UserList[i],
					userContext.getSysUser().getUserId(),
					DateUtils.getCurrTime(DateUtils.DB_STORE_DATE)
				};
				
				getJdbcTemplate().update(strSql,params);
			}
		}
	}
	
	/**
	 * 删除员工的角色信息
	 * @param in_strRoleList
	 * @param in_strDeptUserId
	 */
	public void deleteUserRole(String in_strRoleList,String in_strDeptUserId){
		String strSql = "delete t_sys_user_role where role_id = ? and dept_user_id = ?";

		UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
		String[] IdList = in_strRoleList.split("\\|");
		for (int i = 0; i < IdList.length; i++) {
			Object[] params = new Object[]{
				IdList[i],
				in_strDeptUserId
			};
	
			getJdbcTemplate().update(strSql,params);
		}
	}
}
