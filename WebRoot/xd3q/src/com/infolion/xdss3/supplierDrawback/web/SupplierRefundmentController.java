/*
 * @(#)SupplierRefundmentController.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年06月18日 10点06分58秒
 *  描　述：创建
 */
package com.infolion.xdss3.supplierDrawback.web;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

import net.sf.json.JSONObject;

import com.infolion.xdss3.BusinessState;
import com.infolion.xdss3.collect.domain.Collect;
import com.infolion.xdss3.financeSquare.domain.FundFlow;
import com.infolion.xdss3.financeSquare.domain.SettleSubject;
import com.infolion.xdss3.masterData.service.SyncMasterDataService;
import com.infolion.xdss3.reassign.ReassignConstant;
import com.infolion.xdss3.reassign.dao.ReassignJdbcDao;
import com.infolion.xdss3.reassign.domain.Reassign;
import com.infolion.xdss3.reassign.service.ReassignService;
import com.infolion.xdss3.singleClear.domain.ClearConstant;
import com.infolion.xdss3.singleClear.domain.IInfo;
import com.infolion.xdss3.singleClear.domain.IInfoVoucher;
import com.infolion.xdss3.singleClear.domain.ParameterVoucherObject;
import com.infolion.xdss3.singleClear.service.SupplierClearAccountService;
import com.infolion.xdss3.supplierDrawback.domain.SupplierDbBankItem;
import com.infolion.xdss3.supplierDrawback.domain.SupplierRefundItem;
import com.infolion.xdss3.supplierDrawback.domain.SupplierRefundment;
import com.infolion.xdss3.supplierDrawbackGen.web.SupplierRefundmentControllerGen;
import com.infolion.xdss3.voucher.domain.Voucher;
import com.infolion.xdss3.voucher.service.VoucherService;
import com.infolion.platform.bo.domain.BusinessObject;
import com.infolion.platform.bo.service.BusinessObjectService;
import com.infolion.platform.console.sys.context.UserContext;
import com.infolion.platform.console.sys.context.UserContextHolder;
import com.infolion.platform.dpframework.core.BusinessException;
import com.infolion.platform.dpframework.core.annotation.BDPController;
import com.infolion.platform.dpframework.core.cache.SysCachePoolUtils;
import com.infolion.platform.dpframework.uicomponent.grid.GridService;
import com.infolion.platform.dpframework.uicomponent.grid.data.AuthSql;
import com.infolion.platform.dpframework.uicomponent.grid.data.GridData;
import com.infolion.platform.dpframework.uicomponent.grid.data.GridQueryCondition;
import com.infolion.platform.dpframework.uicomponent.number.service.NumberService;
import com.infolion.platform.dpframework.util.ExBeanUtils;
import com.infolion.platform.dpframework.util.StringUtils;
import com.infolion.platform.workflow.engine.WorkflowService;

/**
 * <pre>
 * 供应商退款(SupplierRefundment)控制器类
 * </pre>
 * 借
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
public class SupplierRefundmentController extends
		SupplierRefundmentControllerGen {

    
    @Autowired
    private GridService gridService;
    
    public void setGridService(GridService gridService) {
        this.gridService = gridService;
    }
    
    @Autowired
    private SyncMasterDataService syncMasterDataService;

    public void setSyncMasterDataService(SyncMasterDataService syncMasterDataService)
    {
        this.syncMasterDataService = syncMasterDataService;
    }
    
    @Autowired
    private VoucherService voucherService;
    
    public void setVoucherService(VoucherService voucherService) {
        this.voucherService = voucherService;
    }
    
	@Autowired
	protected ReassignJdbcDao reassignJdbcDao;

	/**
	 * @param reassignJdbcDao
	 *            the reassignJdbcDao to set
	 */
	public void setReassignJdbcDao(ReassignJdbcDao reassignJdbcDao) {
		this.reassignJdbcDao = reassignJdbcDao;
	}
	
	@Autowired
	protected ReassignService reassignService;

	/**
	 * @param reassignService
	 *            the reassignService to set
	 */
	public void setReassignService(ReassignService reassignService) {
		this.reassignService = reassignService;
	}	
	
	@Resource
	private SupplierClearAccountService supplierClearAccountService;
	
	
	/**
	 * @param supplierClearAccountService the supplierClearAccountService to set
	 */
	public void setSupplierClearAccountService(
			SupplierClearAccountService supplierClearAccountService) {
		this.supplierClearAccountService = supplierClearAccountService;
	}
	
	public Properties getProperties(String absolutePath) {
		Properties p = new Properties();
		try {
			InputStream is = this.getClass().getClassLoader().getResourceAsStream(absolutePath);
			p.load(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return p;
	}

	public void _getRefundmentItemGridData(HttpServletRequest request,
			HttpServletResponse response) {
		String itemids = request.getParameter("itemids");

		List supplierRefundItemList = this.supplierRefundmentService.getSupplierRefundmentItemList(itemids);
		GridData gridJsonData = new GridData();
		gridJsonData.setData(supplierRefundItemList.toArray());
		JSONObject jsonList = JSONObject.fromObject(gridJsonData);
		try {
			response.setContentType("text/html;charset=UTF-8");
			response.getWriter().print(jsonList);
			System.out.println(jsonList.toString());
		} catch (IOException e) {
			logger.error("输出json失败," + e.getMessage(), e.getCause());
		}
	}

	/**
	 * 获取退款银行grid内容
	 * 
	 * @param request
	 * @param response
	 */
	public void _getBankItemGridData(HttpServletRequest request,
			HttpServletResponse response) {
		String itemids = request.getParameter("itemids");

		List customerDbBankItemList = this.supplierRefundmentService
				.getSupplierDbBankItemList(itemids);
		GridData gridJsonData = new GridData();
		gridJsonData.setData(customerDbBankItemList.toArray());
		JSONObject jsonList = JSONObject.fromObject(gridJsonData);
		try {
			response.setContentType("text/html;charset=UTF-8");
			response.getWriter().print(jsonList);
			System.out.println(jsonList.toString());
		} catch (IOException e) {
			logger.error("输出json失败," + e.getMessage(), e.getCause());
		}
	}

	/**
	 * 凭证预览
	 * 
	 * @param request
	 * @param response
	 */
	public void _preview(HttpServletRequest request,
			HttpServletResponse response) {
		JSONObject jo = new JSONObject();
		// 绑定主对象值
		SupplierRefundment supplierRefundment = (SupplierRefundment) ExBeanUtils
				.bindBusinessObjectData(request.getParameterMap(),
						SupplierRefundment.class, true, request.getMethod(),
						true);
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<SupplierDbBankItem> supplierDbBankItemmodifyItems = ExBeanUtils
				.bindModifySubBoData(request.getParameterMap(),
						new Object[] { supplierRefundment },
						SupplierDbBankItem.class, null);
		supplierRefundment.setSupplierDbBankItem(supplierDbBankItemmodifyItems);

		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<SupplierRefundItem> supplierRefundItemmodifyItems = ExBeanUtils
				.bindModifySubBoData(request.getParameterMap(),
						new Object[] { supplierRefundment },
						SupplierRefundItem.class, null);
		supplierRefundment.setSupplierRefundItem(supplierRefundItemmodifyItems);
		
        Set<SupplierDbBankItem> deletedSupplierDbBankItemSet = ExBeanUtils.bindDeleteSubBoData(
                request.getParameterMap(), new Object[] { supplierRefundment },
                SupplierDbBankItem.class, null);

        Set<SupplierRefundItem> deletedSupplierRefundItemSet = ExBeanUtils.bindDeleteSubBoData(
                request.getParameterMap(), new Object[] { supplierRefundment },
                SupplierRefundItem.class, null);
		
		
		// 绑定子对象(一对一关系)
        SettleSubject settleSubject = (SettleSubject) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), SettleSubject.class, false, request.getMethod(), true);
        if (settleSubject != null)
        {
            supplierRefundment.setSettleSubject(settleSubject);
            settleSubject.setSupplierRefundment(supplierRefundment);
        }
        FundFlow fundFlow = (FundFlow) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), FundFlow.class, false, request.getMethod(), true);
        if (fundFlow != null)
        {
            supplierRefundment.setFundFlow(fundFlow);
            fundFlow.setSupplierRefundment(supplierRefundment);
        }
        
        //更新 供应商 状态
//        this.supplierRefundmentService.updateVendorTitle(supplierRefundment.getSupplier());
        
		this.supplierRefundmentService._saveOrUpdate(supplierRefundment,deletedSupplierDbBankItemSet, deletedSupplierRefundItemSet,getBusinessObject());
		
		supplierRefundment =  this.supplierRefundmentService._get(supplierRefundment.getRefundmentid());
		
		// 保存前先删除
		this.voucherService.deleteVoucherByBusinessid(supplierRefundment.getRefundmentid());
		
		List<String> retList = this.supplierRefundmentService.saveVoucher(supplierRefundment);
		// 保存<退款凭证>后判断是否需要删除
		this.voucherService.judgeVoucherNeedDel(supplierRefundment.getRefundmentid());
		
		
//		List<String> retList2 = this.supplierRefundmentService.saveClearVoucher(_supplierRefundment);

		
        ParameterVoucherObject parameterVoucher =supplierClearAccountService.setParameter(supplierRefundment);
		BigDecimal marginAmount = new BigDecimal("0");
		Voucher settleSubjectVoucher = null;
		
//		if (settleSubject != null || fundFlow != null){			
//			settleSubjectVoucher = supplierClearAccountService.saveSettleSubjectVoucher(parameterVoucher);				
//			marginAmount = supplierClearAccountService.getMargin(settleSubjectVoucher.getVoucherItem());
////			客户跟供应商借贷方相反
//			marginAmount = new BigDecimal("0").subtract(marginAmount);
//			parameterVoucher.setVoucherid(settleSubjectVoucher.getVoucherid());
//		}
//		//判断是否纯代理，纯代理不清账
		if (StringUtils.isNullBlank(supplierRefundment.getCustomer())){
		//判断是否需要删除
			this.voucherService.judgeVoucherNeedDel(supplierRefundment.getRefundmentid());
			boolean isSave = false;
			if(!StringUtils.isNullBlank(supplierRefundment.getRefundmentid()))isSave=true;
			IInfo info =supplierClearAccountService.checkClearData(supplierRefundment, marginAmount,isSave);
	//		判断本次数据是否正确
			if(info.isRight()){
				IInfoVoucher infoVoucher = supplierClearAccountService.isClearAccount(supplierRefundment, marginAmount);
				if(null != infoVoucher.getSubtractVlaue()){
					parameterVoucher.setSubtractVlaue(infoVoucher.getSubtractVlaue().setScale(2, BigDecimal.ROUND_HALF_UP));
				}
				if(null != infoVoucher.getSumAdjustamount()){
					parameterVoucher.setSumAdjustamount(infoVoucher.getSumAdjustamount().setScale(2, BigDecimal.ROUND_HALF_UP));
				}
				parameterVoucher.setTaxCode(infoVoucher.getTaxCode());
	//			判断以前的业务数据是否正确
				if(infoVoucher.isRight()){
	//				是否出汇损
					boolean isp = supplierClearAccountService.isProfitAndLoss(parameterVoucher.getCurrencytext(), parameterVoucher.getBukrs());
	//				可以全清出清账凭证
					if(infoVoucher.isClear()){
						Voucher clearVoucher =supplierClearAccountService.saveClearVoucherBySupplier(parameterVoucher,infoVoucher,supplierRefundment.getRefundmentid(),ClearConstant.REFUNDMENT_TYPE_L,isp);
	//					数据有错误
						if(null == clearVoucher){
							jo.put("isRight", false);
							jo.put("info", info.CODE_9);
							this.operateSuccessfullyHiddenInfoWithString(response, jo.toString());
						}
					}else{
	//				部分清（有外币出汇损）,并且差额不为0,并且不是纯代理的，纯代理不出汇损
						if(isp && parameterVoucher.getSubtractVlaue().compareTo(new BigDecimal("0"))!= 0 ){										
							Voucher plVoucher = supplierClearAccountService.saveProfitAndLossVoucher(parameterVoucher);
						}
						
					}
	//				保存本次全清的数据，用来更新isclear状态
					request.getSession().setAttribute(supplierRefundment.getRefundmentid(), infoVoucher);
					//判断是否需要删除
					this.voucherService.judgeVoucherNeedDel(supplierRefundment.getRefundmentid());						
					try {
						response.setContentType("text/html;charset=UTF-8");
						response.getWriter().print(supplierRefundment.getRefundmentid());
						System.out.println(supplierRefundment.getRefundmentid());
					} catch (IOException e) {
						logger.error("输出json失败," + e.getMessage(), e.getCause());
					}
				}else{
	//				数据错误不能保存
					throw new BusinessException(infoVoucher.getInfo());
				}
			}else{
	//			数据错误不能保存
				throw new BusinessException(info.getInfo());
			}
		}
		// 保存<清帐凭证>后判断是否需要删除
      this.voucherService.judgeVoucherNeedDel(supplierRefundment.getRefundmentid());
      
		try {
			String vouchids = "";
			for (int i = 0; i < retList.size(); i++) {
				vouchids += retList.get(i);

				if (i + 1 < retList.size()) {
					vouchids += "&";
				}
			}
			response.setContentType("text/html;charset=UTF-8");
			response.getWriter().print(vouchids);
			System.out.println(vouchids);
		} catch (IOException e) {
			logger.error("输出json失败," + e.getMessage(), e.getCause());
		}
		
	}

	/**
	 * 创建
	 * 
	 * @param request
	 * @param response
	 */
	public ModelAndView _create(HttpServletRequest request,
			HttpServletResponse response) {
		String calActivityId = request.getParameter("calActivityId");
		if (StringUtils.isNotBlank(calActivityId))
			request.setAttribute("calActivityId", calActivityId);
		SupplierRefundment supplierRefundment = new SupplierRefundment();
		String workflowTaskId = request.getParameter("workflowTaskId");
		String workflowNodeDefId = request.getParameter("workflowNodeDefId");
		request.setAttribute("vt", getBusinessObject().getViewText());
		if (null != getBusinessObject().getSubBusinessObject()) {
			// 取得子业务对象
			for (BusinessObject bo : getBusinessObject().getSubBusinessObject()) {
				request.setAttribute(bo.getBeanAttribute()
						.getBeanInstanceName()
						+ "Vt", bo.getViewText());
			}
		}

		// 获取用户上下文ID
		String userId = UserContextHolder.getLocalUserContext()
				.getUserContext().getSysUser().getUserId();
		request.setAttribute("userId", userId);

		String userDeptid = UserContextHolder.getLocalUserContext()
				.getUserContext().getSysDept().getDeptid();
		String userDeptName = UserContextHolder.getLocalUserContext()
				.getUserContext().getSysDept().getDeptname();
		String companyID = this.supplierRefundmentService
				.getCompanyIDByDeptID(userDeptid);
		request.setAttribute("userDeptid", userDeptid);
		request.setAttribute("companyid", companyID);
		request.setAttribute("userDeptName", userDeptName);
		request.setAttribute("cashFlowItem", "151");

		request.setAttribute("supplierRefundment", supplierRefundment);
		boolean haveBindWorkFlow = WorkflowService
				.getHaveBindWorkFlow("000000000295");
		request.setAttribute("haveBindWorkFlow", haveBindWorkFlow);
		request.setAttribute("workflowTaskId", workflowTaskId);
		request.setAttribute("workflowNodeDefId", workflowNodeDefId);
		return new ModelAndView("xdss3/supplierDrawback/supplierRefundmentAdd");
	}
	
	/**
	 * @创建作者：邱杰烜
	 * @创建时间：2010-11-04
	 * 重写"提交"方法
	 */
	public void _submitProcess(HttpServletRequest request,
			HttpServletResponse response) {
		// 绑定主对象值
		SupplierRefundment supplierRefundment = (SupplierRefundment) ExBeanUtils
				.bindBusinessObjectData(request.getParameterMap(),
						SupplierRefundment.class, true, request.getMethod(),
						true);
		// 类型标识是从那个页面提交，view 表示从view页面提交流程。
		String type = request.getParameter("type");
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<SupplierDbBankItem> supplierDbBankItemmodifyItems = ExBeanUtils
				.bindModifySubBoData(request.getParameterMap(),
						new Object[] { supplierRefundment },
						SupplierDbBankItem.class, null);
		Set<SupplierDbBankItem> deletedSupplierDbBankItemSet = ExBeanUtils
				.bindDeleteSubBoData(request.getParameterMap(),
						new Object[] { supplierRefundment },
						SupplierDbBankItem.class, null);
		supplierRefundment.setSupplierDbBankItem(supplierDbBankItemmodifyItems);
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<SupplierRefundItem> supplierRefundItemmodifyItems = ExBeanUtils
				.bindModifySubBoData(request.getParameterMap(),
						new Object[] { supplierRefundment },
						SupplierRefundItem.class, null);
		Set<SupplierRefundItem> deletedSupplierRefundItemSet = ExBeanUtils
				.bindDeleteSubBoData(request.getParameterMap(),
						new Object[] { supplierRefundment },
						SupplierRefundItem.class, null);
		supplierRefundment.setSupplierRefundItem(supplierRefundItemmodifyItems);

		// 绑定子对象(一对一关系)
		SettleSubject settleSubject = (SettleSubject) ExBeanUtils
				.bindBusinessObjectData(request.getParameterMap(),
						SettleSubject.class, false, request.getMethod(), true);
		if (settleSubject != null) {
			supplierRefundment.setSettleSubject(settleSubject);
			settleSubject.setSupplierRefundment(supplierRefundment);
		}
		FundFlow fundFlow = (FundFlow) ExBeanUtils.bindBusinessObjectData(
				request.getParameterMap(), FundFlow.class, false, request
						.getMethod(), true);
		if (fundFlow != null) {
			supplierRefundment.setFundFlow(fundFlow);
			fundFlow.setSupplierRefundment(supplierRefundment);
		}
		if (!"view".equalsIgnoreCase(type)) {
			 if (("财务会计审核并做账").equals(supplierRefundment.getProcessstate()))
	            {
	                /*
	                 * 因为凭证处理类会处理退款行项isClear字段，此时界面没有reload该字段，所以凭证窗口关闭时，不再保存界面的行项。
	                 * 后果("财务会计审核并做账"节点只能通过 凭证预览_preview 来保存界面数据。)
	                */
	            	if("通过".equals(supplierRefundment.getWorkflowLeaveTransitionName())){
	            		// 客户未清抬头数据中的数据，判断是否已结清
	            		String bussinessid = supplierRefundment.getRefundmentid();
	            		IInfoVoucher infoVoucher = (IInfoVoucher)request.getSession().getAttribute(bussinessid);
//	            		是否全清
	            		if(infoVoucher.isClear()){
	            			supplierClearAccountService.updateIsClear(bussinessid);
	            		}else{
	            			supplierClearAccountService.updateIsClear(infoVoucher);
	            		}		
	            		request.getSession().removeAttribute(bussinessid);
	            	}
	            } else {
	            	BigDecimal marginAmount = new BigDecimal("0");
	            	boolean isSave = false;
	        		if(!StringUtils.isNullBlank(supplierRefundment.getRefundmentid()))isSave=true;
	        		IInfo info =supplierClearAccountService.checkClearData(supplierRefundment, marginAmount,isSave);
	        		//判断本次数据是否正确,不正确返回
	        		if(!info.isRight()){
	        			throw new BusinessException(info.getInfo());
	        		}
	        		this.supplierRefundmentService._saveOrUpdate(supplierRefundment,
	    					deletedSupplierDbBankItemSet, deletedSupplierRefundItemSet,
	    					getBusinessObject());
	            }
//			this.supplierRefundmentService._saveOrUpdate(supplierRefundment,
//					deletedSupplierDbBankItemSet, deletedSupplierRefundItemSet,
//					getBusinessObject());
		}
		/*
		 * 设置供应商退款的代办信息
		 */
		String workflowBusinessNote = "";
		String deptName = com.infolion.platform.console.sys.context.UserContextHolder.getLocalUserContext().getUserContext().getSysUser().getDeptName();
		String creator = com.infolion.platform.console.sys.context.UserContextHolder.getLocalUserContext().getUserContext().getSysUser().getRealName();
		String bankAccount = "";
		String bankName = "";
		workflowBusinessNote = supplierRefundment.getRefundmentno() + "|" + deptName + "|" + creator + "|金额" + supplierRefundment.getRefundamount();
		supplierRefundment.setWorkflowBusinessNote(workflowBusinessNote);
		
		this.supplierRefundmentService._submitProcess(supplierRefundment,
				deletedSupplierDbBankItemSet, deletedSupplierRefundItemSet,
				getBusinessObject());
		this.operateSuccessfully(response);
	}

	/**
	 * 重分配复制
	 * 
	 * @param request
	 * @param response
	 */
    public ModelAndView _copyForReassign(HttpServletRequest request, HttpServletResponse response){

        String businessId = request.getParameter("businessId");
        String workflowTaskId = request.getParameter("workflowTaskId");
        String workflowNodeDefId = request.getParameter("workflowNodeDefId");

        Reassign reassign = this.reassignService._get(businessId);
        String id = this.reassignJdbcDao.getBoidByReassignid(businessId);

        /**
         * 判断是否已经拷贝过，
         */
        SupplierRefundment supplierRefundment;
        String boid = this.reassignJdbcDao.isCopyed(id, ReassignConstant.SUPPLIERREFUNDMENT);
        if(boid != null){
            supplierRefundment = this.supplierRefundmentService.getSupplierRefundmentById(boid);
        }else{
            supplierRefundment = this.supplierRefundmentService._getEntityCopy(id);
            request.setAttribute("isCreateCopy", "true");
            supplierRefundment.setReassigneddbid(id); // 设置被重分配退款ID
            supplierRefundment.setBusinessstate(BusinessState.REASSIGNED); // 设置为重分配状态
            supplierRefundment.setText(reassign.getText()); // 设置文本为重分配提交时填写文本
        }
        request.setAttribute("userDeptid", supplierRefundment.getDept_id());
        request.setAttribute("cashFlowItem", supplierRefundment.getCashflowitem());
        request.setAttribute("supplierRefundment", supplierRefundment);
        request.setAttribute("vt", getBusinessObject().getViewText());
        if(null != getBusinessObject().getSubBusinessObject()){
            // 取得子业务对象
            for(BusinessObject bo : getBusinessObject().getSubBusinessObject()){
                request.setAttribute(bo.getBeanAttribute().getBeanInstanceName() + "Vt", bo.getViewText());
            }
        }
        boolean haveBindWorkFlow = WorkflowService.getHaveBindWorkFlow("000000000295");
        request.setAttribute("isReassign", "Y");
        request.setAttribute("haveBindWorkFlow", haveBindWorkFlow);
        request.setAttribute("workflowTaskId", workflowTaskId);
        request.setAttribute("workflowNodeDefId", workflowNodeDefId);
        return new ModelAndView("xdss3/supplierDrawback/supplierRefundmentAdd");
    }

	/**
	 * 重分配查看
	 * 
	 * @param request
	 * @param response
	 */
	public ModelAndView _viewForReassign(HttpServletRequest request,
			HttpServletResponse response) {

		SupplierRefundment supplierRefundment = new SupplierRefundment();
		String workflowTaskId = request.getParameter("workflowTaskId");
		String workflowNodeDefId = request.getParameter("workflowNodeDefId");
		String businessId = request.getParameter("businessId");
		String oldId = this.reassignJdbcDao.getBoidByReassignid(businessId);
		Reassign reassign = this.reassignService._get(businessId);
		String id = "";
		// 若为"冲销（财务部冲销并作废）"，则直接使用原退款单的ID
		if(reassign.getReassigntmethod().equals(ReassignConstant.FI_CLEAR)){
			id = oldId;
		}else{
			id = this.reassignJdbcDao.getNewNoIdByOldNoId(ReassignConstant.SUPPLIERREFUNDMENT, oldId);
		}
		if (null == id)
			id = businessId;

		if (StringUtils.isNullBlank(id)) {
			supplierRefundment = this.supplierRefundmentService._getForEdit(id);
		} else {
			supplierRefundment = this.supplierRefundmentService._getForEdit(id);
		}

		boolean haveBindWorkFlow = WorkflowService
				.getHaveBindWorkFlow("000000000295");
		request.setAttribute("haveBindWorkFlow", haveBindWorkFlow);
		request.setAttribute("businessId", id);
		request.setAttribute("workflowTaskId", workflowTaskId);
		request.setAttribute("workflowNodeDefId", workflowNodeDefId);
		request.setAttribute("vt", getBusinessObject().getViewText());
		if (null != getBusinessObject().getSubBusinessObject()) {
			// 取得子业务对象
			for (BusinessObject bo : getBusinessObject().getSubBusinessObject()) {
				request.setAttribute(bo.getBeanAttribute()
						.getBeanInstanceName()
						+ "Vt", bo.getViewText());
			}
		}
		request.setAttribute("isReassign", "Y");
		request.setAttribute("supplierRefundment", supplierRefundment);
		return new ModelAndView("xdss3/supplierDrawback/supplierRefundmentView");
	}
	
	/**
	 * 重分配编辑
	 * 
	 * @param request
	 * @param response
	 */
	public ModelAndView _editForReassign(HttpServletRequest request,
			HttpServletResponse response) {

		SupplierRefundment supplierRefundment = new SupplierRefundment();
		String workflowTaskId = request.getParameter("workflowTaskId");
		String workflowNodeDefId = request.getParameter("workflowNodeDefId");
		String businessId = request.getParameter("businessId");
		String oldId = this.reassignJdbcDao.getBoidByReassignid(businessId);
		String id = this.reassignJdbcDao.getNewNoIdByOldNoId(ReassignConstant.SUPPLIERREFUNDMENT, oldId);

		if (null == id)
			id = businessId;

		if (StringUtils.isNullBlank(id)) {
			supplierRefundment = this.supplierRefundmentService._getForEdit(id);
		} else {
			supplierRefundment = this.supplierRefundmentService._getForEdit(id);
		}

		boolean haveBindWorkFlow = WorkflowService
				.getHaveBindWorkFlow("000000000295");
		request.setAttribute("haveBindWorkFlow", haveBindWorkFlow);
		request.setAttribute("businessId", id);
		request.setAttribute("workflowTaskId", workflowTaskId);
		request.setAttribute("workflowNodeDefId", workflowNodeDefId);
		request.setAttribute("vt", getBusinessObject().getViewText());
		if (null != getBusinessObject().getSubBusinessObject()) {
			// 取得子业务对象
			for (BusinessObject bo : getBusinessObject().getSubBusinessObject()) {
				request.setAttribute(bo.getBeanAttribute()
						.getBeanInstanceName()
						+ "Vt", bo.getViewText());
			}
		}
		request.setAttribute("isReassign", "Y");
		request.setAttribute("supplierRefundment", supplierRefundment);
		return new ModelAndView("xdss3/supplierDrawback/supplierRefundmentEdit");
	}

	/**
	 * 重分配提交
	 * 
	 * @param request
	 * @param response
	 */
	public void _submitForReassign(HttpServletRequest request,
			HttpServletResponse response) {
		String workflowTaskId = request.getParameter("workflowTaskId");

		// 绑定主对象值
		SupplierRefundment supplierRefundment = (SupplierRefundment) ExBeanUtils
				.bindBusinessObjectData(request.getParameterMap(),
						SupplierRefundment.class, true, request.getMethod(),
						true);
		// 类型标识是从那个页面提交，view 表示从view页面提交流程。
		String type = request.getParameter("type");
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<SupplierDbBankItem> supplierDbBankItemmodifyItems = ExBeanUtils
				.bindModifySubBoData(request.getParameterMap(),
						new Object[] { supplierRefundment },
						SupplierDbBankItem.class, null);
		Set<SupplierDbBankItem> deletedSupplierDbBankItemSet = ExBeanUtils
				.bindDeleteSubBoData(request.getParameterMap(),
						new Object[] { supplierRefundment },
						SupplierDbBankItem.class, null);
		supplierRefundment.setSupplierDbBankItem(supplierDbBankItemmodifyItems);
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<SupplierRefundItem> supplierRefundItemmodifyItems = ExBeanUtils
				.bindModifySubBoData(request.getParameterMap(),
						new Object[] { supplierRefundment },
						SupplierRefundItem.class, null);
		Set<SupplierRefundItem> deletedSupplierRefundItemSet = ExBeanUtils
				.bindDeleteSubBoData(request.getParameterMap(),
						new Object[] { supplierRefundment },
						SupplierRefundItem.class, null);
		supplierRefundment.setSupplierRefundItem(supplierRefundItemmodifyItems);
        // 绑定子对象(一对一关系)
        SettleSubject settleSubject = (SettleSubject) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), SettleSubject.class, false, request.getMethod(), true);
        if (settleSubject != null)
        {
            supplierRefundment.setSettleSubject(settleSubject);
            settleSubject.setSupplierRefundment(supplierRefundment);
        }
        FundFlow fundFlow = (FundFlow) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), FundFlow.class, false, request.getMethod(), true);
        if (fundFlow != null)
        {
            supplierRefundment.setFundFlow(fundFlow);
            fundFlow.setSupplierRefundment(supplierRefundment);
        }
        
		if ( "view".equalsIgnoreCase(type) ) {
		    supplierRefundment.setBusinessstate("2");
			
		}

		String reassignId = this.reassignJdbcDao
				.getReassignidByBoId(supplierRefundment.getReassigneddbid());
		Reassign reassign = this.reassignService.getReassignById(reassignId);
		reassign.setWorkflowTaskId(workflowTaskId);
		reassign.setWorkflowLeaveTransitionName(supplierRefundment
				.getWorkflowLeaveTransitionName());
		reassign.setWorkflowExamine(supplierRefundment.getWorkflowExamine());
		reassign.setWorkflowUserDefineProcessVariable(supplierRefundment.getWorkflowUserDefineProcessVariable());
		reassign.setWorkflowBusinessNote(supplierRefundment.getWorkflowBusinessNote());
		reassign.setWorkflowUserDefineTaskVariable(supplierRefundment.getWorkflowUserDefineTaskVariable());

		/**
		 * 判断重分配方式是否为： 重置（到业务部门重新分配） 需要拷贝凭证
		 */
		if ( reassign.getProcessstate()!= null && !reassign.getProcessstate().equals("财务部审核") &&
				reassign.getReassigntmethod().equals(ReassignConstant.RESET_TO_BS)) {
			this.reassignService.copyVoucher(reassign.getReassignboid(), supplierRefundment.getRefundmentid());
		}
		//审核通过
		if(reassign.getProcessstate() != null
				&& reassign.getProcessstate().equals("财务部审核") && reassign.getWorkflowLeaveTransitionName().equals("确认")){
			supplierRefundment.setBusinessstate("3");			
		}
		if(reassign.getProcessstate() != null
				&& reassign.getProcessstate().equals("财务部审核") && reassign.getWorkflowLeaveTransitionName().equals("作废")){
			supplierRefundment.setBusinessstate("-1");			
		}
		//设置审核状态
		if( supplierRefundment.getWorkflowLeaveTransitionName().equals("作废"))
		{
			this.reassignService.updateReassignState(reassignId, BusinessState.SUBMITNOTPASS);
		}
		if ( "view".equalsIgnoreCase(type) ) {
		   
			this.supplierRefundmentService._saveOrUpdate(supplierRefundment,
					deletedSupplierDbBankItemSet, deletedSupplierRefundItemSet,
					getBusinessObject());
		}

		BusinessObject businessObject = BusinessObjectService
				.getBusinessObject("Reassign");
		this.reassignService._submitProcess(reassign, businessObject);
		this.operateSuccessfully(response);
	}
	
	/**
	 * @创建作者：邱杰烜
	 * @创建时间：2010-10-27
	 * 现金日记账
	 */
	public void _cashJournal(HttpServletRequest request, HttpServletResponse response){
		JSONObject jo = new JSONObject();
		// 绑定主对象值
		SupplierRefundment supplierRefundment = (SupplierRefundment) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(),SupplierRefundment.class, true, request.getMethod(),true);
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<SupplierDbBankItem> supplierDbBankItemmodifyItems = ExBeanUtils.bindModifySubBoData(request.getParameterMap(),new Object[] { supplierRefundment },SupplierDbBankItem.class, null);
		Set<SupplierDbBankItem> deletedSupplierDbBankItemSet = ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(),new Object[] { supplierRefundment },SupplierDbBankItem.class, null);
		supplierRefundment.setSupplierDbBankItem(supplierDbBankItemmodifyItems);
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<SupplierRefundItem> supplierRefundItemmodifyItems = ExBeanUtils.bindModifySubBoData(request.getParameterMap(),new Object[] { supplierRefundment },SupplierRefundItem.class, null);
		Set<SupplierRefundItem> deletedSupplierRefundItemSet = ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(),new Object[] { supplierRefundment },SupplierRefundItem.class, null);
		supplierRefundment.setSupplierRefundItem(supplierRefundItemmodifyItems);
        // 绑定子对象(一对一关系)
        SettleSubject settleSubject = (SettleSubject) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), SettleSubject.class, false, request.getMethod(), true);
        if (settleSubject != null)
        {
            supplierRefundment.setSettleSubject(settleSubject);
            settleSubject.setSupplierRefundment(supplierRefundment);
        }
        FundFlow fundFlow = (FundFlow) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), FundFlow.class, false, request.getMethod(), true);
        if (fundFlow != null)
        {
            supplierRefundment.setFundFlow(fundFlow);
            fundFlow.setSupplierRefundment(supplierRefundment);
        }
		this.supplierRefundmentService._saveOrUpdate(supplierRefundment, deletedSupplierDbBankItemSet, deletedSupplierRefundItemSet, getBusinessObject());
		/*
		 * 将现金日记账所需要的参数拼装成URL
		 */
		UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
		String username = userContext.getSysUser().getUserName();
		String xjrj = this.getProperties("config/config.properties").getProperty("xjrj");
		String journalType = "";		
		String bus_id   = "";		// 存放所有银行行项ID
		String bus_type = "";		// 业务类型 
		String ps = supplierRefundment.getProcessstate();
		if("综合二部审核".equals(ps)){
			journalType = "1";
		}else if(ps.indexOf("出纳审核")>=0 || "上海信达诺出纳审核".equals(ps)){
			journalType = "2";
		}
		SupplierRefundment cr = this.supplierRefundmentService._get(supplierRefundment.getRefundmentid());
		int s = cr.getSupplierDbBankItem().size();
		for(SupplierDbBankItem supplierDbBankItem : cr.getSupplierDbBankItem()){
			bus_id   += supplierDbBankItem.getRefundbankitemid() + ",";
			bus_type += "5,";
		}
		bus_id 	 = bus_id.substring(0, bus_id.length()-1);
		bus_type = bus_type.substring(0, bus_type.length()-1);
		String cashJournalURl = xjrj + "/xjrj/journal.do?method=preAdd" +
				"&journalType=" + journalType +
				"&bus_id=" + bus_id +
				"&bus_type=" + bus_type +
				"&userName=" + username +
				"&isFromXdss=1";
		// 将链接放入JSON中
		jo.put("cashJournalURl", cashJournalURl);
		jo.put("refundmentno", supplierRefundment.getRefundmentno());
		jo.put("refundmentid", supplierRefundment.getRefundmentid());
		String creator = supplierRefundment.getCreator();
		String creator_text = SysCachePoolUtils.getDictDataValue("YUSER",creator);
		jo.put("creator_text", creator_text);
		jo.put("creator", creator);
		jo.put("settlesubjectid", supplierRefundment.getSettleSubject() != null ? supplierRefundment.getSettleSubject().getSettlesubjectid() : "");
		jo.put("fundflowid", supplierRefundment.getFundFlow() != null ? supplierRefundment.getFundFlow().getFundflowid() : "");
		jo.put("createtime", supplierRefundment.getCreatetime());
		jo.put("lastmodifytime", supplierRefundment.getLastmodifytime());
		this.operateSuccessfullyHiddenInfoWithString(response, jo.toString());
	}
	
	   /**
     * 同步指定供应商的未清据
     * 
     * @param request
     * @param response
     */
    public void _synchronizeUnclearVendor(HttpServletRequest request, HttpServletResponse response)
    {
        String strProvider = request.getParameter("privadeid");
//        syncMasterDataService._synchronizeUnclearVendor(strProvider,false);
//      this.supplierRefundmentService.updateVendorTitle(strProvider);
    }
    
    /**
     * 中转综合查询页面
     */
    public ModelAndView _conditionQuery(HttpServletRequest request, HttpServletResponse response)
    {
        return new ModelAndView("xdss3/supplierDrawback/supplierRefundmentQuery");
    }
    
    /**
     * 多条件查询
     * @param request
     * @param response
     * @param gridQueryCondition
     * @throws Exception
     */
    public void queryGrid(HttpServletRequest request,
            HttpServletResponse response, GridQueryCondition gridQueryCondition)
            throws Exception{
        BusinessObject businessObject = BusinessObjectService.getBusinessObject("SupplierRefundment");
        String strAuthSql = "";
        try {
            AuthSql authSql = (AuthSql) Class.forName("com.infolion.xdss3.CommonDataAuthSql").newInstance();
            strAuthSql = authSql.getAuthSql(businessObject);
            // 替换权限默认的前缀表名
            strAuthSql = strAuthSql.replace("YREFUNDMENT", "t");
        } catch (Exception ex) {
            throw new SQLException("类不存在：" + ex.getMessage());
        }
        String signRegExp="[\\~\\!\\@\\#\\$\\%\\^\\&\\*\\(\\)\\_\\+\\`\\-\\=\\～\\！\\＠\\＃\\＄\\" +
        "％\\＾\\＆\\＊\\\\（\\）\\＿\\＋\\＝\\－\\｀\\[\\]\\\\'\\;\\/\\.\\,\\<\\>\\?\\:" +
        "\"\\{\\}\\|\\，\\．\\／\\；\\＇\\［\\］\\＼\\＜\\＞\\？\\：\\＂\\｛\\｝\\｜\\“\\”\\" +
        "‘\\’\\。\r+\n+\t+\\s\\ ]";
        
        StringBuffer sql = new StringBuffer("SELECT DISTINCT a.*,b.contract_no,b.project_no,b.paymentno,dp.NAME1 as supplier_name," +
        		" o.ORGANIZATIONNAME as DEPT_NAME FROM YREFUNDMENT a " +
        		" left join YGETLIFNR dp on ( a.supplier=dp.lifnr ) " +
        		" left join YORGANIZATION o on ( a.dept_id=o.organizationid ) " +
        		" left join YREFUNDMENTITEM b ON a.REFUNDMENTID=b.REFUNDMENTID WHERE 1=1 and replace(supplier,' ','') is not NULL ");
        
        
        
        // 获取SQL参数
        String refundmentno = (String)request.getParameter("refundmentno");     // 退款单号
        String contractNo = (String)request.getParameter("contract_no");        // 合同号
        String projectNo = (String)request.getParameter("project_no");          // 立项号
        String paymentno = (String)request.getParameter("paymentno");           // 收款编号
        String amount1 = (String)request.getParameter("amount1");               // 退款金额1
        String amount2 = (String)request.getParameter("amount2");               // 退款金额2
        String titleText = (String)request.getParameter("title_text");          // 抬头文本
        titleText = URLDecoder.decode(titleText, "UTF-8");
        String supplier = (String)request.getParameter("supplier");             // 客户编号
        String deptId = (String)request.getParameter("dept_id");                // 部门ID
        String applyDate1 = (String)request.getParameter("apply_date1");        // 申请时间1
        String applyDate2 = (String)request.getParameter("apply_date2");        // 申请时间2
        applyDate1 = applyDate1.replaceAll(signRegExp, "");         // 删除"申请时间1"中的所有符号
        applyDate2 = applyDate2.replaceAll(signRegExp, "");         // 删除"申请时间2"中的所有符号
        String approvalDate1 = (String)request.getParameter("approval_date1");  // 审批通过时间1
        String approvalDate2 = (String)request.getParameter("approval_date2");  // 审批通过时间2
        approvalDate1 = approvalDate1.replaceAll(signRegExp, "");   // 删除"审批通过时间1"中的所有符号
        approvalDate2 = approvalDate2.replaceAll(signRegExp, "");   // 删除"审批通过时间2"中的所有符号
        
        String voucherNo = (String)request.getParameter("voucherno");           // 凭证号
        String businessstate = (String)request.getParameter("businessstate");   // 业务状态
        String project_no = (String)request.getParameter("project_no");   // 业务状态
        
        // 设置客户编号条件
        if(StringUtils.isNotBlank(supplier)){
            sql.append("AND a.supplier = '" + supplier + "'");
        }
        // 设置凭证号条件
        if(StringUtils.isNotBlank(voucherNo)){
            sql.append("AND EXISTS(SELECT 1 FROM YVOUCHER d WHERE d.BUSINESSID=a.REFUNDMENTID AND d.VOUCHERNO LIKE '%" + voucherNo + "%')");
        }
        // 设置部门ID条件
        if(StringUtils.isNotBlank(deptId)){
            sql.append("AND a.DEPT_ID = '" + deptId + "'");
        }
        
        // 设置退款编号条件
        if(StringUtils.isNotBlank(refundmentno)){
            sql.append("AND a.REFUNDMENTNO LIKE '%" + refundmentno + "%'");
        }
        // 设置合同编号条件
        if(StringUtils.isNotBlank(contractNo)){
            sql.append("AND b.CONTRACT_NO LIKE '%" + contractNo + "%'");
        }
        // 设置项目编号条件
        if(StringUtils.isNotBlank(project_no)){
            sql.append("AND b.PROJECT_NO LIKE '%" + project_no + "%'");
        }
        // 设置收款编号条件
        if(StringUtils.isNotBlank(paymentno)){
            sql.append("AND b.paymentno LIKE '%" + paymentno + "%'");
        }
        // 设置抬头文本条件
        if(StringUtils.isNotBlank(titleText)){
            sql.append("AND a.TEXT LIKE '%" + titleText + "%'");
        }
        
        // 设置业务状态条件
        if(StringUtils.isNotBlank(businessstate)){
            if(businessstate.equals("0")){
                sql.append("AND a.BUSINESSSTATE IN ('0','',' ')");
            }else{
                sql.append("AND a.BUSINESSSTATE = '" + businessstate + "'");
            }
        }
        
        // 设置金额条件
        if(StringUtils.isNotBlank(amount1) && StringUtils.isNotBlank(amount2)){
            sql.append("AND a.REFUNDAMOUNT BETWEEN '" + amount1 + "' AND '" + amount2 + "'");
        }else if(StringUtils.isNotBlank(amount1)){
            sql.append("AND a.REFUNDAMOUNT > '" + amount1 + "'");
        }else if(StringUtils.isNotBlank(amount2)){
            sql.append("AND a.REFUNDAMOUNT < '" + amount2 + "'");
        }
        
        // 设置申请时间条件
        if(StringUtils.isNotBlank(applyDate1) && StringUtils.isNotBlank(applyDate2)){
            sql.append("AND a.CREATETIME BETWEEN '" + applyDate1 + "' AND '" + applyDate2 + "'");
        }else if(StringUtils.isNotBlank(applyDate1)){
            sql.append("AND a.CREATETIME > '" + applyDate1 + "'");
        }else if(StringUtils.isNotBlank(applyDate2)){
            sql.append("AND a.CREATETIME < '" + applyDate2 + "'");
        }
        // 设置审批通过时间条件
        if(StringUtils.isNotBlank(approvalDate1) && StringUtils.isNotBlank(approvalDate2)){
            sql.append("AND a.BUSINESSSTATE='3' AND a.LASTMODIFYTIME BETWEEN '" + approvalDate1 + "' AND '" + approvalDate2 + "'");
        }else if(StringUtils.isNotBlank(approvalDate1)){
            sql.append("AND a.BUSINESSSTATE='3' AND a.LASTMODIFYTIME > '" + approvalDate1 + "'");
        }else if(StringUtils.isNotBlank(approvalDate2)){
            sql.append("AND a.BUSINESSSTATE='3' AND a.LASTMODIFYTIME < '" + approvalDate2 + "'");
        }
        
        
        gridQueryCondition.setBoName("");
        gridQueryCondition.setTableSql("");
        gridQueryCondition.setDefaultCondition("1=1 " + strAuthSql);
        gridQueryCondition.setWhereSql("");
        gridQueryCondition.setOrderSql("CREATETIME DESC");
        gridQueryCondition.setGroupBySql("");
        gridQueryCondition.setTableName("(" + sql.toString() + ") t");
        gridQueryCondition.setHandlerClass("com.infolion.xdss3.supplierDrawback.web.SupplierGrid");
        String editable = "false";
        String needAuthentication = "true";
        GridData gridJsonData = this.gridService.getGridData(gridQueryCondition, editable, needAuthentication);
        JSONObject jsonList = JSONObject.fromObject(gridJsonData);
        try {
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(jsonList);
        } catch (IOException e) {
            logger.error("输出json失败," + e.getMessage(), e.getCause());
        }
    }

    /**
     * @创建作者：hongjj
     * @创建日期：2011-09-21 增加预确认功能
     */
    public void _prepConfirmSupplierRefundment(HttpServletRequest request,
            HttpServletResponse response) {
        String id = request.getParameter("businessid");
        this.supplierRefundmentService.prepConfirm(id, "2");
        this.operateSuccessfullyWithString(response, "");
    }
}