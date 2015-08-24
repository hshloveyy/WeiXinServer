/*
 * @(#)JdPurchaseController.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：陈喜平
 *  时　间：2009-6-25
 *  描　述：创建
 */

package com.infolion.sapss.jdPurchase.web;

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
import com.infolion.platform.console.sys.context.UserContext;
import com.infolion.platform.console.sys.context.UserContextHolder;
import com.infolion.platform.core.web.BaseMultiActionController;
import com.infolion.platform.util.CodeGenerator;
import com.infolion.platform.util.DateUtils;
import com.infolion.sapss.jdPurchase.domain.TJindaPurchase;
import com.infolion.sapss.jdPurchase.service.JdPurchaseService;

public class JdPurchaseController extends BaseMultiActionController{

	@Autowired
	private JdPurchaseService jdPurchaseService;

	public void setJdPurchaseService(JdPurchaseService jdPurchaseService) {
		this.jdPurchaseService = jdPurchaseService;
	}
	/**
	 * 菜单链接地址
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView toPurchaseManage(HttpServletRequest request, HttpServletResponse response){
		String tradeType = request.getParameter("bizType");
		request.setAttribute("tradeType", tradeType);
		return new ModelAndView("sapss/jindaPurchase/purchaseManager");
	}
	/**
	 * 弹出详细页面
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView toPurchaseInfo(HttpServletRequest request, HttpServletResponse response){
		return purchaseInfo(request, response);
	}
	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	private ModelAndView purchaseInfo(HttpServletRequest request, HttpServletResponse response){
		UserContext txt = UserContextHolder.getLocalUserContext().getUserContext();

		return new ModelAndView("sapss/jindaPurchase/purchaseInfo");
		
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
		Map<String,String> map = this.extractFR(request);
		String applier = map.get("applier");
		String sql = "select ts.*,ts.examine_status EXAMINE_STATUS_D_EXAMINE_STATE from T_JINDA_PURCHASE ts where ts.is_available='1' ";
		if(StringUtils.isNotBlank(applier))
			sql = sql+" and ts.applier like '%"+applier+"%'";
		if(StringUtils.isNotBlank(start) && StringUtils.isNotBlank(end))
			sql = sql+" and ts.approved_time between '"+start+"' and '"+end+"'";
		sql +=" order by ts.CREATE_TIME desc";
		String grid_columns = "SAP_PURCHASE_NO,PURCHASE_ID,EXAMINE_STATUS_D_EXAMINE_STATE,APPLY_TIME,APPROVED_TIME,APPLY_ACCOUNT,CREATOR,APPLIER";
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
		String purchaseId = request.getParameter("purchaseId");
		TJindaPurchase ts = this.jdPurchaseService.findPurchase(purchaseId);
		request.setAttribute("info",ts);
		request.setAttribute("applyTime",ts.getApplyTime());
		return purchaseInfo(request, response);
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
	 * 保存
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	public void savePurchase(HttpServletRequest request, HttpServletResponse response,TJindaPurchase jinda) throws IOException{
		UserContext txt = UserContextHolder.getLocalUserContext().getUserContext();
		jinda.setCreator(txt.getSysUser().getRealName());
		jinda.setCreatorTime(DateUtils.getCurrDate(2));
		jinda.setApplyTime(DateUtils.getCurrTime(2));
		jinda.setCmd(request.getParameter("cmd"));
		jinda.setIsAvailable("1");
		jinda.setExamineStatus("1");
		if(jinda.getPurchaseId()==null || "".equals(jinda.getPurchaseId()))
			jinda.setPurchaseId(CodeGenerator.getUUID());
		this.jdPurchaseService.savePurchase(jinda);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("returnMessage", "保存成功");
		jsonObject.put("PURCHASE_ID",jinda.getPurchaseId());
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
	public void delPurchase(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String purchaseId = request.getParameter("purchaseId");
		this.jdPurchaseService.delPurchase(purchaseId);
		
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
		String purchaseId = request.getParameter("purchaseId");
		TJindaPurchase ts = this.jdPurchaseService.findPurchase(purchaseId);
		String taskId = request.getParameter("workflowTaskId");
		String workflowLeaveTransitionName = request.getParameter("workflowLeaveTransitionName");
		String workflowExamine = request.getParameter("workflowExamine");
		ts.setWorkflowTaskId(taskId);
		ts.setWorkflowLeaveTransitionName(workflowLeaveTransitionName);
		ts.setWorkflowExamine(workflowExamine);
		ts.setWorkflowBusinessNote("采购合同审批");
		ts.setWorkflowModelName("金达采购合同");
		ts.setWorkflowProcessUrl("jdPurchaseController.spr?action=link");
		this.jdPurchaseService.submit(taskId,ts);
		
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
		String purchaseId = request.getParameter("businessRecordId");
		String taskId = request.getParameter("taskId");
		
		request.setAttribute("businessRecordId", purchaseId);
		request.setAttribute("taskId", taskId);
		//业务信息
		request.setAttribute("iframeSrc",//<iframe src="${iframeSrc}" width=100% height=350 id="content" ></iframe>
				"jdPurchaseController.spr?action=view&purchaseId="+purchaseId);
		//提交动作
		request.setAttribute("submitUrl", "jdPurchaseController.spr");
		request.setAttribute("action", "submitPurchase");
		//动作参数
		request.setAttribute("iframeForms","'purchaseId="+purchaseId+"'");
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


}
