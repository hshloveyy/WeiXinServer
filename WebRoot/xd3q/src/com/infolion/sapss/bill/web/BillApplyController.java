/*
 * @(#)billApplyController.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：王懿璞
 *  时　间：2009-6-12
 *  描　述：创建
 */

package com.infolion.sapss.bill.web;

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
import com.infolion.platform.util.DateUtils;
import com.infolion.sapss.bill.domain.TBillApply;
import com.infolion.sapss.bill.domain.TBillApplyMaterial;
import com.infolion.sapss.bill.service.BillApplyHibernateService;
import com.infolion.sapss.bill.service.BillApplyJdbcService;
import com.infolion.sapss.common.ExcelObject;
import com.infolion.sapss.common.NumberUtils;
import com.infolion.sapss.contract.domain.TContractSalesInfo;
import com.infolion.sapss.contract.service.ContractHibernateService;

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
public class BillApplyController extends BaseMultiActionController {
	private final Log log = LogFactory.getLog(BillApplyController.class);
	// 服务类注入
	@Autowired
	private BillApplyHibernateService billApplyHibernateService;

	public void setBillApplyService(BillApplyHibernateService billApplyService) {
		this.billApplyHibernateService = billApplyService;
	}

	@Autowired
	private BillApplyJdbcService billApplyJdbcService;

	public void setBillApplyJdbcService(BillApplyJdbcService billApplyService) {
		this.billApplyJdbcService = billApplyService;
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

	/**
	 * 新增开票申请明细资料
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public void addDetail(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String billApplyId = request.getParameter("billApplyId");
		String salesRowsId = request.getParameter("salesRowsId");
		billApplyHibernateService.addDetail(billApplyId, salesRowsId);
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
	public ModelAndView newBillApplyCreate(HttpServletRequest request,
			HttpServletResponse response) throws IllegalAccessException,
			InvocationTargetException {
		billApplyCreate(request, response, "");
		specialDept(request, response);
		return new ModelAndView("sapss/bill/billApplyCreate");
	}

	public ModelAndView newAgentBillApplyCreate(HttpServletRequest request,
			HttpServletResponse response) throws IllegalAccessException,
			InvocationTargetException {
		billApplyCreate(request, response, "2");
		specialDept(request, response);
		return new ModelAndView("sapss/bill/agentBillApplyCreate");
	}

	private void billApplyCreate(HttpServletRequest request,
			HttpServletResponse response, String billType) {
		// 贸易类型
		String tradeType = request.getParameter("tradeType");
		request.setAttribute("tradeType", tradeType);

		// 操作类型
		String operType = "101";
		request.setAttribute("save", operType.substring(0, 1).equals("0"));
		request.setAttribute("submit", operType.substring(1, 2).equals("0"));
		request.setAttribute("close", operType.substring(2, 3).equals("0"));

		System.out.println("operType = " + operType);

		UserContext userContext = UserContextHolder.getLocalUserContext()
				.getUserContext();

		// TBillApply
		TBillApply info = new TBillApply();

		// 申报日期
		info.setApplyTime(DateUtils.getCurrDate(2));
		info.setBillTime(DateUtils.getCurrDate(2));
		info.setDeptId(userContext.getSysDept().getDeptid());
		info.setCreator(userContext.getSysUser().getUserId());
		info.setCreatorTime(DateUtils.getCurrTime(2));
		info.setIsAvailable("1"); // 可用标识
		info.setExamineState("1"); // 新增标识
		info.setBillType(billType); // 增值税票
		info.setTradeType(tradeType); // 贸易类型
		info.setBillApplyNo(this.billApplyJdbcService.getBillApplyNo());

		String billApplyId = this.billApplyHibernateService.newBillApply(info);

		System.out.print(info.getBillApplyNo());

		SysDept sysDept = sysDeptService.queryDeptById(info.getDeptId());
		request.setAttribute("sysDept", sysDept);

		request.setAttribute("main", info);

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
	public ModelAndView addBillApplyView(HttpServletRequest request,
			HttpServletResponse response) throws IllegalAccessException,
			InvocationTargetException {
		billApplyCreateView(request, response);
		specialDept(request, response);
		return new ModelAndView("sapss/bill/billApplyCreate");
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
	public ModelAndView addAgentBillApplyView(HttpServletRequest request,
			HttpServletResponse response) throws IllegalAccessException,
			InvocationTargetException {
		billApplyCreateView(request, response);
		specialDept(request, response);
		return new ModelAndView("sapss/bill/agentBillApplyCreate");
	}
	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView view_business(HttpServletRequest request,HttpServletResponse response){
		billApplyCreateView(request, response);
		specialDept(request, response);
		TBillApply bill =(TBillApply)request.getAttribute("main");
		if("1".equals(bill.getBillType()))
			return new ModelAndView("sapss/bill/billApplyCreate");
		else 
			return new ModelAndView("sapss/bill/agentBillApplyCreate");
	}
	/**
	 * 
	 */
	private void specialDept(HttpServletRequest request,HttpServletResponse response){
		UserContext txt = UserContextHolder.getLocalUserContext().getUserContext();
		String composite = request.getParameter("from");
		String dept = txt.getSysDept().getDeptname();
		if(composite!=null &&composite.equals("composite")){
			if(dept.equals("财务部")||dept.indexOf("综合")!=-1||dept.indexOf("信达诺财务部")!=-1||dept.indexOf("信达石油财务部")!=-1){
				request.setAttribute("modifiable", true);
				return;
			}
		}
		request.setAttribute("modifiable", false);
	}
	/**
	 * 
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	public void saveSapInfo(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String billApplyId = request.getParameter("billApplyId");
		String sapBillNo = request.getParameter("sapBillNo");
		String taxNo = request.getParameter("taxNo");
		this.billApplyJdbcService.saveSapInfo(billApplyId,sapBillNo,taxNo);
		JSONObject jo = new JSONObject();
		jo.put("returnMessage", "保存成功！");
		String jsontext = jo.toString();
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		this.operateSuccessfullyWithString(response, jsontext);
	}
	private void billApplyCreateView(HttpServletRequest request,
			HttpServletResponse response) {

		// 贸易类型
		String tradeType = request.getParameter("tradeType");
		request.setAttribute("tradeType", tradeType);

		String billApplyId = request.getParameter("billApplyId");
		TBillApply tBillApply = billApplyHibernateService
				.getBillApply(billApplyId);

		if(tBillApply.getLoanTotal()!=null){
			request.setAttribute("loanTotal", NumberUtils.round(tBillApply.getLoanTotal(), 2));
		}
		if(tBillApply.getTaxTotal()!=null){
			request.setAttribute("taxTotal", NumberUtils.round(tBillApply.getTaxTotal(), 2));
		}
		if(tBillApply.getPriceTotal()!=null){
			request.setAttribute("priceTotal", NumberUtils.round(tBillApply.getPriceTotal(), 2));
		}
		request.setAttribute("main", tBillApply);

		// 操作类型
		String operType = request.getParameter("operType");
		if (operType.length() < 3)
			operType = "101";

		SysDept sysDept = sysDeptService.queryDeptById(tBillApply.getDeptId());
		request.setAttribute("sysDept", sysDept);
		request.setAttribute("save", operType.substring(0, 1).equals("0"));
		request.setAttribute("submit", operType.substring(1, 2).equals("0"));
		request.setAttribute("close", operType.substring(2, 3).equals("0"));
		//System.out.println("operType = " + operType);

		String contractSalesId = tBillApply.getContractSalesId();
		String ContractNo = "";
		if (StringUtils.isNotBlank(contractSalesId)) { // 取得销售订单
			TContractSalesInfo tContractSalesInfo = contractHibernateService
					.getContractSalesInfo(contractSalesId);
			ContractNo = tContractSalesInfo.getContractNo();
		}
		request.setAttribute("contractNo", ContractNo);

		String taskType = request.getParameter("taskType");
		request.setAttribute("taskType", taskType);
	}

	/**
	 * 转到开票申请单列表页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public ModelAndView billApplyManageView(HttpServletRequest request,
			HttpServletResponse response) throws IllegalAccessException,
			InvocationTargetException {
		// 贸易类型
		String tradeType = request.getParameter("tradeType");
		request.setAttribute("tradeType", tradeType);

		UserContext userContext = UserContextHolder.getLocalUserContext()
				.getUserContext();
		request.setAttribute("loginer", userContext.getSysUser().getUserId());
		return new ModelAndView("sapss/bill/billApplyManage");
	}

	public ModelAndView agentBillApplyManageView(HttpServletRequest request,
			HttpServletResponse response) throws IllegalAccessException,
			InvocationTargetException {
		// 贸易类型
		String tradeType = request.getParameter("tradeType");
		request.setAttribute("tradeType", tradeType);

		UserContext userContext = UserContextHolder.getLocalUserContext()
				.getUserContext();
		request.setAttribute("loginer", userContext.getSysUser().getUserId());
		return new ModelAndView("sapss/bill/agentBillApplyManage");
	}
	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IllegalAccessException
	 */
	public ModelAndView toAgentBillApplyCompositeQuery(HttpServletRequest request,
			HttpServletResponse response) throws IllegalAccessException{
		return new ModelAndView("sapss/bill/agentBillApplyCompositeQuery");
	}
	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IllegalAccessException
	 */
	public ModelAndView toBillApplyCompositeQuery(HttpServletRequest request,
			HttpServletResponse response) throws IllegalAccessException{
		return new ModelAndView("sapss/bill/billApplyCompositeQuery");
	}
	/**
	 * 保存开票申请申请单
	 * 
	 * @param request
	 * @param response
	 * @param tExportApply
	 * @throws IOException
	 */
	public void updateBillApply(HttpServletRequest request,
			HttpServletResponse response, TBillApply tBillApply)
			throws IOException {
		String billApplyId = request.getParameter("billApplyId");
		JSONObject jo = new JSONObject();
		
		if(("5".equals(tBillApply.getTradeType())||"6".equals(tBillApply.getTradeType()))
				&&billApplyJdbcService.checkAgentQuantity(tBillApply.getContractSalesId())<0)
			throw new com.infolion.platform.dpframework.core.BusinessException("该销售订单开票数量已完成！请确认！");

		List<BigDecimal> list = this.billApplyHibernateService
				.getBillApplyMtMoney(billApplyId);
		if (list != null && list.size() > 0) {
			BigDecimal quantityTotal = (BigDecimal) ((Map) list.get(0))
					.get("quantityTotal");
			BigDecimal taxTotal = (BigDecimal) ((Map) list.get(0))
					.get("taxTotal");
			BigDecimal loanTotal = (BigDecimal) ((Map) list.get(0))
					.get("loanTotal");
			BigDecimal priceTotal = (BigDecimal) ((Map) list.get(0))
					.get("priceTotal");
			tBillApply.setQuantityTotal(quantityTotal.setScale(3,
					BigDecimal.ROUND_HALF_UP).doubleValue());
			tBillApply.setTaxTotal(taxTotal.setScale(2,
					BigDecimal.ROUND_HALF_UP).doubleValue());
			tBillApply.setLoanTotal(loanTotal.setScale(2,
					BigDecimal.ROUND_HALF_UP).doubleValue());
			tBillApply.setPriceTotal(priceTotal.setScale(2,
					BigDecimal.ROUND_HALF_UP).doubleValue());
		}

		billApplyHibernateService.saveBillApply(tBillApply);
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
			TBillApply tBillApply) throws IllegalAccessException,
			InvocationTargetException, IOException {

		String billApplyId = request.getParameter("billApplyId");
		response.setCharacterEncoding("GBK");
		response.setContentType("text ml; charset=GBK");
		System.out.println("ok");

		JSONObject jo = new JSONObject();

		if (StringUtils.isBlank(billApplyId)) {

			billApplyId = this.billApplyHibernateService
					.newBillApply(tBillApply);
			if (StringUtils.isBlank(billApplyId)) {
				jo.put("success", false);
				jo.put("billApplyId", billApplyId);
			} else {
				jo.put("success", true);
				jo.put("billApplyId", billApplyId);
			}

			System.out.println(billApplyId);
		} else {

			List<BigDecimal> list = this.billApplyHibernateService
				.getBillApplyMtMoney(billApplyId);
			if (list != null && list.size() > 0) {
				BigDecimal quantityTotal = (BigDecimal) ((Map) list.get(0))
						.get("quantityTotal");
				BigDecimal taxTotal = (BigDecimal) ((Map) list.get(0))
						.get("taxTotal");
				BigDecimal loanTotal = (BigDecimal) ((Map) list.get(0))
						.get("loanTotal");
				BigDecimal priceTotal = (BigDecimal) ((Map) list.get(0))
						.get("priceTotal");
				tBillApply.setQuantityTotal(quantityTotal.setScale(3,
						BigDecimal.ROUND_HALF_UP).doubleValue());
				tBillApply.setTaxTotal(taxTotal.setScale(2,
						BigDecimal.ROUND_HALF_UP).doubleValue());
				tBillApply.setLoanTotal(loanTotal.setScale(2,
						BigDecimal.ROUND_HALF_UP).doubleValue());
				tBillApply.setPriceTotal(priceTotal.setScale(2,
						BigDecimal.ROUND_HALF_UP).doubleValue());
			}

			billApplyHibernateService.updateBillApply(tBillApply);
			jo.put("success", true);
			jo.put("billApplyId", tBillApply.getBillApplyId());
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
		String billApplyId = (String) request.getParameter("billApplyId");
		int i = this.billApplyJdbcService.deleteBillApply(billApplyId);

		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		if (i > 0) {
			ExceptionPostHandle.generateInfoMessage(response, "删除成功");
		} else {
			ExceptionPostHandle.generateInfoMessage(response, "删除失败");
		}
	}

	/**
	 * 查询
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	public void queryBillApply(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		// 贸易类型
		String tradeType = request.getParameter("tradeType");
		request.setAttribute("tradeType", tradeType);
		// 票据类型 1-增值税票 2-普通发票
		String billType = request.getParameter("billType");
		request.setAttribute("billType", billType);
		Map<String, String> map = extractFR(request);

		String grid_sql = billApplyJdbcService.getQuerySql(map);
		String grid_columns = "BILL_APPLY_ID, BILL_APPLY_NO, BILL_TO_PARTY,"
				+ " CONTRACT_NO,BILL_TO_PARTY_NAME, SAP_BILL_NO, SAP_ORDER_NO, SAP_RETURN_NO, "
				+ "CONTRACT_SALES_ID, TAX_NO, BILL_TYPE, BILL_TIME, "
				+ "QUANTITY_TOTAL, TAX_TOTAL, LOAN_TOTAL, PRICE_TOTAL, "
				+ "CMD, DEPT_ID, EXAMINE_STATE, EXAMINE_STATE_D_EXAMINE_STATE, "
				+ "APPLY_TIME, APPROVED_TIME, "
				+ "IS_AVAILABLE, CREATOR_TIME, CREATOR, EXPORT_APPLY_NO"
				+ ",REAL_NAME,DEPT_NAME";
		String grid_size = "10";

		request.setAttribute("grid_sql", grid_sql);
		request.setAttribute("grid_columns", grid_columns);
		request.setAttribute("grid_size", grid_size);

		ExtComponentServlet extComponentServlet = new ExtComponentServlet();
		extComponentServlet.processGrid(request, response, true);
	}

	/**
	 * 萃取URL参数
	 * 
	 * @param req
	 * @param parm
	 * @return parm对应的参数值
	 */
	private String extractFR(HttpServletRequest req, String parm) {
		try {
			String wait = java.net.URLDecoder.decode(req.getQueryString(),
					"UTF-8");
			int pos = wait.indexOf(parm) + parm.length() + 1;
			wait = wait.substring(pos);
			pos = wait.indexOf("&");
			if (pos != -1) {
				wait = wait.substring(0, pos);
			}
			return wait;
		} catch (UnsupportedEncodingException e) {
		}
		return null;
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
		return new ModelAndView("sapss/bill/selectSalesInfo");
	}

	/**
	 * 初始化开票申请单物料信息�
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void initBillApplyMt(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String shipNo = request.getParameter("shipNo");
		String contractNo = request.getParameter("contractNo");
		String billApplyId = request.getParameter("billApplyId");
		String receiptTime = request.getParameter("receiptTime");
		billApplyHibernateService.initBillApplyMt(billApplyId, contractNo,
				shipNo,receiptTime);

		// 取得开票申请单
		TBillApply tBillApply = billApplyHibernateService
				.getBillApply(billApplyId);
		JSONObject jo = new JSONObject();
		jo.put("exportApplyNo", tBillApply.getExportApplyNo().toString());
		jo.put("sapOrderNo", tBillApply.getSapOrderNo().toString());
		jo.put("sapReturnNo", tBillApply.getSapReturnNo().toString());
		jo.put("taxTotal", tBillApply.getTaxTotal().toString());
		jo.put("priceTotal", tBillApply.getPriceTotal().toString());
		jo.put("quantityTotal", tBillApply.getQuantityTotal().toString());
		jo.put("loanTotal", tBillApply.getLoanTotal().toString());
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
	public void getBillApplyList(HttpServletRequest request,
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
	public void queryMaterial(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String billApplyId = request.getParameter("billApplyId");

		TBillApply tBillApply = billApplyHibernateService
				.getBillApply(billApplyId);

		String sql = "select t.* from t_BILL_APPLY_material t where 1 = 1";
		if (StringUtils.isNotBlank(billApplyId))
			sql += " and t.BILL_APPLY_ID = '" + billApplyId + "'";

		String grid_columns = "EXPORT_MATE_ID,CONTRACT_NO,CONTRACT_SALES_ID, BILL_APPLY_ID, MATERIAL_CODE, MATERIAL_NAME, MATERIAL_UNIT, QUANTITY, LOAN_MONEY, TAX, TAX_RATE,RATE, CURRENCY, TOTAL_MONEY, PRICE, CMD,RECEIPTTIME";

		String grid_size = "50";
		request.setAttribute("grid_sql", sql);
		request.setAttribute("grid_columns", grid_columns);
		request.setAttribute("grid_size", grid_size);

		ExtComponentServlet extComponentServlet = new ExtComponentServlet();
		extComponentServlet.processGrid(request, response, true);
	}

	/**
	 * 删除开票申请单明细资料
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void deleteDetail(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String exportMateId = request.getParameter("exportMateId");
		billApplyHibernateService.deleteMaterial(exportMateId);
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
		billApplyHibernateService.copyMaterial(exportMateId);
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
	public void updateBillApplyMateriel(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String exportMateId = request.getParameter("exportMateId");
		String colName = request.getParameter("colName");
		String colValue = extractFR(request, "colValue");
		JSONObject jsonObject = new JSONObject();
		try {
			billApplyJdbcService.updateReceiptMateriel(exportMateId, colName,
					colValue);
			// 回调更新总额
			// if (colName.equals("QUANTITY") || colName.equals("TOTAL_MONEY")
			// || colName.equals("RATE")) {
			// TBillApplyMaterial tBillApplyMaterial = billApplyHibernateService
			// .getBillApplyMaterial(exportMateId);
			// jsonObject.put("total", tBillApplyMaterial.getTotalMoney());
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

		String sql = billApplyJdbcService.getQuerySalesMaterielSql(filter);
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
	 * @param BillApply
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws IOException
	 * @throws Exception
	 */
	public void submitWorkflow(HttpServletRequest request,
			HttpServletResponse response, TBillApply tBillApply)
			throws IllegalAccessException, InvocationTargetException,
			IOException, Exception {
		String taskId = request.getParameter("workflowTaskId");

		String rtn = this.billApplyHibernateService.verifyFilds(taskId,
				tBillApply);
		if (!"".equals(rtn)) {
			throw new BusinessException(rtn);
		}

		String msg = "";

		tBillApply.setApprovedTime(DateUtils.getCurrTime(2));
		UserContext userContext = UserContextHolder.getLocalUserContext()
				.getUserContext();
		// 业务描述信息
		tBillApply.setWorkflowBusinessNote(userContext.getSysDept()
				.getDeptname()
				+ "|"
				+ userContext.getSysUser().getRealName()
				+ "|"
				+ tBillApply.getBillApplyNo() + "|" + tBillApply.getBillType());

		String doWorkflow = request.getParameter("doWorkflow");
		if (doWorkflow == null && !"mainForm".equals(doWorkflow)) {
			String workflowLeaveTransitionName = request
					.getParameter("workflowLeaveTransitionName");
			String workflowExamine = request.getParameter("workflowExamine");
			tBillApply.setWorkflowExamine(workflowExamine);
			tBillApply
					.setWorkflowLeaveTransitionName(workflowLeaveTransitionName);
		}

		// 取得工作流节点名称

		JSONObject jo = new JSONObject();
		jo = this.billApplyJdbcService.submitWorkflow(tBillApply, taskId);

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
	public ModelAndView billApplyExamine(HttpServletRequest request,
			HttpServletResponse response) throws IllegalAccessException,
			InvocationTargetException {
		String billApplyId = request.getParameter("businessRecordId");
		String tradeType = request.getParameter("tradeType");
		tradeType=(tradeType==null?"":tradeType);
		String taskId = request.getParameter("taskId");

		TaskInstanceContext taskInstanceContext = WorkflowService
				.getTaskInstanceContext(taskId);
		String taskName = taskInstanceContext.getTaskName();
		String taskType = "";
		String isPrintHidden = "true";
		if (taskName.indexOf("开票，并录入票号")>0) {
			request.setAttribute("printUrl", "billApplyController.spr?action=dealPrint&billApplyId="+billApplyId);
			isPrintHidden = "false";
			taskType = "1";
		}
		request.setAttribute("isPrintHidden", isPrintHidden);
		request.setAttribute("businessRecordId", billApplyId);
		request.setAttribute("taskId", taskId);

		request.setAttribute("businessRecordId", billApplyId);
		TBillApply tBillApply = this.billApplyHibernateService
				.getBillApply(billApplyId);

		UserContext userContext = UserContextHolder.getLocalUserContext()
				.getUserContext();
		Boolean modify = false;
		// 创建者能修改：
		if (tBillApply.getCreator()
				.equals(userContext.getSysUser().getUserId())) {
			modify = true;
		}

		request.setAttribute("iframeSrc",
				"billApplyController.spr?action=examineBillApplyView&tradeType="
						+ tradeType + "&operate=iframe&billApplyId="
						+ billApplyId + "&modify=" + modify + "&taskType="
						+ taskType);

		request.setAttribute("submitUrl",
				"billApplyController.spr?action=submitWorkflow");

		System.out.println("businessRecordId：" + billApplyId);

		return new ModelAndView("sapss/workflow/commonProcessPage");
	}

	/**
	 * 提交并保存开票申请单
	 * 
	 * @param request
	 * @param response
	 * @param tBillApply
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws IOException
	 * @throws Exception
	 */
	public void submitAndSaveBillApply(HttpServletRequest request,
			HttpServletResponse response, TBillApply tBillApply)
			throws IllegalAccessException, InvocationTargetException,
			IOException, Exception {
		String taskId = request.getParameter("workflowTaskId");
		System.out.println("taskId : " + taskId);
		billApplyHibernateService.submitAndSaveBillApply(taskId, tBillApply);
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
	public ModelAndView examineBillApplyView(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		// 贸易类型
		String tradeType = request.getParameter("tradeType");
		

		String billApplyId = request.getParameter("billApplyId");
		String modify = request.getParameter("modify");

		TBillApply tBillApply = billApplyHibernateService
				.getBillApply(billApplyId);
		if(tBillApply.getLoanTotal()!=null){
			request.setAttribute("loanTotal", NumberUtils.round(tBillApply.getLoanTotal(), 2));
		}
		if(tBillApply.getTaxTotal()!=null){
			request.setAttribute("taxTotal", NumberUtils.round(tBillApply.getTaxTotal(), 2));
		}
		if(tBillApply.getPriceTotal()!=null){
			request.setAttribute("priceTotal", NumberUtils.round(tBillApply.getPriceTotal(), 2));
		}
		request.setAttribute("main", tBillApply);

		// 操作类型
		String operType = "000";
		if (modify.equals("true"))
			operType = "100";
		request.setAttribute("tradeType", StringUtils.isEmpty(tradeType)?tBillApply.getTradeType():tradeType);
		request.setAttribute("save", operType.substring(0, 1).equals("0"));
		request.setAttribute("submit", operType.substring(1, 2).equals("0"));
		request.setAttribute("close", operType.substring(2, 3).equals("0"));
		//System.out.println("operType = " + operType);

		String strContractSalesId = tBillApply.getContractSalesId();

		SysDept sysDept = sysDeptService.queryDeptById(tBillApply.getDeptId());
		request.setAttribute("sysDept", sysDept);

		String contractSalesId = tBillApply.getContractSalesId();
		String ContractNo = "";
		if (StringUtils.isNotBlank(contractSalesId)) { // 取得销售订单
			TContractSalesInfo tContractSalesInfo = contractHibernateService
					.getContractSalesInfo(contractSalesId);
			ContractNo = tContractSalesInfo.getContractNo();
		}
		request.setAttribute("contractNo", ContractNo);

		String taskType = request.getParameter("taskType");
		
		request.setAttribute("taskType", taskType);
		//specialDept(request, response);
		if("1".equals(taskType)){
			request.setAttribute("modifiable", true);
		}
		else request.setAttribute("modifiable", false);
		if("2".equals(tBillApply.getBillType())){
			return new ModelAndView("sapss/bill/agentBillApplyCreate");
		}
		return new ModelAndView("sapss/bill/billApplyCreate");

	}

	/*
	 * 进入总的开票申请单查询界面
	 * 
	 * @param request @param response @return @throws IOException
	 */
	public ModelAndView ArchBillApplyManageView(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		// 贸易类型
		String tradeType = request.getParameter("tradeType");
		request.setAttribute("tradeType", tradeType);

		UserContext userContext = UserContextHolder.getLocalUserContext()
				.getUserContext();
		request.setAttribute("loginer", userContext.getSysUser().getUserId());
		return new ModelAndView("sapss/bill/Archives/archBillApplyManage");
	}

	/**
	 * 转到查询销售合同物料窗口
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView toFindSalesMaterial(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String contractSalesNo = request.getParameter("contractSalesNo");
		String tradeType = request.getParameter("tradeType");
		request.setAttribute("contractSalesNo", contractSalesNo);
		request.setAttribute("tradeType", tradeType);
		return new ModelAndView("sapss/bill/findSalesMateriel");
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
		String billApplyId = request.getParameter("billApplyId");
		TBillApply tBillApply = billApplyHibernateService.getBillApply(billApplyId);
		dealSapReturnNo(tBillApply);
		List<TBillApplyMaterial> melist = billApplyJdbcService.getBillApplyMaterial(billApplyId);
		List<TaskHisDto> hisList = sysWfUtilsService.queryTaskHisList(billApplyId);
		SysUser user = sysUserService.queryUserById(tBillApply.getCreator(),tBillApply.getDeptId());
	    request.setAttribute("meListCount", melist.size());
	    request.setAttribute("deptName", user.getDeptName());
		request.setAttribute("creator", user.getRealName());
		for(Iterator<TaskHisDto> it = hisList.iterator();it.hasNext();){
			TBillApplyMaterial me = new TBillApplyMaterial();
    		TaskHisDto dto = it.next();
    		me.setMaterialCode(dto.getTaskName());
    		me.setMaterialName(dto.getExaminePerson());
    		me.setMaterialUnit(dto.getExamine());
    		me.setQuantity(0d);
    		me.setLoanMoney(0d);
    		me.setTax(0d);
    		me.setTotalMoney(0d);
    		me.setBillApplyId("fuck");
    		melist.add(me);
    	}
		request.setAttribute("main", tBillApply);
		request.setAttribute("meList", melist);
		
		return new ModelAndView("sapss/bill/print");
	}
	
	
	public ModelAndView markPrint(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String billApplyId = request.getParameter("billApplyId");
		billApplyJdbcService.markPrint(billApplyId);
		return dealPrint(request,response);
	}
	
	private void dealSapReturnNo(TBillApply apply){
		String sapReturnNo = apply.getSapReturnNo();
		if(sapReturnNo==null) return;
		String[] s = sapReturnNo.split(",");
		if(s.length<=3)
			return;
		else {
			StringBuffer buffer = new StringBuffer("");
			for(int i=0;i<s.length;i++){
				buffer.append(s[i]);
				if(i!=s.length-1){
					if((i+1)%3==0) buffer.append("<br>");
					else  buffer.append(",");
				}
			}
			apply.setSapReturnNo(buffer.toString());
		}
	}
	
	
	public ModelAndView billInfoView(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		return new ModelAndView("sapss/bill/billInfo");
	}
	
	public void queryBillInfo(HttpServletRequest request, HttpServletResponse response)
	throws IOException, ServletException {
		Map<String, String> filter = extractFR(request);
//		Map<String, String> filter = shipNotifyJdbcService.getFilter(map);
		String sql = billApplyJdbcService.getQueryBillInfoSql(filter);
		String grid_columns = "DEPT_NAME,PROJECT_NO,PROJECT_NAME,MATERIAL_NAME,MATERIAL_UNIT,QUANTITY,PRICE,LOAN_MONEY,TAX,TOTAL_MONEY,APPLY_TIME,APPROVED_TIME,CMD";
		String grid_size = "10";
		request.setAttribute("grid_sql", sql);
		request.setAttribute("grid_columns", grid_columns);
		request.setAttribute("grid_size", grid_size);

		ExtComponentServlet extComponentServlet = new ExtComponentServlet();
		extComponentServlet.processGrid(request, response, true);
    }
	
	public void dealOutToExcel(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		String[] titles  = new String[]{"序号","部门","立项号","立项名称","物料名称","物料单位","申请数量","单价","货款金额","税款金额","价税总计","申请时间",
				"开票时间","备注"};

		ExcelObject excel = new ExcelObject(titles);
		Map<String, String> filter = extractFR(request);
		String sql = billApplyJdbcService.getQueryBillInfoSql(filter);

		billApplyJdbcService.dealOutToExcel(sql ,excel);
		
		try{
			//response.reset();
	        response.setCharacterEncoding("utf-8");
	        response.setContentType("application/x-download;charset=utf-8");
	        response.setHeader("Content-Disposition", (new StringBuilder("attachment;filename=")).append(URLEncoder.encode("内贸贸易明细表.xls", "utf-8")).toString());
			excel.write(response.getOutputStream());
			response.flushBuffer();
			response.getOutputStream().close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void dealOutToExcel1(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		String[] titles  = new String[]{"序号","部门","申请人","合同编号","纸质合同号","订单号","出仓单号","开票单位","开票申请单号","金税票号 ","开票日期","数量","货款合计","税金合计","价税合计","申报时间","通过时间"};

		ExcelObject excel = new ExcelObject(titles);
	
		Map<String, String> map = extractFR(request);

		String sql = billApplyJdbcService.getQuerySql(map);

		billApplyJdbcService.dealOutToExcel1(sql, excel);		
		try{
			//response.reset();
	        response.setCharacterEncoding("utf-8");
	        response.setContentType("application/x-download;charset=utf-8");
	        response.setHeader("Content-Disposition", (new StringBuilder("attachment;filename=")).append(URLEncoder.encode("增值税发票.xls", "utf-8")).toString());
			excel.write(response.getOutputStream());
			response.flushBuffer();
			response.getOutputStream().close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

}
