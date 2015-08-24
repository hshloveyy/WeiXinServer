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
import com.infolion.platform.console.sys.context.UserContext;
import com.infolion.platform.console.sys.context.UserContextHolder;
import com.infolion.platform.core.web.BaseMultiActionController;
import com.infolion.platform.util.CodeGenerator;
import com.infolion.platform.util.DateUtils;
import com.infolion.sapss.account.domain.TProfitCenterInfo;
import com.infolion.sapss.account.service.ProfitCenterHibernateService;

public class ProfitCenterController extends BaseMultiActionController {
	@Autowired
	private ProfitCenterHibernateService profitCenterHibernateService;

	public void setProfitCenterHibernateService(
			ProfitCenterHibernateService profitCenterHibernateService) {
		this.profitCenterHibernateService = profitCenterHibernateService;
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
		return new ModelAndView("sapss/account/profitManager");
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
				.append("select tpinfo.*,to_date(apply_time,'yyyy-mm-dd hh24:mi:ss') applyTime,approve_state PROFIT_STATE_D_PROFIT_STATE from t_profit_center_info tpinfo where is_available='1' ");
		if (map.get("companyCode") != null)
			sb.append(" and company_Code='" + map.get("companyCode") + "'");
		if (map.get("profitCenterName") != null)
			sb.append(" and profit_Center_Name like '%"
					+ map.get("profitCenterName") + "%'");
		if (map.get("profitGroupName") != null)
			sb.append(" and profit_Group_Name like '%"
					+ map.get("profitGroupName") + "%'");
		if (map.get("upProfitGroup") != null)
			sb.append(" and up_Profit_Group like '%" + map.get("upProfitGroup")
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
		String grid_columns = "PROFIT_ID,COMPANY_CODE,PROFIT_CENTER_NAME,PROFIT_CENTER_NO,UP_PROFIT_GROUP,PROFIT_GROUP_NAME,PROFIT_GROUP_NO,APPLY_TIME,PROFIT_STATE_D_PROFIT_STATE,APPROVED_TIME,CREATOR_NAME,CREATOR_ID";
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
		TProfitCenterInfo info = new TProfitCenterInfo();
		info.setOrgName(userContext.getSysDept().getDeptname());
		info.setOrgID(userContext.getSysDept().getDeptid());
		info.setCreatorID(userContext.getSysUser().getUserId());
		info.setCreatorName(userContext.getSysUser().getRealName());
		String id = CodeGenerator.getUUID();
		info.setProfitID(id);
		request.setAttribute("main", info);
		request.setAttribute("from", "create");
		request.setAttribute("revisable", "true");
		request.setAttribute("submit", false);
		request.setAttribute("save", true);
		return new ModelAndView("sapss/account/profitCreate");
	}

	public void save(HttpServletRequest request, HttpServletResponse response,
			TProfitCenterInfo info) throws IllegalAccessException,
			InvocationTargetException, IOException {
		String profitID = request.getParameter("pid");
		response.setCharacterEncoding("GBK");
		String from = (String) request.getParameter("from");
		response.setContentType("text ml; charset=GBK");
		if ("create".equals(from)) {
			info.setCreateTime(DateUtils.getCurrTime(2));
			info.setIsAvailable("1");
			info.setApproveState("1");
			this.profitCenterHibernateService.saveTProfitCenterInfo(info);

		} else {
			this.profitCenterHibernateService.updateTProfitCenterInfo(info);
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
		String profitID = req.getParameter("profitID");
		req.setAttribute("profitID", profitID);
		String from = (String) req.getParameter("from");
		req.setAttribute("popfrom", from);
		TProfitCenterInfo info = this.profitCenterHibernateService
				.getTProfitCenterInfo(profitID);
		req.setAttribute("main", info);
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
		return new ModelAndView("sapss/account/profitCreate");
	}

	public void delete(HttpServletRequest req, HttpServletResponse resp)
			throws Exception {
		String profitID = (String) req.getParameter("profitID");
		boolean bb = this.profitCenterHibernateService
				.deleteTProfitCenterInfo(profitID);
		resp.setCharacterEncoding("GBK");
		resp.setContentType("text/html; charset=GBK");
		if (bb)
			resp.getWriter().print("{success:true,msg:'删除成功'}");
		else {
			resp.getWriter().print("{success:false,msg:'记录不能删除'}");
		}
	}

	public void submitProfitInfo(HttpServletRequest request,
			HttpServletResponse response) throws IllegalAccessException,
			InvocationTargetException, IOException, Exception {
		String msg = "";
		try {
			String profitID = request.getParameter("profitID");
			String taskId = request.getParameter("workflowTaskId");
			TProfitCenterInfo info = profitCenterHibernateService
					.getTProfitCenterInfo(profitID);
			info.setWorkflowTaskId(taskId);
			String workflowLeaveTransitionName = request
					.getParameter("workflowLeaveTransitionName");
			info.setWorkflowLeaveTransitionName(workflowLeaveTransitionName);
			String workflowExamine = request.getParameter("workflowExamine");
			info.setWorkflowExamine(workflowExamine);
			info.setProfitID(profitID);
			this.profitCenterHibernateService.submitWorkflow(taskId, info);
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
	public ModelAndView profitExamine(HttpServletRequest req,
			HttpServletResponse resp) throws IllegalAccessException,
			InvocationTargetException {
		String profitID = req.getParameter("businessRecordId");
		String taskId = req.getParameter("taskId");
		req.setAttribute("businessRecordId", profitID);
		req.setAttribute("taskId", taskId);
		req.setAttribute("iframeSrc",
				"profitCenterController.spr?action=modify&from=iframe&profitID="
						+ profitID);
		req.setAttribute("submitUrl", "profitCenterController.spr");
		req.setAttribute("action", "submitProfitInfo");
		req.setAttribute("iframeForms",
				"Form.serialize(document.frames['content']['mainForm'])");
		return new ModelAndView("sapss/account/profitWorkflowManager");
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
