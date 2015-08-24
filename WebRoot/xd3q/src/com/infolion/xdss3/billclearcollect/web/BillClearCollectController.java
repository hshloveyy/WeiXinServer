/*
 * @(#)BillClearCollectController.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年06月13日 09点26分47秒
 *  描　述：创建
 */
package com.infolion.xdss3.billclearcollect.web;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

import com.infolion.xdss3.BusinessState;
import com.infolion.xdss3.billClear.domain.BillClearItemPay;
import com.infolion.xdss3.billClear.domain.BillClearPayment;
import com.infolion.xdss3.billClear.domain.BillInPayment;
import com.infolion.xdss3.billclearcollect.domain.BillClearCollect;
import com.infolion.xdss3.billclearcollect.service.BillClearCollectService;
import com.infolion.xdss3.billclearcollectGen.web.BillClearCollectControllerGen;
import com.infolion.xdss3.billclearitem.domain.BillClearItem;
import com.infolion.xdss3.billclearitem.service.BillClearItemService;
import com.infolion.xdss3.billincollect.domain.BillInCollect;
import com.infolion.xdss3.billincollect.service.BillInCollectService;
import com.infolion.xdss3.collectitem.domain.CollectItem;
import com.infolion.xdss3.collectitem.service.CollectItemService;
import com.infolion.xdss3.financeSquare.domain.FundFlow;
import com.infolion.xdss3.financeSquare.domain.SettleSubject;
import com.infolion.xdss3.fundflowBcc.domain.FundFlowBcc;
import com.infolion.xdss3.masterData.dao.CustomerTitleJdbcDao;
import com.infolion.xdss3.masterData.domain.CustomerTitle;
import com.infolion.xdss3.masterData.service.UnclearCustomerService;
import com.infolion.xdss3.payment.service.PaymentItemService;
import com.infolion.xdss3.reassign.ReassignConstant;
import com.infolion.xdss3.reassign.dao.ReassignJdbcDao;
import com.infolion.xdss3.reassign.domain.Reassign;
import com.infolion.xdss3.reassign.service.ReassignService;
import com.infolion.xdss3.settlesubjectbcc.domain.SettleSubjectBcc;
import com.infolion.xdss3.singleClear.domain.ClearConstant;
import com.infolion.xdss3.singleClear.domain.CustomSingleClear;
import com.infolion.xdss3.singleClear.domain.IInfo;
import com.infolion.xdss3.singleClear.domain.IInfoVoucher;
import com.infolion.xdss3.singleClear.domain.ParameterVoucherObject;
import com.infolion.xdss3.singleClear.domain.UnClearCollect;
import com.infolion.xdss3.singleClear.domain.UnClearCustomBill;
import com.infolion.xdss3.singleClear.service.CustomerClearAccountService;
import com.infolion.xdss3.voucher.domain.Voucher;
import com.infolion.xdss3.voucher.service.VoucherService;
import com.infolion.xdss3.voucheritem.dao.VoucherItemJdbcDao;
import com.infolion.xdss3.voucheritem.domain.VoucherItem;
import com.infolion.platform.bo.domain.BusinessObject;
import com.infolion.platform.bo.service.BusinessObjectService;
import com.infolion.platform.calendar.domain.CalActivity;
import com.infolion.platform.calendar.service.CalActivityService;
import com.infolion.platform.console.org.dao.SysDeptJdbcDao;
import com.infolion.platform.console.sys.context.UserContextHolder;
import com.infolion.platform.dpframework.core.BusinessException;
import com.infolion.platform.dpframework.core.annotation.BDPController;
import com.infolion.platform.dpframework.core.cache.SysCachePoolUtils;
import com.infolion.platform.util.MultyGridData;
import com.infolion.platform.workflow.engine.WorkflowService;
import com.infolion.platform.dpframework.util.CodeGenerator;
import com.infolion.platform.dpframework.util.EasyApplicationContextUtils;
import com.infolion.platform.dpframework.util.ExBeanUtils;
import com.infolion.platform.dpframework.util.StringUtils;

/**
 * <pre>
 * 票清预收款(BillClearCollect)控制器类
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
public class BillClearCollectController extends BillClearCollectControllerGen {
	@Autowired
	protected UnclearCustomerService unclearCustomerService;

	public void setUnclearCustomerService(
			UnclearCustomerService unclearCustomerService) {
		this.unclearCustomerService = unclearCustomerService;
	}

	@Autowired
	protected BillClearItemService billClearItemService;

	public void setBillClearItemService(
			BillClearItemService billClearItemService) {
		this.billClearItemService = billClearItemService;
	}

	@Autowired
	protected CollectItemService collectItemService;

	public void setCollectItemService(CollectItemService collectItemService) {
		this.collectItemService = collectItemService;
	}

	@Autowired
	protected BillInCollectService billInCollectService;

	public void setBillInCollectService(
			BillInCollectService billInCollectService) {
		this.billInCollectService = billInCollectService;
	}

	@Autowired
	protected VoucherService voucherService;

	public void setVoucherService(VoucherService voucherService) {
		this.voucherService = voucherService;
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
	private ReassignService reassignService;

	/**
	 * @param reassignService
	 *            the reassignService to set
	 */
	public void setReassignService(ReassignService reassignService) {
		this.reassignService = reassignService;
	}

	@Autowired
	protected BillClearCollectService billClearCollectService;

	public void setBillClearCollectService(
			BillClearCollectService billClearCollectService) {
		this.billClearCollectService = billClearCollectService;
	}

	@Autowired
	private PaymentItemService paymentItemService;
	
	
	
	public void setPaymentItemService(PaymentItemService paymentItemService) {
		this.paymentItemService = paymentItemService;
	}

	@Autowired
	private VoucherItemJdbcDao voucherItemJdbcDao;

	
	public void setVoucherItemJdbcDao(VoucherItemJdbcDao voucherItemJdbcDao) {
		this.voucherItemJdbcDao = voucherItemJdbcDao;
	}
	
	@Autowired
	private CustomerTitleJdbcDao customerTitleJdbcDao;

	public void setCustomerTitleJdbcDao(CustomerTitleJdbcDao customerTitleJdbcDao)
	{
		this.customerTitleJdbcDao = customerTitleJdbcDao;
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
	 * 通过传入发票检验ID来初始化数据
	 * 
	 * @param request
	 * @param response
	 */
	public void getCollectItemData(HttpServletRequest request,
			HttpServletResponse response) {
		String collectitemids = request.getParameter("collectitemids");
		String collectids = request.getParameter("collectids");
		String billclearid = request.getParameter("billclearid");
		// 取得未清完的预收票款单
		List<CollectItem> collectItemList = this.collectItemService
				.getUnclearPreBillCollectList(collectitemids);
		
		List<BillInCollect> billInCollectList = new ArrayList<BillInCollect>();

		for (int i = 0; i < collectItemList.size(); i++) {
			CollectItem collectItem = (CollectItem) collectItemList.get(i);

			// 预收票款
//			BigDecimal prebillamount = collectItem.getPrebillamount();
			// 金额
			BigDecimal goodsAmount = collectItem.getAssignamount();
			
			// 收款单已审批完的(已清)
//			BigDecimal clearedcollectamount = this.billInCollectService
//					.getSumClearedAmount(collectItem.getCollectitemid(), "");
			BigDecimal clearedcollectamount = this.collectItemService.getSumClearAmount(collectItem.getCollectitemid().trim(), BusinessState.ALL_COLLECT_PAIDUP);
			// 收款单在途的(在批)
//			BigDecimal onroadcollectamount = this.billInCollectService
//					.getSumOnroadAmount(collectItem.getCollectitemid(), "");
			BigDecimal onroadcollectamount = this.collectItemService.getSumClearAmount(collectItem.getCollectitemid().trim(), BusinessState.ALL_COLLECT_ONROAD, billclearid);
			String voucherNo =  this.billInCollectService.getVoucherIdByCollectId(collectItem.getCollect().getCollectid());
			//没有凭证号的不显示
			if(null ==voucherNo || "".equals(voucherNo.trim()))continue;	
			VoucherItem voucherItem2 = this.voucherItemJdbcDao.getVoucherItem(collectItem.getCollectitemid(), "1,4");				
			if(null !=voucherItem2){
				String rowNumber = voucherItem2.getRownumber();
				if(rowNumber.length()==2)rowNumber="0" + rowNumber;			
				if(rowNumber.length()==1)rowNumber="00" + rowNumber;
				CustomerTitle customerTitle = customerTitleJdbcDao.getCustomerTitle(voucherItem2.getVoucher().getCompanycode(), voucherItem2.getVoucher().getVoucherno(), voucherItem2.getVoucher().getFiyear(), rowNumber);
				if("1".equals(customerTitle.getIscleared()) || "2".equals(customerTitle.getIscleared())){
					continue;	
				}
			}
			BillInCollect billInCollect = new BillInCollect();
			billInCollect.setAccountdate(collectItem.getCollect().getAccountdate());
			
			billInCollect.setCollectitemid(collectItem.getCollectitemid());
			
			billInCollect.setVoucherno(voucherNo);
			billInCollect.setProject_no(collectItem.getProject_no());
			billInCollect.setContract_no(collectItem.getContract_no());
			billInCollect.setCollectno(collectItem.getCollect().getCollectno());
			billInCollect.setSendgoodsdate(collectItem.getCollect().getSendgoodsdate());
			billInCollect.setCollectamount(goodsAmount);
			billInCollect.setCurrency(collectItem.getCollect().getBillcurrency());
//			billInCollect.setBwbje(collectItem.getAssignamount2());
			billInCollect.setBktxt(collectItem.getCollect().getText());
			billInCollect.setOldcontract_no(this.collectItemService.getOldContractNo(collectItem.getContract_no().trim()));
			

			billInCollect.setOnroadamount(onroadcollectamount);
			billInCollect.setOffsetamount(clearedcollectamount);
			//billInCollect.setVoucherno(vouchernos.get(i));
			//在批的退款保证金
			BigDecimal onroadSuretybond = this.collectItemService.getSumClearAmountByRefundment(collectItem.getCollectitemid().trim(), BusinessState.ALL_COLLECT_ONROAD);
			//实际保证金-在批的退款保证金
			billInCollect.setSuretybond(collectItem.getActsuretybond().subtract(onroadSuretybond));
			
			billInCollect.setBwbje(collectItem.getAssignamount2());
			//汇率
			BigDecimal rat = billInCollect.getBwbje().divide(billInCollect.getCollectamount(),5,BigDecimal.ROUND_HALF_EVEN);
			billInCollect.setExchangerate(rat);
			billInCollect.setUnoffsetamount(goodsAmount.subtract(clearedcollectamount).subtract(onroadcollectamount).subtract(billInCollect.getSuretybond()));
			//未清金额为0不显示
			if(billInCollect.getUnoffsetamount().compareTo(BigDecimal.valueOf(0)) <= 0){
				continue;
			}
			//判断第一次清帐，已抵消收款为0，且在批抵消收款为0，且有保证金的情况。
			if(billInCollect.getOnroadamount().compareTo(new BigDecimal(0))==0 && billInCollect.getOffsetamount().compareTo(new BigDecimal(0))==0 && billInCollect.getSuretybond().compareTo(new BigDecimal(0))!=0){
				//未收款本位币，如果第一次清帐有保证金要减去保证金，如果是外币还在算汇率。
				if("CNY".equals(billInCollect.getCurrency())){
					billInCollect.setUnbwbje(collectItem.getAssignamount2().subtract(billInCollect.getSuretybond()));
					
				}else{
					
					//保证金的本位币
					BigDecimal suretybond2 = billInCollect.getSuretybond().multiply(rat);
					//未收款本位币
					billInCollect.setUnbwbje(billInCollect.getUnoffsetamount().multiply(rat).subtract(suretybond2).setScale(2, BigDecimal.ROUND_HALF_UP));
				}
			}else{
				billInCollect.setUnbwbje(billInCollect.getUnoffsetamount().multiply(rat).setScale(2, BigDecimal.ROUND_HALF_UP));
			}
			
			billInCollectList.add(billInCollect);
		}

		MultyGridData gridJsonData = new MultyGridData();
		gridJsonData.setData(billInCollectList.toArray());
		JSONObject jsonList = JSONObject.fromObject(gridJsonData);
		try {
			response.setContentType("text/html;charset=UTF-8");
			response.getWriter().print(jsonList);
			System.out.println(jsonList.toString());
		} catch (IOException e) {
			logger.error("输出json失败," + e.getMessage(), e.getCause());
		}
	}

	public void getBillClearItemData(HttpServletRequest request,
			HttpServletResponse response) {
		
		String customertitleids = request.getParameter("customertitleids");
		String billclearid = request.getParameter("billclearid");
		
		// 根据客户，取得客户下未清发票数据集合
		List<CustomerTitle> customerTitleList = new ArrayList<CustomerTitle>();
		if (!("").equals(customertitleids) && customertitleids != null)
		{
			customerTitleList = this.unclearCustomerService.getUnclearCustomerInvoiceList(customertitleids);
		}
		List<BillClearItem> billclearitemList = new ArrayList<BillClearItem>();
		// 遍历所有客户下未清发票数据集合
		for (Iterator<CustomerTitle> it = customerTitleList.iterator(); it.hasNext();)
		{
			CustomerTitle customerTitle = (CustomerTitle) it.next();
			
			BillClearItem billclearitem = new BillClearItem();			
			billclearitem.setInvoice(customerTitle.getInvoice());
			billclearitem.setCurrency(customerTitle.getWaers());
			billclearitem.setVoucherno(customerTitle.getBelnr());
			billclearitem.setProject_no(customerTitle.getBname());
			billclearitem.setContract_no(customerTitle.getIhrez());
			billclearitem.setOld_contract_no(this.collectItemService.getOldContractNo(customerTitle.getIhrez().trim())); // 纸质合同号码
			
			billclearitem.setSap_order_no(customerTitle.getVgbel()); // sap订单号,销售凭证号
			billclearitem.setBktxt(customerTitle.getBktxt());
			
			billclearitem.setAccountdate(customerTitle.getBudat());
			
			billclearitem.setTitleid(customerTitle.getCustomertitleid());			
			billclearitem.setBwbje(customerTitle.getBwbje());
			// 开票发票总金额,应付款.
			BigDecimal billamount = customerTitle.getDmbtr();
			billclearitem.setReceivableamount(billamount);
//			billclearitem.setReceivabledate("");
			// 开票发票已经审批完的，发票已收款
			BigDecimal receivedamount = this.unclearCustomerService.getSumClearAmount(customerTitle.getInvoice().trim(), BusinessState.ALL_COLLECT_PAIDUP);
			billclearitem.setReceivedamount(receivedamount);
			
			// 开票发票在途的，发票在途金额,去掉本次的
			BigDecimal onroadamount = this.unclearCustomerService.getSumClearAmount(customerTitle.getInvoice().trim(), BusinessState.ALL_COLLECT_ONROAD, billclearid);
			billclearitem.setOnroadamount(onroadamount);
			
			// 未收款=开票发票金额-发票已收款-在批
			billclearitem.setUnreceivedamount(billamount.subtract(receivedamount).subtract(onroadamount));
			//未收款为0不显示
			if(billclearitem.getUnreceivedamount().compareTo(BigDecimal.valueOf(0)) <= 0){
				continue;
			}
			billclearitem.setClearbillamount(BigDecimal.valueOf(0));
			billclearitem.setAdjustamount(BigDecimal.valueOf(0));
			//汇率
			BigDecimal rat = customerTitle.getBwbje().divide(billamount,5,BigDecimal.ROUND_HALF_EVEN);
			billclearitem.setExchangerate(rat);
			billclearitem.setUnbwbje(billclearitem.getUnreceivedamount().multiply(rat).setScale(2, BigDecimal.ROUND_HALF_UP));
			billclearitemList.add(billclearitem);
		}
//		String invoicenos = request.getParameter("invoicenos");
//		String[] invoiceArr = invoicenos.split(",");
//		List<Map<String,BigDecimal>> result = new ArrayList<Map<String,BigDecimal>>();
//		for(String invoice : invoiceArr){
//			BigDecimal sumClearedInvoice = this.billInCollectService.getSumClearedInvoice(invoice);
//			BigDecimal sumOnroadInvoice = this.billInCollectService.getSumOnroadInvoice(invoice);
//			Map<String,BigDecimal> m = new HashMap<String,BigDecimal>();
//			m.put("receivedamount", sumClearedInvoice);
//			m.put("onroadamount", sumOnroadInvoice);
//			result.add(m);
//		}
		
		MultyGridData gridJsonData = new MultyGridData();
		gridJsonData.setData(billclearitemList.toArray());
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
	 * 通过传入发票检验ID来初始化数据
	 * 
	 * @param request
	 * @param response
	 */
	public void assignAmount(HttpServletRequest request,
			HttpServletResponse response) {
		String customertitleids = request.getParameter("customertitleids");
		String customer = request.getParameter("customer");
		String collectitemids = request.getParameter("collectitemids");
		String invoicenos = request.getParameter("invoicenos");
		String billclearid = request.getParameter("billclearid");
		String[] invoicenoArray = invoicenos.split(",");

		// 取得未清完的发票
		List<CustomerTitle> customerTitleList = this.unclearCustomerService
				.getUnclearCustomerInvoiceList(customertitleids);
//		Collections.reverse(customerTitleList);
		// 取得未清完的预收票款单
		List<CollectItem> collectItemList = new ArrayList<CollectItem>();
		if(null == collectitemids  || "".equals(collectitemids)){
			collectItemList = this.collectItemService.getCustomerUnclearCollectList(customer);
		}else{		
			collectItemList = this.collectItemService.getUnclearPreBillCollectList(collectitemids);
		}
		
		
		List<BillClearItem> billClearItemList = new ArrayList<BillClearItem>();
		List<BillInCollect> billInCollectList = new ArrayList<BillInCollect>();
		//this.billClearCollectService.getBillClearCollectById(billClearId)
		List<CollectItem> collects=new ArrayList<CollectItem>();
		int j = -1;
		for (Iterator<CustomerTitle> iter = customerTitleList.iterator(); iter
				.hasNext();) {
			j++;
			CustomerTitle customerTitle = (CustomerTitle) iter.next();
			BillClearItem billClearItem = new BillClearItem();			
			billClearItem.setInvoice(customerTitle.getInvoice());
			billClearItem.setCurrency(customerTitle.getWaers());
			billClearItem.setVoucherno(customerTitle.getBelnr());
			billClearItem.setProject_no(customerTitle.getBname());
			billClearItem.setContract_no(customerTitle.getIhrez());
			billClearItem.setOld_contract_no(this.collectItemService.getOldContractNo(customerTitle.getIhrez().trim())); // 纸质合同号码
			
			billClearItem.setSap_order_no(customerTitle.getVgbel()); // sap订单号,销售凭证号
			billClearItem.setBktxt(customerTitle.getBktxt());
			
			billClearItem.setAccountdate(customerTitle.getBudat());
			
			billClearItem.setTitleid(customerTitle.getCustomertitleid());			
			billClearItem.setBwbje(customerTitle.getBwbje());
			// 开票发票总金额,应付款.
			BigDecimal billamount = customerTitle.getDmbtr();
			billClearItem.setReceivableamount(billamount);
//			billClearItem.setReceivabledate("");
			// 开票发票已经审批完的，发票已收款
			BigDecimal receivedamount = this.unclearCustomerService.getSumClearAmount(customerTitle.getInvoice().trim(), BusinessState.ALL_COLLECT_PAIDUP);
			billClearItem.setReceivedamount(receivedamount);
			
			// 开票发票在途的，发票在途金额
			BigDecimal onroadamount = this.unclearCustomerService.getSumClearAmount(customerTitle.getInvoice().trim(), BusinessState.ALL_COLLECT_ONROAD,billclearid);
			billClearItem.setOnroadamount(onroadamount);
			
			// 未收款=开票发票金额-发票已收款-在批
			billClearItem.setUnreceivedamount(billamount.subtract(receivedamount).subtract(onroadamount));
			//未收款为0不显示
			if(billClearItem.getUnreceivedamount().compareTo(BigDecimal.valueOf(0)) <= 0){
				continue;
			}
			billClearItem.setClearbillamount(BigDecimal.valueOf(0));
			billClearItem.setAdjustamount(BigDecimal.valueOf(0));
			//汇率
			BigDecimal rat2 = customerTitle.getBwbje().divide(billamount,5,BigDecimal.ROUND_HALF_EVEN);
			billClearItem.setExchangerate(rat2);
			billClearItem.setUnbwbje(billClearItem.getUnreceivedamount().multiply(rat2).setScale(2, BigDecimal.ROUND_HALF_UP));
			
			
			List<CollectItem> collects2=new ArrayList<CollectItem>();
			for (int i = 0; i < collectItemList.size(); i++) {
				BigDecimal billreaminamount = billamount
						.subtract(receivedamount).subtract(onroadamount)
						.subtract(billClearItem.getClearbillamount());
				
				
				CollectItem collectItem = (CollectItem) collectItemList.get(i);
				
				if(null ==billClearItem.getContract_no() )billClearItem.setContract_no("");
				if(null == billClearItem.getProject_no())billClearItem.setProject_no("");				
				 if((billClearItem.getContract_no().equalsIgnoreCase(collectItem.getContract_no()) && !("").equals(billClearItem.getContract_no().trim()))||
				 (billClearItem.getProject_no().equals(collectItem.getProject_no()) && ("").equals(collectItem.getContract_no().trim()) ))
				 {
					 collects2.add(collectItem);
					 collects.add(collectItem);
				if (collectItem.getClearedamount() == null)
					collectItem.setClearedamount(BigDecimal.valueOf(0));
				
				// 收票款
				BigDecimal prebillamount = collectItem.getAssignamount();
				// 收款单已审批完的				
				BigDecimal clearedcollectamount = this.collectItemService.getSumClearAmount(collectItem.getCollectitemid().trim(), BusinessState.ALL_COLLECT_PAIDUP);
				// 收款单在途的(在批)
				BigDecimal onroadcollectamount = this.collectItemService.getSumClearAmount(collectItem.getCollectitemid().trim(), BusinessState.ALL_COLLECT_ONROAD, billclearid);
				
				BigDecimal collectremainamount = prebillamount
						.subtract(clearedcollectamount)
						.subtract(onroadcollectamount)
						.subtract(collectItem.getClearedamount()).subtract(collectItem.getSuretybond());
				
				if (collectremainamount.compareTo(BigDecimal.valueOf(0)) <= 0)
					continue;
				
				String voucherNo =  this.billInCollectService.getVoucherIdByCollectId(collectItem.getCollect().getCollectid());
				//没有凭证号的不显示
				if(null ==voucherNo || "".equals(voucherNo.trim()))continue;
				VoucherItem voucherItem2 = this.voucherItemJdbcDao.getVoucherItem(collectItem.getCollectitemid(), "1,4");				
				if(null !=voucherItem2){
					String rowNumber = voucherItem2.getRownumber();
					if(rowNumber.length()==2)rowNumber="0" + rowNumber;			
					if(rowNumber.length()==1)rowNumber="00" + rowNumber;
					CustomerTitle customerTitle2 = customerTitleJdbcDao.getCustomerTitle(voucherItem2.getVoucher().getCompanycode(), voucherItem2.getVoucher().getVoucherno(), voucherItem2.getVoucher().getFiyear(), rowNumber);
					if("1".equals(customerTitle2.getIscleared()) || "2".equals(customerTitle2.getIscleared())){
						continue;	
					}
				}
				BillInCollect billInCollect = new BillInCollect();
				if (billInCollect.getNowclearamount() == null)
					billInCollect.setNowclearamount(BigDecimal.valueOf(0));
				billInCollect.setAccountdate(collectItem.getCollect().getAccountdate());
				billInCollect.setCollectitemid(collectItem.getCollectitemid());
				billInCollect.setVoucherno(voucherNo);
				billInCollect.setProject_no(collectItem.getProject_no());
				billInCollect.setContract_no(collectItem.getContract_no());
				billInCollect.setCollectno(collectItem.getCollectno());
				billInCollect.setSendgoodsdate(collectItem.getCollect().getSendgoodsdate());
				billInCollect.setCollectamount(prebillamount);
				billInCollect.setCurrency(collectItem.getCollect().getCurrency());
				billInCollect.setOffsetamount(clearedcollectamount);
				
				
				billInCollect.setOnroadamount(onroadcollectamount);
				billInCollect.setBwbje(collectItem.getAssignamount2());
				billInCollect.setSuretybond(collectItem.getActsuretybond());
				billInCollect.setBktxt(collectItem.getCollect().getText());
				billInCollect.setOldcontract_no(this.collectItemService.getOldContractNo(collectItem.getContract_no().trim()));
				//汇率
				BigDecimal rat = billInCollect.getBwbje().divide(billInCollect.getCollectamount(),5,BigDecimal.ROUND_HALF_EVEN);
				billInCollect.setExchangerate(rat);
				
				billInCollect.setUnoffsetamount(prebillamount.subtract(clearedcollectamount).subtract(onroadcollectamount).subtract(billInCollect.getSuretybond()));
				//未清金额为0不显示
				if(billInCollect.getUnoffsetamount().compareTo(BigDecimal.valueOf(0)) <= 0){
					continue;
				}
				//判断第一次清帐，已抵消收款为0，且在批抵消收款为0，且有保证金的情况。
				if(billInCollect.getOnroadamount().compareTo(new BigDecimal(0))==0 && billInCollect.getOffsetamount().compareTo(new BigDecimal(0))==0 && billInCollect.getSuretybond().compareTo(new BigDecimal(0))!=0){
					//未收款本位币，如果第一次清帐有保证金要减去保证金，如果是外币还在算汇率。
					if("CNY".equals(billInCollect.getCurrency())){
						billInCollect.setUnbwbje(collectItem.getAssignamount2().subtract(billInCollect.getSuretybond()));
					}else{
						
						//保证金的本位币
						BigDecimal suretybond2 = billInCollect.getSuretybond().multiply(rat);
						//未收款本位币
						billInCollect.setUnbwbje(billInCollect.getUnoffsetamount().multiply(rat).subtract(suretybond2).setScale(2, BigDecimal.ROUND_HALF_UP));
					}
				}else{
					billInCollect.setUnbwbje(billInCollect.getUnoffsetamount().multiply(rat).setScale(2, BigDecimal.ROUND_HALF_UP));
				}
				//如果票没有分配金也显示出来
				if (billreaminamount.compareTo(BigDecimal.valueOf(0)) == 0){
					billInCollectList.add(billInCollect);
					continue;					
				}
				if (billInCollect.getNowclearamount() == null)
					billInCollect.setNowclearamount(BigDecimal.valueOf(0));
				// 发票剩余被清金额  > 收款单剩余可用金额
				if (billreaminamount.compareTo(collectremainamount) == 1 || billreaminamount.compareTo(collectremainamount) == 0) {
					collectItem.setClearedamount(collectItem.getClearedamount().add(collectremainamount));
					billClearItem.setClearbillamount(billClearItem.getClearbillamount().add(collectremainamount));
					billInCollect.setNowclearamount(billInCollect.getNowclearamount().add(collectItem.getClearedamount()));
					
					billInCollectList.add(billInCollect);
				} else {
					collectItem.setClearedamount(collectItem.getClearedamount().add(billreaminamount));
					billClearItem.setClearbillamount(billClearItem.getClearbillamount().add(billreaminamount));
					billInCollect.setNowclearamount(billInCollect.getNowclearamount().add(collectItem.getClearedamount()));
//					if (iter.hasNext())
//						continue;
//					else
						billInCollectList.add(billInCollect);
				}
				}
			}

			billClearItemList.add(billClearItem);
		}
		Collections.reverse(billInCollectList);
		
		List<BillInCollect> billInCollectList2 = new ArrayList<BillInCollect>();
		
		for(BillInCollect billInCollect2: billInCollectList){
			boolean billFalg=true;
			if(billInCollect2.getNowclearamount() == null) billInCollect2.setNowclearamount(BigDecimal.valueOf(0));
			for(BillInCollect billInCollect3: billInCollectList2){
				if(billInCollect2.getCollectitemid().equals(billInCollect3.getCollectitemid())){
					billFalg=false;
					break;
				}
			}
			if(billFalg)billInCollectList2.add(billInCollect2);
		}
		MultyGridData gridJsonData = new MultyGridData();
		gridJsonData.setData(billClearItemList.toArray());
		gridJsonData.setData2(billInCollectList2.toArray());
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
	 * 通过传入票清收款ID生成凭证
	 * 
	 * @param request
	 * @param response
	 */
	public void voucherPreview(HttpServletRequest request,
			HttpServletResponse response) {
		JSONObject jo = new JSONObject();
		// 绑定主对象值
		BillClearCollect billClearCollect = (BillClearCollect) ExBeanUtils
				.bindBusinessObjectData(request.getParameterMap(),
						BillClearCollect.class, true, request.getMethod(), true);
		// 绑定子对象(一对一关系)
		SettleSubjectBcc settleSubjectBcc = (SettleSubjectBcc) ExBeanUtils
				.bindBusinessObjectData(request.getParameterMap(),
						SettleSubjectBcc.class, false, request.getMethod(),
						true);
		if (settleSubjectBcc != null) {
			billClearCollect.setSettleSubjectBcc(settleSubjectBcc);
			settleSubjectBcc.setBillClearCollect(billClearCollect);
		}
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<BillClearItem> billclearitemmodifyItems = ExBeanUtils
				.bindModifySubBoData(request.getParameterMap(),
						new Object[] { billClearCollect }, BillClearItem.class,
						null);
		billClearCollect.setBillclearitem(billclearitemmodifyItems);
		Set<BillClearItem> billclearitemdeleteItems = ExBeanUtils
				.bindDeleteSubBoData(request.getParameterMap(),
						new Object[] { billClearCollect }, BillClearItem.class,
						null);
		// 绑定子对象(一对一关系)
		FundFlowBcc fundFlowBcc = (FundFlowBcc) ExBeanUtils
				.bindBusinessObjectData(request.getParameterMap(),
						FundFlowBcc.class, false, request.getMethod(), true);
		if (fundFlowBcc != null) {
			billClearCollect.setFundFlowBcc(fundFlowBcc);
			fundFlowBcc.setBillClearCollect(billClearCollect);
		}
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<BillInCollect> billincollectmodifyItems = ExBeanUtils
				.bindModifySubBoData(request.getParameterMap(),
						new Object[] { billClearCollect }, BillInCollect.class,
						null);
		billClearCollect.setBillincollect(billincollectmodifyItems);
		Set<BillInCollect> billincollectdeleteItems = ExBeanUtils
				.bindDeleteSubBoData(request.getParameterMap(),
						new Object[] { billClearCollect }, BillInCollect.class,
						null);

		this.billClearCollectService._saveOrUpdate(billClearCollect,
				billclearitemdeleteItems, billincollectdeleteItems,
				getBusinessObject());

		Voucher voucher = this.billClearCollectService
				.getVoucher(billClearCollect);

		try {
			response.setContentType("text/html;charset=UTF-8");
			response.getWriter().print(voucher.getVoucherid());
			System.out.println(voucher.getVoucherid());
		} catch (IOException e) {
			logger.error("输出json失败," + e.getMessage(), e.getCause());
		}

	}

	/**
	 * 通过传入票清收款ID生成凭证
	 * 
	 * @param request
	 * @param response
	 */
	public void voucherPreview2(HttpServletRequest request,
			HttpServletResponse response) {
		JSONObject jo = new JSONObject();
		// 绑定主对象值
		BillClearCollect billClearCollect = (BillClearCollect) ExBeanUtils
				.bindBusinessObjectData(request.getParameterMap(),
						BillClearCollect.class, true, request.getMethod(), true);
		// 绑定子对象(一对一关系)
		SettleSubjectBcc settleSubjectBcc = (SettleSubjectBcc) ExBeanUtils
				.bindBusinessObjectData(request.getParameterMap(),
						SettleSubjectBcc.class, false, request.getMethod(),
						true);
		if (settleSubjectBcc != null) {
			billClearCollect.setSettleSubjectBcc(settleSubjectBcc);
			settleSubjectBcc.setBillClearCollect(billClearCollect);
		}
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<BillClearItem> billclearitemmodifyItems = ExBeanUtils
				.bindModifySubBoData(request.getParameterMap(),
						new Object[] { billClearCollect }, BillClearItem.class,
						null);
		billClearCollect.setBillclearitem(billclearitemmodifyItems);
		Set<BillClearItem> billclearitemdeleteItems = ExBeanUtils
				.bindDeleteSubBoData(request.getParameterMap(),
						new Object[] { billClearCollect }, BillClearItem.class,
						null);
		// 绑定子对象(一对一关系)
		FundFlowBcc fundFlowBcc = (FundFlowBcc) ExBeanUtils
				.bindBusinessObjectData(request.getParameterMap(),
						FundFlowBcc.class, false, request.getMethod(), true);
		if (fundFlowBcc != null) {
			billClearCollect.setFundFlowBcc(fundFlowBcc);
			fundFlowBcc.setBillClearCollect(billClearCollect);
		}
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<BillInCollect> billincollectmodifyItems = ExBeanUtils
				.bindModifySubBoData(request.getParameterMap(),
						new Object[] { billClearCollect }, BillInCollect.class,
						null);
		billClearCollect.setBillincollect(billincollectmodifyItems);
		Set<BillInCollect> billincollectdeleteItems = ExBeanUtils
				.bindDeleteSubBoData(request.getParameterMap(),
						new Object[] { billClearCollect }, BillInCollect.class,
						null);

		this.billClearCollectService._saveOrUpdate(billClearCollect,
				billclearitemdeleteItems, billincollectdeleteItems,
				getBusinessObject());

		String businessId = billClearCollect.getBillclearid();
		billClearCollect =  billClearCollectService._get(businessId);
		// 先删除凭证信息。
		this.voucherService.deleteVoucherByBusinessid(businessId);
		ParameterVoucherObject parameterVoucher =customerClearAccountService.setParameter(billClearCollect);
		BigDecimal marginAmount = new BigDecimal("0");
		BigDecimal marginAmount_bwb = new BigDecimal("0");
		Voucher settleSubjectVoucher = null;
		
		if (settleSubjectBcc != null || fundFlowBcc != null){			
			settleSubjectVoucher = customerClearAccountService.saveSettleSubjectVoucher(parameterVoucher);				
			marginAmount = customerClearAccountService.getMargin(settleSubjectVoucher.getVoucherItem());
			marginAmount_bwb =customerClearAccountService.getMarginByBwb(settleSubjectVoucher.getVoucherItem());
			parameterVoucher.setVoucherid(settleSubjectVoucher.getVoucherid());
		}
		//判断是否需要删除
		this.voucherService.judgeVoucherNeedDel(businessId);
		boolean isSave = false;
		if(!StringUtils.isNullBlank(businessId))isSave=true;
		IInfo info =customerClearAccountService.checkClearData(billClearCollect, marginAmount,isSave);

//		判断本次数据是否正确
		if(info.isRight()){
			IInfoVoucher infoVoucher = customerClearAccountService.isClearAccount(billClearCollect, marginAmount_bwb);
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
					Voucher clearVoucher =customerClearAccountService.saveClearVoucherByCustomer(parameterVoucher,infoVoucher,businessId,ClearConstant.BILL_TYPE,isp);
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
        BillClearCollect billClearCollect;
        String boid = this.reassignJdbcDao.isCopyed(id, ReassignConstant.BILLCLEARCOLLECT);
        if(boid != null){
            billClearCollect = this.billClearCollectService.getBillClearCollectById(boid);
        }else{
            billClearCollect = this.billClearCollectService._getEntityCopy(id);
            billClearCollect.setCreatetime("");
            request.setAttribute("isCreateCopy", "true");
            billClearCollect.setOldbillclearid(id); // 设置被重分配退款ID
            billClearCollect.setBusinessstate(BusinessState.REASSIGNED); // 设置业务状态为重分配
            billClearCollect.setText(reassign.getText()); // 设置文本为重分配提交时填写文本

            /**
             * 重新计算金额
             */
            Set<BillClearItem> newBillClearItemSet = new HashSet<BillClearItem>();
            for(BillClearItem billClearItem : billClearCollect.getBillclearitem()){
                // 发票已经审批完的
                BigDecimal receivedamount = this.unclearCustomerService.getSumClearAmount(billClearItem.getInvoice(), BusinessState.ALL_BILLCLEAR_PAIDUP);
                billClearItem.setReceivedamount(receivedamount);
                billClearItem.setUnreceivedamount(billClearItem.getReceivableamount().subtract(receivedamount));

                // 发票在途的
                BigDecimal onroadamount = this.unclearCustomerService.getSumClearAmount(billClearItem.getInvoice(), BusinessState.ALL_BILLCLEAR_ONROAD);
                billClearItem.setOnroadamount(onroadamount);

                newBillClearItemSet.add(billClearItem);
            }
            billClearCollect.setBillclearitem(newBillClearItemSet);

            Set<BillInCollect> newbillInCollectSet = new HashSet<BillInCollect>();
            for(BillInCollect billInCollect : billClearCollect.getBillincollect()){
                // 收款单已审批完的
                BigDecimal clearedcollectamount = this.billInCollectService.getSumClearedAmount(billInCollect.getCollectitemid(), "1");
                // 收款单在途的
                BigDecimal onroadcollectamount = this.billInCollectService.getSumOnroadAmount(billInCollect.getCollectitemid(), "1");
                billInCollect.setOffsetamount(clearedcollectamount);
                billInCollect.setUnoffsetamount(billInCollect.getCollectamount().subtract(clearedcollectamount));
                billInCollect.setOnroadamount(onroadcollectamount);

                newbillInCollectSet.add(billInCollect);
            }
            billClearCollect.setBillincollect(newbillInCollectSet);

        }

        request.setAttribute("billClearCollect", billClearCollect);
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
        return new ModelAndView("xdss3/billclearcollect/billClearCollectAdd");
    }

	/**
	 * 重分配查看
	 * 
	 * @param request
	 * @param response
	 */
	public ModelAndView _viewForReassign(HttpServletRequest request,
			HttpServletResponse response) {

		BillClearCollect billClearCollect = new BillClearCollect();
		String workflowTaskId = request.getParameter("workflowTaskId");
		String workflowNodeDefId = request.getParameter("workflowNodeDefId");
		String businessId = request.getParameter("businessId");
		String oldId = this.reassignJdbcDao.getBoidByReassignid(businessId);
		String id = this.reassignJdbcDao.getNewNoIdByOldNoId(
				ReassignConstant.BILLCLEARCOLLECT, oldId);

		if (null == id)
			id = businessId;

		if (StringUtils.isNullBlank(id)) {
			billClearCollect = this.billClearCollectService._getForEdit(id);
		} else {
			billClearCollect = this.billClearCollectService._getForEdit(id);
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
		request.setAttribute("billClearCollect", billClearCollect);
		return new ModelAndView("xdss3/billclearcollect/billClearCollectView");
	}
	
	/**
	 * 重分配编辑
	 * 
	 * @param request
	 * @param response
	 */
	public ModelAndView _editForReassign(HttpServletRequest request,
			HttpServletResponse response) {

		BillClearCollect billClearCollect = new BillClearCollect();
		String workflowTaskId = request.getParameter("workflowTaskId");
		String workflowNodeDefId = request.getParameter("workflowNodeDefId");
		String businessId = request.getParameter("businessId");
		String oldId = this.reassignJdbcDao.getBoidByReassignid(businessId);
		String id = this.reassignJdbcDao.getNewNoIdByOldNoId(
				ReassignConstant.BILLCLEARCOLLECT, oldId);

		if (null == id)
			id = businessId;

		if (StringUtils.isNullBlank(id)) {
			billClearCollect = this.billClearCollectService._getForEdit(id);
		} else {
			billClearCollect = this.billClearCollectService._getForEdit(id);
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
		request.setAttribute("billClearCollect", billClearCollect);
		return new ModelAndView("xdss3/billclearcollect/billClearCollectEdit");
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
		BillClearCollect billClearCollect = new BillClearCollect();
		String workflowTaskId = request.getParameter("workflowTaskId");
		String workflowNodeDefId = request.getParameter("workflowNodeDefId");
		request.setAttribute("vt", getBusinessObject().getViewText());

		// 填入部门ID值。
		billClearCollect.setDeptid(UserContextHolder.getLocalUserContext()
				.getUserContext().getSysUser().getDeptId());
		if (null != getBusinessObject().getSubBusinessObject()) {
			// 取得子业务对象
			for (BusinessObject bo : getBusinessObject().getSubBusinessObject()) {
				request.setAttribute(bo.getBeanAttribute()
						.getBeanInstanceName() + "Vt", bo.getViewText());
			}
		}
		request.setAttribute("billClearCollect", billClearCollect);
		boolean haveBindWorkFlow = WorkflowService
				.getHaveBindWorkFlow("000000000282");
		request.setAttribute("haveBindWorkFlow", haveBindWorkFlow);
		request.setAttribute("workflowTaskId", workflowTaskId);
		request.setAttribute("workflowNodeDefId", workflowNodeDefId);
		return new ModelAndView("xdss3/billclearcollect/billClearCollectAdd");
	}

	/**
	 * 重分配提交
	 * 
	 * @param request
	 * @param response
	 */
    public void _submitForReassign(HttpServletRequest request, HttpServletResponse response){
        String workflowTaskId = request.getParameter("workflowTaskId");

        // 绑定主对象值
        BillClearCollect billClearCollect = (BillClearCollect) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), BillClearCollect.class,
                true, request.getMethod(), true);
        // 类型标识是从那个页面提交，view 表示从view页面提交流程。
        String type = request.getParameter("type");
        // 绑定子对象(一对一关系)
        SettleSubjectBcc settleSubjectBcc = (SettleSubjectBcc) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), SettleSubjectBcc.class,
                false, request.getMethod(), true);
        if(settleSubjectBcc != null){
            billClearCollect.setSettleSubjectBcc(settleSubjectBcc);
            settleSubjectBcc.setBillClearCollect(billClearCollect);
        }
        // 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
        Set<BillClearItem> billclearitemmodifyItems = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[]{billClearCollect},
                BillClearItem.class, null);
        Set<BillClearItem> deletedBillClearItemSet = ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(), new Object[]{billClearCollect},
                BillClearItem.class, null);
        billClearCollect.setBillclearitem(billclearitemmodifyItems);
        // 绑定子对象(一对一关系)
        FundFlowBcc fundFlowBcc = (FundFlowBcc) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), FundFlowBcc.class, false,
                request.getMethod(), true);
        if(fundFlowBcc != null){
            billClearCollect.setFundFlowBcc(fundFlowBcc);
            fundFlowBcc.setBillClearCollect(billClearCollect);
        }
        // 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
        Set<BillInCollect> billincollectmodifyItems = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[]{billClearCollect},
                BillInCollect.class, null);
        Set<BillInCollect> deletedBillInCollectSet = ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(), new Object[]{billClearCollect},
                BillInCollect.class, null);
        billClearCollect.setBillincollect(billincollectmodifyItems);
        if(!"view".equalsIgnoreCase(type)){
            this.billClearCollectService._saveOrUpdate(billClearCollect, deletedBillClearItemSet, deletedBillInCollectSet, getBusinessObject());
        }

        String reassignId = this.reassignJdbcDao.getReassignidByBoId(billClearCollect.getOldbillclearid());
        Reassign reassign = this.reassignService.getReassignById(reassignId);
        reassign.setWorkflowTaskId(workflowTaskId);
        reassign.setWorkflowLeaveTransitionName(billClearCollect.getWorkflowLeaveTransitionName());
        reassign.setWorkflowExamine(billClearCollect.getWorkflowExamine());
        reassign.setWorkflowUserDefineProcessVariable(billClearCollect.getWorkflowUserDefineProcessVariable());
        reassign.setWorkflowBusinessNote(billClearCollect.getWorkflowBusinessNote());
        reassign.setWorkflowUserDefineTaskVariable(billClearCollect.getWorkflowUserDefineTaskVariable());

        /**
         * 判断重分配方式是否为： 重置（到业务部门重新分配） 需要拷贝凭证
         */
        if(reassign.getProcessstate() != null && !reassign.getProcessstate().equals("财务部审核")
                && reassign.getReassigntmethod().equals(ReassignConstant.RESET_TO_BS)){
            this.reassignService.copyVoucher(reassign.getReassignboid(), billClearCollect.getBillclearid());
        }

        // 设置审核状态
        if(billClearCollect.getWorkflowLeaveTransitionName().equals("审核不通过")){
            this.reassignService.updateReassignState(reassignId, BusinessState.SUBMITNOTPASS);
        }

        BusinessObject businessObject = BusinessObjectService.getBusinessObject("Reassign");
        this.reassignService._submitProcess(reassign, businessObject);
        this.operateSuccessfully(response);
    }

	/**
	 * 模拟凭证
	 */
	@Override
	public void _voucherPreview(HttpServletRequest request,
			HttpServletResponse response) {
		JSONObject jo = new JSONObject();
		// 绑定主对象值
		BillClearCollect billClearCollect = (BillClearCollect) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(),
						BillClearCollect.class, true, request.getMethod(), true);
		
		
		// 绑定子对象(一对一关系)
		SettleSubjectBcc settleSubjectBcc = (SettleSubjectBcc) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(),
						SettleSubjectBcc.class, false, request.getMethod(),true);
		if (settleSubjectBcc != null) {
			billClearCollect.setSettleSubjectBcc(settleSubjectBcc);
			settleSubjectBcc.setBillClearCollect(billClearCollect);
		}
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<BillClearItem> billclearitemmodifyItems = ExBeanUtils.bindModifySubBoData(request.getParameterMap(),
						new Object[] { billClearCollect }, BillClearItem.class,null);
		Set<BillClearItem> deletedBillClearItemSet = ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(),
						new Object[] { billClearCollect }, BillClearItem.class,null);
		billClearCollect.setBillclearitem(billclearitemmodifyItems);
		// 绑定子对象(一对一关系)
		FundFlowBcc fundFlowBcc = (FundFlowBcc) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(),
						FundFlowBcc.class, false, request.getMethod(), true);
		if (fundFlowBcc != null) {
			billClearCollect.setFundFlowBcc(fundFlowBcc);
			fundFlowBcc.setBillClearCollect(billClearCollect);
		}
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<BillInCollect> billincollectmodifyItems = ExBeanUtils.bindModifySubBoData(request.getParameterMap(),
						new Object[] { billClearCollect }, BillInCollect.class,null);
		Set<BillInCollect> deletedBillInCollectSet = ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(),
						new Object[] { billClearCollect }, BillInCollect.class,null);
		billClearCollect.setBillincollect(billincollectmodifyItems);
		
		billClearCollectService._saveOrUpdate(billClearCollect, deletedBillClearItemSet, deletedBillInCollectSet, getBusinessObject());
		
		this.voucherService.deleteVoucherByBusinessid(billClearCollect.getBillclearid());
		
		String businessId = billClearCollectService
				.saveVoucher(billClearCollect);
		// if("!null".equals(businessId)){
		// throw new BusinessException("调整金额要等于借款金额减贷款金额！");
		// }
		//判断是否需要删除
		this.voucherService.judgeVoucherNeedDel(businessId);
		
		this.operateSuccessfullyHiddenInfoWithString(response, businessId);

	}	
	
	/**
	 * 提交  
	 *   
	 * @param request
	 * @param response
	 */
	@Override 
	public void _submitProcess(HttpServletRequest request, HttpServletResponse response)
	{
		// 绑定主对象值
		BillClearCollect billClearCollect = (BillClearCollect) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), BillClearCollect.class , true , request.getMethod(), true);
        //类型标识是从那个页面提交，view 表示从view页面提交流程。
        String type = request.getParameter("type");
		//绑定子对象(一对一关系)
		SettleSubjectBcc settleSubjectBcc = (SettleSubjectBcc) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), SettleSubjectBcc.class, false , request.getMethod(), true);
		if(settleSubjectBcc!=null)
		{
		billClearCollect.setSettleSubjectBcc(settleSubjectBcc);
		settleSubjectBcc.setBillClearCollect(billClearCollect);
		}
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<BillClearItem> billclearitemmodifyItems = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { billClearCollect }, BillClearItem.class, null);
		Set<BillClearItem> deletedBillClearItemSet = ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(), new Object[] { billClearCollect }, BillClearItem.class, null);
		billClearCollect.setBillclearitem(billclearitemmodifyItems);
		//绑定子对象(一对一关系)
		FundFlowBcc fundFlowBcc = (FundFlowBcc) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), FundFlowBcc.class, false , request.getMethod(), true);
		if(fundFlowBcc!=null)
		{
		billClearCollect.setFundFlowBcc(fundFlowBcc);
		fundFlowBcc.setBillClearCollect(billClearCollect);
		}
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<BillInCollect> billincollectmodifyItems = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { billClearCollect }, BillInCollect.class, null);
		Set<BillInCollect> deletedBillInCollectSet = ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(), new Object[] { billClearCollect }, BillInCollect.class, null);
		billClearCollect.setBillincollect(billincollectmodifyItems);
		if (!"view".equalsIgnoreCase(type))
		{
		this.billClearCollectService._saveOrUpdate(billClearCollect
		,deletedBillClearItemSet
		,deletedBillInCollectSet
		,getBusinessObject());
		}
		if("会计审批".equals(billClearCollect.getProcessstate()) && "确认提交".equals(billClearCollect.getWorkflowLeaveTransitionName())){
			//更新isclear状态
//			if(this.billClearCollectService.checkVoucher(billClearCollect.getBillclearid(), "A")){
//				this.billClearCollectService.updateOldTitle(billClearCollect, true);
//			}else{
//				this.billClearCollectService.updateOldTitle(billClearCollect, false);
//			}
//		
//			this.billClearCollectService.settleBillClearCollect(billClearCollect.getBillclearid());
			String bussinessid =billClearCollect.getBillclearid();
			IInfoVoucher infoVoucher = (IInfoVoucher)request.getSession().getAttribute(bussinessid);
//			是否全清
			if(infoVoucher.isClear()){
				customerClearAccountService.updateIsClear(bussinessid);
			}else{
				customerClearAccountService.updateIsClear(infoVoucher);
			}		
			request.getSession().removeAttribute(bussinessid);
			
		}
		String workflowBusinessNote = "";
		String deptName = com.infolion.platform.console.sys.context.UserContextHolder.getLocalUserContext().getUserContext().getSysUser().getDeptName();
		String creator = com.infolion.platform.console.sys.context.UserContextHolder.getLocalUserContext().getUserContext().getSysUser().getRealName();
		//  2010-11-11 添加信息进待办信息	
		String projectNo=" ";
		for(BillClearItem billClearItem :billclearitemmodifyItems){
			projectNo=billClearItem.getProject_no();
			break;
		}
		
		workflowBusinessNote = billClearCollect.getBillclearno() + "|" + deptName + "|" + creator + "|立项号" + projectNo;
		
		billClearCollect.setWorkflowBusinessNote(workflowBusinessNote);
		
		this.billClearCollectService._submitProcess(billClearCollect
		,deletedBillClearItemSet
		,deletedBillInCollectSet		,getBusinessObject());
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
		BillClearCollect billClearCollect = (BillClearCollect) ExBeanUtils
				.bindBusinessObjectData(request.getParameterMap(),
						BillClearCollect.class, true, request.getMethod(), true);
		// 绑定子对象(一对一关系)
		SettleSubjectBcc settleSubjectBcc = (SettleSubjectBcc) ExBeanUtils
				.bindBusinessObjectData(request.getParameterMap(),
						SettleSubjectBcc.class, false, request.getMethod(),
						true);
		if (settleSubjectBcc != null) {
			billClearCollect.setSettleSubjectBcc(settleSubjectBcc);
			settleSubjectBcc.setBillClearCollect(billClearCollect);
		}
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<BillClearItem> billclearitemmodifyItems = ExBeanUtils
				.bindModifySubBoData(request.getParameterMap(),
						new Object[] { billClearCollect }, BillClearItem.class,
						null);
		billClearCollect.setBillclearitem(billclearitemmodifyItems);
		Set<BillClearItem> billclearitemdeleteItems = ExBeanUtils
				.bindDeleteSubBoData(request.getParameterMap(),
						new Object[] { billClearCollect }, BillClearItem.class,
						null);
		// 绑定子对象(一对一关系)
		FundFlowBcc fundFlowBcc = (FundFlowBcc) ExBeanUtils
				.bindBusinessObjectData(request.getParameterMap(),
						FundFlowBcc.class, false, request.getMethod(), true);
		if (fundFlowBcc != null) {
			billClearCollect.setFundFlowBcc(fundFlowBcc);
			fundFlowBcc.setBillClearCollect(billClearCollect);
		}
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<BillInCollect> billincollectmodifyItems = ExBeanUtils
				.bindModifySubBoData(request.getParameterMap(),
						new Object[] { billClearCollect }, BillInCollect.class,
						null);
		billClearCollect.setBillincollect(billincollectmodifyItems);
		Set<BillInCollect> billincollectdeleteItems = ExBeanUtils
				.bindDeleteSubBoData(request.getParameterMap(),
						new Object[] { billClearCollect }, BillInCollect.class,
						null);

		
	
		ParameterVoucherObject parameterVoucher =customerClearAccountService.setParameter(billClearCollect);
		BigDecimal marginAmount = new BigDecimal("0");
		Voucher settleSubjectVoucher = null;
		
		if (settleSubjectBcc != null || fundFlowBcc != null){			
			settleSubjectVoucher = customerClearAccountService.saveSettleSubjectVoucher(parameterVoucher);				
			marginAmount = customerClearAccountService.getMargin(settleSubjectVoucher.getVoucherItem());
			parameterVoucher.setVoucherid(settleSubjectVoucher.getVoucherid());
		}
		
		boolean isSave = false;
		if(!StringUtils.isNullBlank(billClearCollect.getBillclearid()))isSave=true;
		IInfo info =customerClearAccountService.checkClearData(billClearCollect, marginAmount,isSave);
	
		jo.put("isRight", info.isRight());
		jo.put("info", info.getInfo());
		this.operateSuccessfullyHiddenInfoWithString(response, jo.toString());
		
	}
}