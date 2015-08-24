/*
 * @(#)ImportPaymentCbillService.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年07月05日 09点48分52秒
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
import com.infolion.platform.dpframework.util.StringUtils;
import com.infolion.platform.console.org.dao.SysDeptJdbcDao;
import com.infolion.platform.console.sys.context.UserContext;
import com.infolion.platform.console.sys.context.UserContextHolder;
import com.infolion.xdss3.BusinessState;
import com.infolion.xdss3.bankInfo.dao.BankInfoJdbcDao;
import com.infolion.xdss3.bankInfo.domain.BankInfo;
import com.infolion.xdss3.bankInfo.service.BankInfoService;
import com.infolion.xdss3.billPayReBankItem.domain.BillPayReBankItem;
import com.infolion.xdss3.collectcbill.domain.CollectCbill;
import com.infolion.xdss3.customerDrawback.dao.CustomerRefundItemJdbcDao;
import com.infolion.xdss3.masterData.dao.VendorTitleHibernateDao;
import com.infolion.xdss3.masterData.dao.VendorTitleJdbcDao;
import com.infolion.xdss3.masterData.domain.VendorTitle;
import com.infolion.xdss3.payment.domain.ClearVoucherItemStruct;
import com.infolion.xdss3.payment.domain.HomePayBankItem;
import com.infolion.xdss3.payment.domain.HomePayment;
import com.infolion.xdss3.payment.domain.HomePaymentCbill;
import com.infolion.xdss3.payment.domain.HomePaymentItem;
import com.infolion.xdss3.payment.domain.ImportDocuBankItem;
import com.infolion.xdss3.payment.domain.ImportFundFlow;
import com.infolion.xdss3.payment.domain.ImportPayBankItem;
import com.infolion.xdss3.payment.domain.ImportPayment;
import com.infolion.xdss3.payment.domain.ImportPaymentCbill;
import com.infolion.xdss3.payment.domain.ImportPaymentItem;
import com.infolion.xdss3.payment.domain.ImportSettSubj;
import com.infolion.xdss3.payment.service.ImportPaymentCbillService;
import com.infolion.xdss3.payment.dao.PayMentCBillJdbcDao;
import com.infolion.xdss3.paymentGen.service.ImportPaymentCbillServiceGen;
import com.infolion.xdss3.voucher.dao.VoucherHibernateDao;
import com.infolion.xdss3.voucher.dao.VoucherJdbcDao;
import com.infolion.xdss3.voucher.domain.Voucher;
import com.infolion.xdss3.voucher.service.VoucherService;
import com.infolion.xdss3.voucheritem.dao.VoucherItemJdbcDao;
import com.infolion.xdss3.voucheritem.domain.VoucherItem;

/**
 * <pre>
 * 发票清帐(ImportPaymentCbill)服务类
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
public class ImportPaymentCbillService extends ImportPaymentCbillServiceGen
{
	private Log log = LogFactory.getFactory().getLog(this.getClass());
	
	@Autowired
	private VendorTitleHibernateDao vendorTitleHibernateDao;

	public void setVendorTitleHibernateDao(VendorTitleHibernateDao vendorTitleHibernateDao)
	{
		this.vendorTitleHibernateDao = vendorTitleHibernateDao;
	}
	
	@Autowired
	protected VoucherItemJdbcDao voucherItemJdbcDao;
	public void setVoucherItemJdbcDao(VoucherItemJdbcDao voucherItemJdbcDao) {
		this.voucherItemJdbcDao = voucherItemJdbcDao;
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
	private PayMentCBillJdbcDao importPayMentCBillJdbcDao;

	public void setImportPayMentCBillJdbcDao(PayMentCBillJdbcDao importPayMentCBillJdbcDao)
	{
		this.importPayMentCBillJdbcDao = importPayMentCBillJdbcDao;
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
	private BankInfoJdbcDao bankInfoJdbcDao;
	
	public void setBankInfoJdbcDao(BankInfoJdbcDao bankInfoJdbcDao)
	{
		this.bankInfoJdbcDao = bankInfoJdbcDao;
	}
	
	@Autowired
	private VoucherService voucherService;
	
	public void setVoucherService(VoucherService voucherService) {
		this.voucherService = voucherService;
	}		
	
	
	/**
	 * 得到清帐凭证的抬头信息
	 * @param importPayment
	 * @param strClass
	 * @return
	 */
	public Voucher getClearVoucher(ImportPayment importPayment,String strClass){
		Voucher voucher = new Voucher();
		
		UserContext userContext = UserContextHolder.getLocalUserContext()
		.getUserContext();
		
		//业务编号
		voucher.setBusinessid(importPayment.getPaymentid());
		//业务类型
		voucher.setBusinesstype("02");
		//凭证类型
		voucher.setVouchertype("KZ");
		//凭证抬头文本
		voucher.setVouchertext("清帐凭证");
		//过账日期
		voucher.setCheckdate(importPayment.getAccountdate().replace("-", ""));
		//公司代码
		String bukrs = getCompanyIDByDeptID(importPayment.getDept_id());
		voucher.setCompanycode(bukrs); //公司代码
		//货币
		if (strClass.equals("7")){
			voucher.setCurrency(importPayment.getFactcurrency());
		}else{
			Iterator<ImportPaymentCbill> importPaymentCbillIterator =  importPayment.getImportPaymentCbill().iterator();
			if(importPaymentCbillIterator.hasNext()){
				ImportPaymentCbill importPaymentCbill = importPaymentCbillIterator.next();
				voucher.setCurrency(importPaymentCbill.getCurrency());
			}else{
				voucher.setCurrency(importPayment.getFactcurrency());
			}
		}
		//凭证日期
		voucher.setVoucherdate(importPayment.getVoucherdate().replace("-", ""));
		//会计年度
		voucher.setFiyear(importPayment.getAccountdate().substring(0, 4));
		//会计期间
		voucher.setFiperiod(importPayment.getAccountdate().substring(5, 7));
		//输入日期
		SimpleDateFormat  dateFormat=new SimpleDateFormat ("yyyyMMdd");
		voucher.setImportdate(dateFormat.format(new Date()));
		//输入者
		voucher.setImporter(userContext.getSysUser().getUserName());
		//预制者
		voucher.setPreparer(userContext.getSysUser().getUserName());
		//银行科目号
		Iterator<ImportPayBankItem> bankItemit = importPayment.getImportPayBankItem().iterator();
		if(importPayment.getImportPayBankItem().size() >0){
		ImportPayBankItem importPayBankItem = bankItemit.next();
		voucher.setKonto(importPayBankItem.getPaybanksubject());
		}
		//计息日
		voucher.setValut(dateFormat.format(new Date()));
		//供应商编号
		voucher.setAgkon(importPayment.getSupplier());
		//科目类型
		voucher.setAgkoa("K");
		//业务范围
		voucher.setGsber(this.sysDeptJdbcDao.getDeptCodeById(importPayment.getDept_id()));
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
	public boolean checkIsClear(ImportPayment importPayment){
		boolean bFlag = false;
		String strMiddleContract = "";
		String strMiddleProject = "";
		
		String strCurrentFlag = this.importPayMentCBillJdbcDao.checkPayCurrentClear(
				importPayment.getPaymentid());
		
		
		String strContractFlag = this.importPayMentCBillJdbcDao.checkPayContractClear(
				importPayment.getPaymentid());
		
		if (strContractFlag == null || "".equals(strContractFlag)){
			strMiddleContract = "'无'";
		}else{
			strMiddleContract = strContractFlag;
		}
		
		String strProjectFlag = this.importPayMentCBillJdbcDao.checkPayProjectClear(
				importPayment.getPaymentid(),strMiddleContract);
		
		if (strProjectFlag == null || "".equals(strProjectFlag)){
			strMiddleProject = "'无'";
		}else{
			strMiddleProject = strProjectFlag;
		}
		
		String strSupplierFlag = this.importPayMentCBillJdbcDao.checkPaySupplierClear(
				importPayment.getPaymentid(),strMiddleContract,strMiddleProject);
		
		
		
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
	 * 得到有汇损益的行项目信息
	 * @param importPayment
	 * @param subtractVlaue
	 * @param strType
	 * @return
	 */
	public VoucherItem getProfitAndLossVoucherItem(ImportPayment importPayment,
			BigDecimal subtractVlaue,
			String strType){
		String bukrs = getCompanyIDByDeptID(importPayment.getDept_id());
		VoucherItem voucherItem = new VoucherItem();
		
		if (strType.equals("1")){
			//科目
			voucherItem.setSubject(importPayment.getSupplier());
			//科目说明
			
			String Subjectdescribe = this.customerRefundItemJdbcDao.getSupplierDescByCustomerId(importPayment.getSupplier(),bukrs);
			voucherItem.setSubjectdescribe(Subjectdescribe);
			
			String subject = this.customerRefundItemJdbcDao.getSubjectBySuppliers(importPayment.getSupplier(),bukrs);
			subject = this.customerRefundItemJdbcDao.getSkont(voucherItem,subject);
			//统驭项目
			voucherItem.setControlaccount(subject);
			//统驭科目说明
			voucherItem.setCaremark(this.customerRefundItemJdbcDao.getSubjectNameById(subject,bukrs));
			
			if (subtractVlaue.signum() == 1){
				//记帐码
				voucherItem.setCheckcode("25");
				voucherItem.setDebitcredit("S");
			}else{
				//记帐码
				voucherItem.setCheckcode("38");
				voucherItem.setDebitcredit("H");
			}
		}
		
		if (strType.equals("2")){
			if (subtractVlaue.signum() == 1){
				//记帐码
				voucherItem.setCheckcode("50");
				voucherItem.setDebitcredit("H");
				String subjectname = this.customerRefundItemJdbcDao.getSubjectNameById("6603050402",bukrs);
				//科目
				voucherItem.setSubject("6603050402");
				//科目说明
				voucherItem.setSubjectdescribe(subjectname);
				//统驭项目
				voucherItem.setControlaccount("6603050402");
				//统驭科目说明
				voucherItem.setCaremark(subjectname);
			}else{
				//记帐码
				voucherItem.setCheckcode("40");
				voucherItem.setDebitcredit("S");
				String subjectname = this.customerRefundItemJdbcDao.getSubjectNameById("6603050401",bukrs);
				//科目
				voucherItem.setSubject("6603050401");
				//科目说明
				voucherItem.setSubjectdescribe(subjectname);
				//统驭项目
				voucherItem.setControlaccount("6603050401");
				//统驭科目说明
				voucherItem.setCaremark(subjectname);
			}
		}
		
		//货币
		voucherItem.setCurrency(importPayment.getFactcurrency());
		//货币金额
		voucherItem.setAmount(new BigDecimal("0"));
		//本位币金额
		voucherItem.setAmount2(subtractVlaue.abs());
		//部门
		voucherItem.setDepid(this.sysDeptJdbcDao.getDeptCodeById(importPayment.getDept_id()));
		//文本
		voucherItem.setText(importPayment.getText());
		
		return voucherItem;
	}
	
	//汇兑损益致凭证
	public void genProfOrLossVoucher(ImportPayment importPayment){
		if (importPayment.getImportPaymentCbill() != null && importPayment.getImportPaymentCbill().size()>0){
			Voucher voucher = this.getVoucher(importPayment, "1");
			Set voucherItems = new HashSet();
			
			BigDecimal billValue = new BigDecimal("0");
			BigDecimal itemValue = new BigDecimal("0");
			BigDecimal subtractVlaue = new BigDecimal("0");
			
			//票的金额（发票的汇率*本位币）、款的金额（收款的汇率*本位币）
			
			String contract_no = "";
			String project_no = "";
			String taxcode = "";
			Iterator<ImportPaymentCbill> importPaymentCbillIterator2 =  importPayment.getImportPaymentCbill().iterator();
			if(importPaymentCbillIterator2.hasNext()){
				ImportPaymentCbill importPaymentCbill = importPaymentCbillIterator2.next();
				
				if(StringUtils.isNullBlank(project_no)){
					if(StringUtils.isNullBlank(contract_no)){
						contract_no = importPaymentCbill.getContract_no();
						taxcode = importPaymentCbill.getContract_no();
					}else{
						if (!contract_no.equals(importPaymentCbill.getContract_no())){
							taxcode = importPaymentCbill.getProject_no();
							project_no = importPaymentCbill.getProject_no();
						}
					}
				}
				
				contract_no = importPaymentCbill.getContract_no();
			}
			
			Iterator<ImportPaymentCbill> importPaymentCbillIterator =  importPayment.getImportPaymentCbill().iterator();
			while(importPaymentCbillIterator.hasNext()){
				ImportPaymentCbill importPaymentCbill = importPaymentCbillIterator.next();
				VendorTitle vendorTitle = this.vendorTitleJdbcDao.getByInvoice(importPaymentCbill.getBillno());

				billValue = billValue.add(vendorTitle.getBwbje().divide(vendorTitle.getDmbtr(),5,BigDecimal.ROUND_HALF_EVEN).multiply(importPaymentCbill.getClearamount2()));
				itemValue = itemValue.add(importPaymentCbill.getClearamount2().multiply(importPayment.getExchangerate()));
			}
		
			subtractVlaue = billValue.subtract(itemValue);
			if (new BigDecimal("0").compareTo(subtractVlaue) != 0){
				VoucherItem supplierVoucherItem = getProfitAndLossVoucherItem(
						importPayment,subtractVlaue,"1");
				supplierVoucherItem.setRownumber("1");
				supplierVoucherItem.setVoucher(voucher);
				voucherItems.add(supplierVoucherItem);
				
				VoucherItem profOrLossVoucherItem = getProfitAndLossVoucherItem(
						importPayment,subtractVlaue,"2");
				profOrLossVoucherItem.setRownumber("2");
				profOrLossVoucherItem.setVoucher(voucher);
				
				profOrLossVoucherItem.setTaxcode(taxcode);
				voucherItems.add(profOrLossVoucherItem);
			}
			
			voucher.setVoucherItem(voucherItems);
			this.voucherHibernateDao.save(voucher);
		}
	}
	
	/**
	 * 处理清帐凭证
	 * @param importPayment   原来的付款实例类
	 * @param payVoucher      付款凭证的信息
	 * @param strIsSHow       是否汇损行项目
	 * @return
	 */
	public void dealClearAccountVoucher(ImportPayment importPayment,Voucher payVoucher,String strIsSHow){
		
		if (checkIsClear(importPayment) == true){
			//this.voucherJdbcDao.deleteVoucherByBusinessidAndClass(importPayment.getPaymentid(), "9");
			//判断是否已经生成清账凭证，	如果有，不再生成	
			List<Voucher> li=voucherHibernateDao.getVoucherByBusinessId2(importPayment.getPaymentid(),"A");
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
			
			String strCurrentFlag = this.importPayMentCBillJdbcDao.checkPayCurrentClear(
					importPayment.getPaymentid());
			String strContractFlag = this.importPayMentCBillJdbcDao.checkPayContractClear(
					importPayment.getPaymentid());
			
			if (strContractFlag == null || "".equals(strContractFlag)){
				strMiddleContract = "'无'";
			}else{
				strMiddleContract = strContractFlag;
			}
			
			String strProjectFlag = this.importPayMentCBillJdbcDao.checkPayProjectClear(
					importPayment.getPaymentid(),strMiddleContract);
			
			if (strProjectFlag == null || "".equals(strProjectFlag)){
				strMiddleProject = "'无'";
			}else{
				strMiddleProject = strProjectFlag;
			}
			
			String strSupplierFlag = this.importPayMentCBillJdbcDao.checkPaySupplierClear(
					importPayment.getPaymentid(),strMiddleContract,strMiddleProject);
			
			//得到票的本位币之和
			BigDecimal billValue = new BigDecimal("0");
			//得到分配项的本位币之和
			BigDecimal itemValue = new BigDecimal("0");
			//分配项之和和票之和的差
			BigDecimal subtractVlaue = new BigDecimal("0");
			
			Voucher voucher = new Voucher();
			//凭证抬头
			voucher = getClearVoucher(importPayment,"9");
			//凭证行项目
			Set<VoucherItem> voucherItemList = new HashSet<VoucherItem>();
			

			if (strCurrentFlag != null && strCurrentFlag.equals("Y")){
				//得到票的行项目信息
				Iterator<ImportPaymentCbill> importPaymentCbillIterator =  importPayment.getImportPaymentCbill().iterator();
				while(importPaymentCbillIterator.hasNext()){
					ImportPaymentCbill importPaymentCbill = importPaymentCbillIterator.next();
					ClearVoucherItemStruct clearVoucherItemStruct = this.vendorTitleJdbcDao.getCurrectBillInfo(importPaymentCbill.getBillno());
					
					VoucherItem voucherItem = getClearVoucherItem(clearVoucherItemStruct.getVoucherno(),
							clearVoucherItemStruct.getRowno(),
							clearVoucherItemStruct.getYear()," ");

					voucherItem.setVoucher(voucher);
					voucherItemList.add(voucherItem);
					
					billValue = billValue.add(clearVoucherItemStruct.getBwbje());
				}
				
				//得到分配项的行项目信息
				Iterator<ImportPaymentItem> PayItemItemit = importPayment.getImportPaymentItem().iterator();		
				while(PayItemItemit.hasNext()){
					ImportPaymentItem importPaymentItem = PayItemItemit.next();
					
					Set<VoucherItem> payVoucherItem = payVoucher.getVoucherItem();
					Iterator<VoucherItem> payVoucherItemit = payVoucherItem.iterator();
					while(payVoucherItemit.hasNext()){
						VoucherItem payItem = payVoucherItemit.next();
						
						if (payItem.getBusinessitemid() != null &&	
							!"".equals(payItem.getBusinessitemid().trim()) && 
							importPaymentItem.getPaymentitemid() != null &&
							!"".equals(importPaymentItem.getPaymentitemid().trim()) && 
							payItem.getVoucher().getBusinessid().equals(importPaymentItem.getImportPayment().getPaymentid()) &&
							payItem.getBusinessitemid().equals(importPaymentItem.getPaymentitemid())){
								
							VoucherItem voucherItem = getClearVoucherItem("",
									payItem.getRownumber(),
									payItem.getVoucher().getFiyear(),
									payItem.getVoucher().getVoucherid());
							
							voucherItem.setBusinessitemid(payItem.getBusinessitemid());
							voucherItem.setVoucher(voucher);
							voucherItemList.add(voucherItem);
							
							itemValue = itemValue.add(payItem.getAmount2());
						}
					}
				}
				
				voucher.setVoucherItem(voucherItemList);
				
				if (strIsSHow.equals("Y")){
					subtractVlaue = billValue.subtract(itemValue);
					
					if (new BigDecimal("0").compareTo(subtractVlaue) != 0){
						VoucherItem supplierVoucherItem = getProfitAndLossVoucherItem(
								importPayment,subtractVlaue,"1");
						supplierVoucherItem.setRownumber("1");
						supplierVoucherItem.setVoucher(voucher);
						voucherItemList.add(supplierVoucherItem);
						
						VoucherItem profOrLossVoucherItem = getProfitAndLossVoucherItem(
								importPayment,subtractVlaue,"2");
						profOrLossVoucherItem.setRownumber("2");
						profOrLossVoucherItem.setVoucher(voucher);
						voucherItemList.add(profOrLossVoucherItem);
					}
				}
				
			}else{
				//合同层的清帐行项目信息
				if (strContractFlag != null && !"".equals(strContractFlag)){
					String[] strContractList = strContractFlag.split(",");
					
					for (int i = 0; i < strContractList.length; i++) {
						
						//得到之前合同的票信息
						List<ClearVoucherItemStruct> clearVoucherItemStructList = 
							this.vendorTitleJdbcDao.getContractBillInfo(importPayment.getSupplier(),strContractList[i].substring(1, strContractList[i].length()-1));
						
						for (int j = 0;j<clearVoucherItemStructList.size();j++){
							ClearVoucherItemStruct clearVoucherItemStruct = 
								(ClearVoucherItemStruct) clearVoucherItemStructList.get(j);
							
							VoucherItem voucherItem = getClearVoucherItem(clearVoucherItemStruct.getVoucherno(),
									clearVoucherItemStruct.getRowno(),
									clearVoucherItemStruct.getYear()," ");
							
							voucherItem.setVoucher(voucher);
							voucherItemList.add(voucherItem);
							
							billValue = billValue.add(clearVoucherItemStruct.getBwbje());
						}
						
						//得到本次票的行项目信息
						Iterator<ImportPaymentCbill> importPaymentCbillIterator =  importPayment.getImportPaymentCbill().iterator();
						while(importPaymentCbillIterator.hasNext()){
							ImportPaymentCbill importPaymentCbill = importPaymentCbillIterator.next();

							if (importPaymentCbill.getContract_no() != null && 
								strContractList[i].indexOf(importPaymentCbill.getContract_no()) != -1){
								ClearVoucherItemStruct clearVoucherItemStruct = this.vendorTitleJdbcDao.getCurrectBillInfo(importPaymentCbill.getBillno());
								
								VoucherItem voucherItem = getClearVoucherItem(clearVoucherItemStruct.getVoucherno(),
										clearVoucherItemStruct.getRowno(),
										clearVoucherItemStruct.getYear()," ");
								
								voucherItem.setVoucher(voucher);
								voucherItemList.add(voucherItem);
								
								billValue = billValue.add(clearVoucherItemStruct.getBwbje());
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
							
							itemValue = itemValue.add(clearVoucherItemStruct.getBwbje());
						}
						
						//得到本次分配项的行项目信息
						Iterator<ImportPaymentItem> PayItemItemit = importPayment.getImportPaymentItem() .iterator();		
						while(PayItemItemit.hasNext()){
							ImportPaymentItem importPaymentItem = PayItemItemit.next();

							if (importPaymentItem.getContract_no() != null && 
								strContractList[i].indexOf(importPaymentItem.getContract_no()) != -1){
								Set<VoucherItem> payVoucherItem = payVoucher.getVoucherItem();
								Iterator<VoucherItem> payVoucherItemit = payVoucherItem.iterator();
								while(payVoucherItemit.hasNext()){
									VoucherItem payItem = payVoucherItemit.next();
									
									if (payItem.getBusinessitemid() != null &&	
										!"".equals(payItem.getBusinessitemid().trim()) && 
										importPaymentItem.getPaymentitemid() != null &&
										!"".equals(importPaymentItem.getPaymentitemid().trim()) && 
										payItem.getVoucher().getBusinessid().equals(importPaymentItem.getImportPayment().getPaymentid()) &&
										payItem.getBusinessitemid().equals(importPaymentItem.getPaymentitemid())){
											
										VoucherItem voucherItem = getClearVoucherItem("",
												payItem.getRownumber(),
												payItem.getVoucher().getFiyear(),
												payItem.getVoucher().getVoucherid());
										
										voucherItem.setBusinessitemid(payItem.getBusinessitemid());
										voucherItem.setVoucher(voucher);
										voucherItemList.add(voucherItem);
										
										itemValue = itemValue.add(payItem.getAmount2());
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
							this.vendorTitleJdbcDao.getProjectBillInfo(importPayment.getSupplier(),strProjectList[i].substring(1, strProjectList[i].length()-1), strContractFlag);
						
						for (int j = 0;j<clearVoucherItemStructList.size();j++){
							ClearVoucherItemStruct clearVoucherItemStruct = 
								(ClearVoucherItemStruct) clearVoucherItemStructList.get(j);
							
							VoucherItem voucherItem = getClearVoucherItem(clearVoucherItemStruct.getVoucherno(),
									clearVoucherItemStruct.getRowno(),
									clearVoucherItemStruct.getYear()," ");

							voucherItem.setVoucher(voucher);
							voucherItemList.add(voucherItem);
							
							billValue = billValue.add(clearVoucherItemStruct.getBwbje());
						}
						
						//得到本次票的行项目信息
						Iterator<ImportPaymentCbill> importPaymentCbillIterator =  importPayment.getImportPaymentCbill().iterator();
						while(importPaymentCbillIterator.hasNext()){
							ImportPaymentCbill importPaymentCbill = importPaymentCbillIterator.next();
							
							if (importPaymentCbill.getProject_no() != null &&
								importPaymentCbill.getContract_no() != null && 
								strProjectList[i].indexOf(importPaymentCbill.getProject_no())!= -1 &&
								strContractFlag.indexOf(importPaymentCbill.getContract_no()) == -1){
								ClearVoucherItemStruct clearVoucherItemStruct = this.vendorTitleJdbcDao.getCurrectBillInfo(importPaymentCbill.getBillno());
								
								VoucherItem voucherItem = getClearVoucherItem(clearVoucherItemStruct.getVoucherno(),
										clearVoucherItemStruct.getRowno(),
										clearVoucherItemStruct.getYear()," ");
								
								
								voucherItem.setVoucher(voucher);
								voucherItemList.add(voucherItem);
								
								billValue = billValue.add(clearVoucherItemStruct.getBwbje());
							}
						}
						
						
						//得到之前的款分配信息
						List<ClearVoucherItemStruct> clearVoucherItemStructList2 = 
							this.vendorTitleJdbcDao.getProjectPayItemInfo(importPayment.getSupplier(),strProjectList[i].substring(1, strProjectList[i].length()-1), strContractFlag);
						
						for (int j = 0;j<clearVoucherItemStructList2.size();j++){
							ClearVoucherItemStruct clearVoucherItemStruct = 
								(ClearVoucherItemStruct) clearVoucherItemStructList2.get(j);
							
							VoucherItem voucherItem = getClearVoucherItem(clearVoucherItemStruct.getVoucherno(),
									clearVoucherItemStruct.getRowno(),
									clearVoucherItemStruct.getYear()," ");
							
							voucherItem.setBusinessitemid(clearVoucherItemStruct.getBusinessitemid());
							voucherItem.setVoucher(voucher);
							voucherItemList.add(voucherItem);
							
							itemValue = itemValue.add(clearVoucherItemStruct.getBwbje());
						}
						
						//得到本次分配项的行项目信息
						Iterator<ImportPaymentItem> PayItemItemit = importPayment.getImportPaymentItem() .iterator();		
						while(PayItemItemit.hasNext()){
							ImportPaymentItem importPaymentItem = PayItemItemit.next();

							if (importPaymentItem.getProject_no() != null &&
								importPaymentItem.getContract_no() != null && 
								strProjectList[i].indexOf(importPaymentItem.getProject_no())!= -1 &&
								strContractFlag.indexOf(importPaymentItem.getContract_no()) == -1){
								Set<VoucherItem> payVoucherItem = payVoucher.getVoucherItem();
								Iterator<VoucherItem> payVoucherItemit = payVoucherItem.iterator();
								while(payVoucherItemit.hasNext()){
									VoucherItem payItem = payVoucherItemit.next();
									
									if (payItem.getBusinessitemid() != null &&	
										!"".equals(payItem.getBusinessitemid().trim()) && 
										importPaymentItem.getPaymentitemid() != null &&
										!"".equals(importPaymentItem.getPaymentitemid().trim()) && 
										payItem.getVoucher().getBusinessid().equals(importPaymentItem.getImportPayment().getPaymentid()) &&
										payItem.getBusinessitemid().equals(importPaymentItem.getPaymentitemid())){
											
										VoucherItem voucherItem = getClearVoucherItem("",
												payItem.getRownumber(),
												payItem.getVoucher().getFiyear(),
												payItem.getVoucher().getVoucherid());

										voucherItem.setBusinessitemid(payItem.getBusinessitemid());
										voucherItem.setVoucher(voucher);
										voucherItemList.add(voucherItem);
										
										itemValue = itemValue.add(payItem.getAmount2());
									}
								}
							}
						}
					}
				}
				
				//供应商层的清帐行项目信息
				if (strSupplierFlag != null && "Y".equals(strSupplierFlag)){
					String bukrs = getCompanyIDByDeptID(importPayment.getDept_id());
					//得到之前合同的票信息
					List<ClearVoucherItemStruct> clearVoucherItemStructList = 
						this.vendorTitleJdbcDao.getSupplierBillInfo(importPayment.getSupplier(), 
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
						
						billValue = billValue.add(clearVoucherItemStruct.getBwbje());
					}
					
					//得到本次票的行项目信息
					Iterator<ImportPaymentCbill> importPaymentCbillIterator =  importPayment.getImportPaymentCbill().iterator();
					while(importPaymentCbillIterator.hasNext()){
						ImportPaymentCbill importPaymentCbill = importPaymentCbillIterator.next();
						
						if (importPaymentCbill.getContract_no() != null && 
							importPaymentCbill.getProject_no() != null && 
							strProjectFlag.indexOf(importPaymentCbill.getProject_no()) == -1 &&
							strContractFlag.indexOf(importPaymentCbill.getContract_no()) == -1){
							ClearVoucherItemStruct clearVoucherItemStruct = this.vendorTitleJdbcDao.getCurrectBillInfo(importPaymentCbill.getBillno());
							
							VoucherItem voucherItem = getClearVoucherItem(clearVoucherItemStruct.getVoucherno(),
									clearVoucherItemStruct.getRowno(),
									clearVoucherItemStruct.getYear()," ");
							
							
							voucherItem.setVoucher(voucher);
							voucherItemList.add(voucherItem);
							
							billValue = billValue.add(clearVoucherItemStruct.getBwbje());
						}
					}
					
					//得到之前的款分配信息
					List<ClearVoucherItemStruct> clearVoucherItemStructList2 = 
						this.vendorTitleJdbcDao.getSupplierPayItemInfo(importPayment.getSupplier(), 
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
						
						itemValue = itemValue.add(clearVoucherItemStruct.getBwbje());
					}
					
					//得到本次分配项的行项目信息
					Iterator<ImportPaymentItem> PayItemItemit = importPayment.getImportPaymentItem() .iterator();		
					while(PayItemItemit.hasNext()){
						ImportPaymentItem importPaymentItem = PayItemItemit.next();

						if (importPaymentItem.getProject_no() != null &&
							importPaymentItem.getContract_no() != null && 
							strProjectFlag.indexOf(importPaymentItem.getProject_no()) == -1 &&
							strContractFlag.indexOf(importPaymentItem.getContract_no()) == -1){
							Set<VoucherItem> payVoucherItem = payVoucher.getVoucherItem();
							Iterator<VoucherItem> payVoucherItemit = payVoucherItem.iterator();
							while(payVoucherItemit.hasNext()){
								VoucherItem payItem = payVoucherItemit.next();
								
								if (payItem.getBusinessitemid() != null &&	
									!"".equals(payItem.getBusinessitemid().trim()) && 
									importPaymentItem.getPaymentitemid() != null &&
									!"".equals(importPaymentItem.getPaymentitemid().trim()) && 
									payItem.getVoucher().getBusinessid().equals(importPaymentItem.getImportPayment().getPaymentid()) &&
									payItem.getBusinessitemid().equals(importPaymentItem.getPaymentitemid())){
										
									VoucherItem voucherItem = getClearVoucherItem("",
											payItem.getRownumber(),
											payItem.getVoucher().getFiyear()," ");

									voucherItem.setBusinessitemid(payItem.getBusinessitemid());
									voucherItem.setVoucher(voucher);
									voucherItemList.add(voucherItem);
									
									itemValue = itemValue.add(payItem.getAmount2());
								}
							}
						}
					}
				}
				
				//看是否需要产生汇损的行项目
				if (strIsSHow.equals("Y")){
					subtractVlaue = billValue.subtract(itemValue);
					
					if (new BigDecimal("0").compareTo(subtractVlaue) != 0){
						VoucherItem supplierVoucherItem = getProfitAndLossVoucherItem(
								importPayment,subtractVlaue,"1");
						supplierVoucherItem.setRownumber("1");
						supplierVoucherItem.setVoucher(voucher);
						voucherItemList.add(supplierVoucherItem);
						
						VoucherItem profOrLossVoucherItem = getProfitAndLossVoucherItem(
								importPayment,subtractVlaue,"2");
						profOrLossVoucherItem.setRownumber("2");
						profOrLossVoucherItem.setVoucher(voucher);
						voucherItemList.add(profOrLossVoucherItem);
					}
				}
				
				voucher.setVoucherItem(voucherItemList);		
			}
			
			this.voucherHibernateDao.save(clearVoucher);
		}
	}
	
	/**
	 * 得到海外代付短期外汇借款清帐
	 * @param importPayment
	 * @param borrowVoucher
	 * @param payVoucher
	 * @param strClass
	 * @param strSubject
	 * @param strBussinessType
	 * @return
	 */
	public List<Voucher> getOverPayLoanClearVendor(ImportPayment importPayment,
			Voucher borrowVoucher,
			Voucher payVoucher,
			String strClass,
			String strSubject,
			String strBussinessType){
		List<Voucher> voucherList = new ArrayList<Voucher>();
		//得到借款金额本位币
		List<ClearVoucherItemStruct> clearVoucherItemStructList = new ArrayList<ClearVoucherItemStruct>();
		String bukrs = getCompanyIDByDeptID(importPayment.getDept_id());
		Set<VoucherItem> borrowVoucherItem = borrowVoucher.getVoucherItem();
		Iterator<VoucherItem> borrowVoucherItemit = borrowVoucherItem.iterator();
		while(borrowVoucherItemit.hasNext()){
			VoucherItem borrowItem = borrowVoucherItemit.next();
			
			if (borrowItem.getCheckcode().equals("50") &&
				borrowItem.getVoucher().getVoucherclass().equals(strBussinessType) &&
				strSubject.indexOf(borrowItem.getSubject()) != -1){
				ClearVoucherItemStruct clearVoucherItemStruct = new ClearVoucherItemStruct();
				
				clearVoucherItemStruct.setVoucherno(borrowItem.getVoucher().getVoucherno());
				clearVoucherItemStruct.setRowno(borrowItem.getRownumber());
				clearVoucherItemStruct.setYear(borrowItem.getVoucher().getFiyear());
				clearVoucherItemStruct.setBwbje(borrowItem.getAmount2());
				clearVoucherItemStruct.setSubject(borrowItem.getSubject());
				clearVoucherItemStruct.setSubjectdescribe(borrowItem.getSubjectdescribe());
				
				clearVoucherItemStructList.add(clearVoucherItemStruct);
			}
		}
		
		for (int j = 0;j<clearVoucherItemStructList.size();j++){
			ClearVoucherItemStruct clearVoucherItemStruct = 
				(ClearVoucherItemStruct) clearVoucherItemStructList.get(j);
			
			Voucher voucher = new Voucher();
			//凭证抬头
			voucher = getClearVoucher(importPayment,strClass);
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
					clearVoucherItemStruct.getYear(),borrowVoucher.getVoucherid());
			
			voucherItem.setVoucher(voucher);
			voucherItemList.add(voucherItem);
			
			itemValue = clearVoucherItemStruct.getBwbje();
			

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
					
					billValue = payItem.getAmount2();
				}
			}
			
			subtractVlaue = itemValue.subtract(billValue);
			
			if (new BigDecimal("0").compareTo(subtractVlaue) != 0){
				VoucherItem supplierVoucherItem = new VoucherItem();
				//科目
				supplierVoucherItem.setSubject(clearVoucherItemStruct.getSubject());
				//科目说明
				supplierVoucherItem.setSubjectdescribe(clearVoucherItemStruct.getSubjectdescribe());
				
				if (subtractVlaue.signum() == 1){
					//记帐码
					supplierVoucherItem.setCheckcode("50");
				}else{
					//记帐码
					supplierVoucherItem.setCheckcode("40");
				}
				//货币
				supplierVoucherItem.setCurrency(importPayment.getFactcurrency());
				//货币金额
				supplierVoucherItem.setAmount(new BigDecimal("0"));
				//本位币金额
				supplierVoucherItem.setAmount2(subtractVlaue.abs());
				//部门
				supplierVoucherItem.setDepid(this.sysDeptJdbcDao.getDeptCodeById(importPayment.getDept_id()));
				//文本
				supplierVoucherItem.setText(importPayment.getText());
				
				supplierVoucherItem.setVoucher(voucher);
				voucherItemList.add(supplierVoucherItem);
				
				VoucherItem profOrLossVoucherItem = new VoucherItem();
				
				if (subtractVlaue.signum() == 1){
					//记帐码
					profOrLossVoucherItem.setCheckcode("50");
					String subjectname = this.customerRefundItemJdbcDao.getSubjectNameById("6603050402",bukrs);
					//科目
					voucherItem.setSubject("6603050402");
					//科目说明
					voucherItem.setSubjectdescribe(subjectname);
					//统驭项目
					voucherItem.setControlaccount("6603050402");
					//统驭科目说明
					voucherItem.setCaremark(subjectname);
				}else{
					//记帐码
					profOrLossVoucherItem.setCheckcode("40");
					String subjectname = this.customerRefundItemJdbcDao.getSubjectNameById("6603050401",bukrs);
					//科目
					voucherItem.setSubject("6603050401");
					//科目说明
					voucherItem.setSubjectdescribe(subjectname);
					//统驭项目
					voucherItem.setControlaccount("6603050401");
					//统驭科目说明
					voucherItem.setCaremark(subjectname);
				}
				
				//货币
				profOrLossVoucherItem.setCurrency(importPayment.getFactcurrency());
				//货币金额
				profOrLossVoucherItem.setAmount(new BigDecimal("0"));
				//本位币金额
				profOrLossVoucherItem.setAmount2(subtractVlaue.abs());
				//部门
				profOrLossVoucherItem.setDepid(this.sysDeptJdbcDao.getDeptCodeById(importPayment.getDept_id()));
				//文本
				profOrLossVoucherItem.setText(importPayment.getText());
				
				profOrLossVoucherItem.setVoucher(voucher);
				voucherItemList.add(profOrLossVoucherItem);
			}
			
			
			voucher.setVoucherItem(voucherItemList);
			
			voucherList.add(voucher);
		}
		
		return voucherList;
	}
	
	/**
	 * 得到短期借款清帐凭证
	 * @param importPayment
	 * @param strClass
	 * @return
	 */
	public List<Voucher> getReturnLoanClearVendor(ImportPayment importPayment,
			Voucher payVoucher,
			String strClass,
			String strSubject,
			String strBussinessType){
		
		List<Voucher> voucherList = new ArrayList<Voucher>();
		String bukrs = getCompanyIDByDeptID(importPayment.getDept_id());
		//得到借款金额本位币
		List<ClearVoucherItemStruct> clearVoucherItemStructList = 
			this.vendorTitleJdbcDao.getLoanItemInfo(importPayment.getPaymentid(),"50",strSubject,strBussinessType);
		
		for (int j = 0;j<clearVoucherItemStructList.size();j++){
			ClearVoucherItemStruct clearVoucherItemStruct = 
				(ClearVoucherItemStruct) clearVoucherItemStructList.get(j);
			
			Voucher voucher = new Voucher();
			//凭证抬头
			voucher = getClearVoucher(importPayment,strClass);
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
				this.vendorTitleJdbcDao.getLoanItemInfo(importPayment.getPaymentid(),"40",strSubject,"3");
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
				supplierVoucherItem.setCurrency(importPayment.getFactcurrency());
				//货币金额
				supplierVoucherItem.setAmount(new BigDecimal("0"));
				//本位币金额
				supplierVoucherItem.setAmount2(subtractVlaue.abs());
				//部门
				supplierVoucherItem.setDepid(importPayment.getRedocarybc());
				//文本
				supplierVoucherItem.setText(importPayment.getText());
				
				supplierVoucherItem.setVoucher(voucher);
				voucherItemList.add(supplierVoucherItem);
				
				VoucherItem profOrLossVoucherItem = new VoucherItem();
				
				if(importPayment.getRedocarybc().equals("2199")){
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
				profOrLossVoucherItem.setCurrency(importPayment.getFactcurrency());
				//货币金额
				profOrLossVoucherItem.setAmount(new BigDecimal("0"));
				//本位币金额
				profOrLossVoucherItem.setAmount2(subtractVlaue.abs());
				//部门
				profOrLossVoucherItem.setDepid(importPayment.getRedocarybc());
				//文本
				profOrLossVoucherItem.setText(importPayment.getText());
				
				profOrLossVoucherItem.setVoucher(voucher);
				voucherItemList.add(profOrLossVoucherItem);
			}
			
			
			voucher.setVoucherItem(voucherItemList);
			
			voucherList.add(voucher);
		}
		
		return voucherList;
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
		
		String strHql = "from VendorTitle a where a.lifnr = '" + lifnr + "' and a.lixiang in ("+strMiddleProjectList+")  and a.iscleared = '0' order by a.budat";
		
		VendorTitleList = this.vendorTitleHibernateDao.find(strHql);
		
		return VendorTitleList;
	}
	
	/**
	 * 把取出来的发票整理到发票清帐表中
	 * @param cntractVendorTitleList
	 * @param projectVendorTitleList
	 * @return
	 */
	public List<ImportPaymentCbill> dealPaymentCbillList(List<VendorTitle> cntractVendorTitleList,List<VendorTitle> projectVendorTitleList){
		List<ImportPaymentCbill> importPaymentCbillList = new ArrayList<ImportPaymentCbill>();
		List<ImportPaymentCbill> projectPaymentCbillList = new ArrayList<ImportPaymentCbill>();
		
		//把合同取出的发票放到LIST中
		for (int i=0;i<cntractVendorTitleList.size();i++){
			ImportPaymentCbill importPaymentCbill = new ImportPaymentCbill();
			VendorTitle vendorTitle = (VendorTitle)cntractVendorTitleList.get(i);
			
			importPaymentCbill.setBillid(vendorTitle.getVendortitleid());
			importPaymentCbill.setBillno(vendorTitle.getInvoice());
			importPaymentCbill.setProject_no(vendorTitle.getLixiang());
			importPaymentCbill.setContract_no(vendorTitle.getVerkf());
			importPaymentCbill.setVoucherno(vendorTitle.getBelnr());
			String strPageContract = this.importPayMentCBillJdbcDao.getPruchasePageContract(importPaymentCbill.getContract_no());
			importPaymentCbill.setOld_contract_no(strPageContract);
			importPaymentCbill.setSap_order_no(vendorTitle.getEbeln());
			importPaymentCbill.setCurrency(vendorTitle.getWaers());
			//应付款金额
			importPaymentCbill.setPayableamount(vendorTitle.getDmbtr());
			//应付款日期
			importPaymentCbill.setPayabledate(vendorTitle.getYfkr());
			//已付金额
			//BigDecimal Paidamount = this.importPayMentCBillJdbcDao.getBillPaidamount(importPaymentCbill.getBillid());
			BigDecimal Paidamount = this.vendorTitleJdbcDao.getSumClearAmount(importPaymentCbill.getBillno(), BusinessState.ALL_BILLCLEAR_PAIDUP, BusinessState.ALL_SINGLECLEAR_PAIDUP, BusinessState.ALL_PAYMENT_PAIDUP);
			importPaymentCbill.setPaidamount(Paidamount);
			//未付金额
			BigDecimal Unpaidamount = importPaymentCbill.getPayableamount().subtract(Paidamount);
			importPaymentCbill.setUnpaidamount(Unpaidamount);
			//在途金额
			//BigDecimal Onroadamount = this.importPayMentCBillJdbcDao.getOnroadamount(importPaymentCbill.getBillid());
			BigDecimal Onroadamount = this.vendorTitleJdbcDao.getSumClearAmount(importPaymentCbill.getBillno(), BusinessState.ALL_BILLCLEAR_ONROAD, BusinessState.ALL_SINGLECLEAR_ONROAD, BusinessState.ALL_PAYMENT_ONROAD);
			importPaymentCbill.setOnroadamount(Onroadamount);
			//清帐金额
			importPaymentCbill.setClearamount2(new BigDecimal("0.0"));
			importPaymentCbill.setAccountdate(vendorTitle.getBudat());
			importPaymentCbill.setText(vendorTitle.getBktxt());
			
			// "未付金额"必须大于"在途金额"，否则不能添加
			if (Unpaidamount.compareTo(Onroadamount) == 1){
				importPaymentCbillList.add(importPaymentCbill);
			}
		}
		
		//处理立项时的发票因为要判断发票号是否存在
		for (int j=0;j<projectVendorTitleList.size();j++){
			VendorTitle vendorTitle = (VendorTitle)projectVendorTitleList.get(j);
			boolean isExists = false;
			
			for (int k=0;k<importPaymentCbillList.size();k++){
				ImportPaymentCbill middlePaymentCbill = new ImportPaymentCbill();
				middlePaymentCbill = (ImportPaymentCbill)importPaymentCbillList.get(k);
				
				if (middlePaymentCbill.getBillno() == vendorTitle.getInvoice()){
					isExists = true;
					break;
				}
			}
			
			if (isExists == false){
				ImportPaymentCbill importPaymentCbill = new ImportPaymentCbill();
				
				importPaymentCbill.setBillid(vendorTitle.getVendortitleid());
				importPaymentCbill.setBillno(vendorTitle.getInvoice());
				importPaymentCbill.setProject_no(vendorTitle.getLixiang());
				importPaymentCbill.setContract_no(vendorTitle.getVerkf());
				importPaymentCbill.setVoucherno(vendorTitle.getBelnr());
				importPaymentCbill.setOld_contract_no("");
				importPaymentCbill.setSap_order_no(vendorTitle.getEbeln());
				importPaymentCbill.setCurrency(vendorTitle.getWaers());
				//应付款金额
				importPaymentCbill.setPayableamount(vendorTitle.getDmbtr());
				//应付款日期
				importPaymentCbill.setPayabledate(vendorTitle.getYfkr());
				//已付金额
				//BigDecimal Paidamount = this.importPayMentCBillJdbcDao.getBillPaidamount(importPaymentCbill.getBillid());
				BigDecimal Paidamount = this.vendorTitleJdbcDao.getSumClearAmount(importPaymentCbill.getBillno(), BusinessState.ALL_BILLCLEAR_PAIDUP, BusinessState.ALL_SINGLECLEAR_PAIDUP, BusinessState.ALL_PAYMENT_PAIDUP);
				importPaymentCbill.setPaidamount(Paidamount);
				//未付金额
				BigDecimal Unpaidamount = importPaymentCbill.getPayableamount().subtract(Paidamount);
				importPaymentCbill.setUnpaidamount(Unpaidamount);
				//在途金额
				//BigDecimal Onroadamount = this.importPayMentCBillJdbcDao.getOnroadamount(importPaymentCbill.getBillid());
				BigDecimal Onroadamount = this.vendorTitleJdbcDao.getSumClearAmount(importPaymentCbill.getBillno(), BusinessState.ALL_BILLCLEAR_ONROAD, BusinessState.ALL_SINGLECLEAR_ONROAD, BusinessState.ALL_PAYMENT_ONROAD);
				importPaymentCbill.setOnroadamount(Onroadamount);
				//清帐金额
				importPaymentCbill.setClearamount2(new BigDecimal("0.0"));
				importPaymentCbill.setAccountdate(vendorTitle.getBudat());
				importPaymentCbill.setText(vendorTitle.getBktxt());
				
				// "未付金额"必须大于"在途金额"，否则不能添加
				if (Unpaidamount.compareTo(Onroadamount) == 1){
					projectPaymentCbillList.add(importPaymentCbill);
				}
			}
		}
		
		//把立项的发票取到列表中
		if (projectPaymentCbillList != null && projectPaymentCbillList.size() > 0){
			for (int l=0;l<projectPaymentCbillList.size();l++){
				ImportPaymentCbill importPaymentCbill = new ImportPaymentCbill();
				importPaymentCbill = (ImportPaymentCbill) projectPaymentCbillList.get(l);
				
				importPaymentCbillList.add(importPaymentCbill);
			}
		}
		
		return importPaymentCbillList;
	}
	
	/**
	 * 删除付款单的所有票的信息
	 * @param strPayMentId
	 */
	public void _deletePayBill(String strPayMentId){
		this.importPayMentCBillJdbcDao._deletePayBill(strPayMentId);
	}
	
	/**
	 * 增加发票信息
	 * @param strBillNoList
	 * @return
	 */
	public List<ImportPaymentCbill> _addBillInfo(String strBillNoList){
		List<ImportPaymentCbill> importPaymentCbillList = new ArrayList<ImportPaymentCbill>();
		
		//取的发票信息
		List<VendorTitle> VendorTitleList = new ArrayList<VendorTitle>();
		String strMiddleBillList = strBillNoList.substring(0, strBillNoList.length()-1);
		strMiddleBillList = "'" + strMiddleBillList.replace("|", "','") + "'";
		String strHql = "from VendorTitle a where a.invoice in ("+strMiddleBillList+") and a.iscleared = '0' order by a.budat";
		VendorTitleList = this.vendorTitleHibernateDao.find(strHql);
		
		//把合同取出的发票放到LIST中
		for (int i=0;i<VendorTitleList.size();i++){
			ImportPaymentCbill importPaymentCbill = new ImportPaymentCbill();
			VendorTitle vendorTitle = (VendorTitle)VendorTitleList.get(i);
			
			importPaymentCbill.setBillid(vendorTitle.getVendortitleid());
			importPaymentCbill.setBillno(vendorTitle.getInvoice());
			importPaymentCbill.setProject_no(vendorTitle.getLixiang());
			importPaymentCbill.setContract_no(vendorTitle.getVerkf());
			importPaymentCbill.setVoucherno(vendorTitle.getBelnr());
			String strPageContract = this.importPayMentCBillJdbcDao.getPruchasePageContract(importPaymentCbill.getContract_no());
			importPaymentCbill.setOld_contract_no(strPageContract);
			importPaymentCbill.setSap_order_no(vendorTitle.getEbeln());
			importPaymentCbill.setCurrency(vendorTitle.getWaers());
			//应付款金额
			importPaymentCbill.setPayableamount(vendorTitle.getDmbtr());
			//应付款日期
			importPaymentCbill.setPayabledate(vendorTitle.getYfkr());
			//已付金额
			BigDecimal Paidamount = this.vendorTitleJdbcDao.getSumClearAmount(importPaymentCbill.getBillno(), BusinessState.ALL_BILLCLEAR_PAIDUP, BusinessState.ALL_SINGLECLEAR_PAIDUP, BusinessState.ALL_PAYMENT_PAIDUP);
			importPaymentCbill.setPaidamount(Paidamount);
			//未付金额
			BigDecimal Unpaidamount = importPaymentCbill.getPayableamount().subtract(Paidamount);
			importPaymentCbill.setUnpaidamount(Unpaidamount);
			//在途金额
			BigDecimal Onroadamount = this.vendorTitleJdbcDao.getSumClearAmount(importPaymentCbill.getBillno(), BusinessState.ALL_BILLCLEAR_ONROAD, BusinessState.ALL_SINGLECLEAR_ONROAD, BusinessState.ALL_PAYMENT_ONROAD);
			importPaymentCbill.setOnroadamount(Onroadamount);
			//清帐金额
			importPaymentCbill.setClearamount2(new BigDecimal("0.0"));
			importPaymentCbill.setAccountdate(vendorTitle.getBudat());
			importPaymentCbill.setText(vendorTitle.getBktxt());
			
			// "未付金额"必须大于"在途金额"，否则不能添加
			if (Unpaidamount.compareTo(Onroadamount) == 1){
				importPaymentCbillList.add(importPaymentCbill);
			}
		}
		
		return importPaymentCbillList;
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
	public Set<VoucherItem> getSettSubjVoucherItem(ImportPayment importPayment){
		Set<VoucherItem> voucherItemList = new HashSet<VoucherItem>();
		BigDecimal amount = new BigDecimal(0);
		BigDecimal standardAmount = new BigDecimal(0);
		ImportSettSubj settleSubjectPay = importPayment.getImportSettSubj();
		String bukrs = getCompanyIDByDeptID(importPayment.getDept_id());
		
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
				voucherItem.setCurrency(settleSubjectPay.getImportPayment().getFactcurrency());
				//货币金额
				voucherItem.setAmount(settleSubjectPay.getAmount1());
				//本位币金额
				voucherItem.setAmount2(settleSubjectPay.getStandardamount1());
				//部门
				voucherItem.setDepid(settleSubjectPay.getDepid1());
				voucherItem.setGsber(settleSubjectPay.getDepid1());
				//文本
				voucherItem.setText(settleSubjectPay.getImportPayment().getText());
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
				voucherItem.setCurrency(settleSubjectPay.getImportPayment().getFactcurrency());
				//货币金额
				voucherItem.setAmount(settleSubjectPay.getAmount2());
				//本位币金额
				voucherItem.setAmount2(settleSubjectPay.getStandardamount2());
				//部门
				voucherItem.setDepid(settleSubjectPay.getDepid2());
				voucherItem.setGsber(settleSubjectPay.getDepid2());
				//文本
				voucherItem.setText(settleSubjectPay.getImportPayment().getText());
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
				voucherItem.setCurrency(settleSubjectPay.getImportPayment().getFactcurrency());
				//货币金额
				voucherItem.setAmount(settleSubjectPay.getAmount3());
				//本位币金额
				voucherItem.setAmount2(settleSubjectPay.getStandardamount3());
				//部门
				voucherItem.setDepid(settleSubjectPay.getDepid3());
				voucherItem.setGsber(settleSubjectPay.getDepid3());
				//文本
				voucherItem.setText(settleSubjectPay.getImportPayment().getText());
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
				voucherItem.setCurrency(settleSubjectPay.getImportPayment().getFactcurrency());
				//货币金额
				voucherItem.setAmount(settleSubjectPay.getAmount4());
				//本位币金额
				voucherItem.setAmount2(settleSubjectPay.getStandardamount4());
				//部门
				voucherItem.setDepid(settleSubjectPay.getDepid4());
				voucherItem.setGsber(settleSubjectPay.getDepid4());
				//文本
				voucherItem.setText(settleSubjectPay.getImportPayment().getText());
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
	public Set<VoucherItem> getFundFlowVoucherItem(ImportPayment importPayment){
		Set<VoucherItem> voucherItemList = new HashSet<VoucherItem>();
		ImportFundFlow fundFlowPay = importPayment.getImportFundFlow();
		String bukrs = getCompanyIDByDeptID(importPayment.getDept_id());
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
				voucherItem.setCurrency(fundFlowPay.getImportPayment().getFactcurrency());
				//货币金额
				voucherItem.setAmount(fundFlowPay.getAmount1());
				//本位币金额
				voucherItem.setAmount2(fundFlowPay.getStandardamount1());
				//部门
				voucherItem.setDepid(fundFlowPay.getDepid1());
				voucherItem.setGsber(fundFlowPay.getDepid1());
				//文本
				voucherItem.setText(fundFlowPay.getImportPayment().getText());
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
				voucherItem.setCurrency(fundFlowPay.getImportPayment().getFactcurrency());
				//货币金额
				voucherItem.setAmount(fundFlowPay.getAmount2());
				//本位币金额
				voucherItem.setAmount2(fundFlowPay.getStandardamount2());
				//部门
				voucherItem.setDepid(fundFlowPay.getDepid2());
				voucherItem.setGsber(fundFlowPay.getDepid2());
				//文本
				voucherItem.setText(fundFlowPay.getImportPayment().getText());
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
				voucherItem.setCurrency(fundFlowPay.getImportPayment().getFactcurrency());
				//货币金额
				voucherItem.setAmount(fundFlowPay.getAmount3());
				//本位币金额
				voucherItem.setAmount2(fundFlowPay.getStandardamount3());
				//部门
				voucherItem.setDepid(fundFlowPay.getDepid3());
				voucherItem.setGsber(fundFlowPay.getDepid3());
				//文本
				voucherItem.setText(fundFlowPay.getImportPayment().getText());
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
	public Voucher getVoucher(ImportPayment importPayment,String strClass){
		Voucher voucher = new Voucher();
		
		UserContext userContext = UserContextHolder.getLocalUserContext()
		.getUserContext();
		
		//业务编号
		voucher.setBusinessid(importPayment.getPaymentid());
		//业务类型
		voucher.setBusinesstype("02");
		//过账日期
		voucher.setCheckdate(importPayment.getAccountdate().replace("-", ""));
		//公司代码
		String bukrs = getCompanyIDByDeptID(importPayment.getDept_id());
		voucher.setCompanycode(bukrs); //公司代码
		//货币
		voucher.setCurrency(importPayment.getFactcurrency());
		//汇率
		voucher.setExchangerate(importPayment.getCloseexchangerat());
		//会计期间
		voucher.setFiperiod(importPayment.getAccountdate().substring(5, 7));
		//会计年度
		voucher.setFiyear(importPayment.getAccountdate().substring(0, 4));
		//输入日期
		SimpleDateFormat  dateFormat=new SimpleDateFormat ("yyyyMMdd");
		voucher.setImportdate(dateFormat.format(new Date()));
		//输入者
		voucher.setImporter(userContext.getSysUser().getUserName());
		//预制者
		voucher.setPreparer(userContext.getSysUser().getUserName());
		//凭证日期
		voucher.setVoucherdate(importPayment.getVoucherdate().replace("-", ""));
		//凭证抬头文本
		voucher.setVouchertext(importPayment.getText());
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
	public List<String> cnySaveVoucher(ImportPayment importPayment){
		List<String> retList = new ArrayList<String>();
		String bukrs = getCompanyIDByDeptID(importPayment.getDept_id());
		
		//this.voucherJdbcDao.deleteVoucherByBusinessidAndClass(importPayment.getPaymentid(), "1");
		
		//凭证抬头
		Voucher voucher = new Voucher();
		//凭证行项目
		Set<VoucherItem> voucherItemList = new HashSet<VoucherItem>();
		
		voucher = this.getVoucher(importPayment, "1");
		
		
		int rowNo = 0;
		//******************供应商行项目凭证行项目**********************************//
		Iterator<ImportPaymentItem> PayItemItemit = importPayment.getImportPaymentItem() .iterator();		
		while(PayItemItemit.hasNext()){
			rowNo = rowNo + 1;
			ImportPaymentItem importPaymentItem = PayItemItemit.next();
			
			VoucherItem voucherItem = new VoucherItem();
			voucherItem.setVoucher(voucher);
			//客户记账码
			voucherItem.setCheckcode("21");
			//特殊G/L标识
			voucherItem.setGlflag("");
			//科目
			voucherItem.setSubject(importPayment.getSupplier());
			//科目说明
			String Subjectdescribe = this.customerRefundItemJdbcDao.getSupplierDescByCustomerId(importPayment.getSupplier(),bukrs);
			voucherItem.setSubjectdescribe(Subjectdescribe);
			//货币
			voucherItem.setCurrency(importPayment.getFactcurrency());
			//货币金额
			voucherItem.setAmount(importPaymentItem.getAssignamount());
			//本位币金额
			voucherItem.setAmount2(importPaymentItem.getAssignamount2());
			//部门
			voucherItem.setDepid(this.sysDeptJdbcDao.getDeptCodeById(importPayment.getDept_id()));
			//文本
			voucherItem.setText(importPayment.getText());
			//现金流项目
			voucherItem.setCashflowitem("");
			//反记账标识
			voucherItem.setUncheckflag("");
			String subject = this.customerRefundItemJdbcDao.getSubjectBySuppliers(importPayment.getSupplier(),bukrs);
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
			voucherItem.setBusinessitemid(importPaymentItem.getPaymentitemid());
			
			voucherItemList.add(voucherItem);
		}
		//******************供应商行项目凭证行项目**********************************//
		
		//******************付款银行行项目凭证行项目*******************************//
		Iterator<ImportPayBankItem> bankItemit = importPayment.getImportPayBankItem().iterator();		
		while(bankItemit.hasNext()){
			rowNo = rowNo + 1;
			ImportPayBankItem importPayBankItem = bankItemit.next();
			
			VoucherItem BankVoucherItem = new VoucherItem();
			BankVoucherItem.setVoucher(voucher);
			//客户记账码
			BankVoucherItem.setCheckcode("50");
			//特殊G/L标识
			BankVoucherItem.setGlflag("");
			//科目
			BankVoucherItem.setSubject(importPayBankItem.getPaybanksubject());
			//科目说明
			BankVoucherItem.setSubjectdescribe(importPayBankItem.getPaybankname());
			//货币
			BankVoucherItem.setCurrency(importPayment.getFactcurrency());
			//货币金额
			BankVoucherItem.setAmount(importPayBankItem.getPayamount());
			//本位币金额
			BankVoucherItem.setAmount2(importPayBankItem.getPayamount2());
			//部门
			BankVoucherItem.setDepid(this.sysDeptJdbcDao.getDeptCodeById(importPayment.getDept_id()));
			//文本
			BankVoucherItem.setText(importPayment.getText());
			//现金流项目
			BankVoucherItem.setCashflowitem(importPayBankItem.getCashflowitem());
			//反记账标识
			BankVoucherItem.setUncheckflag("");
			String subject = this.customerRefundItemJdbcDao.getSubjectBySuppliers(importPayment.getSupplier(),bukrs);
			subject = this.customerRefundItemJdbcDao.getSkont(BankVoucherItem,subject);
			//统驭项目
			BankVoucherItem.setControlaccount(subject);
			//统驭科目说明
			BankVoucherItem.setCaremark(this.customerRefundItemJdbcDao.getSubjectNameById(subject,bukrs));
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
		//结算科目凭证行项目
		Set<VoucherItem> SettlesubjectVoucherItemList = new HashSet<VoucherItem>();
		
		SettlesubjectVoucherItemList = this.getSettSubjVoucherItem(importPayment);
		
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
		//结算科目凭证行项目
		Set<VoucherItem> FundFlowVoucherItemList = new HashSet<VoucherItem>();
		
		FundFlowVoucherItemList = this.getFundFlowVoucherItem(importPayment);
		
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
	 * 外币且付款货币和发票货币一致时模拟凭证
	 * @param importPayment
	 * @return
	 */
	public List<String> sameForeignCurrencySaveVoucher(ImportPayment importPayment){
		List<String> retList = new ArrayList<String>();
		
		//this.voucherJdbcDao.deleteVoucherByBusinessidAndClass(importPayment.getPaymentid(), "1");
		String bukrs = getCompanyIDByDeptID(importPayment.getDept_id());
		//凭证抬头
		Voucher voucher = new Voucher();
		//凭证行项目
		Set<VoucherItem> voucherItemList = new HashSet<VoucherItem>();
		
		voucher = this.getVoucher(importPayment, "1");
		
		
		int rowNo = 0;
		//******************供应商行项目凭证行项目**********************************//
		Iterator<ImportPaymentItem> PayItemItemit = importPayment.getImportPaymentItem() .iterator();		
		while(PayItemItemit.hasNext()){
			rowNo = rowNo + 1;
			ImportPaymentItem importPaymentItem = PayItemItemit.next();
			
			VoucherItem voucherItem = new VoucherItem();
			voucherItem.setVoucher(voucher);
			//客户记账码
			voucherItem.setCheckcode("21");
			//特殊G/L标识
			voucherItem.setGlflag("");
			//科目
			voucherItem.setSubject(importPayment.getSupplier());
			//科目说明
			String Subjectdescribe = this.customerRefundItemJdbcDao.getSupplierDescByCustomerId(importPayment.getSupplier(),bukrs);
			voucherItem.setSubjectdescribe(Subjectdescribe);
			//货币
			voucherItem.setCurrency(importPayment.getFactcurrency());
			//货币金额
			voucherItem.setAmount(importPaymentItem.getAssignamount());
			//本位币金额
			voucherItem.setAmount2(importPaymentItem.getAssignamount2());
			//部门
			voucherItem.setDepid(this.sysDeptJdbcDao.getDeptCodeById(importPayment.getDept_id()));
			//文本
			voucherItem.setText(importPayment.getText());
			//现金流项目
			voucherItem.setCashflowitem("");
			//反记账标识
			voucherItem.setUncheckflag("");
			String subject = this.customerRefundItemJdbcDao.getSubjectBySuppliers(importPayment.getSupplier(),bukrs);
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
			voucherItem.setBusinessitemid(importPaymentItem.getPaymentitemid());
			
			voucherItemList.add(voucherItem);
		}
		//******************供应商行项目凭证行项目**********************************//
		
		//******************付款银行行项目凭证行项目*******************************//
		Iterator<ImportPayBankItem> bankItemit = importPayment.getImportPayBankItem().iterator();		
		while(bankItemit.hasNext()){
			rowNo = rowNo + 1;
			ImportPayBankItem importPayBankItem = bankItemit.next();
			
			VoucherItem BankVoucherItem = new VoucherItem();
			BankVoucherItem.setVoucher(voucher);
			//客户记账码
			BankVoucherItem.setCheckcode("50");
			//特殊G/L标识
			BankVoucherItem.setGlflag("");
			//科目
			BankVoucherItem.setSubject(importPayBankItem.getPaybanksubject());
			//科目说明
			BankVoucherItem.setSubjectdescribe(importPayBankItem.getPaybankname());
			//货币
			BankVoucherItem.setCurrency(importPayment.getFactcurrency());
			//货币金额
			BankVoucherItem.setAmount(importPayBankItem.getPayamount());
			//本位币金额
			BankVoucherItem.setAmount2(importPayBankItem.getPayamount2());
			//部门
			BankVoucherItem.setDepid(this.sysDeptJdbcDao.getDeptCodeById(importPayment.getDept_id()));
			//文本
			BankVoucherItem.setText(importPayment.getText());
			//现金流项目
			BankVoucherItem.setCashflowitem(importPayBankItem.getCashflowitem());
			//反记账标识
			BankVoucherItem.setUncheckflag("");
			//String subject = this.customerRefundItemJdbcDao.getSubjectBySuppliers(importPayment.getSupplier(),bukrs);
			//subject = this.customerRefundItemJdbcDao.getSkont(BankVoucherItem,subject);
			//统驭项目
			BankVoucherItem.setControlaccount(importPayBankItem.getPaybanksubject());
			//统驭科目说明
			BankVoucherItem.setCaremark(importPayBankItem.getPaybankname());  //this.customerRefundItemJdbcDao.getSubjectNameById(subject,bukrs)
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
		
		SettlesubjectVoucherItemList = this.getSettSubjVoucherItem(importPayment);
		
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
		
		FundFlowVoucherItemList = this.getFundFlowVoucherItem(importPayment);
		
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
		
		//*************处理清帐凭证*****************************
		if (checkIsClear(importPayment) == false){
			//this.genProfOrLossVoucher(importPayment);
		}
		//*************处理清帐凭证*****************************
		
		return retList;
	}
	
	/**
	 * 外币并且付款货币和发票货币不一致时模拟凭证
	 * @param importPayment
	 * @return
	 */
	public List<String> differForeignCurrencySaveVoucher(ImportPayment importPayment){
		List<String> retList = new ArrayList<String>();
		
		//this.voucherJdbcDao.deleteVoucherByBusinessidAndClass(importPayment.getPaymentid(), "5");
		String bukrs = getCompanyIDByDeptID(importPayment.getDept_id());
		//凭证抬头
		Voucher voucher = new Voucher();
		//凭证行项目
		Set<VoucherItem> voucherItemList = new HashSet<VoucherItem>();
		voucher = this.getVoucher(importPayment, "5");
		
		int rowNo = 1;
		
		//******************付款银行行项目凭证行项目*******************************//
		BigDecimal bankAmount2 = new BigDecimal("0");
		BigDecimal bankAmount = new BigDecimal("0");
		Iterator<ImportPayBankItem> bankItemit = importPayment.getImportPayBankItem().iterator();		
		while(bankItemit.hasNext()){
			rowNo = rowNo + 1;
			ImportPayBankItem importPayBankItem = bankItemit.next();
			
			VoucherItem BankVoucherItem = new VoucherItem();
			BankVoucherItem.setVoucher(voucher);
			//客户记账码
			BankVoucherItem.setCheckcode("50");
			//特殊G/L标识
			BankVoucherItem.setGlflag("");
			//科目
			BankVoucherItem.setSubject(importPayBankItem.getPaybanksubject());
			//科目说明
			BankVoucherItem.setSubjectdescribe(importPayBankItem.getPaybankname());
			//货币
			BankVoucherItem.setCurrency(importPayment.getFactcurrency());
			//货币金额
			BankVoucherItem.setAmount(importPayBankItem.getPayamount());
			bankAmount = bankAmount.add(importPayBankItem.getPayamount());
			//本位币金额
			BankVoucherItem.setAmount2(importPayBankItem.getPayamount2());
			bankAmount2 = bankAmount2.add(importPayBankItem.getPayamount2());
			//部门
			BankVoucherItem.setDepid(this.sysDeptJdbcDao.getDeptCodeById(importPayment.getDept_id()));
			//文本
			BankVoucherItem.setText(importPayment.getText());
			//现金流项目
			BankVoucherItem.setCashflowitem(importPayBankItem.getCashflowitem());
			//反记账标识
			BankVoucherItem.setUncheckflag("");
			String subject = this.customerRefundItemJdbcDao.getSubjectBySuppliers(importPayment.getSupplier(),bukrs);
			subject = this.customerRefundItemJdbcDao.getSkont(BankVoucherItem,subject);
			//统驭项目
			BankVoucherItem.setControlaccount(subject);
			//统驭科目说明
			BankVoucherItem.setCaremark(this.customerRefundItemJdbcDao.getSubjectNameById(subject,bukrs));
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
		voucherItem.setCurrency(importPayment.getFactcurrency());
		//货币金额
		voucherItem.setAmount(bankAmount);
		//本位币金额
		voucherItem.setAmount2(bankAmount2);
		//部门
		voucherItem.setDepid(this.sysDeptJdbcDao.getDeptCodeById(importPayment.getDept_id()));
		//文本
		voucherItem.setText(importPayment.getText());
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
		
		
		//如果有中转需要取票的币别
		String strCurrency = importPayment.getCurrency();
		if (importPayment.getImportPaymentCbill() != null){
			Iterator<ImportPaymentCbill> importPaymentCbillIteraor = importPayment.getImportPaymentCbill().iterator();
			if(importPaymentCbillIteraor.hasNext()){
				ImportPaymentCbill importPaymentCbill = importPaymentCbillIteraor.next();
				strCurrency = importPaymentCbill.getCurrency();
			}
		}
		//凭证抬头
		Voucher voucher2 = new Voucher();
		//凭证行项目
		Set<VoucherItem> voucherItemList2 = new HashSet<VoucherItem>();
		//this.voucherJdbcDao.deleteVoucherByBusinessidAndClass(importPayment.getPaymentid(), "1");
		voucher2 = this.getVoucher(importPayment, "1");
		voucher2.setCurrency(strCurrency);
		
		rowNo = 0;
		//******************供应商行项目凭证行项目**********************************//
		BigDecimal allAmount = new BigDecimal("0");
		BigDecimal allChangeAmount = new BigDecimal("0");
		Iterator<ImportPaymentItem> PayItemItemit = importPayment.getImportPaymentItem() .iterator();		
		while(PayItemItemit.hasNext()){
			rowNo = rowNo + 1;
			ImportPaymentItem importPaymentItem = PayItemItemit.next();
			
			VoucherItem PaymentItemvoucherItem = new VoucherItem();
			PaymentItemvoucherItem.setVoucher(voucher2);
			//客户记账码
			PaymentItemvoucherItem.setCheckcode("21");
			//特殊G/L标识
			PaymentItemvoucherItem.setGlflag("");
			//科目
			PaymentItemvoucherItem.setSubject(importPayment.getSupplier());
			//科目说明
			String Subjectdescribe = this.customerRefundItemJdbcDao.getSupplierDescByCustomerId(importPayment.getSupplier(),bukrs);
			PaymentItemvoucherItem.setSubjectdescribe(Subjectdescribe);
			//货币
			PaymentItemvoucherItem.setCurrency(strCurrency);
			//货币金额
			PaymentItemvoucherItem.setAmount(importPaymentItem.getAssignamount());
			allChangeAmount = allChangeAmount.add(importPaymentItem.getAssignamount());
			//本位币金额
			PaymentItemvoucherItem.setAmount2(importPaymentItem.getAssignamount2());
			allAmount = allAmount.add(importPaymentItem.getAssignamount2());
			//部门
			PaymentItemvoucherItem.setDepid(this.sysDeptJdbcDao.getDeptCodeById(importPayment.getDept_id()));
			//文本
			PaymentItemvoucherItem.setText(importPayment.getText());
			//现金流项目
			PaymentItemvoucherItem.setCashflowitem("");
			//反记账标识
			PaymentItemvoucherItem.setUncheckflag("");
			String subject = this.customerRefundItemJdbcDao.getSubjectBySuppliers(importPayment.getSupplier(),bukrs);
			subject = this.customerRefundItemJdbcDao.getSkont(PaymentItemvoucherItem,subject);
			//统驭项目
			voucherItem.setControlaccount(subject);
			//统驭科目说明
			voucherItem.setCaremark(this.customerRefundItemJdbcDao.getSubjectNameById(subject,bukrs));
			//销售订单
			PaymentItemvoucherItem.setSalesorder("");
			//销售订单行项目号
			PaymentItemvoucherItem.setOrderrowno("");
			//利润中心
			PaymentItemvoucherItem.setProfitcenter("");
			//成本中心
			PaymentItemvoucherItem.setCostcenter("");
			//借/贷标识符
			PaymentItemvoucherItem.setDebitcredit("S");
			//行号
			PaymentItemvoucherItem.setRownumber(String.valueOf(rowNo));
			//付款行项目信息
			PaymentItemvoucherItem.setBusinessitemid(importPaymentItem.getPaymentitemid());
			
			voucherItemList2.add(PaymentItemvoucherItem);
		}
		//******************供应商行项目凭证行项目**********************************//
		
		//************************应付中转科目**********************************//
		Set<VoucherItem> SettlesubjectVoucherItemList1 = new HashSet<VoucherItem>();
		
		SettlesubjectVoucherItemList1 = this.getSettSubjVoucherItem(importPayment);
		
		Iterator<VoucherItem> Settlesubjectit1 = SettlesubjectVoucherItemList1.iterator();		
		while(Settlesubjectit1.hasNext()){
			VoucherItem setvoucherItem =Settlesubjectit1.next();
			
			if (setvoucherItem.getDebitcredit().equals("S")){
				allChangeAmount = allChangeAmount.add(setvoucherItem.getAmount());
			}
			
			if (setvoucherItem.getDebitcredit().equals("H")){
				allChangeAmount = allChangeAmount.subtract(setvoucherItem.getAmount());
			}
		}
		
		Set<VoucherItem> FundFlowVoucherItemList1 = new HashSet<VoucherItem>();
		
		FundFlowVoucherItemList1 = this.getFundFlowVoucherItem(importPayment);
		
		Iterator<VoucherItem> FundFlowit1 = FundFlowVoucherItemList1.iterator();		
		while(FundFlowit1.hasNext()){
			VoucherItem funvoucherItem =FundFlowit1.next();
			
			if (funvoucherItem.getDebitcredit().equals("S")){
				allChangeAmount = allChangeAmount.add(funvoucherItem.getAmount());
			}
			
			if (funvoucherItem.getDebitcredit().equals("H")){
				allChangeAmount = allChangeAmount.subtract(funvoucherItem.getAmount());
			}
		}
		
		rowNo = rowNo + 1;
		VoucherItem voucherItem2 = new VoucherItem();
		voucherItem2.setVoucher(voucher2);
		//客户记账码
		voucherItem2.setCheckcode("50");
		//特殊G/L标识
		voucherItem2.setGlflag("");
		String subjectdesc = this.customerRefundItemJdbcDao.getSubjectNameById("2202999999",bukrs);
		//科目
		voucherItem2.setSubject("2202999999");
		//科目说明
		voucherItem2.setSubjectdescribe(subjectdesc);
		//货币
		voucherItem2.setCurrency(strCurrency);
		//货币金额
//		voucherItem2.setAmount(allChangeAmount);
		voucherItem2.setAmount(importPayment.getApplyamount());
		//本位币金额
		voucherItem2.setAmount2(bankAmount2);
		//部门
		voucherItem2.setDepid(this.sysDeptJdbcDao.getDeptCodeById(importPayment.getDept_id()));
		//文本
		voucherItem2.setText(importPayment.getText());
		//现金流项目
		voucherItem2.setCashflowitem("");
		//反记账标识
		voucherItem2.setUncheckflag("");
		//统驭项目
		voucherItem2.setControlaccount("2202999999");
		//统驭科目说明
		voucherItem2.setCaremark(subjectdesc);
		//销售订单
		voucherItem2.setSalesorder("");
		//销售订单行项目号
		voucherItem2.setOrderrowno("");
		//利润中心
		voucherItem2.setProfitcenter("");
		//成本中心
		voucherItem2.setCostcenter("");
		//借/贷标识符
		voucherItem2.setDebitcredit("H");
		//行号
		voucherItem2.setRownumber(String.valueOf(rowNo));
		
		voucherItemList2.add(voucherItem2);
		//************************应付中转科目**********************************//
		
		//******************结算科目行项目凭证行项目*******************************//
		Set<VoucherItem> SettlesubjectVoucherItemList = new HashSet<VoucherItem>();
		
		SettlesubjectVoucherItemList = this.getSettSubjVoucherItem(importPayment);

		Iterator<VoucherItem> Settlesubjectit = SettlesubjectVoucherItemList.iterator();		
		while(Settlesubjectit.hasNext()){
			rowNo = rowNo + 1;
			VoucherItem setvoucherItem =Settlesubjectit.next();
			setvoucherItem.setRownumber(String.valueOf(rowNo));
			setvoucherItem.setCurrency(strCurrency);
			setvoucherItem.setVoucher(voucher2);
			
			voucherItemList2.add(setvoucherItem);
		}
		//******************结算科目行项目凭证行项目*******************************//
		
		//******************特殊总帐行项目凭证行项目*******************************//
		//结算科目凭证行项目
		Set<VoucherItem> FundFlowVoucherItemList = new HashSet<VoucherItem>();
		
		FundFlowVoucherItemList = this.getFundFlowVoucherItem(importPayment);
		
		Iterator<VoucherItem> FundFlowit = FundFlowVoucherItemList.iterator();		
		while(FundFlowit.hasNext()){
			rowNo = rowNo + 1;
			VoucherItem funvoucherItem =FundFlowit.next();
			funvoucherItem.setRownumber(String.valueOf(rowNo));
			funvoucherItem.setCurrency(strCurrency);
			funvoucherItem.setVoucher(voucher2);
			
			voucherItemList2.add(funvoucherItem);
		}
		//******************特殊总帐行项目凭证行项目*******************************//
		
		//******************汇兑损益科目号行项目凭证行项目*******************************//
		if (importPayment.getImportSettSubj() != null){
			if (importPayment.getImportSettSubj().getDebtcredit1() != null && 
				"S".equals(importPayment.getImportSettSubj().getDebtcredit1())){
				allAmount = allAmount.add(importPayment.getImportSettSubj().getStandardamount1());
			}
	
			if (importPayment.getImportSettSubj().getDebtcredit1() != null &&
				"H".equals(importPayment.getImportSettSubj().getDebtcredit1())){
				allAmount = allAmount.subtract(importPayment.getImportSettSubj().getStandardamount1());
			}
			
			if (importPayment.getImportSettSubj().getDebtcredit2() != null &&
				"S".equals(importPayment.getImportSettSubj().getDebtcredit2())){
				allAmount = allAmount.add(importPayment.getImportSettSubj().getStandardamount2());
			}
	
			if (importPayment.getImportSettSubj().getDebtcredit2() != null &&
				"H".equals(importPayment.getImportSettSubj().getDebtcredit2())){
				allAmount = allAmount.subtract(importPayment.getImportSettSubj().getStandardamount2());
			}
			
			if (importPayment.getImportSettSubj().getDebtcredit3() != null &&
				"S".equals(importPayment.getImportSettSubj().getDebtcredit3())){
				allAmount = allAmount.add(importPayment.getImportSettSubj().getStandardamount3());
			}
	
			if (importPayment.getImportSettSubj().getDebtcredit3() != null &&
				"H".equals(importPayment.getImportSettSubj().getDebtcredit3())){
				allAmount = allAmount.subtract(importPayment.getImportSettSubj().getStandardamount3());
			}
			
			if (importPayment.getImportSettSubj().getDebtcredit4() != null && 
				"S".equals(importPayment.getImportSettSubj().getDebtcredit4())){
				allAmount = allAmount.add(importPayment.getImportSettSubj().getStandardamount4());
			}
	
			if (importPayment.getImportSettSubj().getDebtcredit4() != null && 
				"H".equals(importPayment.getImportSettSubj().getDebtcredit4())){
				allAmount = allAmount.subtract(importPayment.getImportSettSubj().getStandardamount4());
			}
		}
		
		if (importPayment.getImportFundFlow() != null){
			if (importPayment.getImportFundFlow().getDebtcredit1() != null  &&
				"S".equals(importPayment.getImportFundFlow().getDebtcredit1())){
				allAmount = allAmount.add(importPayment.getImportFundFlow().getStandardamount1());
			}
	
			if (importPayment.getImportFundFlow().getDebtcredit1() != null  &&
				"H".equals(importPayment.getImportFundFlow().getDebtcredit1())){
				allAmount = allAmount.subtract(importPayment.getImportFundFlow().getStandardamount1());
			}
			
			if (importPayment.getImportFundFlow().getDebtcredit2() != null  &&
				"S".equals(importPayment.getImportFundFlow().getDebtcredit2())){
				allAmount = allAmount.add(importPayment.getImportFundFlow().getStandardamount2());
			}
	
			if ("H".equals(importPayment.getImportFundFlow().getDebtcredit2())){
				allAmount = allAmount.subtract(importPayment.getImportFundFlow().getStandardamount2());
			}
			
			if (importPayment.getImportFundFlow().getDebtcredit3() != null  &&
				"S".equals(importPayment.getImportFundFlow().getDebtcredit3())){
				allAmount = allAmount.add(importPayment.getImportFundFlow().getStandardamount3());
			}
	
			if (importPayment.getImportFundFlow().getDebtcredit3() != null  &&
				"H".equals(importPayment.getImportFundFlow().getDebtcredit3())){
				allAmount = allAmount.subtract(importPayment.getImportFundFlow().getStandardamount3());
			}
		}
		//******************汇兑损益科目号行项目凭证行项目*******************************//
//		BigDecimal subtractValue = allAmount.subtract(bankAmount2);
//		if (subtractValue.signum() == 1){
//			rowNo = rowNo + 1;
//			VoucherItem profLossVoucherItem = new VoucherItem();
//			profLossVoucherItem.setVoucher(voucher2);
//			
//			profLossVoucherItem.setCheckcode("50");
//			//特殊G/L标识
//			profLossVoucherItem.setGlflag("");
//			//科目
//			profLossVoucherItem.setSubject("6603050402");
//			//科目说明
//			profLossVoucherItem.setSubjectdescribe("收益科目");
//			//货币
//			profLossVoucherItem.setCurrency(strCurrency);
//			//货币金额
//			profLossVoucherItem.setAmount(new BigDecimal("0"));
//			//本位币金额
//			profLossVoucherItem.setAmount2(subtractValue.abs());
//			//部门
//			profLossVoucherItem.setDepid(this.sysDeptJdbcDao.getDeptCodeById(importPayment.getDept_id()));
//			//文本
//			profLossVoucherItem.setText(importPayment.getText());
//			//现金流项目
//			profLossVoucherItem.setCashflowitem("");
//			//反记账标识
//			profLossVoucherItem.setUncheckflag("");
//			//统驭项目
//			profLossVoucherItem.setControlaccount("");
//			//统驭科目说明
//			profLossVoucherItem.setCaremark("");
//			//销售订单
//			profLossVoucherItem.setSalesorder("");
//			//销售订单行项目号
//			profLossVoucherItem.setOrderrowno("");
//			//利润中心
//			profLossVoucherItem.setProfitcenter("");
//			//成本中心
//			profLossVoucherItem.setCostcenter("");
//			//借/贷标识符
//			profLossVoucherItem.setDebitcredit("S");
//			//行号
//			profLossVoucherItem.setRownumber(String.valueOf(rowNo));
//			
//			voucherItemList2.add(profLossVoucherItem);
//		}
//		if (subtractValue.signum() == -1){
//			rowNo = rowNo + 1;
//			VoucherItem profLossVoucherItem = new VoucherItem();
//			profLossVoucherItem.setVoucher(voucher2);
//			
//			profLossVoucherItem.setCheckcode("40");
//			
//			//特殊G/L标识
//			profLossVoucherItem.setGlflag("");
//			//科目
//			profLossVoucherItem.setSubject("6603050401");
//			//科目说明
//			profLossVoucherItem.setSubjectdescribe("损失科目");
//			//货币
//			profLossVoucherItem.setCurrency(strCurrency);
//			//货币金额
//			profLossVoucherItem.setAmount(new BigDecimal("0"));
//			//本位币金额
//			profLossVoucherItem.setAmount2(subtractValue.abs());
//			//部门
//			profLossVoucherItem.setDepid(this.sysDeptJdbcDao.getDeptCodeById(importPayment.getDept_id()));
//			//文本
//			profLossVoucherItem.setText(importPayment.getText());
//			//现金流项目
//			profLossVoucherItem.setCashflowitem("");
//			//反记账标识
//			profLossVoucherItem.setUncheckflag("");
//			//统驭项目
//			profLossVoucherItem.setControlaccount("");
//			//统驭科目说明
//			profLossVoucherItem.setCaremark("");
//			//销售订单
//			profLossVoucherItem.setSalesorder("");
//			//销售订单行项目号
//			profLossVoucherItem.setOrderrowno("");
//			//利润中心
//			profLossVoucherItem.setProfitcenter("");
//			//成本中心
//			profLossVoucherItem.setCostcenter("");
//			//借/贷标识符
//			profLossVoucherItem.setDebitcredit("S");
//			//行号
//			profLossVoucherItem.setRownumber(String.valueOf(rowNo));
//			
//			voucherItemList2.add(profLossVoucherItem);
//		}
		//******************汇兑损益科目号行项目凭证行项目*******************************//
		voucher2.setVoucherItem(voucherItemList2);		
		this.voucherHibernateDao.save(voucher2);
		
		//*************处理清帐凭证*****************************
		if (checkIsClear(importPayment) == false){
			this.genProfOrLossVoucher(importPayment);
		}
		//*************处理清帐凭证*****************************

		retList.add(voucher2.getVoucherid());

		return retList;
	}
	
	/**
	 * 点押汇申请时
	 * @param importPayment
	 * @return
	 */
	public List<String> negotiatingSaveVoucher(ImportPayment importPayment){
		List<String> retList = new ArrayList<String>();
		return retList;
	}
	
	/**
	 * 纯代理付款
	 * @param importPayment
	 * @return
	 */
	public List<String> representpaySaveVoucher(ImportPayment importPayment){
		List<String> retList = new ArrayList<String>();

        String processstate = importPayment.getProcessstate();
		String bukrs = getCompanyIDByDeptID(importPayment.getDept_id());
		//this.voucherJdbcDao.deleteVoucherByBusinessidAndClass(importPayment.getPaymentid(), "5");
		//凭证抬头
		Voucher voucher = new Voucher();
		//凭证行项目
		Set<VoucherItem> voucherItemList = new HashSet<VoucherItem>();
		voucher = this.getVoucher(importPayment, "5");
		
		int rowNo = 1;
		//******************付款银行行项目凭证行项目*******************************//
		BigDecimal bankAmount2 = new BigDecimal("0");
		BigDecimal bankAmount = new BigDecimal("0");
		Iterator<ImportPayBankItem> bankItemit = importPayment.getImportPayBankItem().iterator();		
		while(bankItemit.hasNext()){
			rowNo = rowNo + 1;
			ImportPayBankItem importPayBankItem = bankItemit.next();
			
			VoucherItem BankVoucherItem = new VoucherItem();
			BankVoucherItem.setVoucher(voucher);
			//客户记账码
			BankVoucherItem.setCheckcode("50");
			//特殊G/L标识
			BankVoucherItem.setGlflag("");
			//科目
			BankVoucherItem.setSubject(importPayBankItem.getPaybanksubject());
			//科目说明
			BankVoucherItem.setSubjectdescribe(importPayBankItem.getPaybankname());
			//货币
			BankVoucherItem.setCurrency(importPayment.getFactcurrency());
			//货币金额
			BankVoucherItem.setAmount(importPayBankItem.getPayamount());
			bankAmount = bankAmount.add(importPayBankItem.getPayamount());
			//本位币金额
			BankVoucherItem.setAmount2(importPayBankItem.getPayamount2());
			bankAmount2 = bankAmount2.add(importPayBankItem.getPayamount2());
			//部门
			if("2".equals(importPayment.getPay_type())){
				BankVoucherItem.setDepid(importPayment.getRedocarybc());
			}else{
				BankVoucherItem.setDepid(this.sysDeptJdbcDao.getDeptCodeById(importPayment.getDept_id()));
			}
			
			
			//文本
			BankVoucherItem.setText(importPayment.getText());
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
		String subjectdescribe = this.customerRefundItemJdbcDao.getSubjectNameById("1122999999",bukrs);
		//科目
		voucherItem.setSubject("1122999999");
		//科目说明
		voucherItem.setSubjectdescribe(subjectdescribe);
		//货币
		voucherItem.setCurrency(importPayment.getFactcurrency());
		//货币金额
		voucherItem.setAmount(bankAmount);
		//本位币金额
		voucherItem.setAmount2(bankAmount2);
		//部门
		if("2".equals(importPayment.getPay_type())){
			voucherItem.setDepid(importPayment.getRedocarybc());
		}else{
			voucherItem.setDepid(this.sysDeptJdbcDao.getDeptCodeById(importPayment.getDept_id()));
		}
		//文本
		voucherItem.setText(importPayment.getText());
		//现金流项目
		voucherItem.setCashflowitem("");
		//反记账标识
		voucherItem.setUncheckflag("");
		//统驭项目
		voucherItem.setControlaccount("1122999999");
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

        if (! (importPayment.getPay_type().equals("2") && processstate.equals("财务会计审核押汇到期付款并做帐"))) {
            this.voucherHibernateDao.save(voucher);
        }
		retList.add(voucher.getVoucherid());
		
		//凭证抬头
		Voucher voucher2 = new Voucher();
		//凭证行项目
		Set<VoucherItem> voucherItemList2 = new HashSet<VoucherItem>();
		//this.voucherJdbcDao.deleteVoucherByBusinessidAndClass(importPayment.getPaymentid(), "1");
		voucher2 = this.getVoucher(importPayment, "1");
		voucher2.setCurrency("CNY");
		voucher2.setExchangerate(new BigDecimal("1"));
		
		rowNo = 0;
		//******************客户行项目凭证行项目**********************************//
		BigDecimal allAmount = new BigDecimal("0");
		Iterator<ImportPaymentItem> PayItemItemit = importPayment.getImportPaymentItem() .iterator();		
		while(PayItemItemit.hasNext()){
			rowNo = rowNo + 1;
			ImportPaymentItem importPaymentItem = PayItemItemit.next();
			
			VoucherItem PaymentItemvoucherItem = new VoucherItem();
			PaymentItemvoucherItem.setVoucher(voucher2);
			//客户记账码
			PaymentItemvoucherItem.setCheckcode("01");
			//特殊G/L标识
			PaymentItemvoucherItem.setGlflag("");
			//科目
			PaymentItemvoucherItem.setSubject(importPayment.getRepresentpaycust());
			//科目说明
			String Subjectdescribe = this.customerRefundItemJdbcDao.getCustomerDescByCustomerId(importPayment.getRepresentpaycust(),bukrs);
			PaymentItemvoucherItem.setSubjectdescribe(Subjectdescribe);
			//货币
			PaymentItemvoucherItem.setCurrency("CNY");
			//货币金额
			PaymentItemvoucherItem.setAmount(importPaymentItem.getAssignamount2());
			//本位币金额
			PaymentItemvoucherItem.setAmount2(importPaymentItem.getAssignamount2());
			allAmount = allAmount.add(importPaymentItem.getAssignamount2());
			//部门
			PaymentItemvoucherItem.setDepid(this.sysDeptJdbcDao.getDeptCodeById(importPayment.getDept_id()));
			//文本
			PaymentItemvoucherItem.setText(importPayment.getText());
			//现金流项目
			PaymentItemvoucherItem.setCashflowitem("");
			//反记账标识
			PaymentItemvoucherItem.setUncheckflag("");
			String subject = this.customerRefundItemJdbcDao.getSubjectByCustomer(importPayment.getRepresentpaycust(),bukrs);
			subject = this.customerRefundItemJdbcDao.getSkont(PaymentItemvoucherItem,subject);
			//统驭项目
			PaymentItemvoucherItem.setControlaccount(subject);
			//统驭科目说明
			PaymentItemvoucherItem.setCaremark(this.customerRefundItemJdbcDao.getSubjectNameById(subject,bukrs));
			//销售订单
			PaymentItemvoucherItem.setSalesorder("");
			//销售订单行项目号
			PaymentItemvoucherItem.setOrderrowno("");
			//利润中心
			PaymentItemvoucherItem.setProfitcenter("");
			//成本中心
			PaymentItemvoucherItem.setCostcenter("");
			//借/贷标识符
			PaymentItemvoucherItem.setDebitcredit("S");
			//行号
			PaymentItemvoucherItem.setRownumber(String.valueOf(rowNo));
			//付款行项目信息
			PaymentItemvoucherItem.setBusinessitemid(importPaymentItem.getPaymentitemid());
			
			voucherItemList2.add(PaymentItemvoucherItem);
		}
		//******************客户行项目凭证行项目**********************************//
		
		//************************应付中转科目**********************************//
		rowNo = rowNo + 1;
		VoucherItem voucherItem2 = new VoucherItem();
		voucherItem2.setVoucher(voucher2);
		//客户记账码
		voucherItem2.setCheckcode("50");
		//特殊G/L标识
		voucherItem2.setGlflag("");
		String subjectdesc = this.customerRefundItemJdbcDao.getSubjectNameById("1122999999",bukrs);
		//科目
		voucherItem2.setSubject("1122999999");
		//科目说明
		voucherItem2.setSubjectdescribe(subjectdesc);
		//货币
		voucherItem2.setCurrency("CNY");
		//货币金额
		voucherItem2.setAmount(bankAmount2);
		//本位币金额
		voucherItem2.setAmount2(bankAmount2);
		//部门
		voucherItem2.setDepid(this.sysDeptJdbcDao.getDeptCodeById(importPayment.getDept_id()));
		//文本
		voucherItem2.setText(importPayment.getText());
		//现金流项目
		voucherItem2.setCashflowitem("");
		//反记账标识
		voucherItem2.setUncheckflag("");
		//统驭项目
		voucherItem2.setControlaccount("1122999999");
		//统驭科目说明
		voucherItem2.setCaremark(subjectdesc);
		//销售订单
		voucherItem2.setSalesorder("");
		//销售订单行项目号
		voucherItem2.setOrderrowno("");
		//利润中心
		voucherItem2.setProfitcenter("");
		//成本中心
		voucherItem2.setCostcenter("");
		//借/贷标识符
		voucherItem2.setDebitcredit("H");
		//行号
		voucherItem2.setRownumber(String.valueOf(rowNo));
		
		voucherItemList2.add(voucherItem2);
		//************************应付中转科目**********************************//
		
		//******************结算科目行项目凭证行项目*******************************//
		//结算科目凭证行项目
		Set<VoucherItem> SettlesubjectVoucherItemList = new HashSet<VoucherItem>();
		
		SettlesubjectVoucherItemList = this.getSettSubjVoucherItem(importPayment);
		
		Iterator<VoucherItem> Settlesubjectit = SettlesubjectVoucherItemList.iterator();		
		while(Settlesubjectit.hasNext()){
			rowNo = rowNo + 1;
			VoucherItem setvoucherItem =Settlesubjectit.next();
			setvoucherItem.setRownumber(String.valueOf(rowNo));
			setvoucherItem.setCurrency("CNY");
//			setvoucherItem.setCheckcode("40");
//			setvoucherItem.setDebitcredit("S");
			setvoucherItem.setVoucher(voucher2);
			
			voucherItemList2.add(setvoucherItem);
		}
		//******************结算科目行项目凭证行项目*******************************//
		
		//******************特殊总帐行项目凭证行项目*******************************//
		//结算科目凭证行项目
		Set<VoucherItem> FundFlowVoucherItemList = new HashSet<VoucherItem>();
		
		FundFlowVoucherItemList = this.getFundFlowVoucherItem(importPayment);
		
		Iterator<VoucherItem> FundFlowit = FundFlowVoucherItemList.iterator();		
		while(FundFlowit.hasNext()){
			rowNo = rowNo + 1;
			VoucherItem funvoucherItem =FundFlowit.next();
			funvoucherItem.setRownumber(String.valueOf(rowNo));
			funvoucherItem.setCurrency("CNY");
			funvoucherItem.setVoucher(voucher2);
			
			voucherItemList2.add(funvoucherItem);
		}
		//******************特殊总帐行项目凭证行项目*******************************//

        
		voucher2.setVoucherItem(voucherItemList2);
		if (! (importPayment.getPay_type().equals("2") && processstate.equals("财务会计审核押汇到期付款并做帐"))) {
		    this.voucherHibernateDao.save(voucher2);
		}
		retList.add(voucher2.getVoucherid());

		//判断是否押汇
		if(importPayment.getPay_type().equals("2")  && processstate.equals("财务会计审核办理押汇并做帐")){
			//凭证抬头
			Voucher voucher3 = new Voucher();
			//凭证行项目
			Set<VoucherItem> voucherItemList3 = new HashSet<VoucherItem>();
			voucher3 = this.getVoucher(importPayment, "2");
			voucher3.setCurrency(importPayment.getCurrency2());
			
			rowNo = 0;
			//******************押汇银行行项目凭证行项目**********************************//
			ImportDocuBankItem importDocuBankItem = new ImportDocuBankItem();
			Iterator<ImportDocuBankItem> docBankItemit = importPayment.getImportDocuBankItem().iterator();		
			while(docBankItemit.hasNext()){
				rowNo = rowNo + 1;
				importDocuBankItem = docBankItemit.next();
				
				VoucherItem BankVoucherItem = new VoucherItem();
				BankVoucherItem.setVoucher(voucher3);
				//客户记账码
				BankVoucherItem.setCheckcode("50");
				//特殊G/L标识
				BankVoucherItem.setGlflag("");
				//科目
				BankVoucherItem.setSubject(importDocuBankItem.getDocuarybanksubj());
				subjectdesc = this.customerRefundItemJdbcDao.getSubjectNameById(importDocuBankItem.getDocuarybanksubj(),bukrs);
				//科目说明
				BankVoucherItem.setSubjectdescribe(subjectdesc);
				//货币
				BankVoucherItem.setCurrency(importPayment.getCurrency2());
				//货币金额
				BankVoucherItem.setAmount(importDocuBankItem.getDocuarypayamount());
				//本位币金额
				BankVoucherItem.setAmount2(importDocuBankItem.getDocuarypayamoun2());
				//部门
				BankVoucherItem.setDepid(this.sysDeptJdbcDao.getDeptCodeById(importPayment.getDept_id()));
				//文本
				BankVoucherItem.setText(importPayment.getText());
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
				BankVoucherItem.setDebitcredit("H");
				//行号
				BankVoucherItem.setRownumber(String.valueOf(rowNo));
				
				voucherItemList3.add(BankVoucherItem);
			}
			//******************押汇银行行项目凭证行项目**********************************//
			//******************付款银行行项目凭证行项目*******************************//
//			Iterator<ImportPayBankItem> bankItemit2 = importPayment.getImportPayBankItem().iterator();		
			docBankItemit = importPayment.getImportDocuBankItem().iterator();    
            while(docBankItemit.hasNext()){
                rowNo = rowNo + 1;
                importDocuBankItem = docBankItemit.next();
                
				VoucherItem BankVoucherItem = new VoucherItem();
				BankVoucherItem.setVoucher(voucher3);
				//客户记账码
				BankVoucherItem.setCheckcode("40");
				//特殊G/L标识
				BankVoucherItem.setGlflag("");
				String subject = this.bankInfoJdbcDao.getBankInfo(importDocuBankItem.getDocuarybankacco());
				subjectdesc = this.customerRefundItemJdbcDao.getSubjectNameById(subject,bukrs);
				//科目
				BankVoucherItem.setSubject(subject);
				//科目说明
				BankVoucherItem.setSubjectdescribe(subjectdesc);
				//货币
				BankVoucherItem.setCurrency(importPayment.getCurrency2());
				//货币金额
				BigDecimal docuarypayamount = importDocuBankItem.getDocuarypayamount();
				BankVoucherItem.setAmount(docuarypayamount == null ? new BigDecimal(0.00) : docuarypayamount);
				//本位币金额
				BigDecimal docuarypayamoun2 = importDocuBankItem.getDocuarypayamoun2();
				BankVoucherItem.setAmount2(docuarypayamoun2 == null ? new BigDecimal(0.00) : docuarypayamoun2);
				//部门
				BankVoucherItem.setDepid(this.sysDeptJdbcDao.getDeptCodeById(importPayment.getDept_id()));
				//文本
				BankVoucherItem.setText(importPayment.getText());
				//现金流项目
				BankVoucherItem.setCashflowitem(importDocuBankItem.getCashflowitem()); // 302
				//反记账标识
				BankVoucherItem.setUncheckflag("");
				//统驭项目
				BankVoucherItem.setControlaccount(subject);
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
				
				voucherItemList3.add(BankVoucherItem);
			}
			//******************付款银行行项目凭证行项目*******************************//

			voucher3.setVoucherItem(voucherItemList3);
			this.voucherHibernateDao.save(voucher3);
			retList.add(voucher3.getVoucherid());
			
		}
		
		
		//判断是否押汇并且还押汇
		if(importPayment.getPay_type().equals("2") && processstate.equals("财务会计审核押汇到期付款并做帐")){
		    allAmount = new BigDecimal(0);
		    bankAmount2 = new BigDecimal(0);
			//凭证抬头
			Voucher voucher4 = new Voucher();
			//凭证行项目
			Set<VoucherItem> voucherItemList4 = new HashSet<VoucherItem>();
			voucher4 = this.getVoucher(importPayment, "3"); // 还押汇为3
			voucher4.setCurrency(importPayment.getCurrency2());
			
			rowNo = 0;
			//******************押汇银行行项目凭证行项目**********************************//
			ImportDocuBankItem importDocuBankItem2 = new ImportDocuBankItem();
			//String dabank_hkonrname2 = "";
			Iterator<ImportDocuBankItem> docBankItemit2 = importPayment.getImportDocuBankItem().iterator();		
			while(docBankItemit2.hasNext()){
				rowNo = rowNo + 1;
				importDocuBankItem2 = docBankItemit2.next();
				
				VoucherItem BankVoucherItem = new VoucherItem();
				BankVoucherItem.setVoucher(voucher4);
				//客户记账码
				BankVoucherItem.setCheckcode("40");
				//特殊G/L标识
				BankVoucherItem.setGlflag("");
				//科目
				String subject = importDocuBankItem2.getDocuarybanksubj();
				subjectdesc = this.customerRefundItemJdbcDao.getSubjectNameById(subject,bukrs);
				BankVoucherItem.setSubject(subject);
				//科目说明
				BankVoucherItem.setSubjectdescribe(subjectdesc);
				//货币
				BankVoucherItem.setCurrency(importPayment.getCurrency2());
				//货币金额
				BankVoucherItem.setAmount(importPayment.getRedocaryamount());
				//本位币金额
				BankVoucherItem.setAmount2(importPayment.getRedocaryamount().multiply(importPayment.getDocumentaryrate()).setScale(2, BigDecimal.ROUND_HALF_UP));
				allAmount = allAmount.add(BankVoucherItem.getAmount2());
				//部门
				BankVoucherItem.setDepid(this.sysDeptJdbcDao.getDeptCodeById(importPayment.getDept_id()));
				//文本
				BankVoucherItem.setText(importPayment.getText());
				//现金流项目
				BankVoucherItem.setCashflowitem("");
				//反记账标识
				BankVoucherItem.setUncheckflag("");
				//统驭项目
				BankVoucherItem.setControlaccount(importDocuBankItem2.getDocuarybanksubj());
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
				
				voucherItemList4.add(BankVoucherItem);
			}
			//******************押汇银行行项目凭证行项目**********************************//
			//******************付款银行行项目凭证行项目*******************************//
//			Iterator<ImportPayBankItem> bankItemit3 = importPayment.getImportPayBankItem().iterator();		
//			while(bankItemit3.hasNext()){
//				rowNo = rowNo + 1;
//				ImportPayBankItem payBank = bankItemit3.next();
//				
//				VoucherItem BankVoucherItem = new VoucherItem();
//				BankVoucherItem.setVoucher(voucher4);
//				//客户记账码
//				BankVoucherItem.setCheckcode("50");
//				//特殊G/L标识
//				BankVoucherItem.setGlflag("");
////				String subject = this.bankInfoJdbcDao.getBankInfo(importDocuBankItem2.getDocuarybankacco());
////				subjectdesc = this.customerRefundItemJdbcDao.getSubjectNameById(subject,bukrs);
//				
//				//科目
//				BankVoucherItem.setSubject(payBank.getPaybanksubject());
//				subjectdesc = this.customerRefundItemJdbcDao.getSubjectNameById(payBank.getPaybanksubject(),bukrs);
//				
//				//科目说明
//				BankVoucherItem.setSubjectdescribe(subjectdesc);
//				//货币
//				BankVoucherItem.setCurrency(importPayment.getCurrency2());
//				//货币金额
//				BigDecimal docuarypayamount = payBank.getPayamount();
//				BankVoucherItem.setAmount(docuarypayamount == null ? new BigDecimal(0.00) : docuarypayamount);
//				//本位币金额
//				BigDecimal docuarypayamoun2 = payBank.getPayamount2();
//				BankVoucherItem.setAmount2(docuarypayamoun2 == null ? new BigDecimal(0.00) : docuarypayamoun2);
//				//部门
//				BankVoucherItem.setDepid(importPayment.getRedocarybc());
//				//文本
//				BankVoucherItem.setText(importPayment.getText());
//				//现金流项目
//				BankVoucherItem.setCashflowitem(payBank.getCashflowitem());
//				//反记账标识
//				BankVoucherItem.setUncheckflag("");
//				//统驭项目
//				BankVoucherItem.setControlaccount(payBank.getPaybanksubject());
//				//统驭科目说明
//				BankVoucherItem.setCaremark(subjectdesc);
//				//销售订单
//				BankVoucherItem.setSalesorder("");
//				//销售订单行项目号
//				BankVoucherItem.setOrderrowno("");
//				//利润中心
//				BankVoucherItem.setProfitcenter("");
//				//成本中心
//				BankVoucherItem.setCostcenter("");
//				//借/贷标识符
//				BankVoucherItem.setDebitcredit("H");
//				//行号
//				BankVoucherItem.setRownumber(String.valueOf(rowNo));
//				
//				voucherItemList4.add(BankVoucherItem);
//			}
			//******************押汇银行行项目凭证行项目**********************************//
			//******************付款银行修改成还押汇银行行项目凭证行项目zzh*******************************//
			Iterator<BillPayReBankItem> billbankItemit = importPayment.getBillPayReBankItem().iterator();		
			VoucherItem bankVoucherItem = null;
			while(billbankItemit.hasNext()){
				rowNo = rowNo + 1;
				BillPayReBankItem billPayReBankItem = billbankItemit.next();
				if("未做账".equals(billPayReBankItem.getBusinesstype())){
					bankVoucherItem = new VoucherItem();
					bankVoucherItem.setVoucher(voucher4);
					//客户记账码
					bankVoucherItem.setCheckcode("50");
					//特殊G/L标识
					bankVoucherItem.setGlflag("");
					String banksubject =this.bankInfoJdbcDao.getBankInfo(billPayReBankItem.getBankacc());
					String billsubjectdescribe = this.customerRefundItemJdbcDao.getSubjectNameById(banksubject,bukrs);
					//科目
					bankVoucherItem.setSubject(banksubject);
					//科目说明
					bankVoucherItem.setSubjectdescribe(billsubjectdescribe);
					//货币
					bankVoucherItem.setCurrency(importPayment.getCurrency2());
					//货币金额
					bankVoucherItem.setAmount(billPayReBankItem.getRealmoney());
					//本位币金额
					bankVoucherItem.setAmount2(billPayReBankItem.getRealmoney2());
					bankAmount2 = bankAmount2.add(bankVoucherItem.getAmount2());
					//部门
					bankVoucherItem.setDepid(importPayment.getRedocarybc());
					//文本
					bankVoucherItem.setText(importPayment.getText());
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
					
					voucherItemList4.add(bankVoucherItem);
				}
			}
			//******************付款银行行项目凭证行项目*******************************//
	
			voucher4.setVoucherItem(voucherItemList4);


		
		//******************汇兑损益科目号行项目凭证行项目*******************************//
        if (importPayment.getImportSettSubj() != null) {
            if (importPayment.getImportSettSubj().getDebtcredit1() != null
                    && "S".equals(importPayment.getImportSettSubj().getDebtcredit1())) {
                allAmount = allAmount.add(importPayment.getImportSettSubj().getStandardamount1());
            }

            if (importPayment.getImportSettSubj().getDebtcredit1() != null
                    && "H".equals(importPayment.getImportSettSubj().getDebtcredit1())) {
                allAmount = allAmount.subtract(importPayment.getImportSettSubj().getStandardamount1());
            }

            if (importPayment.getImportSettSubj().getDebtcredit2() != null
                    && "S".equals(importPayment.getImportSettSubj().getDebtcredit2())) {
                allAmount = allAmount.add(importPayment.getImportSettSubj().getStandardamount2());
            }

            if (importPayment.getImportSettSubj().getDebtcredit2() != null
                    && "H".equals(importPayment.getImportSettSubj().getDebtcredit2())) {
                allAmount = allAmount.subtract(importPayment.getImportSettSubj().getStandardamount2());
            }

            if (importPayment.getImportSettSubj().getDebtcredit3() != null
                    && "S".equals(importPayment.getImportSettSubj().getDebtcredit3())) {
                allAmount = allAmount.add(importPayment.getImportSettSubj().getStandardamount3());
            }

            if (importPayment.getImportSettSubj().getDebtcredit3() != null
                    && "H".equals(importPayment.getImportSettSubj().getDebtcredit3())) {
                allAmount = allAmount.subtract(importPayment.getImportSettSubj().getStandardamount3());
            }

            if (importPayment.getImportSettSubj().getDebtcredit4() != null
                    && "S".equals(importPayment.getImportSettSubj().getDebtcredit4())) {
                allAmount = allAmount.add(importPayment.getImportSettSubj().getStandardamount4());
            }

            if (importPayment.getImportSettSubj().getDebtcredit4() != null
                    && "H".equals(importPayment.getImportSettSubj().getDebtcredit4())) {
                allAmount = allAmount.subtract(importPayment.getImportSettSubj().getStandardamount4());
            }
            Set<VoucherItem> setItem = this.getSettSubjVoucherItem(importPayment);
            for (VoucherItem setvoucherItem : setItem) {
                rowNo++;
                setvoucherItem.setRownumber(String.valueOf(rowNo));
                setvoucherItem.setVoucher(voucher4);
                voucherItemList4.add(setvoucherItem);
            }
        }
		//******************汇兑损益科目号行项目凭证行项目*******************************//

        //******************纯资金行项目凭证行项目*******************************//
        //******************汇兑损益科目号行项目凭证行项目*******************************//
        if (importPayment.getImportFundFlow() != null) {
            if (importPayment.getImportFundFlow().getDebtcredit1() != null
                    && "S".equals(importPayment.getImportFundFlow().getDebtcredit1())) {
                allAmount = allAmount.add(importPayment.getImportFundFlow().getStandardamount1());
            }

            if (importPayment.getImportFundFlow().getDebtcredit1() != null
                    && "H".equals(importPayment.getImportFundFlow().getDebtcredit1())) {
                allAmount = allAmount.subtract(importPayment.getImportFundFlow().getStandardamount1());
            }

            if (importPayment.getImportFundFlow().getDebtcredit2() != null
                    && "S".equals(importPayment.getImportFundFlow().getDebtcredit2())) {
                allAmount = allAmount.add(importPayment.getImportFundFlow().getStandardamount2());
            }

            if (importPayment.getImportFundFlow().getDebtcredit2() != null
                    && "H".equals(importPayment.getImportFundFlow().getDebtcredit2())) {
                allAmount = allAmount.subtract(importPayment.getImportFundFlow().getStandardamount2());
            }

            if (importPayment.getImportFundFlow().getDebtcredit3() != null
                    && "S".equals(importPayment.getImportFundFlow().getDebtcredit3())) {
                allAmount = allAmount.add(importPayment.getImportFundFlow().getStandardamount3());
            }

            if (importPayment.getImportFundFlow().getDebtcredit3() != null
                    && "H".equals(importPayment.getImportFundFlow().getDebtcredit3())) {
                allAmount = allAmount.subtract(importPayment.getImportFundFlow().getStandardamount3());
            }

            FundFlowit = FundFlowVoucherItemList.iterator();      
            while(FundFlowit.hasNext()){
                rowNo = rowNo + 1;
                VoucherItem funvoucherItem =FundFlowit.next();
                funvoucherItem.setRownumber(String.valueOf(rowNo));
                funvoucherItem.setVoucher(voucher4);
                funvoucherItem.setCurrency(importPayment.getCurrency2());
                voucherItemList4.add(funvoucherItem);
            }
        }
        //******************纯资金行项目凭证行项目*******************************//
		
		BigDecimal subtractValue = allAmount.subtract(bankAmount2);
		if (subtractValue.signum() == 1){
			rowNo = rowNo + 1;
			VoucherItem profLossVoucherItem = new VoucherItem();
			profLossVoucherItem.setVoucher(voucher4);
			
			profLossVoucherItem.setCheckcode("50");
			//特殊G/L标识
			profLossVoucherItem.setGlflag("");
			//科目
			profLossVoucherItem.setSubject("6603050502");
			//科目说明
			profLossVoucherItem.setSubjectdescribe("财务费用-进口押汇汇兑收益");
			//货币
			profLossVoucherItem.setCurrency(importPayment.getFactcurrency());
			//货币金额
			profLossVoucherItem.setAmount(new BigDecimal("0"));
			//本位币金额
			profLossVoucherItem.setAmount2(subtractValue.abs());
			//部门
			profLossVoucherItem.setDepid(importPayment.getRedocarybc());
			//文本
			profLossVoucherItem.setText(importPayment.getText());
			//现金流项目
			profLossVoucherItem.setCashflowitem("");
			//反记账标识
			profLossVoucherItem.setUncheckflag("");
			//统驭项目
			profLossVoucherItem.setControlaccount("6603050502");
			//统驭科目说明
			profLossVoucherItem.setCaremark("财务费用-进口押汇汇兑收益");
			//销售订单
			profLossVoucherItem.setSalesorder("");
			//销售订单行项目号
			profLossVoucherItem.setOrderrowno("");
			//利润中心
			profLossVoucherItem.setProfitcenter("");
			//成本中心
			profLossVoucherItem.setCostcenter("");
			//借/贷标识符
			profLossVoucherItem.setDebitcredit("H");
			//行号
			profLossVoucherItem.setRownumber(String.valueOf(rowNo));
			
			voucherItemList4.add(profLossVoucherItem);
		}
		if (subtractValue.signum() == -1){
			rowNo = rowNo + 1;
			VoucherItem profLossVoucherItem = new VoucherItem();
			profLossVoucherItem.setVoucher(voucher4);
			
			profLossVoucherItem.setCheckcode("40");
			
			//特殊G/L标识
			profLossVoucherItem.setGlflag("");
			//科目
			profLossVoucherItem.setSubject("6603050501");
			//科目说明
			profLossVoucherItem.setSubjectdescribe("财务费用-进口押汇汇兑损失");
			//货币
			profLossVoucherItem.setCurrency(importPayment.getFactcurrency());
			//货币金额
			profLossVoucherItem.setAmount(new BigDecimal("0"));
			//本位币金额
			profLossVoucherItem.setAmount2(subtractValue.abs());
			//部门
			profLossVoucherItem.setDepid(importPayment.getRedocarybc());
			//文本
			profLossVoucherItem.setText(importPayment.getText());
			//现金流项目
			profLossVoucherItem.setCashflowitem("");
			//反记账标识
			profLossVoucherItem.setUncheckflag("");
			//统驭项目
			profLossVoucherItem.setControlaccount("6603050501");
			//统驭科目说明
			profLossVoucherItem.setCaremark("财务费用-进口汇兑押汇损失");
			//销售订单
			profLossVoucherItem.setSalesorder("");
			//销售订单行项目号
			profLossVoucherItem.setOrderrowno("");
			//利润中心
			profLossVoucherItem.setProfitcenter("");
			//成本中心
			profLossVoucherItem.setCostcenter("");
			//借/贷标识符
			profLossVoucherItem.setDebitcredit("S");
			//行号
			profLossVoucherItem.setRownumber(String.valueOf(rowNo));
			voucherItemList4.add(profLossVoucherItem);
		}
		
		voucher4.getVoucherItem().addAll(voucherItemList4);
        this.voucherHibernateDao.save(voucher4);
        retList.add(voucher4.getVoucherid());
		//******************汇兑损益科目号行项目凭证行项目*******************************//
      }
        
        return retList;
	}
	
	/**
	 * 纯代理付款 清帐凭证
	 * @param importPayment
	 * @return
	 */
	public List<String> representpaySaveClearVoucher(ImportPayment importPayment) {

        if (!"财务会计审核押汇到期付款并做帐".equals(importPayment.getProcessstate())) {
            return null;
        }
	    List<String> resultList = new ArrayList<String>();
        Voucher voucherClear = new Voucher();
        voucherClear = this.getClearVoucher(importPayment, "2");
        voucherClear.setAgkoa("S");
        voucherClear.setCurrency(importPayment.getCurrency2());

        //凭证行项目
        Set<VoucherItem> clearItems = new HashSet<VoucherItem>();

        voucherClear.setVoucherItem(clearItems);
        voucherClear.setAgkon("");
        VoucherItem clearItem = null;
//      押汇金额
		BigDecimal docaryamount = new BigDecimal("0");
		BigDecimal allAmount = new BigDecimal("0");
		BigDecimal docaryamount2 = new BigDecimal("0");
        List<VoucherItem> existsList = this.voucherItemJdbcDao.getVoucherItemList2(importPayment.getPaymentid(), "02",  "2");
        for (VoucherItem item : existsList) {
            if ( "50".equals(item.getCheckcode()) ) {
            	docaryamount = docaryamount.add(item.getAmount());
            	docaryamount2 = docaryamount2.add(item.getAmount2());
                clearItem = this.getClearVoucherItem(item.getVoucher().getVoucherno(), item.getRownumber(),
                        item.getFiyear(), item.getVoucher().getVoucherid());
                clearItem.setVoucher(voucherClear);
                clearItems.add(clearItem);
                voucherClear.setAgkon(item.getSubject());
                break;
            }
        }
      //如果累计还押汇金额+本次还押汇金额=押汇金额才出短期外汇借款清帐凭证
		if(importPayment.getRedocaryamount2().add(importPayment.getRedocaryamount()).compareTo(docaryamount) !=0){
			return null;
		}
        //docuarybanksubj_text
        String docBankSub = importPayment.getImportDocuBankItem().iterator().next().getDocuarybanksubj();
        existsList = this.voucherItemJdbcDao.getVoucherItemList2(importPayment.getPaymentid(), "02",  "3");
        for (VoucherItem item : existsList) {
            if ( "40".equals(item.getCheckcode()) &&  item.getSubject().equals(docBankSub)) {
                clearItem = this.getClearVoucherItem(item.getVoucher().getVoucherno(), item.getRownumber(),
                        item.getFiyear(), item.getVoucher().getVoucherid());
                clearItem.setVoucher(voucherClear);
                clearItems.add(clearItem);
                allAmount = allAmount.add(item.getAmount2());
                //break;
            }
        }
        int rowNo =1;
        BigDecimal subtractValue = docaryamount2.subtract(allAmount);
		if (subtractValue.signum() == 1){
			rowNo = rowNo + 1;
			VoucherItem profLossVoucherItem = new VoucherItem();
			profLossVoucherItem.setVoucher(voucherClear);
			
			profLossVoucherItem.setCheckcode("50");
			//特殊G/L标识
			profLossVoucherItem.setGlflag("");
			//科目
			profLossVoucherItem.setSubject("6603050502");
			//科目说明
			profLossVoucherItem.setSubjectdescribe("财务费用-进口押汇汇兑收益");
			//货币
			profLossVoucherItem.setCurrency(importPayment.getFactcurrency());
			//货币金额
			profLossVoucherItem.setAmount(new BigDecimal("0"));
			//本位币金额
			profLossVoucherItem.setAmount2(subtractValue.abs());
			//部门
			profLossVoucherItem.setDepid(importPayment.getRedocarybc());
			//文本
			profLossVoucherItem.setText(importPayment.getText());
			//现金流项目
			profLossVoucherItem.setCashflowitem("");
			//反记账标识
			profLossVoucherItem.setUncheckflag("");
			//统驭项目
			profLossVoucherItem.setControlaccount("6603050502");
			//统驭科目说明
			profLossVoucherItem.setCaremark("财务费用-进口押汇汇兑收益");
			//销售订单
			profLossVoucherItem.setSalesorder("");
			//销售订单行项目号
			profLossVoucherItem.setOrderrowno("");
			//利润中心
			profLossVoucherItem.setProfitcenter("");
			//成本中心
			profLossVoucherItem.setCostcenter("");
			//借/贷标识符
			profLossVoucherItem.setDebitcredit("H");
			//行号
			profLossVoucherItem.setRownumber(String.valueOf(rowNo));
			
			clearItems.add(profLossVoucherItem);
		}
		if (subtractValue.signum() == -1){
			rowNo = rowNo + 1;
			VoucherItem profLossVoucherItem = new VoucherItem();
			profLossVoucherItem.setVoucher(voucherClear);
			
			profLossVoucherItem.setCheckcode("40");
			
			//特殊G/L标识
			profLossVoucherItem.setGlflag("");
			//科目
			profLossVoucherItem.setSubject("6603050501");
			//科目说明
			profLossVoucherItem.setSubjectdescribe("财务费用-进口押汇汇兑损失");
			//货币
			profLossVoucherItem.setCurrency(importPayment.getFactcurrency());
			//货币金额
			profLossVoucherItem.setAmount(new BigDecimal("0"));
			//本位币金额
			profLossVoucherItem.setAmount2(subtractValue.abs());
			//部门
			profLossVoucherItem.setDepid(importPayment.getRedocarybc());
			//文本
			profLossVoucherItem.setText(importPayment.getText());
			//现金流项目
			profLossVoucherItem.setCashflowitem("");
			//反记账标识
			profLossVoucherItem.setUncheckflag("");
			//统驭项目
			profLossVoucherItem.setControlaccount("6603050501");
			//统驭科目说明
			profLossVoucherItem.setCaremark("财务费用-进口汇兑押汇损失");
			//销售订单
			profLossVoucherItem.setSalesorder("");
			//销售订单行项目号
			profLossVoucherItem.setOrderrowno("");
			//利润中心
			profLossVoucherItem.setProfitcenter("");
			//成本中心
			profLossVoucherItem.setCostcenter("");
			//借/贷标识符
			profLossVoucherItem.setDebitcredit("S");
			//行号
			profLossVoucherItem.setRownumber(String.valueOf(rowNo));
			clearItems.add(profLossVoucherItem);
		}		
		
        voucherClear.setAgkon("");
        this.voucherHibernateDao.save(voucherClear);
        resultList.add(voucherClear.getVoucherid());
        return resultList;
	}
	
	
	
	//海外代付清帐
	public List<String> overpaySaveVoucher(ImportPayment importPayment){
		List<String> retList = new ArrayList<String>();
		
		//this.voucherJdbcDao.deleteVoucherByBusinessidAndClass(importPayment.getPaymentid(), "2");
		
		//凭证抬头
		Voucher voucher = new Voucher();
		//凭证行项目
		Set<VoucherItem> voucherItemList = new HashSet<VoucherItem>();
		voucher = this.getVoucher(importPayment, "2");
		
		int rowNo = 0;
		//******************供应商行项目凭证行项目**********************************//
		Iterator<ImportPaymentItem> PayItemItemit = importPayment.getImportPaymentItem() .iterator();		
		while(PayItemItemit.hasNext()){
			rowNo = rowNo + 1;
			ImportPaymentItem importPaymentItem = PayItemItemit.next();
			
			VoucherItem voucherItem = new VoucherItem();
			voucherItem.setVoucher(voucher);
			//客户记账码
			voucherItem.setCheckcode("21");
			//特殊G/L标识
			voucherItem.setGlflag("");
			//科目
			voucherItem.setSubject(importPayment.getSupplier());
			//科目说明
			String bukrs = getCompanyIDByDeptID(importPayment.getDept_id());
			String Subjectdescribe = this.customerRefundItemJdbcDao.getSupplierDescByCustomerId(importPayment.getSupplier(),bukrs);
			voucherItem.setSubjectdescribe(Subjectdescribe);
			//货币
			voucherItem.setCurrency(importPayment.getFactcurrency());
			//货币金额
			voucherItem.setAmount(importPaymentItem.getAssignamount());
			//本位币金额
			voucherItem.setAmount2(importPaymentItem.getAssignamount2());
			//部门
			voucherItem.setDepid(this.sysDeptJdbcDao.getDeptCodeById(importPayment.getDept_id()));
			//文本
			voucherItem.setText(importPayment.getText());
			//反记账标识
			voucherItem.setUncheckflag("");
			String subject = this.customerRefundItemJdbcDao.getSubjectBySuppliers(importPayment.getSupplier(),bukrs);
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
			voucherItem.setBusinessitemid(importPaymentItem.getPaymentitemid());
			
			voucherItemList.add(voucherItem);
		}
		//******************供应商行项目凭证行项目**********************************//
		//******************付款银行行项目凭证行项目*******************************//
		Iterator<ImportPayBankItem> bankItemit = importPayment.getImportPayBankItem().iterator();		
		while(bankItemit.hasNext()){
			rowNo = rowNo + 1;
			ImportPayBankItem importPayBankItem = bankItemit.next();
			
			VoucherItem BankVoucherItem = new VoucherItem();
			BankVoucherItem.setVoucher(voucher);
			//客户记账码
			BankVoucherItem.setCheckcode("50");
			//特殊G/L标识
			BankVoucherItem.setGlflag("");
			//科目
			BankVoucherItem.setSubject(importPayBankItem.getPaybanksubject());
			//科目说明
			BankVoucherItem.setSubjectdescribe(importPayBankItem.getPaybankname());
			//货币
			BankVoucherItem.setCurrency(importPayment.getFactcurrency());
			//货币金额
			BankVoucherItem.setAmount(importPayBankItem.getPayamount());
			//本位币金额
			BankVoucherItem.setAmount2(importPayBankItem.getPayamount2());
			//部门
			BankVoucherItem.setDepid(this.sysDeptJdbcDao.getDeptCodeById(importPayment.getDept_id()));
			//文本
			BankVoucherItem.setText(importPayment.getText());
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
			
			voucherItemList.add(BankVoucherItem);
		}
		//******************付款银行行项目凭证行项目*******************************//
		//******************押汇银行行项目凭证行项目**********************************//
//		Iterator<ImportDocuBankItem> docBankItemit = importPayment.getImportDocuBankItem().iterator();		
//		while(docBankItemit.hasNext()){
//			rowNo = rowNo + 1;
//			ImportDocuBankItem importPayBankItem = docBankItemit.next();
//			
//			VoucherItem BankVoucherItem = new VoucherItem();
//			BankVoucherItem.setVoucher(voucher);
//			//客户记账码
//			BankVoucherItem.setCheckcode("50");
//			//特殊G/L标识
//			BankVoucherItem.setGlflag("");
//			//科目
//			BankVoucherItem.setSubject(importPayBankItem.getDocuarybanksubj());
//			//科目说明
//			String dabank_hkonrname = this.bankInfoJdbcDao.getDaBankHkonrName(importPayBankItem.getDocuarybankid(), importPayBankItem.getDocuarybanksubj());
//			BankVoucherItem.setSubjectdescribe(dabank_hkonrname);
//			//货币
//			BankVoucherItem.setCurrency(importPayment.getFactcurrency());
//			//货币金额
//			BankVoucherItem.setAmount(importPayBankItem.getDocuarypayamount());
//			//本位币金额
//			BankVoucherItem.setAmount2(importPayBankItem.getDocuarypayamoun2());
//			//部门
//			BankVoucherItem.setDepid(this.sysDeptJdbcDao.getDeptCodeById(importPayment.getDept_id()));
//			//文本
//			BankVoucherItem.setText(importPayment.getText());
//			//现金流项目
//			BankVoucherItem.setCashflowitem("151");
//			//反记账标识
//			BankVoucherItem.setUncheckflag("");
//			//统驭项目
//			BankVoucherItem.setControlaccount("");
//			//统驭科目说明
//			BankVoucherItem.setCaremark("");
//			//销售订单
//			BankVoucherItem.setSalesorder("");
//			//销售订单行项目号
//			BankVoucherItem.setOrderrowno("");
//			//利润中心
//			BankVoucherItem.setProfitcenter("");
//			//成本中心
//			BankVoucherItem.setCostcenter("");
//			//借/贷标识符
//			BankVoucherItem.setDebitcredit("H");
//			//行号
//			BankVoucherItem.setRownumber(String.valueOf(rowNo));
//			
//			voucherItemList.add(BankVoucherItem);
//		}
		//******************押汇银行行项目凭证行项目**********************************//
		//******************结算科目行项目凭证行项目*******************************//
		Set<VoucherItem> SettlesubjectVoucherItemList = new HashSet<VoucherItem>();
		
		SettlesubjectVoucherItemList = this.getSettSubjVoucherItem(importPayment);
		
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
		
		FundFlowVoucherItemList = this.getFundFlowVoucherItem(importPayment);
		
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
		
		
//		//凭证抬头
//		Voucher voucher2 = new Voucher();
//		//凭证行项目
//		Set<VoucherItem> voucherItemList2 = new HashSet<VoucherItem>();
//		this.voucherJdbcDao.deleteVoucherByBusinessidAndClass(importPayment.getPaymentid(), "3");
//		voucher2 = this.getVoucher(importPayment, "3");
//		
//		
//		rowNo = 0;
//		//******************押汇银行行项目凭证行项目**********************************//
//		String strSubject = "";
//		Iterator<ImportDocuBankItem> docBankItemit2 = importPayment.getImportDocuBankItem().iterator();		
//		while(docBankItemit2.hasNext()){
//			rowNo = rowNo + 1;
//			ImportDocuBankItem importPayBankItem = docBankItemit2.next();
//			
//			VoucherItem BankVoucherItem = new VoucherItem();
//			BankVoucherItem.setVoucher(voucher2);
//			//客户记账码
//			BankVoucherItem.setCheckcode("40");
//			//特殊G/L标识
//			BankVoucherItem.setGlflag("");
//			//科目
//			BankVoucherItem.setSubject(importPayBankItem.getDocuarybanksubj());
//			strSubject = strSubject + importPayBankItem.getDocuarybanksubj() + "','";
//			//科目说明
//			String dabank_hkonrname = this.bankInfoJdbcDao.getDaBankHkonrName(importPayBankItem.getDocuarybankid(), importPayBankItem.getDocuarybanksubj());
//			BankVoucherItem.setSubjectdescribe(dabank_hkonrname);
//			//货币
//			BankVoucherItem.setCurrency(importPayment.getFactcurrency());
//			//货币金额
//			BankVoucherItem.setAmount(importPayBankItem.getDocuarypayamount());
//			//本位币金额
//			BankVoucherItem.setAmount2(importPayBankItem.getDocuarypayamoun2());
//			//部门
//			BankVoucherItem.setDepid(this.sysDeptJdbcDao.getDeptCodeById(importPayment.getDept_id()));
//			//文本
//			BankVoucherItem.setText(importPayment.getText());
//			//现金流项目
//			BankVoucherItem.setCashflowitem("");
//			//反记账标识
//			BankVoucherItem.setUncheckflag("");
//			//统驭项目
//			BankVoucherItem.setControlaccount("");
//			//统驭科目说明
//			BankVoucherItem.setCaremark("");
//			//销售订单
//			BankVoucherItem.setSalesorder("");
//			//销售订单行项目号
//			BankVoucherItem.setOrderrowno("");
//			//利润中心
//			BankVoucherItem.setProfitcenter("");
//			//成本中心
//			BankVoucherItem.setCostcenter("");
//			//借/贷标识符
//			BankVoucherItem.setDebitcredit("S");
//			//行号
//			BankVoucherItem.setRownumber(String.valueOf(rowNo));
//			
//			voucherItemList2.add(BankVoucherItem);
//		}
//		//******************押汇银行行项目凭证行项目**********************************//
//		//******************付款银行行项目凭证行项目*******************************//
//		Iterator<ImportPayBankItem> bankItemit = importPayment.getImportPayBankItem().iterator();		
//		while(bankItemit.hasNext()){
//			rowNo = rowNo + 1;
//			ImportPayBankItem importPayBankItem = bankItemit.next();
//			
//			VoucherItem BankVoucherItem = new VoucherItem();
//			BankVoucherItem.setVoucher(voucher2);
//			//客户记账码
//			BankVoucherItem.setCheckcode("50");
//			//特殊G/L标识
//			BankVoucherItem.setGlflag("");
//			//科目
//			BankVoucherItem.setSubject(importPayBankItem.getPaybanksubject());
//			//科目说明
//			BankVoucherItem.setSubjectdescribe(importPayBankItem.getPaybankname());
//			//货币
//			BankVoucherItem.setCurrency(importPayment.getFactcurrency());
//			//货币金额
//			BankVoucherItem.setAmount(importPayBankItem.getPayamount());
//			//本位币金额
//			BankVoucherItem.setAmount2(importPayBankItem.getPayamount2());
//			//部门
//			BankVoucherItem.setDepid(this.sysDeptJdbcDao.getDeptCodeById(importPayment.getDept_id()));
//			//文本
//			BankVoucherItem.setText(importPayment.getText());
//			//现金流项目
//			BankVoucherItem.setCashflowitem("351");
//			//反记账标识
//			BankVoucherItem.setUncheckflag("");
//			//统驭项目
//			BankVoucherItem.setControlaccount("");
//			//统驭科目说明
//			BankVoucherItem.setCaremark("");
//			//销售订单
//			BankVoucherItem.setSalesorder("");
//			//销售订单行项目号
//			BankVoucherItem.setOrderrowno("");
//			//利润中心
//			BankVoucherItem.setProfitcenter("");
//			//成本中心
//			BankVoucherItem.setCostcenter("");
//			//借/贷标识符
//			BankVoucherItem.setDebitcredit("H");
//			//行号
//			BankVoucherItem.setRownumber(String.valueOf(rowNo));
//			
//			voucherItemList2.add(BankVoucherItem);
//		}
//		//******************付款银行行项目凭证行项目*******************************//
//		//******************结算科目行项目凭证行项目*******************************//
//		Set<VoucherItem> SettlesubjectVoucherItemList2 = new HashSet<VoucherItem>();
//		
//		SettlesubjectVoucherItemList2 = this.getSettSubjVoucherItem(importPayment.getImportSettSubj());
//		
//		Iterator<VoucherItem> Settlesubjectit2 = SettlesubjectVoucherItemList2.iterator();		
//		while(Settlesubjectit2.hasNext()){
//			rowNo = rowNo + 1;
//			VoucherItem setvoucherItem =Settlesubjectit2.next();
//			setvoucherItem.setRownumber(String.valueOf(rowNo));
//			setvoucherItem.setVoucher(voucher2);
//			
//			voucherItemList2.add(setvoucherItem);
//		}
//		//******************结算科目行项目凭证行项目*******************************//
//		
//		voucher2.setVoucherItem(voucherItemList2);
//		this.voucherHibernateDao.save(voucher2);
//		retList.add(voucher2.getVoucherid());
//		
//		//************************处理短期外汇借款清帐凭证***************************
//		this.voucherJdbcDao.deleteVoucherByBusinessidAndClass(importPayment.getPaymentid(), "7");
//		List<Voucher> voucherList = getOverPayLoanClearVendor(importPayment,voucher,voucher2,"7",strSubject,"2");
//		
//		for (int k=0;k<voucherList.size();k++){
//			Voucher returnClearvoucher = voucherList.get(k);
//			
//			this.voucherHibernateDao.save(returnClearvoucher);
//			retList.add(returnClearvoucher.getVoucherid());
//		}
//		//************************处理短期外汇借款清帐凭证***************************
		
		return retList;
	}
		
	//海外代付有中转清帐
	public List<String> differOverpaySaveVoucher(ImportPayment importPayment){
		List<String> retList = new ArrayList<String>();

        // 纯代理
        boolean isRepresentPay = "1".equals(importPayment.getIsrepresentpay()) && "1".equals(importPayment.getIsoverrepay());
		//this.voucherJdbcDao.deleteVoucherByBusinessidAndClass(importPayment.getPaymentid(), "5");
		
		//凭证抬头
		Voucher voucher = new Voucher();
		//凭证行项目
		Set<VoucherItem> voucherItemList = new HashSet<VoucherItem>();
		voucher = this.getVoucher(importPayment, "5");
		
		int rowNo = 1;
		//******************押汇银行行项目凭证行项目**********************************//
		BigDecimal bankAmount2 = new BigDecimal("0");
		BigDecimal bankAmount = new BigDecimal("0");
		BigDecimal assignAmount = new BigDecimal("0");
		
		//******************付款银行行项目凭证行项目*******************************//
		Iterator<ImportPayBankItem> bankItemit = importPayment.getImportPayBankItem().iterator();		
		while(bankItemit.hasNext()){
			rowNo = rowNo + 1;
			ImportPayBankItem importPayBankItem = bankItemit.next();
			
			VoucherItem BankVoucherItem = new VoucherItem();
			BankVoucherItem.setVoucher(voucher);
			//客户记账码
			BankVoucherItem.setCheckcode("50");
			//特殊G/L标识
			BankVoucherItem.setGlflag("");
			//科目
			BankVoucherItem.setSubject(importPayBankItem.getPaybanksubject());
			//科目说明
			BankVoucherItem.setSubjectdescribe(importPayBankItem.getPaybankname());
			//货币
			BankVoucherItem.setCurrency(importPayment.getFactcurrency());
			//货币金额
			BankVoucherItem.setAmount(importPayBankItem.getPayamount());
			bankAmount = bankAmount.add(importPayBankItem.getPayamount());
			//本位币金额
			BankVoucherItem.setAmount2(importPayBankItem.getPayamount2());
			bankAmount2 = bankAmount2.add(importPayBankItem.getPayamount2());
			//部门
			BankVoucherItem.setDepid(this.sysDeptJdbcDao.getDeptCodeById(importPayment.getDept_id()));
			//文本
			BankVoucherItem.setText(importPayment.getText());
			//现金流项目
			BankVoucherItem.setCashflowitem("151");
			if (isRepresentPay) {
	            BankVoucherItem.setCashflowitem(importPayBankItem.getCashflowitem());
			}
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
			
			voucherItemList.add(BankVoucherItem);
		}
		
//		Iterator<ImportDocuBankItem> docBankItemit = importPayment.getImportDocuBankItem().iterator();		
//		while(docBankItemit.hasNext()){
//			rowNo = rowNo + 1;
//			ImportDocuBankItem importPayBankItem = docBankItemit.next();
//			
//			VoucherItem BankVoucherItem = new VoucherItem();
//			BankVoucherItem.setVoucher(voucher);
//			//客户记账码
//			BankVoucherItem.setCheckcode("50");
//			//特殊G/L标识
//			BankVoucherItem.setGlflag("");
//			//科目
//			BankVoucherItem.setSubject(importPayBankItem.getDocuarybanksubj());
//			//科目说明
//			String dabank_hkonrname = this.bankInfoJdbcDao.getDaBankHkonrName(importPayBankItem.getDocuarybankid(), importPayBankItem.getDocuarybanksubj());
//			BankVoucherItem.setSubjectdescribe(dabank_hkonrname);
//			//货币
//			BankVoucherItem.setCurrency(importPayment.getFactcurrency());
//			//货币金额
//			BankVoucherItem.setAmount(importPayBankItem.getDocuarypayamount());
//			bankAmount = bankAmount.add(importPayBankItem.getDocuarypayamount());
//			//本位币金额
//			BankVoucherItem.setAmount2(importPayBankItem.getDocuarypayamoun2());
//			bankAmount2 = bankAmount2.add(importPayBankItem.getDocuarypayamoun2());
//			//部门
//			BankVoucherItem.setDepid(this.sysDeptJdbcDao.getDeptCodeById(importPayment.getDept_id()));
//			//文本
//			BankVoucherItem.setText(importPayment.getText());
//			//现金流项目
//			BankVoucherItem.setCashflowitem("151");
//			//反记账标识
//			BankVoucherItem.setUncheckflag("");
//			//统驭项目
//			BankVoucherItem.setControlaccount("");
//			//统驭科目说明
//			BankVoucherItem.setCaremark("");
//			//销售订单
//			BankVoucherItem.setSalesorder("");
//			//销售订单行项目号
//			BankVoucherItem.setOrderrowno("");
//			//利润中心
//			BankVoucherItem.setProfitcenter("");
//			//成本中心
//			BankVoucherItem.setCostcenter("");
//			//借/贷标识符
//			BankVoucherItem.setDebitcredit("H");
//			//行号
//			BankVoucherItem.setRownumber(String.valueOf(rowNo));
//			
//			voucherItemList.add(BankVoucherItem);
//		}
		//******************押汇银行行项目凭证行项目**********************************//
		//************************应付中转科目**********************************//
		VoucherItem voucherItem = new VoucherItem();
		voucherItem.setVoucher(voucher);
		//客户记账码
		voucherItem.setCheckcode("40");
		//特殊G/L标识
		voucherItem.setGlflag("");
		String bukrs = getCompanyIDByDeptID(importPayment.getDept_id());
		String subjectdescribe = this.customerRefundItemJdbcDao.getSubjectNameById("2202999999",bukrs);
		//科目
		voucherItem.setSubject("2202999999");
		//科目说明
		voucherItem.setSubjectdescribe(subjectdescribe);
		//货币
		voucherItem.setCurrency(importPayment.getFactcurrency());
		//货币金额
		voucherItem.setAmount(bankAmount);
		//本位币金额
		voucherItem.setAmount2(bankAmount2);
		//部门
		voucherItem.setDepid(this.sysDeptJdbcDao.getDeptCodeById(importPayment.getDept_id()));
		//文本
		voucherItem.setText(importPayment.getText());
		//现金流项目
		voucherItem.setCashflowitem("");
		//反记账标识
		voucherItem.setUncheckflag("");
		//统驭项目
		voucherItem.setControlaccount("2202999999");
		//统驭科目说明
		voucherItem.setCaremark(subjectdescribe);

        if (isRepresentPay) {
            // 科目
            voucherItem.setSubject("1122999999");
            // 科目说明
            voucherItem.setSubjectdescribe(this.customerRefundItemJdbcDao.getSubjectNameById("1122999999", bukrs));
            //统驭项目
            voucherItem.setControlaccount("1122999999");
            //统驭科目说明
            voucherItem.setCaremark(this.customerRefundItemJdbcDao.getSubjectNameById("1122999999", bukrs));
        }
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
		
		
		//如果有中转需要取票的币别
		String strCurrency = "";
		if (importPayment.getImportPaymentCbill() != null){
			Iterator<ImportPaymentCbill> importPaymentCbillIteraor = importPayment.getImportPaymentCbill().iterator();
			if (importPaymentCbillIteraor.hasNext()) {
    			ImportPaymentCbill importPaymentCbill = importPaymentCbillIteraor.next();
    			strCurrency = importPaymentCbill.getCurrency();
			}
		}else{
			strCurrency = importPayment.getFactcurrency();
		}
		if ( isRepresentPay ) {
		    strCurrency = "CNY";
		}
		//凭证抬头
		Voucher voucher2 = new Voucher();
		//凭证行项目
		Set<VoucherItem> voucherItemList2 = new HashSet<VoucherItem>();
		//this.voucherJdbcDao.deleteVoucherByBusinessidAndClass(importPayment.getPaymentid(), "2");
		voucher2 = this.getVoucher(importPayment, "2");
		voucher2.setCurrency(strCurrency);
		
		rowNo = 0;
		//******************供应商行项目凭证行项目**********************************//
		BigDecimal allChangeAmount = new BigDecimal("0");
		Iterator<ImportPaymentItem> PayItemItemit = importPayment.getImportPaymentItem().iterator();		
		while(PayItemItemit.hasNext()){
			rowNo = rowNo + 1;
			ImportPaymentItem importPaymentItem = PayItemItemit.next();
			
			VoucherItem itemVoucherItem = new VoucherItem();
			itemVoucherItem.setVoucher(voucher2);
			//客户记账码
			itemVoucherItem.setCheckcode("21");
			
			//特殊G/L标识
			itemVoucherItem.setGlflag("");
			//科目
			itemVoucherItem.setSubject(importPayment.getSupplier());
			//科目说明
			String Subjectdescribe = this.customerRefundItemJdbcDao.getSupplierDescByCustomerId(importPayment.getSupplier(),bukrs);
			itemVoucherItem.setSubjectdescribe(Subjectdescribe);
			//货币
			itemVoucherItem.setCurrency(strCurrency);
			//货币金额
			itemVoucherItem.setAmount(importPaymentItem.getAssignamount());
			allChangeAmount = allChangeAmount.add(importPaymentItem.getAssignamount());
			assignAmount = assignAmount.add(importPaymentItem.getAssignamount());
			//本位币金额
			itemVoucherItem.setAmount2(importPaymentItem.getAssignamount2());
			//部门
			itemVoucherItem.setDepid(this.sysDeptJdbcDao.getDeptCodeById(importPayment.getDept_id()));
			//文本
			itemVoucherItem.setText(importPayment.getText());
			//反记账标识
			itemVoucherItem.setUncheckflag("");
			String subject = this.customerRefundItemJdbcDao.getSubjectBySuppliers(importPayment.getSupplier(),bukrs);
			subject = this.customerRefundItemJdbcDao.getSkont(voucherItem,subject);

            if ( isRepresentPay ) {
            	itemVoucherItem.setCheckcode("01");
                itemVoucherItem.setSubject(importPayment.getRepresentpaycust());
                itemVoucherItem.setSubjectdescribe(this.customerRefundItemJdbcDao.getCustomerDescByCustomerId(importPayment.getRepresentpaycust(),bukrs));
                subject = this.customerRefundItemJdbcDao.getSubjectByCustomer(importPayment.getRepresentpaycust(),bukrs);
                subject = this.customerRefundItemJdbcDao.getSkont(itemVoucherItem,subject);
                //统驭项目
                itemVoucherItem.setControlaccount(subject);
                //统驭科目说明
                itemVoucherItem.setCaremark(this.customerRefundItemJdbcDao.getSubjectNameById(subject,bukrs));
                //货币金额
                itemVoucherItem.setAmount(importPaymentItem.getAssignamount2());
                itemVoucherItem.setAmount2(importPaymentItem.getAssignamount2());
            } else {
              //统驭项目
              voucherItem.setControlaccount(subject);
              //统驭科目说明
              voucherItem.setCaremark(this.customerRefundItemJdbcDao.getSubjectNameById(subject,bukrs));
            }
             
			//销售订单
			itemVoucherItem.setSalesorder("");
			//销售订单行项目号
			itemVoucherItem.setOrderrowno("");
			//利润中心
			itemVoucherItem.setProfitcenter("");
			//成本中心
			itemVoucherItem.setCostcenter("");
			//借/贷标识符
			itemVoucherItem.setDebitcredit("S");
			//行号
			itemVoucherItem.setRownumber(String.valueOf(rowNo));
			//付款行项目信息
			itemVoucherItem.setBusinessitemid(importPaymentItem.getPaymentitemid());
			
			voucherItemList2.add(itemVoucherItem);
		}
		//******************供应商行项目凭证行项目**********************************//
		
		//************************应付中转科目**********************************//
		Set<VoucherItem> SettlesubjectVoucherItemList1 = new HashSet<VoucherItem>();
		
		SettlesubjectVoucherItemList1 = this.getSettSubjVoucherItem(importPayment);
		
		Iterator<VoucherItem> Settlesubjectit1 = SettlesubjectVoucherItemList1.iterator();		
		while(Settlesubjectit1.hasNext()){
			VoucherItem setvoucherItem =Settlesubjectit1.next();
			
			if (setvoucherItem.getDebitcredit().equals("S")){
				allChangeAmount = allChangeAmount.add(setvoucherItem.getAmount());
			}
			
			if (setvoucherItem.getDebitcredit().equals("H")){
				allChangeAmount = allChangeAmount.subtract(setvoucherItem.getAmount());
			}
		}
		
		Set<VoucherItem> FundFlowVoucherItemList1 = new HashSet<VoucherItem>();
		
		FundFlowVoucherItemList1 = this.getFundFlowVoucherItem(importPayment);
		
		Iterator<VoucherItem> FundFlowit1 = FundFlowVoucherItemList1.iterator();		
		while(FundFlowit1.hasNext()){
			VoucherItem funvoucherItem =FundFlowit1.next();
			
			if (funvoucherItem.getDebitcredit().equals("S")){
				allChangeAmount = allChangeAmount.add(funvoucherItem.getAmount());
			}
			
			if (funvoucherItem.getDebitcredit().equals("H")){
				allChangeAmount = allChangeAmount.subtract(funvoucherItem.getAmount());
			}
		}
		
		rowNo = rowNo + 1;
		VoucherItem voucherItem2 = new VoucherItem();
		voucherItem2.setVoucher(voucher2);
		//客户记账码
		voucherItem2.setCheckcode("50");
		//特殊G/L标识
		voucherItem2.setGlflag("");
		String subjectdesc = this.customerRefundItemJdbcDao.getSubjectNameById("2202999999",bukrs);
		//科目
		voucherItem2.setSubject("2202999999");
		//科目说明
		voucherItem2.setSubjectdescribe(subjectdesc);
		//货币
		voucherItem2.setCurrency(strCurrency);
		//货币金额
		voucherItem2.setAmount(allChangeAmount);
		//本位币金额
		voucherItem2.setAmount2(bankAmount2);
		//部门
		voucherItem2.setDepid(this.sysDeptJdbcDao.getDeptCodeById(importPayment.getDept_id()));
		//文本
		voucherItem2.setText(importPayment.getText());
		//现金流项目
		voucherItem2.setCashflowitem("");
		//反记账标识
		voucherItem2.setUncheckflag("");
		//统驭项目
		voucherItem2.setControlaccount("2202999999");
		//统驭科目说明
		voucherItem2.setCaremark(subjectdesc);

        if (isRepresentPay) {
            // 科目
            voucherItem2.setSubject("1122999999");
            // 科目说明
            voucherItem2.setSubjectdescribe(this.customerRefundItemJdbcDao.getSubjectNameById("1122999999", bukrs));
            //统驭项目
            voucherItem2.setControlaccount("1122999999");
            //统驭科目说明
            voucherItem2.setCaremark(this.customerRefundItemJdbcDao.getSubjectNameById("1122999999", bukrs));
          //货币金额
            voucherItem2.setAmount(bankAmount2);
            //本位币金额
            voucherItem2.setAmount2(bankAmount2);
        }
        
		//销售订单
		voucherItem2.setSalesorder("");
		//销售订单行项目号
		voucherItem2.setOrderrowno("");
		//利润中心
		voucherItem2.setProfitcenter("");
		//成本中心
		voucherItem2.setCostcenter("");
		//借/贷标识符
		voucherItem2.setDebitcredit("H");
		//行号
		voucherItem2.setRownumber(String.valueOf(rowNo));
		
		voucherItemList2.add(voucherItem2);
		//************************应付中转科目**********************************// 修改汇差四舍五入
		BigDecimal allAmount = assignAmount.multiply(importPayment.getCloseexchangerat()).setScale(2, BigDecimal.ROUND_HALF_UP);
		//******************结算科目行项目凭证行项目*******************************//
		//结算科目凭证行项目
		Set<VoucherItem> SettlesubjectVoucherItemList = new HashSet<VoucherItem>();
		
		SettlesubjectVoucherItemList = this.getSettSubjVoucherItem(importPayment);
		
		Iterator<VoucherItem> Settlesubjectit = SettlesubjectVoucherItemList.iterator();		
		while(Settlesubjectit.hasNext()){
			rowNo = rowNo + 1;
			VoucherItem setvoucherItem =Settlesubjectit.next();
			setvoucherItem.setRownumber(String.valueOf(rowNo));
//			setvoucherItem.setCheckcode("40");
//			setvoucherItem.setDebitcredit("S");
			setvoucherItem.setCurrency(strCurrency);
			setvoucherItem.setVoucher(voucher2);
			
			voucherItemList2.add(setvoucherItem);
		}
        if (importPayment.getImportSettSubj() != null) {
            if (importPayment.getImportSettSubj().getDebtcredit1() != null
                    && "S".equals(importPayment.getImportSettSubj().getDebtcredit1())) {
                allAmount = allAmount.add(importPayment.getImportSettSubj().getStandardamount1());
            }

            if (importPayment.getImportSettSubj().getDebtcredit1() != null
                    && "H".equals(importPayment.getImportSettSubj().getDebtcredit1())) {
                allAmount = allAmount.subtract(importPayment.getImportSettSubj().getStandardamount1());
            }

            if (importPayment.getImportSettSubj().getDebtcredit2() != null
                    && "S".equals(importPayment.getImportSettSubj().getDebtcredit2())) {
                allAmount = allAmount.add(importPayment.getImportSettSubj().getStandardamount2());
            }

            if (importPayment.getImportSettSubj().getDebtcredit2() != null
                    && "H".equals(importPayment.getImportSettSubj().getDebtcredit2())) {
                allAmount = allAmount.subtract(importPayment.getImportSettSubj().getStandardamount2());
            }

            if (importPayment.getImportSettSubj().getDebtcredit3() != null
                    && "S".equals(importPayment.getImportSettSubj().getDebtcredit3())) {
                allAmount = allAmount.add(importPayment.getImportSettSubj().getStandardamount3());
            }

            if (importPayment.getImportSettSubj().getDebtcredit3() != null
                    && "H".equals(importPayment.getImportSettSubj().getDebtcredit3())) {
                allAmount = allAmount.subtract(importPayment.getImportSettSubj().getStandardamount3());
            }

            if (importPayment.getImportSettSubj().getDebtcredit4() != null
                    && "S".equals(importPayment.getImportSettSubj().getDebtcredit4())) {
                allAmount = allAmount.add(importPayment.getImportSettSubj().getStandardamount4());
            }

            if (importPayment.getImportSettSubj().getDebtcredit4() != null
                    && "H".equals(importPayment.getImportSettSubj().getDebtcredit4())) {
                allAmount = allAmount.subtract(importPayment.getImportSettSubj().getStandardamount4());
            }
        }
		//******************结算科目行项目凭证行项目*******************************//
		
		//******************特殊总帐行项目凭证行项目*******************************//
		//结算科目凭证行项目
		Set<VoucherItem> FundFlowVoucherItemList = new HashSet<VoucherItem>();
		
		FundFlowVoucherItemList = this.getFundFlowVoucherItem(importPayment);
		
		Iterator<VoucherItem> FundFlowit = FundFlowVoucherItemList.iterator();		
		while(FundFlowit.hasNext()){
			rowNo = rowNo + 1;
			VoucherItem funvoucherItem =FundFlowit.next();
			funvoucherItem.setRownumber(String.valueOf(rowNo));
			funvoucherItem.setCurrency(strCurrency);
			funvoucherItem.setVoucher(voucher2);
			
			voucherItemList2.add(funvoucherItem);
		}
		 if (importPayment.getImportFundFlow() != null) {
		    if (importPayment.getImportFundFlow().getDebtcredit1() != null
                    && "S".equals(importPayment.getImportFundFlow().getDebtcredit1())) {
                allAmount = allAmount.add(importPayment.getImportFundFlow().getStandardamount1());
            }

            if (importPayment.getImportFundFlow().getDebtcredit1() != null
                    && "H".equals(importPayment.getImportFundFlow().getDebtcredit1())) {
                allAmount = allAmount.subtract(importPayment.getImportFundFlow().getStandardamount1());
            }

            if (importPayment.getImportFundFlow().getDebtcredit2() != null
                    && "S".equals(importPayment.getImportFundFlow().getDebtcredit2())) {
                allAmount = allAmount.add(importPayment.getImportFundFlow().getStandardamount2());
            }

            if (importPayment.getImportFundFlow().getDebtcredit2() != null
                    && "H".equals(importPayment.getImportFundFlow().getDebtcredit2())) {
                allAmount = allAmount.subtract(importPayment.getImportFundFlow().getStandardamount2());
            }

            if (importPayment.getImportFundFlow().getDebtcredit3() != null
                    && "S".equals(importPayment.getImportFundFlow().getDebtcredit3())) {
                allAmount = allAmount.add(importPayment.getImportFundFlow().getStandardamount3());
            }

            if (importPayment.getImportFundFlow().getDebtcredit3() != null
                    && "H".equals(importPayment.getImportFundFlow().getDebtcredit3())) {
                allAmount = allAmount.subtract(importPayment.getImportFundFlow().getStandardamount3());
            }
		}
		//******************特殊总帐行项目凭证行项目*******************************//
		
		// 汇差
		 if (isRepresentPay) {
        BigDecimal subtractValue = allAmount.subtract(bankAmount2);
        if (subtractValue.signum() == 1 ) {
            rowNo = rowNo + 1;
            VoucherItem profLossVoucherItem = new VoucherItem();
            profLossVoucherItem.setVoucher(voucher2);

            profLossVoucherItem.setCheckcode("50");
            // 特殊G/L标识
            profLossVoucherItem.setGlflag("");
            // 科目
            profLossVoucherItem.setSubject("6603050402");
            // 科目说明
            profLossVoucherItem.setSubjectdescribe("财务费用-进口汇兑收益");
            // 货币
            profLossVoucherItem.setCurrency(importPayment.getFactcurrency());
            // 货币金额
            profLossVoucherItem.setAmount(new BigDecimal("0"));
            if (isRepresentPay) {
                profLossVoucherItem.setCurrency("CNY");
                profLossVoucherItem.setAmount(subtractValue.abs());
            }
            // 本位币金额
            profLossVoucherItem.setAmount2(subtractValue.abs());
            // 部门
            profLossVoucherItem.setDepid(importPayment.getRedocarybc());
            // 文本
            profLossVoucherItem.setText(importPayment.getText());
            // 现金流项目
            profLossVoucherItem.setCashflowitem("");
            // 反记账标识
            profLossVoucherItem.setUncheckflag("");
            // 统驭项目
            profLossVoucherItem.setControlaccount("");
            // 统驭科目说明
            profLossVoucherItem.setCaremark("");
            // 销售订单
            profLossVoucherItem.setSalesorder("");
            // 销售订单行项目号
            profLossVoucherItem.setOrderrowno("");
            // 利润中心
            profLossVoucherItem.setProfitcenter("");
            // 成本中心
            profLossVoucherItem.setCostcenter("");
            // 借/贷标识符
            profLossVoucherItem.setDebitcredit("H");
            // 行号
            profLossVoucherItem.setRownumber(String.valueOf(rowNo));

            voucherItemList2.add(profLossVoucherItem);
        }
        if (subtractValue.signum() == -1) {
            rowNo = rowNo + 1;
            VoucherItem profLossVoucherItem = new VoucherItem();
            profLossVoucherItem.setVoucher(voucher2);

            profLossVoucherItem.setCheckcode("40");

            // 特殊G/L标识
            profLossVoucherItem.setGlflag("");
            // 科目
            profLossVoucherItem.setSubject("6603050401");
            // 科目说明
            profLossVoucherItem.setSubjectdescribe("财务费用-进口汇兑损失");
            // 货币
            profLossVoucherItem.setCurrency(importPayment.getFactcurrency());
            // 货币金额
            profLossVoucherItem.setAmount(new BigDecimal("0"));
            // 本位币金额
            profLossVoucherItem.setAmount2(subtractValue.abs());
            if (isRepresentPay) {
                profLossVoucherItem.setCurrency("CNY");
                profLossVoucherItem.setAmount(subtractValue.abs());
            }
            // 部门
            profLossVoucherItem.setDepid(importPayment.getRedocarybc());
            // 文本
            profLossVoucherItem.setText(importPayment.getText());
            // 现金流项目
            profLossVoucherItem.setCashflowitem("");
            // 反记账标识
            profLossVoucherItem.setUncheckflag("");
            // 统驭项目
            profLossVoucherItem.setControlaccount("");
            // 统驭科目说明
            profLossVoucherItem.setCaremark("");
            // 销售订单
            profLossVoucherItem.setSalesorder("");
            // 销售订单行项目号
            profLossVoucherItem.setOrderrowno("");
            // 利润中心
            profLossVoucherItem.setProfitcenter("");
            // 成本中心
            profLossVoucherItem.setCostcenter("");
            // 借/贷标识符
            profLossVoucherItem.setDebitcredit("S");
            // 行号
            profLossVoucherItem.setRownumber(String.valueOf(rowNo));
            voucherItemList2.add(profLossVoucherItem);
        }
	}
		
		voucher2.setVoucherItem(voucherItemList2);
		this.voucherHibernateDao.save(voucher2);
		retList.add(voucher2.getVoucherid());
		
//		//凭证抬头
//		Voucher voucher3 = new Voucher();
//		//凭证行项目
//		Set<VoucherItem> voucherItemList3 = new HashSet<VoucherItem>();
//		this.voucherJdbcDao.deleteVoucherByBusinessidAndClass(importPayment.getPaymentid(), "3");
//		voucher3 = this.getVoucher(importPayment, "3");
//		
//		rowNo = 0;
//		//******************押汇银行行项目凭证行项目**********************************//
//		String strSubject ="";
//		Iterator<ImportDocuBankItem> docBankItemit3 = importPayment.getImportDocuBankItem().iterator();		
//		while(docBankItemit3.hasNext()){
//			rowNo = rowNo + 1;
//			ImportDocuBankItem importPayBankItem = docBankItemit3.next();
//			
//			VoucherItem BankVoucherItem = new VoucherItem();
//			BankVoucherItem.setVoucher(voucher3);
//			//客户记账码
//			BankVoucherItem.setCheckcode("40");
//			//特殊G/L标识
//			BankVoucherItem.setGlflag("");
//			//科目
//			BankVoucherItem.setSubject(importPayBankItem.getDocuarybanksubj());
//			strSubject = strSubject + importPayBankItem.getDocuarybanksubj() + "','";
//			//科目说明
//			String dabank_hkonrname = this.bankInfoJdbcDao.getDaBankHkonrName(importPayBankItem.getDocuarybankid(), importPayBankItem.getDocuarybanksubj());
//			BankVoucherItem.setSubjectdescribe(dabank_hkonrname);
//			//货币
//			BankVoucherItem.setCurrency(importPayment.getFactcurrency());
//			//货币金额
//			BankVoucherItem.setAmount(importPayBankItem.getDocuarypayamount());
//			//本位币金额
//			BankVoucherItem.setAmount2(importPayBankItem.getDocuarypayamoun2());
//			//部门
//			BankVoucherItem.setDepid(this.sysDeptJdbcDao.getDeptCodeById(importPayment.getDept_id()));
//			//文本
//			BankVoucherItem.setText(importPayment.getText());
//			//现金流项目
//			BankVoucherItem.setCashflowitem("");
//			//反记账标识
//			BankVoucherItem.setUncheckflag("");
//			//统驭项目
//			BankVoucherItem.setControlaccount("");
//			//统驭科目说明
//			BankVoucherItem.setCaremark("");
//			//销售订单
//			BankVoucherItem.setSalesorder("");
//			//销售订单行项目号
//			BankVoucherItem.setOrderrowno("");
//			//利润中心
//			BankVoucherItem.setProfitcenter("");
//			//成本中心
//			BankVoucherItem.setCostcenter("");
//			//借/贷标识符
//			BankVoucherItem.setDebitcredit("S");
//			//行号
//			BankVoucherItem.setRownumber(String.valueOf(rowNo));
//			
//			voucherItemList3.add(BankVoucherItem);
//		}
//		//******************押汇银行行项目凭证行项目**********************************//
//		//******************付款银行行项目凭证行项目*******************************//
//		Iterator<ImportPayBankItem> bankItemit = importPayment.getImportPayBankItem().iterator();		
//		while(bankItemit.hasNext()){
//			rowNo = rowNo + 1;
//			ImportPayBankItem importPayBankItem = bankItemit.next();
//			
//			VoucherItem BankVoucherItem = new VoucherItem();
//			BankVoucherItem.setVoucher(voucher3);
//			//客户记账码
//			BankVoucherItem.setCheckcode("50");
//			//特殊G/L标识
//			BankVoucherItem.setGlflag("");
//			//科目
//			BankVoucherItem.setSubject(importPayBankItem.getPaybanksubject());
//			//科目说明
//			BankVoucherItem.setSubjectdescribe(importPayBankItem.getPaybankname());
//			//货币
//			BankVoucherItem.setCurrency(importPayment.getFactcurrency());
//			//货币金额
//			BankVoucherItem.setAmount(importPayBankItem.getPayamount());
//			//本位币金额
//			BankVoucherItem.setAmount2(importPayBankItem.getPayamount2());
//			//部门
//			BankVoucherItem.setDepid(this.sysDeptJdbcDao.getDeptCodeById(importPayment.getDept_id()));
//			//文本
//			BankVoucherItem.setText(importPayment.getText());
//			//现金流项目
//			BankVoucherItem.setCashflowitem("351");
//			//反记账标识
//			BankVoucherItem.setUncheckflag("");
//			//统驭项目
//			BankVoucherItem.setControlaccount("");
//			//统驭科目说明
//			BankVoucherItem.setCaremark("");
//			//销售订单
//			BankVoucherItem.setSalesorder("");
//			//销售订单行项目号
//			BankVoucherItem.setOrderrowno("");
//			//利润中心
//			BankVoucherItem.setProfitcenter("");
//			//成本中心
//			BankVoucherItem.setCostcenter("");
//			//借/贷标识符
//			BankVoucherItem.setDebitcredit("H");
//			//行号
//			BankVoucherItem.setRownumber(String.valueOf(rowNo));
//			
//			voucherItemList3.add(BankVoucherItem);
//		}
//		//******************付款银行行项目凭证行项目*******************************//
//		//******************结算科目行项目凭证行项目*******************************//
//		/*Set<VoucherItem> SettlesubjectVoucherItemList2 = new HashSet<VoucherItem>();
//		
//		SettlesubjectVoucherItemList2 = this.getSettSubjVoucherItem(importPayment.getImportSettSubj());
//		
//		Iterator<VoucherItem> Settlesubjectit2 = SettlesubjectVoucherItemList2.iterator();		
//		while(Settlesubjectit2.hasNext()){
//			rowNo = rowNo + 1;
//			VoucherItem setvoucherItem =Settlesubjectit2.next();
//			setvoucherItem.setRownumber(String.valueOf(rowNo));
//			setvoucherItem.setVoucher(voucher3);
//			
//			voucherItemList3.add(setvoucherItem);
//		}*/
//		//******************结算科目行项目凭证行项目*******************************//
//		
//		voucher3.setVoucherItem(voucherItemList3);
//		this.voucherHibernateDao.save(voucher3);
//		retList.add(voucher3.getVoucherid());
//		
//		//************************处理短期外汇借款清帐凭证***************************
//		this.voucherJdbcDao.deleteVoucherByBusinessidAndClass(importPayment.getPaymentid(), "7");
//		List<Voucher> voucherList = getOverPayLoanClearVendor(importPayment,voucher,voucher3,"7",strSubject,"5");
//		
//		for (int k=0;k<voucherList.size();k++){
//			Voucher returnClearvoucher = voucherList.get(k);
//			
//			this.voucherHibernateDao.save(returnClearvoucher);
//			retList.add(returnClearvoucher.getVoucherid());
//		}
//		//************************处理短期外汇借款清帐凭证***************************
		
		return retList;
	}
	
	//短期外汇借款
	public List<String> shortTimepaySaveVoucher(ImportPayment importPayment){
		List<String> retList = new ArrayList<String>();
		
		//this.voucherJdbcDao.deleteVoucherByBusinessidAndClass(importPayment.getPaymentid(), "2");
		String bukrs = getCompanyIDByDeptID(importPayment.getDept_id());
		//凭证抬头
		Voucher voucher = new Voucher();
		//凭证行项目
		Set<VoucherItem> voucherItemList = new HashSet<VoucherItem>();
		voucher = this.getVoucher(importPayment, "2");
		voucher.setCurrency(importPayment.getCurrency2());
		
		int rowNo = 0;
		//******************押汇银行行项目凭证行项目**********************************//
		ImportDocuBankItem importDocuBankItem = new ImportDocuBankItem();
		String dabank_hkonrname = "";
		Iterator<ImportDocuBankItem> docBankItemit = importPayment.getImportDocuBankItem().iterator();		
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
			BankVoucherItem.setCurrency(importPayment.getCurrency2());
			//货币金额
			BankVoucherItem.setAmount(importDocuBankItem.getDocuarypayamount());
			//本位币金额
			BankVoucherItem.setAmount2(importDocuBankItem.getDocuarypayamoun2());
			//部门
			BankVoucherItem.setDepid(importPayment.getRedocarybc());
			//文本
			BankVoucherItem.setText(importPayment.getText());
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
		Iterator<ImportDocuBankItem> bankItemit = importPayment.getImportDocuBankItem().iterator();		
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
			BankVoucherItem.setCurrency(importPayment.getCurrency2());
			//货币金额
			BankVoucherItem.setAmount(importDocuBankItem.getDocuarypayamount());
			//本位币金额
			BankVoucherItem.setAmount2(importDocuBankItem.getDocuarypayamoun2());
			//部门
			BankVoucherItem.setDepid(importPayment.getRedocarybc());
			//文本
			BankVoucherItem.setText(importPayment.getText());
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
		//this.voucherJdbcDao.deleteVoucherByBusinessidAndClass(importPayment.getPaymentid(), "1");
		voucher2 = this.getVoucher(importPayment, "1");
		
		rowNo = 0;
		
		//******************供应商行项目凭证行项目**********************************//
		Iterator<ImportPaymentItem> PayItemItemit = importPayment.getImportPaymentItem() .iterator();		
		while(PayItemItemit.hasNext()){
			rowNo = rowNo + 1;
			ImportPaymentItem importPaymentItem = PayItemItemit.next();
			
			VoucherItem voucherItem = new VoucherItem();
			voucherItem.setVoucher(voucher2);
			//客户记账码
			voucherItem.setCheckcode("21");
			//特殊G/L标识
			voucherItem.setGlflag("");
			//科目
			voucherItem.setSubject(importPayment.getSupplier());
			//科目说明
			String Subjectdescribe = this.customerRefundItemJdbcDao.getSupplierDescByCustomerId(importPayment.getSupplier(),bukrs);
			voucherItem.setSubjectdescribe(Subjectdescribe);
			//货币
			voucherItem.setCurrency(importPayment.getFactcurrency());
			//货币金额
			voucherItem.setAmount(importPaymentItem.getAssignamount());
			//本位币金额
			voucherItem.setAmount2(importPaymentItem.getAssignamount2());
			//部门
			voucherItem.setDepid(this.sysDeptJdbcDao.getDeptCodeById(importPayment.getDept_id()));
			//文本
			voucherItem.setText(importPayment.getText());
			//现金流项目
			voucherItem.setCashflowitem("");
			//反记账标识
			voucherItem.setUncheckflag("");
			String subject = this.customerRefundItemJdbcDao.getSubjectBySuppliers(importPayment.getSupplier(),bukrs);
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
			voucherItem.setBusinessitemid(importPaymentItem.getPaymentitemid());
			
			voucherItemList2.add(voucherItem);
		}
		//******************供应商行项目凭证行项目**********************************//
		
		//******************付款银行行项目凭证行项目*******************************//
		Iterator<ImportPayBankItem> bankItemit2 = importPayment.getImportPayBankItem().iterator();		
		while(bankItemit2.hasNext()){
			rowNo = rowNo + 1;
			ImportPayBankItem importPayBankItem = bankItemit2.next();
			
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
			BankVoucherItem.setCurrency(importPayment.getFactcurrency());
			//货币金额
			BankVoucherItem.setAmount(importPayBankItem.getPayamount());
			//本位币金额
			BankVoucherItem.setAmount2(importPayBankItem.getPayamount2());
			//部门
			BankVoucherItem.setDepid(this.sysDeptJdbcDao.getDeptCodeById(importPayment.getDept_id()));
			//文本
			BankVoucherItem.setText(importPayment.getText());
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
		
		SettlesubjectVoucherItemList = this.getSettSubjVoucherItem(importPayment);
		
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
		
		FundFlowVoucherItemList = this.getFundFlowVoucherItem(importPayment);
		
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
	public List<String> returnDiffShortTimeSaveVoucher(ImportPayment importPayment){
		//this.voucherJdbcDao.deleteVoucherByBusinessidAndClass(importPayment.getPaymentid(), "3");
		List<String> retList = new ArrayList<String>();
		
		String bukrs = getCompanyIDByDeptID(importPayment.getDept_id());
		//凭证抬头
		Voucher voucher = new Voucher();
		//凭证行项目
		Set<VoucherItem> voucherItemList = new HashSet<VoucherItem>();
		voucher = this.getVoucher(importPayment, "3");
		voucher.setCurrency(importPayment.getCurrency2());
		
		String strSubject = "";
		int rowNo = 1;
		ImportPayBankItem importPayBankItem = new ImportPayBankItem();
		//******************付款银行行项目凭证行项目*******************************//
		Iterator<ImportPayBankItem> bankItemit = importPayment.getImportPayBankItem().iterator();		
		while(bankItemit.hasNext()){
			rowNo = rowNo + 1;
			importPayBankItem = bankItemit.next();
			
			VoucherItem BankVoucherItem = new VoucherItem();
			BankVoucherItem.setVoucher(voucher);
			//客户记账码
			BankVoucherItem.setCheckcode("50");
			//特殊G/L标识
			BankVoucherItem.setGlflag("");
			List<VoucherItem>  voucheritemList2 = this.voucherItemJdbcDao.getVoucherItemList2(importPayment.getPaymentid(),"02","2");
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
			BankVoucherItem.setCurrency(importPayment.getCurrency2());
			//货币金额
			BankVoucherItem.setAmount(importPayBankItem.getPayamount());
			//本位币金额
			BankVoucherItem.setAmount2(importPayBankItem.getPayamount2());
			//部门
			BankVoucherItem.setDepid(this.sysDeptJdbcDao.getDeptCodeById(importPayment.getDept_id()));
			//文本
			BankVoucherItem.setText(importPayment.getText());
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
		voucherItem.setCurrency(importPayment.getFactcurrency());
		//货币金额
		voucherItem.setAmount(importPayBankItem.getPayamount());
		//本位币金额
		voucherItem.setAmount2(importPayBankItem.getPayamount2());
		//部门
		voucherItem.setDepid(this.sysDeptJdbcDao.getDeptCodeById(importPayment.getDept_id()));
		//文本
		voucherItem.setText(importPayment.getText());
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
		
		voucher = this.getVoucher(importPayment, "3");
		voucherItemList.clear();
		rowNo = 0;
		//******************押汇银行行项目凭证行项目**********************************//
		ImportDocuBankItem importDocuBankItem = new ImportDocuBankItem();
		Iterator<ImportDocuBankItem> docBankItemit = importPayment.getImportDocuBankItem().iterator();		
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
			BankVoucherItem.setCurrency(importPayment.getCurrency2());
			//货币金额
			BankVoucherItem.setAmount(importDocuBankItem.getDocuarypayamount());
			//本位币金额
			BankVoucherItem.setAmount2(importDocuBankItem.getDocuarypayamoun2());
			//部门
			BankVoucherItem.setDepid(this.sysDeptJdbcDao.getDeptCodeById(importPayment.getDept_id()));
			//文本
			BankVoucherItem.setText(importPayment.getText());
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
		_voucherItem.setCurrency(importPayment.getFactcurrency());
		//货币金额
		_voucherItem.setAmount(importPayBankItem.getPayamount());
		//本位币金额
		_voucherItem.setAmount2(importPayBankItem.getPayamount2());
		//部门
		_voucherItem.setDepid(this.sysDeptJdbcDao.getDeptCodeById(importPayment.getDept_id()));
		//文本
		_voucherItem.setText(importPayment.getText());
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

		SettlesubjectVoucherItemList = this.getSettSubjVoucherItem(importPayment);
		
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
		//this.voucherJdbcDao.deleteVoucherByBusinessidAndClass(importPayment.getPaymentid(), "7");
		List<Voucher> voucherList = getReturnLoanClearVendor(importPayment,voucher,"7",strSubject,"2");

		for (int k=0;k<voucherList.size();k++){
			Voucher returnClearvoucher = voucherList.get(k);
			
			this.voucherHibernateDao.save(returnClearvoucher);
			retList.add(returnClearvoucher.getVoucherid());
		}
		//************************处理短期外汇借款清帐凭证***************************
		return retList;
	}
	
	//还短期外汇借款
	public List<String> returnShortTimeSaveVoucher(ImportPayment importPayment){
		List<String> retList = new ArrayList<String>();
		
        //得到票的本位币之和
        BigDecimal billValue = new BigDecimal("0");
        //得到分配项的本位币之和
        BigDecimal itemValue = new BigDecimal("0");
        //分配项之和和票之和的差
        BigDecimal subtractVlaue = new BigDecimal("0");
        
		//this.voucherJdbcDao.deleteVoucherByBusinessidAndClass(importPayment.getPaymentid(), "3");
		String bukrs = getCompanyIDByDeptID(importPayment.getDept_id());
		//凭证抬头
		Voucher voucher = new Voucher();
		//凭证行项目
		Set<VoucherItem> voucherItemList = new HashSet<VoucherItem>();
		voucher = this.getVoucher(importPayment, "3");
		voucher.setCurrency(importPayment.getCurrency2());
		voucher.setExchangerate(importPayment.getRedocaryrate());
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
		List<VoucherItem>  voucheritemList2 = this.voucherItemJdbcDao.getVoucherItemList2(importPayment.getPaymentid(),"02","2");
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
				voucherItem.setAmount(importPayment.getRedocaryamount());
				//本位币金额
				voucherItem.setAmount2(importPayment.getRedocaryamount().multiply(importPayment.getDocumentaryrate()).setScale(2, BigDecimal.ROUND_HALF_UP));
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
		voucherItem.setCurrency(importPayment.getCurrency2());
		//部门
		voucherItem.setDepid(importPayment.getRedocarybc());
		//文本
		voucherItem.setText(importPayment.getText());
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
//		Iterator<ImportPayBankItem> bankItemit = importPayment.getImportPayBankItem().iterator();		
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
//			bankVoucherItem.setCurrency(importPayment.getCurrency2());
//			//货币金额
//			bankVoucherItem.setAmount(importPayBankItem.getPayamount());
//			//本位币金额
//			bankVoucherItem.setAmount2(importPayBankItem.getPayamount2());
//			itemValue = itemValue.add(bankVoucherItem.getAmount2());
//			//部门
//			bankVoucherItem.setDepid(importPayment.getRedocarybc());
//			//文本
//			bankVoucherItem.setText(importPayment.getText());
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
		Iterator<BillPayReBankItem> bankItemit = importPayment.getBillPayReBankItem().iterator();		
		VoucherItem bankVoucherItem = null;
		while(bankItemit.hasNext()){
			rowNo = rowNo + 1;
			BillPayReBankItem billPayReBankItem = bankItemit.next();
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
				bankVoucherItem.setCurrency(importPayment.getCurrency2());
				//货币金额
				bankVoucherItem.setAmount(billPayReBankItem.getRealmoney());
				//本位币金额
				bankVoucherItem.setAmount2(billPayReBankItem.getRealmoney2());
				itemValue = itemValue.add(bankVoucherItem.getAmount2());
				//部门
				bankVoucherItem.setDepid(importPayment.getRedocarybc());
				//文本
				bankVoucherItem.setText(importPayment.getText());
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

		SettlesubjectVoucherItemList = this.getSettSubjVoucherItem(importPayment);
		
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
        
        fundFlowVoucherItemList = this.getFundFlowVoucherItem(importPayment);
        
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
            
//            if(importPayment.getRedocarybc().equals("2199")){
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
            profOrLossVoucherItem.setCurrency(importPayment.getFactcurrency());
            //货币金额
            profOrLossVoucherItem.setAmount(new BigDecimal("0"));
            //本位币金额
            profOrLossVoucherItem.setAmount2(subtractVlaue.abs());
            //部门
            profOrLossVoucherItem.setDepid(importPayment.getRedocarybc());
            //文本
            profOrLossVoucherItem.setText(importPayment.getText());
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
		//this.voucherJdbcDao.deleteVoucherByBusinessidAndClass(importPayment.getPaymentid(), "7");
		//如果累计还押汇金额+本次还押汇金额=押汇金额才出短期外汇借款清帐凭证
		if(importPayment.getRedocaryamount2().add(importPayment.getRedocaryamount()).compareTo(docaryamount) ==0){
			List<Voucher> voucherList = getReturnLoanClearVendor(importPayment,vou,"7",strSubject,"2");
	
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
	 * 应付票据模拟凭证
	 * @param importPayment
	 * @return
	 */
	public List<String> dealBillSaveVoucher(ImportPayment importPayment){
		List<String> retList = new ArrayList<String>();
		String bukrs = getCompanyIDByDeptID(importPayment.getDept_id());
		String depid = this.sysDeptJdbcDao.getDeptCodeById(importPayment.getDept_id());
		//this.voucherJdbcDao.deleteVoucherByBusinessidAndClass(importPayment.getPaymentid(), "1");
		
		//凭证抬头
		Voucher voucher = new Voucher();
		//凭证行项目
		Set<VoucherItem> voucherItemList = new HashSet<VoucherItem>();
		
		voucher = this.getVoucher(importPayment, "1");
		voucher.setPay("X");
		voucher.setFlag("W");
		
		int rowNo = 0;
		//******************供应商行项目凭证行项目**********************************//
		Iterator<ImportPaymentItem> PayItemItemit = importPayment.getImportPaymentItem() .iterator();		
		while(PayItemItemit.hasNext()){
			rowNo = rowNo + 1;
			ImportPaymentItem importPaymentItem = PayItemItemit.next();
			
			VoucherItem voucherItem = new VoucherItem();
			voucherItem.setVoucher(voucher);
			//客户记账码
			voucherItem.setCheckcode("21");
			//特殊G/L标识
			voucherItem.setGlflag("");
			//科目
			voucherItem.setSubject(importPayment.getSupplier());
			//科目说明
			String Subjectdescribe = this.customerRefundItemJdbcDao.getSupplierDescByCustomerId(importPayment.getSupplier(),bukrs);
			voucherItem.setSubjectdescribe(Subjectdescribe);
			//货币
			voucherItem.setCurrency("CNY");
			//货币金额
			voucherItem.setAmount(importPaymentItem.getAssignamount());
			//本位币金额
			voucherItem.setAmount2(importPaymentItem.getAssignamount2());
			//部门
			voucherItem.setDepid(depid);
			//文本
			voucherItem.setText(importPayment.getText());
			//现金流项目
			voucherItem.setCashflowitem("");
			//反记账标识
			voucherItem.setUncheckflag("");
			String subject = this.customerRefundItemJdbcDao.getSubjectBySuppliers(importPayment.getSupplier(),bukrs);
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
			voucherItem.setBusinessitemid(importPaymentItem.getPaymentitemid());
			//业务范围
			voucherItem.setGsber(depid);
			//票据到期日
//			voucherItem.setZfbdt(importPayment.getDraftdate().replace("-", ""));
			voucherItem.setZfbdt(importPayment.getPaydate().replace("-", ""));
			voucherItemList.add(voucherItem);
		}
		//******************供应商行项目凭证行项目**********************************//
		
		//******************供应商行项目凭证行项目**********************************//
		//Iterator<importPaymentItem> PayItemItemit2 = importPayment.getimportPaymentItem() .iterator();		
		//while(PayItemItemit2.hasNext()){
			rowNo = rowNo + 1;
			//importPaymentItem importPaymentItem = PayItemItemit2.next();
			
			VoucherItem voucherItem = new VoucherItem();
			voucherItem.setVoucher(voucher);
			//客户记账码
			voucherItem.setCheckcode("39");
			//特殊G/L标识
			voucherItem.setGlflag("W");
			//国内信用证
			if(importPayment.getPaymenttype().equals("06") || importPayment.getPaymenttype().equals("15") || importPayment.getPaymenttype().equals("16") ){
				//科目
				voucherItem.setSubject(importPayment.getTicketbankid());
				//科目说明
				String Subjectdescribe = this.customerRefundItemJdbcDao.getSupplierDescByCustomerId(importPayment.getTicketbankid(),bukrs);
				voucherItem.setSubjectdescribe(Subjectdescribe);
				String subject = this.customerRefundItemJdbcDao.getSubjectBySuppliers(importPayment.getTicketbankid(),bukrs);
				subject = this.customerRefundItemJdbcDao.getSkont(voucherItem,subject);
				//统驭项目
				voucherItem.setControlaccount(subject);
				//统驭科目说明
				voucherItem.setCaremark(this.customerRefundItemJdbcDao.getSubjectNameById(subject,bukrs));
			}//银行/商业承兑汇票
			else if(importPayment.getPaymenttype().equals("07")){
				//科目
				voucherItem.setSubject(importPayment.getSupplier());
				//科目说明
				String Subjectdescribe = this.customerRefundItemJdbcDao.getSupplierDescByCustomerId(importPayment.getSupplier(),bukrs);
				voucherItem.setSubjectdescribe(Subjectdescribe);
				String subject = this.customerRefundItemJdbcDao.getSubjectBySuppliers(importPayment.getSupplier(),bukrs);
				subject = this.customerRefundItemJdbcDao.getSkont(voucherItem,subject);
				//统驭项目
				voucherItem.setControlaccount(subject);
				//统驭科目说明
				voucherItem.setCaremark(this.customerRefundItemJdbcDao.getSubjectNameById(subject,bukrs));
			}

			//货币
			voucherItem.setCurrency("CNY");
			//货币金额
			voucherItem.setAmount(importPayment.getFactamount());
			//本位币金额
			voucherItem.setAmount2(importPayment.getFactamount());
			//部门
			voucherItem.setDepid(importPayment.getBillbc());
			//文本
			voucherItem.setText(importPayment.getText());
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
			voucherItem.setGsber(importPayment.getBillbc());
			//票据到期日
//			voucherItem.setZfbdt(importPayment.getDraftdate().replace("-", ""));
			voucherItem.setZfbdt(importPayment.getPaydate().replace("-", ""));
			
			voucherItemList.add(voucherItem);
		//}
		//******************供应商行项目凭证行项目**********************************//
		
		
//		//******************结算科目行项目凭证行项目*******************************//
//		Set<VoucherItem> SettlesubjectVoucherItemList = new HashSet<VoucherItem>();
//		
//		SettlesubjectVoucherItemList = this.getSettSubjVoucherItem(importPayment);
//		
//		Iterator<VoucherItem> Settlesubjectit = SettlesubjectVoucherItemList.iterator();		
//		while(Settlesubjectit.hasNext()){
//			rowNo = rowNo + 1;
//			VoucherItem setvoucherItem =Settlesubjectit.next();
//			setvoucherItem.setRownumber(String.valueOf(rowNo));
//			//是否票据标志
//			setvoucherItem.setFlag("W");
//			//业务范围
//			setvoucherItem.setDepid(depid);
//			//业务范围
//			setvoucherItem.setGsber(depid);
//			//票据到期日
//			setvoucherItem.setZfbdt(importPayment.getDraftdate().replace("-", ""));
//			setvoucherItem.setVoucher(voucher);
//			
//			voucherItemList.add(setvoucherItem);
//		}
//		//******************结算科目行项目凭证行项目*******************************//
//		
//		//******************特殊总帐行项目凭证行项目*******************************//
//		Set<VoucherItem> FundFlowVoucherItemList = new HashSet<VoucherItem>();
//		
//		FundFlowVoucherItemList = this.getFundFlowVoucherItem(importPayment);
//		
//		Iterator<VoucherItem> FundFlowit = FundFlowVoucherItemList.iterator();		
//		while(FundFlowit.hasNext()){
//			rowNo = rowNo + 1;
//			VoucherItem funvoucherItem =FundFlowit.next();
//			//是否票据标志
//			funvoucherItem.setFlag("W");
//			//业务范围
//			funvoucherItem.setDepid(depid);
//			//业务范围
//			funvoucherItem.setGsber(depid);
//			//票据到期日
//			funvoucherItem.setZfbdt(importPayment.getDraftdate().replace("-", ""));
//			funvoucherItem.setRownumber(String.valueOf(rowNo));
//			funvoucherItem.setVoucher(voucher);
//			
//			voucherItemList.add(funvoucherItem);
//		}
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
	public List<String> dealBillClearSaveVoucher(ImportPayment importPayment){
		List<String> retList = new ArrayList<String>();
		String depId = this.sysDeptJdbcDao.getDeptCodeById(importPayment.getDept_id());
		String bukrs = getCompanyIDByDeptID(importPayment.getDept_id());
		
		//this.voucherJdbcDao.deleteVoucherByBusinessidAndClass(importPayment.getPaymentid(), "4");
		
		//凭证抬头
		Voucher voucher = new Voucher();
		//凭证行项目
		Set<VoucherItem> voucherItemList = new HashSet<VoucherItem>();
		
		voucher = this.getVoucher(importPayment, "4");
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
		if(importPayment.getPaymenttype().equals("06") || importPayment.getPaymenttype().equals("15") || importPayment.getPaymenttype().equals("16") ){
			//科目
			voucherItem.setSubject(importPayment.getTicketbankid());
			//科目说明
			String Subjectdescribe = this.customerRefundItemJdbcDao.getSupplierDescByCustomerId(importPayment.getTicketbankid(),bukrs);
			voucherItem.setSubjectdescribe(Subjectdescribe);
			String subject = this.customerRefundItemJdbcDao.getSubjectBySuppliers(importPayment.getTicketbankid(),bukrs);
			subject = this.customerRefundItemJdbcDao.getSkont(voucherItem,subject);
			//统驭项目
			voucherItem.setControlaccount(subject);
			//统驭科目说明
			voucherItem.setCaremark(this.customerRefundItemJdbcDao.getSubjectNameById(subject,bukrs));
		}//银行/商业承兑汇票
		else if(importPayment.getPaymenttype().equals("07")){
			//科目
			voucherItem.setSubject(importPayment.getSupplier());
			//科目说明
			String Subjectdescribe = this.customerRefundItemJdbcDao.getSupplierDescByCustomerId(importPayment.getSupplier(),bukrs);
			voucherItem.setSubjectdescribe(Subjectdescribe);
			String subject = this.customerRefundItemJdbcDao.getSubjectBySuppliers(importPayment.getSupplier(),bukrs);
			subject = this.customerRefundItemJdbcDao.getSkont(voucherItem,subject);
			//统驭项目
			voucherItem.setControlaccount(subject);
			//统驭科目说明
			voucherItem.setCaremark(this.customerRefundItemJdbcDao.getSubjectNameById(subject,bukrs));
		}
		//货币
		voucherItem.setCurrency("CNY");
		//取得票据的凭证
		List<VoucherItem>  voucheritemList2 = this.voucherItemJdbcDao.getVoucherItemList(importPayment.getPaymentid(),"02");
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
//		voucherItem.setZfbdt(importPayment.getDraftdate().replace("-", ""));
		voucherItem.setZfbdt(importPayment.getPaydate().replace("-", ""));
		//文本
		voucherItem.setText(importPayment.getText());
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
		Iterator<ImportPayBankItem> bankItemit = importPayment.getImportPayBankItem().iterator();		
		while(bankItemit.hasNext()){
			rowNo = rowNo + 1;
			ImportPayBankItem importPayBankItem = bankItemit.next();
			
			VoucherItem BankVoucherItem = new VoucherItem();
			BankVoucherItem.setVoucher(voucher);
			//客户记账码
			BankVoucherItem.setCheckcode("50");
			//特殊G/L标识
			BankVoucherItem.setGlflag("");
			//收付款清帐标识
			BankVoucherItem.setFlag("W");
			String subjectname = this.customerRefundItemJdbcDao.getSubjectNameById(importPayBankItem.getPaybanksubject(),bukrs);
			//科目
			BankVoucherItem.setSubject(importPayBankItem.getPaybanksubject());
			//科目说明
			BankVoucherItem.setSubjectdescribe(subjectname);
			//货币
			BankVoucherItem.setCurrency("CNY");
			//货币金额
			BankVoucherItem.setAmount(importPayBankItem.getPayamount());
			//本位币金额
			BankVoucherItem.setAmount2(importPayBankItem.getPayamount2());
			//部门
			BankVoucherItem.setDepid(depId);
			//业务范围
			BankVoucherItem.setGsber(depId);
			//票据到期日
//			voucherItem.setZfbdt(importPayment.getDraftdate().replace("-", ""));
			voucherItem.setZfbdt(importPayment.getPaydate().replace("-", ""));
			//文本
			BankVoucherItem.setText(importPayment.getText());
			//现金流项目
			BankVoucherItem.setCashflowitem(importPayBankItem.getCashflowitem());
			//反记账标识
			BankVoucherItem.setUncheckflag("");
			//统驭项目
			BankVoucherItem.setControlaccount(importPayBankItem.getPaybanksubject());
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
//		付款方式，不是押汇的才有结算科目，如果是押汇，要做在短期借款的凭证中
		//******************付款银行行项目凭证行项目*******************************//
		if(!"02".equals(importPayment.getPay_type())){
			//******************结算科目行项目凭证行项目*******************************//
			Set<VoucherItem> SettlesubjectVoucherItemList = new HashSet<VoucherItem>();
			
			SettlesubjectVoucherItemList = this.getSettSubjVoucherItem(importPayment);
			
			Iterator<VoucherItem> Settlesubjectit = SettlesubjectVoucherItemList.iterator();		
			while(Settlesubjectit.hasNext()){
				rowNo = rowNo + 1;
				VoucherItem setvoucherItem =Settlesubjectit.next();
				setvoucherItem.setRownumber(String.valueOf(rowNo));
				setvoucherItem.setFlag("W");
				setvoucherItem.setVoucher(voucher);
				
				voucherItemList.add(setvoucherItem);
			}
			//******************结算科目行项目凭证行项目*******************************//
		}
		voucher.setVoucherItem(voucherItemList);		
		this.voucherHibernateDao.save(voucher);
		retList.add(voucher.getVoucherid());
		
		
		//******************************应付票据清帐凭证*************
		//this.voucherJdbcDao.deleteVoucherByBusinessidAndClass(importPayment.getPaymentid(), "8");
		Voucher voucher2 = this.voucherService.getVoucher(importPayment.getPaymentid(), "02", "4");
		if(voucher2!=null && !StringUtils.isNullBlank(voucher2.getVoucherno())){
			voucher = voucher2;
		}
		Voucher clearVoucher = new Voucher();
		clearVoucher = getBillClearVoucher(importPayment,"8",voucher,"N");
		clearVoucher.setAgkon(importPayment.getTicketbankid());
		clearVoucher.setAgums("W");
		this.voucherHibernateDao.save(clearVoucher);
		retList.add(clearVoucher.getVoucherid());
		//******************************应付票据清帐凭证*************
		
		return retList;
	}
	
	/**
	 * 得到应付票据的清帐凭证
	 * @param homePayment
	 * @param strClass
	 * @param payVoucher
	 * @param strIsSHow
	 * @return
	 */
	public Voucher getBillClearVoucher(ImportPayment importPayment,
			String strClass,
			Voucher payVoucher,
			String strIsSHow){
		Voucher voucher = new Voucher();
		//凭证抬头
		voucher = this.getClearVoucher(importPayment,strClass);
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
			this.vendorTitleJdbcDao.getBillClearItemInfo(importPayment.getPaymentid(),"39","W","1");
		
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
	
	//国内信用证短期借款
	public List<String> shortTimepaySaveVoucher2(ImportPayment importPayment){
		List<String> retList = new ArrayList<String>();
		
		//this.voucherJdbcDao.deleteVoucherByBusinessidAndClass(importPayment.getPaymentid(), "2");
		String bukrs = getCompanyIDByDeptID(importPayment.getDept_id());
		//凭证抬头
		Voucher voucher = new Voucher();
		//凭证行项目
		Set<VoucherItem> voucherItemList = new HashSet<VoucherItem>();
		voucher = this.getVoucher(importPayment, "2");
		voucher.setCurrency(importPayment.getCurrency2());
		
		int rowNo = 0;
		//******************押汇银行行项目凭证行项目**********************************//
		ImportDocuBankItem importDocuBankItem = new ImportDocuBankItem();
		String dabank_hkonrname = "";
		Iterator<ImportDocuBankItem> docBankItemit = importPayment.getImportDocuBankItem().iterator();		
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
			BankVoucherItem.setCurrency(importPayment.getCurrency2());
			//货币金额
			BankVoucherItem.setAmount(importDocuBankItem.getDocuarypayamount());
			//本位币金额
			BankVoucherItem.setAmount2(importDocuBankItem.getDocuarypayamoun2());
			//部门
			BankVoucherItem.setDepid(importPayment.getRedocarybc());
			//文本
			BankVoucherItem.setText(importPayment.getText());
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
		Iterator<ImportDocuBankItem> bankItemit = importPayment.getImportDocuBankItem().iterator();		
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
			BankVoucherItem.setCurrency(importPayment.getCurrency2());
			//货币金额
			BankVoucherItem.setAmount(importDocuBankItem.getDocuarypayamount());
			//本位币金额
			BankVoucherItem.setAmount2(importDocuBankItem.getDocuarypayamoun2());
			//部门
			BankVoucherItem.setDepid(importPayment.getRedocarybc());
			//文本
			BankVoucherItem.setText(importPayment.getText());
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
		//******************结算科目行项目凭证行项目*******************************//
		//结算科目凭证行项目
//		Set<VoucherItem> SettlesubjectVoucherItemList = new HashSet<VoucherItem>();
//		
//		SettlesubjectVoucherItemList = this.getSettSubjVoucherItem(importPayment);
//		
//		Iterator<VoucherItem> Settlesubjectit = SettlesubjectVoucherItemList.iterator();		
//		while(Settlesubjectit.hasNext()){
//			rowNo = rowNo + 1;
//			VoucherItem setvoucherItem =Settlesubjectit.next();
//			setvoucherItem.setRownumber(String.valueOf(rowNo));
//			setvoucherItem.setVoucher(voucher);
//			
//			voucherItemList.add(setvoucherItem);
//		}
//		//******************结算科目行项目凭证行项目*******************************//
//		
//		//******************特殊总帐行项目凭证行项目*******************************//
//		//结算科目凭证行项目
//		Set<VoucherItem> FundFlowVoucherItemList = new HashSet<VoucherItem>();
//		
//		FundFlowVoucherItemList = this.getFundFlowVoucherItem(importPayment);
//		
//		Iterator<VoucherItem> FundFlowit = FundFlowVoucherItemList.iterator();		
//		while(FundFlowit.hasNext()){
//			rowNo = rowNo + 1;
//			VoucherItem funvoucherItem =FundFlowit.next();
//			funvoucherItem.setRownumber(String.valueOf(rowNo));
//			funvoucherItem.setVoucher(voucher);
//			
//			voucherItemList.add(funvoucherItem);
//		}
		//******************特殊总帐行项目凭证行项目*******************************//
		
		voucher.setVoucherItem(voucherItemList);
		this.voucherHibernateDao.save(voucher);
		retList.add(voucher.getVoucherid());
		
		return retList;
	}
	
}