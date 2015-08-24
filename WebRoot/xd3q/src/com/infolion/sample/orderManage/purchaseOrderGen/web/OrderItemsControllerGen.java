/*
 * @(#)OrderItemsControllerGen.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年03月31日 15点00分58秒
 *  描　述：创建
 */
package com.infolion.sample.orderManage.purchaseOrderGen.web;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;
import java.io.IOException;
import java.util.Set;
import java.math.BigDecimal;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import com.infolion.platform.dpframework.util.ExBeanUtils;
import com.infolion.platform.dpframework.util.JsonUtils;
import com.infolion.platform.dpframework.core.BusinessException;
import com.infolion.platform.dpframework.util.StringUtils;
import com.infolion.platform.dpframework.uicomponent.lock.LockService;
import com.infolion.platform.basicApp.authManage.domain.OperationType;
import com.infolion.platform.dpframework.core.cache.SysCachePoolUtils;
import com.infolion.platform.workflow.engine.WorkflowService;
import com.infolion.sample.orderManage.purchaseOrder.domain.OrderItems;
import com.infolion.sample.orderManage.purchaseOrder.service.OrderItemsService;
import com.infolion.platform.bo.domain.BusinessObject;
import com.infolion.platform.bo.service.BusinessObjectService;
import com.infolion.platform.dpframework.core.web.BaseMultiActionController;
import com.infolion.platform.dpframework.core.web.AbstractGenController;

/**
 * <pre>
 * 采购行项目(OrderItems)控制器类
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
public class OrderItemsControllerGen extends AbstractGenController
{
	private final String boName = "OrderItems";

	public String getBoName()
	{
		return this.boName;
	}
	
	@Autowired
	protected OrderItemsService orderItemsService;
	
	public void setOrderItemsService(OrderItemsService orderItemsService)
	{
		this.orderItemsService = orderItemsService;
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
	 * 编辑  
	 *   
	 * @param request
	 * @param response
	 */
	public ModelAndView _edit(HttpServletRequest request, HttpServletResponse response)
	{
		OrderItems orderItems = new OrderItems();
	    orderItems = (OrderItems) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), OrderItems.class , false, request.getMethod(), false);
		request.setAttribute("vt", getBusinessObject().getViewText());
		request.setAttribute("orderItems", orderItems);
		return new ModelAndView("sample/orderManage/purchaseOrder/orderItemsEdit");
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
		OrderItems orderItems = (OrderItems) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), OrderItems.class , true , request.getMethod(), true);
this.orderItemsService._saveOrUpdate(orderItems,getBusinessObject());
		jo.put("orderItemsId", orderItems.getOrderItemsId());
		this.operateSuccessfullyWithString(response,jo.toString());
	}
	
	/**
	 * 创建  
	 *   
	 * @param request
	 * @param response
	 */
	public ModelAndView _create(HttpServletRequest request, HttpServletResponse response)
	{
	    OrderItems orderItems = new OrderItems();
		request.setAttribute("vt", getBusinessObject().getViewText());
		request.setAttribute("orderItems",orderItems);

	    return new ModelAndView("sample/orderManage/purchaseOrder/orderItemsAdd");
	}
	
	
	/**
	 * 复制创建  
	 *   
	 * @param request
	 * @param response
	 */
	public ModelAndView _copyCreate(HttpServletRequest request, HttpServletResponse response)
	{
		OrderItems orderItems = new OrderItems ();
	    orderItems = (OrderItems) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), OrderItems.class , false , request.getMethod(), false);
		request.setAttribute("orderItems", orderItems);
		request.setAttribute("vt", getBusinessObject().getViewText());
		return new ModelAndView("sample/orderManage/purchaseOrder/orderItemsAdd");
	}

	
	/**
	 * 删除  
	 *   
	 * @param request
	 * @param response
	 */
	public void _delete(HttpServletRequest request, HttpServletResponse response)
	{
		String orderItemsId = request.getParameter("orderItemsId");
		orderItemsService._delete(orderItemsId,getBusinessObject());
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
		String orderItemsIds = request.getParameter("orderItemsIds");
		orderItemsService._deletes(orderItemsIds,getBusinessObject());
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
		OrderItems orderItems = new OrderItems();
	    orderItems = (OrderItems) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), OrderItems.class , false , request.getMethod(), false);
		request.setAttribute("vt", getBusinessObject().getViewText());
		
		request.setAttribute("orderItems", orderItems);  
		return new ModelAndView("sample/orderManage/purchaseOrder/orderItemsView");
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
		OrderItems orderItems = (OrderItems) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), OrderItems.class , true , request.getMethod(), true);
this.orderItemsService._submitProcess(orderItems		,getBusinessObject());
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
		return new ModelAndView("sample/orderManage/purchaseOrder/orderItemsManage");
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
		OrderItems orderItems  = new OrderItems();
		String orderItemsId = request.getParameter("orderItemsId");
		orderItems.setOrderItemsId(orderItemsId);
		LockService.unLockBOData(orderItems);
		this.operateSuccessfullyHiddenInfo(response);
	}
}