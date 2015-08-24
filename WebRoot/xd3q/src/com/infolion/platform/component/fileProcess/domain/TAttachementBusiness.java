package com.infolion.platform.component.fileProcess.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.infolion.platform.core.domain.BaseObject;

/**
 * TAttachementBusiness entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_ATTACHEMENT_BUSINESS")
public class TAttachementBusiness extends BaseObject implements java.io.Serializable {

	// Fields

	private String attachementBusinessId;
	private String resourceId;
	private String resourceName;
	private String recordId;
	private String creatorTime;
	private String creator;
	// Property accessors
	@Id
	@Column(name = "ATTACHEMENT_BUSINESS_ID", unique = true, nullable = false, length = 36)
	public String getAttachementBusinessId() {
		return this.attachementBusinessId;
	}

	public void setAttachementBusinessId(String attachementBusinessId) {
		this.attachementBusinessId = attachementBusinessId;
	}

	@Column(name = "RESOURCE_ID", nullable = false, length = 50)
	public String getResourceId() {
		return this.resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

	@Column(name = "RESOURCE_NAME", length = 300)
	public String getResourceName() {
		return this.resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	@Column(name = "RECORD_ID", nullable = false, length = 36)
	public String getRecordId() {
		return this.recordId;
	}

	public void setRecordId(String recordId) {
		this.recordId = recordId;
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