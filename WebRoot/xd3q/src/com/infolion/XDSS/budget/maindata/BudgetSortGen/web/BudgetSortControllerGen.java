/*
 * @(#)BudgetSortControllerGen.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年03月04日 13点22分32秒
 *  描　述：创建
 */
package com.infolion.XDSS.budget.maindata.BudgetSortGen.web;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;
import java.util.Set;
import java.io.IOException;
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
import com.infolion.XDSS.budget.maindata.BudgetSort.domain.BudgetSort;
import com.infolion.XDSS.budget.maindata.BudgetSort.service.BudgetSortService;
          
import com.infolion.XDSS.budget.maindata.BudgetTemplate.domain.BudgetTemplate;
import com.infolion.XDSS.budget.maindata.BudgetTemplate.service.BudgetTemplateService;
import com.infolion.platform.bo.domain.BusinessObject;
import com.infolion.platform.bo.service.BusinessObjectService;
import com.infolion.platform.dpframework.core.web.BaseMultiActionController;
import com.infolion.platform.dpframework.core.web.AbstractGenController;

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
public class BudgetSortControllerGen extends AbstractGenController
{
	private final String boName = "BudgetSort";

	public String getBoName()
	{
		return this.boName;
	}
	
	@Autowired
	protected BudgetSortService budgetSortService;
	
	public void setBudgetSortService(BudgetSortService budgetSortService)
	{
		this.budgetSortService = budgetSortService;
	}
	
	
	/**
	 * 得到节点信息
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void getBudSort(HttpServletRequest request, HttpServletResponse response) 
	throws IOException
	{
		String budsortid = request.getParameter("budsortid");
		BudgetSort budgetSort = new BudgetSort();
		budgetSort = this.budgetSortService._get(budsortid);
		
		JSONObject jo = new JSONObject();
		
		jo.put("budsortid", budgetSort.getBudsortid());
		jo.put("client", budgetSort.getClient());
		jo.put("budupsortid", budgetSort.getBudupsortid());
		jo.put("budsortname", budgetSort.getBudsortname());
		jo.put("budsortdesc", budgetSort.getBudsortdesc());
		jo.put("boid", budgetSort.getBoid());
		//jo.put("ischange", budgetSort.getIschange());
		String creator = budgetSort.getCreator();
		String creator_text = SysCachePoolUtils.getDictDataValue("YUSER", creator);
		jo.put("creator_text", creator_text);
		jo.put("creator", creator);
		jo.put("createtime", budgetSort.getCreatetime());
		String lastmodifyer = budgetSort.getLastmodifyer();
		String lastmodifyer_text = SysCachePoolUtils.getDictDataValue("YUSER", lastmodifyer);
		jo.put("lastmodifyer_text", lastmodifyer_text);
		jo.put("lastmodifyer", lastmodifyer);
		jo.put("lastmodifytime", budgetSort.getLastmodifytime());		

		
		String jsontext = jo.toString();
		System.out.println(jsontext);
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		response.getWriter().print(jsontext);
	}
          
	
	/**
	 * 删除  
	 *   
	 * @param request
	 * @param response
	 */
	public void _delete(HttpServletRequest request, HttpServletResponse response)
	{
		String budsortid = request.getParameter("budsortid");
		budgetSortService._delete(budsortid,getBusinessObject());
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
		String budgetSortIds = request.getParameter("budgetSortIds");
		budgetSortService._deletes(budgetSortIds,getBusinessObject());
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
		BudgetSort budgetSort = new BudgetSort();
		String workflowTaskId = request.getParameter("workflowTaskId");
		String id = request.getParameter("budsortid");
		String businessId = request.getParameter("businessId");	
		
		if (null == id)
			id = businessId ;
			
		if(StringUtils.isNullBlank(businessId))
        {	
		   budgetSort = this.budgetSortService._get(id);
	}
        else
        {
           budgetSort = this.budgetSortService._get(id);
        }
    
      	boolean haveBindWorkFlow = WorkflowService.getHaveBindWorkFlow("000000000192");
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
		
		request.setAttribute("budgetSort", budgetSort);  
		return new ModelAndView("XDSS/budget/maindata/BudgetSort/budgetSortView");
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
		BudgetSort budgetSort = (BudgetSort) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), BudgetSort.class , true , request.getMethod(), true);
// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
Set<BudgetTemplate> budgetTemplatemodifyItems = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { budgetSort }, BudgetTemplate.class, null);
Set<BudgetTemplate> deletedBudgetTemplateSet = ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(), new Object[] { budgetSort }, BudgetTemplate.class, null);
budgetSort.setBudgetTemplate(budgetTemplatemodifyItems);
this.budgetSortService._submitProcess(budgetSort
,deletedBudgetTemplateSet		,getBusinessObject());
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
		return new ModelAndView("XDSS/budget/maindata/BudgetSort/budgetSortManage");
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
		BudgetSort budgetSort  = new BudgetSort();
		String budsortid = request.getParameter("budsortid");
		budgetSort.setBudsortid(budsortid);
		LockService.unLockBOData(budgetSort);
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
		BudgetSort budgetSort = (BudgetSort) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), BudgetSort.class , true , request.getMethod(), true);
// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
Set<BudgetTemplate> budgetTemplatemodifyItems = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { budgetSort }, BudgetTemplate.class, null);
budgetSort.setBudgetTemplate(budgetTemplatemodifyItems);
Set<BudgetTemplate> budgetTemplatedeleteItems = ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(), new Object[] { budgetSort }, BudgetTemplate.class, null);
this.budgetSortService._saveOrUpdate(budgetSort
,budgetTemplatedeleteItems,getBusinessObject());
		jo.put("budsortid", budgetSort.getBudsortid());
String creator = budgetSort.getCreator();
String creator_text = SysCachePoolUtils.getDictDataValue("YUSER", creator);
jo.put("creator_text", creator_text);
jo.put("creator", creator);
jo.put("createtime", budgetSort.getCreatetime());
String lastmodifyer = budgetSort.getLastmodifyer();
String lastmodifyer_text = SysCachePoolUtils.getDictDataValue("YUSER", lastmodifyer);
jo.put("lastmodifyer_text", lastmodifyer_text);
jo.put("lastmodifyer", lastmodifyer);
jo.put("lastmodifytime", budgetSort.getLastmodifytime());		this.operateSuccessfullyWithString(response,jo.toString());
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
		BudgetSort budgetSort = new BudgetSort();
		String id = request.getParameter("budsortid");
		String workflowTaskId = request.getParameter("workflowTaskId");
		String businessId = request.getParameter("businessId");	
		if (null == id)
			id = businessId ;
		budgetSort = this.budgetSortService._getForEdit(id);
		boolean haveBindWorkFlow = WorkflowService.getHaveBindWorkFlow("000000000192");
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
		request.setAttribute("budgetSort", budgetSort);
		return new ModelAndView("XDSS/budget/maindata/BudgetSort/budgetSortEdit");
	}
	
	/**
	 * 创建  
	 *   
	 * @param request
	 * @param response
	 */
	public ModelAndView _create(HttpServletRequest request, HttpServletResponse response)
	{
	    BudgetSort budgetSort = new BudgetSort();
		request.setAttribute("vt", getBusinessObject().getViewText());
        if(null!=getBusinessObject().getSubBusinessObject())
        {
			// 取得子业务对象
			for (BusinessObject bo : getBusinessObject().getSubBusinessObject())
			{
				request.setAttribute(bo.getBeanAttribute().getBeanInstanceName()+"Vt", bo.getViewText());
			}
		}
		request.setAttribute("budgetSort",budgetSort);

	    return new ModelAndView("XDSS/budget/maindata/BudgetSort/budgetSortAdd");
	}
	
	
	/**
	 * 复制创建  
	 *   
	 * @param request
	 * @param response
	 */
	public ModelAndView _copyCreate(HttpServletRequest request, HttpServletResponse response)
	{
		String id = request.getParameter("budsortid");
		BudgetSort budgetSort = this.budgetSortService._getEntityCopy(id);
		request.setAttribute("isCreateCopy", "true");
		request.setAttribute("budgetSort", budgetSort);
		request.setAttribute("vt", getBusinessObject().getViewText());
        if(null!=getBusinessObject().getSubBusinessObject())
        {
			// 取得子业务对象
			for (BusinessObject bo : getBusinessObject().getSubBusinessObject())
			{
				request.setAttribute(bo.getBeanAttribute().getBeanInstanceName()+"Vt", bo.getViewText());
			}
		}
		return new ModelAndView("XDSS/budget/maindata/BudgetSort/budgetSortAdd");
	}

}