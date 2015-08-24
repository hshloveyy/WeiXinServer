package com.infolion.sapss.mainData.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TSuppliersPayTerms entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_SUPPLIERS_PAY_TERMS")
public class TSuppliersPayTerms implements java.io.Serializable {

	// Fields

	private String payTermsId;
	private String suppliersId;
	private String payTypeId;

	// Constructors

	/** default constructor */
	public TSuppliersPayTerms() {
	}

	/** minimal constructor */
	public TSuppliersPayTerms(String payTermsId) {
		this.payTermsId = payTermsId;
	}

	/** full constructor */
	public TSuppliersPayTerms(String payTermsId, String suppliersId,
			String payTypeId) {
		this.payTermsId = payTermsId;
		this.suppliersId = suppliersId;
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

	@Column(name = "SUPPLIERS_ID", length = 36)
	public String getSuppliersId() {
		return this.suppliersId;
	}

	public void setSuppliersId(String suppliersId) {
		this.suppliersId = suppliersId;
	}

	@Column(name = "PAY_TYPE_ID", length = 4)
	public String getPayTypeId() {
		return this.payTypeId;
	}

	public void setPayTypeId(String payTypeId) {
		this.payTypeId = payTypeId;
	}

}