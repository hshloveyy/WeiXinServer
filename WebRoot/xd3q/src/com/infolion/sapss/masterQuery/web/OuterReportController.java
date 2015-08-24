/*
 * @(#)OuterReportController.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：陈喜平
 *  时　间：2009-8-4
 *  描　述：创建
 */

package com.infolion.sapss.masterQuery.web;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

import com.infolion.platform.component.ExtComponentServlet;
import com.infolion.platform.core.web.BaseMultiActionController;
import com.infolion.sapss.common.ExcelObject;
import com.infolion.sapss.masterQuery.service.MasterQueryJdbcService;

public class OuterReportController extends BaseMultiActionController{
	
	@Autowired
	private MasterQueryJdbcService masterQueryJdbcService;
	/**
	 * 报表入口
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView toMainReport(HttpServletRequest request,HttpServletResponse response){
		return new ModelAndView("sapss/reports/mainReport");
	}
	/**
	 * 进口报表
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView toImportReport(HttpServletRequest request,HttpServletResponse response){
		return new ModelAndView("sapss/reports/importReport");
	}
	/**
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void rtnFindImport(HttpServletRequest request,HttpServletResponse response) throws Exception {
		ExtComponentServlet servlet = new ExtComponentServlet();
		String projectNo = request.getParameter("projectNo");
		StringBuffer sql = new StringBuffer();
		StringBuffer columns = new StringBuffer();
		columns.append("project_id,project_no,trade_type,apply_time,provider_link_man");
		columns.append(",purchase_contract_no,ekko_lifnr,ekko_lifnr_name,total,shipment_date,shipment_port,destination_port");
		columns.append(",ekpo_menge,ekpo_meins,ekpo_bprme,ekpo_netpr");
		columns.append(",sale_contract_no,kuagv_kunnr,kuagv_kunnr_name,order_total");
		columns.append(",ekpo_matnr,ekpo_txz01,eket_eindt,ekpo_peinh");
		sql.append("select * from V_PROJECT_CONTRACT_CREDIT");
		String size = request.getParameter("limit");
		request.setAttribute("grid_sql", sql.toString());
		request.setAttribute("grid_columns", columns.toString());
		request.setAttribute("grid_size", size==null||"".equals(size)?"10":size);
		servlet.processGrid(request, response, true);
	}
	
	/**
	 * 出口报表
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView toExportReport(HttpServletRequest request,HttpServletResponse response){
		return new ModelAndView("sapss/reports/exportReport");
	}
	/**
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void rtnFindExport(HttpServletRequest request,HttpServletResponse response) throws Exception {
		ExtComponentServlet servlet = new ExtComponentServlet();
		String projectNo = request.getParameter("projectNo");
		String contractNo = request.getParameter("contractNo");
		String saleSAPNo = request.getParameter("saleSAPNo");
		String purchaseSAPNo = request.getParameter("purchaseSAPNo");
		StringBuffer sql = new StringBuffer();
		StringBuffer columns = new StringBuffer();
		columns.append("project_id,project_no,trade_type,apply_time,customer_link_man,");
		columns.append("purchase_contract_no,purchase_sap_no,ekko_lifnr,ekko_lifnr_name,total,");
		columns.append("ekpo_menge,ekpo_meins,ekpo_bprme,ekpo_netpr,");
		columns.append("sale_contract_no,sale_sap_no,vbak_audat,kuagv_kunnr,kuagv_kunnr_name,vbkd_inco1_name,order_total,shipment_date,shipment_port,destination_port,");
		columns.append("vbap_matnr,vbap_arktx,vbap_kwmeng,vbap_vrkme,konv_kbetr,");
		columns.append("custom_create_date,credit_rec_date,credit_no,loading_period,period");
		
		sql.append("select ").append(columns).append(" from (");
		sql.append("select distinct(tt.contract_sales_id),tt.*,");
		sql.append("g.custom_create_date,g.credit_rec_date,g.credit_no,g.loading_period,g.period from ");
		sql.append("(select t.*,e.credit_id from  ");
		sql.append("(select c.project_no,c.project_id,c.trade_type,c.apply_time,c.customer_link_man,");
		sql.append("'' contract_purchase_id,d.contract_sales_id,");
		sql.append("d.CONTRACT_NO sale_contract_no,d.sap_order_no sale_sap_no,d.vbak_audat,d.kuagv_kunnr,");
		sql.append("d.kuagv_kunnr_name,d.vbkd_inco1_name,d.Vbak_Waerk_Name,d.ORDER_TOTAL,");
		sql.append("d.SHIPMENT_DATE,d.shipment_port,d.destination_port,d.VBAP_MATNR,d.vbap_arktx,");
		sql.append("d.vbap_kwmeng,d.vbap_vrkme,d.konv_kbetr,");
		sql.append("'' purchase_contract_no,'' purchase_sap_no,'' ekko_lifnr,'' ekko_lifnr_name,");
		sql.append("'' total,'' ekpo_bprme,0 ekpo_menge,'' EKPO_MEINS,0 EKPO_NETPR ");
		sql.append("from t_project_info c,");
		sql.append("(select a.contract_sales_id,a.project_id,a.is_available,a.CONTRACT_NO,a.sap_order_no,a.vbak_audat,");
		sql.append("a.kuagv_kunnr,a.kuagv_kunnr_name,a.vbkd_inco1_name,a.Vbak_Waerk_Name,a.ORDER_TOTAL,");
		sql.append("a.SHIPMENT_DATE,a.shipment_port,a.destination_port,b.VBAP_MATNR,b.vbap_arktx,b.vbap_kwmeng,");
		sql.append("b.vbap_vrkme,b.konv_kbetr from t_contract_sales_info a,t_contract_sales_materiel b ");
		sql.append("where a.contract_sales_id=b.contract_sales_id");
		sql.append(" union all ");
		sql.append("select a.contract_sales_id,a.project_id,a.is_available,a.contract_no,a.sap_order_no,a.vbak_audat,");
		sql.append("a.kuagv_kunnr,a.kuagv_kunnr_name,a.vbkd_inco1_name,a.Vbak_Waerk_Name,a.ORDER_TOTAL,");
		sql.append("a.SHIPMENT_DATE,a.shipment_port,a.destination_port,b.vbap_matnr,b.vbap_arktx,b.vbap_kwmeng,");
		sql.append("b.vbap_vrkme,b.konv_kbetr from t_contract_sales_info a,t_contract_agent_materiel b");
		sql.append(" where a.contract_sales_id=b.contract_sales_id)d");
		sql.append(" where c.project_id = d.project_id  and c.is_available='1'");
		sql.append(" and d.is_available='1' ");
		sql.append(" and c.trade_type in('2','4','5')");
		if(StringUtils.isNotBlank(projectNo))
			sql.append(" and c.project_no='"+projectNo+"'");
		if(StringUtils.isNotBlank(contractNo))
			sql.append(" and d.contract_no='"+contractNo+"'");
		if(StringUtils.isNotBlank(saleSAPNo))
			sql.append(" and d.sap_order_no='"+saleSAPNo+"'");
		sql.append(")t left join t_credit_rec e on t.contract_sales_id = e.contract_sales_id");
		sql.append(")tt left join t_credit_info g on tt.credit_id=g.credit_id and g.is_available='1'");
		sql.append(" union all ");
		sql.append("select distinct(tt.contract_purchase_id), tt.*,");
		sql.append("g.custom_create_date,g.credit_rec_date,g.credit_no,g.loading_period,g.period from ");
		sql.append("(select t.*,e.credit_id from  ");
		sql.append("(select a.project_no,a.project_id,a.trade_type,a.apply_time,a.customer_link_man,");
		sql.append("d.contract_purchase_id,'' contract_sales_id,");
		sql.append("'' sale_contract_no,'' sale_sap_no,'' vbak_audat,'' kuagv_kunnr,");
		sql.append("'' kuagv_kunnr_name,'' vbkd_inco1_name,'' Vbak_Waerk_Name,'' ORDER_TOTAL,");
		sql.append("'' SHIPMENT_DATE,'' shipment_port,'' destination_port,'' VBAP_MATNR,'' vbap_arktx,");
		sql.append("0 vbap_kwmeng,'' vbap_vrkme,0 konv_kbetr,");
		sql.append("d.contract_no purchase_contract_no,d.sap_order_no purchase_sap_no,d.ekko_lifnr,d.ekko_lifnr_name,");
		sql.append("d.total,d.ekpo_bprme,d.ekpo_menge,d.EKPO_MEINS,d.EKPO_NETPR ");
		sql.append("from t_project_info a,");
		sql.append("(select b.contract_purchase_id,b.is_available,b.project_id, b.contract_no,b.sap_order_no,b.ekko_lifnr,b.ekko_lifnr_name,");
		sql.append("b.total,c.ekpo_bprme,c.ekpo_menge,c.EKPO_MEINS,c.EKPO_NETPR ");
		sql.append("from t_contract_purchase_info b,t_contract_purchase_materiel c where b.contract_purchase_id=c.contract_purchase_id)d");
		sql.append(" where a.project_id=d.project_id and a.is_available='1'");
		sql.append(" and d.is_available='1'");
		sql.append(" and a.trade_type in('2','4','5')");
		if(StringUtils.isNotBlank(projectNo))
			sql.append(" and a.project_no='"+projectNo+"'");
		if(StringUtils.isNotBlank(contractNo))
			sql.append(" and d.contract_no='"+contractNo+"'");
		sql.append(")t left join t_credit_create e on t.contract_purchase_id = e.contract_purchase_id");
		sql.append(")tt left join t_credit_info g on tt.credit_id=g.credit_id and g.is_available='1'");
		sql.append(")");
		String size = request.getParameter("limit");
		request.setAttribute("grid_sql", sql.toString());
		request.setAttribute("grid_columns", columns.toString());
		request.setAttribute("grid_size", size==null||"".equals(size)?"10":size);
		servlet.processGrid(request, response, true);
	}
	/**
	 * 内贸报表
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView toHomeTradeReport(HttpServletRequest request,HttpServletResponse response){
		return new ModelAndView("sapss/reports/homeTradeReport");
	}
	/**
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void rtnHomeTradeReport(HttpServletRequest request,HttpServletResponse response) throws Exception {
		ExtComponentServlet servlet = new ExtComponentServlet();
		String projectNo = request.getParameter("projectNo");
		StringBuffer sql = new StringBuffer();
		sql.append("select * from (");
		sql.append("select a.project_id,a.project_name,a.project_no,a.trade_type,a.approved_time,a.customer_link_man,a.apply_time,");
		sql.append("b.old_contract_no,b.contract_no purchase_contract_no,'' sale_contract_no,b.sap_order_no ,b.shipment_date,b.shipment_port,");
		sql.append("b.ekko_bedat,b.ekko_lifnr,b.ekko_lifnr_name,b.total,b.ekko_zterm_name,");
		sql.append("c.ekpo_matnr,c.ekpo_txz01,c.ekpo_menge,c.ekpo_meins,c.ekpo_bprme,c.ekpo_netpr,");
		sql.append("'' vbak_audat,'' kuagv_kunnr,");
		sql.append("'' kuagv_kunnr_name,'' vbak_waerk,'' order_total,'' vbkd_zlsch_name,");//销售
		sql.append("'' as vbap_matnr,'' as vbap_arktx,0 as vbap_zmeng,'' as vbap_vrkme,0 as konv_kbetr");
		//条件定价单位,单行物料总金额(订单行项目数量*含税单价/条件定价单位),收款条件(付款条件)
		sql.append(",c.ekpo_peinh,c.ekpo_menge*c.ekpo_netpr/nvl(c.ekpo_peinh,1) purchaseRowTotal,b.ekko_zterm");
		sql.append(",'' vbap_kpein,0 saleRowTotal,'' vbkd_zterm");
		//
		sql.append(",t_receipt_info.RECEIPT_NO,t_receipt_material.QUANTITY receipt_quantity,t_receipt_material.CREATOR_TIME receipt_creator_time,t_receipt_material.PRICE receipt_price");
		sql.append(",'' SHIP_NO,0 ship_QUANTITY,'' ship_CREATOR_TIME");
		//收款信息维护表
		sql.append(",'' RECEIPT_DATE,'' REAL_RECEIPT_DATE");
		sql.append(",'' RECEIPT_TOTAL,'' REAL_RECEIPT_TOTAL,'' RECEIPT_BALANCE,'' SALE_CONTRACT_STAUS");
		//付款信息维护表
		sql.append(",t_payment_money_info.pay_date,t_payment_money_info.real_pay_date,t_payment_money_info.pay_total,t_payment_money_info.real_pay_total");
		sql.append(",t_payment_money_info.pay_balance,t_payment_money_info.purchase_contract_staus");
		sql.append(" from ");
		sql.append("t_project_info a,");
		sql.append("t_contract_purchase_info b,");
		sql.append("t_contract_purchase_materiel c");
		sql.append(",t_receipt_info");
		sql.append(",t_receipt_material");
		sql.append(",t_payment_money_info");
		sql.append(" where");
		sql.append(" a.is_available='1'");
		sql.append(" and a.project_id =b.project_id and");
		sql.append(" b.contract_purchase_id =c.contract_purchase_id and");
		sql.append(" b.is_available='1' and a.trade_type='7'");
		sql.append(" and t_receipt_info.is_available='1' and b.contract_purchase_id=t_receipt_info.contract_purchase_id");
		sql.append(" and t_receipt_info.receipt_id=t_receipt_material.receipt_id");
//		sql.append(" and b.contract_purchase_id=t_payment_money_info.contract_id");
		if(StringUtils.isNotBlank(projectNo))
			sql.append(" and a.project_no='"+projectNo+"'");
		sql.append(" union all (");        
		sql.append("select a.project_id,a.project_name,a.project_no,a.trade_type,a.approved_time,a.customer_link_man,a.apply_time,");//立项
		sql.append("b.old_contract_no,'' purchase_contract_no,b.contract_no sale_contract_no,b.sap_order_no,b.shipment_date,b.shipment_port,");
		sql.append("'' ekko_bedat,'' ekko_lifnr,'' ekko_lifnr_name,'' total,'' ekko_zterm_name,");
		sql.append("'' ekpo_matnr,'' ekpo_txz01,0 ekpo_menge,'' ekpo_meins,'' ekpo_bprme,0 ekpo_netpr,");
		sql.append("b.vbak_audat,b.kuagv_kunnr,");//销售
		sql.append("b.kuagv_kunnr_name,b.vbak_waerk,b.order_total,b.vbkd_zlsch_name,");//销售
		sql.append("c.vbap_matnr,c.vbap_arktx,c.vbap_zmeng,c.vbap_vrkme,c.konv_kbetr");//销售物料
//		sql.append("d.ship_no");//发货
		//条件定价单位,单行物料总金额(销售订单行项目数量*含税单价/条件定价单位),收款条件(付款条件)
		sql.append(",'' ekpo_peinh,0 purchaseRowTotal,'' ekko_zterm");
		sql.append(",c.vbap_kpein,c.vbap_zmeng*c.konv_kbetr/nvl(c.vbap_kpein,1) saleRowTotal,b.vbkd_zterm");
		//
		sql.append(",'' RECEIPT_NO,0 receipt_quantity,'' receipt_creator_time,0 receipt_price");
		sql.append(",t_ship_info.SHIP_NO,t_ship_material.QUANTITY ship_quantity,t_ship_material.CREATOR_TIME ship_creator_time");
		//收款信息维护表
		sql.append(",t_receipt_money_info.RECEIPT_DATE,t_receipt_money_info.REAL_RECEIPT_DATE");
		sql.append(",t_receipt_money_info.RECEIPT_TOTAL,t_receipt_money_info.REAL_RECEIPT_TOTAL,t_receipt_money_info.RECEIPT_BALANCE,t_receipt_money_info.SALE_CONTRACT_STAUS");
		//付款信息维护表
		sql.append(",'' pay_date,'' real_pay_date,'' pay_total,'' real_pay_total");
		sql.append(",'' pay_balance,'' purchase_contract_staus");
		
		sql.append(" from ");
		sql.append("t_project_info a,");
		sql.append("t_contract_sales_info b,");
		sql.append("t_contract_sales_materiel c");
		sql.append(",t_ship_info");
		sql.append(",t_ship_material");
		sql.append(",t_receipt_money_info");
//		sql.append("(select t.* from  t_contract_sales_info aa left join t_ship_info t on aa.contract_sales_id=t.contract_sales_id)d");
		sql.append(" where ");
		sql.append(" a.is_available='1'");
		sql.append(" and a.project_id = b.project_id ");//立项到合同
		sql.append(" and b.contract_sales_id = c.contract_sales_id ");//合同到合同物料
		sql.append(" and b.is_available='1' and a.trade_type='7'");
		sql.append(" and t_ship_info.is_available='1' and b.contract_sales_id=t_ship_info.contract_sales_id");
		sql.append(" and t_ship_info.ship_id=t_ship_material.ship_id");
//		sql.append(" and b.contract_sales_id = t_receipt_money_info.contract_id");
		if(StringUtils.isNotBlank(projectNo))
			sql.append(" and a.project_no='"+projectNo+"'");
		sql.append(")");
		sql.append(") order by apply_time ");
		StringBuffer columns = new StringBuffer();
		columns.append("project_id,project_name,project_no,trade_type,approved_time,customer_link_man,");
		columns.append("old_contract_no,purchase_contract_no,sale_contract_no,sap_order_no,shipment_date,shipment_port,");
		columns.append("ekko_bedat,ekko_lifnr,ekko_lifnr_name,total,ekko_zterm_name,");
		columns.append("ekpo_matnr,ekpo_txz01,ekpo_menge,ekpo_meins,ekpo_bprme,ekpo_netpr,");
		columns.append("vbak_audat,kuagv_kunnr,");
		columns.append("kuagv_kunnr_name,vbak_waerk,order_total,vbkd_zlsch_name,");//销售
		columns.append("vbap_matnr,vbap_arktx,vbap_zmeng,vbap_vrkme,konv_kbetr");
		columns.append(",ekpo_peinh,purchaseRowTotal,ekko_zterm");
		columns.append(",vbap_kpein,saleRowTotal,vbkd_zterm");
		columns.append(",RECEIPT_NO,receipt_quantity,receipt_creator_time,receipt_price");
		columns.append(",SHIP_NO,ship_quantity,ship_creator_time");
		columns.append(",RECEIPT_DATE,REAL_RECEIPT_DATE,RECEIPT_TOTAL,REAL_RECEIPT_TOTAL,RECEIPT_BALANCE,SALE_CONTRACT_STAUS");
		columns.append(",pay_date,real_pay_date,pay_total,real_pay_total,pay_balance,purchase_contract_staus");
		
		String size = request.getParameter("limit");
		request.setAttribute("grid_sql", sql.toString());
		request.setAttribute("grid_columns", columns.toString());
		request.setAttribute("grid_size", size==null||"".equals(size)?"10":size);
		servlet.processGrid(request, response, true);
	}
	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView toAgentMaterialStorage(HttpServletRequest request,HttpServletResponse response){
		return new ModelAndView("sapss/reports/agentMaterialStorage");
	}
	/**
	 * 
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws ServletException 
	 */
	public void rtnAgentMaterialStorage(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
		
		
		StringBuffer columns = new StringBuffer();
		columns.append("material_code,material_name,factory_d_factory,batch_no,approved_time,quantity,out,leave,material_unit,in_total,out_total,leave1,contract_paper_no,contract_name");
		
		ExtComponentServlet servlet = new ExtComponentServlet();
		String size = request.getParameter("limit");
		request.setAttribute("grid_sql", getAgentStorageSql(request, response));
		request.setAttribute("grid_columns", columns.toString());
		request.setAttribute("grid_size", size==null||"".equals(size)?"10":size);
		servlet.processGrid(request, response, true);

	}
	
	private String getAgentStorageSql(HttpServletRequest request,
			HttpServletResponse response){
		String materialNo = request.getParameter("materialNo");
		String factory = request.getParameter("factory");
		String warehouse = request.getParameter("warehouse");
		String batchNo = request.getParameter("batchNo");
		String contractNo = request.getParameter("contractNo");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		StringBuffer sb = new StringBuffer();
		StringBuffer whereSb = new StringBuffer();
		
		if(StringUtils.isNotBlank(contractNo))
			whereSb.append(" and i.contract_paper_no like '%"+contractNo+"%'");
		if(StringUtils.isNotBlank(factory))
			whereSb.append(" and i.factory = '"+factory+"'");
		if(StringUtils.isNotBlank(warehouse))
			whereSb.append(" and i.warehouse = '"+warehouse+"'");
		if(StringUtils.isNotBlank(startTime) && StringUtils.isNotBlank(endTime)){
			whereSb.append(" and i.approved_time between '"+startTime+" 00:00:00'");
			whereSb.append(" and '"+endTime+" 24:00:00'");
		}
		if(StringUtils.isNotBlank(materialNo))
			whereSb.append(" and i.material_code='"+materialNo+"'");
		if(StringUtils.isNotBlank(batchNo))
			whereSb.append(" and i.batch_no like '%"+batchNo+"%' ");
		
		sb.append("select i.receipt_id,i.batch_no,i.material_code,i.material_name,i.material_unit ,i.warehouse,i.quantity,");
		sb.append("i.approved_time,i.contract_paper_no,i.contract_name, i.factory as factory_d_factory,nvl(o.quantity,0) as out,nvl(i.quantity,0)-nvl(o.quantity,0) as leave, ");
		sb.append(" i.total as in_total,nvl(o.out_total,0) as out_total,nvl(i.total,0)-nvl(o.out_total,0) as leave1 ");
		sb.append("from v_agent_storage_in i left outer join ");
	    sb.append(" (select  batch_no, material_code , warehouse, nvl(sum(quantity),0) quantity ,nvl(sum(total),0) as out_total from v_agent_storage_out ");
	    if(StringUtils.isNotBlank(startTime) && StringUtils.isNotBlank(endTime)){
		    sb.append("   where approved_time>='"+startTime+" 00:00:00' and approved_time<='"+endTime+" 24:00:00' ");
	    }
		sb.append(" group by batch_no, material_code, warehouse) o on o.batch_no=i.batch_no ");
		sb.append("and o.material_code=i.material_code and o.warehouse = i.warehouse");
		sb.append(" where 1=1 ");
		sb.append(whereSb);
		sb.append(" union ");
		sb.append("select i.ship_id,i.batch_no,i.material_code,i.material_name,i.material_unit,i.warehouse,0 as quantity,i.approved_time," +
				"i.contract_paper_no,i.contract_name,i.factory as factory_d_factory,i.quantity as out,0-nvl(i.quantity,0) as leave," +
				"0 as in_total,nvl(i.total,0) as out_total,0-nvl(i.total,0) as leave1 from v_agent_storage_out i where i.batch_no not in " +
				"(select i.batch_no from v_agent_storage_in i where 1=1 ").append(whereSb).append(")");
		sb.append(whereSb);

		return sb.toString();
	}
	
	public void dealOutToExcel(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		String[] titles  = new String[]{"物料编号","物料描述","工厂","批次","进仓时间","单位","进仓数量","进仓金额","出仓数量","出仓金额","当前库存","差额"};

		ExcelObject excel = new ExcelObject(titles);
		String sql = getAgentStorageSql(request, response);
		
		masterQueryJdbcService.dealOutToExcel(sql ,excel);
		
		try{
			//response.reset();
	        response.setCharacterEncoding("utf-8");
	        response.setContentType("application/x-download;charset=utf-8");
	        response.setHeader("Content-Disposition", (new StringBuilder("attachment;filename=")).append(URLEncoder.encode("贸易明细表.xls", "utf-8")).toString());
			excel.write(response.getOutputStream());
			response.flushBuffer();
			response.getOutputStream().close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	public MasterQueryJdbcService getMasterQueryJdbcService() {
		return masterQueryJdbcService;
	}
	public void setMasterQueryJdbcService(
			MasterQueryJdbcService masterQueryJdbcService) {
		this.masterQueryJdbcService = masterQueryJdbcService;
	}
}
