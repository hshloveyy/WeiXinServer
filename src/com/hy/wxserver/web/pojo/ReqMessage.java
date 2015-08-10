package com.hy.wxserver.web.pojo;

import java.util.Date;

/**
 * ReqMessageId entity. @author MyEclipse Persistence Tools
 */

public class ReqMessage implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 2976256618395688580L;
	private String msgId;
	private String toUserName;
	private String fromUserName;
	private Date createTime;
	private String msgType;
	private String content;
	private String picUrl;
	private String mediaId;
	private String format;
	private String thumbMediaId;
	private String locationX;
	private String locationY;
	private String scale;
	private String label;
	private String title;
	private String description;
	private String url;

	// Constructors

	/** default constructor */
	public ReqMessage() {
	}

	/** minimal constructor */
	public ReqMessage(String msgId, String toUserName, String fromUserName,
			Date createTime, String msgType) {
		this.msgId = msgId;
		this.toUserName = toUserName;
		this.fromUserName = fromUserName;
		this.createTime = createTime;
		this.msgType = msgType;
	}
	
	/** full constructor */
	public ReqMessage(String msgId, String toUserName, String fromUserName,
			Date createTime, String msgType, String content, String picUrl,
			String mediaId, String format, String thumbMediaId,
			String locationX, String locationY, String scale, String label,
			String title, String description, String url) {
		this.msgId = msgId;
		this.toUserName = toUserName;
		this.fromUserName = fromUserName;
		this.createTime = createTime;
		this.msgType = msgType;
		this.content = content;
		this.picUrl = picUrl;
		this.mediaId = mediaId;
		this.format = format;
		this.thumbMediaId = thumbMediaId;
		this.locationX = locationX;
		this.locationY = locationY;
		this.scale = scale;
		this.label = label;
		this.title = title;
		this.description = description;
		this.url = url;
	}

	// Property accessors

	public String getMsgId() {
		return this.msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

	public String getToUserName() {
		return this.toUserName;
	}

	public void setToUserName(String toUserName) {
		this.toUserName = toUserName;
	}

	public String getFromUserName() {
		return this.fromUserName;
	}

	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getMsgType() {
		return this.msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getPicUrl() {
		return this.picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public String getMediaId() {
		return this.mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}

	public String getFormat() {
		return this.format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getThumbMediaId() {
		return this.thumbMediaId;
	}

	public void setThumbMediaId(String thumbMediaId) {
		this.thumbMediaId = thumbMediaId;
	}

	public String getLocationX() {
		return this.locationX;
	}

	public void setLocationX(String locationX) {
		this.locationX = locationX;
	}

	public String getLocationY() {
		return this.locationY;
	}

	public void setLocationY(String locationY) {
		this.locationY = locationY;
	}

	public String getScale() {
		return this.scale;
	}

	public void setScale(String scale) {
		this.scale = scale;
	}

	public String getLabel() {
		return this.label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}