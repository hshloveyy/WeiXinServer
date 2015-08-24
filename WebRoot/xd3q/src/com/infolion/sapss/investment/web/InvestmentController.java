/*
 * @(#)JdPurchaseController.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：陈喜平
 *  时　间：2009-6-25
 *  描　述：创建
 */

package com.infolion.sapss.investment.web;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Collections;
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
import com.infolion.platform.component.workflow.WorkflowService;
import com.infolion.platform.console.sys.context.UserContext;
import com.infolion.platform.console.sys.context.UserContextHolder;
import com.infolion.platform.core.web.BaseMultiActionController;
import com.infolion.platform.util.CodeGenerator;
import com.infolion.platform.util.DateUtils;
import com.infolion.sapss.investment.domain.TInvestmentInfo;
import com.infolion.sapss.investment.domain.TInvestmentPaymentInfo;
import com.infolion.sapss.investment.domain.TInvestmentProjectInfo;
import com.infolion.sapss.investment.service.InvestmentService;

public class InvestmentController extends BaseMultiActionController{

	@Autowired
	private InvestmentService investmentService;

	public void setInvestmentService(InvestmentService investmentService) {
		this.investmentService = investmentService;
	}
	/**
	 * 菜单链接地址
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView toCompositeQuery(HttpServletRequest request, HttpServletResponse response){
		String TYPE = request.getParameter("TYPE");
		request.setAttribute("TYPE", TYPE);
		return new ModelAndView("sapss/investment/investmentCompositeQuery");
	}
	/**
	 * 菜单链接地址
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView toManage(HttpServletRequest request, HttpServletResponse response){
		String TYPE = request.getParameter("TYPE");
		request.setAttribute("TYPE", TYPE);
		return new ModelAndView("sapss/investment/investmentManager");
	}
	/**
	 * 弹出详细页面
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView toInfo(HttpServletRequest request, HttpServletResponse response){
		UserContext txt = UserContextHolder.getLocalUserContext().getUserContext();
		request.setAttribute("TYPE", request.getParameter("TYPE")) ;
		request.setAttribute("deptName",txt.getSysDept().getDeptname());
		request.setAttribute("createTime",DateUtils.getCurrTime(2));
		return new ModelAndView("sapss/investment/investmentInfo");
	}
	/**
	 * 
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws ServletException 
	 */
	public void find(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		UserContext txt = UserContextHolder.getLocalUserContext().getUserContext();
		String start = request.getParameter("startDate");
		String end = request.getParameter("endDate");
		Map<String,String> map = extractFR(request);
		String investmentType = map.get("investmentType");
		String childType = map.get("childType");
		String subFlow = map.get("subFlow");
		String type = map.get("TYPE");
		String deptId = request.getParameter("deptId");
		String sql = "select ts.*,ts.status STATUS_D_EXAMINE_STATE from t_investment_info ts where ts.is_available='1' ";
		if(StringUtils.isNotBlank(type))
			sql = sql+" and ts.examine_type='"+type+"'";
		
		if(StringUtils.isNotBlank(start) && StringUtils.isNotBlank(end))
			sql = sql+" and ts.approved_time between '"+start+"' and '"+end+"'";
		if(StringUtils.isNotBlank(investmentType))
			sql = sql+" and ts.investment_type='"+investmentType+"'";
		if(StringUtils.isNotBlank(childType))
			sql = sql+" and ts.child_type='"+childType+"'";
		if(StringUtils.isNotBlank(subFlow))
			sql = sql+" and ts.sub_flow='"+subFlow+"'";
		if(StringUtils.isNotBlank(deptId))
			sql = sql+" and ts.dept_id='"+deptId+"'";
		else if("1".equals(txt.getSysDept().getIsFuncDept())){
			sql = sql+" and ts.dept_id in("+txt.getGrantedDepartmentsId()+")";
		}else
			sql = sql+" and ts.dept_id='"+txt.getSysDept().getDeptid()+"'";
			
		sql = sql + " order by ts.CREATE_TIME desc";
		String grid_columns = "info_id,investment_type,STATUS_D_EXAMINE_STATE,examine_type,SUB_FLOW,CREATOR,create_time,dept_id,dept_name";
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
		
		String infoId = request.getParameter("infoId");
		TInvestmentInfo ts = this.investmentService.findInfo(infoId);
		TInvestmentPaymentInfo payment = this.investmentService.findPayment(ts.getInfoId());
		if(payment==null)
			payment = new TInvestmentPaymentInfo();
		request.setAttribute("info",ts);
		request.setAttribute("payment",payment);
		request.setAttribute("TYPE", ts.getExamineType());
		request.setAttribute("deptName",ts.getDeptName());
		request.setAttribute("createTime",ts.getCreateTime());
		return new ModelAndView("sapss/investment/investmentInfo");
	}
	/**
	 * 只读页面
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView view(HttpServletRequest request, HttpServletResponse response){
		String taskId = request.getParameter("taskId");
		String infoId = request.getParameter("infoId");
		String nodeName="";
		if(taskId!=null&& !"".equals(taskId))
			nodeName= WorkflowService.getTaskInstanceContext(infoId, taskId).getTaskName();
		if(nodeName.indexOf("修改")!=-1){
			request.setAttribute("readable",false);
			request.setAttribute("disableBtn",true);
		}else
			request.setAttribute("readable",true);
		return modify(request,response);
	}
	/**
	 * 保存
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	public void saveInfo(HttpServletRequest request, HttpServletResponse response,TInvestmentInfo t) throws IOException{
		UserContext txt = UserContextHolder.getLocalUserContext().getUserContext();
		t.setCreator(txt.getSysUser().getRealName());
		t.setCreatorId(txt.getSysUser().getUserId());
		t.setDeptId(txt.getSysDept().getDeptid());
		t.setDeptName(txt.getSysDept().getDeptname());
//		t.setCreateTime(DateUtils.getCurrDate(2));
		t.setIsAvailable("1");
		t.setStatus("1");
		if(t.getInfoId()==null || "".equals(t.getInfoId()))
			t.setInfoId(CodeGenerator.getUUID());
		this.investmentService.saveInfo(t);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("returnMessage", "保存成功");
		if(t.getSubFlow().indexOf("付款")!=-1)
			jsonObject.put("enablePayment",true);
		else
			jsonObject.put("enablePayment",false);
		jsonObject.put("INFO_ID",t.getInfoId());
		String jsontext = jsonObject.toString();
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		this.operateSuccessfullyWithString(response, jsontext);
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
	 * 删除
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void delInfo(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String infoId = request.getParameter("infoId");
		this.investmentService.delInfo(infoId);
		
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
//			this.savePurchase(request, response, true);
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
		String infoId = request.getParameter("infoId");
		TInvestmentInfo t = this.investmentService.findInfo(infoId);
		String taskId = request.getParameter("workflowTaskId");
		String workflowLeaveTransitionName = request.getParameter("workflowLeaveTransitionName");
		String workflowExamine = request.getParameter("workflowExamine");
		t.setWorkflowTaskId(taskId);
		t.setWorkflowLeaveTransitionName(workflowLeaveTransitionName);
		t.setWorkflowExamine(workflowExamine);
		t.setWorkflowBusinessNote("投资审批");
		t.setWorkflowModelName("投资管理");
		t.setWorkflowProcessUrl("investmentController.spr?action=link");
		this.investmentService.submit(taskId,t);
		
	}
	/**
	 * 流程提交
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void submitInfo(HttpServletRequest request, HttpServletResponse response) throws IOException{
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
		String infoId = request.getParameter("businessRecordId");
		String taskId = request.getParameter("taskId");
		
		request.setAttribute("businessRecordId", infoId);
		request.setAttribute("taskId", taskId);
		//业务信息
		request.setAttribute("iframeSrc",//<iframe src="${iframeSrc}" width=100% height=350 id="content" ></iframe>
				"investmentController.spr?action=view&infoId="+infoId+"&taskId="+taskId);
		//提交动作
		request.setAttribute("submitUrl", "investmentController.spr");
		request.setAttribute("action", "submitInfo");
		//动作参数
		request.setAttribute("iframeForms","'infoId="+infoId+"'");
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
	/**
	 * 
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws ServletException 
	 */
	public void rtnChildTypeDS(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String module = request.getParameter("module");
		if(module!=null){
		String sql="select distinct(child_type),child_type_name from t_investment_type where module='"+module +"' ";
		request.setAttribute("grid_sql", sql);
		request.setAttribute("grid_columns", "child_type,child_type_name");
		request.setAttribute("grid_size", "10");

		ExtComponentServlet extComponentServlet = new ExtComponentServlet();
		extComponentServlet.processGrid(request, response, true);
		}
	}
	/**
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void rtnSubFlowDS(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		Map<String,String> map = extractFR(request);
		String module = request.getParameter("module");
		String child_type_name = map.get("childType");
		if(child_type_name!=null && module!=null){
		String sql="select * from t_investment_type where module="+module+" and child_type_name='"+child_type_name+"'";
		request.setAttribute("grid_sql", sql);
		request.setAttribute("grid_columns", "sub_flow,sub_flow_name");
		request.setAttribute("grid_size", "10");
		
		ExtComponentServlet extComponentServlet = new ExtComponentServlet();
		extComponentServlet.processGrid(request, response, true);
		}
	}
	/**
	 * 
	 * @param request
	 * @param response
	 */
	public ModelAndView addProject(HttpServletRequest request, HttpServletResponse response){
		request.setAttribute("ipId", request.getParameter("ipId"));
		return new ModelAndView("sapss/investment/investmentProjectInfo");
	}
	/**
	 * 
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	public void saveProject(HttpServletRequest request, HttpServletResponse response,TInvestmentProjectInfo info) throws IOException{
		if(info.getPid()==null || "".equals(info.getPid()))
			info.setPid(CodeGenerator.getUUID());
		this.investmentService.saveProject(info);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("returnMessage", "保存成功");
		jsonObject.put("PID",info.getPid());
		String jsontext = jsonObject.toString();
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		this.operateSuccessfullyWithString(response, jsontext);
	}
	/**
	 * 
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	public void delProject(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String pid= request.getParameter("pid");
		this.investmentService.delProject(pid);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("returnMessage", "删除成功");
		String jsontext = jsonObject.toString();
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		this.operateSuccessfullyWithString(response, jsontext);

	}
	/**
	 * 
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws ServletException 
	 */
	public void findProject(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String ipId = request.getParameter("ipId");
		String sql = "select * from t_investment_project_info where ip_id='"+ipId+"'";
		String grid_columns = "pid,investment_code,project_time,count";
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
	 * @param info
	 * @throws IOException
	 */
	public void savePayment(HttpServletRequest request, HttpServletResponse response,TInvestmentPaymentInfo info) throws IOException{
		if(info.getIpId()==null || "".equals(info.getIpId()))
			info.setIpId(CodeGenerator.getUUID());
		UserContext txt = UserContextHolder.getLocalUserContext().getUserContext();
		info.setCreator(txt.getSysUser().getRealName());
		info.setCreatorId(txt.getSysUser().getUserId());
		info.setDeptId(txt.getSysDept().getDeptid());
		info.setDeptName(txt.getSysDept().getDeptname());
		info.setCreateTime(DateUtils.getCurrDate(2));
		info.setIsAvailable("1");
		info.setStatus("1");

		this.investmentService.savePayment(info);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("returnMessage", "保存成功");
		jsonObject.put("IPID",info.getIpId());
		String jsontext = jsonObject.toString();
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		this.operateSuccessfullyWithString(response, jsontext);
	}
	
}
