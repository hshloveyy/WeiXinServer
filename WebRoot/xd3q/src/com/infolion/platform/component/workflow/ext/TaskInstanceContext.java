/*
 * @(#)WorkflowContext.java
 * 版权声明： 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：张崇镇
 *  时　间：2009-2-12
 *  描　述：创建
 */

package com.infolion.platform.component.workflow.ext;

import java.util.ArrayList;
import java.util.List;

import com.infolion.platform.core.domain.BaseObject;

/**
 * 
 * <pre>
 * 工作流实例节点上下文，某个实例节点的上下文信息
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
public class TaskInstanceContext extends BaseObject {
	private long taskId;
	private String taskName;
	private String taskDescription;
	private String currentActor;
	private List<CommonTransition> transitions = new ArrayList();
	private List<CommonTaskInstance> historyTasks = new ArrayList();

	public long getTaskId() {
		return taskId;
	}

	public void setTaskId(long taskId) {
		this.taskId = taskId;
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

	public List<CommonTransition> getTransitions() {
		return transitions;
	}

	public void setTransitions(List<CommonTransition> transitions) {
		this.transitions = transitions;
	}

	public List<CommonTaskInstance> getHistoryTasks() {
		return historyTasks;
	}

	public void setHistoryTasks(List<CommonTaskInstance> historyTasks) {
		this.historyTasks = historyTasks;
	}

	public String getCurrentActor() {
		return currentActor;
	}

	public void setCurrentActor(String currentActor) {
		this.currentActor = currentActor;
	}

}
