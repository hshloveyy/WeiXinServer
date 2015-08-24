/*
 * @(#)PurchasingDocItemControllerGen.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2009年12月01日 19点57分06秒
 *  描　述：创建
 */
package com.infolion.sample.purchaseGen.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;
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
import com.infolion.sample.purchase.domain.PurchasingDocItem;
import com.infolion.sample.purchase.service.PurchasingDocItemService;
import com.infolion.platform.dpframework.core.web.BaseMultiActionController;

/**
 * <pre>
 * SAP采购凭证行项目(PurchasingDocItem)控制器类
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
public class PurchasingDocItemControllerGen extends BaseMultiActionController
{
	@Autowired
	private PurchasingDocItemService purchasingDocItemService;

	public void setPurchasingDocItemService(PurchasingDocItemService purchasingDocItemService)
	{
		this.purchasingDocItemService = purchasingDocItemService;
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
		PurchasingDocItem purchasingDocItem = new PurchasingDocItem();
		purchasingDocItem = (PurchasingDocItem) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), PurchasingDocItem.class, false, request.getMethod(), false);
		request.setAttribute("purchasingDocItem", purchasingDocItem);
		return new ModelAndView("sample/purchase/purchasingDocItemEdit");
	}

	/**
	 * 创建
	 * 
	 * @param request
	 * @param response
	 */
	public ModelAndView _create(HttpServletRequest request, HttpServletResponse response)
	{
		PurchasingDocItem purchasingDocItem = new PurchasingDocItem();

		request.setAttribute("purchasingDocItem", purchasingDocItem);

		return new ModelAndView("sample/purchase/purchasingDocItemAdd");
	}

	/**
	 * 复制创建
	 * 
	 * @param request
	 * @param response
	 */
	public ModelAndView _copyCreate(HttpServletRequest request, HttpServletResponse response)
	{
		PurchasingDocItem purchasingDocItem = new PurchasingDocItem();
		purchasingDocItem = (PurchasingDocItem) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), PurchasingDocItem.class, false, request.getMethod(), false);
		request.setAttribute("purchasingDocItem", purchasingDocItem);

		return new ModelAndView("sample/purchase/purchasingDocItemAdd");
	}

	/**
	 * 删除
	 * 
	 * @param request
	 * @param response
	 */
	public void _delete(HttpServletRequest request, HttpServletResponse response)
	{
		String ebeln = request.getParameter("ebeln");
		purchasingDocItemService._delete(ebeln);
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
		String purchasingDocItemIds = request.getParameter("purchasingDocItemIds");
		purchasingDocItemService._deletes(purchasingDocItemIds);
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
		PurchasingDocItem purchasingDocItem = new PurchasingDocItem();
		purchasingDocItem = (PurchasingDocItem) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), PurchasingDocItem.class, false, request.getMethod(), false);
		request.setAttribute("purchasingDocItem", purchasingDocItem);
		return new ModelAndView("sample/purchase/purchasingDocItemView");
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
		PurchasingDocItem purchasingDocItem = (PurchasingDocItem) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), PurchasingDocItem.class, true, request.getMethod(), true);
		this.purchasingDocItemService._submitProcess(purchasingDocItem);
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

		return new ModelAndView("sample/purchase/purchasingDocItemManage");
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
		PurchasingDocItem purchasingDocItem = new PurchasingDocItem();
		String ebeln = request.getParameter("ebeln");
		purchasingDocItem.setEbeln(ebeln);
		LockService.unLockBOData(purchasingDocItem);
		this.operateSuccessfullyHiddenInfo(response);
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
	 * 分配
	 * 
	 * @param request
	 * @param response
	 */
	public void _assign(HttpServletRequest request, HttpServletResponse response)
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
	 * 保存
	 * 
	 * @param request
	 * @param response
	 */
	public void _saveOrUpdate(HttpServletRequest request, HttpServletResponse response)
	{
		JSONObject jo = new JSONObject();
		// 绑定主对象值
		PurchasingDocItem purchasingDocItem = (PurchasingDocItem) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), PurchasingDocItem.class, true, request.getMethod(), true);
		this.purchasingDocItemService._saveOrUpdate(purchasingDocItem);
		jo.put("ebeln", purchasingDocItem.getEbeln());
		this.operateSuccessfullyWithString(response, jo.toString());
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
}