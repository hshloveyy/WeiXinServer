/*
 * @(#)BudgetClassController.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年03月11日 10点41分29秒
 *  描　述：创建
 */
package com.infolion.XDSS.budget.maindata.BudgetClass.web;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

import net.sf.json.JSONObject;

import com.infolion.XDSS.budget.maindata.BudgetClass.domain.BudgetClass;
import com.infolion.XDSS.budget.maindata.BudgetClass.service.BudgetClassService;
import com.infolion.XDSS.budget.maindata.BudgetClassGen.web.BudgetClassControllerGen;
import com.infolion.XDSS.budget.maindata.BudgetSort.domain.BudgetSort;
import com.infolion.XDSS.budget.maindata.BudgetTemplate.domain.BudgetTemplate;
import com.infolion.XDSS.budget.maindata.BudgetTemplateItem.domain.BudgetTemplateItem;
import com.infolion.platform.dpframework.core.annotation.BDPController;
import com.infolion.platform.dpframework.core.cache.SysCachePoolUtils;
import com.infolion.platform.dpframework.util.ExBeanUtils;

/**
 * <pre>
 * 预算分类(BudgetClass)控制器类
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
public class BudgetClassController extends BudgetClassControllerGen {
	
	/**
	 * 删除  
	 *   
	 * @param request
	 * @param response
	 */
	public void _delete(HttpServletRequest request, HttpServletResponse response)
	{
		String budclassid = request.getParameter("budclassid");
		budgetClassService.deleteNodeAndChildNode(budclassid,getBusinessObject());
		this.operateSuccessfully(response);
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
		BudgetClass budgetClass = (BudgetClass) ExBeanUtils
				.bindBusinessObjectData(request.getParameterMap(),
						BudgetClass.class, true, request.getMethod(), true);
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<BudgetTemplate> budgetTemplatemodifyItems = ExBeanUtils
				.bindModifySubBoData(request.getParameterMap(),
						new Object[] { budgetClass }, BudgetTemplate.class,
						null);
		budgetClass.setBudgetTemplate(budgetTemplatemodifyItems);
		Set<BudgetTemplate> budgetTemplatedeleteItems = ExBeanUtils
				.bindDeleteSubBoData(request.getParameterMap(),
						new Object[] { budgetClass }, BudgetTemplate.class,
						null);
		
		//如果是选择是实体对像的，就要先建一下时版
		if ("2".equals(budgetClass.getSourcetype()) && budgetClass.getBudclassid() == null){
			BudgetClass parentBudgetClass = (BudgetClass) ExBeanUtils
			.bindBusinessObjectData(request.getParameterMap(),
					BudgetClass.class, true, request.getMethod(), true);
			
			parentBudgetClass.setVersion(0);
			parentBudgetClass.setActive("N");
			
			this.budgetClassService._saveOrUpdate(parentBudgetClass,
					null, getBusinessObject());
			
			budgetClass.setBudupclassid(parentBudgetClass.getBudclassid());
			budgetClass.setVersion(-1);
			budgetClass.setActive("N");
			
			this.budgetClassService._saveOrUpdate(budgetClass,
					budgetTemplatedeleteItems, getBusinessObject());
			
			jo.put("budupupclassid", parentBudgetClass.getBudupclassid());
		}else{
			this.budgetClassService._saveOrUpdate(budgetClass,
				budgetTemplatedeleteItems, getBusinessObject());
		}
		jo.put("budclassid", budgetClass.getBudclassid());
		jo.put("budupclassid", budgetClass.getBudupclassid());
		jo.put("budclassname", budgetClass.getBudclassname());
		jo.put("sourcetype", budgetClass.getSourcetype());
		String creator = budgetClass.getCreator();
		String creator_text = SysCachePoolUtils.getDictDataValue("YUSER",
				creator);
		jo.put("creator_text", creator_text);
		jo.put("creator", creator);
		jo.put("createtime", budgetClass.getCreatetime());
		String lastmodifyer = budgetClass.getLastmodifyer();
		String lastmodifyer_text = SysCachePoolUtils.getDictDataValue("YUSER",
				lastmodifyer);
		jo.put("lastmodifyer_text", lastmodifyer_text);
		jo.put("lastmodifyer", lastmodifyer);
		jo.put("lastmodifytime", budgetClass.getLastmodifytime());
		this.operateSuccessfullyWithString(response, jo.toString());
	}
	
	/**
	 * 发布一个新的版本
	 * @param request
	 * @param response
	 */
	public void _promulgate(HttpServletRequest request,
			HttpServletResponse response){
		String strBudClassId = request.getParameter("budclassid");
		String strBudVersion = request.getParameter("version");
	
		BudgetClass budgetClass = this.budgetClassService._promulgate(strBudClassId ,strBudVersion);
		
		JSONObject jo = new JSONObject();

		jo.put("budclassid", budgetClass.getBudclassid());
		jo.put("budupclassid", budgetClass.getBudupclassid());
		jo.put("budclassname", budgetClass.getBudclassname());
		jo.put("version", budgetClass.getVersion());
		String creator = budgetClass.getCreator();
		String creator_text = SysCachePoolUtils.getDictDataValue("YUSER",
				creator);
		jo.put("creator_text", creator_text);
		jo.put("creator", creator);
		jo.put("createtime", budgetClass.getCreatetime());
		String lastmodifyer = budgetClass.getLastmodifyer();
		String lastmodifyer_text = SysCachePoolUtils.getDictDataValue("YUSER",
				lastmodifyer);
		jo.put("lastmodifyer_text", lastmodifyer_text);
		jo.put("lastmodifyer", lastmodifyer);
		jo.put("lastmodifytime", budgetClass.getLastmodifytime());
		this.operateSuccessfullyWithString(response, jo.toString());
	}
	
	/**
	 * 激活一个版本
	 * @param request
	 * @param response
	 */
	public void _active(HttpServletRequest request,
			HttpServletResponse response){
		String strBudClassId = request.getParameter("budclassid");

		BudgetClass budgetClass = this.budgetClassService._active(strBudClassId,getBusinessObject());
		
		JSONObject jo = new JSONObject();
		
		jo.put("budclassid", budgetClass.getBudclassid());
		jo.put("budupclassid", budgetClass.getBudupclassid());
		jo.put("budclassname", budgetClass.getBudclassname());
		String creator = budgetClass.getCreator();
		String creator_text = SysCachePoolUtils.getDictDataValue("YUSER",
				creator);
		jo.put("creator_text", creator_text);
		jo.put("creator", creator);
		jo.put("createtime", budgetClass.getCreatetime());
		String lastmodifyer = budgetClass.getLastmodifyer();
		String lastmodifyer_text = SysCachePoolUtils.getDictDataValue("YUSER",
				lastmodifyer);
		jo.put("lastmodifyer_text", lastmodifyer_text);
		jo.put("lastmodifyer", lastmodifyer);
		jo.put("lastmodifytime", budgetClass.getLastmodifytime());
		this.operateSuccessfullyWithString(response, jo.toString());
	}
}