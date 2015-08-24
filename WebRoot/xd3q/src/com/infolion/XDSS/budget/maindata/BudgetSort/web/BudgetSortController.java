/*
 * @(#)BudgetSortController.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年03月03日 14点48分32秒
 *  描　述：创建
 */
package com.infolion.XDSS.budget.maindata.BudgetSort.web;

import java.io.IOException;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.infolion.XDSS.budget.maindata.BudgetSort.domain.BudgetSort;
import com.infolion.XDSS.budget.maindata.BudgetSortGen.web.BudgetSortControllerGen;
import com.infolion.XDSS.budget.maindata.BudgetTemplate.domain.BudgetTemplate;
import com.infolion.platform.dpframework.core.annotation.BDPController;
import com.infolion.platform.dpframework.core.cache.SysCachePoolUtils;
import com.infolion.platform.dpframework.util.ExBeanUtils;

/**
 * <pre>
 * 预算分类(BudgetSort)控制器类
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
public class BudgetSortController extends BudgetSortControllerGen {
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
		BudgetSort budgetSort = (BudgetSort) ExBeanUtils
				.bindBusinessObjectData(request.getParameterMap(),
						BudgetSort.class, true, request.getMethod(), true);
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<BudgetTemplate> budgetTemplatemodifyItems = ExBeanUtils
				.bindModifySubBoData(request.getParameterMap(),
						new Object[] { budgetSort }, BudgetTemplate.class, null);
		budgetSort.setBudgetTemplate(budgetTemplatemodifyItems);
		Set<BudgetTemplate> budgetTemplatedeleteItems = ExBeanUtils
				.bindDeleteSubBoData(request.getParameterMap(),
						new Object[] { budgetSort }, BudgetTemplate.class, null);
		this.budgetSortService._saveOrUpdate(budgetSort,
				budgetTemplatedeleteItems, getBusinessObject());
		jo.put("budsortid", budgetSort.getBudsortid());
		jo.put("budupsortid", budgetSort.getBudupsortid());
		jo.put("budsortname", budgetSort.getBudsortname());
		String creator = budgetSort.getCreator();
		String creator_text = SysCachePoolUtils.getDictDataValue("YUSER",
				creator);
		jo.put("creator_text", creator_text);
		jo.put("creator", creator);
		jo.put("createtime", budgetSort.getCreatetime());
		String lastmodifyer = budgetSort.getLastmodifyer();
		String lastmodifyer_text = SysCachePoolUtils.getDictDataValue("YUSER",
				lastmodifyer);
		jo.put("lastmodifyer_text", lastmodifyer_text);
		jo.put("lastmodifyer", lastmodifyer);
		jo.put("lastmodifytime", budgetSort.getLastmodifytime());
		this.operateSuccessfullyWithString(response, jo.toString());
	}
}