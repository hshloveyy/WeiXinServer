/*
 * @(#)HomeCreditPaymentService.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2013年11月19日 11点35分25秒
 *  描　述：创建
 */
package com.infolion.xdss3.payment.service;

import net.sf.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;

import com.infolion.platform.console.sys.context.UserContextHolder;
import com.infolion.platform.dpframework.util.DateUtils;
import com.infolion.platform.dpframework.util.StringUtils;
import com.infolion.platform.dpframework.core.BusinessException;
import com.infolion.platform.dpframework.core.Constants;
import com.infolion.platform.dpframework.core.service.BaseService;
import com.infolion.platform.dpframework.uicomponent.number.service.NumberService;
import com.infolion.platform.workflow.engine.WorkflowService;
import com.infolion.platform.dpframework.uicomponent.lock.LockService;
import com.infolion.platform.dpframework.uicomponent.lock.LockException;
import com.infolion.platform.basicApp.authManage.domain.OperationType;
import com.infolion.platform.console.org.dao.SysDeptJdbcDao;
import com.infolion.platform.console.sys.context.UserContext;
import com.infolion.platform.dpframework.core.service.AdvanceService;
import com.infolion.xdss3.masterData.dao.VendorTitleHibernateDao;
import com.infolion.xdss3.masterData.dao.VendorTitleJdbcDao;
import com.infolion.xdss3.payment.domain.ClearVoucherItemStruct;
import com.infolion.xdss3.payment.domain.HomeCreditPayment;
import com.infolion.xdss3.payment.domain.ImportPayment;

import com.infolion.xdss3.payment.service.HomeCreditPaymentService;
import com.infolion.xdss3.payment.dao.HomeCreditPaymentHibernateDao;
import com.infolion.xdss3.payment.dao.PayMentCBillJdbcDao;
import com.infolion.xdss3.paymentGen.service.HomeCreditPaymentServiceGen;
          
import com.infolion.xdss3.payment.domain.HomeCreditPayItem;
import com.infolion.xdss3.payment.service.HomeCreditPayItemService;
          
import com.infolion.xdss3.payment.domain.HomeCreditPayCbill;
import com.infolion.xdss3.payment.service.HomeCreditPayCbillService;
          
import com.infolion.xdss3.payment.domain.HomeCreditBankItem;
import com.infolion.xdss3.payment.service.HomeCreditBankItemService;
          
import com.infolion.xdss3.payment.domain.HomeCreditDocuBank;
import com.infolion.xdss3.payment.service.HomeCreditDocuBankService;
          
import com.infolion.xdss3.bankInfo.dao.BankInfoJdbcDao;
import com.infolion.xdss3.billPayReBankItem.domain.BillPayReBankItem;
import com.infolion.xdss3.collect.dao.CollectJdbcDao;
import com.infolion.xdss3.customerDrawback.dao.CustomerRefundItemJdbcDao;
import com.infolion.xdss3.financeSquare.domain.SettleSubject;
import com.infolion.xdss3.financeSquare.service.SettleSubjectService;
          
import com.infolion.xdss3.financeSquare.domain.FundFlow;
import com.infolion.xdss3.financeSquare.service.FundFlowService;
          
import com.infolion.platform.dpframework.uicomponent.attachement.domain.Attachement;
import com.infolion.platform.dpframework.uicomponent.attachement.service.AttachementService;
import com.infolion.platform.dpframework.uicomponent.attachement.dao.AttachementJdbcDao;
          
import com.infolion.xdss3.payment.domain.HomeCreditRebank;
import com.infolion.xdss3.payment.service.HomeCreditRebankService;
          
import com.infolion.xdss3.payment.domain.HomeCreditRelatPay;
import com.infolion.xdss3.payment.service.HomeCreditRelatPayService;
import com.infolion.xdss3.voucher.dao.VoucherHibernateDao;
import com.infolion.xdss3.voucher.dao.VoucherJdbcDao;
import com.infolion.xdss3.voucher.domain.Voucher;
import com.infolion.xdss3.voucher.service.VoucherService;
import com.infolion.xdss3.voucheritem.dao.VoucherItemJdbcDao;
import com.infolion.xdss3.voucheritem.domain.VoucherItem;

/**
 * <pre>
 * 国内信用证(HomeCreditPayment)服务类
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
public class HomeCreditPaymentService extends HomeCreditPaymentServiceGen
{
	private Log log = LogFactory.getFactory().getLog(this.getClass());
	
	@Autowired
	private VendorTitleHibernateDao vendorTitleHibernateDao;

	public void setVendorTitleHibernateDao(VendorTitleHibernateDao vendorTitleHibernateDao)
	{
		this.vendorTitleHibernateDao = vendorTitleHibernateDao;
	}
	
	@Autowired
	private VoucherHibernateDao voucherHibernateDao;	

	/**
	 * 凭证抬头
	 * @param voucherHibernateDao the voucherHibernateDao to set
	 */
	public void setVoucherHibernateDao(VoucherHibernateDao voucherHibernateDao) {
		this.voucherHibernateDao = voucherHibernateDao;
	}
	
	@Autowired
	private VendorTitleJdbcDao vendorTitleJdbcDao;

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
	private CustomerRefundItemJdbcDao customerRefundItemJdbcDao;
	
	/**
	 * @param customerRefundItemJdbcDao the customerRefundItemJdbcDao to set
	 */
	public void setCustomerRefundItemJdbcDao(
			CustomerRefundItemJdbcDao customerRefundItemJdbcDao) {
		this.customerRefundItemJdbcDao = customerRefundItemJdbcDao;
	}
	
	@Autowired
	private VoucherJdbcDao voucherJdbcDao;
	
	/**
	 * @param voucherJdbcDao the voucherJdbcDao to set
	 */
	public void setVoucherJdbcDao(VoucherJdbcDao voucherJdbcDao) {
		this.voucherJdbcDao = voucherJdbcDao;
	}
	
	@Autowired
	protected VoucherItemJdbcDao voucherItemJdbcDao;
	public void setVoucherItemJdbcDao(VoucherItemJdbcDao voucherItemJdbcDao) {
		this.voucherItemJdbcDao = voucherItemJdbcDao;
	}
	
	@Autowired
	private BankInfoJdbcDao bankInfoJdbcDao;
	
	public void setBankInfoJdbcDao(BankInfoJdbcDao bankInfoJdbcDao)
	{
		this.bankInfoJdbcDao = bankInfoJdbcDao;
	}
	
	@Autowired
	private PayMentCBillJdbcDao homePayMentCBillJdbcDao;

	public void setHomePayMentCBillJdbcDao(PayMentCBillJdbcDao hmportPayMentCBillJdbcDao)
	{
		this.homePayMentCBillJdbcDao = hmportPayMentCBillJdbcDao;
	}
	
	@Autowired
	private CollectJdbcDao collectJdbcDao;
	
	public void setCollectJdbcDao(CollectJdbcDao collectJdbcDao) {
		this.collectJdbcDao = collectJdbcDao;
	}
	@Autowired
	private VoucherService voucherService;

	public void setVoucherService(VoucherService voucherService) {
		this.voucherService = voucherService;
	}

	
	/**
	 * 人民币模拟凭证
	 * @param homeCreditPayment
	 * @return
	 */
	public List<String> cnySaveVoucher(HomeCreditPayment homePayment){
		List<String> retList = new ArrayList<String>();
		String bukrs = getCompanyIDByDeptID(homePayment.getDept_id());
		//this.voucherJdbcDao.deleteVoucherByBusinessidAndClass(homePayment.getPaymentid(), "1");
		
		//凭证抬头
		Voucher voucher = new Voucher();
		//凭证行项目
		Set<VoucherItem> voucherItemList = new HashSet<VoucherItem>();
		
		voucher = this.getVoucher(homePayment, "1");
		
		
		int rowNo = 0;
		//******************供应商行项目凭证行项目**********************************//
		Iterator<HomeCreditPayItem> PayItemItemit = homePayment.getHomeCreditPayItem() .iterator();		
		while(PayItemItemit.hasNext()){
			rowNo = rowNo + 1;
			HomeCreditPayItem homePaymentItem = PayItemItemit.next();
			
			VoucherItem voucherItem = new VoucherItem();
			voucherItem.setVoucher(voucher);
			//客户记账码
			voucherItem.setCheckcode("21");
			//特殊G/L标识
			voucherItem.setGlflag("");
			//科目
			voucherItem.setSubject(homePayment.getSupplier());
			//科目说明
			String Subjectdescribe = this.customerRefundItemJdbcDao.getSupplierDescByCustomerId(homePayment.getSupplier(),bukrs);
			voucherItem.setSubjectdescribe(Subjectdescribe);
			//货币
			voucherItem.setCurrency(homePayment.getFactcurrency());
			//货币金额
			voucherItem.setAmount(homePaymentItem.getAssignamount());
			//本位币金额
			voucherItem.setAmount2(homePaymentItem.getAssignamount2());
			//部门
			voucherItem.setDepid(this.sysDeptJdbcDao.getDeptCodeById(homePayment.getDept_id()));
			//文本
			voucherItem.setText(homePayment.getText());
			//现金流项目
			voucherItem.setCashflowitem("");
			//反记账标识
			voucherItem.setUncheckflag("");
			String subject = this.customerRefundItemJdbcDao.getSubjectBySuppliers(homePayment.getSupplier(),bukrs);
			subject = this.customerRefundItemJdbcDao.getSkont(voucherItem,subject);
			//统驭项目
			voucherItem.setControlaccount(subject);
			//统驭科目说明
			voucherItem.setCaremark(this.customerRefundItemJdbcDao.getSubjectNameById(subject,bukrs));
			//销售订单
			voucherItem.setSalesorder("");
			//销售订单行项目号
			voucherItem.setOrderrowno("");
			//利润中心
			voucherItem.setProfitcenter("");
			//成本中心
			voucherItem.setCostcenter("");
			//借/贷标识符
			voucherItem.setDebitcredit("S");
			//行号
			voucherItem.setRownumber(String.valueOf(rowNo));
			//到期日
			voucherItem.setZfbdt(homePayment.getDraftdate().replace("-", ""));
			//付款行项目信息
			voucherItem.setBusinessitemid(homePaymentItem.getPaymentitemid());
			
			voucherItemList.add(voucherItem);
		}
		//******************供应商行项目凭证行项目**********************************//
		
		if(homePayment.getPaymenttype().equals("08")){
			rowNo = rowNo + 1;
			VoucherItem voucherItem = new VoucherItem();
			voucherItem.setVoucher(voucher);
			//客户记账码
			voucherItem.setCheckcode("50");
			//特殊G/L标识
			voucherItem.setGlflag("");
			//科目
			voucherItem.setSubject("1001010101");
			String subjectdescribe = this.customerRefundItemJdbcDao.getSubjectNameById("1001010101",bukrs);
			//科目说明
			voucherItem.setSubjectdescribe(subjectdescribe);
			//货币
			voucherItem.setCurrency(homePayment.getFactcurrency());
			//货币金额
			voucherItem.setAmount(homePayment.getFactamount());
			//本位币金额
			voucherItem.setAmount2(homePayment.getFactamount());
			//部门
			voucherItem.setDepid(this.sysDeptJdbcDao.getDeptCodeById(homePayment.getDept_id()));
			//文本
			voucherItem.setText(homePayment.getText());
			//现金流项目
			voucherItem.setCashflowitem("101");
			//反记账标识
			voucherItem.setUncheckflag("");
			//统驭项目
			voucherItem.setControlaccount("1001010101");
			//统驭科目说明
			voucherItem.setCaremark(subjectdescribe);
			//销售订单
			voucherItem.setSalesorder("");
			//销售订单行项目号
			voucherItem.setOrderrowno("");
			//利润中心
			voucherItem.setProfitcenter("");
			//成本中心
			voucherItem.setCostcenter("");
			//借/贷标识符
			voucherItem.setDebitcredit("H");
			//行号
			voucherItem.setRownumber(String.valueOf(rowNo));
			
			voucherItemList.add(voucherItem);

		}else{
			//******************付款银行行项目凭证行项目*******************************//
			Iterator<HomeCreditBankItem> bankItemit = homePayment.getHomeCreditBankItem().iterator();		
			while(bankItemit.hasNext()){
				rowNo = rowNo + 1;
				HomeCreditBankItem homePayBankItem = bankItemit.next();
				
				VoucherItem BankVoucherItem = new VoucherItem();
				BankVoucherItem.setVoucher(voucher);
				//客户记账码
				BankVoucherItem.setCheckcode("50");
				//特殊G/L标识
				BankVoucherItem.setGlflag("");
				String subjectdesc = this.customerRefundItemJdbcDao.getSubjectNameById(homePayBankItem.getPaybanksubject(),bukrs);
				//科目
				BankVoucherItem.setSubject(homePayBankItem.getPaybanksubject());
				//科目说明
				BankVoucherItem.setSubjectdescribe(subjectdesc);
				//货币
				BankVoucherItem.setCurrency(homePayment.getFactcurrency());
				//货币金额
				BankVoucherItem.setAmount(homePayBankItem.getPayamount());
				//本位币金额
				BankVoucherItem.setAmount2(homePayBankItem.getPayamount2());
				//部门
				BankVoucherItem.setDepid(this.sysDeptJdbcDao.getDeptCodeById(homePayment.getDept_id()));
				//文本
				BankVoucherItem.setText(homePayment.getText());
				//现金流项目
				BankVoucherItem.setCashflowitem(homePayBankItem.getCashflowitem());
				//反记账标识
				BankVoucherItem.setUncheckflag("");
				//统驭项目
				BankVoucherItem.setControlaccount(homePayBankItem.getPaybanksubject());
				//统驭科目说明
				BankVoucherItem.setCaremark(subjectdesc);
				//销售订单
				BankVoucherItem.setSalesorder("");
				//销售订单行项目号
				BankVoucherItem.setOrderrowno("");
				//利润中心
				BankVoucherItem.setProfitcenter("");
				//成本中心
				BankVoucherItem.setCostcenter("");
				//借/贷标识符
				BankVoucherItem.setDebitcredit("H");
				//行号
				BankVoucherItem.setRownumber(String.valueOf(rowNo));
				
				voucherItemList.add(BankVoucherItem);
			}
			//******************付款银行行项目凭证行项目*******************************//
		}
		
		//******************结算科目行项目凭证行项目*******************************//
		Set<VoucherItem> SettlesubjectVoucherItemList = new HashSet<VoucherItem>();
		
		SettlesubjectVoucherItemList = this.getSettSubjVoucherItem(homePayment.getSettleSubject(),bukrs);
		
		Iterator<VoucherItem> Settlesubjectit = SettlesubjectVoucherItemList.iterator();		
		while(Settlesubjectit.hasNext()){
			rowNo = rowNo + 1;
			VoucherItem setvoucherItem =Settlesubjectit.next();
			setvoucherItem.setRownumber(String.valueOf(rowNo));
			setvoucherItem.setVoucher(voucher);
			
			voucherItemList.add(setvoucherItem);
		}
		//******************结算科目行项目凭证行项目*******************************//
		
		//******************特殊总帐行项目凭证行项目*******************************//
		Set<VoucherItem> FundFlowVoucherItemList = new HashSet<VoucherItem>();
		
		FundFlowVoucherItemList = this.getFundFlowVoucherItem(homePayment);
		
		Iterator<VoucherItem> FundFlowit = FundFlowVoucherItemList.iterator();		
		while(FundFlowit.hasNext()){
			rowNo = rowNo + 1;
			VoucherItem funvoucherItem =FundFlowit.next();
			funvoucherItem.setRownumber(String.valueOf(rowNo));
			funvoucherItem.setVoucher(voucher);
			
			voucherItemList.add(funvoucherItem);
		}
		//******************特殊总帐行项目凭证行项目*******************************//
		
		voucher.setVoucherItem(voucherItemList);		
		this.voucherHibernateDao.save(voucher);
		retList.add(voucher.getVoucherid());
		
		return retList;
	}
	
	/**
	 * 应付票据模拟凭证
	 * @param homeCreditPayment
	 * @return
	 */
	public List<String> dealBillSaveVoucher(HomeCreditPayment homePayment){
		List<String> retList = new ArrayList<String>();
		String bukrs = getCompanyIDByDeptID(homePayment.getDept_id());
		String depid = this.sysDeptJdbcDao.getDeptCodeById(homePayment.getDept_id());
		//this.voucherJdbcDao.deleteVoucherByBusinessidAndClass(homePayment.getPaymentid(), "1");
		
		//凭证抬头
		Voucher voucher = new Voucher();
		//凭证行项目
		Set<VoucherItem> voucherItemList = new HashSet<VoucherItem>();
		
		voucher = this.getVoucher(homePayment, "1");
		voucher.setPay("X");
		voucher.setFlag("W");
		
		int rowNo = 0;
		//******************供应商行项目凭证行项目**********************************//
		Iterator<HomeCreditPayItem> PayItemItemit = homePayment.getHomeCreditPayItem() .iterator();		
		while(PayItemItemit.hasNext()){
			rowNo = rowNo + 1;
			HomeCreditPayItem homePaymentItem = PayItemItemit.next();
			
			VoucherItem voucherItem = new VoucherItem();
			voucherItem.setVoucher(voucher);
			//客户记账码
			voucherItem.setCheckcode("21");
			//特殊G/L标识
			voucherItem.setGlflag("");
			//科目
			voucherItem.setSubject(homePayment.getSupplier());
			//科目说明
			String Subjectdescribe = this.customerRefundItemJdbcDao.getSupplierDescByCustomerId(homePayment.getSupplier(),bukrs);
			voucherItem.setSubjectdescribe(Subjectdescribe);
			//货币
			voucherItem.setCurrency("CNY");
			//货币金额
			voucherItem.setAmount(homePaymentItem.getAssignamount());
			//本位币金额
			voucherItem.setAmount2(homePaymentItem.getAssignamount2());
			//部门
			voucherItem.setDepid(depid);
			//文本
			voucherItem.setText(homePayment.getText());
			//现金流项目
			voucherItem.setCashflowitem("");
			//反记账标识
			voucherItem.setUncheckflag("");
			String subject = this.customerRefundItemJdbcDao.getSubjectBySuppliers(homePayment.getSupplier(),bukrs);
			subject = this.customerRefundItemJdbcDao.getSkont(voucherItem,subject);
			//统驭项目
			voucherItem.setControlaccount(subject);
			//统驭科目说明
			voucherItem.setCaremark(this.customerRefundItemJdbcDao.getSubjectNameById(subject,bukrs));
			//销售订单
			voucherItem.setSalesorder("");
			//销售订单行项目号
			voucherItem.setOrderrowno("");
			//利润中心
			voucherItem.setProfitcenter("");
			//成本中心
			voucherItem.setCostcenter("");
			//借/贷标识符
			voucherItem.setDebitcredit("S");
			//行号
			voucherItem.setRownumber(String.valueOf(rowNo));
			//是否票据标志
			voucherItem.setFlag("W");
			//付款行项目信息
			voucherItem.setBusinessitemid(homePaymentItem.getPaymentitemid());
			//业务范围
			voucherItem.setGsber(depid);
			//票据到期日
			voucherItem.setZfbdt(homePayment.getDraftdate().replace("-", ""));
			
			voucherItemList.add(voucherItem);
		}
		//******************供应商行项目凭证行项目**********************************//
		
		//******************供应商行项目凭证行项目**********************************//
		//Iterator<HomePaymentItem> PayItemItemit2 = homePayment.getHomePaymentItem() .iterator();		
		//while(PayItemItemit2.hasNext()){
			rowNo = rowNo + 1;
			//HomePaymentItem homePaymentItem = PayItemItemit2.next();
			
			VoucherItem voucherItem = new VoucherItem();
			voucherItem.setVoucher(voucher);
			//客户记账码
			voucherItem.setCheckcode("39");
			//特殊G/L标识
			voucherItem.setGlflag("W");
			//国内信用证
			if(homePayment.getPaymenttype().equals("06")){
				//科目
				voucherItem.setSubject(homePayment.getTicketbankid());
				//科目说明
				String Subjectdescribe = this.customerRefundItemJdbcDao.getSupplierDescByCustomerId(homePayment.getTicketbankid(),bukrs);
				voucherItem.setSubjectdescribe(Subjectdescribe);
				String subject = this.customerRefundItemJdbcDao.getSubjectBySuppliers(homePayment.getTicketbankid(),bukrs);
				subject = this.customerRefundItemJdbcDao.getSkont(voucherItem,subject);
				//统驭项目
				voucherItem.setControlaccount(subject);
				//统驭科目说明
				voucherItem.setCaremark(this.customerRefundItemJdbcDao.getSubjectNameById(subject,bukrs));
			}//银行/商业承兑汇票
			else if(homePayment.getPaymenttype().equals("07")){
				//科目
				voucherItem.setSubject(homePayment.getSupplier());
				//科目说明
				String Subjectdescribe = this.customerRefundItemJdbcDao.getSupplierDescByCustomerId(homePayment.getSupplier(),bukrs);
				voucherItem.setSubjectdescribe(Subjectdescribe);
				String subject = this.customerRefundItemJdbcDao.getSubjectBySuppliers(homePayment.getSupplier(),bukrs);
				subject = this.customerRefundItemJdbcDao.getSkont(voucherItem,subject);
				//统驭项目
				voucherItem.setControlaccount(subject);
				//统驭科目说明
				voucherItem.setCaremark(this.customerRefundItemJdbcDao.getSubjectNameById(subject,bukrs));
			}

			//货币
			voucherItem.setCurrency("CNY");
			//货币金额
			voucherItem.setAmount(homePayment.getFactamount());
			//本位币金额
			voucherItem.setAmount2(homePayment.getFactamount());
			//部门
			voucherItem.setDepid(homePayment.getBillbc());
			//文本
			voucherItem.setText(homePayment.getText());
			//现金流项目
			voucherItem.setCashflowitem("");
			//反记账标识
			voucherItem.setUncheckflag("");
			//销售订单
			voucherItem.setSalesorder("");
			//销售订单行项目号
			voucherItem.setOrderrowno("");
			//利润中心
			voucherItem.setProfitcenter("");
			//成本中心
			voucherItem.setCostcenter("");
			//借/贷标识符
			voucherItem.setDebitcredit("H");
			//行号
			voucherItem.setRownumber(String.valueOf(rowNo));
			//是否票据标志
			voucherItem.setFlag("W");
			//付款行项目信息
			voucherItem.setBusinessitemid("");
			//业务范围
			voucherItem.setGsber(homePayment.getBillbc());
			//票据到期日
			voucherItem.setZfbdt(homePayment.getDraftdate().replace("-", ""));
			
			voucherItemList.add(voucherItem);
		//}
		//******************供应商行项目凭证行项目**********************************//
		
		
		//******************结算科目行项目凭证行项目*******************************//
		Set<VoucherItem> SettlesubjectVoucherItemList = new HashSet<VoucherItem>();
		
		SettlesubjectVoucherItemList = this.getSettSubjVoucherItem(homePayment.getSettleSubject(),bukrs);
		
		Iterator<VoucherItem> Settlesubjectit = SettlesubjectVoucherItemList.iterator();		
		while(Settlesubjectit.hasNext()){
			rowNo = rowNo + 1;
			VoucherItem setvoucherItem =Settlesubjectit.next();
			setvoucherItem.setRownumber(String.valueOf(rowNo));
			//是否票据标志
			setvoucherItem.setFlag("W");
			//业务范围
			setvoucherItem.setDepid(depid);
			//业务范围
			setvoucherItem.setGsber(depid);
			//票据到期日
			setvoucherItem.setZfbdt(homePayment.getDraftdate().replace("-", ""));
			setvoucherItem.setVoucher(voucher);
			
			voucherItemList.add(setvoucherItem);
		}
		//******************结算科目行项目凭证行项目*******************************//
		
		//******************特殊总帐行项目凭证行项目*******************************//
		Set<VoucherItem> FundFlowVoucherItemList = new HashSet<VoucherItem>();
		
		FundFlowVoucherItemList = this.getFundFlowVoucherItem(homePayment);
		
		Iterator<VoucherItem> FundFlowit = FundFlowVoucherItemList.iterator();		
		while(FundFlowit.hasNext()){
			rowNo = rowNo + 1;
			VoucherItem funvoucherItem =FundFlowit.next();
			//是否票据标志
			funvoucherItem.setFlag("W");
			//业务范围
			funvoucherItem.setDepid(depid);
			//业务范围
			funvoucherItem.setGsber(depid);
			//票据到期日
			funvoucherItem.setZfbdt(homePayment.getDraftdate().replace("-", ""));
			funvoucherItem.setRownumber(String.valueOf(rowNo));
			funvoucherItem.setVoucher(voucher);
			
			voucherItemList.add(funvoucherItem);
		}
		//******************特殊总帐行项目凭证行项目*******************************//
		
		
		voucher.setVoucherItem(voucherItemList);		
		this.voucherHibernateDao.save(voucher);
		retList.add(voucher.getVoucherid());
		
		return retList;
	}
	
	/**
	 * 应付票据清帐模拟凭证
	 * @param homeCreditPayment
	 * @return
	 */
	public List<String> dealBillClearSaveVoucher(HomeCreditPayment homePayment){
		List<String> retList = new ArrayList<String>();
		String depId = this.sysDeptJdbcDao.getDeptCodeById(homePayment.getDept_id());
		String bukrs = getCompanyIDByDeptID(homePayment.getDept_id());
		
		//this.voucherJdbcDao.deleteVoucherByBusinessidAndClass(homePayment.getPaymentid(), "4");
		
		//凭证抬头
		Voucher voucher = new Voucher();
		//凭证行项目
		Set<VoucherItem> voucherItemList = new HashSet<VoucherItem>();
		
		voucher = this.getVoucher(homePayment, "4");
		voucher.setPay("X");
		voucher.setFlag("W");
		int rowNo = 0;
		
		rowNo = rowNo + 1;
		
		VoucherItem voucherItem = new VoucherItem();
		voucherItem.setVoucher(voucher);
		//客户记账码
		voucherItem.setCheckcode("29");
		//特殊G/L标识
		voucherItem.setGlflag("W");
		//收付款清帐标识
		voucherItem.setFlag("W");
		//国内信用证
		if(homePayment.getPaymenttype().equals("06")){
			//科目
			voucherItem.setSubject(homePayment.getTicketbankid());
			//科目说明
			String Subjectdescribe = this.customerRefundItemJdbcDao.getSupplierDescByCustomerId(homePayment.getTicketbankid(),bukrs);
			voucherItem.setSubjectdescribe(Subjectdescribe);
			String subject = this.customerRefundItemJdbcDao.getSubjectBySuppliers(homePayment.getTicketbankid(),bukrs);
			subject = this.customerRefundItemJdbcDao.getSkont(voucherItem,subject);
			//统驭项目
			voucherItem.setControlaccount(subject);
			//统驭科目说明
			voucherItem.setCaremark(this.customerRefundItemJdbcDao.getSubjectNameById(subject,bukrs));
		}//银行/商业承兑汇票
		else if(homePayment.getPaymenttype().equals("07")){
			//科目
			voucherItem.setSubject(homePayment.getSupplier());
			//科目说明
			String Subjectdescribe = this.customerRefundItemJdbcDao.getSupplierDescByCustomerId(homePayment.getSupplier(),bukrs);
			voucherItem.setSubjectdescribe(Subjectdescribe);
			String subject = this.customerRefundItemJdbcDao.getSubjectBySuppliers(homePayment.getSupplier(),bukrs);
			subject = this.customerRefundItemJdbcDao.getSkont(voucherItem,subject);
			//统驭项目
			voucherItem.setControlaccount(subject);
			//统驭科目说明
			voucherItem.setCaremark(this.customerRefundItemJdbcDao.getSubjectNameById(subject,bukrs));
		}
		//货币
		voucherItem.setCurrency("CNY");
		//取得票据的凭证
		List<VoucherItem>  voucheritemList2 = this.voucherItemJdbcDao.getVoucherItemList(homePayment.getPaymentid(),"02");
		for (Iterator<VoucherItem> iter = voucheritemList2.iterator(); iter.hasNext();)
		{
			VoucherItem _voucherItem = (VoucherItem) iter.next();
			if(_voucherItem.getCheckcode().equals("39")){
				//货币金额
				voucherItem.setAmount(_voucherItem.getAmount());
				//本位币金额
				voucherItem.setAmount2(_voucherItem.getAmount2());
				break;
			}
		}
		//部门
		voucherItem.setDepid(depId);
		//业务范围
		voucherItem.setGsber(depId);
		//票据到期日
		voucherItem.setZfbdt(homePayment.getDraftdate().replace("-", ""));
		//文本
		voucherItem.setText(homePayment.getText());
		//现金流项目
		voucherItem.setCashflowitem("");
		//反记账标识
		voucherItem.setUncheckflag("");
		//销售订单
		voucherItem.setSalesorder("");
		//销售订单行项目号
		voucherItem.setOrderrowno("");
		//利润中心
		voucherItem.setProfitcenter("");
		//成本中心
		voucherItem.setCostcenter("");
		//借/贷标识符
		voucherItem.setDebitcredit("S");
		//行号
		voucherItem.setRownumber(String.valueOf(rowNo));
		
		voucherItemList.add(voucherItem);
		
		//******************付款银行行项目凭证行项目*******************************//
		Iterator<HomeCreditBankItem> bankItemit = homePayment.getHomeCreditBankItem().iterator();		
		while(bankItemit.hasNext()){
			rowNo = rowNo + 1;
			HomeCreditBankItem homePayBankItem = bankItemit.next();
			
			VoucherItem BankVoucherItem = new VoucherItem();
			BankVoucherItem.setVoucher(voucher);
			//客户记账码
			BankVoucherItem.setCheckcode("50");
			//特殊G/L标识
			BankVoucherItem.setGlflag("");
			//收付款清帐标识
			BankVoucherItem.setFlag("W");
			String subjectname = this.customerRefundItemJdbcDao.getSubjectNameById(homePayBankItem.getPaybanksubject(),bukrs);
			//科目
			BankVoucherItem.setSubject(homePayBankItem.getPaybanksubject());
			//科目说明
			BankVoucherItem.setSubjectdescribe(subjectname);
			//货币
			BankVoucherItem.setCurrency("CNY");
			//货币金额
			BankVoucherItem.setAmount(homePayBankItem.getPayamount());
			//本位币金额
			BankVoucherItem.setAmount2(homePayBankItem.getPayamount2());
			//部门
			BankVoucherItem.setDepid(depId);
			//业务范围
			BankVoucherItem.setGsber(depId);
			//票据到期日
			BankVoucherItem.setZfbdt(homePayment.getDraftdate().replace("-", ""));
			//文本
			BankVoucherItem.setText(homePayment.getText());
			//现金流项目
			BankVoucherItem.setCashflowitem(homePayBankItem.getCashflowitem());
			//反记账标识
			BankVoucherItem.setUncheckflag("");
			//统驭项目
			BankVoucherItem.setControlaccount(homePayBankItem.getPaybanksubject());
			//统驭科目说明
			BankVoucherItem.setCaremark(subjectname);
			//销售订单
			BankVoucherItem.setSalesorder("");
			//销售订单行项目号
			BankVoucherItem.setOrderrowno("");
			//利润中心
			BankVoucherItem.setProfitcenter("");
			//成本中心
			BankVoucherItem.setCostcenter("");
			//借/贷标识符
			BankVoucherItem.setDebitcredit("H");
			//行号
			BankVoucherItem.setRownumber(String.valueOf(rowNo));
			
			voucherItemList.add(BankVoucherItem);
		}
		//******************付款银行行项目凭证行项目*******************************//
		
		//******************结算科目行项目凭证行项目*******************************//
		Set<VoucherItem> SettlesubjectVoucherItemList = new HashSet<VoucherItem>();
		
		SettlesubjectVoucherItemList = this.getSettSubjVoucherItem(homePayment.getSettleSubject(),bukrs);
		
		Iterator<VoucherItem> Settlesubjectit = SettlesubjectVoucherItemList.iterator();		
		while(Settlesubjectit.hasNext()){
			rowNo = rowNo + 1;
			VoucherItem setvoucherItem =Settlesubjectit.next();
			setvoucherItem.setRownumber(String.valueOf(rowNo));
			setvoucherItem.setVoucher(voucher);
			
			voucherItemList.add(setvoucherItem);
		}
		//******************结算科目行项目凭证行项目*******************************//
		
		voucher.setVoucherItem(voucherItemList);		
		this.voucherHibernateDao.save(voucher);
		retList.add(voucher.getVoucherid());
		
		
		//******************************应付票据清帐凭证*************
		//this.voucherJdbcDao.deleteVoucherByBusinessidAndClass(homePayment.getPaymentid(), "8");
		Voucher voucher2 = this.voucherService.getVoucher(homePayment.getPaymentid(), "02", "4");
		if(voucher2!=null && !StringUtils.isNullBlank(voucher2.getVoucherno())){
			voucher = voucher2;
		}
		Voucher clearVoucher = new Voucher();
		clearVoucher = getBillClearVoucher(homePayment,"8",voucher,"N");
		clearVoucher.setAgkon(homePayment.getTicketbankid());
		clearVoucher.setAgums("W");
		this.voucherHibernateDao.save(clearVoucher);
		retList.add(clearVoucher.getVoucherid());
		//******************************应付票据清帐凭证*************
		
		return retList;
	}
	
	/**
	 * 根据部门ID获得公司ID
	 */
	public String getCompanyIDByDeptID(String deptID)
	{
		return this.sysDeptJdbcDao.getCompanyCode(deptID);
	}
	
	/**
	 * 得到付款的凭证抬头
	 * @param homeCreditPayment
	 * @return
	 */
	public Voucher getVoucher(HomeCreditPayment homePayment,String strClass){
		Voucher voucher = new Voucher();
		
		UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
		
		//业务编号
		voucher.setBusinessid(homePayment.getPaymentid());
		//业务类型
		voucher.setBusinesstype("02");
		//过账日期
		voucher.setCheckdate(homePayment.getAccountdate().replace("-", ""));
		//公司代码
		String bukrs = getCompanyIDByDeptID(homePayment.getDept_id());
		voucher.setCompanycode(bukrs); //公司代码
		//货币
		voucher.setCurrency(homePayment.getFactcurrency());
		//汇率
		voucher.setExchangerate(homePayment.getCloseexchangerat());
		//会计期间
		voucher.setFiperiod(homePayment.getAccountdate().substring(5, 7));
		//会计年度
		voucher.setFiyear(homePayment.getAccountdate().substring(0, 4));
		//输入日期
		SimpleDateFormat  dateFormat=new SimpleDateFormat ("yyyyMMdd");
		voucher.setImportdate(dateFormat.format(new Date()));
		//输入者
		voucher.setImporter(userContext.getSysUser().getUserName());
		//预制者
		voucher.setPreparer(userContext.getSysUser().getUserName());
		//凭证日期
		voucher.setVoucherdate(homePayment.getVoucherdate().replace("-", ""));
		//凭证抬头文本
		voucher.setVouchertext(homePayment.getText());
		//凭证类型
		voucher.setVouchertype("SA");
		//凭证分类
		voucher.setVoucherclass(strClass);
		
		return voucher;
	}
	
	/**
	 * 得到结算科目的凭证行项目
	 * @param importSettSubj
	 * @return
	 */
	public Set<VoucherItem> getSettSubjVoucherItem(SettleSubject settleSubjectPay, String bukrs){
		Set<VoucherItem> voucherItemList = new HashSet<VoucherItem>();
		BigDecimal amount = new BigDecimal(0);
		BigDecimal standardAmount = new BigDecimal(0);
		if (null != settleSubjectPay)
		{
			amount = settleSubjectPay.getAmount1();
			standardAmount = settleSubjectPay.getStandardamount1();
			if (amount.abs().compareTo(new BigDecimal(0)) == 1 ||
				standardAmount.abs().compareTo(new BigDecimal(0)) == 1)
			{
				// 开始添加凭证行项目
				VoucherItem voucherItem = new VoucherItem();
				
				//客户记账码
				if ("S".equals(settleSubjectPay.getDebtcredit1()))
				{
					voucherItem.setCheckcode("40");
					//借/贷标识符
					voucherItem.setDebitcredit(settleSubjectPay.getDebtcredit1());
				}
				
				//客户记账码
				if ("H".equals(settleSubjectPay.getDebtcredit1()))
				{
					voucherItem.setCheckcode("50");
					//借/贷标识符
					voucherItem.setDebitcredit(settleSubjectPay.getDebtcredit1());
				}
				//特殊G/L标识
				voucherItem.setGlflag("");
				String subjectname = this.customerRefundItemJdbcDao.getSubjectNameById(settleSubjectPay.getSettlesubject1(),bukrs);
				//科目
				voucherItem.setSubject(settleSubjectPay.getSettlesubject1());
				//科目说明
				voucherItem.setSubjectdescribe(subjectname);
				//统驭项目
				voucherItem.setControlaccount(settleSubjectPay.getSettlesubject1());
				//统驭科目说明
				voucherItem.setCaremark(subjectname);
				//货币
				voucherItem.setCurrency(settleSubjectPay.getHomeCreditPayment().getFactcurrency());
				//货币金额
				voucherItem.setAmount(settleSubjectPay.getAmount1());
				//本位币金额
				voucherItem.setAmount2(settleSubjectPay.getStandardamount1());
				//部门
				voucherItem.setDepid(settleSubjectPay.getDepid1());
				//文本
				voucherItem.setText(settleSubjectPay.getHomeCreditPayment().getText());
				//反记账标识
				if (settleSubjectPay.getAntiaccount1() != null && 
					settleSubjectPay.getAntiaccount1().equals("Y")){
					voucherItem.setUncheckflag("X");
				}else{
					voucherItem.setUncheckflag("");
				}
				//销售订单
				voucherItem.setSalesorder(settleSubjectPay.getOrderno1());
				//销售订单行项目号
				voucherItem.setOrderrowno(settleSubjectPay.getRowno1());
				//成本中心
				voucherItem.setCostcenter(settleSubjectPay.getCostcenter1());

				voucherItemList.add(voucherItem);
			}
			
			amount = settleSubjectPay.getAmount2();
			standardAmount = settleSubjectPay.getStandardamount2();
			if (amount.abs().compareTo(new BigDecimal(0)) == 1 ||
				standardAmount.abs().compareTo(new BigDecimal(0)) == 1)
			{
				// 开始添加凭证行项目
				VoucherItem voucherItem = new VoucherItem();
				
				//客户记账码
				if ("S".equals(settleSubjectPay.getDebtcredit2()))
				{
					voucherItem.setCheckcode("40");
					//借/贷标识符
					voucherItem.setDebitcredit(settleSubjectPay.getDebtcredit2());
				}
				
				//客户记账码
				if ("H".equals(settleSubjectPay.getDebtcredit2()))
				{
					voucherItem.setCheckcode("50");
					//借/贷标识符
					voucherItem.setDebitcredit(settleSubjectPay.getDebtcredit2());
				}
				//特殊G/L标识
				voucherItem.setGlflag("");
				String subjectname = this.customerRefundItemJdbcDao.getSubjectNameById(settleSubjectPay.getSettlesubject2(),bukrs);
				//科目
				voucherItem.setSubject(settleSubjectPay.getSettlesubject2());
				//科目说明
				voucherItem.setSubjectdescribe(subjectname);
				//统驭项目
				voucherItem.setControlaccount(settleSubjectPay.getSettlesubject2());
				//统驭科目说明
				voucherItem.setCaremark(subjectname);
				//货币
				voucherItem.setCurrency(settleSubjectPay.getHomeCreditPayment().getFactcurrency());
				//货币金额
				voucherItem.setAmount(settleSubjectPay.getAmount2());
				//本位币金额
				voucherItem.setAmount2(settleSubjectPay.getStandardamount2());
				//部门
				voucherItem.setDepid(settleSubjectPay.getDepid2());
				//文本
				voucherItem.setText(settleSubjectPay.getHomeCreditPayment().getText());
				//反记账标识
				if (settleSubjectPay.getAntiaccount2() != null && 
					settleSubjectPay.getAntiaccount2().equals("Y")){
					voucherItem.setUncheckflag("X");
				}else{
					voucherItem.setUncheckflag("");
				}
				//销售订单
				voucherItem.setSalesorder(settleSubjectPay.getOrderno2());
				//销售订单行项目号
				voucherItem.setOrderrowno(settleSubjectPay.getRowno2());
				//成本中心
				voucherItem.setCostcenter(settleSubjectPay.getCostcenter2());

				voucherItemList.add(voucherItem);
			}
			
			amount = settleSubjectPay.getAmount3();
			standardAmount = settleSubjectPay.getStandardamount3();
			if (amount.abs().compareTo(new BigDecimal(0)) == 1 ||
				standardAmount.abs().compareTo(new BigDecimal(0)) == 1)
			{
				// 开始添加凭证行项目
				VoucherItem voucherItem = new VoucherItem();
				
				//客户记账码
				if ("S".equals(settleSubjectPay.getDebtcredit3()))
				{
					voucherItem.setCheckcode("40");
					//借/贷标识符
					voucherItem.setDebitcredit(settleSubjectPay.getDebtcredit3());
				}
				
				//客户记账码
				if ("H".equals(settleSubjectPay.getDebtcredit3()))
				{
					voucherItem.setCheckcode("50");
					//借/贷标识符
					voucherItem.setDebitcredit(settleSubjectPay.getDebtcredit3());
				}
				//特殊G/L标识
				voucherItem.setGlflag("");
				String subjectname = this.customerRefundItemJdbcDao.getSubjectNameById(settleSubjectPay.getSettlesubject3(),bukrs);
				//科目
				voucherItem.setSubject(settleSubjectPay.getSettlesubject3());
				//科目说明
				voucherItem.setSubjectdescribe(subjectname);
				//统驭项目
				voucherItem.setControlaccount(settleSubjectPay.getSettlesubject3());
				//统驭科目说明
				voucherItem.setCaremark(subjectname);
				//货币
				voucherItem.setCurrency(settleSubjectPay.getHomeCreditPayment().getFactcurrency());
				//货币金额
				voucherItem.setAmount(settleSubjectPay.getAmount3());
				//本位币金额
				voucherItem.setAmount2(settleSubjectPay.getStandardamount3());
				//部门
				voucherItem.setDepid(settleSubjectPay.getDepid3());
				//文本
				voucherItem.setText(settleSubjectPay.getHomeCreditPayment().getText());
				//反记账标识
				if (settleSubjectPay.getAntiaccount3() != null && 
					settleSubjectPay.getAntiaccount3().equals("Y")){
					voucherItem.setUncheckflag("X");
				}else{
					voucherItem.setUncheckflag("");
				}
				//销售订单
				voucherItem.setSalesorder(settleSubjectPay.getOrderno3());
				//销售订单行项目号
				voucherItem.setOrderrowno(settleSubjectPay.getRowno3());
				//成本中心
				voucherItem.setCostcenter(settleSubjectPay.getCostcenter3());

				voucherItemList.add(voucherItem);
			}
			
			amount = settleSubjectPay.getAmount4();
			standardAmount = settleSubjectPay.getStandardamount4();
			if (amount.abs().compareTo(new BigDecimal(0)) == 1 ||
				standardAmount.abs().compareTo(new BigDecimal(0)) == 1)
			{
				// 开始添加凭证行项目
				VoucherItem voucherItem = new VoucherItem();
				
				//客户记账码
				if ("S".equals(settleSubjectPay.getDebtcredit4()))
				{
					voucherItem.setCheckcode("40");
					//借/贷标识符
					voucherItem.setDebitcredit(settleSubjectPay.getDebtcredit4());
				}
				
				//客户记账码
				if ("H".equals(settleSubjectPay.getDebtcredit4()))
				{
					voucherItem.setCheckcode("50");
					//借/贷标识符
					voucherItem.setDebitcredit(settleSubjectPay.getDebtcredit4());
				}
				//特殊G/L标识
				voucherItem.setGlflag("");
				String subjectname = this.customerRefundItemJdbcDao.getSubjectNameById(settleSubjectPay.getSettlesubject4(),bukrs);
				//科目
				voucherItem.setSubject(settleSubjectPay.getSettlesubject4());
				//科目说明
				voucherItem.setSubjectdescribe(subjectname);
				//统驭项目
				voucherItem.setControlaccount(settleSubjectPay.getSettlesubject4());
				//统驭科目说明
				voucherItem.setCaremark(subjectname);
				//货币
				voucherItem.setCurrency(settleSubjectPay.getHomeCreditPayment().getFactcurrency());
				//货币金额
				voucherItem.setAmount(settleSubjectPay.getAmount4());
				//本位币金额
				voucherItem.setAmount2(settleSubjectPay.getStandardamount4());
				//部门
				voucherItem.setDepid(settleSubjectPay.getDepid4());
				//文本
				voucherItem.setText(settleSubjectPay.getHomeCreditPayment().getText());
				//反记账标识
				if (settleSubjectPay.getAntiaccount4() != null && 
					settleSubjectPay.getAntiaccount4().equals("Y")){
					voucherItem.setUncheckflag("X");
				}else{
					voucherItem.setUncheckflag("");
				}
				//销售订单
				voucherItem.setSalesorder(settleSubjectPay.getOrderno4());
				//销售订单行项目号
				voucherItem.setOrderrowno(settleSubjectPay.getRowno4());
				//利润中心
				voucherItem.setProfitcenter(settleSubjectPay.getProfitcenter());

				voucherItemList.add(voucherItem);
			}
		}
		return voucherItemList;
	}
	
	/**
	 * 纯资金往来
	 * @param fundFlowPay
	 * @return
	 */
	public Set<VoucherItem> getFundFlowVoucherItem(HomeCreditPayment homePayment){
		Set<VoucherItem> voucherItemList = new HashSet<VoucherItem>();
		FundFlow fundFlowPay = homePayment.getFundFlow();
		String bukrs = getCompanyIDByDeptID(homePayment.getDept_id());
		BigDecimal amount = new BigDecimal(0);
		BigDecimal standardAmount = new BigDecimal(0);
		if (null != fundFlowPay)
		{
			amount = fundFlowPay.getAmount1();
			standardAmount = fundFlowPay.getStandardamount1();
			if (amount.abs().compareTo(new BigDecimal(0)) == 1 ||
				standardAmount.abs().compareTo(new BigDecimal(0)) == 1)
			{
				VoucherItem voucherItem = new VoucherItem();
				
				//客户记账码
				if ("S".equals(fundFlowPay.getDebtcredit1()))
				{
					if(StringUtils.isNullBlank(fundFlowPay.getSpecialaccount1())){
						voucherItem.setCheckcode("01");
					}else{
						voucherItem.setCheckcode("09");
					}
					//借/贷标识符
					voucherItem.setDebitcredit(fundFlowPay.getDebtcredit1());
				}
				
				//客户记账码
				if ("H".equals(fundFlowPay.getDebtcredit1()))
				{
					if(StringUtils.isNullBlank(fundFlowPay.getSpecialaccount1())){
						voucherItem.setCheckcode("11");
					}else{
						voucherItem.setCheckcode("19");
					}
					//借/贷标识符
					voucherItem.setDebitcredit(fundFlowPay.getDebtcredit1());
				}
				//客户记账码
				//voucherItem.setCheckcode("29");
				//借/贷标识符
				//voucherItem.setDebitcredit(fundFlowPay.getDebtcredit1());
				//特殊G/L标识
				voucherItem.setGlflag(fundFlowPay.getSpecialaccount1());
				//科目
				voucherItem.setSubject(fundFlowPay.getCustomer1());
				//科目说明
				if(!StringUtils.isNullBlank(fundFlowPay.getDepid1())){
					String Subjectdescribe = this.customerRefundItemJdbcDao.getCustomerDescByCustomerId(
							fundFlowPay.getCustomer1()
							,bukrs);
					voucherItem.setSubjectdescribe(Subjectdescribe);
					String subject = this.customerRefundItemJdbcDao.getSubjectByCustomer(fundFlowPay.getCustomer1(),bukrs);
					subject = this.customerRefundItemJdbcDao.getSkont(voucherItem,subject);
					//统驭项目
					voucherItem.setControlaccount(subject);
					//统驭科目说明
					voucherItem.setCaremark(this.customerRefundItemJdbcDao.getSubjectNameById(subject,bukrs));
				}
				//货币
				voucherItem.setCurrency(fundFlowPay.getHomeCreditPayment().getFactcurrency());
				//货币金额
				voucherItem.setAmount(fundFlowPay.getAmount1());
				//本位币金额
				voucherItem.setAmount2(fundFlowPay.getStandardamount1());
				//部门
				voucherItem.setDepid(fundFlowPay.getDepid1());
				//文本
				voucherItem.setText(fundFlowPay.getHomeCreditPayment().getText());
				//反记账标识
				if (fundFlowPay.getAntiaccount1() != null && 
					fundFlowPay.getAntiaccount1().equals("Y")){
					voucherItem.setUncheckflag("X");
				}else{
					voucherItem.setUncheckflag("");
				}
				
				voucherItemList.add(voucherItem);
			}
			
			amount = fundFlowPay.getAmount2();
			standardAmount = fundFlowPay.getStandardamount2();
			if (amount.abs().compareTo(new BigDecimal(0)) == 1 ||
				standardAmount.abs().compareTo(new BigDecimal(0)) == 1)
			{
				VoucherItem voucherItem = new VoucherItem();
				
				//客户记账码
				if ("S".equals(fundFlowPay.getDebtcredit2()))
				{
					if(StringUtils.isNullBlank(fundFlowPay.getSpecialaccount2())){
						voucherItem.setCheckcode("01");
					}else{
						voucherItem.setCheckcode("09");
					}
					//借/贷标识符
					voucherItem.setDebitcredit(fundFlowPay.getDebtcredit2());
				}
				
				//客户记账码
				if ("H".equals(fundFlowPay.getDebtcredit2()))
				{
					if(StringUtils.isNullBlank(fundFlowPay.getSpecialaccount2())){
						voucherItem.setCheckcode("11");
					}else{
						voucherItem.setCheckcode("19");
					}
					//借/贷标识符
					voucherItem.setDebitcredit(fundFlowPay.getDebtcredit2());
				}
				//客户记账码
				//voucherItem.setCheckcode("29");
				//借/贷标识符
				//voucherItem.setDebitcredit(fundFlowPay.getDebtcredit2());
				//特殊G/L标识
				voucherItem.setGlflag(fundFlowPay.getSpecialaccount2());
				//科目
				voucherItem.setSubject(fundFlowPay.getCustomer2());
				//科目说明
				if(!StringUtils.isNullBlank(fundFlowPay.getDepid2())){
					String Subjectdescribe = this.customerRefundItemJdbcDao.getCustomerDescByCustomerId(
							fundFlowPay.getCustomer2()
							,bukrs);
					voucherItem.setSubjectdescribe(Subjectdescribe);
					String subject = this.customerRefundItemJdbcDao.getSubjectByCustomer(fundFlowPay.getCustomer2(),bukrs);
					subject = this.customerRefundItemJdbcDao.getSkont(voucherItem,subject);
					//统驭项目
					voucherItem.setControlaccount(subject);
					//统驭科目说明
					voucherItem.setCaremark(this.customerRefundItemJdbcDao.getSubjectNameById(subject,bukrs));
				}
				//货币
				voucherItem.setCurrency(fundFlowPay.getHomeCreditPayment().getFactcurrency());
				//货币金额
				voucherItem.setAmount(fundFlowPay.getAmount2());
				//本位币金额
				voucherItem.setAmount2(fundFlowPay.getStandardamount2());
				//部门
				voucherItem.setDepid(fundFlowPay.getDepid2());
				//文本
				voucherItem.setText(fundFlowPay.getHomeCreditPayment().getText());
				//反记账标识
				if (fundFlowPay.getAntiaccount1() != null && 
					fundFlowPay.getAntiaccount1().equals("Y")){
					voucherItem.setUncheckflag("X");
				}else{
					voucherItem.setUncheckflag("");
				}
				
				voucherItemList.add(voucherItem);
			}
			
			amount = fundFlowPay.getAmount3();
			standardAmount = fundFlowPay.getStandardamount3();
			if (amount.abs().compareTo(new BigDecimal(0)) == 1 ||
				standardAmount.abs().compareTo(new BigDecimal(0)) == 1)
			{
				VoucherItem voucherItem = new VoucherItem();
				
				//客户记账码
				if ("S".equals(fundFlowPay.getDebtcredit3()))
				{
					if(StringUtils.isNullBlank(fundFlowPay.getSpecialaccount3())){
						voucherItem.setCheckcode("01");
					}else{
						voucherItem.setCheckcode("09");
					}
					//借/贷标识符
					voucherItem.setDebitcredit(fundFlowPay.getDebtcredit3());
				}
				
				//客户记账码
				if ("H".equals(fundFlowPay.getDebtcredit3()))
				{
					if(StringUtils.isNullBlank(fundFlowPay.getSpecialaccount3())){
						voucherItem.setCheckcode("11");
					}else{
						voucherItem.setCheckcode("19");
					}
					//借/贷标识符
					voucherItem.setDebitcredit(fundFlowPay.getDebtcredit3());
				}
				//客户记账码
				//voucherItem.setCheckcode("29");
				//借/贷标识符
				//voucherItem.setDebitcredit(fundFlowPay.getDebtcredit3());
				//特殊G/L标识
				voucherItem.setGlflag(fundFlowPay.getSpecialaccount3());
				//科目
				voucherItem.setSubject(fundFlowPay.getCustomer3());
				//科目说明
				if(!StringUtils.isNullBlank(fundFlowPay.getDepid3())){
					String Subjectdescribe = this.customerRefundItemJdbcDao.getCustomerDescByCustomerId(
							fundFlowPay.getCustomer3()
							,bukrs);
					voucherItem.setSubjectdescribe(Subjectdescribe);
					String subject = this.customerRefundItemJdbcDao.getSubjectByCustomer(fundFlowPay.getCustomer3(),bukrs);
					subject = this.customerRefundItemJdbcDao.getSkont(voucherItem,subject);
					//统驭项目
					voucherItem.setControlaccount(subject);
					//统驭科目说明
					voucherItem.setCaremark(this.customerRefundItemJdbcDao.getSubjectNameById(subject,bukrs));
				}
				//货币
				voucherItem.setCurrency(fundFlowPay.getHomeCreditPayment().getFactcurrency());
				//货币金额
				voucherItem.setAmount(fundFlowPay.getAmount3());
				//本位币金额
				voucherItem.setAmount2(fundFlowPay.getStandardamount3());
				//部门
				voucherItem.setDepid(fundFlowPay.getDepid3());
				//文本
				voucherItem.setText(fundFlowPay.getHomeCreditPayment().getText());
				//反记账标识
				if (fundFlowPay.getAntiaccount1() != null && 
					fundFlowPay.getAntiaccount1().equals("Y")){
					voucherItem.setUncheckflag("X");
				}else{
					voucherItem.setUncheckflag("");
				}
				
				voucherItemList.add(voucherItem);
			}
		}
		
		return voucherItemList;
	}
	/**
	 * 得到应付票据的清帐凭证
	 * @param homePayment
	 * @param strClass
	 * @param payVoucher
	 * @param strIsSHow
	 * @return
	 */
	public Voucher getBillClearVoucher(HomeCreditPayment homePayment,
			String strClass,
			Voucher payVoucher,
			String strIsSHow){
		Voucher voucher = new Voucher();
		//凭证抬头
		voucher = getClearVoucher(homePayment,strClass);
		//凭证行项目
		Set<VoucherItem> voucherItemList = new HashSet<VoucherItem>();
		
		Set<VoucherItem> payVoucherItem = payVoucher.getVoucherItem();
		Iterator<VoucherItem> payVoucherItemit = payVoucherItem.iterator();
		while(payVoucherItemit.hasNext()){
			VoucherItem payItem = payVoucherItemit.next();
			
			if (payItem.getCheckcode().equals("29") &&
					payItem.getGlflag().equals("W")){
					
				VoucherItem voucherItem = getClearVoucherItem(payVoucher.getVoucherno(),
						payItem.getRownumber(),
						payItem.getVoucher().getFiyear(),payItem.getVoucher().getVoucherid());

				voucherItem.setVoucher(voucher);
				voucherItemList.add(voucherItem);
			}
		}
		
		List<ClearVoucherItemStruct> clearVoucherItemStructList = 
			this.vendorTitleJdbcDao.getBillClearItemInfo(homePayment.getPaymentid(),"39","W","1");
		
		for (int j = 0;j<clearVoucherItemStructList.size();j++){
			ClearVoucherItemStruct clearVoucherItemStruct = 
				(ClearVoucherItemStruct) clearVoucherItemStructList.get(j);
			
			VoucherItem voucherItem = getClearVoucherItem(clearVoucherItemStruct.getVoucherno(),
					clearVoucherItemStruct.getRowno(),
					clearVoucherItemStruct.getYear()," ");
			
			voucherItem.setVoucher(voucher);
			voucherItemList.add(voucherItem);
		}
		
		voucher.setVoucherItem(voucherItemList);
		
		return voucher;
	}
	/**
	 * 得到清帐凭证的抬头信息
	 * @param homeCreditPayment
	 * @param strClass
	 * @return
	 */
	public Voucher getClearVoucher(HomeCreditPayment homePayment,String strClass){
		Voucher voucher = new Voucher();
		
		UserContext userContext = UserContextHolder.getLocalUserContext()
		.getUserContext();
		
		//业务编号
		voucher.setBusinessid(homePayment.getPaymentid());
		//业务类型
		voucher.setBusinesstype("02");
		//凭证类型
		voucher.setVouchertype("KZ");
		//凭证抬头文本
		voucher.setVouchertext("清帐凭证");
		//过账日期
		voucher.setCheckdate(homePayment.getAccountdate().replace("-", ""));
		//公司代码
		String bukrs = getCompanyIDByDeptID(homePayment.getDept_id());
		voucher.setCompanycode(bukrs); //公司代码
		//货币
		Iterator<HomeCreditPayCbill> homePaymentCbillIterator =  homePayment.getHomeCreditPayCbill().iterator();
		if(homePaymentCbillIterator.hasNext()){
			HomeCreditPayCbill homePaymentCbill = homePaymentCbillIterator.next();
			voucher.setCurrency(homePaymentCbill.getCurrency());
		}else{
			voucher.setCurrency(homePayment.getFactcurrency());
		}
		//凭证日期
		voucher.setVoucherdate(homePayment.getVoucherdate().replace("-", ""));
		//会计年度
		voucher.setFiyear(homePayment.getAccountdate().substring(0, 4));
		//会计期间
		voucher.setFiperiod(homePayment.getAccountdate().substring(5, 7));
		//输入日期
		SimpleDateFormat  dateFormat=new SimpleDateFormat ("yyyyMMdd");
		voucher.setImportdate(dateFormat.format(new Date()));
		//输入者
		voucher.setImporter(userContext.getSysUser().getUserName());
		//预制者
		voucher.setPreparer(userContext.getSysUser().getUserName());
		//银行科目号
		Iterator<HomeCreditBankItem> bankItemit = homePayment.getHomeCreditBankItem().iterator();
		if(bankItemit.hasNext()){
			HomeCreditBankItem importPayBankItem = bankItemit.next();
			voucher.setKonto(importPayBankItem.getPaybanksubject());
		}
		//计息日
		voucher.setValut(dateFormat.format(new Date()));
		//供应商编号
		voucher.setAgkon(homePayment.getSupplier());
		//科目类型
		voucher.setAgkoa("K");
		//业务范围
		voucher.setGsber(this.sysDeptJdbcDao.getDeptCodeById(homePayment.getDept_id()));
		//付款清帐标识
		voucher.setFlag("P");
		//凭证分类
		voucher.setVoucherclass(strClass);
		//汇率
		voucher.setExchangerate(new BigDecimal("1"));
		//清帐凭证状态
		voucher.setBstat("A");
		
		return voucher;
	}
	/**
	 * 处理清帐凭证行项目
	 * @param strVoucherNo
	 * @param strRowNo
	 * @param strYear
	 * @return
	 */
	public VoucherItem getClearVoucherItem(String strVoucherNo,
			String strRowNo,
			String strYear,
			String strBusVoucherId){
		VoucherItem voucherItem = new VoucherItem();
		
		voucherItem.setVoucherno(strVoucherNo);
		voucherItem.setRownumber(strRowNo);
		voucherItem.setFiyear(strYear);
		voucherItem.setFlag("P");
		voucherItem.setAmount(new BigDecimal("0"));
		voucherItem.setAmount2(new BigDecimal("0"));
		voucherItem.setBusvoucherid(strBusVoucherId);
		
		return voucherItem;
	}
	
	//短期外汇借款
	public List<String> shortTimepaySaveVoucher(HomeCreditPayment homeCreditPayment){
		List<String> retList = new ArrayList<String>();
		
		//this.voucherJdbcDao.deleteVoucherByBusinessidAndClass(homeCreditPayment.getPaymentid(), "2");
		String bukrs = getCompanyIDByDeptID(homeCreditPayment.getDept_id());
		//凭证抬头
		Voucher voucher = new Voucher();
		//凭证行项目
		Set<VoucherItem> voucherItemList = new HashSet<VoucherItem>();
		voucher = this.getVoucher(homeCreditPayment, "2");
		voucher.setCurrency(homeCreditPayment.getCurrency2());
		
		int rowNo = 0;
		//******************押汇银行行项目凭证行项目**********************************//
		HomeCreditDocuBank importDocuBankItem = new HomeCreditDocuBank();
		String dabank_hkonrname = "";
		Iterator<HomeCreditDocuBank> docBankItemit = homeCreditPayment.getHomeCreditDocuBank().iterator();		
		while(docBankItemit.hasNext()){
			rowNo = rowNo + 1;
			importDocuBankItem = docBankItemit.next();
			
			VoucherItem BankVoucherItem = new VoucherItem();
			BankVoucherItem.setVoucher(voucher);
			//客户记账码
			BankVoucherItem.setCheckcode("50");
			//特殊G/L标识
			BankVoucherItem.setGlflag("");
			//科目
			BankVoucherItem.setSubject(importDocuBankItem.getDocuarybanksubj());
			String subjectdescribe = this.customerRefundItemJdbcDao.getSubjectNameById(importDocuBankItem.getDocuarybanksubj(),bukrs);
			//科目说明
			BankVoucherItem.setSubjectdescribe(subjectdescribe);
			//货币
			BankVoucherItem.setCurrency(homeCreditPayment.getCurrency2());
			//货币金额
			BankVoucherItem.setAmount(importDocuBankItem.getDocuarypayamount());
			//本位币金额
			BankVoucherItem.setAmount2(importDocuBankItem.getDocuarypayamoun2());
			//部门
			BankVoucherItem.setDepid(homeCreditPayment.getRedocarybc());
			//文本
			BankVoucherItem.setText(homeCreditPayment.getText());
			//现金流项目
			BankVoucherItem.setCashflowitem("");
			//反记账标识
			BankVoucherItem.setUncheckflag("");
			//统驭项目
			BankVoucherItem.setControlaccount(importDocuBankItem.getDocuarybanksubj());
			//统驭科目说明
			BankVoucherItem.setCaremark(subjectdescribe);
			//销售订单
			BankVoucherItem.setSalesorder("");
			//销售订单行项目号
			BankVoucherItem.setOrderrowno("");
			//利润中心
			BankVoucherItem.setProfitcenter("");
			//成本中心
			BankVoucherItem.setCostcenter("");
			//借/贷标识符
			BankVoucherItem.setDebitcredit("H");
			//行号
			BankVoucherItem.setRownumber(String.valueOf(rowNo));
			
			voucherItemList.add(BankVoucherItem);
		}
		//******************押汇银行行项目凭证行项目**********************************//
		//******************付款银行行项目凭证行项目*******************************//
		Iterator<HomeCreditDocuBank> bankItemit = homeCreditPayment.getHomeCreditDocuBank().iterator();		
		while(bankItemit.hasNext()){
			rowNo = rowNo + 1;
			importDocuBankItem = bankItemit.next();
			
			VoucherItem BankVoucherItem = new VoucherItem();
			BankVoucherItem.setVoucher(voucher);
			//客户记账码
			BankVoucherItem.setCheckcode("40");
			//特殊G/L标识
			BankVoucherItem.setGlflag("");
			String subject = this.bankInfoJdbcDao.getBankInfo(importDocuBankItem.getDocuarybankacco());
			String subjectdescribe = this.customerRefundItemJdbcDao.getSubjectNameById(subject,bukrs);
			//科目
			BankVoucherItem.setSubject(subject);
			//科目说明
			BankVoucherItem.setSubjectdescribe(subjectdescribe);
			//货币
			BankVoucherItem.setCurrency(homeCreditPayment.getCurrency2());
			//货币金额
			BankVoucherItem.setAmount(importDocuBankItem.getDocuarypayamount());
			//本位币金额
			BankVoucherItem.setAmount2(importDocuBankItem.getDocuarypayamoun2());
			//部门
			BankVoucherItem.setDepid(homeCreditPayment.getRedocarybc());
			//文本
			BankVoucherItem.setText(homeCreditPayment.getText());
			//现金流项目
			BankVoucherItem.setCashflowitem(importDocuBankItem.getCashflowitem()); // "302"
			//反记账标识
			BankVoucherItem.setUncheckflag("");
			//统驭项目
			BankVoucherItem.setControlaccount(subject);
			//统驭科目说明
			BankVoucherItem.setCaremark(subjectdescribe);
			//销售订单
			BankVoucherItem.setSalesorder("");
			//销售订单行项目号
			BankVoucherItem.setOrderrowno("");
			//利润中心
			BankVoucherItem.setProfitcenter("");
			//成本中心
			BankVoucherItem.setCostcenter("");
			//借/贷标识符
			BankVoucherItem.setDebitcredit("S");
			//行号
			BankVoucherItem.setRownumber(String.valueOf(rowNo));
			
			voucherItemList.add(BankVoucherItem);
		}
		//******************付款银行行项目凭证行项目*******************************//

		
		voucher.setVoucherItem(voucherItemList);
		this.voucherHibernateDao.save(voucher);
		retList.add(voucher.getVoucherid());
		
		//凭证抬头
		Voucher voucher2 = new Voucher();
		//凭证行项目
		Set<VoucherItem> voucherItemList2 = new HashSet<VoucherItem>();
		//this.voucherJdbcDao.deleteVoucherByBusinessidAndClass(homeCreditPayment.getPaymentid(), "1");
		voucher2 = this.getVoucher(homeCreditPayment, "1");
		
		rowNo = 0;
		
		//******************供应商行项目凭证行项目**********************************//
		Iterator<HomeCreditPayItem> PayItemItemit = homeCreditPayment.getHomeCreditPayItem() .iterator();		
		while(PayItemItemit.hasNext()){
			rowNo = rowNo + 1;
			HomeCreditPayItem homeCreditPaymentItem = PayItemItemit.next();
			
			VoucherItem voucherItem = new VoucherItem();
			voucherItem.setVoucher(voucher2);
			//客户记账码
			voucherItem.setCheckcode("21");
			//特殊G/L标识
			voucherItem.setGlflag("");
			//科目
			voucherItem.setSubject(homeCreditPayment.getSupplier());
			//科目说明
			String Subjectdescribe = this.customerRefundItemJdbcDao.getSupplierDescByCustomerId(homeCreditPayment.getSupplier(),bukrs);
			voucherItem.setSubjectdescribe(Subjectdescribe);
			//货币
			voucherItem.setCurrency(homeCreditPayment.getFactcurrency());
			//货币金额
			voucherItem.setAmount(homeCreditPaymentItem.getAssignamount());
			//本位币金额
			voucherItem.setAmount2(homeCreditPaymentItem.getAssignamount2());
			//部门
			voucherItem.setDepid(this.sysDeptJdbcDao.getDeptCodeById(homeCreditPayment.getDept_id()));
			//文本
			voucherItem.setText(homeCreditPayment.getText());
			//现金流项目
			voucherItem.setCashflowitem("");
			//反记账标识
			voucherItem.setUncheckflag("");
			String subject = this.customerRefundItemJdbcDao.getSubjectBySuppliers(homeCreditPayment.getSupplier(),bukrs);
			subject = this.customerRefundItemJdbcDao.getSkont(voucherItem,subject);
			//统驭项目
			voucherItem.setControlaccount(subject);
			//统驭科目说明
			voucherItem.setCaremark(this.customerRefundItemJdbcDao.getSubjectNameById(subject,bukrs));
			//销售订单
			voucherItem.setSalesorder("");
			//销售订单行项目号
			voucherItem.setOrderrowno("");
			//利润中心
			voucherItem.setProfitcenter("");
			//成本中心
			voucherItem.setCostcenter("");
			//借/贷标识符
			voucherItem.setDebitcredit("S");
			//行号
			voucherItem.setRownumber(String.valueOf(rowNo));
			//付款行项目信息
			voucherItem.setBusinessitemid(homeCreditPaymentItem.getPaymentitemid());
			
			voucherItemList2.add(voucherItem);
		}
		//******************供应商行项目凭证行项目**********************************//
		
		//******************付款银行行项目凭证行项目*******************************//
		Iterator<HomeCreditBankItem> bankItemit2 = homeCreditPayment.getHomeCreditBankItem().iterator();		
		while(bankItemit2.hasNext()){
			rowNo = rowNo + 1;
			HomeCreditBankItem importPayBankItem = bankItemit2.next();
			
			VoucherItem BankVoucherItem = new VoucherItem();
			BankVoucherItem.setVoucher(voucher2);
			//客户记账码
			BankVoucherItem.setCheckcode("50");
			//特殊G/L标识
			BankVoucherItem.setGlflag("");
			//科目
			BankVoucherItem.setSubject(importPayBankItem.getPaybanksubject());
			//科目说明
			BankVoucherItem.setSubjectdescribe(importPayBankItem.getPaybankname());
			//货币
			BankVoucherItem.setCurrency(homeCreditPayment.getFactcurrency());
			//货币金额
			BankVoucherItem.setAmount(importPayBankItem.getPayamount());
			//本位币金额
			BankVoucherItem.setAmount2(importPayBankItem.getPayamount2());
			//部门
			BankVoucherItem.setDepid(this.sysDeptJdbcDao.getDeptCodeById(homeCreditPayment.getDept_id()));
			//文本
			BankVoucherItem.setText(homeCreditPayment.getText());
			//现金流项目
			BankVoucherItem.setCashflowitem(importPayBankItem.getCashflowitem());
			//反记账标识
			BankVoucherItem.setUncheckflag("");
			//统驭项目
			BankVoucherItem.setControlaccount(importPayBankItem.getPaybanksubject());
			//统驭科目说明
			BankVoucherItem.setCaremark(importPayBankItem.getPaybankname());
			//销售订单
			BankVoucherItem.setSalesorder("");
			//销售订单行项目号
			BankVoucherItem.setOrderrowno("");
			//利润中心
			BankVoucherItem.setProfitcenter("");
			//成本中心
			BankVoucherItem.setCostcenter("");
			//借/贷标识符
			BankVoucherItem.setDebitcredit("H");
			//行号
			BankVoucherItem.setRownumber(String.valueOf(rowNo));
			
			voucherItemList2.add(BankVoucherItem);
		}
		//******************付款银行行项目凭证行项目*******************************//
		
		//******************结算科目行项目凭证行项目*******************************//
		//结算科目凭证行项目
		Set<VoucherItem> SettlesubjectVoucherItemList = new HashSet<VoucherItem>();
		
		SettlesubjectVoucherItemList = this.getSettSubjVoucherItem(homeCreditPayment.getSettleSubject(),bukrs);
		
		Iterator<VoucherItem> Settlesubjectit = SettlesubjectVoucherItemList.iterator();		
		while(Settlesubjectit.hasNext()){
			rowNo = rowNo + 1;
			VoucherItem setvoucherItem =Settlesubjectit.next();
			setvoucherItem.setRownumber(String.valueOf(rowNo));
			setvoucherItem.setVoucher(voucher2);
			
			voucherItemList2.add(setvoucherItem);
		}
		//******************结算科目行项目凭证行项目*******************************//
		
		//******************特殊总帐行项目凭证行项目*******************************//
		//结算科目凭证行项目
		Set<VoucherItem> FundFlowVoucherItemList = new HashSet<VoucherItem>();
		
		FundFlowVoucherItemList = this.getFundFlowVoucherItem(homeCreditPayment);
		
		Iterator<VoucherItem> FundFlowit = FundFlowVoucherItemList.iterator();		
		while(FundFlowit.hasNext()){
			rowNo = rowNo + 1;
			VoucherItem funvoucherItem =FundFlowit.next();
			funvoucherItem.setRownumber(String.valueOf(rowNo));
			funvoucherItem.setVoucher(voucher2);
			
			voucherItemList2.add(funvoucherItem);
		}
		//******************特殊总帐行项目凭证行项目*******************************//
		
		voucher2.setVoucherItem(voucherItemList2);
		this.voucherHibernateDao.save(voucher2);
		retList.add(voucher2.getVoucherid());
		
		return retList;
	}
	
	
	//还短期外汇借款 有中转
	public List<String> returnDiffShortTimeSaveVoucher(HomeCreditPayment homeCreditPayment){
		//this.voucherJdbcDao.deleteVoucherByBusinessidAndClass(homeCreditPayment.getPaymentid(), "3");
		List<String> retList = new ArrayList<String>();
		
		String bukrs = getCompanyIDByDeptID(homeCreditPayment.getDept_id());
		//凭证抬头
		Voucher voucher = new Voucher();
		//凭证行项目
		Set<VoucherItem> voucherItemList = new HashSet<VoucherItem>();
		voucher = this.getVoucher(homeCreditPayment, "3");
		voucher.setCurrency(homeCreditPayment.getCurrency2());
		
		String strSubject = "";
		int rowNo = 1;
		HomeCreditBankItem importPayBankItem = new HomeCreditBankItem();
		//******************付款银行行项目凭证行项目*******************************//
		Iterator<HomeCreditBankItem> bankItemit = homeCreditPayment.getHomeCreditBankItem().iterator();		
		while(bankItemit.hasNext()){
			rowNo = rowNo + 1;
			importPayBankItem = bankItemit.next();
			
			VoucherItem BankVoucherItem = new VoucherItem();
			BankVoucherItem.setVoucher(voucher);
			//客户记账码
			BankVoucherItem.setCheckcode("50");
			//特殊G/L标识
			BankVoucherItem.setGlflag("");
			List<VoucherItem>  voucheritemList2 = this.voucherItemJdbcDao.getVoucherItemList2(homeCreditPayment.getPaymentid(),"02","2");
			for (Iterator<VoucherItem> iter = voucheritemList2.iterator(); iter.hasNext();)
			{
				VoucherItem _voucherItem = (VoucherItem) iter.next();
				if(_voucherItem.getCheckcode().equals("50")){
					strSubject = _voucherItem.getSubject();
					//科目
					BankVoucherItem.setSubject(_voucherItem.getSubject());
					//科目说明
					BankVoucherItem.setSubjectdescribe(_voucherItem.getSubjectdescribe());
					//统驭项目
					BankVoucherItem.setControlaccount(_voucherItem.getControlaccount());
					//统驭科目说明
					BankVoucherItem.setCaremark(_voucherItem.getCaremark());
					break;
				}
			}
			//货币
			BankVoucherItem.setCurrency(homeCreditPayment.getCurrency2());
			//货币金额
			BankVoucherItem.setAmount(importPayBankItem.getPayamount());
			//本位币金额
			BankVoucherItem.setAmount2(importPayBankItem.getPayamount2());
			//部门
			BankVoucherItem.setDepid(this.sysDeptJdbcDao.getDeptCodeById(homeCreditPayment.getDept_id()));
			//文本
			BankVoucherItem.setText(homeCreditPayment.getText());
			//现金流项目
			BankVoucherItem.setCashflowitem(importPayBankItem.getCashflowitem()); // 351
			//反记账标识
			BankVoucherItem.setUncheckflag("");
			//销售订单
			BankVoucherItem.setSalesorder("");
			//销售订单行项目号
			BankVoucherItem.setOrderrowno("");
			//利润中心
			BankVoucherItem.setProfitcenter("");
			//成本中心
			BankVoucherItem.setCostcenter("");
			//借/贷标识符
			BankVoucherItem.setDebitcredit("H");
			//行号
			BankVoucherItem.setRownumber(String.valueOf(rowNo));
			
			voucherItemList.add(BankVoucherItem);
		}
		//******************付款银行行项目凭证行项目*******************************//
		
		//************************应付中转科目**********************************//
		VoucherItem voucherItem = new VoucherItem();
		voucherItem.setVoucher(voucher);
		//客户记账码
		voucherItem.setCheckcode("40");
		//特殊G/L标识
		voucherItem.setGlflag("");
		String subjectdescribe = this.customerRefundItemJdbcDao.getSubjectNameById("2202999999",bukrs);
		//科目
		voucherItem.setSubject("2202999999");
		//科目说明
		voucherItem.setSubjectdescribe(subjectdescribe);
		//货币
		voucherItem.setCurrency(homeCreditPayment.getFactcurrency());
		//货币金额
		voucherItem.setAmount(importPayBankItem.getPayamount());
		//本位币金额
		voucherItem.setAmount2(importPayBankItem.getPayamount2());
		//部门
		voucherItem.setDepid(this.sysDeptJdbcDao.getDeptCodeById(homeCreditPayment.getDept_id()));
		//文本
		voucherItem.setText(homeCreditPayment.getText());
		//现金流项目
		voucherItem.setCashflowitem("");
		//反记账标识
		voucherItem.setUncheckflag("");
		//统驭项目
		voucherItem.setControlaccount("2202999999");
		//统驭科目说明
		voucherItem.setCaremark(subjectdescribe);
		//销售订单
		voucherItem.setSalesorder("");
		//销售订单行项目号
		voucherItem.setOrderrowno("");
		//利润中心
		voucherItem.setProfitcenter("");
		//成本中心
		voucherItem.setCostcenter("");
		//借/贷标识符
		voucherItem.setDebitcredit("S");
		//行号
		voucherItem.setRownumber("1");
		
		voucherItemList.add(voucherItem);
		//************************应付中转科目**********************************//
		voucher.setVoucherItem(voucherItemList);
		this.voucherHibernateDao.save(voucher);
		retList.add(voucher.getVoucherid());
		
		voucher = this.getVoucher(homeCreditPayment, "3");
		voucherItemList.clear();
		rowNo = 0;
		//******************押汇银行行项目凭证行项目**********************************//
		HomeCreditDocuBank importDocuBankItem = new HomeCreditDocuBank();
		Iterator<HomeCreditDocuBank> docBankItemit = homeCreditPayment.getHomeCreditDocuBank().iterator();		
		while(docBankItemit.hasNext()){
			rowNo = rowNo + 1;
			importDocuBankItem = docBankItemit.next();
			
			VoucherItem BankVoucherItem = new VoucherItem();
			BankVoucherItem.setVoucher(voucher);
			//客户记账码
			BankVoucherItem.setCheckcode("40");
			//特殊G/L标识
			BankVoucherItem.setGlflag("");
			strSubject = importDocuBankItem.getDocuarybanksubj();
			//科目
			BankVoucherItem.setSubject(importDocuBankItem.getDocuarybanksubj());
			String subjectdesc = this.customerRefundItemJdbcDao.getSubjectNameById(importDocuBankItem.getDocuarybanksubj(),bukrs);
			//科目说明
			BankVoucherItem.setSubjectdescribe(subjectdesc);
			//货币
			BankVoucherItem.setCurrency(homeCreditPayment.getCurrency2());
			//货币金额
			BankVoucherItem.setAmount(importDocuBankItem.getDocuarypayamount());
			//本位币金额
			BankVoucherItem.setAmount2(importDocuBankItem.getDocuarypayamoun2());
			//部门
			BankVoucherItem.setDepid(this.sysDeptJdbcDao.getDeptCodeById(homeCreditPayment.getDept_id()));
			//文本
			BankVoucherItem.setText(homeCreditPayment.getText());
			//现金流项目
			BankVoucherItem.setCashflowitem("");
			//反记账标识
			BankVoucherItem.setUncheckflag("");
			//统驭项目
			BankVoucherItem.setControlaccount(importDocuBankItem.getDocuarybanksubj());
			//统驭科目说明
			BankVoucherItem.setCaremark(subjectdesc);
			//销售订单
			BankVoucherItem.setSalesorder("");
			//销售订单行项目号
			BankVoucherItem.setOrderrowno("");
			//利润中心
			BankVoucherItem.setProfitcenter("");
			//成本中心
			BankVoucherItem.setCostcenter("");
			//借/贷标识符
			BankVoucherItem.setDebitcredit("S");
			//行号
			BankVoucherItem.setRownumber(String.valueOf(rowNo));
			
			voucherItemList.add(BankVoucherItem);
		}
		//******************押汇银行行项目凭证行项目**********************************//
		
		//************************应付中转科目**********************************//
		VoucherItem _voucherItem = new VoucherItem();
		_voucherItem.setVoucher(voucher);
		//客户记账码
		_voucherItem.setCheckcode("50");
		//特殊G/L标识
		_voucherItem.setGlflag("");
		String subjectdesc = this.customerRefundItemJdbcDao.getSubjectNameById("1122999999",bukrs);
		//科目
		_voucherItem.setSubject("2202999999");
		//科目说明
		_voucherItem.setSubjectdescribe(subjectdesc);
		//货币
		_voucherItem.setCurrency(homeCreditPayment.getFactcurrency());
		//货币金额
		_voucherItem.setAmount(importPayBankItem.getPayamount());
		//本位币金额
		_voucherItem.setAmount2(importPayBankItem.getPayamount2());
		//部门
		_voucherItem.setDepid(this.sysDeptJdbcDao.getDeptCodeById(homeCreditPayment.getDept_id()));
		//文本
		_voucherItem.setText(homeCreditPayment.getText());
		//现金流项目
		_voucherItem.setCashflowitem("");
		//反记账标识
		_voucherItem.setUncheckflag("");
		//统驭项目
		_voucherItem.setControlaccount("2202999999");
		//统驭科目说明
		_voucherItem.setCaremark(subjectdesc);
		//销售订单
		_voucherItem.setSalesorder("");
		//销售订单行项目号
		_voucherItem.setOrderrowno("");
		//利润中心
		_voucherItem.setProfitcenter("");
		//成本中心
		_voucherItem.setCostcenter("");
		//借/贷标识符
		_voucherItem.setDebitcredit("S");
		//行号
		_voucherItem.setRownumber(String.valueOf(rowNo));
		
		voucherItemList.add(_voucherItem);
		//************************应付中转科目**********************************//
		//******************结算科目行项目凭证行项目*******************************//
		Set<VoucherItem> SettlesubjectVoucherItemList = new HashSet<VoucherItem>();

		SettlesubjectVoucherItemList = this.getSettSubjVoucherItem(homeCreditPayment.getSettleSubject(),bukrs);
		
		Iterator<VoucherItem> Settlesubjectit = SettlesubjectVoucherItemList.iterator();		
		while(Settlesubjectit.hasNext()){
			rowNo = rowNo + 1;
			VoucherItem setvoucherItem =Settlesubjectit.next();
			setvoucherItem.setRownumber(String.valueOf(rowNo));
			setvoucherItem.setVoucher(voucher);
			
			voucherItemList.add(setvoucherItem);
		}
		//******************结算科目行项目凭证行项目*******************************//
		
		voucher.setVoucherItem(voucherItemList);
		this.voucherHibernateDao.save(voucher);
		retList.add(voucher.getVoucherid());
		
		//************************处理短期外汇借款清帐凭证***************************
		//this.voucherJdbcDao.deleteVoucherByBusinessidAndClass(homeCreditPayment.getPaymentid(), "7");
		List<Voucher> voucherList = getReturnLoanClearVendor(homeCreditPayment,voucher,"7",strSubject,"2");

		for (int k=0;k<voucherList.size();k++){
			Voucher returnClearvoucher = voucherList.get(k);
			
			this.voucherHibernateDao.save(returnClearvoucher);
			retList.add(returnClearvoucher.getVoucherid());
		}
		//************************处理短期外汇借款清帐凭证***************************
		return retList;
	}
	
	//还短期外汇借款
	public List<String> returnShortTimeSaveVoucher(HomeCreditPayment homeCreditPayment){
		List<String> retList = new ArrayList<String>();
		
        //得到票的本位币之和
        BigDecimal billValue = new BigDecimal("0");
        //得到分配项的本位币之和
        BigDecimal itemValue = new BigDecimal("0");
        //分配项之和和票之和的差
        BigDecimal subtractVlaue = new BigDecimal("0");
        
		//this.voucherJdbcDao.deleteVoucherByBusinessidAndClass(homeCreditPayment.getPaymentid(), "3");
		String bukrs = getCompanyIDByDeptID(homeCreditPayment.getDept_id());
		//凭证抬头
		Voucher voucher = new Voucher();
		//凭证行项目
		Set<VoucherItem> voucherItemList = new HashSet<VoucherItem>();
		voucher = this.getVoucher(homeCreditPayment, "3");
		voucher.setCurrency(homeCreditPayment.getCurrency2());
		voucher.setExchangerate(homeCreditPayment.getRedocaryrate());
		String strSubject = "";
		String strSubjectdescribe = "";
		int rowNo = 1;
		//******************押汇银行行项目凭证行项目**********************************//
		VoucherItem voucherItem = new VoucherItem();
		voucherItem.setVoucher(voucher);
		//客户记账码
		voucherItem.setCheckcode("40");
		//特殊G/L标识
		voucherItem.setGlflag("");
		List<VoucherItem>  voucheritemList2 = this.voucherItemJdbcDao.getVoucherItemList2(homeCreditPayment.getPaymentid(),"02","2");
		//押汇金额
		BigDecimal docaryamount = new BigDecimal("0");
		for (Iterator<VoucherItem> iter = voucheritemList2.iterator(); iter.hasNext();)
		{
			VoucherItem _voucherItem = (VoucherItem) iter.next();
			if(_voucherItem.getCheckcode().equals("50")){
				//货币金额
//				voucherItem.setAmount(_voucherItem.getAmount());
				docaryamount = docaryamount.add(_voucherItem.getAmount());
//				金额取本次还押汇的金额
				voucherItem.setAmount(homeCreditPayment.getRedocaryamount());
				//本位币金额
				voucherItem.setAmount2(homeCreditPayment.getRedocaryamount().multiply(homeCreditPayment.getDocumentaryrate()).setScale(2, BigDecimal.ROUND_HALF_UP));
                billValue = billValue.add(voucherItem.getAmount2());
				strSubject = _voucherItem.getSubject();
				strSubjectdescribe = _voucherItem.getSubjectdescribe();
				//科目
				voucherItem.setSubject(_voucherItem.getSubject());
				//科目说明
				voucherItem.setSubjectdescribe(_voucherItem.getSubjectdescribe());
				//统驭项目
				voucherItem.setControlaccount(_voucherItem.getControlaccount());
				//统驭科目说明
				voucherItem.setCaremark(_voucherItem.getCaremark());
				break;
			}
		}
		
		//货币
		voucherItem.setCurrency(homeCreditPayment.getCurrency2());
		//部门
		voucherItem.setDepid(homeCreditPayment.getRedocarybc());
		//文本
		voucherItem.setText(homeCreditPayment.getText());
		//现金流项目
		voucherItem.setCashflowitem("");
		//反记账标识
		voucherItem.setUncheckflag("");
		//销售订单
		voucherItem.setSalesorder("");
		//销售订单行项目号
		voucherItem.setOrderrowno("");
		//利润中心
		voucherItem.setProfitcenter("");
		//成本中心
		voucherItem.setCostcenter("");
		//借/贷标识符
		voucherItem.setDebitcredit("S");
		//行号
		voucherItem.setRownumber(String.valueOf(rowNo));
		
		voucherItemList.add(voucherItem);
		
//		//******************押汇银行行项目凭证行项目**********************************//
//		//******************付款银行行项目凭证行项目*******************************//
//		Iterator<ImportPayBankItem> bankItemit = homeCreditPayment.getImportPayBankItem().iterator();		
//		VoucherItem bankVoucherItem = null;
//		while(bankItemit.hasNext()){
//			rowNo = rowNo + 1;
//			ImportPayBankItem importPayBankItem = bankItemit.next();
//			
//			bankVoucherItem = new VoucherItem();
//			bankVoucherItem.setVoucher(voucher);
//			//客户记账码
//			bankVoucherItem.setCheckcode("50");
//			//特殊G/L标识
//			bankVoucherItem.setGlflag("");
//			
//			String subjectdescribe = this.customerRefundItemJdbcDao.getSubjectNameById(importPayBankItem.getPaybanksubject(),bukrs);
//			//科目
//			bankVoucherItem.setSubject(importPayBankItem.getPaybanksubject());
//			//科目说明
//			bankVoucherItem.setSubjectdescribe(subjectdescribe);
//			//货币
//			bankVoucherItem.setCurrency(homeCreditPayment.getCurrency2());
//			//货币金额
//			bankVoucherItem.setAmount(importPayBankItem.getPayamount());
//			//本位币金额
//			bankVoucherItem.setAmount2(importPayBankItem.getPayamount2());
//			itemValue = itemValue.add(bankVoucherItem.getAmount2());
//			//部门
//			bankVoucherItem.setDepid(homeCreditPayment.getRedocarybc());
//			//文本
//			bankVoucherItem.setText(homeCreditPayment.getText());
//			//现金流项目
//			bankVoucherItem.setCashflowitem(importPayBankItem.getCashflowitem()); // 351
//			//反记账标识
//			bankVoucherItem.setUncheckflag("");
//			//统驭项目
//			bankVoucherItem.setControlaccount(importPayBankItem.getPaybanksubject());
//			//统驭科目说明
//			bankVoucherItem.setCaremark(subjectdescribe);
//			//销售订单
//			bankVoucherItem.setSalesorder("");
//			//销售订单行项目号
//			bankVoucherItem.setOrderrowno("");
//			//利润中心
//			bankVoucherItem.setProfitcenter("");
//			//成本中心
//			bankVoucherItem.setCostcenter("");
//			//借/贷标识符
//			bankVoucherItem.setDebitcredit("H");
//			//行号
//			bankVoucherItem.setRownumber(String.valueOf(rowNo));
//			
//			voucherItemList.add(bankVoucherItem);
//		}
		//******************押汇银行行项目凭证行项目**********************************//
		//******************付款银行修改成还押汇银行行项目凭证行项目zzh*******************************//
		Iterator<HomeCreditRebank> bankItemit = homeCreditPayment.getHomeCreditRebank().iterator();		
		VoucherItem bankVoucherItem = null;
		while(bankItemit.hasNext()){
			rowNo = rowNo + 1;
			HomeCreditRebank billPayReBankItem = bankItemit.next();
			if("未做账".equals(billPayReBankItem.getBusinesstype())){
				bankVoucherItem = new VoucherItem();
				bankVoucherItem.setVoucher(voucher);
				//客户记账码
				bankVoucherItem.setCheckcode("50");
				//特殊G/L标识
				bankVoucherItem.setGlflag("");
				String banksubject =this.bankInfoJdbcDao.getBankInfo(billPayReBankItem.getBankacc());
				String subjectdescribe = this.customerRefundItemJdbcDao.getSubjectNameById(banksubject,bukrs);
				//科目
				bankVoucherItem.setSubject(banksubject);
				//科目说明
				bankVoucherItem.setSubjectdescribe(subjectdescribe);
				//货币
				bankVoucherItem.setCurrency(homeCreditPayment.getCurrency2());
				//货币金额
				bankVoucherItem.setAmount(billPayReBankItem.getRealmoney());
				//本位币金额
				bankVoucherItem.setAmount2(billPayReBankItem.getRealmoney2());
				itemValue = itemValue.add(bankVoucherItem.getAmount2());
				//部门
				bankVoucherItem.setDepid(homeCreditPayment.getRedocarybc());
				//文本
				bankVoucherItem.setText(homeCreditPayment.getText());
				//现金流项目
				bankVoucherItem.setCashflowitem(billPayReBankItem.getCashflowitem()); // 351
				//反记账标识
				bankVoucherItem.setUncheckflag("");
				//统驭项目
				bankVoucherItem.setControlaccount(banksubject);
				//统驭科目说明
				bankVoucherItem.setCaremark(subjectdescribe);
				//销售订单
				bankVoucherItem.setSalesorder("");
				//销售订单行项目号
				bankVoucherItem.setOrderrowno("");
				//利润中心
				bankVoucherItem.setProfitcenter("");
				//成本中心
				bankVoucherItem.setCostcenter("");
				//借/贷标识符
				bankVoucherItem.setDebitcredit("H");
				//行号
				bankVoucherItem.setRownumber(String.valueOf(rowNo));
				
				voucherItemList.add(bankVoucherItem);
			}
		}
		//******************付款银行行项目凭证行项目*******************************//
		//******************结算科目行项目凭证行项目*******************************//
		Set<VoucherItem> SettlesubjectVoucherItemList = new HashSet<VoucherItem>();

		SettlesubjectVoucherItemList = this.getSettSubjVoucherItem(homeCreditPayment.getSettleSubject(),bukrs);
		
		Iterator<VoucherItem> Settlesubjectit = SettlesubjectVoucherItemList.iterator();		
		while(Settlesubjectit.hasNext()){
			rowNo = rowNo + 1;
			VoucherItem setvoucherItem =Settlesubjectit.next();
			setvoucherItem.setRownumber(String.valueOf(rowNo));
			setvoucherItem.setVoucher(voucher);
			
			voucherItemList.add(setvoucherItem);
			if ( "H".equals(setvoucherItem.getDebitcredit()) ) {
                itemValue = itemValue.add(setvoucherItem.getAmount2());
			} else {
			    billValue = billValue.add(setvoucherItem.getAmount2());
			}
		}
		//******************结算科目行项目凭证行项目*******************************//
		
	      //******************特殊总帐行项目凭证行项目*******************************//
        //结算科目凭证行项目
        Set<VoucherItem> fundFlowVoucherItemList = new HashSet<VoucherItem>();
        
        fundFlowVoucherItemList = this.getFundFlowVoucherItem(homeCreditPayment);
        
        Iterator<VoucherItem> FundFlowit = fundFlowVoucherItemList.iterator();      
        while(FundFlowit.hasNext()){
            rowNo = rowNo + 1;
            VoucherItem funvoucherItem =FundFlowit.next();
            funvoucherItem.setRownumber(String.valueOf(rowNo));
            funvoucherItem.setVoucher(voucher);
            if ( "H".equals(funvoucherItem.getDebitcredit()) ) {
                itemValue = itemValue.add(funvoucherItem.getAmount2());
            } else {
                billValue = billValue.add(funvoucherItem.getAmount2());
            }
            voucherItemList.add(funvoucherItem);
        }
		
		// ******************* 汇损出在普通凭证里面 beg **************************
        subtractVlaue = itemValue.subtract(billValue);
        
        if (new BigDecimal("0").compareTo(subtractVlaue) != 0){
            VoucherItem profOrLossVoucherItem = new VoucherItem();
            
//            if(homeCreditPayment.getRedocarybc().equals("2199")){
//                if (subtractVlaue.signum() == 1){
//                    //记帐码
//                    profOrLossVoucherItem.setCheckcode("40");
//                    //借/贷标识符
//                    profOrLossVoucherItem.setDebitcredit("S");
//                    String subjectname = this.customerRefundItemJdbcDao.getSubjectNameById("6603050101",bukrs);
//                    //科目
//                    profOrLossVoucherItem.setSubject("6603050101");
//                    //科目说明
//                    profOrLossVoucherItem.setSubjectdescribe(subjectname);
//                    //统驭项目
//                    profOrLossVoucherItem.setControlaccount("6603050101");
//                    //统驭科目说明
//                    profOrLossVoucherItem.setCaremark(subjectname);
//                }else{
//                    //记帐码
//                    profOrLossVoucherItem.setCheckcode("50");
//                    //借/贷标识符
//                    profOrLossVoucherItem.setDebitcredit("H");
//                    String subjectname = this.customerRefundItemJdbcDao.getSubjectNameById("6603050201",bukrs);
//                    //科目
//                    profOrLossVoucherItem.setSubject("6603050201");
//                    //科目说明
//                    profOrLossVoucherItem.setSubjectdescribe(subjectname);
//                    //统驭项目
//                    profOrLossVoucherItem.setControlaccount("6603050201");
//                    //统驭科目说明
//                    profOrLossVoucherItem.setCaremark(subjectname);
//                }
//            }else{
                if (subtractVlaue.signum() == 1){
                    //记帐码
                    profOrLossVoucherItem.setCheckcode("40");
                    //借/贷标识符
                    profOrLossVoucherItem.setDebitcredit("S");
                    String subjectname = this.customerRefundItemJdbcDao.getSubjectNameById("6603050501",bukrs);
                    //科目
                    profOrLossVoucherItem.setSubject("6603050501");
                    //科目说明
                    profOrLossVoucherItem.setSubjectdescribe(subjectname);
                    //统驭项目
                    profOrLossVoucherItem.setControlaccount("6603050501");
                    //统驭科目说明
                    profOrLossVoucherItem.setCaremark(subjectname);
                }else{
                    //记帐码
                    profOrLossVoucherItem.setCheckcode("50");
                    //借/贷标识符
                    profOrLossVoucherItem.setDebitcredit("H");
                    String subjectname = this.customerRefundItemJdbcDao.getSubjectNameById("6603050502",bukrs);
                    //科目
                    profOrLossVoucherItem.setSubject("6603050502");
                    //科目说明
                    profOrLossVoucherItem.setSubjectdescribe(subjectname);
                    //统驭项目
                    profOrLossVoucherItem.setControlaccount("6603050502");
                    //统驭科目说明
                    profOrLossVoucherItem.setCaremark(subjectname);
                }
//            }
            
            //货币
            profOrLossVoucherItem.setCurrency(homeCreditPayment.getFactcurrency());
            //货币金额
            profOrLossVoucherItem.setAmount(new BigDecimal("0"));
            //本位币金额
            profOrLossVoucherItem.setAmount2(subtractVlaue.abs());
            //部门
            profOrLossVoucherItem.setDepid(homeCreditPayment.getRedocarybc());
            //文本
            profOrLossVoucherItem.setText(homeCreditPayment.getText());
            profOrLossVoucherItem.setRownumber( String.valueOf(++rowNo) ) ;
            profOrLossVoucherItem.setVoucher(voucher);
            voucherItemList.add(profOrLossVoucherItem);
        }
        // ******************* 汇损出在普通凭证里面 end **************************
		
		
		voucher.setVoucherItem(voucherItemList);
		this.voucherHibernateDao.save(voucher);
		retList.add(voucher.getVoucherid());
		Voucher vou=voucherService.judgeVoucherNeedDel_3(voucher);
		//************************处理短期外汇借款清帐凭证***************************
		//this.voucherJdbcDao.deleteVoucherByBusinessidAndClass(homeCreditPayment.getPaymentid(), "7");
		//如果累计还押汇金额+本次还押汇金额=押汇金额才出短期外汇借款清帐凭证
		if(homeCreditPayment.getRedocaryamount2().add(homeCreditPayment.getRedocaryamount()).compareTo(docaryamount) ==0){
			List<Voucher> voucherList = getReturnLoanClearVendor(homeCreditPayment,vou,"7",strSubject,"2");
	
			for (int k=0;k<voucherList.size();k++){
				Voucher returnClearvoucher = voucherList.get(k);
				
				this.voucherHibernateDao.save(returnClearvoucher);
				retList.add(returnClearvoucher.getVoucherid());
			}
		}
		//************************处理短期外汇借款清帐凭证***************************
		return retList;
	}
	
	/**
	 * 得到短期借款清帐凭证
	 * @param homeCreditPayment
	 * @param strClass
	 * @return
	 */
	public List<Voucher> getReturnLoanClearVendor(HomeCreditPayment homeCreditPayment,
			Voucher payVoucher,
			String strClass,
			String strSubject,
			String strBussinessType){
		
		List<Voucher> voucherList = new ArrayList<Voucher>();
		String bukrs = getCompanyIDByDeptID(homeCreditPayment.getDept_id());
		//得到借款金额本位币
		List<ClearVoucherItemStruct> clearVoucherItemStructList = 
			this.vendorTitleJdbcDao.getLoanItemInfo(homeCreditPayment.getPaymentid(),"50",strSubject,strBussinessType);
		
		for (int j = 0;j<clearVoucherItemStructList.size();j++){
			ClearVoucherItemStruct clearVoucherItemStruct = 
				(ClearVoucherItemStruct) clearVoucherItemStructList.get(j);
			
			Voucher voucher = new Voucher();
			//凭证抬头
			voucher = getClearVoucher(homeCreditPayment,strClass);
			voucher.setAgkoa("S");
			voucher.setAgkon(clearVoucherItemStruct.getSubject());
			//凭证行项目
			Set<VoucherItem> voucherItemList = new HashSet<VoucherItem>();
			
			//得到票的本位币之和
			BigDecimal billValue = new BigDecimal("0");
			//得到分配项的本位币之和
			BigDecimal itemValue = new BigDecimal("0");
			//分配项之和和票之和的差
			BigDecimal subtractVlaue = new BigDecimal("0");
			
			
			
			VoucherItem voucherItem = getClearVoucherItem(clearVoucherItemStruct.getVoucherno(),
					clearVoucherItemStruct.getRowno(),
					clearVoucherItemStruct.getYear()," ");
			
			voucherItem.setVoucher(voucher);
			voucherItemList.add(voucherItem);
			
			itemValue = clearVoucherItemStruct.getBwbje();
//			凭证号为空才填加，防止再次模拟凭证，如再次模拟凭证就从部分还押汇那边取
			if(StringUtils.isNullBlank(payVoucher.getVoucherno())){
			Set<VoucherItem> payVoucherItem = payVoucher.getVoucherItem();
			Iterator<VoucherItem> payVoucherItemit = payVoucherItem.iterator();
			while(payVoucherItemit.hasNext()){
				VoucherItem payItem = payVoucherItemit.next();
				
				if (payItem.getSubject().equals(clearVoucherItemStruct.getSubject()) && 
						payItem.getCheckcode().equals("40")){
						
					VoucherItem voucherItem2 = getClearVoucherItem("",
							payItem.getRownumber(),
							payItem.getVoucher().getFiyear(),
							payItem.getVoucher().getVoucherid());

					voucherItem2.setVoucher(voucher);
					voucherItemList.add(voucherItem2);
					
					billValue = billValue.add(payItem.getAmount2());
				}
			}
			
			}
			
			//得到以前部分还押汇借款金额本位币
			List<ClearVoucherItemStruct> clearVoucherItemStructList2 = 
				this.vendorTitleJdbcDao.getLoanItemInfo(homeCreditPayment.getPaymentid(),"40",strSubject,"3");
			for (ClearVoucherItemStruct clearVoucherItemStruct2 : clearVoucherItemStructList2){				
				VoucherItem voucherItem2 = getClearVoucherItem(clearVoucherItemStruct2.getVoucherno(),clearVoucherItemStruct2.getRowno(),
						clearVoucherItemStruct2.getYear()," ");
				voucherItem2.setVoucher(voucher);
				voucherItemList.add(voucherItem2);				
				billValue = billValue.add(clearVoucherItemStruct2.getBwbje());
			}
				
//			subtractVlaue = itemValue.subtract(billValue);
			subtractVlaue = billValue.subtract(itemValue);
			if (new BigDecimal("0").compareTo(subtractVlaue) != 0){
				VoucherItem supplierVoucherItem = new VoucherItem();
				//科目
				supplierVoucherItem.setSubject(clearVoucherItemStruct.getSubject());
				//科目说明
				supplierVoucherItem.setSubjectdescribe(clearVoucherItemStruct.getSubjectdescribe());
				supplierVoucherItem.setControlaccount(clearVoucherItemStruct.getSubject());
				supplierVoucherItem.setCaremark(clearVoucherItemStruct.getSubjectdescribe());
				if (subtractVlaue.signum() == 1){
					//记帐码
					supplierVoucherItem.setCheckcode("50");
					//借/贷标识符
					supplierVoucherItem.setDebitcredit("H");
				}else{
					//记帐码
					supplierVoucherItem.setCheckcode("40");
					//借/贷标识符
					supplierVoucherItem.setDebitcredit("S");
				}
				//货币
				supplierVoucherItem.setCurrency(homeCreditPayment.getFactcurrency());
				//货币金额
				supplierVoucherItem.setAmount(new BigDecimal("0"));
				//本位币金额
				supplierVoucherItem.setAmount2(subtractVlaue.abs());
				//部门
				supplierVoucherItem.setDepid(homeCreditPayment.getRedocarybc());
				//文本
				supplierVoucherItem.setText(homeCreditPayment.getText());
				
				supplierVoucherItem.setVoucher(voucher);
				voucherItemList.add(supplierVoucherItem);
				
				VoucherItem profOrLossVoucherItem = new VoucherItem();
				
				if(homeCreditPayment.getRedocarybc().equals("2199")){
					if (subtractVlaue.signum() == 1){
						//记帐码
						profOrLossVoucherItem.setCheckcode("40");
						//借/贷标识符
						profOrLossVoucherItem.setDebitcredit("S");
						String subjectname = this.customerRefundItemJdbcDao.getSubjectNameById("6603050101",bukrs);
						//科目
						profOrLossVoucherItem.setSubject("6603050101");
						//科目说明
						profOrLossVoucherItem.setSubjectdescribe(subjectname);
						//统驭项目
						profOrLossVoucherItem.setControlaccount("6603050101");
						//统驭科目说明
						profOrLossVoucherItem.setCaremark(subjectname);
					}else{
						//记帐码
						profOrLossVoucherItem.setCheckcode("50");
						//借/贷标识符
						profOrLossVoucherItem.setDebitcredit("H");
						String subjectname = this.customerRefundItemJdbcDao.getSubjectNameById("6603050201",bukrs);
						//科目
						profOrLossVoucherItem.setSubject("6603050201");
						//科目说明
						profOrLossVoucherItem.setSubjectdescribe(subjectname);
						//统驭项目
						profOrLossVoucherItem.setControlaccount("6603050201");
						//统驭科目说明
						profOrLossVoucherItem.setCaremark(subjectname);
					}
				}else{
					if (subtractVlaue.signum() == 1){
						//记帐码
						profOrLossVoucherItem.setCheckcode("40");
						//借/贷标识符
						profOrLossVoucherItem.setDebitcredit("S");
						String subjectname = this.customerRefundItemJdbcDao.getSubjectNameById("6603050401",bukrs);
						//科目
						profOrLossVoucherItem.setSubject("6603050401");
						//科目说明
						profOrLossVoucherItem.setSubjectdescribe(subjectname);
						//统驭项目
						profOrLossVoucherItem.setControlaccount("6603050401");
						//统驭科目说明
						profOrLossVoucherItem.setCaremark(subjectname);
					}else{
						//记帐码
						profOrLossVoucherItem.setCheckcode("50");
						//借/贷标识符
						profOrLossVoucherItem.setDebitcredit("H");
						String subjectname = this.customerRefundItemJdbcDao.getSubjectNameById("6603050402",bukrs);
						//科目
						profOrLossVoucherItem.setSubject("6603050402");
						//科目说明
						profOrLossVoucherItem.setSubjectdescribe(subjectname);
						//统驭项目
						profOrLossVoucherItem.setControlaccount("6603050402");
						//统驭科目说明
						profOrLossVoucherItem.setCaremark(subjectname);
					}
				}
				
				//货币
				profOrLossVoucherItem.setCurrency(homeCreditPayment.getFactcurrency());
				//货币金额
				profOrLossVoucherItem.setAmount(new BigDecimal("0"));
				//本位币金额
				profOrLossVoucherItem.setAmount2(subtractVlaue.abs());
				//部门
				profOrLossVoucherItem.setDepid(homeCreditPayment.getRedocarybc());
				//文本
				profOrLossVoucherItem.setText(homeCreditPayment.getText());
				
				profOrLossVoucherItem.setVoucher(voucher);
				voucherItemList.add(profOrLossVoucherItem);
			}
			
			
			voucher.setVoucherItem(voucherItemList);
			
			voucherList.add(voucher);
		}
		
		return voucherList;
	}
	
	public void updateHomeCreditPayment(HomeCreditPayment homeCreditPayment){
		this.homeCreditPaymentHibernateDao.update(homeCreditPayment);
	}
	
	
}