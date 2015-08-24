/*
 * @(#)ExportIncomeController.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：陈喜平
 *  时　间：2009-5-25
 *  描　述：创建
 */

package com.infolion.sapss.salesReturn.web;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

import com.infolion.platform.component.ExtComponentServlet;
import com.infolion.platform.component.workflow.WorkflowService;
import com.infolion.platform.component.workflow.ext.TaskInstanceContext;
import com.infolion.platform.console.sys.context.UserContext;
import com.infolion.platform.console.sys.context.UserContextHolder;
import com.infolion.platform.core.BusinessException;
import com.infolion.platform.core.web.BaseMultiActionController;
import com.infolion.platform.util.CodeGenerator;
import com.infolion.platform.util.DateUtils;
import com.infolion.sapss.contract.domain.TContractSalesInfo;
import com.infolion.sapss.contract.service.ContractHibernateService;
import com.infolion.sapss.credit.domain.TCreditInfo;
import com.infolion.sapss.credit.service.CreditEntryHibernateService;
import com.infolion.sapss.project.domain.TProjectInfo;
import com.infolion.sapss.project.service.ProjectHibernateService;
import com.infolion.sapss.salesReturn.domain.TSalesReturn;
import com.infolion.sapss.salesReturn.domain.TSalesReturnMaterial;
import com.infolion.sapss.salesReturn.service.SalesReturnService;
import com.infolion.sapss.ship.domain.TShipInfo;
import com.infolion.sapss.ship.domain.TShipMaterial;
import com.infolion.sapss.ship.service.ShipHibernateService;

public class SalesReturnController extends BaseMultiActionController {
	@Autowired
	private SalesReturnService salesReturnService;
	@Autowired
	private ContractHibernateService contractHibernateService;
	@Autowired
	private ProjectHibernateService projectHibernateService;
	@Autowired
	private ShipHibernateService shipHibernateService;

	public void setSalesReturnService(SalesReturnService salesReturnService) {
		this.salesReturnService = salesReturnService;
	}

	public void setContractHibernateService(
			ContractHibernateService contractHibernateService) {
		this.contractHibernateService = contractHibernateService;
	}

	public void setProjectHibernateService(
			ProjectHibernateService projectHibernateService) {
		this.projectHibernateService = projectHibernateService;
	}

	public void setShipHibernateService(
			ShipHibernateService shipHibernateService) {
		this.shipHibernateService = shipHibernateService;
	}

	/**
	 * 系统菜单列表链接
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView toSalesReturnManger(HttpServletRequest request,
			HttpServletResponse response) {
		String returnType = request.getParameter("returnType");
		request.setAttribute("returnType", returnType);
		UserContext userContext = UserContextHolder.getLocalUserContext()
				.getUserContext();
		String loginer = userContext.getSysUser().getUserId();
		request.setAttribute("loginer", loginer);
		if ("0".equals(returnType)) {
			return new ModelAndView(
					"sapss/salesReturn/salesReturnUnBillManager");
		} else {
			return new ModelAndView("sapss/salesReturn/salesReturnManager");
		}
	}
	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView toCompositeQuery(HttpServletRequest request,HttpServletResponse response){
		return new ModelAndView("sapss/salesReturn/salesReturnCompositeQuery");
	}
	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView toCompositeQueryUnbill(HttpServletRequest request,HttpServletResponse response){
		return new ModelAndView("sapss/salesReturn/salesReturnUnBillCompositeQuery");
	}
	/**
	 * 弹出销售退票详细页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView toSalesReturnInfo(HttpServletRequest request,
			HttpServletResponse response) {
		String returnType = request.getParameter("returnType");
		TSalesReturn tSalesReturn = new TSalesReturn();
		tSalesReturn.setReturnType(returnType);
		tSalesReturn.setExamineState("1");
		tSalesReturn = this.salesReturnService.save(tSalesReturn);
		request.setAttribute("tSalesReturn", tSalesReturn);
		request.setAttribute("Enabled", "true");
		if ("0".equals(returnType)) {
			return new ModelAndView("sapss/salesReturn/salesReturnUnBillInfo");
		} else {
			return new ModelAndView("sapss/salesReturn/salesReturnInfo");
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
	public void find(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String grid_sql = this.salesReturnService.querySQL(request);
		String grid_column = "RETURN_ID,RETURN_NO,CONTRACT_SALES_ID,RETURN_TYPE,SHIP_NO,PROJECT_NO,CONTRACT_NO,"
				+ "SALES_SAP_ORDER_NO,SHIP_SAP_ORDER_NO,SAP_DELIVERY_NO,SAP_BILLING_NO,CREATOR,APPLY_TIME,"
				+ "EXAMINE_STATE, EXAMINE_STATE_D_EXAMINE_STATE,SAP_RETURN_NO,APPROVED_TIME";
		String grid_size = request.getParameter("limit");
		grid_size = grid_size == null ? "10" : grid_size;
		request.setAttribute("grid_sql", grid_sql);
		request.setAttribute("grid_columns", grid_column);
		request.setAttribute("grid_size", grid_size);
		ExtComponentServlet servlet = new ExtComponentServlet();
		servlet.processGrid(request, response, true);
	}

	/**
	 * 取得退货单下的物料明细资料
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void queryMaterial(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String returnId = request.getParameter("returnId");
		String sql = "select * from t_sales_return_material where return_id = '"
				+ returnId + "'";
		String grid_columns = "RETURN_MATERIAL_ID,RETURN_ID,MATERIAL_CODE,MATERIAL_NAME,MATERIAL_UNIT,QUANTITY,BATCH_NO,CURRENCY,CALC_UNIT,PRICE_UNIT,FACTORY,DELIVERY_TIME,PRICE,RATE,MONEY,TAXES,TOTAL_MONEY,CMD";
		String grid_size = "10";
		request.setAttribute("grid_sql", sql);
		request.setAttribute("grid_columns", grid_columns);
		request.setAttribute("grid_size", grid_size);

		ExtComponentServlet extComponentServlet = new ExtComponentServlet();
		extComponentServlet.processGrid(request, response, true);
	}

	/**
	 * 保存
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void save(HttpServletRequest request, HttpServletResponse response,
			TSalesReturn info) throws IOException {
		this.salesReturnService.save(info);
		JSONObject jo = new JSONObject();
		jo.put("returnMessage", "保存成功！");
		String jsontext = jo.toString();
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		this.operateSuccessfullyWithString(response, jsontext);
	}

	/**
	 * 初始化退货单
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void initSalesReturnMt(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String returnId = request.getParameter("returnId");
		String shipId = request.getParameter("shipId");
		String contractSalesId = request.getParameter("contractSalesId");
		this.salesReturnService.initSalesReturnMt(returnId, contractSalesId,
				shipId);

	}

	public void addMaterial(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String returnId = request.getParameter("returnId");
		String shipDetailId = request.getParameter("shipDetailId");
		String salesRowsId = request.getParameter("salesRowsId");
		this.salesReturnService
				.addMaterial(returnId, shipDetailId, salesRowsId);
		JSONObject jo = new JSONObject();
		jo.put("returnMessage", "保存成功！");
		String jsontext = jo.toString();
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		this.operateSuccessfullyWithString(response, jsontext);

	}

	public void delMaterial(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String returnMaterialId = request.getParameter("returnMaterialId");
		this.salesReturnService.delMaterial(returnMaterialId);
		JSONObject jo = new JSONObject();
		jo.put("returnMessage", "删除成功！");
		String jsontext = jo.toString();
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		this.operateSuccessfullyWithString(response, jsontext);
	}

	public void updateMaterial(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String returnMaterialId = request.getParameter("returnMaterialId");
		String colName = request.getParameter("colName");
		String colValue = request.getParameter("colValue");
		String returnId = request.getParameter("returnId");
		TSalesReturn tSalesReturn = this.salesReturnService.find(returnId);
		this.salesReturnService.updateMaterial(tSalesReturn, returnMaterialId,colName, colValue);
		JSONObject jsonObject = new JSONObject();
		// 回调更新总额
		if ("0".equals(tSalesReturn.getReturnType())) {
			if (colName.equals("QUANTITY") || colName.equals("PRICE") || colName.equals("RATE")) {
				BigDecimal totalMoney = BigDecimal.valueOf(0);
				List<BigDecimal> list = this.salesReturnService.getSalesReturnMtMoney(returnId);
				if (list != null && list.size() > 0) {
					totalMoney = (BigDecimal) ((Map) list.get(0)).get("total_money");
				}
				jsonObject.put("totalMoney", totalMoney);
			}
		}
		// 回调税金、货款、表单总额等
		if ("1".equals(tSalesReturn.getReturnType())) {
			BigDecimal netMoney = BigDecimal.valueOf(0);
			BigDecimal taxMoney = BigDecimal.valueOf(0);
			BigDecimal totalMoney = BigDecimal.valueOf(0);
			if (colName.equals("PRICE") || colName.equals("QUANTITY") || colName.equals("TOTAL_MONEY") || colName.equals("RATE")) {
				TSalesReturnMaterial tSalesReturnMaterial = this.salesReturnService.getMaterial(returnMaterialId);
				jsonObject.put("price", tSalesReturnMaterial.getPrice());
				jsonObject.put("quantity", tSalesReturnMaterial.getQuantity());
				jsonObject.put("taxes", tSalesReturnMaterial.getTaxes());
				jsonObject.put("money", tSalesReturnMaterial.getMoney());
				jsonObject.put("total", tSalesReturnMaterial.getTotalMoney());
				List<BigDecimal> list = this.salesReturnService.getSalesReturnMtMoney(returnId);
				if (list != null && list.size() > 0) {
					netMoney = (BigDecimal) ((Map) list.get(0)).get("money");
					taxMoney = (BigDecimal) ((Map) list.get(0)).get("taxes");
					totalMoney = (BigDecimal) ((Map) list.get(0)).get("total_money");
				}
				jsonObject.put("netMoney", netMoney);
				jsonObject.put("taxMoney", taxMoney);
				jsonObject.put("totalMoney", totalMoney);
			}
		}
		jsonObject.put("stateCode", "1");
		response.getWriter().write("{success:true");
		String jsontext = jsonObject.toString();
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		this.operateSuccessfullyWithString(response, jsontext);
	}

	/**
	 * 提交审批
	 * 
	 * @param request
	 * @param response
	 * @param info
	 * @throws IOException
	 */
	public void saveAndsubmit(HttpServletRequest request,
			HttpServletResponse response, TSalesReturn info) throws IOException {
		String taskId = request.getParameter("workflowTaskId");
		if (StringUtils.isEmpty(info.getReturnId())) {
			info.setReturnId(CodeGenerator.getUUID());
		}
		info.setExamineState("2");
		info.setApplyTime(DateUtils.getCurrTime(2));
		this.salesReturnService.saveAndSubmit(taskId, info);
		JSONObject js = new JSONObject();
		js.put("returnMessage", "提交成功");
		this.operateSuccessfullyWithString(response, js.toString());
	}

	/**
	 * 进入到流程审批界面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ModelAndView examine(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String id = request.getParameter("businessRecordId");
		String taskId = request.getParameter("taskId");
		TaskInstanceContext taskInstanceContext = WorkflowService
				.getTaskInstanceContext(taskId);
		String taskName = taskInstanceContext.getTaskName();
		String taskType = "";
		if (taskName.equals("信息中心创建退货订单")) {
			taskType = "3";
		} else if (taskName.equals("综合一部处理退货收货")
				|| taskName.equals("综合部处理退货收货")) {
			taskType = "1";
		} else if (taskName.equals("综合一部处理开票") || taskName.equals("综合部处理开票")) {
			taskType = "2";
		}
		request.setAttribute("taskId", taskId);
		request.setAttribute("businessRecordId", id);
		request.setAttribute("taskName", taskName);
		TSalesReturn tSalesReturn = this.salesReturnService.find(id);
		String returnType = tSalesReturn.getReturnType();
		UserContext userContext = UserContextHolder.getLocalUserContext()
				.getUserContext();
		Boolean modify = false;
		// 创建者能修改：
		if (tSalesReturn.getCreator().equals(
				userContext.getSysUser().getUserId())) {
			modify = true;
		}
		// 业务修改权限进入修改页面
		if (modify) {
			request.setAttribute("iframeSrc",
					"salesReturnController.spr?action=forModify&id="
							+ tSalesReturn.getReturnId() + "&from=workflow");
		} else {
			request.setAttribute("iframeSrc",
					"salesReturnController.spr?action=forView&id="
							+ tSalesReturn.getReturnId() + "&taskType="
							+ taskType);
		}
		String submitUrl = "salesReturnController.spr?action=submit";
		request.setAttribute("submitUrl", submitUrl);
		return new ModelAndView("sapss/workflow/commonProcessPage");

	}

	/**
	 * 流程审批提交动作
	 * 
	 * @param request
	 * @param response
	 * @param tSalesReturn
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws IOException
	 * @throws Exception
	 */
	public void submit(HttpServletRequest request,
			HttpServletResponse response, TSalesReturn tSalesReturn)
			throws IllegalAccessException, InvocationTargetException,
			IOException, Exception {

		String taskId = request.getParameter("workflowTaskId");
		String rtn = this.salesReturnService.verifyFilds(taskId, tSalesReturn);
		if (!"".equals(rtn)) {
			throw new BusinessException(rtn);
		}
		this.salesReturnService.submit(taskId, tSalesReturn);
		JSONObject jo = new JSONObject();
		jo.put("returnMessage", "提交成功！");
		String jsontext = jo.toString();
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		this.operateSuccessfullyWithString(response, jsontext);
	}

	/**
	 * 删除
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void delete(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		String id = request.getParameter("id");
		this.salesReturnService.delete(id);
		JSONObject js = new JSONObject();
		js.put("ok", "删除成功");
		this.operateSuccessfullyWithString(response, js.toString());
	}

	/**
	 * 修改
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView forModify(HttpServletRequest request,
			HttpServletResponse response) {
		String id = request.getParameter("id");
		String taskType = request.getParameter("taskType");
		request.setAttribute("taskType", taskType);
		TSalesReturn tSalesReturn = this.salesReturnService.find(id);
		request.setAttribute("tSalesReturn", tSalesReturn);
		request.setAttribute("Enabled", "true");
		if (StringUtils.isNotBlank(tSalesReturn.getContractSalesId())) {
			TContractSalesInfo tContractSalesInfo = this.contractHibernateService
					.getContractSalesInfo(tSalesReturn.getContractSalesId());
			request.setAttribute("tContractSalesInfo", tContractSalesInfo);
			TProjectInfo tProjectInfo = this.projectHibernateService
					.getTProjectInfo(tContractSalesInfo.getProjectId());
			request.setAttribute("tProjectInfo", tProjectInfo);
		}

		if (StringUtils.isNotBlank(tSalesReturn.getShipId())) {
			TShipInfo tShipInfo = this.shipHibernateService
					.getShipInfo(tSalesReturn.getShipId());
			request.setAttribute("tShipInfo", tShipInfo);
		}
		if ("0".equals(tSalesReturn.getReturnType())) {
			return new ModelAndView("sapss/salesReturn/salesReturnUnBillInfo");
		} else {
			return new ModelAndView("sapss/salesReturn/salesReturnInfo");
		}
	}

	/**
	 * 查看
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView forView(HttpServletRequest request,
			HttpServletResponse response) {
		String id = request.getParameter("id");
		String taskType = request.getParameter("taskType");
		request.setAttribute("taskType", taskType);
		TSalesReturn tSalesReturn = this.salesReturnService.find(id);
		request.setAttribute("tSalesReturn", tSalesReturn);
		
		if (StringUtils.isNotBlank(tSalesReturn.getContractSalesId())) {
			TContractSalesInfo tContractSalesInfo = this.contractHibernateService
					.getContractSalesInfo(tSalesReturn.getContractSalesId());
			request.setAttribute("tContractSalesInfo", tContractSalesInfo);
			TProjectInfo tProjectInfo = this.projectHibernateService
					.getTProjectInfo(tContractSalesInfo.getProjectId());
			request.setAttribute("tProjectInfo", tProjectInfo);
		}
		request.setAttribute("Enabled", "false");
		
		if (StringUtils.isNotBlank(tSalesReturn.getShipId())) {
			TShipInfo tShipInfo = this.shipHibernateService
					.getShipInfo(tSalesReturn.getShipId());
			request.setAttribute("tShipInfo", tShipInfo);
		}
		if ("0".equals(tSalesReturn.getReturnType())) {
			return new ModelAndView("sapss/salesReturn/salesReturnUnBillInfo");
		} else {
			return new ModelAndView("sapss/salesReturn/salesReturnInfo");
		}

	}

	/**
	 * 查询销售合同信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView selectSalesInfo(HttpServletRequest request,
			HttpServletResponse response) {

		return new ModelAndView("sapss/export/exportIncome/selectSalesInfo");
	}

}
