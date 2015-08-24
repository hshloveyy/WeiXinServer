/*
 * @(#)BillClearCollectControllerGen.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年09月16日 09点35分05秒
 *  描　述：创建
 */
package com.infolion.xdss3.billclearcollectGen.web;


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
import com.infolion.xdss3.billclearcollect.domain.BillClearCollect;
import com.infolion.xdss3.billclearcollect.service.BillClearCollectService;
          
import com.infolion.xdss3.settlesubjectbcc.domain.SettleSubjectBcc;
import com.infolion.xdss3.settlesubjectbcc.service.SettleSubjectBccService;

          
import com.infolion.xdss3.billclearitem.domain.BillClearItem;
import com.infolion.xdss3.billclearitem.service.BillClearItemService;
          
import com.infolion.xdss3.fundflowBcc.domain.FundFlowBcc;
import com.infolion.xdss3.fundflowBcc.service.FundFlowBccService;
          
import com.infolion.xdss3.billincollect.domain.BillInCollect;
import com.infolion.xdss3.billincollect.service.BillInCollectService;
import com.infolion.platform.bo.domain.BusinessObject;
import com.infolion.platform.bo.service.BusinessObjectService;
import com.infolion.platform.dpframework.core.web.BaseMultiActionController;
import com.infolion.platform.dpframework.core.web.AbstractGenController;

/**
 * <pre>
 * 票清收款(BillClearCollect)控制器类
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
public class BillClearCollectControllerGen extends AbstractGenController
{
	private final String boName = "BillClearCollect";

	public String getBoName()
	{
		return this.boName;
	}
	
	@Autowired
	protected BillClearCollectService billClearCollectService;
	
	public void setBillClearCollectService(BillClearCollectService billClearCollectService)
	{
		this.billClearCollectService = billClearCollectService;
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
	 * 重分配提交  
	 *   
	 * @param request
	 * @param response
	 */
	public void _submitForReassign (HttpServletRequest request, HttpServletResponse response)
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
	    BillClearCollect billClearCollect = new BillClearCollect();
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
		request.setAttribute("billClearCollect",billClearCollect);
      	boolean haveBindWorkFlow = WorkflowService.getHaveBindWorkFlow("000000000282");
		request.setAttribute("haveBindWorkFlow", haveBindWorkFlow);
		request.setAttribute("workflowTaskId", workflowTaskId);	
		request.setAttribute("workflowNodeDefId", workflowNodeDefId);
	    return new ModelAndView("xdss3/billclearcollect/billClearCollectAdd");
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
		String id = request.getParameter("billclearid");
		BillClearCollect billClearCollect = this.billClearCollectService._getEntityCopy(id);
		request.setAttribute("isCreateCopy", "true");
		request.setAttribute("billClearCollect", billClearCollect);
		request.setAttribute("vt", getBusinessObject().getViewText());
        if(null!=getBusinessObject().getSubBusinessObject())
        {
			// 取得子业务对象
			for (BusinessObject bo : getBusinessObject().getSubBusinessObject())
			{
				request.setAttribute(bo.getBeanAttribute().getBeanInstanceName()+"Vt", bo.getViewText());
			}
		}
      	boolean haveBindWorkFlow = WorkflowService.getHaveBindWorkFlow("000000000282");
		request.setAttribute("haveBindWorkFlow", haveBindWorkFlow);
		request.setAttribute("workflowTaskId", "");	
		request.setAttribute("workflowNodeDefId", "");
		return new ModelAndView("xdss3/billclearcollect/billClearCollectAdd");
	}

	
	/**
	 * 删除  
	 *   
	 * @param request
	 * @param response
	 */
	public void _delete(HttpServletRequest request, HttpServletResponse response)
	{
		String billclearid = request.getParameter("billclearid");
		billClearCollectService._delete(billclearid,getBusinessObject());
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
		String billClearCollectIds = request.getParameter("billClearCollectIds");
		billClearCollectService._deletes(billClearCollectIds,getBusinessObject());
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
		BillClearCollect billClearCollect = new BillClearCollect();
		String workflowTaskId = request.getParameter("workflowTaskId");
		String workflowNodeDefId = request.getParameter("workflowNodeDefId");
		String id = request.getParameter("billclearid");
		String businessId = request.getParameter("businessId");	
		if (null == id)
			id = businessId ;

		if (StringUtils.isNullBlank(id))
		{
billClearCollect = this.billClearCollectService._get(id);        }
        else
        {
           billClearCollect = this.billClearCollectService._get(id);
        }
    
      	boolean haveBindWorkFlow = WorkflowService.getHaveBindWorkFlow("000000000282");
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
		
		request.setAttribute("billClearCollect", billClearCollect);  
		return new ModelAndView("xdss3/billclearcollect/billClearCollectView");
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
		BillClearCollect billClearCollect = (BillClearCollect) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), BillClearCollect.class , true , request.getMethod(), true);
        //类型标识是从那个页面提交，view 表示从view页面提交流程。
        String type = request.getParameter("type");
//绑定子对象(一对一关系)
SettleSubjectBcc settleSubjectBcc = (SettleSubjectBcc) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), SettleSubjectBcc.class, false , request.getMethod(), true);
if(settleSubjectBcc!=null)
{
billClearCollect.setSettleSubjectBcc(settleSubjectBcc);
settleSubjectBcc.setBillClearCollect(billClearCollect);
}
// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
Set<BillClearItem> billclearitemmodifyItems = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { billClearCollect }, BillClearItem.class, null);
Set<BillClearItem> deletedBillClearItemSet = ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(), new Object[] { billClearCollect }, BillClearItem.class, null);
billClearCollect.setBillclearitem(billclearitemmodifyItems);
//绑定子对象(一对一关系)
FundFlowBcc fundFlowBcc = (FundFlowBcc) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), FundFlowBcc.class, false , request.getMethod(), true);
if(fundFlowBcc!=null)
{
billClearCollect.setFundFlowBcc(fundFlowBcc);
fundFlowBcc.setBillClearCollect(billClearCollect);
}
// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
Set<BillInCollect> billincollectmodifyItems = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { billClearCollect }, BillInCollect.class, null);
Set<BillInCollect> deletedBillInCollectSet = ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(), new Object[] { billClearCollect }, BillInCollect.class, null);
billClearCollect.setBillincollect(billincollectmodifyItems);
if (!"view".equalsIgnoreCase(type))
{
this.billClearCollectService._saveOrUpdate(billClearCollect
,deletedBillClearItemSet
,deletedBillInCollectSet
,getBusinessObject());
}
this.billClearCollectService._submitProcess(billClearCollect
,deletedBillClearItemSet
,deletedBillInCollectSet		,getBusinessObject());
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
		return new ModelAndView("xdss3/billclearcollect/billClearCollectManage");
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
		BillClearCollect billClearCollect  = new BillClearCollect();
		String billclearid = request.getParameter("billclearid");
		billClearCollect.setBillclearid(billclearid);
		LockService.unLockBOData(billClearCollect);
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
		BillClearCollect billClearCollect = (BillClearCollect) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), BillClearCollect.class , true , request.getMethod(), true);
//绑定子对象(一对一关系)
SettleSubjectBcc settleSubjectBcc = (SettleSubjectBcc) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), SettleSubjectBcc.class, false , request.getMethod(), true);
if(settleSubjectBcc!=null)
{
billClearCollect.setSettleSubjectBcc(settleSubjectBcc);
settleSubjectBcc.setBillClearCollect(billClearCollect);
}
// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
Set<BillClearItem> billclearitemmodifyItems = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { billClearCollect }, BillClearItem.class, null);
billClearCollect.setBillclearitem(billclearitemmodifyItems);
Set<BillClearItem> billclearitemdeleteItems = ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(), new Object[] { billClearCollect }, BillClearItem.class, null);
//绑定子对象(一对一关系)
FundFlowBcc fundFlowBcc = (FundFlowBcc) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), FundFlowBcc.class, false , request.getMethod(), true);
if(fundFlowBcc!=null)
{
billClearCollect.setFundFlowBcc(fundFlowBcc);
fundFlowBcc.setBillClearCollect(billClearCollect);
}
// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
Set<BillInCollect> billincollectmodifyItems = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { billClearCollect }, BillInCollect.class, null);
billClearCollect.setBillincollect(billincollectmodifyItems);
Set<BillInCollect> billincollectdeleteItems = ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(), new Object[] { billClearCollect }, BillInCollect.class, null);
this.billClearCollectService._saveOrUpdate(billClearCollect
,billclearitemdeleteItems
,billincollectdeleteItems,getBusinessObject());

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
				calActivity.setBusid(billClearCollect.getBillclearid());
				calActivityService._update2(calActivity, BusinessObjectService.getBusinessObject("CalActivity"));
			}
			this.operateClose(response);
		}
	    else
		{	
jo.put("billclearno", billClearCollect.getBillclearno());		jo.put("billclearid", billClearCollect.getBillclearid());
jo.put("settlesubjectid", billClearCollect.getSettleSubjectBcc()!=null ? billClearCollect.getSettleSubjectBcc().getSettlesubjectid() : "");
jo.put("fundflowid", billClearCollect.getFundFlowBcc()!=null ? billClearCollect.getFundFlowBcc().getFundflowid() : "");String creator = billClearCollect.getCreator();
String creator_text = SysCachePoolUtils.getDictDataValue("YUSER", creator);
jo.put("creator_text", creator_text);
jo.put("creator", creator);
jo.put("createtime", billClearCollect.getCreatetime());
String lastmodifyer = billClearCollect.getLastmodifyer();
String lastmodifyer_text = SysCachePoolUtils.getDictDataValue("YUSER", lastmodifyer);
jo.put("lastmodifyer_text", lastmodifyer_text);
jo.put("lastmodifyer", lastmodifyer);
jo.put("lastmodifytime", billClearCollect.getLastmodifytime());		this.operateSuccessfullyWithString(response,jo.toString());
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
		BillClearCollect billClearCollect = new BillClearCollect();
		String id = request.getParameter("billclearid");
		String workflowTaskId = request.getParameter("workflowTaskId");
		String workflowNodeDefId = request.getParameter("workflowNodeDefId");
		String businessId = request.getParameter("businessId");	
		if (null == id)
			id = businessId ;
			
		if (StringUtils.isNullBlank(id))
		{	
billClearCollect = this.billClearCollectService._getForEdit(id);        }
        else
        {
           billClearCollect = this.billClearCollectService._getForEdit(id);
        }
		BigDecimal sumclearamount = new BigDecimal(0);
		BigDecimal sumadjustamount = new BigDecimal(0);
		BigDecimal sumnowclearamount = new BigDecimal(0);
		for(BillInCollect billincollect : billClearCollect.getBillincollect()){
			if(null !=billincollect.getNowclearamount())
			sumnowclearamount = sumnowclearamount.add(billincollect.getNowclearamount());
		
		}
		for(BillClearItem Billclearitem : billClearCollect.getBillclearitem()){
			if(null != Billclearitem.getClearbillamount())
			sumclearamount = sumclearamount.add(Billclearitem.getClearbillamount());
			if(null != Billclearitem.getAdjustamount())
			sumadjustamount = sumadjustamount.add(Billclearitem.getAdjustamount());
		}
		request.setAttribute("sumclearamount", sumclearamount.toString());
		request.setAttribute("sumadjustamount", sumadjustamount.toString());
		request.setAttribute("sumnowclearamount", sumnowclearamount.toString());
		boolean haveBindWorkFlow = WorkflowService.getHaveBindWorkFlow("000000000282");
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
		request.setAttribute("billClearCollect", billClearCollect);
		return new ModelAndView("xdss3/billclearcollect/billClearCollectEdit");
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
	 * 自动分配  
	 *   
	 * @param request
	 * @param response
	 */
	public void _autoAssign (HttpServletRequest request, HttpServletResponse response)
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
}