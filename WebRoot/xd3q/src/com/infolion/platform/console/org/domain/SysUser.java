/*
 * @(#)SysUser.java
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

public class SysUser extends BaseObject {
	private String userId;
	private String deptId;
	private String deptName;
	@ValidateRule(dataType = DataType.STRING, label = "用户名", maxLength = 100,required = true)
	private String userName;
	@ValidateRule(dataType = DataType.CHINESS_CHAR, label = "姓名", maxLength = 20,required = true)
	private String realName;
	private String sex;
	@ValidateRule(dataType = DataType.STRING, label = "地址", maxLength = 200)
	private String address;
	@ValidateRule(dataType = DataType.NUMBER, label = "身份证号", maxLength = 18)
	private String idCard;
	@ValidateRule(dataType = DataType.STRING, label = "密码", maxLength = 100)
	private String password;
	@ValidateRule(dataType = DataType.STRING, label = "邮箱", maxLength = 100)
	private String EMail;
	@ValidateRule(dataType = DataType.NUMBER, label = "手机号码", maxLength = 11)
	private String mobile;
	private String positionDes;
	private String cmd;
	private String isFuncDept;
	private String creator;
	private String createTime;
	private String deleted;
	private String deptUserId;
	private char isInitUser;
	private String employeeNo;
	public SysUser() {
		super();
	}
	

	public String getPositionDes()
	{
		return positionDes;
	}


	public void setPositionDes(String positionDes)
	{
		this.positionDes = positionDes;
	}


	public String getCmd()
	{
		return cmd;
	}


	public void setCmd(String cmd)
	{
		this.cmd = cmd;
	}


	public char getIsInitUser() {
		return isInitUser;
	}
	public void setIsInitUser(char isInitUser) {
		this.isInitUser = isInitUser;
	}
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


	public String getEMail() {
		return EMail;
	}


	public void setEMail(String mail) {
		EMail = mail;
	}


	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getDeleted() {
		return deleted;
	}

	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getDeptUserId() {
		return deptUserId;
	}

	public void setDeptUserId(String deptUserId) {
		this.deptUserId = deptUserId;
	}


	public String getEmployeeNo() {
		return employeeNo;
	}


	public void setEmployeeNo(String employeeNo) {
		this.employeeNo = employeeNo;
	}
	
	public String getMobile() {
		return mobile;
	}
	
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}


	public void setIsFuncDept(String isFuncDept) {
		this.isFuncDept = isFuncDept;
	}


	public String getIsFuncDept() {
		return isFuncDept;
	}
}
