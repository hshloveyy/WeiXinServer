package com.infolion.sapss.asset.web;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

import com.infolion.platform.component.ExtComponentServlet;
import com.infolion.platform.console.org.domain.SysDept;
import com.infolion.platform.console.org.service.SysDeptService;
import com.infolion.platform.console.sys.context.UserContext;
import com.infolion.platform.console.sys.context.UserContextHolder;
import com.infolion.platform.core.BusinessException;
import com.infolion.platform.core.web.EnhanceMultiActionController;
import com.infolion.platform.util.CodeGenerator;
import com.infolion.platform.util.DateUtils;
import com.infolion.platform.util.StringUtils;
import com.infolion.sapss.asset.domain.AssetInfo;
import com.infolion.sapss.asset.domain.AssetMaintain;
import com.infolion.sapss.asset.domain.AssetUserHis;
import com.infolion.sapss.asset.service.AssetService;
import com.infolion.sapss.bapi.ExtractSapData;
import com.infolion.sapss.bapi.dto.MasterDataDto;


public class AssetController extends EnhanceMultiActionController {
	
	@Autowired
	private AssetService sssetService;

	public AssetService getSssetService() {
		return sssetService;
	}

	public void setSssetService(AssetService sssetService) {
		this.sssetService = sssetService;
	}
	@Autowired
	private SysDeptService sysDeptService;
	public SysDeptService getSysDeptService() {
		return sysDeptService;
	}

	public void setSysDeptService(SysDeptService sysDeptService) {
		this.sysDeptService = sysDeptService;
	}
	/**
	 * 转到查询成本中心窗口
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView toFindCostCenter(HttpServletRequest request,HttpServletResponse response) throws Exception {
		String type = request.getParameter("type");
		UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
		request.setAttribute("comCode", userContext.getSysDept().getCompanyCode());
		request.setAttribute("type", type);
		return new ModelAndView("sapss/asset/findCostCenter");
	}
	
	public ModelAndView toFindDept(HttpServletRequest request,HttpServletResponse response) throws Exception {
		UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
		request.setAttribute("pdeptid", userContext.getSysDept().getPdeptid());
		List<SysDept> deptList = sysDeptService.queryDeptByParentId("usercompany"); 
		request.setAttribute("depts", deptList);
		return new ModelAndView("sapss/asset/findDept");
	}
	
	public void findDept(HttpServletRequest request,HttpServletResponse response) throws Exception {
		String pdeptid = request.getParameter("pdeptid");
		List list = sssetService.queryDepts(pdeptid);
		String total = String.valueOf(list.size());
		JSONArray ja = JSONArray.fromObject(list);
		JSONObject jo = new JSONObject();
		jo.put("totalProperty", total);
		jo.put("root", ja);
		String jsontext = jo.toString();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		response.getWriter().write(jsontext);
	}
	
	/**
	 * 查找成本中心页面grid数据源
	 * @param request
	 * @param response
	 * @throws Exception
	 * 
	 *    <br>BUKRS 公司代码 
	 */
	public void rtnFindCostCenter(HttpServletRequest request,	HttpServletResponse response) throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		String comCode = request.getParameter("comCode");
		map.put("BUKRS", comCode);
		MasterDataDto data = ExtractSapData.getMasterData_("ZF_GET_CHENGBENZHONGXIN","IT_GETCBZX",map,null);
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
	 * 管理界面
	 * @param request
	 * @param response
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public ModelAndView assetManageView(HttpServletRequest request,
			HttpServletResponse response) throws IllegalAccessException,
			InvocationTargetException {
		UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
		request.setAttribute("category", request.getParameter("category"));
		request.setAttribute("comCode", userContext.getSysDept().getCompanyCode());
		return new ModelAndView("sapss/asset/assetManage");
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
		//Map map = request.getParameterMap();
		Map<String, String> filter = extractFR(request);
		//Map<String, String> filter = shipJdbcService.getFilter(map);
		String sql = sssetService.getQueryAssetSql(filter);
		String grid_columns = "assetInfoId,comCode,assetType,ASSETTYPE_D_ASSET_TYPE,businessScope,businessScope_D_business_Scope,costCenterName,assetName,assetConfig,assetSerialNo,outsideNo,"
			                  +"sapAssetNo,category,category_D_ASSET_category,location,purchaseDate,contractPuchaseNo,cost,supplierName,depreciationYear,scrapDate,state,state_D_ASSET_STATE";
		String grid_size = "10";
		request.setAttribute("grid_sql", sql);
		request.setAttribute("grid_columns", grid_columns);
		request.setAttribute("grid_size", grid_size);

		ExtComponentServlet extComponentServlet = new ExtComponentServlet();
		extComponentServlet.processGrid(request, response, true);
	}
	
	/**
	 * 新增界面
	 * @param request
	 * @param response
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public ModelAndView preAdd(HttpServletRequest request,
			HttpServletResponse response) throws IllegalAccessException,
			InvocationTargetException {
		request.setAttribute("category", request.getParameter("category"));
		UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
		request.setAttribute("comCode", userContext.getSysDept().getCompanyCode());
		return new ModelAndView("sapss/asset/assetAdd");
	}
	/**
	 * 新增
	 * @param request
	 * @param response
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public void add(HttpServletRequest request,
			HttpServletResponse response, AssetInfo assetInfo)
			throws IllegalAccessException, InvocationTargetException,
			IOException {
		String assetInfoId = assetInfo.getAssetInfoId();
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
		if (assetInfoId == null || "".equals(assetInfoId)) {
			assetInfo.setCreator(userContext.getSysUser().getUserId());
			assetInfo.setCreaterTime(DateUtils.getCurrTime(2));
			assetInfo.setIsAvailable("1");
			assetInfoId = CodeGenerator.getUUID();
			assetInfo.setAssetInfoId(assetInfoId);
			this.sssetService.saveOrUpdateAssetInfo(assetInfo);
			response.getWriter().print(	"{success:true,assetInfoId:'" + assetInfoId + "'}");

		} else {
			this.sssetService.updateAssetInfo(assetInfo);
			response.getWriter().print(	"{success:true,assetInfoId:'" + assetInfoId + "'}");
		}
	}
	
	public void addUserHis(HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		
		String assetInfoId = request.getParameter("assetInfoId");
        AssetUserHis userHis = new AssetUserHis();
        userHis.setAssetUserHisId(CodeGenerator.getUUID());
        userHis.setAssetInfoId(assetInfoId);
        userHis.setCreaterTime(DateUtils.getCurrTime(2));
        userHis.setStartDate(DateUtils.getCurrDateStr(2));
        sssetService.saveOrUpdateAssetUserHis(userHis);
		JSONObject jsonObject = new JSONObject();
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		response.getWriter().print(jsonObject.toString());
	}
	
	public void addMaintain(HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		
		String assetInfoId = request.getParameter("assetInfoId");
        AssetMaintain maintain = new AssetMaintain();
        maintain.setAssetMaintainId(CodeGenerator.getUUID());
        maintain.setAssetInfoId(assetInfoId);
        maintain.setCreaterTime(DateUtils.getCurrTime(2));
        maintain.setMaintainDate(DateUtils.getCurrDateStr(2));
        sssetService.saveOrUpdateAssetMaintain(maintain);
		JSONObject jsonObject = new JSONObject();
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		response.getWriter().print(jsonObject.toString());
	}
	
	public void queryUserHis(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		String assetInfoId = request.getParameter("assetInfoId");
		String sql = "select t.* from t_asset_userhis t where t.assetInfoId ='"+assetInfoId+"'order by t.creatertime desc";
		String 	grid_columns = "assetUserHisId,assetInfoId,deptId,deptName,userMan,startDate,endDate,createrTime";
		String grid_size = "10";
		request.setAttribute("grid_sql", sql);
		request.setAttribute("grid_columns", grid_columns);
		request.setAttribute("grid_size", grid_size);

		ExtComponentServlet extComponentServlet = new ExtComponentServlet();
		extComponentServlet.processGrid(request, response, true);
	}
	
	
	public void queryMaintain(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		String assetInfoId = request.getParameter("assetInfoId");
		String sql = "select t.* from t_asset_maintain t where t.assetInfoId ='"+assetInfoId+"'order by t.creatertime desc";
		String 	grid_columns = "assetMaintainId,assetInfoId,maintainDate,maintainComName,maintainMoney,memo,createrTime";
		String grid_size = "10";
		request.setAttribute("grid_sql", sql);
		request.setAttribute("grid_columns", grid_columns);
		request.setAttribute("grid_size", grid_size);

		ExtComponentServlet extComponentServlet = new ExtComponentServlet();
		extComponentServlet.processGrid(request, response, true);
	}
	
	public void deleteUserHis(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String strIdList = request.getParameter("idList");
		JSONObject jsonObject = new JSONObject();
		sssetService.deleteUserHis(strIdList);
		jsonObject.put("returnMessage", "删除成功！");
		String jsonText = jsonObject.toString();
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		this.operateSuccessfullyWithString(response, jsonText);
	}
	
	public void deleteMaintain(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String strIdList = request.getParameter("idList");
		JSONObject jsonObject = new JSONObject();
		sssetService.deleteMaintain(strIdList);
		jsonObject.put("returnMessage", "删除成功！");
		String jsonText = jsonObject.toString();
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		this.operateSuccessfullyWithString(response, jsonText);
	}
	
	/**
	 * 编辑界面
	 * @param request
	 * @param response
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public ModelAndView preEdit(HttpServletRequest request,
			HttpServletResponse response) throws IllegalAccessException,
			InvocationTargetException {
		String assetInfoId = request.getParameter("assetInfoId");
        AssetInfo info = this.sssetService.getAssetInfo(assetInfoId);
        request.setAttribute("info", info);
		return new ModelAndView("sapss/asset/assetEdit");
	}
	/**
	 * 查看界面
	 * @param request
	 * @param response
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public ModelAndView addAssetInfoView(HttpServletRequest request,
			HttpServletResponse response) throws IllegalAccessException,
			InvocationTargetException {
		String assetInfoId = request.getParameter("assetInfoId");
        AssetInfo info = this.sssetService.getAssetInfo(assetInfoId);
        request.setAttribute("info", info);
		return new ModelAndView("sapss/asset/assetView");
	}
	public void updateUserHis(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String assetUserHisId = request.getParameter("assetUserHisId");
		String strColName = request.getParameter("colname");
		String strColValue = request.getParameter("colvalue");
		strColValue = extractFR(request, "colvalue");
		sssetService.updateUserHis(assetUserHisId,
				strColName, strColValue);
	}
	
	public void updateMaintain(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String assetMaintainId = request.getParameter("assetMaintainId");
		String strColName = request.getParameter("colname");
		String strColValue = request.getParameter("colvalue");
		strColValue = extractFR(request, "colvalue");
		sssetService.updateMaintain(assetMaintainId,
				strColName, strColValue);
	}
	
	public void edit(HttpServletRequest request,
			HttpServletResponse response) throws IllegalAccessException,
			InvocationTargetException {
		
	}
	
	public ModelAndView preView(HttpServletRequest request,
			HttpServletResponse response) throws IllegalAccessException,
			InvocationTargetException {
		
		return new ModelAndView("sapss/asset/assetView");
	}
	
	public void delete(HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		String assetId = request.getParameter("assetId");

		JSONObject jsonObject = new JSONObject();

		sssetService.deleteAssetInfo(assetId);

		jsonObject.put("returnMessage", "删除成功！");

		String jsonText = jsonObject.toString();

		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");

		this.operateSuccessfullyWithString(response, jsonText);

	}
	
	public void submitToSap(HttpServletRequest request,
			HttpServletResponse response,AssetInfo assetInfo)
			throws IllegalAccessException, InvocationTargetException,
			IOException, Exception {
		if(!StringUtils.isNullBlank(assetInfo.getSapAssetNo())) throw new BusinessException("该固定资产已写入SAP!");
		Map<String,String> result = sssetService.submitToSap(assetInfo);
		JSONObject jo = new JSONObject();
		if (result.get("SUBRC") != null && "0".equals(result.get("SUBRC"))) {
			jo.put("returnMessage", "固定资产写入SAP成功！固定资产编号："+result.get("WK_ANLN1"));
			jo.put("sapAssetNo", result.get("WK_ANLN1"));
		} else {
			jo.put("returnMessage", "固定资产写入SAP失败！"+result.get("errorMsg"));
			jo.put("sapAssetNo", "");
		}
		String jsontext = jo.toString();
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		this.operateSuccessfullyWithString(response, jsontext);
	}
}
