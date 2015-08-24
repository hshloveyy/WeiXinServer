package com.infolion.sapss.sqlReport.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.infolion.platform.core.domain.BaseObject;
/**
 * SQL语句主解析字段定义，
 * @author Administrator
 *
 */
@Entity
@Table(name = "t_sql_fielddf")
public class TSqlFieldDf extends BaseObject{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String sqlFieldDfId;
	private String sqlElementId ;
	private String filedName;
	private String filedType;//字段类型 0字符、1数值、2日期、3部门、4数据字典、5搜索帮助
	private String dicName;//数据字典/搜索帮助名
	private String isShow;//是否列表显示 0不显示、1显示（默认）
	private String filedShowName;//中文字段名
	
	private String isQuery;//是否提供查询条件 0不查询、1查询
	private Integer orderNo;//排序
	private String width;//显示宽度
	

	@Id
	@Column(name = "sqlFieldDfId", unique = true, length = 36)
	public String getSqlFieldDfId() {
		return sqlFieldDfId;
	}
	public void setSqlFieldDfId(String sqlFieldDfId) {
		this.sqlFieldDfId = sqlFieldDfId;
	}
	@Column(name = "sqlElementId", length = 36)
	public String getSqlElementId() {
		return sqlElementId;
	}
	public void setSqlElementId(String sqlElementId) {
		this.sqlElementId = sqlElementId;
	}
	@Column(name = "filedName", length = 88)
	public String getFiledName() {
		return filedName;
	}
	public void setFiledName(String filedName) {
		this.filedName = filedName;
	}
	@Column(name = "filedType", length = 2)
	public String getFiledType() {
		return filedType;
	}
	public void setFiledType(String filedType) {
		this.filedType = filedType;
	}
	@Column(name = "isShow", length = 2)
	public String getIsShow() {
		return isShow;
	}
	public void setIsShow(String isShow) {
		this.isShow = isShow;
	}
	@Column(name = "isQuery", length = 2)
	public String getIsQuery() {
		return isQuery;
	}
	public void setIsQuery(String isQuery) {
		this.isQuery = isQuery;
	}
	@Column(name = "dicName", length = 88)
	public String getDicName() {
		return dicName;
	}
	public void setDicName(String dicName) {
		this.dicName = dicName;
	}
	@Column(name = "orderNo", length = 20)
	public Integer getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}
	@Column(name = "filedShowName", length = 88)
	public String getFiledShowName() {
		return filedShowName;
	}
	public void setFiledShowName(String filedShowName) {
		this.filedShowName = filedShowName;
	}
	@Column(name = "width", length = 20)
	public String getWidth() {
		return width;
	}
	public void setWidth(String width) {
		this.width = width;
	}

}
