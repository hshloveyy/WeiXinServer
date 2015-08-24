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
import com.infolion.sapss.contract.domain.TContractGroup;
import com.infolion.sapss.contract.domain.TContractSalesInfo;
import com.infolion.sapss.contract.service.ContractHibernateService;
import com.infolion.sapss.export.domain.TNegotiation;
import com.infolion.sapss.export.service.NegotiationService;
import com.infolion.sapss.project.domain.TProjectInfo;
import com.infolion.sapss.project.service.ProjectHibernateService;

public class NegotiationController extends BaseMultiActionController
{
	@Autowired
	private NegotiationService negotiationService;
	@Autowired
	private ContractHibernateService contractHibernateService;
	@Autowired
	private ProjectHibernateService projectHibernateService;

	public void setNegotiationService(NegotiationService negotiationService)
	{
		this.negotiationService = negotiationService;
	}

	public void setContractHibernateService(
			ContractHibernateService contractHibernateService)
	{
		this.contractHibernateService = contractHibernateService;
	}

	public void setProjectHibernateService(
			ProjectHibernateService projectHibernateService)
	{
		this.projectHibernateService = projectHibernateService;
	}

	/**
	 * 系统菜单列表链接
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView toNegotiationManger(HttpServletRequest request,
			HttpServletResponse response)
	{
		UserContext userContext = UserContextHolder.getLocalUserContext()
				.getUserContext();
		String loginer = userContext.getSysUser().getUserId();
		request.setAttribute("loginer", loginer);
		return new ModelAndView("sapss/export/negotiation/negotiationManager");
	}
	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView toCompositeQuery(HttpServletRequest request,
			HttpServletResponse response)
	{
		UserContext userContext = UserContextHolder.getLocalUserContext()
				.getUserContext();
		String loginer = userContext.getSysUser().getUserId();
		request.setAttribute("loginer", loginer);
		return new ModelAndView("sapss/export/negotiation/compositeQuery");
	}

	/**
	 * 弹出议付详细页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView toNegotiationInfo(HttpServletRequest request,
			HttpServletResponse response)
	{
		TNegotiation t = new TNegotiation();
		t.setApplyTime(DateUtils.getCurrTime(2));
		request.setAttribute("tNegotiation", t);
		return new ModelAndView("sapss/export/negotiation/negotiationInfo");
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

		String grid_sql = this.negotiationService.querySQL(request);
		String grid_column = "NEGOTIATION_ID,NEGOTIATION_NO,CONTRACT_SALES_ID,PROJECT_NO,CONTRACT_NO,CONTRACT_NAME,DEPT_NAME,"
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
			TNegotiation info) throws IOException
	{
		UserContext context = UserContextHolder.getLocalUserContext()
				.getUserContext();
		if (StringUtils.isEmpty(info.getNegotiationId()))
		{
			info.setNegotiationId(CodeGenerator.getUUID());
			info.setExamineState("1");
			info.setIsAvailable("1");
			info.setNegotiationNo(this.negotiationService.getNegotiationNo());
		}
		this.negotiationService.save(info);
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
			HttpServletResponse response, TNegotiation info) throws IOException
	{
		String msg="提交成功";
		String taskId = request.getParameter("workflowTaskId");
		if (StringUtils.isEmpty(info.getNegotiationId()))
		{
			info.setNegotiationId(CodeGenerator.getUUID());
			info.setIsAvailable("1");
			info.setNegotiationNo(this.negotiationService.getNegotiationNo());
		}
		info.setExamineState("2");
		info.setApplyTime(DateUtils.getCurrTime(2));
		this.negotiationService.saveAndSubmit(taskId, info);
		JSONObject js = new JSONObject();
		js.put("ok", msg);
		this.operateSuccessfullyWithString(response, js.toString());
	}

	/**
	 * 进入到流程审批界面
	 * 
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
		TaskInstanceContext taskInstanceContext = WorkflowService
				.getTaskInstanceContext(taskId);
		String taskName = taskInstanceContext.getTaskName();
		String taskType = "";
		if (taskName.indexOf("填写实收金额及币别")!=-1)
		{
			taskType = "1";
		}
		request.setAttribute("taskId", taskId);
		request.setAttribute("businessRecordId", id);
		request.setAttribute("taskName", taskName);
		TNegotiation tNegotiation = this.negotiationService.find(id);
		UserContext userContext = UserContextHolder.getLocalUserContext()
				.getUserContext();
		Boolean modify = false;
		// 创建者能修改：
		if (tNegotiation.getCreator().equals(userContext.getSysUser().getUserId()))
		{
			modify = true;
		}
		// 业务修改权限进入修改页面
		if (modify)
		{
			request.setAttribute("iframeSrc",
					"negotiationController.spr?action=forModify&id="
							+ tNegotiation.getNegotiationId() + "&from=workflow");
		}
		else
		{
			request.setAttribute("iframeSrc",
					"negotiationController.spr?action=forView&id="
							+ tNegotiation.getNegotiationId() + "&taskType="
							+ taskType);
		}
		String submitUrl = "negotiationController.spr?action=submit";
		request.setAttribute("submitUrl", submitUrl);
		return new ModelAndView("sapss/workflow/commonProcessPage");

	}

	/**
	 * 流程审批提交动作
	 * 
	 * @param request
	 * @param response
	 * @param tNegotiation
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws IOException
	 * @throws Exception
	 */
	public void submit(HttpServletRequest request,
			HttpServletResponse response, TNegotiation tNegotiation)
			throws IllegalAccessException, InvocationTargetException,
			IOException, Exception
	{

		String taskId = request.getParameter("workflowTaskId");
		String rtn = this.negotiationService.verifyFilds(taskId, tNegotiation);
		if (!"".equals(rtn))
		{
			throw new BusinessException(rtn);
		}
		this.negotiationService.submit(taskId, tNegotiation);
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
		this.negotiationService.delete(id);
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
		TNegotiation tNegotiation = this.negotiationService.find(id);
		request.setAttribute("tNegotiation", tNegotiation);
		TContractSalesInfo tContractSalesInfo = this.contractHibernateService
				.getContractSalesInfo(tNegotiation.getContractSalesId());
		request.setAttribute("tContractSalesInfo", tContractSalesInfo);
		TContractGroup tContractGroup = this.contractHibernateService.getContractGroup(tContractSalesInfo.getContractGroupId());
		request.setAttribute("tContractGroup", tContractGroup);		
		TProjectInfo tProjectInfo = this.projectHibernateService
				.getTProjectInfo(tContractSalesInfo.getProjectId());
		request.setAttribute("tProjectInfo", tProjectInfo);
		return new ModelAndView("sapss/export/negotiation/negotiationInfo");
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
		TNegotiation tNegotiation = this.negotiationService.find(id);
		request.setAttribute("tNegotiation", tNegotiation);
		TContractSalesInfo tContractSalesInfo = this.contractHibernateService
				.getContractSalesInfo(tNegotiation.getContractSalesId());
		request.setAttribute("tContractSalesInfo", tContractSalesInfo);
		TContractGroup tContractGroup = this.contractHibernateService.getContractGroup(tContractSalesInfo.getContractGroupId());
		request.setAttribute("tContractGroup", tContractGroup);		
		TProjectInfo tProjectInfo = this.projectHibernateService
				.getTProjectInfo(tContractSalesInfo.getProjectId());
		request.setAttribute("tProjectInfo", tProjectInfo);
		return new ModelAndView("sapss/export/negotiation/negotiationView");
	}

	/**
	 * 查询销售合同信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView selectSalesInfo(HttpServletRequest request,
			HttpServletResponse response)
	{

		return new ModelAndView("sapss/export/exportIncome/selectSalesInfo");
	}

}
