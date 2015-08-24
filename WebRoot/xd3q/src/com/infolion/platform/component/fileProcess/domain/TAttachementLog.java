package com.infolion.platform.component.fileProcess.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.infolion.platform.core.domain.BaseObject;

@Entity
@Table(name = "t_attachement_log")
public class TAttachementLog extends BaseObject implements java.io.Serializable {

	// Fields

	private String attachementLogId;
	private String attachementId;
	private String attachementBusinessId;
	private String oldName;
	private String readName;
	private String attachementCmd;
	private String uploadTime;
	private String creatorTime;
	private String creator;
	
	private String newOldName;
	private String newReadName;
	private String newAttachementCmd;
	private String operateTime;
	private String operater;
	private String mack;//1:新增，2修改，3删除
	private String cmd;//备注二期或三期
	
	@Id
	@Column(name = "ATTACHEMENT_LOG_ID", unique = true, nullable = false, length = 36)
	public String getAttachementLogId() {
		return attachementLogId;
	}

	public void setAttachementLogId(String attachementLogId) {
		this.attachementLogId = attachementLogId;
	}
	@Column(name = "ATTACHEMENT_ID",length = 36)
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

	@Column(name = "NEW_OLD_NAME", length = 36)
	public String getNewOldName() {
		return newOldName;
	}

	public void setNewOldName(String newOldName) {
		this.newOldName = newOldName;
	}
	@Column(name = "NEW_READ_NAME", length = 36)
	public String getNewReadName() {
		return newReadName;
	}

	public void setNewReadName(String newReadName) {
		this.newReadName = newReadName;
	}
	@Column(name = "NEW_ATTACHEMENT_CMD", length = 36)
	public String getNewAttachementCmd() {
		return newAttachementCmd;
	}

	public void setNewAttachementCmd(String newAttachementCmd) {
		this.newAttachementCmd = newAttachementCmd;
	}
	@Column(name = "OPERATE_TIME", length = 36)
	public String getOperateTime() {
		return operateTime;
	}

	public void setOperateTime(String operateTime) {
		this.operateTime = operateTime;
	}
	@Column(name = "OPERATER", length = 36)
	public String getOperater() {
		return operater;
	}

	public void setOperater(String operater) {
		this.operater = operater;
	}
	@Column(name = "MACK", length = 36)
	public String getMack() {
		return mack;
	}

	public void setMack(String mack) {
		this.mack = mack;
	}
	@Column(name = "cmd", length = 36)
	public String getCmd() {
		return cmd;
	}

	public void setCmd(String cmd) {
		this.cmd = cmd;
	}

}