/*
 * @(#)SampleController.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：王懿璞
 *  时　间：2009-3-04
 *  描　述：创建
 */

package com.infolion.sapss.receipts.web;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.Collection;
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
import com.infolion.platform.console.workflow.dto.TaskHisDto;
import com.infolion.platform.console.workflow.service.SysWfUtilsService;
import com.infolion.platform.core.BusinessException;
import com.infolion.platform.core.ExceptionPostHandle;
import com.infolion.platform.core.web.BaseMultiActionController;
import com.infolion.platform.sys.cache.SysCachePoolUtils;
import com.infolion.platform.util.CodeGenerator;
import com.infolion.platform.util.DateUtils;
import com.infolion.sapss.bapi.domain.TBapiLog;
import com.infolion.sapss.common.ExcelObject;
import com.infolion.sapss.common.WorkflowUtils;
import com.infolion.sapss.contract.domain.TContractPurchaseInfo;
import com.infolion.sapss.contract.domain.TContractPurchaseMateriel;
import com.infolion.sapss.contract.domain.TContractSalesInfo;
import com.infolion.sapss.contract.service.ContractHibernateService;
import com.infolion.sapss.payment.web.PaymentImportsInfoQueryVO;
import com.infolion.sapss.receipts.domain.TReceiptInfo;
import com.infolion.sapss.receipts.domain.TReceiptMaterial;
import com.infolion.sapss.receipts.service.ReceiptsJdbcService;
import com.infolion.sapss.receipts.service.ReceiptsHibernateService;
import com.infolion.sapss.ship.domain.TShipInfo;
import com.infolion.sapss.ship.domain.TShipMaterial;
import com.infolion.sapss.ship.service.ShipJdbcService;

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
public class ReceiptsController extends BaseMultiActionController {
	private final Log log = LogFactory.getLog(ReceiptsController.class);
	// 服务类注入
	@Autowired
	private ReceiptsHibernateService receiptsHibernateService;
	public void setReceiptsService(ReceiptsHibernateService receiptsService) {
		this.receiptsHibernateService = receiptsService;
	}

	@Autowired
	private ReceiptsJdbcService receiptsJdbcService;
	public void setReceiptsJdbcService(ReceiptsJdbcService receiptsService) {
		this.receiptsJdbcService = receiptsService;
	}
	
	@Autowired
	ContractHibernateService contractHibernateService;
	
	@Autowired
	SysDeptService sysDeptService;
	
	@Autowired
	private ShipJdbcService shipJdbcService;


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
	 * 创建立项
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public ModelAndView create(HttpServletRequest request,
			HttpServletResponse response) throws IllegalAccessException,
			InvocationTargetException {		
		
		//贸易类型
		String tradeType = request.getParameter("tradeType");
		request.setAttribute("tradeType", tradeType);
		//贸易类型名称
		String tradeTypeName = SysCachePoolUtils.getDictDataValue("bm_business_type", tradeType);
		request.setAttribute("tradeTypeName", tradeTypeName);

		//操作类型
		String operType = "111";
		request.setAttribute("save", operType.substring(0, 1).equals("0"));
		request.setAttribute("submit", operType.substring(1, 2).equals("0"));		
		request.setAttribute("close", operType.substring(2, 3).equals("0"));		

		//代理物料“当前库存”
		if (tradeType.equals("5")||tradeType.equals("6"))
			request.setAttribute("inventoryNum", false);
		else
			request.setAttribute("inventoryNum", true);
		
		
		UserContext userContext = UserContextHolder.getLocalUserContext()
			.getUserContext();
		
//		TReceiptInfo info=this.receiptsHibernateService.AddReceipts(tradeType,userContext);
		TReceiptInfo info = new TReceiptInfo();

		//贸易类型
		info.setTradeType(tradeType);
		
		//申报日期
		info.setApplyTime(DateUtils.getCurrDate(2));		
		info.setDeptId(userContext.getSysDept().getDeptid());
		info.setCreator(userContext.getSysUser().getUserId());
		info.setCreatorTime(DateUtils.getCurrTime(2));
		info.setIsAvailable("1");		//可用标识
		info.setExamineState("1");		//新增标识
		info.setSerialNo(this.receiptsJdbcService.getSerialNo());//this.receiptsJdbcService.getReceiptNo()
		info.setIsReturn(request.getParameter("isReturn"));
		String receiptId = this.receiptsHibernateService.newReceiptInfo(info);
				
		SysDept sysDept = sysDeptService
			.queryDeptById(info.getDeptId());
		request.setAttribute("sysDept", sysDept);
		
		//代理物料“当前库存”
		if (tradeType.equals("5")||tradeType.equals("6"))
			request.setAttribute("inventoryNum", false);
		else
			request.setAttribute("inventoryNum", true);
		
		request.setAttribute("main", info);		
		request.setAttribute("modifiable", true);
		//仓库
		Map<String, DictionaryRow> wareHouseMap = SysCachePoolUtils.getDictTableGroup("BM_WAREHOUSE");
		Collection<DictionaryRow> wareHouse = wareHouseMap.values();
		request.setAttribute("warehouse", wareHouse);

		return new ModelAndView("sapss/receipts/receiptsCreate");
	}
	
	/**
     * 转到进仓单内容页面
     * @param request
     * @param response
     * @return
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
	public ModelAndView addReceiptView(HttpServletRequest request,
			HttpServletResponse response) throws IllegalAccessException,
			InvocationTargetException {
		String receiptId = request.getParameter("receiptId");
		TReceiptInfo tReceiptInfo = receiptsHibernateService.getReceiptInfo(receiptId);
		UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
		request.setAttribute("main", tReceiptInfo);

		//操作类型
		String operType = request.getParameter("operType");
		if (operType.length()<3) operType="101";
		if(StringUtils.isNotBlank(tReceiptInfo.getOldReceiptId()))
			operType = "0"+operType.substring(1);
		
		SysDept sysDept = sysDeptService
		.queryDeptById(tReceiptInfo.getDeptId());
		request.setAttribute("sysDept", sysDept);
		request.setAttribute("save", operType.substring(0, 1).equals("0"));
		
		request.setAttribute("submit", operType.substring(1, 2).equals("0"));
		request.setAttribute("close", operType.substring(2, 3).equals("0"));
		
		//贸易类型
		String tradeType = tReceiptInfo.getTradeType();
		request.setAttribute("tradeType", tradeType);
		//贸易类型名称
		String tradeTypeName = SysCachePoolUtils.getDictDataValue("bm_business_type", tradeType);
		request.setAttribute("tradeTypeName", tradeTypeName);
		
		//代理物料“当前库存”
		if (tradeType.equals("5")||tradeType.equals("6"))
			request.setAttribute("inventoryNum", false);
		else
			request.setAttribute("inventoryNum", true);
				
		boolean modifiable = true;
		String dept = request.getParameter("specialdepartment");
		if(dept!=null && "yes".equals(dept)){
		 if(tReceiptInfo!=null){
			if("3".equals(tReceiptInfo.getExamineState())){
				boolean auth=false;
				List<SysRole> rl = userContext.getGrantedRoles();
				for (Iterator iterator = rl.iterator(); iterator.hasNext();) {
					SysRole sysRole = (SysRole) iterator.next();
					if(sysRole.getRoleName().indexOf("仓管员")!=-1||sysRole.getRoleName().indexOf("综合部人员")!=-1){
						auth = true;
						request.setAttribute("postDateEdit", "true");
						break;
					}
				}
				if(auth){
					modifiable = false;
				}
			}
		 }
		}
		request.setAttribute("modifiable", modifiable);
		//仓库
		Map<String, DictionaryRow> wareHouseMap = SysCachePoolUtils.getDictTableGroup("BM_WAREHOUSE");
		Collection<DictionaryRow> wareHouse = wareHouseMap.values();
		request.setAttribute("warehouse", wareHouse);
		if(userContext.getSysUser().getUserName().equals("libq")){
			request.setAttribute("save", false);
		}
		if(userContext.getSysUser().getDeptId().equals("E3C96C9E-DFF8-4DD2-BB6A-E59162ADA65D")){
			request.setAttribute("saveDate", true);
		}
		if(userContext.getSysUser().getDeptName().indexOf("财务")>=0){
			request.setAttribute("saveMaterial", true);
		}
		return new ModelAndView("sapss/receipts/receiptsCreate");
	}
	/**
	 * 
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	public void saveSAPReturnNO(HttpServletRequest request,	HttpServletResponse response) throws IOException{
		String receiptId = request.getParameter("receiptId");
		String sapReturnNO = request.getParameter("sapReturnNO");
		String memo = request.getParameter("memo");
		JSONObject jo = new JSONObject();
		int i = this.receiptsJdbcService.updateReceipts(receiptId,"SAP_RETURN_NO",sapReturnNO);
		i = this.receiptsJdbcService.updateReceipts(receiptId,"memo",memo);
		if(i>0)
			jo.put("returnMessage", "更新成功");
		else
			jo.put("returnMessage", "更新失败");
		String jsontext = jo.toString();
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");

		this.operateSuccessfullyWithString(response, jsontext);
		
	}
	
	public void saveDate(HttpServletRequest request,	HttpServletResponse response) throws IOException{
		String receiptId = request.getParameter("receiptId");
		String receiptTime = request.getParameter("receiptTime");
		String payAbleDate = request.getParameter("payAbleDate");
		String receiveBillDate = request.getParameter("receiveBillDate");
		String sendGoodsDate = request.getParameter("sendGoodsDate");
		TReceiptInfo receipt = receiptsHibernateService.getReceiptInfo(receiptId);
		String oldStr = "receiptTime:"+receipt.getReceiptTime()+"|payAbleDate:"+receipt.getPayAbleDate()+"|receiveBillDate:"+receipt.getReceiveBillDate()+"|sendGoodsDate:"+receipt.getSendGoodsDate();
		receipt.setReceiptTime(receiptTime);
		receipt.setPayAbleDate(payAbleDate);
		receipt.setReceiveBillDate(receiveBillDate);
		receipt.setSendGoodsDate(sendGoodsDate);
		String newStr = "receiptTime:"+receipt.getReceiptTime()+"|payAbleDate:"+receipt.getPayAbleDate()+"|receiveBillDate:"+receipt.getReceiveBillDate()+"|sendGoodsDate:"+receipt.getSendGoodsDate();
		receiptsJdbcService.updateDate(receipt,oldStr,newStr,UserContextHolder.getLocalUserContext().getUserContext().getSysUser().getUserId());
		JSONObject jo = new JSONObject();
		jo.put("returnMessage", "更新成功");		
		String jsontext = jo.toString();
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");

		this.operateSuccessfullyWithString(response, jsontext);
		
	}
    /**
     * 转到进仓单列表页面
     * @param request
     * @param response
     * @return
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
	public ModelAndView receiptsManageView(HttpServletRequest request,
			HttpServletResponse response) throws IllegalAccessException,
			InvocationTargetException {
		// 贸易类型
		String tradeType = request.getParameter("tradeType");
		UserContext userContext = UserContextHolder.getLocalUserContext()
				.getUserContext();
		request.setAttribute("loginer", userContext.getSysUser().getUserId());
		request.setAttribute("tradeType", tradeType);
		return new ModelAndView("sapss/receipts/receiptsManage");
	}
	
	/**
	 * 保存进仓申请单
	 * 
	 * @param request
	 * @param response
	 * @param tExportApply
	 * @throws IOException
	 */
	public void updateReceipt(HttpServletRequest request,
			HttpServletResponse response, TReceiptInfo tReceiptInfo)
			throws IOException
	{
		String receiptId = request.getParameter("receiptId");
		TReceiptInfo OldtReceiptInfo = receiptsHibernateService.getReceiptInfo(receiptId);
		String wareHouse = request.getParameter("warehouse");
		JSONObject jo = new JSONObject();
		if (StringUtils.isBlank(wareHouse))
		{
			jo.put("returnMessage", "仓库必选！");
		}
		else
		{
			//receiptsHibernateService.saveReceipt(tReceiptInfo);
			receiptsHibernateService.saveReceiptUpdateDate(tReceiptInfo,OldtReceiptInfo);
			//receiptsHibernateService.updateDate(OldtReceiptInfo, tReceiptInfo);
			
			jo.put("returnMessage", "保存成功！");
		}
		String jsontext = jo.toString();
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");

		this.operateSuccessfullyWithString(response, jsontext);
	}
	
    /**
     * 保存进仓单到数据库
     * @param request
     * @param response
     * @return
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */	
	public void save(HttpServletRequest request,
			HttpServletResponse response, TReceiptInfo receiptInfo)
			throws IllegalAccessException, InvocationTargetException,
			IOException
	{

		String receiptId = request.getParameter("receiptId");
		response.setCharacterEncoding("GBK");
		response.setContentType("text ml; charset=GBK");
		////System.out.println("ok");
		////System.out.println(receiptId);
		
		JSONObject jo = new JSONObject();
		
		if (StringUtils.isBlank(receiptId))
		{
	
			receiptId = this.receiptsHibernateService
					.newReceiptInfo(receiptInfo);
			if(StringUtils.isBlank(receiptId))
			{
				jo.put("success", false);
				jo.put("receiptid", receiptId);
			}
			else
			{
				jo.put("success", true);
				jo.put("receiptid", receiptId);
			}
			
			//System.out.println(receiptId);
			//response.getWriter().print(
			//		"{success:true,receiptID:'" + receiptId + "'}");
		}
		else
		{
			receiptsHibernateService.updateReceiptInfo(receiptInfo);
			jo.put("success", true);
			jo.put("receiptid", receiptInfo.getReceiptId());
		}
		
		this.operateSuccessfullyWithString(response, jo.toString());
	}


	/**
	 * 删除进仓单明细资料
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void delete(HttpServletRequest request, HttpServletResponse response)
	throws Exception
	{
		// 贸易类型
		String tradeType = request.getParameter("tradeType");
		request.setAttribute("tradeType", tradeType);
		// 删除动作实现
		String receiptId = (String) request.getParameter("receiptId");
		int i = this.receiptsJdbcService.deleteReceiptInfo(receiptId);

		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		if (i > 0)
		{
			ExceptionPostHandle.generateInfoMessage(response, "删除成功");
		}
		else
		{
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
	public void query(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException
	{
		// 贸易类型
		String tradeType = request.getParameter("tradeType");
		request.setAttribute("tradeType", tradeType);
		Map<String, String> map = extractFR(request);

		String grid_sql = receiptsJdbcService.getQuerySql(map);
		String grid_columns = "RECEIPT_ID,RECEIPT_NO,SERIALNO,SAP_RETURN_NO,DEPT_ID,DEPT_NAME,WAREHOUSE,WAREHOUSE_ADD,"
			+ "EXAMINE_STATE,RSEXAM_STATE_D_RSEXAM_STATE,PROJECT_NO,PROJECT_NAME,BILL_STATE,"
			+ "CONTRACT_GROUP_NO,CONTRACT_NO,SAP_ORDER_NO,APPLY_TIME,"
			+ "CREATOR,CREATOR_TIME,APPROVED_TIME,IS_AVAILABLE" +
				",REAL_NAME,DEPT_NAME,EKKO_UNSEZ";
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
		String deptId = request.getParameter("deptId");
		String orderState = request.getParameter("orderState");
		String tradeType = request.getParameter("tradeType");
		request.setAttribute("deptId", deptId);
		request.setAttribute("orderState", orderState);
		request.setAttribute("tradeType", tradeType);
		return new ModelAndView("sapss/receipts/selectPurchaseInfo");
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
		String sql = receiptsJdbcService.getPurchaseQuerySql(map);

		//System.out.println(sql);
		
		String grid_columns = "PROJECT_NO,PROJECT_NAME,CONTRACT_GROUP_NO,CONTRACT_GROUP_NAME,CONTRACT_PURCHASE_ID,CONTRACT_NO,CONTRACT_NAME,SAP_ORDER_NO,EKKO_UNSEZ,EKKO_LIFNR_NAME,EKKO_INCO1";

		String grid_size = "20";
		request.setAttribute("grid_sql", sql);
		request.setAttribute("grid_columns", grid_columns);
		request.setAttribute("grid_size", grid_size);

		ExtComponentServlet extComponentServlet = new ExtComponentServlet();
		extComponentServlet.processGrid(request, response, true);
	}
	
	/**
	 * 初始化进仓单物料信息�
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void initReceiptMt(HttpServletRequest request,
			HttpServletResponse response) throws IOException
	{
		String contractPurchaseId = request.getParameter("contractPurchaseId");
		String receiptId = request.getParameter("receiptId");
		String receiptTime = request.getParameter("receiptTime");
		String payAbleDate = request.getParameter("payAbleDate");
		String receiveBillDate = request.getParameter("receiveBillDate");
		String sendGoodsDate = request.getParameter("sendGoodsDate");
		receiptsHibernateService.initReceiptMt(receiptId,contractPurchaseId,receiptTime,payAbleDate,receiveBillDate,sendGoodsDate);
	}
	
	/**
	 * 新增进仓单明细资料
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
		String receiptId = request.getParameter("receiptId");
		String purchaseRowsId = request.getParameter("purchaseRowsId");
		int Lines = Integer.parseInt(request.getParameter("Lines"));		
		receiptsHibernateService.addDetail(receiptId,purchaseRowsId,Lines);
	}
	
	/**
	 * 取得进仓单列表数据
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void getReceiptsList(HttpServletRequest request,
			HttpServletResponse response) throws Exception
	{
		// 贸易类型
		String tradeType = request.getParameter("tradeType");
		String sql = "select * from t_receipt_info where is_available = 1 and trade_type = '"
				+ tradeType + "'";
		String grid_columns = "RECEIPT_ID,CONTRACT_PURCHASE_ID,TRADE_TYPE,PROJECT_NO,PROJECT_NAME,CONTRACT_GROUP_NO,CONTRACT_NO,SAP_ORDER_NO,RECEIPT_NO,creator,CREATOR_TIME,EXAMINE_STATE,IS_AVAILABLE";
		String grid_size = "10";
		request.setAttribute("grid_sql", sql);
		request.setAttribute("grid_columns", grid_columns);
		request.setAttribute("grid_size", grid_size);
		
		//System.out.println(sql);
		
		ExtComponentServlet extComponentServlet = new ExtComponentServlet();
		extComponentServlet.processGrid(request, response, true);
	}
	
	/**
	 * 取得进仓申请单下的物料明细资料
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void queryMaterial(HttpServletRequest request,
			HttpServletResponse response) throws Exception
	{
		String receiptId = request.getParameter("receiptId");
		
		TReceiptInfo tReceiptInfo = receiptsHibernateService
			.getReceiptInfo(receiptId);
		String warehouse=tReceiptInfo.getWarehouse();
		
		String sql = "select t.*,t.quantity old_quantity,";
		
		sql+= "nvl((Select sum(t1.INVENTORY_NUM) From t_material_inventory t1" +
				" where t.MATERIAL_CODE=t1.MATERIAL_CODE" +
				" and nvl(t.BATCH_NO,-1)=nvl(t1.BATCH_NO,-1)";
		
		if (StringUtils.isNotBlank(warehouse))
			sql += " and t1.WAREHOUSE = N'"+ warehouse +"'";

		sql += " ),0) INVENTORY_NUM";
		
		sql += " from t_receipt_material t where 1 = 1";
		if (StringUtils.isNotBlank(receiptId))
			sql += " and t.RECEIPT_ID = '" + receiptId + "'";

		String grid_columns = "RECEIPT_DETAIL_ID,RECEIPT_ID,MATERIAL_CODE,SAP_ROW_NO,MATERIAL_NAME,MATERIAL_UNIT,QUANTITY,SEQUANTITY,"
				+ "BATCH_NO,INVENTORY_NUM,OLD_QUANTITY,PRICE,EKPO_PEINH,CURRENCY,TOTAL,CMD,IS_AVAILABLE,CREATOR_TIME,CREATOR,CURRENT_CLEAR_CHARGE,IS_CLEAR,"
				+ "RECEIPTTIME,PAYABLEDATE,RECEIVEBILLDATE,SENDGOODSDATE,COST_PRICE,COST_TOTAL";
		//RATE,GROSS_WEIGHT,NET_WEIGHT,
		String grid_size = "10";
		request.setAttribute("grid_sql", sql);
		request.setAttribute("grid_columns", grid_columns);
		request.setAttribute("grid_size", grid_size);

		//System.out.println(sql);
		
		ExtComponentServlet extComponentServlet = new ExtComponentServlet();
		extComponentServlet.processGrid(request, response, true);
	}

	/**
	 * 删除进仓单明细资料
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void deleteDetail(HttpServletRequest request,
			HttpServletResponse response) throws Exception
	{
		String receiptDetailIds = request.getParameter("receiptDetailIds");
		receiptsHibernateService.deleteMaterial(receiptDetailIds);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("returnMessage", "删除成功!");
		String jsontext = jsonObject.toString();
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		this.operateSuccessfullyWithString(response, jsontext);
	}
	
	/**
	 * 修改进仓单物料信息
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void updateReceiptMateriel(HttpServletRequest request,
			HttpServletResponse response) throws IOException
	{
		String receiptDetailId = request.getParameter("receiptDetailId");
		String colName = request.getParameter("colName");
		String colValue = extractFR(request, "colValue");
		JSONObject jsonObject = new JSONObject();
		try
		{
			double has_quantity = receiptsJdbcService.updateReceiptMateriel(receiptDetailId,
					colName, colValue);
			// 回调更新总额
			if (colName.equals("QUANTITY") || colName.equals("PRICE")
					|| colName.equals("PEINH"))
			{
				TReceiptMaterial tReceiptMaterial = receiptsHibernateService
						.getReceiptMaterial(receiptDetailId);
				jsonObject.put("total", tReceiptMaterial.getTotal());
			}
			jsonObject.put("stateCode", "1");
			jsonObject.put("hasQuantity", has_quantity);
			response.getWriter().write("{success:true");
			////System.out.println("updateReceiptMateriel - stateCode=1");
		}
		catch (Exception e)
		{
			jsonObject.put("stateCode", "0");
			response.getWriter().write("{success:false,error:'更新信息失败");
			////System.out.println("updateReceiptMateriel - stateCode=0");
		}
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
		return new ModelAndView("sapss/receipts/findPurchaseMateriel");
	}
	
	/**
	 * 查询采购合同物料
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

		String sql = receiptsJdbcService.getQueryPurchaseMaterielSql(filter);
		String grid_columns = "PURCHASE_ROWS_ID,LINES,EKPO_MATNR,EKPO_TXZ01,EKPO_MEINS,EKPO_MENGE,FLOW_NO,TAXES,TOTAL_TAXES";
		String grid_size = "20";
		
		//System.out.println(sql);
		
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
	 * @param receiptInfo
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws IOException
	 * @throws Exception
	 */
	public void submitWorkflow(HttpServletRequest request, HttpServletResponse response, TReceiptInfo receiptInfo) throws IllegalAccessException, InvocationTargetException, IOException, Exception
	{
		String taskId = request.getParameter("workflowTaskId");
		String msg = "";

		receiptInfo.setApprovedTime(DateUtils.getCurrTime(2));
		UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
		// 业务描述信息
		receiptInfo.setWorkflowBusinessNote(userContext.getSysDept().getDeptname() + "|" + userContext.getSysUser().getRealName() + "|" + receiptInfo.getProjectNo() + "|" + receiptInfo.getProjectName());

		String doWorkflow = request.getParameter("doWorkflow");
		if (doWorkflow == null && !"mainForm".equals(doWorkflow))
		{
			String workflowLeaveTransitionName = request.getParameter("workflowLeaveTransitionName");
			String workflowExamine = request.getParameter("workflowExamine");
			receiptInfo.setWorkflowExamine(workflowExamine);
			receiptInfo.setWorkflowLeaveTransitionName(workflowLeaveTransitionName);
		}
		receiptInfo.setWorkflowModelName("进仓管理");
		//取得工作流节点名称
		
		JSONObject jo = new JSONObject();
		jo = this.receiptsJdbcService.submitWorkflow(receiptInfo, taskId);
		
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");

		this.operateSuccessfullyWithString(response, jo.toString());
	}
	
	/**
	 * 进仓单审批界面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public ModelAndView receiptsExamine(HttpServletRequest request, HttpServletResponse response) throws IllegalAccessException, InvocationTargetException
	{
		String receiptId = request.getParameter("businessRecordId");
		String tradeType = request.getParameter("tradeType");
		String taskId = request.getParameter("taskId");
		request.setAttribute("businessRecordId", receiptId);
		request.setAttribute("taskId", taskId);
		
		request.setAttribute("businessRecordId", receiptId);
		TReceiptInfo tReceiptInfo = this.receiptsHibernateService
				.getReceiptInfo(receiptId);

		UserContext userContext = UserContextHolder.getLocalUserContext()
				.getUserContext();
		Boolean modify = false;
//		String writeSap = "true";
		// 创建者能修改：
		if (tReceiptInfo.getCreator().equals(
				userContext.getSysUser().getUserId()))
		{
//			writeSap = "true";
			modify = true;
		}
		TaskInstanceContext taskInstanceContext = WorkflowService.getTaskInstanceContext(taskId);
        String taskName = taskInstanceContext.getTaskName();
        Boolean postDateEdit = false;
        if(taskName.indexOf("仓管员")>=0){
        	request.setAttribute("isShipPrintHidden", false);
        	request.setAttribute("shipPrintUrl", "receiptsController.spr?action=preDealPrint&receiptId="+receiptId);
        	if(taskName.indexOf("*")==-1&&StringUtils.isBlank(tReceiptInfo.getOldReceiptId()))
        	    postDateEdit = true;
        }
//		// 只有信息中心允许手工执行写入动作
//		if ("XXZX".equals(userContext.getSysDept().getDeptShortCode()))
//		{
//			writeSap = "false";
//			modify = true;
//		}
		
		request.setAttribute("iframeSrc", "receiptsController.spr?action=examineReceiptView&tradeType=" + tradeType + "&operate=iframe&receiptId=" + receiptId + "&modify=" + modify+"&postDateEdit="+postDateEdit+"&taskName="+taskName);
		request.setAttribute("submitUrl", "receiptsController.spr?action=submitWorkflow");

		////System.out.println("businessRecordId：" + receiptId);
		request.setAttribute("url", "shipController.spr?action=queryTaskHis&businessid="+receiptId);
		return new ModelAndView("sapss/workflow/commonProcessPage");
	}
	
	/**
	 * 提交并保存进仓单
	 * 
	 * @param request
	 * @param response
	 * @param tReceiptInfo
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws IOException
	 * @throws Exception
	 */
	public void submitAndSaveReceiptInfo(HttpServletRequest request,
			HttpServletResponse response, TReceiptInfo tReceiptInfo)
			throws IllegalAccessException, InvocationTargetException,
			IOException, Exception
	{
		String taskId = request.getParameter("workflowTaskId");
		receiptsHibernateService.submitAndSaveReceiptInfo(taskId,
				tReceiptInfo);
		JSONObject jo = new JSONObject();
		jo.put("returnMessage", "提交成功！");
		String jsontext = jo.toString();
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		this.operateSuccessfullyWithString(response, jsontext);
	}

	/**
	 * 进入流程进仓单界面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ModelAndView examineReceiptView(HttpServletRequest request,
			HttpServletResponse response) throws IOException
	{
		String receiptId = request.getParameter("receiptId");
		String modify = request.getParameter("modify");
		String tradeType = request.getParameter("tradeType");
		
		TReceiptInfo tReceiptInfo = receiptsHibernateService
				.getReceiptInfo(receiptId);
		tradeType=tReceiptInfo.getTradeType();
		
		UserContext userContext = UserContextHolder.getLocalUserContext()
				.getUserContext();
		request.setAttribute("main", tReceiptInfo);
		
		//操作类型
		String operType="000";
		if (modify.equals("true")&&StringUtils.isBlank(tReceiptInfo.getOldReceiptId()))
			operType = "100";
		request.setAttribute("paseDate", DateUtils.getCurrDate(2));
		request.setAttribute("postDateEdit", request.getParameter("postDateEdit"));
		request.setAttribute("taskName", request.getParameter("taskName"));
		request.setAttribute("save", operType.substring(0, 1).equals("0"));
		request.setAttribute("submit", operType.substring(1, 2).equals("0"));		
		request.setAttribute("close", operType.substring(2, 3).equals("0"));		

		//贸易类型
		request.setAttribute("tradeType", tradeType);
		//贸易类型名称
		String tradeTypeName = SysCachePoolUtils.getDictDataValue("bm_business_type", tradeType);
		request.setAttribute("tradeTypeName", tradeTypeName);

		//代理物料“当前库存”
		if (tradeType.equals("5")||tradeType.equals("6"))
			request.setAttribute("inventoryNum", false);
		else
			request.setAttribute("inventoryNum", true);
		request.setAttribute("modifiable", true);
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
		return new ModelAndView("sapss/receipts/receiptsCreate");

	}

	/* 进入总的进仓单查询界面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ModelAndView ArchReceiptsManageView(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String deptCode = UserContextHolder.getLocalUserContext()
		.getUserContext().getSysDept().getDeptShortCode();
		request.setAttribute("deptCode", deptCode);
		Map<String, DictionaryRow> wareHouseMap = SysCachePoolUtils.getDictTableGroup("BM_WAREHOUSE");
		Collection<DictionaryRow> wareHouse = wareHouseMap.values();
		request.setAttribute("warehouse", wareHouse);
		request.setAttribute("isReturn", request.getParameter("isReturn"));
		return new ModelAndView(
				"sapss/receipts/Archives/archReceiptsManage");
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
		JSONObject jsonObject = new JSONObject();
		
		double inventoryNum=receiptsHibernateService.
			queryInventoryNum(materialCode,warehouse,batchNo);
		
		jsonObject.put("inventoryNum", Double.toString(inventoryNum));
		String jsontext = jsonObject.toString();
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		this.operateSuccessfullyWithString(response, jsontext);	
	}
	
	public void isRightBatchNo(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		
		String materialCode = request.getParameter("materialCode");
		String warehouse = request.getParameter("warehouse");
		String batchNo = request.getParameter("batchNo");
		String tradeType = request.getParameter("tradeType");
		String deptId = request.getParameter("deptId");
		JSONObject jsonObject = new JSONObject();
		
		boolean isRightBatchNo=receiptsHibernateService.isRightBatchNo(materialCode,warehouse,batchNo,tradeType,deptId);
		
		jsonObject.put("isRightBatchNo", String.valueOf(isRightBatchNo));
		String jsontext = jsonObject.toString();
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		this.operateSuccessfullyWithString(response, jsontext);	
	}
	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView openSubmitParticularWorkflow(HttpServletRequest request,HttpServletResponse response){
		//receiptsController.spr?action=addReceiptView&receiptId='+ records[0].data.RECEIPT_ID +"&tradeType="+ tradeType +"&operType=001",
		String receiptId = request.getParameter("receiptId");
		String tradeType = request.getParameter("tradeType");
		String operType = request.getParameter("operType");
		String particularId = request.getParameter("particularId");
		String title="入仓特批审批";
		request.setAttribute("title", title);
		request.setAttribute("particularId",particularId);
		String parms = "receiptId,"+receiptId+";tradeType,"+tradeType+";operType,"+operType;
		request.setAttribute("iframeSrc", "receiptsController.spr?action=addReceiptView&receiptId="+receiptId+"&tradeType="+tradeType+"&operType="+operType);
		//判断是否来自待办链接
		if(request.getParameter("fromParticular")==null){
			request.setAttribute("submitUrl", "particularWorkflowController.spr");
			request.setAttribute("parameters", "?action=firstSubmitParticularWorkflow&bizId="+receiptId+"&parms="+parms+
					"&controller=receiptsController&title="+title);
		}
		return new ModelAndView("sapss/workflow/particular/particularWorkflowPage");
	}
	@Autowired
	private SysWfUtilsService sysWfUtilsService;
	@Autowired
	private SysUserService sysUserService;
	/*public ModelAndView dealPrint(HttpServletRequest request,HttpServletResponse response){
		String receiptId = request.getParameter("receiptId");
		List<TaskHisDto> list = sysWfUtilsService.queryTaskHisList(receiptId);
		TReceiptInfo info = receiptsHibernateService.getReceiptInfo(receiptId);
		info.setWarehouse(SysCachePoolUtils.getDictDataValue("bm_warehouse", info.getWarehouse()));
	    request.setAttribute("main", info);
	    request.setAttribute("taskHis", list);
	    SysUser user = sysUserService.queryUserById(info.getCreator(),info.getDeptId());
	    request.setAttribute("deptName", user.getDeptName());
	    request.setAttribute("creator", user.getRealName());
	    request.setAttribute("meList", receiptsJdbcService.queryReceiptMaterial(receiptId));
		return new ModelAndView("sapss/receipts/print");
	}*/
	public ModelAndView dealPrint(HttpServletRequest request,HttpServletResponse response){
		String receiptId = request.getParameter("receiptId");
		String printType = request.getParameter("printType");
		//List<TaskHisDto> list = sysWfUtilsService.queryTaskHisList(receiptId);
		TReceiptInfo info = receiptsHibernateService.getReceiptInfo(receiptId);
		info.setWarehouse(SysCachePoolUtils.getDictDataValue("bm_warehouse", info.getWarehouse()));
		SysDept sysDept = sysDeptService.queryDeptById(info.getDeptId());
		SysUser user = sysUserService.queryUserById(info.getCreator(),info.getDeptId());
		request.setAttribute("deptName", user.getDeptName());
		request.setAttribute("creator", user.getRealName());
		request.setAttribute("main", info);
		request.setAttribute("title", WorkflowUtils.getCompanyName(sysDept.getCompanyCode()));
	    List<TReceiptMaterial> meList = receiptsJdbcService.queryReceiptMaterial(receiptId);
	    request.setAttribute("meListCount", meList.size());
	    List list = shipJdbcService.queryTaskHis(receiptId, true);
	    if(!"3".equals(printType)){
	    	for(int i=0;i<list.size();i++){
	    		TReceiptMaterial me = new TReceiptMaterial();
	    		Map map = (Map)list.get(i);
	    		me.setMaterialCode((String)map.get("TASK_NAME"));
	    		me.setMaterialName((String)map.get("EXAMINE_PERSON"));
	    		me.setMaterialUnit((String)map.get("EXAMINE"));
	    		me.setQuantity(0d);
	    		me.setTotal(0d);
	    		me.setIsAvailabel("fuck");
	    		meList.add(me);
	    	}
	    }
	    request.setAttribute("printType", printType);
	    request.setAttribute("meList", meList);
	    if("4".equals(printType))
	    	return new ModelAndView("sapss/receipts/printAll");
		return new ModelAndView("sapss/receipts/print");
	}
	
	public ModelAndView preDealPrint(HttpServletRequest request,HttpServletResponse response){
		String receiptId = request.getParameter("receiptId");
	    request.setAttribute("receiptId", receiptId);
		return new ModelAndView("sapss/receipts/prePrint");
	}
	public void setSysWfUtilsService(SysWfUtilsService sysWfUtilsService) {
		this.sysWfUtilsService = sysWfUtilsService;
	}
	public void setSysUserService(SysUserService sysUserService) {
		this.sysUserService = sysUserService;
	}
	
	public void dealPost(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String receiptId = request.getParameter("receiptId");
		String postDate = request.getParameter("postDate");
		String deptCode = receiptsJdbcService.queryDeptCode(receiptId);
		JSONObject jo = new JSONObject();
		Map<String,String> map = this.receiptsHibernateService.dealPost(receiptId ,postDate,deptCode);
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
	 * 进入物料行对付款的界面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public ModelAndView receiptClearChargeView(HttpServletRequest request,
			HttpServletResponse response) throws IllegalAccessException,
			InvocationTargetException
	{
		String receiptDetailId = request.getParameter("receiptDetailId");
		
		request.setAttribute("receiptDetailId", receiptDetailId);
		return new ModelAndView("sapss/receipts/receiptsClearCharge");
	}
	
	public ModelAndView preWriteOff(HttpServletRequest request,
			HttpServletResponse response) throws IllegalAccessException,
			InvocationTargetException
	{
		String OldReceiptId = request.getParameter("receiptId");
		if(receiptsHibernateService.isAreadyWriteOff(OldReceiptId))
			throw new BusinessException("该进仓已有冲销单据，请检查");
		//贸易类型
		String tradeType = request.getParameter("tradeType");
		request.setAttribute("tradeType", tradeType);
		//贸易类型名称
		String tradeTypeName = SysCachePoolUtils.getDictDataValue("bm_business_type", tradeType);
		request.setAttribute("tradeTypeName", tradeTypeName);

		//操作类型
		String operType = "011";
		request.setAttribute("save", operType.substring(0, 1).equals("0"));
		request.setAttribute("submit", operType.substring(1, 2).equals("0"));		
		request.setAttribute("close", operType.substring(2, 3).equals("0"));		

		//代理物料“当前库存”
		if (tradeType.equals("5")||tradeType.equals("6"))
			request.setAttribute("inventoryNum", false);
		else
			request.setAttribute("inventoryNum", true);
		
		UserContext userContext = UserContextHolder.getLocalUserContext()
			.getUserContext();
		
//		TReceiptInfo info=this.receiptsHibernateService.AddReceipts(tradeType,userContext);
		TReceiptInfo info = receiptsHibernateService.getReceiptInfo(OldReceiptId);

		if(StringUtils.isNotBlank(info.getOldReceiptId())) throw new com.infolion.platform.dpframework.core.BusinessException("该单据是冲销单据，不能重冲销！");
		//贸易类型
		info.setTradeType(tradeType);
		
		//申报日期
		info.setApplyTime(DateUtils.getCurrDate(2));		
		info.setDeptId(userContext.getSysDept().getDeptid());
		info.setCreator(userContext.getSysUser().getUserId());
		info.setCreatorTime(DateUtils.getCurrTime(2));
		info.setIsAvailable("1");		//可用标识
		info.setExamineState("1");		//新增标识
		info.setBillState("1");
		info.setReceiptNo("");
		//
		info.setOldReceiptId(info.getReceiptId());
		info.setOldReceiptNo(info.getReceiptNo());
		info.setOldSapReturnNo(info.getSapReturnNo());
		info.setSerialNo(this.receiptsJdbcService.getSerialNo());//
		info.setSapReturnNo("");
		info.setMemo("冲销原进仓单号："+info.getOldReceiptNo());
		String receiptId = this.receiptsHibernateService.writeOffReceiptInfo(info);
		
		SysDept sysDept = sysDeptService
			.queryDeptById(info.getDeptId());
		request.setAttribute("sysDept", sysDept);
		
		//代理物料“当前库存”
		if (tradeType.equals("5")||tradeType.equals("6"))
			request.setAttribute("inventoryNum", false);
		else
			request.setAttribute("inventoryNum", true);
		
		request.setAttribute("main", info);		
		request.setAttribute("modifiable", true);
		//仓库
		Map<String, DictionaryRow> wareHouseMap = SysCachePoolUtils.getDictTableGroup("BM_WAREHOUSE");
		Collection<DictionaryRow> wareHouse = wareHouseMap.values();
		request.setAttribute("warehouse", wareHouse);

		return new ModelAndView("sapss/receipts/receiptsCreate");
	}
	
	public ModelAndView receiptsShipStorageView(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String deptCode = UserContextHolder.getLocalUserContext()
		.getUserContext().getSysDept().getDeptShortCode();
		request.setAttribute("deptCode", deptCode);
		Map<String, DictionaryRow> wareHouseMap = SysCachePoolUtils.getDictTableGroup("BM_WAREHOUSE");
		Collection<DictionaryRow> wareHouse = wareHouseMap.values();
		request.setAttribute("wareHouse", wareHouse);
		return new ModelAndView("sapss/receipts/Archives/receiptShipStorage");
	}
	public void queryStorage(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{

		String tradeType = request.getParameter("tradeType");
		request.setAttribute("tradeType", tradeType);
		Map<String, String> map = extractFR(request);

		String grid_sql = receiptsJdbcService.getStorageQuerySql(map);
		String grid_columns = "APPROVED_TIME,COMPANY_CODE,WAREHOUSENAME,DEPT_NAME,TRADETYPENAME,PROJECT_NO,CONTRACT_NO,CONTRACT_PAPER_NO,"+
                              "SAP_ORDER_NO,UNIT_NAME,GROUPNAME,MATERIAL_CODE,MATERIAL_NAME,BATCH_NO,KULIN,MATERIAL_UNIT," +
                              "KUCU,UNITPRICE,EKPO_PEINH,CURRENCY,TOTAL,CNYTOTAL,YUQI";
		String grid_size = "10";

		request.setAttribute("grid_sql", grid_sql);
		request.setAttribute("grid_columns", grid_columns);
		request.setAttribute("grid_size", grid_size);

		ExtComponentServlet extComponentServlet = new ExtComponentServlet();
		extComponentServlet.processGrid(request, response, true);
   }
	
	public void dealStorageToExcel(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String[] titles  = new String[]{"序号","进仓时间","公司代码","仓库","部门","业务类型","立项号","采购合同号","纸质合同号","订单号","供应商","物料组","物料编码","物料名称","批次号","库龄(天)","单位","库存","单价","定价单位","币别","金额","金额（本位币）","逾期"};

		ExcelObject excel = new ExcelObject(titles);
		Map<String, String> map = extractFR(request);

		String sql = receiptsJdbcService.getStorageQuerySql(map);

		receiptsJdbcService.dealStorageToExcel(sql ,excel);
		
		try{
			//response.reset();
	        response.setCharacterEncoding("utf-8");
	        response.setContentType("application/x-download;charset=utf-8");
	        response.setHeader("Content-Disposition", (new StringBuilder("attachment;filename=")).append(URLEncoder.encode("库存综合查询.xls", "utf-8")).toString());
			excel.write(response.getOutputStream());
			response.flushBuffer();
			response.getOutputStream().close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ShipJdbcService getShipJdbcService() {
		return shipJdbcService;
	}

	public void setShipJdbcService(ShipJdbcService shipJdbcService) {
		this.shipJdbcService = shipJdbcService;
	}
	
	
	public ModelAndView receiptsShipSeConfigView(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String deptCode = UserContextHolder.getLocalUserContext()
		.getUserContext().getSysDept().getDeptShortCode();
		request.setAttribute("deptCode", deptCode);
		Map<String, DictionaryRow> wareHouseMap = SysCachePoolUtils.getDictTableGroup("BM_WAREHOUSE");
		Collection<DictionaryRow> wareHouse = wareHouseMap.values();
		request.setAttribute("wareHouse", wareHouse);
		return new ModelAndView("sapss/receipts/Archives/receiptShipSeConfig");
	}
	public void querySeConfig(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{

		String tradeType = request.getParameter("tradeType");
		request.setAttribute("tradeType", tradeType);
		Map<String, String> map = extractFR(request);

		String grid_sql = receiptsJdbcService.getSeConfigQuerySql(map);
		String grid_columns = "TRADETYPENAME,MARK,COMPANY_CODE, SECONFIGTIME,DEPT_NAME,WAREHOUSE_ADD,PROJECT_NO,PROJECT_NAME,SERIALNO,CONTRACT_NO,CONTRACT_PAPER_NO,SAP_ORDER_NO,UNIT_NAME,MGTITLE,MATERIAL_CODE,MATERIAL_NAME,MATERIAL_UNIT,BATCH_NO,QUANTITY,PRICE,EKPO_PEINH,TOTAL,CURRENCY";
		String grid_size = "10";

		request.setAttribute("grid_sql", grid_sql);
		request.setAttribute("grid_columns", grid_columns);
		request.setAttribute("grid_size", grid_size);

		ExtComponentServlet extComponentServlet = new ExtComponentServlet();
		extComponentServlet.processGrid(request, response, true);
   }
	
	public void dealSeConfigToExcel(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String[] titles  = new String[]{"序号","贸易类型","标识","公司代码","确认时间","部门","仓库","立项号","立项名称","进/出仓序号","合同号","纸质合同号","SAP订单号","销货单位","物料组","物料号","物料名","单位","批次号","数量","单价","定价单位","金额","币别"};

		ExcelObject excel = new ExcelObject(titles);
		Map<String, String> map = extractFR(request);

		String sql = receiptsJdbcService.getSeConfigQuerySql(map);

		receiptsJdbcService.dealSeConfigToExcel(sql ,excel);
		
		try{
			//response.reset();
	        response.setCharacterEncoding("utf-8");
	        response.setContentType("application/x-download;charset=utf-8");
	        response.setHeader("Content-Disposition", (new StringBuilder("attachment;filename=")).append(URLEncoder.encode("进出仓二次结算查询.xls", "utf-8")).toString());
			excel.write(response.getOutputStream());
			response.flushBuffer();
			response.getOutputStream().close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void preSeConfig(HttpServletRequest request,	HttpServletResponse response) throws IOException{
		String receiptId = request.getParameter("receiptId");
		receiptsJdbcService.updateWithTransition(receiptId,"确认");
		JSONObject jo = new JSONObject();
		jo.put("returnMessage", "预确认成功");		
		String jsontext = jo.toString();
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");

		this.operateSuccessfullyWithString(response, jsontext);
		
	}
	/**
	public void fuckDeal(HttpServletRequest request,HttpServletResponse response) throws IOException{
		List list = receiptsJdbcService.fuckDealQuery();
		for(int i= 0 ;i<list.size();i++){
			Map map = (Map)list.get(i);
			String receipt_id = (String)map.get("receipt_id".toUpperCase());
			String post_date = (String)map.get("post_date".toUpperCase());
			//
			if(receiptsJdbcService.fuckCheck(receipt_id)>0) continue;
			//
			String deptCode = receiptsJdbcService.queryDeptCode(receipt_id);
			Map<String,String> returnmap = this.receiptsHibernateService.dealPostFuck(receipt_id ,post_date,deptCode);
			String returnNo = returnmap.get("SAP_RETURN_NO");
			if(returnNo.equals("false")) {
				throw new BusinessException("receipt_id:"+receipt_id+":"+returnmap.get("SAP_RETURN_MSG"));
			};
		}
		JSONObject jo = new JSONObject();
		jo.put("returnMessage", "操作完成共计"+list.size()+"条！");
		String jsontext = jo.toString();
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");

		this.operateSuccessfullyWithString(response, jsontext);

	}**/

}
