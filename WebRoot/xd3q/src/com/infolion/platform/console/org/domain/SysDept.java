/*
 * @(#)TSysDept.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：傅渊源
 *  时　间：2008-12-1
 *  描　述：创建
 */

package com.infolion.platform.console.org.domain;

import com.infolion.platform.core.annotation.ValidateRule;
import com.infolion.platform.core.domain.BaseObject;
import com.infolion.platform.core.validation.DataType;

public class SysDept extends BaseObject {
	private String deptid;
	@ValidateRule(dataType = DataType.STRING, label = "部门名称", maxLength = 100,required = true)
	private String deptname;
	@ValidateRule(dataType = DataType.STRING, label = "部门编码", maxLength = 50,required = true)
	private String deptcode;
	private String isFuncDept;
	private String deptShortCode;
	private String pdeptid;
	private String pdeptname;
	private String ywbmcode;
	private String lrzxcode;
	private String cbzxcode;
	private String xszzcode;
	private String cgzzcode;
	private String yszzcode;
	private String creator;
	private String createtime;
	private String deleted;
	private int childcount;
	private String companyCode;

	public SysDept(String deptid, String deptname, String deptcode,
			String pdeptid, String ywbmcode, String lrzxcode, String cbzxcode,
			String xszzcode, String cgzzcode, String yszzcode, String creator,
			String createtime, String deleted,
			String isFuncDept,String deptShortCode,String companyCode) {
		super();
		this.deptid = deptid;
		this.deptname = deptname;
		this.deptcode = deptcode;
		this.pdeptid = pdeptid;
		this.ywbmcode = ywbmcode;
		this.lrzxcode = lrzxcode;
		this.cbzxcode = cbzxcode;
		this.xszzcode = xszzcode;
		this.cgzzcode = cgzzcode;
		this.yszzcode = yszzcode;
		this.creator = creator;
		this.createtime = createtime;
		this.deleted = deleted;
		this.isFuncDept = isFuncDept;
		this.deptShortCode = deptShortCode;
		this.companyCode = companyCode;
	}

	public SysDept() {
		super();
	}


	public String getDeptid() {
		return deptid;
	}
	public void setDeptid(String deptid) {
		this.deptid = deptid;
	}
	public String getDeptname() {
		return deptname;
	}
	public void setDeptname(String deptname) {
		this.deptname = deptname;
	}
	public String getDeptcode() {
		return deptcode;
	}
	public void setDeptcode(String deptcode) {
		this.deptcode = deptcode;
	}
	public String getPdeptid() {
		return pdeptid;
	}
	public void setPdeptid(String pdeptid) {
		this.pdeptid = pdeptid;
	}
	public String getYwbmcode() {
		return ywbmcode;
	}
	public void setYwbmcode(String ywbmcode) {
		this.ywbmcode = ywbmcode;
	}
	public String getLrzxcode() {
		return lrzxcode;
	}
	public void setLrzxcode(String lrzxcode) {
		this.lrzxcode = lrzxcode;
	}
	public String getCbzxcode() {
		return cbzxcode;
	}
	public void setCbzxcode(String cbzxcode) {
		this.cbzxcode = cbzxcode;
	}
	public String getXszzcode() {
		return xszzcode;
	}
	public void setXszzcode(String xszzcode) {
		this.xszzcode = xszzcode;
	}
	public String getCgzzcode() {
		return cgzzcode;
	}
	public void setCgzzcode(String cgzzcode) {
		this.cgzzcode = cgzzcode;
	}
	public String getYszzcode() {
		return yszzcode;
	}
	public void setYszzcode(String yszzcode) {
		this.yszzcode = yszzcode;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public String getCreatetime() {
		return createtime;
	}
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	public String getDeleted() {
		return deleted;
	}
	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}

	public String getPdeptname() {
		return pdeptname;
	}

	public void setPdeptname(String pdeptname) {
		this.pdeptname = pdeptname;
	}

	public int getChildcount() {
		return childcount;
	}

	public void setChildcount(int childcount) {
		this.childcount = childcount;
	}

	public String getIsFuncDept() {
		return isFuncDept;
	}

	public void setIsFuncDept(String isFuncDept) {
		this.isFuncDept = isFuncDept;
	}

	public String getDeptShortCode() {
		return deptShortCode;
	}

	public void setDeptShortCode(String deptShortCode) {
		this.deptShortCode = deptShortCode;
	}
	
	public String getCompanyCode()
	{
		return companyCode;
	}

	public void setCompanyCode(String companyCode)
	{
		this.companyCode = companyCode;
	}
}
