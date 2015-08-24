package com.infolion.sapss.project.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.infolion.sapss.Constants;

/**
 * TProjectAccountingType entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_PROJECT_ACCOUNTING_TYPE")
public class TProjectAccountingType implements java.io.Serializable {

	// Fields

	private String accountingItemId;
	private String accountingItem;
	private String cmd;
	private String isAvailable;
	private String creatorTime;
	private String creator;
	// Property accessors
	@Id
	@Column(name = "ACCOUNTING_ITEM_ID", unique = true, nullable = false, length = 36)
	public String getAccountingItemId() {
		return this.accountingItemId;
	}

	public void setAccountingItemId(String accountingItemId) {
		this.accountingItemId = accountingItemId;
	}

	@Column(name = "ACCOUNTING_ITEM", nullable = false, length = 200)
	public String getAccountingItem() {
		return this.accountingItem;
	}

	public void setAccountingItem(String accountingItem) {
		this.accountingItem = accountingItem;
	}

	@Column(name = "CMD", length = 300)
	public String getCmd() {
		return this.cmd;
	}

	public void setCmd(String cmd) {
		this.cmd = cmd;
	}

	@Column(name = "IS_AVAILABLE", length = 1)
	public String getIsAvailable() {
		return this.isAvailable;
	}

	public void setIsAvailable(String isAvailable) {
		this.isAvailable = isAvailable;
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

}