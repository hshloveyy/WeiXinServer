/*
 * @(#)TSysResourceJdbcDao.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：傅渊源
 *  时　间：2008-11-20
 *  描　述：创建
 */

package com.infolion.platform.console.menu.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;
import com.infolion.platform.core.dao.BaseDao;
import com.infolion.platform.console.menu.domain.SysResource;
import com.infolion.platform.util.DateUtils;

@Repository
public class SysResourceJdbcDao extends BaseDao {
	
	public List<SysResource> getResourceByParentId(String in_strParentId){
		
		final List<SysResource> resourceList = new ArrayList();
		
		String strSql = "select a.*, " +
						"(select z.type_name from bm_sys_res_type z where z.type_id = a.type_id) typename, " +
				        "(select b.resource_title from t_sys_resource b where b.resource_id = a.parent_id and b.deleted = '1') parenttitle, " +
				        "(select count(b.resource_id) from t_sys_resource b where b.parent_id = a.resource_id and b.deleted = '1') childcount " +
				  "from t_sys_resource a " +
				 "where a.parent_id = ? and a.deleted = '1' order by a.display_no";

		getJdbcTemplate().query(strSql, new Object[]{in_strParentId},new RowCallbackHandler(){
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
				tSysResource.setDisplayno(rs.getString("display_no"));
				tSysResource.setParenttitle(rs.getString("parenttitle"));
				tSysResource.setTypename(rs.getString("typename"));
				tSysResource.setChildcount(rs.getInt("childcount"));
				resourceList.add(tSysResource);
			}});
		return resourceList;
	}
	
	public SysResource getResourceById(String in_strId){
		final SysResource tSysResource = new SysResource();
		
		String strSql = "select a.*, " +
				"(select b.resource_title from t_sys_resource b where b.resource_id = a.parent_id and b.deleted = '1') parenttitle " +
				"from t_sys_resource a " +
				"where a.resource_id = ? and a.deleted = '1'";
		
		getJdbcTemplate().query(strSql, new Object[]{in_strId},new RowCallbackHandler(){
			public void processRow(ResultSet rs) throws SQLException{
				tSysResource.setResourceid(rs.getString("resource_id"));
				tSysResource.setTypeid(rs.getString("type_id"));
				tSysResource.setResourcename(rs.getString("resource_name"));
				tSysResource.setResourcetitle(rs.getString("resource_title"));
				tSysResource.setResourceurl(rs.getString("resource_url"));
				tSysResource.setResourceicon(rs.getString("resource_icon"));
				tSysResource.setParentid(rs.getString("parent_id"));
				tSysResource.setDisplayno(rs.getString("display_no"));
				tSysResource.setCmd(rs.getString("cmd"));
				tSysResource.setParenttitle(rs.getString("parenttitle"));
			}});
		
		return tSysResource;
	}
	
	public void addResource(SysResource tSysResource){
		String strSql ="insert into t_sys_resource " +
					  "(resource_id, " +
					   "type_id, " +
					   "resource_name, " +
					   "resource_title, " +
					   "resource_url, " +
					   "resource_icon, " +
					   "parent_id, " +
					   "cmd," +
					   "creator," +
					   "create_time," +
					   "display_no) " +
					"values(?,?,?,?,?,?,?,?,?,?,?)";
		Object[] params = new Object[]{tSysResource.getResourceid(),
									   tSysResource.getTypeid(),
									   tSysResource.getResourcename(),
									   tSysResource.getResourcetitle(),
									   tSysResource.getResourceurl(),
									   tSysResource.getResourceicon(),
									   tSysResource.getParentid(),
									   tSysResource.getCmd(),
									   tSysResource.getCreator(),
									   DateUtils.getCurrTime(DateUtils.DB_STORE_DATE),
									   tSysResource.getDisplayno()};
		getJdbcTemplate().update(strSql,params);
		
		//如果增加资源就自动给超级管理员角色增加记录
		String strRoleSql = "insert into t_sys_role_resource " +
				"(role_rec_id,resource_id,role_id,creator,create_time) " +
				"values " +
				"(?,?,'11',null,?)";
		
		Object[] roleParams = new Object[]{
				tSysResource.getResourceid(),
				tSysResource.getResourceid(),
				DateUtils.getCurrTime(DateUtils.DB_STORE_DATE)};
		
		getJdbcTemplate().update(strRoleSql,roleParams);
	}
	
	public void updateResource(SysResource tSysResource){
		String strSql = "update t_sys_resource " +
					   "set type_id        = ?, " +
					       "resource_name  = ?, " +
					       "resource_title = ?, " +
					       "resource_url   = ?, " +
					       "resource_icon  = ?, " +
					       "cmd            = ?, " +
					       "display_no = ? " +
					 "where resource_id = ?";
		Object[] params = new Object[]{tSysResource.getTypeid(),
				   				tSysResource.getResourcename(),
				   				tSysResource.getResourcetitle(),
								tSysResource.getResourceurl(),
								tSysResource.getResourceicon(),
								tSysResource.getCmd(),
								tSysResource.getDisplayno(),
								tSysResource.getResourceid()};
		getJdbcTemplate().update(strSql,params);
	}
	
	public void deleteResource(String in_strId){
		String strSql = "update t_sys_resource set deleted = '0' where resource_id = ?";
		
		Object[] params = new Object[]{in_strId};
		
		getJdbcTemplate().update(strSql,params);
	}
}
