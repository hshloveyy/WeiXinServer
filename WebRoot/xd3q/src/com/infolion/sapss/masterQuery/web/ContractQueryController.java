/*
 * @(#)MasterQueryController.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：陈喜平
 *  时　间：2009-2-17
 *  描　述：创建
 */

package com.infolion.sapss.masterQuery.web;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

import com.infolion.platform.component.ExtComponentServlet;
import com.infolion.platform.console.sys.context.UserContext;
import com.infolion.platform.console.sys.context.UserContextHolder;
import com.infolion.platform.core.web.BaseMultiActionController;

import com.infolion.sapss.ship.service.ShipHibernateService;

public class ContractQueryController extends BaseMultiActionController {

	@Autowired
	private ShipHibernateService shipHibernateService;

	/**
	 * 进入查询选择销售合同界面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ModelAndView findSalesInfo(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String depId = request.getParameter("depId");
		String orderState = request.getParameter("orderState");
		String tradeType = request.getParameter("tradeType");

		String customer = request.getParameter("customer");
		request.setAttribute("customer", customer);

		request.setAttribute("depId", depId);
		request.setAttribute("orderState", orderState);
		request.setAttribute("tradeType", tradeType);

		return new ModelAndView("sapss/queryForm/findSalesInfo");
	}

	/**
	 * 查询销售合同信息
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	public void querySalesInfo(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		Map<String, String> map = extractFR(request);

		String sql = getQuerySalesSql(map);
		String grid_columns = "PROJECT_NO,PROJECT_NAME,CONTRACT_GROUP_NO,CONTRACT_GROUP_NAME,"
				+ "VBKD_IHREZ,PAYER,PAYER_NAME,KUAGV_KUNNR,KUAGV_KUNNR_NAME,"
				+ "KUWEV_KUNNR,KUWEV_KUNNR_NAME,BILL_TO_PARTY,BILL_TO_PARTY_NAME,"
				+ "CONTRACT_SALES_ID,CONTRACT_NO,CONTRACT_NAME,SAP_ORDER_NO,VBKD_INCO1,VBKD_INCO2";
		String grid_size = "20";

		request.setAttribute("grid_sql", sql);
		request.setAttribute("grid_columns", grid_columns);
		request.setAttribute("grid_size", grid_size);

		ExtComponentServlet extComponentServlet = new ExtComponentServlet();
		extComponentServlet.processGrid(request, response, true);
	}

	/**
	 * 组装销售合同查询条件语句
	 * 
	 * @param filter
	 * @return
	 */
	public String getQuerySalesSql(Map<String, String> filter) {
		String sql = "select c.project_no, c.project_name,b.contract_group_no,b.contract_group_name,a.contract_sales_id,"
				+ "a.contract_no,a.contract_name,a.sap_order_no,"
				+ "a.VBKD_IHREZ,a.PAYER,a.PAYER_NAME,a.KUAGV_KUNNR,a.KUAGV_KUNNR_NAME,"
				+ "a.KUWEV_KUNNR,a.KUWEV_KUNNR_NAME,a.BILL_TO_PARTY,a.BILL_TO_PARTY_NAME,"
				+ "a.vbkd_inco1,a.vbkd_inco2"
				+ "  from t_contract_sales_info a, t_contract_group b, t_project_info c"
				+ " where a.contract_group_id = b.contract_group_id and a.project_id = c.project_id";
		String customer = filter.get("customer");
		if (StringUtils.isNotBlank(customer)) {
			sql += " and a.PAYER like '%" + customer + "%'";
		}
		String contractGroupNo = filter.get("contractGroupNo");
		if (StringUtils.isNotBlank(contractGroupNo)) {
			sql = sql + " and b.contract_group_no like '%" + contractGroupNo
					+ "%'";
		}
		String contractGroupName = filter.get("contractGroupName");
		if (StringUtils.isNotBlank(contractGroupName)) {
			sql = sql + " and b.contract_group_name like '%"
					+ contractGroupName + "%'";
		}
		String contractNo = filter.get("contractNo");
		if (StringUtils.isNotBlank(contractNo)) {
			sql = sql + " and a.contract_no like '%" + contractNo + "%'";
		}
		String contractName = filter.get("contractName");
		if (StringUtils.isNotBlank(contractName)) {
			sql = sql + " and a.contract_name like '%" + contractName + "%'";
		}
		String orderState = filter.get("orderState");
		if (StringUtils.isNotBlank(orderState)) {
			sql = sql + " and a.order_state in (" + orderState + ")";
		} else {
			sql = sql + " and a.order_state in ('3','5','7','8','9')";
		}
		String tradeType = filter.get("tradeType");
		if (StringUtils.isNotBlank(tradeType)) {
			sql = sql + " and a.trade_type = " + tradeType;
		}
		
		// 部门的过滤
		UserContext userContext = UserContextHolder.getLocalUserContext()
				.getUserContext();
		sql += " and a.dept_id in (" + userContext.getGrantedDepartmentsId()
				+ ",";
		sql += "'" + userContext.getSysDept().getDeptid() + "')";		
		
		return sql;
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
