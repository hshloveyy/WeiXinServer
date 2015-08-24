/*
 * @(#)ExportIncomeController.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：陈喜平
 *  时　间：2009-5-25
 *  描　述：创建
 */

package com.infolion.sapss.receiptLog.web;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

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
import com.infolion.platform.console.org.domain.SysDept;
import com.infolion.platform.console.sys.context.UserContext;
import com.infolion.platform.console.sys.context.UserContextHolder;
import com.infolion.platform.core.web.BaseMultiActionController;
import com.infolion.platform.util.CodeGenerator;
import com.infolion.platform.util.DateUtils;
import com.infolion.sapss.receiptLog.domain.TReceiptMoneyInfo;
import com.infolion.sapss.receiptLog.service.ReceiptMoneyService;
import com.infolion.sapss.receipts.domain.TReceiptInfo;

public class ReceiptMoneyController extends BaseMultiActionController{
	@Autowired
	private ReceiptMoneyService receiptMoneyService;
	public void setReceiptMoneyService(ReceiptMoneyService receiptMoneyService) {
		this.receiptMoneyService = receiptMoneyService;
	}

	/**
	 * 系统菜单列表链接
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView toManger(HttpServletRequest request, HttpServletResponse response){
		UserContext userContext = UserContextHolder.getLocalUserContext()
		.getUserContext();
        request.setAttribute("loginer", userContext.getSysUser().getUserId());
		String tradeType = request.getParameter("tradeType");
		request.setAttribute("tradeType", tradeType);
		return new ModelAndView("sapss/receiptLog/receiptMoney/moneyManager");
	}
	/**
	 * 弹出收汇详细页面
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView toInfo(HttpServletRequest request, HttpServletResponse response){
		String tradeType = request.getParameter("tradeType");
		request.setAttribute("tradeType", tradeType);
		String operType = request.getParameter("operType");
		if (operType==null||operType.length()<3) operType="101";

		request.setAttribute("save", operType.substring(0, 1).equals("0"));
		request.setAttribute("submit", operType.substring(1, 2).equals("0"));
		request.setAttribute("close", operType.substring(2, 3).equals("0"));
		return new ModelAndView("sapss/receiptLog/receiptMoney/moneyInfo");
	}
	/**
	 * 查询
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws ServletException 
	 */
	public void find(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String querySQL = this.receiptMoneyService.querySQL(request);
		String grid_column="info_id,contract_id,contract_no,receipt_date,EXAMINE_STATE,EXAMINE_STATE_D_EXAMINE_STATE,REAL_RECEIPT_DATE,RECEIPT_TOTAL,REAL_RECEIPT_TOTAL," +
				"RECEIPT_BALANCE,SALE_CONTRACT_STAUS,INNER_RECEIPT_TOTAL,INNER_RECEIPT_BALANCE,INNER_RECEIPT_DUTY,CREATOR," +
				"INNER_RECEIPT_FEE,CREATOR,CREATE_TIME";
		String grid_size=request.getParameter("limit");
		grid_size = grid_size==null?"10":grid_size;
		request.setAttribute("grid_sql", querySQL);
		request.setAttribute("grid_columns", grid_column);
		request.setAttribute("grid_size", grid_size);
		
		ExtComponentServlet servlet = new ExtComponentServlet();
		servlet.processGrid(request, response, true);
	}
	/**
	 * 保存
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	public void save(HttpServletRequest request, HttpServletResponse response,TReceiptMoneyInfo info) throws IOException{
		UserContext context = UserContextHolder.getLocalUserContext().getUserContext();
		if(StringUtils.isEmpty(info.getInfoId())){
			info.setInfoId(CodeGenerator.getUUID());
		}
		if("".equals(info.getExamineState())||info.getExamineState()==null){
			info.setExamineState("1");
		}
		if("".equals(info.getCreator())||info.getCreator()==null){
			info.setCreator(context.getSysUser().getUserId());
		}
		if("".equals(info.getDeptId())||info.getDeptId()==null){
			info.setDeptId(context.getSysDept().getDeptid());
		}
		if("".equals(info.getCreateTime())||info.getCreateTime()==null){
			info.setCreateTime(DateUtils.getCurrTime(2));
		}

		info.setIsAvailable("1");
		this.receiptMoneyService.saveOrUpdate(info);
		JSONObject jo = new JSONObject();
		jo.put("returnMessage", "保存成功");
		String jsontext = jo.toString();
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");

		this.operateSuccessfullyWithString(response, jsontext);
	}
	
	public void submitAndSaveInfo(HttpServletRequest request,
			HttpServletResponse response, TReceiptMoneyInfo info)
			throws IllegalAccessException, InvocationTargetException,
			IOException, Exception
	{
		String taskId = request.getParameter("workflowTaskId");
		receiptMoneyService.submitAndSaveInfo(taskId,
				info);
		JSONObject jo = new JSONObject();
		jo.put("returnMessage", "提交成功！");
		String jsontext = jo.toString();
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
	public void delete(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String id = request.getParameter("infoId");
		this.receiptMoneyService.delete(id);
		JSONObject js = new JSONObject();
		js.put("ok", "删除成功");
		this.operateSuccessfullyWithString(response, js.toString());
	}
	/**
	 * 修改
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView forModify(HttpServletRequest request, HttpServletResponse response){
		
		//操作类型
		String operType = request.getParameter("operType");
		if (operType==null||operType.length()<3) operType="101";

		request.setAttribute("save", operType.substring(0, 1).equals("0"));
		request.setAttribute("submit", operType.substring(1, 2).equals("0"));
		request.setAttribute("close", operType.substring(2, 3).equals("0"));
		
		String id = request.getParameter("infoId");
		TReceiptMoneyInfo info =  this.receiptMoneyService.find(id);
		request.setAttribute("receiptMoney", info);
		return new ModelAndView("sapss/receiptLog/receiptMoney/moneyInfo");
	}
	
	public ModelAndView receiptsExamine(HttpServletRequest request, HttpServletResponse response) throws IllegalAccessException, InvocationTargetException
	{
		String receiptId = request.getParameter("businessRecordId");
		String taskId = request.getParameter("taskId");
		request.setAttribute("businessRecordId", receiptId);
		request.setAttribute("taskId", taskId);
		
		request.setAttribute("businessRecordId", receiptId);
		TReceiptMoneyInfo info = receiptMoneyService.find(receiptId);
		
		UserContext userContext = UserContextHolder.getLocalUserContext()
				.getUserContext();
		String oType = "000";
		if (info.getCreator().equals(
				userContext.getSysUser().getUserId()))
		{
			oType = "100";
		}
		
		request.setAttribute("iframeSrc", "receiptMoneyController.spr?action=forModify&operType="+oType+"&infoId=" + receiptId);
		request.setAttribute("submitUrl", "receiptMoneyController.spr?action=submitWorkflow");


		return new ModelAndView("sapss/workflow/commonProcessPage");
	}
	
	public void submitWorkflow(HttpServletRequest request, HttpServletResponse response, TReceiptMoneyInfo info) throws IllegalAccessException, InvocationTargetException, IOException, Exception
	{
		String taskId = request.getParameter("workflowTaskId");

		info.setApprovedTime(DateUtils.getCurrTime(2));
		UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
		// 业务描述信息
		info.setWorkflowBusinessNote(userContext.getSysDept().getDeptname() + "|" + userContext.getSysUser().getRealName());

		String doWorkflow = request.getParameter("doWorkflow");
		if (doWorkflow == null && !"mainForm".equals(doWorkflow))
		{
			String workflowLeaveTransitionName = request.getParameter("workflowLeaveTransitionName");
			String workflowExamine = request.getParameter("workflowExamine");
			info.setWorkflowExamine(workflowExamine);
			info.setWorkflowLeaveTransitionName(workflowLeaveTransitionName);
		}
		info.setWorkflowModelName("收款审核");
		//取得工作流节点名称
		
		JSONObject jo = new JSONObject();
		jo = this.receiptMoneyService.submitWorkflow(info, taskId);
		
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");

		this.operateSuccessfullyWithString(response, jo.toString());
	}
	
}
