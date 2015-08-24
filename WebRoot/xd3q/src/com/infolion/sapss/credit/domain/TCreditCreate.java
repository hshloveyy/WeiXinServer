package com.infolion.sapss.credit.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TCreditCreate entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_CREDIT_CREATE")
public class TCreditCreate implements java.io.Serializable {

	// Fields

	private String creditCreateId;
	private String creditId;
	private String contractPurchaseId;
	private String creatorTime;
	private String creator;

	// Constructors

	/** default constructor */
	public TCreditCreate() {
	}

	/** minimal constructor */
	public TCreditCreate(String creditCreateId) {
		this.creditCreateId = creditCreateId;
	}

	/** full constructor */
	public TCreditCreate(String creditCreateId, String creditId,
			String contractPurchaseId, String creatorTime, String creator) {
		this.creditCreateId = creditCreateId;
		this.creditId = creditId;
		this.contractPurchaseId = contractPurchaseId;
		this.creatorTime = creatorTime;
		this.creator = creator;
	}

	// Property accessors
	@Id
	@Column(name = "CREDIT_CREATE_ID", unique = true, nullable = false, length = 36)
	public String getCreditCreateId() {
		return this.creditCreateId;
	}

	public void setCreditCreateId(String creditCreateId) {
		this.creditCreateId = creditCreateId;
	}

	@Column(name = "CREDIT_ID", length = 36)
	public String getCreditId() {
		return this.creditId;
	}

	public void setCreditId(String creditId) {
		this.creditId = creditId;
	}

	@Column(name = "CONTRACT_PURCHASE_ID", length = 36)
	public String getContractPurchaseId() {
		return this.contractPurchaseId;
	}

	public void setContractPurchaseId(String contractPurchaseId) {
		this.contractPurchaseId = contractPurchaseId;
	}

	@Column(name = "CREATOR_TIME", length = 20)
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