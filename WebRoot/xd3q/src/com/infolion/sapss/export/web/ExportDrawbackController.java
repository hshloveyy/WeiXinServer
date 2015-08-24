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
import com.infolion.sapss.export.domain.TExportDrawback;
import com.infolion.sapss.export.service.ExportDrawbackService;

public class ExportDrawbackController extends BaseMultiActionController{
	@Autowired
	private ExportDrawbackService exportDrawbackService;
	public void setExportDrawbackService(ExportDrawbackService exportDrawbackService) {
		this.exportDrawbackService = exportDrawbackService;
	}

	/**
	 * 系统菜单列表链接
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView toExportDrawbackManger(HttpServletRequest request, HttpServletResponse response){
		String tradeType = request.getParameter("bizType");
		request.setAttribute("tradeType", tradeType);
		return new ModelAndView("sapss/export/drawback/exportDrawbackManager");
	}
	/**
	 * 弹出收汇详细页面
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView toExportDrawbackInfo(HttpServletRequest request, HttpServletResponse response){
		String tradeType = request.getParameter("tradeType");
		request.setAttribute("tradeType", tradeType);
		return new ModelAndView("sapss/export/drawback/exportDrawbackInfo");
	}
	/**
	 * 查询
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws ServletException 
	 */
	public void find(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String querySQL = this.exportDrawbackService.querySQL(request);
		String grid_column="EXPORT_DRAWBACK_ID,EXPORT_APPLY_ID,EXPORT_APPLY_NO,WRITE_DATE,DRAWBACK_DATE,DRAWBACK_RATE,DRAWBACK_VALUE,DRAWBACK_REAL,SHIPING_VALUE,RATE,IMPOSE_RATE,CREATOR,CREATOR_TIME,CREATOR_DEPT";
		String grid_size=request.getParameter("limit");
		grid_size = grid_size==null?"10":grid_size;
		request.setAttribute("grid_sql", querySQL);
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
	public void save(HttpServletRequest request, HttpServletResponse response,TExportDrawback info) throws IOException{
		UserContext context = UserContextHolder.getLocalUserContext().getUserContext();
		if(StringUtils.isEmpty(info.getExportDrawbackId())){
			info.setExportDrawbackId(CodeGenerator.getUUID());
		}
		info.setCreator(context.getSysUser().getUserId());
		info.setCreatorDept(context.getSysDept().getDeptcode());
		info.setCreatorTime(DateUtils.getCurrTime(2));
		info.setIsAvailable("1");
		info.setDeptId(this.exportDrawbackService.getExportDeptId(info.getExportApplyId()));
		this.exportDrawbackService.saveOrUpdate(info);
		JSONObject js = new JSONObject();
		js.put("ok", "保存成功");
		js.put("id", info.getExportDrawbackId());
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
		this.exportDrawbackService.delete(id);
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
		TExportDrawback info =  this.exportDrawbackService.find(id);
		request.setAttribute("tradeType",info.getTradeType());
		request.setAttribute("exportDrawbackInfo", info);
		return new ModelAndView("sapss/export/drawback/exportDrawbackInfo");
	}
	
	public void dealOutToExcel(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		String[] titles  = new String[]{"序号","出口货物通知单号","核销单号","合同号","预录入号","出口日期","报关单号","核销日期","成交币别","报关数量","报关金额","保税区业务","核销金额","海运费",
				                        "退税率","退税申报日","退税申报额","实际退税额","税款到帐日","增值税发票金额","换汇比","海关商编","出口口岸","运抵国","变更记录及附注"};

		ExcelObject excel = new ExcelObject(titles);
		
		String sql = this.exportDrawbackService.querySQL(request);

		exportDrawbackService.dealOutToExcel(sql ,excel);
		
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
