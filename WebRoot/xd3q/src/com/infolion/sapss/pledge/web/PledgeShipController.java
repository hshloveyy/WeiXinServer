/*
 * @(#)SampleController.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：林进旭
 *  时　间：2009-3-06
 *  描　述：创建
 */

package com.infolion.sapss.pledge.web;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
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
import com.infolion.platform.component.dictionary.DictionaryRow;
import com.infolion.platform.console.org.domain.SysDept;
import com.infolion.platform.console.org.domain.SysUser;
import com.infolion.platform.console.org.service.SysDeptService;
import com.infolion.platform.console.org.service.SysUserService;
import com.infolion.platform.console.sys.context.UserContext;
import com.infolion.platform.console.sys.context.UserContextHolder;
import com.infolion.platform.core.ExceptionPostHandle;
import com.infolion.platform.core.web.BaseMultiActionController;
import com.infolion.platform.sys.cache.SysCachePoolUtils;
import com.infolion.platform.util.CodeGenerator;
import com.infolion.platform.util.DateUtils;
import com.infolion.sapss.common.WorkflowUtils;
import com.infolion.sapss.pledge.domain.PledgeShipsInfo;
import com.infolion.sapss.pledge.domain.PledgeShipsItem;
import com.infolion.sapss.pledge.service.PledgeShipHibernateService;
import com.infolion.sapss.pledge.service.PledgeShipItemHibernateService;
import com.infolion.sapss.pledge.service.PledgeShipJdbcService;
import com.infolion.sapss.ship.service.ShipJdbcService;

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
public class PledgeShipController extends BaseMultiActionController
{
	private final Log log = LogFactory.getLog(PledgeShipController.class);
	// 服务类注入
	@Autowired
	private PledgeShipHibernateService pledgeShipHibernateService;
	@Autowired
	private PledgeShipItemHibernateService pledgeShipItemHibernateService;
	
	@Autowired
	private PledgeShipJdbcService pledgeShipJdbcService;
	@Autowired
	SysDeptService sysDeptService;
	
	public void setPledgeshipHibernateService(
			PledgeShipHibernateService pledgeshipHibernateService) {
		this.pledgeShipHibernateService = pledgeshipHibernateService;
	}
	public void setPledgeShipJdbcService(PledgeShipJdbcService pledgeShipJdbcService) {
		this.pledgeShipJdbcService = pledgeShipJdbcService;
	}
	public void setPledgeShipItemHibernateService(
			PledgeShipItemHibernateService pledgeShipItemHibernateService) {
		this.pledgeShipItemHibernateService = pledgeShipItemHibernateService;
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
	public ModelAndView pledgeShipManageView(HttpServletRequest request,
			HttpServletResponse response) throws IllegalAccessException,
			InvocationTargetException
	{
		UserContext userContext = UserContextHolder.getLocalUserContext()
				.getUserContext();
		String loginer = userContext.getSysUser().getUserId();
		request.setAttribute("loginer", loginer);
		return new ModelAndView("sapss/pledge/shipManage");
	}
	 /**
     * 转到综合查询页面
     * @param request
     * @param response
     * @return
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
	public ModelAndView archPledgeShipManageView(HttpServletRequest request,
			HttpServletResponse response) throws IllegalAccessException,
			InvocationTargetException {
		UserContext userContext = UserContextHolder.getLocalUserContext()
				.getUserContext();
		String deptCode = userContext.getSysDept().getDeptShortCode();
		request.setAttribute("deptCode", deptCode);
		request.setAttribute("loginer", userContext.getSysUser().getUserId());
		return new ModelAndView("sapss/pledge/Archives/archShipManage");
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
		Map<String, String> map = extractFR(request);
		String sql = pledgeShipJdbcService.getQuerySql(map);
		String grid_columns = "PLEDGESHIPS_INFO_ID,PLEDGESHIPS_INFO_NO,DEPT_ID,DEPT_NAME,WAREHOUSE,WAREHOUSE_ADD,"
			+ "EXAMINE_STATE,RSEXAM_STATE_D_RSEXAM_STATE,PROJECT_NO,PROJECT_NAME,UNIT_NAME,"
			+ "APPLY_TIME,CREATOR,CREATOR_TIME,APPROVED_TIME,IS_AVAILABLE" +
				",REAL_NAME";
		String grid_size = "10";
		request.setAttribute("grid_sql", sql);
		request.setAttribute("grid_columns", grid_columns);
		request.setAttribute("grid_size", grid_size);
		ExtComponentServlet extComponentServlet = new ExtComponentServlet();
		extComponentServlet.processGrid(request, response, true);
	}

	/**
	 * 新增出仓申请
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
		//操作类型
		String operType = "111";
		request.setAttribute("save", operType.substring(0, 1).equals("0"));
		request.setAttribute("submit", operType.substring(1, 2).equals("0"));		
		request.setAttribute("close", operType.substring(2, 3).equals("0"));				
		UserContext userContext = UserContextHolder.getLocalUserContext()
			.getUserContext();
		PledgeShipsInfo info = new PledgeShipsInfo();		
		//申报日期
		info.setApplyTime(DateUtils.getCurrDate(2));		
		info.setDeptId(userContext.getSysDept().getDeptid());
		info.setCreator(userContext.getSysUser().getUserId());
		info.setCreatorTime(DateUtils.getCurrTime(2));
		info.setIsAvailable("1");		//可用标识
		info.setExamineState("1");		//新增标识
		String pledgeShipsInfoId = this.pledgeShipHibernateService.newPledgeShipsInfo(info);
		String shipsInfoNo = this.pledgeShipJdbcService.getPledgeShipsInfoNo(pledgeShipsInfoId);
		info.setPledgeShipsInfoNo(this.pledgeShipJdbcService.getPledgeShipsInfoNo(pledgeShipsInfoId));
		this.pledgeShipJdbcService.update(shipsInfoNo,pledgeShipsInfoId);
		SysDept sysDept = sysDeptService.queryDeptById(info.getDeptId());
		request.setAttribute("sysDept", sysDept);	
		request.setAttribute("main", info);		
		request.setAttribute("modifiable", true);
		//仓库
		Map<String, DictionaryRow> wareHouseMap = SysCachePoolUtils.getDictTableGroup("BM_WAREHOUSE");
		Collection<DictionaryRow> wareHouse = wareHouseMap.values();
		request.setAttribute("warehouse", wareHouse);
		//币别
		Map<String, DictionaryRow> CurrencyMap = SysCachePoolUtils.getDictTableGroup("BM_CURRENCY");
		Collection<DictionaryRow> Currency = CurrencyMap.values();
		request.setAttribute("Currency", Currency);

		return new ModelAndView("sapss/pledge/shipCreate");
	}
	/**
	 * 增加物料信息
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void addMaterielInfo(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String pledgeShipsInfoId =  request.getParameter("pledgeShipsInfoId");
		String strEkpoMatnr = request.getParameter("EkpoMatnr");//物料编号
		String strEkpoTxz01 = extractFR(request, "EkpoTxz01");//物料描述
		String strEkpoMeins = extractFR(request, "EkpoMeins");//单位
		PledgeShipsItem pledgeShipsItem = new PledgeShipsItem();
		UserContext userContext = UserContextHolder.getLocalUserContext()
				.getUserContext();
		pledgeShipsItem.setPledgeShipsId(pledgeShipsInfoId);
		// 物料明细ID
		pledgeShipsItem.setPledgeShipsItemId(CodeGenerator.getUUID());
		
		// 物料号
		pledgeShipsItem.setMaterialCode(strEkpoMatnr);
		// 物料描述
		pledgeShipsItem.setMaterialName(strEkpoTxz01);
		// 单位
		pledgeShipsItem.setMaterialUnit(strEkpoMeins);
		
		// 批次号
		pledgeShipsItem.setBatchNo("");

		// 创建时间
		pledgeShipsItem.setCreatorTime(DateUtils
				.getCurrTime(DateUtils.HYPHEN_DISPLAY_DATE));
		// 创建者
		pledgeShipsItem.setCreator(userContext.getSysUser().getUserId());
		//add
		//每价格单位
		pledgeShipsItem.setEkpoPeinh("");		
		pledgeShipItemHibernateService.saveMaterielInfo(pledgeShipsItem);
		JSONObject jsonObject = JSONObject.fromObject(pledgeShipsItem);
		String jsonText = jsonObject.toString();

		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		response.getWriter().print(jsonText);
	}
	/**
	 * 删除出仓单明细资料(物料)
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void deleteDetail(HttpServletRequest request,
			HttpServletResponse response) throws Exception
	{
		String pledgeShipsItemId = request.getParameter("idList");
		pledgeShipItemHibernateService.deleteMaterial(pledgeShipsItemId);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("returnMessage", "删除成功!");
		String jsontext = jsonObject.toString();
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		this.operateSuccessfullyWithString(response, jsontext);
	}
	
	

	/**
	 * 删除出仓单明细资料
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void delete(HttpServletRequest request, HttpServletResponse response)
	throws Exception
	{
		// 删除动作实现
		String pledgeShipsInfoId = (String) request.getParameter("pledgeShipsInfoId");
		int i = this.pledgeShipJdbcService.deletePledgeShipsInfo(pledgeShipsInfoId);

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
	 * 取得出仓申请单下的物料明细资料
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void queryMaterial(HttpServletRequest request,
			HttpServletResponse response) throws Exception
	{
		String pledgeShipsId = request.getParameter("pledgeShipsInfoId");
		String grid_sql = "select t.* from T_PLEDGESHIPS_ITEM t where t.PLEDGESHIPS_ID ='"+pledgeShipsId+"'order by t.creator_time desc";
		String grid_columns = "PLEDGESHIPS_ITEM_ID,PLEDGESHIPS_ID,MATERIAL_CODE EKPO_MATNR,MATERIAL_NAME EKPO_TXZ01,MATERIAL_UNIT,"
			+ "EKPO_PEINH,BATCH_NO,QUANTITY,PRICE,TOTAL,CURRENCY";
		String grid_size = "10";		
		request.setAttribute("grid_sql", grid_sql);
		request.setAttribute("grid_columns", grid_columns);
		request.setAttribute("grid_size", grid_size);	
		ExtComponentServlet extComponentServlet = new ExtComponentServlet();
		extComponentServlet.processGrid(request, response, true);
	}
	
	/**
	 * 保存出仓单
	 * 
	 * @param request
	 * @param response
	 * @param tExportApply
	 * @throws IOException
	 */
	public void updateShip(HttpServletRequest request,
			HttpServletResponse response, PledgeShipsInfo pledgeShipsInfo)
			throws IOException
	{
		String creator = request.getParameter("creator");
		pledgeShipsInfo.setCreator(creator);
		//System.out.println(pledgeShipsInfo.getPledgeShipsInfoNo());
		pledgeShipHibernateService.saveShipInfo(pledgeShipsInfo);
		JSONObject jo = new JSONObject();
		jo.put("returnMessage", "保存成功！");
		String jsontext = jo.toString();
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		this.operateSuccessfullyWithString(response, jsontext);
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
		String pledgeShipsItemId = request.getParameter("pledgeShipsItemId");
		String colName = request.getParameter("colName");
		String colValue = extractFR(request, "colValue");
		JSONObject jsonObject = new JSONObject();
		pledgeShipJdbcService.updateShipMateriel(pledgeShipsItemId, colName, colValue);
		if (colName.equals("QUANTITY") || colName.equals("PRICE") || "EKPO_PEINH".equals(colName)){
			PledgeShipsItem pledgeShipsItem = pledgeShipItemHibernateService.getPledgeShipsItem(pledgeShipsItemId);
			jsonObject.put("total", pledgeShipsItem.getTotal());
		}
		jsonObject.put("stateCode", "1");
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
			HttpServletResponse response, PledgeShipsInfo pledgeShipsInfo)
			throws IllegalAccessException, InvocationTargetException,
			IOException, Exception
	{
		String creator = request.getParameter("creator");
		pledgeShipsInfo.setCreator(creator);
		pledgeShipsInfo.setExamineState("2");
		pledgeShipsInfo.setApplyTime(DateUtils.getCurrDate(2));		
		String taskId = request.getParameter("workflowTaskId");
		pledgeShipHibernateService.submitAndSaveShipInfo(taskId,
				pledgeShipsInfo);
		JSONObject jo = new JSONObject();
		jo.put("returnMessage", "提交成功！");
		String jsontext = jo.toString();
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		this.operateSuccessfullyWithString(response, jsontext);
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
		String shipId = request.getParameter("pledgeShipsInfoId");
		String shipDetailId = request.getParameter("pledgeShipsItemId");
		String deptId = request.getParameter("deptId");
		JSONObject jsonObject = new JSONObject();
		
		Map map =pledgeShipHibernateService.queryInventoryNum(materialCode,warehouse,batchNo,shipId,shipDetailId,deptId);
		
		if(map==null){
			jsonObject.put("price", "");
			jsonObject.put("currency", "");
			jsonObject.put("hasShipQuqlity", "");
		}else{
			jsonObject.put("price",map.get("PRICE"));
			jsonObject.put("currency",map.get("CURRENCY"));
			jsonObject.put("tiaojiandingjiadanwei",map.get("EKPO_PEINH"));
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
		String pledgeShipsItemId = request.getParameter("pledgeShipsItemId");
		String deptId = request.getParameter("deptId");
		JSONObject jsonObject = new JSONObject();		
		BigDecimal value = pledgeShipHibernateService.getShipQuality(materialCode, warehouse, batchNo, pledgeShipsItemId,deptId);		
		jsonObject.put("hasShipQuqlity", value);
		String jsontext = jsonObject.toString();
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		this.operateSuccessfullyWithString(response, jsontext);	
	}
	
	public ModelAndView modify(HttpServletRequest request,
			HttpServletResponse response) throws IllegalAccessException,
			InvocationTargetException {
		request.setAttribute("save", false);
		request.setAttribute("submit", false);		
		request.setAttribute("close", false);
		String pledgeShipsInfoId = request.getParameter("pledgeShipsInfoId");
		PledgeShipsInfo info = this.pledgeShipHibernateService.getPledgeShipsInfo(pledgeShipsInfoId);
		request.setAttribute("main", info);	
		//仓库
		Map<String, DictionaryRow> wareHouseMap = SysCachePoolUtils.getDictTableGroup("BM_WAREHOUSE");
		Collection<DictionaryRow> wareHouse = wareHouseMap.values();
		request.setAttribute("warehouse", wareHouse);
		//币别
		Map<String, DictionaryRow> CurrencyMap = SysCachePoolUtils.getDictTableGroup("BM_CURRENCY");
		Collection<DictionaryRow> Currency = CurrencyMap.values();
		request.setAttribute("Currency", Currency);
		return new ModelAndView("sapss/pledge/shipCreate");
	}
	
	public ModelAndView view(HttpServletRequest request,
			HttpServletResponse response) throws IllegalAccessException,
			InvocationTargetException {
		request.setAttribute("save", true);
		request.setAttribute("submit", true);		
		request.setAttribute("close", true);
		String pledgeShipsInfoId = request.getParameter("pledgeShipsInfoId");
		PledgeShipsInfo info = this.pledgeShipHibernateService.getPledgeShipsInfo(pledgeShipsInfoId);
		request.setAttribute("main", info);	
		//仓库
		Map<String, DictionaryRow> wareHouseMap = SysCachePoolUtils.getDictTableGroup("BM_WAREHOUSE");
		Collection<DictionaryRow> wareHouse = wareHouseMap.values();
		request.setAttribute("warehouse", wareHouse);
		//币别
		Map<String, DictionaryRow> CurrencyMap = SysCachePoolUtils.getDictTableGroup("BM_CURRENCY");
		Collection<DictionaryRow> Currency = CurrencyMap.values();
		request.setAttribute("Currency", Currency);
		return new ModelAndView("sapss/pledge/shipCreate");
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
	public ModelAndView shipsExamine(HttpServletRequest request, HttpServletResponse response) throws IllegalAccessException, InvocationTargetException
	{
		String shipId = request.getParameter("businessRecordId");
		String taskId = request.getParameter("taskId");
		request.setAttribute("businessRecordId", shipId);
		request.setAttribute("taskId", taskId);
		
		request.setAttribute("businessRecordId", shipId);
		PledgeShipsInfo pledgeShipsInfo = this.pledgeShipHibernateService.getPledgeShipsInfo(shipId);

		UserContext userContext = UserContextHolder.getLocalUserContext()
				.getUserContext();
		Boolean modify = false;
		if (pledgeShipsInfo.getCreator().equals(
				userContext.getSysUser().getUserId()))
		{

			modify = true;
		}	
		request.setAttribute("iframeSrc", "pledgeShipController.spr?action=examineShipView&operate=iframe&shipId=" + shipId + "&modify=" + modify);
		request.setAttribute("submitUrl", "pledgeShipController.spr?action=submitWorkflow");

		request.setAttribute("url", "shipController.spr?action=queryTaskHis&businessid="+shipId);
		return new ModelAndView("sapss/workflow/commonProcessPage");
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
	public void submitWorkflow(HttpServletRequest request, HttpServletResponse response, PledgeShipsInfo pledgeShipsInfo) throws IllegalAccessException, InvocationTargetException, IOException, Exception
	{
		String taskId = request.getParameter("workflowTaskId");
		String msg = "";

		pledgeShipsInfo.setApprovedTime(DateUtils.getCurrTime(2));
		UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
		// 业务描述信息
		pledgeShipsInfo.setWorkflowBusinessNote(userContext.getSysDept().getDeptname() + "|" + userContext.getSysUser().getRealName() + "|" + pledgeShipsInfo.getProjectNo() + "|" + pledgeShipsInfo.getProjectName());

		String doWorkflow = request.getParameter("doWorkflow");
		if (doWorkflow == null && !"mainForm".equals(doWorkflow))
		{
			String workflowLeaveTransitionName = request.getParameter("workflowLeaveTransitionName");
			String workflowExamine = request.getParameter("workflowExamine");
			pledgeShipsInfo.setWorkflowExamine(workflowExamine);
			pledgeShipsInfo.setWorkflowLeaveTransitionName(workflowLeaveTransitionName);
		}
		pledgeShipsInfo.setWorkflowModelName("质押物出仓");
		//取得工作流节点名称
		
		JSONObject jo = new JSONObject();
		jo = this.pledgeShipJdbcService.submitWorkflow(pledgeShipsInfo, taskId);
		
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");

		this.operateSuccessfullyWithString(response, jo.toString());
	}
	/**
	 * 进入流程出仓单界面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ModelAndView examineShipView(HttpServletRequest request,
			HttpServletResponse response) throws IOException
	{
		String shipId = request.getParameter("shipId");
		String modify = request.getParameter("modify");
		
		PledgeShipsInfo pledgeShipsInfo = pledgeShipHibernateService.getPledgeShipsInfo(shipId);		
		UserContext userContext = UserContextHolder.getLocalUserContext()
				.getUserContext();
		request.setAttribute("main", pledgeShipsInfo);
		
		//操作类型
		String operType="000";
		if (modify.equals("true"))
			operType = "100";
		request.setAttribute("save", operType.substring(0, 1).equals("0"));
		request.setAttribute("submit", operType.substring(1, 2).equals("0"));		
		request.setAttribute("close", operType.substring(2, 3).equals("0"));		

		request.setAttribute("modifiable", true);
		//仓库
		Map<String, DictionaryRow> wareHouseMap = SysCachePoolUtils.getDictTableGroup("BM_WAREHOUSE");
		Collection<DictionaryRow> wareHouse = wareHouseMap.values();
		request.setAttribute("warehouse", wareHouse);
		
		//币别
		Map<String, DictionaryRow> CurrencyMap = SysCachePoolUtils.getDictTableGroup("BM_CURRENCY");
		Collection<DictionaryRow> Currency = CurrencyMap.values();
		request.setAttribute("Currency", Currency);
		if(userContext.getSysUser().getUserName().equals("libq")){
			request.setAttribute("save", false);
		}
		return new ModelAndView("sapss/pledge/shipCreate");

	}
	
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private ShipJdbcService shipJdbcService;
	public ModelAndView dealPrintShip(HttpServletRequest request,HttpServletResponse response){
		String shipId = request.getParameter("shipId");
		String printType = request.getParameter("printType");
		//List<TaskHisDto> list = sysWfUtilsService.queryTaskHisList(shipId);
		PledgeShipsInfo info = pledgeShipHibernateService.getPledgeShipsInfo(shipId);
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
	    
	    List<PledgeShipsItem> meList = pledgeShipJdbcService.queryShipMaterialList(shipId);
	    request.setAttribute("meListCount", meList.size());
	    List list = shipJdbcService.queryTaskHis(shipId, true);
	    if(!"3".equals(printType)){
	    	for(int i=0;i<list.size();i++){
	    		Map map = (Map)list.get(i);
	    		PledgeShipsItem me = new PledgeShipsItem();
	    		me.setMaterialCode((String)map.get("TASK_NAME"));
	    		me.setMaterialName((String)map.get("EXAMINE_PERSON"));
	    		me.setMaterialUnit((String)map.get("EXAMINE"));
	    		me.setQuantity(0d);
	    		me.setPledgeShipsItemId("fuck");
	    		meList.add(me);
	    	}
	    }
	    request.setAttribute("printType", printType);
	    request.setAttribute("meList", meList);
	    //if("4".equals(printType))
	    	//return new ModelAndView("sapss/ship/printAll");
		return new ModelAndView("sapss/pledge/shipprint");
	}
	
	public ModelAndView preDealPrint(HttpServletRequest request,HttpServletResponse response){
		String shipId = request.getParameter("shipId");
	    request.setAttribute("shipId", shipId);
		return new ModelAndView("sapss/pledge/shipPrePrint");
	}
	
}
