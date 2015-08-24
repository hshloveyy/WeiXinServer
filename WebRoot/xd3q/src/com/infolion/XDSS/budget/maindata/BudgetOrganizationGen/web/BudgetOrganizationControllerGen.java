/*
 * @(#)BudgetOrganizationControllerGen.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年03月05日 13点59分04秒
 *  描　述：创建
 */
package com.infolion.XDSS.budget.maindata.BudgetOrganizationGen.web;


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
import com.infolion.XDSS.budget.maindata.BudgetOrganization.domain.BudgetOrganization;
import com.infolion.XDSS.budget.maindata.BudgetOrganization.service.BudgetOrganizationService;
          
import com.infolion.XDSS.budget.maindata.BudgetOrgTemp.domain.BudgetOrgTemp;
import com.infolion.XDSS.budget.maindata.BudgetOrgTemp.service.BudgetOrgTempService;
import com.infolion.XDSS.budget.maindata.BudgetSort.domain.BudgetSort;
import com.infolion.platform.bo.domain.BusinessObject;
import com.infolion.platform.bo.service.BusinessObjectService;
import com.infolion.platform.dpframework.core.web.BaseMultiActionController;
import com.infolion.platform.dpframework.core.web.AbstractGenController;

/**
 * <pre>
 * 预算组织(BudgetOrganization)控制器类
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
public class BudgetOrganizationControllerGen extends AbstractGenController
{
	private final String boName = "BudgetOrganization";

	public String getBoName()
	{
		return this.boName;
	}
	
	@Autowired
	protected BudgetOrganizationService budgetOrganizationService;
	
	public void setBudgetOrganizationService(BudgetOrganizationService budgetOrganizationService)
	{
		this.budgetOrganizationService = budgetOrganizationService;
	}
	
	/**
	 * 得到节点信息
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void getBudOrg(HttpServletRequest request, HttpServletResponse response) 
	throws IOException
	{
		String budorgid = request.getParameter("budorgid");
		BudgetOrganization budgetOrganization = new BudgetOrganization();
		budgetOrganization = this.budgetOrganizationService._get(budorgid);
		
		JSONObject jo = new JSONObject();
		
		jo.put("budorgid", budgetOrganization.getBudorgid());
		jo.put("client", budgetOrganization.getClient());
		jo.put("buduporgid", budgetOrganization.getBuduporgid());
		jo.put("budorgname", budgetOrganization.getBudorgname());
		jo.put("budorgtype", budgetOrganization.getBudorgtype());
		jo.put("budcontype", budgetOrganization.getBudcontype());
		jo.put("companycode", budgetOrganization.getCompanycode());
		jo.put("deptid", budgetOrganization.getDeptid());
		jo.put("costcenter", budgetOrganization.getCostcenter());
		jo.put("status", budgetOrganization.getStatus());
		
		String creator = budgetOrganization.getCreator();
		String creator_text = SysCachePoolUtils.getDictDataValue("YUSER", creator);
		jo.put("creator_text", creator_text);
		jo.put("creator", creator);
		jo.put("createtime", budgetOrganization.getCreatetime());
		String lastmodifyer = budgetOrganization.getLastmodifyer();
		String lastmodifyer_text = SysCachePoolUtils.getDictDataValue("YUSER", lastmodifyer);
		jo.put("lastmodifyer_text", lastmodifyer_text);
		jo.put("lastmodifyer", lastmodifyer);
		jo.put("lastmodifytime", budgetOrganization.getLastmodifytime());		

		
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
		String budorgid = request.getParameter("budorgid");
		budgetOrganizationService._delete(budorgid,getBusinessObject());
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
		String budgetOrganizationIds = request.getParameter("budgetOrganizationIds");
		budgetOrganizationService._deletes(budgetOrganizationIds,getBusinessObject());
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
		BudgetOrganization budgetOrganization = new BudgetOrganization();
		String workflowTaskId = request.getParameter("workflowTaskId");
		String id = request.getParameter("budorgid");
		String businessId = request.getParameter("businessId");	
		
		if (null == id)
			id = businessId ;
			
		if(StringUtils.isNullBlank(businessId))
        {	
		   budgetOrganization = this.budgetOrganizationService._get(id);
	}
        else
        {
           budgetOrganization = this.budgetOrganizationService._get(id);
        }
    
      	boolean haveBindWorkFlow = WorkflowService.getHaveBindWorkFlow("000000000198");
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
		
		request.setAttribute("budgetOrganization", budgetOrganization);  
		return new ModelAndView("XDSS/budget/maindata/BudgetOrganization/budgetOrganizationView");
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
		BudgetOrganization budgetOrganization = (BudgetOrganization) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), BudgetOrganization.class , true , request.getMethod(), true);
// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
Set<BudgetOrgTemp> budgetOrgTempmodifyItems = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { budgetOrganization }, BudgetOrgTemp.class, null);
Set<BudgetOrgTemp> deletedBudgetOrgTempSet = ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(), new Object[] { budgetOrganization }, BudgetOrgTemp.class, null);
budgetOrganization.setBudgetOrgTemp(budgetOrgTempmodifyItems);
this.budgetOrganizationService._submitProcess(budgetOrganization
,deletedBudgetOrgTempSet		,getBusinessObject());
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
		return new ModelAndView("XDSS/budget/maindata/BudgetOrganization/budgetOrganizationManage");
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
		BudgetOrganization budgetOrganization  = new BudgetOrganization();
		String budorgid = request.getParameter("budorgid");
		budgetOrganization.setBudorgid(budorgid);
		LockService.unLockBOData(budgetOrganization);
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
		BudgetOrganization budgetOrganization = (BudgetOrganization) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), BudgetOrganization.class , true , request.getMethod(), true);
// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
Set<BudgetOrgTemp> budgetOrgTempmodifyItems = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { budgetOrganization }, BudgetOrgTemp.class, null);
budgetOrganization.setBudgetOrgTemp(budgetOrgTempmodifyItems);
Set<BudgetOrgTemp> budgetOrgTempdeleteItems = ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(), new Object[] { budgetOrganization }, BudgetOrgTemp.class, null);
this.budgetOrganizationService._saveOrUpdate(budgetOrganization
,budgetOrgTempdeleteItems,getBusinessObject());
		jo.put("budorgid", budgetOrganization.getBudorgid());
String creator = budgetOrganization.getCreator();
String creator_text = SysCachePoolUtils.getDictDataValue("YUSER", creator);
jo.put("creator_text", creator_text);
jo.put("creator", creator);
jo.put("createtime", budgetOrganization.getCreatetime());
String lastmodifyer = budgetOrganization.getLastmodifyer();
String lastmodifyer_text = SysCachePoolUtils.getDictDataValue("YUSER", lastmodifyer);
jo.put("lastmodifyer_text", lastmodifyer_text);
jo.put("lastmodifyer", lastmodifyer);
jo.put("lastmodifytime", budgetOrganization.getLastmodifytime());		this.operateSuccessfullyWithString(response,jo.toString());
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
		BudgetOrganization budgetOrganization = new BudgetOrganization();
		String id = request.getParameter("budorgid");
		String workflowTaskId = request.getParameter("workflowTaskId");
		String businessId = request.getParameter("businessId");	
		if (null == id)
			id = businessId ;
		budgetOrganization = this.budgetOrganizationService._getForEdit(id);
		boolean haveBindWorkFlow = WorkflowService.getHaveBindWorkFlow("000000000198");
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
		request.setAttribute("budgetOrganization", budgetOrganization);
		return new ModelAndView("XDSS/budget/maindata/BudgetOrganization/budgetOrganizationEdit");
	}
	
	/**
	 * 创建  
	 *   
	 * @param request
	 * @param response
	 */
	public ModelAndView _create(HttpServletRequest request, HttpServletResponse response)
	{
	    BudgetOrganization budgetOrganization = new BudgetOrganization();
		request.setAttribute("vt", getBusinessObject().getViewText());
        if(null!=getBusinessObject().getSubBusinessObject())
        {
			// 取得子业务对象
			for (BusinessObject bo : getBusinessObject().getSubBusinessObject())
			{
				request.setAttribute(bo.getBeanAttribute().getBeanInstanceName()+"Vt", bo.getViewText());
			}
		}
		request.setAttribute("budgetOrganization",budgetOrganization);

	    return new ModelAndView("XDSS/budget/maindata/BudgetOrganization/budgetOrganizationAdd");
	}
	
	
	/**
	 * 复制创建  
	 *   
	 * @param request
	 * @param response
	 */
	public ModelAndView _copyCreate(HttpServletRequest request, HttpServletResponse response)
	{
		String id = request.getParameter("budorgid");
		BudgetOrganization budgetOrganization = this.budgetOrganizationService._getEntityCopy(id);
		request.setAttribute("isCreateCopy", "true");
		request.setAttribute("budgetOrganization", budgetOrganization);
		request.setAttribute("vt", getBusinessObject().getViewText());
        if(null!=getBusinessObject().getSubBusinessObject())
        {
			// 取得子业务对象
			for (BusinessObject bo : getBusinessObject().getSubBusinessObject())
			{
				request.setAttribute(bo.getBeanAttribute().getBeanInstanceName()+"Vt", bo.getViewText());
			}
		}
		return new ModelAndView("XDSS/budget/maindata/BudgetOrganization/budgetOrganizationAdd");
	}

}