/*
 * @(#)workflowController.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：傅渊源
 *  时　间：2008-12-24
 *  描　述：创建
 */

package com.infolion.platform.console.workflow.web;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

import com.infolion.platform.bo.domain.BusinessObject;
import com.infolion.platform.bo.service.BusinessObjectService;
import com.infolion.platform.component.ExtComponentServlet;
import com.infolion.platform.component.workflow.ext.CommonProcessInstance;
import com.infolion.platform.console.org.domain.SysUser;
import com.infolion.platform.console.sys.context.UserContext;
import com.infolion.platform.console.sys.context.UserContextHolder;
import com.infolion.platform.console.workflow.domain.ComboBoxFormat;
import com.infolion.platform.console.workflow.domain.JbpmProcessdefinition;
import com.infolion.platform.console.workflow.domain.JbpmTask;
import com.infolion.platform.console.workflow.domain.SysWfProcessAccessory;
import com.infolion.platform.console.workflow.domain.SysWfTaskActor;
import com.infolion.platform.console.workflow.service.SysWfCommonalityService;
import com.infolion.platform.console.workflow.service.SysWfProcessAccessoryService;
import com.infolion.platform.console.workflow.service.SysWfTaskActorService;
import com.infolion.platform.console.workflow.service.TestTableService;
import com.infolion.platform.core.web.BaseMultiActionController;
import com.infolion.platform.sys.Constants;
import com.infolion.platform.util.CodeGenerator;
import com.infolion.platform.util.StringUtils;
import com.infolion.platform.workflow.manager.service.ExtProcessDefinitionService;
import com.infolion.sapss.credit.service.CreditEntryHisJdbcService;
import com.infolion.sapss.workflow.particular.service.ParticularWorkflowService;

/**
 * <pre>
 * XDSS3工作流控制器。
 * 工作流相关页面转发。
 * 
 * </pre>
 * 
 * <br>
 * JDK版本:1.5
 * 
 * @author 林进旭
 * @version 1.0
 * @see The author for more details
 * @since 1.0
 */
public class WorkflowController extends BaseMultiActionController {
	@Autowired
	private SysWfTaskActorService sysWfTaskActorService;

	public void setSysWfTaskActorService(
			SysWfTaskActorService sysWfTaskActorService) {
		this.sysWfTaskActorService = sysWfTaskActorService;
	}

	@Autowired
	private SysWfCommonalityService sysWfCommonalityService;

	public void setSysWfCommonalityService(
			SysWfCommonalityService sysWfCommonalityService) {
		this.sysWfCommonalityService = sysWfCommonalityService;
	}

	@Autowired
	private SysWfProcessAccessoryService sysWfProcessAccessoryService;

	public void setSysWfProcessAccessoryService(
			SysWfProcessAccessoryService sysWfProcessAccessoryService) {
		this.sysWfProcessAccessoryService = sysWfProcessAccessoryService;
	}

	@Autowired
	private TestTableService testTableService;

	public void setTestTableService(TestTableService testTableService) {
		this.testTableService = testTableService;
	}

	@Autowired
	private ParticularWorkflowService particularWorkflowService;

	public void setParticularWorkflowService(
			ParticularWorkflowService particularWorkflowService) {
		this.particularWorkflowService = particularWorkflowService;
	}
	
	@Autowired
	private CreditEntryHisJdbcService creditEntryHisJdbcService;
	
	
	@Autowired
	private ExtProcessDefinitionService extProcessDefinitionService;


	/**
	 * 进入流程节点参与者界面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ModelAndView configNodeActorView(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		return new ModelAndView("console/workflow/configNodeActor");
	}

	/**
	 * 进入增加参与人信息界面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ModelAndView addConfigNodeActorView(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		request.setAttribute("processid", request.getParameter("processid"));
		request.setAttribute("taskid", request.getParameter("taskid"));
		return new ModelAndView("console/workflow/addNodeActor");
	}
	
	public ModelAndView addConfigNodeDecisionView(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		request.setAttribute("processid", request.getParameter("processid"));
		request.setAttribute("nodeid", request.getParameter("nodeid"));
		return new ModelAndView("console/workflow/configTransition");
	}
	public void queryTransition(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		String nodeid = request.getParameter("nodeid");

		String strSql = "select TRANSITIONID,NODEID,TRANSITIONNAME,LOGICSQL,PROCESSDEFID from t_sys_wf_transition WHERE NODEID='"+nodeid+"'";
		String grid_columns = "TRANSITIONID,NODEID,TRANSITIONNAME,LOGICSQL,PROCESSDEFID";
		String grid_size = "10";
		request.setAttribute("grid_sql", strSql);
		request.setAttribute("grid_columns", grid_columns);
		request.setAttribute("grid_size", grid_size);
		ExtComponentServlet extComponentServlet = new ExtComponentServlet();
		extComponentServlet.processGrid(request, response, true);
	}
	public void reloadTransition(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String nodeid = request.getParameter("nodeid");
		sysWfTaskActorService.reloadTransition(nodeid);

	}
	
	public void deleteTransition(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String strIdList = request.getParameter("idList");

		JSONObject jsonObject = new JSONObject();

		sysWfTaskActorService.deleteTransition(strIdList);

		jsonObject.put("returnMessage", "删除成功！");

		String jsonText = jsonObject.toString();

		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");

		this.operateSuccessfullyWithString(response, jsonText);
	}
	
	public void updateTransition(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String transitionId = request.getParameter("transitionId");
		String strColName = request.getParameter("colname");
		String strColValue = request.getParameter("colvalue");
		strColValue = extractFR(request, "colvalue");
		sysWfTaskActorService.updateTransition(transitionId,strColName, strColValue);
	}
	
	public ModelAndView addConfigNodeActionView(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String nodeid = request.getParameter("nodeid");
		request.setAttribute("processid", request.getParameter("processid"));
		request.setAttribute("nodeid", nodeid);
		List<Map> list = sysWfTaskActorService.queryForAction(nodeid);
		if(!list.isEmpty()&&list.size()>0){
			Map map = list.get(0);
			request.setAttribute("actionid", map.get("ACTIONID"));
			request.setAttribute("actionSQL", map.get("ACTIONSQL"));
			request.setAttribute("actionType", map.get("ACTIONTYPE"));
		}
		return new ModelAndView("console/workflow/configAction");
	}
	
	public void saveOrUpdateAction(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
        String processid = request.getParameter("processid");
        String nodeid = request.getParameter("nodeid");
        String actionid = request.getParameter("actionid");
        String actionSQL = request.getParameter("actionSQL");
        String actionType = request.getParameter("actionType");
        sysWfTaskActorService.saveOrUpdateAction(processid, nodeid, actionid, actionSQL, actionType);
		this.operateSuccessfullyWithString(response, actionid);
	}

	/**
	 * 取的流程的ID,Name给前台的ComboBox
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void queryProcessForComboBox(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		List<ComboBoxFormat> comboBoxFormatList = sysWfTaskActorService
				.queryProcessForComboBox();

		JSONArray ja = JSONArray.fromObject(comboBoxFormatList);

		JSONObject jo = new JSONObject();
		jo.put("totalProperty", comboBoxFormatList.size());
		jo.put("root", ja);

		String jsontext = jo.toString();

		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		response.getWriter().print(jsontext);
	}

	/**
	 * 取的流程节点的ID,Name给前台的ComboBox
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void queryProcessNodeForComboBox(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String strProcessId = request.getParameter("processid");
		List<ComboBoxFormat> comboBoxFormatList = sysWfTaskActorService
				.queryProcessNodeForComboBox(strProcessId);

		JSONArray ja = JSONArray.fromObject(comboBoxFormatList);

		JSONObject jo = new JSONObject();
		jo.put("totalProperty", comboBoxFormatList.size());
		jo.put("root", ja);

		String jsontext = jo.toString();

		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		response.getWriter().print(jsontext);
	}

	/**
	 * 取的流程版本号给前台的ComboBox
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void queryProcessVerForComboBox(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String strProcessId = request.getParameter("processid");
		List<ComboBoxFormat> comboBoxFormatList = sysWfTaskActorService
				.queryProcessVerForComboBox(strProcessId);

		JSONArray ja = JSONArray.fromObject(comboBoxFormatList);

		JSONObject jo = new JSONObject();
		jo.put("totalProperty", comboBoxFormatList.size());
		jo.put("root", ja);

		String jsontext = jo.toString();

		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		response.getWriter().print(jsontext);
	}
	/**
	 * 根椐流程编号或节点编号查询节点信息
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void queryProcessNodeForGrid(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String strProcessId = request.getParameter("processid");
		String strTaskId = request.getParameter("taskid");

		List<JbpmTask> jbpmTaskList = sysWfTaskActorService
				.queryProcessNodeForGrid(strProcessId, strTaskId);

		JSONArray ja = JSONArray.fromObject(jbpmTaskList);

		JSONObject jo = new JSONObject();
		jo.put("totalProperty", jbpmTaskList.size());
		jo.put("root", ja);

		String jsontext = jo.toString();
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		response.getWriter().print(jsontext);
	}

	/**
	 * 增加参与人信息
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void addTaskActor(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		SysWfTaskActor sysWfTaskActor = new SysWfTaskActor();
		sysWfTaskActor.setProcessDefId(request.getParameter("processDefId"));
		sysWfTaskActor.setTaskDefId(request.getParameter("taskDefId"));
		sysWfTaskActor.setActorType(request.getParameter("actorType"));
		sysWfTaskActor.setActorId(request.getParameter("actorId"));
		sysWfTaskActor.setActorName(request.getParameter("actorName"));

		sysWfTaskActorService.addTaskActor(sysWfTaskActor);

		this.operateSuccessfully(response);
	}

	/**
	 * 根椐流程编号和节点编号查询参与人信息
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void queryTaskActorForGrid(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String strProcessId = request.getParameter("processid");
		String strTaskId = request.getParameter("taskid");

		List<SysWfTaskActor> sysWfTaskActorList = sysWfTaskActorService
				.queryTaskActorForGrid(strProcessId, strTaskId);

		JSONArray ja = JSONArray.fromObject(sysWfTaskActorList);

		JSONObject jo = new JSONObject();
		jo.put("totalProperty", sysWfTaskActorList.size());
		jo.put("root", ja);

		String jsontext = jo.toString();

		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		response.getWriter().print(jsontext);
	}

	/**
	 * 删除流程参与者信息
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void deleteTaskActor(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String strTaskActorIdList = request.getParameter("taskactoridlist");

		sysWfTaskActorService.deleteTaskActor(strTaskActorIdList);

		this.operateSuccessfully(response);
	}

	/**
	 * 进入待办事务管理界面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ModelAndView workItemManageView(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		return new ModelAndView("console/workflow/workItemManage");
	}

	/**
	 * 进入待办事项
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ModelAndView waitForTransactWorkView(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		return new ModelAndView("console/workflow/waitForTransactWork");
	}

	/**
	 * 已完成事务
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ModelAndView finishWorkView(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		return new ModelAndView("console/workflow/finishWork");
	}
	
	public ModelAndView leaderExam(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		return new ModelAndView("console/workflow/leaderExam");
	}
	
	public void getLeaderExam(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String strStart = request.getParameter("start");
		String strLimit = request.getParameter("limit");
		int start = Integer.valueOf(strStart);
		int size = Integer.valueOf(strLimit);
		Map map = extractFR(request);
		String modalName = (String) map.get("modalName");
		String businessInfo = (String) map.get("businessInfo");
		String examPerson = (String) map.get("examPerson");
		String processState = (String) map.get("processState");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String opStartDate = request.getParameter("opStartDate");
		String opEndDate = request.getParameter("opEndDate");
		StringBuffer sb = new StringBuffer();
		if (modalName != null && !"".equals(modalName.trim()))
			sb.append(" and t.modelname like '%").append(modalName)
					.append("%'");
		if (examPerson != null && !"".equals(examPerson.trim()))
			sb.append(" and t.examineperson like '%").append(examPerson)
					.append("%'");
		if (businessInfo != null && !"".equals(businessInfo.trim()))
			sb.append(" and t.businessnote like '%").append(businessInfo)
					.append("%'");
		if (opStartDate != null && !"".equals(opStartDate.trim()))
			sb.append(" and substr(t.INSENDTIME,0,8)>='")
					.append(opStartDate).append("'");
		if (opEndDate != null && !"".equals(opEndDate.trim()))
			sb.append(" and substr(t.INSENDTIME,0,8)<='").append(opEndDate)
					.append("'");


		StringBuffer strSqlSb = new StringBuffer();
		strSqlSb.append("select * from (select rownum rnum,his.* from ");
		strSqlSb.append("(select *  ");
		strSqlSb.append(" from t_sys_leader_exam t ");
		strSqlSb.append(" where 1=1 " + sb.toString());
		strSqlSb.append(" order by t.insEndTime desc) his ) where rnum>"
				+ start + " and rnum<" + (start + size + 1) + "");

		StringBuffer strCountSql = new StringBuffer();
		strCountSql
				.append("select count(*) from ("
						+ "select * ");
		strCountSql.append("from t_sys_leader_exam t ");
		strCountSql.append("where 1=1 " + sb.toString()
				+ ")");

		log.debug("取得历史流程记录:" + strSqlSb.toString());
		int iResultCount = sysWfCommonalityService.getResultCount(strCountSql
				.toString());

		List  instanceList = sysWfTaskActorService.getLeadTaskIns(strSqlSb.toString());

		JSONArray ja = JSONArray.fromObject(instanceList);

		JSONObject jo = new JSONObject();
		jo.put("totalProperty", iResultCount);
		jo.put("root", ja);

		String jsontext = jo.toString();
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		response.getWriter().print(jsontext);
	}


	/**
	 * 审批信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ModelAndView allFinishWorkView(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		request.setAttribute("isAll", request.getParameter("isAll"));
		return new ModelAndView("console/workflow/allFinishWork");
	}

	/**
	 * 取的我的待办事项，整合BDP平台待办事项。
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void getMyProcessInstances(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String strStart = request.getParameter("start");
		String strLimit = request.getParameter("limit");
		int start = Integer.valueOf(strStart == null ? "0" : strStart);
		int size = Integer.valueOf(strLimit == null ? "0" : strLimit);
		Map map = extractFR(request);
		String modalName = (String) map.get("modalName");
		String businessInfo = (String) map.get("businessInfo");
		String taskName = (String) map.get("taskName");

		// 取得我的待办事项Grid数据JSONOBjcct. 整合BDP平台待办事项。
		JSONObject jo = this.sysWfCommonalityService.getMyProcessInstances(
				start, size, modalName, businessInfo, taskName);

		String jsontext = jo.toString();
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		response.getWriter().print(jsontext);
	}

	/**
	 * 取得历史流程记录
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void getMyProcessInstancesHistory(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String strStart = request.getParameter("start");
		String strLimit = request.getParameter("limit");
		int start = Integer.valueOf(strStart);
		int size = Integer.valueOf(strLimit);
		Map map = extractFR(request);
		String modalName = (String) map.get("modalName");
		String businessInfo = (String) map.get("businessInfo");
		String result = (String) map.get("result");
		String processState = (String) map.get("processState");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String opStartDate = request.getParameter("opStartDate");
		String opEndDate = request.getParameter("opEndDate");
		StringBuffer sb = new StringBuffer();
		if (modalName != null && !"".equals(modalName.trim()))
			sb.append(" and t.model_name like '%").append(modalName)
					.append("%'");
		if (businessInfo != null && !"".equals(businessInfo.trim()))
			sb.append(" and t.business_note like '%").append(businessInfo)
					.append("%'");
		if (result != null && !"".equals(result.trim()))
			sb.append(" and t.end_node_name like '%").append(result)
					.append("%'");
		if (processState != null && !"".equals(processState.trim())) {
			if ("finish".equals(processState)) {
				sb.append("and trim(t.INS_END_TIME) is not null ");
				// sb.append(" and trim(t.INS_END_TIME) is not null");
			} else if ("unfinish".equals(processState)) {
				sb.append("and trim(t.INS_END_TIME) is  null ");
				// sb.append(" and trim(t.INS_END_TIME) is null");
			}
		}
		if (startDate != null && !"".equals(startDate.trim()))
			sb.append(" and substr(t.ins_end_time,0,8)>='").append(startDate)
					.append("'");
		if (endDate != null && !"".equals(endDate.trim()))
			sb.append(" and substr(t.ins_end_time,0,8)<='").append(endDate)
					.append("'");

		if (opStartDate != null && !"".equals(opStartDate.trim()))
			sb.append(" and substr(t.task_end_time,0,8)>='")
					.append(opStartDate).append("'");
		if (opEndDate != null && !"".equals(opEndDate.trim()))
			sb.append(" and substr(t.task_end_time,0,8)<='").append(opEndDate)
					.append("'");

		String userName = UserContextHolder.getLocalUserContext()
				.getUserContext().getSysUser().getUserName();

		StringBuffer strSqlSb = new StringBuffer();
		strSqlSb.append("select * from (select rownum rnum,his.* from ");
		strSqlSb.append("(select  t.BUSINESS_RECORD_ID,t.model_id,t.MODEL_NAME,t.PROCESS_ID,t.END_NODE_NAME,t.INS_END_TIME,t.INS_CREATE_TIME,t.BUSINESS_NOTE,t.task_end_time,t.PROCESSTYPE,t.BOID,'' NAME_ ");
		strSqlSb.append(" from V_SYS_WF_PROCESSTASK_HISTORY t ");
		strSqlSb.append(" where t.creator='" + userName + "'" + sb.toString());
		strSqlSb.append(" order by t.task_end_time desc) his ) where rnum>"
				+ start + " and rnum<" + (start + size + 1) + "");

		StringBuffer strCountSql = new StringBuffer();
		strCountSql
				.append("select count(*) from ("
						+ "select  t.BUSINESS_RECORD_ID,t.model_id,t.MODEL_NAME,t.PROCESS_ID,t.END_NODE_NAME,t.INS_END_TIME,t.INS_CREATE_TIME,t.BUSINESS_NOTE,t.task_end_time,t.PROCESSTYPE,t.BOID ");
		strCountSql.append("from V_SYS_WF_PROCESSTASK_HISTORY t ");
		strCountSql.append("where t.creator='" + userName + "'" + sb.toString()
				+ ")");

		log.debug("取得历史流程记录:" + strSqlSb.toString());
		int iResultCount = sysWfCommonalityService.getResultCount(strCountSql
				.toString());

		List<CommonProcessInstance> instanceList = sysWfTaskActorService
				.getMyProcessInstancesHistory(strSqlSb.toString());

		JSONArray ja = JSONArray.fromObject(instanceList);

		JSONObject jo = new JSONObject();
		jo.put("totalProperty", iResultCount);
		jo.put("root", ja);

		String jsontext = jo.toString();
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		response.getWriter().print(jsontext);
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void getAllProcessInstancesHistory(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String strStart = request.getParameter("start");
		String strLimit = request.getParameter("limit");
		int start = Integer.valueOf(strStart);
		int size = Integer.valueOf(strLimit);
		Map map = extractFR(request);
		String modalName = (String) map.get("modalName");
		String businessInfo = (String) map.get("businessInfo");
		String result = (String) map.get("result");
		String processState = (String) map.get("processState");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String opStartDate = request.getParameter("opStartDate");
		String opEndDate = request.getParameter("opEndDate");
		String isAll = request.getParameter("isAll");
		StringBuffer sb = new StringBuffer();
		if (modalName != null && !"".equals(modalName.trim()))
			sb.append(" and t.model_name like '%").append(modalName)
					.append("%'");
		if (businessInfo != null && !"".equals(businessInfo.trim()))
			sb.append(" and t.business_note like '%").append(businessInfo)
					.append("%'");
		if (result != null && !"".equals(result.trim()))
			sb.append(" and t.end_node_name like '%").append(result)
					.append("%'");
		if (processState != null && !"".equals(processState.trim())) {
			if ("finish".equals(processState))
				sb.append(" and trim(t.INS_END_TIME) is not null");
			else if ("unfinish".equals(processState))
				sb.append(" and trim(t.INS_END_TIME) is null");
		}
		if (startDate != null && !"".equals(startDate.trim()))
			sb.append(
					" and substr(t.ins_end_time,0,4)||substr(t.ins_end_time,6,2)||substr(t.ins_end_time,9,2)>='")
					.append(startDate).append("'");
		if (endDate != null && !"".equals(endDate.trim()))
			sb.append(
					" and substr(t.ins_end_time,0,4)||substr(t.ins_end_time,6,2)||substr(t.ins_end_time,9,2)<='")
					.append(endDate).append("'");

		if (opStartDate != null && !"".equals(opStartDate.trim()))
			sb.append(
					" and substr(t.ins_create_time,0,4)||substr(t.ins_create_time,6,2)||substr(t.ins_create_time,9,2)>='")
					.append(opStartDate).append("'");
		if (opEndDate != null && !"".equals(opEndDate.trim()))
			sb.append(
					" and substr(t.ins_create_time,0,4)||substr(t.ins_create_time,6,2)||substr(t.ins_create_time,9,2)<='")
					.append(opEndDate).append("'");

		String appendSQL = " 1=1";
		SysUser sysUser = UserContextHolder.getLocalUserContext()
				.getUserContext().getSysUser();
		if ("self".equals(isAll)) {
			appendSQL += " and (t.creator='" + sysUser.getUserName() + "'";
			appendSQL += " or t.creator='" + sysUser.getUserId() + "')";
		}
		// 非职能部门职能看到自己部门的数据

		String strSql = "select * from (select rownum rnum,his.* from ";
		strSql += "(select * from V_SYS_WF_PROCESS_INSTANCE t where"
				+ appendSQL + sb.toString();
		strSql += " order by t.create_time desc) his ) where rnum>" + start
				+ " and rnum<" + (start + size + 1);

		String strCountSql = "select count(*) from ("
				+ "select * from V_SYS_WF_PROCESS_INSTANCE t where "
				+ appendSQL + sb.toString() + ")";

		int iResultCount = sysWfCommonalityService.getResultCount(strCountSql);

		log.debug("用户审批流程查看：" + strSql);
		List<CommonProcessInstance> instanceList = sysWfTaskActorService
				.getMyProcessInstancesHistory(strSql);

		JSONArray ja = JSONArray.fromObject(instanceList);

		JSONObject jo = new JSONObject();
		jo.put("totalProperty", iResultCount);
		jo.put("root", ja);

		String jsontext = jo.toString();
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		response.getWriter().print(jsontext);
	}

	/**
	 * 进入流程实例的附件列表界面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ModelAndView processAccessoryManageView(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		request.setAttribute("processid", request.getParameter("processid"));
		return new ModelAndView("console/workflow/processAccessoryManage");
	}

	/**
	 * 取得流程实例的附件列表信息
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void queryProcessAccessory(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String strProcessId = request.getParameter("processid");

		List<SysWfProcessAccessory> accessoryList = sysWfProcessAccessoryService
				.queryProcessAccessory(strProcessId);

		JSONArray ja = JSONArray.fromObject(accessoryList);

		JSONObject jo = new JSONObject();
		jo.put("totalProperty", accessoryList.size());
		jo.put("root", ja);

		String jsontext = jo.toString();
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		response.getWriter().print(jsontext);
	}

	/**
	 * 删除流程实例的附件信息
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void deleteProcessAccessory(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String strProcessIdList = request.getParameter("processidlist");

		sysWfProcessAccessoryService.deleteProcessAccessory(strProcessIdList);

		this.operateSuccessfully(response);
	}

	public ModelAndView testchecktree(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		// request.setAttribute("processid", "1");
		// return new ModelAndView("testchecktree");

		request.setAttribute("processid", "1");
		return new ModelAndView("testeditgrid");

		// request.setAttribute("processid", "56");
		// return new ModelAndView("console/workflow/processAccessoryManage");
	}

	/**
	 * 进入流程发布界面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ModelAndView workflowVersionManageView(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		return new ModelAndView("console/workflow/workflowVersionManage");
	}

	/**
	 * 取得流程的版本信息
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void queryProcessDefinition(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		List<JbpmProcessdefinition> accessoryList = sysWfProcessAccessoryService
				.queryProcessDefinition();

		JSONArray ja = JSONArray.fromObject(accessoryList);

		JSONObject jo = new JSONObject();
		jo.put("totalProperty", accessoryList.size());
		jo.put("root", ja);

		String jsontext = jo.toString();
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		response.getWriter().print(jsontext);
	}

	public void queryTestTable(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		List testTableList = testTableService.queryTestTable();
		JSONArray ja = JSONArray.fromObject(testTableList);

		JSONObject jo = new JSONObject();
		jo.put("totalProperty", testTableList.size());
		jo.put("root", ja);

		String jsontext = jo.toString();
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		response.getWriter().print(jsontext);
	}

	/**
	 * 审批信息 查看页面。
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ModelAndView finishWorkDetailView(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String businessRecordId = (String) request
				.getAttribute("businessRecordId");
		if (businessRecordId == null)
			businessRecordId = request.getParameter("businessRecordId");
		request.setAttribute("businessRecordId", businessRecordId);
		String workFlowTaskId = this.sysWfTaskActorService
				.findWorkflowPictureTaskId(businessRecordId);
		request.setAttribute("taskId", workFlowTaskId);
		String isRSHis = request.getParameter("isRSHis");
		if("1".equals(isRSHis)){
		    request.setAttribute("url", "shipController.spr?action=queryTaskHis&businessid="+businessRecordId);
		}
		return new ModelAndView("console/workflow/finishWorkDetail");
	}

	/**
	 * 审批信息 查看页面。
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ModelAndView finishWorkDetailViewByBdp(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String businessRecordId = (String) request
				.getAttribute("businessRecordId");
		if (businessRecordId == null)
			businessRecordId = request.getParameter("businessRecordId");
		request.setAttribute("businessRecordId", businessRecordId);
		return new ModelAndView("console/workflow/finishWorkDetailBdp");
	}

	/**
	 * 用于显示自定义查询流程历史的页面
	 */
	public ModelAndView businessAllProcessRecords(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String businessRecordId = (String) request
				.getAttribute("businessRecordId");
		if (businessRecordId == null)
			businessRecordId = request.getParameter("businessRecordId");
		String sql = "select * from (select * from (SELECT * fROM t_sys_wf_task_history union SELECT * FROM t_sys_wf_task_history_O) b where b.business_record_id "
				+ "in (select a.particular_id from t_particular_workflow a where a.original_biz_id='"
				+ businessRecordId
				+ "')"
				+ "union select * from (SELECT * fROM t_sys_wf_task_history union SELECT * FROM t_sys_wf_task_history_O) c where c.business_record_id='"
				+ businessRecordId + "')d order by d.task_end_time desc";

		request.setAttribute("customeSQL", sql);
		String particularId = this.particularWorkflowService
				.getParticularIdByOriginalId(businessRecordId);

		request.setAttribute("businessRecordId", particularId);
		String workFlowTaskId = this.sysWfTaskActorService
				.findWorkflowPictureTaskId(particularId);
		request.setAttribute("taskId", workFlowTaskId);
		return new ModelAndView("console/workflow/businessAllProcessRecords");
	}

	/**
	 * 萃取url
	 * 
	 * @param req
	 * @return 参数名,参数值的map
	 */
	private Map<String, String> extractFR(HttpServletRequest req) {
		try {
			String wait = java.net.URLDecoder.decode(req.getQueryString(),
					"UTF-8");
			String ar1[] = wait.split("&");
			Map<String, String> map = new HashMap<String, String>();
			String str = "";
			String ar2[];
			for (int i = 0; i < ar1.length; i++) {
				str = ar1[i];
				ar2 = str.split("=");
				if (ar2.length == 1)
					continue;
				map.put(ar2[0], ar2[1]);
			}
			return map;
		} catch (UnsupportedEncodingException e) {
		}
		return Collections.EMPTY_MAP;
	}

	/**
	 * 查看业务详情
	 * 
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	public void toViewDetail(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String businessId = req.getParameter("businessRecordId");
		String modelName = extractFR(req, "modelName");
		String boId = req.getParameter("boId");
		if (modelName != null) {
			// LJX 20100913 Add 加入平台业务信息查看。
			if (!StringUtils.isNullBlank(boId)) {
				BusinessObject bo = BusinessObjectService
						.getBusinessObjectByBoId(boId);
				String primatrKeyName = bo.getPrimaryKeyProperty()
						.getPropertyName();
				String url = bo.getMethodByName("_view").getUrl();
				url = url + "&" + primatrKeyName + "=" + businessId
						+ "&businessId=" + businessId + "&operType=000";
				log.debug("进入业务信息查看URL:" + url);
				req.getRequestDispatcher(url).forward(req, resp);
			} else if (modelName.indexOf("付款审批") != -1) {
				req.getRequestDispatcher(
						"paymentImportsInfoController.spr?action=findPaymentImportInfo&type=view&paymentId="
								+ businessId).forward(req, resp);
			} else if (modelName.indexOf("项目立项申请") != -1) {
				req.getRequestDispatcher(
						"projectController.spr?action=modify&from=view&projectId="
								+ businessId).forward(req, resp);
			} else if (modelName.indexOf("立项变更申请") != -1) {
				req.getRequestDispatcher(
						"changeProjectController.spr?action=findProjectInfo&from=changeR&changeId="
								+ businessId).forward(req, resp);
			} else if (modelName.indexOf("销售合同申请") != -1) {
				req.getRequestDispatcher(
						"contractController.spr?action=archSalesInfoView&businessRecordId="
								+ businessId).forward(req, resp);
			} else if (modelName.indexOf("采购合同申请") != -1) {
				req.getRequestDispatcher(
						"contractController.spr?action=ArchPurchaseInfoView&businessRecordId="
								+ businessId).forward(req, resp);
			} else if (modelName.indexOf("销售合同变更申请") != -1) {
				req.getRequestDispatcher(
						"contractController.spr?action=archSalesInfoView&from=changeR&businessRecordId="
								+ businessId).forward(req, resp);
			} else if (modelName.indexOf("采购合同变更申请") != -1) {
				req.getRequestDispatcher(
						"contractController.spr?action=ArchPurchaseInfoView&from=changeR&businessRecordId="
								+ businessId).forward(req, resp);
			} else if (modelName.indexOf("内贸付款") != -1
					|| modelName.indexOf("国内付款") != -1) {
				req.getRequestDispatcher(
						"innerTradePayController.spr?action=viewRecord&paymentId="
								+ businessId).forward(req, resp);
			} else if (modelName.indexOf("非货款") != -1) {
				req.getRequestDispatcher(
						"innerTradePayController.spr?action=viewRecord&paymentId="
								+ businessId).forward(req, resp);
			} else if (modelName.indexOf("到单申请") != -1) {
				req.getRequestDispatcher(
						"pickListInfoController.spr?action=viewPickListInfo&pickListId="
								+ businessId).forward(req, resp);
			} else if (modelName.indexOf("信用证开证申请") != -1) {
				req.getRequestDispatcher(
						"creditEntryController.spr?action=modify&operate=view&creditId="
								+ businessId).forward(req, resp);
			} else if (modelName.indexOf("信用证到证申请") != -1) {
				req.getRequestDispatcher(
						"creditArriveController.spr?action=modify&operate=view&creditId="
								+ businessId).forward(req, resp);
			} else if (modelName.indexOf("入仓申请") != -1) {
				req.getRequestDispatcher(
						"receiptsController.spr?action=addReceiptView&operType=001&receiptId="
								+ businessId).forward(req, resp);
			} else if (modelName.indexOf("出仓单申请") != -1) {
				req.getRequestDispatcher(
						"shipController.spr?action=addShipInfoView&operType=001&shipId="
								+ businessId).forward(req, resp);
			} else if (modelName.indexOf("出口货物通知单申请") != -1) {
				req.getRequestDispatcher(
						"shipNotifyController.spr?action=addShipNotifyView&operType=00&exportApplyId="
								+ businessId).forward(req, resp);
			} else if (modelName.indexOf("金达采购") != -1) {
				req.getRequestDispatcher(
						"jdPurchaseController.spr?action=view&purchaseId="
								+ businessId).forward(req, resp);
			} else if (modelName.indexOf("投资管理") != -1) {
				req.getRequestDispatcher(
						"investmentController.spr?action=view&infoId="
								+ businessId).forward(req, resp);
			} else if (modelName.indexOf("报废") != -1) {
				req.getRequestDispatcher(
						"scrappedController.spr?action=view&scrappedId="
								+ businessId).forward(req, resp);
			} else if (modelName.indexOf("仓储") != -1) {
				req.getRequestDispatcher(
						"storageProtocolController.spr?action=view&infoId="
								+ businessId).forward(req, resp);
			} else if (modelName.indexOf("退货") != -1) {
				req.getRequestDispatcher(
						"salesReturnController.spr?action=forView&id="
								+ businessId).forward(req, resp);
			} else if (modelName.indexOf("采购开票申请") != -1) {
				req.getRequestDispatcher(
						"purchaseBillController.spr?action=addPurchaseBillView&purchaseBillId="
								+ businessId + "&operType=001&tradeType=")
						.forward(req, resp);
			} else if (modelName.indexOf("利息发票开票申请") != -1) {
				req.getRequestDispatcher(
						"interestBillController.spr?action=interestBillView&interestBillId="
								+ businessId + "&operType=001&tradeType=")
						.forward(req, resp);
			} else if (modelName.indexOf("开票") != -1) {
				req.getRequestDispatcher(
						"billApplyController.spr?action=view_business&billApplyId="
								+ businessId + "&operType=001&tradeType=")
						.forward(req, resp);
			} else if (modelName.indexOf("保证金退回") != -1) {
				req.getRequestDispatcher(
						"depositRetreatController.spr?action=updateDepositRetreatView&depositRetreatId="
								+ businessId + "&operType=000").forward(req,
						resp);
			}else if(modelName.indexOf("客户主数据") != -1){
				req.getRequestDispatcher(
						"guestController.spr?action=forView&id="+businessId ).forward(req,resp);				
			}else if(modelName.indexOf("供应商主数据") != -1){
				req.getRequestDispatcher(
						"supplyController.spr?action=forView&id="+businessId ).forward(req,resp);				
			}else if(modelName.indexOf("物料主数据") != -1){
				req.getRequestDispatcher(
						"materialController.spr?action=forView&id="+businessId ).forward(req,resp);				
			}
			else if (modelName.indexOf("银行主数据申请") != -1) {
				req.getRequestDispatcher(
						"accountController.spr?action=modify&from=view&accountID="
								+ businessId).forward(req, resp);
			}
			else if (modelName.indexOf("信用证开证改证申请") != -1) {
				String creditId = creditEntryHisJdbcService.getCreditId(businessId);
				req.getRequestDispatcher(
						"creditEntryHisController.spr?action=modifyCreditHisInfo&operate=view&creditId="
								+ creditId).forward(req, resp);
			}

		}
	}

	/**
	 * 萃取URL参数
	 * 
	 * @param req
	 * @param parm
	 * @return parm对应的参数值
	 */
	private String extractFR(HttpServletRequest req, String parm) {
		try {
			String wait = java.net.URLDecoder.decode(req.getQueryString(),
					"UTF-8");
			int pos = wait.indexOf(parm) + parm.length() + 1;
			wait = wait.substring(pos);
			pos = wait.indexOf("&");
			if (pos != -1) {
				wait = wait.substring(0, pos);
			}
			return wait;
		} catch (UnsupportedEncodingException e) {
		}
		return "";
	}

	/**
	 * 旧信达系统，待办取数, 取的我的待办事项
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@Deprecated
	public void getMyProcessInstances2(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String strStart = request.getParameter("start");
		String strLimit = request.getParameter("limit");
		int start = Integer.valueOf(strStart == null ? "0" : strStart);
		int size = Integer.valueOf(strLimit == null ? "0" : strLimit);
		Map map = extractFR(request);
		String modalName = (String) map.get("modalName");
		String businessInfo = (String) map.get("businessInfo");
		String taskName = (String) map.get("taskName");

		StringBuffer sb = new StringBuffer();
		if (modalName != null && !"".equals(modalName.trim()))
			sb.append(" and model_name like '%").append(modalName).append("%'");
		if (businessInfo != null && !"".equals(businessInfo.trim()))
			sb.append(" and business_note like '%").append(businessInfo)
					.append("%'");
		if (taskName != null && !"".equals(taskName.trim()))
			sb.append(" and name_ like '%").append(taskName).append("%'");

		String actorId = sysWfCommonalityService.getUserAllActorId();
		// 是否职能部门 1是 2否
		String isFuncDept = UserContextHolder.getLocalUserContext()
				.getUserContext().getSysDept().getIsFuncDept();
		String appendSQL = " and 1=1";
		String strSql;
		String strCountSql;
		// 非职能部门职能看到自己部门的数据
		if ("2".equals(isFuncDept)) {
			String deptId = UserContextHolder.getLocalUserContext()
					.getUserContext().getSysDept().getDeptid();
			String userName = UserContextHolder.getLocalUserContext()
					.getUserContext().getSysUser().getUserName();
			// 部门经理可以看到所属部门下的业务记录，其他业务人员不允许看到
			if (sysWfCommonalityService.isManager()) {
				appendSQL = " and Department_Id='"
						+ deptId
						+ "' and ((t.ACTOR_NAME not like '%业务员%' or t.CREATOR='"
						+ userName
						+ "') or "
						+ "(Department_Id='"
						+ deptId
						+ "' and CREATOR not in (select u.user_name from t_sys_user u,t_sys_dept_user t where u.user_id=t.user_id and t.dept_id='"
						+ deptId + "')))";
			} else {
				appendSQL = " and( (Department_Id='"
						+ deptId
						+ "' and CREATOR='"
						+ userName
						+ "') or "
						+ "(Department_Id='"
						+ deptId
						+ "' and CREATOR not in (select u.user_name from t_sys_user u,t_sys_dept_user t where u.user_id=t.user_id and t.dept_id='"
						+ deptId + "')))";
			}
			strSql = "select * from (select rownum rnum,a.* "
					+ " from ( select  t.PROCESS_ID,t.START_,t.MODEL_ID,t.TASK_ID,t.BUSINESS_RECORD_ID,t.BUSINESS_NOTE,t.PROCESS_URL,t.Ins_Create_Time,t.MODEL_NAME,t.NAME_"
					+ " from V_SYS_WF_PROCESS_USER t " + "where ACTOR_ID in ("
					+ actorId + ")" + appendSQL + sb.toString()
					+ " order by ins_create_time desc) a )where rnum>" + start
					+ " and rnum<" + (start + size + 1);

			strCountSql = "select count(*) from (select t.PROCESS_ID,t.TASK_ID,t.BUSINESS_RECORD_ID,t.BUSINESS_NOTE,t.PROCESS_URL,t.Ins_Create_Time,t.MODEL_NAME,t.NAME_"
					+ " from V_SYS_WF_PROCESS_USER t "
					+ " where ACTOR_ID in ("
					+ actorId + ")" + appendSQL + sb.toString() + ")";
		} else {
			String processSql = sysWfCommonalityService.getUserProcessSql();
			String authSql = sysWfCommonalityService.getAuthProcessSql();

			strSql = "select * from (select rownum rnum,a.* "
					+ " from ( select  t.PROCESS_ID,t.START_,t.MODEL_ID,t.TASK_ID,t.BUSINESS_RECORD_ID,t.BUSINESS_NOTE,t.PROCESS_URL,t.Ins_Create_Time,t.MODEL_NAME,t.NAME_"
					+ " from V_SYS_WF_PROCESS_USER t " + "where 1=1 and ("
					+ processSql + authSql + ") " + sb.toString()
					+ " order by ins_create_time desc) a )where rnum>" + start
					+ " and rnum<" + (start + size + 1);

			strCountSql = "select count(*) from (select  t.PROCESS_ID,t.TASK_ID,t.BUSINESS_RECORD_ID,t.BUSINESS_NOTE,t.PROCESS_URL,t.Ins_Create_Time,t.MODEL_NAME,t.NAME_"
					+ " from V_SYS_WF_PROCESS_USER t "
					+ " where 1=1 and ("
					+ processSql + authSql + ") " + sb.toString() + ")";
		}

		int iResultCount = sysWfCommonalityService.getResultCount(strCountSql);

		List<CommonProcessInstance> instanceList = sysWfTaskActorService
				.getMyProcessInstances(strSql);

		JSONArray ja = JSONArray.fromObject(instanceList);

		JSONObject jo = new JSONObject();
		jo.put("totalProperty", iResultCount);
		jo.put("root", ja);

		String jsontext = jo.toString();
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		response.getWriter().print(jsontext);
	}

	public CreditEntryHisJdbcService getCreditEntryHisJdbcService() {
		return creditEntryHisJdbcService;
	}

	public void setCreditEntryHisJdbcService(
			CreditEntryHisJdbcService creditEntryHisJdbcService) {
		this.creditEntryHisJdbcService = creditEntryHisJdbcService;
	}

	/**
	 * 增加参与人信息
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void copyProcessConfig(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String poid = request.getParameter("poid");
		String copoid = request.getParameter("copoid");

		sysWfTaskActorService.copyProcessConfig(poid,copoid);

		this.operateSuccessfully(response);
	}
	
	/**
	 * 增加参与人信息
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void copyBDPProcessConfig(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String extProId = request.getParameter("extProId");
		String toExtProId = request.getParameter("toExtProId");
		extProcessDefinitionService.copyCreate(extProId, toExtProId);
		this.operateSuccessfully(response);
	}

	public ExtProcessDefinitionService getExtProcessDefinitionService() {
		return extProcessDefinitionService;
	}

	public void setExtProcessDefinitionService(
			ExtProcessDefinitionService extProcessDefinitionService) {
		this.extProcessDefinitionService = extProcessDefinitionService;
	}
}
