/*
 * @(#)BillPurchasedControllerGen.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年12月15日 16点10分17秒
 *  描　述：创建
 */
package com.infolion.xdss3.billpurchasedGen.web;


import java.math.BigDecimal;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

import com.infolion.platform.bo.domain.BusinessObject;
import com.infolion.platform.bo.service.BusinessObjectService;
import com.infolion.platform.calendar.domain.CalActivity;
import com.infolion.platform.calendar.service.CalActivityService;
import com.infolion.platform.dpframework.core.cache.SysCachePoolUtils;
import com.infolion.platform.dpframework.core.web.AbstractGenController;
import com.infolion.platform.dpframework.uicomponent.lock.LockService;
import com.infolion.platform.dpframework.util.EasyApplicationContextUtils;
import com.infolion.platform.dpframework.util.ExBeanUtils;
import com.infolion.platform.dpframework.util.StringUtils;
import com.infolion.platform.workflow.engine.WorkflowService;
import com.infolion.xdss3.billPurBankItem.domain.BillPurBankItem;
import com.infolion.xdss3.billPurBillItem.domain.BillPurBillItem;
import com.infolion.xdss3.billPurReBankItem.domain.BillPurReBankItem;
import com.infolion.xdss3.billPurReBankItem.domain.BillPurReBankTwo;
import com.infolion.xdss3.billpurchased.domain.BillPurchased;
import com.infolion.xdss3.billpurchased.service.BillPurchasedService;
import com.infolion.xdss3.financeSquare.domain.FundFlow;
import com.infolion.xdss3.financeSquare.domain.SettleSubject;

/**
 * <pre>
 * 出口押汇(BillPurchased)控制器类
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
public class BillPurchasedControllerGen extends AbstractGenController
{
	private final String boName = "BillPurchased";

	public String getBoName()
	{
		return this.boName;
	}
	
	@Autowired
	protected BillPurchasedService billPurchasedService;
	
	public void setBillPurchasedService(BillPurchasedService billPurchasedService)
	{
		this.billPurchasedService = billPurchasedService;
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
	    BillPurchased billPurchased = new BillPurchased();
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
        com.infolion.platform.console.sys.context.UserContext xdssUserContext = com.infolion.platform.console.sys.context.UserContextHolder
        .getLocalUserContext().getUserContext();
        if (null != xdssUserContext)
            billPurchased.setDept_id(xdssUserContext.getSysUser().getDeptId());
        
		request.setAttribute("billPurchased",billPurchased);
      	boolean haveBindWorkFlow = WorkflowService.getHaveBindWorkFlow("000000000351");
		request.setAttribute("haveBindWorkFlow", haveBindWorkFlow);
		request.setAttribute("workflowTaskId", workflowTaskId);	
		request.setAttribute("workflowNodeDefId", workflowNodeDefId);
	    return new ModelAndView("xdss3/billpurchased/billPurchasedAdd");
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
		String id = request.getParameter("billpurid");
		BillPurchased billPurchased = this.billPurchasedService._getEntityCopy(id);
		request.setAttribute("isCreateCopy", "true");
		request.setAttribute("billPurchased", billPurchased);
		request.setAttribute("vt", getBusinessObject().getViewText());
        if(null!=getBusinessObject().getSubBusinessObject())
        {
			// 取得子业务对象
			for (BusinessObject bo : getBusinessObject().getSubBusinessObject())
			{
				request.setAttribute(bo.getBeanAttribute().getBeanInstanceName()+"Vt", bo.getViewText());
			}
		}
      	boolean haveBindWorkFlow = WorkflowService.getHaveBindWorkFlow("000000000351");
		request.setAttribute("haveBindWorkFlow", haveBindWorkFlow);
		request.setAttribute("workflowTaskId", "");	
		request.setAttribute("workflowNodeDefId", "");
		return new ModelAndView("xdss3/billpurchased/billPurchasedAdd");
	}

	
	/**
	 * 删除  
	 *   
	 * @param request
	 * @param response
	 */
	public void _delete(HttpServletRequest request, HttpServletResponse response)
	{
		String billpurid = request.getParameter("billpurid");
		billPurchasedService._delete(billpurid,getBusinessObject());
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
		String billPurchasedIds = request.getParameter("billPurchasedIds");
		billPurchasedService._deletes(billPurchasedIds,getBusinessObject());
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
		BillPurchased billPurchased = new BillPurchased();
		String workflowTaskId = request.getParameter("workflowTaskId");
		String workflowNodeDefId = request.getParameter("workflowNodeDefId");
		String id = request.getParameter("billpurid");
		String businessId = request.getParameter("businessId");	
		if (null == id)
			id = businessId ;

        if (StringUtils.isNullBlank(id)) {
            billPurchased = this.billPurchasedService._get(id);
        } else {
            billPurchased = this.billPurchasedService._get(id);
        }
    
      	boolean haveBindWorkFlow = WorkflowService.getHaveBindWorkFlow("000000000351");
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
		
		request.setAttribute("billPurchased", billPurchased);  
		return new ModelAndView("xdss3/billpurchased/billPurchasedView");
	}
	
	/**
	 * 提交  
	 *   
	 * @param request
	 * @param response
	 */
	 
    public void _submitProcess(HttpServletRequest request, HttpServletResponse response) {
        // 绑定主对象值
        BillPurchased billPurchased = (BillPurchased) ExBeanUtils.bindBusinessObjectData(
                request.getParameterMap(), BillPurchased.class, true, request.getMethod(), true);
        // 类型标识是从那个页面提交，view 表示从view页面提交流程。
        String type = request.getParameter("type");
        // 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
        Set<BillPurBillItem> billPurBillItemmodifyItems = ExBeanUtils.bindModifySubBoData(
                request.getParameterMap(), new Object[] { billPurchased }, BillPurBillItem.class,
                null);
        Set<BillPurBillItem> deletedBillPurBillItemSet = ExBeanUtils.bindDeleteSubBoData(
                request.getParameterMap(), new Object[] { billPurchased }, BillPurBillItem.class,
                null);
        billPurchased.setBillPurBillItem(billPurBillItemmodifyItems);
        // 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
        Set<BillPurBankItem> billPurBankItemmodifyItems = ExBeanUtils.bindModifySubBoData(
                request.getParameterMap(), new Object[] { billPurchased }, BillPurBankItem.class,
                null);
        Set<BillPurBankItem> deletedBillPurBankItemSet = ExBeanUtils.bindDeleteSubBoData(
                request.getParameterMap(), new Object[] { billPurchased }, BillPurBankItem.class,
                null);
        billPurchased.setBillPurBankItem(billPurBankItemmodifyItems);
        // 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
        Set<BillPurReBankItem> billPurReBankItemmodifyItems = ExBeanUtils.bindModifySubBoData(
                request.getParameterMap(), new Object[] { billPurchased }, BillPurReBankItem.class,
                null);
        Set<BillPurReBankItem> deletedBillPurReBankItemSet = ExBeanUtils.bindDeleteSubBoData(
                request.getParameterMap(), new Object[] { billPurchased }, BillPurReBankItem.class,
                null);
        billPurchased.setBillPurReBankItem(billPurReBankItemmodifyItems);
        // 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
        Set<BillPurReBankTwo> billPurReBankTwomodifyItems = ExBeanUtils.bindModifySubBoData(
                request.getParameterMap(), new Object[] { billPurchased }, BillPurReBankTwo.class,
                null);
        billPurchased.setBillPurReBankTwo(billPurReBankTwomodifyItems);
        // 绑定子对象(一对一关系)
        SettleSubject settleSubject = (SettleSubject) ExBeanUtils.bindBusinessObjectData(
                request.getParameterMap(), SettleSubject.class, false, request.getMethod(), true);
        if (settleSubject != null) {
            billPurchased.setSettleSubject(settleSubject);
            settleSubject.setBillPurchased(billPurchased);
        }
        // 绑定子对象(一对一关系)
        FundFlow fundFlow = (FundFlow) ExBeanUtils.bindBusinessObjectData(
                request.getParameterMap(), FundFlow.class, false, request.getMethod(), true);
        if (fundFlow != null) {
            billPurchased.setFundFlow(fundFlow);
            fundFlow.setBillPurchased(billPurchased);
        }
        if (!"view".equalsIgnoreCase(type)) {
            this.billPurchasedService._saveOrUpdate(billPurchased, deletedBillPurBillItemSet,
                    deletedBillPurBankItemSet, deletedBillPurReBankItemSet, getBusinessObject());
            this.billPurchasedService.updateReBankTwo(billPurchased.getBillpurid()); // 更新还押汇银行2
        }

        // HONGJJ 添加待办信息
        String workflowBusinessNote = "";
        String deptName = com.infolion.platform.console.sys.context.UserContextHolder.getLocalUserContext().getUserContext().getSysUser().getDeptName();
        String creator = com.infolion.platform.console.sys.context.UserContextHolder.getLocalUserContext().getUserContext().getSysUser().getRealName();
        String bankAccount = "";
        String bankName = "";
        if (billPurchased.getBillPurBankItem().iterator().hasNext()) {
            bankAccount = billPurchased.getBillPurBankItem().iterator().next().getBankacc();
            bankName = this.billPurchasedService.getBankInfoByAccount(bankAccount);
            workflowBusinessNote = billPurchased.getBillpur_no() + "|" + deptName + "|" + creator + "|"
                    + billPurchased.getCurrency() + "|金额" + billPurchased.getApplyamount() + "|" + bankName;
        } else {
            workflowBusinessNote = billPurchased.getBillpur_no() + "|" + deptName + "|" + creator + "|"
                    + billPurchased.getCurrency() + "|金额" + billPurchased.getApplyamount();
        }
        billPurchased.setWorkflowBusinessNote(workflowBusinessNote);
        
        this.billPurchasedService._submitProcess(billPurchased, deletedBillPurBillItemSet,
                deletedBillPurBankItemSet, deletedBillPurReBankItemSet, getBusinessObject());
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
		return new ModelAndView("xdss3/billpurchased/billPurchasedManage");
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
		BillPurchased billPurchased  = new BillPurchased();
		String billpurid = request.getParameter("billpurid");
		billPurchased.setBillpurid(billpurid);
		LockService.unLockBOData(billPurchased);
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
    public void _saveOrUpdate(HttpServletRequest request, HttpServletResponse response) {
        JSONObject jo = new JSONObject();
        // 绑定主对象值
        BillPurchased billPurchased = (BillPurchased) ExBeanUtils.bindBusinessObjectData(
                request.getParameterMap(), BillPurchased.class, true, request.getMethod(), true);
        // 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
        Set<BillPurBillItem> billPurBillItemmodifyItems = ExBeanUtils.bindModifySubBoData(
                request.getParameterMap(), new Object[] { billPurchased }, BillPurBillItem.class,
                null);
        billPurchased.setBillPurBillItem(billPurBillItemmodifyItems);
        Set<BillPurBillItem> billPurBillItemdeleteItems = ExBeanUtils.bindDeleteSubBoData(
                request.getParameterMap(), new Object[] { billPurchased }, BillPurBillItem.class,
                null);
        // 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
        Set<BillPurBankItem> billPurBankItemmodifyItems = ExBeanUtils.bindModifySubBoData(
                request.getParameterMap(), new Object[] { billPurchased }, BillPurBankItem.class,
                null);
        billPurchased.setBillPurBankItem(billPurBankItemmodifyItems);
        Set<BillPurBankItem> billPurBankItemdeleteItems = ExBeanUtils.bindDeleteSubBoData(
                request.getParameterMap(), new Object[] { billPurchased }, BillPurBankItem.class,
                null);
        // 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
        Set<BillPurReBankItem> billPurReBankItemmodifyItems = ExBeanUtils.bindModifySubBoData(
                request.getParameterMap(), new Object[] { billPurchased }, BillPurReBankItem.class,
                null);
        billPurchased.setBillPurReBankItem(billPurReBankItemmodifyItems);
        // 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
        Set<BillPurReBankTwo> billPurReBankTwomodifyItems = ExBeanUtils.bindModifySubBoData(
                request.getParameterMap(), new Object[] { billPurchased }, BillPurReBankTwo.class,
                null);
//        for (BillPurReBankTwo two : billPurReBankTwomodifyItems) {
//            two.setRealmoney3(new BigDecimal(0));
//            two.setRealmoney4(new BigDecimal(0));
//        }
        billPurchased.setBillPurReBankTwo(billPurReBankTwomodifyItems);        
        Set<BillPurReBankItem> billPurReBankItemdeleteItems = ExBeanUtils.bindDeleteSubBoData(
                request.getParameterMap(), new Object[] { billPurchased }, BillPurReBankItem.class,
                null);
        // 绑定子对象(一对一关系)
        SettleSubject settleSubject = (SettleSubject) ExBeanUtils.bindBusinessObjectData(
                request.getParameterMap(), SettleSubject.class, false, request.getMethod(), true);
        if (settleSubject != null) {
            billPurchased.setSettleSubject(settleSubject);
            settleSubject.setBillPurchased(billPurchased);
        }
        // 绑定子对象(一对一关系)
        FundFlow fundFlow = (FundFlow) ExBeanUtils.bindBusinessObjectData(
                request.getParameterMap(), FundFlow.class, false, request.getMethod(), true);
        if (fundFlow != null) {
            billPurchased.setFundFlow(fundFlow);
            fundFlow.setBillPurchased(billPurchased);
        }
        this.billPurchasedService._saveOrUpdate(billPurchased, billPurBillItemdeleteItems,
                billPurBankItemdeleteItems, billPurReBankItemdeleteItems, getBusinessObject());
        this.billPurchasedService.updateReBankTwo(billPurchased.getBillpurid()); // 更新还押汇银行2
        
        // 如果返回参数带有calActivityId，将业务对象保存到相应的CalActivity中
        String calActivityId = request.getParameter("calActivityId");
        if (StringUtils.isNotBlank(calActivityId)) {
            CalActivityService calActivityService = (CalActivityService) EasyApplicationContextUtils
                    .getBeanByName("calActivityService");
            CalActivity calActivity = calActivityService._get(calActivityId);
            if (calActivity != null) {
                calActivity.setBoid(this.getBusinessObject().getBoId());
                calActivity.setBoname(this.getBoName());
                calActivity.setBusid(billPurchased.getBillpurid());
                calActivityService._update2(calActivity,
                        BusinessObjectService.getBusinessObject("CalActivity"));
            }
            this.operateClose(response);
        } else {
            jo.put("billpurid", billPurchased.getBillpurid());
            jo.put("settlesubjectid", billPurchased.getSettleSubject() != null ? billPurchased
                    .getSettleSubject().getSettlesubjectid() : "");
            jo.put("fundflowid", billPurchased.getFundFlow() != null ? billPurchased.getFundFlow()
                    .getFundflowid() : "");
            String creator = billPurchased.getCreator();
            String creator_text = SysCachePoolUtils.getDictDataValue("YUSER", creator);
            jo.put("creator_text", creator_text);
            jo.put("creator", creator);
            jo.put("createtime", billPurchased.getCreatetime());
            jo.put("lastmodifytime", billPurchased.getLastmodifytime());
            jo.put("billpur_no", billPurchased.getBillpur_no());
            this.operateSuccessfullyWithString(response, jo.toString());
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
    public ModelAndView _edit(HttpServletRequest request, HttpServletResponse response) {
        BillPurchased billPurchased = new BillPurchased();
        String id = request.getParameter("billpurid");
        String workflowTaskId = request.getParameter("workflowTaskId");
        String workflowNodeDefId = request.getParameter("workflowNodeDefId");
        String businessId = request.getParameter("businessId");
        if (null == id)
            id = businessId;

        if (StringUtils.isNullBlank(id)) {
            billPurchased = this.billPurchasedService._getForEdit(id);
        } else {
            billPurchased = this.billPurchasedService._getForEdit(id);
        }

        if ( "业务填写还押汇信息".equals(billPurchased.getProcessstate()) ) {
        	billPurchased.setReaccountdate(" ");
        	billPurchased.setRevoucherdate(" ");
        	billPurchased.setRetext(" ");
            billPurchased.setSettleSubject(null);
            billPurchased.setFundFlow(null);
            this.billPurchasedService.updateBIllPurchased(billPurchased);
            this.billPurchasedService.deleteSubObject(billPurchased.getBillpurid());
        }
        
        boolean haveBindWorkFlow = WorkflowService.getHaveBindWorkFlow("000000000351");
        request.setAttribute("businessId", id);
        request.setAttribute("workflowTaskId", workflowTaskId);
        request.setAttribute("workflowNodeDefId", workflowNodeDefId);
        request.setAttribute("haveBindWorkFlow", haveBindWorkFlow);
        request.setAttribute("vt", getBusinessObject().getViewText());
        if (null != getBusinessObject().getSubBusinessObject()) {
            // 取得子业务对象
            for (BusinessObject bo : getBusinessObject().getSubBusinessObject()) {
                request.setAttribute(bo.getBeanAttribute().getBeanInstanceName() + "Vt",
                        bo.getViewText());
            }
        }
        request.setAttribute("billPurchased", billPurchased);
        return new ModelAndView("xdss3/billpurchased/billPurchasedEdit");
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
	 * 查看流程状态  
	 *   
	 * @param request
	 * @param response
	 */
	public void _viewProcessState (HttpServletRequest request, HttpServletResponse response)
	{
       
	}
    

}