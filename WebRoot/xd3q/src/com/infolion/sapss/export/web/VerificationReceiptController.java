package com.infolion.sapss.export.web;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
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
import com.infolion.sapss.export.domain.TExportBills;
import com.infolion.sapss.export.domain.TVerificationReceipt;
import com.infolion.sapss.export.service.VerificationReceiptService;

public class VerificationReceiptController extends BaseMultiActionController{
	
	@Autowired
	private VerificationReceiptService verificationReceiptService;

	public VerificationReceiptService getVerificationReceiptService() {
		return verificationReceiptService;
	}

	public void setVerificationReceiptService(
			VerificationReceiptService verificationReceiptService) {
		this.verificationReceiptService = verificationReceiptService;
	}
	
	
	public ModelAndView updateView(HttpServletRequest request,
			HttpServletResponse response) throws IllegalAccessException,
			InvocationTargetException {
		String id = request.getParameter("verificationReceiptId");
		TVerificationReceipt receipt = verificationReceiptService.getVerificationReceipt(id);
		request.setAttribute("verification", receipt);
		return new ModelAndView("sapss/export/verification/verificationReceiptCreate");
	}
	
	public ModelAndView createView(HttpServletRequest request,HttpServletResponse response){
	    return new ModelAndView("sapss/export/verification/verificationReceiptCreate");
	}
	
	public void updateVerification(HttpServletRequest request,
			HttpServletResponse response, TVerificationReceipt re)
			throws IOException {
		re.setIsAvailable("1");
		if (re.getVerificationReceiptId() == null
				|| "".equals(re.getVerificationReceiptId())) {
			UserContext userContext = UserContextHolder.getLocalUserContext()
					.getUserContext();
			re.setVerificationReceiptId(CodeGenerator.getUUID());
			re.setCreaterTime(DateUtils.getCurrTime(2));
			re.setCreator(userContext.getSysUser().getUserId());
			re.setDeptId(userContext.getSysDept().getDeptid());
		}
		if(re.getReceiptDeptId().equals(request.getParameter("receiptDeptIdTemp"))){
			re.setReceiptDeptName(request.getParameter("receiptDeptNameTemp"));
		}
		verificationReceiptService.saveOrUpdateVerificationReceipt(re);
		JSONObject jo = new JSONObject();
		jo.put("returnMessage", "保存成功！");
		String jsontext = jo.toString();
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		this.operateSuccessfullyWithString(response, jsontext);
	}
	
	public ModelAndView manageView(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		return new ModelAndView("sapss/export/verification/manager");
	}
	
	public void delete(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String id = request.getParameter("verificationReceiptId");
		TVerificationReceipt receipt = verificationReceiptService.getVerificationReceipt(id);
		receipt.setIsAvailable("0");
		verificationReceiptService.saveOrUpdateVerificationReceipt(receipt);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("returnMessage", "删除成功!");
		String jsontext = jsonObject.toString();
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		this.operateSuccessfullyWithString(response, jsontext);
	}
	
	public void find(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		Map<String, String> filter = extractFR(request);
		String sql = getQuerySql(filter);
		String grid_columns = "VERIFICATION_RECEIPT_ID, RECEIPT_DEPT_NAME, RECEIPT_DEPT_ID,RECEIPT_MAN,EXPORT_APPLY_ID," +
				              "EXPORT_APPLY_NO,RECEIPT_DATE,BACK_DATE,WRITE_NO,TAX_NO,CREATOR,DEPT_ID,CREATER_TIME,IS_AVAILABLE,MARK";
		String grid_size = "10";

		request.setAttribute("grid_sql", sql);
		request.setAttribute("grid_columns", grid_columns);
		request.setAttribute("grid_size", grid_size);

		ExtComponentServlet extComponentServlet = new ExtComponentServlet();
		extComponentServlet.processGrid(request, response, true);
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
	
	
	public String getQuerySql(Map<String, String> filter) {
		String sql = "SELECT t.* FROM T_EXPORT_VERIFICATION_RECEIPT t where t.is_available=1 ";
		if (filter != null && !filter.isEmpty()) {
			
			String deptId = filter.get("deptId");
			if (StringUtils.isNotBlank(deptId)) {
				sql += " and t.RECEIPT_DEPT_ID = '" + deptId + "'";
			}

			String noticeNo = filter.get("noticeNo");
			if (StringUtils.isNotBlank(noticeNo)) {
				sql += " and t.export_Apply_No like '%" + noticeNo + "%'";
			}
			String taxNo = filter.get("taxNo");
			if (StringUtils.isNotBlank(taxNo)) {
				sql += " and t.tax_No like '%" + taxNo + "%'";
			}
			
			String receiptMan = filter.get("receiptMan");
			if (StringUtils.isNotBlank(receiptMan)) {
				sql += " and t.receipt_Man like '%" + receiptMan + "%'";
			}

			String receiptDate = filter.get("receiptDate");
			if (StringUtils.isNotBlank(receiptDate)) {
				sql += " and t.receipt_Date >= '" + receiptDate + "'";
			}
			String receiptDate1 = filter.get("receiptDate1");
			if (StringUtils.isNotBlank(receiptDate1)) {
				sql += " and t.receipt_Date <= '" + receiptDate1 + "'";
			}
			
			String backDate = filter.get("backDate");
			if (StringUtils.isNotBlank(backDate)) {
				sql += " and t.back_Date >= '" + backDate + "'";
			}
			String backDate1 = filter.get("backDate1");
			if (StringUtils.isNotBlank(backDate1)) {
				sql += " and t.receipt_Date <= '" + backDate1 + "'";
			}
			String writeNo = filter.get("writeNo");
			if (StringUtils.isNotBlank(writeNo)) {
				sql += " and t.write_No like '%" + writeNo + "%'";
			}
		}
		
		sql += " order by t.CREATER_TIME desc";
		return sql;
	}
	
	public void dealOutToExcel(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		String[] titles  = new String[]{"序号","出口货物通知单号","核销单号","部门","领单人","领单日期","回单日期","发票号","备注"};

		ExcelObject excel = new ExcelObject(titles);
		Map<String, String> filter = extractFR(request);
		String sql = getQuerySql(filter);

		verificationReceiptService.dealOutToExcel(sql ,excel);
		
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
