/*
 * @(#)PickListInfoController.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：张崇镇
 *  时　间：Mar 30, 2009
 *  描　述：创建
 */

package com.infolion.sapss.payment.web;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.HashMap;
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
import com.infolion.platform.console.org.domain.SysDept;
import com.infolion.platform.console.org.domain.SysUser;
import com.infolion.platform.console.org.service.SysDeptService;
import com.infolion.platform.console.org.service.SysUserService;
import com.infolion.platform.console.sys.context.UserContext;
import com.infolion.platform.console.sys.context.UserContextHolder;
import com.infolion.platform.core.web.BaseMultiActionController;
import com.infolion.sapss.payment.dao.PaymentImportsMaterialHibernateDao;
import com.infolion.sapss.payment.domain.TPaymentImportsMaterial;
import com.infolion.sapss.payment.domain.TPickListInfo;
import com.infolion.sapss.payment.service.PickListInfoHibernateService;
import com.infolion.sapss.payment.service.PickListInfoJdbcService;

public class PickListInfoController extends BaseMultiActionController
{
	@Autowired
	private PickListInfoHibernateService pickListInfoHibernateService;
	@Autowired
	private PickListInfoJdbcService pickListInfoJdbcService;
	@Autowired
	PaymentImportsMaterialHibernateDao paymentImportsMaterialHibernateDao;
	@Autowired
	SysDeptService sysDeptService;
	@Autowired
	private SysUserService SysUserService;
	public void setPickListInfoHibernateService(
			PickListInfoHibernateService pickListInfoHibernateService)
	{
		this.pickListInfoHibernateService = pickListInfoHibernateService;
	}

	public void setPickListInfoJdbcService(
			PickListInfoJdbcService pickListInfoJdbcService)
	{
		this.pickListInfoJdbcService = pickListInfoJdbcService;
	}

	public void setPaymentImportsMaterialHibernateDao(
			PaymentImportsMaterialHibernateDao paymentImportsMaterialHibernateDao)
	{
		this.paymentImportsMaterialHibernateDao = paymentImportsMaterialHibernateDao;
	}

	public void setSysDeptService(SysDeptService sysDeptService)
	{
		this.sysDeptService = sysDeptService;
	}

	public void setSysUserService(SysUserService sysUserService) {
		SysUserService = sysUserService;
	}

	/**
	 * 萃取url
	 * 
	 * @param req
	 * @return 参数名,参数值的map
	 */
	private Map<String, String> extractFR(HttpServletRequest req)
	{
		try
		{
			String wait = java.net.URLDecoder.decode(req.getQueryString(),
					"UTF-8");
			String ar1[] = wait.split("&");
			Map<String, String> map = new HashMap<String, String>();
			String str = "";
			String ar2[];
			for (int i = 0; i < ar1.length; i++)
			{
				str = ar1[i];
				ar2 = str.split("=");
				if (ar2.length == 1)
					continue;
				map.put(ar2[0], ar2[1]);
			}
			return map;
		}
		catch (UnsupportedEncodingException e)
		{}
		return Collections.EMPTY_MAP;
	}

	/**
	 * 入口方法
	 * 
	 * @param request
	 * @param response
	 * @return 
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public ModelAndView entry(HttpServletRequest request,
			HttpServletResponse response) throws IllegalAccessException,
			InvocationTargetException
	{
		String pickListType = request.getParameter("pickListType");
		String tag = pickListInfoHibernateService.getPickListTag(pickListType);
		request.setAttribute("pickListType", pickListType);
		UserContext userContext = UserContextHolder.getLocalUserContext()
				.getUserContext();
		String loginer = userContext.getSysUser().getUserId();
		request.setAttribute("loginer", loginer);
		return new ModelAndView("sapss/payment/pickList/pickListManager" + tag);
	}
	/**
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	public void query(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException
	{
		//Map map = extractFR(request);//request.getParameterMap();
		Map<String, String> filter = extractFR(request);//pickListInfoJdbcService.getFilter(map);
		String sql = pickListInfoJdbcService.getQueryPickListInfoSql(filter);
		String grid_columns = "PICK_LIST_ID,CONTRACT_PURCHASE_ID,TRADE_TYPE,PICK_LIST_TYPE,PICK_LIST_NO,ISSUING_BANK,LC_NO,"
				+ "COLLECTION_BANK,CONTRACT_NO,SAP_ORDER_NO,DP_DA_NO,ISSUING_DATE,SHIPMENT_PORT,DESTINATION_PORT,ARRIVAL_DATE,"
				+ "PICK_LIST_REC_DATE,SECURITY_PICK_DATE,ACCEPTANCE_DATE,PAY_DATE,HAS_REC_WRITE,WRITE_LIST_NO,EXAMINE_ADVICE,"
				+ "DEPT_ID,APPLY_TIME,APPROVED_TIME,CMD,IS_AVAILABLE,CREATOR_TIME,CREATOR,EXAMINE_STATE,EXAMINE_STATE_D_EXAMINE_STATE,"
				+ "DEPT_NAME,REAL_NAME,TYPE_NAME";
		String grid_size = "10";

		request.setAttribute("grid_sql", sql);
		request.setAttribute("grid_columns", grid_columns);
		request.setAttribute("grid_size", grid_size);

		ExtComponentServlet extComponentServlet = new ExtComponentServlet();
		extComponentServlet.processGrid(request, response, true);
	}

	/**
	 * 新增进口到单
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView addPickListInfo(HttpServletRequest request,
			HttpServletResponse response) throws Exception
	{
		UserContext uc = UserContextHolder.getLocalUserContext().getUserContext();
		String pickListType = request.getParameter("pickListType");
		request.setAttribute("pickListType", pickListType);
		TPickListInfo tPickListInfo = pickListInfoHibernateService
				.addPickListInfo(pickListType);
		String tag = pickListInfoHibernateService.getPickListTag(pickListType);
		if (StringUtils.isNotBlank(tPickListInfo.getDeptId()))
		{
			SysDept sysDept = sysDeptService.queryDeptById(tPickListInfo
					.getDeptId());
			request.setAttribute("selectId_dept", sysDept.getDeptid());
			request.setAttribute("selectText_dept", sysDept.getDeptname());
			request.setAttribute("sysDept", sysDept);
		}
		if("home".equals(pickListType)){
		    tPickListInfo.setCurrencyId("CNY");
		}
		request.setAttribute("tPickListInfo", tPickListInfo);
		request.setAttribute("creatorName", uc.getSysUser().getRealName());
		return new ModelAndView("sapss/payment/pickList/createPickList" + tag);
	}

	/**
	 * 到单删除动作
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void delete(HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		String pickListId = request.getParameter("pickListId");
		pickListInfoHibernateService.delPickListInfo(pickListId);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("returnMessage", "删除成功!");
		String jsontext = jsonObject.toString();
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		this.operateSuccessfullyWithString(response, jsontext);
	}

	/**
	 * 修改到单界面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ModelAndView addPickListInfoView(HttpServletRequest request,
			HttpServletResponse response) throws IOException
	{
		UserContext uc = UserContextHolder.getLocalUserContext().getUserContext();
		request.setAttribute("from", request.getParameter("from"));
		String pickListId = request.getParameter("pickListId");
		String pickListType = request.getParameter("pickListType");
		TPickListInfo tPickListInfo = pickListInfoHibernateService
				.getPickListInfo(pickListId);
		request.setAttribute("tPickListInfo", tPickListInfo);
		SysDept sysDept = new SysDept();
		if (StringUtils.isNotBlank(tPickListInfo.getDeptId()))
		{
			sysDept = sysDeptService.queryDeptById(tPickListInfo.getDeptId());
			request.setAttribute("selectId_dept", sysDept.getDeptid());
			request.setAttribute("selectText_dept", sysDept.getDeptname());
		}
		request.setAttribute("sysDept", sysDept);
		String tag = pickListInfoHibernateService.getPickListTag(pickListType);
		request.setAttribute("creatorName", uc.getSysUser().getRealName());
		return new ModelAndView("sapss/payment/pickList/createPickList" + tag);
	}

	/**
	 * 编辑修改到单动作
	 * 
	 * @param request
	 * @param response
	 * @param tExportApply
	 * @throws IOException
	 */
	public void updatePickListInfo(HttpServletRequest request,
			HttpServletResponse response, TPickListInfo tPickListInfo)
			throws IOException
	{
		JSONObject jo = new JSONObject();
		if(pickListInfoJdbcService.isExistBillNo(tPickListInfo.getPickListId(),tPickListInfo.getPickListNo())){
			jo.put("returnMessage", "保存失败，到单号重复，请检查！");
		}
		else {
			pickListInfoHibernateService.savePickListInfo(tPickListInfo);
			jo.put("returnMessage", "保存成功！");
		}
		String jsontext = jo.toString();
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");

		this.operateSuccessfullyWithString(response, jsontext);
	}

	/**
	 * 进入查询采购合同界面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ModelAndView selectPurchaseInfo(HttpServletRequest request,
			HttpServletResponse response) throws IOException
	{
		String depId = request.getParameter("depId");
		String orderState = request.getParameter("orderState");
		request.setAttribute("depId", depId);
		request.setAttribute("orderState", orderState);
		return new ModelAndView("sapss/payment/pickList/selectPurchaseInfo");
	}
	
	/**
	 * 进入查询信用证界面
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ModelAndView selectLcNoInfo(HttpServletRequest request,
			HttpServletResponse response) throws IOException
	{
		return new ModelAndView("sapss/payment/pickList/selectLcNoInfo");
	}
	

	/**
	 * 查询采购合同信息
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	public void queryPurchaseInfo(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException
	{
		Map<String, String> map = extractFR(request);
		String sql = pickListInfoJdbcService.getPurchaseQuerySql(map);

		String grid_columns = "PROJECT_ID,PROJECT_NO,PROJECT_NAME,CONTRACT_GROUP_NO,CONTRACT_GROUP_NAME,EKKO_ZTERM_D_PAYMENT_TYPE," +
				"CONTRACT_PURCHASE_ID,CONTRACT_NO,CONTRACT_NAME,SAP_ORDER_NO,EKKO_INCO1,EKKO_WAERS,EKKO_LIFNR_NAME,YMAT_GROUP";

		String grid_size = "20";
		request.setAttribute("grid_sql", sql);
		request.setAttribute("grid_columns", grid_columns);
		request.setAttribute("grid_size", grid_size);

		ExtComponentServlet extComponentServlet = new ExtComponentServlet();
		extComponentServlet.processGrid(request, response, true);
	}
	
	/**
	 * 查询信用证合同信息
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	public void queryLcNoInfo(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException
	{
		Map<String, String> map = extractFR(request);
		String sql = pickListInfoJdbcService.getLcNoQuerySql(map);

		String grid_columns = "CREATE_BANK,CREATE_DATE,WRITE_OFF_SINGLE_NO,CONTRACT_PURCHASE_ID,CREDIT_NO,GOODS," +
				"CONTRACT_NO,CONTRACT_NAME,SAP_ORDER_NO,EKKO_WAERS,EKKO_LIFNR_NAME,AVAIL_DATE,YMAT_GROUP";

		String grid_size = "20";
		request.setAttribute("grid_sql", sql);
		request.setAttribute("grid_columns", grid_columns);
		request.setAttribute("grid_size", grid_size);

		ExtComponentServlet extComponentServlet = new ExtComponentServlet();
		extComponentServlet.processGrid(request, response, true);
	}	

	/**
	 * 转到查询采购合同物料窗口
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView toFindPurchaseMaterial(HttpServletRequest request,
			HttpServletResponse response) throws Exception
	{
		String contractPurchaseId = request.getParameter("contractPurchaseId");
		String tradeType = request.getParameter("tradeType");
		request.setAttribute("contractPurchaseId", contractPurchaseId);
		request.setAttribute("tradeType", tradeType);
		return new ModelAndView("sapss/payment/pickList/findPurchaseMateriel");
	}

	/**
	 * 查询采购合同物料
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	public void queryPurchaseMateriel(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException
	{
		Map<String, String> filter = extractFR(request);
		String sql = pickListInfoJdbcService
				.getQueryPurchaseMaterielSql(filter);
		String grid_columns = "PURCHASE_ROWS_ID,EKPO_MATNR,EKPO_TXZ01,EKPO_MEINS,EKPO_MENGE,FLOW_NO,TAXES,TOTAL_TAXES";
		String grid_size = "20";

		System.out.println(sql);

		request.setAttribute("grid_sql", sql);
		request.setAttribute("grid_columns", grid_columns);
		request.setAttribute("grid_size", grid_size);

		ExtComponentServlet extComponentServlet = new ExtComponentServlet();
		extComponentServlet.processGrid(request, response, true);
	}

	/**
	 * 初始化到单信息
	 * 
	 * @param request
	 * @param response
	 */
	public void initPickListInfoMt(HttpServletRequest request,
			HttpServletResponse response)
	{
		String contractPurchaseId = request.getParameter("contractPurchaseId");
		String pickListId = request.getParameter("pickListId");
		String lcNo = request.getParameter("lcNo");

		pickListInfoHibernateService.initPickListInfoMt(pickListId,
				contractPurchaseId, lcNo);

	}

	/**
	 * 获取到单物料信息
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void queryMaterial(HttpServletRequest request,
			HttpServletResponse response) throws Exception
	{
		String pickListId = request.getParameter("pickListId");
		String sql = "select t.* from t_payment_imports_material t where pick_list_id = '"
				+ pickListId + "'";
		String grid_columns = "PURCHASE_ROWS_ID,PICK_LIST_ID,EKPO_MATNR,EKPO_MEINS,EKPO_TXZ01,EKPO_MENGE,EKPO_BPRME,TOTAL_VALUE,TAX";
		String grid_size = "10";
		request.setAttribute("grid_sql", sql);
		request.setAttribute("grid_columns", grid_columns);
		request.setAttribute("grid_size", grid_size);

		ExtComponentServlet extComponentServlet = new ExtComponentServlet();
		extComponentServlet.processGrid(request, response, true);
	}

	/**
	 * 新增物料
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void addDetail(HttpServletRequest request,
			HttpServletResponse response) throws IOException
	{
		String pickListId = request.getParameter("pickListId");
		String purchaseRowsId = request.getParameter("purchaseRowsId");

		TPaymentImportsMaterial tPaymentImportsMaterial = pickListInfoHibernateService
				.addMtByCon(pickListId, purchaseRowsId);

		JSONObject jsonObject = JSONObject.fromObject(tPaymentImportsMaterial);
		String jsonText = jsonObject.toString();
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		response.getWriter().print(jsonText);
	}

	/**
	 * 删除物料
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void deleteMateriel(HttpServletRequest request,
			HttpServletResponse response) throws Exception
	{
		String purchaseRowsId = request.getParameter("purchaseRowsId");
		pickListInfoHibernateService.deleteMaterial(purchaseRowsId);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("returnMessage", "删除成功!");
		String jsontext = jsonObject.toString();
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		this.operateSuccessfullyWithString(response, jsontext);
	}

	/**
	 * 更新物料信息
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void updateMateriel(HttpServletRequest request,
			HttpServletResponse response) throws IOException
	{
		String purchaseRowsId = request.getParameter("purchaseRowsId");
		String colName = request.getParameter("colName");
		String colValue = request.getParameter("colValue");
		JSONObject jsonObject = new JSONObject();
		pickListInfoJdbcService.updateMateriel(purchaseRowsId, colName,
				colValue);
		String jsontext = jsonObject.toString();
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		this.operateSuccessfullyWithString(response, jsontext);
	}

	/**
	 * 保存并提交到单信息
	 * 
	 * @param request
	 * @param response
	 * @param tShipInfo
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws IOException
	 * @throws Exception
	 */
	public void saveAndSubmitPickListInfo(HttpServletRequest request,
			HttpServletResponse response, TPickListInfo tPickListInfo)
			throws IllegalAccessException, InvocationTargetException,
			IOException, Exception
	{
		JSONObject jo = new JSONObject();
		String taskId = request.getParameter("workflowTaskId");
		if((null==taskId||"".equals(taskId))&&pickListInfoJdbcService.isExistBillNo(tPickListInfo.getPickListId(),tPickListInfo.getPickListNo())){
			jo.put("returnMessage", "提交失败，到单号重复，请检查！");
		}
		else {
			pickListInfoHibernateService.saveAndSubmitPickListInfo(taskId,tPickListInfo);
			jo.put("returnMessage", "提交成功！");
		}
		String jsontext = jo.toString();
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		this.operateSuccessfullyWithString(response, jsontext);
	}

	/**
	 * 进入流程审批到单界面
	 * 
	 * @param request
	 * @param resp
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public ModelAndView pickListInfoExamine(HttpServletRequest request,
			HttpServletResponse resp) throws IllegalAccessException,
			InvocationTargetException
	{
		String pickListId = request.getParameter("businessRecordId");
		String taskId = request.getParameter("taskId");
		request.setAttribute("taskId", taskId);
		request.setAttribute("businessRecordId", pickListId);
		TPickListInfo tPickListInfo = pickListInfoHibernateService
				.getPickListInfo(pickListId);

		UserContext userContext = UserContextHolder.getLocalUserContext()
				.getUserContext();
		Boolean modify = false;
		// 创建者能修改：
		if (tPickListInfo.getCreator().equals(userContext.getSysUser().getUserId())||userContext.getSysDept().getDeptname().equals("贸管部")||userContext.getSysDept().getDeptname().equals("风险管理部"))
		{
			modify = true;
		}
		// 业务修改权限进入修改页面
		if (modify)
		{
			request.setAttribute("iframeSrc",
					"pickListInfoController.spr?action=addPickListInfoView&pickListType="
							+ tPickListInfo.getTradeType() + "&pickListId="+ pickListId+"&from=workflow");
		}
		else
		{
			request.setAttribute("iframeSrc",
					"pickListInfoController.spr?action=examinePickListInfoView&pickListType="
							+ tPickListInfo.getTradeType() + "&pickListId="
							+ pickListId);
		}
		String submitUrl = "pickListInfoController.spr?action=submitPickListInfo";
		request.setAttribute("submitUrl", submitUrl);
		return new ModelAndView("sapss/workflow/commonProcessPage");
	}

	/**
	 * 提交到单流程
	 * 
	 * @param request
	 * @param response
	 * @param tPickListInfo
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws IOException
	 * @throws Exception
	 */
	public void submitPickListInfo(HttpServletRequest request,
			HttpServletResponse response, TPickListInfo tPickListInfo)
			throws IllegalAccessException, InvocationTargetException,
			IOException, Exception
	{

		String taskId = request.getParameter("workflowTaskId");
		pickListInfoHibernateService.submitPickListInfo(taskId, tPickListInfo);
		JSONObject jo = new JSONObject();
		jo.put("returnMessage", "提交成功！");
		String jsontext = jo.toString();
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		this.operateSuccessfullyWithString(response, jsontext);
	}

	/**
	 * 进入到单流程审批界面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ModelAndView examinePickListInfoView(HttpServletRequest request,
			HttpServletResponse response) throws IOException
	{
		String pickListId = request.getParameter("pickListId");
		TPickListInfo tPickListInfo = pickListInfoHibernateService
				.getPickListInfo(pickListId);

		SysDept sysDept = sysDeptService.queryDeptById(tPickListInfo.getDeptId());
		request.setAttribute("selectId_dept", sysDept.getDeptid());
		request.setAttribute("selectText_dept", sysDept.getDeptname());
		request.setAttribute("sysDept", sysDept);
		UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
		request.setAttribute("tPickListInfo", tPickListInfo);
		String tag = pickListInfoHibernateService.getPickListTag(tPickListInfo.getTradeType());
		List<SysUser> list = this.SysUserService.queryUserByUserId(tPickListInfo.getCreator());
		if(list!=null && list.size()>0){
			request.setAttribute("creatorName", list.get(0).getRealName());
		}
		return new ModelAndView("sapss/payment/pickList/viewPickList" + tag);

	}

	/**
	 * 查看进口到单
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView viewPickListInfo(HttpServletRequest request,
			HttpServletResponse response) throws Exception
	{
		String pickListType = request.getParameter("pickListType");
		String pickListId = request.getParameter("pickListId");
		TPickListInfo tPickListInfo = new TPickListInfo();
		if (StringUtils.isNotBlank(pickListId)){
			 tPickListInfo = pickListInfoHibernateService.getPickListInfo(pickListId);	
			 pickListType = tPickListInfo.getTradeType();
		}
		if (StringUtils.isNotBlank(tPickListInfo.getDeptId()))
		{
			SysDept sysDept = sysDeptService.queryDeptById(tPickListInfo
					.getDeptId());
			request.setAttribute("selectId_dept", sysDept.getDeptid());
			request.setAttribute("selectText_dept", sysDept.getDeptname());
			request.setAttribute("sysDept", sysDept);
		}		
		
		request.setAttribute("pickListType", pickListType);
		request.setAttribute("tPickListInfo", tPickListInfo);
		String tag = pickListInfoHibernateService.getPickListTag(pickListType);
		if(tPickListInfo.getCreator()!=null && !"".equals(tPickListInfo.getCreator())){
			List<SysUser> list = this.SysUserService.queryUserByUserId(tPickListInfo.getCreator());
			if(list!=null && list.size()>0){
				request.setAttribute("creatorName", list.get(0).getRealName());
			}
		}
		return new ModelAndView("sapss/payment/pickList/viewPickList" + tag);
	}
	/**
	 * 综合查询入口
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView compositeQuery(HttpServletRequest request,HttpServletResponse response){
		return new ModelAndView("sapss/payment/pickList/pickListCompositeQuery");
	}
	/**
	 * 查看合同
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void showContract(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
		String pickListId = request.getParameter("businessRecordId");
		TPickListInfo info = this.pickListInfoHibernateService.getPickListInfo(pickListId);
		request.getRequestDispatcher("contractController.spr?action=ArchPurchaseInfoView&businessRecordId="+info.getContractPurchaseId()).forward(request, response);
	}
}
