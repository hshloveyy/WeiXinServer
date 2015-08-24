/*
 * @(#)SampleController.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：王懿璞
 *  时　间：2009-3-04
 *  描　述：创建
 */

package com.infolion.sapss.pledge.web;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
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
import com.infolion.sapss.bapi.ExtractSapData;
import com.infolion.sapss.bapi.dto.MasterDataDto;
import com.infolion.sapss.common.ExcelObject;
import com.infolion.sapss.common.NumberUtils;
import com.infolion.sapss.pledge.domain.PledgeReceiptsInfo;
import com.infolion.sapss.pledge.domain.PledgeReceiptsItem;
import com.infolion.sapss.pledge.service.PledgeReceiptsHibernateService;
import com.infolion.sapss.pledge.service.PledgeReceiptsItemHibernateService;
import com.infolion.sapss.pledge.service.PledgeReceiptsJdbcService;
import com.infolion.sapss.receipts.domain.TReceiptInfo;
import com.infolion.sapss.receipts.domain.TReceiptMaterial;
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
public class PledgeReceiptsController extends BaseMultiActionController {
	private final Log log = LogFactory.getLog(PledgeReceiptsController.class);
	// 服务类注入
	@Autowired
	private PledgeReceiptsHibernateService pledgeReceiptsHibernateService;
	public void setPledgeReceiptsService(PledgeReceiptsHibernateService pledgeReceiptsService) {
		this.pledgeReceiptsHibernateService = pledgeReceiptsService;
	}

	@Autowired
	private PledgeReceiptsJdbcService pledgeReceiptsJdbcService;
	public void setPledgeReceiptsJdbcService(PledgeReceiptsJdbcService pledgeReceiptsJdbcService) {
		this.pledgeReceiptsJdbcService = pledgeReceiptsJdbcService;
	}
	
	@Autowired
	private PledgeReceiptsItemHibernateService pledgeReceiptsItemHibernateService;
	
	public void setPledgeReceiptsItemHibernateService(PledgeReceiptsItemHibernateService pledgeReceiptsItemHibernateService) {
		this.pledgeReceiptsItemHibernateService = pledgeReceiptsItemHibernateService;
	}
	@Autowired
	SysDeptService sysDeptService;
	
	@Autowired
	SysUserService sysUserService;
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
     * 转到进仓单列表页面
     * @param request
     * @param response
     * @return
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
	public ModelAndView pledgeReceiptsManageView(HttpServletRequest request,
			HttpServletResponse response) throws IllegalAccessException,
			InvocationTargetException {
		UserContext userContext = UserContextHolder.getLocalUserContext()
				.getUserContext();
		request.setAttribute("loginer", userContext.getSysUser().getUserId());
		return new ModelAndView("sapss/pledge/pledgeReceiptsManage");
	}
	
	 /**
     * 转到综合查询页面
     * @param request
     * @param response
     * @return
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
	public ModelAndView archPledgeReceiptsManageView(HttpServletRequest request,
			HttpServletResponse response) throws IllegalAccessException,
			InvocationTargetException {
		UserContext userContext = UserContextHolder.getLocalUserContext()
				.getUserContext();
		String deptCode = userContext.getSysDept().getDeptShortCode();
		request.setAttribute("deptCode", deptCode);
		request.setAttribute("loginer", userContext.getSysUser().getUserId());
		return new ModelAndView("sapss/pledge/Archives/archReceiptsManage");
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
		Map<String, String> map = extractFR(request);

		String grid_sql = pledgeReceiptsJdbcService.getQuerySql(map);
		String grid_columns = "PLEDGERECEIPTS_INFO_ID,PLEDGERECEIPTS_INFO_NO,DEPT_ID,DEPT_NAME,WAREHOUSE,WAREHOUSE_ADD,"
			+ "EXAMINE_STATE,RSEXAM_STATE_D_RSEXAM_STATE,PROJECT_NO,PROJECT_NAME,UNIT_NAME,"
			+ "APPLY_TIME,CREATOR,CREATOR_TIME,APPROVED_TIME,IS_AVAILABLE" +
				",REAL_NAME";
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
	 * 新增入库申请
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
		
//		TReceiptInfo info=this.receiptsHibernateService.AddReceipts(tradeType,userContext);
		PledgeReceiptsInfo info = new PledgeReceiptsInfo();
		
		//申报日期
		info.setApplyTime(DateUtils.getCurrDate(2));		
		info.setDeptId(userContext.getSysDept().getDeptid());
		info.setCreator(userContext.getSysUser().getUserId());
		info.setCreatorTime(DateUtils.getCurrTime(2));
		info.setIsAvailable("1");		//可用标识
		info.setExamineState("1");		//新增标识
		
		String pledgeReceiptsInfoId = this.pledgeReceiptsHibernateService.newPledgeReceiptsInfo(info);
		String receiptsInfoNo = this.pledgeReceiptsJdbcService.getPledgeReceiptsInfoNo(pledgeReceiptsInfoId);
		info.setPledgeReceiptsInfoNo(this.pledgeReceiptsJdbcService.getPledgeReceiptsInfoNo(pledgeReceiptsInfoId));
		this.pledgeReceiptsJdbcService.update(receiptsInfoNo,pledgeReceiptsInfoId);		
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

		return new ModelAndView("sapss/pledge/pledgeReceiptsCreate");
	}
	/**
	 * 转到查询物料窗口
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView toFindMaterial(HttpServletRequest request,
			HttpServletResponse response) throws Exception
	{   
		UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
		request.setAttribute("saleOrg", userContext.getSysDept().getXszzcode());
		request.setAttribute("factory", userContext.getSysDept().getXszzcode());
		request.setAttribute("isFunctionOrg", "1".equals(userContext.getSysDept().getIsFuncDept())?"false":"true");
		return new ModelAndView("sapss/pledge/findMaterial");
	}
	
	/**
	 * 查找物料页面grid数据源
	 * @param request
	 * @param response
	 * @throws Exception
	 * 
	 *    <br>I_MATNR 物料号 
	 *    <br>I_MAKTX 物料描述（短文本） 
	 *    <br>I_MTART 物料类型 
	 *    <br>I_WERKS 关于国家(集中批准)合同的工厂表 
	 *    <br>I_VKORG 销售组织 
	 *    <br>I_MATKL 出口/进口物料组 
	 *    <br>I_NEEDSUBDATA 是否需要分段数据（如需分页，值为1） 
	 *    <br>I_STARTROWNUM 起始行数 
	 *    <br>I_ENDROWNUM 结束行数
	 */
	public void rtnFindMaterial(HttpServletRequest request,	HttpServletResponse response) throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
		int start = Integer.valueOf(request.getParameter("start")).intValue();
		int size = Integer.valueOf(request.getParameter("limit")).intValue();
		map.put("I_NEEDSUBDATA", "1");
		map.put("I_STARTROWNUM", start + 1 + "");
		map.put("I_ENDROWNUM", start + size + "");
		String I_MATNR = request.getParameter("materialCd");
		String I_MAKTX = request.getParameter("materialName");
		Map req = extractFR(request);
		I_MAKTX =(String)req.get("materialName");
		String I_WERKS = request.getParameter("factory");
		String I_MTART = request.getParameter("materialType");
		String I_VKORG = request.getParameter("saleOrg");
		String I_MATKL = request.getParameter("materialOrg");
		if(!"1".equals(userContext.getSysDept().getIsFuncDept())){
			I_VKORG = userContext.getSysDept().getXszzcode();
			I_WERKS = I_VKORG;
		}	
		map.put("I_MATNR", I_MATNR == null || "".equals(I_MATNR.trim()) ? "" : I_MATNR);
		map.put("I_MAKTX", I_MAKTX == null || "".equals(I_MAKTX.trim()) ? "": I_MAKTX);
		map.put("I_WERKS", I_WERKS == null || "".equals(I_WERKS.trim()) ? "": I_WERKS);
		map.put("I_MTART", I_MTART == null || "".equals(I_MTART.trim()) ? "": I_MTART);
		map.put("I_VKORG", I_VKORG == null || "".equals(I_VKORG.trim()) ? "": I_VKORG);
		map.put("I_MATKL", I_MATKL == null || "".equals(I_MATKL.trim()) ? "": I_MATKL);
		MasterDataDto data = ExtractSapData.getMaterialMasterData(map);
		String total = data.getTotalNumber();
		JSONArray ja = JSONArray.fromObject(data.getData());
		JSONObject jo = new JSONObject();
		jo.put("totalProperty", total);
		jo.put("root", ja);
		String jsontext = jo.toString();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		response.getWriter().write(jsontext);

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
		// 删除动作实现
		String pledgeReceiptsInfoId = (String) request.getParameter("pledgeReceiptsInfoId");
		int i = this.pledgeReceiptsJdbcService.deletePledgeReceiptsInfo(pledgeReceiptsInfoId);

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
	 * 增加物料信息
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void addMaterielInfo(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String pledgeReceiptsInfoId =  request.getParameter("pledgeReceiptsInfoId");
		String strEkpoMatnr = request.getParameter("EkpoMatnr");//物料编号
		String strEkpoTxz01 = extractFR(request, "EkpoTxz01");//物料描述
		String strEkpoMeins = extractFR(request, "EkpoMeins");//单位
		PledgeReceiptsItem pledgeReceiptsItem = new PledgeReceiptsItem();
		UserContext userContext = UserContextHolder.getLocalUserContext()
				.getUserContext();

		pledgeReceiptsItem.setPledgeReceiptsId(pledgeReceiptsInfoId);
		// 物料明细ID
		pledgeReceiptsItem.setPledgeReceiptsItemId(CodeGenerator.getUUID());
		
		// 物料号
		pledgeReceiptsItem.setMaterialCode(strEkpoMatnr);
		// 物料描述
		pledgeReceiptsItem.setMaterialName(strEkpoTxz01);
		// 单位
		pledgeReceiptsItem.setMaterialUnit(strEkpoMeins);
		
		// 批次号
		pledgeReceiptsItem.setBatchNo("");

		// 创建时间
		pledgeReceiptsItem.setCreatorTime(DateUtils
				.getCurrTime(DateUtils.HYPHEN_DISPLAY_DATE));
		// 创建者
		pledgeReceiptsItem.setCreator(userContext.getSysUser().getUserId());
		//add
		//每价格单位
		pledgeReceiptsItem.setEkpoPeinh("");
		
		
		pledgeReceiptsItemHibernateService.saveMaterielInfo(pledgeReceiptsItem);



		JSONObject jsonObject = JSONObject.fromObject(pledgeReceiptsItem);
		String jsonText = jsonObject.toString();

		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		response.getWriter().print(jsonText);
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
		String pledgeReceiptsItemId = request.getParameter("pledgeReceiptsItemId");
		String colName = request.getParameter("colName");
		String colValue = extractFR(request, "colValue");
		JSONObject jsonObject = new JSONObject();
		try
		{
			pledgeReceiptsJdbcService.updateReceiptMateriel(pledgeReceiptsItemId,
					colName, colValue);
			// 回调更新总额
			if (colName.equals("QUANTITY") || colName.equals("PRICE")
					|| colName.equals("EKPO_PEINH"))
			{
				PledgeReceiptsItem pledgeReceiptsItem = pledgeReceiptsItemHibernateService.getPledgeReceiptsItem(pledgeReceiptsItemId);
				jsonObject.put("total", pledgeReceiptsItem.getTotal());
			}
			jsonObject.put("stateCode", "1");

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
	 * 删除进仓单明细资料
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void deleteDetail(HttpServletRequest request,
			HttpServletResponse response) throws Exception
	{
		String pledgeReceiptsItemId = request.getParameter("idList");
		pledgeReceiptsItemHibernateService.deleteMaterial(pledgeReceiptsItemId);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("returnMessage", "删除成功!");
		String jsontext = jsonObject.toString();
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		this.operateSuccessfullyWithString(response, jsontext);
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
		String pledgeReceiptsId = request.getParameter("pledgeReceiptsId");		
		String grid_sql = "select t.* from T_PLEDGERECEIPTS_ITEM t where t.PLEDGERECEIPTS_ID ='"+pledgeReceiptsId+"'order by t.creator_time desc";
		String grid_columns = "PLEDGERECEIPTS_ITEM_ID,PLEDGERECEIPTS_ID,MATERIAL_CODE EKPO_MATNR,MATERIAL_NAME EKPO_TXZ01,MATERIAL_UNIT,"
			+ "EKPO_PEINH,BATCH_NO,QUANTITY,PRICE,TOTAL,CURRENCY";
		String grid_size = "10";		
		request.setAttribute("grid_sql", grid_sql);
		request.setAttribute("grid_columns", grid_columns);
		request.setAttribute("grid_size", grid_size);	
		ExtComponentServlet extComponentServlet = new ExtComponentServlet();
		extComponentServlet.processGrid(request, response, true);
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
			HttpServletResponse response, PledgeReceiptsInfo pledgeReceiptsInfo)
			throws IOException
	{
				
		String wareHouse = request.getParameter("warehouse");
		String creator = request.getParameter("creator");
		JSONObject jo = new JSONObject();
		if (StringUtils.isBlank(wareHouse))
		{
			jo.put("returnMessage", "仓库必选！");
		}
		else
		{
			pledgeReceiptsInfo.setCreator(creator);
			pledgeReceiptsHibernateService.saveOrUpdate(pledgeReceiptsInfo);
			
			jo.put("returnMessage", "保存成功！");
		}
		String jsontext = jo.toString();
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
		String pledgeReceiptsInfoId = request.getParameter("pledgeReceiptsInfoId");
		PledgeReceiptsInfo info = this.pledgeReceiptsHibernateService.getPledgeReceiptsInfo(pledgeReceiptsInfoId);
		request.setAttribute("main", info);	
		//仓库
		Map<String, DictionaryRow> wareHouseMap = SysCachePoolUtils.getDictTableGroup("BM_WAREHOUSE");
		Collection<DictionaryRow> wareHouse = wareHouseMap.values();
		request.setAttribute("warehouse", wareHouse);
		//币别
		Map<String, DictionaryRow> CurrencyMap = SysCachePoolUtils.getDictTableGroup("BM_CURRENCY");
		Collection<DictionaryRow> Currency = CurrencyMap.values();
		request.setAttribute("Currency", Currency);
		return new ModelAndView("sapss/pledge/pledgeReceiptsCreate");
	}
	
	public ModelAndView view(HttpServletRequest request,
			HttpServletResponse response) throws IllegalAccessException,
			InvocationTargetException {
		request.setAttribute("save", true);
		request.setAttribute("submit", true);		
		request.setAttribute("close", true);
		String pledgeReceiptsInfoId = request.getParameter("pledgeReceiptsInfoId");
		PledgeReceiptsInfo info = this.pledgeReceiptsHibernateService.getPledgeReceiptsInfo(pledgeReceiptsInfoId);
		request.setAttribute("main", info);	
		//仓库
		Map<String, DictionaryRow> wareHouseMap = SysCachePoolUtils.getDictTableGroup("BM_WAREHOUSE");
		Collection<DictionaryRow> wareHouse = wareHouseMap.values();
		request.setAttribute("warehouse", wareHouse);
		//币别
		Map<String, DictionaryRow> CurrencyMap = SysCachePoolUtils.getDictTableGroup("BM_CURRENCY");
		Collection<DictionaryRow> Currency = CurrencyMap.values();
		request.setAttribute("Currency", Currency);
		return new ModelAndView("sapss/pledge/pledgeReceiptsCreate");
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
			HttpServletResponse response, PledgeReceiptsInfo pledgeReceiptsInfo)
			throws IllegalAccessException, InvocationTargetException,
			IOException, Exception
	{
		String creator = request.getParameter("creator");
		pledgeReceiptsInfo.setCreator(creator);
		pledgeReceiptsInfo.setExamineState("2");
		pledgeReceiptsInfo.setApplyTime(DateUtils.getCurrDate(2));		
		String taskId = request.getParameter("workflowTaskId");
		pledgeReceiptsHibernateService.submitAndSaveReceiptInfo(taskId,
				pledgeReceiptsInfo);
		JSONObject jo = new JSONObject();
		jo.put("returnMessage", "提交成功！");
		String jsontext = jo.toString();
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		this.operateSuccessfullyWithString(response, jsontext);
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
		String taskId = request.getParameter("taskId");
		request.setAttribute("businessRecordId", receiptId);
		request.setAttribute("taskId", taskId);
		
		request.setAttribute("businessRecordId", receiptId);
		PledgeReceiptsInfo pledgeReceiptsInfo = this.pledgeReceiptsHibernateService.getPledgeReceiptsInfo(receiptId);

		UserContext userContext = UserContextHolder.getLocalUserContext()
				.getUserContext();
		Boolean modify = false;
//		String writeSap = "true";
		// 创建者能修改：
		if (pledgeReceiptsInfo.getCreator().equals(
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
        	request.setAttribute("shipPrintUrl", "pledgeReceiptsController.spr?action=preDealPrint&receiptId="+receiptId);
        	
        }
//		// 只有信息中心允许手工执行写入动作
//		if ("XXZX".equals(userContext.getSysDept().getDeptShortCode()))
//		{
//			writeSap = "false";
//			modify = true;
//		}
		
		request.setAttribute("iframeSrc", "pledgeReceiptsController.spr?action=examineReceiptView&operate=iframe&receiptId=" + receiptId + "&modify=" + modify);
		request.setAttribute("submitUrl", "pledgeReceiptsController.spr?action=submitWorkflow");

		////System.out.println("businessRecordId：" + receiptId);
		request.setAttribute("url", "shipController.spr?action=queryTaskHis&businessid="+receiptId);
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
	public void submitWorkflow(HttpServletRequest request, HttpServletResponse response, PledgeReceiptsInfo pledgeReceiptsInfo) throws IllegalAccessException, InvocationTargetException, IOException, Exception
	{
		String taskId = request.getParameter("workflowTaskId");
		String msg = "";

		pledgeReceiptsInfo.setApprovedTime(DateUtils.getCurrTime(2));
		UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
		// 业务描述信息
		pledgeReceiptsInfo.setWorkflowBusinessNote(userContext.getSysDept().getDeptname() + "|" + userContext.getSysUser().getRealName() + "|" + pledgeReceiptsInfo.getProjectNo() + "|" + pledgeReceiptsInfo.getProjectName());

		String doWorkflow = request.getParameter("doWorkflow");
		if (doWorkflow == null && !"mainForm".equals(doWorkflow))
		{
			String workflowLeaveTransitionName = request.getParameter("workflowLeaveTransitionName");
			String workflowExamine = request.getParameter("workflowExamine");
			pledgeReceiptsInfo.setWorkflowExamine(workflowExamine);
			pledgeReceiptsInfo.setWorkflowLeaveTransitionName(workflowLeaveTransitionName);
		}
		pledgeReceiptsInfo.setWorkflowModelName("质押物进仓");
		//取得工作流节点名称
		
		JSONObject jo = new JSONObject();
		jo = this.pledgeReceiptsJdbcService.submitWorkflow(pledgeReceiptsInfo, taskId);
		
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");

		this.operateSuccessfullyWithString(response, jo.toString());
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
		
		PledgeReceiptsInfo pledgeReceiptsInfo = pledgeReceiptsHibernateService.getPledgeReceiptsInfo(receiptId);		
		UserContext userContext = UserContextHolder.getLocalUserContext()
				.getUserContext();
		request.setAttribute("main", pledgeReceiptsInfo);
		
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
		return new ModelAndView("sapss/pledge/pledgeReceiptsCreate");

	}
	
	public ModelAndView preDealPrint(HttpServletRequest request,HttpServletResponse response){
		String receiptId = request.getParameter("receiptId");
	    request.setAttribute("receiptId", receiptId);
		return new ModelAndView("sapss/pledge/prePrint");
	}
	
	public ModelAndView dealPrint(HttpServletRequest request,HttpServletResponse response){
		String receiptId = request.getParameter("receiptId");
		String printType = request.getParameter("printType");
		//List<TaskHisDto> list = sysWfUtilsService.queryTaskHisList(receiptId);
		PledgeReceiptsInfo info = pledgeReceiptsHibernateService.getPledgeReceiptsInfo(receiptId);
		info.setWarehouse(SysCachePoolUtils.getDictDataValue("bm_warehouse", info.getWarehouse()));
		SysUser user = sysUserService.queryUserById(info.getCreator(),info.getDeptId());
		request.setAttribute("deptName", user.getDeptName());
		request.setAttribute("creator", user.getRealName());
		request.setAttribute("main", info);
	    
	    List<PledgeReceiptsItem> meList = pledgeReceiptsJdbcService.queryReceiptMaterial(receiptId);
	    request.setAttribute("meListCount", meList.size());
	    List list = shipJdbcService.queryTaskHis(receiptId, true);
	    if(!"3".equals(printType)){
	    	for(int i=0;i<list.size();i++){
	    		PledgeReceiptsItem me = new PledgeReceiptsItem();
	    		Map map = (Map)list.get(i);
	    		me.setMaterialCode((String)map.get("TASK_NAME"));
	    		me.setMaterialName((String)map.get("EXAMINE_PERSON"));
	    		me.setMaterialUnit((String)map.get("EXAMINE"));
	    		me.setQuantity(0d);
	    		me.setTotal(0d);
	    		me.setPledgeReceiptsId("fuck");
	    		meList.add(me);
	    	}
	    }
	    request.setAttribute("printType", printType);
	    request.setAttribute("meList", meList);
	    if("4".equals(printType))
	    	return new ModelAndView("sapss/pledge/printAll");
		return new ModelAndView("sapss/pledge/print");
	}
	public ModelAndView receiptsShipStorageView(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String deptCode = UserContextHolder.getLocalUserContext()
		.getUserContext().getSysDept().getDeptShortCode();
		request.setAttribute("deptCode", deptCode);
		return new ModelAndView("sapss/pledge/Archives/receiptShipStorage");
	}
	
	public void queryStorage(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{


		Map<String, String> map = extractFR(request);

		String grid_sql = pledgeReceiptsJdbcService.getStorageQuerySql(map);
		String grid_columns = "APPROVED_TIME,COMPANY_CODE,WAREHOUSENAME,DEPT_NAME,PROJECT_NO,"+
                              "MATERIAL_CODE,MATERIAL_NAME,BATCH_NO,KULIN,MATERIAL_UNIT," +
                              "KUCU,UNITPRICE,EKPO_PEINH,CURRENCY,TOTAL,CNYTOTAL,YUQI";
		String grid_size = "10";

		request.setAttribute("grid_sql", grid_sql);
		request.setAttribute("grid_columns", grid_columns);
		request.setAttribute("grid_size", grid_size);

		ExtComponentServlet extComponentServlet = new ExtComponentServlet();
		extComponentServlet.processGrid(request, response, true);
   }
	public void dealStorageToExcel(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String[] titles  = new String[]{"序号","进仓时间","公司代码","仓库","部门","立项号","物料编码","物料名称","批次号","库龄(天)","单位","库存","单价","定价单位","币别","金额","金额（本位币）"/**,"逾期"**/};

		ExcelObject excel = new ExcelObject(titles);
		Map<String, String> map = extractFR(request);

		String sql = pledgeReceiptsJdbcService.getStorageQuerySql(map);

		pledgeReceiptsJdbcService.dealStorageToExcel(sql ,excel);
		
		try{
			//response.reset();
	        response.setCharacterEncoding("utf-8");
	        response.setContentType("application/x-download;charset=utf-8");
	        response.setHeader("Content-Disposition", (new StringBuilder("attachment;filename=")).append(URLEncoder.encode("质押物库存综合查询.xls", "utf-8")).toString());
			excel.write(response.getOutputStream());
			response.flushBuffer();
			response.getOutputStream().close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 进入项目信息查询界面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ModelAndView selectProjrctInfo(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String strDeptId = request.getParameter("deptid");
		String projectState = request.getParameter("projectState");
		String tradeType = request.getParameter("tradeType");
		request.setAttribute("deptid", strDeptId);
		request.setAttribute("projectState", projectState);
		request.setAttribute("tradeType", tradeType);
		return new ModelAndView("sapss/pledge/selectProjectInfo");
	}
	
	/**
	 * 查找合同
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	public void queryProjectInfo(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		String strProjectName = extractFR(request, "projectname"); 
		String strProjectNo = request.getParameter("projectno");
		String strDeptId = request.getParameter("deptid");
		String tradeType = request.getParameter("tradeType");

		String strSql = "select a.project_id,a.project_name,a.project_no,a.provider_link_man,a.customer_link_man "
				+ "from t_project_info a " + "where a.is_available = '1'";
		UserContext userContext = UserContextHolder.getLocalUserContext()
		.getUserContext();
		if("1".equals(userContext.getSysDept().getIsFuncDept())){
			
			strSql +=" and a.dept_id in("+userContext.getGrantedDepartmentsId()+")";
		}
		else 
	    	strSql +=" and a.dept_id = '"+userContext.getSysDept().getDeptid()+"'";
		
		//部门不为空
		if(!"".equals(strDeptId)){
			//部门多个
			if (strDeptId.indexOf(",") != -1) {
				String[] dp = strDeptId.split(",");
				strDeptId = "";
				for (int i = 0; i < dp.length; i++) {
					if (i == (dp.length - 1))
						strDeptId = strDeptId + "'" + dp[i] + "'";
					else
						strDeptId = strDeptId + "'" + dp[i] + "',";
				}
			}else
				strDeptId="'"+strDeptId+"'";
			
			strSql = strSql + " and a.dept_id in("+strDeptId+")";
		}
		
		
		if (strProjectName != null && !"".equals(strProjectName)) {
			strSql = strSql + " and a.project_name like '%" + strProjectName
					+ "%'";
		}

		if (strProjectNo != null && !"".equals(strProjectNo)) {
			strSql = strSql + " and a.project_no like '%" + strProjectNo + "%'";
		}

		if (tradeType != null && !"".equals(tradeType)) {
			strSql = strSql + " and a.trade_type = '" + tradeType+"'";
		}
		//立项状态为生效,变更通过
			strSql = strSql + " and a.project_state in('3','8') order by creator_time desc";

		String grid_columns = "PROJECT_ID,PROJECT_NAME,PROJECT_NO,PROVIDER_LINK_MAN,CUSTOMER_LINK_MAN";

		String grid_size = request.getParameter("limit");
		grid_size = grid_size==null || "".equals(grid_size)?"10":grid_size;
		request.setAttribute("grid_sql", strSql);
		request.setAttribute("grid_columns", grid_columns);
		request.setAttribute("grid_size", grid_size);

		ExtComponentServlet extComponentServlet = new ExtComponentServlet();
		extComponentServlet.processGrid(request, response, true);
	}
}
