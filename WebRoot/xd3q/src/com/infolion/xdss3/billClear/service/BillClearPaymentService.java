/*
 * @(#)BillClearPaymentService.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年06月22日 15点15分21秒
 *  描　述：创建
 */
package com.infolion.xdss3.billClear.service;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infolion.platform.basicApp.authManage.UserContextHolder;
import com.infolion.platform.basicApp.authManage.domain.UserContext;
import com.infolion.platform.bo.service.BusinessObjectService;
import com.infolion.platform.console.org.dao.SysDeptJdbcDao;
import com.infolion.platform.dpframework.uicomponent.number.service.NumberService;
import com.infolion.platform.dpframework.util.StringUtils;
import com.infolion.platform.util.DateUtils;
import com.infolion.xdss3.BusinessState;
import com.infolion.xdss3.Constants;
import com.infolion.xdss3.Constants.cleared;
import com.infolion.xdss3.billClear.dao.BillClearPaymentJdbcDao;
import com.infolion.xdss3.billClear.dao.BillInPaymentJdbcDao;
import com.infolion.xdss3.billClear.domain.BillClearItemPay;
import com.infolion.xdss3.billClear.domain.BillClearPayment;
import com.infolion.xdss3.billClear.domain.BillInPayment;
import com.infolion.xdss3.billClearGen.service.BillClearPaymentServiceGen;
import com.infolion.xdss3.customerDrawback.dao.CustomerRefundItemJdbcDao;
import com.infolion.xdss3.financeSquare.domain.FundFlow;
import com.infolion.xdss3.financeSquare.domain.SettleSubject;
import com.infolion.xdss3.masterData.dao.VendorTitleJdbcDao;
import com.infolion.xdss3.masterData.domain.CustomerTitle;
import com.infolion.xdss3.masterData.domain.VendorTitle;
import com.infolion.xdss3.masterData.service.VendorService;
import com.infolion.xdss3.payment.dao.PaymentItemJdbcDao;
import com.infolion.xdss3.payment.domain.ClearVoucherItemStruct;
import com.infolion.xdss3.payment.domain.ImportPaymentItem;
import com.infolion.xdss3.payment.service.PaymentItemService;
import com.infolion.xdss3.reassign.dao.ReassignJdbcDao;
import com.infolion.xdss3.voucher.dao.VoucherHibernateDao;
import com.infolion.xdss3.voucher.dao.VoucherJdbcDao;
import com.infolion.xdss3.voucher.domain.Voucher;
import com.infolion.xdss3.voucher.service.VoucherService;
import com.infolion.xdss3.voucheritem.dao.VoucherItemJdbcDao;
import com.infolion.xdss3.voucheritem.domain.VoucherItem;

/**
 * <pre>
 * 票清付款(BillClearPayment)服务类
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
public class BillClearPaymentService extends BillClearPaymentServiceGen
{
	private Log log = LogFactory.getFactory().getLog(this.getClass());

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
	private BillInPaymentJdbcDao billInPaymentJdbcDao;

	/**
	 * @param billInPaymentJdbcDao
	 *            the billInPaymentJdbcDao to set
	 */
	public void setBillInPaymentJdbcDao(BillInPaymentJdbcDao billInPaymentJdbcDao)
	{
		this.billInPaymentJdbcDao = billInPaymentJdbcDao;
	}
	
	@Autowired
	private BillClearPaymentJdbcDao billClearPaymentJdbcDao;

	/**
	 * @param billInPaymentJdbcDao
	 *            the billInPaymentJdbcDao to set
	 */
	public void setBillClearPaymentJdbcDao(BillClearPaymentJdbcDao billClearPaymentJdbcDao)
	{
		this.billClearPaymentJdbcDao = billClearPaymentJdbcDao;
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
	protected VoucherHibernateDao voucherHibernateDao;

	public void setVoucherHibernateDao(VoucherHibernateDao voucherHibernateDao)
	{
		this.voucherHibernateDao = voucherHibernateDao;
	}
	
	@Autowired
	private VoucherItemJdbcDao voucherItemJdbcDao;
	
	public void setVoucherItemjdbcDao(VoucherItemJdbcDao voucherItemJdbcDao) {
		this.voucherItemJdbcDao = voucherItemJdbcDao;
	}
	
	@Autowired
	private SysDeptJdbcDao sysDeptJdbcDao;

	public void setSysDeptJdbcDao(SysDeptJdbcDao sysDeptJdbcDao) {
		this.sysDeptJdbcDao = sysDeptJdbcDao;
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
	/**
	 * 取得供应商下未清发票数据集合
	 * 
	 * @param vendortitleids
	 * @return List<VendorTitle>
	 */
	public List<VendorTitle> getVendorTitleList(String vendortitleids)
	{
		return this.vendorTitleJdbcDao.getVendorTitleList(vendortitleids);
	}

	/**
	 * 保存凭证。
	 * 
	 * @param billClearPayment
	 * @param billClearItemPays
	 * @param fundFlowPay
	 * @param settleSubjectPay
	 */
	public List<String> _saveVoucher(BillClearPayment billClearPayment, Set<BillClearItemPay> billClearItemPays, FundFlow fundFlowPay, SettleSubject settleSubjectPay )
	{
		List<String> voucherIds = new ArrayList<String>();
		String businessId = billClearPayment.getBillclearid();
		// 先删除凭证信息。
//		this.voucherService._deleteVoucherByBusinessId(businessId);
		
		// 币别
		String currency = "";
//		BigDecimal sumAmount2 = new BigDecimal(0); // 所有财务结算本位币金额的和
		BigDecimal amount2 = new BigDecimal(0); // 财务结算本位币金额
		String supplier = billClearPayment.getSupplier();
		//把部门ID转成业务范围
		String deptId = this.sysDeptJdbcDao.getDeptCodeById(billClearPayment.getDeptid());
		Voucher voucher=null;
		if (null != settleSubjectPay || null != fundFlowPay)
		{
			// 凭证
			voucher = getVoucher(billClearPayment);
			List<VoucherItem> voucherItemList = new ArrayList<VoucherItem>();

			Iterator<BillClearItemPay> it = billClearItemPays.iterator();

			int iRowNo = 1; // 行项目号
			// 调整差额总额
			BigDecimal sumAdjustamount = new BigDecimal(0);
			while (it.hasNext())
			{
				BillClearItemPay billClearItemPay = (BillClearItemPay) it.next();
				// 当调整差额 有填入值。
				if (billClearItemPay.getAdjustamount().abs().compareTo(new BigDecimal(0)) == 1)
				{
					if (StringUtils.isNullBlank(currency))
					{
						currency = billClearItemPay.getCurrency();
					}
				}
				BigDecimal adjustamount = billClearItemPay.getAdjustamount();
				sumAdjustamount = sumAdjustamount.add(adjustamount);
			}			
			voucher.setCurrency(currency);

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
			voucherItem.setSubjectdescribe(billClearPayment.getSupplier_htext());
			voucherItem.setDepid(deptId);
			voucherItem.setText(billClearPayment.getText());
			String bukrs = this.voucherService.getCompanyIDByDeptID(billClearPayment.getDeptid());
			String subject = this.customerRefundItemJdbcDao.getSubjectByCustomer(billClearPayment.getSupplier(),bukrs);
			String subjectname = this.customerRefundItemJdbcDao.getSubjectNameById(subject,bukrs);
			voucherItem.setControlaccount(subject);
			voucherItem.setCaremark(subjectname);
			voucherItem.setVoucher(voucher);
			voucherItemList.add(voucherItem);

			// 结算科目
			// 金额1有填入值
			BigDecimal amount = new BigDecimal(0);
			String debitcredit = "";
			if (null != settleSubjectPay)
			{
				amount = settleSubjectPay.getAmount1();
				debitcredit = settleSubjectPay.getDebtcredit1();
				if (amount.abs().compareTo(new BigDecimal(0)) == 1)
				{
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
					amount2 = settleSubjectPay.getStandardamount1();
					voucherItems.setAmount2(amount2);
//					sumAmount2 = sumAmount2.add(amount2);
					voucherItems.setSubject(settleSubjectPay.getSettlesubject1());
					voucherItems.setSubjectdescribe(settleSubjectPay.getSettlesubject1_htext());
					voucherItems.setDepid(settleSubjectPay.getDepid1());
					voucherItems.setCostcenter(settleSubjectPay.getCostcenter1());	
//					voucherItems.setUncheckflag(settleSubjectPay.getAntiaccount1());
					
					if("Y".equals(settleSubjectPay.getAntiaccount1())){
						voucherItems.setUncheckflag("X");
	                }else{
	                	voucherItems.setUncheckflag("");
	                }
					voucherItems.setOrderrowno(settleSubjectPay.getRowno1());
					voucherItems.setSalesorder(settleSubjectPay.getOrderno1());	
					voucherItems.setText(billClearPayment.getText());
					voucherItems.setControlaccount(subject);
					voucherItems.setCaremark(subjectname);
					voucherItems.setVoucher(voucher);
					voucherItemList.add(voucherItems);
				}

				amount = settleSubjectPay.getAmount2();
				debitcredit = settleSubjectPay.getDebtcredit2();
				if (amount.abs().compareTo(new BigDecimal(0)) == 1)
				{
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
					amount2 = settleSubjectPay.getStandardamount2();
					voucherItems.setAmount2(amount2);
//					sumAmount2 = sumAmount2.add(amount2);
					voucherItems.setSubject(settleSubjectPay.getSettlesubject2());
					voucherItems.setSubjectdescribe(settleSubjectPay.getSettlesubject2_htext());
					voucherItems.setDepid(settleSubjectPay.getDepid2());
					voucherItems.setCostcenter(settleSubjectPay.getCostcenter2());	
//					voucherItems.setUncheckflag(settleSubjectPay.getAntiaccount2());
					if("Y".equals(settleSubjectPay.getAntiaccount2())){
						voucherItems.setUncheckflag("X");
	                }else{
	                	voucherItems.setUncheckflag("");
	                }
					voucherItems.setOrderrowno(settleSubjectPay.getRowno2());
					voucherItems.setSalesorder(settleSubjectPay.getOrderno2());	
					voucherItems.setText(billClearPayment.getText());
					voucherItems.setControlaccount(subject);
					voucherItems.setCaremark(subjectname);
					voucherItems.setVoucher(voucher);
					voucherItemList.add(voucherItems);
				}

				amount = settleSubjectPay.getAmount3();
				debitcredit = settleSubjectPay.getDebtcredit3();
				if (amount.abs().compareTo(new BigDecimal(0)) == 1)
				{
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
					amount2 = settleSubjectPay.getStandardamount3();
					voucherItems.setAmount2(amount2);
//					sumAmount2 = sumAmount2.add(amount2);
					voucherItems.setSubject(settleSubjectPay.getSettlesubject3());
					voucherItems.setSubjectdescribe(settleSubjectPay.getSettlesubject3_htext());
					voucherItems.setDepid(settleSubjectPay.getDepid3());
					voucherItems.setCostcenter(settleSubjectPay.getCostcenter3());	
//					voucherItems.setUncheckflag(settleSubjectPay.getAntiaccount3());
					if("Y".equals(settleSubjectPay.getAntiaccount3())){
						voucherItems.setUncheckflag("X");
	                }else{
	                	voucherItems.setUncheckflag("");
	                }
					voucherItems.setOrderrowno(settleSubjectPay.getRowno3());
					voucherItems.setSalesorder(settleSubjectPay.getOrderno3());	
					voucherItems.setText(billClearPayment.getText());
					voucherItems.setControlaccount(subject);
					voucherItems.setCaremark(subjectname);
					voucherItems.setVoucher(voucher);
					voucherItemList.add(voucherItems);
				}

				amount = settleSubjectPay.getAmount4();
				debitcredit = settleSubjectPay.getDebtcredit4();
				if (amount.abs().compareTo(new BigDecimal(0)) == 1)
				{
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
					amount2 = settleSubjectPay.getStandardamount4();
					voucherItems.setAmount2(amount2);
//					sumAmount2 = sumAmount2.add(amount2);
					voucherItems.setSubject(settleSubjectPay.getSettlesubject4());
					voucherItems.setSubjectdescribe(settleSubjectPay.getSettlesubject4_htext());
					voucherItems.setDepid(settleSubjectPay.getDepid4());
					voucherItems.setProfitcenter(settleSubjectPay.getProfitcenter());	
//					voucherItems.setUncheckflag(settleSubjectPay.getAntiaccount4());
					if("Y".equals(settleSubjectPay.getAntiaccount4())){
						voucherItems.setUncheckflag("X");
	                }else{
	                	voucherItems.setUncheckflag("");
	                }
					voucherItems.setOrderrowno(settleSubjectPay.getRowno4());
					voucherItems.setSalesorder(settleSubjectPay.getOrderno4());	
					voucherItems.setText(billClearPayment.getText());
					voucherItems.setControlaccount(subject);
					voucherItems.setCaremark(subjectname);
					voucherItems.setVoucher(voucher);
					voucherItemList.add(voucherItems);
				}
			}
			// 纯资金往来
			// fundFlowPay.getAmount1()
			if (null != fundFlowPay)
			{
				amount = fundFlowPay.getAmount1();
				debitcredit = fundFlowPay.getDebtcredit1();
				if (amount.abs().compareTo(new BigDecimal(0)) == 1)
				{
					iRowNo = iRowNo + 1;
					// 开始添加凭证行项目
					VoucherItem voucherItems = new VoucherItem();
					voucherItems.setRownumber(String.valueOf(iRowNo));
					voucherItems.setCurrency(currency);
					voucherItems.setAmount(amount);
					voucherItems.setDebitcredit(debitcredit);
					if ("H".equalsIgnoreCase(debitcredit))
					{
						if(StringUtils.isNullBlank(fundFlowPay.getSpecialaccount1())){
							voucherItem.setCheckcode("11");
						}else{
							voucherItems.setCheckcode("19");
						}
					}
					else if ("S".equalsIgnoreCase(debitcredit))
					{
						if(StringUtils.isNullBlank(fundFlowPay.getSpecialaccount1())){
							voucherItem.setCheckcode("01");
						}else{
							voucherItems.setCheckcode("09");
						}
					}
					amount2 = fundFlowPay.getStandardamount1();
					voucherItems.setAmount2(amount2);
//					sumAmount2 = sumAmount2.add(amount2);
					voucherItems.setSubject(fundFlowPay.getCustomer1());
					voucherItems.setSubjectdescribe(fundFlowPay.getCustomer1_htext());
					voucherItems.setDepid(fundFlowPay.getDepid1());
					voucherItems.setText(billClearPayment.getText());
					voucherItems.setGlflag(fundFlowPay.getSpecialaccount1());	
//					voucherItems.setUncheckflag(fundFlowPay.getAntiaccount1());
					if("Y".equals(fundFlowPay.getAntiaccount1())){
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
					voucherItems.setVoucher(voucher);
					voucherItemList.add(voucherItems);
				}

				amount = fundFlowPay.getAmount2();
				debitcredit = fundFlowPay.getDebtcredit2();
				if (amount.abs().compareTo(new BigDecimal(0)) == 1)
				{
					iRowNo = iRowNo + 1;
					// 开始添加凭证行项目
					VoucherItem voucherItems = new VoucherItem();
					voucherItems.setRownumber(String.valueOf(iRowNo));
					voucherItems.setCurrency(currency);
					voucherItems.setAmount(amount);
					voucherItems.setDebitcredit(debitcredit);
					if ("H".equalsIgnoreCase(debitcredit))
					{
						if(StringUtils.isNullBlank(fundFlowPay.getSpecialaccount2())){
							voucherItem.setCheckcode("11");
						}else{
							voucherItems.setCheckcode("19");
						}
					}
					else if ("S".equalsIgnoreCase(debitcredit))
					{
						if(StringUtils.isNullBlank(fundFlowPay.getSpecialaccount2())){
							voucherItem.setCheckcode("01");
						}else{
							voucherItems.setCheckcode("09");
						}
					}
					amount2 = fundFlowPay.getStandardamount2();
					voucherItems.setAmount2(amount2);
//					sumAmount2 = sumAmount2.add(amount2);
					voucherItems.setSubject(fundFlowPay.getCustomer2());
					voucherItems.setSubjectdescribe(fundFlowPay.getCustomer2_htext());
					voucherItems.setDepid(fundFlowPay.getDepid2());
					voucherItems.setGlflag(fundFlowPay.getSpecialaccount2());	
//					voucherItems.setUncheckflag(fundFlowPay.getAntiaccount2());
					if("Y".equals(fundFlowPay.getAntiaccount2())){
						voucherItems.setUncheckflag("X");
	                }else{
	                	voucherItems.setUncheckflag("");
	                }
					voucherItems.setText(billClearPayment.getText());
					voucherItems.setControlaccount(subject);
					voucherItems.setCaremark(subjectname);
					if(!StringUtils.isNullBlank(voucherItems.getGlflag())){
						subject = this.customerRefundItemJdbcDao.getSkont(voucherItems,subject);
						String subjectname2 = this.customerRefundItemJdbcDao.getSubjectNameById(subject,bukrs);
						voucherItems.setControlaccount(subject);
						voucherItems.setCaremark(subjectname2);
					}
					voucherItems.setVoucher(voucher);
					voucherItemList.add(voucherItems);
				}

				amount = fundFlowPay.getAmount3();
				debitcredit = fundFlowPay.getDebtcredit3();
				if (amount.abs().compareTo(new BigDecimal(0)) == 1)
				{
					iRowNo = iRowNo + 1;
					// 开始添加凭证行项目
					VoucherItem voucherItems = new VoucherItem();
					voucherItems.setRownumber(String.valueOf(iRowNo));
					voucherItems.setCurrency(currency);
					voucherItems.setAmount(amount);
					voucherItems.setDebitcredit(debitcredit);
					if ("H".equalsIgnoreCase(debitcredit))
					{
						if(StringUtils.isNullBlank(fundFlowPay.getSpecialaccount3())){
							voucherItem.setCheckcode("11");
						}else{
							voucherItems.setCheckcode("19");
						}
					}
					else if ("S".equalsIgnoreCase(debitcredit))
					{
						if(StringUtils.isNullBlank(fundFlowPay.getSpecialaccount3())){
							voucherItem.setCheckcode("01");
						}else{
							voucherItems.setCheckcode("09");
						}
					}

					amount2 = fundFlowPay.getStandardamount3();
					voucherItems.setAmount2(amount2);
//					sumAmount2 = sumAmount2.add(amount2);
					voucherItems.setSubject(fundFlowPay.getCustomer3());
					voucherItems.setSubjectdescribe(fundFlowPay.getCustomer3_htext());
					voucherItems.setDepid(fundFlowPay.getDepid3());
					voucherItems.setGlflag(fundFlowPay.getSpecialaccount3());	
//					voucherItems.setUncheckflag(fundFlowPay.getAntiaccount3());
					if("Y".equals(fundFlowPay.getAntiaccount3())){
						voucherItems.setUncheckflag("X");
	                }else{
	                	voucherItems.setUncheckflag("");
	                }
					voucherItems.setText(billClearPayment.getText());
					voucherItems.setControlaccount(subject);
					voucherItems.setCaremark(subjectname);
					if(!StringUtils.isNullBlank(voucherItems.getGlflag())){
						subject = this.customerRefundItemJdbcDao.getSkont(voucherItems,subject);
						String subjectname2 = this.customerRefundItemJdbcDao.getSubjectNameById(subject,bukrs);
						voucherItems.setControlaccount(subject);
						voucherItems.setCaremark(subjectname2);
					}
					voucherItems.setVoucher(voucher);
					voucherItemList.add(voucherItems);
				}
			}

//			if (sumAmount2.compareTo(new BigDecimal(0)) == 1)
//			{
//				for (VoucherItem voucherItem0 : voucherItemList)
//				{
//					if (voucherItem0.getRownumber().equals("1"))
//					{
//						voucherItem0.setAmount2(sumAmount2);
//					}
//				}
//			}
			
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
			List<Voucher> li=voucherHibernateDao.getVoucherByBusinessId2(billClearPayment.getBillclearid()," ");
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
		Voucher clearVoucher = _saveClearAccountVoucher(billClearPayment,voucher);
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
	 * 取得清帐凭证抬头
	 * 
	 * @param supplierSinglClear
	 */
	private Voucher getClearVoucher(BillClearPayment billClearPayment, String bukrs)
	{
		Voucher voucher = new Voucher();
		String fiperiod = "";
		String fiyear = "";
		// 添加凭证抬头数据
		voucher.setBusinessid(billClearPayment.getBillclearid());
		voucher.setBusinesstype("04"); // 票清付款
		voucher.setVouchertype("SA");
		voucher.setCompanycode(bukrs);
		voucher.setVoucherclass("9");
		String accountdate = billClearPayment.getAccountdate().replace("-", "");
		voucher.setCheckdate(accountdate);
		BigDecimal sumAmount2 = new BigDecimal(0); // 所有财务结算本位币金额的和
		BigDecimal amount2 = new BigDecimal(0); // 财务结算本位币金额

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
		voucher.setVoucherdate(billClearPayment.getVoucherdate().replace("-", ""));
		voucher.setVouchertext("清帐凭证"); // 凭证抬头文本
		//把部门ID转成业务范围
		String depid = this.sysDeptJdbcDao.getDeptCodeById(billClearPayment.getDeptid());
		voucher.setGsber(depid);
		// 付款清帐标识
		voucher.setFlag("P");
		// 凭证分类
		voucher.setVoucherclass("");
		// 计息日
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		voucher.setValut(dateFormat.format(new Date()));
		// 供应商编号
		voucher.setAgkon(billClearPayment.getSupplier());
		// 科目类型
		voucher.setAgkoa("K");
		// 清帐凭证状态
		voucher.setBstat("A");
		// 汇率
		voucher.setExchangerate(new BigDecimal("1"));

		return voucher;
	}

	/**
	 * 处理清帐凭证,
	 * 
	 * @param supplierSinglClear
	 */
	public Voucher _saveClearAccountVoucher(BillClearPayment billClearPayment,Voucher voucher)
	{
		String businessId = billClearPayment.getBillclearid();
		String supplier = billClearPayment.getSupplier();
		

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
		
		// Set<BillClearItemPay> billClearItemPay;
		Set<BillClearItemPay> billClearItemPays = billClearPayment.getBillClearItemPay();
		Set<BillInPayment> billInPayments = billClearPayment.getBillInPayment();

		// 重新计算未清金额(未收款、未付款等)
		for (BillClearItemPay billClearItemPay : billClearItemPays)
		{
			// 发票总金额
			BigDecimal billamount = billClearItemPay.getReceivableamount();
			// 发票已经审批完的，发票已清金额
			BigDecimal receivedamount = this.vendorService.getSumClearAmount(billClearItemPay.getInvoice().trim(), BusinessState.ALL_SUBMITPASS);
			billClearItemPay.setReceivedamount(receivedamount);
			billClearItemPay.setUnreceivedamount(billamount.subtract(receivedamount));
			
			billAmount = billAmount.add(billClearItemPay.getBwbje());
			if(billClearItemPay.getUnreceivedamount().compareTo(billClearItemPay.getClearbillamount()) !=0){
				billF = true;
			}
			sumbillAmount = sumbillAmount.add(billClearItemPay.getReceivableamount());
			sumclearAmount = sumclearAmount.add(billClearItemPay.getClearbillamount());
			sumajAmount = sumajAmount.add(billClearItemPay.getAdjustamount());
			sumUnreceivableamou = sumUnreceivableamou.add(billClearItemPay.getUnreceivedamount());
			sumUnoffsetamountBwb = sumUnoffsetamountBwb.add(billClearItemPay.getUnbwbje());
//			sumclearAmountBwb = sumclearAmountBwb.add(billClearItemPay.getBwbje().divide(billClearItemPay.getReceivableamount(),3,BigDecimal.ROUND_HALF_EVEN).multiply(billClearItemPay.getClearbillamount()));
			sumclearAmountBwb = sumclearAmountBwb.add(billClearItemPay.getBwbje().multiply(billClearItemPay.getClearbillamount()).divide(billClearItemPay.getReceivableamount(),13,BigDecimal.ROUND_HALF_EVEN));
		}

		for (BillInPayment billInPayment : billInPayments)
		{
			// 款总金额
			BigDecimal goodsamount = billInPayment.getPaymentamount();
			// 付款单已审批完的 ，已清票款
			BigDecimal clearedPaymentAmount = this.paymentItemService.getSumClearAmount(billInPayment.getPaymentitemid().trim(), BusinessState.ALL_SUBMITPASS);
			// 款未抵消金额
			billInPayment.setUnoffsetamount(goodsamount.subtract(clearedPaymentAmount));
			
			collectAmount = collectAmount.add(billInPayment.getBwbje());
			if(billInPayment.getUnoffsetamount().compareTo(billInPayment.getNowclearamount()) !=0){
				clearF = true;
			}
			sumcollectAmount = sumcollectAmount.add(billInPayment.getPaymentamount());
			sumoffsetAmount = sumoffsetAmount.add(billInPayment.getNowclearamount());
			sumUnoffsetamount = sumUnoffsetamount.add(billInPayment.getUnoffsetamount());
			sumUnreceivableamouBwb= sumUnreceivableamouBwb.add(billInPayment.getUnbwbje());
//			sumoffsetAmountBwb = sumoffsetAmountBwb.add(billInPayment.getBwbje().divide(billInPayment.getPaymentamount(),3,BigDecimal.ROUND_HALF_EVEN).multiply(billInPayment.getNowclearamount()));
			sumoffsetAmountBwb = sumoffsetAmountBwb.add(billInPayment.getBwbje().multiply(billInPayment.getNowclearamount()).divide(billInPayment.getPaymentamount(),13,BigDecimal.ROUND_HALF_EVEN));
		}

		billClearPayment.setBillClearItemPay(billClearItemPays);
		billClearPayment.setBillInPayment(billInPayments);

		// 币别
		String currency = "";
		String deptId = billClearPayment.getDeptid();
		String bukrs = this.voucherService.getCompanyIDByDeptID(deptId);
		// 凭证
		Voucher clearVoucher = getClearVoucher(billClearPayment, bukrs);
		Set<VoucherItem> voucherItemList = new HashSet<VoucherItem>();
		Iterator<BillClearItemPay> it = billClearItemPays.iterator();

		int rowNo = 1; // 行项目号
		while (it.hasNext())
		{
			BillClearItemPay unClearSupplieBill = (BillClearItemPay) it.next();

			if (StringUtils.isNullBlank(currency))
			{
				currency = unClearSupplieBill.getCurrency();
				break;
			}
		}
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
		
		
		
		String taxCode =  getTaxCode(billClearPayment);
		
		
		if(null != voucher){
			for(VoucherItem voucherItem : voucher.getVoucherItem()){
				
				if ("21".equals(voucherItem.getCheckcode()))
				{
					ajValue =  ajValue.add(voucherItem.getAmount2());
				}
				else if ("31".equals(voucherItem.getCheckcode()))
				{
					ajValue =  ajValue.add(voucherItem.getAmount2());
				}
			}
		}
		
		if (judgeIsAllClear(billClearPayment))
		{
			// 处理本次可以全清帐
			getClearVoucherItem(billClearPayment, clearVoucher, voucherItemList, currency,ajValue,taxCode);
		}
		else
		{
			// 合同
			List<String> clearedContractNo = judgeContractIsAllClear(billClearPayment);
			if (null != clearedContractNo && clearedContractNo.size() > 0)
			{
				// 处理合同下清帐凭证行项目.
				contractNos = getContractClearVoucherItem(billClearPayment, clearedContractNo, clearVoucher, voucherItemList, currency, billValue, itemValue,ajValue);
			}

			if (!StringUtils.isNullBlank(contractNos))
			{
				contractNos = contractNos.substring(0, contractNos.length() - 1);
			}
			// 立项下
			List<String> clearedProjectNo = judgeProjectIsAllClear(billClearPayment, clearedContractNo, contractNos);
			if (null != clearedProjectNo && clearedProjectNo.size() > 0)
			{
				// 处理立项下清帐凭证行项目.
				projectNos = getProjectClearVoucherItem(billClearPayment, clearVoucher, voucherItemList, contractNos, clearedContractNo, clearedProjectNo, currency, billValue, itemValue,ajValue);
			}

			if (!StringUtils.isNullBlank(projectNos))
			{
				projectNos = projectNos.substring(0, projectNos.length() - 1);
			}
			// 供应商清帐
			if (judgeSupplierIsAllClear(billClearPayment, supplier, contractNos, projectNos, bukrs))
			{
				getSupplierClearVoucherItem(billClearPayment, clearVoucher, voucherItemList, contractNos, projectNos, clearedContractNo, clearedProjectNo, currency, billValue, itemValue,ajValue, clearVoucher.getCompanycode());
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
						VoucherItem supplierVoucherItem = getProfitAndLossVoucherItem(billClearPayment, subtractVlaue2, currency,taxCode, "1");
						supplierVoucherItem.setRownumber("3");
						supplierVoucherItem.setVoucher(voucher);
						voucher.getVoucherItem().add(supplierVoucherItem);
	
						VoucherItem profOrLossVoucherItem = getProfitAndLossVoucherItem(billClearPayment, subtractVlaue2, currency,taxCode, "2");
						profOrLossVoucherItem.setRownumber("4");
						profOrLossVoucherItem.setVoucher(voucher);
						voucher.getVoucherItem().add(profOrLossVoucherItem);
					}
				
			}
			
			// 判断是否外币,无调整金
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
						subtractVlaue2 = sumUnoffsetamountBwb.subtract(sumoffsetAmountBwb);
					}
					//款的一边全清
					if(!clearF){
//						subtractVlaue2 = billAmount.divide(sumbillAmount,5,BigDecimal.ROUND_HALF_EVEN).multiply(sumclearAmount).subtract(collectAmount);
						subtractVlaue2 = sumclearAmountBwb.subtract(sumUnreceivableamouBwb);
					}
				}	
					if (subtractVlaue2.abs().compareTo(new BigDecimal(0)) != 0)
					{
						Voucher voucher2 = getVoucher(billClearPayment);
						voucher2.setAgkoa(" ");
						voucher2.setAgkon(" ");
						voucher2.setBstat(" ");
						voucher2.setGsber(" ");
						voucher2.setFlag(" ");
						voucher2.setVouchertype("SA");						
						voucher2.setValut(" ");
						VoucherItem supplierVoucherItem = getProfitAndLossVoucherItem(billClearPayment, subtractVlaue2, currency,taxCode, "1");
						supplierVoucherItem.setVoucher(voucher2);
						voucherItemList.add(supplierVoucherItem);
	
						VoucherItem profOrLossVoucherItem = getProfitAndLossVoucherItem(billClearPayment, subtractVlaue2, currency,taxCode, "2");
						profOrLossVoucherItem.setVoucher(voucher2);
						voucherItemList.add(profOrLossVoucherItem);
						voucher2.setVoucherItem(voucherItemList);
						//判断是否已经生成损益凭证，
						Voucher voucher3=null;
						List<Voucher> li=voucherHibernateDao.getVoucherByBusinessId2(billClearPayment.getBillclearid()," ");
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
			List<Voucher> li=voucherHibernateDao.getVoucherByBusinessId2(billClearPayment.getBillclearid(),"A");
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
	 * 取得taxCode
	 * @param customSingleClear
	 * @return
	 */
	public  String getTaxCode(BillClearPayment billClearPayment){
		
		//合同号列表
		List<String> contractNoList = new ArrayList<String>();
		String projectNo =" ";
		String contractNo = " ";
		// 重新计算未清金额(未收款、未付款等)
		Set<BillClearItemPay> billClearItemPays = billClearPayment.getBillClearItemPay();
		Set<BillInPayment> billInPayments = billClearPayment.getBillInPayment();
		for(BillClearItemPay billClearItemPay:billClearItemPays){
			
			
			if(billClearItemPay.getUnreceivedamount().compareTo(billClearItemPay.getClearbillamount()) !=0){
				projectNo = billClearItemPay.getProject_no();
			}
			contractNo = billClearItemPay.getContract_no();
			contractNoList.add(contractNo);			
		}
		
		for(BillInPayment billInPayment:billInPayments){
			
			if(billInPayment.getUnoffsetamount().compareTo(billInPayment.getNowclearamount()) !=0){
				projectNo = billInPayment.getProject_no();
			}
			contractNo = billInPayment.getContract_no();
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
	 * 取得Voucher主數據。
	 * 
	 * @param billClearPayment
	 * @param deptId
	 * @return
	 */
	private Voucher getVoucher(BillClearPayment billClearPayment)
	{
		Voucher voucher = new Voucher();
		String fiperiod = "";
		String fiyear = "";
		String bukrs = this.voucherService.getCompanyIDByDeptID(billClearPayment.getDeptid());
		// 添加凭证抬头数据
		voucher.setBusinessid(billClearPayment.getBillclearid());
		voucher.setBusinesstype("04"); // 票清付款
		voucher.setVouchertype("DR");
		voucher.setCompanycode(bukrs);
		voucher.setExchangerate(new BigDecimal(0));
		voucher.setVoucherclass("1");
		String accountdate = billClearPayment.getAccountdate().replace("-", "");
		voucher.setCheckdate(accountdate);
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
		voucher.setVoucherdate(billClearPayment.getVoucherdate().replace("-", "")); // 凭证日期
		voucher.setVouchertext(billClearPayment.getText()); // 凭证抬头文本

		return voucher;
	}

	/**
	 * 清除分配
	 * 
	 * @param billClearPayment
	 * @param billClearItemPays
	 */
	public void _clearAssign(BillClearPayment billClearPayment, Set<BillClearItemPay> billClearItemPays, Set<BillInPayment> billInPaymentmodifyItems)
	{
		String billClearId = billClearPayment.getBillclearid();
		this.billInPaymentJdbcDao.deletes(billClearId);
	}

	/**
	 * 
	 * 删除子对象
	 * 
	 * @param billClearPayment
	 */
	public void _deleteSubObject(BillClearPayment billClearPayment)
	{
		if (!StringUtils.isNullBlank(billClearPayment.getBillclearid()))
		{
			this.billClearPaymentHibernateDao.deleteById("YBILLCLEARITEM", "BILLCLEARID", billClearPayment.getBillclearid());
			this.billClearPaymentHibernateDao.deleteById("YBILLINPAYMENT", "BILLCLEARID", billClearPayment.getBillclearid());
		}

	}

	/**
	 * 
	 * 保存
	 * 
	 * @param billClearPayment
	 */
	public void _saveOrUpdate(BillClearPayment billClearPayment)
	{
		if (StringUtils.isNullBlank(billClearPayment.getBillclearno()))
		{
			String billclearno = NumberService.getNextObjectNumber("billclearnoPayment", billClearPayment);
			billClearPayment.setBillclearno(billclearno);
		}
		_deleteSubObject(billClearPayment);
		_saveOrUpdateBillClearPayment(billClearPayment);

	}

	/**
	 * 保存
	 * 
	 * @param billClearPayment
	 */
	public void _saveOrUpdateBillClearPayment(BillClearPayment billClearPayment)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(billClearPayment);
		// 对id赋空值，使之执行insert操作(适应复制创建场景)

		Set<BillClearItemPay> billClearItemPaySet = billClearPayment.getBillClearItemPay();
		Set<BillClearItemPay> newBillClearItemPaySet = null;
		if (null != billClearItemPaySet)
		{
			newBillClearItemPaySet = new HashSet();
			Iterator<BillClearItemPay> itBillClearItemPay = billClearItemPaySet.iterator();
			while (itBillClearItemPay.hasNext())
			{
				BillClearItemPay billClearItemPay = (BillClearItemPay) itBillClearItemPay.next();
				billClearItemPay.setBillclearitemid(null);
				newBillClearItemPaySet.add(billClearItemPay);
			}
		}
		billClearPayment.setBillClearItemPay(newBillClearItemPaySet);

		Set<BillInPayment> billInPaymentSet = billClearPayment.getBillInPayment();
		Set<BillInPayment> newBillInPaymentSet = null;
		if (null != billInPaymentSet)
		{
			newBillInPaymentSet = new HashSet();
			Iterator<BillInPayment> itBillInPayment = billInPaymentSet.iterator();
			while (itBillInPayment.hasNext())
			{
				BillInPayment billInPayment = (BillInPayment) itBillInPayment.next();
				billInPayment.setBillnpaymentid(null);
				newBillInPaymentSet.add(billInPayment);
			}
		}
		billClearPayment.setBillInPayment(newBillInPaymentSet);

		billClearPaymentHibernateDao.saveOrUpdate(billClearPayment);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(billClearPayment);
	}

	/**
	 * 更新所有相关的数据为SAP已清状态。
	 * 
	 * @param supplierSinglClear
	 * @return
	 */
	public void updateAllDataIsSAPCleared(BillClearPayment billClearPayment)
	{
		String businessId = billClearPayment.getBillclearid();
		String deptId = billClearPayment.getDeptid();
		String bukrs = this.voucherService.getCompanyIDByDeptID(deptId);
		String supplier = billClearPayment.getSupplier();
		// 单据本次是否可全清
		String contractNos = "";
		String projectNos = "";
		String vendortitleidsSqlIn = "";
		String paymentItemIdsSqlIn = "";

		if (judgeIsAllClear(billClearPayment))
		{
			vendortitleidsSqlIn = "select TITLEID from YBILLCLEARITEM where BILLCLEARID='" + businessId + "'";
			paymentItemIdsSqlIn = "select PAYMENTITEMID from YBILLINPAYMENT where BILLCLEARID='" + businessId + "'";
			this.vendorTitleJdbcDao.updateIsCleared(vendortitleidsSqlIn, cleared.sapIsCleared);
			this.paymentItemJdbcDao.updateIsCleared(paymentItemIdsSqlIn, cleared.sapIsCleared);
		}
		else
		{
			// 合同
			List<String> clearedContractNo = judgeContractIsAllClear(billClearPayment);
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

				vendortitleidsSqlIn = "select TITLEID from YBILLCLEARITEM where BILLCLEARID='" + businessId + "' and CONTRACT_NO in(" + contractNos + ")";
				paymentItemIdsSqlIn = "select PAYMENTITEMID from YBILLINPAYMENT  where BILLCLEARID='" + businessId + "' and CONTRACT_NO in(" + contractNos + ")";
				this.vendorTitleJdbcDao.updateIsCleared(vendortitleidsSqlIn, cleared.sapIsCleared);
				this.paymentItemJdbcDao.updateIsCleared(paymentItemIdsSqlIn, cleared.sapIsCleared);

				this.vendorTitleJdbcDao.updateIsClearedByContractNo(contractNos, cleared.sapIsCleared);
				this.paymentItemJdbcDao.updateIsClearedByContractNo(contractNos, cleared.sapIsCleared);
			}

			// 立项下
			List<String> clearedProjectNo = judgeProjectIsAllClear(billClearPayment, clearedContractNo, contractNos);
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

				vendortitleidsSqlIn = "select TITLEID from YBILLCLEARITEM where BILLCLEARID='" + businessId + "' and PROJECT_NO in(" + projectNos + ") ";
				if (!StringUtils.isNullBlank(contractNos))
					vendortitleidsSqlIn += " and CONTRACT_NO not in(" + contractNos + ")";

				paymentItemIdsSqlIn = "select PAYMENTITEMID from YBILLINPAYMENT  where BILLCLEARID='" + businessId + "' and PROJECT_NO in(" + projectNos + ") ";
				if (!StringUtils.isNullBlank(contractNos))
					paymentItemIdsSqlIn += " and CONTRACT_NO not in(" + contractNos + ")";
				this.vendorTitleJdbcDao.updateIsCleared(vendortitleidsSqlIn, cleared.sapIsCleared);
				this.paymentItemJdbcDao.updateIsCleared(paymentItemIdsSqlIn, cleared.sapIsCleared);

				this.vendorTitleJdbcDao.updateIsClearedByProjectNo(contractNos, projectNos, cleared.sapIsCleared);
				this.paymentItemJdbcDao.updateIsClearedByProjectNo(contractNos, projectNos, cleared.sapIsCleared);
			}
			// 供应商清帐
			if (judgeSupplierIsAllClear(billClearPayment, supplier, contractNos, projectNos, bukrs))
			{
				vendortitleidsSqlIn = "select TITLEID from YBILLCLEARITEM where BILLCLEARID='" + businessId + "'";
				if (!StringUtils.isNullBlank(projectNos))
					vendortitleidsSqlIn += " and PROJECT_NO not in(" + projectNos + ")";
				if (!StringUtils.isNullBlank(contractNos))
					vendortitleidsSqlIn += " and CONTRACT_NO not in(" + contractNos + ")";

				paymentItemIdsSqlIn = "select PAYMENTITEMID from YBILLINPAYMENT  where BILLCLEARID='" + businessId + "'";
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
	 * 根据退款ID获得票清付款业务对象
	 * 
	 * @param billClearId
	 * @return
	 */
	public BillClearPayment getBillClearPaymentById(String billClearId)
	{
//		return this.billClearPaymentHibernateDao.get(billClearId);
		return billClearPaymentJdbcDao.getBillClearPaymentById(billClearId);
	}

	/**
	 * 判断付款分配行项目是否已结清。
	 * 
	 * @param billclearid
	 */
	public void updatePaymentItemIsCleared(String billclearid)
	{
		BillClearPayment billClearPayment = this._getEntityCopy(billclearid);
		Set<BillInPayment> billInPayments = billClearPayment.getBillInPayment();
		for (Iterator<BillInPayment> iter = billInPayments.iterator(); iter.hasNext();)
		{
			BillInPayment billInPayment = (BillInPayment) iter.next();
			String paymentitemid = billInPayment.getPaymentitemid();
			// 付款单已审批完的
			BigDecimal clearedpaymentamount = this.paymentItemService.getSumClearAmount(paymentitemid.trim(), BusinessState.ALL_SUBMITPASS);
			ImportPaymentItem paymentItem = this.paymentItemService.get(paymentitemid);
			// 预收票款 = 审批完的清票 + 本次清票 则结清 paymentItem.getPrepayamount()
			//改为货款金额 = 审批完的清票 + 本次清票 则结清
			if (paymentItem.getAssignamount().compareTo(clearedpaymentamount.add(billInPayment.getNowclearamount())) == 0)
			{
				this.paymentItemService.updatePaymentItemIsCleared(paymentitemid, Constants.cleared.isCleared);
				VendorTitle title = this.getVendorTitle(paymentitemid,"1,4");
				this.vendorTitleJdbcDao.updateVendorTitleIsCleared(title.getVendortitleid(), Constants.cleared.isCleared);
			}
		}
	}
	
	/**
	 * 通过paymentitemid找到未清表的款
	 * @param collectitemid
	 * @param voucherclass
	 * @return
	 */
	public VendorTitle getVendorTitle(String paymentitemid,String voucherclass){
		VoucherItem voucheritem = this.voucherItemJdbcDao.getVoucherItem(paymentitemid, voucherclass);
		String rownumber = StringUtils.leftPad(voucheritem.getRownumber(), 3, '0');				
		VendorTitle title = vendorTitleJdbcDao.getByVoucherNo(voucheritem.getVoucher().getCompanycode(), voucheritem.getVoucher().getVoucherno(), voucheritem.getVoucher().getFiyear(), rownumber);
		return title;
	}

	/**
	 * 判断供应商未清发票是否已结清。
	 * 
	 * @param billclearid
	 */
	public void updateVendorTitleIsCleared(String billclearid)
	{
		BillClearPayment billClearPayment = this._getEntityCopy(billclearid);
		Set<BillClearItemPay> billClearItemPays = billClearPayment.getBillClearItemPay();
		Iterator<BillClearItemPay> it = billClearItemPays.iterator();
		while (it.hasNext())
		{
			BillClearItemPay billClearItemPay = (BillClearItemPay) it.next();

			String invoice = billClearItemPay.getInvoice();
			String vendortitleid = billClearItemPay.getTitleid();

			// 当前单据，票清帐金额总计。
			BigDecimal billClearAmount = billClearItemPay.getClearbillamount();
			// 发票总金额
			BigDecimal billTotAmount = billClearItemPay.getReceivableamount();
			// 票已清金额。
			BigDecimal billsumClearedAmount = this.vendorService.getSumClearAmount(invoice.trim(), BusinessState.ALL_SUBMITPASS);

			// 票已清金额+当前单据上清票金额 = 票总金额，则票已经结清。
			if (billsumClearedAmount.add(billClearAmount).compareTo(billTotAmount) == 0)
			{
				log.debug("发票号:" + invoice + "，票已清金额+当前单据上清票金额 = 票总金额:" + billsumClearedAmount + "+" + billClearAmount + " = " + billTotAmount);
				this.vendorService.updateVendorTitleIsCleared(vendortitleid, Constants.cleared.isCleared);
			}

		}
	}

	/**
	 * 处理本次可以全清帐
	 * 
	 * @param supplierSinglClear
	 * @param currency
	 * @param voucher
	 * @param voucherItemList
	 */
	private void getClearVoucherItem(BillClearPayment billClearPayment, Voucher clearVoucher, Set<VoucherItem> voucherItemList, String currency,BigDecimal ajValue,String taxCode)
	{
		// 得到票的本位币之和
		BigDecimal billValue = new BigDecimal("0");
		// 得到分配项的本位币之和
		BigDecimal itemValue = new BigDecimal("0");
		// 分配项之和和票之和的差
		BigDecimal subtractVlaue = new BigDecimal("0");
		
	
		
		
		Set<BillClearItemPay> billClearItemPays = billClearPayment.getBillClearItemPay();
		Set<BillInPayment> billInPayments = billClearPayment.getBillInPayment();
		for (BillClearItemPay billClearItemPay : billClearItemPays)
		{
			String invoice = billClearItemPay.getInvoice();
			ClearVoucherItemStruct clearVoucherItemStruct = vendorTitleJdbcDao.getCurrectBillInfo(invoice);
			VoucherItem voucherItem = getClearVoucherItem(clearVoucherItemStruct);

			billValue = billValue.add(clearVoucherItemStruct.getBwbje());
			voucherItem.setVoucher(clearVoucher);
			voucherItemList.add(voucherItem);
		}

		for (BillInPayment billInPayment : billInPayments)
		{
//			Voucher oldVoucher = this.voucherService.getVoucher(billInPayment.getPaymentid());
//			Set<VoucherItem> oldVoucherItem = oldVoucher.getVoucherItem();
//			Iterator<VoucherItem> oldVoucherItemIt = oldVoucherItem.iterator();
//			while (oldVoucherItemIt.hasNext())
//			{
//				VoucherItem oldItem = oldVoucherItemIt.next();
//
//				if (oldItem.getVoucher().getBusinessid().equals(billInPayment.getPaymentid()) && oldItem.getBusinessitemid().equals(billInPayment.getPaymentitemid()))
//				{
//					ClearVoucherItemStruct clearVoucherItemStruct = new ClearVoucherItemStruct();
//					clearVoucherItemStruct.setRowno(oldItem.getRownumber());
//					clearVoucherItemStruct.setVoucherno(billInPayment.getVoucherno());
//					clearVoucherItemStruct.setYear(oldItem.getVoucher().getFiyear());
//					VoucherItem voucherItem = getClearVoucherItem(clearVoucherItemStruct);
//					itemValue = itemValue.add(oldItem.getAmount2());
//
//					voucherItem.setVoucher(clearVoucher);
//					voucherItemList.add(voucherItem);
//				}
//			}
			VoucherItem voucheritem2 = this.voucherItemJdbcDao.getVoucherItem(billInPayment.getPaymentitemid(), "1,4");
			if(null !=voucheritem2){
				ClearVoucherItemStruct clearVoucherItemStruct = new ClearVoucherItemStruct();
				clearVoucherItemStruct.setRowno(voucheritem2.getRownumber());
				clearVoucherItemStruct.setVoucherno(billInPayment.getVoucherno());
				clearVoucherItemStruct.setYear(voucheritem2.getVoucher().getFiyear());
				VoucherItem voucherItem = getClearVoucherItem(clearVoucherItemStruct);
				itemValue = itemValue.add(voucheritem2.getAmount2());

				voucherItem.setVoucher(clearVoucher);
				voucherItemList.add(voucherItem);
			}
		}

		// 判断是否外币
		if (!"CNY".equalsIgnoreCase(currency))
		{
			subtractVlaue = itemValue.subtract(billValue);
			subtractVlaue = subtractVlaue.add(ajValue);
			if (subtractVlaue != new BigDecimal("0"))
			{
				VoucherItem supplierVoucherItem = getProfitAndLossVoucherItem(billClearPayment, subtractVlaue, currency,taxCode, "1");
				supplierVoucherItem.setVoucher(clearVoucher);
				voucherItemList.add(supplierVoucherItem);

				VoucherItem profOrLossVoucherItem = getProfitAndLossVoucherItem(billClearPayment, subtractVlaue, currency,taxCode, "2");
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
	public VoucherItem getProfitAndLossVoucherItem(BillClearPayment billClearPayment, BigDecimal subtractVlaue, String currency, String taxCode,String strType)
	{
		VoucherItem voucherItem = new VoucherItem();
		// 科目说明
		String bukrs = this.voucherService.getCompanyIDByDeptID(billClearPayment.getDeptid());
		String subject = this.customerRefundItemJdbcDao.getSubjectBySuppliers(billClearPayment.getSupplier(),bukrs);
		String subjectname = this.customerRefundItemJdbcDao.getSubjectNameById(subject,bukrs);
		voucherItem.setControlaccount(subject);
		voucherItem.setCaremark(subjectname);
		voucherItem.setTaxcode(taxCode);
		if (strType.equals("1"))
		{
			voucherItem.setRownumber("1");
			// 科目
			voucherItem.setSubject(billClearPayment.getSupplier());
			
			String Subjectdescribe = this.voucherService.getSupplierDescByCustomerId(billClearPayment.getSupplier(), bukrs);
			voucherItem.setSubjectdescribe(Subjectdescribe);

			if (subtractVlaue.signum() == 1)
			{
				// 记帐码
				voucherItem.setCheckcode("25");
				voucherItem.setDebitcredit("S");
			}
			else
			{
				// 记帐码
				voucherItem.setCheckcode("38");
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
		// 部门 业务范围
		voucherItem.setDepid(this.sysDeptJdbcDao.getDeptCodeById(billClearPayment.getDeptid()));
		// 文本
		voucherItem.setText(billClearPayment.getText());

		return voucherItem;
	}

	/**
	 * 处理供应商下清帐凭证行项目.
	 * 
	 * @param billClearPayment
	 * @param clearVoucher
	 * @param voucherItemList
	 * @param contractNos
	 * @param projectNos
	 * @param clearedContractNo
	 * @param clearedProjectNo
	 * @param oldVoucher
	 */
	private void getSupplierClearVoucherItem(BillClearPayment billClearPayment, Voucher clearVoucher, Set<VoucherItem> voucherItemList, String contractNos, String projectNos, List<String> clearedContractNo, List<String> clearedProjectNo, String currency, BigDecimal billValue, BigDecimal itemValue,BigDecimal ajValue, String bukrs)
	{
		String supplier = billClearPayment.getSupplier();
		// 分配项之和和票之和的差
		BigDecimal subtractVlaue = new BigDecimal("0");
		Set<BillClearItemPay> billClearItemPays = billClearPayment.getBillClearItemPay();
		Set<BillInPayment> billInPayments = billClearPayment.getBillInPayment();
		// 得到之前合同的票信息
		List<ClearVoucherItemStruct> clearVoucherItemStructList = this.vendorTitleJdbcDao.getSupplierBillInfo(supplier, contractNos, projectNos, bukrs);
		for (ClearVoucherItemStruct clearVoucherItemStruct : clearVoucherItemStructList)
		{
			VoucherItem voucherItem = getClearVoucherItem(clearVoucherItemStruct);
			voucherItem.setVoucher(clearVoucher);
			billValue = billValue.add(clearVoucherItemStruct.getBwbje());
			voucherItemList.add(voucherItem);
		}

		// 得到本次票的行项目信息
		Iterator<BillClearItemPay> billClearItemPaysIt = billClearItemPays.iterator();
		while (billClearItemPaysIt.hasNext())
		{
			BillClearItemPay billClearItemPay = billClearItemPaysIt.next();
			if (clearedContractNo.contains(billClearItemPay.getContract_no()))
			{
				log.debug("合同号:" + billClearItemPay.getContract_no() + "已经存在已清合同列表," + contractNos);
			}
			if (clearedProjectNo.contains(billClearItemPay.getProject_no()))
			{
				log.debug("合同号:" + billClearItemPay.getProject_no() + "已经存在已清合同列表," + projectNos);
			}

			if (!clearedContractNo.contains(billClearItemPay.getContract_no()) && !clearedProjectNo.contains(billClearItemPay.getProject_no()))
			{
//				ClearVoucherItemStruct clearVoucherItemStruct = this.vendorTitleJdbcDao.getCurrectBillInfo(billClearItemPay.getInvoice());
//				VoucherItem voucherItem = getClearVoucherItem(clearVoucherItemStruct);
//				voucherItem.setVoucher(clearVoucher);
//				billValue = billValue.add(clearVoucherItemStruct.getBwbje());
//				voucherItemList.add(voucherItem);
				
				ClearVoucherItemStruct clearVoucherItemStruct = new ClearVoucherItemStruct();
				VendorTitle vendorTitle = this.vendorTitleJdbcDao.getByVendortitleid(billClearItemPay.getTitleid());				 
				clearVoucherItemStruct.setRowno(vendorTitle.getBuzei());
				clearVoucherItemStruct.setVoucherno(billClearItemPay.getVoucherno());
				clearVoucherItemStruct.setYear(vendorTitle.getGjahr());
				VoucherItem voucherItem = getClearVoucherItem(clearVoucherItemStruct);						
				billValue = billValue.add(vendorTitle.getBwbje());	
				voucherItem.setVoucher(clearVoucher);
				voucherItemList.add(voucherItem);
			}
		}
		
		//得到之前汇损，或调整金的voucheritem
		List<VoucherItem> voucherItems = voucherItemJdbcDao.getVoucherItems(supplier, "");
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
		
		//取得SAP手工做账的被部分清的汇损凭证
		List<ClearVoucherItemStruct> clearVoucherItemStructList2 = this.vendorTitleJdbcDao.getSupplierVoucherInfo(supplier, contractNos, projectNos, bukrs);
		for (ClearVoucherItemStruct clearVoucherItemStruct : clearVoucherItemStructList2)
		{
			VoucherItem voucherItem = voucherItemJdbcDao.getVoucherItem(clearVoucherItemStruct.getVoucherno(), bukrs, clearVoucherItemStruct.getYear(), clearVoucherItemStruct.getRowno());
			//如果没有数据，说明只有是SAP手工做账，如果有数据是外围做账，不在这边处理，前面(得到之前汇损，或调整金的voucheritem)已做好了。
			if(null == voucherItem ){
				VoucherItem voucherItem2 = getClearVoucherItem(clearVoucherItemStruct);
				voucherItem2.setVoucher(clearVoucher);
				itemValue = itemValue.add(clearVoucherItemStruct.getBwbje());
				voucherItemList.add(voucherItem2);
			}
		}
		
		// 得到之前的款分配信息
		List<ClearVoucherItemStruct> clearVoucherItemStructs2 = this.vendorTitleJdbcDao.getSupplierPayItemInfo(supplier, contractNos, projectNos, bukrs);
		for (ClearVoucherItemStruct clearVoucherItemStruct : clearVoucherItemStructs2)
		{
			VoucherItem voucherItem = getClearVoucherItem(clearVoucherItemStruct);
			voucherItem.setVoucher(clearVoucher);
			itemValue = itemValue.add(clearVoucherItemStruct.getBwbje());
			voucherItemList.add(voucherItem);
		}
		//退款的行项目信息
		List<ClearVoucherItemStruct> clearVoucherItemStructList3 = this.vendorTitleJdbcDao.getRefundItemBySupplierNo(supplier, BusinessState.ALL_BILLCLEAR_PAIDUP, projectNos, contractNos,billClearPayment.getDeptid());
		for (ClearVoucherItemStruct clearVoucherItemStruct : clearVoucherItemStructList3)
		{
			VoucherItem voucherItem = getClearVoucherItem(clearVoucherItemStruct);
			billValue = billValue.add(clearVoucherItemStruct.getBwbje());
			voucherItem.setVoucher(clearVoucher);
			voucherItemList.add(voucherItem);
		}
		// 得到本次分配项的行项目信息
		Iterator<BillInPayment> billInPaymentsIt = billInPayments.iterator();
		while (billInPaymentsIt.hasNext())
		{
			BillInPayment billInPayment = billInPaymentsIt.next();
			if (clearedContractNo.contains(billInPayment.getContract_no()))
			{
				log.debug("合同号:" + billInPayment.getContract_no() + "已经存在已清合同列表," + contractNos);
			}
			if (clearedProjectNo.contains(billInPayment.getProject_no()))
			{
				log.debug("合同号:" + billInPayment.getProject_no() + "已经存在已清合同列表," + projectNos);
			}

			if (!clearedContractNo.contains(billInPayment.getContract_no()) && !clearedProjectNo.contains(billInPayment.getProject_no()))
			{
				Voucher oldVoucher = this.voucherService.getVoucherByBusinessId(billInPayment.getPaymentid(), "1");
				Set<VoucherItem> oldVoucherItem = oldVoucher.getVoucherItem();
				Iterator<VoucherItem> payVoucherItemit = oldVoucherItem.iterator();
				while (payVoucherItemit.hasNext())
				{
					VoucherItem oldItem = payVoucherItemit.next();
					if (oldItem.getVoucher().getBusinessid().equals(billInPayment.getPaymentid()) && oldItem.getBusinessitemid().equals(billInPayment.getPaymentitemid()))
					{
						ClearVoucherItemStruct clearVoucherItemStruct = new ClearVoucherItemStruct();
						clearVoucherItemStruct.setRowno(oldItem.getRownumber());
						clearVoucherItemStruct.setVoucherno(billInPayment.getVoucherno());
						clearVoucherItemStruct.setYear(oldItem.getVoucher().getFiyear());
						VoucherItem voucherItem = getClearVoucherItem(clearVoucherItemStruct);
						voucherItem.setVoucher(clearVoucher);
						itemValue = itemValue.add(oldItem.getAmount2());
						voucherItemList.add(voucherItem);
					}
				}
			}
		}
		// 判断是否外币,有调整金
		if (!"CNY".equalsIgnoreCase(currency))
		{
			subtractVlaue = itemValue.subtract(billValue);
			subtractVlaue = subtractVlaue.add(ajValue);
			if (subtractVlaue.abs().compareTo(new BigDecimal(0)) != 0)
			{
				VoucherItem supplierVoucherItem = getProfitAndLossVoucherItem(billClearPayment, subtractVlaue, currency,"", "1");
				supplierVoucherItem.setVoucher(clearVoucher);
				voucherItemList.add(supplierVoucherItem);

				VoucherItem profOrLossVoucherItem = getProfitAndLossVoucherItem(billClearPayment, subtractVlaue, currency,"", "2");
				profOrLossVoucherItem.setVoucher(clearVoucher);
				voucherItemList.add(profOrLossVoucherItem);
			}
		}
	}

	/**
	 * 处理立项下清帐凭证行项目.
	 * 
	 * @param billClearPayment
	 * @param clearVoucher
	 * @param voucherItemList
	 * @param contractNos
	 * @param clearedContractNo
	 * @param clearedProjectNo
	 * @param currency
	 * @param billValue
	 * @param itemValue
	 * @return
	 */
	private String getProjectClearVoucherItem(BillClearPayment billClearPayment, Voucher clearVoucher, Set<VoucherItem> voucherItemList, String contractNos, List<String> clearedContractNo, List<String> clearedProjectNo, String currency, BigDecimal billValue, BigDecimal itemValue,BigDecimal ajValue)
	{
		String projectNos = "";
		// 分配项之和和票之和的差
		BigDecimal subtractVlaue = new BigDecimal("0");
		Set<BillClearItemPay> billClearItemPays = billClearPayment.getBillClearItemPay();
		Set<BillInPayment> billInPayments = billClearPayment.getBillInPayment();
		// 循环所有立項号。
		for (String projectNo : clearedProjectNo)
		{
			projectNos = projectNos + "'" + projectNo + "',";
			// 得到之前合同的票信息
			List<ClearVoucherItemStruct> clearVoucherItemStructs = this.vendorTitleJdbcDao.getProjectBillInfo(billClearPayment.getSupplier(), projectNo, contractNos);
			for (ClearVoucherItemStruct clearVoucherItemStruct : clearVoucherItemStructs)
			{
				VoucherItem voucherItem = getClearVoucherItem(clearVoucherItemStruct);
				voucherItem.setVoucher(clearVoucher);
				billValue = billValue.add(clearVoucherItemStruct.getBwbje());
				voucherItemList.add(voucherItem);
			}
			//得到之前汇损，或调整金的voucheritem
			List<VoucherItem> voucherItems = voucherItemJdbcDao.getVoucherItems(billClearPayment.getSupplier(), projectNo);
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
			// 得到本次票的行项目信息
			Iterator<BillClearItemPay> billClearItemPaysIt = billClearItemPays.iterator();
			while (billClearItemPaysIt.hasNext())
			{
				BillClearItemPay billClearItemPay = billClearItemPaysIt.next();
				if (clearedContractNo.contains(billClearItemPay.getContract_no()))
				{
					log.debug("合同号:" + billClearItemPay.getContract_no() + "已经存在已清合同列表," + contractNos);
				}
				if (projectNo.equals(billClearItemPay.getProject_no()) && !clearedContractNo.contains(billClearItemPay.getContract_no()))
				{
//					ClearVoucherItemStruct clearVoucherItemStruct = this.vendorTitleJdbcDao.getCurrectBillInfo(billClearItemPay.getInvoice());
//					VoucherItem voucherItem = getClearVoucherItem(clearVoucherItemStruct);
//					voucherItem.setVoucher(clearVoucher);
//					billValue = billValue.add(clearVoucherItemStruct.getBwbje());
//					voucherItemList.add(voucherItem);
					
					ClearVoucherItemStruct clearVoucherItemStruct = new ClearVoucherItemStruct();
					VendorTitle vendorTitle = this.vendorTitleJdbcDao.getByVendortitleid(billClearItemPay.getTitleid());				 
					clearVoucherItemStruct.setRowno(vendorTitle.getBuzei());
					clearVoucherItemStruct.setVoucherno(billClearItemPay.getVoucherno());
					clearVoucherItemStruct.setYear(vendorTitle.getGjahr());
					VoucherItem voucherItem = getClearVoucherItem(clearVoucherItemStruct);						
					billValue = billValue.add(vendorTitle.getBwbje());	
					voucherItem.setVoucher(clearVoucher);
					voucherItemList.add(voucherItem);
				}
			}
			//退款的行项目信息
			List<ClearVoucherItemStruct> clearVoucherItemStructList3 = this.vendorTitleJdbcDao.getRefundItemBySupplierNoAndProjectNo(billClearPayment.getSupplier(), BusinessState.ALL_BILLCLEAR_PAIDUP, projectNo, contractNos);
			for (ClearVoucherItemStruct clearVoucherItemStruct : clearVoucherItemStructList3)
			{
				VoucherItem voucherItem = getClearVoucherItem(clearVoucherItemStruct);
				billValue = billValue.add(clearVoucherItemStruct.getBwbje());
				voucherItem.setVoucher(clearVoucher);
				voucherItemList.add(voucherItem);
			}
			// 得到之前的款分配信息
			List<ClearVoucherItemStruct> clearVoucherItemStructs2 = this.vendorTitleJdbcDao.getProjectPayItemInfo(billClearPayment.getSupplier(),projectNo, contractNos);
			for (ClearVoucherItemStruct clearVoucherItemStruct : clearVoucherItemStructs2)
			{
				VoucherItem voucherItem = getClearVoucherItem(clearVoucherItemStruct);
				voucherItem.setVoucher(clearVoucher);
				itemValue = itemValue.add(clearVoucherItemStruct.getBwbje());
				voucherItemList.add(voucherItem);
			}

			// 得到本次分配项的行项目信息
			Iterator<BillInPayment> billInPaymentsIt = billInPayments.iterator();
			while (billInPaymentsIt.hasNext())
			{
				BillInPayment billInPayment = billInPaymentsIt.next();
				if (clearedContractNo.contains(billInPayment.getContract_no()))
				{
					log.debug("合同号:" + billInPayment.getContract_no() + "已经存在已清合同列表," + contractNos);
				}
				if (projectNo.equals(billInPayment.getProject_no()) && !clearedContractNo.contains(billInPayment.getContract_no()))
				{
					Voucher oldVoucher = this.voucherService.getVoucherByBusinessId(billInPayment.getPaymentid(), "1");
					Set<VoucherItem> oldVoucherItem = oldVoucher.getVoucherItem();
					Iterator<VoucherItem> payVoucherItemit = oldVoucherItem.iterator();
					while (payVoucherItemit.hasNext())
					{
						VoucherItem oldItem = payVoucherItemit.next();
						if (oldItem.getVoucher().getBusinessid().equals(billInPayment.getPaymentid()) && oldItem.getBusinessitemid().equals(billInPayment.getPaymentitemid()))
						{
							ClearVoucherItemStruct clearVoucherItemStruct = new ClearVoucherItemStruct();
							clearVoucherItemStruct.setRowno(oldItem.getRownumber());
							clearVoucherItemStruct.setVoucherno(billInPayment.getVoucherno());
							clearVoucherItemStruct.setYear(oldItem.getVoucher().getFiyear());
							VoucherItem voucherItem = getClearVoucherItem(clearVoucherItemStruct);
							itemValue = itemValue.add(oldItem.getAmount2());
							voucherItem.setVoucher(clearVoucher);
							voucherItemList.add(voucherItem);
						}
					}
				}
			}
			
			// 判断是否外币,有调整金
			if (!"CNY".equalsIgnoreCase(currency))
			{
				subtractVlaue = itemValue.subtract(billValue);
				subtractVlaue = subtractVlaue.add(ajValue);
				if (subtractVlaue.abs().compareTo(new BigDecimal(0)) != 0)
				{
					VoucherItem supplierVoucherItem = getProfitAndLossVoucherItem(billClearPayment, subtractVlaue, currency,projectNo, "1");
					supplierVoucherItem.setVoucher(clearVoucher);
					voucherItemList.add(supplierVoucherItem);

					VoucherItem profOrLossVoucherItem = getProfitAndLossVoucherItem(billClearPayment, subtractVlaue, currency,projectNo, "2");
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
	 * @param billClearPayment
	 * @param clearedContractNo
	 * @param clearVoucher
	 * @param voucherItemList
	 * @param currency
	 * @param billValue
	 * @param itemValue
	 * @return
	 */
	private String getContractClearVoucherItem(BillClearPayment billClearPayment, List<String> clearedContractNo, Voucher clearVoucher, Set<VoucherItem> voucherItemList, String currency, BigDecimal billValue, BigDecimal itemValue,BigDecimal ajValue)
	{
		String contractNos = "";
		// 分配项之和和票之和的差
		BigDecimal subtractVlaue = new BigDecimal("0");
		Set<BillClearItemPay> billClearItemPays = billClearPayment.getBillClearItemPay();
		Set<BillInPayment> billInPayments = billClearPayment.getBillInPayment();
		// 循环所有合同号。
		for (String conntractNo : clearedContractNo)
		{
			contractNos = contractNos + "'" + conntractNo + "',";
			// 得到之前合同的票信息
			List<ClearVoucherItemStruct> clearVoucherItemStructList = this.vendorTitleJdbcDao.getContractBillInfo(billClearPayment.getSupplier(),conntractNo);
			for (ClearVoucherItemStruct clearVoucherItemStruct : clearVoucherItemStructList)
			{
				VoucherItem voucherItem = getClearVoucherItem(clearVoucherItemStruct);
				billValue = billValue.add(clearVoucherItemStruct.getBwbje());
				voucherItem.setVoucher(clearVoucher);
				voucherItemList.add(voucherItem);
			}
			//得到之前汇损，或调整金的voucheritem
			List<VoucherItem> voucherItems = voucherItemJdbcDao.getVoucherItems(billClearPayment.getSupplier(), conntractNo);
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
			
			// 得到本次票的行项目信息
			Iterator<BillClearItemPay> billClearItemPaysIt = billClearItemPays.iterator();
			while (billClearItemPaysIt.hasNext())
			{
				BillClearItemPay billClearItemPay = billClearItemPaysIt.next();
				if (conntractNo.equals(billClearItemPay.getContract_no()))
				{
//					ClearVoucherItemStruct clearVoucherItemStruct = this.vendorTitleJdbcDao.getCurrectBillInfo(billClearItemPay.getInvoice());
//					VoucherItem voucherItem = getClearVoucherItem(clearVoucherItemStruct);
//					billValue = billValue.add(clearVoucherItemStruct.getBwbje());
//					voucherItem.setVoucher(clearVoucher);
//					voucherItemList.add(voucherItem);
					
					ClearVoucherItemStruct clearVoucherItemStruct = new ClearVoucherItemStruct();
					VendorTitle vendorTitle = this.vendorTitleJdbcDao.getByVendortitleid(billClearItemPay.getTitleid());				 
					clearVoucherItemStruct.setRowno(vendorTitle.getBuzei());
					clearVoucherItemStruct.setVoucherno(billClearItemPay.getVoucherno());
					clearVoucherItemStruct.setYear(vendorTitle.getGjahr());
					VoucherItem voucherItem = getClearVoucherItem(clearVoucherItemStruct);						
					billValue = billValue.add(vendorTitle.getBwbje());	
					voucherItem.setVoucher(clearVoucher);
					voucherItemList.add(voucherItem);
				}
			}
			//退款的行项目信息
			List<ClearVoucherItemStruct> clearVoucherItemStructList3 = this.vendorTitleJdbcDao.getRefundItemBySupplierNoAndContractNo(billClearPayment.getSupplier(), BusinessState.ALL_BILLCLEAR_PAIDUP, conntractNo);
			for (ClearVoucherItemStruct clearVoucherItemStruct : clearVoucherItemStructList3)
			{
				VoucherItem voucherItem = getClearVoucherItem(clearVoucherItemStruct);
				billValue = billValue.add(clearVoucherItemStruct.getBwbje());
				voucherItem.setVoucher(clearVoucher);
				voucherItemList.add(voucherItem);
			}
			// 得到之前的款分配信息
			List<ClearVoucherItemStruct> clearVoucherItemStructList2 = this.vendorTitleJdbcDao.getContractPayItemInfo(conntractNo);
			for (ClearVoucherItemStruct clearVoucherItemStruct : clearVoucherItemStructList2)
			{
				VoucherItem voucherItem = getClearVoucherItem(clearVoucherItemStruct);
				itemValue = itemValue.add(clearVoucherItemStruct.getBwbje());
				voucherItem.setVoucher(clearVoucher);
				voucherItemList.add(voucherItem);
			}

			// 得到本次分配项的行项目信息
			Iterator<BillInPayment> billInPaymentsIt = billInPayments.iterator();
			while (billInPaymentsIt.hasNext())
			{
				BillInPayment billInPayment = billInPaymentsIt.next();
				if (conntractNo.equals(billInPayment.getContract_no()))
				{
					Voucher oldVoucher = this.voucherService.getVoucherByBusinessId(billInPayment.getPaymentid(), "1");
					Set<VoucherItem> oldVoucherItem = oldVoucher.getVoucherItem();
					Iterator<VoucherItem> payVoucherItemit = oldVoucherItem.iterator();
					while (payVoucherItemit.hasNext())
					{
						VoucherItem oldItem = payVoucherItemit.next();
						if (oldItem.getVoucher().getBusinessid().equals(billInPayment.getPaymentid()) && oldItem.getBusinessitemid().equals(billInPayment.getPaymentitemid()))
						{
							ClearVoucherItemStruct clearVoucherItemStruct = new ClearVoucherItemStruct();
							clearVoucherItemStruct.setRowno(oldItem.getRownumber());
							clearVoucherItemStruct.setVoucherno(billInPayment.getVoucherno());
							clearVoucherItemStruct.setYear(oldItem.getVoucher().getFiyear());
							VoucherItem voucherItem = getClearVoucherItem(clearVoucherItemStruct);
							itemValue = itemValue.add(oldItem.getAmount2());
							voucherItem.setVoucher(clearVoucher);
							voucherItemList.add(voucherItem);
						}
					}
				}
			}
			// 判断是否外币,有调整金
			if (!"CNY".equalsIgnoreCase(currency))
			{
				subtractVlaue = itemValue.subtract(billValue);
				subtractVlaue = subtractVlaue.add(ajValue);
				if (subtractVlaue.abs().compareTo(new BigDecimal(0)) != 0)
				{
					VoucherItem supplierVoucherItem = getProfitAndLossVoucherItem(billClearPayment, subtractVlaue, currency,conntractNo, "1");
					supplierVoucherItem.setVoucher(clearVoucher);
					voucherItemList.add(supplierVoucherItem);

					VoucherItem profOrLossVoucherItem = getProfitAndLossVoucherItem(billClearPayment, subtractVlaue, currency,conntractNo, "2");
					profOrLossVoucherItem.setVoucher(clearVoucher);
					voucherItemList.add(profOrLossVoucherItem);
				}
			}
		}
		return contractNos;
	}

	/**
	 * 判断单据是否可全清帐
	 * 
	 * @param supplierSinglClear
	 * @return
	 */
	public boolean judgeIsAllClear(BillClearPayment billClearPayment)
	{
		Set<BillClearItemPay> billClearItemPays = billClearPayment.getBillClearItemPay();
		Set<BillInPayment> billInPayments = billClearPayment.getBillInPayment();
		// 应付款总计
		BigDecimal sumBillAmount = new BigDecimal(0);
		// 清帐金额总计
		BigDecimal sumBillClearAmount = new BigDecimal(0);
		for (BillClearItemPay billClearItemPay : billClearItemPays)
		{
			sumBillAmount = sumBillAmount.add(billClearItemPay.getReceivableamount());
			sumBillClearAmount = sumBillClearAmount.add(billClearItemPay.getClearbillamount());
		}
		// 付款单金额总计
		BigDecimal sumFundAmount = new BigDecimal(0);
		// 本次抵消金额总计
		BigDecimal sumFundClearAmount = new BigDecimal(0);
		for (BillInPayment billInPayment : billInPayments)
		{
			sumFundAmount = sumFundAmount.add(billInPayment.getPaymentamount());
			sumFundClearAmount = sumFundClearAmount.add(billInPayment.getNowclearamount());
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
	 * @param itemSet
	 * @return
	 */
	private List<String> getContractNoList(Set<BillClearItemPay> billClearItemPays)
	{
		String contractNo = null;
		List<String> contractNos = new ArrayList<String>();
		Iterator<BillClearItemPay> it = billClearItemPays.iterator();
		while (it.hasNext())
		{
			BillClearItemPay billClearItemPay = it.next();
			if (contractNo == null || !contractNo.equals(billClearItemPay.getContract_no()))
			{
				contractNos.add(billClearItemPay.getContract_no());
			}
			contractNo = billClearItemPay.getContract_no();
		}
		return contractNos;
	}

	/**
	 * 获取合同号列表
	 * 
	 * @param itemSet
	 * @return
	 */
	private List<String> getProjectNoList(Set<BillClearItemPay> billClearItemPays)
	{
		String projectNo = null;
		List<String> projectNos = new ArrayList<String>();
		Iterator<BillClearItemPay> it = billClearItemPays.iterator();
		while (it.hasNext())
		{
			BillClearItemPay billClearItemPay = it.next();
			if (projectNo == null || !projectNo.equals(billClearItemPay.getProject_no()))
			{
				projectNos.add(billClearItemPay.getProject_no());
			}
			projectNo = billClearItemPay.getProject_no();
		}
		return projectNos;
	}

	/**
	 * 判断单据下合同是否可全清帐
	 * 
	 * @param supplierSinglClear
	 * @return
	 */
	public List<String> judgeContractIsAllClear(BillClearPayment billClearPayment)
	{
		List<String> clearedContractNo = new ArrayList<String>();
		Set<BillClearItemPay> billClearItemPays = billClearPayment.getBillClearItemPay();
		Set<BillInPayment> billInPayments = billClearPayment.getBillInPayment();

		// 取得票下合同列表
		List<String> contractNos = getContractNoList(billClearItemPays);
		if (null != contractNos && contractNos.size() > 0)
		{
			log.debug("共有" + contractNos.size() + "个合同：");
			for (String contractNo : contractNos)
			{
				// 合同下(未付款、未收款)合计
				BigDecimal billUnClearAmount = new BigDecimal(0);
				// 合同下清帐金额合计
				BigDecimal billClearAmount = new BigDecimal(0);
				//调整金金额
				BigDecimal ajAmount = new BigDecimal(0);
				//应收款金额
				BigDecimal receivableamount = new BigDecimal(0);
				
				BigDecimal amount = new BigDecimal(0);
				
				for (BillClearItemPay billClearItemPay : billClearItemPays)
				{
					if (contractNo.equals(billClearItemPay.getContract_no()))
					{
						billUnClearAmount = billUnClearAmount.add(billClearItemPay.getUnreceivedamount());
						billClearAmount = billClearAmount.add(billClearItemPay.getClearbillamount());
						ajAmount = ajAmount.add(billClearItemPay.getAdjustamount());
						receivableamount = receivableamount.add(billClearItemPay.getReceivableamount());
					}
				}
				log.debug("合同号:" + contractNo + "billUnClearAmount:" + billUnClearAmount + ", billClearAmount:" + billClearAmount);

				if (billUnClearAmount.compareTo(billClearAmount) == 0)
				{
					// 款抵消金额
					BigDecimal fundClearamount = new BigDecimal(0.00);
					// 款未抵消金额
					BigDecimal fundUnClearAmount = new BigDecimal(0.00);
					for (BillInPayment billInPayment : billInPayments)
					{
						if (contractNo.equals(billInPayment.getContract_no()))
						{
							fundClearamount = fundClearamount.add(billInPayment.getNowclearamount());
							fundUnClearAmount = fundUnClearAmount.add(billInPayment.getUnoffsetamount());
							amount = amount.add(billInPayment.getPaymentamount());
						}
					}
					
					log.debug("合同号:" + contractNo + "fundClearamount:" + fundClearamount + ", fundUnClearAmount:" + fundUnClearAmount);
					
					//得到之前汇损，或调整金的voucheritem
					List<VoucherItem> voucherItems = voucherItemJdbcDao.getVoucherItems(billClearPayment.getSupplier(), contractNo);
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
					
					// 款上，款本次抵消金额合计==款未抵消金额
					if (fundClearamount.compareTo(fundUnClearAmount) == 0)
					{
						// 合同下已清票金额合计
						BigDecimal sumAllContractBillTot = this.vendorTitleJdbcDao.getSumAmount(contractNo);
						BigDecimal sumAllContractFundTot = this.paymentItemJdbcDao.getSumAmount(contractNo);
						//合同下已清票退款金额合计
						BigDecimal sumAllContractRefundTot = this.vendorTitleJdbcDao.getSumRefundAmount(billClearPayment.getSupplier(), BusinessState.ALL_BILLCLEAR_PAIDUP, contractNo);
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
							clearedContractNo.add(contractNo);
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
	 * @param billClearPayment
	 * @return
	 */
	public List<String> judgeProjectIsAllClear(BillClearPayment billClearPayment, List<String> clearedContractNo, String contractNos)
	{
		Set<BillClearItemPay> billClearItemPays = billClearPayment.getBillClearItemPay();
		Set<BillInPayment> billInPayments = billClearPayment.getBillInPayment();
		List<String> clearedProjectNo = new ArrayList<String>();

		// 取得票下立项列表
		List<String> projectNos = getProjectNoList(billClearItemPays);
		if (null != projectNos && projectNos.size() > 0)
		{
			log.debug("共有" + projectNos.size() + "个立项：");
			for (String projectNo : projectNos)
			{
				// 立项下(未付款、未收款)合计
				BigDecimal billUnClearAmount = new BigDecimal(0);
				// 立项下清帐金额合计
				BigDecimal billClearAmount = new BigDecimal(0);
				
				//调整金金额
				BigDecimal ajAmount = new BigDecimal(0);
				//应收款金额
				BigDecimal receivableamount = new BigDecimal(0);
				
				BigDecimal amount = new BigDecimal(0);
				
				for (BillClearItemPay billClearItemPay : billClearItemPays)
				{
					String contract_no = billClearItemPay.getContract_no();
					if (projectNo.equals(billClearItemPay.getProject_no()))
					{
						if (null != clearedContractNo && clearedContractNo.contains(contract_no))
						{
							continue;
						}
						else
						{
							billUnClearAmount = billUnClearAmount.add(billClearItemPay.getUnreceivedamount());
							billClearAmount = billClearAmount.add(billClearItemPay.getClearbillamount());
							ajAmount = ajAmount.add(billClearItemPay.getAdjustamount());
							receivableamount = receivableamount.add(billClearItemPay.getReceivableamount());
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
					
					
					for (BillInPayment billInPayment : billInPayments)
					{
						String contract_no = billInPayment.getProject_no();
						if (projectNo.equals(billInPayment.getProject_no()))
						{
							if (null != clearedContractNo && clearedContractNo.contains(contract_no))
							{
								continue;
							}
							else
							{
								fundClearamount = fundClearamount.add(billInPayment.getNowclearamount());
								fundUnClearAmount = fundUnClearAmount.add(billInPayment.getUnoffsetamount());
								
								amount = amount.add(billInPayment.getPaymentamount());
							}
						}
					}
					log.debug("立项号:" + projectNo + "fundClearamount:" + fundClearamount + ", fundUnClearAmount:" + fundUnClearAmount);
					//得到之前汇损，或调整金的voucheritem
					List<VoucherItem> voucherItems = voucherItemJdbcDao.getVoucherItems(billClearPayment.getSupplier(), projectNo);
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
					// 款上，款本次抵消金额合计==款未抵消金额
					if (fundClearamount.compareTo(fundUnClearAmount) == 0)
					{
						// 合同下已清票金额合计
						BigDecimal sumAllContractBillTot = this.vendorTitleJdbcDao.getSumAmount(projectNo, contractNos);
						BigDecimal sumAllContractFundTot = this.paymentItemJdbcDao.getSumAmount(projectNo, contractNos);
						//合同下已清票退款金额合计
						BigDecimal sumAllContractRefundTot = this.vendorTitleJdbcDao.getSumRefundAmountByProject(billClearPayment.getSupplier(), BusinessState.ALL_BILLCLEAR_PAIDUP,projectNo, contractNos);
						
						BigDecimal tot1 = sumAllContractBillTot.add(receivableamount);
						tot1 = tot1.add(sumAllContractRefundTot);
						BigDecimal tot2 = sumAllContractFundTot.add(amount);
						tot2 = tot2.subtract(ajAmount);
						log.debug("sumAllContractBillTot:" + sumAllContractBillTot + ",sumAllContractFundTot:" + sumAllContractFundTot);
						log.debug("billClearAmount:" + billClearAmount + ",fundClearamount:" + fundClearamount);

						// 如果金额一致，可以清帐。
						if (tot1.compareTo(tot2) == 0)
						{
							log.debug("立项号：" + projectNo + "，可以清帐！");
							clearedContractNo.add(projectNo);
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
	 * @param billClearPayment
	 * @param supplier
	 * @param contractNos
	 * @param projectNos
	 * @param bukrs
	 * @return
	 */
	public boolean judgeSupplierIsAllClear(BillClearPayment billClearPayment, String supplier, String contractNos, String projectNos, String bukrs)
	{
		Set<BillClearItemPay> billClearItemPays = billClearPayment.getBillClearItemPay();
		Set<BillInPayment> billInPayments = billClearPayment.getBillInPayment();
		// 票清帐金额合计
		BigDecimal sumBillClearAmount = new BigDecimal(0);
		// (未付款)合计
		BigDecimal billUnClearAmount = new BigDecimal(0);
		//应收款金额
		BigDecimal receivableamount = new BigDecimal(0);
		
		BigDecimal amount = new BigDecimal(0);
		//调整金金额
		BigDecimal ajAmount = new BigDecimal(0);
		
		for (BillClearItemPay billClearItemPay : billClearItemPays)
		{
			sumBillClearAmount = sumBillClearAmount.add(billClearItemPay.getClearbillamount());
			billUnClearAmount = billUnClearAmount.add(billClearItemPay.getUnreceivedamount());
			receivableamount = receivableamount.add(billClearItemPay.getReceivableamount());
			ajAmount = ajAmount.add(billClearItemPay.getAdjustamount());
		}
		if (billUnClearAmount.compareTo(sumBillClearAmount) == 0)
		{
			// 款清帐金额合计
			BigDecimal sumFundClearAmount = new BigDecimal(0);
			// 款未抵消金额
			BigDecimal fundUnClearAmount = new BigDecimal(0.00);
			for (BillInPayment billInPayment : billInPayments)
			{
				sumFundClearAmount = sumFundClearAmount.add(billInPayment.getNowclearamount());
				fundUnClearAmount = fundUnClearAmount.add(billInPayment.getUnoffsetamount());
				amount = amount.add(billInPayment.getPaymentamount());
			}
			// 款上，款本次抵消金额合计==款未抵消金额
			if (sumFundClearAmount.compareTo(fundUnClearAmount) == 0)
			{
				
				//得到之前汇损，或调整金的voucheritem
				List<VoucherItem> voucherItems = voucherItemJdbcDao.getVoucherItems(billClearPayment.getSupplier(), "");
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
				
				// 供应商下已清票金额合计
				BigDecimal sumAllSupplierBillTot = this.vendorTitleJdbcDao.getSumAmount(supplier, contractNos, projectNos, bukrs);
				BigDecimal sumAllSupplierFundTot = this.paymentItemJdbcDao.getSumAmount(supplier, contractNos, projectNos, bukrs);
				//合同下已清票退款金额合计
				BigDecimal sumAllContractRefundTot = this.vendorTitleJdbcDao.getSumRefundAmount(supplier, BusinessState.ALL_BILLCLEAR_PAIDUP,projectNos, contractNos,billClearPayment.getDeptid());
				
				BigDecimal tot1 = receivableamount.add(sumAllSupplierBillTot);
				BigDecimal tot2 = amount.add(sumAllSupplierFundTot);
				tot1 = tot1.add(sumAllContractRefundTot);
				tot2 = tot2.subtract(ajAmount);
				log.debug("sumBillClearAmount:" + sumBillClearAmount + ",sumAllSupplierBillTot:" + sumAllSupplierBillTot);
				log.debug("sumFundClearAmount:" + sumFundClearAmount + ",sumAllSupplierFundTot:" + sumAllSupplierFundTot);
		
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
	 * 判断是否可以出清帐凭证。
	 * 
	 * @param customSingleClear
	 * @return
	 */
	public boolean isHaveClearVoucher(BillClearPayment billClearPayment)
	{
		String businessId = billClearPayment.getBillclearid();
		String deptId = billClearPayment.getDeptid();
		String bukrs = this.voucherService.getCompanyIDByDeptID(deptId);
		String customer = billClearPayment.getCustomer();
		if (judgeIsAllClear(billClearPayment))
		{
			return true;
		}
		else
		{
			// 合同
			List<String> clearedContractNo = judgeContractIsAllClear(billClearPayment);
			if (null != clearedContractNo && clearedContractNo.size() > 0)
			{
				// 处理合同下清帐凭证行项目.
				return true;
			}

			// 立项下
			List<String> clearedProjectNo = judgeProjectIsAllClear(billClearPayment, clearedContractNo, "");
			if (null != clearedProjectNo && clearedProjectNo.size() > 0)
			{
				// 处理立项下清帐凭证行项目.
				return true;

			}
			// 供应商清帐
			if (judgeSupplierIsAllClear(billClearPayment, customer, "", "", bukrs))
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
	
	public VoucherItem getVoucherItem(String businessItemId,String voucherClass){
		return voucherItemJdbcDao.getVoucherItem(businessItemId,voucherClass);
	}
	
	/**
	 * 更新所有的包括旧的title,和收付款，的 iscleared flag true为 sap清帐
	 * @param 
	 * @param 
	 */
	public void updateOldTitle(BillClearPayment billClearPayment,boolean flag){
		String businessid = billClearPayment.getBillclearid();
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
				if(flag){
					this.vendorService.updateVendorTitleIsCleared(vendorTitle.getVendortitleid(), Constants.cleared.sapIsCleared);
				}else{
					this.vendorService.updateVendorTitleIsCleared(vendorTitle.getVendortitleid(), Constants.cleared.isCleared);
				}
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
				if(flag){
					this.paymentItemService.updatePaymentItemIsCleared(paymentItem.getPaymentitemid(), Constants.cleared.sapIsCleared);
				}else{
					this.paymentItemService.updatePaymentItemIsCleared(paymentItem.getPaymentitemid(), Constants.cleared.isCleared);
				}
			}
		}
		
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
	/* (non-Javadoc)
	 * @see com.infolion.xdss3.billClearGen.service.BillClearPaymentServiceGen#_get(java.lang.String)
	 */
	@Override
	public BillClearPayment _get(String id) {
		// TODO Auto-generated method stub
		return billClearPaymentJdbcDao.getBillClearPaymentById(id);
	}
	/* (non-Javadoc)
	 * @see com.infolion.xdss3.billClearGen.service.BillClearPaymentServiceGen#_getDetached(java.lang.String)
	 */
	@Override
	public BillClearPayment _getDetached(String id) {
		// TODO Auto-generated method stub
		return billClearPaymentJdbcDao.getBillClearPaymentById(id);
	}
	/* (non-Javadoc)
	 * @see com.infolion.xdss3.billClearGen.service.BillClearPaymentServiceGen#_getEntityCopy(java.lang.String)
	 */
	@Override
	public BillClearPayment _getEntityCopy(String id) {
		BillClearPayment billClearPayment = new BillClearPayment();
		BillClearPayment billClearPaymentOld = billClearPaymentJdbcDao.getBillClearPaymentById(id);
		try
		{
			BeanUtils.copyProperties(billClearPayment, billClearPaymentOld);
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (InvocationTargetException e)
		{
			e.printStackTrace();
		}
		billClearPayment.setBillclearno(null);
		billClearPayment.setBillclearid(null);
		billClearPayment.setProcessstate(" ");
		return billClearPayment;
	}
	/* (non-Javadoc)
	 * @see com.infolion.xdss3.billClearGen.service.BillClearPaymentServiceGen#_getForEdit(java.lang.String)
	 */
	@Override
	public BillClearPayment _getForEdit(String id) {
		// TODO Auto-generated method stub
		return billClearPaymentJdbcDao.getBillClearPaymentById(id);
	}
	
}