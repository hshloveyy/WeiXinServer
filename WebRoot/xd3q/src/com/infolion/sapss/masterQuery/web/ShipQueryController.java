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

public class ShipQueryController extends BaseMultiActionController
{

	@Autowired
	private ShipHibernateService shipHibernateService;

	/**
	 * 进入查询选择出货通知单界面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ModelAndView findExportApply(HttpServletRequest request,
			HttpServletResponse response) throws IOException
	{
		String deptId = request.getParameter("deptId");
		String examineState = request.getParameter("examineState");
		String tradeType = request.getParameter("tradeType");
		request.setAttribute("deptId", deptId);
		request.setAttribute("examineState", examineState);
		request.setAttribute("tradeType", tradeType);
		return new ModelAndView("sapss/queryForm/findExportApply");
	}

	public void queryExportApply(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException
	{
		Map<String, String> map = extractFR(request);
		String sql = getQueryExportApplySql(map);
		String grid_columns = "EXPORT_APPLY_ID,CONTRACT_SALES_ID,TRADE_TYPE,PROJECT_NO,PROJECT_NAME,CONTRACT_GROUP_NO,SALES_NO,SAP_ORDER_NO,"
				+ "VBKD_IHREZ,PAYER,PAYER_NAME,KUAGV_KUNNR,KUAGV_KUNNR_NAME,"
				+ "KUWEV_KUNNR,KUWEV_KUNNR_NAME,BILL_TO_PARTY,BILL_TO_PARTY_NAME,NOTICE_NO,"
				+ "CREATOR,CREATOR_TIME,EXAMINE_STATE,EXAMINE_STATE_D_EXAMINE_STATE,IS_AVAILABLE,CONTRACT_NAME";
		String grid_size = "20";
		request.setAttribute("grid_sql", sql);
		request.setAttribute("grid_columns", grid_columns);
		request.setAttribute("grid_size", grid_size);

		ExtComponentServlet extComponentServlet = new ExtComponentServlet();
		extComponentServlet.processGrid(request, response, true);
	}

	/**
	 * 组装出仓通知单查询条件语句
	 * 
	 * @param map
	 * @return
	 */
	public String getQueryExportApplySql(Map<String, String> filter)
	{
		String sql = "select t.*,t.EXAMINE_STATE EXAMINE_STATE_D_EXAMINE_STATE,F.CONTRACT_NAME,"
				+ "f.VBKD_IHREZ,f.PAYER,f.PAYER_NAME,f.KUAGV_KUNNR,f.KUAGV_KUNNR_NAME,"
				+ "f.KUWEV_KUNNR,f.KUWEV_KUNNR_NAME,f.BILL_TO_PARTY,f.BILL_TO_PARTY_NAME"
				+ " from t_export_apply t left outer join t_contract_sales_info f on t.contract_sales_id = f.contract_sales_id "
				+ " where t.is_available = 1 ";
		if (filter != null && !filter.isEmpty())
		{
			String customer = filter.get("customer");
			if (StringUtils.isNotBlank(customer)) {
				sql += " and f.PAYER like '%" + customer + "%'";
			}
			String examineState = filter.get("examineState");
			if (StringUtils.isNotBlank(examineState))
				sql += " and t.examine_State = '" + examineState + "'";

			// SAP订单号
			String sapOrderNo = filter.get("sapOrderNo");
			if (StringUtils.isNotBlank(sapOrderNo))
				sql += " and t.sap_order_no like '%" + sapOrderNo + "%'";

			String tradeType = filter.get("tradeType");
			if (StringUtils.isNotBlank(tradeType))
			{
				sql += " and t.trade_type = '" + tradeType + "'";
			}
			String projectNo = filter.get("projectNo");
			if (StringUtils.isNotBlank(projectNo))
			{
				sql += " and t.project_no like '%" + projectNo + "%'";
			}
			String contractGroupNo = filter.get("contractGroupNo");
			if (StringUtils.isNotBlank(contractGroupNo))
			{
				sql += " and t.contract_group_no like '%" + contractGroupNo
						+ "%'";
			}
			String noticeNo = filter.get("noticeNo");
			if (StringUtils.isNotBlank(noticeNo))
			{
				sql += " and t.notice_no like '%" + noticeNo + "%'";
			}
			String contractNo = filter.get("contractNo");
			if (StringUtils.isNotBlank(contractNo))
			{
				sql += " and t.sales_no like '%" + contractNo + "%'";
			}
			String contractName = filter.get("contractName");
			if (StringUtils.isNotBlank(contractNo))
			{
				sql += " and f.contract_name like '%" + contractName + "%'";
			}
			String sDate = filter.get("sDate");
			if (StringUtils.isNotBlank(sDate))
			{
				sql += " and t.creator_time >= '" + sDate + "'";
			}
			String eDate = filter.get("eDate");
			if (StringUtils.isNotBlank(eDate))
			{
				sql += " and t.creator_time <= '" + eDate + "'";
			}
		}
		
		// 部门的过滤
		UserContext userContext = UserContextHolder.getLocalUserContext()
				.getUserContext();
		sql += " and t.dept_id in (" + userContext.getGrantedDepartmentsId()
				+ ",";
		sql += "'" + userContext.getSysDept().getDeptid() + "')";

		sql += " order by t.creator_time desc,t.notice_no desc";

		return sql;
	}

	/**
	 * 进入查询选择出仓单界面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ModelAndView findShipInfo(HttpServletRequest request,
			HttpServletResponse response) throws IOException
	{
		String deptId = request.getParameter("deptId");
		String examineState = request.getParameter("examineState");
		String tradeType = request.getParameter("tradeType");
		String contractSalesId = request.getParameter("contractSalesId");
		String contractNo = request.getParameter("contractNo");

		String customer = request.getParameter("customer");
		request.setAttribute("customer", customer);
		
		request.setAttribute("deptId", deptId);
		request.setAttribute("examineState", examineState);
		request.setAttribute("tradeType", tradeType);
		request.setAttribute("contractSalesId", contractSalesId);
		request.setAttribute("contractNo", contractNo);
		
		return new ModelAndView("sapss/queryForm/findShipInfo");
	}

	/**
	 * 查询出仓单信息
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	public void queryShipInfo(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException
	{
		Map<String, String> map = extractFR(request);
		String sql = getQueryShipSql(map);
		String grid_columns = "SHIP_ID,SHIP_NO,QUANTITY,PROJECT_NO,PROJECT_NAME,CONTRACT_GROUP_NO,CONTRACT_SALES_ID,SALES_NO,SAP_ORDER_NO,CONTRACT_NAME,"
				+ "VBKD_IHREZ,PAYER,PAYER_NAME,KUAGV_KUNNR,KUAGV_KUNNR_NAME,"
				+ "KUWEV_KUNNR,KUWEV_KUNNR_NAME,BILL_TO_PARTY,BILL_TO_PARTY_NAME,SAP_RETURN_NO,WAREHOUSE_ADD,WAREHOUSE,"
				+ "EXAMINE_STATE_D_EXAMINE_STATE,APPLY_TIME,APPROVED_TIME,IS_AVAILABLE,CREATOR_TIME,CREATOR";
		String grid_size = "60";
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
	public String getQueryShipSql(Map<String, String> filter)
	{
		String sql = "select t1.*,t1.EXAMINE_STATE EXAMINE_STATE_D_EXAMINE_STATE,tm.quantity,"
				+ "t2.CONTRACT_NAME,t2.VBKD_IHREZ,t2.PAYER,t2.PAYER_NAME,t2.KUAGV_KUNNR,t2.KUAGV_KUNNR_NAME,"
				+ "t2.KUWEV_KUNNR,t2.KUWEV_KUNNR_NAME,t2.BILL_TO_PARTY,t2.BILL_TO_PARTY_NAME "
				+ " from t_ship_info t1 "
				+ "left outer join (select  t2.ship_id, sum(t2.quantity) as quantity from  t_ship_material t2 group by t2.ship_id) tm on tm.ship_id=t1.ship_id "
				+ "left outer join t_contract_sales_info t2 on t1.contract_sales_id = t2.contract_sales_id "
				+ "where t1.is_available = 1 and t1.ship_id not in (select old_ship_id from t_ship_info where old_ship_id is not null and examine_state in ('2','3') and is_available=1 ) and t1.old_ship_id is null";
		if (filter != null && !filter.isEmpty())
		{
			String customer = filter.get("customer");
			if (StringUtils.isNotBlank(customer)) {
				sql += " and t2.PAYER like '%" + customer + "%'";
			}
			
			String contractSalesId = filter.get("contractSalesId");
			if (StringUtils.isNotBlank(contractSalesId))
				sql += " and t1.contract_sales_id = '" + contractSalesId + "'";
			
			String examineState = filter.get("examineState");
			if (StringUtils.isNotBlank(examineState))
				sql += " and t1.examine_State = '" + examineState + "'";

			String tradeType = filter.get("tradeType");
			if (StringUtils.isNotBlank(tradeType))
			{
				sql += " and t1.trade_type = '" + tradeType + "'";
			}
			String sapOrderNo = filter.get("sapOrderNo");
			if (StringUtils.isNotBlank(sapOrderNo))
			{
				sql += " and t1.sap_order_no Like '%" + sapOrderNo + "%'";
			}
			String projectNo = filter.get("projectNo");
			if (StringUtils.isNotBlank(projectNo))
			{
				sql += " and t1.project_no like '%" + projectNo + "%'";
			}
			String contractGroupNo = filter.get("contractGroupNo");
			if (StringUtils.isNotBlank(contractGroupNo))
			{
				sql += " and t1.contract_group_no like '%" + contractGroupNo
						+ "%'";
			}
			String shipNo = filter.get("shipNo");
			if (StringUtils.isNotBlank(shipNo))
			{
				sql += " and t1.ship_no like '%" + shipNo + "%'";
			}
			String contractNo = filter.get("contractNo");
			if (StringUtils.isNotBlank(contractNo))
			{
				sql += " and t1.sales_no like '%" + contractNo + "%'";
			}
			String contractName = filter.get("contractName");
			if (StringUtils.isNotBlank(contractName))
			{
				sql += " and t2.contract_name like '%" + contractName + "%'";
			}
			String unitName = filter.get("unitName");
			if(StringUtils.isNotBlank(unitName)){
				sql +=" and t2.BILL_TO_PARTY_NAME like '%"+unitName+"%'";
			}
			String sDate = filter.get("sDate");
			if (StringUtils.isNotBlank(sDate))
			{
				sql += " and t1.creator_time >= '" + sDate + "'";
			}
			String eDate = filter.get("eDate");
			if (StringUtils.isNotBlank(eDate))
			{
				sql += " and t1.creator_time <= '" + eDate + "'";
			}
			String sapReturnNo = filter.get("sapReturnNo");
			if (StringUtils.isNotBlank(sapReturnNo))
			{
				sql += " and t1.sap_return_no like '%" + sapReturnNo + "%'";
			}
		}

		// 部门的过滤
		UserContext userContext = UserContextHolder.getLocalUserContext()
				.getUserContext();
		sql += " and t1.dept_id in (" + userContext.getGrantedDepartmentsId()
				+ ",";
		sql += "'" + userContext.getSysDept().getDeptid() + "')";

		sql += " order by t1.ship_no desc";

		return sql;
	}

	/**
	 * 进入查询选择出仓单物料界面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ModelAndView findShipMaterial(HttpServletRequest request,
			HttpServletResponse response) throws IOException
	{
		String shipId = request.getParameter("shipId");
		String deptId = request.getParameter("deptId");
		String examineState = request.getParameter("examineState");
		String tradeType = request.getParameter("tradeType");
		String contractSalesId = request.getParameter("contractSalesId");
		request.setAttribute("shipId", shipId);
		request.setAttribute("deptId", deptId);
		request.setAttribute("examineState", examineState);
		request.setAttribute("tradeType", tradeType);
		request.setAttribute("contractSalesId", contractSalesId);
		return new ModelAndView("sapss/queryForm/findShipMaterial");
	}

	/**
	 * 查询出仓单物料信息
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	public void queryShipMaterial(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException
	{
		Map<String, String> map = extractFR(request);
		String sql = getQueryShipMaterialSql(map);
		String grid_columns = "SHIP_DETAIL_ID,SHIP_ID,SALES_ROWS_ID,EXPORT_MATE_ID,MATERIAL_CODE,MATERIAL_NAME,"
				+ "MATERIAL_UNIT,QUANTITY,BATCH_NO,PRICE,CURRENCY,TOTAL,CMD,IS_AVAILABLE,PURCHASE_ROWS_ID,BOM_ID,EKPO_PEINH";
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
	public String getQueryShipMaterialSql(Map<String, String> filter)
	{
		String sql = "select * from t_ship_material t1 where t1.is_available = 1";
		if (filter != null && !filter.isEmpty())
		{
			String shipId = filter.get("shipId");
			if (StringUtils.isNotBlank(shipId))
				sql += " and t1.ship_id = '" + shipId + "'";			
			String contractSalesId = filter.get("contractSalesId");
			if (StringUtils.isNotBlank(contractSalesId))
				sql += " and t1.contract_sales_id = '" + contractSalesId + "'";
			String materialCode = filter.get("materialCode");
			if (StringUtils.isNotBlank(materialCode))
				sql += " and t1.material_code like '%" + materialCode + "%'";
			String materialName = filter.get("materialName");
			if (StringUtils.isNotBlank(materialName))
				sql += " and t1.material_name like '%" + materialName + "%'";
		}
		sql += " order by t1.creator_time desc";

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

}
