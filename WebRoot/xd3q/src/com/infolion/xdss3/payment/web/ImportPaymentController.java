/*
 * @(#)ImportPaymentController.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年05月27日 11点58分49秒
 *  描　述：创建
 */
package com.infolion.xdss3.payment.web;

import java.math.BigDecimal;
import java.net.URLEncoder;
import java.io.IOException;
import java.io.InputStream;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
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

import org.apache.commons.collections.map.ListOrderedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

import com.infolion.platform.bo.domain.BusinessObject;
import com.infolion.platform.bo.domain.Property;
import com.infolion.platform.bo.service.BusinessObjectService;
import com.infolion.platform.console.org.dao.SysDeptJdbcDao;
import com.infolion.platform.console.org.domain.SysRole;
import com.infolion.platform.console.sys.context.UserContext;
import com.infolion.platform.console.sys.context.UserContextHolder;
import com.infolion.platform.dpframework.core.BusinessException;
import com.infolion.platform.dpframework.core.annotation.BDPController;
import com.infolion.platform.dpframework.core.cache.SysCachePoolUtils;
import com.infolion.platform.dpframework.uicomponent.searchHelp.service.SearchHelpService;
import com.infolion.platform.dpframework.uicomponent.grid.GridService;
import com.infolion.platform.dpframework.uicomponent.grid.data.AuthSql;
import com.infolion.platform.dpframework.uicomponent.grid.data.GridData;
import com.infolion.platform.dpframework.uicomponent.grid.data.GridQueryCondition;
import com.infolion.platform.dpframework.util.ExBeanUtils;
import com.infolion.platform.dpframework.util.StringUtils;
import com.infolion.platform.util.DateUtils;
import com.infolion.platform.util.MultyGridData;
import com.infolion.platform.workflow.engine.WorkflowService;
import com.infolion.platform.workflow.engine.dao.ProcessJdbcDao;
import com.infolion.platform.workflow.engine.domain.ExtendTaskInstance;
import com.infolion.sapss.common.ExcelObject;
import com.infolion.sapss.payment.PaymentContants;
import com.infolion.sapss.payment.dao.PaymentContractJdbcDao;
import com.infolion.sapss.payment.service.PaymentInnerInfoJDBCService;
import com.infolion.sapss.ship.service.CheckCustomerSendCreditSerice;
import com.infolion.xdss3.BusinessState;
import com.infolion.xdss3.CommonDataAuth;
import com.infolion.xdss3.UpdateState;

import com.infolion.xdss3.billPayReBankItem.domain.BillPayReBankItem;
import com.infolion.xdss3.collect.domain.Collect;
import com.infolion.xdss3.masterData.dao.VendorTitleJdbcDao;
import com.infolion.xdss3.masterData.domain.VendorTitle;
import com.infolion.xdss3.masterData.service.SyncMasterDataService;

import com.infolion.xdss3.payment.domain.ImportDocuBankItem;
import com.infolion.xdss3.payment.domain.ImportFundFlow;
import com.infolion.xdss3.payment.domain.ImportPayBankItem;
import com.infolion.xdss3.payment.domain.ImportPayment;
import com.infolion.xdss3.payment.domain.ImportPaymentCbill;
import com.infolion.xdss3.payment.domain.ImportPaymentItem;
import com.infolion.xdss3.payment.domain.ImportRelatPayment;
import com.infolion.xdss3.payment.domain.ImportSettSubj;
import com.infolion.xdss3.payment.domain.PlickListInfo;
import com.infolion.xdss3.payment.service.ImportPaymentCbillService;
import com.infolion.xdss3.payment.service.PaymentItemService;
import com.infolion.xdss3.paymentGen.web.ImportPaymentControllerGen;
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
 * 进口付款(ImportPayment)控制器类
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
public class ImportPaymentController extends ImportPaymentControllerGen
{
    
    @Autowired
    private GridService gridService;
    
    public void setGridService(GridService gridService) {
        this.gridService = gridService;
    }
    
    @Autowired
    private PaymentContractJdbcDao paymentContractJdbcDao;
    
    public void setPaymentContractJdbcDao(
            PaymentContractJdbcDao paymentContractJdbcDao) {
        this.paymentContractJdbcDao = paymentContractJdbcDao;
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
	private ImportPaymentCbillService importPaymentCbillService;

	public void setImportPaymentCbillService(ImportPaymentCbillService importPaymentCbillService)
	{
		this.importPaymentCbillService = importPaymentCbillService;
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
	private ReassignJdbcDao reassignJdbcDao;

	/**
	 * @param reassignJdbcDao
	 *            the reassignJdbcDao to set
	 */
	public void setReassignJdbcDao(ReassignJdbcDao reassignJdbcDao)
	{
		this.reassignJdbcDao = reassignJdbcDao;
	}

	@Autowired
	private ReassignService reassignService;

	/**
	 * @param reassignService
	 *            the reassignService to set
	 */
	public void setReassignService(ReassignService reassignService)
	{
		this.reassignService = reassignService;
	}

	@Autowired
	private VendorTitleJdbcDao vendorTitleJdbcDao;

	public void setVendorTitleJdbcDao(VendorTitleJdbcDao vendorTitleJdbcDao)
	{
		this.vendorTitleJdbcDao = vendorTitleJdbcDao;
	}

	@Autowired
	private CheckCustomerSendCreditSerice checkCustomerSendCreditSerice;

	public void setCheckCustomerSendCreditSerice(CheckCustomerSendCreditSerice checkCustomerSendCreditSerice)
	{
		this.checkCustomerSendCreditSerice = checkCustomerSendCreditSerice;
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
	public void setPaymentItemService(PaymentItemService paymentItemService)
	{
		this.paymentItemService = paymentItemService;
	}

	@Autowired
	private ProcessJdbcDao processJdbcDao;

	/**
	 * @param processJdbcDao
	 *            the processJdbcDao to set
	 */
	public void setProcessJdbcDao(ProcessJdbcDao processJdbcDao)
	{
		this.processJdbcDao = processJdbcDao;
	}

	@Autowired	
	private SysDeptJdbcDao sysDeptJdbcDao;
	
	
	public void setSysDeptJdbcDao(SysDeptJdbcDao sysDeptJdbcDao) {
		this.sysDeptJdbcDao = sysDeptJdbcDao;
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
	
	/**
	 * 自动分配
	 * 
	 * @param request
	 * @param response
	 */
	public void _autoassign(HttpServletRequest request, HttpServletResponse response)
	{
		String strContractList = request.getParameter("contractList");
		String strProjectList = request.getParameter("projectList");
		String lifnr = request.getParameter("lifnr");

		List<ImportPaymentCbill> importPaymentCbillList = new ArrayList<ImportPaymentCbill>();
		List<VendorTitle> cntractVendorTitleList = new ArrayList<VendorTitle>();
		List<VendorTitle> projectVendorTitleList = new ArrayList<VendorTitle>();

		if (strContractList != null && !"".equals(strContractList)){
			cntractVendorTitleList = this.importPaymentCbillService.queryVendorByContract(strContractList);
		}
		if(strProjectList != null && !"".equals(strProjectList)){
			projectVendorTitleList = this.importPaymentCbillService.queryVendorByProject(strProjectList, lifnr);
		}

		importPaymentCbillList = this.importPaymentCbillService.dealPaymentCbillList(cntractVendorTitleList, projectVendorTitleList);
		Collections.sort(importPaymentCbillList,new DateComparator());
		MultyGridData gridJsonData = new MultyGridData();
		gridJsonData.setData(importPaymentCbillList.toArray());
		JSONObject jsonList = JSONObject.fromObject(gridJsonData);
		try
		{
			response.setContentType("text/html;charset=UTF-8");
			response.getWriter().print(jsonList);
			System.out.println(jsonList.toString());
		}
		catch (IOException e)
		{
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
			  ImportPaymentCbill p1 = (ImportPaymentCbill) object1; // 强制转换
			  ImportPaymentCbill p2 = (ImportPaymentCbill) object2; 
			  int d=DateUtils.getIntervalDays(p1.getAccountdate(),p2.getAccountdate());
		   return new Double("0").compareTo( new Double(d));
		  }
	}
	/**
	 * 获取进口到单信息
	 */
	public void getPlickListInfoByno(HttpServletRequest request, HttpServletResponse response)
	{
		String plickListId = request.getParameter("plicklistid");
		PlickListInfo plickListInfo = this.importPaymentService.getPlickListInfoByno(plickListId);

		JSONObject jo = new JSONObject();
		if (plickListInfo != null)
		{
			jo.put("currencyId", plickListInfo.getCurrencyId() == null ? "" : plickListInfo.getCurrencyId());
			jo.put("totalCurrency", plickListInfo.getTotalCurrency() == null ? "" : plickListInfo.getTotalCurrency());
			jo.put("arrivalDate", plickListInfo.getArrivalDate() == null ? "" : plickListInfo.getArrivalDate());
			jo.put("payDate", plickListInfo.getPayDate() == null ? "" : plickListInfo.getPayDate());
			jo.put("writeListNo", (plickListInfo.getWriteListNo() == null || plickListInfo.getWriteListNo().equals("null")) ? "" : plickListInfo.getWriteListNo());
			jo.put("bankName", plickListInfo.getIssuingBank() == null || plickListInfo.getIssuingBank().equals(null) ? "" : plickListInfo.getIssuingBank());
		}
		else
		{
			jo.put("currencyId", "");
			jo.put("totalCurrency", "");
			jo.put("arrivalDate", "");
			jo.put("payDate", "");
			jo.put("writeListNo", "");
		}

		try
		{
			response.setContentType("text/html;charset=UTF-8");
			response.getWriter().print(jo);
			System.out.println(jo.toString());
		}
		catch (IOException e)
		{
			logger.error("输出json失败," + e.getMessage(), e.getCause());
		}

	}

	public void getSupplierByID(HttpServletRequest request, HttpServletResponse response)
	{
		String supplierid = request.getParameter("supplierid");
		ImportPayment importPayment = this.importPaymentService.getSupplierByID(supplierid);
		JSONObject jo = new JSONObject();
		if (importPayment != null)
		{
			jo.put("collectbankid", importPayment.getCollectbankid());
			jo.put("collectbankacc", importPayment.getCollectbankacc());
		}
		else
		{
			jo.put("collectbankid", "");
			jo.put("collectbankacc", "");
		}

		try
		{
			response.setContentType("text/html;charset=UTF-8");
			response.getWriter().print(jo);
			System.out.println(jo.toString());
		}
		catch (IOException e)
		{
			logger.error("输出json失败," + e.getMessage(), e.getCause());
		}
	}

	/**
	 * 清除分配
	 * 
	 * @param request
	 * @param response
	 */
	public void _clearassign(HttpServletRequest request, HttpServletResponse response)
	{

	}

	/**
	 * 现金日记帐
	 * 
	 * @param request
	 * @param response
	 */
	public void _bookofaccount(HttpServletRequest request, HttpServletResponse response)
	{

	}

	/**
	 * 查看信用额度
	 * 
	 * @param request
	 * @param response
	 */
	public void _viewCredit(HttpServletRequest request, HttpServletResponse response)
	{

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
//			this.importPaymentService.updateVendorTitle2(supplier,paymentid);
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
	public void _simulate(HttpServletRequest request, HttpServletResponse response)
	{
		// 绑定主对象值
		ImportPayment importPayment = (ImportPayment) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), ImportPayment.class, true, request.getMethod(), true);
		// 类型标识是从那个页面提交，view 表示从view页面提交流程。
		String type = request.getParameter("type");
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<ImportPaymentItem> importPaymentItemmodifyItems = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { importPayment }, ImportPaymentItem.class, null);
		Set<ImportPaymentItem> deletedImportPaymentItemSet = ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(), new Object[] { importPayment }, ImportPaymentItem.class, null);
		importPayment.setImportPaymentItem(importPaymentItemmodifyItems);
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<ImportPaymentCbill> importPaymentCbillmodifyItems = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { importPayment }, ImportPaymentCbill.class, null);
		Set<ImportPaymentCbill> deletedImportPaymentCbillSet = ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(), new Object[] { importPayment }, ImportPaymentCbill.class, null);
		importPayment.setImportPaymentCbill(importPaymentCbillmodifyItems);
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<ImportPayBankItem> importPayBankItemmodifyItems = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { importPayment }, ImportPayBankItem.class, null);
		Set<ImportPayBankItem> deletedImportPayBankItemSet = ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(), new Object[] { importPayment }, ImportPayBankItem.class, null);
		importPayment.setImportPayBankItem(importPayBankItemmodifyItems);
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<ImportDocuBankItem> importDocuBankItemmodifyItems = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { importPayment }, ImportDocuBankItem.class, null);
		Set<ImportDocuBankItem> deletedImportDocuBankItemSet = ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(), new Object[] { importPayment }, ImportDocuBankItem.class, null);
		importPayment.setImportDocuBankItem(importDocuBankItemmodifyItems);
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<BillPayReBankItem> billPayReBankItemmodifyItems = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { importPayment }, BillPayReBankItem.class, null);
		Set<BillPayReBankItem> deletedBillPayReBankItemSet = ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(), new Object[] { importPayment }, BillPayReBankItem.class, null);
		importPayment.setBillPayReBankItem(billPayReBankItemmodifyItems);
		// 绑定子对象(一对一关系)
		ImportSettSubj importSettSubj = (ImportSettSubj) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), ImportSettSubj.class, false, request.getMethod(), true);
		if (importSettSubj != null)
		{
			importPayment.setImportSettSubj(importSettSubj);
			importSettSubj.setImportPayment(importPayment);
		}
		// 绑定子对象(一对一关系)
		ImportFundFlow importFundFlow = (ImportFundFlow) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), ImportFundFlow.class, false, request.getMethod(), true);
		if (importFundFlow != null)
		{
			importPayment.setImportFundFlow(importFundFlow);
			importFundFlow.setImportPayment(importPayment);
		}
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<ImportRelatPayment> importRelatPaymentmodifyItems = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { importPayment }, ImportRelatPayment.class, null);
		Set<ImportRelatPayment> deletedImportRelatPaymentSet = ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(), new Object[] { importPayment }, ImportRelatPayment.class, null);
		importPayment.setImportRelatPayment(importRelatPaymentmodifyItems);

		this.importPaymentService._saveOrUpdate(importPayment, deletedImportPaymentItemSet, deletedImportPaymentCbillSet, deletedImportPayBankItemSet, deletedImportDocuBankItemSet,deletedBillPayReBankItemSet, deletedImportRelatPaymentSet, getBusinessObject());

		String strSimulateType = request.getParameter("simulatetype");

		List<String> retList = new ArrayList<String>();

		ImportPayment _importPayment = this.importPaymentService._get(importPayment.getPaymentid());
		
		//更新状态
//		this.importPaymentService.updateVendorTitle(importPayment.getSupplier());
		this.voucherService.deleteVoucherByBusinessid(importPayment.getPaymentid());
		// 人民币清帐
		if (strSimulateType.equals("1") == true)
		{
			retList = this.importPaymentCbillService.cnySaveVoucher(_importPayment);
			//判断是否需要删除
//			this.voucherService.judgeVoucherNeedDel(_importPayment.getPaymentid());
//			if(_importPayment.getImportPaymentCbill() != null && _importPayment.getImportPaymentCbill().size() !=0){
//				Voucher voucher = this.voucherService.getVoucher(_importPayment.getPaymentid(), "02", "1");
//				this.importPaymentCbillService.dealClearAccountVoucher(_importPayment,voucher,"N");
//			}
		}

		// 外币付款，且付款货币和发票货币一致时
		if (strSimulateType.equals("2") == true)
		{
			retList = this.importPaymentCbillService.sameForeignCurrencySaveVoucher(_importPayment);
			
			//判断是否需要删除
//			this.voucherService.judgeVoucherNeedDel(_importPayment.getPaymentid());
//			Voucher voucher = this.voucherService.getVoucher(_importPayment.getPaymentid(), "02", "1");
//			this.importPaymentCbillService.dealClearAccountVoucher(_importPayment,voucher,"Y");
		}
		// 外币付款，且付款货币和发票货币不一致时
		if (strSimulateType.equals("3") == true)
		{
			retList = this.importPaymentCbillService.differForeignCurrencySaveVoucher(_importPayment);
			
			//判断是否需要删除
//			this.voucherService.judgeVoucherNeedDel(_importPayment.getPaymentid());
//			Voucher voucher = this.voucherService.getVoucher(_importPayment.getPaymentid(), "02", "5");
//			this.importPaymentCbillService.dealClearAccountVoucher(_importPayment,voucher,"Y");
		}

		// 纯代理业务
		if (strSimulateType.equals("5") == true)
		{
			retList = this.importPaymentCbillService.representpaySaveVoucher(_importPayment);
			this.voucherService.judgeVoucherNeedDel_2(_importPayment.getPaymentid());			
			List list = this.importPaymentCbillService.representpaySaveClearVoucher(_importPayment);			
			if (list!=null && list.size() > 0) {
			    retList.addAll(list);
			}
			
		}

		// 海外代付清帐
		if (strSimulateType.equals("6") == true)
		{
			retList = this.importPaymentCbillService.overpaySaveVoucher(_importPayment);
			
			//判断是否需要删除
//			this.voucherService.judgeVoucherNeedDel(_importPayment.getPaymentid());
//			Voucher voucher = this.voucherService.getVoucher(_importPayment.getPaymentid(), "02", "2");
//			this.importPaymentCbillService.dealClearAccountVoucher(importPayment,voucher,"Y");
		}

		// 海外代付清帐（有中转）
		if (strSimulateType.equals("7") == true)
		{
			retList = this.importPaymentCbillService.differOverpaySaveVoucher(_importPayment);
			
			//判断是否需要删除
//			this.voucherService.judgeVoucherNeedDel(_importPayment.getPaymentid());
//			Voucher voucher = this.voucherService.getVoucher(_importPayment.getPaymentid(), "02", "5");
//			this.importPaymentCbillService.dealClearAccountVoucher(importPayment,voucher,"Y");
		}
		List<String> retList2 = new ArrayList<String>();
		// 即期国内信用证，付款
		if (strSimulateType.equals("10") == true) {
			retList = this.importPaymentCbillService.dealBillSaveVoucher(_importPayment);
			this.voucherService.judgeVoucherNeedDel_2(_importPayment.getPaymentid());
			retList2 = this.importPaymentCbillService.dealBillClearSaveVoucher(_importPayment);
			retList.addAll(retList2);
		}
		// 远期国内信用证，办理承兑
		if (strSimulateType.equals("11") == true) {
			retList = this.importPaymentCbillService.dealBillSaveVoucher(_importPayment);			
		}
		// 远期国内证到期，付款后
		if (strSimulateType.equals("12") == true) {			
			retList = this.importPaymentCbillService.dealBillClearSaveVoucher(_importPayment);			
		}
		// 即期国内信用证，即期付款办理押汇后
		if (strSimulateType.equals("13") == true) {
			retList = this.importPaymentCbillService.dealBillSaveVoucher(_importPayment);
			this.voucherService.judgeVoucherNeedDel_2(_importPayment.getPaymentid());
			this.voucherService.judgeVoucherNeedDel(_importPayment.getPaymentid());
			retList2 = this.importPaymentCbillService.dealBillClearSaveVoucher(_importPayment);
			retList.addAll(retList2);		
			this.voucherService.judgeVoucherNeedDel_2(_importPayment.getPaymentid());
			retList2 = this.importPaymentCbillService.shortTimepaySaveVoucher2(_importPayment);
			retList.addAll(retList2);	
		}
		// 远期国内证到期，办理押汇支付货款后
		if (strSimulateType.equals("14") == true) {			
			retList = this.importPaymentCbillService.dealBillClearSaveVoucher(_importPayment);	
			this.voucherService.judgeVoucherNeedDel_2(_importPayment.getPaymentid());
			retList2 = this.importPaymentCbillService.shortTimepaySaveVoucher2(_importPayment);
			retList.addAll(retList2);		
		}
		// 短期外汇借款
		if (strSimulateType.equals("8") == true)
		{
			retList = this.importPaymentCbillService.shortTimepaySaveVoucher(_importPayment);
			
			//判断是否需要删除
//			this.voucherService.judgeVoucherNeedDel(_importPayment.getPaymentid());
//			Voucher voucher = this.voucherService.getVoucher(_importPayment.getPaymentid(), "02", "2");
//			this.importPaymentCbillService.dealClearAccountVoucher(importPayment,voucher,"Y");
		}

		// 还短期外汇借款
		if (strSimulateType.equals("9") == true)
		{
			//币别一致
			//if(_importPayment.getCurrency().equals(_importPayment.getCurrency2())){
			if(null ==_importPayment.getRedocaryamount() || _importPayment.getRedocaryamount().compareTo(new BigDecimal("0")) ==0){
				throw new BusinessException("还押汇金额不能为空或者0");				
			}
			if(null ==_importPayment.getRedocaryrate() || _importPayment.getRedocaryrate().compareTo(new BigDecimal("0")) ==0){
				throw new BusinessException("还押汇汇率不能为空或者0");				
			}
			retList = this.importPaymentCbillService.returnShortTimeSaveVoucher(_importPayment);
			this.voucherService.judgeVoucherNeedDel_2(_importPayment.getPaymentid());
			//}
			//币别不一致
			//else{
				//retList = this.importPaymentCbillService.returnDiffShortTimeSaveVoucher(_importPayment);
			//}
		} 
        if (
                ("5".equals(strSimulateType) && "财务会计审核押汇到期付款并做帐".equals(importPayment.getProcessstate()))
                || 
                "9".equals(strSimulateType) 
                ){
		    // 还短期外汇借款 与 纯代理业务(还押汇时) 不再做判断
		}  else
		{
    		this.voucherService.judgeVoucherNeedDel(_importPayment.getPaymentid());
		}

        JSONObject jo = new JSONObject();
//		生成清账凭证，zzh
		if ("1".equals(strSimulateType) || "2".equals(strSimulateType) || "3".equals(strSimulateType) || "6".equals(strSimulateType) || "7".equals(strSimulateType) || "8".equals(strSimulateType)) {
//			如果有清票时
			if(null !=_importPayment.getImportPaymentCbill() && _importPayment.getImportPaymentCbill().size()!=0){		
				ParameterVoucherObject parameterVoucher =supplierClearAccountService.setParameter(_importPayment);
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
				this.voucherService.judgeVoucherNeedDel(_importPayment.getPaymentid());
				boolean isSave = false;
				if(!StringUtils.isNullBlank(_importPayment.getPaymentid()))isSave=true;
				IInfo info =supplierClearAccountService.checkClearData(_importPayment, marginAmount,isSave);
//				判断本次数据是否正确
				if(info.isRight()){
					IInfoVoucher infoVoucher = supplierClearAccountService.isClearAccount(_importPayment, marginAmount);
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
							Voucher clearVoucher =supplierClearAccountService.saveClearVoucherBySupplier(parameterVoucher,infoVoucher,_importPayment.getPaymentid(),ClearConstant.PAYMENT_TYPE,isp);
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
						request.getSession().setAttribute(_importPayment.getPaymentid(), infoVoucher);
						//判断是否需要删除
						this.voucherService.judgeVoucherNeedDel(_importPayment.getPaymentid());						
						try {
							response.setContentType("text/html;charset=UTF-8");
							response.getWriter().print(_importPayment.getPaymentid());
							System.out.println(_importPayment.getPaymentid());
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
			this.voucherService.judgeVoucherNeedDel(_importPayment.getPaymentid());
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

	/**
	 * 删除指定付款单的所有票信息
	 * 
	 * @param request
	 * @param response
	 */
	public void _deletePayBill(HttpServletRequest request, HttpServletResponse response)
	{
		String strPaymentid = request.getParameter("paymentid");

		this.importPaymentCbillService._deletePayBill(strPaymentid);

		this.operateSuccessfully(response);
	}

	/**
	 * 创建
	 * 
	 * @param request
	 * @param response
	 */
	public ModelAndView _create(HttpServletRequest request, HttpServletResponse response)
	{
		String strPaymentType = request.getParameter("paymenttype");

		String calActivityId = request.getParameter("calActivityId");
		if (StringUtils.isNotBlank(calActivityId))
			request.setAttribute("calActivityId", calActivityId);
		ImportPayment importPayment = new ImportPayment();
		if("15".equals(strPaymentType) || "16".equals(strPaymentType)){
			importPayment.setTrade_type("01");
		}else{
			importPayment.setTrade_type("02");
		}
		importPayment.setPaymenttype(strPaymentType);
		importPayment.setPaymentstate("0");
		importPayment.setIsrepresentpay("0");
		importPayment.setIstradesubsist("0");
		importPayment.setIsoverrepay("0");
		importPayment.setBusinessstate("0");

		UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
		importPayment.setDept_id(userContext.getSysDept().getDeptid());

		BusinessObject boImportPayment = getBusinessObject();
		
		String workflowTaskId = request.getParameter("workflowTaskId");
		String workflowNodeDefId = request.getParameter("workflowNodeDefId");
		request.setAttribute("vt", boImportPayment.getViewText());
		request.setAttribute("importPayment", importPayment);
		boolean haveBindWorkFlow = WorkflowService.getHaveBindWorkFlow("000000000261");
		request.setAttribute("haveBindWorkFlow", haveBindWorkFlow);
		request.setAttribute("workflowTaskId", workflowTaskId);
		request.setAttribute("workflowNodeDefId", workflowNodeDefId);

		String strRoleType = "";
		for (int i = 0; i < userContext.getGrantedRoles().size(); i++)
		{
			SysRole sysRole = (SysRole) userContext.getGrantedRoles().get(i);

			if ("业务员".indexOf(sysRole.getRoleName()) != -1)
			{
				strRoleType = "1";
			}
		}

		request.setAttribute("roletype", strRoleType);

		Set<Property> properties = boImportPayment.getProperties();

		for (Iterator<Property> iter = properties.iterator(); iter.hasNext();) {
			Property property = (Property) iter.next();
			if (!StringUtils.isNullBlank(property.getSearchHelp())) {
				String value = com.infolion.platform.dpframework.util.BeanUtils.getPropertyValue(importPayment, property.getPropertyName());
				String shlpData = this.searchHelpService.getShlpDataByBoName(property, value);

				request.setAttribute(getBoName() + property.getPropertyName(),shlpData);
			}
		}
		
		List<BusinessObject> subBos = boImportPayment.getSubBusinessObject("ImportSettSubj,ImportFundFlow");
		if (null != subBos)
		{
			// 取得子业务对象
			for (BusinessObject bo : subBos)
			{
				request.setAttribute(bo.getBeanAttribute().getBeanInstanceName() + "Vt", bo.getViewText());
			}
		}
		
		return new ModelAndView("xdss3/payment/importPaymentAdd");
	}

	/**
	 * 编辑
	 * 
	 * @param request
	 * @param response
	 */
	public ModelAndView _edityw(HttpServletRequest request, HttpServletResponse response)
	{
		ImportPayment importPayment = new ImportPayment();
		String id = request.getParameter("paymentid");
		String workflowTaskId = request.getParameter("workflowTaskId");
		String workflowNodeDefId = request.getParameter("workflowNodeDefId");
		String businessId = request.getParameter("businessId");
		if (null == id)
			id = businessId;

		// if (StringUtils.isNullBlank(id))
		// {
		// importPayment = this.importPaymentService._getForEdit(id); }
		// else
		// {
		// importPayment = this.importPaymentService._getForEdit(id);
		// }
		importPayment = this.importPaymentService._getForEdit(id);

		/**
		 * 修改人:陈非
		 * 修改日期:2010-9-28
		 */
		String processstate = importPayment.getProcessstate();
		if(processstate.equals("押汇付款申请")){//清空"付款结算科目"和"付款纯资金"两个页签数据
			importPayment.setImportSettSubj(new ImportSettSubj());
			importPayment.setImportFundFlow(new ImportFundFlow());
			importPayment.getImportPayBankItem().clear();
			//importPayment.getImportDocuBankItem().clear();
		}
		BusinessObject boImportPayment = getBusinessObject();
		
		boolean haveBindWorkFlow = WorkflowService.getHaveBindWorkFlow("000000000261");
		request.setAttribute("businessId", id);
		request.setAttribute("workflowTaskId", workflowTaskId);
		request.setAttribute("workflowNodeDefId", workflowNodeDefId);
		request.setAttribute("haveBindWorkFlow", haveBindWorkFlow);
		request.setAttribute("vt", boImportPayment.getViewText());
		
		request.setAttribute("importPayment", importPayment);

		UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
		String strRoleType = "";
		for (int i = 0; i < userContext.getGrantedRoles().size(); i++)
		{
			SysRole sysRole = (SysRole) userContext.getGrantedRoles().get(i);

			if ("业务员".indexOf(sysRole.getRoleName()) != -1)
			{
				strRoleType = "1";
			}
		}

		Properties prop = this.getProperties("config/config.properties");
		String xjrj = prop.getProperty("xjrj");
		if (xjrj != null && !"".equals(xjrj))
		{
			request.setAttribute("xjrj", xjrj);
		}

		request.setAttribute("roletype", strRoleType);
		request.setAttribute("username", userContext.getSysUser().getUserName());

		request.setAttribute("roletype", strRoleType);

		Set<Property> properties = boImportPayment.getProperties();

		for (Iterator<Property> iter = properties.iterator(); iter.hasNext();) {
			Property property = (Property) iter.next();
			if (!StringUtils.isNullBlank(property.getSearchHelp())) {
				String value = com.infolion.platform.dpframework.util.BeanUtils.getPropertyValue(importPayment, property.getPropertyName());
				String shlpData = this.searchHelpService.getShlpDataByBoName(property, value);

				request.setAttribute(getBoName() + property.getPropertyName(),shlpData);
			}
		}
		
		List<BusinessObject> subBos = boImportPayment.getSubBusinessObject("ImportSettSubj,ImportFundFlow");
		if (null != subBos)
		{
			// 取得子业务对象
			for (BusinessObject bo : subBos)
			{
				request.setAttribute(bo.getBeanAttribute().getBeanInstanceName() + "Vt", bo.getViewText());
			}
		}
		
		return new ModelAndView("xdss3/payment/importPaymentEdit");
	}

	/**
	 * 编辑
	 * 
	 * @param request
	 * @param response
	 */
	public ModelAndView _edit(HttpServletRequest request, HttpServletResponse response)
	{
		ImportPayment importPayment = new ImportPayment();
		String id = request.getParameter("paymentid");
		String workflowTaskId = request.getParameter("workflowTaskId");
		String workflowNodeDefId = request.getParameter("workflowNodeDefId");
		String businessId = request.getParameter("businessId");
		if (null == id)
			id = businessId;

		// if (StringUtils.isNullBlank(id))
		// {
		// importPayment = this.importPaymentService._getForEdit(id); }
		// else
		// {
		// importPayment = this.importPaymentService._getForEdit(id);
		// }
		importPayment = this.importPaymentService._getForEdit(id);

		/**
		 * 修改人:陈非
		 * 修改日期:2010-9-28
		 */
		String processstate = importPayment.getProcessstate();
		if(processstate.equals("押汇付款申请")){//清空"付款结算科目"和"付款纯资金"两个页签数据
			importPayment.setImportSettSubj(new ImportSettSubj());
			importPayment.setImportFundFlow(new ImportFundFlow());
			importPayment.getImportPayBankItem().clear();
			importPayment.getImportDocuBankItem().clear();
		}
		
		BusinessObject boImportPayment = getBusinessObject();
		
		boolean haveBindWorkFlow = WorkflowService.getHaveBindWorkFlow("000000000261");
		request.setAttribute("businessId", id);
		request.setAttribute("workflowTaskId", workflowTaskId);
		request.setAttribute("workflowNodeDefId", workflowNodeDefId);
		request.setAttribute("haveBindWorkFlow", haveBindWorkFlow);
		request.setAttribute("vt", boImportPayment.getViewText());
		
		request.setAttribute("importPayment", importPayment);

		UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
		String strRoleType = "";
		for (int i = 0; i < userContext.getGrantedRoles().size(); i++)
		{
			SysRole sysRole = (SysRole) userContext.getGrantedRoles().get(i);

			if ("业务员".indexOf(sysRole.getRoleName()) != -1)
			{
				strRoleType = "1";
			}
		}

		Properties prop = this.getProperties("config/config.properties");
		String xjrj = prop.getProperty("xjrj");
		if (xjrj != null && !"".equals(xjrj))
		{
			request.setAttribute("xjrj", xjrj);
		}

		request.setAttribute("roletype", strRoleType);
		request.setAttribute("username", userContext.getSysUser().getUserName());

		request.setAttribute("roletype", strRoleType);
		
		Set<Property> properties = boImportPayment.getProperties();

		for (Iterator<Property> iter = properties.iterator(); iter.hasNext();) {
			Property property = (Property) iter.next();
			if (!StringUtils.isNullBlank(property.getSearchHelp())) {
				String value = com.infolion.platform.dpframework.util.BeanUtils.getPropertyValue(importPayment, property.getPropertyName());
				String shlpData = this.searchHelpService.getShlpDataByBoName(property, value);

				request.setAttribute(getBoName() + property.getPropertyName(),shlpData);
			}
		}
		
		List<BusinessObject> subBos = boImportPayment.getSubBusinessObject();
		if (null != subBos)
		{
			// 取得子业务对象
			for (BusinessObject subBo : subBos)
			{
				request.setAttribute(subBo.getBeanAttribute().getBeanInstanceName() + "Vt", subBo.getViewText());
				
				if(subBo.getBoName().equals("importSettSubj")||subBo.getBoName().equals("importFundFlow")){
					Set<Property> subproperties = subBo.getProperties();
					for (Iterator<Property> subiter = subproperties.iterator(); subiter.hasNext();) {
						Property subproperty = (Property) subiter.next();
						if (!StringUtils.isNullBlank(subproperty.getSearchHelp())) {
							String value = "";
							if (subBo.getBoName().equals("importSettSubj")) {
								value = com.infolion.platform.dpframework.util.BeanUtils.getPropertyValue(importPayment.getImportSettSubj(), subproperty.getPropertyName());
							}
							if (subBo.getBoName().equals("importFundFlow")) {
								value = com.infolion.platform.dpframework.util.BeanUtils.getPropertyValue(importPayment.getImportFundFlow(), subproperty.getPropertyName());
							}
							String shlpData = this.searchHelpService.getShlpDataByBoName(subproperty, value);

							request.setAttribute(subBo.getBoName() + subproperty.getPropertyName(), shlpData);
						}
					}
				}
			}
		}
		
		return new ModelAndView("xdss3/payment/importPaymentEdit");
	}
	
	/**
	 * 
	 * @param absolutePath
	 */
	public Properties getProperties(String absolutePath)
	{
		Properties p = new Properties();
		try
		{
			InputStream is = this.getClass().getClassLoader().getResourceAsStream(absolutePath);
			p.load(is);
		}
		catch (IOException e)
		{
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
	public ModelAndView _manage(HttpServletRequest request, HttpServletResponse response)
	{
		request.setAttribute("vt", getBusinessObject().getViewText());
		String strPaymentType = request.getParameter("paymenttype");
		String strTradeType = request.getParameter("tradetype");
		// LJX 20100907 管理页面加入grid数据权限过滤。
		String strDataAuthSql = CommonDataAuth.getDataAuthSql(this.getBusinessObject());
		request.setAttribute("dataAuthSql", strDataAuthSql);

		request.setAttribute("paymenttype", strPaymentType);
		request.setAttribute("tradetype", strTradeType);

		return new ModelAndView("xdss3/payment/importPaymentManage");
	}

	/**
	 * 增加发票信息
	 * 
	 * @param request
	 * @param response
	 */
	public void _addBillInfo(HttpServletRequest request, HttpServletResponse response)
	{
		String strBillNoList = request.getParameter("billnolist");

		List<ImportPaymentCbill> importPaymentCbillList = new ArrayList<ImportPaymentCbill>();

		importPaymentCbillList = this.importPaymentCbillService._addBillInfo(strBillNoList);

		MultyGridData gridJsonData = new MultyGridData();
		gridJsonData.setData(importPaymentCbillList.toArray());
		JSONObject jsonList = JSONObject.fromObject(gridJsonData);
		try
		{
			response.setContentType("text/html;charset=UTF-8");
			response.getWriter().print(jsonList);
			System.out.println(jsonList.toString());
		}
		catch (IOException e)
		{
			logger.error("输出json失败," + e.getMessage(), e.getCause());
		}
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
//		先不同步
		syncMasterDataService._synchronizeUnclearVendor(strProvider,false);
//		this.importPaymentService.updateVendorTitle(strProvider);
//		UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
//		String bukrs = userContext.getSysDept().getCompanyCode();
//		bukrs = sysDeptJdbcDao.getCompanyCode(userContext.getSysDept().toString());
//		syncMasterDataService._synchronizeWriteOff(bukrs, null, "S", "2");
	}
	public void _updateCT(HttpServletRequest request, HttpServletResponse response)
	{
		String strProvider = request.getParameter("privadeid");
		
		this.importPaymentService.updateVendorTitle(strProvider);
	
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
		syncMasterDataService._synchronizeWriteOff(bukrs, null, "S", "2");
	}
	
	/**
	 * 检查供应商信用额度
	 * 
	 * @param request
	 * @param response
	 */
	public void checkCredit(HttpServletRequest request, HttpServletResponse response)
	{
		String strProviderId = request.getParameter("providerid");
		String strProjectNo = request.getParameter("projectno");
		String strValue = request.getParameter("value");
		String paymentid = request.getParameter("paymentid");
		BigDecimal value = new BigDecimal(strValue);
//		如果有退回要先减去原来的金额
		if(!StringUtils.isNullBlank(paymentid)){
			String binstate = paymentItemService.getbussinessState(paymentid);
			if("1".equals(binstate)){
				BigDecimal acctamount = paymentItemService.getCurrAmount(paymentid);
				value = value.subtract(acctamount);
			}
		}
		String strResult = this.checkCustomerSendCreditSerice.checkCredit(strProviderId, strProjectNo, value.floatValue());
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("result", strResult);
		String jsontext = jsonObject.toString();

		try
		{
			response.setContentType("text/html;charset=UTF-8");
			response.getWriter().print(jsontext);
		}
		catch (IOException e)
		{
			logger.error("输出json失败," + e.getMessage(), e.getCause());
		}
	}

	/**
	 * 重分配复制
	 * 
	 * @param request
	 * @param response
	 */
	public ModelAndView _copyForReassign(HttpServletRequest request, HttpServletResponse response)
	{

		String businessId = request.getParameter("businessId");
		String workflowTaskId = request.getParameter("workflowTaskId");
		String workflowNodeDefId = request.getParameter("workflowNodeDefId");
		Reassign reassign = this.reassignService._get(businessId);
		String id = this.reassignJdbcDao.getBoidByReassignid(businessId);

		/**
		 * 判断是否已经拷贝过，
		 */
		ImportPayment importPayment;
		String boid = this.reassignJdbcDao.isCopyed(id, ReassignConstant.PAYMENT);
		if (boid != null)
		{
			importPayment = this.importPaymentService.getImportPaymentById(boid);
		}
		else
		{
			importPayment = this.importPaymentService._getEntityCopy(id);
			request.setAttribute("isCreateCopy", "true");
			importPayment.setRepaymentid(id); // 设置被重分配退款ID
			importPayment.setBusinessstate(BusinessState.REASSIGNED); // 设置业务状态为重分配
			importPayment.setText(reassign.getText()); // 设置文本为重分配提交时填写文本

			/**
			 * 重新计算金额
			 */
			Set<ImportPaymentCbill> newImportPaymentCbillSet = null;
			;
			Set<ImportPaymentCbill> importPaymentCbillSet = importPayment.getImportPaymentCbill();
			if (importPaymentCbillSet != null)
			{
				newImportPaymentCbillSet = new HashSet<ImportPaymentCbill>();
				Iterator<ImportPaymentCbill> it = importPaymentCbillSet.iterator();
				while (it.hasNext())
				{
					ImportPaymentCbill newImportPaymentCbill = it.next();

					// 已付金额
					BigDecimal Paidamount = this.vendorTitleJdbcDao.getSumClearAmount(newImportPaymentCbill.getBillno(), BusinessState.ALL_SUBMITPASS);
					newImportPaymentCbill.setPaidamount(Paidamount);

					// 未付金额
					BigDecimal Unpaidamount = newImportPaymentCbill.getPayableamount().subtract(Paidamount);
					newImportPaymentCbill.setUnpaidamount(Unpaidamount);

					// 在途金额
					BigDecimal Onroadamount = this.vendorTitleJdbcDao.getSumClearAmount(newImportPaymentCbill.getBillno(), BusinessState.ALL_ONROAD);
					newImportPaymentCbill.setOnroadamount(Onroadamount);
					newImportPaymentCbillSet.add(newImportPaymentCbill);
				}
			}
			importPayment.setImportPaymentCbill(newImportPaymentCbillSet);

		}

		request.setAttribute("importPayment", importPayment);
		request.setAttribute("vt", getBusinessObject().getViewText());
		if (null != getBusinessObject().getSubBusinessObject())
		{
			// 取得子业务对象
			for (BusinessObject bo : getBusinessObject().getSubBusinessObject())
			{
				request.setAttribute(bo.getBeanAttribute().getBeanInstanceName() + "Vt", bo.getViewText());
			}
		}
		boolean haveBindWorkFlow = WorkflowService.getHaveBindWorkFlow("000000000295");
		request.setAttribute("haveBindWorkFlow", haveBindWorkFlow);
		request.setAttribute("workflowTaskId", workflowTaskId);
		request.setAttribute("workflowNodeDefId", workflowNodeDefId);
		return new ModelAndView("xdss3/payment/importPaymentAdd");
	}

	/**
	 * 重分配查看
	 */
	public ModelAndView _viewForReassign(HttpServletRequest request, HttpServletResponse response)
	{

		ImportPayment importPayment = new ImportPayment();
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

		if (StringUtils.isNullBlank(id))
		{
			importPayment = this.importPaymentService._getForEdit(id);
		}
		else
		{
			importPayment = this.importPaymentService._getForEdit(id);
		}

		boolean haveBindWorkFlow = WorkflowService.getHaveBindWorkFlow("000000000295");
		request.setAttribute("haveBindWorkFlow", haveBindWorkFlow);
		request.setAttribute("businessId", id);
		request.setAttribute("workflowTaskId", workflowTaskId);
		request.setAttribute("workflowNodeDefId", workflowNodeDefId);
		request.setAttribute("vt", getBusinessObject().getViewText());
		if (null != getBusinessObject().getSubBusinessObject())
		{
			// 取得子业务对象
			for (BusinessObject bo : getBusinessObject().getSubBusinessObject())
			{
				request.setAttribute(bo.getBeanAttribute().getBeanInstanceName() + "Vt", bo.getViewText());
			}
		}
		request.setAttribute("isReassign", "Y");
		request.setAttribute("importPayment", importPayment);
		return new ModelAndView("xdss3/payment/importPaymentView");
	}
	
	/**
	 * 重分配编辑
	 */
	public ModelAndView _editForReassign(HttpServletRequest request,
			HttpServletResponse response) {

		ImportPayment importPayment = new ImportPayment();
		String workflowTaskId = request.getParameter("workflowTaskId");
		String workflowNodeDefId = request.getParameter("workflowNodeDefId");
		String businessId = request.getParameter("businessId");
		String oldId = this.reassignJdbcDao.getBoidByReassignid(businessId);
		String id = this.reassignJdbcDao.getNewNoIdByOldNoId(
				ReassignConstant.PAYMENT, oldId);

		if (null == id)
			id = businessId;

		if (StringUtils.isNullBlank(id)) {
			importPayment = this.importPaymentService._getForEdit(id);
		} else {
			importPayment = this.importPaymentService._getForEdit(id);
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
		UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
		Properties prop = this.getProperties("config/config.properties");
		String xjrj = prop.getProperty("xjrj");
		if (xjrj != null && !"".equals(xjrj)) {
			request.setAttribute("xjrj", xjrj);
		}
		request.setAttribute("isReassign", "Y");
		request.setAttribute("username", userContext.getSysUser().getUserName());
		request.setAttribute("importPayment", importPayment);
		return new ModelAndView("xdss3/payment/importPaymentEdit");
	}

	/**
	 * 重分配提交
	 * 
	 * @param request
	 * @param response
	 */
	public void _submitForReassign(HttpServletRequest request, HttpServletResponse response)
	{
		String workflowTaskId = request.getParameter("workflowTaskId");

		// 绑定主对象值
		ImportPayment importPayment = (ImportPayment) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), ImportPayment.class, true, request.getMethod(), true);
		// 类型标识是从那个页面提交，view 表示从view页面提交流程。
		String type = request.getParameter("type");
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<ImportPaymentItem> importPaymentItemmodifyItems = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { importPayment }, ImportPaymentItem.class, null);
		Set<ImportPaymentItem> deletedImportPaymentItemSet = ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(), new Object[] { importPayment }, ImportPaymentItem.class, null);
		importPayment.setImportPaymentItem(importPaymentItemmodifyItems);
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<ImportPaymentCbill> importPaymentCbillmodifyItems = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { importPayment }, ImportPaymentCbill.class, null);
		Set<ImportPaymentCbill> deletedImportPaymentCbillSet = ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(), new Object[] { importPayment }, ImportPaymentCbill.class, null);
		importPayment.setImportPaymentCbill(importPaymentCbillmodifyItems);
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<ImportPayBankItem> importPayBankItemmodifyItems = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { importPayment }, ImportPayBankItem.class, null);
		Set<ImportPayBankItem> deletedImportPayBankItemSet = ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(), new Object[] { importPayment }, ImportPayBankItem.class, null);
		importPayment.setImportPayBankItem(importPayBankItemmodifyItems);
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<ImportDocuBankItem> importDocuBankItemmodifyItems = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { importPayment }, ImportDocuBankItem.class, null);
		Set<ImportDocuBankItem> deletedImportDocuBankItemSet = ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(), new Object[] { importPayment }, ImportDocuBankItem.class, null);
		importPayment.setImportDocuBankItem(importDocuBankItemmodifyItems);
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<BillPayReBankItem> billPayReBankItemmodifyItems = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { importPayment }, BillPayReBankItem.class, null);
		Set<BillPayReBankItem> deletedBillPayReBankItemSet = ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(), new Object[] { importPayment }, BillPayReBankItem.class, null);
		importPayment.setBillPayReBankItem(billPayReBankItemmodifyItems);
		// 绑定子对象(一对一关系)
		ImportSettSubj importSettSubj = (ImportSettSubj) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), ImportSettSubj.class, false, request.getMethod(), true);
		if (importSettSubj != null)
		{
			importPayment.setImportSettSubj(importSettSubj);
			importSettSubj.setImportPayment(importPayment);
		}
		// 绑定子对象(一对一关系)
		ImportFundFlow importFundFlow = (ImportFundFlow) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), ImportFundFlow.class, false, request.getMethod(), true);
		if (importFundFlow != null)
		{
			importPayment.setImportFundFlow(importFundFlow);
			importFundFlow.setImportPayment(importPayment);
		}
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<ImportRelatPayment> importRelatPaymentmodifyItems = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { importPayment }, ImportRelatPayment.class, null);
		Set<ImportRelatPayment> deletedImportRelatPaymentSet = ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(), new Object[] { importPayment }, ImportRelatPayment.class, null);
		importPayment.setImportRelatPayment(importRelatPaymentmodifyItems);
		if (!"view".equalsIgnoreCase(type))
		{
		    importPayment.setBusinessstate("2");
			
		}

		String reassignId = this.reassignJdbcDao.getReassignidByBoId(importPayment.getRepaymentid());
		Reassign reassign = this.reassignService.getReassignById(reassignId);
		reassign.setWorkflowTaskId(workflowTaskId);
		reassign.setWorkflowLeaveTransitionName(importPayment.getWorkflowLeaveTransitionName());
		reassign.setWorkflowExamine(importPayment.getWorkflowExamine());
		reassign.setWorkflowUserDefineProcessVariable(importPayment.getWorkflowUserDefineProcessVariable());
		reassign.setWorkflowBusinessNote(importPayment.getWorkflowBusinessNote());
		reassign.setWorkflowUserDefineTaskVariable(importPayment.getWorkflowUserDefineTaskVariable());


		/**
		 * 判断重分配方式是否为： 重置（到业务部门重新分配） 需要拷贝凭证
		 */
		if (reassign.getProcessstate() != null && 
			    !reassign.getProcessstate().equals("财务部审核") && 
			    (reassign.getReassigntmethod().equals(ReassignConstant.RESET_TO_BS) || reassign.getReassigntmethod().equals(ReassignConstant.RESET_TO_FI))){
			this.reassignService.copyVoucher(reassign.getReassignboid(), importPayment.getPaymentid());
		}
		//审核通过
		if(reassign.getProcessstate() != null
				&& reassign.getProcessstate().equals("财务部审核") && reassign.getWorkflowLeaveTransitionName().equals("确认")){
			importPayment.setBusinessstate("4");			
		}
		if(reassign.getProcessstate() != null
				&& reassign.getProcessstate().equals("财务部审核") && reassign.getWorkflowLeaveTransitionName().equals("作废")){
			importPayment.setBusinessstate("-1");			
		}
		// 设置审核状态
		if (importPayment.getWorkflowLeaveTransitionName().equals("作废"))
		{
			this.reassignService.updateReassignState(reassignId, BusinessState.SUBMITNOTPASS);
		}
		if (!"view".equalsIgnoreCase(type))
		{
		    
			this.importPaymentService._saveOrUpdate(importPayment, deletedImportPaymentItemSet, deletedImportPaymentCbillSet, deletedImportPayBankItemSet, deletedImportDocuBankItemSet,deletedBillPayReBankItemSet, deletedImportRelatPaymentSet, getBusinessObject());
		}
		BusinessObject businessObject = BusinessObjectService.getBusinessObject("Reassign");
		this.reassignService._submitProcess(reassign, businessObject);
		this.operateSuccessfully(response);
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView _print(HttpServletRequest request, HttpServletResponse response)
	{
		String paymentId = request.getParameter("paymentId");
		ImportPayment importPayment = this.importPaymentService._getDetached(paymentId);
		String creator = importPayment.getCreator();
		importPayment.setCreatorName(com.infolion.platform.sys.cache.SysCachePoolUtils.getDictDataValue("T_SYS_USER", creator));
		String dept_id = importPayment.getDept_id();
		importPayment.setDeptName(com.infolion.platform.sys.cache.SysCachePoolUtils.getDictDataValue("T_SYS_DEPT", dept_id));
		String coantractNoStr = this.paymentItemService.queryContractNoByPayMentId(paymentId);
		List<ExtendTaskInstance> listTaskIns = processJdbcDao.getExtTaskInstances2(paymentId);
		Collections.sort(listTaskIns, new Comparator<ExtendTaskInstance>() {
			public int compare(ExtendTaskInstance a, ExtendTaskInstance b) {
			return a.getTaskEndTime().compareTo(b.getTaskEndTime());
			}
		});
		if (coantractNoStr == null)
			coantractNoStr = "";

		String pickListNo = this.paymentItemService.queryPickListNoByPayMentId(paymentId);
		String lcNo = this.paymentItemService.queryLcNoByPickListNo(pickListNo);
		String supplierName = this.paymentItemService.querySupplier(importPayment.getSupplier());
		
		request.setAttribute("pickListNo", pickListNo);
		request.setAttribute("lcNo", lcNo);   //yanghancai 2010-09-25  增加信用证号
		request.setAttribute("contractNoStr", coantractNoStr);
		request.setAttribute("taskHis", listTaskIns);
		request.setAttribute("main", importPayment);
		request.setAttribute("supplierName", supplierName);
		request.setAttribute("printType", request.getParameter("printType"));
		// String payValue = homePayment.getFactamount().divide(new
		// BigDecimal(1), 2, RoundingMode.HALF_UP).toString();
		// request.setAttribute("payValue", payValue);
		request.setAttribute("payValueBig", PaymentContants.changeToBig(new Double(importPayment.getFactamount().doubleValue())));
		return new ModelAndView("xdss3/payment/importPaymentPrint");
	}
	
	public void cashJournal(HttpServletRequest request, HttpServletResponse response)
	{
		JSONObject jo = new JSONObject();
		// 绑定主对象值
		ImportPayment importPayment = (ImportPayment) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), ImportPayment.class, true, request.getMethod(), true);

		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<ImportPaymentItem> importPaymentItemmodifyItems = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { importPayment }, ImportPaymentItem.class, null);
		importPayment.setImportPaymentItem(importPaymentItemmodifyItems);
		Set<ImportPaymentItem> importPaymentItemdeleteItems = ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(), new Object[] { importPayment }, ImportPaymentItem.class, null);
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<ImportPaymentCbill> importPaymentCbillmodifyItems = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { importPayment }, ImportPaymentCbill.class, null);
		importPayment.setImportPaymentCbill(importPaymentCbillmodifyItems);
		Set<ImportPaymentCbill> importPaymentCbilldeleteItems = ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(), new Object[] { importPayment }, ImportPaymentCbill.class, null);
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<ImportPayBankItem> importPayBankItemmodifyItems = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { importPayment }, ImportPayBankItem.class, null);
		importPayment.setImportPayBankItem(importPayBankItemmodifyItems);
		Set<ImportPayBankItem> importPayBankItemdeleteItems = ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(), new Object[] { importPayment }, ImportPayBankItem.class, null);
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<ImportDocuBankItem> importDocuBankItemmodifyItems = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { importPayment }, ImportDocuBankItem.class, null);
		importPayment.setImportDocuBankItem(importDocuBankItemmodifyItems);
		Set<ImportDocuBankItem> importDocuBankItemdeleteItems = ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(), new Object[] { importPayment }, ImportDocuBankItem.class, null);
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<BillPayReBankItem> billPayReBankItemmodifyItems = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { importPayment }, BillPayReBankItem.class, null);
		Set<BillPayReBankItem> deletedBillPayReBankItemSet = ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(), new Object[] { importPayment }, BillPayReBankItem.class, null);
		importPayment.setBillPayReBankItem(billPayReBankItemmodifyItems);
		// 绑定子对象(一对一关系)
		ImportSettSubj importSettSubj = (ImportSettSubj) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), ImportSettSubj.class, false, request.getMethod(), true);
		if (importSettSubj != null)
		{
			importPayment.setImportSettSubj(importSettSubj);
			importSettSubj.setImportPayment(importPayment);
		}
		// 绑定子对象(一对一关系)
		ImportFundFlow importFundFlow = (ImportFundFlow) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), ImportFundFlow.class, false, request.getMethod(), true);
		if (importFundFlow != null)
		{
			importPayment.setImportFundFlow(importFundFlow);
			importFundFlow.setImportPayment(importPayment);
		}
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<ImportRelatPayment> importRelatPaymentmodifyItems = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { importPayment }, ImportRelatPayment.class, null);
		importPayment.setImportRelatPayment(importRelatPaymentmodifyItems);
		Set<ImportRelatPayment> importRelatPaymentdeleteItems = ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(), new Object[] { importPayment }, ImportRelatPayment.class, null);

		this.importPaymentService._saveOrUpdate(importPayment, importPaymentItemdeleteItems, importPaymentCbilldeleteItems, importPayBankItemdeleteItems, importDocuBankItemdeleteItems,deletedBillPayReBankItemSet, importRelatPaymentdeleteItems, getBusinessObject());

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
	
	/**
	 * @创建作者：邱杰烜
	 * @创建日期：2010-11-03
	 * 付款清票状态查询
	 */
	public ModelAndView _paymentBillManage(HttpServletRequest request, HttpServletResponse response){
		return new ModelAndView("xdss3/fundBill/paymentBillManage");
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
        return new ModelAndView("xdss3/payment/importPaymentCompositeQuery");
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
        
        BusinessObject businessObject = BusinessObjectService.getBusinessObject("ImportPayment");
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
        gridQueryCondition.setHandlerClass("com.infolion.xdss3.payment.domain.ImportPaymentGrid");
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
     * 跳转报表查询
     */
    public ModelAndView _forwardReportQuery(HttpServletRequest request, HttpServletResponse response)
    {
//    	request.setAttribute("vt", getBusinessObject().getViewText());
    	return new ModelAndView("xdss3/payment/importPaymentReportQuery");
    }
    
    /**
     * 报表查询
     */
    public void reportQuery(HttpServletRequest request, HttpServletResponse response,
    		GridQueryCondition gridQueryCondition) throws Exception {
    	
    	BusinessObject businessObject = BusinessObjectService.getBusinessObject("ImportPayment");
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
    	

        String issuing_date = parameter.get("issuing_date");
        String issuing_dateEnd = parameter.get("issuing_dateEnd");
        if (StringUtils.isNotBlank(issuing_date)) {
            issuing_date = issuing_date.replaceAll("-", "");
            parameter.put("issuing_date", issuing_date);
        }
        if (StringUtils.isNotBlank(issuing_dateEnd)) {
            issuing_dateEnd = issuing_dateEnd.replaceAll("-", "");
            parameter.put("issuing_dateEnd", issuing_dateEnd);
        }
    	
    	String grid_sql = this.importPaymentService.getPaymentReportQuerySql(parameter);
    	
    	gridQueryCondition.setBoName("");
    	gridQueryCondition.setTableSql("");
    	gridQueryCondition.setDefaultCondition("1=1 " + strAuthSql);
    	gridQueryCondition.setWhereSql("");
//    	gridQueryCondition.setOrderSql("CREATETIME DESC");
    	gridQueryCondition.setGroupBySql("");
    	gridQueryCondition.setTableName("(" + grid_sql + ") t");
    	gridQueryCondition.setHandlerClass("com.infolion.xdss3.payment.domain.ImportPaymentReportGrid");
    	String editable = "false";
    	String needAuthentication = "true";
    	GridData gridJsonData = this.gridService.getGridData(gridQueryCondition, editable, needAuthentication);
    	
    	Object[] array = (Object [])gridJsonData.getData();
        for (Object o : array) {
            ListOrderedMap lomap = (ListOrderedMap)o;
            String billpurid = (String)lomap.get("PAYMENTID");
            if (!StringUtils.isNotBlank(issuing_dateEnd)) {
                issuing_dateEnd = issuing_date;
            }
            // 期初
            Map<String,String> amountBegMap = this.paymentContractJdbcDao.getAmount(billpurid, issuing_date, null, "押汇"); //时间段前押汇金额
            Map<String,String> reAmountBegMap = this.paymentContractJdbcDao.getAmount(billpurid, issuing_date, null, null); //时间段前还押汇金额
            Map<String,String> amountPeriodMap = this.paymentContractJdbcDao.getAmount(billpurid, issuing_date, issuing_dateEnd, "押汇"); //时间段内押汇金额
            Map<String,String> reAmountPeriodMap = this.paymentContractJdbcDao.getAmount(billpurid, issuing_date, issuing_dateEnd, null); //时间段内还押汇金额
            
            BigDecimal begAmount = new BigDecimal((String)amountBegMap.get("AMOUNT"));
            BigDecimal reAmount = new BigDecimal((String)reAmountBegMap.get("AMOUNT"));
            
            lomap.put("BEGBALANCE",  begAmount.subtract(reAmount) );
            lomap.put("BEGBALANCE2", (new BigDecimal((String)amountBegMap.get("AMOUNT2"))).subtract(new BigDecimal((String)reAmountBegMap.get("AMOUNT2"))));
            
            
            BigDecimal perAmount = new BigDecimal((String)amountPeriodMap.get("AMOUNT"));
            BigDecimal perAmount2 = new BigDecimal((String)amountPeriodMap.get("AMOUNT2"));
            
            lomap.put("APPLYAMOUNT",  perAmount );
            lomap.put("APPLYAMOUNT2", perAmount2);
            
            BigDecimal reperAmount = new BigDecimal((String)reAmountPeriodMap.get("AMOUNT"));
            BigDecimal reperAmount2 = new BigDecimal((String)reAmountPeriodMap.get("AMOUNT2"));
            
            lomap.put("REDOCARYAMOUNT",  reperAmount );
            lomap.put("REDOCARYAMOUNT2", reperAmount2);
            
            lomap.put("ENDBALANCE",  ((BigDecimal)lomap.get("BEGBALANCE")).add(perAmount).subtract(reperAmount) );
            lomap.put("ENDBALANCE2", ((BigDecimal)lomap.get("BEGBALANCE2")).add(perAmount2).subtract(reperAmount2));
            
        }
        
        // 取得海外代付数据
        String grid_sql2 = this.importPaymentService.getPaymentIsOverRePayReportQuerySql(parameter);
        gridQueryCondition.setBoName("");
        gridQueryCondition.setTableSql("");
        gridQueryCondition.setDefaultCondition("1=1 " + strAuthSql);
        gridQueryCondition.setWhereSql("");
//      gridQueryCondition.setOrderSql("CREATETIME DESC");
        gridQueryCondition.setGroupBySql("");
        gridQueryCondition.setTableName("(" + grid_sql2 + ") t");
        gridQueryCondition.setHandlerClass("com.infolion.xdss3.payment.domain.ImportPaymentReportGrid");
        GridData gridJsonData2 = this.gridService.getGridData(gridQueryCondition, editable, needAuthentication);
        Object[] list2 = (Object [])gridJsonData2.getData();

        List fixList = Arrays.asList(array);
        List tmpList = new ArrayList();
        tmpList.addAll(fixList);
        for (Object o : list2) {
            ListOrderedMap lomap = (ListOrderedMap)o;
            String billpurid = (String)lomap.get("PAYMENTID");
            if (!StringUtils.isNotBlank(issuing_dateEnd)) {
                issuing_dateEnd = issuing_date;
            }
            // 期初
            Map<String,String> amountBegMap = this.paymentContractJdbcDao.getAmountIsOverRePay(billpurid, issuing_date, null, "押汇"); //时间段前押汇金额
            Map<String,String> reAmountBegMap = this.paymentContractJdbcDao.getAmountIsOverRePay(billpurid, issuing_date, null, null); //时间段前还押汇金额
            Map<String,String> amountPeriodMap = this.paymentContractJdbcDao.getAmountIsOverRePay(billpurid, issuing_date, issuing_dateEnd, "押汇"); //时间段内押汇金额
            Map<String,String> reAmountPeriodMap = this.paymentContractJdbcDao.getAmountIsOverRePay(billpurid, issuing_date, issuing_dateEnd, null); //时间段内还押汇金额
            
            BigDecimal begAmount = new BigDecimal((String)amountBegMap.get("AMOUNT"));
            BigDecimal reAmount = new BigDecimal((String)reAmountBegMap.get("AMOUNT"));
            
            lomap.put("BEGBALANCE",  begAmount.subtract(reAmount) );
            lomap.put("BEGBALANCE2", (new BigDecimal((String)amountBegMap.get("AMOUNT2"))).subtract(new BigDecimal((String)reAmountBegMap.get("AMOUNT2"))));
            
            
            BigDecimal perAmount = new BigDecimal((String)amountPeriodMap.get("AMOUNT"));
            BigDecimal perAmount2 = new BigDecimal((String)amountPeriodMap.get("AMOUNT2"));
            
            lomap.put("APPLYAMOUNT",  perAmount );
            lomap.put("APPLYAMOUNT2", perAmount2);
            
            BigDecimal reperAmount = new BigDecimal((String)reAmountPeriodMap.get("AMOUNT"));
            BigDecimal reperAmount2 = new BigDecimal((String)reAmountPeriodMap.get("AMOUNT2"));
            
            lomap.put("REDOCARYAMOUNT",  reperAmount );
            lomap.put("REDOCARYAMOUNT2", reperAmount2);
            
            lomap.put("ENDBALANCE",  ((BigDecimal)lomap.get("BEGBALANCE")).add(perAmount).subtract(reperAmount));
            lomap.put("ENDBALANCE2", ((BigDecimal)lomap.get("BEGBALANCE2")).add(perAmount2).subtract(reperAmount2));
            tmpList.add(lomap);            
            gridJsonData.setTotalSize(gridJsonData.getTotalSize()+1);
        }

        gridJsonData.setData(tmpList.toArray());
        
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
		ImportPayment payment = importPaymentService.getPaymentByPaymentId(paymentid);
		String oldStr = "musttaketickleda:"+payment.getMusttaketickleda()+"|arrivegoodsdate:"+payment.getArrivegoodsdate()+"|replacedate:"+payment.getReplacedate();
		payment.setMusttaketickleda(musttaketickleda);
		payment.setArrivegoodsdate(arrivegoodsdate);
		payment.setReplacedate(replacedate);
		String newStr = "musttaketickleda:"+payment.getMusttaketickleda()+"|arrivegoodsdate:"+payment.getArrivegoodsdate()+"|replacedate:"+payment.getReplacedate();
		importPaymentService.updateDate(payment,oldStr,newStr,UserContextHolder.getLocalUserContext().getUserContext().getSysUser().getUserId());
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
		ImportPayment importPayment = (ImportPayment) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), ImportPayment.class, true, request.getMethod(), true);
		// 类型标识是从那个页面提交，view 表示从view页面提交流程。
		String type = request.getParameter("type");
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<ImportPaymentItem> importPaymentItemmodifyItems = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { importPayment }, ImportPaymentItem.class, null);
		Set<ImportPaymentItem> deletedImportPaymentItemSet = ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(), new Object[] { importPayment }, ImportPaymentItem.class, null);
		importPayment.setImportPaymentItem(importPaymentItemmodifyItems);
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<ImportPaymentCbill> importPaymentCbillmodifyItems = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { importPayment }, ImportPaymentCbill.class, null);
		Set<ImportPaymentCbill> deletedImportPaymentCbillSet = ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(), new Object[] { importPayment }, ImportPaymentCbill.class, null);
		importPayment.setImportPaymentCbill(importPaymentCbillmodifyItems);
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<ImportPayBankItem> importPayBankItemmodifyItems = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { importPayment }, ImportPayBankItem.class, null);
		Set<ImportPayBankItem> deletedImportPayBankItemSet = ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(), new Object[] { importPayment }, ImportPayBankItem.class, null);
		importPayment.setImportPayBankItem(importPayBankItemmodifyItems);
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<ImportDocuBankItem> importDocuBankItemmodifyItems = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { importPayment }, ImportDocuBankItem.class, null);
		Set<ImportDocuBankItem> deletedImportDocuBankItemSet = ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(), new Object[] { importPayment }, ImportDocuBankItem.class, null);
		importPayment.setImportDocuBankItem(importDocuBankItemmodifyItems);
		// 绑定子对象(一对一关系)
		ImportSettSubj importSettSubj = (ImportSettSubj) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), ImportSettSubj.class, false, request.getMethod(), true);
		if (importSettSubj != null)
		{
			importPayment.setImportSettSubj(importSettSubj);
			importSettSubj.setImportPayment(importPayment);
		}
		// 绑定子对象(一对一关系)
		ImportFundFlow importFundFlow = (ImportFundFlow) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), ImportFundFlow.class, false, request.getMethod(), true);
		if (importFundFlow != null)
		{
			importPayment.setImportFundFlow(importFundFlow);
			importFundFlow.setImportPayment(importPayment);
		}
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<ImportRelatPayment> importRelatPaymentmodifyItems = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { importPayment }, ImportRelatPayment.class, null);
		Set<ImportRelatPayment> deletedImportRelatPaymentSet = ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(), new Object[] { importPayment }, ImportRelatPayment.class, null);
		importPayment.setImportRelatPayment(importRelatPaymentmodifyItems);
		
//		如果有清票时
		if(null !=importPayment.getImportPaymentCbill()){			
			BigDecimal marginAmount = new BigDecimal("0");
			boolean isSave = false;
			if(!StringUtils.isNullBlank(importPayment.getPaymentid())){
				isSave=true;				
			}
			IInfo info =supplierClearAccountService.checkClearData(importPayment, marginAmount,isSave);
			jo.put("isRight", info.isRight());
			jo.put("info", info.getInfo());
			this.operateSuccessfullyHiddenInfoWithString(response, jo.toString());
		}else{
			jo.put("isRight", true);			
			this.operateSuccessfullyHiddenInfoWithString(response, jo.toString());
		}
		
    }
    
    public void _export(HttpServletRequest request, HttpServletResponse response,
            GridQueryCondition gridQueryCondition) throws Exception {
        
        BusinessObject businessObject = BusinessObjectService.getBusinessObject("ImportPayment");
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
        
        
        String[] titles  = new String[]{"序号","付款单号","部门","供应商名称","银行","币别","银行对外付款日","押汇到期付款日","金额","付款类型","预计到货日","纸质合同号","物料组","贸易类型","付款方式","业务状态"
        		,"押汇币别","押汇期限","押汇日(押汇)","押汇汇率","押汇利率","还押汇汇率"};
        ExcelObject excel = new ExcelObject(titles);
        paymentInnerInfoJDBCService.dealOutToExcel1(grid_sql,strAuthSql ,excel);

        
        try{
			//response.reset();
	        response.setCharacterEncoding("utf-8");
	        response.setContentType("application/x-download;charset=utf-8");
	        response.setHeader("Content-Disposition", (new StringBuilder("attachment;filename=")).append(URLEncoder.encode("进口付款表.xls", "utf-8")).toString());
			excel.write(response.getOutputStream());
			response.flushBuffer();
			response.getOutputStream().close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}

    }
    

}