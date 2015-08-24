package com.infolion.xdss3.foreignExchange.web;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

import com.infolion.platform.core.web.BaseMultiActionController;
import com.infolion.platform.dpframework.util.StringUtils;
import com.infolion.platform.util.MultyGridData;
import com.infolion.xdss3.foreignExchange.service.ForeignExchangeService;
import com.infolion.xdss3.voucher.service.VoucherService;



public class ForeignExchangeController  extends BaseMultiActionController
{
	@Autowired
	private ForeignExchangeService foreignExchangeService;
	
	/**
	 * @param foreignExchangeService the foreignExchangeService to set
	 */
	public void setForeignExchangeService(
			ForeignExchangeService foreignExchangeService) {
		this.foreignExchangeService = foreignExchangeService;
	}

	@Autowired
	private VoucherService voucherService;

	/**
	 * @param voucherService
	 *            the voucherService to set
	 */
	public void setVoucherService(VoucherService voucherService)
	{
		this.voucherService = voucherService;
	}
	
	public ModelAndView foreignExchangeManger(HttpServletRequest request,
            HttpServletResponse response) {
        return new ModelAndView("xdss3/foreignExchange/foreignExchange");
    }
	
	public void voucherPreview(HttpServletRequest request, HttpServletResponse response)
	{
		String p_bukrs = request.getParameter("p_bukrs");
		String p_gsber = request.getParameter("p_gsber");
		String p_gjahr = request.getParameter("p_gjahr");
		String p_monat = request.getParameter("p_monat");
		String p_waers = request.getParameter("p_waers");
		String s_hkont_st = request.getParameter("s_hkont_st");
		String s_hkont_end = request.getParameter("s_hkont_end");
		String s_kunnr_st = request.getParameter("s_kunnr_st");
		String s_kunnr_end = request.getParameter("s_kunnr_end");
		String s_lifnr_st = request.getParameter("s_lifnr_st");
		String s_lifnr_end = request.getParameter("s_lifnr_end");
		String ck_zz = request.getParameter("ck_zz");
		String ck_kh = request.getParameter("ck_kh");
		String ck_gys = request.getParameter("ck_gys");
		String ck_wbzj = request.getParameter("ck_wbzj");
		String ck_sybm = request.getParameter("ck_sybm");
		String businessid2 =request.getParameter("businessid");
		this.voucherService.deleteVoucherByBusinessid(businessid2);
		//判断是否需要删除
		this.voucherService.judgeVoucherNeedDel(businessid2);
		if(StringUtils.isNotBlank(ck_sybm))p_gsber="*";
		List<Map<String, String>> list=foreignExchangeService._executeRfc(p_bukrs, p_gsber, p_gjahr, p_monat, p_waers, s_hkont_st, s_hkont_end, s_kunnr_st, s_kunnr_end, s_lifnr_st, s_lifnr_end, ck_zz, ck_kh, ck_gys, ck_wbzj);
		if(StringUtils.isNotBlank(ck_sybm) && "2100".equals(p_bukrs)){
			p_gsber="2199";
		}
		if(StringUtils.isNotBlank(ck_sybm) && !"2100".equals(p_bukrs)){
			p_gsber="";
		}
		String businessid=this.foreignExchangeService.genFEVoucher(list, p_gjahr, p_monat, p_gsber,p_bukrs,businessid2);
		
//		MultyGridData gridJsonData = new MultyGridData();
//		gridJsonData.setData(ageAnalysisList.toArray());
//        JSONObject jsonList = JSONObject.fromObject(gridJsonData);
		JSONObject jo = new JSONObject();
		jo.put("businessid", businessid);
        try {
//            response.setContentType("text/html;charset=UTF-8");
//            response.getWriter().print(jo);
        	this.operateSuccessfullyWithString(response, jo.toString());
        } catch (IOException e) {
            logger.error("输出json失败," + e.getMessage(), e.getCause());
        }
		
		
	}
}
