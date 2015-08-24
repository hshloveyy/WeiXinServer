/*
 * @(#)BudgetOrgTempControllerGen.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年03月05日 13点59分06秒
 *  描　述：创建
 */
package com.infolion.XDSS.budget.maindata.BudgetOrgTempGen.web;


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
import com.infolion.XDSS.budget.maindata.BudgetOrgTemp.domain.BudgetOrgTemp;
import com.infolion.XDSS.budget.maindata.BudgetOrgTemp.service.BudgetOrgTempService;
import com.infolion.platform.bo.domain.BusinessObject;
import com.infolion.platform.bo.service.BusinessObjectService;
import com.infolion.platform.dpframework.core.web.BaseMultiActionController;
import com.infolion.platform.dpframework.core.web.AbstractGenController;

/**
 * <pre>
 * 预算组织模版(BudgetOrgTemp)控制器类
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
public class BudgetOrgTempControllerGen extends AbstractGenController
{
	private final String boName = "BudgetOrgTemp";

	public String getBoName()
	{
		return this.boName;
	}
	
	@Autowired
	protected BudgetOrgTempService budgetOrgTempService;
	
	public void setBudgetOrgTempService(BudgetOrgTempService budgetOrgTempService)
	{
		this.budgetOrgTempService = budgetOrgTempService;
	}
	
	/**
	 * 编辑  
	 *   
	 * @param request
	 * @param response
	 */
	public ModelAndView _edit(HttpServletRequest request, HttpServletResponse response)
	{
		BudgetOrgTemp budgetOrgTemp = new BudgetOrgTemp();
	    budgetOrgTemp = (BudgetOrgTemp) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), BudgetOrgTemp.class , false, request.getMethod(), false);
		request.setAttribute("vt", getBusinessObject().getViewText());
		request.setAttribute("budgetOrgTemp", budgetOrgTemp);
		return new ModelAndView("XDSS/budget/maindata/BudgetOrgTemp/budgetOrgTempEdit");
	}
	
	/**
	 * 创建  
	 *   
	 * @param request
	 * @param response
	 */
	public ModelAndView _create(HttpServletRequest request, HttpServletResponse response)
	{
	    BudgetOrgTemp budgetOrgTemp = new BudgetOrgTemp();
		request.setAttribute("vt", getBusinessObject().getViewText());
		request.setAttribute("budgetOrgTemp",budgetOrgTemp);

	    return new ModelAndView("XDSS/budget/maindata/BudgetOrgTemp/budgetOrgTempAdd");
	}
	
	
	/**
	 * 复制创建  
	 *   
	 * @param request
	 * @param response
	 */
	public ModelAndView _copyCreate(HttpServletRequest request, HttpServletResponse response)
	{
		BudgetOrgTemp budgetOrgTemp = new BudgetOrgTemp ();
	    budgetOrgTemp = (BudgetOrgTemp) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), BudgetOrgTemp.class , false , request.getMethod(), false);
		request.setAttribute("budgetOrgTemp", budgetOrgTemp);
		request.setAttribute("vt", getBusinessObject().getViewText());
		return new ModelAndView("XDSS/budget/maindata/BudgetOrgTemp/budgetOrgTempAdd");
	}

	
	/**
	 * 删除  
	 *   
	 * @param request
	 * @param response
	 */
	public void _delete(HttpServletRequest request, HttpServletResponse response)
	{
		String budorgtemid = request.getParameter("budorgtemid");
		budgetOrgTempService._delete(budorgtemid,getBusinessObject());
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
		String budgetOrgTempIds = request.getParameter("budgetOrgTempIds");
		budgetOrgTempService._deletes(budgetOrgTempIds,getBusinessObject());
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
		BudgetOrgTemp budgetOrgTemp = new BudgetOrgTemp();
	    budgetOrgTemp = (BudgetOrgTemp) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), BudgetOrgTemp.class , false , request.getMethod(), false);
		request.setAttribute("vt", getBusinessObject().getViewText());
		
		request.setAttribute("budgetOrgTemp", budgetOrgTemp);  
		return new ModelAndView("XDSS/budget/maindata/BudgetOrgTemp/budgetOrgTempView");
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
		BudgetOrgTemp budgetOrgTemp = (BudgetOrgTemp) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), BudgetOrgTemp.class , true , request.getMethod(), true);
this.budgetOrgTempService._submitProcess(budgetOrgTemp		,getBusinessObject());
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
		return new ModelAndView("XDSS/budget/maindata/BudgetOrgTemp/budgetOrgTempManage");
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
		BudgetOrgTemp budgetOrgTemp  = new BudgetOrgTemp();
		String budorgtemid = request.getParameter("budorgtemid");
		budgetOrgTemp.setBudorgtemid(budorgtemid);
		LockService.unLockBOData(budgetOrgTemp);
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
		BudgetOrgTemp budgetOrgTemp = (BudgetOrgTemp) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), BudgetOrgTemp.class , true , request.getMethod(), true);
this.budgetOrgTempService._saveOrUpdate(budgetOrgTemp,getBusinessObject());
		jo.put("budorgtemid", budgetOrgTemp.getBudorgtemid());
String creator = budgetOrgTemp.getCreator();
String creator_text = SysCachePoolUtils.getDictDataValue("YUSER", creator);
jo.put("creator_text", creator_text);
jo.put("creator", creator);
jo.put("createtime", budgetOrgTemp.getCreatetime());
String lastmodifyer = budgetOrgTemp.getLastmodifyer();
String lastmodifyer_text = SysCachePoolUtils.getDictDataValue("YUSER", lastmodifyer);
jo.put("lastmodifyer_text", lastmodifyer_text);
jo.put("lastmodifyer", lastmodifyer);
jo.put("lastmodifytime", budgetOrgTemp.getLastmodifytime());		this.operateSuccessfullyWithString(response,jo.toString());
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
}