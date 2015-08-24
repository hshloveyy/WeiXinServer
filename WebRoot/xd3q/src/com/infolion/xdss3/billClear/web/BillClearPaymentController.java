/*
 * @(#)BillClearPaymentController.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年06月22日 15点15分19秒
 *  描　述：创建
 */
package com.infolion.xdss3.billClear.web;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

import com.infolion.platform.bo.domain.BusinessObject;
import com.infolion.platform.bo.service.BusinessObjectService;
import com.infolion.platform.console.org.dao.SysDeptJdbcDao;
import com.infolion.platform.console.sys.context.UserContextHolder;
import com.infolion.platform.dpframework.core.BusinessException;
import com.infolion.platform.dpframework.core.annotation.BDPController;
import com.infolion.platform.dpframework.core.cache.SysCachePoolUtils;
import com.infolion.platform.dpframework.util.ExBeanUtils;
import com.infolion.platform.dpframework.util.StringUtils;
import com.infolion.platform.util.MultyGridData;
import com.infolion.platform.workflow.engine.WorkflowService;
import com.infolion.xdss3.BusinessState;
import com.infolion.xdss3.billClear.domain.BillClearItemPay;
import com.infolion.xdss3.billClear.domain.BillClearPayment;
import com.infolion.xdss3.billClear.domain.BillInPayment;
import com.infolion.xdss3.billClear.service.BillClearItemPayService;
import com.infolion.xdss3.billClear.service.BillInPaymentService;
import com.infolion.xdss3.billClearGen.web.BillClearPaymentControllerGen;
import com.infolion.xdss3.billclearitem.domain.BillClearItem;
import com.infolion.xdss3.collectitem.service.CollectItemService;
import com.infolion.xdss3.financeSquare.domain.FundFlow;
import com.infolion.xdss3.financeSquare.domain.SettleSubject;
import com.infolion.xdss3.masterData.dao.VendorTitleJdbcDao;
import com.infolion.xdss3.masterData.domain.VendorTitle;
import com.infolion.xdss3.masterData.service.VendorService;
import com.infolion.xdss3.payment.domain.ImportPaymentItem;
import com.infolion.xdss3.payment.service.PaymentItemService;
import com.infolion.xdss3.reassign.ReassignConstant;
import com.infolion.xdss3.reassign.dao.ReassignJdbcDao;
import com.infolion.xdss3.reassign.domain.Reassign;
import com.infolion.xdss3.reassign.service.ReassignService;
import com.infolion.xdss3.singleClear.domain.ClearConstant;
import com.infolion.xdss3.singleClear.domain.IInfo;
import com.infolion.xdss3.singleClear.domain.IInfoVoucher;
import com.infolion.xdss3.singleClear.domain.ParameterVoucherObject;
import com.infolion.xdss3.singleClear.service.ISupplierClearAccount;
import com.infolion.xdss3.singleClear.service.SupplierClearAccountService;
import com.infolion.xdss3.voucher.domain.Voucher;
import com.infolion.xdss3.voucher.service.VoucherService;
import com.infolion.xdss3.voucheritem.domain.VoucherItem;

/**
 * <pre>
 * 票清付款(BillClearPayment)控制器类
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
public class BillClearPaymentController extends BillClearPaymentControllerGen
{

	@Autowired
	private BillClearItemPayService billClearItemPayService;

	/**
	 * @param billClearItemPayService
	 *            the billClearItemPayService to set
	 */
	public void setBillClearItemPayService(BillClearItemPayService billClearItemPayService)
	{
		this.billClearItemPayService = billClearItemPayService;
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
	private BillInPaymentService billInPaymentService;

	/**
	 * @param billInPaymentService
	 *            the billInPaymentService to set
	 */
	public void setBillInPaymentService(BillInPaymentService billInPaymentService)
	{
		this.billInPaymentService = billInPaymentService;
	}

	@Autowired
	private VoucherService voucherService;

	/**
	 * @param voucherService
	 *            the voucherService to set
	 */
	public void setVoucherService(VoucherService voucherService)
	{
		this.voucherService = voucherService;
	}

	@Autowired
	private VendorService vendorService;

	/**
	 * @param vendorService
	 *            the vendorService to set
	 */
	public void setVendorService(VendorService vendorService)
	{
		this.vendorService = vendorService;
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

	/**
	 * @param vendorTitleJdbcDao
	 *            the vendorTitleJdbcDao to set
	 */
	public void setVendorTitleJdbcDao(VendorTitleJdbcDao vendorTitleJdbcDao)
	{
		this.vendorTitleJdbcDao = vendorTitleJdbcDao;
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
	 * 
	 * 
	 * @param request
	 * @param response
	 */
	public void getPaymentItemData(HttpServletRequest request, HttpServletResponse response)
	{
		String paymentItemIds = request.getParameter("paymentItemIds");
		String billclearid = request.getParameter("billclearid");
		// 取得未清完的预付款单
		List<ImportPaymentItem> paymentItemList = this.paymentItemService.getUnclearPaymentItemList(paymentItemIds);

		List<BillInPayment> billInPaymentList = new ArrayList<BillInPayment>();

		for (int i = 0; i < paymentItemList.size(); i++)
		{
			ImportPaymentItem paymentItem = (ImportPaymentItem) paymentItemList.get(i);

			// 预付票款
//			BigDecimal prebillamount = paymentItem.getGoodsamount();
			BigDecimal prebillamount = paymentItem.getAssignamount();
			// 付款单已审批完的 ，已清票款
			BigDecimal clearedPaymentAmount = this.paymentItemService.getSumClearAmount(paymentItem.getPaymentitemid(), BusinessState.ALL_SUBMITPASS);

			// 付款单在途的 ，在途票款
			BigDecimal onroadPaymentAmount = this.paymentItemService.getSumClearAmount(paymentItem.getPaymentitemid(), BusinessState.ALL_PAYMENT_ONROAD,billclearid);

			BillInPayment billInPayment = new BillInPayment();
			billInPayment.setPaymentitemid(paymentItem.getPaymentitemid());
			VoucherItem voucherItem2 = billClearPaymentService.getVoucherItem(paymentItem.getPaymentitemid(),"1,4");
			
			if(null !=voucherItem2){
				billInPayment.setVoucherno(voucherItem2.getVoucher().getVoucherno());
				String rowNumber = voucherItem2.getRownumber();
				if(rowNumber.length()==2)rowNumber="0" + rowNumber;			
				if(rowNumber.length()==1)rowNumber="00" + rowNumber;
				VendorTitle vendorTitle = vendorTitleJdbcDao.getByVoucherNo(voucherItem2.getVoucher().getCompanycode(), voucherItem2.getVoucher().getVoucherno(), voucherItem2.getVoucher().getFiyear(), rowNumber);
				if("1".equals(vendorTitle.getIscleared()) || "2".equals(vendorTitle.getIscleared())){
					continue;	
				}
			}else{
				//没有凭证号的不显示
				billInPayment.setVoucherno("");
				continue;
			}
			
			billInPayment.setProject_no(paymentItem.getProject_no());
			billInPayment.setContract_no(paymentItem.getContract_no());
			billInPayment.setPaymentno(paymentItem.getPaymentno());
			billInPayment.setPaymentid(paymentItem.getImportPayment().getPaymentid());
			billInPayment.setArrivegoodsdate(paymentItem.getImportPayment().getArrivegoodsdate());
			billInPayment.setPaymentamount(prebillamount);
			billInPayment.setCurrency(paymentItem.getImportPayment().getFactcurrency());
			billInPayment.setAccountdate(voucherItem2.getVoucher().getCheckdate());
			
			billInPayment.setBktxt(paymentItem.getImportPayment().getText());
			billInPayment.setOldcontract_no(paymentItem.getOld_contract_no());
			billInPayment.setOffsetamount(clearedPaymentAmount);
			billInPayment.setUnoffsetamount(prebillamount.subtract(clearedPaymentAmount).subtract(onroadPaymentAmount));
			billInPayment.setOnroadamount(onroadPaymentAmount);
			billInPayment.setBwbje(paymentItem.getAssignamount2());
			//未抵消金额为0不显示
			if(billInPayment.getUnoffsetamount().compareTo(BigDecimal.valueOf(0)) <= 0){
				continue;
			}
			//汇率
			BigDecimal rat = billInPayment.getBwbje().divide(billInPayment.getPaymentamount(),5,BigDecimal.ROUND_HALF_EVEN);
			billInPayment.setExchangerate(rat);
			billInPayment.setUnbwbje(billInPayment.getUnoffsetamount().multiply(rat).setScale(2, BigDecimal.ROUND_HALF_UP));
			billInPaymentList.add(billInPayment);
		}

		MultyGridData gridJsonData = new MultyGridData();
		gridJsonData.setData(billInPaymentList.toArray());
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
	 * 通过传入供应商未清数据抬头ID来初始化票清款行项数据
	 * 
	 * @param request
	 * @param response
	 */
	public void getBillClearItemData(HttpServletRequest request, HttpServletResponse response)
	{
		String vendortitleids = request.getParameter("vendortitleids");
		String supplier = request.getParameter("supplier");
		String billclearid = request.getParameter("billclearid");
		// 取得供应商下未清发票数据集合
		List<VendorTitle> vendorTitleList = this.vendorService.getVendorTitleList(vendortitleids);
		
		// 票清付款行项目
		List<BillClearItemPay> billClearItemPayList = new ArrayList<BillClearItemPay>();
		
		// 遍历所有供应商下未清发票数据集合
		for (Iterator<VendorTitle> it = vendorTitleList.iterator(); it.hasNext();)
		{
			VendorTitle vendorTitle = (VendorTitle) it.next();
			BillClearItemPay billClearItemPay = new BillClearItemPay();
			// String billClearitemId = CodeGenerator.getUUID();
			// billClearItemPay.setBillclearitemid(billClearitemId);
			billClearItemPay.setTitleid(vendorTitle.getVendortitleid());
			billClearItemPay.setInvoice(vendorTitle.getInvoice());
			billClearItemPay.setCurrency(vendorTitle.getWaers());
			billClearItemPay.setVoucherno(vendorTitle.getBelnr());
			billClearItemPay.setProject_no(vendorTitle.getLixiang());
			billClearItemPay.setContract_no(vendorTitle.getVerkf());
			billClearItemPay.setAccountdate(vendorTitle.getBudat());
			// billClearItemPay.setExport_apply_no(""); // 到单号
			billClearItemPay.setOld_contract_no(this.paymentItemService.getOldContractNo(vendorTitle.getVerkf())); // 纸质合同号码
			billClearItemPay.setSap_order_no(vendorTitle.getEbeln()); // sap订单号,采购凭证号
			billClearItemPay.setBktxt(vendorTitle.getBktxt());
			// 发票总金额,应付款.
			BigDecimal billamount = vendorTitle.getDmbtr();
			billClearItemPay.setReceivableamount(billamount);

			// 发票已经审批完的，发票已清金额
			BigDecimal receivedamount = this.vendorService.getSumClearAmount(vendorTitle.getInvoice(), BusinessState.ALL_SUBMITPASS);
			billClearItemPay.setReceivedamount(receivedamount);
			

			// 发票在途的，发票在途金额
			BigDecimal onroadamount = this.vendorService.getSumClearAmount(vendorTitle.getInvoice(), BusinessState.ALL_PAYMENT_ONROAD,billclearid);
			
			billClearItemPay.setUnreceivedamount(billamount.subtract(receivedamount).subtract(onroadamount));
			
			billClearItemPay.setOnroadamount(onroadamount);
			billClearItemPay.setClearbillamount(BigDecimal.valueOf(0));
			billClearItemPay.setBwbje(vendorTitle.getBwbje());
			//汇率
			BigDecimal rat = billClearItemPay.getBwbje().divide(billClearItemPay.getReceivableamount(),5,BigDecimal.ROUND_HALF_EVEN);
			billClearItemPay.setExchangerate(rat);
			billClearItemPay.setUnbwbje(billClearItemPay.getUnreceivedamount().multiply(rat).setScale(2, BigDecimal.ROUND_HALF_UP));			
			//未付款为0不显示
			if(billClearItemPay.getUnreceivedamount().compareTo(BigDecimal.valueOf(0)) <= 0){
				continue;
			}

			billClearItemPayList.add(billClearItemPay);
		}

		MultyGridData gridJsonData = new MultyGridData();
		gridJsonData.setData(billClearItemPayList.toArray());
		
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
	 * 模拟凭证
	 * 
	 * @param request
	 * @param response
	 */
	@Override
	public void _voucherPreview(HttpServletRequest request, HttpServletResponse response)
	{
		JSONObject jo = new JSONObject();
		String deptid1_htext = request.getParameter("SettleSubjectPay.deptid1_htext");
		log.debug("deptid1_htext: " + deptid1_htext);
		// 绑定主对象值
		BillClearPayment billClearPayment = (BillClearPayment) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), BillClearPayment.class, true, request.getMethod(), true);
		
		
		
		
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<BillClearItemPay> billClearItemPays = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { billClearPayment }, BillClearItemPay.class, null);
		billClearPayment.setBillClearItemPay(billClearItemPays);
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<BillInPayment> billInPayments = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { billClearPayment }, BillInPayment.class, null);
		billClearPayment.setBillInPayment(billInPayments);request.getParameterMap().get("BillInPayment.projectno");
		// 绑定子对象(一对一关系)
		FundFlow fundFlow = (FundFlow) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), FundFlow.class, false, request.getMethod(), true);
		if (fundFlow != null)
		{
			billClearPayment.setFundFlow(fundFlow);
			fundFlow.setBillClearPayment(billClearPayment);
		}
		// 绑定子对象(一对一关系)
		SettleSubject settleSubject = (SettleSubject) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), SettleSubject.class, false, request.getMethod(), true);
		if (settleSubject != null)
		{
			billClearPayment.setSettleSubject(settleSubject);
			settleSubject.setBillClearPayment(billClearPayment);
		}

		this.billClearPaymentService._saveOrUpdate(billClearPayment);
		String businessId = billClearPayment.getBillclearid();
		this.voucherService.deleteVoucherByBusinessid(businessId);
		List<String> voucherIds = this.billClearPaymentService._saveVoucher(billClearPayment, billClearItemPays, fundFlow, settleSubject);
		for (int i = 0; i < voucherIds.size(); i++)
		{
			if("!null".equals(voucherIds.get(i))){
				throw new BusinessException("调整金额要等于借款金额减贷款金额！");
			}
		}
		//判断是否需要删除
		this.voucherService.judgeVoucherNeedDel(businessId);
		
		String returnVoucherIds = "";
//		for (int i = 0; i < voucherIds.size(); i++)
//		{
//			returnVoucherIds += voucherIds.get(i);
//			if (i + 1 < voucherIds.size())
//			{
//				returnVoucherIds += "&";
//			}
//		}
		if(null != voucherIds && voucherIds.size()!=0) returnVoucherIds = billClearPayment.getBillclearid();
		this.operateSuccessfullyHiddenInfoWithString(response, returnVoucherIds);
	}

	/**
	 * 模拟凭证2
	 * 
	 * @param request
	 * @param response
	 */
	
	public void _voucherPreview2(HttpServletRequest request, HttpServletResponse response)
	{
		JSONObject jo = new JSONObject();
		String deptid1_htext = request.getParameter("SettleSubjectPay.deptid1_htext");
		log.debug("deptid1_htext: " + deptid1_htext);
		// 绑定主对象值
		BillClearPayment billClearPayment = (BillClearPayment) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), BillClearPayment.class, true, request.getMethod(), true);
		
		
		
		
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<BillClearItemPay> billClearItemPays = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { billClearPayment }, BillClearItemPay.class, null);
		billClearPayment.setBillClearItemPay(billClearItemPays);
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<BillInPayment> billInPayments = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { billClearPayment }, BillInPayment.class, null);
		billClearPayment.setBillInPayment(billInPayments);request.getParameterMap().get("BillInPayment.projectno");
		// 绑定子对象(一对一关系)
		FundFlow fundFlow = (FundFlow) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), FundFlow.class, false, request.getMethod(), true);
		if (fundFlow != null)
		{
			billClearPayment.setFundFlow(fundFlow);
			fundFlow.setBillClearPayment(billClearPayment);
		}
		// 绑定子对象(一对一关系)
		SettleSubject settleSubject = (SettleSubject) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), SettleSubject.class, false, request.getMethod(), true);
		if (settleSubject != null)
		{
			billClearPayment.setSettleSubject(settleSubject);
			settleSubject.setBillClearPayment(billClearPayment);
		}

		this.billClearPaymentService._saveOrUpdate(billClearPayment);
		String businessId = billClearPayment.getBillclearid();
		// 先删除凭证信息。
		this.voucherService.deleteVoucherByBusinessid(businessId);
		ParameterVoucherObject parameterVoucher =supplierClearAccountService.setParameter(billClearPayment);
		BigDecimal marginAmount = new BigDecimal("0");
		BigDecimal marginAmount_bwb = new BigDecimal("0");
		Voucher settleSubjectVoucher = null;
		
		if (settleSubject != null || fundFlow != null){			
			settleSubjectVoucher = supplierClearAccountService.saveSettleSubjectVoucher(parameterVoucher);				
			marginAmount = supplierClearAccountService.getMargin(settleSubjectVoucher.getVoucherItem());
			marginAmount_bwb =supplierClearAccountService.getMarginByBwb(settleSubjectVoucher.getVoucherItem());
//			客户跟供应商借贷方相反
			marginAmount = new BigDecimal("0").subtract(marginAmount);
			marginAmount_bwb = new BigDecimal("0").subtract(marginAmount_bwb);
			parameterVoucher.setVoucherid(settleSubjectVoucher.getVoucherid());
		}
		//判断是否需要删除
		this.voucherService.judgeVoucherNeedDel(businessId);
		boolean isSave = false;
		if(!StringUtils.isNullBlank(businessId))isSave=true;
		IInfo info =supplierClearAccountService.checkClearData(billClearPayment, marginAmount,isSave);

//		判断本次数据是否正确
		if(info.isRight()){
			IInfoVoucher infoVoucher = supplierClearAccountService.isClearAccount(billClearPayment, marginAmount_bwb);
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
					Voucher clearVoucher =supplierClearAccountService.saveClearVoucherBySupplier(parameterVoucher,infoVoucher,businessId,ClearConstant.BILL_TYPE_P,isp);
//					数据有错误
					if(null == clearVoucher){
						jo.put("isRight", false);
						jo.put("info", info.CODE_9);
						this.operateSuccessfullyHiddenInfoWithString(response, jo.toString());
					}
				}else{
//				部分清（有外币出汇损）,并且差额不为0
					if(isp && parameterVoucher.getSubtractVlaue().compareTo(new BigDecimal("0"))!= 0){									
						Voucher plVoucher = supplierClearAccountService.saveProfitAndLossVoucher(parameterVoucher);
					}else{
//						保存本次全清的数据，用来更新isclear状态
						request.getSession().setAttribute(businessId, infoVoucher);
						//判断是否需要删除
						this.voucherService.judgeVoucherNeedDel(businessId);				
						jo.put("businessid", businessId);				
						jo.put("isRight", true);
						jo.put("isClear", false);
						this.operateSuccessfullyHiddenInfoWithString(response, jo.toString());
					}
					
				}
//				保存本次全清的数据，用来更新isclear状态
				request.getSession().setAttribute(businessId, infoVoucher);
				//判断是否需要删除
				this.voucherService.judgeVoucherNeedDel(businessId);				
				jo.put("businessid", businessId);				
				jo.put("isRight", true);
				jo.put("isClear", true);
				this.operateSuccessfullyHiddenInfoWithString(response, jo.toString());
			}else{
//				数据错误不能保存
				
				jo.put("isRight", infoVoucher.isRight());
				jo.put("info", infoVoucher.getInfo());
				this.operateSuccessfullyHiddenInfoWithString(response, jo.toString());
			}
		}else{
//			数据错误不能保存
			
			jo.put("isRight", info.isRight());
			jo.put("info", info.getInfo());
			this.operateSuccessfullyHiddenInfoWithString(response, jo.toString());
		}
	}
	
	/**
	 * 自动分配
	 * 
	 * @param request
	 * @param response
	 */
	@Override
	public void _autoAssign(HttpServletRequest request, HttpServletResponse response)
	{
		String vendortitleids = request.getParameter("vendortitleids");
		String supplier = request.getParameter("supplier");
		String billclearid = request.getParameter("billclearid");
		String paymentItemIds = request.getParameter("paymentItemIds");

		
		// 取得供应商下未清发票数据集合
		List<VendorTitle> vendorTitleList = this.vendorService.getVendorTitleList(vendortitleids);
		Collections.reverse(vendorTitleList);
		// 取得未清完的付款单明细。
		List<ImportPaymentItem> paymentItemList = new ArrayList<ImportPaymentItem>();
		if(null == paymentItemIds  || "".equals(paymentItemIds)){
			paymentItemList = this.paymentItemService.getUnclearPaymentItemList(supplier, BusinessState.ALL_SUBMITPASS);
		}else{
			
			paymentItemList = this.paymentItemService.getUnclearPaymentItemList(paymentItemIds);

		}
		// 票清付款行项目
		List<BillClearItemPay> billClearItemPayList = new ArrayList<BillClearItemPay>();
		// 票款关系表。
		List<BillInPayment> billInPaymentList = new ArrayList<BillInPayment>();
		// 遍历所有供应商下未清发票数据集合
		for (Iterator<VendorTitle> it = vendorTitleList.iterator(); it.hasNext();)
		{
			VendorTitle vendorTitle = (VendorTitle) it.next();
			BillClearItemPay billClearItemPay = new BillClearItemPay();
			// String billClearitemId = CodeGenerator.getUUID();
			// billClearItemPay.setBillclearitemid(billClearitemId);
			billClearItemPay.setTitleid(vendorTitle.getVendortitleid());
			billClearItemPay.setInvoice(vendorTitle.getInvoice());
			billClearItemPay.setCurrency(vendorTitle.getWaers());
			billClearItemPay.setVoucherno(vendorTitle.getBelnr());
			billClearItemPay.setProject_no(vendorTitle.getLixiang());
			billClearItemPay.setContract_no(vendorTitle.getVerkf());
			billClearItemPay.setAccountdate(vendorTitle.getBudat());
			// billClearItemPay.setExport_apply_no(""); // 到单号
			billClearItemPay.setOld_contract_no(this.paymentItemService.getOldContractNo(vendorTitle.getVerkf())); // 纸质合同号码
			billClearItemPay.setSap_order_no(vendorTitle.getEbeln()); // sap订单号,采购凭证号
			billClearItemPay.setBktxt(vendorTitle.getBktxt());
			// 发票总金额,应付款.
			BigDecimal billamount = vendorTitle.getDmbtr();
			billClearItemPay.setReceivableamount(billamount);

			// 发票已经审批完的，发票已清金额
			BigDecimal receivedamount = this.vendorService.getSumClearAmount(vendorTitle.getInvoice(), BusinessState.ALL_SUBMITPASS);
			billClearItemPay.setReceivedamount(receivedamount);
			

			// 发票在途的，发票在途金额
			BigDecimal onroadamount = this.vendorService.getSumClearAmount(vendorTitle.getInvoice(), BusinessState.ALL_PAYMENT_ONROAD,billclearid);
			
			billClearItemPay.setUnreceivedamount(billamount.subtract(receivedamount).subtract(onroadamount));
			billClearItemPay.setOnroadamount(onroadamount);
			billClearItemPay.setClearbillamount(BigDecimal.valueOf(0));
			billClearItemPay.setBwbje(vendorTitle.getBwbje());
			//未付款为0不显示
			if(billClearItemPay.getUnreceivedamount().compareTo(BigDecimal.valueOf(0)) <= 0){
				continue;
			}
			//汇率
			BigDecimal rat = billClearItemPay.getBwbje().divide(billClearItemPay.getReceivableamount(),5,BigDecimal.ROUND_HALF_EVEN);
			billClearItemPay.setExchangerate(rat);
			billClearItemPay.setUnbwbje(billClearItemPay.getUnreceivedamount().multiply(rat).setScale(2, BigDecimal.ROUND_HALF_UP));
			
			// 遍历 未清完的付款单明细
			for (int i = 0; i < paymentItemList.size(); i++)
			{
				// 发票总金额-发票已清金额-发票在途金额-清票金额
				BigDecimal billreaminamount = billamount.subtract(receivedamount).subtract(onroadamount).subtract(billClearItemPay.getClearbillamount());
				ImportPaymentItem paymentItem = paymentItemList.get(i);

				// 要取同一个合同的付款单，还要取同一个立项下没有合同的付款单
				if ((billClearItemPay.getContract_no().equalsIgnoreCase(paymentItem.getContract_no()) && !("").equals(billClearItemPay.getContract_no().trim())) 
					|| (billClearItemPay.getProject_no().equals(paymentItem.getProject_no()) && ("").equals(paymentItem.getContract_no().trim())))
				{
					// 本次已清金额
					if (paymentItem.getClearedamount() == null)
						paymentItem.setClearedamount(BigDecimal.valueOf(0));

					// 预付票款
					BigDecimal prebillamount = paymentItem.getGoodsamount();
					// 付款单已审批完的
					BigDecimal clearedPaymentAmount = this.paymentItemService.getSumClearAmount(paymentItem.getPaymentitemid(), BusinessState.ALL_SUBMITPASS);
					// 付款单在途的
					BigDecimal onroadPaymentAmount = this.paymentItemService.getSumClearAmount(paymentItem.getPaymentitemid(), BusinessState.ALL_PAYMENT_ONROAD,billclearid);

					BigDecimal paymentremainamount = prebillamount.subtract(clearedPaymentAmount).subtract(onroadPaymentAmount).subtract(paymentItem.getClearedamount());
					BigDecimal unoffsetamount = prebillamount.subtract(clearedPaymentAmount).subtract(onroadPaymentAmount);
					if (paymentremainamount.compareTo(BigDecimal.valueOf(0)) <= 0)
						continue;

					BillInPayment billInPayment = new BillInPayment();

					VoucherItem voucherItem2 = billClearPaymentService.getVoucherItem(paymentItem.getPaymentitemid(),"1,4");
					if(null !=voucherItem2){
						billInPayment.setVoucherno(voucherItem2.getVoucher().getVoucherno());
						String rowNumber = voucherItem2.getRownumber();
						if(rowNumber.length()==2)rowNumber="0" + rowNumber;			
						if(rowNumber.length()==1)rowNumber="00" + rowNumber;
						VendorTitle vendorTitle2 = vendorTitleJdbcDao.getByVoucherNo(voucherItem2.getVoucher().getCompanycode(), voucherItem2.getVoucher().getVoucherno(), voucherItem2.getVoucher().getFiyear(), rowNumber);
						if("1".equals(vendorTitle2.getIscleared()) || "2".equals(vendorTitle2.getIscleared())){
							continue;	
						}
					}else{
						billInPayment.setVoucherno(" ");
						//没有凭证号的不显示
						continue;
					}
					billInPayment.setAccountdate(voucherItem2.getVoucher().getCheckdate());
					
					billInPayment.setPaymentitemid(paymentItem.getPaymentitemid());
					billInPayment.setProject_no(paymentItem.getProject_no());
					billInPayment.setContract_no(paymentItem.getContract_no());
					billInPayment.setPaymentno(paymentItem.getPaymentno());
					billInPayment.setPaymentid(paymentItem.getImportPayment().getPaymentid());
					billInPayment.setArrivegoodsdate(paymentItem.getImportPayment().getArrivegoodsdate());
					billInPayment.setPaymentamount(prebillamount);
					billInPayment.setCurrency(paymentItem.getImportPayment().getCurrency());
//					billInPayment.setExchangerate(paymentItem.getImportPayment().getExchangerate());
					billInPayment.setOffsetamount(clearedPaymentAmount);
					billInPayment.setUnoffsetamount(unoffsetamount);
					billInPayment.setOnroadamount(onroadPaymentAmount);
					billInPayment.setBwbje(paymentItem.getAssignamount2());
					billInPayment.setBktxt(paymentItem.getImportPayment().getText());
					billInPayment.setOldcontract_no(paymentItem.getOld_contract_no());
					//汇率
					BigDecimal rat2 = billInPayment.getBwbje().divide(billInPayment.getPaymentamount(),5,BigDecimal.ROUND_HALF_EVEN);
					billInPayment.setExchangerate(rat2);
					billInPayment.setUnbwbje(billInPayment.getUnoffsetamount().multiply(rat2).setScale(2, BigDecimal.ROUND_HALF_UP));
					
					//如果票没有分配金也显示出来
					if (billreaminamount.compareTo(BigDecimal.valueOf(0)) == 0){
						billInPaymentList.add(billInPayment);
						continue;					
					}
					if (billInPayment.getNowclearamount() == null)
						billInPayment.setNowclearamount(BigDecimal.valueOf(0));
					// 发票剩余被清金额 > 付款单剩余可用金额
					if (billreaminamount.compareTo(paymentremainamount) == 1 || billreaminamount.compareTo(paymentremainamount) == 0)
					{
						paymentItem.setClearedamount(paymentItem.getClearedamount().add(paymentremainamount));
						billClearItemPay.setClearbillamount(billClearItemPay.getClearbillamount().add(paymentremainamount));
						billInPayment.setNowclearamount(billInPayment.getNowclearamount().add(paymentItem.getClearedamount()));
						
						billInPaymentList.add(billInPayment);
					}
					else
					{
						paymentItem.setClearedamount(paymentItem.getClearedamount().add(billreaminamount));
						billClearItemPay.setClearbillamount(billClearItemPay.getClearbillamount().add(billreaminamount));
						billInPayment.setNowclearamount(billInPayment.getNowclearamount().add(paymentItem.getClearedamount()));
//						if (it.hasNext())
//							continue;
//						else
						
						billInPaymentList.add(billInPayment);
					}
					
				}
			}

			billClearItemPayList.add(billClearItemPay);
		}
	
		
		List<BillInPayment> billInPaymentList2 = new ArrayList<BillInPayment>();
		Collections.reverse(billInPaymentList);
		for(BillInPayment billinpayment2: billInPaymentList){
			boolean billFalg=true;
			if(billinpayment2.getNowclearamount() == null) billinpayment2.setNowclearamount(BigDecimal.valueOf(0));
			for(BillInPayment billinpayment3: billInPaymentList2){
				if(billinpayment2.getPaymentitemid().equals(billinpayment3.getPaymentitemid())){
					billFalg=false;
					break;
				}
			}
			if(billFalg)billInPaymentList2.add(billinpayment2);
		}
	
		MultyGridData gridJsonData = new MultyGridData();
		gridJsonData.setData(billClearItemPayList.toArray());
		gridJsonData.setData2(billInPaymentList2.toArray());
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
	 * 清除分配
	 * 
	 * @param request
	 * @param response
	 */
	@Override
	public void _clearAssign(HttpServletRequest request, HttpServletResponse response)
	{
		// 绑定主对象值
		BillClearPayment billClearPayment = (BillClearPayment) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), BillClearPayment.class, true, request.getMethod(), true);
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<BillClearItemPay> billClearItemPays = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { billClearPayment }, BillClearItemPay.class, null);
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<BillInPayment> billInPayments = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { billClearPayment }, BillInPayment.class, null);
		this.billClearPaymentService._clearAssign(billClearPayment, billClearItemPays, billInPayments);
		this.operateSuccessfully(response);
	}

	/**
	 * 创建
	 * 
	 * @param request
	 * @param response
	 */
	@Override
	public ModelAndView _create(HttpServletRequest request, HttpServletResponse response)
	{
		String calActivityId = request.getParameter("calActivityId");
		if (StringUtils.isNotBlank(calActivityId))
			request.setAttribute("calActivityId", calActivityId);
		BillClearPayment billClearPayment = new BillClearPayment();
		String workflowTaskId = request.getParameter("workflowTaskId");
		String workflowNodeDefId = request.getParameter("workflowNodeDefId");
		billClearPayment.setBusinessstate("0");
		// 开始接收从_manage方法传递过来的参数。
		String cleartypeManager = request.getParameter("cleartype");
		billClearPayment.setCleartype(cleartypeManager);
		// 填入部门ID值。
		billClearPayment.setDeptid(UserContextHolder.getLocalUserContext().getUserContext().getSysUser().getDeptId());

		request.setAttribute("vt", getBusinessObject().getViewText());
		if (null != getBusinessObject().getSubBusinessObject())
		{
			// 取得子业务对象
			for (BusinessObject bo : getBusinessObject().getSubBusinessObject())
			{
				request.setAttribute(bo.getBeanAttribute().getBeanInstanceName() + "Vt", bo.getViewText());
			}
		}

		request.setAttribute("billClearPayment", billClearPayment);
		boolean haveBindWorkFlow = WorkflowService.getHaveBindWorkFlow("000000000297");
		request.setAttribute("haveBindWorkFlow", haveBindWorkFlow);
		request.setAttribute("workflowTaskId", workflowTaskId);
		request.setAttribute("workflowNodeDefId", workflowNodeDefId);
		return new ModelAndView("xdss3/billClear/billClearPaymentAdd");
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
		BillClearPayment billClearPayment = (BillClearPayment) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), BillClearPayment.class, true, request.getMethod(), true);
		// 类型标识是从那个页面提交，view 表示从view页面提交流程。
		String type = request.getParameter("type");
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<BillClearItemPay> billClearItemPaymodifyItems = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { billClearPayment }, BillClearItemPay.class, null);
		Set<BillClearItemPay> deletedBillClearItemPaySet = ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(), new Object[] { billClearPayment }, BillClearItemPay.class, null);
		billClearPayment.setBillClearItemPay(billClearItemPaymodifyItems);
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<BillInPayment> billInPaymentmodifyItems = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { billClearPayment }, BillInPayment.class, null);
		Set<BillInPayment> deletedBillInPaymentSet = ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(), new Object[] { billClearPayment }, BillInPayment.class, null);
		billClearPayment.setBillInPayment(billInPaymentmodifyItems);
		// 绑定子对象(一对一关系)
		FundFlow fundFlow = (FundFlow) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), FundFlow.class, false, request.getMethod(), true);
		if (fundFlow != null)
		{
			billClearPayment.setFundFlow(fundFlow);
			fundFlow.setBillClearPayment(billClearPayment);
		}
		// 绑定子对象(一对一关系)
		SettleSubject settleSubject = (SettleSubject) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), SettleSubject.class, false, request.getMethod(), true);
		if (settleSubject != null)
		{
			billClearPayment.setSettleSubject(settleSubject);
			settleSubject.setBillClearPayment(billClearPayment);
		}
		if("财务会计审核".equals(billClearPayment.getProcessstate()) && "退回业务修改".equals(billClearPayment.getWorkflowLeaveTransitionName()) ){
			billClearPayment.setBusinessstate("0");			
		}
		if("业务进行清账分配".equals(billClearPayment.getProcessstate()) || "修改".equals(billClearPayment.getProcessstate())){
			billClearPayment.setBusinessstate("1");
		}
		if (!"view".equalsIgnoreCase(type))
		{
			this.billClearPaymentService._saveOrUpdate(billClearPayment, deletedBillClearItemPaySet, deletedBillInPaymentSet, getBusinessObject());
		}
		if("财务会计审核".equals(billClearPayment.getProcessstate()) && "通过".equals(billClearPayment.getWorkflowLeaveTransitionName()) ){
			//更新isclear状态
//			if(this.billClearPaymentService.checkVoucher(billClearPayment.getBillclearid(), "A")){
//				this.billClearPaymentService.updateOldTitle(billClearPayment, true);
//			}else{
//				this.billClearPaymentService.updateOldTitle(billClearPayment, false);
//			}
//		
//			this.billClearPaymentService.updatePaymentItemIsCleared(billClearPayment.getBillclearid());
//			this.billClearPaymentService.updateVendorTitleIsCleared(billClearPayment.getBillclearid());
			
			// 供应商未清抬头数据中的数据，判断是否已结清
			String bussinessid = billClearPayment.getBillclearid();
			IInfoVoucher infoVoucher = (IInfoVoucher)request.getSession().getAttribute(bussinessid);
//			是否全清
			if(infoVoucher.isClear()){
				supplierClearAccountService.updateIsClear(bussinessid);
			}else{
				supplierClearAccountService.updateIsClear(infoVoucher);
			}		
			request.getSession().removeAttribute(bussinessid);

			
		}
		String workflowBusinessNote = "";
		String deptName = com.infolion.platform.console.sys.context.UserContextHolder.getLocalUserContext().getUserContext().getSysUser().getDeptName();
		String creator = com.infolion.platform.console.sys.context.UserContextHolder.getLocalUserContext().getUserContext().getSysUser().getRealName();
		//  2010-11-11 添加信息进待办信息	
		String projectNo=" ";
		for(BillClearItemPay billClearItemPay :billClearItemPaymodifyItems){
			projectNo=billClearItemPay.getProject_no();
			break;
		}
		
		workflowBusinessNote = billClearPayment.getBillclearno() + "|" + deptName + "|" + creator + "|立项号" + projectNo;
		
		billClearPayment.setWorkflowBusinessNote(workflowBusinessNote);
		this.billClearPaymentService._submitProcess(billClearPayment, deletedBillClearItemPaySet, deletedBillInPaymentSet, getBusinessObject());
		this.operateSuccessfully(response);
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
		BillClearPayment billClearPayment = (BillClearPayment) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), BillClearPayment.class, true, request.getMethod(), true);
		// 类型标识是从那个页面提交，view 表示从view页面提交流程。
		String type = request.getParameter("type");
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<BillClearItemPay> billClearItemPaymodifyItems = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { billClearPayment }, BillClearItemPay.class, null);
		Set<BillClearItemPay> deletedBillClearItemPaySet = ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(), new Object[] { billClearPayment }, BillClearItemPay.class, null);
		billClearPayment.setBillClearItemPay(billClearItemPaymodifyItems);
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<BillInPayment> billInPaymentmodifyItems = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { billClearPayment }, BillInPayment.class, null);
		Set<BillInPayment> deletedBillInPaymentSet = ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(), new Object[] { billClearPayment }, BillInPayment.class, null);
		billClearPayment.setBillInPayment(billInPaymentmodifyItems);
		// 绑定子对象(一对一关系)
		FundFlow fundFlow = (FundFlow) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), FundFlow.class, false, request.getMethod(), true);
		if (fundFlow != null)
		{
			billClearPayment.setFundFlow(fundFlow);
			fundFlow.setBillClearPayment(billClearPayment);
		}
		// 绑定子对象(一对一关系)
		SettleSubject settleSubject = (SettleSubject) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), SettleSubject.class, false, request.getMethod(), true);
		if (settleSubject != null)
		{
			billClearPayment.setSettleSubject(settleSubject);
			settleSubject.setBillClearPayment(billClearPayment);
		}
		if (!"view".equalsIgnoreCase(type))
		{
			this.billClearPaymentService._saveOrUpdate(billClearPayment, deletedBillClearItemPaySet, deletedBillInPaymentSet, getBusinessObject());
		}
		jo.put("billclearno", billClearPayment.getBillclearno());
		jo.put("billclearid", billClearPayment.getBillclearid());
		jo.put("fundflowid", billClearPayment.getFundFlow() != null ? billClearPayment.getFundFlow().getFundflowid() : "");
		jo.put("settlesubjectid", billClearPayment.getSettleSubject() != null ? billClearPayment.getSettleSubject().getSettlesubjectid() : "");
		String creator = billClearPayment.getCreator();
		String creator_text = SysCachePoolUtils.getDictDataValue("YUSER", creator);
		jo.put("creator_text", creator_text);
		jo.put("creator", creator);
		jo.put("createtime", billClearPayment.getCreatetime());
		String lastmodifyer = billClearPayment.getLastmodifyer();
		String lastmodifyer_text = SysCachePoolUtils.getDictDataValue("YUSER", lastmodifyer);
		jo.put("lastmodifyer_text", lastmodifyer_text);
		jo.put("lastmodifyer", lastmodifyer);
		jo.put("lastmodifytime", billClearPayment.getLastmodifytime());
		this.operateSuccessfullyWithString(response, jo.toString());

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
        BillClearPayment billClearPayment;
        String boid = this.reassignJdbcDao.isCopyed(id, ReassignConstant.BILLCLEARPAYMENT);
        if(boid != null){
            billClearPayment = this.billClearPaymentService.getBillClearPaymentById(boid);
        }else{
            billClearPayment = this.billClearPaymentService._getEntityCopy(id);
            billClearPayment.setCreatetime("");
            request.setAttribute("isCreateCopy", "true");
            billClearPayment.setOldbillclearid(id); // 设置被重分配退款ID
            billClearPayment.setBusinessstate(BusinessState.REASSIGNED); // 设置业务状态为重分配
            billClearPayment.setText(reassign.getText()); // 设置文本为重分配提交时填写文本

            /**
             * 重新计算金额
             */
            Set<BillClearItemPay> newBillClearItemPaySet = new HashSet<BillClearItemPay>();
            for(BillClearItemPay billClearItemPay : billClearPayment.getBillClearItemPay()){
                billClearItemPay.setBillclearitemid(null);
                // 发票已经审批完的，发票已清金额
                BigDecimal receivedamount = this.vendorService.getSumClearAmount(billClearItemPay.getInvoice(), BusinessState.ALL_BILLCLEAR_PAIDUP);
                billClearItemPay.setReceivedamount(receivedamount);
                billClearItemPay.setUnreceivedamount(billClearItemPay.getReceivableamount().subtract(receivedamount));

                // 发票在途的，发票在途金额
                BigDecimal onroadamount = this.vendorService.getSumClearAmount(billClearItemPay.getInvoice(), BusinessState.ALL_BILLCLEAR_ONROAD);
                billClearItemPay.setOnroadamount(onroadamount);

                newBillClearItemPaySet.add(billClearItemPay);
            }
            billClearPayment.setBillClearItemPay(newBillClearItemPaySet);

            Set<BillInPayment> newBillInPaymentSet = new HashSet<BillInPayment>();
            for(BillInPayment billInPayment : billClearPayment.getBillInPayment()){
                billInPayment.setBillnpaymentid(null);
                // 付款单已审批完的
                BigDecimal clearedPaymentAmount = this.paymentItemService.getSumClearAmount(billInPayment.getPaymentitemid(),
                        BusinessState.ALL_SUBMITPASS);

                // 付款单在途的
                BigDecimal onroadPaymentAmount = this.paymentItemService
                        .getSumClearAmount(billInPayment.getPaymentitemid(), BusinessState.ALL_ONROAD);

                billInPayment.setOffsetamount(clearedPaymentAmount);
                billInPayment.setUnoffsetamount(billInPayment.getPaymentamount().subtract(clearedPaymentAmount));
                billInPayment.setOnroadamount(onroadPaymentAmount);
                newBillInPaymentSet.add(billInPayment);
            }
            billClearPayment.setBillInPayment(newBillInPaymentSet);

        }
        request.setAttribute("billClearPayment", billClearPayment);
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
        return new ModelAndView("xdss3/billClear/billClearPaymentAdd");
    }

	/**
	 * 重分配查看
	 * 
	 * @param request
	 * @param response
	 */
	public ModelAndView _viewForReassign(HttpServletRequest request, HttpServletResponse response)
	{

		BillClearPayment billClearPayment = new BillClearPayment();
		String workflowTaskId = request.getParameter("workflowTaskId");
		String workflowNodeDefId = request.getParameter("workflowNodeDefId");
		String businessId = request.getParameter("businessId");
		String oldId = this.reassignJdbcDao.getBoidByReassignid(businessId);
		String id = this.reassignJdbcDao.getNewNoIdByOldNoId(ReassignConstant.BILLCLEARPAYMENT, oldId);

		if (null == id)
			id = businessId;

		if (StringUtils.isNullBlank(id))
		{
			billClearPayment = this.billClearPaymentService._getForEdit(id);
		}
		else
		{
			billClearPayment = this.billClearPaymentService._getForEdit(id);
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
		request.setAttribute("billClearPayment", billClearPayment);
		return new ModelAndView("xdss3/billClear/billClearPaymentView");
	}
	
	/**
	 * 重分配编辑
	 * 
	 * @param request
	 * @param response
	 */
	public ModelAndView _editForReassign(HttpServletRequest request, HttpServletResponse response)
	{

		BillClearPayment billClearPayment = new BillClearPayment();
		String workflowTaskId = request.getParameter("workflowTaskId");
		String workflowNodeDefId = request.getParameter("workflowNodeDefId");
		String businessId = request.getParameter("businessId");
		String oldId = this.reassignJdbcDao.getBoidByReassignid(businessId);
		String id = this.reassignJdbcDao.getNewNoIdByOldNoId(ReassignConstant.BILLCLEARPAYMENT, oldId);

		if (null == id)
			id = businessId;

		if (StringUtils.isNullBlank(id))
		{
			billClearPayment = this.billClearPaymentService._getForEdit(id);
		}
		else
		{
			billClearPayment = this.billClearPaymentService._getForEdit(id);
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
		request.setAttribute("billClearPayment", billClearPayment);
		return new ModelAndView("xdss3/billClear/billClearPaymentEdit");
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
		BillClearPayment billClearPayment = (BillClearPayment) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), BillClearPayment.class, true, request.getMethod(), true);
		// 类型标识是从那个页面提交，view 表示从view页面提交流程。
		String type = request.getParameter("type");
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<BillClearItemPay> billClearItemPaymodifyItems = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { billClearPayment }, BillClearItemPay.class, null);
		Set<BillClearItemPay> deletedBillClearItemPaySet = ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(), new Object[] { billClearPayment }, BillClearItemPay.class, null);
		billClearPayment.setBillClearItemPay(billClearItemPaymodifyItems);
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<BillInPayment> billInPaymentmodifyItems = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { billClearPayment }, BillInPayment.class, null);
		Set<BillInPayment> deletedBillInPaymentSet = ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(), new Object[] { billClearPayment }, BillInPayment.class, null);
		billClearPayment.setBillInPayment(billInPaymentmodifyItems);
		// 绑定子对象(一对一关系)
		FundFlow fundFlow = (FundFlow) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), FundFlow.class, false, request.getMethod(), true);
		if (fundFlow != null)
		{
			billClearPayment.setFundFlow(fundFlow);
			fundFlow.setBillClearPayment(billClearPayment);
		}
		// 绑定子对象(一对一关系)
		SettleSubject settleSubject = (SettleSubject) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), SettleSubject.class, false, request.getMethod(), true);
		if (settleSubject != null)
		{
			billClearPayment.setSettleSubject(settleSubject);
			settleSubject.setBillClearPayment(billClearPayment);
		}
		if (!"view".equalsIgnoreCase(type))
		{
			this.billClearPaymentService._saveOrUpdate(billClearPayment, deletedBillClearItemPaySet, deletedBillInPaymentSet, getBusinessObject());
		}

		String reassignId = this.reassignJdbcDao.getReassignidByBoId(billClearPayment.getOldbillclearid());
		Reassign reassign = this.reassignService.getReassignById(reassignId);
		reassign.setWorkflowTaskId(workflowTaskId);
		reassign.setWorkflowLeaveTransitionName(billClearPayment.getWorkflowLeaveTransitionName());
		reassign.setWorkflowExamine(billClearPayment.getWorkflowExamine());
		reassign.setWorkflowUserDefineProcessVariable(billClearPayment.getWorkflowUserDefineProcessVariable());
		reassign.setWorkflowBusinessNote(billClearPayment.getWorkflowBusinessNote());
		reassign.setWorkflowUserDefineTaskVariable(billClearPayment.getWorkflowUserDefineTaskVariable());

		/**
		 * 判断重分配方式是否为： 重置（到业务部门重新分配） 需要拷贝凭证
		 */
		if (reassign.getProcessstate() != null && !reassign.getProcessstate().equals("财务部审核") && reassign.getReassigntmethod().equals(ReassignConstant.RESET_TO_BS))
		{
			this.reassignService.copyVoucher(reassign.getReassignboid(), billClearPayment.getBillclearid());
		}

		// 设置审核状态
		if (billClearPayment.getWorkflowLeaveTransitionName().equals("审核不通过"))
		{
			this.reassignService.updateReassignState(reassignId, BusinessState.SUBMITNOTPASS);
		}

		BusinessObject businessObject = BusinessObjectService.getBusinessObject("Reassign");
		this.reassignService._submitProcess(reassign, businessObject);
		this.operateSuccessfully(response);
	}

	/**
	 * 检查数据的正确性
	 * @param request
	 * @param response
	 */
	public void checkClearData(HttpServletRequest request, HttpServletResponse response){
		JSONObject jo = new JSONObject();
		// 绑定主对象值
		BillClearPayment billClearPayment = (BillClearPayment) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), BillClearPayment.class, true, request.getMethod(), true);
		// 类型标识是从那个页面提交，view 表示从view页面提交流程。
		String type = request.getParameter("type");
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<BillClearItemPay> billClearItemPaymodifyItems = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { billClearPayment }, BillClearItemPay.class, null);
		Set<BillClearItemPay> deletedBillClearItemPaySet = ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(), new Object[] { billClearPayment }, BillClearItemPay.class, null);
		billClearPayment.setBillClearItemPay(billClearItemPaymodifyItems);
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<BillInPayment> billInPaymentmodifyItems = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { billClearPayment }, BillInPayment.class, null);
		Set<BillInPayment> deletedBillInPaymentSet = ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(), new Object[] { billClearPayment }, BillInPayment.class, null);
		billClearPayment.setBillInPayment(billInPaymentmodifyItems);
		// 绑定子对象(一对一关系)
		FundFlow fundFlow = (FundFlow) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), FundFlow.class, false, request.getMethod(), true);
		if (fundFlow != null)
		{
			billClearPayment.setFundFlow(fundFlow);
			fundFlow.setBillClearPayment(billClearPayment);
		}
		// 绑定子对象(一对一关系)
		SettleSubject settleSubject = (SettleSubject) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), SettleSubject.class, false, request.getMethod(), true);
		if (settleSubject != null)
		{
			billClearPayment.setSettleSubject(settleSubject);
			settleSubject.setBillClearPayment(billClearPayment);
		}
		
		ParameterVoucherObject parameterVoucher =supplierClearAccountService.setParameter(billClearPayment);
		BigDecimal marginAmount = new BigDecimal("0");
		Voucher settleSubjectVoucher = null;
		
		if (settleSubject != null || fundFlow != null){			
			settleSubjectVoucher = supplierClearAccountService.saveSettleSubjectVoucher(parameterVoucher);				
			marginAmount = supplierClearAccountService.getMargin(settleSubjectVoucher.getVoucherItem());
//			客户跟供应商借贷方相反
			marginAmount = new BigDecimal("0").subtract(marginAmount);
			parameterVoucher.setVoucherid(settleSubjectVoucher.getVoucherid());
		}
		
		boolean isSave = false;
		if(!StringUtils.isNullBlank(billClearPayment.getBillclearid()))isSave=true;
		IInfo info =supplierClearAccountService.checkClearData(billClearPayment, marginAmount,isSave);
	
		jo.put("isRight", info.isRight());
		jo.put("info", info.getInfo());
		this.operateSuccessfullyHiddenInfoWithString(response, jo.toString());
		
	}

	

}