/*
 * @(#)ProcessObject.java
 * 版权声明： 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：张崇镇
 *  时　间：2008-12-18
 *  描　述：创建
 */

package com.infolion.platform.core.domain;

import java.util.Map;

import com.infolion.platform.util.StringUtils;

/**
 * 
 * <pre>
 * 需要审批流程的基础领域对象
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
public abstract class ProcessObject extends BaseObject {

	// 流程定义名称
	protected String workflowProcessName;
	// 是否同意
	private String workflowIsAllow;
	// 离开当前节点的迁移名称
	private String workflowLeaveTransitionName;
	// 审批意见
	private String workflowExamine;
	// 所属模块编号
	protected String workflowModelId;
	// 所属模块名称
	protected String workflowModelName;
	// 当前任务节点id
	private String workflowTaskId;
	// 附件文件路径
	private String workflowFilePath;
	// 附件文件描述
	private String workflowFileDescription;
	// 业务描述
	private String workflowBusinessNote;
	// 处理审批的Url
	protected String workflowProcessUrl;
	// 用户自定义流程变量
	private Map workflowUserDefineProcessVariable = null;
	// 用户自定义任务变量
	private Map workflowUserDefineTaskVariable = null;

	public ProcessObject() {
		setWorkflowModelName();
		setWorkflowModelId();
		setWorkflowProcessName();
		setWorkflowProcessUrl();
	}

	/**
	 * 由具体领域对象指定其所属模块编号
	 * 
	 */
	public abstract void setWorkflowModelId();

	/**
	 * 由具体领域对象指定其所属模块名称
	 * 
	 */
	public abstract void setWorkflowModelName();

	/**
	 * 由具体领域对象指定其所属流程名称
	 * 
	 */
	public abstract void setWorkflowProcessName();

	/**
	 * 由具体领域对象指定其处理流程的url(在待办事项中调用的url)
	 */
	public abstract void setWorkflowProcessUrl();

	public String getWorkflowProcessUrl() {
		return workflowProcessUrl;
	}

	public String getWorkflowBusinessNote() {
		return workflowBusinessNote;
	}

	public void setWorkflowBusinessNote(String workflowBusinessNote) {
		this.workflowBusinessNote = workflowBusinessNote;
	}

	public void setWorkflowProcessUrl(String workflowProcessUrl) {
		this.workflowProcessUrl = workflowProcessUrl;
	}

	public Map getWorkflowUserDefineProcessVariable() {
		return workflowUserDefineProcessVariable;
	}

	public void setWorkflowUserDefineProcessVariable(
			Map workflowUserDefineProcessVariable) {
		this.workflowUserDefineProcessVariable = workflowUserDefineProcessVariable;
	}

	public Map getWorkflowUserDefineTaskVariable() {
		return workflowUserDefineTaskVariable;
	}

	public void setWorkflowUserDefineTaskVariable(
			Map workflowUserDefineTaskVariable) {
		this.workflowUserDefineTaskVariable = workflowUserDefineTaskVariable;
	}

	public String getWorkflowTaskId() {
		return workflowTaskId;
	}

	public void setWorkflowTaskId(String workflowTaskId) {
		this.workflowTaskId = workflowTaskId;
	}

	public String getWorkflowIsAllow() {
		return workflowIsAllow;
	}

	public void setWorkflowIsAllow(String workflowIsAllow) {
		this.workflowIsAllow = workflowIsAllow;
	}

	public String getWorkflowExamine() {
		return workflowExamine;
	}

	public void setWorkflowExamine(String workflowExamine) {
		this.workflowExamine = workflowExamine;
	}

	public String getWorkflowModelId() {
		return workflowModelId;
	}

	public String getWorkflowModelName() {
		return workflowModelName;
	}

	public String getWorkflowProcessName() {
		return workflowProcessName;
	}

	public void setWorkflowProcessName(String workflowProcessName) {
		this.workflowProcessName = workflowProcessName;
	}

	public void setWorkflowModelId(String workflowModelId) {
		this.workflowModelId = workflowModelId;
	}

	public void setWorkflowModelName(String workflowModelName) {
		this.workflowModelName = workflowModelName;
	}

	public String getWorkflowFilePath() {
		return workflowFilePath;
	}

	public void setWorkflowFilePath(String workflowFilePath) {
		this.workflowFilePath = workflowFilePath;
	}

	public String getWorkflowFileDescription() {
		return workflowFileDescription;
	}

	public void setWorkflowFileDescription(String workflowFileDescription) {
		this.workflowFileDescription = workflowFileDescription;
	}

	public String getWorkflowLeaveTransitionName() {
		return workflowLeaveTransitionName;
	}

	public void setWorkflowLeaveTransitionName(
			String workflowLeaveTransitionName) {
		this.workflowLeaveTransitionName = workflowLeaveTransitionName;
	}

}
