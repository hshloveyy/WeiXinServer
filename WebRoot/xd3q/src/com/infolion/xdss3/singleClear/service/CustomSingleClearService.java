/*
 * @(#)CustomSingleClearService.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年07月16日 11点02分05秒
 *  描　述：创建
 */
package com.infolion.xdss3.singleClear.service;

import java.lang.reflect.InvocationTargetException;
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

import net.sf.json.JSONObject;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infolion.platform.basicApp.authManage.UserContextHolder;
import com.infolion.platform.basicApp.authManage.domain.UserContext;
import com.infolion.platform.bo.domain.BusinessObject;
import com.infolion.platform.bo.service.BusinessObjectService;
import com.infolion.platform.console.org.dao.SysDeptJdbcDao;
import com.infolion.platform.dpframework.core.BusinessException;
import com.infolion.platform.dpframework.uicomponent.number.service.NumberService;
import com.infolion.platform.dpframework.uicomponent.systemMessage.SysMsgConstants;
import com.infolion.platform.dpframework.util.DateUtils;
import com.infolion.platform.dpframework.util.StringUtils;
import com.infolion.platform.util.MultyGridData;
import com.infolion.xdss3.BusinessState;
import com.infolion.xdss3.Constants;
import com.infolion.xdss3.collect.dao.CollectHibernateDao;
import com.infolion.xdss3.collect.dao.CollectJdbcDao;
import com.infolion.xdss3.collect.domain.Collect;
import com.infolion.xdss3.collectitem.dao.CollectItemJdbcDao;
import com.infolion.xdss3.collectitem.domain.CollectItem;
import com.infolion.xdss3.collectitem.service.CollectItemService;
import com.infolion.xdss3.customerDrawback.dao.CustomerRefundItemJdbcDao;
import com.infolion.xdss3.customerDrawback.domain.CustomerRefundItem;
import com.infolion.xdss3.customerDrawback.service.CustomerRefundItemService;
import com.infolion.xdss3.customerDrawback.service.CustomerRefundmentService;
import com.infolion.xdss3.financeSquare.domain.FundFlow;
import com.infolion.xdss3.financeSquare.domain.SettleSubject;
import com.infolion.xdss3.masterData.dao.CustomerTitleHibernateDao;
import com.infolion.xdss3.masterData.dao.CustomerTitleJdbcDao;
import com.infolion.xdss3.masterData.domain.CustomerTitle;
import com.infolion.xdss3.masterData.service.UnclearCustomerService;
import com.infolion.xdss3.payment.dao.ImportPaymentHibernateDao;
import com.infolion.xdss3.payment.domain.ClearVoucherItemStruct;
import com.infolion.xdss3.payment.domain.ImportPayment;
import com.infolion.xdss3.payment.domain.ImportPaymentItem;
import com.infolion.xdss3.payment.service.PaymentItemService;
import com.infolion.xdss3.singleClear.dao.CustomSingleClearJdbcDao;
import com.infolion.xdss3.singleClear.domain.CustomSingleClear;
import com.infolion.xdss3.singleClear.domain.UnClearCollect;
import com.infolion.xdss3.singleClear.domain.UnClearCustomBill;
import com.infolion.xdss3.singleClearGen.service.CustomSingleClearServiceGen;
import com.infolion.xdss3.voucher.dao.VoucherHibernateDao;
import com.infolion.xdss3.voucher.dao.VoucherJdbcDao;
import com.infolion.xdss3.voucher.domain.Voucher;
import com.infolion.xdss3.voucher.service.VoucherService;
import com.infolion.xdss3.voucheritem.dao.VoucherItemJdbcDao;
import com.infolion.xdss3.voucheritem.domain.VoucherItem;

/**
 * <pre>
 * 客户单清帐(CustomSingleClear)服务类
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
@Service
public class CustomSingleClearService extends CustomSingleClearServiceGen
{

	private Log log = LogFactory.getFactory().getLog(this.getClass());

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
	private VoucherJdbcDao voucherJdbcDao;

	/**
	 * @param voucherJdbcDao
	 *            the voucherJdbcDao to set
	 */
	public void setVoucherJdbcDao(VoucherJdbcDao voucherJdbcDao)
	{
		this.voucherJdbcDao = voucherJdbcDao;
	}

	@Autowired
	private CollectHibernateDao collectHibernateDao;

	/**
	 * @param collectHibernateDao
	 *            the collectHibernateDao to set
	 */
	public void setCollectHibernateDao(CollectHibernateDao collectHibernateDao)
	{
		this.collectHibernateDao = collectHibernateDao;
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
	private CollectJdbcDao collectJdbcDao;

	/**
	 * @param collectItemService
	 *            the collectItemService to set
	 */
	public void setCollectJdbcDao(CollectJdbcDao collectJdbcDao)
	{
		this.collectJdbcDao = collectJdbcDao;
	}
	
	
	@Autowired
	private ImportPaymentHibernateDao importPaymentHibernateDao;

	/**
	 * @param importPaymentHibernateDao
	 *            the importPaymentHibernateDao to set
	 */
	public void setImportPaymentHibernateDao(ImportPaymentHibernateDao importPaymentHibernateDao)
	{
		this.importPaymentHibernateDao = importPaymentHibernateDao;
	}

	@Autowired
	private CustomerTitleJdbcDao customerTitleJdbcDao;

	/**
	 * @param customerTitleJdbcDao
	 *            the customerTitleJdbcDao to set
	 */
	public void setCustomerTitleJdbcDao(CustomerTitleJdbcDao customerTitleJdbcDao)
	{
		this.customerTitleJdbcDao = customerTitleJdbcDao;
	}

	@Autowired
	private CollectItemJdbcDao collectItemJdbcDao;

	/**
	 * @param collectItemJdbcDao
	 *            the collectItemJdbcDao to set
	 */
	public void setCollectItemJdbcDao(CollectItemJdbcDao collectItemJdbcDao)
	{
		this.collectItemJdbcDao = collectItemJdbcDao;
	}

	@Autowired
	protected VoucherHibernateDao voucherHibernateDao;

	public void setVoucherHibernateDao(VoucherHibernateDao voucherHibernateDao)
	{
		this.voucherHibernateDao = voucherHibernateDao;
	}
	
	@Autowired
	protected CustomerTitleHibernateDao customerTitleHibernateDao;
	
	public void setCustomerTitleHibernateDao(
			CustomerTitleHibernateDao customerTitleHibernateDao) {
		this.customerTitleHibernateDao = customerTitleHibernateDao;
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
	private VoucherItemJdbcDao voucherItemJdbcDao;

	
	public void setVoucherItemJdbcDao(VoucherItemJdbcDao voucherItemJdbcDao) {
		this.voucherItemJdbcDao = voucherItemJdbcDao;
	}

	@Autowired	
	private SysDeptJdbcDao sysDeptJdbcDao;
	
	
	public void setSysDeptJdbcDao(SysDeptJdbcDao sysDeptJdbcDao) {
		this.sysDeptJdbcDao = sysDeptJdbcDao;
	}
	
	@Autowired	
	private CustomerRefundItemService customerRefundItemService;
	
	
	public void setCustomerRefundItemService(CustomerRefundItemService customerRefundItemService) {
		this.customerRefundItemService = customerRefundItemService;
	}
	

	/**
	 * 自动分配
	 * 
	 * @param supplier
	 * @return
	 */
	public JSONObject _autoAssign(String custom, String subject,String currencytext ,String companyno ,String depid)
	{
		// 根据客户，取得客户下未清发票数据集合
		//List<CustomerTitle> customerTitleList = this.unclearCustomerService.getCustomerTitleListBySupplier(custom, subject, "" );
		List<CustomerTitle> customerTitleList = this.unclearCustomerService.getCustomerTitleListBySupplier(custom, subject,currencytext,companyno,depid, "" );
		// 未清应收（借方）
		List<UnClearCustomBill> unClearCustomBillList = new ArrayList<UnClearCustomBill>();
		// 未清收款（贷方）
		List<UnClearCollect> unClearCollectList = new ArrayList<UnClearCollect>();

		// 查询计算出所有未清数据，供自动分配时候使用。
		this.getUnClearInfo(customerTitleList, unClearCustomBillList, unClearCollectList);

		for (int i = 0; i < unClearCustomBillList.size(); i++)
		{
			UnClearCustomBill unClearCustomBill = unClearCustomBillList.get(i);
			// 发票总金额
			BigDecimal receivableamount = unClearCustomBill.getReceivableamount();
			// 发票已经审批完的，发票已收款
			BigDecimal receivedamount = unClearCustomBill.getReceivedamount();
			// 未收款=发票金额-发票已收款
			BigDecimal unreceivableamou = unClearCustomBill.getUnreceivableamou();
			// 发票在途的，发票在途金额
			BigDecimal onroadamount = unClearCustomBill.getOnroadamount();
			String contractNo = unClearCustomBill.getContract_no();

			// 遍历 未清完的付款单明细
			for (int j = 0; j < unClearCollectList.size(); j++)
			{
				UnClearCollect unClearCollect = unClearCollectList.get(j);
				// 发票总金额-发票已收款-发票在途金额-清帐金额
				BigDecimal billreaminamount = receivableamount.subtract(receivedamount).subtract(onroadamount).subtract(unClearCustomBill.getClearamount());

				// 取得相同合同编号的数据进行自动分配金额
				if (contractNo.equalsIgnoreCase(unClearCollect.getContract_no()))
				{
					// 本次已清金额
					if (unClearCollect.getClearedamount() == null)
						unClearCollect.setClearedamount(BigDecimal.valueOf(0));

					// 金额
					BigDecimal goodsAmount = unClearCollect.getAmount();
					// 本次抵消金额
					BigDecimal nowclearamount = new BigDecimal(0);
					if(unClearCollect.getNowclearamount() != null && unClearCollect.getNowclearamount().compareTo(BigDecimal.valueOf(0)) != 0){
						
					}else{
						unClearCollect.setNowclearamount(nowclearamount);
					}
					// 已清金额
					BigDecimal offsetamount = unClearCollect.getOffsetamount();
					// 在批金额
					BigDecimal onroadamount1 = unClearCollect.getOnroadamount();
					// 未清金额, = 金额 - 已清金额
					BigDecimal unoffsetamount = unClearCollect.getUnoffsetamount();

					// 收款单剩余可用金额 = 总金额-已清金额-在批金额- 本次已清金额(累加)
					BigDecimal collectRemainAmount = goodsAmount.subtract(offsetamount).subtract(onroadamount1).subtract(unClearCollect.getClearedamount());
					if (collectRemainAmount.compareTo(BigDecimal.valueOf(0)) == 0)
						continue;

					// 发票剩余被清金额 > 收款单剩余可用金额
					if (billreaminamount.compareTo(collectRemainAmount) == 1 || billreaminamount.compareTo(collectRemainAmount) == 0)
					{
						unClearCustomBill.setClearamount(unClearCustomBill.getClearamount().add(collectRemainAmount));
						unClearCollect.setClearedamount(unClearCollect.getClearedamount().add(collectRemainAmount));
						unClearCollect.setNowclearamount(unClearCollect.getClearedamount());
					}
					else
					{
						unClearCustomBill.setClearamount(unClearCustomBill.getClearamount().add(billreaminamount));
						unClearCollect.setClearedamount(unClearCollect.getClearedamount().add(billreaminamount));
						unClearCollect.setNowclearamount(unClearCollect.getClearedamount());

					}

				}

			}

		}

		MultyGridData gridJsonData = new MultyGridData();
		gridJsonData.setData(unClearCustomBillList.toArray());
		gridJsonData.setData2(unClearCollectList.toArray());
		JSONObject jsonList = JSONObject.fromObject(gridJsonData);

		return jsonList;
	}

	/**
	 * 根据选择的未清客户编号，查询出未清客户下所有未清发票信息。
	 */
	public JSONObject _queryUnClear(String custom, String subject)
	{
		// 根据客户，取得客户下未清发票数据集合
		List<CustomerTitle> customerTitleList = this.unclearCustomerService.getCustomerTitleListBySupplier(custom, subject, "");
		// 未清应收（借方）
		List<UnClearCustomBill> unClearCustomBillList = new ArrayList<UnClearCustomBill>();
		// 未清收款（贷方）
		List<UnClearCollect> unClearCollectList = new ArrayList<UnClearCollect>();

		this.getUnClearInfo(customerTitleList, unClearCustomBillList, unClearCollectList);

		MultyGridData gridJsonData = new MultyGridData();
		gridJsonData.setData(unClearCustomBillList.toArray());
		gridJsonData.setData2(unClearCollectList.toArray());
		JSONObject jsonList = JSONObject.fromObject(gridJsonData);

		return jsonList;
	}
	
	/**
	 * 根据选择的未清客户编号。
	 */
	public JSONObject _queryUnClear(String customertitleids)
	{
		// 根据客户，取得客户下未清发票数据集合
		List<CustomerTitle> customerTitleList = new ArrayList<CustomerTitle>();
		if (!("").equals(customertitleids) && customertitleids != null)
		{
			customerTitleList = this.unclearCustomerService.getUnclearCustomerInvoiceList(customertitleids);
		}
		// 未清应收（借方）
		List<UnClearCustomBill> unClearCustomBillList = new ArrayList<UnClearCustomBill>();
		// 未清收款（贷方）
		List<UnClearCollect> unClearCollectList = new ArrayList<UnClearCollect>();

		this.getUnClearInfo(customerTitleList, unClearCustomBillList, unClearCollectList);

		MultyGridData gridJsonData = new MultyGridData();
		if(unClearCustomBillList.size()!=0){
			gridJsonData.setData(unClearCustomBillList.toArray());
		}
		if(unClearCollectList.size() != 0){
			gridJsonData.setData(unClearCollectList.toArray());
		}
		JSONObject jsonList = JSONObject.fromObject(gridJsonData);

		return jsonList;
	}
	
	/**
	 * 根据选择的未清客户编号，科目，货币，公司代码,业务范围，查询出未清客户下所有未清发票信息。
	 */
	public JSONObject _queryUnClear(String custom, String subject ,String currencytext ,String companyno ,String depid)
	{
		// 根据客户，取得客户下未清发票数据集合
		List<CustomerTitle> customerTitleList = this.unclearCustomerService.getCustomerTitleListBySupplier(custom, subject,currencytext,companyno,depid, "");
		// 未清应收（借方）
		List<UnClearCustomBill> unClearCustomBillList = new ArrayList<UnClearCustomBill>();
		// 未清收款（贷方）
		List<UnClearCollect> unClearCollectList = new ArrayList<UnClearCollect>();

		this.getUnClearInfo(customerTitleList, unClearCustomBillList, unClearCollectList);

		MultyGridData gridJsonData = new MultyGridData();
		gridJsonData.setData(unClearCustomBillList.toArray());
		gridJsonData.setData2(unClearCollectList.toArray());
		JSONObject jsonList = JSONObject.fromObject(gridJsonData);

		return jsonList;
	}

	/**
	 * 
	 * @param customerTitleList
	 * @param unClearSupplieBillList
	 * @param unClearPaymentList
	 */
	private void getUnClearInfo(List<CustomerTitle> customerTitleList, List<UnClearCustomBill> unClearCustomBillList, List<UnClearCollect> unClearCollectList)
	{
		// 遍历所有客户下未清发票数据集合
		for (Iterator<CustomerTitle> it = customerTitleList.iterator(); it.hasNext();)
		{
			CustomerTitle customerTitle = (CustomerTitle) it.next();

			String shkzg = customerTitle.getShkzg();
			if (StringUtils.isNullBlank(shkzg))
			{
				continue;
			}

			// 会计年度
			String fiYear = customerTitle.getGjahr();
			// 财务凭证号
			String voucherNo = customerTitle.getBelnr();
			// 公司代码
			String companyCode = customerTitle.getBukrs();
			String rowNumber = customerTitle.getBuzei();
			
			String customertitleid = customerTitle.getCustomertitleid();

			// 未清应收（借方）
			if (shkzg.equals("S"))
			{
				String invoice = customerTitle.getInvoice();
				// 如果发票号为空，则为纯代理情况。需要取得付款ID。
				if (!StringUtils.isNullBlank(invoice))
				{
					UnClearCustomBill unClearCustomBill = new UnClearCustomBill();
					unClearCustomBill.setBillno(customerTitle.getInvoice());
					unClearCustomBill.setCurrency(customerTitle.getWaers());
					unClearCustomBill.setVoucherno(customerTitle.getBelnr());
					unClearCustomBill.setProject_no(customerTitle.getBname());
					unClearCustomBill.setContract_no(customerTitle.getIhrez());
					unClearCustomBill.setOld_contract_no(this.collectItemService.getOldContractNo(customerTitle.getIhrez().trim())); // 纸质合同号码
					unClearCustomBill.setSap_order_no(customerTitle.getVgbel()); // sap订单号,销售凭证号
					unClearCustomBill.setText(customerTitle.getBktxt());
					unClearCustomBill.setAccountdate(customerTitle.getBudat());
					unClearCustomBill.setPaymentid(" ");
					unClearCustomBill.setPaymentitemid(" ");
					unClearCustomBill.setCustomertitleid(customertitleid);
					unClearCustomBill.setPaymentno(" ");
					unClearCustomBill.setBwbje(customerTitle.getBwbje());
					// 开票发票总金额,应付款.
					BigDecimal billamount = customerTitle.getDmbtr();
					unClearCustomBill.setReceivableamount(billamount);

					// 开票发票已经审批完的，发票已收款
					BigDecimal receivedamount = this.unclearCustomerService.getSumClearAmount(customerTitle.getInvoice().trim(), BusinessState.ALL_COLLECT_PAIDUP);
					unClearCustomBill.setReceivedamount(receivedamount);
					// 开票发票在途的，发票在途金额
					BigDecimal onroadamount = this.unclearCustomerService.getSumClearAmount(customerTitle.getInvoice().trim(), BusinessState.ALL_COLLECT_ONROAD);
					unClearCustomBill.setOnroadamount(onroadamount);
					
					// 未收款=开票发票金额-发票已收款-在批
					unClearCustomBill.setUnreceivableamou(billamount.subtract(receivedamount).subtract(onroadamount));
					//未收款为0不显示
					if(unClearCustomBill.getUnreceivableamou().compareTo(BigDecimal.valueOf(0)) <= 0 && unClearCustomBill.getReceivableamount().compareTo(BigDecimal.valueOf(0)) != 0){
						continue;
					}
					unClearCustomBill.setClearamount(unClearCustomBill.getUnreceivableamou());
					unClearCustomBill.setAdjustamount(BigDecimal.valueOf(0));
					//汇率
					BigDecimal rat = new BigDecimal(1);
					if(unClearCustomBill.getReceivableamount().compareTo(BigDecimal.valueOf(0)) != 0){
						rat = customerTitle.getBwbje().divide(billamount,5,BigDecimal.ROUND_HALF_EVEN);
//						设置还没有部分清的取应付款的本位币
						if(unClearCustomBill.getReceivableamount().compareTo(unClearCustomBill.getUnreceivableamou()) ==0){
							unClearCustomBill.setUnbwbje(unClearCustomBill.getBwbje());
						}else{
						
						unClearCustomBill.setUnbwbje(unClearCustomBill.getUnreceivableamou().multiply(rat).setScale(2, BigDecimal.ROUND_HALF_UP));
						}
						
					}else{
						unClearCustomBill.setUnbwbje(unClearCustomBill.getBwbje());
					}
					unClearCustomBill.setExchangerate(rat);
					
					unClearCustomBillList.add(unClearCustomBill);
				}
				else
				{
					
					// 付款ID
					String businessId = this.voucherJdbcDao.getBusinessId(voucherNo, companyCode, fiYear, rowNumber);
//					if (StringUtils.isNullBlank(businessId))
//						continue;

					// 付款信息
					ImportPayment payment = this.importPaymentHibernateDao.getDetached(businessId);
					
					//  没有付款信息的情况
					if (null == payment)
					{
						UnClearCustomBill unClearCustomBill = new UnClearCustomBill();
						unClearCustomBill.setExchangerate(new BigDecimal(1));
						unClearCustomBill.setBillno(customerTitle.getInvoice());
						unClearCustomBill.setCurrency(customerTitle.getWaers());
						unClearCustomBill.setVoucherno(customerTitle.getBelnr());
						unClearCustomBill.setProject_no(customerTitle.getBname());
						unClearCustomBill.setContract_no(customerTitle.getIhrez());
						unClearCustomBill.setOld_contract_no(this.paymentItemService.getOldContractNo(customerTitle.getIhrez())); // 纸质合同号码
						unClearCustomBill.setSap_order_no(customerTitle.getVgbel()); // sap订单号,销售凭证号
						unClearCustomBill.setText(customerTitle.getBktxt());
						unClearCustomBill.setAccountdate(customerTitle.getBudat());
						unClearCustomBill.setPaymentid(" ");
						unClearCustomBill.setPaymentitemid(" ");
						unClearCustomBill.setCustomertitleid(customertitleid);
						unClearCustomBill.setPaymentno(" ");
						unClearCustomBill.setBwbje(customerTitle.getBwbje());
						
						// 已收金额
						BigDecimal goodsAmount = customerTitle.getDmbtr();
						unClearCustomBill.setReceivableamount(goodsAmount);
						// 本次抵消金额
//						BigDecimal clearamount = new BigDecimal(0);
//						unClearCustomBill.setClearamount(clearamount);
						// 已清金额
						BigDecimal receivedamount = getSumClearAmount2(customertitleid.trim(), BusinessState.ALL_COLLECT_PAIDUP);
						unClearCustomBill.setReceivedamount(receivedamount);
						// 在批金额
						BigDecimal onroadamount1 = getSumClearAmount2(customertitleid.trim(), BusinessState.ALL_COLLECT_ONROAD);
						unClearCustomBill.setOnroadamount(onroadamount1);
						// 未清金额, = 金额 - 已清金额-在批
						BigDecimal unreceivableamou = goodsAmount.subtract(receivedamount).subtract(onroadamount1);
						unClearCustomBill.setUnreceivableamou(unreceivableamou);
						//未清金额为0不显示
						if(unClearCustomBill.getUnreceivableamou().compareTo(BigDecimal.valueOf(0)) == 0 && unClearCustomBill.getReceivableamount().compareTo(BigDecimal.valueOf(0)) != 0){
							continue;
						}
						
						unClearCustomBill.setClearamount(unClearCustomBill.getUnreceivableamou());
						//汇率
						BigDecimal rat = new BigDecimal(1);
						if(unClearCustomBill.getReceivableamount().compareTo(BigDecimal.valueOf(0)) != 0){
							rat = customerTitle.getBwbje().divide(goodsAmount,5,BigDecimal.ROUND_HALF_EVEN);
//							设置还没有部分清的取应付款的本位币
							if(unClearCustomBill.getReceivableamount().compareTo(unClearCustomBill.getUnreceivableamou()) ==0){
								unClearCustomBill.setUnbwbje(unClearCustomBill.getBwbje());
							}else{
							
							unClearCustomBill.setUnbwbje(unClearCustomBill.getUnreceivableamou().multiply(rat).setScale(2, BigDecimal.ROUND_HALF_UP));
							}
							
						}else{
							unClearCustomBill.setUnbwbje(unClearCustomBill.getBwbje());
						}
						unClearCustomBill.setExchangerate(rat);
						
						unClearCustomBillList.add(unClearCustomBill);
						continue;
					}
					String paymentBUsinessState = payment.getBusinessstate();
					// 付款为作废不显示
					if (!StringUtils.isNullBlank(paymentBUsinessState) && BusinessState.BLANKOUT.equals(paymentBUsinessState)){
						continue;
					}
					
					// 收款已经审批通过，付款单状态为 正常、重做。
//					if (!StringUtils.isNullBlank(paymentBUsinessState) && BusinessState.ALL_SUBMITPASS.indexOf("'" + paymentBUsinessState + "'") >= 0){
						Set<ImportPaymentItem> paymentItemList = payment.getImportPaymentItem();
						// 遍历 未清完的付款单明细
						Iterator<ImportPaymentItem> itPaymentItem = paymentItemList.iterator();
						while (itPaymentItem.hasNext())
						{
							ImportPaymentItem importPaymentItem = (ImportPaymentItem) itPaymentItem.next();
							// 如果行项已经打上结清标识
							if (Constants.cleared.isCleared.equals(importPaymentItem.getBillisclear()) || Constants.cleared.sapIsCleared.equals(importPaymentItem.getBillisclear()))
							{
								continue;
							}
	
							// 本次已清金额
							if (importPaymentItem.getClearedamount() == null)
								importPaymentItem.setClearedamount(BigDecimal.valueOf(0));
	
							String paymentItemId = importPaymentItem.getPaymentitemid();
							UnClearCustomBill unClearCustomBill = new UnClearCustomBill();
							unClearCustomBill.setAccountdate(customerTitle.getBudat());
							unClearCustomBill.setContract_no(importPaymentItem.getContract_no());
							unClearCustomBill.setProject_no(importPaymentItem.getProject_no());
							unClearCustomBill.setCurrency(customerTitle.getWaers());
//							unClearCustomBill.setExchangerate(payment.getFactexchangerate());
							unClearCustomBill.setOld_contract_no(importPaymentItem.getOld_contract_no());
							unClearCustomBill.setPaymentid(payment.getPaymentid());
							unClearCustomBill.setPaymentitemid(paymentItemId);
							unClearCustomBill.setPaymentno(payment.getPaymentno());
							unClearCustomBill.setCustomertitleid(customertitleid);
							unClearCustomBill.setBwbje(customerTitle.getBwbje());
							unClearCustomBill.setText(customerTitle.getBktxt());
							unClearCustomBill.setVoucherno(customerTitle.getBelnr());
							// 已收金额
//							BigDecimal goodsAmount = importPaymentItem.getGoodsamount();
							BigDecimal goodsAmount = customerTitle.getDmbtr();
							unClearCustomBill.setReceivableamount(goodsAmount);
							// 本次抵消金额
							BigDecimal clearamount = new BigDecimal(0);
							unClearCustomBill.setClearamount(clearamount);
							// 已清金额
							BigDecimal receivedamount = this.paymentItemService.getSumClearAmount(paymentItemId, BusinessState.ALL_SUBMITPASS);
							unClearCustomBill.setReceivedamount(receivedamount);
							// 在批金额
							BigDecimal onroadamount1 = this.paymentItemService.getSumClearAmount(paymentItemId, BusinessState.ALL_ONROAD);
							unClearCustomBill.setOnroadamount(onroadamount1);
							// 未清金额, = 金额 - 已清金额-在批
							BigDecimal unreceivableamou = goodsAmount.subtract(receivedamount).subtract(onroadamount1);
							unClearCustomBill.setUnreceivableamou(unreceivableamou);
							//未清金额为0不显示
							if(unClearCustomBill.getUnreceivableamou().compareTo(BigDecimal.valueOf(0)) <= 0 && unClearCustomBill.getReceivableamount().compareTo(BigDecimal.valueOf(0)) != 0){
								continue;
							}
							unClearCustomBill.setClearamount(unClearCustomBill.getUnreceivableamou());
							unClearCustomBill.setAdjustamount(BigDecimal.valueOf(0));
							//汇率
							BigDecimal rat = new BigDecimal(1);
							if(unClearCustomBill.getReceivableamount().compareTo(BigDecimal.valueOf(0)) != 0){
								rat = customerTitle.getBwbje().divide(goodsAmount,5,BigDecimal.ROUND_HALF_EVEN);
//								设置还没有部分清的取应付款的本位币
								if(unClearCustomBill.getReceivableamount().compareTo(unClearCustomBill.getUnreceivableamou()) ==0){
									unClearCustomBill.setUnbwbje(unClearCustomBill.getBwbje());
								}else{
								
								unClearCustomBill.setUnbwbje(unClearCustomBill.getUnreceivableamou().multiply(rat).setScale(2, BigDecimal.ROUND_HALF_UP));
								}
								
							}else{
								unClearCustomBill.setUnbwbje(unClearCustomBill.getBwbje());
							}
							unClearCustomBill.setExchangerate(rat);
							
							unClearCustomBillList.add(unClearCustomBill);
						}
//					}

				}

			}
			// 未清收款（贷方）
			else if (shkzg.equals("H"))
			{
				// 收款ID
				String businessId = this.voucherJdbcDao.getBusinessId(voucherNo, companyCode, fiYear, rowNumber);
//				if (StringUtils.isNullBlank(businessId))
//					continue;
			
				String businessItemId = this.voucherItemJdbcDao.getBusinessId(voucherNo, companyCode, fiYear, rowNumber);
				if (StringUtils.isNullBlank(businessItemId))
				{
					
					UnClearCollect unClearCollect = new UnClearCollect();
					unClearCollect.setAccountdate(customerTitle.getBudat());
					unClearCollect.setContract_no(customerTitle.getIhrez());
					unClearCollect.setProject_no(customerTitle.getBname());
					unClearCollect.setCurrency(customerTitle.getWaers());
					unClearCollect.setExchangerate(new BigDecimal(1));
					unClearCollect.setOld_contract_no(" ");
					unClearCollect.setCollectno(" ");
					unClearCollect.setCollectitemid(" ");
					unClearCollect.setCollectid(" ");
					unClearCollect.setBktxt(customerTitle.getBktxt());
					unClearCollect.setVoucherno(voucherNo);
					unClearCollect.setCustomertitleid(customertitleid);
					unClearCollect.setBwbje(customerTitle.getBwbje());
					// 金额
					BigDecimal goodsAmount = customerTitle.getDmbtr();
					unClearCollect.setAmount(goodsAmount);
					
					// 已清金额
					BigDecimal offsetamount = getSumClearAmount(customertitleid.trim(), BusinessState.ALL_COLLECT_PAIDUP);
					unClearCollect.setOffsetamount(offsetamount);
					// 在批金额
					BigDecimal onroadamount = getSumClearAmount(customertitleid.trim(),BusinessState.ALL_COLLECT_ONROAD);
					unClearCollect.setOnroadamount(onroadamount);
					// 未抵消金额, = 金额 - 已清金额-在批金额
					BigDecimal unoffsetamount = goodsAmount.subtract(offsetamount).subtract(onroadamount);
					unClearCollect.setUnoffsetamount(unoffsetamount);
					//未抵消金额为0不显示
					if(unClearCollect.getUnoffsetamount().compareTo(BigDecimal.valueOf(0)) <= 0 && unClearCollect.getAmount().compareTo(BigDecimal.valueOf(0)) != 0){
						continue;
					}
					//判断sap手工调汇率，是否有清账
					if(unClearCollect.getAmount().compareTo(BigDecimal.valueOf(0)) == 0 && customSingleClearJdbcDao.getSapCollect(customertitleid.trim())){
						continue;
					}
					// 本次抵消金额
					BigDecimal nowclearamount = new BigDecimal(0);
					unClearCollect.setNowclearamount(unClearCollect.getUnoffsetamount());
					//汇率
					BigDecimal rat = new BigDecimal(1);
					if(unClearCollect.getAmount().compareTo(BigDecimal.valueOf(0)) != 0){
						rat = customerTitle.getBwbje().divide(goodsAmount,5,BigDecimal.ROUND_HALF_EVEN);
//						设置还没有部分清的取应付款的本位币
						if(unClearCollect.getAmount().compareTo(unClearCollect.getUnoffsetamount()) ==0){
							unClearCollect.setUnbwbje(unClearCollect.getBwbje());
						}else{
						
						unClearCollect.setUnbwbje(unClearCollect.getUnoffsetamount().multiply(rat).setScale(2, BigDecimal.ROUND_HALF_UP));
						}
						
					}else{
						unClearCollect.setUnbwbje(unClearCollect.getBwbje());
					}
					unClearCollect.setExchangerate(rat);
					
					unClearCollectList.add(unClearCollect);
					continue;
				}

				// 收款信息
//				Collect collect = this.collectHibernateDao.getDetached(businessId);
				CollectItem collectItem = this.collectItemJdbcDao.getCollectItem(businessItemId);
				Collect collect = collectItem.getCollect();
				String collectBUsinessState = collect.getBusinessstate();
				// 收款为作废不显示
				if (!StringUtils.isNullBlank(collectBUsinessState) && BusinessState.BLANKOUT.equals(collectBUsinessState)){
					continue;
				}
				// 收款已经审批通过，付款单状态为 正常、重做。
//				if (!StringUtils.isNullBlank(collectBUsinessState) && BusinessState.COLLECTSUBMITPASS.equals(collectBUsinessState)){
//					Set<CollectItem> collectItems = collect.getCollectitem();
//					// 遍历 未清完的付款单明细
//					Iterator<CollectItem> itCollectItem = collectItems.iterator();
//					while (itCollectItem.hasNext())
//					{
//						CollectItem collectItem = (CollectItem) itCollectItem.next();
						// 如果行项已经打上结清标识
						if (Constants.cleared.isCleared.equals(collectItem.getIsclear()) || Constants.cleared.sapIsCleared.equals(collectItem.getIsclear()))
						{
							continue;
						}

						String collectItemId = collectItem.getCollectitemid();
						if(!StringUtils.isNullBlank(businessItemId) && !collectItemId.equals(businessItemId)){
							continue;
						}
						UnClearCollect unClearCollect = new UnClearCollect();
						unClearCollect.setAccountdate(customerTitle.getBudat());
						unClearCollect.setContract_no(collectItem.getContract_no());
						unClearCollect.setProject_no(collectItem.getProject_no());
						unClearCollect.setCurrency(customerTitle.getWaers());//取未清的币别
						unClearCollect.setExchangerate(collect.getCollectrate());
						unClearCollect.setOld_contract_no(this.collectItemService.getOldContractNo(collectItem.getContract_no()));
						unClearCollect.setCollectno(collect.getCollectno());
						unClearCollect.setCollectitemid(collectItemId);
						unClearCollect.setCollectid(collect.getCollectid());
						unClearCollect.setVoucherno(voucherNo);
						unClearCollect.setCustomertitleid(customertitleid);
						
						
						unClearCollect.setBktxt(customerTitle.getBktxt());
						//在批的退款保证金
						BigDecimal onroadSuretybond = this.collectItemService.getSumClearAmountByRefundment(collectItemId.trim(), BusinessState.ALL_COLLECT_ONROAD);
						//实际保证金-在批的退款保证金
						unClearCollect.setSuretybond(collectItem.getActsuretybond().subtract(onroadSuretybond));
						// 金额
						BigDecimal goodsAmount = collectItem.getAssignamount();
						unClearCollect.setAmount(goodsAmount);
						// 本次抵消金额
						BigDecimal nowclearamount = new BigDecimal(0);
//						unClearCollect.setNowclearamount(nowclearamount);
						// 已清金额
						//3为审批通过
						BigDecimal offsetamount = this.collectItemService.getSumClearAmount(collectItemId.trim(), BusinessState.ALL_COLLECT_PAIDUP);
						unClearCollect.setOffsetamount(offsetamount);
						// 在批金额
						//1,2为在批
						BigDecimal onroadamount = this.collectItemService.getSumClearAmount(collectItemId.trim(), BusinessState.ALL_COLLECT_ONROAD);
						unClearCollect.setOnroadamount(onroadamount);
						// 未抵消金额, = 金额 - 已清金额-在批金额
						BigDecimal unoffsetamount = goodsAmount.subtract(offsetamount).subtract(onroadamount);
						
						unClearCollect.setUnoffsetamount(unoffsetamount.subtract(unClearCollect.getSuretybond()));
						//未抵消金额为0不显示
						if(unClearCollect.getUnoffsetamount().compareTo(BigDecimal.valueOf(0)) <= 0 && unClearCollect.getAmount().compareTo(BigDecimal.valueOf(0)) != 0){
							continue;
						}
						unClearCollect.setNowclearamount(unClearCollect.getUnoffsetamount());
						//汇率
						BigDecimal rat = new BigDecimal(1);
						if(unClearCollect.getAmount().compareTo(BigDecimal.valueOf(0)) != 0){
							rat = customerTitle.getBwbje().divide(goodsAmount,5,BigDecimal.ROUND_HALF_EVEN);
						}
						unClearCollect.setExchangerate(rat);
						unClearCollect.setBwbje(customerTitle.getBwbje());
						//判断第一次清帐，已抵消收款为0，且在批抵消收款为0，且有保证金的情况。
						if(onroadamount.compareTo(new BigDecimal(0))==0 && offsetamount.compareTo(new BigDecimal(0))==0 && unClearCollect.getSuretybond().compareTo(new BigDecimal(0))!=0){
							//未收款本位币，如果第一次清帐有保证金要减去保证金，如果是外币还在算汇率。
							if("CNY".equals(collect.getCurrency())){
								unClearCollect.setUnbwbje(customerTitle.getBwbje().subtract(unClearCollect.getSuretybond()));
							}else{
								
								//保证金的本位币
								BigDecimal suretybond2 = unClearCollect.getSuretybond().multiply(rat);
								//未收款本位币
								unClearCollect.setUnbwbje(unoffsetamount.multiply(rat).subtract(suretybond2).setScale(2, BigDecimal.ROUND_HALF_UP));
								
							}
						}else{
//							设置还没有部分清的取应付款的本位币
							if(unClearCollect.getAmount().compareTo(unClearCollect.getUnoffsetamount()) ==0){
								unClearCollect.setUnbwbje(unClearCollect.getBwbje());
							}else{
								unClearCollect.setUnbwbje(unClearCollect.getUnoffsetamount().multiply(rat).setScale(2, BigDecimal.ROUND_HALF_UP));
							}
						}
						
						if(unClearCollect.getAmount().compareTo(BigDecimal.valueOf(0)) == 0){
							unClearCollect.setUnbwbje(unClearCollect.getBwbje());
						}
						unClearCollectList.add(unClearCollect);
//					}
//				}

			}
		}
	}

	
	/**
	 * 根据业务状态、customertitleid，取得款已经审批完金额(款已清金额)、在途金额(款在途金额)。
	 * 
	 * @param customertitleid
	 * @param businessstates
	 * @return
	 */
	public BigDecimal getSumClearAmount(String customertitleid, String businessstates)
	{
		
		return this.customSingleClearJdbcDao.getSumClearAmount(customertitleid, businessstates);
	}	
	
	/**
	 * 根据业务状态、customertitleid，取得票已经审批完金额(款已清金额)、在途金额(款在途金额)。
	 * 
	 * @param customertitleid
	 * @param businessstates
	 * @return
	 */
	public BigDecimal getSumClearAmount2(String customertitleid, String businessstates)
	{
		
		return this.customSingleClearJdbcDao.getSumClearAmount2(customertitleid, businessstates);
	}	
	
	/**
	 * 客户未清抬头数据中的数据，判断是否已结清
	 * 
	 * @param supplierSinglClear
	 */
	public void updateCustomerTitleIsCleared(CustomSingleClear customSingleClear)
	{
		// 客户单清，未清收款(贷方)
		Set<UnClearCollect> unClearCollects = customSingleClear.getUnClearCollect();
		Set<Map<String, String>> collectIds = new HashSet<Map<String, String>>();
		String customerclearid = customSingleClear.getCustomsclearid();
		if (null != unClearCollects)
		{
			Iterator<UnClearCollect> itUnClearCollect = unClearCollects.iterator();
			while (itUnClearCollect.hasNext())
			{
				UnClearCollect unClearCollect = (UnClearCollect) itUnClearCollect.next();
				
				if(!StringUtils.isNullBlank(unClearCollect.getCollectid())){
					Map map = new HashMap<String, String>();				
					map.put("CUSTOMERTITLEID", unClearCollect.getCustomertitleid());
					map.put("COLLECTID", unClearCollect.getCollectid());
					collectIds.add(map);
				}
				
			}
		}

		if (null != collectIds && collectIds.size() > 0)
		{
			for (Map<String, String> map : collectIds)
			{
				String customertitleid = map.get("CUSTOMERTITLEID");
				String collectid = map.get("COLLECTID");
				// 收款信息
//				Collect collect = this.collectHibernateDao.getDetached(collectid);
				Collect collect = collectJdbcDao.getCollectById(collectid);
				
				Set<CollectItem> collectItems = collect.getCollectitem();
				int i = collectItems.size();
				int j = 0;
				// 遍历 未清完的付款单明细
				for (CollectItem collectItem : collectItems)
				{
					if (Constants.cleared.isCleared.equals(collectItem.getIsclear()) || Constants.cleared.sapIsCleared.equals(collectItem.getIsclear()))
					{
						log.debug("collectItemId:" + collectItem.getCollectitemid() + ";Isclear:" + collectItem.getIsclear());
						j = j + 1;
					}

				}
				log.debug("COLLECTID:" + collectid + " ; CUSTOMERTITLEID:" + customertitleid + ",collectItems.size():" + i + ",j:" + j);
				if (i > 0 && i == j)
				{
					if(checkVoucher(customerclearid,"A")){
						this.unclearCustomerService.updateCustomerTitleIsCleared(customertitleid, Constants.cleared.sapIsCleared);
					}else{
						this.unclearCustomerService.updateCustomerTitleIsCleared(customertitleid, Constants.cleared.isCleared);
					}
				}
			}

		}

		// 客户单清， 未清应收(借方)
		Set<UnClearCustomBill> unClearCustomBills = customSingleClear.getUnClearCustomBill();
		Set<Map<String, String>> paymentIds = new HashSet<Map<String, String>>();
		if (null != unClearCustomBills)
		{
			Iterator<UnClearCustomBill> itUnClearCustomBill = unClearCustomBills.iterator();
			while (itUnClearCustomBill.hasNext())
			{
				UnClearCustomBill unClearCustomBill = (UnClearCustomBill) itUnClearCustomBill.next();
				String paymentid = unClearCustomBill.getPaymentid();
				if (!StringUtils.isNullBlank(paymentid))
				{
					Map map = new HashMap<String, String>();
					map.put("PAYMENTID", paymentid);
					map.put("CUSTOMERTITLEID", unClearCustomBill.getCustomertitleid());					
					paymentIds.add(map);
				}
			}
		}

		if (null != paymentIds && paymentIds.size() > 0)
		{
			for (Map<String, String> map : paymentIds)
			{
				String customertitleid = map.get("CUSTOMERTITLEID");
				// 付款信息
				ImportPayment payment = this.importPaymentHibernateDao.getDetached(map.get("PAYMENTID"));
				Set<ImportPaymentItem> paymentItemList = payment.getImportPaymentItem();
				int i = paymentItemList.size();
				int j = 0;
				// 遍历 未清完的付款单明细
				for (ImportPaymentItem paymentItem : paymentItemList)
				{
					if (Constants.cleared.isCleared.equals(paymentItem.getBillisclear()) || Constants.cleared.sapIsCleared.equals(paymentItem.getBillisclear()))
					{
						j = j + 1;
					}
				}
				log.debug("PAYMENTID:" + map.get("PAYMENTID") + " ; CUSTOMERTITLEID:" + customertitleid + ",paymentItemList.size():" + i + ",j:" + j);
				if (i > 0 && i == j)
				{
					if(checkVoucher(customerclearid,"A")){
						this.unclearCustomerService.updateCustomerTitleIsCleared(customertitleid, Constants.cleared.sapIsCleared);
					}else{
						this.unclearCustomerService.updateCustomerTitleIsCleared(customertitleid, Constants.cleared.isCleared);
					}
					
				}
			}

		}
		//更新旧的clear 为sap
		if(checkVoucher(customerclearid,"A")){
			
			this.updateOldTitle(customSingleClear);
		}

	}

	/**
	 * 
	 * 保存
	 * 
	 * @param supplierSinglClear
	 */
	public void _saveOrUpdate(CustomSingleClear customSingleClear, BusinessObject businessObject)
	{
		// 删除所有1比多子对象数据。
		this.customSingleClearJdbcDao.deleteCustomSingleClearUnderOneToManySubObject(customSingleClear.getCustomsclearid());
		_saveOrUpdateCustomSingleClear(customSingleClear);

	}

	/**
	 * 保存
	 * 
	 * @param supplierSinglClear
	 */
	public void _saveOrUpdateCustomSingleClear(CustomSingleClear customSingleClear)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(customSingleClear);
		// 对id赋空值，使之执行insert操作(适应复制创建场景)
		Set<UnClearCustomBill> unClearCustomBillSet = customSingleClear.getUnClearCustomBill();
		Set<UnClearCustomBill> newUnClearCustomBillSet = null;
		//哪一边不能全清
//		String clearFlag = checkAllClear(customSingleClear);
//		//汇率
//		BigDecimal rate = getUnoffAmoutRate(customSingleClear);
		 
		if (null != unClearCustomBillSet)
		{
			newUnClearCustomBillSet = new HashSet();
			Iterator<UnClearCustomBill> itUnClearCustomBill = unClearCustomBillSet.iterator();
			while (itUnClearCustomBill.hasNext())
			{
				UnClearCustomBill unClearCustomBill = (UnClearCustomBill) itUnClearCustomBill.next();
				unClearCustomBill.setUnclearcusbillid(null);
				CustomerTitle customerTitle = customerTitleJdbcDao.getByCustomertitleid(unClearCustomBill.getCustomertitleid());
				// 单据为审批通过即确认清帐，则需要判断票款是否已结清。
				if (BusinessState.COLLECTSUBMITPASS.equals(customSingleClear.getBusinessstate()) )
				{
					String invoice = unClearCustomBill.getBillno();
					String customertitleid = unClearCustomBill.getCustomertitleid();
					String customerclearid = customSingleClear.getCustomsclearid();
					String paymentid = unClearCustomBill.getPaymentid();
					String paymentItemId = unClearCustomBill.getPaymentitemid();
					
					// 如果paymentid为空，则为非纯代理情况。
					if (StringUtils.isNullBlank(paymentid))
					{
						// 当前单据，票清帐金额总计。
						BigDecimal billClearAmount = unClearCustomBill.getClearamount();
						// 发票总金额,应收款
						BigDecimal billTotAmount = unClearCustomBill.getReceivableamount();
						// 票已清金额。
						BigDecimal billsumClearedAmount = this.unclearCustomerService.getSumClearAmount(invoice, BusinessState.ALL_COLLECT_PAIDUP);

						// 票已清金额+当前单据上清票金额 = 票总金额，则票已经结清。
						if (billsumClearedAmount.add(billClearAmount).compareTo(billTotAmount) == 0)
						{
							log.debug("发票号:" + invoice + "，票已清金额+当前单据上清票金额 = 票总金额:" + billsumClearedAmount + "+" + billClearAmount + " = " + billTotAmount);
							//如果是清帐凭证，iscleared为2
							if(checkVoucher(customerclearid,"A")){
								this.unclearCustomerService.updateCustomerTitleIsCleared(customertitleid, Constants.cleared.sapIsCleared);
								
							}else{
								this.unclearCustomerService.updateCustomerTitleIsCleared(customertitleid, Constants.cleared.isCleared);
							}
						}
					}
					// 如果paymentid不为空，则为纯代理情况。
					else
					{
						// 付款行项目，金额。
						BigDecimal paymentamount = unClearCustomBill.getReceivableamount();
						// 付款行项目，已清金额。
						BigDecimal offsetamount = this.paymentItemService.getSumClearAmount(paymentItemId, BusinessState.ALL_SUBMITPASS);
						// 本次抵消金额
						BigDecimal nowclearamount = unClearCustomBill.getClearamount();

						// 款已清金额+当前单据上清票金额 = 款总金额，则票已经结清。
						if (offsetamount.add(nowclearamount).compareTo(paymentamount) == 0)
						{
							log.debug("paymentItemId:" + paymentItemId + "，款已清金额+当前单据上清帐金额 = 款分配行项总金额:" + offsetamount + "+" + nowclearamount + " = " + paymentamount);
							
							
							if(checkVoucher(customerclearid,"A")){
								this.unclearCustomerService.updateCustomerTitleIsCleared(customertitleid, Constants.cleared.sapIsCleared);
								this.paymentItemService.updatePaymentItemIsCleared(paymentItemId, Constants.cleared.sapIsCleared);
							}else{
								this.unclearCustomerService.updateCustomerTitleIsCleared(customertitleid, Constants.cleared.isCleared);
								this.paymentItemService.updatePaymentItemIsCleared(paymentItemId, Constants.cleared.isCleared);
							}
						}
					}
					
//					if("billClear".equals(clearFlag)){
//						//未收款本位币
//						BigDecimal unoff = unClearCustomBill.getBwbje();
//						//本次清帐金额本位币
//						BigDecimal clearBwbje = unClearCustomBill.getClearamount().multiply(rate);
//						customerTitle.setBwbje(unoff.subtract(clearBwbje));
//						//更新未收款本位币
//						customerTitleHibernateDao.saveOrUpdate(customerTitle);
//					}
					
				}

				newUnClearCustomBillSet.add(unClearCustomBill);
			}
		}
		customSingleClear.setUnClearCustomBill(newUnClearCustomBillSet);

		Set<UnClearCollect> unClearCollectSet = customSingleClear.getUnClearCollect();
		Set<UnClearCollect> newUnClearCollectSet = null;
		if (null != unClearCollectSet)
		{
			newUnClearCollectSet = new HashSet();
			Iterator<UnClearCollect> itUnClearCollect = unClearCollectSet.iterator();
			while (itUnClearCollect.hasNext())
			{
				UnClearCollect unClearCollect = (UnClearCollect) itUnClearCollect.next();
				unClearCollect.setUnclearcollectid(null);
				CustomerTitle customerTitle = customerTitleJdbcDao.getByCustomertitleid(unClearCollect.getCustomertitleid());
				// 单据为审批通过即确认清帐，则需要判断票款是否已结清。
				if (BusinessState.COLLECTSUBMITPASS.equals(customSingleClear.getBusinessstate()) )
				{
					String customertitleid = unClearCollect.getCustomertitleid();
					String customerclearid = customSingleClear.getCustomsclearid();
					String collectItemId = unClearCollect.getCollectitemid();
					// 本次已抵消收款
					BigDecimal nowclearamount = unClearCollect.getNowclearamount();
					// 收款分配表行项金额
					BigDecimal collectAmount = unClearCollect.getAmount();
					// 已清金额
					BigDecimal offsetamount = this.collectItemService.getSumClearAmount(collectItemId, BusinessState.ALL_COLLECT_PAIDUP);
					// 已清金额+当前单据上本次已抵消收款 = 收款分配表行项金额，则款已经结清。
					if (offsetamount.add(nowclearamount).compareTo(collectAmount) == 0)
					{
						log.debug("collectItemId:" + collectItemId + "，已清金额+当前单据上本次已抵消收款 = 收款分配表行项金额:" + offsetamount + "+" + nowclearamount + " = " + collectAmount);
						
						if(checkVoucher(customerclearid,"A")){
							this.unclearCustomerService.updateCustomerTitleIsCleared(customertitleid, Constants.cleared.sapIsCleared);
							this.collectItemService.updateCollectItemIsCleared(collectItemId, Constants.cleared.sapIsCleared);
						}else{
							this.unclearCustomerService.updateCustomerTitleIsCleared(customertitleid, Constants.cleared.isCleared);
							this.collectItemService.updateCollectItemIsCleared(collectItemId, Constants.cleared.isCleared);
						}
					}
//					if("collectClear".equals(clearFlag)){
//						//未收款本位币
//						BigDecimal unoff = unClearCollect.getBwbje();
//						//本次已抵消金额本位币
//						BigDecimal clearBwbje = unClearCollect.getNowclearamount().multiply(rate);
//						customerTitle.setBwbje(unoff.subtract(clearBwbje));
//						customerTitleHibernateDao.saveOrUpdate(customerTitle);
//					}
				}
				newUnClearCollectSet.add(unClearCollect);
			}
		}

		customSingleClear.setUnClearCollect(newUnClearCollectSet);	
		//生成单号
		if (StringUtils.isNullBlank(customSingleClear.getCustomclearno())) {
			String customClearNo = NumberService.getNextObjectNumber("customSingleClear",customSingleClear);			
			customSingleClear.setCustomclearno(customClearNo);
		}
		customSingleClearHibernateDao.saveOrUpdate(customSingleClear);

		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(customSingleClear);
	}
	/**
	 * 更新旧的title,和收付款，的 iscleared sap清帐
	 * @param subject 客户编号 ，对应voucheritme的科目号
	 * @param taxCode 合同号或立项号，对应voucheritem中的taxCode
	 */
	public void updateOldTitle(CustomSingleClear customSingleClear){
		String businessid = customSingleClear.getCustomsclearid();
		List<VoucherItem> voucherItems = voucherItemJdbcDao.getVoucherItems(businessid);
		
		for(VoucherItem voucherItem : voucherItems){
			// 公司代码
			String companyCode = voucherItem.getVoucher().getCompanycode();
			// 财务凭证号
			String voucherNo =voucherItem.getVoucherno();
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
//			Collect collect = this.collectItemHibernateDao.getDetached(businessId);
//			if(null != collect){
//				for(CollectItem collectItem :collect.getCollectitem()){
//					if(taxCode.equals(collectItem.getContract_no()) || taxCode.equals(collectItem.getProject_no())){
//						this.collectItemService.updateCollectItemIsCleared(collectItem.getCollectitemid(), Constants.cleared.sapIsCleared);
//					}
//				}
//			}
			
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
	 * 检查是否有凭证 
	 * @param businessId
	 * @return
	 */
	public boolean checkVoucher(String businessId){
		List<Voucher> li=voucherHibernateDao.getVoucherByBusinessId2(businessId);
		if(li!=null && li.size()!=0){
			boolean flag = true;
			for(Voucher vo : li){
				if(StringUtils.isNullBlank(vo.getVoucherno())){
					flag = false;
				}
			}
			return flag;
		}
		return false;
	}
	
	/**
	 * 检查是否有凭证 ,清帐凭证，为A
	 * @param businessId
	 * @return
	 */
	public boolean checkVoucher(String businessId,String bstat){
		List<Voucher> li=voucherHibernateDao.getVoucherByBusinessId2(businessId,bstat);
		if(li!=null && li.size()!=0){
			boolean flag = true;
			for(Voucher vo : li){
				if(StringUtils.isNullBlank(vo.getVoucherno())){
					flag = false;
				}
			}
			return flag;
		}
		return false;
	}
	
	/**
	 * 作废
	 * 
	 * @param customsClearId
	 */
	public void _blankOut(CustomSingleClear customSingleClear)
	{
		this.customSingleClearJdbcDao.updateBusinessstate(customSingleClear.getCustomsclearid(), BusinessState.BLANKOUT);
		// 更新所有已经打上已结清标志的票、款，为未结清。
		Set<UnClearCustomBill> unClearCustomBillset = customSingleClear.getUnClearCustomBill();
		Set<UnClearCollect> unClearCollectSet = customSingleClear.getUnClearCollect();

		log.debug("更新所有已经打上已结清标志的票、款，为未结清。");
		if (null != unClearCustomBillset)
		{
			Iterator<UnClearCustomBill> itUnClearCustomBill = unClearCustomBillset.iterator();
			while (itUnClearCustomBill.hasNext())
			{
				UnClearCustomBill unClearCustomBill = (UnClearCustomBill) itUnClearCustomBill.next();
				String invoice = unClearCustomBill.getBillno();
				String customertitleid = unClearCustomBill.getCustomertitleid();
				String paymentitemid = unClearCustomBill.getPaymentitemid();
				log.debug("customertitleid:UnClearCustomBill:" + customertitleid);
				this.unclearCustomerService.updateCustomerTitleIsCleared(customertitleid, Constants.cleared.notCleared);
				if (!StringUtils.isNullBlank(paymentitemid))
				{
					this.paymentItemService.updatePaymentItemIsCleared(paymentitemid, Constants.cleared.notCleared);
				}
			}
		}

		if (null != unClearCollectSet)
		{
			Iterator<UnClearCollect> itUnClearCollect = unClearCollectSet.iterator();
			while (itUnClearCollect.hasNext())
			{
				UnClearCollect unClearCollect = (UnClearCollect) itUnClearCollect.next();
				String collectitemid = unClearCollect.getCollectitemid();
				String collectno = unClearCollect.getCollectno();
				String customertitleid = unClearCollect.getCustomertitleid();
				log.debug("customertitleid:UnClearPayment:" + customertitleid + " ;collectitemid:" + collectitemid);
				this.unclearCustomerService.updateCustomerTitleIsCleared(customertitleid, Constants.cleared.notCleared);
				this.collectItemService.updateCollectItemIsCleared(collectitemid, Constants.cleared.notCleared);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.infolion.xdss3.singleClearGen.service.SupplierSinglClearServiceGen
	 * #_delete(java.lang.String,
	 * com.infolion.platform.bo.domain.BusinessObject)
	 */
	@Override
	public void _delete(String customsClearId, BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(customsClearId))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("suppliersclearid"));
		CustomSingleClear customSingleClear = this.customSingleClearHibernateDao.load(customsClearId);

		if (customSingleClear.getBusinessstate().equals(BusinessState.COLLECTSUBMITPASS) || customSingleClear.getBusinessstate().equals(BusinessState.BLANKOUT) || customSingleClear.getBusinessstate().equals(BusinessState.SUBMITNOTPASS))
		{
			throw new BusinessException("删除数据错误，确认清帐、作废的数据不能删除！");
		}
		_delete(customSingleClear);
	}

	/**
	 * 保存凭证。
	 * 
	 * @param customSingleClear
	 * @param unClearSupplieBillItems
	 * @param fundFlow
	 * @param settleSubject
	 * @return
	 */
	public List<String> _saveVoucher(CustomSingleClear customSingleClear, Set<UnClearCustomBill> unClearCustomBills, FundFlow fundFlow, SettleSubject settleSubject)
	{
		List<String> voucherIds = new ArrayList<String>();
		

		// 币别
		String currency = "";

		String custom = customSingleClear.getCustom();
//		BigDecimal sumAmount2 = new BigDecimal(0); // 所有财务结算本位币金额的和
		BigDecimal sumAmount2_s = new BigDecimal(0); // 所有财务结算借方本位币金额的和
		BigDecimal sumAmount2_h = new BigDecimal(0); // 所有财务结算贷方本位币金额的和
		BigDecimal amount2 = new BigDecimal(0); // 财务结算本位币金额
		BigDecimal sumAmount3_s = new BigDecimal(0); // 所有财务结算借方金额的和
		BigDecimal sumAmount3_h = new BigDecimal(0); // 所有财务结算贷方金额的和
		//String deptId = customSingleClear.getDeptid();
		String bukrs = customSingleClear.getCompanyno();
		//String bukrs = this.voucherService.getCompanyIDByDeptID(deptId);
		
		// 凭证
		Voucher voucher = null;
		if (null != settleSubject || null != fundFlow)
		{
			voucher = getVoucher(customSingleClear, bukrs);
			List<VoucherItem> voucherItemList = new ArrayList<VoucherItem>();

			Iterator<UnClearCustomBill> it = unClearCustomBills.iterator();

			int iRowNo = 1; // 行项目号
			// 调整差额总额
			BigDecimal sumAdjustamount = new BigDecimal(0);
			while (it.hasNext())
			{
				UnClearCustomBill unClearCustomBill = (UnClearCustomBill) it.next();
				// 当调整差额 有填入值。
				if (unClearCustomBill.getAdjustamount().abs().compareTo(new BigDecimal(0)) == 1)
				{
					if (StringUtils.isNullBlank(currency))
					{
						currency = unClearCustomBill.getCurrency();
					}
				}
				BigDecimal adjustamount = unClearCustomBill.getAdjustamount();
				sumAdjustamount = sumAdjustamount.add(adjustamount);
			}
			if("".equals(currency))currency = customSingleClear.getCurrencytext();
			voucher.setCurrency(currency);
			String taxCode =  getTaxCode(customSingleClear);
			// 开始添加凭证行项目
			VoucherItem voucherItem = new VoucherItem();
			voucherItem.setRownumber(String.valueOf(iRowNo));
			voucherItem.setCurrency(currency);
			voucherItem.setAmount(sumAdjustamount.abs());
			//voucherItem.setAmount(new BigDecimal(0));
			if (sumAdjustamount.signum() == -1)
			{
				voucherItem.setCheckcode("01");
				voucherItem.setDebitcredit("S");
			}
			else if (sumAdjustamount.signum() == 1)
			{
				voucherItem.setCheckcode("11");
				voucherItem.setDebitcredit("H");
			}
			voucherItem.setAmount2(new BigDecimal(0));
			voucherItem.setSubject(custom);
			voucherItem.setSubjectdescribe(customSingleClear.getCustom_htext());
			voucherItem.setDepid(customSingleClear.getDepid());
			voucherItem.setText(customSingleClear.getText());			
			String subject = this.customerRefundItemJdbcDao.getSubjectByCustomer(customSingleClear.getCustom(),bukrs);
			String subjectname = this.customerRefundItemJdbcDao.getSubjectNameById(subject,bukrs);
			voucherItem.setControlaccount(subject);
			voucherItem.setCaremark(subjectname);
			voucherItem.setTaxcode(taxCode);
			
			voucherItem.setVoucher(voucher);
			
			voucherItemList.add(voucherItem);

			// 结算科目
			// 金额1有填入值
			BigDecimal amount = new BigDecimal(0);
			String debitcredit = "";

			if (null != settleSubject)
			{
				amount = settleSubject.getAmount1();
				debitcredit = settleSubject.getDebtcredit1();
				if (amount.abs().compareTo(new BigDecimal(0)) == 1)
				{
					iRowNo = iRowNo + 1;
					// 开始添加凭证行项目
					VoucherItem voucherItems = new VoucherItem();
					voucherItems.setRownumber(String.valueOf(iRowNo));
					voucherItems.setCurrency(currency);
					voucherItems.setAmount(amount);					
					voucherItems.setDebitcredit(debitcredit);
					if ("S".equalsIgnoreCase(debitcredit))
					{
						voucherItems.setCheckcode("40");
					}
					else if ("H".equalsIgnoreCase(debitcredit))
					{
						voucherItems.setCheckcode("50");
					}
					amount2 = settleSubject.getStandardamount1();
					voucherItems.setAmount2(amount2);
					//sumAmount2 = sumAmount2.add(amount2);					
					voucherItems.setSubject(settleSubject.getSettlesubject1());
					voucherItems.setSubjectdescribe(settleSubject.getSettlesubject1_htext());
					voucherItems.setDepid(settleSubject.getDepid1());
					voucherItems.setText(customSingleClear.getText());	
					voucherItems.setCostcenter(settleSubject.getCostcenter1());	
					if("Y".equals(settleSubject.getAntiaccount1())){
						voucherItems.setUncheckflag("X");
	                }else{
	                	voucherItems.setUncheckflag("");
	                }

//					voucherItems.setUncheckflag(settleSubject.getAntiaccount1());
//					voucherItems.setControlaccount(subject);
//					voucherItems.setCaremark(subjectname);
					voucherItems.setOrderrowno(settleSubject.getRowno1());
					voucherItems.setSalesorder(settleSubject.getOrderno1());	
					voucherItems.setTaxcode(taxCode);
					voucherItems.setVoucher(voucher);
					voucherItemList.add(voucherItems);
				}

				amount = settleSubject.getAmount2();
				debitcredit = settleSubject.getDebtcredit2();
				if (amount.abs().compareTo(new BigDecimal(0)) == 1)
				{
					iRowNo = iRowNo + 1;
					// 开始添加凭证行项目
					VoucherItem voucherItems = new VoucherItem();
					voucherItems.setRownumber(String.valueOf(iRowNo));
					voucherItems.setCurrency(currency);
					voucherItems.setAmount(amount);					
					voucherItems.setDebitcredit(debitcredit);
					if ("S".equalsIgnoreCase(debitcredit))
					{
						voucherItems.setCheckcode("40");
					}
					else if ("H".equalsIgnoreCase(debitcredit))
					{
						voucherItems.setCheckcode("50");
					}
					amount2 = settleSubject.getStandardamount2();
					voucherItems.setAmount2(amount2);
					//sumAmount2 = sumAmount2.add(amount2);					
					voucherItems.setSubject(settleSubject.getSettlesubject2());
					voucherItems.setSubjectdescribe(settleSubject.getSettlesubject2_htext());
					voucherItems.setDepid(settleSubject.getDepid2());
					voucherItems.setText(customSingleClear.getText());
					voucherItems.setCostcenter(settleSubject.getCostcenter2());
					if("Y".equals(settleSubject.getAntiaccount2())){
						voucherItems.setUncheckflag("X");
	                }else{
	                	voucherItems.setUncheckflag("");
	                }
//					voucherItems.setUncheckflag(settleSubject.getAntiaccount2());
					voucherItems.setOrderrowno(settleSubject.getRowno2());
					voucherItems.setSalesorder(settleSubject.getOrderno2());
//					voucherItems.setControlaccount(subject);
//					voucherItems.setCaremark(subjectname);
					voucherItems.setTaxcode(taxCode);
					voucherItems.setVoucher(voucher);
					voucherItemList.add(voucherItems);
				}

				amount = settleSubject.getAmount3();
				debitcredit = settleSubject.getDebtcredit3();
				if (amount.abs().compareTo(new BigDecimal(0)) == 1)
				{
					iRowNo = iRowNo + 1;
					// 开始添加凭证行项目
					VoucherItem voucherItems = new VoucherItem();
					voucherItems.setRownumber(String.valueOf(iRowNo));
					voucherItems.setCurrency(currency);
					voucherItems.setAmount(amount);					
					voucherItems.setDebitcredit(debitcredit);
					if ("S".equalsIgnoreCase(debitcredit))
					{
						voucherItems.setCheckcode("40");
					}
					else if ("H".equalsIgnoreCase(debitcredit))
					{
						voucherItems.setCheckcode("50");
					}
					amount2 = settleSubject.getStandardamount3();
					voucherItems.setAmount2(amount2);
					//sumAmount2 = sumAmount2.add(amount2);					
					voucherItems.setSubject(settleSubject.getSettlesubject3());
					voucherItems.setSubjectdescribe(settleSubject.getSettlesubject3_htext());
					voucherItems.setDepid(settleSubject.getDepid3());
					voucherItems.setText(customSingleClear.getText());
					voucherItems.setCostcenter(settleSubject.getCostcenter3());
					if("Y".equals(settleSubject.getAntiaccount3())){
						voucherItems.setUncheckflag("X");
	                }else{
	                	voucherItems.setUncheckflag("");
	                }
//					voucherItems.setUncheckflag(settleSubject.getAntiaccount3());
					voucherItems.setOrderrowno(settleSubject.getRowno3());
					voucherItems.setSalesorder(settleSubject.getOrderno3());
//					voucherItems.setControlaccount(subject);
//					voucherItems.setCaremark(subjectname);
					voucherItems.setTaxcode(taxCode);
					voucherItems.setVoucher(voucher);
					voucherItemList.add(voucherItems);
				}

				amount = settleSubject.getAmount4();
				debitcredit = settleSubject.getDebtcredit4();
				if (amount.abs().compareTo(new BigDecimal(0)) == 1)
				{
					iRowNo = iRowNo + 1;
					// 开始添加凭证行项目
					VoucherItem voucherItems = new VoucherItem();
					voucherItems.setRownumber(String.valueOf(iRowNo));
					voucherItems.setCurrency(currency);
					voucherItems.setAmount(amount);
					
					voucherItems.setDebitcredit(debitcredit);
					if ("S".equalsIgnoreCase(debitcredit))
					{
						voucherItems.setCheckcode("40");
					}
					else if ("H".equalsIgnoreCase(debitcredit))
					{
						voucherItems.setCheckcode("50");
					}
					amount2 = settleSubject.getStandardamount4();
					voucherItems.setAmount2(amount2);
					//sumAmount2 = sumAmount2.add(amount2);
					
					voucherItems.setSubject(settleSubject.getSettlesubject4());
					voucherItems.setSubjectdescribe(settleSubject.getSettlesubject4_htext());
					voucherItems.setDepid(settleSubject.getDepid4());
					voucherItems.setText(customSingleClear.getText());
					voucherItems.setProfitcenter(settleSubject.getProfitcenter());
					if("Y".equals(settleSubject.getAntiaccount4())){
						voucherItems.setUncheckflag("X");
	                }else{
	                	voucherItems.setUncheckflag("");
	                }
//					voucherItems.setUncheckflag(settleSubject.getAntiaccount4());
					voucherItems.setOrderrowno(settleSubject.getRowno4());
					voucherItems.setSalesorder(settleSubject.getOrderno4());
//					voucherItems.setControlaccount(subject);
//					voucherItems.setCaremark(subjectname);
					voucherItems.setTaxcode(taxCode);
					voucherItems.setVoucher(voucher);
					voucherItemList.add(voucherItems);
				}
			}
			// 纯资金往来
			// fundFlowPay.getAmount1()
			if (null != fundFlow)
			{
				amount = fundFlow.getAmount1();
				debitcredit = fundFlow.getDebtcredit1();
				if (amount.abs().compareTo(new BigDecimal(0)) == 1)
				{
					iRowNo = iRowNo + 1;
					// 开始添加凭证行项目
					VoucherItem voucherItems = new VoucherItem();
					voucherItems.setRownumber(String.valueOf(iRowNo));
					voucherItems.setCurrency(currency);
					voucherItems.setAmount(amount);
					
					voucherItems.setDebitcredit(debitcredit);
					if ("S".equalsIgnoreCase(debitcredit))
					{
						if(StringUtils.isNullBlank(fundFlow.getSpecialaccount1())){
							voucherItems.setCheckcode("01");
						}else{
							voucherItems.setCheckcode("09");
						}
					}
					else if ("H".equalsIgnoreCase(debitcredit))
					{
						if(StringUtils.isNullBlank(fundFlow.getSpecialaccount1())){
							voucherItems.setCheckcode("11");
						}else{
							voucherItems.setCheckcode("19");
						}
					}
					amount2 = fundFlow.getStandardamount1();
					voucherItems.setAmount2(amount2);
					//sumAmount2 = sumAmount2.add(amount2);
					
					voucherItems.setSubject(fundFlow.getCustomer1());
					voucherItems.setSubjectdescribe(fundFlow.getCustomer1_htext());
					voucherItems.setDepid(fundFlow.getDepid1());
					voucherItems.setText(customSingleClear.getText());
					voucherItems.setGlflag(fundFlow.getSpecialaccount1());
					
					if("Y".equals(fundFlow.getAntiaccount1())){
						voucherItems.setUncheckflag("X");
	                }else{
	                	voucherItems.setUncheckflag("");
	                }
//					voucherItems.setUncheckflag(fundFlow.getAntiaccount1());
					voucherItems.setControlaccount(subject);
					voucherItems.setCaremark(subjectname);
					if(!StringUtils.isNullBlank(voucherItems.getGlflag())){
						subject = this.customerRefundItemJdbcDao.getSkont(voucherItems,subject);
						String subjectname2 = this.customerRefundItemJdbcDao.getSubjectNameById(subject,bukrs);
						voucherItems.setControlaccount(subject);
						voucherItems.setCaremark(subjectname2);
					}
					voucherItems.setTaxcode(taxCode);
					voucherItems.setVoucher(voucher);
					voucherItemList.add(voucherItems);
				}

				amount = fundFlow.getAmount2();
				debitcredit = fundFlow.getDebtcredit1();
				if (amount.abs().compareTo(new BigDecimal(0)) == 1)
				{
					iRowNo = iRowNo + 1;
					// 开始添加凭证行项目
					VoucherItem voucherItems = new VoucherItem();
					voucherItems.setRownumber(String.valueOf(iRowNo));
					voucherItems.setCurrency(currency);
					voucherItems.setAmount(amount);
					
					voucherItems.setDebitcredit(debitcredit);
					if ("S".equalsIgnoreCase(debitcredit))
					{
						if(StringUtils.isNullBlank(fundFlow.getSpecialaccount2())){
							voucherItems.setCheckcode("01");
						}else{
							voucherItems.setCheckcode("09");
						}
					}
					else if ("H".equalsIgnoreCase(debitcredit))
					{
						if(StringUtils.isNullBlank(fundFlow.getSpecialaccount2())){
							voucherItems.setCheckcode("11");
						}else{
							voucherItems.setCheckcode("19");
						}
					}
					amount2 = fundFlow.getStandardamount2();
					voucherItems.setAmount2(amount2);
					//sumAmount2 = sumAmount2.add(amount2);
					
					voucherItems.setSubject(fundFlow.getCustomer2());
					voucherItems.setSubjectdescribe(fundFlow.getCustomer2_htext());
					voucherItems.setDepid(fundFlow.getDepid2());
					voucherItems.setText(customSingleClear.getText());
					voucherItems.setGlflag(fundFlow.getSpecialaccount2());

					if("Y".equals(fundFlow.getAntiaccount2())){
						voucherItems.setUncheckflag("X");
	                }else{
	                	voucherItems.setUncheckflag("");
	                }
//					voucherItems.setUncheckflag(fundFlow.getAntiaccount2());
					voucherItems.setControlaccount(subject);
					voucherItems.setCaremark(subjectname);
					if(!StringUtils.isNullBlank(voucherItems.getGlflag())){
						subject = this.customerRefundItemJdbcDao.getSkont(voucherItems,subject);
						String subjectname2 = this.customerRefundItemJdbcDao.getSubjectNameById(subject,bukrs);
						voucherItems.setControlaccount(subject);
						voucherItems.setCaremark(subjectname2);
					}
					voucherItems.setTaxcode(taxCode);
					voucherItems.setVoucher(voucher);
					voucherItemList.add(voucherItems);
				}

				amount = fundFlow.getAmount3();
				debitcredit = fundFlow.getDebtcredit1();
				if (amount.abs().compareTo(new BigDecimal(0)) == 1)
				{
					iRowNo = iRowNo + 1;
					// 开始添加凭证行项目
					VoucherItem voucherItems = new VoucherItem();
					voucherItems.setRownumber(String.valueOf(iRowNo));
					voucherItems.setCurrency(currency);
					voucherItems.setAmount(amount);
					
					voucherItems.setDebitcredit(debitcredit);
					if ("S".equalsIgnoreCase(debitcredit))
					{
						if(StringUtils.isNullBlank(fundFlow.getSpecialaccount3())){
							voucherItems.setCheckcode("01");
						}else{
							voucherItems.setCheckcode("09");
						}
					}
					else if ("H".equalsIgnoreCase(debitcredit))
					{
						if(StringUtils.isNullBlank(fundFlow.getSpecialaccount3())){
							voucherItems.setCheckcode("11");
						}else{
							voucherItems.setCheckcode("19");
						}
					}
					amount2 = fundFlow.getStandardamount3();
					voucherItems.setAmount2(amount2);
					//sumAmount2 = sumAmount2.add(amount2);
					
					voucherItems.setSubject(fundFlow.getCustomer3());
					voucherItems.setSubjectdescribe(fundFlow.getCustomer3_htext());
					voucherItems.setDepid(fundFlow.getDepid3());
					voucherItems.setText(customSingleClear.getText());
					voucherItems.setGlflag(fundFlow.getSpecialaccount3());

					if("Y".equals(fundFlow.getAntiaccount3())){
						voucherItems.setUncheckflag("X");
	                }else{
	                	voucherItems.setUncheckflag("");
	                }
//					voucherItems.setUncheckflag(fundFlow.getAntiaccount3());
					voucherItems.setControlaccount(subject);
					voucherItems.setCaremark(subjectname);
					if(!StringUtils.isNullBlank(voucherItems.getGlflag())){
						subject = this.customerRefundItemJdbcDao.getSkont(voucherItems,subject);
						String subjectname2 = this.customerRefundItemJdbcDao.getSubjectNameById(subject,bukrs);
						voucherItems.setControlaccount(subject);
						voucherItems.setCaremark(subjectname2);
					}
					voucherItems.setTaxcode(taxCode);
					voucherItems.setVoucher(voucher);
					voucherItemList.add(voucherItems);
				}
			}

//			if (sumAmount2.abs().compareTo(new BigDecimal(0)) == 1)
//			{
//				for (VoucherItem voucherItem0 : voucherItemList)
//				{
//					if (voucherItem0.getRownumber().equals("1"))
//					{
//						voucherItem0.setAmount2(sumAmount2);
//					}
//				}
//			}
			
			for(int n=1;n<voucherItemList.size();n++){
				//第一条为调整金，不统计
				VoucherItem voucherItem0 = (VoucherItem)voucherItemList.get(n);
				if ("S".equalsIgnoreCase(voucherItem0.getDebitcredit())){
					sumAmount2_s = sumAmount2_s.add(voucherItem0.getAmount2());
					sumAmount3_s = sumAmount3_s.add(voucherItem0.getAmount());
				}
				if ("H".equalsIgnoreCase(voucherItem0.getDebitcredit())){
					sumAmount2_h = sumAmount2_h.add(voucherItem0.getAmount2());
					sumAmount3_h = sumAmount3_h.add(voucherItem0.getAmount());
				}
				
			}
			//金额借-贷相减
			BigDecimal sumAmount5 = sumAmount3_s.subtract(sumAmount3_h);
			//金额借-贷相减 与调整金额，不相等，
			if (sumAmount5.compareTo(sumAdjustamount) != 0){
				 voucherIds.add("!null");
				 return voucherIds;
			}
			//本位币借-贷相减
			BigDecimal sumAmount6 = sumAmount2_s.subtract(sumAmount2_h);
			
			if (sumAmount6.abs().compareTo(new BigDecimal(0)) == 1){
				for (VoucherItem voucherItem0 : voucherItemList){
					if (voucherItem0.getRownumber().equals("1")){
						voucherItem0.setAmount2(sumAmount6.abs());						
					}
				}
			}
			//本位币为0 并且，金额也为0，不加行项目
			if (sumAmount6.abs().compareTo(new BigDecimal(0)) == 0 && sumAdjustamount.abs().compareTo(new BigDecimal(0)) == 0){
				for (VoucherItem voucherItem0 : voucherItemList){					
					if (voucherItem0.getRownumber().equals("1")){
						voucherItemList.remove(voucherItem0);
						break;
					}
				}
			}
			
			Set<VoucherItem> vi=new HashSet<VoucherItem>(voucherItemList);
			//vi.addAll(voucherItemList);
			voucher.setVoucherItem(vi);
			//判断是否已经生成损益凭证，
			Voucher voucher2=null;
			List<Voucher> li=voucherHibernateDao.getVoucherByBusinessId2(customSingleClear.getCustomsclearid()," ");
			if(li!=null && li.size()!=0){				
				for(Voucher vo : li){
					if(!StringUtils.isNullBlank(vo.getVoucherno())){
						voucher2=vo;
						break;
					}
				} 
			}
			if(null == voucher2 && !voucher.getVoucherItem().isEmpty()){
				this.voucherService._saveOrUpdate(voucher, null, BusinessObjectService.getBusinessObject("Voucher"));
				voucherHibernateDao.flush();		
				voucherIds.add(voucher.getVoucherid());
			}else{
				voucher=voucher2;
			}
			
		}

		// 开始处理清帐凭证
		Voucher clearVoucher = _saveClearAccountVoucher(customSingleClear,voucher);
		
		if(null != voucher && !voucher.getVoucherItem().isEmpty()){
			this.voucherService._saveOrUpdate(voucher, null, BusinessObjectService.getBusinessObject("Voucher"));
			voucherIds.add(voucher.getVoucherid());
		}
		
		if (null != clearVoucher)
		{
			voucherIds.add(clearVoucher.getVoucherid());
		}
		
		
		return voucherIds;
	}

	/**
	 * 取得凭证Voucher抬头主数据。
	 * 
	 * @param customSingleClear
	 * @return
	 */
	private Voucher getVoucher(CustomSingleClear customSingleClear, String bukrs)
	{
		Voucher voucher = new Voucher();
		String fiperiod = "";
		String fiyear = "";
		// 添加凭证抬头数据
		voucher.setBusinessid(customSingleClear.getCustomsclearid());
		voucher.setBusinesstype("08"); // 客户单清
		voucher.setVouchertype("DR");
		voucher.setCompanycode(bukrs);
		voucher.setExchangerate(new BigDecimal(1));
		voucher.setCurrency(customSingleClear.getCurrencytext());
		voucher.setGsber(customSingleClear.getDepid());
		String accountdate = customSingleClear.getAccountdate().replace("-", "");
		voucher.setCheckdate(accountdate);
		voucher.setVoucherclass("1");
		if (!StringUtils.isNullBlank(accountdate))
		{
			fiperiod = accountdate.substring(4, 6);
			fiyear = accountdate.substring(0, 4);
		}
		voucher.setFiperiod(fiperiod);// 会计期间
		voucher.setFiyear(fiyear); // 会计年度
		voucher.setImportdate(DateUtils.getCurrDate(DateUtils.DB_STORE_DATE).substring(0, 8));
		// 输入日期
		UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
		voucher.setImporter(userContext.getUser().getUserName()); // 输入者
		voucher.setPreparer(userContext.getUser().getUserName()); // 预制者
		voucher.setVoucherdate(customSingleClear.getVoucherdate().replace("-", "")); // 凭证日期
		voucher.setVouchertext(customSingleClear.getText()); // 凭证抬头文本

		return voucher;
	}

	/**
	 * 取得清帐凭证抬头
	 * 
	 * @param supplierSinglClear
	 */
	private Voucher getClearVoucher(CustomSingleClear customSingleClear, String bukrs)
	{
		Voucher voucher = new Voucher();
		String fiperiod = "";
		String fiyear = "";
		// 添加凭证抬头数据
		voucher.setBusinessid(customSingleClear.getCustomsclearid());
		voucher.setBusinesstype("08"); // 票清付款
		voucher.setVouchertype("SA");
		voucher.setCompanycode(bukrs);
		voucher.setCurrency(customSingleClear.getCurrencytext());
		voucher.setGsber(customSingleClear.getDepid());
		String accountdate = customSingleClear.getAccountdate().replace("-", "");
		BigDecimal sumAmount2 = new BigDecimal(0); // 所有财务结算本位币金额的和
		BigDecimal amount2 = new BigDecimal(0); // 财务结算本位币金额
		voucher.setCheckdate(accountdate);
		voucher.setVoucherclass("9");
		if (!StringUtils.isNullBlank(accountdate))
		{
			fiperiod = accountdate.substring(4, 6);
			fiyear = accountdate.substring(0, 4);
		}
		// 会计期间
		voucher.setFiperiod(fiperiod);
		// 会计年度
		voucher.setFiyear(fiyear);
		// 输入日期
		voucher.setImportdate(DateUtils.getCurrDate(DateUtils.DB_STORE_DATE).substring(0, 8));
		UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
		// 输入者
		voucher.setImporter(userContext.getUser().getUserName());
		// 预制者
		voucher.setPreparer(userContext.getUser().getUserName());
		// 凭证日期
		voucher.setVoucherdate(customSingleClear.getVoucherdate().replace("-", ""));
		voucher.setVouchertext(customSingleClear.getText()); // 凭证抬头文本
		// 付款清帐标识
		voucher.setFlag("R");
		// 凭证分类
		voucher.setVoucherclass("");
		// 计息日
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		voucher.setValut(dateFormat.format(new Date()));
		// 客户编号
		voucher.setAgkon(customSingleClear.getCustom());
		// 科目类型
		voucher.setAgkoa("D");
		// 清帐凭证状态
		voucher.setBstat("A");
		// 汇率
		voucher.setExchangerate(new BigDecimal("1"));

		return voucher;
	}

	/**
	 * 处理本次可以全清帐
	 * 
	 * @param supplierSinglClear
	 * @param currency
	 * @param voucher
	 * @param voucherItemList
	 */
	private void getClearVoucherItem(CustomSingleClear customSingleClear, Voucher clearVoucher, List<VoucherItem> voucherItemList, String currency,BigDecimal ajValue,String taxCode)
	{
		// 得到票的本位币之和
		BigDecimal billValue = new BigDecimal("0");
		// 得到分配项的本位币之和
		BigDecimal itemValue = new BigDecimal("0");
		// 分配项之和和票之和的差
		BigDecimal subtractVlaue = new BigDecimal("0");
		
		
		
		Set<UnClearCustomBill> unClearCustomBills = customSingleClear.getUnClearCustomBill();
		Set<UnClearCollect> unClearCollects = customSingleClear.getUnClearCollect();
		
		for (UnClearCustomBill unClearCustomBill : unClearCustomBills)
		{
			String invoice = unClearCustomBill.getBillno();
			VoucherItem voucherItem = null;			 
			if(!StringUtils.isNullBlank(invoice)){
				ClearVoucherItemStruct clearVoucherItemStruct = this.customerTitleJdbcDao.getCurrectBillInfo(invoice);
				 voucherItem = getClearVoucherItem(clearVoucherItemStruct);
				 if(null ==clearVoucherItemStruct.getBwbje())clearVoucherItemStruct.setBwbje(new BigDecimal("0"));					
				 billValue = billValue.add(clearVoucherItemStruct.getBwbje());
			}else{
				
				ClearVoucherItemStruct clearVoucherItemStruct = new ClearVoucherItemStruct();
				 CustomerTitle customerTitle = this.customerTitleJdbcDao.getByCustomertitleid(unClearCustomBill.getCustomertitleid());				 
				 clearVoucherItemStruct.setRowno(customerTitle.getBuzei());
				 clearVoucherItemStruct.setVoucherno(unClearCustomBill.getVoucherno());
				 clearVoucherItemStruct.setYear(customerTitle.getGjahr());
				 voucherItem = getClearVoucherItem(clearVoucherItemStruct);
				 billValue = billValue.add(customerTitle.getBwbje());
			}		
			
			
			voucherItem.setVoucher(clearVoucher);
			voucherItemList.add(voucherItem);
		}
		
		for (UnClearCollect unClearCollect : unClearCollects)
		{
//			String collectid = unClearCollect.getCollectid();
//			String collectitemid = unClearCollect.getCollectitemid();
			VoucherItem voucherItem = null;
//			if(!StringUtils.isNullBlank(collectitemid)){
//				Voucher oldVoucher = this.voucherService.getVoucher(collectid);
//				Set<VoucherItem> oldVoucherItem = oldVoucher.getVoucherItem();
//				Iterator<VoucherItem> oldVoucherItemIt = oldVoucherItem.iterator();
//				while (oldVoucherItemIt.hasNext())
//				{
//					VoucherItem oldItem = oldVoucherItemIt.next();
//
//					if (oldItem.getVoucher().getBusinessid().equals(unClearCollect.getCollectid()) && oldItem.getBusinessitemid().equals(unClearCollect.getCollectitemid()))
//					{
//						ClearVoucherItemStruct clearVoucherItemStruct = new ClearVoucherItemStruct();
//						clearVoucherItemStruct.setRowno(oldItem.getRownumber());
//						clearVoucherItemStruct.setVoucherno(unClearCollect.getVoucherno());
//						clearVoucherItemStruct.setYear(oldItem.getVoucher().getFiyear());
//						voucherItem = getClearVoucherItem(clearVoucherItemStruct);
//						itemValue = itemValue.add(oldItem.getAmount2());						
//					}
//				}
//				VoucherItem voucheritem2 = this.voucherItemJdbcDao.getVoucherItem(collectitemid, "1");
//				if(null !=voucheritem2){
//					ClearVoucherItemStruct clearVoucherItemStruct = new ClearVoucherItemStruct();
//					clearVoucherItemStruct.setRowno(voucheritem2.getRownumber());
//					clearVoucherItemStruct.setVoucherno(unClearCollect.getVoucherno());
//					clearVoucherItemStruct.setYear(voucheritem2.getVoucher().getFiyear());
//					voucherItem = getClearVoucherItem(clearVoucherItemStruct);
//					itemValue = itemValue.add(voucheritem2.getAmount2());	
//					voucherItem.setVoucher(clearVoucher);
//					voucherItemList.add(voucherItem);
//				}
//			}else{
				ClearVoucherItemStruct clearVoucherItemStruct = new ClearVoucherItemStruct();
				 CustomerTitle customerTitle = this.customerTitleJdbcDao.getByCustomertitleid(unClearCollect.getCustomertitleid());				 
				 clearVoucherItemStruct.setRowno(customerTitle.getBuzei());
				clearVoucherItemStruct.setVoucherno(unClearCollect.getVoucherno());
				clearVoucherItemStruct.setYear(customerTitle.getGjahr());
				voucherItem = getClearVoucherItem(clearVoucherItemStruct);			
				
				itemValue = itemValue.add(customerTitle.getBwbje());	
				voucherItem.setVoucher(clearVoucher);
				voucherItemList.add(voucherItem);
//			}
			
			
			
		}

		// 判断是否外币
		if (!"CNY".equalsIgnoreCase(currency))
		{
			subtractVlaue = itemValue.subtract(billValue);
			subtractVlaue = subtractVlaue.add(ajValue);
			if (subtractVlaue.abs().compareTo(new BigDecimal(0)) != 0)
			{
				VoucherItem supplierVoucherItem = getProfitAndLossVoucherItem(customSingleClear, subtractVlaue, currency,taxCode, "1");
				supplierVoucherItem.setVoucher(clearVoucher);
				voucherItemList.add(supplierVoucherItem);

				VoucherItem profOrLossVoucherItem = getProfitAndLossVoucherItem(customSingleClear, subtractVlaue, currency,taxCode, "2");
				profOrLossVoucherItem.setVoucher(clearVoucher);
				voucherItemList.add(profOrLossVoucherItem);
			}
		}
		Set<VoucherItem> vi=new HashSet<VoucherItem>();
		vi.addAll(voucherItemList);
		clearVoucher.setVoucherItem(vi);

	}

	/**
	 * 处理清帐凭证,
	 * 
	 * @param supplierSinglClear
	 */
	public Voucher _saveClearAccountVoucher(CustomSingleClear customSingleClear,Voucher voucher)
	{
		String businessId = customSingleClear.getCustomsclearid();
		String customer = customSingleClear.getCustom();
		//本位币合计
		BigDecimal billAmount = new BigDecimal("0");
		BigDecimal collectAmount = new BigDecimal("0");
		//判断哪一边全清
		boolean billF = false;
		boolean clearF = false;
		
		//金额合计
		BigDecimal sumbillAmount = new BigDecimal("0");
		BigDecimal sumcollectAmount = new BigDecimal("0");
		//清帐金额合计
		BigDecimal sumclearAmount = new BigDecimal("0");
		//本次抵消金额合计
		BigDecimal sumoffsetAmount = new BigDecimal("0");
		
		//调整金额合计
		BigDecimal sumajAmount = new BigDecimal("0");
		//款未抵消金额合计
		BigDecimal sumUnoffsetamount = new BigDecimal("0");
		//未收款金额合计
		BigDecimal sumUnreceivableamou = new BigDecimal("0");
		
		//款未抵消本位币金额合计
		BigDecimal sumUnoffsetamountBwb = new BigDecimal("0");
		//未收款本位币金额合计
		BigDecimal sumUnreceivableamouBwb = new BigDecimal("0");
		
		//清帐金额本位币合计
		BigDecimal sumclearAmountBwb = new BigDecimal("0");
		//本次抵消本位币金额合计
		BigDecimal sumoffsetAmountBwb = new BigDecimal("0");
		
		Set<UnClearCustomBill> unClearCustomBills = customSingleClear.getUnClearCustomBill();
		Set<UnClearCollect> unClearCollects = customSingleClear.getUnClearCollect();
		
		// 重新计算未清金额(未收款、未付款等)
		for (UnClearCustomBill unClearCustomBill : unClearCustomBills)
		{
			billAmount = billAmount.add(unClearCustomBill.getBwbje());
			
			// 发票总金额
			BigDecimal billamount = unClearCustomBill.getReceivableamount();
			// 发票已经审批完的，发票已清金额
			BigDecimal receivedamount = this.unclearCustomerService.getSumClearAmount(unClearCustomBill.getBillno(), BusinessState.ALL_COLLECT_PAIDUP);
			if(!StringUtils.isNullBlank(unClearCustomBill.getPaymentitemid())){
				//根据付款itemid取得供应商退款纯代理金额合计
				BigDecimal receivedamount2 = this.paymentItemService.getRefundmentAmount(unClearCustomBill.getPaymentitemid());
				receivedamount = receivedamount.add(receivedamount2);
			}
			unClearCustomBill.setReceivedamount(receivedamount);
			unClearCustomBill.setUnreceivableamou(billamount.subtract(receivedamount));
			
			if(unClearCustomBill.getUnreceivableamou().compareTo(unClearCustomBill.getClearamount()) !=0){
				billF = true;
				
			}
			sumajAmount = sumajAmount.add(unClearCustomBill.getAdjustamount());
			sumbillAmount = sumbillAmount.add(unClearCustomBill.getReceivableamount());
			sumclearAmount = sumclearAmount.add(unClearCustomBill.getClearamount());
			sumUnreceivableamou = sumUnreceivableamou.add(unClearCustomBill.getUnreceivableamou());
			sumUnoffsetamountBwb = sumUnoffsetamountBwb.add(unClearCustomBill.getUnbwbje());
			if(unClearCustomBill.getReceivableamount().compareTo(BigDecimal.valueOf(0)) ==0){
				sumclearAmountBwb = sumclearAmountBwb.add(unClearCustomBill.getBwbje());
			}else{
//				sumclearAmountBwb = sumclearAmountBwb.add(unClearCustomBill.getBwbje().divide(unClearCustomBill.getReceivableamount(),13,BigDecimal.ROUND_HALF_EVEN).multiply(unClearCustomBill.getClearamount()));
				sumclearAmountBwb = sumclearAmountBwb.add(unClearCustomBill.getBwbje().multiply(unClearCustomBill.getClearamount()).divide(unClearCustomBill.getReceivableamount(),13,BigDecimal.ROUND_HALF_EVEN));
			}
		}
		for (UnClearCollect unClearCollect : unClearCollects)
		{
			collectAmount = collectAmount.add(unClearCollect.getBwbje());
			
			// 款总金额
			BigDecimal goodsamount = unClearCollect.getAmount();
			// 收款单已审批完的 ，已清票款
			BigDecimal clearedPaymentAmount = this.collectItemService.getSumClearAmount(unClearCollect.getCollectitemid(), BusinessState.ALL_COLLECT_PAIDUP);
			// 款未抵消金额
			unClearCollect.setUnoffsetamount(goodsamount.subtract(clearedPaymentAmount));
			
			if(unClearCollect.getUnoffsetamount().compareTo(unClearCollect.getNowclearamount()) !=0){
				clearF = true;
				
			}
			sumcollectAmount = sumcollectAmount.add(unClearCollect.getAmount());
			sumoffsetAmount = sumoffsetAmount.add(unClearCollect.getNowclearamount());
			sumUnoffsetamount = sumUnoffsetamount.add(unClearCollect.getUnoffsetamount());
			sumUnreceivableamouBwb= sumUnreceivableamouBwb.add(unClearCollect.getUnbwbje());
			if(unClearCollect.getAmount().compareTo(BigDecimal.valueOf(0)) ==0){
				sumoffsetAmountBwb = sumoffsetAmountBwb.add(unClearCollect.getBwbje());
			}else{
//				sumoffsetAmountBwb = sumoffsetAmountBwb.add(unClearCollect.getBwbje().divide(unClearCollect.getAmount(),13,BigDecimal.ROUND_HALF_EVEN).multiply(unClearCollect.getNowclearamount()));
				sumoffsetAmountBwb = sumoffsetAmountBwb.add(unClearCollect.getBwbje().multiply(unClearCollect.getNowclearamount()).divide(unClearCollect.getAmount(),13,BigDecimal.ROUND_HALF_EVEN));
			}
		}
		
		String taxCode =  getTaxCode(customSingleClear);
		
		customSingleClear.setUnClearCustomBill(unClearCustomBills);
		customSingleClear.setUnClearCollect(unClearCollects);

		// 币别
		String currency = "";
		//String deptId = customSingleClear.getDeptid();
		String bukrs = customSingleClear.getCompanyno();
		//String bukrs = this.voucherService.getCompanyIDByDeptID(deptId);
		// 凭证
		Voucher clearVoucher = getClearVoucher(customSingleClear, bukrs);
		List<VoucherItem> voucherItemList = new ArrayList<VoucherItem>();
		Iterator<UnClearCustomBill> it = unClearCustomBills.iterator();

		int rowNo = 1; // 行项目号
		while (it.hasNext())
		{
			UnClearCustomBill unClearCustomBill = (UnClearCustomBill) it.next();

			if (StringUtils.isNullBlank(currency))
			{
				currency = unClearCustomBill.getCurrency();
				break;
			}
		}
		if("".equals(currency))currency = customSingleClear.getCurrencytext();
		clearVoucher.setCurrency(currency);

		// 单据本次是否可全清
		String contractNos = "";
		String projectNos = "";
		// 得到票的本位币之和
		BigDecimal billValue = new BigDecimal("0");
		// 得到分配项的本位币之和
		BigDecimal itemValue = new BigDecimal("0");
		// 分配项之和和票之和的差
		BigDecimal subtractVlaue = new BigDecimal("0");
		
		// 调整金本位币之和
		BigDecimal ajValue = new BigDecimal("0");
		
		
		
		if(null != voucher){
			for(VoucherItem voucherItem : voucher.getVoucherItem()){
				
				if (StringUtils.isNullBlank(voucherItem.getGlflag()) && "11".equals(voucherItem.getCheckcode()))
				{
					ajValue =  ajValue.add(voucherItem.getAmount2());
				}
				else if (StringUtils.isNullBlank(voucherItem.getGlflag()) && "01".equals(voucherItem.getCheckcode()))
				{
					ajValue =  ajValue.subtract(voucherItem.getAmount2());
				}
			}
		}
		if (judgeIsAllClear(customSingleClear))
		{
			// 处理本次可以全清帐
			getClearVoucherItem(customSingleClear, clearVoucher, voucherItemList, currency,ajValue,taxCode);
		}
		else
		{
			// 合同
			List<String> clearedContractNo = judgeContractIsAllClear(customSingleClear);
			if (null != clearedContractNo && clearedContractNo.size() > 0)
			{
				// 处理合同下清帐凭证行项目.
				contractNos = getContractClearVoucherItem(customSingleClear, clearedContractNo, clearVoucher, voucherItemList, currency, billValue, itemValue,ajValue);
			}

			if (!StringUtils.isNullBlank(contractNos))
			{
				contractNos = contractNos.substring(0, contractNos.length() - 1);
			}
			// 立项下
			List<String> clearedProjectNo = judgeProjectIsAllClear(customSingleClear, clearedContractNo, contractNos);
			if (null != clearedProjectNo && clearedProjectNo.size() > 0)
			{
				// 处理立项下清帐凭证行项目.
				projectNos = getProjectClearVoucherItem(customSingleClear, clearVoucher, voucherItemList, contractNos, clearedContractNo, clearedProjectNo, currency, billValue, itemValue,ajValue);
			}

			if (!StringUtils.isNullBlank(projectNos))
			{
				projectNos = projectNos.substring(0, projectNos.length() - 1);
			}
			// 客户清帐
			if (judgeSupplierIsAllClear(customSingleClear, customer, contractNos, projectNos, bukrs))
			{
//				如果客户层能清，就把合同层，立项层去掉
				voucherItemList = new ArrayList<VoucherItem>();
				contractNos="";
				projectNos="";
				clearedContractNo=new ArrayList<String>();
				clearedProjectNo=new ArrayList<String>();
				getCustomClearVoucherItem(customSingleClear, clearVoucher, voucherItemList, contractNos, projectNos, clearedContractNo, clearedProjectNo, currency, billValue, itemValue,ajValue, clearVoucher.getCompanycode());
			}

			// 判断是否外币,有调整金且没有清帐凭证的情况下。
			if (!"CNY".equalsIgnoreCase(currency)&&(sumajAmount.compareTo(new BigDecimal(0))!=0) && null != voucherItemList && voucherItemList.size() == 0)
			{
				BigDecimal subtractVlaue2 = new BigDecimal(0);	
				if(!clearF && !billF){
					
					//刚好两全清，款的本位币合计 -票的本位币合计
					
					subtractVlaue2 = sumUnreceivableamouBwb.subtract(sumUnoffsetamountBwb);
				}else{
									
					//票的一边全清
					if(!billF){
						//算款，的本位币=款的本位币合计/款的金额的合计*本次已抵消收款合计划-票的本位币合计
//						subtractVlaue2 = collectAmount.divide(sumcollectAmount,3,BigDecimal.ROUND_HALF_EVEN).multiply(sumoffsetAmount).subtract(billAmount);
						//修改成 算款，的本位币=未抵消收款的本位币合计/未抵消收款的金额的合计*本次已抵消收款合计划-票的本位币合计
//						subtractVlaue2 = collectAmount.divide(sumUnoffsetamount,5,BigDecimal.ROUND_HALF_EVEN).multiply(sumoffsetAmount).subtract(billAmount);
						//修改成各个行项目算汇率，再算清账本位币再相加						
						subtractVlaue2 = sumoffsetAmountBwb.subtract(sumUnoffsetamountBwb);
					}
					//款的一边全清
					if(!clearF){
						//算票，的本位币=款的本位币合计-(票的本位币合计/票的金额的合计*清帐金额合计)
//						subtractVlaue2 = billAmount.divide(sumbillAmount,3,BigDecimal.ROUND_HALF_EVEN).multiply(sumclearAmount);
						//修改成 算票，的本位币=款的本位币合计-(未收款的本位币合计/未收款的金额的合计*清帐金额合计)
//						subtractVlaue2 = billAmount.divide(sumUnreceivableamou,5,BigDecimal.ROUND_HALF_EVEN).multiply(sumclearAmount);
						//修改成各个行项目算汇率，再算清账本位币再相加
						subtractVlaue2 = sumUnreceivableamouBwb.subtract(sumclearAmountBwb);
					}			
					
				}
				if (null != voucher && subtractVlaue2.abs().compareTo(new BigDecimal(0)) != 0)
				{
					
					subtractVlaue2 = subtractVlaue2.subtract(ajValue);
					VoucherItem supplierVoucherItem = getProfitAndLossVoucherItem(customSingleClear, subtractVlaue2, currency,taxCode, "1");
					supplierVoucherItem.setRownumber("3");
					supplierVoucherItem.setVoucher(voucher);
					voucher.getVoucherItem().add(supplierVoucherItem);						

					VoucherItem profOrLossVoucherItem = getProfitAndLossVoucherItem(customSingleClear, subtractVlaue2, currency,taxCode, "2");
					profOrLossVoucherItem.setRownumber("4");
					profOrLossVoucherItem.setVoucher(voucher);
					voucher.getVoucherItem().add(profOrLossVoucherItem);
					
				}
			}
			
			// 判断是否外币,无调整金,且没有清帐凭证的情况下。
			if (!"CNY".equalsIgnoreCase(currency)&&(sumajAmount.compareTo(new BigDecimal(0))==0) && null != voucherItemList && voucherItemList.size() == 0)
			{
				BigDecimal subtractVlaue2 = new BigDecimal(0);	
				if(!clearF && !billF){
					//刚好两全清，款的本位币合计 -票的本位币合计
					subtractVlaue2 = sumUnreceivableamouBwb.subtract(sumUnoffsetamountBwb);
				}else{
									
					//票的一边全清
					if(!billF){
						//算款，的本位币=款的本位币合计/款的金额的合计*本次已抵消收款合计划-票的本位币合计
//						subtractVlaue2 = collectAmount.divide(sumcollectAmount,3,BigDecimal.ROUND_HALF_EVEN).multiply(sumoffsetAmount).subtract(billAmount);
						//修改成 算款，的本位币=未抵消收款的本位币合计/未抵消收款的金额的合计*本次已抵消收款合计划-票的本位币合计
//						subtractVlaue2 = collectAmount.divide(sumUnoffsetamount,5,BigDecimal.ROUND_HALF_EVEN).multiply(sumoffsetAmount).subtract(billAmount);
						//修改成各个行项目算汇率，再算清账本位币再相加						
						subtractVlaue2 = sumoffsetAmountBwb.subtract(sumUnoffsetamountBwb);
					}
					//款的一边全清
					if(!clearF){
						//算票，的本位币=款的本位币合计-(票的本位币合计/票的金额的合计*清帐金额合计)
//						subtractVlaue2 = billAmount.divide(sumbillAmount,3,BigDecimal.ROUND_HALF_EVEN).multiply(sumclearAmount);
						//修改成 算票，的本位币=款的本位币合计-(未收款的本位币合计/未收款的金额的合计*清帐金额合计)
//						subtractVlaue2 = billAmount.divide(sumUnreceivableamou,5,BigDecimal.ROUND_HALF_EVEN).multiply(sumclearAmount);
//						subtractVlaue2 = collectAmount.subtract(subtractVlaue2);
						//修改成各个行项目算汇率，再算清账本位币再相加
						subtractVlaue2 = sumUnreceivableamouBwb.subtract(sumclearAmountBwb);
					}
				}
				if (subtractVlaue2.abs().compareTo(new BigDecimal(0)) != 0)
					{
						// 凭证
						Voucher voucher2 = getVoucher(customSingleClear, bukrs);
						UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
						voucher2.setAgkoa(" ");
						voucher2.setAgkon(" ");
						voucher2.setBstat(" ");
						voucher2.setGsber(" ");
						voucher2.setFlag(" ");
						voucher2.setVouchertype("SA");
						voucher2.setImporter(userContext.getUser().getUserName()); // 输入者
						voucher2.setPreparer(userContext.getUser().getUserName()); // 预制者
						voucher2.setValut(" ");
						VoucherItem supplierVoucherItem = getProfitAndLossVoucherItem(customSingleClear, subtractVlaue2, currency,taxCode, "1");
						
						supplierVoucherItem.setVoucher(voucher2);
						voucherItemList.add(supplierVoucherItem);
	
						VoucherItem profOrLossVoucherItem = getProfitAndLossVoucherItem(customSingleClear, subtractVlaue2, currency,taxCode, "2");
						
						profOrLossVoucherItem.setVoucher(voucher2);
						voucherItemList.add(profOrLossVoucherItem);
						Set<VoucherItem> vi=new HashSet<VoucherItem>(voucherItemList);			
						voucher2.setVoucherItem(vi);
						//判断是否已经生成损益凭证，
						Voucher voucher3=null;
						List<Voucher> li=voucherHibernateDao.getVoucherByBusinessId2(customSingleClear.getCustomsclearid()," ");
						if(li!=null && li.size()!=0){				
							for(Voucher vo : li){
								if(!StringUtils.isNullBlank(vo.getVoucherno())){
									voucher3=vo;
									break;
								}
							} 
						}
						if(null == voucher3){
							this.voucherHibernateDao.save(voucher2);
						}else{
							voucher2=voucher3;
						}
						return voucher2;
					}
				
			}
			
			log.debug("contractNos:" + contractNos + ",projectNos:" + projectNos);
		}

		if (null != voucherItemList && voucherItemList.size() > 0)
		{
			
			//如果有调整金额生成凭证行项目
			if(null != voucher){
				VoucherItem voucherItem = new VoucherItem();
				voucherItem.setVoucherno("");
				voucherItem.setFiyear(voucher.getFiyear());
				for(VoucherItem voucheritem2:voucher.getVoucherItem()){
					if(StringUtils.isNullBlank(voucheritem2.getGlflag()) && ( "01".equals(voucheritem2.getCheckcode()) || "11".equals(voucheritem2.getCheckcode()) )){
						voucherItem.setRownumber(voucheritem2.getRownumber());
					}
				}
				List<Voucher> vl = voucherService.getVoucherList(voucher.getBusinessid());
				boolean vf=false;
				for(Voucher v : vl){
					if(!"A".equals(v.getBstat()) && !StringUtils.isNullBlank(v.getVoucherno()) ){
						voucherItem.setBusvoucherid(v.getVoucherid());
						vf=true;
						break;
					}
				}				
				if(!vf){
					voucherItem.setBusvoucherid(voucher.getVoucherid());
				}
				voucherItem.setFlag("P");
				voucherItem.setAmount(new BigDecimal("0.00"));
				voucherItem.setAmount2(new BigDecimal("0.00"));
				voucherItem.setVoucher(clearVoucher);
				voucherItemList.add(voucherItem);
			}
			Set<VoucherItem> vi=new HashSet<VoucherItem>(voucherItemList);			
			clearVoucher.setVoucherItem(vi);
			//判断是否已经生成清账凭证，
			Voucher clearVoucher2=null;
			List<Voucher> li=voucherHibernateDao.getVoucherByBusinessId2(customSingleClear.getCustomsclearid(),"A");
			if(li!=null && li.size()!=0){				
				for(Voucher vo : li){
					if(!StringUtils.isNullBlank(vo.getVoucherno())){
						clearVoucher2=vo;
						break;
					}
				} 
			}
			if(null == clearVoucher2){
				this.voucherHibernateDao.save(clearVoucher);
			}else{
				clearVoucher = clearVoucher2;
			}			

			return clearVoucher;
		}		
		return null;
	}
	/**
	 * 未收款汇率
	 * @param customSingleClear
	 * @return
	 */
	private BigDecimal getUnoffAmoutRate(CustomSingleClear customSingleClear){
		Set<UnClearCustomBill> unClearCustomBills = customSingleClear.getUnClearCustomBill();
		Set<UnClearCollect> unClearCollects = customSingleClear.getUnClearCollect();
		//判断哪一边不全清
		boolean billF = false;
		boolean clearF = false;
		
		//本位币合计
		BigDecimal billAmount = new BigDecimal("0");
		BigDecimal collectAmount = new BigDecimal("0");
		
		
		//款未抵消金额合计
		BigDecimal sumUnoffsetamount = new BigDecimal("0");
		//未收款金额合计
		BigDecimal sumUnreceivableamou = new BigDecimal("0");
		// 重新计算未清金额(未收款、未付款等)
		for (UnClearCustomBill unClearCustomBill : unClearCustomBills)
		{
			billAmount = billAmount.add(unClearCustomBill.getBwbje());
			
			// 发票总金额
			BigDecimal billamount = unClearCustomBill.getReceivableamount();
			// 发票已经审批完的，发票已清金额
			BigDecimal receivedamount = this.unclearCustomerService.getSumClearAmount(unClearCustomBill.getBillno(), BusinessState.ALL_COLLECT_PAIDUP);
			unClearCustomBill.setReceivedamount(receivedamount);
			unClearCustomBill.setUnreceivableamou(billamount.subtract(receivedamount));
			
			if(unClearCustomBill.getUnreceivableamou().compareTo(unClearCustomBill.getClearamount()) !=0){
				billF = true;
				
			}			
			sumUnreceivableamou = sumUnreceivableamou.add(unClearCustomBill.getUnreceivableamou());
			
		}
		for (UnClearCollect unClearCollect : unClearCollects)
		{
			collectAmount = collectAmount.add(unClearCollect.getBwbje());
			
			// 款总金额
			BigDecimal goodsamount = unClearCollect.getAmount();
			// 收款单已审批完的 ，已清票款
			BigDecimal clearedPaymentAmount = this.collectItemService.getSumClearAmount(unClearCollect.getCollectitemid(), BusinessState.ALL_COLLECT_PAIDUP);
			// 款未抵消金额
			unClearCollect.setUnoffsetamount(goodsamount.subtract(clearedPaymentAmount));
			
			if(unClearCollect.getUnoffsetamount().compareTo(unClearCollect.getNowclearamount()) !=0){
				clearF = true;
				
			}			
			sumUnoffsetamount = sumUnoffsetamount.add(unClearCollect.getUnoffsetamount());
		}
		BigDecimal subtractVlaue2 = new BigDecimal(0);	
		//票的一边不全清,
		if(billF){
			// 未收款汇率=(未收款的本位币合计/未收款的金额的合计)
			subtractVlaue2 = billAmount.divide(sumUnreceivableamou,5,BigDecimal.ROUND_HALF_EVEN);
			
		}
		//款的一边不全清
		if(clearF){
			//未抵消收款汇率=未抵消收款的本位币合计/未抵消收款的金额的合计
			subtractVlaue2 = collectAmount.divide(sumUnoffsetamount,5,BigDecimal.ROUND_HALF_EVEN);			
		}
		return subtractVlaue2;
	}
	/**
	 * //判断哪一边不全清
	 * @param customSingleClear
	 * @return
	 */
	private String checkAllClear(CustomSingleClear customSingleClear){
		Set<UnClearCustomBill> unClearCustomBills = customSingleClear.getUnClearCustomBill();
		Set<UnClearCollect> unClearCollects = customSingleClear.getUnClearCollect();
		//判断哪一边不全清
		boolean billF = false;
		boolean clearF = false;
		
		
		// 重新计算未清金额(未收款、未付款等)
		for (UnClearCustomBill unClearCustomBill : unClearCustomBills)
		{
			
			
			// 发票总金额
			BigDecimal billamount = unClearCustomBill.getReceivableamount();
			// 发票已经审批完的，发票已清金额
			BigDecimal receivedamount = this.unclearCustomerService.getSumClearAmount(unClearCustomBill.getBillno(), BusinessState.ALL_COLLECT_PAIDUP);
			unClearCustomBill.setReceivedamount(receivedamount);
			unClearCustomBill.setUnreceivableamou(billamount.subtract(receivedamount));
			
			if(unClearCustomBill.getUnreceivableamou().compareTo(unClearCustomBill.getClearamount()) !=0){
				billF = true;
				
			}			
			
			
		}
		for (UnClearCollect unClearCollect : unClearCollects)
		{
			
			
			// 款总金额
			BigDecimal goodsamount = unClearCollect.getAmount();
			// 收款单已审批完的 ，已清票款
			BigDecimal clearedPaymentAmount = this.collectItemService.getSumClearAmount(unClearCollect.getCollectitemid(), BusinessState.ALL_COLLECT_PAIDUP);
			// 款未抵消金额
			unClearCollect.setUnoffsetamount(goodsamount.subtract(clearedPaymentAmount));
			
			if(unClearCollect.getUnoffsetamount().compareTo(unClearCollect.getNowclearamount()) !=0){
				clearF = true;
				
			}			
			
		}
		if(billF){
			return "billClear";
		}else{
			return "collectClear";
		}		
	}
	/**
	 * 取得taxCode
	 * @param customSingleClear
	 * @return
	 */
	public String getTaxCode(CustomSingleClear customSingleClear){
		Set<UnClearCustomBill> unClearCustomBills = customSingleClear.getUnClearCustomBill();
		Set<UnClearCollect> unClearCollects = customSingleClear.getUnClearCollect();
		//合同号列表
		List<String> contractNoList = new ArrayList<String>();
		String projectNo =" ";
		String contractNo = " ";
		// 重新计算未清金额(未收款、未付款等)
		for (UnClearCustomBill unClearCustomBill : unClearCustomBills)
		{
			// 发票总金额
			BigDecimal billamount = unClearCustomBill.getReceivableamount();
			// 发票已经审批完的，发票已清金额
			BigDecimal receivedamount = this.unclearCustomerService.getSumClearAmount(unClearCustomBill.getBillno(), BusinessState.ALL_COLLECT_PAIDUP);
			
			BigDecimal unreceivableamou = billamount.subtract(receivedamount);
			if(unreceivableamou.compareTo(unClearCustomBill.getClearamount()) !=0){
				
				projectNo = unClearCustomBill.getProject_no();
			}
			
			contractNo = unClearCustomBill.getContract_no();
			contractNoList.add(contractNo);
		}
		for (UnClearCollect unClearCollect : unClearCollects)
		{
			// 款总金额
			BigDecimal goodsamount = unClearCollect.getAmount();
			// 收款单已审批完的 ，已清票款
			BigDecimal clearedPaymentAmount = this.collectItemService.getSumClearAmount(unClearCollect.getCollectitemid(), BusinessState.ALL_COLLECT_PAIDUP);
			// 款未抵消金额			
			BigDecimal unoffsetamount = goodsamount.subtract(clearedPaymentAmount);
			if(unoffsetamount.compareTo(unClearCollect.getNowclearamount()) !=0){				
				projectNo = unClearCollect.getProject_no();
			}			
			contractNo = unClearCollect.getContract_no();
			contractNoList.add(contractNo);
		}
		String taxCode = " ";
		boolean taxCodeFlag=false;
		for(int i=0;i<contractNoList.size()-1;i++){
			String cn = contractNoList.get(i);
			String cn2 = contractNoList.get(i+1);
			if(null == cn || null == cn2){
				taxCodeFlag=true;
				break;
			}
			//是否全部合同号都一样
			if(!cn.equals(cn2))taxCodeFlag=true;
		}
		//如果合同都一样，taxcode取合同号，否则取能全清一边的立项号。
		if(taxCodeFlag  && !StringUtils.isNullBlank(projectNo)){
			taxCode = projectNo;
		}else{
			taxCode = contractNo;
		}
		return taxCode;
	}
	/**
	 * 处理客户清帐凭证行项目.
	 * 
	 * @param supplierSinglClear
	 * @param clearVoucher
	 * @param voucherItemList
	 * @param contractNos
	 * @param projectNos
	 * @param clearedContractNo
	 * @param clearedProjectNo
	 * @param oldVoucher
	 */
	private void getCustomClearVoucherItem(CustomSingleClear customSingleClear, Voucher clearVoucher, List<VoucherItem> voucherItemList, String contractNos, String projectNos, List<String> clearedContractNo, List<String> clearedProjectNo, String currency, BigDecimal billValue, BigDecimal itemValue,BigDecimal ajValue, String bukrs)
	{
		String costomer = customSingleClear.getCustom();
		// 分配项之和和票之和的差
		BigDecimal subtractVlaue = new BigDecimal("0");
		
		Set<UnClearCustomBill> unClearCustomBills = customSingleClear.getUnClearCustomBill();
		Set<UnClearCollect> unClearCollects = customSingleClear.getUnClearCollect();
		// 得到之前合同的票信息
//		List<ClearVoucherItemStruct> clearVoucherItemStructList = this.customerTitleJdbcDao.getCustomerBillInfo(costomer, contractNos, projectNos, bukrs);
//		for (ClearVoucherItemStruct clearVoucherItemStruct : clearVoucherItemStructList)
//		{
//			VoucherItem voucherItem = getClearVoucherItem(clearVoucherItemStruct);
//			voucherItem.setVoucher(clearVoucher);
//			billValue = billValue.add(clearVoucherItemStruct.getBwbje());
//			voucherItemList.add(voucherItem);
//		}

		// 得到本次票的行项目信息
		Iterator<UnClearCustomBill> unClearCustomBillsIt = unClearCustomBills.iterator();
		while (unClearCustomBillsIt.hasNext())
		{
			UnClearCustomBill unClearCustomBill = unClearCustomBillsIt.next();
			if (clearedContractNo.contains(unClearCustomBill.getContract_no()))
			{
				log.debug("合同号:" + unClearCustomBill.getContract_no() + "已经存在已清合同列表," + contractNos);
			}
			if (clearedProjectNo.contains(unClearCustomBill.getProject_no()))
			{
				log.debug("合同号:" + unClearCustomBill.getProject_no() + "已经存在已清合同列表," + projectNos);
			}

			if (!clearedContractNo.contains(unClearCustomBill.getContract_no()) && !clearedProjectNo.contains(unClearCustomBill.getProject_no()))
			{
//				ClearVoucherItemStruct clearVoucherItemStruct = this.customerTitleJdbcDao.getCurrectBillInfo(unClearCustomBill.getBillno());
//				VoucherItem voucherItem = getClearVoucherItem(clearVoucherItemStruct);
//				voucherItem.setVoucher(clearVoucher);
//				billValue = billValue.add(clearVoucherItemStruct.getBwbje());
//				voucherItemList.add(voucherItem);
				
				ClearVoucherItemStruct clearVoucherItemStruct = new ClearVoucherItemStruct();
				CustomerTitle customerTitle = this.customerTitleJdbcDao.getByCustomertitleid(unClearCustomBill.getCustomertitleid());				 
				clearVoucherItemStruct.setRowno(customerTitle.getBuzei());
				clearVoucherItemStruct.setVoucherno(unClearCustomBill.getVoucherno());
				clearVoucherItemStruct.setYear(customerTitle.getGjahr());
				VoucherItem voucherItem = getClearVoucherItem(clearVoucherItemStruct);						
				billValue = billValue.add(customerTitle.getBwbje());	
				voucherItem.setVoucher(clearVoucher);
				voucherItemList.add(voucherItem);
			}
		}
		//得到之前汇损，或调整金的voucheritem
//		List<VoucherItem> voucherItems = voucherItemJdbcDao.getVoucherItems(customSingleClear.getCustom(), "");
//		for(VoucherItem voucherItem2 : voucherItems){
//			String rowNumber = voucherItem2.getRownumber();
//			if(rowNumber.length()==2)rowNumber="0" + rowNumber;			
//			if(rowNumber.length()==1)rowNumber="00" + rowNumber;
//			CustomerTitle customerTitle = customerTitleJdbcDao.getCustomerTitle(voucherItem2.getVoucher().getCompanycode(), voucherItem2.getVoucher().getVoucherno(), voucherItem2.getVoucher().getFiyear(), rowNumber);
//			if(null !=customerTitle && "1".equals(customerTitle.getIscleared())){
//				ClearVoucherItemStruct clearVoucherItemStruct = new ClearVoucherItemStruct();
//				clearVoucherItemStruct.setBwbje(voucherItem2.getAmount2());
//				clearVoucherItemStruct.setRowno(voucherItem2.getRownumber());
//				clearVoucherItemStruct.setSubject(voucherItem2.getSubject());
//				clearVoucherItemStruct.setVoucherno(voucherItem2.getVoucher().getVoucherno());
//				clearVoucherItemStruct.setYear(voucherItem2.getVoucher().getFiyear());
//				if("01".equals(voucherItem2.getCheckcode()) || "08".equals(voucherItem2.getCheckcode())){
//					billValue = billValue.add(clearVoucherItemStruct.getBwbje());
//				}else if("11".equals(voucherItem2.getCheckcode()) || "15".equals(voucherItem2.getCheckcode())){
//					itemValue = itemValue.add(clearVoucherItemStruct.getBwbje());
//				}
//				VoucherItem voucherItem = getClearVoucherItem(clearVoucherItemStruct);				
//				voucherItem.setVoucher(clearVoucher);
//				voucherItemList.add(voucherItem);
//			}
//		}
		//取得SAP手工做账的被部分清的汇损凭证
//		List<ClearVoucherItemStruct> clearVoucherItemStructList2 = this.customerTitleJdbcDao.getCustomerVoucherInfo(customSingleClear.getCustom(), contractNos, projectNos, bukrs);
//		for (ClearVoucherItemStruct clearVoucherItemStruct : clearVoucherItemStructList2)
//		{
//			VoucherItem voucherItem = voucherItemJdbcDao.getVoucherItem(clearVoucherItemStruct.getVoucherno(), bukrs, clearVoucherItemStruct.getYear(), clearVoucherItemStruct.getRowno());
//			//如果没有数据，说明只有是SAP手工做账，如果有数据是外围做账，不在这边处理，前面(得到之前汇损，或调整金的voucheritem)已做好了。
//			if(null == voucherItem ){
//				VoucherItem voucherItem2 = getClearVoucherItem(clearVoucherItemStruct);
//				voucherItem2.setVoucher(clearVoucher);
//				itemValue = itemValue.add(clearVoucherItemStruct.getBwbje());
//				voucherItemList.add(voucherItem2);
//			}
//		}
//		
//		// 得到之前的款分配信息
//		List<ClearVoucherItemStruct> clearVoucherItemStructs2 = this.customerTitleJdbcDao.getCustomerCollectItemInfo(costomer, contractNos, projectNos, bukrs);
//		for (ClearVoucherItemStruct clearVoucherItemStruct : clearVoucherItemStructs2)
//		{
//			VoucherItem voucherItem = getClearVoucherItem(clearVoucherItemStruct);
//			voucherItem.setVoucher(clearVoucher);
//			itemValue = itemValue.add(clearVoucherItemStruct.getBwbje());
//			voucherItemList.add(voucherItem);
//		}
//		
//		//退款的行项目信息
//		List<ClearVoucherItemStruct> clearVoucherItemStructList3 = this.customerTitleJdbcDao.getRefundItemByCustomerNo(customSingleClear.getCustom(), BusinessState.ALL_BILLCLEAR_PAIDUP, projectNos,contractNos,bukrs);
//		for (ClearVoucherItemStruct clearVoucherItemStruct : clearVoucherItemStructList3)
//		{
//			VoucherItem voucherItem = getClearVoucherItem(clearVoucherItemStruct);
//			billValue = billValue.add(clearVoucherItemStruct.getBwbje());
//			voucherItem.setVoucher(clearVoucher);
//			voucherItemList.add(voucherItem);
//		}
//		
		itemValue = itemValue.add(this.customerTitleJdbcDao.getSumBwbje(customSingleClear.getCustom(), contractNos, projectNos, bukrs, "H",customSingleClear.getCurrencytext(),customSingleClear.getDepid(),customSingleClear.getSubject()));
		billValue = billValue.add(this.customerTitleJdbcDao.getSumBwbje(customSingleClear.getCustom(), contractNos, projectNos, bukrs, "S",customSingleClear.getCurrencytext(),customSingleClear.getDepid(),customSingleClear.getSubject()));
		
		// 得到之前所有外围已清的凭证行项目信息
		List<ClearVoucherItemStruct> clearVoucherItemStructList = this.customerTitleJdbcDao.getCustomerVoucherInfo2(costomer, contractNos, projectNos, bukrs,customSingleClear.getDepid(),customSingleClear.getSubject(),customSingleClear.getCurrencytext());
		for (ClearVoucherItemStruct clearVoucherItemStruct : clearVoucherItemStructList)
		{
			VoucherItem voucherItem = getClearVoucherItem(clearVoucherItemStruct);
			voucherItem.setVoucher(clearVoucher);			
			voucherItemList.add(voucherItem);
		}
		
		// 得到本次分配项的行项目信息
		Iterator<UnClearCollect> unClearCollectsIt = unClearCollects.iterator();
		while (unClearCollectsIt.hasNext())
		{
			UnClearCollect unClearCollect = unClearCollectsIt.next();
			if (clearedContractNo.contains(unClearCollect.getContract_no()))
			{
				log.debug("合同号:" + unClearCollect.getContract_no() + "已经存在已清合同列表," + contractNos);
			}
			if (clearedProjectNo.contains(unClearCollect.getProject_no()))
			{
				log.debug("合同号:" + unClearCollect.getProject_no() + "已经存在已清合同列表," + projectNos);
			}

			if (!clearedContractNo.contains(unClearCollect.getContract_no()) && !clearedProjectNo.contains(unClearCollect.getProject_no()))
			{
//				Voucher oldVoucher = this.voucherService.getVoucher(unClearCollect.getCollectid());
//				Set<VoucherItem> oldVoucherItem = oldVoucher.getVoucherItem();
//				Iterator<VoucherItem> payVoucherItemit = oldVoucherItem.iterator();
//				while (payVoucherItemit.hasNext())
//				{
//					VoucherItem oldItem = payVoucherItemit.next();
//					if (oldItem.getVoucher().getBusinessid().equals(unClearCollect.getCollectid()) && oldItem.getBusinessitemid().equals(unClearCollect.getCollectitemid()))
//					{
//						ClearVoucherItemStruct clearVoucherItemStruct = new ClearVoucherItemStruct();
//						clearVoucherItemStruct.setRowno(oldItem.getRownumber());
//						clearVoucherItemStruct.setVoucherno(oldVoucher.getVoucherno());
//						clearVoucherItemStruct.setYear(oldItem.getVoucher().getFiyear());
//						VoucherItem voucherItem = getClearVoucherItem(clearVoucherItemStruct);
//						voucherItem.setVoucher(clearVoucher);
//						itemValue = itemValue.add(oldItem.getAmount2());
//						voucherItemList.add(voucherItem);
//					}
//				}
				ClearVoucherItemStruct clearVoucherItemStruct = new ClearVoucherItemStruct();
				CustomerTitle customerTitle = this.customerTitleJdbcDao.getByCustomertitleid(unClearCollect.getCustomertitleid());				 
				clearVoucherItemStruct.setRowno(customerTitle.getBuzei());
				clearVoucherItemStruct.setVoucherno(unClearCollect.getVoucherno());
				clearVoucherItemStruct.setYear(customerTitle.getGjahr());
				VoucherItem voucherItem = getClearVoucherItem(clearVoucherItemStruct);						
				itemValue = itemValue.add(customerTitle.getBwbje());	
				voucherItem.setVoucher(clearVoucher);
				voucherItemList.add(voucherItem);
			}
		}
		
		// 判断是否外币,有调整金
		if (!"CNY".equalsIgnoreCase(currency))
		{
			subtractVlaue = itemValue.subtract(billValue);
			subtractVlaue = subtractVlaue.add(ajValue);
			if (subtractVlaue.abs().compareTo(new BigDecimal(0)) != 0)
			{
				
				VoucherItem supplierVoucherItem = getProfitAndLossVoucherItem(customSingleClear, subtractVlaue, currency,"", "1");
				supplierVoucherItem.setVoucher(clearVoucher);
				voucherItemList.add(supplierVoucherItem);

				VoucherItem profOrLossVoucherItem = getProfitAndLossVoucherItem(customSingleClear, subtractVlaue, currency,"", "2");
				profOrLossVoucherItem.setVoucher(clearVoucher);
				voucherItemList.add(profOrLossVoucherItem);
			}
		}
	}

	/**
	 * 处理合同下清帐凭证行项目.
	 * 
	 * @param supplierSinglClear
	 * @param clearedContractNo
	 * @param clearVoucher
	 * @param voucherItemList
	 * @param oldVoucher
	 * @return
	 */
	private String getContractClearVoucherItem(CustomSingleClear customSingleClear, List<String> clearedContractNo, Voucher clearVoucher, List<VoucherItem> voucherItemList, String currency, BigDecimal billValue, BigDecimal itemValue,BigDecimal ajValue)
	{
		String contractNos = "";
		// 分配项之和和票之和的差
		BigDecimal subtractVlaue = new BigDecimal("0");
		Set<UnClearCustomBill> unClearCustomBills = customSingleClear.getUnClearCustomBill();
		Set<UnClearCollect> unClearCollects = customSingleClear.getUnClearCollect();
		// 循环所有合同号。
		for (String conntractNo : clearedContractNo)
		{
			contractNos = contractNos + "'" + conntractNo + "',";
			// 得到之前合同的票信息(可考虑移到循环外提高性能。)
			List<ClearVoucherItemStruct> clearVoucherItemStructList = this.customerTitleJdbcDao.getContractBillInfo(customSingleClear.getCustom(),conntractNo);
			for (ClearVoucherItemStruct clearVoucherItemStruct : clearVoucherItemStructList)
			{
				VoucherItem voucherItem = getClearVoucherItem(clearVoucherItemStruct);
				billValue = billValue.add(clearVoucherItemStruct.getBwbje());
				voucherItem.setVoucher(clearVoucher);
				voucherItemList.add(voucherItem);
			}
			//得到之前汇损，或调整金的voucheritem
			List<VoucherItem> voucherItems = voucherItemJdbcDao.getVoucherItems(customSingleClear.getCustom(), conntractNo);
			for(VoucherItem voucherItem2 : voucherItems){
				String rowNumber = voucherItem2.getRownumber();
				if(rowNumber.length()==2)rowNumber="0" + rowNumber;			
				if(rowNumber.length()==1)rowNumber="00" + rowNumber;
				CustomerTitle customerTitle = customerTitleJdbcDao.getCustomerTitle(voucherItem2.getVoucher().getCompanycode(), voucherItem2.getVoucher().getVoucherno(), voucherItem2.getVoucher().getFiyear(), rowNumber);
				if(null !=customerTitle && "1".equals(customerTitle.getIscleared())){
					ClearVoucherItemStruct clearVoucherItemStruct = new ClearVoucherItemStruct();
					clearVoucherItemStruct.setBwbje(voucherItem2.getAmount2());
					clearVoucherItemStruct.setRowno(voucherItem2.getRownumber());
					clearVoucherItemStruct.setSubject(voucherItem2.getSubject());
					clearVoucherItemStruct.setVoucherno(voucherItem2.getVoucher().getVoucherno());
					clearVoucherItemStruct.setYear(voucherItem2.getVoucher().getFiyear());
					if("01".equals(voucherItem2.getCheckcode()) || "08".equals(voucherItem2.getCheckcode())){
						billValue = billValue.add(clearVoucherItemStruct.getBwbje());
					}else if("11".equals(voucherItem2.getCheckcode()) || "15".equals(voucherItem2.getCheckcode())){
						itemValue = itemValue.add(clearVoucherItemStruct.getBwbje());
					}
					VoucherItem voucherItem = getClearVoucherItem(clearVoucherItemStruct);				
					voucherItem.setVoucher(clearVoucher);
					voucherItemList.add(voucherItem);
				}
			}
			// 得到本次票的行项目信息
			Iterator<UnClearCustomBill> unClearCustomBillsIt = unClearCustomBills.iterator();
			while (unClearCustomBillsIt.hasNext())
			{
				UnClearCustomBill unClearCustomBill = unClearCustomBillsIt.next();
				if (conntractNo.equals(unClearCustomBill.getContract_no()) || unClearCustomBill.getReceivableamount().compareTo(new BigDecimal("0")) ==0)
				{
//					ClearVoucherItemStruct clearVoucherItemStruct = this.customerTitleJdbcDao.getCurrectBillInfo(unClearCustomBill.getBillno());
//					VoucherItem voucherItem = getClearVoucherItem(clearVoucherItemStruct);
//					billValue = billValue.add(clearVoucherItemStruct.getBwbje());
//					voucherItem.setVoucher(clearVoucher);
//					voucherItemList.add(voucherItem);
					
					ClearVoucherItemStruct clearVoucherItemStruct = new ClearVoucherItemStruct();
					CustomerTitle customerTitle = this.customerTitleJdbcDao.getByCustomertitleid(unClearCustomBill.getCustomertitleid());				 
					clearVoucherItemStruct.setRowno(customerTitle.getBuzei());
					clearVoucherItemStruct.setVoucherno(unClearCustomBill.getVoucherno());
					clearVoucherItemStruct.setYear(customerTitle.getGjahr());
					VoucherItem voucherItem = getClearVoucherItem(clearVoucherItemStruct);						
					billValue = billValue.add(customerTitle.getBwbje());	
					voucherItem.setVoucher(clearVoucher);
					voucherItemList.add(voucherItem);
				}
			}

			// 得到之前的款分配信息(可考虑移到循环外提高性能。)
			List<ClearVoucherItemStruct> clearVoucherItemStructList2 = this.customerTitleJdbcDao.getContractCollectItemInfo(customSingleClear.getCustom(),conntractNo);
			for (ClearVoucherItemStruct clearVoucherItemStruct : clearVoucherItemStructList2)
			{
				VoucherItem voucherItem = getClearVoucherItem(clearVoucherItemStruct);
				itemValue = itemValue.add(clearVoucherItemStruct.getBwbje());
				voucherItem.setVoucher(clearVoucher);
				voucherItemList.add(voucherItem);
			}
			//退款的行项目信息
			List<ClearVoucherItemStruct> clearVoucherItemStructList3 = this.customerTitleJdbcDao.getRefundItemByCustomerNoAndContractNo(customSingleClear.getCustom(), BusinessState.ALL_BILLCLEAR_PAIDUP, conntractNo);
			for (ClearVoucherItemStruct clearVoucherItemStruct : clearVoucherItemStructList3)
			{
				VoucherItem voucherItem = getClearVoucherItem(clearVoucherItemStruct);
				billValue = billValue.add(clearVoucherItemStruct.getBwbje());
				voucherItem.setVoucher(clearVoucher);
				voucherItemList.add(voucherItem);
			}
			// 得到本次分配项的行项目信息
			Iterator<UnClearCollect> unClearCollectsIt = unClearCollects.iterator();
			while (unClearCollectsIt.hasNext())
			{
				UnClearCollect unClearCollect = unClearCollectsIt.next();
				if (conntractNo.equals(unClearCollect.getContract_no()) || unClearCollect.getAmount().compareTo(new BigDecimal("0")) ==0)
				{
//					Voucher oldVoucher = this.voucherService.getVoucher(unClearCollect.getCollectid());
//					Set<VoucherItem> oldVoucherItem = oldVoucher.getVoucherItem();
//					Iterator<VoucherItem> payVoucherItemit = oldVoucherItem.iterator();
//					while (payVoucherItemit.hasNext())
//					{
//						VoucherItem oldItem = payVoucherItemit.next();
//						if (oldItem.getVoucher().getBusinessid().equals(unClearCollect.getCollectid()) && oldItem.getBusinessitemid().equals(unClearCollect.getCollectitemid()))
//						{
//							ClearVoucherItemStruct clearVoucherItemStruct = new ClearVoucherItemStruct();
//							clearVoucherItemStruct.setRowno(oldItem.getRownumber());
//							clearVoucherItemStruct.setVoucherno(unClearCollect.getVoucherno());
//							clearVoucherItemStruct.setYear(oldItem.getVoucher().getFiyear());
//							VoucherItem voucherItem = getClearVoucherItem(clearVoucherItemStruct);
//							itemValue = itemValue.add(oldItem.getAmount2());
//							voucherItem.setVoucher(clearVoucher);
//							voucherItemList.add(voucherItem);
//						}
//					}
					ClearVoucherItemStruct clearVoucherItemStruct = new ClearVoucherItemStruct();
					CustomerTitle customerTitle = this.customerTitleJdbcDao.getByCustomertitleid(unClearCollect.getCustomertitleid());				 
					clearVoucherItemStruct.setRowno(customerTitle.getBuzei());
					clearVoucherItemStruct.setVoucherno(unClearCollect.getVoucherno());
					clearVoucherItemStruct.setYear(customerTitle.getGjahr());
					VoucherItem voucherItem = getClearVoucherItem(clearVoucherItemStruct);						
					itemValue = itemValue.add(customerTitle.getBwbje());	
					voucherItem.setVoucher(clearVoucher);
					voucherItemList.add(voucherItem);
				}
			}
			// 判断是否外币,有调整金
			if (!"CNY".equalsIgnoreCase(currency))
			{
				subtractVlaue = itemValue.subtract(billValue);
				subtractVlaue = subtractVlaue.add(ajValue);
				if (subtractVlaue.abs().compareTo(new BigDecimal(0)) != 0)
				{
					
					VoucherItem supplierVoucherItem = getProfitAndLossVoucherItem(customSingleClear, subtractVlaue, currency,conntractNo, "1");
					supplierVoucherItem.setVoucher(clearVoucher);
					voucherItemList.add(supplierVoucherItem);

					VoucherItem profOrLossVoucherItem = getProfitAndLossVoucherItem(customSingleClear, subtractVlaue, currency,conntractNo, "2");
					profOrLossVoucherItem.setVoucher(clearVoucher);
					voucherItemList.add(profOrLossVoucherItem);
				}
			}
		}
		return contractNos;
	}

	/**
	 * 处理立项下清帐凭证行项目.
	 * 
	 * @param supplierSinglClear
	 * @param clearVoucher
	 * @param voucherItemList
	 * @param contractNos
	 * @param clearedContractNo
	 * @param clearedProjectNo
	 * @param oldVoucher
	 * @return
	 */
	private String getProjectClearVoucherItem(CustomSingleClear customSingleClear, Voucher clearVoucher, List<VoucherItem> voucherItemList, String contractNos, List<String> clearedContractNo, List<String> clearedProjectNo, String currency, BigDecimal billValue, BigDecimal itemValue ,BigDecimal ajValue)
	{
		String projectNos = "";
		// 分配项之和和票之和的差
		BigDecimal subtractVlaue = new BigDecimal("0");
		Set<UnClearCustomBill> unClearCustomBills = customSingleClear.getUnClearCustomBill();
		Set<UnClearCollect> unClearCollects = customSingleClear.getUnClearCollect();
		// 循环所有立項号。
		for (String projectNo : clearedProjectNo)
		{
			projectNos = projectNos + "'" + projectNo + "',";
			// 得到之前合同的票信息
			List<ClearVoucherItemStruct> clearVoucherItemStructs = this.customerTitleJdbcDao.getProjectBillInfo(customSingleClear.getCustom(),projectNo, contractNos);
			for (ClearVoucherItemStruct clearVoucherItemStruct : clearVoucherItemStructs)
			{
				VoucherItem voucherItem = getClearVoucherItem(clearVoucherItemStruct);
				voucherItem.setVoucher(clearVoucher);
				billValue = billValue.add(clearVoucherItemStruct.getBwbje());
				voucherItemList.add(voucherItem);
			}

			// 得到本次票的行项目信息
			Iterator<UnClearCustomBill> unClearCustomBillsIt = unClearCustomBills.iterator();
			while (unClearCustomBillsIt.hasNext())
			{
				UnClearCustomBill unClearCustomBill = unClearCustomBillsIt.next();
				if (clearedContractNo.contains(unClearCustomBill.getContract_no()))
				{
					log.debug("合同号:" + unClearCustomBill.getContract_no() + "已经存在已清合同列表," + contractNos);
				}
				if ((projectNo.equals(unClearCustomBill.getProject_no()) && !clearedContractNo.contains(unClearCustomBill.getContract_no())) ||  unClearCustomBill.getReceivableamount().compareTo(new BigDecimal("0")) ==0)
				{
//					ClearVoucherItemStruct clearVoucherItemStruct = this.customerTitleJdbcDao.getCurrectBillInfo(unClearCustomBill.getBillno());
//					VoucherItem voucherItem = getClearVoucherItem(clearVoucherItemStruct);
//					voucherItem.setVoucher(clearVoucher);
//					billValue = billValue.add(clearVoucherItemStruct.getBwbje());
//					voucherItemList.add(voucherItem);
					
					ClearVoucherItemStruct clearVoucherItemStruct = new ClearVoucherItemStruct();
					CustomerTitle customerTitle = this.customerTitleJdbcDao.getByCustomertitleid(unClearCustomBill.getCustomertitleid());				 
					clearVoucherItemStruct.setRowno(customerTitle.getBuzei());
					clearVoucherItemStruct.setVoucherno(unClearCustomBill.getVoucherno());
					clearVoucherItemStruct.setYear(customerTitle.getGjahr());
					VoucherItem voucherItem = getClearVoucherItem(clearVoucherItemStruct);						
					billValue = billValue.add(customerTitle.getBwbje());	
					voucherItem.setVoucher(clearVoucher);
					voucherItemList.add(voucherItem);
				}
			}
			//得到之前汇损，或调整金的voucheritem
			List<VoucherItem> voucherItems = voucherItemJdbcDao.getVoucherItems(customSingleClear.getCustom(), projectNo);
			for(VoucherItem voucherItem2 : voucherItems){
				String rowNumber = voucherItem2.getRownumber();
				if(rowNumber.length()==2)rowNumber="0" + rowNumber;			
				if(rowNumber.length()==1)rowNumber="00" + rowNumber;
				CustomerTitle customerTitle = customerTitleJdbcDao.getCustomerTitle(voucherItem2.getVoucher().getCompanycode(), voucherItem2.getVoucher().getVoucherno(), voucherItem2.getVoucher().getFiyear(), rowNumber);
				if(null !=customerTitle && "1".equals(customerTitle.getIscleared())){
					ClearVoucherItemStruct clearVoucherItemStruct = new ClearVoucherItemStruct();
					clearVoucherItemStruct.setBwbje(voucherItem2.getAmount2());
					clearVoucherItemStruct.setRowno(voucherItem2.getRownumber());
					clearVoucherItemStruct.setSubject(voucherItem2.getSubject());
					clearVoucherItemStruct.setVoucherno(voucherItem2.getVoucher().getVoucherno());
					clearVoucherItemStruct.setYear(voucherItem2.getVoucher().getFiyear());
					if("01".equals(voucherItem2.getCheckcode()) || "08".equals(voucherItem2.getCheckcode())){
						billValue = billValue.add(clearVoucherItemStruct.getBwbje());
					}else if("11".equals(voucherItem2.getCheckcode()) || "15".equals(voucherItem2.getCheckcode())){
						itemValue = itemValue.add(clearVoucherItemStruct.getBwbje());
					}
					VoucherItem voucherItem = getClearVoucherItem(clearVoucherItemStruct);				
					voucherItem.setVoucher(clearVoucher);
					voucherItemList.add(voucherItem);
				}
			}
			// 得到之前的款分配信息
			List<ClearVoucherItemStruct> clearVoucherItemStructs2 = this.customerTitleJdbcDao.getProjectCollectItemInfo(customSingleClear.getCustom(),projectNo, contractNos);
			for (ClearVoucherItemStruct clearVoucherItemStruct : clearVoucherItemStructs2)
			{
				VoucherItem voucherItem = getClearVoucherItem(clearVoucherItemStruct);
				voucherItem.setVoucher(clearVoucher);
				itemValue = itemValue.add(clearVoucherItemStruct.getBwbje());
				voucherItemList.add(voucherItem);
			}
			//退款的行项目信息
			List<ClearVoucherItemStruct> clearVoucherItemStructList3 = this.customerTitleJdbcDao.getRefundItemByCustomerNoAndProjectNo(customSingleClear.getCustom(), BusinessState.ALL_BILLCLEAR_PAIDUP, projectNo,contractNos);
			for (ClearVoucherItemStruct clearVoucherItemStruct : clearVoucherItemStructList3)
			{
				VoucherItem voucherItem = getClearVoucherItem(clearVoucherItemStruct);
				billValue = billValue.add(clearVoucherItemStruct.getBwbje());
				voucherItem.setVoucher(clearVoucher);
				voucherItemList.add(voucherItem);
			}
			// 得到本次分配项的行项目信息
			Iterator<UnClearCollect> unClearCollectsIt = unClearCollects.iterator();
			while (unClearCollectsIt.hasNext())
			{
				UnClearCollect unClearCollect = unClearCollectsIt.next();
				if (clearedContractNo.contains(unClearCollect.getContract_no()))
				{
					log.debug("合同号:" + unClearCollect.getContract_no() + "已经存在已清合同列表," + contractNos);
				}
				if ((projectNo.equals(unClearCollect.getProject_no()) && !clearedContractNo.contains(unClearCollect.getContract_no())) || unClearCollect.getAmount().compareTo(new BigDecimal("0")) ==0)
				{
//					Voucher oldVoucher = this.voucherService.getVoucher(unClearCollect.getCollectid());
//					Set<VoucherItem> oldVoucherItem = oldVoucher.getVoucherItem();
//					Iterator<VoucherItem> payVoucherItemit = oldVoucherItem.iterator();
//					while (payVoucherItemit.hasNext())
//					{
//						VoucherItem oldItem = payVoucherItemit.next();
//						if (oldItem.getVoucher().getBusinessid().equals(unClearCollect.getCollectid()) && oldItem.getBusinessitemid().equals(unClearCollect.getCollectitemid()))
//						{
//							ClearVoucherItemStruct clearVoucherItemStruct = new ClearVoucherItemStruct();
//							clearVoucherItemStruct.setRowno(oldItem.getRownumber());
//							clearVoucherItemStruct.setVoucherno(unClearCollect.getVoucherno());
//							clearVoucherItemStruct.setYear(oldItem.getVoucher().getFiyear());
//							VoucherItem voucherItem = getClearVoucherItem(clearVoucherItemStruct);
//							itemValue = itemValue.add(oldItem.getAmount2());
//							voucherItem.setVoucher(clearVoucher);
//							voucherItemList.add(voucherItem);
//						}
//					}
					ClearVoucherItemStruct clearVoucherItemStruct = new ClearVoucherItemStruct();
					CustomerTitle customerTitle = this.customerTitleJdbcDao.getByCustomertitleid(unClearCollect.getCustomertitleid());				 
					clearVoucherItemStruct.setRowno(customerTitle.getBuzei());
					clearVoucherItemStruct.setVoucherno(unClearCollect.getVoucherno());
					clearVoucherItemStruct.setYear(customerTitle.getGjahr());
					VoucherItem voucherItem = getClearVoucherItem(clearVoucherItemStruct);						
					itemValue = itemValue.add(customerTitle.getBwbje());	
					voucherItem.setVoucher(clearVoucher);
					voucherItemList.add(voucherItem);
				}
			}
			// 判断是否外币,有调整金
			if (!"CNY".equalsIgnoreCase(currency))
			{
				subtractVlaue = itemValue.subtract(billValue);
				subtractVlaue = subtractVlaue.add(ajValue);
				if (subtractVlaue.abs().compareTo(new BigDecimal(0)) != 0)
				{
					
					VoucherItem supplierVoucherItem = getProfitAndLossVoucherItem(customSingleClear, subtractVlaue, currency,projectNo, "1");
					supplierVoucherItem.setVoucher(clearVoucher);
					voucherItemList.add(supplierVoucherItem);

					VoucherItem profOrLossVoucherItem = getProfitAndLossVoucherItem(customSingleClear, subtractVlaue, currency,projectNo, "2");
					profOrLossVoucherItem.setVoucher(clearVoucher);
					voucherItemList.add(profOrLossVoucherItem);
				}
			}

		}

		return projectNos;
	}

	/**
	 * 判断单据是否可全清帐
	 * 
	 * @param supplierSinglClear
	 * @return
	 */
	public boolean judgeIsAllClear(CustomSingleClear customSingleClear)
	{
		Set<UnClearCustomBill> unClearCustomBills = customSingleClear.getUnClearCustomBill();
		Set<UnClearCollect> unClearCollects = customSingleClear.getUnClearCollect();
		// 应付款总计
		BigDecimal sumBillAmount = new BigDecimal(0);
		// 清帐金额总计
		BigDecimal sumBillClearAmount = new BigDecimal(0);
		for (UnClearCustomBill unClearCustomBill : unClearCustomBills)
		{
			sumBillAmount = sumBillAmount.add(unClearCustomBill.getReceivableamount());
			sumBillClearAmount = sumBillClearAmount.add(unClearCustomBill.getClearamount());
		}
		// 收款单金额总计
		BigDecimal sumFundAmount = new BigDecimal(0);
		// 本次抵消金额总计
		BigDecimal sumFundClearAmount = new BigDecimal(0);
		for (UnClearCollect unClearCollect : unClearCollects)
		{
			sumFundAmount = sumFundAmount.add(unClearCollect.getAmount());
			sumFundClearAmount = sumFundClearAmount.add(unClearCollect.getNowclearamount());
		}

		log.debug("票sumBillAmount:" + sumBillAmount + ";sumBillClearAmount:" + sumBillClearAmount);
		log.debug("款sumFundAmount:" + sumFundAmount + ";sumFundClearAmount:" + sumFundClearAmount);

		if (sumBillAmount.compareTo(sumBillClearAmount) == 0 && sumFundAmount.compareTo(sumFundClearAmount) == 0)
		{
			return true;
		}

		return false;
	}

	/**
	 * 获取合同号列表
	 * 
	 * @param unClearCustomBills
	 * @return
	 */
	private List<String> getContractNoList(Set<UnClearCustomBill> unClearCustomBills)
	{
		String contractNo = null;
		List<String> contractNos = new ArrayList<String>();
		Iterator<UnClearCustomBill> it = unClearCustomBills.iterator();
		while (it.hasNext())
		{
			UnClearCustomBill unClearCustomBill = it.next();
			if (contractNo == null || !contractNo.equals(unClearCustomBill.getContract_no()))
			{
				contractNos.add(unClearCustomBill.getContract_no());
			}
			contractNo = unClearCustomBill.getContract_no();
		}
		return contractNos;
	}

	/**
	 * 获取合同号列表
	 * 
	 * @param unClearCustomBills
	 * @return
	 */
	private List<String> getProjectNoList(Set<UnClearCustomBill> unClearCustomBills)
	{
		String projectNo = null;
		List<String> projectNos = new ArrayList<String>();
		Iterator<UnClearCustomBill> it = unClearCustomBills.iterator();
		while (it.hasNext())
		{
			UnClearCustomBill unClearCustomBill = it.next();
			if (projectNo == null || !projectNo.equals(unClearCustomBill.getProject_no()))
			{
				projectNos.add(unClearCustomBill.getProject_no());
			}
			projectNo = unClearCustomBill.getProject_no();
		}
		return projectNos;
	}

	/**
	 * 判断单据下合同是否可全清帐
	 * 
	 * @param customSingleClear
	 * @return
	 */
	public List<String> judgeContractIsAllClear(CustomSingleClear customSingleClear)
	{
		List<String> clearedContractNo = new ArrayList<String>();
		Set<UnClearCustomBill> unClearCustomBills = customSingleClear.getUnClearCustomBill();
		Set<UnClearCollect> unClearCollects = customSingleClear.getUnClearCollect();

		// 重新计算未清金额(未收款、未付款等)
		for (UnClearCustomBill unClearCustomBill : unClearCustomBills)
		{
			// 发票总金额
			BigDecimal billamount = unClearCustomBill.getReceivableamount();
			// 发票已经审批完的，发票已清金额
			BigDecimal receivedamount = this.unclearCustomerService.getSumClearAmount(unClearCustomBill.getBillno(), BusinessState.ALL_COLLECT_PAIDUP);
			if(!StringUtils.isNullBlank(unClearCustomBill.getPaymentitemid())){
				//根据付款itemid取得供应商退款纯代理金额合计
				BigDecimal receivedamount2 = this.paymentItemService.getRefundmentAmount(unClearCustomBill.getPaymentitemid());
				receivedamount = receivedamount.add(receivedamount2);
			}
			unClearCustomBill.setReceivedamount(receivedamount);
			unClearCustomBill.setUnreceivableamou(billamount.subtract(receivedamount));
		}
		for (UnClearCollect unClearCollect : unClearCollects)
		{
			//如果有保证金不能在合同层，和立项层清帐
			if(unClearCollect.getSuretybond().compareTo(new BigDecimal(0)) != 0){
				return clearedContractNo;
			}
			// 款总金额
			BigDecimal goodsamount = unClearCollect.getAmount();
			// 收费款单已审批完的 ，已清票款
			BigDecimal clearedPaymentAmount = this.collectItemService.getSumClearAmount(unClearCollect.getCollectitemid(), BusinessState.ALL_COLLECT_PAIDUP);
			// 款未抵消金额
			unClearCollect.setUnoffsetamount(goodsamount.subtract(clearedPaymentAmount));
		}

		// 取得票下合同列表
		List<String> contractNos = getContractNoList(unClearCustomBills);
		if (null != contractNos && contractNos.size() > 0)
		{
			
			log.debug("共有" + contractNos.size() + "个合同：");
			for (String contractNo : contractNos)
			{
				//没有合同号就不用
				if(StringUtils.isNullBlank(contractNo))continue;
				
				// 合同下(未付款、未收款)合计
				BigDecimal billUnClearAmount = new BigDecimal(0);
				// 合同下清帐金额合计
				BigDecimal billClearAmount = new BigDecimal(0);
				//应收款金额
				BigDecimal receivableamount = new BigDecimal(0);
				
				BigDecimal amount = new BigDecimal(0);
				//调整金金额
				BigDecimal ajAmount = new BigDecimal(0);
				
				//得到之前汇损，或调整金的voucheritem
				List<VoucherItem> voucherItems = voucherItemJdbcDao.getVoucherItems(customSingleClear.getCustom(), contractNo);
				for(VoucherItem voucherItem2 : voucherItems){
					String rowNumber = voucherItem2.getRownumber();
					if(rowNumber.length()==2)rowNumber="0" + rowNumber;			
					if(rowNumber.length()==1)rowNumber="00" + rowNumber;
					CustomerTitle customerTitle = customerTitleJdbcDao.getCustomerTitle(voucherItem2.getVoucher().getCompanycode(), voucherItem2.getVoucher().getVoucherno(), voucherItem2.getVoucher().getFiyear(), rowNumber);
					if(null !=customerTitle && "1".equals(customerTitle.getIscleared())){
						if("01".equals(voucherItem2.getCheckcode()) || "08".equals(voucherItem2.getCheckcode())){
							ajAmount = ajAmount.subtract(voucherItem2.getAmount());
						}else if("11".equals(voucherItem2.getCheckcode()) || "15".equals(voucherItem2.getCheckcode())){
							ajAmount = ajAmount.add(voucherItem2.getAmount());
						}					
					}
				}
				
				for (UnClearCustomBill unClearCustomBill : unClearCustomBills)
				{
					if (contractNo !=null && contractNo.equals(unClearCustomBill.getContract_no()))
					{
						billUnClearAmount = billUnClearAmount.add(unClearCustomBill.getUnreceivableamou());
						billClearAmount = billClearAmount.add(unClearCustomBill.getClearamount());
						receivableamount = receivableamount.add(unClearCustomBill.getReceivableamount());
						ajAmount = ajAmount.add(unClearCustomBill.getAdjustamount());
					}
				}
				log.debug("合同号:" + contractNo + "billUnClearAmount:" + billUnClearAmount + ", billClearAmount:" + billClearAmount);

				if (billUnClearAmount.compareTo(billClearAmount) == 0)
				{
					// 款抵消金额
					BigDecimal fundClearamount = new BigDecimal(0.00);
					// 款未抵消金额
					BigDecimal fundUnClearAmount = new BigDecimal(0.00);
					for (UnClearCollect unClearCollect : unClearCollects)
					{
						if (contractNo !=null && contractNo.equals(unClearCollect.getContract_no()))
						{
							fundClearamount = fundClearamount.add(unClearCollect.getNowclearamount());
							fundUnClearAmount = fundUnClearAmount.add(unClearCollect.getUnoffsetamount());
							amount = amount.add(unClearCollect.getAmount());
						}
					}
					log.debug("合同号:" + contractNo + "fundClearamount:" + fundClearamount + ", fundUnClearAmount:" + fundUnClearAmount);
					// 款上，款本次抵消金额合计==款未抵消金额
					if (fundClearamount.compareTo(fundUnClearAmount) == 0)
					{
						// 合同下已清票金额合计
						BigDecimal sumAllContractBillTot = this.customerTitleJdbcDao.getSumAmount(contractNo);
						BigDecimal sumAllContractFundTot = this.collectItemJdbcDao.getSumAmount(contractNo);
						//合同下已清票退款金额合计
						BigDecimal sumAllContractRefundTot = this.customerTitleJdbcDao.getSumRefundAmount(customSingleClear.getCustom(), BusinessState.ALL_BILLCLEAR_PAIDUP, contractNo);
						BigDecimal tot1 = sumAllContractBillTot.add(receivableamount);
						tot1 = tot1.add(sumAllContractRefundTot);
						BigDecimal tot2 = sumAllContractFundTot.add(amount);
						tot2 = tot2.add(ajAmount);
						log.debug("sumAllContractBillTot:" + sumAllContractBillTot + ",sumAllContractFundTot:" + sumAllContractFundTot);
						log.debug("billClearAmount:" + billClearAmount + ",fundClearamount:" + fundClearamount);

						// 如果金额一致，可以清帐。
						if (tot1.compareTo(tot2) == 0)
						{
							log.debug("合同号：" + contractNo + "，可以清帐！");
							if(!clearedContractNo.contains(contractNo)){
								clearedContractNo.add(contractNo);
							}
						}
						else
						{
							log.debug("合同号：" + contractNo + "，不可以清帐！");
						}
					}
				}
				else
				{
					log.debug("合同号：" + contractNo + "，不可以清帐！");
				}
			}
		}

		return clearedContractNo;
	}

	/**
	 * 判断单据下立项是否可全清帐
	 * 
	 * @param customSingleClear
	 * @param clearedContractNo
	 * @param contractNos
	 * @return
	 */
	public List<String> judgeProjectIsAllClear(CustomSingleClear customSingleClear, List<String> clearedContractNo, String contractNos)
	{
		Set<UnClearCustomBill> unClearCustomBills = customSingleClear.getUnClearCustomBill();
		Set<UnClearCollect> unClearCollects = customSingleClear.getUnClearCollect();
		List<String> clearedProjectNo = new ArrayList<String>();

		// 取得票下立项列表
		List<String> projectNos = getProjectNoList(unClearCustomBills);
		if (null != projectNos && projectNos.size() > 0)
		{
			log.debug("共有" + projectNos.size() + "个立项：");
			for (String projectNo : projectNos)
			{   
				//没有立项号就不用
				if(StringUtils.isNullBlank(projectNo))continue;
				
				// 立项下(未付款、未收款)合计
				BigDecimal billUnClearAmount = new BigDecimal(0);
				// 立项下清帐金额合计
				BigDecimal billClearAmount = new BigDecimal(0);
				
				//应收款金额
				BigDecimal receivableamount = new BigDecimal(0);
				
				BigDecimal amount = new BigDecimal(0);
				//调整金金额
				BigDecimal ajAmount = new BigDecimal(0);
				
				//得到之前汇损，或调整金的voucheritem
				List<VoucherItem> voucherItems = voucherItemJdbcDao.getVoucherItems(customSingleClear.getCustom(), projectNo);
				for(VoucherItem voucherItem2 : voucherItems){
					String rowNumber = voucherItem2.getRownumber();
					if(rowNumber.length()==2)rowNumber="0" + rowNumber;			
					if(rowNumber.length()==1)rowNumber="00" + rowNumber;
					CustomerTitle customerTitle = customerTitleJdbcDao.getCustomerTitle(voucherItem2.getVoucher().getCompanycode(), voucherItem2.getVoucher().getVoucherno(), voucherItem2.getVoucher().getFiyear(), rowNumber);
					if(null !=customerTitle && "1".equals(customerTitle.getIscleared())){
						if("01".equals(voucherItem2.getCheckcode()) || "08".equals(voucherItem2.getCheckcode())){
							ajAmount = ajAmount.subtract(voucherItem2.getAmount());
						}else if("11".equals(voucherItem2.getCheckcode()) || "15".equals(voucherItem2.getCheckcode())){
							ajAmount = ajAmount.add(voucherItem2.getAmount());
						}		
					}
				}
				
				for (UnClearCustomBill unClearCustomBill : unClearCustomBills)
				{
					String contract_no = unClearCustomBill.getContract_no();
					if (projectNo.equals(unClearCustomBill.getProject_no()))
					{
						if (null != clearedContractNo && clearedContractNo.contains(contract_no))
						{
							continue;
						}
						else
						{
							billUnClearAmount = billUnClearAmount.add(unClearCustomBill.getUnreceivableamou());
							billClearAmount = billClearAmount.add(unClearCustomBill.getClearamount());
							receivableamount = receivableamount.add(unClearCustomBill.getReceivableamount());
							ajAmount = ajAmount.add(unClearCustomBill.getAdjustamount());
						}
					}
				}
				log.debug("立项号:" + projectNo + "billUnClearAmount:" + billUnClearAmount + ", billClearAmount:" + billClearAmount);

				if (billUnClearAmount.compareTo(billClearAmount) == 0)
				{
					// 款抵消金额
					BigDecimal fundClearamount = new BigDecimal(0.00);
					// 款未抵消金额
					BigDecimal fundUnClearAmount = new BigDecimal(0.00);
					for (UnClearCollect unClearCollect : unClearCollects)
					{
						//如果有保证金不能在合同层，和立项层清帐
						if(unClearCollect.getSuretybond().compareTo(new BigDecimal(0)) != 0){
							return clearedProjectNo;
						}
						
						String contract_no = unClearCollect.getProject_no();
						if (projectNo.equals(unClearCollect.getProject_no()))
						{
							if (null != clearedContractNo && clearedContractNo.contains(contract_no))
							{
								continue;
							}
							else
							{
								fundClearamount = fundClearamount.add(unClearCollect.getNowclearamount());
								fundUnClearAmount = fundUnClearAmount.add(unClearCollect.getUnoffsetamount());
								amount = amount.add(unClearCollect.getAmount());
							}
						}
					}
					log.debug("立项号:" + projectNo + "fundClearamount:" + fundClearamount + ", fundUnClearAmount:" + fundUnClearAmount);
					// 款上，款本次抵消金额合计==款未抵消金额
					if (fundClearamount.compareTo(fundUnClearAmount) == 0)
					{
						// 合同下已清票金额合计
						BigDecimal sumAllContractBillTot = this.customerTitleJdbcDao.getSumAmount(projectNo, contractNos);
						BigDecimal sumAllContractFundTot = this.collectItemJdbcDao.getSumAmount(projectNo, contractNos);
						//合同下已清票退款金额合计
						BigDecimal sumAllContractRefundTot = this.customerTitleJdbcDao.getSumRefundAmountByProject(customSingleClear.getCustom(), BusinessState.ALL_BILLCLEAR_PAIDUP,projectNo, contractNos);
						BigDecimal tot1 = sumAllContractBillTot.add(receivableamount);
						tot1 = tot1.add(sumAllContractRefundTot);
						BigDecimal tot2 = sumAllContractFundTot.add(amount);
						tot2 = tot2.add(ajAmount);
						log.debug("sumAllContractBillTot:" + sumAllContractBillTot + ",sumAllContractFundTot:" + sumAllContractFundTot);
						log.debug("billClearAmount:" + billClearAmount + ",fundClearamount:" + fundClearamount);

						// 如果金额一致，可以清帐。
						if (tot1.compareTo(tot2) == 0)
						{
							log.debug("立项号：" + projectNo + "，可以清帐！");
							if(!clearedProjectNo.contains(projectNo)){
								clearedProjectNo.add(projectNo);
							}
						}
						else
						{
							log.debug("立项号：" + projectNo + "，不可以清帐！");
						}
					}
				}
				else
				{
					log.debug("立项号：" + projectNo + "，不可以清帐！");
				}
			}
		}

		return clearedProjectNo;
	}

	/**
	 * 判断单据下客户是否可全清帐
	 * 
	 * @param customSingleClear
	 * @param custom
	 * @param contractNos
	 * @param projectNos
	 * @param bukrs
	 * @return
	 */
	public boolean judgeSupplierIsAllClear(CustomSingleClear customSingleClear, String customer, String contractNos, String projectNos, String bukrs)
	{
		Set<UnClearCustomBill> unClearCustomBills = customSingleClear.getUnClearCustomBill();
		Set<UnClearCollect> unClearCollects = customSingleClear.getUnClearCollect();
		
		// (未付款、未收款)合计
		BigDecimal billUnClearAmount = new BigDecimal(0);
		// 清帐金额合计
		BigDecimal billClearAmount = new BigDecimal(0);
		//应收款金额
		BigDecimal receivableamount = new BigDecimal(0);
		
		BigDecimal amount = new BigDecimal(0);
		//调整金金额
		BigDecimal ajAmount = new BigDecimal(0);
		
		for (UnClearCustomBill unClearCustomBill : unClearCustomBills)
		{
				billUnClearAmount = billUnClearAmount.add(unClearCustomBill.getUnreceivableamou());
				billClearAmount = billClearAmount.add(unClearCustomBill.getClearamount());
				receivableamount = receivableamount.add(unClearCustomBill.getReceivableamount());
				ajAmount = ajAmount.add(unClearCustomBill.getAdjustamount());
		}
		

		if (billUnClearAmount.compareTo(billClearAmount) == 0)
		{
			// 款抵消金额
			BigDecimal fundClearamount = new BigDecimal(0.00);
			// 款未抵消金额
			BigDecimal fundUnClearAmount = new BigDecimal(0.00);
			for (UnClearCollect unClearCollect : unClearCollects)
			{				
					fundClearamount = fundClearamount.add(unClearCollect.getNowclearamount());
					fundUnClearAmount = fundUnClearAmount.add(unClearCollect.getUnoffsetamount());
					amount = amount.add(unClearCollect.getAmount());
			}
			
			// 款上，款本次抵消金额合计==款未抵消金额
			if (fundClearamount.compareTo(fundUnClearAmount) == 0)
			{
				//调整金金额
//				BigDecimal ajAmount = new BigDecimal(0);
				
				//得到之前汇损，或调整金的voucheritem
//				List<VoucherItem> voucherItems = voucherItemJdbcDao.getVoucherItems(customSingleClear.getCustom(), "");
//				for(VoucherItem voucherItem2 : voucherItems){
//					String rowNumber = voucherItem2.getRownumber();
//					if(rowNumber.length()==2)rowNumber="0" + rowNumber;			
//					if(rowNumber.length()==1)rowNumber="00" + rowNumber;
//					CustomerTitle customerTitle = customerTitleJdbcDao.getCustomerTitle(voucherItem2.getVoucher().getCompanycode(), voucherItem2.getVoucher().getVoucherno(), voucherItem2.getVoucher().getFiyear(), rowNumber);
//					if(null !=customerTitle && "1".equals(customerTitle.getIscleared())){
//						if("01".equals(voucherItem2.getCheckcode()) || "08".equals(voucherItem2.getCheckcode())){
//							ajAmount = ajAmount.subtract(voucherItem2.getAmount());
//						}else if("11".equals(voucherItem2.getCheckcode()) || "15".equals(voucherItem2.getCheckcode())){
//							ajAmount = ajAmount.add(voucherItem2.getAmount());
//						}		
//					}
//				}
				
				// 客户 下已清票金额合计
				BigDecimal sumAllSupplierBillTot = this.customerTitleJdbcDao.getSumAmount2(customer, contractNos, projectNos, bukrs,"S",customSingleClear.getCurrencytext(),customSingleClear.getDepid(),customSingleClear.getSubject());
				BigDecimal sumAllSupplierFundTot = this.customerTitleJdbcDao.getSumAmount2(customer, contractNos, projectNos, bukrs,"H",customSingleClear.getCurrencytext(),customSingleClear.getDepid(),customSingleClear.getSubject());
//				BigDecimal sumAllSupplierFundTot = this.collectItemJdbcDao.getSumAmount(customer, contractNos, projectNos, bukrs);
				//取得部门ID
//				List<String> deptidList = sysDeptJdbcDao.getDeptIdByDeptCode(customSingleClear.getDepid());
//				String[] s=  (String[])deptidList.toArray(new String[deptidList.size()]);
//				String deptids = StringUtils.arrayToString(s, ",");
//				//合同下已清票退款金额合计
//				BigDecimal sumAllContractRefundTot = this.customerTitleJdbcDao.getSumRefundAmount(customSingleClear.getCustom(), BusinessState.ALL_BILLCLEAR_PAIDUP,projectNos, contractNos,deptids);
				BigDecimal tot1 = receivableamount.add(sumAllSupplierBillTot);
//				tot1 = tot1.add(sumAllContractRefundTot);
				BigDecimal tot2 = amount.add(sumAllSupplierFundTot);
				tot2 = tot2.add(ajAmount);
				log.debug("sumBillClearAmount:" + billClearAmount + ",sumAllSupplierBillTot:" + sumAllSupplierBillTot);
				log.debug("sumFundClearAmount:" + fundClearamount + ",sumAllSupplierFundTot:" + sumAllSupplierFundTot);

//				if (tot1.compareTo(tot2) == 0 && sumAllSupplierBillTot.compareTo(new BigDecimal(0)) == 1 && sumAllSupplierFundTot.compareTo(new BigDecimal(0)) == 1)
				if (tot1.compareTo(tot2) == 0 )
				{
					log.debug("客户" + customer + "，可以清帐。");
					return true;
				}
				else
				{
					log.debug("客户" + customer + "，不可以清帐。");
				}
			}
		}	

		return false;
	}

	/**
	 * 处理清帐凭证行项目
	 * 
	 * @param clearVoucherItemStruct
	 * @return
	 */
	public VoucherItem getClearVoucherItem(ClearVoucherItemStruct clearVoucherItemStruct)
	{
		VoucherItem voucherItem = new VoucherItem();
		voucherItem.setVoucherno(clearVoucherItemStruct.getVoucherno());
		voucherItem.setRownumber(clearVoucherItemStruct.getRowno());
		voucherItem.setFiyear(clearVoucherItemStruct.getYear());
		voucherItem.setFlag("R");
		voucherItem.setAmount(new BigDecimal("0.00"));
		voucherItem.setAmount2(new BigDecimal("0.00"));

		return voucherItem;
	}

	/**
	 * 得到有汇损益的行项目信息
	 * 
	 * @param customSingleClear
	 * @param subtractVlaue
	 * @param currency
	 * @param strType
	 * @return
	 */
	public VoucherItem getProfitAndLossVoucherItem(CustomSingleClear customSingleClear, BigDecimal subtractVlaue, String currency,String taxCode, String strType)
	{
		VoucherItem voucherItem = new VoucherItem();
		String bukrs = customSingleClear.getCompanyno();
		String subject = this.customerRefundItemJdbcDao.getSubjectByCustomer(customSingleClear.getCustom(),bukrs);
		String subjectname = this.customerRefundItemJdbcDao.getSubjectNameById(subject,bukrs);
		voucherItem.setControlaccount(subject);
		voucherItem.setCaremark(subjectname);
		voucherItem.setTaxcode(taxCode);
		if (strType.equals("1"))
		{
			voucherItem.setRownumber("1");
			// 科目
			voucherItem.setSubject(customSingleClear.getCustom());
			// 科目说明
			// String Subjectdescribe =
			// this.voucherService.getSupplierDescByCustomerId(customSingleClear.getCustom(),
			// bukrs);
			voucherItem.setSubjectdescribe(customSingleClear.getCustom_htext());

			if (subtractVlaue.signum() == 1)
			{
				// 记帐码
				voucherItem.setCheckcode("08");
				voucherItem.setDebitcredit("S");
			}
			else
			{
				// 记帐码
				voucherItem.setCheckcode("15");
				voucherItem.setDebitcredit("H");
			}
		}

		if (strType.equals("2"))
		{
			voucherItem.setRownumber("2");
			if (subtractVlaue.signum() == 1)
			{
				// 记帐码
				voucherItem.setCheckcode("50");
				voucherItem.setDebitcredit("H");
				// 科目
				voucherItem.setSubject("6603050302");
				// 科目说明
				voucherItem.setSubjectdescribe(this.customerRefundItemJdbcDao.getSubjectNameById("6603050302",bukrs));
				voucherItem.setControlaccount(voucherItem.getSubject());
				voucherItem.setCaremark(voucherItem.getSubjectdescribe());
			}
			else
			{
				// 记帐码
				voucherItem.setCheckcode("40");
				voucherItem.setDebitcredit("S");
				// 科目
				voucherItem.setSubject("6603050301");
				// 科目说明
				voucherItem.setSubjectdescribe(this.customerRefundItemJdbcDao.getSubjectNameById("6603050301",bukrs));
				voucherItem.setControlaccount(voucherItem.getSubject());
				voucherItem.setCaremark(voucherItem.getSubjectdescribe());
			}

		}

		// 货币
		voucherItem.setCurrency(currency);
		// 货币金额
		voucherItem.setAmount(new BigDecimal("0"));
		// 本位币金额
		voucherItem.setAmount2(subtractVlaue.abs().setScale(2, BigDecimal.ROUND_HALF_UP));
		// 部门
		voucherItem.setDepid(customSingleClear.getDepid());
		// 文本
		voucherItem.setText(customSingleClear.getText());

		return voucherItem;
	}

	/**
	 * 判断是否可以出清帐凭证。
	 * 
	 * @param customSingleClear
	 * @return
	 */
	public boolean isHaveClearVoucher(CustomSingleClear customSingleClear)
	{
		String businessId = customSingleClear.getCustomsclearid();
		String deptId = customSingleClear.getDeptid();
		String bukrs = this.voucherService.getCompanyIDByDeptID(deptId);
		String supplier = customSingleClear.getCustom();
		if (judgeIsAllClear(customSingleClear))
		{
			return true;
		}
		else
		{
			// 合同
			List<String> clearedContractNo = judgeContractIsAllClear(customSingleClear);
			if (null != clearedContractNo && clearedContractNo.size() > 0)
			{
				// 处理合同下清帐凭证行项目.
				return true;
			}

			// 立项下
			List<String> clearedProjectNo = judgeProjectIsAllClear(customSingleClear, clearedContractNo, "");
			if (null != clearedProjectNo && clearedProjectNo.size() > 0)
			{
				// 处理立项下清帐凭证行项目.
				return true;

			}
			// 客户清帐
			if (judgeSupplierIsAllClear(customSingleClear, supplier, "", "", bukrs))
			{
				return true;
			}
		}

		return false;

	}

	/**
	 * 是否已经进行凭证预览。
	 * 
	 * @param businessId
	 * @return
	 */
	public boolean isVoucherExist(String businessId)
	{
		return this.voucherJdbcDao.isVoucherExist(businessId);
	}
	/**
	 * 取得已生成凭证的ID
	 * @param businessId
	 * @return
	 */
	public String getVoucherId(String businessId){
		return this.voucherJdbcDao.getVoucherId(businessId);
	}


	/* (non-Javadoc)
	 * @see com.infolion.xdss3.singleClearGen.service.CustomSingleClearServiceGen#_get(java.lang.String)
	 */
	@Override
	public CustomSingleClear _get(String id) {
		// TODO Auto-generated method stub
		return customSingleClearJdbcDao.getCustomSingleClearById(id);
	}


	/* (non-Javadoc)
	 * @see com.infolion.xdss3.singleClearGen.service.CustomSingleClearServiceGen#_getDetached(java.lang.String)
	 */
	@Override
	public CustomSingleClear _getDetached(String id) {
		// TODO Auto-generated method stub
		return customSingleClearJdbcDao.getCustomSingleClearById(id);
	}


	/* (non-Javadoc)
	 * @see com.infolion.xdss3.singleClearGen.service.CustomSingleClearServiceGen#_getEntityCopy(java.lang.String)
	 */
	@Override
	public CustomSingleClear _getEntityCopy(String id) {
		 CustomSingleClear customSingleClear = new CustomSingleClear();
			CustomSingleClear customSingleClearOld = customSingleClearJdbcDao.getCustomSingleClearById(id);
			try
			{
				BeanUtils.copyProperties(customSingleClear, customSingleClearOld);
			}
			catch (IllegalAccessException e)
			{
				e.printStackTrace();
			}
			catch (InvocationTargetException e)
			{
				e.printStackTrace();
			}
			//customSingleClear.setCustomsclearid(null); 
			customSingleClear.setProcessstate(" ");
			return customSingleClear;	
	}


	/* (non-Javadoc)
	 * @see com.infolion.xdss3.singleClearGen.service.CustomSingleClearServiceGen#_getForEdit(java.lang.String)
	 */
	@Override
	public CustomSingleClear _getForEdit(String id) {
		// TODO Auto-generated method stub
		return customSingleClearJdbcDao.getCustomSingleClearById(id);
	}

	
}