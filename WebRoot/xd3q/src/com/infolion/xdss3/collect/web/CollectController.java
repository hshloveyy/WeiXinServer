/*
 * @(#)CollectController.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年05月27日 07点45分09秒
 *  描　述：创建
 */
package com.infolion.xdss3.collect.web;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.collections.map.ListOrderedMap;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

import com.ctc.wstx.util.DataUtil;
import com.infolion.platform.bo.domain.BusinessObject;
import com.infolion.platform.bo.domain.Property;
import com.infolion.platform.bo.service.BusinessObjectService;
import com.infolion.platform.calendar.domain.CalActivity;
import com.infolion.platform.calendar.service.CalActivityService;
import com.infolion.platform.console.org.dao.SysDeptJdbcDao;
import com.infolion.platform.console.org.domain.SysRole;
import com.infolion.platform.console.sys.context.UserContext;
import com.infolion.platform.console.sys.context.UserContextHolder;
import com.infolion.platform.dpframework.core.BusinessException;
import com.infolion.platform.dpframework.core.annotation.BDPController;
import com.infolion.platform.dpframework.core.cache.SysCachePoolUtils;
import com.infolion.platform.dpframework.uicomponent.attachement.dao.AttachementJdbcDao;
import com.infolion.platform.dpframework.uicomponent.attachement.service.AttachementService;
import com.infolion.platform.dpframework.uicomponent.grid.GridService;
import com.infolion.platform.dpframework.uicomponent.grid.data.AuthSql;
import com.infolion.platform.dpframework.uicomponent.grid.data.GridData;
import com.infolion.platform.dpframework.uicomponent.grid.data.GridQueryCondition;
import com.infolion.platform.dpframework.uicomponent.searchHelp.service.SearchHelpService;
import com.infolion.platform.util.DateUtils;
import com.infolion.platform.dpframework.util.EasyApplicationContextUtils;
import com.infolion.platform.dpframework.util.ExBeanUtils;
import com.infolion.platform.dpframework.util.StringUtils;

import com.infolion.platform.util.MultyGridData;
import com.infolion.platform.workflow.engine.WorkflowService;
import com.infolion.sapss.common.ExcelObject;
import com.infolion.sapss.masterQuery.service.ReportService;
import com.infolion.sapss.masterQuery.web.ReportUtil;
import com.infolion.xdss3.BusinessState;
import com.infolion.xdss3.CommonDataAuth;
import com.infolion.xdss3.UpdateState;
import com.infolion.xdss3.collect.dao.CollectHibernateDao;
import com.infolion.xdss3.collect.domain.Collect;
import com.infolion.xdss3.collectGen.web.CollectControllerGen;
import com.infolion.xdss3.collectbankitem.domain.CollectBankItem;
import com.infolion.xdss3.collectcbill.dao.CollectCbillJdbcDao;
import com.infolion.xdss3.collectcbill.domain.CollectCbill;
import com.infolion.xdss3.collectcbill.service.CollectCbillService;
import com.infolion.xdss3.collectitem.domain.CollectItem;
import com.infolion.xdss3.collectitem.service.CollectItemService;
import com.infolion.xdss3.collectrelated.domain.CollectRelated;
import com.infolion.xdss3.financeSquare.domain.FundFlow;
import com.infolion.xdss3.financeSquare.domain.SettleSubject;
import com.infolion.xdss3.masterData.domain.CustomerTitle;
import com.infolion.xdss3.masterData.service.SyncMasterDataService;
import com.infolion.xdss3.masterData.service.UnclearCustomerService;
import com.infolion.xdss3.reassign.ReassignConstant;
import com.infolion.xdss3.reassign.dao.ReassignJdbcDao;
import com.infolion.xdss3.reassign.domain.Reassign;
import com.infolion.xdss3.reassign.service.ReassignService;
import com.infolion.xdss3.singleClear.domain.ClearConstant;
import com.infolion.xdss3.singleClear.domain.IInfo;
import com.infolion.xdss3.singleClear.domain.IInfoVoucher;
import com.infolion.xdss3.singleClear.domain.ParameterVoucherObject;
import com.infolion.xdss3.singleClear.service.CustomerClearAccountService;
import com.infolion.xdss3.unnameCollect.domain.UnnamerCollect;
import com.infolion.xdss3.unnameCollect.service.UnnamerCollectService;
import com.infolion.xdss3.voucher.domain.Voucher;
import com.infolion.xdss3.voucher.service.VoucherService;

/**
 * <pre>
 * 收款信息表(Collect)控制器类
 * </pre>
 * 
 * <br>
 * JDK版本:1.5
 * 
 * @author java业务平台代码生成工具getBillCbillData
 * @version 1.0
 * @see The author for more details
 * @since 1.0
 */
@BDPController(parent = "baseMultiActionController")
public class CollectController extends CollectControllerGen {
    
    
    @Autowired
    private ReportService reportService;
    
    public void setReportService(ReportService reportService) {
        this.reportService = reportService;
    }
    
    
	@Autowired
	private AttachementJdbcDao attachementJdbcDao;

	public void setAttachementJdbcDao(AttachementJdbcDao attachementJdbcDao) {
		this.attachementJdbcDao = attachementJdbcDao;
	}

	@Autowired
	protected UnclearCustomerService unclearCustomerService;

	public void setUnclearCustomerService(
			UnclearCustomerService unclearCustomerService) {
		this.unclearCustomerService = unclearCustomerService;
	}

	@Autowired
	protected CollectCbillService collectCbillService;

	public void setCollectCbillService(CollectCbillService collectCbillService) {
		this.collectCbillService = collectCbillService;
	}

	@Autowired
	protected UnnamerCollectService unnamerCollectService;

	public void setUnnamerCollectService(
			UnnamerCollectService unnamerCollectService) {
		this.unnamerCollectService = unnamerCollectService;
	}

	@Autowired
	private SyncMasterDataService syncMasterDataService;

	public void setSyncMasterDataService(
			SyncMasterDataService syncMasterDataService) {
		this.syncMasterDataService = syncMasterDataService;
	}

	@Autowired
	private VoucherService voucherService;

	public void setVoucherService(VoucherService voucherService) {
		this.voucherService = voucherService;
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
	private ReassignJdbcDao reassignJdbcDao;

	/**
	 * @param reassignJdbcDao
	 *            the reassignJdbcDao to set
	 */
	public void setReassignJdbcDao(ReassignJdbcDao reassignJdbcDao) {
		this.reassignJdbcDao = reassignJdbcDao;
	}

	@Autowired
	protected CollectItemService collectItemService;

	public void setCollectItemService(CollectItemService collectItemService) {
		this.collectItemService = collectItemService;
	}

	@Autowired
	protected CollectCbillJdbcDao collectCbillJdbcDao;

	public void setCollectCbillJdbcDao(CollectCbillJdbcDao collectCbillJdbcDao) {
		this.collectCbillJdbcDao = collectCbillJdbcDao;
	}

	@Autowired
	protected CollectHibernateDao collectHibernateDao;

	public void setCollectHibernateDao(CollectHibernateDao collectHibernateDao) {
		this.collectHibernateDao = collectHibernateDao;
	}

	@Autowired
	private AttachementService attachementService;

	public void setAttachementService(AttachementService attachementService) {
		this.attachementService = attachementService;
	}

	@Autowired
	protected SearchHelpService searchHelpService;

	public void setSearchHelpService(SearchHelpService searchHelpService) {
		this.searchHelpService = searchHelpService;
	}

	@Autowired
	private GridService gridService;

	public void setGridService(GridService gridService) {
		this.gridService = gridService;
	}

	@Autowired	
	private SysDeptJdbcDao sysDeptJdbcDao;
	
	
	public void setSysDeptJdbcDao(SysDeptJdbcDao sysDeptJdbcDao) {
		this.sysDeptJdbcDao = sysDeptJdbcDao;
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
	
	/**
	 * 同步指定客户的未清据
	 * 
	 * @param request
	 * @param response
	 */
	public void syncUnclearCustomer(HttpServletRequest request,
			HttpServletResponse response) {
		String customer = request.getParameter("customer");
//		先不同步
		syncMasterDataService._synchronizeUnclearCustomer(customer,false);
		// this.collectService.updateCustomerTitle(customer);
	
	}
	//检验数据
	public void updateCT(HttpServletRequest request,
			HttpServletResponse response) {
		String customer = request.getParameter("customer");		
		 this.collectService.updateCustomerTitle(customer);
	
	}
	/**
	 * 同步当天的冲销凭证
	 * @param request
	 * @param response
	 */
	public void syncWriteoff(HttpServletRequest request,
			HttpServletResponse response) {
		String bukrs = request.getParameter("bukrs");	
		if(StringUtils.isNullBlank(bukrs)){
			UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();		
			bukrs = sysDeptJdbcDao.getCompanyCode(userContext.getSysDept().getDeptid());
		}
		syncMasterDataService._synchronizeWriteOff(bukrs, null, null, "1");
	}
	
	/**
	 * 更新未清数据
	 * 
	 * @param request
	 * @param response
	 */
	public void updateCustomerTitle(HttpServletRequest request,
			HttpServletResponse response) {
		String customer = request.getParameter("customer");
		String collectid = request.getParameter("collectid");

		// 邱杰烜 2011-05-18 更新收款子对象（收款银行、结算科目、纯资金往来）的Flag字段
		this.collectService.updateCollectSubObjFlag(collectid);
		
		// 客户未清抬头数据中的数据，判断是否已结清
		String bussinessid = collectid;
		IInfoVoucher infoVoucher = (IInfoVoucher)request.getSession().getAttribute(bussinessid);
//		是否全清
		if(infoVoucher.isClear()){
			customerClearAccountService.updateIsClear(bussinessid);
		}else{
			customerClearAccountService.updateIsClear(infoVoucher);
		}		
		request.getSession().removeAttribute(bussinessid);
		
//		// 同一客户要等更新完成后才能做账
//		UpdateState.getMap().put(customer, "customer");
//		log.debug("----------------执行客户(" + customer + ")未清抬头状态更新的存储过程……");
//		try {
//			this.collectService.updateCustomerTitle2(customer, collectid);
//		} catch (Exception ex) {
//			log.debug("----------------执行客户(" + customer
//					+ ")未清抬头状态更新的存储过程土地出错！");
//			UpdateState.getMap().remove(customer);
//		} finally {
//			UpdateState.getMap().remove(customer);
//		}
//		UpdateState.getMap().remove(customer);
//		log.debug("----------------执行客户(" + customer + ")未清抬头状态更新的存储过程成功！");
	}

	/**
	 * 取得状态,在调用存储过程中，可能出现时间差，通过这个来判断存储过程是否执行完。有返回值，说明还在执行，不能模拟凭证
	 * 
	 * @param request
	 * @param response
	 */
	public void getUpdateState(HttpServletRequest request,
			HttpServletResponse response) {
		JSONObject jo = new JSONObject();
		String customer = request.getParameter("customer");
		String cus = UpdateState.getMap().get(customer);
		if (!StringUtils.isNullBlank(cus)) {
			jo.put("customer", customer);
		} else {
			jo.put("customer", "");
		}

		try {
			response.setContentType("text/html;charset=UTF-8");
			response.getWriter().print(jo.get("customer"));
			System.out.println(jo.toString());
		} catch (IOException e) {
			logger.error("输出json失败," + e.getMessage(), e.getCause());
		}
	}

	/**
	 * 保证金转货款用于搜索帮助，计算实际保证金
	 * 
	 * @param request
	 * @param response
	 */
	public void updateCollectDeposit(HttpServletRequest request,
			HttpServletResponse response) {
		this.collectService.updateCollectDeposit();
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
		Collect collect = new Collect();
		BusinessObject bocollect = getBusinessObject();

		String workflowTaskId = request.getParameter("workflowTaskId");
		String workflowNodeDefId = request.getParameter("workflowNodeDefId");
		request.setAttribute("vt", bocollect.getViewText());

		String companycode = this.collectService.getUserCompany();

		com.infolion.platform.console.sys.context.UserContext xdssUserContext = com.infolion.platform.console.sys.context.UserContextHolder
				.getLocalUserContext().getUserContext();
		if (null != xdssUserContext)
			collect.setDept_id(xdssUserContext.getSysUser().getDeptId());

		collect.setIncsuretybond("N");
		request.setAttribute("companycode", companycode);
		request.setAttribute("collect", collect);
		boolean haveBindWorkFlow = WorkflowService
				.getHaveBindWorkFlow("000000000258");
		request.setAttribute("haveBindWorkFlow", haveBindWorkFlow);
		request.setAttribute("workflowTaskId", workflowTaskId);
		request.setAttribute("workflowNodeDefId", workflowNodeDefId);

		UserContext userContext = UserContextHolder.getLocalUserContext()
				.getUserContext();
		String strRoleType = "";
		for (int i = 0; i < userContext.getGrantedRoles().size(); i++) {
			SysRole sysRole = (SysRole) userContext.getGrantedRoles().get(i);

			if ("业务员".indexOf(sysRole.getRoleName()) != -1) {
				strRoleType = "1";
			}
		}
		request.setAttribute("roletype", strRoleType);

		Set<Property> properties = bocollect.getProperties();

		for (Iterator<Property> iter = properties.iterator(); iter.hasNext();) {
			Property property = (Property) iter.next();
			if (!StringUtils.isNullBlank(property.getSearchHelp())) {
				String value = com.infolion.platform.dpframework.util.BeanUtils
						.getPropertyValue(collect, property.getPropertyName());
				String shlpData = this.searchHelpService.getShlpDataByBoName(
						property, value);

				request.setAttribute(getBoName() + property.getPropertyName(),
						shlpData);
			}
		}

		List<BusinessObject> subBos = bocollect
				.getSubBusinessObject("SettleSubject,FundFlow");
		if (null != subBos) {
			// 取得子业务对象
			for (BusinessObject subBo : subBos) {
				request.setAttribute(subBo.getBeanAttribute()
						.getBeanInstanceName()
						+ "Vt", subBo.getViewText());
			}
		}

		return new ModelAndView("xdss3/collect/collectAdd");
	}

	/**
	 * 业务编辑
	 * 
	 * @param request
	 * @param response
	 */
	public ModelAndView _edityw(HttpServletRequest request,
			HttpServletResponse response) {
		Collect collect = new Collect();
		String id = request.getParameter("collectid");
		String workflowTaskId = request.getParameter("workflowTaskId");
		String workflowNodeDefId = request.getParameter("workflowNodeDefId");
		String businessId = request.getParameter("businessId");
		if (null == id)
			id = businessId;

		if (StringUtils.isNullBlank(id)) {
			collect = this.collectService._getForEdit(id);
		} else {
			collect = this.collectService._getForEdit(id);
		}

		BusinessObject bocollect = getBusinessObject();

		boolean haveBindWorkFlow = WorkflowService
				.getHaveBindWorkFlow("000000000258");
		request.setAttribute("businessId", id);
		request.setAttribute("workflowTaskId", workflowTaskId);
		request.setAttribute("workflowNodeDefId", workflowNodeDefId);
		request.setAttribute("haveBindWorkFlow", haveBindWorkFlow);
		request.setAttribute("vt", bocollect.getViewText());

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
		request
				.setAttribute("username", userContext.getSysUser()
						.getUserName());

		request.setAttribute("collect", collect);

		Set<Property> properties = bocollect.getProperties();

		for (Iterator<Property> iter = properties.iterator(); iter.hasNext();) {
			Property property = (Property) iter.next();
			if (!StringUtils.isNullBlank(property.getSearchHelp())) {
				String value = com.infolion.platform.dpframework.util.BeanUtils
						.getPropertyValue(collect, property.getPropertyName());
				String shlpData = this.searchHelpService.getShlpDataByBoName(
						property, value);

				request.setAttribute(getBoName() + property.getPropertyName(),
						shlpData);
			}
		}

		List<BusinessObject> subBos = bocollect
				.getSubBusinessObject("SettleSubject,FundFlow");
		if (null != subBos) {
			// 取得子业务对象
			for (BusinessObject subBo : subBos) {
				request.setAttribute(subBo.getBeanAttribute()
						.getBeanInstanceName()
						+ "Vt", subBo.getViewText());
			}
		}

		return new ModelAndView("xdss3/collect/collectywEdit");
	}

	/**
	 * 编辑
	 * 
	 * @param request
	 * @param response
	 */
	public ModelAndView _edit(HttpServletRequest request,
			HttpServletResponse response) {
		Collect collect = new Collect();
		String id = request.getParameter("collectid");
		String workflowTaskId = request.getParameter("workflowTaskId");
		String workflowNodeDefId = request.getParameter("workflowNodeDefId");
		String businessId = request.getParameter("businessId");
		if (null == id)
			id = businessId;

		if (StringUtils.isNullBlank(id)) {
			collect = this.collectService._getForEdit(id);
		} else {
			collect = this.collectService._getForEdit(id);
		}
		BusinessObject bocollect = getBusinessObject();

		boolean haveBindWorkFlow = WorkflowService
				.getHaveBindWorkFlow("000000000258");
		request.setAttribute("businessId", id);
		request.setAttribute("workflowTaskId", workflowTaskId);
		request.setAttribute("workflowNodeDefId", workflowNodeDefId);
		request.setAttribute("haveBindWorkFlow", haveBindWorkFlow);
		request.setAttribute("vt", bocollect.getViewText());

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
		if (!StringUtils.isNullBlank(collect.getOldcollectitemid())) {
			request.setAttribute("isDeposit", "Y");
			// CollectItem collectItem =
			// this.collectItemService.get(collect.getOldcollectitemid().trim());
			BigDecimal availableDeposit = collectService
					.queryAvailableDepositValue(collect.getOldcollectitemid());
			request.setAttribute("actsuretybond2", availableDeposit.add(collect
					.getApplyamount()));
		}
		request.setAttribute("roletype", strRoleType);
		request
				.setAttribute("username", userContext.getSysUser()
						.getUserName());

		request.setAttribute("collect", collect);

		Set<Property> properties = bocollect.getProperties();

		for (Iterator<Property> iter = properties.iterator(); iter.hasNext();) {
			Property property = (Property) iter.next();
			if (!StringUtils.isNullBlank(property.getSearchHelp())) {
				String value = com.infolion.platform.dpframework.util.BeanUtils
						.getPropertyValue(collect, property.getPropertyName());
				String shlpData = this.searchHelpService.getShlpDataByBoName(
						property, value);

				request.setAttribute(getBoName() + property.getPropertyName(),
						shlpData);
			}
		}

		List<BusinessObject> subBos = bocollect.getSubBusinessObject();
		if (null != subBos) {
			// 取得子业务对象
			for (BusinessObject subBo : subBos) {
				request.setAttribute(subBo.getBeanAttribute()
						.getBeanInstanceName()
						+ "Vt", subBo.getViewText());

				if (subBo.getBoName().equals("settleSubject")
						|| subBo.getBoName().equals("fundFlow")) {
					Set<Property> subproperties = subBo.getProperties();
					for (Iterator<Property> subiter = subproperties.iterator(); subiter
							.hasNext();) {
						Property subproperty = (Property) subiter.next();
						if (!StringUtils.isNullBlank(subproperty
								.getSearchHelp())) {
							String value = "";
							if (subBo.getBoName().equals("settleSubject")) {
								value = com.infolion.platform.dpframework.util.BeanUtils
										.getPropertyValue(collect
												.getSettleSubject(),
												subproperty.getPropertyName());
							}
							if (subBo.getBoName().equals("fundFlow")) {
								value = com.infolion.platform.dpframework.util.BeanUtils
										.getPropertyValue(
												collect.getFundFlow(),
												subproperty.getPropertyName());
							}
							String shlpData = this.searchHelpService
									.getShlpDataByBoName(subproperty, value);

							request.setAttribute(subBo.getBoName()
									+ subproperty.getPropertyName(), shlpData);
						}
					}
				}
			}
		}

		return new ModelAndView("xdss3/collect/collectEdit");
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
	 * 初始化收款清票数据
	 * 
	 * @param request
	 * @param response
	 */
	public void getCollectCbillData(HttpServletRequest request,
			HttpServletResponse response) {
		String contractnos = request.getParameter("contractnos");
		String projectnos = request.getParameter("projectnos");
		String customertitleids = request.getParameter("customertitleids");

		List customerTitleList = new ArrayList();
		List billCbillList = new ArrayList();

		if (!("").equals(contractnos) && contractnos != null) {
			customerTitleList = this.unclearCustomerService
					.getUnclearInvoiceListByContract(contractnos);
		}

		if (!("").equals(projectnos) && projectnos != null) {
			customerTitleList = this.unclearCustomerService
					.getUnclearInvoiceListByProject(projectnos);
		}

		if (!("").equals(customertitleids) && customertitleids != null) {
			customerTitleList = this.unclearCustomerService
					.getUnclearCustomerInvoiceList(customertitleids);
		}

		for (int i = 0; i < customerTitleList.size(); i++) {
			CustomerTitle customerTitle = (CustomerTitle) customerTitleList
					.get(i);

			CollectCbill collectCbill = new CollectCbill();
			collectCbill.setContract_no(customerTitle.getIhrez());
			collectCbill.setProject_no(customerTitle.getBname());
			collectCbill.setVoucherno(customerTitle.getBelnr());
			collectCbill.setAccountdate(customerTitle.getBudat());
			collectCbill.setBillno(customerTitle.getInvoice());
			collectCbill.setText(customerTitle.getBktxt());
			collectCbill.setOld_contract_no(this.collectCbillJdbcDao
					.getOldContractNo(customerTitle.getIhrez()));
			collectCbill.setSap_order_no(this.collectCbillJdbcDao
					.getSapOrderNo(customerTitle.getIhrez()));
			collectCbill.setCurrency(customerTitle.getWaers());

			// 应收款
			BigDecimal billamount = customerTitle.getDmbtr();
			collectCbill.setReceivableamount(billamount);
			// 已收款
			BigDecimal receivedamount = this.collectService.getSumClearAmount(
					customerTitle.getInvoice(),
					BusinessState.ALL_BILLCLEAR_PAIDUP,
					BusinessState.ALL_BILLCLEAR_PAIDUP,
					BusinessState.ALL_COLLECT_PAIDUP);
			collectCbill.setReceivedamount(receivedamount);
			// 未收款
			collectCbill.setUnreceivedamount(billamount
					.subtract(receivedamount));
			// 在批金额
			BigDecimal onroadamount = this.collectService.getSumClearAmount(
					customerTitle.getInvoice(),
					BusinessState.ALL_BILLCLEAR_ONROAD,
					BusinessState.ALL_BILLCLEAR_ONROAD,
					BusinessState.ALL_COLLECT_ONROAD);
			collectCbill.setOnroadamount(onroadamount);
			// 清帐金额
			BigDecimal billreaminamount = billamount.subtract(receivedamount)
					.subtract(onroadamount);
			collectCbill.setClearamount(BigDecimal.valueOf(0));

			// 清帐金额大于0时才返回本条数据
			if (billreaminamount.compareTo(BigDecimal.valueOf(0)) == 1) {
				billCbillList.add(collectCbill);
			}
		}

		MultyGridData gridJsonData = new MultyGridData();
		gridJsonData.setData(billCbillList.toArray());
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
	 * 自动分配收款清票数据
	 * 
	 * @param request
	 * @param response
	 */
	public void assignCollectCbillData(HttpServletRequest request,
			HttpServletResponse response) {

		JSONObject jo = new JSONObject();
		// 绑定主对象值
		Collect collect = (Collect) ExBeanUtils.bindBusinessObjectData(request
				.getParameterMap(), Collect.class, true, request.getMethod(),
				true);
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<CollectItem> collectitemmodifyItems = ExBeanUtils
				.bindModifySubBoData(request.getParameterMap(),
						new Object[] { collect }, CollectItem.class, null);

		List billCbillList = new ArrayList();
		List customerTitleList = new ArrayList();

		for (Iterator<CollectItem> iter = collectitemmodifyItems.iterator(); iter
				.hasNext();) {
			CollectItem collectItem = (CollectItem) iter.next();

			BigDecimal assignamount = collectItem.getAssignamount();

			if (!("").equals(collectItem.getContract_no())
					&& collectItem.getContract_no() != null) {
				customerTitleList = this.unclearCustomerService
						.getUnclearInvoiceListByContract(collect.getCustomer(),
								collectItem.getContract_no());
			} else if (!("").equals(collectItem.getProject_no())
					&& collectItem.getProject_no() != null) {
				customerTitleList = this.unclearCustomerService
						.getUnclearInvoiceListByProject(collect.getCustomer(),
								collectItem.getProject_no());
			}

			for (int i = 0; i < customerTitleList.size(); i++) {
				if (assignamount.compareTo(BigDecimal.valueOf(0)) == 0)
					continue;

				CustomerTitle customerTitle = (CustomerTitle) customerTitleList
						.get(i);

				CollectCbill collectCbill = new CollectCbill();
				collectCbill.setContract_no(customerTitle.getIhrez());
				collectCbill.setProject_no(customerTitle.getBname());
				collectCbill.setVoucherno(customerTitle.getBelnr());
				collectCbill.setAccountdate(customerTitle.getBudat());
				collectCbill.setBillno(customerTitle.getInvoice());
				collectCbill.setText(customerTitle.getBktxt());
				collectCbill.setOld_contract_no(this.collectCbillJdbcDao
						.getOldContractNo(customerTitle.getIhrez()));
				collectCbill.setSap_order_no(this.collectCbillJdbcDao
						.getSapOrderNo(customerTitle.getIhrez()));
				collectCbill.setCurrency(customerTitle.getWaers());

				// 应收款
				BigDecimal billamount = customerTitle.getDmbtr();
				collectCbill.setReceivableamount(billamount);
				// 已收款
				BigDecimal receivedamount = this.collectService
						.getSumClearAmount(customerTitle.getInvoice(),
								BusinessState.ALL_BILLCLEAR_PAIDUP,
								BusinessState.ALL_BILLCLEAR_PAIDUP,
								BusinessState.ALL_COLLECT_PAIDUP);
				collectCbill.setReceivedamount(receivedamount);
				// 未收款
				collectCbill.setUnreceivedamount(billamount
						.subtract(receivedamount));
				// 在批金额
				BigDecimal onroadamount = this.collectService
						.getSumClearAmount(customerTitle.getInvoice(),
								BusinessState.ALL_BILLCLEAR_ONROAD,
								BusinessState.ALL_BILLCLEAR_ONROAD,
								BusinessState.ALL_COLLECT_ONROAD);
				collectCbill.setOnroadamount(onroadamount);
				// 清帐金额
				BigDecimal billreaminamount = billamount.subtract(
						receivedamount).subtract(onroadamount);
				collectCbill.setClearamount(BigDecimal.valueOf(0));

				// 清帐金额大于0时才返回本条数据xx
				if (billreaminamount.compareTo(BigDecimal.valueOf(0)) == 1) {
					// assignamount = assignamount.subtract(billreaminamount);
					// collectCbill.setClearamount(billreaminamount);
					billCbillList.add(collectCbill);
				}
				// else
				// {
				// collectCbill.setClearamount(assignamount);
				// billCbillList.add(collectCbill);
				// break;
				// }
				// billCbillList.add(collectCbill);
			}
		}
		Collections.sort(billCbillList, new DateComparator());
		MultyGridData gridJsonData = new MultyGridData();
		gridJsonData.setData(billCbillList.toArray());
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
	 * @创建作者 钟志华
	 * @创建日期 2011-2-17 日期比较器
	 * 
	 */
	static class DateComparator implements Comparator {
		public int compare(Object object1, Object object2) {// 实现接口中的方法
			CollectCbill p1 = (CollectCbill) object1; // 强制转换
			CollectCbill p2 = (CollectCbill) object2;
			int d = DateUtils.getIntervalDays(p1.getAccountdate(), p2
					.getAccountdate());
			return new Double("0").compareTo(new Double(d));
		}
	}

	/**
	 * @创建作者：邱杰烜
	 * @创建日期：2010-09-17 根据收款金额分配的行项目来判断该项目是否已经授信，若已经授信则反回授信类型
	 * @返回结果： 0 - 无授信 1 - 客户放货 2 - 客户代垫 3 - 客户放货+代垫 4 - 供应商授信
	 */
	public void checkProjCreditType(HttpServletRequest request,
			HttpServletResponse response) {
		JSONObject jsonObj = new JSONObject();
		// String boId = request.getParameter("boid");
		String projectNos = request.getParameter("projectnos");
		String creditTypes = this.collectService
				.checkProjCreditType(projectNos);
		jsonObj.put("credittypes", creditTypes);
		this.operateSuccessfullyWithString(response, jsonObj.toString());
	}

	/**
	 * 凭证预览
	 * 
	 * @param request
	 * @param response
	 */
	public void voucherPreview(HttpServletRequest request,
			HttpServletResponse response) {
		JSONObject jo = new JSONObject();
		// 绑定主对象值
		Collect collect = (Collect) ExBeanUtils.bindBusinessObjectData(request
				.getParameterMap(), Collect.class, true, request.getMethod(),
				true);
		// 执行凭证预览操作前先调用以下这个存储过程
		// this.collectService.updateCustomerTitle(collect.getCustomer());
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<CollectItem> collectitemmodifyItems = ExBeanUtils
				.bindModifySubBoData(request.getParameterMap(),
						new Object[] { collect }, CollectItem.class, null);
		collect.setCollectitem(collectitemmodifyItems);
		Set<CollectItem> collectitemdeleteItems = ExBeanUtils
				.bindDeleteSubBoData(request.getParameterMap(),
						new Object[] { collect }, CollectItem.class, null);
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<CollectCbill> collectcbillmodifyItems = ExBeanUtils
				.bindModifySubBoData(request.getParameterMap(),
						new Object[] { collect }, CollectCbill.class, null);
		collect.setCollectcbill(collectcbillmodifyItems);
		Set<CollectCbill> collectcbilldeleteItems = ExBeanUtils
				.bindDeleteSubBoData(request.getParameterMap(),
						new Object[] { collect }, CollectCbill.class, null);
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<CollectRelated> collectrelatedmodifyItems = ExBeanUtils
				.bindModifySubBoData(request.getParameterMap(),
						new Object[] { collect }, CollectRelated.class, null);
		collect.setCollectrelated(collectrelatedmodifyItems);
		Set<CollectRelated> collectrelateddeleteItems = ExBeanUtils
				.bindDeleteSubBoData(request.getParameterMap(),
						new Object[] { collect }, CollectRelated.class, null);
		// 绑定子对象(一对一关系)
		SettleSubject settleSubject = (SettleSubject) ExBeanUtils
				.bindBusinessObjectData(request.getParameterMap(),
						SettleSubject.class, false, request.getMethod(), true);
		if (settleSubject != null) {
			if (null == settleSubject.getFlag()
					|| "".equals(settleSubject.getFlag().trim())) {
				settleSubject.setFlag("0");
			}
			collect.setSettleSubject(settleSubject);
			settleSubject.setCollect(collect);
		}
		// 绑定子对象(一对一关系)
		FundFlow fundFlow = (FundFlow) ExBeanUtils.bindBusinessObjectData(
				request.getParameterMap(), FundFlow.class, false, request
						.getMethod(), true);
		if (fundFlow != null) {
			if (null == fundFlow.getFlag()
					|| "".equals(fundFlow.getFlag().trim())) {
				fundFlow.setFlag("0");
			}
			collect.setFundFlow(fundFlow);
			fundFlow.setCollect(collect);
		}
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<CollectBankItem> collectbankitemmodifyItems = ExBeanUtils
				.bindModifySubBoData(request.getParameterMap(),
						new Object[] { collect }, CollectBankItem.class, null);
		collect.setCollectbankitem(collectbankitemmodifyItems);
		Set<CollectBankItem> collectbankitemdeleteItems = ExBeanUtils
				.bindDeleteSubBoData(request.getParameterMap(),
						new Object[] { collect }, CollectBankItem.class, null);
		
//		检查单据号码是否有重复，有重复不能提交
		if(StringUtils.isNotBlank(collect.getDraft())){
			Collect co =this.collectService.getCollectBydraft(collect.getDraft().trim(), collect.getCollectid());
			if(null != co){
				throw new BusinessException("单据号码已经存在！请重新填写，可在号码后加A来区别以前的号码！");
			}
		}
		
		this.collectService._saveOrUpdate(collect, collectitemdeleteItems,
				collectcbilldeleteItems, collectrelateddeleteItems,
				collectbankitemdeleteItems, getBusinessObject());

		Collect _collect = this.collectService._get(collect.getCollectid());
		// 更新状态
		// this.collectService.updateCustomerTitle(collect.getCustomer());
		//取得公司代码
		String companyCode = this.sysDeptJdbcDao.getCompanyCode(_collect.getDept_id());
		
		this.voucherService.deleteVoucherByBusinessid(collect.getCollectid());
		String type = request.getParameter("type");
		if (type.equals("1")) {
			// 生成收款凭证
			if (_collect.getCurrency().equals("CNY") || ("2600".equals(companyCode) && _collect.getCurrency().equals("HKD")) || ("3000".equals(companyCode) && _collect.getCurrency().equals("SGD")) 
					|| ("3100".equals(companyCode) && _collect.getCurrency().equals("USD")) || ("3010".equals(companyCode) && _collect.getCurrency().equals("USD"))) {
				// 人民币
				this.collectService.genCnyVoucher(_collect);
			} else {
				//香港公司,新加坡
				if("2600".equals(companyCode) || "3000".equals(companyCode) || "3100".equals(companyCode) || "3010".equals(companyCode)){
					if (_collect.getBillcurrency().equals(_collect.getActcurrency())) {
						// 币别一致
						this.collectService.genSameUnCnyVoucher(_collect);
					}else{
						// 币别不一致
						this.collectService.genDiffUnCnyVoucher(_collect);
					}
				}else{
					//不是香港公司，新加坡
					if (_collect.getBillcurrency().equals(_collect.getActcurrency())) {
						// 国外的并且不是信达诺
						if (!_collect.getCustomer().startsWith("005") && !_collect.getCustomer().equals("0010000006")&& !_collect.getCustomer().equals("0010000057")) {
							// 纯代理
							this.collectService.representCollectVoucher(_collect);
						} else {
							// 币别一致
							this.collectService.genSameUnCnyVoucher(_collect);
						}
					} else {
						// 币别不一致
						this.collectService.genDiffUnCnyVoucher(_collect);
					}
				}
			}
			// 判断是否需要删除
			this.voucherService.judgeVoucherNeedDel(_collect.getCollectid());
			// 生成清帐凭证
//			this.collectService.genClearVoucher(_collect);
		}

		if (type.equals("2")) {
			// 生成票据凭证
			this.collectService.genBillVoucher(_collect);
			// 判断是否需要删除
			this.voucherService.judgeVoucherNeedDel(_collect.getCollectid());
			// 生成清帐凭证
//			this.collectService.genClearVoucher(_collect);
		}

		if (type.equals("3")) {
			// 生成票据收款凭证
			Voucher voucher = this.collectService.genBillClearVoucher(_collect);
			// 判断是否需要删除
			this.voucherService.judgeVoucherNeedDel(_collect.getCollectid());
			// 生成票据收款、清帐凭证
			this.collectService.getBillClearVoucher(collect, "6", voucher, "N");
		}

		if (type.equals("4")) {
			// 生成未明户收款及清帐凭证
			if (_collect.getBillcurrency().equals(_collect.getCurrency())) {
				this.collectService.genUnnamerVoucher(_collect);
			} else {
				this.collectService.genDiffUnnamerVoucher(_collect);
			}
			// 判断是否需要删除
			this.voucherService.judgeVoucherNeedDel(_collect.getCollectid());
			// 生成客户清帐凭证
//			this.collectService.genClearVoucher(_collect);
		}

		if (type.equals("5")) {
			// 保证金转货款
			this.collectService.genDepositVoucher(_collect);
			// 生成保证金转货款清帐凭证
			// this.collectService.genDepositClearVoucher(_collect);
		}

		if (type.equals("6")) {
			// 财务会计审核贴现或议付并做帐
			//贴现 银行承兑汇票  议付 国内信用证
			this.collectService.genDiscountVoucher(_collect);
			//累计贴现金额 等于 收款金额清帐
			if(_collect.getToldisnegamount().compareTo(_collect.getApplyamount())==0){
		        // 判断是否需要删除
			    // 邱杰烜 2011-05-30 将通用的判断凭证是否重复生成的逻辑换成只针对贴现/议付凭证判断的逻辑
	            this.voucherService.judgeDisNegVoucherNeedDel(_collect.getCollectid());
				this.collectService.genDiscountClearVoucher(_collect);
			}
		}
		// 判断是否需要删除
		// 邱杰烜 2011-05-30 加入type为6的区分判断
		if(type.equals("6")){
		    this.voucherService.judgeDisNegVoucherNeedDel(_collect.getCollectid());
		}else{
		    this.voucherService.judgeVoucherNeedDel(_collect.getCollectid());
		}

//		生成清账凭证，zzh
		if ("1".equals(type) || "2".equals(type) || "4".equals(type)) {
//			如果有清票时
			if(null !=_collect.getCollectcbill() && _collect.getCollectcbill().size()!=0){		
				ParameterVoucherObject parameterVoucher =customerClearAccountService.setParameter(_collect);
				BigDecimal marginAmount = new BigDecimal("0");
				Voucher settleSubjectVoucher = null;
				
//				if (settleSubject != null || fundFlow != null){			
//					settleSubjectVoucher = customerClearAccountService.saveSettleSubjectVoucher(parameterVoucher);				
//					marginAmount = customerClearAccountService.getMargin(settleSubjectVoucher.getVoucherItem());
//					parameterVoucher.setVoucherid(settleSubjectVoucher.getVoucherid());
//				}
				//判断是否需要删除
				this.voucherService.judgeVoucherNeedDel(_collect.getCollectid());
				boolean isSave = false;
				if(!StringUtils.isNullBlank(_collect.getCollectid()))isSave=true;
				IInfo info =customerClearAccountService.checkClearData(_collect, marginAmount,isSave);
//				判断本次数据是否正确
				if(info.isRight()){
					IInfoVoucher infoVoucher = customerClearAccountService.isClearAccount(_collect, marginAmount);
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
						boolean isp = customerClearAccountService.isProfitAndLoss(parameterVoucher.getCurrencytext(), parameterVoucher.getBukrs());
//						可以全清出清账凭证
						if(infoVoucher.isClear()){
							Voucher clearVoucher =customerClearAccountService.saveClearVoucherByCustomer(parameterVoucher,infoVoucher,_collect.getCollectid(),ClearConstant.COLLECT_TYPE,isp);
//							数据有错误
							if(null == clearVoucher){
								jo.put("isRight", false);
								jo.put("info", info.CODE_9);
								this.operateSuccessfullyHiddenInfoWithString(response, jo.toString());
							}
						}else{
//						部分清（有外币出汇损）,并且差额不为0
							if(isp && parameterVoucher.getSubtractVlaue().compareTo(new BigDecimal("0"))!= 0){									
								Voucher plVoucher = customerClearAccountService.saveProfitAndLossVoucher(parameterVoucher);
							}
							
						}
//						保存本次全清的数据，用来更新isclear状态
						request.getSession().setAttribute(_collect.getCollectid(), infoVoucher);
						//判断是否需要删除
						this.voucherService.judgeVoucherNeedDel(_collect.getCollectid());						
						try {
							response.setContentType("text/html;charset=UTF-8");
							response.getWriter().print(_collect.getCollectid());
							System.out.println(_collect.getCollectid());
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
		}
		try {
			response.setContentType("text/html;charset=UTF-8");
			response.getWriter().print(_collect.getCollectid());
			System.out.println(_collect.getCollectid());
		} catch (IOException e) {
			logger.error("输出json失败," + e.getMessage(), e.getCause());
		}
	}

	public void cashJournal(HttpServletRequest request,
			HttpServletResponse response) {
		JSONObject jo = new JSONObject();
		// 绑定主对象值
		Collect collect = (Collect) ExBeanUtils.bindBusinessObjectData(request
				.getParameterMap(), Collect.class, true, request.getMethod(),
				true);
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<CollectItem> collectitemmodifyItems = ExBeanUtils
				.bindModifySubBoData(request.getParameterMap(),
						new Object[] { collect }, CollectItem.class, null);
		collect.setCollectitem(collectitemmodifyItems);
		Set<CollectItem> collectitemdeleteItems = ExBeanUtils
				.bindDeleteSubBoData(request.getParameterMap(),
						new Object[] { collect }, CollectItem.class, null);
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<CollectCbill> collectcbillmodifyItems = ExBeanUtils
				.bindModifySubBoData(request.getParameterMap(),
						new Object[] { collect }, CollectCbill.class, null);
		collect.setCollectcbill(collectcbillmodifyItems);
		Set<CollectCbill> collectcbilldeleteItems = ExBeanUtils
				.bindDeleteSubBoData(request.getParameterMap(),
						new Object[] { collect }, CollectCbill.class, null);
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<CollectRelated> collectrelatedmodifyItems = ExBeanUtils
				.bindModifySubBoData(request.getParameterMap(),
						new Object[] { collect }, CollectRelated.class, null);
		collect.setCollectrelated(collectrelatedmodifyItems);
		Set<CollectRelated> collectrelateddeleteItems = ExBeanUtils
				.bindDeleteSubBoData(request.getParameterMap(),
						new Object[] { collect }, CollectRelated.class, null);
		// 绑定子对象(一对一关系)
		SettleSubject settleSubject = (SettleSubject) ExBeanUtils
				.bindBusinessObjectData(request.getParameterMap(),
						SettleSubject.class, false, request.getMethod(), true);
		if (settleSubject != null) {
			collect.setSettleSubject(settleSubject);
			settleSubject.setCollect(collect);
		}
		// 绑定子对象(一对一关系)
		FundFlow fundFlow = (FundFlow) ExBeanUtils.bindBusinessObjectData(
				request.getParameterMap(), FundFlow.class, false, request
						.getMethod(), true);
		if (fundFlow != null) {
			collect.setFundFlow(fundFlow);
			fundFlow.setCollect(collect);
		}
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<CollectBankItem> collectbankitemmodifyItems = ExBeanUtils
				.bindModifySubBoData(request.getParameterMap(),
						new Object[] { collect }, CollectBankItem.class, null);
		collect.setCollectbankitem(collectbankitemmodifyItems);
		Set<CollectBankItem> collectbankitemdeleteItems = ExBeanUtils
				.bindDeleteSubBoData(request.getParameterMap(),
						new Object[] { collect }, CollectBankItem.class, null);

		this.collectService._saveOrUpdate(collect, collectitemdeleteItems,
				collectcbilldeleteItems, collectrelateddeleteItems,
				collectbankitemdeleteItems, getBusinessObject());
		this.operateSuccessfullyHiddenInfo(response);
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
		Reassign reassign = this.reassignService._get(businessId);
		String id = this.reassignJdbcDao.getBoidByReassignid(businessId);

		/**
		 * 判断是否已经拷贝过，
		 */
		Collect collect;
		String boid = this.reassignJdbcDao.isCopyed(id,
				ReassignConstant.COLLECT);
		if (boid != null) {
			collect = this.collectService.getCollectById(boid);
		} else {
			// yanghancai 2010-10-18
			// collect = this.collectService._getEntityCopy(id);
			collect = this.collectService._get(id);
			request.setAttribute("isCreateCopy", "true");
			collect.setOldcollectid(id); // 设置被重分配收款ID
			collect.setOldcollectno(collect.getCollectno());
			collect.setCollectno(null);
			collect.setBusinessstate(BusinessState.REASSIGNED); // 设置业务状态为重分配
			collect.setText(reassign.getText()); // 设置文本为重分配提交时填写文本

			/**
			 * 重新计算金额
			 */
			Set<CollectCbill> newCollectCbillSet = null;
			Set<CollectCbill> collectCbillSet = collect.getCollectcbill();
			if (collectCbillSet != null) {
				newCollectCbillSet = new HashSet<CollectCbill>();
				Iterator<CollectCbill> it = newCollectCbillSet.iterator();
				while (it.hasNext()) {
					CollectCbill collectCbill = it.next();

					// 发票已经审批完的
					BigDecimal receivedamount = this.collectCbillService
							.getSumClearedAmount(collectCbill.getBillno());
					collectCbill.setReceivedamount(receivedamount);
					collectCbill.setUnreceivedamount(collectCbill
							.getReceivableamount().subtract(receivedamount));

					// 发票在途的
					BigDecimal onroadamount = this.collectCbillService
							.getSumOnroadAmount(collectCbill.getBillno());
					collectCbill.setOnroadamount(onroadamount);

					newCollectCbillSet.add(collectCbill);
				}
			}
			collect.setCollectcbill(newCollectCbillSet);

		}

		request.setAttribute("collect", collect);
		request.setAttribute("vt", getBusinessObject().getViewText());
		if (null != getBusinessObject().getSubBusinessObject()) {
			// 取得子业务对象
			for (BusinessObject bo : getBusinessObject().getSubBusinessObject()) {
				request.setAttribute(bo.getBeanAttribute()
						.getBeanInstanceName()
						+ "Vt", bo.getViewText());
			}
		}
		boolean haveBindWorkFlow = WorkflowService
				.getHaveBindWorkFlow("000000000295");
		request.setAttribute("haveBindWorkFlow", haveBindWorkFlow);
		request.setAttribute("workflowTaskId", workflowTaskId);
		request.setAttribute("workflowNodeDefId", workflowNodeDefId);
		return new ModelAndView("xdss3/collect/collectAdd");
	}

	/**
	 * 重分配查看
	 * 
	 * @param request
	 * @param response
	 */
	public ModelAndView _viewForReassign(HttpServletRequest request,
			HttpServletResponse response) {

		Collect collect = new Collect();
		String workflowTaskId = request.getParameter("workflowTaskId");
		String workflowNodeDefId = request.getParameter("workflowNodeDefId");
		String businessId = request.getParameter("businessId");
		String oldId = this.reassignJdbcDao.getBoidByReassignid(businessId);
		String id = "";
		Reassign reassign = this.reassignService._get(businessId);
		// 若为"冲销（财务部冲销并作废）"，则直接使用原收款单的ID
		if (reassign.getReassigntmethod().equals(ReassignConstant.FI_CLEAR)) {
			id = oldId;
		} else {
			id = this.reassignJdbcDao.getNewNoIdByOldNoId(
					ReassignConstant.COLLECT, oldId);
		}

		if (null == id)
			id = businessId;

		if (StringUtils.isNullBlank(id)) {
			collect = this.collectService._getForEdit(id);
		} else {
			collect = this.collectService._getForEdit(id);
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
		request.setAttribute("collect", collect);
		return new ModelAndView("xdss3/collect/collectView");
	}

	/**
	 * 重分配编辑
	 */
	public ModelAndView _editForReassign(HttpServletRequest request,
			HttpServletResponse response) {
		Collect collect = new Collect();
		String workflowTaskId = request.getParameter("workflowTaskId");
		String workflowNodeDefId = request.getParameter("workflowNodeDefId");
		String businessId = request.getParameter("businessId");
		String oldId = this.reassignJdbcDao.getBoidByReassignid(businessId);
		String id = "";
		Reassign reassign = this.reassignService._get(businessId);
		if (reassign.getReassigntmethod().equals(ReassignConstant.FI_CLEAR)) {
			id = oldId;
		} else {
			id = this.reassignJdbcDao.getNewNoIdByOldNoId(
					ReassignConstant.COLLECT, oldId);
		}

		if (null == id)
			id = businessId;

		if (StringUtils.isNullBlank(id)) {
			collect = this.collectService._getForEdit(id);
		} else {
			collect = this.collectService._getForEdit(id);
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
		UserContext userContext = UserContextHolder.getLocalUserContext()
				.getUserContext();
		Properties prop = this.getProperties("config/config.properties");
		String xjrj = prop.getProperty("xjrj");
		if (xjrj != null && !"".equals(xjrj)) {
			request.setAttribute("xjrj", xjrj);
		}

		request
				.setAttribute("username", userContext.getSysUser()
						.getUserName());
		request.setAttribute("isReassign", "Y");
		request.setAttribute("collect", collect);
		return new ModelAndView("xdss3/collect/collectEdit");
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
		Collect collect = (Collect) ExBeanUtils.bindBusinessObjectData(request
				.getParameterMap(), Collect.class, true, request.getMethod(),
				true);
		// 类型标识是从那个页面提交，view 表示从view页面提交流程。
		String type = request.getParameter("type");
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<CollectItem> collectitemmodifyItems = ExBeanUtils
				.bindModifySubBoData(request.getParameterMap(),
						new Object[] { collect }, CollectItem.class, null);
		Set<CollectItem> deletedCollectItemSet = ExBeanUtils
				.bindDeleteSubBoData(request.getParameterMap(),
						new Object[] { collect }, CollectItem.class, null);
		collect.setCollectitem(collectitemmodifyItems);
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<CollectCbill> collectcbillmodifyItems = ExBeanUtils
				.bindModifySubBoData(request.getParameterMap(),
						new Object[] { collect }, CollectCbill.class, null);
		Set<CollectCbill> deletedCollectCbillSet = ExBeanUtils
				.bindDeleteSubBoData(request.getParameterMap(),
						new Object[] { collect }, CollectCbill.class, null);
		collect.setCollectcbill(collectcbillmodifyItems);
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<CollectRelated> collectrelatedmodifyItems = ExBeanUtils
				.bindModifySubBoData(request.getParameterMap(),
						new Object[] { collect }, CollectRelated.class, null);
		Set<CollectRelated> deletedCollectRelatedSet = ExBeanUtils
				.bindDeleteSubBoData(request.getParameterMap(),
						new Object[] { collect }, CollectRelated.class, null);
		collect.setCollectrelated(collectrelatedmodifyItems);
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<CollectBankItem> collectbankitemmodifyItems = ExBeanUtils
				.bindModifySubBoData(request.getParameterMap(),
						new Object[] { collect }, CollectBankItem.class, null);
		Set<CollectBankItem> deletedCollectBankItemSet = ExBeanUtils
				.bindDeleteSubBoData(request.getParameterMap(),
						new Object[] { collect }, CollectBankItem.class, null);
		collect.setCollectbankitem(collectbankitemmodifyItems);
		// 绑定子对象(一对一关系)
		SettleSubject settleSubject = (SettleSubject) ExBeanUtils
				.bindBusinessObjectData(request.getParameterMap(),
						SettleSubject.class, false, request.getMethod(), true);

		if (settleSubject != null) {
			collect.setSettleSubject(settleSubject);
			settleSubject.setCollect(collect);
		}
		// 绑定子对象(一对一关系)
		FundFlow fundFlow = (FundFlow) ExBeanUtils.bindBusinessObjectData(
				request.getParameterMap(), FundFlow.class, false, request
						.getMethod(), true);
		if (fundFlow != null) {
			collect.setFundFlow(fundFlow);
			fundFlow.setCollect(collect);
		}

		if (!"view".equalsIgnoreCase(type)) {
		    collect.setBusinessstate("2");
		}
		String reassignId = this.reassignJdbcDao.getReassignidByBoId(collect
				.getOldcollectid());
		Reassign reassign = this.reassignService.getReassignById(reassignId);
		reassign.setWorkflowTaskId(workflowTaskId);
		reassign.setWorkflowLeaveTransitionName(collect
				.getWorkflowLeaveTransitionName());
		reassign.setWorkflowExamine(collect.getWorkflowExamine());
		reassign.setWorkflowUserDefineProcessVariable(collect
				.getWorkflowUserDefineProcessVariable());
		reassign.setWorkflowBusinessNote(collect.getWorkflowBusinessNote());
		reassign.setWorkflowUserDefineTaskVariable(collect
				.getWorkflowUserDefineTaskVariable());

		/**
		 * 判断重分配方式是否为： 重置（到业务部门重新分配）或 重置（财务部直接解除分配关系）需要拷贝凭证
		 */
		if (reassign.getProcessstate() != null
				&& !reassign.getProcessstate().equals("财务部审核")
				&& (reassign.getReassigntmethod().equals(
						ReassignConstant.RESET_TO_BS) || reassign
						.getReassigntmethod().equals(
								ReassignConstant.RESET_TO_FI))) {
			this.reassignService.copyVoucher(reassign.getReassignboid(),
					collect.getCollectid());
		}
		//审核通过
		if(reassign.getProcessstate() != null
				&& reassign.getProcessstate().equals("财务部审核") && reassign.getWorkflowLeaveTransitionName().equals("确认")){
			 collect.setBusinessstate("3");			
		}
		if(reassign.getProcessstate() != null
				&& reassign.getProcessstate().equals("财务部审核") && reassign.getWorkflowLeaveTransitionName().equals("作废")){
			 collect.setBusinessstate("-1");			
		}
		// 设置审核状态
		if (collect.getWorkflowLeaveTransitionName().equals("作废")) {
			this.reassignService.updateReassignState(reassignId,
					BusinessState.SUBMITNOTPASS);			
			   
		}
		
		if (!"view".equalsIgnoreCase(type)) {
		    
			this.collectService._saveOrUpdate(collect, deletedCollectItemSet,
					deletedCollectCbillSet, deletedCollectRelatedSet,
					deletedCollectBankItemSet, getBusinessObject());
		}
		BusinessObject businessObject = BusinessObjectService
				.getBusinessObject("Reassign");
		this.reassignService._submitProcess(reassign, businessObject);
		this.operateSuccessfully(response);
	}

	/**
	 * 保证金转货款管理
	 */
	public ModelAndView _manageDeposit1(HttpServletRequest request,
			HttpServletResponse response) {
		// request.setAttribute("vt", getBusinessObject().getViewText());
		String strDataAuthSql = CommonDataAuth.getDataAuthSql(this
				.getBusinessObject());
		request.setAttribute("dataAuthSql", strDataAuthSql);
		return new ModelAndView("xdss3/collect/collectDepositManage");
	}

	/**
	 * 收款综合查询 yanghancai 2010-10-09 新建
	 * 
	 * @param request
	 * @param response
	 */
	public ModelAndView _collectQuery(HttpServletRequest request,
			HttpServletResponse response) {
		request.setAttribute("vt", getBusinessObject().getViewText());
		/** ******************************************************************** */
		String strDataAuthSql = CommonDataAuth.getDataAuthSql(this
				.getBusinessObject());
		request.setAttribute("dataAuthSql", strDataAuthSql);
		/** ******************************************************************** */
		return new ModelAndView("xdss3/collect/collectQuery");
	}

	/**
	 * 保证金转货款创建
	 * 
	 * @param request
	 * @param response
	 */
	public ModelAndView _createDeposit(HttpServletRequest request,
			HttpServletResponse response) {
		String collectId = request.getParameter("collectId");
		String collectItemId = request.getParameter("collectItemId");
		String actsuretybond = request.getParameter("actsuretybond");
		Collect col = collectService._get(collectId);
		// 执行保证金转货款时，先执行以下这个存储过程
		// this.collectService.updateCustomerTitle(col.getCustomer());
		CollectItem item = collectItemService._get(collectItemId);
		Collect collect = new Collect();
		BeanUtils.copyProperties(col, collect);
		collect.setBusinessstate(null);
		collect.setProcessstate(null);
		collect.setCollectid(null);
		collect.setCollectno(null);
		collect.setIncsuretybond("N");// 是否包含保证金，改成N
		collect.setOldcollectid(col.getCollectid());
		collect.setOldcollectitemid(item.getCollectitemid());
		collect.setOldcollectno(col.getCollectno());
		collect.setOldproject_no(item.getProject_no());
		collect.setOldcontract_no(item.getContract_no());
		// collect.setApplyamount(item.getActsuretybond());
		// collect.setConvertamount(item.getActsuretybond());
		collect.setApplyamount(new BigDecimal(actsuretybond));
		collect.setConvertamount(new BigDecimal(actsuretybond));
		collect.setVoucherdate(null);// 凭证日期
		collect.setAccountdate(null);// 记账日期
		collect.setSettleSubject(null);// 结算科目
		collect.setFundFlow(null);// 存资金往来
		collect.setDept_id(UserContextHolder.getLocalUserContext()
				.getUserContext().getSysDept().getDeptid());
		collect.setCollectcbill(null);
		collect.setCollectrelated(null);
		collect.setSettleSubject(null);
		collect.setFundFlow(null);
		collect.setCollectbankitem(null);
		collect.setGoodsamount(BigDecimal.ZERO);
		collect.setReplaceamount(BigDecimal.ZERO);
		collect.setSupplieramount(BigDecimal.ZERO);
		collect.setText("");
		/*
		 * collect.setCreatetime(""); collect.setCreator("");
		 * collect.setLastmodifyer(""); collect.setLastmodifytime("");
		 */
		Set<CollectItem> itSet = new HashSet<CollectItem>();
		CollectItem newItem = new CollectItem();
		BeanUtils.copyProperties(item, newItem);
		newItem.setCollectitemid(null);
		newItem.setCollect(collect);
		newItem.setAssignamount(new BigDecimal(actsuretybond));
		newItem.setSuretybond(BigDecimal.ZERO);
		newItem.setPrebillamount(BigDecimal.ZERO);
		newItem.setActsuretybond(BigDecimal.ZERO);
		newItem.setAssignamount2(BigDecimal.ZERO);
		itSet.add(newItem);
		collect.setCollectitem(itSet);
		collectService._saveOrUpdate(collect, null, null, null, null,
				getBusinessObject());

		String id = collect.getCollectid();
		String workflowTaskId = request.getParameter("workflowTaskId");
		String workflowNodeDefId = request.getParameter("workflowNodeDefId");

		boolean haveBindWorkFlow = WorkflowService
				.getHaveBindWorkFlow("000000000258");

		request.setAttribute("actsuretybond2", actsuretybond);
		request.setAttribute("businessId", id);
		request.setAttribute("workflowTaskId", workflowTaskId);
		request.setAttribute("workflowNodeDefId", workflowNodeDefId);
		request.setAttribute("haveBindWorkFlow", haveBindWorkFlow);
		request.setAttribute("vt", getBusinessObject().getViewText());
		if (null != getBusinessObject().getSubBusinessObject()) {
			// 取得子业务对象
			for (BusinessObject bo : getBusinessObject().getSubBusinessObject()) {
				request.setAttribute(bo.getBeanAttribute()
						.getBeanInstanceName()
						+ "Vt", bo.getViewText());
			}
		}
		request.setAttribute("isDeposit", "Y");
		request.setAttribute("roletype", "1");
		request.setAttribute("collect", collect);
		return new ModelAndView("xdss3/collect/collectEdit");
	}

	/**
	 * 未名户认领
	 * 
	 * @param request
	 * @param response
	 */
	public ModelAndView _manageUnnamerCollect(HttpServletRequest request,
			HttpServletResponse response) {
		String strDataAuthSql = CommonDataAuth.getDataAuthSql(this
				.getBusinessObject());
		request.setAttribute("dataAuthSql", strDataAuthSql);
		return new ModelAndView("xdss3/collect/collectUnnamerManage");
	}

	/**
	 * 创建
	 * 
	 * @param request
	 * @param response
	 */
	public ModelAndView _createUnnamerCollect(HttpServletRequest request,
			HttpServletResponse response) {
		String unnamercollectid = request.getParameter("unnamercollectid");
		UnnamerCollect unnamerCollect = this.unnamerCollectService
				._get(unnamercollectid);

		Collect collect = new Collect();
		collect.setApplyamount(unnamerCollect.getApplyamount());
		collect.setUnnamercollectid(unnamerCollect.getUnnamercollectid());
		collect.setCurrency(unnamerCollect.getCurrency());
		collect.setCollectrate(unnamerCollect.getCollectrate());
		collect.setSettlerate(unnamerCollect.getCollectrate());
		collect.setDept_id(unnamerCollect.getDeptid());
		collect.setText(unnamerCollect.getText());
		collect.setRemark(unnamerCollect.getRemark());
		collect.setActamount(BigDecimal.valueOf(0));
		collect.setConvertamount(BigDecimal.valueOf(0));
		collect.setGoodsamount(BigDecimal.valueOf(0));
		collect.setReplaceamount(BigDecimal.valueOf(0));
		collect.setSupplieramount(BigDecimal.valueOf(0));

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

		String companycode = this.collectService.getUserCompany();
		// yanghancai 2010-09-28 用户属于两部门，则默认部门为用户所选部门
		com.infolion.platform.console.sys.context.UserContext xdssUserContext = com.infolion.platform.console.sys.context.UserContextHolder
				.getLocalUserContext().getUserContext();
		if (null != xdssUserContext)
			collect.setDept_id(xdssUserContext.getSysUser().getDeptId());
		// String dept_id = this.collectService.getUserDept();
		// collect.setDept_id(dept_id);
		collect.setIncsuretybond("N");
		request.setAttribute("companycode", companycode);
		request.setAttribute("collect", collect);
		boolean haveBindWorkFlow = WorkflowService
				.getHaveBindWorkFlow("000000000258");
		request.setAttribute("haveBindWorkFlow", haveBindWorkFlow);
		request.setAttribute("workflowTaskId", workflowTaskId);
		request.setAttribute("workflowNodeDefId", workflowNodeDefId);

		UserContext userContext = UserContextHolder.getLocalUserContext()
				.getUserContext();
		String strRoleType = "";
		for (int i = 0; i < userContext.getGrantedRoles().size(); i++) {
			SysRole sysRole = (SysRole) userContext.getGrantedRoles().get(i);

			if ("业务员".indexOf(sysRole.getRoleName()) != -1) {
				strRoleType = "1";
			}
		}

		request.setAttribute("roletype", strRoleType);

		return new ModelAndView("xdss3/collect/collectAdd");
	}

	/**
	 * @修改作者：邱杰烜
	 * @修改日期：2010-08-20 保存收款项目，若为未明户认领，还必须将未明户收款表中的ISCLAIM字段设置为1
	 * @param request
	 * @param response
	 */
	public void _saveOrUpdate(HttpServletRequest request,
			HttpServletResponse response) {
		JSONObject jo = new JSONObject();
		// 绑定主对象值
		Collect collect = (Collect) ExBeanUtils.bindBusinessObjectData(request
				.getParameterMap(), Collect.class, true, request.getMethod(),
				true);
		/*
		 * 邱杰烜 2010-09-21 保存前先判断"出单发票号"是否已经被关联
		 */
		String refCollectNo = this.collectService
				.getCollectnoByExportNo(collect.getExport_apply_no());
		if (null == collect.getCollectno())
			collect.setCollectno("");
		if (!"".equals(refCollectNo)
				&& !refCollectNo.equals(collect.getCollectno())) {
			throw new BusinessException("该出单发票号已被收款单[" + refCollectNo + "]关联！");
		}
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<CollectItem> collectitemmodifyItems = ExBeanUtils
				.bindModifySubBoData(request.getParameterMap(),
						new Object[] { collect }, CollectItem.class, null);
		collect.setCollectitem(collectitemmodifyItems);
		Set<CollectItem> collectitemdeleteItems = ExBeanUtils
				.bindDeleteSubBoData(request.getParameterMap(),
						new Object[] { collect }, CollectItem.class, null);
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<CollectCbill> collectcbillmodifyItems = ExBeanUtils
				.bindModifySubBoData(request.getParameterMap(),
						new Object[] { collect }, CollectCbill.class, null);
		collect.setCollectcbill(collectcbillmodifyItems);
		Set<CollectCbill> collectcbilldeleteItems = ExBeanUtils
				.bindDeleteSubBoData(request.getParameterMap(),
						new Object[] { collect }, CollectCbill.class, null);
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<CollectRelated> collectrelatedmodifyItems = ExBeanUtils
				.bindModifySubBoData(request.getParameterMap(),
						new Object[] { collect }, CollectRelated.class, null);
		collect.setCollectrelated(collectrelatedmodifyItems);
		Set<CollectRelated> collectrelateddeleteItems = ExBeanUtils
				.bindDeleteSubBoData(request.getParameterMap(),
						new Object[] { collect }, CollectRelated.class, null);
		// 绑定子对象(一对一关系)
		SettleSubject settleSubject = (SettleSubject) ExBeanUtils
				.bindBusinessObjectData(request.getParameterMap(),
						SettleSubject.class, false, request.getMethod(), true);
		if (settleSubject != null) {
			if (null == settleSubject.getFlag()
					|| "".equals(settleSubject.getFlag().trim())) {
				settleSubject.setFlag("0");
			}
			collect.setSettleSubject(settleSubject);
			settleSubject.setCollect(collect);
		}
		// 绑定子对象(一对一关系)
		FundFlow fundFlow = (FundFlow) ExBeanUtils.bindBusinessObjectData(
				request.getParameterMap(), FundFlow.class, false, request
						.getMethod(), true);
		if (fundFlow != null) {
			if (null == fundFlow.getFlag()
					|| "".equals(fundFlow.getFlag().trim())) {
				fundFlow.setFlag("0");
			}
			collect.setFundFlow(fundFlow);
			fundFlow.setCollect(collect);
		}
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<CollectBankItem> collectbankitemmodifyItems = ExBeanUtils
				.bindModifySubBoData(request.getParameterMap(),
						new Object[] { collect }, CollectBankItem.class, null);
		collect.setCollectbankitem(collectbankitemmodifyItems);
		Set<CollectBankItem> collectbankitemdeleteItems = ExBeanUtils
				.bindDeleteSubBoData(request.getParameterMap(),
						new Object[] { collect }, CollectBankItem.class, null);

		// TODO LJX 20100901 DEBUG
		log
				.debug("collectbankitemmodifyItems:"
						+ collectbankitemmodifyItems != null ? collectbankitemmodifyItems
						.size()
						: "0");
		log
				.debug("collectbankitemdeleteItems:"
						+ collectbankitemdeleteItems != null ? collectbankitemdeleteItems
						.size()
						: "0");
		/** 
		 * 添加实际剩余保证金数据修正 (新增数据，或者审批中才修正)
		 * */
        if ("Y".equals(collect.getIncsuretybond())
                && (
                        StringUtils.isNullBlank(collect.getCollectno()) || 
                        "1".equals(collect.getBusinessstate()) || 
                        "2".equals(collect.getBusinessstate())
                        )
              ) {
		    for (CollectItem ci   : collect.getCollectitem()  ) {
		        ci.setActsuretybond(ci.getSuretybond());
		    }
		}
		
		
		/**
		 * 判断是否为未明户收款，若是，设置未明户收款为已确认
		 */
		UnnamerCollect unnamerCollect = new UnnamerCollect();
		/** ************************************************************* */
		// 若未明户收款ID不为空
		if (!StringUtils.isNullBlank(collect.getUnnamercollectid())) {
			unnamerCollect = this.unnamerCollectService._get(collect
					.getUnnamercollectid());
			log.debug("collect.getUnnamercollectid():"
					+ collect.getUnnamercollectid());
			if (StringUtils.isNullBlank(collect.getCollectid())) {
				Set<CollectBankItem> itSet = new HashSet<CollectBankItem>();
				CollectBankItem collectBankItem = new CollectBankItem();
				collectBankItem.setCollectbankid(unnamerCollect
						.getCollectbankid());
				collectBankItem.setCollectbankacc(unnamerCollect
						.getCollcetbankacc());
				collectBankItem.setColbanksubject(unnamerCollect
						.getCollectbanksbj());
				collectBankItem.setCollectamount(unnamerCollect
						.getApplyamount());
				collectBankItem.setCollectamount2(unnamerCollect
						.getApplyamount2());
				collectBankItem.setCollect(collect);
				itSet.add(collectBankItem);
				collect.setCollectbankitem(itSet);
				// TODO LJX 20100901 DEBUG
				log.debug("判断是否为未明户收款，若是，设置未明户收款为已确认：" + itSet.size());
			}
		}

		this.collectService._saveOrUpdate(collect, collectitemdeleteItems,
				collectcbilldeleteItems, collectrelateddeleteItems,
				collectbankitemdeleteItems, getBusinessObject());
		/**
		 * 判断是否为未明户收款，若是，设置未明户收款为已确认
		 */
		/** ************************************************************* */
		// 若未明户收款ID不为空
		if (!StringUtils.isNullBlank(collect.getUnnamercollectid())) {
			unnamerCollect.setIsclaim("1");
			this.unnamerCollectService._update(unnamerCollect,
					getBusinessObject());
		}
		/** ************************************************************* */

		// 如果返回参数带有calActivityId，将业务对象保存到相应的CalActivity中
		String calActivityId = request.getParameter("calActivityId");
		if (StringUtils.isNotBlank(calActivityId)) {
			CalActivityService calActivityService = (CalActivityService) EasyApplicationContextUtils
					.getBeanByName("calActivityService");
			CalActivity calActivity = calActivityService._get(calActivityId);
			if (calActivity != null) {
				calActivity.setBoid(this.getBusinessObject().getBoId());
				calActivity.setBoname(this.getBoName());
				calActivity.setBusid(collect.getCollectid());
				calActivityService._update2(calActivity, BusinessObjectService
						.getBusinessObject("CalActivity"));
			}
			this.operateClose(response);
		} else {
			request.setAttribute("businessId", collect.getCollectid());
			jo.put("collectno", collect.getCollectno());
			jo.put("collectid", collect.getCollectid());
			jo.put("settlesubjectid",
					collect.getSettleSubject() != null ? collect
							.getSettleSubject().getSettlesubjectid() : "");
			jo.put("fundflowid", collect.getFundFlow() != null ? collect
					.getFundFlow().getFundflowid() : "");
			String creator = collect.getCreator();
			String creator_text = SysCachePoolUtils.getDictDataValue("YUSER",
					creator);
			jo.put("creator_text", creator_text);
			jo.put("creator", creator);
			jo.put("createtime", collect.getCreatetime());
			String lastmodifyer = collect.getLastmodifyer();
			String lastmodifyer_text = SysCachePoolUtils.getDictDataValue(
					"YUSER", lastmodifyer);
			jo.put("lastmodifyer_text", lastmodifyer_text);
			jo.put("lastmodifyer", lastmodifyer);
			jo.put("lastmodifytime", collect.getLastmodifytime());
			this.operateSuccessfullyWithString(response, jo.toString());
		}
	}

	/**
	 * @创建作者：yanghancai
	 * @创建日期：2010-09-26 增加预确认功能
	 */
	public void _prepConfirmCollect(HttpServletRequest request,
			HttpServletResponse response) {
		Collect collect = new Collect();
		String id = request.getParameter("collectid");
		this.collectService.prepConfirmCollect(id, "2");
		this.operateSuccessfullyWithString(response, "");
	}

	/**
	 * @创建作者：邱杰烜
	 * @创建日期：2010-10-25 查看相关单据的信息
	 */
	public ModelAndView viewRelatedInfo(HttpServletRequest request,
			HttpServletResponse response) {
		Collect collect = new Collect();
		String relatedNo = request.getParameter("relatedNo");
		collect = this.collectService.getCollectByNo(relatedNo);
		boolean haveBindWorkFlow = WorkflowService
				.getHaveBindWorkFlow("000000000258");
		request.setAttribute("haveBindWorkFlow", haveBindWorkFlow);
		request.setAttribute("businessId", "");
		request.setAttribute("workflowTaskId", "");
		request.setAttribute("workflowNodeDefId", "");
		request.setAttribute("vt", getBusinessObject().getViewText());
		if (null != getBusinessObject().getSubBusinessObject()) {
			// 取得子业务对象
			for (BusinessObject bo : getBusinessObject().getSubBusinessObject()) {
				request.setAttribute(bo.getBeanAttribute()
						.getBeanInstanceName()
						+ "Vt", bo.getViewText());
			}
		}
		request.setAttribute("collect", collect);
		return new ModelAndView("xdss3/collect/collectView");
	}

	/**
	 * @创建作者：邱杰烜
	 * @创建日期：2010-11-04 收款清票状态查询
	 */
	public ModelAndView _collectBillManage(HttpServletRequest request,
			HttpServletResponse response) {
		return new ModelAndView("xdss3/fundBill/collectBillManage");
	}

	/**
	 * @创建作者：邱杰烜
	 * @创建日期：2010-11-11 收款多条件综合查询
	 */
	public ModelAndView _collectMultiConditionQuery(HttpServletRequest request,
			HttpServletResponse response) {
		return new ModelAndView("xdss3/collect/collectMultiConditionQuery");
	}

	public ModelAndView _suretyBondMultiConditionQuery(
			HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("xdss3/collect/suretyBondMultiConditionQuery");
	}

	public ModelAndView _suretyBondSubitem(HttpServletRequest request,
			HttpServletResponse response) {
		String collectno = request.getParameter("collectno");
		request.setAttribute("collectno", collectno);
		return new ModelAndView("xdss3/collect/suretyBondSubitem");
	}
	
	
	private String getGridSql(HttpServletRequest request) throws Exception{
		String signRegExp = "[\\~\\!\\@\\#\\$\\%\\^\\&\\*\\(\\)\\_\\+\\`\\-\\=\\～\\！\\＠\\＃\\＄\\"
			+ "％\\＾\\＆\\＊\\\\（\\）\\＿\\＋\\＝\\－\\｀\\[\\]\\\\'\\;\\/\\.\\,\\<\\>\\?\\:"
			+ "\"\\{\\}\\|\\，\\．\\／\\；\\＇\\［\\］\\＼\\＜\\＞\\？\\：\\＂\\｛\\｝\\｜\\“\\”\\"
			+ "‘\\’\\。\r+\n+\t+\\s\\ ]";
		StringBuffer sql = new StringBuffer(
		"SELECT DISTINCT a.*,C.NAME1,d.dept_name,bt.title,b.project_no,s.ddtext stateText,ct.ddtext collectTypeText FROM YCOLLECT a LEFT JOIN YCOLLECTITEM b ON a.COLLECTID=b.COLLECTID LEFT JOIN YGETKUNNR C ON A.CUSTOMER = C.KUNNR ");
		sql.append(" left outer join t_sys_dept d on a.dept_id=d.dept_id");
		sql.append(" left outer join t_project_info p on b.project_no=p.project_no");
		sql.append(" left outer join bm_business_type bt on p.trade_type=bt.id ");
		sql.append(" left outer join (select DOMVALUE_L,DDTEXT from DD07T where domname='YDCOLLECTBUZSTATE') s on a.businessstate=s.DOMVALUE_L");
		sql.append(" left outer join (select DOMVALUE_L,DDTEXT from DD07T where  domname='YDCOLLECTTYPE') ct on a.collecttype=ct.domvalue_l WHERE 1=1 ");
		// 获取SQL参数
		String collectNo = (String) request.getParameter("collectno"); // 收款单号
		String customer = (String) request.getParameter("customer"); // 客户编号
		String voucherNo = (String) request.getParameter("voucherno"); // 凭证号
		String deptId = (String) request.getParameter("dept_id"); // 部门ID
		String draft = (String) request.getParameter("draft"); // 单据号码
		String ihrez = (String) request.getParameter("ihrez"); // 外部纸质合同号
		String currency = (String) request.getParameter("currency"); // 币别
		String collecttype = (String) request.getParameter("collecttype"); // 收款方式
		String businessstate = (String) request.getParameter("businessstate"); // 业务状态
		String exportApplyNo = (String) request.getParameter("export_apply_no"); // 出单发票号
		String titleText = (String) request.getParameter("title_text"); // 抬头文本
		titleText = URLDecoder.decode(titleText, "UTF-8");
		String contractNo = (String) request.getParameter("contract_no"); // 合同号
		String projectNo = (String) request.getParameter("project_no"); // 立项号
		String amount1 = (String) request.getParameter("amount1"); // 金额1
		String amount2 = (String) request.getParameter("amount2"); // 金额2
		String applyDate1 = (String) request.getParameter("apply_date1"); // 申请时间1
		String applyDate2 = (String) request.getParameter("apply_date2"); // 申请时间2
		applyDate1 = applyDate1.replaceAll(signRegExp, ""); // 删除"申请时间1"中的所有符号
		applyDate2 = applyDate2.replaceAll(signRegExp, ""); // 删除"申请时间2"中的所有符号
		String approvalDate1 = (String) request.getParameter("approval_date1"); // 审批通过时间1
		String approvalDate2 = (String) request.getParameter("approval_date2"); // 审批通过时间2
		approvalDate1 = approvalDate1.replaceAll(signRegExp, ""); // 删除"审批通过时间1"中的所有符号
		approvalDate2 = approvalDate2.replaceAll(signRegExp, ""); // 删除"审批通过时间2"中的所有符号
		String draftDate1 = (String) request.getParameter("draft_date1"); // 银行承兑汇票/国内信用证到期日1
		String draftDate2 = (String) request.getParameter("draft_date2"); // 银行承兑汇票/国内信用证到期日2
		draftDate1 = draftDate1.replaceAll(signRegExp, ""); // 删除"银行承兑汇票/国内信用证到期日1"中的所有符号
		draftDate2 = draftDate2.replaceAll(signRegExp, ""); // 删除"银行承兑汇票/国内信用证到期日2"中的所有符号
		String ymatGroup = (String) request.getParameter("ymatGroup"); // 抬头文本
		ymatGroup = URLDecoder.decode(ymatGroup, "UTF-8");
		String sendgoodsdate = (String) request.getParameter("sendgoodsdate"); // 预计发货日
		sendgoodsdate = sendgoodsdate.replaceAll(signRegExp, "");
		String sendgoodsdate1 = (String) request.getParameter("sendgoodsdate1"); // 预计发货日
		sendgoodsdate1=sendgoodsdate1.replaceAll(signRegExp, "");
		String yushouTS = (String) request.getParameter("yushouTS"); // 预收天数
		String yushouTS1 = (String) request.getParameter("yushouTS1"); // 预收天数
		String yanshouTS = (String) request.getParameter("yanshouTS"); // 收款方式
		String yanshouTS1 = (String) request.getParameter("yanshouTS1"); // 收款方式
		String tradeType = (String) request.getParameter("tradeType"); // 贸易方式


		if ("请选择".equals(ymatGroup)) {
			ymatGroup = "";
		}
		// 设置收款编号条件
		if (StringUtils.isNotBlank(collectNo)) {
			sql.append("AND a.COLLECTNO LIKE '%" + collectNo + "%'");
		}
		// 设置客户编号条件
		if (StringUtils.isNotBlank(customer)) {
			sql.append("AND a.CUSTOMER = '" + customer + "'");
		}
		// 设置凭证号条件
		if (StringUtils.isNotBlank(voucherNo)) {
			sql
			.append("AND EXISTS(SELECT 1 FROM YVOUCHER d WHERE d.BUSINESSID=a.COLLECTID AND d.VOUCHERNO LIKE '%"
					+ voucherNo + "%')");
		}
		// 设置部门ID条件
		if (StringUtils.isNotBlank(deptId)) {
			sql.append("AND a.DEPT_ID = '" + deptId + "'");
		}
		// 设置单据号码条件
		if (StringUtils.isNotBlank(draft)) {
			sql.append("AND a.DRAFT LIKE '%" + draft + "%'");
		}
		// 物料组
		if (StringUtils.isNotBlank(ymatGroup)) {
			sql.append(" AND b.ymat_group like '%" + ymatGroup + "%'");
		}
		// 设置外部纸质合同号条件
		if (StringUtils.isNotBlank(ihrez)) {
			sql
			.append("AND b.CONTRACT_NO IN (SELECT CONTRACT_NO FROM T_CONTRACT_SALES_INFO WHERE VBKD_IHREZ LIKE '%"
					+ ihrez + "%')");
		}
		// 设置币别条件
		if (StringUtils.isNotBlank(currency)) {
			sql.append("AND a.CURRENCY = '" + currency + "'");
		}
		// 设置收款方式条件
		if (StringUtils.isNotBlank(collecttype)) {
			sql.append("AND a.COLLECTTYPE = '" + collecttype + "'");
		}
		// 设置业务状态条件
		if (StringUtils.isNotBlank(businessstate)) {
			if (businessstate.equals("0")) {
				sql.append("AND a.BUSINESSSTATE IN ('0','',' ')");
			} else {
				sql.append("AND a.BUSINESSSTATE = '" + businessstate + "'");
			}
		}
		// 设置出单号条件
		if (StringUtils.isNotBlank(exportApplyNo)) {
			exportApplyNo = java.net.URLDecoder.decode(exportApplyNo, "UTF-8");
			sql.append("AND a.EXPORT_APPLY_NO LIKE '%" + exportApplyNo + "%'");
		}
		// 设置抬头文本条件
		if (StringUtils.isNotBlank(titleText)) {
			sql.append("AND a.TEXT LIKE '%" + titleText + "%'");
		}
		// 设置合同号条件
		if (StringUtils.isNotBlank(contractNo)) {
			sql.append("AND b.CONTRACT_NO LIKE '%" + contractNo + "%'");
		}
		// 设置立项号条件
		if (StringUtils.isNotBlank(projectNo)) {
			sql.append("AND b.PROJECT_NO LIKE '%" + projectNo + "%'");
		}
		// 设置金额条件
		if (StringUtils.isNotBlank(amount1) && StringUtils.isNotBlank(amount2)) {
			sql.append("AND a.APPLYAMOUNT BETWEEN '" + amount1 + "' AND '"
					+ amount2 + "'");
		} else if (StringUtils.isNotBlank(amount1)) {
			sql.append("AND a.APPLYAMOUNT > '" + amount1 + "'");
		} else if (StringUtils.isNotBlank(amount2)) {
			sql.append("AND a.APPLYAMOUNT < '" + amount2 + "'");
		}
		// 设置申请时间条件
		if (StringUtils.isNotBlank(applyDate1)
				&& StringUtils.isNotBlank(applyDate2)) {
			sql.append("AND a.CREATETIME BETWEEN '" + applyDate1 + "' AND '"
					+ applyDate2 + "'");
		} else if (StringUtils.isNotBlank(applyDate1)) {
			sql.append("AND a.CREATETIME > '" + applyDate1 + "'");
		} else if (StringUtils.isNotBlank(applyDate2)) {
			sql.append("AND a.CREATETIME < '" + applyDate2 + "'");
		}
		// 设置审批通过时间条件
		if (StringUtils.isNotBlank(approvalDate1)
				&& StringUtils.isNotBlank(approvalDate2)) {
			sql.append("AND a.BUSINESSSTATE='3' AND a.LASTMODIFYTIME BETWEEN '"
					+ approvalDate1 + "' AND '" + approvalDate2 + "'");
		} else if (StringUtils.isNotBlank(approvalDate1)) {
			sql.append("AND a.BUSINESSSTATE='3' AND a.LASTMODIFYTIME > '"
					+ approvalDate1 + "'");
		} else if (StringUtils.isNotBlank(approvalDate2)) {
			sql.append("AND a.BUSINESSSTATE='3' AND a.LASTMODIFYTIME < '"
					+ approvalDate2 + "'");
		}
		// 设置银行承兑汇票/国内信用证到期日条件
		if (StringUtils.isNotBlank(draftDate1)
				&& StringUtils.isNotBlank(draftDate2)) {
			sql.append("AND a.DRAFTDATE BETWEEN '" + draftDate1 + "' AND '"
					+ draftDate2 + "'");
		} else if (StringUtils.isNotBlank(draftDate1)) {
			sql.append("AND a.DRAFTDATE > '" + draftDate1 + "'");
		} else if (StringUtils.isNotBlank(draftDate2)) {
			sql.append("AND a.DRAFTDATE < '" + draftDate2 + "'");
		}
		//
		if (StringUtils.isNotBlank(sendgoodsdate)) {
			sql.append(" AND a.sendgoodsdate >= '" + sendgoodsdate + "'");
		}
		if (StringUtils.isNotBlank(sendgoodsdate1)) {
			sql.append(" AND a.sendgoodsdate <= '" + sendgoodsdate1 + "'");
		}
		if (StringUtils.isNotBlank(yushouTS)) {
			sql.append(" AND to_date(substr(a.createtime,0,8),'yyyymmdd')-to_date(trim(a.sendgoodsdate),'yyyy-mm-dd') >= " + yushouTS + "");
		}
		if (StringUtils.isNotBlank(yushouTS1)) {
			sql.append(" AND to_date(substr(a.createtime,0,8),'yyyymmdd')-to_date(trim(a.sendgoodsdate),'yyyy-mm-dd') <= " + yushouTS1 + "");
		}
		if (StringUtils.isNotBlank(yanshouTS)) {
			sql.append(" AND to_date(trim(a.sendgoodsdate),'yyyy-mm-dd')-to_date(substr(a.createtime,0,8),'yyyymmdd') >= " + yanshouTS + "");
		}
		if (StringUtils.isNotBlank(yanshouTS1)) {
			sql.append(" AND to_date(trim(a.sendgoodsdate),'yyyy-mm-dd')-to_date(substr(a.createtime,0,8),'yyyymmdd') <= " + yanshouTS1 + "");
		}
		if (StringUtils.isNotBlank(tradeType)) {
			sql.append(" and exists (select '' from t_project_info where  project_no=b.project_no and trade_type ='" + tradeType + "') ");
		}
		//
		return sql.toString();
	}

	public void queryGrid(HttpServletRequest request,
			HttpServletResponse response, GridQueryCondition gridQueryCondition)
			throws Exception {
		BusinessObject businessObject = BusinessObjectService
				.getBusinessObject("Collect");
		String strAuthSql = "";
		try {
			AuthSql authSql = (AuthSql) Class.forName(
					"com.infolion.xdss3.CommonDataAuthSql").newInstance();
			strAuthSql = authSql.getAuthSql(businessObject);
			// 替换权限默认的前缀表名
			strAuthSql = strAuthSql.replace("YCOLLECT", "t");
		} catch (Exception ex) {
			throw new SQLException("类不存在：" + ex.getMessage());
		}
		
		gridQueryCondition.setBoName("");
		gridQueryCondition.setTableSql("");
		gridQueryCondition.setDefaultCondition("1=1 " + strAuthSql);
		gridQueryCondition.setWhereSql("");
		gridQueryCondition.setOrderSql("CREATETIME DESC");
		gridQueryCondition.setGroupBySql("");
		gridQueryCondition.setTableName("(" + getGridSql(request) + ") t");
		gridQueryCondition
				.setHandlerClass("com.infolion.xdss3.collect.domain.CollectGrid");
		String editable = "false";
		String needAuthentication = "true";
		GridData gridJsonData = this.gridService.getGridData(
				gridQueryCondition, editable, needAuthentication);
		JSONObject jsonList = JSONObject.fromObject(gridJsonData);
		try {
			response.setContentType("text/html;charset=UTF-8");
			response.getWriter().print(jsonList);
		} catch (IOException e) {
			logger.error("输出json失败," + e.getMessage(), e.getCause());
		}
	}
	
	public void _export(HttpServletRequest request, HttpServletResponse response,
			GridQueryCondition gridQueryCondition) throws Exception {

		BusinessObject businessObject = BusinessObjectService.getBusinessObject("Collect");
		String strAuthSql = "";
		try {
			AuthSql authSql = (AuthSql) Class.forName(
			"com.infolion.xdss3.CommonDataAuthSql").newInstance();
			strAuthSql = authSql.getAuthSql(businessObject);
			// 替换权限默认的前缀表名
			strAuthSql = strAuthSql.replace("YCOLLECT", "t");
		} catch (Exception ex) {
			throw new SQLException("类不存在：" + ex.getMessage());
		}


		String grid_sql = getGridSql(request);


		String[] titles  = new String[]{"序号","贸易方式","收款单号","部门","客户名称","立项号","金额","币别","预计发货日","创建日期","凭证日期","收款方式","状态"};
		ExcelObject excel = new ExcelObject(titles);
		collectService.dealOutToExcel(grid_sql,strAuthSql ,excel);


		try{
			//response.reset();
			response.setCharacterEncoding("utf-8");
			response.setContentType("application/x-download;charset=utf-8");
			response.setHeader("Content-Disposition", (new StringBuilder("attachment;filename=")).append(URLEncoder.encode("收款表.xls", "utf-8")).toString());
			excel.write(response.getOutputStream());
			response.flushBuffer();
			response.getOutputStream().close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	
	public void _exportSurety(HttpServletRequest request, HttpServletResponse response,
			GridQueryCondition gridQueryCondition) throws Exception {

		BusinessObject businessObject = BusinessObjectService.getBusinessObject("Collect");
		String strAuthSql = "";
		try {
			AuthSql authSql = (AuthSql) Class.forName(
			"com.infolion.xdss3.CommonDataAuthSql").newInstance();
			strAuthSql = authSql.getAuthSql(businessObject);
			// 替换权限默认的前缀表名
			strAuthSql = strAuthSql.replace("YCOLLECT", "t");
		} catch (Exception ex) {
			throw new SQLException("类不存在：" + ex.getMessage());
		}


		String grid_sql = getSuretyGridSql(request);


		String[] titles  = new String[]{"序号","收款单号","部门","客户名称","立项号","金额","币别","备注","文本","创建日期"};
		ExcelObject excel = new ExcelObject(titles);
		collectService.dealOutToExcelSurety(grid_sql,strAuthSql ,excel);


		try{
			//response.reset();
			response.setCharacterEncoding("utf-8");
			response.setContentType("application/x-download;charset=utf-8");
			response.setHeader("Content-Disposition", (new StringBuilder("attachment;filename=")).append(URLEncoder.encode("保证金收款表.xls", "utf-8")).toString());
			excel.write(response.getOutputStream());
			response.flushBuffer();
			response.getOutputStream().close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	private String getSuretyGridSql(HttpServletRequest request) throws Exception{
		String signRegExp = "[\\~\\!\\@\\#\\$\\%\\^\\&\\*\\(\\)\\_\\+\\`\\-\\=\\～\\！\\＠\\＃\\＄\\"
			+ "％\\＾\\＆\\＊\\\\（\\）\\＿\\＋\\＝\\－\\｀\\[\\]\\\\'\\;\\/\\.\\,\\<\\>\\?\\:"
			+ "\"\\{\\}\\|\\，\\．\\／\\；\\＇\\［\\］\\＼\\＜\\＞\\？\\：\\＂\\｛\\｝\\｜\\“\\”\\"
			+ "‘\\’\\。\r+\n+\t+\\s\\ ]";
		StringBuffer sql = new StringBuffer(
		"SELECT DISTINCT a.*,b.project_no,C.NAME1,D.DEPT_NAME FROM YCOLLECT a LEFT JOIN YCOLLECTITEM b ON a.COLLECTID=b.COLLECTID LEFT JOIN YGETKUNNR C ON A.CUSTOMER = C.KUNNR LEFT JOIN T_SYS_DEPT D ON A.DEPT_ID = D.DEPT_ID WHERE 1=1 ");
		// 获取SQL参数
		String collectNo = (String) request.getParameter("collectno"); // 收款单号
		String customer = (String) request.getParameter("customer"); // 客户编号
		String voucherNo = (String) request.getParameter("voucherno"); // 收款单号
		String deptId = (String) request.getParameter("dept_id"); // 部门ID
		String draft = (String) request.getParameter("draft"); // 单据号码
		String ihrez = (String) request.getParameter("ihrez"); // 外部纸质合同号
		String currency = (String) request.getParameter("currency"); // 币别
		String collecttype = (String) request.getParameter("collecttype"); // 收款方式
		String businessstate = (String) request.getParameter("businessstate"); // 业务状态
		String exportApplyNo = (String) request.getParameter("export_apply_no"); // 出单发票号
		String titleText = (String) request.getParameter("title_text"); // 抬头文本
		if (StringUtils.isNotBlank(titleText)) {
			titleText = URLDecoder.decode(titleText, "UTF-8");
		}
		String contractNo = (String) request.getParameter("contract_no"); // 合同号
		String projectNo = (String) request.getParameter("project_no"); // 立项号
		String isProjectClose = (String) request.getParameter("isProjectClose"); // 是否关闭立项
		String amount1 = (String) request.getParameter("amount1"); // 金额1
		String amount2 = (String) request.getParameter("amount2"); // 金额2
		String applyDate1 = (String) request.getParameter("apply_date1"); // 申请时间1
		String applyDate2 = (String) request.getParameter("apply_date2"); // 申请时间2
		if (StringUtils.isNotBlank(applyDate1)) {
			applyDate1 = applyDate1.replaceAll(signRegExp, ""); // 删除"申请时间1"中的所有符号
		}
		if (StringUtils.isNotBlank(applyDate2)) {
			applyDate2 = applyDate2.replaceAll(signRegExp, ""); // 删除"申请时间2"中的所有符号
		}
		String approvalDate1 = (String) request.getParameter("approval_date1"); // 审批通过时间1
		String approvalDate2 = (String) request.getParameter("approval_date2"); // 审批通过时间2
		if (StringUtils.isNotBlank(approvalDate1)) {
			approvalDate1 = approvalDate1.replaceAll(signRegExp, ""); // 删除"审批通过时间1"中的所有符号
		}
		if (StringUtils.isNotBlank(approvalDate2)) {
			approvalDate2 = approvalDate2.replaceAll(signRegExp, ""); // 删除"审批通过时间2"中的所有符号
		}
		String draftDate1 = (String) request.getParameter("draft_date1"); // 银行承兑汇票/国内信用证到期日1
		String draftDate2 = (String) request.getParameter("draft_date2"); // 银行承兑汇票/国内信用证到期日2
		// draftDate1 = draftDate1.replaceAll(signRegExp, ""); //
		// 删除"银行承兑汇票/国内信用证到期日1"中的所有符号
		// draftDate2 = draftDate2.replaceAll(signRegExp, ""); //
		// 删除"银行承兑汇票/国内信用证到期日2"中的所有符号
		// 设置收款编号条件
		if (StringUtils.isNotBlank(collectNo)) {
			sql.append("AND a.COLLECTNO LIKE '%" + collectNo + "%'");
		}
		// 设置客户编号条件
		if (StringUtils.isNotBlank(customer)) {
			sql.append("AND a.CUSTOMER = '" + customer + "'");
		}
		// 设置凭证号条件
		if (StringUtils.isNotBlank(voucherNo)) {
			sql
			.append("AND EXISTS(SELECT 1 FROM YVOUCHER d WHERE d.BUSINESSID=a.COLLECTID AND d.VOUCHERNO LIKE '%"
					+ voucherNo + "%')");
		}
		// 设置部门ID条件
		if (StringUtils.isNotBlank(deptId)) {
			sql.append("AND a.DEPT_ID = '" + deptId + "'");
		}
		// 设置单据号码条件
		if (StringUtils.isNotBlank(draft)) {
			sql.append("AND a.DRAFT LIKE '%" + draft + "%'");
		}
		// 设置外部纸质合同号条件
		if (StringUtils.isNotBlank(ihrez)) {
			sql
			.append("AND b.CONTRACT_NO IN (SELECT CONTRACT_NO FROM T_CONTRACT_SALES_INFO WHERE VBKD_IHREZ LIKE '%"
					+ ihrez + "%')");
		}
		// 设置币别条件
		if (StringUtils.isNotBlank(currency)) {
			sql.append("AND a.CURRENCY = '" + currency + "'");
		}
		// 设置收款方式条件
		if (StringUtils.isNotBlank(collecttype)) {
			sql.append("AND a.COLLECTTYPE = '" + collecttype + "'");
		}
		// 设置业务状态条件
		if (StringUtils.isNotBlank(businessstate)) {
			if (businessstate.equals("0")) {
				sql.append("AND a.BUSINESSSTATE IN ('0','',' ')");
			} else {
				sql.append("AND a.BUSINESSSTATE = '" + businessstate + "'");
			}
		}
		// 设置出单号条件
		if (StringUtils.isNotBlank(exportApplyNo)) {
			exportApplyNo = java.net.URLDecoder.decode(exportApplyNo, "UTF-8");
			sql.append("AND a.EXPORT_APPLY_NO LIKE '%" + exportApplyNo + "%'");
		}
		// 设置抬头文本条件
		if (StringUtils.isNotBlank(titleText)) {
			sql.append("AND a.TEXT LIKE '%" + titleText + "%'");
		}
		// 设置合同号条件
		if (StringUtils.isNotBlank(contractNo)) {
			sql.append("AND b.CONTRACT_NO LIKE '%" + contractNo + "%'");
		}
		// 设置立项号条件
		if (StringUtils.isNotBlank(projectNo)) {
			sql.append("AND b.PROJECT_NO LIKE '%" + projectNo + "%'");
		}
		// 设置立项号条件
		if (StringUtils.isNotBlank(isProjectClose)) {
			if("0".equals(isProjectClose)){
				sql.append(" and not exists (select '' from t_project_info p where p.project_no=b.project_no and p.project_state='9')");
			}
			else if("1".equals(isProjectClose)){
				sql.append(" and exists (select '' from t_project_info p where p.project_no=b.project_no and p.project_state='9')");
			}
		}
		// 设置金额条件
		if (StringUtils.isNotBlank(amount1) && StringUtils.isNotBlank(amount2)) {
			sql.append("AND a.APPLYAMOUNT BETWEEN '" + amount1 + "' AND '"
					+ amount2 + "'");
		} else if (StringUtils.isNotBlank(amount1)) {
			sql.append("AND a.APPLYAMOUNT > '" + amount1 + "'");
		} else if (StringUtils.isNotBlank(amount2)) {
			sql.append("AND a.APPLYAMOUNT < '" + amount2 + "'");
		}
		// 设置申请时间条件
		if (StringUtils.isNotBlank(applyDate1)
				&& StringUtils.isNotBlank(applyDate2)) {
			sql.append("AND a.CREATETIME BETWEEN '" + applyDate1 + "' AND '"
					+ applyDate2 + "'");
		} else if (StringUtils.isNotBlank(applyDate1)) {
			sql.append("AND a.CREATETIME > '" + applyDate1 + "'");
		} else if (StringUtils.isNotBlank(applyDate2)) {
			sql.append("AND a.CREATETIME < '" + applyDate2 + "'");
		}
		// 设置审批通过时间条件
		if (StringUtils.isNotBlank(approvalDate1)
				&& StringUtils.isNotBlank(approvalDate2)) {
			sql.append("AND a.BUSINESSSTATE='3' AND a.LASTMODIFYTIME BETWEEN '"
					+ approvalDate1 + "' AND '" + approvalDate2 + "'");
		} else if (StringUtils.isNotBlank(approvalDate1)) {
			sql.append("AND a.BUSINESSSTATE='3' AND a.LASTMODIFYTIME > '"
					+ approvalDate1 + "'");
		} else if (StringUtils.isNotBlank(approvalDate2)) {
			sql.append("AND a.BUSINESSSTATE='3' AND a.LASTMODIFYTIME < '"
					+ approvalDate2 + "'");
		}
		// 设置银行承兑汇票/国内信用证到期日条件
		if (StringUtils.isNotBlank(draftDate1)
				&& StringUtils.isNotBlank(draftDate2)) {
			sql.append("AND a.DRAFTDATE BETWEEN '" + draftDate1 + "' AND '"
					+ draftDate2 + "'");
		} else if (StringUtils.isNotBlank(draftDate1)) {
			sql.append("AND a.DRAFTDATE > '" + draftDate1 + "'");
		} else if (StringUtils.isNotBlank(draftDate2)) {
			sql.append("AND a.DRAFTDATE < '" + draftDate2 + "'");
		}

		sql.append("AND  a.incsuretybond='Y'");
		return sql.toString();

	}

	public void suretyBondqueryGrid(HttpServletRequest request,
			HttpServletResponse response, GridQueryCondition gridQueryCondition)
			throws Exception {
		BusinessObject businessObject = BusinessObjectService
				.getBusinessObject("Collect");
		String strAuthSql = "";
		try {
			AuthSql authSql = (AuthSql) Class.forName(
					"com.infolion.xdss3.CommonDataAuthSql").newInstance();
			strAuthSql = authSql.getAuthSql(businessObject);
			// 替换权限默认的前缀表名
			strAuthSql = strAuthSql.replace("YCOLLECT", "t");
		} catch (Exception ex) {
			throw new SQLException("类不存在：" + ex.getMessage());
		}


		gridQueryCondition.setBoName("");
		gridQueryCondition.setTableSql("");
		gridQueryCondition.setDefaultCondition("1=1 " + strAuthSql);
		gridQueryCondition.setWhereSql("");
		gridQueryCondition.setOrderSql("CREATETIME DESC");
		gridQueryCondition.setGroupBySql("");
		gridQueryCondition.setTableName("(" + getSuretyGridSql(request) + ") t");
		gridQueryCondition
				.setHandlerClass("com.infolion.xdss3.collect.domain.CollectSuretyBondGrid");
		String editable = "false";
		String needAuthentication = "true";
		GridData gridJsonData = this.gridService.getGridData(
				gridQueryCondition, editable, needAuthentication);
		JSONObject jsonList = JSONObject.fromObject(gridJsonData);
		try {
			response.setContentType("text/html;charset=UTF-8");
			response.getWriter().print(jsonList);
		} catch (IOException e) {
			logger.error("输出json失败," + e.getMessage(), e.getCause());
		}
	}

	public void suretyBondSubitem(HttpServletRequest request,
			HttpServletResponse response, GridQueryCondition gridQueryCondition)
			throws Exception {
		BusinessObject businessObject = BusinessObjectService
				.getBusinessObject("Collect");
		String strAuthSql = "";
		try {
			AuthSql authSql = (AuthSql) Class.forName(
					"com.infolion.xdss3.CommonDataAuthSql").newInstance();
			strAuthSql = authSql.getAuthSql(businessObject);
			// 替换权限默认的前缀表名
			strAuthSql = strAuthSql.replace("YCOLLECT", "t");
		} catch (Exception ex) {
			throw new SQLException("类不存在：" + ex.getMessage());
		}
		StringBuffer sql = new StringBuffer();
		// 获取SQL参数
		String collectNo = (String) request.getParameter("collectno"); // 收款单号
		if (StringUtils.isNotBlank(collectNo)) {
			sql
					.append(" SELECT DISTINCT ")
					.append(" '1' AS CATEGORY, ")
					.append(" a.MANDT, ")
					.append(" a.COLLECTID, ")
					.append(" a.COLLECTNO, ")
					.append(" a.CUSTOMER, ")
					.append(" a.TRADE_TYPE, ")
					.append(" a.INCSURETYBOND, ")
					.append(" a.COLLECTTYPE, ")
					.append(" a.CURRENCY, ")
					.append(" a.APPLYAMOUNT, ")
					.append(" a.BILLCURRENCY, ")
					.append(" a.CONVERTAMOUNT, ")
					.append(" a.SETTLERATE, ")
					.append(" a.DRAFT, ")
					.append(" a.EXPIREDATE, ")
					.append(" a.BILLBC, ")
					.append(" a.DRAFTDATE, ")
					.append(" a.EXPORT_APPLY_NO, ")
					.append(" a.SENDGOODSDATE, ")
					.append(" a.BILLCHECKDATE, ")
					.append(" a.GOODSAMOUNT, ")
					.append(" a.REPLACEAMOUNT, ")
					.append(" a.SUPPLIERAMOUNT, ")
					.append(" a.DEPT_ID, ")
					.append(" a.TEXT, ")
					.append(" a.REMARK, ")
					.append(" a.UNNAMERCOLLECTID, ")
					.append(" a.OLDCOLLECTID, ")
					.append(" a.OLDCOLLECTNO, ")
					.append(" a.OLDCOLLECTITEMID, ")
					.append(" a.OLDCONTRACT_NO, ")
					.append(" a.OLDPROJECT_NO, ")
					.append(" a.REMAINSURETYBOND, ")
					.append(" a.COLLECTRATE, ")
					.append(" a.VOUCHERDATE, ")
					.append(" a.ACCOUNTDATE, ")
					.append(" a.ACTAMOUNT, ")
					.append(" a.ACTCURRENCY, ")
					.append(" a.BUSINESSSTATE, ")
					.append(" a.PROCESSSTATE, ")
					.append(" a.CREATOR, ")
					.append(" a.CREATETIME, ")
					.append(" a.LASTMODIFYER, ")
					.append(" a.LASTMODIFYTIME, ")
					.append(" a.TICKETBANKID, ")
					.append(
							" C.NAME1 FROM YCOLLECT a LEFT JOIN YCOLLECTITEM b ON a.COLLECTID=b.COLLECTID LEFT JOIN YGETKUNNR C ON A.CUSTOMER = C.KUNNR WHERE 1=1 "
									+ " and a.oldcollectid in ( select collectid  from YCOLLECT where collectno like '%"
									+ collectNo + "%'  AND incsuretybond='Y' )");
		}
		sql.append("union SELECT '2' AS CATEGORY, F.MANDT AS MANDT, ").append(
				"        F.REFUNDMENTID AS COLLECTID, ").append(
				"        F.REFUNDMENTNO AS COLLECTNO, ").append(
				"        F.CUSTOMER AS CUSTOMER, ").append(
				"        F.TRADETYPE AS TRADE_TYPE, ").append(
				"        R.ISTYBOND AS INCSURETYBOND, ").append(
				"        '' AS COLLECTTYPE, ").append(
				"        F.REFUNDCURRENCY AS CURRENCY, ")
				.append("        R.REFUNDMENTAMOUNT AS APPLYAMOUNT,  ")
				// --退款行项 清原币金额 当 申请收款
				.append("        R.CURRENCY AS BILLCURRENCY, ")
				.append("        R.PEFUNDMENTAMOUNT AS CONVERTAMOUNT, ")
				// --折算金额/保证金转货款金额
				.append("        0 AS SETTLERATE, ").append(
						"        '' AS DRAFT, ").append(
						"        '' AS EXPIREDATE, ").append(
						"        '' AS BILLBC, ").append(
						"        '' AS DRAFTDATE, ").append(
						"        '' AS EXPORT_APPLY_NO, ").append(
						"        '' AS SENDGOODSDATE, ").append(
						"        '' AS BILLCHECKDATE, ").append(
						"        0 AS GOODSAMOUNT, ").append(
						"        0 AS REPLACEAMOUNT, ").append(
						"        0 AS SUPPLIERAMOUNT, ").append(
						"        F.DEPT_ID AS DEPT_ID, ").append(
						"        F.TEXT AS TEXT, ").append(
						"        F.REMARK AS REMARK, ").append(
						"        '' AS UNNAMERCOLLECTID, ").append(
						"        '' AS OLDCOLLECTID, ").append(
						"        '' AS OLDCOLLECTNO, ").append(
						"        '' AS OLDCOLLECTITEMID, ").append(
						"        '' AS OLDCONTRACT_NO, ").append(
						"        '' AS OLDPROJECT_NO, ").append(
						"        0 AS REMAINSURETYBOND, ").append(
						"        0 AS COLLECTRATE, ").append(
						"        F.VOUCHERDATE AS VOUCHERDATE, ").append(
						"        F.ACCOUNTDATE AS ACCOUNTDATE, ").append(
						"        0 AS ACTAMOUNT, ").append(
						"        '' AS ACTCURRENCY, ").append(
						"        F.BUSINESSSTATE AS BUSINESSSTATE, ").append(
						"        F.PROCESSSTATE AS PROCESSSTATE, ").append(
						"        F.CREATOR AS CREATOR, ").append(
						"        F.CREATETIME AS CREATETIME, ").append(
						"        F.LASTMODIYER AS LASTMODIFYER, ").append(
						"        F.LASTMODIFYTIME AS LASTMODIFYTIME, ").append(
						"        '' AS TICKETBANKID, ").append(
						"        C.NAME1 AS NAME1 ").append(
						"   FROM YREFUNDMENT F ").append(
						"   LEFT JOIN YGETKUNNR C ").append(
						"     ON (F.CUSTOMER = C.KUNNR), YREFUNDMENTITEM R ")
				.append("  WHERE 1 = 1 ").append(
						"    AND F.REFUNDMENTID = R.REFUNDMENTID ").append(
						"    AND R.COLLECTNO like '%" + collectNo + "%' ")
				.append("    AND R.ISTYBOND = 'Y' ");

		gridQueryCondition.setBoName("");
		gridQueryCondition.setTableSql("");
		gridQueryCondition.setDefaultCondition("1=1 " + strAuthSql);
		gridQueryCondition.setWhereSql("");
		gridQueryCondition.setOrderSql("CREATETIME desc");
		gridQueryCondition.setGroupBySql("");
		gridQueryCondition.setTableName("(" + sql.toString() + ") t");
		gridQueryCondition
				.setHandlerClass("com.infolion.xdss3.collect.domain.CollectSuretyBondGrid2");
		String editable = "false";
		String needAuthentication = "true";
		Collect collect = this.collectHibernateDao.getCollectByNo(collectNo);
		BigDecimal remainsuretybond = new BigDecimal(0);
		if (collect != null) {
			remainsuretybond = collect.getRemainsuretybond();
		}
		GridData gridJsonData = this.gridService.getGridData(
				gridQueryCondition, editable, needAuthentication);
		for (int i = 0; i < gridJsonData.getData().length; i++) {
			Object data = gridJsonData.getData()[i];
			ListOrderedMap list = (ListOrderedMap) data;
			BigDecimal applyamount = new BigDecimal((String) list
					.get("applyamount"));
			if ("-1".equals((String) list.get("businessstate"))) {
				list.put("applyamount", applyamount.negate());
				continue;
			}
			list.put("remainsuretybond", remainsuretybond.toString());
			remainsuretybond = remainsuretybond.add(applyamount);
		}
		JSONObject jsonList = JSONObject.fromObject(gridJsonData);
		try {
			response.setContentType("text/html;charset=UTF-8");
			response.getWriter().print(jsonList);
		} catch (IOException e) {
			logger.error("输出json失败," + e.getMessage(), e.getCause());
		}
	}

	/**
	 * 取得默认查询条件，如果有多个值传入，以最后的一个查询条件为准
	 * 
	 * @param request
	 * @return
	 */
	private String getDefaultCondition(HttpServletRequest request) {
		String[] defaultConditions = request
				.getParameterValues("defaultCondition");
		if (null == defaultConditions || defaultConditions.length < 1) {
			return "";
		}
		return defaultConditions[defaultConditions.length - 1];
	}

	public void getJournalType(HttpServletRequest request,
			HttpServletResponse response) {
		JSONObject jo = new JSONObject();
		String colbankitemids = request.getParameter("colbankitemids");
		String[] bankids = colbankitemids.split(",");
		String ispva = collectService.getBankIsPvaByAccount(bankids[0]);

		String journalType = "2";
		if ("1".equals(ispva))
			journalType = "1";

		jo.put("journalType", journalType);
		try {
			response.setContentType("text/html;charset=UTF-8");
			response.getWriter().print(jo);
			System.out.println(jo.toString());
		} catch (IOException e) {
			logger.error("输出json失败," + e.getMessage(), e.getCause());
		}
	}

	/**
	 * 取得在批的退款号
	 * 
	 * @param request
	 * @param response
	 */
	public void getRefundByCollectitemid(HttpServletRequest request,
			HttpServletResponse response) {
		JSONObject jo = new JSONObject();
		String collectItemid = request.getParameter("collectItemid");
		String refundNo = collectService
				.getRefundByCollectitemid(collectItemid);
		if (null != refundNo) {
			jo.put("refundNo", refundNo);
		} else {
			jo.put("refundNo", "");
		}

		try {
			response.setContentType("text/html;charset=UTF-8");
			response.getWriter().print(jo.get("refundNo"));
			System.out.println(jo.toString());
		} catch (IOException e) {
			logger.error("输出json失败," + e.getMessage(), e.getCause());
		}
	}

    /**
     * 按合同层分组，需回填入 S.TRADE_TYPE ，
     * @param request
     * @return
     */
    private String _suretyBondContractQueryHelp(HttpServletRequest request) {
        String signRegExp = "[\\~\\!\\@\\#\\$\\%\\^\\&\\*\\(\\)\\_\\+\\`\\-\\=\\～\\！\\＠\\＃\\＄\\％\\＾\\＆\\＊\\\\（\\）\\＿\\＋\\＝\\－\\｀\\[\\]\\\\'\\;\\/\\.\\,\\<\\>\\?\\:\"\\{\\}\\|\\，\\．\\／\\；\\＇\\［\\］\\＼\\＜\\＞\\？\\：\\＂\\｛\\｝\\｜\\“\\”\\‘\\’\\。\r+\n+\t+\\s\\ ]";
        StringBuffer sb = new StringBuffer();
        StringBuffer sb4Union = new StringBuffer();
        StringBuffer sbTitle = new StringBuffer(" select * ");
        sbTitle.append("        From (");
        
        sb.append("SELECT MAX(S.DEPT_ID) DEPT_ID, ");
        sb.append("       MAX(SD.DEPT_NAME) DEPT_NAME,");
        sb.append("       MAX(S.TRADE_TYPE) TRADE_TYPE,       ");
        sb.append("       MAX(TABTRADETYPE.TEXT) TRADE_TYPE_TEXT, ");
        sb.append("       MAX(T.PROJECT_NO) PROJECT_NO,");
        sb.append("       MAX(S.CONTRACT_NO) CONTRACT_NO,");
        sb.append("       SUM(S.MARGIN_RATIO) MARGIN_RATIO,");
        sb.append("       round(SUM(S.MARGIN_RATIO * S.ORDER_TOTAL / 100),2) AS SURETYBOND,");
        sb.append("       MAX(S.VBAK_WAERK) VBAK_WAERK,");
        sb.append("       SUM(S.ORDER_TOTAL) ORDER_TOTAL,");
        sb.append("       MAX(S.YMAT_GROUP) YMAT_GROUP,");
        sb.append("       MAX(MA.TITLE) TITLE,");
        sb.append("       MAX(case S.ispromise when '1' then '是' else '否' end) ISPROMISE,");
        sb.append("       MAX(S.VBKD_IHREZ) VBKD_IHREZ");
        sb.append("  FROM T_PROJECT_INFO        T, ");
        sb.append("       T_CONTRACT_SALES_INFO S ");
        sb.append("  LEFT JOIN (SELECT '1' CODE, '外贸自营进口*业务' TEXT  FROM DUAL  ");
        sb.append("             UNION ");
        sb.append("             SELECT '2' CODE, '外贸自营出口*业务' TEXT  FROM DUAL  ");
        sb.append("             UNION ");
        sb.append("             SELECT '3' CODE, '外贸自营进口业务' TEXT  FROM DUAL  ");
        sb.append("             UNION ");
        sb.append("             SELECT '4' CODE, '外贸自营出口业务' TEXT  FROM DUAL  ");
        sb.append("             UNION ");
        sb.append("             SELECT '5' CODE, '外贸代理出口业务' TEXT  FROM DUAL  ");
        sb.append("             UNION ");
        sb.append("             SELECT '6' CODE, '外贸代理进口业务' TEXT  FROM DUAL  ");
        sb.append("             UNION ");
        sb.append("             SELECT '7' CODE, '内贸业务' TEXT  FROM DUAL  ");
        sb.append("             UNION ");
        sb.append("             SELECT '8' CODE, '进料加工业务内销' TEXT  FROM DUAL  ");
        sb.append("             UNION ");
        sb.append("             SELECT '9' CODE, '自营进口敞口业务' TEXT  FROM DUAL  ");
        sb.append("             UNION ");
        sb.append("             SELECT '11' CODE, '转口业务' TEXT  FROM DUAL  ");
        sb.append("             UNION ");
        sb.append("             SELECT '12' CODE, '进料加工业务外销' TEXT  FROM DUAL  ");
        sb.append("             UNION ");
        sb.append("             SELECT '10' CODE, '内贸敞口' TEXT FROM DUAL) TABTRADETYPE ");
        sb.append("    ON (S.TRADE_TYPE = TABTRADETYPE.CODE) ");
        sb.append("  LEFT JOIN T_SYS_DEPT SD ");
        sb.append("    ON (SD.DEPT_ID = S.DEPT_ID) ");
        sb.append("  LEFT JOIN BM_MATERIAL_GROUP MA ");
        sb.append("    ON (MA.IS_AVAILABLE = '1' AND MA.ID = S.YMAT_GROUP) ");
        sb.append(" WHERE 1 = 1 ");
        sb.append("   AND S.PROJECT_ID = T.PROJECT_ID ");
        sb.append("   AND T.IS_AVAILABLE = '1' ");
        sb.append("   AND S.ORDER_STATE NOT IN ('1','2','4','13') ");

        sb4Union.append(sb.toString());
        sb4Union.append("   AND EXISTS (SELECT 'X' ");
        sb4Union.append("          FROM YCOLLECT C, YCOLLECTITEM CI ");
        sb4Union.append("         WHERE C.COLLECTID = CI.COLLECTID ");
        sb4Union.append("           AND C.INCSURETYBOND = 'Y' ");
        sb4Union.append("           AND TRIM(CI.CONTRACT_NO) IS NOT NULL ");
        sb4Union.append("           AND CI.SURETYBOND > 0 ");
        sb4Union.append("           AND C.BUSINESSSTATE <> '-1' ");
        sb4Union.append("           AND S.CONTRACT_NO = CI.CONTRACT_NO) ");
        sb.append("   AND S.MARGIN_RATIO > 0 ");
        
        String deptId = (String) request.getParameter("dept_id"); // 部门ID
        String contractNo = (String) request.getParameter("contract_no"); // 合同号
        String projectNo = (String) request.getParameter("project_no"); // 立项号
        String trade_type = (String) request.getParameter("trade_type"); // 业务类型
        String project_state = (String) request.getParameter("project_state"); // 立项状态

        String con_applyDate1 = (String) request.getParameter("con_apply_date1"); // 合同审批时间1
        String con_applyDate2 = (String) request.getParameter("con_apply_date2"); // 合同审批时间2
        con_applyDate1 = StringUtils.isNotBlank(con_applyDate1) ? con_applyDate1.replaceAll(signRegExp, "") : null;
        con_applyDate2 = StringUtils.isNotBlank(con_applyDate2) ? con_applyDate2.replaceAll(signRegExp, "") : null;

        String proj_approvalDate1 = (String) request.getParameter("pro_approval_date1"); // 审批通过时间1
        String pro_approvalDate2 = (String) request.getParameter("pro_approval_date2"); // 审批通过时间2
        proj_approvalDate1 = StringUtils.isNotBlank(proj_approvalDate1) ? proj_approvalDate1.replaceAll(signRegExp, "")
                : null;
        pro_approvalDate2 = StringUtils.isNotBlank(pro_approvalDate2) ? pro_approvalDate2.replaceAll(signRegExp, "")
                : null;
        
        StringBuffer sbWhere = new StringBuffer();
        // 部门
        if (StringUtils.isNotBlank(deptId)) {
            sbWhere.append(" AND s.dept_id like '%" + deptId + "%'");
        }
        // 业务类型
        if (StringUtils.isNotBlank(trade_type)) {
            sbWhere.append(" AND S.TRADE_TYPE in (" + trade_type + ")");
        }
        // 设置合同号条件
        if (StringUtils.isNotBlank(contractNo)) {
            sbWhere.append("AND s.contract_no LIKE '%" + contractNo + "%'");
        }
        // 设置立项号条件
        if (StringUtils.isNotBlank(projectNo)) {
            sbWhere.append(" AND t.project_no LIKE '%" + projectNo + "%'");
        }
        // 设置立项状态

        // sbWhere.append("   AND T.PROJECT_STATE IN ('2', '3', '4', '5', '6', '8') ");
        if (StringUtils.isNotBlank(project_state) && !"*".equals(project_state)) {
            sbWhere.append(" AND t.project_state in (" + project_state + ")");
        }

        // 设置合同审批审批时间
        if (StringUtils.isNotBlank(con_applyDate1) && StringUtils.isNotBlank(con_applyDate2)) {
            sbWhere.append(" AND substr(REPLACE (s.approved_time,'-', ''),0,8) BETWEEN '" + con_applyDate1 + "' AND '"
                    + con_applyDate2 + "'");
        } else if (StringUtils.isNotBlank(con_applyDate1)) {
            sbWhere.append(" AND substr(REPLACE (s.approved_time,'-', ''),0,8)> '" + con_applyDate1 + "'");
        } else if (StringUtils.isNotBlank(con_applyDate2)) {
            sbWhere.append(" AND substr(REPLACE (s.approved_time,'-', ''),0,8) < '" + con_applyDate2 + "'");
        }

        // 设置立项审批通过时间条件
        if (StringUtils.isNotBlank(proj_approvalDate1) && StringUtils.isNotBlank(pro_approvalDate2)) {
            sbWhere.append(" AND substr(REPLACE (t.approved_time,'-', ''),0,8) BETWEEN '" + proj_approvalDate1 + "' AND '"
                    + pro_approvalDate2 + "'");
        } else if (StringUtils.isNotBlank(proj_approvalDate1)) {
            sbWhere.append(" substr(REPLACE (t.approved_time,'-', ''),0,8) > '" + proj_approvalDate1 + "'");
        } else if (StringUtils.isNotBlank(pro_approvalDate2)) {
            sbWhere.append(" substr(REPLACE (t.approved_time,'-', ''),0,8) < '" + pro_approvalDate2 + "'");
        }
        sb.append(sbWhere).append(" GROUP BY S.CONTRACT_NO ");
        sb4Union.append(sbWhere).append(" GROUP BY S.CONTRACT_NO ");
        sb.append(" union ").append(sb4Union);
        return sbTitle.toString()+ sb.toString() +") s where 1=1 " ;
    }
    /**
     * 跳转保证金合同查询
     * @param request
     * @param response
     * @return
     */
    public ModelAndView _suretyBondContract(HttpServletRequest request,
            HttpServletResponse response) {
        String sdate = DateUtils.getCurrDateStr(DateUtils.HYPHEN_DISPLAY_DATE);
        String begDate =  sdate.substring(0, 4) + "-01-01"; 
        request.setAttribute("begDate", begDate);
        request.setAttribute("sdate", sdate);
        return new ModelAndView("xdss3/collect/suretyBondContract");
    }
    
    public void _suretyBondContractQuery(HttpServletRequest request,
            HttpServletResponse response, GridQueryCondition gridQueryCondition)
            throws Exception {
        BusinessObject businessObject = BusinessObjectService
                .getBusinessObject("Collect");
        String strAuthSql = "";
        try {
            AuthSql authSql = (AuthSql) Class.forName(
                    "com.infolion.xdss3.CommonDataAuthSql").newInstance();
            strAuthSql = authSql.getAuthSql(businessObject);
            // 替换权限默认的前缀表名
            strAuthSql = strAuthSql.replace("YCOLLECT", "t");
        } catch (Exception ex) {
            throw new SQLException("类不存在：" + ex.getMessage());
        }
      
       String sb = this._suretyBondContractQueryHelp(request);
        gridQueryCondition.setBoName("");
        gridQueryCondition.setTableSql("");
        gridQueryCondition.setDefaultCondition("1=1 " + strAuthSql);
        gridQueryCondition.setWhereSql("");
        gridQueryCondition.setOrderSql(" DEPT_ID, TRADE_TYPE, PROJECT_NO, CONTRACT_NO ");
        gridQueryCondition.setGroupBySql("");
        gridQueryCondition.setTableName("(" + sb + ") t");
        gridQueryCondition
                .setHandlerClass("com.infolion.xdss3.collect.domain.CollectSuretyBondContractGrid");
        String editable = "false";
        String needAuthentication = "true";
        GridData gridJsonData = this.gridService.getGridData(
                gridQueryCondition, editable, needAuthentication);
        JSONObject jsonList = JSONObject.fromObject(gridJsonData);
        try {
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(jsonList);
        } catch (IOException e) {
            logger.error("输出json失败," + e.getMessage(), e.getCause());
        }
    }

    
    /**
     * POI导出Excel
     */
    public void _suretyBondContractToExcel(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String col = ReportUtil.getProperty("suretybondcontract");
        String sql = this._suretyBondContractQueryHelp(request);
        // 部门的过滤
        UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
        sql += " and s.DEPT_ID in (" + userContext.getGrantedDepartmentsId()
                + ",";
        sql += "'" + userContext.getSysDept().getDeptid() + "')  order by  S.DEPT_ID, S.TRADE_TYPE, S.PROJECT_NO, S.CONTRACT_NO  ";
        
        String[] cols = col.split(",");
        String colName = new String(ReportUtil.getProperty("suretybondcontract_names").getBytes("ISO-8859-1"), "UTF-8");
        String[] colNames = colName.split(",");
        InputStream is = this.reportService.createExcelFile(cols, colNames, sql, null);
        ReportUtil util = new ReportUtil();
        util.download(is, "合同保证金信息汇总表_"+(DateUtils.getCurrDateStr(DateUtils.DB_STORE_DATE))+".xls", response);
        
        is.close();
    }
    
    
    private String _suretyBondContractDetailQueryHelp(HttpServletRequest request) {
        String signRegExp = "[\\~\\!\\@\\#\\$\\%\\^\\&\\*\\(\\)\\_\\+\\`\\-\\=\\～\\！\\＠\\＃\\＄\\％\\＾\\＆\\＊\\\\（\\）\\＿\\＋\\＝\\－\\｀\\[\\]\\\\'\\;\\/\\.\\,\\<\\>\\?\\:\"\\{\\}\\|\\，\\．\\／\\；\\＇\\［\\］\\＼\\＜\\＞\\？\\：\\＂\\｛\\｝\\｜\\“\\”\\‘\\’\\。\r+\n+\t+\\s\\ ]";
        StringBuffer sb = new StringBuffer();
        sb.append(" SELECT C.DEPT_ID, ");
        sb.append("        S2.TRADE_TYPE, ");
        sb.append("        SD.DEPT_NAME, ");
        sb.append("        CASE ");
        sb.append("          WHEN S2.TRADE_TYPE = '1' THEN ");
        sb.append("           '外贸自营进口*业务' ");
        sb.append("          WHEN S2.TRADE_TYPE = '2' THEN ");
        sb.append("           '外贸自营出口*业务' ");
        sb.append("          WHEN S2.TRADE_TYPE = '3' THEN ");
        sb.append("           '外贸自营进口业务' ");
        sb.append("          WHEN S2.TRADE_TYPE = '4' THEN ");
        sb.append("           '外贸自营出口业务' ");
        sb.append("          WHEN S2.TRADE_TYPE = '5' THEN ");
        sb.append("           '外贸代理出口业务' ");
        sb.append("          WHEN S2.TRADE_TYPE = '6' THEN ");
        sb.append("           '外贸代理进口业务' ");
        sb.append("          WHEN S2.TRADE_TYPE = '7' THEN ");
        sb.append("           '内贸业务' ");
        sb.append("          WHEN S2.TRADE_TYPE = '8' THEN ");
        sb.append("           '进料加工业务内销' ");
        sb.append("          WHEN S2.TRADE_TYPE = '9' THEN ");
        sb.append("           '自营进口敞口业务' ");
        sb.append("          WHEN S2.TRADE_TYPE = '10' THEN ");
        sb.append("           '内贸敞口' ");
        sb.append("          WHEN S2.TRADE_TYPE = '11' THEN ");
        sb.append("           '转口业务' ");
        sb.append("          WHEN S2.TRADE_TYPE = '12' THEN ");
        sb.append("           '进料加工业务外销' ");
        sb.append("        END AS TRADE_TYPE_TEXT, ");
        sb.append("        CI.PROJECT_NO, ");
        sb.append("        CI.CONTRACT_NO, ");
        sb.append("        S.VBKD_IHREZ, ");
        sb.append("        S.VBAK_WAERK, ");
        sb.append("        S.ORDER_TOTAL, ");
        sb.append("        S.MARGIN_RATIO, ");
        sb.append("        case S.ispromise when '1' then '是' else '否' end ISPROMISE, ");
        sb.append("        S.SURETYBOND_C, ");
        sb.append("        CI.YMAT_GROUP, ");
        sb.append("        SUBSTR(C.CREATETIME, 0, 8) CREATETIME, ");
        sb.append("        C.COLLECTNO, ");
        sb.append("        CI.SURETYBOND, ");
        sb.append("        CI.ACTSURETYBOND REMAINSURETYBOND, ");
        /**20150708 cat /剩余保证金直接取，不用取退款数据，退款审批通过后会更新收款的剩余保证金
        sb.append("  CASE ");
        sb.append("  WHEN trim(rs.PEFUNDMENTAMOUNT) IS NOT NULL THEN ");
        sb.append("  					      (CI.ACTSURETYBOND - rs.PEFUNDMENTAMOUNT) ");
        sb.append("  				 WHEN trim(rs.PEFUNDMENTAMOUNT) IS  NULL THEN ");
        sb.append("  				      CI.ACTSURETYBOND ");
        sb.append("  				END AS REMAINSURETYBOND, ");**/
        sb.append("        CASE ");
        sb.append("          WHEN C.BUSINESSSTATE = '0' THEN ");
        sb.append("           '新增' ");
        sb.append("          WHEN C.BUSINESSSTATE = '1' THEN ");
        sb.append("           '审批' ");
        sb.append("          WHEN C.BUSINESSSTATE = '2' THEN ");
        sb.append("           '过资金部' ");
        sb.append("          WHEN C.BUSINESSSTATE = '3' THEN ");
        sb.append("           '审批通过' ");
        sb.append("        END BUSINESSSTATE, ");
        sb.append("        C.CURRENCY ");
        sb.append("   FROM YCOLLECT C ");
        sb.append("   LEFT JOIN T_SYS_DEPT SD ");
        sb.append("     ON (SD.DEPT_ID = C.DEPT_ID), YCOLLECTITEM CI ");
        sb.append("   LEFT JOIN (SELECT SC.CONTRACT_NO, ");
        sb.append("                     SC.VBKD_IHREZ, ");
        sb.append("                     SC.VBAK_WAERK, ");
        sb.append("                     SC.ORDER_TOTAL, ");
        sb.append("                     SC.MARGIN_RATIO, ");
        sb.append("                     SC.ISPROMISE, ");
//        sb.append("                     SC.TRADE_TYPE, ");
        sb.append("                     ROUND(SC.MARGIN_RATIO * SC.ORDER_TOTAL / 100, 2) AS SURETYBOND_C ");
        sb.append("                FROM T_PROJECT_INFO T, T_CONTRACT_SALES_INFO SC ");
        sb.append("               WHERE SC.PROJECT_ID = T.PROJECT_ID ");
        sb.append("                 AND T.IS_AVAILABLE = '1') S ");
        sb.append("     ON (S.CONTRACT_NO = CI.CONTRACT_NO) ");
        sb.append("  LEFT JOIN (SELECT T.TRADE_TYPE,T.Project_No,T.Approved_Time,T.Is_Available,T.Project_State  FROM T_PROJECT_INFO   T WHERE T.IS_AVAILABLE = '1')	S2	 ON (S2.PROJECT_NO=CI.Project_No)	");
/**20150708 cat /剩余保证金直接取，不用取退款数据，退款审批通过后会更新收款的剩余保证金
        sb.append("LEFT JOIN (SELECT REI.PEFUNDMENTAMOUNT, REI.COLLECTITEMID ");
        sb.append("        FROM YREFUNDMENTITEM REI, YREFUNDMENT RE ");
        sb.append("        WHERE RE.REFUNDMENTID = REI.REFUNDMENTID ");
        sb.append("         AND REI.ISTYBOND = 'Y' ");
        sb.append("         AND RE.BUSINESSSTATE IN ('2', '3')) RS ON RS.COLLECTITEMID = CI.COLLECTITEMID ");
        **/
        sb.append("  WHERE 1 = 1 ");
        sb.append("    AND C.INCSURETYBOND = 'Y' ");
        sb.append("    AND CI.SURETYBOND > 0 ");
        sb.append("    AND C.COLLECTID = CI.COLLECTID ");
        sb.append("    AND C.BUSINESSSTATE <> '-1' ");
        
        String deptId = (String) request.getParameter("dept_id"); // 部门ID
        String contractNo = (String) request.getParameter("contract_no"); // 合同号
        String projectNo = (String) request.getParameter("project_no"); // 立项号
        String trade_type = (String) request.getParameter("trade_type"); // 业务类型
        String project_state = (String) request.getParameter("project_state"); // 立项状态
        String businessstate = (String) request.getParameter("businessstate"); // 业务状态
        
        String con_applyDate1 = (String) request.getParameter("con_apply_date1"); // 收款创建时间1
        String con_applyDate2 = (String) request.getParameter("con_apply_date2"); // 收款创建时间2
        con_applyDate1 = StringUtils.isNotBlank(con_applyDate1) ? con_applyDate1.replaceAll(signRegExp, "") : null;
        con_applyDate2 = StringUtils.isNotBlank(con_applyDate2) ? con_applyDate2.replaceAll(signRegExp, "") : null;
        
        String proj_approvalDate1 = (String) request.getParameter("pro_approval_date1"); // 审批通过时间1
        String pro_approvalDate2 = (String) request.getParameter("pro_approval_date2"); // 审批通过时间2
        proj_approvalDate1 = StringUtils.isNotBlank(proj_approvalDate1) ? proj_approvalDate1.replaceAll(signRegExp, "")
                : null;
        pro_approvalDate2 = StringUtils.isNotBlank(pro_approvalDate2) ? pro_approvalDate2.replaceAll(signRegExp, "")
                : null;
        
        // 部门
        if (StringUtils.isNotBlank(deptId)) {
            sb.append(" AND c.dept_id like '%" + deptId + "%'");
        }
        // 业务类型
        if (StringUtils.isNotBlank(trade_type)) {
            sb.append(" AND S2.TRADE_TYPE in (" + trade_type + ")");
        }
        // 设置合同号条件
        if (StringUtils.isNotBlank(contractNo)) {
            sb.append(" AND ci.contract_no LIKE '%" + contractNo + "%'");
        }
        // 设置立项号条件
        if (StringUtils.isNotBlank(projectNo)) {
            sb.append(" AND ci.project_no LIKE '%" + projectNo + "%'");
        }
        // 设置立项状态
        
        // sb.append("   AND T.PROJECT_STATE IN ('2', '3', '4', '5', '6', '8') ");
        if (StringUtils.isNotBlank(project_state) && !"*".equals(project_state)) {
            sb.append(" AND s2.project_state in (" + project_state + ")");
        }
        
        if (StringUtils.isNotBlank(businessstate) && !"*".equals(businessstate)) {
            sb.append(" AND c.businessstate in (" + businessstate + ")");
        }
        
        // 设置合同审批审批时间
        if (StringUtils.isNotBlank(con_applyDate1) && StringUtils.isNotBlank(con_applyDate2)) {
            sb.append(" AND substr(REPLACE (c.createtime,'-', ''),0,8) BETWEEN '" + con_applyDate1 + "' AND '"
                    + con_applyDate2 + "'");
        } else if (StringUtils.isNotBlank(con_applyDate1)) {
            sb.append(" AND substr(REPLACE (c.createtime,'-', ''),0,8)> '" + con_applyDate1 + "'");
        } else if (StringUtils.isNotBlank(con_applyDate2)) {
            sb.append(" AND substr(REPLACE (c.createtime,'-', ''),0,8) < '" + con_applyDate2 + "'");
        }
        
        // 设置立项审批通过时间条件
        if (StringUtils.isNotBlank(proj_approvalDate1) && StringUtils.isNotBlank(pro_approvalDate2)) {
            sb.append(" AND substr(REPLACE (s2.approved_time,'-', ''),0,8) BETWEEN '" + proj_approvalDate1 + "' AND '"
                    + pro_approvalDate2 + "'");
        } else if (StringUtils.isNotBlank(proj_approvalDate1)) {
            sb.append("substr(REPLACE (s2.approved_time,'-', ''),0,8) > '" + proj_approvalDate1 + "'");
        } else if (StringUtils.isNotBlank(pro_approvalDate2)) {
            sb.append("substr(REPLACE (s2.approved_time,'-', ''),0,8) < '" + pro_approvalDate2 + "'");
        }
        return sb.toString();
    }
    /**
     * 跳转保证金合同查询
     * @param request
     * @param response
     * @return
     */
    public ModelAndView _suretyBondContractDetail(HttpServletRequest request,
            HttpServletResponse response) {
        String sdate = DateUtils.getCurrDateStr(DateUtils.HYPHEN_DISPLAY_DATE);
        String begDate =  sdate.substring(0, 4) + "-01-01"; 
        request.setAttribute("begDate", begDate);
        request.setAttribute("sdate", sdate);
        return new ModelAndView("xdss3/collect/suretyBondContractDetail");
    }
    
    public void _suretyBondContractDetailQuery(HttpServletRequest request,
            HttpServletResponse response, GridQueryCondition gridQueryCondition)
    throws Exception {
        BusinessObject businessObject = BusinessObjectService
        .getBusinessObject("Collect");
        String strAuthSql = "";
        try {
            AuthSql authSql = (AuthSql) Class.forName(
            "com.infolion.xdss3.CommonDataAuthSql").newInstance();
            strAuthSql = authSql.getAuthSql(businessObject);
            // 替换权限默认的前缀表名
            strAuthSql = strAuthSql.replace("YCOLLECT", "t");
        } catch (Exception ex) {
            throw new SQLException("类不存在：" + ex.getMessage());
        }
        
        String sb = this._suretyBondContractDetailQueryHelp(request);
        
        gridQueryCondition.setBoName("");
        gridQueryCondition.setTableSql("");
        gridQueryCondition.setDefaultCondition("1=1 " + strAuthSql);
        gridQueryCondition.setWhereSql("");
        gridQueryCondition.setOrderSql(" DEPT_ID, TRADE_TYPE, PROJECT_NO, CONTRACT_NO ");
        gridQueryCondition.setGroupBySql("");
        gridQueryCondition.setTableName("(" + sb + ") t");
        gridQueryCondition
        .setHandlerClass("com.infolion.xdss3.collect.domain.CollectSuretyBondContractDetailGrid");
        String editable = "false";
        String needAuthentication = "true";
        GridData gridJsonData = this.gridService.getGridData(
                gridQueryCondition, editable, needAuthentication);
        JSONObject jsonList = JSONObject.fromObject(gridJsonData);
        try {
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(jsonList);
        } catch (IOException e) {
            logger.error("输出json失败," + e.getMessage(), e.getCause());
        }
    }
    
    
    /**
     * POI导出Excel
     */
    public void _suretyBondContractDetailToExcel(HttpServletRequest request, HttpServletResponse response)
    throws IOException {
        String col = ReportUtil.getProperty("suretybondcontractdetail");
        String sql = this._suretyBondContractDetailQueryHelp(request);
        // 部门的过滤
        UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
        sql += " and C.DEPT_ID in (" + userContext.getGrantedDepartmentsId()
        + ",";
        sql += "'" + userContext.getSysDept().getDeptid() + "') order by  C.DEPT_ID, S2.TRADE_TYPE, CI.PROJECT_NO, S.CONTRACT_NO  ";
        
        String[] cols = col.split(",");
        String colName = new String(ReportUtil.getProperty("suretybondcontractdetail_names").getBytes("ISO-8859-1"), "UTF-8");
        String[] colNames = colName.split(",");
        InputStream is = this.reportService.createExcelFile(cols, colNames, sql, null);
        ReportUtil util = new ReportUtil();
        util.download(is, "保证金实际执行情况汇总表_"+(DateUtils.getCurrDateStr(DateUtils.DB_STORE_DATE))+".xls", response);
        
        is.close();
    }
    public void _saveDate(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String collectid = request.getParameter("collectid");
		String sendgoodsdate = request.getParameter("sendgoodsdate");
		String billcheckdate = request.getParameter("billcheckdate");
		Collect collect = collectService.getCollectByCollectId(collectid);
		String oldStr = "sendgoodsdate:"+collect.getSendgoodsdate()+"|billcheckdate:"+collect.getBillcheckdate();
		collect.setSendgoodsdate(sendgoodsdate);
		collect.setBillcheckdate(billcheckdate);
		String newStr = "sendgoodsdate:"+collect.getSendgoodsdate()+"|billcheckdate:"+collect.getBillcheckdate();
		collectService.updateDate(collect,oldStr,newStr,UserContextHolder.getLocalUserContext().getUserContext().getSysUser().getUserId());
		JSONObject jo = new JSONObject();
		jo.put("returnMessage", "更新成功");		
		String jsontext = jo.toString();
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		this.operateSuccessfullyWithString(response, jsontext);
		
	}
    
    public void checkClearData(HttpServletRequest request,
			HttpServletResponse response) {
		JSONObject jo = new JSONObject();
		// 绑定主对象值
		Collect collect = (Collect) ExBeanUtils.bindBusinessObjectData(request
				.getParameterMap(), Collect.class, true, request.getMethod(),
				true);
		// 执行凭证预览操作前先调用以下这个存储过程
		// this.collectService.updateCustomerTitle(collect.getCustomer());
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<CollectItem> collectitemmodifyItems = ExBeanUtils
				.bindModifySubBoData(request.getParameterMap(),
						new Object[] { collect }, CollectItem.class, null);
		collect.setCollectitem(collectitemmodifyItems);
		Set<CollectItem> collectitemdeleteItems = ExBeanUtils
				.bindDeleteSubBoData(request.getParameterMap(),
						new Object[] { collect }, CollectItem.class, null);
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<CollectCbill> collectcbillmodifyItems = ExBeanUtils
				.bindModifySubBoData(request.getParameterMap(),
						new Object[] { collect }, CollectCbill.class, null);
		collect.setCollectcbill(collectcbillmodifyItems);
		Set<CollectCbill> collectcbilldeleteItems = ExBeanUtils
				.bindDeleteSubBoData(request.getParameterMap(),
						new Object[] { collect }, CollectCbill.class, null);
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<CollectRelated> collectrelatedmodifyItems = ExBeanUtils
				.bindModifySubBoData(request.getParameterMap(),
						new Object[] { collect }, CollectRelated.class, null);
		collect.setCollectrelated(collectrelatedmodifyItems);
		Set<CollectRelated> collectrelateddeleteItems = ExBeanUtils
				.bindDeleteSubBoData(request.getParameterMap(),
						new Object[] { collect }, CollectRelated.class, null);
		// 绑定子对象(一对一关系)
		SettleSubject settleSubject = (SettleSubject) ExBeanUtils
				.bindBusinessObjectData(request.getParameterMap(),
						SettleSubject.class, false, request.getMethod(), true);
		if (settleSubject != null) {
			if (null == settleSubject.getFlag()
					|| "".equals(settleSubject.getFlag().trim())) {
				settleSubject.setFlag("0");
			}
			collect.setSettleSubject(settleSubject);
			settleSubject.setCollect(collect);
		}
		// 绑定子对象(一对一关系)
		FundFlow fundFlow = (FundFlow) ExBeanUtils.bindBusinessObjectData(
				request.getParameterMap(), FundFlow.class, false, request
						.getMethod(), true);
		if (fundFlow != null) {
			if (null == fundFlow.getFlag()
					|| "".equals(fundFlow.getFlag().trim())) {
				fundFlow.setFlag("0");
			}
			collect.setFundFlow(fundFlow);
			fundFlow.setCollect(collect);
		}
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<CollectBankItem> collectbankitemmodifyItems = ExBeanUtils
				.bindModifySubBoData(request.getParameterMap(),
						new Object[] { collect }, CollectBankItem.class, null);
		collect.setCollectbankitem(collectbankitemmodifyItems);
		Set<CollectBankItem> collectbankitemdeleteItems = ExBeanUtils
				.bindDeleteSubBoData(request.getParameterMap(),
						new Object[] { collect }, CollectBankItem.class, null);
		
//		如果有清票时
		if(null !=collect.getCollectcbill()){			
			BigDecimal marginAmount = new BigDecimal("0");
			boolean isSave = false;
			if(!StringUtils.isNullBlank(collect.getCollectid())){
				isSave=true;				
			}
			IInfo info =customerClearAccountService.checkClearData(collect, marginAmount,isSave);
			jo.put("isRight", info.isRight());
			jo.put("info", info.getInfo());
			this.operateSuccessfullyHiddenInfoWithString(response, jo.toString());
		}else{
			jo.put("isRight", true);			
			this.operateSuccessfullyHiddenInfoWithString(response, jo.toString());
		}
		
    }
    /**
     * 取得立项的业务类型，，钟志华，20140122
     * @param request
     * @param response
     */
    public void getTradeTypeByProjectno(HttpServletRequest request,
			HttpServletResponse response) {
		JSONObject jsonObj = new JSONObject();
		String projectno = request.getParameter("projectno");
		String tradeType = this.collectService.getTradeTypeByProjectno(projectno);
		jsonObj.put("tradeType", tradeType);
		this.operateSuccessfullyWithString(response, jsonObj.toString());
	}
    
}