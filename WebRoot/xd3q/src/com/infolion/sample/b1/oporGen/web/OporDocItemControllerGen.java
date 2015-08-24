/*
 * @(#)OporDocItemControllerGen.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年03月25日 19点56分39秒
 *  描　述：创建
 */
package com.infolion.sample.b1.oporGen.web;


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
import com.infolion.sample.b1.opor.domain.OporDocItem;
import com.infolion.sample.b1.opor.service.OporDocItemService;
import com.infolion.platform.bo.domain.BusinessObject;
import com.infolion.platform.bo.service.BusinessObjectService;
import com.infolion.platform.dpframework.core.web.BaseMultiActionController;
import com.infolion.platform.dpframework.core.web.AbstractGenController;

/**
 * <pre>
 * 采购订单明细(OporDocItem)控制器类
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
public class OporDocItemControllerGen extends AbstractGenController
{
	private final String boName = "OporDocItem";

	public String getBoName()
	{
		return this.boName;
	}
	
	@Autowired
	protected OporDocItemService oporDocItemService;
	
	public void setOporDocItemService(OporDocItemService oporDocItemService)
	{
		this.oporDocItemService = oporDocItemService;
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
		OporDocItem oporDocItem = new OporDocItem();
	    oporDocItem = (OporDocItem) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), OporDocItem.class , false, request.getMethod(), false);
		request.setAttribute("vt", getBusinessObject().getViewText());
		request.setAttribute("oporDocItem", oporDocItem);
		return new ModelAndView("sample/b1/opor/oporDocItemEdit");
	}
	
	/**
	 * 创建  
	 *   
	 * @param request
	 * @param response
	 */
	public ModelAndView _create(HttpServletRequest request, HttpServletResponse response)
	{
	    OporDocItem oporDocItem = new OporDocItem();
		request.setAttribute("vt", getBusinessObject().getViewText());
		request.setAttribute("oporDocItem",oporDocItem);

	    return new ModelAndView("sample/b1/opor/oporDocItemAdd");
	}
	
	
	/**
	 * 复制创建  
	 *   
	 * @param request
	 * @param response
	 */
	public ModelAndView _copyCreate(HttpServletRequest request, HttpServletResponse response)
	{
		OporDocItem oporDocItem = new OporDocItem ();
	    oporDocItem = (OporDocItem) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), OporDocItem.class , false , request.getMethod(), false);
		request.setAttribute("oporDocItem", oporDocItem);
		request.setAttribute("vt", getBusinessObject().getViewText());
		return new ModelAndView("sample/b1/opor/oporDocItemAdd");
	}

	
	/**
	 * 删除  
	 *   
	 * @param request
	 * @param response
	 */
	public void _delete(HttpServletRequest request, HttpServletResponse response)
	{
		String oporDocItemId = request.getParameter("oporDocItemId");
		oporDocItemService._delete(oporDocItemId,getBusinessObject());
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
		String oporDocItemIds = request.getParameter("oporDocItemIds");
		oporDocItemService._deletes(oporDocItemIds,getBusinessObject());
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
		OporDocItem oporDocItem = new OporDocItem();
	    oporDocItem = (OporDocItem) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), OporDocItem.class , false , request.getMethod(), false);
		request.setAttribute("vt", getBusinessObject().getViewText());
		
		request.setAttribute("oporDocItem", oporDocItem);  
		return new ModelAndView("sample/b1/opor/oporDocItemView");
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
		OporDocItem oporDocItem = (OporDocItem) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), OporDocItem.class , true , request.getMethod(), true);
this.oporDocItemService._submitProcess(oporDocItem		,getBusinessObject());
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
		return new ModelAndView("sample/b1/opor/oporDocItemManage");
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
		OporDocItem oporDocItem  = new OporDocItem();
		String oporDocItemId = request.getParameter("oporDocItemId");
		oporDocItem.setOporDocItemId(oporDocItemId);
		LockService.unLockBOData(oporDocItem);
		this.operateSuccessfullyHiddenInfo(response);
	}
    
	/**
	 * 分配  
	 *   
	 * @param request
	 * @param response
	 */
	public void _assign (HttpServletRequest request, HttpServletResponse response)
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
		OporDocItem oporDocItem = (OporDocItem) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), OporDocItem.class , true , request.getMethod(), true);
this.oporDocItemService._saveOrUpdate(oporDocItem,getBusinessObject());
		jo.put("oporDocItemId", oporDocItem.getOporDocItemId());
		this.operateSuccessfullyWithString(response,jo.toString());
	}
    
	/**
	 * 附加空行  
	 *   
	 * @param request
	 * @param response
	 */
	public void _appendLine (HttpServletRequest request, HttpServletResponse response)
	{
       
	}
    
	/**
	 * 插入行  
	 *   
	 * @param request
	 * @param response
	 */
	public void _insertLine (HttpServletRequest request, HttpServletResponse response)
	{
       
	}
    
	/**
	 * 增加同级节点  
	 *   
	 * @param request
	 * @param response
	 */
	public void _addNode (HttpServletRequest request, HttpServletResponse response)
	{
       
	}
    
	/**
	 * 增加下级节点  
	 *   
	 * @param request
	 * @param response
	 */
	public void _addSubNode (HttpServletRequest request, HttpServletResponse response)
	{
       
	}
    
	/**
	 * 删除节点  
	 *   
	 * @param request
	 * @param response
	 */
	public void _deleteNode (HttpServletRequest request, HttpServletResponse response)
	{
       
	}
    
	/**
	 * 图表查询  
	 *   
	 * @param request
	 * @param response
	 */
	public void _queryChart (HttpServletRequest request, HttpServletResponse response)
	{
       
	}
    
	/**
	 * 图表明细查询  
	 *   
	 * @param request
	 * @param response
	 */
	public void _queryChartDetail (HttpServletRequest request, HttpServletResponse response)
	{
       
	}
    
	/**
	 * 下载  
	 *   
	 * @param request
	 * @param response
	 */
	public void _download (HttpServletRequest request, HttpServletResponse response)
	{
       
	}
}