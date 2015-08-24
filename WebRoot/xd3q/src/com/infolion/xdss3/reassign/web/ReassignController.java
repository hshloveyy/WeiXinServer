/*
 * @(#)ReassignController.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年07月13日 09点39分00秒
 *  描　述：创建
 */
package com.infolion.xdss3.reassign.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.infolion.platform.bo.domain.BusinessObject;
import com.infolion.platform.bo.service.BusinessObjectService;
import com.infolion.platform.calendar.domain.CalActivity;
import com.infolion.platform.calendar.service.CalActivityService;
import com.infolion.platform.console.sys.context.UserContext;
import com.infolion.platform.console.sys.context.UserContextHolder;
import com.infolion.platform.dpframework.core.BusinessException;
import com.infolion.platform.dpframework.core.annotation.BDPController;
import com.infolion.platform.dpframework.core.cache.SysCachePoolUtils;
import com.infolion.platform.dpframework.util.EasyApplicationContextUtils;
import com.infolion.platform.dpframework.util.ExBeanUtils;
import com.infolion.platform.dpframework.util.StringUtils;
import com.infolion.platform.util.MultyGridData;
import com.infolion.platform.workflow.engine.WorkflowService;
import com.infolion.xdss3.CommonDataAuth;
import com.infolion.xdss3.Constants;
import com.infolion.xdss3.billClear.web.BillClearPaymentController;
import com.infolion.xdss3.billclearcollect.web.BillClearCollectController;
import com.infolion.xdss3.collect.web.CollectController;
import com.infolion.xdss3.customerDrawback.web.CustomerRefundmentController;
import com.infolion.xdss3.masterData.domain.CertificateNo;
import com.infolion.xdss3.payment.web.HomePaymentController;
import com.infolion.xdss3.payment.web.ImportPaymentController;
import com.infolion.xdss3.reassign.ReassignConstant;
import com.infolion.xdss3.reassign.domain.Reassign;
import com.infolion.xdss3.reassignGen.web.ReassignControllerGen;
import com.infolion.xdss3.singleClear.domain.IKeyValue;
import com.infolion.xdss3.singleClear.domain.InfoObject;
import com.infolion.xdss3.supplierDrawback.web.SupplierRefundmentController;
import com.infolion.xdss3.voucher.service.VoucherService;
import com.infolion.xdss3.singleClear.service.CustomerClearAccountService;

/**
 * <pre>
 * 重分配(Reassign)控制器类
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
@BDPController(parent = "baseMultiActionController")
public class ReassignController extends ReassignControllerGen {
	@Autowired
	private CollectController collectController;
	@Autowired
	private HomePaymentController homePaymentController;
	@Autowired
	private ImportPaymentController importPaymentController;
	@Autowired
	private BillClearCollectController billClearCollectController;
	@Autowired
	private BillClearPaymentController billClearPaymentController;
	@Autowired
	private CustomerRefundmentController customerRefundmentController;
	@Autowired
	private SupplierRefundmentController supplierRefundmentController;
	
	public void setCollectController(CollectController collectController) {
		this.collectController = collectController;
	}
	public void setHomePaymentController(HomePaymentController homePaymentController) {
		this.homePaymentController = homePaymentController;
	}
	public void setImportPaymentController(
			ImportPaymentController importPaymentController) {
		this.importPaymentController = importPaymentController;
	}
	public void setBillClearCollectController(
			BillClearCollectController billClearCollectController) {
		this.billClearCollectController = billClearCollectController;
	}
	public void setBillClearPaymentController(
			BillClearPaymentController billClearPaymentController) {
		this.billClearPaymentController = billClearPaymentController;
	}
	public void setCustomerRefundmentController(
			CustomerRefundmentController customerRefundmentController) {
		this.customerRefundmentController = customerRefundmentController;
	}
	public void setSupplierRefundmentController(
			SupplierRefundmentController supplierRefundmentController) {
		this.supplierRefundmentController = supplierRefundmentController;
	}
	
	@Autowired
	protected VoucherService voucherService;

	/**
	 * @param voucherService
	 *            the voucherService to set
	 */
	public void setVoucherService(VoucherService voucherService) {
		this.voucherService = voucherService;
	}
	
	@Autowired
	protected CustomerClearAccountService customerClearAccountService;

	/**
	 * @param voucherService
	 *            the voucherService to set
	 */
	public void setCustomerClearAccountService(CustomerClearAccountService customerClearAccountService) {
		this.customerClearAccountService = customerClearAccountService;
	}
	
	/**
	 * 创建  
	 *   
	 * @param request
	 * @param response
	 */
    public ModelAndView _create(HttpServletRequest request, HttpServletResponse response){
        String calActivityId = request.getParameter("calActivityId");
        if(StringUtils.isNotBlank(calActivityId))
            request.setAttribute("calActivityId", calActivityId);
        Reassign reassign = new Reassign();
        // 获取当前人员的部门ID
        UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
//        if(null != userContext){
//            reassign.setDept_id(userContext.getSysDept().getDeptid());
//        }
        String workflowTaskId = request.getParameter("workflowTaskId");
        String workflowNodeDefId = request.getParameter("workflowNodeDefId");
        request.setAttribute("vt", getBusinessObject().getViewText());
        if(null != getBusinessObject().getSubBusinessObject()){
            // 取得子业务对象
            for(BusinessObject bo : getBusinessObject().getSubBusinessObject()){
                request.setAttribute(bo.getBeanAttribute().getBeanInstanceName() + "Vt", bo.getViewText());
            }
        }
        String companycode = this.collectService.getUserCompany();
        request.setAttribute("companycode", companycode);
        request.setAttribute("deptcode", this.reassignService.getDeptcodeByUsername(userContext.getSysUser().getUserName()));
        request.setAttribute("reassign", reassign);
        boolean haveBindWorkFlow = WorkflowService.getHaveBindWorkFlow("000000000328");
        request.setAttribute("haveBindWorkFlow", haveBindWorkFlow);
        request.setAttribute("workflowTaskId", workflowTaskId);
        request.setAttribute("workflowNodeDefId", workflowNodeDefId);
        return new ModelAndView("xdss3/reassign/reassignAdd");
    }
    
    /**
     * 编辑  
     *   
     * @param request
     * @param response
     */
    public ModelAndView _edit(HttpServletRequest request, HttpServletResponse response){
        Reassign reassign = new Reassign();
        String id = request.getParameter("reassignid");
        String workflowTaskId = request.getParameter("workflowTaskId");
        String workflowNodeDefId = request.getParameter("workflowNodeDefId");
        String businessId = request.getParameter("businessId");
        if(null == id)
            id = businessId;

        if(StringUtils.isNullBlank(id)){
            reassign = this.reassignService._getForEdit(id);
        }else{
            reassign = this.reassignService._getForEdit(id);
        }
        UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();

        boolean haveBindWorkFlow = WorkflowService.getHaveBindWorkFlow("000000000328");
        String companycode = this.collectService.getUserCompany();
        request.setAttribute("companycode", companycode);
        
        request.setAttribute("deptcode", this.reassignService.getDeptcodeByUsername(userContext.getSysUser().getUserName()));
        request.setAttribute("businessId", id);
        request.setAttribute("workflowTaskId", workflowTaskId);
        request.setAttribute("workflowNodeDefId", workflowNodeDefId);
        request.setAttribute("haveBindWorkFlow", haveBindWorkFlow);
        request.setAttribute("vt", getBusinessObject().getViewText());
        if(null != getBusinessObject().getSubBusinessObject()){
            // 取得子业务对象
            for(BusinessObject bo : getBusinessObject().getSubBusinessObject()){
                request.setAttribute(bo.getBeanAttribute().getBeanInstanceName() + "Vt", bo.getViewText());
            }
        }
        request.setAttribute("reassign", reassign);
        return new ModelAndView("xdss3/reassign/reassignEdit");
    }

	/**
	 * 提交
	 * xx
	 * @param request
	 * @param response
	 */

	public void _submitProcess(HttpServletRequest request,
			HttpServletResponse response) {
		// 绑定主对象值
		Reassign reassign = (Reassign) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), Reassign.class, true, request.getMethod(), true);

		// 财务部重分配提交
		if (reassign.getProcessstate()==null || reassign.getProcessstate().trim().equals("")) {
			/**
			 * 判断是否凭证是否全部清帐
			 */
			String msg = this.reassignService.isVoucherReseted(reassign.getReassigntype(), reassign.getReassigntmethod(), reassign.getReassignboid());
			
			if (msg != null) {
				throw new BusinessException(msg);
			}else{			
				InfoObject infoObject = new InfoObject();
				Map<String,List<IKeyValue>> map = null;
				
				if(ReassignConstant.BILLCLEARCOLLECT.equals(reassign.getReassigntype()) || ReassignConstant.COLLECT.equals(reassign.getReassigntype()) 
						|| ReassignConstant.CUSTOMERREFUNDMENT.equals(reassign.getReassigntype()) || ReassignConstant.CUSTOMERSINGLECLEAR.equals(reassign.getReassigntype()) 	){
					map = this.reassignService.getPartClearByCustomer(reassign.getReassignboid(), reassignService.convertType(reassign.getReassigntype()),infoObject,"0");
				}else{
//					以后修改成供应商
					map = this.reassignService.getPartClearBySupplier(reassign.getReassignboid(), reassignService.convertType(reassign.getReassigntype()),infoObject,"0");
				}
				
				
				List<IKeyValue> clearList = map.get("clear");
				List<IKeyValue> reassignList = map.get("reassign");	
//				List<IKeyValue> unclearList = new ArrayList<IKeyValue>();
//				//取出部分清中没有不用被重分配的单据
//				for(IKeyValue keyValue:clearList){
//					for(IKeyValue keyValue2:reassignList){
//						if(!keyValue.getKey().equals(keyValue2.getKey()) && !unclearList.contains(keyValue.getKey())){
//							unclearList.add(keyValue);
//						}
//					}
//				}				
				
				boolean flag=false;
				IKeyValue keyValue2=null;
				//取出部分清的数据如果不只当前的单据出清账凭证的（作废清账凭证（只会有一条清账凭证）然后作废生成清账凭证的原始单据（也是最后一条清账的原始单据））
				if(null != clearList && clearList.size()>1){
					for(IKeyValue keyValue:clearList){
						String Reassigntype = this.reassignService.convertType2(keyValue.getValue());
//						当前的重分配的ID不等于部分清列表的ID才去检验是否有生成清账凭证（因为前面以对当前的重分配的ID做了检验）
						if(!reassign.getReassignboid().equals(keyValue.getKey())){			
//							找出最后清的那个条单据（也就是有清账凭证的单据）
							List<CertificateNo> cfn = this.reassignService.getClearVoucher(keyValue.getKey());
							if(cfn != null && cfn.size()>0){
								msg = this.reassignService.isVoucherReseted(Reassigntype, ReassignConstant.FI_CLEAR, keyValue.getKey());
								if (msg != null) {
									throw new BusinessException(msg);
								}else{
									if(!flag){
										Map<String,List<IKeyValue>> map2 =  null;
										if(ReassignConstant.BILLCLEARCOLLECT.equals(reassign.getReassigntype()) || ReassignConstant.COLLECT.equals(reassign.getReassigntype()) 
												|| ReassignConstant.CUSTOMERREFUNDMENT.equals(reassign.getReassigntype()) || ReassignConstant.CUSTOMERSINGLECLEAR.equals(reassign.getReassigntype()) 	){
											map2 = this.reassignService.getPartClearByCustomer(reassign.getReassignboid(), reassignService.convertType(reassign.getReassigntype()),infoObject,"1");
										}else{
//											以后修改成供应商 
											map2 = this.reassignService.getPartClearBySupplier(reassign.getReassignboid(), reassignService.convertType(reassign.getReassigntype()),infoObject,"1");
										}
										List<IKeyValue> allClearList = map2.get("clear");
										//部分清中的单据的更新为isclear=1
										for(IKeyValue keyValue3:allClearList){
											String Reassigntype3 = this.reassignService.convertType2(keyValue3.getValue());
											this.reassignService.resetClearFlag(Reassigntype3,keyValue3.getKey(),Constants.cleared.isCleared);
										}
										flag=true;
									}
//									确定SAP已重置
									//作废清账凭证（只会有一条清账凭证）然后作废生成清账凭证的原始单据
									//      （如果是票清款，单清账，直接作废，如果是收 付款（删除票的数据）如果是退款不用做什么操作）
						        	for(CertificateNo certificateNo :cfn){
						        		voucherService.updateVoucherState(certificateNo.getBelnr(), certificateNo.getBukrs(), certificateNo.getGjahr());						        		
						        	}
						        	if(ReassignConstant.BILLCLEARCOLLECT.equals(Reassigntype) || ReassignConstant.BILLCLEARPAYMENT.equals(Reassigntype)
						        			 || ReassignConstant.CUSTOMERSINGLECLEAR.equals(Reassigntype) || ReassignConstant.SUPPLIERSINGLECLEAR.equals(Reassigntype)
						        			 ){
//						        		废除旧单号
						        		this.reassignService.abolishOldNo( Reassigntype, keyValue.getKey());
						        	}
						        	
						        	/*
									 * 取消已清标识
									 */
									this.reassignService.resetClearFlag(Reassigntype,keyValue.getKey(),Constants.cleared.notCleared);
									
						        	/*
									 * 删除收款清票，把收款，付款修改成预收款，预付款，isclear=0付款清票中票的一方的数据,，收款，付款，
									 * 这步要在(取消已清标识)之后做
									 */
									if(ReassignConstant.COLLECT.equals(Reassigntype) || ReassignConstant.PAYMENT.equals(Reassigntype) ){
										this.reassignService.resetDel( Reassigntype, keyValue.getKey());
									}
									keyValue2 = keyValue;
								}
							}
						}
					}
				}	
				
		
//				去掉本身这条数据							
				if(null !=reassignList && reassignList.size()>1){				
					for(IKeyValue keyValue:reassignList){
						
//						部分清重分配单据等于原始重分配单据的，部分清重分配单据等于出清账凭证的单据的不作处理(下面处理)
						if(reassign.getReassignboid().equals(keyValue.getKey()) || (null !=keyValue2 && keyValue.getKey().equals(keyValue2.getKey()) ) ){	
							
						}else{
//							3.5．	作废掉有关联的原始单据（票清款，单清账），收付款（删除票的数据）退款（不处理）												
							String Reassigntype = this.reassignService.convertType2(keyValue.getValue());
							//6．	作废掉有关联生成的汇损凭证，结算科目 凭证		
							this.reassignService.abolishOldVoucherNo(Reassigntype, ReassignConstant.FI_CLEAR, keyValue.getKey());
							
				        	if(ReassignConstant.BILLCLEARCOLLECT.equals(Reassigntype) || ReassignConstant.BILLCLEARPAYMENT.equals(Reassigntype)
				        			 || ReassignConstant.CUSTOMERSINGLECLEAR.equals(Reassigntype) || ReassignConstant.SUPPLIERSINGLECLEAR.equals(Reassigntype)
				        			 ){
//				        		废除旧单号
				        		this.reassignService.abolishOldNo( Reassigntype, keyValue.getKey());
				        	}
				        	/*
							 * 取消已清标识
							 */
							this.reassignService.resetClearFlag(Reassigntype,keyValue.getKey(),Constants.cleared.notCleared);
							
				        	/*
							 * 删除收款清票，把收款，付款修改成预收款，预付款，isclear=0付款清票中票的一方的数据,，收款，付款，
							 * 这步要在(取消已清标识)之后做
							 */
							if(ReassignConstant.COLLECT.equals(Reassigntype) || ReassignConstant.PAYMENT.equals(Reassigntype) ){
								this.reassignService.resetDel( Reassigntype, keyValue.getKey());
							}
						}
					}					
				}	
				
					
//					对当前的单据的处理
				/*
				 * 废除旧单号,除了，收款，付款，并且 方式 为重置（财务部直接解除分配关系）不用作 废原单
				 */
				if((ReassignConstant.COLLECT.equals(reassign.getReassigntype()) || ReassignConstant.PAYMENT.equals(reassign.getReassigntype())) && ReassignConstant.RESET_TO_FI.equals(reassign.getReassigntmethod())){
					
				}else{
					this.reassignService.abolishOldNo(reassign.getReassigntype(), reassign.getReassignboid());
				}
				/*
				 * 取消已清标识
				 */
				this.reassignService.resetClearFlag(reassign.getReassigntype(), reassign.getReassignboid(),Constants.cleared.notCleared);
				/*
				 * 废除已生成的旧的凭证号
				 */
				this.reassignService.abolishOldVoucherNo(reassign.getReassigntype(), reassign.getReassigntmethod(), reassign.getReassignboid());
				
				/*
				 * 删除收款清票，把收款，付款修改成预收款，预付款，isclear=0付款清票中票的一方的数据,，收款，付款，并且 方式 为重置（财务部直接解除分配关系）,
				 * 这步要在(取消已清标识)之后做
				 */
				if((ReassignConstant.COLLECT.equals(reassign.getReassigntype()) || ReassignConstant.PAYMENT.equals(reassign.getReassigntype())) && ReassignConstant.RESET_TO_FI.equals(reassign.getReassigntmethod())){
					this.reassignService.resetDel(reassign.getReassigntype(), reassign.getReassignboid());
				}				
				
			}
		}
		// 类型标识是从那个页面提交，view 表示从view页面提交流程。
		String type = request.getParameter("type");
		if (!StringUtils.isNullBlank(type) && type.equalsIgnoreCase("view")) {
			this.reassignService._saveOrUpdate(reassign, getBusinessObject());
		}
		this.reassignService._submitProcess(reassign, getBusinessObject());
		this.operateSuccessfully(response);
	}
	
	/**
	 * 检查是否有多条要重分配的数据。
	 * xx
	 * @param request
	 * @param response
	 */
	public void checkReassign(HttpServletRequest request,
			HttpServletResponse response) {
		
		// 绑定主对象值
		Reassign reassign = (Reassign) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), Reassign.class, true, request.getMethod(), true);
		Map<String,List<IKeyValue>> map = null;
		InfoObject infoObject = new InfoObject();
		if(ReassignConstant.BILLCLEARCOLLECT.equals(reassign.getReassigntype()) || ReassignConstant.COLLECT.equals(reassign.getReassigntype()) 
				|| ReassignConstant.CUSTOMERREFUNDMENT.equals(reassign.getReassigntype()) || ReassignConstant.CUSTOMERSINGLECLEAR.equals(reassign.getReassigntype()) 	){
			map = this.reassignService.getPartClearByCustomer(reassign.getReassignboid(), reassignService.convertType(reassign.getReassigntype()),infoObject,"0");
		}else{
//			以后修改成供应商 
			map = this.reassignService.getPartClearBySupplier(reassign.getReassignboid(), reassignService.convertType(reassign.getReassigntype()),infoObject,"0");
		}
		String flag="0";
		if(!infoObject.isRight()){
//			原始单据有错误
			flag="-1";
		}
		List<IKeyValue> clearList = map.get("clear");
		//取出部分清的数据如果不只当前的单据出清账凭证的（作废清账凭证（只会有一条清账凭证）然后作废生成清账凭证的原始单据（也是最后一条清账的原始单据））
		if(null != clearList && clearList.size()>1){
			for(IKeyValue keyValue:clearList){
//				String Reassigntype = this.reassignService.convertType2(keyValue.getValue());
				
//					找出最后清的那个条单据（也就是有清账凭证的单据）
					List<CertificateNo> cfn = this.reassignService.getClearVoucher(keyValue.getKey());
					if(cfn != null && cfn.size()>0){
//						有清账凭证（最后一笔单据）不是当前的这笔单据的提示有多张关联的单据	
						if(!reassign.getReassignboid().equals(keyValue.getKey())){	
							flag="1";
							break;
						}
					}
				}
		}
		List<IKeyValue> reassignList = map.get("reassign");		
//		去掉本身这条数据
		if(null !=reassignList && reassignList.size()>1){	
//			TODO 取是单号不是ID号
//			提示有多张关联的清账单号，确定后会把关联的清账的单号全部重分配掉，是否继续！
			flag="1";
		}
//		MultyGridData gridJsonData = new MultyGridData();
//		gridJsonData.setData(reassignList.toArray());
//		JSONObject jsonList = JSONObject.fromObject(gridJsonData);
		try
		{
			response.setContentType("text/html;charset=UTF-8");
//			response.getWriter().print(jsonList);			
//			System.out.println(jsonList.toString());
			
			response.getWriter().print(flag);
			System.out.println(flag);
		}
		catch (IOException e)
		{
			logger.error("输出json失败," + e.getMessage(), e.getCause());
		}
	}
	/**
	 * 查看
	 * 
	 * @param request
	 * @param response
	 */
	public ModelAndView _view(HttpServletRequest request,
 HttpServletResponse response){
        Reassign reassign = new Reassign();
        String workflowTaskId = request.getParameter("workflowTaskId");
        String workflowNodeDefId = request.getParameter("workflowNodeDefId");
        String id = request.getParameter("reassignid");
        String businessId = request.getParameter("businessId");
        if(null == id){
            id = businessId;
        }
        if(StringUtils.isNullBlank(id)){
            reassign = this.reassignService._get(id);
        }else{
            reassign = this.reassignService._get(id);
        }

        // 不是从流程过来的情况,直接返回重分配查看界面
        if(businessId == null){
            boolean haveBindWorkFlow = WorkflowService.getHaveBindWorkFlow("000000000328");
            request.setAttribute("haveBindWorkFlow", haveBindWorkFlow);
            request.setAttribute("businessId", id);
            request.setAttribute("workflowTaskId", workflowTaskId);
            request.setAttribute("workflowNodeDefId", workflowNodeDefId);
            request.setAttribute("vt", getBusinessObject().getViewText());
            if(null != getBusinessObject().getSubBusinessObject()){
                // 取得子业务对象
                for(BusinessObject bo : getBusinessObject().getSubBusinessObject()){
                    request.setAttribute(bo.getBeanAttribute().getBeanInstanceName() + "Vt", bo.getViewText());
                }
            }

            request.setAttribute("reassign", reassign);
            return new ModelAndView("xdss3/reassign/reassignView");
        }

        /**
         * 判断重分配类型
         */
        /**
         * 重分配收款单
         */
        if(reassign.getReassigntype().equals(ReassignConstant.COLLECT)){
            CollectController controller = (CollectController) RequestContextUtils.getWebApplicationContext(request).getBean("collectController");
            return controller._viewForReassign(request, response);
        }
        /**
         * 重分配付款单
         */
        else if(reassign.getReassigntype().equals(ReassignConstant.PAYMENT)){
            String tradeType = this.reassignService.getPaymentTradeType(reassign.getReassignboid());

            // 内贸付款
            if("01".equals(tradeType)){
                HomePaymentController controller = (HomePaymentController) RequestContextUtils.getWebApplicationContext(request).getBean(
                        "homePaymentController");
                return controller._viewForReassign(request, response);
            }
            // 进口付款
            else{
                ImportPaymentController controller = (ImportPaymentController) RequestContextUtils.getWebApplicationContext(request).getBean(
                        "importPaymentController");
                return controller._viewForReassign(request, response);
            }
        }
        /**
         * 重分配票清收款
         */
        else if(reassign.getReassigntype().equals(ReassignConstant.BILLCLEARCOLLECT)){
            BillClearCollectController controller = (BillClearCollectController) RequestContextUtils.getWebApplicationContext(request).getBean(
                    "billClearCollectController");
            return controller._viewForReassign(request, response);
        }
        /**
         * 重分配票清付款
         */
        else if(reassign.getReassigntype().equals(ReassignConstant.BILLCLEARPAYMENT)){
            BillClearPaymentController controller = (BillClearPaymentController) RequestContextUtils.getWebApplicationContext(request).getBean(
                    "billClearPaymentController");
            return controller._viewForReassign(request, response);
        }
        /**
         * 重分配退款单
         */
        else if(reassign.getReassigntype().equals(ReassignConstant.CUSTOMERREFUNDMENT)){
            // 客户退款
            CustomerRefundmentController controller = (CustomerRefundmentController) RequestContextUtils.getWebApplicationContext(request).getBean("customerRefundmentController");
            return controller._viewForReassign(request, response);
        }else if(reassign.getReassigntype().equals(ReassignConstant.SUPPLIERREFUNDMENT)){
            // 供应商退款
            SupplierRefundmentController controller = (SupplierRefundmentController) RequestContextUtils.getWebApplicationContext(request).getBean("supplierRefundmentController");
            return controller._viewForReassign(request, response);
        }

        return null;
    }

	/**
	 * 保存
	 * 
	 * @param request
	 * @param response
	 */
	public void _saveOrUpdate(HttpServletRequest request,
			HttpServletResponse response) {
		JSONObject jo = new JSONObject();
		// 绑定主对象值
		Reassign reassign = (Reassign) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), Reassign.class, true, request.getMethod(), true);

		// 后台客户退款、供应商退款统一都为5
//		if (reassign.getReassigntype().equals("6")) {
//			reassign.setReassigntype("5");
//		}
		String dept_id = this.reassignService.getDeptId(reassign.getReassigntype(),reassign.getReassignboid() );
        reassign.setDept_id(dept_id);
		String boNo = this.reassignService.getBusinessNoById(reassign.getReassigntype(), reassign.getReassignboid());
		reassign.setReassignbono(boNo);
		String ocreator = this.reassignService.getOcreator(reassign.getReassigntype(), reassign.getReassignboid());
		reassign.setOcreator(ocreator);
		this.reassignService._saveOrUpdate(reassign, getBusinessObject());

		// 如果返回参数带有calActivityId，将业务对象保存到相应的CalActivity中
		String calActivityId = request.getParameter("calActivityId");
		if (StringUtils.isNotBlank(calActivityId)) {
			CalActivityService calActivityService = (CalActivityService) EasyApplicationContextUtils.getBeanByName("calActivityService");
			CalActivity calActivity = calActivityService._get(calActivityId);
			if (calActivity != null) {
				calActivity.setBoid(this.getBusinessObject().getBoId());
				calActivity.setBoname(this.getBoName());
				calActivity.setBusid(reassign.getReassignid());
				calActivityService._update2(calActivity, BusinessObjectService.getBusinessObject("CalActivity"));
			}
			this.operateClose(response);
		} else {
			jo.put("reassignid", reassign.getReassignid());
			String creator = reassign.getCreator();
			String creator_text = SysCachePoolUtils.getDictDataValue("YUSER",creator);
			jo.put("creator_text", creator_text);
			jo.put("creator", creator);
			jo.put("createtime", reassign.getCreatetime());
			String lastmodifyer = reassign.getLastmodifyer();
			String lastmodifyer_text = SysCachePoolUtils.getDictDataValue(
					"YUSER", lastmodifyer);
			jo.put("lastmodifyer_text", lastmodifyer_text);
			jo.put("lastmodifyer", lastmodifyer);
			jo.put("lastmodifytime", reassign.getLastmodifytime());
			jo.put("ocreator", ocreator);
			jo.put("dept_id", reassign.getDept_id());
			this.operateSuccessfullyWithString(response, jo.toString());
		}
	}
	
	/**
	 * @创建作者：邱杰烜
	 * @创建日期：2010-10-21
	 * 重分配查看审批
	 */
	public ModelAndView _viewForReassign(HttpServletRequest request, HttpServletResponse response){
		String businessId = request.getParameter("businessId");
		Reassign reassign = this.reassignService._get(businessId);
		/* 
		 * 重分配类型定义：
		 * 1：收款重分配
		 * 2：付款重分配
		 * 3：票清收款重分配
         * 4：票清付款重分配
         * 5：退款重分配
		 */
		String reassignType = reassign.getReassigntype();
		if(reassignType.equals(ReassignConstant.COLLECT)){		// 收款重分配
			return this.collectController._viewForReassign(request, response);
		}else if(reassignType.equals(ReassignConstant.PAYMENT)){	// 付款重分配
			int tradeType = this.reassignService.judgePaymentTradeType(reassign.getReassignboid());
			if(tradeType == 1){					// 若为内贸付款
				return this.homePaymentController._viewForReassign(request, response);
			}else{								// 若为进口付款
				return this.importPaymentController._viewForReassign(request, response);
			}
		}else if(reassignType.equals(ReassignConstant.BILLCLEARPAYMENT)){	// 票清付款重分配
			return this.billClearPaymentController._viewForReassign(request, response);
		}else if(reassignType.equals(ReassignConstant.BILLCLEARCOLLECT)){	// 票清收款重分配
		    return this.billClearCollectController._viewForReassign(request, response);
		}else if(reassignType.equals(ReassignConstant.CUSTOMERREFUNDMENT)){	// 客户退款重分配
		    return this.customerRefundmentController._viewForReassign(request, response);
		}else if(reassignType.equals(ReassignConstant.SUPPLIERREFUNDMENT)){ // 供应商退款重分配 
		    return this.supplierRefundmentController._viewForReassign(request, response);
		}else{
		    return null;
		}
	}
	
	/**
	 * @创建作者：邱杰烜
	 * @创建日期：2010-10-21
	 * 重分配编辑审批
	 */
	public ModelAndView _editForReassign(HttpServletRequest request, HttpServletResponse response){
		String businessId = request.getParameter("businessId");
		Reassign reassign = this.reassignService._get(businessId);
		/* 
		 * 重分配类型定义：
		 * 1：收款重分配
		 * 2：付款重分配
		 * 3：票清收款重分配
         * 4：票清付款重分配
         * 5：退款重分配
		 */
		String reassignType = reassign.getReassigntype();
		if(reassignType.equals(ReassignConstant.COLLECT)){		// 【收款重分配】
			// 若该收款重分配为"冲销（财务部冲销并作废）"，则做特殊处理，即返回重分配查看界面
			if(reassign.getReassigntmethod().equals(ReassignConstant.FI_CLEAR)){
				return this.collectController._viewForReassign(request, response);
			}else{
				return this.collectController._editForReassign(request, response);
			}
		}else if(reassignType.equals(ReassignConstant.PAYMENT)){	// 【付款重分配】
			int tradeType = this.reassignService.judgePaymentTradeType(reassign.getReassignboid());
			if(tradeType == 1){					// 〖若为内贸付款〗
				// 若该收款重分配为"冲销（财务部冲销并作废）"，则做特殊处理，即返回重分配查看界面
				if(reassign.getReassigntmethod().equals(ReassignConstant.FI_CLEAR)){
					return this.homePaymentController._viewForReassign(request, response);
				}else{
					return this.homePaymentController._editForReassign(request, response);
				}
			}else{								// 〖若为进口付款〗
				// 若该收款重分配为"冲销（财务部冲销并作废）"，则做特殊处理，即返回重分配查看界面
				if(reassign.getReassigntmethod().equals(ReassignConstant.FI_CLEAR)){
					return this.importPaymentController._viewForReassign(request, response);
				}else{
					return this.importPaymentController._editForReassign(request, response);
				}
			}
		}else if(reassignType.equals(ReassignConstant.BILLCLEARPAYMENT)){	// 【票清付款重分配】
		    return this.billClearPaymentController._editForReassign(request, response);
		}else if(reassignType.equals(ReassignConstant.BILLCLEARCOLLECT)){	// 【票清收款重分配】
		    return this.billClearCollectController._editForReassign(request, response);
		}else if(reassignType.equals(ReassignConstant.CUSTOMERREFUNDMENT)){ // 【客户退款重分配】
		    // 若该收款重分配为"冲销（财务部冲销并作废）"，则做特殊处理，即返回重分配查看界面
            if(reassign.getReassigntmethod().equals(ReassignConstant.FI_CLEAR)){
                return this.customerRefundmentController._viewForReassign(request, response);
            }else{
                return this.customerRefundmentController._editForReassign(request, response);
            }
        }else if(reassignType.equals(ReassignConstant.SUPPLIERREFUNDMENT)){ // 【供应商退款重分配】 
            // 若该收款重分配为"冲销（财务部冲销并作废）"，则做特殊处理，即返回重分配查看界面
            if(reassign.getReassigntmethod().equals(ReassignConstant.FI_CLEAR)){
                return this.supplierRefundmentController._viewForReassign(request, response);
            }else{
                return this.supplierRefundmentController._editForReassign(request, response);
            }
        }else{
            return null;
        }
	}
	
	/**
	 * 管理  
	 * @param request
	 * @param response
	 */
	public ModelAndView _manage(HttpServletRequest request,HttpServletResponse response) {
		String strDataAuthSql = CommonDataAuth.getDataAuthSql(this.getBusinessObject());
		request.setAttribute("dataAuthSql", strDataAuthSql);
		request.setAttribute("vt", getBusinessObject().getViewText());
		return new ModelAndView("xdss3/reassign/reassignManage");
	}
}





