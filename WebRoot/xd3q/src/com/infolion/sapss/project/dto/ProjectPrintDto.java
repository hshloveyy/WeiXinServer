package com.infolion.sapss.project.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.infolion.platform.console.workflow.dto.TaskHisDto;


/***
 * 立项打印信息装载类
 * @author Administrator
 *
 */
public class ProjectPrintDto implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String projectId;
	private String orgName;
	private String nuderTaker;//承办人
	private String projectNo;
	private String oldProjectNo;//原项目编码
	private String projectName;
	private String applyTime;//申报时间
	private String availableDataEnd;
	private String customerLinkMan;//客户名称
	private String customerPayType;//客户付款方式
	private String customerBalanceType;//客户结算方式
	private String providerLinkMan;//供应商名称
	private String providerPayType;//供应商付款方式
	private String providerBalanceType;//供应商结算方式
	
	private List<TaskHisDto> tasks = new ArrayList<TaskHisDto>() ;
	
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getNuderTaker() {
		return nuderTaker;
	}
	public void setNuderTaker(String nuderTaker) {
		this.nuderTaker = nuderTaker;
	}
	public String getProjectNo() {
		return projectNo;
	}
	public void setProjectNo(String projectNo) {
		this.projectNo = projectNo;
	}
	public String getOldProjectNo() {
		return oldProjectNo;
	}
	public void setOldProjectNo(String oldProjectNo) {
		this.oldProjectNo = oldProjectNo;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getApplyTime() {
		return applyTime;
	}
	public void setApplyTime(String applyTime) {
		this.applyTime = applyTime;
	}
	public String getCustomerLinkMan() {
		return customerLinkMan;
	}
	public void setCustomerLinkMan(String customerLinkMan) {
		this.customerLinkMan = customerLinkMan;
	}
	public String getCustomerPayType() {
		return customerPayType;
	}
	public void setCustomerPayType(String customerPayType) {
		this.customerPayType = customerPayType;
	}
	public String getCustomerBalanceType() {
		return customerBalanceType;
	}
	public void setCustomerBalanceType(String customerBalanceType) {
		this.customerBalanceType = customerBalanceType;
	}
	public String getProviderLinkMan() {
		return providerLinkMan;
	}
	public void setProviderLinkMan(String providerLinkMan) {
		this.providerLinkMan = providerLinkMan;
	}
	public String getProviderPayType() {
		return providerPayType;
	}
	public void setProviderPayType(String providerPayType) {
		this.providerPayType = providerPayType;
	}
	public String getProviderBalanceType() {
		return providerBalanceType;
	}
	public void setProviderBalanceType(String providerBalanceType) {
		this.providerBalanceType = providerBalanceType;
	}
	public List<TaskHisDto> getTasks() {
		return tasks;
	}
	public void setTasks(List<TaskHisDto> tasks) {
		this.tasks = tasks;
	}
	public String getAvailableDataEnd() {
		return availableDataEnd;
	}
	public void setAvailableDataEnd(String availableDataEnd) {
		this.availableDataEnd = availableDataEnd;
	}

}
