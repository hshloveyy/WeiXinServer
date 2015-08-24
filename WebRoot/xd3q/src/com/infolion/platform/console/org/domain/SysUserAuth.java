/*
 * @(#)SysUserAuth.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：傅渊源
 *  时　间：2008-12-15
 *  描　述：创建
 */

package com.infolion.platform.console.org.domain;

import com.infolion.platform.core.domain.BaseObject;

public class SysUserAuth extends BaseObject {
	private String authId;
	private String fromUserId;
	private String fromUserName;
	private String toUserId;
	private String toUserName;
	private String typeId;
	private String typeName;
	private String toId;
	private String toName;
	private String authTime;
	private String unAuthTime;
	private String lastTime;
	private String isEffect;
	private String creator;
	private String createTime;
	
	public SysUserAuth() {
		super();
	}

	public String getAuthId() {
		return authId;
	}
	public void setAuthId(String authId) {
		this.authId = authId;
	}
	public String getFromUserId() {
		return fromUserId;
	}
	public void setFromUserId(String fromUserId) {
		this.fromUserId = fromUserId;
	}
	public String getToUserId() {
		return toUserId;
	}
	public void setToUserId(String toUserId) {
		this.toUserId = toUserId;
	}
	public String getAuthType() {
		return typeId;
	}
	public void setAuthType(String authType) {
		this.typeId = authType;
	}
	public String getToId() {
		return toId;
	}
	public void setToId(String toId) {
		this.toId = toId;
	}
	public String getAuthTime() {
		return authTime;
	}
	public void setAuthTime(String authTime) {
		this.authTime = authTime;
	}
	public String getUnAuthTime() {
		return unAuthTime;
	}
	public void setUnAuthTime(String unAuthTime) {
		this.unAuthTime = unAuthTime;
	}
	public String getLastTime() {
		return lastTime;
	}
	public void setLastTime(String lastTime) {
		this.lastTime = lastTime;
	}
	public String getIsEffect() {
		return isEffect;
	}
	public void setIsEffect(String isEffect) {
		this.isEffect = isEffect;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getFromUserName() {
		return fromUserName;
	}

	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}

	public String getToUserName() {
		return toUserName;
	}

	public void setToUserName(String toUserName) {
		this.toUserName = toUserName;
	}

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getToName() {
		return toName;
	}

	public void setToName(String toName) {
		this.toName = toName;
	}
}
