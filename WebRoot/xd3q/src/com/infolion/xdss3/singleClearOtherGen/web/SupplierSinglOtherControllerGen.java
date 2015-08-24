/*
 * @(#)SupplierSinglOtherControllerGen.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2012年06月08日 11点51分23秒
 *  描　述：创建
 */
package com.infolion.xdss3.singleClearOtherGen.web;


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
import com.infolion.platform.dpframework.util.EasyApplicationContextUtils;
import com.infolion.platform.calendar.domain.CalActivity;
import com.infolion.platform.calendar.service.CalActivityService;
import com.infolion.xdss3.singleClearOther.domain.SupplierSinglOther;
import com.infolion.xdss3.singleClearOther.service.SupplierSinglOtherService;
          
import com.infolion.xdss3.singleClearOther.domain.UnCleaPaymentOther;
import com.infolion.xdss3.singleClearOther.service.UnCleaPaymentOtherService;
          
import com.infolion.xdss3.singleClearOther.domain.UnSupplieBillOther;
import com.infolion.xdss3.singleClearOther.service.UnSupplieBillOtherService;
          
import com.infolion.xdss3.singleClearOther.domain.SettleSubjectOther;
import com.infolion.xdss3.singleClearOther.service.SettleSubjectOtherService;
          
import com.infolion.xdss3.singleClearOther.domain.FundFlowOther;
import com.infolion.xdss3.singleClearOther.service.FundFlowOtherService;
import com.infolion.platform.bo.domain.BusinessObject;
import com.infolion.platform.bo.service.BusinessObjectService;
import com.infolion.platform.dpframework.core.web.BaseMultiActionController;
import com.infolion.platform.dpframework.core.web.AbstractGenController;

/**
 * <pre>
 * 其他公司供应商单清帐(SupplierSinglOther)控制器类
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
public class SupplierSinglOtherControllerGen extends AbstractGenController
{
	private final String boName = "SupplierSinglOther";

	public String getBoName()
	{
		return this.boName;
	}
	
	@Autowired
	protected SupplierSinglOtherService supplierSinglOtherService;
	
	public void setSupplierSinglOtherService(SupplierSinglOtherService supplierSinglOtherService)
	{
		this.supplierSinglOtherService = supplierSinglOtherService;
	}
          
          
          
          


	
	/**
	 * 创建  
	 *   
	 * @param request
	 * @param response
	 */
	public ModelAndView _create(HttpServletRequest request, HttpServletResponse response)
	{
		String calActivityId = request.getParameter("calActivityId");
		if (StringUtils.isNotBlank(calActivityId))
			request.setAttribute("calActivityId", calActivityId);
	    SupplierSinglOther supplierSinglOther = new SupplierSinglOther();
	    String workflowTaskId = request.getParameter("workflowTaskId");
	    String workflowNodeDefId = request.getParameter("workflowNodeDefId");
		request.setAttribute("vt", getBusinessObject().getViewText());
        if(null!=getBusinessObject().getSubBusinessObject())
        {
			// 取得子业务对象
			for (BusinessObject bo : getBusinessObject().getSubBusinessObject())
			{
				request.setAttribute(bo.getBeanAttribute().getBeanInstanceName()+"Vt", bo.getViewText());
			}
		}
		request.setAttribute("supplierSinglOther",supplierSinglOther);
      	boolean haveBindWorkFlow = WorkflowService.getHaveBindWorkFlow("000000000368");
		request.setAttribute("haveBindWorkFlow", haveBindWorkFlow);
		request.setAttribute("workflowTaskId", workflowTaskId);	
		request.setAttribute("workflowNodeDefId", workflowNodeDefId);
	    return new ModelAndView("xdss3/singleClearOther/supplierSinglOtherAdd");
	}
	
	
	/**
	 * 复制创建  
	 *   
	 * @param request
	 * @param response
	 */
	public ModelAndView _copyCreate(HttpServletRequest request, HttpServletResponse response)
	{
		String calActivityId = request.getParameter("calActivityId");
		if (StringUtils.isNotBlank(calActivityId))
			request.setAttribute("calActivityId", calActivityId);
		String id = request.getParameter("suppliersclearid");
		SupplierSinglOther supplierSinglOther = this.supplierSinglOtherService._getEntityCopy(id);
		request.setAttribute("isCreateCopy", "true");
		request.setAttribute("supplierSinglOther", supplierSinglOther);
		request.setAttribute("vt", getBusinessObject().getViewText());
        if(null!=getBusinessObject().getSubBusinessObject())
        {
			// 取得子业务对象
			for (BusinessObject bo : getBusinessObject().getSubBusinessObject())
			{
				request.setAttribute(bo.getBeanAttribute().getBeanInstanceName()+"Vt", bo.getViewText());
			}
		}
      	boolean haveBindWorkFlow = WorkflowService.getHaveBindWorkFlow("000000000368");
		request.setAttribute("haveBindWorkFlow", haveBindWorkFlow);
		request.setAttribute("workflowTaskId", "");	
		request.setAttribute("workflowNodeDefId", "");
		return new ModelAndView("xdss3/singleClearOther/supplierSinglOtherAdd");
	}

	
	/**
	 * 删除  
	 *   
	 * @param request
	 * @param response
	 */
	public void _delete(HttpServletRequest request, HttpServletResponse response)
	{
		String suppliersclearid = request.getParameter("suppliersclearid");
		supplierSinglOtherService._delete(suppliersclearid,getBusinessObject());
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
		String supplierSinglOtherIds = request.getParameter("supplierSinglOtherIds");
		supplierSinglOtherService._deletes(supplierSinglOtherIds,getBusinessObject());
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
		SupplierSinglOther supplierSinglOther = new SupplierSinglOther();
		String workflowTaskId = request.getParameter("workflowTaskId");
		String workflowNodeDefId = request.getParameter("workflowNodeDefId");
		String id = request.getParameter("suppliersclearid");
		String businessId = request.getParameter("businessId");	
		if (null == id)
			id = businessId ;

		if (StringUtils.isNullBlank(id))
		{
supplierSinglOther = this.supplierSinglOtherService._get(id);        }
        else
        {
           supplierSinglOther = this.supplierSinglOtherService._get(id);
        }
    
      	boolean haveBindWorkFlow = WorkflowService.getHaveBindWorkFlow("000000000368");
		request.setAttribute("haveBindWorkFlow", haveBindWorkFlow);
		request.setAttribute("businessId", id);
		request.setAttribute("workflowTaskId", workflowTaskId);
		request.setAttribute("workflowNodeDefId", workflowNodeDefId);
		request.setAttribute("vt", getBusinessObject().getViewText());
        if(null!=getBusinessObject().getSubBusinessObject())
        {
			// 取得子业务对象
			for (BusinessObject bo : getBusinessObject().getSubBusinessObject())
			{
				request.setAttribute(bo.getBeanAttribute().getBeanInstanceName()+"Vt", bo.getViewText());
			}
		}
		
		request.setAttribute("supplierSinglOther", supplierSinglOther);  
		return new ModelAndView("xdss3/singleClearOther/supplierSinglOtherView");
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
		SupplierSinglOther supplierSinglOther = (SupplierSinglOther) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), SupplierSinglOther.class , true , request.getMethod(), true);
        //类型标识是从那个页面提交，view 表示从view页面提交流程。
        String type = request.getParameter("type");
// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
Set<UnCleaPaymentOther> UnCleaPaymentOthermodifyItems = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { supplierSinglOther }, UnCleaPaymentOther.class, null);
Set<UnCleaPaymentOther> deletedUnCleaPaymentOtherSet = ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(), new Object[] { supplierSinglOther }, UnCleaPaymentOther.class, null);
supplierSinglOther.setUnCleaPaymentOther(UnCleaPaymentOthermodifyItems);
// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
Set<UnSupplieBillOther> UnSupplieBillOthermodifyItems = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { supplierSinglOther }, UnSupplieBillOther.class, null);
Set<UnSupplieBillOther> deletedUnSupplieBillOtherSet = ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(), new Object[] { supplierSinglOther }, UnSupplieBillOther.class, null);
supplierSinglOther.setUnSupplieBillOther(UnSupplieBillOthermodifyItems);
//绑定子对象(一对一关系)
SettleSubjectOther SettleSubjectOther = (SettleSubjectOther) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), SettleSubjectOther.class, false , request.getMethod(), true);
if(SettleSubjectOther!=null)
{
supplierSinglOther.setSettleSubjectOther(SettleSubjectOther);
SettleSubjectOther.setSupplierSinglOther(supplierSinglOther);
}
//绑定子对象(一对一关系)
FundFlowOther FundFlowOther = (FundFlowOther) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), FundFlowOther.class, false , request.getMethod(), true);
if(FundFlowOther!=null)
{
supplierSinglOther.setFundFlowOther(FundFlowOther);
FundFlowOther.setSupplierSinglOther(supplierSinglOther);
}
if (!"view".equalsIgnoreCase(type))
{
this.supplierSinglOtherService._saveOrUpdate(supplierSinglOther
,deletedUnCleaPaymentOtherSet
,deletedUnSupplieBillOtherSet
,getBusinessObject());
}
this.supplierSinglOtherService._submitProcess(supplierSinglOther
,deletedUnCleaPaymentOtherSet
,deletedUnSupplieBillOtherSet		,getBusinessObject());
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
		return new ModelAndView("xdss3/singleClearOther/supplierSinglOtherManage");
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
		SupplierSinglOther supplierSinglOther  = new SupplierSinglOther();
		String suppliersclearid = request.getParameter("suppliersclearid");
		supplierSinglOther.setSuppliersclearid(suppliersclearid);
		LockService.unLockBOData(supplierSinglOther);
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
		SupplierSinglOther supplierSinglOther = (SupplierSinglOther) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), SupplierSinglOther.class , true , request.getMethod(), true);
// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
Set<UnCleaPaymentOther> UnCleaPaymentOthermodifyItems = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { supplierSinglOther }, UnCleaPaymentOther.class, null);
supplierSinglOther.setUnCleaPaymentOther(UnCleaPaymentOthermodifyItems);
Set<UnCleaPaymentOther> UnCleaPaymentOtherdeleteItems = ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(), new Object[] { supplierSinglOther }, UnCleaPaymentOther.class, null);
// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
Set<UnSupplieBillOther> UnSupplieBillOthermodifyItems = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { supplierSinglOther }, UnSupplieBillOther.class, null);
supplierSinglOther.setUnSupplieBillOther(UnSupplieBillOthermodifyItems);
Set<UnSupplieBillOther> UnSupplieBillOtherdeleteItems = ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(), new Object[] { supplierSinglOther }, UnSupplieBillOther.class, null);
//绑定子对象(一对一关系)
SettleSubjectOther SettleSubjectOther = (SettleSubjectOther) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), SettleSubjectOther.class, false , request.getMethod(), true);
if(SettleSubjectOther!=null)
{
supplierSinglOther.setSettleSubjectOther(SettleSubjectOther);
SettleSubjectOther.setSupplierSinglOther(supplierSinglOther);
}
//绑定子对象(一对一关系)
FundFlowOther FundFlowOther = (FundFlowOther) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), FundFlowOther.class, false , request.getMethod(), true);
if(FundFlowOther!=null)
{
supplierSinglOther.setFundFlowOther(FundFlowOther);
FundFlowOther.setSupplierSinglOther(supplierSinglOther);
}
this.supplierSinglOtherService._saveOrUpdate(supplierSinglOther
,UnCleaPaymentOtherdeleteItems
,UnSupplieBillOtherdeleteItems,getBusinessObject());

		// 如果返回参数带有calActivityId，将业务对象保存到相应的CalActivity中
		String calActivityId = request.getParameter("calActivityId");
		if (StringUtils.isNotBlank(calActivityId))
		{
			CalActivityService calActivityService = (CalActivityService) EasyApplicationContextUtils.getBeanByName("calActivityService");
			CalActivity calActivity = calActivityService._get(calActivityId);
			if (calActivity != null)
			{
				calActivity.setBoid(this.getBusinessObject().getBoId());
				calActivity.setBoname(this.getBoName());
				calActivity.setBusid(supplierSinglOther.getSuppliersclearid());
				calActivityService._update2(calActivity, BusinessObjectService.getBusinessObject("CalActivity"));
			}
			this.operateClose(response);
		}
	    else
		{	
jo.put("supplierclearno", supplierSinglOther.getSupplierclearno());		jo.put("suppliersclearid", supplierSinglOther.getSuppliersclearid());
jo.put("settlesubjectid", supplierSinglOther.getSettleSubjectOther()!=null ? supplierSinglOther.getSettleSubjectOther().getSettlesubjectid() : "");
jo.put("fundflowid", supplierSinglOther.getFundFlowOther()!=null ? supplierSinglOther.getFundFlowOther().getFundflowid() : "");String creator = supplierSinglOther.getCreator();
String creator_text = SysCachePoolUtils.getDictDataValue("YUSER", creator);
jo.put("creator_text", creator_text);
jo.put("creator", creator);
jo.put("createtime", supplierSinglOther.getCreatetime());
String lastmodifyer = supplierSinglOther.getLastmodifyer();
String lastmodifyer_text = SysCachePoolUtils.getDictDataValue("YUSER", lastmodifyer);
jo.put("lastmodifyer_text", lastmodifyer_text);
jo.put("lastmodifyer", lastmodifyer);
jo.put("lastmodifytime", supplierSinglOther.getLastmodifytime());		this.operateSuccessfullyWithString(response,jo.toString());
	   }
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
	 * 上传  
	 *   
	 * @param request
	 * @param response
	 */
	public void _upload (HttpServletRequest request, HttpServletResponse response)
	{
       
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
		SupplierSinglOther supplierSinglOther = new SupplierSinglOther();
		String id = request.getParameter("suppliersclearid");
		String workflowTaskId = request.getParameter("workflowTaskId");
		String workflowNodeDefId = request.getParameter("workflowNodeDefId");
		String businessId = request.getParameter("businessId");	
		if (null == id)
			id = businessId ;
			
		if (StringUtils.isNullBlank(id))
		{	
supplierSinglOther = this.supplierSinglOtherService._getForEdit(id);        }
        else
        {
           supplierSinglOther = this.supplierSinglOtherService._getForEdit(id);
        }
       
		boolean haveBindWorkFlow = WorkflowService.getHaveBindWorkFlow("000000000368");
		request.setAttribute("businessId", id);
		request.setAttribute("workflowTaskId", workflowTaskId);	
		request.setAttribute("workflowNodeDefId", workflowNodeDefId);
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
		request.setAttribute("supplierSinglOther", supplierSinglOther);
		return new ModelAndView("xdss3/singleClearOther/supplierSinglOtherEdit");
	}
    
	/**
	 * 批量解锁  
	 *   
	 * @param request
	 * @param response
	 */
	public void _unlock (HttpServletRequest request, HttpServletResponse response)
	{
       
	}
    
	/**
	 * 锁定  
	 *   
	 * @param request
	 * @param response
	 */
	public void _locked (HttpServletRequest request, HttpServletResponse response)
	{
       
	}
    
	/**
	 * 模拟凭证  
	 *   
	 * @param request
	 * @param response
	 */
	public void _voucherPreview (HttpServletRequest request, HttpServletResponse response)
	{
       
	}
    
	/**
	 * 查询  
	 *   
	 * @param request
	 * @param response
	 */
	public void _queryUnClear (HttpServletRequest request, HttpServletResponse response)
	{
       
	}
}