/*
 * @(#)HomePaymentCbillService.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年07月19日 10点44分14秒
 *  描　述：创建
 */
package com.infolion.xdss3.payment.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.math.BigDecimal;
import com.infolion.platform.console.sys.context.UserContextHolder;
import com.infolion.platform.dpframework.util.StringUtils;
import com.infolion.platform.dpframework.core.BusinessException;
import com.infolion.platform.console.org.dao.SysDeptJdbcDao;
import com.infolion.platform.console.sys.context.UserContext;
import com.infolion.xdss3.BusinessState;
import com.infolion.xdss3.bankInfo.dao.BankInfoJdbcDao;
import com.infolion.xdss3.collect.dao.CollectJdbcDao;
import com.infolion.xdss3.collect.domain.Collect;
import com.infolion.xdss3.collectcbill.domain.CollectCbill;
import com.infolion.xdss3.customerDrawback.dao.CustomerRefundItemJdbcDao;
import com.infolion.xdss3.masterData.dao.VendorTitleHibernateDao;
import com.infolion.xdss3.masterData.dao.VendorTitleJdbcDao;
import com.infolion.xdss3.masterData.domain.CustomerTitle;
import com.infolion.xdss3.masterData.domain.VendorTitle;
import com.infolion.xdss3.payment.domain.ClearVoucherItemStruct;
import com.infolion.xdss3.payment.domain.HomeFundFlow;
import com.infolion.xdss3.payment.domain.HomePayBankItem;
import com.infolion.xdss3.payment.domain.HomePayment;
import com.infolion.xdss3.payment.domain.HomePaymentCbill;
import com.infolion.xdss3.payment.domain.HomePaymentItem;
import com.infolion.xdss3.payment.domain.HomeSettSubj;
import com.infolion.xdss3.payment.service.HomePaymentCbillService;
import com.infolion.xdss3.payment.dao.PayMentCBillJdbcDao;
import com.infolion.xdss3.paymentGen.service.HomePaymentCbillServiceGen;
import com.infolion.xdss3.voucher.dao.VoucherHibernateDao;
import com.infolion.xdss3.voucher.dao.VoucherJdbcDao;
import com.infolion.xdss3.voucher.domain.Voucher;
import com.infolion.xdss3.voucher.service.VoucherService;
import com.infolion.xdss3.voucheritem.dao.VoucherItemJdbcDao;
import com.infolion.xdss3.voucheritem.domain.VoucherItem;

/**
 * <pre>
 * 内贸付款清票(HomePaymentCbill)服务类
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
public class HomePaymentCbillService extends HomePaymentCbillServiceGen
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
	 * 得到背书的客户清帐信息
	 * @param homePayment
	 * @param strClass
	 * @param payVoucher
	 * @param strIsSHow
	 * @return
	 */
	public Voucher getRepeatLessonClearVoucher(HomePayment homePayment,
			String strClass,
			Voucher payVoucher,
			String strIsSHow){
		Voucher voucher = new Voucher();
		//凭证抬头
		voucher = getClearVoucher(homePayment,strClass);
		//凭证行项目
		Set<VoucherItem> voucherItemList = new HashSet<VoucherItem>();
		//得到收款单编号
		String strCollectId = this.homePayMentCBillJdbcDao.getCollectIdBydraft(homePayment.getDraft());
		//得到客户的编号
		String strCustomer = this.homePayMentCBillJdbcDao.getCustomerBydraft(homePayment.getDraft());
		
		voucher.setAgkon(strCustomer);
		voucher.setAgkoa("D");
		voucher.setAgums("W");
		
		List<ClearVoucherItemStruct> clearVoucherItemStructList = 
			this.vendorTitleJdbcDao.getCollectRepeatLessonInfo(strCollectId, strCustomer, "09", "W");
		
		for (int j = 0;j<clearVoucherItemStructList.size();j++){
			ClearVoucherItemStruct clearVoucherItemStruct = 
				(ClearVoucherItemStruct) clearVoucherItemStructList.get(j);
			
			VoucherItem voucherItem = getClearVoucherItem(clearVoucherItemStruct.getVoucherno(),
					clearVoucherItemStruct.getRowno(),
					clearVoucherItemStruct.getYear()," ");
			
			voucherItem.setVoucher(voucher);
			voucherItemList.add(voucherItem);
		}
		
		Set<VoucherItem> payVoucherItem = payVoucher.getVoucherItem();
		Iterator<VoucherItem> payVoucherItemit = payVoucherItem.iterator();
		while(payVoucherItemit.hasNext()){
			VoucherItem payItem = payVoucherItemit.next();
			
			if (payItem.getCheckcode().equals("19") && 
				payItem.getGlflag().equals("W")){
					
				VoucherItem voucherItem = getClearVoucherItem(payVoucher.getVoucherno(),
						payItem.getRownumber(),
						payItem.getVoucher().getFiyear(),payItem.getVoucher().getVoucherid());
				//如果是背书清账，凭证的抬头的业务范围是清的票据的业务范围
				voucher.setGsber(payItem.getGsber());
				voucherItem.setVoucher(voucher);
				voucherItemList.add(voucherItem);
			}
		}
		
		voucher.setVoucherItem(voucherItemList);
		this.voucherHibernateDao.save(voucher);
		return voucher;
	}
	
	/**
	 * 得到应付票据的清帐凭证
	 * @param homePayment
	 * @param strClass
	 * @param payVoucher
	 * @param strIsSHow
	 * @return
	 */
	public Voucher getBillClearVoucher(HomePayment homePayment,
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
	 * @param importPayment
	 * @param strClass
	 * @return
	 */
	public Voucher getClearVoucher(HomePayment homePayment,String strClass){
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
		Iterator<HomePaymentCbill> homePaymentCbillIterator =  homePayment.getHomePaymentCbill().iterator();
		if(homePaymentCbillIterator.hasNext()){
			HomePaymentCbill homePaymentCbill = homePaymentCbillIterator.next();
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
		Iterator<HomePayBankItem> bankItemit = homePayment.getHomePayBankItem().iterator();
		if(bankItemit.hasNext()){
			HomePayBankItem importPayBankItem = bankItemit.next();
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
	
	/**
	 * 判断是否需要产生清帐凭证
	 * @param importPayment
	 * @return
	 */
	public boolean checkIsClear(HomePayment homePayment){
		boolean bFlag = false;
		String strMiddleContract = "";
		String strMiddleProject = "";
		
		
		String strCurrentFlag = this.homePayMentCBillJdbcDao.checkPayCurrentClear(
				homePayment.getPaymentid());
		String strContractFlag = this.homePayMentCBillJdbcDao.checkPayContractClear(
				homePayment.getPaymentid());
		
		if (strContractFlag == null || "".equals(strContractFlag)){
			strMiddleContract = "'无'";
		}else{
			strMiddleContract = strContractFlag;
		}
		
		String strProjectFlag = this.homePayMentCBillJdbcDao.checkPayProjectClear(
				homePayment.getPaymentid(),strMiddleContract);
		
		
		if (strProjectFlag == null || "".equals(strProjectFlag)){
			strMiddleProject = "'无'";
		}else{
			strMiddleProject = strProjectFlag;
		}
		
		String strSupplierFlag = this.homePayMentCBillJdbcDao.checkPaySupplierClear(
				homePayment.getPaymentid(),strMiddleContract,strMiddleProject);
		
		if (strCurrentFlag != null && strCurrentFlag.equals("Y")){
			return true;
		}; 
		
		if (strContractFlag != null && !"".equals(strContractFlag)){
			return true;
		}
		
		if (strProjectFlag != null && !"".equals(strProjectFlag)){
			return true;
		}
		
		if (strSupplierFlag != null && strSupplierFlag.equals("Y")){
			return true;
		}
		
		return bFlag;
	}
	
	/**
	 * 处理清帐凭证
	 * @param importPayment   原来的付款实例类
	 * @param strClass        付款时的环节类型
	 * @param strIsSHow       是否汇损行项目
	 * @return
	 */
	public void dealClearAccountVoucher(HomePayment homePayment,Voucher payVoucher,String strClass){
		//*************处理清帐凭证*****************************
		if (checkIsClear(homePayment) == true){
			//this.voucherJdbcDao.deleteVoucherByBusinessidAndClass(homePayment.getPaymentid(), "9");
			
			//判断是否已经生成清账凭证，	如果有，不再生成	
			List<Voucher> li=voucherHibernateDao.getVoucherByBusinessId2(homePayment.getPaymentid(),"A");
			if(li!=null && li.size()!=0){				
				for(Voucher vo : li){
					if(!StringUtils.isNullBlank(vo.getVoucherno())){
						return;
					}
				} 
			}
			
			//凭证抬头
			Voucher clearVoucher = new Voucher();

			String strMiddleContract = "";
			String strMiddleProject = "";

			String strCurrentFlag = this.homePayMentCBillJdbcDao.checkPayCurrentClear(
					homePayment.getPaymentid());
			String strContractFlag = this.homePayMentCBillJdbcDao.checkPayContractClear(
					homePayment.getPaymentid());
			
			if (strContractFlag == null || "".equals(strContractFlag)){
				strMiddleContract = "'无'";
			}else{
				strMiddleContract = strContractFlag;
			}
			
			String strProjectFlag = this.homePayMentCBillJdbcDao.checkPayProjectClear(
					homePayment.getPaymentid(),strMiddleContract);
			
			
			if (strProjectFlag == null || "".equals(strProjectFlag)){
				strMiddleProject = "'无'";
			}else{
				strMiddleProject = strProjectFlag;
			}
			
			String strSupplierFlag = this.homePayMentCBillJdbcDao.checkPaySupplierClear(
					homePayment.getPaymentid(),strMiddleContract,strMiddleProject);
			
			Voucher voucher = new Voucher();
			//凭证抬头
			voucher = getClearVoucher(homePayment,strClass);
			//凭证行项目
			Set<VoucherItem> voucherItemList = new HashSet<VoucherItem>();			
			
			if (strCurrentFlag != null && strCurrentFlag.equals("Y")){
				//得到票的行项目信息
				Iterator<HomePaymentCbill> homePaymentCbillIterator =  homePayment.getHomePaymentCbill().iterator();
				while(homePaymentCbillIterator.hasNext()){
					HomePaymentCbill homePaymentCbill = homePaymentCbillIterator.next();
					ClearVoucherItemStruct clearVoucherItemStruct = this.vendorTitleJdbcDao.getCurrectBillInfo(homePaymentCbill.getBillno());
					
					VoucherItem voucherItem = getClearVoucherItem(clearVoucherItemStruct.getVoucherno(),
							clearVoucherItemStruct.getRowno(),
							clearVoucherItemStruct.getYear()," ");

					voucherItem.setVoucher(voucher);
					voucherItemList.add(voucherItem);
				}
				
				//得到分配项的行项目信息
				Iterator<HomePaymentItem> PayItemItemit = homePayment.getHomePaymentItem().iterator();		
				while(PayItemItemit.hasNext()){
					HomePaymentItem homePaymentItem = PayItemItemit.next();
					
					Set<VoucherItem> payVoucherItem = payVoucher.getVoucherItem();
					Iterator<VoucherItem> payVoucherItemit = payVoucherItem.iterator();
					while(payVoucherItemit.hasNext()){
						VoucherItem payItem = payVoucherItemit.next();
						
						if (payItem.getBusinessitemid() != null &&	
							!"".equals(payItem.getBusinessitemid().trim()) && 
							homePaymentItem.getPaymentitemid() != null &&
							!"".equals(homePaymentItem.getPaymentitemid().trim()) && 
							payItem.getVoucher().getBusinessid().equals(homePaymentItem.getHomePayment().getPaymentid()) &&
							payItem.getBusinessitemid().equals(homePaymentItem.getPaymentitemid())){
								
							VoucherItem voucherItem = getClearVoucherItem("",
									payItem.getRownumber(),
									payItem.getVoucher().getFiyear(),
									payItem.getVoucher().getVoucherid());
							
							voucherItem.setVoucherno(payItem.getVoucher().getVoucherno());
							voucherItem.setBusinessitemid(payItem.getBusinessitemid());
							voucherItem.setVoucher(voucher);
							voucherItemList.add(voucherItem);
						}
					}
				}
				
				voucher.setVoucherItem(voucherItemList);
			}else{
				//合同层的清帐行项目信息
				if (strContractFlag != null && !"".equals(strContractFlag)){
					String[] strContractList = strContractFlag.split(",");
					
					for (int i = 0; i < strContractList.length; i++) {
						
						//得到之前合同的票信息
						List<ClearVoucherItemStruct> clearVoucherItemStructList = 
							this.vendorTitleJdbcDao.getContractBillInfo(homePayment.getSupplier(),strContractList[i].substring(1, strContractList[i].length()-1));
						
						for (int j = 0;j<clearVoucherItemStructList.size();j++){
							ClearVoucherItemStruct clearVoucherItemStruct = 
								(ClearVoucherItemStruct) clearVoucherItemStructList.get(j);
							
							VoucherItem voucherItem = getClearVoucherItem(clearVoucherItemStruct.getVoucherno(),
									clearVoucherItemStruct.getRowno(),
									clearVoucherItemStruct.getYear()," ");
							
							voucherItem.setVoucher(voucher);
							voucherItemList.add(voucherItem);
						}
						
						//得到本次票的行项目信息
						Iterator<HomePaymentCbill> homePaymentCbillIterator =  homePayment.getHomePaymentCbill().iterator();
						while(homePaymentCbillIterator.hasNext()){
							HomePaymentCbill homePaymentCbill = homePaymentCbillIterator.next();

							if (homePaymentCbill.getContract_no() != null && 
								strContractList[i].indexOf(homePaymentCbill.getContract_no()) != -1){
								ClearVoucherItemStruct clearVoucherItemStruct = this.vendorTitleJdbcDao.getCurrectBillInfo(homePaymentCbill.getBillno());
								
								VoucherItem voucherItem = getClearVoucherItem(clearVoucherItemStruct.getVoucherno(),
										clearVoucherItemStruct.getRowno(),
										clearVoucherItemStruct.getYear()," ");
								
								voucherItem.setVoucher(voucher);
								voucherItemList.add(voucherItem);
							}
						}
						
						
						//得到之前的款分配信息
						List<ClearVoucherItemStruct> clearVoucherItemStructList2 = 
							this.vendorTitleJdbcDao.getContractPayItemInfo(strContractList[i].substring(1, strContractList[i].length()-1));
						
						for (int j = 0;j<clearVoucherItemStructList2.size();j++){
							ClearVoucherItemStruct clearVoucherItemStruct = 
								(ClearVoucherItemStruct) clearVoucherItemStructList2.get(j);
							
							VoucherItem voucherItem = getClearVoucherItem(clearVoucherItemStruct.getVoucherno(),
									clearVoucherItemStruct.getRowno(),
									clearVoucherItemStruct.getYear()," ");

							voucherItem.setBusinessitemid(clearVoucherItemStruct.getBusinessitemid());
							voucherItem.setVoucher(voucher);
							voucherItemList.add(voucherItem);
						}
						
						//得到本次分配项的行项目信息
						Iterator<HomePaymentItem> PayItemItemit = homePayment.getHomePaymentItem() .iterator();		
						while(PayItemItemit.hasNext()){
							HomePaymentItem homePaymentItem = PayItemItemit.next();

							if (homePaymentItem.getContract_no() != null && 
								strContractList[i].indexOf(homePaymentItem.getContract_no()) != -1){
								Set<VoucherItem> payVoucherItem = payVoucher.getVoucherItem();
								Iterator<VoucherItem> payVoucherItemit = payVoucherItem.iterator();
								while(payVoucherItemit.hasNext()){
									VoucherItem payItem = payVoucherItemit.next();
									
									if (payItem.getBusinessitemid() != null &&	
										!"".equals(payItem.getBusinessitemid().trim()) && 
										homePaymentItem.getPaymentitemid() != null &&
										!"".equals(homePaymentItem.getPaymentitemid().trim()) && 
										payItem.getVoucher().getBusinessid().equals(homePaymentItem.getHomePayment().getPaymentid()) &&
										payItem.getBusinessitemid().equals(homePaymentItem.getPaymentitemid())){
											
										VoucherItem voucherItem = getClearVoucherItem("",
												payItem.getRownumber(),
												payItem.getVoucher().getFiyear(),
												payItem.getVoucher().getVoucherid());
										
										voucherItem.setVoucherno(payItem.getVoucher().getVoucherno());
										voucherItem.setBusinessitemid(payItem.getBusinessitemid());
										voucherItem.setVoucher(voucher);
										voucherItemList.add(voucherItem);
									}
								}
							}
						}
					}
				}
				
				//立项层的清帐行项目信息
				if (strProjectFlag != null && !"".equals(strProjectFlag)){
					String[] strProjectList = strProjectFlag.split(",");
					
					for (int i = 0; i < strProjectList.length; i++) {
						//得到之前合同的票信息
						List<ClearVoucherItemStruct> clearVoucherItemStructList = 
							this.vendorTitleJdbcDao.getProjectBillInfo(homePayment.getSupplier(),strProjectList[i].substring(1, strProjectList[i].length()-1), strContractFlag);
						
						for (int j = 0;j<clearVoucherItemStructList.size();j++){
							ClearVoucherItemStruct clearVoucherItemStruct = 
								(ClearVoucherItemStruct) clearVoucherItemStructList.get(j);
							
							VoucherItem voucherItem = getClearVoucherItem(clearVoucherItemStruct.getVoucherno(),
									clearVoucherItemStruct.getRowno(),
									clearVoucherItemStruct.getYear()," ");

							voucherItem.setVoucher(voucher);
							voucherItemList.add(voucherItem);
						}
						
						//得到本次票的行项目信息
						Iterator<HomePaymentCbill> homePaymentCbillIterator =  homePayment.getHomePaymentCbill().iterator();
						while(homePaymentCbillIterator.hasNext()){
							HomePaymentCbill homePaymentCbill = homePaymentCbillIterator.next();

							if (homePaymentCbill.getProject_no() != null &&
								homePaymentCbill.getContract_no() != null && 
								strProjectList[i].indexOf(homePaymentCbill.getProject_no())!= -1 &&
								strContractFlag.indexOf(homePaymentCbill.getContract_no()) == -1){
								ClearVoucherItemStruct clearVoucherItemStruct = this.vendorTitleJdbcDao.getCurrectBillInfo(homePaymentCbill.getBillno());
								
								VoucherItem voucherItem = getClearVoucherItem(clearVoucherItemStruct.getVoucherno(),
										clearVoucherItemStruct.getRowno(),
										clearVoucherItemStruct.getYear()," ");
								
								
								voucherItem.setVoucher(voucher);
								voucherItemList.add(voucherItem);
							}
						}
						
						
						//得到之前的款分配信息
						List<ClearVoucherItemStruct> clearVoucherItemStructList2 = 
							this.vendorTitleJdbcDao.getProjectPayItemInfo(homePayment.getSupplier(),strProjectList[i].substring(1, strProjectList[i].length()-1), strContractFlag);
						
						for (int j = 0;j<clearVoucherItemStructList2.size();j++){
							ClearVoucherItemStruct clearVoucherItemStruct = 
								(ClearVoucherItemStruct) clearVoucherItemStructList2.get(j);
							
							VoucherItem voucherItem = getClearVoucherItem(clearVoucherItemStruct.getVoucherno(),
									clearVoucherItemStruct.getRowno(),
									clearVoucherItemStruct.getYear()," ");
							
							voucherItem.setBusinessitemid(clearVoucherItemStruct.getBusinessitemid());
							voucherItem.setVoucher(voucher);
							voucherItemList.add(voucherItem);
						}
						
						//得到本次分配项的行项目信息
						Iterator<HomePaymentItem> PayItemItemit = homePayment.getHomePaymentItem() .iterator();		
						while(PayItemItemit.hasNext()){
							HomePaymentItem homePaymentItem = PayItemItemit.next();

							if (homePaymentItem.getProject_no() != null &&
								homePaymentItem.getContract_no() != null && 
								strProjectList[i].indexOf(homePaymentItem.getProject_no())!= -1 &&
								strContractFlag.indexOf(homePaymentItem.getContract_no()) == -1){
								Set<VoucherItem> payVoucherItem = payVoucher.getVoucherItem();
								Iterator<VoucherItem> payVoucherItemit = payVoucherItem.iterator();
								while(payVoucherItemit.hasNext()){
									VoucherItem payItem = payVoucherItemit.next();
									
									if (payItem.getBusinessitemid() != null &&	
										!"".equals(payItem.getBusinessitemid().trim()) && 
										homePaymentItem.getPaymentitemid() != null &&
										!"".equals(homePaymentItem.getPaymentitemid().trim()) && 
										payItem.getVoucher().getBusinessid().equals(homePaymentItem.getHomePayment().getPaymentid()) &&
										payItem.getBusinessitemid().equals(homePaymentItem.getPaymentitemid())){
											
										VoucherItem voucherItem = getClearVoucherItem("",
												payItem.getRownumber(),
												payItem.getVoucher().getFiyear(),
												payItem.getVoucher().getVoucherid());

										voucherItem.setVoucherno(payItem.getVoucher().getVoucherno());
										voucherItem.setBusinessitemid(payItem.getBusinessitemid());
										voucherItem.setVoucher(voucher);
										voucherItemList.add(voucherItem);
									}
								}
							}
						}
					}
				}
				
				//供应商层的清帐行项目信息
				if (strSupplierFlag != null && "Y".equals(strSupplierFlag)){
					String bukrs = getCompanyIDByDeptID(homePayment.getDept_id());
					//得到之前合同的票信息
					List<ClearVoucherItemStruct> clearVoucherItemStructList = 
						this.vendorTitleJdbcDao.getSupplierBillInfo(homePayment.getSupplier(), 
								strContractFlag, 
								strProjectFlag,
								bukrs);
					
					for (int j = 0;j<clearVoucherItemStructList.size();j++){
						ClearVoucherItemStruct clearVoucherItemStruct = 
							(ClearVoucherItemStruct) clearVoucherItemStructList.get(j);
						
						VoucherItem voucherItem = getClearVoucherItem(clearVoucherItemStruct.getVoucherno(),
								clearVoucherItemStruct.getRowno(),
								clearVoucherItemStruct.getYear()," ");

						voucherItem.setVoucher(voucher);
						voucherItemList.add(voucherItem);
						
					}
					
					//得到本次票的行项目信息
					Iterator<HomePaymentCbill> homePaymentCbillIterator =  homePayment.getHomePaymentCbill().iterator();
					while(homePaymentCbillIterator.hasNext()){
						HomePaymentCbill homePaymentCbill = homePaymentCbillIterator.next();
						
						if (homePaymentCbill.getContract_no() != null && 
							homePaymentCbill.getProject_no() != null && 
							strProjectFlag.indexOf(homePaymentCbill.getProject_no()) == -1 &&
							strContractFlag.indexOf(homePaymentCbill.getContract_no()) == -1){
							ClearVoucherItemStruct clearVoucherItemStruct = this.vendorTitleJdbcDao.getCurrectBillInfo(homePaymentCbill.getBillno());
							
							VoucherItem voucherItem = getClearVoucherItem(clearVoucherItemStruct.getVoucherno(),
									clearVoucherItemStruct.getRowno(),
									clearVoucherItemStruct.getYear()," ");
							
							
							voucherItem.setVoucher(voucher);
							voucherItemList.add(voucherItem);
						}
					}
					
					//得到之前的款分配信息
					List<ClearVoucherItemStruct> clearVoucherItemStructList2 = 
						this.vendorTitleJdbcDao.getSupplierPayItemInfo(homePayment.getSupplier(), 
								strContractFlag, 
								strProjectFlag,
								bukrs);
					
					for (int j = 0;j<clearVoucherItemStructList2.size();j++){
						ClearVoucherItemStruct clearVoucherItemStruct = 
							(ClearVoucherItemStruct) clearVoucherItemStructList2.get(j);
						
						VoucherItem voucherItem = getClearVoucherItem(clearVoucherItemStruct.getVoucherno(),
								clearVoucherItemStruct.getRowno(),
								clearVoucherItemStruct.getYear()," ");
						
						voucherItem.setBusinessitemid(clearVoucherItemStruct.getBusinessitemid());
						voucherItem.setVoucher(voucher);
						voucherItemList.add(voucherItem);
					}
					
					//得到本次分配项的行项目信息
					Iterator<HomePaymentItem> PayItemItemit = homePayment.getHomePaymentItem() .iterator();		
					while(PayItemItemit.hasNext()){
						HomePaymentItem homePaymentItem = PayItemItemit.next();

						if (homePaymentItem.getProject_no() != null &&
							homePaymentItem.getContract_no() != null && 
							strProjectFlag.indexOf(homePaymentItem.getProject_no()) == -1 &&
							strContractFlag.indexOf(homePaymentItem.getContract_no()) == -1){
							Set<VoucherItem> payVoucherItem = payVoucher.getVoucherItem();
							Iterator<VoucherItem> payVoucherItemit = payVoucherItem.iterator();
							while(payVoucherItemit.hasNext()){
								VoucherItem payItem = payVoucherItemit.next();
								
								if (payItem.getBusinessitemid() != null &&	
									!"".equals(payItem.getBusinessitemid().trim()) && 
									homePaymentItem.getPaymentitemid() != null &&
									!"".equals(homePaymentItem.getPaymentitemid().trim()) && 
									payItem.getVoucher().getBusinessid().equals(homePaymentItem.getHomePayment().getPaymentid()) &&
									payItem.getBusinessitemid().equals(homePaymentItem.getPaymentitemid())){
										
									VoucherItem voucherItem = getClearVoucherItem("",
											payItem.getRownumber(),
											payItem.getVoucher().getFiyear(),
											payItem.getVoucher().getVoucherid());

									voucherItem.setVoucherno(payItem.getVoucher().getVoucherno());
									voucherItem.setBusinessitemid(payItem.getBusinessitemid());
									voucherItem.setVoucher(voucher);
									voucherItemList.add(voucherItem);
								}
							}
						}
					}
				}

				voucher.setVoucherItem(voucherItemList);		
			}
			
			this.voucherHibernateDao.save(voucher);
		}
	}
	
	/**
	 * 取的合同下未清的发票
	 * @param strContractList
	 * @return
	 */
	public List<VendorTitle> queryVendorByContract(String strContractList){
		List<VendorTitle> VendorTitleList = new ArrayList<VendorTitle>();
		
		String strMiddleContractList = strContractList.substring(0, strContractList.length()-1);
		strMiddleContractList = "'" + strMiddleContractList.replace("|", "','") + "'";
		
		String strHql = "from VendorTitle a where a.verkf in ("+strMiddleContractList+") and a.iscleared = '0' order by a.budat";
		
		VendorTitleList = this.vendorTitleHibernateDao.find(strHql);
		
		return VendorTitleList;
	}
	
	/**
	 * 取的立项下未清的发票
	 * @param strProjectList
	 * @return
	 */
	public List<VendorTitle> queryVendorByProject(String strProjectList, String lifnr){
		List<VendorTitle> VendorTitleList = new ArrayList<VendorTitle>();
		
		String strMiddleProjectList = strProjectList.substring(0, strProjectList.length()-1);
		strMiddleProjectList = "'" + strMiddleProjectList.replace("|", "','") + "'";
		
		String strHql = "from VendorTitle a where a.lifnr = '" + lifnr + "' and a.lixiang in ("+strMiddleProjectList+") and a.iscleared = '0' order by a.budat";
		
		VendorTitleList = this.vendorTitleHibernateDao.find(strHql);
		
		return VendorTitleList;
	}
	
	/**
	 * 把取出来的发票整理到发票清帐表中
	 * @param cntractVendorTitleList
	 * @param projectVendorTitleList
	 * @return
	 */
	public List<HomePaymentCbill> dealPaymentCbillList(List<VendorTitle> cntractVendorTitleList,List<VendorTitle> projectVendorTitleList){
		List<HomePaymentCbill> homePaymentCbillList = new ArrayList<HomePaymentCbill>();
		List<HomePaymentCbill> projectPaymentCbillList = new ArrayList<HomePaymentCbill>();
		
		//把合同取出的发票放到LIST中
		for (int i=0;i<cntractVendorTitleList.size();i++){
			HomePaymentCbill homePaymentCbill = new HomePaymentCbill();
			VendorTitle vendorTitle = (VendorTitle)cntractVendorTitleList.get(i);
			
			homePaymentCbill.setBillid(vendorTitle.getVendortitleid());
			homePaymentCbill.setBillno(vendorTitle.getInvoice());
			homePaymentCbill.setProject_no(vendorTitle.getLixiang());
			homePaymentCbill.setContract_no(vendorTitle.getVerkf());
			homePaymentCbill.setVoucherno(vendorTitle.getBelnr());
			String strPageContract = this.homePayMentCBillJdbcDao.getPruchasePageContract(homePaymentCbill.getContract_no());
			homePaymentCbill.setOld_contract_no(strPageContract);
			homePaymentCbill.setSap_order_no(vendorTitle.getEbeln());
			homePaymentCbill.setCurrency(vendorTitle.getWaers());
			//应付款金额
			homePaymentCbill.setPayableamount(vendorTitle.getDmbtr());
			//应付款日期
			homePaymentCbill.setPayabledate(vendorTitle.getYfkr());
			//已付金额
			//BigDecimal Paidamount = this.importPayMentCBillJdbcDao.getBillPaidamount(importPaymentCbill.getBillid());
			BigDecimal Paidamount = this.vendorTitleJdbcDao.getSumClearAmount(homePaymentCbill.getBillno(), BusinessState.ALL_BILLCLEAR_PAIDUP, BusinessState.ALL_SINGLECLEAR_PAIDUP, BusinessState.ALL_PAYMENT_PAIDUP);
			homePaymentCbill.setPaidamount(Paidamount);
			//未付金额
			BigDecimal Unpaidamount = homePaymentCbill.getPayableamount().subtract(Paidamount);
			homePaymentCbill.setUnpaidamount(Unpaidamount);
			//在途金额
			//BigDecimal Onroadamount = this.importPayMentCBillJdbcDao.getOnroadamount(importPaymentCbill.getBillid());
			BigDecimal Onroadamount = this.vendorTitleJdbcDao.getSumClearAmount(homePaymentCbill.getBillno(), BusinessState.ALL_BILLCLEAR_ONROAD, BusinessState.ALL_SINGLECLEAR_ONROAD, BusinessState.ALL_PAYMENT_ONROAD);
			homePaymentCbill.setOnroadamount(Onroadamount);
			//清帐金额
			homePaymentCbill.setClearamount2(new BigDecimal("0.0"));
			homePaymentCbill.setAccountdate(vendorTitle.getBudat());
			homePaymentCbill.setText(vendorTitle.getBktxt());

			// "未付金额"必须大于"在途金额"，否则不能添加
			if(Unpaidamount.compareTo(Onroadamount) == 1){
				homePaymentCbillList.add(homePaymentCbill);
			}
		}
		
		//处理立项时的发票因为要判断发票号是否存在
		for (int j=0;j<projectVendorTitleList.size();j++){
			VendorTitle vendorTitle = (VendorTitle)projectVendorTitleList.get(j);
			boolean isExists = false;
			
			for (int k=0;k<homePaymentCbillList.size();k++){
				HomePaymentCbill middlePaymentCbill = new HomePaymentCbill();
				middlePaymentCbill = (HomePaymentCbill)homePaymentCbillList.get(k);
				
				if (middlePaymentCbill.getBillno() == vendorTitle.getInvoice()){
					isExists = true;
					break;
				}
			}
			
			if (isExists == false){
				HomePaymentCbill homePaymentCbill = new HomePaymentCbill();
				
				homePaymentCbill.setBillid(vendorTitle.getVendortitleid());
				homePaymentCbill.setBillno(vendorTitle.getInvoice());
				homePaymentCbill.setProject_no(vendorTitle.getLixiang());
				homePaymentCbill.setContract_no(vendorTitle.getVerkf());
				homePaymentCbill.setVoucherno(vendorTitle.getBelnr());
				homePaymentCbill.setOld_contract_no("");
				homePaymentCbill.setSap_order_no(vendorTitle.getEbeln());
				homePaymentCbill.setCurrency(vendorTitle.getWaers());
				//应付款金额
				homePaymentCbill.setPayableamount(vendorTitle.getDmbtr());
				//应付款日期
				homePaymentCbill.setPayabledate(vendorTitle.getYfkr());
				//已付金额
				//BigDecimal Paidamount = this.importPayMentCBillJdbcDao.getBillPaidamount(importPaymentCbill.getBillid());
				BigDecimal Paidamount = this.vendorTitleJdbcDao.getSumClearAmount(homePaymentCbill.getBillno(), BusinessState.ALL_BILLCLEAR_PAIDUP, BusinessState.ALL_SINGLECLEAR_PAIDUP, BusinessState.ALL_PAYMENT_PAIDUP);
				homePaymentCbill.setPaidamount(Paidamount);
				//未付金额
				BigDecimal Unpaidamount = homePaymentCbill.getPayableamount().subtract(Paidamount);
				homePaymentCbill.setUnpaidamount(Unpaidamount);
				//在途金额
				//BigDecimal Onroadamount = this.importPayMentCBillJdbcDao.getOnroadamount(importPaymentCbill.getBillid());
				BigDecimal Onroadamount = this.vendorTitleJdbcDao.getSumClearAmount(homePaymentCbill.getBillno(), BusinessState.ALL_BILLCLEAR_ONROAD, BusinessState.ALL_SINGLECLEAR_ONROAD, BusinessState.ALL_PAYMENT_ONROAD);
				homePaymentCbill.setOnroadamount(Onroadamount);
				//清帐金额
				homePaymentCbill.setClearamount2(new BigDecimal("0.0"));
				homePaymentCbill.setAccountdate(vendorTitle.getBudat());
				homePaymentCbill.setText(vendorTitle.getBktxt());
				
				// "未付金额"必须大于"在途金额"，否则不能添加
				if (Unpaidamount.compareTo(Onroadamount) == 1){
					projectPaymentCbillList.add(homePaymentCbill);
				}
			}
		}
		
		//把立项的发票取到列表中
		if (projectPaymentCbillList != null && projectPaymentCbillList.size() > 0){
			for (int l=0;l<projectPaymentCbillList.size();l++){
				HomePaymentCbill homePaymentCbill = new HomePaymentCbill();
				homePaymentCbill = (HomePaymentCbill) projectPaymentCbillList.get(l);
				
				homePaymentCbillList.add(homePaymentCbill);
			}
		}
		
		return homePaymentCbillList;
	}
	
	/**
	 * 增加发票信息
	 * @param strBillNoList
	 * @return
	 */
	public List<HomePaymentCbill> _addBillInfo(String strBillNoList){
		List<HomePaymentCbill> homePaymentCbillList = new ArrayList<HomePaymentCbill>();
		
		//取的发票信息
		List<VendorTitle> VendorTitleList = new ArrayList<VendorTitle>();
		String strMiddleBillList = strBillNoList.substring(0, strBillNoList.length()-1);
		strMiddleBillList = "'" + strMiddleBillList.replace("|", "','") + "'";
		String strHql = "from VendorTitle a where a.invoice in ("+strMiddleBillList+") and a.iscleared = '0' order by a.budat";
		VendorTitleList = this.vendorTitleHibernateDao.find(strHql);
		
		//把合同取出的发票放到LIST中
		for (int i=0;i<VendorTitleList.size();i++){
			HomePaymentCbill homePaymentCbill = new HomePaymentCbill();
			VendorTitle vendorTitle = (VendorTitle)VendorTitleList.get(i);
			
			homePaymentCbill.setBillid(vendorTitle.getVendortitleid());
			homePaymentCbill.setBillno(vendorTitle.getInvoice());
			homePaymentCbill.setProject_no(vendorTitle.getLixiang());
			homePaymentCbill.setContract_no(vendorTitle.getVerkf());
			homePaymentCbill.setVoucherno(vendorTitle.getBelnr());
			String strPageContract = this.homePayMentCBillJdbcDao.getPruchasePageContract(homePaymentCbill.getContract_no());
			homePaymentCbill.setOld_contract_no(strPageContract);
			homePaymentCbill.setSap_order_no(vendorTitle.getEbeln());
			homePaymentCbill.setCurrency(vendorTitle.getWaers());
			//应付款金额
			homePaymentCbill.setPayableamount(vendorTitle.getDmbtr());
			//应付款日期
			homePaymentCbill.setPayabledate(vendorTitle.getYfkr());
			//已付金额
			BigDecimal Paidamount = this.vendorTitleJdbcDao.getSumClearAmount(homePaymentCbill.getBillno(), BusinessState.ALL_BILLCLEAR_PAIDUP, BusinessState.ALL_SINGLECLEAR_PAIDUP, BusinessState.ALL_PAYMENT_PAIDUP);
			homePaymentCbill.setPaidamount(Paidamount);
			//未付金额
			BigDecimal Unpaidamount = homePaymentCbill.getPayableamount().subtract(Paidamount);
			homePaymentCbill.setUnpaidamount(Unpaidamount);
			//在途金额
			BigDecimal Onroadamount = this.vendorTitleJdbcDao.getSumClearAmount(homePaymentCbill.getBillno(), BusinessState.ALL_BILLCLEAR_ONROAD, BusinessState.ALL_SINGLECLEAR_ONROAD, BusinessState.ALL_PAYMENT_ONROAD);
			homePaymentCbill.setOnroadamount(Onroadamount);
			//清帐金额
			homePaymentCbill.setClearamount2(new BigDecimal("0.0"));
			homePaymentCbill.setAccountdate(vendorTitle.getBudat());
			homePaymentCbill.setText(vendorTitle.getBktxt());
			
			// "未付金额"必须大于"在途金额"，否则不能添加
			if (Unpaidamount.compareTo(Onroadamount) == 1){
				homePaymentCbillList.add(homePaymentCbill);
			}
		}
		
		return homePaymentCbillList;
	}
	
	/**
	 * 根据部门ID获得公司ID
	 */
	public String getCompanyIDByDeptID(String deptID)
	{
		return this.sysDeptJdbcDao.getCompanyCode(deptID);
	}
	
	/**
	 * 得到结算科目的凭证行项目
	 * @param importSettSubj
	 * @return
	 */
	public Set<VoucherItem> getSettSubjVoucherItem(HomeSettSubj settleSubjectPay, String bukrs){
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
				voucherItem.setCurrency(settleSubjectPay.getHomePayment().getFactcurrency());
				//货币金额
				voucherItem.setAmount(settleSubjectPay.getAmount1());
				//本位币金额
				voucherItem.setAmount2(settleSubjectPay.getStandardamount1());
				//部门
				voucherItem.setDepid(settleSubjectPay.getDepid1());
				//文本
				voucherItem.setText(settleSubjectPay.getHomePayment().getText());
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
				voucherItem.setCurrency(settleSubjectPay.getHomePayment().getFactcurrency());
				//货币金额
				voucherItem.setAmount(settleSubjectPay.getAmount2());
				//本位币金额
				voucherItem.setAmount2(settleSubjectPay.getStandardamount2());
				//部门
				voucherItem.setDepid(settleSubjectPay.getDepid2());
				//文本
				voucherItem.setText(settleSubjectPay.getHomePayment().getText());
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
				voucherItem.setCurrency(settleSubjectPay.getHomePayment().getFactcurrency());
				//货币金额
				voucherItem.setAmount(settleSubjectPay.getAmount3());
				//本位币金额
				voucherItem.setAmount2(settleSubjectPay.getStandardamount3());
				//部门
				voucherItem.setDepid(settleSubjectPay.getDepid3());
				//文本
				voucherItem.setText(settleSubjectPay.getHomePayment().getText());
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
				voucherItem.setCurrency(settleSubjectPay.getHomePayment().getFactcurrency());
				//货币金额
				voucherItem.setAmount(settleSubjectPay.getAmount4());
				//本位币金额
				voucherItem.setAmount2(settleSubjectPay.getStandardamount4());
				//部门
				voucherItem.setDepid(settleSubjectPay.getDepid4());
				//文本
				voucherItem.setText(settleSubjectPay.getHomePayment().getText());
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
	public Set<VoucherItem> getFundFlowVoucherItem(HomePayment homePayment){
		Set<VoucherItem> voucherItemList = new HashSet<VoucherItem>();
		HomeFundFlow fundFlowPay = homePayment.getHomeFundFlow();
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
				voucherItem.setCurrency(fundFlowPay.getHomePayment().getFactcurrency());
				//货币金额
				voucherItem.setAmount(fundFlowPay.getAmount1());
				//本位币金额
				voucherItem.setAmount2(fundFlowPay.getStandardamount1());
				//部门
				voucherItem.setDepid(fundFlowPay.getDepid1());
				//文本
				voucherItem.setText(fundFlowPay.getHomePayment().getText());
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
				voucherItem.setCurrency(fundFlowPay.getHomePayment().getFactcurrency());
				//货币金额
				voucherItem.setAmount(fundFlowPay.getAmount2());
				//本位币金额
				voucherItem.setAmount2(fundFlowPay.getStandardamount2());
				//部门
				voucherItem.setDepid(fundFlowPay.getDepid2());
				//文本
				voucherItem.setText(fundFlowPay.getHomePayment().getText());
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
				voucherItem.setCurrency(fundFlowPay.getHomePayment().getFactcurrency());
				//货币金额
				voucherItem.setAmount(fundFlowPay.getAmount3());
				//本位币金额
				voucherItem.setAmount2(fundFlowPay.getStandardamount3());
				//部门
				voucherItem.setDepid(fundFlowPay.getDepid3());
				//文本
				voucherItem.setText(fundFlowPay.getHomePayment().getText());
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
	 * 得到付款的凭证抬头
	 * @param importPayment
	 * @return
	 */
	public Voucher getVoucher(HomePayment homePayment,String strClass){
		Voucher voucher = new Voucher();
		
		UserContext userContext = UserContextHolder.getLocalUserContext()
		.getUserContext();
		
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
	 * 人民币模拟凭证
	 * @param importPayment
	 * @return
	 */
	public List<String> cnySaveVoucher(HomePayment homePayment){
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
		Iterator<HomePaymentItem> PayItemItemit = homePayment.getHomePaymentItem() .iterator();		
		while(PayItemItemit.hasNext()){
			rowNo = rowNo + 1;
			HomePaymentItem homePaymentItem = PayItemItemit.next();
			
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
			Iterator<HomePayBankItem> bankItemit = homePayment.getHomePayBankItem().iterator();		
			while(bankItemit.hasNext()){
				rowNo = rowNo + 1;
				HomePayBankItem homePayBankItem = bankItemit.next();
				
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
		
		SettlesubjectVoucherItemList = this.getSettSubjVoucherItem(homePayment.getHomeSettSubj(),bukrs);
		
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
	 * @param importPayment
	 * @return
	 */
	public List<String> dealBillSaveVoucher(HomePayment homePayment){
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
		Iterator<HomePaymentItem> PayItemItemit = homePayment.getHomePaymentItem() .iterator();		
		while(PayItemItemit.hasNext()){
			rowNo = rowNo + 1;
			HomePaymentItem homePaymentItem = PayItemItemit.next();
			
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
		
		SettlesubjectVoucherItemList = this.getSettSubjVoucherItem(homePayment.getHomeSettSubj(),bukrs);
		
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
	 * @param importPayment
	 * @return
	 */
	public List<String> dealBillClearSaveVoucher(HomePayment homePayment){
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
		Iterator<HomePayBankItem> bankItemit = homePayment.getHomePayBankItem().iterator();		
		while(bankItemit.hasNext()){
			rowNo = rowNo + 1;
			HomePayBankItem homePayBankItem = bankItemit.next();
			
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
		
		SettlesubjectVoucherItemList = this.getSettSubjVoucherItem(homePayment.getHomeSettSubj(),bukrs);
		
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
	 * 背书模拟凭证
	 * @param importPayment
	 * @return
	 */
	public List<String> repeatLessonSaveVoucher(HomePayment homePayment) throws BusinessException{
		List<String> retList = new ArrayList<String>();
		
		//this.voucherJdbcDao.deleteVoucherByBusinessidAndClass(homePayment.getPaymentid(), "1");
		String bukrs = getCompanyIDByDeptID(homePayment.getDept_id());
		//凭证抬头
		Voucher voucher = new Voucher();
		//凭证行项目
		Set<VoucherItem> voucherItemList = new HashSet<VoucherItem>();
		
		voucher = this.getVoucher(homePayment, "1");
		voucher.setPay("X");
		//voucher.setReceive("X");
		voucher.setFlag("W");
		
		Collect collect = this.collectJdbcDao.getCustomerBydraft(homePayment.getDraft());
		if(collect==null){
			throw new BusinessException("不存在该银行承兑汇票号!");
		}
		int rowNo = 0;
		//******************供应商行项目凭证行项目**********************************//
		Iterator<HomePaymentItem> PayItemItemit = homePayment.getHomePaymentItem().iterator();		
		while(PayItemItemit.hasNext()){
			rowNo = rowNo + 1;
			HomePaymentItem homePaymentItem = PayItemItemit.next();
			
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
			//是否票据标志
			voucherItem.setFlag("W");
			//票据业务范围
//			voucherItem.setGsber(homePayment.getBillbc());
			//应付账款的业务范围取本部的业务范围
			voucherItem.setGsber(this.sysDeptJdbcDao.getDeptCodeById(homePayment.getDept_id()));
			//票据到期 日
			voucherItem.setZfbdt(collect.getDraftdate().replace("-", ""));
			//付款行项目信息
			voucherItem.setBusinessitemid(homePaymentItem.getPaymentitemid());
			
			voucherItemList.add(voucherItem);
		}
		//******************供应商行项目凭证行项目**********************************//
		//*********************客户行项目凭证行项目*********************/
		String strCustomer = collect.getCustomer();
		String strCustomerbukrs = getCompanyIDByDeptID(homePayment.getDept_id());
		String strCustomerName = this.customerRefundItemJdbcDao.getCustomerDescByCustomerId(strCustomer,strCustomerbukrs);
		//Iterator<HomePaymentItem> PayItemItemit2 = homePayment.getHomePaymentItem().iterator();		
		//while(PayItemItemit2.hasNext()){
			rowNo = rowNo + 1;
			//HomePaymentItem homePaymentItem = PayItemItemit2.next();
			
			VoucherItem voucherItem = new VoucherItem();
			voucherItem.setVoucher(voucher);
			//客户记账码
			voucherItem.setCheckcode("19");
			//特殊G/L标识
			voucherItem.setGlflag("W");
			//科目
			voucherItem.setSubject(strCustomer);
			//科目说明
			voucherItem.setSubjectdescribe(strCustomerName);
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
			voucherItem.setCashflowitem("");
			//反记账标识
			voucherItem.setUncheckflag("");
			String subject = this.customerRefundItemJdbcDao.getSubjectByCustomer(strCustomer,strCustomerbukrs);
			subject = this.customerRefundItemJdbcDao.getSkont(voucherItem,subject);
			//统驭项目
			voucherItem.setControlaccount(subject);
			//统驭科目说明
			voucherItem.setCaremark(this.customerRefundItemJdbcDao.getSubjectNameById(subject,strCustomerbukrs));
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
			//票据业务范围
//			voucherItem.setGsber(homePayment.getBillbc());
			//票据的业务范围取单据的业务范围
			voucherItem.setGsber(collect.getBillbc());
			//票据到期 日
			voucherItem.setZfbdt(collect.getDraftdate().replace("-", ""));
			
			voucherItemList.add(voucherItem);
		//}
		//*********************客户行项目凭证行项目*********************/
		
		//******************结算科目行项目凭证行项目*******************************//
		Set<VoucherItem> SettlesubjectVoucherItemList = new HashSet<VoucherItem>();
		
		SettlesubjectVoucherItemList = this.getSettSubjVoucherItem(homePayment.getHomeSettSubj(),bukrs);
		
		Iterator<VoucherItem> Settlesubjectit = SettlesubjectVoucherItemList.iterator();		
		while(Settlesubjectit.hasNext()){
			rowNo = rowNo + 1;
			VoucherItem setvoucherItem =Settlesubjectit.next();
			//是否票据标志
			setvoucherItem.setFlag("W");
			setvoucherItem.setRownumber(String.valueOf(rowNo));
			//票据业务范围
			setvoucherItem.setGsber(homePayment.getBillbc());
			//票据到期 日
			setvoucherItem.setZfbdt(collect.getDraftdate().replace("-", ""));
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
			//是否票据标志
			funvoucherItem.setFlag("W");
			//票据业务范围
			funvoucherItem.setGsber(homePayment.getBillbc());
			//票据到期 日
			funvoucherItem.setZfbdt(collect.getDraftdate().replace("-", ""));
			funvoucherItem.setVoucher(voucher);
			
			voucherItemList.add(funvoucherItem);
		}
		//******************特殊总帐行项目凭证行项目*******************************//
		
		voucher.setVoucherItem(voucherItemList);		
		this.voucherHibernateDao.save(voucher);
		retList.add(voucher.getVoucherid());
		
		//*************客户清帐处理清帐凭证*****************************
//		this.voucherJdbcDao.deleteVoucherByBusinessidAndClass(homePayment.getPaymentid(), "9");
//		Voucher customerClearVoucher = new Voucher();
//		customerClearVoucher = getRepeatLessonClearVoucher(homePayment,"9",voucher,"N");
//		
//		this.voucherHibernateDao.save(customerClearVoucher);
//		retList.add(customerClearVoucher.getVoucherid());
		
		return retList;
	}
	
	/**
	 * 检查背书时的银行承兑汇票号信息
	 * 0:不存在或有不只一个有银行承兑汇票号
	 * 1:银行承兑汇票号已经使用过
	 * -1:可以使用
	 * @param strDraft
	 * @return
	 */
	public int _checkDraftInfo(String strDraft,String strPaymentId){
		int iResult = -1;
		
		iResult = this.homePayMentCBillJdbcDao.isExitsDraftInfo(strDraft);
		
		if (iResult == 1){
			iResult = this.homePayMentCBillJdbcDao.isUseDraftInfo(strDraft,strPaymentId);
			
			if (iResult > 0){
				iResult = 1;
			}else{
				iResult = -1;
			}
		}else{
			iResult = 0;
		}

		return iResult;
	}
}