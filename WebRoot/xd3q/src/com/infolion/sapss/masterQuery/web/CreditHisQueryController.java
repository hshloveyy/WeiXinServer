/*
 * @(#)billApplyController.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：王懿璞
 *  时　间：2009-6-12
 *  描　述：创建
 */

package com.infolion.sapss.masterQuery.web;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

import com.infolion.platform.component.ExtComponentServlet;
import com.infolion.platform.core.web.BaseMultiActionController;
import com.infolion.sapss.common.ExcelObject;
import com.infolion.sapss.masterQuery.service.CreditHisQueryJdbcService;

/**
 * 
 * <pre>
 * 控制器
 * </pre>
 * 
 * <br>
 * JDK版本:1.5
 * 
 * @author 王懿璞
 * @version 1.0
 * @see The author for more details
 * @since 1.0
 */
public class CreditHisQueryController extends BaseMultiActionController {
	private final Log log = LogFactory.getLog(CreditHisQueryController.class);
	// 服务类注入	
	@Autowired
	private CreditHisQueryJdbcService creditHisQueryJdbcService;
	public ModelAndView creditHisView(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		return new ModelAndView("sapss/credit/creditHisQuery");
	}
	
	public ModelAndView creditPayView(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		return new ModelAndView("sapss/credit/creditPayQuery");
	}
	
	
	/**
	 * 萃取url
	 * @param req
	 * @return 参数名,参数值的map
	 */
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
			
	/**
	 * 查询
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */	
	public void queryCreditInfo(HttpServletRequest request, HttpServletResponse response)
	throws IOException, ServletException {
		Map<String, String> filter = extractFR(request);
		String sql = creditHisQueryJdbcService.getQueryCreditHisInfoSql(filter);
		String grid_columns = "DEPT_NAME,BUSINESS_TYPE,PROJECT_NO,CONTRACT_NO,EKKO_UNSEZ,MATERIAL_GROUP,BENEFIT,CUSTOMER_LINK_MAN,CREDIT_INFO,CREATE_DATE," +
	    "CREATE_BANK,CREDIT_NO,AVAIL_DATE,CURRENCY,AMOUNT,USDAMOUNT,LOADING_PERIOD,VALID_DATE,CREDIT_STATE,PICK_LIST_TOTAL,AMOUNT_PICKTOTAL,USDAMOUNT_PICKTOTAL,AMOUNT_PAYMENTTOTAL,USDAMOUNT_PAYMENTTOTAL";
		String grid_size = "20";
		request.setAttribute("grid_sql", sql);
		request.setAttribute("grid_columns", grid_columns);
		request.setAttribute("grid_size", grid_size);
		ExtComponentServlet extComponentServlet = new ExtComponentServlet();
		extComponentServlet.processGrid(request, response, true);
    }
	
	public void queryCreditInfoPay(HttpServletRequest request, HttpServletResponse response)
	throws IOException, ServletException {
		Map<String, String> filter = extractFR(request);
		String sql = creditHisQueryJdbcService.getQueryCreditPayInfoSql(filter);
		String grid_columns = "DEPT_NAME,CREATE_DATE,CREATE_BANK,CREDIT_NO,PROJECT_NO,AMOUNT,CURRENCY,ESTITLE,PICK_LIST_NO,ACCEPTANCE_DATE,PAY_DATE,PAYMENTNO," +
				"PTTITLE,BSTITLE,PPAYDATE,PCURRENCY,DOCUMENTARYDATE,APPLYAMOUNT,COLLECTBANKID,DOCUMENTARYLIMIT,DOCTARYINTEREST,DOCUMENTARYRATE" ;
		String grid_size = "20";
		request.setAttribute("grid_sql", sql);
		request.setAttribute("grid_columns", grid_columns);
		request.setAttribute("grid_size", grid_size);
		ExtComponentServlet extComponentServlet = new ExtComponentServlet();
		extComponentServlet.processGrid(request, response, true);
    }
	
	public void dealOutToExcel(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		String[] titles  = new String[]{"序号","部门","业务类型","项目编号","采购合同号","外部纸质合同号","物料组名称","受益人","国内委托方","保证金","开证日期",
				"开证行","信用证号","信用证期限","币别","信用证金额","信用证美金金额","装期","效期","信用证状态","到单金额","信用证扣去到单余额","信用证扣去到单美金余额","信用证扣去实际付款余额","信用证扣去实际付款美金余额"};
		ExcelObject excel = new ExcelObject(titles);
		Map<String, String> filter = extractFR(request);
		String sql = creditHisQueryJdbcService.getQueryCreditHisInfoSql(filter);
		creditHisQueryJdbcService.dealOutToExcel(sql ,excel);
		
		try{
			//response.reset();
	        response.setCharacterEncoding("utf-8");
	        response.setContentType("application/x-download;charset=utf-8");
	        response.setHeader("Content-Disposition", (new StringBuilder("attachment;filename=")).append(URLEncoder.encode("信用证历史明细表.xls", "utf-8")).toString());
			excel.write(response.getOutputStream());
			response.flushBuffer();
			response.getOutputStream().close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void dealOutToExcelPay(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		String[] titles  = new String[]{"序号","部门","开证日期","开证行","信用证号","立项号","开证金额","开证币别","开证状态","到单号",
				"承兑日","付款日","付款单号","付款类型","付款状态","银行到期付款日","付款币别","押汇日期","押汇/付款金额","押汇/付款银行","押汇期限","押汇利率","押汇汇率"};
		ExcelObject excel = new ExcelObject(titles);
		Map<String, String> filter = extractFR(request);
		String sql = creditHisQueryJdbcService.getQueryCreditPayInfoSql(filter);
		creditHisQueryJdbcService.dealOutToExcelPay(sql ,excel);
		
		try{
			//response.reset();
	        response.setCharacterEncoding("utf-8");
	        response.setContentType("application/x-download;charset=utf-8");
	        response.setHeader("Content-Disposition", (new StringBuilder("attachment;filename=")).append(URLEncoder.encode("信用证付款明细表.xls", "utf-8")).toString());
			excel.write(response.getOutputStream());
			response.flushBuffer();
			response.getOutputStream().close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

}
