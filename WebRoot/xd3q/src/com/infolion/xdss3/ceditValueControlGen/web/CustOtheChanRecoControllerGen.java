/*
 * @(#)CustOtheChanRecoControllerGen.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年05月28日 13点31分18秒
 *  描　述：创建
 */
package com.infolion.xdss3.ceditValueControlGen.web;


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
import com.infolion.platform.console.sys.context.UserContextHolder;
import com.infolion.xdss3.ceditValueControl.domain.CustOtheChanReco;
import com.infolion.xdss3.ceditValueControl.service.CustOtheChanRecoService;
import com.infolion.platform.bo.domain.BusinessObject;
import com.infolion.platform.bo.service.BusinessObjectService;
import com.infolion.platform.dpframework.core.web.BaseMultiActionController;
import com.infolion.platform.dpframework.core.web.AbstractGenController;

/**
 * <pre>
 * 客户代垫费用查询(CustOtheChanReco)控制器类
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
public class CustOtheChanRecoControllerGen extends AbstractGenController
{
	private final String boName = "CustOtheChanReco";

	public String getBoName()
	{
		return this.boName;
	}
	
	@Autowired
	protected CustOtheChanRecoService custOtheChanRecoService;
	
	public void setCustOtheChanRecoService(CustOtheChanRecoService custOtheChanRecoService)
	{
		this.custOtheChanRecoService = custOtheChanRecoService;
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
	    CustOtheChanReco custOtheChanReco = new CustOtheChanReco();
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
		request.setAttribute("custOtheChanReco",custOtheChanReco);
      	boolean haveBindWorkFlow = WorkflowService.getHaveBindWorkFlow("000000000272");
		request.setAttribute("haveBindWorkFlow", haveBindWorkFlow);
		request.setAttribute("workflowTaskId", workflowTaskId);	
		request.setAttribute("workflowNodeDefId", workflowNodeDefId);
	    return new ModelAndView("xdss3/ceditValueControl/custOtheChanRecoAdd");
	}
	
	
	/**
	 * 复制创建  
	 *   
	 * @param request
	 * @param response
	 */
	public ModelAndView _copyCreate(HttpServletRequest request, HttpServletResponse response)
	{
		String id = request.getParameter("changeid");
		CustOtheChanReco custOtheChanReco = this.custOtheChanRecoService._getEntityCopy(id);
		request.setAttribute("isCreateCopy", "true");
		request.setAttribute("custOtheChanReco", custOtheChanReco);
		request.setAttribute("vt", getBusinessObject().getViewText());
        if(null!=getBusinessObject().getSubBusinessObject())
        {
			// 取得子业务对象
			for (BusinessObject bo : getBusinessObject().getSubBusinessObject())
			{
				request.setAttribute(bo.getBeanAttribute().getBeanInstanceName()+"Vt", bo.getViewText());
			}
		}
		return new ModelAndView("xdss3/ceditValueControl/custOtheChanRecoAdd");
	}

	
	/**
	 * 删除  
	 *   
	 * @param request
	 * @param response
	 */
	public void _delete(HttpServletRequest request, HttpServletResponse response)
	{
		String changeid = request.getParameter("changeid");
		custOtheChanRecoService._delete(changeid,getBusinessObject());
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
		String custOtheChanRecoIds = request.getParameter("custOtheChanRecoIds");
		custOtheChanRecoService._deletes(custOtheChanRecoIds,getBusinessObject());
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
		CustOtheChanReco custOtheChanReco = new CustOtheChanReco();
		String workflowTaskId = request.getParameter("workflowTaskId");
		String workflowNodeDefId = request.getParameter("workflowNodeDefId");
		String id = request.getParameter("changeid");
		String businessId = request.getParameter("businessId");	
		if (null == id)
			id = businessId ;

		if (StringUtils.isNullBlank(id))
		{
custOtheChanReco = this.custOtheChanRecoService._get(id);        }
        else
        {
           custOtheChanReco = this.custOtheChanRecoService._get(id);
        }
    
      	boolean haveBindWorkFlow = WorkflowService.getHaveBindWorkFlow("000000000272");
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
		
		request.setAttribute("custOtheChanReco", custOtheChanReco);  
		return new ModelAndView("xdss3/ceditValueControl/custOtheChanRecoView");
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
		CustOtheChanReco custOtheChanReco = (CustOtheChanReco) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), CustOtheChanReco.class , true , request.getMethod(), true);
this.custOtheChanRecoService._submitProcess(custOtheChanReco		,getBusinessObject());
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

        // 获取用户上下文ID
        String userId = UserContextHolder.getLocalUserContext().getUserContext().getSysUser().getUserId();
        request.setAttribute("userId", userId);
		return new ModelAndView("xdss3/ceditValueControl/custOtheChanRecoManage");
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
		CustOtheChanReco custOtheChanReco  = new CustOtheChanReco();
		String changeid = request.getParameter("changeid");
		custOtheChanReco.setChangeid(changeid);
		LockService.unLockBOData(custOtheChanReco);
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
		CustOtheChanReco custOtheChanReco = (CustOtheChanReco) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), CustOtheChanReco.class , true , request.getMethod(), true);
this.custOtheChanRecoService._saveOrUpdate(custOtheChanReco,getBusinessObject());

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
				calActivity.setBusid(custOtheChanReco.getChangeid());
				calActivityService._update2(calActivity, BusinessObjectService.getBusinessObject("CalActivity"));
			}
			this.operateClose(response);
		}
	    else
		{	
		jo.put("changeid", custOtheChanReco.getChangeid());
String creator = custOtheChanReco.getCreator();
String creator_text = SysCachePoolUtils.getDictDataValue("YUSER", creator);
jo.put("creator_text", creator_text);
jo.put("creator", creator);
jo.put("createtime", custOtheChanReco.getCreatetime());
String lastmodifyer = custOtheChanReco.getLastmodifyer();
String lastmodifyer_text = SysCachePoolUtils.getDictDataValue("YUSER", lastmodifyer);
jo.put("lastmodifyer_text", lastmodifyer_text);
jo.put("lastmodifyer", lastmodifyer);
jo.put("lastmodifytime", custOtheChanReco.getLastmodifytime());		this.operateSuccessfullyWithString(response,jo.toString());
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
		CustOtheChanReco custOtheChanReco = new CustOtheChanReco();
		String id = request.getParameter("changeid");
		String workflowTaskId = request.getParameter("workflowTaskId");
		String workflowNodeDefId = request.getParameter("workflowNodeDefId");
		String businessId = request.getParameter("businessId");	
		if (null == id)
			id = businessId ;
			
		if (StringUtils.isNullBlank(id))
		{	
custOtheChanReco = this.custOtheChanRecoService._getForEdit(id);        }
        else
        {
           custOtheChanReco = this.custOtheChanRecoService._getForEdit(id);
        }
       
		boolean haveBindWorkFlow = WorkflowService.getHaveBindWorkFlow("000000000272");
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
		request.setAttribute("custOtheChanReco", custOtheChanReco);
		return new ModelAndView("xdss3/ceditValueControl/custOtheChanRecoEdit");
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