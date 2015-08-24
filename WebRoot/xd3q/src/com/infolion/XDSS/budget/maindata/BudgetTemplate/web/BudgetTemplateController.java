/*
 * @(#)BudgetTemplateController.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年03月03日 17点15分23秒
 *  描　述：创建
 */
package com.infolion.XDSS.budget.maindata.BudgetTemplate.web;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

import com.infolion.XDSS.budget.maindata.BudgetClass.domain.BudgetClass;
import com.infolion.XDSS.budget.maindata.BudgetClass.service.BudgetClassService;
import com.infolion.XDSS.budget.maindata.BudgetSort.domain.BudgetSort;
import com.infolion.XDSS.budget.maindata.BudgetSort.service.BudgetSortService;
import com.infolion.XDSS.budget.maindata.BudgetTemplate.domain.BudgetTemplate;
import com.infolion.XDSS.budget.maindata.BudgetTemplateGen.web.BudgetTemplateControllerGen;
import com.infolion.XDSS.budget.maindata.BudgetTemplateItem.domain.BudgetTemplateItem;
import com.infolion.platform.bo.domain.BusinessObject;
import com.infolion.platform.dpframework.core.annotation.BDPController;
import com.infolion.platform.dpframework.core.cache.SysCachePoolUtils;
import com.infolion.platform.dpframework.util.ExBeanUtils;

/**
 * <pre>
 * 预算模版(BudgetTemplate)控制器类
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
public class BudgetTemplateController extends BudgetTemplateControllerGen
{
	@Autowired
	protected BudgetClassService budgetClassService;
	
	public void setBudgetSortService(BudgetClassService budgetClassService)
	{
		this.budgetClassService = budgetClassService;
	}
	
	/**
	 * 进入临时版本的界面
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView _tempGrid(HttpServletRequest request, HttpServletResponse response)
	{
		request.setAttribute("vt", getBusinessObject().getViewText());
		String strBudClassId = request.getParameter("budclassid");
		request.setAttribute("budclassid", strBudClassId);
		return new ModelAndView("XDSS/budget/maindata/BudgetTemplate/tempGrid");
	}
	
	/**
	 * 进入版本的界面
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView _versionGrid(HttpServletRequest request, HttpServletResponse response)
	{
		request.setAttribute("vt", getBusinessObject().getViewText());
		String strBudClassId = request.getParameter("budclassid");
		request.setAttribute("budclassid", strBudClassId);
		return new ModelAndView("XDSS/budget/maindata/BudgetTemplate/versionGrid");
	}
	
	/**
	 * 创建  
	 *   
	 * @param request
	 * @param response
	 */
	public ModelAndView _create(HttpServletRequest request, HttpServletResponse response)
	{
	    BudgetTemplate budgetTemplate = new BudgetTemplate();
	    String strBudClassId = request.getParameter("budclassid");
		request.setAttribute("vt", getBusinessObject().getViewText());
        if(null!=getBusinessObject().getSubBusinessObject())
        {
			// 取得子业务对象
			for (BusinessObject bo : getBusinessObject().getSubBusinessObject())
			{
				request.setAttribute(bo.getBeanAttribute().getBeanInstanceName()+"Vt", bo.getViewText());
			}
		}
        
        BudgetClass budgetClass = new BudgetClass();
        budgetClass = this.budgetClassService._get(strBudClassId);
		
        budgetTemplate.setBudgetClass(budgetClass);
		request.setAttribute("budgetTemplate",budgetTemplate);

	    return new ModelAndView("XDSS/budget/maindata/BudgetTemplate/budgetTemplateAdd");
	}
	
	/**
	 * 保存
	 * 
	 * @param request
	 * @param response
	 */
	public void _saveOrUpdate(HttpServletRequest request,
			HttpServletResponse response) {
		String strBudClassId = request.getParameter("BudgetTemplate.budgetClass.budclassid");

		JSONObject jo = new JSONObject();
		// 绑定主对象值
		BudgetTemplate budgetTemplate = (BudgetTemplate) ExBeanUtils
				.bindBusinessObjectData(request.getParameterMap(),
						BudgetTemplate.class, true, request.getMethod(), true);
		BudgetClass budgetClass = new BudgetClass();
		budgetClass.setBudclassid(strBudClassId);
		budgetTemplate.setBudgetClass(budgetClass);
		
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<BudgetTemplateItem> budgetTemplateItemmodifyItems = ExBeanUtils
				.bindModifySubBoData(request.getParameterMap(),
						new Object[] { budgetTemplate },
						BudgetTemplateItem.class, null);
		budgetTemplate.setBudgetTemplateItem(budgetTemplateItemmodifyItems);
		Set<BudgetTemplateItem> budgetTemplateItemdeleteItems = ExBeanUtils
				.bindDeleteSubBoData(request.getParameterMap(),
						new Object[] { budgetTemplate },
						BudgetTemplateItem.class, null);
		this.budgetTemplateService._saveOrUpdate(budgetTemplate,
				budgetTemplateItemdeleteItems, getBusinessObject());
		
		jo.put("budtemid", budgetTemplate.getBudtemid());
		String creator = budgetTemplate.getCreator();
		String creator_text = SysCachePoolUtils.getDictDataValue("YUSER",
				creator);
		jo.put("creator_text", creator_text);
		jo.put("creator", creator);
		jo.put("createtime", budgetTemplate.getCreatetime());
		String lastmodifyer = budgetTemplate.getLastmodifyer();
		String lastmodifyer_text = SysCachePoolUtils.getDictDataValue("YUSER",
				lastmodifyer);
		jo.put("lastmodifyer_text", lastmodifyer_text);
		jo.put("lastmodifyer", lastmodifyer);
		jo.put("lastmodifytime", budgetTemplate.getLastmodifytime());
		this.operateSuccessfullyWithString(response, jo.toString());
	}
}