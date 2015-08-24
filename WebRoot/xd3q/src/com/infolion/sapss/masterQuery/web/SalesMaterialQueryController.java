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

public class SalesMaterialQueryController extends BaseMultiActionController
{

	@Autowired
	private ShipHibernateService shipHibernateService;

	/**
	 * 进入查询选择销售物料单界面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ModelAndView findSalesMaterial(HttpServletRequest request,
			HttpServletResponse response) throws IOException
	{
		String deptId = request.getParameter("deptId");
		String examineState = request.getParameter("examineState");
		String tradeType = request.getParameter("tradeType");
		String contractSalesId = request.getParameter("contractSalesId");
		String contractSalesNo = request.getParameter("contractSalesNo");
		request.setAttribute("deptId", deptId);
		request.setAttribute("examineState", examineState);
		request.setAttribute("tradeType", tradeType);
		request.setAttribute("contractSalesId", contractSalesId);
		request.setAttribute("contractSalesNo", contractSalesNo);
		return new ModelAndView("sapss/queryForm/findSalesMaterial");
	}

	/**
	 * 查询物料信息
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	public void querySalesMaterial(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException
	{
		Map<String, String> map = extractFR(request);
		String sql = getQuerySalesMaterialSql(map);
		String grid_columns = "SALES_ROWS_ID,CONTRACT_SALES_ID,SAP_ROW_NO,VBAP_PSTYV,VBAP_MATNR,VBAP_ARKTX,VBAP_VRKME,"
				+ "RV45A_ETDAT,VBAP_WERKS,VBAP_UEBTO,VBAP_UNTTO,EKPO_MENGE,VBAP_KWMENG,KONV_KBETR,VBKD_ZTERM,VBKD_ZTERM_NAME,"
				+ "VBAP_ZMENG,VBAP_KPEIN,VBAP_KMEIN,PAYER,PAYER_NAME,BILL_TO_PARTY,BILL_TO_PARTY_NAME,ROW_TOTAL,ROW_NET,ROW_TAXES,"
				+ "ROW_TAXES_RALE,CREATE_TIME,CREATOR,VBAP_ZMENG";
		String grid_size = "20";
		request.setAttribute("grid_sql", sql);
		request.setAttribute("grid_columns", grid_columns);
		request.setAttribute("grid_size", grid_size);

		ExtComponentServlet extComponentServlet = new ExtComponentServlet();
		extComponentServlet.processGrid(request, response, true);
	}

	/**
	 * 组装出仓单查询条件语句
	 * 
	 * @param map
	 * @return
	 */
	public String getQuerySalesMaterialSql(Map<String, String> filter)
	{
		String sql = "select t1.* from t_contract_sales_materiel t1 where 1 = 1";
		if (filter != null && !filter.isEmpty())
		{
			String contractSalesId = filter.get("contractSalesId");
			if (StringUtils.isNotBlank(contractSalesId))
				sql += " and t1.contract_sales_id = '" + contractSalesId + "'";
			String materialCode = filter.get("materialCode");
			if (StringUtils.isNotBlank(materialCode))
				sql += " and t1.VBAP_MATNR = '" + materialCode + "'";

			String materialName = filter.get("materialName");
			if (StringUtils.isNotBlank(materialName))
			{
				sql += " and t1.VBAP_ARKTX = '" + materialName + "'";
			}
		}

		return sql;
	}

	/**
	 * 萃取url
	 * 
	 * @param req
	 * @return 参数名,参数值的map
	 */
	private Map<String, String> extractFR(HttpServletRequest req)
	{
		try
		{
			String wait = java.net.URLDecoder.decode(req.getQueryString(),
					"UTF-8");
			String ar1[] = wait.split("&");
			Map<String, String> map = new HashMap<String, String>();
			String str = "";
			String ar2[];
			for (int i = 0; i < ar1.length; i++)
			{
				str = ar1[i];
				ar2 = str.split("=");
				if (ar2.length == 1)
					continue;
				map.put(ar2[0], ar2[1]);
			}
			return map;
		}
		catch (UnsupportedEncodingException e)
		{}
		return Collections.EMPTY_MAP;
	}
	
	/**
	 * 进入查询选择销售物料单界面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ModelAndView findSalesMaterialByContractNo(HttpServletRequest request,
			HttpServletResponse response) throws IOException
	{
		String deptId = request.getParameter("deptId");
		String examineState = request.getParameter("examineState");
		String tradeType = request.getParameter("tradeType");
		String contractSalesId = request.getParameter("contractSalesId");
		String contractSalesNo = request.getParameter("contractSalesNo");
		request.setAttribute("deptId", deptId);
		request.setAttribute("examineState", examineState);
		request.setAttribute("tradeType", tradeType);
		request.setAttribute("contractSalesId", contractSalesId);
		request.setAttribute("contractSalesNo", contractSalesNo);
		return new ModelAndView("sapss/queryForm/findSalesMaterialByContractNo");
	}
	

	/**
	 * 查询物料信息
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	public void querySalesMateriaByContractNo(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException
	{
		Map<String, String> map = extractFR(request);
		String sql = getQuerySalesMaterialSqlByContractNo(map);
		String grid_columns = "CONTRACT_NO,SALES_ROWS_ID,CONTRACT_SALES_ID,SAP_ROW_NO,VBAP_PSTYV,VBAP_MATNR,VBAP_ARKTX,VBAP_VRKME,"
				+ "RV45A_ETDAT,VBAP_WERKS,VBAP_UEBTO,VBAP_UNTTO,EKPO_MENGE,VBAP_KWMENG,KONV_KBETR,VBKD_ZTERM,VBKD_ZTERM_NAME,"
				+ "VBAP_KPEIN,VBAP_KMEIN,PAYER,PAYER_NAME,BILL_TO_PARTY,BILL_TO_PARTY_NAME,ROW_TOTAL,ROW_NET,ROW_TAXES,"
				+ "ROW_TAXES_RALE,CREATE_TIME,CREATOR,VBAP_ZMENG";
		String grid_size = "20";
		request.setAttribute("grid_sql", sql);
		request.setAttribute("grid_columns", grid_columns);
		request.setAttribute("grid_size", grid_size);

		ExtComponentServlet extComponentServlet = new ExtComponentServlet();
		extComponentServlet.processGrid(request, response, true);
	}

	/**
	 * 组装出仓单查询条件语句
	 * 
	 * @param map
	 * @return
	 */
	public String getQuerySalesMaterialSqlByContractNo(Map<String, String> filter)
	{
		String sql = "select t.CONTRACT_NO,t1.* from T_CONTRACT_SALES_INFO t Inner Join t_contract_sales_materiel t1 " +
				"On t.contract_Sales_Id=t1.contract_Sales_Id where 1 = 1";
		if (filter != null && !filter.isEmpty())
		{
			String contractSalesNo = filter.get("contractSalesNo");
			if (StringUtils.isNotBlank(contractSalesNo))
				sql += " and t.CONTRACT_NO In ('" + contractSalesNo.replace(",", "','") + "')";

			String contractSalesId = filter.get("contractSalesId");
			if (StringUtils.isNotBlank(contractSalesId))
				sql += " and t1.contract_sales_id = '" + contractSalesId + "'";

			String materialCode = filter.get("materialCode");
			if (StringUtils.isNotBlank(materialCode))
				sql += " and t1.VBAP_MATNR = '" + materialCode + "'";

			String materialName = filter.get("materialName");
			if (StringUtils.isNotBlank(materialName))
			{
				sql += " and t1.VBAP_ARKTX = '" + materialName + "'";
			}
		}
		
		// 部门的过滤
		UserContext userContext = UserContextHolder.getLocalUserContext()
				.getUserContext();
		sql += " and t.dept_id in (" + userContext.getGrantedDepartmentsId()
				+ ",";
		sql += "'" + userContext.getSysDept().getDeptid() + "')";

		return sql;
	}
}
