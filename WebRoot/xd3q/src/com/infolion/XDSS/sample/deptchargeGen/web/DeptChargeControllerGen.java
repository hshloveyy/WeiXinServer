/*
 * @(#)DeptChargeControllerGen.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年03月11日 09点45分48秒
 *  描　述：创建
 */
package com.infolion.XDSS.sample.deptchargeGen.web;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

import com.infolion.XDSS.budget.maindata.BudgetOrganization.domain.BudgetOrganization;
import com.infolion.XDSS.budget.maindata.BudgetOrganization.service.BudgetOrganizationService;
import com.infolion.XDSS.budget.sheet.service.BudgetSheetService;
import com.infolion.XDSS.sample.deptBudgetting.domain.DeptBudgetting;
import com.infolion.XDSS.sample.deptBudgetting.service.DeptBudgettingService;
import com.infolion.XDSS.sample.deptcharge.domain.DeptCharDetail;
import com.infolion.XDSS.sample.deptcharge.domain.DeptCharge;
import com.infolion.XDSS.sample.deptcharge.service.DeptChargeService;
import com.infolion.platform.basicApp.authManage.UserContextHolder;
import com.infolion.platform.bo.domain.BusinessObject;
import com.infolion.platform.dpframework.core.BusinessException;
import com.infolion.platform.dpframework.core.web.AbstractGenController;
import com.infolion.platform.dpframework.uicomponent.lock.LockService;
import com.infolion.platform.dpframework.util.EasyApplicationContextUtils;
import com.infolion.platform.dpframework.util.ExBeanUtils;
import com.infolion.platform.dpframework.util.StringUtils;
import com.infolion.platform.workflow.engine.WorkflowService;

/**
 * <pre>
 * 管理费用预算(DeptCharge)控制器类
 * </pre>
 * 
 * <br>
 * JDK版本:1.5
 * 
 * @author java业务平台代码生成工具
 * @version 1.0
 * @see The author for more details
 * @since 1.0
 */
public class DeptChargeControllerGen extends AbstractGenController
{
	private final String boName = "DeptCharge";

	public String getBoName()
	{
		return this.boName;
	}

	@Autowired
	protected DeptChargeService deptChargeService;

	public void setDeptChargeService(DeptChargeService deptChargeService)
	{
		this.deptChargeService = deptChargeService;
	}

	@Autowired
	protected BudgetSheetService budgetSheetService;

	public void setBudgetSheetService(BudgetSheetService budgetSheetService)
	{
		this.budgetSheetService = budgetSheetService;
	}

	/**
	 * 删除
	 * 
	 * @param request
	 * @param response
	 */
	public void _delete(HttpServletRequest request, HttpServletResponse response)
	{
		String deptchargeid = request.getParameter("deptchargeid");
		deptChargeService._delete(deptchargeid, getBusinessObject());
		this.operateSuccessfully(response);
	}

	/**
	 * 删除
	 * 
	 * @param request
	 * @param response
	 */
	public void _deletes(HttpServletRequest request, HttpServletResponse response)
	{
		String deptChargeIds = request.getParameter("deptChargeIds");
		deptChargeService._deletes(deptChargeIds, getBusinessObject());
		this.operateSuccessfully(response);
	}

	/**
	 * 查看
	 * 
	 * @param request
	 * @param response
	 */
	public ModelAndView _view(HttpServletRequest request, HttpServletResponse response)
	{
		DeptCharge deptCharge = new DeptCharge();
		String workflowTaskId = request.getParameter("workflowTaskId");
		String id = request.getParameter("deptchargeid");
		String businessId = request.getParameter("businessId");

		if (null == id)
			id = businessId;

		if (StringUtils.isNullBlank(businessId))
		{
			deptCharge = this.deptChargeService._get(id);
		}
		else
		{
			deptCharge = this.deptChargeService._get(id);
		}

		boolean haveBindWorkFlow = WorkflowService.getHaveBindWorkFlow("000000000203");
		JSONObject jsonObj = this.budgetSheetService.createFlexParaJsonObj(request, BudgetSheetService.APP_VIEW);
		jsonObj.element("budclassid", deptCharge.getBudclassid());
		jsonObj.element("boInstId", id);
		jsonObj.element("hasTree", false);
		jsonObj.element("submittable", haveBindWorkFlow && StringUtils.isNullBlank(deptCharge.getProcessstate()));
		jsonObj.element("hasProcess", false);
		jsonObj.element("workflowTaskId", workflowTaskId);

		request.setAttribute("flexPara", jsonObj.toString());
		return new ModelAndView("XDSS/sample/deptcharge/deptChargeView");
	}

	/**
	 * 提交
	 * 
	 * @param request
	 * @param response
	 */

	public void _submitProcess(HttpServletRequest request, HttpServletResponse response)
	{
		// 绑定主对象值
		DeptCharge deptCharge = (DeptCharge) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), DeptCharge.class, true, request.getMethod(), true);
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<DeptCharDetail> chargeDetailmodifyItems = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { deptCharge }, DeptCharDetail.class, null);
		Set<DeptCharDetail> deletedDeptCharDetailSet = ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(), new Object[] { deptCharge }, DeptCharDetail.class, null);
		deptCharge.setChargeDetail(chargeDetailmodifyItems);
		this.deptChargeService._submitProcess(deptCharge, deletedDeptCharDetailSet, getBusinessObject());
		this.operateSuccessfully(response);
	}

	/**
	 * 管理
	 * 
	 * @param request
	 * @param response
	 */
	public ModelAndView _manage(HttpServletRequest request, HttpServletResponse response)
	{
		request.setAttribute("vt", getBusinessObject().getViewText());
		return new ModelAndView("XDSS/sample/deptcharge/deptChargeManage");
	}

	/**
	 * 取消后的解锁
	 * 
	 * @param request
	 * @param response
	 */
	public void _cancel(HttpServletRequest request, HttpServletResponse response)
	{
		// 绑定主对象值
		DeptCharge deptCharge = new DeptCharge();
		String deptchargeid = request.getParameter("deptchargeid");
		deptCharge.setDeptchargeid(deptchargeid);
		LockService.unLockBOData(deptCharge);
		this.operateSuccessfullyHiddenInfo(response);
	}

	/**
	 * 分配
	 * 
	 * @param request
	 * @param response
	 */
	public void _assign(HttpServletRequest request, HttpServletResponse response)
	{

	}

	/**
	 * 保存
	 * 
	 * @param request
	 * @param response
	 */
	public void _saveOrUpdate(HttpServletRequest request, HttpServletResponse response)
	{
		JSONObject jo = new JSONObject();
		// 绑定主对象值
		DeptCharge deptCharge = (DeptCharge) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), DeptCharge.class, true, request.getMethod(), true);
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<DeptCharDetail> chargeDetailmodifyItems = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { deptCharge }, DeptCharDetail.class, null);
		deptCharge.setChargeDetail(chargeDetailmodifyItems);
		Set<DeptCharDetail> chargeDetaildeleteItems = ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(), new Object[] { deptCharge }, DeptCharDetail.class, null);
		this.deptChargeService._saveOrUpdate(deptCharge, chargeDetaildeleteItems, getBusinessObject());
		jo.put("deptchargeid", deptCharge.getDeptchargeid());
		this.operateSuccessfullyWithString(response, jo.toString());
	}

	/**
	 * 查询
	 * 
	 * @param request
	 * @param response
	 */
	public void _query(HttpServletRequest request, HttpServletResponse response)
	{

	}

	/**
	 * 附加空行
	 * 
	 * @param request
	 * @param response
	 */
	public void _appendLine(HttpServletRequest request, HttpServletResponse response)
	{

	}

	/**
	 * 插入行
	 * 
	 * @param request
	 * @param response
	 */
	public void _insertLine(HttpServletRequest request, HttpServletResponse response)
	{

	}

	/**
	 * 增加同级节点
	 * 
	 * @param request
	 * @param response
	 */
	public void _addNode(HttpServletRequest request, HttpServletResponse response)
	{

	}

	/**
	 * 增加下级节点
	 * 
	 * @param request
	 * @param response
	 */
	public void _addSubNode(HttpServletRequest request, HttpServletResponse response)
	{

	}

	/**
	 * 删除节点
	 * 
	 * @param request
	 * @param response
	 */
	public void _deleteNode(HttpServletRequest request, HttpServletResponse response)
	{

	}

	/**
	 * 图表查询
	 * 
	 * @param request
	 * @param response
	 */
	public void _queryChart(HttpServletRequest request, HttpServletResponse response)
	{

	}

	/**
	 * 图表明细查询
	 * 
	 * @param request
	 * @param response
	 */
	public void _queryChartDetail(HttpServletRequest request, HttpServletResponse response)
	{

	}

	/**
	 * 下载
	 * 
	 * @param request
	 * @param response
	 */
	public void _download(HttpServletRequest request, HttpServletResponse response)
	{

	}

	/**
	 * 编辑
	 * 
	 * @param request
	 * @param response
	 */
	public ModelAndView _edit(HttpServletRequest request, HttpServletResponse response)
	{
		DeptCharge deptCharge = new DeptCharge();
		String id = request.getParameter("deptchargeid");
		String businessId = request.getParameter("businessId");
		if (null == id)
			id = businessId;
		deptCharge = this.deptChargeService._getForEdit(id);
		JSONObject jsonObj = this.budgetSheetService.createFlexParaJsonObj(request, BudgetSheetService.APP_EDIT);
		jsonObj.element("budclassid", deptCharge.getBudclassid());
		jsonObj.element("boInstId", id);
		jsonObj.element("hasTree", false);
		jsonObj.element("hasProcess", false);
		// String flexPara =
		// this.budgetSheetService.createFlexPara(request.getContextPath(),
		// BudgetSheetService.APP_EDIT, id, deptCharge.getBudclassid(),
		// request.getContextPath() + "/messagebroker/amf",
		// "budgetSheetService", false, false, null);
		request.setAttribute("flexPara", jsonObj.toString());
		return new ModelAndView("XDSS/sample/deptcharge/deptChargeEdit");
	}

	/**
	 * 创建
	 * 
	 * @param request
	 * @param response
	 */
	public ModelAndView _create(HttpServletRequest request, HttpServletResponse response)
	{
		DeptCharge deptCharge = new DeptCharge();
		request.setAttribute("vt", getBusinessObject().getViewText());
		if (null != getBusinessObject().getSubBusinessObject())
		{
			// 取得子业务对象
			for (BusinessObject bo : getBusinessObject().getSubBusinessObject())
			{
				request.setAttribute(bo.getBeanAttribute().getBeanInstanceName() + "Vt", bo.getViewText());
			}
		}
		request.setAttribute("deptCharge", deptCharge);

		return new ModelAndView("XDSS/sample/deptcharge/deptChargeAdd");
	}

	/**
	 * 复制创建
	 * 
	 * @param request
	 * @param response
	 */
	public ModelAndView _copyCreate(HttpServletRequest request, HttpServletResponse response)
	{
		String id = request.getParameter("deptchargeid");
		DeptCharge deptCharge = this.deptChargeService._getEntityCopy(id);
		request.setAttribute("isCreateCopy", "true");
		request.setAttribute("deptCharge", deptCharge);
		request.setAttribute("vt", getBusinessObject().getViewText());
		if (null != getBusinessObject().getSubBusinessObject())
		{
			// 取得子业务对象
			for (BusinessObject bo : getBusinessObject().getSubBusinessObject())
			{
				request.setAttribute(bo.getBeanAttribute().getBeanInstanceName() + "Vt", bo.getViewText());
			}
		}
		return new ModelAndView("XDSS/sample/deptcharge/deptChargeAdd");
	}

	/**
	 * 汇总
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView _summary(HttpServletRequest request, HttpServletResponse response)
	{
		DeptCharge deptCharge = new DeptCharge();
		String workflowTaskId = request.getParameter("workflowTaskId");
		String id = request.getParameter("deptchargeid");
		String businessId = request.getParameter("businessId");

		if (null == id)
			id = businessId;

		if (StringUtils.isNullBlank(businessId))
		{
			deptCharge = this.deptChargeService._get(id);
		}
		else
		{
			deptCharge = this.deptChargeService._get(id);
		}

		boolean haveBindWorkFlow = WorkflowService.getHaveBindWorkFlow("000000000203");

		JSONObject jsonObj = this.budgetSheetService.createFlexParaJsonObj(request, BudgetSheetService.APP_VIEW);
		jsonObj.element("budclassid", deptCharge.getBudclassid());
		jsonObj.element("boInstId", id);
		jsonObj.element("hasTree", true);
		jsonObj.element("hasProcess", haveBindWorkFlow);
		jsonObj.element("submittable", haveBindWorkFlow);
		jsonObj.element("workflowTaskId", workflowTaskId);
		// 取得分配给当前用户的所有预算组织根节点
		jsonObj.element("budorgidList", JSONArray.fromObject(budgetSheetService.getUserBudOrgIds(UserContextHolder.getLocalUserContext().getUserContext().getUser().getUserId())));
		request.setAttribute("flexPara", jsonObj.toString());
		return new ModelAndView("XDSS/sample/deptcharge/deptChargeView");

	}

	/**
	 * 编制
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView _formulate(HttpServletRequest request, HttpServletResponse response)
	{
		String workflowTaskId = request.getParameter("workflowTaskId");
		String id = request.getParameter("deptchargeid");
		String businessId = request.getParameter("businessId");
		String taskName = request.getParameter("taskName");

		// ///////////////DeptBudgetting流程特殊逻辑
		DeptBudgettingService deptBudgettingService = (DeptBudgettingService) EasyApplicationContextUtils.getBeanByName("deptBudgettingService");
		DeptBudgetting deptBudgetting = deptBudgettingService._get(businessId);
		// 将任务节点的任务名作为关联的预算组织名称
		String budOrgName = taskName;
		String rootBudOrgId = deptBudgetting.getBudorgid();
		BudgetOrganizationService budgetOrganizationService = (BudgetOrganizationService) EasyApplicationContextUtils.getBeanByName("budgetOrganizationService");
		// 找到该节点关联的预算组织
		BudgetOrganization budgetOrganization = budgetOrganizationService.getSubOrgByName(rootBudOrgId, budOrgName);
		if (budgetOrganization == null)
		{
			BudgetOrganization budgetOrganization2 = budgetOrganizationService._get(rootBudOrgId);
			if (budgetOrganization2 == null)
			{
				throw new BusinessException("找不到ID为[" + rootBudOrgId + "]的预算组织");
			}
			else
			{
				throw new BusinessException("预算组织[" + budgetOrganization2.getBudorgname() + "]下找不到名称为[" + budOrgName + "]的预算组织！");
			}
		}

		String budorgid = budgetOrganization.getBudorgid();
		DeptCharge tempDeptCharge = this.deptChargeService.get(budorgid, deptBudgetting.getBudclassid(), deptBudgetting.getVersion(), deptBudgetting.getAyear());
		if (tempDeptCharge == null)
		{
			throw new BusinessException("找不到与预算组织[" + budgetOrganization.getBudorgname() + "]关联的记录！");
		}
		id = tempDeptCharge.getDeptchargeid();
		// ///////////////

		DeptCharge deptCharge = new DeptCharge();

		if (null == id)
			id = businessId;

		if (StringUtils.isNullBlank(businessId))
		{
			deptCharge = this.deptChargeService._get(id);
		}
		else
		{
			deptCharge = this.deptChargeService._get(id);
		}

		boolean haveBindWorkFlow = WorkflowService.getHaveBindWorkFlow("000000000203");

		JSONObject jsonObj = this.budgetSheetService.createFlexParaJsonObj(request, BudgetSheetService.APP_EDIT);
		jsonObj.element("budclassid", deptCharge.getBudclassid());
		jsonObj.element("boInstId", id);
		jsonObj.element("hasTree", false);
		jsonObj.element("hasProcess", haveBindWorkFlow);
		jsonObj.element("submittable", haveBindWorkFlow);
		jsonObj.element("workflowTaskId", workflowTaskId);
		request.setAttribute("flexPara", jsonObj.toString());
		return new ModelAndView("XDSS/sample/deptcharge/deptChargeView");

	}

}