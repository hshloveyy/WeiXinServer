/*
 * @(#)SalesDocControllerGen.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2009年12月02日 17点06分44秒
 *  描　述：创建
 */
package com.infolion.sample.salesGen.web;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

import com.infolion.platform.basicApp.authManage.UserContextHolder;
import com.infolion.platform.dpframework.core.web.BaseMultiActionController;
import com.infolion.platform.dpframework.uicomponent.lock.LockService;
import com.infolion.platform.dpframework.util.DateUtils;
import com.infolion.platform.dpframework.util.ExBeanUtils;
import com.infolion.platform.dpframework.util.StringUtils;
import com.infolion.platform.workflow.engine.WorkflowService;
import com.infolion.sample.sales.domain.SalesDoc;
import com.infolion.sample.sales.domain.SalesDocItem;
import com.infolion.sample.sales.domain.SalesDocKey;
import com.infolion.sample.sales.service.SalesDocService;

/**
 * <pre>
 * SAP销售订单(SalesDoc)控制器类
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
public class SalesDocControllerGen extends BaseMultiActionController
{
	@Autowired
	private SalesDocService salesDocService;

	public void setSalesDocService(SalesDocService salesDocService)
	{
		this.salesDocService = salesDocService;
	}

	/**
	 * 查看
	 * 
	 * @param request
	 * @param response
	 */
	public ModelAndView _view(HttpServletRequest request, HttpServletResponse response)
	{
		SalesDoc salesDoc = new SalesDoc();
		String workflowTaskId = request.getParameter("workflowTaskId");
		String id = request.getParameter("vbeln");
		String client = request.getParameter("client");
		String businessId = request.getParameter("businessId");

		if (null == id)
			id = businessId;

		if (StringUtils.isNullBlank(client))
			client = "800";

		SalesDocKey key = new SalesDocKey();
		key.setClient(client);
		key.setVbeln(id);

		if (StringUtils.isNullBlank(businessId))
		{
			salesDoc = this.salesDocService._get(key);
		}
		else
		{
			salesDoc = this.salesDocService._get(key);
		}

		boolean haveBindWorkFlow = WorkflowService.getHaveBindWorkFlow("000000000173");
		request.setAttribute("haveBindWorkFlow", haveBindWorkFlow);
		request.setAttribute("businessId", id);
		request.setAttribute("workflowTaskId", workflowTaskId);
		request.setAttribute("salesDoc", salesDoc);

		return new ModelAndView("sample/sales/salesDocView");
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
		SalesDoc salesDoc = (SalesDoc) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), SalesDoc.class, true, request.getMethod(), true);
		// String lastappname = "";
		// String lastapptime = "";
		//		
		// lastapptime = DateUtils.getCurrTime(DateUtils.DB_STORE_DATE);
		// lastappname =
		// UserContextHolder.getLocalUserContext().getUserContext().getUser().getUserName();
		// salesDoc.setLastappname(lastappname);
		// salesDoc.setLastapptime(lastapptime);
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<SalesDocItem> salesDocItemsmodifyItems = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { salesDoc }, SalesDocItem.class, null);
		Set<SalesDocItem> deletedSalesDocItemSet = ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(), new Object[] { salesDoc }, SalesDocItem.class, null);
		salesDoc.setSalesDocItems(salesDocItemsmodifyItems);
		this.salesDocService._submitProcess(salesDoc, deletedSalesDocItemSet);
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
		return new ModelAndView("sample/sales/salesDocManage");
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
		SalesDoc salesDoc = new SalesDoc();
		String vbeln = request.getParameter("vbeln");
		salesDoc.setVbeln(vbeln);
		LockService.unLockBOData(salesDoc);
		this.operateSuccessfullyHiddenInfo(response);
	}
}