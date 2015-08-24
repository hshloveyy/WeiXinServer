package com.infolion.xdss3.singleClear.service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import com.infolion.platform.basicApp.authManage.UserContextHolder;
import com.infolion.platform.basicApp.authManage.domain.UserContext;
import com.infolion.platform.bo.service.BusinessObjectService;
import com.infolion.platform.core.service.BaseService;
import com.infolion.platform.dpframework.util.DateUtils;
import com.infolion.platform.dpframework.util.StringUtils;
import com.infolion.xdss3.Constants;
import com.infolion.xdss3.collectitem.domain.CollectItem;
import com.infolion.xdss3.customerDrawback.dao.CustomerRefundItemJdbcDao;
import com.infolion.xdss3.customerDrawback.domain.CustomerRefundItem;

import com.infolion.xdss3.financeSquare.domain.FundFlow;
import com.infolion.xdss3.financeSquare.domain.IFundFlow;
import com.infolion.xdss3.financeSquare.domain.ISettleSubject;
import com.infolion.xdss3.financeSquare.domain.SettleSubject;
import com.infolion.xdss3.masterData.domain.CustomerTitle;
import com.infolion.xdss3.payment.domain.ClearVoucherItemStruct;
import com.infolion.xdss3.payment.domain.ImportPaymentItem;
import com.infolion.xdss3.singleClear.dao.CustomerClearAccountJdbcDao;
import com.infolion.xdss3.singleClear.dao.SupplierClearAccountJdbcDao;
import com.infolion.xdss3.singleClear.domain.ClearConstant;
import com.infolion.xdss3.singleClear.domain.ClearVoucherItem;
import com.infolion.xdss3.singleClear.domain.IInfoVoucher;
import com.infolion.xdss3.singleClear.domain.IKeyValue;
import com.infolion.xdss3.singleClear.domain.IParameter;
import com.infolion.xdss3.singleClear.domain.IParameterVoucher;
import com.infolion.xdss3.singleClear.domain.InfoObject;
import com.infolion.xdss3.singleClear.domain.KeyValue;
import com.infolion.xdss3.singleClear.domain.ParameterVoucherObject;

import com.infolion.xdss3.voucher.dao.VoucherHibernateDao;
import com.infolion.xdss3.voucher.domain.Voucher;
import com.infolion.xdss3.voucher.service.VoucherService;
import com.infolion.xdss3.voucheritem.dao.VoucherItemJdbcDao;
import com.infolion.xdss3.voucheritem.domain.VoucherItem;

public class ClearAccount extends BaseService implements IClearAccount{
	@Autowired
	private CustomerRefundItemJdbcDao customerRefundItemJdbcDao;
	
	/**
	 * @param customerRefundItemJdbcDao the customerRefundItemJdbcDao to set
	 */
	public void setCustomerRefundItemJdbcDao(
			CustomerRefundItemJdbcDao customerRefundItemJdbcDao) {
		this.customerRefundItemJdbcDao = customerRefundItemJdbcDao;
	}	
	
//	@Autowired
//	private CustomerClearAccountJdbcDao customerClearAccountJdbcDao;
//	
//	
//
//	@Autowired
//	private SupplierClearAccountJdbcDao supplierClearAccountJdbcDao;
//	
//	
//	/**
//	 * @param customerClearAccountJdbcDao the customerClearAccountJdbcDao to set
//	 */
//	public void setCustomerClearAccountJdbcDao(
//			CustomerClearAccountJdbcDao customerClearAccountJdbcDao) {
//		this.customerClearAccountJdbcDao = customerClearAccountJdbcDao;
//	}
//
//	/**
//	 * @param supplierClearAccountJdbcDao the supplierClearAccountJdbcDao to set
//	 */
//	public void setSupplierClearAccountJdbcDao(
//			SupplierClearAccountJdbcDao supplierClearAccountJdbcDao) {
//		this.supplierClearAccountJdbcDao = supplierClearAccountJdbcDao;
//	}

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
	private VoucherItemJdbcDao voucherItemJdbcDao;

	
	public void setVoucherItemJdbcDao(VoucherItemJdbcDao voucherItemJdbcDao) {
		this.voucherItemJdbcDao = voucherItemJdbcDao;
	}
	
	/**
	 * 
	 * 
	 * 取得凭证抬头
	 * *  @param parameterObject 参数对象（以下是要用到的属性）
	 * @param businesstype  客户08 供应商09
	 * @param vouchertype  SA
	 * @param vouchertype  1
	 * @param businessid  id
	 * @param bukrs 公司代码
	 * @param accountdate 过账日期
	 * @param currencytext 币别
	 * @param gsber 业务范围
	 * @param voucherdate 凭证日期
	 * @param text 凭证抬头文本
	 * @return
	 */
	public Voucher getVoucher(IParameter parameterObject){
		
		String businesstype = parameterObject.getBusinesstype();
		String vouchertype = parameterObject.getVouchertype();
		String voucherclass = parameterObject.getVoucherclass();
		String businessid = parameterObject.getBusinessid();
		String bukrs = parameterObject.getBukrs();
		String accountdate = parameterObject.getAccountdate();
		String currencytext = parameterObject.getCurrencytext();
		String gsber = parameterObject.getGsber();
		String voucherdate = parameterObject.getVoucherdate();
		String text = parameterObject.getText();
		
		Voucher voucher = new Voucher();
		String fiperiod = "";
		String fiyear = "";
		// 添加凭证抬头数据
		voucher.setBusinessid(businessid);
		voucher.setBusinesstype(businesstype); // 客户单清
		voucher.setVouchertype(vouchertype);
		voucher.setCompanycode(bukrs);
		voucher.setExchangerate(new BigDecimal(1));
		voucher.setCurrency(currencytext);
		voucher.setGsber(gsber);
		String accountdate2 = accountdate.replace("-", "");
		voucher.setCheckdate(accountdate2);
		voucher.setVoucherclass(voucherclass);
		if (!StringUtils.isNullBlank(accountdate))
		{
			fiperiod = accountdate2.substring(4, 6);
			fiyear = accountdate2.substring(0, 4);
		}
		voucher.setFiperiod(fiperiod);// 会计期间
		voucher.setFiyear(fiyear); // 会计年度
		voucher.setImportdate(DateUtils.getCurrDate(DateUtils.DB_STORE_DATE).substring(0, 8));
		// 输入日期
		UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
		voucher.setImporter(userContext.getUser().getUserName()); // 输入者
		voucher.setPreparer(userContext.getUser().getUserName()); // 预制者
		voucher.setVoucherdate(voucherdate.replace("-", "")); // 凭证日期
		voucher.setVouchertext(text); // 凭证抬头文本
		return voucher;
	}
	
	/**
	 * 
	 * 第一步，有结算科目或纯资金凭证凭证要先出，不管能不能清账
	 * 取得结算科目或纯资金凭证凭证抬头
	 * *  @param parameterObject 参数对象（以下是要用到的属性）
	 * @param businesstype  客户08 供应商09
	 * @param vouchertype  SA
	 * @param vouchertype  1
	 * @param businessid  id
	 * @param bukrs 公司代码
	 * @param accountdate 过账日期
	 * @param currencytext 币别
	 * @param gsber 业务范围
	 * @param voucherdate 凭证日期
	 * @param text 凭证抬头文本
	 * @return
	 */
//	public Voucher getSettleSubjectVoucher(String businesstype,String vouchertype,String voucherclass,String businessid,String bukrs,String accountdate,String currencytext,String gsber,String voucherdate,String text){
		
	public Voucher getSettleSubjectVoucher(IParameter parameterObject){
		return this.getVoucher(parameterObject);	
	}
	/**
	 * 
	 * 第一步，有结算科目或纯资金凭证凭证要先出，不管能不能清账
	 * 取得结算科目或纯资金凭证凭证行项目	
	 *  @param parameterObject 参数对象（以下是要用到的属性）
	 *  @param customer 客户号
	 *  @param custom_htext 客户文本	 
	 *  @param text 文本
	 * @param fundFlow 纯资金
	 * @param settleSubject 结算科目
	 * @param bukrs 公司代码
	 *  @param gsber 业务范围	 
	 * @param sumAdjustamount
	 * @param voucher 凭证抬头
	 * @param taxCode 用来区别汇损（调整）和 其他凭证
	 * @return  如果没有结算科目或纯资金返回null 有返回list
	 */
//	public List<VoucherItem> getSettleSubjectVoucherItem(String customer,String custom_htext,String text,FundFlow fundFlow,SettleSubject settleSubject,String bukrs,String gsber,BigDecimal sumAdjustamount,Voucher voucher,String taxCode){
	public List<VoucherItem> getSettleSubjectVoucherItem(IParameterVoucher parameterVoucherObject){	
		String customer = parameterVoucherObject.getCustomer();
		String custom_htext = parameterVoucherObject.getCustom_htext();
		String text = parameterVoucherObject.getText();
		IFundFlow fundFlow = parameterVoucherObject.getFundFlow();
		ISettleSubject settleSubject = parameterVoucherObject.getSettleSubject();
		String bukrs = parameterVoucherObject.getBukrs();
		String gsber = parameterVoucherObject.getGsber();
		BigDecimal sumAdjustamount = parameterVoucherObject.getSumAdjustamount();
//		Voucher voucher = parameterVoucherObject.getVoucher();
		String taxCode = parameterVoucherObject.getTaxCode();
		
		String supplier = parameterVoucherObject.getSupplier();
		String supplier_htext = parameterVoucherObject.getSupplier_htext();		
	
		List<VoucherItem> voucherItemList =null;	
		BigDecimal amount2 = new BigDecimal(0); // 财务结算本位币金额
		BigDecimal sumAmount2_s = new BigDecimal(0); // 所有财务结算借方本位币金额的和
		BigDecimal sumAmount2_h = new BigDecimal(0); // 所有财务结算贷方本位币金额的和
		
		BigDecimal sumAmount3_s = new BigDecimal(0); // 所有财务结算借方金额的和
		BigDecimal sumAmount3_h = new BigDecimal(0); // 所有财务结算贷方金额的和
		if (null != settleSubject || null != fundFlow){
						
			String currency = parameterVoucherObject.getCurrencytext();			
			voucherItemList = new ArrayList<VoucherItem>();				
			int iRowNo = 1; // 行项目号
//			voucher.setCurrency(currency);
			// 开始添加凭证行项目
			VoucherItem voucherItem = new VoucherItem();
			voucherItem.setRownumber(String.valueOf(iRowNo));
			voucherItem.setCurrency(currency);
			voucherItem.setAmount(sumAdjustamount.abs());

			if (sumAdjustamount.signum() == -1){
				//客户
				if(!StringUtils.isNullBlank(customer)){
					voucherItem.setCheckcode("01");
					voucherItem.setDebitcredit("S");
				}else{
//					供应商
					voucherItem.setCheckcode("31");
					voucherItem.setDebitcredit("H");
				}
			}else if (sumAdjustamount.signum() == 1){
				//客户
				if(!StringUtils.isNullBlank(customer)){
					voucherItem.setCheckcode("11");
					voucherItem.setDebitcredit("H");
				}else{
//					供应商
					voucherItem.setCheckcode("21");
					voucherItem.setDebitcredit("S");
				}
			}
			voucherItem.setAmount2(new BigDecimal(0));
			if(!StringUtils.isNullBlank(customer)){
				voucherItem.setSubject(customer);
				voucherItem.setSubjectdescribe(custom_htext);
			}else{
				voucherItem.setSubject(supplier);
				voucherItem.setSubjectdescribe(supplier_htext);
			}
			voucherItem.setDepid(gsber);
			voucherItem.setText(text);			
			String subject = "";
			if(!StringUtils.isNullBlank(customer)){
				subject = this.customerRefundItemJdbcDao.getSubjectByCustomer(customer,bukrs);
			}else{
				subject = this.customerRefundItemJdbcDao.getSubjectBySuppliers(supplier,bukrs);
			}
			String subjectname = this.customerRefundItemJdbcDao.getSubjectNameById(subject,bukrs);
			voucherItem.setControlaccount(subject);
			voucherItem.setCaremark(subjectname);
			voucherItem.setTaxcode(taxCode);			
			voucherItemList.add(voucherItem);

			// 结算科目
			// 金额1有填入值
			BigDecimal amount = new BigDecimal(0);
			String debitcredit = "";

			if (null != settleSubject){
				amount = settleSubject.getAmount1();
				debitcredit = settleSubject.getDebtcredit1();
				if (amount.abs().compareTo(new BigDecimal(0)) == 1){
					iRowNo = iRowNo + 1;
					// 开始添加凭证行项目
					VoucherItem voucherItem2 = new VoucherItem();
					voucherItem2.setRownumber(String.valueOf(iRowNo));
					voucherItem2.setCurrency(currency);
					voucherItem2.setAmount(amount);					
					voucherItem2.setDebitcredit(debitcredit);
					if ("S".equalsIgnoreCase(debitcredit)){			
							voucherItem2.setCheckcode("40");	
					}else if ("H".equalsIgnoreCase(debitcredit)){	
							voucherItem2.setCheckcode("50");
					
					}
					amount2 = settleSubject.getStandardamount1();
					voucherItem2.setAmount2(amount2);							
					voucherItem2.setSubject(settleSubject.getSettlesubject1());
					String subjectdescribe = this.customerRefundItemJdbcDao.getSubjectNameById(voucherItem2.getSubject(),bukrs);
					voucherItem2.setSubjectdescribe(subjectdescribe);
//					voucherItem2.setSubjectdescribe(settleSubject.getSettlesubject1_htext());
					voucherItem2.setDepid(settleSubject.getDepid1());
					voucherItem2.setText(text);	
					voucherItem2.setCostcenter(settleSubject.getCostcenter1());	
					if("Y".equals(settleSubject.getAntiaccount1())){
						voucherItem2.setUncheckflag("X");
	                }else{
	                	voucherItem2.setUncheckflag("");
	                }
					voucherItem2.setOrderrowno(settleSubject.getRowno1());
					voucherItem2.setSalesorder(settleSubject.getOrderno1());	
					voucherItem2.setTaxcode(taxCode);
//					voucherItem2.setVoucher(voucher);
					voucherItemList.add(voucherItem2);
				}

				amount = settleSubject.getAmount2();
				debitcredit = settleSubject.getDebtcredit2();
				if (amount.abs().compareTo(new BigDecimal(0)) == 1)	{
					iRowNo = iRowNo + 1;
					// 开始添加凭证行项目
					VoucherItem voucherItem2 = new VoucherItem();
					voucherItem2.setRownumber(String.valueOf(iRowNo));
					voucherItem2.setCurrency(currency);
					voucherItem2.setAmount(amount);					
					voucherItem2.setDebitcredit(debitcredit);
					if ("S".equalsIgnoreCase(debitcredit)){
						voucherItem2.setCheckcode("40");
					}else if ("H".equalsIgnoreCase(debitcredit)){
						voucherItem2.setCheckcode("50");
					}
					amount2 = settleSubject.getStandardamount2();
					voucherItem2.setAmount2(amount2);
			
					voucherItem2.setSubject(settleSubject.getSettlesubject2());
					String subjectdescribe = this.customerRefundItemJdbcDao.getSubjectNameById(voucherItem2.getSubject(),bukrs);
					voucherItem2.setSubjectdescribe(subjectdescribe);
//					voucherItem2.setSubjectdescribe(settleSubject.getSettlesubject2_htext());
					voucherItem2.setDepid(settleSubject.getDepid2());
					voucherItem2.setText(text);
					voucherItem2.setCostcenter(settleSubject.getCostcenter2());
					if("Y".equals(settleSubject.getAntiaccount2())){
						voucherItem2.setUncheckflag("X");
	                }else{
	                	voucherItem2.setUncheckflag("");
	                }

					voucherItem2.setOrderrowno(settleSubject.getRowno2());
					voucherItem2.setSalesorder(settleSubject.getOrderno2());

					voucherItem2.setTaxcode(taxCode);
//					voucherItem2.setVoucher(voucher);
					voucherItemList.add(voucherItem2);
				}

				amount = settleSubject.getAmount3();
				debitcredit = settleSubject.getDebtcredit3();
				if (amount.abs().compareTo(new BigDecimal(0)) == 1){
					iRowNo = iRowNo + 1;
					// 开始添加凭证行项目
					VoucherItem voucherItem2 = new VoucherItem();
					voucherItem2.setRownumber(String.valueOf(iRowNo));
					voucherItem2.setCurrency(currency);
					voucherItem2.setAmount(amount);					
					voucherItem2.setDebitcredit(debitcredit);
					if ("S".equalsIgnoreCase(debitcredit)){
						voucherItem2.setCheckcode("40");
					}
					else if ("H".equalsIgnoreCase(debitcredit)){
						voucherItem2.setCheckcode("50");
					}
					amount2 = settleSubject.getStandardamount3();
					voucherItem2.setAmount2(amount2);
				
					voucherItem2.setSubject(settleSubject.getSettlesubject3());
					String subjectdescribe = this.customerRefundItemJdbcDao.getSubjectNameById(voucherItem2.getSubject(),bukrs);
					voucherItem2.setSubjectdescribe(subjectdescribe);
//					voucherItem2.setSubjectdescribe(settleSubject.getSettlesubject3_htext());
					voucherItem2.setDepid(settleSubject.getDepid3());
					voucherItem2.setText(text);
					voucherItem2.setCostcenter(settleSubject.getCostcenter3());
					if("Y".equals(settleSubject.getAntiaccount3())){
						voucherItem2.setUncheckflag("X");
	                }else{
	                	voucherItem2.setUncheckflag("");
	                }

					voucherItem2.setOrderrowno(settleSubject.getRowno3());
					voucherItem2.setSalesorder(settleSubject.getOrderno3());

					voucherItem2.setTaxcode(taxCode);
//					voucherItem2.setVoucher(voucher);
					voucherItemList.add(voucherItem2);
				}

				amount = settleSubject.getAmount4();
				debitcredit = settleSubject.getDebtcredit4();
				if (amount.abs().compareTo(new BigDecimal(0)) == 1){
					iRowNo = iRowNo + 1;
					// 开始添加凭证行项目
					VoucherItem voucherItem2 = new VoucherItem();
					voucherItem2.setRownumber(String.valueOf(iRowNo));
					voucherItem2.setCurrency(currency);
					voucherItem2.setAmount(amount);
					
					voucherItem2.setDebitcredit(debitcredit);
					if ("S".equalsIgnoreCase(debitcredit)){
						voucherItem2.setCheckcode("40");
					}else if ("H".equalsIgnoreCase(debitcredit)){
						voucherItem2.setCheckcode("50");
					}
					amount2 = settleSubject.getStandardamount4();
					voucherItem2.setAmount2(amount2);
					
					voucherItem2.setSubject(settleSubject.getSettlesubject4());
					String subjectdescribe = this.customerRefundItemJdbcDao.getSubjectNameById(voucherItem2.getSubject(),bukrs);
					voucherItem2.setSubjectdescribe(subjectdescribe);
//					voucherItem2.setSubjectdescribe(settleSubject.getSettlesubject4_htext());
					voucherItem2.setDepid(settleSubject.getDepid4());
					voucherItem2.setText(text);
					voucherItem2.setProfitcenter(settleSubject.getProfitcenter());
					if("Y".equals(settleSubject.getAntiaccount4())){
						voucherItem2.setUncheckflag("X");
	                }else{
	                	voucherItem2.setUncheckflag("");
	                }

					voucherItem2.setOrderrowno(settleSubject.getRowno4());
					voucherItem2.setSalesorder(settleSubject.getOrderno4());

					voucherItem2.setTaxcode(taxCode);
//					voucherItem2.setVoucher(voucher);
					voucherItemList.add(voucherItem2);
				}
			}
			// 纯资金往来
			// fundFlowPay.getAmount1()
			if (null != fundFlow){
				amount = fundFlow.getAmount1();
				debitcredit = fundFlow.getDebtcredit1();
				if (amount.abs().compareTo(new BigDecimal(0)) == 1)	{
					iRowNo = iRowNo + 1;
					// 开始添加凭证行项目
					VoucherItem voucherItem2 = new VoucherItem();
					voucherItem2.setRownumber(String.valueOf(iRowNo));
					voucherItem2.setCurrency(currency);
					voucherItem2.setAmount(amount);
					
					voucherItem2.setDebitcredit(debitcredit);
					if ("S".equalsIgnoreCase(debitcredit)){
						if(StringUtils.isNullBlank(fundFlow.getSpecialaccount1())){
							voucherItem2.setCheckcode("01");
						}else{
							voucherItem2.setCheckcode("09");
						}
					}
					else if ("H".equalsIgnoreCase(debitcredit)){
						if(StringUtils.isNullBlank(fundFlow.getSpecialaccount1())){
							voucherItem2.setCheckcode("11");
						}else{
							voucherItem2.setCheckcode("19");
						}
					}
					amount2 = fundFlow.getStandardamount1();
					voucherItem2.setAmount2(amount2);
			
					
					voucherItem2.setSubject(fundFlow.getCustomer1());
					String subjectdescribe = this.customerRefundItemJdbcDao.getSubjectNameById(voucherItem2.getSubject(),bukrs);
					voucherItem2.setSubjectdescribe(subjectdescribe);
//					voucherItem2.setSubjectdescribe(fundFlow.getCustomer1_htext());
					voucherItem2.setDepid(fundFlow.getDepid1());
					voucherItem2.setText(text);
					voucherItem2.setGlflag(fundFlow.getSpecialaccount1());
					
					if("Y".equals(fundFlow.getAntiaccount1())){
						voucherItem2.setUncheckflag("X");
	                }else{
	                	voucherItem2.setUncheckflag("");
	                }

					voucherItem2.setControlaccount(subject);
					voucherItem2.setCaremark(subjectname);
					if(!StringUtils.isNullBlank(voucherItem2.getGlflag())){
						subject = this.customerRefundItemJdbcDao.getSkont(voucherItem2,subject);
						String subjectname2 = this.customerRefundItemJdbcDao.getSubjectNameById(subject,bukrs);
						voucherItem2.setControlaccount(subject);
						voucherItem2.setCaremark(subjectname2);
					}
					voucherItem2.setTaxcode(taxCode);
//					voucherItem2.setVoucher(voucher);
					voucherItemList.add(voucherItem2);
				}

				amount = fundFlow.getAmount2();
				debitcredit = fundFlow.getDebtcredit1();
				if (amount.abs().compareTo(new BigDecimal(0)) == 1){
					iRowNo = iRowNo + 1;
					// 开始添加凭证行项目
					VoucherItem voucherItem2 = new VoucherItem();
					voucherItem2.setRownumber(String.valueOf(iRowNo));
					voucherItem2.setCurrency(currency);
					voucherItem2.setAmount(amount);
					
					voucherItem2.setDebitcredit(debitcredit);
					if ("S".equalsIgnoreCase(debitcredit)){
						if(StringUtils.isNullBlank(fundFlow.getSpecialaccount2())){
							voucherItem2.setCheckcode("01");
						}else{
							voucherItem2.setCheckcode("09");
						}
					}
					else if ("H".equalsIgnoreCase(debitcredit)){
						if(StringUtils.isNullBlank(fundFlow.getSpecialaccount2())){
							voucherItem2.setCheckcode("11");
						}else{
							voucherItem2.setCheckcode("19");
						}
					}
					amount2 = fundFlow.getStandardamount2();
					voucherItem2.setAmount2(amount2);
				
					
					voucherItem2.setSubject(fundFlow.getCustomer2());
					String subjectdescribe = this.customerRefundItemJdbcDao.getSubjectNameById(voucherItem2.getSubject(),bukrs);
					voucherItem2.setSubjectdescribe(subjectdescribe);
//					voucherItem2.setSubjectdescribe(fundFlow.getCustomer2_htext());
					voucherItem2.setDepid(fundFlow.getDepid2());
					voucherItem2.setText(text);
					voucherItem2.setGlflag(fundFlow.getSpecialaccount2());

					if("Y".equals(fundFlow.getAntiaccount2())){
						voucherItem2.setUncheckflag("X");
	                }else{
	                	voucherItem2.setUncheckflag("");
	                }

					voucherItem2.setControlaccount(subject);
					voucherItem2.setCaremark(subjectname);
					if(!StringUtils.isNullBlank(voucherItem2.getGlflag())){
						subject = this.customerRefundItemJdbcDao.getSkont(voucherItem2,subject);
						String subjectname2 = this.customerRefundItemJdbcDao.getSubjectNameById(subject,bukrs);
						voucherItem2.setControlaccount(subject);
						voucherItem2.setCaremark(subjectname2);
					}
					voucherItem2.setTaxcode(taxCode);
//					voucherItem2.setVoucher(voucher);
					voucherItemList.add(voucherItem2);
				}

				amount = fundFlow.getAmount3();
				debitcredit = fundFlow.getDebtcredit1();
				if (amount.abs().compareTo(new BigDecimal(0)) == 1){
					iRowNo = iRowNo + 1;
					// 开始添加凭证行项目
					VoucherItem voucherItem2 = new VoucherItem();
					voucherItem2.setRownumber(String.valueOf(iRowNo));
					voucherItem2.setCurrency(currency);
					voucherItem2.setAmount(amount);
					
					voucherItem2.setDebitcredit(debitcredit);
					if ("S".equalsIgnoreCase(debitcredit)){
						if(StringUtils.isNullBlank(fundFlow.getSpecialaccount3())){
							voucherItem2.setCheckcode("01");
						}else{
							voucherItem2.setCheckcode("09");
						}
					}
					else if ("H".equalsIgnoreCase(debitcredit)){
						if(StringUtils.isNullBlank(fundFlow.getSpecialaccount3())){
							voucherItem2.setCheckcode("11");
						}else{
							voucherItem2.setCheckcode("19");
						}
					}
					amount2 = fundFlow.getStandardamount3();
					voucherItem2.setAmount2(amount2);
			
					
					voucherItem2.setSubject(fundFlow.getCustomer3());
					String subjectdescribe = this.customerRefundItemJdbcDao.getSubjectNameById(voucherItem2.getSubject(),bukrs);
					voucherItem2.setSubjectdescribe(subjectdescribe);
//					voucherItem2.setSubjectdescribe(fundFlow.getCustomer3_htext());
					voucherItem2.setDepid(fundFlow.getDepid3());
					voucherItem2.setText(text);
					voucherItem2.setGlflag(fundFlow.getSpecialaccount3());

					if("Y".equals(fundFlow.getAntiaccount3())){
						voucherItem2.setUncheckflag("X");
	                }else{
	                	voucherItem2.setUncheckflag("");
	                }

					voucherItem2.setControlaccount(subject);
					voucherItem2.setCaremark(subjectname);
					if(!StringUtils.isNullBlank(voucherItem2.getGlflag())){
						subject = this.customerRefundItemJdbcDao.getSkont(voucherItem2,subject);
						String subjectname2 = this.customerRefundItemJdbcDao.getSubjectNameById(subject,bukrs);
						voucherItem2.setControlaccount(subject);
						voucherItem2.setCaremark(subjectname2);
					}
					voucherItem2.setTaxcode(taxCode);
//					voucherItem2.setVoucher(voucher);
					voucherItemList.add(voucherItem2);
				}
			}
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
			
			//本位币借-贷相减
			BigDecimal sumAmount6 = sumAmount2_s.subtract(sumAmount2_h);
			
			if (sumAmount6.abs().compareTo(new BigDecimal(0)) == 1){
				for (VoucherItem voucherItem0 : voucherItemList){
					if ("1".equals(voucherItem0.getRownumber()) || "001".equals(voucherItem0.getRownumber()) ){
						voucherItem0.setAmount2(sumAmount6.abs());						
					}
				}
			}
			//本位币为0 并且，金额也为0，不加行项目
			if (sumAmount6.abs().compareTo(new BigDecimal(0)) == 0 && sumAdjustamount.abs().compareTo(new BigDecimal(0)) == 0){
				for (VoucherItem voucherItem0 : voucherItemList){					
					if ("1".equals(voucherItem0.getRownumber()) || "001".equals(voucherItem0.getRownumber())){
						voucherItemList.remove(voucherItem0);
						break;
					}
				}
			}
			
		}
		return 	voucherItemList;
		
	}
	
	/**
	 * 得到有汇损益的凭证抬头
	 * *  @param parameterObject 参数对象（以下是要用到的属性）
	 * @param businesstype  客户08 供应商09
	 * @param vouchertype  SA
	 * @param vouchertype  1
	 * @param businessid id
	 * @param bukrs 公司代码
	 * @param accountdate 过账日期
	 * @param currencytext 币别
	 * @param gsber 业务范围
	 * @param voucherdate 凭证日期
	 * @param text 凭证抬头文本
	 * @return
	 */
//	public Voucher getProfitAndLossVoucher(String businesstype,String vouchertype,String voucherclass,String businessid,String bukrs,String accountdate,String currencytext,String gsber,String voucherdate,String text){
	public Voucher getProfitAndLossVoucher(IParameter parameterObject){			
		Voucher voucher = this.getVoucher(parameterObject);
		voucher.setAgkoa(" ");
		voucher.setAgkon(" ");
		voucher.setBstat(" ");
		voucher.setGsber(" ");
		voucher.setFlag(" ");
		voucher.setValut(" ");
		return voucher;
	}
	/**
	 * 得到有汇损益的行项目信息
	 * *  @param parameterObject 参数对象（以下是要用到的属性）
	 * 
	 * @param bukrs 公司代码
	 *  @param customer 客户号
	 *  @param custom_htext 客户文本
	 *  @param gsber 业务范围
	 *  @param text 文本
	 * @param subtractVlaue 本位币相差的金额
	 * @param currency 币别（2600为HKD,其他为CNY）
	 * @param strType 
	 *  @param rownumber 行项目
	 *  
	 * @return
	 */
//	public VoucherItem getProfitAndLossVoucherItem(String bukrs,String customer,String custom_htext,String gsber,String text, BigDecimal subtractVlaue, String currency,String taxCode, String strType,String rownumber){
	public VoucherItem getProfitAndLossVoucherItem(IParameter parameterObject){
		String bukrs = parameterObject.getBukrs();
		String customer = parameterObject.getCustomer();
		String custom_htext = parameterObject.getCustom_htext();
		String gsber = parameterObject.getGsber();
		String text = parameterObject.getText(); 
		BigDecimal subtractVlaue = parameterObject.getSubtractVlaue();
		String currency = parameterObject.getCurrencytext();
		String taxCode = parameterObject.getTaxCode(); 
		String strType = parameterObject.getStrType();
		String rownumber = parameterObject.getRownumber();
		
		String supplier = parameterObject.getSupplier();
		String supplier_htext = parameterObject.getSupplier_htext();		
		
		VoucherItem voucherItem = new VoucherItem();
		String subject = "";
		if(!StringUtils.isNullBlank(customer)){
			subject = this.customerRefundItemJdbcDao.getSubjectByCustomer(customer,bukrs);
		}else{
			subject = this.customerRefundItemJdbcDao.getSubjectBySuppliers(supplier,bukrs);
		}
		
		String subjectname = this.customerRefundItemJdbcDao.getSubjectNameById(subject,bukrs);
		voucherItem.setControlaccount(subject);
		voucherItem.setCaremark(subjectname);
		voucherItem.setTaxcode(taxCode);
		voucherItem.setRownumber(rownumber);
		if (strType.equals("1"))
		{
			voucherItem.setRownumber("001");
			// 科目
			
			// 科目说明
			// String Subjectdescribe =
			// this.voucherService.getSupplierDescByCustomerId(customSingleClear.getCustom(),
			// bukrs);
			if(!StringUtils.isNullBlank(customer)){
				voucherItem.setSubject(customer);
//				客户
				voucherItem.setSubjectdescribe(custom_htext);
				if (subtractVlaue.signum() == 1){
					// 记帐码
					voucherItem.setCheckcode("08");
					voucherItem.setDebitcredit("S");
				}
				else{
					// 记帐码
					voucherItem.setCheckcode("15");
					voucherItem.setDebitcredit("H");
				}
			}else{
				voucherItem.setSubject(supplier);
//				供应商
				voucherItem.setSubjectdescribe(supplier_htext);
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
			
		}

		if (strType.equals("2")){
			voucherItem.setRownumber("002");
//			客户
			if(!StringUtils.isNullBlank(customer)){
				if (subtractVlaue.signum() == 1){
					// 记帐码
					voucherItem.setCheckcode("50");
					voucherItem.setDebitcredit("H");
					String su="6603050302";
					if("2600".equals(bukrs)){
						// 科目
						su = "6603050201";
					}
					// 科目
					voucherItem.setSubject(su);
					// 科目说明
					voucherItem.setSubjectdescribe(this.customerRefundItemJdbcDao.getSubjectNameById(su,bukrs));
					voucherItem.setControlaccount(voucherItem.getSubject());
					voucherItem.setCaremark(voucherItem.getSubjectdescribe());
				}else{
					// 记帐码
					voucherItem.setCheckcode("40");
					voucherItem.setDebitcredit("S");
					String su ="6603050301";
					if("2600".equals(bukrs)){
						// 科目
						su = "6603050101";
					}
					// 科目
					voucherItem.setSubject(su);
					// 科目说明
					voucherItem.setSubjectdescribe(this.customerRefundItemJdbcDao.getSubjectNameById(su,bukrs));
					voucherItem.setControlaccount(voucherItem.getSubject());
					voucherItem.setCaremark(voucherItem.getSubjectdescribe());
				}
				
			}else{
//				供应商
				if (subtractVlaue.signum() == -1){
					// 记帐码
					voucherItem.setCheckcode("50");
					voucherItem.setDebitcredit("H");
					String su ="6603050402";
					if("2600".equals(bukrs)){
						// 科目
						su = "6603050201";
					}
					// 科目
					voucherItem.setSubject(su);
					// 科目说明
					voucherItem.setSubjectdescribe(this.customerRefundItemJdbcDao.getSubjectNameById(su,bukrs));
					voucherItem.setControlaccount(voucherItem.getSubject());
					voucherItem.setCaremark(voucherItem.getSubjectdescribe());
				}else{
					// 记帐码
					voucherItem.setCheckcode("40");
					voucherItem.setDebitcredit("S");
					String su ="6603050401";
					if("2600".equals(bukrs)){
						// 科目
						su = "6603050101";
					}
					// 科目
					voucherItem.setSubject(su);
					// 科目说明
					voucherItem.setSubjectdescribe(this.customerRefundItemJdbcDao.getSubjectNameById(su,bukrs));
					voucherItem.setControlaccount(voucherItem.getSubject());
					voucherItem.setCaremark(voucherItem.getSubjectdescribe());
				}
			}

		}
		// 货币
		voucherItem.setCurrency(currency);
		// 货币金额
		voucherItem.setAmount(new BigDecimal("0"));
		// 本位币金额
		voucherItem.setAmount2(subtractVlaue.abs().setScale(2, BigDecimal.ROUND_HALF_UP));
		// 部门
		voucherItem.setDepid(gsber);
		// 文本
		voucherItem.setText(text);
		return voucherItem;
	}
	/**取得清账凭证抬头
	 * *  @param parameterObject 参数对象（以下是要用到的属性）
	 * 
	 * @param businesstype  客户08 供应商09
	 * @param vouchertype  SA
	 * @param voucherclass  清账9 
	 * @param businessid id 
	 * @param bukrs 公司代码
	 * @param accountdate 过账日期
	 * @param currencytext 币别
	 * @param gsber 业务范围
	 * @param voucherdate 凭证日期
	 * @param text 凭证抬头文本
	 * 
	 * @return
	 */
//	public Voucher getClearVoucher(String businesstype,String vouchertype,String voucherclass,String businessid,String bukrs,String accountdate,String currencytext,String gsber,String voucherdate,String text,String customer){
	public Voucher getClearVoucher(IParameter parameterObject){
		Voucher voucher = this.getVoucher(parameterObject);
		// 收款清帐标识
		voucher.setFlag("R");		
		// 计息日
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		voucher.setValut(dateFormat.format(new Date()));
		if(!StringUtils.isNullBlank(parameterObject.getCustomer())){
		// 客户编号
			voucher.setAgkon(parameterObject.getCustomer());
		}else{
			voucher.setAgkon(parameterObject.getSupplier());
		}
		// 科目类型
		if(!StringUtils.isNullBlank(parameterObject.getCustomer())){
			voucher.setAgkoa("D");
		}else{
			voucher.setAgkoa("K");
		}
		// 清帐凭证状态
		voucher.setBstat("A");
		// 汇率
		voucher.setExchangerate(new BigDecimal("1"));

		return voucher;
	}

	
	
	/***
	 * 
	 * @param clid
	 * @param list
	 * @return 相同的返回true
	 */
	public boolean isSame(String clid,List<IKeyValue> list){
		for(IKeyValue kv : list){
			if(clid.equals(kv.getKey())){
				return true;
			}
		}
		return false;
	}
	
	
	/**
	 * 转换成凭证行项目，
	 * @param viSet
	 * @param clid
	 * @return
	 */
	public Set<VoucherItem> getClearVoucherItems(Set<ClearVoucherItem> cviSet,Voucher voucher,String voucherid){
		Set<VoucherItem> viSet = new HashSet<VoucherItem>();
		for(ClearVoucherItem clearVoucherItem:cviSet){
			
//			取有凭证号的并且不等于本次的
			if(!StringUtils.isNullBlank(clearVoucherItem.getVoucherno()) && !voucher.getBusinessid().equals(clearVoucherItem.getBusinessid())){
				VoucherItem voucherItem = getClearVoucherItem(clearVoucherItem);
				if(isExistSet(viSet,voucherItem))continue;
				viSet.add(voucherItem);
				voucherItem.setVoucher(voucher);
			}else{
//				本次的取进来，不管有没有生成凭证号
				if(voucher.getBusinessid().equals(clearVoucherItem.getBusinessid())){
					VoucherItem voucherItem = getClearVoucherItem(clearVoucherItem);
					if(StringUtils.isNullBlank(voucherItem.getVoucherno())){
						voucherItem.setBusvoucherid(voucherid);
					}else{
						voucherItem.setBusvoucherid(" ");
					}
					if(isExistSet(viSet,voucherItem))continue;
					viSet.add(voucherItem);
					voucherItem.setVoucher(voucher);
				}
			}
			
		}
		return viSet;
	}
	
	
	/**
	 * 处理清帐凭证行项目
	 * 
	 * @param clearVoucherItemStruct
	 * @return
	 */
	public VoucherItem getClearVoucherItem(ClearVoucherItem clearVoucherItem){
		VoucherItem voucherItem = new VoucherItem();
		voucherItem.setVoucherno(clearVoucherItem.getVoucherno());
		voucherItem.setRownumber(clearVoucherItem.getRowno());
		voucherItem.setFiyear(clearVoucherItem.getYear());
		voucherItem.setBusinessitemid(clearVoucherItem.getBusinessitemid());
		voucherItem.setFlag("R");
		voucherItem.setAmount(new BigDecimal("0.00"));
		voucherItem.setAmount2(new BigDecimal("0.00"));
		return voucherItem;
	}
//	/**
//	 * 取得清账凭证行项目
//	 * @param clearIdList 所有的清账单据ID，key为ID，value为类型,参照ClearConstant
//	 * @param voucher
//	 * @param voucherid 结算科目的凭证ID
//	 * @return
//	 */
//	public Set<VoucherItem> getClearVoucherItemByCustomer2(List<IKeyValue> clearIdList,Voucher voucher,String voucherid){
//		Set<ClearVoucherItem> cviSet = this.getClearVoucherItemByCustomer(clearIdList);
//		return this.getClearVoucherItems(cviSet,voucher,voucherid);		
//	}
	/**
	 * 保存有结算科目或纯资金凭证凭证
	 * @param parameterVoucher 参数对象
	 * @return
	 */
	public Voucher saveSettleSubjectVoucher(IParameterVoucher parameterVoucher){
		Voucher voucher = this.getSettleSubjectVoucher(parameterVoucher);
		List<VoucherItem> list =this.getSettleSubjectVoucherItem(parameterVoucher);
		Set<VoucherItem> viSet = new HashSet<VoucherItem>();
		for(VoucherItem voucherItem:list){
			voucherItem.setVoucher(voucher);
			viSet.add(voucherItem);
		}
		voucher.setVoucherItem(viSet);
		this.voucherService._saveOrUpdate(voucher, null, BusinessObjectService.getBusinessObject("Voucher"));
		return voucher;
	}
	
	/**
	 * 保存汇损凭证
	 * @param parameterVoucher 参数对象
	 * @return
	 */
	public Voucher saveProfitAndLossVoucher(ParameterVoucherObject parameterVoucher){
		Voucher voucher=this.getProfitAndLossVoucher(parameterVoucher);		
		parameterVoucher.setTaxCode(parameterVoucher.getTaxCode());
		parameterVoucher.setRownumber("001");
		parameterVoucher.setStrType("1");
		VoucherItem voucherItem1 = this.getProfitAndLossVoucherItem(parameterVoucher);
		voucherItem1.setVoucher(voucher);
		parameterVoucher.setRownumber("002");
		parameterVoucher.setStrType("2");
		VoucherItem voucherItem2 = this.getProfitAndLossVoucherItem(parameterVoucher);
		voucherItem2.setVoucher(voucher);
		Set<VoucherItem> vi=new HashSet<VoucherItem>();
		vi.add(voucherItem1);
		vi.add(voucherItem2);
		voucher.setVoucherItem(vi);
		this.voucherService._saveOrUpdate(voucher, null, BusinessObjectService.getBusinessObject("Voucher"));
		return voucher;
	}
	
//	/**
//	 * 保存客户清账凭证
//	 * @param parameterVoucher 参数对象
//	 * @param infoVoucher  返回的信息对象
//	 * @param clearid 当前的清账单据ID
//	 * @param type2 类型，参照ClearConstant
//	 * @return
//	 */
//	public Voucher saveClearVoucherByCustomer(ParameterVoucherObject parameterVoucher,IInfoVoucher infoVoucher,String clearid,String type2,boolean isp){
////		所有的清账voucherclass都是9
//		parameterVoucher.setVoucherclass("9");
//		Voucher voucher = this.getClearVoucher(parameterVoucher);
//		List<IKeyValue>  clearIdList = this.getPartClearByCustomer(parameterVoucher, infoVoucher, clearid, type2);
//		if(null == clearIdList)return null;
//		Set<VoucherItem> viSet =this.getClearVoucherItemByCustomer2(clearIdList,voucher,parameterVoucher.getVoucherid());
////		如果是收款，因为没有生成凭证,加入收款凭证行项目，
//		if(ClearConstant.COLLECT_TYPE.equals(type2)){
//			
//			List<ClearVoucherItem> viList = this.customerClearAccountJdbcDao.getVoucherItemByCollectid(clearid, "1,4");
//			for(ClearVoucherItem clearVoucherItem:viList){
//				VoucherItem voucherItem = getClearVoucherItem(clearVoucherItem);
////				为空说明没有生成凭证，加进去，有上面会取到
//				if(StringUtils.isNullBlank(voucherItem.getVoucherno())){
//					voucherItem.setBusvoucherid(clearVoucherItem.getVoucherid());
//					voucherItem.setVoucher(voucher);
//					viSet.add(voucherItem);		
//				}	
//			}
//		}
////		如果是退款，因为没有生成凭证,加入收款凭证行项目，
//		if(ClearConstant.REFUNDMENT_TYPE.equals(type2)){
//			
//			List<ClearVoucherItem> viList = this.customerClearAccountJdbcDao.getVoucherItemByRefmentid(clearid, "1,4");
//			for(ClearVoucherItem clearVoucherItem:viList){
//				VoucherItem voucherItem = getClearVoucherItem(clearVoucherItem);
////				为空说明没有生成凭证，加进去，有上面会取到
//				if(StringUtils.isNullBlank(voucherItem.getVoucherno())){
//					voucherItem.setBusvoucherid(clearVoucherItem.getVoucherid());
//					voucherItem.setVoucher(voucher);
//					viSet.add(voucherItem);		
//				}	
//			}
//			
//			viList = this.customerClearAccountJdbcDao.getCollectVoucherItemByRefmentid(clearid, "1,4");
//			for(ClearVoucherItem clearVoucherItem:viList){
//				VoucherItem voucherItem = getClearVoucherItem(clearVoucherItem);				
//				voucherItem.setVoucher(voucher);
////				判断是否已经存在,不存在加入
//				if(!isExistSet(viSet,voucherItem)){
//					viSet.add(voucherItem);				
//				}					
//			}
//		}
////		是否出汇损,并且差额不为0
//		if(isp && parameterVoucher.getSubtractVlaue().compareTo(new BigDecimal("0"))!= 0){
//			
//			parameterVoucher.setRownumber("001");
//			parameterVoucher.setStrType("1");
//			VoucherItem voucherItem1 = this.getProfitAndLossVoucherItem(parameterVoucher);
//			voucherItem1.setVoucher(voucher);
//			viSet.add(voucherItem1);			
//			parameterVoucher.setRownumber("002");
//			parameterVoucher.setStrType("2");
//			VoucherItem voucherItem2 = this.getProfitAndLossVoucherItem(parameterVoucher);
//			voucherItem2.setVoucher(voucher);
//			viSet.add(voucherItem2);
//		}
//		voucher.setVoucherItem(viSet);
//		//判断是否已经生成清账凭证，
//		Voucher clearVoucher2=null;
//		List<Voucher> li=voucherHibernateDao.getVoucherByBusinessId2(parameterVoucher.getBusinessid(),"A");
//		if(li!=null && li.size()!=0){				
//			for(Voucher vo : li){
//				if(!StringUtils.isNullBlank(vo.getVoucherno()) && "9".equals(vo.getVoucherclass())){
//					clearVoucher2=vo;
//					break;
//				}
//			} 
//		}
//		if(null == clearVoucher2){
//			this.voucherService._saveOrUpdate(voucher, null, BusinessObjectService.getBusinessObject("Voucher"));
//		}else{
//			voucher = clearVoucher2;
//		}
//		
//		return voucher;
//	}
	
	/***
	 * 
	 * @param viSet
	 * @param vi
	 * @return
	 */
	public boolean isExistSet(Set<VoucherItem> viSet,VoucherItem vi){
		for(VoucherItem item:viSet){
			if(item.getRownumber().equals(vi.getRownumber()) && item.getFiyear().equals(vi.getFiyear()) && item.getVoucherno().equals(vi.getVoucherno())){
				return true;
			}
		}
		return false;
	}
}
