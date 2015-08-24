/*
 * @(#)SapReportController.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：陈喜平
 *  时　间：2009-8-4
 *  描　述：创建
 */

package com.infolion.sapss.masterQuery.web;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.web.servlet.ModelAndView;

import com.infolion.platform.core.web.BaseMultiActionController;
import com.infolion.sapss.bapi.BapiConstant;
import com.infolion.sapss.bapi.ExtractSapData;
import com.infolion.sapss.bapi.dto.MasterDataDto;

public class SapReportController extends BaseMultiActionController{
	
	//SAP报表--资产负债表--函数名称
	private final String REPORT_ZICHANFUZHAI_FUNC="ZF_ZFIRPT002";
	//SAP报表--资产负债表--内表名称
	private final String REPORT_ZICHANFUZHAI_TABLE="IT_PRINT01";
	//SAP报表--资产负债表--错误消息表名称
	private final String REPORT_ZICHANFUZHAI_MSG="IT_MSG";

	//SAP报表--现金流量表--函数名称
	private final String REPORT_XIANJINLIULIANG_FUNC="ZF_ZFIRPT006";
	//SAP报表--现金流量表--内表名称
	private final String REPORT_XIANJINLIULIANG_TABLE="IT_RESULT_B";
	//SAP报表--现金流量表--错误消息表名称
	private final String REPORT_XIANJINLIULIANG_MSG="IT_BAPIRET2";

	//SAP报表--利润及利润分配表--函数名称
	private final String REPORT_LIRUNJILIRUNFENPEI_FUNC="ZF_ZFIRPT026B";
	//SAP报表--利润及利润分配表--内表名称
	private final String REPORT_LIRUNJILIRUNFENPEI_TABLE="IT_OUTPUT2";
	//SAP报表--利润及利润分配表--错误消息表名称
	private final String REPORT_LIRUNJILIRUNFENPEI_MSG="IT_BAPIRET2";

	//公司代码--函数名称
	private final String REPORT_GONGSIDAIMA_FUNC="ZF_GETBUKRS";
	//公司代码--内表名称
	private final String REPORT_GONGSIDAIMA_TABLE="OT_BUKRS";
	

	/**
	 * 资产负债表内表
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView toZichanfuzhai(HttpServletRequest request,HttpServletResponse response) throws Exception {
		request.setAttribute("kuaijiniandu", request.getParameter("kuaijiniandu"));
		request.setAttribute("kuaijiqijian", request.getParameter("kuaijiqijian"));
		request.setAttribute("gongsidaima", request.getParameter("gongsidaima"));
		return new ModelAndView("sapss/sapReport/zichanfuzhai");
	}
	/**
	 * 查找资产负债信息
	 * @param request
	 * @param response
	 * @throws Exception
	 * 
	 */
	public void rtnZichanfuzhai(HttpServletRequest request,	HttpServletResponse response) throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("I_RLDNR", "0L");
		String kuaijiniandu = request.getParameter("kuaijiniandu");
		map.put("I_GJAHR",kuaijiniandu==null||"".equals(kuaijiniandu)?Calendar.getInstance().get(Calendar.YEAR):kuaijiniandu);//会计年度
		String gongsidaima = request.getParameter("gongsidaima");
		map.put("I_BUKRS",gongsidaima==null||"".equals(gongsidaima)?"2100":gongsidaima);//公司代码
		String kuaijiqijian = request.getParameter("kuaijiqijian");
		map.put("I_MONAT",kuaijiqijian==null||"".equals(kuaijiqijian)?Calendar.getInstance().get(Calendar.MONTH):kuaijiqijian);//会计期间
		this.rtnData(request, response, this.REPORT_ZICHANFUZHAI_FUNC, this.REPORT_ZICHANFUZHAI_TABLE, map, this.REPORT_ZICHANFUZHAI_MSG);
		
	}
	/**
	 * 现金流量表 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView toXianjinliuliang(HttpServletRequest request,HttpServletResponse response) throws Exception {
		request.setAttribute("kuaijiniandu", request.getParameter("kuaijiniandu"));
		request.setAttribute("kuaijiqijian", request.getParameter("kuaijiqijian"));
		request.setAttribute("gongsidaima", request.getParameter("gongsidaima"));
		return new ModelAndView("sapss/sapReport/xianjinliuliang");
	}
	/**
	 * 查找现金流量信息
	 * @param request
	 * @param response
	 * @throws Exception
	 * 
	 */
	public void rtnXianjinliuliang(HttpServletRequest request,	HttpServletResponse response) throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("I_RLDNR", "0L");
		String kuaijiniandu = request.getParameter("kuaijiniandu");
		map.put("I_GJAHR",kuaijiniandu==null||"".equals(kuaijiniandu)?Calendar.getInstance().get(Calendar.YEAR):kuaijiniandu);//会计年度
		String gongsidaima = request.getParameter("gongsidaima");
		map.put("I_BUKRS",gongsidaima==null||"".equals(gongsidaima)?"2100":gongsidaima);//公司代码
		String kuaijiqijian = request.getParameter("kuaijiqijian");
		map.put("I_MONAT",kuaijiqijian==null||"".equals(kuaijiqijian)?Calendar.getInstance().get(Calendar.MONTH):kuaijiqijian);//会计期间
		
		this.rtnData(request, response, this.REPORT_XIANJINLIULIANG_FUNC, this.REPORT_XIANJINLIULIANG_TABLE, map, this.REPORT_XIANJINLIULIANG_MSG);
	}
	/**
	 * 利润及利润分配表
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView toLirunjilirunfenpei(HttpServletRequest request,HttpServletResponse response) throws Exception {
		request.setAttribute("kuaijiniandu", request.getParameter("kuaijiniandu"));
		request.setAttribute("kuaijiqijian", request.getParameter("kuaijiqijian"));
		request.setAttribute("gongsidaima", request.getParameter("gongsidaima"));
		return new ModelAndView("sapss/sapReport/lirunjilirunfenpei");
	}
	/**
	 * 查找利润及利润分配信息
	 * @param request
	 * @param response
	 * @throws Exception
	 * 
	 */
	public void rtnLirunjilirunfenpei(HttpServletRequest request,	HttpServletResponse response) throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("I_RLDNR", "0L");
		String kuaijiniandu = request.getParameter("kuaijiniandu");
		map.put("I_GJAHR",kuaijiniandu==null||"".equals(kuaijiniandu)?Calendar.getInstance().get(Calendar.YEAR):kuaijiniandu);//会计年度
		String gongsidaima = request.getParameter("gongsidaima");
		map.put("I_BUKRS",gongsidaima==null||"".equals(gongsidaima)?"2100":gongsidaima);//公司代码
		String kuaijiqijian = request.getParameter("kuaijiqijian");
		map.put("I_MONAT",kuaijiqijian==null||"".equals(kuaijiqijian)?Calendar.getInstance().get(Calendar.MONTH):kuaijiqijian);//会计期间
		
		this.rtnData(request, response, this.REPORT_LIRUNJILIRUNFENPEI_FUNC, this.REPORT_LIRUNJILIRUNFENPEI_TABLE, map, this.REPORT_LIRUNJILIRUNFENPEI_MSG);
	}
	/**
	 * 利润及利润分配表
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView toGongsidaima(HttpServletRequest request,HttpServletResponse response) throws Exception {
		return new ModelAndView("sapss/sapReport/gongsidaima");
	}
	
	public ModelAndView sapReport(HttpServletRequest request,HttpServletResponse response) throws Exception {
		String comCode = request.getParameter("comCode");
		request.setAttribute("comCode", comCode);
		return new ModelAndView("sapss/sapReport/sapReport1");
	}
	
	public void findSapReport(HttpServletRequest request,	HttpServletResponse response) throws Exception {
		String reportType = request.getParameter("reportType");
		if(reportType.equals("1")){
			rtnZichanfuzhai(request, response);
		}
		else if(reportType.equals("2")){
			rtnLirunjilirunfenpei(request, response);
		}
		else if(reportType.equals("3")){
			rtnXianjinliuliang(request, response);
		}

	}
	/**
	 * 查找利润及利润分配信息
	 * @param request
	 * @param response
	 * @throws Exception
	 * 
	 */
	public void rtnGongsidaima(HttpServletRequest request,	HttpServletResponse response) throws Exception {
		Map map = new HashMap();
		this.rtnData(request, response, this.REPORT_GONGSIDAIMA_FUNC, this.REPORT_GONGSIDAIMA_TABLE, map, null);
	}	
	/**
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private void rtnData(HttpServletRequest request,HttpServletResponse response,
			String func,String mainT,Map parm,String msgT) throws Exception {
		String start = request.getParameter("start");
		String size = request.getParameter("limit");
		int b = start!=null&&!"".equals(start)?Integer.valueOf(start).intValue():0;
		int e = size!=null&&!"".equals(size)?Integer.valueOf(size).intValue():10;
		String noPage = request.getParameter("noPage");
		
		MasterDataDto data = ExtractSapData.getMasterData_(func,mainT, parm, msgT);
		String total = data.getTotalNumber();
		JSONObject jo = new JSONObject();
		JSONArray ja = null;
		String jsontext = "";
		List list = data.getData();
		if(list!=null &&list.size()>0){
			if(!"1".equals(noPage)){
				if((b+e)>Integer.parseInt(total==null?"0":total))
					list = list.subList(b,Integer.parseInt(total));
				else
					list = list.subList(b, b+e);
			}
			ja = JSONArray.fromObject(list);
			jo.put("totalProperty", total);
			jo.put("root", ja);
			jsontext = jo.toString();
		}else{
			List err = data.getErrData();
			if(err!=null && err.size()>0 ){
				ja=JSONArray.fromObject(err);
				jsontext = ja.toString();
			}
		}
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		response.getWriter().write(jsontext);
	}
}
