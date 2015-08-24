package com.infolion.platform.console.workflow.dto;

import java.io.Serializable;

public class TaskHisDto implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String taskName;
	private String examineDeptName;
	private String examinePerson;
	private String examineResult;
	private String examine;
	private String taskCreateTime;
	private String taskEndTime;
	
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public String getExamineDeptName() {
		return examineDeptName;
	}
	public void setExamineDeptName(String examineDeptName) {
		this.examineDeptName = examineDeptName;
	}
	public String getExaminePerson() {
		return examinePerson;
	}
	public void setExaminePerson(String examinePerson) {
		this.examinePerson = examinePerson;
	}
	public String getExamineResult() {
		return examineResult;
	}
	public void setExamineResult(String examineResult) {
		this.examineResult = examineResult;
	}
	public String getExamine() {
		return examine;
	}
	public void setExamine(String examine) {
		this.examine = examine;
	}
	public String getTaskCreateTime() {
		return taskCreateTime;
	}
	public void setTaskCreateTime(String taskCreateTime) {
		this.taskCreateTime = taskCreateTime;
	}
	public String getTaskEndTime() {
		return taskEndTime;
	}
	public void setTaskEndTime(String taskEndTime) {
		this.taskEndTime = taskEndTime;
	}
	

}
