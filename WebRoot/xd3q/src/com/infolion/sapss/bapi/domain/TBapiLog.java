package com.infolion.sapss.bapi.domain;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.infolion.sapss.Constants;

/**
 * TBapiLog entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_BAPI_LOG")
public class TBapiLog implements java.io.Serializable {

	// Fields

	private String logId;
	private String businessRecordId;
	private String contractNo;
	private String contractName;
	private String sapOrderNo;
	private String orderType;
	private String orderCreteDept;
	private String orderCreator;
	private String creatorTime;
	private String creator;
	private Set<TBapiLogDetail> TBapiLogDetails = new HashSet<TBapiLogDetail>(0);

	// Constructors

	/** default constructor */
	public TBapiLog() {
	}

	/** minimal constructor */
	public TBapiLog(String logId, String businessRecordId, String logType,
			String logCode, String logContent) {
		this.logId = logId;
		this.businessRecordId = businessRecordId;
	}

	/** full constructor */
	public TBapiLog(String logId, String businessRecordId, String contractNo,
			String contractName, String sapOrderNo, String orderType,
			String orderCreteDept, String orderCreator, 
			String creatorTime, String creator,
			Set<TBapiLogDetail> TBapiLogDetails) {
		this.logId = logId;
		this.businessRecordId = businessRecordId;
		this.contractNo = contractNo;
		this.contractName = contractName;
		this.sapOrderNo = sapOrderNo;
		this.orderType = orderType;
		this.orderCreteDept = orderCreteDept;
		this.orderCreator = orderCreator;
		this.creatorTime = creatorTime;
		this.creator = creator;
		this.TBapiLogDetails = TBapiLogDetails;
	}

	// Property accessors
	@Id
	@Column(name = "LOG_ID", unique = true, nullable = false, length = 36)
	public String getLogId() {
		return this.logId;
	}

	public void setLogId(String logId) {
		this.logId = logId;
	}

	@Column(name = "BUSINESS_RECORD_ID", nullable = false, length = 36)
	public String getBusinessRecordId() {
		return this.businessRecordId;
	}

	public void setBusinessRecordId(String businessRecordId) {
		this.businessRecordId = businessRecordId;
	}

	@Column(name = "CONTRACT_NO", length = 36)
	public String getContractNo() {
		return this.contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	@Column(name = "CONTRACT_NAME", length = 100)
	public String getContractName() {
		return this.contractName;
	}

	public void setContractName(String contractName) {
		this.contractName = contractName;
	}

	@Column(name = "SAP_ORDER_NO", length = 30)
	public String getSapOrderNo() {
		return this.sapOrderNo;
	}

	public void setSapOrderNo(String sapOrderNo) {
		this.sapOrderNo = sapOrderNo;
	}

	@Column(name = "ORDER_TYPE", length = 1)
	public String getOrderType() {
		return this.orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	@Column(name = "ORDER_CRETE_DEPT", length = 100)
	public String getOrderCreteDept() {
		return this.orderCreteDept;
	}

	public void setOrderCreteDept(String orderCreteDept) {
		this.orderCreteDept = orderCreteDept;
	}

	@Column(name = "ORDER_CREATOR", length = 100)
	public String getOrderCreator() {
		return this.orderCreator;
	}

	public void setOrderCreator(String orderCreator) {
		this.orderCreator = orderCreator;
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

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "TBapiLog")
	public Set<TBapiLogDetail> getTBapiLogDetails() {
		return this.TBapiLogDetails;
	}

	public void setTBapiLogDetails(Set<TBapiLogDetail> TBapiLogDetails) {
		this.TBapiLogDetails = TBapiLogDetails;
	}

}