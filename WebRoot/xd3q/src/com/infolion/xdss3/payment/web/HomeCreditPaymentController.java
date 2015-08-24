/*
 * @(#)HomeCreditPaymentController.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2013年11月19日 11点35分25秒
 *  描　述：创建
 */
package com.infolion.xdss3.payment.web;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

import com.infolion.xdss3.CommonDataAuth;
import com.infolion.xdss3.financeSquare.domain.FundFlow;
import com.infolion.xdss3.financeSquare.domain.SettleSubject;
import com.infolion.xdss3.masterData.dao.VendorTitleJdbcDao;
import com.infolion.xdss3.masterData.service.SupplierService;
import com.infolion.xdss3.payment.domain.HomeCreditBankItem;
import com.infolion.xdss3.payment.domain.HomeCreditDocuBank;
import com.infolion.xdss3.payment.domain.HomeCreditPayCbill;
import com.infolion.xdss3.payment.domain.HomeCreditPayItem;
import com.infolion.xdss3.payment.domain.HomeCreditPayment;
import com.infolion.xdss3.payment.domain.HomeCreditRebank;
import com.infolion.xdss3.payment.domain.HomeCreditRelatPay;
import com.infolion.xdss3.payment.domain.ImportPayment;

import com.infolion.xdss3.payment.domain.HomePaymentCbill;
import com.infolion.xdss3.payment.service.HomeCreditPaymentService;
import com.infolion.xdss3.payment.service.HomePaymentCbillService;
import com.infolion.xdss3.payment.service.HomePaymentService;
import com.infolion.xdss3.payment.service.ImportPaymentService;
import com.infolion.xdss3.payment.service.PaymentItemService;
import com.infolion.xdss3.paymentGen.web.HomeCreditPaymentControllerGen;
import com.infolion.xdss3.reassign.dao.ReassignJdbcDao;
import com.infolion.xdss3.reassign.service.ReassignService;
import com.infolion.xdss3.singleClear.domain.ClearConstant;
import com.infolion.xdss3.singleClear.domain.IInfo;
import com.infolion.xdss3.singleClear.domain.IInfoVoucher;
import com.infolion.xdss3.singleClear.domain.ParameterVoucherObject;
import com.infolion.xdss3.singleClear.service.SupplierClearAccountService;
import com.infolion.xdss3.voucher.domain.Voucher;
import com.infolion.xdss3.voucher.service.VoucherService;
import com.infolion.platform.bo.domain.BusinessObject;
import com.infolion.platform.bo.domain.Property;
import com.infolion.platform.console.org.domain.SysRole;
import com.infolion.platform.console.sys.context.UserContext;
import com.infolion.platform.console.sys.context.UserContextHolder;
import com.infolion.platform.dpframework.core.BusinessException;
import com.infolion.platform.dpframework.core.annotation.BDPController;
import com.infolion.platform.dpframework.uicomponent.attachement.domain.Attachement;
import com.infolion.platform.dpframework.uicomponent.grid.GridService;
import com.infolion.platform.dpframework.uicomponent.searchHelp.service.SearchHelpService;
import com.infolion.platform.dpframework.util.ExBeanUtils;
import com.infolion.platform.dpframework.util.StringUtils;
import com.infolion.platform.util.DateUtils;
import com.infolion.platform.util.MultyGridData;
import com.infolion.platform.workflow.engine.WorkflowService;
import com.infolion.platform.workflow.engine.dao.ProcessJdbcDao;
import com.infolion.platform.workflow.engine.domain.ExtendTaskInstance;
import com.infolion.sapss.payment.PaymentContants;
import com.infolion.sapss.payment.service.PaymentInnerInfoJDBCService;

/**
 * <pre>
 * 国内信用证(HomeCreditPayment)控制器类
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
public class HomeCreditPaymentController extends HomeCreditPaymentControllerGen
{
    @Autowired
    private GridService gridService;
    
    public void setGridService(GridService gridService) {
        this.gridService = gridService;
    }
    
	@Autowired
	private ReassignJdbcDao reassignJdbcDao;

	@Autowired
	private SupplierService supplierService;

	public void setSupplierService(SupplierService supplierService) {
		this.supplierService = supplierService;
	}

	@Autowired
	private VoucherService voucherService;

	public void setVoucherService(VoucherService voucherService) {
		this.voucherService = voucherService;
	}

	/**
	 * @param reassignJdbcDao
	 *            the reassignJdbcDao to set
	 */
	public void setReassignJdbcDao(ReassignJdbcDao reassignJdbcDao) {
		this.reassignJdbcDao = reassignJdbcDao;
	}
	
    @Autowired
    private PaymentInnerInfoJDBCService paymentInnerInfoJDBCService;

    public PaymentInnerInfoJDBCService getPaymentInnerInfoJDBCService() {
        return paymentInnerInfoJDBCService;
    }

    public void setPaymentInnerInfoJDBCService(
            PaymentInnerInfoJDBCService paymentInnerInfoJDBCService) {
        this.paymentInnerInfoJDBCService = paymentInnerInfoJDBCService;
    }
    
	@Autowired
	HomePaymentService homePaymentService;

	/**
	 * @param homePaymentService
	 *            the homePaymentService to set
	 */
	public void setHomePaymentService(HomePaymentService homePaymentService) {
		this.homePaymentService = homePaymentService;
	}

	@Autowired
	private ReassignService reassignService;

	/**
	 * @param reassignService
	 *            the reassignService to set
	 */
	public void setReassignService(ReassignService reassignService) {
		this.reassignService = reassignService;
	}

	@Autowired
	private VendorTitleJdbcDao vendorTitleJdbcDao;

	public void setVendorTitleJdbcDao(VendorTitleJdbcDao vendorTitleJdbcDao) {
		this.vendorTitleJdbcDao = vendorTitleJdbcDao;
	}

	@Autowired
	private HomePaymentCbillService homePaymentCbillService;

	public void setHomePaymentCbillService(
			HomePaymentCbillService homePaymentCbillService) {
		this.homePaymentCbillService = homePaymentCbillService;
	}

	@Autowired
	private ProcessJdbcDao processJdbcDao;

	/**
	 * @param processJdbcDao
	 *            the processJdbcDao to set
	 */
	public void setProcessJdbcDao(ProcessJdbcDao processJdbcDao) {
		this.processJdbcDao = processJdbcDao;
	}

	@Autowired
	protected SearchHelpService searchHelpService;

	public void setSearchHelpService(SearchHelpService searchHelpService) {
		this.searchHelpService = searchHelpService;
	}
	
	@Autowired
	private PaymentItemService paymentItemService;

	/**
	 * @param paymentItemService
	 *            the paymentItemService to set
	 */
	public void setPaymentItemService(PaymentItemService paymentItemService) {
		this.paymentItemService = paymentItemService;
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
	
	@Autowired
	protected HomeCreditPaymentService homeCreditPaymentService;
	
	public void setHomeCreditPaymentService(HomeCreditPaymentService homeCreditPaymentService)
	{
		this.homeCreditPaymentService = homeCreditPaymentService;
	}
	
	@Autowired
	protected ImportPaymentService importPaymentService;

	public void setImportPaymentService(ImportPaymentService importPaymentService)
	{
		this.importPaymentService = importPaymentService;
	}
	
	public void getSupplierByID(HttpServletRequest request,
			HttpServletResponse response) {
		String supplier = request.getParameter("supplierid");
		List<Map<String,String>> paymentList = this.homePaymentService.getBankInfoBySupplierNo(supplier);
		JSONObject jo = new JSONObject();
		if(paymentList.size() > 0){
			jo.put("collectbankid",  paymentList.get(0).get("COLLECTBANKID"));
			jo.put("collectbankacc", paymentList.get(0).get("COLLECTBANKACC"));
		}else{
			jo.put("collectbankid",  " ");
			jo.put("collectbankacc", " ");
		}
		this.operateSuccessfullyHiddenInfoWithString(response, jo.toString());
	}
	/**
	 * 创建
	 * 
	 * @param request
	 * @param response
	 */
	public ModelAndView _create(HttpServletRequest request,
			HttpServletResponse response) {
		UserContext userContext = UserContextHolder.getLocalUserContext()
				.getUserContext();
		String strPaymentType = request.getParameter("paymenttype");

		String calActivityId = request.getParameter("calActivityId");
		if (StringUtils.isNotBlank(calActivityId))
			request.setAttribute("calActivityId", calActivityId);
		HomeCreditPayment homePayment = new HomeCreditPayment();

		homePayment.setTrade_type("01");
		homePayment.setPaymentstate("0");
		homePayment.setIsrepresentpay("0");
		homePayment.setIstradesubsist("0");
		homePayment.setIsoverrepay("0");
		homePayment.setBusinessstate("0");
		homePayment.setPaymenttype(strPaymentType);
		homePayment.setCloseexchangerat(new BigDecimal("1"));
		homePayment.setExchangerate(new BigDecimal("1"));
		homePayment.setCurrency("CNY");
		homePayment.setFactcurrency("CNY");
		homePayment.setRedocaryrate(new BigDecimal("1"));
		homePayment.setDoctaryinterest("1");
		homePayment.setDocumentaryrate(new BigDecimal("1"));
		homePayment.setDoctaryrealrate(new BigDecimal("1"));
		homePayment.setDept_id(userContext.getSysDept().getDeptid());
		homePayment.setPay_type("3");
		homePayment.setApplyamount(new BigDecimal("0"));
		com.infolion.platform.console.sys.context.UserContext xdssUserContext = com.infolion.platform.console.sys.context.UserContextHolder
				.getLocalUserContext().getUserContext();
		if (null != xdssUserContext)
			homePayment.setDept_id(xdssUserContext.getSysDept().getDeptid());

		BusinessObject boHomePayment = getBusinessObject();
		
		String workflowTaskId = request.getParameter("workflowTaskId");
		String workflowNodeDefId = request.getParameter("workflowNodeDefId");
		request.setAttribute("vt", boHomePayment.getViewText());

		request.setAttribute("homeCreditPayment", homePayment);
		boolean haveBindWorkFlow = WorkflowService
				.getHaveBindWorkFlow("000000000315");
		request.setAttribute("haveBindWorkFlow", haveBindWorkFlow);
		request.setAttribute("workflowTaskId", workflowTaskId);
		request.setAttribute("workflowNodeDefId", workflowNodeDefId);

		String strRoleType = "";
		for (int i = 0; i < userContext.getGrantedRoles().size(); i++) {
			SysRole sysRole = (SysRole) userContext.getGrantedRoles().get(i);

			if ("业务员".indexOf(sysRole.getRoleName()) != -1) {
				strRoleType = "1";
			}
		}

		request.setAttribute("roletype", strRoleType);
		
		Set<Property> properties = boHomePayment.getProperties();

		for (Iterator<Property> iter = properties.iterator(); iter.hasNext();) {
			Property property = (Property) iter.next();
			if (!StringUtils.isNullBlank(property.getSearchHelp())) {
				String value = com.infolion.platform.dpframework.util.BeanUtils.getPropertyValue(homePayment, property.getPropertyName());
				String shlpData = this.searchHelpService.getShlpDataByBoName(property, value);

				request.setAttribute(getBoName() + property.getPropertyName(),shlpData);
			}
		}
		
		List<BusinessObject> subBos = boHomePayment.getSubBusinessObject("SettleSubject,FundFlow");
		if (null != subBos) {
			// 取得子业务对象
			for (BusinessObject bo : subBos) {
				request.setAttribute(bo.getBeanAttribute().getBeanInstanceName() + "Vt", bo.getViewText());
			}
		}
		
		return new ModelAndView("xdss3/payment/homeCreditPaymentAdd");
	}
	
	/**
	 * @创建作者 钟志华
	 * @创建日期 2011-2-17 日期比较器
	 * 
	 */
	static class DateComparator implements Comparator {  
		  public int compare(Object object1, Object object2) {// 实现接口中的方法
			  HomePaymentCbill p1 = (HomePaymentCbill) object1; // 强制转换
			  HomePaymentCbill p2 = (HomePaymentCbill) object2; 
			  int d=DateUtils.getIntervalDays(p1.getAccountdate(),p2.getAccountdate());
		   return new Double("0").compareTo( new Double(d));
		  }
	}
	
	/**
	 * 增加发票信息
	 * 
	 * @param request
	 * @param response
	 */
	public void _addBillInfo(HttpServletRequest request,
			HttpServletResponse response) {
		String strBillNoList = request.getParameter("billnolist");

		List<HomePaymentCbill> homePaymentCbillList = new ArrayList<HomePaymentCbill>();

		homePaymentCbillList = this.homePaymentCbillService
				._addBillInfo(strBillNoList);

		MultyGridData gridJsonData = new MultyGridData();
		gridJsonData.setData(homePaymentCbillList.toArray());
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
	 * 编辑
	 * 
	 * @param request
	 * @param response
	 */
	public ModelAndView _edit(HttpServletRequest request,
			HttpServletResponse response) {
		HomeCreditPayment homePayment = new HomeCreditPayment();
		String id = request.getParameter("paymentid");
		String workflowTaskId = request.getParameter("workflowTaskId");
		String workflowNodeDefId = request.getParameter("workflowNodeDefId");
		String businessId = request.getParameter("businessId");
		if (null == id)
			id = businessId;

		if (StringUtils.isNullBlank(id)) {
			homePayment = this.homeCreditPaymentService._getForEdit(id);
		} else {
			homePayment = this.homeCreditPaymentService._getForEdit(id);
		}

		BusinessObject boHomePayment = getBusinessObject();
		
		String processstate = homePayment.getProcessstate();
		if (processstate.equals("资金部确认银行承兑汇票或国内信用证到期付款")||processstate.equals("确认银行承兑汇票或国内信用证到期付款")) {// 清空"付款结算科目"和"付款纯资金"两个页签数据
			homePayment.setSettleSubject(null);
			homePayment.setFundFlow(null);
			this.homePaymentService
					.deleteSettleFund(homePayment.getPaymentid());
		}

		boolean haveBindWorkFlow = WorkflowService
				.getHaveBindWorkFlow("000000000261");
		request.setAttribute("businessId", id);
		request.setAttribute("workflowTaskId", workflowTaskId);
		request.setAttribute("workflowNodeDefId", workflowNodeDefId);
		request.setAttribute("haveBindWorkFlow", haveBindWorkFlow);
		request.setAttribute("vt", boHomePayment.getViewText());
		
		request.setAttribute("homeCreditPayment", homePayment);

		UserContext userContext = UserContextHolder.getLocalUserContext()
				.getUserContext();
		String strRoleType = "";
		for (int i = 0; i < userContext.getGrantedRoles().size(); i++) {
			SysRole sysRole = (SysRole) userContext.getGrantedRoles().get(i);

			if ("业务员".indexOf(sysRole.getRoleName()) != -1) {
				strRoleType = "1";
			}
		}

		Properties prop = this.getProperties("config/config.properties");
		String xjrj = prop.getProperty("xjrj");
		if (xjrj != null && !"".equals(xjrj)) {
			request.setAttribute("xjrj", xjrj);
		}

		request.setAttribute("roletype", strRoleType);
		request.setAttribute("username", userContext.getSysUser().getUserName());

		request.setAttribute("roletype", strRoleType);
		
		Set<Property> properties = boHomePayment.getProperties();

		for (Iterator<Property> iter = properties.iterator(); iter.hasNext();) {
			Property property = (Property) iter.next();
			if (!StringUtils.isNullBlank(property.getSearchHelp())) {
				String value = com.infolion.platform.dpframework.util.BeanUtils.getPropertyValue(homePayment, property.getPropertyName());
				String shlpData = this.searchHelpService.getShlpDataByBoName(property, value);

				request.setAttribute(getBoName() + property.getPropertyName(),shlpData);
			}
		}
		
		List<BusinessObject> subBos = boHomePayment.getSubBusinessObject();
		
		if (null != subBos) {
			// 取得子业务对象
			for (BusinessObject subBo : subBos) {
				request.setAttribute(subBo.getBeanAttribute().getBeanInstanceName() + "Vt", subBo.getViewText());
				
				if(subBo.getBoName().equals("settleSubject")||subBo.getBoName().equals("fundFlow")){
					Set<Property> subproperties = subBo.getProperties();
					for (Iterator<Property> subiter = subproperties.iterator(); subiter.hasNext();) {
						Property subproperty = (Property) subiter.next();
						if (!StringUtils.isNullBlank(subproperty.getSearchHelp())) {
							String value = "";
							if (subBo.getBoName().equals("settleSubject")) {
								value = com.infolion.platform.dpframework.util.BeanUtils.getPropertyValue(homePayment.getSettleSubject(), subproperty.getPropertyName());
							}
							if (subBo.getBoName().equals("fundFlow")) {
								value = com.infolion.platform.dpframework.util.BeanUtils.getPropertyValue(homePayment.getFundFlow(), subproperty.getPropertyName());
							}
							String shlpData = this.searchHelpService.getShlpDataByBoName(subproperty, value);

							request.setAttribute(subBo.getBoName() + subproperty.getPropertyName(), shlpData);
						}
					}
				}
				
			}
		}
		
		
		return new ModelAndView("xdss3/payment/homeCreditPaymentEdit");
	}
	
	/**
	 * 
	 * @param absolutePath
	 */
	public Properties getProperties(String absolutePath) {
		Properties p = new Properties();
		try {
			InputStream is = this.getClass().getClassLoader()
					.getResourceAsStream(absolutePath);
			p.load(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return p;
	}
	
	/**
	 * 管理
	 * 
	 * @param request
	 * @param response
	 */
	public ModelAndView _manage(HttpServletRequest request,
			HttpServletResponse response) {
		request.setAttribute("vt", getBusinessObject().getViewText());
		String strPaymentType = request.getParameter("paymenttype");
		String strTradeType = request.getParameter("tradetype");

		String strDataAuthSql = CommonDataAuth.getDataAuthSql(this
				.getBusinessObject());
		request.setAttribute("dataAuthSql", strDataAuthSql);

		request.setAttribute("paymenttype", strPaymentType);
		request.setAttribute("tradetype", strTradeType);

		return new ModelAndView("xdss3/payment/homeCreditPaymentManage");
	}

	public ModelAndView _print(HttpServletRequest request,
			HttpServletResponse response) {
		String paymentId = request.getParameter("paymentId");
		HomeCreditPayment homePayment = this.homeCreditPaymentService
				._getDetached(paymentId);
		String creator = homePayment.getCreator();
		homePayment.setCreatorName(com.infolion.platform.sys.cache.SysCachePoolUtils.getDictDataValue("T_SYS_USER",
				creator));
		String dept_id = homePayment.getDept_id();
		homePayment
				.setDeptName(com.infolion.platform.sys.cache.SysCachePoolUtils
						.getDictDataValue("T_SYS_DEPT", dept_id));
		// start yanghancai 2010-09-25 增加供应商号码关联供应商名称
		String supplier = homePayment.getSupplier();
		String supplierName = supplierService.getSupplierName(supplier);
		// end yanghancai 2010-09-25 增加供应商号码关联供应商名称
		String coantractNoStr = this.paymentItemService
				.queryContractNoByPayMentId(paymentId);
		List<ExtendTaskInstance> listTaskIns = processJdbcDao
				.getExtTaskInstances2(paymentId);
		Collections.sort(listTaskIns, new Comparator<ExtendTaskInstance>() {
			public int compare(ExtendTaskInstance a, ExtendTaskInstance b) {
			return a.getTaskEndTime().compareTo(b.getTaskEndTime());
			}
		});

		//Collections.sort(listTaskIns, new Comparator<ExtendTaskInstance>);
		if (coantractNoStr == null)
			coantractNoStr = "";

		request.setAttribute("contractNoStr", coantractNoStr);
		request.setAttribute("taskHis", listTaskIns);
		request.setAttribute("supplierName", supplierName); // yanghancai
															// 2010-09-25
															// 获取供应商名称
		request.setAttribute("main", homePayment);
		// String payValue = homePayment.getFactamount().divide(new
		// BigDecimal(1), 2, RoundingMode.HALF_UP).toString();
		// request.setAttribute("payValue", payValue);
		request.setAttribute("payValueBig", PaymentContants
				.changeToBig(new Double(homePayment.getApplyamount()
						.doubleValue())));
		return new ModelAndView("xdss3/payment/homeCreditPaymentPrint");
	}
	
	/**
	 * 检查背书时的银行承兑汇票号信息
	 */
	public void _checkDraftInfo(HttpServletRequest request,
			HttpServletResponse response) {
		String strDraftInfo = request.getParameter("draftinfo");
		String strPaymentId = request.getParameter("paymentid");

		JSONObject jsonObject = new JSONObject();

		int iResult = this.homePaymentCbillService._checkDraftInfo(
				strDraftInfo, strPaymentId);
		jsonObject.put("result", iResult);
		if (iResult == 0) {
			jsonObject.put("text", "不存在或有不只一个有银行承兑汇票号!");
		}

		if (iResult == 1) {
			jsonObject.put("text", "银行承兑汇票号已经使用过!");
		}

		String jsontext = jsonObject.toString();

		try {
			response.setContentType("text/html;charset=UTF-8");
			response.getWriter().print(jsontext);
		} catch (IOException e) {
			logger.error("输出json失败," + e.getMessage(), e.getCause());
		}
	}
	/**
	 * 模拟凭证
	 * 
	 * @param request
	 * @param response
	 */
	public void _simulate(HttpServletRequest request, HttpServletResponse response)
	{
		JSONObject jo = new JSONObject();
		// 绑定主对象值
		HomeCreditPayment homeCreditPayment = (HomeCreditPayment) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), HomeCreditPayment.class , true , request.getMethod(), true);
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<HomeCreditPayItem> homeCreditPayItemmodifyItems = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { homeCreditPayment }, HomeCreditPayItem.class, null);
		homeCreditPayment.setHomeCreditPayItem(homeCreditPayItemmodifyItems);
		/***判断行项目立项信息是否有配置客户授信类型为代垫的，如有，则代垫到期日必须填写*/
		for(HomeCreditPayItem item : homeCreditPayItemmodifyItems){
			if(homePaymentService.isValidCustCreditPro(item.getProject_no())&&StringUtils.isEmpty(homeCreditPayment.getReplacedate().trim()))
				throw new BusinessException("代垫到期日必须填写!");
		}
		Set<HomeCreditPayItem> homeCreditPayItemdeleteItems = ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(), new Object[] { homeCreditPayment }, HomeCreditPayItem.class, null);
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<HomeCreditPayCbill> homeCreditPayCbillmodifyItems = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { homeCreditPayment }, HomeCreditPayCbill.class, null);
		homeCreditPayment.setHomeCreditPayCbill(homeCreditPayCbillmodifyItems);
		Set<HomeCreditPayCbill> homeCreditPayCbilldeleteItems = ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(), new Object[] { homeCreditPayment }, HomeCreditPayCbill.class, null);
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<HomeCreditBankItem> homeCreditBankItemmodifyItems = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { homeCreditPayment }, HomeCreditBankItem.class, null);
		homeCreditPayment.setHomeCreditBankItem(homeCreditBankItemmodifyItems);
		Set<HomeCreditBankItem> homeCreditBankItemdeleteItems = ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(), new Object[] { homeCreditPayment }, HomeCreditBankItem.class, null);
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<HomeCreditDocuBank> homeCreditDocuBankmodifyItems = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { homeCreditPayment }, HomeCreditDocuBank.class, null);
		homeCreditPayment.setHomeCreditDocuBank(homeCreditDocuBankmodifyItems);
		Set<HomeCreditDocuBank> homeCreditDocuBankdeleteItems = ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(), new Object[] { homeCreditPayment }, HomeCreditDocuBank.class, null);
		//绑定子对象(一对一关系)
		SettleSubject settleSubject = (SettleSubject) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), SettleSubject.class, false , request.getMethod(), true);
		if(settleSubject!=null)
		{
		homeCreditPayment.setSettleSubject(settleSubject);
		settleSubject.setHomeCreditPayment(homeCreditPayment);
		}
		//绑定子对象(一对一关系)
		FundFlow fundFlow = (FundFlow) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), FundFlow.class, false , request.getMethod(), true);
		if(fundFlow!=null)
		{
		homeCreditPayment.setFundFlow(fundFlow);
		fundFlow.setHomeCreditPayment(homeCreditPayment);
		}
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<HomeCreditRebank> homeCreditRebankmodifyItems = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { homeCreditPayment }, HomeCreditRebank.class, null);
		homeCreditPayment.setHomeCreditRebank(homeCreditRebankmodifyItems);
		Set<HomeCreditRebank> homeCreditRebankdeleteItems = ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(), new Object[] { homeCreditPayment }, HomeCreditRebank.class, null);
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<HomeCreditRelatPay> homeCreditRelatPaymodifyItems = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { homeCreditPayment }, HomeCreditRelatPay.class, null);
		homeCreditPayment.setHomeCreditRelatPay(homeCreditRelatPaymodifyItems);
		Set<HomeCreditRelatPay> homeCreditRelatPaydeleteItems = ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(), new Object[] { homeCreditPayment }, HomeCreditRelatPay.class, null);
		//取得业务附件，业务ID
		Set<Attachement> attachements = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), null, Attachement.class, null);
		this.homeCreditPaymentService._saveOrUpdate(homeCreditPayment
		,homeCreditPayItemdeleteItems
		,homeCreditPayCbilldeleteItems
		,homeCreditBankItemdeleteItems
		,homeCreditDocuBankdeleteItems
		,homeCreditRebankdeleteItems
		,homeCreditRelatPaydeleteItems
		,attachements,getBusinessObject());
		
		String strSimulateType = request.getParameter("simulatetype");

		List<String> retList = new ArrayList<String>();

		HomeCreditPayment _homeCreditPayment = this.homeCreditPaymentService._get(homeCreditPayment.getPaymentid());
		
	
		this.voucherService.deleteVoucherByBusinessid(homeCreditPayment.getPaymentid());
		// 人民币清帐
		if (strSimulateType.equals("1") == true)
		{
			retList = this.homeCreditPaymentService.cnySaveVoucher(_homeCreditPayment);			
		}
		// 应付票据
		if (strSimulateType.equals("2") == true) {
			retList = this.homeCreditPaymentService.dealBillSaveVoucher(_homeCreditPayment);		
		}

		// 应付票据清帐
		if (strSimulateType.equals("3") == true) {
			retList = this.homeCreditPaymentService.dealBillClearSaveVoucher(_homeCreditPayment);
		}
		
		// 短期外汇借款
		if (strSimulateType.equals("8") == true)
		{
			retList = this.homeCreditPaymentService.shortTimepaySaveVoucher(_homeCreditPayment);
			
		
		}

		// 还短期外汇借款
		if (strSimulateType.equals("9") == true)
		{
			//币别一致
			//if(_importPayment.getCurrency().equals(_importPayment.getCurrency2())){
			if(null ==_homeCreditPayment.getRedocaryamount() || _homeCreditPayment.getRedocaryamount().compareTo(new BigDecimal("0")) ==0){
				throw new BusinessException("还押汇金额不能为空或者0");				
			}			
			retList = this.homeCreditPaymentService.returnShortTimeSaveVoucher(_homeCreditPayment);
			this.voucherService.judgeVoucherNeedDel_2(_homeCreditPayment.getPaymentid());
			
		} 
        if (             
                "9".equals(strSimulateType)
                ){
		    // 还短期外汇借款 与 纯代理业务(还押汇时) 不再做判断
		}  else
		{
    		this.voucherService.judgeVoucherNeedDel(_homeCreditPayment.getPaymentid());
		}
        
//		生成清账凭证，zzh
		if ("1".equals(strSimulateType) || "2".equals(strSimulateType) || "3".equals(strSimulateType) || "6".equals(strSimulateType) || "7".equals(strSimulateType) || "8".equals(strSimulateType)) {
//			如果有清票时
			if(null !=_homeCreditPayment.getHomeCreditPayCbill() && _homeCreditPayment.getHomeCreditPayCbill().size()!=0){		
				ParameterVoucherObject parameterVoucher =supplierClearAccountService.setParameter(_homeCreditPayment);
				BigDecimal marginAmount = new BigDecimal("0");
				Voucher settleSubjectVoucher = null;
				
//				if (_importPayment.getImportSettSubj() != null || _importPayment.getImportFundFlow() != null){			
//					settleSubjectVoucher = supplierClearAccountService.saveSettleSubjectVoucher(parameterVoucher);				
//					marginAmount = supplierClearAccountService.getMargin(settleSubjectVoucher.getVoucherItem());
////					客户跟供应商借贷方相反
//					marginAmount = new BigDecimal("0").subtract(marginAmount);
//					parameterVoucher.setVoucherid(settleSubjectVoucher.getVoucherid());
//				}
				//判断是否需要删除
				this.voucherService.judgeVoucherNeedDel(_homeCreditPayment.getPaymentid());
				boolean isSave = false;
				if(!StringUtils.isNullBlank(_homeCreditPayment.getPaymentid()))isSave=true;
				IInfo info =supplierClearAccountService.checkClearData(_homeCreditPayment, marginAmount,isSave);
//				判断本次数据是否正确
				if(info.isRight()){
					IInfoVoucher infoVoucher = supplierClearAccountService.isClearAccount(_homeCreditPayment, marginAmount);
					if(null != infoVoucher.getSubtractVlaue()){
						parameterVoucher.setSubtractVlaue(infoVoucher.getSubtractVlaue().setScale(2, BigDecimal.ROUND_HALF_UP));
					}
					if(null != infoVoucher.getSumAdjustamount()){
						parameterVoucher.setSumAdjustamount(infoVoucher.getSumAdjustamount().setScale(2, BigDecimal.ROUND_HALF_UP));
					}
					parameterVoucher.setTaxCode(infoVoucher.getTaxCode());
//					判断以前的业务数据是否正确
					if(infoVoucher.isRight()){
//						是否出汇损
						boolean isp = supplierClearAccountService.isProfitAndLoss(parameterVoucher.getCurrencytext(), parameterVoucher.getBukrs());
//						可以全清出清账凭证
						if(infoVoucher.isClear()){
							Voucher clearVoucher =supplierClearAccountService.saveClearVoucherBySupplier(parameterVoucher,infoVoucher,_homeCreditPayment.getPaymentid(),ClearConstant.PAYMENT_TYPE,isp);
//							数据有错误
							if(null == clearVoucher){
								jo.put("isRight", false);
								jo.put("info", info.CODE_9);
								this.operateSuccessfullyHiddenInfoWithString(response, jo.toString());
							}
						}else{
//						部分清（有外币出汇损）,并且差额不为0
							if(isp && parameterVoucher.getSubtractVlaue().compareTo(new BigDecimal("0"))!= 0){									
								Voucher plVoucher = supplierClearAccountService.saveProfitAndLossVoucher(parameterVoucher);
							}
							
						}
//						保存本次全清的数据，用来更新isclear状态
						request.getSession().setAttribute(_homeCreditPayment.getPaymentid(), infoVoucher);
						//判断是否需要删除
						this.voucherService.judgeVoucherNeedDel(_homeCreditPayment.getPaymentid());						
						try {
							response.setContentType("text/html;charset=UTF-8");
							response.getWriter().print(_homeCreditPayment.getPaymentid());
							System.out.println(_homeCreditPayment.getPaymentid());
						} catch (IOException e) {
							logger.error("输出json失败," + e.getMessage(), e.getCause());
						}
					}else{
//						数据错误不能保存
						throw new BusinessException(infoVoucher.getInfo());
					}
				}else{
//					数据错误不能保存
					throw new BusinessException(info.getInfo());
				}
			}
			this.voucherService.judgeVoucherNeedDel(_homeCreditPayment.getPaymentid());
		}
		
		try
		{
			String vouchids = "";
			for (int i = 0; i < retList.size(); i++)	
			{
				vouchids += retList.get(i);

				if (i + 1 < retList.size())
				{
					vouchids += "&";
				}
			}
			response.setContentType("text/html;charset=UTF-8");
			response.getWriter().print(vouchids);
		}
		catch (IOException e)
		{
			logger.error("输出json失败," + e.getMessage(), e.getCause());
		}
		
		
	}
	public void cashJournal(HttpServletRequest request, HttpServletResponse response)
	{
		JSONObject jo = new JSONObject();
		// 绑定主对象值
		HomeCreditPayment homeCreditPayment = (HomeCreditPayment) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), HomeCreditPayment.class , true , request.getMethod(), true);
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<HomeCreditPayItem> homeCreditPayItemmodifyItems = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { homeCreditPayment }, HomeCreditPayItem.class, null);
		homeCreditPayment.setHomeCreditPayItem(homeCreditPayItemmodifyItems);
		/***判断行项目立项信息是否有配置客户授信类型为代垫的，如有，则代垫到期日必须填写*/
		for(HomeCreditPayItem item : homeCreditPayItemmodifyItems){
			if(homePaymentService.isValidCustCreditPro(item.getProject_no())&&StringUtils.isEmpty(homeCreditPayment.getReplacedate().trim()))
				throw new BusinessException("代垫到期日必须填写!");
		}
		Set<HomeCreditPayItem> homeCreditPayItemdeleteItems = ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(), new Object[] { homeCreditPayment }, HomeCreditPayItem.class, null);
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<HomeCreditPayCbill> homeCreditPayCbillmodifyItems = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { homeCreditPayment }, HomeCreditPayCbill.class, null);
		homeCreditPayment.setHomeCreditPayCbill(homeCreditPayCbillmodifyItems);
		Set<HomeCreditPayCbill> homeCreditPayCbilldeleteItems = ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(), new Object[] { homeCreditPayment }, HomeCreditPayCbill.class, null);
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<HomeCreditBankItem> homeCreditBankItemmodifyItems = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { homeCreditPayment }, HomeCreditBankItem.class, null);
		homeCreditPayment.setHomeCreditBankItem(homeCreditBankItemmodifyItems);
		Set<HomeCreditBankItem> homeCreditBankItemdeleteItems = ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(), new Object[] { homeCreditPayment }, HomeCreditBankItem.class, null);
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<HomeCreditDocuBank> homeCreditDocuBankmodifyItems = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { homeCreditPayment }, HomeCreditDocuBank.class, null);
		homeCreditPayment.setHomeCreditDocuBank(homeCreditDocuBankmodifyItems);
		Set<HomeCreditDocuBank> homeCreditDocuBankdeleteItems = ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(), new Object[] { homeCreditPayment }, HomeCreditDocuBank.class, null);
		//绑定子对象(一对一关系)
		SettleSubject settleSubject = (SettleSubject) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), SettleSubject.class, false , request.getMethod(), true);
		if(settleSubject!=null)
		{
		homeCreditPayment.setSettleSubject(settleSubject);
		settleSubject.setHomeCreditPayment(homeCreditPayment);
		}
		//绑定子对象(一对一关系)
		FundFlow fundFlow = (FundFlow) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), FundFlow.class, false , request.getMethod(), true);
		if(fundFlow!=null)
		{
		homeCreditPayment.setFundFlow(fundFlow);
		fundFlow.setHomeCreditPayment(homeCreditPayment);
		}
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<HomeCreditRebank> homeCreditRebankmodifyItems = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { homeCreditPayment }, HomeCreditRebank.class, null);
		homeCreditPayment.setHomeCreditRebank(homeCreditRebankmodifyItems);
		Set<HomeCreditRebank> homeCreditRebankdeleteItems = ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(), new Object[] { homeCreditPayment }, HomeCreditRebank.class, null);
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<HomeCreditRelatPay> homeCreditRelatPaymodifyItems = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { homeCreditPayment }, HomeCreditRelatPay.class, null);
		homeCreditPayment.setHomeCreditRelatPay(homeCreditRelatPaymodifyItems);
		Set<HomeCreditRelatPay> homeCreditRelatPaydeleteItems = ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(), new Object[] { homeCreditPayment }, HomeCreditRelatPay.class, null);
		//取得业务附件，业务ID
		Set<Attachement> attachements = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), null, Attachement.class, null);
		this.homeCreditPaymentService._saveOrUpdate(homeCreditPayment
		,homeCreditPayItemdeleteItems
		,homeCreditPayCbilldeleteItems
		,homeCreditBankItemdeleteItems
		,homeCreditDocuBankdeleteItems
		,homeCreditRebankdeleteItems
		,homeCreditRelatPaydeleteItems
		,attachements,getBusinessObject());
		
		this.operateSuccessfullyHiddenInfo(response);

	}
	
	/**
	 * @创建作者：邱杰烜
	 * @创建日期：2010-09-27
	 * 根据部门ID去取得押汇业务范围
	 */
	public void getRedocarybcByDeptID(HttpServletRequest request, HttpServletResponse response){
		JSONObject jsonObj = new JSONObject();
		String deptId = request.getParameter("deptid");
		String redocarybc = this.importPaymentService.getRedocarybcByDeptID(deptId);
		if(null==redocarybc){
			redocarybc = "";
		}
		jsonObj.put("redocarybc", redocarybc);
		this.operateSuccessfullyWithString(response, jsonObj.toString());
	}
	
	/**
	 * @创建作者：邱杰烜
	 * @创建日期：2010-10-25
	 * 查看相关单据的信息
	 */
	public ModelAndView viewRelatedInfo(HttpServletRequest request, HttpServletResponse response){
		String relatedNo = request.getParameter("relatedNo");
		ImportPayment importPayment = new ImportPayment();
		importPayment = this.importPaymentService.getPaymentByNo(relatedNo);
		String workflowTaskId = request.getParameter("workflowTaskId");
		String workflowNodeDefId = request.getParameter("workflowNodeDefId");
		boolean haveBindWorkFlow = WorkflowService.getHaveBindWorkFlow("000000000261");
		request.setAttribute("haveBindWorkFlow", haveBindWorkFlow);
		request.setAttribute("businessId", "");
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
		request.setAttribute("importPayment", importPayment);
		return new ModelAndView("xdss3/payment/importPaymentView");
	}
	 public void checkClearData(HttpServletRequest request,
				HttpServletResponse response) {
		 JSONObject jo = new JSONObject();
			// 绑定主对象值
			HomeCreditPayment homeCreditPayment = (HomeCreditPayment) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), HomeCreditPayment.class , true , request.getMethod(), true);
			// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
			Set<HomeCreditPayItem> homeCreditPayItemmodifyItems = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { homeCreditPayment }, HomeCreditPayItem.class, null);
			homeCreditPayment.setHomeCreditPayItem(homeCreditPayItemmodifyItems);
			/***判断行项目立项信息是否有配置客户授信类型为代垫的，如有，则代垫到期日必须填写*/
			for(HomeCreditPayItem item : homeCreditPayItemmodifyItems){
				if(homePaymentService.isValidCustCreditPro(item.getProject_no())&&StringUtils.isEmpty(homeCreditPayment.getReplacedate().trim()))
					throw new BusinessException("代垫到期日必须填写!");
			}
			Set<HomeCreditPayItem> homeCreditPayItemdeleteItems = ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(), new Object[] { homeCreditPayment }, HomeCreditPayItem.class, null);
			// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
			Set<HomeCreditPayCbill> homeCreditPayCbillmodifyItems = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { homeCreditPayment }, HomeCreditPayCbill.class, null);
			homeCreditPayment.setHomeCreditPayCbill(homeCreditPayCbillmodifyItems);
			Set<HomeCreditPayCbill> homeCreditPayCbilldeleteItems = ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(), new Object[] { homeCreditPayment }, HomeCreditPayCbill.class, null);
			// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
			Set<HomeCreditBankItem> homeCreditBankItemmodifyItems = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { homeCreditPayment }, HomeCreditBankItem.class, null);
			homeCreditPayment.setHomeCreditBankItem(homeCreditBankItemmodifyItems);
			Set<HomeCreditBankItem> homeCreditBankItemdeleteItems = ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(), new Object[] { homeCreditPayment }, HomeCreditBankItem.class, null);
			// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
			Set<HomeCreditDocuBank> homeCreditDocuBankmodifyItems = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { homeCreditPayment }, HomeCreditDocuBank.class, null);
			homeCreditPayment.setHomeCreditDocuBank(homeCreditDocuBankmodifyItems);
			Set<HomeCreditDocuBank> homeCreditDocuBankdeleteItems = ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(), new Object[] { homeCreditPayment }, HomeCreditDocuBank.class, null);
			//绑定子对象(一对一关系)
			SettleSubject settleSubject = (SettleSubject) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), SettleSubject.class, false , request.getMethod(), true);
			if(settleSubject!=null)
			{
			homeCreditPayment.setSettleSubject(settleSubject);
			settleSubject.setHomeCreditPayment(homeCreditPayment);
			}
			//绑定子对象(一对一关系)
			FundFlow fundFlow = (FundFlow) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), FundFlow.class, false , request.getMethod(), true);
			if(fundFlow!=null)
			{
			homeCreditPayment.setFundFlow(fundFlow);
			fundFlow.setHomeCreditPayment(homeCreditPayment);
			}
			// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
			Set<HomeCreditRebank> homeCreditRebankmodifyItems = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { homeCreditPayment }, HomeCreditRebank.class, null);
			homeCreditPayment.setHomeCreditRebank(homeCreditRebankmodifyItems);
			Set<HomeCreditRebank> homeCreditRebankdeleteItems = ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(), new Object[] { homeCreditPayment }, HomeCreditRebank.class, null);
			// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
			Set<HomeCreditRelatPay> homeCreditRelatPaymodifyItems = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { homeCreditPayment }, HomeCreditRelatPay.class, null);
			homeCreditPayment.setHomeCreditRelatPay(homeCreditRelatPaymodifyItems);
			Set<HomeCreditRelatPay> homeCreditRelatPaydeleteItems = ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(), new Object[] { homeCreditPayment }, HomeCreditRelatPay.class, null);
			//取得业务附件，业务ID
			Set<Attachement> attachements = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), null, Attachement.class, null);
			this.homeCreditPaymentService._saveOrUpdate(homeCreditPayment
			,homeCreditPayItemdeleteItems
			,homeCreditPayCbilldeleteItems
			,homeCreditBankItemdeleteItems
			,homeCreditDocuBankdeleteItems
			,homeCreditRebankdeleteItems
			,homeCreditRelatPaydeleteItems
			,attachements,getBusinessObject());
//			如果有清票时
			if(null !=homeCreditPayment.getHomeCreditPayCbill()){			
				BigDecimal marginAmount = new BigDecimal("0");
				boolean isSave = false;
				if(!StringUtils.isNullBlank(homeCreditPayment.getPaymentid())){
					isSave=true;				
				}
				IInfo info =supplierClearAccountService.checkClearData(homeCreditPayment, marginAmount,isSave);
				jo.put("isRight", info.isRight());
				jo.put("info", info.getInfo());
				this.operateSuccessfullyHiddenInfoWithString(response, jo.toString());
			}else{
				jo.put("isRight", true);			
				this.operateSuccessfullyHiddenInfoWithString(response, jo.toString());
			}
	 }
}