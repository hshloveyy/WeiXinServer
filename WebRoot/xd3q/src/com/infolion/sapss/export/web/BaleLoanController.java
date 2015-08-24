/*
 * @(#)ExportIncomeController.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：陈喜平
 *  时　间：2009-5-25
 *  描　述：创建
 */

package com.infolion.sapss.export.web;

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
import com.infolion.platform.console.sys.context.UserContext;
import com.infolion.platform.console.sys.context.UserContextHolder;
import com.infolion.platform.core.BusinessException;
import com.infolion.platform.core.web.BaseMultiActionController;
import com.infolion.platform.util.CodeGenerator;
import com.infolion.platform.util.DateUtils;
import com.infolion.sapss.credit.domain.TCreditInfo;
import com.infolion.sapss.credit.service.CreditEntryHibernateService;
import com.infolion.sapss.export.domain.TBaleLoan;
import com.infolion.sapss.export.service.BaleLoanService;

public class BaleLoanController extends BaseMultiActionController
{
	@Autowired
	private BaleLoanService baleLoanService;
	@Autowired
	private CreditEntryHibernateService creditEntryHibernateService;

	public void setBaleLoanService(BaleLoanService baleLoanService)
	{
		this.baleLoanService = baleLoanService;
	}

	public void setCreditEntryHibernateService(
			CreditEntryHibernateService creditEntryHibernateService)
	{
		this.creditEntryHibernateService = creditEntryHibernateService;
	}

	/**
	 * 系统菜单列表链接
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView toBaleLoanManger(HttpServletRequest request,
			HttpServletResponse response)
	{
		UserContext userContext = UserContextHolder.getLocalUserContext()
				.getUserContext();
		String loginer = userContext.getSysUser().getUserId();
		request.setAttribute("loginer", loginer);
		return new ModelAndView("sapss/export/baleLoan/baleLoanManager");
	}
	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView toCompositeQuery(HttpServletRequest request,
			HttpServletResponse response) {
		UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
		String loginer = userContext.getSysUser().getUserId();
		request.setAttribute("loginer", loginer);
		return new ModelAndView("sapss/export/baleLoan/compositeQuery");
	}
	
	/**
	 * 弹出打包贷款详细页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView toBaleLoanInfo(HttpServletRequest request,
			HttpServletResponse response)
	{
		return new ModelAndView("sapss/export/baleLoan/baleLoanInfo");
	}

	/**
	 * 查询
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	public void find(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{

		String grid_sql = this.baleLoanService.querySQL(request);
		String grid_column = "BALE_LOAN_ID,BALE_LOAN_NO,CREDIT_ID,CREDIT_NO,CREDIT_REC_DATE,CREATE_BANK,INVOICE,CONTRACT_NO,DEPT_NAME,"
				+ "APPLY_TIME,CREATOR,EXAMINE_STATE,EXAMINE_STATE_D_EXAMINE_STATE,CURRENCY,APPLY_MONEY,REAL_MONEY,BANK,APPROVED_TIME";
		String grid_size = request.getParameter("limit");
		grid_size = grid_size == null ? "10" : grid_size;
		request.setAttribute("grid_sql", grid_sql);
		request.setAttribute("grid_columns", grid_column);
		request.setAttribute("grid_size", grid_size);
		ExtComponentServlet servlet = new ExtComponentServlet();
		servlet.processGrid(request, response, true);
	}

	/**
	 * 保存
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void save(HttpServletRequest request, HttpServletResponse response,
			TBaleLoan info) throws IOException
	{
		UserContext context = UserContextHolder.getLocalUserContext()
				.getUserContext();
		if (StringUtils.isEmpty(info.getBaleLoanId()))
		{
			info.setBaleLoanId(CodeGenerator.getUUID());
			info.setExamineState("1");
			info.setIsAvailable("1");
			info.setBaleLoanNo(this.baleLoanService.getBaleLoanNo());
		}
		this.baleLoanService.save(info);
		JSONObject js = new JSONObject();
		js.put("ok", "保存成功");
		this.operateSuccessfullyWithString(response, js.toString());
	}

	/**
	 * 提交审批
	 * 
	 * @param request
	 * @param response
	 * @param info
	 * @throws IOException
	 */
	public void saveAndsubmit(HttpServletRequest request,
			HttpServletResponse response, TBaleLoan info) throws IOException
	{
		String taskId = request.getParameter("workflowTaskId");
		if (StringUtils.isEmpty(info.getBaleLoanId()))
		{
			info.setBaleLoanId(CodeGenerator.getUUID());
			info.setIsAvailable("1");	
			info.setBaleLoanNo(this.baleLoanService.getBaleLoanNo());			
		}
		info.setExamineState("2");
		info.setApplyTime(DateUtils.getCurrTime(2));
		this.baleLoanService.saveAndSubmit(taskId, info);
		JSONObject js = new JSONObject();
		js.put("ok", "提交成功");
		this.operateSuccessfullyWithString(response, js.toString());
	}

	/**
	 * 进入到流程审批界面
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ModelAndView examine(HttpServletRequest request,
			HttpServletResponse response) throws IOException
	{
		String id = request.getParameter("businessRecordId");
		String taskId = request.getParameter("taskId");
		TaskInstanceContext taskInstanceContext = WorkflowService.getTaskInstanceContext(taskId);
		String taskName = taskInstanceContext.getTaskName();
		String taskType = "";
		if (taskName.equals("资金部人员办理并填写实收金额及币别")){
			taskType = "1";
		}
		request.setAttribute("taskId", taskId);
		request.setAttribute("businessRecordId", id);
		request.setAttribute("taskName", taskName);
		TBaleLoan tBaleLoan = this.baleLoanService.find(id);
		UserContext userContext = UserContextHolder.getLocalUserContext()
				.getUserContext();
		Boolean modify = false;
		// 创建者能修改：
		if (tBaleLoan.getCreator().equals(
				userContext.getSysUser().getUserId()))
		{
			modify = true;
		}
		// 业务修改权限进入修改页面
		if (modify)
		{
			request.setAttribute("iframeSrc",
					"baleLoanController.spr?action=forModify&id="+ tBaleLoan.getBaleLoanId()+"&from=workflow");
		}
		else
		{
			request.setAttribute("iframeSrc",
					"baleLoanController.spr?action=forView&id=" + tBaleLoan.getBaleLoanId() + "&taskType=" + taskType);
		}
		String submitUrl = "baleLoanController.spr?action=submit";
		request.setAttribute("submitUrl", submitUrl);
		return new ModelAndView("sapss/workflow/commonProcessPage");

	}
	
	/**
	 * 流程审批提交动作
	 * @param request
	 * @param response
	 * @param tBaleLoan
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws IOException
	 * @throws Exception
	 */
	public void submit(HttpServletRequest request,
			HttpServletResponse response, TBaleLoan tBaleLoan)
			throws IllegalAccessException, InvocationTargetException,
			IOException, Exception
	{

		String taskId = request.getParameter("workflowTaskId");
		String rtn = this.baleLoanService.verifyFilds(taskId, tBaleLoan);
		if (!"".equals(rtn)){
			throw new BusinessException(rtn);
		}
		this.baleLoanService.submit(taskId, tBaleLoan);
		JSONObject jo = new JSONObject();
		jo.put("returnMessage", "提交成功！");
		String jsontext = jo.toString();
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		this.operateSuccessfullyWithString(response, jsontext);
	}
	
	
	/**
	 * 删除
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void delete(HttpServletRequest request, HttpServletResponse response)
			throws IOException
	{
		String id = request.getParameter("id");
		this.baleLoanService.delete(id);
		JSONObject js = new JSONObject();
		js.put("ok", "删除成功");
		this.operateSuccessfullyWithString(response, js.toString());
	}

	/**
	 * 修改
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView forModify(HttpServletRequest request,
			HttpServletResponse response)
	{
		String id = request.getParameter("id");
		TBaleLoan tBaleLoan = this.baleLoanService.find(id);
		request.setAttribute("tBaleLoan", tBaleLoan);
		TCreditInfo tCreditInfo = this.creditEntryHibernateService
				.getTCreditInfo(tBaleLoan.getCreditId());
		request.setAttribute("tCreditInfo", tCreditInfo);
		return new ModelAndView("sapss/export/baleLoan/baleLoanInfo");
	}
	
	/**
	 * 查看
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView forView(HttpServletRequest request,
			HttpServletResponse response)
	{
		String id = request.getParameter("id");
		String taskType = request.getParameter("taskType");
		request.setAttribute("taskType", taskType);
		TBaleLoan tBaleLoan = this.baleLoanService.find(id);
		request.setAttribute("tBaleLoan", tBaleLoan);
		TCreditInfo tCreditInfo = this.creditEntryHibernateService
				.getTCreditInfo(tBaleLoan.getCreditId());
		request.setAttribute("tCreditInfo", tCreditInfo);
		return new ModelAndView("sapss/export/baleLoan/baleLoanView");
	}	

	/**
	 * 查询信用证到证信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView selectCreditInfo(HttpServletRequest request,
			HttpServletResponse response)
	{

		return new ModelAndView("sapss/queryForm/findCredit");
	}

}
