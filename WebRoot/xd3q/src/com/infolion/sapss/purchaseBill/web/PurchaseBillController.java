/*
 * @(#)purchaseBillController.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：王懿璞
 *  时　间：2009-6-12
 *  描　述：创建
 */

package com.infolion.sapss.purchaseBill.web;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

import com.infolion.platform.component.ExtComponentServlet;
import com.infolion.platform.component.workflow.WorkflowService;
import com.infolion.platform.component.workflow.ext.TaskInstanceContext;
import com.infolion.platform.console.org.domain.SysDept;
import com.infolion.platform.console.org.domain.SysUser;
import com.infolion.platform.console.org.service.SysDeptService;
import com.infolion.platform.console.org.service.SysUserService;
import com.infolion.platform.console.sys.context.UserContext;
import com.infolion.platform.console.sys.context.UserContextHolder;
import com.infolion.platform.console.workflow.dto.TaskHisDto;
import com.infolion.platform.console.workflow.service.SysWfUtilsService;
import com.infolion.platform.core.BusinessException;
import com.infolion.platform.core.ExceptionPostHandle;
import com.infolion.platform.core.web.BaseMultiActionController;
import com.infolion.platform.core.web.EnhanceMultiActionController;
import com.infolion.platform.util.DateUtils;
import com.infolion.sapss.common.ExcelObject;
import com.infolion.sapss.common.NumberUtils;
import com.infolion.sapss.common.web.CommonController;
import com.infolion.sapss.contract.domain.TContractSalesInfo;
import com.infolion.sapss.contract.service.ContractHibernateService;
import com.infolion.sapss.purchaseBill.domain.TPurchaseBill;
import com.infolion.sapss.purchaseBill.domain.TPurchaseBillMaterial;
import com.infolion.sapss.purchaseBill.service.PurchaseBillHibernateService;
import com.infolion.sapss.purchaseBill.service.PurchaseBillJdbcService;

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
public class PurchaseBillController extends EnhanceMultiActionController {
	private final Log log = LogFactory.getLog(PurchaseBillController.class);
	// 服务类注入
	@Autowired
	private PurchaseBillHibernateService purchaseBillHibernateService;

	public void setPurchaseBillService(PurchaseBillHibernateService purchaseBillService) {
		this.purchaseBillHibernateService = purchaseBillService;
	}

	@Autowired
	private PurchaseBillJdbcService purchaseBillJdbcService;

	public void setPurchaseBillJdbcService(PurchaseBillJdbcService purchaseBillService) {
		this.purchaseBillJdbcService = purchaseBillService;
	}

	@Autowired
	ContractHibernateService contractHibernateService;

	@Autowired
	SysDeptService sysDeptService;

	/**
	 * 默认方法
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 * @throws Exception
	 */
	public ModelAndView defaultMethod(HttpServletRequest request,
			HttpServletResponse response) throws IllegalAccessException,
			InvocationTargetException {
		return new ModelAndView();
	}
	
	public ModelAndView purchaseBillManageView(HttpServletRequest request,
			HttpServletResponse response) throws IllegalAccessException,
			InvocationTargetException {
		UserContext userContext = UserContextHolder.getLocalUserContext()
				.getUserContext();
		request.setAttribute("loginer", userContext.getSysUser().getUserId());
		return new ModelAndView("sapss/purchaseBill/purchaseBillManage");
	}
	
	/**
	 * 查询
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	public void queryPurchaseBill(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		Map<String, String> map = extractFR(request);

		String grid_sql = purchaseBillJdbcService.getQuerySql(map);
		String grid_columns = "PURCHASE_BILL_ID,PURCHASE_BILL_NO,CONTRACT_PURCHASE_ID,CONTRACT_PURCHASE_NO,TAX_NO,BILL_TO_PARTY," +
				"BILL_TO_PARTY_NAME,SAP_ORDER_NO,BILL_TIME,QUANTITY_TOTAL,PRICE_TOTAL,CMD,DEPT_ID,EXAMINE_STATE,EXAMINE_STATE_D_EXAMINE_STATE," +
				"APPLY_TIME,APPROVED_TIME,IS_AVAILABLE,CREATOR_TIME,CREATOR,DEPT_NAME";
				
		String grid_size = "10";

		request.setAttribute("grid_sql", grid_sql);
		request.setAttribute("grid_columns", grid_columns);
		request.setAttribute("grid_size", grid_size);

		ExtComponentServlet extComponentServlet = new ExtComponentServlet();
		extComponentServlet.processGrid(request, response, true);
	}

	/**
	 * 创建开票申请
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public ModelAndView newPurchaseBillCreate(HttpServletRequest request,
			HttpServletResponse response) throws IllegalAccessException,
			InvocationTargetException {
		purchaseBillCreate(request, response, "");
		specialDept(request, response);
		return new ModelAndView("sapss/purchaseBill/purchaseBillCreate");
	}
	

	private void purchaseBillCreate(HttpServletRequest request,
			HttpServletResponse response, String billType) {
		// 操作类型
		String operType = "101";
		request.setAttribute("save", operType.substring(0, 1).equals("0"));
		request.setAttribute("submit", operType.substring(1, 2).equals("0"));
		request.setAttribute("close", operType.substring(2, 3).equals("0"));

		UserContext userContext = UserContextHolder.getLocalUserContext()
				.getUserContext();

		// TPurchaseBill
		TPurchaseBill info = new TPurchaseBill();

		// 申报日期
		info.setApplyTime(DateUtils.getCurrDate(2));
		info.setBillTime(DateUtils.getCurrDate(2));
		info.setDeptId(userContext.getSysDept().getDeptid());
		info.setCreator(userContext.getSysUser().getUserId());
		info.setCreatorTime(DateUtils.getCurrTime(2));
		info.setIsAvailable("1"); // 可用标识
		info.setExamineState("1"); // 新增标识
		info.setPurchaseBillNo(this.purchaseBillJdbcService.getPurchaseBillNo());

		String purchaseBillId = this.purchaseBillHibernateService.newPurchaseBill(info);

		SysDept sysDept = sysDeptService.queryDeptById(info.getDeptId());
		request.setAttribute("sysDept", sysDept);
		request.setAttribute("main", info);
	}

	public void addDetail(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String purchaseBillId = request.getParameter("purchaseBillId");
		String purchaseRowsId = request.getParameter("purchaseRowsId");
		purchaseBillHibernateService.addDetail(purchaseBillId, purchaseRowsId);
	}
	
	private void purchaseBillCreateView(HttpServletRequest request,
			HttpServletResponse response) {

		// 贸易类型
		String tradeType = request.getParameter("tradeType");
		request.setAttribute("tradeType", tradeType);

		String purchaseBillId = request.getParameter("purchaseBillId");
		TPurchaseBill tPurchaseBill = purchaseBillHibernateService
				.getPurchaseBill(purchaseBillId);

		if(tPurchaseBill.getPriceTotal()!=null){
			request.setAttribute("priceTotal", NumberUtils.round(tPurchaseBill.getPriceTotal(), 2));
		}
		request.setAttribute("main", tPurchaseBill);

		// 操作类型
		String operType = request.getParameter("operType");
		if (operType.length() < 3)
			operType = "101";

		SysDept sysDept = sysDeptService.queryDeptById(tPurchaseBill.getDeptId());
		request.setAttribute("sysDept", sysDept);
		request.setAttribute("save", operType.substring(0, 1).equals("0"));
		request.setAttribute("submit", operType.substring(1, 2).equals("0"));
		request.setAttribute("close", operType.substring(2, 3).equals("0"));
		String taskType = request.getParameter("taskType");
		request.setAttribute("taskType", taskType);
	}

	/**
	 * 转到开票申请单内容页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public ModelAndView addPurchaseBillView(HttpServletRequest request,
			HttpServletResponse response) throws IllegalAccessException,
			InvocationTargetException {
		purchaseBillCreateView(request, response);
		specialDept(request, response);
		return new ModelAndView("sapss/purchaseBill/purchaseBillCreate");
	}

	private void specialDept(HttpServletRequest request,HttpServletResponse response){
		UserContext txt = UserContextHolder.getLocalUserContext().getUserContext();
		String composite = request.getParameter("from");
		String dept = txt.getSysDept().getDeptname();
		if(composite!=null &&composite.equals("composite")){
			if(dept.indexOf("综合")!=-1||dept.indexOf("信达诺财务部")!=-1){
				request.setAttribute("modifiable", true);
				return;
			}
		}
		request.setAttribute("modifiable", false);
	}
	
	public void queryMaterial(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String purchaseBillId = request.getParameter("purchaseBillId");

		String sql = "select t.* from T_purchase_BILL_MATERIAL t where 1 = 1";
		if (StringUtils.isNotBlank(purchaseBillId))
			sql += " and t.PURCHASE_BILL_ID = '" + purchaseBillId + "'";

		String grid_columns = "PURCHASE_BILL_MATE_ID,CONTRACT_NO, PURCHASE_BILL_ID, MATERIAL_CODE, MATERIAL_NAME, MATERIAL_UNIT, QUANTITY, CURRENCY, TOTAL_MONEY, PRICE, CMD";
		String grid_size = "10";
		request.setAttribute("grid_sql", sql);
		request.setAttribute("grid_columns", grid_columns);
		request.setAttribute("grid_size", grid_size);
		ExtComponentServlet extComponentServlet = new ExtComponentServlet();
		extComponentServlet.processGrid(request, response, true);
	}
 
	public ModelAndView toPurchaseBillCompositeQuery(HttpServletRequest request,
			HttpServletResponse response) throws IllegalAccessException{
		return new ModelAndView("sapss/purchaseBill/purchaseBillCompositeQuery");
	}

	public void updatePurchaseBill(HttpServletRequest request,
			HttpServletResponse response, TPurchaseBill tPurchaseBill)
			throws IOException {
		String purchaseBillId = request.getParameter("purchaseBillId");
		JSONObject jo = new JSONObject();

		List<BigDecimal> list = this.purchaseBillHibernateService
				.getPurchaseBillMtMoney(purchaseBillId);
		if (list != null && list.size() > 0) {
			BigDecimal quantityTotal = (BigDecimal) ((Map) list.get(0))
					.get("quantityTotal");
			BigDecimal priceTotal = (BigDecimal) ((Map) list.get(0))
					.get("priceTotal");
			tPurchaseBill.setQuantityTotal(quantityTotal.setScale(3,
					BigDecimal.ROUND_HALF_UP).doubleValue());
			tPurchaseBill.setPriceTotal(priceTotal.setScale(2,
					BigDecimal.ROUND_HALF_UP).doubleValue());
		}

		purchaseBillHibernateService.savePurchaseBill(tPurchaseBill);
		jo.put("returnMessage", "保存成功！");

		String jsontext = jo.toString();
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");

		this.operateSuccessfullyWithString(response, jsontext);
	}

	/**
	 * 保存开票申请单到数据库
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public void save(HttpServletRequest request, HttpServletResponse response,
			TPurchaseBill tPurchaseBill) throws IllegalAccessException,
			InvocationTargetException, IOException {

		String purchaseBillId = request.getParameter("purchaseBillId");
		response.setCharacterEncoding("GBK");
		response.setContentType("text ml; charset=GBK");
		System.out.println("ok");

		JSONObject jo = new JSONObject();

		if (StringUtils.isBlank(purchaseBillId)) {

			purchaseBillId = this.purchaseBillHibernateService
					.newPurchaseBill(tPurchaseBill);
			if (StringUtils.isBlank(purchaseBillId)) {
				jo.put("success", false);
				jo.put("purchaseBillId", purchaseBillId);
			} else {
				jo.put("success", true);
				jo.put("purchaseBillId", purchaseBillId);
			}

			System.out.println(purchaseBillId);
		} else {

			List<BigDecimal> list = this.purchaseBillHibernateService
				.getPurchaseBillMtMoney(purchaseBillId);
			if (list != null && list.size() > 0) {
				BigDecimal quantityTotal = (BigDecimal) ((Map) list.get(0))
						.get("quantityTotal");
				BigDecimal taxTotal = (BigDecimal) ((Map) list.get(0))
						.get("taxTotal");
				BigDecimal loanTotal = (BigDecimal) ((Map) list.get(0))
						.get("loanTotal");
				BigDecimal priceTotal = (BigDecimal) ((Map) list.get(0))
						.get("priceTotal");
				tPurchaseBill.setQuantityTotal(quantityTotal.setScale(3,
						BigDecimal.ROUND_HALF_UP).doubleValue());
				tPurchaseBill.setPriceTotal(priceTotal.setScale(2,
						BigDecimal.ROUND_HALF_UP).doubleValue());
			}

			purchaseBillHibernateService.updatePurchaseBill(tPurchaseBill);
			jo.put("success", true);
			jo.put("purchaseBillId", tPurchaseBill.getPurchaseBillId());
		}

		this.operateSuccessfullyWithString(response, jo.toString());
	}

	/**
	 * 删除开票申请单明细资料
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void delete(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// 贸易类型
		String tradeType = request.getParameter("tradeType");
		request.setAttribute("tradeType", tradeType);
		// 删除动作实现
		String purchaseBillId = (String) request.getParameter("purchaseBillId");
		int i = this.purchaseBillJdbcService.deletePurchaseBill(purchaseBillId);

		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		if (i > 0) {
			ExceptionPostHandle.generateInfoMessage(response, "删除成功");
		} else {
			ExceptionPostHandle.generateInfoMessage(response, "删除失败");
		}
	}

	/**
	 * 进入查询采购合同界面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ModelAndView selectSalesInfo(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String deptId = request.getParameter("deptId");
		String orderState = request.getParameter("orderState");
		String tradeType = request.getParameter("tradeType");
		request.setAttribute("deptId", deptId);
		request.setAttribute("orderState", orderState);
		request.setAttribute("tradeType", tradeType);
		return new ModelAndView("sapss/purchaseBill/selectSalesInfo");
	}

	/**
	 * 初始化开票申请单物料信息�
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void initPurchaseBillMt(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String contractPurchaseId = request.getParameter("contractPurchaseId");
		String purchaseBillId = request.getParameter("purchaseBillId");
		purchaseBillHibernateService.initPurchaseBillMt(purchaseBillId, contractPurchaseId);

		// 取得开票申请单
		TPurchaseBill tPurchaseBill = purchaseBillHibernateService
				.getPurchaseBill(purchaseBillId);
		JSONObject jo = new JSONObject();
		//jo.put("exportApplyNo", tPurchaseBill.getExportApplyNo().toString());
		//jo.put("sapOrderNo", tPurchaseBill.getSapOrderNo().toString());
		//jo.put("sapReturnNo", tPurchaseBill.getSapReturnNo().toString());
		//jo.put("taxTotal", tPurchaseBill.getTaxTotal().toString());
		jo.put("priceTotal", tPurchaseBill.getPriceTotal().toString());
		jo.put("quantityTotal", tPurchaseBill.getQuantityTotal().toString());
		jo.put("billToParty", tPurchaseBill.getBillToParty());
		jo.put("billToPartyName", tPurchaseBill.getBillToPartyName());
		//jo.put("loanTotal", tPurchaseBill.getLoanTotal().toString());
		String jsontext = jo.toString();
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		this.operateSuccessfullyWithString(response, jsontext);
	}

	/**
	 * 取得开票申请单列表数据
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void getPurchaseBillList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// 贸易类型
		String tradeType = request.getParameter("tradeType");
		String sql = "select * from t_BILL_APPLY_info where is_available = 1 and trade_type = '"
				+ tradeType + "'";
		String grid_columns = "BILL_APPLY_ID,CONTRACT_Sales_ID,TRADE_TYPE,PROJECT_NO,PROJECT_NAME,CONTRACT_GROUP_NO,CONTRACT_NO,SAP_ORDER_NO,BILL_APPLY_NO,creator,CREATOR_TIME,EXAMINE_STATE,IS_AVAILABLE";
		String grid_size = "10";
		request.setAttribute("grid_sql", sql);
		request.setAttribute("grid_columns", grid_columns);
		request.setAttribute("grid_size", grid_size);

		System.out.println(sql);

		ExtComponentServlet extComponentServlet = new ExtComponentServlet();
		extComponentServlet.processGrid(request, response, true);
	}

	/**
	 * 取得开票申请申请单下的物料明细资料
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */


	/**
	 * 删除开票申请单明细资料
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void deleteDetail(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String purchaseBillMateId = request.getParameter("purchaseBillMateId");
		purchaseBillHibernateService.deleteMaterial(purchaseBillMateId);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("returnMessage", "删除成功!");
		String jsontext = jsonObject.toString();
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		this.operateSuccessfullyWithString(response, jsontext);
	}

	/**
	 * 复制开票申请单明细资料
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void copyDetail(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String exportMateId = request.getParameter("exportMateId");
		purchaseBillHibernateService.copyMaterial(exportMateId);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("returnMessage", "复制成功!");
		String jsontext = jsonObject.toString();
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		this.operateSuccessfullyWithString(response, jsontext);
	}

	/**
	 * 修改开票申请单物料信息
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void updatePurchaseBillMateriel(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String exportMateId = request.getParameter("purchaseBillMateId");
		String colName = request.getParameter("colName");
		String colValue = extractFR(request, "colValue");
		JSONObject jsonObject = new JSONObject();
		try {
			purchaseBillJdbcService.updateReceiptMateriel(exportMateId, colName,
					colValue);
			// 回调更新总额
			// if (colName.equals("QUANTITY") || colName.equals("TOTAL_MONEY")
			// || colName.equals("RATE")) {
			// TPurchaseBillMaterial tPurchaseBillMaterial = purchaseBillHibernateService
			// .getPurchaseBillMaterial(exportMateId);
			// jsonObject.put("total", tPurchaseBillMaterial.getTotalMoney());
			// }
			jsonObject.put("stateCode", "1");
			response.getWriter().write("{success:true");
		} catch (Exception e) {
			jsonObject.put("stateCode", "0");
			response.getWriter().write("{success:false,error:'更新信息失败");
		}
		String jsontext = jsonObject.toString();
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		this.operateSuccessfullyWithString(response, jsontext);
	}

	/**
	 * 查询采购合同物料
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	public void querySalesMateriel(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		String contractSalesId = request.getParameter("contractSalesId");
		String tradeType = request.getParameter("tradeType");
		String materialCode = request.getParameter("materialCode");
		String materialName = extractFR(request, "materialName");
		Map<String, String> filter = new HashMap();

		filter.put("contractSalesId", contractSalesId);
		filter.put("tradeType", tradeType);
		filter.put("materialCode", materialCode);
		filter.put("materialName", materialName);

		String sql = purchaseBillJdbcService.getQuerySalesMaterielSql(filter);
		String grid_columns = "SALES_ROWS_ID,LINES,EKPO_MATNR,EKPO_TXZ01,EKPO_MEINS,EKPO_MENGE,FLOW_NO,TAXES,TOTAL_TAXES";
		String grid_size = "20";

		System.out.println(sql);

		request.setAttribute("grid_sql", sql);
		request.setAttribute("grid_columns", grid_columns);
		request.setAttribute("grid_size", grid_size);

		ExtComponentServlet extComponentServlet = new ExtComponentServlet();
		extComponentServlet.processGrid(request, response, true);
	}

	/**
	 * 提交流程审批
	 * 
	 * @param request
	 * @param response
	 * @param PurchaseBill
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws IOException
	 * @throws Exception
	 */
	public void submitWorkflow(HttpServletRequest request,
			HttpServletResponse response, TPurchaseBill tPurchaseBill)
			throws IllegalAccessException, InvocationTargetException,
			IOException, Exception {
		String taskId = request.getParameter("workflowTaskId");

		String rtn = this.purchaseBillHibernateService.verifyFilds(taskId,
				tPurchaseBill);
		if (!"".equals(rtn)) {
			throw new BusinessException(rtn);
		}

		String msg = "";

		tPurchaseBill.setApprovedTime(DateUtils.getCurrTime(2));
		UserContext userContext = UserContextHolder.getLocalUserContext()
				.getUserContext();
		// 业务描述信息
		tPurchaseBill.setWorkflowBusinessNote(userContext.getSysDept()
				.getDeptname()
				+ "|"
				+ userContext.getSysUser().getRealName()
				+ "|"
				+ tPurchaseBill.getPurchaseBillNo());

		String doWorkflow = request.getParameter("doWorkflow");
		if (doWorkflow == null && !"mainForm".equals(doWorkflow)) {
			String workflowLeaveTransitionName = request
					.getParameter("workflowLeaveTransitionName");
			String workflowExamine = request.getParameter("workflowExamine");
			tPurchaseBill.setWorkflowExamine(workflowExamine);
			tPurchaseBill
					.setWorkflowLeaveTransitionName(workflowLeaveTransitionName);
		}

		// 取得工作流节点名称

		JSONObject jo = new JSONObject();
		jo = this.purchaseBillJdbcService.submitWorkflow(tPurchaseBill, taskId);

		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");

		this.operateSuccessfullyWithString(response, jo.toString());
	}

	/**
	 * 开票申请单审批界面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public ModelAndView purchaseBillExamine(HttpServletRequest request,
			HttpServletResponse response) throws IllegalAccessException,
			InvocationTargetException {
		String purchaseBillId = request.getParameter("businessRecordId");
		String tradeType = request.getParameter("tradeType");
		String taskId = request.getParameter("taskId");

		TaskInstanceContext taskInstanceContext = WorkflowService
				.getTaskInstanceContext(taskId);
		String taskName = taskInstanceContext.getTaskName();
		String taskType = "";
		String isPrintHidden = "true";
		if (taskName.indexOf("开票，并录入票号")>0) {
			request.setAttribute("printUrl", "purchaseBillController.spr?action=dealPrint&purchaseBillId="+purchaseBillId);
			isPrintHidden = "false";
			taskType = "1";
		}
		request.setAttribute("isPrintHidden", isPrintHidden);
		request.setAttribute("businessRecordId", purchaseBillId);
		request.setAttribute("taskId", taskId);

		request.setAttribute("businessRecordId", purchaseBillId);
		TPurchaseBill tPurchaseBill = this.purchaseBillHibernateService
				.getPurchaseBill(purchaseBillId);

		UserContext userContext = UserContextHolder.getLocalUserContext()
				.getUserContext();
		Boolean modify = false;
		// 创建者能修改：
		if (tPurchaseBill.getCreator()
				.equals(userContext.getSysUser().getUserId())) {
			modify = true;
		}

		request.setAttribute("iframeSrc",
				"purchaseBillController.spr?action=examinePurchaseBillView&tradeType="
						+ tradeType + "&operate=iframe&purchaseBillId="
						+ purchaseBillId + "&modify=" + modify + "&taskType="
						+ taskType);

		request.setAttribute("submitUrl",
				"purchaseBillController.spr?action=submitWorkflow");

		System.out.println("businessRecordId：" + purchaseBillId);

		return new ModelAndView("sapss/workflow/commonProcessPage");
	}

	/**
	 * 提交并保存开票申请单
	 * 
	 * @param request
	 * @param response
	 * @param tPurchaseBill
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws IOException
	 * @throws Exception
	 */
	public void submitAndSavePurchaseBill(HttpServletRequest request,
			HttpServletResponse response, TPurchaseBill tPurchaseBill)
			throws IllegalAccessException, InvocationTargetException,
			IOException, Exception {
		String taskId = request.getParameter("workflowTaskId");
		System.out.println("taskId : " + taskId);
		purchaseBillHibernateService.submitAndSavePurchaseBill(taskId, tPurchaseBill);
		JSONObject jo = new JSONObject();
		jo.put("returnMessage", "提交成功！");
		String jsontext = jo.toString();
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		this.operateSuccessfullyWithString(response, jsontext);
	}

	/**
	 * 进入开票申请单流程审批界面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ModelAndView examinePurchaseBillView(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		// 贸易类型
		String tradeType = request.getParameter("tradeType");
		request.setAttribute("tradeType", tradeType);

		String purchaseBillId = request.getParameter("purchaseBillId");
		String modify = request.getParameter("modify");

		TPurchaseBill tPurchaseBill = purchaseBillHibernateService
				.getPurchaseBill(purchaseBillId);
		if(tPurchaseBill.getPriceTotal()!=null){
			request.setAttribute("priceTotal", NumberUtils.round(tPurchaseBill.getPriceTotal(), 2));
		}
		request.setAttribute("main", tPurchaseBill);

		// 操作类型
		String operType = "000";
		if (modify.equals("true"))
			operType = "100";

		request.setAttribute("save", operType.substring(0, 1).equals("0"));
		request.setAttribute("submit", operType.substring(1, 2).equals("0"));
		request.setAttribute("close", operType.substring(2, 3).equals("0"));
		//System.out.println("operType = " + operType);

		//String strContractSalesId = tPurchaseBill.getContractSalesId();

		SysDept sysDept = sysDeptService.queryDeptById(tPurchaseBill.getDeptId());
		request.setAttribute("sysDept", sysDept);

		//String contractSalesId = tPurchaseBill.getContractSalesId();
		String ContractNo = "";
		/*if (StringUtils.isNotBlank(contractSalesId)) { // 取得销售订单
			TContractSalesInfo tContractSalesInfo = contractHibernateService
					.getContractSalesInfo(contractSalesId);
			ContractNo = tContractSalesInfo.getContractNo();
		}
		request.setAttribute("contractNo", ContractNo);
*/
		String taskType = request.getParameter("taskType");
		
		request.setAttribute("taskType", taskType);
		//specialDept(request, response);
		if("1".equals(taskType)){
			request.setAttribute("modifiable", true);
		}
		else request.setAttribute("modifiable", false);
		return new ModelAndView("sapss/purchaseBill/purchaseBillCreate");

	}

	/*
	 * 进入总的开票申请单查询界面
	 * 
	 * @param request @param response @return @throws IOException
	 */
	public ModelAndView archPurchaseBillManageView(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		
		UserContext userContext = UserContextHolder.getLocalUserContext()
				.getUserContext();
		request.setAttribute("loginer", userContext.getSysUser().getUserId());
		return new ModelAndView("sapss/purchaseBill/Archives/archPurchaseBillManage");
	}

	
	public ModelAndView billInfoView(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		return new ModelAndView("sapss/purchaseBill/billInfo");
	}
	
	public void queryBillInfo(HttpServletRequest request, HttpServletResponse response)
	throws IOException, ServletException {
		Map<String, String> filter = extractFR(request);
//		Map<String, String> filter = shipNotifyJdbcService.getFilter(map);
		String sql = purchaseBillJdbcService.getQueryBillInfoSql(filter);
		String grid_columns = "DEPT_NAME,PROJECT_NO,PROJECT_NAME,MATERIAL_NAME,MATERIAL_UNIT,QUANTITY,PRICE,LOAN_MONEY,TAX,TOTAL_MONEY,APPLY_TIME,APPROVED_TIME,CMD";
		String grid_size = "10";
		request.setAttribute("grid_sql", sql);
		request.setAttribute("grid_columns", grid_columns);
		request.setAttribute("grid_size", grid_size);

		ExtComponentServlet extComponentServlet = new ExtComponentServlet();
		extComponentServlet.processGrid(request, response, true);
    }
	
	public void saveSapInfo(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String purchaseBillId = request.getParameter("purchaseBillId");
		String taxNo = request.getParameter("taxNo");
		this.purchaseBillJdbcService.saveSapInfo(purchaseBillId,taxNo);
		JSONObject jo = new JSONObject();
		jo.put("returnMessage", "保存成功！");
		String jsontext = jo.toString();
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		this.operateSuccessfullyWithString(response, jsontext);
	}
	@Autowired
	private SysWfUtilsService sysWfUtilsService;
	@Autowired
	private SysUserService sysUserService;
	public void setSysWfUtilsService(SysWfUtilsService sysWfUtilsService) {
		this.sysWfUtilsService = sysWfUtilsService;
	}
	public void setSysUserService(SysUserService sysUserService) {
		this.sysUserService = sysUserService;
	}
	public ModelAndView dealPrint(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String purchaseBillId = request.getParameter("purchaseBillId");
		TPurchaseBill bill = purchaseBillHibernateService.getPurchaseBill(purchaseBillId);
		List<TPurchaseBillMaterial> melist = purchaseBillJdbcService.getPurchaseBillMaterial(purchaseBillId);
		List<TaskHisDto> hisList = sysWfUtilsService.queryTaskHisList(purchaseBillId);
		SysUser user = sysUserService.queryUserById(bill.getCreator(),bill.getDeptId());
	    request.setAttribute("meListCount", melist.size());
	    request.setAttribute("deptName", user.getDeptName());
		request.setAttribute("creator", user.getRealName());
		for(Iterator<TaskHisDto> it = hisList.iterator();it.hasNext();){
			TPurchaseBillMaterial me = new TPurchaseBillMaterial();
    		TaskHisDto dto = it.next();
    		me.setMaterialCode(dto.getTaskName());
    		me.setMaterialName(dto.getExaminePerson());
    		me.setMaterialUnit(dto.getExamine());
    		me.setQuantity(0d);
    		me.setTotalMoney(0d);
    		me.setPurchaseBillId("fuck");
    		melist.add(me);
    	}
		request.setAttribute("main", bill);
		request.setAttribute("meList", melist);
		
		return new ModelAndView("sapss/purchaseBill/print");
	}
	
}