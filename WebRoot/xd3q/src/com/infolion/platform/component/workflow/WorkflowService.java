/*
 * @(#)WorkflowHandler.java
 * 版权声明： 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：张崇镇
 *  时　间：2008-12-12
 *  描　述：创建
 */

package com.infolion.platform.component.workflow;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.jbpm.JbpmContext;
import org.jbpm.JbpmException;
import org.jbpm.graph.def.ProcessDefinition;
import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.taskmgmt.exe.TaskInstance;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springmodules.workflow.jbpm31.JbpmCallback;
import org.springmodules.workflow.jbpm31.JbpmTemplate;

import com.infolion.platform.component.workflow.ext.CommonProcessInstance;
import com.infolion.platform.component.workflow.ext.CommonTaskInstance;
import com.infolion.platform.component.workflow.ext.CommonTransition;
import com.infolion.platform.component.workflow.ext.TaskInstanceContext;
import com.infolion.platform.console.org.domain.SysRole;
import com.infolion.platform.console.sys.context.UserContext;
import com.infolion.platform.console.sys.context.UserContextHolder;
import com.infolion.platform.core.BusinessException;
import com.infolion.platform.core.domain.ProcessObject;
import com.infolion.platform.sys.Constants;
import com.infolion.platform.util.CodeGenerator;
import com.infolion.platform.util.DateUtils;
import com.infolion.platform.util.EasyApplicationContextUtils;
import com.infolion.platform.util.StringUtils;

/**
 * 
 * <pre>
 * 工作流服务
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
public class WorkflowService
{
	/**
	 * 创建流程实例（保存）
	 * 
	 * @param domainObject
	 *            领域对象
	 * @param keyValue
	 *            领域对象关键字段值
	 */
	public static void createProcessInstance(final Object domainObject, final String keyValue)
	{
		final String deptId = UserContextHolder.getLocalUserContext().getUserContext().getSysDept().getDeptid();
		createProcessInstance(domainObject, keyValue, deptId);
	}

	/**
	 * 创建带部门id的流程实例（保存）
	 * 
	 * @param domainObject
	 *            领域对象
	 * @param keyValue
	 *            领域对象关键字段值
	 * @param departmentId
	 *            提交部门
	 */
	public static void createProcessInstance(final Object domainObject, final String keyValue, final String departmentId)
	{
		if (null == keyValue || "".equals(keyValue) || null == domainObject)
			throw new BusinessException("推动流程时的业务信息不完整，请检查id值或实体。");
		if (null == departmentId || "".equals(departmentId))
			throw new BusinessException("推动流程时的业务信息(推进部门id)不完整，请检查部门id值或实体。");
		final Log log = LogFactory.getLog("com.infolion.platform.component.workflow.WorkflowService");
		log.debug("调用流程实例创建服务开始.");
		JbpmTemplate jbpmTemplate = (JbpmTemplate) EasyApplicationContextUtils.getBeanByName("jbpmTemplate");
		final String userName = UserContextHolder.getLocalUserContext().getUserContext().getSysUser().getUserName();
		jbpmTemplate.execute(new JbpmCallback()
		{
			public Object doInJbpm(JbpmContext jbpmContext) throws JbpmException
			{

				ProcessObject processObject = (ProcessObject) domainObject;
				ProcessDefinition processDefinition = jbpmContext.getGraphSession().findLatestProcessDefinition(processObject.getWorkflowProcessName());

				Session session = jbpmContext.getSessionFactory().getCurrentSession();
				log.debug("在当前线程中成功获取session.");
				String commonProcessId = CodeGenerator.getUUID();
				CommonProcessInstance commonProcessInstance = new CommonProcessInstance();
				commonProcessInstance.setCommonProcessId(commonProcessId);
				commonProcessInstance.setBusinessRecordId(keyValue);
				commonProcessInstance.setModelId(processObject.getWorkflowModelId());
				commonProcessInstance.setModelName(processObject.getWorkflowModelName());
				commonProcessInstance.setProcessUrl(processObject.getWorkflowProcessUrl());
				//
				commonProcessInstance.setCreator(userName);
				commonProcessInstance.setCreateTime(DateUtils.getCurrTimeStr(DateUtils.DB_STORE_DATE));
				commonProcessInstance.setInsCreateTime(DateUtils.getCurrTimeStr(DateUtils.HYPHEN_DISPLAY_DATE));
				commonProcessInstance.setDepartmentId(departmentId);
				// 保存扩展流程实例
				session.save(commonProcessInstance);
				ProcessInstance processInstance = processDefinition.createProcessInstance();
				log.debug("流程实例创建成功.");
				commonProcessInstance.setProcessId(processInstance.getId());
				session.update(commonProcessInstance);
				log.debug("成功保存流程扩展实例.");
				// 保存用户自定义变量
				Map processVariableMap = processObject.getWorkflowUserDefineProcessVariable();
				if (null == processVariableMap)
					processVariableMap = new HashMap();
				processVariableMap.put(Constants.WORKFLOW_USER_KEY_VALUE, keyValue);
				processVariableMap.put(Constants.WORKFLOW_COMMON_PROCESS_ID, commonProcessId);
				if (null != processVariableMap)
				{
					Set keySet = processVariableMap.keySet();
					for (Iterator iterator = keySet.iterator(); iterator.hasNext();)
					{
						String variableName = (String) iterator.next();
						Object variableValue = processVariableMap.get(variableName);
						processInstance.getContextInstance().setVariable(variableName, variableValue);
					}
				}
				log.debug("保存用户自定义变量成功.");

				return null;
			}
		});
		log.debug("调用流程实例创建服务成功结束.");
	}

	/**
	 * 创建并推进流程（保存并提交）
	 * 
	 * @param domainObject
	 *            领域对象
	 * @param keyValue
	 *            领域对象关键字段值
	 */
	public static void createAndSignalProcessInstance(final Object domainObject, final String keyValue)
	{
		final String departmentId = UserContextHolder.getLocalUserContext().getUserContext().getSysDept().getDeptid();
		createAndSignalProcessInstance(domainObject, keyValue, departmentId);
	}

	/**
	 * 创建带部门id并推进流程（保存并提交）
	 * 
	 * @param domainObject
	 *            领域对象
	 * @param keyValue
	 *            领域对象关键字段值
	 * @param departmentId
	 *            部门id
	 */
	public static void createAndSignalProcessInstance(final Object domainObject, final String keyValue, final String departmentId)
	{
		if (null == keyValue || "".equals(keyValue) || null == domainObject)
			throw new BusinessException("推动流程时的业务信息不完整，请检查id值或实体。");
		final Log log = LogFactory.getLog("com.infolion.platform.component.workflow.WorkflowService");
		log.debug("调用流程实例创建并推动服务开始.");
		JbpmTemplate jbpmTemplate = (JbpmTemplate) EasyApplicationContextUtils.getBeanByName("jbpmTemplate");
		final String userName = UserContextHolder.getLocalUserContext().getUserContext().getSysUser().getUserName();
		jbpmTemplate.execute(new JbpmCallback()
		{
			public Object doInJbpm(JbpmContext jbpmContext) throws JbpmException
			{
				Session session = jbpmContext.getSessionFactory().getCurrentSession();
				log.debug("在当前线程中成功获取session.");
				ProcessObject processObject = (ProcessObject) domainObject;
				ProcessDefinition processDefinition = jbpmContext.getGraphSession().findLatestProcessDefinition(processObject.getWorkflowProcessName());
				if (null == processDefinition)
					throw new BusinessException("流程定义文件未找到，请确认。");
				// 工作流流程实例表
				String commonProcessId = CodeGenerator.getUUID();
				CommonProcessInstance commonProcessInstance = new CommonProcessInstance();
				commonProcessInstance.setCommonProcessId(commonProcessId);
				commonProcessInstance.setBusinessRecordId(keyValue);
				commonProcessInstance.setModelId(processObject.getWorkflowModelId());
				commonProcessInstance.setModelName(processObject.getWorkflowModelName());
				commonProcessInstance.setProcessUrl(processObject.getWorkflowProcessUrl());

				//
				commonProcessInstance.setCreator(userName);
				commonProcessInstance.setCreateTime(DateUtils.getCurrTimeStr(DateUtils.DB_STORE_DATE));
				commonProcessInstance.setInsCreateTime(DateUtils.getCurrTimeStr(DateUtils.HYPHEN_DISPLAY_DATE));
				// 业务信息描述
				commonProcessInstance.setBusinessNote(processObject.getWorkflowBusinessNote());
				commonProcessInstance.setDepartmentId(departmentId);
				// 保存扩展流程实例
				session.save(commonProcessInstance);
				ProcessInstance processInstance = processDefinition.createProcessInstance();
				log.debug("流程实例创建成功.");
				commonProcessInstance.setProcessId(processInstance.getId());
				session.update(commonProcessInstance);
				log.debug("成功保存流程扩展实例.");
				// 写入业务记录ｉｄ
				// 保存用户自定义流程变量
				Map processVariableMap = processObject.getWorkflowUserDefineProcessVariable();
				if (null == processVariableMap)
					processVariableMap = new HashMap();
				processVariableMap.put(Constants.WORKFLOW_USER_KEY_VALUE, keyValue);
				processVariableMap.put(Constants.WORKFLOW_COMMON_PROCESS_ID, commonProcessId);
				// processVariableMap.putAll(processObject
				// .getWorkflowUserDefineProcessVariable());
				if (null != processVariableMap)
				{
					Set keySet = processVariableMap.keySet();
					for (Iterator iterator = keySet.iterator(); iterator.hasNext();)
					{
						String variableName = (String) iterator.next();
						Object variableValue = processVariableMap.get(variableName);
						processInstance.getContextInstance().setVariable(variableName, variableValue);
					}
				}
				TaskInstance taskInstance = processInstance.getTaskMgmtInstance().createStartTaskInstance();
				if (null != taskInstance)
				{
					taskInstance.setVariable(Constants.WORKFLOW_IS_ALLOW, processObject.getWorkflowIsAllow());
					taskInstance.setStart(new Date());
					taskInstance.getToken().signal();
					// 保存用户自定义任务变量
					Map taskVariableMap = processObject.getWorkflowUserDefineTaskVariable();
					if (null != taskVariableMap)
					{
						Set keySet = taskVariableMap.keySet();
						for (Iterator iterator = keySet.iterator(); iterator.hasNext();)
						{
							String variableName = (String) iterator.next();
							Object variableValue = taskVariableMap.get(variableName);
							taskInstance.setVariable(variableName, variableValue);
						}
					}
					log.debug("保存用户自定义变量成功.");
				}
				else
				{
					processInstance.signal();
					log.debug("流程实例推动成功.");
				}
				return null;
			}
		});
		log.debug("调用流程实例创建并推动服务成功结束.");
	}

	/**
	 * 推进流程实例
	 * 
	 * @param domainObject
	 *            领域对象
	 * @param keyValue
	 *            领域对象关键字段值
	 * @param transitionName
	 *            迁移名称
	 */
	public static void signalProcessInstance(final Object domainObject, final String keyValue, final String transitionName)
	{
		if (null == keyValue || "".equals(keyValue) || null == domainObject)
			throw new BusinessException("推动流程时的业务信息不完整，请检查id值或实体。");
		final Log log = LogFactory.getLog("com.infolion.platform.component.workflow.WorkflowService");
		log.debug("调用流程实例推动服务开始.");
		ProcessObject processObject = (ProcessObject) domainObject;
		log.debug("判断当前用户是否有推进该节点的权限.");
		if (!isCurrentActor(processObject.getWorkflowTaskId()))
			throw new BusinessException("其他人员已经完成了该任务的处理，请检查！");
		JbpmTemplate jbpmTemplate = (JbpmTemplate) EasyApplicationContextUtils.getBeanByName("jbpmTemplate");
		final String userName = UserContextHolder.getLocalUserContext().getUserContext().getSysUser().getUserName();
		final String userCnName = UserContextHolder.getLocalUserContext().getUserContext().getSysUser().getRealName();
		final String deptName = UserContextHolder.getLocalUserContext().getUserContext().getSysDept().getDeptname();

		jbpmTemplate.execute(new JbpmCallback()
		{
			public Object doInJbpm(JbpmContext jbpmContext) throws JbpmException
			{
				Session session = jbpmContext.getSessionFactory().getCurrentSession();
				log.debug("在当前线程中成功获取session.");
				ProcessObject processObject = (ProcessObject) domainObject;

				String leaveTransitionName = StringUtils.urlEncode(processObject.getWorkflowLeaveTransitionName());
				TaskInstance taskInstance = jbpmContext.getTaskInstance(Long.parseLong(processObject.getWorkflowTaskId()));
				CommonTaskInstance commonTaskInstance = new CommonTaskInstance();
				commonTaskInstance.setTaskHisId(CodeGenerator.getUUID());
				commonTaskInstance.setBusinessRecordId(keyValue);
				commonTaskInstance.setCreateTime(DateUtils.getCurrTimeStr(DateUtils.DB_STORE_DATE));
				commonTaskInstance.setCreator(userName);
				commonTaskInstance.setExamine(StringUtils.urlEncode(processObject.getWorkflowExamine()));
				commonTaskInstance.setExamineDeptName(deptName);
				commonTaskInstance.setExaminePerson(userCnName);
				commonTaskInstance.setExamineResult(leaveTransitionName);

				// 保存扩展任务实例
				session.save(commonTaskInstance);

				taskInstance.setVariable(Constants.WORKFLOW_IS_ALLOW, processObject.getWorkflowIsAllow());
				// 保存用户自定义任务变量
				Map taskVariableMap = processObject.getWorkflowUserDefineTaskVariable();
				if (null != taskVariableMap)
				{
					Set keySet = taskVariableMap.keySet();
					for (Iterator iterator = keySet.iterator(); iterator.hasNext();)
					{
						String variableName = (String) iterator.next();
						Object variableValue = taskVariableMap.get(variableName);
						taskInstance.setVariable(variableName, variableValue);
					}
				}
				// 如果指定迁移
				if (null != transitionName && !"".equals(transitionName))
				{
					taskInstance.setEnd(new Date());
					taskInstance.getToken().signal(transitionName);
				}
				// 如果页面传入指定迁移
				else if (null != leaveTransitionName
						&& !"".equals(leaveTransitionName))
				{
					taskInstance.setEnd(new Date());
					if (null == taskInstance.getToken())
						throw new BusinessException("任务("
								+ taskInstance.getName()
								+ ")不存在一个合法token，流程推进失败。");
					taskInstance.getToken().signal(leaveTransitionName);
				}
				else
				{
					boolean isEnd = taskInstance.getProcessInstance().hasEnded();
					if (isEnd)
					{
						System.out.println("流程："
								+ taskInstance.getProcessInstance().getProcessDefinition().getName()
								+ "已结束！");
						throw new BusinessException("流程："
								+ taskInstance.getProcessInstance().getProcessDefinition().getName()
								+ "已结束！");
					}
					else
					{
						taskInstance.setEnd(new Date());
						taskInstance.getToken().signal();
					}
				}
				log.debug("任务实例推动成功.");
				commonTaskInstance.setProcessId(taskInstance.getProcessInstance().getId());
				commonTaskInstance.setTaskCreateTime(DateUtils.getTimeStr(taskInstance.getCreate(), DateUtils.HYPHEN_DISPLAY_DATE));
				commonTaskInstance.setTaskDescription(taskInstance.getDescription());
				commonTaskInstance.setTaskEndTime(DateUtils.getTimeStr(taskInstance.getEnd(), DateUtils.HYPHEN_DISPLAY_DATE));
				commonTaskInstance.setTaskId(taskInstance.getId());
				commonTaskInstance.setTaskName(taskInstance.getName());
				session.update(commonTaskInstance);
				deleteWfProcessUser(taskInstance.getId());
				log.debug("扩展任务实例成功保存.");
				return null;
			}
		});
		log.debug("调用流程实例推动服务成功结束.");
	}

	/**
	 * 更新流程实例的状态为结束
	 * 
	 * @param processId
	 */
	public static void updateProcessInstanceEndState(long processId, String endNodeName)
	{
		JdbcTemplate jdbcTemplate = (JdbcTemplate) EasyApplicationContextUtils.getBeanByName("jdbcTemplate");
		jdbcTemplate.update("update t_sys_wf_process_instance set ins_end_time='"
				+ DateUtils.getCurrTimeStr(DateUtils.HYPHEN_DISPLAY_DATE)
				+ "',end_node_name='"
				+ endNodeName
				+ "' where process_id="
				+ String.valueOf(processId));
	}
	/**
	 * 删除物化视图中的任务实例
	 * @param taskId
	 */
	public static void deleteWfProcessUser(long taskId)
	{
		JdbcTemplate jdbcTemplate = (JdbcTemplate) EasyApplicationContextUtils.getBeanByName("jdbcTemplate");
		try{
			jdbcTemplate.execute("delete V_SYS_WF_PROCESS_USER where TASK_ID="+taskId);
		}catch(Exception ex){}
	}
	

	/**
	 * 取得当前未结束流程实例(待办事项)
	 * 
	 * @return
	 */
	public static List<CommonProcessInstance> getMyProcessInstances()
	{
		String actorId = getUserAllActorId();
		JdbcTemplate jdbcTemplate = (JdbcTemplate) EasyApplicationContextUtils.getBeanByName("jdbcTemplate");
		final List<CommonProcessInstance> result = new ArrayList<CommonProcessInstance>();
		String sql = "select * from V_SYS_WF_PROCESS_USER where ACTOR_ID in ("
				+ actorId + ")";
		// System.out.println(sql);
		jdbcTemplate.query(sql, new RowCallbackHandler()
		{
			public void processRow(ResultSet rs) throws SQLException
			{
				CommonProcessInstance commonProcessInstance = new CommonProcessInstance();
				commonProcessInstance.setBusinessRecordId(rs.getString("BUSINESS_RECORD_ID"));
				commonProcessInstance.setProcessUrl(rs.getString("PROCESS_URL"));
				commonProcessInstance.setModelId(rs.getString("MODEL_ID"));
				commonProcessInstance.setModelName(rs.getString("MODEL_NAME"));
				commonProcessInstance.setProcessId(rs.getLong("PROCESS_ID"));
				commonProcessInstance.setStartTime(rs.getDate("START_"));
				commonProcessInstance.setTaskId(rs.getLong("TASK_ID"));
				commonProcessInstance.setTaskName(rs.getString("NAME_"));
				commonProcessInstance.setExamineAndApprove("审批");
				commonProcessInstance.setWorkFlowState("流程状态");
				result.add(commonProcessInstance);
			}
		});
		return result;
	}

	/**
	 * 判断当前用户是否有执行该节点的权限
	 * 
	 * @return
	 */
	private static boolean isCurrentActor(String taskId)
	{
		String actorId = getUserAllActorId();
		JdbcTemplate jdbcTemplate = (JdbcTemplate) EasyApplicationContextUtils.getBeanByName("jdbcTemplate");
		String sql = "select end_ from jbpm_taskinstance where id_=" + taskId;
		Object o = jdbcTemplate.queryForObject(sql, String.class);
		if (o==null)
			return true;
		else
			return false;
	}

	/**
	 * 取得任务节点历史
	 * 
	 * @param taskId
	 * @return
	 */
	public static List<CommonTaskInstance> getTaskInstancesHistory(final String businessRecordId)
	{
		final List<CommonTaskInstance> result = new ArrayList();
		JdbcTemplate jdbcTemplate = (JdbcTemplate) EasyApplicationContextUtils.getBeanByName("jdbcTemplate");
		String sql = "select * from V_SYS_WF_TASK_HISTORY where BUSINESS_RECORD_ID=?";
		jdbcTemplate.query(sql, new Object[] { businessRecordId }, new RowCallbackHandler()
		{
			public void processRow(ResultSet rs) throws SQLException
			{
				CommonTaskInstance commonTaskInstance = new CommonTaskInstance();
				commonTaskInstance.setBusinessRecordId(businessRecordId);
				commonTaskInstance.setCreator(rs.getString("CREATOR"));
				commonTaskInstance.setExamine(rs.getString("EXAMINE"));
				commonTaskInstance.setExaminePerson(rs.getString("EXAMINE_PERSON"));
				commonTaskInstance.setExamineDeptName(rs.getString("EXAMINE_DEPT_NAME"));
				commonTaskInstance.setExamineResult(rs.getString("EXAMINE_RESULT"));
				commonTaskInstance.setProcessId(rs.getLong("PROCESS_ID"));
				commonTaskInstance.setTaskCreateTime(rs.getString("TASK_CREATE_TIME"));
				commonTaskInstance.setTaskDescription(rs.getString("TASK_DESCRIPTION"));
				commonTaskInstance.setTaskEndTime(rs.getString("TASK_END_TIME"));
				commonTaskInstance.setTaskId(rs.getLong("TASK_ID"));
				commonTaskInstance.setTaskName(rs.getString("TASK_NAME"));
				result.add(commonTaskInstance);
			}
		});
		return result;
	}

	/**
	 * 根据当前任务实例id，取得当前任务实例上下文
	 * 
	 * @param businessRecordId
	 * @param taskInstanceId
	 * @return
	 */
	public static TaskInstanceContext getTaskInstanceContext(String businessRecordId, String taskInstanceId)
	{
		UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
		if (null == userContext)
			return null;
		TaskInstanceContext taskInstanceContext = getTaskInstanceLeaveTransitions(taskInstanceId);
		taskInstanceContext.setHistoryTasks(getTaskInstancesHistory(businessRecordId));
		taskInstanceContext.setCurrentActor(userContext.getSysUser().getRealName());
		return taskInstanceContext;
	}

	/**
	 * 取得当前任务上下文信息，不包含任务历史
	 * 
	 * @param taskInstanceId
	 * @return
	 */
	public static TaskInstanceContext getTaskInstanceContext(String taskInstanceId)
	{
		UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
		if (null == userContext)
			return null;
		TaskInstanceContext taskInstanceContext = getTaskInstanceLeaveTransitions(taskInstanceId);
		taskInstanceContext.setCurrentActor(userContext.getSysUser().getRealName());
		return taskInstanceContext;
	}

	/**
	 * 子流程创建
	 * 
	 * @param executionContext
	 */
	public static void createSubProcess(ExecutionContext executionContext)
	{
		if (null == executionContext)
			throw new BusinessException("没有子流程创建的执行环境，请检查配置。");
		final Log log = LogFactory.getLog("com.infolion.platform.component.workflow.WorkflowService");
		log.debug("子流程创建扩展服务开始.");
		final String commonProcessId = (String) executionContext.getContextInstance().getVariable(Constants.WORKFLOW_COMMON_PROCESS_ID);
		if (null != commonProcessId)
			log.debug("成功取得扩展流程实例Id:" + commonProcessId);
		else
			throw new BusinessException("无法取得扩展流程实例Id，请检查。");
		// 子流程实例id
		final String subCommonProcessId = CodeGenerator.getUUID();
		final long subProcessId = executionContext.getToken().getSubProcessInstance().getId();
		if (0 != subProcessId)
			log.debug("成功取得子流程实例Id:" + subProcessId);
		else
			throw new BusinessException("无法取得子流程实例Id，请检查。");
		JbpmTemplate jbpmTemplate = (JbpmTemplate) EasyApplicationContextUtils.getBeanByName("jbpmTemplate");
		jbpmTemplate.execute(new JbpmCallback()
		{
			public Object doInJbpm(JbpmContext jbpmContext) throws JbpmException
			{
				Session session = jbpmContext.getSessionFactory().getCurrentSession();
				log.debug("在当前线程中成功获取session.");
				CommonProcessInstance parentCommonProcessInstance = (CommonProcessInstance) session.get(CommonProcessInstance.class, commonProcessId);
				log.debug("成功获取父扩展流程实例."
						+ parentCommonProcessInstance.getProcessId());
				CommonProcessInstance commonProcessInstance = new CommonProcessInstance();
				try
				{
					BeanUtils.copyProperties(commonProcessInstance, parentCommonProcessInstance);
				}
				catch (Exception e)
				{
					throw new BusinessException("扩展流程实例拷贝错误.");
				}
				// 标志正在执行子流程 为正在执行标记
				commonProcessInstance.setCommonProcessId(subCommonProcessId);
				commonProcessInstance.setParentCommonProcessId(commonProcessId);
				commonProcessInstance.setProcessId(subProcessId);
				session.update(parentCommonProcessInstance);
				session.save(commonProcessInstance);
				// trans.commit();
				log.debug("成功创建子流程扩展流程实例:" + subCommonProcessId);
				return null;
			}
		});
		executionContext.getContextInstance().setVariable(Constants.WORKFLOW_SUB_COMMON_PROCESS_ID, subCommonProcessId);
		log.debug("子流程创建扩展服务成功结束，并将扩展子流程实例ID放入"
				+ Constants.WORKFLOW_SUB_COMMON_PROCESS_ID + "变量中.");
	}

	/**
	 * 子流程结束
	 * 
	 * @param executionContext
	 */
	public static void endSubProcess(ExecutionContext executionContext)
	{
		if (null == executionContext)
			throw new BusinessException("没有子流程创建的执行环境，请检查配置。");
		final Log log = LogFactory.getLog("com.infolion.platform.component.workflow.WorkflowService");
		log.debug("子流程结束扩展服务开始.");
		final String commonProcessId = (String) executionContext.getContextInstance().getVariable(Constants.WORKFLOW_COMMON_PROCESS_ID);
		final String subCommonProcessId = (String) executionContext.getContextInstance().getVariable(Constants.WORKFLOW_SUB_COMMON_PROCESS_ID);
		if (null != commonProcessId)
			log.debug("成功取得扩展流程实例Id:" + commonProcessId);
		else
			throw new BusinessException("无法取得扩展流程实例Id，请检查。");
		if (null != subCommonProcessId)
			log.debug("成功取得子扩展流程实例Id:" + subCommonProcessId);
		else
			throw new BusinessException("无法取得子扩展流程实例Id，请检查。");
		JbpmTemplate jbpmTemplate = (JbpmTemplate) EasyApplicationContextUtils.getBeanByName("jbpmTemplate");
		jbpmTemplate.execute(new JbpmCallback()
		{
			public Object doInJbpm(JbpmContext jbpmContext) throws JbpmException
			{
				Session session = jbpmContext.getSessionFactory().getCurrentSession();
				log.debug("在当前线程中成功获取session.");
				CommonProcessInstance parentCommonProcessInstance = (CommonProcessInstance) session.get(CommonProcessInstance.class, commonProcessId);
				CommonProcessInstance commonProcessInstance = (CommonProcessInstance) session.get(CommonProcessInstance.class, subCommonProcessId);
				log.debug("成功获取扩展流程实例.");
				// 标志正在执行子流程 为结束标记
				commonProcessInstance.setInsEndTime(DateUtils.getCurrTimeStr(DateUtils.HYPHEN_DISPLAY_DATE));
				commonProcessInstance.setEndNodeName(parentCommonProcessInstance.getModelName()
						+ "子流程结束");
				session.update(parentCommonProcessInstance);
				session.update(commonProcessInstance);
				// trans.commit();
				log.debug("成功更新子流程扩展流程实例.");
				return null;
			}
		});
		log.debug("子流程结束扩展服务成功结束.");
	}

	/**
	 * 取得当前任务节点的离开迁移集合
	 * 
	 * @param taskInstanceId
	 * @return
	 */
	private static TaskInstanceContext getTaskInstanceLeaveTransitions(String taskInstanceId)
	{
		String sql = "select a.id_,a.name_,b.name_ next_transition_name,b.id_ transition_id from jbpm_taskinstance a,jbpm_transition b,jbpm_task c where a.task_=c.id_ and c.tasknode_ = b.from_ and a.id_=? order by b.fromindex_ asc";
		final TaskInstanceContext taskInstanceContext = new TaskInstanceContext();
		final List<CommonTransition> transitions = new ArrayList();
		JdbcTemplate jdbcTemplate = (JdbcTemplate) EasyApplicationContextUtils.getBeanByName("jdbcTemplate");
		jdbcTemplate.query(sql, new Object[] { taskInstanceId }, new RowCallbackHandler()
		{
			public void processRow(ResultSet rs) throws SQLException
			{
				taskInstanceContext.setTaskName(rs.getString("name_"));
				CommonTransition commonTransition = new CommonTransition();
				commonTransition.setTransitionId(rs.getString("transition_id"));
				commonTransition.setTransitionName(rs.getString("next_transition_name"));
				transitions.add(commonTransition);
			}
		});
		taskInstanceContext.setTransitions(transitions);
		return taskInstanceContext;
	}

	/**
	 * 取得用户所拥有的ActorId(包括个人id，和角色id)
	 * 
	 * @return
	 */
	private static String getUserAllActorId()
	{
		UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
		if (null == userContext)
			return null;
		List<SysRole> list = userContext.getGrantedRoles();
		StringBuffer sb = new StringBuffer();
		for (Iterator iterator = list.iterator(); iterator.hasNext();)
		{
			SysRole sysRole = (SysRole) iterator.next();
			sb.append("'");
			sb.append(sysRole.getRoleId());
			sb.append("'");
			sb.append(",");
		}
		sb.append("'");
		sb.append(userContext.getSysUser().getUserId());
		sb.append("'");
		return sb.toString();
	}
}
