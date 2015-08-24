/*
 * @(#)ProcessTaskKey.java
 * 版权声明： 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：张崇镇
 *  时　间：2008-12-23
 *  描　述：创建
 */

package com.infolion.platform.component.workflow.ext;

import java.io.Serializable;

import javax.persistence.Column;

public class ProcessTaskKey implements Serializable {

	@Column(name = "TASK_ID")
	private long taskId;
	@Column(name = "PROCESS_ID")
	private long processId;

	public ProcessTaskKey() {
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (processId ^ (processId >>> 32));
		result = prime * result + (int) (taskId ^ (taskId >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProcessTaskKey other = (ProcessTaskKey) obj;
		if (processId != other.processId)
			return false;
		if (taskId != other.taskId)
			return false;
		return true;
	}

	public long getProcessId() {
		return processId;
	}

	public void setProcessId(long processId) {
		this.processId = processId;
	}

	public long getTaskId() {
		return taskId;
	}

	public void setTaskId(long taskId) {
		this.taskId = taskId;
	}

}
