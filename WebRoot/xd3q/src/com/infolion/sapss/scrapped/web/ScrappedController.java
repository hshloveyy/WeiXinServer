/*
 * @(#)ScrappedController.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：陈喜平
 *  时　间：2009-6-17
 *  描　述：创建
 */

package com.infolion.sapss.scrapped.web;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

import com.infolion.platform.component.ExtComponentServlet;
import com.infolion.platform.component.dictionary.DictionaryRow;
import com.infolion.platform.component.workflow.WorkflowService;
import com.infolion.platform.component.workflow.ext.TaskInstanceContext;
import com.infolion.platform.console.org.domain.SysDept;
import com.infolion.platform.console.org.service.SysDeptService;
import com.infolion.platform.console.sys.context.UserContext;
import com.infolion.platform.console.sys.context.UserContextHolder;
import com.infolion.platform.core.web.BaseMultiActionController;
import com.infolion.platform.sys.cache.SysCachePoolUtils;
import com.infolion.platform.util.CodeGenerator;
import com.infolion.platform.util.DateUtils;
import com.infolion.sapss.scrapped.domain.TScrapped;
import com.infolion.sapss.scrapped.domain.TScrappedMaterial;
import com.infolion.sapss.scrapped.service.ScrappedService;

public class ScrappedController extends BaseMultiActionController{
	@Autowired
	private ScrappedService scrappedService;

	public void setScrappedService(ScrappedService scrappedService) {
		this.scrappedService = scrappedService;
	}
	@Autowired
	private SysDeptService sysDeptService;
	/**
	 * 菜单链接地址
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView toScrappedCompositeQuery(HttpServletRequest request, HttpServletResponse response){
		String tradeType = request.getParameter("bizType");
		request.setAttribute("tradeType", tradeType);
		return new ModelAndView("sapss/scrapped/scrappedCompositeQuery");
	}
	
	/**
	 * 菜单链接地址
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView toScrappedManage(HttpServletRequest request, HttpServletResponse response){
		String tradeType = request.getParameter("bizType");
		request.setAttribute("tradeType", tradeType);
		return new ModelAndView("sapss/scrapped/scrappedManager");
	}
	/**
	 * 弹出详细页面
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView toScrappedInfo(HttpServletRequest request, HttpServletResponse response){
		request.setAttribute("scrappedId", CodeGenerator.getUUID());
		return scrappedInfo(request, response);
	}
	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	private ModelAndView scrappedInfo(HttpServletRequest request, HttpServletResponse response){
		UserContext txt = UserContextHolder.getLocalUserContext().getUserContext();
		String deptId = (String)request.getAttribute("deptId");
		if(deptId!=null&&!deptId.equals(txt.getSysDept().getDeptid())){
			SysDept dept = sysDeptService.queryDeptById(deptId);
			request.setAttribute("deptName", dept.getDeptname());
			request.setAttribute("deptId", deptId);
		}
		else {
			request.setAttribute("deptName", txt.getSysDept().getDeptname());
			request.setAttribute("deptId", txt.getSysDept().getDeptid());
		}
		
		
		Map<String, DictionaryRow> WareHouseMap = SysCachePoolUtils.getDictTableGroup("BM_WAREHOUSE");
		Collection<DictionaryRow> WareHouse = WareHouseMap.values();
		request.setAttribute("wareHouse", WareHouse);
		
		Map<String, DictionaryRow> currencyMap = SysCachePoolUtils.getDictTableGroup("BM_CURRENCY");
		Collection<DictionaryRow> currency = currencyMap.values();
		request.setAttribute("currency", currency);

		return new ModelAndView("sapss/scrapped/scrappedInfo");
		
	}
	/**
	 * 取得物料列表
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws ServletException 
	 */
	public void getMaterial(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String scrappedId = request.getParameter("scrappedId");
		String sql = "select t.* from t_scrapped_material t where scrapped_id = '"+ scrappedId + "'";
		String grid_columns = "SCRAPPED_MATERIAL_ID,SCRAPPED_ID,MATERIAL_CODE,MATERIAL_NAME,MATERIAL_UNIT,BATCH_NO,QUANTITY,MONEY,WAREHOUSE,REASON,CURRENCY";
		String grid_size = "10";
		request.setAttribute("grid_sql", sql);
		request.setAttribute("grid_columns", grid_columns);
		request.setAttribute("grid_size", grid_size);

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
	public void find(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String start = request.getParameter("startDate");
		String end = request.getParameter("endDate");
		String creator = request.getParameter("creator");
		String sql = "select ts.*,ts.examine_state EXAMINE_STATE_D_EXAMINE_STATE from t_scrapped ts where ts.is_available='1'";
		if(StringUtils.isNotBlank(creator))
			sql = sql+" and ts.creator like '%"+creator+"%'";
		if(StringUtils.isNotBlank(start) && StringUtils.isNotBlank(end))
			sql = sql+" and ts.approved_time between '"+start+"' and '"+end+"'";
		String grid_columns = "SCRAPPED_ID,SCRAPPED_NO,EXAMINE_STATE_D_EXAMINE_STATE,APPLY_TIME,APPROVED_TIME,CREATOR_TIME,CREATOR";
		String grid_size = "10";
		request.setAttribute("grid_sql", sql);
		request.setAttribute("grid_columns", grid_columns);
		request.setAttribute("grid_size", grid_size);

		ExtComponentServlet extComponentServlet = new ExtComponentServlet();
		extComponentServlet.processGrid(request, response, true);
	}
	/**
	 * 修改
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView modify(HttpServletRequest request, HttpServletResponse response){
		String scrappedId = request.getParameter("scrappedId");
		request.setAttribute("scrappedId",scrappedId);
		TScrapped ts = this.scrappedService.findScrapped(scrappedId);
		request.setAttribute("cmd",ts.getCmd());
		request.setAttribute("applyTime",ts.getApplyTime());
		request.setAttribute("approvedTime",ts.getApprovedTime());
		request.setAttribute("deptId", ts.getDeptId());
		request.setAttribute("nosubmit", request.getParameter("nosubmit"));
		return scrappedInfo(request, response);
	}
	/**
	 * 只读页面
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView view(HttpServletRequest request, HttpServletResponse response){
		request.setAttribute("readable",true);
		return modify(request,response);
	}
	/**
	 * 增加物料
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	public void addMaterial(HttpServletRequest request, HttpServletResponse response) throws IOException{
		Map<String,String> map = extractFR(request);
		String scrappedId = request.getParameter("scrappedId");
		String matnr = request.getParameter("MATNR");
		String maktx = map.get("MAKTX");
		String meins = request.getParameter("MEINS");
		TScrappedMaterial sm = new TScrappedMaterial();
		sm.setScrappedId(scrappedId);
		sm.setScrappedMaterialId(CodeGenerator.getUUID());
		sm.setMaterialCode(matnr);
		sm.setMaterialName(maktx);
		sm.setMaterialUnit(meins);
		this.scrappedService.saveMaterial(sm);
		JSONObject jsonObject = JSONObject.fromObject(sm);
		String jsonText = jsonObject.toString();
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		response.getWriter().print(jsonText);
	}
	/**
	 * 删除物料
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	public void delMaterial(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String materialId = request.getParameter("scrappedMaterialId");
		this.scrappedService.delMaterial(materialId);
		this.viewMessage(request, response, "删除成功!");
	}
	/**
	 * 更新物料字段
	 * @param request
	 * @param response
	 */
	public void updateMateriel(HttpServletRequest request, HttpServletResponse response){
		Map<String,String> map = extractFR(request);
		String scrappedMaterialId= request.getParameter("scrappedMaterialId");
		String colName = map.get("colName");
		String colValue= map.get("colValue");
		int rtn = this.scrappedService.updateMaterial(scrappedMaterialId,colName,colValue);
	}
	/**
	 * 保存报废主信息
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	public void updateScrapped(HttpServletRequest request, HttpServletResponse response) throws IOException{
		saveScrapped(request, response,false);
		this.viewMessage(request, response, "保存成功!");
	}
	/**
	 * 
	 * @param request
	 * @param response
	 */
	private void saveScrapped(HttpServletRequest request, HttpServletResponse response,boolean flag){
		String scrappedId = request.getParameter("scrappedId");
		String deptId = request.getParameter("deptId");
		UserContext txt = UserContextHolder.getLocalUserContext().getUserContext();
		TScrapped s = scrappedService.findScrapped(scrappedId);
		if(s.getScrappedId()==null) s.setScrappedId(CodeGenerator.getUUID());
		s.setCmd(request.getParameter("cmd"));
		if(!"yes".equals(request.getParameter("nosubmit"))){
			s.setDeptId(deptId);
			if(flag)
				s.setApplyTime(DateUtils.getTimeStr(new Date(), 2));
			s.setCreator(txt.getSysUser().getRealName());
			s.setCreatorTime(DateUtils.getCurrDate(2));
			
			s.setIsAvailable("1");
			s.setExamineState("1");
		}
		this.scrappedService.saveScrapped(s);
	}
	/**
	 * 页面显示信息
	 * @param request
	 * @param response
	 * @param msg
	 * @throws IOException
	 */
	private void viewMessage(HttpServletRequest request, HttpServletResponse response,String msg) throws IOException{
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("returnMessage", msg);
		String jsontext = jsonObject.toString();
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		this.operateSuccessfullyWithString(response, jsontext);
	}
	/**
	 * 删除报废主信息
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void delScrapped(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String scrappedId = request.getParameter("scrappedId");
		this.scrappedService.delScrapped(scrappedId);
		
		this.viewMessage(request, response, "删除成功!");
	}
	/**
	 * 保存并提交审批
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	public void firstSubmit(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String msg="";
		try {
			this.saveScrapped(request, response, true);
			this.submit(request, response);
		}catch(Exception e){
			msg="异常类:" + e.getClass().getName() + "<br>异常信息:"+ e.getMessage();
			e.printStackTrace();
		}
		this.viewMessage(request, response, msg==""?"提交成功!":msg);
	}
	/**
	 * 
	 * @param request
	 * @param response
	 */
	private void submit(HttpServletRequest request, HttpServletResponse response){
		String scrappedId = request.getParameter("scrappedId");
		TScrapped ts = this.scrappedService.findScrapped(scrappedId);
		String taskId = request.getParameter("workflowTaskId");
		String workflowLeaveTransitionName = request.getParameter("workflowLeaveTransitionName");
		String workflowExamine = request.getParameter("workflowExamine");
		ts.setWorkflowTaskId(taskId);
		ts.setWorkflowLeaveTransitionName(workflowLeaveTransitionName);
		ts.setWorkflowExamine(workflowExamine);
		ts.setWorkflowBusinessNote("报废申请审批");
		this.scrappedService.submit(taskId,ts);
		
	}
	/**
	 * 流程提交
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void submitScrapped(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String msg="";
		try {
			this.submit(request, response);
		}catch(Exception e){
			msg="异常类:" + e.getClass().getName() + "<br>异常信息:"+ e.getMessage();
			e.printStackTrace();
		}
		this.viewMessage(request, response, msg==""?"提交成功!":msg);
	}

	/**
	 * 待办链接
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView link(HttpServletRequest request, HttpServletResponse response){
		String scrappedId = request.getParameter("businessRecordId");
		String taskId = request.getParameter("taskId");
		TaskInstanceContext taskInstanceContext = WorkflowService.getTaskInstanceContext(taskId);

		String taskName = taskInstanceContext.getTaskName();
		
		request.setAttribute("businessRecordId", scrappedId);
		request.setAttribute("taskId", taskId);
		//业务信息
		if(taskName.indexOf("修改")>=0){
			request.setAttribute("iframeSrc",//<iframe src="${iframeSrc}" width=100% height=350 id="content" ></iframe>
					"scrappedController.spr?action=modify&nosubmit=yes&scrappedId="+scrappedId);
		}else 
		request.setAttribute("iframeSrc",//<iframe src="${iframeSrc}" width=100% height=350 id="content" ></iframe>
				"scrappedController.spr?action=view&scrappedId="+scrappedId);
		//提交动作
		request.setAttribute("submitUrl", "scrappedController.spr");
		request.setAttribute("action", "submitScrapped");
		//动作参数
		request.setAttribute("iframeForms","'scrappedId="+scrappedId+"'");
		return new ModelAndView("sapss/payment/paymentImports/paymentWorkflowManager");

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

	public void setSysDeptService(SysDeptService sysDeptService) {
		this.sysDeptService = sysDeptService;
	}

}
