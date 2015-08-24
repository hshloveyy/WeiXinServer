/*
 * @(#)ExportIncomeController.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：陈喜平
 *  时　间：2009-5-25
 *  描　述：创建
 */

package com.infolion.sapss.export.web;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

import com.infolion.platform.component.ExtComponentServlet;
import com.infolion.platform.console.sys.context.UserContext;
import com.infolion.platform.console.sys.context.UserContextHolder;
import com.infolion.platform.core.web.BaseMultiActionController;
import com.infolion.platform.util.CodeGenerator;
import com.infolion.platform.util.DateUtils;
import com.infolion.sapss.common.ExcelObject;
import com.infolion.sapss.export.dao.ExportApplyIncomeHibernateDao;
import com.infolion.sapss.export.domain.TExportApplyIncome;
import com.infolion.sapss.export.domain.TExportIncomeInfo;
import com.infolion.sapss.export.service.ExportApplyIncomeService;
import com.infolion.sapss.export.service.ExportIncomeService;

public class ExportIncomeController extends BaseMultiActionController{
	@Autowired
	private ExportIncomeService exportIncomeService;

	public void setExportIncomeService(ExportIncomeService exportIncomeService) {
		this.exportIncomeService = exportIncomeService;
	}
	@Autowired
	private ExportApplyIncomeService exportApplyIncomeService;
	public void setExportApplyIncomeService(
			ExportApplyIncomeService exportApplyIncomeService) {
		this.exportApplyIncomeService = exportApplyIncomeService;
	}
	
	
	/**
	 * 系统菜单列表链接
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView toExportIncomeManger(HttpServletRequest request, HttpServletResponse response){
		return new ModelAndView("sapss/export/exportIncome/exportIncomeManager");
	}
	/**
	 * 弹出收汇详细页面
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView toExportIncomeInfo(HttpServletRequest request, HttpServletResponse response){
		return new ModelAndView("sapss/export/exportIncome/exportIncomeInfo");
	}
	/**
	 * 查询
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws ServletException 
	 */
	public void find(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{

		String grid_sql = this.exportIncomeService.querySQL(request);
		String grid_column="EXPORT_INCOME_INFO_ID,PROJECT_ID,PROJECT_NO,CONTRACT_SALES_ID,CONTRACT_NO,INV_NO,ACCEPT_DATE";
		String grid_size=request.getParameter("limit");
		grid_size = grid_size==null?"10":grid_size;
		request.setAttribute("grid_sql", grid_sql);
		request.setAttribute("grid_columns", grid_column);
		request.setAttribute("grid_size", grid_size);
		ExtComponentServlet servlet = new ExtComponentServlet();
		servlet.processGrid(request, response, true);
	}
	/**
	 * 保存
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	public void save(HttpServletRequest request, HttpServletResponse response,TExportIncomeInfo info) throws IOException{
		UserContext context = UserContextHolder.getLocalUserContext().getUserContext();
		if(StringUtils.isEmpty(info.getExportIncomeInfoId())){
			info.setExportIncomeInfoId(CodeGenerator.getUUID());
		}
		info.setCreator(context.getSysUser().getRealName());
		info.setCreatorDept(context.getSysDept().getDeptcode());
		info.setCreatorTime(DateUtils.getCurrTime(2));
		info.setIsAvailable("1");
		this.exportIncomeService.saveOrUpdate(info);
		String exportApplyId = request.getParameter("exportApplyId");
		String noticeNo = request.getParameter("exportApplyNo");
		String id = request.getParameter("id");
		/*if(StringUtils.isNotEmpty(exportApplyId)){
			TExportApplyIncome ai = new TExportApplyIncome();
			ai.setId(id);
			if(StringUtils.isEmpty(id))
				ai.setId(CodeGenerator.getUUID());
			ai.setExportApplyId(exportApplyId);
			ai.setExportIncomeInfoId(info.getExportIncomeInfoId());
			ai.setCreator(context.getSysUser().getRealName());
			ai.setCreatorTime(DateUtils.getCurrTime(2));
			ai.setExportApplyNo(noticeNo);
			this.exportApplyIncomeService.saveOrUpdate(ai);
		}*/
		JSONObject js = new JSONObject();
		js.put("ok", "保存成功");
		this.operateSuccessfullyWithString(response, js.toString());
	}
	/**
	 * 删除
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	public void delete(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String id = request.getParameter("id");
		this.exportIncomeService.delete(id);
		JSONObject js = new JSONObject();
		js.put("ok", "删除成功");
		this.operateSuccessfullyWithString(response, js.toString());
	}
	/**
	 * 修改
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView forModify(HttpServletRequest request, HttpServletResponse response){
		String id = request.getParameter("id");
		TExportIncomeInfo info =  this.exportIncomeService.find(id);
		request.setAttribute("exportIncomeInfo", info);
		//TExportApplyIncome ai = this.exportApplyIncomeService.find(info.getExportIncomeInfoId());
		//request.setAttribute("exportApplyNo",ai.getExportApplyNo());
		//request.setAttribute("exportApplyId",ai.getExportApplyId());
		//request.setAttribute("id", ai.getId());
		return new ModelAndView("sapss/export/exportIncome/exportIncomeInfo");
	}
	/**
	 * 查询出口货物通知单信息
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView selectExportApplyInfo(HttpServletRequest request, HttpServletResponse response){
		
		return new ModelAndView("sapss/export/exportIncome/selectExportApplyInfo");
	}
	/**
	 * 查询销售合同信息
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView selectSalesInfo(HttpServletRequest request, HttpServletResponse response){
		
		return new ModelAndView("sapss/export/exportIncome/selectSalesInfo");
	}
	/**
	 * 返回查询出口通知单列表
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws ServletException 
	 */
	public void queryExportApply(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		String querySQL = this.exportIncomeService.queryExportApplySQL(request);
		String grid_column="EXPORT_APPLY_ID,TRADE_TYPE,PROJECT_NO,PROJECT_NAME,CONTRACT_GROUP_NO,SALES_NO,PURCHASE_NO,SAP_ORDER_NO,NOTICE_NO,WRITE_NO,TOTAL_QUANTITY,TOTAL_MONEY,EXPORT_PORT,DESTINATIONS,CONTRACT_PAPER_NO";
		String grid_size=request.getParameter("limit");
		grid_size = grid_size==null?"10":grid_size;
		request.setAttribute("grid_sql", querySQL);
		request.setAttribute("grid_columns", grid_column);
		request.setAttribute("grid_size", grid_size);
		ExtComponentServlet servlet = new ExtComponentServlet();
		servlet.processGrid(request, response, true);
	}
	/**
	 * 查询销售合同信息
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	public void querySalesInfo(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException
	{
		String sql = exportIncomeService.querySalesSql(request);
		String grid_columns = "ORDER_STATE_D_ORDER_STATE,OLD_CONTRACT_NO,PROJECT_NO,PROJECT_ID,PROJECT_NAME,CONTRACT_GROUP_NO,CONTRACT_GROUP_NAME,CONTRACT_SALES_ID,CONTRACT_NO,CONTRACT_NAME,SAP_ORDER_NO,VBKD_INCO1,VBKD_IHREZ,VBKD_INCO2";
		String grid_size=request.getParameter("limit");
		grid_size = grid_size==null?"10":grid_size;
		request.setAttribute("grid_sql", sql);
		request.setAttribute("grid_columns", grid_columns);
		request.setAttribute("grid_size", grid_size);

		ExtComponentServlet extComponentServlet = new ExtComponentServlet();
		extComponentServlet.processGrid(request, response, true);
	}
	
	public void dealOutToExcel(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		String[] titles  = new String[]{"序号","项目号","合同号","发票号","出口货物通知单号","核销单号","实际收汇金额","实际收汇日","押汇利率","押汇收款金额","押汇收款日","收汇币别","收汇银行","折人民币","用途"};

		ExcelObject excel = new ExcelObject(titles);
		
		String sql = this.exportIncomeService.querySQL(request);

		exportIncomeService.dealOutToExcel(sql ,excel);
		
		try{
			//response.reset();
	        response.setCharacterEncoding("utf-8");
	        response.setContentType("application/x-download;charset=utf-8");
	        response.setHeader("Content-Disposition", (new StringBuilder("attachment;filename=")).append(URLEncoder.encode("出口贸易明细表.xls", "utf-8")).toString());
			excel.write(response.getOutputStream());
			response.flushBuffer();
			response.getOutputStream().close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
