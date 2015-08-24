/*
 * @(#)importController.java
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

public class ImportController extends BaseMultiActionController{
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
		return new ModelAndView("sapss/reports/import/main");
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
		String sql="select v.* from t_v_import_sale v where 1=1";
		sql+=queryCondiction(request, response);
		sql = sql + " order by dept_name,contract_no";
		String columns="dept_name,trade_type,project_no,customer_link_man,contract_group_no,";
		columns +="contract_date,contract_no,old_contract_no,sap_order_no,kuagv_kunnr_name,";
		columns +="vbap_arktx,Vbap_Zmeng,VBAP_VRKME,VBAK_WAERK,konv_kbetr,VBAP_KPEIN,";
		columns +="saleRowTotal,order_total,shipment_port,shipment_date,vbkd_zterm,vbkd_zlsch,";
		columns +="SAME_CONTRACT_MATERIEL_CT,SUM_TOTAL,leftCt,";
		columns +="receipt_total,real_receipt_total,receipt_balance,sale_contract_staus";
		request.setAttribute("grid_sql", sql.toString());
		request.setAttribute("grid_columns", columns.toString());
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
		String projectNo= request.getParameter("projectNo");
		String size = request.getParameter("limit");
		String sql="select v.* from t_v_import_purchase v where 1=1 ";
		sql+=queryCondiction(request, response);
		sql = sql + " order by dept_name,contract_no";
		String columns="dept_name,trade_type,project_no,CUSTOMER_LINK_MAN,contract_group_no,contract_no,old_contract_no,";
		columns +="sap_order_no,EKKO_BEDAT,EKKO_LIFNR_NAME,ekpo_txz01,";
		columns +="ekpo_menge,ekpo_meins,ekpo_bprme,ekpo_netpr,ekpo_peinh,";
		columns +="purchaserowtotal,total,ekko_inco1,shipment_date,shipment_port,destination_port,EKKO_ZTERM,PAY_WAY,";
		columns +="ekko_zterm,pay_way,credit_no,SUM_QUANTITY,sum_total,leftCt,";
		columns +="pay_total,real_pay_total,pay_balance,purchase_contract_staus";
		request.setAttribute("grid_sql", sql.toString());
		request.setAttribute("grid_columns", columns.toString());
		request.setAttribute("grid_size", size==null||"".equals(size)?"10":size);
		servlet.processGrid(request, response, true);
	}
	/**
	 * 收货
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView toReceiptGoods(HttpServletRequest request,HttpServletResponse response){
		this.sendAttr(request);
		return new ModelAndView("sapss/reports/import/receiptGoods");
	}
	/**
	 * 
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws ServletException 
	 */
	public void rtnReceiptGoods(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
		List rtn = this.reportService.rtnDetailData("import_receipt_goods", this.parmMap(request));
		this.rtn(rtn, request, response);
	}
	/**
	 * 采购收货
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void receiptGoodsToExcel(HttpServletRequest request,HttpServletResponse response) throws IOException{
		this.detailToExcel("import_receipt_goods", request, "采购收货", response, null);
	}
	/**
	 * 收款
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView toReceiptMoney(HttpServletRequest request,HttpServletResponse response){
		this.sendAttr(request);
		return new ModelAndView("sapss/reports/import/receiptMoney");
	}
	/**
	 * 
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws ServletException 
	 */
	public void rtnReceiptMoney(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
		List rtn = this.reportService.rtnDetailData("import_receipt_money", this.parmMap(request));
		this.rtn(rtn, request, response);
	}
	/**
	 * 
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	public void receiptMoneyToExcel(HttpServletRequest request,HttpServletResponse response) throws IOException{
		this.detailToExcel("import_receipt_money", request, "收款", response, null);
	}
	/**
	 * 收款明细
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView toReceiptDetail(HttpServletRequest request,HttpServletResponse response){
		this.sendAttr(request);
		return new ModelAndView("sapss/reports/import/receiptDetail");
	}
	/**
	 * 收款明细
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws ServletException 
	 */
	public void rtnReceiptDetail(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
		List rtn = this.reportService.rtnDetailData("import_receipt_detail", this.parmMap(request));
		this.rtn(rtn, request, response);
	}
	/**
	 * 收款明细
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	public void receiptDetailToExcel(HttpServletRequest request,HttpServletResponse response) throws IOException{
		this.detailToExcel("import_receipt_detail", request, "收款明细", response, null);
	}
	/**
	 * 发货
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView toShipGoods(HttpServletRequest request,HttpServletResponse response){
		this.sendAttr(request);
		return new ModelAndView("sapss/reports/import/shipGoods");
	}
	/**
	 * 
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws ServletException 
	 */
	public void rtnShipGoods(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
		List rtn = this.reportService.rtnDetailData("import_ship_goods", this.parmMap(request));
		this.rtn(rtn, request, response);
	}
	/**
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void shipGoodsToExcel(HttpServletRequest request,HttpServletResponse response) throws IOException{
		this.detailToExcel("import_ship_goods", request, "销售发货", response, null);
		
	}
	/**
	 * 信用证信息表
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView toCreditView(HttpServletRequest request,HttpServletResponse response){
		this.sendAttr(request);
		return new ModelAndView("sapss/reports/import/creditView");
	}
	/**
	 * 
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws ServletException 
	 */
	public void rtnCreditView(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
		List rtn = this.reportService.rtnDetailData("import_credit_view", this.parmMap(request));
		this.rtn(rtn, request, response);
	}
	/**
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void creditViewToExcel(HttpServletRequest request,HttpServletResponse response) throws IOException{
		this.detailToExcel("import_credit_view", request, "信用证信息", response, null);
		
	}
	/**
	 * 付款明细
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView toPayDetail(HttpServletRequest request,HttpServletResponse response){
		this.sendAttr(request);
		return new ModelAndView("sapss/reports/import/payDetail");
	}
	/**
	 * 付款明细
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws ServletException 
	 */
	public void rtnPayDetail(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
		List rtn = this.reportService.rtnDetailData("import_pay_detail", this.parmMap(request));
		this.rtn(rtn, request, response);
	}
	/**
	 * 付款明细
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void payDetailToExcel(HttpServletRequest request,HttpServletResponse response) throws IOException{
		this.detailToExcel("import_pay_detail", request, "付款明细", response, null);
		
	}
	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView toPayCreditPicklist(HttpServletRequest request,HttpServletResponse response){
		this.sendAttr(request);
		return new ModelAndView("sapss/reports/import/payCreditPicklist");
	}
	/**
	 * 开票
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView toOpenBill(HttpServletRequest request,HttpServletResponse response){
		this.sendAttr(request);
		return new ModelAndView("sapss/reports/import/openBill");
	}
	/**
	 * 开票
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws ServletException 
	 */
	public void rtnOpenBill(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
		List rtn = this.reportService.rtnDetailData("import_open_bill", this.parmMap(request));
		this.rtn(rtn, request, response);
	}
	/**
	 * 开票
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void openBillToExcel(HttpServletRequest request,HttpServletResponse response) throws IOException{
		this.detailToExcel("import_open_bill", request, "开票", response, null);
		
	}

	/**
	 * 信用证到单一览表
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView toCreditPicklistView(HttpServletRequest request,HttpServletResponse response){
		this.sendAttr(request);
		return new ModelAndView("sapss/reports/import/creditPicklistView");
	}
	/**
	 * 信用证到单一览表
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws ServletException 
	 */
	public void rtnCreditPicklistView(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
		List rtn = this.reportService.rtnDetailData("import_credit_picklist_view", this.parmMap(request));
		this.rtn(rtn, request, response);
	}
	/**
	 * 信用证到单一览表
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void creditPicklistViewToExcel(HttpServletRequest request,HttpServletResponse response) throws IOException{
		this.detailToExcel("import_credit_picklist_view", request, "信用证到单一览表", response, null);
		
	}
	/**
	 * DPDA到单一览表
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView toDPDAPicklistView(HttpServletRequest request,HttpServletResponse response){
		this.sendAttr(request);
		return new ModelAndView("sapss/reports/import/DPDAPicklistView");
	}
	/**
	 * DPDA到单一览表
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws ServletException 
	 */
	public void rtnDPDAPicklistView(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
		List rtn = this.reportService.rtnDetailData("import_dpda_picklist_view", this.parmMap(request));
		this.rtn(rtn, request, response);
	}
	/**
	 * DPDA到单一览表
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void DPDAPicklistViewToExcel(HttpServletRequest request,HttpServletResponse response) throws IOException{
		this.detailToExcel("import_dpda_picklist_view", request, "DPDA到单一览表", response, null);
		
	}
	/**
	 * TT到单一览表
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView toTTPicklistView(HttpServletRequest request,HttpServletResponse response){
		this.sendAttr(request);
		return new ModelAndView("sapss/reports/import/TTPicklistView");
	}
	/**
	 * TT到单一览表
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws ServletException 
	 */
	public void rtnTTPicklistView(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
		List rtn = this.reportService.rtnDetailData("import_tt_picklist_view", this.parmMap(request));
		this.rtn(rtn, request, response);
	}
	/**
	 * TT到单一览表
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void TTPicklistViewToExcel(HttpServletRequest request,HttpServletResponse response) throws IOException{
		this.detailToExcel("import_tt_picklist_view", request, "TT到单一览表", response, null);
		
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws ServletException 
	 */
	public void rtnPayCreditPicklist(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{

		List rtn = this.reportService.rtnDetailData("import_pay_credit_picklist", this.parmMap(request));
		this.rtn(rtn, request, response);
	}
	/**
	 * 
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	public void payCreditPicklistToExcel(HttpServletRequest request,HttpServletResponse response) throws IOException{
		this.detailToExcel("import_pay_credit_picklist", request, "付款信用证到单", response, null);
	}
	/**
	 * 核销退税
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView toDrawback(HttpServletRequest request,HttpServletResponse response){
		this.sendAttr(request);
		return new ModelAndView("sapss/reports/import/drawback");
	}
	/**
	 * 
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws ServletException 
	 */
	public void rtnDrawback(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
		List rtn = this.reportService.rtnDetailData("import_drawback", this.parmMap(request));
		this.rtn(rtn, request, response);
	}
	/**
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void drawbackToExcel(HttpServletRequest request,HttpServletResponse response) throws IOException{
		this.detailToExcel("import_drawback", request, "进口核销统计", response, null);
	}
	
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView toReceiptLog(HttpServletRequest request,HttpServletResponse response){
//		request.setAttribute("purchaseId", request.getParameter("purchaseId"));
		this.sendAttr(request);
		return new ModelAndView("sapss/reports/import/receiptLog");
	}
	/**
	 * 
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws ServletException 
	 */
	public void rtnReceiptLog(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
//		ExtComponentServlet servlet = new ExtComponentServlet();
//		String purchaseId= request.getParameter("purchaseId");
//		String size = request.getParameter("limit");
//		String sql="select a.import_date,a.custome_no,a.pre_wr_cd,a.custome_price,a.custome_cash,a.custome_port,a.import_country,a.custome_total from ";
//		sql+=" t_receipt_goods_info a left join t_receipt_info b on a.receipt_id=b.receipt_id ";
//		sql+=" left join t_contract_purchase_info c on b.contract_purchase_id=c.contract_purchase_id where c.contract_purchase_id='"+purchaseId+"'";
//		String columns="import_date,custome_no,pre_wr_cd,custome_price,custome_cash,custome_port,import_country,custome_total";
//		request.setAttribute("grid_sql", sql);
//		request.setAttribute("grid_columns", columns);
//		request.setAttribute("grid_size", size==null||"".equals(size)?"100":size);
//		servlet.processGrid(request, response, true);
		List rtn = this.reportService.rtnDetailData("import_receipt_log", this.parmMap(request));
		this.rtn(rtn, request, response);
	}
	/**
	 * 
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	public void receiptLogToExcel(HttpServletRequest request,HttpServletResponse response) throws IOException{
		this.detailToExcel("import_receipt_log", request, "收货记录", response, null);
	}
	/**
	 * 
	 * @param request
	 * @return
	 */
	private Map<String, String> getParameterMap(HttpServletRequest request){
		Map<String, String> parameter = new HashMap<String,String>();
		Map<?, ?> parameterMap = request.getParameterMap();
		Iterator<?> keySet = parameterMap.keySet().iterator();
		for(;keySet.hasNext();){
			String key = keySet.next().toString();
			if(parameterMap.get(key)!=null){
				try{
					String value[]=(String[])parameterMap.get(key);
					if(value.length>0&&value[0].length()>0){
						value[0] = new String(value[0].getBytes("ISO-8859-1"),"UTF-8");
						parameter.put(key, value[0]);
					}
				}catch(Exception e){
				}
			}
		}
		return parameter;
	}	
	/**
	 * 
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws ServletException 
	 */
	public void exportToExcel(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException{
		
		String saleSql="select v.* from t_v_import_sale v where 1=1 ";
		saleSql +=queryCondiction(request,response);
		saleSql =  saleSql + " order by dept_name,contract_no";
		String[] columns= new String[]{"dept_name","trade_type","project_no","customer_link_man","contract_group_no",
		"contract_date","contract_no","old_contract_no","sap_order_no","kuagv_kunnr_name",
		"vbap_arktx","Vbap_Zmeng","VBAP_VRKME","VBAK_WAERK","konv_kbetr","VBAP_KPEIN",
		"saleRowTotal","order_total","shipment_port","shipment_date","vbkd_zterm","vbkd_zlsch",
		"SAME_CONTRACT_MATERIEL_CT","SUM_TOTAL","leftCt",
		"receipt_total","real_receipt_total","receipt_balance","sale_contract_staus","mat_group_name"};
		
		String[] colsName=new String[]{"部门","业务类型","立项号","买方/委托方","合同组号",
				"销售合同日期","销售合同号","外部纸质合同号","销售订单号","客户名称",
				"物料名称","合同数量","单位","合同币别","单价","条件定价单位",
				"单行物料总金额","合同总金额","合同交货地点","合同交货日期","收款条件","收款方式",
				"累计出仓数量","累计出仓金额","合同未执行数量",
				"累计应收金额","累计已收金额","应收余额","销售合同状态","物料组"};
		
		String purchaseSql="select v.* from t_v_import_purchase v where 1=1 ";
		purchaseSql +=queryCondiction(request,response);
		purchaseSql =  purchaseSql + " order by dept_name,contract_no";
		String[] columnp=new String[]{"dept_name","trade_type","project_no","CUSTOMER_LINK_MAN","contract_group_no",
			"ekko_bedat","contract_no","old_contract_no","sap_order_no","ekko_lifnr_name","ekpo_txz01",
			"ekpo_menge","ekpo_meins","ekpo_bprme","ekpo_netpr","ekpo_peinh",
			"purchaserowtotal","total","ekko_inco1","shipment_date","shipment_port","destination_port",
			"ekko_zterm","pay_way","credit_no","SUM_QUANTITY","sum_total","leftCt",
			"pay_total","real_pay_total","pay_balance","purchase_contract_staus","mat_group_name"};
		String[] colsNamep=new String[]{"部门","业务类型","立项号","买方/委托方","合同组号",
				"采购合同日期","采购合同号","外部纸质合同号","采购订单号","客户名称","物料名称",
				"合同数量","单位","合同币别","单价","条件定价单位",
				"单行物料总金额","合同总金额","价格术语","装运期限","装运港","目的港",
				"付汇条件","付汇方式","信用证号",
				"累计进仓数量","累计进仓金额","合同未执行数量",
				"累计应付金额","累计已付金额","应付余额","采购合同状态","物料组"};
		String fileName="进口跟踪表.xls";
		InputStream is = this.reportService.createExcelFile(columns, colsName, saleSql,columnp,colsNamep,purchaseSql);
		ReportUtil util = new ReportUtil();
		util.download(is,fileName,response);
		is.close();
	}
	/**
	 * 
	 * @param detailName
	 * @param request
	 * @param fileName
	 * @param response
	 * @throws IOException
	 */
	private void detailToExcel(String detailName,
								HttpServletRequest request,
								String fileName,
								HttpServletResponse response,
								List<?> dataList) throws IOException{
		this.detailToExcel(detailName, request, fileName, response, dataList, false);
	}
	/**
	 * 
	 * @param detailName
	 * @param request
	 * @param fileName
	 * @param response
	 * @throws IOException
	 */
	private void detailToExcel(String detailName,
								HttpServletRequest request,
								String fileName,
								HttpServletResponse response,
								List<?> dataList,
								boolean isTable) throws IOException{
		String col =  ReportUtil.getProperty(detailName);
		String sql= ReportUtil.queryForExcel(isTable, detailName, request, col);
		String[] cols = col.split(",");
		String colName = new String(ReportUtil.getProperty(detailName+"_names").getBytes("ISO-8859-1"),"UTF-8");
		String[] colNames = colName.split(",");
		InputStream is = this.reportService.createExcelFile(cols, colNames, sql,dataList);
		ReportUtil util = new ReportUtil();
		util.download(is,fileName+".xls",response);
		is.close();
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
	 */
	private void sendAttr(HttpServletRequest request){
		request.setAttribute("PROJECT_NO", request.getParameter("PROJECT_NO"));
		request.setAttribute("CONTRACT_NO", request.getParameter("CONTRACT_NO"));
		request.setAttribute("CONTRACT_GROUP_NO", request.getParameter("CONTRACT_GROUP_NO"));
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
	
}
