/*
 * @(#)JdPurchaseController.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：陈喜平
 *  时　间：2009-6-25
 *  描　述：创建
 */

package com.infolion.sapss.storageProtocol.web;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
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
import com.infolion.platform.component.workflow.WorkflowService;
import com.infolion.platform.component.workflow.ext.TaskInstanceContext;
import com.infolion.platform.console.sys.context.UserContext;
import com.infolion.platform.console.sys.context.UserContextHolder;
import com.infolion.platform.core.BusinessException;
import com.infolion.platform.core.web.BaseMultiActionController;
import com.infolion.platform.sys.cache.SysCachePoolUtils;
import com.infolion.platform.util.CodeGenerator;
import com.infolion.platform.util.DateUtils;
import com.infolion.sapss.Constants;
import com.infolion.sapss.common.WorkflowUtils;
import com.infolion.sapss.contract.domain.TContractSalesInfo;
import com.infolion.sapss.contract.domain.TSalesChange;
import com.infolion.sapss.project.domain.TProjectChange;
import com.infolion.sapss.project.domain.TProjectInfo;
import com.infolion.sapss.storageProtocol.domain.TStorageProtocol;
import com.infolion.sapss.storageProtocol.domain.TStorageProtocolChange;
import com.infolion.sapss.storageProtocol.service.StorageProtocolService;

public class StorageProtocolController extends BaseMultiActionController{

	@Autowired
	private StorageProtocolService storageProtocolService;

	public void setStorageProtocolService(StorageProtocolService storageProtocolService) {
		this.storageProtocolService = storageProtocolService;
	}
	/**
	 * 菜单链接地址
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView toCompositeQuery(HttpServletRequest request, HttpServletResponse response){
		//投资项目规划与计划审批
		//投资项目决策审批
		//投资项目执行审批
		//投资项目日常监控与专项评估
		//投资项目处置
		String TYPE = request.getParameter("TYPE");
		request.setAttribute("TYPE", TYPE);
		UserContext txt = UserContextHolder.getLocalUserContext().getUserContext();
		if(txt.getSysDept().getDeptid().equals(Constants.FILE_DEPT_NAME_ID)){
			request.setAttribute("fileDisable", "false");
		}
		return new ModelAndView("sapss/storageProtocol/storageProtocolCompositeQuery");
	}
	/**
	 * 菜单链接地址
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView toManage(HttpServletRequest request, HttpServletResponse response){
		//投资项目规划与计划审批
		//投资项目决策审批
		//投资项目执行审批
		//投资项目日常监控与专项评估
		//投资项目处置
		UserContext userContext = UserContextHolder.getLocalUserContext()
		.getUserContext();
		request.setAttribute("loginer", userContext.getSysUser()
				.getUserId());
		String TYPE = request.getParameter("TYPE");
		request.setAttribute("TYPE", TYPE);
		return new ModelAndView("sapss/storageProtocol/storageProtocolManager");
	}
	/**
	 * 弹出详细页面
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView toInfo(HttpServletRequest request, HttpServletResponse response){
		return info(request, response);
	}
	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	private ModelAndView info(HttpServletRequest request, HttpServletResponse response){
		UserContext txt = UserContextHolder.getLocalUserContext().getUserContext();
		request.setAttribute("TYPE", request.getParameter("TYPE")) ;
		request.setAttribute("deptName",txt.getSysDept().getDeptname()) ;
		return new ModelAndView("sapss/storageProtocol/storageProtocolInfo");
		
	}
	/**
	 * 
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws ServletException 
	 */
	public void find(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		Map map = extractFR(request);
		String start = request.getParameter("startDate");
		String end = request.getParameter("endDate");
		String applyType = request.getParameter("applyType");
		String deptId = request.getParameter("deptId");
		String applyStatus = request.getParameter("applyStatus");
		String sql = "select ts.*,ts.status STATUS_D_STORAGEPROTOCOL_STATE from t_storage_protocol ts where ts.is_available='1' ";
		if(StringUtils.isNotBlank(applyType))
			sql = sql+" and ts.apply_type = '"+applyType+"'";
		if(StringUtils.isNotBlank(deptId))
			sql = sql+" and ts.dept_id = '"+deptId+"'";
		if(StringUtils.isNotBlank(applyStatus))
			sql = sql+" and ts.status = '"+applyStatus+"'";
		String projectNo = request.getParameter("projectNo");
		if(StringUtils.isNotBlank(projectNo))
			sql = sql+" and ts.project_No like '%"+projectNo+"%'";
		String com = (String)map.get("com");
		if(StringUtils.isNotBlank(com))
			sql = sql+" and ts.com like '%"+com+"%'";
		String protocolNo = request.getParameter("protocolNo");
		if(StringUtils.isNotBlank(protocolNo))
			sql = sql+" and ts.protocol_No like '%"+protocolNo+"%'";
		
		
		
		if(StringUtils.isNotBlank(start) && StringUtils.isNotBlank(end))
			sql = sql+" and ts.approved_time between '"+start+"' and '"+end+"'";
		
		sql +=" and ts.dept_id in ("+UserContextHolder.getLocalUserContext().getUserContext().getGrantedDepartmentsId()+")";
		sql=sql+ " order by ts.CREATE_TIME desc";
		String grid_columns = "info_id,apply_type,STATUS_D_STORAGEPROTOCOL_STATE,KEEP_FLAG,CREATOR,CREATOR_ID,create_time,dept_id,dept_name,COM,PROJECT_NO,PROTOCOL_NO";
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
		TStorageProtocol ts = this.storageProtocolService.findInfo(infoId);
		request.setAttribute("info",ts);
		request.setAttribute("nosub", request.getParameter("nosub"));
		return info(request, response);
	}
	/**
	 * 只读页面
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView view(HttpServletRequest request, HttpServletResponse response){
		UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
		if("1".equals(userContext.getSysDept().getIsFuncDept()))
			request.setAttribute("readable",false);
		else request.setAttribute("readable",true);
		return modify(request,response);
	}
	/**
	 * 保存
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	public void saveInfo(HttpServletRequest request, HttpServletResponse response,TStorageProtocol t) throws IOException{
		UserContext txt = UserContextHolder.getLocalUserContext().getUserContext();
		if("".equals(t.getCreator())||t.getCreator()==null){
		     t.setCreator(txt.getSysUser().getRealName());
		}
		if("".equals(t.getCreatorId())||t.getCreatorId()==null){
		     t.setCreatorId(txt.getSysUser().getUserId());
		}
		if("".equals(t.getDeptId())||t.getDeptId()==null){
		     t.setDeptId(txt.getSysDept().getDeptid());
		}
		if("".equals(t.getDeptName())||t.getDeptName()==null){
		     t.setDeptName(txt.getSysDept().getDeptname());
		}
		if("".equals(t.getCreateTime())||t.getCreateTime()==null){
		     t.setCreateTime(DateUtils.getCurrDate(2));
		}
		t.setIsAvailable("1");
		if("".equals(t.getStatus())||t.getStatus()==null){
		     t.setStatus("1");
		}
		if(t.getInfoId()==null || "".equals(t.getInfoId()))
			t.setInfoId(CodeGenerator.getUUID());
		String contractIds[] = request
		.getParameterValues("contractPurchaseId");
		this.storageProtocolService.saveInfo(t,contractIds);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("returnMessage", "保存成功");
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
		this.storageProtocolService.delInfo(infoId);
		
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
		TStorageProtocol t = this.storageProtocolService.findInfo(infoId);
		String taskId = request.getParameter("workflowTaskId");
		String workflowLeaveTransitionName = request.getParameter("workflowLeaveTransitionName");
		String workflowExamine = request.getParameter("workflowExamine");
		t.setWorkflowTaskId(taskId);
		t.setWorkflowLeaveTransitionName(workflowLeaveTransitionName);
		t.setWorkflowExamine(workflowExamine);
		t.setWorkflowBusinessNote(t.getDeptName()+"|"+t.getCreator()+"|立项号:"+t.getProjectNo()+"|货物品名:"+t.getGoods());
		t.setWorkflowModelName("仓储/货代/保险协议");
		t.setWorkflowProcessUrl("storageProtocolController.spr?action=link");
		this.storageProtocolService.submit(taskId,t);
		
	}
	/**
	 * 流程提交
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void submitPurchase(HttpServletRequest request, HttpServletResponse response) throws IOException{
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
		TaskInstanceContext taskInstanceContext = WorkflowService.getTaskInstanceContext(taskId);
        String taskName = taskInstanceContext.getTaskName();
        if(taskName.indexOf("业务修改")!=-1||taskName.indexOf("贸管")!=-1)
        	request.setAttribute("iframeSrc","storageProtocolController.spr?action=modify&nosub=1&infoId="+infoId);
		//业务信息
        else 
		    request.setAttribute("iframeSrc",//<iframe src="${iframeSrc}" width=100% height=350 id="content" ></iframe>
				"storageProtocolController.spr?action=view&infoId="+infoId);
		//提交动作
		request.setAttribute("submitUrl", "storageProtocolController.spr");
		request.setAttribute("action", "submitPurchase");
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

	public void queryContract(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		String infoId = request.getParameter("infoId");
		// 贸易类型
		String grid_sql = "select * From T_STORAGE_PROTOCOL_C where STORAGE_PROTOCOL_ID='"+infoId+"'";
		String grid_columns = "STORAGE_PROTOCOL_C_ID,CONTRACT_ID, CONTRACT_NO, SAP_ORDER_NO,PAPER_NO,CONTRACT_TYPE,STORAGE_PROTOCOL_ID";
		String grid_size = "5";
		request.setAttribute("grid_sql", grid_sql);
		request.setAttribute("grid_columns", grid_columns);
		request.setAttribute("grid_size", grid_size);
		ExtComponentServlet extComponentServlet = new ExtComponentServlet();
		extComponentServlet.processGrid(request, response, true);
	}
	
	
	public ModelAndView changeW(HttpServletRequest request, HttpServletResponse response){
		String infoId = request.getParameter("infoId");
		TStorageProtocol ts = this.storageProtocolService.findInfo(infoId);
		request.setAttribute("info",ts);
		request.setAttribute("nosub", request.getParameter("nosub"));
		request.setAttribute("readable", request.getParameter("readable"));
		request.setAttribute("from", "changeW");
		request.setAttribute("increateFile", request.getParameter("increateFile"));
		return new ModelAndView("sapss/storageProtocol/storageProtocolChange");
	}
	
	public ModelAndView changeR(HttpServletRequest request, HttpServletResponse response){
		String infoId = request.getParameter("infoId");
		TStorageProtocol ts = this.storageProtocolService.findInfo(infoId);
		request.setAttribute("info",ts);
		request.setAttribute("nosub", request.getParameter("nosub"));
		request.setAttribute("from", "changeR");
		request.setAttribute("readable", request.getParameter("readable"));
		return new ModelAndView("sapss/storageProtocol/storageProtocolChange");
	}
	
	public ModelAndView toChangeTab(HttpServletRequest request, HttpServletResponse response){
		UserContext ut = UserContextHolder.getLocalUserContext().getUserContext();
		String infoId = request.getParameter("infoId");
		request.setAttribute("infoId", infoId);
		//查找变更审批中的记录
		UserContext uc = UserContextHolder.getLocalUserContext().getUserContext();
		List<TStorageProtocolChange> list =  storageProtocolService.findStorageProtocolChangeByInfoId(infoId,"2");	
		TStorageProtocolChange bo = new TStorageProtocolChange();
		if(list!=null && list.size()>0){
			bo =list.get(0);
		} else {
			bo.setChanger(ut.getSysUser().getRealName());
			bo.setInfoId(infoId);
			bo.setChangeTime(DateUtils.getCurrTimeStr(2));
			bo.setChangeNo("0");
			bo.setCreator(uc.getSysUser().getUserId());
		}
		
		//判断是不是信息中心
		if("XXZX".equals(ut.getSysDept().getDeptShortCode())){
			request.setAttribute("closeChange", false);
			bo.setChangeOperator(ut.getSysUser().getRealName());
		}else
			request.setAttribute("closeChange", true);
		//流程中:当退回给业务人员修改时,判断是不是创建人
		String from = request.getParameter("from");
		boolean wfCanModify = false;
		if(from!=null && "changeW".equals(from)){
			if(bo.getCreator().equals(uc.getSysUser().getUserId())){
				wfCanModify = true;
			}
		}
		request.setAttribute("wfCanModify", wfCanModify);
		request.setAttribute("bo", bo);
		request.setAttribute("isChanged", bo.getIsChanged());
		if(wfCanModify)
			return new ModelAndView("sapss/storageProtocol/changeTab");
		else
			return new ModelAndView("sapss/storageProtocol/changeTabR");
	}
	
	public void findChange(HttpServletRequest req,HttpServletResponse resp) throws Exception{
		String infoId= req.getParameter("infoId");
		String sql = "select tpc.*, change_state CHANGE_STATE_D_CHANGE_STATE from" +
				" T_STORAGEPROTOCOL_CHANGE tpc where is_available='1' and info_id='"+infoId+"'";
		String columns="change_desc,change_time,changer,apply_time,approved_time,info_id,change_id,CHANGE_STATE_D_CHANGE_STATE,change_no";
		String size= req.getParameter("limit");
		ExtComponentServlet servlet = new ExtComponentServlet();
		req.setAttribute("grid_sql", sql);
		req.setAttribute("grid_columns", columns);
		req.setAttribute("grid_size", size);
		servlet.processGrid(req, resp, true);
	}
	
	public void saveChange(HttpServletRequest req,HttpServletResponse resp,TStorageProtocolChange tc) throws Exception{
		String infoId = req.getParameter("infoId");
		UserContext ut = UserContextHolder.getLocalUserContext().getUserContext();
		tc.setInfoId(infoId);
		tc.setCreator(ut.getSysUser().getUserId());
		tc.setCreateTime(DateUtils.getCurrTimeStr(2));
		tc.setApplyTime(DateUtils.getCurrTimeStr(2));
		tc.setIsAvailable("1");
		tc.setChangeState("1");
		resp.setCharacterEncoding("GBK");
		resp.setContentType("text/html; charset=GBK");

		if(tc.getChangeId()==null || "".equals(tc.getChangeId().trim())){
			int no=this.storageProtocolService.getChangeNo(infoId);
			no++;
			tc.setChangeNo(no+"");
			String changeId = storageProtocolService.saveChange(tc);
			resp.getWriter().print("{success:true,msg:'保存成功!',changeId:'"+changeId+"',changeNo:'"+no+"'}");
		}	
		else{
			int rst = storageProtocolService.updateChange(tc);
			if(rst>0)
				resp.getWriter().print("{success:true,msg:'更新成功!'}");
			else
				resp.getWriter().print("{success:true,msg:'更新失败!'}");
		}	
	}
	
	public void deleteChange(HttpServletRequest req,HttpServletResponse resp) throws Exception{
		String changeId = req.getParameter("changeId");
		int rst = storageProtocolService.deleteChange(changeId);
		resp.setCharacterEncoding("GBK");
		resp.setContentType("text/html; charset=GBK");
		if (rst > 0)
			resp.getWriter().print("{success:true,msg:'删除成功'}");
		else {
			resp.getWriter().print("{success:false,msg:'记录不能删除'}");
		}

	}
	
	public void submitChangeWorkflow(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String changeId = request.getParameter("changeId");
		//查找变更内容
		TStorageProtocolChange change =  this.storageProtocolService.getChange(changeId);
		UserContext uct = UserContextHolder.getLocalUserContext().getUserContext();
		//如果是信息中心,检查是否填写
		String msg = "";
		try {
		
		TStorageProtocol info = this.storageProtocolService.findInfo(change.getInfoId());
		String taskId = request.getParameter("workflowTaskId");
		change.setWorkflowTaskId(taskId);
		change.setApprovedTime(DateUtils.getCurrTime(2));
		UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
		change.setWorkflowBusinessNote(userContext.getSysDept().getDeptname()
				+ "|"
				+ userContext.getSysUser().getRealName()
				+ "|"
				+ info.getProjectNo()
				+ "|"
				+ info.getCom());
			String workflowLeaveTransitionName = request.getParameter("workflowLeaveTransitionName");
			String workflowExamine = request.getParameter("workflowExamine");
			change.setWorkflowExamine(workflowExamine);
			change.setWorkflowLeaveTransitionName(workflowLeaveTransitionName);
		
			this.storageProtocolService.submitChangeWorkflow(taskId, change,info);
		} catch (Exception e) {
			msg = "异常类:" + e.getClass().getName() + "<br>异常信息:"+ e.getMessage();
			e.printStackTrace();
		}
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		JSONObject jo = new JSONObject();
		if ("".equals(msg)){
			jo.put("returnMessage", "提交成功!");
			jo.put("success", true);
		}	
		else{
			jo.put("returnMessage", msg);
			jo.put("success", true);
		}	
		this.operateSuccessfullyWithString(response, jo.toString());
		//response.getWriter().print(jo.toString());
	}
	
	public ModelAndView changeExamine(HttpServletRequest req,HttpServletResponse resp) throws Exception{
		//从代办中取得业务记录ID
		String changeId = req.getParameter("businessRecordId");
		String taskId = req.getParameter("taskId");
		//历史跟踪
		req.setAttribute("businessRecordId", changeId);
		TStorageProtocolChange change =  this.storageProtocolService.getChange(changeId);
		TStorageProtocol info = storageProtocolService.findInfo(change.getInfoId());
		
		//
		req.setAttribute("taskId", taskId);
		UserContext ut = UserContextHolder.getLocalUserContext().getUserContext();
		TaskInstanceContext taskInstanceContext = WorkflowService.getTaskInstanceContext(taskId);
        String taskName = taskInstanceContext.getTaskName();
		//判断是不是信息中心
		if("XXZX".equals(ut.getSysDept().getDeptShortCode())){
			req.setAttribute("iframeSrc",
					"storageProtocolController.spr?action=changeR&from=changeR&readable=false&infoId="+ info.getInfoId());
		}else if(taskName.indexOf("修改")!=-1){
			req.setAttribute("iframeSrc",
					"storageProtocolController.spr?action=changeW&from=changeW&increateFile=true&readable=true&infoId="+ info.getInfoId());
		}
		else if(taskName.indexOf("贸管部")!=-1){
			req.setAttribute("iframeSrc",
					"storageProtocolController.spr?action=changeW&from=changeW&increateFile=true&readable=true&infoId="+ info.getInfoId());
		}
		else
			req.setAttribute("iframeSrc",
					"storageProtocolController.spr?action=changeR&from=changeR&readable=true&infoId="+ info.getInfoId());
		//提交动作
		req.setAttribute("submitUrl", "storageProtocolController.spr");
		req.setAttribute("revisable", "false");// Form.serialize(window.frames['content']['mainForm'])
		req.setAttribute("iframeForms","Form.serialize(window.frames['content']['mainForm'])");
		req.setAttribute("action", "submitChangeWorkflow&changeId="+ changeId);
		//动作参数
		return new ModelAndView("sapss/project/workflowManager");
		
	}
	
	public ModelAndView selectProtocolInfo(HttpServletRequest request, HttpServletResponse response) throws IOException{
		return new ModelAndView("sapss/storageProtocol/selectProtocolInfo");
	}
	
	public void queryProtocolInfo(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
	{
		String applyType = extractFR(request, "applyType"); // request.getParameter("projectname");
		String strProjectNo = request.getParameter("projectno");
		String deptId = request.getParameter("deptId");
		String protocolNo = request.getParameter("protocolNo");
		String com = extractFR(request, "com");

		String strSql = getQueryProtocolInfoSQL(protocolNo, strProjectNo, deptId, applyType,com);

		String grid_columns = "INFO_ID,PROTOCOL_NO,PROJECT_NO,DEPT_NAME,COM";

		String grid_size = "20";
		request.setAttribute("grid_sql", strSql);
		request.setAttribute("grid_columns", grid_columns);
		request.setAttribute("grid_size", grid_size);


		ExtComponentServlet extComponentServlet = new ExtComponentServlet();
		extComponentServlet.processGrid(request, response, true);
	}
	
	private String extractFR(HttpServletRequest req, String parm) {
		try {
			String wait = java.net.URLDecoder.decode(req.getQueryString(),"UTF-8");
			int pos = wait.indexOf(parm) + parm.length() + 1;
			wait = wait.substring(pos);
			pos = wait.indexOf("&");
			if (pos != -1) {
				wait = wait.substring(0, pos);
			}
			return wait;
		} catch (UnsupportedEncodingException e) {
		}
		return "";
	}
	
	public String getQueryProtocolInfoSQL(String protocolNo, String strProjectNo, String deptId, String applyType,String com)
	{
		UserContext txt = UserContextHolder.getLocalUserContext().getUserContext();
		String strSql = "select a.info_id,a.dept_name,a.protocol_no,a.project_no,a.com from t_storage_protocol a where  a.is_available = '1' ";
		if (protocolNo != null && !"".equals(protocolNo))
		{
			strSql = strSql + " and a.protocol_no like '%" + protocolNo + "%'";
		}

		if (strProjectNo != null && !"".equals(strProjectNo))
		{
			strSql = strSql + " and a.project_no like '%" + strProjectNo + "%'";
		}
		if (com != null && !"".equals(com))
		{
			strSql = strSql + " and a.com like '%" + com + "%'";
		}
		if (applyType != null && !"".equals(applyType))
		{
			strSql = strSql + " and a.apply_Type = '" + applyType + "'";
		}
		if(StringUtils.isNotBlank(deptId))//有选部门
			strSql = strSql + " and a.dept_id = '" + deptId + "'";
		else if(txt.getSysDept().getIsFuncDept()=="1")//是职能部门
			strSql+=" and a.dept_id in("+txt.getGrantedDepartmentsId()+")";
		else //自己所在的部门 
			strSql+=" and a.dept_id in('"+txt.getSysDept().getDeptid()+"')";
		// 立项状态为生效,变更通过
		strSql = strSql + " and a.status in('3','8')";
		
		return strSql;
	}
	
	public void fileProtocol(HttpServletRequest request,	HttpServletResponse response) throws IOException {
		String strIdList = request.getParameter("idList");
		storageProtocolService.fileProtocol(strIdList);
		JSONObject jo = new JSONObject();		
		jo.put("returnMessage", "操作成功！");
		String jsonText = jo.toString();
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		this.operateSuccessfullyWithString(response, jsonText);
	}

}
