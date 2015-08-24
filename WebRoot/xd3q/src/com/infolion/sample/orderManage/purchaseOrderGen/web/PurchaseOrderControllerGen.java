/*
 * @(#)PurchaseOrderControllerGen.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年03月31日 15点00分57秒
 *  描　述：创建
 */
package com.infolion.sample.orderManage.purchaseOrderGen.web;


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
import com.infolion.sample.orderManage.purchaseOrder.domain.PurchaseOrder;
import com.infolion.sample.orderManage.purchaseOrder.service.PurchaseOrderService;
          
import com.infolion.sample.orderManage.purchaseOrder.domain.OrderItems;
import com.infolion.sample.orderManage.purchaseOrder.service.OrderItemsService;
          
import com.infolion.platform.dpframework.uicomponent.attachement.domain.Attachement;
import com.infolion.platform.dpframework.uicomponent.attachement.service.AttachementService;
import com.infolion.platform.bo.domain.BusinessObject;
import com.infolion.platform.bo.service.BusinessObjectService;
import com.infolion.platform.dpframework.core.web.BaseMultiActionController;
import com.infolion.platform.dpframework.core.web.AbstractGenController;

/**
 * <pre>
 * 采购订单(PurchaseOrder)控制器类
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
public class PurchaseOrderControllerGen extends AbstractGenController
{
	private final String boName = "PurchaseOrder";

	public String getBoName()
	{
		return this.boName;
	}
	
	@Autowired
	protected PurchaseOrderService purchaseOrderService;
	
	public void setPurchaseOrderService(PurchaseOrderService purchaseOrderService)
	{
		this.purchaseOrderService = purchaseOrderService;
	}
          
          
	@Autowired
	protected AttachementService attachementService;
	
	public void setAttachementService(AttachementService attachementService)
	{
		this.attachementService = attachementService;
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
		PurchaseOrder purchaseOrder = new PurchaseOrder();
		String id = request.getParameter("purchaseOrderId");
		String workflowTaskId = request.getParameter("workflowTaskId");
		String businessId = request.getParameter("businessId");	
		if (null == id)
			id = businessId ;
		purchaseOrder = this.purchaseOrderService._getForEdit(id);
		boolean haveBindWorkFlow = WorkflowService.getHaveBindWorkFlow("000000000006");
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
		request.setAttribute("purchaseOrder", purchaseOrder);
		return new ModelAndView("sample/orderManage/purchaseOrder/purchaseOrderEdit");
	}
	
	/**
	 * 创建  
	 *   
	 * @param request
	 * @param response
	 */
	public ModelAndView _create(HttpServletRequest request, HttpServletResponse response)
	{
	    PurchaseOrder purchaseOrder = new PurchaseOrder();
    	purchaseOrder.setSupplierNo("ALEGHENY");
    	purchaseOrder.setCertificateDate("20090816");
    	purchaseOrder.setSalespeople("卖肉的");
    	purchaseOrder.setAddress("厦门软件园望海路21号福富软件");
    	purchaseOrder.setMemo("不要让我写啥子备注了，TMD。");
	    String aaa = request.getParameter("aaa");
		request.setAttribute("aaa",aaa);
        String bbb = request.getParameter("bbb");
        if(StringUtils.isNotBlank(bbb))
        {
        	purchaseOrder.setPurchaseOrderNo(bbb);
        }
	    String ccc = request.getParameter("ccc");
		request.setAttribute("ccc",ccc);
	    String ddd = request.getParameter("ddd");
		request.setAttribute("ddd",ddd);
        //开始接收从_manage方法传递过来的参数。
        String aaManager = request.getParameter("aa");
        purchaseOrder.setCertificateType(aaManager);
		request.setAttribute("vt", getBusinessObject().getViewText());
        if(null!=getBusinessObject().getSubBusinessObject())
        {
			// 取得子业务对象
			for (BusinessObject bo : getBusinessObject().getSubBusinessObject())
			{
				request.setAttribute(bo.getBeanAttribute().getBeanInstanceName()+"Vt", bo.getViewText());
			}
		}
		request.setAttribute("purchaseOrder",purchaseOrder);

	    return new ModelAndView("sample/orderManage/purchaseOrder/purchaseOrderAdd");
	}
	
	
	/**
	 * 复制创建  
	 *   
	 * @param request
	 * @param response
	 */
	public ModelAndView _copyCreate(HttpServletRequest request, HttpServletResponse response)
	{
		String id = request.getParameter("purchaseOrderId");
		PurchaseOrder purchaseOrder = this.purchaseOrderService._getEntityCopy(id);
		request.setAttribute("isCreateCopy", "true");
		request.setAttribute("purchaseOrder", purchaseOrder);
		request.setAttribute("vt", getBusinessObject().getViewText());
        if(null!=getBusinessObject().getSubBusinessObject())
        {
			// 取得子业务对象
			for (BusinessObject bo : getBusinessObject().getSubBusinessObject())
			{
				request.setAttribute(bo.getBeanAttribute().getBeanInstanceName()+"Vt", bo.getViewText());
			}
		}
		return new ModelAndView("sample/orderManage/purchaseOrder/purchaseOrderAdd");
	}

	
	/**
	 * 删除  
	 *   
	 * @param request
	 * @param response
	 */
	public void _delete(HttpServletRequest request, HttpServletResponse response)
	{
		String purchaseOrderId = request.getParameter("purchaseOrderId");
		purchaseOrderService._delete(purchaseOrderId,getBusinessObject());
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
		String purchaseOrderIds = request.getParameter("purchaseOrderIds");
		purchaseOrderService._deletes(purchaseOrderIds,getBusinessObject());
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
		PurchaseOrder purchaseOrder = new PurchaseOrder();
		String workflowTaskId = request.getParameter("workflowTaskId");
		String id = request.getParameter("purchaseOrderId");
		String businessId = request.getParameter("businessId");	
		
		if (null == id)
			id = businessId ;
			
		if(StringUtils.isNullBlank(businessId))
        {	
		   purchaseOrder = this.purchaseOrderService._get(id);
	}
        else
        {
           purchaseOrder = this.purchaseOrderService._get(id);
        }
    
      	boolean haveBindWorkFlow = WorkflowService.getHaveBindWorkFlow("000000000006");
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
		
		request.setAttribute("purchaseOrder", purchaseOrder);  
		return new ModelAndView("sample/orderManage/purchaseOrder/purchaseOrderView");
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
		PurchaseOrder purchaseOrder = (PurchaseOrder) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), PurchaseOrder.class , true , request.getMethod(), true);
// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
Set<OrderItems> orderItemsmodifyItems = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { purchaseOrder }, OrderItems.class, null);
Set<OrderItems> deletedOrderItemsSet = ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(), new Object[] { purchaseOrder }, OrderItems.class, null);
purchaseOrder.setOrderItems(orderItemsmodifyItems);
this.purchaseOrderService._submitProcess(purchaseOrder
,deletedOrderItemsSet		,getBusinessObject());
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
        StringBuilder sbSqlWhere = new StringBuilder();
        String aa = request.getParameter("aa");
        request.setAttribute("aa",aa);
		if (aa != null && StringUtils.isNotBlank(aa))
		{
			sbSqlWhere.append(" YPURCHASEORDER.CERTIFICATETYPE='" + aa + "'");
	    } 		
		request.setAttribute("sqlWhere", sbSqlWhere.toString());
		request.setAttribute("vt", getBusinessObject().getViewText());
		return new ModelAndView("sample/orderManage/purchaseOrder/purchaseOrderManage");
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
		PurchaseOrder purchaseOrder  = new PurchaseOrder();
		String purchaseOrderId = request.getParameter("purchaseOrderId");
		purchaseOrder.setPurchaseOrderId(purchaseOrderId);
		LockService.unLockBOData(purchaseOrder);
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
		PurchaseOrder purchaseOrder = (PurchaseOrder) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), PurchaseOrder.class , true , request.getMethod(), true);
// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
Set<OrderItems> orderItemsmodifyItems = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { purchaseOrder }, OrderItems.class, null);
purchaseOrder.setOrderItems(orderItemsmodifyItems);
Set<OrderItems> orderItemsdeleteItems = ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(), new Object[] { purchaseOrder }, OrderItems.class, null);
//取得业务附件，业务ID
Set<Attachement> attachements = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), null, Attachement.class, null);
this.purchaseOrderService._saveOrUpdate(purchaseOrder
,orderItemsdeleteItems
,attachements,getBusinessObject());
jo.put("purchaseOrderNo", purchaseOrder.getPurchaseOrderNo());		jo.put("purchaseOrderId", purchaseOrder.getPurchaseOrderId());
String creator = purchaseOrder.getCreator();
String creator_text = SysCachePoolUtils.getDictDataValue("YUSER", creator);
jo.put("creator_text", creator_text);
jo.put("creator", creator);
jo.put("createTime", purchaseOrder.getCreateTime());
String lastModifyor = purchaseOrder.getLastModifyor();
String lastModifyor_text = SysCachePoolUtils.getDictDataValue("YUSER", lastModifyor);
jo.put("lastModifyor_text", lastModifyor_text);
jo.put("lastModifyor", lastModifyor);
jo.put("lastModifyTime", purchaseOrder.getLastModifyTime());		this.operateSuccessfullyWithString(response,jo.toString());
	}
    
	/**
	 * 打印  
	 *   
	 * @param request
	 * @param response
	 */
	public void _print (HttpServletRequest request, HttpServletResponse response)
	{
       
	}
}