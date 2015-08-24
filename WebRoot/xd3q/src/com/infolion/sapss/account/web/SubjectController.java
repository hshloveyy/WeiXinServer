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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

import com.infolion.platform.component.ExtComponentServlet;
import com.infolion.platform.console.sys.context.UserContext;
import com.infolion.platform.console.sys.context.UserContextHolder;
import com.infolion.platform.core.web.BaseMultiActionController;
import com.infolion.platform.util.DateUtils;
import com.infolion.sapss.account.domain.TSubjectInfo;
import com.infolion.sapss.account.service.SubjectHibernateService;
import com.infolion.sapss.account.service.SubjectJdbcService;
import com.infolion.sapss.project.web.ProjectController;

public class SubjectController extends BaseMultiActionController {
	private final Log log = LogFactory.getLog(ProjectController.class);
	@Autowired
	private SubjectHibernateService subjectHibernateService;

	public void setSubjectHibernateService(
			SubjectHibernateService subjectHibernateService) {
		this.subjectHibernateService = subjectHibernateService;
	}

	@Autowired
	private SubjectJdbcService subjectJdbcService;

	public void setSubjectJdbcService(SubjectJdbcService subjectJdbcService) {
		this.subjectJdbcService = subjectJdbcService;
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
		return new ModelAndView("sapss/account/subjectManager");
	}

	// 查询
	// 条件：subjectNO_SAP,shortDescription,subjectState
	public void find(HttpServletRequest req, HttpServletResponse resp)
			throws Exception {
		ExtComponentServlet servlet = new ExtComponentServlet();
		UserContext userContext = UserContextHolder.getLocalUserContext()
				.getUserContext();
		Map<String, String> map = extractFR(req);
		StringBuffer sb = new StringBuffer();
		sb
				.append("select tsinfo.*,to_date(apply_time,'yyyy-mm-dd hh24:mi:ss') applyTime,approve_state SUBJECT_STATE_D_SUBJECT_STATE from t_subject_info tsinfo where is_available='1' ");
		// sb.append("and org_id='");
		// sb.append(userContext.getSysDept().getDeptid() + "'");

		if (map.get("subjectNO_SAP") != null)
			sb.append(" and subject_NO_SAP like '%" + map.get("subjectNO_SAP")
					+ "%'");
		if (map.get("shortDescription") != null)
			sb.append(" and short_Description like '%"
					+ map.get("shortDescription") + "%'");
		if (map.get("state") != null)
			sb.append(" and approve_state = '" + map.get("state") + "'");
		String mgtDeptId = UserContextHolder.getLocalUserContext()
				.getUserContext().getGrantedDepartmentsId();
		// 查看自己所在部门的、以及自己有数据权限的部门记录
		sb.append(" and (org_id='" + userContext.getSysDept().getDeptid()
				+ "' or org_id in (" + mgtDeptId + "))");
		sb.append(" order by apply_time desc");

		String grid_sql = sb.toString();
		String grid_columns = "SUBJECT_ID,SUBJECT_NO_SAP,SHORT_DESCRIPTION,ACCOUNT_GROUP,CURRENCY,COL_STATE_GROUP,APPLY_TIME,SUBJECT_STATE_D_SUBJECT_STATE,APPROVED_TIME,CREATOR_NAME,CREATOR_ID";
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
		TSubjectInfo info = new TSubjectInfo();
		info.setOrgName(userContext.getSysDept().getDeptname());
		info.setOrgID(userContext.getSysDept().getDeptid());
		info.setCreatorID(userContext.getSysUser().getUserId());
		info.setCreatorName(userContext.getSysUser().getRealName());
		request.setAttribute("main", info);
		request.setAttribute("revisable", "true");
		request.setAttribute("save_button_enable", false);
		return new ModelAndView("sapss/account/subjectCreate");

	}

	public void save(HttpServletRequest request, HttpServletResponse response,
			TSubjectInfo info) throws IllegalAccessException,
			InvocationTargetException, IOException {
		String subjectID = request.getParameter("pid");
		response.setCharacterEncoding("GBK");
		response.setContentType("text ml; charset=GBK");
		if (subjectID == null || "".equals(subjectID)) {
			info.setCreateTime(DateUtils.getCurrTime(2));
			info.setIsAvailable("1");
			info.setApproveState("1");
			subjectID = this.subjectHibernateService.saveSubjectInfo(info);
			response.getWriter().print(
					"{success:true,subjectID:'" + subjectID + "'}");

		} else {
			TSubjectInfo temp = this.subjectHibernateService
					.getTSubjectInfo(subjectID);
			temp.setOrgID(info.getOrgID());
			temp.setOrgName(info.getOrgName());
			temp.setCompanyCode(info.getCompanyCode());
			temp.setCreatorID(info.getCreatorID());
			temp.setCreatorName(info.getCreatorName());
			temp.setTel(info.getTel());
			temp.setEmail(info.getEmail());
			temp.setSubjectNO_SAP(info.getSubjectNO_SAP());
			temp.setAccountGroup(info.getAccountGroup());
			temp.setShortDescription(info.getShortDescription());
			temp.setLongDescription(info.getLongDescription());
			this.subjectHibernateService.updateSubjectInfo(temp);
			response.getWriter().print(
					"{success:true,subjectID:'" + subjectID + "'}");
		}
	}

	public ModelAndView modify(HttpServletRequest req, HttpServletResponse resp)
			throws IllegalAccessException, InvocationTargetException {
		String subjectID = req.getParameter("subjectID");
		req.setAttribute("subjectID", subjectID);
		String from = (String) req.getParameter("from");
		req.setAttribute("popfrom", from);
		TSubjectInfo info = this.subjectHibernateService
				.getTSubjectInfo(subjectID);
		req.setAttribute("main", info);
		return new ModelAndView("sapss/account/subjectCreate");
	}

	public void delete(HttpServletRequest req, HttpServletResponse resp)
			throws Exception {
		String subjectID = (String) req.getParameter("subjectID");
		boolean bb = this.subjectHibernateService.deleteSubjectInfo(subjectID);
		resp.setCharacterEncoding("GBK");
		resp.setContentType("text/html; charset=GBK");
		if (bb)
			resp.getWriter().print("{success:true,msg:'删除成功'}");
		else {
			resp.getWriter().print("{success:false,msg:'记录不能删除'}");
		}
	}

	public void submitSubjectInfo(HttpServletRequest request,
			HttpServletResponse response) throws IllegalAccessException,
			InvocationTargetException, IOException, Exception {
		String msg = "";
		try {
			String subjectID = request.getParameter("subjectID");
			String taskId = request.getParameter("workflowTaskId");
			TSubjectInfo info = subjectHibernateService
					.getTSubjectInfo(subjectID);
			info.setWorkflowTaskId(taskId);
			String workflowExamine = request.getParameter("workflowExamine");
			info.setWorkflowExamine(workflowExamine);
			String workflowLeaveTransitionName = request
					.getParameter("workflowLeaveTransitionName");
			info.setWorkflowLeaveTransitionName(workflowLeaveTransitionName);
			info.setSubjectID(subjectID);
			this.subjectHibernateService.submitWorkflow(taskId, info);
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
	public ModelAndView subjectExamine(HttpServletRequest req,
			HttpServletResponse resp) throws IllegalAccessException,
			InvocationTargetException {
		String subjectID = req.getParameter("businessRecordId");
		String taskId = req.getParameter("taskId");
		req.setAttribute("businessRecordId", subjectID);
		req.setAttribute("taskId", taskId);
		req.setAttribute("iframeSrc",
				"subjectController.spr?action=modify&from=iframe&subjectID="
						+ subjectID);
		req.setAttribute("submitUrl", "subjectController.spr");
		req.setAttribute("action", "submitSubjectInfo");
		req.setAttribute("iframeForms",
				"Form.serialize(document.frames['content']['mainForm'])");
		return new ModelAndView("sapss/account/subjectWorkflowManager");
	}

	public void saveAdditionalInfo(HttpServletRequest request,
			HttpServletResponse response, SubjectAdditionalVO info)
			throws IllegalAccessException, InvocationTargetException,
			IOException {
		String subjectID = request.getParameter("subjectID");
		response.setCharacterEncoding("GBK");
		response.setContentType("text ml; charset=GBK");
		if (subjectID == null || "".equals(subjectID)) {
			response.getWriter().print(
					"{success:false,subjectID:'" + subjectID + "'}");

		} else {
			this.subjectJdbcService.saveAdditionalInfo(info);
			response.getWriter().print(
					"{success:true,subjectID:'" + subjectID + "'}");
		}
	}

	public void saveFinalInfo(HttpServletRequest request,
			HttpServletResponse response, SubjectFinalVO info)
			throws IllegalAccessException, InvocationTargetException,
			IOException {
		String subjectID = request.getParameter("subjectID");
		response.setCharacterEncoding("GBK");
		response.setContentType("text ml; charset=GBK");
		if (subjectID == null || "".equals(subjectID)) {
			response.getWriter().print(
					"{success:false,subjectID:'" + subjectID + "'}");

		} else {
			this.subjectJdbcService.saveFinalInfo(info);
			response.getWriter().print(
					"{success:true,subjectID:'" + subjectID + "'}");
		}
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
