package com.infolion.sapss.account.web;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

import com.infolion.platform.component.ExtComponentServlet;
import com.infolion.platform.component.workflow.WorkflowService;
import com.infolion.platform.console.sys.context.UserContext;
import com.infolion.platform.console.sys.context.UserContextHolder;
import com.infolion.platform.console.workflow.dto.TaskHisDto;
import com.infolion.platform.console.workflow.service.SysWfUtilsService;
import com.infolion.platform.core.web.BaseMultiActionController;
import com.infolion.platform.util.CodeGenerator;
import com.infolion.platform.util.DateUtils;
import com.infolion.sapss.account.domain.TInnerTransferDetail;
import com.infolion.sapss.account.domain.TInnerTransferInfo;
import com.infolion.sapss.account.service.InnerTransferHibernateService;
import com.infolion.sapss.account.service.InnerTransferJdbcService;

public class InnerTransferController extends BaseMultiActionController {
	@Autowired
	private InnerTransferHibernateService innerTransferHibernateService;

	public void setInnerTransferHibernateService(
			InnerTransferHibernateService innerTransferHibernateService) {
		this.innerTransferHibernateService = innerTransferHibernateService;
	}

	@Autowired
	private InnerTransferJdbcService innerTransferJdbcService;

	public void setInnerTransferJdbcService(
			InnerTransferJdbcService innerTransferJdbcService) {
		this.innerTransferJdbcService = innerTransferJdbcService;
	}

	@Autowired
	private SysWfUtilsService sysWfUtilsService;

	public void setSysWfUtilsService(SysWfUtilsService sysWfUtilsService) {
		this.sysWfUtilsService = sysWfUtilsService;
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
		return new ModelAndView("sapss/account/transferManager");
	}

	// 查询
	// 条件：payer,payAccount,payType,receiver,receiveAccount,state
	public void find(HttpServletRequest req, HttpServletResponse resp)
			throws Exception {
		ExtComponentServlet servlet = new ExtComponentServlet();
		UserContext userContext = UserContextHolder.getLocalUserContext()
				.getUserContext();
		Map<String, String> map = extractFR(req);
		StringBuffer sb = new StringBuffer();
		sb
				.append("select tainfo.*,to_date(apply_time,'yyyy-mm-dd hh24:mi:ss') applyTime,approve_state TRANS_STATE_D_TRANS_STATE from t_inner_transfer tainfo where is_available='1' ");
		if (map.get("payer") != null)
			sb.append(" and payer like '%" + map.get("payer") + "%'");
		if (map.get("payType") != null)
			sb.append(" and pay_Type like '%" + map.get("payType") + "%'");
		if (map.get("state") != null)
			sb.append(" and approve_state = '" + map.get("state") + "'");
		String mgtDeptId = UserContextHolder.getLocalUserContext()
				.getUserContext().getGrantedDepartmentsId();
		// 查看自己所在部门的、以及自己有数据权限的部门记录
		sb.append(" and (org_id='" + userContext.getSysDept().getDeptid()
				+ "' or org_id in (" + mgtDeptId + "))");
		sb.append(" order by apply_time desc");

		String grid_sql = sb.toString();
		String grid_columns = "TRANSFER_ID,PAYER,RECEIVER,PAY_TYPE,APPLY_TIME,TRANS_STATE_D_TRANS_STATE,APPROVED_TIME,CREATOR_NAME,CREATOR_ID";
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
		TInnerTransferInfo info = new TInnerTransferInfo();
		String transferID = CodeGenerator.getUUID();
		info.setTransferID(transferID);
		info.setOrgName(userContext.getSysDept().getDeptname());
		info.setOrgID(userContext.getSysDept().getDeptid());
		info.setCreatorID(userContext.getSysUser().getUserId());
		info.setCreatorName(userContext.getSysUser().getRealName());
		info.setApproveState("1");
		info.setCreateTime(DateUtils.getCurrTime(2));
		info.setIsAvailable("1");
		this.innerTransferHibernateService.saveTInnerTransferInfo(info);
		request.setAttribute("main", info);
		request.setAttribute("revisable", "true");
		request.setAttribute("transferID", transferID);
		request.setAttribute("submit", false);
		request.setAttribute("save", true);
		request.setAttribute("save_button_enable", false);
		return new ModelAndView("sapss/account/transferCreate");
	}

	public void save(HttpServletRequest request, HttpServletResponse response,
			TInnerTransferInfo info) throws IllegalAccessException,
			InvocationTargetException, IOException {
		this.innerTransferHibernateService.updateTInnerTransferInfo(info);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("returnMessage", "保存成功！");
		String jsonText = jsonObject.toString();
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		this.operateSuccessfullyWithString(response, jsonText);
	}

	public ModelAndView modify(HttpServletRequest req, HttpServletResponse resp)
			throws IllegalAccessException, InvocationTargetException {
		String transferID = req.getParameter("transferID");
		req.setAttribute("transferID", transferID);
		String from = (String) req.getParameter("from");
		req.setAttribute("popfrom", from);
		TInnerTransferInfo info = this.innerTransferHibernateService
				.getTInnerTransferInfo(transferID);
		req.setAttribute("main", info);
		if (from.equals("workflow")) {
			req.setAttribute("submit", true);
			req.setAttribute("save", false);
		} else if (from.equals("view")) {
			req.setAttribute("submit", false);
			req.setAttribute("save", false);
		} else if (from.equals("iframe") || from.equals("modify")) {
			req.setAttribute("submit", false);
			req.setAttribute("save", true);
		}
		return new ModelAndView("sapss/account/transferCreate");
	}

	public void delete(HttpServletRequest req, HttpServletResponse response)
			throws Exception {
		String transferID = (String) req.getParameter("transferID");
		boolean bb = this.innerTransferHibernateService
				.deleteTInnerTransferInfo(transferID);
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		if (bb)
			response.getWriter().print("{success:true,msg:'删除成功'}");
		else {
			response.getWriter().print("{success:false,msg:'记录不能删除'}");
		}
	}

	public void submitTransferInfo(HttpServletRequest request,
			HttpServletResponse response) throws IllegalAccessException,
			InvocationTargetException, IOException, Exception {
		String msg = "";
		try {
			String transferID = request.getParameter("transferID");
			String taskId = request.getParameter("workflowTaskId");
			TInnerTransferInfo info = innerTransferHibernateService
					.getTInnerTransferInfo(transferID);
			info.setWorkflowTaskId(taskId);
			String workflowExamine = request.getParameter("workflowExamine");
			info.setWorkflowExamine(workflowExamine);
			String workflowLeaveTransitionName = request
					.getParameter("workflowLeaveTransitionName");
			info.setWorkflowLeaveTransitionName(workflowLeaveTransitionName);
			info.setTransferID(transferID);
			this.innerTransferHibernateService.submitWorkflow(taskId, info);
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
	public ModelAndView transferExamine(HttpServletRequest req,
			HttpServletResponse resp) throws IllegalAccessException,
			InvocationTargetException {
		String transferID = req.getParameter("businessRecordId");
		String taskId = req.getParameter("taskId");
		req.setAttribute("businessRecordId", transferID);
		req.setAttribute("taskId", taskId);
		req.setAttribute("taskName", WorkflowService.getTaskInstanceContext(
				taskId).getTaskName());
		req.setAttribute("iframeSrc",
				"innerTransferController.spr?action=modify&from=iframe&transferID="
						+ transferID);
		req.setAttribute("submitUrl", "innerTransferController.spr");
		req.setAttribute("action", "submitTransferInfo");
		req.setAttribute("iframeForms",
				"Form.serialize(document.frames['content']['mainForm'])");
		return new ModelAndView("sapss/account/transferWorkflowManager");
	}

	public ModelAndView print(HttpServletRequest req, HttpServletResponse resp)
			throws IllegalAccessException, InvocationTargetException {
		String transferID = req.getParameter("transferID");
		req.setAttribute("transferID", transferID);
		TInnerTransferInfo info = this.innerTransferHibernateService
				.getTInnerTransferInfo(transferID);
		req.setAttribute("main", info);
		List<TInnerTransferDetail> details = this.innerTransferJdbcService
				.getDetailList(transferID);
		req.setAttribute("details", details);
		List<TaskHisDto> list = this.sysWfUtilsService
				.queryTaskHisList(transferID);
		TaskHisDto temp = list.get(0);
		req.setAttribute("dept", temp.getExamine());
		temp = list.get(1);
		req.setAttribute("general", temp.getExamine());
		return new ModelAndView("sapss/account/transferPrint");
	}

	public void createDetail(HttpServletRequest request,
			HttpServletResponse response) throws IllegalAccessException,
			InvocationTargetException, IOException {
		UserContext userContext = UserContextHolder.getLocalUserContext()
				.getUserContext();
		TInnerTransferDetail detail = new TInnerTransferDetail();
		String transferID = request.getParameter("transferID");
		detail.setTransferID(transferID);
		detail.setDetailID(CodeGenerator.getUUID());
		detail.setCreatorID(userContext.getSysUser().getUserId());
		detail.setCreatorName(userContext.getSysUser().getRealName());
		detail.setIsAvailable("1");
		detail.setCreateTime(DateUtils.getCurrTime(2));
		this.innerTransferHibernateService.saveTInnerTransferDetail(detail);
		JSONObject jsonObject = JSONObject.fromObject(detail);
		String jsonText = jsonObject.toString();
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		response.getWriter().print(jsonText);
	}

	public void saveDetail(HttpServletRequest request,
			HttpServletResponse response, TInnerTransferDetail info)
			throws IllegalAccessException, InvocationTargetException,
			IOException {
		String detailID = request.getParameter("detailID");
		String colName = request.getParameter("colName");
		String colValue = this.extractFR(request, "colValue");
		response.setCharacterEncoding("GBK");
		response.setContentType("text ml; charset=GBK");
		this.innerTransferJdbcService.updateTInnerTransferDetail(detailID,
				colName, colValue);
	}

	public ModelAndView modifyDetail(HttpServletRequest req,
			HttpServletResponse resp) throws IllegalAccessException,
			InvocationTargetException {
		String detailID = req.getParameter("detailID");
		req.setAttribute("detailID", detailID);
		TInnerTransferDetail info = this.innerTransferHibernateService
				.getTInnerTransferDetail(detailID);
		req.setAttribute("detail", info);
		return new ModelAndView("sapss/account/transferDetailCreate");
	}

	public void deleteDetail(HttpServletRequest req,
			HttpServletResponse response) throws Exception {
		String idStr = (String) req.getParameter("idList");
		String transferID = (String) req.getParameter("transferID");
		boolean bb = this.innerTransferHibernateService
				.deleteTInnerTransferDetail(idStr, transferID);
		JSONObject jsonObject = new JSONObject();
		if (bb)
			jsonObject.put("returnMessage", "删除成功！");
		else
			jsonObject.put("returnMessage", "删除失败！");
		String jsonText = jsonObject.toString();
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		this.operateSuccessfullyWithString(response, jsonText);
	}

	public void findDetail(HttpServletRequest req, HttpServletResponse resp)
			throws Exception {
		ExtComponentServlet servlet = new ExtComponentServlet();
		String transferID = (String) req.getParameter("transferID");
		StringBuffer sb = new StringBuffer();
		sb
				.append("select tdinfo.* from t_inner_transfer_detail tdinfo where is_available='1' ");
		sb.append(" and transfer_ID = '" + transferID + "'");
		String grid_sql = sb.toString();
		String grid_columns = "DETAIL_ID,TRANSFER_ID,PAY_BANK,PAY_ACCOUNT,RECEIVE_BANK,RECEIVE_ACCOUNT,SUM";
		String grid_size = "10";
		req.setAttribute("grid_sql", grid_sql);
		req.setAttribute("grid_columns", grid_columns);
		req.setAttribute("grid_size", grid_size);
		servlet.processGrid(req, resp, true);
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
		return null;
	}
}
