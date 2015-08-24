/*
 * @(#)HomeTradeController.java
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

public class HomeTradeController extends BaseMultiActionController{
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
		return new ModelAndView("sapss/reports/homeTrade/main");
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
		String projectNo= request.getParameter("projectNo");
		String size = request.getParameter("limit");
		String sql="select v.* from t_v_home_sale v where 1=1 ";
		sql +=queryCondiction(request,response);
		sql = sql + " order by dept_name,contract_no";
		String columns="PROJECT_NO,DEPT_NAME,TRADE_TYPE,CONTRACT_GROUP_NO,OLD_CONTRACT_NO,CONTRACT_NO,SAP_ORDER_NO,CONTRACT_DATE";
		columns +=",KUAGV_KUNNR_NAME,VBAP_ARKTX,VBAP_ZMENG,VBAP_VRKME,VBAK_WAERK,KONV_KBETR,VBAP_KPEIN";
		columns +=",SALEROWTOTAL,ORDER_TOTAL,SHIPMENT_DATE,SHIPMENT_PORT,VBKD_ZTERM,VBKD_ZLSCH,PASTED_TOTAL,SAME_CONTRACT_MATERIEL_CT";
		columns +=",LEFTCT,BILL_APPLY_NO";
		columns +=",RECEIPT_TOTAL,REAL_RECEIPT_TOTAL,RECEIPT_BALANCE,CONTRACT_SALES_ID,SALE_CONTRACT_STAUS";
		request.setAttribute("grid_sql", sql.toString());
		request.setAttribute("grid_columns", columns.toString());
		request.setAttribute("grid_size", size==null||"".equals(size)?"10":size);
		servlet.processGrid(request, response, true);
		//"PROJECT_NO","DEPT_NAME","TRADE_TYPE",dept_id,approved_time,"CONTRACT_SALES_ID","OLD_CONTRACT_NO","CONTRACT_NO","SAP_ORDER_NO","CONTRACT_DATE","RELATER","CONTRACT_GROUP_NO",
		//"VBAP_ARKTX","VBAP_ZMENG","VBAP_VRKME","VBAK_WAERK","KONV_KBETR","VBAP_KPEIN","SALEROWTOTAL","ORDER_TOTAL","SHIPMENT_PORT","SHIPMENT_DATE","VBKD_ZTERM","VBKD_ZLSCH","SAME_CONTRACT_MATERIEL_CT",
		//"SUM_TOTAL","PASTED_TOTAL","LEFTCT","BILL_APPLY_NO","RECEIPT_TOTAL","REAL_RECEIPT_TOTAL","RECEIPT_BALANCE"
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
		String sql="select v.* from t_v_home_purchase v where 1=1 ";
		sql +=queryCondiction(request,response);
		sql = sql + " order by dept_name,contract_no";
		String columns="dept_name,trade_type,project_no,approved_time,contract_group_no,old_contract_no,contract_no,sap_order_no,EKKO_BEDAT";
		columns +=",EKKO_LIFNR,EKKO_LIFNR_NAME,EKPO_MATNR,EKPO_TXZ01,EKPO_MENGE,EKPO_MEINS,EKPO_BPRME,EKPO_NETPR,EKPO_PEINH";
		columns +=",PURCHASEROWTOTAL,total,eket_eindt,factory_local,ekko_zterm,ekko_inco1";
		columns +=",PASTED_TOTAL,SAME_CONTRACT_MATERIEL_CT,SUM_TOTAL,LEFTCT,CONTRACT_PURCHASE_ID,PURCHASE_CONTRACT_STAUS";
		columns +=",pay_total,real_pay_total,pay_balance,purchase_contract_staus,pay_way";
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
	public ModelAndView toReceipt(HttpServletRequest request,HttpServletResponse response){
		this.sendAttr(request);
		return new ModelAndView("sapss/reports/homeTrade/receiptGoods");
	}
	/**
	 * 
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws ServletException 
	 */
	public void rtnReceiptGoods(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
		List<?> rtn = this.reportService.rtnDetailData("home_receipt_goods", this.parmMap(request));
		this.rtn(rtn, request, response);
	}
	public void receiptGoodsToExcel(HttpServletRequest request,HttpServletResponse response) throws IOException{
		this.detailToExcel("home_receipt_goods", request, "收货", response, null);
	}
	

	/**
	 * 发货
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView toShip(HttpServletRequest request,HttpServletResponse response){
		this.sendAttr(request);
		return new ModelAndView("sapss/reports/homeTrade/shipGoods");
	}
	/**
	 * 
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	public void shipGoodsToExcel(HttpServletRequest request,HttpServletResponse response) throws IOException{
		this.detailToExcel("home_ship_goods", request, "出货", response, null);
	}
	/**
	 * 
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws ServletException 
	 */
	public void rtnShipGoods(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
		List rtn = this.reportService.rtnDetailData("home_ship_goods", this.parmMap(request));
		this.rtn(rtn, request, response);
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
	 * 付款明细
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView toPayDetail(HttpServletRequest request,HttpServletResponse response){
		this.sendAttr(request);
		return new ModelAndView("sapss/reports/homeTrade/payDetail");
	}
	/**
	 * 发票校验
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView toBillValidate(HttpServletRequest request,HttpServletResponse response){
		this.sendAttr(request);
		return new ModelAndView("sapss/reports/homeTrade/billValidate");
	}
	/**
	 * 发票校验
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws ServletException 
	 */
	public void rtnBillValidate(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
		List<?> rtn = this.reportService.rtnDetailData("home_bill_validate", this.parmMap(request));
		this.rtn(rtn, request, response);
	}
	/**
	 * 发票校验
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws ServletException 
	 */
	public void billValidateToExcel(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
		this.detailToExcel("home_bill_validate", request, "发票校验", response, null);
	}
	
	
	/**
	 * 返回付款数据
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws ServletException 
	 */
	public void rtnPayDetail(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
		List<?> rtn = this.reportService.rtnDetailData("home_pay_detail", this.parmMap(request));
		this.rtn(rtn, request, response);
	}
	/**
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void payDetailToExcel(HttpServletRequest request,HttpServletResponse response) throws IOException{
		this.detailToExcel("home_pay_detail", request, "付款明细", response, null);
	}
	/**
	 * 收款明细
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView toReceiptDetail(HttpServletRequest request,HttpServletResponse response){
		this.sendAttr(request);
		return new ModelAndView("sapss/reports/homeTrade/receiptDetail");
	}
	/**
	 * 开票
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView toOpenBill(HttpServletRequest request,HttpServletResponse response){
		this.sendAttr(request);
		return new ModelAndView("sapss/reports/homeTrade/openBill");
	}
	/**
	 * 开票
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws ServletException 
	 */
	public void rtnOpenBill(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
		List<?> rtn = this.reportService.rtnDetailData("home_open_bill", this.parmMap(request));
		this.rtn(rtn, request, response);
	}
	/**
	 * 开票
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void openBillToExcel(HttpServletRequest request,HttpServletResponse response) throws IOException{
		this.detailToExcel("home_open_bill", request, "开票", response, null);
	}
	
	/**
	 * 返回收款数据
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws ServletException 
	 */
	public void rtnReceiptDetail(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
		List<?> rtn = this.reportService.rtnDetailData("home_receipt_detail", this.parmMap(request));
		this.rtn(rtn, request, response);
	}
	/**
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void receiptDetailToExcel(HttpServletRequest request,HttpServletResponse response) throws IOException{
		this.detailToExcel("home_receipt_detail", request, "收款明细", response, null);
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
		
		String saleSql="select v.* from t_v_home_sale v where 1=1 ";
		saleSql +=queryCondiction(request,response);
		saleSql =  saleSql + " order by dept_name,contract_no";
		String[] columns=new String[]{"dept_name","trade_type","project_no","contract_group_no","contract_date","contract_no","old_contract_no","sap_order_no",
				"kuagv_kunnr_name","vbap_arktx","vbap_zmeng","vbap_vrkme","vbak_waerk",
				"konv_kbetr","vbap_kpein","salerowtotal","order_total","shipment_date","shipment_port","vbkd_zterm","vbkd_zlsch",
				"same_contract_materiel_ct","pasted_total","leftct",
				"receipt_total","real_receipt_total","receipt_balance","sale_contract_staus","mat_group_name"};

		String[] colsName=new String[]{"部门","业务类型","立项号","合同组号","销售合同日期","销售合同号","外部纸质合同号","销售订单号",
				"客户名称","物料名称","合同数量","单位","合同币别",
				"单价","条件定价单位","单行物料总金额","合同总金额","合同交货日期","合同交货地点","收款条件","收款方式",
				"累计出仓数量","累计出仓金额","合同未执行数量",
				"累计应收金额","累计已收金额","应收余额","销售合同状态","物料组"};
		
		String purchaseSql="select v.* from t_v_home_purchase v where 1=1";
		purchaseSql +=queryCondiction(request,response);
		purchaseSql =  purchaseSql + " order by dept_name,contract_no";
		String[] columnp= new String[]{"dept_name","trade_type","project_no","contract_group_no","ekko_bedat","contract_no","old_contract_no","sap_order_no",
			"ekko_lifnr_name","ekpo_txz01","ekpo_menge","ekpo_meins","ekpo_bprme","ekpo_netpr","ekpo_peinh",
			"purchaserowtotal","total","eket_eindt","factory_local","ekko_zterm","pay_way",
			"same_contract_materiel_ct","sum_total","leftct",
			"pay_total","real_pay_total","pay_balance","purchase_contract_staus","mat_group_name"};
		String[] colsNamep=new String[]{"部门","业务类型","立项号","合同组号","采购合同日期","采购合同号","外部纸质合同号","采购订单号",
				"供应商名称","物料名称","合同数量","单位","合同币别","单价","条件定价单位",
				"单行物料总金额","合同总金额","合同交货日期","合同交货地点","付款条件","付款方式",
				"累计进仓数量","累计进仓金额","合同未执行数量",
				"累计应付金额","累计已付金额","应付余额","采购合同状态","物料组"};
		String fileName="内贸跟踪表.xls";
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
	private void detailToExcel(String detailName,HttpServletRequest request,String fileName,HttpServletResponse response,List<?> dataList) throws IOException{
		String col = ReportUtil.getProperty(detailName);
		String sql = ReportUtil.queryForExcel(false,detailName, request, col);
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
	
}
