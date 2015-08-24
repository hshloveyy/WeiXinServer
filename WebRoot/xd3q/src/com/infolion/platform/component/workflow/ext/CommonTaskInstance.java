/*
 * @(#)CommonTaskInstance.java
 * 版权声明： 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：张崇镇
 *  时　间：2008-12-12
 *  描　述：创建
 */

package com.infolion.platform.component.workflow.ext;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import com.infolion.platform.core.domain.BaseObject;

/**
 * 
 * <pre>
 * 加上自定义变量的工作项类
 * </pre>
 * 
 * <br>
 * JDK版本:1.5
 * 
 * @author 张崇镇
 * @version 1.0
 * @see The author for more details
 * @since 1.0
 */
@Entity
@Table(name = "T_SYS_WF_TASK_HISTORY")
public class CommonTaskInstance extends BaseObject {

	@Id
	@Column(name = "TASK_HIS_ID")
	private String taskHisId;
	
	@Column(name = "TASK_ID")
	private long taskId;
	
	@Column(name = "PROCESS_ID")
	private long processId;

	@Column(name = "BUSINESS_RECORD_ID")
	private String businessRecordId;

	@Column(name = "TASK_NAME")
	private String taskName;

	@Column(name = "TASK_DESCRIPTION")
	private String taskDescription;

	@Column(name = "TASK_CREATE_TIME")
	private String taskCreateTime;

	@Column(name = "TASK_END_TIME")
	private String taskEndTime;

	@Column(name = "CREATOR")
	private String creator;

	@Column(name = "CREATE_TIME")
	private String createTime;

	@Column(name = "EXAMINE")
	private String examine;

	@Column(name = "EXAMINE_PERSON")
	private String examinePerson;

	@Column(name = "EXAMINE_RESULT")
	private String examineResult;

	@Column(name = "EXAMINE_DEPT_NAME")
	private String examineDeptName;

	public String getExamineDeptName() {
		return examineDeptName;
	}

	public void setExamineDeptName(String examineDeptName) {
		this.examineDeptName = examineDeptName;
	}

	public String getTaskHisId() {
		return taskHisId;
	}

	public void setTaskHisId(String taskHisId) {
		this.taskHisId = taskHisId;
	}

	public long getTaskId() {
		return taskId;
	}

	public void setTaskId(long taskId) {
		this.taskId = taskId;
	}

	public long getProcessId() {
		return processId;
	}

	public void setProcessId(long processId) {
		this.processId = processId;
	}

	public String getBusinessRecordId() {
		return businessRecordId;
	}

	public void setBusinessRecordId(String businessRecordId) {
		this.businessRecordId = businessRecordId;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getTaskDescription() {
		return taskDescription;
	}

	public void setTaskDescription(String taskDescription) {
		this.taskDescription = taskDescription;
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

	public String getExamine() {
		return examine;
	}

	public void setExamine(String examine) {
		this.examine = examine;
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
