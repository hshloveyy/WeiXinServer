package com.infolion.sapss.investment.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TInvestmentProjectInfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_INVESTMENT_PROJECT_INFO")
public class TInvestmentProjectInfo implements java.io.Serializable {

	// Fields

	private String pid;
	private String ipId;
	private String investmentCode;
	private String projectTime;
	private String count;

	// Constructors

	/** default constructor */
	public TInvestmentProjectInfo() {
	}

	/** minimal constructor */
	public TInvestmentProjectInfo(String pid) {
		this.pid = pid;
	}

	/** full constructor */
	public TInvestmentProjectInfo(String pid, String ipId,
			String investmentCode, String projectTime, String count) {
		this.pid = pid;
		this.ipId = ipId;
		this.investmentCode = investmentCode;
		this.projectTime = projectTime;
		this.count = count;
	}

	// Property accessors
	@Id
	@Column(name = "PID", unique = true, nullable = false, length = 36)
	public String getPid() {
		return this.pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	@Column(name = "IP_ID", length = 36)
	public String getIpId() {
		return this.ipId;
	}

	public void setIpId(String ipId) {
		this.ipId = ipId;
	}

	@Column(name = "INVESTMENT_CODE", length = 20)
	public String getInvestmentCode() {
		return this.investmentCode;
	}

	public void setInvestmentCode(String investmentCode) {
		this.investmentCode = investmentCode;
	}

	@Column(name = "PROJECT_TIME", length = 20)
	public String getProjectTime() {
		return this.projectTime;
	}

	public void setProjectTime(String projectTime) {
		this.projectTime = projectTime;
	}

	@Column(name = "COUNT", length = 20)
	public String getCount() {
		return this.count;
	}

	public void setCount(String count) {
		this.count = count;
	}

}