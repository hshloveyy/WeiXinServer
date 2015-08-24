/*
 * @(#)TApplyProviderCredit.java
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
@Table(name = "T_APPLY_PROVIDER_CREDIT")
public class TApplyProviderCredit extends BaseObject
{
	public TApplyProviderCredit()
	{
		super();
		// TODO Auto-generated constructor stub
	}

	@Id
	@Column(name = "APPLYID", unique = true, nullable = false)
	private String providerApplyId;

	@Column(name = "PROJECT_ID")
	private String projectId;
	
	@Column(name = "TOTALVALUE")
	private Double providerTotalValue;
	
	@Column(name = "STARTINGTIME")
	private String providerStartingTime;
	
	@Column(name = "ENDTIME ")
	private String providerEndTime;
	
	
	@Column(name = "ISCREDITPROVIDER")
	private String isCreditProvider;
	
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
	

	public String getIsCreditProvider()
	{
		return isCreditProvider;
	}

	public void setIsCreditProvider(String isCreditProvider)
	{
		this.isCreditProvider = isCreditProvider;
	}

	public String getProjectId()
	{
		return projectId;
	}

	public void setProjectId(String projectId)
	{
		this.projectId = projectId;
	}

	public String getProviderApplyId()
	{
		return providerApplyId;
	}

	public void setProviderApplyId(String providerApplyId)
	{
		this.providerApplyId = providerApplyId;
	}

	public Double getProviderTotalValue()
	{
		return providerTotalValue;
	}

	public void setProviderTotalValue(Double providerTotalValue)
	{
		this.providerTotalValue = providerTotalValue;
	}

	public String getProviderStartingTime()
	{
		return providerStartingTime;
	}

	public void setProviderStartingTime(String providerStartingTime)
	{
		this.providerStartingTime = providerStartingTime;
	}

	public String getProviderEndTime()
	{
		return providerEndTime;
	}

	public void setProviderEndTime(String providerEndTime)
	{
		this.providerEndTime = providerEndTime;
	}
}
