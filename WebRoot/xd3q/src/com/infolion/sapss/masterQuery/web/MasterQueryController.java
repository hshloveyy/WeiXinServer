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
import com.infolion.sapss.bapi.ExtractSapData;
import com.infolion.sapss.bapi.dto.MasterDataDto;
import com.infolion.sapss.masterQuery.service.MasterQueryJdbcService;

public class MasterQueryController extends BaseMultiActionController {
	
	@Autowired
	private MasterQueryJdbcService masterQueryJdbcService;
	
	/**
	 * @param masterQueryJdbcService the masterQueryJdbcService to set
	 */
	public void setMasterQueryJdbcService(
			MasterQueryJdbcService masterQueryJdbcService) {
		this.masterQueryJdbcService = masterQueryJdbcService;
	}

	/**
	 * 转到查询物料窗口
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView toFindMaterial(HttpServletRequest request,HttpServletResponse response) throws Exception {
		UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
		request.setAttribute("saleOrg", userContext.getSysDept().getXszzcode());
		request.setAttribute("factory", userContext.getSysDept().getXszzcode());
		request.setAttribute("materialOrg", request.getParameter("materialOrg"));
		request.setAttribute("isFunctionOrg", "1".equals(userContext.getSysDept().getIsFuncDept())?"false":"true");
		return new ModelAndView("sapss/queryForm/findMaterial");
	}
	
	/**
	 * 转到查询物料窗口
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView toFindMaterial_vat(HttpServletRequest request,HttpServletResponse response) throws Exception {
	    UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
	    request.setAttribute("saleOrg", userContext.getSysDept().getXszzcode());
	    request.setAttribute("factory", userContext.getSysDept().getXszzcode());
	    request.setAttribute("isFunctionOrg", "1".equals(userContext.getSysDept().getIsFuncDept())?"false":"true");
	    return new ModelAndView("sapss/queryForm/findMaterial_vat");
	}
	
	/**
	 * 查找物料页面grid数据源
	 * @param request
	 * @param response
	 * @throws Exception
	 * 
	 *    <br>I_MATNR 物料号 
	 *    <br>I_MAKTX 物料描述（短文本） 
	 *    <br>I_MTART 物料类型 
	 *    <br>I_WERKS 关于国家(集中批准)合同的工厂表 
	 *    <br>I_VKORG 销售组织 
	 *    <br>I_MATKL 出口/进口物料组 
	 *    <br>I_NEEDSUBDATA 是否需要分段数据（如需分页，值为1） 
	 *    <br>I_STARTROWNUM 起始行数 
	 *    <br>I_ENDROWNUM 结束行数
	 */
	public void rtnFindMaterial(HttpServletRequest request,	HttpServletResponse response) throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
		int start = Integer.valueOf(request.getParameter("start")).intValue();
		int size = Integer.valueOf(request.getParameter("limit")).intValue();
		map.put("I_NEEDSUBDATA", "1");
		map.put("I_STARTROWNUM", start + 1 + "");
		map.put("I_ENDROWNUM", start + size + "");
		String I_MATNR = request.getParameter("materialCd");
		String I_MAKTX = request.getParameter("materialName");
		Map req = extractFR(request);
		I_MAKTX =(String)req.get("materialName");
		String I_WERKS = request.getParameter("factory");
		String I_MTART = request.getParameter("materialType");
		String I_VKORG = request.getParameter("saleOrg");
		String I_MATKL = request.getParameter("materialOrg");
		if(!"1".equals(userContext.getSysDept().getIsFuncDept())){
			I_VKORG = userContext.getSysDept().getXszzcode();
			I_WERKS = I_VKORG;
		}	
		map.put("I_MATNR", I_MATNR == null || "".equals(I_MATNR.trim()) ? "" : I_MATNR);
		map.put("I_MAKTX", I_MAKTX == null || "".equals(I_MAKTX.trim()) ? "": I_MAKTX);
		map.put("I_WERKS", I_WERKS == null || "".equals(I_WERKS.trim()) ? "": I_WERKS);
		map.put("I_MTART", I_MTART == null || "".equals(I_MTART.trim()) ? "": I_MTART);
		map.put("I_VKORG", I_VKORG == null || "".equals(I_VKORG.trim()) ? "": I_VKORG);
		map.put("I_MATKL", I_MATKL == null || "".equals(I_MATKL.trim()) ? "": I_MATKL);
		MasterDataDto data = ExtractSapData.getMaterialMasterData(map);
		String total = data.getTotalNumber();
		JSONArray ja = JSONArray.fromObject(data.getData());
		JSONObject jo = new JSONObject();
		jo.put("totalProperty", total);
		jo.put("root", ja);
		String jsontext = jo.toString();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		response.getWriter().write(jsontext);

	}
	/**
	 * 转到供应商查找页面
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView toFindSupplier(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
		request.setAttribute("purchaseOrg", userContext.getSysDept().getCgzzcode());
		request.setAttribute("isFunctionOrg", "1".equals(userContext.getSysDept().getIsFuncDept())?"false":"true");
		String isrfc = request.getParameter("isrfc");
		if("true".equals(isrfc))
		return new ModelAndView("sapss/queryForm/findSupplier");
		else
			return new ModelAndView("sapss/queryForm/findSupplier1");	
	}

	/**
	 * 供应商查找页面grid数据源
	 * @param request
	 * @param response
	 * @throws Exception
	 *    <br>I_LIFNR 供应商或债权人的帐号
	 *    <br>I_NAME1 名称
	 *    <br>I_SORTL 排序字段
	 *    <br>I_EKORG 采购组织
	 *    <br>I_NEEDSUBDATA 是否需要分段数据（如需分页，值为1）
	 *    <br>I_STARTROWNUM 起始行数
	 *    <br>I_ENDROWNUM 结束行数
	 */
	public void rtnFindSupplier(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
		int start = Integer.valueOf(request.getParameter("start")).intValue();
		int size = Integer.valueOf(request.getParameter("limit")).intValue();
		map.put("I_NEEDSUBDATA", "1");
		map.put("I_STARTROWNUM", start + 1 + "");
		map.put("I_ENDROWNUM", start + size + "");

		Map req = extractFR(request);
		String name = (String)req.get("supplyName");		
		String lifnr = request.getParameter("supplyCd");
		String ekorg = request.getParameter("purchaseOrg");
		if(!"1".equals(userContext.getSysDept().getIsFuncDept()))
			ekorg = userContext.getSysDept().getCgzzcode();
		map.put("I_NAME1", name == null || "".equals(name.trim()) ? "" : name);
		map.put("I_LIFNR", lifnr == null || "".equals(lifnr.trim()) ? "": lifnr);
		map.put("I_EKORG", ekorg == null || "".equals(ekorg.trim()) ? "": ekorg);
		MasterDataDto data = ExtractSapData.getSupplierMasterData(map);
		String total = data.getTotalNumber();
		JSONArray ja = JSONArray.fromObject(data.getData());
		JSONObject jo = new JSONObject();
		jo.put("totalProperty", total);
		jo.put("root", ja);
		String jsontext = jo.toString();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		response.getWriter().write(jsontext);

	}

	/**
	 * 供应商查找页面grid数据源
	 * @param request
	 * @param response
	 * @throws Exception
	 *    <br>I_LIFNR 供应商或债权人的帐号
	 *    <br>I_NAME1 名称
	 *    <br>I_SORTL 排序字段
	 *    <br>I_EKORG 采购组织
	 *    <br>I_NEEDSUBDATA 是否需要分段数据（如需分页，值为1）
	 *    <br>I_STARTROWNUM 起始行数
	 *    <br>I_ENDROWNUM 结束行数
	 */
	public void findSupplier(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
		int start = Integer.valueOf(request.getParameter("start")).intValue();
		int size = Integer.valueOf(request.getParameter("limit")).intValue();
//		map.put("I_NEEDSUBDATA", "1");
//		map.put("I_STARTROWNUM", start + 1 + "");
//		map.put("I_ENDROWNUM", start + size + "");
		
		Map req = extractFR(request);
		String name = (String)req.get("supplyName");		
		String lifnr = request.getParameter("supplyCd");
		String ekorg = request.getParameter("purchaseOrg");
		if(!"1".equals(userContext.getSysDept().getIsFuncDept()))
			ekorg = userContext.getSysDept().getCgzzcode();
//		map.put("I_NAME1", name == null || "".equals(name.trim()) ? "" : name);
//		map.put("I_LIFNR", lifnr == null || "".equals(lifnr.trim()) ? "": lifnr);
//		map.put("I_EKORG", ekorg == null || "".equals(ekorg.trim()) ? "": ekorg);
//		MasterDataDto data = ExtractSapData.getSupplierMasterData(map);
		MasterDataDto data = masterQueryJdbcService.getSupplierMaster(lifnr, name, ekorg);
		String total = data.getTotalNumber();
		int endrownum =start + size;
		if(endrownum > Integer.parseInt(total)) endrownum=Integer.parseInt(total);
		JSONArray ja = JSONArray.fromObject(data.getData().subList( start , endrownum));
		JSONObject jo = new JSONObject();
		jo.put("totalProperty", total);
		jo.put("root", ja);
		String jsontext = jo.toString();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		response.getWriter().write(jsontext);

	}
	
	/**
	 * 转到查找客户页面
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView toFindCustomer(HttpServletRequest request,HttpServletResponse response) throws Exception {
		UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
		request.setAttribute("salerOrg", userContext.getSysDept().getXszzcode());
		request.setAttribute("isFunctionOrg", "1".equals(userContext.getSysDept().getIsFuncDept())?"false":"true");
		String isrfc = request.getParameter("isrfc");
		if("true".equals(isrfc))
		   return new ModelAndView("sapss/queryForm/findCustomer");
		else return new ModelAndView("sapss/queryForm/findCustomer1");
	}
	/**
	 * 客户查找页面grid数据源
	 * @param request
	 * @param response
	 * @throws Exception
	 *    <br>I_KUNNR       客户编号
	 *    <br>I_NAME        名称
	 *    <br>I_SORTL       排序字段
	 *    <br>I_VKORG       销售组织
	 *    <br>I_VTWEG       分销渠道
	 *    <br>I_SPART       产品组
	 *    <br>I_FLAG        是否需要分段数据（如需分页，值为1）
	 *    <br>I_FROM        起始行
	 *    <br>I_END         结束行
	 */
	public void rtnFindCustomer(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
		int start = Integer.valueOf(request.getParameter("start")).intValue();
		int size = Integer.valueOf(request.getParameter("limit")).intValue();
		map.put("I_FLAG", "1");
		map.put("I_FROM", start + 1 + "");
		map.put("I_END", start + size + "");
		Map req = extractFR(request);
		String I_KUNNR = request.getParameter("customerCd");
		String I_NAME =(String)req.get("customerName");		
		String I_VKORG = request.getParameter("salerOrg");
		String I_VTWEG = "99";
		String I_SPART = "99";
		if(!"1".equals(userContext.getSysDept().getIsFuncDept()))
			I_VKORG = userContext.getSysDept().getXszzcode();
		map.put("I_KUNNR", I_KUNNR == null || "".equals(I_KUNNR.trim()) ? "": I_KUNNR);
		map.put("I_NAME", I_NAME == null || "".equals(I_NAME.trim()) ? "": I_NAME);
		map.put("I_VKORG", I_VKORG == null || "".equals(I_VKORG.trim()) ? "": I_VKORG);
		map.put("I_VTWEG", I_VTWEG == null || "".equals(I_VTWEG.trim()) ? "": I_VTWEG);
		map.put("I_SPART", I_KUNNR == null || "".equals(I_SPART.trim()) ? "": I_SPART);
		MasterDataDto data = ExtractSapData.getCustomerMasterData(map);
		String total = data.getTotalNumber();
		JSONArray ja = JSONArray.fromObject(data.getData());
		JSONObject jo = new JSONObject();
		jo.put("totalProperty", total);
		jo.put("root", ja);
		String jsontext = jo.toString();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		response.getWriter().write(jsontext);

	}
	
	/**
	 * 客户查找页面grid数据源
	 * @param request
	 * @param response
	 * @throws Exception
	 *    <br>I_KUNNR       客户编号
	 *    <br>I_NAME        名称
	 *    <br>I_SORTL       排序字段
	 *    <br>I_VKORG       销售组织
	 *    <br>I_VTWEG       分销渠道
	 *    <br>I_SPART       产品组
	 *    <br>I_FLAG        是否需要分段数据（如需分页，值为1）
	 *    <br>I_FROM        起始行
	 *    <br>I_END         结束行
	 */
	public void findCustomer(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
		int start = Integer.valueOf(request.getParameter("start")).intValue();
		int size = Integer.valueOf(request.getParameter("limit")).intValue();
		map.put("I_FLAG", "1");
		map.put("I_FROM", start + 1 + "");
		map.put("I_END", start + size + "");
		Map req = extractFR(request);
		String I_KUNNR = request.getParameter("customerCd");
		String I_NAME =(String)req.get("customerName");		
		String I_VKORG = request.getParameter("salerOrg");
		String I_VTWEG = "99";
		String I_SPART = "99";
		if(!"1".equals(userContext.getSysDept().getIsFuncDept()))
			I_VKORG = userContext.getSysDept().getXszzcode();
		
//		map.put("I_KUNNR", I_KUNNR == null || "".equals(I_KUNNR.trim()) ? "": I_KUNNR);
//		map.put("I_NAME", I_NAME == null || "".equals(I_NAME.trim()) ? "": I_NAME);
//		map.put("I_VKORG", I_VKORG == null || "".equals(I_VKORG.trim()) ? "": I_VKORG);
//		map.put("I_VTWEG", I_VTWEG == null || "".equals(I_VTWEG.trim()) ? "": I_VTWEG);
//		map.put("I_SPART", I_KUNNR == null || "".equals(I_SPART.trim()) ? "": I_SPART);
//		MasterDataDto data = ExtractSapData.getCustomerMasterData(map);
		MasterDataDto data = masterQueryJdbcService.getCustomerMaster(I_KUNNR, I_NAME, I_VKORG);
		String total = data.getTotalNumber();
		int endrownum =start + size;
		if(endrownum > Integer.parseInt(total)) endrownum=Integer.parseInt(total);
		JSONArray ja = JSONArray.fromObject(data.getData().subList( start , endrownum));
		JSONObject jo = new JSONObject();
		jo.put("totalProperty", total);
		jo.put("root", ja);
		String jsontext = jo.toString();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		response.getWriter().write(jsontext);

	}
	/**
	 * 查找批次号
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView toFindBatchNo(HttpServletRequest request,HttpServletResponse response) throws Exception {
		String factory = request.getParameter("factory");
		String materialNo = request.getParameter("materialNo");
		String batchNO = request.getParameter("batchNO");
		request.setAttribute("factory", factory);
		request.setAttribute("materialNo", materialNo);
		request.setAttribute("batchNO", batchNO);
		return new ModelAndView("sapss/queryForm/findBatchNo");
	}
	/**
	 * 批次查找页面grid数据源
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void rtnFindBatchNo(HttpServletRequest request,HttpServletResponse response) throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		String werks = request.getParameter("factory");
		String matnr = request.getParameter("materialNo");
		String charg = request.getParameter("batchNo");
		map.put("WERKS", werks == null || "".equals(werks.trim()) ? "": werks);
		map.put("MATNR", matnr == null || "".equals(matnr.trim()) ? "": matnr);
		map.put("CHARG", charg == null || "".equals(charg.trim()) ? "": charg);
		List<String> list = ExtractSapData.getBatchNo(map);
		Map<String,String> listToMap;
		List<Map> rtnList = new ArrayList<Map>();
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			listToMap =  new HashMap<String,String>();
			String batch = (String) iterator.next();
			listToMap.put("BATCH", batch);
			rtnList.add(listToMap);
		}
		
		int total = list.size();
		JSONArray ja = JSONArray.fromObject(rtnList);
		JSONObject jo = new JSONObject();
		jo.put("totalProperty", total);
		jo.put("root", ja);
		String jsontext = jo.toString();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		response.getWriter().write(jsontext);
	}
	/**
	 * 查找销售或采购组
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView toFindGroup(HttpServletRequest request,HttpServletResponse response) throws Exception {
		String dept = request.getParameter("dept");
		request.setAttribute("dept", dept);
		return new ModelAndView("sapss/queryForm/findGroup");
	}
	/**
	 * 查找付款方式
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView toFindPayTerm(HttpServletRequest request,HttpServletResponse response) throws Exception {
		String dept = request.getParameter("dept");
		request.setAttribute("dept", dept);
		return new ModelAndView("sapss/queryForm/findPayTerm");
	}
	/**
	 * 组查找页面grid数据源
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void rtnFindPayTerm(HttpServletRequest request,HttpServletResponse response) throws Exception {
		ExtComponentServlet servlet = new ExtComponentServlet();
		String grid_sql = "select ID,TITLE,CMD from bm_pay_type";
		String grid_columns = "ID,TITLE,CMD";
		String grid_size = "20";
		request.setAttribute("grid_sql", grid_sql);
		request.setAttribute("grid_columns", grid_columns);
		request.setAttribute("grid_size", grid_size);
		servlet.processGrid(request, response, true);
	}
	/**
	 * 组查找页面grid数据源
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void rtnFindContry(HttpServletRequest request,HttpServletResponse response) throws Exception {
		ExtComponentServlet servlet = new ExtComponentServlet();
		String grid_sql = "select ID,TITLE,CMD from bm_Country order by TITLE";
		String grid_columns = "ID,TITLE,CMD";
		String grid_size = "1000";
		request.setAttribute("grid_sql", grid_sql);
		request.setAttribute("grid_columns", grid_columns);
		request.setAttribute("grid_size", grid_size);
		servlet.processGrid(request, response, true);
	}
	/**
	 * 组查找页面grid数据源
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void rtnFindArea(HttpServletRequest request,HttpServletResponse response) throws Exception {
		ExtComponentServlet servlet = new ExtComponentServlet();
		String country = request.getParameter("id");
		String grid_sql;
		if(country != null)
		{
		grid_sql = "select ID,TITLE,CMD from bm_Area where COUNTRYID = '" + country + "' order by TITLE";
		}
		else
		{
			grid_sql = "select ID,TITLE,CMD from bm_Area";
		}
		String grid_columns = "ID,TITLE,CMD";
		String grid_size = "100";
		request.setAttribute("grid_sql", grid_sql);
		request.setAttribute("grid_columns", grid_columns);
		request.setAttribute("grid_size", grid_size);
		servlet.processGrid(request, response, true);
	}
	/**
	 * 组查找页面grid数据源
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void rtnFindGroup(HttpServletRequest request,HttpServletResponse response) throws Exception {
		String dept = request.getParameter("dept");
		Map map = new HashMap();
		map.put("I_VKBUR", dept);
		List list = ExtractSapData.getSaleGroupList(map);
		JSONArray ja = JSONArray.fromObject(list);
		JSONObject jo = new JSONObject();
		jo.put("totalProperty", list.size());
		jo.put("root", ja);
		String jsontext = jo.toString();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		response.getWriter().write(jsontext);
	}
	
	
	
	
	/**
	 * 萃取url
	 * @param req
	 * @return 参数名,参数值的map
	 */
	private Map<String,String> extractFR(HttpServletRequest req) {
		try {
			String wait = java.net.URLDecoder.decode(req.getQueryString(),"UTF-8");
			String ar1[] = wait.split("&");
			Map<String,String> map = new HashMap<String,String>();
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
	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView toSeniorManagerExamin(HttpServletRequest request,HttpServletResponse response){
		request.setAttribute("person",request.getParameter("person"));
		return new ModelAndView("sapss/workflow/particular/seniorManagerExamine");
	}
	/**
	 * 
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws ServletException 
	 */
	public void findSaleContract(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
		StringBuffer sb = new StringBuffer();
		String person = request.getParameter("person");
		sb.append("select distinct s.contract_no,contract_sales_id from t_contract_sales_info s " +
				"left outer join bm_currency c on s.vbak_waerk=c.id left outer join " +
				"(SELECT * fROM t_sys_wf_task_history union SELECT * FROM t_sys_wf_task_history_O) t on s.contract_sales_id=t.business_record_id where ");
		if(StringUtils.isNotBlank(person)&& "1".equals(person))
			sb.append("((s.vbak_waerk!='USD' AND s.order_total*C.RATE>7000000) or (s.vbak_waerk='USD' AND S.ORDER_TOTAL>1000000))");
		else 
			sb.append("((s.vbak_waerk!='USD' AND s.order_total*C.RATE>14000000) or (s.vbak_waerk='USD' AND S.ORDER_TOTAL>2000000))");
		sb.append("and s.is_available=1 and s.order_state='3' and s.vbak_auart_name is null and s.contract_sales_id not " +
				"in(select h.business_record_id from (SELECT * fROM t_sys_wf_task_history union SELECT * FROM t_sys_wf_task_history_O) h where " );
		if(StringUtils.isNotBlank(person)&& "1".equals(person))
			sb.append("h.task_name='股份公司总经理审批')");
		else
			sb.append("h.task_name='股份公司董事长审批')");
		ExtComponentServlet servlet = new ExtComponentServlet();
		String size = request.getParameter("limit");
		request.setAttribute("grid_sql", sb.toString());
		request.setAttribute("grid_columns", "contract_no,contract_sales_id");
		request.setAttribute("grid_size", size==null||"".equals(size)?"10":size);
		servlet.processGrid(request, response, true);
	}
	
	
	public ModelAndView toSeniorProjectExamin(HttpServletRequest request,HttpServletResponse response){
		request.setAttribute("person",request.getParameter("person"));
		return new ModelAndView("sapss/workflow/particular/seniorProjectExamine");
	}
	/**
	 * 
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws ServletException 
	 */
	public void findProjectExam(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
		StringBuffer sb = new StringBuffer();
		String person = request.getParameter("person");
		sb.append("select distinct s.project_no,project_id from t_project_info s " +
				"left outer join bm_currency c on s.currency=c.id  left outer join " +
				"(SELECT * fROM t_sys_wf_task_history union SELECT * FROM t_sys_wf_task_history_O) t on s.project_id=t.business_record_id " +
				"where ");
		if(StringUtils.isNotBlank(person)&& "1".equals(person))
			sb.append("((s.currency!='USD' AND s.sum*C.RATE>700) or (s.currency='USD' AND S.sum>100))");
		else 
			sb.append("((s.currency!='USD' AND s.sum*C.RATE>1400) or (s.currency='USD' AND S.sum>200))");
		sb.append("and s.is_available=1 and s.project_state='3' and s.exchange_rate=0 and s.project_id not " +
				"in(select h.business_record_id from (SELECT * fROM t_sys_wf_task_history union SELECT * FROM t_sys_wf_task_history_O) h where " );
		if(StringUtils.isNotBlank(person)&& "1".equals(person))
			sb.append("h.task_name='股份公司总经理审批')");
		else
			sb.append("h.task_name like '%股份公司董事长审批%')");
		ExtComponentServlet servlet = new ExtComponentServlet();
		String size = request.getParameter("limit");
		request.setAttribute("grid_sql", sb.toString());
		request.setAttribute("grid_columns", "project_no,project_id");
		request.setAttribute("grid_size", size==null||"".equals(size)?"10":size);
		servlet.processGrid(request, response, true);
	}
}
