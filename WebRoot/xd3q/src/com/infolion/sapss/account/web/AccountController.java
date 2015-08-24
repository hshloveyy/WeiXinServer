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
import com.infolion.platform.util.DateUtils;
import com.infolion.sapss.account.domain.TAccountInfo;
import com.infolion.sapss.account.service.AccountHibernateService;
import com.infolion.sapss.account.service.AccountJdbcService;

public class AccountController extends BaseMultiActionController {
	@Autowired
	private AccountHibernateService accountHibernateService;

	public void setAccountHibernateService(
			AccountHibernateService accountHibernateService) {
		this.accountHibernateService = accountHibernateService;
	}

	@Autowired
	private AccountJdbcService accountJdbcService;

	public void setAccountJdbcService(AccountJdbcService accountJdbcService) {
		this.accountJdbcService = accountJdbcService;
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
		return new ModelAndView("sapss/account/accountManager");
	}

	// 查询
	// 条件：companyCode,headOfficeName,bankName,accountCode,currency,approvedState
	public void find(HttpServletRequest req, HttpServletResponse resp)
			throws Exception {
		ExtComponentServlet servlet = new ExtComponentServlet();
		UserContext userContext = UserContextHolder.getLocalUserContext()
				.getUserContext();
		Map<String, String> map = extractFR(req);
		StringBuffer sb = new StringBuffer();
		sb
				.append("select tainfo.*,to_date(apply_time,'yyyy-mm-dd hh24:mi:ss') applyTime,approve_state ACCOUNT_STATE_D_ACCOUNT_STATE from t_account_info tainfo where is_available='1' ");
		if (map.get("companyCode") != null)
			sb.append(" and company_Code='" + map.get("companyCode") + "'");
		if (map.get("headOfficeName") != null)
			sb.append(" and headOffice_Name like '%"
					+ map.get("headOfficeName") + "%'");
		if (map.get("bankName") != null)
			sb.append(" and bank_Name like '%" + map.get("bankName") + "%'");
		if (map.get("accountCode") != null)
			sb.append(" and account_Code like '%" + map.get("accountCode")
					+ "%'");
		if (map.get("currency") != null)
			sb.append(" and currency='" + map.get("currency") + "'");
		if (map.get("state") != null)
			sb.append(" and approve_state = '" + map.get("state") + "'");
		String mgtDeptId = UserContextHolder.getLocalUserContext()
				.getUserContext().getGrantedDepartmentsId();
		// 查看自己所在部门的、以及自己有数据权限的部门记录
		sb.append(" and (org_id='" + userContext.getSysDept().getDeptid()
				+ "' or org_id in (" + mgtDeptId + "))");
		sb.append(" order by apply_time desc");

		String grid_sql = sb.toString();
		String grid_columns = "ACCOUNT_ID,COMPANY_CODE,BANK_NAME,ACCOUNT_CODE,CURRENCY,SUBJECT_NO,APPLY_TIME,ACCOUNT_STATE_D_ACCOUNT_STATE,APPROVED_TIME,CREATOR_NAME,CREATOR_ID";
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
		TAccountInfo info = new TAccountInfo();
		info.setOrgName(userContext.getSysDept().getDeptname());
		info.setOrgID(userContext.getSysDept().getDeptid());
		info.setCreatorID(userContext.getSysUser().getUserId());
		info.setCreatorName(userContext.getSysUser().getRealName());
		request.setAttribute("main", info);
		request.setAttribute("revisable", "true");
		request.setAttribute("save_button_enable", false);
		return new ModelAndView("sapss/account/accountCreate");
	}

	public void save(HttpServletRequest request, HttpServletResponse response,
			TAccountInfo info) throws IllegalAccessException,
			InvocationTargetException, IOException {
		String accountID = request.getParameter("pid");
		response.setCharacterEncoding("GBK");
		response.setContentType("text ml; charset=GBK");
		if (accountID == null || "".equals(accountID)) {
			info.setCreateTime(DateUtils.getCurrTime(2));
			info.setIsAvailable("1");
			info.setApproveState("1");
			accountID = this.accountHibernateService.saveAccountInfo(info);
			response.getWriter().print(
					"{success:true,accountID:'" + accountID + "'}");

		} else {
			int i = this.accountJdbcService.updateAccountInfo(info);
			if (i > 0) {
				response.getWriter().print(
						"{success:true,accountID:'" + accountID + "'}");
			} else {
				response.getWriter().print(
						"{success:false,accountID:'" + accountID + "'}");
			}
		}
	}

	public ModelAndView modify(HttpServletRequest req, HttpServletResponse resp)
			throws IllegalAccessException, InvocationTargetException {
		String accountID = req.getParameter("accountID");
		req.setAttribute("accountID", accountID);
		String from = (String) req.getParameter("from");
		req.setAttribute("popfrom", from);
		TAccountInfo info = this.accountHibernateService
				.getTAccountInfo(accountID);
		req.setAttribute("main", info);
		return new ModelAndView("sapss/account/accountCreate");
	}

	public void delete(HttpServletRequest req, HttpServletResponse resp)
			throws Exception {
		String accountID = (String) req.getParameter("accountID");
		boolean bb = this.accountHibernateService.deleteAccountInfo(accountID);
		resp.setCharacterEncoding("GBK");
		resp.setContentType("text/html; charset=GBK");
		if (bb)
			resp.getWriter().print("{success:true,msg:'删除成功'}");
		else {
			resp.getWriter().print("{success:false,msg:'记录不能删除'}");
		}
	}

	public void submitAccountInfo(HttpServletRequest request,
			HttpServletResponse response) throws IllegalAccessException,
			InvocationTargetException, IOException, Exception {
		String msg = "";
		try {
			String accountID = request.getParameter("accountID");
			String taskId = request.getParameter("workflowTaskId");
			TAccountInfo info = accountHibernateService
					.getTAccountInfo(accountID);
			info.setWorkflowTaskId(taskId);
			String workflowExamine = request.getParameter("workflowExamine");
			info.setWorkflowExamine(workflowExamine);
			String workflowLeaveTransitionName = request
					.getParameter("workflowLeaveTransitionName");
			info.setWorkflowLeaveTransitionName(workflowLeaveTransitionName);
			info.setAccountID(accountID);
			this.accountHibernateService.submitWorkflow(taskId, info);
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
	public ModelAndView accountExamine(HttpServletRequest req,
			HttpServletResponse resp) throws IllegalAccessException,
			InvocationTargetException {
		String accountID = req.getParameter("businessRecordId");
		String taskId = req.getParameter("taskId");
		req.setAttribute("businessRecordId", accountID);
		req.setAttribute("taskId", taskId);
		req.setAttribute("iframeSrc",
				"accountController.spr?action=modify&from=iframe&accountID="
						+ accountID);
		req.setAttribute("submitUrl", "accountController.spr");
		req.setAttribute("action", "submitAccountInfo");
		req.setAttribute("iframeForms",
				"Form.serialize(document.frames['content']['mainForm'])");
		return new ModelAndView("sapss/account/accountWorkflowManager");
	}

	public void saveFundInfo(HttpServletRequest request,
			HttpServletResponse response, AccountFundVO info)
			throws IllegalAccessException, InvocationTargetException,
			IOException {
		String accountID = request.getParameter("accountID");
		response.setCharacterEncoding("GBK");
		response.setContentType("text ml; charset=GBK");
		if (accountID == null || "".equals(accountID)) {
			response.getWriter().print(
					"{success:false,accountID:'" + accountID + "'}");

		} else {
			this.accountJdbcService.saveFundInfo(info);
			response.getWriter().print(
					"{success:true,accountID:'" + accountID + "'}");
		}
	}

	public void saveGeneralInfo(HttpServletRequest request,
			HttpServletResponse response, AccountGeneralVO info)
			throws IllegalAccessException, InvocationTargetException,
			IOException {
		String accountID = request.getParameter("accountID");
		response.setCharacterEncoding("GBK");
		response.setContentType("text ml; charset=GBK");
		if (accountID == null || "".equals(accountID)) {
			response.getWriter().print(
					"{success:false,accountID:'" + accountID + "'}");

		} else {
			this.accountJdbcService.saveGeneralInfo(info);
			response.getWriter().print(
					"{success:true,accountID:'" + accountID + "'}");
		}
	}

	public void saveMaintainInfo(HttpServletRequest request,
			HttpServletResponse response, AccountMaintainVO info)
			throws IllegalAccessException, InvocationTargetException,
			IOException {
		String accountID = request.getParameter("accountID");
		response.setCharacterEncoding("GBK");
		response.setContentType("text ml; charset=GBK");
		if (accountID == null || "".equals(accountID)) {
			response.getWriter().print(
					"{success:false,accountID:'" + accountID + "'}");

		} else {
			this.accountJdbcService.saveMaintainInfo(info);
			response.getWriter().print(
					"{success:true,accountID:'" + accountID + "'}");
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
