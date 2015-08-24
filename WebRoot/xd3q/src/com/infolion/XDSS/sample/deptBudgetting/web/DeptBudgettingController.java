/*
 * @(#)DeptBudgettingController.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年04月08日 16点03分14秒
 *  描　述：创建
 */
package com.infolion.XDSS.sample.deptBudgetting.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

import com.infolion.XDSS.budget.sheet.service.BudgetSheetService;
import com.infolion.XDSS.sample.deptBudgetting.domain.DeptBudgetting;
import com.infolion.XDSS.sample.deptBudgettingGen.web.DeptBudgettingControllerGen;
import com.infolion.platform.basicApp.authManage.UserContextHolder;
import com.infolion.platform.dpframework.core.BusinessException;
import com.infolion.platform.dpframework.core.annotation.BDPController;
import com.infolion.platform.dpframework.util.StringUtils;

/**
 * <pre>
 * 部门预算编制(DeptBudgetting)控制器类
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
@BDPController(parent = "baseMultiActionController")
public class DeptBudgettingController extends DeptBudgettingControllerGen
{
	@Autowired
	protected BudgetSheetService budgetSheetService;

	/**
	 * 汇总
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView _summary(HttpServletRequest request, HttpServletResponse response)
	{
		String workflowTaskId = request.getParameter("workflowTaskId");
		String businessId = request.getParameter("businessId");
		DeptBudgetting deptBudgetting = this.deptBudgettingService._get(businessId);
		JSONObject jsonObj = this.budgetSheetService.createFlexParaJsonObj(request, BudgetSheetService.APP_VIEW);
		jsonObj.element("budclassid", deptBudgetting.getBudclassid());
		jsonObj.element("assistantId", businessId);
		jsonObj.element("hasTree", true);
		jsonObj.element("hasProcess", true);
		jsonObj.element("submittable", true);
		jsonObj.element("workflowTaskId", workflowTaskId);
		// 取得分配给当前用户的所有预算组织根节点
		jsonObj.element("budorgidList", JSONArray.fromObject(budgetSheetService.getUserBudOrgIds(UserContextHolder.getLocalUserContext().getUserContext().getUser().getUserId())));
		request.setAttribute("flexPara", jsonObj.toString());
		return new ModelAndView("XDSS/sample/deptcharge/deptChargeView");

	}

	/**
	 * 汇总查看
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView _viewSummary(HttpServletRequest request, HttpServletResponse response)
	{
		String id = request.getParameter("deptbudgettingid");
		String businessId = request.getParameter("businessId");

		if (null == id)
			id = businessId;
		DeptBudgetting deptBudgetting = this.deptBudgettingService._get(id);
		if (deptBudgetting == null)
		{
			throw new BusinessException("找不到ID为[" + id + "]的" + this.getBoName() + "记录");
		}
		// 取得分配给当前用户的所有预算组织根节点
		// List<String> budorgidList =
		// budgetSheetService.getUserBudOrgIds(UserContextHolder.getLocalUserContext().getUserContext().getUser().getUserId());
		// if (budorgidList == null || budorgidList.size() == 0)
		// {
		// throw new BusinessException("该用户没有分配任何预算组织");
		// }
		List<String> budorgidList = new ArrayList<String>();
		budorgidList.add(deptBudgetting.getBudorgid());

		JSONObject jsonObj = this.budgetSheetService.createFlexParaJsonObj(request, BudgetSheetService.APP_VIEW);
		jsonObj.element("budclassid", deptBudgetting.getBudclassid());
		jsonObj.element("assistantId", id);
		jsonObj.element("hasTree", true);
		jsonObj.element("hasProcess", false);
		jsonObj.element("submittable", false);
		jsonObj.element("budorgidList", JSONArray.fromObject(budorgidList));
		request.setAttribute("flexPara", jsonObj.toString());
		return new ModelAndView("XDSS/sample/deptcharge/deptChargeView");
	}

}