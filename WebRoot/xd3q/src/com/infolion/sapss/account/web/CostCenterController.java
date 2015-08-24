package com.infolion.sapss.account.web;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

import com.infolion.platform.component.ExtComponentServlet;
import com.infolion.platform.component.dictionary.DictionaryRow;
import com.infolion.platform.console.sys.context.UserContext;
import com.infolion.platform.console.sys.context.UserContextHolder;
import com.infolion.platform.core.web.BaseMultiActionController;
import com.infolion.platform.sys.cache.SysCachePoolUtils;
import com.infolion.platform.util.CodeGenerator;
import com.infolion.platform.util.DateUtils;
import com.infolion.sapss.account.domain.TCostCenterInfo;
import com.infolion.sapss.account.service.CostCenterHibernateService;

public class CostCenterController extends BaseMultiActionController {
	@Autowired
	private CostCenterHibernateService costCenterHibernateService;

	public void setCostCenterHibernateService(
			CostCenterHibernateService costCenterHibernateService) {
		this.costCenterHibernateService = costCenterHibernateService;
	}

	// 默认方法
	public ModelAndView defaultMethod(HttpServletRequest request,
			HttpServletResponse response) throws IllegalAccessException,
			InvocationTargetException {
		return new ModelAndView();
	}

	// 到列表页面
	public ModelAndView toManager(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UserContext userContext = UserContextHolder.getLocalUserContext()
				.getUserContext();
		request.setAttribute("loginer", userContext.getSysUser().getUserId());
		return new ModelAndView("sapss/account/costManager");
	}

	// 查询
	public void find(HttpServletRequest req, HttpServletResponse resp)
			throws Exception {
		ExtComponentServlet servlet = new ExtComponentServlet();
		UserContext userContext = UserContextHolder.getLocalUserContext()
				.getUserContext();
		Map<String, String> map = extractFR(req);
		StringBuffer sb = new StringBuffer();
		sb
				.append("select tpinfo.*,to_date(apply_time,'yyyy-mm-dd hh24:mi:ss') applyTime,approve_state COST_STATE_D_COST_STATE from t_cost_center_info tpinfo where is_available='1' ");
		if (map.get("companyCode") != null)
			sb.append(" and company_Code='" + map.get("companyCode") + "'");
		if (map.get("costCenterName") != null)
			sb.append(" and cost_Center_Name like '%"
					+ map.get("costCenterName") + "%'");
		if (map.get("upCostGroup") != null)
			sb.append(" and up_Cost_Group like '%" + map.get("upCostGroup")
					+ "%'");
		if (map.get("costGroupType") != null)
			sb.append(" and cost_group_type like '%" + map.get("costGroupType")
					+ "%'");
		if (map.get("isChangeStandard") != null)
			sb.append(" and is_Change_Standard='" + map.get("isChangeStandard")
					+ "'");
		if (map.get("state") != null)
			sb.append(" and approve_state = '" + map.get("state") + "'");
		String mgtDeptId = UserContextHolder.getLocalUserContext()
				.getUserContext().getGrantedDepartmentsId();
		// 查看自己所在部门的、以及自己有数据权限的部门记录
		sb.append(" and (org_id='" + userContext.getSysDept().getDeptid()
				+ "' or org_id in (" + mgtDeptId + "))");
		sb.append(" order by apply_time desc");

		String grid_sql = sb.toString();
		String grid_columns = "COST_ID,COMPANY_CODE,COST_CENTER_NAME,COST_CENTER_NO,UP_COST_GROUP,COST_CENTER_TYPE,APPLY_TIME,COST_STATE_D_COST_STATE,APPROVED_TIME,CREATOR_NAME,CREATOR_ID";
		String grid_size = "10";
		req.setAttribute("grid_sql", grid_sql);
		req.setAttribute("grid_columns", grid_columns);
		req.setAttribute("grid_size", grid_size);
		servlet.processGrid(req, resp, true);
	}

	public ModelAndView create(HttpServletRequest request,
			HttpServletResponse response) throws IllegalAccessException,
			InvocationTargetException {
		UserContext userContext = UserContextHolder.getLocalUserContext()
				.getUserContext();
		TCostCenterInfo info = new TCostCenterInfo();
		info.setOrgName(userContext.getSysDept().getDeptname());
		info.setOrgID(userContext.getSysDept().getDeptid());
		info.setCreatorID(userContext.getSysUser().getUserId());
		info.setCreatorName(userContext.getSysUser().getRealName());
		String id = CodeGenerator.getUUID();
		info.setCostID(id);
		Map<String, DictionaryRow> sysDictMap = SysCachePoolUtils
				.getDictTableGroup("BM_FUNCTION_RANGE");
		request.setAttribute("bm_function_range", sysDictMap);
		request.setAttribute("main", info);
		request.setAttribute("from", "create");
		request.setAttribute("revisable", "true");
		request.setAttribute("submit", false);
		request.setAttribute("save", true);
		return new ModelAndView("sapss/account/costCreate");
	}

	public void save(HttpServletRequest request, HttpServletResponse response,
			TCostCenterInfo info) throws IllegalAccessException,
			InvocationTargetException, IOException {
		response.setCharacterEncoding("GBK");
		String from = (String) request.getParameter("from");
		response.setContentType("text ml; charset=GBK");
		if ("create".equals(from)) {
			info.setCreateTime(DateUtils.getCurrTime(2));
			info.setIsAvailable("1");
			info.setApproveState("1");
			this.costCenterHibernateService.saveTCostCenterInfo(info);

		} else {
			this.costCenterHibernateService.updateTCostCenterInfo(info);
		}
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("returnMessage", "保存成功！");
		String jsonText = jsonObject.toString();
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		this.operateSuccessfullyWithString(response, jsonText);
	}

	public ModelAndView modify(HttpServletRequest req, HttpServletResponse resp)
			throws IllegalAccessException, InvocationTargetException {
		String costID = req.getParameter("costID");
		req.setAttribute("costID", costID);
		String from = (String) req.getParameter("from");
		req.setAttribute("popfrom", from);
		TCostCenterInfo info = this.costCenterHibernateService
				.getTCostCenterInfo(costID);
		req.setAttribute("main", info);
		Map<String, DictionaryRow> sysDictMap = SysCachePoolUtils
				.getDictTableGroup("BM_FUNCTION_RANGE");
		req.setAttribute("bm_function_range", sysDictMap);
		String functionRange = info.getFunctionRange();
		req.setAttribute("functionRange", functionRange);
		if (from.equals("workflow")) {
			req.setAttribute("submit", true);
			req.setAttribute("save", false);
			req.setAttribute("revisable", "true");
		} else if (from.equals("view")) {
			req.setAttribute("submit", false);
			req.setAttribute("save", false);
			req.setAttribute("revisable", "false");
		} else if (from.equals("iframe") || from.equals("modify")) {
			req.setAttribute("submit", false);
			req.setAttribute("save", true);
			req.setAttribute("revisable", "true");
		}
		return new ModelAndView("sapss/account/costCreate");
	}

	public void delete(HttpServletRequest req, HttpServletResponse resp)
			throws Exception {
		String costID = (String) req.getParameter("costID");
		boolean bb = this.costCenterHibernateService
				.deleteTCostCenterInfo(costID);
		resp.setCharacterEncoding("GBK");
		resp.setContentType("text/html; charset=GBK");
		if (bb)
			resp.getWriter().print("{success:true,msg:'删除成功'}");
		else {
			resp.getWriter().print("{success:false,msg:'记录不能删除'}");
		}
	}

	public void submitCostInfo(HttpServletRequest request,
			HttpServletResponse response) throws IllegalAccessException,
			InvocationTargetException, IOException, Exception {
		String msg = "";
		try {
			String costID = request.getParameter("costID");
			String taskId = request.getParameter("workflowTaskId");
			TCostCenterInfo info = costCenterHibernateService
					.getTCostCenterInfo(costID);
			info.setWorkflowTaskId(taskId);
			String workflowLeaveTransitionName = request
					.getParameter("workflowLeaveTransitionName");
			info.setWorkflowLeaveTransitionName(workflowLeaveTransitionName);
			String workflowExamine = request.getParameter("workflowExamine");
			info.setWorkflowExamine(workflowExamine);
			info.setCostID(costID);
			this.costCenterHibernateService.submitWorkflow(taskId, info);
		} catch (Exception e) {
			msg = "异常类:" + e.getClass().getName() + "<br>异常信息:"
					+ e.getMessage();
			e.printStackTrace();
		}
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		JSONObject jo = new JSONObject();
		if ("".equals(msg))
			jo.put("returnMessage", "提交成功！");
		else
			jo.put("returnMessage", msg);
		this.operateSuccessfullyWithString(response, jo.toString());

	}

	// 审批界面
	public ModelAndView costExamine(HttpServletRequest req,
			HttpServletResponse resp) throws IllegalAccessException,
			InvocationTargetException {
		String costID = req.getParameter("businessRecordId");
		String taskId = req.getParameter("taskId");
		req.setAttribute("businessRecordId", costID);
		req.setAttribute("taskId", taskId);
		req.setAttribute("iframeSrc",
				"costCenterController.spr?action=modify&from=iframe&costID="
						+ costID);
		req.setAttribute("submitUrl", "costCenterController.spr");
		req.setAttribute("action", "submitCostInfo");
		req.setAttribute("iframeForms",
				"Form.serialize(document.frames['content']['mainForm'])");
		return new ModelAndView("sapss/account/costWorkflowManager");
	}

	public void findCostCenterType(HttpServletRequest request,
			HttpServletResponse response) throws IllegalAccessException,
			IOException, InvocationTargetException {
		String functionRange = request.getParameter("functionRange");
		// DictionaryRow temp =
		// this.costCenterHibernateService.findDictionaryRow(functionRange);
		String cmd = this.costCenterHibernateService
				.findCostCenterType(functionRange);
		response.setContentType("text/html; charset=GBK");
		JSONObject jo = new JSONObject();
		jo.put("returnMessage", cmd);
		this.operateSuccessfullyWithString(response, jo.toString());
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
}
