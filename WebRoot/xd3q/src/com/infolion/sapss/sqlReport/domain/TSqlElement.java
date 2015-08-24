package com.infolion.sapss.sqlReport.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.infolion.platform.core.annotation.ValidateRule;
import com.infolion.platform.core.domain.BaseObject;
import com.infolion.platform.core.validation.DataType;
/**
 * SQL语句主信息，定义SQL语句
 * @author Administrator
 *
 */
@Entity
@Table(name = "t_sql_element")
public class TSqlElement extends BaseObject{
	
	private String sqlElementId ;
	@ValidateRule(dataType = DataType.STRING, label = "名称",required = true)
	private String name;

	private String sql;
	@ValidateRule(dataType = DataType.INT, label = "是否部门权限控制",required = true)
	private String isDeptAuthority;//是否部门权限控制
	private String cmd;
	
	private String creator;//创建者
	private String deptId;//创建部门
	private String createrTime;//创建时间
	private String isAvailable;//
	
	@Id
	@Column(name = "sqlElementId", unique = true, length = 36)
	public String getSqlElementId() {
		return sqlElementId;
	}
	public void setSqlElementId(String sqlElementId) {
		this.sqlElementId = sqlElementId;
	}
	@Column(name = "name", length = 88)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Column(name = "sql", length = 4000)
	public String getSql() {
		return sql;
	}
	public void setSql(String sql) {
		this.sql = sql;
	}
	@Column(name = "isDeptAuthority", length = 200)
	public String getIsDeptAuthority() {
		return isDeptAuthority;
	}
	public void setIsDeptAuthority(String isDeptAuthority) {
		this.isDeptAuthority = isDeptAuthority;
	}
	@Column(name = "cmd", length = 500)
	public String getCmd() {
		return cmd;
	}
	public void setCmd(String cmd) {
		this.cmd = cmd;
	}
	@Column(name = "creator", length = 36)
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	@Column(name = "deptId", length = 36)
	public String getDeptId() {
		return deptId;
	}
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	@Column(name = "createrTime", length = 20)
	public String getCreaterTime() {
		return createrTime;
	}
	public void setCreaterTime(String createrTime) {
		this.createrTime = createrTime;
	}
	@Column(name = "isAvailable", length = 2)
	public String getIsAvailable() {
		return isAvailable;
	}
	public void setIsAvailable(String isAvailable) {
		this.isAvailable = isAvailable;
	}

}
