/*
 * @(#)BudgetTemplateControllerGen.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年03月12日 15点21分40秒
 *  描　述：创建
 */
package com.infolion.XDSS.budget.maindata.BudgetTemplateGen.web;


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
import com.infolion.XDSS.budget.maindata.BudgetTemplate.domain.BudgetTemplate;
import com.infolion.XDSS.budget.maindata.BudgetTemplate.service.BudgetTemplateService;
          
import com.infolion.XDSS.budget.maindata.BudgetTemplateItem.domain.BudgetTemplateItem;
import com.infolion.XDSS.budget.maindata.BudgetTemplateItem.service.BudgetTemplateItemService;
import com.infolion.platform.bo.domain.BusinessObject;
import com.infolion.platform.bo.service.BusinessObjectService;
import com.infolion.platform.dpframework.core.web.BaseMultiActionController;
import com.infolion.platform.dpframework.core.web.AbstractGenController;

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
public class BudgetTemplateControllerGen extends AbstractGenController
{
	private final String boName = "BudgetTemplate";

	public String getBoName()
	{
		return this.boName;
	}
	
	@Autowired
	protected BudgetTemplateService budgetTemplateService;
	
	public void setBudgetTemplateService(BudgetTemplateService budgetTemplateService)
	{
		this.budgetTemplateService = budgetTemplateService;
	}
          
	
	/**
	 * 删除  
	 *   
	 * @param request
	 * @param response
	 */
	public void _delete(HttpServletRequest request, HttpServletResponse response)
	{
		String budtemid = request.getParameter("budtemid");
		budgetTemplateService._delete(budtemid,getBusinessObject());
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
		String budgetTemplateIds = request.getParameter("budgetTemplateIds");
		budgetTemplateService._deletes(budgetTemplateIds,getBusinessObject());
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
		BudgetTemplate budgetTemplate = new BudgetTemplate();
		String workflowTaskId = request.getParameter("workflowTaskId");
		String id = request.getParameter("budtemid");
		String businessId = request.getParameter("businessId");	
		
		if (null == id)
			id = businessId ;
			
		if(StringUtils.isNullBlank(businessId))
        {	
		   budgetTemplate = this.budgetTemplateService._get(id);
	}
        else
        {
           budgetTemplate = this.budgetTemplateService._get(id);
        }
    
      	boolean haveBindWorkFlow = WorkflowService.getHaveBindWorkFlow("000000000195");
		request.setAttribute("haveBindWorkFlow", haveBindWorkFlow);
		request.setAttribute("businessId", id);
		request.setAttribute("workflowTaskId", workflowTaskId);
		request.setAttribute("vt", getBusinessObject().getViewText());
        if(null!=getBusinessObject().getSubBusinessObject())
        {
			// 取得子业务对象
			for (BusinessObject bo : getBusinessObject().getSubBusinessObject())
			{
				request.setAttribute(bo.getBeanAttribute().getBeanInstanceName()+"Vt", bo.getViewText());
			}
		}
		
		request.setAttribute("budgetTemplate", budgetTemplate);  
		return new ModelAndView("XDSS/budget/maindata/BudgetTemplate/budgetTemplateView");
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
		BudgetTemplate budgetTemplate = (BudgetTemplate) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), BudgetTemplate.class , true , request.getMethod(), true);
// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
Set<BudgetTemplateItem> budgetTemplateItemmodifyItems = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { budgetTemplate }, BudgetTemplateItem.class, null);
Set<BudgetTemplateItem> deletedBudgetTemplateItemSet = ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(), new Object[] { budgetTemplate }, BudgetTemplateItem.class, null);
budgetTemplate.setBudgetTemplateItem(budgetTemplateItemmodifyItems);
this.budgetTemplateService._submitProcess(budgetTemplate
,deletedBudgetTemplateItemSet		,getBusinessObject());
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
		return new ModelAndView("XDSS/budget/maindata/BudgetTemplate/budgetTemplateManage");
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
		BudgetTemplate budgetTemplate  = new BudgetTemplate();
		String budtemid = request.getParameter("budtemid");
		budgetTemplate.setBudtemid(budtemid);
		LockService.unLockBOData(budgetTemplate);
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
		BudgetTemplate budgetTemplate = (BudgetTemplate) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), BudgetTemplate.class , true , request.getMethod(), true);
// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
Set<BudgetTemplateItem> budgetTemplateItemmodifyItems = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { budgetTemplate }, BudgetTemplateItem.class, null);
budgetTemplate.setBudgetTemplateItem(budgetTemplateItemmodifyItems);
Set<BudgetTemplateItem> budgetTemplateItemdeleteItems = ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(), new Object[] { budgetTemplate }, BudgetTemplateItem.class, null);
this.budgetTemplateService._saveOrUpdate(budgetTemplate
,budgetTemplateItemdeleteItems,getBusinessObject());
		jo.put("budtemid", budgetTemplate.getBudtemid());
String creator = budgetTemplate.getCreator();
String creator_text = SysCachePoolUtils.getDictDataValue("YUSER", creator);
jo.put("creator_text", creator_text);
jo.put("creator", creator);
jo.put("createtime", budgetTemplate.getCreatetime());
String lastmodifyer = budgetTemplate.getLastmodifyer();
String lastmodifyer_text = SysCachePoolUtils.getDictDataValue("YUSER", lastmodifyer);
jo.put("lastmodifyer_text", lastmodifyer_text);
jo.put("lastmodifyer", lastmodifyer);
jo.put("lastmodifytime", budgetTemplate.getLastmodifytime());		this.operateSuccessfullyWithString(response,jo.toString());
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
		BudgetTemplate budgetTemplate = new BudgetTemplate();
		String id = request.getParameter("budtemid");
		String workflowTaskId = request.getParameter("workflowTaskId");
		String businessId = request.getParameter("businessId");	
		if (null == id)
			id = businessId ;
		budgetTemplate = this.budgetTemplateService._getForEdit(id);
		boolean haveBindWorkFlow = WorkflowService.getHaveBindWorkFlow("000000000195");
		request.setAttribute("businessId", id);
		request.setAttribute("workflowTaskId", workflowTaskId);	
		request.setAttribute("haveBindWorkFlow", haveBindWorkFlow);	
		request.setAttribute("vt", getBusinessObject().getViewText());
        if(null!=getBusinessObject().getSubBusinessObject())
        {
			// 取得子业务对象
			for (BusinessObject bo : getBusinessObject().getSubBusinessObject())
			{
				request.setAttribute(bo.getBeanAttribute().getBeanInstanceName()+"Vt", bo.getViewText());
			}
		}
		request.setAttribute("budgetTemplate", budgetTemplate);
		return new ModelAndView("XDSS/budget/maindata/BudgetTemplate/budgetTemplateEdit");
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
		request.setAttribute("vt", getBusinessObject().getViewText());
        if(null!=getBusinessObject().getSubBusinessObject())
        {
			// 取得子业务对象
			for (BusinessObject bo : getBusinessObject().getSubBusinessObject())
			{
				request.setAttribute(bo.getBeanAttribute().getBeanInstanceName()+"Vt", bo.getViewText());
			}
		}
		request.setAttribute("budgetTemplate",budgetTemplate);

	    return new ModelAndView("XDSS/budget/maindata/BudgetTemplate/budgetTemplateAdd");
	}
	
	
	/**
	 * 复制创建  
	 *   
	 * @param request
	 * @param response
	 */
	public ModelAndView _copyCreate(HttpServletRequest request, HttpServletResponse response)
	{
		String id = request.getParameter("budtemid");
		BudgetTemplate budgetTemplate = this.budgetTemplateService._getEntityCopy(id);
		request.setAttribute("isCreateCopy", "true");
		request.setAttribute("budgetTemplate", budgetTemplate);
		request.setAttribute("vt", getBusinessObject().getViewText());
        if(null!=getBusinessObject().getSubBusinessObject())
        {
			// 取得子业务对象
			for (BusinessObject bo : getBusinessObject().getSubBusinessObject())
			{
				request.setAttribute(bo.getBeanAttribute().getBeanInstanceName()+"Vt", bo.getViewText());
			}
		}
		return new ModelAndView("XDSS/budget/maindata/BudgetTemplate/budgetTemplateAdd");
	}

}