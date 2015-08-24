/*
 * @(#)Supplier.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年05月25日 14点51分13秒
 *  描　述：创建
 */
package com.infolion.xdss3.masterData.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import com.infolion.platform.dpframework.core.annotation.ValidateRule;
import com.infolion.platform.dpframework.core.domain.BaseObject;

/**
 * <pre>
 * 部门(Dept)实体类
 * </pre>
 * 
 * <br>
 * JDK版本:1.5
 * 
 * @author java业务平台代码生成工具
 * @version 1.0
 * @see The author for more details
 * @since 1.0
 */
public class Dept extends BaseObject
{
	/*
	 * 客户端
	 */
	@Column(name = "MANDT")
	private String client;
	
	/**
	 * 部门ID
	 */
	@Id
	@Column(name = "DEPTID")
	private String deptid;

	/**
	 * 部门名称
	 */
	@Column(name = "DEPTNAME")
	private String deptName;

	/**
	 * 公司ID
	 */
	@Column(name = "COMPANYID")
	private String companyid;

	/**
	 * @return the client
	 */
	public String getClient() {
		return client;
	}

	/**
	 * @param client the client to set
	 */
	public void setClient(String client) {
		this.client = client;
	}

	/**
	 * @return the deptid
	 */
	public String getDeptid() {
		return deptid;
	}

	/**
	 * @param deptid the deptid to set
	 */
	public void setDeptid(String deptid) {
		this.deptid = deptid;
	}

	/**
	 * @return the deptName
	 */
	public String getDeptName() {
		return deptName;
	}

	/**
	 * @param deptName the deptName to set
	 */
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	/**
	 * @return the companyid
	 */
	public String getCompanyid() {
		return companyid;
	}

	/**
	 * @param companyid the companyid to set
	 */
	public void setCompanyid(String companyid) {
		this.companyid = companyid;
	}	
}
