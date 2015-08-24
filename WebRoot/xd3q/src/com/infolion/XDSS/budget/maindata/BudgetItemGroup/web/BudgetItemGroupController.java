/*
 * @(#)BudgetItemGroupController.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年03月05日 17点03分34秒
 *  描　述：创建
 */
package com.infolion.XDSS.budget.maindata.BudgetItemGroup.web;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.web.servlet.ModelAndView;

import com.infolion.XDSS.budget.maindata.BudgetItem.domain.BudgetItem;
import com.infolion.XDSS.budget.maindata.BudgetItemGroup.domain.BudgetItemGroup;
import com.infolion.XDSS.budget.maindata.BudgetItemGroupGen.web.BudgetItemGroupControllerGen;
import com.infolion.platform.bo.domain.BusinessObject;
import com.infolion.platform.dpframework.core.annotation.BDPController;
import com.infolion.platform.dpframework.core.cache.SysCachePoolUtils;
import com.infolion.platform.dpframework.util.ExBeanUtils;

/**
 * <pre>
 * 预算项分组(BudgetItemGroup)控制器类
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
public class BudgetItemGroupController extends BudgetItemGroupControllerGen {
	/**
	 * 管理
	 * 
	 * @param request
	 * @param response
	 */
	public ModelAndView _manage(HttpServletRequest request,
			HttpServletResponse response) {
		BudgetItemGroup budgetItemGroup = new BudgetItemGroup();
		request.setAttribute("vt", getBusinessObject().getViewText());
		if (null != getBusinessObject().getSubBusinessObject()) {
			// 取得子业务对象
			for (BusinessObject bo : getBusinessObject().getSubBusinessObject()) {
				request.setAttribute(bo.getBeanAttribute()
						.getBeanInstanceName()
						+ "Vt", bo.getViewText());
			}
		}
		request.setAttribute("budgetItemGroup", budgetItemGroup);

		return new ModelAndView(
				"XDSS/budget/maindata/BudgetItemGroup/budgetItemGroupManage");
	}

	/**
	 * 保存
	 * 
	 * @param request
	 * @param response
	 */
	public void _saveOrUpdate(HttpServletRequest request,
			HttpServletResponse response) {
		JSONObject jo = new JSONObject();
		// 绑定主对象值
		BudgetItemGroup budgetItemGroup = (BudgetItemGroup) ExBeanUtils
				.bindBusinessObjectData(request.getParameterMap(),
						BudgetItemGroup.class, true, request.getMethod(), true);
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<BudgetItem> budgetItemmodifyItems = ExBeanUtils
				.bindModifySubBoData(request.getParameterMap(),
						new Object[] { budgetItemGroup }, BudgetItem.class,
						null);
		budgetItemGroup.setBudgetItem(budgetItemmodifyItems);
		Set<BudgetItem> budgetItemdeleteItems = ExBeanUtils
				.bindDeleteSubBoData(request.getParameterMap(),
						new Object[] { budgetItemGroup }, BudgetItem.class,
						null);
		this.budgetItemGroupService._saveOrUpdate(budgetItemGroup,
				budgetItemdeleteItems, getBusinessObject());
		jo.put("budgroupid", budgetItemGroup.getBudgroupid());
		jo.put("budupgroupid", budgetItemGroup.getBudupgroupid());
		jo.put("budgroupname", budgetItemGroup.getBudgroupname());
		String creator = budgetItemGroup.getCreator();
		String creator_text = SysCachePoolUtils.getDictDataValue("YUSER",
				creator);
		jo.put("creator_text", creator_text);
		jo.put("creator", creator);
		jo.put("createtime", budgetItemGroup.getCreatetime());
		String lastmodifyer = budgetItemGroup.getLastmodifyer();
		String lastmodifyer_text = SysCachePoolUtils.getDictDataValue("YUSER",
				lastmodifyer);
		jo.put("lastmodifyer_text", lastmodifyer_text);
		jo.put("lastmodifyer", lastmodifyer);
		jo.put("lastmodifytime", budgetItemGroup.getLastmodifytime());
		this.operateSuccessfullyWithString(response, jo.toString());
	}
}