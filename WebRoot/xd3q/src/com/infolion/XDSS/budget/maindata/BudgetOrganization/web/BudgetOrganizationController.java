/*
 * @(#)BudgetOrganizationController.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年03月05日 13点59分04秒
 *  描　述：创建
 */
package com.infolion.XDSS.budget.maindata.BudgetOrganization.web;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.web.servlet.ModelAndView;

import com.infolion.XDSS.budget.maindata.BudgetOrgTemp.domain.BudgetOrgTemp;
import com.infolion.XDSS.budget.maindata.BudgetOrganization.domain.BudgetOrganization;
import com.infolion.XDSS.budget.maindata.BudgetOrganizationGen.web.BudgetOrganizationControllerGen;
import com.infolion.platform.bo.domain.BusinessObject;
import com.infolion.platform.dpframework.core.annotation.BDPController;
import com.infolion.platform.dpframework.core.cache.SysCachePoolUtils;
import com.infolion.platform.dpframework.util.ExBeanUtils;

/**
 * <pre>
 * 预算组织(BudgetOrganization)控制器类
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
public class BudgetOrganizationController extends
		BudgetOrganizationControllerGen {
	/**
	 * 管理
	 * 
	 * @param request
	 * @param response
	 */
	public ModelAndView _manage(HttpServletRequest request,
			HttpServletResponse response) {
		BudgetOrganization budgetOrganization = new BudgetOrganization();
		request.setAttribute("vt", getBusinessObject().getViewText());
		if (null != getBusinessObject().getSubBusinessObject()) {
			// 取得子业务对象
			for (BusinessObject bo : getBusinessObject().getSubBusinessObject()) {
				request.setAttribute(bo.getBeanAttribute()
						.getBeanInstanceName()
						+ "Vt", bo.getViewText());
			}
		}
		request.setAttribute("budgetOrganization", budgetOrganization);

		return new ModelAndView(
				"XDSS/budget/maindata/BudgetOrganization/budgetOrganizationManage");
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
		BudgetOrganization budgetOrganization = (BudgetOrganization) ExBeanUtils
				.bindBusinessObjectData(request.getParameterMap(),
						BudgetOrganization.class, true, request.getMethod(),
						true);
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<BudgetOrgTemp> budgetOrgTempmodifyItems = ExBeanUtils
				.bindModifySubBoData(request.getParameterMap(),
						new Object[] { budgetOrganization },
						BudgetOrgTemp.class, null);
		budgetOrganization.setBudgetOrgTemp(budgetOrgTempmodifyItems);
		Set<BudgetOrgTemp> budgetOrgTempdeleteItems = ExBeanUtils
				.bindDeleteSubBoData(request.getParameterMap(),
						new Object[] { budgetOrganization },
						BudgetOrgTemp.class, null);
		this.budgetOrganizationService._saveOrUpdate(budgetOrganization,
				budgetOrgTempdeleteItems, getBusinessObject());
		jo.put("budorgid", budgetOrganization.getBudorgid());
		jo.put("buduporgid", budgetOrganization.getBuduporgid());
		jo.put("budorgname", budgetOrganization.getBudorgname());
		String creator = budgetOrganization.getCreator();
		String creator_text = SysCachePoolUtils.getDictDataValue("YUSER",
				creator);
		jo.put("creator_text", creator_text);
		jo.put("creator", creator);
		jo.put("createtime", budgetOrganization.getCreatetime());
		String lastmodifyer = budgetOrganization.getLastmodifyer();
		String lastmodifyer_text = SysCachePoolUtils.getDictDataValue("YUSER",
				lastmodifyer);
		jo.put("lastmodifyer_text", lastmodifyer_text);
		jo.put("lastmodifyer", lastmodifyer);
		jo.put("lastmodifytime", budgetOrganization.getLastmodifytime());
		this.operateSuccessfullyWithString(response, jo.toString());
	}
}