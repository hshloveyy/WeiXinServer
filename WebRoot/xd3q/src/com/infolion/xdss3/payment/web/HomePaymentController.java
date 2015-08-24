/*
 * @(#)HomePaymentController.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年07月19日 10点44分13秒
 *  描　述：创建
 */
package com.infolion.xdss3.payment.web;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
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

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

import com.infolion.platform.bo.domain.BusinessObject;
import com.infolion.platform.bo.domain.Property;
import com.infolion.platform.bo.service.BusinessObjectService;
import com.infolion.platform.console.org.domain.SysRole;
import com.infolion.platform.console.sys.context.UserContext;
import com.infolion.platform.console.sys.context.UserContextHolder;
import com.infolion.platform.dpframework.core.BusinessException;
import com.infolion.platform.dpframework.core.annotation.BDPController;
import com.infolion.platform.dpframework.uicomponent.grid.GridService;
import com.infolion.platform.dpframework.uicomponent.grid.data.AuthSql;
import com.infolion.platform.dpframework.uicomponent.grid.data.GridData;
import com.infolion.platform.dpframework.uicomponent.grid.data.GridQueryCondition;
import com.infolion.platform.dpframework.uicomponent.searchHelp.service.SearchHelpService;
import com.infolion.platform.dpframework.util.ExBeanUtils;
import com.infolion.platform.dpframework.util.StringUtils;
import com.infolion.platform.util.DateUtils;
import com.infolion.platform.util.MultyGridData;
import com.infolion.platform.workflow.engine.WorkflowService;
import com.infolion.platform.workflow.engine.dao.ProcessJdbcDao;
import com.infolion.platform.workflow.engine.domain.ExtendTaskInstance;
import com.infolion.sapss.common.ExcelObject;
import com.infolion.sapss.payment.PaymentContants;
import com.infolion.sapss.payment.service.PaymentInnerInfoJDBCService;
import com.infolion.sapss.ship.service.CheckCustomerSendCreditSerice;
import com.infolion.xdss3.BusinessState;
import com.infolion.xdss3.CommonDataAuth;
import com.infolion.xdss3.UpdateState;
import com.infolion.xdss3.masterData.dao.VendorTitleJdbcDao;
import com.infolion.xdss3.masterData.domain.VendorTitle;
import com.infolion.xdss3.masterData.service.SupplierService;
import com.infolion.xdss3.payment.domain.HomeDocuBankItem;
import com.infolion.xdss3.payment.domain.HomeFundFlow;
import com.infolion.xdss3.payment.domain.HomePayBankItem;
import com.infolion.xdss3.payment.domain.HomePayment;
import com.infolion.xdss3.payment.domain.HomePaymentCbill;
import com.infolion.xdss3.payment.domain.HomePaymentItem;
import com.infolion.xdss3.payment.domain.HomePaymentRelat;
import com.infolion.xdss3.payment.domain.HomeSettSubj;
import com.infolion.xdss3.payment.service.HomePaymentCbillService;
import com.infolion.xdss3.payment.service.HomePaymentService;
import com.infolion.xdss3.payment.service.PaymentItemService;
import com.infolion.xdss3.paymentGen.web.HomePaymentControllerGen;
import com.infolion.xdss3.reassign.ReassignConstant;
import com.infolion.xdss3.reassign.dao.ReassignJdbcDao;
import com.infolion.xdss3.reassign.domain.Reassign;
import com.infolion.xdss3.reassign.service.ReassignService;
import com.infolion.xdss3.singleClear.domain.ClearConstant;
import com.infolion.xdss3.singleClear.domain.IInfo;
import com.infolion.xdss3.singleClear.domain.IInfoVoucher;
import com.infolion.xdss3.singleClear.domain.ParameterVoucherObject;
import com.infolion.xdss3.singleClear.service.SupplierClearAccountService;
import com.infolion.xdss3.voucher.domain.Voucher;
import com.infolion.xdss3.voucher.service.VoucherService;

/**
 * <pre>
 * 内贸付款(HomePayment)控制器类
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
public class HomePaymentController extends HomePaymentControllerGen {

    
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
	private CheckCustomerSendCreditSerice checkCustomerSendCreditSerice;

	public void setCheckCustomerSendCreditSerice(CheckCustomerSendCreditSerice checkCustomerSendCreditSerice)
	{
		this.checkCustomerSendCreditSerice = checkCustomerSendCreditSerice;
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
	 * 更新未清数据
	 * @param request
	 * @param response
	 */
	public void updateVendorTitle(HttpServletRequest request,
			HttpServletResponse response){
		String supplier = request.getParameter("supplier");
		String paymentid = request.getParameter("paymentid");
//		UpdateState.getMap().put(supplier,"supplier");
//		try{
//			this.homePaymentService.updateVendorTitle2(supplier,paymentid);
//		}catch(Exception ex){
//			UpdateState.getMap().remove(supplier);
//		}finally{
//			UpdateState.getMap().remove(supplier);
//		}
//		UpdateState.getMap().remove(supplier);
		
		// 供应商未清抬头数据中的数据，判断是否已结清		
		IInfoVoucher infoVoucher = (IInfoVoucher)request.getSession().getAttribute(paymentid);
//		是否全清
		if(infoVoucher.isClear()){
			supplierClearAccountService.updateIsClear(paymentid);
		}else{
			supplierClearAccountService.updateIsClear(infoVoucher);
		}		
		request.getSession().removeAttribute(paymentid);
		
	}

	/**
	 * 取得状态,在调用存储过程中，可能出现时间差，通过这个来判断存储过程是否执行完。有返回值，说明还在执行，不能模拟凭证
	 * @param request
	 * @param response
	 */
	public void getUpdateState(HttpServletRequest request,
			HttpServletResponse response) {
		JSONObject jo = new JSONObject();
		String supplier = request.getParameter("supplier");
		String cus=UpdateState.getMap().get(supplier);
		if(!StringUtils.isNullBlank(cus)){
			jo.put("supplier", supplier);
		}else{
			jo.put("supplier", "");
		}
		
		try
		{
			response.setContentType("text/html;charset=UTF-8");
			response.getWriter().print(jo.get("supplier"));
			System.out.println(jo.toString());
		}
		catch (IOException e)
		{
			logger.error("输出json失败," + e.getMessage(), e.getCause());
		}
	}
	
	/**
	 * 模拟凭证
	 * 
	 * @param request
	 * @param response
	 */
	public void _simulate(HttpServletRequest request,
			HttpServletResponse response) {
		JSONObject jo = new JSONObject();
		// 绑定主对象值
		HomePayment homePayment = (HomePayment) ExBeanUtils
				.bindBusinessObjectData(request.getParameterMap(),
						HomePayment.class, true, request.getMethod(), true);
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<HomePaymentItem> homePaymentItemmodifyItems = ExBeanUtils
				.bindModifySubBoData(request.getParameterMap(),
						new Object[] { homePayment }, HomePaymentItem.class,
						null);
		homePayment.setHomePaymentItem(homePaymentItemmodifyItems);
		Set<HomePaymentItem> homePaymentItemdeleteItems = ExBeanUtils
				.bindDeleteSubBoData(request.getParameterMap(),
						new Object[] { homePayment }, HomePaymentItem.class,
						null);
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<HomePaymentCbill> homePaymentCbillmodifyItems = ExBeanUtils
				.bindModifySubBoData(request.getParameterMap(),
						new Object[] { homePayment }, HomePaymentCbill.class,
						null);
		homePayment.setHomePaymentCbill(homePaymentCbillmodifyItems);
		Set<HomePaymentCbill> homePaymentCbilldeleteItems = ExBeanUtils
				.bindDeleteSubBoData(request.getParameterMap(),
						new Object[] { homePayment }, HomePaymentCbill.class,
						null);
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<HomePayBankItem> homePayBankItemmodifyItems = ExBeanUtils
				.bindModifySubBoData(request.getParameterMap(),
						new Object[] { homePayment }, HomePayBankItem.class,
						null);
		homePayment.setHomePayBankItem(homePayBankItemmodifyItems);
		Set<HomePayBankItem> homePayBankItemdeleteItems = ExBeanUtils
				.bindDeleteSubBoData(request.getParameterMap(),
						new Object[] { homePayment }, HomePayBankItem.class,
						null);
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<HomeDocuBankItem> homeDocuBankItemmodifyItems = ExBeanUtils
				.bindModifySubBoData(request.getParameterMap(),
						new Object[] { homePayment }, HomeDocuBankItem.class,
						null);
		homePayment.setHomeDocuBankItem(homeDocuBankItemmodifyItems);
		Set<HomeDocuBankItem> homeDocuBankItemdeleteItems = ExBeanUtils
				.bindDeleteSubBoData(request.getParameterMap(),
						new Object[] { homePayment }, HomeDocuBankItem.class,
						null);
		// 绑定子对象(一对一关系)
		HomeSettSubj homeSettSubj = (HomeSettSubj) ExBeanUtils
				.bindBusinessObjectData(request.getParameterMap(),
						HomeSettSubj.class, false, request.getMethod(), true);
		if (homeSettSubj != null) {
			homePayment.setHomeSettSubj(homeSettSubj);
			homeSettSubj.setHomePayment(homePayment);
		}
		// 绑定子对象(一对一关系)
		HomeFundFlow homeFundFlow = (HomeFundFlow) ExBeanUtils
				.bindBusinessObjectData(request.getParameterMap(),
						HomeFundFlow.class, false, request.getMethod(), true);
		if (homeFundFlow != null) {
			homePayment.setHomeFundFlow(homeFundFlow);
			homeFundFlow.setHomePayment(homePayment);
		}
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<HomePaymentRelat> homePaymentRelatmodifyItems = ExBeanUtils
				.bindModifySubBoData(request.getParameterMap(),
						new Object[] { homePayment }, HomePaymentRelat.class,
						null);
		homePayment.setHomePaymentRelat(homePaymentRelatmodifyItems);
		Set<HomePaymentRelat> homePaymentRelatdeleteItems = ExBeanUtils
				.bindDeleteSubBoData(request.getParameterMap(),
						new Object[] { homePayment }, HomePaymentRelat.class,
						null);
		this.homePaymentService._saveOrUpdate(homePayment,
				homePaymentItemdeleteItems, homePaymentCbilldeleteItems,
				homePayBankItemdeleteItems, homeDocuBankItemdeleteItems,
				homePaymentRelatdeleteItems, getBusinessObject());

		String strSimulateType = request.getParameter("simulatetype");

		List<String> retList = new ArrayList<String>();

		HomePayment _homePayment = this.homePaymentService._get(homePayment.getPaymentid());

		//更新状态
//		this.homePaymentService.updateVendorTitle(homePayment.getSupplier());
		this.voucherService.deleteVoucherByBusinessid(homePayment.getPaymentid());
		// 人民币清帐
		if (strSimulateType.equals("1") == true) {
			retList = this.homePaymentCbillService.cnySaveVoucher(_homePayment);			
			// 判断是否需要删除
			this.voucherService.judgeVoucherNeedDel(_homePayment.getPaymentid());
			//付款清票有票才清账
			if(_homePayment.getHomePaymentCbill() != null && _homePayment.getHomePaymentCbill().size() !=0){
			//*************处理清帐凭证*****************************
//				Voucher voucher = this.voucherService.getVoucher(_homePayment.getPaymentid(), "02", "1");
//				this.homePaymentCbillService.dealClearAccountVoucher(_homePayment,voucher,"9");
			}
		}

		// 应付票据
		if (strSimulateType.equals("2") == true) {
			retList = this.homePaymentCbillService.dealBillSaveVoucher(_homePayment);
			// 判断是否需要删除
//			this.voucherService.judgeVoucherNeedDel(_homePayment.getPaymentid());
//			Voucher voucher = this.voucherService.getVoucher(_homePayment.getPaymentid(), "02", "1");
//			this.homePaymentCbillService.dealClearAccountVoucher(homePayment,voucher,"9");
		}

		// 应付票据清帐
		if (strSimulateType.equals("3") == true) {
			retList = this.homePaymentCbillService.dealBillClearSaveVoucher(_homePayment);
		}

		// 背书
		if (strSimulateType.equals("4") == true) {
			retList = this.homePaymentCbillService.repeatLessonSaveVoucher(_homePayment);
			// 判断是否需要删除
			this.voucherService.judgeVoucherNeedDel(_homePayment.getPaymentid());
			Voucher voucher = this.voucherService.getVoucher(_homePayment.getPaymentid(), "02", "1");
			Voucher customerClearVoucher = homePaymentCbillService.getRepeatLessonClearVoucher(homePayment,"9",voucher,"N");
			retList.add(customerClearVoucher.getVoucherid());
//			this.homePaymentCbillService.dealClearAccountVoucher(homePayment,voucher,"1");
		}


		
//		生成清账凭证，zzh
		if ("1".equals(strSimulateType) || "2".equals(strSimulateType) || "4".equals(strSimulateType)) {
//			如果有清票时
			if(null !=_homePayment.getHomePaymentCbill() && _homePayment.getHomePaymentCbill().size()!=0){		
				ParameterVoucherObject parameterVoucher =supplierClearAccountService.setParameter(_homePayment);
				BigDecimal marginAmount = new BigDecimal("0");
				Voucher settleSubjectVoucher = null;
				
//				if (_homePayment.getHomeSettSubj() != null || _homePayment.getHomeFundFlow() != null){			
//					settleSubjectVoucher = supplierClearAccountService.saveSettleSubjectVoucher(parameterVoucher);				
//					marginAmount = supplierClearAccountService.getMargin(settleSubjectVoucher.getVoucherItem());
////					客户跟供应商借贷方相反
//					marginAmount = new BigDecimal("0").subtract(marginAmount);
//					parameterVoucher.setVoucherid(settleSubjectVoucher.getVoucherid());
//				}
				//判断是否需要删除
				this.voucherService.judgeVoucherNeedDel(_homePayment.getPaymentid());
				boolean isSave = false;
				if(!StringUtils.isNullBlank(_homePayment.getPaymentid()))isSave=true;
				IInfo info =supplierClearAccountService.checkClearData(_homePayment, marginAmount,isSave);
//				判断本次数据是否正确
				if(info.isRight()){
					IInfoVoucher infoVoucher = supplierClearAccountService.isClearAccount(_homePayment, marginAmount);
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
							Voucher clearVoucher =supplierClearAccountService.saveClearVoucherBySupplier(parameterVoucher,infoVoucher,_homePayment.getPaymentid(),ClearConstant.PAYMENT_TYPE,isp);
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
						request.getSession().setAttribute(_homePayment.getPaymentid(), infoVoucher);
						//判断是否需要删除
						this.voucherService.judgeVoucherNeedDel(_homePayment.getPaymentid());						
						try {
							response.setContentType("text/html;charset=UTF-8");
							response.getWriter().print(_homePayment.getPaymentid());
							System.out.println(_homePayment.getPaymentid());
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
		
		// 判断是否需要删除
		this.voucherService.judgeVoucherNeedDel(_homePayment.getPaymentid());

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
		} catch (IOException e) {
			logger.error("输出json失败," + e.getMessage(), e.getCause());
		}
		
	}

	/**
	 * 自动分配
	 * 
	 * @param request
	 * @param response
	 */
	public void _autoassign(HttpServletRequest request,
			HttpServletResponse response) {
		String strContractList = request.getParameter("contractList");
		String strProjectList = request.getParameter("projectList");
		String lifnr = request.getParameter("lifnr");

		List<HomePaymentCbill> homePaymentCbillList = new ArrayList<HomePaymentCbill>();
		List<VendorTitle> cntractVendorTitleList = new ArrayList<VendorTitle>();
		List<VendorTitle> projectVendorTitleList = new ArrayList<VendorTitle>();

		if (strContractList != null && !"".equals(strContractList)) {
			cntractVendorTitleList = this.homePaymentCbillService.queryVendorByContract(strContractList);
		}
		if (strProjectList != null && !"".equals(strProjectList)) {
			projectVendorTitleList = this.homePaymentCbillService.queryVendorByProject(strProjectList, lifnr);
		}

		homePaymentCbillList = this.homePaymentCbillService.dealPaymentCbillList(cntractVendorTitleList, projectVendorTitleList);
		Collections.sort(homePaymentCbillList,new DateComparator());
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
		HomePayment homePayment = new HomePayment();

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
		homePayment.setDept_id(userContext.getSysDept().getDeptid());
		homePayment.setPay_type("3");

		com.infolion.platform.console.sys.context.UserContext xdssUserContext = com.infolion.platform.console.sys.context.UserContextHolder
				.getLocalUserContext().getUserContext();
		if (null != xdssUserContext)
			homePayment.setDept_id(xdssUserContext.getSysDept().getDeptid());

		BusinessObject boHomePayment = getBusinessObject();
		
		String workflowTaskId = request.getParameter("workflowTaskId");
		String workflowNodeDefId = request.getParameter("workflowNodeDefId");
		request.setAttribute("vt", boHomePayment.getViewText());

		request.setAttribute("homePayment", homePayment);
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
		
		List<BusinessObject> subBos = boHomePayment.getSubBusinessObject("HomeSettSubj,HomeFundFlow");
		if (null != subBos) {
			// 取得子业务对象
			for (BusinessObject bo : subBos) {
				request.setAttribute(bo.getBeanAttribute().getBeanInstanceName() + "Vt", bo.getViewText());
			}
		}
		
		return new ModelAndView("xdss3/payment/homePaymentAdd");
	}

	/**
	 * 编辑
	 * 
	 * @param request
	 * @param response
	 */
	public ModelAndView _edityw(HttpServletRequest request,
			HttpServletResponse response) {
		HomePayment homePayment = new HomePayment();
		String id = request.getParameter("paymentid");
		String workflowTaskId = request.getParameter("workflowTaskId");
		String workflowNodeDefId = request.getParameter("workflowNodeDefId");
		String businessId = request.getParameter("businessId");
		if (null == id)
			id = businessId;

		if (StringUtils.isNullBlank(id)) {
			homePayment = this.homePaymentService._getForEdit(id);
		} else {
			homePayment = this.homePaymentService._getForEdit(id);
		}

		BusinessObject boHomePayment = getBusinessObject();
		
		String processstate = homePayment.getProcessstate();
		if (processstate.equals("资金部确认银行承兑汇票或国内信用证到期付款")||processstate.equals("确认银行承兑汇票或国内信用证到期付款")) {// 清空"付款结算科目"和"付款纯资金"两个页签数据
			homePayment.setHomeSettSubj(null);
			homePayment.setHomeFundFlow(null);
			this.homePaymentService.deleteSettleFund(homePayment.getPaymentid());
		}

		boolean haveBindWorkFlow = WorkflowService.getHaveBindWorkFlow("000000000261");
		request.setAttribute("businessId", id);
		request.setAttribute("workflowTaskId", workflowTaskId);
		request.setAttribute("workflowNodeDefId", workflowNodeDefId);
		request.setAttribute("haveBindWorkFlow", haveBindWorkFlow);
		request.setAttribute("vt", boHomePayment.getViewText());

		request.setAttribute("homePayment", homePayment);

		UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
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
		
		List<BusinessObject> subBos = boHomePayment.getSubBusinessObject("HomeSettSubj,HomeFundFlow");
		
		if (null != subBos) {
			// 取得子业务对象
			for (BusinessObject bo : subBos) {
				request.setAttribute(bo.getBeanAttribute().getBeanInstanceName() + "Vt", bo.getViewText());
			}
		}
		return new ModelAndView("xdss3/payment/homePaymentywEdit");
	}

	/**
	 * 编辑
	 * 
	 * @param request
	 * @param response
	 */
	public ModelAndView _edit(HttpServletRequest request,
			HttpServletResponse response) {
		HomePayment homePayment = new HomePayment();
		String id = request.getParameter("paymentid");
		String workflowTaskId = request.getParameter("workflowTaskId");
		String workflowNodeDefId = request.getParameter("workflowNodeDefId");
		String businessId = request.getParameter("businessId");
		if (null == id)
			id = businessId;

		if (StringUtils.isNullBlank(id)) {
			homePayment = this.homePaymentService._getForEdit(id);
		} else {
			homePayment = this.homePaymentService._getForEdit(id);
		}

		BusinessObject boHomePayment = getBusinessObject();
		
		String processstate = homePayment.getProcessstate();
		if (processstate.equals("资金部确认银行承兑汇票或国内信用证到期付款")||processstate.equals("确认银行承兑汇票或国内信用证到期付款")) {// 清空"付款结算科目"和"付款纯资金"两个页签数据
			homePayment.setHomeSettSubj(null);
			homePayment.setHomeFundFlow(null);
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
		
		request.setAttribute("homePayment", homePayment);

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
				
				if(subBo.getBoName().equals("homeSettSubj")||subBo.getBoName().equals("homeFundFlow")){
					Set<Property> subproperties = subBo.getProperties();
					for (Iterator<Property> subiter = subproperties.iterator(); subiter.hasNext();) {
						Property subproperty = (Property) subiter.next();
						if (!StringUtils.isNullBlank(subproperty.getSearchHelp())) {
							String value = "";
							if (subBo.getBoName().equals("homeSettSubj")) {
								value = com.infolion.platform.dpframework.util.BeanUtils.getPropertyValue(homePayment.getHomeSettSubj(), subproperty.getPropertyName());
							}
							if (subBo.getBoName().equals("homeFundFlow")) {
								value = com.infolion.platform.dpframework.util.BeanUtils.getPropertyValue(homePayment.getHomeFundFlow(), subproperty.getPropertyName());
							}
							String shlpData = this.searchHelpService.getShlpDataByBoName(subproperty, value);

							request.setAttribute(subBo.getBoName() + subproperty.getPropertyName(), shlpData);
						}
					}
				}
				
			}
		}
		
		
		return new ModelAndView("xdss3/payment/homePaymentEdit");
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

		return new ModelAndView("xdss3/payment/homePaymentManage");
	}

	public ModelAndView _print(HttpServletRequest request,
			HttpServletResponse response) {
		String paymentId = request.getParameter("paymentId");
		HomePayment homePayment = this.homePaymentService
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
		request.setAttribute("printType", request.getParameter("printType"));
		return new ModelAndView("xdss3/payment/homePaymentPrint");
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
		HomePayment homePayment;
		String boid = this.reassignJdbcDao.isCopyed(id,
				ReassignConstant.PAYMENT);
		if (boid != null) {
			homePayment = this.homePaymentService.getHomePaymentById(boid);
		} else {
			homePayment = this.homePaymentService._getEntityCopy(id);
			request.setAttribute("isCreateCopy", "true");
			homePayment.setRepaymentid(id); // 设置被重分配退款ID
			homePayment.setBusinessstate(BusinessState.REASSIGNED); // 设置业务状态为重分配
			homePayment.setText(reassign.getText()); // 设置文本为重分配提交时填写文本

			/**
			 * 重新计算金额
			 */
			Set<HomePaymentCbill> newHomePaymentCbillSet = null;
			Set<HomePaymentCbill> homePaymentCbillSet = homePayment
					.getHomePaymentCbill();
			if (homePaymentCbillSet != null) {
				newHomePaymentCbillSet = new HashSet<HomePaymentCbill>();
				Iterator<HomePaymentCbill> it = homePaymentCbillSet.iterator();
				while (it.hasNext()) {
					HomePaymentCbill newHomePaymentCbill = it.next();

					// 已付金额
					BigDecimal Paidamount = this.vendorTitleJdbcDao
							.getSumClearAmount(newHomePaymentCbill.getBillno(),
									BusinessState.ALL_SUBMITPASS);
					newHomePaymentCbill.setPaidamount(Paidamount);

					// 未付金额
					BigDecimal Unpaidamount = newHomePaymentCbill
							.getPayableamount().subtract(Paidamount);
					newHomePaymentCbill.setUnpaidamount(Unpaidamount);

					// 在途金额
					BigDecimal Onroadamount = this.vendorTitleJdbcDao
							.getSumClearAmount(newHomePaymentCbill.getBillno(),
									BusinessState.ALL_ONROAD);
					newHomePaymentCbill.setOnroadamount(Onroadamount);

					newHomePaymentCbillSet.add(newHomePaymentCbill);
				}
			}
			homePayment.setHomePaymentCbill(newHomePaymentCbillSet);

		}

		request.setAttribute("homePayment", homePayment);
		request.setAttribute("vt", getBusinessObject().getViewText());
		if (null != getBusinessObject().getSubBusinessObject()) {
			// 取得子业务对象
			for (BusinessObject bo : getBusinessObject().getSubBusinessObject()) {
				request.setAttribute(bo.getBeanAttribute()
						.getBeanInstanceName() + "Vt", bo.getViewText());
			}
		}
		boolean haveBindWorkFlow = WorkflowService
				.getHaveBindWorkFlow("000000000295");
		request.setAttribute("haveBindWorkFlow", haveBindWorkFlow);
		request.setAttribute("workflowTaskId", workflowTaskId);
		request.setAttribute("workflowNodeDefId", workflowNodeDefId);
		return new ModelAndView("xdss3/payment/homePaymentAdd");
	}

	/**
	 * 重分配查看
	 */
	public ModelAndView _viewForReassign(HttpServletRequest request,
			HttpServletResponse response) {

		HomePayment homePayment = new HomePayment();
		String workflowTaskId = request.getParameter("workflowTaskId");
		String workflowNodeDefId = request.getParameter("workflowNodeDefId");
		String businessId = request.getParameter("businessId");
		String oldId = this.reassignJdbcDao.getBoidByReassignid(businessId);
		Reassign reassign = this.reassignService._get(businessId);
		String id = "";
		// 若为"冲销（财务部冲销并作废）"，则直接使用原付款单的ID
		if(reassign.getReassigntmethod().equals(ReassignConstant.FI_CLEAR)){
			id = oldId;
		}else{
			id = this.reassignJdbcDao.getNewNoIdByOldNoId(ReassignConstant.PAYMENT, oldId);
		}
		if (null == id)
			id = businessId;

		if (StringUtils.isNullBlank(id)) {
			homePayment = this.homePaymentService._getForEdit(id);
		} else {
			homePayment = this.homePaymentService._getForEdit(id);
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
						.getBeanInstanceName() + "Vt", bo.getViewText());
			}
		}
		request.setAttribute("isReassign", "Y");
		request.setAttribute("homePayment", homePayment);
		return new ModelAndView("xdss3/payment/homePaymentView");
	}
	
	/**
	 * 重分配编辑
	 */
	public ModelAndView _editForReassign(HttpServletRequest request,
			HttpServletResponse response) {

		HomePayment homePayment = new HomePayment();
		String workflowTaskId = request.getParameter("workflowTaskId");
		String workflowNodeDefId = request.getParameter("workflowNodeDefId");
		String businessId = request.getParameter("businessId");
		String oldId = this.reassignJdbcDao.getBoidByReassignid(businessId);
		String id = this.reassignJdbcDao.getNewNoIdByOldNoId(
				ReassignConstant.PAYMENT, oldId);

		if (null == id)
			id = businessId;

		if (StringUtils.isNullBlank(id)) {
			homePayment = this.homePaymentService._getForEdit(id);
		} else {
			homePayment = this.homePaymentService._getForEdit(id);
		}

		boolean haveBindWorkFlow = WorkflowService.getHaveBindWorkFlow("000000000295");
		request.setAttribute("haveBindWorkFlow", haveBindWorkFlow);
		request.setAttribute("businessId", id);
		request.setAttribute("workflowTaskId", workflowTaskId);
		request.setAttribute("workflowNodeDefId", workflowNodeDefId);
		request.setAttribute("vt", getBusinessObject().getViewText());
		if (null != getBusinessObject().getSubBusinessObject()) {
			// 取得子业务对象
			for (BusinessObject bo : getBusinessObject().getSubBusinessObject()) {
				request.setAttribute(bo.getBeanAttribute().getBeanInstanceName() + "Vt", bo.getViewText());
			}
		}
		UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
		Properties prop = this.getProperties("config/config.properties");
		String xjrj = prop.getProperty("xjrj");
		if (xjrj != null && !"".equals(xjrj)) {
			request.setAttribute("xjrj", xjrj);
		}

		request.setAttribute("username", userContext.getSysUser().getUserName());
		request.setAttribute("isReassign", "Y");
		request.setAttribute("homePayment", homePayment);
		return new ModelAndView("xdss3/payment/homePaymentEdit");
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
		HomePayment homePayment = (HomePayment) ExBeanUtils
				.bindBusinessObjectData(request.getParameterMap(),
						HomePayment.class, true, request.getMethod(), true);
		// 类型标识是从那个页面提交，view 表示从view页面提交流程。
		String type = request.getParameter("type");
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<HomePaymentItem> homePaymentItemmodifyItems = ExBeanUtils
				.bindModifySubBoData(request.getParameterMap(),
						new Object[] { homePayment }, HomePaymentItem.class,
						null);
		Set<HomePaymentItem> deletedHomePaymentItemSet = ExBeanUtils
				.bindDeleteSubBoData(request.getParameterMap(),
						new Object[] { homePayment }, HomePaymentItem.class,
						null);
		homePayment.setHomePaymentItem(homePaymentItemmodifyItems);
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<HomePaymentCbill> homePaymentCbillmodifyItems = ExBeanUtils
				.bindModifySubBoData(request.getParameterMap(),
						new Object[] { homePayment }, HomePaymentCbill.class,
						null);
		Set<HomePaymentCbill> deletedHomePaymentCbillSet = ExBeanUtils
				.bindDeleteSubBoData(request.getParameterMap(),
						new Object[] { homePayment }, HomePaymentCbill.class,
						null);
		homePayment.setHomePaymentCbill(homePaymentCbillmodifyItems);
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<HomePayBankItem> homePayBankItemmodifyItems = ExBeanUtils
				.bindModifySubBoData(request.getParameterMap(),
						new Object[] { homePayment }, HomePayBankItem.class,
						null);
		Set<HomePayBankItem> deletedHomePayBankItemSet = ExBeanUtils
				.bindDeleteSubBoData(request.getParameterMap(),
						new Object[] { homePayment }, HomePayBankItem.class,
						null);
		homePayment.setHomePayBankItem(homePayBankItemmodifyItems);
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<HomeDocuBankItem> homeDocuBankItemmodifyItems = ExBeanUtils
				.bindModifySubBoData(request.getParameterMap(),
						new Object[] { homePayment }, HomeDocuBankItem.class,
						null);
		Set<HomeDocuBankItem> deletedHomeDocuBankItemSet = ExBeanUtils
				.bindDeleteSubBoData(request.getParameterMap(),
						new Object[] { homePayment }, HomeDocuBankItem.class,
						null);
		homePayment.setHomeDocuBankItem(homeDocuBankItemmodifyItems);
		// 绑定子对象(一对一关系)
		HomeSettSubj homeSettSubj = (HomeSettSubj) ExBeanUtils
				.bindBusinessObjectData(request.getParameterMap(),
						HomeSettSubj.class, false, request.getMethod(), true);
		if (homeSettSubj != null) {
			homePayment.setHomeSettSubj(homeSettSubj);
			homeSettSubj.setHomePayment(homePayment);
		}
		// 绑定子对象(一对一关系)
		HomeFundFlow homeFundFlow = (HomeFundFlow) ExBeanUtils
				.bindBusinessObjectData(request.getParameterMap(),
						HomeFundFlow.class, false, request.getMethod(), true);
		if (homeFundFlow != null) {
			homePayment.setHomeFundFlow(homeFundFlow);
			homeFundFlow.setHomePayment(homePayment);
		}
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<HomePaymentRelat> homePaymentRelatmodifyItems = ExBeanUtils
				.bindModifySubBoData(request.getParameterMap(),
						new Object[] { homePayment }, HomePaymentRelat.class,
						null);
		Set<HomePaymentRelat> deletedHomePaymentRelatSet = ExBeanUtils
				.bindDeleteSubBoData(request.getParameterMap(),
						new Object[] { homePayment }, HomePaymentRelat.class,
						null);
		homePayment.setHomePaymentRelat(homePaymentRelatmodifyItems);
		if (!"view".equalsIgnoreCase(type)) {
		    homePayment.setBusinessstate("2");
			
		}

		String reassignId = this.reassignJdbcDao
				.getReassignidByBoId(homePayment.getRepaymentid());
		Reassign reassign = this.reassignService.getReassignById(reassignId);
		reassign.setWorkflowTaskId(workflowTaskId);
		reassign.setWorkflowLeaveTransitionName(homePayment
				.getWorkflowLeaveTransitionName());
		reassign.setWorkflowExamine(homePayment.getWorkflowExamine());
		reassign.setWorkflowUserDefineProcessVariable(homePayment.getWorkflowUserDefineProcessVariable());
		reassign.setWorkflowBusinessNote(homePayment.getWorkflowBusinessNote());
		reassign.setWorkflowUserDefineTaskVariable(homePayment.getWorkflowUserDefineTaskVariable());

		/**
		 * 判断重分配方式是否为： 重置（到业务部门重新分配） 需要拷贝凭证
		 */
		if (reassign.getProcessstate() != null && 
			    !reassign.getProcessstate().equals("财务部审核") && 
			    (reassign.getReassigntmethod().equals(ReassignConstant.RESET_TO_BS) || reassign.getReassigntmethod().equals(ReassignConstant.RESET_TO_FI))){
			this.reassignService.copyVoucher(reassign.getReassignboid(),
					homePayment.getPaymentid());
		}
		//审核通过
		if(reassign.getProcessstate() != null
				&& reassign.getProcessstate().equals("财务部审核") && reassign.getWorkflowLeaveTransitionName().equals("确认")){
			homePayment.setBusinessstate("4");			
		}
		if(reassign.getProcessstate() != null
				&& reassign.getProcessstate().equals("财务部审核") && reassign.getWorkflowLeaveTransitionName().equals("作废")){
			homePayment.setBusinessstate("-1");			
		}
		// 设置审核状态
		if (homePayment.getWorkflowLeaveTransitionName().equals("作废")) {
			this.reassignService.updateReassignState(reassignId,
					BusinessState.SUBMITNOTPASS);
		}
		if (!"view".equalsIgnoreCase(type)) {		    
			this.homePaymentService._saveOrUpdate(homePayment,
					deletedHomePaymentItemSet, deletedHomePaymentCbillSet,
					deletedHomePayBankItemSet, deletedHomeDocuBankItemSet,
					deletedHomePaymentRelatSet, getBusinessObject());
		}
		BusinessObject businessObject = BusinessObjectService
				.getBusinessObject("Reassign");
		this.reassignService._submitProcess(reassign, businessObject);
		this.operateSuccessfully(response);
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
	 * 提交
	 * 
	 * @param request
	 * @param response
	 */

	public void _submitProcess(HttpServletRequest request,
			HttpServletResponse response) {
		// 绑定主对象值
		HomePayment homePayment = (HomePayment) ExBeanUtils
				.bindBusinessObjectData(request.getParameterMap(),
						HomePayment.class, true, request.getMethod(), true);
		// 类型标识是从那个页面提交，view 表示从view页面提交流程。
		String type = request.getParameter("type");
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<HomePaymentItem> homePaymentItemmodifyItems = ExBeanUtils
				.bindModifySubBoData(request.getParameterMap(),
						new Object[] { homePayment }, HomePaymentItem.class,
						null);
		Set<HomePaymentItem> deletedHomePaymentItemSet = ExBeanUtils
				.bindDeleteSubBoData(request.getParameterMap(),
						new Object[] { homePayment }, HomePaymentItem.class,
						null);
		homePayment.setHomePaymentItem(homePaymentItemmodifyItems);
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<HomePaymentCbill> homePaymentCbillmodifyItems = ExBeanUtils
				.bindModifySubBoData(request.getParameterMap(),
						new Object[] { homePayment }, HomePaymentCbill.class,
						null);
		Set<HomePaymentCbill> deletedHomePaymentCbillSet = ExBeanUtils
				.bindDeleteSubBoData(request.getParameterMap(),
						new Object[] { homePayment }, HomePaymentCbill.class,
						null);
		homePayment.setHomePaymentCbill(homePaymentCbillmodifyItems);
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<HomePayBankItem> homePayBankItemmodifyItems = ExBeanUtils
				.bindModifySubBoData(request.getParameterMap(),
						new Object[] { homePayment }, HomePayBankItem.class,
						null);
		Set<HomePayBankItem> deletedHomePayBankItemSet = ExBeanUtils
				.bindDeleteSubBoData(request.getParameterMap(),
						new Object[] { homePayment }, HomePayBankItem.class,
						null);
		homePayment.setHomePayBankItem(homePayBankItemmodifyItems);
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<HomeDocuBankItem> homeDocuBankItemmodifyItems = ExBeanUtils
				.bindModifySubBoData(request.getParameterMap(),
						new Object[] { homePayment }, HomeDocuBankItem.class,
						null);
		Set<HomeDocuBankItem> deletedHomeDocuBankItemSet = ExBeanUtils
				.bindDeleteSubBoData(request.getParameterMap(),
						new Object[] { homePayment }, HomeDocuBankItem.class,
						null);
		homePayment.setHomeDocuBankItem(homeDocuBankItemmodifyItems);
		// 绑定子对象(一对一关系)
		HomeSettSubj homeSettSubj = (HomeSettSubj) ExBeanUtils
				.bindBusinessObjectData(request.getParameterMap(),
						HomeSettSubj.class, false, request.getMethod(), true);
		if (homeSettSubj != null) {
			homePayment.setHomeSettSubj(homeSettSubj);
			homeSettSubj.setHomePayment(homePayment);
		}
		// 绑定子对象(一对一关系)
		HomeFundFlow homeFundFlow = (HomeFundFlow) ExBeanUtils
				.bindBusinessObjectData(request.getParameterMap(),
						HomeFundFlow.class, false, request.getMethod(), true);
		if (homeFundFlow != null) {
			homePayment.setHomeFundFlow(homeFundFlow);
			homeFundFlow.setHomePayment(homePayment);
		}
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<HomePaymentRelat> homePaymentRelatmodifyItems = ExBeanUtils
				.bindModifySubBoData(request.getParameterMap(),
						new Object[] { homePayment }, HomePaymentRelat.class,
						null);
		Set<HomePaymentRelat> deletedHomePaymentRelatSet = ExBeanUtils
				.bindDeleteSubBoData(request.getParameterMap(),
						new Object[] { homePayment }, HomePaymentRelat.class,
						null);
		homePayment.setHomePaymentRelat(homePaymentRelatmodifyItems);
		if (!"view".equalsIgnoreCase(type)) {
			this.homePaymentService._saveOrUpdate(homePayment,
					deletedHomePaymentItemSet, deletedHomePaymentCbillSet,
					deletedHomePayBankItemSet, deletedHomeDocuBankItemSet,
					deletedHomePaymentRelatSet, getBusinessObject());
		}
		String businesssate =homePayment.getBusinessstate();
		boolean flag =true;
		String res ="";
		//保存提交时，检查授信
		if("0".equals(businesssate) || StringUtils.isNullBlank(businesssate)){
			String strProviderId = homePayment.getSupplier();
			String strProjectNo="";
			for(HomePaymentItem pi : homePayment.getHomePaymentItem()){
				strProjectNo = pi.getProject_no();
				break;
			}
			BigDecimal value = homePayment.getApplyamount().multiply(homePayment.getCloseexchangerat());
			String strResult = this.checkCustomerSendCreditSerice.checkCredit(strProviderId, strProjectNo, value.floatValue());
			String andFlag[] = strResult.split("&");
			
			if ("true".equals(andFlag[1])){
				flag= true;
			}else{ 
				if ("false".equals(andFlag[0]) && "false".equals(andFlag[1])){
					flag= true;
				}else{
					res="额度超出不能提交!"+andFlag[0];
					flag= false;
				}
			}
		}
		if(flag){
			// TODO LJX 20100902 Add 加入工作流待办业务信息
			String workflowBusinessNote = "";
			String deptName = com.infolion.platform.console.sys.context.UserContextHolder
					.getLocalUserContext().getUserContext().getSysUser()
					.getDeptName();
			String creator = com.infolion.platform.console.sys.context.UserContextHolder
					.getLocalUserContext().getUserContext().getSysUser()
					.getRealName();
			String payType1 = "";	// 付款方式
			String payType2 = "";	// 付款类型
			payType1 = this.homePaymentService.getDomainText("YDPAYTRADETYPE", homePayment.getPaymenttype());
			payType2 = this.homePaymentService.getDomainText("YDPAYTYPE", homePayment.getPay_type());
			String supplier = this.homePaymentService.getSupplierNameByNo(homePayment.getSupplier());
			workflowBusinessNote = homePayment.getPaymentno() + "|" + deptName + 
								   "|" + creator + "|" + payType1 + 
								   "|" + payType2 + "|金额" + homePayment.getApplyamount() + 
								   "|供应商" + supplier;
			homePayment.setWorkflowBusinessNote(workflowBusinessNote);
	
			this.homePaymentService._submitProcess(homePayment,
					deletedHomePaymentItemSet, deletedHomePaymentCbillSet,
					deletedHomePayBankItemSet, deletedHomeDocuBankItemSet,
					deletedHomePaymentRelatSet, getBusinessObject());
			this.operateSuccessfully(response);
		}else{
			JSONObject jo = new JSONObject();
			jo.put("info", res);
			this.operateSuccessfullyHiddenInfoWithString(response, jo.toString());

		}
	}

	public void cashJournal(HttpServletRequest request,
			HttpServletResponse response) {
		JSONObject jo = new JSONObject();
		// 绑定主对象值
		HomePayment homePayment = (HomePayment) ExBeanUtils
				.bindBusinessObjectData(request.getParameterMap(),
						HomePayment.class, true, request.getMethod(), true);
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<HomePaymentItem> homePaymentItemmodifyItems = ExBeanUtils
				.bindModifySubBoData(request.getParameterMap(),
						new Object[] { homePayment }, HomePaymentItem.class,
						null);
		homePayment.setHomePaymentItem(homePaymentItemmodifyItems);
		Set<HomePaymentItem> homePaymentItemdeleteItems = ExBeanUtils
				.bindDeleteSubBoData(request.getParameterMap(),
						new Object[] { homePayment }, HomePaymentItem.class,
						null);
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<HomePaymentCbill> homePaymentCbillmodifyItems = ExBeanUtils
				.bindModifySubBoData(request.getParameterMap(),
						new Object[] { homePayment }, HomePaymentCbill.class,
						null);
		homePayment.setHomePaymentCbill(homePaymentCbillmodifyItems);
		Set<HomePaymentCbill> homePaymentCbilldeleteItems = ExBeanUtils
				.bindDeleteSubBoData(request.getParameterMap(),
						new Object[] { homePayment }, HomePaymentCbill.class,
						null);
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<HomePayBankItem> homePayBankItemmodifyItems = ExBeanUtils
				.bindModifySubBoData(request.getParameterMap(),
						new Object[] { homePayment }, HomePayBankItem.class,
						null);
		homePayment.setHomePayBankItem(homePayBankItemmodifyItems);
		Set<HomePayBankItem> homePayBankItemdeleteItems = ExBeanUtils
				.bindDeleteSubBoData(request.getParameterMap(),
						new Object[] { homePayment }, HomePayBankItem.class,
						null);
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<HomeDocuBankItem> homeDocuBankItemmodifyItems = ExBeanUtils
				.bindModifySubBoData(request.getParameterMap(),
						new Object[] { homePayment }, HomeDocuBankItem.class,
						null);
		homePayment.setHomeDocuBankItem(homeDocuBankItemmodifyItems);
		Set<HomeDocuBankItem> homeDocuBankItemdeleteItems = ExBeanUtils
				.bindDeleteSubBoData(request.getParameterMap(),
						new Object[] { homePayment }, HomeDocuBankItem.class,
						null);
		// 绑定子对象(一对一关系)
		HomeSettSubj homeSettSubj = (HomeSettSubj) ExBeanUtils
				.bindBusinessObjectData(request.getParameterMap(),
						HomeSettSubj.class, false, request.getMethod(), true);
		if (homeSettSubj != null) {
			homePayment.setHomeSettSubj(homeSettSubj);
			homeSettSubj.setHomePayment(homePayment);
		}
		// 绑定子对象(一对一关系)
		HomeFundFlow homeFundFlow = (HomeFundFlow) ExBeanUtils
				.bindBusinessObjectData(request.getParameterMap(),
						HomeFundFlow.class, false, request.getMethod(), true);
		if (homeFundFlow != null) {
			homePayment.setHomeFundFlow(homeFundFlow);
			homeFundFlow.setHomePayment(homePayment);
		}
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<HomePaymentRelat> homePaymentRelatmodifyItems = ExBeanUtils
				.bindModifySubBoData(request.getParameterMap(),
						new Object[] { homePayment }, HomePaymentRelat.class,
						null);
		homePayment.setHomePaymentRelat(homePaymentRelatmodifyItems);
		Set<HomePaymentRelat> homePaymentRelatdeleteItems = ExBeanUtils
				.bindDeleteSubBoData(request.getParameterMap(),
						new Object[] { homePayment }, HomePaymentRelat.class,
						null);
		this.homePaymentService._saveOrUpdate(homePayment,
				homePaymentItemdeleteItems, homePaymentCbilldeleteItems,
				homePayBankItemdeleteItems, homeDocuBankItemdeleteItems,
				homePaymentRelatdeleteItems, getBusinessObject());

		this.operateSuccessfullyHiddenInfo(response);
	}
	
	/**
	 * @创建作者：邱杰烜
	 * @创建日期：2010-10-25
	 * 查看相关单据的信息
	 */
	public ModelAndView viewRelatedInfo(HttpServletRequest request, HttpServletResponse response){
		String relatedNo = request.getParameter("relatedNo");
		HomePayment homePayment = new HomePayment();
		homePayment = this.homePaymentService.getPaymentByNo(relatedNo);
		String workflowTaskId = request.getParameter("workflowTaskId");
		String workflowNodeDefId = request.getParameter("workflowNodeDefId");
		boolean haveBindWorkFlow = WorkflowService.getHaveBindWorkFlow("000000000315");
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
		request.setAttribute("homePayment", homePayment);
		return new ModelAndView("xdss3/payment/homePaymentView");
	}
	
    /**
     * 内贸付款综合查询
     * 
     * @param request
     * @param response
     */
    public ModelAndView compositeQuery(HttpServletRequest request, HttpServletResponse response)
    {
        request.setAttribute("vt", getBusinessObject().getViewText());
        return new ModelAndView("xdss3/payment/homePaymentCompositeQuery");
    }

    /**
     * 查询 拼接SQL
     * 
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public void queryGrid(HttpServletRequest request, HttpServletResponse response,
            GridQueryCondition gridQueryCondition) throws Exception {
        
        BusinessObject businessObject = BusinessObjectService.getBusinessObject("HomePayment");
        String strAuthSql = "";
        try {
            AuthSql authSql = (AuthSql) Class.forName("com.infolion.xdss3.CommonDataAuthSql").newInstance();
            strAuthSql = authSql.getAuthSql(businessObject);
            // 替换权限默认的前缀表名
            strAuthSql = strAuthSql.replace("YPAYMENT", "t");
        } catch (Exception ex) {
            throw new SQLException("类不存在：" + ex.getMessage());
        }
        
        Map<String, String> parameter = this.paymentInnerInfoJDBCService.getParameterMap(request);
        String grid_sql = this.paymentInnerInfoJDBCService.getPaymentQuerySql(parameter);
        
        gridQueryCondition.setBoName("");
        gridQueryCondition.setTableSql("");
        gridQueryCondition.setDefaultCondition("1=1 " + strAuthSql);
        gridQueryCondition.setWhereSql("");
        gridQueryCondition.setOrderSql("CREATETIME DESC");
        gridQueryCondition.setGroupBySql("");
        gridQueryCondition.setTableName("(" + grid_sql + ") t");
        gridQueryCondition.setHandlerClass("com.infolion.xdss3.payment.domain.HomePaymentGrid");
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
    public void _saveDate(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String paymentid = request.getParameter("paymentid");
		String musttaketickleda = request.getParameter("musttaketickleda");
		String arrivegoodsdate = request.getParameter("arrivegoodsdate");
		String replacedate = request.getParameter("replacedate");
		HomePayment payment = homePaymentService.getPaymentByPaymentId(paymentid);
		String oldStr = "musttaketickleda:"+payment.getMusttaketickleda()+"|arrivegoodsdate:"+payment.getArrivegoodsdate()+"|replacedate:"+payment.getReplacedate();
		payment.setMusttaketickleda(musttaketickleda);
		payment.setArrivegoodsdate(arrivegoodsdate);
		payment.setReplacedate(replacedate);
		String newStr = "musttaketickleda:"+payment.getMusttaketickleda()+"|arrivegoodsdate:"+payment.getArrivegoodsdate()+"|replacedate:"+payment.getReplacedate();
		homePaymentService.updateDate(payment,oldStr,newStr,UserContextHolder.getLocalUserContext().getUserContext().getSysUser().getUserId());
		JSONObject jo = new JSONObject();
		jo.put("returnMessage", "更新成功");		
		String jsontext = jo.toString();
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		this.operateSuccessfullyWithString(response, jsontext);
		
	}
    
	public void _export(HttpServletRequest request, HttpServletResponse response,
			GridQueryCondition gridQueryCondition) throws Exception {

		BusinessObject businessObject = BusinessObjectService.getBusinessObject("HomePayment");
		String strAuthSql = "";
		try {
			AuthSql authSql = (AuthSql) Class.forName(
			"com.infolion.xdss3.CommonDataAuthSql").newInstance();
			strAuthSql = authSql.getAuthSql(businessObject);
			// 替换权限默认的前缀表名
			strAuthSql = strAuthSql.replace("YPAYMENT", "t");
		} catch (Exception ex) {
			throw new SQLException("类不存在：" + ex.getMessage());
		}


		Map<String, String> parameter = this.paymentInnerInfoJDBCService.getParameterMap(request);
        String grid_sql = this.paymentInnerInfoJDBCService.getPaymentQuerySql(parameter);


		String[] titles  = new String[]{"序号","付款单号","供应商名称","部门","金额","币别","收款银行","审核节点","应收票日","付款方式","创建日期","抬头文本","备注","立项号","合同号","预计到货日","银行对外付款日","银承到期日"};
		ExcelObject excel = new ExcelObject(titles);
		homePaymentService.dealOutToExcel(grid_sql,strAuthSql ,excel);
		try{
			//response.reset();
			response.setCharacterEncoding("utf-8");
			response.setContentType("application/x-download;charset=utf-8");
			response.setHeader("Content-Disposition", (new StringBuilder("attachment;filename=")).append(URLEncoder.encode("内贸付款表.xls", "utf-8")).toString());
			excel.write(response.getOutputStream());
			response.flushBuffer();
			response.getOutputStream().close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}

	}
    
    public void checkClearData(HttpServletRequest request,
			HttpServletResponse response) {
    	JSONObject jo = new JSONObject();
		// 绑定主对象值
		HomePayment homePayment = (HomePayment) ExBeanUtils
				.bindBusinessObjectData(request.getParameterMap(),
						HomePayment.class, true, request.getMethod(), true);
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<HomePaymentItem> homePaymentItemmodifyItems = ExBeanUtils
				.bindModifySubBoData(request.getParameterMap(),
						new Object[] { homePayment }, HomePaymentItem.class,
						null);
		homePayment.setHomePaymentItem(homePaymentItemmodifyItems);
		Set<HomePaymentItem> homePaymentItemdeleteItems = ExBeanUtils
				.bindDeleteSubBoData(request.getParameterMap(),
						new Object[] { homePayment }, HomePaymentItem.class,
						null);
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<HomePaymentCbill> homePaymentCbillmodifyItems = ExBeanUtils
				.bindModifySubBoData(request.getParameterMap(),
						new Object[] { homePayment }, HomePaymentCbill.class,
						null);
		homePayment.setHomePaymentCbill(homePaymentCbillmodifyItems);
		Set<HomePaymentCbill> homePaymentCbilldeleteItems = ExBeanUtils
				.bindDeleteSubBoData(request.getParameterMap(),
						new Object[] { homePayment }, HomePaymentCbill.class,
						null);
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<HomePayBankItem> homePayBankItemmodifyItems = ExBeanUtils
				.bindModifySubBoData(request.getParameterMap(),
						new Object[] { homePayment }, HomePayBankItem.class,
						null);
		homePayment.setHomePayBankItem(homePayBankItemmodifyItems);
		Set<HomePayBankItem> homePayBankItemdeleteItems = ExBeanUtils
				.bindDeleteSubBoData(request.getParameterMap(),
						new Object[] { homePayment }, HomePayBankItem.class,
						null);
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<HomeDocuBankItem> homeDocuBankItemmodifyItems = ExBeanUtils
				.bindModifySubBoData(request.getParameterMap(),
						new Object[] { homePayment }, HomeDocuBankItem.class,
						null);
		homePayment.setHomeDocuBankItem(homeDocuBankItemmodifyItems);
		Set<HomeDocuBankItem> homeDocuBankItemdeleteItems = ExBeanUtils
				.bindDeleteSubBoData(request.getParameterMap(),
						new Object[] { homePayment }, HomeDocuBankItem.class,
						null);
		// 绑定子对象(一对一关系)
		HomeSettSubj homeSettSubj = (HomeSettSubj) ExBeanUtils
				.bindBusinessObjectData(request.getParameterMap(),
						HomeSettSubj.class, false, request.getMethod(), true);
		if (homeSettSubj != null) {
			homePayment.setHomeSettSubj(homeSettSubj);
			homeSettSubj.setHomePayment(homePayment);
		}
		// 绑定子对象(一对一关系)
		HomeFundFlow homeFundFlow = (HomeFundFlow) ExBeanUtils
				.bindBusinessObjectData(request.getParameterMap(),
						HomeFundFlow.class, false, request.getMethod(), true);
		if (homeFundFlow != null) {
			homePayment.setHomeFundFlow(homeFundFlow);
			homeFundFlow.setHomePayment(homePayment);
		}
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<HomePaymentRelat> homePaymentRelatmodifyItems = ExBeanUtils
				.bindModifySubBoData(request.getParameterMap(),
						new Object[] { homePayment }, HomePaymentRelat.class,
						null);
		homePayment.setHomePaymentRelat(homePaymentRelatmodifyItems);
		Set<HomePaymentRelat> homePaymentRelatdeleteItems = ExBeanUtils
				.bindDeleteSubBoData(request.getParameterMap(),
						new Object[] { homePayment }, HomePaymentRelat.class,
						null);
		
//		如果有清票时
		if(null !=homePayment.getHomePaymentCbill()){			
			BigDecimal marginAmount = new BigDecimal("0");
			boolean isSave = false;
			if(!StringUtils.isNullBlank(homePayment.getPaymentid())){
				isSave=true;				
			}
			IInfo info =supplierClearAccountService.checkClearData(homePayment, marginAmount,isSave);
			jo.put("isRight", info.isRight());
			jo.put("info", info.getInfo());
			this.operateSuccessfullyHiddenInfoWithString(response, jo.toString());
		}else{
			jo.put("isRight", true);			
			this.operateSuccessfullyHiddenInfoWithString(response, jo.toString());
		}
		
    }
}