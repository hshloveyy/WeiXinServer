/*
 * @(#)SysWfTaskActor.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：傅渊源
 *  时　间：2008-12-24
 *  描　述：创建
 */

package com.infolion.platform.console.workflow.domain;

import com.infolion.platform.core.domain.BaseObject;

public class SysWfTaskActor extends BaseObject {
	private String taskActorId;
	private String processDefId;
	private String taskDefId;
	private String actorType;
	private String actorId;
	private String actorName;
	private String creator;
	private String createTime;
	
	public SysWfTaskActor() {
		super();
	}

	public String getTaskActorId() {
		return taskActorId;
	}
	public void setTaskActorId(String taskActorId) {
		this.taskActorId = taskActorId;
	}
	public String getProcessDefId() {
		return processDefId;
	}
	public void setProcessDefId(String processDefId) {
		this.processDefId = processDefId;
	}
	public String getTaskDefId() {
		return taskDefId;
	}
	public void setTaskDefId(String taskDefId) {
		this.taskDefId = taskDefId;
	}
	public String getActorType() {
		return actorType;
	}
	public void setActorType(String actorType) {
		this.actorType = actorType;
	}
	public String getActorId() {
		return actorId;
	}
	public void setActorId(String actorId) {
		this.actorId = actorId;
	}
	public String getActorName() {
		return actorName;
	}
	public void setActorName(String actorName) {
		this.actorName = actorName;
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
}
