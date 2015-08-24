/*
 * @(#)SupplierSinglClearControllerGen.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年07月14日 16点59分07秒
 *  描　述：创建
 */
package com.infolion.xdss3.singleClearGen.web;


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
import com.infolion.xdss3.singleClear.domain.SupplierSinglClear;
import com.infolion.xdss3.singleClear.domain.UnClearCollect;
import com.infolion.xdss3.singleClear.domain.UnClearCustomBill;
import com.infolion.xdss3.singleClear.service.SupplierSinglClearService;
          
import com.infolion.xdss3.singleClear.domain.UnClearSupplieBill;
import com.infolion.xdss3.singleClear.service.UnClearSupplieBillService;
          
import com.infolion.xdss3.singleClear.domain.UnClearPayment;
import com.infolion.xdss3.singleClear.service.UnClearPaymentService;
          
import com.infolion.xdss3.financeSquare.domain.FundFlow;
import com.infolion.xdss3.financeSquare.service.FundFlowService;
          
import com.infolion.xdss3.financeSquare.domain.SettleSubject;
import com.infolion.xdss3.financeSquare.service.SettleSubjectService;
import com.infolion.platform.bo.domain.BusinessObject;
import com.infolion.platform.bo.service.BusinessObjectService;
import com.infolion.platform.dpframework.core.web.BaseMultiActionController;
import com.infolion.platform.dpframework.core.web.AbstractGenController;

/**
 * <pre>
 * 供应商单清帐(SupplierSinglClear)控制器类
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
public class SupplierSinglClearControllerGen extends AbstractGenController
{
	private final String boName = "SupplierSinglClear";

	public String getBoName()
	{
		return this.boName;
	}
	
	@Autowired
	protected SupplierSinglClearService supplierSinglClearService;
	
	public void setSupplierSinglClearService(SupplierSinglClearService supplierSinglClearService)
	{
		this.supplierSinglClearService = supplierSinglClearService;
	}
          
          
          
          


    
	/**
	 * 作废  
	 *   
	 * @param request
	 * @param response
	 */
	public void _blankOut (HttpServletRequest request, HttpServletResponse response)
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
	 * 清除分配  
	 *   
	 * @param request
	 * @param response
	 */
	public void _clearAssign (HttpServletRequest request, HttpServletResponse response)
	{
       
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
		SupplierSinglClear supplierSinglClear = this.supplierSinglClearService._getEntityCopy(id);
		request.setAttribute("isCreateCopy", "true");
		request.setAttribute("supplierSinglClear", supplierSinglClear);
		request.setAttribute("vt", getBusinessObject().getViewText());
        if(null!=getBusinessObject().getSubBusinessObject())
        {
			// 取得子业务对象
			for (BusinessObject bo : getBusinessObject().getSubBusinessObject())
			{
				request.setAttribute(bo.getBeanAttribute().getBeanInstanceName()+"Vt", bo.getViewText());
			}
		}
      	boolean haveBindWorkFlow = WorkflowService.getHaveBindWorkFlow("000000000325");
		request.setAttribute("haveBindWorkFlow", haveBindWorkFlow);
		request.setAttribute("workflowTaskId", "");	
		request.setAttribute("workflowNodeDefId", "");
		return new ModelAndView("xdss3/singleClear/supplierSinglClearAdd");
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
		supplierSinglClearService._delete(suppliersclearid,getBusinessObject());
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
		String supplierSinglClearIds = request.getParameter("supplierSinglClearIds");
		supplierSinglClearService._deletes(supplierSinglClearIds,getBusinessObject());
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
		SupplierSinglClear supplierSinglClear = new SupplierSinglClear();
		String workflowTaskId = request.getParameter("workflowTaskId");
		String workflowNodeDefId = request.getParameter("workflowNodeDefId");
		String id = request.getParameter("suppliersclearid");
		String businessId = request.getParameter("businessId");	
		if (null == id)
			id = businessId ;

		if (StringUtils.isNullBlank(id))
		{
supplierSinglClear = this.supplierSinglClearService._get(id);        }
        else
        {
           supplierSinglClear = this.supplierSinglClearService._get(id);
        }
    
      	boolean haveBindWorkFlow = WorkflowService.getHaveBindWorkFlow("000000000325");
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
		
		request.setAttribute("supplierSinglClear", supplierSinglClear);  
		return new ModelAndView("xdss3/singleClear/supplierSinglClearView");
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
		SupplierSinglClear supplierSinglClear = (SupplierSinglClear) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), SupplierSinglClear.class , true , request.getMethod(), true);
        //类型标识是从那个页面提交，view 表示从view页面提交流程。
        String type = request.getParameter("type");
// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
Set<UnClearSupplieBill> unClearSupplieBillmodifyItems = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { supplierSinglClear }, UnClearSupplieBill.class, null);
Set<UnClearSupplieBill> deletedUnClearSupplieBillSet = ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(), new Object[] { supplierSinglClear }, UnClearSupplieBill.class, null);
supplierSinglClear.setUnClearSupplieBill(unClearSupplieBillmodifyItems);
// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
Set<UnClearPayment> unClearPaymentmodifyItems = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { supplierSinglClear }, UnClearPayment.class, null);
Set<UnClearPayment> deletedUnClearPaymentSet = ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(), new Object[] { supplierSinglClear }, UnClearPayment.class, null);
supplierSinglClear.setUnClearPayment(unClearPaymentmodifyItems);
//绑定子对象(一对一关系)
FundFlow fundFlow = (FundFlow) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), FundFlow.class, false , request.getMethod(), true);
if(fundFlow!=null)
{
supplierSinglClear.setFundFlow(fundFlow);
fundFlow.setSupplierSinglClear(supplierSinglClear);
}
//绑定子对象(一对一关系)
SettleSubject settleSubject = (SettleSubject) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), SettleSubject.class, false , request.getMethod(), true);
if(settleSubject!=null)
{
supplierSinglClear.setSettleSubject(settleSubject);
settleSubject.setSupplierSinglClear(supplierSinglClear);
}
if (!StringUtils.isNullBlank(type) && type.equalsIgnoreCase("view"))
{
this.supplierSinglClearService._saveOrUpdate(supplierSinglClear
,deletedUnClearSupplieBillSet
,deletedUnClearPaymentSet
,getBusinessObject());
}
this.supplierSinglClearService._submitProcess(supplierSinglClear
,deletedUnClearSupplieBillSet
,deletedUnClearPaymentSet		,getBusinessObject());
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
		return new ModelAndView("xdss3/singleClear/supplierSinglClearManage");
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
		SupplierSinglClear supplierSinglClear  = new SupplierSinglClear();
		String suppliersclearid = request.getParameter("suppliersclearid");
		supplierSinglClear.setSuppliersclearid(suppliersclearid);
		LockService.unLockBOData(supplierSinglClear);
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
		SupplierSinglClear supplierSinglClear = (SupplierSinglClear) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), SupplierSinglClear.class , true , request.getMethod(), true);
// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
Set<UnClearSupplieBill> unClearSupplieBillmodifyItems = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { supplierSinglClear }, UnClearSupplieBill.class, null);
supplierSinglClear.setUnClearSupplieBill(unClearSupplieBillmodifyItems);
Set<UnClearSupplieBill> unClearSupplieBilldeleteItems = ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(), new Object[] { supplierSinglClear }, UnClearSupplieBill.class, null);
// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
Set<UnClearPayment> unClearPaymentmodifyItems = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { supplierSinglClear }, UnClearPayment.class, null);
supplierSinglClear.setUnClearPayment(unClearPaymentmodifyItems);
Set<UnClearPayment> unClearPaymentdeleteItems = ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(), new Object[] { supplierSinglClear }, UnClearPayment.class, null);
//绑定子对象(一对一关系)
FundFlow fundFlow = (FundFlow) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), FundFlow.class, false , request.getMethod(), true);
if(fundFlow!=null)
{
supplierSinglClear.setFundFlow(fundFlow);
fundFlow.setSupplierSinglClear(supplierSinglClear);
}
//绑定子对象(一对一关系)
SettleSubject settleSubject = (SettleSubject) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), SettleSubject.class, false , request.getMethod(), true);
if(settleSubject!=null)
{
supplierSinglClear.setSettleSubject(settleSubject);
settleSubject.setSupplierSinglClear(supplierSinglClear);
}
this.supplierSinglClearService._saveOrUpdate(supplierSinglClear
,unClearSupplieBilldeleteItems
,unClearPaymentdeleteItems,getBusinessObject());

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
				calActivity.setBusid(supplierSinglClear.getSuppliersclearid());
				calActivityService._update2(calActivity, BusinessObjectService.getBusinessObject("CalActivity"));
			}
			this.operateClose(response);
		}
	    else
		{	
		jo.put("suppliersclearid", supplierSinglClear.getSuppliersclearid());
jo.put("fundflowid", supplierSinglClear.getFundFlow()!=null ? supplierSinglClear.getFundFlow().getFundflowid() : "");
jo.put("settlesubjectid", supplierSinglClear.getSettleSubject()!=null ? supplierSinglClear.getSettleSubject().getSettlesubjectid() : "");String creator = supplierSinglClear.getCreator();
String creator_text = SysCachePoolUtils.getDictDataValue("YUSER", creator);
jo.put("creator_text", creator_text);
jo.put("creator", creator);
jo.put("createtime", supplierSinglClear.getCreatetime());
String lastmodifyer = supplierSinglClear.getLastmodifyer();
String lastmodifyer_text = SysCachePoolUtils.getDictDataValue("YUSER", lastmodifyer);
jo.put("lastmodifyer_text", lastmodifyer_text);
jo.put("lastmodifyer", lastmodifyer);
jo.put("lastmodifytime", supplierSinglClear.getLastmodifytime());		this.operateSuccessfullyWithString(response,jo.toString());
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
		SupplierSinglClear supplierSinglClear = new SupplierSinglClear();
		String id = request.getParameter("suppliersclearid");
		String workflowTaskId = request.getParameter("workflowTaskId");
		String workflowNodeDefId = request.getParameter("workflowNodeDefId");
		String businessId = request.getParameter("businessId");	
		if (null == id)
			id = businessId ;
			
		if (StringUtils.isNullBlank(id))
		{	
supplierSinglClear = this.supplierSinglClearService._getForEdit(id);        }
        else
        {
           supplierSinglClear = this.supplierSinglClearService._getForEdit(id);
        }
		
		BigDecimal sumclearamount = new BigDecimal(0);
		BigDecimal sumadjustamount = new BigDecimal(0);
		BigDecimal sumnowclearamount = new BigDecimal(0);
		for(UnClearPayment unClearPayment : supplierSinglClear.getUnClearPayment()){
			if(null !=unClearPayment.getNowclearamount())
			sumnowclearamount = sumnowclearamount.add(unClearPayment.getNowclearamount());
		
		}
		for(UnClearSupplieBill unClearSupplieBill : supplierSinglClear.getUnClearSupplieBill()){
			if(null != unClearSupplieBill.getClearamount())
			sumclearamount = sumclearamount.add(unClearSupplieBill.getClearamount());
			if(null != unClearSupplieBill.getAdjustamount())
			sumadjustamount = sumadjustamount.add(unClearSupplieBill.getAdjustamount());
		}
		request.setAttribute("sumclearamount", sumclearamount.toString());
		request.setAttribute("sumadjustamount", sumadjustamount.toString());
		request.setAttribute("sumnowclearamount", sumnowclearamount.toString());
		boolean haveBindWorkFlow = WorkflowService.getHaveBindWorkFlow("000000000325");
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
		request.setAttribute("supplierSinglClear", supplierSinglClear);
		return new ModelAndView("xdss3/singleClear/supplierSinglClearEdit");
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
	 * 查询  
	 *   
	 * @param request
	 * @param response
	 */
	public void _queryUnClear (HttpServletRequest request, HttpServletResponse response)
	{
       
	}
    
	/**
	 * 自动分配  
	 *   
	 * @param request
	 * @param response
	 */
	public void _autoAssign (HttpServletRequest request, HttpServletResponse response)
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
	    SupplierSinglClear supplierSinglClear = new SupplierSinglClear();
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
		request.setAttribute("supplierSinglClear",supplierSinglClear);
      	boolean haveBindWorkFlow = WorkflowService.getHaveBindWorkFlow("000000000325");
		request.setAttribute("haveBindWorkFlow", haveBindWorkFlow);
		request.setAttribute("workflowTaskId", workflowTaskId);	
		request.setAttribute("workflowNodeDefId", workflowNodeDefId);
	    return new ModelAndView("xdss3/singleClear/supplierSinglClearAdd");
	}
	
    
	/**
	 * 确认清帐  
	 *   
	 * @param request
	 * @param response
	 */
	public void _submitClear (HttpServletRequest request, HttpServletResponse response)
	{
       
	}
}