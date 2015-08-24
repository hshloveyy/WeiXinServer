/*
 * @(#)BudgetItemGroupControllerGen.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年03月05日 17点03分34秒
 *  描　述：创建
 */
package com.infolion.XDSS.budget.maindata.BudgetItemGroupGen.web;


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
import com.infolion.XDSS.budget.maindata.BudgetItemGroup.domain.BudgetItemGroup;
import com.infolion.XDSS.budget.maindata.BudgetItemGroup.service.BudgetItemGroupService;
          
import com.infolion.XDSS.budget.maindata.BudgetItem.domain.BudgetItem;
import com.infolion.XDSS.budget.maindata.BudgetItem.service.BudgetItemService;
import com.infolion.platform.bo.domain.BusinessObject;
import com.infolion.platform.bo.service.BusinessObjectService;
import com.infolion.platform.dpframework.core.web.BaseMultiActionController;
import com.infolion.platform.dpframework.core.web.AbstractGenController;

/**
 * <pre>
 * 预算项分组(BudgetItemGroup)控制器类
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
public class BudgetItemGroupControllerGen extends AbstractGenController
{
	private final String boName = "BudgetItemGroup";

	public String getBoName()
	{
		return this.boName;
	}
	
	@Autowired
	protected BudgetItemGroupService budgetItemGroupService;
	
	public void setBudgetItemGroupService(BudgetItemGroupService budgetItemGroupService)
	{
		this.budgetItemGroupService = budgetItemGroupService;
	}
    
	/**
	 * 得到节点信息
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void getBudGroup(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		String budgroupid = request.getParameter("budgroupid");
		BudgetItemGroup budgetItemGroup = new BudgetItemGroup();
		budgetItemGroup = this.budgetItemGroupService._get(budgroupid);
		
		JSONObject jo = new JSONObject();
		
		jo.put("budgroupid", budgetItemGroup.getBudgroupid());
		jo.put("client", budgetItemGroup.getClient());
		jo.put("budupgroupid", budgetItemGroup.getBudupgroupid());
		jo.put("budgroupname", budgetItemGroup.getBudgroupname());
		jo.put("budgroupdesc", budgetItemGroup.getBudgroupdesc());
		String creator = budgetItemGroup.getCreator();
		String creator_text = SysCachePoolUtils.getDictDataValue("YUSER", creator);
		jo.put("creator_text", creator_text);
		jo.put("creator", creator);
		jo.put("createtime", budgetItemGroup.getCreatetime());
		String lastmodifyer = budgetItemGroup.getLastmodifyer();
		String lastmodifyer_text = SysCachePoolUtils.getDictDataValue("YUSER", lastmodifyer);
		jo.put("lastmodifyer_text", lastmodifyer_text);
		jo.put("lastmodifyer", lastmodifyer);
		jo.put("lastmodifytime", budgetItemGroup.getLastmodifytime());		

		
		String jsontext = jo.toString();
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
		String budgroupid = request.getParameter("budgroupid");
		budgetItemGroupService._delete(budgroupid,getBusinessObject());
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
		String budgetItemGroupIds = request.getParameter("budgetItemGroupIds");
		budgetItemGroupService._deletes(budgetItemGroupIds,getBusinessObject());
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
		BudgetItemGroup budgetItemGroup = new BudgetItemGroup();
		String workflowTaskId = request.getParameter("workflowTaskId");
		String id = request.getParameter("budgroupid");
		String businessId = request.getParameter("businessId");	
		
		if (null == id)
			id = businessId ;
			
		if(StringUtils.isNullBlank(businessId))
        {	
		   budgetItemGroup = this.budgetItemGroupService._get(id);
	}
        else
        {
           budgetItemGroup = this.budgetItemGroupService._get(id);
        }
    
      	boolean haveBindWorkFlow = WorkflowService.getHaveBindWorkFlow("000000000193");
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
		
		request.setAttribute("budgetItemGroup", budgetItemGroup);  
		return new ModelAndView("XDSS/budget/maindata/BudgetItemGroup/budgetItemGroupView");
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
		BudgetItemGroup budgetItemGroup = (BudgetItemGroup) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), BudgetItemGroup.class , true , request.getMethod(), true);
// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
Set<BudgetItem> budgetItemmodifyItems = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { budgetItemGroup }, BudgetItem.class, null);
Set<BudgetItem> deletedBudgetItemSet = ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(), new Object[] { budgetItemGroup }, BudgetItem.class, null);
budgetItemGroup.setBudgetItem(budgetItemmodifyItems);
this.budgetItemGroupService._submitProcess(budgetItemGroup
,deletedBudgetItemSet		,getBusinessObject());
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
		return new ModelAndView("XDSS/budget/maindata/BudgetItemGroup/budgetItemGroupManage");
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
		BudgetItemGroup budgetItemGroup  = new BudgetItemGroup();
		String budgroupid = request.getParameter("budgroupid");
		budgetItemGroup.setBudgroupid(budgroupid);
		LockService.unLockBOData(budgetItemGroup);
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
		BudgetItemGroup budgetItemGroup = (BudgetItemGroup) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), BudgetItemGroup.class , true , request.getMethod(), true);
// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
Set<BudgetItem> budgetItemmodifyItems = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { budgetItemGroup }, BudgetItem.class, null);
budgetItemGroup.setBudgetItem(budgetItemmodifyItems);
Set<BudgetItem> budgetItemdeleteItems = ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(), new Object[] { budgetItemGroup }, BudgetItem.class, null);
this.budgetItemGroupService._saveOrUpdate(budgetItemGroup
,budgetItemdeleteItems,getBusinessObject());
		jo.put("budgroupid", budgetItemGroup.getBudgroupid());
String creator = budgetItemGroup.getCreator();
String creator_text = SysCachePoolUtils.getDictDataValue("YUSER", creator);
jo.put("creator_text", creator_text);
jo.put("creator", creator);
jo.put("createtime", budgetItemGroup.getCreatetime());
String lastmodifyer = budgetItemGroup.getLastmodifyer();
String lastmodifyer_text = SysCachePoolUtils.getDictDataValue("YUSER", lastmodifyer);
jo.put("lastmodifyer_text", lastmodifyer_text);
jo.put("lastmodifyer", lastmodifyer);
jo.put("lastmodifytime", budgetItemGroup.getLastmodifytime());		this.operateSuccessfullyWithString(response,jo.toString());
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
		BudgetItemGroup budgetItemGroup = new BudgetItemGroup();
		String id = request.getParameter("budgroupid");
		String workflowTaskId = request.getParameter("workflowTaskId");
		String businessId = request.getParameter("businessId");	
		if (null == id)
			id = businessId ;
		budgetItemGroup = this.budgetItemGroupService._getForEdit(id);
		boolean haveBindWorkFlow = WorkflowService.getHaveBindWorkFlow("000000000193");
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
		request.setAttribute("budgetItemGroup", budgetItemGroup);
		return new ModelAndView("XDSS/budget/maindata/BudgetItemGroup/budgetItemGroupEdit");
	}
	
	/**
	 * 创建  
	 *   
	 * @param request
	 * @param response
	 */
	public ModelAndView _create(HttpServletRequest request, HttpServletResponse response)
	{
	    BudgetItemGroup budgetItemGroup = new BudgetItemGroup();
		request.setAttribute("vt", getBusinessObject().getViewText());
        if(null!=getBusinessObject().getSubBusinessObject())
        {
			// 取得子业务对象
			for (BusinessObject bo : getBusinessObject().getSubBusinessObject())
			{
				request.setAttribute(bo.getBeanAttribute().getBeanInstanceName()+"Vt", bo.getViewText());
			}
		}
		request.setAttribute("budgetItemGroup",budgetItemGroup);

	    return new ModelAndView("XDSS/budget/maindata/BudgetItemGroup/budgetItemGroupAdd");
	}
	
	
	/**
	 * 复制创建  
	 *   
	 * @param request
	 * @param response
	 */
	public ModelAndView _copyCreate(HttpServletRequest request, HttpServletResponse response)
	{
		String id = request.getParameter("budgroupid");
		BudgetItemGroup budgetItemGroup = this.budgetItemGroupService._getEntityCopy(id);
		request.setAttribute("isCreateCopy", "true");
		request.setAttribute("budgetItemGroup", budgetItemGroup);
		request.setAttribute("vt", getBusinessObject().getViewText());
        if(null!=getBusinessObject().getSubBusinessObject())
        {
			// 取得子业务对象
			for (BusinessObject bo : getBusinessObject().getSubBusinessObject())
			{
				request.setAttribute(bo.getBeanAttribute().getBeanInstanceName()+"Vt", bo.getViewText());
			}
		}
		return new ModelAndView("XDSS/budget/maindata/BudgetItemGroup/budgetItemGroupAdd");
	}

}