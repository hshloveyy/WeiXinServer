/*
 * @(#)PurchasingDocControllerGen.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2009年12月01日 19点57分05秒
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
import com.infolion.sample.purchase.domain.PurchasingDoc;
import com.infolion.sample.purchase.domain.PurchasingDocItem;
import com.infolion.sample.purchase.domain.PurchasingDocKey;
import com.infolion.sample.purchase.service.PurchasingDocItemService;
import com.infolion.sample.purchase.service.PurchasingDocService;

import com.infolion.platform.dpframework.core.web.BaseMultiActionController;

/**
 * <pre>
 * SAP采购凭证(PurchasingDoc)控制器类
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
public class PurchasingDocControllerGen extends BaseMultiActionController
{
	@Autowired
	private PurchasingDocService purchasingDocService;

	public void setPurchasingDocService(PurchasingDocService purchasingDocService)
	{
		this.purchasingDocService = purchasingDocService;
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
		PurchasingDoc purchasingDoc = new PurchasingDoc();
		String id = request.getParameter("ebeln");
		String client = request.getParameter("client");
		PurchasingDocKey key = new PurchasingDocKey();
		key.setClient(client);
		key.setEbeln(id);
		String workflowTaskId = request.getParameter("workflowTaskId");
		String businessId = request.getParameter("businessId");
		if (null == id)
			id = businessId;
		purchasingDoc = this.purchasingDocService._getForEdit(key);
		boolean haveBindWorkFlow = WorkflowService.getHaveBindWorkFlow("000000000169");
		request.setAttribute("businessId", id);
		request.setAttribute("workflowTaskId", workflowTaskId);
		request.setAttribute("haveBindWorkFlow", haveBindWorkFlow);
		request.setAttribute("purchasingDoc", purchasingDoc);
		return new ModelAndView("sample/purchase/purchasingDocEdit");
	}

	/**
	 * 创建
	 * 
	 * @param request
	 * @param response
	 */
	public ModelAndView _create(HttpServletRequest request, HttpServletResponse response)
	{
		PurchasingDoc purchasingDoc = new PurchasingDoc();

		request.setAttribute("purchasingDoc", purchasingDoc);

		return new ModelAndView("sample/purchase/purchasingDocAdd");
	}

	/**
	 * 复制创建
	 * 
	 * @param request
	 * @param response
	 */
	public ModelAndView _copyCreate(HttpServletRequest request, HttpServletResponse response)
	{
		String id = request.getParameter("ebeln");
		String client = request.getParameter("client");
		PurchasingDocKey key = new PurchasingDocKey();
		key.setClient(client);
		key.setEbeln(id);
		PurchasingDoc purchasingDoc = this.purchasingDocService._getEntityCopy(key);
		request.setAttribute("isCreateCopy", "true");
		request.setAttribute("purchasingDoc", purchasingDoc);

		return new ModelAndView("sample/purchase/purchasingDocAdd");
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
		purchasingDocService._delete(ebeln);
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
		String purchasingDocIds = request.getParameter("purchasingDocIds");
		purchasingDocService._deletes(purchasingDocIds);
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
		PurchasingDoc purchasingDoc = new PurchasingDoc();
		String workflowTaskId = request.getParameter("workflowTaskId");
		String id = request.getParameter("ebeln");
		String client = request.getParameter("client");

		if (StringUtils.isNullBlank(client))
			client = "800";

		String businessId = request.getParameter("businessId");


		if (null == id)
			id = businessId;

		PurchasingDocKey key = new PurchasingDocKey();
		key.setClient(client);
		key.setEbeln(id);
		log.debug("id: " + id);
		log.debug("businessId: " + businessId);
		log.debug("client: " + client);

		if (StringUtils.isNullBlank(businessId))
		{
			purchasingDoc = this.purchasingDocService._get(key);
		}
		else
		{
			purchasingDoc = this.purchasingDocService._get(key);
		}

		boolean haveBindWorkFlow = WorkflowService.getHaveBindWorkFlow("000000000169");
		request.setAttribute("haveBindWorkFlow", haveBindWorkFlow);
		request.setAttribute("businessId", id);
		request.setAttribute("workflowTaskId", workflowTaskId);
		request.setAttribute("purchasingDoc", purchasingDoc);
		
		return new ModelAndView("sample/purchase/purchasingDocView");
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
		PurchasingDoc purchasingDoc = (PurchasingDoc) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), PurchasingDoc.class, true, request.getMethod(), true);
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<PurchasingDocItem> purchasingDocItemsmodifyItems = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { purchasingDoc }, PurchasingDocItem.class, null);
		Set<PurchasingDocItem> deletedPurchasingDocItemSet = ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(), new Object[] { purchasingDoc }, PurchasingDocItem.class, null);
		purchasingDoc.setPurchasingDocItems(purchasingDocItemsmodifyItems);
		this.purchasingDocService._submitProcess(purchasingDoc, deletedPurchasingDocItemSet);
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

		return new ModelAndView("sample/purchase/purchasingDocManage");
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
		PurchasingDoc purchasingDoc = new PurchasingDoc();
		String ebeln = request.getParameter("ebeln");
		purchasingDoc.setEbeln(ebeln);
		LockService.unLockBOData(purchasingDoc);
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
		PurchasingDoc purchasingDoc = (PurchasingDoc) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), PurchasingDoc.class, true, request.getMethod(), true);
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<PurchasingDocItem> purchasingDocItemsmodifyItems = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { purchasingDoc }, PurchasingDocItem.class, null);
		purchasingDoc.setPurchasingDocItems(purchasingDocItemsmodifyItems);
		Set<PurchasingDocItem> purchasingDocItemsdeleteItems = ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(), new Object[] { purchasingDoc }, PurchasingDocItem.class, null);
		this.purchasingDocService._saveOrUpdate(purchasingDoc, purchasingDocItemsdeleteItems);
		jo.put("ebeln", purchasingDoc.getEbeln());
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