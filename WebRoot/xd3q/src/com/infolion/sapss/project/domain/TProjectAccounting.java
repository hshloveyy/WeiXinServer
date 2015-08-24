package com.infolion.sapss.project.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.infolion.platform.core.domain.BaseObject;
import com.infolion.sapss.Constants;

/**
 * TProjectAccounting entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_PROJECT_ACCOUNTING")
public class TProjectAccounting extends BaseObject{

	// Fields

	private String id;
	private String projectId;
	private int accountingItemId;
	private String accountingItem;
	private String currency;
	private String accountingValue;
	private String creatorTime;
	private String creator;
	private String contractSalesId;
	private String contractGroupId;
	// Property accessors
	@Id
	@Column(name = "ID", unique = true, nullable = false, length = 36)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "PROJECT_ID", length = 36)
	public String getProjectId() {
		return this.projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	@Column(name = "ACCOUNTING_ITEM_ID")
	public int getAccountingItemId() {
		return this.accountingItemId;
	}

	public void setAccountingItemId(int accountingItemId) {
		this.accountingItemId = accountingItemId;
	}

	@Column(name = "ACCOUNTING_ITEM", length = 100)
	public String getAccountingItem() {
		return this.accountingItem;
	}

	public void setAccountingItem(String accountingItem) {
		this.accountingItem = accountingItem;
	}

	@Column(name = "CURRENCY", length = 10)
	public String getCurrency() {
		return this.currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	@Column(name = "ACCOUNTING_VALUE", nullable = false, length = 10)
	public String getAccountingValue() {
		return this.accountingValue;
	}

	public void setAccountingValue(String accountingValue) {
		this.accountingValue = accountingValue;
	}

	@Column(name = "CREATOR_TIME", length = 14)
	public String getCreatorTime() {
		return this.creatorTime;
	}

	public void setCreatorTime(String creatorTime) {
		this.creatorTime = creatorTime;
	}

	@Column(name = "CREATOR", length = 36)
	public String getCreator() {
		return this.creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	/**
	 * @return the contractSalesId
	 */
	@Column(name = "CONTRACT_SALES_ID", length = 36)
	public String getContractSalesId() {
		return contractSalesId;
	}

	/**
	 * @param contractSalesId the contractSalesId to set
	 */
	
	public void setContractSalesId(String contractSalesId) {
		this.contractSalesId = contractSalesId;
	}

	/**
	 * @return the contractGroupId
	 */
	@Column(name = "CONTRACT_GROUP_ID", length = 36)
	public String getContractGroupId() {
		return contractGroupId;
	}

	/**
	 * @param contractGroupId the contractGroupId to set
	 */
	public void setContractGroupId(String contractGroupId) {
		this.contractGroupId = contractGroupId;
	}
	

}