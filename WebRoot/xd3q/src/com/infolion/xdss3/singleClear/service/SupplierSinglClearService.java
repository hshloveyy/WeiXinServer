/*
 * @(#)SupplierSinglClearService.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年07月08日 14点42分18秒
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
import com.infolion.platform.util.DateUtils;
import com.infolion.platform.util.MultyGridData;
import com.infolion.platform.util.StringUtils;
import com.infolion.xdss3.BusinessState;
import com.infolion.xdss3.Constants;
import com.infolion.xdss3.Constants.cleared;
import com.infolion.xdss3.collect.domain.Collect;
import com.infolion.xdss3.collectitem.domain.CollectItem;
import com.infolion.xdss3.customerDrawback.dao.CustomerRefundItemJdbcDao;
import com.infolion.xdss3.financeSquare.domain.FundFlow;
import com.infolion.xdss3.financeSquare.domain.SettleSubject;
import com.infolion.xdss3.masterData.dao.VendorTitleHibernateDao;
import com.infolion.xdss3.masterData.dao.VendorTitleJdbcDao;

import com.infolion.xdss3.masterData.domain.CustomerTitle;
import com.infolion.xdss3.masterData.domain.VendorTitle;
import com.infolion.xdss3.masterData.service.VendorService;
import com.infolion.xdss3.payment.dao.ImportPaymentHibernateDao;
import com.infolion.xdss3.payment.dao.PaymentItemJdbcDao;
import com.infolion.xdss3.payment.domain.ClearVoucherItemStruct;
import com.infolion.xdss3.payment.domain.ImportPayment;
import com.infolion.xdss3.payment.domain.ImportPaymentItem;
import com.infolion.xdss3.payment.service.PaymentItemService;
import com.infolion.xdss3.singleClear.dao.SupplierSinglClearJdbcDao;

import com.infolion.xdss3.singleClear.domain.SupplierSinglClear;

import com.infolion.xdss3.singleClear.domain.CustomSingleClear;
import com.infolion.xdss3.singleClear.domain.UnClearCollect;
import com.infolion.xdss3.singleClear.domain.UnClearCustomBill;
import com.infolion.xdss3.singleClear.domain.UnClearPayment;
import com.infolion.xdss3.singleClear.domain.UnClearSupplieBill;
import com.infolion.xdss3.singleClearGen.service.SupplierSinglClearServiceGen;
import com.infolion.xdss3.supplierDrawback.dao.SupplierRefundItemHibernateDao;
import com.infolion.xdss3.supplierDrawback.domain.SupplierRefundItem;
import com.infolion.xdss3.supplierDrawback.service.SupplierRefundItemService;
import com.infolion.xdss3.voucher.dao.VoucherHibernateDao;
import com.infolion.xdss3.voucher.dao.VoucherJdbcDao;
import com.infolion.xdss3.voucher.domain.Voucher;
import com.infolion.xdss3.voucher.service.VoucherService;
import com.infolion.xdss3.voucheritem.dao.VoucherItemJdbcDao;
import com.infolion.xdss3.voucheritem.domain.VoucherItem;

/**
 * <pre>
 * 供应商单清帐(SupplierSinglClear)服务类
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
public class SupplierSinglClearService extends SupplierSinglClearServiceGen
{

	/**
	 * 供应商未清数据(Vendor)服务类
	 */
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
	private CustomerRefundItemJdbcDao customerRefundItemJdbcDao;
	
	/**
	 * @param customerRefundItemJdbcDao the customerRefundItemJdbcDao to set
	 */
	public void setCustomerRefundItemJdbcDao(
			CustomerRefundItemJdbcDao customerRefundItemJdbcDao) {
		this.customerRefundItemJdbcDao = customerRefundItemJdbcDao;
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
	protected VoucherHibernateDao voucherHibernateDao;

	public void setVoucherHibernateDao(VoucherHibernateDao voucherHibernateDao)
	{
		this.voucherHibernateDao = voucherHibernateDao;
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
	private PaymentItemJdbcDao paymentItemJdbcDao;

	/**
	 * @param paymentItemJdbcDao
	 *            the paymentItemJdbcDao to set
	 */
	public void setPaymentItemJdbcDao(PaymentItemJdbcDao paymentItemJdbcDao)
	{
		this.paymentItemJdbcDao = paymentItemJdbcDao;
	}

	@Autowired
	private VoucherItemJdbcDao voucherItemJdbcDao;

	
	public void setVoucherItemJdbcDao(VoucherItemJdbcDao voucherItemJdbcDao) {
		this.voucherItemJdbcDao = voucherItemJdbcDao;
	}
	
	@Autowired
	protected VendorTitleHibernateDao vendorTitleHibernateDao;

	/**
	 * @param vendorTitleHibernateDao
	 *            the vendorTitleHibernateDao to set
	 */
	public void setVendorTitleHibernateDao(VendorTitleHibernateDao vendorTitleHibernateDao)
	{
		this.vendorTitleHibernateDao = vendorTitleHibernateDao;
	}
	
	@Autowired	
	private SysDeptJdbcDao sysDeptJdbcDao;
	
	
	public void setSysDeptJdbcDao(SysDeptJdbcDao sysDeptJdbcDao) {
		this.sysDeptJdbcDao = sysDeptJdbcDao;
	}
	
	@Autowired	
	private SupplierRefundItemService supplierRefundItemService;
	
	
	public void setSupplierRefundItemService(SupplierRefundItemService supplierRefundItemService) {
		this.supplierRefundItemService = supplierRefundItemService;
	}
	
	@Autowired
	protected SupplierRefundItemHibernateDao supplierRefundItemHibernateDao;
	
	public void setSupplierRefundItemHibernateDao(SupplierRefundItemHibernateDao supplierRefundItemHibernateDao)
	{
		this.supplierRefundItemHibernateDao = supplierRefundItemHibernateDao;
	}
	
	/**
	 * 自动分配
	 * 
	 * @param supplier
	 * @return
	 */
	public JSONObject _autoAssign(String supplier, String subject,String currencytext ,String companyno,String depid)
	{
		// 根据供应商，取得供应商下未清发票数据集合
		//List<VendorTitle> vendorTitleList = this.vendorService.getVendorTitleListBySupplier(supplier, subject, "");
		List<VendorTitle> vendorTitleList = this.vendorService.getVendorTitleListBySupplier(supplier, subject,currencytext,companyno,depid, "");
		// 未清应付（贷方）
		List<UnClearSupplieBill> unClearSupplieBillList = new ArrayList<UnClearSupplieBill>();
		// 未清付款（借方）
		List<UnClearPayment> unClearPaymentList = new ArrayList<UnClearPayment>();

		// 查询计算出所有未清数据，供自动分配时候使用。
		this.getUnClearInfo(vendorTitleList, unClearSupplieBillList, unClearPaymentList);
		// 未清应付（贷方）
		List<UnClearSupplieBill> newUnClearSupplieBillList = new ArrayList<UnClearSupplieBill>();
		// 未清付款（借方）
		List<UnClearPayment> newUnClearPaymentList = new ArrayList<UnClearPayment>();

		for (int i = 0; i < unClearSupplieBillList.size(); i++)
		{
			UnClearSupplieBill unClearSupplieBill = unClearSupplieBillList.get(i);
			// 发票总金额
			BigDecimal receivableamount = unClearSupplieBill.getReceivableamount();
			// 发票已经审批完的，发票已付款
			BigDecimal paidamount = unClearSupplieBill.getPaidamount();
			// 未付款=发票金额-发票已付款
			BigDecimal unpaidamount = unClearSupplieBill.getUnpaidamount();
			// 发票在途的，发票在途金额
			BigDecimal onroadamount = unClearSupplieBill.getOnroadamount();
			String contractNo = unClearSupplieBill.getContract_no();

			// 遍历 未清完的付款单明细
			for (int j = 0; j < unClearPaymentList.size(); j++)
			{
				UnClearPayment unClearPayment = unClearPaymentList.get(j);
				BigDecimal billreaminamount = receivableamount.subtract(paidamount).subtract(onroadamount).subtract(unClearSupplieBill.getClearamount());

				// 取得相同合同编号的数据进行自动分配金额
				if (contractNo.equalsIgnoreCase(unClearPayment.getContract_no()))
				{
					// 本次已清金额
					if (unClearPayment.getClearedamount() == null)
						unClearPayment.setClearedamount(BigDecimal.valueOf(0));

					// 金额
					BigDecimal goodsAmount = unClearPayment.getPaymentamount();
					// 本次抵消金额
					BigDecimal nowclearamount = new BigDecimal(0);
					if(unClearPayment.getNowclearamount() != null && unClearPayment.getNowclearamount().compareTo(BigDecimal.valueOf(0)) != 0){
						
					}else{
						unClearPayment.setNowclearamount(nowclearamount);
					}
					// 已清金额
					BigDecimal offsetamount = unClearPayment.getOffsetamount();
					// 在批金额
					BigDecimal onroadamount1 = unClearPayment.getOnroadamount();
					// 未清金额, = 金额 - 已清金额
					BigDecimal unoffsetamount = unClearPayment.getUnoffsetamount();

					// 付款单剩余可用金额 = 总金额-已清金额-在批金额- 本次已清金额(累加)
					BigDecimal paymentRemainAmount = goodsAmount.subtract(offsetamount).subtract(onroadamount1).subtract(unClearPayment.getClearedamount());
					if (paymentRemainAmount.compareTo(BigDecimal.valueOf(0)) == 0)
						continue;
					
					// 发票剩余被清金额 > 付款单剩余可用金额
					if (billreaminamount.compareTo(paymentRemainAmount) == 1 || billreaminamount.compareTo(paymentRemainAmount) == 0)
					{
						unClearSupplieBill.setClearamount(unClearSupplieBill.getClearamount().add(paymentRemainAmount));
						unClearPayment.setClearedamount(unClearPayment.getClearedamount().add(paymentRemainAmount));
						unClearPayment.setNowclearamount(unClearPayment.getClearedamount());
					}
					else
					{
						unClearSupplieBill.setClearamount(unClearSupplieBill.getClearamount().add(billreaminamount));
						unClearPayment.setClearedamount(unClearPayment.getClearedamount().add(billreaminamount));
						unClearPayment.setNowclearamount(unClearPayment.getClearedamount());
					}					

				}
			}
		}

		MultyGridData gridJsonData = new MultyGridData();
		gridJsonData.setData(unClearSupplieBillList.toArray());
		gridJsonData.setData2(unClearPaymentList.toArray());
		JSONObject jsonList = JSONObject.fromObject(gridJsonData);

		return jsonList;
	}

	/**
	 * 根据选择的未清供应商编号，查询出未清供应商下所有未清发票信息。
	 */
	public JSONObject _queryUnClear(String supplier, String subject)
	{
		// 根据供应商，取得供应商下未清发票数据集合
		List<VendorTitle> vendorTitleList = this.vendorService.getVendorTitleListBySupplier(supplier, subject, "");
		// 未清应付（贷方）
		List<UnClearSupplieBill> unClearSupplieBillList = new ArrayList<UnClearSupplieBill>();
		// 未清付款（借方）
		List<UnClearPayment> unClearPaymentList = new ArrayList<UnClearPayment>();

		this.getUnClearInfo(vendorTitleList, unClearSupplieBillList, unClearPaymentList);

		MultyGridData gridJsonData = new MultyGridData();
		gridJsonData.setData(unClearSupplieBillList.toArray());
		gridJsonData.setData2(unClearPaymentList.toArray());
		JSONObject jsonList = JSONObject.fromObject(gridJsonData);

		return jsonList;
	}
	
	/**
	 * 根据选择的未清客户编号。
	 */
	public JSONObject _queryUnClear(String vendortitleids)
	{
		// 根据供应商，取得供应商下未清发票数据集合
		List<VendorTitle> vendorTitleList = new ArrayList<VendorTitle>();
		if (!("").equals(vendortitleids) && vendortitleids != null)
		{
			vendorTitleList = this.vendorService.getVendorTitleList(vendortitleids);
		}
		// 未清应付（贷方）
		List<UnClearSupplieBill> unClearSupplieBillList = new ArrayList<UnClearSupplieBill>();
		// 未清付款（借方）
		List<UnClearPayment> unClearPaymentList = new ArrayList<UnClearPayment>();

		this.getUnClearInfo(vendorTitleList, unClearSupplieBillList, unClearPaymentList);

		MultyGridData gridJsonData = new MultyGridData();
		if(unClearSupplieBillList.size()!=0){
			gridJsonData.setData(unClearSupplieBillList.toArray());
		}
		if(unClearPaymentList.size() != 0){
			gridJsonData.setData(unClearPaymentList.toArray());
		}
		JSONObject jsonList = JSONObject.fromObject(gridJsonData);

		return jsonList;
	}
	
	/**
	 * 根据选择的未清供应商编号，科目，货币，公司代码,业务范围，查询出未清供应商下所有未清发票信息。
	 */
	public JSONObject _queryUnClear(String supplier, String subject,String currencytext ,String companyno,String depid)
	{
		// 根据供应商，取得供应商下未清发票数据集合
		//List<VendorTitle> vendorTitleList = this.vendorService.getVendorTitleListBySupplier(supplier, subject, "");
		List<VendorTitle> vendorTitleList = this.vendorService.getVendorTitleListBySupplier(supplier, subject,currencytext,companyno,depid, "");
		// 未清应付（贷方）
		List<UnClearSupplieBill> unClearSupplieBillList = new ArrayList<UnClearSupplieBill>();
		// 未清付款（借方）
		List<UnClearPayment> unClearPaymentList = new ArrayList<UnClearPayment>();

		this.getUnClearInfo(vendorTitleList, unClearSupplieBillList, unClearPaymentList);

		MultyGridData gridJsonData = new MultyGridData();
		gridJsonData.setData(unClearSupplieBillList.toArray());
		gridJsonData.setData2(unClearPaymentList.toArray());
		JSONObject jsonList = JSONObject.fromObject(gridJsonData);

		return jsonList;
	}

	/**
	 * 根据业务状态、vendorTitleId，取得款已经审批完金额(款已清金额)、在途金额(款在途金额)。
	 * 
	 * @param vendorTitleId
	 * @param businessstates
	 * @return
	 */
	public BigDecimal getSumClearAmount(String vendorTitleId, String businessstates)
	{
		return this.supplierSinglClearJdbcDao.getSumClearAmount(vendorTitleId, businessstates);
	}

	/**
	 * 根据业务状态、vendorTitleId，取得票已经审批完金额(款已清金额)、在途金额(款在途金额)。
	 * 
	 * @param vendorTitleId
	 * @param businessstates
	 * @return
	 */
	public BigDecimal getSumClearAmount2(String vendorTitleId, String businessstates)
	{
		return this.supplierSinglClearJdbcDao.getSumClearAmount2(vendorTitleId, businessstates);
	}
	
	/**
	 * 
	 * @param vendorTitleList
	 * @param unClearSupplieBillList
	 * @param unClearPaymentList
	 */
	private void getUnClearInfo(List<VendorTitle> vendorTitleList, List<UnClearSupplieBill> unClearSupplieBillList, List<UnClearPayment> unClearPaymentList)
	{
		// 遍历所有供应商下未清发票数据集合
		for (Iterator<VendorTitle> it = vendorTitleList.iterator(); it.hasNext();)
		{
			VendorTitle vendorTitle = (VendorTitle) it.next();

			String vendorTitleId = vendorTitle.getVendortitleid();
			String rowNumber = vendorTitle.getBuzei();
			String shkzg = vendorTitle.getShkzg();
			if (StringUtils.isNullBlank(shkzg))
			{
				continue;
			}

			// 会计年度
			String fiYear = vendorTitle.getGjahr();
			// 财务凭证号
			String voucherNo = vendorTitle.getBelnr();
			// 公司代码
			String companyCode = vendorTitle.getBukrs();

			// 未清应付（贷方）
			if (shkzg.equals("H"))
			{
				UnClearSupplieBill unClearSupplieBill = new UnClearSupplieBill();
				unClearSupplieBill.setInvoice(vendorTitle.getInvoice());
				unClearSupplieBill.setCurrency(vendorTitle.getWaers());
				unClearSupplieBill.setVoucherno(vendorTitle.getBelnr());
				unClearSupplieBill.setProject_no(vendorTitle.getLixiang());
				unClearSupplieBill.setContract_no(vendorTitle.getVerkf());
				unClearSupplieBill.setOld_contract_no(this.paymentItemService.getOldContractNo(vendorTitle.getVerkf())); // 纸质合同号码
				unClearSupplieBill.setSap_order_no(vendorTitle.getEbeln()); // sap订单号,采购凭证号
				unClearSupplieBill.setText(vendorTitle.getBktxt());
				unClearSupplieBill.setAccountdate(vendorTitle.getBudat());
				unClearSupplieBill.setVendortitleid(vendorTitle.getVendortitleid());
				unClearSupplieBill.setBwbje(vendorTitle.getBwbje());
				// 发票总金额,应付款.
				BigDecimal billamount = vendorTitle.getDmbtr();
				unClearSupplieBill.setReceivableamount(billamount);
				unClearSupplieBill.setReceivabledate(vendorTitle.getYfkr());

				// 发票已经审批完的，发票已付款
				BigDecimal paidamount ;
				if(StringUtils.isNullBlank(vendorTitle.getInvoice())){
					paidamount = this.vendorService.getSumClearAmountByVendorTitleID(vendorTitle.getVendortitleid().trim(), BusinessState.ALL_SUBMITPASS);
				}else{
					paidamount = this.vendorService.getSumClearAmount(vendorTitle.getInvoice(), BusinessState.ALL_SUBMITPASS);
				}
				
				unClearSupplieBill.setPaidamount(paidamount);
				// 发票在途的，发票在途金额
				BigDecimal onroadamount;
				if(StringUtils.isNullBlank(vendorTitle.getInvoice())){
					onroadamount = this.vendorService.getSumClearAmountByVendorTitleID(vendorTitle.getVendortitleid().trim(), BusinessState.ALL_PAYMENT_ONROAD);
				}else{
					onroadamount = this.vendorService.getSumClearAmount(vendorTitle.getInvoice(), BusinessState.ALL_PAYMENT_ONROAD);
				}
				
				unClearSupplieBill.setOnroadamount(onroadamount);
				
				// 未付款=发票金额-发票已付款-在批
				unClearSupplieBill.setUnpaidamount(billamount.subtract(paidamount).subtract(onroadamount));

				//未付款金额为0不显示
				if(unClearSupplieBill.getUnpaidamount().compareTo(BigDecimal.valueOf(0)) <= 0 && unClearSupplieBill.getReceivableamount().compareTo(BigDecimal.valueOf(0)) != 0){
					continue;
				}
				unClearSupplieBill.setClearamount(unClearSupplieBill.getUnpaidamount());
				unClearSupplieBill.setAdjustamount(BigDecimal.valueOf(0));
				//汇率
				BigDecimal rat = new BigDecimal(1);
				if(unClearSupplieBill.getReceivableamount().compareTo(BigDecimal.valueOf(0)) != 0){
					rat = vendorTitle.getBwbje().divide(billamount,5,BigDecimal.ROUND_HALF_EVEN);
//					设置还没有部分清的取应付款的本位币
					if(unClearSupplieBill.getReceivableamount().compareTo(unClearSupplieBill.getUnpaidamount()) ==0){
						unClearSupplieBill.setUnbwbje(unClearSupplieBill.getBwbje());
					}else{						
						unClearSupplieBill.setUnbwbje(unClearSupplieBill.getUnpaidamount().multiply(rat).setScale(2, BigDecimal.ROUND_HALF_UP));
					}
					
				}else{
					unClearSupplieBill.setUnbwbje(unClearSupplieBill.getBwbje());
				}
				unClearSupplieBill.setExchangerate(rat);
				
				
				unClearSupplieBillList.add(unClearSupplieBill);
			}
			// 未清付款（借方）
			else if (shkzg.equals("S"))
			{
				// 付款ID
								
				String businessId = this.voucherJdbcDao.getBusinessId(voucherNo, companyCode, fiYear, rowNumber);
				
				String businessItemId = this.voucherItemJdbcDao.getBusinessId(voucherNo, companyCode, fiYear, rowNumber);
				
				if (StringUtils.isNullBlank(businessItemId))
				{
					// LJX 20100824 Add 需要处理对应不上付款单的数据。
					UnClearPayment unClearPayment = new UnClearPayment();
					// 以下几个不用填值
					unClearPayment.setContract_no(vendorTitle.getVerkf());
					unClearPayment.setProject_no(vendorTitle.getLixiang());
					unClearPayment.setPick_list_no(" ");
					unClearPayment.setOld_contract_no(" ");
					unClearPayment.setPaymentid(" ");
					unClearPayment.setPaymentitemid(" ");

					unClearPayment.setCurrency(vendorTitle.getWaers());
					unClearPayment.setExchangerate(new BigDecimal(1));
					unClearPayment.setVoucherno(voucherNo);
					unClearPayment.setVendortitleid(vendorTitleId);
					unClearPayment.setAccountdate(vendorTitle.getBudat());
					unClearPayment.setBwbje(vendorTitle.getBwbje());
					unClearPayment.setBktxt(vendorTitle.getBktxt());
					
					// 金额
					BigDecimal goodsAmount = vendorTitle.getDmbtr();
					unClearPayment.setPaymentamount(goodsAmount);
					
					// 已清金额
					BigDecimal offsetamount = getSumClearAmount(vendorTitleId.trim(), BusinessState.ALL_SUBMITPASS);// 根据vendorTitleId到YUNCLEARPAYMENT计算已经金额。
					// 需要重写实现计算金额代码,danthis.paymentItemService.getSumClearAmount(paymentItemId,
					// BusinessState.ALL_SUBMITPASS);
					unClearPayment.setOffsetamount(offsetamount);
					// 在批金额
					BigDecimal onroadamount = getSumClearAmount(vendorTitleId.trim(), BusinessState.ALL_PAYMENT_ONROAD);// this.paymentItemService.getSumClearAmount(paymentItemId,
					// BusinessState.ALL_ONROAD);
					unClearPayment.setOnroadamount(onroadamount);
					// 未清金额, = 金额 - 已清金额-在批
					BigDecimal unoffsetamount = goodsAmount.subtract(offsetamount).subtract(onroadamount);
					unClearPayment.setUnoffsetamount(unoffsetamount);
					//未清金额为0不显示
					if(unClearPayment.getUnoffsetamount().compareTo(BigDecimal.valueOf(0)) <= 0 && unClearPayment.getPaymentamount().compareTo(BigDecimal.valueOf(0)) != 0){
						continue;
					}
					//判断sap手工调汇率，是否有清账
					if(unClearPayment.getPaymentamount().compareTo(BigDecimal.valueOf(0)) == 0 && supplierSinglClearJdbcDao.getSapPayment(vendorTitleId.trim())){
						continue;
					}
					// 本次抵消金额
					BigDecimal nowclearamount = new BigDecimal(0);
					unClearPayment.setNowclearamount(unClearPayment.getUnoffsetamount());
					//汇率
					BigDecimal rat = new BigDecimal(1);
					if(unClearPayment.getPaymentamount().compareTo(BigDecimal.valueOf(0)) != 0){
						rat = vendorTitle.getBwbje().divide(goodsAmount,5,BigDecimal.ROUND_HALF_EVEN);
//						设置还没有部分清的取应付款的本位币
						if(unClearPayment.getPaymentamount().compareTo(unClearPayment.getUnoffsetamount()) ==0){
							unClearPayment.setUnbwbje(unClearPayment.getBwbje());
						}else{
							
							unClearPayment.setUnbwbje(unClearPayment.getUnoffsetamount().multiply(rat).setScale(2, BigDecimal.ROUND_HALF_UP));
						}
						
					}else{
						unClearPayment.setUnbwbje(unClearPayment.getBwbje());
					}
					unClearPayment.setExchangerate(rat);
					
					unClearPaymentList.add(unClearPayment);
				}
				else {
					// 付款信息
					ImportPayment payment = this.importPaymentHibernateDao.getDetached(businessId);
					String paymentBUsinessState = payment.getBusinessstate();
					// 付款为作废不显示
					if (!StringUtils.isNullBlank(paymentBUsinessState) && BusinessState.BLANKOUT.equals(paymentBUsinessState)){
						continue;
					}
					// 付款已经审批通过，(付款单状态为 正常、重做。现在状态不用了，zhongzh 去掉)
//					if (!StringUtils.isNullBlank(paymentBUsinessState) && BusinessState.ALL_SUBMITPASS.indexOf("'" + paymentBUsinessState + "'") >= 0 )
//					{
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

							String paymentItemId = importPaymentItem.getPaymentitemid();
							if(!StringUtils.isNullBlank(businessItemId) && !paymentItemId.equals(businessItemId)){
								continue;
							}
							UnClearPayment unClearPayment = new UnClearPayment();
							unClearPayment.setAccountdate(vendorTitle.getBudat());
							unClearPayment.setContract_no(importPaymentItem.getContract_no());
							unClearPayment.setProject_no(importPaymentItem.getProject_no());
							unClearPayment.setPick_list_no(importPaymentItem.getPick_list_no());
							unClearPayment.setCurrency(payment.getFactcurrency());
							unClearPayment.setExchangerate(payment.getFactexchangerate());
							unClearPayment.setOld_contract_no(importPaymentItem.getOld_contract_no());
							unClearPayment.setPaymentid(payment.getPaymentid());
							unClearPayment.setPaymentitemid(paymentItemId);
							unClearPayment.setVoucherno(voucherNo);
							unClearPayment.setVendortitleid(vendorTitleId);
							unClearPayment.setBwbje(vendorTitle.getBwbje());
							unClearPayment.setBktxt(vendorTitle.getBktxt());
							// 金额
//							BigDecimal goodsAmount = importPaymentItem.getGoodsamount();
							BigDecimal goodsAmount = importPaymentItem.getAssignamount();
							unClearPayment.setPaymentamount(goodsAmount);
							// 本次抵消金额
							BigDecimal nowclearamount = new BigDecimal(0);
//							unClearPayment.setNowclearamount(nowclearamount);
							// 已清金额
							BigDecimal offsetamount = this.paymentItemService.getSumClearAmount(paymentItemId.trim(), BusinessState.ALL_SUBMITPASS);
							unClearPayment.setOffsetamount(offsetamount);
							// 在批金额
							BigDecimal onroadamount = this.paymentItemService.getSumClearAmount(paymentItemId.trim(), BusinessState.ALL_PAYMENT_ONROAD);
							unClearPayment.setOnroadamount(onroadamount);
							// 未抵消金额, = 金额 - 已清金额 -在批
							BigDecimal unoffsetamount = goodsAmount.subtract(offsetamount).subtract(onroadamount);
							unClearPayment.setUnoffsetamount(unoffsetamount);
							//未抵消金额为0不显示
							if(unClearPayment.getUnoffsetamount().compareTo(BigDecimal.valueOf(0)) <= 0 && unClearPayment.getPaymentamount().compareTo(BigDecimal.valueOf(0)) != 0){
								continue;
							}
							unClearPayment.setNowclearamount(unClearPayment.getUnoffsetamount());
							//汇率
							BigDecimal rat = new BigDecimal(1);
							if(unClearPayment.getPaymentamount().compareTo(BigDecimal.valueOf(0)) != 0){
								rat = vendorTitle.getBwbje().divide(goodsAmount,5,BigDecimal.ROUND_HALF_EVEN);
//								设置还没有部分清的取应付款的本位币
								if(unClearPayment.getPaymentamount().compareTo(unClearPayment.getUnoffsetamount()) ==0){
									unClearPayment.setUnbwbje(unClearPayment.getBwbje());
								}else{
									
									unClearPayment.setUnbwbje(unClearPayment.getUnoffsetamount().multiply(rat).setScale(2, BigDecimal.ROUND_HALF_UP));
								}
								
							}else{
								unClearPayment.setUnbwbje(unClearPayment.getBwbje());
							}
							unClearPayment.setExchangerate(rat);
							
							
							unClearPaymentList.add(unClearPayment);
						}
//					}
				}
			}
		}
	}

	/**
	 * 
	 * 保存
	 * 
	 * @param supplierSinglClear
	 */
	public void _saveOrUpdate(SupplierSinglClear supplierSinglClear, BusinessObject businessObject)
	{
		// 删除所有1比多子对象数据。
		this.supplierSinglClearJdbcDao.deleteSupplierSingleClearUnderOneToManySubObject(supplierSinglClear.getSuppliersclearid());
		_saveOrUpdateSupplierSinglClear(supplierSinglClear);

	}

	/**
	 * 判断是否可以出清帐凭证。
	 * 
	 * @param supplierSinglClear
	 * @return
	 */
	public boolean isHaveClearVoucher(SupplierSinglClear supplierSinglClear)
	{
		String businessId = supplierSinglClear.getSuppliersclearid();
		String deptId = supplierSinglClear.getDeptid();
		String bukrs = this.voucherService.getCompanyIDByDeptID(deptId);
		String supplier = supplierSinglClear.getSupplier();
		if (judgeIsAllClear(supplierSinglClear))
		{
			return true;
		}
		else
		{
			// 合同
			List<String> clearedContractNo = judgeContractIsAllClear(supplierSinglClear);
			if (null != clearedContractNo && clearedContractNo.size() > 0)
			{
				// 处理合同下清帐凭证行项目.
				return true;
			}

			// 立项下
			List<String> clearedProjectNo = judgeProjectIsAllClear(supplierSinglClear,clearedContractNo, "");
			if (null != clearedProjectNo && clearedProjectNo.size() > 0)
			{
				// 处理立项下清帐凭证行项目.
				return true;

			}
			// 供应商清帐
			if (judgeSupplierIsAllClear(supplierSinglClear,businessId, supplier, "", "", bukrs))
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
	 * 保存
	 * 
	 * @param supplierSinglClear
	 */
	public void _saveOrUpdateSupplierSinglClear(SupplierSinglClear supplierSinglClear)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(supplierSinglClear);

		Set<UnClearSupplieBill> unClearSupplieBillSet = supplierSinglClear.getUnClearSupplieBill();
		Set<UnClearSupplieBill> newUnClearSupplieBillSet = null;
		//哪一边不能全清
//		String clearFlag = checkAllClear(supplierSinglClear);
//		//汇率
//		BigDecimal rate = getUnoffAmoutRate(supplierSinglClear);
		
		if (null != unClearSupplieBillSet)
		{
			newUnClearSupplieBillSet = new HashSet();
			Iterator<UnClearSupplieBill> itUnClearSupplieBill = unClearSupplieBillSet.iterator();
			while (itUnClearSupplieBill.hasNext())
			{
				UnClearSupplieBill unClearSupplieBill = (UnClearSupplieBill) itUnClearSupplieBill.next();
				unClearSupplieBill.setUnclearsbillid(null);
				VendorTitle vendorTitle = vendorTitleJdbcDao.getByVendortitleid(unClearSupplieBill.getVendortitleid());
				// 单据为审批通过即确认清帐，则需要判断票款是否已结清。
				if (BusinessState.SUBMITPASS.equals(supplierSinglClear.getBusinessstate()) )
				{
					String invoice = unClearSupplieBill.getInvoice();
					if(StringUtils.isNullBlank(invoice))invoice="";
					String vendortitleid = unClearSupplieBill.getVendortitleid();
					String clearid = supplierSinglClear.getSuppliersclearid();
					// 当前单据，票清帐金额总计。
					BigDecimal billClearAmount = unClearSupplieBill.getClearamount();
					// 发票总金额
					BigDecimal billTotAmount = unClearSupplieBill.getReceivableamount();
					// 票已清金额。
					BigDecimal billsumClearedAmount = this.vendorService.getSumClearAmount(invoice, BusinessState.ALL_SUBMITPASS);

					// 票已清金额+当前单据上清票金额 = 票总金额，则票已经结清。
					if (billsumClearedAmount.add(billClearAmount).compareTo(billTotAmount) == 0)
					{
						log.debug("发票号:" + invoice + "，票已清金额+当前单据上清票金额 = 票总金额:" + billsumClearedAmount + "+" + billClearAmount + " = " + billTotAmount);
						
						if(checkVoucher(clearid,"A")){
							this.vendorService.updateVendorTitleIsCleared(vendortitleid, Constants.cleared.sapIsCleared);
						}else{
							this.vendorService.updateVendorTitleIsCleared(vendortitleid, Constants.cleared.isCleared);
						}
					}
//					if("billClear".equals(clearFlag)){
//						//未收款本位币
//						BigDecimal unoff = unClearSupplieBill.getBwbje();
//						//本次清帐金额本位币
//						BigDecimal clearBwbje = unClearSupplieBill.getClearamount().multiply(rate);
//						vendorTitle.setBwbje(unoff.subtract(clearBwbje));
//						vendorTitleHibernateDao.saveOrUpdate(vendorTitle);
//					}
				}
				newUnClearSupplieBillSet.add(unClearSupplieBill);
			}
		}

		Set<UnClearPayment> unClearPaymentSet = supplierSinglClear.getUnClearPayment();
		Set<UnClearPayment> newUnClearPaymentSet = null;
		if (null != unClearPaymentSet)
		{
			newUnClearPaymentSet = new HashSet();
			Iterator<UnClearPayment> itUnClearPayment = unClearPaymentSet.iterator();
			while (itUnClearPayment.hasNext())
			{
				UnClearPayment unClearPayment = (UnClearPayment) itUnClearPayment.next();
				unClearPayment.setUnclearpaymentid(null);
				VendorTitle vendorTitle = vendorTitleJdbcDao.getByVendortitleid(unClearPayment.getVendortitleid());
				// 单据为审批通过即确认清帐，则需要判断票款是否已结清。
				if (BusinessState.SUBMITPASS.equals(supplierSinglClear.getBusinessstate()) )
				{
					
					String paymentId = unClearPayment.getPaymentid();
					String vendortitleid = unClearPayment.getVendortitleid();
					String clearid = supplierSinglClear.getSuppliersclearid();
					//付款ID为空的情况
					if(StringUtils.isNullBlank(paymentId)){	
						// 已清金额
						BigDecimal offsetamount2 = getSumClearAmount(unClearPayment.getVendortitleid().trim(), BusinessState.ALL_SUBMITPASS);// 根据vendorTitleId到YUNCLEARPAYMENT计算已经金额。
						
						//当前单据上清票金额
						BigDecimal nowclearamount2 = unClearPayment.getNowclearamount();				
						//款总金额
						BigDecimal paymentamount2 = unClearPayment.getPaymentamount();		
						
						// 款已清金额+当前单据上清票金额 = 款总金额，则票已经结清。
						if (offsetamount2.add(nowclearamount2).compareTo(paymentamount2) == 0){
							log.debug("款已清金额+当前单据上清帐金额 = 款分配行项总金额:" + offsetamount2 + "+" + nowclearamount2 + " = " + paymentamount2);
							
							if(checkVoucher(clearid,"A")){
								this.vendorService.updateVendorTitleIsCleared(vendortitleid, Constants.cleared.sapIsCleared);
							}else{
								this.vendorService.updateVendorTitleIsCleared(vendortitleid, Constants.cleared.isCleared);
							}
						}
					}else{
						String paymentItemId = unClearPayment.getPaymentitemid();
						// 付款行项目，金额。
						BigDecimal paymentamount = unClearPayment.getPaymentamount();
						// 付款行项目，已清金额。
						BigDecimal offsetamount = this.paymentItemService.getSumClearAmount(paymentItemId.trim(), BusinessState.ALL_SUBMITPASS);
						// 本次抵消金额
						BigDecimal nowclearamount = unClearPayment.getNowclearamount();

						// 款已清金额+当前单据上清票金额 = 款总金额，则票已经结清。
						if (offsetamount.add(nowclearamount).compareTo(paymentamount) == 0)
						{
							log.debug("paymentItemId:" + paymentItemId + "，款已清金额+当前单据上清帐金额 = 款分配行项总金额:" + offsetamount + "+" + nowclearamount + " = " + paymentamount);
							
							if(checkVoucher(clearid,"A")){
								this.vendorService.updateVendorTitleIsCleared(vendortitleid, Constants.cleared.sapIsCleared);
								this.paymentItemService.updatePaymentItemIsCleared(paymentItemId, Constants.cleared.sapIsCleared);
							}else{
								this.vendorService.updateVendorTitleIsCleared(vendortitleid, Constants.cleared.isCleared);
								this.paymentItemService.updatePaymentItemIsCleared(paymentItemId, Constants.cleared.isCleared);
							}			
						}
					}
//					if("paymentClear".equals(clearFlag)){
//						//未收款本位币
//						BigDecimal unoff = unClearPayment.getBwbje();
//						//本次已抵消金额本位币
//						BigDecimal clearBwbje = unClearPayment.getNowclearamount().multiply(rate);
//						vendorTitle.setBwbje(unoff.subtract(clearBwbje));
//						vendorTitleHibernateDao.saveOrUpdate(vendorTitle);
//					}

				}

				newUnClearPaymentSet.add(unClearPayment);
			}
		}
		supplierSinglClear.setUnClearPayment(newUnClearPaymentSet);
		//生成单号
		if (StringUtils.isNullBlank(supplierSinglClear.getSupplierclearno())) {
			String supplierClearNo = NumberService.getNextObjectNumber("supplierSingleClear",supplierSinglClear);			
			supplierSinglClear.setSupplierclearno(supplierClearNo);
		}
		supplierSinglClearHibernateDao.saveOrUpdate(supplierSinglClear);
		// 单据为审批通过即确认清帐，则需要判断票款是否已结清。
		if (BusinessState.SUBMITPASS.equals(supplierSinglClear.getBusinessstate()))
		{
			updateAllDataIsSAPCleared(supplierSinglClear);
		}
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(supplierSinglClear);
	}
	/**
	 * 更新旧的title,和收付款，的 iscleared sap清帐
	 * @param subject 客户编号 ，对应voucheritme的科目号
	 * @param taxCode 合同号或立项号，对应voucheritem中的taxCode
	 */
	public void updateOldTitle(SupplierSinglClear supplierSinglClear){
		String businessid = supplierSinglClear.getSuppliersclearid();
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
			VendorTitle vendorTitle = vendorTitleJdbcDao.getByVoucherNo(companyCode, voucherNo, fiYear, rowNumber);
			if(null != vendorTitle){
				this.vendorService.updateVendorTitleIsCleared(vendorTitle.getVendortitleid(), Constants.cleared.sapIsCleared);				
			}
			String businessItemId = this.voucherItemJdbcDao.getBusinessId(voucherNo, companyCode, fiYear, rowNumber);
			// 收款信息
//			Collect collect = this.collectHibernateDao.getDetached(businessId);
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
//			SupplierRefundItem supplierRefundItem = supplierRefundItemService._get(businessItemId);
			SupplierRefundItem supplierRefundItem =  supplierRefundItemHibernateDao.get(businessItemId);
			
			if(null != supplierRefundItem){
				customerRefundItemJdbcDao.updateRefundItemIsCleared(businessItemId, Constants.cleared.sapIsCleared);
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
	 * 更新所有相关的数据为SAP已清状态。
	 * 
	 * @param supplierSinglClear
	 * @return
	 */
	public void updateAllDataIsSAPCleared(SupplierSinglClear supplierSinglClear)
	{
		String businessId = supplierSinglClear.getSuppliersclearid();
		String deptId = supplierSinglClear.getDeptid();
		String bukrs = this.voucherService.getCompanyIDByDeptID(deptId);
		String supplier = supplierSinglClear.getSupplier();
		// 单据本次是否可全清
		String contractNos = "";
		String projectNos = "";
		String vendortitleidsSqlIn = "";
		String paymentItemIdsSqlIn = "";

		if (judgeIsAllClear(supplierSinglClear))
		{
			vendortitleidsSqlIn = "select VENDORTITLEID from YUNCLEARSUPPBILL where SUPPLIERSCLEARID='" + businessId + "'";
			paymentItemIdsSqlIn = "select PAYMENTITEMID from YUNCLEARPAYMENT where SUPPLIERSCLEARID='" + businessId + "'";
			this.vendorTitleJdbcDao.updateIsCleared(vendortitleidsSqlIn, cleared.sapIsCleared);
			this.paymentItemJdbcDao.updateIsCleared(paymentItemIdsSqlIn, cleared.sapIsCleared);
		}
		else
		{
			// 合同
			List<String> clearedContractNo = judgeContractIsAllClear(supplierSinglClear);
			if (null != clearedContractNo && clearedContractNo.size() > 0)
			{
				// 循环所有合同号。
				for (String conntractNo : clearedContractNo)
				{
					contractNos = contractNos + "'" + conntractNo + "',";
				}
				// 处理合同下清帐凭证行项目.
				if (!StringUtils.isNullBlank(contractNos))
				{
					contractNos = contractNos.substring(0, contractNos.length() - 1);
				}

				vendortitleidsSqlIn = "select VENDORTITLEID from YUNCLEARSUPPBILL where SUPPLIERSCLEARID='" + businessId + "' and CONTRACT_NO in(" + contractNos + ")";
				paymentItemIdsSqlIn = "select PAYMENTITEMID from YUNCLEARPAYMENT  where SUPPLIERSCLEARID='" + businessId + "' and CONTRACT_NO in(" + contractNos + ")";
				this.vendorTitleJdbcDao.updateIsCleared(vendortitleidsSqlIn, cleared.sapIsCleared);
				this.paymentItemJdbcDao.updateIsCleared(paymentItemIdsSqlIn, cleared.sapIsCleared);

				this.vendorTitleJdbcDao.updateIsClearedByContractNo(contractNos, cleared.sapIsCleared);
				this.paymentItemJdbcDao.updateIsClearedByContractNo(contractNos, cleared.sapIsCleared);
			}

			// 立项下
			List<String> clearedProjectNo = judgeProjectIsAllClear(supplierSinglClear,clearedContractNo, contractNos);
			if (null != clearedProjectNo && clearedProjectNo.size() > 0)
			{
				// 处理立项下清帐凭证行项目.
				// 循环所有立項号。
				for (String projectNo : clearedProjectNo)
				{
					projectNos = projectNos + "'" + projectNo + "',";
				}

				if (!StringUtils.isNullBlank(projectNos))
				{
					projectNos = projectNos.substring(0, projectNos.length() - 1);
				}

				vendortitleidsSqlIn = "select VENDORTITLEID from YUNCLEARSUPPBILL where SUPPLIERSCLEARID='" + businessId + "' and PROJECT_NO in(" + projectNos + ") ";
				if (!StringUtils.isNullBlank(contractNos))
					vendortitleidsSqlIn += " and CONTRACT_NO not in(" + contractNos + ")";

				paymentItemIdsSqlIn = "select PAYMENTITEMID from YUNCLEARPAYMENT  where SUPPLIERSCLEARID='" + businessId + "' and PROJECT_NO in(" + projectNos + ") ";
				if (!StringUtils.isNullBlank(contractNos))
					paymentItemIdsSqlIn += " and CONTRACT_NO not in(" + contractNos + ")";

				this.vendorTitleJdbcDao.updateIsCleared(vendortitleidsSqlIn, cleared.sapIsCleared);
				this.paymentItemJdbcDao.updateIsCleared(paymentItemIdsSqlIn, cleared.sapIsCleared);

				this.vendorTitleJdbcDao.updateIsClearedByProjectNo(contractNos, projectNos, cleared.sapIsCleared);
				this.paymentItemJdbcDao.updateIsClearedByProjectNo(contractNos, projectNos, cleared.sapIsCleared);
			}
			// 供应商清帐
			if (judgeSupplierIsAllClear(supplierSinglClear,businessId, supplier, contractNos, projectNos, bukrs))
			{
				vendortitleidsSqlIn = "select VENDORTITLEID from YUNCLEARSUPPBILL where SUPPLIERSCLEARID='" + businessId + "'";
				if (!StringUtils.isNullBlank(projectNos))
					vendortitleidsSqlIn += " and PROJECT_NO not in(" + projectNos + ")";
				if (!StringUtils.isNullBlank(contractNos))
					vendortitleidsSqlIn += " and CONTRACT_NO not in(" + contractNos + ")";

				paymentItemIdsSqlIn = "select PAYMENTITEMID from YUNCLEARPAYMENT  where SUPPLIERSCLEARID='" + businessId + "'";
				if (!StringUtils.isNullBlank(projectNos))
					paymentItemIdsSqlIn += " and PROJECT_NO not in(" + projectNos + ") ";
				if (!StringUtils.isNullBlank(contractNos))
					paymentItemIdsSqlIn += " and CONTRACT_NO not in(" + contractNos + ")";

				this.vendorTitleJdbcDao.updateIsCleared(vendortitleidsSqlIn, cleared.sapIsCleared);
				this.paymentItemJdbcDao.updateIsCleared(paymentItemIdsSqlIn, cleared.sapIsCleared);

				this.vendorTitleJdbcDao.updateIsClearedBySupplier(supplier, contractNos, projectNos, cleared.sapIsCleared);
				this.paymentItemJdbcDao.updateIsClearedBySupplier(supplier, contractNos, projectNos, cleared.sapIsCleared);
			}
		}
	}

	/**
	 * 作废
	 * 
	 * @param suppliersClearId
	 */
	public void _blankOut(SupplierSinglClear supplierSinglClear)
	{
		// 作废当前单据
		this.supplierSinglClearJdbcDao.updateBusinessstate(supplierSinglClear.getSuppliersclearid(), BusinessState.BLANKOUT);
		// 更新所有已经打上已结清标志的票、款，为未结清。
		Set<UnClearSupplieBill> unClearSupplieBillSet = supplierSinglClear.getUnClearSupplieBill();
		Set<UnClearPayment> unClearPaymentSet = supplierSinglClear.getUnClearPayment();

		if (null != unClearSupplieBillSet)
		{
			Iterator<UnClearSupplieBill> itUnClearSupplieBill = unClearSupplieBillSet.iterator();
			while (itUnClearSupplieBill.hasNext())
			{
				UnClearSupplieBill unClearSupplieBill = (UnClearSupplieBill) itUnClearSupplieBill.next();
				String invoice = unClearSupplieBill.getInvoice();
				String vendortitleid = unClearSupplieBill.getVendortitleid();
				log.debug("vendortitleid:UnClearSupplieBill:" + vendortitleid);
				this.vendorService.updateVendorTitleIsCleared(vendortitleid, Constants.cleared.notCleared);

			}
		}

		if (null != unClearPaymentSet)
		{
			Iterator<UnClearPayment> itUnClearPayment = unClearPaymentSet.iterator();
			while (itUnClearPayment.hasNext())
			{
				UnClearPayment unClearPayment = (UnClearPayment) itUnClearPayment.next();

				String paymentItemId = unClearPayment.getPaymentitemid();
				String paymentId = unClearPayment.getPaymentid();
				String vendortitleid = unClearPayment.getVendortitleid();
				log.debug("vendortitleid:UnClearPayment:" + vendortitleid + " ;paymentItemId:" + paymentItemId);
				this.vendorService.updateVendorTitleIsCleared(vendortitleid, Constants.cleared.notCleared);
				this.paymentItemService.updatePaymentItemIsCleared(paymentItemId, Constants.cleared.notCleared);
			}
		}
	}

	/**
	 * 供应商未清抬头数据中借贷为S的数据，判断是否已结清
	 * 
	 * @param supplierSinglClear
	 */
	public void updateVendorTitleIsCleared(SupplierSinglClear supplierSinglClear)
	{
		Set<UnClearPayment> unClearPaymentSet = supplierSinglClear.getUnClearPayment();
		Set<Map<String, String>> paymentIds = new HashSet<Map<String, String>>();
		Set<Map<String, Object>> vendortitleIds = new HashSet<Map<String, Object>>();
		String clearid = supplierSinglClear.getSuppliersclearid();
		if (null != unClearPaymentSet)
		{
			Iterator<UnClearPayment> itUnClearPayment = unClearPaymentSet.iterator();
			while (itUnClearPayment.hasNext())
			{
				UnClearPayment unClearPayment = (UnClearPayment) itUnClearPayment.next();
				Map map = new HashMap<String, String>();
				map.put("VENDORTITLEID", unClearPayment.getVendortitleid());
				if(StringUtils.isNullBlank(unClearPayment.getPaymentid())){
//					map.put("UNCLEARPAYMENT", unClearPayment);
//					vendortitleIds.add(map);					
				}else{
					map.put("PAYMENTID", unClearPayment.getPaymentid());					
					paymentIds.add(map);
				}
			}
		}

		if (null != paymentIds && paymentIds.size() > 0)
		{
			for (Map<String, String> map : paymentIds)
			{
				String vendortitleid = map.get("VENDORTITLEID");
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
				log.debug("PAYMENTID:" + map.get("PAYMENTID") + " ; VENDORTITLEID:" + vendortitleid + ",paymentItemList.size():" + i + ",j:" + j);

				if (i > 0 && i == j)
				{
					if(checkVoucher(clearid,"A")){
						this.vendorService.updateVendorTitleIsCleared(vendortitleid, Constants.cleared.sapIsCleared);
					}else{
						this.vendorService.updateVendorTitleIsCleared(vendortitleid, Constants.cleared.isCleared);
					}
				}
			}

		}
		//更新旧的clear 为sap
		if(checkVoucher(clearid,"A")){
			
			this.updateOldTitle(supplierSinglClear);
		}
		//没有paymentId的情况
//		if (null != vendortitleIds && vendortitleIds.size() > 0)
//		{
//			for (Map<String, Object> map : vendortitleIds)
//			{
//				String vendortitleid = (String)map.get("VENDORTITLEID");	
//				// 已清金额
//				BigDecimal offsetamount = getSumClearAmount(vendortitleid, BusinessState.ALL_SUBMITPASS);// 根据vendorTitleId到YUNCLEARPAYMENT计算已经金额。
//				UnClearPayment unClearPayment2 = (UnClearPayment)map.get("UNCLEARPAYMENT");
//				//当前单据上清票金额
//				BigDecimal nowclearamount = unClearPayment2.getNowclearamount();				
//				//款总金额
//				BigDecimal paymentamount = unClearPayment2.getPaymentamount();		
//				
//				// 款已清金额+当前单据上清票金额 = 款总金额，则票已经结清。
//				if (offsetamount.add(nowclearamount).compareTo(paymentamount) == 0){
//					this.vendorService.updateVendorTitleIsCleared(vendortitleid, Constants.cleared.isCleared);
//				}
//			}
//
//		}
		
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.infolion.xdss3.singleClearGen.service.SupplierSinglClearServiceGen
	 * #_delete(java.lang.String,
	 * com.infolion.platform.bo.domain.BusinessObject)
	 */
	@Override
	public void _delete(String supplierSinglClearId, BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(supplierSinglClearId))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("suppliersclearid"));
		SupplierSinglClear supplierSinglClear = this.supplierSinglClearHibernateDao.load(supplierSinglClearId);

		if (supplierSinglClear.getBusinessstate().equals(BusinessState.SUBMITPASS) || supplierSinglClear.getBusinessstate().equals(BusinessState.BLANKOUT) || supplierSinglClear.getBusinessstate().equals(BusinessState.SUBMITNOTPASS))
		{
			throw new BusinessException("删除数据错误，确认清帐、作废的数据不能删除！");
		}
		_delete(supplierSinglClear);
	}

	/**
	 * 保存凭证。
	 * 
	 * @param supplierSinglClear
	 * @param unClearSupplieBillItems
	 * @param fundFlow
	 * @param settleSubject
	 * @return
	 */
	public List<String> _saveVoucher(SupplierSinglClear supplierSinglClear, Set<UnClearSupplieBill> unClearSupplieBillItems, FundFlow fundFlow, SettleSubject settleSubject)
	{
		List<String> voucherIds = new ArrayList<String>();
		
		// 币别
		String currency = "";
		String supplier = supplierSinglClear.getSupplier();
		Voucher voucher = null;
		if (null != settleSubject || null != fundFlow)
		{
			// 凭证
			voucher = getVoucher(supplierSinglClear);
			String deptId = supplierSinglClear.getDeptid();
			List<VoucherItem> voucherItemList = new ArrayList<VoucherItem>();
			Iterator<UnClearSupplieBill> it = unClearSupplieBillItems.iterator();

			int iRowNo = 1; // 行项目号
			// 调整差额总额
			BigDecimal sumAdjustamount = new BigDecimal(0);
			while (it.hasNext())
			{
				UnClearSupplieBill unClearSupplieBill = (UnClearSupplieBill) it.next();
				// 当调整差额 有填入值。
				if (unClearSupplieBill.getAdjustamount().abs().compareTo(new BigDecimal(0)) == 1)
				{
					if (StringUtils.isNullBlank(currency))
					{
						currency = unClearSupplieBill.getCurrency();
					}
				}
				BigDecimal adjustamount = unClearSupplieBill.getAdjustamount();
				sumAdjustamount = sumAdjustamount.add(adjustamount);
			}
			
			if("".equals(currency))currency = supplierSinglClear.getCurrencytext();
			
			voucher.setCurrency(currency);
			String taxCode =  getTaxCode(supplierSinglClear);
			// 开始添加凭证行项目
			VoucherItem voucherItem = new VoucherItem();
			voucherItem.setRownumber(String.valueOf(iRowNo));
			voucherItem.setCurrency(currency);
			voucherItem.setAmount(sumAdjustamount.abs());
			if (sumAdjustamount.signum() == 1)
			{
				voucherItem.setCheckcode("21");
				voucherItem.setDebitcredit("S");
			}
			else if (sumAdjustamount.signum() == -1)
			{
				voucherItem.setCheckcode("31");
				voucherItem.setDebitcredit("H");
			}
			voucherItem.setAmount2(new BigDecimal(0));
			voucherItem.setSubject(supplier);
			// SupplierJdbcDao supplierJdbcDao = (SupplierJdbcDao)
			// EasyApplicationContextUtils.getBeanByName("supplierJdbcDao");
			// String subjectdescribe =
			// supplierJdbcDao.getSupplierName(supplier,bukrs);
			voucherItem.setSubjectdescribe(supplierSinglClear.getSupplier_htext());
			voucherItem.setDepid(supplierSinglClear.getDepid());
			voucherItem.setText(supplierSinglClear.getText());
			
			String bukrs = supplierSinglClear.getCompanyno();
			String subject = this.customerRefundItemJdbcDao.getSubjectBySuppliers(supplierSinglClear.getSupplier(),bukrs);
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
				
				if (amount.abs().compareTo(new BigDecimal(0)) == 1)
				{
					debitcredit = settleSubject.getDebtcredit1();
					iRowNo = iRowNo + 1;
					// 开始添加凭证行项目
					VoucherItem voucherItems = new VoucherItem();
					voucherItems.setRownumber(String.valueOf(iRowNo));
					voucherItems.setCurrency(currency);
					voucherItems.setAmount(amount);
					voucherItems.setDebitcredit(debitcredit);

					if ("H".equalsIgnoreCase(debitcredit))
					{
						voucherItems.setCheckcode("50");
					}
					else if ("S".equalsIgnoreCase(debitcredit))
					{
						voucherItems.setCheckcode("40");
					}
					voucherItems.setAmount2(settleSubject.getStandardamount1());
					voucherItems.setSubject(settleSubject.getSettlesubject1());
					voucherItems.setSubjectdescribe(settleSubject.getSettlesubject1_htext());
					voucherItems.setDepid(settleSubject.getDepid1());
					voucherItems.setText(supplierSinglClear.getText());
					voucherItems.setCostcenter(settleSubject.getCostcenter1());
					

					if("Y".equals(settleSubject.getAntiaccount1())){
						voucherItems.setUncheckflag("X");
	                }else{
	                	voucherItems.setUncheckflag("");
	                }
//					voucherItems.setUncheckflag(settleSubject.getAntiaccount1());
					voucherItems.setOrderrowno(settleSubject.getRowno1());
					voucherItems.setSalesorder(settleSubject.getOrderno1());	
//					voucherItems.setControlaccount(subject);
//					voucherItems.setCaremark(subjectname);
					voucherItems.setTaxcode(taxCode);
					voucherItems.setVoucher(voucher);
					voucherItemList.add(voucherItems);
				}

				amount = settleSubject.getAmount2();
				
				if (amount.abs().compareTo(new BigDecimal(0)) == 1)
				{
					debitcredit = settleSubject.getDebtcredit2();
					iRowNo = iRowNo + 1;
					// 开始添加凭证行项目
					VoucherItem voucherItems = new VoucherItem();
					voucherItems.setRownumber(String.valueOf(iRowNo));
					voucherItems.setCurrency(currency);
					voucherItems.setAmount(amount);
					voucherItems.setDebitcredit(debitcredit);
					if ("H".equalsIgnoreCase(debitcredit))
					{
						voucherItems.setCheckcode("50");
					}
					else if ("S".equalsIgnoreCase(debitcredit))
					{
						voucherItems.setCheckcode("40");
					}
					voucherItems.setAmount2(settleSubject.getStandardamount2());
					voucherItems.setSubject(settleSubject.getSettlesubject2());
					voucherItems.setSubjectdescribe(settleSubject.getSettlesubject2_htext());
					voucherItems.setDepid(settleSubject.getDepid2());
					voucherItems.setText(supplierSinglClear.getText());
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
				
				if (amount.abs().compareTo(new BigDecimal(0)) == 1)
				{
					debitcredit = settleSubject.getDebtcredit3();
					iRowNo = iRowNo + 1;
					// 开始添加凭证行项目
					VoucherItem voucherItems = new VoucherItem();
					voucherItems.setRownumber(String.valueOf(iRowNo));
					voucherItems.setCurrency(currency);
					voucherItems.setAmount(amount);
					voucherItems.setDebitcredit(debitcredit);
					if ("H".equalsIgnoreCase(debitcredit))
					{
						voucherItems.setCheckcode("50");
					}
					else if ("S".equalsIgnoreCase(debitcredit))
					{
						voucherItems.setCheckcode("40");
					}
					voucherItems.setAmount2(settleSubject.getStandardamount3());
					voucherItems.setSubject(settleSubject.getSettlesubject3());
					voucherItems.setSubjectdescribe(settleSubject.getSettlesubject3_htext());
					voucherItems.setDepid(settleSubject.getDepid3());
					voucherItems.setText(supplierSinglClear.getText());
					voucherItems.setCostcenter(settleSubject.getCostcenter3());	
//					voucherItems.setUncheckflag(settleSubject.getAntiaccount3());
					if("Y".equals(settleSubject.getAntiaccount3())){
						voucherItems.setUncheckflag("X");
	                }else{
	                	voucherItems.setUncheckflag("");
	                }
					voucherItems.setOrderrowno(settleSubject.getRowno3());
					voucherItems.setSalesorder(settleSubject.getOrderno3());
//					voucherItems.setControlaccount(subject);
//					voucherItems.setCaremark(subjectname);
					voucherItems.setTaxcode(taxCode);
					voucherItems.setVoucher(voucher);
					voucherItemList.add(voucherItems);
				}

				amount = settleSubject.getAmount4();
				
				if (amount.abs().compareTo(new BigDecimal(0)) == 1)
				{
					debitcredit = settleSubject.getDebtcredit4();
					iRowNo = iRowNo + 1;
					// 开始添加凭证行项目
					VoucherItem voucherItems = new VoucherItem();
					voucherItems.setRownumber(String.valueOf(iRowNo));
					voucherItems.setCurrency(currency);
					voucherItems.setAmount(amount);
					voucherItems.setDebitcredit(debitcredit);
					if ("H".equalsIgnoreCase(debitcredit))
					{
						voucherItems.setCheckcode("50");
					}
					else if ("S".equalsIgnoreCase(debitcredit))
					{
						voucherItems.setCheckcode("40");
					}
					voucherItems.setAmount2(settleSubject.getStandardamount4());
					voucherItems.setSubject(settleSubject.getSettlesubject4());
					voucherItems.setSubjectdescribe(settleSubject.getSettlesubject4_htext());
					voucherItems.setDepid(settleSubject.getDepid4());
					voucherItems.setText(supplierSinglClear.getText());
					voucherItems.setProfitcenter(settleSubject.getProfitcenter());
//					voucherItems.setUncheckflag(settleSubject.getAntiaccount4());
					if("Y".equals(settleSubject.getAntiaccount4())){
						voucherItems.setUncheckflag("X");
	                }else{
	                	voucherItems.setUncheckflag("");
	                }
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
				
				if (amount.abs().compareTo(new BigDecimal(0)) == 1)
				{
					debitcredit = fundFlow.getDebtcredit1();
					iRowNo = iRowNo + 1;
					// 开始添加凭证行项目
					VoucherItem voucherItems = new VoucherItem();
					voucherItems.setRownumber(String.valueOf(iRowNo));
					voucherItems.setCurrency(currency);
					voucherItems.setAmount(amount);
					voucherItems.setDebitcredit(debitcredit);
					if ("H".equalsIgnoreCase(debitcredit))
					{
						if(StringUtils.isNullBlank(fundFlow.getSpecialaccount1())){
							voucherItem.setCheckcode("11");
						}else{
							voucherItems.setCheckcode("19");
						}
					}
					else if ("S".equalsIgnoreCase(debitcredit))
					{
						if(StringUtils.isNullBlank(fundFlow.getSpecialaccount1())){
							voucherItem.setCheckcode("01");
						}else{
							voucherItems.setCheckcode("09");
						}
					}
					voucherItems.setAmount2(fundFlow.getStandardamount1());
					voucherItems.setSubject(fundFlow.getCustomer1());
					voucherItems.setSubjectdescribe(fundFlow.getCustomer1_htext());
					voucherItems.setDepid(fundFlow.getDepid1());
					voucherItems.setText(supplierSinglClear.getText());
					voucherItems.setGlflag(fundFlow.getSpecialaccount1());
//					voucherItems.setUncheckflag(fundFlow.getAntiaccount1());
					if("Y".equals(fundFlow.getAntiaccount1())){
						voucherItems.setUncheckflag("X");
	                }else{
	                	voucherItems.setUncheckflag("");
	                }
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
				
				if (amount.abs().compareTo(new BigDecimal(0)) == 1)
				{
					debitcredit = fundFlow.getDebtcredit2();
					iRowNo = iRowNo + 1;
					// 开始添加凭证行项目
					VoucherItem voucherItems = new VoucherItem();
					voucherItems.setRownumber(String.valueOf(iRowNo));
					voucherItems.setCurrency(currency);
					voucherItems.setAmount(amount);
					voucherItems.setDebitcredit(debitcredit);
					if ("H".equalsIgnoreCase(debitcredit))
					{
						if(StringUtils.isNullBlank(fundFlow.getSpecialaccount2())){
							voucherItem.setCheckcode("11");
						}else{
							voucherItems.setCheckcode("19");
						}
					}
					else if ("S".equalsIgnoreCase(debitcredit))
					{
						if(StringUtils.isNullBlank(fundFlow.getSpecialaccount2())){
							voucherItem.setCheckcode("01");
						}else{
							voucherItems.setCheckcode("09");
						}
					}
					voucherItems.setAmount2(fundFlow.getStandardamount2());
					voucherItems.setSubject(fundFlow.getCustomer2());
					voucherItems.setSubjectdescribe(fundFlow.getCustomer2_htext());
					voucherItems.setDepid(fundFlow.getDepid2());
					voucherItems.setText(supplierSinglClear.getText());
					voucherItems.setGlflag(fundFlow.getSpecialaccount2());	
//					voucherItems.setUncheckflag(fundFlow.getAntiaccount2());
					if("Y".equals(fundFlow.getAntiaccount2())){
						voucherItems.setUncheckflag("X");
	                }else{
	                	voucherItems.setUncheckflag("");
	                }
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
				
				if (amount.abs().compareTo(new BigDecimal(0)) == 1)
				{
					debitcredit = fundFlow.getDebtcredit3();
					iRowNo = iRowNo + 1;
					// 开始添加凭证行项目
					VoucherItem voucherItems = new VoucherItem();
					voucherItems.setRownumber(String.valueOf(iRowNo));
					voucherItems.setCurrency(currency);
					voucherItems.setAmount(amount);
					voucherItems.setDebitcredit(debitcredit);
					if ("H".equalsIgnoreCase(debitcredit))
					{
						if(StringUtils.isNullBlank(fundFlow.getSpecialaccount3())){
							voucherItem.setCheckcode("11");
						}else{
							voucherItems.setCheckcode("19");
						}
					}
					else if ("S".equalsIgnoreCase(debitcredit))
					{
						if(StringUtils.isNullBlank(fundFlow.getSpecialaccount3())){
							voucherItem.setCheckcode("01");
						}else{
							voucherItems.setCheckcode("09");
						}
					}
					voucherItems.setAmount2(fundFlow.getStandardamount3());
					voucherItems.setSubject(fundFlow.getCustomer3());
					voucherItems.setSubjectdescribe(fundFlow.getCustomer3_htext());
					voucherItems.setDepid(fundFlow.getDepid3());
					voucherItems.setText(supplierSinglClear.getText());
					voucherItems.setGlflag(fundFlow.getSpecialaccount3());	
//					voucherItems.setUncheckflag(fundFlow.getAntiaccount3());
					if("Y".equals(fundFlow.getAntiaccount3())){
						voucherItems.setUncheckflag("X");
	                }else{
	                	voucherItems.setUncheckflag("");
	                }
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
			BigDecimal sumAmount2_s = new BigDecimal(0); // 所有财务结算借方本位币金额的和
			BigDecimal sumAmount2_h = new BigDecimal(0); // 所有财务结算贷方本位币金额的和			
			BigDecimal sumAmount3_s = new BigDecimal(0); // 所有财务结算借方金额的和
			BigDecimal sumAmount3_h = new BigDecimal(0); // 所有财务结算贷方金额的和
			
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
			//金额贷-借 -相减
			BigDecimal sumAmount5 = sumAmount3_h.subtract(sumAmount3_s);
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
			
			Set<VoucherItem> vi=new HashSet<VoucherItem>();
			vi.addAll(voucherItemList);
			voucher.setVoucherItem(vi);
			//判断是否已经生成损益凭证，
			Voucher voucher2=null;
			List<Voucher> li=voucherHibernateDao.getVoucherByBusinessId2(supplierSinglClear.getSuppliersclearid()," ");
			if(li!=null && li.size()!=0){				
				for(Voucher vo : li){
					if(!StringUtils.isNullBlank(vo.getVoucherno())){
						voucher2=vo;
						break;
					}
				} 
			}
			if(null == voucher2){
				this.voucherService._saveOrUpdate(voucher, null, BusinessObjectService.getBusinessObject("Voucher"));
				voucherHibernateDao.flush();
			}else{
				voucher=voucher2;
			}
			voucherIds.add(voucher.getVoucherid());
		}
		// 开始处理清帐凭证
		Voucher clearVoucher = _saveClearAccountVoucher(supplierSinglClear,voucher);

		if(null != voucher){
			this.voucherService._saveOrUpdate(voucher, null, BusinessObjectService.getBusinessObject("Voucher"));
		}
		if (null != clearVoucher)
		{
			voucherIds.add(clearVoucher.getVoucherid());
		}
		
		return voucherIds;
	}

	/**
	 * 取得凭证抬头数据
	 * 
	 * @param supplierSinglClear
	 * @return
	 */
	private Voucher getVoucher(SupplierSinglClear supplierSinglClear)
	{
		Voucher voucher = new Voucher();
		String fiperiod = "";
		String fiyear = "";
//		String deptId = supplierSinglClear.getDeptid();
//		String bukrs = this.voucherService.getCompanyIDByDeptID(deptId);
		// 添加凭证抬头数据
		voucher.setBusinessid(supplierSinglClear.getSuppliersclearid());
		voucher.setBusinesstype("09");
		voucher.setVouchertype("SA");
		voucher.setCompanycode(supplierSinglClear.getCompanyno());
		voucher.setExchangerate(new BigDecimal(1));
		voucher.setCurrency(supplierSinglClear.getCurrencytext());
//		voucher.setGsber(supplierSinglClear.getDepid());
		String accountdate = supplierSinglClear.getAccountdate().replace("-", "");
		BigDecimal sumAmount2 = new BigDecimal(0); // 所有财务结算本位币金额的和
		BigDecimal amount2 = new BigDecimal(0); // 财务结算本位币金额
		voucher.setCheckdate(accountdate);
		voucher.setVoucherclass("1");
		if (!StringUtils.isNullBlank(accountdate))
		{
			fiperiod = accountdate.substring(4, 6);
			fiyear = accountdate.substring(0, 4);
		}
		voucher.setFiperiod(fiperiod);// 会计期间
		voucher.setFiyear(fiyear); // 会计年度
		voucher.setImportdate(DateUtils.getCurrDate(DateUtils.DB_STORE_DATE).substring(0, 8)); // 输入日期
		UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
		voucher.setImporter(userContext.getUser().getUserName()); // 输入者
		voucher.setPreparer(userContext.getUser().getUserName()); // 预制者
		voucher.setVoucherdate(supplierSinglClear.getVoucherdate().replace("-", "")); // 凭证日期
		voucher.setVouchertext(supplierSinglClear.getText()); // 凭证抬头文本

		return voucher;
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
		voucherItem.setFlag("P");
		voucherItem.setAmount(new BigDecimal("0.00"));
		voucherItem.setAmount2(new BigDecimal("0.00"));

		return voucherItem;
	}

	/**
	 * 处理清帐凭证,
	 * 
	 * @param supplierSinglClear
	 */
	public Voucher _saveClearAccountVoucher(SupplierSinglClear supplierSinglClear,Voucher voucher)
	{
		String businessId = supplierSinglClear.getSuppliersclearid();
		String supplier = supplierSinglClear.getSupplier();
		Set<UnClearSupplieBill> unClearSupplieBillItems = supplierSinglClear.getUnClearSupplieBill();
		Set<UnClearPayment> unClearPaymentItems = supplierSinglClear.getUnClearPayment();
		// 币别
		String currency = "";
//		String deptId = supplierSinglClear.getDeptid();
//		String bukrs = this.voucherService.getCompanyIDByDeptID(deptId);
		String bukrs = supplierSinglClear.getCompanyno();
		// 凭证
		Voucher clearVoucher = getClearVoucher(supplierSinglClear, bukrs);
		Set<VoucherItem> voucherItemList = new HashSet<VoucherItem>();
		Iterator<UnClearSupplieBill> it = unClearSupplieBillItems.iterator();

		int rowNo = 1; // 行项目号
		while (it.hasNext())
		{
			UnClearSupplieBill unClearSupplieBill = (UnClearSupplieBill) it.next();

			if (StringUtils.isNullBlank(currency))
			{
				currency = unClearSupplieBill.getCurrency();
				break;
			}
		}
		if("".equals(currency))currency = supplierSinglClear.getCurrencytext();
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
		
		Set<UnClearSupplieBill> unclearSupplieBills = supplierSinglClear.getUnClearSupplieBill();
		for(UnClearSupplieBill unclearSupplieBill:unclearSupplieBills){
			
			billAmount = billAmount.add(unclearSupplieBill.getBwbje());
			if(unclearSupplieBill.getUnpaidamount().compareTo(unclearSupplieBill.getClearamount()) !=0){
				billF = true;
			}
			sumbillAmount = sumbillAmount.add(unclearSupplieBill.getReceivableamount());
			sumclearAmount = sumclearAmount.add(unclearSupplieBill.getClearamount());
			sumajAmount = sumajAmount.add(unclearSupplieBill.getAdjustamount());
			sumUnreceivableamou = sumUnreceivableamou.add(unclearSupplieBill.getUnpaidamount());
			sumUnoffsetamountBwb = sumUnoffsetamountBwb.add(unclearSupplieBill.getUnbwbje());
			if(unclearSupplieBill.getReceivableamount().compareTo(BigDecimal.valueOf(0)) ==0){
				sumclearAmountBwb = sumclearAmountBwb.add(unclearSupplieBill.getBwbje());
			}else{
//				sumclearAmountBwb = sumclearAmountBwb.add(unclearSupplieBill.getBwbje().divide(unclearSupplieBill.getReceivableamount(),3,BigDecimal.ROUND_HALF_EVEN).multiply(unclearSupplieBill.getClearamount()));
				sumclearAmountBwb = sumclearAmountBwb.add(unclearSupplieBill.getBwbje().multiply(unclearSupplieBill.getClearamount()).divide(unclearSupplieBill.getReceivableamount(),13,BigDecimal.ROUND_HALF_EVEN));
			}
		}
		
		for(UnClearPayment unClearPayment:unClearPaymentItems){
			collectAmount = collectAmount.add(unClearPayment.getBwbje());
			if(unClearPayment.getUnoffsetamount().compareTo(unClearPayment.getNowclearamount()) !=0){
				clearF = true;
			}
			sumcollectAmount = sumcollectAmount.add(unClearPayment.getPaymentamount());
			sumoffsetAmount = sumoffsetAmount.add(unClearPayment.getNowclearamount());
			sumUnoffsetamount = sumUnoffsetamount.add(unClearPayment.getUnoffsetamount());
			sumUnreceivableamouBwb= sumUnreceivableamouBwb.add(unClearPayment.getUnbwbje());
			if(unClearPayment.getPaymentamount().compareTo(BigDecimal.valueOf(0)) ==0){
				sumoffsetAmountBwb = sumoffsetAmountBwb.add(unClearPayment.getBwbje());
			}else{
//				sumoffsetAmountBwb = sumoffsetAmountBwb.add(unClearPayment.getBwbje().divide(unClearPayment.getPaymentamount(),3,BigDecimal.ROUND_HALF_EVEN).multiply(unClearPayment.getNowclearamount()));
				sumoffsetAmountBwb = sumoffsetAmountBwb.add(unClearPayment.getBwbje().multiply(unClearPayment.getNowclearamount()).divide(unClearPayment.getPaymentamount(),13,BigDecimal.ROUND_HALF_EVEN));
			}
		}
		if(null != voucher){
			for(VoucherItem voucherItem : voucher.getVoucherItem()){
				
				if ("21".equals(voucherItem.getCheckcode()))
				{
					ajValue =  ajValue.add(voucherItem.getAmount2());
				}
				else if ("31".equals(voucherItem.getCheckcode()))
				{
					ajValue =  ajValue.subtract(voucherItem.getAmount2());
				}
			}
		}
		String taxCode =  getTaxCode(supplierSinglClear);
		
		if (judgeIsAllClear(supplierSinglClear))
		{
			// 处理本次可以全清帐
			getClearVoucherItem(supplierSinglClear, clearVoucher, voucherItemList, currency,ajValue,taxCode);
		}
		else
		{
			// 合同
			List<String> clearedContractNo = judgeContractIsAllClear(supplierSinglClear);
			if (null != clearedContractNo && clearedContractNo.size() > 0)
			{
				// 处理合同下清帐凭证行项目.
				contractNos = getContractClearVoucherItem(supplierSinglClear, clearedContractNo, clearVoucher, voucherItemList, currency, billValue, itemValue,ajValue);
			}

			if (!StringUtils.isNullBlank(contractNos))
			{
				contractNos = contractNos.substring(0, contractNos.length() - 1);
			}
			// 立项下
			List<String> clearedProjectNo = judgeProjectIsAllClear(supplierSinglClear,clearedContractNo, contractNos);
			if (null != clearedProjectNo && clearedProjectNo.size() > 0)
			{
				// 处理立项下清帐凭证行项目.
				projectNos = getProjectClearVoucherItem(supplierSinglClear, clearVoucher, voucherItemList, contractNos, clearedContractNo, clearedProjectNo, currency, billValue, itemValue,ajValue);
			}

			if (!StringUtils.isNullBlank(projectNos))
			{
				projectNos = projectNos.substring(0, projectNos.length() - 1);
			}
			// 供应商清帐
			if (judgeSupplierIsAllClear(supplierSinglClear,businessId, supplier, contractNos, projectNos, bukrs))
			{
//				如果供应商层能清，就把合同层，立项层去掉
				voucherItemList = new HashSet<VoucherItem>();
				contractNos="";
				projectNos="";
				clearedContractNo=new ArrayList<String>();
				clearedProjectNo=new ArrayList<String>();
				getSupplierClearVoucherItem(supplierSinglClear, clearVoucher, voucherItemList, contractNos, projectNos, clearedContractNo, clearedProjectNo, currency, billValue, itemValue,ajValue, clearVoucher.getCompanycode());
			}

			// 判断是否外币,有调整金且没有清帐凭证的情况下。
			if (!"CNY".equalsIgnoreCase(currency)&&(sumajAmount.compareTo(new BigDecimal(0))!=0) && null != voucherItemList && voucherItemList.size() == 0)
			{
				BigDecimal subtractVlaue2 = new BigDecimal(0);			
				if(!clearF && !billF){
					//刚好两全清，款的本位币合计 -票的本位币合计
					subtractVlaue2 = sumUnoffsetamountBwb.subtract(sumUnreceivableamouBwb);
				}else{
									
					//票的一边全清
					if(!billF){
//						subtractVlaue2 = collectAmount.divide(sumcollectAmount,5,BigDecimal.ROUND_HALF_EVEN).multiply(sumoffsetAmount);
//						subtractVlaue2 = billAmount.subtract(collectAmount);
						//修改成各个行项目算汇率，再算清账本位币再相加						
						subtractVlaue2 = sumUnoffsetamountBwb.subtract(sumoffsetAmountBwb);
					}
					//款的一边全清
					if(!clearF){
//						subtractVlaue2 = billAmount.divide(sumbillAmount,5,BigDecimal.ROUND_HALF_EVEN).multiply(sumclearAmount).subtract(collectAmount);
						subtractVlaue2 = sumclearAmountBwb.subtract(sumUnreceivableamouBwb);
					}
				}
					if (null != voucher && subtractVlaue2.abs().compareTo(new BigDecimal(0)) != 0)
					{
						subtractVlaue2 = subtractVlaue2.add(ajValue);
						VoucherItem supplierVoucherItem = getProfitAndLossVoucherItem(supplierSinglClear, subtractVlaue2, currency,taxCode, "1");
						supplierVoucherItem.setRownumber("3");
						supplierVoucherItem.setVoucher(voucher);
						voucher.getVoucherItem().add(supplierVoucherItem);
	
						VoucherItem profOrLossVoucherItem = getProfitAndLossVoucherItem(supplierSinglClear, subtractVlaue2, currency,taxCode, "2");
						profOrLossVoucherItem.setRownumber("4");
						profOrLossVoucherItem.setVoucher(voucher);
						voucher.getVoucherItem().add(profOrLossVoucherItem);
					}
				
			}
			
			// 判断是否外币,无调整金且没有清帐凭证的情况下。
			if (!"CNY".equalsIgnoreCase(currency)&&(sumajAmount.compareTo(new BigDecimal(0))==0) && null != voucherItemList && voucherItemList.size() == 0)
			{
				BigDecimal subtractVlaue2 = new BigDecimal(0);	
				if(!clearF && !billF){
					//刚好两全清，款的本位币合计 -票的本位币合计
					subtractVlaue2 = sumUnoffsetamountBwb.subtract(sumUnreceivableamouBwb);
				}else{
									
					//票的一边全清
					if(!billF){
//						subtractVlaue2 = collectAmount.divide(sumcollectAmount,5,BigDecimal.ROUND_HALF_EVEN).multiply(sumoffsetAmount);
//						subtractVlaue2 = billAmount.subtract(collectAmount);
						//修改成各个行项目算汇率，再算清账本位币再相加						
						subtractVlaue2 = sumUnoffsetamountBwb.subtract(sumoffsetAmountBwb);
					}
					//款的一边全清
					if(!clearF){
//						subtractVlaue2 = billAmount.divide(sumbillAmount,5,BigDecimal.ROUND_HALF_EVEN).multiply(sumclearAmount).subtract(collectAmount);
						//修改成各个行项目算汇率，再算清账本位币再相加
						subtractVlaue2 = sumclearAmountBwb.subtract(sumUnreceivableamouBwb);
					}
				}	
					if (subtractVlaue2.abs().compareTo(new BigDecimal(0)) != 0)
					{
						// 凭证
						Voucher voucher2 = getVoucher(supplierSinglClear);
						voucher2.setAgkoa(" ");
						voucher2.setAgkon(" ");
						voucher2.setBstat(" ");
						voucher2.setGsber(" ");
						voucher2.setFlag(" ");
						voucher2.setVouchertype("SA");
//						clearVoucher.setImporter(supplierSinglClear.getCreator()); // 输入者
//						clearVoucher.setPreparer(supplierSinglClear.getCreator()); // 预制者
						voucher2.setValut(" ");
						VoucherItem supplierVoucherItem = getProfitAndLossVoucherItem(supplierSinglClear, subtractVlaue2, currency,taxCode, "1");
						
						supplierVoucherItem.setVoucher(voucher2);
						voucherItemList.add(supplierVoucherItem);
	
						VoucherItem profOrLossVoucherItem = getProfitAndLossVoucherItem(supplierSinglClear, subtractVlaue2, currency,taxCode, "2");
						
						profOrLossVoucherItem.setVoucher(voucher2);
						voucherItemList.add(profOrLossVoucherItem);
						voucher2.setVoucherItem(voucherItemList);
						//判断是否已经生成损益凭证，
						Voucher voucher3=null;
						List<Voucher> li=voucherHibernateDao.getVoucherByBusinessId2(supplierSinglClear.getSuppliersclearid()," ");
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
			
			clearVoucher.setVoucherItem(voucherItemList);
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
					if("21".equals(voucheritem2.getCheckcode()) || "31".equals(voucheritem2.getCheckcode())){
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
			//判断是否已经生成清账凭证，
			Voucher clearVoucher2=null;
			List<Voucher> li=voucherHibernateDao.getVoucherByBusinessId2(supplierSinglClear.getSuppliersclearid(),"A");
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

	private BigDecimal getUnoffAmoutRate(SupplierSinglClear supplierSinglClear){
		Set<UnClearSupplieBill> unclearSupplieBills = supplierSinglClear.getUnClearSupplieBill();
		Set<UnClearPayment> unClearPaymentItems = supplierSinglClear.getUnClearPayment();
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
		for(UnClearSupplieBill unclearSupplieBill:unclearSupplieBills){
		
			billAmount = billAmount.add(unclearSupplieBill.getBwbje());
			
			// 发票总金额
			BigDecimal billamount = unclearSupplieBill.getReceivableamount();
			// 发票已经审批完的，发票已清金额
			BigDecimal receivedamount = this.vendorService.getSumClearAmountByVendorTitleID(unclearSupplieBill.getUnclearsbillid().trim(),BusinessState.ALL_SUBMITPASS);
			unclearSupplieBill.setUnpaidamount(billamount.subtract(receivedamount));		
			
			if(unclearSupplieBill.getUnpaidamount().compareTo(unclearSupplieBill.getClearamount()) !=0){
				billF = true;
				
			}			
			sumUnreceivableamou = sumUnreceivableamou.add(unclearSupplieBill.getUnpaidamount());
			
		}
		for(UnClearPayment unClearPayment:unClearPaymentItems){
		
			collectAmount = collectAmount.add(unClearPayment.getBwbje());
			
			// 款总金额
			BigDecimal goodsamount = unClearPayment.getPaymentamount();
			// 付款单已审批完的 ，已清票款
			BigDecimal clearedPaymentAmount = this.paymentItemJdbcDao.getSumClearAmount(unClearPayment.getPaymentitemid().trim(), BusinessState.ALL_SUBMITPASS);
			// 款未抵消金额
			unClearPayment.setUnoffsetamount(goodsamount.subtract(clearedPaymentAmount));
			
			if(unClearPayment.getUnoffsetamount().compareTo(unClearPayment.getNowclearamount()) !=0){
				clearF = true;
				
			}			
			sumUnoffsetamount = sumUnoffsetamount.add(unClearPayment.getUnoffsetamount());
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
	private String checkAllClear(SupplierSinglClear supplierSinglClear){
		Set<UnClearSupplieBill> unclearSupplieBills = supplierSinglClear.getUnClearSupplieBill();
		Set<UnClearPayment> unClearPaymentItems = supplierSinglClear.getUnClearPayment();
		//判断哪一边不全清
		boolean billF = false;
		boolean clearF = false;
		
		//本位币合计
		BigDecimal billAmount = new BigDecimal("0");
		BigDecimal collectAmount = new BigDecimal("0");
		
		
		
		// 重新计算未清金额(未收款、未付款等)
		for(UnClearSupplieBill unclearSupplieBill:unclearSupplieBills){
		
			billAmount = billAmount.add(unclearSupplieBill.getBwbje());
			
			// 发票总金额
			BigDecimal billamount = unclearSupplieBill.getReceivableamount();
			// 发票已经审批完的，发票已清金额
			BigDecimal receivedamount = this.vendorService.getSumClearAmountByVendorTitleID(unclearSupplieBill.getUnclearsbillid().trim(),BusinessState.ALL_SUBMITPASS);
			unclearSupplieBill.setUnpaidamount(billamount.subtract(receivedamount));		
			
			if(unclearSupplieBill.getUnpaidamount().compareTo(unclearSupplieBill.getClearamount()) !=0){
				billF = true;
				
			}			
			
			
		}
		for(UnClearPayment unClearPayment:unClearPaymentItems){
		
			collectAmount = collectAmount.add(unClearPayment.getBwbje());
			
			// 款总金额
			BigDecimal goodsamount = unClearPayment.getPaymentamount();
			// 付款单已审批完的 ，已清票款
			BigDecimal clearedPaymentAmount = this.paymentItemJdbcDao.getSumClearAmount(unClearPayment.getPaymentitemid().trim(), BusinessState.ALL_SUBMITPASS);
			// 款未抵消金额
			unClearPayment.setUnoffsetamount(goodsamount.subtract(clearedPaymentAmount));
			
			if(unClearPayment.getUnoffsetamount().compareTo(unClearPayment.getNowclearamount()) !=0){
				clearF = true;
				
			}			
			
		}
		if(billF){
			return "billClear";
		}else{
			return "paymentClear";
		}		
	}
	
	/**
	 * 取得taxCode
	 * @param customSingleClear
	 * @return
	 */
	public String getTaxCode(SupplierSinglClear supplierSinglClear){
		
		//合同号列表
		List<String> contractNoList = new ArrayList<String>();
		String projectNo =" ";
		String contractNo = " ";
		// 重新计算未清金额(未收款、未付款等)
		Set<UnClearSupplieBill> unclearSupplieBills = supplierSinglClear.getUnClearSupplieBill();
		Set<UnClearPayment> unClearPaymentItems = supplierSinglClear.getUnClearPayment();
		for(UnClearSupplieBill unclearSupplieBill:unclearSupplieBills){
			
			
			if(unclearSupplieBill.getUnpaidamount().compareTo(unclearSupplieBill.getClearamount()) !=0){
				projectNo = unclearSupplieBill.getProject_no();
			}
			contractNo = unclearSupplieBill.getContract_no();
			contractNoList.add(contractNo);			
		}
		
		for(UnClearPayment unClearPayment:unClearPaymentItems){
			
			if(unClearPayment.getUnoffsetamount().compareTo(unClearPayment.getNowclearamount()) !=0){
				projectNo = unClearPayment.getProject_no();
			}
			contractNo = unClearPayment.getContract_no();
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
		if(taxCodeFlag && !StringUtils.isNullBlank(projectNo)){
			taxCode = projectNo;
		}else{
			taxCode = contractNo;
		}
		return taxCode;
	}
	
	/**
	 * 处理本次可以全清帐
	 * 
	 * @param supplierSinglClear
	 * @param currency
	 * @param voucher
	 * @param voucherItemList
	 * @param oldVoucher
	 */
	private void getClearVoucherItem(SupplierSinglClear supplierSinglClear, Voucher clearVoucher, Set<VoucherItem> voucherItemList, String currency,BigDecimal ajValue,String taxCode)
	{
		// 得到票的本位币之和
		BigDecimal billValue = new BigDecimal("0");
		// 得到分配项的本位币之和
		BigDecimal itemValue = new BigDecimal("0");
		// 分配项之和和票之和的差
		BigDecimal subtractVlaue = new BigDecimal("0");

		BigDecimal billAmount = new BigDecimal("0");
		BigDecimal paymentAmount = new BigDecimal("0");
		
		Set<UnClearSupplieBill> unClearSupplieBillItems = supplierSinglClear.getUnClearSupplieBill();
		Set<UnClearPayment> unClearPaymentItems = supplierSinglClear.getUnClearPayment();
		for (UnClearSupplieBill unClearSupplieBill : unClearSupplieBillItems)
		{
			billAmount = billAmount.add(unClearSupplieBill.getBwbje());
			String invoice = unClearSupplieBill.getInvoice();
			VoucherItem voucherItem = null;
			 
			if(!StringUtils.isNullBlank(invoice)){
				ClearVoucherItemStruct clearVoucherItemStruct = vendorTitleJdbcDao.getCurrectBillInfo(invoice);
				 voucherItem = getClearVoucherItem(clearVoucherItemStruct);
				 if(null ==clearVoucherItemStruct.getBwbje())clearVoucherItemStruct.setBwbje(new BigDecimal("0"));
				 billValue = billValue.add(clearVoucherItemStruct.getBwbje());
			}else{
				ClearVoucherItemStruct clearVoucherItemStruct = new ClearVoucherItemStruct();				 
				 VendorTitle vendorTitle = this.vendorTitleJdbcDao.getByVendortitleid(unClearSupplieBill.getVendortitleid());				 
				 clearVoucherItemStruct.setRowno(vendorTitle.getBuzei());
				 clearVoucherItemStruct.setVoucherno(unClearSupplieBill.getVoucherno());
				 clearVoucherItemStruct.setYear(vendorTitle.getGjahr());
				 voucherItem = getClearVoucherItem(clearVoucherItemStruct);
				 billValue = billValue.add(vendorTitle.getBwbje());
			}
			
			voucherItem.setVoucher(clearVoucher);
			voucherItemList.add(voucherItem);
		}

		for (UnClearPayment unClearPayment : unClearPaymentItems)
		{
			paymentAmount = paymentAmount.add(unClearPayment.getBwbje());
//			String paymentid = unClearPayment.getPaymentid();
//			String paymentitemid=unClearPayment.getPaymentitemid();
			VoucherItem voucherItem = null;
//			if(!StringUtils.isNullBlank(paymentitemid)){
//				Voucher oldVoucher = this.voucherService.getVoucher(paymentid);
//				VoucherItem voucheritem2 = this.voucherItemJdbcDao.getVoucherItem(paymentitemid, "1");
//				Set<VoucherItem> oldVoucherItem = oldVoucher.getVoucherItem();
//				Iterator<VoucherItem> oldVoucherItemIt = oldVoucherItem.iterator();
//				while (oldVoucherItemIt.hasNext())
//				{
//					VoucherItem oldItem = oldVoucherItemIt.next();
//
//					if (oldItem.getVoucher().getBusinessid().equals(unClearPayment.getPaymentid()) && oldItem.getBusinessitemid().equals(unClearPayment.getPaymentitemid()))
//					{
//						ClearVoucherItemStruct clearVoucherItemStruct = new ClearVoucherItemStruct();
//						clearVoucherItemStruct.setRowno(oldItem.getRownumber());
//						clearVoucherItemStruct.setVoucherno(unClearPayment.getVoucherno());
//						clearVoucherItemStruct.setYear(oldItem.getVoucher().getFiyear());
//						voucherItem = getClearVoucherItem(clearVoucherItemStruct);
//						itemValue = itemValue.add(oldItem.getAmount2());
//
//						
//					}
//				}
//				if(null !=voucheritem2){
//					ClearVoucherItemStruct clearVoucherItemStruct = new ClearVoucherItemStruct();
//					clearVoucherItemStruct.setRowno(voucheritem2.getRownumber());
//					clearVoucherItemStruct.setVoucherno(unClearPayment.getVoucherno());
//					clearVoucherItemStruct.setYear(voucheritem2.getVoucher().getFiyear());
//					voucherItem = getClearVoucherItem(clearVoucherItemStruct);
//					itemValue = itemValue.add(voucheritem2.getAmount2());
//					voucherItem.setVoucher(clearVoucher);
//					voucherItemList.add(voucherItem);
//				}
//			}else{
				ClearVoucherItemStruct clearVoucherItemStruct = new ClearVoucherItemStruct();				 
				 VendorTitle vendorTitle = this.vendorTitleJdbcDao.getByVendortitleid(unClearPayment.getVendortitleid());				 
				 clearVoucherItemStruct.setRowno(vendorTitle.getBuzei());
				 clearVoucherItemStruct.setVoucherno(unClearPayment.getVoucherno());
				 clearVoucherItemStruct.setYear(vendorTitle.getGjahr());
				 voucherItem = getClearVoucherItem(clearVoucherItemStruct);
				 itemValue = itemValue.add(vendorTitle.getBwbje());
				 voucherItem.setVoucher(clearVoucher);
				 voucherItemList.add(voucherItem);
//			}
			
		}

		// 判断是否外币
		if (!"CNY".equalsIgnoreCase(currency)&&(billAmount.compareTo(paymentAmount)!=0))
		{
			subtractVlaue = itemValue.subtract(billValue);
			subtractVlaue = subtractVlaue.add(ajValue);
			if (subtractVlaue.abs().compareTo(new BigDecimal(0)) != 0)
			{
				VoucherItem supplierVoucherItem = getProfitAndLossVoucherItem(supplierSinglClear, subtractVlaue, currency,taxCode, "1");
				supplierVoucherItem.setVoucher(clearVoucher);
				voucherItemList.add(supplierVoucherItem);

				VoucherItem profOrLossVoucherItem = getProfitAndLossVoucherItem(supplierSinglClear, subtractVlaue, currency,taxCode, "2");
				profOrLossVoucherItem.setVoucher(clearVoucher);
				voucherItemList.add(profOrLossVoucherItem);
			}
		}

		clearVoucher.setVoucherItem(voucherItemList);

	}

	/**
	 * 得到有汇损益的行项目信息
	 * 
	 * @param supplierSinglClear
	 * @param subtractVlaue
	 * @param currency
	 * @param strType
	 * @return
	 */
	public VoucherItem getProfitAndLossVoucherItem(SupplierSinglClear supplierSinglClear, BigDecimal subtractVlaue, String currency,String taxCode, String strType)
	{
		VoucherItem voucherItem = new VoucherItem();
		String bukrs = supplierSinglClear.getCompanyno();
		String subject = this.customerRefundItemJdbcDao.getSubjectBySuppliers(supplierSinglClear.getSupplier(),bukrs);
		String subjectname = this.customerRefundItemJdbcDao.getSubjectNameById(subject,bukrs);
		voucherItem.setControlaccount(subject);
		voucherItem.setCaremark(subjectname);
		voucherItem.setTaxcode(taxCode);
		if (strType.equals("1"))
		{
			voucherItem.setRownumber("1");
			// 科目
			voucherItem.setSubject(supplierSinglClear.getSupplier());
			// 科目说明
			String Subjectdescribe = this.voucherService.getSupplierDescByCustomerId(supplierSinglClear.getSupplier(), bukrs);
			voucherItem.setSubjectdescribe(Subjectdescribe);

			if (subtractVlaue.signum() == 1)
			{
				// 记帐码
				voucherItem.setCheckcode("38");
				voucherItem.setDebitcredit("H");
			}
			else
			{
				// 记帐码
				voucherItem.setCheckcode("25");
				voucherItem.setDebitcredit("S");
			}
		}

		if (strType.equals("2"))
		{
			voucherItem.setRownumber("2");
			if (subtractVlaue.signum() == -1)
			{
				// 记帐码
				voucherItem.setCheckcode("50");
				voucherItem.setDebitcredit("H");
				// 科目
				voucherItem.setSubject("6603050402");
				// 科目说明
				voucherItem.setSubjectdescribe(this.customerRefundItemJdbcDao.getSubjectNameById("6603050402",bukrs));
				voucherItem.setControlaccount(voucherItem.getSubject());
				voucherItem.setCaremark(voucherItem.getSubjectdescribe());
			}
			else
			{
				// 记帐码
				voucherItem.setCheckcode("40");
				voucherItem.setDebitcredit("S");
				// 科目
				voucherItem.setSubject("6603050401");
				// 科目说明
				voucherItem.setSubjectdescribe(this.customerRefundItemJdbcDao.getSubjectNameById("6603050401",bukrs));
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
		voucherItem.setDepid(supplierSinglClear.getDepid());
		// 文本
		voucherItem.setText(supplierSinglClear.getText());

		return voucherItem;
	}

	/**
	 * 处理供应商下清帐凭证行项目.
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
	private void getSupplierClearVoucherItem(SupplierSinglClear supplierSinglClear, Voucher clearVoucher, Set<VoucherItem> voucherItemList, String contractNos, String projectNos, List<String> clearedContractNo, List<String> clearedProjectNo, String currency, BigDecimal billValue, BigDecimal itemValue,BigDecimal ajValue, String bukrs)
	{
		String supplier = supplierSinglClear.getSupplier();
		// 分配项之和和票之和的差
		BigDecimal subtractVlaue = new BigDecimal("0");
		Set<UnClearSupplieBill> unClearSupplieBillItems = supplierSinglClear.getUnClearSupplieBill();
		Set<UnClearPayment> unClearPaymentItems = supplierSinglClear.getUnClearPayment();
		// 得到之前合同的票信息
//		List<ClearVoucherItemStruct> clearVoucherItemStructList = this.vendorTitleJdbcDao.getSupplierBillInfo(supplier, contractNos, projectNos, bukrs);
//		for (ClearVoucherItemStruct clearVoucherItemStruct : clearVoucherItemStructList)
//		{
//			VoucherItem voucherItem = getClearVoucherItem(clearVoucherItemStruct);
//			voucherItem.setVoucher(clearVoucher);
//			billValue = billValue.add(clearVoucherItemStruct.getBwbje());
//			voucherItemList.add(voucherItem);
//		}

		// 得到本次票的行项目信息
		Iterator<UnClearSupplieBill> unClearSupplieBillItemIt = unClearSupplieBillItems.iterator();
		while (unClearSupplieBillItemIt.hasNext())
		{
			UnClearSupplieBill unClearSupplieBill = unClearSupplieBillItemIt.next();
			if (clearedContractNo.contains(unClearSupplieBill.getContract_no()))
			{
				log.debug("合同号:" + unClearSupplieBill.getContract_no() + "已经存在已清合同列表," + contractNos);
			}
			if (clearedProjectNo.contains(unClearSupplieBill.getProject_no()))
			{
				log.debug("合同号:" + unClearSupplieBill.getProject_no() + "已经存在已清合同列表," + projectNos);
			}

			if (!clearedContractNo.contains(unClearSupplieBill.getContract_no()) && !clearedProjectNo.contains(unClearSupplieBill.getProject_no()))
			{
//				ClearVoucherItemStruct clearVoucherItemStruct = this.vendorTitleJdbcDao.getCurrectBillInfo(unClearSupplieBill.getInvoice());
//				VoucherItem voucherItem = getClearVoucherItem(clearVoucherItemStruct);
//				voucherItem.setVoucher(clearVoucher);
//				billValue = billValue.add(clearVoucherItemStruct.getBwbje());
//				voucherItemList.add(voucherItem);
				
				ClearVoucherItemStruct clearVoucherItemStruct = new ClearVoucherItemStruct();
				VendorTitle vendorTitle = this.vendorTitleJdbcDao.getByVendortitleid(unClearSupplieBill.getVendortitleid());				 
				clearVoucherItemStruct.setRowno(vendorTitle.getBuzei());
				clearVoucherItemStruct.setVoucherno(unClearSupplieBill.getVoucherno());
				clearVoucherItemStruct.setYear(vendorTitle.getGjahr());
				VoucherItem voucherItem = getClearVoucherItem(clearVoucherItemStruct);						
				billValue = billValue.add(vendorTitle.getBwbje());	
				voucherItem.setVoucher(clearVoucher);
				voucherItemList.add(voucherItem);
			}
		}
		//得到之前汇损，或调整金的voucheritem
//		List<VoucherItem> voucherItems = voucherItemJdbcDao.getVoucherItems(supplierSinglClear.getSupplier(), "");
//		for(VoucherItem voucherItem2 : voucherItems){
//			String rowNumber = voucherItem2.getRownumber();
//			if(rowNumber.length()==2)rowNumber="0" + rowNumber;			
//			if(rowNumber.length()==1)rowNumber="00" + rowNumber;
//			VendorTitle vendorTitle = vendorTitleJdbcDao.getByVoucherNo(voucherItem2.getVoucher().getCompanycode(), voucherItem2.getVoucher().getVoucherno(), voucherItem2.getVoucher().getFiyear(), rowNumber);
//			if(null !=vendorTitle && "1".equals(vendorTitle.getIscleared())){
//				ClearVoucherItemStruct clearVoucherItemStruct = new ClearVoucherItemStruct();
//				clearVoucherItemStruct.setBwbje(voucherItem2.getAmount2());
//				clearVoucherItemStruct.setRowno(voucherItem2.getRownumber());
//				clearVoucherItemStruct.setSubject(voucherItem2.getSubject());
//				clearVoucherItemStruct.setVoucherno(voucherItem2.getVoucher().getVoucherno());
//				clearVoucherItemStruct.setYear(voucherItem2.getVoucher().getFiyear());
//				if("31".equals(voucherItem2.getCheckcode()) ||"19".equals(voucherItem2.getCheckcode())){
//					billValue = billValue.add(clearVoucherItemStruct.getBwbje());
//				}else if("21".equals(voucherItem2.getCheckcode()) || "09".equals(voucherItem2.getCheckcode())){
//					itemValue = itemValue.add(clearVoucherItemStruct.getBwbje());
//				}
//				VoucherItem voucherItem = getClearVoucherItem(clearVoucherItemStruct);				
//				voucherItem.setVoucher(clearVoucher);
//				voucherItemList.add(voucherItem);
//			}
//		}
//		
//		//取得SAP手工做账的被部分清的汇损凭证
//		List<ClearVoucherItemStruct> clearVoucherItemStructList2 = this.vendorTitleJdbcDao.getSupplierVoucherInfo(supplier, contractNos, projectNos, bukrs);
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
//		// 得到之前的款分配信息
//		List<ClearVoucherItemStruct> clearVoucherItemStructs2 = this.vendorTitleJdbcDao.getSupplierPayItemInfo(supplier, contractNos, projectNos, bukrs);
//		for (ClearVoucherItemStruct clearVoucherItemStruct : clearVoucherItemStructs2)
//		{
//			VoucherItem voucherItem = getClearVoucherItem(clearVoucherItemStruct);
//			voucherItem.setVoucher(clearVoucher);
//			itemValue = itemValue.add(clearVoucherItemStruct.getBwbje());
//			voucherItemList.add(voucherItem);
//		}
//		//退款的行项目信息
//		List<ClearVoucherItemStruct> clearVoucherItemStructList3 = this.vendorTitleJdbcDao.getRefundItemBySupplierNo(supplierSinglClear.getSupplier(), BusinessState.ALL_BILLCLEAR_PAIDUP, projectNos, contractNos,bukrs);
//		for (ClearVoucherItemStruct clearVoucherItemStruct : clearVoucherItemStructList3)
//		{
//			VoucherItem voucherItem = getClearVoucherItem(clearVoucherItemStruct);
//			billValue = billValue.add(clearVoucherItemStruct.getBwbje());
//			voucherItem.setVoucher(clearVoucher);
//			voucherItemList.add(voucherItem);
//		}
		
		List<ClearVoucherItemStruct> clearVoucherItemStructList2 = this.vendorTitleJdbcDao.getSupplierVoucherInfo2(supplier, contractNos, projectNos, bukrs,supplierSinglClear.getDepid(),supplierSinglClear.getSubject(),supplierSinglClear.getCurrencytext());
		for (ClearVoucherItemStruct clearVoucherItemStruct : clearVoucherItemStructList2)
		{
			VoucherItem voucherItem = getClearVoucherItem(clearVoucherItemStruct);
			voucherItem.setVoucher(clearVoucher);			
			voucherItemList.add(voucherItem);
		}
		itemValue = itemValue.add(vendorTitleJdbcDao.getSumBwbje(supplier, contractNos, projectNos, bukrs, "S",supplierSinglClear.getCurrencytext(),supplierSinglClear.getDepid(),supplierSinglClear.getSubject()));
		billValue = billValue.add(vendorTitleJdbcDao.getSumBwbje(supplier, contractNos, projectNos, bukrs, "H",supplierSinglClear.getCurrencytext(),supplierSinglClear.getDepid(),supplierSinglClear.getSubject()));
		// 得到本次分配项的行项目信息
		Iterator<UnClearPayment> unClearPaymentItemsIt = unClearPaymentItems.iterator();
		while (unClearPaymentItemsIt.hasNext())
		{
			UnClearPayment unClearPayment = unClearPaymentItemsIt.next();
			if (clearedContractNo.contains(unClearPayment.getContract_no()))
			{
				log.debug("合同号:" + unClearPayment.getContract_no() + "已经存在已清合同列表," + contractNos);
			}
			if (clearedProjectNo.contains(unClearPayment.getProject_no()))
			{
				log.debug("合同号:" + unClearPayment.getProject_no() + "已经存在已清合同列表," + projectNos);
			}

			if (!clearedContractNo.contains(unClearPayment.getContract_no()) && !clearedProjectNo.contains(unClearPayment.getProject_no()))
			{
//				Voucher oldVoucher = this.voucherService.getVoucherByBusinessId(unClearPayment.getPaymentid(), "1");
//				Set<VoucherItem> oldVoucherItem = oldVoucher.getVoucherItem();
//				Iterator<VoucherItem> payVoucherItemit = oldVoucherItem.iterator();
//				while (payVoucherItemit.hasNext())
//				{
//					VoucherItem oldItem = payVoucherItemit.next();
//					if (oldItem.getVoucher().getBusinessid().equals(unClearPayment.getPaymentid()) && oldItem.getBusinessitemid().equals(unClearPayment.getPaymentitemid()))
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
				VendorTitle vendortitle = vendorTitleHibernateDao.get(unClearPayment.getVendortitleid());
				ClearVoucherItemStruct clearVoucherItemStruct = new ClearVoucherItemStruct();
				clearVoucherItemStruct.setRowno(vendortitle.getBuzei());
				clearVoucherItemStruct.setVoucherno(vendortitle.getBelnr());
				clearVoucherItemStruct.setYear(vendortitle.getGjahr());
				VoucherItem voucherItem = getClearVoucherItem(clearVoucherItemStruct);
				voucherItem.setVoucher(clearVoucher);
				itemValue = itemValue.add(vendortitle.getBwbje());
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
				VoucherItem supplierVoucherItem = getProfitAndLossVoucherItem(supplierSinglClear, subtractVlaue, currency,"", "1");
				supplierVoucherItem.setVoucher(clearVoucher);
				voucherItemList.add(supplierVoucherItem);

				VoucherItem profOrLossVoucherItem = getProfitAndLossVoucherItem(supplierSinglClear, subtractVlaue, currency,"", "2");
				profOrLossVoucherItem.setVoucher(clearVoucher);
				voucherItemList.add(profOrLossVoucherItem);
			}
		}
		
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
	private String getProjectClearVoucherItem(SupplierSinglClear supplierSinglClear, Voucher clearVoucher, Set<VoucherItem> voucherItemList, String contractNos, List<String> clearedContractNo, List<String> clearedProjectNo, String currency, BigDecimal billValue, BigDecimal itemValue,BigDecimal ajValue)
	{
		String projectNos = "";
		// 分配项之和和票之和的差
		BigDecimal subtractVlaue = new BigDecimal("0");
		Set<UnClearSupplieBill> unClearSupplieBillItems = supplierSinglClear.getUnClearSupplieBill();
		Set<UnClearPayment> unClearPaymentItems = supplierSinglClear.getUnClearPayment();
		// 循环所有立項号。
		for (String projectNo : clearedProjectNo)
		{
			projectNos = projectNos + "'" + projectNo + "',";
			// 得到之前合同的票信息
			List<ClearVoucherItemStruct> clearVoucherItemStructs = this.vendorTitleJdbcDao.getProjectBillInfo(supplierSinglClear.getSupplier(), projectNo, contractNos);
			for (ClearVoucherItemStruct clearVoucherItemStruct : clearVoucherItemStructs)
			{
				VoucherItem voucherItem = getClearVoucherItem(clearVoucherItemStruct);
				voucherItem.setVoucher(clearVoucher);
				billValue = billValue.add(clearVoucherItemStruct.getBwbje());
				voucherItemList.add(voucherItem);
			}

			// 得到本次票的行项目信息
			Iterator<UnClearSupplieBill> unClearSupplieBillItemIt = unClearSupplieBillItems.iterator();
			while (unClearSupplieBillItemIt.hasNext())
			{
				UnClearSupplieBill unClearSupplieBill = unClearSupplieBillItemIt.next();
				if (clearedContractNo.contains(unClearSupplieBill.getContract_no()))
				{
					log.debug("合同号:" + unClearSupplieBill.getContract_no() + "已经存在已清合同列表," + contractNos);
				}
				if ((projectNo.equals(unClearSupplieBill.getProject_no()) && !clearedContractNo.contains(unClearSupplieBill.getContract_no())) || unClearSupplieBill.getReceivableamount().compareTo(new BigDecimal("0")) ==0)
				{
//					ClearVoucherItemStruct clearVoucherItemStruct = this.vendorTitleJdbcDao.getCurrectBillInfo(unClearSupplieBill.getInvoice());
//					VoucherItem voucherItem = getClearVoucherItem(clearVoucherItemStruct);
//					voucherItem.setVoucher(clearVoucher);
//					billValue = billValue.add(clearVoucherItemStruct.getBwbje());
//					voucherItemList.add(voucherItem);
					
					ClearVoucherItemStruct clearVoucherItemStruct = new ClearVoucherItemStruct();
					VendorTitle vendorTitle = this.vendorTitleJdbcDao.getByVendortitleid(unClearSupplieBill.getVendortitleid());				 
					clearVoucherItemStruct.setRowno(vendorTitle.getBuzei());
					clearVoucherItemStruct.setVoucherno(unClearSupplieBill.getVoucherno());
					clearVoucherItemStruct.setYear(vendorTitle.getGjahr());
					VoucherItem voucherItem = getClearVoucherItem(clearVoucherItemStruct);						
					billValue = billValue.add(vendorTitle.getBwbje());	
					voucherItem.setVoucher(clearVoucher);
					voucherItemList.add(voucherItem);
				}
			}
			//得到之前汇损，或调整金的voucheritem
			List<VoucherItem> voucherItems = voucherItemJdbcDao.getVoucherItems(supplierSinglClear.getSupplier(), projectNo);
			for(VoucherItem voucherItem2 : voucherItems){
				String rowNumber = voucherItem2.getRownumber();
				if(rowNumber.length()==2)rowNumber="0" + rowNumber;			
				if(rowNumber.length()==1)rowNumber="00" + rowNumber;
				VendorTitle vendorTitle = vendorTitleJdbcDao.getByVoucherNo(voucherItem2.getVoucher().getCompanycode(), voucherItem2.getVoucher().getVoucherno(), voucherItem2.getVoucher().getFiyear(), rowNumber);
				if(null !=vendorTitle && "1".equals(vendorTitle.getIscleared())){
					ClearVoucherItemStruct clearVoucherItemStruct = new ClearVoucherItemStruct();
					clearVoucherItemStruct.setBwbje(voucherItem2.getAmount2());
					clearVoucherItemStruct.setRowno(voucherItem2.getRownumber());
					clearVoucherItemStruct.setSubject(voucherItem2.getSubject());
					clearVoucherItemStruct.setVoucherno(voucherItem2.getVoucher().getVoucherno());
					clearVoucherItemStruct.setYear(voucherItem2.getVoucher().getFiyear());
					if("31".equals(voucherItem2.getCheckcode()) ||"19".equals(voucherItem2.getCheckcode())){
						billValue = billValue.add(clearVoucherItemStruct.getBwbje());
					}else if("21".equals(voucherItem2.getCheckcode()) || "09".equals(voucherItem2.getCheckcode())){
						itemValue = itemValue.add(clearVoucherItemStruct.getBwbje());
					}
					VoucherItem voucherItem = getClearVoucherItem(clearVoucherItemStruct);				
					voucherItem.setVoucher(clearVoucher);
					voucherItemList.add(voucherItem);
				}
			}
			// 得到之前的款分配信息
			List<ClearVoucherItemStruct> clearVoucherItemStructs2 = this.vendorTitleJdbcDao.getProjectPayItemInfo(supplierSinglClear.getSupplier(),projectNo, contractNos);
			for (ClearVoucherItemStruct clearVoucherItemStruct : clearVoucherItemStructs2)
			{
				VoucherItem voucherItem = getClearVoucherItem(clearVoucherItemStruct);
				voucherItem.setVoucher(clearVoucher);
				itemValue = itemValue.add(clearVoucherItemStruct.getBwbje());
				voucherItemList.add(voucherItem);
			}
			//退款的行项目信息
			List<ClearVoucherItemStruct> clearVoucherItemStructList3 = this.vendorTitleJdbcDao.getRefundItemBySupplierNoAndProjectNo(supplierSinglClear.getSupplier(), BusinessState.ALL_BILLCLEAR_PAIDUP, projectNo, contractNos);
			for (ClearVoucherItemStruct clearVoucherItemStruct : clearVoucherItemStructList3)
			{
				VoucherItem voucherItem = getClearVoucherItem(clearVoucherItemStruct);
				billValue = billValue.add(clearVoucherItemStruct.getBwbje());
				voucherItem.setVoucher(clearVoucher);
				voucherItemList.add(voucherItem);
			}
			// 得到本次分配项的行项目信息
			Iterator<UnClearPayment> unClearPaymentItemsIt = unClearPaymentItems.iterator();
			while (unClearPaymentItemsIt.hasNext())
			{
				UnClearPayment unClearPayment = unClearPaymentItemsIt.next();
				if (clearedContractNo.contains(unClearPayment.getContract_no()))
				{
					log.debug("合同号:" + unClearPayment.getContract_no() + "已经存在已清合同列表," + contractNos);
				}
				if ((projectNo.equals(unClearPayment.getProject_no()) && !clearedContractNo.contains(unClearPayment.getContract_no())) || unClearPayment.getPaymentamount().compareTo(new BigDecimal("0")) ==0)
				{
//					Voucher oldVoucher = this.voucherService.getVoucherByBusinessId(unClearPayment.getPaymentid(), "1");
//					Set<VoucherItem> oldVoucherItem = oldVoucher.getVoucherItem();
//					Iterator<VoucherItem> payVoucherItemit = oldVoucherItem.iterator();
//					while (payVoucherItemit.hasNext())
//					{
//						VoucherItem oldItem = payVoucherItemit.next();
//						if (oldItem.getVoucher().getBusinessid().equals(unClearPayment.getPaymentid()) && oldItem.getBusinessitemid().equals(unClearPayment.getPaymentitemid()))
//						{
//							ClearVoucherItemStruct clearVoucherItemStruct = new ClearVoucherItemStruct();
//							clearVoucherItemStruct.setRowno(oldItem.getRownumber());
//							clearVoucherItemStruct.setVoucherno(unClearPayment.getVoucherno());
//							clearVoucherItemStruct.setYear(oldItem.getVoucher().getFiyear());
//							VoucherItem voucherItem = getClearVoucherItem(clearVoucherItemStruct);
//							itemValue = itemValue.add(oldItem.getAmount2());
//							voucherItem.setVoucher(clearVoucher);
//							voucherItemList.add(voucherItem);
//						}
//					}
					VendorTitle vendortitle = vendorTitleHibernateDao.get(unClearPayment.getVendortitleid());
					ClearVoucherItemStruct clearVoucherItemStruct = new ClearVoucherItemStruct();
					clearVoucherItemStruct.setRowno(vendortitle.getBuzei());
					clearVoucherItemStruct.setVoucherno(vendortitle.getBelnr());
					clearVoucherItemStruct.setYear(vendortitle.getGjahr());
					VoucherItem voucherItem = getClearVoucherItem(clearVoucherItemStruct);
					voucherItem.setVoucher(clearVoucher);
					itemValue = itemValue.add(vendortitle.getBwbje());
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
					VoucherItem supplierVoucherItem = getProfitAndLossVoucherItem(supplierSinglClear, subtractVlaue, currency,projectNo, "1");
					supplierVoucherItem.setVoucher(clearVoucher);
					voucherItemList.add(supplierVoucherItem);

					VoucherItem profOrLossVoucherItem = getProfitAndLossVoucherItem(supplierSinglClear, subtractVlaue, currency,projectNo, "2");
					profOrLossVoucherItem.setVoucher(clearVoucher);
					voucherItemList.add(profOrLossVoucherItem);
				}
			}

		}
		return projectNos;
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
	private String getContractClearVoucherItem(SupplierSinglClear supplierSinglClear, List<String> clearedContractNo, Voucher clearVoucher, Set<VoucherItem> voucherItemList, String currency, BigDecimal billValue, BigDecimal itemValue,BigDecimal ajValue)
	{
		String contractNos = "";
		// 分配项之和和票之和的差
		BigDecimal subtractVlaue = new BigDecimal("0");
		Set<UnClearSupplieBill> unClearSupplieBillItems = supplierSinglClear.getUnClearSupplieBill();
		Set<UnClearPayment> unClearPaymentItems = supplierSinglClear.getUnClearPayment();
		// 循环所有合同号。
		for (String conntractNo : clearedContractNo)
		{
			contractNos = contractNos + "'" + conntractNo + "',";
			// 得到之前合同的票信息
			List<ClearVoucherItemStruct> clearVoucherItemStructList = this.vendorTitleJdbcDao.getContractBillInfo(supplierSinglClear.getSupplier(),conntractNo);
			for (ClearVoucherItemStruct clearVoucherItemStruct : clearVoucherItemStructList)
			{
				VoucherItem voucherItem = getClearVoucherItem(clearVoucherItemStruct);
				billValue = billValue.add(clearVoucherItemStruct.getBwbje());
				voucherItem.setVoucher(clearVoucher);
				voucherItemList.add(voucherItem);
			}

			// 得到本次票的行项目信息
			Iterator<UnClearSupplieBill> unClearSupplieBillItemIt = unClearSupplieBillItems.iterator();
			while (unClearSupplieBillItemIt.hasNext())
			{
				UnClearSupplieBill unClearSupplieBill = unClearSupplieBillItemIt.next();
				if (conntractNo.equals(unClearSupplieBill.getContract_no()) || unClearSupplieBill.getReceivableamount().compareTo(new BigDecimal("0")) ==0)
				{
//					ClearVoucherItemStruct clearVoucherItemStruct = this.vendorTitleJdbcDao.getCurrectBillInfo(unClearSupplieBill.getInvoice());
//					VoucherItem voucherItem = getClearVoucherItem(clearVoucherItemStruct);
//					billValue = billValue.add(clearVoucherItemStruct.getBwbje());
//					voucherItem.setVoucher(clearVoucher);
//					voucherItemList.add(voucherItem);
					
					ClearVoucherItemStruct clearVoucherItemStruct = new ClearVoucherItemStruct();
					VendorTitle vendorTitle = this.vendorTitleJdbcDao.getByVendortitleid(unClearSupplieBill.getVendortitleid());				 
					clearVoucherItemStruct.setRowno(vendorTitle.getBuzei());
					clearVoucherItemStruct.setVoucherno(unClearSupplieBill.getVoucherno());
					clearVoucherItemStruct.setYear(vendorTitle.getGjahr());
					VoucherItem voucherItem = getClearVoucherItem(clearVoucherItemStruct);						
					billValue = billValue.add(vendorTitle.getBwbje());	
					voucherItem.setVoucher(clearVoucher);
					voucherItemList.add(voucherItem);
				}
			}
			
			//得到之前汇损，或调整金的voucheritem
			List<VoucherItem> voucherItems = voucherItemJdbcDao.getVoucherItems(supplierSinglClear.getSupplier(), conntractNo);
			for(VoucherItem voucherItem2 : voucherItems){
				String rowNumber = voucherItem2.getRownumber();
				if(rowNumber.length()==2)rowNumber="0" + rowNumber;			
				if(rowNumber.length()==1)rowNumber="00" + rowNumber;
				VendorTitle vendorTitle = vendorTitleJdbcDao.getByVoucherNo(voucherItem2.getVoucher().getCompanycode(), voucherItem2.getVoucher().getVoucherno(), voucherItem2.getVoucher().getFiyear(), rowNumber);
				if(null !=vendorTitle && "1".equals(vendorTitle.getIscleared())){
					ClearVoucherItemStruct clearVoucherItemStruct = new ClearVoucherItemStruct();
					clearVoucherItemStruct.setBwbje(voucherItem2.getAmount2());
					clearVoucherItemStruct.setRowno(voucherItem2.getRownumber());
					clearVoucherItemStruct.setSubject(voucherItem2.getSubject());
					clearVoucherItemStruct.setVoucherno(voucherItem2.getVoucher().getVoucherno());
					clearVoucherItemStruct.setYear(voucherItem2.getVoucher().getFiyear());
					if("31".equals(voucherItem2.getCheckcode()) ||"19".equals(voucherItem2.getCheckcode())){
						billValue = billValue.add(clearVoucherItemStruct.getBwbje());
					}else if("21".equals(voucherItem2.getCheckcode()) || "09".equals(voucherItem2.getCheckcode())){
						itemValue = itemValue.add(clearVoucherItemStruct.getBwbje());
					}
					VoucherItem voucherItem = getClearVoucherItem(clearVoucherItemStruct);				
					voucherItem.setVoucher(clearVoucher);
					voucherItemList.add(voucherItem);
				}
			}
			
			// 得到之前的款分配信息
			List<ClearVoucherItemStruct> clearVoucherItemStructList2 = this.vendorTitleJdbcDao.getContractPayItemInfo2(supplierSinglClear.getSupplier(),conntractNo);
			for (ClearVoucherItemStruct clearVoucherItemStruct : clearVoucherItemStructList2)
			{
				VoucherItem voucherItem = getClearVoucherItem(clearVoucherItemStruct);
				itemValue = itemValue.add(clearVoucherItemStruct.getBwbje());
				voucherItem.setVoucher(clearVoucher);
				voucherItemList.add(voucherItem);
			}
			//退款的行项目信息
			List<ClearVoucherItemStruct> clearVoucherItemStructList3 = this.vendorTitleJdbcDao.getRefundItemBySupplierNoAndContractNo(supplierSinglClear.getSupplier(), BusinessState.ALL_BILLCLEAR_PAIDUP, conntractNo);
			for (ClearVoucherItemStruct clearVoucherItemStruct : clearVoucherItemStructList3)
			{
				VoucherItem voucherItem = getClearVoucherItem(clearVoucherItemStruct);
				billValue = billValue.add(clearVoucherItemStruct.getBwbje());
				voucherItem.setVoucher(clearVoucher);
				voucherItemList.add(voucherItem);
			}
			// 得到本次分配项的行项目信息
			Iterator<UnClearPayment> unClearPaymentItemsIt = unClearPaymentItems.iterator();
			while (unClearPaymentItemsIt.hasNext())
			{
				UnClearPayment unClearPayment = unClearPaymentItemsIt.next();
				if (conntractNo.equals(unClearPayment.getContract_no())  || unClearPayment.getPaymentamount().compareTo(new BigDecimal("0")) ==0)
				{
//					Voucher oldVoucher = this.voucherService.getVoucherByBusinessId(unClearPayment.getPaymentid(), "1");
//					Set<VoucherItem> oldVoucherItem = oldVoucher.getVoucherItem();
//					Iterator<VoucherItem> payVoucherItemit = oldVoucherItem.iterator();
//					while (payVoucherItemit.hasNext())
//					{
//						VoucherItem oldItem = payVoucherItemit.next();
//						if (oldItem.getVoucher().getBusinessid().equals(unClearPayment.getPaymentid()) && oldItem.getBusinessitemid().equals(unClearPayment.getPaymentitemid()))
//						{
//							ClearVoucherItemStruct clearVoucherItemStruct = new ClearVoucherItemStruct();
//							clearVoucherItemStruct.setRowno(oldItem.getRownumber());
//							clearVoucherItemStruct.setVoucherno(unClearPayment.getVoucherno());
//							clearVoucherItemStruct.setYear(oldItem.getVoucher().getFiyear());
//							VoucherItem voucherItem = getClearVoucherItem(clearVoucherItemStruct);
//							itemValue = itemValue.add(oldItem.getAmount2());
//							voucherItem.setVoucher(clearVoucher);
//							voucherItemList.add(voucherItem);
//						}
//					}
					VendorTitle vendortitle = vendorTitleHibernateDao.get(unClearPayment.getVendortitleid());
					ClearVoucherItemStruct clearVoucherItemStruct = new ClearVoucherItemStruct();
					clearVoucherItemStruct.setRowno(vendortitle.getBuzei());
					clearVoucherItemStruct.setVoucherno(vendortitle.getBelnr());
					clearVoucherItemStruct.setYear(vendortitle.getGjahr());
					VoucherItem voucherItem = getClearVoucherItem(clearVoucherItemStruct);
					voucherItem.setVoucher(clearVoucher);
					itemValue = itemValue.add(vendortitle.getBwbje());
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
					VoucherItem supplierVoucherItem = getProfitAndLossVoucherItem(supplierSinglClear, subtractVlaue, currency,conntractNo, "1");
					supplierVoucherItem.setVoucher(clearVoucher);
					voucherItemList.add(supplierVoucherItem);

					VoucherItem profOrLossVoucherItem = getProfitAndLossVoucherItem(supplierSinglClear, subtractVlaue, currency,conntractNo, "2");
					profOrLossVoucherItem.setVoucher(clearVoucher);
					voucherItemList.add(profOrLossVoucherItem);
				}
			}
		}
		return contractNos;
	}

	/**
	 * 取得清帐凭证抬头
	 * 
	 * @param supplierSinglClear
	 */
	private Voucher getClearVoucher(SupplierSinglClear supplierSinglClear, String bukrs)
	{
		Voucher voucher = new Voucher();
		String fiperiod = "";
		String fiyear = "";
		// 添加凭证抬头数据
		voucher.setBusinessid(supplierSinglClear.getSuppliersclearid());
		voucher.setBusinesstype("09"); // 票清付款
		voucher.setVouchertype("SA");
		voucher.setCompanycode(bukrs);
		voucher.setCurrency(supplierSinglClear.getCurrencytext());
		voucher.setGsber(supplierSinglClear.getDepid());
		String accountdate = supplierSinglClear.getAccountdate().replace("-", "");
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
		voucher.setVoucherdate(supplierSinglClear.getVoucherdate().replace("-", ""));
		voucher.setVouchertext(supplierSinglClear.getText()); // 凭证抬头文本
		// 付款清帐标识
		voucher.setFlag("P");
		// 凭证分类
		voucher.setVoucherclass("");
		// 计息日
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		voucher.setValut(dateFormat.format(new Date()));
		// 供应商编号
		voucher.setAgkon(supplierSinglClear.getSupplier());
		// 科目类型
		voucher.setAgkoa("K");
		// 清帐凭证状态
		voucher.setBstat("A");
		// 汇率
		voucher.setExchangerate(new BigDecimal("1"));

		return voucher;
	}

	/**
	 * 判断单据是否可全清帐
	 * 
	 * @param businessId
	 * @return
	 */
	@Deprecated
	public boolean judgeIsAllClear(String businessId)
	{
		return this.supplierSinglClearJdbcDao.judgeIsAllClear(businessId);
	}

	/**
	 * 判断单据是否可全清帐
	 * 
	 * @param supplierSinglClear
	 * @return
	 */
	public boolean judgeIsAllClear(SupplierSinglClear supplierSinglClear)
	{
		Set<UnClearSupplieBill> unClearSupplieBills = supplierSinglClear.getUnClearSupplieBill();
		Set<UnClearPayment> unClearPayments = supplierSinglClear.getUnClearPayment();

		// 应付款总计
		BigDecimal sumBillAmount = new BigDecimal(0);
		// 清帐金额总计
		BigDecimal sumBillClearAmount = new BigDecimal(0);
		for (UnClearSupplieBill unClearSupplieBill : unClearSupplieBills)
		{
			sumBillAmount = sumBillAmount.add(unClearSupplieBill.getReceivableamount());
			sumBillClearAmount = sumBillClearAmount.add(unClearSupplieBill.getClearamount());
		}
		// 付款单金额总计
		BigDecimal sumFundAmount = new BigDecimal(0);
		// 本次抵消金额总计
		BigDecimal sumFundClearAmount = new BigDecimal(0);
		for (UnClearPayment unClearPayment : unClearPayments)
		{
			sumFundAmount = sumFundAmount.add(unClearPayment.getPaymentamount());
			sumFundClearAmount = sumFundClearAmount.add(unClearPayment.getNowclearamount());
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
	 * 判断单据下合同是否可全清帐
	 * 
	 * @param supplierSinglClear
	 * @return
	 */
	public List<String> judgeContractIsAllClear(SupplierSinglClear supplierSinglClear)
	{
		String businessId = supplierSinglClear.getSuppliersclearid();
		List<String> clearedContractNo = new ArrayList<String>();
		List<Map<String, Object>> list = this.supplierSinglClearJdbcDao.getUnClearSuppbillByContractNo(businessId);
		List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();
		if (null != list && list.size() > 0)
		{
			log.debug("开始判断所有行项目下合同是否可清：");
			String tempCOntractNo = "";
			// 合同未付(未收)金额合计
			BigDecimal sumBillUnClearAmount = new BigDecimal(0.00);
			BigDecimal sumBillClearAmount = new BigDecimal(0.00);
			List<String> titleIdList = new ArrayList<String>();
			for (int i = 0; i < list.size(); i++)
			{
				Map map = list.get(i);
				String contractNo = (String) map.get("CONTRACT_NO");
				BigDecimal tempAmount1 = (BigDecimal) map.get("CLEARAMOUNT");
				String invoice = (String) map.get("INVOICE");
				BigDecimal totAmount = (BigDecimal) map.get("RECEIVABLEAMOUNT");
				BigDecimal paidamount = this.vendorTitleJdbcDao.getSumClearAmount(invoice.trim(), BusinessState.ALL_SUBMITPASS);
				// 未付款、未收款
				BigDecimal tempAmount2 = totAmount.subtract(paidamount);
				String titleId = (String) map.get("VENDORTITLEID");
				if (StringUtils.isNullBlank(tempCOntractNo))
				{
					tempCOntractNo = contractNo;
					sumBillClearAmount = sumBillClearAmount.add(tempAmount1);
					sumBillUnClearAmount = sumBillUnClearAmount.add(tempAmount2);
					titleIdList.add(titleId);
					if (i == list.size() - 1)
					{
						Map<String, Object> tempMap = new HashMap<String, Object>();
						tempMap.put("UNPAIDAMOUNT", sumBillUnClearAmount);
						tempMap.put("CLEARAMOUNT", sumBillClearAmount);
						tempMap.put("TITLEIDS", titleIdList);
						tempMap.put("CONTRACTNO", tempCOntractNo);
						returnList.add(tempMap);
					}
				}
				else
				{
					if (contractNo.equals(tempCOntractNo))
					{
						sumBillClearAmount = sumBillClearAmount.add(tempAmount1);
						sumBillUnClearAmount = sumBillUnClearAmount.add(tempAmount2);
						titleIdList.add(titleId);
						if (i == list.size() - 1)
						{
							Map<String, Object> tempMap = new HashMap<String, Object>();
							tempMap.put("UNPAIDAMOUNT", sumBillUnClearAmount);
							tempMap.put("CLEARAMOUNT", sumBillClearAmount);
							tempMap.put("TITLEIDS", titleIdList);
							tempMap.put("CONTRACTNO", tempCOntractNo);
							returnList.add(tempMap);
						}
					}
					else
					{
						Map<String, Object> tempMap = new HashMap<String, Object>();
						tempMap.put("UNPAIDAMOUNT", sumBillUnClearAmount);
						tempMap.put("CLEARAMOUNT", sumBillClearAmount);
						tempMap.put("TITLEIDS", titleIdList);
						tempMap.put("CONTRACTNO", tempCOntractNo);
						returnList.add(tempMap);

						tempCOntractNo = contractNo;
						sumBillUnClearAmount = new BigDecimal(0.00);
						sumBillClearAmount = new BigDecimal(0.00);
						titleIdList = new ArrayList<String>();
						sumBillClearAmount = sumBillClearAmount.add(tempAmount1);
						sumBillUnClearAmount = sumBillUnClearAmount.add(tempAmount2);
						titleIdList.add(titleId);

						Map<String, Object> tempMap1 = new HashMap<String, Object>();
						tempMap1.put("UNPAIDAMOUNT", sumBillUnClearAmount);
						tempMap1.put("CLEARAMOUNT", sumBillClearAmount);
						tempMap1.put("TITLEIDS", titleIdList);
						tempMap1.put("CONTRACTNO", tempCOntractNo);
						returnList.add(tempMap1);
					}
				}

			}
		}

		if (null != returnList && returnList.size() > 0)
		{
			
			
			log.debug("共有" + returnList.size() + "个合同：");
			for (Map<String, Object> map : returnList)
			{
				String contractNo = (String) map.get("CONTRACTNO");
				// 合同下(未付款、未收款)合计
				BigDecimal billUnClearAmount = (BigDecimal) map.get("UNPAIDAMOUNT");
				// 合同下清帐金额合计
				BigDecimal billClearAmount = (BigDecimal) map.get("CLEARAMOUNT");
				
				//应收款金额
				BigDecimal receivableamount = new BigDecimal(0);
				
				BigDecimal amount = new BigDecimal(0);
				//调整金金额
				BigDecimal ajAmount = new BigDecimal(0);
				Set<UnClearSupplieBill> unclearSupplieBills = supplierSinglClear.getUnClearSupplieBill();
				Set<UnClearPayment> unClearPaymentItems = supplierSinglClear.getUnClearPayment();
				
				for(UnClearSupplieBill unclearSupplieBill:unclearSupplieBills){
					if (contractNo !=null && contractNo.equals(unclearSupplieBill.getContract_no())){
					receivableamount = receivableamount.add(unclearSupplieBill.getReceivableamount());
					ajAmount = ajAmount.add(unclearSupplieBill.getAdjustamount());
					}
				}
				
				for(UnClearPayment unClearPayment:unClearPaymentItems){
					if (contractNo !=null && contractNo.equals(unClearPayment.getContract_no())){
						amount = amount.add(unClearPayment.getPaymentamount());
					}
				}
				//得到之前汇损，或调整金的voucheritem
				List<VoucherItem> voucherItems = voucherItemJdbcDao.getVoucherItems(supplierSinglClear.getSupplier(), contractNo);
				for(VoucherItem voucherItem2 : voucherItems){
					String rowNumber = voucherItem2.getRownumber();
					if(rowNumber.length()==2)rowNumber="0" + rowNumber;			
					if(rowNumber.length()==1)rowNumber="00" + rowNumber;
					VendorTitle vendorTitle = vendorTitleJdbcDao.getByVoucherNo(voucherItem2.getVoucher().getCompanycode(), voucherItem2.getVoucher().getVoucherno(), voucherItem2.getVoucher().getFiyear(), rowNumber);
					if(null !=vendorTitle && "1".equals(vendorTitle.getIscleared())){
						if("31".equals(voucherItem2.getCheckcode()) || "19".equals(voucherItem2.getCheckcode())){
							ajAmount = ajAmount.add(voucherItem2.getAmount());
						}else if("21".equals(voucherItem2.getCheckcode()) || "09".equals(voucherItem2.getCheckcode())){
							ajAmount = ajAmount.subtract(voucherItem2.getAmount());
						}					
					}
				}
				
				log.debug("合同号:" + contractNo + ", billUnClearAmount:" + billUnClearAmount + ", billClearAmount:" + billClearAmount);
				if (billUnClearAmount.compareTo(billClearAmount) == 0)
				{
					// 根据合同号等，查找该合同未清款表中未抵消付款合计
					List<Map<String, Object>> listFund = this.supplierSinglClearJdbcDao.getUnclearpaymentByContractNo(businessId, contractNo);
					// 款抵消金额
					BigDecimal fundClearamount = new BigDecimal(0.00);
					// 款未抵消金额
					BigDecimal fundUnClearAmount = new BigDecimal(0.00);
					
					
					for (int j = 0; j < listFund.size(); j++)
					{
						Map mapFund = listFund.get(j);
						String itemId = (String) mapFund.get("PAYMENTITEMID");
						// 总金额
						BigDecimal fundAmount = (BigDecimal) mapFund.get("PAYMENTAMOUNT");
						// 已清金额
						BigDecimal offsetamount = this.paymentItemJdbcDao.getSumClearAmount(itemId.trim(), BusinessState.ALL_SUBMITPASS);
						// 未清金额, = 金额 - 已清金额
						BigDecimal unoffsetamount = fundAmount.subtract(offsetamount);
						BigDecimal clearAmount = (BigDecimal) mapFund.get("NOWCLEARAMOUNT");
						fundClearamount = fundClearamount.add(clearAmount);
						fundUnClearAmount = fundUnClearAmount.add(unoffsetamount);
						
					}
					// 款上，款本次抵消金额合计==款未抵消金额
					log.debug("fundClearamount:" + fundClearamount + ",fundUnClearAmount:" + fundUnClearAmount);
					if (fundClearamount.compareTo(fundUnClearAmount) == 0)
					{
						// 合同下已清票金额合计
						BigDecimal sumAllContractBillTot = this.vendorTitleJdbcDao.getSumAmount(contractNo);
						BigDecimal sumAllContractFundTot = this.paymentItemJdbcDao.getSumAmount(contractNo);
						//合同下已清票退款金额合计
						BigDecimal sumAllContractRefundTot = this.vendorTitleJdbcDao.getSumRefundAmount(supplierSinglClear.getSupplier(), BusinessState.ALL_BILLCLEAR_PAIDUP, contractNo);
						BigDecimal tot1 = sumAllContractBillTot.add(receivableamount);
						tot1 = tot1.add(sumAllContractRefundTot);
						BigDecimal tot2 = sumAllContractFundTot.add(amount);
						tot2 = tot2.subtract(ajAmount);
						log.debug("sumAllContractBillTot:" + sumAllContractBillTot + ",sumAllContractFundTot:" + sumAllContractFundTot);
						log.debug("billClearAmount:" + billClearAmount + ",fundClearamount:" + fundClearamount);

						// 如果金额一致，可以清帐。
						if (tot1.compareTo(tot2) == 0)
						{
							log.debug("合同号：" + contractNo + "，可以清帐！");
							boolean f=true;
							for(String no :clearedContractNo){
								if(no.equals(contractNo)){
									f=false;
									break;
								}
							}
							if(f)clearedContractNo.add(contractNo);
						}
						else
						{
							log.debug("合同号：" + contractNo + "，不可以清帐！");
						}
					}
					else
					{
						log.debug("合同号：" + contractNo + "，不可以清帐！");
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
	 * @param supplierSinglClear
	 * @return
	 */
	public List<String> judgeProjectIsAllClear(SupplierSinglClear supplierSinglClear,List<String> clearedContractNo, String contractNos)
	{
		String businessId = supplierSinglClear.getSuppliersclearid();
		List<String> clearedProjectNo = new ArrayList<String>();
		List<Map<String, Object>> list = this.supplierSinglClearJdbcDao.getUnClearSuppbillByProjectNo(businessId);
		List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();
		if (null != list && list.size() > 0)
		{
			log.debug("开始判断所有行项目下立项是否可清：");
			String tempProjectNo = "";
			// 立项未付(未收)金额合计
			BigDecimal sumBillUnClearAmount = new BigDecimal(0.00);
			BigDecimal sumBillClearAmount = new BigDecimal(0.00);
			List<String> titleIdList = new ArrayList<String>();
			for (int i = 0; i < list.size(); i++)
			{
				Map map = list.get(i);
				String projectNo = (String) map.get("PROJECT_NO");
				BigDecimal tempAmount1 = (BigDecimal) map.get("CLEARAMOUNT");
				String invoice = (String) map.get("INVOICE");
				BigDecimal totAmount = (BigDecimal) map.get("RECEIVABLEAMOUNT");
				BigDecimal paidamount = this.vendorTitleJdbcDao.getSumClearAmount(invoice.trim(), BusinessState.ALL_SUBMITPASS);
				// 未付款、未收款
				BigDecimal tempAmount2 = totAmount.subtract(paidamount);
				String titleId = (String) map.get("VENDORTITLEID");
				if (StringUtils.isNullBlank(tempProjectNo))
				{
					tempProjectNo = projectNo;
					sumBillClearAmount = sumBillClearAmount.add(tempAmount1);
					sumBillUnClearAmount = sumBillUnClearAmount.add(tempAmount2);
					titleIdList.add(titleId);
					if (i == list.size() - 1)
					{
						Map<String, Object> tempMap = new HashMap<String, Object>();
						tempMap.put("UNPAIDAMOUNT", sumBillUnClearAmount);
						tempMap.put("CLEARAMOUNT", sumBillClearAmount);
						tempMap.put("TITLEIDS", titleIdList);
						tempMap.put("PROJECTNO", tempProjectNo);
						returnList.add(tempMap);
					}
				}
				else
				{
					if (projectNo.equals(tempProjectNo))
					{
						sumBillClearAmount = sumBillClearAmount.add(tempAmount1);
						sumBillUnClearAmount = sumBillUnClearAmount.add(tempAmount2);
						titleIdList.add(titleId);
						if (i == list.size() - 1)
						{
							Map<String, Object> tempMap = new HashMap<String, Object>();
							tempMap.put("UNPAIDAMOUNT", sumBillUnClearAmount);
							tempMap.put("CLEARAMOUNT", sumBillClearAmount);
							tempMap.put("TITLEIDS", titleIdList);
							tempMap.put("PROJECTNO", tempProjectNo);
							returnList.add(tempMap);
						}
					}
					else
					{
						Map<String, Object> tempMap = new HashMap<String, Object>();
						tempMap.put("UNPAIDAMOUNT", sumBillUnClearAmount);
						tempMap.put("CLEARAMOUNT", sumBillClearAmount);
						tempMap.put("TITLEIDS", titleIdList);
						tempMap.put("PROJECTNO", tempProjectNo);
						returnList.add(tempMap);

						tempProjectNo = projectNo;
						sumBillUnClearAmount = new BigDecimal(0.00);
						sumBillClearAmount = new BigDecimal(0.00);
						titleIdList = new ArrayList<String>();
						sumBillClearAmount = sumBillClearAmount.add(tempAmount1);
						sumBillUnClearAmount = sumBillUnClearAmount.add(tempAmount2);
						titleIdList.add(titleId);

						Map<String, Object> tempMap1 = new HashMap<String, Object>();
						tempMap1.put("UNPAIDAMOUNT", sumBillUnClearAmount);
						tempMap1.put("CLEARAMOUNT", sumBillClearAmount);
						tempMap1.put("TITLEIDS", titleIdList);
						tempMap1.put("PROJECTNO", tempProjectNo);
						returnList.add(tempMap1);
					}
				}

			}
		}

		if (null != returnList && returnList.size() > 0)
		{
			
			
			log.debug("共有" + returnList.size() + "个立项：");
			for (Map<String, Object> map : returnList)
			{
				String projectNo = (String) map.get("PROJECTNO");
				// 立项下(未付款、未收款)合计
				BigDecimal billUnClearAmount = (BigDecimal) map.get("UNPAIDAMOUNT");
				// 立项下清帐金额合计
				BigDecimal billClearAmount = (BigDecimal) map.get("CLEARAMOUNT");
				log.debug("立项号:" + projectNo + "billUnClearAmount:" + billUnClearAmount + ", billClearAmount:" + billClearAmount);
				if (billUnClearAmount.compareTo(billClearAmount) == 0)
				{
					// 根据合同号等，查找该立项未清款表中未抵消付款合计
					List<Map<String, Object>> listFund = this.supplierSinglClearJdbcDao.getUnclearpaymentByProjectNo(businessId, projectNo);
					// 款抵消金额
					BigDecimal fundClearamount = new BigDecimal(0.00);
					// 款未抵消金额
					BigDecimal fundUnClearAmount = new BigDecimal(0.00);
					//应收款金额
					BigDecimal receivableamount = new BigDecimal(0);
					
					BigDecimal amount = new BigDecimal(0);
					//调整金金额
					BigDecimal ajAmount = new BigDecimal(0);
					Set<UnClearSupplieBill> unclearSupplieBills = supplierSinglClear.getUnClearSupplieBill();
					Set<UnClearPayment> unClearPaymentItems = supplierSinglClear.getUnClearPayment();
					
					for(UnClearSupplieBill unclearSupplieBill:unclearSupplieBills){
						String contract_no = unclearSupplieBill.getContract_no();
						if (projectNo.equals(unclearSupplieBill.getProject_no()))
						{
							if (null != clearedContractNo && clearedContractNo.contains(contract_no))
							{
								continue;
							}
							else
							{
								receivableamount = receivableamount.add(unclearSupplieBill.getReceivableamount());
								ajAmount = ajAmount.add(unclearSupplieBill.getAdjustamount());
							}
						}
					}
					
					for(UnClearPayment unClearPayment:unClearPaymentItems){
						String contract_no = unClearPayment.getProject_no();
						if (projectNo.equals(unClearPayment.getProject_no()))
						{
							if (null != clearedContractNo && clearedContractNo.contains(contract_no))
							{
								continue;
							}
							else
							{
								amount = amount.add(unClearPayment.getPaymentamount());
							}
						}
					}
					
					//得到之前汇损，或调整金的voucheritem
					List<VoucherItem> voucherItems = voucherItemJdbcDao.getVoucherItems(supplierSinglClear.getSupplier(), projectNo);
					for(VoucherItem voucherItem2 : voucherItems){
						String rowNumber = voucherItem2.getRownumber();
						if(rowNumber.length()==2)rowNumber="0" + rowNumber;			
						if(rowNumber.length()==1)rowNumber="00" + rowNumber;
						VendorTitle vendorTitle = vendorTitleJdbcDao.getByVoucherNo(voucherItem2.getVoucher().getCompanycode(), voucherItem2.getVoucher().getVoucherno(), voucherItem2.getVoucher().getFiyear(), rowNumber);
						if(null !=vendorTitle && "1".equals(vendorTitle.getIscleared())){
							if("31".equals(voucherItem2.getCheckcode()) || "19".equals(voucherItem2.getCheckcode())){
								ajAmount = ajAmount.subtract(voucherItem2.getAmount());
							}else if("21".equals(voucherItem2.getCheckcode()) || "09".equals(voucherItem2.getCheckcode())){
								ajAmount = ajAmount.add(voucherItem2.getAmount());
							}				
						}
					}
					
					for (int j = 0; j < listFund.size(); j++)
					{
						Map mapFund = listFund.get(j);
						String itemId = (String) mapFund.get("PAYMENTITEMID");
						// 总金额
						BigDecimal fundAmount = (BigDecimal) mapFund.get("PAYMENTAMOUNT");
						// 已清金额
						BigDecimal offsetamount = this.paymentItemJdbcDao.getSumClearAmount(itemId.trim(), BusinessState.ALL_SUBMITPASS);
						// 未清金额, = 金额 - 已清金额
						BigDecimal unoffsetamount = fundAmount.subtract(offsetamount);
						BigDecimal clearAmount = (BigDecimal) mapFund.get("NOWCLEARAMOUNT");
						fundClearamount = fundClearamount.add(clearAmount);
						fundUnClearAmount = fundUnClearAmount.add(unoffsetamount);
					}
					// 款上，款本次抵消金额合计==款未抵消金额
					if (fundClearamount.compareTo(fundUnClearAmount) == 0)
					{
						// 立项下已清票金额合计
						BigDecimal sumAllProjectBillTot = this.vendorTitleJdbcDao.getSumAmount(projectNo, contractNos);
						BigDecimal sumAllProjectFundTot = this.paymentItemJdbcDao.getSumAmount(projectNo, contractNos);
						//合同下已清票退款金额合计
						BigDecimal sumAllContractRefundTot = this.vendorTitleJdbcDao.getSumRefundAmountByProject(supplierSinglClear.getSupplier(), BusinessState.ALL_BILLCLEAR_PAIDUP,projectNo, contractNos);
						
						BigDecimal tot1 = sumAllProjectBillTot.add(receivableamount);
						tot1 = tot1.add(sumAllContractRefundTot);
						BigDecimal tot2 = sumAllProjectFundTot.add(amount);
						tot2 = tot2.subtract(ajAmount);
						log.debug("sumAllProjectBillTot:" + sumAllProjectBillTot + ",sumAllProjectFundTot:" + sumAllProjectFundTot);
						log.debug("billClearAmount:" + billClearAmount + ",fundClearamount:" + fundClearamount);

						// 如果金额一致，可以清帐。
						if (tot1.compareTo(tot2) == 0)
						{
							log.debug("立项号：" + projectNo + "，可以清帐！");
							boolean f=true;
							for(String no :clearedProjectNo){
								if(no.equals(projectNo)){
									f=false;
									break;
								}
							}
							if(f)clearedProjectNo.add(projectNo);
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
	 * 判断单据下供应商是否可全清帐
	 * 
	 * @param supplierSinglClear
	 * @return
	 */
	public boolean judgeSupplierIsAllClear(SupplierSinglClear supplierSinglClear,String businessId, String supplier, String contractNos, String projectNos, String bukrs)
	{
		Set<UnClearSupplieBill> unClearSupplieBills = supplierSinglClear.getUnClearSupplieBill();
		Set<UnClearPayment> unClearPaymentItems = supplierSinglClear.getUnClearPayment();
		// (未付款)合计
		BigDecimal billUnClearAmount = new BigDecimal(0);
		// 清帐金额合计
		BigDecimal billClearAmount = new BigDecimal(0);
		
		//应收款金额
		BigDecimal receivableamount = new BigDecimal(0);
		
		BigDecimal amount = new BigDecimal(0);
		//调整金金额
		BigDecimal ajAmount = new BigDecimal(0);
		for(UnClearSupplieBill unclearSupplieBill:unClearSupplieBills){
			
			billUnClearAmount = billUnClearAmount.add(unclearSupplieBill.getUnpaidamount());
			billClearAmount = billClearAmount.add(unclearSupplieBill.getClearamount());
			receivableamount = receivableamount.add(unclearSupplieBill.getReceivableamount());
			ajAmount = ajAmount.add(unclearSupplieBill.getAdjustamount());
		}
		if (billUnClearAmount.compareTo(billClearAmount) == 0)
		{
			// 款抵消金额
			BigDecimal fundClearamount = new BigDecimal(0.00);
			// 款未抵消金额
			BigDecimal fundUnClearAmount = new BigDecimal(0.00);
			for(UnClearPayment unClearPayment:unClearPaymentItems){
				fundClearamount = fundClearamount.add(unClearPayment.getNowclearamount());
				fundUnClearAmount = fundUnClearAmount.add(unClearPayment.getUnoffsetamount());
				amount = amount.add(unClearPayment.getPaymentamount());
			}
			// 款上，款本次抵消金额合计==款未抵消金额
			if (fundClearamount.compareTo(fundUnClearAmount) == 0)
			{
				
				//得到之前汇损，或调整金的voucheritem
//				List<VoucherItem> voucherItems = voucherItemJdbcDao.getVoucherItems(supplierSinglClear.getSupplier(), "");
//				for(VoucherItem voucherItem2 : voucherItems){
//					String rowNumber = voucherItem2.getRownumber();
//					if(rowNumber.length()==2)rowNumber="0" + rowNumber;			
//					if(rowNumber.length()==1)rowNumber="00" + rowNumber;
//					VendorTitle vendorTitle = vendorTitleJdbcDao.getByVoucherNo(voucherItem2.getVoucher().getCompanycode(), voucherItem2.getVoucher().getVoucherno(), voucherItem2.getVoucher().getFiyear(), rowNumber);
//					if(null !=vendorTitle && "1".equals(vendorTitle.getIscleared())){
//						if("31".equals(voucherItem2.getCheckcode()) || "19".equals(voucherItem2.getCheckcode())){
//							ajAmount = ajAmount.subtract(voucherItem2.getAmount());
//						}else if("21".equals(voucherItem2.getCheckcode()) || "09".equals(voucherItem2.getCheckcode())){
//							ajAmount = ajAmount.add(voucherItem2.getAmount());
//						}				
//					}
//				}
				
//				StringBuffer sb = new StringBuffer();
//				sb = new StringBuffer();
//				sb.append("select nvl(sum(CLEARAMOUNT),0) from YUNCLEARSUPPBILL");
//				sb.append(" where SUPPLIERSCLEARID='" + businessId + "'");
//				// 票清帐金额合计
//				BigDecimal sumBillClearAmount = this.supplierSinglClearJdbcDao.getSumAmount(sb.toString());
//		
//				sb = new StringBuffer();
//				sb.append("select nvl(sum(NOWCLEARAMOUNT),0) from YUNCLEARPAYMENT");
//				sb.append(" where SUPPLIERSCLEARID='" + businessId + "'");
//				// 款清帐金额合计
//				BigDecimal sumFundClearAmount = this.supplierSinglClearJdbcDao.getSumAmount(sb.toString());
		
				// 供应商下已清票金额合计
				BigDecimal sumAllSupplierBillTot = this.vendorTitleJdbcDao.getSumAmount2(supplier, contractNos, projectNos, bukrs,"H",supplierSinglClear.getCurrencytext(),supplierSinglClear.getDepid(),supplierSinglClear.getSubject());
				BigDecimal sumAllSupplierFundTot = this.vendorTitleJdbcDao.getSumAmount2(supplier, contractNos, projectNos, bukrs,"S",supplierSinglClear.getCurrencytext(),supplierSinglClear.getDepid(),supplierSinglClear.getSubject());
//				BigDecimal sumAllSupplierFundTot = this.paymentItemJdbcDao.getSumAmount(supplier, contractNos, projectNos, bukrs);
				//取得部门ID
//				List<String> deptidList = sysDeptJdbcDao.getDeptIdByDeptCode(supplierSinglClear.getDepid());
//				String[] s=  (String[])deptidList.toArray(new String[deptidList.size()]);
//				String deptids = StringUtils.arrayToString(s, ",");
//				
//				//合同下已清票退款金额合计
//				BigDecimal sumAllContractRefundTot = this.vendorTitleJdbcDao.getSumRefundAmount(supplierSinglClear.getSupplier(), BusinessState.ALL_BILLCLEAR_PAIDUP,projectNos, contractNos,deptids);
				BigDecimal tot1 = receivableamount.add(sumAllSupplierBillTot);
//				tot1 = tot1.add(sumAllContractRefundTot);
				BigDecimal tot2 = amount.add(sumAllSupplierFundTot);
				tot2 = tot2.subtract(ajAmount);
//				log.debug("sumBillClearAmount:" + sumBillClearAmount + ",sumAllSupplierBillTot:" + sumAllSupplierBillTot);
//				log.debug("sumFundClearAmount:" + sumFundClearAmount + ",sumAllSupplierFundTot:" + sumAllSupplierFundTot);
		
//				if (tot1.compareTo(tot2) == 0 && sumAllSupplierBillTot.compareTo(new BigDecimal(0)) == 1 && sumAllSupplierFundTot.compareTo(new BigDecimal(0)) == 1)
				if (tot1.compareTo(tot2) == 0 )	
				{
					log.debug("供应商" + supplier + "，可以清帐。");
					return true;
				}
				else
				{
					log.debug("供应商" + supplier + "，不可以清帐。");
				}
			}
		}
		return false;
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
	 * @see com.infolion.xdss3.singleClearGen.service.SupplierSinglClearServiceGen#_get(java.lang.String)
	 */
	@Override
	public SupplierSinglClear _get(String id) {
		// TODO Auto-generated method stub
		return supplierSinglClearJdbcDao.getSupplierSinglClearById(id);
	}

	/* (non-Javadoc)
	 * @see com.infolion.xdss3.singleClearGen.service.SupplierSinglClearServiceGen#_getDetached(java.lang.String)
	 */
	@Override
	public SupplierSinglClear _getDetached(String id) {
		// TODO Auto-generated method stub
		return supplierSinglClearJdbcDao.getSupplierSinglClearById(id);
	}

	/* (non-Javadoc)
	 * @see com.infolion.xdss3.singleClearGen.service.SupplierSinglClearServiceGen#_getEntityCopy(java.lang.String)
	 */
	@Override
	public SupplierSinglClear _getEntityCopy(String id) {
		SupplierSinglClear supplierSinglClear = new SupplierSinglClear();
		SupplierSinglClear supplierSinglClearOld = supplierSinglClearJdbcDao.getSupplierSinglClearById(id);
		try
		{
			BeanUtils.copyProperties(supplierSinglClear, supplierSinglClearOld);
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (InvocationTargetException e)
		{
			e.printStackTrace();
		}
		// supplierSinglClear.setSuppliersclearid(null);
		supplierSinglClear.setProcessstate(" ");
		return supplierSinglClear;
	}

	/* (non-Javadoc)
	 * @see com.infolion.xdss3.singleClearGen.service.SupplierSinglClearServiceGen#_getForEdit(java.lang.String)
	 */
	@Override
	public SupplierSinglClear _getForEdit(String id) {
		// TODO Auto-generated method stub
		return supplierSinglClearJdbcDao.getSupplierSinglClearById(id);
	}
	
	
}