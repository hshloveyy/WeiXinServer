package com.infolion.sapss.credit.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TCreditRec entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_CREDIT_REC")
public class TCreditRec implements java.io.Serializable {

	// Fields

	private String creditRecId;
	private String creditId;
	private String contractSalesId;
	private String creatorTime;
	private String creator;

	// Constructors

	/** default constructor */
	public TCreditRec() {
	}

	/** minimal constructor */
	public TCreditRec(String creditRecId) {
		this.creditRecId = creditRecId;
	}

	/** full constructor */
	public TCreditRec(String creditRecId, String creditId,
			String contractSalesId, String creatorTime, String creator) {
		this.creditRecId = creditRecId;
		this.creditId = creditId;
		this.contractSalesId = contractSalesId;
		this.creatorTime = creatorTime;
		this.creator = creator;
	}

	// Property accessors
	@Id
	@Column(name = "CREDIT_REC_ID", unique = true, nullable = false, length = 36)
	public String getCreditRecId() {
		return this.creditRecId;
	}

	public void setCreditRecId(String creditRecId) {
		this.creditRecId = creditRecId;
	}

	@Column(name = "CREDIT_ID", length = 36)
	public String getCreditId() {
		return this.creditId;
	}

	public void setCreditId(String creditId) {
		this.creditId = creditId;
	}

	@Column(name = "CONTRACT_SALES_ID", length = 36)
	public String getContractSalesId() {
		return this.contractSalesId;
	}

	public void setContractSalesId(String contractSalesId) {
		this.contractSalesId = contractSalesId;
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