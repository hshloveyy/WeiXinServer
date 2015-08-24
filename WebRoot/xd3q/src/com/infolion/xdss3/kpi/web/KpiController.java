package com.infolion.xdss3.kpi.web;



import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

import com.infolion.platform.core.web.BaseMultiActionController;
import com.infolion.platform.dpframework.uicomponent.grid.GridService;
import com.infolion.platform.dpframework.uicomponent.grid.data.GridData;
import com.infolion.platform.dpframework.uicomponent.grid.data.GridQueryCondition;
import com.infolion.platform.dpframework.util.StringUtils;
import com.infolion.platform.util.MultyGridData;

import com.infolion.xdss3.kpi.domain.BankAcceptance;
import com.infolion.xdss3.kpi.service.KpiService;




public class KpiController  extends BaseMultiActionController
{
	@Autowired
	private KpiService kpiService;
	/**
	 * @param kpiService the kpiService to set
	 */
	public void setKpiService(KpiService kpiService) {
		this.kpiService = kpiService;
	}

	 @Autowired
    private GridService gridService;
    
    public void setGridService(GridService gridService) {
        this.gridService = gridService;
    }
	    
	public ModelAndView kpiManger(HttpServletRequest request,
            HttpServletResponse response) {
        return new ModelAndView("xdss3/kpi/kpi");
    }
	
	public void getKpi(HttpServletRequest request, HttpServletResponse response)
	{
		String p_bukrs = request.getParameter("p_bukrs");
		String p_gsber = request.getParameter("p_gsber");
		String p_checkdate = request.getParameter("p_checkdate");
		p_checkdate = p_checkdate.replaceAll("-", "");
		Map<String,String> res = this.kpiService._executeRfc(p_bukrs, p_gsber, p_checkdate);
		String todate = p_checkdate.substring(0,4);
		todate = todate + "0101";		
		
		
		BigDecimal pjzy_sum= new BigDecimal("0");
		try {
			pjzy_sum = this.kpiService.getww_pjzy_sum(p_bukrs,p_gsber,todate,p_checkdate);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}		
//		BigDecimal pjzy = pjzy_sum.divide(new BigDecimal(res.get("WK_COUNT")),2,BigDecimal.ROUND_HALF_EVEN).add(new BigDecimal(res.get("WK_PJZY")));
		BigDecimal pjzy = pjzy_sum.add(new BigDecimal(res.get("WK_PJZY")));
		BigDecimal zzl = new BigDecimal(res.get("WK_YYSR")).multiply( new BigDecimal("100")).divide(pjzy,2,BigDecimal.ROUND_HALF_EVEN);
		BigDecimal jll = new BigDecimal(res.get("WK_JLR")).multiply( new BigDecimal("100")).divide(pjzy,2,BigDecimal.ROUND_HALF_EVEN);
		JSONObject jo = new JSONObject();
		jo.put("SAP_PJZY", res.get("WK_PJZY") );		
		jo.put("SAP_ZZL", res.get("WK_ZZL")+ "%");
		jo.put("SAP_JLL", res.get("WK_JLL")+ "%");
		jo.put("PJZY", pjzy.toString());		
		jo.put("ZZL", zzl.toString()+ "%");
		jo.put("JLL", jll.toString()+ "%");
		try {
//            response.setContentType("text/html;charset=UTF-8");
//            response.getWriter().print(jo);
			this.operateSuccessfullyWithString(response, jo.toString());
        } catch (IOException e) {
            logger.error("输出json失败," + e.getMessage(), e.getCause());
        }
	}
	
	public void getBankAcceptance(HttpServletRequest request, HttpServletResponse response,GridQueryCondition gridQueryCondition) throws Exception {
		String p_bukrs = request.getParameter("p_bukrs");
		String p_gsber = request.getParameter("p_gsber");
		String p_checkdate = request.getParameter("p_checkdate");
		p_checkdate = p_checkdate.replaceAll("-", "");
		String todate = p_checkdate.substring(0,4);
		todate = todate + "0101";
//		String grid_sql = this.kpiService.getPaymentQuerySql(p_bukrs,p_gsber,todate,p_checkdate);
//		gridQueryCondition.setBoName("");
//        gridQueryCondition.setTableSql("");
//        gridQueryCondition.setDefaultCondition("1=1 ");
//        gridQueryCondition.setWhereSql("");
//        gridQueryCondition.setOrderSql("");
//        gridQueryCondition.setGroupBySql("");
//        gridQueryCondition.setTableName("(" + grid_sql + ") t");
//        gridQueryCondition.setHandlerClass("com.infolion.xdss3.kpi.domain.BankAcceptanceGrid");
//        String editable = "false";
//        String needAuthentication = "true";
//        GridData gridJsonData = this.gridService.getGridData(gridQueryCondition, editable, needAuthentication);		
//        JSONObject jsonList = JSONObject.fromObject(gridJsonData);

		List<BankAcceptance> list =this.kpiService.getBankAcceptance2(p_bukrs, p_gsber, todate, p_checkdate);

		
		MultyGridData gridJsonData = new MultyGridData();
		gridJsonData.setData(list.toArray());
		JSONObject jsonList = JSONObject.fromObject(gridJsonData);
        try {
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(jsonList);
        } catch (IOException e) {
            logger.error("输出json失败," + e.getMessage(), e.getCause());
        }
	}
}
