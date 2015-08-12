package com.hy.wxserver.web.pojo;

import java.util.Date;

/**
 * ServiceMenu entity. @author MyEclipse Persistence Tools
 */

public class ServiceMenu implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -5740128012638255106L;
	private Integer id;
	private String code;
	private String codeDesc;
	private Date createTime;
	private Integer order;
	private String msgId;

	// Constructors

	/** default constructor */
	public ServiceMenu() {
	}

	/** full constructor */
	public ServiceMenu(Integer id, String code, String codeDesc,
			Date createTime, Integer order, String msgId) {
		this.id = id;
		this.code = code;
		this.codeDesc = codeDesc;
		this.createTime = createTime;
		this.order = order;
		this.msgId = msgId;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCodeDesc() {
		return this.codeDesc;
	}

	public void setCodeDesc(String codeDesc) {
		this.codeDesc = codeDesc;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getOrder() {
		return this.order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public String getMsgId() {
		return this.msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ServiceMenu : {\n\t\"id\" : \"");
		builder.append(id);
		builder.append("\", \n\t\"code\" : \"");
		builder.append(code);
		builder.append("\", \n\t\"codeDesc\" : \"");
		builder.append(codeDesc);
		builder.append("\", \n\t\"createTime\" : \"");
		builder.append(createTime);
		builder.append("\", \n\t\"order\" : \"");
		builder.append(order);
		builder.append("\", \n\t\"msgId\" : \"");
		builder.append(msgId);
		builder.append("\"\n}");
		return builder.toString();
	}

}