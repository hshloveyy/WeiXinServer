/*
 * @(#)BudgetTemplateItemControllerGen.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年03月12日 15点21分40秒
 *  描　述：创建
 */
package com.infolion.XDSS.budget.maindata.BudgetTemplateItemGen.web;


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
import com.infolion.XDSS.budget.maindata.BudgetTemplateItem.domain.BudgetTemplateItem;
import com.infolion.XDSS.budget.maindata.BudgetTemplateItem.service.BudgetTemplateItemService;
import com.infolion.platform.bo.domain.BusinessObject;
import com.infolion.platform.bo.service.BusinessObjectService;
import com.infolion.platform.dpframework.core.web.BaseMultiActionController;
import com.infolion.platform.dpframework.core.web.AbstractGenController;

/**
 * <pre>
 * 模版预算项(BudgetTemplateItem)控制器类
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
public class BudgetTemplateItemControllerGen extends AbstractGenController
{
	private final String boName = "BudgetTemplateItem";

	public String getBoName()
	{
		return this.boName;
	}
	
	@Autowired
	protected BudgetTemplateItemService budgetTemplateItemService;
	
	public void setBudgetTemplateItemService(BudgetTemplateItemService budgetTemplateItemService)
	{
		this.budgetTemplateItemService = budgetTemplateItemService;
	}
	
	/**
	 * 删除  
	 *   
	 * @param request
	 * @param response
	 */
	public void _delete(HttpServletRequest request, HttpServletResponse response)
	{
		String budtemitemid = request.getParameter("budtemitemid");
		budgetTemplateItemService._delete(budtemitemid,getBusinessObject());
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
		String budgetTemplateItemIds = request.getParameter("budgetTemplateItemIds");
		budgetTemplateItemService._deletes(budgetTemplateItemIds,getBusinessObject());
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
		BudgetTemplateItem budgetTemplateItem = new BudgetTemplateItem();
	    budgetTemplateItem = (BudgetTemplateItem) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), BudgetTemplateItem.class , false , request.getMethod(), false);
		request.setAttribute("vt", getBusinessObject().getViewText());
		
		request.setAttribute("budgetTemplateItem", budgetTemplateItem);  
		return new ModelAndView("XDSS/budget/maindata/BudgetTemplateItem/budgetTemplateItemView");
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
		BudgetTemplateItem budgetTemplateItem = (BudgetTemplateItem) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), BudgetTemplateItem.class , true , request.getMethod(), true);
this.budgetTemplateItemService._submitProcess(budgetTemplateItem		,getBusinessObject());
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
		return new ModelAndView("XDSS/budget/maindata/BudgetTemplateItem/budgetTemplateItemManage");
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
		BudgetTemplateItem budgetTemplateItem  = new BudgetTemplateItem();
		String budtemitemid = request.getParameter("budtemitemid");
		budgetTemplateItem.setBudtemitemid(budtemitemid);
		LockService.unLockBOData(budgetTemplateItem);
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
		BudgetTemplateItem budgetTemplateItem = (BudgetTemplateItem) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), BudgetTemplateItem.class , true , request.getMethod(), true);
this.budgetTemplateItemService._saveOrUpdate(budgetTemplateItem,getBusinessObject());
		jo.put("budtemitemid", budgetTemplateItem.getBudtemitemid());
String creator = budgetTemplateItem.getCreator();
String creator_text = SysCachePoolUtils.getDictDataValue("YUSER", creator);
jo.put("creator_text", creator_text);
jo.put("creator", creator);
jo.put("createtime", budgetTemplateItem.getCreatetime());
String lastmodifyer = budgetTemplateItem.getLastmodifyer();
String lastmodifyer_text = SysCachePoolUtils.getDictDataValue("YUSER", lastmodifyer);
jo.put("lastmodifyer_text", lastmodifyer_text);
jo.put("lastmodifyer", lastmodifyer);
jo.put("lastmodifytime", budgetTemplateItem.getLastmodifytime());		this.operateSuccessfullyWithString(response,jo.toString());
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
	
	/**
	 * 编辑  
	 *   
	 * @param request
	 * @param response
	 */
	public ModelAndView _edit(HttpServletRequest request, HttpServletResponse response)
	{
		BudgetTemplateItem budgetTemplateItem = new BudgetTemplateItem();
	    budgetTemplateItem = (BudgetTemplateItem) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), BudgetTemplateItem.class , false, request.getMethod(), false);
		request.setAttribute("vt", getBusinessObject().getViewText());
		request.setAttribute("budgetTemplateItem", budgetTemplateItem);
		return new ModelAndView("XDSS/budget/maindata/BudgetTemplateItem/budgetTemplateItemEdit");
	}
	
	/**
	 * 创建  
	 *   
	 * @param request
	 * @param response
	 */
	public ModelAndView _create(HttpServletRequest request, HttpServletResponse response)
	{
	    BudgetTemplateItem budgetTemplateItem = new BudgetTemplateItem();
		request.setAttribute("vt", getBusinessObject().getViewText());
		request.setAttribute("budgetTemplateItem",budgetTemplateItem);

	    return new ModelAndView("XDSS/budget/maindata/BudgetTemplateItem/budgetTemplateItemAdd");
	}
	
	
	/**
	 * 复制创建  
	 *   
	 * @param request
	 * @param response
	 */
	public ModelAndView _copyCreate(HttpServletRequest request, HttpServletResponse response)
	{
		BudgetTemplateItem budgetTemplateItem = new BudgetTemplateItem ();
	    budgetTemplateItem = (BudgetTemplateItem) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), BudgetTemplateItem.class , false , request.getMethod(), false);
		request.setAttribute("budgetTemplateItem", budgetTemplateItem);
		request.setAttribute("vt", getBusinessObject().getViewText());
		return new ModelAndView("XDSS/budget/maindata/BudgetTemplateItem/budgetTemplateItemAdd");
	}

}