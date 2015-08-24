/*
 * @(#)FundsRequireQueryController.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：陈喜平
 *  时　间：2009-11-3
 *  描　述：创建
 */

package com.infolion.sapss.capitalRequirement.web;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

import com.infolion.platform.core.web.BaseMultiActionController;
import com.infolion.sapss.capitalRequirement.service.FundsRequireQueryService;
import com.infolion.sapss.masterQuery.service.ReportService;
import com.infolion.sapss.masterQuery.web.ReportUtil;

public class FundsRequireQueryController extends BaseMultiActionController  {
	@Autowired
	private FundsRequireQueryService fundsRequireQueryService;
	@Autowired
	private ReportService reportService;
	public void setReportService(ReportService reportService) {
		this.reportService = reportService;
	}
	public void setFundsRequireQueryService(
			FundsRequireQueryService fundsRequireQueryService) {
		this.fundsRequireQueryService = fundsRequireQueryService;
	}
	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView toFundsRequireQuery(HttpServletRequest request,HttpServletResponse response){
		return new ModelAndView("sapss/capitalRequirement/fundsRequireQuery");
		
	}
	/**
	 * 
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	public void toExcel(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String[] cols= new String[]{"date","payment_value","bankAcceptance_value",
				"homeCredit_value","cnyShortLoan_value","cnyLongLoan_value","foreignLoan_value","draft_value","draftOut_value","day_sum","bapi_receive","bapi_pay"};
		String[] colNames= new String[]{"到期日","到期信用证","到期银行承兑","到期国内信用证",
					"到期人民币短期借款","到期人民币长期借款","到期外币短期借款","到期进口押汇","到期出口押汇","日合计","应收","应付"};
		List rtn = this.fundsRequireQueryService.makeData(this.paramMap(request));
		InputStream is = this.reportService.createExcelFile(cols, colNames, null,rtn);
		ReportUtil util = new ReportUtil();
		util.download(is,"资金需求.xls",response);
		is.close();
	}
	/**
	 * 
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	public void rtnData(HttpServletRequest request,HttpServletResponse response) throws IOException{
		List rtn = this.fundsRequireQueryService.makeData(this.paramMap(request));
		this.rtn(rtn, request, response);
	}
	/**
	 * 
	 * @param request
	 * @return
	 */
	private Map<String,String> paramMap(HttpServletRequest request){
		Map<String,String> map = new HashMap();
		map.put("year", request.getParameter("year"));
		map.put("month", request.getParameter("month"));
		return map;
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
	public ModelAndView showImportPaymentInfo(HttpServletRequest request,HttpServletResponse response){
		request.setAttribute("date", request.getParameter("date"));
		return new ModelAndView("sapss/capitalRequirement/importPaymentInfo");
	}
	public void rtnImportPaymentInfo(HttpServletRequest request,HttpServletResponse response) throws IOException{
		List rtn = this.fundsRequireQueryService.rtnImportPaymentInfo(request.getParameter("date"));
		this.rtn(rtn, request, response);
	}
	
	//人民币长/短期借款
	public ModelAndView showTableInfo(HttpServletRequest request,HttpServletResponse response){
		request.setAttribute("date", request.getParameter("date"));
		request.setAttribute("classify", request.getParameter("classify"));
		return new ModelAndView("sapss/capitalRequirement/cnyLoanInfo");
	}
	public void rtn4TableInfo(HttpServletRequest request,HttpServletResponse response) throws IOException{
		List rtn = this.fundsRequireQueryService.rtn4TableInfo(request.getParameter("date"),request.getParameter("classify"));
		this.rtn(rtn, request, response);
	}
	
	//外币借款
	public ModelAndView showForeignShortLoanInfo(HttpServletRequest request,HttpServletResponse response){
		request.setAttribute("date", request.getParameter("date"));
		return new ModelAndView("sapss/capitalRequirement/foreignShortLoanInfo");
	}
	public void rtnForeignShortLoanInfo(HttpServletRequest request,HttpServletResponse response) throws IOException{
		List rtn = this.fundsRequireQueryService.rtnForeignShortLoanInfo(request.getParameter("date"));
		this.rtn(rtn, request, response);
	}
	
	//进口押汇
	public ModelAndView showDraftInfo(HttpServletRequest request,HttpServletResponse response){
		request.setAttribute("date", request.getParameter("date"));
		return new ModelAndView("sapss/capitalRequirement/draftInfo");
	}
	public void rtnDraftInfo(HttpServletRequest request,HttpServletResponse response) throws IOException{
		List rtn = this.fundsRequireQueryService.rtnDraftBillInfo(request.getParameter("date"));
		this.rtn(rtn, request, response);
	}
	
	//出口押汇
	public ModelAndView showDraftOutInfo(HttpServletRequest request,HttpServletResponse response){
		request.setAttribute("date", request.getParameter("date"));
		return new ModelAndView("sapss/capitalRequirement/draftOutInfo");
	}
	public void rtnDraftOutInfo(HttpServletRequest request,HttpServletResponse response) throws IOException{
		List rtn = this.fundsRequireQueryService.rtnDraftOutBillInfo(request.getParameter("date"));
		this.rtn(rtn, request, response);
	}
	
	//银行承兑汇票
	public ModelAndView showBankAcceptancInfo(HttpServletRequest request,HttpServletResponse response){
		request.setAttribute("date", request.getParameter("date"));
		return new ModelAndView("sapss/capitalRequirement/bankAcceptanceInfo");
	}
	public void rtnBankAcceptancInfo(HttpServletRequest request,HttpServletResponse response) throws IOException{
		List rtn = this.fundsRequireQueryService.rtnBankAcceptancInfo(request.getParameter("date"));
		this.rtn(rtn, request, response);
	}
	
	//国内证
	public ModelAndView showHomeCreditInfo(HttpServletRequest request,HttpServletResponse response){
		request.setAttribute("date", request.getParameter("date"));
		return new ModelAndView("sapss/capitalRequirement/homeCreditInfo");
	}
	public void rtnHomeCreditInfo(HttpServletRequest request,HttpServletResponse response) throws IOException{
		List rtn = this.fundsRequireQueryService.rtnHomeCreditInfo(request.getParameter("date"));
		this.rtn(rtn, request, response);
	}
}
