package com.infolion.sapss.capitalRequirement.web;

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
import com.infolion.sapss.capitalRequirement.domain.TZJXQLoanCNYInfo;
import com.infolion.sapss.capitalRequirement.domain.TZJXQLoanCNYRepayInfo;
import com.infolion.sapss.capitalRequirement.service.LoanCNYHibernateService;
import com.infolion.sapss.capitalRequirement.service.LoanCNYJdbcService;

public class LoanCNYController extends BaseMultiActionController {
	@Autowired
	private LoanCNYHibernateService loanCNYHibernateService;
	@Autowired
	private LoanCNYJdbcService loanCNYJdbcService;

	public void setLoanCNYHibernateService(
			LoanCNYHibernateService loanCNYHibernateService) {
		this.loanCNYHibernateService = loanCNYHibernateService;
	}

	public void setLoanCNYJdbcService(LoanCNYJdbcService loanCNYJdbcService) {
		this.loanCNYJdbcService = loanCNYJdbcService;
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
		String type = request.getParameter("type");
		request.setAttribute("type", type);
		return new ModelAndView("sapss/capitalRequirement/loanCNYManager");
	}

	// 查询
	// 条件：companyCode,billNO,isPay
	public void find(HttpServletRequest req, HttpServletResponse resp)
			throws Exception {
		ExtComponentServlet servlet = new ExtComponentServlet();
		UserContext userContext = UserContextHolder.getLocalUserContext()
				.getUserContext();
		Map<String, String> map = extractFR(req);
		StringBuffer sb = new StringBuffer();
		sb
				.append("select tainfo.*,(case when (to_char(sysdate,'yyyy-mm-dd')> end_date and is_pay = 0) then 1 else 0  end) as IS_OVERDUE from T_ZJXQ_LOAN_CNY tainfo where is_available='1' ");
		if (map.get("companyCode") != null)
			sb.append(" and company_Code='" + map.get("companyCode") + "'");
		if (map.get("billNO") != null)
			sb.append(" and billNO like '%" + map.get("billNO") + "%'");
		if (map.get("isPay") != null)
			sb.append(" and is_Pay = '" + map.get("isPay") + "'");
		if (map.get("type") != null)
			sb.append(" and type = '" + map.get("type") + "'");
		String mgtDeptId = UserContextHolder.getLocalUserContext()
				.getUserContext().getGrantedDepartmentsId();
		// 查看自己所在部门的、以及自己有数据权限的部门记录
		sb.append(" and (org_id='" + userContext.getSysDept().getDeptid()
				+ "' or org_id in (" + mgtDeptId + "))");
		sb.append(" order by IS_OVERDUE desc,create_time desc");

		String grid_sql = sb.toString();
		String grid_columns = "LOAN_ID,COMPANY_CODE,AMOUNT,INSTITUTION,BALANCE,RATE,BEGIN_DATE,END_DATE,IS_PAY,IS_OVERDUE,CREATOR_NAME,CREATOR_ID";
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
		String type = request.getParameter("type");
		TZJXQLoanCNYInfo info = new TZJXQLoanCNYInfo();
		String loanID = CodeGenerator.getUUID();
		info.setLoanID(loanID);
		info.setType(type);
		info.setOrgName(userContext.getSysDept().getDeptname());
		info.setOrgID(userContext.getSysDept().getDeptid());
		info.setCreatorID(userContext.getSysUser().getUserId());
		info.setCreatorName(userContext.getSysUser().getRealName());
		info.setCreateTime(DateUtils.getCurrTime(2));
		info.setIsAvailable("1");
		this.loanCNYHibernateService.saveLoanCNYInfo(info);
		request.setAttribute("main", info);
		request.setAttribute("from", "create");
		request.setAttribute("loanID", loanID);
		request.setAttribute("save_button_enable", true);
		return new ModelAndView("sapss/capitalRequirement/loanCNYCreate");
	}

	public void save(HttpServletRequest request, HttpServletResponse response,
			TZJXQLoanCNYInfo info) throws IllegalAccessException,
			InvocationTargetException, IOException {
		this.loanCNYHibernateService.updateLoanCNYInfo(info);
		this.loanCNYHibernateService.calBalance(info);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("returnMessage", "保存成功！");
		String jsonText = jsonObject.toString();
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		this.operateSuccessfullyWithString(response, jsonText);
	}

	public ModelAndView modify(HttpServletRequest request,
			HttpServletResponse response) throws IllegalAccessException,
			InvocationTargetException {
		String loanID = request.getParameter("loanID");
		request.setAttribute("loanID", loanID);
		String from = (String) request.getParameter("from");
		request.setAttribute("popfrom", from);
		TZJXQLoanCNYInfo info = this.loanCNYHibernateService
				.getLoanCNYInfo(loanID);
		request.setAttribute("main", info);
		if (from.equals("view")) {
			request.setAttribute("save_button_enable", false);
		} else if (from.equals("modify")) {
			request.setAttribute("save_button_enable", true);
		}
		return new ModelAndView("sapss/capitalRequirement/loanCNYCreate");
	}

	public void delete(HttpServletRequest req, HttpServletResponse resp)
			throws Exception {
		String loanID = (String) req.getParameter("loanID");
		boolean bb = this.loanCNYHibernateService.deleteLoanInfo(loanID);
		resp.setCharacterEncoding("GBK");
		resp.setContentType("text/html; charset=GBK");
		if (bb)
			resp.getWriter().print("{success:true,msg:'删除成功'}");
		else {
			resp.getWriter().print("{success:false,msg:'记录不能删除'}");
		}
	}

	public void findRepay(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ExtComponentServlet servlet = new ExtComponentServlet();
		String loanID = (String) request.getParameter("loanID");
		StringBuffer sb = new StringBuffer();
		sb
				.append("select tdinfo.* from T_ZJXQ_LOAN_CNY_REPAY tdinfo where is_available='1' ");
		sb.append(" and loan_ID = '" + loanID + "'");
		sb.append(" order by create_time");
		String grid_sql = sb.toString();
		String grid_columns = "REPAY_ID,LOAN_ID,AMOUNT,REPAY_DATE,REMARK";
		String grid_size = "10";
		request.setAttribute("grid_sql", grid_sql);
		request.setAttribute("grid_columns", grid_columns);
		request.setAttribute("grid_size", grid_size);
		servlet.processGrid(request, response, true);
	}

	public void createRepay(HttpServletRequest request,
			HttpServletResponse response) throws IllegalAccessException,
			InvocationTargetException, IOException {
		UserContext userContext = UserContextHolder.getLocalUserContext()
				.getUserContext();
		TZJXQLoanCNYRepayInfo repay = new TZJXQLoanCNYRepayInfo();
		String loanID = request.getParameter("loanID");
		repay.setLoanID(loanID);
		repay.setRepayID(CodeGenerator.getUUID());
		repay.setCreatorID(userContext.getSysUser().getUserId());
		repay.setCreatorName(userContext.getSysUser().getRealName());
		repay.setIsAvailable("1");
		repay.setCreateTime(DateUtils.getCurrTime(2));
		this.loanCNYHibernateService.saveLoanCNYRepay(repay);
		JSONObject jsonObject = JSONObject.fromObject(repay);
		String jsonText = jsonObject.toString();
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		response.getWriter().print(jsonText);
	}

	public void saveRepay(HttpServletRequest request,
			HttpServletResponse response, TZJXQLoanCNYRepayInfo info)
			throws IllegalAccessException, InvocationTargetException,
			IOException {
		String repayID = request.getParameter("repayID");
		String colName = request.getParameter("colName");
		String colValue = this.extractFR(request, "colValue");
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		this.loanCNYJdbcService.updateLoanCNYRepayInfo(repayID, colName,
				colValue);
	}

	public void deleteRepay(HttpServletRequest req, HttpServletResponse response)
			throws Exception {
		String idStr = (String) req.getParameter("idList");
		String loanID = (String) req.getParameter("loanID");
		this.loanCNYHibernateService.deleteLoanCNYRepayInfo(idStr, loanID);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("returnMessage", "删除成功！");
		String jsonText = jsonObject.toString();
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		this.operateSuccessfullyWithString(response, jsonText);
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
