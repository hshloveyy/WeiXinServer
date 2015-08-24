/*
 * @(#)PurchaseInfoControllerGen.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年01月09日 11点03分53秒
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
import com.infolion.sample.purchase.domain.PurchaseInfo;
import com.infolion.sample.purchase.service.PurchaseInfoService;
          
import com.infolion.sample.purchase.domain.PurchaseRows;
import com.infolion.sample.purchase.service.PurchaseRowsService;
import com.infolion.platform.bo.domain.BusinessObject;
import com.infolion.platform.bo.service.BusinessObjectService;
import com.infolion.platform.dpframework.core.web.BaseMultiActionController;
import com.infolion.platform.dpframework.core.web.AbstractGenController;

/**
 * <pre>
 * 采购订单(SAP)(PurchaseInfo)控制器类
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
public class PurchaseInfoControllerGen extends AbstractGenController
{
	private final String boName = "PurchaseInfo";

	public String getBoName()
	{
		return this.boName;
	}
	
	@Autowired
	protected PurchaseInfoService purchaseInfoService;
	
	public void setPurchaseInfoService(PurchaseInfoService purchaseInfoService)
	{
		this.purchaseInfoService = purchaseInfoService;
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
		PurchaseInfo purchaseInfo = new PurchaseInfo();
		String id = request.getParameter("purchaseinfoId");
		String workflowTaskId = request.getParameter("workflowTaskId");
		String businessId = request.getParameter("businessId");	
		if (null == id)
			id = businessId ;
		purchaseInfo = this.purchaseInfoService._getForEdit(id);
		boolean haveBindWorkFlow = WorkflowService.getHaveBindWorkFlow("000000000184");
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
		request.setAttribute("purchaseInfo", purchaseInfo);
		return new ModelAndView("sample/purchase/purchaseInfoEdit");
	}
	
	/**
	 * 创建  
	 *   
	 * @param request
	 * @param response
	 */
	public ModelAndView _create(HttpServletRequest request, HttpServletResponse response)
	{
	    PurchaseInfo purchaseInfo = new PurchaseInfo();
    	purchaseInfo.setPincr("1");
    	purchaseInfo.setTradeType("1");
    	purchaseInfo.setZtermName("1");
    	purchaseInfo.setInvoicingName("1");
    	purchaseInfo.setInco1Name("1");
    	purchaseInfo.setInco2("1");
    	purchaseInfo.setWaersName("1");
    	purchaseInfo.setContractgroupId("1");
    	purchaseInfo.setProjectId("1");
    	purchaseInfo.setPayer("1");
		request.setAttribute("vt", getBusinessObject().getViewText());
        if(null!=getBusinessObject().getSubBusinessObject())
        {
			// 取得子业务对象
			for (BusinessObject bo : getBusinessObject().getSubBusinessObject())
			{
				request.setAttribute(bo.getBeanAttribute().getBeanInstanceName()+"Vt", bo.getViewText());
			}
		}
		request.setAttribute("purchaseInfo",purchaseInfo);

	    return new ModelAndView("sample/purchase/purchaseInfoAdd");
	}
	
	
	/**
	 * 复制创建  
	 *   
	 * @param request
	 * @param response
	 */
	public ModelAndView _copyCreate(HttpServletRequest request, HttpServletResponse response)
	{
		String id = request.getParameter("purchaseinfoId");
		PurchaseInfo purchaseInfo = this.purchaseInfoService._getEntityCopy(id);
		request.setAttribute("isCreateCopy", "true");
		request.setAttribute("purchaseInfo", purchaseInfo);
		request.setAttribute("vt", getBusinessObject().getViewText());
        if(null!=getBusinessObject().getSubBusinessObject())
        {
			// 取得子业务对象
			for (BusinessObject bo : getBusinessObject().getSubBusinessObject())
			{
				request.setAttribute(bo.getBeanAttribute().getBeanInstanceName()+"Vt", bo.getViewText());
			}
		}
		return new ModelAndView("sample/purchase/purchaseInfoAdd");
	}

	
	/**
	 * 删除  
	 *   
	 * @param request
	 * @param response
	 */
	public void _delete(HttpServletRequest request, HttpServletResponse response)
	{
		String purchaseinfoId = request.getParameter("purchaseinfoId");
		purchaseInfoService._delete(purchaseinfoId,getBusinessObject());
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
		String purchaseInfoIds = request.getParameter("purchaseInfoIds");
		purchaseInfoService._deletes(purchaseInfoIds,getBusinessObject());
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
		PurchaseInfo purchaseInfo = new PurchaseInfo();
		String workflowTaskId = request.getParameter("workflowTaskId");
		String id = request.getParameter("purchaseinfoId");
		String businessId = request.getParameter("businessId");	
		
		if (null == id)
			id = businessId ;
			
		if(StringUtils.isNullBlank(businessId))
        {	
		   purchaseInfo = this.purchaseInfoService._get(id);
	}
        else
        {
           purchaseInfo = this.purchaseInfoService._get(id);
        }
    
      	boolean haveBindWorkFlow = WorkflowService.getHaveBindWorkFlow("000000000184");
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
		
		request.setAttribute("purchaseInfo", purchaseInfo);  
		return new ModelAndView("sample/purchase/purchaseInfoView");
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
		PurchaseInfo purchaseInfo = (PurchaseInfo) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), PurchaseInfo.class , true , request.getMethod(), true);
// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
Set<PurchaseRows> purchaseRowsmodifyItems = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { purchaseInfo }, PurchaseRows.class, null);
Set<PurchaseRows> deletedPurchaseRowsSet = ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(), new Object[] { purchaseInfo }, PurchaseRows.class, null);
purchaseInfo.setPurchaseRows(purchaseRowsmodifyItems);
this.purchaseInfoService._submitProcess(purchaseInfo
,deletedPurchaseRowsSet		,getBusinessObject());
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
		return new ModelAndView("sample/purchase/purchaseInfoManage");
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
		PurchaseInfo purchaseInfo  = new PurchaseInfo();
		String purchaseinfoId = request.getParameter("purchaseinfoId");
		purchaseInfo.setPurchaseinfoId(purchaseinfoId);
		LockService.unLockBOData(purchaseInfo);
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
		PurchaseInfo purchaseInfo = (PurchaseInfo) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), PurchaseInfo.class , true , request.getMethod(), true);
// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
Set<PurchaseRows> purchaseRowsmodifyItems = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { purchaseInfo }, PurchaseRows.class, null);
purchaseInfo.setPurchaseRows(purchaseRowsmodifyItems);
Set<PurchaseRows> purchaseRowsdeleteItems = ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(), new Object[] { purchaseInfo }, PurchaseRows.class, null);
this.purchaseInfoService._saveOrUpdate(purchaseInfo
,purchaseRowsdeleteItems,getBusinessObject());
		jo.put("purchaseinfoId", purchaseInfo.getPurchaseinfoId());
String creator = purchaseInfo.getCreator();
String creator_text = SysCachePoolUtils.getDictDataValue("YUSER", creator);
jo.put("creator_text", creator_text);
jo.put("creator", creator);
jo.put("createTime", purchaseInfo.getCreateTime());
jo.put("lastmodifyTime", purchaseInfo.getLastmodifyTime());
String lastmodifyer = purchaseInfo.getLastmodifyer();
String lastmodifyer_text = SysCachePoolUtils.getDictDataValue("YUSER", lastmodifyer);
jo.put("lastmodifyer_text", lastmodifyer_text);
jo.put("lastmodifyer", lastmodifyer);		this.operateSuccessfullyWithString(response,jo.toString());
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
}