/*
 * @(#)VoucherService.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年06月30日 06点58分32秒
 *  描　述：创建
 */
package com.infolion.xdss3.voucher.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infolion.platform.console.org.dao.SysDeptJdbcDao;
import com.infolion.platform.util.StringUtils;
import com.infolion.sapss.bapi.ConnectManager;
import com.infolion.sapss.bapi.ExtractSapData;
import com.infolion.xdss3.collect.domain.Collect;
import com.infolion.xdss3.collect.service.CollectService;
import com.infolion.xdss3.collectcbill.domain.CollectCbill;
import com.infolion.xdss3.collectitem.dao.CollectItemJdbcDao;
import com.infolion.xdss3.collectitem.domain.CollectItem;
import com.infolion.xdss3.collectitem.service.CollectItemService;
import com.infolion.xdss3.customerDrawback.dao.CustomerRefundmentHibernateDao;
import com.infolion.xdss3.customerDrawback.domain.CustomerRefundItem;
import com.infolion.xdss3.customerDrawback.domain.CustomerRefundment;
import com.infolion.xdss3.customerDrawback.service.CustomerRefundItemService;
import com.infolion.xdss3.masterData.dao.CustomerTitleJdbcDao;
import com.infolion.xdss3.masterData.dao.VendorTitleJdbcDao;
import com.infolion.xdss3.masterData.domain.CertificateNo;
import com.infolion.xdss3.masterData.domain.CustomerTitle;
import com.infolion.xdss3.masterData.domain.VendorTitle;
import com.infolion.xdss3.masterData.service.UnclearCustomerService;
import com.infolion.xdss3.payment.dao.PaymentItemJdbcDao;
import com.infolion.xdss3.payment.domain.ImportPayment;
import com.infolion.xdss3.payment.domain.ImportPaymentCbill;
import com.infolion.xdss3.payment.domain.ImportPaymentItem;
import com.infolion.xdss3.payment.service.ImportPaymentService;
import com.infolion.xdss3.payment.service.PaymentItemService;
import com.infolion.xdss3.usermapping.dao.UserMappingHibernateDao;
import com.infolion.xdss3.voucher.dao.VoucherJdbcDao;
import com.infolion.xdss3.voucher.domain.Voucher;
import com.infolion.xdss3.voucherGen.service.VoucherServiceGen;
import com.infolion.xdss3.voucheritem.dao.VoucherItemJdbcDao;
import com.infolion.xdss3.voucheritem.domain.VoucherItem;
import com.sap.mw.jco.IFunctionTemplate;
import com.sap.mw.jco.JCO;

/**
 * <pre>
 * 凭证预览(Voucher)服务类
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
@SuppressWarnings("static-access")
@Service
public class VoucherService extends VoucherServiceGen
{
	private Log log = LogFactory.getFactory().getLog(this.getClass());
	
	@Autowired
	protected CollectService collectService;

	public void setCollectService(CollectService collectService)
	{
		this.collectService = collectService;
	}
	
	@Autowired
	protected ImportPaymentService importPaymentService;

	public void setImportPaymentService(ImportPaymentService importPaymentService)
	{
		this.importPaymentService = importPaymentService;
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
	private CollectItemJdbcDao collectItemJdbcDao;

	public void setCollectItemJdbcDao(CollectItemJdbcDao collectItemJdbcDao) {
		this.collectItemJdbcDao = collectItemJdbcDao;
	}
	
	@Autowired
	private CustomerTitleJdbcDao customerTitleJdbcDao;
	
	public void setCustomerTitleJdbcDao(CustomerTitleJdbcDao customerTitleJdbcDao) {
		this.customerTitleJdbcDao = customerTitleJdbcDao;
	}
	
	@Autowired
	private VendorTitleJdbcDao vendorTitleJdbcDao;
	
	public void setVendorTitleJdbcDao(VendorTitleJdbcDao vendorTitleJdbcDao) {
		this.vendorTitleJdbcDao = vendorTitleJdbcDao;
	}
	
	@Autowired
	private PaymentItemJdbcDao paymentItemJdbcDao;
	
	@Autowired
	private VoucherItemJdbcDao voucherItemJdbcDao;
	
	public void setVoucherItemJdbcDao(VoucherItemJdbcDao voucherItemJdbcDao) {
		this.voucherItemJdbcDao = voucherItemJdbcDao;
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
    
    @Autowired
    private CustomerRefundItemService customerRefundItemService;

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

	/**
	 * @param sysDeptJdbcDao
	 *            the sysDeptJdbcDao to set
	 */
	public void setSysDeptJdbcDao(SysDeptJdbcDao sysDeptJdbcDao)
	{
		this.sysDeptJdbcDao = sysDeptJdbcDao;
	}
	
	@Autowired
	private UserMappingHibernateDao userMappingHibernateDao;
	
	public void setUserMappingHibernateDao(
			UserMappingHibernateDao userMappingHibernateDao) {
		this.userMappingHibernateDao = userMappingHibernateDao;
	}
	
	@Autowired
    private CustomerRefundmentHibernateDao customerRefundmentHibernateDao;

    public void setCustomerRefundmentHibernateDao(CustomerRefundmentHibernateDao customerRefundmentHibernateDao){
        this.customerRefundmentHibernateDao = customerRefundmentHibernateDao;
    }

    public String getSAPAccount(String username, String depid){
		return this.userMappingHibernateDao.getSAPAccount(username, depid);
	}
	
	public void judgeVoucherNeedDel(String businessId)
	{
		List voucherList = this.voucherJdbcDao.getNewVoucherByBusinessId(businessId);
		
		for(int i=voucherList.size()-1;i>=0;i--){
			Voucher voucher = (Voucher) voucherList.get(i);
			List voucherItemList = this.voucherItemJdbcDao.getVoucherItemById(voucher.getVoucherid());
			
			for(int j=voucherItemList.size()-1;j>=0;j--){
				VoucherItem voucherItem = (VoucherItem) voucherItemList.get(j);
				if(this.voucherItemJdbcDao.isVoucherItemExist(voucherItem)){
					this.voucherJdbcDao.deleteVoucherByVoucherid(voucher.getVoucherid());
					break;
				}
			}
		}
	}
	
	/**
	 * @创建作者：邱杰烜
	 * @创建日期：2011-05-30 
	 * 判断贴现/议付凭证是否已经生成过
	 */
	public void judgeDisNegVoucherNeedDel(String businessId)
	{
		List voucherList = this.voucherJdbcDao.getNewVoucherByBusinessId(businessId);
		
		for(int i=voucherList.size()-1;i>=0;i--){
			Voucher voucher = (Voucher) voucherList.get(i);
			List voucherItemList = this.voucherItemJdbcDao.getVoucherItemById(voucher.getVoucherid());
			
			for(int j=voucherItemList.size()-1;j>=0;j--){
				VoucherItem voucherItem = (VoucherItem) voucherItemList.get(j);
				if(this.voucherItemJdbcDao.isDisNegVoucherItemExist(voucherItem)){
					this.voucherJdbcDao.deleteVoucherByVoucherid(voucher.getVoucherid());
					break;
				}
			}
		}
	}
	
	/**
	 * 判断同一凭证级别是否存在相同数据（区别行项判断）
	 * @param businessId
	 */
	public void judgeVoucherNeedDel_2(String businessId)
 {
        List<Voucher> newVoucherList = this.voucherJdbcDao.getNewVoucherByBusinessId(businessId);
        List<Voucher> oldVoucherList = this.voucherJdbcDao.getHasVoucherNoByBusinessId(businessId);
        if (oldVoucherList.size() < 1) {
            // 未有旧凭证(第一次生成凭证)不需要判断
            return;
        }
        for (Voucher voucher : newVoucherList) {
            List<VoucherItem> newVoucherItemList = this.voucherItemJdbcDao.getVoucherItemById(voucher.getVoucherid());
            boolean canDel = true;
            for (Voucher oldVoucher : oldVoucherList) { //循环旧voucher抬头
                canDel = true;
                List<VoucherItem> oldVoucherItemList = this.voucherItemJdbcDao.getVoucherItemById(oldVoucher.getVoucherid());
                if (newVoucherItemList.size() != oldVoucherItemList.size()) {
                    canDel = false;
                    continue;  // 不同凭证行项数不进行比对
                }
                for (VoucherItem newItem : newVoucherItemList) { // 循环新voucherItem
                    if (!this.voucherItemJdbcDao.isVoucherItemExistInVoucherGroup(newItem, oldVoucher.getVoucherid())) {
                        canDel = false;
                    }
                }
            }
            if (canDel) {
                this.voucherJdbcDao.deleteVoucherByVoucherid(voucher.getVoucherid());
            }
        }
    }
	
	/**
	 * 判断同一凭证级别是否存在相同数据（区别行项判断），如果有相同的凭证返回旧的有生成凭证号的凭证。
	 * @param businessId
	 */
	public Voucher judgeVoucherNeedDel_3(Voucher voucher)
 {
		
            Set<VoucherItem> newVoucherItemList = voucher.getVoucherItem();
            List<Voucher> oldVoucherList = this.voucherJdbcDao.getHasVoucherNoByBusinessId(voucher.getBusinessid());
            if (oldVoucherList.size() < 1) {
                // 未有旧凭证(第一次生成凭证)不需要判断
                return voucher;
            }
            boolean canDel = true;
           
            for (Voucher oldVoucher : oldVoucherList) { //循环旧voucher抬头
                canDel = true;
                List<VoucherItem> oldVoucherItemList = this.voucherItemJdbcDao.getVoucherItemById(oldVoucher.getVoucherid());
                if (newVoucherItemList.size() != oldVoucherItemList.size()) {
                    canDel = false;
                    continue;  // 不同凭证行项数不进行比对
                }
                for (VoucherItem newItem : newVoucherItemList) { // 循环新voucherItem
                    if (!this.voucherItemJdbcDao.isVoucherItemExistInVoucherGroup(newItem, oldVoucher.getVoucherid())) {
                        canDel = false;
                    }
                }
                if(canDel){
                	
                	return this.voucherHibernateDao.get(oldVoucher.getVoucherid());
                }
            }
            return voucher;
          
    }
	
	/**
	 * 处理凭证里的款、票状态
	 * @param voucherList
	 */
	public void dealVoucher(List voucherList)
	{
		for(int i=0;i<voucherList.size();i++){
			Voucher voucher = (Voucher)voucherList.get(i);
			if(!StringUtils.isNullBlank(voucher.getVoucherno())){
				//收款处理
				if(voucher.getBusinesstype().equals("01")){
					Set<VoucherItem> voucherItems = voucher.getVoucherItem();
					for (Iterator<VoucherItem> iter = voucherItems.iterator(); iter.hasNext();)
					{
						VoucherItem voucherItem = (VoucherItem) iter.next();
						if(!StringUtils.isNullBlank(voucherItem.getBusinessitemid())){
							//款
							CollectItem collectItem = this.collectItemJdbcDao.getCollectItem(voucherItem.getBusinessitemid());
							Collect collect = this.collectService._get(collectItem.getCollect().getCollectid());
							
							Set<CollectCbill> collectcbills = collect.getCollectcbill();
							
							for (Iterator<CollectCbill> iter2 = collectcbills.iterator(); iter2.hasNext();)
							{
								CollectCbill collectCbill = (CollectCbill) iter2.next();
								
								if((collectCbill.getUnreceivedamount().subtract(collectCbill.getOnroadamount()))
										.compareTo(collectCbill.getClearamount())==0)
								{
									this.customerTitleJdbcDao.updateCustomerTitleIsClearedByInvoice(collectCbill.getBillno(), "1");
								}
							}
							
							if((collectItem.getPrebillamount().compareTo(BigDecimal.valueOf(0))==0)
									&&(collectItem.getSuretybond().compareTo(BigDecimal.valueOf(0))==0))
							{
								this.collectItemJdbcDao.updateCollectItemIsCleared(collectItem.getCollectitemid(), "1");
							}else{
								this.collectItemJdbcDao.updateCollectItemIsCleared(collectItem.getCollectitemid(), "0");
							}
						}
					}
				}
				// 客户退款处理
				else if(voucher.getBusinesstype().equals("05")){
					Set<VoucherItem> voucherItems = voucher.getVoucherItem();
					for (Iterator<VoucherItem> iter = voucherItems.iterator(); iter.hasNext();)
					{
						VoucherItem voucherItem = (VoucherItem) iter.next();
						if(!StringUtils.isNullBlank(voucherItem.getBusinessitemid())){
						    this.customerRefundItemService.updateRefundItemIsCleared(voucherItem.getBusinessitemid(), com.infolion.xdss3.Constants.cleared.isCleared);
							//款
							CollectItem collectItem = this.collectItemJdbcDao.getCollectItemByRefundment(voucherItem.getBusinessitemid());
                            if (collectItem != null) {
                                //款
                                Collect collect = this.collectService._get(collectItem.getCollect().getCollectid());
                                
                                Set<CollectCbill> collectcbills = collect.getCollectcbill();
                                
                                for (Iterator<CollectCbill> iter2 = collectcbills.iterator(); iter2.hasNext();)
                                {
                                    CollectCbill collectCbill = (CollectCbill) iter2.next();
                                    
                                    if((collectCbill.getUnreceivedamount().subtract(collectCbill.getOnroadamount()))
                                            .compareTo(collectCbill.getClearamount())==0)
                                    {
                                        this.customerTitleJdbcDao.updateCustomerTitleIsClearedByInvoice(collectCbill.getBillno(), "1");
                                    }
                                }
                                
                                if((collectItem.getPrebillamount().compareTo(BigDecimal.valueOf(0))==0)
                                        &&(collectItem.getSuretybond().compareTo(BigDecimal.valueOf(0))==0))
                                {
                                    this.collectItemJdbcDao.updateCollectItemIsCleared(collectItem.getCollectitemid(), "1");
                                }else{
                                    this.collectItemJdbcDao.updateCollectItemIsCleared(collectItem.getCollectitemid(), "0");
                                }
                            } else { // 取不到原有收款，即为添加的退款行项(立项、合同)，多退
                                this.customerRefundItemService.updateRefundItemIsCleared(voucherItem.getBusinessitemid(), com.infolion.xdss3.Constants.cleared.notCleared);
                            }
                        }
					}
				}
				
				//付款处理
				else if(voucher.getBusinesstype().equals("02")){
					Set<VoucherItem> voucherItems = voucher.getVoucherItem();
					for (Iterator<VoucherItem> iter = voucherItems.iterator(); iter.hasNext();)
					{
						VoucherItem voucherItem = (VoucherItem) iter.next();
						if(!StringUtils.isNullBlank(voucherItem.getBusinessitemid())){
							ImportPaymentItem importPaymentItem = this.paymentItemJdbcDao.getPaymentItem(voucherItem.getBusinessitemid());
							ImportPayment importPayment = this.importPaymentService._get(importPaymentItem.getImportPayment().getPaymentid());
							
							Set<ImportPaymentCbill> importpaymentcbills = importPayment.getImportPaymentCbill();
							
							for (Iterator<ImportPaymentCbill> iter2 = importpaymentcbills.iterator(); iter2.hasNext();)
							{
								ImportPaymentCbill importPaymentCbill = (ImportPaymentCbill) iter2.next();
								if((importPaymentCbill.getUnpaidamount().subtract(importPaymentCbill.getOnroadamount()))
										.compareTo(importPaymentCbill.getClearamount2())==0)
								{
									this.vendorTitleJdbcDao.updateVendorTitleIsClearedByInvoice(importPaymentCbill.getBillno(), "1");
								}
							}
							
							if((importPaymentItem.getPrepayamount().compareTo(BigDecimal.valueOf(0))==0)){
								this.paymentItemJdbcDao.updatePaymentItemIsCleared(importPaymentItem.getPaymentitemid(), "1");
							}else{
								this.paymentItemJdbcDao.updatePaymentItemIsCleared(importPaymentItem.getPaymentitemid(), "0");
							}
						}
					}
				} else if(voucher.getBusinesstype().equals("06")){// 供应商退款
					Set<VoucherItem> voucherItems = voucher.getVoucherItem();
					for (Iterator<VoucherItem> iter = voucherItems.iterator(); iter.hasNext();)
					{
						VoucherItem voucherItem = (VoucherItem) iter.next();
						if(!StringUtils.isNullBlank(voucherItem.getBusinessitemid())){
                            this.customerRefundItemService.updateRefundItemIsCleared(voucherItem.getBusinessitemid(), com.infolion.xdss3.Constants.cleared.isCleared);
                            
							ImportPaymentItem importPaymentItem = this.paymentItemJdbcDao.getPaymentItemByRefundment(voucherItem.getBusinessitemid());
							// 修改对于供应商退款，外加合同、立项退款，不进行处理 20110301
							if (importPaymentItem != null) {
								ImportPayment importPayment = this.importPaymentService._get(importPaymentItem.getImportPayment().getPaymentid());
								
								Set<ImportPaymentCbill> importpaymentcbills = importPayment.getImportPaymentCbill();
								
								for (Iterator<ImportPaymentCbill> iter2 = importpaymentcbills.iterator(); iter2.hasNext();)
								{
									ImportPaymentCbill importPaymentCbill = (ImportPaymentCbill) iter2.next();
									if((importPaymentCbill.getUnpaidamount().subtract(importPaymentCbill.getOnroadamount()))
											.compareTo(importPaymentCbill.getClearamount2())==0)
									{
										this.vendorTitleJdbcDao.updateVendorTitleIsClearedByInvoice(importPaymentCbill.getBillno(), "1");
									}
								}
								
								if((importPaymentItem.getPrepayamount().compareTo(BigDecimal.valueOf(0))==0)){
									this.paymentItemJdbcDao.updatePaymentItemIsCleared(importPaymentItem.getPaymentitemid(), "1");
								}else{
									this.paymentItemJdbcDao.updatePaymentItemIsCleared(importPaymentItem.getPaymentitemid(), "0");
								}
							} else {  // 取不到原有付款，即为添加的退款行项(立项、合同)，多退
							    this.customerRefundItemService.updateRefundItemIsCleared(voucherItem.getBusinessitemid(), com.infolion.xdss3.Constants.cleared.notCleared);
							}
						}
					}
				}
			}
		}
	}
	
	/**
	 * 处理清帐凭证里的款、票状态
	 * @param clearVoucherList
	 */
	public void dealClearVoucher(List clearVoucherList)
	{
		for(int i=0;i<clearVoucherList.size();i++){
			Voucher clearVoucher = (Voucher)clearVoucherList.get(i);
			//只有清账凭证才做以下操作zhongzh
			if(!"9".equals(clearVoucher.getVoucherclass()))continue;
			if(!StringUtils.isNullBlank(clearVoucher.getVoucherno())){
				//收款处理
				if(clearVoucher.getBusinesstype().equals("01") || clearVoucher.getBusinesstype().equals("05")){
					Set<VoucherItem> voucherItems = clearVoucher.getVoucherItem();
					for (Iterator<VoucherItem> iter = voucherItems.iterator(); iter.hasNext();)
					{
						VoucherItem voucherItem = (VoucherItem) iter.next();
						if(!StringUtils.isNullBlank(voucherItem.getVoucherno())){
							if(StringUtils.isNullBlank(voucherItem.getBusvoucherid())){
								//票
								CustomerTitle customerTitle = this.customerTitleJdbcDao.getByVoucherNo(clearVoucher.getCompanycode(),voucherItem.getVoucherno(),voucherItem.getFiyear(),voucherItem.getRownumber());
								if(!StringUtils.isNullBlank(customerTitle.getCustomertitleid())){
									this.customerTitleJdbcDao.updateCustomerTitleIsCleared(customerTitle.getCustomertitleid(), "2");
								}
							}else{
								//款
								List<VoucherItem> voucherItemList = this.voucherItemJdbcDao.getVoucherItemListById(voucherItem.getBusvoucherid());
								
								for(int j=0;j<voucherItemList.size();j++){
									VoucherItem _voucherItem = (VoucherItem)voucherItemList.get(j);
									this.collectItemJdbcDao.updateCollectItemIsCleared(_voucherItem.getBusinessitemid(), "2");
								}
							}
						}
					}
				}//付款处理
				else if(clearVoucher.getBusinesstype().equals("02") || clearVoucher.getBusinesstype().equals("06")){
					Set<VoucherItem> voucherItems = clearVoucher.getVoucherItem();
					for (Iterator<VoucherItem> iter = voucherItems.iterator(); iter.hasNext();)
					{
						VoucherItem voucherItem = (VoucherItem) iter.next();
						if(!StringUtils.isNullBlank(voucherItem.getVoucherno())){
							if(StringUtils.isNullBlank(voucherItem.getBusvoucherid())){
								//票
								VendorTitle vendorTitle = this.vendorTitleJdbcDao.getByVoucherNo(clearVoucher.getCompanycode(),voucherItem.getVoucherno(),voucherItem.getFiyear(),voucherItem.getRownumber());
								if(!StringUtils.isNullBlank(vendorTitle.getVendortitleid())){
									this.vendorTitleJdbcDao.updateVendorTitleIsCleared(vendorTitle.getVendortitleid(), "2");
								}
							}else{
								//款
								List<VoucherItem> voucherItemList = this.voucherItemJdbcDao.getVoucherItemListById(voucherItem.getBusvoucherid());
								
								for(int j=0;j<voucherItemList.size();j++){
									VoucherItem _voucherItem = (VoucherItem)voucherItemList.get(j);
									this.paymentItemJdbcDao.updatePaymentItemIsCleared(_voucherItem.getBusinessitemid(), "2");
								}
							}
						}
					}
				}
				//客户单清处理
				else if(clearVoucher.getBusinesstype().equals("08")){

				}
				//供应商单清处理
				else if(clearVoucher.getBusinesstype().equals("09")){
					
				}
			}
		}
	}
	
    /**
     * 更新旧的title,和收付款，的 iscleared sap清帐
     * @param subject 客户编号 ，对应voucheritme的科目号
     * @param taxCode 合同号或立项号，对应voucheritem中的taxCode
     */
    public void updateOldTitle(String businessid){

        List<VoucherItem> voucherItems = voucherItemJdbcDao.getVoucherItems(businessid);
        
        for(VoucherItem voucherItem : voucherItems){
            // 公司代码
            String companyCode = voucherItem.getVoucher().getCompanycode();
            // 财务凭证号
            String voucherNo =voucherItem.getVoucherno();
            // 会计年度
            String fiYear =voucherItem.getFiyear();
            
            //1、行项目
            String rowNumber = voucherItem.getRownumber();
            if(rowNumber.length()==2)rowNumber="0" + rowNumber;         
            if(rowNumber.length()==1)rowNumber="00" + rowNumber;
            log.debug("rowNumber:" + rowNumber);
            CustomerTitle customerTitle = customerTitleJdbcDao.getCustomerTitle(companyCode, voucherNo, fiYear, rowNumber);
            if(null != customerTitle){
                this.unclearCustomerService.updateCustomerTitleIsCleared(customerTitle.getCustomertitleid(), com.infolion.xdss3.Constants.cleared.sapIsCleared);               
            }
            String businessItemId = this.voucherItemJdbcDao.getBusinessId(voucherNo, companyCode, fiYear, rowNumber);
            
            //2、收款信息
            CollectItem collectItem = this.collectItemService.get(businessItemId);
            if(null != collectItem){
                this.collectItemService.updateCollectItemIsCleared(collectItem.getCollectitemid(), com.infolion.xdss3.Constants.cleared.sapIsCleared);
            }
            
            //3、付款信息
            ImportPaymentItem paymentItem = this.paymentItemService.get(businessItemId);
            if(null != paymentItem){                
                this.paymentItemService.updatePaymentItemIsCleared(paymentItem.getPaymentitemid(), com.infolion.xdss3.Constants.cleared.sapIsCleared); 
            }
            
            //4、客户、供应商退款信息            
            CustomerRefundItem refundItem = this.customerRefundItemService.get(businessItemId);
            if(null != refundItem){                
                this.customerRefundItemService.updateRefundItemIsCleared(refundItem.getRefundmentitemid(), com.infolion.xdss3.Constants.cleared.sapIsCleared); 
            }
        }
        
    }
    
    /**
     * @创建作者：邱杰烜
     * @创建日期：2010-12-22
     * 更新相应的行项信息
     */
    public void updateRelatedItem(String businessid){
        /*
         * BUSINESSTYPE
         * 收款：01 
         * 付款：02 
         * 票清收款：03 
         * 票清付款：04 
         * 客户退款：05 
         * 供应商退款：06 
         * 未名户收款：07 
         * 客户单清表：08 
         * 供应商单清表：09
         */
        List<Map<String, String>> voucherItems = voucherItemJdbcDao.getRelateVoucherItems(businessid);
        Map<String, String> strItemMap = new HashMap<String, String>(); 
        for(Map<String, String> map : voucherItems){
            if(map.get("BUSINESSTYPE").equals("05")){   // 【客户退款】
                String refundmentId = map.get("BUSINESSID");        // 客户退款ID 
                String refundmentitemid = map.get("BUSINESSITEMID");   //原为: 客户退款对应的收款行项ID，现已改为　退款行项ID，根据退款行项ID反查收款行项ID
                CustomerRefundItem item = this.customerRefundItemService.get(refundmentitemid);
                // hjj 去除多余collectitemid
                if (item != null) {
                    strItemMap.put(item.getCollectitemid(), refundmentId);
                }
            }
        }
        Iterator it = strItemMap.keySet().iterator();
        while (it.hasNext()) {
            String collectitemid = (String) it.next();
            if (strItemMap.get(collectitemid) != null) {
                this.customerRefundItemService.updateActsuretybond(strItemMap.get(collectitemid), collectitemid);
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
    
    
	/**
	 * 根据业务编号删除凭证
	 * 
	 * @param bussinessid
	 */
	public void deleteVoucherByBusinessid(String bussinessid)
	{
		/**
		 * 先删除没有凭证号的
		 */
		String delSql = "delete from YVOUCHERITEM where VOUCHERID in (select VOUCHERID from YVOUCHER where trim(VOUCHERNO) is null and BUSINESSID = '" + bussinessid + "') ";
		this.voucherItemJdbcDao.getJdbcTemplate().execute(delSql);

		delSql = "delete from YVOUCHER where trim(VOUCHERNO) is null and BUSINESSID = '" + bussinessid + "' ";
		this.voucherItemJdbcDao.getJdbcTemplate().execute(delSql);
		
		/**
		 * 删除有凭证号 被冲销的
		 */
		List<CertificateNo> certificateNoList = new ArrayList<CertificateNo>();
		String sql = "select VOUCHERNO voucherno, FIYEAR fiyear, COMPANYCODE bukrs from YVOUCHER where trim(VOUCHERNO) is not null and BUSINESSID = '"
			+ bussinessid + "'" ;
	
		List<Map<String,String>> jdbcRetList = this.voucherItemJdbcDao.getJdbcTemplate().queryForList(sql);
					
		if(jdbcRetList != null)
		{
			for(int i = 0; i < jdbcRetList.size(); i++ )
			{
				CertificateNo certificateNo = new CertificateNo();
				certificateNo.setBelnr(jdbcRetList.get(i).get("voucherno"));
				certificateNo.setBukrs(jdbcRetList.get(i).get("bukrs"));
				certificateNo.setGjahr(jdbcRetList.get(i).get("fiyear"));
				certificateNoList.add(certificateNo);
			}
		}
		
		/**
		 * 有凭证编号
		 */
		if( certificateNoList != null && certificateNoList.size() > 0 )
		{
			/**
			 * 调用RFC判断是否已经人工重置清帐凭证
			 */
			ConnectManager manager = ConnectManager.manager;
			manager.getClient();
			JCO.Client client = null;
			JCO.Table inputTable = null;
			JCO.Table outputTable = null;
			try 
			{
				IFunctionTemplate ftemplate = manager.repository.getFunctionTemplate("ZF_REVERSAL");
				
				if (ftemplate != null) 
				{
					JCO.Function function = ftemplate.getFunction();
					client = JCO.getClient(manager.poolName);
					inputTable = function.getTableParameterList().getTable(
					"T_BELNR");
					inputTable.firstRow();
					for (int i = 0; i < certificateNoList.size(); i++) 
					{
						inputTable.appendRow();
						inputTable.setValue(certificateNoList.get(i)
								.getBelnr(), "BELNR");
						inputTable.setValue(certificateNoList.get(i)
								.getBukrs(), "BUKRS");
						inputTable.setValue(certificateNoList.get(i)
								.getGjahr(), "GJAHR");
					}
					
					client.execute(function);	//执行RFC
					
					outputTable = function.getTableParameterList().getTable(
					"T_BELNR");
					
					List<Map<String, String>> outPutList = ExtractSapData
					.getList(outputTable);
				
					/**
					 * 判断是否全部重置或冲销
					 */
					if(outPutList != null && outPutList.size()>0 )
					{	
						for( int j = 0; j < outPutList.size(); j++ )
						{
							String strVouchNo = outPutList.get(j).get("BELNR");
							String strVoucherState = outPutList.get(j).get("BSTAT");
							String strOffVouchNo = outPutList.get(j).get("STBLG");
							String strOffYear = outPutList.get(j).get("STJAH");
							
							if (!StringUtils.isNullBlank(strOffVouchNo)){
								
								delSql = "delete from YVOUCHERITEM where VOUCHERID in (select VOUCHERID from YVOUCHER where trim(VOUCHERNO) ='"+strVouchNo+"' and BUSINESSID = '" + bussinessid + "') ";
								this.voucherItemJdbcDao.getJdbcTemplate().execute(delSql);

								delSql = "delete from YVOUCHER where trim(VOUCHERNO) ='"+strVouchNo+"' and BUSINESSID = '" + bussinessid + "' ";
								this.voucherItemJdbcDao.getJdbcTemplate().execute(delSql);
							}
						}
					}
				}
			}
			catch(Exception ex) 
			{
				
			}
		}
	}

	public Voucher getVoucher(String businessid, String businesstype, String voucherclass)
	{
		return this.voucherJdbcDao.getVoucher(businessid, businesstype, voucherclass);
	}
	
	/**
	 * 根据businessId删除凭证。
	 * 
	 * @param businessId
	 */
	public void _deleteVoucherByBusinessId(String businessId)
	{
		String hql = "from Voucher where businessid = ?";
		List<Voucher> voucherList = this.voucherHibernateDao.find(hql, businessId);
		if (voucherList != null && voucherList.size() > 0)
		{
			for (int i = 0; i < voucherList.size(); i++)
			{
				this.voucherHibernateDao.remove(voucherList.get(i));
			}
		}
	}

	/**
	 * 根据部门ID获得公司ID
	 */
	public String getCompanyIDByDeptID(String deptID)
	{
		if (StringUtils.isNullBlank(deptID))
		{
			log.error("deptID为空！请联系系统管理员XXX！");
			return "";
		}
		return this.sysDeptJdbcDao.getCompanyCode(deptID);
	}

	/**
	 * 根据业务ID取得凭证对象。
	 * 
	 * @param businessid
	 * @return
	 */
	public Voucher getVoucher(String businessid)
	{
		String hql = "from Voucher where businessid = ?";
		List list = this.voucherHibernateDao.find(hql, new Object[] { businessid });

		if (list != null && list.size() > 0)
		{
			return (Voucher) list.get(0);
		}
		else
		{
			return new Voucher();
		}
	}

	/**
	 * 根据ID取得凭证对象。
	 * 
	 * @param businessid
	 * @return
	 */
	public Voucher getVoucherById(String voucherid)
	{
		return this.voucherJdbcDao.getVoucherById(voucherid);
	}
	
	/**
	 * 根据业务ID,凭证分类，取得凭证对象。
	 * 
	 * @param businessId
	 * @param voucherclass
	 * @return
	 */
	public Voucher getVoucherByBusinessId(String businessId, String voucherclass)
	{
		return this.voucherHibernateDao.getVoucherByBusinessId(businessId, voucherclass);
	}

	/**
	 * 删除凭证
	 * 
	 * @param businessid
	 */
	public void deleteVoucher(String businessid)
	{
		this.voucherHibernateDao.deleteById("YVOUCHER", "businessid", businessid);
	}

	public List getVoucherList(String businessid)
	{
		String hql = "from Voucher where businessid = ?";
		return this.voucherHibernateDao.find(hql, new Object[] { businessid });
	}
	/**
	 * 根据供应商id获得供应商名称
	 * 
	 * @param customerId
	 * @param bukrs
	 * @return
	 */
	public String getSupplierDescByCustomerId(String supplierId, String burks)
	{
		return this.voucherJdbcDao.getSupplierDescByCustomerId(supplierId, burks);
	}

	/**
	 * 是否已经生成凭证。
	 * 
	 * @param businessId
	 * @return
	 */
	public boolean isVoucherGen(String businessId)
	{
		return this.voucherJdbcDao.isVoucherGen(businessId);
	}


	public void setPaymentItemJdbcDao(PaymentItemJdbcDao paymentItemJdbcDao) {
		this.paymentItemJdbcDao = paymentItemJdbcDao;
	}
	
	/**
	 * 更新凭证的业务状态，为冲销，processstate =-1
	 * @param voucherNo
	 * @param companyCode
	 * @param fiYear
	 */
	public void updateVoucherState(String voucherNo, String companyCode, String fiYear)
	{
		this.voucherJdbcDao.updateVoucherState(voucherNo, companyCode, fiYear);
	}
	
	/**
	 * 取创建时间区间的凭证，不取清账凭证,去掉单清账，票清款
	 * @param importdate_to
	 * @param importdate_from
	 * @return
	 */
	public List<Voucher> getHasVoucherNoByImportdate(String importdate_to,String importdate_from){
		return voucherJdbcDao.getHasVoucherNoByImportdate(importdate_to, importdate_from);
	}
	
	/**
	 * 取创建时间区间的凭证,取票据的清账凭证
	 * @param importdate_to
	 * @param importdate_from
	 * @return
	 */
	public List<Voucher> getHasVoucherNoByImportdate2(String importdate_to,String importdate_from){
		return voucherJdbcDao.getHasVoucherNoByImportdate2(importdate_to, importdate_from);
	}
	/**
	 * 新增到sap的凭证日志
	 * @param voucherid
	 * @param bussinessid
	 * @param bussinesstype
	 * @param oldvoucherNo
	 * @param newvoucherNo
	 * @param companyCode
	 * @param fiYear
	 * @param createtime
	 * @param flag
	 * @param agums
	 * @param logtext
	 */
	public void insertSapVoucherLog(String voucherid,String bussinessid,String bussinesstype,String oldvoucherNo,String newvoucherNo, String companyCode, String fiYear,String createtime,String flag,String agums,String logtext,String isgen)
	{
		this.voucherJdbcDao.insertSapVoucherLog(voucherid, bussinessid, bussinesstype, oldvoucherNo, newvoucherNo, companyCode, fiYear, createtime, flag, agums, logtext,isgen);
	}
	
	/**
	 * 判断在日志表中是否有生成过的凭证
	 * @param voucherid
	 * @return
	 */
	public boolean isgen(String voucherid ){
		return this.voucherJdbcDao.isgen(voucherid);
	}
}
