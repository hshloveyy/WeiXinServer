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
import com.infolion.platform.util.DateUtils;
import com.infolion.sapss.capitalRequirement.domain.TZJXQCreditInfo;
import com.infolion.sapss.capitalRequirement.service.CreditHibernateService;

public class CreditController extends BaseMultiActionController {

	@Autowired
	private CreditHibernateService creditHibernateService;

	public void setCreditHibernateService(
			CreditHibernateService creditHibernateService) {
		this.creditHibernateService = creditHibernateService;
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
		if ("1".equals(type)) {
			return new ModelAndView(
					"sapss/capitalRequirement/acceptanceManager");
		} else {
			return new ModelAndView("sapss/capitalRequirement/creditManager");
		}
	}

	// 查询
	// 条件：companyCode,bank,receiver,billNO,beginDate,isPay
	public void find(HttpServletRequest req, HttpServletResponse resp)
			throws Exception {
		ExtComponentServlet servlet = new ExtComponentServlet();
		UserContext userContext = UserContextHolder.getLocalUserContext()
				.getUserContext();
		Map<String, String> map = extractFR(req);
		StringBuffer sb = new StringBuffer();
		sb
				.append("select tainfo.* from T_ZJXQ_CREDIT tainfo where is_available='1' ");
		if (map.get("companyCode") != null)
			sb.append(" and company_Code='" + map.get("companyCode") + "'");
		if (map.get("bank") != null)
			sb.append(" and bank like '%" + map.get("bank") + "%'");
		if (map.get("receiver") != null)
			sb.append(" and receiver like '%" + map.get("receiver") + "%'");
		if (map.get("billNO") != null)
			sb.append(" and billNO like '%" + map.get("billNO") + "%'");
		if (map.get("beginDate") != null)
			sb.append(" and begin_Date='" + map.get("beginDate") + "'");
		if (map.get("isPay") != null)
			sb.append(" and is_Pay = '" + map.get("isPay") + "'");
		if (map.get("type") != null)
			sb.append(" and type = '" + map.get("type") + "'");
		String mgtDeptId = UserContextHolder.getLocalUserContext()
				.getUserContext().getGrantedDepartmentsId();
		// 查看自己所在部门的、以及自己有数据权限的部门记录
		sb.append(" and (org_id='" + userContext.getSysDept().getDeptid()
				+ "' or org_id in (" + mgtDeptId + "))");
		sb.append(" order by create_time desc");

		String grid_sql = sb.toString();
		String grid_columns = "CREDIT_ID,COMPANY_CODE,BANK,AMOUNT,RECEIVER,CREDIT_ID,BEGIN_DATE,END_DATE,IS_PAY,CREATOR_NAME,CREATOR_ID";
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
		TZJXQCreditInfo info = new TZJXQCreditInfo();
		info.setType(type);
		info.setOrgName(userContext.getSysDept().getDeptname());
		info.setOrgID(userContext.getSysDept().getDeptid());
		info.setCreatorID(userContext.getSysUser().getUserId());
		info.setCreatorName(userContext.getSysUser().getRealName());
		request.setAttribute("main", info);
		request.setAttribute("from", "create");
		request.setAttribute("save_button_enable", true);
		if ("1".equals(type)) {
			return new ModelAndView("sapss/capitalRequirement/acceptanceCreate");
		} else {
			return new ModelAndView("sapss/capitalRequirement/creditCreate");
		}
	}

	public void save(HttpServletRequest request, HttpServletResponse response,
			TZJXQCreditInfo info) throws IllegalAccessException,
			InvocationTargetException, IOException {
		String creditID = request.getParameter("creditID");
		response.setCharacterEncoding("GBK");
		response.setContentType("text ml; charset=GBK");
		if (creditID == null || "".equals(creditID)) {
			info.setCreateTime(DateUtils.getCurrTime(2));
			info.setIsAvailable("1");
			this.creditHibernateService.saveCreditInfo(info);
		} else {
			this.creditHibernateService.updateCreditInfo(info);
		}
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
		String creditID = request.getParameter("creditID");
		String type = request.getParameter("type");
		request.setAttribute("creditID", creditID);
		String from = (String) request.getParameter("from");
		request.setAttribute("popfrom", from);
		TZJXQCreditInfo info = this.creditHibernateService
				.getCreditInfo(creditID);
		request.setAttribute("main", info);
		if (from.equals("view")) {
			request.setAttribute("save_button_enable", false);
		} else if (from.equals("modify")) {
			request.setAttribute("save_button_enable", true);
		}
		if ("1".equals(type)) {
			return new ModelAndView("sapss/capitalRequirement/acceptanceCreate");
		} else {
			return new ModelAndView("sapss/capitalRequirement/creditCreate");
		}
	}

	public void delete(HttpServletRequest req, HttpServletResponse resp)
			throws Exception {
		String creditID = (String) req.getParameter("creditID");
		boolean bb = this.creditHibernateService.deleteCreditInfo(creditID);
		resp.setCharacterEncoding("GBK");
		resp.setContentType("text/html; charset=GBK");
		if (bb)
			resp.getWriter().print("{success:true,msg:'删除成功'}");
		else {
			resp.getWriter().print("{success:false,msg:'记录不能删除'}");
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
