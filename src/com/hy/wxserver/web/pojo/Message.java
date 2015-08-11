package com.hy.wxserver.web.pojo;

import java.util.Date;

/**
 * Message entity. @author MyEclipse Persistence Tools
 */

public class Message implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 7384473768645343917L;
	private Integer msgId = 21;
	private String msgType = "r";
	private String msgBody = "323121";
	private Date createTime = new Date();

	// Constructors

	/** default constructor */
	public Message() {
	}

	/** full constructor */
	public Message(Integer msgId, String msgType, String msgBody,
			Date createTime) {
		this.msgId = msgId;
		this.msgType = msgType;
		this.msgBody = msgBody;
		this.createTime = createTime;
	}

	// Property accessors

	public Integer getMsgId() {
		return this.msgId;
	}

	public void setMsgId(Integer msgId) {
		this.msgId = msgId;
	}

	public String getMsgType() {
		return this.msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	public String getMsgBody() {
		return this.msgBody;
	}

	public void setMsgBody(String msgBody) {
		this.msgBody = msgBody;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Message : {\n\t\"msgId\" : \"");
		builder.append(msgId);
		builder.append("\", \n\t\"msgType\" : \"");
		builder.append(msgType);
		builder.append("\", \n\t\"msgBody\" : \"");
		builder.append(msgBody);
		builder.append("\", \n\t\"createTime\" : \"");
		builder.append(createTime);
		builder.append("\"\n}");
		return builder.toString();
	}

	
}