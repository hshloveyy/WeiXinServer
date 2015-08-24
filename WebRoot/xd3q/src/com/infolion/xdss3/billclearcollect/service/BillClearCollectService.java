/*
 * @(#)BillClearCollectService.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年06月13日 09点26分47秒
 *  描　述：创建
 */
package com.infolion.xdss3.billclearcollect.service;

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
import com.infolion.xdss3.Constants;
import com.infolion.platform.basicApp.authManage.UserContextHolder;
import com.infolion.platform.dpframework.util.DateUtils;
import com.infolion.platform.dpframework.util.StringUtils;
import com.infolion.platform.dpframework.core.BusinessException;

import com.infolion.platform.dpframework.core.service.BaseService;
import com.infolion.platform.dpframework.uicomponent.number.service.NumberService;
import com.infolion.platform.dpframework.uicomponent.searchHelp.domain.SearchHelpDetail;
import com.infolion.platform.workflow.engine.WorkflowService;
import com.infolion.platform.dpframework.uicomponent.lock.LockService;
import com.infolion.platform.dpframework.uicomponent.lock.LockException;
import com.infolion.platform.basicApp.authManage.domain.OperationType;
import com.infolion.platform.basicApp.authManage.domain.UserContext;
import com.infolion.platform.bo.domain.BusinessObject;
import com.infolion.platform.bo.service.BusinessObjectService;
import com.infolion.platform.console.org.dao.SysDeptJdbcDao;
import com.infolion.platform.dpframework.core.service.AdvanceService;
import com.infolion.xdss3.BusinessState;
import com.infolion.xdss3.billClear.domain.BillClearItemPay;
import com.infolion.xdss3.billClear.domain.BillClearPayment;
import com.infolion.xdss3.billClear.domain.BillInPayment;
import com.infolion.xdss3.billclearcollect.domain.BillClearCollect;
import com.infolion.xdss3.billclearcollect.service.BillClearCollectService;
import com.infolion.xdss3.billclearcollect.dao.BillClearCollectHibernateDao;
import com.infolion.xdss3.billclearcollectGen.service.BillClearCollectServiceGen;
import com.infolion.xdss3.billclearitem.domain.BillClearItem;
import com.infolion.xdss3.billclearitem.service.BillClearItemService;
import com.infolion.xdss3.billincollect.domain.BillInCollect;
import com.infolion.xdss3.billincollect.service.BillInCollectService;
import com.infolion.xdss3.collect.dao.CollectJdbcDao;
import com.infolion.xdss3.collect.domain.Collect;
import com.infolion.xdss3.collectitem.dao.CollectItemJdbcDao;
import com.infolion.xdss3.collectitem.domain.CollectItem;
import com.infolion.xdss3.collectitem.service.CollectItemService;
import com.infolion.xdss3.customerDrawback.dao.CustomerRefundItemJdbcDao;
import com.infolion.xdss3.fundflowBcc.domain.FundFlowBcc;
import com.infolion.xdss3.masterData.dao.CustomerJdbcDao;
import com.infolion.xdss3.masterData.dao.CustomerTitleJdbcDao;
import com.infolion.xdss3.masterData.domain.CustomerTitle;
import com.infolion.xdss3.payment.domain.ClearVoucherItemStruct;
import com.infolion.xdss3.payment.domain.ImportPaymentItem;
import com.infolion.xdss3.payment.service.PaymentItemService;
import com.infolion.xdss3.reassign.ReassignConstant;
import com.infolion.xdss3.reassign.dao.ReassignJdbcDao;
import com.infolion.xdss3.settlesubjectbcc.domain.SettleSubjectBcc;
import com.infolion.xdss3.singleClear.domain.CustomSingleClear;
import com.infolion.xdss3.voucher.dao.VoucherHibernateDao;
import com.infolion.xdss3.voucher.domain.Voucher;
import com.infolion.xdss3.voucher.service.VoucherService;
import com.infolion.xdss3.voucheritem.dao.VoucherItemJdbcDao;
import com.infolion.xdss3.voucheritem.domain.VoucherItem;

/**
 * <pre>
 * 票清预收款(BillClearCollect)服务类
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
public class BillClearCollectService extends BillClearCollectServiceGen
{
	private Log log = LogFactory.getFactory().getLog(this.getClass());
	
	
	@Autowired
	protected VoucherService voucherService;
	
	public void setVoucherService(VoucherService voucherService) {
		this.voucherService = voucherService;
	}
	
	@Autowired
	protected CollectItemService collectItemService;
	
	public void setCollectItemService(CollectItemService collectItemService) {
		this.collectItemService = collectItemService;
	}
	
	@Autowired
	private CustomerTitleJdbcDao customerTitleJdbcDao;

	public void setCustomerTitleJdbcDao(CustomerTitleJdbcDao customerTitleJdbcDao)
	{
		this.customerTitleJdbcDao = customerTitleJdbcDao;
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
	private CollectJdbcDao collectJdbcDao;
	
	public void setCollectJdbcDao(CollectJdbcDao collectJdbcDao) {
		this.collectJdbcDao = collectJdbcDao;
	}
	
	@Autowired
	protected VoucherHibernateDao voucherHibernateDao;

	public void setVoucherHibernateDao(VoucherHibernateDao voucherHibernateDao)
	{
		this.voucherHibernateDao = voucherHibernateDao;
	}
	
	@Autowired
	private CustomerJdbcDao customerJdbcDao;

	public void setCustomerJdbcDao(CustomerJdbcDao customerJdbcDao) {
		this.customerJdbcDao = customerJdbcDao;
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
	
	/**
	 * 保存凭证 
	 * @param billClearCollect
	 * @return
	 */
	public String saveVoucher(BillClearCollect billClearCollect){
		
		Voucher voucher=getVoucher(billClearCollect);
		
		String businessId = genClearVoucher(billClearCollect,voucher);
		if(null !=voucher){
//			this.voucherService._save(voucher);
			voucherService._saveOrUpdate(voucher, null, BusinessObjectService.getBusinessObject("Voucher"));
		}
		
		//没有清帐凭证和调整金凭证
		if(null == businessId && null ==voucher)return "";
		//没有清帐凭证有调整金凭证
		if(null != voucher && null == businessId)businessId = billClearCollect.getBillclearid();
		//if(null == voucher)return "!=null";
		return businessId;
	}

public Voucher getVoucher2(BillClearCollect billClearCollect){
	Voucher voucher = new Voucher();
		voucher.setBusinessid(billClearCollect.getBillclearid());
		voucher.setBusinesstype("03");
		voucher.setVouchertype("DR");
		voucher.setVouchertext(billClearCollect.getText());
		String accountdate = billClearCollect.getAccountdate().replace("-", "");
		voucher.setCheckdate(accountdate);
		voucher.setVoucherclass("1");
		voucher.setVoucherdate(billClearCollect.getVoucherdate().replace("-", ""));
		if (!StringUtils.isNullBlank(accountdate)){
			voucher.setFiyear(accountdate.substring(0,4));
			voucher.setFiperiod(accountdate.substring(4,6));
		}
		voucher.setExchangerate(BigDecimal.valueOf(1));
		
		voucher.setImportdate(DateUtils.getCurrDate(DateUtils.DB_STORE_DATE).substring(0, 8));
		UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
		voucher.setImporter(userContext.getUser().getUserName());
		voucher.setPreparer(userContext.getUser().getUserName());
		//把部门ID转成业务范围
		String deptid = this.sysDeptJdbcDao.getDeptCodeById(billClearCollect.getDeptid());
		String bukrs =  this.voucherService.getCompanyIDByDeptID(billClearCollect.getDeptid());
//		String subject = this.customerRefundItemJdbcDao.getSubjectByCustomer(billClearCollect.getCustomer(),bukrs);
//		String subjectname = this.customerRefundItemJdbcDao.getSubjectNameById(subject,bukrs);
		String currency ="";
		for (BillClearItem billClearItem : billClearCollect.getBillclearitem())
		{
			if(!"".equals(billClearItem.getCurrency().trim())){
				currency = billClearItem.getCurrency();
				break;
			}
		}
		voucher.setCurrency(currency);
		voucher.setGsber(deptid);
		voucher.setCompanycode(bukrs);
		
		return voucher;
}
	/**
	 * 得到调整金凭证
	 * @param billClearCollect
	 * @return
	 */
	public Voucher getVoucher(BillClearCollect billClearCollect){
//		this.voucherService.deleteVoucher(billClearCollect.getBillclearid());
		
		//纯资金往来
		FundFlowBcc fundFlowBcc = billClearCollect.getFundFlowBcc();
		//结算科目
		SettleSubjectBcc settleSubjectBcc = billClearCollect.getSettleSubjectBcc();
		Voucher voucher = null;
		if(null != fundFlowBcc || null !=settleSubjectBcc){
			voucher =getVoucher2(billClearCollect);
			//把部门ID转成业务范围
			String deptid = this.sysDeptJdbcDao.getDeptCodeById(billClearCollect.getDeptid());
			String bukrs =  this.voucherService.getCompanyIDByDeptID(billClearCollect.getDeptid());
			String subject = this.customerRefundItemJdbcDao.getSubjectByCustomer(billClearCollect.getCustomer(),bukrs);
			String subjectname = this.customerRefundItemJdbcDao.getSubjectNameById(subject,bukrs);
			String currency = voucher.getCurrency();
			List<VoucherItem> voucherItems = new ArrayList<VoucherItem>();
			
			int i = 1;
			BigDecimal adjustAmount  = this.billClearItemService.getSumAdjustAmount(billClearCollect.getBillclearid());
			
			
			if(adjustAmount.compareTo(BigDecimal.valueOf(0))==1){
				VoucherItem voucherItem = new VoucherItem();
				
				voucherItem.setRownumber(Integer.toString(i));
				i++;
				voucherItem.setVoucher(voucher);
				voucherItem.setCheckcode("11");
				voucherItem.setSubject(billClearCollect.getCustomer());
				voucherItem.setAmount(adjustAmount.abs());
				voucherItem.setDepid(deptid);
				voucherItem.setCurrency(currency);
				voucherItem.setText(billClearCollect.getText());
				voucherItem.setDebitcredit("H");
				voucherItem.setControlaccount(subject);
				voucherItem.setCaremark(subjectname);
				voucherItems.add(voucherItem);
			}else if(adjustAmount.compareTo(BigDecimal.valueOf(0))==-1){
				VoucherItem voucherItem = new VoucherItem();
				voucherItem.setRownumber(Integer.toString(i));
				i++;
				voucherItem.setVoucher(voucher);
				voucherItem.setCheckcode("01");
				voucherItem.setSubject(billClearCollect.getCustomer());
				voucherItem.setAmount(adjustAmount.abs());
				voucherItem.setDepid(deptid);
				voucherItem.setText(billClearCollect.getText());
				voucherItem.setDebitcredit("S");
				voucherItem.setCurrency(currency);
				voucherItem.setControlaccount(subject);
				voucherItem.setCaremark(subjectname);
				voucherItems.add(voucherItem);
			}
			
			
			//结算科目
			if(null != settleSubjectBcc){
				//1
				if(null!=settleSubjectBcc.getAmount1()){
					if(settleSubjectBcc.getAmount1().compareTo(BigDecimal.valueOf(0))==1){
						VoucherItem voucherItem = new VoucherItem();
						voucherItem.setRownumber(Integer.toString(i));
						i++;
						voucherItem.setVoucher(voucher);
						voucherItem.setCheckcode("50");
						voucherItem.setSubject(settleSubjectBcc.getSettlesubject1());
						voucherItem.setAmount(settleSubjectBcc.getAmount1());
						voucherItem.setAmount2(settleSubjectBcc.getStandardamount1());
						voucherItem.setDepid(settleSubjectBcc.getDepid1());
						voucherItem.setSalesorder(settleSubjectBcc.getOrderno1());
						voucherItem.setOrderrowno(settleSubjectBcc.getRowno1());
						voucherItem.setCostcenter(settleSubjectBcc.getCostcenter1());
//						voucherItem.setUncheckflag(settleSubjectBcc.getAntiaccount1());
						
						if("Y".equals(settleSubjectBcc.getAntiaccount1())){
							voucherItem.setUncheckflag("X");
		                }else{
		                	voucherItem.setUncheckflag("");
		                }
						voucherItem.setDebitcredit(settleSubjectBcc.getDebtcredit1());
						voucherItem.setControlaccount(subject);
						voucherItem.setCaremark(subjectname);
						voucherItem.setText(billClearCollect.getText());
						voucherItem.setCurrency(currency);
						voucherItems.add(voucherItem);
					}else if(settleSubjectBcc.getAmount1().compareTo(BigDecimal.valueOf(0))==-1){
						VoucherItem voucherItem = new VoucherItem();
						voucherItem.setRownumber(Integer.toString(i));
						i++;
						voucherItem.setVoucher(voucher);
						voucherItem.setCheckcode("40");
						voucherItem.setSubject(settleSubjectBcc.getSettlesubject1());
						voucherItem.setAmount(settleSubjectBcc.getAmount1());
						voucherItem.setAmount2(settleSubjectBcc.getStandardamount1());
						voucherItem.setDepid(settleSubjectBcc.getDepid1());
						voucherItem.setSalesorder(settleSubjectBcc.getOrderno1());
						voucherItem.setOrderrowno(settleSubjectBcc.getRowno1());
						voucherItem.setCostcenter(settleSubjectBcc.getCostcenter1());
//						voucherItem.setUncheckflag(settleSubjectBcc.getAntiaccount1());
						if("Y".equals(settleSubjectBcc.getAntiaccount1())){
							voucherItem.setUncheckflag("X");
		                }else{
		                	voucherItem.setUncheckflag("");
		                }
						voucherItem.setDebitcredit(settleSubjectBcc.getDebtcredit1());
						voucherItem.setControlaccount(subject);
						voucherItem.setCaremark(subjectname);
						voucherItem.setText(billClearCollect.getText());
						voucherItem.setCurrency(currency);
						voucherItems.add(voucherItem);
					}
				}
				//2
				if(null!=settleSubjectBcc.getAmount2()){
					if(settleSubjectBcc.getAmount2().compareTo(BigDecimal.valueOf(0))==1){
						VoucherItem voucherItem = new VoucherItem();
						voucherItem.setRownumber(Integer.toString(i));
						i++;
						voucherItem.setVoucher(voucher);
						voucherItem.setCheckcode("50");
						voucherItem.setSubject(settleSubjectBcc.getSettlesubject2());
						voucherItem.setAmount(settleSubjectBcc.getAmount2());
						voucherItem.setAmount2(settleSubjectBcc.getStandardamount2());
						voucherItem.setDepid(settleSubjectBcc.getDepid2());
						voucherItem.setSalesorder(settleSubjectBcc.getOrderno2());
						voucherItem.setOrderrowno(settleSubjectBcc.getRowno2());
						voucherItem.setCostcenter(settleSubjectBcc.getCostcenter2());
//						voucherItem.setUncheckflag(settleSubjectBcc.getAntiaccount2());
						if("Y".equals(settleSubjectBcc.getAntiaccount2())){
							voucherItem.setUncheckflag("X");
		                }else{
		                	voucherItem.setUncheckflag("");
		                }
						voucherItem.setDebitcredit(settleSubjectBcc.getDebtcredit2());
						voucherItem.setControlaccount(subject);
						voucherItem.setCaremark(subjectname);
						voucherItem.setText(billClearCollect.getText());
						voucherItem.setCurrency(currency);
						voucherItems.add(voucherItem);
					}else if(settleSubjectBcc.getAmount2().compareTo(BigDecimal.valueOf(0))==-1){
						VoucherItem voucherItem = new VoucherItem();
						voucherItem.setRownumber(Integer.toString(i));
						i++;
						voucherItem.setVoucher(voucher);
						voucherItem.setCheckcode("40");
						voucherItem.setSubject(settleSubjectBcc.getSettlesubject2());
						voucherItem.setAmount(settleSubjectBcc.getAmount2());
						voucherItem.setAmount2(settleSubjectBcc.getStandardamount2());
						voucherItem.setDepid(settleSubjectBcc.getDepid2());
						voucherItem.setSalesorder(settleSubjectBcc.getOrderno2());
						voucherItem.setOrderrowno(settleSubjectBcc.getRowno2());
						voucherItem.setCostcenter(settleSubjectBcc.getCostcenter2());
//						voucherItem.setUncheckflag(settleSubjectBcc.getAntiaccount2());
						if("Y".equals(settleSubjectBcc.getAntiaccount2())){
							voucherItem.setUncheckflag("X");
		                }else{
		                	voucherItem.setUncheckflag("");
		                }
						voucherItem.setDebitcredit(settleSubjectBcc.getDebtcredit2());
						voucherItem.setControlaccount(subject);
						voucherItem.setCaremark(subjectname);
						voucherItem.setText(billClearCollect.getText());
						voucherItem.setCurrency(currency);
						voucherItems.add(voucherItem);
					}
				}
				//3
				if(null!=settleSubjectBcc.getAmount3()){
					if(settleSubjectBcc.getAmount3().compareTo(BigDecimal.valueOf(0))==1){
						VoucherItem voucherItem = new VoucherItem();
						voucherItem.setRownumber(Integer.toString(i));
						i++;
						voucherItem.setVoucher(voucher);
						voucherItem.setCheckcode("50");
						voucherItem.setSubject(settleSubjectBcc.getSettlesubject3());
						voucherItem.setAmount(settleSubjectBcc.getAmount3());
						voucherItem.setAmount2(settleSubjectBcc.getStandardamount3());
						voucherItem.setDepid(settleSubjectBcc.getDepid3());
						voucherItem.setSalesorder(settleSubjectBcc.getOrderno3());
						voucherItem.setOrderrowno(settleSubjectBcc.getRowno3());
						voucherItem.setCostcenter(settleSubjectBcc.getCostcenter3());
//						voucherItem.setUncheckflag(settleSubjectBcc.getAntiaccount3());
						if("Y".equals(settleSubjectBcc.getAntiaccount3())){
							voucherItem.setUncheckflag("X");
		                }else{
		                	voucherItem.setUncheckflag("");
		                }
						voucherItem.setDebitcredit(settleSubjectBcc.getDebtcredit3());
						voucherItem.setControlaccount(subject);
						voucherItem.setCaremark(subjectname);
						voucherItem.setText(billClearCollect.getText());
						voucherItem.setCurrency(currency);
						voucherItems.add(voucherItem);
					}else if(settleSubjectBcc.getAmount3().compareTo(BigDecimal.valueOf(0))==-1){
						VoucherItem voucherItem = new VoucherItem();
						voucherItem.setRownumber(Integer.toString(i));
						i++;
						voucherItem.setVoucher(voucher);
						voucherItem.setCheckcode("40");
						voucherItem.setSubject(settleSubjectBcc.getSettlesubject3());
						voucherItem.setAmount(settleSubjectBcc.getAmount3());
						voucherItem.setAmount2(settleSubjectBcc.getStandardamount3());
						voucherItem.setDepid(settleSubjectBcc.getDepid3());
						voucherItem.setSalesorder(settleSubjectBcc.getOrderno3());
						voucherItem.setOrderrowno(settleSubjectBcc.getRowno3());
						voucherItem.setCostcenter(settleSubjectBcc.getCostcenter3());
//						voucherItem.setUncheckflag(settleSubjectBcc.getAntiaccount3());
						if("Y".equals(settleSubjectBcc.getAntiaccount3())){
							voucherItem.setUncheckflag("X");
		                }else{
		                	voucherItem.setUncheckflag("");
		                }
						voucherItem.setDebitcredit(settleSubjectBcc.getDebtcredit3());
						voucherItem.setControlaccount(subject);
						voucherItem.setCaremark(subjectname);
						voucherItem.setText(billClearCollect.getText());
						voucherItem.setCurrency(currency);
						voucherItems.add(voucherItem);
					}
				}
				//4
				if(null!=settleSubjectBcc.getAmount4()){
					if(settleSubjectBcc.getAmount4().compareTo(BigDecimal.valueOf(0))==1){
						VoucherItem voucherItem = new VoucherItem();
						voucherItem.setRownumber(Integer.toString(i));
						i++;
						voucherItem.setVoucher(voucher);
						voucherItem.setCheckcode("50");
						voucherItem.setSubject(settleSubjectBcc.getSettlesubject4());
						voucherItem.setAmount(settleSubjectBcc.getAmount4());
						voucherItem.setAmount2(settleSubjectBcc.getStandardamount4());
						voucherItem.setDepid(settleSubjectBcc.getDepid4());
						voucherItem.setSalesorder(settleSubjectBcc.getOrderno4());
						voucherItem.setOrderrowno(settleSubjectBcc.getRowno4());
						voucherItem.setProfitcenter(settleSubjectBcc.getProfitcenter());
//						voucherItem.setUncheckflag(settleSubjectBcc.getAntiaccount4());
						if("Y".equals(settleSubjectBcc.getAntiaccount4())){
							voucherItem.setUncheckflag("X");
		                }else{
		                	voucherItem.setUncheckflag("");
		                }
						voucherItem.setDebitcredit(settleSubjectBcc.getDebtcredit4());
						voucherItem.setControlaccount(subject);
						voucherItem.setCaremark(subjectname);
						voucherItem.setText(billClearCollect.getText());
						voucherItem.setCurrency(currency);
						voucherItems.add(voucherItem);
					}else if(settleSubjectBcc.getAmount4().compareTo(BigDecimal.valueOf(0))==-1){
						VoucherItem voucherItem = new VoucherItem();
						voucherItem.setRownumber(Integer.toString(i));
						i++;
						voucherItem.setVoucher(voucher);
						voucherItem.setCheckcode("40");
						voucherItem.setSubject(settleSubjectBcc.getSettlesubject4());
						voucherItem.setAmount(settleSubjectBcc.getAmount4());
						voucherItem.setAmount2(settleSubjectBcc.getStandardamount4());
						voucherItem.setDepid(settleSubjectBcc.getDepid4());
						voucherItem.setSalesorder(settleSubjectBcc.getOrderno4());
						voucherItem.setOrderrowno(settleSubjectBcc.getRowno4());
						voucherItem.setProfitcenter(settleSubjectBcc.getProfitcenter());
//						voucherItem.setUncheckflag(settleSubjectBcc.getAntiaccount4());
						if("Y".equals(settleSubjectBcc.getAntiaccount4())){
							voucherItem.setUncheckflag("X");
		                }else{
		                	voucherItem.setUncheckflag("");
		                }
						voucherItem.setDebitcredit(settleSubjectBcc.getDebtcredit4());
						voucherItem.setControlaccount(subject);
						voucherItem.setCaremark(subjectname);
						voucherItem.setText(billClearCollect.getText());
						voucherItem.setCurrency(currency);
						voucherItems.add(voucherItem);
					}
				}
			}
			//纯资金往来
			
			if(null != fundFlowBcc){
				if(null!=fundFlowBcc.getAmount1()){
					if(fundFlowBcc.getAmount1().compareTo(BigDecimal.valueOf(0))==1){
						VoucherItem voucherItem = new VoucherItem();
						voucherItem.setRownumber(Integer.toString(i));
						i++;
						voucherItem.setVoucher(voucher);
						if(StringUtils.isNullBlank(fundFlowBcc.getSpecialaccount1())){
							voucherItem.setCheckcode("11");
						}else{
							voucherItem.setCheckcode("19");
						}
						voucherItem.setSubject(fundFlowBcc.getCustomer1());
						voucherItem.setGlflag(fundFlowBcc.getSpecialaccount1());
						voucherItem.setAmount(fundFlowBcc.getAmount1());
						voucherItem.setAmount2(fundFlowBcc.getStandardamount1());
						voucherItem.setDepid(fundFlowBcc.getDepid1());
//						voucherItem.setUncheckflag(fundFlowBcc.getAntiaccount1());
						if("Y".equals(fundFlowBcc.getAntiaccount1())){
							voucherItem.setUncheckflag("X");
		                }else{
		                	voucherItem.setUncheckflag("");
		                }
						voucherItem.setDebitcredit(fundFlowBcc.getDebtcredit1());
						
						voucherItem.setControlaccount(subject);
						voucherItem.setCaremark(subjectname);
						if(!StringUtils.isNullBlank(voucherItem.getGlflag())){
							subject = this.customerRefundItemJdbcDao.getSkont(voucherItem,subject);
							String subjectname2 = this.customerRefundItemJdbcDao.getSubjectNameById(subject,bukrs);
							voucherItem.setControlaccount(subject);
							voucherItem.setCaremark(subjectname2);
						}
						voucherItem.setText(billClearCollect.getText());
						voucherItem.setCurrency(currency);
						voucherItems.add(voucherItem);
					}else if(fundFlowBcc.getAmount1().compareTo(BigDecimal.valueOf(0))==-1){
						VoucherItem voucherItem = new VoucherItem();
						voucherItem.setRownumber(Integer.toString(i));
						i++;
						voucherItem.setVoucher(voucher);
						if(StringUtils.isNullBlank(fundFlowBcc.getSpecialaccount1())){
							voucherItem.setCheckcode("01");
						}else{
							voucherItem.setCheckcode("09");
						}
						voucherItem.setSubject(fundFlowBcc.getCustomer1());
						voucherItem.setGlflag(fundFlowBcc.getSpecialaccount1());
						voucherItem.setAmount(fundFlowBcc.getAmount1());
						voucherItem.setAmount2(fundFlowBcc.getStandardamount1());
						voucherItem.setDepid(fundFlowBcc.getDepid1());
//						voucherItem.setUncheckflag(fundFlowBcc.getAntiaccount1());
						if("Y".equals(fundFlowBcc.getAntiaccount1())){
							voucherItem.setUncheckflag("X");
		                }else{
		                	voucherItem.setUncheckflag("");
		                }
						voucherItem.setDebitcredit(fundFlowBcc.getDebtcredit1());
						voucherItem.setControlaccount(subject);
						voucherItem.setCaremark(subjectname);
						if(!StringUtils.isNullBlank(voucherItem.getGlflag())){
							subject = this.customerRefundItemJdbcDao.getSkont(voucherItem,subject);
							String subjectname2 = this.customerRefundItemJdbcDao.getSubjectNameById(subject,bukrs);
							voucherItem.setControlaccount(subject);
							voucherItem.setCaremark(subjectname2);
						}
						voucherItem.setText(billClearCollect.getText());
						voucherItem.setCurrency(currency);
						voucherItems.add(voucherItem);
					}
				}
				
				if(null!=fundFlowBcc.getAmount1()){
					if(fundFlowBcc.getAmount2().compareTo(BigDecimal.valueOf(0))==1){
						VoucherItem voucherItem = new VoucherItem();
						voucherItem.setRownumber(Integer.toString(i));
						i++;
						voucherItem.setVoucher(voucher);
						if(StringUtils.isNullBlank(fundFlowBcc.getSpecialaccount2())){
							voucherItem.setCheckcode("11");
						}else{
							voucherItem.setCheckcode("19");
						}
						voucherItem.setSubject(fundFlowBcc.getCustomer2());
						voucherItem.setGlflag(fundFlowBcc.getSpecialaccount2());
						voucherItem.setAmount(fundFlowBcc.getAmount2());
						voucherItem.setAmount2(fundFlowBcc.getStandardamount2());
						voucherItem.setDepid(fundFlowBcc.getDepid2());
//						voucherItem.setUncheckflag(fundFlowBcc.getAntiaccount2());
						if("Y".equals(fundFlowBcc.getAntiaccount2())){
							voucherItem.setUncheckflag("X");
		                }else{
		                	voucherItem.setUncheckflag("");
		                }
						voucherItem.setDebitcredit(fundFlowBcc.getDebtcredit2());
						voucherItem.setControlaccount(subject);
						voucherItem.setCaremark(subjectname);
						if(!StringUtils.isNullBlank(voucherItem.getGlflag())){
							subject = this.customerRefundItemJdbcDao.getSkont(voucherItem,subject);
							String subjectname2 = this.customerRefundItemJdbcDao.getSubjectNameById(subject,bukrs);
							voucherItem.setControlaccount(subject);
							voucherItem.setCaremark(subjectname2);
						}
						voucherItem.setText(billClearCollect.getText());
						voucherItem.setCurrency(currency);
						voucherItems.add(voucherItem);
					}else if(fundFlowBcc.getAmount2().compareTo(BigDecimal.valueOf(0))==-1){
						VoucherItem voucherItem = new VoucherItem();
						voucherItem.setRownumber(Integer.toString(i));
						i++;
						voucherItem.setVoucher(voucher);
						if(StringUtils.isNullBlank(fundFlowBcc.getSpecialaccount2())){
							voucherItem.setCheckcode("01");
						}else{
							voucherItem.setCheckcode("09");
						}
						voucherItem.setSubject(fundFlowBcc.getCustomer2());
						voucherItem.setGlflag(fundFlowBcc.getSpecialaccount2());
						voucherItem.setAmount(fundFlowBcc.getAmount2());
						voucherItem.setAmount2(fundFlowBcc.getStandardamount2());
						voucherItem.setDepid(fundFlowBcc.getDepid2());
//						voucherItem.setUncheckflag(fundFlowBcc.getAntiaccount2());
						if("Y".equals(fundFlowBcc.getAntiaccount2())){
							voucherItem.setUncheckflag("X");
		                }else{
		                	voucherItem.setUncheckflag("");
		                }
						voucherItem.setDebitcredit(fundFlowBcc.getDebtcredit2());
						voucherItem.setControlaccount(subject);
						voucherItem.setCaremark(subjectname);
						if(!StringUtils.isNullBlank(voucherItem.getGlflag())){
							subject = this.customerRefundItemJdbcDao.getSkont(voucherItem,subject);
							String subjectname2 = this.customerRefundItemJdbcDao.getSubjectNameById(subject,bukrs);
							voucherItem.setControlaccount(subject);
							voucherItem.setCaremark(subjectname2);
						}
						voucherItem.setText(billClearCollect.getText());
						voucherItem.setCurrency(currency);
						voucherItems.add(voucherItem);
					}
				}
				
				if(null!=fundFlowBcc.getAmount3()){
					if(fundFlowBcc.getAmount3().compareTo(BigDecimal.valueOf(0))==1){
						VoucherItem voucherItem = new VoucherItem();
						voucherItem.setRownumber(Integer.toString(i));
						i++;
						voucherItem.setVoucher(voucher);
						if(StringUtils.isNullBlank(fundFlowBcc.getSpecialaccount3())){
							voucherItem.setCheckcode("11");
						}else{
							voucherItem.setCheckcode("19");
						}
						voucherItem.setSubject(fundFlowBcc.getCustomer3());
						voucherItem.setGlflag(fundFlowBcc.getSpecialaccount3());
						voucherItem.setAmount(fundFlowBcc.getAmount3());
						voucherItem.setAmount2(fundFlowBcc.getStandardamount3());
						voucherItem.setDepid(fundFlowBcc.getDepid3());
//						voucherItem.setUncheckflag(fundFlowBcc.getAntiaccount3());
						if("Y".equals(fundFlowBcc.getAntiaccount3())){
							voucherItem.setUncheckflag("X");
		                }else{
		                	voucherItem.setUncheckflag("");
		                }
						voucherItem.setDebitcredit(fundFlowBcc.getDebtcredit3());
						voucherItem.setControlaccount(subject);
						voucherItem.setCaremark(subjectname);
						if(!StringUtils.isNullBlank(voucherItem.getGlflag())){
							subject = this.customerRefundItemJdbcDao.getSkont(voucherItem,subject);
							String subjectname2 = this.customerRefundItemJdbcDao.getSubjectNameById(subject,bukrs);
							voucherItem.setControlaccount(subject);
							voucherItem.setCaremark(subjectname2);
						}
						voucherItem.setText(billClearCollect.getText());
						voucherItem.setCurrency(currency);
						voucherItems.add(voucherItem);
					}else if(fundFlowBcc.getAmount3().compareTo(BigDecimal.valueOf(0))==-1){
						VoucherItem voucherItem = new VoucherItem();
						voucherItem.setRownumber(Integer.toString(i));
						i++;
						voucherItem.setVoucher(voucher);
						if(StringUtils.isNullBlank(fundFlowBcc.getSpecialaccount3())){
							voucherItem.setCheckcode("11");
						}else{
							voucherItem.setCheckcode("09");
						}
						voucherItem.setSubject(fundFlowBcc.getCustomer3());
						voucherItem.setGlflag(fundFlowBcc.getSpecialaccount3());
						voucherItem.setAmount(fundFlowBcc.getAmount3());
						voucherItem.setAmount2(fundFlowBcc.getStandardamount3());
						voucherItem.setDepid(fundFlowBcc.getDepid3());
//						voucherItem.setUncheckflag(fundFlowBcc.getAntiaccount3());
						if("Y".equals(fundFlowBcc.getAntiaccount3())){
							voucherItem.setUncheckflag("X");
		                }else{
		                	voucherItem.setUncheckflag("");
		                }
						voucherItem.setDebitcredit(fundFlowBcc.getDebtcredit3());
						voucherItem.setControlaccount(subject);
						voucherItem.setCaremark(subjectname);
						if(!StringUtils.isNullBlank(voucherItem.getGlflag())){
							subject = this.customerRefundItemJdbcDao.getSkont(voucherItem,subject);
							String subjectname2 = this.customerRefundItemJdbcDao.getSubjectNameById(subject,bukrs);
							voucherItem.setControlaccount(subject);
							voucherItem.setCaremark(subjectname2);
						}
						voucherItem.setText(billClearCollect.getText());
						voucherItem.setCurrency(currency);
						voucherItems.add(voucherItem);
					}
				}
			}
			
			BigDecimal sumAmount2_s = new BigDecimal(0); // 所有财务结算借方本位币金额的和
			BigDecimal sumAmount2_h = new BigDecimal(0); // 所有财务结算贷方本位币金额的和
			
			BigDecimal sumAmount3_s = new BigDecimal(0); // 所有财务结算借方金额的和
			BigDecimal sumAmount3_h = new BigDecimal(0); // 所有财务结算贷方金额的和
			int k=0;
			if(adjustAmount.compareTo(BigDecimal.valueOf(0)) == 0){
				k =0;
			}else{
				//第一条为调整金，不统计
				k=1;
			}
			for(int n=k;n<voucherItems.size();n++){
				
				VoucherItem voucherItem0 = (VoucherItem)voucherItems.get(n);
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
			if (sumAmount5.compareTo(adjustAmount) != 0){			 
				 return null;
			}
			//本位币借-贷相减
			BigDecimal sumAmount6 = sumAmount2_s.subtract(sumAmount2_h);
			
			if (sumAmount6.abs().compareTo(new BigDecimal(0)) == 1){
				for (VoucherItem voucherItem0 : voucherItems){
					if (voucherItem0.getRownumber().equals("1")){
						voucherItem0.setAmount2(sumAmount6.abs());						
					}
				}
			}
			//本位币为0 并且，金额也为0，不加行项目
			if (sumAmount6.abs().compareTo(new BigDecimal(0)) == 0 && adjustAmount.abs().compareTo(new BigDecimal(0)) == 0){
				for (VoucherItem voucherItem0 : voucherItems){					
					if (voucherItem0.getRownumber().equals("1")){
						voucherItems.remove(voucherItem0);
						break;
					}
				}
			}
			
			Set<VoucherItem> vi=new HashSet<VoucherItem>();
			vi.addAll(voucherItems);
			voucher.setVoucherItem(vi);
			
			voucher.setVoucherItem(vi);
			//判断是否已经生成损益凭证，
			Voucher voucher2=null;
			List<Voucher> li=voucherHibernateDao.getVoucherByBusinessId2(billClearCollect.getBillclearid()," ");
			if(li!=null && li.size()!=0){				
				for(Voucher vo : li){
					if(!StringUtils.isNullBlank(vo.getVoucherno())){
						voucher2=vo;
						break;
					}
				} 
			}
			if(null == voucher2){
				voucherService._saveOrUpdate(voucher, null, BusinessObjectService.getBusinessObject("Voucher"));
				voucherHibernateDao.flush();
			}else{
				voucher=voucher2;
			}
		}
		return voucher;
	}
	
	//生成清帐凭证
	public String genClearVoucher(BillClearCollect billClearCollect,Voucher voucher){
		String businessId = billClearCollect.getBillclearid();
		String customer = billClearCollect.getCustomer();
		// Set<BillClearItemPay> billClearItemPay;
		Set<BillClearItem> billClearItems = billClearCollect.getBillclearitem();
		Set<BillInCollect> billInCollects = billClearCollect.getBillincollect();
		
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
		
		// 重新计算未清金额(未收款、未付款等)
		for (BillClearItem billClearItem : billClearItems)
		{
			// 发票总金额
			BigDecimal billamount = billClearItem.getReceivableamount();
			// 发票已经审批完的，发票已清金额
			BigDecimal receivedamount = this.customerTitleJdbcDao.getSumClearAmount(billClearItem.getInvoice().trim(), BusinessState.ALL_COLLECT_PAIDUP);
			billClearItem.setReceivedamount(receivedamount);
			billClearItem.setUnreceivedamount(billamount.subtract(receivedamount));
			
			if(billClearItem.getUnreceivedamount().compareTo(billClearItem.getClearbillamount()) !=0){
				billF = true;
			}
			sumajAmount = sumajAmount.add(billClearItem.getAdjustamount());
			sumbillAmount = sumbillAmount.add(billClearItem.getReceivableamount());
			sumclearAmount = sumclearAmount.add(billClearItem.getClearbillamount());
			billAmount = billAmount.add(billClearItem.getBwbje());
			sumUnreceivableamou = sumUnreceivableamou.add(billClearItem.getUnreceivedamount());
			sumUnoffsetamountBwb = sumUnoffsetamountBwb.add(billClearItem.getUnbwbje());
//			sumclearAmountBwb = sumclearAmountBwb.add(billClearItem.getBwbje().divide(billClearItem.getReceivableamount(),3,BigDecimal.ROUND_HALF_EVEN).multiply(billClearItem.getClearbillamount()));
			sumclearAmountBwb = sumclearAmountBwb.add(billClearItem.getBwbje().multiply(billClearItem.getClearbillamount()).divide(billClearItem.getReceivableamount(),13,BigDecimal.ROUND_HALF_EVEN));
		}
		for (BillInCollect billInCollect : billInCollects)
		{
			// 款总金额
			BigDecimal goodsamount = billInCollect.getCollectamount();
			// 收款单已审批完的 ，已清票款
			BigDecimal clearedPaymentAmount = this.collectItemService.getSumClearAmount(billInCollect.getCollectitemid().trim(), BusinessState.ALL_COLLECT_PAIDUP);
			// 款未抵消金额
			billInCollect.setUnoffsetamount(goodsamount.subtract(clearedPaymentAmount));
			collectAmount = collectAmount.add(billInCollect.getBwbje());
			if(billInCollect.getUnoffsetamount().compareTo(billInCollect.getNowclearamount()) !=0){
				clearF = true;
			}
			sumcollectAmount = sumcollectAmount.add(billInCollect.getCollectamount());
			sumoffsetAmount = sumoffsetAmount.add(billInCollect.getNowclearamount());
			sumUnoffsetamount = sumUnoffsetamount.add(billInCollect.getUnoffsetamount());
			sumUnreceivableamouBwb= sumUnreceivableamouBwb.add(billInCollect.getUnbwbje());
//			sumoffsetAmountBwb = sumoffsetAmountBwb.add(billInCollect.getBwbje().divide(billInCollect.getCollectamount(),3,BigDecimal.ROUND_HALF_EVEN).multiply(billInCollect.getNowclearamount()));
			sumoffsetAmountBwb = sumoffsetAmountBwb.add(billInCollect.getBwbje().multiply(billInCollect.getNowclearamount()).divide(billInCollect.getCollectamount(),13,BigDecimal.ROUND_HALF_EVEN));
		}
		billClearCollect.setBillclearitem(billClearItems);
		billClearCollect.setBillincollect(billInCollects);
		String taxCode =  getTaxCode(billClearCollect);
		// 币别
		String currency = "";
		String deptId = billClearCollect.getDeptid();
		String bukrs = this.voucherService.getCompanyIDByDeptID(deptId);
		// 凭证
		Voucher clearVoucher = getClearVoucher(billClearCollect, bukrs);
		Set<VoucherItem> voucherItemList = new HashSet<VoucherItem>();
		Iterator<BillClearItem> it = billClearItems.iterator();

		int rowNo = 1; // 行项目号
		while (it.hasNext())
		{
			BillClearItem unClearSupplieBill = (BillClearItem) it.next();

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
		
	
		
		
		if(null != voucher){
			for(VoucherItem voucherItem : voucher.getVoucherItem()){
				
				if (StringUtils.isNullBlank(voucherItem.getGlflag()) &&"11".equals(voucherItem.getCheckcode()))
				{
					ajValue =  ajValue.add(voucherItem.getAmount2());
				}
				else if (StringUtils.isNullBlank(voucherItem.getGlflag()) &&"01".equals(voucherItem.getCheckcode()))
				{
					ajValue =  ajValue.add(voucherItem.getAmount2());
				}
			}
		}
		
		if (judgeIsAllClear(billClearCollect))
		{
			// 处理本次可以全清帐
			getClearVoucherItem(billClearCollect, clearVoucher, voucherItemList, currency ,ajValue,taxCode);
		}
		else
		{
			// 合同
			List<String> clearedContractNo = judgeContractIsAllClear(billClearCollect);
			if (null != clearedContractNo && clearedContractNo.size() > 0)
			{
				// 处理合同下清帐凭证行项目.
				contractNos = getContractClearVoucherItem(billClearCollect, clearedContractNo, clearVoucher, voucherItemList, currency, billValue, itemValue, ajValue);
			}

			if (!StringUtils.isNullBlank(contractNos))
			{
				contractNos = contractNos.substring(0, contractNos.length() - 1);
			}
			// 立项下
			List<String> clearedProjectNo = judgeProjectIsAllClear(billClearCollect, clearedContractNo, contractNos);
			if (null != clearedProjectNo && clearedProjectNo.size() > 0)
			{
				// 处理立项下清帐凭证行项目.
				projectNos = getProjectClearVoucherItem(billClearCollect, clearVoucher, voucherItemList, contractNos, clearedContractNo, clearedProjectNo, currency, billValue, itemValue, ajValue);
			}

			if (!StringUtils.isNullBlank(projectNos))
			{
				projectNos = projectNos.substring(0, projectNos.length() - 1);
			}
			//客户清帐
			if (judgeSupplierIsAllClear(billClearCollect, customer, contractNos, projectNos, bukrs))
			{
				getCustomerClearVoucherItem(billClearCollect, clearVoucher, voucherItemList, contractNos, projectNos, clearedContractNo, clearedProjectNo, currency, billValue, itemValue,ajValue, clearVoucher.getCompanycode());
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
//						subtractVlaue2 = collectAmount.subtract(subtractVlaue2);
						//修改成各个行项目算汇率，再算清账本位币再相加
						subtractVlaue2 = sumUnreceivableamouBwb.subtract(sumclearAmountBwb);
					}
					
				}
					if (null != voucher && subtractVlaue2.abs().compareTo(new BigDecimal(0)) != 0)
					{
						
						subtractVlaue2 = subtractVlaue2.subtract(ajValue);
						VoucherItem supplierVoucherItem = getProfitAndLossVoucherItem(billClearCollect, subtractVlaue2, currency,taxCode, "1");
						supplierVoucherItem.setRownumber("3");
						supplierVoucherItem.setVoucher(voucher);
						voucher.getVoucherItem().add(supplierVoucherItem);						
	
						VoucherItem profOrLossVoucherItem = getProfitAndLossVoucherItem(billClearCollect, subtractVlaue2, currency,taxCode, "2");
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
					subtractVlaue2 = sumUnreceivableamouBwb.subtract(sumUnoffsetamountBwb);
					
				}else{
							
					//票的一边全清
					if(!billF){
						//算款，的本位币
//						subtractVlaue2 = collectAmount.divide(sumcollectAmount,3,BigDecimal.ROUND_HALF_EVEN).multiply(sumoffsetAmount).subtract(billAmount);
//						subtractVlaue2 = collectAmount.divide(sumUnoffsetamount,5,BigDecimal.ROUND_HALF_EVEN).multiply(sumoffsetAmount).subtract(billAmount);
						subtractVlaue2 = sumoffsetAmountBwb.subtract(sumUnoffsetamountBwb);
					}
					//款的一边全清
					if(!clearF){
						//算票，的本位币
//						subtractVlaue2 = billAmount.divide(sumbillAmount,3,BigDecimal.ROUND_HALF_EVEN).multiply(sumclearAmount);
//						subtractVlaue2 = billAmount.divide(sumUnreceivableamou,5,BigDecimal.ROUND_HALF_EVEN).multiply(sumclearAmount);
//						subtractVlaue2 = collectAmount.subtract(subtractVlaue2);
						subtractVlaue2 = sumUnreceivableamouBwb.subtract(sumclearAmountBwb);
					}
				}
					if (subtractVlaue2.abs().compareTo(new BigDecimal(0)) != 0)
					{
						Voucher voucher2 = getVoucher2(billClearCollect);
//						UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
						voucher2.setAgkoa(" ");
						voucher2.setAgkon(" ");
						voucher2.setBstat(" ");
						voucher2.setGsber(" ");
						voucher2.setFlag(" ");
						voucher2.setVouchertype("SA");
//						clearVoucher.setImporter(userContext.getUser().getUserName()); // 输入者
//						clearVoucher.setPreparer(userContext.getUser().getUserName()); // 预制者
						voucher2.setValut(" ");
						VoucherItem supplierVoucherItem = getProfitAndLossVoucherItem(billClearCollect, subtractVlaue2, currency,taxCode, "1");
						supplierVoucherItem.setVoucher(voucher2);
						voucherItemList.add(supplierVoucherItem);
	
						VoucherItem profOrLossVoucherItem = getProfitAndLossVoucherItem(billClearCollect, subtractVlaue2, currency,taxCode, "2");
						profOrLossVoucherItem.setVoucher(voucher2);
						voucherItemList.add(profOrLossVoucherItem);
						voucher2.setVoucherItem(voucherItemList);
						
						//判断是否已经生成损益凭证，
						Voucher voucher3=null;
						List<Voucher> li=voucherHibernateDao.getVoucherByBusinessId2(billClearCollect.getBillclearid()," ");
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
						
						
						return businessId;
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
			//判断是否已经生成清账凭证，
			Voucher clearVoucher2=null;
			List<Voucher> li=voucherHibernateDao.getVoucherByBusinessId2(billClearCollect.getBillclearid(),"A");
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
			
			return businessId;
		}
		return null;
	}
	
	/**
	 * 取得taxCode
	 * @param customSingleClear
	 * @return
	 */
	public String getTaxCode(BillClearCollect billClearCollect){
		Set<BillClearItem> billClearItems = billClearCollect.getBillclearitem();
		Set<BillInCollect> billInCollects = billClearCollect.getBillincollect();
		//合同号列表
		List<String> contractNoList = new ArrayList<String>();
		String projectNo =" ";
		String contractNo = " ";
		// 重新计算未清金额(未收款、未付款等)
		for (BillClearItem billClearItem : billClearItems)
		{
			// 发票总金额
			BigDecimal billamount = billClearItem.getReceivableamount();
			// 发票已经审批完的，发票已清金额
			BigDecimal receivedamount = this.customerTitleJdbcDao.getSumClearAmount(billClearItem.getInvoice().trim(), BusinessState.ALL_COLLECT_PAIDUP);
			
			BigDecimal unreceivableamou = billamount.subtract(receivedamount);
			if(unreceivableamou.compareTo(billClearItem.getClearbillamount()) !=0){
				
				projectNo = billClearItem.getProject_no();
			}
			
			contractNo = billClearItem.getContract_no();
			contractNoList.add(contractNo);
		}
		for (BillInCollect billInCollect : billInCollects)
		{
			// 款总金额
			BigDecimal goodsamount = billInCollect.getCollectamount();
			// 收款单已审批完的 ，已清票款
			BigDecimal clearedPaymentAmount = this.collectItemService.getSumClearAmount(billInCollect.getCollectitemid().trim(), BusinessState.ALL_COLLECT_PAIDUP);
			// 款未抵消金额			
			BigDecimal unoffsetamount = goodsamount.subtract(clearedPaymentAmount);
			if(unoffsetamount.compareTo(billInCollect.getNowclearamount()) !=0){				
				projectNo = billInCollect.getProject_no();
			}			
			contractNo = billInCollect.getContract_no();
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
	 * 取得清帐凭证抬头
	 * 
	 * @param supplierSinglClear
	 */
	private Voucher getClearVoucher(BillClearCollect billClearCollect, String bukrs)
	{
		Voucher voucher = new Voucher();
		String fiperiod = "";
		String fiyear = "";
		// 添加凭证抬头数据
		voucher.setBusinessid(billClearCollect.getBillclearid());
		voucher.setBusinesstype("03"); // 票清收款
		voucher.setVouchertype("SA");
		voucher.setCompanycode(bukrs);
		voucher.setVoucherclass("9");
		String accountdate = billClearCollect.getAccountdate().replace("-", "");
		BigDecimal sumAmount2 = new BigDecimal(0); // 所有财务结算本位币金额的和
		BigDecimal amount2 = new BigDecimal(0); // 财务结算本位币金额
		voucher.setCheckdate(accountdate);
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
		//把部门ID转成业务范围
		String depid = this.sysDeptJdbcDao.getDeptCodeById(billClearCollect.getDeptid());
		voucher.setGsber(depid);
		// 凭证日期
		voucher.setVoucherdate(billClearCollect.getVoucherdate().replace("-", ""));
		voucher.setVouchertext("清帐凭证"); // 凭证抬头文本
		// 付款清帐标识
		voucher.setFlag("R");
		// 凭证分类
		voucher.setVoucherclass("");
		// 计息日
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		voucher.setValut(dateFormat.format(new Date()));
		// 客户编号
		voucher.setAgkon(billClearCollect.getCustomer());
		// 科目类型
		voucher.setAgkoa("D");
		// 清帐凭证状态
		voucher.setBstat("A");
		// 汇率
		voucher.setExchangerate(new BigDecimal("1"));

		return voucher;
	}
	
	/**
	 * 判断单据是否可全清帐
	 * 
	 * @param supplierSinglClear
	 * @return
	 */
	public boolean judgeIsAllClear(BillClearCollect billClearCollect)
	{
		Set<BillClearItem> billClearItems = billClearCollect.getBillclearitem();
		Set<BillInCollect> billInCollects = billClearCollect.getBillincollect();
		// 应付款总计
		BigDecimal sumBillAmount = new BigDecimal(0);
		// 清帐金额总计
		BigDecimal sumBillClearAmount = new BigDecimal(0);
		for (BillClearItem billClearItem : billClearItems)
		{
			sumBillAmount = sumBillAmount.add(billClearItem.getReceivableamount());
			sumBillClearAmount = sumBillClearAmount.add(billClearItem.getClearbillamount());
		}
		// 付款单金额总计
		BigDecimal sumFundAmount = new BigDecimal(0);
		// 本次抵消金额总计
		BigDecimal sumFundClearAmount = new BigDecimal(0);
		for (BillInCollect billInCollect : billInCollects)
		{
			sumFundAmount = sumFundAmount.add(billInCollect.getCollectamount());
			sumFundClearAmount = sumFundClearAmount.add(billInCollect.getNowclearamount());
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
	public List<String> judgeContractIsAllClear(BillClearCollect billClearCollect)
	{
		List<String> clearedContractNo = new ArrayList<String>();
		Set<BillClearItem> billClearItems = billClearCollect.getBillclearitem();
		Set<BillInCollect> billInCollects = billClearCollect.getBillincollect();

		// 取得票下合同列表
		List<String> contractNos = getContractNoList(billClearItems);
		if (null != contractNos && contractNos.size() > 0)
		{
			log.debug("共有" + contractNos.size() + "个合同：");
			for (String contractNo : contractNos)
			{
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
				List<VoucherItem> voucherItems = voucherItemJdbcDao.getVoucherItems(billClearCollect.getCustomer(), contractNo);
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
				
				for (BillClearItem billClearItem : billClearItems)
				{
					if (contractNo.equals(billClearItem.getContract_no()))
					{
						billUnClearAmount = billUnClearAmount.add(billClearItem.getUnreceivedamount());
						billClearAmount = billClearAmount.add(billClearItem.getClearbillamount());
						receivableamount = receivableamount.add(billClearItem.getReceivableamount());
						ajAmount = ajAmount.add(billClearItem.getAdjustamount());
					}
				}
				log.debug("合同号:" + contractNo + "billUnClearAmount:" + billUnClearAmount + ", billClearAmount:" + billClearAmount);

				if (billUnClearAmount.compareTo(billClearAmount) == 0)
				{
					// 款抵消金额
					BigDecimal fundClearamount = new BigDecimal(0.00);
					// 款未抵消金额
					BigDecimal fundUnClearAmount = new BigDecimal(0.00);
					for (BillInCollect billInCollect : billInCollects)
					{
						//如果有保证金不能在合同层，和立项层清帐
						if(billInCollect.getSuretybond().compareTo(new BigDecimal(0)) != 0){
							return clearedContractNo;
						}
						if (contractNo.equals(billInCollect.getContract_no()))
						{
							fundClearamount = fundClearamount.add(billInCollect.getNowclearamount());
							fundUnClearAmount = fundUnClearAmount.add(billInCollect.getUnoffsetamount());
							amount = amount.add(billInCollect.getCollectamount());
						}
					}
					log.debug("合同号:" + contractNo + "fundClearamount:" + fundClearamount + ", fundUnClearAmount:" + fundUnClearAmount);
					// 款上，款本次抵消金额合计==款未抵消金额
					if (fundClearamount.compareTo(fundUnClearAmount) == 0)
					{
						// 合同下已清票金额合计
						BigDecimal sumAllContractBillTot = this.customerTitleJdbcDao.getSumAmount(contractNo);
						BigDecimal sumAllContractFundTot = this.collectItemJdbcDao.getSumAmount(contractNo);
						BigDecimal tot1 = sumAllContractBillTot.add(billClearAmount);
						BigDecimal tot2 = sumAllContractFundTot.add(fundClearamount);

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
	public List<String> judgeProjectIsAllClear(BillClearCollect billClearCollect, List<String> clearedContractNo, String contractNos)
	{
		Set<BillClearItem> billClearItems = billClearCollect.getBillclearitem();
		Set<BillInCollect> billInCollects = billClearCollect.getBillincollect();
		List<String> clearedProjectNo = new ArrayList<String>();

		// 取得票下立项列表
		List<String> projectNos = getProjectNoList(billClearItems);
		if (null != projectNos && projectNos.size() > 0)
		{
			log.debug("共有" + projectNos.size() + "个立项：");
			for (String projectNo : projectNos)
			{
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
				List<VoucherItem> voucherItems = voucherItemJdbcDao.getVoucherItems(billClearCollect.getCustomer(), projectNo);
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
				
				for (BillClearItem billClearItem : billClearItems)
				{
					String contract_no = billClearItem.getContract_no();
					if (projectNo.equals(billClearItem.getProject_no()))
					{
						if (null != clearedContractNo && clearedContractNo.contains(contract_no))
						{
							continue;
						}
						else
						{
							billUnClearAmount = billUnClearAmount.add(billClearItem.getUnreceivedamount());
							billClearAmount = billClearAmount.add(billClearItem.getClearbillamount());
							receivableamount = receivableamount.add(billClearItem.getReceivableamount());
							ajAmount = ajAmount.add(billClearItem.getAdjustamount());
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
					for (BillInCollect billInCollect : billInCollects)
					{
						//如果有保证金不能在合同层，和立项层清帐
						if(billInCollect.getSuretybond().compareTo(new BigDecimal(0)) != 0){
							return clearedProjectNo;
						}
						String contract_no = billInCollect.getProject_no();
						if (projectNo.equals(billInCollect.getProject_no()))
						{
							if (null != clearedContractNo && clearedContractNo.contains(contract_no))
							{
								continue;
							}
							else
							{
								fundClearamount = fundClearamount.add(billInCollect.getNowclearamount());
								fundUnClearAmount = fundUnClearAmount.add(billInCollect.getUnoffsetamount());
								amount = amount.add(billInCollect.getCollectamount());
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
						BigDecimal sumAllContractRefundTot = this.customerTitleJdbcDao.getSumRefundAmountByProject(billClearCollect.getCustomer(), BusinessState.ALL_BILLCLEAR_PAIDUP,projectNo, contractNos);
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
	 * 判断单据下客户是否可全清帐
	 * 
	 * @param billClearPayment
	 * @param supplier
	 * @param contractNos
	 * @param projectNos
	 * @param bukrs
	 * @return
	 */
	public boolean judgeSupplierIsAllClear(BillClearCollect billClearCollect, String customer, String contractNos, String projectNos, String bukrs)
	{
		Set<BillClearItem> billClearItems = billClearCollect.getBillclearitem();
		Set<BillInCollect> billInCollects = billClearCollect.getBillincollect();
		// 票清帐金额合计
		BigDecimal sumBillClearAmount = new BigDecimal(0);
		
		// (未付款、未收款)合计
		BigDecimal billUnClearAmount = new BigDecimal(0);
		//应收款金额
		BigDecimal receivableamount = new BigDecimal(0);
		
		BigDecimal amount = new BigDecimal(0);
		//调整金金额
		BigDecimal ajAmount = new BigDecimal(0);
		
		for (BillClearItem billClearItem : billClearItems)
		{
			sumBillClearAmount = sumBillClearAmount.add(billClearItem.getClearbillamount());
			billUnClearAmount = billUnClearAmount.add(billClearItem.getUnreceivedamount());
			receivableamount = receivableamount.add(billClearItem.getReceivableamount());
			ajAmount = ajAmount.add(billClearItem.getAdjustamount());
		}
		if (billUnClearAmount.compareTo(sumBillClearAmount) == 0)
		{
			// 款清帐金额合计
			BigDecimal sumFundClearAmount = new BigDecimal(0);
			// 款未抵消金额
			BigDecimal fundUnClearAmount = new BigDecimal(0.00);
			
			for (BillInCollect billInCollect : billInCollects)
			{
				sumFundClearAmount = sumFundClearAmount.add(billInCollect.getNowclearamount());
				fundUnClearAmount = fundUnClearAmount.add(billInCollect.getUnoffsetamount());
				amount = amount.add(billInCollect.getCollectamount());
			}
			// 款上，款本次抵消金额合计==款未抵消金额
			if (sumFundClearAmount.compareTo(fundUnClearAmount) == 0)
			{
				
				
				//得到之前汇损，或调整金的voucheritem
				List<VoucherItem> voucherItems = voucherItemJdbcDao.getVoucherItems(billClearCollect.getCustomer(), "");
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
				// 客户下已清票金额合计
				BigDecimal sumAllSupplierBillTot = this.customerTitleJdbcDao.getSumAmount(customer, contractNos, projectNos, bukrs);
				BigDecimal sumAllSupplierFundTot = this.collectItemJdbcDao.getSumAmount(customer, contractNos, projectNos, bukrs);
				//合同下已清票退款金额合计
				BigDecimal sumAllContractRefundTot = this.customerTitleJdbcDao.getSumRefundAmount(customer, BusinessState.ALL_BILLCLEAR_PAIDUP,projectNos, contractNos,billClearCollect.getDeptid());
				BigDecimal tot1 = receivableamount.add(sumAllSupplierBillTot);
				tot1 = tot1.add(sumAllContractRefundTot);
				BigDecimal tot2 = amount.add(sumAllSupplierFundTot);
				tot2 = tot2.add(ajAmount);
				log.debug("sumBillClearAmount:" + sumBillClearAmount + ",sumAllSupplierBillTot:" + sumAllSupplierBillTot);
				log.debug("sumFundClearAmount:" + sumFundClearAmount + ",sumAllSupplierFundTot:" + sumAllSupplierFundTot);
		
//				if (tot1.compareTo(tot2) == 0 && sumAllSupplierBillTot.compareTo(new BigDecimal(0)) == 1 && sumAllSupplierFundTot.compareTo(new BigDecimal(0)) == 1)
				if (tot1.compareTo(tot2) == 0 )
				{
					log.debug("客户" + customer + "，可以清帐。");
					return true;
				}
				else
				{
					log.debug("客户" + customer + "，可以清帐。");
				}
			}
		}
		return false;
	}
	
	/**
	 * 处理本次可以全清帐
	 * 
	 * @param supplierSinglClear
	 * @param currency
	 * @param voucher
	 * @param voucherItemList
	 */
	private void getClearVoucherItem(BillClearCollect billClearCollect, Voucher clearVoucher, Set<VoucherItem> voucherItemList, String currency,BigDecimal ajValue ,String taxCode)
	{
		// 得到票的本位币之和
		BigDecimal billValue = new BigDecimal("0");
		// 得到分配项的本位币之和
		BigDecimal itemValue = new BigDecimal("0");
		// 分配项之和和票之和的差
		BigDecimal subtractVlaue = new BigDecimal("0");
		Set<BillClearItem> billClearItems = billClearCollect.getBillclearitem();
		Set<BillInCollect> billInCollects = billClearCollect.getBillincollect();
		for (BillClearItem billClearItem : billClearItems)
		{
			String invoice = billClearItem.getInvoice();
			ClearVoucherItemStruct clearVoucherItemStruct = customerTitleJdbcDao.getCurrectBillInfo(invoice);
			VoucherItem voucherItem = getClearVoucherItem(clearVoucherItemStruct);

			billValue = billValue.add(clearVoucherItemStruct.getBwbje());
			voucherItem.setVoucher(clearVoucher);
			voucherItemList.add(voucherItem);
		}

		for (BillInCollect billInCollect : billInCollects)
		{
			//银行/商业承兑汇票,收款
			VoucherItem voucheritem2 = this.voucherItemJdbcDao.getVoucherItem(billInCollect.getCollectitemid(), "1,4");
			
			
			if(null !=voucheritem2){
				ClearVoucherItemStruct clearVoucherItemStruct = new ClearVoucherItemStruct();
				clearVoucherItemStruct.setRowno(voucheritem2.getRownumber());
				clearVoucherItemStruct.setVoucherno(billInCollect.getVoucherno());
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
				VoucherItem supplierVoucherItem = getProfitAndLossVoucherItem(billClearCollect, subtractVlaue, currency,taxCode, "1");
				supplierVoucherItem.setVoucher(clearVoucher);
				voucherItemList.add(supplierVoucherItem);

				VoucherItem profOrLossVoucherItem = getProfitAndLossVoucherItem(billClearCollect, subtractVlaue, currency,taxCode, "2");
				profOrLossVoucherItem.setVoucher(clearVoucher);
				voucherItemList.add(profOrLossVoucherItem);
			}
		}

		clearVoucher.setVoucherItem(voucherItemList);

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
	private String getContractClearVoucherItem(BillClearCollect billClearCollect, List<String> clearedContractNo, Voucher clearVoucher, Set<VoucherItem> voucherItemList, String currency, BigDecimal billValue, BigDecimal itemValue,BigDecimal ajValue)
	{
		String contractNos = "";
		// 分配项之和和票之和的差
		BigDecimal subtractVlaue = new BigDecimal("0");
		Set<BillClearItem> billClearItems = billClearCollect.getBillclearitem();
		Set<BillInCollect> billInCollects = billClearCollect.getBillincollect();
		// 循环所有合同号。
		for (String conntractNo : clearedContractNo)
		{
			contractNos = contractNos + "'" + conntractNo + "',";
			// 得到之前合同的票信息
			List<ClearVoucherItemStruct> clearVoucherItemStructList = this.customerTitleJdbcDao.getContractBillInfo(billClearCollect.getCustomer(),conntractNo);
			for (ClearVoucherItemStruct clearVoucherItemStruct : clearVoucherItemStructList)
			{
				VoucherItem voucherItem = getClearVoucherItem(clearVoucherItemStruct);
				billValue = billValue.add(clearVoucherItemStruct.getBwbje());
				voucherItem.setVoucher(clearVoucher);
				voucherItemList.add(voucherItem);
			}
			
			//得到之前汇损，或调整金的voucheritem
			List<VoucherItem> voucherItems = voucherItemJdbcDao.getVoucherItems(billClearCollect.getCustomer(), conntractNo);
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
			Iterator<BillClearItem> billClearItemIt = billClearItems.iterator();
			while (billClearItemIt.hasNext())
			{
				BillClearItem billClearItem = billClearItemIt.next();
				if (conntractNo.equals(billClearItem.getContract_no()))
				{
//					ClearVoucherItemStruct clearVoucherItemStruct = this.customerTitleJdbcDao.getCurrectBillInfo(billClearItem.getInvoice());
//					VoucherItem voucherItem = getClearVoucherItem(clearVoucherItemStruct);
//					billValue = billValue.add(clearVoucherItemStruct.getBwbje());
//					voucherItem.setVoucher(clearVoucher);
//					voucherItemList.add(voucherItem);
					
					ClearVoucherItemStruct clearVoucherItemStruct = new ClearVoucherItemStruct();
					CustomerTitle customerTitle = this.customerTitleJdbcDao.getByCustomertitleid(billClearItem.getTitleid());				 
					clearVoucherItemStruct.setRowno(customerTitle.getBuzei());
					clearVoucherItemStruct.setVoucherno(billClearItem.getVoucherno());
					clearVoucherItemStruct.setYear(customerTitle.getGjahr());
					VoucherItem voucherItem = getClearVoucherItem(clearVoucherItemStruct);						
					billValue = billValue.add(customerTitle.getBwbje());	
					voucherItem.setVoucher(clearVoucher);
					voucherItemList.add(voucherItem);
				}
			}

			// 得到之前的款分配信息
			List<ClearVoucherItemStruct> clearVoucherItemStructList2 = this.customerTitleJdbcDao.getContractCollectItemInfo(billClearCollect.getCustomer(),conntractNo);
			for (ClearVoucherItemStruct clearVoucherItemStruct : clearVoucherItemStructList2)
			{
				VoucherItem voucherItem = getClearVoucherItem(clearVoucherItemStruct);
				itemValue = itemValue.add(clearVoucherItemStruct.getBwbje());
				voucherItem.setVoucher(clearVoucher);
				voucherItemList.add(voucherItem);
			}
			//退款的行项目信息
			List<ClearVoucherItemStruct> clearVoucherItemStructList3 = this.customerTitleJdbcDao.getRefundItemByCustomerNoAndContractNo(billClearCollect.getCustomer(), BusinessState.ALL_BILLCLEAR_PAIDUP, conntractNo);
			for (ClearVoucherItemStruct clearVoucherItemStruct : clearVoucherItemStructList3)
			{
				VoucherItem voucherItem = getClearVoucherItem(clearVoucherItemStruct);
				billValue = billValue.add(clearVoucherItemStruct.getBwbje());
				voucherItem.setVoucher(clearVoucher);
				voucherItemList.add(voucherItem);
			}
			// 得到本次分配项的行项目信息
			Iterator<BillInCollect> billInCollectsIt = billInCollects.iterator();
			while (billInCollectsIt.hasNext())
			{
				BillInCollect billInCollect = billInCollectsIt.next();
				if (conntractNo.equals(billInCollect.getContract_no()))
				{
					Collect collect = this.collectJdbcDao.getCollectByCollectNo(billInCollect.getCollectno());
					Voucher oldVoucher = this.voucherService.getVoucherByBusinessId(collect.getCollectid(), "1");
					Set<VoucherItem> oldVoucherItem = oldVoucher.getVoucherItem();
					Iterator<VoucherItem> payVoucherItemit = oldVoucherItem.iterator();
					while (payVoucherItemit.hasNext())
					{
						VoucherItem oldItem = payVoucherItemit.next();
						if (oldItem.getVoucher().getBusinessid().equals(collect.getCollectid()) && oldItem.getBusinessitemid().equals(billInCollect.getCollectitemid()))
						{
							ClearVoucherItemStruct clearVoucherItemStruct = new ClearVoucherItemStruct();
							clearVoucherItemStruct.setRowno(oldItem.getRownumber());
							clearVoucherItemStruct.setVoucherno(billInCollect.getVoucherno());
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
					
					VoucherItem supplierVoucherItem = getProfitAndLossVoucherItem(billClearCollect, subtractVlaue, currency,conntractNo, "1");
					supplierVoucherItem.setVoucher(clearVoucher);
					voucherItemList.add(supplierVoucherItem);

					VoucherItem profOrLossVoucherItem = getProfitAndLossVoucherItem(billClearCollect, subtractVlaue, currency,conntractNo, "2");
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
	private String getProjectClearVoucherItem(BillClearCollect billClearCollect, Voucher clearVoucher, Set<VoucherItem> voucherItemList, String contractNos, List<String> clearedContractNo, List<String> clearedProjectNo, String currency, BigDecimal billValue, BigDecimal itemValue ,BigDecimal ajValue)
	{
		String projectNos = "";
		// 分配项之和和票之和的差
		BigDecimal subtractVlaue = new BigDecimal("0");
		Set<BillClearItem> billClearItems = billClearCollect.getBillclearitem();
		Set<BillInCollect> billInCollects = billClearCollect.getBillincollect();
		// 循环所有立項号。
		for (String projectNo : clearedProjectNo)
		{
			projectNos = projectNos + "'" + projectNo + "',";
			// 得到之前合同的票信息
			List<ClearVoucherItemStruct> clearVoucherItemStructs = this.customerTitleJdbcDao.getProjectBillInfo(billClearCollect.getCustomer(),projectNo, contractNos);
			for (ClearVoucherItemStruct clearVoucherItemStruct : clearVoucherItemStructs)
			{
				VoucherItem voucherItem = getClearVoucherItem(clearVoucherItemStruct);
				voucherItem.setVoucher(clearVoucher);
				billValue = billValue.add(clearVoucherItemStruct.getBwbje());
				voucherItemList.add(voucherItem);
			}
			//得到之前汇损，或调整金的voucheritem
			List<VoucherItem> voucherItems = voucherItemJdbcDao.getVoucherItems(billClearCollect.getCustomer(), projectNo);
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
			Iterator<BillClearItem> billClearItemIt = billClearItems.iterator();
			while (billClearItemIt.hasNext())
			{
				BillClearItem billClearItem = billClearItemIt.next();
				if (clearedContractNo.contains(billClearItem.getContract_no()))
				{
					log.debug("合同号:" + billClearItem.getContract_no() + "已经存在已清合同列表," + contractNos);
				}
				if (projectNo.equals(billClearItem.getProject_no()) && !clearedContractNo.contains(billClearItem.getContract_no()))
				{
//					ClearVoucherItemStruct clearVoucherItemStruct = this.customerTitleJdbcDao.getCurrectBillInfo(billClearItem.getInvoice());
//					VoucherItem voucherItem = getClearVoucherItem(clearVoucherItemStruct);
//					voucherItem.setVoucher(clearVoucher);
//					billValue = billValue.add(clearVoucherItemStruct.getBwbje());
//					voucherItemList.add(voucherItem);
					
					ClearVoucherItemStruct clearVoucherItemStruct = new ClearVoucherItemStruct();
					CustomerTitle customerTitle = this.customerTitleJdbcDao.getByCustomertitleid(billClearItem.getTitleid());				 
					clearVoucherItemStruct.setRowno(customerTitle.getBuzei());
					clearVoucherItemStruct.setVoucherno(billClearItem.getVoucherno());
					clearVoucherItemStruct.setYear(customerTitle.getGjahr());
					VoucherItem voucherItem = getClearVoucherItem(clearVoucherItemStruct);						
					billValue = billValue.add(customerTitle.getBwbje());	
					voucherItem.setVoucher(clearVoucher);
					voucherItemList.add(voucherItem);
				}
			}

			// 得到之前的款分配信息
			List<ClearVoucherItemStruct> clearVoucherItemStructs2 = this.customerTitleJdbcDao.getProjectCollectItemInfo(billClearCollect.getCustomer(),projectNo, contractNos);
			for (ClearVoucherItemStruct clearVoucherItemStruct : clearVoucherItemStructs2)
			{
				VoucherItem voucherItem = getClearVoucherItem(clearVoucherItemStruct);
				voucherItem.setVoucher(clearVoucher);
				itemValue = itemValue.add(clearVoucherItemStruct.getBwbje());
				voucherItemList.add(voucherItem);
			}
			//退款的行项目信息
			List<ClearVoucherItemStruct> clearVoucherItemStructList3 = this.customerTitleJdbcDao.getRefundItemByCustomerNoAndProjectNo(billClearCollect.getCustomer(), BusinessState.ALL_BILLCLEAR_PAIDUP, projectNo,contractNos);
			for (ClearVoucherItemStruct clearVoucherItemStruct : clearVoucherItemStructList3)
			{
				VoucherItem voucherItem = getClearVoucherItem(clearVoucherItemStruct);
				billValue = billValue.add(clearVoucherItemStruct.getBwbje());
				voucherItem.setVoucher(clearVoucher);
				voucherItemList.add(voucherItem);
			}
			// 得到本次分配项的行项目信息
			Iterator<BillInCollect> billInCollectIt = billInCollects.iterator();
			while (billInCollectIt.hasNext())
			{
				BillInCollect billInCollect = billInCollectIt.next();
				if (clearedContractNo.contains(billInCollect.getContract_no()))
				{
					log.debug("合同号:" + billInCollect.getContract_no() + "已经存在已清合同列表," + contractNos);
				}
				if (projectNo.equals(billInCollect.getProject_no()) && !clearedContractNo.contains(billInCollect.getContract_no()))
				{
					Collect collect = this.collectJdbcDao.getCollectByCollectNo(billInCollect.getCollectno());
					Voucher oldVoucher = this.voucherService.getVoucherByBusinessId(collect.getCollectid(), "1");
					Set<VoucherItem> oldVoucherItem = oldVoucher.getVoucherItem();
					Iterator<VoucherItem> payVoucherItemit = oldVoucherItem.iterator();
					while (payVoucherItemit.hasNext())
					{
						VoucherItem oldItem = payVoucherItemit.next();
						if (oldItem.getVoucher().getBusinessid().equals(collect.getCollectid()) && oldItem.getBusinessitemid().equals(billInCollect.getCollectitemid()))
						{
							ClearVoucherItemStruct clearVoucherItemStruct = new ClearVoucherItemStruct();
							clearVoucherItemStruct.setRowno(oldItem.getRownumber());
							clearVoucherItemStruct.setVoucherno("");
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
					
					VoucherItem supplierVoucherItem = getProfitAndLossVoucherItem(billClearCollect, subtractVlaue, currency,projectNo, "1");
					supplierVoucherItem.setVoucher(clearVoucher);
					voucherItemList.add(supplierVoucherItem);

					VoucherItem profOrLossVoucherItem = getProfitAndLossVoucherItem(billClearCollect, subtractVlaue, currency,projectNo, "2");
					profOrLossVoucherItem.setVoucher(clearVoucher);
					voucherItemList.add(profOrLossVoucherItem);
				}
			}

		}
		return projectNos;
	}
	
	/**
	 * 处理客户下清帐凭证行项目.
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
	private void getCustomerClearVoucherItem(BillClearCollect billClearCollect, Voucher clearVoucher, Set<VoucherItem> voucherItemList, String contractNos, String projectNos, List<String> clearedContractNo, List<String> clearedProjectNo, String currency, BigDecimal billValue, BigDecimal itemValue,BigDecimal ajValue, String bukrs)
	{
		String supplier = billClearCollect.getCustomer();
		// 分配项之和和票之和的差
		BigDecimal subtractVlaue = new BigDecimal("0");
		Set<BillClearItem> billClearItems = billClearCollect.getBillclearitem();
		Set<BillInCollect> billInCollects = billClearCollect.getBillincollect();
		// 得到之前合同的票信息
		List<ClearVoucherItemStruct> clearVoucherItemStructList = this.customerTitleJdbcDao.getCustomerBillInfo(supplier, contractNos, projectNos, bukrs);
		for (ClearVoucherItemStruct clearVoucherItemStruct : clearVoucherItemStructList)
		{
			VoucherItem voucherItem = getClearVoucherItem(clearVoucherItemStruct);
			voucherItem.setVoucher(clearVoucher);
			billValue = billValue.add(clearVoucherItemStruct.getBwbje());
			voucherItemList.add(voucherItem);
		}

		// 得到本次票的行项目信息
		Iterator<BillClearItem> billClearItemsIt = billClearItems.iterator();
		while (billClearItemsIt.hasNext())
		{
			BillClearItem billClearItem = billClearItemsIt.next();
			if (clearedContractNo.contains(billClearItem.getContract_no()))
			{
				log.debug("合同号:" + billClearItem.getContract_no() + "已经存在已清合同列表," + contractNos);
			}
			if (clearedProjectNo.contains(billClearItem.getProject_no()))
			{
				log.debug("合同号:" + billClearItem.getProject_no() + "已经存在已清合同列表," + projectNos);
			}

			if (!clearedContractNo.contains(billClearItem.getContract_no()) && !clearedProjectNo.contains(billClearItem.getProject_no()))
			{
//				ClearVoucherItemStruct clearVoucherItemStruct = this.customerTitleJdbcDao.getCurrectBillInfo(billClearItem.getInvoice());
//				VoucherItem voucherItem = getClearVoucherItem(clearVoucherItemStruct);
//				voucherItem.setVoucher(clearVoucher);
//				billValue = billValue.add(clearVoucherItemStruct.getBwbje());
//				voucherItemList.add(voucherItem);
				
				ClearVoucherItemStruct clearVoucherItemStruct = new ClearVoucherItemStruct();
				CustomerTitle customerTitle = this.customerTitleJdbcDao.getByCustomertitleid(billClearItem.getTitleid());				 
				clearVoucherItemStruct.setRowno(customerTitle.getBuzei());
				clearVoucherItemStruct.setVoucherno(billClearItem.getVoucherno());
				clearVoucherItemStruct.setYear(customerTitle.getGjahr());
				VoucherItem voucherItem = getClearVoucherItem(clearVoucherItemStruct);						
				billValue = billValue.add(customerTitle.getBwbje());	
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
		//取得SAP手工做账的被部分清的汇损凭证
		List<ClearVoucherItemStruct> clearVoucherItemStructList2 = this.customerTitleJdbcDao.getCustomerVoucherInfo(supplier, contractNos, projectNos, bukrs);
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
		List<ClearVoucherItemStruct> clearVoucherItemStructs2 = this.customerTitleJdbcDao.getCustomerPayItemInfo(supplier, contractNos, projectNos, bukrs);
		for (ClearVoucherItemStruct clearVoucherItemStruct : clearVoucherItemStructs2)
		{
			VoucherItem voucherItem = getClearVoucherItem(clearVoucherItemStruct);
			voucherItem.setVoucher(clearVoucher);
			itemValue = itemValue.add(clearVoucherItemStruct.getBwbje());
			voucherItemList.add(voucherItem);
		}
		//退款的行项目信息
		List<ClearVoucherItemStruct> clearVoucherItemStructList3 = this.customerTitleJdbcDao.getRefundItemByCustomerNo(supplier, BusinessState.ALL_BILLCLEAR_PAIDUP, projectNos,contractNos,billClearCollect.getDeptid());
		for (ClearVoucherItemStruct clearVoucherItemStruct : clearVoucherItemStructList3)
		{
			VoucherItem voucherItem = getClearVoucherItem(clearVoucherItemStruct);
			billValue = billValue.add(clearVoucherItemStruct.getBwbje());
			voucherItem.setVoucher(clearVoucher);
			voucherItemList.add(voucherItem);
		}

		// 得到本次分配项的行项目信息
		Iterator<BillInCollect> billInCollectsIt = billInCollects.iterator();
		while (billInCollectsIt.hasNext())
		{
			BillInCollect billInCollect = billInCollectsIt.next();
			if (clearedContractNo.contains(billInCollect.getContract_no()))
			{
				log.debug("合同号:" + billInCollect.getContract_no() + "已经存在已清合同列表," + contractNos);
			}
			if (clearedProjectNo.contains(billInCollect.getProject_no()))
			{
				log.debug("合同号:" + billInCollect.getProject_no() + "已经存在已清合同列表," + projectNos);
			}

			if (!clearedContractNo.contains(billInCollect.getContract_no()) && !clearedProjectNo.contains(billInCollect.getProject_no()))
			{
				Collect collect = this.collectJdbcDao.getCollectByCollectNo(billInCollect.getCollectno());
				Voucher oldVoucher = this.voucherService.getVoucherByBusinessId(collect.getCollectid(), "1");
				Set<VoucherItem> oldVoucherItem = oldVoucher.getVoucherItem();
				Iterator<VoucherItem> payVoucherItemit = oldVoucherItem.iterator();
				while (payVoucherItemit.hasNext())
				{
					VoucherItem oldItem = payVoucherItemit.next();
					if (oldItem.getVoucher().getBusinessid().equals(collect.getCollectid()) && oldItem.getBusinessitemid().equals(billInCollect.getCollectitemid()))
					{
						ClearVoucherItemStruct clearVoucherItemStruct = new ClearVoucherItemStruct();
						clearVoucherItemStruct.setRowno(oldItem.getRownumber());
						clearVoucherItemStruct.setVoucherno(billInCollect.getVoucherno());
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
				
				VoucherItem supplierVoucherItem = getProfitAndLossVoucherItem(billClearCollect, subtractVlaue, currency,"", "1");
				supplierVoucherItem.setVoucher(clearVoucher);
				voucherItemList.add(supplierVoucherItem);

				VoucherItem profOrLossVoucherItem = getProfitAndLossVoucherItem(billClearCollect, subtractVlaue, currency,"", "2");
				profOrLossVoucherItem.setVoucher(clearVoucher);
				voucherItemList.add(profOrLossVoucherItem);
			}
		}
		
	}
	
	/**
	 * 获取合同号列表
	 * 
	 * @param itemSet
	 * @return
	 */
	private List<String> getContractNoList(Set<BillClearItem> billClearItems)
	{
		String contractNo = null;
		List<String> contractNos = new ArrayList<String>();
		Iterator<BillClearItem> it = billClearItems.iterator();
		while (it.hasNext())
		{
			BillClearItem billClearItem = it.next();
			if (contractNo == null || !contractNo.equals(billClearItem.getContract_no()))
			{
				contractNos.add(billClearItem.getContract_no());
			}
			contractNo = billClearItem.getContract_no();
		}
		return contractNos;
	}

	/**
	 * 获取立项号列表
	 * 
	 * @param itemSet
	 * @return
	 */
	private List<String> getProjectNoList(Set<BillClearItem> billClearItems)
	{
		String projectNo = null;
		List<String> projectNos = new ArrayList<String>();
		Iterator<BillClearItem> it = billClearItems.iterator();
		while (it.hasNext())
		{
			BillClearItem billClearItem = it.next();
			if (projectNo == null || !projectNo.equals(billClearItem.getProject_no()))
			{
				projectNos.add(billClearItem.getProject_no());
			}
			projectNo = billClearItem.getProject_no();
		}
		return projectNos;
	}

	/**
	 * 判断票清收款下的款和票是否可请
	 * @param billclearid
	 * @return
	 */
	public void settleBillClearCollect(String billclearid){
		BillClearCollect billClearCollect = this._get(billclearid);
		
		//判断款
		Set billincollects = billClearCollect.getBillincollect();
		for (Iterator<BillInCollect> iter = billincollects.iterator(); iter.hasNext();)
		{
			BillInCollect billInCollect = (BillInCollect) iter.next();
			//自动判断是否需要结清
//			boolean isclear =this.collectItemService.autoSettleCollectItem(billInCollect.getCollectitemid());
			BigDecimal clearedCollectamount = this.collectItemService.getSumClearAmount(billInCollect.getCollectitemid(),  BusinessState.ALL_COLLECT_PAIDUP);
			//如果是已清，去更新未清表中款的状态
			if(billInCollect.getCollectamount().compareTo(clearedCollectamount.add(billInCollect.getNowclearamount()))==0){
				this.collectItemJdbcDao.updateCollectItemIsCleared(billInCollect.getCollectitemid(), Constants.cleared.isCleared);				
				CustomerTitle title=this.getCustomerTitle(billInCollect.getCollectitemid(),"1,4");
				if(null!=title){
					this.customerTitleJdbcDao.updateCustomerTitleIsCleared(title.getCustomertitleid(), Constants.cleared.isCleared);
				}
				
			}
			
		}
		
		//判断票
		Set billclearitems = billClearCollect.getBillclearitem();
		for (Iterator<BillClearItem> iter = billclearitems.iterator(); iter.hasNext();)
		{
			BillClearItem billClearItem = (BillClearItem) iter.next();
			
			String billno = billClearItem.getInvoice();
			
			CustomerTitle customerTitle = this.customerTitleJdbcDao.getByInvoice(billno.trim());
			//已审批完的金额
			BigDecimal clearedAmount = this.customerTitleJdbcDao.getSumClearAmount(billno.trim(), BusinessState.ALL_COLLECT_PAIDUP);
			//发票金额
			BigDecimal billAmount = customerTitle.getDmbtr();
			if((clearedAmount.compareTo(billAmount)==0)
					||((clearedAmount.add(billClearItem.getClearbillamount())).compareTo(billAmount)==0))
			{
				this.customerTitleJdbcDao.updateCustomerTitleIsClearedByInvoice(billno, "1");
			}
		}
			
	}
	/**
	 * 通过colleccttitemid找到未清表的款
	 * @param collectitemid
	 * @param voucherclass
	 * @return
	 */
	public CustomerTitle getCustomerTitle(String collectitemid,String voucherclass){
		VoucherItem voucheritem = this.voucherItemJdbcDao.getVoucherItem(collectitemid, voucherclass);
		String rownumber = StringUtils.leftPad(voucheritem.getRownumber(), 3, '0');		
		CustomerTitle title = customerTitleJdbcDao.getCustomerTitle(voucheritem.getVoucher().getCompanycode(), voucheritem.getVoucher().getVoucherno(), voucheritem.getVoucher().getFiyear(), rownumber);
		return title;
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
	 * @param supplierSinglClear
	 * @param subtractVlaue
	 * @param currency
	 * @param strType
	 * @return
	 */
	public VoucherItem getProfitAndLossVoucherItem(BillClearCollect billClearCollect, BigDecimal subtractVlaue, String currency,String taxCode, String strType)
	{
		VoucherItem voucherItem = new VoucherItem();
		String bukrs = this.voucherService.getCompanyIDByDeptID(billClearCollect.getDeptid());
		String subject = this.customerRefundItemJdbcDao.getSubjectByCustomer(billClearCollect.getCustomer(),bukrs);
		String subjectname = this.customerRefundItemJdbcDao.getSubjectNameById(subject,bukrs);
		voucherItem.setControlaccount(subject);
		voucherItem.setCaremark(subjectname);
		voucherItem.setTaxcode(taxCode);
		if (strType.equals("1"))
		{
			voucherItem.setRownumber("1");
			// 科目
			voucherItem.setSubject(billClearCollect.getCustomer());
			// 科目说明
			String Subjectdescribe =  this.customerJdbcDao.getCustomerName(billClearCollect.getCustomer(), bukrs);
//			String Subjectdescribe = this.voucherService.getSupplierDescByCustomerId(billClearCollect.getCustomer(), bukrs);
			voucherItem.setSubjectdescribe(Subjectdescribe);

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
		voucherItem.setDepid(this.sysDeptJdbcDao.getDeptCodeById(billClearCollect.getDeptid()));
		// 文本
		voucherItem.setText(billClearCollect.getText());

		return voucherItem;
	}
	
	/**
	 * 根据退款ID获得票清付款业务对象
	 * @param billClearId
	 * @return
	 */
	public BillClearCollect getBillClearCollectById(String billClearId )
	{
		return this.billClearCollectHibernateDao.get(billClearId);
	}
	
	/**
	 * 更新所有的包括旧的title,和收付款，的 iscleared falg  true 为sap清帐,
	 * @param 
	 * @param 
	 */
	public void updateOldTitle(BillClearCollect billClearCollect,boolean flag){
		String businessid = billClearCollect.getBillclearid();
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
				if(flag){
					this.customerTitleJdbcDao.updateCustomerTitleIsCleared(customerTitle.getCustomertitleid(), Constants.cleared.sapIsCleared);
				}else{
					this.customerTitleJdbcDao.updateCustomerTitleIsCleared(customerTitle.getCustomertitleid(), Constants.cleared.isCleared);
				}
			}
			String businessItemId = this.voucherItemJdbcDao.getBusinessId(voucherNo, companyCode, fiYear, rowNumber);
			// 收款信息
			CollectItem collectItem = this.collectItemService.get(businessItemId);
			if(null != collectItem){
				if(flag){
					this.collectItemService.updateCollectItemIsCleared(collectItem.getCollectitemid(), Constants.cleared.sapIsCleared);
				}else{
					this.collectItemService.updateCollectItemIsCleared(collectItem.getCollectitemid(), Constants.cleared.isCleared);
				}
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
	
}