/*
 * @(#)CustomSingleOtherControllerGen.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2012年06月08日 11点48分53秒
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
import com.infolion.xdss3.singleClearOther.domain.CustomSingleOther;
import com.infolion.xdss3.singleClearOther.service.CustomSingleOtherService;
          
import com.infolion.xdss3.singleClearOther.domain.SettleSubjectOther;
import com.infolion.xdss3.singleClearOther.service.SettleSubjectOtherService;
          
import com.infolion.xdss3.singleClearOther.domain.FundFlowOther;
import com.infolion.xdss3.singleClearOther.service.FundFlowOtherService;
          
import com.infolion.xdss3.singleClearOther.domain.UnCleaCollectOther;
import com.infolion.xdss3.singleClearOther.service.UnCleaCollectOtherService;
          
import com.infolion.xdss3.singleClearOther.domain.UnCustomBillOther;
import com.infolion.xdss3.singleClearOther.service.UnCustomBillOtherService;
import com.infolion.platform.bo.domain.BusinessObject;
import com.infolion.platform.bo.service.BusinessObjectService;
import com.infolion.platform.dpframework.core.web.BaseMultiActionController;
import com.infolion.platform.dpframework.core.web.AbstractGenController;

/**
 * <pre>
 * 其他公司客户单清帐(CustomSingleOther)控制器类
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
public class CustomSingleOtherControllerGen extends AbstractGenController
{
	private final String boName = "CustomSingleOther";

	public String getBoName()
	{
		return this.boName;
	}
	
	@Autowired
	protected CustomSingleOtherService customSingleOtherService;
	
	public void setCustomSingleOtherService(CustomSingleOtherService customSingleOtherService)
	{
		this.customSingleOtherService = customSingleOtherService;
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
	    CustomSingleOther customSingleOther = new CustomSingleOther();
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
		request.setAttribute("customSingleOther",customSingleOther);
      	boolean haveBindWorkFlow = WorkflowService.getHaveBindWorkFlow("000000000367");
		request.setAttribute("haveBindWorkFlow", haveBindWorkFlow);
		request.setAttribute("workflowTaskId", workflowTaskId);	
		request.setAttribute("workflowNodeDefId", workflowNodeDefId);
	    return new ModelAndView("xdss3/singleClearOther/customSingleOtherAdd");
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
		String id = request.getParameter("customsclearid");
		CustomSingleOther customSingleOther = this.customSingleOtherService._getEntityCopy(id);
		request.setAttribute("isCreateCopy", "true");
		request.setAttribute("customSingleOther", customSingleOther);
		request.setAttribute("vt", getBusinessObject().getViewText());
        if(null!=getBusinessObject().getSubBusinessObject())
        {
			// 取得子业务对象
			for (BusinessObject bo : getBusinessObject().getSubBusinessObject())
			{
				request.setAttribute(bo.getBeanAttribute().getBeanInstanceName()+"Vt", bo.getViewText());
			}
		}
      	boolean haveBindWorkFlow = WorkflowService.getHaveBindWorkFlow("000000000367");
		request.setAttribute("haveBindWorkFlow", haveBindWorkFlow);
		request.setAttribute("workflowTaskId", "");	
		request.setAttribute("workflowNodeDefId", "");
		return new ModelAndView("xdss3/singleClearOther/customSingleOtherAdd");
	}

	
	/**
	 * 删除  
	 *   
	 * @param request
	 * @param response
	 */
	public void _delete(HttpServletRequest request, HttpServletResponse response)
	{
		String customsclearid = request.getParameter("customsclearid");
		customSingleOtherService._delete(customsclearid,getBusinessObject());
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
		String customSingleOtherIds = request.getParameter("customSingleOtherIds");
		customSingleOtherService._deletes(customSingleOtherIds,getBusinessObject());
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
		CustomSingleOther customSingleOther = new CustomSingleOther();
		String workflowTaskId = request.getParameter("workflowTaskId");
		String workflowNodeDefId = request.getParameter("workflowNodeDefId");
		String id = request.getParameter("customsclearid");
		String businessId = request.getParameter("businessId");	
		if (null == id)
			id = businessId ;

		if (StringUtils.isNullBlank(id))
		{
customSingleOther = this.customSingleOtherService._get(id);        }
        else
        {
           customSingleOther = this.customSingleOtherService._get(id);
        }
    
      	boolean haveBindWorkFlow = WorkflowService.getHaveBindWorkFlow("000000000367");
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
		
		request.setAttribute("customSingleOther", customSingleOther);  
		return new ModelAndView("xdss3/singleClearOther/customSingleOtherView");
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
		CustomSingleOther customSingleOther = (CustomSingleOther) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), CustomSingleOther.class , true , request.getMethod(), true);
        //类型标识是从那个页面提交，view 表示从view页面提交流程。
        String type = request.getParameter("type");
//绑定子对象(一对一关系)
SettleSubjectOther SettleSubjectOther = (SettleSubjectOther) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), SettleSubjectOther.class, false , request.getMethod(), true);
if(SettleSubjectOther!=null)
{
customSingleOther.setSettleSubjectOther(SettleSubjectOther);
SettleSubjectOther.setCustomSingleOther(customSingleOther);
}
//绑定子对象(一对一关系)
FundFlowOther FundFlowOther = (FundFlowOther) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), FundFlowOther.class, false , request.getMethod(), true);
if(FundFlowOther!=null)
{
customSingleOther.setFundFlowOther(FundFlowOther);
FundFlowOther.setCustomSingleOther(customSingleOther);
}
// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
Set<UnCleaCollectOther> UnCleaCollectOthermodifyItems = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { customSingleOther }, UnCleaCollectOther.class, null);
Set<UnCleaCollectOther> deletedUnCleaCollectOtherSet = ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(), new Object[] { customSingleOther }, UnCleaCollectOther.class, null);
customSingleOther.setUnCleaCollectOther(UnCleaCollectOthermodifyItems);
// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
Set<UnCustomBillOther> UnCustomBillOthermodifyItems = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { customSingleOther }, UnCustomBillOther.class, null);
Set<UnCustomBillOther> deletedUnCustomBillOtherSet = ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(), new Object[] { customSingleOther }, UnCustomBillOther.class, null);
customSingleOther.setUnCustomBillOther(UnCustomBillOthermodifyItems);
if (!"view".equalsIgnoreCase(type))
{
this.customSingleOtherService._saveOrUpdate(customSingleOther
,deletedUnCleaCollectOtherSet
,deletedUnCustomBillOtherSet
,getBusinessObject());
}
this.customSingleOtherService._submitProcess(customSingleOther
,deletedUnCleaCollectOtherSet
,deletedUnCustomBillOtherSet		,getBusinessObject());
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
		return new ModelAndView("xdss3/singleClearOther/customSingleOtherManage");
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
		CustomSingleOther customSingleOther  = new CustomSingleOther();
		String customsclearid = request.getParameter("customsclearid");
		customSingleOther.setCustomsclearid(customsclearid);
		LockService.unLockBOData(customSingleOther);
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
		CustomSingleOther customSingleOther = (CustomSingleOther) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), CustomSingleOther.class , true , request.getMethod(), true);
//绑定子对象(一对一关系)
SettleSubjectOther SettleSubjectOther = (SettleSubjectOther) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), SettleSubjectOther.class, false , request.getMethod(), true);
if(SettleSubjectOther!=null)
{
customSingleOther.setSettleSubjectOther(SettleSubjectOther);
SettleSubjectOther.setCustomSingleOther(customSingleOther);
}
//绑定子对象(一对一关系)
FundFlowOther FundFlowOther = (FundFlowOther) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), FundFlowOther.class, false , request.getMethod(), true);
if(FundFlowOther!=null)
{
customSingleOther.setFundFlowOther(FundFlowOther);
FundFlowOther.setCustomSingleOther(customSingleOther);
}
// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
Set<UnCleaCollectOther> UnCleaCollectOthermodifyItems = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { customSingleOther }, UnCleaCollectOther.class, null);
customSingleOther.setUnCleaCollectOther(UnCleaCollectOthermodifyItems);
Set<UnCleaCollectOther> UnCleaCollectOtherdeleteItems = ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(), new Object[] { customSingleOther }, UnCleaCollectOther.class, null);
// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
Set<UnCustomBillOther> UnCustomBillOthermodifyItems = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { customSingleOther }, UnCustomBillOther.class, null);
customSingleOther.setUnCustomBillOther(UnCustomBillOthermodifyItems);
Set<UnCustomBillOther> UnCustomBillOtherdeleteItems = ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(), new Object[] { customSingleOther }, UnCustomBillOther.class, null);
//this.customSingleOtherService._saveOrUpdate(customSingleOther
//,UnCleaCollectOtherdeleteItems
//,UnCustomBillOtherdeleteItems,getBusinessObject());
this.customSingleOtherService._saveOrUpdate(customSingleOther, getBusinessObject());

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
				calActivity.setBusid(customSingleOther.getCustomsclearid());
				calActivityService._update2(calActivity, BusinessObjectService.getBusinessObject("CalActivity"));
			}
			this.operateClose(response);
		}
	    else
		{	
jo.put("customclearno", customSingleOther.getCustomclearno());		jo.put("customsclearid", customSingleOther.getCustomsclearid());
jo.put("settlesubjectid", customSingleOther.getSettleSubjectOther()!=null ? customSingleOther.getSettleSubjectOther().getSettlesubjectid() : "");
jo.put("fundflowid", customSingleOther.getFundFlowOther()!=null ? customSingleOther.getFundFlowOther().getFundflowid() : "");String creator = customSingleOther.getCreator();
String creator_text = SysCachePoolUtils.getDictDataValue("YUSER", creator);
jo.put("creator_text", creator_text);
jo.put("creator", creator);
jo.put("createtime", customSingleOther.getCreatetime());
String lastmodifyer = customSingleOther.getLastmodifyer();
String lastmodifyer_text = SysCachePoolUtils.getDictDataValue("YUSER", lastmodifyer);
jo.put("lastmodifyer_text", lastmodifyer_text);
jo.put("lastmodifyer", lastmodifyer);
jo.put("lastmodifytime", customSingleOther.getLastmodifytime());		this.operateSuccessfullyWithString(response,jo.toString());
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
		CustomSingleOther customSingleOther = new CustomSingleOther();
		String id = request.getParameter("customsclearid");
		String workflowTaskId = request.getParameter("workflowTaskId");
		String workflowNodeDefId = request.getParameter("workflowNodeDefId");
		String businessId = request.getParameter("businessId");	
		if (null == id)
			id = businessId ;
			
		if (StringUtils.isNullBlank(id))
		{	
customSingleOther = this.customSingleOtherService._getForEdit(id);        }
        else
        {
           customSingleOther = this.customSingleOtherService._getForEdit(id);
        }
       
		boolean haveBindWorkFlow = WorkflowService.getHaveBindWorkFlow("000000000367");
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
		request.setAttribute("customSingleOther", customSingleOther);
		return new ModelAndView("xdss3/singleClearOther/customSingleOtherEdit");
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
}