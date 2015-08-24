package com.infolion.xdss3.singleClear.service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infolion.platform.basicApp.authManage.UserContextHolder;
import com.infolion.platform.basicApp.authManage.domain.UserContext;
import com.infolion.platform.bo.service.BusinessObjectService;
import com.infolion.platform.console.org.dao.SysDeptJdbcDao;
import com.infolion.platform.dpframework.core.service.BaseService;
import com.infolion.platform.dpframework.util.DateUtils;
import com.infolion.platform.dpframework.util.StringUtils;
import com.infolion.xdss3.BusinessState;
import com.infolion.xdss3.Constants;
import com.infolion.xdss3.billclearcollect.domain.BillClearCollect;
import com.infolion.xdss3.billclearcollect.service.BillClearCollectService;
import com.infolion.xdss3.billclearitem.domain.BillClearItem;
import com.infolion.xdss3.billincollect.domain.BillInCollect;
import com.infolion.xdss3.collect.domain.Collect;
import com.infolion.xdss3.collectcbill.domain.CollectCbill;
import com.infolion.xdss3.collectitem.domain.CollectItem;
import com.infolion.xdss3.collectitem.service.CollectItemService;
import com.infolion.xdss3.customerDrawback.dao.CustomerRefundItemJdbcDao;
import com.infolion.xdss3.customerDrawback.domain.CustomerRefundItem;
import com.infolion.xdss3.customerDrawback.domain.CustomerRefundment;
import com.infolion.xdss3.customerDrawback.service.CustomerRefundItemService;
import com.infolion.xdss3.financeSquare.domain.FundFlow;
import com.infolion.xdss3.financeSquare.domain.SettleSubject;
import com.infolion.xdss3.fundflowBcc.domain.FundFlowBcc;
import com.infolion.xdss3.masterData.dao.CustomerJdbcDao;
import com.infolion.xdss3.masterData.dao.CustomerTitleHibernateDao;
import com.infolion.xdss3.masterData.dao.CustomerTitleJdbcDao;
import com.infolion.xdss3.masterData.domain.CustomerTitle;
import com.infolion.xdss3.masterData.domain.VendorTitle;
import com.infolion.xdss3.masterData.service.UnclearCustomerService;
import com.infolion.xdss3.payment.domain.ImportPaymentItem;
import com.infolion.xdss3.payment.service.PaymentItemService;
import com.infolion.xdss3.settlesubjectbcc.domain.SettleSubjectBcc;
import com.infolion.xdss3.singleClear.dao.CustomSingleClearJdbcDao;
import com.infolion.xdss3.singleClear.dao.CustomerClearAccountJdbcDao;
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
import com.infolion.xdss3.singleClear.domain.ParameterObject;
import com.infolion.xdss3.singleClear.domain.ParameterVoucherObject;
import com.infolion.xdss3.singleClear.domain.UnClearCollect;
import com.infolion.xdss3.singleClear.domain.UnClearCustomBill;
import com.infolion.xdss3.voucher.domain.Voucher;
import com.infolion.xdss3.voucher.service.VoucherService;
import com.infolion.xdss3.voucheritem.dao.VoucherItemJdbcDao;
import com.infolion.xdss3.voucheritem.domain.VoucherItem;

/**
 * * <pre>
 * 客户全面清帐(CustomerClearAccountService)服务类
 * </pre>
 * 
 * @author zhongzh
 * @version 1.0
 * @see The author for more details
 * @since 1.0
 */

@Service
public class CustomerClearAccountService extends IsClearAccount implements ICustomerClearAccount{
	
	
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
	private CollectItemService collectItemService;

	/**
	 * @param collectItemService
	 *            the collectItemService to set
	 */
	public void setCollectItemService(CollectItemService collectItemService)
	{
		this.collectItemService = collectItemService;
	}
	
	@Autowired
	private UnclearCustomerService unclearCustomerService;

	/**
	 * @param unclearCustomerService
	 *            the unclearCustomerService to set
	 */
	public void setUnclearCustomerService(UnclearCustomerService unclearCustomerService)
	{
		this.unclearCustomerService = unclearCustomerService;
	}
	
	@Autowired
	protected CustomSingleClearService customSingleClearService;

	public void setCustomSingleClearService(CustomSingleClearService customSingleClearService)
	{
		this.customSingleClearService = customSingleClearService;
	}
	
	@Autowired
	protected BillClearCollectService billClearCollectService;

	public void setBillClearCollectService(
			BillClearCollectService billClearCollectService) {
		this.billClearCollectService = billClearCollectService;
	}
	@Autowired
	private CustomSingleClearJdbcDao customSingleClearJdbcDao;

	/**
	 * @param customSingleClearJdbcDao
	 *            the customSingleClearJdbcDao to set
	 */
	public void setCustomSingleClearJdbcDao(CustomSingleClearJdbcDao customSingleClearJdbcDao)
	{
		this.customSingleClearJdbcDao = customSingleClearJdbcDao;
	}
	
	@Autowired
	protected CustomerTitleHibernateDao customerTitleHibernateDao;
	
	public void setCustomerTitleHibernateDao(
			CustomerTitleHibernateDao customerTitleHibernateDao) {
		this.customerTitleHibernateDao = customerTitleHibernateDao;
	}
	
	@Autowired
	protected CustomerTitleJdbcDao customerTitleJdbcDao;
	
	public void setCustomerTitleJdbcDao(
			CustomerTitleJdbcDao customerTitleJdbcDao) {
		this.customerTitleJdbcDao = customerTitleJdbcDao;
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
	protected CustomerJdbcDao customerJdbcDao;
	
	public void setCustomerJdbcDao(
			CustomerJdbcDao customerJdbcDao) {
		this.customerJdbcDao = customerJdbcDao;
	}
	
	@Autowired
	private VoucherItemJdbcDao voucherItemJdbcDao;

	
	public void setVoucherItemJdbcDao(VoucherItemJdbcDao voucherItemJdbcDao) {
		this.voucherItemJdbcDao = voucherItemJdbcDao;
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
	private SysDeptJdbcDao sysDeptJdbcDao;

	public void setSysDeptJdbcDao(SysDeptJdbcDao sysDeptJdbcDao) {
		this.sysDeptJdbcDao = sysDeptJdbcDao;
	}
	
	@Autowired
	private CustomerClearAccountJdbcDao customerClearAccountJdbcDao;
	
	/**
	 * @param customerClearAccountJdbcDao the customerClearAccountJdbcDao to set
	 */
	public void setCustomerClearAccountJdbcDao(
			CustomerClearAccountJdbcDao customerClearAccountJdbcDao) {
		this.customerClearAccountJdbcDao = customerClearAccountJdbcDao;
	}
	
	/**
	 * 第一步，有结算科目或纯资金凭证凭证要先出，不管能不能清账
	 * 第二步	 * 判断是否可以清账
	 * 第三步，外币要出汇损（2600公司本币是港币(HKD)，其他公司本币是人民币(CNY)）
	 * 第四步，更新isclear状态
	 * @param id 主表的id
	 * @param type 类型 1为单清账，2为票清收款 ，3为收款清票，4为退款
	 * @return
	 */
	public boolean isClearAccount(String id,String type){
		CustomSingleClear CustomSingleClear =null;
		Collect collect =null;
		BillClearCollect billClearCollect = null;
		CustomerRefundment customerRefundment = null;
		if(ClearConstant.SINGLE_TYPE.equals(type)){
			
		}
		if(ClearConstant.BILL_TYPE.equals(type)){
					
		}
		if(ClearConstant.COLLECT_TYPE.equals(type)){
			
		}
		if(ClearConstant.REFUNDMENT_TYPE.equals(type)){
			
		}
		return false;
	}
	
	
	
	
	
	
	
	
	/**
	 * 
	 * 检查单清账的数据正确性()
	 *  * 1。是否有相同的数据，
	 * 2。是否能保每次清账一定要至少一边全清，并且部分清只能只有一行有余额未清。（如果有保证金的话，部分清的一定要在保证金这一行。）
	 * 3。保证清账的单借贷方相等
	 *4.(贷方的清账总金额 = 本次已抵消收款 总金额 + 调整差额的合计 (有正有负))
	*	(结算科目金额总计+纯资金往来金额总计的和=调整差额的合计值)
	 * @param customSingleClear（重新取通过ID去数据库取得该对象）
	 *  @param marginAmount (差额)结算科目金额总计+纯资金往来金额总计的和
	 *  @param isSave 是否保存
	 * @return false 数据错误，true 数据正确
	 */
	public IInfo checkClearData(CustomSingleClear customSingleClear,BigDecimal marginAmount,boolean isSave){
		InfoObject infoObject =new InfoObject();
		//默认为true
		infoObject.setRight(true);
		
		Set<UnClearCustomBill> unClearCustomBills = customSingleClear.getUnClearCustomBill();
		Set<UnClearCollect> unClearCollects = customSingleClear.getUnClearCollect();	
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
		for (UnClearCustomBill unClearCustomBill : unClearCustomBills){
			// 1。检查是否有相同的数据，
			if(!ids.contains(unClearCustomBill.getCustomertitleid())){
				ids.add(unClearCustomBill.getCustomertitleid());
			}else{
				log.debug("客户清账检查单清账的数据正确性---"+infoObject.CODE_0+",Customertitleid=" + unClearCustomBill.getCustomertitleid());
				infoObject.setInfo(infoObject.CODE_0);
				infoObject.setRight(false);				
				return infoObject;
			}
		

			// 开票发票在途的，发票在途金额
			BigDecimal onroadamount = this.unclearCustomerService.getSumClearAmount(unClearCustomBill.getBillno(), BusinessState.ALL_COLLECT_ONROAD);
//			如果已经保存了，去掉本次的
			if(isSave){
				onroadamount = onroadamount.subtract(unClearCustomBill.getClearamount());
			}
			//2.是否能保每次清账一定要至少一边全清，并且部分清只能只有一行有余额未清,在批的也算部分清
			if(unClearCustomBill.getUnreceivableamou().compareTo(unClearCustomBill.getClearamount()) !=0 || onroadamount.compareTo(new BigDecimal("0")) > 0){
				billF = true;				
				i++;
				if(onroadamount.compareTo(new BigDecimal("0")) !=0){
					isonload=true;
				}
			}
			billAmount = billAmount.add(unClearCustomBill.getClearamount());
			ajAmount = ajAmount.add(unClearCustomBill.getAdjustamount());
		}
		
		ids = new ArrayList<String>();
		
		for (UnClearCollect unClearCollect : unClearCollects){
			// 1。检查是否有相同的数据，
			if(!ids.contains(unClearCollect.getCustomertitleid())){
				ids.add(unClearCollect.getCustomertitleid());
			}else{
				log.debug("客户清账检查单清账的数据正确性---"+infoObject.CODE_1+",Customertitleid=" + unClearCollect.getCustomertitleid());
				infoObject.setInfo(infoObject.CODE_1);
				infoObject.setRight(false);
				return infoObject;
			}
//			在批的
			BigDecimal onroadamount1 = new BigDecimal("0");
			if(StringUtils.isNullBlank(unClearCollect.getCollectitemid())){
				onroadamount1 = customSingleClearJdbcDao.getSumClearAmount2(unClearCollect.getCustomertitleid(), BusinessState.ALL_COLLECT_ONROAD);
			}else{
				onroadamount1 = this.collectItemService.getSumClearAmount(unClearCollect.getCollectitemid(), BusinessState.ALL_COLLECT_ONROAD);
			}
//			如果已经保存了，去掉本次的
			if(isSave){
				onroadamount1 = onroadamount1.subtract(unClearCollect.getNowclearamount());
			}
			//2.是否能保每次清账一定要至少一边全清，并且部分清只能只有一行有余额未清,在批的也算部分清
			if(unClearCollect.getUnoffsetamount().compareTo(unClearCollect.getNowclearamount()) !=0 || onroadamount1.compareTo(new BigDecimal("0")) >0){
				clearF = true;			
				i++;		
				if(onroadamount1.compareTo(new BigDecimal("0")) !=0){
					isonload=true;
				}
			}
			//检查是否有几条保证金
			if(unClearCollect.getSuretybond().compareTo(new BigDecimal("0")) !=0){
				k++;
				if(unClearCollect.getUnoffsetamount().compareTo(unClearCollect.getNowclearamount()) !=0){
					isa = true;
				}
			}
			cpAmount = cpAmount.add(unClearCollect.getNowclearamount());
		}
		//两边没有一边全清
		if(billF && clearF){
			if(isonload){
				log.debug("客户清账检查单清账的数据正确性---"+ infoObject.CODE_2 + "(有在批的单据)");
				infoObject.setInfo(infoObject.CODE_2  + "(有在批的单据)");
				infoObject.setRight(false);
				return infoObject;
			}else{
				log.debug("客户清账检查单清账的数据正确性---"+ infoObject.CODE_2);
				infoObject.setInfo(infoObject.CODE_2);
				infoObject.setRight(false);
				return infoObject;
			}
		}
//		部分清只能只有一行有余额未清
		if(i>1){
			log.debug("客户清账检查单清账的数据正确性---"+ infoObject.CODE_3);
			infoObject.setInfo(infoObject.CODE_3);
			infoObject.setRight(false);
			return infoObject;
		}
//		保证金只能有一条
		if(k>1){
			log.debug("客户清账检查单清账的数据正确性---"+ infoObject.CODE_4);
			infoObject.setInfo(infoObject.CODE_4);
			infoObject.setRight(false);
			return infoObject;
		}
//		有保证金只能这一条有部分清(在判断的最后面)
		if(k==1 && !isa){
			log.debug("客户清账检查单清账的数据正确性---"+ infoObject.CODE_5);
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
					log.debug("客户清账检查单清账的数据正确性---"+ infoObject.CODE_7);
					infoObject.setInfo(infoObject.CODE_7);
					infoObject.setRight(false);
					return infoObject;
				}
			}else{
				log.debug("客户清账检查单清账的数据正确性---"+ infoObject.CODE_6);
				infoObject.setInfo(infoObject.CODE_6);
				infoObject.setRight(false);
				return infoObject;
			}
		}
//		return infoObject;
	}
	/**
	 * 检查收款清账的数据正确性,
	 * 1。是否有相同的数据，
	 * 2。是否能保每次清账一定要至少一边全清，并且部分清只能只有一行有余额未清。（如果有保证金的话，部分清的一定要在保证金这一行。）
	 * 3。保证清账的单借贷方相等
	 * 4.收款，如果有清票，就不能有两条以上的保证金
	 * 
	 *  @param collect（重新取通过ID去数据库取得该对象）
	 * @return
	 */
	public IInfo checkClearData(Collect collect,BigDecimal marginAmount,boolean isSave){
		InfoObject infoObject =new InfoObject();
		//默认为true
		infoObject.setRight(true);
		Set<CollectCbill> collectCbills =collect.getCollectcbill();
		Set<CollectItem> collectItems = collect.getCollectitem();	
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
		for (CollectCbill collectCbill : collectCbills){
			// 1。检查是否有相同的数据，
			if(!ids.contains(collectCbill.getBillno())){
				ids.add(collectCbill.getBillno());
			}else{
				log.debug("客户清账检查单清账的数据正确性---"+infoObject.CODE_0+",Billno=" + collectCbill.getBillno());
				infoObject.setInfo(infoObject.CODE_0);
				infoObject.setRight(false);				
				return infoObject;
			}
			// 开票发票在途的，发票在途金额
			BigDecimal onroadamount = this.unclearCustomerService.getSumClearAmount(collectCbill.getBillno(), BusinessState.ALL_COLLECT_ONROAD);
//			如果已经保存了，去掉本次的
			if(isSave){
				onroadamount = onroadamount.subtract(collectCbill.getClearamount());
			}
			//2.是否能保每次清账一定要至少一边全清，并且部分清只能只有一行有余额未清
			if(collectCbill.getUnreceivedamount().compareTo(collectCbill.getClearamount()) !=0 || onroadamount.compareTo(new BigDecimal("0")) > 0){
				billF = true;				
				i++;
				if(onroadamount.compareTo(new BigDecimal("0")) !=0){
					isonload=true;
				}
			}
			billAmount = billAmount.add(collectCbill.getClearamount());
			
		}
		//如果没有票就不用做以下的判断了(数据正确，在清账时不出清账凭证)
		if(billAmount.compareTo(new BigDecimal("0")) == 0){
			infoObject.setRight(true);
			return infoObject;
		}
		ids = new ArrayList<String>();		
		for (CollectItem collectItem : collectItems){
			
			//2.是否能保每次清账一定要至少一边全清，并且部分清只能只有一行有余额未清
//			收款的清账金额 = 收款分配金额 - 预收款 - 保证金,如果等于0说明没有清，
			BigDecimal collectClearAmount = collectItem.getAssignamount().subtract(collectItem.getPrebillamount()).subtract(collectItem.getSuretybond());
//			只要预收款 或者保证金不为空说明是部分清
			if(collectItem.getSuretybond().compareTo(new BigDecimal("0")) !=0 || collectItem.getPrebillamount().compareTo(new BigDecimal("0")) !=0 ){
				clearF = true;			
				i++;
			}
			//检查是否有几条保证金
			if(collectItem.getSuretybond().compareTo(new BigDecimal("0")) !=0){
				k++;
				if(collectItem.getPrebillamount().compareTo(new BigDecimal("0")) !=0){
					isa = true;
				}
			}
			cpAmount = cpAmount.add(collectClearAmount);
		}
		//两边没有一边全清
		if(billF && clearF){
			if(isonload){
				log.debug("客户清账检查单清账的数据正确性---"+ infoObject.CODE_2 + "(有在批的单据)");
				infoObject.setInfo(infoObject.CODE_2  + "(有在批的单据)");
				infoObject.setRight(false);
				return infoObject;
			}else{
				log.debug("客户清账检查单清账的数据正确性---"+ infoObject.CODE_2);
				infoObject.setInfo(infoObject.CODE_2);
				infoObject.setRight(false);
				return infoObject;
			}
		}
//		部分清只能只有一行有余额未清
		if(i>1){
			log.debug("客户清账检查单清账的数据正确性---"+ infoObject.CODE_3);
			infoObject.setInfo(infoObject.CODE_3);
			infoObject.setRight(false);
			return infoObject;
		}
//		保证金只能有一条
		if(k>1){
			log.debug("客户清账检查单清账的数据正确性---"+ infoObject.CODE_4);
			infoObject.setInfo(infoObject.CODE_4);
			infoObject.setRight(false);
			return infoObject;
		}
//		有保证金只能这一条有部分清(在判断的最后面)
		if(k==1 && !isa){
			log.debug("客户清账检查单清账的数据正确性---"+ infoObject.CODE_5);
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
					log.debug("客户清账检查单清账的数据正确性---"+ infoObject.CODE_7);
					infoObject.setInfo(infoObject.CODE_7);
					infoObject.setRight(false);
					return infoObject;
				}
			}else{
				log.debug("客户清账检查单清账的数据正确性---"+ infoObject.CODE_6);
				infoObject.setInfo(infoObject.CODE_6);
				infoObject.setRight(false);
				return infoObject;
			}
			
		}
//		return infoObject;		
	}
	/**
	 * 检查票清收款清账的数据正确性
	 *  * 1。是否有相同的数据，
	 * 2。是否能保每次清账一定要至少一边全清，并且部分清只能只有一行有余额未清。（如果有保证金的话，部分清的一定要在保证金这一行。）
	 * 3。保证清账的单借贷方相等
	 * *4.(贷方的清账总金额 = 本次已抵消收款 总金额 + 调整差额的合计 (有正有负))
	*	(结算科目金额总计+纯资金往来金额总计的和=调整差额的合计值)
	* @param marginAmount (差额)结算科目金额总计+纯资金往来金额总计的和
	 *  @param billClearCollect（重新取通过ID去数据库取得该对象）
	 * @return
	 */
	public IInfo checkClearData(BillClearCollect billClearCollect,BigDecimal marginAmount,boolean isSave){
		InfoObject infoObject =new InfoObject();
		//默认为true
		infoObject.setRight(true);
		Set<BillClearItem> billClearItems = billClearCollect.getBillclearitem();
		Set<BillInCollect> billInCollects = billClearCollect.getBillincollect();	
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
		
		for (BillClearItem billClearItem : billClearItems){		
			// 1。检查是否有相同的数据，
			if(!ids.contains(billClearItem.getTitleid())){
				ids.add(billClearItem.getTitleid());
			}else{
				log.debug("客户清账检查单清账的数据正确性---"+infoObject.CODE_0+",Customertitleid=" + billClearItem.getTitleid());
				infoObject.setInfo(infoObject.CODE_0);
				infoObject.setRight(false);				
				return infoObject;
			}
			// 开票发票在途的，发票在途金额
			BigDecimal onroadamount = this.unclearCustomerService.getSumClearAmount(billClearItem.getInvoice(), BusinessState.ALL_COLLECT_ONROAD);
//			如果已经保存了，去掉本次的
			if(isSave){
				onroadamount = onroadamount.subtract(billClearItem.getClearbillamount());
			}
			//2.是否能保每次清账一定要至少一边全清，并且部分清只能只有一行有余额未清
			if(billClearItem.getUnreceivedamount().compareTo(billClearItem.getClearbillamount()) !=0 || onroadamount.compareTo(new BigDecimal("0"))  > 0){
				billF = true;				
				i++;
				if(onroadamount.compareTo(new BigDecimal("0")) !=0){
					isonload=true;
				}
			}
			billAmount = billAmount.add(billClearItem.getClearbillamount());
			ajAmount = ajAmount.add(billClearItem.getAdjustamount());
		}
		
		ids = new ArrayList<String>();
		
		for (BillInCollect billInCollect : billInCollects){
			// 1。检查是否有相同的数据，
			if(!ids.contains(billInCollect.getCollectitemid())){
				ids.add(billInCollect.getCollectitemid());
			}else{
				log.debug("客户清账检查单清账的数据正确性---"+infoObject.CODE_1+",Collectitemid=" + billInCollect.getCollectitemid());
				infoObject.setInfo(infoObject.CODE_1);
				infoObject.setRight(false);
				return infoObject;
			}
//			在批的			
			BigDecimal onroadamount1 = this.collectItemService.getSumClearAmount(billInCollect.getCollectitemid(), BusinessState.ALL_COLLECT_ONROAD);
//			如果已经保存了，去掉本次的
			if(isSave){
				onroadamount1 = onroadamount1.subtract(billInCollect.getNowclearamount());
			}
			//2.是否能保每次清账一定要至少一边全清，并且部分清只能只有一行有余额未清
			if(billInCollect.getUnoffsetamount().compareTo(billInCollect.getNowclearamount()) !=0 || onroadamount1.compareTo(new BigDecimal("0")) > 0){
				clearF = true;			
				i++;
				if(onroadamount1.compareTo(new BigDecimal("0")) !=0){
					isonload=true;
				}
			}
			//检查是否有几条保证金
			if(billInCollect.getSuretybond().compareTo(new BigDecimal("0")) !=0){
				k++;
				if(billInCollect.getUnoffsetamount().compareTo(billInCollect.getNowclearamount()) !=0){
					isa = true;
				}
			}
			cpAmount = cpAmount.add(billInCollect.getNowclearamount());
		}
		//两边没有一边全清
		if(billF && clearF){
			if(isonload){
				log.debug("客户清账检查单清账的数据正确性---"+ infoObject.CODE_2 + "(有在批的单据)");
				infoObject.setInfo(infoObject.CODE_2  + "(有在批的单据)");
				infoObject.setRight(false);
				return infoObject;
			}else{
				log.debug("客户清账检查单清账的数据正确性---"+ infoObject.CODE_2);
				infoObject.setInfo(infoObject.CODE_2);
				infoObject.setRight(false);
				return infoObject;
			}
		}
//		部分清只能只有一行有余额未清
		if(i>1){
			log.debug("客户清账检查单清账的数据正确性---"+ infoObject.CODE_3);
			infoObject.setInfo(infoObject.CODE_3);
			infoObject.setRight(false);
			return infoObject;
		}
//		保证金只能有一条
		if(k>1){
			log.debug("客户清账检查单清账的数据正确性---"+ infoObject.CODE_4);
			infoObject.setInfo(infoObject.CODE_4);
			infoObject.setRight(false);
			return infoObject;
		}
//		有保证金只能这一条有部分清(在判断的最后面)
		if(k==1 && !isa){
			log.debug("客户清账检查单清账的数据正确性---"+ infoObject.CODE_5);
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
					log.debug("客户清账检查单清账的数据正确性---"+ infoObject.CODE_7);
					infoObject.setInfo(infoObject.CODE_7);
					infoObject.setRight(false);
					return infoObject;
				}
			}else{
				log.debug("客户清账检查单清账的数据正确性---"+ infoObject.CODE_6);
				infoObject.setInfo(infoObject.CODE_6);
				infoObject.setRight(false);
				return infoObject;
			}
		}
//		return infoObject;
		
		
	}
	/**
	 * 检查退款清账的数据正确性
	 *  * 1。是否有相同的数据，
	 * 2。是否能保每次清账一定要至少一边全清，并且部分清只能只有一行有余额未清。（如果有保证金的话，部分清的一定要在保证金这一行。）
	 * 3。保证清账的单借贷方相等
	 *  @param customerRefundment（重新取通过ID去数据库取得该对象）
	 * @return
	 */
	public IInfo checkClearData(CustomerRefundment customerRefundment,BigDecimal marginAmount,boolean isSave){
		InfoObject infoObject =new InfoObject();
		//默认为true
		infoObject.setRight(true);
		Set<CustomerRefundItem> customerRefundItems =customerRefundment.getCustomerRefundItem();			
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
		
		for (CustomerRefundItem customerRefundItem : customerRefundItems){
			
			//2.是否能保每次清账一定要至少一边全清，并且部分清只能只有一行有余额未清
//			没有部分清的金额 = 可退金额 - 实际退款金额 ,如果等于0说明全清，
			BigDecimal clearAmount = customerRefundItem.getUnassignamount().subtract(customerRefundItem.getPefundmentamount());
//			只要预收款 或者保证金不为空说明是部分清
			if(customerRefundItem.getPefundmentamount().compareTo(customerRefundItem.getUnassignamount()) !=0){		
				i++;
			}
			//检查是否有几条保证金
			if("Y".equals(customerRefundItem.getIstybond())){
				k++;
				//实际退款金额 和 可退金额 是否一样，一样则是全退,不一样则部分退
				if(customerRefundItem.getPefundmentamount().compareTo(customerRefundItem.getUnassignamount()) !=0){
					isa = true;
				}
			}
			ajAmount = ajAmount.add(clearAmount);
//			在批的			
			BigDecimal onroadamount1 = this.collectItemService.getSumClearAmount(customerRefundItem.getCollectitemid(), BusinessState.ALL_COLLECT_ONROAD);
//			如果已经保存了，去掉本次的
			if(isSave){
				CustomerRefundItem ri=this.customerRefundItemService.get(customerRefundItem.getRefundmentitemid());
				if(null != ri && null != ri.getRefundmentamount()){
					onroadamount1 = onroadamount1.subtract(ri.getRefundmentamount());
				}
			}
			if(onroadamount1.compareTo(new BigDecimal("0")) > 0){
				isonload=true;
			}
		}
		if(isonload){
			log.debug("客户清账检查单清账的数据正确性---"+ infoObject.CODE_2 + "(有在批的单据)");
			infoObject.setInfo(infoObject.CODE_2  + "(有在批的单据)");
			infoObject.setRight(false);
			return infoObject;
		}
//		部分清只能只有一行有余额未清
		if(i>1){
			log.debug("客户清账检查单清账的数据正确性---"+ infoObject.CODE_3);
			infoObject.setInfo(infoObject.CODE_3);
			infoObject.setRight(false);
			return infoObject;
		}
//		保证金只能有一条
		if(k>1){
			log.debug("客户清账检查单清账的数据正确性---"+ infoObject.CODE_4);
			infoObject.setInfo(infoObject.CODE_4);
			infoObject.setRight(false);
			return infoObject;
		}
//		有保证金只能这一条有部分清(在判断的最后面)
		if(k==1 &&  customerRefundItems.size() > 1 && !isa){
			log.debug("客户清账检查单清账的数据正确性---"+ infoObject.CODE_5);
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
	/**
	 * 判断是否能全清或部分清（只有两种情况）
	 * 
	 * 1.遍历寻找本次行项目有部分清的状态，还要去查找本次凭证以前部分清（审批通过，还有退款的审批通过）的金额合计，加上本次清账的金额是否等于总的金额（如应收款金额）,如果相等才能清账
	  * 2.A．贷方（票方），每一行的 未清收款金额 = 清账金额
	*	B．借方（款方），每一行的 未抵消收款 = 本次已抵消收款
	*	C. 借方的清账总金额 = 本次已抵消收款 总金额
	*	D．如果：贷方的清账总金额 不相等于 本次已抵消收款 总金额 那么
	*	(贷方的清账总金额 = 本次已抵消收款 总金额 + 调整差额的合计 (有正有负))
	*	(结算科目金额总计+纯资金往来金额总计的和=调整差额的合计值)
	*	（1）。ABC 同时满足可以出清账凭证, 外币还要出汇损
	*	（2）。ABD同时满足可以出清账凭证，同时出调整差额的凭证，外币还要出汇损
	 * @param customSingleClear
	 * @return true 为能全清，false 为部分清
	 */
	public IInfoVoucher isClearAccount(CustomSingleClear customSingleClear,BigDecimal marginAmount){
		InfoVoucherObject infoVoucherObject =new InfoVoucherObject();
		//默认为true
		infoVoucherObject.setRight(true);
//		默认能全清
		infoVoucherObject.setClear(true);
		Set<UnClearCustomBill> unClearCustomBills = customSingleClear.getUnClearCustomBill();
		Set<UnClearCollect> unClearCollects = customSingleClear.getUnClearCollect();
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
		boolean ispl = this.isProfitAndLoss(customSingleClear.getCurrencytext(), customSingleClear.getCompanyno());
		
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
		for (UnClearCustomBill unClearCustomBill : unClearCustomBills){
			// 发票总金额
			BigDecimal billamount = unClearCustomBill.getReceivableamount();
			// 发票已经审批完的，发票已清金额
			BigDecimal receivedamount = this.unclearCustomerService.getSumClearAmount(unClearCustomBill.getBillno(), BusinessState.ALL_COLLECT_PAIDUP);
			if(!StringUtils.isNullBlank(unClearCustomBill.getPaymentitemid())){
				//根据付款itemid取得供应商退款纯代理金额合计
				BigDecimal receivedamount2 = this.paymentItemService.getRefundmentAmount(unClearCustomBill.getPaymentitemid());
				receivedamount = receivedamount.add(receivedamount2);
			}
//			所有的清账金额
			BigDecimal allClearAmount = unClearCustomBill.getClearamount().add(receivedamount);	
			
			KeyValue keyValue = new KeyValue();
			keyValue.setKey(unClearCustomBill.getBillno());
			keyValue.setValue(unClearCustomBill.getCustomertitleid());
			
			
//			等于（全清）
			if(billamount.compareTo(allClearAmount) == 0){
				billIdsByAllClear.add(keyValue);
			}
//			小于（数据错误）
			if(billamount.compareTo(allClearAmount) == -1){
				infoVoucherObject.setRight(false);
				infoVoucherObject.setInfo(infoVoucherObject.CODE_8 + unClearCustomBill.getVoucherno());
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
			sumUnoffsetamountBwb = sumUnoffsetamountBwb.add(unClearCustomBill.getUnbwbje());
			if(unClearCustomBill.getReceivableamount().compareTo(BigDecimal.valueOf(0)) ==0){
				sumclearAmountBwb = sumclearAmountBwb.add(unClearCustomBill.getBwbje());
			}else{
				sumclearAmountBwb = sumclearAmountBwb.add(unClearCustomBill.getBwbje().multiply(unClearCustomBill.getClearamount()).divide(unClearCustomBill.getReceivableamount(),13,BigDecimal.ROUND_HALF_EVEN));
			}
			contractNoList.add(unClearCustomBill.getContract_no());
			if(!StringUtils.isNullBlank(unClearCustomBill.getProject_no()) && StringUtils.isNullBlank(projectNo)){
				projectNo = unClearCustomBill.getProject_no();
			}
			//加入本次票的ID
			billIds.add(keyValue);			
		}
		
		
		
		for (UnClearCollect unClearCollect : unClearCollects){
			// 款总金额
			BigDecimal goodsamount = unClearCollect.getAmount();
			// 收款单已审批完的 ，已清票款
			BigDecimal clearedPaymentAmount = this.collectItemService.getSumClearAmount(unClearCollect.getCollectitemid(), BusinessState.ALL_COLLECT_PAIDUP);
			// 所有的清账金额	
			BigDecimal allClearAmount = unClearCollect.getNowclearamount().add(clearedPaymentAmount);
			
			KeyValue keyValue = new KeyValue();
			keyValue.setKey(unClearCollect.getCollectitemid());
			keyValue.setValue(unClearCollect.getCustomertitleid());
			
			
//			等于（全清）
			if(goodsamount.compareTo(allClearAmount) ==0){	
				cpIdsByAllClear.add(keyValue);
			}
//			小于（数据错误）
			if(goodsamount.compareTo(allClearAmount) == -1){	
				infoVoucherObject.setRight(false);
				infoVoucherObject.setInfo(infoVoucherObject.CODE_8 + unClearCollect.getVoucherno());
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
			sumUnreceivableamouBwb= sumUnreceivableamouBwb.add(unClearCollect.getUnbwbje());
			if(unClearCollect.getAmount().compareTo(BigDecimal.valueOf(0)) ==0){
				sumoffsetAmountBwb = sumoffsetAmountBwb.add(unClearCollect.getBwbje());
			}else{
				sumoffsetAmountBwb = sumoffsetAmountBwb.add(unClearCollect.getBwbje().multiply(unClearCollect.getNowclearamount()).divide(unClearCollect.getAmount(),13,BigDecimal.ROUND_HALF_EVEN));
			}
			contractNoList.add(unClearCollect.getContract_no());
			if(!StringUtils.isNullBlank(unClearCollect.getProject_no()) && StringUtils.isNullBlank(projectNo)){
				projectNo = unClearCollect.getProject_no();
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
		ids.add(customSingleClear.getCustomsclearid());
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
	 * 判断是否能全清或部分清（只有两种情况）
	 * 
	 * 遍历寻找本次行项目有部分清的状态，还要去查找本次凭证以前部分清（审批通过，还有退款的审批通过）的金额合计，加上本次清账的金额是否等于总的金额（如应收款金额）,如果相等才能清账
	  * 2.A．贷方（票方），每一行的 未清收款金额 = 清账金额
	*	B．借方（款方），每一行的 未抵消收款 = 本次已抵消收款
	*	C. 借方的清账总金额 = 本次已抵消收款 总金额
	*	D．如果：贷方的清账总金额 不相等于 本次已抵消收款 总金额 那么
	*	(贷方的清账总金额 = 本次已抵消收款 总金额 + 调整差额的合计 (有正有负))
	*	(结算科目金额总计+纯资金往来金额总计的和=调整差额的合计值)
	*	（1）。ABC 同时满足可以出清账凭证, 外币还要出汇损
	*	（2）。ABD同时满足可以出清账凭证，同时出调整差额的凭证，外币还要出汇损
	 * @param collect
	 * @return  true 为能全清，false 为部分清
	 */
	public IInfoVoucher isClearAccount(Collect collect,BigDecimal marginAmount){
		InfoVoucherObject infoVoucherObject =new InfoVoucherObject();
		//默认为true
		infoVoucherObject.setRight(true);
//		默认能全清
		infoVoucherObject.setClear(true);
		Set<CollectCbill> collectCbills = collect.getCollectcbill();
		Set<CollectItem> collectItems = collect.getCollectitem();
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
		String bukrs = this.voucherService.getCompanyIDByDeptID(collect.getDept_id());
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
		for (CollectCbill collectCbill : collectCbills){
//			取第一个币别
			if(StringUtils.isNullBlank(currency)){
				currency = collectCbill.getCurrency();
			}
			// 发票总金额
			BigDecimal billamount = collectCbill.getReceivableamount();
			// 发票已经审批完的，发票已清金额
			BigDecimal receivedamount = this.unclearCustomerService.getSumClearAmount(collectCbill.getBillno(), BusinessState.ALL_COLLECT_PAIDUP);
//			所有的清账金额
			BigDecimal allClearAmount = collectCbill.getClearamount().add(receivedamount);	
			
			KeyValue keyValue = new KeyValue();
			keyValue.setKey(collectCbill.getBillno());
//			keyValue.setValue(collectCbill.getTitleid());
			
			
//			等于（全清）
			if(billamount.compareTo(allClearAmount) == 0){
				billIdsByAllClear.add(keyValue);
			}
//			小于（数据错误）
			if(billamount.compareTo(allClearAmount) == -1){
				infoVoucherObject.setRight(false);
				infoVoucherObject.setInfo(infoVoucherObject.CODE_8 + collectCbill.getVoucherno());
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
			CustomerTitle customerTitle = customerTitleJdbcDao.getByInvoice(collectCbill.getBillno());
			rat = customerTitle.getBwbje().divide(billamount,5,BigDecimal.ROUND_HALF_EVEN);			
			BigDecimal Unbwbje = collectCbill.getUnreceivedamount().multiply(rat).setScale(2, BigDecimal.ROUND_HALF_UP);
			sumUnoffsetamountBwb = sumUnoffsetamountBwb.add(Unbwbje);
			if(collectCbill.getReceivableamount().compareTo(BigDecimal.valueOf(0)) ==0){				
				sumclearAmountBwb = sumclearAmountBwb.add(customerTitle.getBwbje());
			}else{				
				sumclearAmountBwb = sumclearAmountBwb.add(customerTitle.getBwbje().multiply(collectCbill.getClearamount()).divide(collectCbill.getReceivableamount(),13,BigDecimal.ROUND_HALF_EVEN));
			}
			contractNoList.add(collectCbill.getContract_no());
			if(!StringUtils.isNullBlank(collectCbill.getProject_no()) && StringUtils.isNullBlank(projectNo)){
				projectNo = collectCbill.getProject_no();
			}
			//加入本次票的ID
			billIds.add(keyValue);
			
		}
		
		ispl = this.isProfitAndLoss(currency,bukrs);
		for (CollectItem collectItem : collectItems){
			// 款总金额
			BigDecimal goodsamount = collectItem.getAssignamount();
			
			// 所有的清账金额	
			BigDecimal allClearAmount = collectItem.getAssignamount().subtract(collectItem.getSuretybond()).subtract(collectItem.getPrebillamount());
			
			KeyValue keyValue = new KeyValue();
			keyValue.setKey(collectItem.getCollectitemid());
//			keyValue.setValue(billInCollect.getCustomertitleid());
			
			
//			等于（全清）
			if(goodsamount.compareTo(allClearAmount) ==0){	
				cpIdsByAllClear.add(keyValue);
			}
//			小于（数据错误）
			if(goodsamount.compareTo(allClearAmount) == -1){	
				infoVoucherObject.setRight(false);
				infoVoucherObject.setInfo(infoVoucherObject.CODE_8 + collectItem.getCollectitemid());
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
			BigDecimal rat = collect.getCollectrate();
			BigDecimal Unbwbje = collectItem.getAssignamount().multiply(rat).setScale(2, BigDecimal.ROUND_HALF_UP);
			sumUnreceivableamouBwb= sumUnreceivableamouBwb.add(Unbwbje);			
			sumoffsetAmountBwb = sumoffsetAmountBwb.add(allClearAmount.multiply(rat).setScale(2, BigDecimal.ROUND_HALF_UP));
			
			contractNoList.add(collectItem.getContract_no());
			if(!StringUtils.isNullBlank(collectItem.getProject_no()) && StringUtils.isNullBlank(projectNo)){
				projectNo = collectItem.getProject_no();
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
		ids.add(collect.getCollectid());
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
	/**判断是否能全清或部分清（只有两种情况）
	 * 
	 * 1.遍历寻找本次行项目有部分清的状态，还要去查找本次凭证以前部分清（审批通过，还有退款的审批通过）的金额合计，加上本次清账的金额是否等于总的金额（如应收款金额）,如果相等才能清账
	 * 2.A．贷方（票方），每一行的 未清收款金额 = 清账金额
	*	B．借方（款方），每一行的 未抵消收款 = 本次已抵消收款
	*	C. 借方的清账总金额 = 本次已抵消收款 总金额
	*	D．如果：贷方的清账总金额 不相等于 本次已抵消收款 总金额 那么
	*	(贷方的清账总金额 = 本次已抵消收款 总金额 + 调整差额的合计 (有正有负))
	*	(结算科目金额总计+纯资金往来金额总计的和=调整差额的合计值)
	*	（1）。ABC 同时满足可以出清账凭证, 外币还要出汇损
	*	（2）。ABD同时满足可以出清账凭证，同时出调整差额的凭证，外币还要出汇损
	 * @param billClearCollect
	 * @return  true 为能全清，false 为部分清
	 */
	public IInfoVoucher isClearAccount(BillClearCollect billClearCollect,BigDecimal marginAmount){
		InfoVoucherObject infoVoucherObject =new InfoVoucherObject();
		//默认为true
		infoVoucherObject.setRight(true);
//		默认能全清
		infoVoucherObject.setClear(true);
		Set<BillClearItem> billClearItems = billClearCollect.getBillclearitem();
		Set<BillInCollect> billInCollects = billClearCollect.getBillincollect();
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
		String bukrs = this.voucherService.getCompanyIDByDeptID(billClearCollect.getDeptid());
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
		for (BillClearItem billClearItem : billClearItems){
//			取第一个币别
			if(StringUtils.isNullBlank(currency)){
				currency = billClearItem.getCurrency();
			}
			// 发票总金额
			BigDecimal billamount = billClearItem.getReceivableamount();
			// 发票已经审批完的，发票已清金额
			BigDecimal receivedamount = this.unclearCustomerService.getSumClearAmount(billClearItem.getInvoice(), BusinessState.ALL_COLLECT_PAIDUP);
//			所有的清账金额
			BigDecimal allClearAmount = billClearItem.getClearbillamount().add(receivedamount);	
			
			KeyValue keyValue = new KeyValue();
			keyValue.setKey(billClearItem.getInvoice());
			keyValue.setValue(billClearItem.getTitleid());
			
			
//			等于（全清）
			if(billamount.compareTo(allClearAmount) == 0){
				billIdsByAllClear.add(keyValue);
			}
//			小于（数据错误）
			if(billamount.compareTo(allClearAmount) == -1){
				infoVoucherObject.setRight(false);
				infoVoucherObject.setInfo(infoVoucherObject.CODE_8 + billClearItem.getVoucherno());
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
			sumUnoffsetamountBwb = sumUnoffsetamountBwb.add(billClearItem.getUnbwbje());
			if(billClearItem.getReceivableamount().compareTo(BigDecimal.valueOf(0)) ==0){
				sumclearAmountBwb = sumclearAmountBwb.add(billClearItem.getBwbje());
			}else{
				sumclearAmountBwb = sumclearAmountBwb.add(billClearItem.getBwbje().multiply(billClearItem.getClearbillamount()).divide(billClearItem.getReceivableamount(),13,BigDecimal.ROUND_HALF_EVEN));
			}
			contractNoList.add(billClearItem.getContract_no());
			if(!StringUtils.isNullBlank(billClearItem.getProject_no()) && StringUtils.isNullBlank(projectNo)){
				projectNo = billClearItem.getProject_no();
			}
			//加入本次票的ID
			billIds.add(keyValue);
			
		}
		
		ispl = this.isProfitAndLoss(currency,bukrs);
		for (BillInCollect billInCollect : billInCollects){
			// 款总金额
			BigDecimal goodsamount = billInCollect.getCollectamount();
			// 收款单已审批完的 ，已清票款
			BigDecimal clearedPaymentAmount = this.collectItemService.getSumClearAmount(billInCollect.getCollectitemid(), BusinessState.ALL_COLLECT_PAIDUP);
			// 所有的清账金额	
			BigDecimal allClearAmount = billInCollect.getNowclearamount().add(clearedPaymentAmount);
			
			KeyValue keyValue = new KeyValue();
			keyValue.setKey(billInCollect.getCollectitemid());
//			keyValue.setValue(billInCollect.getCustomertitleid());
			
			
//			等于（全清）
			if(goodsamount.compareTo(allClearAmount) ==0){	
				cpIdsByAllClear.add(keyValue);
			}
//			小于（数据错误）
			if(goodsamount.compareTo(allClearAmount) == -1){	
				infoVoucherObject.setRight(false);
				infoVoucherObject.setInfo(infoVoucherObject.CODE_8 + billInCollect.getVoucherno());
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
			sumUnreceivableamouBwb= sumUnreceivableamouBwb.add(billInCollect.getUnbwbje());
			if(billInCollect.getCollectamount().compareTo(BigDecimal.valueOf(0)) ==0){
				sumoffsetAmountBwb = sumoffsetAmountBwb.add(billInCollect.getBwbje());
			}else{
				sumoffsetAmountBwb = sumoffsetAmountBwb.add(billInCollect.getBwbje().multiply(billInCollect.getNowclearamount()).divide(billInCollect.getCollectamount(),13,BigDecimal.ROUND_HALF_EVEN));
			}
			contractNoList.add(billInCollect.getContract_no());
			if(!StringUtils.isNullBlank(billInCollect.getProject_no()) && StringUtils.isNullBlank(projectNo)){
				projectNo = billInCollect.getProject_no();
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
		ids.add(billClearCollect.getBillclearid());
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
	/**判断是否能全清或部分清（只有两种情况）
	 * 
	 * 遍历寻找本次行项目有部分清的状态，还要去查找本次凭证以前部分清（审批通过，还有退款的审批通过）的金额合计，加上本次清账的金额是否等于总的金额（如应收款金额）,如果相等才能清账
	  * 2.A．贷方（票方），每一行的 未清收款金额 = 清账金额
	*	B．借方（款方），每一行的 未抵消收款 = 本次已抵消收款
	*	C. 借方的清账总金额 = 本次已抵消收款 总金额
	*	D．如果：贷方的清账总金额 不相等于 本次已抵消收款 总金额 那么
	*	(贷方的清账总金额 = 本次已抵消收款 总金额 + 调整差额的合计 (有正有负))
	*	(结算科目金额总计+纯资金往来金额总计的和=调整差额的合计值)
	*	（1）。ABC 同时满足可以出清账凭证, 外币还要出汇损
	*	（2）。ABD同时满足可以出清账凭证，同时出调整差额的凭证，外币还要出汇损
	 * @param customerRefundment
	 * @return  true 为能全清，false 为部分清
	 */
	public IInfoVoucher isClearAccount(CustomerRefundment customerRefundment,BigDecimal marginAmount){
		InfoVoucherObject infoVoucherObject =new InfoVoucherObject();
		//默认为true
		infoVoucherObject.setRight(true);
//		默认能全清
		infoVoucherObject.setClear(true);
		
		Set<CustomerRefundItem> customerRefundItems = customerRefundment.getCustomerRefundItem();
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
		String bukrs = this.voucherService.getCompanyIDByDeptID(customerRefundment.getDept_id());
//		是否出汇损
		boolean ispl = false;
		String currency = customerRefundment.getRefundcurrency();
		
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
		for (CustomerRefundItem customerRefundItem : customerRefundItems){
			
			
			
			//退款金额本位币 REFUNDMENTVALUE
			BigDecimal staRefundmentAmount = customerRefundItem.getRefundmentvalue();
			// 退款金额 * 退款的汇率	=清账本位币
			BigDecimal clearBwb = customerRefundItem.getRefundmentamount().multiply(customerRefundItem.getExchangerate()).setScale(2, BigDecimal.ROUND_HALF_UP);
			
			CollectItem collectitem = this.collectItemService.get(customerRefundItem.getCollectitemid());
			KeyValue keyValue = new KeyValue();
			keyValue.setKey(customerRefundItem.getRefundmentitemid());
//			keyValue.setValue(billInCollect.getCustomertitleid());
			KeyValue keyValue2 = new KeyValue();
			keyValue2.setKey(customerRefundItem.getCollectitemid());
			// 收款单已审批完的 ，已清票款
			BigDecimal clearedAmount = this.collectItemService.getSumClearAmount(customerRefundItem.getCollectitemid(), BusinessState.ALL_COLLECT_PAIDUP);
//			所有的清账金额
			BigDecimal allClearAmount  = clearedAmount.add(customerRefundItem.getRefundmentamount());
			
//				等于（全清）
			if(collectitem.getAssignamount().compareTo(allClearAmount) ==0){	
				cpIdsByAllClear.add(keyValue);
				billIdsByAllClear.add(keyValue2);
			}
//				小于（数据错误）
			if(collectitem.getAssignamount().compareTo(allClearAmount) == -1){	
				infoVoucherObject.setRight(false);
				infoVoucherObject.setInfo(infoVoucherObject.CODE_8 + customerRefundItem.getCollectno());
				return infoVoucherObject;			
			}
//				大于(是部分清)
			if(collectitem.getAssignamount().compareTo(allClearAmount) == 1){	
				infoVoucherObject.setClear(false);
				clearF = true;
			}
			sumoffsetAmountBwb = sumoffsetAmountBwb.add(clearBwb);
			sumclearAmountBwb = sumclearAmountBwb.add(staRefundmentAmount);
			contractNoList.add(customerRefundItem.getContract_no());
			if(!StringUtils.isNullBlank(customerRefundItem.getProject_no()) && StringUtils.isNullBlank(projectNo)){
				projectNo = customerRefundItem.getProject_no();
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
		ids.add(customerRefundment.getRefundmentid());
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



	/*  设置参数
	 * @see com.infolion.xdss3.singleClear.service.ICustomerClearAccount#setParameter(com.infolion.xdss3.billclearcollect.domain.BillClearCollect)
	 */
	public ParameterVoucherObject setParameter(BillClearCollect billClearCollect) {
		ParameterVoucherObject parameterVoucher = new ParameterVoucherObject();
		parameterVoucher.setAccountdate(billClearCollect.getAccountdate());
		String bukrs =  this.voucherService.getCompanyIDByDeptID(billClearCollect.getDeptid());		
		parameterVoucher.setBukrs(bukrs);
		parameterVoucher.setBusinessid(billClearCollect.getBillclearid());
		parameterVoucher.setBusinesstype("08");
		parameterVoucher.setCheckCode("");
		parameterVoucher.setCheckCode2("");
		String taxCode = "";
		for (BillClearItem billClearItem : billClearCollect.getBillclearitem())
		{
			if(!"".equals(billClearItem.getCurrency().trim())){
				
				parameterVoucher.setCurrencytext(billClearItem.getCurrency());
				
			}
			if(!StringUtils.isNullBlank(taxCode)){
				break;
			}
			if(!StringUtils.isNullBlank(billClearItem.getContract_no())){
				taxCode = billClearItem.getContract_no();
			}else{
				taxCode = billClearItem.getProject_no();
			}
			
		}
		for (BillInCollect billincollect : billClearCollect.getBillincollect())
		{
			if(!StringUtils.isNullBlank(taxCode)){
				break;
			}
			if(!StringUtils.isNullBlank(billincollect.getContract_no())){
				taxCode = billincollect.getContract_no();
			}else{
				taxCode = billincollect.getProject_no();
			}			
		}
		String custom_htext = this.customerRefundItemJdbcDao.getCustomerDescByCustomerId(billClearCollect.getCustomer(),bukrs);
		parameterVoucher.setCustom_htext(custom_htext);
		parameterVoucher.setCustomer(billClearCollect.getCustomer());
		parameterVoucher.setDebitcredit("");
		parameterVoucher.setFundFlow(billClearCollect.getFundFlowBcc());
		//把部门ID转成业务范围
		String deptid = this.sysDeptJdbcDao.getDeptCodeById(billClearCollect.getDeptid());
		parameterVoucher.setGsber(deptid);
		parameterVoucher.setRownumber("");
		parameterVoucher.setSettleSubject(billClearCollect.getSettleSubjectBcc());
		parameterVoucher.setStrType("");
		parameterVoucher.setSubtractVlaue(new BigDecimal("0"));
		parameterVoucher.setSumAdjustamount(new BigDecimal("0"));
		parameterVoucher.setSupplier("");
		parameterVoucher.setSupplier_htext("");
//		String taxCode = billClearCollectService.getTaxCode(billClearCollect);
		parameterVoucher.setTaxCode(taxCode);
		parameterVoucher.setText(billClearCollect.getText());
//		parameterVoucher.setVoucher();
		parameterVoucher.setVoucherclass("1");
		parameterVoucher.setVoucherdate(billClearCollect.getVoucherdate());
		parameterVoucher.setVouchertype("SA");		
		return parameterVoucher;		
	}


	/*  设置参数
	 * @see com.infolion.xdss3.singleClear.service.ICustomerClearAccount#setParameter(com.infolion.xdss3.collect.domain.Collect)
	 */
	public ParameterVoucherObject setParameter(Collect collect) {
		ParameterVoucherObject parameterVoucher = new ParameterVoucherObject();
		parameterVoucher.setAccountdate(collect.getAccountdate());
		String bukrs =  this.voucherService.getCompanyIDByDeptID(collect.getDept_id());		
		parameterVoucher.setBukrs(bukrs);
		parameterVoucher.setBusinessid(collect.getCollectid());
		parameterVoucher.setBusinesstype("08");
		parameterVoucher.setCheckCode("");
		parameterVoucher.setCheckCode2("");
		parameterVoucher.setCurrencytext(collect.getBillcurrency());
		String custom_htext = this.customerRefundItemJdbcDao.getCustomerDescByCustomerId(collect.getCustomer(),bukrs);
		parameterVoucher.setCustom_htext(custom_htext);
		parameterVoucher.setCustomer(collect.getCustomer());
		parameterVoucher.setDebitcredit("");
		parameterVoucher.setFundFlow(collect.getFundFlow());
		//把部门ID转成业务范围
		String deptid = this.sysDeptJdbcDao.getDeptCodeById(collect.getDept_id());
		parameterVoucher.setGsber(deptid);
		parameterVoucher.setRownumber("");
		parameterVoucher.setSettleSubject(collect.getSettleSubject());
		parameterVoucher.setStrType("");
		parameterVoucher.setSubtractVlaue(new BigDecimal("0"));
		parameterVoucher.setSumAdjustamount(new BigDecimal("0"));
		parameterVoucher.setSupplier("");
		parameterVoucher.setSupplier_htext("");		
		for(CollectCbill cb:collect.getCollectcbill()){
			if(StringUtils.isNullBlank(cb.getContract_no())){
				parameterVoucher.setTaxCode(cb.getContract_no());
			}else{
				parameterVoucher.setTaxCode(cb.getProject_no());
			}
			break;
		}
		
		parameterVoucher.setText(collect.getText());
//		parameterVoucher.setVoucher();
		parameterVoucher.setVoucherclass("1");
		parameterVoucher.setVoucherdate(collect.getVoucherdate());
		parameterVoucher.setVouchertype("SA");		
		return parameterVoucher;	
	}


	/*  设置参数
	 * @see com.infolion.xdss3.singleClear.service.ICustomerClearAccount#setParameter(com.infolion.xdss3.customerDrawback.domain.CustomerRefundment)
	 */
	public ParameterVoucherObject setParameter(CustomerRefundment customerRefundment) {
		ParameterVoucherObject parameterVoucher = new ParameterVoucherObject();
		parameterVoucher.setAccountdate(customerRefundment.getAccountdate());
		String bukrs =  this.voucherService.getCompanyIDByDeptID(customerRefundment.getDept_id());		
		parameterVoucher.setBukrs(bukrs);
		parameterVoucher.setBusinessid(customerRefundment.getRefundmentid());
		parameterVoucher.setBusinesstype("08");
		parameterVoucher.setCheckCode("");
		parameterVoucher.setCheckCode2("");
		parameterVoucher.setCurrencytext(customerRefundment.getRefundcurrency());
		String custom_htext = this.customerRefundItemJdbcDao.getCustomerDescByCustomerId(customerRefundment.getCustomer(),bukrs);
		parameterVoucher.setCustom_htext(custom_htext);
		parameterVoucher.setCustomer(customerRefundment.getCustomer());
		parameterVoucher.setDebitcredit("");
		parameterVoucher.setFundFlow(customerRefundment.getFundFlow());
		//把部门ID转成业务范围
		String deptid = this.sysDeptJdbcDao.getDeptCodeById(customerRefundment.getDept_id());
		parameterVoucher.setGsber(deptid);
		parameterVoucher.setRownumber("");
		parameterVoucher.setSettleSubject(customerRefundment.getSettleSubject());
		parameterVoucher.setStrType("");
		parameterVoucher.setSubtractVlaue(new BigDecimal("0"));
		parameterVoucher.setSumAdjustamount(new BigDecimal("0"));
		parameterVoucher.setSupplier("");
		parameterVoucher.setSupplier_htext("");		
		for(CustomerRefundItem customerRefundItem:customerRefundment.getCustomerRefundItem()){
			if(StringUtils.isNullBlank(customerRefundItem.getContract_no())){
				parameterVoucher.setTaxCode(customerRefundItem.getContract_no());
			}else{
				parameterVoucher.setTaxCode(customerRefundItem.getProject_no());
			}
			break;
		}
		
		parameterVoucher.setText(customerRefundment.getText());
//		parameterVoucher.setVoucher();
		parameterVoucher.setVoucherclass("1");
		parameterVoucher.setVoucherdate(customerRefundment.getVoucherdate());
		parameterVoucher.setVouchertype("SA");		
		return parameterVoucher;	
	}


	/*  设置参数
	 * @see com.infolion.xdss3.singleClear.service.ICustomerClearAccount#setParameter(com.infolion.xdss3.singleClear.domain.CustomSingleClear)
	 */
	public ParameterVoucherObject setParameter(CustomSingleClear customSingleClear) {		
		
		ParameterVoucherObject parameterVoucher = new ParameterVoucherObject();
		parameterVoucher.setAccountdate(customSingleClear.getAccountdate());
		parameterVoucher.setBukrs(customSingleClear.getCompanyno());
		parameterVoucher.setBusinessid(customSingleClear.getCustomsclearid());
		parameterVoucher.setBusinesstype("08");
		parameterVoucher.setCheckCode("");
		parameterVoucher.setCheckCode2("");
		parameterVoucher.setCurrencytext(customSingleClear.getCurrencytext());
		String Custom_htext = customerJdbcDao.getCustomerName(customSingleClear.getCustom());
		parameterVoucher.setCustom_htext(Custom_htext);
		parameterVoucher.setCustomer(customSingleClear.getCustom());
		parameterVoucher.setDebitcredit("");
		parameterVoucher.setFundFlow(customSingleClear.getFundFlow());
		parameterVoucher.setGsber(customSingleClear.getDepid());
		parameterVoucher.setRownumber("");
		parameterVoucher.setSettleSubject(customSingleClear.getSettleSubject());
		parameterVoucher.setStrType("");
		parameterVoucher.setSubtractVlaue(new BigDecimal("0"));
		
		Set<UnClearCustomBill> unClearCustomBills = customSingleClear.getUnClearCustomBill();	
		String taxCode =  " ";
		BigDecimal sumAdjustamount= new BigDecimal("0");
		// 计算调整金之和
		for (UnClearCustomBill unClearCustomBill : unClearCustomBills){
			sumAdjustamount = sumAdjustamount.add(unClearCustomBill.getAdjustamount());
			if(StringUtils.isNullBlank(taxCode)){			
				if(!StringUtils.isNullBlank(unClearCustomBill.getContract_no())){
					taxCode = unClearCustomBill.getContract_no();
				}else{
					taxCode = unClearCustomBill.getProject_no();
				}
			}
		}
		Set<UnClearCollect> unClearCollects = customSingleClear.getUnClearCollect();
		for (UnClearCollect unClearCollect : unClearCollects){
			if(!StringUtils.isNullBlank(taxCode)){
				break;
			}
			if(!StringUtils.isNullBlank(unClearCollect.getContract_no())){
				taxCode = unClearCollect.getContract_no();
			}else{
				taxCode = unClearCollect.getProject_no();
			}
		}
//		String taxCode = customSingleClearService.getTaxCode(customSingleClear);
		parameterVoucher.setTaxCode(taxCode);
		
		parameterVoucher.setSumAdjustamount(sumAdjustamount);
		parameterVoucher.setSupplier("");
		parameterVoucher.setSupplier_htext("");		
		parameterVoucher.setText(customSingleClear.getText());
//		parameterVoucher.setVoucher();
		parameterVoucher.setVoucherclass("1");
		parameterVoucher.setVoucherdate(customSingleClear.getVoucherdate());
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
			CustomerTitle customerTitle = customerTitleJdbcDao.getCustomerTitle(companyCode, voucherNo, fiYear, rowNumber);
			if(null != customerTitle){
				this.unclearCustomerService.updateCustomerTitleIsCleared(customerTitle.getCustomertitleid(), Constants.cleared.sapIsCleared);				
			}
			String businessItemId = this.voucherItemJdbcDao.getBusinessId(voucherNo, companyCode, fiYear, rowNumber);
			// 收款信息
			CollectItem collectItem = this.collectItemService.get(businessItemId);
			if(null != collectItem){
				this.collectItemService.updateCollectItemIsCleared(collectItem.getCollectitemid(), Constants.cleared.sapIsCleared);
			}
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
	 * 更新部分清的isclear的状态
	 */
	public void updateIsClear(IInfoVoucher infoVoucher){
//		取出本次全清的数据
		for(IKeyValue keyValue:infoVoucher.getBillIdsByAllClear()){
			String billno = keyValue.getKey();
			String customertitleid = keyValue.getValue();
			if(StringUtils.isNullBlank(customertitleid)){
				CustomerTitle customerTitle =customerTitleJdbcDao.getByInvoice(billno);
				customertitleid =customerTitle.getCustomertitleid();
			}
			this.unclearCustomerService.updateCustomerTitleIsCleared(customertitleid, Constants.cleared.isCleared);
		}
		for(IKeyValue keyValue:infoVoucher.getCpIdsByAllClear()){
			String collectItemid = keyValue.getKey();
			String customertitleid = keyValue.getValue();
			if(!StringUtils.isNullBlank(customertitleid)){
				this.unclearCustomerService.updateCustomerTitleIsCleared(customertitleid, Constants.cleared.isCleared);
			}else{
				VoucherItem voucherItem=voucherItemJdbcDao.getVoucherItem(collectItemid, "1,4");
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
				
				CustomerTitle customerTitle = customerTitleJdbcDao.getCustomerTitle(companyCode, voucherNo, fiYear, rowNumber);
				if(null != customerTitle){
					this.unclearCustomerService.updateCustomerTitleIsCleared(customerTitle.getCustomertitleid(), Constants.cleared.isCleared);
				}
			}
			if(!StringUtils.isNullBlank(collectItemid)){				
				this.collectItemService.updateCollectItemIsCleared(collectItemid, Constants.cleared.isCleared);				
			}
		}
	}
	/**
	 * 更新部分清的isclear的状态,收款本身的行项目全清状态
	 */
//	public void updateIsClear(Collect collect){
//		for(CollectItem ci:collect.getCollectitem()){
//			if(ci.getSuretybond().compareTo(new BigDecimal("0")) ==0 && ci.getPrebillamount().compareTo(new BigDecimal("0")) == 0){
//				this.collectItemService.updateCollectItemIsCleared(ci.getCollectitemid(), Constants.cleared.isCleared);
//			}
//		}
//	}
//	/**
//	 * 更新部分清的isclear的状态，退款本身的行项目全清状态
//	 */
//	public void updateIsClear(CustomerRefundment customerRefundment){
//		for(CustomerRefundItem cri:customerRefundment.getCustomerRefundItem()){
//			if(cri.getPefundmentamount().compareTo(cri.getRefundmentamount()) == 0){
//				customerRefundItemService.updateRefundItemIsCleared(cri.getRefundmentitemid(), Constants.cleared.isCleared);
//				this.collectItemService.updateCollectItemIsCleared(cri.getCollectitemid(), Constants.cleared.isCleared);	
//			}
//		}
//	}
	/**
	 * 同步ycustomertitle时要判断收款是否被清账，更新收款行项目的isclear
	 */
	public void updateCollectItemIsClear(String companyCode, String voucherNo, String fiYear, String rowNumber){
		
	}
	
	
	/**
	 * 取得清账凭证行项目
	 * @return
	 */
	public Set<ClearVoucherItem> getClearVoucherItemByCustomer(List<IKeyValue> clearIdList){
		
		StringBuffer collectSb = new StringBuffer();	
		StringBuffer refundmentSb = new StringBuffer();	
		StringBuffer refundmentSb2 = new StringBuffer();	
		StringBuffer billSb = new StringBuffer();	
		StringBuffer singleSb = new StringBuffer();	

		String collectids ="";
		String refundmentids ="";
		String billids ="";
		String singleids ="";
		String ids ="";
		for(IKeyValue keyValue:clearIdList){
			String clid = keyValue.getKey();
			String type = keyValue.getValue();
			if(ClearConstant.COLLECT_TYPE.equals(type)){
				if(StringUtils.isNullBlank(collectids)){
					collectids=clid;
				}else{
					collectids= collectids + "','" + clid ;
				}
				
			}
			if(ClearConstant.REFUNDMENT_TYPE.equals(type)){
				if(StringUtils.isNullBlank(refundmentids)){
					refundmentids=clid;
				}else{
					refundmentids= refundmentids + "','" + clid ;
				}
			}
			if(ClearConstant.BILL_TYPE.equals(type)){
				if(StringUtils.isNullBlank(billids)){
					billids=clid;
				}else{
					billids= billids + "','" + clid ;
				}
			}
			if(ClearConstant.SINGLE_TYPE.equals(type)){
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
		collectSb.append("SELECT ci.collectitemid FROM ycollectitem ci WHERE ci.collectid IN ( '");
		refundmentSb.append("SELECT rei.refundmentitemid FROM yrefundmentitem rei  WHERE rei.refundmentid IN ( '");
		refundmentSb2.append("SELECT rei.collectitemid FROM yrefundmentitem rei  WHERE rei.refundmentid IN ( '");
		billSb.append("SELECT bic.collectitemid FROM ybillincollect bic WHERE bic.billclearid IN ( '");
		singleSb.append("SELECT uc.customertitleid FROM yunclearcollect uc WHERE uc.customsclearid IN ( '");
		collectSb.append(collectids + "')");
		refundmentSb.append(refundmentids + "')");
		refundmentSb2.append(refundmentids + "')");
		billSb.append(billids + "')");
		singleSb.append(singleids + "')");
//		为了去掉重复
		Set<ClearVoucherItem> viSet = new HashSet<ClearVoucherItem>();
//		如果没有id就不用找了，提高查询性能
		if(!StringUtils.isNullBlank(collectids)){
			List<ClearVoucherItem> cList = customerClearAccountJdbcDao.getVoucherItem(collectSb.toString(),true);
			viSet.addAll(cList);
		}
		if(!StringUtils.isNullBlank(refundmentids)){
			List<ClearVoucherItem> rList = customerClearAccountJdbcDao.getVoucherItem(refundmentSb.toString(),false);
			viSet.addAll(rList);
			List<ClearVoucherItem> rList2 = customerClearAccountJdbcDao.getVoucherItem(refundmentSb2.toString(),false);
			viSet.addAll(rList2);
		}
		if(!StringUtils.isNullBlank(billids)){
			List<ClearVoucherItem> bList = customerClearAccountJdbcDao.getVoucherItem(billSb.toString(),true);
			viSet.addAll(bList);
		}
		if(!StringUtils.isNullBlank(singleids)){
			List<ClearVoucherItem> sList = customerClearAccountJdbcDao.getVoucherItemByTitle(singleSb.toString());
//			如果是单清账，收款行项目要做处理，重新取。
			List<Map> lim=customerClearAccountJdbcDao.getunclearcollect(singleids);
			for(ClearVoucherItem cvi:sList){
				for(Map map:lim){
//					设置收款行项目
					if(map.get("customertitleid").toString().equals(cvi.getBusinessitemid())){
						cvi.setBusinessitemid(map.get("collectitemid").toString());
					}
				}
			}
			viSet.addAll(sList);
		}

//		清空,要通过使用sbi.setLength(0);来清空StringBuffer对象中的内容效率最高。
		collectSb.setLength(0);	
		refundmentSb.setLength(0);	
		billSb.setLength(0);	
		singleSb.setLength(0);	
//		查找票方
		collectSb.append("SELECT distinct ct.customertitleid FROM ycollectcbill cb,ycustomertitle ct WHERE cb.billno=ct.invoice AND  cb.collectid IN  ( '");		
		billSb.append("SELECT bci.titleid FROM ybillclearitem bci WHERE bci.billclearid IN ( '");
		singleSb.append("SELECT ub.customertitleid FROM yunclearcustbill ub WHERE ub.customsclearid IN ( '");
		collectSb.append(collectids + "')");		
		billSb.append(billids + "')");
		singleSb.append(singleids + "')");
		
		if(!StringUtils.isNullBlank(collectids)){
			List<ClearVoucherItem> cList = customerClearAccountJdbcDao.getVoucherItemByTitle(collectSb.toString());
			viSet.addAll(cList);
		}
		if(!StringUtils.isNullBlank(billids)){
			List<ClearVoucherItem> bList = customerClearAccountJdbcDao.getVoucherItemByTitle(billSb.toString());
			viSet.addAll(bList);
		}
		if(!StringUtils.isNullBlank(singleids)){
			List<ClearVoucherItem> sList = customerClearAccountJdbcDao.getVoucherItemByTitle(singleSb.toString());
//			如果是单清账，有付款行项目要做处理，重新取。
			List<Map> lim=customerClearAccountJdbcDao.getunclearPaymentByCustomer(singleids);
			for(ClearVoucherItem cvi:sList){
				for(Map map:lim){
//					设置收款行项目
					if(map.get("customertitleid").toString().equals(cvi.getBusinessitemid())){
						cvi.setBusinessitemid(map.get("paymentitemid").toString());
					}
				}
			}
			viSet.addAll(sList);
		}
//		查找外围生成的汇损和调整金(有可能包含本次的已经生成的调整金,通过businessid来判断)
		List<ClearVoucherItem> pList =customerClearAccountJdbcDao.getProfitAndLossByPart(ids);
		viSet.addAll(pList);		
		return viSet;
	}
	/**
	 * 
	 * 取得客户部分清的单据ID信息
	 * @return
	 */	 
	public List<IKeyValue> getPartClearByCustomer(IParameterVoucher infoVoucherObject,IInfoVoucher infoVoucher,String clearid,String type2){
		
//		保存清账的ID号
		List<IKeyValue> clearIdList = new ArrayList<IKeyValue>();
//		保存已经找过的款id
//		List<String> cpIdList = new ArrayList<String>();
//		List<String> cpIdCollectitemList = new ArrayList<String>();
//		保存已经找过的票id
//		List<String> billIdList = new ArrayList<String>();
//		List<String> billnoList = new ArrayList<String>();
		KeyValue keyValue2 = new KeyValue(); 
		keyValue2.setKey(clearid);
		keyValue2.setValue(type2);
		clearIdList.add(keyValue2);
		InfoObject infoObject = new InfoObject();
		infoObject.setRight(true);
//		本次部分清的款ID
//		List<IKeyValue> cpidsByPartList = infoVoucher.getCpIdsByPart();		
//		循环找到所有客户单清款方的所有部分清的数据
//		for(IKeyValue keyValue : cpidsByPartList){
////			value保存titleid,key保存collectitemid
//			String collectitemid = keyValue.getKey();
//			String titleid = keyValue.getValue();			
//			cpIdList.add(titleid);
//			cpIdCollectitemList.add(collectitemid);
//			getCustomerClearPartByCp(titleid,collectitemid,clearid,clearIdList,cpIdList,cpIdCollectitemList,infoObject, billIdList,billnoList);
//		}		
//		List<IKeyValue> billidsByPartList = infoVoucher.getBillIdsByPart();
////		循环找到所有客户单清票方的所有部分清的数据
//		for(IKeyValue keyValue : billidsByPartList){
////			value保存titleid,key保存billno
//			String titleid = keyValue.getValue();	
//			String billno = keyValue.getKey();			
//			billIdList.add(titleid);
//			getCustomerClearPartByBill(titleid,billno,clearid,clearIdList,billIdList,billnoList,infoObject, cpIdList, cpIdCollectitemList);
//		}
		List<IKeyValue> partIdsList = new ArrayList<IKeyValue>();
		partIdsList.addAll(infoVoucher.getCpIdsByPart());
		partIdsList.addAll(infoVoucher.getBillIdsByPart());
//		如果是退款，本次全清的款，也要去找以前有被清的数据
		if(ClearConstant.REFUNDMENT_TYPE.equals(type2)){
			partIdsList.addAll(infoVoucher.getBillIds());
		}
//		保存已经找过的票或款id
		List<String> titleIdList = new ArrayList<String>();
		List<String> collectitemOrBillnoList = new ArrayList<String>();
		for(IKeyValue keyValue : partIdsList){
//		value保存titleid,key保存billno或collectitemid
			String titleid = keyValue.getValue();	
			String collectitemidOrBillno = keyValue.getKey();			
			titleIdList.add(titleid);
			collectitemOrBillnoList.add(collectitemidOrBillno);
			getCustomerClearPart(titleid,collectitemidOrBillno,clearid,clearIdList,titleIdList,collectitemOrBillnoList,infoObject);

		}
//		判断是否正确
		if(infoObject.isRight()){
			return clearIdList;
		}else{
			return null;
		}		
	}
	
	
	/***
	 * 循环找到所有客户单清款方的所有部分清的数据
	 * @param titleid
	 * @param clearid
	 * @param clearIdList
	 */
	/**
	public void getCustomerClearPartByCp(String titleid,String collectitemid,String clearid,List<IKeyValue> clearIdList,List<String> cpIdList,List<String> cpIdCollectitemList,InfoObject infoObject,List<String> billIdList,List<String> billnoList){
		
//		取得除本次以外的款方有部分清的，客户清账ID 
		List<IKeyValue> clearList = customerClearAccountJdbcDao.getPartCpIdsByCustomerClear(titleid,collectitemid, clearid);
		for(IKeyValue keyValue2 : clearList){
			String clid = keyValue2.getKey();			
			String type2= keyValue2.getValue();
//			如果有在clearIdList说明有跟以前的单据重复，这是不允许的
			if(this.isSame(clid, clearIdList)){
				infoObject.setRight(false);
				infoObject.setInfo(infoObject.CODE_9 + clid);
				infoObject.setCode(clid);
				log.debug(infoObject.CODE_9 + clid);
				return;
			}else{
				KeyValue keyValue3 = new KeyValue(); 
				keyValue3.setKey(clid);
				keyValue3.setValue(type2);
				clearIdList.add(keyValue3);
			}			
//			取得本次有部分清的，款行项目titleID, IKeyValue key 为titleid,value 为collectitemID,value2 为客户单清账ID
			List<IKeyValue> kvList = customerClearAccountJdbcDao.getPartCpIdsByCustomerClear(clid);
			for(IKeyValue keyValue :kvList){
				String titleid2 = keyValue.getKey();
				String collectitemid2 = keyValue.getValue();				
//				如果有在cpIdList说明有跟以前的有找过了，不用再找
				if(cpIdList.contains(titleid2) && cpIdCollectitemList.contains(collectitemid2)){
					continue;
				}else{
					cpIdList.add(titleid2);
					cpIdCollectitemList.add(collectitemid2);
				}
				getCustomerClearPartByCp(titleid2,collectitemid2,keyValue.getValue2(),clearIdList,cpIdList,cpIdCollectitemList,infoObject,billIdList,billnoList);
			}
//			取票有部分清的数据
//			取得本次有部分清的，款行项目titleID, IKeyValue key 为titleid,value 为客户单清账ID
			List<IKeyValue> kvList2 = customerClearAccountJdbcDao.getPartBillIdsByCustomerClear(clid);
			for(IKeyValue keyValue :kvList2){
				String titleid2 = keyValue.getKey();
				String billno2 = keyValue.getValue();
//				如果有在billIdList说明有跟以前的有找过了，不用再找
				if(billIdList.contains(titleid2) && billnoList.contains(billno2)){
					continue;
				}else{
					billIdList.add(titleid2);
					billnoList.add(billno2);
				}
				getCustomerClearPartByBill(titleid2,billno2,keyValue.getValue2(),clearIdList,billIdList,billnoList,infoObject, cpIdList, cpIdCollectitemList);
			}
		}

		
	}
	**/
	/***
	 * 循环找到所有客户单清票方的所有部分清的数据
	 * @param titleid
	 * @param clearid
	 * @param clearIdList
	 */
	/**
	public void getCustomerClearPartByBill(String titleid,String billno,String clearid,List<IKeyValue> clearIdList,List<String> billIdList,List<String> billnoList,InfoObject infoObject,List<String> cpIdList,List<String> cpIdCollectitemList){
		
//		取得除本次以外的款方有部分清的，客户单清账ID 
		List<IKeyValue> clearList = customerClearAccountJdbcDao.getPartBillIdsByCustomerClear(titleid,billno, clearid);
		for(IKeyValue keyValue2 : clearList){
			String clid = keyValue2.getKey();			
			String type2= keyValue2.getValue();
//			如果有在clearIdList说明有跟以前的单据重复，这是不允许的
			if(this.isSame(clid, clearIdList)){
				infoObject.setRight(false);
				infoObject.setInfo(infoObject.CODE_9 + clid);
				infoObject.setCode(clid);
				log.debug(infoObject.CODE_9 + clid);
				return;
			}else{
				KeyValue keyValue3 = new KeyValue(); 
				keyValue3.setKey(clid);
				keyValue3.setValue(type2);
				clearIdList.add(keyValue3);
			}
			
//			取得本次有部分清的，款行项目titleID, IKeyValue key 为titleid,value 为客户单清账ID
			List<IKeyValue> kvList = customerClearAccountJdbcDao.getPartBillIdsByCustomerClear(clid);
			for(IKeyValue keyValue :kvList){
				String titleid2 = keyValue.getKey();
				String billno2 = keyValue.getValue();
//				如果有在billIdList说明有跟以前的有找过了，不用再找
				if(billIdList.contains(titleid2) && billnoList.contains(billno2)){
					continue;
				}else{
					billIdList.add(titleid2);
					billnoList.add(billno2);
				}
				getCustomerClearPartByBill(titleid2,billno2,keyValue.getValue2(),clearIdList,billIdList,billnoList,infoObject,cpIdList,cpIdCollectitemList);
			}
//			取得本次有部分清的，款行项目titleID, IKeyValue key 为titleid,value 为collectitemID,value2 为客户单清账ID
			List<IKeyValue> kvList2 = customerClearAccountJdbcDao.getPartCpIdsByCustomerClear(clid);
			for(IKeyValue keyValue :kvList2){
				String titleid2 = keyValue.getKey();
				String collectitemid2 = keyValue.getValue();				
//				如果有在cpIdList说明有跟以前的有找过了，不用再找
				if(cpIdList.contains(titleid2) && cpIdCollectitemList.contains(collectitemid2)){
					continue;
				}else{
					cpIdList.add(titleid2);
					cpIdCollectitemList.add(collectitemid2);
				}
				getCustomerClearPartByCp(titleid2,collectitemid2,keyValue.getValue2(),clearIdList,cpIdList,cpIdCollectitemList,infoObject,billIdList,billnoList);
			}
			
		}
	}
	**/
	/***
	 * 循环找到所有客户有部分清的数据
	 * @param titleid
	 * @param clearid
	 * @param clearIdList
	 */
	public void getCustomerClearPart(String titleid,String collectitemidOrBillno,String clearid,List<IKeyValue> clearIdList,List<String> titleIdList,List<String> collectitemOrBillnoList,InfoObject infoObject){
//		判断是否正确
		if(!infoObject.isRight()){
			return;
		}
//		取得除本次以外的款方有部分清的，客户清账ID 
		List<IKeyValue> clearList = customerClearAccountJdbcDao.getPartIdsByCustomerClear(titleid, clearid,collectitemidOrBillno);
		for(IKeyValue keyValue2 : clearList){
			String clid = keyValue2.getKey();			
			String type2= keyValue2.getValue();
//			如果有在clearIdList说明有跟以前的单据重复，这是不允许的
			if(this.isSame(clid, clearIdList)){
//				如果是退款有多个行项目，可能出现重复
				if(ClearConstant.REFUNDMENT_TYPE.equals(type2))continue;
				infoObject.setRight(false);
				infoObject.setInfo(infoObject.CODE_9 + clid);
				infoObject.setCode(clid);
				log.debug(infoObject.CODE_9  + "clearid=" + clid + "titleid=" + titleid + "collectitemidOrBillno=" + collectitemidOrBillno);
				log.error(infoObject.CODE_9 + "clearid=" + clid + "titleid=" + titleid + "collectitemidOrBillno=" + collectitemidOrBillno);
				return;
			}else{
				KeyValue keyValue3 = new KeyValue(); 
				keyValue3.setKey(clid);
				keyValue3.setValue(type2);
				clearIdList.add(keyValue3);
			}			
//			取得本次有部分清的，款行项目titleID, IKeyValue key 为titleid,value 为collectitemID,value2 为客户单清账ID
			List<IKeyValue> kvList = customerClearAccountJdbcDao.getPartIdsByCustomerClear(clid);
			for(IKeyValue keyValue :kvList){
				String titleid2 = keyValue.getKey();
				String collectitemidOrBillno2 = keyValue.getValue();		
//				如果是退款有清过账的收款不要再找
				if(ClearConstant.REFUNDMENT_TYPE.equals(type2) && collectitemOrBillnoList.contains(collectitemidOrBillno2))continue;
//				如果有在titleIdList.CollectitemOrBillnoList说明有跟以前的有找过了，不用再找
				if(titleIdList.contains(titleid2) && collectitemOrBillnoList.contains(collectitemidOrBillno2)){
					continue;
				}else{
					titleIdList.add(titleid2);
					collectitemOrBillnoList.add(collectitemidOrBillno2);					
				}
				getCustomerClearPart(titleid2,collectitemidOrBillno2,keyValue.getValue2(),clearIdList,titleIdList,collectitemOrBillnoList,infoObject);

			}
		}

		
	}
	
	
	/**
	 * 取得清账凭证行项目
	 * @param clearIdList 所有的清账单据ID，key为ID，value为类型,参照ClearConstant
	 * @param voucher
	 * @param voucherid 结算科目的凭证ID
	 * @return
	 */
	public Set<VoucherItem> getClearVoucherItemByCustomer2(List<IKeyValue> clearIdList,Voucher voucher,String voucherid){
		Set<ClearVoucherItem> cviSet = this.getClearVoucherItemByCustomer(clearIdList);
		return this.getClearVoucherItems(cviSet,voucher,voucherid);		
	}
	
	
	/**
	 * 保存客户清账凭证
	 * @param parameterVoucher 参数对象
	 * @param infoVoucher  返回的信息对象
	 * @param clearid 当前的清账单据ID
	 * @param type2 类型，参照ClearConstant
	 * @return
	 */
	public Voucher saveClearVoucherByCustomer(ParameterVoucherObject parameterVoucher,IInfoVoucher infoVoucher,String clearid,String type2,boolean isp){
//		所有的清账voucherclass都是9
		parameterVoucher.setVoucherclass("9");
		Voucher voucher = this.getClearVoucher(parameterVoucher);
		List<IKeyValue>  clearIdList = this.getPartClearByCustomer(parameterVoucher, infoVoucher, clearid, type2);
		if(null == clearIdList)return null;
		Set<VoucherItem> viSet =this.getClearVoucherItemByCustomer2(clearIdList,voucher,parameterVoucher.getVoucherid());
//		取得没有凭证号的本次币
		BigDecimal bwb = new BigDecimal("0");
//		如果是收款，因为没有生成凭证,加入收款凭证行项目，
		if(ClearConstant.COLLECT_TYPE.equals(type2)){
			
			List<ClearVoucherItem> viList = this.customerClearAccountJdbcDao.getVoucherItemByCollectid(clearid, "1,2,4");
			for(ClearVoucherItem clearVoucherItem:viList){
				VoucherItem voucherItem = getClearVoucherItem(clearVoucherItem);
//				为空说明没有生成凭证，加进去，有上面会取到
				if(StringUtils.isNullBlank(voucherItem.getVoucherno())){
					voucherItem.setBusvoucherid(clearVoucherItem.getVoucherid());
					voucherItem.setVoucher(voucher);
					viSet.add(voucherItem);		
					bwb = bwb.add(clearVoucherItem.getBwbje());
				}	
			}
		}
//		如果是退款，因为没有生成凭证,加入收款凭证行项目，
		if(ClearConstant.REFUNDMENT_TYPE.equals(type2)){
			
			List<ClearVoucherItem> viList = this.customerClearAccountJdbcDao.getVoucherItemByRefmentid(clearid, "1,2,4");
			for(ClearVoucherItem clearVoucherItem:viList){
				VoucherItem voucherItem = getClearVoucherItem(clearVoucherItem);
//				为空说明没有生成凭证，加进去，有上面会取到
				if(StringUtils.isNullBlank(voucherItem.getVoucherno())){
					voucherItem.setBusvoucherid(clearVoucherItem.getVoucherid());
					voucherItem.setVoucher(voucher);
					viSet.add(voucherItem);		
					bwb = bwb.subtract(clearVoucherItem.getBwbje());
				}	
			}
			
			viList = this.customerClearAccountJdbcDao.getCollectVoucherItemByRefmentid(clearid, "1,2,4");
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
					CustomerTitle customerTitle = this.customerTitleJdbcDao.getCustomerTitle(voucher.getCompanycode(), voucherItem.getVoucherno(), voucherItem.getFiyear(), voucherItem.getRownumber());
					if("S".equals(customerTitle.getShkzg())){
						subtractVlaue = subtractVlaue.subtract(customerTitle.getBwbje());
					}else{
						subtractVlaue =  subtractVlaue.add(customerTitle.getBwbje());
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
