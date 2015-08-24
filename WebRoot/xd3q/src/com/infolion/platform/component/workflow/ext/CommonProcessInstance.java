/*
 * @(#)CommonProcessInstance.java
 * 版权声明： 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：张崇镇
 *  时　间：2008-12-22
 *  描　述：创建
 */

package com.infolion.platform.component.workflow.ext;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.infolion.platform.core.domain.BaseObject;

/**
 * 
 * <pre>
 * 扩展业务属性的流程实例
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
@Table(name = "T_SYS_WF_PROCESS_INSTANCE")
public class CommonProcessInstance extends BaseObject
{
	@Id
	@Column(name = "COMMON_PROCESS_ID")
	private String commonProcessId;

	@Column(name = "PROCESS_ID")
	private long processId;

	/**
	 * 业务记录ID
	 */
	@Column(name = "BUSINESS_RECORD_ID")
	private String businessRecordId;
	// 为了对待办事项进行数据范围的控制，加入部门编号字段
	@Column(name = "DEPARTMENT_ID")
	private String departmentId;

	@Column(name = "MODEL_ID")
	private String modelId;

	@Column(name = "MODEL_NAME")
	private String modelName;

	@Column(name = "PROCESS_URL")
	private String processUrl;

	@Column(name = "INS_CREATE_TIME")
	private String insCreateTime;

	@Column(name = "INS_END_TIME")
	private String insEndTime;

	@Column(name = "END_NODE_NAME")
	private String endNodeName;

	@Column(name = "CREATOR")
	private String creator;

	@Column(name = "CREATE_TIME")
	private String createTime;

	@Column(name = "BUSINESS_NOTE")
	private String businessNote;

	/**
	 * 父流程扩展实例ID
	 */
	@Column(name = "PARENT_COMMON_PROCESS_ID")
	private String parentCommonProcessId;

	// 流程未结束时所在的任务名称和任务开始时间
	/**
	 * taskInstanceId
	 */
	@Transient
	private long taskId;

	@Transient
	private String taskName;
	@Transient
	private Date startTime;
	@Transient
	private String examineAndApprove;
	@Transient
	private String workFlowState;

	// TODO LJX20100430 XDSS3、BDP系统整合。
	/**
	 * 工作流类别 1：为信达旧系统流程， 2：为BDP系统流程。
	 */
	@Transient
	private String processType;

	/**
	 * BDP字段，流程节点ID
	 */
	@Transient
	private long nodeId;

	/**
	 * BDP字段，还不清楚用处
	 */
	@Transient
	private String assignLogic;

	/**
	 * BDP字段，业务对象ID
	 */
	@Transient
	private String boId;

	/**
	 * 任务创建时间
	 */
	@Transient
	private String taskCreateTime;

	/**
	 * 流程扩展实例ID
	 */
	@Transient
	private String extProcessId;

	/**
	 * 流程扩展实例ID
	 * 
	 * @return the extProcessId
	 */
	public String getExtProcessId()
	{
		return extProcessId;
	}

	/**
	 * 流程扩展实例ID
	 * 
	 * @param extProcessId
	 *            the extProcessId to set
	 */
	public void setExtProcessId(String extProcessId)
	{
		this.extProcessId = extProcessId;
	}

	/**
	 * 任务创建时间
	 * 
	 * @return the taskCreateTime
	 */
	public String getTaskCreateTime()
	{
		return taskCreateTime;
	}

	/**
	 * 任务创建时间
	 * 
	 * @param taskCreateTime
	 *            the taskCreateTime to set
	 */
	public void setTaskCreateTime(String taskCreateTime)
	{
		this.taskCreateTime = taskCreateTime;
	}

	/**
	 * 工作流类别 1：为信达旧系统流程， 2：为BDP系统流程。
	 * 
	 * @return the processType
	 */
	public String getProcessType()
	{
		return processType;
	}

	/**
	 * 工作流类别 1：为信达旧系统流程， 2：为BDP系统流程。
	 * 
	 * @param processType
	 *            the processType to set
	 */
	public void setProcessType(String processType)
	{
		this.processType = processType;
	}

	/**
	 * BDP字段，流程节点ID
	 * 
	 * @return the nodeId
	 */
	public long getNodeId()
	{
		return nodeId;
	}

	/**
	 * BDP字段，流程节点ID
	 * 
	 * @param nodeId
	 *            the nodeId to set
	 */
	public void setNodeId(long nodeId)
	{
		this.nodeId = nodeId;
	}

	/**
	 * @return the assignLogic
	 */
	public String getAssignLogic()
	{
		return assignLogic;
	}

	/**
	 * @param assignLogic
	 *            the assignLogic to set
	 */
	public void setAssignLogic(String assignLogic)
	{
		this.assignLogic = assignLogic;
	}

	/**
	 * BDP字段，业务对象ID
	 * 
	 * @return the boId
	 */
	public String getBoId()
	{
		return boId;
	}

	/**
	 * BDP字段，业务对象ID
	 * 
	 * @param boId
	 *            the boId to set
	 */
	public void setBoId(String boId)
	{
		this.boId = boId;
	}

	public CommonProcessInstance()
	{
	}

	public CommonProcessInstance(long processId, String businessRecordId, String modelId, String modelName, String processUrl, String creator, String createTime, long taskId, String taskName, Date startTime)
	{
		super();
		this.processId = processId;
		this.businessRecordId = businessRecordId;
		this.modelId = modelId;
		this.modelName = modelName;
		this.processUrl = processUrl;
		this.creator = creator;
		this.createTime = createTime;
		this.taskId = taskId;
		this.taskName = taskName;
		this.startTime = startTime;
	}

	public String getParentCommonProcessId()
	{
		return parentCommonProcessId;
	}

	public void setParentCommonProcessId(String parentCommonProcessId)
	{
		this.parentCommonProcessId = parentCommonProcessId;
	}

	public String getCommonProcessId()
	{
		return commonProcessId;
	}

	public void setCommonProcessId(String commonProcessId)
	{
		this.commonProcessId = commonProcessId;
	}

	public String getBusinessNote()
	{
		return businessNote;
	}

	public String getDepartmentId()
	{
		return departmentId;
	}

	public void setDepartmentId(String departmentId)
	{
		this.departmentId = departmentId;
	}

	public void setBusinessNote(String businessNote)
	{
		this.businessNote = businessNote;
	}

	/**
	 * taskInstanceId
	 * 
	 * @return
	 */
	@Transient
	public long getTaskId()
	{
		return taskId;
	}

	/**
	 * taskInstanceId
	 * 
	 * @param taskId
	 */
	public void setTaskId(long taskId)
	{
		this.taskId = taskId;
	}

	@Transient
	public String getTaskName()
	{
		return taskName;
	}

	public void setTaskName(String taskName)
	{
		this.taskName = taskName;
	}

	@Transient
	public Date getStartTime()
	{
		return startTime;
	}

	public void setStartTime(Date startTime)
	{
		this.startTime = startTime;
	}

	public long getProcessId()
	{
		return processId;
	}

	public void setProcessId(long processId)
	{
		this.processId = processId;
	}

	/**
	 * 业务记录ID
	 * 
	 * @return
	 */
	public String getBusinessRecordId()
	{
		return businessRecordId;
	}

	/**
	 * 业务记录ID
	 * 
	 * @param businessRecordId
	 */
	public void setBusinessRecordId(String businessRecordId)
	{
		this.businessRecordId = businessRecordId;
	}

	public String getModelId()
	{
		return modelId;
	}

	public void setModelId(String modelId)
	{
		this.modelId = modelId;
	}

	public String getModelName()
	{
		return modelName;
	}

	public void setModelName(String modelName)
	{
		this.modelName = modelName;
	}

	public String getProcessUrl()
	{
		return processUrl;
	}

	public void setProcessUrl(String processUrl)
	{
		this.processUrl = processUrl;
	}

	public String getCreator()
	{
		return creator;
	}

	public void setCreator(String creator)
	{
		this.creator = creator;
	}

	public String getCreateTime()
	{
		return createTime;
	}

	public void setCreateTime(String createTime)
	{
		this.createTime = createTime;
	}

	public String getExamineAndApprove()
	{
		return examineAndApprove;
	}

	public void setExamineAndApprove(String examineAndApprove)
	{
		this.examineAndApprove = examineAndApprove;
	}

	public String getWorkFlowState()
	{
		return workFlowState;
	}

	public void setWorkFlowState(String workFlowState)
	{
		this.workFlowState = workFlowState;
	}

	public String getInsCreateTime()
	{
		return insCreateTime;
	}

	public void setInsCreateTime(String insCreateTime)
	{
		this.insCreateTime = insCreateTime;
	}

	public String getInsEndTime()
	{
		return insEndTime;
	}

	public void setInsEndTime(String insEndTime)
	{
		this.insEndTime = insEndTime;
	}

	public String getEndNodeName()
	{
		return endNodeName;
	}

	public void setEndNodeName(String endNodeName)
	{
		this.endNodeName = endNodeName;
	}

}
