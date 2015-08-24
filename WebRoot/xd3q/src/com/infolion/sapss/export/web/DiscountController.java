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
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
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
import com.infolion.sapss.credit.domain.TCreditInfo;
import com.infolion.sapss.credit.service.CreditEntryHibernateService;
import com.infolion.sapss.export.domain.TDiscount;
import com.infolion.sapss.export.domain.TDiscount;
import com.infolion.sapss.export.service.DiscountService;
import com.infolion.sapss.project.domain.TProjectInfo;
import com.infolion.sapss.project.service.ProjectHibernateService;

public class DiscountController extends BaseMultiActionController
{
	@Autowired
	private DiscountService discountService;
	@Autowired
	private ContractHibernateService contractHibernateService;
	@Autowired
	private ProjectHibernateService projectHibernateService;

	public void setDiscountService(DiscountService discountService)
	{
		this.discountService = discountService;
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
	 * 系统菜单列表链接
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView toDiscountManger(HttpServletRequest request,
			HttpServletResponse response)
	{
		UserContext userContext = UserContextHolder.getLocalUserContext()
				.getUserContext();
		String loginer = userContext.getSysUser().getUserId();
		request.setAttribute("loginer", loginer);
		return new ModelAndView("sapss/export/discount/discountManager");
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
		return new ModelAndView("sapss/export/discount/compositeQuery");
	}

	/**
	 * 弹出贴现详细页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView toBaleLoanInfo(HttpServletRequest request,
			HttpServletResponse response)
	{
		return new ModelAndView("sapss/export/discount/discountInfo");
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

		String grid_sql = this.discountService.querySQL(request);
		String grid_column = "DISCOUNT_ID,DISCOUNT_NO,CONTRACT_SALES_ID,PROJECT_NO,CONTRACT_NO,CONTRACT_NAME,DEPT_NAME,"
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
			TDiscount info) throws IOException
	{
		UserContext context = UserContextHolder.getLocalUserContext()
				.getUserContext();
		if (StringUtils.isEmpty(info.getDiscountId()))
		{
			info.setDiscountId(CodeGenerator.getUUID());
			info.setExamineState("1");
			info.setIsAvailable("1");
			info.setDiscountNo(this.discountService.getDiscountNo());
		}
		this.discountService.save(info);
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
			HttpServletResponse response, TDiscount info) throws IOException
	{
		String taskId = request.getParameter("workflowTaskId");
		if (StringUtils.isEmpty(info.getDiscountId()))
		{
			info.setDiscountId(CodeGenerator.getUUID());
			info.setIsAvailable("1");
			info.setDiscountNo(this.discountService.getDiscountNo());
		}
		info.setExamineState("2");
		info.setApplyTime(DateUtils.getCurrTime(2));
		this.discountService.saveAndSubmit(taskId, info);
		JSONObject js = new JSONObject();
		js.put("ok", "提交成功");
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
		if (taskName.equals("资金部人员办理并填写实收金额及币别"))
		{
			taskType = "1";
		}
		request.setAttribute("taskId", taskId);
		request.setAttribute("businessRecordId", id);
		request.setAttribute("taskName", taskName);
		TDiscount tDiscount = this.discountService.find(id);
		UserContext userContext = UserContextHolder.getLocalUserContext()
				.getUserContext();
		Boolean modify = false;
		// 创建者能修改：
		if (tDiscount.getCreator().equals(userContext.getSysUser().getUserId()))
		{
			modify = true;
		}
		// 业务修改权限进入修改页面
		if (modify)
		{
			request.setAttribute("iframeSrc",
					"discountController.spr?action=forModify&id="
							+ tDiscount.getDiscountId() + "&from=workflow");
		}
		else
		{
			request.setAttribute("iframeSrc",
					"discountController.spr?action=forView&id="
							+ tDiscount.getDiscountId() + "&taskType="
							+ taskType);
		}
		String submitUrl = "discountController.spr?action=submit";
		request.setAttribute("submitUrl", submitUrl);
		return new ModelAndView("sapss/workflow/commonProcessPage");

	}

	/**
	 * 流程审批提交动作
	 * 
	 * @param request
	 * @param response
	 * @param tDiscount
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws IOException
	 * @throws Exception
	 */
	public void submit(HttpServletRequest request,
			HttpServletResponse response, TDiscount tDiscount)
			throws IllegalAccessException, InvocationTargetException,
			IOException, Exception
	{

		String taskId = request.getParameter("workflowTaskId");
		String rtn = this.discountService.verifyFilds(taskId, tDiscount);
		if (!"".equals(rtn))
		{
			throw new BusinessException(rtn);
		}
		this.discountService.submit(taskId, tDiscount);
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
		this.discountService.delete(id);
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
		TDiscount tDiscount = this.discountService.find(id);
		request.setAttribute("tDiscount", tDiscount);
		TContractSalesInfo tContractSalesInfo = this.contractHibernateService
				.getContractSalesInfo(tDiscount.getContractSalesId());
		request.setAttribute("tContractSalesInfo", tContractSalesInfo);
		TContractGroup tContractGroup = this.contractHibernateService.getContractGroup(tContractSalesInfo.getContractGroupId());
		request.setAttribute("tContractGroup", tContractGroup);
		TProjectInfo tProjectInfo = this.projectHibernateService
				.getTProjectInfo(tContractSalesInfo.getProjectId());
		request.setAttribute("tProjectInfo", tProjectInfo);
		return new ModelAndView("sapss/export/discount/discountInfo");
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
		TDiscount tDiscount = this.discountService.find(id);
		request.setAttribute("tDiscount", tDiscount);
		TContractSalesInfo tContractSalesInfo = this.contractHibernateService
				.getContractSalesInfo(tDiscount.getContractSalesId());
		request.setAttribute("tContractSalesInfo", tContractSalesInfo);
		TContractGroup tContractGroup = this.contractHibernateService.getContractGroup(tContractSalesInfo.getContractGroupId());
		request.setAttribute("tContractGroup", tContractGroup);		
		TProjectInfo tProjectInfo = this.projectHibernateService
				.getTProjectInfo(tContractSalesInfo.getProjectId());
		request.setAttribute("tProjectInfo", tProjectInfo);
		return new ModelAndView("sapss/export/discount/discountView");
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
