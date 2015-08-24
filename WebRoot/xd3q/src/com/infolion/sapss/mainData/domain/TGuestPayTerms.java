package com.infolion.sapss.mainData.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TGuestPayTerms entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_GUEST_PAY_TERMS")
public class TGuestPayTerms implements java.io.Serializable {

	// Fields

	private String payTermsId;
	private String guestId;
	private String payTypeId;

	// Constructors

	/** default constructor */
	public TGuestPayTerms() {
	}

	/** minimal constructor */
	public TGuestPayTerms(String payTermsId) {
		this.payTermsId = payTermsId;
	}

	/** full constructor */
	public TGuestPayTerms(String payTermsId, String guestId, String payTypeId) {
		this.payTermsId = payTermsId;
		this.guestId = guestId;
		this.payTypeId = payTypeId;
	}

	// Property accessors
	@Id
	@Column(name = "PAY_TERMS_ID", unique = true, nullable = false, length = 36)
	public String getPayTermsId() {
		return this.payTermsId;
	}

	public void setPayTermsId(String payTermsId) {
		this.payTermsId = payTermsId;
	}

	@Column(name = "GUEST_ID", length = 36)
	public String getGuestId() {
		return this.guestId;
	}

	public void setGuestId(String guestId) {
		this.guestId = guestId;
	}

	@Column(name = "PAY_TYPE_ID", length = 4)
	public String getPayTypeId() {
		return this.payTypeId;
	}

	public void setPayTypeId(String payTypeId) {
		this.payTypeId = payTypeId;
	}

}