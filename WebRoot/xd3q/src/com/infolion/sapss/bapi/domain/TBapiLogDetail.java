package com.infolion.sapss.bapi.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.infolion.sapss.Constants;

/**
 * TBapiLogDetail entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_BAPI_LOG_DETAIL")
public class TBapiLogDetail implements java.io.Serializable {

	// Fields

	private String logDetailId;
	private TBapiLog TBapiLog;
	private String logType;//消息类型: S 成功,E 错误,W 警告,I 信息,A 中断
	private String logCode;
	private String logContent;//消息文本
	private String logText;
	private String creatorTime;
	private String creator;

	// Constructors

	/** default constructor */
	public TBapiLogDetail() {
	}

	/** minimal constructor */
	public TBapiLogDetail(String logDetailId, String logType, String logCode,
			String logContent) {
		this.logDetailId = logDetailId;
		this.logType = logType;
		this.logCode = logCode;
		this.logContent = logContent;
	}

	/** full constructor */
	public TBapiLogDetail(String logDetailId, TBapiLog TBapiLog,
			String logType, String logCode, String logContent, String logText,
			String creatorTime, String creator) {
		this.logDetailId = logDetailId;
		this.TBapiLog = TBapiLog;
		this.logType = logType;
		this.logCode = logCode;
		this.logContent = logContent;
		this.logText = logText;
		this.creatorTime = creatorTime;
		this.creator = creator;
	}

	// Property accessors
	@Id
	@Column(name = "LOG_DETAIL_ID", unique = true, nullable = false, length = 36)
	public String getLogDetailId() {
		return this.logDetailId;
	}

	public void setLogDetailId(String logDetailId) {
		this.logDetailId = logDetailId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "LOG_ID")
	public TBapiLog getTBapiLog() {
		return this.TBapiLog;
	}

	public void setTBapiLog(TBapiLog TBapiLog) {
		this.TBapiLog = TBapiLog;
	}

	@Column(name = "LOG_TYPE", nullable = false, length = 1)
	public String getLogType() {
		return this.logType;
	}

	public void setLogType(String logType) {
		this.logType = logType;
	}

	@Column(name = "LOG_CODE", nullable = false, length = 50)
	public String getLogCode() {
		return this.logCode;
	}

	public void setLogCode(String logCode) {
		this.logCode = logCode;
	}

	@Column(name = "LOG_CONTENT", nullable = false, length = 300)
	public String getLogContent() {
		return this.logContent;
	}

	public void setLogContent(String logContent) {
		this.logContent = logContent;
	}

	@Column(name = "LOG_TEXT", length = 2000)
	public String getLogText() {
		return this.logText;
	}

	public void setLogText(String logText) {
		this.logText = logText;
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