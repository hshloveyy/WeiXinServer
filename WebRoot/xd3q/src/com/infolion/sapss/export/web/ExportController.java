package com.infolion.sapss.export.web;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;



import com.infolion.platform.component.ExtComponentServlet;
import com.infolion.platform.component.dictionary.DictionaryRow;
import com.infolion.platform.component.workflow.WorkflowService;
import com.infolion.platform.component.workflow.ext.TaskInstanceContext;
import com.infolion.platform.console.org.domain.SysDept;
import com.infolion.platform.console.org.service.SysDeptService;
import com.infolion.platform.console.org.service.SysUserService;
import com.infolion.platform.console.sys.context.UserContext;
import com.infolion.platform.console.sys.context.UserContextHolder;
import com.infolion.platform.core.BusinessException;
import com.infolion.platform.core.web.BaseMultiActionController;
import com.infolion.platform.dpframework.util.ExBeanUtils;
import com.infolion.platform.sys.cache.SysCachePoolUtils;
import com.infolion.platform.util.CodeGenerator;
import com.infolion.platform.util.DateUtils;
import com.infolion.sapss.common.ExcelObject;
import com.infolion.sapss.export.domain.TExportBillExam;
import com.infolion.sapss.export.domain.TExportBillExamD;
import com.infolion.sapss.export.domain.TExportBills;
import com.infolion.sapss.export.domain.TExportShipmentStat;
import com.infolion.sapss.export.service.ExportJdbcService;
import com.infolion.sapss.export.service.ExportService;
import com.infolion.xdss3.profitLoss.domain.ProfitLoss;

public class ExportController extends BaseMultiActionController {

	@Autowired
	private ExportService exportService;
	@Autowired
	private ExportJdbcService exportJdbcService;
	@Autowired
	SysDeptService sysDeptService;
	@Autowired
	SysUserService sysUserService;

	public void setExportService(ExportService exportService) {
		this.exportService = exportService;
	}

	// ///////////////出口押汇开始 By Wang Yipu 2009.06.10///////////////
	/**
	 * 提交并保存出口押汇单
	 * 
	 * @param request
	 * @param response
	 * @param tExportBills
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws IOException
	 * @throws Exception
	 */
	public void submitAndSaveExportBills(HttpServletRequest request,
			HttpServletResponse response, TExportBills tExportBills)
			throws IllegalAccessException, InvocationTargetException,
			IOException, Exception {
		String taskId = request.getParameter("workflowTaskId");
		System.out.println("taskId : " + taskId);
		exportService.submitAndSaveExportBills(taskId, tExportBills);
		JSONObject jo = new JSONObject();
		jo.put("returnMessage", "提交成功！");
		String jsontext = jo.toString();
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		this.operateSuccessfullyWithString(response, jsontext);
	}

	/**
	 * 提交流程审批
	 * 
	 * @param request
	 * @param response
	 * @param receiptInfo
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws IOException
	 * @throws Exception
	 */
	public void submitExportBillsWorkflow(HttpServletRequest request,
			HttpServletResponse response, TExportBills tExportBills)
			throws IllegalAccessException, InvocationTargetException,
			IOException, Exception {
		String taskId = request.getParameter("workflowTaskId");

		String rtn = this.exportService.verifyFilds(taskId, tExportBills);
		if (!"".equals(rtn)) {
			throw new BusinessException(rtn);
		}

		String msg = "";

		tExportBills.setApprovedTime(DateUtils.getCurrTime(2));
		UserContext userContext = UserContextHolder.getLocalUserContext()
				.getUserContext();
		// 业务描述信息
		tExportBills.setWorkflowBusinessNote(userContext.getSysDept()
				.getDeptname()
				+ "|"
				+ userContext.getSysUser().getRealName()
				+ "|"
				+ tExportBills.getExportBillNo()
				+ "|"
				+ tExportBills.getRealMoney());

		String doWorkflow = request.getParameter("doWorkflow");
		if (doWorkflow == null && !"mainForm".equals(doWorkflow)) {
			String workflowLeaveTransitionName = request
					.getParameter("workflowLeaveTransitionName");
			String workflowExamine = request.getParameter("workflowExamine");
			tExportBills.setWorkflowExamine(workflowExamine);
			tExportBills
					.setWorkflowLeaveTransitionName(workflowLeaveTransitionName);
		}

		// 取得工作流节点名称

		JSONObject jo = new JSONObject();
		jo = this.exportService.submitExportBillsWorkflow(tExportBills, taskId);

		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");

		this.operateSuccessfullyWithString(response, jo.toString());
	}

	/**
	 * 出口押汇单审批界面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public ModelAndView exportBillsExamine(HttpServletRequest request,
			HttpServletResponse response) throws IllegalAccessException,
			InvocationTargetException {
		String exportBillId = request.getParameter("businessRecordId");
		String tradeType = request.getParameter("tradeType");
		String taskId = request.getParameter("taskId");

		TaskInstanceContext taskInstanceContext = WorkflowService
				.getTaskInstanceContext(taskId);
		String taskName = taskInstanceContext.getTaskName();
		String taskType = "";
		if (taskName.equals("资金部人员办理并填写实收金额及币别")
				|| taskName.equals("贸管审单员办理并填写实收金额及币别")) {
			taskType = "1";
		}

		request.setAttribute("businessRecordId", exportBillId);
		request.setAttribute("taskId", taskId);

		request.setAttribute("businessRecordId", exportBillId);
		TExportBills tExportBills = this.exportService
				.getExportBills(exportBillId);

		UserContext userContext = UserContextHolder.getLocalUserContext()
				.getUserContext();
		Boolean modify = false;
		// 创建者能修改：
		if (tExportBills.getCreator().equals(
				userContext.getSysUser().getUserId())) {
			modify = true;
		}

		request.setAttribute("iframeSrc",
				"exportController.spr?action=examineExportBillsView&tradeType="
						+ tradeType + "&operate=iframe&exportBillId="
						+ exportBillId + "&modify=" + modify + "&taskType="
						+ taskType);
		request.setAttribute("submitUrl",
				"exportController.spr?action=submitExportBillsWorkflow");

		System.out.println("businessRecordId：" + exportBillId);

		return new ModelAndView("sapss/workflow/commonProcessPage");
	}

	/**
	 * 进入审批流程出口押汇单界面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ModelAndView examineExportBillsView(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String exportBillId = request.getParameter("exportBillId");
		String modify = request.getParameter("modify");
		String tradeType = request.getParameter("tradeType");

		TExportBills tExportBills = exportService.getExportBills(exportBillId);

		TExportBillExam tExportBillExam = exportService
				.getExportBillExam(tExportBills.getExportBillExamId());
		tradeType = tExportBillExam.getTradeType();

		UserContext userContext = UserContextHolder.getLocalUserContext()
				.getUserContext();
		request.setAttribute("main", tExportBills);
		request.setAttribute("tExportBillExam", tExportBillExam);

		// 操作类型
		String operType = "000";
		if (modify.equals("true"))
			operType = "100";

		request.setAttribute("save", operType.substring(0, 1).equals("0"));
		request.setAttribute("submit", operType.substring(1, 2).equals("0"));
		request.setAttribute("close", operType.substring(2, 3).equals("0"));
		System.out.println("operType = " + operType);

		// 贸易类型
		request.setAttribute("tradeType", tradeType);

		String taskType = request.getParameter("taskType");
		request.setAttribute("taskType", taskType);
		return new ModelAndView("sapss/export/exportBills/exportBillsCreate");

	}

	public ModelAndView exportBillsManageView(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		UserContext userContext = UserContextHolder.getLocalUserContext()
				.getUserContext();
		String loginer = userContext.getSysUser().getUserId();
		request.setAttribute("loginer", loginer);

		return new ModelAndView("sapss/export/exportBills/exportBillsManage");
	}
	/**
	 * 转到综合查询
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ModelAndView toCompositeQuery(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
		String loginer = userContext.getSysUser().getUserId();
		request.setAttribute("loginer", loginer);

		return new ModelAndView("sapss/export/exportBills/compositeQuery");

	}
	
	public ModelAndView createExportBills(HttpServletRequest request,
			HttpServletResponse response) throws IllegalAccessException,
			InvocationTargetException {

		// 贸易类型
		String tradeType = request.getParameter("tradeType");
		request.setAttribute("tradeType", tradeType);

		// 货币
		Map<String, DictionaryRow> CurrencyMap = SysCachePoolUtils
				.getDictTableGroup("BM_CURRENCY");
		Collection<DictionaryRow> Currency = CurrencyMap.values();
		request.setAttribute("Currency", Currency);

		request.setAttribute("isShow", "false");

		// 操作类型
		String operType = "111";
		request.setAttribute("save", operType.substring(0, 1).equals("0"));
		request.setAttribute("submit", operType.substring(1, 2).equals("0"));
		request.setAttribute("close", operType.substring(2, 3).equals("0"));

		String taskType = request.getParameter("taskType");
		request.setAttribute("taskType", taskType);
		return new ModelAndView("sapss/export/exportBills/exportBillsCreate");
	}

	public ModelAndView updateExportBillsView(HttpServletRequest request,
			HttpServletResponse response) throws IllegalAccessException,
			InvocationTargetException {

		String ExportBillId = request.getParameter("ExportBillId");
		TExportBills tExportBills = exportService.getExportBills(ExportBillId);
		request.setAttribute("main", tExportBills);

		String exportBillExamId = tExportBills.getExportBillExamId();
		TExportBillExam tExportBillExam = null;
		if (StringUtils.isNotBlank(exportBillExamId))
			tExportBillExam = exportService.getExportBillExam(exportBillExamId);
		request.setAttribute("tExportBillExam", tExportBillExam);

		// 货币
		Map<String, DictionaryRow> CurrencyMap = SysCachePoolUtils
				.getDictTableGroup("BM_CURRENCY");
		Collection<DictionaryRow> Currency = CurrencyMap.values();
		request.setAttribute("Currency", Currency);
		request.setAttribute("isShow", "1".equals(request
				.getParameter("isShow")));

		// 操作类型
		String operType = request.getParameter("operType");
		if (operType.length() < 3)
			operType = "111";

		SysDept sysDept = sysDeptService
				.queryDeptById(tExportBills.getDeptId());
		request.setAttribute("sysDept", sysDept);
		request.setAttribute("save", operType.substring(0, 1).equals("0"));
		request.setAttribute("submit", operType.substring(1, 2).equals("0"));
		request.setAttribute("close", operType.substring(2, 3).equals("0"));
		System.out.println("operType = " + operType);

		String taskType = request.getParameter("taskType");
		request.setAttribute("taskType", taskType);
		return new ModelAndView("sapss/export/exportBills/exportBillsCreate");
	}

	public void updateExportBills(HttpServletRequest request,
			HttpServletResponse response, TExportBills ExportBills)
			throws IOException {
		ExportBills.setIsAvailable("1");
		if (ExportBills.getExportBillId() == null
				|| "".equals(ExportBills.getExportBillId())) {
			UserContext userContext = UserContextHolder.getLocalUserContext()
					.getUserContext();
			ExportBills.setExportBillId(CodeGenerator.getUUID());
			ExportBills.setCreatorTime(DateUtils.getCurrTime(2));
			ExportBills.setCreator(userContext.getSysUser().getUserId());
			ExportBills.setDeptId(userContext.getSysDept().getDeptid());
			ExportBills.setExamineState("1");
			ExportBills.setExportBillNo(exportJdbcService.getExportBillNo());
			exportService.saveExportBillsObject(ExportBills);
		} else {
			exportService.updateExportBillsObject(ExportBills);
		}
		JSONObject jo = new JSONObject();
		jo.put("returnMessage", "保存成功！");
		String jsontext = jo.toString();
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");

		this.operateSuccessfullyWithString(response, jsontext);
	}

	public void deleteExportBills(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String exportBillId = request.getParameter("exportBillId");
		TExportBills tExportBills = exportService.getExportBills(exportBillId);

		tExportBills.setIsAvailable("0");
		exportService.updateExportBillsObject(tExportBills);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("returnMessage", "删除成功!");
		String jsontext = jsonObject.toString();
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		this.operateSuccessfullyWithString(response, jsontext);
	}

	public void queryExportBills(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		Map<String, String> filter = extractFR(request);
		String sql = getQueryExportBillsSql(filter);
		String grid_columns = "EXPORT_BILL_ID,EXPORT_BILL_NO,EXPORT_BILL_EXAM_ID,CMD,"
				+ "DEPT_ID,EXAMINE_STATE,EXAMINE_STATE_D_EXAMINE_STATE,APPLY_TIME,BANK,CURRENCY,"
				+ "APPROVED_TIME,IS_AVAILABLE,CREATOR_TIME,CREATOR,APPLY_MONEY,REAL_MONEY"
				+ ",REAL_NAME,DEPT_NAME";
		String grid_size = "10";

		request.setAttribute("grid_sql", sql);
		request.setAttribute("grid_columns", grid_columns);
		request.setAttribute("grid_size", grid_size);

		ExtComponentServlet extComponentServlet = new ExtComponentServlet();
		extComponentServlet.processGrid(request, response, true);
	}

	// ///////////////出口押汇结束 By Wang Yipu 2009.06.10//////////////

	public ModelAndView createExportStat(HttpServletRequest request,
			HttpServletResponse response) throws IllegalAccessException,
			InvocationTargetException {

		// 贸易类型
		String tradeType = request.getParameter("tradeType");
		request.setAttribute("tradeType", tradeType);

		// 货币
		Map<String, DictionaryRow> CurrencyMap = SysCachePoolUtils
				.getDictTableGroup("BM_CURRENCY");
		Collection<DictionaryRow> Currency = CurrencyMap.values();
		request.setAttribute("Currency", Currency);

		request.setAttribute("isShow", "false");

		return new ModelAndView("sapss/export/exportStat/exportStatCreate");
	}

	public ModelAndView updateExportStatView(HttpServletRequest request,
			HttpServletResponse response) throws IllegalAccessException,
			InvocationTargetException {

		//
		String exportStateId = request.getParameter("exportStatId");
		Object export = exportService.getExportShipmentStat(exportStateId);
		TExportShipmentStat main = (TExportShipmentStat) export;
		request.setAttribute("main", main);

		// 货币
		Map<String, DictionaryRow> CurrencyMap = SysCachePoolUtils
				.getDictTableGroup("BM_CURRENCY");
		Collection<DictionaryRow> Currency = CurrencyMap.values();
		request.setAttribute("Currency", Currency);
		request.setAttribute("isShow", "1".equals(request
				.getParameter("isShow")));

		return new ModelAndView("sapss/export/exportStat/exportStatCreate");
	}

	public void updateExportStat(HttpServletRequest request,
			HttpServletResponse response, TExportShipmentStat exportStat)
			throws IOException, ParseException {
		exportStat.setIsAvailable("1");
		if (exportStat.getExportShipmentStatId() == null
				|| "".equals(exportStat.getExportShipmentStatId())) {
			UserContext userContext = UserContextHolder.getLocalUserContext()
					.getUserContext();
			exportStat.setExportShipmentStatId(CodeGenerator.getUUID());
			exportStat.setCreatorTime(DateUtils.getCurrTime(2));
			exportStat.setCreator(userContext.getSysUser().getUserId());
			exportStat.setCreatorDept(userContext.getSysDept().getDeptid());
			updateDrawBackEndDate(exportStat);
			exportService.saveExportStatObject(exportStat);
		}

		else {
			updateDrawBackEndDate(exportStat);
			exportService.updateExportStatObject(exportStat);
		}
		JSONObject jo = new JSONObject();
		jo.put("returnMessage", "保存成功！");
		String jsontext = jo.toString();
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");

		this.operateSuccessfullyWithString(response, jsontext);
	}
	
	private void updateDrawBackEndDate(TExportShipmentStat state) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = sdf.parse(state.getDrawbackDate());
		Calendar calendar=Calendar.getInstance();   
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH,calendar.get(Calendar.DAY_OF_MONTH)+90);//
		state.setDrawbackEndDate(sdf.format(calendar.getTime()));//);
	}

	public void deleteExportStat(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String exportShipMentStatID = request
				.getParameter("exportShipMentStatID");
		TExportShipmentStat stat = exportService
				.getExportShipmentStat(exportShipMentStatID);

		stat.setIsAvailable("0");
		exportService.updateExportStatObject(stat);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("returnMessage", "删除成功!");
		String jsontext = jsonObject.toString();
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		this.operateSuccessfullyWithString(response, jsontext);
	}

	public void queryExportStat(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		Map<String, String> filter = extractFR(request);
		String sql = getQueryExportStatSql(filter);
		String grid_columns = "EXPORT_SHIPMENT_STAT_ID,EXPORT_APPLY_NO,CREATOR,CREATOR_DEPT,PROJECT_NO,PROJECT_NAME,CONTRACT_NO,SAP_ORDER_NO,"
				+ "BATCH_NO,INV_NO,EXPORT_DATE,PRS_NUM,UNIT,TOTAL,CURRENCY,ADDED_TAX_INV_NO,ADDED_TAX_INV_VALUE,ADDED_TAX_VALUE,WRITE_NO,"
				+ "CATCH_NO,PORT,CUSTOME_NAME,RATE,RECEIPT_DATE,DRAWBACK_DATE,DRAWBACK_END_DATE,MARK";
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

	public String getQueryExportStatSql(Map<String, String> filter) {
		String sql = "SELECT t.*,ap.notice_no,c.contract_no,c.sap_order_no,p.project_name,p.project_no FROM t_export_shipment_stat t "
				+ "left outer join t_export_apply ap on t.export_apply_id=ap.export_apply_id "
				+ "left outer join t_contract_sales_info c on c.contract_sales_id=ap.contract_sales_id "
				+ "left outer join t_sys_dept d on d.dept_id=t.creator_dept "
				+ "left outer join t_project_info p on p.project_id = c.project_id where t.is_available=1";
		if (filter != null && !filter.isEmpty()) {
			String deptCode = filter.get("deteCode");
			if (StringUtils.isNotBlank(deptCode)) {
				sql += " and d.ywbm_code = '" + deptCode + "'";
			}

			String exportApplyNo = filter.get("exportApplyNo");
			if (StringUtils.isNotBlank(exportApplyNo)) {
				sql += " and t.export_Apply_No like '%" + exportApplyNo + "%'";
			}
			String INVNO = filter.get("INVNO");
			if (StringUtils.isNotBlank(INVNO)) {
				sql += " and t.INV_NO like '%" + INVNO + "%'";
			}
			String contractNo = filter.get("contractNo");
			if (StringUtils.isNotBlank(contractNo)) {
				sql += " and c.contract_no like '%" + contractNo + "%'";
			}
			String sapOrderNo = filter.get("sapOrderNo");
			if (StringUtils.isNotBlank(sapOrderNo)) {
				sql += " and c.sap_order_no like '%" + sapOrderNo + "%'";
			}
			String addedTaxINVNO = filter.get("addedTaxINVNO");

			if (StringUtils.isNotBlank(addedTaxINVNO)) {
				sql += " and t.added_Tax_INVNO like '%" + addedTaxINVNO + "%'";
			}
			String writeNo = filter.get("writeNo");
			if (StringUtils.isNotBlank(writeNo)) {
				sql += " and t.write_No like '%" + writeNo + "%'";
			}
			String declarationNO = filter.get("declarationNO");
			if (StringUtils.isNotBlank(declarationNO)) {
				sql += " and t.declaration_NO like '%" + declarationNO + "%'";
			}
			String catchNo = filter.get("catchNo");
			if (StringUtils.isNotBlank(catchNo)) {
				sql += " and t.catch_No like '%" + catchNo + "%'";
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
		sql += " and t.creator_dept in ( "
				+ userContext.getGrantedDepartmentsId() + ")";
		sql += " order by t.creator_time desc";
		return sql;
	}

	public String getQueryExportBillsSql(Map<String, String> filter) {
		String sql = "SELECT t.*,nvl(t.EXAMINE_STATE,1) EXAMINE_STATE_D_EXAMINE_STATE "
				+ ",nvl(T1.REAL_NAME,'') REAL_NAME,nvl(T2.DEPT_NAME,'') DEPT_NAME "
				+ "FROM t_export_bills t "
				+ "left outer join t_sys_dept d on d.dept_id=t.dept_id "
				+ "Left Join T_Sys_User T1 On T.Creator=T1.User_Id "
				+ "Left Join T_Sys_Dept T2 On T.Dept_ID=T2.Dept_ID "
				+ "Where t.is_available = 1 ";

		if (filter != null && !filter.isEmpty()) {
			String deptCode = filter.get("dict_deptCode");
			if (StringUtils.isNotBlank(deptCode)&&!"请选择...".equals(deptCode)) {
				sql += " and d.dept_name = '" + deptCode + "'";
			}

			String exportBillNo = filter.get("exportBillNo");
			if (StringUtils.isNotBlank(exportBillNo)) {
				sql += " and t.EXPORT_BILL_NO like '%" + exportBillNo + "%'";
			}

			String exportBillExamId = filter.get("exportBillExamId");
			if (StringUtils.isNotBlank(exportBillExamId)) {
				sql += " and t.EXPORT_BILL_EXAM_ID like '%" + exportBillExamId
						+ "%'";
			}

			String realMoney = filter.get("realMoney");
			if (StringUtils.isNotBlank(realMoney)) {
				sql += " and t.REAL_MONEY >='" + realMoney+"'";
			}
			
			String realMoney1 = filter.get("realMoney1");
			if (StringUtils.isNotBlank(realMoney1)) {
				sql += " and t.REAL_MONEY <='" + realMoney1+"'";
			}
			
			
			String realName = filter.get("realName");
			if (StringUtils.isNotBlank(realName)) {
				sql += " and T1.REAL_NAME like '%" + realName+"%'";
			}

			String cmd = filter.get("cmd");
			if (StringUtils.isNotBlank(cmd)) {
				sql += " and nvl(t.CMD,'') like '%" + cmd + "%'";
			}

			String sDate = filter.get("sDate");
			if (StringUtils.isNotBlank(sDate)) {
				sql += " and t.CREATOR_TIME >= '" + sDate + "'";
			}
			String eDate = filter.get("eDate");
			if (StringUtils.isNotBlank(eDate)) {
				sql += " and t.CREATOR_TIME <= '" + eDate + "'";
			}
		}

		// 部门的过滤
		UserContext userContext = UserContextHolder.getLocalUserContext()
				.getUserContext();
		sql += " and t.dept_id in (" + userContext.getGrantedDepartmentsId()
				+ ",";
		sql += "'" + userContext.getSysDept().getDeptid() + "')";
		sql += " order by t.creator_time desc";

		//System.out.println("查询SQL语句:" + sql);

		return sql;
	}
	
	public ModelAndView exportStatManageView(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String strTradeType = request.getParameter("tradeType");
		request.setAttribute("tradeType", strTradeType);
		UserContext userContext = UserContextHolder.getLocalUserContext()
		.getUserContext();
		String loginer = userContext.getSysUser().getUserId();
		request.setAttribute("loginer", loginer);
		return new ModelAndView("sapss/export/exportStat/exportStatManage");
	}
	
	public ModelAndView exportBillExamManageView(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String strTradeType = request.getParameter("tradeType");
		request.setAttribute("tradeType", strTradeType);
		UserContext userContext = UserContextHolder.getLocalUserContext()
		.getUserContext();
		String loginer = userContext.getSysUser().getUserId();
		request.setAttribute("loginer", loginer);
		return new ModelAndView("sapss/export/exportBillExam/exportBillExamManage");
	}
	
	public ModelAndView exportBillExamQuery(HttpServletRequest request,HttpServletResponse response){
		return new ModelAndView("sapss/export/exportBillExam/exportBillExamQuery");
	}
	
	public ModelAndView createExportBillExam(HttpServletRequest request,
			HttpServletResponse response) throws IllegalAccessException,
			InvocationTargetException {

		// 贸易类型
		String tradeType = request.getParameter("tradeType");
		request.setAttribute("tradeType", tradeType);

		// 货币
		Map<String, DictionaryRow> CurrencyMap = SysCachePoolUtils
				.getDictTableGroup("BM_CURRENCY");
		Collection<DictionaryRow> Currency = CurrencyMap.values();
		request.setAttribute("Currency", Currency);

		request.setAttribute("isShow", "false");
		
		
		TExportBillExam main = new TExportBillExam();
		UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
		main.setExportBillExamId(CodeGenerator.getUUID());
		main.setCreatorTime(DateUtils.getCurrTime(2));
		main.setCreator(userContext.getSysUser().getUserId());
		main.setCreatorDept(userContext.getSysDept().getDeptid());
		request.setAttribute("main", main);
		exportService.updateExportBillExam(main);
		return new ModelAndView("sapss/export/exportBillExam/exportBillExamCreate");
	}
	
	public ModelAndView copyCreate(HttpServletRequest request,
			HttpServletResponse response) throws IllegalAccessException,
			InvocationTargetException,Exception {
		//
		String exportStateId = request.getParameter("exportBillExamId");		
		TExportBillExam main = new TExportBillExam();
		UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
		main.setExportBillExamId(CodeGenerator.getUUID());
		main.setCreatorTime(DateUtils.getCurrTime(2));
		main.setCreator(userContext.getSysUser().getUserId());
		main.setCreatorDept(userContext.getSysDept().getDeptid());
		exportService.copyCreate(main, exportStateId);
		request.setAttribute("main", main);
		exportService.updateExportBillExam(main);		
		request.setAttribute("main", main);		
		if (StringUtils.isNotBlank(main.getDeptId()))
		{   
			SysDept sysDept = sysDeptService.queryDeptById(main.getDeptId());
			request.setAttribute("selectId_dept", sysDept.getDeptid());
			request.setAttribute("selectText_dept", sysDept.getDeptname());
		}

		// 货币
		Map<String, DictionaryRow> CurrencyMap = SysCachePoolUtils
				.getDictTableGroup("BM_CURRENCY");
		Collection<DictionaryRow> Currency = CurrencyMap.values();
		request.setAttribute("Currency", Currency);
		request.setAttribute("isShow", "false");
		return new ModelAndView("sapss/export/exportBillExam/exportBillExamCreate");
	}

	public ModelAndView updateExportBillExamView(HttpServletRequest request,
			HttpServletResponse response) throws IllegalAccessException,
			InvocationTargetException,Exception {

		//
		String exportStateId = request.getParameter("exportBillExamId");
		Object export = exportService.getExportBillExam(exportStateId);
		TExportBillExam main = (TExportBillExam) export;
		request.setAttribute("main", main);
		
		if (StringUtils.isNotBlank(main.getDeptId()))
		{   
			SysDept sysDept = sysDeptService.queryDeptById(main.getDeptId());
			request.setAttribute("selectId_dept", sysDept.getDeptid());
			request.setAttribute("selectText_dept", sysDept.getDeptname());
		}

		// 货币
		Map<String, DictionaryRow> CurrencyMap = SysCachePoolUtils
				.getDictTableGroup("BM_CURRENCY");
		Collection<DictionaryRow> Currency = CurrencyMap.values();
		request.setAttribute("Currency", Currency);
		request.setAttribute("isShow", "1".equals(request
				.getParameter("isShow")));
		/*Set<ExportCcollect> exportCcollectSet =  exportService.initExportCcollect(exportStateId) ;
		JSONObject jsonObject = JSONObject.fromObject(exportCcollectSet);
		//JSONObject jsonObject = JSONObject.fromObject(d);
		String jsonText = jsonObject.toString();
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		response.getWriter().print(jsonText);*/
		return new ModelAndView("sapss/export/exportBillExam/exportBillExamCreate");
	}
	
	
	public void updateExportBillExam(HttpServletRequest request,
			HttpServletResponse response, TExportBillExam exportBill)
			throws IOException {
		exportBill.setIsAvailable("1");
		if (exportBill.getExportBillExamId() == null
				|| "".equals(exportBill.getExportBillExamId())) {
			UserContext userContext = UserContextHolder.getLocalUserContext()
					.getUserContext();
			exportBill.setExportBillExamId(CodeGenerator.getUUID());
			exportBill.setCreatorTime(DateUtils.getCurrTime(2));
			exportBill.setCreator(userContext.getSysUser().getUserId());
			exportBill.setCreatorDept(userContext.getSysDept().getDeptid());
		}
		//Set<ExportCcollect> exportCcollectSet = ExBeanUtils.bindModifyBoData(request.getParameterMap(), ExportCcollect.class);
		if(exportService.isExistInvNo(exportBill.getExportBillExamId(), exportBill.getInvNo())){
			throw new BusinessException("发票号重复，请更改！");
		}
		String collectId = exportBill.getCollectId();
		//出单审单时如果选择的收款单已和其他出单关联，不允许关联。
		if(StringUtils.isNotBlank(collectId)){
			String invNo = exportService.getInvNoByCollect(collectId);
			if(StringUtils.isNotBlank(invNo) && !invNo.equals(exportBill.getInvNo())){
				throw new BusinessException("收款单已和" + invNo +"的发票号有关联！请先删除关联");
			}
		}
		
		//出单的发票号填进ycollect
		String oldCollectId = request.getParameter("oldCollectId");
		if(StringUtils.isNotBlank(oldCollectId)){
			exportService.updateCollect(exportBill,oldCollectId);
		}else{
			if(StringUtils.isNotBlank(exportBill.getCollectId())){
				exportService.updateCollect(exportBill);
			}
		}
		
		if(!StringUtils.isNotBlank(exportBill.getCollectNo())){
			exportBill.setCollectId("");
		}
		exportService.updateExportBillExam(exportBill);
		//exportService.updateExportBillExam(exportBill, exportCcollectSet);
		
		
		JSONObject jo = new JSONObject();
		jo.put("returnMessage", "保存成功！");
		String jsontext = jo.toString();
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");

		this.operateSuccessfullyWithString(response, jsontext);
	}
	
	public ModelAndView dealPrintExportBillExam(HttpServletRequest request,
			HttpServletResponse response) throws IllegalAccessException,
			InvocationTargetException {

		//
		String exportBillExamId = request.getParameter("exportBillExamId");
		TExportBillExam main = exportService.getExportBillExam(exportBillExamId);
		request.setAttribute("deptName", sysDeptService.queryDeptById(main.getDeptId()).getDeptname());
		request.setAttribute("creator", sysUserService.queryUserByUserId(main.getCreator()).get(0).getRealName());
		main.setExamRecord(main.getExamRecord().replaceAll("\n","<br>"));
		exportJdbcService.assemExportBillExamPrint(main);
		request.setAttribute("main", main);
		return new ModelAndView("sapss/export/exportBillExam/print");
	}
	
	public void saveBillExamD(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String billExamId = request.getParameter("billExamId");
		String noticeNo = request.getParameter("noticeNo");
		String contractNo = request.getParameter("contractNo");
		String lcdpdaNo = request.getParameter("lcdpdaNo");
		String writeNo = request.getParameter("writeNo");
		String exportApplyId = request.getParameter("exportApplyId");
		boolean r = exportService.checkIsExitExportApply(billExamId,exportApplyId);
		if(r) throw new BusinessException("该出口货物通知单已存在！");
		TExportBillExamD d = new TExportBillExamD();
		UserContext userContext = UserContextHolder.getLocalUserContext()
		.getUserContext();
		d.setContractNo(contractNo);
		d.setCreator(userContext.getSysUser().getUserId());
		d.setCreatorTime(DateUtils.getCurrTime(2));
		d.setExportApplyId(exportApplyId);
		d.setExportBillExamDId(CodeGenerator.getUUID());
		d.setExportBillExamId(billExamId);
		d.setLcdpdaNo(lcdpdaNo);
		d.setNoticeNo(noticeNo);
		d.setWriteNo(writeNo);
		exportService.saveExportBillExamD(d);
		//初始化出单清款数据
		//Set<ExportCcollect> exportCcollectSet = exportService.initExportCcollect(billExamId);
		//JSONObject jsonObject = JSONObject.fromObject(exportCcollectSet);
		JSONObject jsonObject = JSONObject.fromObject(d);
		String jsonText = jsonObject.toString();

		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		response.getWriter().print(jsonText);
		
	}
	
	public void deleteBillExamD(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String billExamDId = request.getParameter("billExamDId");
		exportService.deleteExportBillExamD(billExamDId);
		JSONObject jsonObject = new JSONObject();
		String jsonText = jsonObject.toString();

		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		response.getWriter().print(jsonText);
	}

	public void deleteExportBillExam(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String exportBillExamID = request
				.getParameter("exportBillExamID");
		TExportBillExam stat = exportService
				.getExportBillExam(exportBillExamID);

		stat.setIsAvailable("0");
		//删除出仓时，删除与收款单的关联
		if(StringUtils.isNotBlank(stat.getCollectId())){
			exportService.delExportApplyNo(stat);
		}
		exportService.updateExportBillExam(stat);
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("returnMessage", "删除成功!");
		String jsontext = jsonObject.toString();
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		this.operateSuccessfullyWithString(response, jsontext);
	}

	public void queryExportBillExam(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		Map<String, String> filter = extractFR(request);
		String sql = getQueryExportBillExamSql(filter);
		String grid_columns = "EXPORT_BILL_EXAM_ID,EXPORT_APPLY_NO,CREATOR,CREATOR_DEPT," +
				"BILL_TYPE,INV_NO,EXAM_DATE,LCDPDA_NO,TOTAL,CURRENCY,BANK,IS_NEGOTIAT,BILL_SHIP_DATE,SHOULD_INCOME_DATE,MARK,DEPT_NAME";
		String grid_size = "10";

		request.setAttribute("grid_sql", sql);
		request.setAttribute("grid_columns", grid_columns);
		request.setAttribute("grid_size", grid_size);

		ExtComponentServlet extComponentServlet = new ExtComponentServlet();
		extComponentServlet.processGrid(request, response, true);
	}
	
	public void queryExportApply(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		String exportExamId  = request.getParameter("exportExamId");
		String sql = "select t.*,t.EXPORT_BILL_EXAM_D_ID as EXPORT_BILLD_ID from T_EXPORT_BILL_EXAM_D t where t.EXPORT_BILL_EXAM_ID='"+exportExamId+"'";
		String grid_columns = "EXPORT_BILLD_ID,EXPORT_BILL_EXAM_ID,CONTRACT_NO,WRITE_NO,EXPORT_APPLY_ID,CREATOR,CREATOR_TIME,NOTICE_NO,LCDPDA_NO";
		String grid_size = "5";
		request.setAttribute("grid_sql", sql);
		request.setAttribute("grid_columns", grid_columns);
		request.setAttribute("grid_size", grid_size);
		ExtComponentServlet extComponentServlet = new ExtComponentServlet();
		extComponentServlet.processGrid(request, response, true);
	}
	
	
	
	public String getQueryExportBillExamSql(Map<String, String> filter) {
		String sql = "SELECT distinct t.export_bill_exam_id as kk,t.*,d.dept_name FROM T_EXPORT_BILL_EXAM t "
			+ "left outer join T_EXPORT_BILL_EXAM_D t1 on t1.export_bill_exam_id=t.export_bill_exam_id "
			+ "left outer join t_export_apply ap on t1.export_apply_id=ap.export_apply_id "
			+ "left outer join t_contract_sales_info c on c.contract_sales_id=ap.contract_sales_id "
			+ "left outer join t_sys_dept d on d.dept_id=t.dept_id "
			+ "left outer join t_project_info p on p.project_id = c.project_id where t.is_available=1";
		if (filter != null && !filter.isEmpty()) {
			String deptCode = filter.get("deptCode");
			if (StringUtils.isNotBlank(deptCode)) {
				sql += " and d.ywbm_code like '%" + deptCode + "%'";
			}
			String lcdpdaNo = filter.get("lcdpdaNo");
			if (StringUtils.isNotBlank(lcdpdaNo)) {
				sql += " and t1.lcdpda_no like '%" + lcdpdaNo + "%'";
			}
			String exportApplyNo = filter.get("exportApplyNo");
			if (StringUtils.isNotBlank(exportApplyNo)) {
				sql += " and t.export_Apply_No like '%" + exportApplyNo + "%'";
			}
			String INVNO = filter.get("INVNO");
			if (StringUtils.isNotBlank(INVNO)) {
				sql += " and t.INV_NO like '%" + INVNO + "%'";
			}
			String contractNo = filter.get("contractNo");
			if (StringUtils.isNotBlank(contractNo)) {
				sql += " and c.contract_no like '%" + contractNo + "%'";
			}
			String sapOrderNo = filter.get("sapOrderNo");
			if (StringUtils.isNotBlank(sapOrderNo)) {
				sql += " and c.sap_order_no like '%" + sapOrderNo + "%'";
			}
			
			String billType = filter.get("billType");

			if (StringUtils.isNotBlank(billType)) {
				sql += " and t.BILL_TYPE like '%" + billType + "%'";
			}
			String writeNo = filter.get("writeNo");
			if (StringUtils.isNotBlank(writeNo)) {
				sql += " and t.write_No like '%" + writeNo + "%'";
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
		sql += " and t.dept_id in ( "
			+ userContext.getGrantedDepartmentsId() + ")";
		sql += " order by t.creator_time desc";
		return sql;
	}
	
	public void dealOutToExcel(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		String[] titles  = new String[]{"批次号","出口日期","数量(PRS）","单位","出口金额","币别","发票金额（进项）","税面额","核销单号","提单号","口岸","客户","换汇比","进仓日","申报退税日","最后申报日","备注"};

		ExcelObject excel = new ExcelObject(titles);
		Map<String, String> filter = extractFR(request);
		String sql = getQueryExportStatSql(filter);
		
		exportJdbcService.dealOutToExcel(sql ,excel);
		
		try{
			//response.reset();
	        response.setCharacterEncoding("utf-8");
	        response.setContentType("application/x-download;charset=utf-8");
	        response.setHeader("Content-Disposition", (new StringBuilder("attachment;filename=")).append(URLEncoder.encode("出货统计表.xls", "utf-8")).toString());
			excel.write(response.getOutputStream());
			response.flushBuffer();
			response.getOutputStream().close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	

}
