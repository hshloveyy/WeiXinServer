/*
 * @(#)SysUserAuthJdbcDao.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：傅渊源
 *  时　间：2008-12-16
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
import com.infolion.platform.console.org.domain.SysDept;
import com.infolion.platform.console.org.domain.SysRole;
import com.infolion.platform.console.org.domain.SysUserAuth;
import com.infolion.platform.console.sys.context.UserContext;
import com.infolion.platform.console.sys.context.UserContextHolder;
import com.infolion.platform.core.dao.BaseDao;
import com.infolion.platform.sys.cache.SysCachePoolUtils;
import com.infolion.platform.util.CodeGenerator;
import com.infolion.platform.util.DateUtils;
@Repository
public class SysUserAuthJdbcDao extends BaseDao {
	public List<SysUserAuth> queryAuthByCondition(SysUserAuth in_sysUserAuth){
		final List<SysUserAuth> sysUserAuthList = new ArrayList();
		
		String strSql ="select a.*, " +
						"b.user_name from_user_name, " +
						"d.user_name to_user_name, " +
						"f.resource_title to_name " +
						"from t_sys_user_auth a, " +
						"t_sys_user b, " +
						"t_sys_dept_user c, " +
						"t_sys_user d, " +
						"t_sys_dept_user e, " +
						"t_sys_resource f " +
						"where a.from_user_id = c.dept_user_id " +
						"and c.user_id = b.user_id " +
						"and a.to_user_id = e.dept_user_id " +
						"and e.user_id =  d.user_id " +
						"and a.to_id = f.resource_id";
		
		String strRoleSql = "select a.*, " +
						"b.user_name from_user_name, " +
						"d.user_name to_user_name, " +
						"f.role_name to_name " +
						"from t_sys_user_auth a, " +
						"t_sys_user b, " +
						"t_sys_dept_user c, " +
						"t_sys_user d, " +
						"t_sys_dept_user e, " +
						"t_sys_role f " +
						"where a.from_user_id = c.dept_user_id " +
						"and c.user_id = b.user_id " +
						"and a.to_user_id = e.dept_user_id " +
						"and e.user_id =  d.user_id " +
						"and a.to_id = f.role_id";
		
		if (in_sysUserAuth.getFromUserId() != null && !"".equals(in_sysUserAuth.getFromUserId())){
			strSql = strSql + " and a.from_user_id = '" + in_sysUserAuth.getFromUserId() + "'";
			strRoleSql = strRoleSql + " and a.from_user_id = '" + in_sysUserAuth.getFromUserId() + "'";
		}
		
		if (in_sysUserAuth.getToUserId() != null && !"".equals(in_sysUserAuth.getToUserId())){
			strSql = strSql + " and a.to_user_id = '" + in_sysUserAuth.getToUserId() + "'";
			strRoleSql = strRoleSql + " and a.to_user_id = '" + in_sysUserAuth.getToUserId() + "'";
		}
		
		if (in_sysUserAuth.getIsEffect() != null && !"".equals(in_sysUserAuth.getIsEffect())){
			strSql = strSql + " and a.is_effect = '" + in_sysUserAuth.getIsEffect() + "'";
			strRoleSql = strRoleSql + " and a.is_effect = '" + in_sysUserAuth.getIsEffect() + "'";
		}
		
		if (in_sysUserAuth.getAuthTime() != null && !"".equals(in_sysUserAuth.getAuthTime())){
			strSql = strSql + " and a.auth_time between '" + in_sysUserAuth.getAuthTime() + "' and '" + in_sysUserAuth.getUnAuthTime() + "'";
			strRoleSql = strRoleSql + " and a.auth_time between '" + in_sysUserAuth.getAuthTime() + "' and '" + in_sysUserAuth.getUnAuthTime() + "'";
		}
		
		
		if (in_sysUserAuth.getLastTime() != null && !"".equals(in_sysUserAuth.getLastTime())){
			strSql = strSql + " and a.un_auth_time between '" + in_sysUserAuth.getLastTime() + "' and '" + in_sysUserAuth.getCreateTime() + "'";
			strRoleSql = strRoleSql + " and a.un_auth_time between '" + in_sysUserAuth.getLastTime() + "' and '" + in_sysUserAuth.getCreateTime() + "'";
		}

		getJdbcTemplate().query(strSql, new Object[]{},new RowCallbackHandler(){
			public void processRow(ResultSet rs) throws SQLException{
				SysUserAuth sysUserAuth = new SysUserAuth();
				
				sysUserAuth.setAuthId(rs.getString("auth_id"));
				sysUserAuth.setFromUserId(rs.getString("from_user_id"));
				sysUserAuth.setFromUserName(rs.getString("from_user_name"));
				sysUserAuth.setToUserId(rs.getString("to_user_id"));
				sysUserAuth.setToUserName(rs.getString("to_user_name"));
				sysUserAuth.setToId(rs.getString("to_id"));
				sysUserAuth.setToName(rs.getString("to_name"));
				sysUserAuth.setTypeId(rs.getString("type_id"));
				sysUserAuth.setTypeName(SysCachePoolUtils.getDictDataValue("BM_SYS_AUTH_TYPE", rs.getString("type_id")));
				sysUserAuth.setAuthTime(rs.getString("auth_time"));
				sysUserAuth.setUnAuthTime(rs.getString("un_auth_time"));
				sysUserAuth.setLastTime(rs.getString("last_time"));
				sysUserAuth.setCreator(rs.getString("creator"));
				sysUserAuth.setCreateTime(rs.getString("create_time"));
				if (rs.getString("is_effect").equals("1"))
					sysUserAuth.setIsEffect("有");
				else
					sysUserAuth.setIsEffect("否");
				sysUserAuthList.add(sysUserAuth);
			}});
		
		getJdbcTemplate().query(strRoleSql, new Object[]{},new RowCallbackHandler(){
			public void processRow(ResultSet rs) throws SQLException{
				SysUserAuth sysUserAuth = new SysUserAuth();
				
				sysUserAuth.setAuthId(rs.getString("auth_id"));
				sysUserAuth.setFromUserId(rs.getString("from_user_id"));
				sysUserAuth.setFromUserName(rs.getString("from_user_name"));
				sysUserAuth.setToUserId(rs.getString("to_user_id"));
				sysUserAuth.setToUserName(rs.getString("to_user_name"));
				sysUserAuth.setToId(rs.getString("to_id"));
				sysUserAuth.setToName(rs.getString("to_name"));
				sysUserAuth.setTypeId(rs.getString("type_id"));
				sysUserAuth.setTypeName(SysCachePoolUtils.getDictDataValue("BM_SYS_AUTH_TYPE", rs.getString("type_id")));
				sysUserAuth.setAuthTime(rs.getString("auth_time"));
				sysUserAuth.setUnAuthTime(rs.getString("un_auth_time"));
				sysUserAuth.setLastTime(rs.getString("last_time"));
				sysUserAuth.setCreator(rs.getString("creator"));
				sysUserAuth.setCreateTime(rs.getString("create_time"));
				if (rs.getString("is_effect").equals("1"))
					sysUserAuth.setIsEffect("有");
				else
					sysUserAuth.setIsEffect("否");
				sysUserAuthList.add(sysUserAuth);
			}});
		
		return sysUserAuthList;
	}
	
	/**
	 * 增加员工授权信息
	 * @param in_sysUserAuth
	 */
	public void addUserAuth(SysUserAuth in_sysUserAuth){
		String strSql ="insert into t_sys_user_auth( " +
						"auth_id, " +
						"from_user_id, " +
						"to_user_id, " +
						"to_id, " +
						"type_id, " +
						"auth_time, " +
						"un_auth_time, " +
						"last_time, " +
						"is_effect, " +
						"creator, " +
						"create_time " +
						") values " +
						"(?,?,?,?,?,?,?,?,'1',?,?)";
		
		String[] toUserIdList = in_sysUserAuth.getToUserId().split("\\|");
		String[] toIdList = in_sysUserAuth.getToId().split("\\|");
		UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
		for (int i=0;i<toUserIdList.length;i++){
			for (int j=0;j<toIdList.length;j++){
				Object[] params = new Object[]{
						CodeGenerator.getUUID(),
						userContext.getSysUser().getDeptUserId(),
						toUserIdList[i],
						toIdList[j],
						in_sysUserAuth.getTypeId(),
						DateUtils.getCurrDateStr(DateUtils.DB_STORE_DATE),
						"",
						in_sysUserAuth.getLastTime(),
						userContext.getSysUser().getDeptUserId(),
						DateUtils.getCurrTime(DateUtils.DB_STORE_DATE)
				};
				
				getJdbcTemplate().update(strSql,params);
			}
		}
	}
	
	/**
	 * 查询员工的角色给权限转移使用
	 * @param in_strDeptUserId
	 * @return
	 */
	public List<SysRole> queryRoleForTransferManage(String in_strDeptUserId){
		final List<SysRole> sysRoleList = new ArrayList();
		String strSql ="select distinct b.role_id,b.role_name from " +
						"t_sys_user_role a, " +
						"t_sys_role b " +
						"where a.dept_user_id = ? " +
						"and a.role_id = b.role_id " +
						"and b.deleted = '1'";
		
		getJdbcTemplate().query(strSql, new Object[]{in_strDeptUserId},new RowCallbackHandler(){
			public void processRow(ResultSet rs) throws SQLException{
				SysRole sysRole = new SysRole();
				
				sysRole.setRoleId(rs.getString("role_id"));
				sysRole.setRoleName(rs.getString("role_name"));
				sysRoleList.add(sysRole);
			}});
		
		return sysRoleList;
	}
	
	/**
	 * 查询用户的资源给权限转移使用
	 * @param in_strDeptUserId
	 * @param in_strParentId
	 * @return
	 */
	public List<SysResource> queryResourceForTransferManage(String in_strDeptUserId){
		final List<SysResource> sysResourceList = new ArrayList();
		String strSql ="";

		strSql = "select distinct c.resource_id,c.resource_title " +
				"from " +
				"t_sys_user_role a, " +
				"t_sys_role_resource b, " +
				"t_sys_resource c " +
				"where a.dept_user_id = ? " +
				"and a.role_id = b.role_id " +
				"and b.resource_id = c.resource_id " +
				"and c.deleted = '1' " +
				"and c.resource_url is not null ";
		
		getJdbcTemplate().query(strSql, new Object[]{in_strDeptUserId},new RowCallbackHandler(){
			public void processRow(ResultSet rs) throws SQLException{
				SysResource sysResource = new SysResource();
				sysResource.setResourceid(rs.getString("resource_id"));
				sysResource.setResourcetitle(rs.getString("resource_title"));
				sysResourceList.add(sysResource);
			}});

		return sysResourceList;
	}
	
	/**
	 * 回收转移权限信息
	 * @param in_strAuthIdList
	 */
	public void deleteUserAuth(String in_strAuthIdList){
		String strSql = "update t_sys_user_auth set un_auth_time = ? , is_effect = '0' where auth_id = ?";
		
		String[] toAuthIdList = in_strAuthIdList.split("\\|");
		for (int i=0;i<toAuthIdList.length;i++){
			Object[] params = new Object[]{
					DateUtils.getCurrTime(DateUtils.DB_STORE_DATE),
					toAuthIdList[i]
			};
			
			getJdbcTemplate().update(strSql,params);
		}
	}
}
