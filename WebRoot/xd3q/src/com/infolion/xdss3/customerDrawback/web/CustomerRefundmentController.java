/*
 * @(#)CustomerRefundmentController.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年06月17日 14点59分07秒
 *  描　述：创建
 */
package com.infolion.xdss3.customerDrawback.web;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

import com.infolion.platform.bo.domain.BusinessObject;
import com.infolion.platform.bo.service.BusinessObjectService;
import com.infolion.platform.console.sys.context.UserContext;
import com.infolion.platform.console.sys.context.UserContextHolder;
import com.infolion.platform.dpframework.core.BusinessException;
import com.infolion.platform.dpframework.core.annotation.BDPController;
import com.infolion.platform.dpframework.core.cache.SysCachePoolUtils;
import com.infolion.platform.dpframework.uicomponent.attachement.domain.Attachement;
import com.infolion.platform.dpframework.uicomponent.grid.GridService;
import com.infolion.platform.dpframework.uicomponent.grid.data.AuthSql;
import com.infolion.platform.dpframework.uicomponent.grid.data.GridData;
import com.infolion.platform.dpframework.uicomponent.grid.data.GridQueryCondition;
import com.infolion.platform.dpframework.util.ExBeanUtils;
import com.infolion.platform.dpframework.util.StringUtils;
import com.infolion.platform.workflow.engine.WorkflowService;
import com.infolion.platform.workflow.engine.dao.ProcessJdbcDao;
import com.infolion.platform.workflow.engine.domain.ExtendTaskInstance;
import com.infolion.sapss.payment.PaymentContants;
import com.infolion.xdss3.BusinessState;
import com.infolion.xdss3.collect.service.CollectService;
import com.infolion.xdss3.customerDrawback.domain.CustomerDbBankItem;
import com.infolion.xdss3.customerDrawback.domain.CustomerRefundItem;
import com.infolion.xdss3.customerDrawback.domain.CustomerRefundment;
import com.infolion.xdss3.customerDrawbackGen.web.CustomerRefundmentControllerGen;
import com.infolion.xdss3.financeSquare.domain.FundFlow;
import com.infolion.xdss3.financeSquare.domain.SettleSubject;
import com.infolion.xdss3.reassign.ReassignConstant;
import com.infolion.xdss3.reassign.dao.ReassignJdbcDao;
import com.infolion.xdss3.reassign.domain.Reassign;
import com.infolion.xdss3.reassign.service.ReassignService;
import com.infolion.xdss3.singleClear.domain.ClearConstant;
import com.infolion.xdss3.singleClear.domain.IInfo;
import com.infolion.xdss3.singleClear.domain.IInfoVoucher;
import com.infolion.xdss3.singleClear.domain.ParameterVoucherObject;
import com.infolion.xdss3.singleClear.service.CustomerClearAccountService;
import com.infolion.xdss3.voucher.domain.Voucher;
import com.infolion.xdss3.voucher.service.VoucherService;

/**
 * <pre>
 * 客户退款(CustomerRefundment)控制器类
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
public class CustomerRefundmentController extends
		CustomerRefundmentControllerGen {

    @Autowired
    protected CollectService collectService;

    public void setCollectService(CollectService collectService)
    {
        this.collectService = collectService;
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
    private VoucherService voucherService;
    
    public void setVoucherService(VoucherService voucherService) {
        this.voucherService = voucherService;
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
	
	@Autowired
	private GridService gridService;
	
	public void setGridService(GridService gridService) {
		this.gridService = gridService;
	}
    
    @Autowired
    private ProcessJdbcDao processJdbcDao;
    
    public void setProcessJdbcDao(ProcessJdbcDao processJdbcDao) {
        this.processJdbcDao = processJdbcDao;
    }
	
    @Autowired
	private CustomerClearAccountService customerClearAccountService;
	

	/**
	 * @param customerClearAccountService the customerClearAccountService to set
	 */
	public void setCustomerClearAccountService(
			CustomerClearAccountService customerClearAccountService) {
		this.customerClearAccountService = customerClearAccountService;
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

	/**
	 * 获取退款行项目对象
	 * 
	 * @param request
	 * @param response
	 */
	public void _getRefundmentItemGridData(HttpServletRequest request,
			HttpServletResponse response) {
		String itemids = request.getParameter("itemids");

		List customerRefundItemList = this.customerRefundmentService
				.getCustomerRefundmentItemList(itemids);
		GridData gridJsonData = new GridData();
		gridJsonData.setData(customerRefundItemList.toArray());
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

		List customerDbBankItemList = this.customerRefundmentService
				.getCustomerDbBankItemList(itemids);
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
	 * 预览凭证
	 * 
	 * @param request
	 * @param response
	 */
    public void _preview(HttpServletRequest request, HttpServletResponse response){

        JSONObject jo = new JSONObject();
        // 绑定主对象值
        CustomerRefundment customerRefundment = (CustomerRefundment) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(),
                CustomerRefundment.class, true, request.getMethod(), true);
        // 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
        Set<CustomerDbBankItem> customerDbBankItemmodifyItems = ExBeanUtils.bindModifySubBoData(request.getParameterMap(),
                new Object[]{customerRefundment}, CustomerDbBankItem.class, null);
        customerRefundment.setCustomerDbBankItem(customerDbBankItemmodifyItems);
        Set<CustomerDbBankItem> customerDbBankItemdeleteItems = ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(),
                new Object[]{customerRefundment}, CustomerDbBankItem.class, null);
        // 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
        Set<CustomerRefundItem> customerRefundItemmodifyItems = ExBeanUtils.bindModifySubBoData(request.getParameterMap(),
                new Object[]{customerRefundment}, CustomerRefundItem.class, null);
        customerRefundment.setCustomerRefundItem(customerRefundItemmodifyItems);
        Set<CustomerRefundItem> customerRefundItemdeleteItems = ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(),
                new Object[]{customerRefundment}, CustomerRefundItem.class, null);
        // 绑定子对象(一对一关系)
        SettleSubject settleSubject = (SettleSubject) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), SettleSubject.class, false,
                request.getMethod(), true);
        if(settleSubject != null){
            customerRefundment.setSettleSubject(settleSubject);
            settleSubject.setCustomerRefundment(customerRefundment);
        }
        FundFlow fundFlow = (FundFlow) ExBeanUtils
                .bindBusinessObjectData(request.getParameterMap(), FundFlow.class, false, request.getMethod(), true);
        if(fundFlow != null){
            customerRefundment.setFundFlow(fundFlow);
            fundFlow.setCustomerRefundment(customerRefundment);
        }
        Set<Attachement> attachements = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), null, Attachement.class, null);
        this.customerRefundmentService._saveOrUpdate(customerRefundment, customerDbBankItemdeleteItems, customerRefundItemdeleteItems,attachements,
                getBusinessObject());
        
        customerRefundment = this.customerRefundmentService.getCustomerRefundmentById(customerRefundment.getRefundmentid());
        // 调用
//        this.collectService.updateCustomerTitle(customerRefundment.getCustomer());

        // 保存前先删除
        this.voucherService.deleteVoucherByBusinessid(customerRefundment.getRefundmentid());

        List<String> retList = this.customerRefundmentService.saveVoucher(customerRefundment);
        // 判断是否需要删除
        this.voucherService.judgeVoucherNeedDel(customerRefundment.getRefundmentid());

//        this.customerRefundmentService.saveClearVoucher(customerRefundment);
        // 保存<清帐凭证>后判断是否需要删除
       
        
        ParameterVoucherObject parameterVoucher =customerClearAccountService.setParameter(customerRefundment);
		BigDecimal marginAmount = new BigDecimal("0");
		Voucher settleSubjectVoucher = null;
		
//		if (settleSubject != null || fundFlow != null){			
//			settleSubjectVoucher = customerClearAccountService.saveSettleSubjectVoucher(parameterVoucher);				
//			marginAmount = customerClearAccountService.getMargin(settleSubjectVoucher.getVoucherItem());
//			parameterVoucher.setVoucherid(settleSubjectVoucher.getVoucherid());
//		}
		//判断是否需要删除
		this.voucherService.judgeVoucherNeedDel(customerRefundment.getRefundmentid());
		boolean isSave = false;
		if(!StringUtils.isNullBlank(customerRefundment.getRefundmentid()))isSave=true;
		IInfo info =customerClearAccountService.checkClearData(customerRefundment, marginAmount,isSave);
//		判断本次数据是否正确
		if(info.isRight()){
			IInfoVoucher infoVoucher = customerClearAccountService.isClearAccount(customerRefundment, marginAmount);
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
				boolean isp = customerClearAccountService.isProfitAndLoss(parameterVoucher.getCurrencytext(), parameterVoucher.getBukrs());
//				可以全清出清账凭证
				if(infoVoucher.isClear()){
					Voucher clearVoucher =customerClearAccountService.saveClearVoucherByCustomer(parameterVoucher,infoVoucher,customerRefundment.getRefundmentid(),ClearConstant.REFUNDMENT_TYPE,isp);
//					数据有错误
					if(null == clearVoucher){
						jo.put("isRight", false);
						jo.put("info", info.CODE_9);
						this.operateSuccessfullyHiddenInfoWithString(response, jo.toString());
					}
				}else{
//				部分清（有外币出汇损）,并且差额不为0
					if(isp && parameterVoucher.getSubtractVlaue().compareTo(new BigDecimal("0"))!= 0){						
						Voucher plVoucher = customerClearAccountService.saveProfitAndLossVoucher(parameterVoucher);
					}
					
				}
//				保存本次全清的数据，用来更新isclear状态
				request.getSession().setAttribute(customerRefundment.getRefundmentid(), infoVoucher);
				//判断是否需要删除
				this.voucherService.judgeVoucherNeedDel(customerRefundment.getRefundmentid());						
				try {
					response.setContentType("text/html;charset=UTF-8");
					response.getWriter().print(customerRefundment.getRefundmentid());
					System.out.println(customerRefundment.getRefundmentid());
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
		 this.voucherService.judgeVoucherNeedDel(customerRefundment.getRefundmentid());	
        try{
            String vouchids = "";
            for(int i = 0; i < retList.size(); i++){
                vouchids += retList.get(i);

                if(i + 1 < retList.size()){
                    vouchids += "&";
                }
            }
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(vouchids);
            System.out.println(vouchids);
        }catch(IOException e){
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
		CustomerRefundment customerRefundment = new CustomerRefundment();
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
		String companyID = this.customerRefundmentService
				.getCompanyIDByDeptID(userDeptid);
		request.setAttribute("userDeptid", userDeptid);
		request.setAttribute("companyid", companyID);
		request.setAttribute("userDeptName", userDeptName);
		request.setAttribute("cashFlowItem", "101");

		request.setAttribute("customerRefundment", customerRefundment);
		boolean haveBindWorkFlow = WorkflowService
				.getHaveBindWorkFlow("000000000291");
		request.setAttribute("haveBindWorkFlow", haveBindWorkFlow);
		request.setAttribute("workflowTaskId", workflowTaskId);
		request.setAttribute("workflowNodeDefId", workflowNodeDefId);
		return new ModelAndView("xdss3/customerDrawback/customerRefundmentAdd");
	}
	
	/**
	 * @创建作者：邱杰烜
	 * @创建时间：2010-11-04
	 * 重写"提交"方法
	 */
	public void _submitProcess(HttpServletRequest request,
			HttpServletResponse response) {
		// 绑定主对象值
		CustomerRefundment customerRefundment = (CustomerRefundment) ExBeanUtils
				.bindBusinessObjectData(request.getParameterMap(),
						CustomerRefundment.class, true, request.getMethod(),
						true);
		// 类型标识是从那个页面提交，view 表示从view页面提交流程。
		String type = request.getParameter("type");
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<CustomerDbBankItem> customerDbBankItemmodifyItems = ExBeanUtils
				.bindModifySubBoData(request.getParameterMap(),
						new Object[] { customerRefundment },
						CustomerDbBankItem.class, null);
		Set<CustomerDbBankItem> deletedCustomerDbBankItemSet = ExBeanUtils
				.bindDeleteSubBoData(request.getParameterMap(),
						new Object[] { customerRefundment },
						CustomerDbBankItem.class, null);
		customerRefundment.setCustomerDbBankItem(customerDbBankItemmodifyItems);
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<CustomerRefundItem> customerRefundItemmodifyItems = ExBeanUtils
				.bindModifySubBoData(request.getParameterMap(),
						new Object[] { customerRefundment },
						CustomerRefundItem.class, null);
		Set<CustomerRefundItem> deletedCustomerRefundItemSet = ExBeanUtils
				.bindDeleteSubBoData(request.getParameterMap(),
						new Object[] { customerRefundment },
						CustomerRefundItem.class, null);
		customerRefundment.setCustomerRefundItem(customerRefundItemmodifyItems);
		// 绑定子对象(一对一关系)
		SettleSubject settleSubject = (SettleSubject) ExBeanUtils
				.bindBusinessObjectData(request.getParameterMap(),
						SettleSubject.class, false, request.getMethod(), true);
		if (settleSubject != null) {
			customerRefundment.setSettleSubject(settleSubject);
			settleSubject.setCustomerRefundment(customerRefundment);
		}
		FundFlow fundFlow = (FundFlow) ExBeanUtils.bindBusinessObjectData(
				request.getParameterMap(), FundFlow.class, false, request
						.getMethod(), true);
		if (fundFlow != null) {
			customerRefundment.setFundFlow(fundFlow);
			fundFlow.setCustomerRefundment(customerRefundment);
		}
		//取得业务附件，业务ID
        Set<Attachement> attachements = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), null, Attachement.class, null);
		if (!"view".equalsIgnoreCase(type)) {
		    
            if (("财务会计审核并做账").equals(customerRefundment.getProcessstate()))
            {
                /*
                 * 因为凭证处理类会处理退款行项isClear字段，此时界面没有reload该字段，所以凭证窗口关闭时，不再保存界面的行项。
                 * 后果("财务会计审核并做账"节点只能通过 凭证预览_preview 来保存界面数据。)
                */
            	if("通过".equals(customerRefundment.getWorkflowLeaveTransitionName())){
            		// 客户未清抬头数据中的数据，判断是否已结清
            		String bussinessid = customerRefundment.getRefundmentid();
            		IInfoVoucher infoVoucher = (IInfoVoucher)request.getSession().getAttribute(bussinessid);
//            		是否全清
            		if(infoVoucher.isClear()){
            			customerClearAccountService.updateIsClear(bussinessid);
            		}else{
            			customerClearAccountService.updateIsClear(infoVoucher);
            		}		
            		request.getSession().removeAttribute(bussinessid);
            	}
            } else {
            	BigDecimal marginAmount = new BigDecimal("0");
            	boolean isSave = false;
        		if(!StringUtils.isNullBlank(customerRefundment.getRefundmentid()))isSave=true;
        		IInfo info =customerClearAccountService.checkClearData(customerRefundment, marginAmount,isSave);
        		//判断本次数据是否正确,不正确返回
        		if(!info.isRight()){
        			throw new BusinessException(info.getInfo());
        		}
    			this.customerRefundmentService._saveOrUpdate(customerRefundment,
    					deletedCustomerDbBankItemSet, deletedCustomerRefundItemSet,attachements,
    					getBusinessObject());
            }
		}
		/*
		 * 设置客户退款的代办信息
		 */
		String workflowBusinessNote = "";
		String deptName = com.infolion.platform.console.sys.context.UserContextHolder.getLocalUserContext().getUserContext().getSysUser().getDeptName();
		String creator = com.infolion.platform.console.sys.context.UserContextHolder.getLocalUserContext().getUserContext().getSysUser().getRealName();
		String bankAccount = "";
		String bankName = "";
		workflowBusinessNote = customerRefundment.getRefundmentno() + "|" + deptName + "|" + creator + "|金额" + customerRefundment.getRefundamount();
		customerRefundment.setWorkflowBusinessNote(workflowBusinessNote);
		
		this.customerRefundmentService._submitProcess(customerRefundment,
				deletedCustomerDbBankItemSet, deletedCustomerRefundItemSet,
				getBusinessObject());
		this.operateSuccessfully(response);
	}
	
	/**
	 * @创建作者：邱杰烜
	 * @创建时间：2010-10-14
	 * 重写"编辑"方法
	 */
	public ModelAndView _edit(HttpServletRequest request, HttpServletResponse response) {
		CustomerRefundment customerRefundment = new CustomerRefundment();
		String id = request.getParameter("refundmentid");
		String workflowTaskId = request.getParameter("workflowTaskId");
		String workflowNodeDefId = request.getParameter("workflowNodeDefId");
		String businessId = request.getParameter("businessId");
		if (null == id){
			id = businessId;
		}
		if (StringUtils.isNullBlank(id)) {
			customerRefundment = this.customerRefundmentService._getForEdit(id);
		} else {
			customerRefundment = this.customerRefundmentService._getForEdit(id);
		}
		boolean haveBindWorkFlow = WorkflowService.getHaveBindWorkFlow("000000000291");
		request.setAttribute("businessId", id);
		request.setAttribute("workflowTaskId", workflowTaskId);
		request.setAttribute("workflowNodeDefId", workflowNodeDefId);
		request.setAttribute("haveBindWorkFlow", haveBindWorkFlow);
		request.setAttribute("vt", getBusinessObject().getViewText());
		if (null != getBusinessObject().getSubBusinessObject()) {
			// 取得子业务对象
			for (BusinessObject bo : getBusinessObject().getSubBusinessObject()) {
				request.setAttribute(bo.getBeanAttribute().getBeanInstanceName() + "Vt", bo.getViewText());
			}
		}
		request.setAttribute("customerRefundment", customerRefundment);
		return new ModelAndView("xdss3/customerDrawback/customerRefundmentEdit");
	}
	
	/**
	 * @创建作者：邱杰烜
	 * @创建时间：2010-10-14
	 * 现金日记账
	 */
	public void _cashJournal(HttpServletRequest request, HttpServletResponse response){
		JSONObject jo = new JSONObject();
		// 绑定主对象值
		CustomerRefundment customerRefundment = (CustomerRefundment) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(),CustomerRefundment.class, true, request.getMethod(),true);
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<CustomerDbBankItem> customerDbBankItemmodifyItems = ExBeanUtils.bindModifySubBoData(request.getParameterMap(),new Object[] { customerRefundment },CustomerDbBankItem.class, null);
		customerRefundment.setCustomerDbBankItem(customerDbBankItemmodifyItems);
		Set<CustomerDbBankItem> customerDbBankItemdeleteItems = ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(),new Object[] { customerRefundment },CustomerDbBankItem.class, null);
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<CustomerRefundItem> customerRefundItemmodifyItems = ExBeanUtils.bindModifySubBoData(request.getParameterMap(),new Object[] { customerRefundment },CustomerRefundItem.class, null);
		customerRefundment.setCustomerRefundItem(customerRefundItemmodifyItems);
		Set<CustomerRefundItem> customerRefundItemdeleteItems = ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(),new Object[] { customerRefundment },CustomerRefundItem.class, null);
        // 绑定子对象(一对一关系)
        SettleSubject settleSubject = (SettleSubject) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), SettleSubject.class, false, request.getMethod(), true);
        if (settleSubject != null)
        {
            customerRefundment.setSettleSubject(settleSubject);
            settleSubject.setCustomerRefundment(customerRefundment);
        }
        FundFlow fundFlow = (FundFlow) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), FundFlow.class, false, request.getMethod(), true);
        if (fundFlow != null)
        {
            customerRefundment.setFundFlow(fundFlow);
            fundFlow.setCustomerRefundment(customerRefundment);
        }
      //取得业务附件，业务ID
        Set<Attachement> attachements = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), null, Attachement.class, null);
		this.customerRefundmentService._saveOrUpdate(customerRefundment, customerDbBankItemdeleteItems, customerRefundItemdeleteItems, attachements,getBusinessObject());
		/*
		 * 将现金日记账所需要的参数拼装成URL
		 */
		UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
		String username = userContext.getSysUser().getUserName();
		String xjrj = this.getProperties("config/config.properties").getProperty("xjrj");
		String journalType = "";		
		String bus_id   = "";		// 存放所有银行行项ID
		String bus_type = "";		// 业务类型 
//		String ps = customerRefundment.getProcessstate();
//		if("综合二部审核".equals(ps)){
//			journalType = "1";
//		}else if("资金部出纳审核".equals(ps) || "上海信达诺出纳审核".equals(ps)){
			journalType = "2";
//		}
		CustomerRefundment cr = this.customerRefundmentService._get(customerRefundment.getRefundmentid());
		int s = cr.getCustomerDbBankItem().size();
		for(CustomerDbBankItem customerDbBankItem : cr.getCustomerDbBankItem()){
			bus_id   += customerDbBankItem.getRefundbankitemid() + ",";
			bus_type += "4,";
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
		jo.put("refundmentno", customerRefundment.getRefundmentno());
		jo.put("refundmentid", customerRefundment.getRefundmentid());
		String creator = customerRefundment.getCreator();
		String creator_text = SysCachePoolUtils.getDictDataValue("YUSER",creator);
		jo.put("creator_text", creator_text);
		jo.put("creator", creator);
		jo.put("settlesubjectid", customerRefundment.getSettleSubject() != null ? customerRefundment.getSettleSubject().getSettlesubjectid() : "");
		jo.put("fundflowid", customerRefundment.getFundFlow() != null ? customerRefundment.getFundFlow().getFundflowid() : "");
		jo.put("createtime", customerRefundment.getCreatetime());
		jo.put("lastmodifytime", customerRefundment.getLastmodifytime());
		this.operateSuccessfullyHiddenInfoWithString(response, jo.toString());
	}
	
	/**
	 * 重分配复制
	 * 
	 * @param request
	 * @param response
	 */
	public ModelAndView _copyForReassign(HttpServletRequest request,
			HttpServletResponse response) {
		String businessId = request.getParameter("businessId");

		String workflowTaskId = request.getParameter("workflowTaskId");
	    String workflowNodeDefId = request.getParameter("workflowNodeDefId");
	    Reassign  reassign =this.reassignService._get(businessId);
		String id = this.reassignJdbcDao.getBoidByReassignid(businessId);
		
		/**
		 * 判断是否已经拷贝过，
		 */
		CustomerRefundment customerRefundment;
		String boid = this.reassignJdbcDao.isCopyed(id, ReassignConstant.CUSTOMERREFUNDMENT);
		if(  boid != null )
		{
			
			customerRefundment = this.customerRefundmentService.getCustomerRefundmentById(boid);
		}
		else
		{
			customerRefundment = this.customerRefundmentService._get(id);
			request.setAttribute("isCreateCopy", "true");
			customerRefundment.setReassigneddbid(id); 	// 设置被重分配退款ID
			customerRefundment.setBusinessstate(BusinessState.REASSIGNED);	// 设置为重分配状态
			customerRefundment.setText(reassign.getText()); // 设置文本为重分配提交时填写文本
		}	
		request.setAttribute("userDeptid", customerRefundment.getDept_id());
		request.setAttribute("cashFlowItem", customerRefundment.getCashflowitem());
		request.setAttribute("customerRefundment", customerRefundment);
		request.setAttribute("vt", getBusinessObject().getViewText());
		if (null != getBusinessObject().getSubBusinessObject()) {
			// 取得子业务对象
			for (BusinessObject bo : getBusinessObject().getSubBusinessObject()) {
				request.setAttribute(bo.getBeanAttribute()
						.getBeanInstanceName()
						+ "Vt", bo.getViewText());
			}
		}
		boolean haveBindWorkFlow = WorkflowService.getHaveBindWorkFlow("000000000291");
		request.setAttribute("isReassign", "Y");
		request.setAttribute("customerRefundment", customerRefundment);
		request.setAttribute("haveBindWorkFlow", haveBindWorkFlow);
		request.setAttribute("workflowTaskId", workflowTaskId);
		request.setAttribute("workflowNodeDefId", workflowNodeDefId);
		return new ModelAndView("xdss3/customerDrawback/customerRefundmentAdd");
	}

	/**
	 * 重分配查看
	 * 
	 * @param request
	 * @param response
	 */
	public ModelAndView _viewForReassign(HttpServletRequest request,
			HttpServletResponse response) {
		
		CustomerRefundment customerRefundment = new CustomerRefundment();
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
			id = this.reassignJdbcDao.getNewNoIdByOldNoId(ReassignConstant.CUSTOMERREFUNDMENT, oldId);
		}
		
		if (null == id)
			id = businessId;

		if (StringUtils.isNullBlank(id)) {
			customerRefundment = this.customerRefundmentService._getForEdit(id);
		} else {
			customerRefundment = this.customerRefundmentService._getForEdit(id);
		}

		boolean haveBindWorkFlow = WorkflowService
				.getHaveBindWorkFlow("000000000328");
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
		request.setAttribute("customerRefundment", customerRefundment);
		return new ModelAndView("xdss3/customerDrawback/customerRefundmentView");
	}
	
	/**
	 * 重分配编辑
	 * 
	 * @param request
	 * @param response
	 */
	public ModelAndView _editForReassign(HttpServletRequest request,
			HttpServletResponse response) {
		
		CustomerRefundment customerRefundment = new CustomerRefundment();
		String workflowTaskId = request.getParameter("workflowTaskId");
		String workflowNodeDefId = request.getParameter("workflowNodeDefId");
		String businessId = request.getParameter("businessId");
		String oldId = this.reassignJdbcDao.getBoidByReassignid(businessId);
		String id = this.reassignJdbcDao.getNewNoIdByOldNoId(ReassignConstant.CUSTOMERREFUNDMENT, oldId);
		
		if (null == id)
			id = businessId;

		if (StringUtils.isNullBlank(id)) {
			customerRefundment = this.customerRefundmentService._getForEdit(id);
		} else {
			customerRefundment = this.customerRefundmentService._getForEdit(id);
		}

		boolean haveBindWorkFlow = WorkflowService
				.getHaveBindWorkFlow("000000000328");
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
		request.setAttribute("customerRefundment", customerRefundment);
		return new ModelAndView("xdss3/customerDrawback/customerRefundmentEdit");
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
		CustomerRefundment customerRefundment = (CustomerRefundment) ExBeanUtils
				.bindBusinessObjectData(request.getParameterMap(),
						CustomerRefundment.class, true, request.getMethod(),
						true);
		// 类型标识是从那个页面提交，view 表示从view页面提交流程。
		String type = request.getParameter("type");
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<CustomerDbBankItem> customerDbBankItemmodifyItems = ExBeanUtils
				.bindModifySubBoData(request.getParameterMap(),
						new Object[] { customerRefundment },
						CustomerDbBankItem.class, null);
		Set<CustomerDbBankItem> deletedCustomerDbBankItemSet = ExBeanUtils
				.bindDeleteSubBoData(request.getParameterMap(),
						new Object[] { customerRefundment },
						CustomerDbBankItem.class, null);
		customerRefundment.setCustomerDbBankItem(customerDbBankItemmodifyItems);
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<CustomerRefundItem> customerRefundItemmodifyItems = ExBeanUtils
				.bindModifySubBoData(request.getParameterMap(),
						new Object[] { customerRefundment },
						CustomerRefundItem.class, null);
		Set<CustomerRefundItem> deletedCustomerRefundItemSet = ExBeanUtils
				.bindDeleteSubBoData(request.getParameterMap(),
						new Object[] { customerRefundment },
						CustomerRefundItem.class, null);
		customerRefundment.setCustomerRefundItem(customerRefundItemmodifyItems);
		//取得业务附件，业务ID
		Set<Attachement> attachements = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), null, Attachement.class, null);
        // 绑定子对象(一对一关系)
        SettleSubject settleSubject = (SettleSubject) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), SettleSubject.class, false, request.getMethod(), true);
        if (settleSubject != null)
        {
            customerRefundment.setSettleSubject(settleSubject);
            settleSubject.setCustomerRefundment(customerRefundment);
        }
        FundFlow fundFlow = (FundFlow) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), FundFlow.class, false, request.getMethod(), true);
        if (fundFlow != null)
        {
            customerRefundment.setFundFlow(fundFlow);
            fundFlow.setCustomerRefundment(customerRefundment);
        }
		if ("view".equalsIgnoreCase(type)) {
		    customerRefundment.setBusinessstate("2");
			
		}
		
		String reassignId = this.reassignJdbcDao.getReassignidByBoId(customerRefundment.getReassigneddbid());		
		Reassign reassign = this.reassignService.getReassignById(reassignId);
		reassign.setWorkflowTaskId(workflowTaskId);	
		reassign.setWorkflowLeaveTransitionName(customerRefundment.getWorkflowLeaveTransitionName());
		reassign.setWorkflowExamine(customerRefundment.getWorkflowExamine());
		reassign.setWorkflowUserDefineProcessVariable(customerRefundment.getWorkflowUserDefineProcessVariable());
		reassign.setWorkflowBusinessNote(customerRefundment.getWorkflowBusinessNote());
		reassign.setWorkflowUserDefineTaskVariable(customerRefundment.getWorkflowUserDefineTaskVariable());
		/**
		 * 判断重分配方式是否为： 重置（到业务部门重新分配） 需要拷贝凭证
		 */
		if( reassign.getProcessstate()!= null && !reassign.getProcessstate().equals("财务部审核") && 
				reassign.getReassigntmethod().equals(ReassignConstant.RESET_TO_BS) )
		{
			this.reassignService.copyVoucher(reassign.getReassignboid(), customerRefundment.getRefundmentid());
		}
		//审核通过
		if(reassign.getProcessstate() != null
				&& reassign.getProcessstate().equals("财务部审核") && reassign.getWorkflowLeaveTransitionName().equals("确认")){
			customerRefundment.setBusinessstate("3");			
		}
		if(reassign.getProcessstate() != null
				&& reassign.getProcessstate().equals("财务部审核") && reassign.getWorkflowLeaveTransitionName().equals("作废")){
			customerRefundment.setBusinessstate("-1");			
		}
		//设置审核状态
		if( customerRefundment.getWorkflowLeaveTransitionName().equals("作废"))
		{
			this.reassignService.updateReassignState(reassignId, BusinessState.SUBMITNOTPASS);
		}
		if ("view".equalsIgnoreCase(type)) {
		   
			this.customerRefundmentService._saveOrUpdate(customerRefundment,
					deletedCustomerDbBankItemSet, deletedCustomerRefundItemSet,attachements,
					getBusinessObject());
		}
		BusinessObject businessObject = BusinessObjectService.getBusinessObject("Reassign");
		this.reassignService._submitProcess(reassign, businessObject);
		this.operateSuccessfully(response);
	}
	
	/**
	 * 客户退款查询 
	 * 陈非 2011-01-21
	 */
	public ModelAndView _conditionQuery(HttpServletRequest request,
			HttpServletResponse response) {
		request.setAttribute("vt", getBusinessObject().getViewText());
		return new ModelAndView("xdss3/customerDrawback/customerMultiConditionQuery");
	}
	
	public void queryGrid(HttpServletRequest request,
			HttpServletResponse response, GridQueryCondition gridQueryCondition)
			throws Exception{
		BusinessObject businessObject = BusinessObjectService.getBusinessObject("CustomerRefundment");
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
		
		StringBuffer sql = new StringBuffer("SELECT DISTINCT a.*,b.contract_no,b.project_no,b.collectNo,dp.NAME1 as customer_name,o.ORGANIZATIONNAME as DEPT_NAME FROM YREFUNDMENT a left join YGETKUNNR dp on ( a.CUSTOMER=dp.KUNNR ) left join YORGANIZATION o on ( a.dept_id=o.organizationid ) LEFT JOIN YREFUNDMENTITEM b ON a.REFUNDMENTID=b.REFUNDMENTID WHERE 1=1 AND trim(a.customer) <> 0");
		// 获取SQL参数
		String refundmentno = (String)request.getParameter("refundmentno");		// 退款单号
		String contractNo = (String)request.getParameter("contract_no");		// 合同号
		String projectNo = (String)request.getParameter("project_no");			// 立项号
		String collectNo = (String)request.getParameter("collectno");			// 收款编号
		String amount1 = (String)request.getParameter("amount1");				// 退款金额1
		String amount2 = (String)request.getParameter("amount2");				// 退款金额2
		String titleText = (String)request.getParameter("title_text");			// 抬头文本
		titleText = URLDecoder.decode(titleText, "UTF-8");
		String customer = (String)request.getParameter("customer");				// 客户编号
		String deptId = (String)request.getParameter("dept_id");				// 部门ID
		String applyDate1 = (String)request.getParameter("apply_date1");		// 申请时间1
		String applyDate2 = (String)request.getParameter("apply_date2");		// 申请时间2
		applyDate1 = applyDate1.replaceAll(signRegExp, "");			// 删除"申请时间1"中的所有符号
		applyDate2 = applyDate2.replaceAll(signRegExp, "");			// 删除"申请时间2"中的所有符号
		String approvalDate1 = (String)request.getParameter("approval_date1");	// 审批通过时间1
		String approvalDate2 = (String)request.getParameter("approval_date2");	// 审批通过时间2
		approvalDate1 = approvalDate1.replaceAll(signRegExp, "");	// 删除"审批通过时间1"中的所有符号
		approvalDate2 = approvalDate2.replaceAll(signRegExp, "");	// 删除"审批通过时间2"中的所有符号
		
		String voucherNo = (String)request.getParameter("voucherno");           // 凭证号
		String businessstate = (String)request.getParameter("businessstate");	// 业务状态
		
		// 设置客户编号条件
		if(StringUtils.isNotBlank(customer)){
			sql.append("AND a.CUSTOMER = '" + customer + "'");
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
		if(StringUtils.isNotBlank(projectNo)){
			sql.append("AND b.PROJECT_NO LIKE '%" + projectNo + "%'");
		}
		// 设置收款编号条件
		if(StringUtils.isNotBlank(collectNo)){
			sql.append("AND b.COLLECTNO LIKE '%" + collectNo + "%'");
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
		gridQueryCondition.setHandlerClass("com.infolion.xdss3.customerDrawback.web.CustomerGrid");
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
	
	public ModelAndView _print(HttpServletRequest request,
            HttpServletResponse response) {
        String refundmentId = request.getParameter("refundmentId");
        CustomerRefundment customerMent = this.customerRefundmentService.getCustomerRefundmentById(refundmentId);
        customerMent.setCreatorName(com.infolion.platform.sys.cache.SysCachePoolUtils.getDictDataValue("T_SYS_USER",customerMent.getCreator()));
        customerMent.setDeptName(com.infolion.platform.sys.cache.SysCachePoolUtils.getDictDataValue("T_SYS_DEPT", customerMent.getDept_id()));
        String customerName = this.customerRefundmentService.getCustomerName(customerMent.getCustomer());
        String coantractNoStr = this.customerRefundmentService.queryContractNoByRefundmentId(refundmentId);
        List<ExtendTaskInstance> listTaskIns = processJdbcDao.getExtTaskInstances2(refundmentId);
        Collections.sort(listTaskIns, new Comparator<ExtendTaskInstance>() {
            public int compare(ExtendTaskInstance a, ExtendTaskInstance b) {
            return a.getTaskEndTime().compareTo(b.getTaskEndTime());
            }
        });

        if (coantractNoStr == null)
            coantractNoStr = "";

        request.setAttribute("contractNoStr", coantractNoStr);
        request.setAttribute("taskHis", listTaskIns);
        request.setAttribute("customerName", customerName);
        request.setAttribute("main", customerMent);
        request.setAttribute("payValueBig", PaymentContants
                .changeToBig(new Double(customerMent.getRefundamount()
                        .doubleValue())));
        return new ModelAndView("xdss3/customerDrawback/customerRefundmentPrint");
    }
	
	
}