/*
 * @(#)ViewToTableJob.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：陈喜平
 *  时　间：2009-10-17
 *  描　述：创建
 */

package com.infolion.platform.console.quartzJob;

import org.springframework.jdbc.core.JdbcTemplate;
import com.infolion.platform.util.DateUtils;

public class ViewToTableJob {
	private JdbcTemplate jdbcTemplate;

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	/**
	 * 执行任务入口
	 */
	public void execute(){
		this.doit(viewNames);
	}
	/**
	 * 删除表
	 * @param tableName
	 */
	private boolean deleteTable(String tableName,String viewName){
		String sql="select COUNT(*) from user_tables t where t.table_name='"+tableName.toUpperCase()+"'";
		int count = this.jdbcTemplate.queryForInt(sql);
		if(count>0){//表若存在,删除数据
			this.deleteTable(tableName);
			return false;
		}else{//不存在表,则创建
			this.createTable(tableName, viewName);
			return true;
		}
	}
	/**
	 * 删除表数据
	 * @param tableName
	 */
	private void deleteTable(String tableName){
		String sql = "delete "+tableName;
		this.jdbcTemplate.execute(sql);
	}
	/**
	 * 以view为数据源创建表
	 * @param tableName
	 * @param viewName
	 */
	private void createTable(String tableName,String viewName){
		String sql="create table "+tableName+" as select * from "+viewName ;
		this.jdbcTemplate.execute(sql);
	}
	/**
	 * 插入数据
	 * @param tableName
	 * @param viewName
	 */
	private void insertTable(String tableName,String viewName){
		String sql="insert into  "+tableName+" select * from "+viewName;
		try {
			this.jdbcTemplate.execute(sql);
		} catch (Exception e) {//可能表结构已变动
			this.dropTable(tableName);
			this.createTable(tableName, viewName);
		}
		System.out.println(DateUtils.getCurrDate(1)+" "+sql+" ok!");
	}
	/**
	 * 删除表
	 * @param tableName
	 */
	private void dropTable(String tableName){
		String sql = "drop table "+tableName;
		this.jdbcTemplate.execute(sql);
	}
	/**
	 * 
	 * @param viewNames
	 */
	private void doit(String[] viewNames){
		boolean isCreate= false;
		for(String view:viewNames){
			isCreate = this.deleteTable("t_"+view,view);
			if(!isCreate)
				this.insertTable("t_"+view, view);
		}
	}
	
	
	/**
	 * 视图名
	 */
	private static String[] viewNames=new String[]{"v_home_sale"
												   ,"v_home_purchase"
												   ,"v_export_sale"
												   ,"v_export_purchase"
												   ,"v_import_sale"
												   ,"v_import_purchase"
												   //,"v_export_lc_view"
												  };
}
/*
t_v_home_sale
t_v_home_purchase
t_v_export_sale
t_v_export_purchase
t_v_import_sale
t_v_import_purchase
t_v_export_lc_view
*/