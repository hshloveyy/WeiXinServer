/*
 * @(#)SysWfProcessUser.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：傅渊源
 *  时　间：2008-12-30
 *  描　述：创建
 */

package com.infolion.platform.console.workflow.domain;

import com.infolion.platform.core.domain.BaseObject;

public class SysWfProcessUser extends BaseObject {
	private String businessRecordId; 
	private String modelId; 
	private String modelName; 
	private String creator; 
	private String actorType; 
	private String actorId; 
	private String actorName;
	private String name; 
	private String start;
	private String task; 
	private String taskId;
	
	public SysWfProcessUser() {
		super();
	}
	
	public String getBusinessRecordId() {
		return businessRecordId;
	}
	public void setBusinessRecordId(String businessRecordId) {
		this.businessRecordId = businessRecordId;
	}
	public String getModelId() {
		return modelId;
	}
	public void setModelId(String modelId) {
		this.modelId = modelId;
	}
	public String getModelName() {
		return modelName;
	}
	public void setModelName(String modelName) {
		this.modelName = modelName;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStart() {
		return start;
	}
	public void setStart(String start) {
		this.start = start;
	}
	public String getTask() {
		return task;
	}
	public void setTask(String task) {
		this.task = task;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
}
