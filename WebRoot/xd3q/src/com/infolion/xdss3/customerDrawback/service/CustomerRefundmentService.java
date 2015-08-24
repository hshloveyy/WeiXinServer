/*
 * @(#)CustomerRefundmentService.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年06月17日 14点59分09秒
 *  描　述：创建
 */
package com.infolion.xdss3.customerDrawback.service;

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

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infolion.platform.console.org.dao.SysDeptJdbcDao;
import com.infolion.platform.console.sys.context.UserContext;
import com.infolion.platform.console.sys.context.UserContextHolder;
import com.infolion.platform.dpframework.util.StringUtils;
import com.infolion.xdss3.collectcbill.domain.CollectCbill;
import com.infolion.xdss3.collectitem.dao.CollectItemJdbcDao;
import com.infolion.xdss3.collectitem.domain.CollectItem;
import com.infolion.xdss3.collectitem.service.CollectItemService;
import com.infolion.xdss3.customerDrawback.dao.CustomerRefundItemJdbcDao;
import com.infolion.xdss3.customerDrawback.domain.CustomerDbBankItem;
import com.infolion.xdss3.customerDrawback.domain.CustomerRefundItem;
import com.infolion.xdss3.customerDrawback.domain.CustomerRefundment;
import com.infolion.xdss3.customerDrawbackGen.service.CustomerRefundmentServiceGen;
import com.infolion.xdss3.financeSquare.domain.FundFlow;
import com.infolion.xdss3.financeSquare.domain.SettleSubject;
import com.infolion.xdss3.masterData.dao.CustomerJdbcDao;
import com.infolion.xdss3.masterData.dao.CustomerTitleJdbcDao;
import com.infolion.xdss3.masterData.service.UnclearCustomerService;
import com.infolion.xdss3.payment.domain.ClearVoucherItemStruct;
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
 * 客户退款(CustomerRefundment)服务类
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
public class CustomerRefundmentService extends CustomerRefundmentServiceGen
{
	private Log log = LogFactory.getFactory().getLog(this.getClass());
	
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
	 * 凭证抬头
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
	private ReassignJdbcDao reassignJdbcDao;

	/**
	 * @param reassignJdbcDao
	 *            the reassignJdbcDao to set
	 */
	public void setReassignJdbcDao(ReassignJdbcDao reassignJdbcDao) {
		this.reassignJdbcDao = reassignJdbcDao;
	}

    @Autowired
    private VoucherService voucherService;
    
    public void setVoucherService(VoucherService voucherService) {
        this.voucherService = voucherService;
    }
    
    @Autowired
    protected VoucherItemJdbcDao voucherItemJdbcDao;
    public void setVoucherItemJdbcDao(VoucherItemJdbcDao voucherItemJdbcDao) {
        this.voucherItemJdbcDao = voucherItemJdbcDao;
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
    private CustomerJdbcDao customerJdbcDao;

    public void setCustomerJdbcDao(CustomerJdbcDao customerJdbcDao) {
        this.customerJdbcDao = customerJdbcDao;
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
    private PaymentItemService paymentItemService;

    /**
     * @param paymentItemService
     *            the paymentItemService to set
     */
    public void setPaymentItemService(PaymentItemService paymentItemService)
    {
        this.paymentItemService = paymentItemService;
    }
    
	/**
	 * 根据部门ID获得公司ID
	 */
	public String getCompanyIDByDeptID(String deptID)
	{
		return this.sysDeptJdbcDao.getCompanyCode(deptID);
	}

	/**
	 * 根据收款分配表ID获取客户退款行项目
	 * @param itemids
	 * @return
	 */
	public List<CustomerRefundItem> getCustomerRefundmentItemList( String itemids )
	{
		List<CustomerRefundItem> retList = new ArrayList<CustomerRefundItem>();
		List<CustomerRefundItem> tempList = new ArrayList<CustomerRefundItem>();
		
		String[] itemidList = StringUtils.splitString(itemids, ",");
		
		for( int i = 0; i < itemidList.length; i++ )
		{
			tempList = this.customerRefundItemJdbcDao.getCustomerRefundItem(itemidList[i]);
			if( tempList != null )
			{
				retList.addAll(tempList);
			}
		}
		return retList;
	}
	
	/**
	 * 根据银行ID获取客户退款银行行项目
	 * @param itemids
	 * @return
	 */
	public List<CustomerDbBankItem> getCustomerDbBankItemList( String itemids )
	{
		List<CustomerDbBankItem> retList = new ArrayList<CustomerDbBankItem>();
		CustomerDbBankItem customerRefundItem = new CustomerDbBankItem();
		
		String[] itemidList = StringUtils.splitString(itemids, ",");
		
		for( int i = 0; i < itemidList.length; i++ )
		{
			customerRefundItem = this.customerRefundItemJdbcDao.getCustomerDbBankItem(itemidList[i]);
			retList.add(customerRefundItem);
		}
		return retList;
	}
	
	/**
	 * 保存凭证信息
	 * @param refundmentId
	 * @return
	 */
	public List<String> saveVoucher(CustomerRefundment customerRefundment)
	{
		List<String> retList = new ArrayList<String>();
		retList.clear();
		
		/**
		 * 判断原币和退款币别是否相同
		 */
		
		String preCurrency = "";
		String refundCurrency = customerRefundment.getRefundcurrency(); // 取退款表
		
		boolean isSameCurrency = true;
        Iterator<CustomerRefundItem> it = customerRefundment.getCustomerRefundItem().iterator();
        while(it.hasNext())
        {
            CustomerRefundItem customerRefundItem = it.next();
            preCurrency = customerRefundItem.getCurrency();
            if ( ! StringUtils.isNullBlank(preCurrency) ) {
                if (!preCurrency.equals(refundCurrency)) {
                    isSameCurrency = false;
                }
            }
        }
		String voucherId = "";
		
		//是否人民币清帐
		boolean isRmb = false;
		
		/**
		 * 原币和退款币别相同
		 */
		if(isSameCurrency)
		{	String bukrs =  this.voucherService.getCompanyIDByDeptID(customerRefundment.getDept_id());		
			if( refundCurrency.equals("CNY")  || ("2600".equals(bukrs) && refundCurrency.equals("HKD")))
			{
				isRmb = true;
			}
			
			voucherId = saveVoucherForSameCurrency(customerRefundment,refundCurrency);
			retList.add(voucherId);
		}
		else
		{
			retList = saveVoucherForDiffCurrency(customerRefundment,refundCurrency);
		}
        
		return retList;
	}
	
	/**
	 * 根据退款ID获得退款业务对象
	 * @param refundmentId
	 * @return
	 */
	public CustomerRefundment getCustomerRefundmentById(String refundmentId )
	{
//		return this.customerRefundmentHibernateDao.get(refundmentId);
		return customerRefundItemJdbcDao.getCustomerRefundmentById(refundmentId);
	}
	
	/**
	 * 保存凭证信息(币别相同)
	 * @param refundmentId 退款ID
	 * @param currency 退款币别
	 * @ return : 凭证编号
	 */
	private String saveVoucherForSameCurrency(CustomerRefundment customerRefundment, String currency )
	{
			
		/**
		 * 保存前先删除
		 */		
		//this.voucherJdbcDao.deleteVoucherByBusinessid(customerRefundment.getRefundmentid());		

		Voucher voucher = new Voucher();
		Set<VoucherItem> voucherItemList = new HashSet<VoucherItem>();
		
		voucher.setBusinessid(customerRefundment.getRefundmentid());
		voucher.setBusinesstype("05");			//5： 客户退款
		
		voucher.setCheckdate(customerRefundment.getAccountdate().replace("-", "")); // 过账日期
		
		String bukrs = getCompanyIDByDeptID(customerRefundment.getDept_id());
		voucher.setCompanycode(bukrs); //公司代码
		/**
		 * 退款币别
		 */
		voucher.setCurrency(currency);	//退款币别
		voucher.setExchangerate(customerRefundment.getExchangerate());	// 退款汇率
		voucher.setFiperiod(customerRefundment.getAccountdate().substring(5, 7));//会计期间
		voucher.setFiyear(customerRefundment.getAccountdate().substring(0, 4));	//会计年度
		
		SimpleDateFormat  dateFormat=new SimpleDateFormat ("yyyyMMdd");
		voucher.setImportdate(dateFormat.format(new Date()));	//输入日期
		
		UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
		voucher.setImporter(userContext.getSysUser().getUserName());	//输入者
		voucher.setPreparer(userContext.getSysUser().getUserName());	//预制者
		
		voucher.setVoucherdate(customerRefundment.getVoucherdate().replace("-", "")); //凭证日期
		voucher.setVouchertext(customerRefundment.getText());   //凭证抬头文本
		voucher.setVouchertype("DR");
		voucher.setVoucherclass("1");
		/**
		 * 遍历客户退款行项目
		 */		
		int iRowNo = 1; 	//行项目号
		Iterator<CustomerRefundItem> refundItemit = customerRefundment.getCustomerRefundItem().iterator();		
		while( refundItemit.hasNext() )
		{
		  
			CustomerRefundItem customerRefundItem = refundItemit.next();
			
			// 退款行项
			
	                
    			VoucherItem voucherItem = new VoucherItem();
    			voucherItem.setVoucher(voucher);
    			
    			voucherItem.setCheckcode("01");	//客户记账码
                voucherItem.setAmount(customerRefundItem.getPefundmentamount().abs());// 金额取 实际退款金额
    			voucherItem.setAmount2(customerRefundItem.getRefundmentvalue().abs());	//退款金额（本位币）
    			voucherItem.setCurrency(customerRefundment.getRefundcurrency());	//退款币别 取退款表
    			voucherItem.setUncheckflag("X");
    			if (  customerRefundItem.getPefundmentamount().compareTo(new BigDecimal(0)) > -1  ) {
    			    voucherItem.setDebitcredit("S");			//借贷标识符
    			} else {
    			    voucherItem.setDebitcredit("H");         //借贷标识符
    			}
    			voucherItem.setDepid(this.sysDeptJdbcDao.getDeptCodeById(customerRefundment.getDept_id()));	//设置部门
    			voucherItem.setText(customerRefundment.getText());		//抬头文本
    			voucherItem.setBusinessitemid(customerRefundItem.getRefundmentitemid());
    			String comstomerid = customerRefundment.getCustomer(); //客户
    			
    			String akont = this.customerRefundItemJdbcDao.getSubjectByCustomer(customerRefundment.getCustomer(),bukrs);
    			voucherItem.setControlaccount(akont); //统驭科目
                voucherItem.setCaremark(this.customerRefundItemJdbcDao.getSubjectNameById(akont,bukrs)); // 统驭科目说明 
    			voucherItem.setSubjectdescribe(this.customerRefundItemJdbcDao.getCustomerDescByCustomerId(comstomerid,bukrs));//科目说明： 客户名称
    			
    			/**
    			 * 客户前面补零
    			 */
    			int iaddLen = 10 - comstomerid.length();
    			while( iaddLen != 0 )
    			{
    				comstomerid = "0" + comstomerid;
    				iaddLen--;
    			}
    			voucherItem.setSubject(comstomerid);	//科目： 客户
    			voucherItem.setRownumber(Integer.toString(iRowNo));//行项目号
    			
    			voucherItemList.add(voucherItem);
    			
			
			iRowNo++;			
		}
		
		/**
		 * 遍历退款银行
		 */
		Iterator<CustomerDbBankItem> bankItemit = customerRefundment.getCustomerDbBankItem().iterator();		
		while( bankItemit.hasNext() )
		{
			CustomerDbBankItem customerDbBankItem = bankItemit.next();
			
			VoucherItem voucherItem = new VoucherItem();
			
			voucherItem.setVoucher(voucher);
			
			voucherItem.setCheckcode("50");	//客户记账码
			voucherItem.setAmount(customerDbBankItem.getRefundamount());//退款金额
			voucherItem.setAmount2(customerDbBankItem.getRefundamount2());	//退款金额（本位币）
			voucherItem.setCurrency(currency);	//退款币别
			voucherItem.setDebitcredit("H");			//借贷标识符
			voucherItem.setDepid(this.sysDeptJdbcDao.getDeptCodeById(customerRefundment.getDept_id()));	//设置部门
			voucherItem.setText(customerRefundment.getText());		//抬头文本
								
			voucherItem.setSubjectdescribe(customerDbBankItem.getRefundbankname());//科目说明： 客户名称
			
			voucherItem.setSubject(customerDbBankItem.getBanksubject());	//科目： 客户
			voucherItem.setRownumber(Integer.toString(iRowNo));//行项目号
			voucherItem.setCashflowitem(customerRefundment.getCashflowitem());//现金流量项
			voucherItem.setControlaccount(customerDbBankItem.getBanksubject()); // 统驭科目
            voucherItem.setCaremark(this.customerRefundItemJdbcDao.getSubjectNameById(customerDbBankItem.getBanksubject(),bukrs)); // 统驭科目说明
			
			voucherItemList.add(voucherItem);
			iRowNo++;			
			
		}
		
	    // ******************结算科目行项目凭证行项目*******************************//
        Set<VoucherItem> SettlesubjectVoucherItemList = new HashSet<VoucherItem>();

        SettlesubjectVoucherItemList = this.getSettSubjVoucherItem(customerRefundment);

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
        
        FundFlowVoucherItemList = this.getFundFlowVoucherItem(customerRefundment);
        
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
	private List<String> saveVoucherForDiffCurrency(CustomerRefundment customerRefundment, String currency )
	{
		List<String> retList = new ArrayList<String>();

		/**
		 * 凭证抬头
		 */
		Voucher voucher = new Voucher();
		Set<VoucherItem> voucherItemList = new HashSet<VoucherItem>();
		
		voucher.setBusinessid(customerRefundment.getRefundmentid());
		voucher.setBusinesstype("05");			//5： 客户退款
		voucher.setCheckdate(customerRefundment.getAccountdate().replace("-", "")); // 过账日期
		
		String bukrs = getCompanyIDByDeptID(customerRefundment.getDept_id());
		voucher.setCompanycode(bukrs); //公司代码

		voucher.setExchangerate(customerRefundment.getExchangerate());	// 退款汇率
		voucher.setFiperiod(customerRefundment.getAccountdate().substring(5, 7));//会计期间
		voucher.setFiyear(customerRefundment.getAccountdate().substring(0, 4));	//会计年度
		
		SimpleDateFormat  dateFormat=new SimpleDateFormat ("yyyyMMdd");
		voucher.setImportdate(dateFormat.format(new Date()));	//输入日期
		
		UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
		voucher.setImporter(userContext.getSysUser().getUserName());	//输入者
		voucher.setPreparer(userContext.getSysUser().getUserName());	//预制者
		voucher.setVoucherdate(customerRefundment.getVoucherdate().replace("-", "")); //凭证日期
		voucher.setVouchertext(customerRefundment.getText());   //凭证抬头文本
		voucher.setVouchertype("DR");
		voucher.setVoucherclass("1");
		
		
		/**
		 * 遍历客户退款行项目
		 */			
		String preCurrency = "";	//原币币别
		BigDecimal totalPreAmount1 = new BigDecimal("0.0");	//中转科目原币退款金额
		BigDecimal totalPreAmount2 = new BigDecimal("0.0");	//中转科目本位币金额
		
		int iRowNo = 1; 	//行项目号
		Iterator<CustomerRefundItem> refundItemit = customerRefundment.getCustomerRefundItem().iterator();		
		while( refundItemit.hasNext() )
		{
			CustomerRefundItem customerRefundItem = refundItemit.next();
			// 退款行项
    			VoucherItem voucherItem = new VoucherItem();
    			voucherItem.setVoucher(voucher);
    			
    			voucherItem.setCheckcode("01");	//客户记账码
    			totalPreAmount1 = totalPreAmount1.add(customerRefundItem.getPefundmentamount());
                voucherItem.setAmount(customerRefundItem.getPefundmentamount().abs());// 金额取 实际退款金额
    			
                //add by chenf at 2011-02-15
                voucherItem.setUncheckflag("X");
                
                totalPreAmount2 = totalPreAmount2.add(customerRefundItem.getRefundmentvalue());
    			
    			voucherItem.setAmount2(customerRefundItem.getRefundmentvalue().abs());	//退款金额（本位币）
    			preCurrency = customerRefundItem.getCurrency();
    			voucherItem.setCurrency(customerRefundItem.getCurrency());	//原币币别
    			voucherItem.setBusinessitemid(customerRefundItem.getRefundmentitemid());	//  业务行项目ID
                if (  customerRefundItem.getPefundmentamount().compareTo(new BigDecimal(0)) > -1  ) {
                    voucherItem.setDebitcredit("S");            //借贷标识符
                } else {
                    voucherItem.setDebitcredit("H");         //借贷标识符
                }
    			voucherItem.setDepid(this.sysDeptJdbcDao.getDeptCodeById(customerRefundment.getDept_id()));	//设置部门
    			voucherItem.setText(customerRefundment.getText());		//抬头文本
    			
    			String comstomerid = customerRefundment.getCustomer(); //客户						
    			voucherItem.setSubjectdescribe(this.customerRefundItemJdbcDao.getCustomerDescByCustomerId(comstomerid,bukrs));//科目说明： 客户名称
    			String akont = this.customerRefundItemJdbcDao.getSubjectByCustomer(customerRefundment.getCustomer(),bukrs);
    			voucherItem.setControlaccount(akont); //统驭科目
    			voucherItem.setCaremark(this.customerRefundItemJdbcDao.getSubjectNameById(akont,bukrs)); // 统驭科目说明 
    			/**
    			 * 客户前面补零
    			 */
    			int iaddLen = 10 - comstomerid.length();
    			while( iaddLen != 0 )
    			{
    				comstomerid = "0" + comstomerid;
    				iaddLen--;
    			}
    			voucherItem.setSubject(comstomerid);	//科目： 客户
    			voucherItem.setRownumber(Integer.toString(iRowNo));//行项目号
    			
                voucherItemList.add(voucherItem);
                
                iRowNo++;		
		}
		
		/**
		 * 客户行项目对应中转科目
		 */
		VoucherItem voucherItem1 = new VoucherItem();
		voucherItem1.setVoucher(voucher);
		voucherItem1.setRownumber(Integer.toString(iRowNo));//行项目号
		voucherItem1.setCheckcode("50");	//记账码
		voucherItem1.setSubject("1122999999");	//科目
		voucherItem1.setSubjectdescribe(this.customerRefundItemJdbcDao.getSubjectNameById("1122999999",bukrs));//科目名称
		voucherItem1.setCurrency(preCurrency);				//原币币别
		voucherItem1.setAmount(totalPreAmount1);	//退款原币金额
		voucherItem1.setAmount2(totalPreAmount2);			//本位币金额
		voucherItem1.setDepid(this.sysDeptJdbcDao.getDeptCodeById(customerRefundment.getDept_id()));
		voucherItem1.setText(customerRefundment.getText());		//抬头文本
		voucherItem1.setDebitcredit("H");			//借贷标识符
		voucherItem1.setControlaccount("1122999999"); //统驭项目
		voucherItem1.setCaremark(this.customerRefundItemJdbcDao.getSubjectNameById("1122999999",bukrs)); //统驭科目说明
		voucherItemList.add(voucherItem1);
		iRowNo++;
		voucher.setCurrency(preCurrency);    //退款币别 hjj 修改取"币别"	
		
		  
        // ******************结算科目行项目凭证行项目 beg*******************************//
        Set<VoucherItem> SettlesubjectVoucherItemList = new HashSet<VoucherItem>();

        SettlesubjectVoucherItemList = this.getSettSubjVoucherItem(customerRefundment);

        Iterator<VoucherItem> Settlesubjectit = SettlesubjectVoucherItemList.iterator();
        while (Settlesubjectit.hasNext()) {
            VoucherItem setvoucherItem = Settlesubjectit.next();
            setvoucherItem.setRownumber(String.valueOf(iRowNo));
            setvoucherItem.setVoucher(voucher);
            iRowNo++;
            voucherItemList.add(setvoucherItem);
        }
        // ******************结算科目行项目凭证行项目 end*******************************//
        
        //******************特殊总帐行项目凭证行项目 beg *******************************//
        Set<VoucherItem> FundFlowVoucherItemList = new HashSet<VoucherItem>();
        
        FundFlowVoucherItemList = this.getFundFlowVoucherItem(customerRefundment);
        
        Iterator<VoucherItem> FundFlowit = FundFlowVoucherItemList.iterator();      
        while(FundFlowit.hasNext()){
            VoucherItem funvoucherItem =FundFlowit.next();
            funvoucherItem.setRownumber(String.valueOf(iRowNo));
            funvoucherItem.setVoucher(voucher);
            iRowNo++;
            voucherItemList.add(funvoucherItem);
        }
        //******************特殊总帐行项目凭证行项目 end *******************************//
		
		
		voucher.setVoucherItem(voucherItemList);		
		this.voucherHibernateDao.save(voucher);
		retList.add(voucher.getVoucherid());	
		
		
		/**
		 * 凭证抬头
		 */
		Voucher voucher2 = new Voucher();
		Set<VoucherItem> voucherItemList2 = new HashSet<VoucherItem>();
		
		voucher2.setBusinessid(customerRefundment.getRefundmentid());
		voucher2.setBusinesstype("05");			//5： 客户退款
		voucher2.setCheckdate(customerRefundment.getAccountdate().replace("-", "")); // 过账日期
		
		voucher2.setCompanycode(bukrs); //公司代码

		voucher2.setCurrency(currency);	//退款币别
		voucher2.setExchangerate(customerRefundment.getExchangerate());	// 退款汇率
		voucher2.setFiperiod(customerRefundment.getAccountdate().substring(5, 7));//会计期间
		voucher2.setFiyear(customerRefundment.getAccountdate().substring(0, 4));	//会计年度
		voucher2.setImportdate(dateFormat.format(new Date()));	//输入日期
		
		voucher2.setImporter(userContext.getSysUser().getUserName());	//输入者
		voucher2.setPreparer(userContext.getSysUser().getUserName());	//预制者
		voucher2.setVoucherdate(customerRefundment.getVoucherdate().replace("-", "")); //凭证日期
		voucher2.setVouchertext(customerRefundment.getText());   //凭证抬头文本
		voucher2.setVouchertype("DR");
		
		iRowNo = 1;
		
		/**
		 * 遍历退款银行
		 */
		BigDecimal totalAmount1 = new BigDecimal("0.0");	//中转科目原币退款金额
		BigDecimal totalAmount2 = new BigDecimal("0.0");	//中转科目本位币金额
		
		Iterator<CustomerDbBankItem> bankItemit = customerRefundment.getCustomerDbBankItem().iterator();		
		while( bankItemit.hasNext() )
		{
			CustomerDbBankItem customerDbBankItem = bankItemit.next();
			
			VoucherItem voucherItem = new VoucherItem();
			
			voucherItem.setVoucher(voucher2);
			
			voucherItem.setCheckcode("50");	//记账码
			totalAmount1 = totalAmount1.add(customerDbBankItem.getRefundamount());			
			voucherItem.setAmount(customerDbBankItem.getRefundamount());//退款金额
			totalAmount2 = totalAmount2.add(customerDbBankItem.getRefundamount2());
			
			voucherItem.setAmount2(customerDbBankItem.getRefundamount2());	//退款金额（本位币）
			voucherItem.setCurrency(currency);	//退款币别
			voucherItem.setDebitcredit("H");			//借贷标识符
			voucherItem.setDepid(this.sysDeptJdbcDao.getDeptCodeById(customerRefundment.getDept_id()));	//设置部门
			voucherItem.setText(customerRefundment.getText());		//抬头文本
									
			voucherItem.setSubjectdescribe(customerDbBankItem.getRefundbankname());//科目说明： 客户名称
	
			voucherItem.setSubject(customerDbBankItem.getBanksubject());	//科目： 
			voucherItem.setRownumber(Integer.toString(iRowNo));//行项目号
            voucherItem.setCashflowitem(customerRefundment.getCashflowitem());// 现金流量项
            
            voucherItem.setControlaccount(customerDbBankItem.getBanksubject()); // 统驭科目
            voucherItem.setCaremark(this.customerRefundItemJdbcDao.getSubjectNameById(customerDbBankItem.getBanksubject(),bukrs)); // 统驭科目说明
			
			voucherItemList2.add(voucherItem);
			iRowNo++;			
			
		}
		
		/**
		 * 银行对应中转科目行项
		 */
		VoucherItem voucherItem2 = new VoucherItem();
		voucherItem2.setVoucher(voucher2);
		voucherItem2.setRownumber(Integer.toString(iRowNo));//行项目号
		voucherItem2.setCheckcode("40");		//记账码
		voucherItem2.setSubject("1122999999");	//科目
		voucherItem2.setSubjectdescribe(this.customerRefundItemJdbcDao.getSubjectNameById("1122999999",bukrs));//科目名称
		voucherItem2.setCurrency(currency);				//退款币别
		voucherItem2.setAmount(totalAmount1);	//退款金额
		voucherItem2.setAmount2(totalAmount2);			//本位币金额
		voucherItem2.setDepid(this.sysDeptJdbcDao.getDeptCodeById(customerRefundment.getDept_id()));	//部门
		voucherItem2.setText(customerRefundment.getText());		//抬头文本
		voucherItem2.setDebitcredit("S");			//借贷标识符
		voucherItem2.setControlaccount("1122999999"); // 统驭项目
		voucherItem2.setCaremark(this.customerRefundItemJdbcDao.getSubjectNameById("1122999999",bukrs)); //统驭科目说明
		 voucherItem2.setUncheckflag("X");
		voucherItemList2.add(voucherItem2);
		iRowNo++;
				
		voucher2.setVoucherItem(voucherItemList2);		
		this.voucherHibernateDao.save(voucher2);
		retList.add(voucher2.getVoucherid());
		return retList;
	}

	/**
	 * 保存清帐凭证
	 * @param customerRefundment
	 * @return 产生的清帐凭证号
	 */
	public List<String> saveClearVoucher( CustomerRefundment customerRefundment )
	{
		Set<CustomerRefundItem> itemSet =  customerRefundment.getCustomerRefundItem(); //退款行项
		
		List<String> lstVouchNo = new ArrayList<String>(); // 返回凭证号列表
		
		List<VoucherItem> lstVoucherItem =  new ArrayList<VoucherItem>();		//清帐凭证行项目
		List<VoucherItem> lstNewVoucherItem =  new ArrayList<VoucherItem>();	//外币损益行项目
		
		//款的本位币金额合计值
		BigDecimal sumStdAmount = new BigDecimal("0.00");
		
		//票的本位币金额合计值
		BigDecimal sumStdBillAmount = new BigDecimal("0.00");
		
		/**
		 * 本次原币退款合计金额
		 */
		BigDecimal totalPefundmentAmount = new BigDecimal("0.00");
		BigDecimal allOldPefundmentAmount = new BigDecimal("0.00");
		//本次的可退金额合计
		BigDecimal allPefundmentAmount = new BigDecimal("0.00");
		boolean needToCreate = false;	// 判断是否需要产生清帐凭证
		boolean amountCanClean = true;	// 判断是否产生清帐凭证
		String currency = customerRefundment.getRefundcurrency(); // 取退款表
		boolean isRmb = "CNY".equals(currency);
		String preCurrency = "";
		Map collectItemMap =  new HashMap();
		if( itemSet != null )
		{
			/**
			 * 退款行项目清帐
			 */
            for (CustomerRefundItem refItem : itemSet) {
                totalPefundmentAmount = totalPefundmentAmount.add(refItem.getPefundmentamount()); //得到本次所有实际退款金额
                allPefundmentAmount = allPefundmentAmount.add(refItem.getUnassignamount()); //本次的可退金额合计
                //多次退款情况：之前退款+本次退款等于原收款金额，可清帐。
                allOldPefundmentAmount = allOldPefundmentAmount.add(this.customerRefundItemJdbcDao
                        .getSumPefundmentamountByCollectItemId(refItem.getCollectitemid()));
                if (refItem.getUnassignamount().compareTo(refItem.getRefundmentamount()) > 0) {
                    amountCanClean = false;
                }
            }
			for( CustomerRefundItem customerRefundItem : itemSet )
			{
                if (!amountCanClean) {
			        continue;
			    }
				preCurrency = customerRefundItem.getCurrency();
				BigDecimal assignAmount 
					= this.customerRefundItemJdbcDao.getAmountByItemId(customerRefundItem.getCollectitemid());
				// 实际剩余保证金
				BigDecimal actsuretybond = this.customerRefundItemJdbcDao.getActsuretybond(customerRefundItem.getCollectitemid());
				/**
				 * 1.判断金额是否等于原币退款金额(hjj修改)，原为:"金额＝退款金额"
				 */
				
				//非保证金退款
				boolean istybond = customerRefundItem.getIstybond().equals("N"); // true为货款,false为保证金
				
				BigDecimal amount = this.customerRefundItemJdbcDao.getAmountByItemId(customerRefundItem.getCollectitemid());
				boolean cleanOldRefund = (allOldPefundmentAmount.add(customerRefundItem.getPefundmentamount()).compareTo(amount)==0);
				
					/**
					 * (分配金额-保证金)等于原币退款金额
					 * 或者
					 * 保证金 等于原币退款金额 
					 */
					if( ( istybond && assignAmount.subtract(actsuretybond).compareTo(customerRefundItem.getPefundmentamount()) == 0)	
					    ||
					    (!istybond && actsuretybond.compareTo(customerRefundItem.getPefundmentamount())==0)
					    || cleanOldRefund
					    //|| ( istybond && customerRefundItem.getPefundmentamount().compareTo(customerRefundItem.getUnassignamount())==0 )
					    )
					{
						/**
						 * 生成清帐凭证
						 */
					    
						//清帐凭证行项目
                        List<VoucherItem> lstTempVoucherItem = this.customerRefundItemJdbcDao
                                .getVoucherItemForItemClear4(customerRefundItem.getCollectitemid(),
                                        customerRefundItem.getRefundmentitemid());
                        if (collectItemMap.containsKey(customerRefundItem.getCollectitemid())) {
                            lstTempVoucherItem = this.customerRefundItemJdbcDao.getVoucherItemJustRefundment(customerRefundItem.getRefundmentitemid());
                        }
  
						lstVoucherItem.addAll(lstTempVoucherItem);
						collectItemMap.put(customerRefundItem.getCollectitemid(), customerRefundItem.getCollectitemid());
						if (allOldPefundmentAmount.compareTo(new BigDecimal(0)) > 0) {
                            if (!lstVoucherItem.containsAll(this.customerRefundItemJdbcDao
                                    .getVoucherItemForOldClean(customerRefundItem.getCollectitemid()))) {
                                lstVoucherItem.addAll(this.customerRefundItemJdbcDao
                                        .getVoucherItemForOldClean(customerRefundItem.getCollectitemid()));
                            }
						}
                       // 退款清帐不重复同一收款行项凭证
//                        for (int i = 0; i < lstVoucherItem.size() - 1; i++) {
//                            for (int j = lstVoucherItem.size() - 1; j > i; j--) {
//                                if (lstVoucherItem.get(j).getVoucher().getVoucherno()
//                                        .equals(lstVoucherItem.get(i).getVoucher().getVoucherno())
//                                        && customerRefundItem.getCollectitemid().equals((lstVoucherItem.get(j).getBusvoucherid()))) {
//                                    lstVoucherItem.remove(j);
//                                }
//                            }
//                        }
                        
						//非人民币退款
						if( !isRmb )
						{
							//收款分配行项目的分配金额本位币
							BigDecimal staAmount 
								= this.customerRefundItemJdbcDao.getStaAmountByItemId(customerRefundItem.getCollectitemid());
							
							//退款金额本位币 REFUNDMENTVALUE
							BigDecimal staRefundmentAmount = customerRefundItem.getRefundmentvalue();
							
							//累加款和票的金额
							sumStdAmount = sumStdAmount.add(staAmount); //退款金额
							sumStdBillAmount = sumStdBillAmount.add(staRefundmentAmount); //原币退款金额
						}
						
						needToCreate = true;
					}				
			}
			/**
			 * 1.5 部分清帐 
			 * */
			if ( itemSet.size()==1) { // 退款行项仅一行
			    CustomerRefundItem customerRefundItem = itemSet.iterator().next();

			    // 根据收款行项目Id查询同一收款ID的收款行项目
			    List<CollectItem> collectitemList = this.customerRefundItemJdbcDao.getCollectItemListByItemId(customerRefundItem.getCollectitemid());
			    if (collectitemList!=null && collectitemList.size()==1) { // 收款行项目仅一行
			        CollectItem collectItem = collectitemList.get(0);
			        BigDecimal assignAmount = collectItem.getAssignamount(); // 分配金额、总金额
			        
			        // 根据收款抬头Id查询收款发票项
			        List<CollectCbill> billList = this.customerRefundItemJdbcDao.getCollectcBillListByItemId(collectItem.getCollect().getCollectid());
			        if (billList!=null && billList.size()==1) { // 发票项仅一行
			            CollectCbill bill = billList.get(0);
			            
                        if (bill.getReceivableamount().compareTo(bill.getClearamount()) == 0 // 票的总额=票的清帐金额
                                && bill.getReceivableamount().add(
                                        customerRefundItem.getRefundmentvalue()).compareTo(
                                        assignAmount) == 0 // 票的总额+退款原币金额 ＝ 总金额
                                && assignAmount.compareTo(customerRefundItem.getPefundmentamount()) != 0 // 退款原币金额不等于总金额
                                        ) {
                            
                          //清帐凭证行项目
                            List<VoucherItem> lstTempVoucherItem = 
                                this.customerRefundItemJdbcDao.getVoucherItemForBill(bill.getBillno()); // 用billno关联ycustomertitle.invoice得到清帐行项目信息(年度、行项目号、凭证号)
                            
                            List<VoucherItem> lstTempVoucherItem2 = this.customerRefundItemJdbcDao
                                    .getVoucherItemForItemClear3(customerRefundItem.getRefundmentitemid(), customerRefundment.getRefundmentid());
                            
                            List<VoucherItem> lstTempVoucherItem3 = this.customerRefundItemJdbcDao
                            .getVoucherItemForItemClear3(collectItem.getCollectitemid(), collectItem.getCollect().getCollectid());
                            
                            lstVoucherItem.addAll(lstTempVoucherItem);
                            lstVoucherItem.addAll(lstTempVoucherItem2);
                            lstVoucherItem.addAll(lstTempVoucherItem3);
                            needToCreate = true;
                        }
			        } // end 发票仅一行
			    }
			}
			
			
			/**
			 * 2.判断本次客户退款所在的合同是否能清帐(不可用)
			 */			
			List<String> clearedContractNo = this.clearAccountByContractNo(itemSet);
			
			//根据合同号产生清帐凭证
			if(clearedContractNo != null && clearedContractNo.size() > 0)
			{
				String contractNo_sql = this.constructSqlCond(clearedContractNo);
				BigDecimal billValue = new BigDecimal(0);
				BigDecimal itemValue = new BigDecimal(0);
				//凭证行项目 票
				List<VoucherItem> lstTempVoucherItem = 
					this.customerRefundItemJdbcDao.getVoucherItemForContractNoClearBill(contractNo_sql, 
							customerRefundment.getRefundmentid());
				if (lstTempVoucherItem !=null) {
				    for (VoucherItem item : lstTempVoucherItem) {
				        billValue = billValue.add(item.getAmount2());			// 24085.13       
				        item.setAmount2(null);
				    }
				}
				lstVoucherItem.addAll(lstTempVoucherItem);
				
				//凭证行项目 款
				List<VoucherItem> lstTempVoucherItem2 = 
				    this.customerRefundItemJdbcDao.getVoucherItemForContractNoClear(contractNo_sql, 
				            customerRefundment.getRefundmentid(), customerRefundment.getCustomer());
				if (lstTempVoucherItem2 !=null) {
				    for (VoucherItem item : lstTempVoucherItem2) {
				        billValue = billValue.add(item.getAmount2());				// 532
				        item.setAmount2(null);
				    }
				}
				lstVoucherItem.addAll(lstTempVoucherItem2);
				
				for (String conntractNo : clearedContractNo){
    		        //得到之前汇损，或调整金的voucheritem
    	            List<VoucherItem> voucherItems = voucherItemJdbcDao.getVoucherItems(customerRefundment.getCustomer(), conntractNo);
    	            for(VoucherItem voucherItem2 : voucherItems){
    	                ClearVoucherItemStruct clearVoucherItemStruct = new ClearVoucherItemStruct();
    	                clearVoucherItemStruct.setBwbje(voucherItem2.getAmount2());
    	                clearVoucherItemStruct.setRowno(voucherItem2.getRownumber());
    	                clearVoucherItemStruct.setSubject(voucherItem2.getSubject());
    	                clearVoucherItemStruct.setVoucherno(voucherItem2.getVoucher().getVoucherno());
    	                clearVoucherItemStruct.setYear(voucherItem2.getVoucher().getFiyear());
    	                if("01".equals(voucherItem2.getCheckcode()) || "08".equals(voucherItem2.getCheckcode())){
    	                    billValue = billValue.add(clearVoucherItemStruct.getBwbje());     // 582.87
    	                }else if("11".equals(voucherItem2.getCheckcode()) || "15".equals(voucherItem2.getCheckcode())){
    	                    itemValue = itemValue.add(clearVoucherItemStruct.getBwbje());
    	                }
    	                VoucherItem voucherItem = getClearVoucherItem(clearVoucherItemStruct);              
    	                lstVoucherItem.add(voucherItem);
    	            }
    	            
    	            // 得到之前的款分配信息(可考虑移到循环外提高性能。)
    	            List<ClearVoucherItemStruct> clearVoucherItemStructList2 = this.customerTitleJdbcDao.getContractCollectItemInfo(customerRefundment.getCustomer(),conntractNo);
    	            for (ClearVoucherItemStruct clearVoucherItemStruct : clearVoucherItemStructList2)
    	            {
    	                VoucherItem voucherItem = getClearVoucherItem(clearVoucherItemStruct);
    	                itemValue = itemValue.add(clearVoucherItemStruct.getBwbje());
    	                lstVoucherItem.add(voucherItem);
    	            }
    	            
    	            // 得到本次分配项的行项目信息
    	            for( CustomerRefundItem customerRefundItem : itemSet )
    	            {
                        List<VoucherItem> oldVoucherItems = this.customerRefundItemJdbcDao
                                .getVoucherItemForContractNoClear3(conntractNo, customerRefundItem
                                        .getRefundmentitemid());
                        for(VoucherItem oldItem : oldVoucherItems){
                            itemValue = itemValue.add(oldItem.getAmount2());   //25200
                            oldItem.setAmount2(null);
                        }
                        lstVoucherItem.addAll(oldVoucherItems);
    	            }
//    	            itemSet
				}
				//非人民币退款
				if( !isRmb )
				{
					//款的金额本位币
//					BigDecimal staAmount 
//						= this.customerRefundItemJdbcDao.getSumStdAmountByContractNo(contractNo_sql);
				    BigDecimal staAmount = new BigDecimal(0);
				    
					//票的本位币金额
//					BigDecimal staBillAmount 
//						= this.customerRefundItemJdbcDao.getSumStaInvoiceAmountByContractNo(contractNo_sql);
					BigDecimal staBillAmount = new BigDecimal(0) ;

					//累加款和票的金额
					sumStdAmount = sumStdAmount.add(itemValue);
					sumStdBillAmount = sumStdBillAmount.add(billValue);
					
				}
				needToCreate = true;
			}
			
			
			/**
			 * 3.判断本次收款票所在的立项是否能清帐(不可用)
			 */
			List<String> clearedProjectNo = this.clearAccountByProjectNo(itemSet, clearedContractNo);
			
			//根据立项号生成清帐凭证
			if(clearedProjectNo != null && clearedProjectNo.size() > 0)
			{
				String projectNo = this.constructSqlCond(clearedProjectNo);
				String contractNo = this.constructSqlCond(clearedContractNo);
				
				//凭证行项目
				List<VoucherItem> lstTempVoucherItem = 
					this.customerRefundItemJdbcDao.getVoucherItemForProjectNoClear(projectNo,
							contractNo,
							customerRefundment.getRefundmentid()
							);
				
				lstVoucherItem.addAll(lstTempVoucherItem);
				
				//非人民币退款
				if( !isRmb )
				{
					//款的金额本位币
					BigDecimal staAmount 
						= this.customerRefundItemJdbcDao.getSumStdAmountByProjectNo(projectNo,contractNo);
					
					//票的本位币金额
					BigDecimal staBillAmount 
						= this.customerRefundItemJdbcDao.getSumStdInvoiceAmountByProjectNo(projectNo,contractNo);

					//累加款和票的金额
					sumStdAmount = sumStdAmount.add(staAmount);
					sumStdBillAmount = sumStdBillAmount.add(staBillAmount);
				}
				needToCreate = true;
			}
			
			/**
			 * 4.判断该客户是否能清帐(客户可用)
			 */
			String bukrs = getCompanyIDByDeptID(customerRefundment.getDept_id());
			String gsber = this.sysDeptJdbcDao.getDeptCodeById(customerRefundment.getDept_id());
			boolean isClear = this.clearAccountByCustomer(customerRefundment.getCustomer(),
										clearedContractNo, 
										clearedProjectNo, 
										totalPefundmentAmount,
										bukrs,currency,gsber,allPefundmentAmount);
			
			//根据客户生成清帐凭证
			if( isClear )
			{
				String projectNo = this.constructSqlCond(clearedProjectNo);
				String contractNo = this.constructSqlCond(clearedContractNo);
				
				//凭证行项目
				List<VoucherItem> lstTempVoucherItem = 
					this.customerRefundItemJdbcDao.getVoucherItemForCustomerClear(customerRefundment.getCustomer(), 
						contractNo, 
						projectNo, 
						customerRefundment.getRefundmentid(),
						bukrs,currency,gsber);
				
				for (CustomerRefundItem customerRefundItem : customerRefundment.getCustomerRefundItem()){
					String collectitemid=customerRefundItem.getCollectitemid();
					//取得当前被退款的收款凭证
					if(!StringUtils.isNullBlank(collectitemid)){
						VoucherItem voucherItem = this.voucherItemJdbcDao.getVoucherItem(collectitemid, "1,4");	
						lstVoucherItem.add(voucherItem);
					}
					//取得当前的退款凭证
					List<VoucherItem> voucherItems =this.customerRefundItemJdbcDao.getVoucherItemJustRefundment(customerRefundItem.getRefundmentitemid());
					lstVoucherItem.addAll(voucherItems);
				}
				
				lstVoucherItem.addAll(lstTempVoucherItem);
				
				//非人民币退款
				if( !isRmb )
				{
					//款的金额本位币
					BigDecimal staAmount 
						= this.customerRefundItemJdbcDao.getSumStdAmountByCustomer(customerRefundment.getCustomer(),
										contractNo,		
										projectNo,
										bukrs);
					
					//票的本位币金额
					BigDecimal staBillAmount 
						= this.customerRefundItemJdbcDao.getSumStdInvoiceAmountByCustomer(customerRefundment.getCustomer(),
								contractNo,projectNo,bukrs);

					//累加款和票的金额
					sumStdAmount = sumStdAmount.add(staAmount);
					sumStdBillAmount = sumStdBillAmount.add(staBillAmount);
				}
				needToCreate = true;
			}
			
			/**
			 * 需要产生清帐凭证 
			 */
			if( needToCreate == true )
			{
				//差额绝对值
				BigDecimal absAmount = sumStdAmount.subtract(sumStdBillAmount).abs();
				
				//款  < 票
				if( sumStdAmount.compareTo(sumStdBillAmount) == -1 )
				{
					lstNewVoucherItem = this.createNewVoucherItem(preCurrency,
							customerRefundment, absAmount, "H");
				}
				// 款 > 票
				else if( sumStdAmount.compareTo(sumStdBillAmount) == 1 )
				{
					lstNewVoucherItem = this.createNewVoucherItem(preCurrency,
							customerRefundment, absAmount, "S");
				}
				
				 //保存清帐凭证
				String voucherNo = this.createClearAccountVoucher(preCurrency, customerRefundment, lstVoucherItem, 
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
	private List<String> clearAccountByContractNo( Set<CustomerRefundItem> itemSet )
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
			
			// 原币退款合计金额
			BigDecimal totalPefundAmount = new BigDecimal("0.00");
			
			// 退款合计金额
			BigDecimal totalRefundamount = new BigDecimal("0.00");
			// 收款合计金额
			BigDecimal totalAssignamount = new BigDecimal("0.00");
			
			// 发票金额合计金额
			BigDecimal totalInvoiceAmount = new BigDecimal("0.00");
			
			// 收款合计金额
			BigDecimal totalCollectAmount = new BigDecimal("0.00");
			
			// 调整金金额
            BigDecimal ajAmount = new BigDecimal(0);
            
            boolean haveActsuretybond =true; // 默认有保证金，不可清
			Iterator<CustomerRefundItem> it = itemSet.iterator();		
			while(it.hasNext())
			{
				CustomerRefundItem customerRefundItem = it.next();
				if( contractNo.equals(customerRefundItem.getContract_no()) )
				{
				    //判断保证金是否为空
				    haveActsuretybond = this.customerRefundItemJdbcDao.judgeHaveActsuretybondByContractNo(contractNo, customerRefundItem.getCollectno());
					//未分配金额
					BigDecimal unassignAmount = new BigDecimal("0.00");
					
					//保证金退款
					if( customerRefundItem.getIstybond().equals("Y") )
					{
						unassignAmount = this.customerRefundItemJdbcDao.getActsuretybond(customerRefundItem.getCollectitemid());
					}
					//普通退款
					else
					{
//						Map<String,BigDecimal> mapAmount 
//							= this.customerRefundItemJdbcDao.getUnsignedAmountByCollectItemId(customerRefundItem.getCollectitemid());
					    if ( StringUtils.isNullBlank(customerRefundItem.getCollectitemid()) ) {
					        unassignAmount = new BigDecimal(0);
					    } else {
    					    Map<String,BigDecimal> mapAmount = this.customerRefundItemJdbcDao.getTotalUnassignedAmount(customerRefundItem.getCollectitemid());
    						unassignAmount = mapAmount.get("unassignedAmount");
						}
					}	
					
		            // 得到之前汇损，或调整金的voucheritem
	                List<VoucherItem> voucherItems = voucherItemJdbcDao.getVoucherItems(customerRefundItem.getCustomerRefundment().getCustomer(), contractNo);
	                for(VoucherItem oldVoucherItem : voucherItems){
	                    if("01".equals(oldVoucherItem.getCheckcode()) || "08".equals(oldVoucherItem.getCheckcode())){
	                        ajAmount = ajAmount.subtract(oldVoucherItem.getAmount()); // 票
	                    }else if("11".equals(oldVoucherItem.getCheckcode()) || "15".equals(oldVoucherItem.getCheckcode())){
	                        ajAmount = ajAmount.add(oldVoucherItem.getAmount()); // 款
	                    }                   
	                }
	                
					//累加未分配金额
					totalUnassignAmount = totalUnassignAmount.add(unassignAmount); // <款>
					
					//累加原币退款金额
					totalPefundAmount = totalPefundAmount.add(customerRefundItem.getPefundmentamount());
					
					//累加退款金额
					totalRefundamount = totalRefundamount.add(customerRefundItem.getRefundmentamount());
					//累加收款金额
					totalAssignamount = totalAssignamount.add(customerRefundItem.getAssignamount());
				}
			}
			if (haveActsuretybond) {
			    continue;
			}
            // 该合同号下的未分配金额合计=退款金额
            //①该合同未分配金额合计（需重新获取）等于原币退款金额合计
			if( totalUnassignAmount.compareTo(totalPefundAmount) == 0 )
			{
                //获取合同号关联的外围已清发票金额<票>
				totalInvoiceAmount = this.customerRefundItemJdbcDao.getSumInvoiceAmountByContractNo(contractNo);
				
				//获取合同号关联的外围已清收款金额 <款>
				totalCollectAmount = this.customerRefundItemJdbcDao.getSumAmountByContractNo(contractNo);
				totalCollectAmount = totalCollectAmount.add(totalAssignamount);
				//已清发票合计金额 = 已清货款金额 - 本次原币退款金额
				//②发票金额 = 收款金额-本次退款金额
				if( totalInvoiceAmount.compareTo(totalCollectAmount.subtract(totalPefundAmount)) == 0)
				{	
		            BigDecimal sumAllContractBillTot = this.customerTitleJdbcDao.getSumAmount(contractNo); //票
		            BigDecimal sumAllContractFundTot = this.collectItemJdbcDao.getSumAmount(contractNo); //款
		            BigDecimal tot1 = sumAllContractBillTot.add(totalRefundamount); // <退款 票>
		            BigDecimal tot2 = sumAllContractFundTot.add(totalAssignamount); // <收款>
		            tot2 = tot2.add(ajAmount);
		            log.debug("sumAllContractBillTot:" + sumAllContractBillTot + ",sumAllContractFundTot:" + sumAllContractFundTot);

		            if (tot1.compareTo(tot2) == 0)
		            {
		                log.debug("合同号：" + contractNo + "，可以清帐！");
		                clearedContractNo.add(contractNo);
		            }
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
	private List<String> clearAccountByProjectNo( Set<CustomerRefundItem> itemSet, List<String> clearedContractNoList )
	{
		/**
		 * 拼装已清合同号字符串
		 */
		String strContractNo = this.constructSqlCond(clearedContractNoList);
		
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
			
			// 原币退款合计金额
			BigDecimal totalPefundAmount = new BigDecimal("0.00");
			
			// 发票金额合计金额
			BigDecimal totalInvoiceAmount = new BigDecimal("0.00");
			
			// 收款合计金额
			BigDecimal totalCollectAmount = new BigDecimal("0.00");
			
			Iterator<CustomerRefundItem> it = itemSet.iterator();		
			while(it.hasNext())
			{
				CustomerRefundItem customerRefundItem = it.next();
				if( projectNo.equals(customerRefundItem.getProject_no()) )
				{

					//未分配金额
					BigDecimal unassignAmount = new BigDecimal("0.00");
					
					//保证金退款
					if( customerRefundItem.getIstybond().equals("Y") )
					{
						unassignAmount = this.customerRefundItemJdbcDao.getActsuretybond(customerRefundItem.getCollectitemid());
					}
					//普通退款
					else
					{	
						Map<String,BigDecimal> mapAmount 
							= this.customerRefundItemJdbcDao.getUnsignedAmountByCollectItemId(customerRefundItem.getCollectitemid());
						unassignAmount = mapAmount.get("unAssignedAmount");
					}	
					
					//if("N".equals(customerRefundItem.getIstybond())){
						//累加未分配金额
						totalUnassignAmount = totalUnassignAmount.add(unassignAmount);
						
						//累加原币退款金额
						totalPefundAmount = totalPefundAmount.add(customerRefundItem.getPefundmentamount());
					//}
				}
			}
			
			// 该立项下的未分配金额合计=退款金额
			//①该合同未分配金额合计（需重新获取）等于原币退款金额合计
			if( totalUnassignAmount.compareTo(totalPefundAmount) == 0 )
			{
				//获取立项号关联的外围已清发票金额
				totalInvoiceAmount = this.customerRefundItemJdbcDao.getSumInvoiceAmountByProjectNo(projectNo,strContractNo);
				
				//获取立项号关联的外围已清收款金额
				totalCollectAmount = this.customerRefundItemJdbcDao.getSumAmountByProjectNo(projectNo,strContractNo);
				
				//已清发票合计金额 = 已清货款金额 - 本次原币退款金额
				//②发票金额 = 收款金额-本次原币退款金额
				if( totalInvoiceAmount.compareTo(totalCollectAmount.subtract(totalPefundAmount)) == 0)
				{					
					clearedProjectNo.add(projectNo);
				}
			}
		}
		
		return clearedProjectNo;
	}
	
	/**
	 * 获取合同号列表(移除重复合同号)
	 * @param itemSet
	 * @return
	 */
	private List<String> getContractNoList( Set<CustomerRefundItem> itemSet )
	{
		String contractNo = null;
		List<String> retList = new ArrayList<String>();
		
		//遍历退款行项目
		Iterator<CustomerRefundItem> it = itemSet.iterator();	
		while(it.hasNext())
		{
			CustomerRefundItem customerRefundItem = it.next();
			if( contractNo == null || !contractNo.equals(customerRefundItem.getContract_no()) )
			{
				retList.add(customerRefundItem.getContract_no());
			}			
			contractNo = customerRefundItem.getContract_no();
		}
		return retList;
	}

	/**
	 * 获取立项号列表
	 * @param itemSet
	 * @return
	 */
	private List<String> getProjectNoList( Set<CustomerRefundItem> itemSet )
	{
		String projectNo = null;
		List<String> retList = new ArrayList<String>();
		
		//遍历退款行项目
		Iterator<CustomerRefundItem> it = itemSet.iterator();	
		while(it.hasNext())
		{
			CustomerRefundItem customerRefundItem = it.next();
			if( projectNo == null || !projectNo.equals(customerRefundItem.getProject_no()) )
			{
				retList.add(customerRefundItem.getProject_no());
			}			
			projectNo = customerRefundItem.getProject_no();
		}
		return retList;
	}

	/**
	 * 根据客户清帐
	 * @param customerId 客户编号
	 * @param clearedContractNoList： 已清合同号
	 * @param clearedProjectNoList： 已清立项号
	 * @param totalPefundAmount : 客户的本次退款金额合计
	 *  @param pefundAmount : 本次可退金额合计
	 * return 客户是否可请
	 */
	private boolean clearAccountByCustomer( String customerId, 
			List<String> clearedContractNoList, 
			List<String> clearedProjectNoList, 
			BigDecimal totalPefundAmount,
			String bukrs,
			String currency,
			String gsber,
			BigDecimal pefundAmount)
	{	
		/**
		 * 拼装已清合同号字符串
		 */
		String clearedContractNo = this.constructSqlCond(clearedContractNoList);
		
		/**
		 * 拼装已清立项号字符串
		 */
		String clearedProjectNo = this.constructSqlCond(clearedProjectNoList);
		
		//该客户的发票合计金额（外围已清）
//		BigDecimal sumInvoiceAmount = this.customerRefundItemJdbcDao.getSumInvoiceAmountByCustomer(customerId, clearedContractNo, clearedProjectNo,bukrs);
//		
//		//该客户关联的收款金额合计（外围已清）
//		BigDecimal sumAmount = this.customerRefundItemJdbcDao.getSumAmountByCustomer(customerId, clearedContractNo, clearedProjectNo, bukrs);
		//"1122010003" 退款只能是货款
		BigDecimal sumInvoiceAmount = this.customerTitleJdbcDao.getSumAmount2(customerId, clearedContractNo, clearedProjectNo, bukrs,"S",currency,gsber,"1122010003");
		BigDecimal sumAmount = this.customerTitleJdbcDao.getSumAmount2(customerId, clearedContractNo, clearedProjectNo, bukrs,"H",currency,gsber,"1122010003");
		
		// 发票金额 合计 = 该客户关联的收款金额合计（外围已清） + (本次可退金额合计 - 本次原币退款金额合计)
		if( sumInvoiceAmount.compareTo( sumAmount.add(pefundAmount).subtract(totalPefundAmount)) == 0)
		{
			return true;
		}
		return false;
	}

	/**
	 * 保存清帐凭证到凭证表
	 * @param currency
	 * @param customerRefundment
	 * @param cleanItemList	实际清帐用行项目
	 * @param proLossItemList 预览用行项目
	 * @param isRmb 是否人民币退款
	 * @return
	 */
    private String createClearAccountVoucher(String currency,
            CustomerRefundment customerRefundment, List<VoucherItem> cleanItemList,
            List<VoucherItem> proLossItemList, boolean isRmb) {

        Voucher voucher = this.createVoucherTitle(currency, customerRefundment);
        Set<VoucherItem> newItemSet = new HashSet<VoucherItem>();
        /**
         * 加入实际清帐用行项目
         */
        for (VoucherItem item : cleanItemList) {
            VoucherItem tempItem = new VoucherItem();
            tempItem.setFiyear(item.getFiyear());
            tempItem.setVoucherno(item.getVoucherno());
            tempItem.setRownumber(item.getRownumber());
            tempItem.setFlag("R");
            tempItem.setVoucher(voucher);
            tempItem.setAmount(BigDecimal.valueOf(0));
            tempItem.setAmount2(BigDecimal.valueOf(0));
            if(null !=item.getVoucher()){
            	tempItem.setBusvoucherid(item.getVoucher().getVoucherid()); // 凭证预览ID
            }
            newItemSet.add(tempItem);
        }

        if (!isRmb) { // 人民币不加入损益科目
            /**
             * 加入预览用行项目 损益科目
             */
            for (VoucherItem item : proLossItemList) {
                item.setVoucher(voucher);
                item.setFlag("R");

                if (item.getAmount() == null) {
                    item.setAmount(BigDecimal.valueOf(0)); // 补充凭证行项目不为空字段
                }
                if (item.getAmount2() == null) {
                    item.setAmount2(BigDecimal.valueOf(0)); // 补充凭证行项目不为空字段
                }
                newItemSet.add(item);
            }
        }
        
        voucher.setVoucherItem(newItemSet);
        this.voucherHibernateDao.save(voucher);

        return voucher.getVoucherid();
    }
	
	/**
	 * 生成清帐凭证抬头
	 * @param currency: 实际退款币别
	 * @param customerRefundment: 客户退款对象
	 */
	private Voucher createVoucherTitle( String currency , CustomerRefundment customerRefundment )
	{
		Voucher voucher = new Voucher();							
		voucher.setBusinessid(customerRefundment.getRefundmentid());
		voucher.setBusinesstype("05");			//5： 客户退款
		
		voucher.setCheckdate(customerRefundment.getAccountdate().replace("-", "")); // 过账日期
		
		String bukrs = getCompanyIDByDeptID(customerRefundment.getDept_id());
		voucher.setCompanycode(bukrs); //公司代码
		/**
		 * 退款币别
		 */
		voucher.setCurrency(currency);	//退款币别
		voucher.setExchangerate(customerRefundment.getExchangerate());	// 退款汇率
		voucher.setFiperiod(customerRefundment.getAccountdate().substring(5, 7));//会计期间
		voucher.setFiyear(customerRefundment.getAccountdate().substring(0, 4));	//会计年度
		
		SimpleDateFormat  dateFormat=new SimpleDateFormat ("yyyyMMdd");
		voucher.setImportdate(dateFormat.format(new Date()));	//输入日期
		
		UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
		voucher.setImporter(userContext.getSysUser().getUserName());	//输入者
		voucher.setPreparer(userContext.getSysUser().getUserName());	//预制者
		voucher.setVoucherdate(customerRefundment.getVoucherdate().replace("-", "")); //凭证日期
		voucher.setVouchertext("清帐凭证");   //凭证抬头文本
		voucher.setVouchertype("DZ");
		voucher.setBstat("A"); 	//设置清帐标志
		
		Set<CustomerDbBankItem> set = customerRefundment.getCustomerDbBankItem();
		Iterator<CustomerDbBankItem> it = set.iterator();
		String konto = "";
		while( it.hasNext() )
		{
			konto = it.next().getBanksubject();
			break;
		}		
		voucher.setKonto(konto);	// 银行科目
		voucher.setValut(dateFormat.format(new Date()));	// 计息日
		voucher.setAgkon(customerRefundment.getCustomer()); //供应商
		voucher.setAgkoa("D");
		voucher.setGsber(this.sysDeptJdbcDao.getDeptCodeById(customerRefundment.getDept_id())); //业务范围
		voucher.setFlag("R");
		
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
	private List<VoucherItem> createNewVoucherItem( String currency , CustomerRefundment customerRefundment, 
					BigDecimal stdAmount, String shFlag )
	{
		List<VoucherItem> lstVoucherItem = new ArrayList<VoucherItem>();
		
		//公司代码
		String bukrs = getCompanyIDByDeptID(customerRefundment.getDept_id());
		
		//业务范围
		String gsber = this.sysDeptJdbcDao.getDeptCodeById(customerRefundment.getDept_id());
		String subject="";
		
		/**
		 * 行项目1
		 */
		VoucherItem voucherItem1 = new VoucherItem();
		voucherItem1.setRownumber("1");
		
		//票的本位币 < 款的本位币
		if(shFlag.equals("S"))
		{
			voucherItem1.setCheckcode("05");
			voucherItem1.setDebitcredit("S");
		}
		//票的本位币 > 款的本位币
		else
		{
			voucherItem1.setCheckcode("15");
			voucherItem1.setDebitcredit("H");
		}
		
		
		String comstomerid = customerRefundment.getCustomer(); //客户						
		voucherItem1.setSubjectdescribe(this.customerRefundItemJdbcDao.getCustomerDescByCustomerId(comstomerid,bukrs));//科目说明： 客户名称
		
		/**
		 * 客户前面补零
		 */
		int iaddLen = 10 - comstomerid.length();
		while( iaddLen != 0 )
		{
			comstomerid = "0" + comstomerid;
			iaddLen--;
		}
		voucherItem1.setSubject(comstomerid);	//科目： 客户
		
		voucherItem1.setCurrency(currency);		// 币别
		voucherItem1.setAmount2(stdAmount);		// 本位币金额
		voucherItem1.setDepid(gsber);			// 设置业务范围
		voucherItem1.setText("清帐凭证");		// 抬头文本

        if(currency.equals("CNY")){
            subject = this.customerRefundItemJdbcDao.getSubjectByCustomer("0040000244",bukrs);
        }else{
            subject = this.customerRefundItemJdbcDao.getSubjectByCustomer("0050000208",bukrs);
        }
        subject = this.customerRefundItemJdbcDao.getSkont(voucherItem1,subject);
        //统驭项目
        voucherItem1.setControlaccount(subject);
        //统驭科目说明
        voucherItem1.setCaremark(this.customerRefundItemJdbcDao.getSubjectNameById(subject,bukrs));
		lstVoucherItem.add(voucherItem1);
		
		/**
		 * 行项目2
		 */
		VoucherItem voucherItem2 = new VoucherItem();
		voucherItem2.setRownumber("2");
		
		//票的本位币 < 款的本位币
		if(shFlag.equals("S"))
		{
			voucherItem2.setCheckcode("50");
			voucherItem2.setDebitcredit("H");
			voucherItem2.setSubject("6603050402");	
			voucherItem2.setSubjectdescribe("收益科目");	//科目说明：
			String subjectname = this.customerRefundItemJdbcDao.getSubjectNameById("6603050402",bukrs);
            //统驭项目
			voucherItem2.setControlaccount("6603050402");
            //统驭科目说明
			voucherItem2.setCaremark(subjectname);
		}
		//票的本位币 > 款的本位币
		else
		{
			voucherItem2.setCheckcode("40");
			voucherItem2.setDebitcredit("S");
			voucherItem2.setSubject("6603050401");		//损失科目
			voucherItem2.setSubjectdescribe("损失科目");	//科目说明：
			String subjectname = this.customerRefundItemJdbcDao.getSubjectNameById("6603050401",bukrs);
			voucherItem2.setControlaccount("6603050401"); //统驭项目
			voucherItem2.setCaremark(subjectname); //统驭科目说明
		}
								
		voucherItem2.setCurrency(currency);		// 币别
		voucherItem2.setAmount2(stdAmount);		// 本位币金额
		voucherItem2.setDepid(gsber);			// 设置业务范围
		voucherItem2.setText("清帐凭证");		// 抬头文本
		

        
		lstVoucherItem.add(voucherItem2);
		
		return lstVoucherItem;
		
		
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
     * 得到结算科目的凭证行项目
     * @param importSettSubj
     * @return
     */
    public Set<VoucherItem> getSettSubjVoucherItem(CustomerRefundment customerRefundment){
        SettleSubject settleSubject = customerRefundment.getSettleSubject();
        Set<VoucherItem> voucherItemList = new HashSet<VoucherItem>();
        BigDecimal amount = new BigDecimal(0);
        BigDecimal standardamount = new BigDecimal(0);
        String bukrs = getCompanyIDByDeptID(customerRefundment.getDept_id());
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
                voucherItem.setCurrency(settleSubject.getCustomerRefundment().getRefundcurrency());
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
                voucherItem.setText(settleSubject.getCustomerRefundment().getText());
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
                voucherItem.setCurrency(settleSubject.getCustomerRefundment().getRefundcurrency());
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
                voucherItem.setText(settleSubject.getCustomerRefundment().getText());
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
                voucherItem.setCurrency(settleSubject.getCustomerRefundment().getRefundcurrency());
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
                voucherItem.setText(settleSubject.getCustomerRefundment().getText());
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
                voucherItem.setCurrency(settleSubject.getCustomerRefundment().getRefundcurrency());
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
                voucherItem.setText(settleSubject.getCustomerRefundment().getText());
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
    public Set<VoucherItem> getFundFlowVoucherItem(CustomerRefundment customerRefundment){
        FundFlow flowPay = customerRefundment.getFundFlow();
        Set<VoucherItem> voucherItemList = new HashSet<VoucherItem>();
        String bukrs = getCompanyIDByDeptID(customerRefundment.getDept_id());
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
                voucherItem.setSubjectdescribe(Subjectdescribe);
                //货币
                voucherItem.setCurrency(flowPay.getCustomerRefundment().getRefundcurrency());
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
                //统驭项目
                voucherItem.setControlaccount(subject);
                //统驭科目说明
                voucherItem.setCaremark(this.customerRefundItemJdbcDao.getSubjectNameById(subject,bukrs));
                //文本
                voucherItem.setText(flowPay.getCustomerRefundment().getText());
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
                voucherItem.setSubjectdescribe(Subjectdescribe);
                //货币
                voucherItem.setCurrency(flowPay.getCustomerRefundment().getRefundcurrency());
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
                //统驭项目
                voucherItem.setControlaccount(subject);
                //统驭科目说明
                voucherItem.setCaremark(this.customerRefundItemJdbcDao.getSubjectNameById(subject,bukrs));
                //业务范围
                voucherItem.setGsber(flowPay.getDepid2());
                //文本
                voucherItem.setText(flowPay.getCustomerRefundment().getText());
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
                voucherItem.setSubjectdescribe(Subjectdescribe);
                //货币
                voucherItem.setCurrency(flowPay.getCustomerRefundment().getRefundcurrency());
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
                //统驭项目
                voucherItem.setControlaccount(subject);
                //统驭科目说明
                voucherItem.setCaremark(this.customerRefundItemJdbcDao.getSubjectNameById(subject,bukrs));
                //业务范围
                voucherItem.setGsber(flowPay.getDepid3());
                //文本
                voucherItem.setText(flowPay.getCustomerRefundment().getText());
                //反记账标识
                if(flowPay.getAntiaccount3().equals("Y")){
                    voucherItem.setUncheckflag("X");
                }
                
                voucherItemList.add(voucherItem);
            }
        }
        
        return voucherItemList;
    }   
    
    public String getCustomerName(String customer){
        return this.customerJdbcDao.getCustomerName(customer);
    }
    
    public String queryContractNoByRefundmentId(String refundmentId){
        return this.customerJdbcDao.queryContractNoByRefundmentId(refundmentId);
    }

	/* (non-Javadoc)
	 * @see com.infolion.xdss3.customerDrawbackGen.service.CustomerRefundmentServiceGen#_get(java.lang.String)
	 */
	@Override
	public CustomerRefundment _get(String id) {
		// TODO Auto-generated method stub
		return customerRefundItemJdbcDao.getCustomerRefundmentById(id);
	}

	/* (non-Javadoc)
	 * @see com.infolion.xdss3.customerDrawbackGen.service.CustomerRefundmentServiceGen#_getDetached(java.lang.String)
	 */
	@Override
	public CustomerRefundment _getDetached(String id) {
		// TODO Auto-generated method stub
		return customerRefundItemJdbcDao.getCustomerRefundmentById(id);
	}

	/* (non-Javadoc)
	 * @see com.infolion.xdss3.customerDrawbackGen.service.CustomerRefundmentServiceGen#_getEntityCopy(java.lang.String)
	 */
	@Override
	public CustomerRefundment _getEntityCopy(String id) {
		// TODO Auto-generated method stub
		 CustomerRefundment customerRefundment = new CustomerRefundment();
			CustomerRefundment customerRefundmentOld = customerRefundItemJdbcDao.getCustomerRefundmentById(id);
			try
			{
				BeanUtils.copyProperties(customerRefundment, customerRefundmentOld);
			}
			catch (IllegalAccessException e)
			{
				e.printStackTrace();
			}
			catch (InvocationTargetException e)
			{
				e.printStackTrace();
			}
			customerRefundment.setRefundmentno(null); 
			//customerRefundment.setRefundmentid(null); 
			customerRefundment.setProcessstate(" ");
			return customerRefundment;	
	}

	/* (non-Javadoc)
	 * @see com.infolion.xdss3.customerDrawbackGen.service.CustomerRefundmentServiceGen#_getForEdit(java.lang.String)
	 */
	@Override
	public CustomerRefundment _getForEdit(String id) {
		// TODO Auto-generated method stub
		return customerRefundItemJdbcDao.getCustomerRefundmentById(id);
	}
    
}