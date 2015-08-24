/*
 * @(#)ParticularWorkflowController.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：陈喜平
 *  时　间：2009-6-11
 *  描　述：创建
 */

package com.infolion.sapss.workflow.particular.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

import com.infolion.platform.component.workflow.WorkflowService;
import com.infolion.platform.console.sys.context.UserContext;
import com.infolion.platform.console.sys.context.UserContextHolder;
import com.infolion.platform.core.web.BaseMultiActionController;
import com.infolion.platform.util.CodeGenerator;
import com.infolion.platform.util.DateUtils;
import com.infolion.sapss.workflow.particular.domain.TParticularWorkflow;
import com.infolion.sapss.workflow.particular.service.ParticularWorkflowService;

public class ParticularWorkflowController extends BaseMultiActionController{
	@Autowired
	private ParticularWorkflowService particularWorkflowService;
	public void setParticularWorkflowService(
			ParticularWorkflowService particularWorkflowService) {
		this.particularWorkflowService = particularWorkflowService;
	}
	/**
	 * 首次提交流程审批
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void firstSubmitParticularWorkflow(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String msg="";
		try {
			String bizId = request.getParameter("bizId");
			String parms = request.getParameter("parms");
			String controller = request.getParameter("controller");
			String title =request.getParameter("title");
			
			TParticularWorkflow obj = new TParticularWorkflow();
			obj.setOriginalBizId(bizId);
			obj.setParticularId(CodeGenerator.getUUID());
			obj.setWorkflowBusinessNote(title);
			obj.setCreatedTime(DateUtils.getCurrTime(DateUtils.HYPHEN_DISPLAY_DATE));
			UserContext ctext = UserContextHolder.getLocalUserContext().getUserContext();
			obj.setCreator(ctext.getSysUser().getUserName());
			obj.setModuleName(title);
			obj.setLinkUrl("");
			this.particularWorkflowService.save(obj);
			this.particularWorkflowService.firstSubmitParticularWorkflow(controller,parms,obj);
		} catch (Exception e) {
			msg="异常类:" + e.getClass().getName() + "<br>异常信息:"+ e.getMessage();
			e.printStackTrace();
		}
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
		
	}
	/**
	 * 待办链接
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ModelAndView linkSubmitParticularWorkflow(HttpServletRequest request,HttpServletResponse response) throws IOException{
		//从代办中取得业务记录ID
		String pariticularId = request.getParameter("businessRecordId");
		String taskId = request.getParameter("taskId");
		//历史跟踪
		request.setAttribute("businessRecordId", pariticularId);
		//取得原业务记录Id
		String originalBizId = this.particularWorkflowService.find(pariticularId);
		//
		request.setAttribute("taskId", taskId);
		String parm = request.getParameter("parms");
		String controller = request.getParameter("controller");
		//解析其它参数
		String parms = urlParms(parm);
		request.setAttribute("iframeSrc",controller+".spr?action=openSubmitParticularWorkflow&bizId="+ originalBizId+"&particularId="+pariticularId+"&fromParticular=true"+parms);
		request.setAttribute("submitUrl", "particularWorkflowController.spr");
		//参数：特批ID，控制器，其它参数
		request.setAttribute("action", "submitParticularWorkflow&pariticularId="+ pariticularId+"&controller="+controller+"&parm="+parm);
		request.setAttribute("revisable", "false");// 
		//iframe表单域
		request.setAttribute("iframeForms", "'p=p'");
		String taskName = WorkflowService.getTaskInstanceContext(taskId).getTaskName();
		if(taskName.indexOf("出纳")>=0){
			request.setAttribute("isPrintView", "true");
			request.setAttribute("controller", controller);
			request.setAttribute("bizId", originalBizId);
		}
		else request.setAttribute("isPrintView", "false");
		return new ModelAndView("sapss/project/workflowManager");
	}
	/**
	 * 
	 * @param parms
	 * @return
	 */
	private String urlParms(String parms){
		if(parms!=null &&!"".equals(parms)){
			String ss[] = parms.split(";");
			String s="",result="";
			for (int i = 0; i < ss.length; i++) {
				s = ss[i];
				String str[] = s.split(",");
				result=result+"&"+ str[0]+"="+str[1];
			}
			return result;
		}else
			return "";
	}
	/**
	 * 提交特批流程审批
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void submitParticularWorkflow(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String msg="";
		try{
			String taskId = request.getParameter("workflowTaskId");
			String workflowLeaveTransitionName = request.getParameter("workflowLeaveTransitionName");
			String workflowExamine = request.getParameter("workflowExamine");
			String pariticularId = request.getParameter("pariticularId");
			String controller = request.getParameter("controller");
			String parm = request.getParameter("parm");
			TParticularWorkflow obj = new TParticularWorkflow();
			obj.setParticularId(pariticularId);
			obj.setWorkflowExamine(workflowExamine);
			obj.setWorkflowTaskId(taskId);
			obj.setWorkflowLeaveTransitionName(workflowLeaveTransitionName);
			//obj.setWorkflowProcessUrl("particularWorkflowController.spr?action=linkSubmitParticularWorkflow&controller="+controller+"&parm="+parm);//设置待办链接的URL
			obj.setWorkflowBusinessNote("特批流程审批");
			Map variable = new HashMap();
			variable.put("PARTICULAR_ID",pariticularId);
			variable.put("CONTROLLER", controller);
			obj.setWorkflowUserDefineTaskVariable(variable);
			this.particularWorkflowService.submitParticularWorkflow(obj);

		} catch (Exception e) {
			msg="异常类:" + e.getClass().getName() + "<br>异常信息:"+ e.getMessage();
			e.printStackTrace();
		}
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

}
