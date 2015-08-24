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

import com.infolion.sapss.export.service.ExportService;

public class ExportQueryController extends BaseMultiActionController {

	@Autowired
	private ExportService exportService;

	/**
	 * 转到出口出单审单查找页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView toFindExportBillExam(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return new ModelAndView("sapss/queryForm/findExportBillExam");
	}

	/**
	 * 查询采购合同信息
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	public void queryExportBillExam(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		Map<String, String> map = extractFR(request);
		String sql = getQueryExportBillExamSql(map);

		String grid_columns = "EXPORT_BILL_EXAM_ID, EXPORT_APPLY_ID,"
				+ "EXPORT_APPLY_NO,INV_NO,BILL_TYPE,DEPT_ID,BILL_DATE,"
				+ "CONTRACT_NO,LCDPDA_NO,TOTAL,CURRENCY,WRITE_NO,EXAM_RECORD,"
				+ "EXAM_DATE,BANK,IS_NEGOTIAT,BILL_SHIP_DATE,"
				+ "SHOULD_INCOME_DATE,MARK,IS_AVAILABLE,CREATOR_TIME,"
				+ "CREATOR,CREATOR_DEPT,TRADE_TYPE";

		String grid_size = "20";
		request.setAttribute("grid_sql", sql);
		request.setAttribute("grid_columns", grid_columns);
		request.setAttribute("grid_size", grid_size);

		ExtComponentServlet extComponentServlet = new ExtComponentServlet();
		extComponentServlet.processGrid(request, response, true);
	}

	public String getQueryExportBillExamSql(Map<String, String> filter) {
		String sql = "SELECT t.* FROM t_export_bill_exam t Where t.is_available=1 ";

		if (filter != null && !filter.isEmpty()) {
			String contractNo = filter.get("contractNo");
			if (StringUtils.isNotBlank(contractNo)) {
				sql += " and t.CONTRACT_NO like '%" + contractNo + "%'";
			}

			String exportApplyNo = filter.get("exportApplyNo");
			if (StringUtils.isNotBlank(exportApplyNo)) {
				sql += " and t.export_Apply_No like '%" + exportApplyNo + "%'";
			}
			
			String invNo = filter.get("invNo");
			if (StringUtils.isNotBlank(invNo)) {
				sql += " and t.INV_NO like '%" + invNo + "%'";
			}
			
			String writeNo = filter.get("writeNo");
			if (StringUtils.isNotBlank(writeNo)) {
				sql += " and t.WRITE_NO like '%" + writeNo + "%'";
			}

			String sDate = filter.get("sDate");
			if (StringUtils.isNotBlank(sDate)) {
				sql += " and t.creator_time >= '" + sDate + "'";
			}
			String eDate = filter.get("eDate");
			if (StringUtils.isNotBlank(eDate)) {
				sql += " and t.creator_time <= '" + eDate + "'";
			}
		}

		// 部门的过滤
		UserContext userContext = UserContextHolder.getLocalUserContext()
				.getUserContext();
		sql += " and t.dept_id in (" + userContext.getGrantedDepartmentsId()
				+ ",";
		sql += "'" + userContext.getSysDept().getDeptid() + "')";

		sql += " order by t.creator_time desc";
		return sql;
	}

	/**
	 * 萃取url
	 * 
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

}
