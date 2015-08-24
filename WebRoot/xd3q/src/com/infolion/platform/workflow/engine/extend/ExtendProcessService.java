/**
 * @(#) ExtendProcessService.java
 * 版权声明： 福建讯盟软件有限公司, 版权所有 违者必究
 * 修订记录:
 *  1)更改者：MagicDraw V16
 *    时　间：May 12, 2009 3:00:57 PM
 *    描　述：创建
 */

package com.infolion.platform.workflow.engine.extend;

import java.io.Reader;
import java.io.StringReader;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jbpm.JbpmContext;
import org.jbpm.graph.def.Action;
import org.jbpm.graph.def.ProcessDefinition;
import org.jbpm.graph.def.Transition;
import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.exe.Token;
import org.jbpm.instantiation.Delegation;
import org.jbpm.jpdl.el.ELException;
import org.jbpm.jpdl.el.impl.BinaryOperatorExpression;
import org.jbpm.jpdl.el.impl.ExpressionString;
import org.jbpm.jpdl.el.impl.JbpmExpressionEvaluator;
import org.jbpm.jpdl.el.impl.NamedValue;
import org.jbpm.jpdl.el.parser.ELParser;
import org.jbpm.jpdl.el.parser.ParseException;
import org.jbpm.taskmgmt.def.Task;
import org.jbpm.taskmgmt.def.TaskController;
import org.jbpm.taskmgmt.exe.TaskInstance;
import org.springframework.jdbc.core.JdbcTemplate;

import com.infolion.platform.basicApp.authManage.UserContextHolder;
import com.infolion.platform.basicApp.authManage.dao.UserJdbcDao;
import com.infolion.platform.basicApp.authManage.dao.UserPersonnelJdbcDao;
import com.infolion.platform.basicApp.authManage.domain.UserContext;
import com.infolion.platform.bo.domain.BusinessObject;
import com.infolion.platform.bo.domain.Method;
import com.infolion.platform.bo.domain.MethodParameter;
import com.infolion.platform.bo.domain.Relationship;
import com.infolion.platform.bo.service.BusinessObjectService;
import com.infolion.platform.codeGenerator.util.BeanNameUtil;
import com.infolion.platform.codeGenerator.util.BeanPostFix;
import com.infolion.platform.dpframework.core.Constants;
import com.infolion.platform.dpframework.core.domain.BaseObject;
import com.infolion.platform.dpframework.mail.MailContent;
import com.infolion.platform.dpframework.mail.MailFactory;
import com.infolion.platform.dpframework.mail.MailService;
import com.infolion.platform.dpframework.outsideInterface.OutsidePersistenceService;
import com.infolion.platform.dpframework.uicomponent.systemMessage.SysMsgConstants;
import com.infolion.platform.dpframework.util.AssertUtil;
import com.infolion.platform.dpframework.util.BeanUtils;
import com.infolion.platform.dpframework.util.CodeGenerator;
import com.infolion.platform.dpframework.util.DateUtils;
import com.infolion.platform.dpframework.util.EasyApplicationContextUtils;
import com.infolion.platform.dpframework.util.StringUtils;
import com.infolion.platform.workflow.WorkFlowConstants;
import com.infolion.platform.workflow.engine.WorkflowCanRedirectedException;
import com.infolion.platform.workflow.engine.WorkflowException;
import com.infolion.platform.workflow.engine.WorkflowService;
import com.infolion.platform.workflow.engine.dao.DynamicTaskActorHibernateDao;
import com.infolion.platform.workflow.engine.dao.DynamicTaskActorJdbcDao;
import com.infolion.platform.workflow.engine.dao.ExtendProcessInstanceHibernateDao;
import com.infolion.platform.workflow.engine.dao.ProcessDefHibernateDao;
import com.infolion.platform.workflow.engine.dao.ProcessJdbcDao;
import com.infolion.platform.workflow.engine.dao.TaskActorConditionHibernateDao;
import com.infolion.platform.workflow.engine.domain.BusinessProcessDefinition;
import com.infolion.platform.workflow.engine.domain.DynamicTaskActor;
import com.infolion.platform.workflow.engine.domain.ExtendNodeDefinition;
import com.infolion.platform.workflow.engine.domain.ExtendProcessDefinition;
import com.infolion.platform.workflow.engine.domain.ExtendProcessInstance;
import com.infolion.platform.workflow.engine.domain.ExtendTransition;
import com.infolion.platform.workflow.engine.domain.NodeInfo;
import com.infolion.platform.workflow.engine.domain.TaskActor;
import com.infolion.platform.workflow.engine.domain.TaskActorCondition;
import com.infolion.platform.workflow.manager.dao.BoProcessDefinitionDao;
import com.infolion.platform.workflow.manager.dao.ExtNodeDefinitionDao;
import com.infolion.platform.workflow.manager.dao.ExtendTransitionDao;
import com.infolion.platform.workflow.manager.dao.TaskActorDao;
import com.infolion.platform.workflow.manager.service.BoProcessDefinitionService;
import com.infolion.platform.workflow.manager.service.ExtNodeDefinitionService;
import com.infolion.platform.workflow.manager.service.ExtProcessDefinitionService;

/**
 * 
 * <pre>
 * 扩展流程服务，完成与jbpm流转相关的服务
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
public class ExtendProcessService
{
	// 统一的用于存放流程当前状态的字段名
	public static final String COMMON_PROCESS_ACTION_COLUMN = "processState";
	public static final String COMMON_PROCESS_ACTION_COLUMN_NO_CASE = "processstate";
	private static Log log = LogFactory.getLog("com.infolion.platform.workflow.extend.ExtendProcessService");

	/**
	 * 根据流程业务对象，取得当前活动的流程定义版本
	 * 
	 * @param processName
	 * @return
	 */
	public static ProcessDefinition getActiveProcessDefinition(BusinessProcessDefinition businessProcessDefinition, JbpmContext jbpmContext)
	{
		ProcessDefHibernateDao processDefHibernateDao = (ProcessDefHibernateDao) EasyApplicationContextUtils.getBeanByName("processDefHibernateDao");

		String processDefinitionName = businessProcessDefinition.getProcessDefinitionName();
		List<ExtendProcessDefinition> list = processDefHibernateDao.getActiveProcessDefinition(processDefinitionName);
		ExtendProcessDefinition extendProcessDefinition = new ExtendProcessDefinition();
		if (null == list || list.size() == 0)
		{
			// throw new WorkflowException("不存在流程【" + processDefinitionName +
			// "】的激活版本，请检查！");
			throw new WorkflowException(SysMsgConstants.MSG_CLASS_DP, WorkflowException.NoActiveProcessDefinitionFound, processDefinitionName);
		}
		else if (list.size() > 1)
		{
			// throw new WorkflowException("流程：" + processDefinitionName +
			// "存在多个激活版本，请检查！");
			throw new WorkflowException(SysMsgConstants.MSG_CLASS_DP, WorkflowException.NotUniqueActiveProcessDefinition, processDefinitionName);
		}
		else if (list.size() == 1)
			extendProcessDefinition = list.get(0);
		else
			return null;
		// 取得特定版本的流程定义
		int version = Integer.parseInt(extendProcessDefinition.getVersion());
		ProcessDefinition processDefinition = jbpmContext.getGraphSession().findProcessDefinition(processDefinitionName, version);
		return processDefinition;
	}

	/**
	 * 取得业务对象名关联的业务对象流程配置信息
	 * 
	 * @param boName
	 * @return
	 */
	public static BusinessProcessDefinition getDomainRefProcessDefinition(BusinessObject businessObject, Object domainObject)
	{

		BoProcessDefinitionDao boProcessDefinitionDao = (BoProcessDefinitionDao) EasyApplicationContextUtils.getBeanByName("boProcessDefinitionDao");
		String boId = businessObject.getBoId();
		String organizationIdFieldName = BeanUtils.getOrganizationIdFieldName(domainObject);
		String organizationId = "";
		if (!StringUtils.isNullBlank(organizationIdFieldName))
		{
			organizationId = BeanUtils.getOrganizationIdValue(domainObject, organizationIdFieldName);
		}
		BusinessProcessDefinition businessProcessDefinition = new BusinessProcessDefinition();
		// 取得业务流程信息
		Map<String, BusinessProcessDefinition> bpdefs = boProcessDefinitionDao._get(boId);
		if (bpdefs == null || bpdefs.size() == 0)
		{
			throw new WorkflowException(SysMsgConstants.MSG_CLASS_DP, WorkflowException.NoProcessDefFoundRelateToBO, businessObject.getDescription(), businessObject.getBoName());
		}
		else if (bpdefs.size() >= 1)
		{
			// 如果组织ID为空，则寻找流程定义中组织ID为空的记录，如果找不到则寻找默认流程定义。默认流程定义不存在则提示无流程定义。
			if (StringUtils.isNullBlank(organizationId))
			{
				businessProcessDefinition = bpdefs.get(" ");
				if (null == businessProcessDefinition)
					businessProcessDefinition = bpdefs.get(null);
			}
			else
			{
				businessProcessDefinition = bpdefs.get(organizationId);
			}
			if (null == businessProcessDefinition)
				businessProcessDefinition = bpdefs.get(WorkFlowConstants.DefaultWorkFlowDef);

			// 找不到流程定义
			if (null == businessProcessDefinition)
				throw new WorkflowException(SysMsgConstants.MSG_CLASS_DP, WorkflowException.NoProcessDefFoundRelateToBO, businessObject.getDescription(), businessObject.getBoName());

			// throw new WorkflowException(SysMsgConstants.MSG_CLASS_DP,
			// WorkflowException.NotUniqueProcessDefRelateToBO,
			// businessObject.getDescription(), businessObject.getBoName());
		}
		// else if (bpdefs.size() == 1)
		// businessProcessDefinition = bpdefs.get(0);

		return businessProcessDefinition;
	}

	/**
	 * 取得领域对象所对应的业务对象元数据
	 * 
	 * @param domainObject
	 * @return
	 */
	public static BusinessObject getDomainRefBusinessObject(Object domainObject)
	{
		// 以领域对象的类名，作为业务对象名
		String boName = domainObject.getClass().getSimpleName();
		// 取得该实体类对应的业务对象元数据
		BusinessObject businessObject = BusinessObjectService.getBusinessObject(boName);
		// 不存在业务对象元数据
		if (null == businessObject)
		{
			// throw new WorkflowException("业务对象【" + boName + "】不存在配置信息，请检查！");
			throw new WorkflowException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBusinessObjectFound, boName);
		}
		return businessObject;
	}

	/**
	 * 获取是否签名标识 20141124 add by cat
	 * 
	 * @param taskStrId
	 * @return
	 */
	public static String isNeedSignature(String taskStrId)
	{		
		return "N";
	}
	
	/**
	 * 取得业务对象中，用于查看待办事项的所有方法的入口
	 * 
	 * @param businessObject
	 * @return
	 */
	public static String getEntryProcessUrl(BusinessObject businessObject)
	{
		String url = null;
		String boName = businessObject.getBoName();
		String modelPath = businessObject.getAppModel();
		String controllerPath = businessObject.getJavaPath();
		String boInsName = BeanNameUtil.getBeanInstanceName(boName);
		url = modelPath + "/" + controllerPath + "/" + boInsName + BeanPostFix.POSTFIX_CONTROLLER + ".spr?action=_entryProcess";
		return url;
	}

	/**
	 * 设置当前流程审批状态
	 * 
	 * @param domainObject
	 * @param currentAction
	 */
	public static void setCurrentProcessAction(Object domainObject, String currentAction)
	{
		log.debug("设置当前流程审批状态，currentAction：" + currentAction);
		try
		{
			BeanUtils.setProperty(domainObject, COMMON_PROCESS_ACTION_COLUMN, currentAction);
			BeanUtils.setProperty(domainObject, COMMON_PROCESS_ACTION_COLUMN_NO_CASE, currentAction);
		}
		catch (Exception e)
		{
			// throw new
			// WorkflowException("启用工作流的业务对象中，必须存在processState字段用于存放当前流程审批状态。");
			throw new WorkflowException(SysMsgConstants.MSG_CLASS_DP, WorkflowException.SetCurrentProcessActionExcetpion);
		}
	}

	/**
	 * 取得子流程，业务ID流程变量。
	 * 
	 * @param businessObject
	 * @param domainObject
	 * @param taskInstance
	 * @return
	 */
	public static Map getSubWorkFlowBOBusinessIdVariable(BusinessObject businessObject, Object domainObject, TaskInstance taskInstance)
	{
		log.debug("进入业务对象【" + businessObject.getDescription() + "】业务ID流程变量获取..");
		Map paraValueMap = new HashMap();
		String parValue = "";
		String primaryKeyPropertyName = businessObject.getPrimaryKeyProperty().getPropertyName();
		parValue = com.infolion.platform.dpframework.util.BeanUtils.getPropertyValue(domainObject, primaryKeyPropertyName);
		// 当前流程名称
		String processDefinitionName = taskInstance.getProcessInstance().getProcessDefinition().getName();
		log.debug(" 获取业务对象业务ID流程变量【" + Constants.WORKFLOW_USER_KEY_VALUE + "_" + processDefinitionName + " 】值：" + parValue);
		paraValueMap.put(WorkflowService.getSubWorkFolwBusinessIdVariableName(processDefinitionName), parValue);

		return paraValueMap;
	}

	/**
	 * 给协作对象的参数赋值
	 * 
	 * @param businessObject
	 * @param domainObject
	 * @param taskInstance
	 * @return
	 */
	public static Map getRelationshipVariable(BusinessObject businessObject, Object domainObject, TaskInstance taskInstance)
	{
		log.debug("进入业务对象【" + businessObject.getDescription() + "】协作业务对象 流程变量获取..");
		Map paraValueMap = new HashMap();
		// 取得协作对象方法中的参数，并转换为当前业务对象属性的值保存，供当前业务对象使用
		Set<Relationship> relationships = businessObject.getRelationships();
		Iterator<Relationship> realIter = relationships.iterator();
		while (realIter.hasNext())
		{
			Relationship relationship = (Relationship) realIter.next();
			// 协作对象的属性
			String relaPar = relationship.getMapProperty();
			// 自身属性名
			String myPar = relationship.getRelateProperty();

			String relaParValue = (String) (taskInstance.getVariable(relaPar) == null ? "" : taskInstance.getVariable(relaPar));
			log.debug(" 获取协作业务对象流程变量【" + myPar + " 】值：" + relaParValue);
			paraValueMap.put(myPar, relaParValue);
			try
			{
				// TODO LJX 20100816 Add 如果已经有值了，不再射它。
				// 此处存在问题，暂时搞不明白为什么要射它，需要再次深入理解。
				String oldValue = BeanUtils.getPropertyValue(domainObject, myPar);
				if (StringUtils.isNullBlank(oldValue))
					BeanUtils.setProperty(domainObject, myPar, relaParValue);
			}
			catch (Exception e)
			{
				// throw new WorkflowException("给参数赋值时出错：" + e.getMessage());
				throw new WorkflowException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.SetPropertyException, e.getMessage());
			}
		}
		return paraValueMap;
	}

	/**
	 * 取得提交流程中的参数，并从领域对象中，取得相对应的值集合
	 * 
	 * @param businessObject
	 * @return
	 */
	public static Map getSubmitProcessUrlVariable(BusinessObject businessObject, Object domainObject)
	{
		log.debug("进入业务对象【" + businessObject.getDescription() + "】 流程变量获取..");
		BaseObject baseObject = (BaseObject) domainObject;
		Method method = getTaskRefMethod(baseObject.getWorkflowTaskId());
		return getMethodVariable(businessObject, domainObject, method);
	}

	/**
	 * 取得业务对象方法所对应参数的参数值
	 * 
	 * @param businessObject
	 * @param domainObject
	 * @param method
	 * @return
	 */
	public static Map<String, Object> getMethodVariable(BusinessObject businessObject, Object domainObject, Method method)
	{
		Map<String, Object> paraValueMap = new HashMap<String, Object>();
		if (null == method)
			return paraValueMap;

		Set<MethodParameter> parameters = method.getMethodParameters();
		Iterator<MethodParameter> parameteriter = parameters.iterator();

		while (parameteriter.hasNext())
		{
			MethodParameter methodParameter = (MethodParameter) parameteriter.next();
			String paraType = methodParameter.getParameterType();
			String paraName = methodParameter.getParameterName();
			// String paraValue = null;
			Object paraValue = null;
			// 常量、数字、字符
			if ("1".equals(paraType) || "3".equals(paraType) || "4".equals(paraType))
			{
				paraValue = methodParameter.getConstantValue();
			}
			// 来自属性
			else if ("2".equals(paraType))
			{
				try
				{
					// paraValue = BeanUtils.getProperty(domainObject,
					// methodParameter.getParameterRef());
					paraValue = BeanUtils.getNestedPropertyReturnObject(domainObject, methodParameter.getParameterRef());
				}
				catch (Exception e)
				{
					// throw new WorkflowException("给参数赋值时出错：" +
					// e.getMessage());
					throw new WorkflowException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.SetPropertyException, e.getMessage());
				}
			}
			log.debug(" 获取流程变量【" + paraName + " 】值：" + paraValue);
			paraValueMap.put(paraName, paraValue);
		}
		log.debug("完成业务对象：" + businessObject.getDescription() + " 流程变量获取..");
		return paraValueMap;
	}

	/**
	 * 设置动态参与者条件表达式上变量的值，把之放入流程上下文变量中
	 * 
	 * @param taskInstance
	 * @param domainObject
	 */
	private static void setDynaActorIfStatementParameter(TaskInstance taskInstance, Object domainObject, String extProcessId)
	{
		TaskActorConditionHibernateDao actorConditionHibernateDao = (TaskActorConditionHibernateDao) EasyApplicationContextUtils.getBeanByName("taskActorConditionHibernateDao");
		long nodeId = taskInstance.getToken().getNode().getId();
		List<TaskActorCondition> taskActorConditions = actorConditionHibernateDao.getTaskActorConditionByNodeId(extProcessId, nodeId);
		// 如果没有配置动态参与者条件
		if (null == taskActorConditions || taskActorConditions.size() < 1)
			return;
		for (TaskActorCondition taskActorCondition : taskActorConditions)
		{
			setExpressionVariablesValue(taskInstance, domainObject, taskActorCondition.getIfStatement());
			// 如果then表达式是用户定义参与者类型或者动态sql，则必须对其中的变量进行赋值
			if (taskActorCondition.THEN_STATEMENT_TYPE_USER_DEFINE_TYPE.equals(taskActorCondition.getThenStatementType()) || taskActorCondition.THEN_STATEMENT_TYPE_DYNC_SQL.equals(taskActorCondition.getThenStatementType()))
				setExpressionVariablesValue(taskInstance, domainObject, taskActorCondition.getThenStatement());
		}
	}

	/**
	 * 根据扩展流程定义ID,节点id，取得该节点的 扩展流程节点定义配置信息
	 * 
	 * @param extProcessId
	 *            流程扩展ID
	 * @param nodeId
	 *            JBPM流程节点ID
	 * @return
	 */
	public static ExtendNodeDefinition getExtendNodeDefinition(String extProcessId, long nodeId)
	{
		ExtNodeDefinitionDao extNodtDefDao = (ExtNodeDefinitionDao) EasyApplicationContextUtils.getBeanByName("extNodeDefinitionDao");
		ExtendNodeDefinition extNodtDef = extNodtDefDao.getByExtProcessId(extProcessId, nodeId);
		return extNodtDef;
	}

	// /**
	// * 根据JBPM流程定义ID,节点id，取得该节点的 扩展流程节点定义配置信息。
	// *
	// * @param processId
	// * JBPM流程定义ID
	// * @param nodeId
	// * JBPM流程节点ID
	// * @return
	// */
	// public static ExtendNodeDefinition getExtendNodeDefinition(long
	// processId, long nodeId)
	// {
	// ExtProcessDefinitionService extProcessDefinitionService =
	// (ExtProcessDefinitionService)
	// EasyApplicationContextUtils.getBeanByName("extProcessDefinitionService");
	// String extProcessId =
	// extProcessDefinitionService.getExtProcessDefId(processId);
	// return getExtendNodeDefinition(extProcessId, nodeId);
	// }

	/**
	 * 根据节点id，取得以该节点为起始点的所有迁移路径
	 * 
	 * @param nodeId
	 * @return
	 */
	public static List<ExtendTransition> getAllExtendTransitions(String extProcessId, long nodeId)
	{
		ExtendTransitionDao transitionDao = (ExtendTransitionDao) EasyApplicationContextUtils.getBeanByName("extendTransitionDao");
		List<ExtendTransition> transitionDef = transitionDao.getByNodeId(extProcessId, String.valueOf(nodeId));
		return transitionDef;
	}

	/**
	 * 取得以该任务节点为起点的一条有效路径
	 * 
	 * @param extendTransitions
	 * @return
	 */
	public static String getVariableTransition(TaskInstance taskInstance, List<ExtendTransition> extendTransitions)
	{
		if (null == extendTransitions || extendTransitions.size() < 1)
			return null;
		Token token = taskInstance.getToken();
		Task task = taskInstance.getTask();
		// 创建表达式执行上下文
		ExecutionContext executionContext = new ExecutionContext(token);
		executionContext.setTask(task);
		executionContext.setTaskInstance(taskInstance);
		int count = 0;
		String transitionName = null;
		for (ExtendTransition extendTransition : extendTransitions)
		{
			// 处理类
			if ("1".equals(extendTransition.getConditionType()))
			{

			}
			// 表达式
			else if ("2".equals(extendTransition.getConditionType()))
			{
				String condition = extendTransition.getCondition();
				Object result = JbpmExpressionEvaluator.evaluate(condition, executionContext);
				log.debug("执行JBPM表达式:" + condition + " == " + result);
				if (result == null)
				{
					throw new WorkflowException("执行迁移路径（" + extendTransition.getTransitionName() + "）上的条件表达式：" + condition + "，返回空值，请检查条件表达式配置！");
				}
				else if (!(result instanceof Boolean))
				{
					throw new WorkflowException("执行迁移路径（" + extendTransition.getTransitionName() + "）上的条件表达式：" + condition + "，返回非布尔型数据，请检查条件表达式配置！表达式执行返回的结果类型为：" + result.getClass().getName());
				}
				// 如果表达式执行正确
				else if (((Boolean) result).booleanValue())
				{
					transitionName = extendTransition.getTransitionName();
					count++;
				}
			}
			else if (StringUtils.isNullBlank(extendTransition.getConditionType()))
			{
				transitionName = extendTransition.getTransitionName();
				count++;
			}
		}

		if (count < 1)
		{
			throw new WorkflowException("以节点（" + taskInstance.getToken().getNode().getName() + "）为起始节点的迁移路径中，不存在执行条件为true的路径，请检查流程配置！");
		}
		if (count > 1)
		{
			throw new WorkflowException("以节点（" + taskInstance.getToken().getNode().getName() + "）为起始节点的迁移路径中，包含多个执行条件为true的路径，请检查流程配置！");
		}

		// LJX 20100420 发现以下代码纯属虚构，如有雷同请不要参考。
		if (extendTransitions.size() == 1)
			return extendTransitions.get(0).getTransitionName();

		return transitionName;
	}

	/**
	 * 取得以该任务节点为起点的一条有效路径
	 * 
	 * @param extendTransitions
	 * @return
	 */
	public static String getVariableTransition(ExecutionContext executionContext, List<ExtendTransition> extendTransitions)
	{
		if (null == extendTransitions || extendTransitions.size() < 1)
			return null;
		AssertUtil.notNull(executionContext, "JBPM执行上下文");
		int count = 0;
		String transitionName = null;
		for (ExtendTransition extendTransition : extendTransitions)
		{
			// 处理类
			if ("1".equals(extendTransition.getConditionType()))
			{

			}
			// 表达式
			else if ("2".equals(extendTransition.getConditionType()))
			{
				String condition = extendTransition.getCondition();
				Object result = JbpmExpressionEvaluator.evaluate(condition, executionContext);
				log.debug("执行JBPM表达式:" + condition + " == " + result);

				if (result == null)
				{
					throw new WorkflowException("执行迁移路径（" + extendTransition.getTransitionName() + "）上的条件表达式：" + condition + "，返回空值，请检查条件表达式配置！");
				}
				else if (!(result instanceof Boolean))
				{
					throw new WorkflowException("执行迁移路径（" + extendTransition.getTransitionName() + "）上的条件表达式：" + condition + "，返回非布尔型数据，请检查条件表达式配置！表达式执行返回的结果类型为：" + result.getClass().getName());
				}
				// 如果表达式执行正确
				else if (((Boolean) result).booleanValue())
				{
					transitionName = extendTransition.getTransitionName();
					count++;
				}
			}
			else if (StringUtils.isNullBlank(extendTransition.getConditionType()))
			{
				transitionName = extendTransition.getTransitionName();
				count++;
			}
		}

		if (count < 1)
		{
			throw new WorkflowException("以节点（" + executionContext.getToken().getNode().getName() + "）为起始节点的迁移路径中，不存在执行条件为true的路径，请检查流程配置！");
		}
		if (count > 1)
		{
			throw new WorkflowException("以节点（" + executionContext.getToken().getNode().getName() + "）为起始节点的迁移路径中，包含多个执行条件为true的路径，请检查流程配置！");
		}

		// LJX 20100420 发现以下代码纯属虚构，如有雷同请不要参考。
		if (extendTransitions.size() == 1)
			return extendTransitions.get(0).getTransitionName();

		return transitionName;
	}

	/**
	 * 取得if条件语句中的变量集合
	 * 
	 * @param nodeId
	 * @return
	 */
	public static Set getDyncActorIfStatement(String extProcessId, long nodeId)
	{
		TaskActorConditionHibernateDao actorConditionHibernateDao = (TaskActorConditionHibernateDao) EasyApplicationContextUtils.getBeanByName("taskActorConditionHibernateDao");
		List<TaskActorCondition> list = actorConditionHibernateDao.getTaskActorConditionByNodeId(extProcessId, nodeId);

		return null;
	}

	/**
	 * 把该任务节点的所有迁移路径上的条件变量值，全部放入流程上下文中
	 * 
	 * @param taskInstance
	 * @param domainObject
	 */
	private static void setTransitionConditionParameterValue(TaskInstance taskInstance, Object domainObject, List<ExtendTransition> transitionDef)
	{
		String taskName = taskInstance.getTask().getName();
		// 设置迁移路径条件表达式
		if (null == transitionDef || transitionDef.size() < 1)
			return;
		Set set = taskInstance.getToken().getAvailableTransitions();
		Iterator<Transition> iter = set.iterator();
		while (iter.hasNext())
		{
			Transition transition = iter.next();
			String transitionName = transition.getName();
			// 设置迁移路径条件表达式
			for (int i = 0; i < transitionDef.toArray().length; i++)
			{
				if (transitionName.equals(transitionDef.get(i).getTransitionName()))
				{
					String userDefCondition = transitionDef.get(i).getCondition();
					// 为流程自定义变量设定值
					setExpressionVariablesValue(taskInstance, domainObject, userDefCondition);
					// 为了不影响jbpm对迁移路径条件的判断，不把流程条件设置到jbpm上下文中
					// transition.setCondition(userDefCondition);
					log.debug("为任务【" + taskName + "】上的迁移路径【" + transitionName + "】,增加自定义条件表达式【" + userDefCondition + "】");
				}
			}
		}
	}

	/**
	 * 根据任务id，取得该任务对应的业务对象方法
	 * 
	 * @param taskStrId
	 * @return
	 */
	public static Method getTaskRefMethod(String taskStrId)
	{
		if (StringUtils.isNullBlank(taskStrId))
			return null;
		long taskId = Long.parseLong(taskStrId);
		// 取得扩展节点定义信息
		ExtNodeDefinitionService definitionService = (ExtNodeDefinitionService) EasyApplicationContextUtils.getBeanByName("extNodeDefinitionService");
		ExtProcessDefinitionService extProcessDefinitionService = (ExtProcessDefinitionService) EasyApplicationContextUtils.getBeanByName("extProcessDefinitionService");
		// 取得节点定义信息
		NodeInfo nodeInfo = WorkflowService.getNodeIdByTaskId(taskId);
		if (null == nodeInfo)
		{
			// throw new WorkflowException("未找到待办记录对应的节点定义信息，请检查。");
			throw new WorkflowCanRedirectedException(SysMsgConstants.MSG_CLASS_DP, WorkflowException.NodeInfoNotFoundRelateToTask);
		}
		long parentProcessDefSubProcessNodeId = nodeInfo.getParentProcessDefSubProcessNodeId();

		String nodeStrId = String.valueOf(nodeInfo.getNodeId());
		BusinessObjectService businessObjectService = (BusinessObjectService) EasyApplicationContextUtils.getBeanByName("businessObjectService");
		// 根据JBPM任务ID查找到该节点的扩展节点定义
		// ////0810///////
		String extProcessDefId = (String) WorkflowService.getRootWorkFlowVariableValue(taskStrId, com.infolion.platform.dpframework.core.Constants.WORKFLOW_EXTPROCESS_ID);// extProcessDefinitionService.getExtProcessDefId(nodeInfo.getProcessId());
		ExtendNodeDefinition extendNodeDefinition = definitionService.getByExtProcessId(nodeInfo.getNodeId(), extProcessDefId);
		if (null == extendNodeDefinition)
		{
			throw new WorkflowCanRedirectedException("流程" + nodeInfo.getProcessDescription() + "下，节点" + nodeInfo.getNodeName() + "未配置节点逻辑配置信息，请检查配置！");
		}
		String methodId = extendNodeDefinition.getBoMethodId();
		String boId = extendNodeDefinition.getBoId();

		// 如果当前流程为子流程，则需要到父流程节点逻辑中找到 子流程节点逻辑配置上配置的相关业务对象ID、方法ID
		if (nodeInfo.isSubWorkFlowProcess())
		{
			// LJX20100815 Add 如果还是找不到与之绑定的业务对象方法ID,则流程为子流程，必须到父流程节点逻辑配置上
			if (StringUtils.isNullBlank(methodId))
			{
				ExtendNodeDefinition subextendNodeDefinition = definitionService.getByExtProcessId(parentProcessDefSubProcessNodeId, extProcessDefId);
				if (null == subextendNodeDefinition || StringUtils.isNullBlank(subextendNodeDefinition.getBoId()) || StringUtils.isNullBlank(subextendNodeDefinition.getBoMethodId()))
				{
					throw new WorkflowCanRedirectedException("子流程" + subextendNodeDefinition.getNodeDefinitionName() + ",未在父流程节点上配置节点逻辑配置信息(关联业务对象、关联业务对象方法)，请检查配置！");
				}
				methodId = subextendNodeDefinition.getBoMethodId();
				boId = subextendNodeDefinition.getBoId();
			}
		}
		else
		{
			// 如果在任务节点上未定义方法，则默认从流程实例中取得关联的方法
			if (StringUtils.isNullBlank(methodId))
			{
				// 取得对应的流程定义id，由此查找扩展流程定义逻辑
				String processName = nodeInfo.getProcessName();
				BoProcessDefinitionService boProcessDefinitionService = (BoProcessDefinitionService) EasyApplicationContextUtils.getBeanByName("boProcessDefinitionService");
				BusinessProcessDefinition businessProcessDefinition = boProcessDefinitionService.getByProcessDefinitionName(processName);
				methodId = businessProcessDefinition.getBoMethodId();
				boId = businessProcessDefinition.getBoId();
			}
		}

		if (StringUtils.isNullBlank(methodId) || StringUtils.isNullBlank(boId))
		{
			// throw new
			// WorkflowException("流程定义中，必须至少指定一个方法，用于获取待办信息，请检查业务对象的流程配置。");
			throw new WorkflowCanRedirectedException(SysMsgConstants.MSG_CLASS_DP, WorkflowException.ProcessConfigNotCorrect);
		}

		Method bomethod = businessObjectService.getMethod(boId, methodId);
		if (null == bomethod)
		{
			// throw new WorkflowException("业务对象(id:" + boId + ")的方法配置中，找不到id为："
			// + methodId + "的方法。");
			throw new WorkflowCanRedirectedException(SysMsgConstants.MSG_CLASS_DP, WorkflowException.MethodNotFoundInProcessConfig, boId, methodId);
		}
		return bomethod;
	}

	/**
	 * 执行节点逻辑,先判断执行节点逻辑的类型，再根据判断结果执行相应的逻辑。<br>
	 * 暂时只支持 ：3、SQL语句 4、外部接口方法
	 * 
	 * @param extProcessId
	 * @param nodeId
	 * @param businessId
	 * @param viableLogics
	 *            可执行的逻辑类型编号，不填表示都可执行
	 */
	public static void executeLogic(String extProcessId, long nodeId, String businessId, String... viableLogics)
	{
		// 根据节点id，取得该节点的逻辑配置信息
		ExtendNodeDefinition extendNodeDefinition = ExtendProcessService.getExtendNodeDefinition(extProcessId, nodeId);
		if (null != extendNodeDefinition && !StringUtils.isNullBlank(extendNodeDefinition.getBoId()))
		{
			BusinessObjectService boService = (BusinessObjectService) EasyApplicationContextUtils.getBeanByName("businessObjectService");
			String boName = boService.getBusinessObjectName(extendNodeDefinition.getBoId());
			Object boInstance = BusinessObjectService.getBoInstance(boName, businessId);
			List<String> viables = Arrays.asList(viableLogics);
			if ("4".equals(extendNodeDefinition.getExeLogicType()) && (viables.size() == 0 || viables.contains("4")))
			{
				// 执行外部接口
				outsideInterfaceAction(boInstance, extendNodeDefinition);
			}
			else if ("3".equals(extendNodeDefinition.getExeLogicType()) && (viables.size() == 0 || viables.contains("3")))
			{
				// 执行SQL语句
				sqlAction(boInstance, extendNodeDefinition);
			}
		}
	}

	/**
	 * 执行JBPM逻辑处理。1、表达式 2、处理类 <BR>
	 * 设置用户自定义条件，并把自定义条件上的参数值，保存到流程上下文中
	 * 
	 * @param taskInstance
	 * @param nodeId
	 * @param businessId
	 */
	public static void executeJbpmLogic(ExecutionContext executionContext, ExtendNodeDefinition extendNodeDefinition, List<ExtendTransition> extendTransitions, String businessId)
	{
		BusinessObjectService boService = (BusinessObjectService) EasyApplicationContextUtils.getBeanByName("businessObjectService");
		String boName = boService.getBusinessObjectName(extendNodeDefinition.getBoId());
		Object boInstance = BusinessObjectService.getBoInstance(boName, businessId);

		TaskInstance taskInstance = executionContext.getTaskInstance();
		String taskName = taskInstance.getTask().getName();
		String condition = extendNodeDefinition.getExeLogic();
		// 为任务节点设置（处理类）
		if (!StringUtils.isNullBlank(condition))
		{
			// 处理类
			if ("1".equals(extendNodeDefinition.getExeLogicType()))
			{
				Delegation delegation = new Delegation();
				Action action = new Action();
				delegation.setClassName(condition);
				delegation.setConfigType("bean");
				action.setActionDelegation(delegation);
				taskInstance.getTask().executeAction(action, executionContext);
				// taskInstance.getTask().setTaskController(taskController);
				log.debug("为任务【" + taskName + "】,增加自定义控制器【" + condition + "】");
			}
		}

	}

	/**
	 * 为任务实例设置用户定义表达式，包括任务节点控制器和迁移路径条件表达式
	 * 
	 * @param taskInstance
	 */
	public static void setUserDefineCondition(TaskInstance taskInstance, Object domainObject, ExtendNodeDefinition taskDef, List<ExtendTransition> transitionDef)
	{
		if (null == taskInstance || null == taskInstance.getTask())
		{
			// throw new WorkflowException("流程实例或者流程任务节点为空，请检查！");
			throw new WorkflowException(SysMsgConstants.MSG_CLASS_DP, WorkflowException.TaskInstOrTaskIsNull);
		}
		String taskName = taskInstance.getTask().getName();
		log.debug("为任务:(" + taskName + ")设置用户自定义条件开始。");
		if (null == taskDef)
		{
			log.debug("未为任务【" + taskName + "】设置用户自定义条件。");
			return;
		}
		String condition = taskDef.getExeLogic();
		// 为任务节点设置条件或控制器代理
		if (!StringUtils.isNullBlank(condition))
		{
			// 表达式
			if ("2".equals(taskDef.getExeLogicType()))
			{
				// 为流程自定义变量设定值
				setTaskVariablesValue(taskInstance, domainObject, taskName, condition);
				// taskInstance.getTask().setCondition(condition);
				// log.debug("为任务【" + taskName + "】,增加自定义条件表达式：(" + condition
				// + ")");
			}
			// 处理类
			else if ("1".equals(taskDef.getExeLogicType()))
			{
				TaskController taskController = new TaskController();
				Delegation delegation = new Delegation();
				delegation.setClassName(condition);
				taskController.setTaskControllerDelegation(delegation);
				taskInstance.getTask().setTaskController(taskController);
				log.debug("为任务【" + taskName + "】,增加自定义控制器【" + condition + "】");
			}
		}
		// 为该节点保存迁移路径参数值
		setTransitionConditionParameterValue(taskInstance, domainObject, transitionDef);
		log.debug("为任务:(" + taskName + ")设置用户自定义条件结束。");
		setDynaActorIfStatementParameter(taskInstance, domainObject, taskDef.getExtProcessId());
		log.debug("为任务:(" + taskName + ")设置动态参与者条件变量结束。");
	}

	/**
	 * 执行SQL语句，将语句中的${}替换成业务对象相应属性的值，用法类似JSP2.0 EL用法<br>
	 * 例如：update YPURCHASEORDER set ADDRESS='new address' where
	 * PURCHASEORDERID='${purchaseOrderId}'
	 * 
	 * @param boInstance
	 * @param extendNodeDefinition
	 */
	public static void sqlAction(Object boInstance, ExtendNodeDefinition extendNodeDefinition)
	{
		String sql = extendNodeDefinition.getExeLogic();
		log.debug("get sql:" + sql);
		if (StringUtils.isNullBlank(sql))
			return;
		int i;
		String newSql = sql;
		// 解析SQL语句，替换所有${}块的内容
		while ((i = newSql.indexOf("${")) >= 0)
		{
			int j = newSql.indexOf("}", i);
			if (j == -1)
			{
				throw new WorkflowException(SysMsgConstants.MSG_CLASS_DP, WorkflowException.ParseSqlError1, sql);
			}
			String elStr = newSql.substring(i + 2, j);
			AssertUtil._notNullBlank(elStr, WorkflowException.ParseSqlError4, sql);
			String value = " ";
			log.debug("   get elStr:" + elStr);
			try
			{
				Object obj = PropertyUtils.getProperty(boInstance, elStr);
				if (obj != null)
					value = obj.toString();
			}
			catch (NoSuchMethodException e)
			{
				throw new WorkflowException(SysMsgConstants.MSG_CLASS_DP, WorkflowException.ParseSqlError5, boInstance.getClass().getSimpleName(), elStr, sql);
			}
			catch (Exception e)
			{
				throw new WorkflowException(e, SysMsgConstants.MSG_CLASS_DP, WorkflowException.ParseSqlError2, elStr, sql);
			}
			newSql = StringUtils.replaceOnce(newSql, newSql.substring(i, j + 1), value);
		}
		log.debug("解析后SQL语句：" + newSql);
		ProcessJdbcDao jdbcDao = (ProcessJdbcDao) EasyApplicationContextUtils.getBeanByName("processJdbcDao");
		try
		{
			jdbcDao.getJdbcTemplate().execute(newSql);
		}
		catch (Exception e)
		{
			throw new WorkflowException(e, SysMsgConstants.MSG_CLASS_DP, WorkflowException.ParseSqlError3, newSql);
		}
	}

	/**
	 * 外部接口动作
	 * 
	 * @param boInstance
	 * @param extendNodeDefinition
	 */
	public static void outsideInterfaceAction(Object boInstance, ExtendNodeDefinition extendNodeDefinition)
	{

		if (UserContextHolder.getLocalUserContext() != null && UserContextHolder.getLocalUserContext().getUserContext() != null)
		{
			// if (boInstance instanceof SalesDoc)
			// {
			// boInstance = ((SalesDoc) boInstance).copy();
			// }
			String lastapptime = DateUtils.getCurrTime(DateUtils.DB_STORE_DATE);
			String lastappname = UserContextHolder.getLocalUserContext().getUserContext().getUser().getUserName();

			try
			{
				BeanUtils.setProperty(boInstance, "lastapptime", lastapptime);
				BeanUtils.setProperty(boInstance, "lastappname", lastappname);
				String methodName = extendNodeDefinition.getExeLogic();
				OutsidePersistenceService.execute(boInstance, methodName);

				// throw new BusinessException("BBC"); 测试用。
				// if (boInstance instanceof SalesDoc)
				// {
				// BeanUtils.setProperty(boInstance, "endorsestate", "X");
				// String methodName = extendNodeDefinition.getExeLogic();
				// OutsidePersistenceService.execute(boInstance,
				// methodName);
				// }
				// else if (boInstance instanceof PurchasingDoc)
				// {
				// BeanUtils.setProperty(boInstance, "frgzu", "X");
				// }
			}
			catch (IllegalAccessException e)
			{
				e.printStackTrace();
			}
			catch (InvocationTargetException e)
			{
				e.printStackTrace();
			}
		}

	}

	/**
	 * 邮件动作：判断是否要发送邮件，再根据判断结果查找相关信息并发送邮件
	 * 
	 * @param nodeId
	 *            结点ID
	 * @param taskInsId
	 *            任务实例ID
	 * @param dynaTaskActorList
	 *            动态参与者列表
	 * @param businessId
	 *            业务对象实例ID
	 */
	public static void mailAction(String extProcessId, long nodeId, long taskInsId, List<DynamicTaskActor> dynaTaskActorList, String businessId)
	{
		ExtendNodeDefinition extendNodeDefinition = getExtendNodeDefinition(extProcessId, nodeId);
		// 如果需要发邮件通知
		if (extendNodeDefinition != null && "Y".equals(extendNodeDefinition.getIsSendMail()))
		{
			BusinessObjectService businessObjectService = (BusinessObjectService) EasyApplicationContextUtils.getBeanByName("businessObjectService");
			BusinessObject businessObject = businessObjectService.getBusinessObjectByBoId(extendNodeDefinition.getBoId());
			String url = ExtendProcessService.getEntryProcessUrl(businessObject);
			List<String> actorIds = new ArrayList<String>();
			for (DynamicTaskActor dta : dynaTaskActorList)
			{
				actorIds.add(dta.getActorId());
			}
			List<TaskActor> taskActors = ((TaskActorDao) EasyApplicationContextUtils.getBeanByName("taskActorDao")).getByNodeId(String.valueOf(nodeId));
			for (TaskActor ta : taskActors)
			{
				actorIds.add(ta.getActorId());
			}
			ExtendProcessService.sendMail(url, businessId, taskInsId, actorIds);
		}
	}

	/**
	 * 根据URL拼凑参数和收件人ID列表构造并发送邮件
	 * 
	 * @param processUrl
	 * @param businessId
	 * @param taskId
	 * @param actorIds
	 */
	public static void sendMail(String processUrl, String businessId, long taskId, List<String> actorIds)
	{
		MailService mailService = (MailService) EasyApplicationContextUtils.getBeanByName("mailService");

		// 获得所有执行者的EMAIL地址
		ArrayList<String> actorMailAddress = new ArrayList<String>();
		UserJdbcDao userJdbcDao = (UserJdbcDao) EasyApplicationContextUtils.getBeanByName("userJdbcDao");
		for (String actorId : actorIds)
		{
			String email = ((UserPersonnelJdbcDao) EasyApplicationContextUtils.getBeanByName("userPersonnelJdbcDao")).getEMailAddressByUserId(actorId);
			if (!StringUtils.isNullBlank(email))
			{
				actorMailAddress.add(email);
			}
		}

		String to[] = actorMailAddress.toArray(new String[actorMailAddress.size()]);

		// 构造邮件内容
		// 拼凑URL
		StringBuffer url = new StringBuffer();
		String contextPath = "";
		if (UserContextHolder.getLocalUserContext() != null && UserContextHolder.getLocalUserContext().getUserContext() != null)
			contextPath = UserContextHolder.getLocalUserContext().getUserContext().getUserDefineParameter(UserContext.WEB_APP_PATH).toString();
		else
		{
			contextPath = EasyApplicationContextUtils.getSysParamAsString("webAppPath");
		}
		url.append(contextPath).append("/").append(processUrl).append("&workflowTaskId=").append(taskId).append("&businessId=").append(businessId).append("&isOuterUrl=Y");
		// 从邮件工厂通过邮件模板构造邮件
		Map<String, String> params = new TreeMap<String, String>();
		params.put("url", url.toString());
		MailContent mail = MailFactory.createSystemMailByTemplate("wf", params, to, null);
		// // 构造消息
		// StringBuffer msg = new StringBuffer();
		// msg.append("    您有一个流程等待处理，点此链接进入处理页面:");
		// msg.append("<a href=\"").append(url).append("\">").append(url).append(
		// "</a>");
		// MailContent mail = new MailContent();
		// mail.setHost("mail.ffcs.cn");
		// mail.setFrom("liujj@ffcs.cn");
		// mail.setTo(to);
		// mail.setAuth(true);
		// mail.setUsername("");
		// mail.setPassword("");
		// mail.setSubject("流程处理提醒");
		// mail.setText(msg.toString());
		// mail.setUseHtml(true);
		// 发送邮件
		mailService.sendMailImmediately(mail);

	}

	/**
	 * 生成任务节点的动态参与者,并返回动态参与者
	 * 
	 * @param processId
	 * @param taskId
	 * @param businessId
	 * @return 返回动态参与者
	 */
	public static List<DynamicTaskActor> generateDynaTaskActor(ExecutionContext executionContext, String businessId)
	{
		TaskActorConditionHibernateDao actorConditionHibernateDao = (TaskActorConditionHibernateDao) EasyApplicationContextUtils.getBeanByName("taskActorConditionHibernateDao");
		DynamicTaskActorHibernateDao dynamicTaskActorHibernateDao = (DynamicTaskActorHibernateDao) EasyApplicationContextUtils.getBeanByName("dynamicTaskActorHibernateDao");
		DynamicTaskActorJdbcDao dynamicTaskActorJdbcDao = (DynamicTaskActorJdbcDao) EasyApplicationContextUtils.getBeanByName("dynamicTaskActorJdbcDao");
		long nodeId = executionContext.getToken().getNode().getId();
		String extProcessId = (String) WorkflowService.getRootWorkFlowVariableValue(executionContext.getContextInstance(), Constants.WORKFLOW_EXTPROCESS_ID);
		List<TaskActorCondition> taskActorConditions = actorConditionHibernateDao.getTaskActorConditionByNodeId(extProcessId, nodeId);
		List<DynamicTaskActor> returnList = new ArrayList<DynamicTaskActor>();
		// 如果没有配置动态参与者条件
		if (null == taskActorConditions || taskActorConditions.size() < 1)
			return returnList;
		for (TaskActorCondition taskActorCondition : taskActorConditions)
		{
			// 执行判断条件语句
			String condition = taskActorCondition.getIfStatement();
			boolean haveCondition = true; // 是否有设定条件表达式 LJX20100223
			Object result = null;
			// 原来配置上如果无条件,存入资料库（ifStatement字段）值为 ${}
			if (StringUtils.isNullBlank(condition) || condition.equalsIgnoreCase("${}"))
			{
				haveCondition = false;
			}
			else
				result = JbpmExpressionEvaluator.evaluate(condition, executionContext);

			if (haveCondition && result == null)
			{
				throw new WorkflowException("动态执行者条件表达式：" + condition + "，返回空值，请检查条件表达式配置！");
			}
			else if (haveCondition && !(result instanceof Boolean))
			{
				throw new WorkflowException("动态执行者条件表达式：" + condition + "，返回非布尔型数据，请检查条件表达式配置！表达式执行返回的结果类型为：" + result.getClass().getName());
			}
			// 如果表达式执行正确，则表示满足该条件下的参与者，是taskActorCondition对象中ThenStatement描述的用户集合
			// LJX 20100223 如果没有配置条件表达式，默认执行“动态SQL语句”功能
			else if ((haveCondition && ((Boolean) result).booleanValue()) || !haveCondition)
			{
				// 当前需要执行的任务
				long taskId = executionContext.getTaskInstance().getTask().getId();
				// 此处尚未加入sql查询的动态参与者
				// taskActorCondition.getThenStatementType();
				// then语句的类型：1 静态指定 2 用户设定类型 3 动态sql
				if (taskActorCondition.THEN_STATEMENT_TYPE_FIXED_VALUE.equals(taskActorCondition.getThenStatementType()))
				{
					String actorId = taskActorCondition.getThenStatement();
					// 向动态参与者表中插入动态执行者
					DynamicTaskActor dynamicTaskActor = new DynamicTaskActor();
					dynamicTaskActor.setDynaTaskActorId(CodeGenerator.getUUID());
					dynamicTaskActor.setActorId(actorId);
					dynamicTaskActor.setBusinessId(businessId);
					dynamicTaskActor.setTaskId(taskId);
					dynamicTaskActorHibernateDao.save(dynamicTaskActor);
					returnList.add(dynamicTaskActor);
				}
				// 如果是动态SQL语句（语句必须满足一个返回值列表示参与者）
				else if (taskActorCondition.THEN_STATEMENT_TYPE_DYNC_SQL.equals(taskActorCondition.getThenStatementType()) || taskActorCondition.THEN_STATEMENT_TYPE_USER_DEFINE_TYPE.equals(taskActorCondition.getThenStatementType()))
				{
					String dyncActorQuerySql = generateDynaTaskActorQuerySql(executionContext, taskActorCondition.getThenStatement());
					Set actors = dynamicTaskActorJdbcDao.getDyncActor(dyncActorQuerySql);
					if (null == actors)
						throw new WorkflowException("动态执行者条件表达式：" + condition + "的条件下，动态参与者查询语句：" + dyncActorQuerySql + "取不到参与者的值，请检查！");
					// 向动态参与者表中插入动态执行者
					for (Iterator iterator = actors.iterator(); iterator.hasNext();)
					{
						String actorId = (String) iterator.next();
						DynamicTaskActor dynamicTaskActor = new DynamicTaskActor();
						dynamicTaskActor.setDynaTaskActorId(CodeGenerator.getUUID());
						dynamicTaskActor.setActorId(actorId);
						dynamicTaskActor.setBusinessId(businessId);
						dynamicTaskActor.setTaskId(taskId);
						dynamicTaskActorHibernateDao.save(dynamicTaskActor);
						returnList.add(dynamicTaskActor);
					}
				}
			}

		}
		return returnList;
	}

	/**
	 * 生成动态参与者的查询条件语句
	 * 
	 * @param executionContext
	 * @param statement
	 * @return
	 */
	public static String generateDynaTaskActorQuerySql(ExecutionContext executionContext, String statement)
	{
		Reader r = new StringReader(statement);
		ELParser parser = new ELParser(r);
		Object ret = null;
		try
		{
			ret = parser.ExpressionString();
		}
		catch (ParseException e)
		{
			throw new WorkflowException("动态参与者的查询条件语句格式异常：" + e.getMessage());
		}
		StringBuffer sb = new StringBuffer();
		// 如果是不带条件的查询语句
		if (ret instanceof String)
		{
			String strSql = (String) ret;
			sb.append(strSql);
			return sb.toString();
		}
		ExpressionString expressionString = (ExpressionString) ret;
		Object[] elements = expressionString.getElements();
		for (Object object : elements)
		{
			// 如果是表达式，则是sql语句
			if (object instanceof String)
			{
				String exp = (String) object;
				sb.append(exp);
			}
			// 如果是变量，则是jbpm上下文中的变量替换
			if (object instanceof NamedValue)
			{
				NamedValue namedValue = (NamedValue) object;
				String parName = namedValue.getName();
				Object parObj = executionContext.getVariable(parName);
				String parValue = null;
				if (null != parObj)
					parValue = (String) parObj;
				if (StringUtils.isNullBlank(parValue))
					throw new WorkflowException("动态执行者条件表达式：" + statement + "，中的变量：" + parName + "在流程上下文中不存在值，请检查！");
				sb.append(parValue);
			}
		}
		log.debug("动态参与者查询SQL：" + sb.toString());
		System.out.println("动态参与者查询SQL：" + sb.toString());
		return sb.toString();
	}

	/**
	 * 删除当前任务的动态参与者记录
	 * 
	 * @param taskId
	 */
	public static void deleteDynaTaskActor(long taskInsId)
	{
		DynamicTaskActorJdbcDao dynamicTaskActorJdbcDao = (DynamicTaskActorJdbcDao) EasyApplicationContextUtils.getBeanByName("dynamicTaskActorJdbcDao");
		dynamicTaskActorJdbcDao.deleteDynamicTaskActorByTaskId(taskInsId);
	}
	
	public static void deleteDynaTaskActor(String businessid)
	{
		DynamicTaskActorJdbcDao dynamicTaskActorJdbcDao = (DynamicTaskActorJdbcDao) EasyApplicationContextUtils.getBeanByName("dynamicTaskActorJdbcDao");
		dynamicTaskActorJdbcDao.deleteDynamicTaskActorByBusinessId(businessid);
	}

	/**
	 * 设置用户自定义变量值
	 * 
	 * @param taskInstance
	 */
	private static String getVariablesValue(Object domainObject, String variableName)
	{
		String variableValue = null;
		try
		{
			variableValue = BeanUtils.getProperty(domainObject, variableName) == null ? "" : (String) BeanUtils.getProperty(domainObject, variableName);
		}
		catch (Exception e)
		{
			log.error("取得领域对象属性值时出错：" + e.getMessage());
			// throw new WorkflowException("取得领域对象属性值时出错：" + e.getMessage());
			throw new WorkflowException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.GetPropertyException, e.getMessage());
		}
		log.debug("取得领域对象【" + domainObject.getClass() + "】的属性【" + variableName + "】值为:(" + variableValue + ")");
		return variableValue;
	}

	/**
	 * 设置任务用户自定义变量值
	 * 
	 * @param taskInstance
	 * @param domainObject
	 * @param variableName
	 */
	private static void setTaskVariablesValue(TaskInstance taskInstance, Object domainObject, String taskName, String userDefCondition)
	{
		if (StringUtils.isNullBlank(userDefCondition))
			return;
		String variableName = ConditionExpression.getVariableName(userDefCondition);
		String variableValue = getVariablesValue(domainObject, variableName);
		taskInstance.setVariable(variableName, variableValue);
		log.debug("为任务【" + taskName + "】的自定义变量【" + variableName + "】设定值：(" + variableValue + ")");
	}

	/**
	 * 设置表达式上的用户自定义变量值，并把值放入流程上下文中
	 * 
	 * @param taskInstance
	 * @param domainObject
	 * @param variableName
	 */
	private static void setExpressionVariablesValue(TaskInstance taskInstance, Object domainObject, String userDefCondition)
	{
		if (StringUtils.isNullBlank(userDefCondition))
			return;
		// String variableName =
		// ConditionExpression.getVariableName(userDefCondition);

		Reader r = new StringReader(userDefCondition);
		ELParser parser = new ELParser(r);
		Object ret = null;
		try
		{
			ret = parser.ExpressionString();
		}
		catch (ParseException e)
		{
			throw new ELException("jPDL表达式格式异常：" + e.getMessage() + ",userDefCondition:" + userDefCondition);
		}
		List<String> variablesName = new ArrayList();
		// 如果是el表达式
		if (ret instanceof BinaryOperatorExpression)
		{
			BinaryOperatorExpression binaryOperatorExpression = (BinaryOperatorExpression) ret;
			setBinaryOperatorExpressionVariablesName(variablesName, binaryOperatorExpression);
		}
		// 如果是完整表达式，例如SQL
		if (ret instanceof ExpressionString)
		{
			ExpressionString expressionString = (ExpressionString) ret;
			setExpressionStringVariablesName(variablesName, expressionString);
		}
		for (String variableName : variablesName)
		{
			String variableValue = getVariablesValue(domainObject, variableName);
			taskInstance.setVariable(variableName, variableValue);
			log.debug("为表达式【" + userDefCondition + "】上的自定义变量【" + variableName + "】设定值：(" + variableValue + ")");
		}
	}

	/**
	 * 取得完整表达式中的变量名称
	 * 
	 * @param variablesName
	 * @param expressionString
	 */
	private static void setExpressionStringVariablesName(List<String> variablesName, ExpressionString expressionString)
	{
		Object[] elements = expressionString.getElements();
		for (Object object : elements)
		{
			if (object instanceof NamedValue)
			{
				NamedValue namedValue = (NamedValue) object;
				String parName = namedValue.getName();
				variablesName.add(parName);
			}
		}
	}

	/**
	 * 取得el表达式中所有变量的名称
	 * 
	 * @param variablesName
	 * @param binaryOperatorExpression
	 */
	private static void setBinaryOperatorExpressionVariablesName(List<String> variablesName, BinaryOperatorExpression binaryOperatorExpression)
	{
		NamedValue namedValue = null;
		if (binaryOperatorExpression.getExpression() instanceof BinaryOperatorExpression)
		{
			BinaryOperatorExpression boExp = (BinaryOperatorExpression) binaryOperatorExpression.getExpression();
			namedValue = (NamedValue) boExp.getExpression();
		}
		else if (binaryOperatorExpression.getExpression() instanceof NamedValue)
		{
			namedValue = (NamedValue) binaryOperatorExpression.getExpression();
		}
		else
		{
			return;
		}
		variablesName.add(namedValue.getName());
		List expressions = binaryOperatorExpression.getExpressions();
		for (int i = 0; i < expressions.size(); i++)
		{
			if (expressions.get(i) instanceof BinaryOperatorExpression)
			{
				BinaryOperatorExpression obinaryOperatorExpression = (BinaryOperatorExpression) expressions.get(i);
				setBinaryOperatorExpressionVariablesName(variablesName, obinaryOperatorExpression);
			}
			else
			{
				continue;
			}
		}
	}

	/**
	 * 流程实例结束后，清除流程实例的变量
	 */
	public static void cleanJbpmVariableInstance()
	{
		JdbcTemplate jdbcTemplate = (JdbcTemplate) EasyApplicationContextUtils.getBeanByName("jdbcTemplate");
		String sql = "delete from JBPM_VARIABLEINSTANCE where ID_ in(select v.ID_ from JBPM_VARIABLEINSTANCE v, JBPM_TOKEN t, JBPM_PROCESSINSTANCE p where v.TOKEN_ = t.ID_ and t.PROCESSINSTANCE_ = p.ID_ and p.END_ is not null)";
		jdbcTemplate.execute(sql);
		//System.out.println("流程实例结束后，清除流程实例的变量！");
	}

	/**
	 * 取得流程扩展实例
	 * 
	 * @param processName
	 * @return
	 */
	public static ExtendProcessInstance getExtendProcessInstance(long processId)
	{
		ExtendProcessInstanceHibernateDao extendProcessInstanceHibernateDao = (ExtendProcessInstanceHibernateDao) EasyApplicationContextUtils.getBeanByName("extendProcessInstanceHibernateDao");
		return extendProcessInstanceHibernateDao.getExtendProcessInstance(processId);
	}
	
	  public static boolean taskNodeIsSignal(String businessId, long jbpmTaskInsId)
	  {
	    ProcessJdbcDao processJdbcDao = (ProcessJdbcDao)EasyApplicationContextUtils.getBeanByName("processJdbcDao");
	    return processJdbcDao.taskNodeIsSignal(businessId, jbpmTaskInsId);
	  }
	  
	  public static boolean processIsSignal(String businessId)
	  {
	    ProcessJdbcDao processJdbcDao = (ProcessJdbcDao)EasyApplicationContextUtils.getBeanByName("processJdbcDao");
	    return processJdbcDao.processIsSignal(businessId);
	  }

}
