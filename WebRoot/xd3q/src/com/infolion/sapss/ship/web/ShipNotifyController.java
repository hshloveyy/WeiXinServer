/*
 * @(#)SampleController.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：林进旭
 *  时　间：2009-3-06
 *  描　述：创建
 */

package com.infolion.sapss.ship.web;

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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

import com.infolion.platform.component.ExtComponentServlet;
import com.infolion.platform.console.org.domain.SysDept;
import com.infolion.platform.console.org.service.SysDeptService;
import com.infolion.platform.console.sys.context.UserContext;
import com.infolion.platform.console.sys.context.UserContextHolder;
import com.infolion.platform.core.BusinessException;
import com.infolion.platform.core.web.BaseMultiActionController;
import com.infolion.sapss.common.ExcelObject;
import com.infolion.sapss.common.ExcelUtil;
import com.infolion.sapss.contract.domain.TContractSalesInfo;
import com.infolion.sapss.contract.service.ContractHibernateService;
import com.infolion.sapss.ship.domain.TExportApply;
import com.infolion.sapss.ship.domain.TExportApplyMaterial;
import com.infolion.sapss.ship.service.ShipNotifyHibernateService;
import com.infolion.sapss.ship.service.ShipNotifyJdbcService;

// import com.infolion.sapss.ship.service.CreditEntryJdbcService;

/**
 * 
 * <pre>
 * 控制器
 * </pre>
 * 
 * <br>
 * JDK版本:1.5
 * 
 * @author 林进旭
 * @version 1.0
 * @see The author for more details
 * @since 1.0
 */
public class ShipNotifyController extends BaseMultiActionController {
	private final Log log = LogFactory.getLog(ShipController.class);
	// 服务类注入
	@Autowired
	private ShipNotifyHibernateService shipNotifyHibernateService;
	@Autowired
	private ShipNotifyJdbcService shipNotifyJdbcService;
	@Autowired
	ContractHibernateService contractHibernateService;
	@Autowired
	SysDeptService sysDeptService;

	public void setShipNotifyHibernateService(
			ShipNotifyHibernateService shipNotifyHibernateService) {
		this.shipNotifyHibernateService = shipNotifyHibernateService;
	}

	public void setShipNotifyJdbcService(
			ShipNotifyJdbcService shipNotifyJdbcService) {
		this.shipNotifyJdbcService = shipNotifyJdbcService;
	}

	public void setContractHibernateService(
			ContractHibernateService contractHibernateService) {
		this.contractHibernateService = contractHibernateService;
	}

	public void setSysDeptService(SysDeptService sysDeptService) {
		this.sysDeptService = sysDeptService;
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
	 * 进入出口货物申请表界面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ModelAndView shipNotifyManageView(HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		String tradeType = request.getParameter("tradeType");
		request.setAttribute("tradeType", tradeType);
		UserContext userContext = UserContextHolder.getLocalUserContext()
				.getUserContext();
		String loginer = userContext.getSysUser().getUserId();
		request.setAttribute("loginer", loginer);
		return new ModelAndView("sapss/ship/shipNotifyManage");
	}

	/**
	 * 查询出仓申请单信息
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	public void query(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		Map<String, String> filter = extractFR(request);
		// Map<String, String> filter = shipNotifyJdbcService.getFilter(map);
		String sql = shipNotifyJdbcService.getQueryExportApplySql(filter);
		String grid_columns = "EXPORT_APPLY_ID,CONTRACT_SALES_ID,TRADE_TYPE,PROJECT_NO,CONTRACT_NAME,PROJECT_NAME,CONTRACT_GROUP_NO,SALES_NO,VBKD_IHREZ,"
				+ "SAP_ORDER_NO,NOTICE_NO,CREATOR,CREATOR_TIME,EXAMINE_STATE,EXAMINE_STATE_D_EXAMINE_STATE,IS_AVAILABLE,APPROVED_TIME"
				+ ",WRITE_NO,GET_SHEET_TIME";
		String grid_size = "10";
		request.setAttribute("grid_sql", sql);
		request.setAttribute("grid_columns", grid_columns);
		request.setAttribute("grid_size", grid_size);

		ExtComponentServlet extComponentServlet = new ExtComponentServlet();
		extComponentServlet.processGrid(request, response, true);
	}

	/**
	 * 新增出口货物通知界面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public void addShipNotify(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		// 贸易类型
		String tradeType = request.getParameter("tradeType");
		request.setAttribute("tradeType", tradeType);
		TExportApply tExportApply = this.shipNotifyHibernateService
				.addExportApply(tradeType);
		JSONObject jsonObject = JSONObject.fromObject(tExportApply);
		String jsontext = jsonObject.toString();
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		response.getWriter().print(jsontext);
	}

	/**
	 * 删除出口货物通知单
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void delete(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String exportApplyId = request.getParameter("exportApplyId");
		
		JSONObject jsonObject = new JSONObject();
        if(shipNotifyHibernateService.delExportApply(exportApplyId)){
        	jsonObject.put("returnMessage", "删除成功!");
		}
        else jsonObject.put("returnMessage", "删除失败!该单已关联出单，请联系贸管部！");
		String jsontext = jsonObject.toString();
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		this.operateSuccessfullyWithString(response, jsontext);
	}

	/**
	 * 进入编辑出口货物通知单界面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ModelAndView addShipNotifyView(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		// 贸易类型
		String tradeType = request.getParameter("tradeType");
		request.setAttribute("tradeType", tradeType);

		String exportApplyId = request.getParameter("exportApplyId");
		String operType = request.getParameter("operType");
		TExportApply tExportApply = shipNotifyHibernateService
				.getExportApply(exportApplyId);
		SysDept sysDept = sysDeptService
				.queryDeptById(tExportApply.getDeptId());
		request.setAttribute("tExportApply", tExportApply);
		request.setAttribute("sysDept", sysDept);
		request.setAttribute("save", operType.substring(0, 1).equals("0"));
		request.setAttribute("submit", operType.substring(1, 2).equals("0"));

		// //代理物料“当前库存”
		// if (tradeType.equals("5")||tradeType.equals("6"))
		// request.setAttribute("inventoryNum", false);
		// else
		request.setAttribute("inventoryNum", true);
		UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
		String userDeptName = userContext.getSysUser().getDeptName();
		if(userDeptName.equals("综合二部")||userDeptName.equals("贸管部")||userDeptName.equals("信息中心")){
			request.setAttribute("showWriteNoButton", "true");
			request.setAttribute("save", "false");
		}
		if(userContext.getSysDept().getDeptShortCode().equals("MG")){
			request.setAttribute("save", "false");
		}

		return new ModelAndView("sapss/ship/shipNotifyCreate");

	}

	/**
	 * 进入流程出口货物通知单界面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ModelAndView examineShipNotifyView(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String exportApplyId = request.getParameter("exportApplyId");
		TExportApply tExportApply = shipNotifyHibernateService
		.getExportApply(exportApplyId);
		SysDept sysDept = sysDeptService
		.queryDeptById(tExportApply.getDeptId());
		request.setAttribute("sysDept", sysDept);
		request.setAttribute("tExportApply", tExportApply);
		return new ModelAndView("sapss/ship/shipNotifyExamine");

	}

	/**
	 * 保存出口货物通知单
	 * 
	 * @param request
	 * @param response
	 * @param tExportApply
	 * @throws IOException
	 */
	public void updateExportApply(HttpServletRequest request,
			HttpServletResponse response, TExportApply tExportApply)
			throws IOException {
		String salesId = tExportApply.getContractSalesId();
		if (!"".equals(salesId) && salesId != null) {
			TContractSalesInfo sales = contractHibernateService
					.getContractSalesInfo(salesId);
			if (sales.getVbkdZlsch().equals("M")
					&& (tExportApply.getLcno() == null || ""
							.equals(tExportApply.getLcno())))
				throw new BusinessException("销售合同" + sales.getContractNo()
						+ "付款方式为信用证，请选择信用证号");

		}
		shipNotifyHibernateService.saveExportApply(tExportApply);
		JSONObject jo = new JSONObject();
		jo.put("returnMessage", "保存成功！");
		String jsontext = jo.toString();
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");

		this.operateSuccessfullyWithString(response, jsontext);
	}
	
	public void updateWriteNo(HttpServletRequest request,
			HttpServletResponse response)
			throws IOException {
		String exportApplyId = request.getParameter("exportApplyId");
		String writeNo = request.getParameter("writeNo");
		String getSheetTime = request.getParameter("getSheetTime"); 
		TExportApply apply = shipNotifyHibernateService.getExportApply(exportApplyId);
		apply.setWriteTo(writeNo);
		apply.setGetSheetTime(getSheetTime);
		shipNotifyHibernateService.saveExportApply(apply);
		JSONObject jo = new JSONObject();
		jo.put("returnMessage", "保存成功！");
		String jsontext = jo.toString();
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");

		this.operateSuccessfullyWithString(response, jsontext);
	}

	/**
	 * 通过ID取的出口货物通知单内容
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void getExportApply(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String exportApplyId = request.getParameter("exportApplyId");
		TExportApply tExportApply = shipNotifyHibernateService
				.getExportApply(exportApplyId);
		JSONObject jsonObject = JSONObject.fromObject(tExportApply);
		String jsontext = jsonObject.toString();
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		response.getWriter().print(jsontext);
	}

	/**
	 * 初始化出口货物通知单信息
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void initExportApply(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String contractSalesId = request.getParameter("contractSalesId");
		String exportApplyId = request.getParameter("exportApplyId");
		shipNotifyHibernateService.initExportApply(exportApplyId,
				contractSalesId);
	}

	/**
	 * 取得出仓申请单下的物料明细资料
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void queryMaterial(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String exportApplyId = request.getParameter("exportApplyId");
		String sql = "select t.*,t.quantity old_quantity from t_export_apply_material t where t.export_apply_id = '"
				+ exportApplyId + "'";
		String grid_columns = "EXPORT_MATE_ID,EXPORT_APPLY_ID,MATERIAL_CODE,MATERIAL_NAME,MATERIAL_UNIT,QUANTITY,GROSS_WEIGHT,NET_WEIGHT,"
				+ "RATE,PRICE,CURRENCY,TOTAL,CMD,IS_AVAILABLE,CREATOR_TIME,CREATOR,PEINH,OLD_QUANTITY";
		String grid_size = "10";
		request.setAttribute("grid_sql", sql);
		request.setAttribute("grid_columns", grid_columns);
		request.setAttribute("grid_size", grid_size);
		ExtComponentServlet extComponentServlet = new ExtComponentServlet();
		extComponentServlet.processGrid(request, response, true);

	}

	/**
	 * 新增出仓单明细资料
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public void addDetail(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String exportApplyId = request.getParameter("exportApplyId");
		String tradeType = request.getParameter("tradeType");
		String salesRowsId = request.getParameter("salesRowsId");
		String hasQuantity = request.getParameter("hasQuantity");
		TExportApplyMaterial tExportApplyMaterial = shipNotifyHibernateService
				.addMtByCon(exportApplyId, tradeType, salesRowsId, hasQuantity);
		JSONObject jsonObject = JSONObject.fromObject(tExportApplyMaterial);
		String jsonText = jsonObject.toString();

		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		response.getWriter().print(jsonText);
	}

	/**
	 * 修改出仓通知单物料信息
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void updateExportApplyMateriel(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String exportMateId = request.getParameter("exportMateId");
		String colName = request.getParameter("colName");
		String colValue = extractFR(request, "colValue");
		JSONObject jsonObject = new JSONObject();
		try {
			shipNotifyJdbcService.updateExportApplyMateriel(exportMateId,
					colName, colValue);
			// 回调更新总额
			if (colName.equals("QUANTITY") || colName.equals("PRICE")
					|| colName.equals("PEINH")) {
				TExportApplyMaterial tExportApplyMaterial = shipNotifyHibernateService
						.getExportApplyMaterial(exportMateId);
				jsonObject.put("total", tExportApplyMaterial.getTotal());
			}
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
	 * 删除出仓单明细资料
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void deleteDetail(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String exportMateId = request.getParameter("exportMateId");
		shipNotifyHibernateService.deleteMaterial(exportMateId);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("returnMessage", "删除成功!");
		String jsontext = jsonObject.toString();
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		this.operateSuccessfullyWithString(response, jsontext);
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
		String contractSalesId = request.getParameter("contractSalesId");
		String tradeType = request.getParameter("tradeType");
		String shipType = request.getParameter("shipType");
		request.setAttribute("contractSalesId", contractSalesId);
		request.setAttribute("tradeType", tradeType);
		request.setAttribute("shipType", shipType);
		return new ModelAndView("sapss/ship/findSalesMateriel");
	}

	/**
	 * 转到查询信用证号窗口
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView toFindLcNo(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String contractNo = request.getParameter("contractNo");
		String tradeType = request.getParameter("tradeType");
		request.setAttribute("contractNo", contractNo);
		request.setAttribute("tradeType", tradeType);
		String contractSalesId = request.getParameter("contractSalesId");
		request.setAttribute("contractSalesId", contractSalesId);
		return new ModelAndView("sapss/ship/findLcNo");
	}
	/**
	 * 转到查询外客户窗口
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView toFindCus(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return new ModelAndView("sapss/ship/findCus");
	}
	public void rtnFindLcNo(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// LJX 20090424 出口货物通知单申请：信用证选择的是 收证的信用证，且状态为备用的信用证 \改证通过的信用证
		Map map = request.getParameterMap();
		Map<String, String> filter = shipNotifyJdbcService.getFilter(map);
		String sql = shipNotifyJdbcService.getQueryLcNo(filter);

		String grid_columns = "CREDIT_NO,COUNTRY,CONTRACT_NO,CONTRACT_NAME";
		String grid_size = "10";

		request.setAttribute("grid_sql", sql);
		request.setAttribute("grid_columns", grid_columns);
		request.setAttribute("grid_size", grid_size);

		log.debug("取信用证选择列表数据SQL:" + sql);
		ExtComponentServlet extComponentServlet = new ExtComponentServlet();
		extComponentServlet.processGrid(request, response, true);
	}
	public void rtnFindCus(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map map = request.getParameterMap();
		Map<String, String> filter = shipNotifyJdbcService.getFilter(map);
		String sql = shipNotifyJdbcService.getQueryCus(filter);

		String grid_columns = "ID,TITLE";
		String grid_size = "10";

		request.setAttribute("grid_sql", sql);
		request.setAttribute("grid_columns", grid_columns);
		request.setAttribute("grid_size", grid_size);

		log.debug("取外客户选择列表数据SQL:" + sql);
		ExtComponentServlet extComponentServlet = new ExtComponentServlet();
		extComponentServlet.processGrid(request, response, true);
	}
	/**
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
		String shipType = request.getParameter("shipType");
		Map<String, String> filter = new HashMap();

		filter.put("contractSalesId", contractSalesId);
		filter.put("tradeType", tradeType);
		filter.put("materialCode", materialCode);
		filter.put("materialName", materialName);
		filter.put("shipType", shipType);

		String sql = shipNotifyJdbcService.getQuerySalesMaterielSql(filter);
		String grid_columns = "SALES_ROWS_ID,MATERIAL_CODE,MATERIAL_NAME,MATERIAL_UNIT,TOTAL_QUANTITY,RATE,PRICE,CURRENCY,KPEIN,TOTAL,APPLY_QUANTITY,HAS_QUANTITY";
		String grid_size = "20";

		request.setAttribute("grid_sql", sql);
		request.setAttribute("grid_columns", grid_columns);
		request.setAttribute("grid_size", grid_size);

		ExtComponentServlet extComponentServlet = new ExtComponentServlet();
		extComponentServlet.processGrid(request, response, true);
	}

	/**
	 * 进入查询选择销售合同界面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ModelAndView selectSalesInfo(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String depId = request.getParameter("depId");
		String orderState = request.getParameter("orderState");
		String tradeType = request.getParameter("tradeType");
		request.setAttribute("depId", depId);
		request.setAttribute("orderState", orderState);
		request.setAttribute("tradeType", tradeType);
		return new ModelAndView("sapss/ship/selectSalesInfo");
	}

	/**
	 * 查询销售合同信息
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	public void querySalesInfo(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		Map map = request.getParameterMap();
		Map<String, String> filter = shipNotifyJdbcService.getFilter(map);

		String sql = shipNotifyJdbcService.getQuerySalesSql(filter);
		String grid_columns = "PROJECT_NO,PROJECT_NAME,CONTRACT_GROUP_NO,CONTRACT_GROUP_NAME," +
				"CONTRACT_SALES_ID,CONTRACT_NO,CONTRACT_NAME,SAP_ORDER_NO,VBKD_IHREZ,KUWEV_KUNNR_NAME,VBKD_INCO1,VBKD_INCO2,"+
				"vbkd_zlsch_d_pay_type,shipment_date,shipment_port,destination_port,collection_Date";
		String grid_size = "20";

		request.setAttribute("grid_sql", sql);
		request.setAttribute("grid_columns", grid_columns);
		request.setAttribute("grid_size", grid_size);

		ExtComponentServlet extComponentServlet = new ExtComponentServlet();
		extComponentServlet.processGrid(request, response, true);
	}

	/**
	 * 提交并保存出货通知单
	 * 
	 * @param request
	 * @param response
	 * @param tExportApply
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws IOException
	 * @throws Exception
	 */
	public void submitAndSaveExportApply(HttpServletRequest request,
			HttpServletResponse response, TExportApply tExportApply)
			throws IllegalAccessException, InvocationTargetException,
			IOException, Exception {
		String taskId = request.getParameter("workflowTaskId");
		shipNotifyHibernateService.submitAndSaveExportApply(taskId,
				tExportApply);
		JSONObject jo = new JSONObject();
		jo.put("returnMessage", "提交成功！");
		String jsontext = jo.toString();
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		this.operateSuccessfullyWithString(response, jsontext);
	}

	/**
	 * 提交出货通知单
	 * 
	 * @param request
	 * @param response
	 * @param tExportApply
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws IOException
	 * @throws Exception
	 */
	public void submitExportApply(HttpServletRequest request,
			HttpServletResponse response, TExportApply tExportApply)
			throws IllegalAccessException, InvocationTargetException,
			IOException, Exception {

		String taskId = request.getParameter("workflowTaskId");
		String taskName = (String) request
				.getParameter("workflowCurrentTaskName");
		shipNotifyHibernateService.checkSubmit(taskId, taskName, tExportApply);
		shipNotifyHibernateService.submitExportApply(taskId, tExportApply);
		JSONObject jo = new JSONObject();
		jo.put("returnMessage", "提交成功！");
		String jsontext = jo.toString();
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		this.operateSuccessfullyWithString(response, jsontext);
	}

	/**
	 * 进入流程审批出货通知单界面
	 * 
	 * @param request
	 * @param resp
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public ModelAndView shipNotifyExamine(HttpServletRequest request,
			HttpServletResponse resp) throws IllegalAccessException,
			InvocationTargetException {
		String exportApplyId = request.getParameter("businessRecordId");
		String taskId = request.getParameter("taskId");
		request.setAttribute("taskId", taskId);
		request.setAttribute("businessRecordId", exportApplyId);
		TExportApply tExportApply = this.shipNotifyHibernateService
				.getExportApply(exportApplyId);

		UserContext userContext = UserContextHolder.getLocalUserContext()
				.getUserContext();
		Boolean modify = false;
		// 创建者能修改：
		if (tExportApply.getCreator().equals(
				userContext.getSysUser().getUserId())) {
			modify = true;
		}
		// 流程返回业务修改进入修改页面
		if (modify) {
			request.setAttribute("iframeSrc",
					"shipNotifyController.spr?action=addShipNotifyView&operType=10&exportApplyId="
							+ exportApplyId);
		} else {
			request.setAttribute("iframeSrc",
					"shipNotifyController.spr?action=examineShipNotifyView&exportApplyId="
							+ exportApplyId);
		}
		String submitUrl = "shipNotifyController.spr?action=submitExportApply";
		request.setAttribute("submitUrl", submitUrl);
		return new ModelAndView("sapss/workflow/commonProcessPage");
	}

	/*
	 * 进入总的出仓单查询界面
	 * 
	 * @param request @param response @return @throws IOException
	 */
	public ModelAndView ArchShipNotifyManageView(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		request.setAttribute("deptShortCode", UserContextHolder.getLocalUserContext()
				.getUserContext().getSysDept().getDeptShortCode());
		return new ModelAndView("sapss/ship/Archives/ArchShipNotifyManage");
	}

	/*
	 * 查询物料的库存数量
	 * 
	 * @param request @param response @return @throws IOException
	 */
	public void queryInventoryNum(HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		String materialCode = request.getParameter("materialCode");
		String warehouse = request.getParameter("warehouse");
		String batchNo = request.getParameter("batchNo");
		JSONObject jsonObject = new JSONObject();

		double inventoryNum = shipNotifyHibernateService.queryInventoryNum(
				materialCode, warehouse, batchNo);

		jsonObject.put("inventoryNum", Double.toString(inventoryNum));
		String jsontext = jsonObject.toString();
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		this.operateSuccessfullyWithString(response, jsontext);
	}
	
	public ModelAndView notifyInfoView(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		return new ModelAndView("sapss/ship/Archives/ShipNotifyInfo");
	}
	
	public void queryNotifyInfo(HttpServletRequest request, HttpServletResponse response)
	throws IOException, ServletException {
		Map<String, String> filter = extractFR(request);
//		Map<String, String> filter = shipNotifyJdbcService.getFilter(map);
		String sql = shipNotifyJdbcService.getQueryNotifyInfoSql(filter);
		String grid_columns = "DEPT_NAME,PROJECT_NO,PROJECT_NAME,MATERIAL_NAME,MATERIAL_UNIT,QUANTITY,PRICE,TOTAL,CURRENCY," +
				"WRITE_NO,GET_SHEET_TIME,CREATOR_TIME,EXPORT_DATE,EXCHANGE_TYPE,TRADE_TYPE,EXPORT_PORT,DESTINATIONS,CMD";
		String grid_size = "10";
		request.setAttribute("grid_sql", sql);
		request.setAttribute("grid_columns", grid_columns);
		request.setAttribute("grid_size", grid_size);

		ExtComponentServlet extComponentServlet = new ExtComponentServlet();
		extComponentServlet.processGrid(request, response, true);
    }
	
	public void dealOutToExcel(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		String[] titles  = new String[]{"序号","部门","立项号","立项名称","物料名称","物料单位","申请数量","单价","总价","币别","核销单号","领单时间",
				"申报时间","出口时间","收汇方式","贸易方式","出口口岸","目的地","备注"};

		ExcelObject excel = new ExcelObject(titles);
		Map<String, String> filter = extractFR(request);
		String sql = shipNotifyJdbcService.getQueryNotifyInfoSql(filter);

		shipNotifyJdbcService.dealOutToExcel(sql ,excel);
		
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
	
	public void dealOutToExcelV1(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		String[] titles  = new String[]{"序号","立项号","立项名称","合同名称","合同编码","SAP订单号","通知单号","核销单号","总金额"};

		ExcelObject excel = new ExcelObject(titles);
		Map<String, String> filter = extractFR(request);
		String sql = shipNotifyJdbcService.getQueryExportApplySql(filter);

		shipNotifyJdbcService.dealOutToExcelV1(sql ,excel);
		
		try{
			//response.reset();
	        response.setCharacterEncoding("utf-8");
	        response.setContentType("application/x-download;charset=utf-8");
	        response.setHeader("Content-Disposition", (new StringBuilder("attachment;filename=")).append(URLEncoder.encode("出口五联单表.xls", "utf-8")).toString());
			excel.write(response.getOutputStream());
			response.flushBuffer();
			response.getOutputStream().close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
