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

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.ModelAndView;

import com.infolion.platform.component.ExtComponentServlet;
import com.infolion.platform.console.sys.context.UserContext;
import com.infolion.platform.console.sys.context.UserContextHolder;
import com.infolion.platform.core.web.BaseMultiActionController;
import com.infolion.sapss.bapi.ExtractSapData;
import com.infolion.sapss.bapi.dto.MasterDataDto;

public class CreditQueryController extends BaseMultiActionController {

	public void rtnFindCredit(HttpServletRequest request,	HttpServletResponse response) throws Exception {
		String creditNo = request.getParameter("creditNo");
		String createBank = request.getParameter("createBank");
		String invoice = request.getParameter("invoice");
		String contract_no = request.getParameter("contract_no");
		String tradeType = request.getParameter("tradeType");
		String sql = "select * from t_credit_Info t where t.is_available = '1' and t.create_or_rec = '2'";
		if (StringUtils.isNotEmpty(creditNo))
		{
			sql = sql + " and t.credit_no like '%" + creditNo + "%'";
		}
		if (StringUtils.isNotEmpty(createBank))
		{
			sql = sql + " and t.create_bank like '%" + createBank + "%'";
		}
		if (StringUtils.isNotEmpty(invoice))
		{
			sql = sql + " and t.invoice like '%" + invoice + "%'";
		}
		if (StringUtils.isNotEmpty(contract_no))
		{
			sql = sql + " and t.contract_no like '%" + contract_no + "%'";
		}
		if (StringUtils.isNotEmpty(tradeType))
		{
			sql = sql + " and t.trade_type = '" + tradeType + "'";
		}
		
		// 部门的过滤
		UserContext userContext = UserContextHolder.getLocalUserContext()
				.getUserContext();
		sql += " and t.dept_id in (" + userContext.getGrantedDepartmentsId()
				+ ",";
		sql += "'" + userContext.getSysDept().getDeptid() + "')";
		
		String grid_column="CREDIT_ID,CREDIT_NO,CREDIT_REC_DATE,CREATE_BANK,TRADE_TYPE,INVOICE,CONTRACT_NO";
		String grid_size=request.getParameter("limit");
		grid_size = grid_size==null?"10":grid_size;
		request.setAttribute("grid_sql", sql);
		request.setAttribute("grid_columns", grid_column);
		request.setAttribute("grid_size", grid_size);
		ExtComponentServlet servlet = new ExtComponentServlet();
		servlet.processGrid(request, response, true);

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
		return new ModelAndView("sapss/queryForm/findSupplier");
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
		return new ModelAndView("sapss/queryForm/findCustomer");
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
	
}
