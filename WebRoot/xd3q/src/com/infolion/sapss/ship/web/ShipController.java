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
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collection;
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
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

import com.infolion.platform.component.ExtComponentServlet;
import com.infolion.platform.component.dictionary.DictionaryRow;
import com.infolion.platform.component.workflow.WorkflowService;
import com.infolion.platform.component.workflow.ext.TaskInstanceContext;
import com.infolion.platform.console.org.domain.SysDept;
import com.infolion.platform.console.org.domain.SysRole;
import com.infolion.platform.console.org.domain.SysUser;
import com.infolion.platform.console.org.service.SysDeptService;
import com.infolion.platform.console.org.service.SysUserService;
import com.infolion.platform.console.sys.context.UserContext;
import com.infolion.platform.console.sys.context.UserContextHolder;
import com.infolion.platform.console.workflow.service.SysWfUtilsService;
import com.infolion.platform.core.web.BaseMultiActionController;
import com.infolion.platform.sys.cache.SysCachePoolUtils;
import com.infolion.sapss.common.WorkflowUtils;
import com.infolion.sapss.contract.service.ContractHibernateService;
import com.infolion.sapss.ship.domain.TExportApply;
import com.infolion.sapss.ship.domain.TShipInfo;
import com.infolion.sapss.ship.domain.TShipMaterial;
import com.infolion.sapss.ship.service.CheckCustomerSendCreditSerice;
import com.infolion.sapss.ship.service.ShipHibernateService;
import com.infolion.sapss.ship.service.ShipJdbcService;
import com.infolion.sapss.ship.service.ShipNotifyHibernateService;
import com.infolion.sapss.ship.service.ShipNotifyJdbcService;

/**
 * 
 * <pre>
 * 货物出仓控制器
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
public class ShipController extends BaseMultiActionController
{
	private final Log log = LogFactory.getLog(ShipController.class);
	// 服务类注入
	@Autowired
	private ShipHibernateService shipHibernateService;
	@Autowired
	private ShipJdbcService shipJdbcService;
	@Autowired
	ContractHibernateService contractHibernateService;
	@Autowired
	private ShipNotifyHibernateService shipNotifyHibernateService;
	@Autowired
	ShipNotifyJdbcService shipNotifyJdbcService;
	@Autowired
	SysDeptService sysDeptService;
	@Autowired
	CheckCustomerSendCreditSerice checkCustomerSendCreditSerice;
	
	public void setShipService(ShipHibernateService shipService)
	{
		this.shipHibernateService = shipService;
	}

	public void setShipHibernateService(
			ShipHibernateService shipHibernateService)
	{
		this.shipHibernateService = shipHibernateService;
	}

	public void setShipJdbcService(ShipJdbcService shipJdbcService)
	{
		this.shipJdbcService = shipJdbcService;
	}

	public void setContractHibernateService(
			ContractHibernateService contractHibernateService)
	{
		this.contractHibernateService = contractHibernateService;
	}

	public void setShipNotifyHibernateService(
			ShipNotifyHibernateService shipNotifyHibernateService)
	{
		this.shipNotifyHibernateService = shipNotifyHibernateService;
	}

	public void setShipNotifyJdbcService(
			ShipNotifyJdbcService shipNotifyJdbcService)
	{
		this.shipNotifyJdbcService = shipNotifyJdbcService;
	}
	
	

	public void setSysDeptService(SysDeptService sysDeptService)
	{
		this.sysDeptService = sysDeptService;
	}

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
			InvocationTargetException
	{
		return new ModelAndView();
	}

	/**
	 * 萃取URL参数
	 * 
	 * @param req
	 * @param parm
	 * @return parm对应的参数值
	 */
	private String extractFR(HttpServletRequest req, String parm)
	{
		try
		{
			String wait = java.net.URLDecoder.decode(req.getQueryString(),
					"UTF-8");
			int pos = wait.indexOf(parm) + parm.length() + 1;
			wait = wait.substring(pos);
			pos = wait.indexOf("&");
			if (pos != -1)
			{
				wait = wait.substring(0, pos);
			}
			return wait;
		}
		catch (UnsupportedEncodingException e)
		{}
		return null;
	}
	
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
	 * 转到出仓单管理页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public ModelAndView shipManageView(HttpServletRequest request,
			HttpServletResponse response) throws IllegalAccessException,
			InvocationTargetException
	{
		// 贸易类型
		String tradeType = request.getParameter("tradeType");
		String tradeTypeName = SysCachePoolUtils.getDictDataValue("bm_business_type", tradeType);
		request.setAttribute("tradeTypeName", tradeTypeName);

		UserContext userContext = UserContextHolder.getLocalUserContext()
				.getUserContext();
		String loginer = userContext.getSysUser().getUserId();
		request.setAttribute("tradeType", tradeType);
		request.setAttribute("loginer", loginer);
		return new ModelAndView("sapss/ship/shipManage");
	}

	/**
	 * 查询出仓单信息
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	public void query(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException
	{
		//Map map = request.getParameterMap();
		Map<String, String> filter = extractFR(request);
		//Map<String, String> filter = shipJdbcService.getFilter(map);
		String sql = shipJdbcService.getQueryShipSql(filter);
		String grid_columns = "SHIP_ID,EXPORT_APPLY_ID,CONTRACT_SALES_ID,TRADE_TYPE,PROJECT_NO,PROJECT_NAME,CONTRACT_GROUP_NO,SALES_NO,SAP_ORDER_NO,SAP_RETURN_NO,SHIP_NO,SERIALNO,"
				+ "DECLARATIONS_NO,WAREHOUSE,WAREHOUSE_ADD,BILL_STATE,SHIP_OPERATOR,SHIP_NOTE,SHIP_TIME,DEPT_ID,EXAMINE_STATE,BILL_STATE,"
				+ "RSEXAM_STATE_D_RSEXAM_STATE,APPLY_TIME,APPROVED_TIME,IS_AVAILABLE,CREATOR_TIME,CREATOR,CONTRACT_PAPER_NO,REAL_NAME";
		String grid_size = "10";

		request.setAttribute("grid_sql", sql);
		request.setAttribute("grid_columns", grid_columns);
		request.setAttribute("grid_size", grid_size);

		ExtComponentServlet extComponentServlet = new ExtComponentServlet();
		extComponentServlet.processGrid(request, response, true);
	}

	/**
	 * 新增出仓单界面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public void addShipInfo(HttpServletRequest request,
			HttpServletResponse response) throws IOException
	{

		String tradeType = request.getParameter("tradeType");
		String isProduct = request.getParameter("isProduct");
		String isHome = request.getParameter("isHome");;
		request.setAttribute("tradeType", tradeType);
		TShipInfo tShipInfo = shipHibernateService.addShipInfo(tradeType,
				isProduct,isHome);

		JSONObject jsonObject = JSONObject.fromObject(tShipInfo);
		String jsontext = jsonObject.toString();
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		response.getWriter().print(jsontext);
	}

	/**
	 * 进入流程出仓单界面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ModelAndView examineShipInfoView(HttpServletRequest request,
			HttpServletResponse response) throws IOException
	{
		String shipId = request.getParameter("shipId");
		TShipInfo tShipInfo = shipHibernateService.getShipInfo(shipId);
		TExportApply tExportApply = new TExportApply();
		String exportApplyId = tShipInfo.getExportApplyId();
		if (StringUtils.isNotBlank(exportApplyId))
		{
			tExportApply = shipNotifyHibernateService
					.getExportApply(exportApplyId);
		}
		SysDept sysDept = sysDeptService.queryDeptById(tShipInfo.getDeptId());
		UserContext userContext = UserContextHolder.getLocalUserContext()
				.getUserContext();
		request.setAttribute("tShipInfo", tShipInfo);
		request.setAttribute("tExportApply", tExportApply);
		request.setAttribute("sysDept", sysDept);
		
		String operType = request.getParameter("operType");
		request.setAttribute("save", operType.substring(0, 1).equals("0"));
		request.setAttribute("submit", operType.substring(1, 2).equals("0"));
		request.setAttribute("close", operType.substring(2, 3).equals("0"));
		request.setAttribute("postDateEdit", request.getParameter("postDateEdit"));
		request.setAttribute("taskName", request.getParameter("taskName"));
		String tradeType = tShipInfo.getTradeType();
		String tradeTypeName = SysCachePoolUtils.getDictDataValue("bm_business_type", tradeType);
		request.setAttribute("tradeTypeName", tradeTypeName);
		//仓库
		Map<String, DictionaryRow> wareHouseMap = SysCachePoolUtils.getDictTableGroup("BM_WAREHOUSE");
		Collection<DictionaryRow> wareHouse = wareHouseMap.values();
		request.setAttribute("warehouse", wareHouse);
		if(userContext.getSysUser().getDeptId().equals("E3C96C9E-DFF8-4DD2-BB6A-E59162ADA65D")){
			request.setAttribute("saveDate", true);
		}
		if(userContext.getSysUser().getDeptName().indexOf("财务")>=0){
			request.setAttribute("saveMaterial", true);
		}
		//代理物料“当前库存”
		if ("5".equals(tradeType)||"6".equals(tradeType))
			request.setAttribute("inventoryNum", false);
		else
			request.setAttribute("inventoryNum", true);
		
		return new ModelAndView("sapss/ship/shipCreate");
		//return new ModelAndView("sapss/ship/shipExamine");

	}

	/**
	 * 出仓申请单删除动作
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void delete(HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		String shipId = request.getParameter("shipId");
		TShipInfo tShipInfo = shipHibernateService.getShipInfo(shipId);

		tShipInfo.setIsAvailable("0");
		shipHibernateService.saveShipInfo(tShipInfo);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("returnMessage", "删除成功!");
		String jsontext = jsonObject.toString();
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		this.operateSuccessfullyWithString(response, jsontext);
	}

	/**
	 * 进入编辑出仓单的界面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ModelAndView addShipInfoView(HttpServletRequest request,
			HttpServletResponse response) throws IOException
	{
		//贸易类型
		String tradeType = request.getParameter("tradeType");
		request.setAttribute("tradeType", tradeType);
		

		String shipId = request.getParameter("shipId");
		String operType = request.getParameter("operType");
		
		TShipInfo tShipInfo = shipHibernateService.getShipInfo(shipId);
		if(StringUtils.isNotBlank(tShipInfo.getOldShipId()))
			operType = "0"+operType.substring(1);
		if(tradeType==null) tradeType = tShipInfo.getTradeType();
		String tradeTypeName = SysCachePoolUtils.getDictDataValue("bm_business_type", tradeType);
		request.setAttribute("tradeTypeName", tradeTypeName);
		TExportApply tExportApply = new TExportApply();
		String exportApplyId = tShipInfo.getExportApplyId();
		if (StringUtils.isNotBlank(exportApplyId))
		{
			tExportApply = shipNotifyHibernateService
					.getExportApply(exportApplyId);
		}
		SysDept sysDept = sysDeptService.queryDeptById(tShipInfo.getDeptId());
		request.setAttribute("tShipInfo", tShipInfo);
		request.setAttribute("tExportApply", tExportApply);
		request.setAttribute("sysDept", sysDept);
		request.setAttribute("save", operType.substring(0, 1).equals("0"));
		request.setAttribute("submit", operType.substring(1, 2).equals("0"));
		request.setAttribute("close", operType.substring(2, 3).equals("0"));
		//货币
		Map<String, DictionaryRow> CurrencyMap = SysCachePoolUtils.getDictTableGroup("BM_CURRENCY");
		Collection<DictionaryRow> Currency = CurrencyMap.values();
		request.setAttribute("Currency", Currency);
		//仓库
		Map<String, DictionaryRow> wareHouseMap = SysCachePoolUtils.getDictTableGroup("BM_WAREHOUSE");
		Collection<DictionaryRow> wareHouse = wareHouseMap.values();
		request.setAttribute("warehouse", wareHouse);

		//代理物料“当前库存”
		if ("5".equals(tradeType)||"6".equals(tradeType))
			request.setAttribute("inventoryNum", false);
		else
			request.setAttribute("inventoryNum", true);
		
		specialDepartAction(request,response,tShipInfo);
		UserContext userContext = UserContextHolder.getLocalUserContext()
		.getUserContext();
		if(userContext.getSysUser().getUserName().equals("libq")){
			request.setAttribute("save", false);
		}
		if(userContext.getSysUser().getDeptId().equals("E3C96C9E-DFF8-4DD2-BB6A-E59162ADA65D")){
			request.setAttribute("saveDate", true);
		}
		if(userContext.getSysUser().getDeptName().indexOf("财务")>=0){
			request.setAttribute("saveMaterial", true);
		}
		return new ModelAndView("sapss/ship/shipCreate");
	}
	
	public void saveDate(HttpServletRequest request,	HttpServletResponse response) throws IOException{
		String shipId = request.getParameter("shipId");
		String intendGatherTime = request.getParameter("intendGatherTime");
		String makeInvoiceTime = request.getParameter("makeInvoiceTime");

		TShipInfo tShipInfo = shipHibernateService.getShipInfo(shipId);
		String oldStr = "intendGatherTime:"+tShipInfo.getIntendGatherTime()+"|makeInvoiceTime:"+tShipInfo.getMakeInvoiceTime();
		tShipInfo.setIntendGatherTime(intendGatherTime);
		tShipInfo.setMakeInvoiceTime(makeInvoiceTime);
		String newStr = "intendGatherTime:"+tShipInfo.getIntendGatherTime()+"|makeInvoiceTime:"+tShipInfo.getMakeInvoiceTime();
		shipJdbcService.updateDate(tShipInfo,oldStr,newStr,UserContextHolder.getLocalUserContext().getUserContext().getSysUser().getUserId());
		JSONObject jo = new JSONObject();
		jo.put("returnMessage", "更新成功");		
		String jsontext = jo.toString();
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");

		this.operateSuccessfullyWithString(response, jsontext);
		
	}
	/**
	 * 特殊部门
	 * @param request
	 * @param response
	 */
	private void specialDepartAction(HttpServletRequest request,HttpServletResponse response,TShipInfo info){
		if(!"3".equals(info.getExamineState())){
			request.setAttribute("specialDepart",false);
			return;
		}
		UserContext context = UserContextHolder.getLocalUserContext().getUserContext();
		String dept ="综合";
		String role="仓管员";
		String sd =request.getParameter("specialdepartment");
		if(sd!=null && "yes".equals(sd)){
			if(context.getSysDept().getDeptname().indexOf(dept)>=0){
				List<SysRole> list = context.getGrantedRoles();
				if(list!=null&& list.size()>0){
					for (Iterator iterator = list.iterator(); iterator.hasNext();) {
						SysRole sysRole = (SysRole) iterator.next();
						if(sysRole.getRoleName().indexOf(role)>=0||sysRole.getRoleName().indexOf("信达安综合部人员")>=0){
							request.setAttribute("specialDepart",true);
							return;
						}
					}
				}
			}
		}
		request.setAttribute("specialDepart",false);
	}
	/**
	 * 保存特殊部门的修改
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	public void saveSpecialDepartAction(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String shipId = request.getParameter("shipId");
		String sapReturnNo = request.getParameter("sapReturnNo");
		String cmd = request.getParameter("cmd");
		this.shipJdbcService.saveColumnValue(shipId,"SAP_RETURN_NO",sapReturnNo);
		this.shipJdbcService.saveColumnValue(shipId,"cmd",cmd);
		JSONObject jo = new JSONObject();
		jo.put("returnMessage", "保存成功！");
		String jsontext = jo.toString();
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");

		this.operateSuccessfullyWithString(response, jsontext);

	}
	/**
	 * 保存出仓单
	 * 
	 * @param request
	 * @param response
	 * @param tExportApply
	 * @throws IOException
	 */
	public void updateShipInfo(HttpServletRequest request,
			HttpServletResponse response, TShipInfo tShipInfo)
			throws IOException
	{
		shipHibernateService.saveShipInfo(tShipInfo);
		JSONObject jo = new JSONObject();
		jo.put("returnMessage", "保存成功！");
		String jsontext = jo.toString();
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");

		this.operateSuccessfullyWithString(response, jsontext);
	}

	/**
	 * 通过出仓单ID取的出仓单内容
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void getShipInfo(HttpServletRequest request,
			HttpServletResponse response) throws IOException
	{
		String shipId = request.getParameter("shipId");
		TShipInfo tShipInfo = shipHibernateService.getShipInfo(shipId);
		JSONObject jsonObject = JSONObject.fromObject(tShipInfo);
		String jsontext = jsonObject.toString();
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		response.getWriter().print(jsontext);
	}
	
	/**
	 * 进入查询选择采购合同界面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ModelAndView selectPurchaseInfo(HttpServletRequest request,
			HttpServletResponse response) throws IOException
	{
		String deptId = request.getParameter("deptId");
		String examineState = request.getParameter("examineState");
		String tradeType = request.getParameter("tradeType");
		request.setAttribute("deptId", deptId);
		request.setAttribute("examineState", examineState);
		request.setAttribute("tradeType", tradeType);
		return new ModelAndView("sapss/ship/selectPurchaseInfo");
	}
	
	/**
	 * 进入查询选择出货通知单界面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ModelAndView selectExportApply(HttpServletRequest request,
			HttpServletResponse response) throws IOException
	{
		String deptId = request.getParameter("deptId");
		String examineState = request.getParameter("examineState");
		String tradeType = request.getParameter("tradeType");
		request.setAttribute("deptId", deptId);
		request.setAttribute("examineState", examineState);
		request.setAttribute("tradeType", tradeType);
		return new ModelAndView("sapss/ship/selectExportApply");
	}
	
	public ModelAndView selectBillApply(HttpServletRequest request,
			HttpServletResponse response) throws IOException
	{
		String deptId = request.getParameter("deptId");
		String examineState = request.getParameter("examineState");
		String tradeType = request.getParameter("tradeType");
		request.setAttribute("deptId", deptId);
		request.setAttribute("examineState", examineState);
		request.setAttribute("tradeType", tradeType);
		return new ModelAndView("sapss/ship/selectBillApply");
	}

	public void queryExportApply(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException
	{
		Map map = request.getParameterMap();
		Map<String, String> filter = shipNotifyJdbcService.getFilter(map);
		String sql = shipNotifyJdbcService.getQueryExportApplySql(filter);
		String grid_columns = "EXPORT_APPLY_ID,CONTRACT_SALES_ID,TRADE_TYPE,PROJECT_NO,PROJECT_NAME,CONTRACT_GROUP_NO,SALES_NO,LCNO,TOTAL,TOTAL_QUANTITY,VBAK_WAERK,CUSTOMER,"
				+ "SAP_ORDER_NO,VBKD_IHREZ,KUWEV_KUNNR_NAME,NOTICE_NO,creator,CREATOR_TIME,EXAMINE_STATE,EXAMINE_STATE_D_EXAMINE_STATE,IS_AVAILABLE,CONTRACT_NAME,WRITE_NO,EXPORT_PORT,SUPPLIER";
		String grid_size = "20";
		request.setAttribute("grid_sql", sql);
		request.setAttribute("grid_columns", grid_columns);
		request.setAttribute("grid_size", grid_size);

		ExtComponentServlet extComponentServlet = new ExtComponentServlet();
		extComponentServlet.processGrid(request, response, true);
	}
	
	
	public void queryBillApply(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException
	{
		Map map = request.getParameterMap();
		Map<String, String> filter = shipNotifyJdbcService.getFilter(map);
		String sql = shipNotifyJdbcService.getQueryBillApplySql(filter);
		String grid_columns = "Bill_APPLY_ID,Bill_APPLY_NO,SAP_BILL_NO,PRICE_TOTAL,CONTRACT_SALES_NO,CONTRACT_NAME,SAP_ORDER_NO,VBKD_IHREZ";
		String grid_size = "20";
		request.setAttribute("grid_sql", sql);
		request.setAttribute("grid_columns", grid_columns);
		request.setAttribute("grid_size", grid_size);

		ExtComponentServlet extComponentServlet = new ExtComponentServlet();
		extComponentServlet.processGrid(request, response, true);
	}
	
	/**
	 * 初始化出仓单信息
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void initShipInfo(HttpServletRequest request,
			HttpServletResponse response) throws IOException
	{
		String shipId = request.getParameter("shipId");
		String exportApplyId = request.getParameter("exportApplyId");
		String contractSalesId = request.getParameter("contractSalesId");
		String intendGatherTime = request.getParameter("intendGatherTime");
		String makeInvoiceTime = request.getParameter("makeInvoiceTime");
		shipHibernateService.initShipInfo(shipId, exportApplyId,contractSalesId,intendGatherTime,makeInvoiceTime);
	}
	
	/**
	 * 取得出仓单下的物料明细资料
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void queryMaterial(HttpServletRequest request,
			HttpServletResponse response) throws Exception
	{
		String shipId = request.getParameter("shipId");
		String contractPurchaseId = request.getParameter("contractPurchaseId");
		String sql;
		
		TShipInfo tShipInfo = shipHibernateService.getShipInfo(shipId);
		String warehouse = tShipInfo.getWarehouse();
		
		String inventoryNum = ",nvl((Select sum(t2.INVENTORY_NUM) From t_material_inventory t2" +
			" where t.MATERIAL_CODE=t2.MATERIAL_CODE" +
			" and nvl(t.BATCH_NO,-1)=nvl(t2.BATCH_NO,-1)";
		if (StringUtils.isNotBlank(warehouse))
			inventoryNum += " and t2.WAREHOUSE = N'"+ warehouse +"'";		
		inventoryNum += " ),0) INVENTORY_NUM";		
			
		if (StringUtils.isNotBlank(contractPurchaseId)) 	//采购合同
		{
			sql = "select t.*,t.quantity old_quantity,t1.EKPO_MATNR,t1.EKPO_TXZ01";//EKPO_PEINH:条件定价单位
			sql += inventoryNum;
			sql += " from t_ship_material t";
			sql += " Left Join T_CONTRACT_PURCHASE_MATERIEL t1 On t.PURCHASE_ROWS_ID=t1.PURCHASE_ROWS_ID";
		}			
		else{												//销售合同
			sql = "select t.*,t.quantity old_quantity" ;
			sql += inventoryNum;
			sql +=" from t_ship_material t ";
		}
		sql += " where 1 = 1";
		if (StringUtils.isNotBlank(shipId))
		{
			sql += " and t.ship_id = '" + shipId + "' order by t.sap_row_no";
		}
		String 	grid_columns = "SHIP_DETAIL_ID,SHIP_ID,MATERIAL_CODE,MATERIAL_NAME,MATERIAL_UNIT,QUANTITY,SEQUANTITY,BATCH_NO,"
			+ "PRICE,CURRENCY,TOTAL,CMD,IS_AVAILABLE,CREATOR_TIME,CREATOR,EKPO_PEINH,CURRENT_CLEAR_CHARGE,IS_CLEAR_FINISH,SALE_PRICE,SALE_TOTAL,VBAP_KPEIN,SALE_CURRENCY,"
			+"INTEND_GATHER_TIME,MAKE_INVOICE_TIME,SAP_ROW_NO";

		if (StringUtils.isNotBlank(contractPurchaseId))		// 采购合同
			grid_columns+=",EKPO_MATNR,EKPO_TXZ01";
		if (StringUtils.isNotBlank(inventoryNum))			// 库存数量
			grid_columns+=",INVENTORY_NUM";
			
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
			HttpServletResponse response) throws IOException
	{
		String shipId = request.getParameter("shipId");
		String exportApplyId = request.getParameter("exportApplyId");
		String contractSalesId = request.getParameter("contractSalesId");
		String mtRowsId = request.getParameter("mtRowsId");
		String hasQuantity = request.getParameter("hasQuantity");
		String tradeType = request.getParameter("tradeType");
		String intendGatherTime = request.getParameter("intendGatherTime");
		String makeInvoiceTime = request.getParameter("makeInvoiceTime");

		TShipMaterial tShipMaterial = shipHibernateService.addMtByCon(shipId,exportApplyId, contractSalesId, mtRowsId, hasQuantity,tradeType,
				intendGatherTime,makeInvoiceTime);
		String jsonText="";
		if(tShipMaterial==null){
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("error","找不到相应的物料!");
			jsonText = jsonObject.toString();
		}else{
			JSONObject jsonObject = JSONObject.fromObject(tShipMaterial);
			jsonText = jsonObject.toString();
		}
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		response.getWriter().print(jsonText);
	}

	/**
	 * 转到查询销售合同物料窗口
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView toFindExportApplyMaterial(HttpServletRequest request,
			HttpServletResponse response) throws Exception
	{
		String exportApplyId = request.getParameter("exportApplyId");
		String tradeType = request.getParameter("tradeType");
		request.setAttribute("exportApplyId", exportApplyId);
		request.setAttribute("tradeType", tradeType);
		
		log.debug("出仓单作业销售合同物料窗口：exportApplyId，" + exportApplyId );
		
		return new ModelAndView("sapss/ship/findExportApplyMateriel");
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	public void queryExportApplyMateriel(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException
	{
		String exportApplyId = request.getParameter("exportApplyId");
		String tradeType = request.getParameter("tradeType");
		String materialCode = request.getParameter("materialCode");
		String materialName = extractFR(request, "materialName");
		Map filter = new HashMap();

		filter.put("exportApplyId", exportApplyId);
		filter.put("tradeType", tradeType);
		filter.put("materialCode", materialCode);
		filter.put("materialName", materialName);

		
		String sql = shipNotifyJdbcService
				.getQueryExportApplyMaterielSql(filter);
		String grid_columns = "EXPORT_MATE_ID,EXPORT_APPLY_ID,MATERIAL_CODE,MATERIAL_NAME,MATERIAL_UNIT,TOTAL_QUANTITY,TOTAL_QUANTITY,RATE,PRICE,CURRENCY,PEINH,TOTAL,APPLY_QUANTITY,HAS_QUANTITY";
		String grid_size = "20";

		request.setAttribute("grid_sql", sql);
		request.setAttribute("grid_columns", grid_columns);
		request.setAttribute("grid_size", grid_size);
		log.debug("exportApplyId:" + exportApplyId);
		log.debug("查询:" + sql);
		ExtComponentServlet extComponentServlet = new ExtComponentServlet();
		extComponentServlet.processGrid(request, response, true);
	}

	/**
	 * 修改出仓单物料信息
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void updateShipMateriel(HttpServletRequest request,
			HttpServletResponse response) throws IOException
	{
		String shipDetailId = request.getParameter("shipDetailId");
		String colName = request.getParameter("colName");
		String colValue = extractFR(request, "colValue");
		JSONObject jsonObject = new JSONObject();
		shipJdbcService.updateShipMateriel(shipDetailId, colName, colValue);
		if (colName.equals("QUANTITY") || colName.equals("PRICE") || "EKPO_PEINH".equals(colName)|| colName.equals("SALE_PRICE") || "VBAP_KPEIN".equals(colName)){

		TShipMaterial tShipMaterial = shipHibernateService.getShipMaterial(shipDetailId);
		jsonObject.put("stateCode", "0");
		jsonObject.put("total", tShipMaterial.getTotal().divide(new BigDecimal(1), 2, RoundingMode.HALF_UP));
		jsonObject.put("sale_total", tShipMaterial.getSaleTotal().divide(new BigDecimal(1), 2, RoundingMode.HALF_UP));
		}

		/**
		try
		{
			double has_quantity = shipJdbcService.updateShipMateriel(shipDetailId, colName, colValue);
			if (has_quantity>=0)
			{
				//回调更新总额
//				if (colName.equals("QUANTITY") || colName.equals("PRICE") || "EKPO_PEINH".equals(colName)){
//					TShipMaterial tShipMaterial = shipHibernateService.getShipMaterial(shipDetailId);
//					jsonObject.put("total", tShipMaterial.getTotal().divide(new BigDecimal(1), 2, RoundingMode.HALF_UP));
//				}
				jsonObject.put("stateCode", "1");
//				response.getWriter().write("{success:true");
			}
			else
			{
				jsonObject.put("stateCode", "0");
				jsonObject.put("hasQuantity", has_quantity);
//				response.getWriter().write("{success:false,error:'申请数量超过合同物料数量");
			}
			TShipMaterial tShipMaterial = shipHibernateService.getShipMaterial(shipDetailId);
			jsonObject.put("total", tShipMaterial.getTotal().divide(new BigDecimal(1), 2, RoundingMode.HALF_UP));
			jsonObject.put("sale_total", tShipMaterial.getSaleTotal().divide(new BigDecimal(1), 2, RoundingMode.HALF_UP));
		}
		catch (Exception e)
		{
			jsonObject.put("stateCode", "0");
			response.getWriter().write("{success:false,error:'更新信息失败");
		}
		**/
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
	public void deleteShipMateriel(HttpServletRequest request,
			HttpServletResponse response) throws Exception
	{
		String shipDetailIds = request.getParameter("shipDetailIds");
		shipHibernateService.deleteShipMaterial(shipDetailIds);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("returnMessage", "删除成功!");
		String jsontext = jsonObject.toString();
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		this.operateSuccessfullyWithString(response, jsontext);
	}

	/**
	 * 提交并保存出仓单
	 * 
	 * @param request
	 * @param response
	 * @param tShipInfo
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws IOException
	 * @throws Exception
	 */
	public void submitAndSaveShipInfo(HttpServletRequest request,
			HttpServletResponse response, TShipInfo tShipInfo)
			throws IllegalAccessException, InvocationTargetException,
			IOException, Exception
	{
		JSONObject jo = new JSONObject();
		String taskId = request.getParameter("workflowTaskId");
		//是客户授信，并且类型为1,3才要检查授信。
		boolean isc = checkCustomerSendCreditSerice.checkCustomerCredit(tShipInfo.getProjectNo());
		
		if(isc){
			boolean iscc=checkCustomerSendCreditSerice.checkCustomerSendCredit( tShipInfo.getContractSalesId(), tShipInfo.getShipId());
			if(iscc){
				shipHibernateService.submitAndSaveShipInfo(taskId, tShipInfo);
				jo.put("returnMessage", "提交成功！");
			}else{			
				jo.put("returnMessage", "发货额度不足，无法提交！");
			}	
		}else{
			shipHibernateService.submitAndSaveShipInfo(taskId, tShipInfo);
			jo.put("returnMessage", "提交成功！");
		}			
		String jsontext = jo.toString();
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		this.operateSuccessfullyWithString(response, jsontext);
	}
	
	/**
	 * 检查发货信用额度
	 * 
	 * @param request
	 * @param response
	 */
	public void checkCredit(HttpServletRequest request, HttpServletResponse response)
	{
		
		String strProjectNo = request.getParameter("projectno");
		String strValue = request.getParameter("value");
		String shipId = request.getParameter("shipId");
		String strResult = this.checkCustomerSendCreditSerice.viewCheckCustomerSendCredit(strProjectNo,shipId, new Float(strValue));

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("result", strResult);
		String jsontext = jsonObject.toString();

		try
		{
			response.setContentType("text/html;charset=UTF-8");
			response.getWriter().print(jsontext);
		}
		catch (IOException e)
		{
			logger.error("输出json失败," + e.getMessage(), e.getCause());
		}
	}

	/**
	 * 提交出仓单
	 * 
	 * @param request
	 * @param response
	 * @param tShipInfo
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws IOException
	 * @throws Exception
	 */
	public void submitShipInfo(HttpServletRequest request,
			HttpServletResponse response, TShipInfo tShipInfo)
			throws IllegalAccessException, InvocationTargetException,
			IOException, Exception
	{

		String taskId = request.getParameter("workflowTaskId");
		String msg="";
		try {
			shipHibernateService.submitShipInfo(taskId, tShipInfo);
		} catch (Exception e) {
			msg = "异常类:" + e.getClass().getName() + "<br>异常信息:" + e.getMessage();
			e.printStackTrace();
		}
		JSONObject jo = new JSONObject();
		if ("".equals(msg))
			jo.put("returnMessage", "提交审批成功！  ");
		else
			jo.put("returnMessage", msg);

		String jsontext = jo.toString();
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		this.operateSuccessfullyWithString(response, jsontext);
	}

	/**
	 * 进入流程审批出仓单界面
	 * 
	 * @param request
	 * @param resp
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public ModelAndView shipExamine(HttpServletRequest request,
			HttpServletResponse resp) throws IllegalAccessException,
			InvocationTargetException
	{
		String shipId = request.getParameter("businessRecordId");
		String taskId = request.getParameter("taskId");
		request.setAttribute("taskId", taskId);
		request.setAttribute("businessRecordId", shipId);
		TShipInfo tShipInfo = this.shipHibernateService.getShipInfo(shipId);

		UserContext userContext = UserContextHolder.getLocalUserContext()
				.getUserContext();
		Boolean modify = false;
		// 创建者能修改：
		if (tShipInfo.getCreator().equals(userContext.getSysUser().getUserId()))
		{
			TaskInstanceContext  taski = WorkflowService.getTaskInstanceContext(shipId, taskId);
			if(taski.getTaskName().indexOf("业务确认")!=-1||taski.getTaskName().indexOf("修改")!=-1)
				modify = true;
		}
		TaskInstanceContext taskInstanceContext = WorkflowService.getTaskInstanceContext(taskId);
        String taskName = taskInstanceContext.getTaskName();
        Boolean postDateEdit = false;
        if(taskName.indexOf("仓管员")>=0&&taskName.indexOf("*")==-1){
        	request.setAttribute("isShipPrintHidden", false);
        	request.setAttribute("shipPrintUrl", "shipController.spr?action=preDealPrint&shipId="+shipId);
        	if(StringUtils.isBlank(tShipInfo.getOldShipId()))
        	    postDateEdit = true;
        }
		// 业务修改权限进入修改页面
		if (modify)
		{
			request.setAttribute("iframeSrc",
					"shipController.spr?action=addShipInfoView&operType=100&shipId="
							+ shipId);
		}
		else
		{
			request.setAttribute("iframeSrc",
					"shipController.spr?action=examineShipInfoView&operType=000&shipId="
							+ shipId+"&postDateEdit="+postDateEdit+"&taskName="+taskName);
		}
		String submitUrl = "shipController.spr?action=submitShipInfo";
		request.setAttribute("submitUrl", submitUrl);
		request.setAttribute("url", "shipController.spr?action=queryTaskHis&businessid="+shipId);
		return new ModelAndView("sapss/workflow/commonProcessPage");
	}

	/* 进入总的出仓单查询界面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ModelAndView ArchShipManageView(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String deptCode = UserContextHolder.getLocalUserContext()
		.getUserContext().getSysDept().getDeptShortCode();
		request.setAttribute("deptCode", deptCode);
		Map<String, DictionaryRow> wareHouseMap = SysCachePoolUtils.getDictTableGroup("BM_WAREHOUSE");
		Collection<DictionaryRow> wareHouse = wareHouseMap.values();
		request.setAttribute("wareHouse", wareHouse);
		return new ModelAndView(
				"sapss/ship/Archives/ArchShipManage");
	}
	
	/**
	 * 萃取url
	 * 
	 * @param req
	 * @return 参数名,参数值的map
	 */
	private Map<String, String> extractFRM(HttpServletRequest req)
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
	 * 查询原材料采购合同信息
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	public void queryPurchaseInfo(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException
	{
		Map<String, String> map = extractFRM(request);
		String sql = shipJdbcService.getPurchaseQuerySql(map);

		System.out.println(sql);
		
		String grid_columns = "PROJECT_NO,PROJECT_NAME,CONTRACT_GROUP_NO,CONTRACT_GROUP_NAME,CONTRACT_PURCHASE_ID,CONTRACT_NO,CONTRACT_NAME,SAP_ORDER_NO,EKKO_UNSEZ,EKKO_LIFNR_NAME,EKKO_INCO1";

		String grid_size = "20";
		request.setAttribute("grid_sql", sql);
		request.setAttribute("grid_columns", grid_columns);
		request.setAttribute("grid_size", grid_size);

		ExtComponentServlet extComponentServlet = new ExtComponentServlet();
		extComponentServlet.processGrid(request, response, true);
	}

	/**
	 * 初始化原材料出仓单信息
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void initShipBOMInfo(HttpServletRequest request,
			HttpServletResponse response) throws IOException
	{
		String shipId = request.getParameter("shipId");
		String contractPurchaseId = request.getParameter("contractPurchaseId");
		String intendGatherTime = request.getParameter("intendGatherTime");
		String makeInvoiceTime = request.getParameter("makeInvoiceTime");
		shipHibernateService.initShipBOMInfo(shipId, contractPurchaseId,intendGatherTime,makeInvoiceTime);
	}
	
	/* 查询物料的库存数量
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public void queryInventoryNum(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		
		String materialCode = request.getParameter("materialCode");
		String warehouse = request.getParameter("warehouse");
		String batchNo = request.getParameter("batchNo");
		String shipId = request.getParameter("shipId");
		String shipDetailId = request.getParameter("shipDetailId");
		String deptId = request.getParameter("deptId");
		String examState = request.getParameter("examState");
		JSONObject jsonObject = new JSONObject();
		
		Map map =shipHibernateService.queryInventoryNum(materialCode,warehouse,batchNo,shipId,shipDetailId,deptId,examState);
		
		if(map==null){
			//jsonObject.put("inventoryNum","0");
			jsonObject.put("price", "");
			jsonObject.put("currency", "");
			jsonObject.put("tiaojiandingjiadanwei", "");
			jsonObject.put("declarationsNo", "");
			jsonObject.put("hasShipQuqlity", "");
		}else{
			//jsonObject.put("inventoryNum",map.get("kucunshu"));
			jsonObject.put("price",map.get("PRICE"));
			jsonObject.put("currency",map.get("CURRENCY"));
			jsonObject.put("tiaojiandingjiadanwei",map.get("EKPO_PEINH"));
			jsonObject.put("declarationsNo",map.get("DECLARATIONS_NO"));
			jsonObject.put("hasShipQuqlity", map.get("HAS_SHIP_QUALITY"));
		}
		String jsontext = jsonObject.toString();
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		this.operateSuccessfullyWithString(response, jsontext);	
	}
	
	/* 查询物料的库存数量20120611
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public void getShipQuality(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		
		String materialCode = request.getParameter("materialCode");
		String warehouse = request.getParameter("warehouse");
		String batchNo = request.getParameter("batchNo");
		String shipDetailId = request.getParameter("shipDetailId");
		String deptId = request.getParameter("deptId");
		JSONObject jsonObject = new JSONObject();		
		BigDecimal value = shipHibernateService.getShipQuality(materialCode, warehouse, batchNo, shipDetailId,deptId);		
		jsonObject.put("hasShipQuqlity", value);
		String jsontext = jsonObject.toString();
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		this.operateSuccessfullyWithString(response, jsontext);	
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
		return new ModelAndView("sapss/ship/findPurchaseMateriel");
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	public void queryPurchaseMateriel(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException
	{
		String contractPurchaseId = request.getParameter("contractPurchaseId");
		String tradeType = request.getParameter("tradeType");
		String materialCode = request.getParameter("materialCode");
		String materialName = extractFR(request, "materialName");
		Map<String, String> filter = new HashMap();

		filter.put("contractPurchaseId", contractPurchaseId);
		filter.put("tradeType", tradeType);
		filter.put("materialCode", materialCode);
		filter.put("materialName", materialName);

		String sql = shipJdbcService.getQueryPurchaseMaterielSql(filter);
		String grid_columns = "PURCHASE_ROWS_ID,BOM_ID,LINES,MATERIEL,MATERIEL_NAME,ENTRY_QUANTITY,HAS_QUANTITY,QUANTITY,ENTRY_UOM,CMD,PLANT,EKPO_MATNR,EKPO_MATNR,EKPO_TXZ01,EKPO_MEINS,EKPO_MENGE,FLOW_NO,TAXES,TOTAL_TAXES";
		String grid_size = "20";

		request.setAttribute("grid_sql", sql);
		request.setAttribute("grid_columns", grid_columns);
		request.setAttribute("grid_size", grid_size);

		ExtComponentServlet extComponentServlet = new ExtComponentServlet();
		extComponentServlet.processGrid(request, response, true);
	}
	
	/**
	 * 新增"进料加工业务"出仓单明细资料
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public void addPurchaseDetail(HttpServletRequest request,
			HttpServletResponse response) throws IOException
	{
		String shipId = request.getParameter("shipId");
		String contractPurchaseId = request.getParameter("contractPurchaseId");
		String purchaseRowsId = request.getParameter("purchaseRowsId");
		String bomId = request.getParameter("bomId");
		String intendGatherTime = request.getParameter("intendGatherTime");
		String makeInvoiceTime = request.getParameter("makeInvoiceTime");

		TShipMaterial tShipMaterial = shipHibernateService.addMtByPurchase(shipId,
				contractPurchaseId,purchaseRowsId, bomId,intendGatherTime,makeInvoiceTime);

		JSONObject jsonObject = JSONObject.fromObject(tShipMaterial);
		String jsonText = jsonObject.toString();
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		response.getWriter().print(jsonText);
	}
	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView openSubmitParticularWorkflow(HttpServletRequest request,HttpServletResponse response){
		//shipController.spr?action=addShipInfoView&shipId='+ records[0].data.SHIP_ID + "&tradeType=" + tradeType + "&operType=001
		String shipId = request.getParameter("shipId");
		String tradeType = request.getParameter("tradeType");
		String operType = request.getParameter("operType");
		String particularId = request.getParameter("particularId");
		String title="出仓特批审批";
		request.setAttribute("title", title);
		request.setAttribute("particularId",particularId);
		String parms = "shipId,"+shipId+";tradeType,"+tradeType+";operType,"+operType;
		request.setAttribute("iframeSrc", "shipController.spr?action=addShipInfoView&shipId="+shipId+"&tradeType="+tradeType+"&operType="+operType);
		//判断是否来自待办链接
		if(request.getParameter("fromParticular")==null){
			request.setAttribute("submitUrl", "particularWorkflowController.spr");
			request.setAttribute("parameters", "?action=firstSubmitParticularWorkflow&bizId="+shipId+"&parms="+parms+
					"&controller=shipController&title="+title);
		}
		return new ModelAndView("sapss/workflow/particular/particularWorkflowPage");

	}
	
	@Autowired
	private SysWfUtilsService sysWfUtilsService;
	@Autowired
	private SysUserService sysUserService;
	public ModelAndView dealPrint(HttpServletRequest request,HttpServletResponse response){
		String shipId = request.getParameter("shipId");
		String printType = request.getParameter("printType");
		//List<TaskHisDto> list = sysWfUtilsService.queryTaskHisList(shipId);
		TShipInfo info = shipHibernateService.getShipInfo(shipId);
		SysDept sysDept = sysDeptService.queryDeptById(info.getDeptId());
		info.setWarehouse(SysCachePoolUtils.getDictDataValue("bm_warehouse", info.getWarehouse()));
		if(info.getApprovedTime()!=null){
			info.setApprovedTime(info.getApprovedTime().substring(0,10));
		}
		SysUser user = sysUserService.queryUserById(info.getCreator(),info.getDeptId());
		request.setAttribute("deptName", user.getDeptName());
		request.setAttribute("creator", user.getRealName());
		request.setAttribute("main", info);
		request.setAttribute("title", WorkflowUtils.getCompanyName(sysDept.getCompanyCode()));
	    
	    List<TShipMaterial> meList = shipJdbcService.queryShipMaterialList(shipId);
	    request.setAttribute("meListCount", meList.size());
	    List list = shipJdbcService.queryTaskHis(shipId, true);
	    if(!"3".equals(printType)){
	    	for(int i=0;i<list.size();i++){
	    		Map map = (Map)list.get(i);
	    		TShipMaterial me = new TShipMaterial();
	    		me.setMaterialCode((String)map.get("TASK_NAME"));
	    		me.setMaterialName((String)map.get("EXAMINE_PERSON"));
	    		me.setMaterialUnit((String)map.get("EXAMINE"));
	    		me.setQuantity(new BigDecimal(0));
	    		me.setIsAvailabel("fuck");
	    		meList.add(me);
	    	}
	    }
	    request.setAttribute("printType", printType);
	    request.setAttribute("meList", meList);
	    if("4".equals(printType))
	    	return new ModelAndView("sapss/ship/printAll");
		return new ModelAndView("sapss/ship/print");
	}
	
	public ModelAndView preDealPrint(HttpServletRequest request,HttpServletResponse response){
		String shipId = request.getParameter("shipId");
	    request.setAttribute("shipId", shipId);
		return new ModelAndView("sapss/ship/prePrint");
	}

	public void setSysWfUtilsService(SysWfUtilsService sysWfUtilsService) {
		this.sysWfUtilsService = sysWfUtilsService;
	}
	public void setSysUserService(SysUserService sysUserService) {
		this.sysUserService = sysUserService;
	}
	public void dealPost(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String shipId = request.getParameter("shipId");
		String deptCode = shipJdbcService.queryDeptCode(shipId);
		JSONObject jo = new JSONObject();
		Map<String,String> map = this.shipHibernateService.dealPost(shipId ,deptCode);
		String returnNo = map.get("SAP_RETURN_NO");
		String returnMsg = map.get("SAP_RETURN_MSG");
		String msg = null;
		if(returnNo.length()==0) msg = "过帐失败："+returnMsg;
		else{
			msg = "过帐成功："+returnNo;
			//receiptsJdbcService.updateReceipts(receiptId, "SAP_RETURN_NO", returnNo);
		}
        jo.put("returnMessage", msg);
		String jsontext = jo.toString();
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");

		this.operateSuccessfullyWithString(response, jsontext);
		
	}
	
	/**
	 * 进入物料行对应款的界面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public ModelAndView shipClearChargeView(HttpServletRequest request,
			HttpServletResponse response) throws IllegalAccessException,
			InvocationTargetException
	{
		String strShipDetailId = request.getParameter("shipDetailId");
		
		request.setAttribute("shipDetailId", strShipDetailId);
		return new ModelAndView("sapss/ship/shipMaterielClearCharge");
	}
	
	public ModelAndView preWriteOff(HttpServletRequest request,
			HttpServletResponse response) throws IOException
	{
		String oldShipId = request.getParameter("shipId");
		//贸易类型
		String tradeType = request.getParameter("tradeType");
		request.setAttribute("tradeType", tradeType);
		
		
		
		TShipInfo tShipInfo = shipHibernateService.writeOff(oldShipId);
		if(tradeType==null) tradeType = tShipInfo.getTradeType();
		String tradeTypeName = SysCachePoolUtils.getDictDataValue("bm_business_type", tradeType);
		request.setAttribute("tradeTypeName", tradeTypeName);
		TExportApply tExportApply = new TExportApply();
		String exportApplyId = tShipInfo.getExportApplyId();
		if (StringUtils.isNotBlank(exportApplyId))
		{
			tExportApply = shipNotifyHibernateService
					.getExportApply(exportApplyId);
		}
		String operType = "011";
		SysDept sysDept = sysDeptService.queryDeptById(tShipInfo.getDeptId());
		request.setAttribute("tShipInfo", tShipInfo);
		request.setAttribute("tExportApply", tExportApply);
		request.setAttribute("sysDept", sysDept);
		request.setAttribute("save", operType.substring(0, 1).equals("0"));
		request.setAttribute("submit", operType.substring(1, 2).equals("0"));
		request.setAttribute("close", operType.substring(2, 3).equals("0"));
		//货币
		Map<String, DictionaryRow> CurrencyMap = SysCachePoolUtils.getDictTableGroup("BM_CURRENCY");
		Collection<DictionaryRow> Currency = CurrencyMap.values();
		request.setAttribute("Currency", Currency);
		//仓库
		Map<String, DictionaryRow> wareHouseMap = SysCachePoolUtils.getDictTableGroup("BM_WAREHOUSE");
		Collection<DictionaryRow> wareHouse = wareHouseMap.values();
		request.setAttribute("warehouse", wareHouse);

		//代理物料“当前库存”
		if ("5".equals(tradeType)||"6".equals(tradeType))
			request.setAttribute("inventoryNum", false);
		else
			request.setAttribute("inventoryNum", true);
		
		specialDepartAction(request,response,tShipInfo);
		
		
		return new ModelAndView("sapss/ship/shipCreate");
	}
	
	public void queryTaskHis(HttpServletRequest req,HttpServletResponse response) throws IOException{
		String businessid = req.getParameter("businessid");
		List rs =  shipJdbcService.queryTaskHis(businessid,false);
		JSONArray ja = JSONArray.fromObject(rs);
		JSONObject jo = new JSONObject();
		jo.put("totalProperty",rs.size());
		jo.put("root", ja);
		String jsontext = jo.toString();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		response.getWriter().print(jsontext);
	}
	
	public void preSeConfig(HttpServletRequest request,	HttpServletResponse response) throws IOException{
		String shipId = request.getParameter("shipId");
		shipJdbcService.updateWithTransition(shipId,"确认");
		JSONObject jo = new JSONObject();
		jo.put("returnMessage", "预确认成功");		
		String jsontext = jo.toString();
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		this.operateSuccessfullyWithString(response, jsontext);		
	}
	/**
	public void fuckDeal(HttpServletRequest request,HttpServletResponse response) throws IOException{
		List list = shipJdbcService.fuckDealQuery();
		for(int i= 0 ;i<list.size();i++){
			Map map = (Map)list.get(i);
			String ship_id = (String)map.get("ship_id".toUpperCase());
			if(shipJdbcService.fuckCheck(ship_id)>0) continue;
			String deptCode = shipJdbcService.queryDeptCode(ship_id);
			Map<String,String> returnmap = this.shipHibernateService.dealPostFuck(ship_id ,deptCode);
			String returnNo = returnmap.get("SAP_RETURN_NO");
			String returnMsg = returnmap.get("SAP_RETURN_MSG");
			if(returnNo.equals("false")) {
				throw new BusinessException("ship_id:"+ship_id+":"+returnMsg);
			};
		}
		JSONObject jo = new JSONObject();
		jo.put("returnMessage", "操作完成共计"+list.size()+"条！");
		String jsontext = jo.toString();
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");

		this.operateSuccessfullyWithString(response, jsontext);

	}
	public void fuckDealBill(HttpServletRequest request,HttpServletResponse response) throws IOException{
		int i = shipJdbcService.fuckDealBill();		
		JSONObject jo = new JSONObject();
		jo.put("returnMessage", "操作完成共计"+i+"条！");
		String jsontext = jo.toString();
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");

		this.operateSuccessfullyWithString(response, jsontext);
	}**/
}
