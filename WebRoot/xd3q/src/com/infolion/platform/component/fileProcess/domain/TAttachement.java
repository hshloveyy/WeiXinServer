package com.infolion.platform.component.fileProcess.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.infolion.platform.core.annotation.ValidateRule;
import com.infolion.platform.core.domain.BaseObject;
import com.infolion.platform.core.validation.DataType;

/**
 * TAttachement entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_ATTACHEMENT")
public class TAttachement extends BaseObject implements java.io.Serializable {

	// Fields

	private String attachementId;
	private String attachementBusinessId;
	@ValidateRule(dataType = DataType.STRING, label = "原文件名", maxLength = 300, required = true)
	private String oldName;
	@ValidateRule(dataType = DataType.STRING, label = "物理文件名", maxLength = 300, required = true)
	private String readName;
	private String attachementCmd;
	private String uploadTime;
	private String creatorTime;
	private String creator;
	// Property accessors
	@Id
	@Column(name = "ATTACHEMENT_ID", unique = true, nullable = false, length = 36)
	public String getAttachementId() {
		return this.attachementId;
	}

	public void setAttachementId(String attachementId) {
		this.attachementId = attachementId;
	}

	@Column(name = "ATTACHEMENT_BUSINESS_ID", length = 36)
	public String getAttachementBusinessId() {
		return this.attachementBusinessId;
	}

	public void setAttachementBusinessId(String attachementBusinessId) {
		this.attachementBusinessId = attachementBusinessId;
	}

	@Column(name = "OLD_NAME", nullable = false, length = 300)
	public String getOldName() {
		return this.oldName;
	}

	public void setOldName(String oldName) {
		this.oldName = oldName;
	}

	@Column(name = "READ_NAME", nullable = false, length = 300)
	public String getReadName() {
		return this.readName;
	}

	public void setReadName(String readName) {
		this.readName = readName;
	}

	@Column(name = "ATTACHEMENT_CMD", length = 300)
	public String getAttachementCmd() {
		return this.attachementCmd;
	}

	public void setAttachementCmd(String attachementCmd) {
		this.attachementCmd = attachementCmd;
	}

	@Column(name = "UPLOAD_TIME", length = 14)
	public String getUploadTime() {
		return this.uploadTime;
	}

	public void setUploadTime(String uploadTime) {
		this.uploadTime = uploadTime;
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