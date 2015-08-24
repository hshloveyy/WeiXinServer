package com.infolion.sapss.depositRetreat.web;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

import com.infolion.platform.component.ExtComponentServlet;
import com.infolion.platform.component.workflow.WorkflowService;
import com.infolion.platform.component.workflow.ext.TaskInstanceContext;
import com.infolion.platform.console.org.service.SysDeptService;
import com.infolion.platform.console.sys.context.UserContext;
import com.infolion.platform.console.sys.context.UserContextHolder;
import com.infolion.platform.console.workflow.dto.TaskHisDto;
import com.infolion.platform.console.workflow.service.SysWfUtilsService;
import com.infolion.platform.core.web.BaseMultiActionController;
import com.infolion.platform.sys.cache.SysCachePoolUtils;
import com.infolion.platform.util.CodeGenerator;
import com.infolion.platform.util.DateUtils;
import com.infolion.sapss.depositRetreat.domain.TDepositRetreat;
import com.infolion.sapss.depositRetreat.service.DepositRetreatService;
import com.infolion.sapss.payment.PaymentContants;

public class DepositRetreatController extends BaseMultiActionController{

	
	@Autowired
	private DepositRetreatService depositRetreatService;

	public void setDepositRetreatService(DepositRetreatService depositRetreatService)
	{
		this.depositRetreatService = depositRetreatService;
	}
	
	
	public ModelAndView depositRetreatManageView(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		UserContext userContext = UserContextHolder.getLocalUserContext()
				.getUserContext();
		String loginer = userContext.getSysUser().getUserId();
		request.setAttribute("loginer", loginer);

		return new ModelAndView("sapss/depositRetreat/depositRetreatManage");
	}
	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ModelAndView toCompositeQuery(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		UserContext userContext = UserContextHolder.getLocalUserContext()
				.getUserContext();
		String loginer = userContext.getSysUser().getUserId();
		request.setAttribute("loginer", loginer);

		return new ModelAndView("sapss/depositRetreat/compositeQuery");
	}

	public ModelAndView createDepositRetreat(HttpServletRequest request,
			HttpServletResponse response) throws IllegalAccessException,
			InvocationTargetException {

		request.setAttribute("isShow", "false");
		UserContext userContext = UserContextHolder.getLocalUserContext()
		.getUserContext();
		String realName = userContext.getSysUser().getRealName();
		request.setAttribute("realName", realName);
		// 操作类型
		String operType = "101";
		request.setAttribute("save", operType.substring(0, 1).equals("0"));
		request.setAttribute("submit", operType.substring(1, 2).equals("0"));
		request.setAttribute("close", operType.substring(2, 3).equals("0"));

		return new ModelAndView("sapss/depositRetreat/depositRetreatCreate");
	}

	public ModelAndView updateDepositRetreatView(HttpServletRequest request,
			HttpServletResponse response) throws IllegalAccessException,
			InvocationTargetException {
		
		String depositRetreatId = request.getParameter("depositRetreatId");
		TDepositRetreat deposit = depositRetreatService.getDepositRetreat(depositRetreatId);
		request.setAttribute("main", deposit);


		request.setAttribute("isShow", "1".equals(request
				.getParameter("isShow")));

		// 操作类型
		String operType = request.getParameter("operType");
		if (operType.length() < 3)
			operType = "111";

		request.setAttribute("save", operType.substring(0, 1).equals("0"));
		request.setAttribute("submit", operType.substring(1, 2).equals("0"));
		request.setAttribute("close", operType.substring(2, 3).equals("0"));

		return new ModelAndView("sapss/depositRetreat/depositRetreatCreate");
	}

	public void updateDepositRetreat(HttpServletRequest request,
			HttpServletResponse response, TDepositRetreat depositRetreat)
			throws IOException {
		depositRetreat.setIsValid("1");
		if (depositRetreat.getDepositRetreatId() == null
				|| "".equals(depositRetreat.getDepositRetreatId())) {
			UserContext userContext = UserContextHolder.getLocalUserContext()
					.getUserContext();
			depositRetreat.setDepositRetreatId(CodeGenerator.getUUID());
			depositRetreat.setCreaterTime(DateUtils.getCurrTime(2));
			depositRetreat.setCreator(userContext.getSysUser().getUserId());
			depositRetreat.setDeptId(userContext.getSysDept().getDeptid());
			depositRetreat.setExamState("1");
		} 
	    depositRetreatService.saveDepositRetreat(depositRetreat);

		JSONObject jo = new JSONObject();
		jo.put("returnMessage", "保存成功！");
		String jsontext = jo.toString();
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");

		this.operateSuccessfullyWithString(response, jsontext);
	}

	public void deleteDepositRetreat(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String depositRetreatId = request.getParameter("depositRetreatId");
		TDepositRetreat depositRetreat = depositRetreatService.getDepositRetreat(depositRetreatId);
		depositRetreat.setIsValid("0");
		depositRetreatService.saveDepositRetreat(depositRetreat);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("returnMessage", "删除成功!");
		String jsontext = jsonObject.toString();
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		this.operateSuccessfullyWithString(response, jsontext);
	}

	public void queryDepositRetreat(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		Map<String, String> filter = extractFR(request);
		String sql = getQueryDepositRetreatSql(filter);
		String grid_columns = "DEPOSIT_RETREAT_ID,APPLYER,APPLY_SUN,APPLY_ACCOUNT,APPLY_ACCOUNT_NAME,RECEIPT_ACCOUNT,RECEIPT_ACCOUNT_NAME,APPLY_DATE,APPLY_TIME,APPLYED_TIME," +
				              "EXAM_STATE,CREATOR,DEPT_ID,CREATER_TIME,IS_VALID,EXAM_STATE_D_EXAMINE_STATE";
		String grid_size = "10";

		request.setAttribute("grid_sql", sql);
		request.setAttribute("grid_columns", grid_columns);
		request.setAttribute("grid_size", grid_size);

		ExtComponentServlet extComponentServlet = new ExtComponentServlet();
		extComponentServlet.processGrid(request, response, true);
	}
	
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
	
	public String getQueryDepositRetreatSql(Map<String, String> filter) {
		String sql = "SELECT t.*,t.EXAM_STATE as EXAM_STATE_D_EXAMINE_STATE,a.title as apply_account_name,b.title as receipt_account_name " +
				" FROM t_Deposit_Retreat t left outer join bm_deposit_account  a on a.id =t.apply_account " +
				" left outer join bm_deposit_receipt_account b on b.id=t.receipt_account" +
				" where t.IS_VALID=1";
		if (filter != null && !filter.isEmpty()) {
			String applyer = filter.get("applyer");
			if (StringUtils.isNotBlank(applyer)) {
				sql += " and t.applyer like '%" + applyer + "%'";
			}
			String applyAccount = filter.get("applyAccount");
			if (StringUtils.isNotBlank(applyAccount)) {
				sql += " and t.apply_Account = '" + applyAccount + "'";
			}
			String receiptAccount = filter.get("receiptAccount");
			if (StringUtils.isNotBlank(receiptAccount)) {
				sql += " and t.receipt_Account = '" + receiptAccount + "'";
			}
			String sDate = filter.get("sDate");
			if (StringUtils.isNotBlank(sDate)) {
				sql += " and t.apply_date >= '" + sDate + "'";
			}
			String eDate = filter.get("eDate");
			if (StringUtils.isNotBlank(eDate)) {
				sql += " and t.apply_date <= '" + eDate + "'";
			}
		}
		// 部门的过滤
		UserContext userContext = UserContextHolder.getLocalUserContext()
				.getUserContext();
		sql += " and t.dept_id in ( "
				+ userContext.getGrantedDepartmentsId() + ")";
		sql += " order by t.creater_time desc";
		return sql;
	}
	
	public void submitAndSaveDepositRetreat(HttpServletRequest request,
			HttpServletResponse response, TDepositRetreat tDepositRetreat)
			throws IllegalAccessException, InvocationTargetException,
			IOException, Exception {
		String taskId = request.getParameter("workflowTaskId");

		depositRetreatService.submitAndSaveDepositRetreat(taskId, tDepositRetreat);
		JSONObject jo = new JSONObject();
		jo.put("returnMessage", "提交成功！");
		String jsontext = jo.toString();
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		this.operateSuccessfullyWithString(response, jsontext);
	}
	
	public void submitDepositRetreatWorkflow(HttpServletRequest request,
			HttpServletResponse response, TDepositRetreat tDepositRetreat)
			throws IllegalAccessException, InvocationTargetException,
			IOException, Exception {
		String taskId = request.getParameter("workflowTaskId");


		String msg = "";

		tDepositRetreat.setApplyedTime(DateUtils.getCurrTime(2));
		UserContext userContext = UserContextHolder.getLocalUserContext()
				.getUserContext();
		// 业务描述信息
		tDepositRetreat.setWorkflowBusinessNote(userContext.getSysDept()
				.getDeptname()
				+ "|"
				+ userContext.getSysUser().getRealName()
				+ "|保证金回退");

		String doWorkflow = request.getParameter("doWorkflow");
		if (doWorkflow == null && !"mainForm".equals(doWorkflow)) {
			String workflowLeaveTransitionName = request
					.getParameter("workflowLeaveTransitionName");
			String workflowExamine = request.getParameter("workflowExamine");
			tDepositRetreat.setWorkflowExamine(workflowExamine);
			tDepositRetreat.setWorkflowLeaveTransitionName(workflowLeaveTransitionName);
		}

		// 取得工作流节点名称

		JSONObject jo = new JSONObject();
		jo = this.depositRetreatService.submitDepositRetreatWorkflow(tDepositRetreat, taskId);

		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");

		this.operateSuccessfullyWithString(response, jo.toString());
	}
	
	public ModelAndView depositRetreatExamine(HttpServletRequest request,
			HttpServletResponse response) throws IllegalAccessException,
			InvocationTargetException {
		String depositRetreatId = request.getParameter("businessRecordId");
		String taskId = request.getParameter("taskId");

		TaskInstanceContext taskInstanceContext = WorkflowService
				.getTaskInstanceContext(taskId);
		String taskName = taskInstanceContext.getTaskName();

		request.setAttribute("businessRecordId", depositRetreatId);
		request.setAttribute("taskId", taskId);

		request.setAttribute("businessRecordId", depositRetreatId);
		
		TDepositRetreat deposit = this.depositRetreatService.getDepositRetreat(depositRetreatId);
        UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();

        if(userContext.getSysUser().getUserId().equals(deposit.getCreator())&&taskName.indexOf("修改")>=0){
 		   request.setAttribute("iframeSrc","depositRetreatController.spr?action=updateDepositRetreatView&operType=100&depositRetreatId="+depositRetreatId);
        }
        else {
		   request.setAttribute("iframeSrc","depositRetreatController.spr?action=updateDepositRetreatView&operType=000&depositRetreatId="+depositRetreatId);
        }
        if(taskName.indexOf("出纳")>=0){
        	request.setAttribute("printHandle", "true");
        }
		request.setAttribute("submitUrl","depositRetreatController.spr?action=submitDepositRetreatWorkflow");
		return new ModelAndView("sapss/depositRetreat/workflowExamPage");
	}
	@Autowired
	private SysWfUtilsService sysWfUtilsService;
	@Autowired
	private SysDeptService sysDeptService;
	public SysWfUtilsService getSysWfUtilsService() {
		return sysWfUtilsService;
	}
	

	public void setSysWfUtilsService(SysWfUtilsService sysWfUtilsService) {
		this.sysWfUtilsService = sysWfUtilsService;
	}
	public ModelAndView dealPrint(HttpServletRequest request,
			HttpServletResponse response) throws IllegalAccessException,
			InvocationTargetException {
		
		String depositRetreatId = request.getParameter("depositRetreatId");
		TDepositRetreat deposit = depositRetreatService.getDepositRetreat(depositRetreatId);
		deposit.setApplyAccount(SysCachePoolUtils.getDictDataValue("BM_DEPOSIT_ACCOUNT", deposit.getApplyAccount()));
		deposit.setReceiptAccount(SysCachePoolUtils.getDictDataValue("BM_DEPOSIT_RECEIPT_ACCOUNT", deposit.getReceiptAccount()));
		request.setAttribute("deptName", sysDeptService.queryDeptById(deposit.getDeptId()).getDeptname());
		request.setAttribute("main", deposit);
		request.setAttribute("payBig",PaymentContants.changeToBig(Double.parseDouble(deposit.getApplySun())));
		List<TaskHisDto> listDto = sysWfUtilsService.queryTaskHisList(depositRetreatId);
		request.setAttribute("taskHis", listDto);
		return new ModelAndView("sapss/depositRetreat/print");
	}


	public SysDeptService getSysDeptService() {
		return sysDeptService;
	}


	public void setSysDeptService(SysDeptService sysDeptService) {
		this.sysDeptService = sysDeptService;
	}


}
