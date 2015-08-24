/*
 * @(#)ExportIncomeController.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：陈喜平
 *  时　间：2009-5-25
 *  描　述：创建
 */

package com.infolion.sapss.receiptLog.web;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.HashMap;
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
import com.infolion.sapss.receiptLog.domain.TReceiptGoodsInfo;
import com.infolion.sapss.receiptLog.service.ReceiptGoodsService;

public class ReceiptGoodsController extends BaseMultiActionController{
	@Autowired
	private ReceiptGoodsService receiptGoodsService;
	public void setExportDrawbackService(ReceiptGoodsService receiptGoodsService) {
		this.receiptGoodsService = receiptGoodsService;
	}

	/**
	 * 系统菜单列表链接
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView toManger(HttpServletRequest request, HttpServletResponse response){
		String tradeType = request.getParameter("tradeType");
		request.setAttribute("tradeType", tradeType);
		return new ModelAndView("sapss/receiptLog/receiptGoods/goodsManager");
	}
	/**
	 * 弹出收汇详细页面
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView toInfo(HttpServletRequest request, HttpServletResponse response){
		String tradeType = request.getParameter("tradeType");
		request.setAttribute("tradeType", tradeType);
		return new ModelAndView("sapss/receiptLog/receiptGoods/goodsInfo");
	}
	/**
	 * 查询
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws ServletException 
	 */
	public void find(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String querySQL = this.receiptGoodsService.querySQL(extractFR(request));
		String grid_column="info_id,receipt_id,receipt_no,import_date,custome_no,pre_wr_cd,CUSTOME_PRICE,CUSTOME_CASH," +
				"CUSTOME_PORT,IMPORT_COUNTRY,CUSTOME_TOTAL,CREATOR,CREATOR_TIME";
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
	public void save(HttpServletRequest request, HttpServletResponse response,TReceiptGoodsInfo info) throws IOException{
		UserContext context = UserContextHolder.getLocalUserContext().getUserContext();
		if(StringUtils.isEmpty(info.getInfoId())){
			info.setInfoId(CodeGenerator.getUUID());
		}
		info.setCreator(context.getSysUser().getRealName());
		info.setDeptId(context.getSysDept().getDeptid());
		info.setCreatorTime(DateUtils.getCurrTime(2));
		info.setIsAvailable("1");
		this.receiptGoodsService.saveOrUpdate(info);
		JSONObject js = new JSONObject();
		js.put("infoId", info.getInfoId());
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
		String id = request.getParameter("infoId");
		this.receiptGoodsService.delete(id);
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
		String id = request.getParameter("infoId");
		TReceiptGoodsInfo info =  this.receiptGoodsService.find(id);
		request.setAttribute("receiptGoods", info);
		return new ModelAndView("sapss/receiptLog/receiptGoods/goodsInfo");
	}
	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView toFindReceiptInfo(HttpServletRequest request, HttpServletResponse response){
		UserContext context = UserContextHolder.getLocalUserContext().getUserContext();
		request.setAttribute("deptid", context.getSysDept().getDeptid());
		return new ModelAndView("sapss/queryForm/findReceiptInfo");
	}
	/**
	 * 
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws ServletException 
	 */
	public void queryReceiptInfo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String deptid = request.getParameter("deptid");
		String receiptNo = request.getParameter("receiptNo");
		String projectNo = request.getParameter("projectNo");
		String projectName = request.getParameter("projectName");
		String contractNo = request.getParameter("contractNo");
		String contractName = request.getParameter("contractName");
		String sql = "select t.*,c.contract_name from t_receipt_info t left join t_contract_purchase_info c on t.contract_purchase_id=c.contract_purchase_id  where 1=1";
		if(StringUtils.isNotBlank(deptid))
			sql =sql+ " and t.dept_id='"+deptid+"'";
		if(StringUtils.isNotBlank(receiptNo))
			sql =sql+ " and t.receipt_no like '%"+receiptNo+"%'";
		if(StringUtils.isNotBlank(projectNo))
			sql =sql+ " and t.project_no like '%"+projectNo+"%'";
		if(StringUtils.isNotBlank(projectName))
			sql =sql+ " and t.project_name like '%"+projectName+"%'";
		if(StringUtils.isNotBlank(contractNo))
			sql =sql+ " and t.contract_no like '%"+contractNo+"%'";
		if(StringUtils.isNotBlank(contractName))
			sql =sql+ " and c.contract_name like '%"+contractName+"%'";
		
		String grid_column = "receipt_id,receipt_no,project_no,project_name,contract_no,contract_purchase_id,contract_name";
		String grid_size=request.getParameter("limit");
		grid_size = grid_size==null?"10":grid_size;
		request.setAttribute("grid_sql", sql);
		request.setAttribute("grid_columns", grid_column);
		request.setAttribute("grid_size", grid_size);
		
		ExtComponentServlet servlet = new ExtComponentServlet();
		servlet.processGrid(request, response, true);

	}
	
	
	public void dealOutToExcel(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		String[] titles  = new String[]{"序号","到单号","核销单号","进口日期","报关单号","预录入号","报关价格条件","报关金额","报关口岸","进口国别","关单总计","海关商编","成交币别","报关数量","单价","报关单位","成交方式","核销金额","核销日期","退汇金额","退汇日期","核销进度","备注"};

		ExcelObject excel = new ExcelObject(titles);
		Map<String, String> filter = extractFR(request);
		String sql = receiptGoodsService.querySQL(filter);

		receiptGoodsService.dealOutToExcel(sql ,excel);
		
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
