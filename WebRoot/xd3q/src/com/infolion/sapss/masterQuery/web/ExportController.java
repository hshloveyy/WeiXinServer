/*
 * @(#)exportController.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：陈喜平
 *  时　间：2009-9-22
 *  描　述：创建
 */

package com.infolion.sapss.masterQuery.web;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.Date;
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
import com.infolion.platform.util.DateUtils;
import com.infolion.sapss.masterQuery.service.ReportService;

public class ExportController extends BaseMultiActionController{
	@Autowired
	private ReportService reportService;
	
	public void setReportService(ReportService reportService) {
		this.reportService = reportService;
	}
	/**
	 * 主信息页面
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView toMain(HttpServletRequest request,HttpServletResponse response){
		return new ModelAndView("sapss/reports/export/main");
	}
	/**
	 * 
	 * @param rs
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	private void rtn(List rtn,HttpServletRequest request,HttpServletResponse response) throws IOException{
		JSONArray ja = JSONArray.fromObject(rtn);
		JSONObject jo = new JSONObject();
		jo.put("totalProperty",rtn.size());
		jo.put("root", ja);
		String jsontext = jo.toString();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		response.getWriter().print(jsontext);

	}
	/**
	 * 
	 */
	private String queryCondiction(HttpServletRequest request,HttpServletResponse response){
		return ReportUtil.queryCondiction(request, response);
	}
	/**
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void rtnSale(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
		ExtComponentServlet servlet = new ExtComponentServlet();
		String size = request.getParameter("limit");
		String sql="select * from t_v_export_sale v where 1=1";
		sql +=queryCondiction(request,response);
		sql = sql + " order by dept_name,contract_no";
		String columns=ReportUtil.getProperty("export_sale");
		if(columns==null)
			return;
		request.setAttribute("grid_sql", sql.toString());
		request.setAttribute("grid_columns", columns);
		request.setAttribute("grid_size", size==null||"".equals(size)?"10":size);
		servlet.processGrid(request, response, true);

	}
	/**
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void rtnPurchase(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
		ExtComponentServlet servlet = new ExtComponentServlet();
		String size = request.getParameter("limit");
		String sql="select * from t_v_export_purchase v where 1=1";
		sql +=queryCondiction(request,response);
		sql = sql + " order by dept_name,contract_no";
		String columns =ReportUtil.getProperty("export_purchase");
		if(columns==null)
			return;
		request.setAttribute("grid_sql", sql.toString());
		request.setAttribute("grid_columns", columns);
		request.setAttribute("grid_size", size==null||"".equals(size)?"10":size);
		servlet.processGrid(request, response, true);
		
	}
	/**
	 * 采购收货
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView toReceiptGoods(HttpServletRequest request,HttpServletResponse response){
		this.sendAttr(request);
		return new ModelAndView("sapss/reports/export/receiptGoods");
	}
	/**
	 * 
	 * @param request
	 */
	private void sendAttr(HttpServletRequest request){
		request.setAttribute("PROJECT_NO", request.getParameter("PROJECT_NO"));
		request.setAttribute("CONTRACT_NO", request.getParameter("CONTRACT_NO"));
		request.setAttribute("CONTRACT_GROUP_NO", request.getParameter("CONTRACT_GROUP_NO"));
	}
	/**
	 * 
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws ServletException 
	 */
	public void rtnReceiptGoods(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
		List rtn = this.reportService.rtnDetailData("export_receiptGoods",parmMap(request));
		this.rtn(rtn, request, response);
	}
	/**
	 * 
	 * @param request
	 * @return
	 */
	private Map parmMap(HttpServletRequest request){
		Map map = new HashMap();
		if(StringUtils.isNotBlank(request.getParameter("project_no")))
			map.put("project_no", request.getParameter("project_no"));
		if(StringUtils.isNotBlank(request.getParameter("contract_no")))
			map.put("contract_no", request.getParameter("contract_no"));
		if(StringUtils.isNotBlank(request.getParameter("contract_group_no")))
			map.put("contract_group_no", request.getParameter("contract_group_no"));
		return map;
	}
	/**
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void receiptGoodsToExcel(HttpServletRequest request,HttpServletResponse response) throws IOException{
		this.detailToExcel("export_receiptGoods", request, "采购收货", response,null);
	}
	/**
	 * 
	 * @param detailName
	 * @param request
	 * @param fileName
	 * @param response
	 * @throws IOException
	 */
	private void detailToExcel(String detailName,HttpServletRequest request,String fileName,HttpServletResponse response,List<?> dataList) throws IOException{
		this.detailToExcel(false, detailName, request, fileName, response, dataList);
	}
	/**
	 * 
	 * @param detailName
	 * @param request
	 * @param fileName
	 * @param response
	 * @throws IOException
	 */
	private void detailToExcel(boolean isTable,String detailName,HttpServletRequest request,String fileName,HttpServletResponse response,List<?> dataList) throws IOException{
		String col =  ReportUtil.getProperty(detailName);
		String sql= ReportUtil.queryForExcel(isTable,detailName, request, col);
		String[] cols = col.split(",");
		String colName = new String(ReportUtil.getProperty(detailName+"_names").getBytes("ISO-8859-1"),"UTF-8");
		String[] colNames = colName.split(",");
		InputStream is = this.reportService.createExcelFile(cols, colNames, sql,dataList);
		ReportUtil util = new ReportUtil();
		util.download(is,fileName+".xls",response);
		is.close();
	}
	
	/**
	 * 收款
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView toReceiptMoney(HttpServletRequest request,HttpServletResponse response){
		this.sendAttr(request);
		return new ModelAndView("sapss/reports/export/receiptMoney");
	}

	/**
	 * 销售发货
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView toShipGoods(HttpServletRequest request,HttpServletResponse response){
		this.sendAttr(request);
		return new ModelAndView("sapss/reports/export/shipGoods");
	}
	/**
	 * 
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	public void rtnShipGoods(HttpServletRequest request,HttpServletResponse response) throws IOException{
		List rtn = this.reportService.rtnDetailData("export_ship_goods", this.parmMap(request));
		this.rtn(rtn, request, response);
	}
	/**
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void shipGoodsToExcel(HttpServletRequest request,HttpServletResponse response) throws IOException{
		this.detailToExcel("export_ship_goods", request, "销售出货", response,null);
	}
	/**
	 * 发票校验
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView toBillValidate(HttpServletRequest request,HttpServletResponse response){
		this.sendAttr(request);
		return new ModelAndView("sapss/reports/export/billValidate");
	}
	/**
	 * 发票校验
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	public void rtnBillValidate(HttpServletRequest request,HttpServletResponse response) throws IOException{
		List rtn = this.reportService.rtnDetailData("export_bill_validate", this.parmMap(request));
		this.rtn(rtn, request, response);
	}
	/**
	 * 发票校验
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void billValidateToExcel(HttpServletRequest request,HttpServletResponse response) throws IOException{
		this.detailToExcel("export_bill_validate", request, "发票校验", response,null);
	}
	/**
	 * 收汇明细
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView toReceiptMoneyDetail(HttpServletRequest request,HttpServletResponse response){
		this.sendAttr(request);
		return new ModelAndView("sapss/reports/export/receiptMoneyDetail");
	}
	/**
	 * 收汇明细
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	public void rtnReceiptMoneyDetail(HttpServletRequest request,HttpServletResponse response) throws IOException{
		List rtn = this.reportService.rtnDetailData("export_receipt_money_detail", this.parmMap(request));
		this.rtn(rtn, request, response);
	}
	/**
	 * 收汇明细
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void receiptMoneyDetailToExcel(HttpServletRequest request,HttpServletResponse response) throws IOException{
		this.detailToExcel("export_receipt_money_detail", request, "收汇明细", response,null);
	}
	
	/**
	 * L/C项下出单一览表
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView toLCView(HttpServletRequest request,HttpServletResponse response){
		this.sendAttr(request);
		return new ModelAndView("sapss/reports/export/LCView");
	}
	/**
	 * L/C项下出单一览表
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	public void rtnLCView(HttpServletRequest request,HttpServletResponse response) throws IOException{
		List rtn = this.reportService.rtnDetailData(false,"export_lc_view", this.parmMap(request));
		this.rtn(rtn, request, response);
	}
	/**
	 * L/C项下出单一览表
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void LCViewToExcel(HttpServletRequest request,HttpServletResponse response) throws IOException{
		this.detailToExcel("export_lc_view", request, "LC项下出单一览表", response,null);
	}
	/**
	 * 托收项下出单一览表
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView toDPDAView(HttpServletRequest request,HttpServletResponse response){
		this.sendAttr(request);
		return new ModelAndView("sapss/reports/export/DPDAView");
	}
	/**
	 * 托收项下出单一览表
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	public void rtnDPDAView(HttpServletRequest request,HttpServletResponse response) throws IOException{
		List rtn = this.reportService.rtnDetailData("export_dpda_view", this.parmMap(request));
		this.rtn(rtn, request, response);
	}
	/**
	 * 托收项下出单一览表
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void DPDAViewToExcel(HttpServletRequest request,HttpServletResponse response) throws IOException{
		this.detailToExcel("export_dpda_view", request, "托收项下出单一览表", response,null);
	}
	
	/**
	 * T/T项下出单一览表
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView toTTView(HttpServletRequest request,HttpServletResponse response){
		this.sendAttr(request);
		return new ModelAndView("sapss/reports/export/TTView");
	}
	/**
	 *T/T项下出单一览表
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	public void rtnTTView(HttpServletRequest request,HttpServletResponse response) throws IOException{
		List rtn = this.reportService.rtnDetailData("export_tt_view", this.parmMap(request));
		this.rtn(rtn, request, response);
	}
	/**
	 *T/T项下出单一览表
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void TTViewToExcel(HttpServletRequest request,HttpServletResponse response) throws IOException{
		this.detailToExcel("export_tt_view", request, "TT项下出单一览表", response,null);
	}
	/**
	 * 付款明细
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView toPayDetail(HttpServletRequest request,HttpServletResponse response){
		this.sendAttr(request);
		return new ModelAndView("sapss/reports/export/payDetail");
	}
	/**
	 * 付款明细
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws ServletException 
	 */
	public void rtnPayDetail(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
		Map map = this.parmMap(request);
		map.put("contract_purchase_id", request.getParameter("contract_purchase_id"));
//		List rtn = this.reportService.rtnExportPayMoney(map);
		List rtn = this.reportService.rtnDetailData("export_payDetail", this.parmMap(request));
		this.rtn(rtn, request, response);
	}
	/**
	 * 付款明细
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void payDetailToExcel(HttpServletRequest request,HttpServletResponse response) throws IOException{
//		List<?> rtn = this.reportService.rtnDetailData("export_payMoney", this.parmMap(request));
//		List<?> rtn = this.reportService.rtnExportPayMoney(this.parmMap(request));
		this.detailToExcel("export_payDetail", request, "付款明细", response,null);
	}
	/**
	 * 信用证收证一览表
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView toCreditReceiptView(HttpServletRequest request,HttpServletResponse response){
		this.sendAttr(request);
		return new ModelAndView("sapss/reports/export/creditReceiptView");
	}
	/**
	 * 信用证收证一览表
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws ServletException 
	 */
	public void rtnCreditReceiptView(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
		List rtn = this.reportService.rtnDetailData("export_credit_receipt_view", this.parmMap(request));
		this.rtn(rtn, request, response);
	}
	/**
	 * 信用证收证一览表
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	public void creditReceiptViewToExcel(HttpServletRequest request,HttpServletResponse response) throws IOException{
		this.detailToExcel("export_credit_receipt_view", request, "信用证收证一览表", response, null);
	}
	/**
	 * 核销退税
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView toDrawback(HttpServletRequest request,HttpServletResponse response){
		this.sendAttr(request);
		return new ModelAndView("sapss/reports/export/drawback");
	}
	/**
	 * 
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws ServletException 
	 */
	public void rtnDrawback(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
		List rtn = this.reportService.rtnDetailData("export_drawback", this.parmMap(request));
		this.rtn(rtn, request, response);
	}
	/**
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void drawbackToExcel(HttpServletRequest request,HttpServletResponse response) throws IOException{
		this.detailToExcel("export_drawback", request, "核销退税", response, null);
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws ServletException 
	 */
	public void exportToExcel(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException{
		
		String saleSql="select * from t_v_export_sale v where 1=1";
		String sqlTemp = queryCondiction(request,response);
		saleSql +=sqlTemp;
		saleSql =  saleSql + " order by dept_name,contract_no";
		String[] columns=new String[]{"dept_name","trade_type","project_no","customer_link_man","contract_group_no","contract_apply_time","contract_no","old_contract_no","sap_order_no",
				"kuagv_kunnr_name","vbap_arktx","vbap_zmeng","vbap_vrkme","vbak_waerk",
				"konv_kbetr","vbap_kpein","row_total","order_total","vbkd_inco1","shipment_date","shipment_port","destination_port","vbkd_zterm","vbkd_zlsch",
				"credit_no","same_contract_materiel_ct","pasted_total","leftct",
				"receipt_total","real_receipt_total","receipt_balance","sale_contract_staus","mat_group_name"};
		String[] colsName=new String[]{"部门","业务类型","立项号","卖方/委托方","合同组号","销售合同日期","销售合同号","外部纸质合同号","销售订单号",
				"客户名称","物料名称","合同数量","单位","合同币别",
				"单价","条件定价单位","单行物料总金额","合同总金额","价格术语","装运期限","装运港","目的港","收汇条件","收汇方式",
				"信用证号","累计出仓数量","累计出仓金额","合同未执行数量",
				"累计应收金额","累计已收金额","应收余额","销售合同状态","物料组"};
		
		String purchaseSql="select * from t_v_export_purchase v where 1=1";
		purchaseSql +=sqlTemp;
		purchaseSql = purchaseSql + " order by dept_name,contract_no";
		String[] columnp= new String[]{"dept_name","trade_type","project_no","provider_link_man","contract_group_no","ekko_bedat","contract_no","old_contract_no","sap_order_no",
				"ekko_lifnr_name","ekpo_txz01","ekpo_menge","ekpo_meins","ekpo_bprme","ekpo_netpr","ekpo_peinh",
				"purchaserowtotal","total","eket_eindt","factory_local","ekko_zterm","PAY_WAY",//"",
				"same_contract_materiel_ct","pasted_total","leftct",
				"pay_total","real_pay_total","pay_balance","purchase_contract_staus","mat_group_name"};
			String[] colsNamep=new String[]{"部门","业务类型","立项号","卖方/委托方","合同组号","采购合同日期","采购合同号","外部纸质合同号","采购订单号",
					"供应商名称","物料名称","合同数量","单位","合同币别","单价","条件定价单位",
					"单行物料总金额","合同总金额","合同交货日期","合同交货地点","付款条件","付款方式",
					"累计进仓数量","累计进仓金额","合同未执行数量",
					"累计应付金额","累计已付金额","应付余额","采购合同状态","物料组"};
		String fileName="出口跟踪表.xls";
		InputStream is = this.reportService.createExcelFile(columns, colsName, saleSql,columnp,colsNamep,purchaseSql);
		ReportUtil util = new ReportUtil();
		util.download(is,fileName,response);
		is.close();
	}
}
