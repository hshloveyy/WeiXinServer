/*
 * @(#)TApplyCustomerCredit.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：傅渊源
 *  时　间：2010-5-10
 *  描　述：创建
 */

package com.infolion.sapss.project.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.infolion.platform.core.annotation.ValidateRule;
import com.infolion.platform.dpframework.core.domain.BaseObject;
import com.infolion.platform.core.validation.DataType;

@Entity
@Table(name = "T_APPLY_CUSTOMER_CREDIT")
public class TApplyCustomerCredit extends BaseObject
{
	public TApplyCustomerCredit()
	{
		super();
		// TODO Auto-generated constructor stub
	}

	@Id
	@Column(name = "APPLYID", unique = true, nullable = false)
	private String applyId;

	@Column(name = "PROJECT_ID")
	private String projectId;
	
	
	@Column(name = "PREPAYVALUE")
	private Double prepayVlaue;
	
	@Column(name = "SENDVALUE")
	private Double sendValue;
	
	@Column(name = "STARTINGTIME")
	private String startingTime;
	
	@Column(name = "ENDTIME ")
	private String endTime;
	
	@Column(name = "ISCREDITCUST ")
	private String isCreditCust;
	
	@Column(name = "CREDITTYPE ")
	private String creditType;

	public String getCreditType()
	{
		return creditType;
	}

	public void setCreditType(String creditType)
	{
		this.creditType = creditType;
	}

	public String getIsCreditCust()
	{
		return isCreditCust;
	}

	public void setIsCreditCust(String isCreditCust)
	{
		this.isCreditCust = isCreditCust;
	}

	public String getApplyId()
	{
		return applyId;
	}

	public void setApplyId(String applyId)
	{
		this.applyId = applyId;
	}

	public String getProjectId()
	{
		return projectId;
	}

	public void setProjectId(String projectId)
	{
		this.projectId = projectId;
	}

	public Double getPrepayVlaue()
	{
		return prepayVlaue;
	}

	public void setPrepayVlaue(Double prepayVlaue)
	{
		this.prepayVlaue = prepayVlaue;
	}

	public Double getSendValue()
	{
		return sendValue;
	}

	public void setSendValue(Double sendValue)
	{
		this.sendValue = sendValue;
	}

	public String getStartingTime()
	{
		return startingTime;
	}

	public void setStartingTime(String startingTime)
	{
		this.startingTime = startingTime;
	}

	public String getEndTime()
	{
		return endTime;
	}

	public void setEndTime(String endTime)
	{
		this.endTime = endTime;
	}
}
