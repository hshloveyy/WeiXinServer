/*
 * @(#)SupplierRefundmentService.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年06月18日 10点06分59秒
 *  描　述：创建
 */
package com.infolion.xdss3.supplierDrawback.service;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Service;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import com.infolion.platform.dpframework.util.StringUtils;
import com.infolion.platform.console.org.dao.SysDeptJdbcDao;
import com.infolion.platform.console.sys.context.UserContext;
import com.infolion.platform.console.sys.context.UserContextHolder;
import com.infolion.xdss3.BusinessState;
import com.infolion.xdss3.customerDrawback.dao.CustomerRefundItemJdbcDao;
import com.infolion.xdss3.financeSquare.domain.FundFlow;
import com.infolion.xdss3.financeSquare.domain.SettleSubject;
import com.infolion.xdss3.masterData.dao.VendorTitleJdbcDao;
import com.infolion.xdss3.masterData.domain.CustomerTitle;
import com.infolion.xdss3.masterData.domain.VendorTitle;
import com.infolion.xdss3.payment.dao.PayMentCBillJdbcDao;
import com.infolion.xdss3.payment.dao.PaymentItemJdbcDao;
import com.infolion.xdss3.payment.domain.ClearVoucherItemStruct;
import com.infolion.xdss3.payment.domain.ImportPayBankItem;
import com.infolion.xdss3.payment.domain.ImportPayment;
import com.infolion.xdss3.payment.domain.ImportPaymentCbill;
import com.infolion.xdss3.payment.domain.ImportPaymentItem;
import com.infolion.xdss3.payment.service.ImportPaymentService;
import com.infolion.xdss3.reassign.dao.ReassignJdbcDao;
import com.infolion.xdss3.singleClear.dao.SupplierSinglClearJdbcDao;
import com.infolion.xdss3.singleClear.domain.SupplierSinglClear;
import com.infolion.xdss3.singleClear.domain.UnClearCollect;
import com.infolion.xdss3.singleClear.domain.UnClearCustomBill;
import com.infolion.xdss3.singleClear.domain.UnClearPayment;
import com.infolion.xdss3.singleClear.domain.UnClearSupplieBill;
import com.infolion.xdss3.supplierDrawback.domain.SupplierDbBankItem;
import com.infolion.xdss3.supplierDrawback.domain.SupplierRefundment;
import com.infolion.xdss3.supplierDrawback.service.SupplierRefundmentService;
import com.infolion.xdss3.supplierDrawback.dao.SupplierRefundItemJdbcDao;
import com.infolion.xdss3.supplierDrawbackGen.service.SupplierRefundmentServiceGen;
import com.infolion.xdss3.supplierDrawback.domain.SupplierRefundItem;
import com.infolion.xdss3.voucher.dao.VoucherHibernateDao;
import com.infolion.xdss3.voucher.dao.VoucherJdbcDao;
import com.infolion.xdss3.voucher.domain.Voucher;
import com.infolion.xdss3.voucher.service.VoucherService;
import com.infolion.xdss3.voucheritem.dao.VoucherItemJdbcDao;
import com.infolion.xdss3.voucheritem.domain.VoucherItem;

/**
 * <pre>
 * 供应商退款(SupplierRefundment)服务类
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
public class SupplierRefundmentService extends SupplierRefundmentServiceGen
{
	private Log log = LogFactory.getFactory().getLog(this.getClass());
	
	@Autowired
	private SupplierRefundItemJdbcDao supplierRefundItemJdbcDao;
	
	/**
	 * @param supplierRefundItemJdbcDao the supplierRefundItemJdbcDao to set
	 */
	public void setSupplierRefundItemJdbcDao(
			SupplierRefundItemJdbcDao supplierRefundItemJdbcDao) {
		this.supplierRefundItemJdbcDao = supplierRefundItemJdbcDao;
	}
	
	@Autowired
	protected ImportPaymentService importPaymentService;

	public void setImportPaymentService(ImportPaymentService importPaymentService)
	{
		this.importPaymentService = importPaymentService;
	}
	
	@Autowired
	private SysDeptJdbcDao sysDeptJdbcDao;
	/**
	 * @param sysDeptJdbcDao the sysDeptJdbcDao to set
	 */
	public void setSysDeptJdbcDao(SysDeptJdbcDao sysDeptJdbcDao) {
		this.sysDeptJdbcDao = sysDeptJdbcDao;
	}
	
	@Autowired
	private VoucherHibernateDao voucherHibernateDao;
	
	/**
	 * @param voucherHibernateDao the voucherHibernateDao to set
	 */
	public void setVoucherHibernateDao(VoucherHibernateDao voucherHibernateDao) {
		this.voucherHibernateDao = voucherHibernateDao;
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
	private VoucherItemJdbcDao voucherItemJdbcDao;
	
	
	
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
	private CustomerRefundItemJdbcDao customerRefundItemJdbcDao;
	
	/**
	 * @param customerRefundItemJdbcDao the customerRefundItemJdbcDao to set
	 */
	public void setCustomerRefundItemJdbcDao(
			CustomerRefundItemJdbcDao customerRefundItemJdbcDao) {
		this.customerRefundItemJdbcDao = customerRefundItemJdbcDao;
	}

	@Autowired
	private PaymentItemJdbcDao paymentItemJdbcDao;
	
	public void setPaymentItemJdbcDao(PaymentItemJdbcDao paymentItemJdbcDao) {
		this.paymentItemJdbcDao = paymentItemJdbcDao;
	}

	@Autowired
	private PayMentCBillJdbcDao payMentCBillJdbcDao;
	
	public void setPayMentCBillJdbcDao(PayMentCBillJdbcDao payMentCBillJdbcDao) {
		this.payMentCBillJdbcDao = payMentCBillJdbcDao;
	}

	@Autowired
	private VendorTitleJdbcDao vendorTitleJdbcDao;

	public void setVendorTitleJdbcDao(VendorTitleJdbcDao vendorTitleJdbcDao)
	{
		this.vendorTitleJdbcDao = vendorTitleJdbcDao;
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
	protected VoucherService voucherService;
	
	public void setVoucherService(VoucherService voucherService) {
		this.voucherService = voucherService;
	}
	
	/**
	 * 根据部门ID获得公司ID
	 */
	public String getCompanyIDByDeptID(String deptID)
	{
		return this.sysDeptJdbcDao.getCompanyCode(deptID);
	}

	/**
	 * 根据收款分配表ID获取供应商退款行项目
	 * @param itemids
	 * @return
	 */
	public List<SupplierRefundItem> getSupplierRefundmentItemList( String itemids )
	{
		List<SupplierRefundItem> retList = new ArrayList<SupplierRefundItem>();
		SupplierRefundItem supplierRefundItem = new SupplierRefundItem();
		
		String[] itemidList = StringUtils.splitString(itemids, ",");
		
		for( int i = 0; i < itemidList.length; i++ )
		{
			supplierRefundItem = this.supplierRefundItemJdbcDao.getSupplierRefundItemList(itemidList[i]);
			if( supplierRefundItem != null )
			{
				retList.add(supplierRefundItem);
			}
		}
		return retList;
	}
	
	/**
	 * 根据银行ID获取供应商退款银行行项目
	 * @param itemids
	 * @return
	 */
	public List<SupplierDbBankItem> getSupplierDbBankItemList( String itemids )
	{
		List<SupplierDbBankItem> retList = new ArrayList<SupplierDbBankItem>();
		SupplierDbBankItem supplierDbBankItem = new SupplierDbBankItem();
		
		String[] itemidList = StringUtils.splitString(itemids, ",");
		
		for( int i = 0; i < itemidList.length; i++ )
		{
			supplierDbBankItem = this.supplierRefundItemJdbcDao.getSupplierDbBankItem(itemidList[i]);
			retList.add(supplierDbBankItem);
		}
		return retList;
	}
	
    /**
     * 调用存储过程UPDATE_VENDORTITLE
     */
    public void updateVendorTitle(final String supplier){
        this.supplierRefundmentHibernateDao.getHibernateTemplate().execute(new HibernateCallback() {
            
            public Object doInHibernate(Session session) throws HibernateException,
                    SQLException {
                CallableStatement cs = session.connection().prepareCall("{call UPDATE_VENDORTITLE(?)}");    
                cs.setString(1, supplier);
                boolean isSuccess = cs.execute();
                return isSuccess; 
            }
        });
    }
    
	/**
	 * 保存凭证信息
	 * @param refundmentId
	 * @return
	 */
	public List<String> saveVoucher(SupplierRefundment supplierRefundment)
	{
		List<String> retList = new ArrayList<String>();
		retList.clear();
		
		/**
		 * 判断原币和退款币别是否相同
		 */
        
        String preCurrency = "";
        String refundCurrency = supplierRefundment.getRefundcurrency(); // 取退款表
        
        boolean isSameCurrency = true;
        Iterator<SupplierRefundItem> it = supplierRefundment.getSupplierRefundItem().iterator();
        while(it.hasNext())
        {
            SupplierRefundItem supplierRefundItem = it.next();
            preCurrency = supplierRefundItem.getCurrency();
            if ( ! StringUtils.isNullBlank(preCurrency) ) {
                if (!preCurrency.equals(refundCurrency)) {
                    isSameCurrency = false;
                }
            }
        }
        
		String voucherId = "";
		
		//是否人民币清帐
		boolean isRmb = false;
		
		//添加如果是纯客户代理也应该有中转科目，判断币别相同为false
        if ( StringUtils.isNotBlank(supplierRefundment.getRepresentpaycust()) ) {
            isSameCurrency = false;
		}
		/**
		 * 原币和退款币别相同
		 */
		if(isSameCurrency)
		{
			String bukrs =  this.voucherService.getCompanyIDByDeptID(supplierRefundment.getDept_id());	
			if( refundCurrency.equals("CNY")   || ("2600".equals(bukrs) && refundCurrency.equals("HKD")))
			{
				isRmb = true;
			}
			voucherId = saveVoucherForSameCurrency(supplierRefundment,refundCurrency);
			retList.add(voucherId);
		}
		else
		{
			retList = saveVoucherForDiffCurrency(supplierRefundment,refundCurrency,preCurrency);
		}
		
		return retList;
	}
	
	/**
	 * 根据退款ID获得退款业务对象
	 * @param refundmentId
	 * @return
	 */
	public SupplierRefundment getSupplierRefundmentById(String refundmentId )
	{
//		return this.supplierRefundmentHibernateDao.get(refundmentId);
		return this._get(refundmentId);
	}
	
	public VoucherItem saveSupplierRefundmentVoucherItem(SupplierRefundItem supplierRefundItem,Voucher voucher,String bukrs,BigDecimal amount, BigDecimal amount2){
		VoucherItem voucherItem = new VoucherItem();
		voucherItem.setVoucher(voucher);
		voucherItem.setUncheckflag("X");
		voucherItem.setCheckcode("31");	//供应商记账码
        // 纯代理客户科目转换
        String comstomerid = supplierRefundItem.getSupplierRefundment().getRepresentpaycust();
        if ( !StringUtils.isNullBlank(comstomerid) ) {
            voucherItem.setCheckcode("15"); //客户记账码
        }
//	    voucherItem.setAmount(supplierRefundItem.getPefundmentamount().abs());// 金额取实际退款金额
//		voucherItem.setAmount2(supplierRefundItem.getRefundmentvalue().abs());	//退款金额（本位币）
		voucherItem.setAmount(amount);
		voucherItem.setAmount2(amount2);
		voucherItem.setCurrency(supplierRefundItem.getSupplierRefundment().getRefundcurrency());	//退款币别  // 取退款表
	//	if (  supplierRefundItem.getPefundmentamount().compareTo(new BigDecimal(0)) == 0  ) {
	//	    voucherItem.setDebitcredit("H");         //借贷标识符
	//    } else {
	//        voucherItem.setDebitcredit("S");            //借贷标识符
	//    }
	    voucherItem.setDebitcredit("H"); 
		voucherItem.setDepid(this.sysDeptJdbcDao.getDeptCodeById(supplierRefundItem.getSupplierRefundment().getDept_id()));	//设置部门
		voucherItem.setText(supplierRefundItem.getSupplierRefundment().getText());		//抬头文本
		voucherItem.setBusinessitemid(supplierRefundItem.getRefundmentitemid());
		
		String supplierid = supplierRefundItem.getSupplierRefundment().getSupplier(); //供应商						
		voucherItem.setSubjectdescribe(this.customerRefundItemJdbcDao.getSupplierDescByCustomerId(supplierid,bukrs));//科目说明： 供应商名称
		String subject = this.customerRefundItemJdbcDao.getSubjectBySuppliers(supplierid,bukrs);
		subject = this.customerRefundItemJdbcDao.getSkont(voucherItem,subject);
		//统驭项目
		voucherItem.setControlaccount(subject);
		//统驭科目说明
		voucherItem.setCaremark(this.customerRefundItemJdbcDao.getSubjectNameById(subject,bukrs));
		
		/**
		 * 供应商前面补零
		 */
		int iaddLen = 10 - supplierid.length();
		while( iaddLen != 0 )
		{
			supplierid = "0" + supplierid;
			iaddLen--;
		}
		voucherItem.setSubject(supplierid);	//科目： 供应商
		
		// 纯代理客户科目转换
		if ( !StringUtils.isNullBlank(comstomerid)  ) {
            
            String akont = this.customerRefundItemJdbcDao.getSubjectByCustomer(comstomerid,bukrs);
            voucherItem.setControlaccount(akont); //统驭科目
            voucherItem.setCaremark(this.customerRefundItemJdbcDao.getSubjectNameById(akont,bukrs)); // 统驭科目说明 
            voucherItem.setSubjectdescribe(this.customerRefundItemJdbcDao.getCustomerDescByCustomerId(comstomerid,bukrs));//科目说明： 客户名称
            
            comstomerid = StringUtils.leftPad(comstomerid, 10, "0");
            voucherItem.setSubject(comstomerid);    //科目： 客户
		}
	
		return  voucherItem;
	}
	
	/**
	 * 保存凭证信息(币别相同)
	 * @param refundmentId 退款ID
	 * @param currency 退款币别
	 * @ return : 凭证编号
	 */
	private String saveVoucherForSameCurrency(SupplierRefundment supplierRefundment, String currency )
	{
			
		/**
		 * 保存前先删除
		 */
		//this.voucherJdbcDao.deleteVoucherByBusinessid(supplierRefundment.getRefundmentid());	

		Voucher voucher = new Voucher();
		Set<VoucherItem> voucherItemList = new HashSet<VoucherItem>();
		
		voucher.setBusinessid(supplierRefundment.getRefundmentid());
		voucher.setBusinesstype("06");			//06： 供应商退款
		voucher.setCheckdate(supplierRefundment.getAccountdate().replace("-", "")); // 过账日期
		
		String bukrs = getCompanyIDByDeptID(supplierRefundment.getDept_id());
		voucher.setCompanycode(bukrs); //公司代码
		/**
		 * 退款币别
		 */
		voucher.setCurrency(currency);	//退款币别
		voucher.setExchangerate(supplierRefundment.getExchangerate());	// 退款汇率
		voucher.setFiperiod(supplierRefundment.getAccountdate().substring(5, 7));//会计期间
		voucher.setFiyear(supplierRefundment.getAccountdate().substring(0, 4));	//会计年度
		
		SimpleDateFormat  dateFormat=new SimpleDateFormat ("yyyyMMdd");
		voucher.setImportdate(dateFormat.format(new Date()));	//输入日期
		
		UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
		voucher.setImporter(userContext.getSysUser().getUserName());	//输入者
		voucher.setPreparer(userContext.getSysUser().getUserName());	//预制者
		voucher.setVoucherdate(supplierRefundment.getVoucherdate().replace("-", "")); //凭证日期
		voucher.setVouchertext(supplierRefundment.getText());   //凭证抬头文本
		
		voucher.setVouchertype("KR");
		voucher.setVoucherclass("1");
		/**
		 * 遍历供应商退款行项目
		 */		
		int iRowNo = 1; 	//行项目号
		Iterator<SupplierRefundItem> refundItemit = supplierRefundment.getSupplierRefundItem().iterator();		
		while( refundItemit.hasNext() )
		{
			SupplierRefundItem supplierRefundItem = refundItemit.next();
			//ImportPaymentItem paymentItem = this.paymentItemJdbcDao.getPaymentItem(supplierRefundItem.getPaymentitemid());
//			if(supplierRefundItem.getRefundmentamount().compareTo( paymentItem.getAssignamount()) != 1){
//				VoucherItem voucherItem = saveSupplierRefundmentVoucherItem(supplierRefundItem,voucher,bukrs,supplierRefundItem.getPefundmentamount().abs());
//				voucherItem.setRownumber(Integer.toString(iRowNo));//行项目号
//	    		voucherItemList.add(voucherItem);	
//				iRowNo++;		
//			}else{
//				VoucherItem voucherItem = saveSupplierRefundmentVoucherItem(supplierRefundItem,voucher,bukrs,paymentItem.getAssignamount().abs());
//				voucherItem.setRownumber(Integer.toString(iRowNo));//行项目号
//	    		voucherItemList.add(voucherItem);	
//				iRowNo++;	
//				voucherItem = saveSupplierRefundmentVoucherItem(supplierRefundItem,voucher,bukrs,(supplierRefundItem.getPefundmentamount().abs().subtract(paymentItem.getAssignamount().abs())));
//				voucherItem.setRownumber(Integer.toString(iRowNo));//行项目号
//	    		voucherItemList.add(voucherItem);	
//				iRowNo++;	
//			}
			VoucherItem voucherItem = saveSupplierRefundmentVoucherItem(supplierRefundItem,voucher,bukrs,supplierRefundItem.getPefundmentamount().abs(), supplierRefundItem.getRefundmentvalue().abs());
			voucherItem.setRownumber(Integer.toString(iRowNo));//行项目号
    		voucherItemList.add(voucherItem);	
			iRowNo++;			
		}
		
		/**
		 * 遍历退款银行
		 */
		Iterator<SupplierDbBankItem> bankItemit = supplierRefundment.getSupplierDbBankItem().iterator();		
		while( bankItemit.hasNext() )
		{
			SupplierDbBankItem supplierDbBankItem = bankItemit.next();
			
			VoucherItem voucherItem = new VoucherItem();
			
			voucherItem.setVoucher(voucher);
			
			voucherItem.setCheckcode("40");	//银行记账码
			voucherItem.setAmount(supplierDbBankItem.getRefundamount());//退款金额
			voucherItem.setAmount2(supplierDbBankItem.getRefundamount2());	//退款金额（本位币）
			voucherItem.setCurrency(currency);	//退款币别
			voucherItem.setDebitcredit("S");			//借贷标识符
			voucherItem.setDepid(this.sysDeptJdbcDao.getDeptCodeById(supplierRefundment.getDept_id()));	//设置部门
			voucherItem.setText(supplierRefundment.getText());		//抬头文本
								
			voucherItem.setSubjectdescribe(supplierDbBankItem.getRefundbankname());//科目说明： 银行名称
			
			voucherItem.setSubject(supplierDbBankItem.getBanksubject());	//科目： 银行科目
			voucherItem.setRownumber(Integer.toString(iRowNo));//行项目号
			voucherItem.setCashflowitem(supplierRefundment.getCashflowitem());//现金流量项
			
			//统驭项目
			voucherItem.setControlaccount(supplierDbBankItem.getBanksubject());
			//统驭科目说明
			voucherItem.setCaremark(supplierDbBankItem.getRefundbankname());
			
			voucherItemList.add(voucherItem);
			iRowNo++;			
			
		}
        // ******************结算科目行项目凭证行项目*******************************//
        Set<VoucherItem> SettlesubjectVoucherItemList = new HashSet<VoucherItem>();

        SettlesubjectVoucherItemList = this.getSettSubjVoucherItem(supplierRefundment);

        Iterator<VoucherItem> Settlesubjectit = SettlesubjectVoucherItemList.iterator();
        while (Settlesubjectit.hasNext()) {
            VoucherItem setvoucherItem = Settlesubjectit.next();
            setvoucherItem.setRownumber(String.valueOf(iRowNo));
            setvoucherItem.setVoucher(voucher);
            iRowNo++;
            voucherItemList.add(setvoucherItem);
        }
        // ******************结算科目行项目凭证行项目*******************************//
        
        //******************特殊总帐行项目凭证行项目*******************************//
        Set<VoucherItem> FundFlowVoucherItemList = new HashSet<VoucherItem>();
        
        FundFlowVoucherItemList = this.getFundFlowVoucherItem(supplierRefundment);
        
        Iterator<VoucherItem> FundFlowit = FundFlowVoucherItemList.iterator();      
        while(FundFlowit.hasNext()){
            VoucherItem funvoucherItem =FundFlowit.next();
            funvoucherItem.setRownumber(String.valueOf(iRowNo));
            funvoucherItem.setVoucher(voucher);
            iRowNo++;
            voucherItemList.add(funvoucherItem);
        }
        //******************特殊总帐行项目凭证行项目*******************************//
        
		voucher.setVoucherItem(voucherItemList);		
		this.voucherHibernateDao.save(voucher);
		return voucher.getVoucherid();
	}
	
	/**
	 * 保存凭证信息(币别不同)
	 * @param refundmentId 退款ID
	 * @param currency 退款币别
	 * @ return : 凭证编号
	 */
	private List<String> saveVoucherForDiffCurrency(SupplierRefundment supplierRefundment, String currency ,String preCurrency)
	{
		List<String> retList = new ArrayList<String>();
		
		/**
		 * 凭证抬头
		 */
		Voucher voucher = new Voucher();
		Set<VoucherItem> voucherItemList = new HashSet<VoucherItem>();
		
		voucher.setBusinessid(supplierRefundment.getRefundmentid());
		voucher.setBusinesstype("06");			//6： 供应商退款
		voucher.setCheckdate(supplierRefundment.getAccountdate().replace("-", "")); // 过账日期
		
		String bukrs = getCompanyIDByDeptID(supplierRefundment.getDept_id());
		voucher.setCompanycode(bukrs); //公司代码

		voucher.setCurrency(preCurrency);	//退款币别
		voucher.setExchangerate(supplierRefundment.getExchangerate());	// 退款汇率
		voucher.setFiperiod(supplierRefundment.getAccountdate().substring(5, 7));//会计期间
		voucher.setFiyear(supplierRefundment.getAccountdate().substring(0, 4));	//会计年度
		
		SimpleDateFormat  dateFormat=new SimpleDateFormat ("yyyyMMdd");
		voucher.setImportdate(dateFormat.format(new Date()));	//输入日期
		
		UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
		voucher.setImporter(userContext.getSysUser().getUserName());	//输入者
		voucher.setPreparer(userContext.getSysUser().getUserName());	//预制者
		voucher.setVoucherdate(supplierRefundment.getVoucherdate().replace("-", "")); //凭证日期
		voucher.setVouchertext(supplierRefundment.getText());   //凭证抬头文本
		voucher.setVouchertype("KR");
		voucher.setVoucherclass("1");
		String comstomerid = supplierRefundment.getRepresentpaycust();
		
		/**
		 * 遍历供应商退款行项目
		 */			
		BigDecimal totalPreAmount1 = new BigDecimal("0.0");	//中转科目原币退款金额
		BigDecimal totalPreAmount2 = new BigDecimal("0.0");	//中转科目本位币金额
		
		int iRowNo = 1; 	//行项目号
		Iterator<SupplierRefundItem> refundItemit = supplierRefundment.getSupplierRefundItem().iterator();		
		while( refundItemit.hasNext() )
		{
			SupplierRefundItem supplierRefundItem = refundItemit.next();
			VoucherItem voucherItem = new VoucherItem();
			voucherItem.setVoucher(voucher);
			
			voucherItem.setUncheckflag("X");
			voucherItem.setCheckcode("31");	//供应商记账码
			totalPreAmount1 = totalPreAmount1.add(supplierRefundItem.getPefundmentamount());
            voucherItem.setAmount(supplierRefundItem.getPefundmentamount().abs());// 金额取实际退款金额
            totalPreAmount2 = totalPreAmount2.add(supplierRefundItem.getRefundmentvalue());
			voucherItem.setAmount2(supplierRefundItem.getRefundmentvalue().abs());	//退款金额（本位币）
			voucherItem.setCurrency(supplierRefundItem.getCurrency());	//原币币别
            if (  supplierRefundItem.getPefundmentamount().compareTo(new BigDecimal(0)) > -1  ) {
                voucherItem.setDebitcredit("H");         //借贷标识符
            } else {
                voucherItem.setDebitcredit("S");            //借贷标识符
            }
			voucherItem.setDepid(this.sysDeptJdbcDao.getDeptCodeById(supplierRefundment.getDept_id()));	//设置部门
			voucherItem.setText(supplierRefundment.getText());		//抬头文本
			voucherItem.setBusinessitemid(supplierRefundItem.getRefundmentitemid()); //  业务行项目ID
			
			String supplierid = supplierRefundment.getSupplier(); //供应商						
			voucherItem.setSubjectdescribe(this.customerRefundItemJdbcDao.getSupplierDescByCustomerId(supplierid,bukrs));//科目说明： 供应商名称
			
			String subject = this.customerRefundItemJdbcDao.getSubjectBySuppliers(supplierid,bukrs);
			subject = this.customerRefundItemJdbcDao.getSkont(voucherItem,subject);
			//统驭项目
			voucherItem.setControlaccount(subject);
			//统驭科目说明
			voucherItem.setCaremark(this.customerRefundItemJdbcDao.getSubjectNameById(subject,bukrs));
			/**
			 * 供应商前面补零
			 */
			int iaddLen = 10 - supplierid.length();
			while( iaddLen != 0 )
			{
				supplierid = "0" + supplierid;
				iaddLen--;
			}
			voucherItem.setSubject(supplierid);	//科目： 供应商
			voucherItem.setRownumber(Integer.toString(iRowNo));//行项目号
			
		     
	        // 纯代理客户科目转换
	        if ( !StringUtils.isNullBlank(comstomerid) ) {

                voucherItem.setCheckcode("15"); //客户记账码
	            String akont = this.customerRefundItemJdbcDao.getSubjectByCustomer(comstomerid,bukrs);
	            voucherItem.setControlaccount(akont); //统驭科目
	            voucherItem.setCaremark(this.customerRefundItemJdbcDao.getSubjectNameById(akont,bukrs)); // 统驭科目说明 
	            voucherItem.setSubjectdescribe(this.customerRefundItemJdbcDao.getCustomerDescByCustomerId(comstomerid,bukrs));//科目说明： 客户名称
	            
	            comstomerid = StringUtils.leftPad(comstomerid, 10, "0");
	            voucherItem.setSubject(comstomerid);    //科目： 客户
	            voucherItem.setCurrency("CNY");
	            voucherItem.setAmount(supplierRefundItem.getRefundmentvalue().abs());// 金额取实际退款金额
	            voucherItem.setAmount2(supplierRefundItem.getRefundmentvalue().abs());  //退款金额（本位币）
	        }
			
			voucherItemList.add(voucherItem);
			iRowNo++;			
		}
		
		/**
		 * 供应商行项目对应中转科目
		 */
		VoucherItem voucherItem1 = new VoucherItem();
		voucherItem1.setVoucher(voucher);
		voucherItem1.setRownumber(Integer.toString(iRowNo));//行项目号
		voucherItem1.setCheckcode("40");	//记账码

		voucherItem1.setSubject("2202999999");	//科目
		voucherItem1.setSubjectdescribe(this.customerRefundItemJdbcDao.getSubjectNameById("2202999999",bukrs));//科目名称
		voucherItem1.setCurrency(preCurrency);				//原币币别
		voucherItem1.setAmount(totalPreAmount1);	//退款原币金额，如果是纯代理客户也是取退款行项原币金额
		voucherItem1.setAmount2(totalPreAmount2);			//本位币金额
		voucherItem1.setDepid(this.sysDeptJdbcDao.getDeptCodeById(supplierRefundment.getDept_id()));
		voucherItem1.setText(supplierRefundment.getText());		//抬头文本
		voucherItem1.setDebitcredit("S");			//借贷标识符
		
		//统驭项目
		voucherItem1.setControlaccount("2202999999");
		//统驭科目说明
		voucherItem1.setCaremark(this.customerRefundItemJdbcDao.getSubjectNameById("2202999999",bukrs));
		
		
        // 纯代理客户科目转换
        if ( !StringUtils.isNullBlank(comstomerid) ) {
            // 纯代理客户科目转换，取抬头“退款金额”*“退款汇率”＝“本位币金额”
            voucherItem1.setAmount2(supplierRefundment.getRefundamount().multiply(supplierRefundment.getExchangerate()).setScale(2, BigDecimal.ROUND_HALF_UP));          //本位币金额
            voucherItem1.setCurrency("CNY");
            voucherItem1.setSubject("1122999999");  //科目
            voucherItem1.setSubjectdescribe(this.customerRefundItemJdbcDao.getSubjectNameById("1122999999",bukrs));//科目名称
            //统驭项目
            voucherItem1.setControlaccount("1122999999");
            //统驭科目说明
            voucherItem1.setCaremark(this.customerRefundItemJdbcDao.getSubjectNameById("1122999999",bukrs)); 
            voucherItem1.setAmount(supplierRefundment.getRefundamount().multiply(supplierRefundment.getExchangerate()).setScale(2, BigDecimal.ROUND_HALF_UP));    //人民币取
        }
        
		voucherItemList.add(voucherItem1);
		iRowNo++;
		
		
		// ******************结算科目行项目凭证行项目*******************************//
        Set<VoucherItem> SettlesubjectVoucherItemList = new HashSet<VoucherItem>();

        SettlesubjectVoucherItemList = this.getSettSubjVoucherItem(supplierRefundment);

        Iterator<VoucherItem> Settlesubjectit = SettlesubjectVoucherItemList.iterator();
        while (Settlesubjectit.hasNext()) {
            VoucherItem setvoucherItem = Settlesubjectit.next();
            if ( !StringUtils.isNullBlank(comstomerid) ) {
                setvoucherItem.setCurrency("CNY");
            }
            setvoucherItem.setRownumber(String.valueOf(iRowNo));
            setvoucherItem.setVoucher(voucher);
            iRowNo++;
            voucherItemList.add(setvoucherItem);
        }
        // ******************结算科目行项目凭证行项目*******************************//
        
        //******************特殊总帐行项目凭证行项目*******************************//
        Set<VoucherItem> FundFlowVoucherItemList = new HashSet<VoucherItem>();
        
        FundFlowVoucherItemList = this.getFundFlowVoucherItem(supplierRefundment);
        
        Iterator<VoucherItem> FundFlowit = FundFlowVoucherItemList.iterator();      
        while(FundFlowit.hasNext()){
            VoucherItem funvoucherItem =FundFlowit.next();
            if ( !StringUtils.isNullBlank(comstomerid) ) {
                funvoucherItem.setCurrency("CNY");
            }
            funvoucherItem.setRownumber(String.valueOf(iRowNo));
            funvoucherItem.setVoucher(voucher);
            iRowNo++;
            voucherItemList.add(funvoucherItem);
        }
        //******************特殊总帐行项目凭证行项目*******************************//		
        // 纯代理客户科目转换
        if ( !StringUtils.isNullBlank(comstomerid) ) {
            voucher.setCurrency("CNY");
        }
		voucher.setVoucherItem(voucherItemList);		
		this.voucherHibernateDao.save(voucher);
		
		retList.add(voucher.getVoucherid());
		
		/**
		 * 凭证抬头
		 */
		Voucher voucher2 = new Voucher();
		Set<VoucherItem> voucherItemList2 = new HashSet<VoucherItem>();
		
		voucher2.setBusinessid(supplierRefundment.getRefundmentid());
		voucher2.setBusinesstype("06");			//6： 供应商退款
		voucher2.setCheckdate(supplierRefundment.getAccountdate().replace("-", "")); // 过账日期
		
		voucher2.setCompanycode(bukrs); //公司代码

		voucher2.setCurrency(currency);	//退款币别
		voucher2.setExchangerate(supplierRefundment.getExchangerate());	// 退款汇率
		voucher2.setFiperiod(supplierRefundment.getAccountdate().substring(5, 7));//会计期间
		voucher2.setFiyear(supplierRefundment.getAccountdate().substring(0, 4));	//会计年度
		voucher2.setImportdate(dateFormat.format(new Date()));	//输入日期
		
		voucher2.setImporter(userContext.getSysUser().getUserName());	//输入者
		voucher2.setPreparer(userContext.getSysUser().getUserName());	//预制者
		voucher2.setVoucherdate(supplierRefundment.getVoucherdate().replace("-", "")); //凭证日期
		voucher2.setVouchertext(supplierRefundment.getText());   //凭证抬头文本
		voucher2.setVouchertype("KR");
		
		
		iRowNo = 1;
		
		/**
		 * 遍历退款银行
		 */
		BigDecimal totalAmount1 = new BigDecimal("0.0");	//中转科目原币退款金额
		BigDecimal totalAmount2 = new BigDecimal("0.0");	//中转科目本位币金额
		
		Iterator<SupplierDbBankItem> bankItemit = supplierRefundment.getSupplierDbBankItem().iterator();		
		while( bankItemit.hasNext() )
		{
			SupplierDbBankItem supplierDbBankItem = bankItemit.next();
			
			VoucherItem voucherItem = new VoucherItem();
			
			voucherItem.setVoucher(voucher2);
			
			voucherItem.setCheckcode("40");	//记账码
			totalAmount1 = totalAmount1.add(supplierDbBankItem.getRefundamount());			
			voucherItem.setAmount(supplierDbBankItem.getRefundamount());//退款金额
			totalAmount2 = totalAmount2.add(supplierDbBankItem.getRefundamount2());
			
			voucherItem.setAmount2(supplierDbBankItem.getRefundamount2());	//退款金额（本位币）
			voucherItem.setCurrency(currency);	//退款币别
			voucherItem.setDebitcredit("S");			//借贷标识符
			voucherItem.setDepid(this.sysDeptJdbcDao.getDeptCodeById(supplierRefundment.getDept_id()));	//设置部门
			voucherItem.setText(supplierRefundment.getText());		//抬头文本
									
			voucherItem.setSubjectdescribe(supplierDbBankItem.getRefundbankname());//科目说明： 银行名称
	
			voucherItem.setSubject(supplierDbBankItem.getBanksubject());	//科目： 
			voucherItem.setRownumber(Integer.toString(iRowNo));//行项目号
			voucherItem.setCashflowitem(supplierRefundment.getCashflowitem());//现金流量项
			
			//统驭项目
			voucherItem.setControlaccount(supplierDbBankItem.getBanksubject());
			//统驭科目说明
			voucherItem.setCaremark(supplierDbBankItem.getRefundbankname());
			
			voucherItemList2.add(voucherItem);
			iRowNo++;			
			
		}
		
		/**
		 * 银行对应中转科目行项
		 */
		VoucherItem voucherItem2 = new VoucherItem();
		voucherItem2.setVoucher(voucher2);
		voucherItem2.setRownumber(Integer.toString(iRowNo));//行项目号
		voucherItem2.setCheckcode("50");		//记账码
		voucherItem2.setSubject("2202999999");	//科目
		voucherItem2.setSubjectdescribe(this.customerRefundItemJdbcDao.getSubjectNameById("2202999999",bukrs));//科目名称
		voucherItem2.setCurrency(currency);				//退款币别
		voucherItem2.setAmount(totalAmount1);	//退款金额
		voucherItem2.setAmount2(totalAmount2);			//本位币金额
		voucherItem2.setDepid(this.sysDeptJdbcDao.getDeptCodeById(supplierRefundment.getDept_id()));	//部门
		voucherItem2.setText(supplierRefundment.getText());		//抬头文本
		voucherItem2.setDebitcredit("H");			//借贷标识符
		voucherItem2.setControlaccount("2202999999");	//科目
		voucherItem2.setCaremark(this.customerRefundItemJdbcDao.getSubjectNameById("2202999999",bukrs));//科目名称
//		voucherItem2.setUncheckflag("X"); // 周
		
        // 纯代理客户科目转换
        if ( !StringUtils.isNullBlank(comstomerid) ) {
            voucherItem2.setSubject("1122999999");  //科目
            voucherItem2.setSubjectdescribe(this.customerRefundItemJdbcDao.getSubjectNameById("1122999999",bukrs));//科目名称
            //统驭项目
            voucherItem2.setControlaccount("1122999999");
            //统驭科目说明
            voucherItem2.setCaremark(this.customerRefundItemJdbcDao.getSubjectNameById("1122999999",bukrs)); 
        }
        
		voucherItemList2.add(voucherItem2);
		iRowNo++;
				
		voucher2.setVoucherItem(voucherItemList2);		
		this.voucherHibernateDao.save(voucher2);
		retList.add(voucher2.getVoucherid());
		return retList;
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
	 * 得到有汇损益的行项目信息
	 * @param importPayment
	 * @param subtractVlaue
	 * @param strType
	 * @return
	 */
	public VoucherItem getProfitAndLossVoucherItem(SupplierRefundment supplierRefundment,
			BigDecimal subtractVlaue,
			String strType){
		String bukrs = getCompanyIDByDeptID(supplierRefundment.getDept_id());
		VoucherItem voucherItem = new VoucherItem();
		
		if (strType.equals("1")){
			//科目
			voucherItem.setSubject(supplierRefundment.getSupplier());
			//科目说明
			
			String Subjectdescribe = this.customerRefundItemJdbcDao.getSupplierDescByCustomerId(supplierRefundment.getSupplier(),bukrs);
			voucherItem.setSubjectdescribe(Subjectdescribe);
			
			String subject = this.customerRefundItemJdbcDao.getSubjectBySuppliers(supplierRefundment.getSupplier(),bukrs);
			subject = this.customerRefundItemJdbcDao.getSkont(voucherItem,subject);
			//统驭项目
			voucherItem.setControlaccount(subject);
			//统驭科目说明
			voucherItem.setCaremark(this.customerRefundItemJdbcDao.getSubjectNameById(subject,bukrs));
			
		     
	        // 纯代理客户科目转换
	        String comstomerid = supplierRefundment.getRepresentpaycust();
	        if ( !StringUtils.isNullBlank(comstomerid) ) {
	            
	            String akont = this.customerRefundItemJdbcDao.getSubjectByCustomer(comstomerid,bukrs);
	            voucherItem.setControlaccount(akont); //统驭科目
	            voucherItem.setCaremark(this.customerRefundItemJdbcDao.getSubjectNameById(akont,bukrs)); // 统驭科目说明 
	            voucherItem.setSubjectdescribe(this.customerRefundItemJdbcDao.getCustomerDescByCustomerId(comstomerid,bukrs));//科目说明： 客户名称
	            
	            comstomerid = StringUtils.leftPad(comstomerid, 10, "0");
	            voucherItem.setSubject(comstomerid);    //科目： 客户
	        }
	        
			if (subtractVlaue.signum() == 1){
				//记帐码
				voucherItem.setCheckcode("25");
                // 纯代理客户科目转换
                if ( !StringUtils.isNullBlank(comstomerid) ) {
                    voucherItem.setCheckcode("15");
                }
				voucherItem.setDebitcredit("S");
			}else{
				//记帐码
				voucherItem.setCheckcode("38");
		        // 纯代理客户科目转换
		        if ( !StringUtils.isNullBlank(comstomerid) ) {
		            voucherItem.setCheckcode("08");
		        }
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
		voucherItem.setCurrency("");
		//货币金额
		voucherItem.setAmount(new BigDecimal("0"));
		//本位币金额
		voucherItem.setAmount2(subtractVlaue.abs());
		//部门
		voucherItem.setDepid(this.sysDeptJdbcDao.getDeptCodeById(supplierRefundment.getDept_id()));
		//文本
		voucherItem.setText(supplierRefundment.getText());
		
		return voucherItem;
	}
	
	public Voucher getClearVoucher(SupplierRefundment supplierRefundment,String strClass){
		Voucher voucher = new Voucher();
		
		UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
		
		//业务编号
		voucher.setBusinessid(supplierRefundment.getRefundmentid());
		//业务类型
		voucher.setBusinesstype("06");
		//凭证类型
		voucher.setVouchertype("KZ");
		//凭证抬头文本
		voucher.setVouchertext("清帐凭证");
		//过账日期
		voucher.setCheckdate(supplierRefundment.getAccountdate().replace("-", ""));
		//公司代码
		String bukrs = getCompanyIDByDeptID(supplierRefundment.getDept_id());
		voucher.setCompanycode(bukrs); //公司代码
		//货币
		voucher.setCurrency("");
		//凭证日期
		voucher.setVoucherdate(supplierRefundment.getVoucherdate().replace("-", ""));
		//会计年度
		voucher.setFiyear(supplierRefundment.getAccountdate().substring(0, 4));
		//会计期间
		voucher.setFiperiod(supplierRefundment.getAccountdate().substring(5, 7));
		//输入日期
		SimpleDateFormat  dateFormat=new SimpleDateFormat ("yyyyMMdd");
		voucher.setImportdate(dateFormat.format(new Date()));
		//输入者
		voucher.setImporter(userContext.getSysUser().getUserName());
		//预制者
		voucher.setPreparer(userContext.getSysUser().getUserName());
		//银行科目号
		Iterator<SupplierDbBankItem> bankItemit = supplierRefundment.getSupplierDbBankItem().iterator();
		SupplierDbBankItem supplierDbBankItem = bankItemit.next();
		voucher.setKonto(supplierDbBankItem.getBanksubject());
		//计息日
		voucher.setValut(dateFormat.format(new Date()));
		//供应商编号
		voucher.setAgkon(supplierRefundment.getSupplier());
        
        // 纯代理客户科目转换
        String comstomerid = supplierRefundment.getRepresentpaycust();
        if ( !StringUtils.isNullBlank(comstomerid) ) {
            voucher.setAgkon(comstomerid);
        }
        
		//科目类型
		voucher.setAgkoa("K");
		//业务范围
		voucher.setGsber(this.sysDeptJdbcDao.getDeptCodeById(supplierRefundment.getDept_id()));
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
	 * 判断单据下合同是否可全清帐
	 * 
	 * @param supplierSinglClear
	 * @return
	 */
	public List<String> judgeContractIsAllClear(SupplierRefundment supplierRefundment)
	{
		List<String> clearedContractNo = new ArrayList<String>();
		Set supplierRefundItems = supplierRefundment.getSupplierRefundItem();
		Iterator<SupplierRefundItem> it = supplierRefundItems.iterator();
		if(it.hasNext())
		{
			SupplierRefundItem supplierRefundItem = it.next();

			if(!StringUtils.isNullBlank(supplierRefundItem.getContract_no())){
				if(this.supplierRefundItemJdbcDao.checkRefundContractClear(supplierRefundItem)){
					clearedContractNo.add(supplierRefundItem.getContract_no());
				}
			}
		}

		return clearedContractNo;
	}
	
	/**
	 * 保存清帐凭证
	 * @param customerRefundment
	 * @param currency 退款币别
	 * @return
	 */
	public List<String> saveClearVoucher( SupplierRefundment supplierRefundment)
	{
		/**
		 * 判断原币和退款币别是否相同
		 */
        
        String preCurrency = "";
        String refundCurrency = supplierRefundment.getRefundcurrency(); // 取退款表
        
        boolean isSameCurrency = true;
        Iterator<SupplierRefundItem> it = supplierRefundment.getSupplierRefundItem().iterator();
        while(it.hasNext())
        {
            SupplierRefundItem supplierRefundItem = it.next();
            preCurrency = supplierRefundItem.getCurrency();
            if (StringUtils.isNotBlank(preCurrency)) {
                if (!preCurrency.equals(refundCurrency)) {
                    isSameCurrency = false;
                }
            }
        }
        
		//是否人民币清帐
		boolean isRmb = false;
		
		/**
		 * 原币和退款币别相同
		 */
		if(isSameCurrency) //preCurrency行项目为空时有可能是合同、立项默认相同
		{
			if( refundCurrency.equals("CNY") )
			{
				isRmb = true;
			}
		}
		
		Set<SupplierRefundItem> itemSet =  supplierRefundment.getSupplierRefundItem();
		
		/**
		 * 本次退款合计金额
		 */
		BigDecimal totalRefundmentAmount = new BigDecimal("0.00");
		List<String> lstVouchNo = new ArrayList<String>();
		
		List<VoucherItem> lstVoucherItem =  new ArrayList<VoucherItem>();		//清帐凭证行项目
		List<VoucherItem> lstNewVoucherItem =  new ArrayList<VoucherItem>();	//外币损益行项目
		
		BigDecimal sumStdAmount = new BigDecimal("0.00");				//款的本位币金额合计
		BigDecimal sumStdBillAmount = new BigDecimal("0.00");			//票的本位币金额合计
		
		//本次的可退金额合计
		BigDecimal allPefundmentAmount2 = new BigDecimal("0.00");
		
		boolean needToCreate = false;	// 判断是否需要产生清帐凭证
		
        if (itemSet != null) {
			/**
			 * 退款行项目清帐
			 */
			for( SupplierRefundItem supplierRefundItem : itemSet )
			{	
				totalRefundmentAmount = totalRefundmentAmount.add(supplierRefundItem.getRefundmentamount());
				allPefundmentAmount2 = allPefundmentAmount2.add(supplierRefundItem.getUnassignamount()); //本次的可退金额合计
				BigDecimal assignAmount 
					= this.supplierRefundItemJdbcDao.getAmountByItemId(supplierRefundItem.getPaymentitemid());
				// 合同、立项跳过
				if (StringUtils.isNullBlank(supplierRefundItem.getPaymentitemid())){
				    continue;
				}
				
				ImportPaymentItem paymentItem = this.paymentItemJdbcDao.getPaymentItem(supplierRefundItem.getPaymentitemid());
				
				ImportPayment importPayment = this.importPaymentService._get(paymentItem.getImportPayment().getPaymentid());
				
				/**
				* 退预收款
				*/
				String strCurrentFlag = "N";
				//this.payMentCBillJdbcDao.checkPayCurrentClear(paymentItem.getImportPayment().getPaymentid(), supplierRefundItem.getRefundmentamount());

				if(strCurrentFlag.equals("Y")){
					Voucher voucher = getClearVoucher(supplierRefundment,"9");
					//凭证行项目
					Set<VoucherItem> voucherItemList = new HashSet<VoucherItem>();
					
					//得到票的本位币之和
					BigDecimal billValue = new BigDecimal("0");
					//得到分配项的本位币之和
					BigDecimal itemValue = new BigDecimal("0");
					//分配项之和和票之和的差
					BigDecimal subtractVlaue = new BigDecimal("0");
					
					List<VoucherItem> payVoucherItem = this.voucherItemJdbcDao.getVoucherItemList(importPayment.getPaymentid());
					
					//得到票的行项目信息
					Iterator<ImportPaymentCbill> importPaymentCbillIterator = importPayment.getImportPaymentCbill().iterator();
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
								
								voucherItem.setVoucherno(payItem.getVoucher().getVoucherno());
								voucherItem.setBusinessitemid(payItem.getBusinessitemid());
								voucherItem.setVoucher(voucher);
								voucherItemList.add(voucherItem);
								
								itemValue = itemValue.add(payItem.getAmount2());
							}
						}
					}
					
					//本次的退款行项
					List refundVoucherItemList = this.voucherItemJdbcDao.getVoucherItemList(supplierRefundment.getRefundmentid(),"06");
					for (int i = 0;i<refundVoucherItemList.size();i++){
						VoucherItem voucherItem = (VoucherItem) refundVoucherItemList.get(i);
						if(voucherItem.getCheckcode().equals("31")){
							VoucherItem _voucherItem = new VoucherItem();
							_voucherItem.setVoucher(voucher);
							_voucherItem.setBusvoucherid(voucherItem.getVoucher().getVoucherid());
							_voucherItem.setRownumber(voucherItem.getRownumber());
							_voucherItem.setAmount(BigDecimal.valueOf(0));
							_voucherItem.setAmount2(BigDecimal.valueOf(0));
							_voucherItem.setFiyear(voucherItem.getVoucher().getFiyear());
							_voucherItem.setBusinessitemid(voucherItem.getBusinessitemid());
							_voucherItem.setFlag("R");
							voucherItemList.add(_voucherItem);
							itemValue = itemValue.add(voucherItem.getAmount2());
						}
					}
					
					voucher.setVoucherItem(voucherItemList);
					
					if(!importPayment.getFactcurrency().equals("CNY")){
						subtractVlaue = billValue.subtract(itemValue);
						
						if (new BigDecimal("0").compareTo(subtractVlaue) != 0){
							VoucherItem supplierVoucherItem = getProfitAndLossVoucherItem(
									supplierRefundment,subtractVlaue,"1");
							supplierVoucherItem.setRownumber("1");
							supplierVoucherItem.setVoucher(voucher);
							voucherItemList.add(supplierVoucherItem);
							
							VoucherItem profOrLossVoucherItem = getProfitAndLossVoucherItem(
									supplierRefundment,subtractVlaue,"2");
							profOrLossVoucherItem.setRownumber("2");
							profOrLossVoucherItem.setVoucher(voucher);
							voucherItemList.add(profOrLossVoucherItem);
						}
					}
					
					this.voucherHibernateDao.save(voucher);
				}
				
				/**
				 * 1.判断金额是否等于退款金额
				 */
//				//多次退款情况：之前退款+本次退款等于原付款金额，可清帐。
                BigDecimal allPefundmentAmount = this.supplierRefundItemJdbcDao.getSumPefundmentamountByPaymentItemId(supplierRefundItem.getPaymentitemid());
                BigDecimal amount = this.supplierRefundItemJdbcDao.getAmountByItemId(supplierRefundItem.getPaymentitemid());
                boolean cleanOldRefund = (allPefundmentAmount.add(supplierRefundItem.getRefundmentamount()).compareTo(amount)==0);
                
				if( supplierRefundItem.getRefundmentamount().compareTo(assignAmount) == 0 || cleanOldRefund)						
				{
					
//					//预收款凭证
//					if(supplierRefundItem.getRefundmentamount().compareTo(assignAmount) > 0){
//						
//					}
					
					//凭证行项目
					List<VoucherItem> lstTempVoucherItem = 
						this.supplierRefundItemJdbcDao.getVoucherItemForItemClear2(supplierRefundItem.getRefundmentitemid(), 
								supplierRefundItem.getPaymentitemid(), supplierRefundment.getRefundmentid());	
					
					lstVoucherItem.addAll(lstTempVoucherItem);
					if (cleanOldRefund) {
					    lstVoucherItem.addAll(this.supplierRefundItemJdbcDao.getVoucherItemForOldClean(supplierRefundItem.getPaymentitemid()));
					}
					
					//非人民币退款
					if( !isRmb )
					{			
						//收款分配行项目的分配金额本位币
						BigDecimal staAmount 
							= this.supplierRefundItemJdbcDao.getStaAmountByItemId(supplierRefundItem.getPaymentitemid());
						
						//退款金额本位币
						BigDecimal staRefundmentAmount = supplierRefundItem.getRefundmentvalue();

						//累加款，票的本位币金额合计
						sumStdAmount = sumStdAmount.add(staAmount);
						sumStdBillAmount = sumStdBillAmount.add(staRefundmentAmount);
					}		
					
					needToCreate = true;
				}								
			}
			
			/**
			 * 2.判断本次供应商退款所在的合同是否能清帐
			 */		
			// 合同
//			List<String> contractList = judgeContractIsAllClear(supplierRefundment);
//			if (null != contractList && contractList.size() > 0)
//			{
//				//得到票的本位币之和
//				BigDecimal billValue = new BigDecimal("0");
//				//得到分配项的本位币之和
//				BigDecimal itemValue = new BigDecimal("0");
//				//分配项之和和票之和的差
//				BigDecimal subtractVlaue = new BigDecimal("0");
//				
//				Set<VoucherItem> voucherItems = new HashSet<VoucherItem>();
//				// 处理合同下清帐凭证行项目.
//				Voucher voucher = getClearVoucher(supplierRefundment,"9");
//				
//				String contractnos = "";
//				for (int i = 0;i<contractList.size();i++){
//					String contractno = (String)contractList.get(i);
//					contractnos += "'" +contractno + "',";
//				}
//				contractnos = contractnos.substring(0, contractnos.length() - 1);
//				List voucherItemList = this.voucherItemJdbcDao.getPayVoucherItemListByContract(contractnos);
//				
//				for (int i = 0;i<voucherItemList.size();i++){
//					VoucherItem voucherItem = (VoucherItem) voucherItemList.get(i);
//					
//					VoucherItem _voucherItem = new VoucherItem();
//					_voucherItem.setBusvoucherid(voucherItem.getVoucher().getVoucherid());
//					_voucherItem.setRownumber(voucherItem.getRownumber());
//					_voucherItem.setVoucherno(voucherItem.getVoucher().getVoucherno());
//					_voucherItem.setFiyear(voucherItem.getVoucher().getFiyear());
//					_voucherItem.setFlag("R");
//					_voucherItem.setVoucher(voucher);
//					_voucherItem.setAmount(BigDecimal.valueOf(0));
//					_voucherItem.setAmount2(BigDecimal.valueOf(0));
//					_voucherItem.setBusinessitemid(voucherItem.getBusinessitemid());
//					voucherItems.add(_voucherItem);
//					itemValue = itemValue.add(voucherItem.getAmount2());
//				}
//				
//				List refundVoucherItemList = this.voucherItemJdbcDao.getRefundVoucherItemListByContract(contractnos);
//				
//				for (int i = 0;i<refundVoucherItemList.size();i++){
//					VoucherItem voucherItem = (VoucherItem) refundVoucherItemList.get(i);
//					
//					VoucherItem _voucherItem = new VoucherItem();
//					_voucherItem.setBusvoucherid(voucherItem.getVoucher().getVoucherid());
//					_voucherItem.setRownumber(voucherItem.getRownumber());
//					_voucherItem.setVoucherno(voucherItem.getVoucher().getVoucherno());
//					_voucherItem.setFiyear(voucherItem.getVoucher().getFiyear());
//					_voucherItem.setFlag("R");
//					_voucherItem.setVoucher(voucher);
//					_voucherItem.setAmount(BigDecimal.valueOf(0));
//					_voucherItem.setAmount2(BigDecimal.valueOf(0));
//					_voucherItem.setBusinessitemid(voucherItem.getBusinessitemid());
//					voucherItems.add(_voucherItem);
//					itemValue = itemValue.add(voucherItem.getAmount2());
//				}
//				
//				List<VendorTitle> vendorTitleList = this.vendorTitleJdbcDao.getByContract(contractnos);
//				for (int i = 0;i<vendorTitleList.size();i++){
//					VendorTitle vendorTitle = (VendorTitle)vendorTitleList.get(i);
//					
//					VoucherItem _voucherItem = new VoucherItem();
//					_voucherItem.setVoucher(voucher);
//					_voucherItem.setRownumber(vendorTitle.getBuzei());
//					_voucherItem.setVoucherno(vendorTitle.getBelnr());
//					_voucherItem.setFiyear(vendorTitle.getGjahr());
//					_voucherItem.setFlag("R");
//					_voucherItem.setAmount(BigDecimal.valueOf(0));
//					_voucherItem.setAmount2(BigDecimal.valueOf(0));
//					voucherItems.add(_voucherItem);
//					itemValue = itemValue.subtract(vendorTitle.getBwbje());
//				}
//				
//				if (!isRmb){
//					subtractVlaue = billValue.subtract(itemValue);
//					
//					if (new BigDecimal("0").compareTo(subtractVlaue) != 0){
//						VoucherItem supplierVoucherItem = getProfitAndLossVoucherItem(
//								supplierRefundment,subtractVlaue,"1");
//						supplierVoucherItem.setVoucher(voucher);
//						voucherItems.add(supplierVoucherItem);
//						
//						VoucherItem profOrLossVoucherItem = getProfitAndLossVoucherItem(
//								supplierRefundment,subtractVlaue,"2");
//						profOrLossVoucherItem.setVoucher(voucher);
//						voucherItems.add(profOrLossVoucherItem);
//					}
//				}
//				
//				voucher.setVoucherItem(voucherItems);
//				//统一保存
//				this.voucherService._save(voucher);
//			}

			List<String> clearedContractNo = new ArrayList<String>();
			
			/**
			 * 3.判断本次收款票所在的立项是否能清帐
			 */
			List<String> clearedProjectNo = this.clearAccountByProjectNo(itemSet, clearedContractNo);
			//根据立项号生成清帐凭证
			if(clearedProjectNo != null && clearedProjectNo.size() > 0)
			{
				String projectNo = this.constructSqlCond(clearedProjectNo);
				String contractNo = this.constructSqlCond(clearedContractNo);
				
				//凭证行项目
				List<VoucherItem> lstTempVoucherItem = 
					this.supplierRefundItemJdbcDao.getVoucherItemForProjectNoClear(projectNo,
							contractNo,
							supplierRefundment.getRefundmentid()
							);
				
				lstVoucherItem.addAll(lstTempVoucherItem);
				
				//非人民币退款
				if( !isRmb )
				{
					//款的金额
					BigDecimal staAmount 
						= this.supplierRefundItemJdbcDao.getSumStdAmountByProjectNo(projectNo,contractNo);
					
					//票的金额
					BigDecimal staBillAmount 
						= this.supplierRefundItemJdbcDao.getSumStdInvoiceAmountByProjectNo(projectNo,contractNo);

					//累加款，票的本位币金额合计
					sumStdAmount = sumStdAmount.add(staAmount);
					sumStdBillAmount = sumStdBillAmount.add(staBillAmount);
				}
				
				needToCreate = true;
			}
			
			/**
			 * 4.判断该客户是否能清帐
			 */
			//取得公司代码
			String bukrs = getCompanyIDByDeptID(supplierRefundment.getDept_id());
			String gsber = this.sysDeptJdbcDao.getDeptCodeById(supplierRefundment.getDept_id());
			boolean isClear = this.clearAccountByCustomer(supplierRefundment.getSupplier(),
										clearedContractNo, 
										clearedProjectNo, 
										totalRefundmentAmount,
										bukrs,refundCurrency,gsber,allPefundmentAmount2);
			
			//根据客户生成清帐凭证
			if( isClear )
			{
				String projectNo = this.constructSqlCond(clearedProjectNo);
				String contractNo = this.constructSqlCond(clearedContractNo);
				
				//凭证行项目
				List<VoucherItem> lstTempVoucherItem = 
					this.supplierRefundItemJdbcDao.getVoucherItemForSupplierClear(supplierRefundment.getSupplier(), 
						contractNo, 
						projectNo, 
						supplierRefundment.getRefundmentid(),
						bukrs,refundCurrency,gsber);
				for(SupplierRefundItem supplierRefundItem :supplierRefundment.getSupplierRefundItem()){
					String paymentitemid=supplierRefundItem.getPaymentitemid();
					//取得当前被退款的收款凭证
					if(!StringUtils.isNullBlank(paymentitemid)){
						VoucherItem voucherItem = this.voucherItemJdbcDao.getVoucherItem(paymentitemid, "1,4");	
						lstVoucherItem.add(voucherItem);
					}
					//取得当前的退款凭证
					List<VoucherItem> voucherItems =this.customerRefundItemJdbcDao.getVoucherItemJustRefundment(paymentitemid);
					lstVoucherItem.addAll(voucherItems);
				}
				lstVoucherItem.addAll(lstTempVoucherItem);
				
				//非人民币退款
				if( !isRmb )
				{
					//款的金额
					BigDecimal staAmount 
						= this.supplierRefundItemJdbcDao.getSumStdAmountBySupplier(supplierRefundment.getSupplier(),
								contractNo,
								projectNo,
								bukrs);
					
					//票的金额
					BigDecimal staBillAmount 
						= this.supplierRefundItemJdbcDao.getSumStdInvoiceAmountBySupplier(supplierRefundment.getSupplier(),
								contractNo,
								projectNo,
								bukrs);
					
					//累加款，票的本位币金额合计
					sumStdAmount = sumStdAmount.add(staAmount);
					sumStdBillAmount = sumStdBillAmount.add(staBillAmount);

				}
				
				needToCreate = true;
			}
			
            if (needToCreate == true) {
                // 差额绝对值
                BigDecimal absAmount = sumStdAmount.subtract(sumStdBillAmount).abs();

                //周村 退款汇率＝付款汇率不能修改，退款抬头汇率为银行汇率，出现汇差时，退款凭证多一行汇差损益，清帐凭证不出现损益行
                // (款) < (票)
                if (sumStdAmount.compareTo(sumStdBillAmount) == -1) {
                    lstNewVoucherItem = this.createNewVoucherItem(preCurrency, supplierRefundment, absAmount, "S");
                }
                // (款) > (票)
                else if (sumStdAmount.compareTo(sumStdBillAmount) == 1) {
                    lstNewVoucherItem = this.createNewVoucherItem(preCurrency, supplierRefundment, absAmount, "H");
                }

                // 保存清帐凭证
                String voucherNo = this.createClearAccountVoucher(preCurrency, supplierRefundment, lstVoucherItem,
                        lstNewVoucherItem, isRmb);

                lstVouchNo.add(voucherNo);
            }
		}
		return lstVouchNo;
	}
	
	/**
	 * 根据合同号，清帐
	 * @param itemSet
	 * @return 已清合同号
	 */
	private List<String> clearAccountByContractNo( Set<SupplierRefundItem> itemSet )
	{
		/**
		 * 获取合同列表
		 */
		List<String> contractNoList = this.getContractNoList(itemSet);
		
		List<String> clearedContractNo = new ArrayList<String>();
		
		//遍历合同列表
		for( String contractNo : contractNoList )
		{
			if( contractNo == null || contractNo.trim().equals(""))
			{
				continue;
			}
			// 未分配金额合计金额
			BigDecimal totalUnassignAmount = new BigDecimal("0.00");
			
			// 退款合计金额
			BigDecimal totalRefundAmount = new BigDecimal("0.00");
			
			// 发票金额合计金额
			BigDecimal totalInvoiceAmount = new BigDecimal("0.00");
			
			// 收款合计金额
			BigDecimal totalPaymentAmount = new BigDecimal("0.00");
			
			Iterator<SupplierRefundItem> it = itemSet.iterator();		
			while(it.hasNext())
			{
				SupplierRefundItem supplierRefundItem = it.next();
				if( contractNo.equals(supplierRefundItem.getContract_no()) )
				{

					//未分配金额
					BigDecimal unassignAmount = new BigDecimal("0.00");
					
					Map<String,BigDecimal> mapAmount 
							= this.supplierRefundItemJdbcDao.getUnsignedAmountByPaymentItemId(supplierRefundItem.getPaymentitemid());
					unassignAmount = mapAmount.get("unAssignedAmount");	
					
					//累加未分配金额
					totalUnassignAmount = totalUnassignAmount.add(unassignAmount);
					
					//累加退款金额
					totalRefundAmount = totalRefundAmount.add(supplierRefundItem.getRefundmentamount());
				}
			}
			
			// 该合同号下的未分配金额合计=退款金额
			//①该合同未分配金额合计（需重新获取）等于退款金额合计
			if( totalUnassignAmount.compareTo(totalRefundAmount) == 0 )
			{
				//获取合同号关联的外围已清发票金额
				totalInvoiceAmount = this.supplierRefundItemJdbcDao.getSumInvoiceAmountByContractNo(contractNo);
				
				//获取合同号关联的外围已清收款金额
				totalPaymentAmount = this.supplierRefundItemJdbcDao.getSumAmountByContractNo(contractNo);
				
				//已清发票合计金额 = 已清货款金额 - 本次退款金额
				//②发票金额 = 收款金额-本次退款金额
				if( totalInvoiceAmount.compareTo(totalPaymentAmount.subtract(totalRefundAmount)) == 0)
				{
					clearedContractNo.add(contractNo);
				}
			}
		}
		return clearedContractNo;
	}
	
	/**
	 * 根据立项号清帐
	 * @param itemSet
	 * @return
	 */
	private List<String> clearAccountByProjectNo( Set<SupplierRefundItem> itemSet, List<String> clearedContractNoList )
	{
		/**
		 * 拼装已清合同号字符串
		 */
		String strContractNo = "";
		int i = clearedContractNoList.size();
		for( String contractNo : clearedContractNoList )
		{
			i--;
			strContractNo = "'" + contractNo + "'";
			if( i > 0 )
			{
				strContractNo += ",";
			}
		}
		
		/**
		 * 获取合同列表
		 */
		List<String> projectNoList = this.getProjectNoList(itemSet);
		
		List<String> clearedProjectNo = new ArrayList<String>();
		
		//遍历合同列表
		for( String projectNo : projectNoList )			
		{
			if( projectNo == null || projectNo.trim().equals(""))
			{
				continue;
			}
			
			// 未分配金额合计金额
			BigDecimal totalUnassignAmount = new BigDecimal("0.00");
			
			// 退款合计金额
			BigDecimal totalRefundAmount = new BigDecimal("0.00");
			
			// 发票金额合计金额
			BigDecimal totalInvoiceAmount = new BigDecimal("0.00");
			
			// 付款合计金额
			BigDecimal totalPaymentAmount = new BigDecimal("0.00");
			
			Iterator<SupplierRefundItem> it = itemSet.iterator();		
			while(it.hasNext())
			{
				SupplierRefundItem supplierRefundItem = it.next();
				if( projectNo.equals(supplierRefundItem.getProject_no()) )
				{

					//未分配金额
					BigDecimal unassignAmount = new BigDecimal("0.00");
					
					Map<String,BigDecimal> mapAmount 
							= this.supplierRefundItemJdbcDao.getUnsignedAmountByPaymentItemId(supplierRefundItem.getPaymentitemid());
					unassignAmount = mapAmount.get("unAssignedAmount");
					
					//累加未分配金额
					totalUnassignAmount = totalUnassignAmount.add(unassignAmount);
					
					//累加退款金额
					totalRefundAmount = totalRefundAmount.add(supplierRefundItem.getRefundmentamount());
				}
			}
			
			// 该立项下的未分配金额合计=退款金额
			//①该合同未分配金额合计（需重新获取）等于退款金额合计
			if( totalUnassignAmount.compareTo(totalRefundAmount) == 0 )
			{
				//获取立项号关联的外围已清发票金额
				totalInvoiceAmount = this.supplierRefundItemJdbcDao.getSumInvoiceAmountByProjectNo(projectNo,strContractNo);
				
				//获取立项号关联的外围已清付款金额
				totalPaymentAmount = this.supplierRefundItemJdbcDao.getSumAmountByProjectNo(projectNo,strContractNo);
				
				//已清发票合计金额 = 已清货款金额 - 本次退款金额
				//②发票金额 = 付款金额-本次退款金额
				if( totalInvoiceAmount.compareTo(totalPaymentAmount.subtract(totalRefundAmount)) == 0)
				{					
					clearedProjectNo.add(projectNo);
				}
			}
		}
		
		return clearedProjectNo;
	}
	
	/**
	 * 获取合同号列表
	 * @param itemSet
	 * @return
	 */
	private List<String> getContractNoList( Set<SupplierRefundItem> itemSet )
	{
		String contractNo = null;
		List<String> retList = new ArrayList<String>();
		
		//遍历退款行项目
		Iterator<SupplierRefundItem> it = itemSet.iterator();	
		while(it.hasNext())
		{
			SupplierRefundItem supplierRefundItem = it.next();
			if( contractNo == null || !contractNo.equals(supplierRefundItem.getContract_no()) )
			{
				retList.add(supplierRefundItem.getContract_no());
			}			
			contractNo = supplierRefundItem.getContract_no();
		}
		return retList;
	}

	/**
	 * 获取立项号列表
	 * @param itemSet
	 * @return
	 */
	private List<String> getProjectNoList( Set<SupplierRefundItem> itemSet )
	{
		String projectNo = null;
		List<String> retList = new ArrayList<String>();
		
		//遍历退款行项目
		Iterator<SupplierRefundItem> it = itemSet.iterator();	
		while(it.hasNext())
		{
			SupplierRefundItem supplierRefundItem = it.next();
			if( projectNo == null || !projectNo.equals(supplierRefundItem.getProject_no()) )
			{
				retList.add(supplierRefundItem.getProject_no());
			}			
			projectNo = supplierRefundItem.getProject_no();
		}
		return retList;
	}

	/**
	 * 根据客户清帐
	 * @param supplier 供应商编号
	 * @param clearedContractNoList： 已清合同号
	 * @param clearedProjectNoList： 已清立项号
	 * @param totalRefundAmount : 客户的本次退款金额合计
	 */
	private boolean clearAccountByCustomer( String supplier, 
			List<String> clearedContractNoList, 
			List<String> clearedProjectNoList, 
			BigDecimal totalRefundAmount,
			String bukrs,
			String currency,
			String gsber,
			BigDecimal pefundAmount)
	{	
		/**
		 * 拼装已清合同号字符串
		 */
		String clearedContractNo = "";
		int i = clearedContractNoList.size();
		for( String contractNo : clearedContractNoList )
		{
			i--;
			clearedContractNo = "'" + contractNo + "'";
			if( i > 0 )
			{
				clearedContractNo += ",";
			}
		}
		
		/**
		 * 拼装已清立项号字符串
		 */
		String clearedProjectNo = "";
		i = clearedProjectNoList.size();
		for( String contractNo : clearedContractNoList )
		{
			i--;
			clearedProjectNo = "'" + contractNo + "'";
			if( i > 0 )
			{
				clearedProjectNo += ",";
			}
		}
		
//		//该客户的发票合计金额（外围已清）
//		BigDecimal sumInvoiceAmount 
//			= this.supplierRefundItemJdbcDao.getSumInvoiceAmountBySupplier(supplier, clearedContractNo, clearedProjectNo,bukrs);
//		
//		//该客户关联的收款金额合计（外围已清）
//		BigDecimal sumAmount = this.supplierRefundItemJdbcDao.getSumAmountBySupplier(supplier, clearedContractNo, clearedProjectNo,bukrs);
		
		// 供应商下已清票金额合计("2202010003" 退款只能是货款)
		BigDecimal sumInvoiceAmount = this.vendorTitleJdbcDao.getSumAmount2(supplier, clearedContractNo, clearedProjectNo, bukrs,"H",currency,gsber,"2202010003");
		BigDecimal sumAmount = this.vendorTitleJdbcDao.getSumAmount2(supplier, clearedContractNo, clearedProjectNo, bukrs,"S",currency,gsber,"2202010003");
		// 发票金额 合计 = 收款金额合计 - 本次退款金额
		if( sumInvoiceAmount.compareTo( sumAmount.add(pefundAmount).subtract(totalRefundAmount)) == 0)
		{
			return true;
		}		
		return false;
	}
	
    /**
     * 保存清帐凭证到凭证表
     * 
     * @param currency
     * @param customerRefundment
     * @param lstVoucherItem
     *            实际清帐用行项目
     * @param profitLossItem
     *            预览用行项目
     * @param isRmb
     *            是否人民币退款
     * @return
     */
    private String createClearAccountVoucher(String currency,
            SupplierRefundment supplierRefundment, List<VoucherItem> lstVoucherItem,
            List<VoucherItem> profitLossItem, boolean isRmb) {
        /**
         * 保存前先删除
         */
        // this.voucherJdbcDao.deleteVoucherByBusinessid(supplierRefundment.getRefundmentid());
        // this.voucherService.deleteVoucherByBusinessid(supplierRefundment.getRefundmentid());
        Voucher voucher = this.createVoucherTitle(currency, supplierRefundment);

        /**
         * 加入实际清帐用行项目
         */
        Set<VoucherItem> newItemSet = new HashSet<VoucherItem>();
        for (VoucherItem item : lstVoucherItem) {
            VoucherItem tempItem = new VoucherItem();
            tempItem.setFiyear(item.getFiyear());
            tempItem.setVoucherno(item.getVoucherno());
            tempItem.setRownumber(item.getRownumber());
            tempItem.setFlag("P");
            tempItem.setVoucher(voucher);
            tempItem.setAmount(new BigDecimal(0));
            tempItem.setAmount2(new BigDecimal(0));
            if ( StringUtils.isBlank(item.getVoucherno()) ) {
            	tempItem.setBusvoucherid(item.getVoucher().getVoucherid());
            }
            newItemSet.add(tempItem);
        }
        voucher.setVoucherItem(newItemSet);

        /**
         * 加入损益行项目 (非人民币)
         */
        if (!isRmb) {
            for (VoucherItem item : profitLossItem) {
                item.setVoucher(voucher);
                item.setFlag("P");
                item.setAmount(new BigDecimal(0));
                newItemSet.add(item);
            }
            this.voucherHibernateDao.save(voucher);
        }
        if (newItemSet.size() > 0) {
            this.voucherHibernateDao.save(voucher);
        }
        return voucher.getVoucherid();
    }
	
	/**
	 * 生成清帐凭证抬头
	 * @param currency: 实际退款币别
	 * @param customerRefundment: 客户退款对象
	 */
	private Voucher createVoucherTitle( String currency , SupplierRefundment supplierRefundment )
	{
		Voucher voucher = new Voucher();		
		voucher.setBusinessid(supplierRefundment.getRefundmentid());
		voucher.setBusinesstype("06");			//06： 供应商退款
		voucher.setCheckdate(supplierRefundment.getAccountdate().replace("-", "")); // 过账日期
		
		String bukrs = getCompanyIDByDeptID(supplierRefundment.getDept_id());
		voucher.setCompanycode(bukrs); //公司代码
		
		/**
		 * 退款币别
		 */
		voucher.setCurrency(currency);	//退款币别
		voucher.setExchangerate(supplierRefundment.getExchangerate());	// 退款汇率
		voucher.setFiperiod(supplierRefundment.getAccountdate().substring(5, 7));//会计期间
		voucher.setFiyear(supplierRefundment.getAccountdate().substring(0, 4));	//会计年度
		
		SimpleDateFormat  dateFormat=new SimpleDateFormat ("yyyyMMdd");
		voucher.setImportdate(dateFormat.format(new Date()));	//输入日期
		
		UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
		voucher.setImporter(userContext.getSysUser().getUserName());	//输入者
		voucher.setPreparer(userContext.getSysUser().getUserName());	//预制者
		voucher.setVoucherdate(supplierRefundment.getVoucherdate().replace("-", "")); //凭证日期
		voucher.setVouchertext(supplierRefundment.getText());   //凭证抬头文本
		
		voucher.setVouchertext("清帐凭证");   //凭证抬头文本
		voucher.setVouchertype("KZ");
		voucher.setBstat("A"); 	//设置清帐标志
		
		Set<SupplierDbBankItem> set = supplierRefundment.getSupplierDbBankItem();
		Iterator<SupplierDbBankItem> it = set.iterator();
		String konto = "";
		while( it.hasNext() )
		{
			konto = it.next().getBanksubject();
			break;
		}		
		voucher.setKonto(konto);	// 银行科目
		voucher.setValut(dateFormat.format(new Date()));	// 计息日
		voucher.setAgkon(supplierRefundment.getSupplier()); //供应商
		voucher.setAgkoa("K");
		voucher.setGsber(this.sysDeptJdbcDao.getDeptCodeById(supplierRefundment.getDept_id())); //业务范围
		voucher.setFlag("P");
		
		
        // 纯代理客户科目转换
        String comstomerid = supplierRefundment.getRepresentpaycust();
        if ( !StringUtils.isNullBlank(comstomerid) ) {
            voucher.setAgkon(comstomerid);
            voucher.setAgkoa("D");
        }
        
		return voucher;
	}

	/**
	 * 拼装sql in查询条件
	 * @param litInput
	 * @return
	 */
	private String constructSqlCond( List<String> litInput )
	{
		String strRet = "";
		
		int i = litInput.size();
		for( String str : litInput )
		{
			i--;
			strRet = "'" + str + "'";
			if( i > 0 )
			{
				strRet += ",";
			}
		}
		return strRet;
	}

	/**
	 * 创建汇兑损益行项目
	 * @param currency
	 * @param customerRefundment
	 * @param Amount
	 * @param shFlag
	 * @return
	 */
	private List<VoucherItem> createNewVoucherItem( String currency , SupplierRefundment supplierRefundment, 
					BigDecimal stdAmount, String shFlag )
	{
		List<VoucherItem> lstVoucherItem = new ArrayList<VoucherItem>();
		
		//公司代码
		String bukrs = getCompanyIDByDeptID(supplierRefundment.getDept_id());
		
		//业务范围
		String gsber = this.sysDeptJdbcDao.getDeptCodeById(supplierRefundment.getDept_id());
		
		
		/**
		 * 行项目1
		 */
		VoucherItem voucherItem1 = new VoucherItem();
		voucherItem1.setRownumber("1");
		
		//票的本位币 < 款的本位币
		if(shFlag.equals("H"))
		{
			voucherItem1.setCheckcode("38");
			voucherItem1.setDebitcredit("H");
		}
		//票的本位币 > 款的本位币
		else
		{
			voucherItem1.setCheckcode("25");
			voucherItem1.setDebitcredit("S");
		}
		
		String supplier = supplierRefundment.getSupplier(); //供应商						
		voucherItem1.setSubjectdescribe(this.customerRefundItemJdbcDao.getSupplierDescByCustomerId(supplier,bukrs));//科目说明： 客户名称
		
		/**
		 * 供应商前面补零
		 */
		int iaddLen = 10 - supplier.length();
		while( iaddLen != 0 )
		{
			supplier = "0" + supplier;
			iaddLen--;
		}
		voucherItem1.setSubject(supplier);	//科目： 供应商
        
        // 纯代理客户科目转换
        String comstomerid = supplierRefundment.getRepresentpaycust();
        if ( !StringUtils.isNullBlank(comstomerid) ) {
            
            String akont = this.customerRefundItemJdbcDao.getSubjectByCustomer(comstomerid,bukrs);
            voucherItem1.setSubjectdescribe(this.customerRefundItemJdbcDao.getCustomerDescByCustomerId(comstomerid,bukrs));//科目说明： 客户名称
            
            comstomerid = StringUtils.leftPad(comstomerid, 10, "0");
            voucherItem1.setSubject(comstomerid);    //科目： 客户
        }
		
		voucherItem1.setCurrency(currency);		// 币别
		voucherItem1.setAmount2(stdAmount);		// 本位币金额
		voucherItem1.setDepid(gsber);			// 设置业务范围
		voucherItem1.setText("清帐凭证");		// 抬头文本
		
		lstVoucherItem.add(voucherItem1);
		
		/**
		 * 行项目2
		 */
		VoucherItem voucherItem2 = new VoucherItem();
		voucherItem2.setRownumber("2");
		
		//票的本位币 < 款的本位币
		if(shFlag.equals("H"))
		{
			voucherItem2.setCheckcode("40");
			voucherItem2.setDebitcredit("S");
			voucherItem2.setSubject("6603050401");		//损失科目
			voucherItem2.setSubjectdescribe("损失科目");	//科目说明
		}
		//票的本位币 > 款的本位币
		else
		{
			voucherItem2.setCheckcode("50");
			voucherItem2.setDebitcredit("S");
			voucherItem2.setSubject("6603050402");	
			voucherItem2.setSubjectdescribe("收益科目");	//科目说明：
		}
								
		voucherItem2.setCurrency(currency);		// 币别
		voucherItem2.setAmount2(stdAmount);		// 本位币金额
		voucherItem2.setDepid(gsber);			// 设置业务范围
		voucherItem2.setText("清帐凭证");		// 抬头文本
		
		lstVoucherItem.add(voucherItem2);
		
		return lstVoucherItem;
		
		
	}

	public void setVoucherItemJdbcDao(VoucherItemJdbcDao voucherItemJdbcDao) {
		this.voucherItemJdbcDao = voucherItemJdbcDao;
	}
	/**
     * 得到结算科目的凭证行项目
     * @param importSettSubj
     * @return
     */
    public Set<VoucherItem> getSettSubjVoucherItem(SupplierRefundment supplierRefundment){
        SettleSubject settleSubject = supplierRefundment.getSettleSubject();
        Set<VoucherItem> voucherItemList = new HashSet<VoucherItem>();
        BigDecimal amount = new BigDecimal(0);
        BigDecimal standardamount = new BigDecimal(0);
        String bukrs = getCompanyIDByDeptID(supplierRefundment.getDept_id());
        if (null != settleSubject)
        {
            amount = settleSubject.getAmount1();
            standardamount = settleSubject.getStandardamount1();
            if (amount.abs().compareTo(new BigDecimal(0)) == 1||standardamount.abs().compareTo(new BigDecimal(0)) == 1)
            {
                // 开始添加凭证行项目
                VoucherItem voucherItem = new VoucherItem();
                
                //客户记账码
                if ("S".equals(settleSubject.getDebtcredit1()))
                {
                    voucherItem.setCheckcode("40");
                    //借/贷标识符
                    voucherItem.setDebitcredit(settleSubject.getDebtcredit1());
                }
                
                //客户记账码
                if ("H".equals(settleSubject.getDebtcredit1()))
                {
                    voucherItem.setCheckcode("50");
                    //借/贷标识符
                    voucherItem.setDebitcredit(settleSubject.getDebtcredit1());
                }
                //特殊G/L标识
                voucherItem.setGlflag("");
                String subjectname = this.customerRefundItemJdbcDao.getSubjectNameById(settleSubject.getSettlesubject1(),bukrs);
                //科目
                voucherItem.setSubject(settleSubject.getSettlesubject1());
                //科目说明
                voucherItem.setSubjectdescribe(subjectname);
                //统驭项目
                voucherItem.setControlaccount(settleSubject.getSettlesubject1());
                //统驭科目说明
                voucherItem.setCaremark(subjectname);
                //货币
                voucherItem.setCurrency(settleSubject.getSupplierRefundment().getRefundcurrency()); //取抬头退款币别
                // 纯代理客户科目转换，则取行项币别
                if ( !StringUtils.isNullBlank(supplierRefundment.getRepresentpaycust()) ) {
                    voucherItem.setCurrency(supplierRefundment.getSupplierRefundItem().iterator().next().getCurrency());
                }
                //货币金额
                voucherItem.setAmount(settleSubject.getAmount1());
                //本位币金额
                voucherItem.setAmount2(settleSubject.getStandardamount1());
                //到期日
                voucherItem.setZfbdt("");
                //部门
                voucherItem.setDepid(settleSubject.getDepid1());
                //业务范围
                voucherItem.setGsber(settleSubject.getDepid1());
                //文本
                voucherItem.setText(settleSubject.getSupplierRefundment().getText());
                //反记账标识
                if(settleSubject.getAntiaccount1().equals("Y")){
                    voucherItem.setUncheckflag("X");
                }
                //销售订单
                voucherItem.setSalesorder(settleSubject.getOrderno1());
                //销售订单行项目号
                voucherItem.setOrderrowno(settleSubject.getRowno1());
                //成本中心
                voucherItem.setCostcenter(settleSubject.getCostcenter1());

                voucherItemList.add(voucherItem);
            }
            
            amount = settleSubject.getAmount2();
            standardamount = settleSubject.getStandardamount2();
            if (amount.abs().compareTo(new BigDecimal(0)) == 1||standardamount.abs().compareTo(new BigDecimal(0)) == 1)
            {
                // 开始添加凭证行项目
                VoucherItem voucherItem = new VoucherItem();
                
                //客户记账码
                if ("S".equals(settleSubject.getDebtcredit2()))
                {
                    voucherItem.setCheckcode("40");
                    //借/贷标识符
                    voucherItem.setDebitcredit(settleSubject.getDebtcredit2());
                }
                
                //客户记账码
                if ("H".equals(settleSubject.getDebtcredit2()))
                {
                    voucherItem.setCheckcode("50");
                    //借/贷标识符
                    voucherItem.setDebitcredit(settleSubject.getDebtcredit2());
                }
                //特殊G/L标识
                voucherItem.setGlflag("");
                String subjectname = this.customerRefundItemJdbcDao.getSubjectNameById(settleSubject.getSettlesubject2(),bukrs);
                //科目
                voucherItem.setSubject(settleSubject.getSettlesubject2());
                //科目说明
                voucherItem.setSubjectdescribe(subjectname);
                //统驭项目
                voucherItem.setControlaccount(settleSubject.getSettlesubject2());
                //统驭科目说明
                voucherItem.setCaremark(subjectname);
                //货币
                voucherItem.setCurrency(settleSubject.getSupplierRefundment().getRefundcurrency());
                // 纯代理客户科目转换，则取行项币别
                if ( !StringUtils.isNullBlank(supplierRefundment.getRepresentpaycust()) ) {
                    voucherItem.setCurrency(supplierRefundment.getSupplierRefundItem().iterator().next().getCurrency());
                }
                
                //货币金额
                voucherItem.setAmount(settleSubject.getAmount2());
                //本位币金额
                voucherItem.setAmount2(settleSubject.getStandardamount2());
                //到期日
                voucherItem.setZfbdt("");
                //部门
                voucherItem.setDepid(settleSubject.getDepid2());
                //业务范围
                voucherItem.setGsber(settleSubject.getDepid2());
                //文本
                voucherItem.setText(settleSubject.getSupplierRefundment().getText());
                //反记账标识
                if(settleSubject.getAntiaccount2().equals("Y")){
                    voucherItem.setUncheckflag("X");
                }
                //销售订单
                voucherItem.setSalesorder(settleSubject.getOrderno2());
                //销售订单行项目号
                voucherItem.setOrderrowno(settleSubject.getRowno2());
                //成本中心
                voucherItem.setCostcenter(settleSubject.getCostcenter2());

                voucherItemList.add(voucherItem);
            }
            
            amount = settleSubject.getAmount3();
            standardamount = settleSubject.getStandardamount3();
            if (amount.abs().compareTo(new BigDecimal(0)) == 1||standardamount.abs().compareTo(new BigDecimal(0)) == 1)
            {
                // 开始添加凭证行项目
                VoucherItem voucherItem = new VoucherItem();
                
                //客户记账码
                if ("S".equals(settleSubject.getDebtcredit3()))
                {
                    voucherItem.setCheckcode("40");
                    //借/贷标识符
                    voucherItem.setDebitcredit(settleSubject.getDebtcredit3());
                }
                
                //客户记账码
                if ("H".equals(settleSubject.getDebtcredit3()))
                {
                    voucherItem.setCheckcode("50");
                    //借/贷标识符
                    voucherItem.setDebitcredit(settleSubject.getDebtcredit3());
                }
                //特殊G/L标识
                voucherItem.setGlflag("");
                String subjectname = this.customerRefundItemJdbcDao.getSubjectNameById(settleSubject.getSettlesubject3(),bukrs);
                //科目
                voucherItem.setSubject(settleSubject.getSettlesubject3());
                //科目说明
                voucherItem.setSubjectdescribe(subjectname);
                //统驭项目
                voucherItem.setControlaccount(settleSubject.getSettlesubject3());
                //统驭科目说明
                voucherItem.setCaremark(subjectname);
                //货币
                voucherItem.setCurrency(settleSubject.getSupplierRefundment().getRefundcurrency());
                // 纯代理客户科目转换，则取行项币别
                if ( !StringUtils.isNullBlank(supplierRefundment.getRepresentpaycust()) ) {
                    voucherItem.setCurrency(supplierRefundment.getSupplierRefundItem().iterator().next().getCurrency());
                }
                //货币金额
                voucherItem.setAmount(settleSubject.getAmount3());
                //本位币金额
                voucherItem.setAmount2(settleSubject.getStandardamount3());
                //到期日
                voucherItem.setZfbdt("");
                //部门
                voucherItem.setDepid(settleSubject.getDepid3());
                //业务范围
                voucherItem.setGsber(settleSubject.getDepid3());
                //文本
                voucherItem.setText(settleSubject.getSupplierRefundment().getText());
                //反记账标识
                if(settleSubject.getAntiaccount3().equals("Y")){
                    voucherItem.setUncheckflag("X");
                }
                //销售订单
                voucherItem.setSalesorder(settleSubject.getOrderno3());
                //销售订单行项目号
                voucherItem.setOrderrowno(settleSubject.getRowno3());
                //成本中心
                voucherItem.setCostcenter(settleSubject.getCostcenter3());

                voucherItemList.add(voucherItem);
            }
            
            amount = settleSubject.getAmount4();
            standardamount = settleSubject.getStandardamount4();
            if (amount.abs().compareTo(new BigDecimal(0)) == 1||standardamount.abs().compareTo(new BigDecimal(0)) == 1)
            {
                // 开始添加凭证行项目
                VoucherItem voucherItem = new VoucherItem();
                
                //客户记账码
                if ("S".equals(settleSubject.getDebtcredit4()))
                {
                    voucherItem.setCheckcode("40");
                    //借/贷标识符
                    voucherItem.setDebitcredit(settleSubject.getDebtcredit4());
                }
                
                //客户记账码
                if ("H".equals(settleSubject.getDebtcredit4()))
                {
                    voucherItem.setCheckcode("50");
                    //借/贷标识符
                    voucherItem.setDebitcredit(settleSubject.getDebtcredit4());
                }
                //特殊G/L标识
                voucherItem.setGlflag("");
                String subjectname = this.customerRefundItemJdbcDao.getSubjectNameById(settleSubject.getSettlesubject4(),bukrs);
                //科目
                voucherItem.setSubject(settleSubject.getSettlesubject4());
                //科目说明
                voucherItem.setSubjectdescribe(subjectname);
                //统驭项目
                voucherItem.setControlaccount(settleSubject.getSettlesubject4());
                //统驭科目说明
                voucherItem.setCaremark(subjectname);
                //货币
                voucherItem.setCurrency(settleSubject.getSupplierRefundment().getRefundcurrency());
                // 纯代理客户科目转换，则取行项币别
                if ( !StringUtils.isNullBlank(supplierRefundment.getRepresentpaycust()) ) {
                    voucherItem.setCurrency(supplierRefundment.getSupplierRefundItem().iterator().next().getCurrency());
                }
                //货币金额
                voucherItem.setAmount(settleSubject.getAmount4());
                //本位币金额
                voucherItem.setAmount2(settleSubject.getStandardamount4());
                //到期日
                voucherItem.setZfbdt("");
                //部门
                voucherItem.setDepid(settleSubject.getDepid4());
                //业务范围
                voucherItem.setGsber(settleSubject.getDepid4());
                //文本
                voucherItem.setText(settleSubject.getSupplierRefundment().getText());
                //反记账标识
                if(settleSubject.getAntiaccount4().equals("Y")){
                    voucherItem.setUncheckflag("X");
                }
                //销售订单
                voucherItem.setSalesorder(settleSubject.getOrderno4());
                //销售订单行项目号
                voucherItem.setOrderrowno(settleSubject.getRowno4());
                //利润中心
                voucherItem.setProfitcenter(settleSubject.getProfitcenter());

                voucherItemList.add(voucherItem);
            }
        }
        return voucherItemList;
    }
    
    /**
     * 纯资金往来
     * @param flowPay
     * @return
     */
    public Set<VoucherItem> getFundFlowVoucherItem(SupplierRefundment supplierRefundment){
        FundFlow flowPay = supplierRefundment.getFundFlow();
        Set<VoucherItem> voucherItemList = new HashSet<VoucherItem>();
        String bukrs = getCompanyIDByDeptID(supplierRefundment.getDept_id());
        BigDecimal amount = new BigDecimal(0);
        BigDecimal standardamount = new BigDecimal(0);
        if (null != flowPay)
        {
            amount = flowPay.getAmount1();
            standardamount = flowPay.getStandardamount1();
            if (amount.abs().compareTo(new BigDecimal(0)) == 1||standardamount.abs().compareTo(new BigDecimal(0)) == 1)
            {
                VoucherItem voucherItem = new VoucherItem();
                
                //客户记账码
                if ("S".equals(flowPay.getDebtcredit1()))
                {
                    voucherItem.setCheckcode("09");
                    //借/贷标识符
                    voucherItem.setDebitcredit(flowPay.getDebtcredit1());
                }
                
                //客户记账码
                if ("H".equals(flowPay.getDebtcredit1()))
                {
                    voucherItem.setCheckcode("19");
                    //借/贷标识符
                    voucherItem.setDebitcredit(flowPay.getDebtcredit1());
                }
                //客户记账码
                //voucherItem.setCheckcode("29");
                //借/贷标识符
                //voucherItem.setDebitcredit(flowPay.getDebtcredit1());
                //特殊G/L标识
                voucherItem.setGlflag(flowPay.getSpecialaccount1());
                //科目
                voucherItem.setSubject(flowPay.getCustomer1());
                //科目说明
                String Subjectdescribe = this.customerRefundItemJdbcDao.getCustomerDescByCustomerId(
                        flowPay.getCustomer1()
                        ,bukrs);
                if ("D".equals(flowPay.getSpecialaccount1())) {
                    //科目
                    voucherItem.setSubject(flowPay.getSupplier1());
                    //科目说明
                    Subjectdescribe = this.customerRefundItemJdbcDao.getSupplierDescByCustomerId(flowPay.getSupplier1(), bukrs);
                }
                voucherItem.setSubjectdescribe(Subjectdescribe);
                //货币
                voucherItem.setCurrency(flowPay.getSupplierRefundment().getRefundcurrency());
                //货币金额
                voucherItem.setAmount(flowPay.getAmount1());
                //本位币金额
                voucherItem.setAmount2(flowPay.getStandardamount1());
                //到期日
                voucherItem.setZfbdt("");
                //部门
                voucherItem.setDepid(flowPay.getDepid1());
                //业务范围
                voucherItem.setGsber(flowPay.getDepid1());
                String subject = this.customerRefundItemJdbcDao.getSubjectByCustomer(flowPay.getCustomer1(),bukrs);
                subject = this.customerRefundItemJdbcDao.getSkont(voucherItem,subject);
                if ("D".equals(flowPay.getSpecialaccount1())) {
                    subject = this.customerRefundItemJdbcDao.getSubjectBySuppliers(flowPay.getSupplier1(), bukrs);
                    subject = this.customerRefundItemJdbcDao.getSkont(voucherItem,subject);
                }
                //统驭项目
                voucherItem.setControlaccount(subject);
                //统驭科目说明
                voucherItem.setCaremark(this.customerRefundItemJdbcDao.getSubjectNameById(subject,bukrs));
                //文本
                voucherItem.setText(flowPay.getSupplierRefundment().getText());
                //反记账标识
                if(flowPay.getAntiaccount1().equals("Y")){
                    voucherItem.setUncheckflag("X");
                }
                voucherItemList.add(voucherItem);
            }
            
            amount = flowPay.getAmount2();
            standardamount = flowPay.getStandardamount2();
            if (amount.abs().compareTo(new BigDecimal(0)) == 1||standardamount.abs().compareTo(new BigDecimal(0)) == 1)
            {
                VoucherItem voucherItem = new VoucherItem();
                
                //客户记账码
                if ("S".equals(flowPay.getDebtcredit2()))
                {
                    voucherItem.setCheckcode("09");
                    //借/贷标识符
                    voucherItem.setDebitcredit(flowPay.getDebtcredit2());
                }
                
                //客户记账码
                if ("H".equals(flowPay.getDebtcredit2()))
                {
                    voucherItem.setCheckcode("19");
                    //借/贷标识符
                    voucherItem.setDebitcredit(flowPay.getDebtcredit2());
                }
                //客户记账码
                //voucherItem.setCheckcode("29");
                //借/贷标识符
                //voucherItem.setDebitcredit(flowPay.getDebtcredit2());
                //特殊G/L标识
                voucherItem.setGlflag(flowPay.getSpecialaccount2());
                //科目
                voucherItem.setSubject(flowPay.getCustomer2());
                //科目说明
                String Subjectdescribe = this.customerRefundItemJdbcDao.getCustomerDescByCustomerId(
                        flowPay.getCustomer2()
                        ,bukrs);
                if ("D".equals(flowPay.getSpecialaccount2())) {
                    //科目
                    voucherItem.setSubject(flowPay.getSupplier2());
                    //科目说明
                    Subjectdescribe = this.customerRefundItemJdbcDao.getSupplierDescByCustomerId(flowPay.getSupplier2(), bukrs);
                }
                voucherItem.setSubjectdescribe(Subjectdescribe);
                //货币
                voucherItem.setCurrency(flowPay.getSupplierRefundment().getRefundcurrency());
                //货币金额
                voucherItem.setAmount(flowPay.getAmount2());
                //本位币金额
                voucherItem.setAmount2(flowPay.getStandardamount2());
                //到期日
                voucherItem.setZfbdt("");
                //部门
                voucherItem.setDepid(flowPay.getDepid2());
                String subject = this.customerRefundItemJdbcDao.getSubjectByCustomer(flowPay.getCustomer2(),bukrs);
                subject = this.customerRefundItemJdbcDao.getSkont(voucherItem,subject);
                if ("D".equals(flowPay.getSpecialaccount2())) {
                    subject = this.customerRefundItemJdbcDao.getSubjectBySuppliers(flowPay.getSupplier2(), bukrs);
                    subject = this.customerRefundItemJdbcDao.getSkont(voucherItem,subject);
                }
                //统驭项目
                voucherItem.setControlaccount(subject);
                //统驭科目说明
                voucherItem.setCaremark(this.customerRefundItemJdbcDao.getSubjectNameById(subject,bukrs));
                //业务范围
                voucherItem.setGsber(flowPay.getDepid2());
                //文本
                voucherItem.setText(flowPay.getSupplierRefundment().getText());
                //反记账标识
                if(flowPay.getAntiaccount2().equals("Y")){
                    voucherItem.setUncheckflag("X");
                }
                
                voucherItemList.add(voucherItem);
            }
            
            amount = flowPay.getAmount3();
            standardamount = flowPay.getStandardamount3();
            if (amount.abs().compareTo(new BigDecimal(0)) == 1||standardamount.abs().compareTo(new BigDecimal(0)) == 1)
            {
                VoucherItem voucherItem = new VoucherItem();
                
                //客户记账码
                if ("S".equals(flowPay.getDebtcredit3()))
                {
                    voucherItem.setCheckcode("09");
                    //借/贷标识符
                    voucherItem.setDebitcredit(flowPay.getDebtcredit3());
                }
                
                //客户记账码
                if ("H".equals(flowPay.getDebtcredit3()))
                {
                    voucherItem.setCheckcode("19");
                    //借/贷标识符
                    voucherItem.setDebitcredit(flowPay.getDebtcredit3());
                }
                //客户记账码
                //voucherItem.setCheckcode("29");
                //借/贷标识符
                //voucherItem.setDebitcredit(flowPay.getDebtcredit3());
                //特殊G/L标识
                voucherItem.setGlflag(flowPay.getSpecialaccount3());
                //科目
                voucherItem.setSubject(flowPay.getCustomer3());
                //科目说明
                String Subjectdescribe = this.customerRefundItemJdbcDao.getCustomerDescByCustomerId(
                        flowPay.getCustomer3()
                        ,bukrs);
                if ("D".equals(flowPay.getSpecialaccount3())) {
                    //科目
                    voucherItem.setSubject(flowPay.getSupplier3());
                    //科目说明
                    Subjectdescribe = this.customerRefundItemJdbcDao.getSupplierDescByCustomerId(flowPay.getSupplier3(), bukrs);
                }
                voucherItem.setSubjectdescribe(Subjectdescribe);
                //货币
                voucherItem.setCurrency(flowPay.getSupplierRefundment().getRefundcurrency());
                //货币金额
                voucherItem.setAmount(flowPay.getAmount3());
                //本位币金额
                voucherItem.setAmount2(flowPay.getStandardamount3());
                //到期日
                voucherItem.setZfbdt("");
                //部门
                voucherItem.setDepid(flowPay.getDepid3());
                String subject = this.customerRefundItemJdbcDao.getSubjectByCustomer(flowPay.getCustomer3(),bukrs);
                subject = this.customerRefundItemJdbcDao.getSkont(voucherItem,subject);
                if ("D".equals(flowPay.getSpecialaccount3())) {
                    subject = this.customerRefundItemJdbcDao.getSubjectBySuppliers(flowPay.getSupplier3(), bukrs);
                    subject = this.customerRefundItemJdbcDao.getSkont(voucherItem,subject);
                }
                //统驭项目
                voucherItem.setControlaccount(subject);
                //统驭科目说明
                voucherItem.setCaremark(this.customerRefundItemJdbcDao.getSubjectNameById(subject,bukrs));
                //业务范围
                voucherItem.setGsber(flowPay.getDepid3());
                //文本
                voucherItem.setText(flowPay.getSupplierRefundment().getText());
                //反记账标识
                if(flowPay.getAntiaccount3().equals("Y")){
                    voucherItem.setUncheckflag("X");
                }
                
                voucherItemList.add(voucherItem);
            }
        }
        
        return voucherItemList;
    }
    
    
    /**
     * @创建作者：hongjj
     * @创建日期：2011-09-21
     * 增加预确认功能
     */ 
    public void prepConfirm(String refundmentId, String businessstate){
        //更新收款单的业务状态 为2
        this.supplierRefundItemJdbcDao.prepConfirm(refundmentId, businessstate);
    }

	/* (non-Javadoc)
	 * @see com.infolion.xdss3.supplierDrawbackGen.service.SupplierRefundmentServiceGen#_get(java.lang.String)
	 */
	@Override
	public SupplierRefundment _get(String id) {
		// TODO Auto-generated method stub
		return supplierRefundItemJdbcDao.getSupplierRefundmentById(id);
	}

	/* (non-Javadoc)
	 * @see com.infolion.xdss3.supplierDrawbackGen.service.SupplierRefundmentServiceGen#_getDetached(java.lang.String)
	 */
	@Override
	public SupplierRefundment _getDetached(String id) {
		// TODO Auto-generated method stub
		return supplierRefundItemJdbcDao.getSupplierRefundmentById(id);
	}

	/* (non-Javadoc)
	 * @see com.infolion.xdss3.supplierDrawbackGen.service.SupplierRefundmentServiceGen#_getEntityCopy(java.lang.String)
	 */
	@Override
	public SupplierRefundment _getEntityCopy(String id) {
		SupplierRefundment supplierRefundment = new SupplierRefundment();
		SupplierRefundment supplierRefundmentOld = supplierRefundItemJdbcDao.getSupplierRefundmentById(id);
		try
		{
			BeanUtils.copyProperties(supplierRefundment, supplierRefundmentOld);
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (InvocationTargetException e)
		{
			e.printStackTrace();
		}
		supplierRefundment.setRefundmentno(null); 
		//supplierRefundment.setRefundmentid(null); 
		supplierRefundment.setProcessstate(" ");
		return supplierRefundment;	
	}

	/* (non-Javadoc)
	 * @see com.infolion.xdss3.supplierDrawbackGen.service.SupplierRefundmentServiceGen#_getForEdit(java.lang.String)
	 */
	@Override
	public SupplierRefundment _getForEdit(String id) {
		// TODO Auto-generated method stub
		return supplierRefundItemJdbcDao.getSupplierRefundmentById(id);
	}
    
    
}