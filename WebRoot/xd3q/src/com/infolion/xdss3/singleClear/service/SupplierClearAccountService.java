package com.infolion.xdss3.singleClear.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infolion.platform.bo.service.BusinessObjectService;
import com.infolion.platform.console.org.dao.SysDeptJdbcDao;
import com.infolion.platform.dpframework.util.StringUtils;
import com.infolion.xdss3.BusinessState;
import com.infolion.xdss3.Constants;
import com.infolion.xdss3.billClear.domain.BillClearItemPay;
import com.infolion.xdss3.billClear.domain.BillClearPayment;
import com.infolion.xdss3.billClear.domain.BillInPayment;
import com.infolion.xdss3.billClear.service.BillClearPaymentService;
import com.infolion.xdss3.billclearcollect.domain.BillClearCollect;
import com.infolion.xdss3.billclearitem.domain.BillClearItem;
import com.infolion.xdss3.billincollect.domain.BillInCollect;
import com.infolion.xdss3.collect.domain.Collect;
import com.infolion.xdss3.collectcbill.domain.CollectCbill;
import com.infolion.xdss3.collectitem.domain.CollectItem;
import com.infolion.xdss3.customerDrawback.dao.CustomerRefundItemJdbcDao;
import com.infolion.xdss3.customerDrawback.domain.CustomerRefundItem;
import com.infolion.xdss3.customerDrawback.domain.CustomerRefundment;
import com.infolion.xdss3.customerDrawback.service.CustomerRefundItemService;
import com.infolion.xdss3.financeSquare.domain.FundFlow;
import com.infolion.xdss3.financeSquare.domain.SettleSubject;
import com.infolion.xdss3.fundflowBcc.domain.FundFlowBcc;
import com.infolion.xdss3.masterData.dao.VendorTitleJdbcDao;
import com.infolion.xdss3.masterData.domain.CustomerTitle;
import com.infolion.xdss3.masterData.domain.VendorTitle;
import com.infolion.xdss3.masterData.service.VendorService;
import com.infolion.xdss3.payment.domain.HomeCreditPayCbill;
import com.infolion.xdss3.payment.domain.HomeCreditPayItem;
import com.infolion.xdss3.payment.domain.HomeCreditPayment;
import com.infolion.xdss3.payment.domain.HomePayment;
import com.infolion.xdss3.payment.domain.HomePaymentCbill;
import com.infolion.xdss3.payment.domain.HomePaymentItem;
import com.infolion.xdss3.payment.domain.ImportPayment;
import com.infolion.xdss3.payment.domain.ImportPaymentCbill;
import com.infolion.xdss3.payment.domain.ImportPaymentItem;
import com.infolion.xdss3.payment.service.PaymentItemService;
import com.infolion.xdss3.settlesubjectbcc.domain.SettleSubjectBcc;
import com.infolion.xdss3.singleClear.dao.SupplierClearAccountJdbcDao;
import com.infolion.xdss3.singleClear.dao.SupplierSinglClearJdbcDao;
import com.infolion.xdss3.singleClear.domain.ClearConstant;
import com.infolion.xdss3.singleClear.domain.ClearVoucherItem;
import com.infolion.xdss3.singleClear.domain.CustomSingleClear;
import com.infolion.xdss3.singleClear.domain.IInfo;
import com.infolion.xdss3.singleClear.domain.IInfoVoucher;
import com.infolion.xdss3.singleClear.domain.IKeyValue;
import com.infolion.xdss3.singleClear.domain.IParameterVoucher;
import com.infolion.xdss3.singleClear.domain.InfoObject;
import com.infolion.xdss3.singleClear.domain.InfoVoucherObject;
import com.infolion.xdss3.singleClear.domain.KeyValue;
import com.infolion.xdss3.singleClear.domain.ParameterVoucherObject;
import com.infolion.xdss3.singleClear.domain.UnClearCollect;
import com.infolion.xdss3.singleClear.domain.UnClearCustomBill;
import com.infolion.xdss3.singleClear.domain.UnClearPayment;
import com.infolion.xdss3.singleClear.domain.UnClearSupplieBill;

import com.infolion.xdss3.singleClear.domain.SupplierSinglClear;

import com.infolion.xdss3.supplierDrawback.dao.SupplierRefundItemJdbcDao;
import com.infolion.xdss3.supplierDrawback.domain.SupplierRefundItem;
import com.infolion.xdss3.supplierDrawback.domain.SupplierRefundment;
import com.infolion.xdss3.supplierDrawback.service.SupplierRefundItemService;
import com.infolion.xdss3.voucher.domain.Voucher;
import com.infolion.xdss3.voucher.service.VoucherService;
import com.infolion.xdss3.voucheritem.dao.VoucherItemJdbcDao;
import com.infolion.xdss3.voucheritem.domain.VoucherItem;


/**
 * * <pre>
 * 供应商全面清帐(SupplierClearAccountService)服务类
 * </pre>
 * 
 * @author zhongzh
 * @version 1.0
 * @see The author for more details
 * @since 1.0
 */

@Service
public class SupplierClearAccountService extends IsClearAccount implements ISupplierClearAccount{
	

	@Autowired	
	private CustomerRefundItemService customerRefundItemService;
	
	
	public void setCustomerRefundItemService(CustomerRefundItemService customerRefundItemService) {
		this.customerRefundItemService = customerRefundItemService;
	}
	@Autowired
	private CustomerRefundItemJdbcDao customerRefundItemJdbcDao;
	
	/**
	 * @param customerRefundItemJdbcDao the customerRefundItemJdbcDao to set
	 */
	public void setCustomerRefundItemJdbcDao(
			CustomerRefundItemJdbcDao customerRefundItemJdbcDao) {
		this.customerRefundItemJdbcDao = customerRefundItemJdbcDao;
	}	
	@Autowired
	private SupplierRefundItemService supplierRefundItemService;
	
	/**
	 * @param supplierRefundItemJdbcDao the supplierRefundItemJdbcDao to set
	 */
	public void setSupplierRefundItemService(
			SupplierRefundItemService supplierRefundItemService) {
		this.supplierRefundItemService = supplierRefundItemService;
	}		
	
	
	@Autowired
	private VendorService vendorService;
	
	/**
	 * @param vendorService the vendorService to set
	 */
	public void setVendorService(VendorService vendorService) {
		this.vendorService = vendorService;
	}
	@Autowired
	private SupplierSinglClearJdbcDao supplierSinglClearJdbcDao;

	/**
	 * @param supplierSinglClearJdbcDao
	 *            the supplierSinglClearJdbcDao to set
	 */
	public void setSupplierSinglClearJdbcDao(SupplierSinglClearJdbcDao supplierSinglClearJdbcDao)
	{
		this.supplierSinglClearJdbcDao = supplierSinglClearJdbcDao;
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
	private VendorTitleJdbcDao vendorTitleJdbcDao;

	/**
	 * @param vendorTitleJdbcDao
	 *            the vendorTitleJdbcDao to set
	 */
	public void setVendorTitleJdbcDao(VendorTitleJdbcDao vendorTitleJdbcDao)
	{
		this.vendorTitleJdbcDao = vendorTitleJdbcDao;
	}

	@Autowired
	private SysDeptJdbcDao sysDeptJdbcDao;

	public void setSysDeptJdbcDao(SysDeptJdbcDao sysDeptJdbcDao) {
		this.sysDeptJdbcDao = sysDeptJdbcDao;
	}
	@Autowired
	private BillClearPaymentService billClearPaymentService;
	
	
	/**
	 * @param billClearPaymentService the billClearPaymentService to set
	 */
	public void setBillClearPaymentService(
			BillClearPaymentService billClearPaymentService) {
		this.billClearPaymentService = billClearPaymentService;
	}
	@Autowired
	private SupplierSinglClearService supplierSinglClearService;



	/**
	 * @param supplierSinglClearService the supplierSinglClearService to set
	 */
	public void setSupplierSinglClearService(
			SupplierSinglClearService supplierSinglClearService) {
		this.supplierSinglClearService = supplierSinglClearService;
	}


	@Autowired
	private VoucherItemJdbcDao voucherItemJdbcDao;

	
	public void setVoucherItemJdbcDao(VoucherItemJdbcDao voucherItemJdbcDao) {
		this.voucherItemJdbcDao = voucherItemJdbcDao;
	}
	@Autowired
	private SupplierClearAccountJdbcDao supplierClearAccountJdbcDao;
	
	/**
	 * @param supplierClearAccountJdbcDao the supplierClearAccountJdbcDao to set
	 */
	public void setSupplierClearAccountJdbcDao(
			SupplierClearAccountJdbcDao supplierClearAccountJdbcDao) {
		this.supplierClearAccountJdbcDao = supplierClearAccountJdbcDao;
	}
	
	public SupplierClearAccountService() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 第一步，有结算科目或纯资金凭证凭证要先出，不管能不能清账
	 * 第二步	 * 判断是否可以清账
	 * 第三步，外币要出汇损（2600公司本币是港币(HKD)，其他公司本币是人民币(CNY)）
	 * 第四步，更新isclear状态
	 * @param id 主表的id
	 * @param type 类型 1为单清账，2为票清付款 ，3为付款清票，4为退款
	 * @return
	 */
	public boolean isClearAccount(String id,String type){
//		CustomSingleClear CustomSingleClear =null;
//		Collect collect =null;
//		BillClearCollect billClearCollect = null;
//		CustomerRefundment customerRefundment = null;
		if(ClearConstant.SINGLE_TYPE.equals(type)){
			
		}
		if(ClearConstant.BILL_TYPE.equals(type)){
					
		}
		if(ClearConstant.PAYMENT_TYPE.equals(type)){
			
		}
		if(ClearConstant.REFUNDMENT_TYPE.equals(type)){
			
		}
		return false;
	}
	
	

	
	
	
	
	/**
	 * 
	 * 检查单清账的数据正确性()
	 *  * 1。是否有相同的数据，
	 * 2。是否能保每次清账一定要至少一边全清，并且部分清只能只有一行有余额未清。
	 * 3。保证清账的单借贷方相等
	 *4.(贷方的清账总金额 = 本次已抵消付款 总金额 + 调整差额的合计 (有正有负))
	*	(结算科目金额总计+纯资金往来金额总计的和=调整差额的合计值)
	*@param marginAmount (差额)结算科目金额总计+纯资金往来金额总计的和
	 * @param supplierSinglClear（重新取通过ID去数据库取得该对象）
	 * @return false 数据错误，true 数据正确
	 */
	public IInfo checkClearData(SupplierSinglClear supplierSinglClear,BigDecimal marginAmount,boolean isSave){
		InfoObject infoObject =new InfoObject();
		//默认为true
		infoObject.setRight(true);
		
		Set<UnClearSupplieBill> unClearSupplieBills = supplierSinglClear.getUnClearSupplieBill();
		Set<UnClearPayment> unClearPayments = supplierSinglClear.getUnClearPayment();	
		
		List<String> ids = new ArrayList<String>();
//		判断哪一边全清
		boolean billF = false;
		boolean clearF = false;
//		判断有几条数据为部分清
		int i=0;
//		判断有几条数据为保证金
		int k=0;
//		
		boolean isa = false;
//		票的总额
		BigDecimal billAmount = new BigDecimal("0");
//		款的总额
		BigDecimal cpAmount = new BigDecimal("0");
//		调整金
		BigDecimal ajAmount = new BigDecimal("0");
//		有在批的单据也算部分清
		boolean isonload =false;
		for (UnClearSupplieBill unClearSupplieBill : unClearSupplieBills){
			// 1。检查是否有相同的数据，
			if(!ids.contains(unClearSupplieBill.getVendortitleid())){
				ids.add(unClearSupplieBill.getVendortitleid());
			}else{
				log.debug("供应商清账检查单清账的数据正确性---"+infoObject.CODE_0+",vendortitleid=" + unClearSupplieBill.getVendortitleid());
				infoObject.setInfo(infoObject.CODE_0);
				infoObject.setRight(false);				
				return infoObject;
			}
		

			// 开票发票在途的，发票在途金额
			BigDecimal onroadamount = this.vendorService.getSumClearAmount(unClearSupplieBill.getInvoice(), BusinessState.ALL_PAYMENT_ONROAD);
//			如果已经保存了，去掉本次的
			if(isSave){
				onroadamount = onroadamount.subtract(unClearSupplieBill.getClearamount());
			}
			//2.是否能保每次清账一定要至少一边全清，并且部分清只能只有一行有余额未清,在批的也算部分清
			if(unClearSupplieBill.getUnpaidamount().compareTo(unClearSupplieBill.getClearamount()) !=0 || onroadamount.compareTo(new BigDecimal("0")) > 0){
				billF = true;				
				i++;
				if(onroadamount.compareTo(new BigDecimal("0")) !=0){
					isonload=true;
				}
			}
			billAmount = billAmount.add(unClearSupplieBill.getClearamount());
			ajAmount = ajAmount.add(unClearSupplieBill.getAdjustamount());
		}
		
		ids = new ArrayList<String>();
		
		for (UnClearPayment unClearPayment : unClearPayments){
			// 1。检查是否有相同的数据，
			if(!ids.contains(unClearPayment.getVendortitleid())){
				ids.add(unClearPayment.getVendortitleid());
			}else{
				log.debug("供应商清账检查单清账的数据正确性---"+infoObject.CODE_1+",Vendortitleid=" + unClearPayment.getVendortitleid());
				infoObject.setInfo(infoObject.CODE_1);
				infoObject.setRight(false);
				return infoObject;
			}
//			在批的
			BigDecimal onroadamount1 = new BigDecimal("0");
			if(StringUtils.isNullBlank(unClearPayment.getPaymentitemid())){
				onroadamount1 = supplierSinglClearJdbcDao.getSumClearAmount2(unClearPayment.getVendortitleid(), BusinessState.ALL_PAYMENT_ONROAD);
			}else{
				onroadamount1 = this.paymentItemService.getSumClearAmount(unClearPayment.getPaymentitemid(), BusinessState.ALL_PAYMENT_ONROAD);
			}
//			如果已经保存了，去掉本次的
			if(isSave){
//				this.supplierClearAccountJdbcDao.getSumPefundmentamountByRefundmentId();
				onroadamount1 = onroadamount1.subtract(unClearPayment.getNowclearamount());
			}
			//2.是否能保每次清账一定要至少一边全清，并且部分清只能只有一行有余额未清,在批的也算部分清
			if(unClearPayment.getUnoffsetamount().compareTo(unClearPayment.getNowclearamount()) !=0 || onroadamount1.compareTo(new BigDecimal("0")) >0){
				clearF = true;			
				i++;		
				if(onroadamount1.compareTo(new BigDecimal("0")) !=0){
					isonload=true;
				}
			}			
			cpAmount = cpAmount.add(unClearPayment.getNowclearamount());
		}
		//两边没有一边全清
		if(billF && clearF){
			if(isonload){
				log.debug("供应商清账检查单清账的数据正确性---"+ infoObject.CODE_2 + "(有在批的单据)");
				infoObject.setInfo(infoObject.CODE_2  + "(有在批的单据)");
				infoObject.setRight(false);
				return infoObject;
			}else{
				log.debug("供应商清账检查单清账的数据正确性---"+ infoObject.CODE_2);
				infoObject.setInfo(infoObject.CODE_2);
				infoObject.setRight(false);
				return infoObject;
			}
		}
//		部分清只能只有一行有余额未清
		if(i>1){
			log.debug("供应商清账检查单清账的数据正确性---"+ infoObject.CODE_3);
			infoObject.setInfo(infoObject.CODE_3);
			infoObject.setRight(false);
			return infoObject;
		}
//		保证金只能有一条
		if(k>1){
			log.debug("供应商清账检查单清账的数据正确性---"+ infoObject.CODE_4);
			infoObject.setInfo(infoObject.CODE_4);
			infoObject.setRight(false);
			return infoObject;
		}
//		有保证金只能这一条有部分清(在判断的最后面)
		if(k==1 && !isa){
			log.debug("供应商清账检查单清账的数据正确性---"+ infoObject.CODE_5);
			infoObject.setInfo(infoObject.CODE_5);
			infoObject.setRight(false);
			return infoObject;
		}
//		清账的单借贷方相等
		if(billAmount.compareTo(cpAmount) ==0){
			infoObject.setRight(true);
			return infoObject;
		}else{
//		清账的单借贷方不相等，	(贷方的清账总金额 = 本次已抵消收款 总金额 + 调整差额的合计 (有正有负))
//		(结算科目金额总计+纯资金往来金额总计的和=调整差额的合计值)
			if(ajAmount.compareTo(new BigDecimal("0")) !=0){				
				if(billAmount.compareTo(cpAmount.add(ajAmount)) ==0 && ajAmount.compareTo(marginAmount) == 0  && marginAmount.compareTo(new BigDecimal("0")) !=0){
					infoObject.setRight(true);
					return infoObject;
				}else{
					log.debug("供应商清账检查单清账的数据正确性---"+ infoObject.CODE_7);
					infoObject.setInfo(infoObject.CODE_7);
					infoObject.setRight(false);
					return infoObject;
				}
			}else{
				log.debug("供应商清账检查单清账的数据正确性---"+ infoObject.CODE_6);
				infoObject.setInfo(infoObject.CODE_6);
				infoObject.setRight(false);
				return infoObject;
			}
		}
	}
	/**
	 * 检查付款清账的数据正确性,
	 * 1。是否有相同的数据，
	 * 2。是否能保每次清账一定要至少一边全清，并且部分清只能只有一行有余额未清。
	 * 3。保证清账的单借贷方相等
	 * 
	 * 
	 *  @param payment（重新取通过ID去数据库取得该对象）
	 * @return
	 */
	public IInfo checkClearData(HomePayment payment,BigDecimal marginAmount,boolean isSave){
		InfoObject infoObject =new InfoObject();
		//默认为true
		infoObject.setRight(true);
		Set<HomePaymentCbill> homePaymentCbills =payment.getHomePaymentCbill();
		Set<HomePaymentItem> homePaymentItems = payment.getHomePaymentItem();	
		List<String> ids = new ArrayList<String>();
//		判断哪一边全清
		boolean billF = false;
		boolean clearF = false;
//		判断有几条数据为部分清
		int i=0;
//		判断有几条数据为保证金
		int k=0;
//		
		boolean isa = false;
//		票的总额
		BigDecimal billAmount = new BigDecimal("0");
//		款的总额
		BigDecimal cpAmount = new BigDecimal("0");
//		调整金
//		BigDecimal ajAmount = new BigDecimal("0");
//		有在批的单据也算部分清
		boolean isonload =false;
		for (HomePaymentCbill paymentCbill : homePaymentCbills){
			// 1。检查是否有相同的数据，
			if(!ids.contains(paymentCbill.getBillno())){
				ids.add(paymentCbill.getBillno());
			}else{
				log.debug("供应商清账检查单清账的数据正确性---"+infoObject.CODE_0+",Billno=" + paymentCbill.getBillno());
				infoObject.setInfo(infoObject.CODE_0);
				infoObject.setRight(false);				
				return infoObject;
			}
			// 开票发票在途的，发票在途金额
			BigDecimal onroadamount = this.vendorService.getSumClearAmount(paymentCbill.getBillno(), BusinessState.ALL_PAYMENT_ONROAD);
//			如果已经保存了，去掉本次的
			if(isSave){
				onroadamount = onroadamount.subtract(paymentCbill.getClearamount2());
			}
			//2.是否能保每次清账一定要至少一边全清，并且部分清只能只有一行有余额未清
			if(paymentCbill.getUnpaidamount().compareTo(paymentCbill.getClearamount2()) !=0 || onroadamount.compareTo(new BigDecimal("0")) > 0){
				billF = true;				
				i++;
				if(onroadamount.compareTo(new BigDecimal("0")) !=0){
					isonload=true;
				}
			}
			billAmount = billAmount.add(paymentCbill.getClearamount2());
			
		}
		//如果没有票就不用做以下的判断了(数据正确，在清账时不出清账凭证)
		if(billAmount.compareTo(new BigDecimal("0")) == 0){
			infoObject.setRight(true);
			return infoObject;
		}
		ids = new ArrayList<String>();		
		for (HomePaymentItem paymentItem : homePaymentItems){
			
			//2.是否能保每次清账一定要至少一边全清，并且部分清只能只有一行有余额未清
//			付款的清账金额 = 付款分配金额 - 预付款,如果等于0说明没有清，
			BigDecimal paymentClearAmount = paymentItem.getAssignamount().subtract(paymentItem.getPrepayamount());
//			只要预收款不为空说明是部分清
			if(paymentItem.getPrepayamount().compareTo(new BigDecimal("0")) !=0 ){
				clearF = true;			
				i++;
			}			
			cpAmount = cpAmount.add(paymentClearAmount);
		}
		//两边没有一边全清
		if(billF && clearF){
			if(isonload){
				log.debug("供应商清账检查单清账的数据正确性---"+ infoObject.CODE_2 + "(有在批的单据)");
				infoObject.setInfo(infoObject.CODE_2  + "(有在批的单据)");
				infoObject.setRight(false);
				return infoObject;
			}else{
				log.debug("供应商清账检查单清账的数据正确性---"+ infoObject.CODE_2);
				infoObject.setInfo(infoObject.CODE_2);
				infoObject.setRight(false);
				return infoObject;
			}
		}
//		部分清只能只有一行有余额未清
		if(i>1){
			log.debug("供应商清账检查单清账的数据正确性---"+ infoObject.CODE_3);
			infoObject.setInfo(infoObject.CODE_3);
			infoObject.setRight(false);
			return infoObject;
		}
//		保证金只能有一条
		if(k>1){
			log.debug("供应商清账检查单清账的数据正确性---"+ infoObject.CODE_4);
			infoObject.setInfo(infoObject.CODE_4);
			infoObject.setRight(false);
			return infoObject;
		}
//		有保证金只能这一条有部分清(在判断的最后面)
		if(k==1 && !isa){
			log.debug("供应商清账检查单清账的数据正确性---"+ infoObject.CODE_5);
			infoObject.setInfo(infoObject.CODE_5);
			infoObject.setRight(false);
			return infoObject;
		}
//		清账的单借贷方相等
		if(billAmount.compareTo(cpAmount) ==0){
			infoObject.setRight(true);
			return infoObject;
		}else{
//			清账的单借贷方不相等，	(贷方的清账总金额 = 本次已抵消收款 总金额 + 调整差额的合计 (有正有负))
//			(结算科目金额总计+纯资金往来金额总计的和=调整差额的合计值)
			BigDecimal ajAmount = cpAmount.subtract(billAmount);
			if(ajAmount.compareTo(new BigDecimal("0")) !=0){
				if(ajAmount.compareTo(marginAmount) == 0  && marginAmount.compareTo(new BigDecimal("0")) !=0){
					infoObject.setRight(true);
					return infoObject;
				}else{
					log.debug("供应商清账检查单清账的数据正确性---"+ infoObject.CODE_7);
					infoObject.setInfo(infoObject.CODE_7);
					infoObject.setRight(false);
					return infoObject;
				}
			}else{
				log.debug("供应商清账检查单清账的数据正确性---"+ infoObject.CODE_6);
				infoObject.setInfo(infoObject.CODE_6);
				infoObject.setRight(false);
				return infoObject;
			}
			
		}
	}
	
	/**
	 * 检查付款清账的数据正确性,
	 * 1。是否有相同的数据，
	 * 2。是否能保每次清账一定要至少一边全清，并且部分清只能只有一行有余额未清。
	 * 3。保证清账的单借贷方相等
	 * 
	 * 
	 *  @param payment（重新取通过ID去数据库取得该对象）
	 * @return
	 */
	public IInfo checkClearData(HomeCreditPayment payment,BigDecimal marginAmount,boolean isSave){
		InfoObject infoObject =new InfoObject();
		//默认为true
		infoObject.setRight(true);
		Set<HomeCreditPayCbill> homePaymentCbills =payment.getHomeCreditPayCbill();
		Set<HomeCreditPayItem> homePaymentItems = payment.getHomeCreditPayItem();	
		List<String> ids = new ArrayList<String>();
//		判断哪一边全清
		boolean billF = false;
		boolean clearF = false;
//		判断有几条数据为部分清
		int i=0;
//		判断有几条数据为保证金
		int k=0;
//		
		boolean isa = false;
//		票的总额
		BigDecimal billAmount = new BigDecimal("0");
//		款的总额
		BigDecimal cpAmount = new BigDecimal("0");
//		调整金
//		BigDecimal ajAmount = new BigDecimal("0");
//		有在批的单据也算部分清
		boolean isonload =false;
		for (HomeCreditPayCbill paymentCbill : homePaymentCbills){
			// 1。检查是否有相同的数据，
			if(!ids.contains(paymentCbill.getBillno())){
				ids.add(paymentCbill.getBillno());
			}else{
				log.debug("供应商清账检查单清账的数据正确性---"+infoObject.CODE_0+",Billno=" + paymentCbill.getBillno());
				infoObject.setInfo(infoObject.CODE_0);
				infoObject.setRight(false);				
				return infoObject;
			}
			// 开票发票在途的，发票在途金额
			BigDecimal onroadamount = this.vendorService.getSumClearAmount(paymentCbill.getBillno(), BusinessState.ALL_PAYMENT_ONROAD);
//			如果已经保存了，去掉本次的
			if(isSave){
				onroadamount = onroadamount.subtract(paymentCbill.getClearamount2());
			}
			//2.是否能保每次清账一定要至少一边全清，并且部分清只能只有一行有余额未清
			if(paymentCbill.getUnpaidamount().compareTo(paymentCbill.getClearamount2()) !=0 || onroadamount.compareTo(new BigDecimal("0")) > 0){
				billF = true;				
				i++;
				if(onroadamount.compareTo(new BigDecimal("0")) !=0){
					isonload=true;
				}
			}
			billAmount = billAmount.add(paymentCbill.getClearamount2());
			
		}
		//如果没有票就不用做以下的判断了(数据正确，在清账时不出清账凭证)
		if(billAmount.compareTo(new BigDecimal("0")) == 0){
			infoObject.setRight(true);
			return infoObject;
		}
		ids = new ArrayList<String>();		
		for (HomeCreditPayItem paymentItem : homePaymentItems){
			
			//2.是否能保每次清账一定要至少一边全清，并且部分清只能只有一行有余额未清
//			付款的清账金额 = 付款分配金额 - 预付款,如果等于0说明没有清，
			BigDecimal paymentClearAmount = paymentItem.getAssignamount().subtract(paymentItem.getPrepayamount());
//			只要预收款不为空说明是部分清
			if(paymentItem.getPrepayamount().compareTo(new BigDecimal("0")) !=0 ){
				clearF = true;			
				i++;
			}			
			cpAmount = cpAmount.add(paymentClearAmount);
		}
		//两边没有一边全清
		if(billF && clearF){
			if(isonload){
				log.debug("供应商清账检查单清账的数据正确性---"+ infoObject.CODE_2 + "(有在批的单据)");
				infoObject.setInfo(infoObject.CODE_2  + "(有在批的单据)");
				infoObject.setRight(false);
				return infoObject;
			}else{
				log.debug("供应商清账检查单清账的数据正确性---"+ infoObject.CODE_2);
				infoObject.setInfo(infoObject.CODE_2);
				infoObject.setRight(false);
				return infoObject;
			}
		}
//		部分清只能只有一行有余额未清
		if(i>1){
			log.debug("供应商清账检查单清账的数据正确性---"+ infoObject.CODE_3);
			infoObject.setInfo(infoObject.CODE_3);
			infoObject.setRight(false);
			return infoObject;
		}
//		保证金只能有一条
		if(k>1){
			log.debug("供应商清账检查单清账的数据正确性---"+ infoObject.CODE_4);
			infoObject.setInfo(infoObject.CODE_4);
			infoObject.setRight(false);
			return infoObject;
		}
//		有保证金只能这一条有部分清(在判断的最后面)
		if(k==1 && !isa){
			log.debug("供应商清账检查单清账的数据正确性---"+ infoObject.CODE_5);
			infoObject.setInfo(infoObject.CODE_5);
			infoObject.setRight(false);
			return infoObject;
		}
//		清账的单借贷方相等
		if(billAmount.compareTo(cpAmount) ==0){
			infoObject.setRight(true);
			return infoObject;
		}else{
//			清账的单借贷方不相等，	(贷方的清账总金额 = 本次已抵消收款 总金额 + 调整差额的合计 (有正有负))
//			(结算科目金额总计+纯资金往来金额总计的和=调整差额的合计值)
			BigDecimal ajAmount = cpAmount.subtract(billAmount);
			if(ajAmount.compareTo(new BigDecimal("0")) !=0){
				if(ajAmount.compareTo(marginAmount) == 0  && marginAmount.compareTo(new BigDecimal("0")) !=0){
					infoObject.setRight(true);
					return infoObject;
				}else{
					log.debug("供应商清账检查单清账的数据正确性---"+ infoObject.CODE_7);
					infoObject.setInfo(infoObject.CODE_7);
					infoObject.setRight(false);
					return infoObject;
				}
			}else{
				log.debug("供应商清账检查单清账的数据正确性---"+ infoObject.CODE_6);
				infoObject.setInfo(infoObject.CODE_6);
				infoObject.setRight(false);
				return infoObject;
			}
			
		}
	}
	
	public IInfo checkClearData(ImportPayment payment,BigDecimal marginAmount,boolean isSave){
		InfoObject infoObject =new InfoObject();
		//默认为true
		infoObject.setRight(true);
		Set<ImportPaymentCbill> importPaymentCbills =payment.getImportPaymentCbill();
		Set<ImportPaymentItem> importPaymentItems = payment.getImportPaymentItem();	
		List<String> ids = new ArrayList<String>();
//		判断哪一边全清
		boolean billF = false;
		boolean clearF = false;
//		判断有几条数据为部分清
		int i=0;
//		判断有几条数据为保证金
		int k=0;
//		
		boolean isa = false;
//		票的总额
		BigDecimal billAmount = new BigDecimal("0");
//		款的总额
		BigDecimal cpAmount = new BigDecimal("0");
//		调整金
//		BigDecimal ajAmount = new BigDecimal("0");
//		有在批的单据也算部分清
		boolean isonload =false;
		for (ImportPaymentCbill paymentCbill : importPaymentCbills){
			// 1。检查是否有相同的数据，
			if(!ids.contains(paymentCbill.getBillno())){
				ids.add(paymentCbill.getBillno());
			}else{
				log.debug("供应商清账检查单清账的数据正确性---"+infoObject.CODE_0+",Billno=" + paymentCbill.getBillno());
				infoObject.setInfo(infoObject.CODE_0);
				infoObject.setRight(false);				
				return infoObject;
			}
			// 开票发票在途的，发票在途金额
			BigDecimal onroadamount = this.vendorService.getSumClearAmount(paymentCbill.getBillno(), BusinessState.ALL_PAYMENT_ONROAD);
//			如果已经保存了，去掉本次的
			if(isSave){
				onroadamount = onroadamount.subtract(paymentCbill.getClearamount2());
			}
			//2.是否能保每次清账一定要至少一边全清，并且部分清只能只有一行有余额未清
			if(paymentCbill.getUnpaidamount().compareTo(paymentCbill.getClearamount2()) !=0 || onroadamount.compareTo(new BigDecimal("0")) > 0){
				billF = true;				
				i++;
				if(onroadamount.compareTo(new BigDecimal("0")) !=0){
					isonload=true;
				}
			}
			billAmount = billAmount.add(paymentCbill.getClearamount2());
			
		}
		//如果没有票就不用做以下的判断了(数据正确，在清账时不出清账凭证)
		if(billAmount.compareTo(new BigDecimal("0")) == 0){
			infoObject.setRight(true);
			return infoObject;
		}
		ids = new ArrayList<String>();		
		for (ImportPaymentItem paymentItem : importPaymentItems){
			
			//2.是否能保每次清账一定要至少一边全清，并且部分清只能只有一行有余额未清
//			付款的清账金额 = 付款分配金额 - 预付款,如果等于0说明没有清，
			BigDecimal paymentClearAmount = paymentItem.getAssignamount().subtract(paymentItem.getPrepayamount());
//			只要预收款不为空说明是部分清
			if(paymentItem.getPrepayamount().compareTo(new BigDecimal("0")) !=0 ){
				clearF = true;			
				i++;
			}			
			cpAmount = cpAmount.add(paymentClearAmount);
		}
		//两边没有一边全清
		if(billF && clearF){
			if(isonload){
				log.debug("供应商清账检查单清账的数据正确性---"+ infoObject.CODE_2 + "(有在批的单据)");
				infoObject.setInfo(infoObject.CODE_2  + "(有在批的单据)");
				infoObject.setRight(false);
				return infoObject;
			}else{
				log.debug("供应商清账检查单清账的数据正确性---"+ infoObject.CODE_2);
				infoObject.setInfo(infoObject.CODE_2);
				infoObject.setRight(false);
				return infoObject;
			}
		}
//		部分清只能只有一行有余额未清
		if(i>1){
			log.debug("供应商清账检查单清账的数据正确性---"+ infoObject.CODE_3);
			infoObject.setInfo(infoObject.CODE_3);
			infoObject.setRight(false);
			return infoObject;
		}
//		保证金只能有一条
		if(k>1){
			log.debug("供应商清账检查单清账的数据正确性---"+ infoObject.CODE_4);
			infoObject.setInfo(infoObject.CODE_4);
			infoObject.setRight(false);
			return infoObject;
		}
//		有保证金只能这一条有部分清(在判断的最后面)
		if(k==1 && !isa){
			log.debug("供应商清账检查单清账的数据正确性---"+ infoObject.CODE_5);
			infoObject.setInfo(infoObject.CODE_5);
			infoObject.setRight(false);
			return infoObject;
		}
//		清账的单借贷方相等
		if(billAmount.compareTo(cpAmount) ==0){
			infoObject.setRight(true);
			return infoObject;
		}else{
//			清账的单借贷方不相等，	(贷方的清账总金额 = 本次已抵消收款 总金额 + 调整差额的合计 (有正有负))
//			(结算科目金额总计+纯资金往来金额总计的和=调整差额的合计值)
			BigDecimal ajAmount = cpAmount.subtract(billAmount);
			if(ajAmount.compareTo(new BigDecimal("0")) !=0){
				if(ajAmount.compareTo(marginAmount) == 0  && marginAmount.compareTo(new BigDecimal("0")) !=0){
					infoObject.setRight(true);
					return infoObject;
				}else{
					log.debug("供应商清账检查单清账的数据正确性---"+ infoObject.CODE_7);
					infoObject.setInfo(infoObject.CODE_7);
					infoObject.setRight(false);
					return infoObject;
				}
			}else{
				log.debug("供应商清账检查单清账的数据正确性---"+ infoObject.CODE_6);
				infoObject.setInfo(infoObject.CODE_6);
				infoObject.setRight(false);
				return infoObject;
			}
			
		}
	}
	/**
	 * 检查票清付款清账的数据正确性
	 *  * 1。是否有相同的数据，
	 * 2。是否能保每次清账一定要至少一边全清，并且部分清只能只有一行有余额未清。
	 * 3。保证清账的单借贷方相等
	 * *4.(贷方的清账总金额 = 本次已抵消付款 总金额 + 调整差额的合计 (有正有负))
	*	(结算科目金额总计+纯资金往来金额总计的和=调整差额的合计值)
	*@param marginAmount (差额)结算科目金额总计+纯资金往来金额总计的和
	 *  @param billClearPayment（重新取通过ID去数据库取得该对象）
	 * @return
	 */
	public IInfo checkClearData(BillClearPayment billClearPayment,BigDecimal marginAmount,boolean isSave){
		InfoObject infoObject =new InfoObject();
		//默认为true
		infoObject.setRight(true);
		Set<BillClearItemPay> billClearItemPays = billClearPayment.getBillClearItemPay();
		Set<BillInPayment> billInPayments = billClearPayment.getBillInPayment();	
		List<String> ids = new ArrayList<String>();
//		判断哪一边全清
		boolean billF = false;
		boolean clearF = false;
//		判断有几条数据为部分清
		int i=0;
//		判断有几条数据为保证金
		int k=0;
//		
		boolean isa = false;
//		票的总额
		BigDecimal billAmount = new BigDecimal("0");
//		款的总额
		BigDecimal cpAmount = new BigDecimal("0");
//		调整金
		BigDecimal ajAmount = new BigDecimal("0");
//		有在批的单据也算部分清
		boolean isonload =false;
		
		for (BillClearItemPay billClearItemPay : billClearItemPays){		
			// 1。检查是否有相同的数据，
			if(!ids.contains(billClearItemPay.getTitleid())){
				ids.add(billClearItemPay.getTitleid());
			}else{
				log.debug("供应商清账检查单清账的数据正确性---"+infoObject.CODE_0+",titleid=" + billClearItemPay.getTitleid());
				infoObject.setInfo(infoObject.CODE_0);
				infoObject.setRight(false);				
				return infoObject;
			}
			// 开票发票在途的，发票在途金额
			BigDecimal onroadamount = this.vendorService.getSumClearAmount(billClearItemPay.getInvoice(), BusinessState.ALL_PAYMENT_ONROAD);
//			如果已经保存了，去掉本次的
			if(isSave){
				onroadamount = onroadamount.subtract(billClearItemPay.getClearbillamount());
			}
			//2.是否能保每次清账一定要至少一边全清，并且部分清只能只有一行有余额未清
			if(billClearItemPay.getUnreceivedamount().compareTo(billClearItemPay.getClearbillamount()) !=0 || onroadamount.compareTo(new BigDecimal("0"))  > 0){
				billF = true;				
				i++;
				if(onroadamount.compareTo(new BigDecimal("0")) !=0){
					isonload=true;
				}
			}
			billAmount = billAmount.add(billClearItemPay.getClearbillamount());
			ajAmount = ajAmount.add(billClearItemPay.getAdjustamount());
		}
		
		ids = new ArrayList<String>();
		
		for (BillInPayment billInPayment : billInPayments){
			// 1。检查是否有相同的数据，
			if(!ids.contains(billInPayment.getPaymentitemid())){
				ids.add(billInPayment.getPaymentitemid());
			}else{
				log.debug("客户清账检查单清账的数据正确性---"+infoObject.CODE_1+",paymentitemid=" + billInPayment.getPaymentitemid());
				infoObject.setInfo(infoObject.CODE_1);
				infoObject.setRight(false);
				return infoObject;
			}
//			在批的			
			BigDecimal onroadamount1 = this.paymentItemService.getSumClearAmount(billInPayment.getPaymentitemid(), BusinessState.ALL_PAYMENT_ONROAD);
//			如果已经保存了，去掉本次的
			if(isSave){
				onroadamount1 = onroadamount1.subtract(billInPayment.getNowclearamount());
			}
			//2.是否能保每次清账一定要至少一边全清，并且部分清只能只有一行有余额未清
			if(billInPayment.getUnoffsetamount().compareTo(billInPayment.getNowclearamount()) !=0 || onroadamount1.compareTo(new BigDecimal("0")) > 0){
				clearF = true;			
				i++;
				if(onroadamount1.compareTo(new BigDecimal("0")) !=0){
					isonload=true;
				}
			}
			
			cpAmount = cpAmount.add(billInPayment.getNowclearamount());
		}
		//两边没有一边全清
		if(billF && clearF){
			if(isonload){
				log.debug("供应商清账检查单清账的数据正确性---"+ infoObject.CODE_2 + "(有在批的单据)");
				infoObject.setInfo(infoObject.CODE_2  + "(有在批的单据)");
				infoObject.setRight(false);
				return infoObject;
			}else{
				log.debug("供应商清账检查单清账的数据正确性---"+ infoObject.CODE_2);
				infoObject.setInfo(infoObject.CODE_2);
				infoObject.setRight(false);
				return infoObject;
			}
		}
//		部分清只能只有一行有余额未清
		if(i>1){
			log.debug("供应商清账检查单清账的数据正确性---"+ infoObject.CODE_3);
			infoObject.setInfo(infoObject.CODE_3);
			infoObject.setRight(false);
			return infoObject;
		}
//		保证金只能有一条
		if(k>1){
			log.debug("供应商清账检查单清账的数据正确性---"+ infoObject.CODE_4);
			infoObject.setInfo(infoObject.CODE_4);
			infoObject.setRight(false);
			return infoObject;
		}
//		有保证金只能这一条有部分清(在判断的最后面)
		if(k==1 && !isa){
			log.debug("供应商清账检查单清账的数据正确性---"+ infoObject.CODE_5);
			infoObject.setInfo(infoObject.CODE_5);
			infoObject.setRight(false);
			return infoObject;
		}
//		清账的单借贷方相等
		if(billAmount.compareTo(cpAmount) ==0){
			infoObject.setRight(true);
			return infoObject;
		}else{
//		清账的单借贷方不相等，	(贷方的清账总金额 = 本次已抵消收款 总金额 + 调整差额的合计 (有正有负))
//		(结算科目金额总计+纯资金往来金额总计的和=调整差额的合计值)
			if(ajAmount.compareTo(new BigDecimal("0")) !=0){
				if(billAmount.compareTo(cpAmount.add(ajAmount)) ==0 && ajAmount.compareTo(marginAmount) == 0  && marginAmount.compareTo(new BigDecimal("0")) !=0){
					infoObject.setRight(true);
					return infoObject;
				}else{
					log.debug("供应商清账检查单清账的数据正确性---"+ infoObject.CODE_7);
					infoObject.setInfo(infoObject.CODE_7);
					infoObject.setRight(false);
					return infoObject;
				}
			}else{
				log.debug("供应商清账检查单清账的数据正确性---"+ infoObject.CODE_6);
				infoObject.setInfo(infoObject.CODE_6);
				infoObject.setRight(false);
				return infoObject;
			}
		}
	}
	/**
	 * 检查退款清账的数据正确性
	 *  * 1。是否有相同的数据，
	 * 2。是否能保每次清账一定要至少一边全清，并且部分清只能只有一行有余额未清。
	 * 3。保证清账的单借贷方相等
	 *  @param supplierRefundment（重新取通过ID去数据库取得该对象）
	 * @return
	 */
	public IInfo checkClearData(SupplierRefundment supplierRefundment,BigDecimal marginAmount,boolean isSave){
		InfoObject infoObject =new InfoObject();
		//默认为true
		infoObject.setRight(true);
		Set<SupplierRefundItem> supplierRefundItems =supplierRefundment.getSupplierRefundItem();			
		List<String> ids = new ArrayList<String>();

//		判断有几条数据为部分清
		int i=0;
//		判断有几条数据为保证金
		int k=0;
//		
		boolean isa = false;
//		票的总额
//		BigDecimal billAmount = new BigDecimal("0");
//		款的总额
//		BigDecimal cpAmount = new BigDecimal("0");
//		调整金
		BigDecimal ajAmount = new BigDecimal("0");
		ids = new ArrayList<String>();
		i=0;
//		有在批的单据也算部分清
		boolean isonload =false;
		
		for (SupplierRefundItem supplierRefundItem : supplierRefundItems){
			
			//2.是否能保每次清账一定要至少一边全清，并且部分清只能只有一行有余额未清
//			没有部分清的金额 = 可退金额 - 实际退款金额 ,如果等于0说明全清，
			BigDecimal clearAmount = supplierRefundItem.getUnassignamount().subtract(supplierRefundItem.getPefundmentamount());
//			只要预收款 不为空说明是部分清
			if(supplierRefundItem.getPefundmentamount().compareTo(supplierRefundItem.getUnassignamount()) !=0){		
				i++;
			}			
			ajAmount = ajAmount.add(clearAmount);
//			在批的			
			BigDecimal onroadamount1 = this.paymentItemService.getSumClearAmount(supplierRefundItem.getPaymentitemid(), BusinessState.ALL_PAYMENT_ONROAD);
//			如果已经保存了，去掉本次的
			if(isSave){
				BigDecimal onroadamount2 = new BigDecimal("0");
//				判断是否纯代理,是取体位币
				boolean ispureagent = this.paymentItemService.isPureAgent(supplierRefundItem.getPaymentitemid());
				SupplierRefundItem ri=supplierRefundItemService._get(supplierRefundItem.getRefundmentitemid());
				
					if(ispureagent){
						if(null != ri && null != ri.getRefundmentvalue()){
							onroadamount2 = ri.getRefundmentvalue();
						}
					}else{
						if(null != ri && null != ri.getRefundmentamount()){
							onroadamount2 = ri.getRefundmentamount();
						}
					}
				
				onroadamount1 = onroadamount1.subtract(onroadamount2);
			}
			if(onroadamount1.compareTo(new BigDecimal("0")) > 0){
				isonload=true;
			}
		}
		if(isonload){
			log.debug("供应商清账检查单清账的数据正确性---"+ infoObject.CODE_2 + "(有在批的单据)");
			infoObject.setInfo(infoObject.CODE_2  + "(有在批的单据)");
			infoObject.setRight(false);
			return infoObject;
		}
//		部分清只能只有一行有余额未清
		if(i>1){
			log.debug("供应商清账检查单清账的数据正确性---"+ infoObject.CODE_3);
			infoObject.setInfo(infoObject.CODE_3);
			infoObject.setRight(false);
			return infoObject;
		}
//		保证金只能有一条
		if(k>1){
			log.debug("供应商清账检查单清账的数据正确性---"+ infoObject.CODE_4);
			infoObject.setInfo(infoObject.CODE_4);
			infoObject.setRight(false);
			return infoObject;
		}
//		有保证金只能这一条有部分清(在判断的最后面)
		if(k==1 && !isa){
			log.debug("供应商清账检查单清账的数据正确性---"+ infoObject.CODE_5);
			infoObject.setInfo(infoObject.CODE_5);
			infoObject.setRight(false);
			return infoObject;
		}
//		清账的单借贷方不相等，	(贷方的清账总金额 = 本次已抵消收款 总金额 + 调整差额的合计 (有正有负))
//		(结算科目金额总计+纯资金往来金额总计的和=调整差额的合计值)
//		if(ajAmount.compareTo(new BigDecimal("0")) !=0){
//			if(ajAmount.compareTo(marginAmount) == 0 && marginAmount.compareTo(new BigDecimal("0")) !=0 ){
//				infoObject.setRight(true);
//				return infoObject;
//			}else{
//				log.debug("客户清账检查单清账的数据正确性---"+ infoObject.CODE_7);
//				infoObject.setInfo(infoObject.CODE_7);
//				infoObject.setRight(false);
//				return infoObject;
//			}
//		}
		return infoObject;		
	}
	/** 判断是否能全清或部分清（只有两种情况）
	 * 
	 * 1.遍历寻找本次行项目有部分清的状态，还要去查找本次凭证以前部分清（审批通过，还有退款的审批通过）的金额合计，加上本次清账的金额是否等于总的金额（如应收款金额）,如果相等才能清账
	  * 2.A．贷方（票方），每一行的 未清付款金额 = 清账金额
	*	B．借方（款方），每一行的 未抵消付款 = 本次已抵消付款
	*	C. 借方的清账总金额 = 本次已抵消付款 总金额
	*	D．如果：贷方的清账总金额 不相等于 本次已抵消付款 总金额 那么
	*	(贷方的清账总金额 = 本次已抵消付款 总金额 + 调整差额的合计 (有正有负))
	*	(结算科目金额总计+纯资金往来金额总计的和=调整差额的合计值)
	*	（1）。ABC 同时满足可以出清账凭证, 外币还要出汇损
	*	（2）。ABD同时满足可以出清账凭证，同时出调整差额的凭证，外币还要出汇损
	 * @param supplierSinglClear
	 * @return   true 为能全清，false 为部分清
	 */
	public IInfoVoucher isClearAccount(SupplierSinglClear supplierSinglClear,BigDecimal marginAmount){
		InfoVoucherObject infoVoucherObject =new InfoVoucherObject();
		//默认为true
		infoVoucherObject.setRight(true);
//		默认能全清
		infoVoucherObject.setClear(true);
		Set<UnClearSupplieBill> unClearSupplieBills = supplierSinglClear.getUnClearSupplieBill();
		Set<UnClearPayment> unClearPayments = supplierSinglClear.getUnClearPayment();	
//		部分清的ID
		List<IKeyValue> billIdsByPart= new ArrayList<IKeyValue>(); 
		List<IKeyValue> cpIdsByPart= new ArrayList<IKeyValue>();
//		本次全清的ID
		List<IKeyValue> billIdsByAllClear= new ArrayList<IKeyValue>(); 
		List<IKeyValue> cpIdsByAllClear= new ArrayList<IKeyValue>();
		
//		所有的ID
		List<IKeyValue> billIds= new ArrayList<IKeyValue>(); 
		List<IKeyValue> cpIds= new ArrayList<IKeyValue>(); 
//		是否出汇损
		boolean ispl = this.isProfitAndLoss(supplierSinglClear.getCurrencytext(), supplierSinglClear.getCompanyno());
		
		//款未抵消本位币金额合计
		BigDecimal sumUnoffsetamountBwb = new BigDecimal("0");
		//未收款本位币金额合计
		BigDecimal sumUnreceivableamouBwb = new BigDecimal("0");
		
		//清帐金额本位币合计
		BigDecimal sumclearAmountBwb = new BigDecimal("0");
		//本次抵消本位币金额合计
		BigDecimal sumoffsetAmountBwb = new BigDecimal("0");
		//判断哪一边全清
		boolean billF = false;
		boolean clearF = false;		
		
		//合同号列表
		List<String> contractNoList = new ArrayList<String>();
		String projectNo =" ";
		
		// 重新计算未清金额(未收款、未付款等)
		for (UnClearSupplieBill unClearSupplieBill : unClearSupplieBills){
			// 发票总金额
			BigDecimal billamount = unClearSupplieBill.getReceivableamount();
			// 发票已经审批完的，发票已清金额
			BigDecimal receivedamount ;
			if(StringUtils.isNullBlank(unClearSupplieBill.getInvoice())){
				receivedamount = this.vendorService.getSumClearAmountByVendorTitleID(unClearSupplieBill.getVendortitleid().trim(), BusinessState.ALL_SUBMITPASS);
			}else{
				receivedamount = this.vendorService.getSumClearAmount(unClearSupplieBill.getInvoice(), BusinessState.ALL_SUBMITPASS);
			}
//			所有的清账金额
			BigDecimal allClearAmount = unClearSupplieBill.getClearamount().add(receivedamount);	
			
			KeyValue keyValue = new KeyValue();
			keyValue.setKey(unClearSupplieBill.getInvoice());
			keyValue.setValue(unClearSupplieBill.getVendortitleid());
			
			
//			等于（全清）
			if(billamount.compareTo(allClearAmount) == 0){
				billIdsByAllClear.add(keyValue);
			}
//			小于（数据错误）
			if(billamount.compareTo(allClearAmount) == -1){
				infoVoucherObject.setRight(false);
				infoVoucherObject.setInfo(infoVoucherObject.CODE_8 + unClearSupplieBill.getVoucherno());
				return infoVoucherObject;
			}
//			大于(是本次部分清)
			if(billamount.compareTo(allClearAmount) == 1){
				infoVoucherObject.setClear(false);
				billF = true;							
			}			
//			找部分清的数据
			if(receivedamount.compareTo(new BigDecimal("0")) !=0){
				billIdsByPart.add(keyValue);	
			}
			sumUnoffsetamountBwb = sumUnoffsetamountBwb.add(unClearSupplieBill.getUnbwbje());
			if(unClearSupplieBill.getReceivableamount().compareTo(BigDecimal.valueOf(0)) ==0){
				sumclearAmountBwb = sumclearAmountBwb.add(unClearSupplieBill.getBwbje());
			}else{
				sumclearAmountBwb = sumclearAmountBwb.add(unClearSupplieBill.getBwbje().multiply(unClearSupplieBill.getClearamount()).divide(unClearSupplieBill.getReceivableamount(),13,BigDecimal.ROUND_HALF_EVEN));
			}
			contractNoList.add(unClearSupplieBill.getContract_no());
			if(!StringUtils.isNullBlank(unClearSupplieBill.getProject_no()) && StringUtils.isNullBlank(projectNo)){
				projectNo = unClearSupplieBill.getProject_no();
			}
			//加入本次票的ID
			billIds.add(keyValue);			
		}
		
		
		
		for (UnClearPayment unClearPayment : unClearPayments){
			// 款总金额
			BigDecimal goodsamount = unClearPayment.getPaymentamount();
			// 收款单已审批完的 ，已清票款
			BigDecimal clearedPaymentAmount ; 
			if(StringUtils.isNullBlank(unClearPayment.getPaymentitemid())){
				// 已清金额
				clearedPaymentAmount = this.supplierSinglClearJdbcDao.getSumClearAmount(unClearPayment.getVendortitleid(), BusinessState.ALL_SUBMITPASS);
			}else{
				clearedPaymentAmount = this.paymentItemService.getSumClearAmount(unClearPayment.getPaymentitemid(), BusinessState.ALL_SUBMITPASS);
			}
			// 所有的清账金额	
			BigDecimal allClearAmount = unClearPayment.getNowclearamount().add(clearedPaymentAmount);
			
			KeyValue keyValue = new KeyValue();
			keyValue.setKey(unClearPayment.getPaymentitemid());
			keyValue.setValue(unClearPayment.getVendortitleid());
			
			
//			等于（全清）
			if(goodsamount.compareTo(allClearAmount) ==0){	
				cpIdsByAllClear.add(keyValue);
			}
//			小于（数据错误）
			if(goodsamount.compareTo(allClearAmount) == -1){	
				infoVoucherObject.setRight(false);
				infoVoucherObject.setInfo(infoVoucherObject.CODE_8 + unClearPayment.getVoucherno());
				return infoVoucherObject;			
			}
//			大于(是部分清)
			if(goodsamount.compareTo(allClearAmount) == 1){	
				infoVoucherObject.setClear(false);
				clearF = true;
			}			
//			找部分清的数据
			if(clearedPaymentAmount.compareTo(new BigDecimal("0")) !=0){
				cpIdsByPart.add(keyValue);
			}
			sumUnreceivableamouBwb= sumUnreceivableamouBwb.add(unClearPayment.getUnbwbje());
			if(unClearPayment.getPaymentamount().compareTo(BigDecimal.valueOf(0)) ==0){
				sumoffsetAmountBwb = sumoffsetAmountBwb.add(unClearPayment.getBwbje());
			}else{
				sumoffsetAmountBwb = sumoffsetAmountBwb.add(unClearPayment.getBwbje().multiply(unClearPayment.getNowclearamount()).divide(unClearPayment.getPaymentamount(),13,BigDecimal.ROUND_HALF_EVEN));
			}
			contractNoList.add(unClearPayment.getContract_no());
			if(!StringUtils.isNullBlank(unClearPayment.getProject_no()) && StringUtils.isNullBlank(projectNo)){
				projectNo = unClearPayment.getProject_no();
			}
			//加入本次款的ID
			cpIds.add(keyValue);			
		}
		infoVoucherObject.setBillIdsByPart(billIdsByPart);
		infoVoucherObject.setCpIds(cpIds);
		infoVoucherObject.setCpIdsByPart(cpIdsByPart);
		infoVoucherObject.setBillIds(billIds);
		infoVoucherObject.setBillIdsByAllClear(billIdsByAllClear);
		infoVoucherObject.setCpIdsByAllClear(cpIdsByAllClear);
//		加入本次单据的ID
		List<String> ids = new ArrayList<String>();
		ids.add(supplierSinglClear.getSuppliersclearid());
		infoVoucherObject.setIds(ids);
//		计算汇损差额
		if(ispl){
			BigDecimal subtractVlaue2 = this.getMarginByprofitAndLoss(clearF, billF, sumUnreceivableamouBwb, sumUnoffsetamountBwb, sumoffsetAmountBwb, sumclearAmountBwb);
//			计算汇损差额 贷H- 借S 调整金是  借S - 贷H 刚好是反方向 所以用加的
			subtractVlaue2 = subtractVlaue2.add(marginAmount);
			infoVoucherObject.setSubtractVlaue(subtractVlaue2);
			String taxCode = this.getTaxCode(contractNoList, projectNo);
			infoVoucherObject.setTaxCode(taxCode);
		}
		
		return infoVoucherObject;
	}
	/**
	 *  判断是否能全清或部分清（只有两种情况）
	 *  
	 * 遍历寻找本次行项目有部分清的状态，还要去查找本次凭证以前部分清（审批通过，还有退款的审批通过）的金额合计，加上本次清账的金额是否等于总的金额（如应收款金额）,如果相等才能清账
	  * 2.A．贷方（票方），每一行的 未清付款金额 = 清账金额
	*	B．借方（款方），每一行的 未抵消付款 = 本次已抵消付款
	*	C. 借方的清账总金额 = 本次已抵消付款 总金额
	*	D．如果：贷方的清账总金额 不相等于 本次已抵消付款 总金额 那么
	*	(贷方的清账总金额 = 本次已抵消付款 总金额 + 调整差额的合计 (有正有负))
	*	(结算科目金额总计+纯资金往来金额总计的和=调整差额的合计值)
	*	（1）。ABC 同时满足可以出清账凭证, 外币还要出汇损
	*	（2）。ABD同时满足可以出清账凭证，同时出调整差额的凭证，外币还要出汇损
	 * @param payment
	 * @return   true 为能全清，false 为部分清
	 */
	public IInfoVoucher isClearAccount(HomePayment payment,BigDecimal marginAmount){
		InfoVoucherObject infoVoucherObject =new InfoVoucherObject();
		//默认为true
		infoVoucherObject.setRight(true);
//		默认能全清
		infoVoucherObject.setClear(true);
		Set<HomePaymentCbill> homePaymentCbills =payment.getHomePaymentCbill();
		Set<HomePaymentItem> homePaymentItems = payment.getHomePaymentItem();	
//		部分清的ID
		List<IKeyValue> billIdsByPart= new ArrayList<IKeyValue>(); 
		List<IKeyValue> cpIdsByPart= new ArrayList<IKeyValue>();
//		本次全清的ID
		List<IKeyValue> billIdsByAllClear= new ArrayList<IKeyValue>(); 
		List<IKeyValue> cpIdsByAllClear= new ArrayList<IKeyValue>();
//		所有的ID
		List<IKeyValue> billIds= new ArrayList<IKeyValue>(); 
		List<IKeyValue> cpIds= new ArrayList<IKeyValue>(); 
//		公司代码
		String bukrs = this.voucherService.getCompanyIDByDeptID(payment.getDept_id());
//		是否出汇损
		boolean ispl = false;
		String currency =null;
		
		//款未抵消本位币金额合计
		BigDecimal sumUnoffsetamountBwb = new BigDecimal("0");
		//未收款本位币金额合计
		BigDecimal sumUnreceivableamouBwb = new BigDecimal("0");
		
		//清帐金额本位币合计
		BigDecimal sumclearAmountBwb = new BigDecimal("0");
		//本次抵消本位币金额合计
		BigDecimal sumoffsetAmountBwb = new BigDecimal("0");
		//判断哪一边全清
		boolean billF = false;
		boolean clearF = false;
		
		//合同号列表
		List<String> contractNoList = new ArrayList<String>();
		String projectNo =" ";
		
		// 重新计算未清金额(未收款、未付款等)
		for (HomePaymentCbill paymentCbill : homePaymentCbills){
//			取第一个币别
			if(StringUtils.isNullBlank(currency)){
				currency = paymentCbill.getCurrency();
			}
			// 发票总金额
			BigDecimal billamount = paymentCbill.getPayableamount();
			// 发票已经审批完的，发票已清金额
			BigDecimal receivedamount = this.vendorService.getSumClearAmount(paymentCbill.getBillno(), BusinessState.ALL_SUBMITPASS);
//			所有的清账金额
			BigDecimal allClearAmount = paymentCbill.getClearamount2().add(receivedamount);	
			
			KeyValue keyValue = new KeyValue();
			keyValue.setKey(paymentCbill.getBillno());
			keyValue.setValue(paymentCbill.getBillid());
			
			
//			等于（全清）
			if(billamount.compareTo(allClearAmount) == 0){
				billIdsByAllClear.add(keyValue);
			}
//			小于（数据错误）
			if(billamount.compareTo(allClearAmount) == -1){
				infoVoucherObject.setRight(false);
				infoVoucherObject.setInfo(infoVoucherObject.CODE_8 + paymentCbill.getVoucherno());
				return infoVoucherObject;
			}
//			大于(是部分清)
			if(billamount.compareTo(allClearAmount) == 1){
				infoVoucherObject.setClear(false);
				billF = true;
				
				
			}
//			找部分清的数据
			if(receivedamount.compareTo(new BigDecimal("0")) !=0){
				billIdsByPart.add(keyValue);
			}
			//汇率
			BigDecimal rat = new BigDecimal(1);
			VendorTitle vendorTitle = vendorTitleJdbcDao.getByInvoice(paymentCbill.getBillno());
			rat = vendorTitle.getBwbje().divide(billamount,5,BigDecimal.ROUND_HALF_EVEN);			
			BigDecimal Unbwbje = paymentCbill.getUnpaidamount().multiply(rat).setScale(2, BigDecimal.ROUND_HALF_UP);
			sumUnoffsetamountBwb = sumUnoffsetamountBwb.add(Unbwbje);
			if(paymentCbill.getPayableamount().compareTo(BigDecimal.valueOf(0)) ==0){				
				sumclearAmountBwb = sumclearAmountBwb.add(vendorTitle.getBwbje());
			}else{				
				sumclearAmountBwb = sumclearAmountBwb.add(vendorTitle.getBwbje().multiply(paymentCbill.getClearamount2()).divide(paymentCbill.getPayableamount(),13,BigDecimal.ROUND_HALF_EVEN));
			}
			contractNoList.add(paymentCbill.getContract_no());
			if(!StringUtils.isNullBlank(paymentCbill.getProject_no()) && StringUtils.isNullBlank(projectNo)){
				projectNo = paymentCbill.getProject_no();
			}
			//加入本次票的ID
			billIds.add(keyValue);
			
		}
		
		ispl = this.isProfitAndLoss(currency,bukrs);
		for (HomePaymentItem paymentItem : homePaymentItems){
			// 款总金额
			BigDecimal goodsamount = paymentItem.getAssignamount();
			
			// 所有的清账金额	
			BigDecimal allClearAmount = paymentItem.getAssignamount().subtract(paymentItem.getPrepayamount());
			
			KeyValue keyValue = new KeyValue();
			keyValue.setKey(paymentItem.getPaymentitemid());
//			keyValue.setValue(billInCollect.getCustomertitleid());
			
			
//			等于（全清）
			if(goodsamount.compareTo(allClearAmount) ==0){	
				cpIdsByAllClear.add(keyValue);
			}
//			小于（数据错误）
			if(goodsamount.compareTo(allClearAmount) == -1){	
				infoVoucherObject.setRight(false);
				infoVoucherObject.setInfo(infoVoucherObject.CODE_8 + paymentItem.getPaymentitemid());
				return infoVoucherObject;			
			}
//			大于(是部分清)
			if(goodsamount.compareTo(allClearAmount) == 1){	
				infoVoucherObject.setClear(false);				
				clearF = true;
			}
//			找部分清的数据
			if(allClearAmount.compareTo(new BigDecimal("0")) !=0){
				cpIdsByPart.add(keyValue);
			}
			//汇率
			BigDecimal rat = payment.getCloseexchangerat();
			BigDecimal Unbwbje = paymentItem.getAssignamount().multiply(rat).setScale(2, BigDecimal.ROUND_HALF_UP);
			sumUnreceivableamouBwb= sumUnreceivableamouBwb.add(Unbwbje);			
			sumoffsetAmountBwb = sumoffsetAmountBwb.add(allClearAmount.multiply(rat).setScale(2, BigDecimal.ROUND_HALF_UP));
			
			contractNoList.add(paymentItem.getContract_no());
			if(!StringUtils.isNullBlank(paymentItem.getProject_no()) && StringUtils.isNullBlank(projectNo)){
				projectNo = paymentItem.getProject_no();
			}
			//加入本次款的ID
			cpIds.add(keyValue);
			
		}
		infoVoucherObject.setBillIdsByPart(billIdsByPart);
		infoVoucherObject.setCpIds(cpIds);
		infoVoucherObject.setCpIdsByPart(cpIdsByPart);
		infoVoucherObject.setBillIds(billIds);
		infoVoucherObject.setBillIdsByAllClear(billIdsByAllClear);
		infoVoucherObject.setCpIdsByAllClear(cpIdsByAllClear);
		
//		加入本次单据的ID
		List<String> ids = new ArrayList<String>();
		ids.add(payment.getPaymentid());
		infoVoucherObject.setIds(ids);
//		计算汇损差额
		if(ispl){
			BigDecimal subtractVlaue2 = this.getMarginByprofitAndLoss(clearF, billF, sumUnreceivableamouBwb, sumUnoffsetamountBwb, sumoffsetAmountBwb, sumclearAmountBwb);
//			计算汇损差额 贷H- 借S 调整金是  借S - 贷H 刚好是反方向 所以用加的
			subtractVlaue2 = subtractVlaue2.add(marginAmount);
			infoVoucherObject.setSubtractVlaue(subtractVlaue2);
			String taxCode = this.getTaxCode(contractNoList, projectNo);
			infoVoucherObject.setTaxCode(taxCode);
		}
		
		return infoVoucherObject;
	}
	
	/**
	 *  判断是否能全清或部分清（只有两种情况）
	 *  
	 * 遍历寻找本次行项目有部分清的状态，还要去查找本次凭证以前部分清（审批通过，还有退款的审批通过）的金额合计，加上本次清账的金额是否等于总的金额（如应收款金额）,如果相等才能清账
	  * 2.A．贷方（票方），每一行的 未清付款金额 = 清账金额
	*	B．借方（款方），每一行的 未抵消付款 = 本次已抵消付款
	*	C. 借方的清账总金额 = 本次已抵消付款 总金额
	*	D．如果：贷方的清账总金额 不相等于 本次已抵消付款 总金额 那么
	*	(贷方的清账总金额 = 本次已抵消付款 总金额 + 调整差额的合计 (有正有负))
	*	(结算科目金额总计+纯资金往来金额总计的和=调整差额的合计值)
	*	（1）。ABC 同时满足可以出清账凭证, 外币还要出汇损
	*	（2）。ABD同时满足可以出清账凭证，同时出调整差额的凭证，外币还要出汇损
	 * @param payment
	 * @return   true 为能全清，false 为部分清
	 */
	public IInfoVoucher isClearAccount(HomeCreditPayment payment,BigDecimal marginAmount){
		InfoVoucherObject infoVoucherObject =new InfoVoucherObject();
		//默认为true
		infoVoucherObject.setRight(true);
//		默认能全清
		infoVoucherObject.setClear(true);
		Set<HomeCreditPayCbill> homePaymentCbills =payment.getHomeCreditPayCbill();
		Set<HomeCreditPayItem> homePaymentItems = payment.getHomeCreditPayItem();	
//		部分清的ID
		List<IKeyValue> billIdsByPart= new ArrayList<IKeyValue>(); 
		List<IKeyValue> cpIdsByPart= new ArrayList<IKeyValue>();
//		本次全清的ID
		List<IKeyValue> billIdsByAllClear= new ArrayList<IKeyValue>(); 
		List<IKeyValue> cpIdsByAllClear= new ArrayList<IKeyValue>();
//		所有的ID
		List<IKeyValue> billIds= new ArrayList<IKeyValue>(); 
		List<IKeyValue> cpIds= new ArrayList<IKeyValue>(); 
//		公司代码
		String bukrs = this.voucherService.getCompanyIDByDeptID(payment.getDept_id());
//		是否出汇损
		boolean ispl = false;
		String currency =null;
		
		//款未抵消本位币金额合计
		BigDecimal sumUnoffsetamountBwb = new BigDecimal("0");
		//未收款本位币金额合计
		BigDecimal sumUnreceivableamouBwb = new BigDecimal("0");
		
		//清帐金额本位币合计
		BigDecimal sumclearAmountBwb = new BigDecimal("0");
		//本次抵消本位币金额合计
		BigDecimal sumoffsetAmountBwb = new BigDecimal("0");
		//判断哪一边全清
		boolean billF = false;
		boolean clearF = false;
		
		//合同号列表
		List<String> contractNoList = new ArrayList<String>();
		String projectNo =" ";
		
		// 重新计算未清金额(未收款、未付款等)
		for (HomeCreditPayCbill paymentCbill : homePaymentCbills){
//			取第一个币别
			if(StringUtils.isNullBlank(currency)){
				currency = paymentCbill.getCurrency();
			}
			// 发票总金额
			BigDecimal billamount = paymentCbill.getPayableamount();
			// 发票已经审批完的，发票已清金额
			BigDecimal receivedamount = this.vendorService.getSumClearAmount(paymentCbill.getBillno(), BusinessState.ALL_SUBMITPASS);
//			所有的清账金额
			BigDecimal allClearAmount = paymentCbill.getClearamount2().add(receivedamount);	
			
			KeyValue keyValue = new KeyValue();
			keyValue.setKey(paymentCbill.getBillno());
			keyValue.setValue(paymentCbill.getBillid());
			
			
//			等于（全清）
			if(billamount.compareTo(allClearAmount) == 0){
				billIdsByAllClear.add(keyValue);
			}
//			小于（数据错误）
			if(billamount.compareTo(allClearAmount) == -1){
				infoVoucherObject.setRight(false);
				infoVoucherObject.setInfo(infoVoucherObject.CODE_8 + paymentCbill.getVoucherno());
				return infoVoucherObject;
			}
//			大于(是部分清)
			if(billamount.compareTo(allClearAmount) == 1){
				infoVoucherObject.setClear(false);
				billF = true;
				
				
			}
//			找部分清的数据
			if(receivedamount.compareTo(new BigDecimal("0")) !=0){
				billIdsByPart.add(keyValue);
			}
			//汇率
			BigDecimal rat = new BigDecimal(1);
			VendorTitle vendorTitle = vendorTitleJdbcDao.getByInvoice(paymentCbill.getBillno());
			rat = vendorTitle.getBwbje().divide(billamount,5,BigDecimal.ROUND_HALF_EVEN);			
			BigDecimal Unbwbje = paymentCbill.getUnpaidamount().multiply(rat).setScale(2, BigDecimal.ROUND_HALF_UP);
			sumUnoffsetamountBwb = sumUnoffsetamountBwb.add(Unbwbje);
			if(paymentCbill.getPayableamount().compareTo(BigDecimal.valueOf(0)) ==0){				
				sumclearAmountBwb = sumclearAmountBwb.add(vendorTitle.getBwbje());
			}else{				
				sumclearAmountBwb = sumclearAmountBwb.add(vendorTitle.getBwbje().multiply(paymentCbill.getClearamount2()).divide(paymentCbill.getPayableamount(),13,BigDecimal.ROUND_HALF_EVEN));
			}
			contractNoList.add(paymentCbill.getContract_no());
			if(!StringUtils.isNullBlank(paymentCbill.getProject_no()) && StringUtils.isNullBlank(projectNo)){
				projectNo = paymentCbill.getProject_no();
			}
			//加入本次票的ID
			billIds.add(keyValue);
			
		}
		
		ispl = this.isProfitAndLoss(currency,bukrs);
		for (HomeCreditPayItem paymentItem : homePaymentItems){
			// 款总金额
			BigDecimal goodsamount = paymentItem.getAssignamount();
			
			// 所有的清账金额	
			BigDecimal allClearAmount = paymentItem.getAssignamount().subtract(paymentItem.getPrepayamount());
			
			KeyValue keyValue = new KeyValue();
			keyValue.setKey(paymentItem.getPaymentitemid());
//			keyValue.setValue(billInCollect.getCustomertitleid());
			
			
//			等于（全清）
			if(goodsamount.compareTo(allClearAmount) ==0){	
				cpIdsByAllClear.add(keyValue);
			}
//			小于（数据错误）
			if(goodsamount.compareTo(allClearAmount) == -1){	
				infoVoucherObject.setRight(false);
				infoVoucherObject.setInfo(infoVoucherObject.CODE_8 + paymentItem.getPaymentitemid());
				return infoVoucherObject;			
			}
//			大于(是部分清)
			if(goodsamount.compareTo(allClearAmount) == 1){	
				infoVoucherObject.setClear(false);				
				clearF = true;
			}
//			找部分清的数据
			if(allClearAmount.compareTo(new BigDecimal("0")) !=0){
				cpIdsByPart.add(keyValue);
			}
			//汇率
			BigDecimal rat = payment.getCloseexchangerat();
			BigDecimal Unbwbje = paymentItem.getAssignamount().multiply(rat).setScale(2, BigDecimal.ROUND_HALF_UP);
			sumUnreceivableamouBwb= sumUnreceivableamouBwb.add(Unbwbje);			
			sumoffsetAmountBwb = sumoffsetAmountBwb.add(allClearAmount.multiply(rat).setScale(2, BigDecimal.ROUND_HALF_UP));
			
			contractNoList.add(paymentItem.getContract_no());
			if(!StringUtils.isNullBlank(paymentItem.getProject_no()) && StringUtils.isNullBlank(projectNo)){
				projectNo = paymentItem.getProject_no();
			}
			//加入本次款的ID
			cpIds.add(keyValue);
			
		}
		infoVoucherObject.setBillIdsByPart(billIdsByPart);
		infoVoucherObject.setCpIds(cpIds);
		infoVoucherObject.setCpIdsByPart(cpIdsByPart);
		infoVoucherObject.setBillIds(billIds);
		infoVoucherObject.setBillIdsByAllClear(billIdsByAllClear);
		infoVoucherObject.setCpIdsByAllClear(cpIdsByAllClear);
		
//		加入本次单据的ID
		List<String> ids = new ArrayList<String>();
		ids.add(payment.getPaymentid());
		infoVoucherObject.setIds(ids);
//		计算汇损差额
		if(ispl){
			BigDecimal subtractVlaue2 = this.getMarginByprofitAndLoss(clearF, billF, sumUnreceivableamouBwb, sumUnoffsetamountBwb, sumoffsetAmountBwb, sumclearAmountBwb);
//			计算汇损差额 贷H- 借S 调整金是  借S - 贷H 刚好是反方向 所以用加的
			subtractVlaue2 = subtractVlaue2.add(marginAmount);
			infoVoucherObject.setSubtractVlaue(subtractVlaue2);
			String taxCode = this.getTaxCode(contractNoList, projectNo);
			infoVoucherObject.setTaxCode(taxCode);
		}
		
		return infoVoucherObject;
	}
	
	public IInfoVoucher isClearAccount(ImportPayment payment,BigDecimal marginAmount){
		InfoVoucherObject infoVoucherObject =new InfoVoucherObject();
		//默认为true
		infoVoucherObject.setRight(true);
//		默认能全清
		infoVoucherObject.setClear(true);
		Set<ImportPaymentCbill> importPaymentCbills =payment.getImportPaymentCbill();
		Set<ImportPaymentItem> importPaymentItems = payment.getImportPaymentItem();	
//		部分清的ID
		List<IKeyValue> billIdsByPart= new ArrayList<IKeyValue>(); 
		List<IKeyValue> cpIdsByPart= new ArrayList<IKeyValue>();
//		本次全清的ID
		List<IKeyValue> billIdsByAllClear= new ArrayList<IKeyValue>(); 
		List<IKeyValue> cpIdsByAllClear= new ArrayList<IKeyValue>();
//		所有的ID
		List<IKeyValue> billIds= new ArrayList<IKeyValue>(); 
		List<IKeyValue> cpIds= new ArrayList<IKeyValue>(); 
//		公司代码
		String bukrs = this.voucherService.getCompanyIDByDeptID(payment.getDept_id());
//		是否出汇损
		boolean ispl = false;
		String currency =null;
		
		//款未抵消本位币金额合计
		BigDecimal sumUnoffsetamountBwb = new BigDecimal("0");
		//未收款本位币金额合计
		BigDecimal sumUnreceivableamouBwb = new BigDecimal("0");
		
		//清帐金额本位币合计
		BigDecimal sumclearAmountBwb = new BigDecimal("0");
		//本次抵消本位币金额合计
		BigDecimal sumoffsetAmountBwb = new BigDecimal("0");
		//判断哪一边全清
		boolean billF = false;
		boolean clearF = false;
		
		//合同号列表
		List<String> contractNoList = new ArrayList<String>();
		String projectNo =" ";
		
		// 重新计算未清金额(未收款、未付款等)
		for (ImportPaymentCbill paymentCbill : importPaymentCbills){
//			取第一个币别
			if(StringUtils.isNullBlank(currency)){
				currency = paymentCbill.getCurrency();
			}
			// 发票总金额
			BigDecimal billamount = paymentCbill.getPayableamount();
			// 发票已经审批完的，发票已清金额
			BigDecimal receivedamount = this.vendorService.getSumClearAmount(paymentCbill.getBillno(), BusinessState.ALL_SUBMITPASS);
//			所有的清账金额
			BigDecimal allClearAmount = paymentCbill.getClearamount2().add(receivedamount);	
			
			KeyValue keyValue = new KeyValue();
			keyValue.setKey(paymentCbill.getBillno());
			keyValue.setValue(paymentCbill.getBillid());
			
			
//			等于（全清）
			if(billamount.compareTo(allClearAmount) == 0){
				billIdsByAllClear.add(keyValue);
			}
//			小于（数据错误）
			if(billamount.compareTo(allClearAmount) == -1){
				infoVoucherObject.setRight(false);
				infoVoucherObject.setInfo(infoVoucherObject.CODE_8 + paymentCbill.getVoucherno());
				return infoVoucherObject;
			}
//			大于(是部分清)
			if(billamount.compareTo(allClearAmount) == 1){
				infoVoucherObject.setClear(false);
				billF = true;
				
				
			}
//			找部分清的数据
			if(receivedamount.compareTo(new BigDecimal("0")) !=0){
				billIdsByPart.add(keyValue);
			}
			//汇率
			BigDecimal rat = new BigDecimal(1);
			VendorTitle vendorTitle = vendorTitleJdbcDao.getByInvoice(paymentCbill.getBillno());
			rat = vendorTitle.getBwbje().divide(billamount,5,BigDecimal.ROUND_HALF_EVEN);			
			BigDecimal Unbwbje = paymentCbill.getUnpaidamount().multiply(rat).setScale(2, BigDecimal.ROUND_HALF_UP);
			sumUnoffsetamountBwb = sumUnoffsetamountBwb.add(Unbwbje);
			if(paymentCbill.getPayableamount().compareTo(BigDecimal.valueOf(0)) ==0){				
				sumclearAmountBwb = sumclearAmountBwb.add(vendorTitle.getBwbje());
			}else{				
				sumclearAmountBwb = sumclearAmountBwb.add(vendorTitle.getBwbje().multiply(paymentCbill.getClearamount2()).divide(paymentCbill.getPayableamount(),13,BigDecimal.ROUND_HALF_EVEN));
			}
			contractNoList.add(paymentCbill.getContract_no());
			if(!StringUtils.isNullBlank(paymentCbill.getProject_no()) && StringUtils.isNullBlank(projectNo)){
				projectNo = paymentCbill.getProject_no();
			}
			//加入本次票的ID
			billIds.add(keyValue);
			
		}
		
		ispl = this.isProfitAndLoss(currency,bukrs);
		for (ImportPaymentItem paymentItem : importPaymentItems){
			// 款总金额
			BigDecimal goodsamount = paymentItem.getAssignamount();
			
			// 所有的清账金额	
			BigDecimal allClearAmount = paymentItem.getAssignamount().subtract(paymentItem.getPrepayamount());
			
			KeyValue keyValue = new KeyValue();
			keyValue.setKey(paymentItem.getPaymentitemid());
//			keyValue.setValue(billInCollect.getCustomertitleid());
			
			
//			等于（全清）
			if(goodsamount.compareTo(allClearAmount) ==0){	
				cpIdsByAllClear.add(keyValue);
			}
//			小于（数据错误）
			if(goodsamount.compareTo(allClearAmount) == -1){	
				infoVoucherObject.setRight(false);
				infoVoucherObject.setInfo(infoVoucherObject.CODE_8 + paymentItem.getPaymentitemid());
				return infoVoucherObject;			
			}
//			大于(是部分清)
			if(goodsamount.compareTo(allClearAmount) == 1){	
				infoVoucherObject.setClear(false);				
				clearF = true;
			}
//			找部分清的数据
			if(allClearAmount.compareTo(new BigDecimal("0")) !=0){
				cpIdsByPart.add(keyValue);
			}
			//汇率
			BigDecimal rat = payment.getCloseexchangerat();
			BigDecimal Unbwbje = paymentItem.getAssignamount().multiply(rat).setScale(2, BigDecimal.ROUND_HALF_UP);
			sumUnreceivableamouBwb= sumUnreceivableamouBwb.add(Unbwbje);			
			sumoffsetAmountBwb = sumoffsetAmountBwb.add(allClearAmount.multiply(rat).setScale(2, BigDecimal.ROUND_HALF_UP));
			
			contractNoList.add(paymentItem.getContract_no());
			if(!StringUtils.isNullBlank(paymentItem.getProject_no()) && StringUtils.isNullBlank(projectNo)){
				projectNo = paymentItem.getProject_no();
			}
			//加入本次款的ID
			cpIds.add(keyValue);
			
		}
		infoVoucherObject.setBillIdsByPart(billIdsByPart);
		infoVoucherObject.setCpIds(cpIds);
		infoVoucherObject.setCpIdsByPart(cpIdsByPart);
		infoVoucherObject.setBillIds(billIds);
		infoVoucherObject.setBillIdsByAllClear(billIdsByAllClear);
		infoVoucherObject.setCpIdsByAllClear(cpIdsByAllClear);
		
//		加入本次单据的ID
		List<String> ids = new ArrayList<String>();
		ids.add(payment.getPaymentid());
		infoVoucherObject.setIds(ids);
//		计算汇损差额
		if(ispl){
			BigDecimal subtractVlaue2 = this.getMarginByprofitAndLoss(clearF, billF, sumUnreceivableamouBwb, sumUnoffsetamountBwb, sumoffsetAmountBwb, sumclearAmountBwb);
//			计算汇损差额 贷H- 借S 调整金是  借S - 贷H 刚好是反方向 所以用加的
			subtractVlaue2 = subtractVlaue2.add(marginAmount);
			infoVoucherObject.setSubtractVlaue(subtractVlaue2);
			String taxCode = this.getTaxCode(contractNoList, projectNo);
			infoVoucherObject.setTaxCode(taxCode);
		}
		
		return infoVoucherObject;
	}
	/**
	 *  判断是否能全清或部分清（只有两种情况）
	 *  
	 * 1.遍历寻找本次行项目有部分清的状态，还要去查找本次凭证以前部分清（审批通过，还有退款的审批通过）的金额合计，加上本次清账的金额是否等于总的金额（如应收款金额）,如果相等才能清账
	 * 2.A．贷方（票方），每一行的 未清付款金额 = 清账金额
	*	B．借方（款方），每一行的 未抵消付款 = 本次已抵消付款
	*	C. 借方的清账总金额 = 本次已抵消付款 总金额
	*	D．如果：贷方的清账总金额 不相等于 本次已抵消付款 总金额 那么
	*	(贷方的清账总金额 = 本次已抵消付款 总金额 + 调整差额的合计 (有正有负))
	*	(结算科目金额总计+纯资金往来金额总计的和=调整差额的合计值)
	*	（1）。ABC 同时满足可以出清账凭证, 外币还要出汇损
	*	（2）。ABD同时满足可以出清账凭证，同时出调整差额的凭证，外币还要出汇损
	 * @param billClearPayment
	 * @return   true 为能全清，false 为部分清
	 */
	public IInfoVoucher isClearAccount(BillClearPayment billClearPayment,BigDecimal marginAmount){
		InfoVoucherObject infoVoucherObject =new InfoVoucherObject();
		//默认为true
		infoVoucherObject.setRight(true);
//		默认能全清
		infoVoucherObject.setClear(true);
		Set<BillClearItemPay> billClearItemPays = billClearPayment.getBillClearItemPay();
		Set<BillInPayment> billInPayments = billClearPayment.getBillInPayment();
//		部分清的ID
		List<IKeyValue> billIdsByPart= new ArrayList<IKeyValue>(); 
		List<IKeyValue> cpIdsByPart= new ArrayList<IKeyValue>();
//		本次全清的ID
		List<IKeyValue> billIdsByAllClear= new ArrayList<IKeyValue>(); 
		List<IKeyValue> cpIdsByAllClear= new ArrayList<IKeyValue>();
//		所有的ID
		List<IKeyValue> billIds= new ArrayList<IKeyValue>(); 
		List<IKeyValue> cpIds= new ArrayList<IKeyValue>(); 
//		公司代码
		String bukrs = this.voucherService.getCompanyIDByDeptID(billClearPayment.getDeptid());
//		是否出汇损
		boolean ispl = false;
		String currency =null;
		
		//款未抵消本位币金额合计
		BigDecimal sumUnoffsetamountBwb = new BigDecimal("0");
		//未收款本位币金额合计
		BigDecimal sumUnreceivableamouBwb = new BigDecimal("0");
		
		//清帐金额本位币合计
		BigDecimal sumclearAmountBwb = new BigDecimal("0");
		//本次抵消本位币金额合计
		BigDecimal sumoffsetAmountBwb = new BigDecimal("0");
		//判断哪一边全清
		boolean billF = false;
		boolean clearF = false;
		
		//合同号列表
		List<String> contractNoList = new ArrayList<String>();
		String projectNo =" ";
		
		// 重新计算未清金额(未收款、未付款等)
		for (BillClearItemPay billClearItemPay : billClearItemPays){
//			取第一个币别
			if(StringUtils.isNullBlank(currency)){
				currency = billClearItemPay.getCurrency();
			}
			// 发票总金额
			BigDecimal billamount = billClearItemPay.getReceivableamount();
			// 发票已经审批完的，发票已清金额
			BigDecimal receivedamount = this.vendorService.getSumClearAmount(billClearItemPay.getInvoice(), BusinessState.ALL_SUBMITPASS);
//			所有的清账金额
			BigDecimal allClearAmount = billClearItemPay.getClearbillamount().add(receivedamount);	
			
			KeyValue keyValue = new KeyValue();
			keyValue.setKey(billClearItemPay.getInvoice());
			keyValue.setValue(billClearItemPay.getTitleid());
			
			
//			等于（全清）
			if(billamount.compareTo(allClearAmount) == 0){
				billIdsByAllClear.add(keyValue);
			}
//			小于（数据错误）
			if(billamount.compareTo(allClearAmount) == -1){
				infoVoucherObject.setRight(false);
				infoVoucherObject.setInfo(infoVoucherObject.CODE_8 + billClearItemPay.getVoucherno());
				return infoVoucherObject;
			}
//			大于(是部分清)
			if(billamount.compareTo(allClearAmount) == 1){
				infoVoucherObject.setClear(false);
				billF = true;
					
			}
//			找部分清的数据
			if(receivedamount.compareTo(new BigDecimal("0")) !=0){
				billIdsByPart.add(keyValue);			
			}
			sumUnoffsetamountBwb = sumUnoffsetamountBwb.add(billClearItemPay.getUnbwbje());
			if(billClearItemPay.getReceivableamount().compareTo(BigDecimal.valueOf(0)) ==0){
				sumclearAmountBwb = sumclearAmountBwb.add(billClearItemPay.getBwbje());
			}else{
				sumclearAmountBwb = sumclearAmountBwb.add(billClearItemPay.getBwbje().multiply(billClearItemPay.getClearbillamount()).divide(billClearItemPay.getReceivableamount(),13,BigDecimal.ROUND_HALF_EVEN));
			}
			contractNoList.add(billClearItemPay.getContract_no());
			if(!StringUtils.isNullBlank(billClearItemPay.getProject_no()) && StringUtils.isNullBlank(projectNo)){
				projectNo = billClearItemPay.getProject_no();
			}
			//加入本次票的ID
			billIds.add(keyValue);
			
		}
		
		ispl = this.isProfitAndLoss(currency,bukrs);
		for (BillInPayment billInPayment : billInPayments){
			// 款总金额
			BigDecimal goodsamount = billInPayment.getPaymentamount();
			// 收款单已审批完的 ，已清票款
			BigDecimal clearedPaymentAmount = this.paymentItemService.getSumClearAmount(billInPayment.getPaymentitemid(), BusinessState.ALL_SUBMITPASS);
			// 所有的清账金额	
			BigDecimal allClearAmount = billInPayment.getNowclearamount().add(clearedPaymentAmount);
			
			KeyValue keyValue = new KeyValue();
			keyValue.setKey(billInPayment.getPaymentitemid());
//			keyValue.setValue(billInCollect.getCustomertitleid());
			
			
//			等于（全清）
			if(goodsamount.compareTo(allClearAmount) ==0){	
				cpIdsByAllClear.add(keyValue);
			}
//			小于（数据错误）
			if(goodsamount.compareTo(allClearAmount) == -1){	
				infoVoucherObject.setRight(false);
				infoVoucherObject.setInfo(infoVoucherObject.CODE_8 + billInPayment.getVoucherno());
				return infoVoucherObject;			
			}
//			大于(是部分清)
			if(goodsamount.compareTo(allClearAmount) == 1){	
				infoVoucherObject.setClear(false);				
				
				clearF = true;
			}
//			找部分清的数据
			if(clearedPaymentAmount.compareTo(new BigDecimal("0")) !=0){
				cpIdsByPart.add(keyValue);	
			}
			sumUnreceivableamouBwb= sumUnreceivableamouBwb.add(billInPayment.getUnbwbje());
			if(billInPayment.getPaymentamount().compareTo(BigDecimal.valueOf(0)) ==0){
				sumoffsetAmountBwb = sumoffsetAmountBwb.add(billInPayment.getBwbje());
			}else{
				sumoffsetAmountBwb = sumoffsetAmountBwb.add(billInPayment.getBwbje().multiply(billInPayment.getNowclearamount()).divide(billInPayment.getPaymentamount(),13,BigDecimal.ROUND_HALF_EVEN));
			}
			contractNoList.add(billInPayment.getContract_no());
			if(!StringUtils.isNullBlank(billInPayment.getProject_no()) && StringUtils.isNullBlank(projectNo)){
				projectNo = billInPayment.getProject_no();
			}
			//加入本次款的ID
			cpIds.add(keyValue);
			
		}
		infoVoucherObject.setBillIdsByPart(billIdsByPart);
		infoVoucherObject.setCpIds(cpIds);
		infoVoucherObject.setCpIdsByPart(cpIdsByPart);
		infoVoucherObject.setBillIds(billIds);
		infoVoucherObject.setBillIdsByAllClear(billIdsByAllClear);
		infoVoucherObject.setCpIdsByAllClear(cpIdsByAllClear);
		
//		加入本次单据的ID
		List<String> ids = new ArrayList<String>();
		ids.add(billClearPayment.getBillclearid());
		infoVoucherObject.setIds(ids);
//		计算汇损差额
		if(ispl){
			BigDecimal subtractVlaue2 = this.getMarginByprofitAndLoss(clearF, billF, sumUnreceivableamouBwb, sumUnoffsetamountBwb, sumoffsetAmountBwb, sumclearAmountBwb);
//			计算汇损差额 贷H- 借S 调整金是  借S - 贷H 刚好是反方向 所以用加的
			subtractVlaue2 = subtractVlaue2.add(marginAmount);
			infoVoucherObject.setSubtractVlaue(subtractVlaue2);
			String taxCode = this.getTaxCode(contractNoList, projectNo);
			infoVoucherObject.setTaxCode(taxCode);
		}
		
		return infoVoucherObject;
	}
	/**
	 *  判断是否能全清或部分清（只有两种情况）
	 *  
	 * 遍历寻找本次行项目有部分清的状态，还要去查找本次凭证以前部分清（审批通过，还有退款的审批通过）的金额合计，加上本次清账的金额是否等于总的金额（如应收款金额）,如果相等才能清账
	  * 2.A．贷方（票方），每一行的 未清付款金额 = 清账金额
	*	B．借方（款方），每一行的 未抵消付款 = 本次已抵消付款
	*	C. 借方的清账总金额 = 本次已抵消付款 总金额
	*	D．如果：贷方的清账总金额 不相等于 本次已抵消付款 总金额 那么
	*	(贷方的清账总金额 = 本次已抵消付款 总金额 + 调整差额的合计 (有正有负))
	*	(结算科目金额总计+纯资金往来金额总计的和=调整差额的合计值)
	*	（1）。ABC 同时满足可以出清账凭证, 外币还要出汇损
	*	（2）。ABD同时满足可以出清账凭证，同时出调整差额的凭证，外币还要出汇损
	 * @param supplierRefundment
	 * @return   true 为能全清，false 为部分清
	 */
	public IInfoVoucher isClearAccount(SupplierRefundment supplierRefundment,BigDecimal marginAmount){
		InfoVoucherObject infoVoucherObject =new InfoVoucherObject();
		//默认为true
		infoVoucherObject.setRight(true);
//		默认能全清
		infoVoucherObject.setClear(true);
		
		Set<SupplierRefundItem> supplierRefundItems = supplierRefundment.getSupplierRefundItem();
//		部分清的ID
		List<IKeyValue> billIdsByPart= new ArrayList<IKeyValue>(); 
		List<IKeyValue> cpIdsByPart= new ArrayList<IKeyValue>();
//		本次全清的ID
		List<IKeyValue> billIdsByAllClear= new ArrayList<IKeyValue>(); 
		List<IKeyValue> cpIdsByAllClear= new ArrayList<IKeyValue>();
//		所有的ID
		List<IKeyValue> billIds= new ArrayList<IKeyValue>(); 
		List<IKeyValue> cpIds= new ArrayList<IKeyValue>(); 
//		公司代码
		String bukrs = this.voucherService.getCompanyIDByDeptID(supplierRefundment.getDept_id());
//		是否出汇损
		boolean ispl = false;
		String currency = supplierRefundment.getRefundcurrency();
		
		//款未抵消本位币金额合计
		BigDecimal sumUnoffsetamountBwb = new BigDecimal("0");
		//未收款本位币金额合计
		BigDecimal sumUnreceivableamouBwb = new BigDecimal("0");
		
		//清帐金额本位币合计
		BigDecimal sumclearAmountBwb = new BigDecimal("0");
		//本次抵消本位币金额合计
		BigDecimal sumoffsetAmountBwb = new BigDecimal("0");
		//判断哪一边全清
		boolean billF = false;
		boolean clearF = false;
		
		//合同号列表
		List<String> contractNoList = new ArrayList<String>();
		String projectNo =" ";
		ispl = this.isProfitAndLoss(currency,bukrs);
		for (SupplierRefundItem supplierRefundItem : supplierRefundItems){
			
			
			
			//退款金额本位币 REFUNDMENTVALUE
			BigDecimal staRefundmentAmount = supplierRefundItem.getRefundmentvalue();
			// 退款金额 * 退款的汇率	=清账本位币
			BigDecimal clearBwb = supplierRefundItem.getRefundmentamount().multiply(supplierRefundItem.getExchangerate()).setScale(2, BigDecimal.ROUND_HALF_UP);
			
			ImportPaymentItem paymentItem=this.paymentItemService.get(supplierRefundItem.getPaymentitemid());
			KeyValue keyValue = new KeyValue();
			keyValue.setKey(supplierRefundItem.getRefundmentitemid());
//			keyValue.setValue(billInCollect.getCustomertitleid());
			KeyValue keyValue2 = new KeyValue();
			keyValue2.setKey(supplierRefundItem.getPaymentitemid());
			// 收款单已审批完的 ，已清票款
			BigDecimal clearedAmount = this.paymentItemService.getSumClearAmount(supplierRefundItem.getPaymentitemid(), BusinessState.ALL_SUBMITPASS);
//			所有的清账金额
			BigDecimal allClearAmount  = clearedAmount.add(supplierRefundItem.getRefundmentamount());
			
//				等于（全清）
			if(paymentItem.getAssignamount().compareTo(allClearAmount) ==0){	
				cpIdsByAllClear.add(keyValue);
				billIdsByAllClear.add(keyValue2);
			}
//				小于（数据错误）
			if(paymentItem.getAssignamount().compareTo(allClearAmount) == -1){	
				infoVoucherObject.setRight(false);
				infoVoucherObject.setInfo(infoVoucherObject.CODE_8 + supplierRefundItem.getPaymentno());
				return infoVoucherObject;			
			}
//				大于(是部分清)
			if(paymentItem.getAssignamount().compareTo(allClearAmount) == 1){	
				infoVoucherObject.setClear(false);
				clearF = true;
			}
			sumoffsetAmountBwb = sumoffsetAmountBwb.add(clearBwb);
			sumclearAmountBwb = sumclearAmountBwb.add(staRefundmentAmount);
			contractNoList.add(supplierRefundItem.getContract_no());
			if(!StringUtils.isNullBlank(supplierRefundItem.getProject_no()) && StringUtils.isNullBlank(projectNo)){
				projectNo = supplierRefundItem.getProject_no();
			}
			//加入本次款的ID
			cpIds.add(keyValue);
			billIds.add(keyValue2);
		}
		infoVoucherObject.setBillIdsByPart(billIdsByPart);
		infoVoucherObject.setCpIds(cpIds);
		infoVoucherObject.setCpIdsByPart(cpIdsByPart);
		infoVoucherObject.setBillIds(billIds);
		infoVoucherObject.setBillIdsByAllClear(billIdsByAllClear);
		infoVoucherObject.setCpIdsByAllClear(cpIdsByAllClear);
		
//		加入本次单据的ID
		List<String> ids = new ArrayList<String>();
		ids.add(supplierRefundment.getRefundmentid());
		infoVoucherObject.setIds(ids);
//		计算汇损差额
		if(ispl){
			BigDecimal subtractVlaue2 = sumoffsetAmountBwb.subtract(sumclearAmountBwb);
//			计算汇损差额 贷H- 借S 调整金是  借S - 贷H 刚好是反方向 所以用加的
			subtractVlaue2 = subtractVlaue2.add(marginAmount);
			infoVoucherObject.setSubtractVlaue(subtractVlaue2);
			String taxCode = this.getTaxCode(contractNoList, projectNo);
			infoVoucherObject.setTaxCode(taxCode);
		}
		
		return infoVoucherObject;
	}
	
	


	/* 设置参数
	 * @see com.infolion.xdss3.singleClear.service.ISupplierClearAccount#setParameter(com.infolion.xdss3.billClear.domain.BillClearPayment)
	 */
	public ParameterVoucherObject setParameter(BillClearPayment billClearPayment) {
		ParameterVoucherObject parameterVoucher = new ParameterVoucherObject();
		parameterVoucher.setAccountdate(billClearPayment.getAccountdate());
		String bukrs =  this.voucherService.getCompanyIDByDeptID(billClearPayment.getDeptid());		
		parameterVoucher.setBukrs(bukrs);
		parameterVoucher.setBusinessid(billClearPayment.getBillclearid());
		parameterVoucher.setBusinesstype("08");
		parameterVoucher.setCheckCode("");
		parameterVoucher.setCheckCode2("");
		String taxCode = "";
		for (BillClearItemPay billClearItem : billClearPayment.getBillClearItemPay())
		{
			if(!"".equals(billClearItem.getCurrency().trim())){
				
				parameterVoucher.setCurrencytext(billClearItem.getCurrency());
				
			}
			if(StringUtils.isNullBlank(taxCode)){			
				if(!StringUtils.isNullBlank(billClearItem.getContract_no())){
					taxCode = billClearItem.getContract_no();
				}else{
					taxCode = billClearItem.getProject_no();
				}
			}
		}
		for(BillInPayment billInPayment : billClearPayment.getBillInPayment()){
			if(StringUtils.isNullBlank(taxCode)){			
				if(!StringUtils.isNullBlank(billInPayment.getContract_no())){
					taxCode = billInPayment.getContract_no();
				}else{
					taxCode = billInPayment.getProject_no();
				}
			}
		}
		String supplier_htext = this.customerRefundItemJdbcDao.getSupplierDescByCustomerId(billClearPayment.getSupplier(),bukrs);
		parameterVoucher.setSupplier_htext(supplier_htext);
		parameterVoucher.setSupplier(billClearPayment.getSupplier());
		parameterVoucher.setDebitcredit("");
		parameterVoucher.setFundFlow(billClearPayment.getFundFlow());
		//把部门ID转成业务范围
		String deptid = this.sysDeptJdbcDao.getDeptCodeById(billClearPayment.getDeptid());
		parameterVoucher.setGsber(deptid);
		parameterVoucher.setRownumber("");
		parameterVoucher.setSettleSubject(billClearPayment.getSettleSubject());
		parameterVoucher.setStrType("");
		parameterVoucher.setSubtractVlaue(new BigDecimal("0"));
		parameterVoucher.setSumAdjustamount(new BigDecimal("0"));		
//		String taxCode = billClearPaymentService.getTaxCode(billClearPayment);
		parameterVoucher.setTaxCode(taxCode);
		parameterVoucher.setText(billClearPayment.getText());
//		parameterVoucher.setVoucher();
		parameterVoucher.setVoucherclass("1");
		parameterVoucher.setVoucherdate(billClearPayment.getVoucherdate());
		parameterVoucher.setVouchertype("SA");		
		return parameterVoucher;		
	}


	/* 设置参数
	 * @see com.infolion.xdss3.singleClear.service.ISupplierClearAccount#setParameter(com.infolion.xdss3.payment.domain.HomePayment)
	 */
	public ParameterVoucherObject setParameter(HomePayment payment) {
		ParameterVoucherObject parameterVoucher = new ParameterVoucherObject();
		parameterVoucher.setAccountdate(payment.getAccountdate());
		String bukrs =  this.voucherService.getCompanyIDByDeptID(payment.getDept_id());		
		parameterVoucher.setBukrs(bukrs);
		parameterVoucher.setBusinessid(payment.getPaymentid());
		parameterVoucher.setBusinesstype("08");
		parameterVoucher.setCheckCode("");
		parameterVoucher.setCheckCode2("");
		parameterVoucher.setCurrencytext(payment.getCurrency());
		String supplier_htext = this.customerRefundItemJdbcDao.getSupplierDescByCustomerId(payment.getSupplier(),bukrs);
		parameterVoucher.setSupplier_htext(supplier_htext);
		parameterVoucher.setSupplier(payment.getSupplier());
		parameterVoucher.setDebitcredit("");
		parameterVoucher.setFundFlow(payment.getHomeFundFlow());
		//把部门ID转成业务范围
		String deptid = this.sysDeptJdbcDao.getDeptCodeById(payment.getDept_id());
		parameterVoucher.setGsber(deptid);
		parameterVoucher.setRownumber("");
		parameterVoucher.setSettleSubject(payment.getHomeSettSubj());
		parameterVoucher.setStrType("");
		parameterVoucher.setSubtractVlaue(new BigDecimal("0"));
		parameterVoucher.setSumAdjustamount(new BigDecimal("0"));				
		for(HomePaymentCbill cb:payment.getHomePaymentCbill()){
			if(StringUtils.isNullBlank(cb.getContract_no())){
				parameterVoucher.setTaxCode(cb.getContract_no());
			}else{
				parameterVoucher.setTaxCode(cb.getProject_no());
			}
			break;
		}
		
		parameterVoucher.setText(payment.getText());
//		parameterVoucher.setVoucher();
		parameterVoucher.setVoucherclass("1");
		parameterVoucher.setVoucherdate(payment.getVoucherdate());
		parameterVoucher.setVouchertype("SA");		
		return parameterVoucher;	
	}


	/* 设置参数
	 * @see com.infolion.xdss3.singleClear.service.ISupplierClearAccount#setParameter(com.infolion.xdss3.payment.domain.HomePayment)
	 */
	public ParameterVoucherObject setParameter(HomeCreditPayment payment) {
		ParameterVoucherObject parameterVoucher = new ParameterVoucherObject();
		parameterVoucher.setAccountdate(payment.getAccountdate());
		String bukrs =  this.voucherService.getCompanyIDByDeptID(payment.getDept_id());		
		parameterVoucher.setBukrs(bukrs);
		parameterVoucher.setBusinessid(payment.getPaymentid());
		parameterVoucher.setBusinesstype("08");
		parameterVoucher.setCheckCode("");
		parameterVoucher.setCheckCode2("");
		parameterVoucher.setCurrencytext(payment.getCurrency());
		String supplier_htext = this.customerRefundItemJdbcDao.getSupplierDescByCustomerId(payment.getSupplier(),bukrs);
		parameterVoucher.setSupplier_htext(supplier_htext);
		parameterVoucher.setSupplier(payment.getSupplier());
		parameterVoucher.setDebitcredit("");
		parameterVoucher.setFundFlow(payment.getFundFlow());
		//把部门ID转成业务范围
		String deptid = this.sysDeptJdbcDao.getDeptCodeById(payment.getDept_id());
		parameterVoucher.setGsber(deptid);
		parameterVoucher.setRownumber("");
		parameterVoucher.setSettleSubject(payment.getSettleSubject());
		parameterVoucher.setStrType("");
		parameterVoucher.setSubtractVlaue(new BigDecimal("0"));
		parameterVoucher.setSumAdjustamount(new BigDecimal("0"));				
		for(HomeCreditPayCbill cb:payment.getHomeCreditPayCbill()){
			if(StringUtils.isNullBlank(cb.getContract_no())){
				parameterVoucher.setTaxCode(cb.getContract_no());
			}else{
				parameterVoucher.setTaxCode(cb.getProject_no());
			}
			break;
		}
		
		parameterVoucher.setText(payment.getText());
//		parameterVoucher.setVoucher();
		parameterVoucher.setVoucherclass("1");
		parameterVoucher.setVoucherdate(payment.getVoucherdate());
		parameterVoucher.setVouchertype("SA");		
		return parameterVoucher;	
	}
	
	/* 设置参数
	 * @see com.infolion.xdss3.singleClear.service.ISupplierClearAccount#setParameter(com.infolion.xdss3.payment.domain.ImportPayment)
	 */
	public ParameterVoucherObject setParameter(ImportPayment payment) {
		ParameterVoucherObject parameterVoucher = new ParameterVoucherObject();
		parameterVoucher.setAccountdate(payment.getAccountdate());
		String bukrs =  this.voucherService.getCompanyIDByDeptID(payment.getDept_id());		
		parameterVoucher.setBukrs(bukrs);
		parameterVoucher.setBusinessid(payment.getPaymentid());
		parameterVoucher.setBusinesstype("08");
		parameterVoucher.setCheckCode("");
		parameterVoucher.setCheckCode2("");
		parameterVoucher.setCurrencytext(payment.getCurrency());
		String supplier_htext = this.customerRefundItemJdbcDao.getSupplierDescByCustomerId(payment.getSupplier(),bukrs);
		parameterVoucher.setSupplier_htext(supplier_htext);
		parameterVoucher.setSupplier(payment.getSupplier());
		parameterVoucher.setDebitcredit("");
		parameterVoucher.setFundFlow(payment.getImportFundFlow());
		//把部门ID转成业务范围
		String deptid = this.sysDeptJdbcDao.getDeptCodeById(payment.getDept_id());
		parameterVoucher.setGsber(deptid);
		parameterVoucher.setRownumber("");
		parameterVoucher.setSettleSubject(payment.getImportSettSubj());
		parameterVoucher.setStrType("");
		parameterVoucher.setSubtractVlaue(new BigDecimal("0"));
		parameterVoucher.setSumAdjustamount(new BigDecimal("0"));			
		for(ImportPaymentCbill cb:payment.getImportPaymentCbill()){
			if(StringUtils.isNullBlank(cb.getContract_no())){
				parameterVoucher.setTaxCode(cb.getContract_no());
			}else{
				parameterVoucher.setTaxCode(cb.getProject_no());
			}
			break;
		}
		
		parameterVoucher.setText(payment.getText());
//		parameterVoucher.setVoucher();
		parameterVoucher.setVoucherclass("1");
		parameterVoucher.setVoucherdate(payment.getVoucherdate());
		parameterVoucher.setVouchertype("SA");		
		return parameterVoucher;	
	}


	/* 设置参数
	 * @see com.infolion.xdss3.singleClear.service.ISupplierClearAccount#setParameter(com.infolion.xdss3.supplierDrawback.domain.SupplierRefundment)
	 */
	public ParameterVoucherObject setParameter(SupplierRefundment supplierRefundment) {
		ParameterVoucherObject parameterVoucher = new ParameterVoucherObject();
		parameterVoucher.setAccountdate(supplierRefundment.getAccountdate());
		String bukrs =  this.voucherService.getCompanyIDByDeptID(supplierRefundment.getDept_id());		
		parameterVoucher.setBukrs(bukrs);
		parameterVoucher.setBusinessid(supplierRefundment.getRefundmentid());
		parameterVoucher.setBusinesstype("08");
		parameterVoucher.setCheckCode("");
		parameterVoucher.setCheckCode2("");
		parameterVoucher.setCurrencytext(supplierRefundment.getRefundcurrency());
		String supplier_htext = this.customerRefundItemJdbcDao.getSupplierDescByCustomerId(supplierRefundment.getSupplier(),bukrs);
		parameterVoucher.setSupplier_htext(supplier_htext);
		parameterVoucher.setSupplier(supplierRefundment.getSupplier());
		parameterVoucher.setDebitcredit("");
		parameterVoucher.setFundFlow(supplierRefundment.getFundFlow());
		//把部门ID转成业务范围
		String deptid = this.sysDeptJdbcDao.getDeptCodeById(supplierRefundment.getDept_id());
		parameterVoucher.setGsber(deptid);
		parameterVoucher.setRownumber("");
		parameterVoucher.setSettleSubject(supplierRefundment.getSettleSubject());
		parameterVoucher.setStrType("");
		parameterVoucher.setSubtractVlaue(new BigDecimal("0"));
		parameterVoucher.setSumAdjustamount(new BigDecimal("0"));			
		for(SupplierRefundItem supplierRefundItem:supplierRefundment.getSupplierRefundItem()){
			if(StringUtils.isNullBlank(supplierRefundItem.getContract_no())){
				parameterVoucher.setTaxCode(supplierRefundItem.getContract_no());
			}else{
				parameterVoucher.setTaxCode(supplierRefundItem.getProject_no());
			}
			break;
		}
		
		parameterVoucher.setText(supplierRefundment.getText());
//		parameterVoucher.setVoucher();
		parameterVoucher.setVoucherclass("1");
		parameterVoucher.setVoucherdate(supplierRefundment.getVoucherdate());
		parameterVoucher.setVouchertype("SA");		
		return parameterVoucher;	
	}


	/* 设置参数
	 * @see com.infolion.xdss3.singleClear.service.ISupplierClearAccount#setParameter(com.infolion.xdss3.singleClear.domain.SupplierSinglClear)
	 */
	public ParameterVoucherObject setParameter(SupplierSinglClear supplierSinglClear) {
		ParameterVoucherObject parameterVoucher = new ParameterVoucherObject();
		parameterVoucher.setAccountdate(supplierSinglClear.getAccountdate());
		parameterVoucher.setBukrs(supplierSinglClear.getCompanyno());
		parameterVoucher.setBusinessid(supplierSinglClear.getSuppliersclearid());
		parameterVoucher.setBusinesstype("08");
		parameterVoucher.setCheckCode("");
		parameterVoucher.setCheckCode2("");
		parameterVoucher.setCurrencytext(supplierSinglClear.getCurrencytext());
		String supplier_htext = this.customerRefundItemJdbcDao.getSupplierDescByCustomerId(supplierSinglClear.getSupplier(),supplierSinglClear.getCompanyno());
		parameterVoucher.setSupplier_htext(supplier_htext);
		parameterVoucher.setSupplier(supplierSinglClear.getSupplier());
		parameterVoucher.setDebitcredit("");
		parameterVoucher.setFundFlow(supplierSinglClear.getFundFlow());
		parameterVoucher.setGsber(supplierSinglClear.getDepid());
		parameterVoucher.setRownumber("");
		parameterVoucher.setSettleSubject(supplierSinglClear.getSettleSubject());
		parameterVoucher.setStrType("");
		parameterVoucher.setSubtractVlaue(new BigDecimal("0"));
		
		Set<UnClearSupplieBill> unClearSupplieBills = supplierSinglClear.getUnClearSupplieBill();	
		BigDecimal sumAdjustamount= new BigDecimal("0");
		String taxCode = "";
		// 计算调整金之和
		for (UnClearSupplieBill unClearSupplieBill : unClearSupplieBills){
			sumAdjustamount = sumAdjustamount.add(unClearSupplieBill.getAdjustamount());
			if(StringUtils.isNullBlank(taxCode)){			
				if(!StringUtils.isNullBlank(unClearSupplieBill.getContract_no())){
					taxCode = unClearSupplieBill.getContract_no();
				}else{
					taxCode = unClearSupplieBill.getProject_no();
				}
			}
		}
		for(UnClearPayment unClearPayment :supplierSinglClear.getUnClearPayment()){
			if(StringUtils.isNullBlank(taxCode)){			
				if(!StringUtils.isNullBlank(unClearPayment.getContract_no())){
					taxCode = unClearPayment.getContract_no();
				}else{
					taxCode = unClearPayment.getProject_no();
				}
			}
		}
		parameterVoucher.setSumAdjustamount(sumAdjustamount);	
//		String taxCode = supplierSinglClearService.getTaxCode(supplierSinglClear);
		parameterVoucher.setTaxCode(taxCode);
		parameterVoucher.setText(supplierSinglClear.getText());
//		parameterVoucher.setVoucher();
		parameterVoucher.setVoucherclass("1");
		parameterVoucher.setVoucherdate(supplierSinglClear.getVoucherdate());
		parameterVoucher.setVouchertype("SA");		
		return parameterVoucher;
	}

	/**
	 * 更新isclear的状态
	 */
	public void updateIsClear(String businessid){
		List<VoucherItem> voucherItems = voucherItemJdbcDao.getVoucherItems(businessid);
		
		for(VoucherItem voucherItem : voucherItems){
//			如果不是清账凭证就不更新
			if(!"A".equals(voucherItem.getVoucher().getBstat()) && !"9".equals(voucherItem.getVoucher().getVoucherclass())){
				return ;
			}
			// 公司代码
			String companyCode = voucherItem.getVoucher().getCompanycode();
			// 财务凭证号
			String voucherNo =voucherItem.getVoucher().getVoucherno();
			// 会计年度
			String fiYear =voucherItem.getFiyear();
			//行项目
			String rowNumber = voucherItem.getRownumber();
			if(rowNumber.length()==2)rowNumber="0" + rowNumber;			
			if(rowNumber.length()==1)rowNumber="00" + rowNumber;
			log.debug("rowNumber:" + rowNumber);
			VendorTitle vendorTitle = vendorTitleJdbcDao.getByVoucherNo(companyCode, voucherNo, fiYear, rowNumber);
			if(null != vendorTitle){
				vendorService.updateVendorTitleIsCleared(vendorTitle.getVendortitleid(), Constants.cleared.sapIsCleared);							
			}
			String businessItemId = this.voucherItemJdbcDao.getBusinessId(voucherNo, companyCode, fiYear, rowNumber);
			
			// 付款信息
			ImportPaymentItem paymentItem = this.paymentItemService.get(businessItemId);
			if(null != paymentItem){				
				this.paymentItemService.updatePaymentItemIsCleared(paymentItem.getPaymentitemid(), Constants.cleared.sapIsCleared);	
			}
			CustomerRefundItem customerRefundItem =customerRefundItemService.get(businessItemId);
			if(null !=customerRefundItem){
				customerRefundItemService.updateRefundItemIsCleared(businessItemId, Constants.cleared.sapIsCleared);
			}			
		}
	}
	/**
	 * 同步ycustomertitle时要判断付款是否被清账，更新付款行项目的isclear
	 */
	public void updateIsClear(IInfoVoucher infoVoucher){
//		取出本次全清的数据
		for(IKeyValue keyValue:infoVoucher.getBillIdsByAllClear()){
			String billno = keyValue.getKey();
			String titleid = keyValue.getValue();
			if(StringUtils.isNullBlank(titleid)){
				VendorTitle vendorTitle =this.vendorTitleJdbcDao.getByInvoice(billno);
				titleid =vendorTitle.getVendortitleid();
			}
			this.vendorService.updateVendorTitleIsCleared(titleid, Constants.cleared.isCleared);
		}
		for(IKeyValue keyValue:infoVoucher.getCpIdsByAllClear()){
			String itemid = keyValue.getKey();
			String titleid = keyValue.getValue();
			if(!StringUtils.isNullBlank(titleid)){
				this.vendorService.updateVendorTitleIsCleared(titleid, Constants.cleared.isCleared);
			}else{
				VoucherItem voucherItem=voucherItemJdbcDao.getVoucherItem(itemid, "1,4");
				// 公司代码
				String companyCode = voucherItem.getVoucher().getCompanycode();
				// 财务凭证号
				String voucherNo =voucherItem.getVoucher().getVoucherno();
				// 会计年度
				String fiYear =voucherItem.getFiyear();
				//行项目
				String rowNumber = voucherItem.getRownumber();
				if(rowNumber.length()==2)rowNumber="0" + rowNumber;			
				if(rowNumber.length()==1)rowNumber="00" + rowNumber;
				
				VendorTitle vendorTitle = vendorTitleJdbcDao.getByVoucherNo(companyCode, voucherNo, fiYear, rowNumber);
				if(null != vendorTitle){
					this.vendorService.updateVendorTitleIsCleared(vendorTitle.getVendortitleid(), Constants.cleared.isCleared);
				}
			}
			if(!StringUtils.isNullBlank(itemid)){				
				this.paymentItemService.updatePaymentItemIsCleared(itemid, Constants.cleared.isCleared);				
			}
		}
	}
	
	
	
	/**
	 * 取得供应商清账凭证行项目
	 * @param List<IKeyValue> clearIdList , key 保存id,value 保存类型
	 * @return
	 */
	public Set<ClearVoucherItem> getClearVoucherItemBySupplier(List<IKeyValue> clearIdList){
		StringBuffer paymentSb = new StringBuffer();	
		StringBuffer refundmentSb = new StringBuffer();	
		StringBuffer refundmentSb2 = new StringBuffer();	
		StringBuffer billSb = new StringBuffer();	
		StringBuffer singleSb = new StringBuffer();	

		String paymentids ="";
		String refundmentids ="";
		String billids ="";
		String singleids ="";
		String ids ="";
		for(IKeyValue keyValue:clearIdList){
			String clid = keyValue.getKey();
			String type = keyValue.getValue();
			if(ClearConstant.PAYMENT_TYPE.equals(type)){
				if(StringUtils.isNullBlank(paymentids)){
					paymentids=clid;
				}else{
					paymentids= paymentids + "','" + clid ;
				}
				
			}
			if(ClearConstant.REFUNDMENT_TYPE_L.equals(type)){
				if(StringUtils.isNullBlank(refundmentids)){
					refundmentids=clid;
				}else{
					refundmentids= refundmentids + "','" + clid ;
				}
			}
			if(ClearConstant.BILL_TYPE_P.equals(type)){
				if(StringUtils.isNullBlank(billids)){
					billids=clid;
				}else{
					billids= billids + "','" + clid ;
				}
			}
			if(ClearConstant.SINGLE_TYPE_L.equals(type)){
				if(StringUtils.isNullBlank(singleids)){
					singleids=clid;
				}else{
					singleids= singleids +  "','" + clid ;
				}
			}
			
			if(StringUtils.isNullBlank(ids)){
				ids=clid;
			}else{
				ids= ids + "','" + clid ;
			}
			
		}	
//		查找款方
		paymentSb.append("SELECT pi.paymentitemid FROM ypaymentitem pi WHERE pi.paymentid IN ( '");
		refundmentSb.append("SELECT rei.refundmentitemid FROM yrefundmentitem rei  WHERE rei.refundmentid IN ( '");
		refundmentSb2.append("SELECT rei.paymentitemid FROM yrefundmentitem rei  WHERE rei.refundmentid IN ( '");
		billSb.append("SELECT bp.paymentitemid FROM ybillinpayment bp WHERE bp.billclearid IN ( '");
		singleSb.append("SELECT up.vendortitleid FROM yunclearpayment up  WHERE up.suppliersclearid IN ( '");
		paymentSb.append(paymentids + "')");
		refundmentSb.append(refundmentids + "')");
		refundmentSb2.append(refundmentids + "')");
		billSb.append(billids + "')");
		singleSb.append(singleids + "')");
//		为了去掉重复
		Set<ClearVoucherItem> viSet = new HashSet<ClearVoucherItem>();
//		如果没有id就不用找了，提高查询性能
		if(!StringUtils.isNullBlank(paymentids)){
			List<ClearVoucherItem> cList = supplierClearAccountJdbcDao.getVoucherItem(paymentSb.toString(),true);
			viSet.addAll(cList);
		}
		if(!StringUtils.isNullBlank(refundmentids)){
			List<ClearVoucherItem> rList = supplierClearAccountJdbcDao.getVoucherItem(refundmentSb.toString(),false);
			viSet.addAll(rList);
			List<ClearVoucherItem> rList2 = supplierClearAccountJdbcDao.getVoucherItem(refundmentSb2.toString(),false);
			viSet.addAll(rList2);
		}
		if(!StringUtils.isNullBlank(billids)){
			List<ClearVoucherItem> bList = supplierClearAccountJdbcDao.getVoucherItem(billSb.toString(),true);
			viSet.addAll(bList);
		}
		if(!StringUtils.isNullBlank(singleids)){
			List<ClearVoucherItem> sList = supplierClearAccountJdbcDao.getVoucherItemByTitle(singleSb.toString());
//			如果是单清账，收款行项目要做处理，重新取。
			List<Map> lim=supplierClearAccountJdbcDao.getunclearpayment(singleids);
			for(ClearVoucherItem cvi:sList){
				for(Map map:lim){
//					设置收款行项目
					if(map.get("vendortitleid").toString().equals(cvi.getBusinessitemid())){
						cvi.setBusinessitemid(map.get("paymentitemid").toString());
					}
				}
			}
			viSet.addAll(sList);
		}

//		清空,要通过使用sbi.setLength(0);来清空StringBuffer对象中的内容效率最高。
		paymentSb.setLength(0);	
		refundmentSb.setLength(0);	
		billSb.setLength(0);	
		singleSb.setLength(0);	
//		查找票方
		paymentSb.append("SELECT  pb.billid as vendortitleid FROM ypaymentcbill pb WHERE   pb.paymentid IN  ( '");		
		billSb.append("SELECT bci.titleid FROM ybillclearitem bci WHERE bci.billclearid IN ( '");
		singleSb.append("SELECT ub.vendortitleid FROM yunclearsuppbill ub WHERE ub.suppliersclearid IN ( '");
		paymentSb.append(paymentids + "')");		
		billSb.append(billids + "')");
		singleSb.append(singleids + "')");
		
		if(!StringUtils.isNullBlank(paymentids)){
			List<ClearVoucherItem> cList = supplierClearAccountJdbcDao.getVoucherItemByTitle(paymentSb.toString());
			viSet.addAll(cList);
		}
		if(!StringUtils.isNullBlank(billids)){
			List<ClearVoucherItem> bList = supplierClearAccountJdbcDao.getVoucherItemByTitle(billSb.toString());
			viSet.addAll(bList);
		}
		if(!StringUtils.isNullBlank(singleids)){
			List<ClearVoucherItem> sList = supplierClearAccountJdbcDao.getVoucherItemByTitle(singleSb.toString());
			viSet.addAll(sList);
		}
//		查找外围生成的汇损和调整金(有可能包含本次的已经生成的调整金,通过businessid来判断)
		List<ClearVoucherItem> pList =supplierClearAccountJdbcDao.getProfitAndLossByPart(ids);
		viSet.addAll(pList);		
		return viSet;
	}
	
	/**
	 * 取得供应商部分清的单据ID信息
	 * @return
	 */
	public List<IKeyValue> getPartClearBySupplier(IParameterVoucher infoVoucherObject,IInfoVoucher infoVoucher,String clearid,String type2){
	
		
//		保存清账的ID号
		List<IKeyValue> clearIdList = new ArrayList<IKeyValue>();

		KeyValue keyValue2 = new KeyValue(); 
		keyValue2.setKey(clearid);
		keyValue2.setValue(type2);
		clearIdList.add(keyValue2);
		InfoObject infoObject = new InfoObject();
		infoObject.setRight(true);

		List<IKeyValue> partIdsList = new ArrayList<IKeyValue>();
		partIdsList.addAll(infoVoucher.getCpIdsByPart());
		partIdsList.addAll(infoVoucher.getBillIdsByPart());
//		如果是退款，本次全清的款，也要去找以前有被清的数据
		if(ClearConstant.REFUNDMENT_TYPE_L.equals(type2)){
			partIdsList.addAll(infoVoucher.getBillIds());
		}
//		保存已经找过的票或款id
		List<String> titleIdList = new ArrayList<String>();
		List<String> paymentitemOrBillnoList = new ArrayList<String>();
		for(IKeyValue keyValue : partIdsList){
//		value保存titleid,key保存billno或collectitemid
			String titleid = keyValue.getValue();	
			String itemidOrBillno = keyValue.getKey();			
			titleIdList.add(titleid);
			paymentitemOrBillnoList.add(itemidOrBillno);
			getSupplierClearPart(titleid,itemidOrBillno,clearid,clearIdList,titleIdList,paymentitemOrBillnoList,infoObject);

		}
//		判断是否正确
		if(infoObject.isRight()){
			return clearIdList;
		}else{
			return null;
		}		
	}
	
	/***
	 * 循环找到所有客户有部分清的数据
	 * @param titleid
	 * @param clearid
	 * @param clearIdList
	 */
	public void getSupplierClearPart(String titleid,String itemidOrBillno,String clearid,List<IKeyValue> clearIdList,List<String> titleIdList,List<String> paymentitemOrBillnoList,InfoObject infoObject){
//		判断是否正确
		if(!infoObject.isRight()){
			return;
		}
//		取得除本次以外的款方有部分清的，客户清账ID 
		List<IKeyValue> clearList = supplierClearAccountJdbcDao.getPartIdsBySupplierClear(titleid, clearid,itemidOrBillno);
		for(IKeyValue keyValue2 : clearList){
			String clid = keyValue2.getKey();			
			String type2= keyValue2.getValue();
//			如果有在clearIdList说明有跟以前的单据重复，这是不允许的
			if(this.isSame(clid, clearIdList)){
//				如果是退款有多个行项目，可能出现重复
				if(ClearConstant.REFUNDMENT_TYPE_L.equals(type2))continue;
				infoObject.setRight(false);
				infoObject.setInfo(infoObject.CODE_9 + clid);
				infoObject.setCode(clid);
				log.debug(infoObject.CODE_9  + "clearid=" + clid + "titleid=" + titleid + "itemidOrBillno=" + itemidOrBillno);
				log.error(infoObject.CODE_9 + "clearid=" + clid + "titleid=" + titleid + "itemidOrBillno=" + itemidOrBillno);
				return;
			}else{
				KeyValue keyValue3 = new KeyValue(); 
				keyValue3.setKey(clid);
				keyValue3.setValue(type2);
				clearIdList.add(keyValue3);
			}			
//			取得本次有部分清的，款行项目titleID, IKeyValue key 为titleid,value 为collectitemID,value2 为客户单清账ID
			List<IKeyValue> kvList = supplierClearAccountJdbcDao.getPartIdsBySupplierClear(clid);
			for(IKeyValue keyValue :kvList){
				String titleid2 = keyValue.getKey();
				String itemidOrBillno2 = keyValue.getValue();	
//				如果是退款有清过账的收款不要再找
				if(ClearConstant.REFUNDMENT_TYPE_L.equals(type2) && paymentitemOrBillnoList.contains(itemidOrBillno2))continue;
//				如果有在titleIdList.CollectitemOrBillnoList说明有跟以前的有找过了，不用再找
				if(titleIdList.contains(titleid2) && paymentitemOrBillnoList.contains(itemidOrBillno2)){
					continue;
				}else{
					titleIdList.add(titleid2);
					paymentitemOrBillnoList.add(itemidOrBillno2);
				}
				getSupplierClearPart(titleid2,itemidOrBillno2,keyValue.getValue2(),clearIdList,titleIdList,paymentitemOrBillnoList,infoObject);

			}
		}

		
	}
	
	/* (non-Javadoc)
	 * @see com.infolion.xdss3.singleClear.service.ISupplierClearAccount#getClearVoucherItemBySupplier2(java.util.List, com.infolion.xdss3.voucher.domain.Voucher, java.lang.String)
	 */
	public Set<VoucherItem> getClearVoucherItemBySupplier2(List<IKeyValue> clearIdList, Voucher voucher, String voucherid) {
		Set<ClearVoucherItem> cviSet = this.getClearVoucherItemBySupplier(clearIdList);
		return this.getClearVoucherItems(cviSet,voucher,voucherid);		
	}


	/* (non-Javadoc)
	 * @see com.infolion.xdss3.singleClear.service.ISupplierClearAccount#saveClearVoucherBySupplier(com.infolion.xdss3.singleClear.domain.ParameterVoucherObject, com.infolion.xdss3.singleClear.domain.IInfoVoucher, java.lang.String, java.lang.String, boolean)
	 */
	public Voucher saveClearVoucherBySupplier(ParameterVoucherObject parameterVoucher, IInfoVoucher infoVoucher,String clearid, String type2, boolean isp) {
//		所有的清账voucherclass都是9
		parameterVoucher.setVoucherclass("9");
		Voucher voucher = this.getClearVoucher(parameterVoucher);
		List<IKeyValue>  clearIdList = this.getPartClearBySupplier(parameterVoucher, infoVoucher, clearid, type2);
		if(null == clearIdList)return null;
		Set<VoucherItem> viSet =this.getClearVoucherItemBySupplier2(clearIdList,voucher,parameterVoucher.getVoucherid());
//		取得没有凭证号的本次币
		BigDecimal bwb = new BigDecimal("0");
//		如果是付款，因为没有生成凭证,加入付款凭证行项目，
		if(ClearConstant.PAYMENT_TYPE.equals(type2)){
			
			List<ClearVoucherItem> viList = this.supplierClearAccountJdbcDao.getVoucherItemByPaymetid(clearid, "1,2,4");
			for(ClearVoucherItem clearVoucherItem:viList){
				VoucherItem voucherItem = getClearVoucherItem(clearVoucherItem);
//				为空说明没有生成凭证，加进去，有上面会取到
				if(StringUtils.isNullBlank(voucherItem.getVoucherno())){
					voucherItem.setBusvoucherid(clearVoucherItem.getVoucherid());
					voucherItem.setVoucher(voucher);
					viSet.add(voucherItem);		
					bwb = bwb.add(voucherItem.getAmount2());
				}	
			}
		}

//		如果是退款，因为没有生成凭证,加入付款凭证行项目，
		if(ClearConstant.REFUNDMENT_TYPE_L.equals(type2)){
			
			List<ClearVoucherItem> viList = this.supplierClearAccountJdbcDao.getVoucherItemByRefmentid(clearid, "1,2,4");
			for(ClearVoucherItem clearVoucherItem:viList){
				VoucherItem voucherItem = getClearVoucherItem(clearVoucherItem);
//				为空说明没有生成凭证，加进去，有上面会取到
				if(StringUtils.isNullBlank(voucherItem.getVoucherno())){
					voucherItem.setBusvoucherid(clearVoucherItem.getVoucherid());
					voucherItem.setVoucher(voucher);
					viSet.add(voucherItem);		
					bwb = bwb.subtract(voucherItem.getAmount2());
				}	
			}
			
			viList = this.supplierClearAccountJdbcDao.getPaymentVoucherItemByRefmentid(clearid, "1,2,4");
			for(ClearVoucherItem clearVoucherItem:viList){
				VoucherItem voucherItem = getClearVoucherItem(clearVoucherItem);				
				voucherItem.setVoucher(voucher);
//				判断是否已经存在,不存在加入
				if(!isExistSet(viSet,voucherItem)){
					viSet.add(voucherItem);						
				}					
			}
		}
//		有汇损，重新计算本位币（因为金额大会出现差额）
		if(isp){
			BigDecimal subtractVlaue = new BigDecimal("0");
			for(VoucherItem voucherItem :viSet){				
				if(!StringUtils.isNullBlank(voucherItem.getVoucherno())){
					VendorTitle vendorTitle = this.vendorTitleJdbcDao.getByVoucherNo(voucher.getCompanycode(), voucherItem.getVoucherno(), voucherItem.getFiyear(), voucherItem.getRownumber());
					if("S".equals(vendorTitle.getShkzg())){
						subtractVlaue = subtractVlaue.add(vendorTitle.getBwbje());
					}else{
						subtractVlaue =  subtractVlaue.subtract(vendorTitle.getBwbje());
					}
				}else{
					subtractVlaue =  subtractVlaue.add(bwb);
				}
			}
			parameterVoucher.setSubtractVlaue(subtractVlaue);
		}
//		是否出汇损,并且差额不为0
		if(isp && parameterVoucher.getSubtractVlaue().compareTo(new BigDecimal("0"))!= 0){
			
			parameterVoucher.setRownumber("001");
			parameterVoucher.setStrType("1");
			VoucherItem voucherItem1 = this.getProfitAndLossVoucherItem(parameterVoucher);
			voucherItem1.setVoucher(voucher);
			viSet.add(voucherItem1);			
			parameterVoucher.setRownumber("002");
			parameterVoucher.setStrType("2");
			VoucherItem voucherItem2 = this.getProfitAndLossVoucherItem(parameterVoucher);
			voucherItem2.setVoucher(voucher);
			viSet.add(voucherItem2);
		}
		voucher.setVoucherItem(viSet);
		//判断是否已经生成清账凭证，
		Voucher clearVoucher2=null;
		List<Voucher> li=voucherHibernateDao.getVoucherByBusinessId2(parameterVoucher.getBusinessid(),"A");
		if(li!=null && li.size()!=0){				
			for(Voucher vo : li){
				if(!StringUtils.isNullBlank(vo.getVoucherno()) && "9".equals(vo.getVoucherclass())){
					clearVoucher2=vo;
					break;
				}
			} 
		}
		if(null == clearVoucher2){
			this.voucherService._saveOrUpdate(voucher, null, BusinessObjectService.getBusinessObject("Voucher"));
		}else{
			voucher = clearVoucher2;
		}
		
		return voucher;
	}

	
	
	
}
