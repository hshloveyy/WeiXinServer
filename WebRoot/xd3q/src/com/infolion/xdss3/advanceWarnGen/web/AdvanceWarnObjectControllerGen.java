/*
 * @(#)AdvanceWarnObjectControllerGen.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年05月19日 11点29分37秒
 *  描　述：创建
 */
package com.infolion.xdss3.advanceWarnGen.web;


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
import com.infolion.xdss3.advanceWarn.domain.AdvanceWarnObject;
import com.infolion.xdss3.advanceWarn.service.AdvanceWarnObjectService;
          
import com.infolion.xdss3.advanceWarn.domain.AdvanceWarnRecv;
import com.infolion.xdss3.advanceWarn.service.AdvanceWarnRecvService;
          
import com.infolion.xdss3.advanceWarn.domain.AdvanceWarnCond;
import com.infolion.xdss3.advanceWarn.service.AdvanceWarnCondService;
import com.infolion.xdss3.mail.MailSenderInfo;
import com.infolion.xdss3.mail.SimpleMailSender;
import com.infolion.platform.bo.domain.BusinessObject;
import com.infolion.platform.bo.service.BusinessObjectService;
import com.infolion.platform.dpframework.core.web.BaseMultiActionController;
import com.infolion.platform.dpframework.core.web.AbstractGenController;

/**
 * <pre>
 * 预警对像配置(AdvanceWarnObject)控制器类
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
public class AdvanceWarnObjectControllerGen extends AbstractGenController
{
	private final String boName = "AdvanceWarnObject";

	public String getBoName()
	{
		return this.boName;
	}
	
	@Autowired
	protected AdvanceWarnObjectService advanceWarnObjectService;
	
	public void setAdvanceWarnObjectService(AdvanceWarnObjectService advanceWarnObjectService)
	{
		this.advanceWarnObjectService = advanceWarnObjectService;
	}
          
	/**
	 * 创建  
	 *   
	 * @param request
	 * @param response
	 */
	public ModelAndView _create(HttpServletRequest request, HttpServletResponse response)
	{
		//this.advanceWarnObjectService.doAlarmCheckWork();		
		String calActivityId = request.getParameter("calActivityId");
		if (StringUtils.isNotBlank(calActivityId))
			request.setAttribute("calActivityId", calActivityId);
	    AdvanceWarnObject advanceWarnObject = new AdvanceWarnObject();
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
		request.setAttribute("advanceWarnObject",advanceWarnObject);
      	boolean haveBindWorkFlow = WorkflowService.getHaveBindWorkFlow("000000000244");
		request.setAttribute("haveBindWorkFlow", haveBindWorkFlow);
		request.setAttribute("workflowTaskId", workflowTaskId);	
		request.setAttribute("workflowNodeDefId", workflowNodeDefId);
	    return new ModelAndView("xdss3/advanceWarn/advanceWarnObjectAdd");
	}
	
	
	/**
	 * 复制创建  
	 *   
	 * @param request
	 * @param response
	 */
	public ModelAndView _copyCreate(HttpServletRequest request, HttpServletResponse response)
	{
		String id = request.getParameter("warnid");
		AdvanceWarnObject advanceWarnObject = this.advanceWarnObjectService._getEntityCopy(id);
		request.setAttribute("isCreateCopy", "true");
		request.setAttribute("advanceWarnObject", advanceWarnObject);
		request.setAttribute("vt", getBusinessObject().getViewText());
        if(null!=getBusinessObject().getSubBusinessObject())
        {
			// 取得子业务对象
			for (BusinessObject bo : getBusinessObject().getSubBusinessObject())
			{
				request.setAttribute(bo.getBeanAttribute().getBeanInstanceName()+"Vt", bo.getViewText());
			}
		}
		return new ModelAndView("xdss3/advanceWarn/advanceWarnObjectAdd");
	}

	
	/**
	 * 删除  
	 *   
	 * @param request
	 * @param response
	 */
	public void _delete(HttpServletRequest request, HttpServletResponse response)
	{
		String warnid = request.getParameter("warnid");
		advanceWarnObjectService._delete(warnid,getBusinessObject());
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
		String advanceWarnObjectIds = request.getParameter("advanceWarnObjectIds");
		advanceWarnObjectService._deletes(advanceWarnObjectIds,getBusinessObject());
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
		AdvanceWarnObject advanceWarnObject = new AdvanceWarnObject();
		String workflowTaskId = request.getParameter("workflowTaskId");
		String workflowNodeDefId = request.getParameter("workflowNodeDefId");
		String id = request.getParameter("warnid");
		String businessId = request.getParameter("businessId");	
		if (null == id)
			id = businessId ;

		if (StringUtils.isNullBlank(id))
		{
advanceWarnObject = this.advanceWarnObjectService._get(id);        }
        else
        {
           advanceWarnObject = this.advanceWarnObjectService._get(id);
        }
    
      	boolean haveBindWorkFlow = WorkflowService.getHaveBindWorkFlow("000000000244");
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
		
		request.setAttribute("advanceWarnObject", advanceWarnObject);  
		return new ModelAndView("xdss3/advanceWarn/advanceWarnObjectView");
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
		AdvanceWarnObject advanceWarnObject = (AdvanceWarnObject) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), AdvanceWarnObject.class , true , request.getMethod(), true);
// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
Set<AdvanceWarnRecv> advanceWarnReceivermodifyItems = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { advanceWarnObject }, AdvanceWarnRecv.class, null);
Set<AdvanceWarnRecv> deletedAdvanceWarnRecvSet = ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(), new Object[] { advanceWarnObject }, AdvanceWarnRecv.class, null);
advanceWarnObject.setAdvanceWarnReceiver(advanceWarnReceivermodifyItems);
// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
Set<AdvanceWarnCond> advanceWarnConditionmodifyItems = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { advanceWarnObject }, AdvanceWarnCond.class, null);
Set<AdvanceWarnCond> deletedAdvanceWarnCondSet = ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(), new Object[] { advanceWarnObject }, AdvanceWarnCond.class, null);
advanceWarnObject.setAdvanceWarnCondition(advanceWarnConditionmodifyItems);
this.advanceWarnObjectService._submitProcess(advanceWarnObject
,deletedAdvanceWarnRecvSet
,deletedAdvanceWarnCondSet		,getBusinessObject());
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
		return new ModelAndView("xdss3/advanceWarn/advanceWarnObjectManage");
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
		AdvanceWarnObject advanceWarnObject  = new AdvanceWarnObject();
		String warnid = request.getParameter("warnid");
		advanceWarnObject.setWarnid(warnid);
		LockService.unLockBOData(advanceWarnObject);
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
		AdvanceWarnObject advanceWarnObject = (AdvanceWarnObject) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), AdvanceWarnObject.class , true , request.getMethod(), true);
// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
Set<AdvanceWarnRecv> advanceWarnReceivermodifyItems = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { advanceWarnObject }, AdvanceWarnRecv.class, null);
advanceWarnObject.setAdvanceWarnReceiver(advanceWarnReceivermodifyItems);
Set<AdvanceWarnRecv> advanceWarnReceiverdeleteItems = ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(), new Object[] { advanceWarnObject }, AdvanceWarnRecv.class, null);
// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
Set<AdvanceWarnCond> advanceWarnConditionmodifyItems = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { advanceWarnObject }, AdvanceWarnCond.class, null);
advanceWarnObject.setAdvanceWarnCondition(advanceWarnConditionmodifyItems);
Set<AdvanceWarnCond> advanceWarnConditiondeleteItems = ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(), new Object[] { advanceWarnObject }, AdvanceWarnCond.class, null);
this.advanceWarnObjectService._saveOrUpdate(advanceWarnObject
,advanceWarnReceiverdeleteItems
,advanceWarnConditiondeleteItems,getBusinessObject());

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
				calActivity.setBusid(advanceWarnObject.getWarnid());
				calActivityService._update2(calActivity, BusinessObjectService.getBusinessObject("CalActivity"));
			}
			this.operateClose(response);
		}
	    else
		{	
		jo.put("warnid", advanceWarnObject.getWarnid());
String creator = advanceWarnObject.getCreator();
String creator_text = SysCachePoolUtils.getDictDataValue("YUSER", creator);
jo.put("creator_text", creator_text);
jo.put("creator", creator);
jo.put("createtime", advanceWarnObject.getCreatetime());
String lastmodifyer = advanceWarnObject.getLastmodifyer();
String lastmodifyer_text = SysCachePoolUtils.getDictDataValue("YUSER", lastmodifyer);
jo.put("lastmodifyer_text", lastmodifyer_text);
jo.put("lastmodifyer", lastmodifyer);
jo.put("lastmodifytime", advanceWarnObject.getLastmodifytime());		this.operateSuccessfullyWithString(response,jo.toString());
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
		AdvanceWarnObject advanceWarnObject = new AdvanceWarnObject();
		String id = request.getParameter("warnid");
		String workflowTaskId = request.getParameter("workflowTaskId");
		String workflowNodeDefId = request.getParameter("workflowNodeDefId");
		String businessId = request.getParameter("businessId");	
		if (null == id)
			id = businessId ;
			
		if (StringUtils.isNullBlank(id))
		{	
advanceWarnObject = this.advanceWarnObjectService._getForEdit(id);        }
        else
        {
           advanceWarnObject = this.advanceWarnObjectService._getForEdit(id);
        }
       
		boolean haveBindWorkFlow = WorkflowService.getHaveBindWorkFlow("000000000244");
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
		request.setAttribute("advanceWarnObject", advanceWarnObject);
		return new ModelAndView("xdss3/advanceWarn/advanceWarnObjectEdit");
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
}