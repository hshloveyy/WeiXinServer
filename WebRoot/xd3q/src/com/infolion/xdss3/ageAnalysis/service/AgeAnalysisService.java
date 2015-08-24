package com.infolion.xdss3.ageAnalysis.service;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Service;

import com.infolion.platform.basicApp.authManage.UserContextHolder;
import com.infolion.platform.basicApp.authManage.domain.UserContext;
import com.infolion.platform.dpframework.util.StringUtils;
import com.infolion.platform.util.DateUtils;
import com.infolion.sapss.bapi.ConnectManager;
import com.infolion.sapss.bapi.ExtractSapData;
import com.infolion.sapss.contract.domain.TContractPurchaseInfo;
import com.infolion.sapss.contract.domain.TContractSalesInfo;

import com.infolion.xdss3.ageAnalysis.dao.AgeAnalysisJdbcDao;
import com.infolion.xdss3.ageAnalysis.dao.AgeAnalysisHibernateDao;
import com.infolion.xdss3.ageAnalysis.dao.ClearItemJdbcDao;
import com.infolion.xdss3.ageAnalysis.dao.UnclearDetailedJdbcDao;

import com.infolion.xdss3.masterData.dao.BsegHibernateDao;
import com.infolion.xdss3.masterData.dao.BsegJdbcDao;

import com.infolion.xdss3.masterData.dao.CustomerJdbcDao;

import com.infolion.xdss3.masterData.dao.SupplierJdbcDao;
import com.infolion.xdss3.masterData.domain.Bseg;
import com.infolion.xdss3.masterData.domain.SyncMasterLog;
import com.infolion.xdss3.masterData.service.SyncMasterLogService;
import com.infolion.xdss3.payment.dao.PaymentItemJdbcDao;
import com.infolion.xdss3.payment.domain.ImportPaymentItem;
import com.infolion.xdss3.payment.service.PaymentItemService;
import com.infolion.xdss3.voucheritem.dao.VoucherItemJdbcDao;
import com.infolion.xdss3.voucheritem.domain.VoucherItem;

import com.infolion.xdss3.ageAnalysis.domain.AgeAnalysis;
import com.infolion.xdss3.ageAnalysis.domain.AgeExport;
import com.infolion.xdss3.ageAnalysis.domain.AgeInvoice;
import com.infolion.xdss3.ageAnalysis.domain.AgeVoucher;
import com.infolion.xdss3.ageAnalysis.domain.ClearItem;
import com.infolion.xdss3.collectcbill.domain.CollectCbill;
import com.infolion.xdss3.collectitem.dao.CollectItemJdbcDao;
import com.infolion.xdss3.collectitem.domain.CollectItem;
import com.infolion.xdss3.collectitem.service.CollectItemService;
import com.infolion.xdss3.customerDrawback.dao.CustomerRefundItemJdbcDao;
import com.sap.mw.jco.IFunctionTemplate;
import com.sap.mw.jco.JCO;
@Service
public class AgeAnalysisService {
	protected static Log log = LogFactory.getFactory().getLog(AgeAnalysisService.class);
	private static String ZFI032B ="ZFIRPT032C";
	private static String ZFI_SETVOUCHER ="ZFI_SETVOUCHER";
	private static String ZFI_SETINVOICE = "ZFI_SETINVOICE";
	private static String ZFI_SETEXPORT = "ZFI_SETEXPORT";
	@Autowired
    private BsegHibernateDao bsegHibernateDao;
    
    @Autowired
    private BsegJdbcDao bsegJdbcDao;
    
    @Autowired
    private AgeAnalysisHibernateDao ageAnalysisHibernateDao;
    
    @Autowired
    private AgeAnalysisJdbcDao ageAnalysisJdbcDao;
    
    @Autowired
    private  CustomerJdbcDao customerJdbcDao;
    
    @Autowired
    private SupplierJdbcDao supplierJdbcDao;
    
    @Autowired
	private CustomerRefundItemJdbcDao customerRefundItemJdbcDao;
    
    @Autowired
	private ClearItemJdbcDao clearItemJdbcDao;
    @Autowired
	private SyncMasterLogService syncMasterLogService;

    @Autowired
    private CollectItemJdbcDao collectItemJdbcDao;

	public void setCollectItemJdbcDao(CollectItemJdbcDao collectItemJdbcDao) {
		this.collectItemJdbcDao = collectItemJdbcDao;
	}
	
	@Autowired
    private PaymentItemJdbcDao paymentItemJdbcDao;
	
	 @Autowired
	 private UnclearDetailedJdbcDao unclearDetailedJdbcDao;
	 
	 public void setUnclearDetailedJdbcDao(
				UnclearDetailedJdbcDao unclearDetailedJdbcDao) {
			this.unclearDetailedJdbcDao = unclearDetailedJdbcDao;
		}
	 
	public void setPaymentItemJdbcDao(PaymentItemJdbcDao paymentItemJdbcDao) {
		this.paymentItemJdbcDao = paymentItemJdbcDao;
	}
	
	/**
	 * @param syncMasterLogService
	 *            the syncMasterLogService to set
	 */
	public void setSyncMasterLogService(SyncMasterLogService syncMasterLogService)
	{
		this.syncMasterLogService = syncMasterLogService;
	}
    
    
    
    /**
	 * @param clearItemJdbcDao the clearItemJdbcDao to set
	 */
	public void setClearItemJdbcDao(ClearItemJdbcDao clearItemJdbcDao) {
		this.clearItemJdbcDao = clearItemJdbcDao;
	}

	public void setBsegHibernateDao(BsegHibernateDao bsegHibernateDao){
        this.bsegHibernateDao = bsegHibernateDao;
    }

    public void setBsegJdbcDao(BsegJdbcDao bsegJdbcDao){
        this.bsegJdbcDao = bsegJdbcDao;
    }

	/**
	 * @param ageAnalysisHibernateDao the ageAnalysisHibernateDao to set
	 */
	public void setAgeAnalysisHibernateDao(
			AgeAnalysisHibernateDao ageAnalysisHibernateDao) {
		this.ageAnalysisHibernateDao = ageAnalysisHibernateDao;
	}

	/**
	 * @param ageAnalysisDao the ageAnalysisDao to set
	 */
	public void setAgeAnalysisDao(AgeAnalysisJdbcDao ageAnalysisJdbcDao) {
		this.ageAnalysisJdbcDao = ageAnalysisJdbcDao;
	}
    
	
	
	/**
	 * @param customerJdbcDao the customerJdbcDao to set
	 */
	public void setCustomerJdbcDao(CustomerJdbcDao customerJdbcDao) {
		this.customerJdbcDao = customerJdbcDao;
	}

	/**
	 * @param supplierJdbcDao the supplierJdbcDao to set
	 */
	public void setSupplierJdbcDao(SupplierJdbcDao supplierJdbcDao) {
		this.supplierJdbcDao = supplierJdbcDao;
	}

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
	
	/**
	 * 计算金额
	 * @param maps
	 * @param bsegs 
	 * @param customerType 1为客户，2为供应商
	 * @param dateType ,区间值
	 */
	public void  calcAmount(Map<String,AgeAnalysis> maps,List<Bseg> bsegs,String customerType,String dateType,BigDecimal sumOffAmount){
		for(Bseg bseg:bsegs){
			String key="";			
			if("1".equals(customerType)){
				key=bseg.getKunnr()+ "," + bseg.getHkont() + "," +  bseg.getBukrs() + "," +  bseg.getGsber() + ","  + bseg.getVbeltype();
			}
			if("2".equals(customerType)){
				key=bseg.getLifnr()+ "," + bseg.getHkont() + "," +  bseg.getBukrs() + "," +  bseg.getGsber() + ","  + bseg.getBsart();
			}
			AgeAnalysis ageAnalysis = new AgeAnalysis();
			if(null !=maps.get(key)){
				ageAnalysis =maps.get(key);			
			}else{		
				if("1".equals(customerType)){
					ageAnalysis.setCustomerNo(bseg.getKunnr());					
				}
				if("2".equals(customerType)){
					ageAnalysis.setCustomerNo(bseg.getLifnr());
				}
				ageAnalysis.setSubjectCode(bseg.getHkont());
				ageAnalysis.setBukrs(bseg.getBukrs());
				ageAnalysis.setGsber(bseg.getGsber());
				ageAnalysis.setCustomerType(customerType);
				maps.put(key, ageAnalysis);
			}
			//当HKONT以1开头时：
			//SHKZG值为S，=DMBTR;值为H，=-DMBTR.
			//当HKONT以2开头时	：
			//SHKZG值为S，=-DMBTR;值为H，=DMBTR

			BigDecimal amount = new BigDecimal("0");
			if("1".equals(bseg.getHkont().substring(0, 1)) && "S".equals(bseg.getShkzg())){
				amount =bseg.getDmbtr();
			}
			if("1".equals(bseg.getHkont().substring(0, 1)) && "H".equals(bseg.getShkzg())){
				if(bseg.getDmbtr().compareTo(new BigDecimal("0")) !=0){
					amount =new BigDecimal("-" + bseg.getDmbtr());
				}
			}
			if("2".equals(bseg.getHkont().substring(0, 1)) && "S".equals(bseg.getShkzg())){
				if(bseg.getDmbtr().compareTo(new BigDecimal("0")) !=0){
					amount =new BigDecimal("-" + bseg.getDmbtr());
				}
			}
			if("2".equals(bseg.getHkont().substring(0, 1)) && "H".equals(bseg.getShkzg())){
				amount =bseg.getDmbtr();
			}	
			//减去已清金额 
			amount = amount.subtract(sumOffAmount);
			
			if("1_3_d".equals(dateType)){
				BigDecimal amount2 = ageAnalysis.getDate1_3().add(amount);			
				ageAnalysis.setDate1_3(amount2);
			}
			if("4_6_d".equals(dateType)){
				BigDecimal amount2 = ageAnalysis.getDate4_6().add(amount);
				ageAnalysis.setDate4_6(amount2);
			}
			if("7_12_d".equals(dateType)){
				BigDecimal amount2 = ageAnalysis.getDate7_12().add(amount);
				ageAnalysis.setDate7_12(amount2);
			}
			if("1_2_y".equals(dateType)){
				BigDecimal amount2 = ageAnalysis.getYear1_2().add(amount);
				ageAnalysis.setYear1_2(amount2);
			}
			if("2_3_y".equals(dateType)){
				BigDecimal amount2 = ageAnalysis.getYear2_3().add(amount);
				ageAnalysis.setYear2_3(amount2);
			}
			if("3_4_y".equals(dateType)){
				BigDecimal amount2 = ageAnalysis.getYear3_4().add(amount);
				ageAnalysis.setYear3_4(amount2);
			}
			if("4_5_y".equals(dateType)){
				BigDecimal amount2 = ageAnalysis.getYear4_5().add(amount);
				ageAnalysis.setYear4_5(amount2);
			}
			if("5_y".equals(dateType)){
				BigDecimal amount2 = ageAnalysis.getYear5_above().add(amount);
				ageAnalysis.setYear5_above(amount2);
			}

		}
	}
	/**
	 * 
	 * 计算往来账账龄，并更新数据,
	 *
	 * @param date 日期格式为yyyymmdd  必填
     * @param companyno 公司代码
     * @param depid 业务范围   
     * @param customerNo 供应商NO  
     * @param subjectcode 总账科目代码  必填
     *  @param  businessstate 业务类型
	 * @param isExceed2 是否只显示逾期,0为否，1为是
	 * @param customerType 1为客户，2为供应商 必填
	 */
	public void sysAgeAnalysis(String date,String companyno,String depid,String customerNo,String subjectcode,String businessstate,String isExceed2,String customerType){	
		UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
		String username =userContext.getUser().getUserName();
		if("1".equals(customerType)){
			Map<String,AgeAnalysis> maps=new HashMap<String,AgeAnalysis>();
			//客户1到3个月的金额合计
			List<Bseg> bsegs=bsegJdbcDao.getSumAmountByCustomer("90", "0",date,companyno,depid,customerNo,subjectcode,businessstate);	
//			List<Bseg> bsegList=bsegJdbcDao.getBsegByCustomer("90", "0",date,companyno,depid,customerNo,subjectcode,businessstate);	
			BigDecimal sumOffAmount = clearItemJdbcDao.getSumOffAmountByCustomer("90", "0",date,companyno,depid,customerNo,subjectcode,businessstate,username);
			calcAmount(maps,bsegs,"1","1_3_d",sumOffAmount);
			//客户4到7个月的金额合计
			bsegs=bsegJdbcDao.getSumAmountByCustomer("180", "90",date,companyno,depid,customerNo,subjectcode,businessstate);	
//			bsegList=bsegJdbcDao.getBsegByCustomer("180", "90",date,companyno,depid,customerNo,subjectcode,businessstate);	
			sumOffAmount = clearItemJdbcDao.getSumOffAmountByCustomer("180", "90",date,companyno,depid,customerNo,subjectcode,businessstate,username);
			calcAmount(maps,bsegs,"1","4_6_d",sumOffAmount);
	
			//客户7到12个月的金额合计
			bsegs=bsegJdbcDao.getSumAmountByCustomer("365", "180",date,companyno,depid,customerNo,subjectcode,businessstate);	
//			bsegList=bsegJdbcDao.getBsegByCustomer("365", "180",date,companyno,depid,customerNo,subjectcode,businessstate);
			sumOffAmount = clearItemJdbcDao.getSumOffAmountByCustomer("365", "180",date,companyno,depid,customerNo,subjectcode,businessstate,username);
			calcAmount(maps,bsegs,"1","7_12_d",sumOffAmount);
	
			//客户1到2年的金额合计
			bsegs=bsegJdbcDao.getSumAmountByCustomer("730", "365",date,companyno,depid,customerNo,subjectcode,businessstate);		
//			bsegList=bsegJdbcDao.getBsegByCustomer("730", "365",date,companyno,depid,customerNo,subjectcode,businessstate);
			sumOffAmount = clearItemJdbcDao.getSumOffAmountByCustomer("730", "365",date,companyno,depid,customerNo,subjectcode,businessstate,username);
			calcAmount(maps,bsegs,"1","1_2_y",sumOffAmount);
	
			//客户2到3年的金额合计
			bsegs=bsegJdbcDao.getSumAmountByCustomer("1095", "730",date,companyno,depid,customerNo,subjectcode,businessstate);	
//			bsegList=bsegJdbcDao.getBsegByCustomer("1095", "730",date,companyno,depid,customerNo,subjectcode,businessstate);	
			sumOffAmount = clearItemJdbcDao.getSumOffAmountByCustomer("1095", "730",date,companyno,depid,customerNo,subjectcode,businessstate,username);
			calcAmount(maps,bsegs,"1","2_3_y",sumOffAmount);
	
			//客户3到4年的金额合计
			bsegs=bsegJdbcDao.getSumAmountByCustomer("1460", "1095",date,companyno,depid,customerNo,subjectcode,businessstate);	
//			bsegList=bsegJdbcDao.getBsegByCustomer("1460", "1095",date,companyno,depid,customerNo,subjectcode,businessstate);	
			sumOffAmount = clearItemJdbcDao.getSumOffAmountByCustomer("1460", "1095",date,companyno,depid,customerNo,subjectcode,businessstate,username);
			calcAmount(maps,bsegs,"1","3_4_y",sumOffAmount);
	
			//客户4到5年的金额合计
			bsegs=bsegJdbcDao.getSumAmountByCustomer("1825", "1460",date,companyno,depid,customerNo,subjectcode,businessstate);	
//			bsegList=bsegJdbcDao.getBsegByCustomer("1825", "1460",date,companyno,depid,customerNo,subjectcode,businessstate);	
			sumOffAmount = clearItemJdbcDao.getSumOffAmountByCustomer("1825", "1460",date,companyno,depid,customerNo,subjectcode,businessstate,username);
			calcAmount(maps,bsegs,"1","4_5_y",sumOffAmount);
	
			//客户5年以上的金额合计
			bsegs=bsegJdbcDao.getSumAmountByCustomer("9999", "1825",date,companyno,depid,customerNo,subjectcode,businessstate);	
//			bsegList=bsegJdbcDao.getBsegByCustomer("9999", "1825",date,companyno,depid,customerNo,subjectcode,businessstate);	
			sumOffAmount = clearItemJdbcDao.getSumOffAmountByCustomer("9999", "1825",date,companyno,depid,customerNo,subjectcode,businessstate,username);
			calcAmount(maps,bsegs,"1","5_y",sumOffAmount);
	
			int i=0;
			for(String key2:maps.keySet()){
				AgeAnalysis ageAnalysis=maps.get(key2);
				String isExceed = bsegJdbcDao.isExceedByCustomer(ageAnalysis.getCustomerNo(), ageAnalysis.getSubjectCode(),date);
				ageAnalysis.setIsExceed(isExceed);
				if(!StringUtils.isNullBlank(isExceed2)){
					if(!isExceed.equals(isExceed2))continue;
				}
				String kunnr=ageAnalysis.getCustomerNo();
				if(null !=kunnr){
					if(kunnr.length()==8)kunnr="00" + kunnr;
					String customerName =customerJdbcDao.getCustomerName(kunnr);				
					ageAnalysis.setCustomerName(customerName);
				}
				String subjectName =customerRefundItemJdbcDao.getSubjectNameById(ageAnalysis.getSubjectCode(),"2100");
				ageAnalysis.setSubjectName(subjectName);			
				ageAnalysis.setAugdt(date);
				ageAnalysis.setVbeltype(businessstate);
				
				ageAnalysisHibernateDao.save(ageAnalysis);
				if(i==500){
					ageAnalysisHibernateDao.flush();
					ageAnalysisHibernateDao.getSessionFactory().getCurrentSession().getTransaction().commit();
					i=0;
				}
				i++;
			}
		}
		if("2".equals(customerType)){
			Map<String,AgeAnalysis> maps2=new HashMap<String,AgeAnalysis>();
			//供应商1到3个月的金额合计
			List<Bseg> bsegs=bsegJdbcDao.getSumAmountBySupplier("90", "0",date,companyno,depid,customerNo,subjectcode,businessstate);	
//			List<Bseg> bsegList=bsegJdbcDao.getBsegBySupplier("90", "0",date,companyno,depid,customerNo,subjectcode,businessstate);
//			BigDecimal sumOffAmount = getSumOffAmount(bsegList);
			BigDecimal sumOffAmount = clearItemJdbcDao.getSumOffAmountBySupplier("90", "0",date,companyno,depid,customerNo,subjectcode,businessstate,username);
			calcAmount(maps2,bsegs,"2","1_3_d",sumOffAmount);
	
			//供应商4到6个月的金额合计
			bsegs=bsegJdbcDao.getSumAmountBySupplier("180", "90",date,companyno,depid,customerNo,subjectcode,businessstate);	
//			bsegList=bsegJdbcDao.getBsegBySupplier("180", "90",date,companyno,depid,customerNo,subjectcode,businessstate);	
//			sumOffAmount = getSumOffAmount(bsegList);
			sumOffAmount = clearItemJdbcDao.getSumOffAmountBySupplier("180", "90",date,companyno,depid,customerNo,subjectcode,businessstate,username);
			calcAmount(maps2,bsegs,"2","4_6_d",sumOffAmount);
	
			//供应商7到12个月的金额合计
			bsegs=bsegJdbcDao.getSumAmountBySupplier("365", "180",date,companyno,depid,customerNo,subjectcode,businessstate);	
//			bsegList=bsegJdbcDao.getBsegBySupplier("365", "180",date,companyno,depid,customerNo,subjectcode,businessstate);	
//			sumOffAmount = getSumOffAmount(bsegList);
			sumOffAmount = clearItemJdbcDao.getSumOffAmountBySupplier("365", "180",date,companyno,depid,customerNo,subjectcode,businessstate,username);
			calcAmount(maps2,bsegs,"2","7_12_d",sumOffAmount);
	
			//供应商1到2年的金额合计
			bsegs=bsegJdbcDao.getSumAmountBySupplier("730", "365",date,companyno,depid,customerNo,subjectcode,businessstate);	
//			bsegList=bsegJdbcDao.getBsegBySupplier("730", "365",date,companyno,depid,customerNo,subjectcode,businessstate);	
//			sumOffAmount = getSumOffAmount(bsegList);
			sumOffAmount = clearItemJdbcDao.getSumOffAmountBySupplier("730", "365",date,companyno,depid,customerNo,subjectcode,businessstate,username);
			calcAmount(maps2,bsegs,"2","1_2_y",sumOffAmount);
	
			//供应商2到3年的金额合计
			bsegs=bsegJdbcDao.getSumAmountBySupplier("1095", "730",date,companyno,depid,customerNo,subjectcode,businessstate);
//			bsegList=bsegJdbcDao.getBsegBySupplier("1095", "730",date,companyno,depid,customerNo,subjectcode,businessstate);
//			sumOffAmount = getSumOffAmount(bsegList);
			sumOffAmount = clearItemJdbcDao.getSumOffAmountBySupplier("1095", "730",date,companyno,depid,customerNo,subjectcode,businessstate,username);
			calcAmount(maps2,bsegs,"2","2_3_y",sumOffAmount);
	
			//供应商3到4年的金额合计
			bsegs=bsegJdbcDao.getSumAmountBySupplier("1460", "1095",date,companyno,depid,customerNo,subjectcode,businessstate);
//			bsegList=bsegJdbcDao.getBsegBySupplier("1460", "1095",date,companyno,depid,customerNo,subjectcode,businessstate);
//			sumOffAmount = getSumOffAmount(bsegList);
			sumOffAmount = clearItemJdbcDao.getSumOffAmountBySupplier("1460", "1095",date,companyno,depid,customerNo,subjectcode,businessstate,username);
			calcAmount(maps2,bsegs,"2","3_4_y",sumOffAmount);
	
			//供应商4到5年的金额合计
			bsegs=bsegJdbcDao.getSumAmountBySupplier("1825", "1460",date,companyno,depid,customerNo,subjectcode,businessstate);	
//			bsegList=bsegJdbcDao.getBsegBySupplier("1825", "1460",date,companyno,depid,customerNo,subjectcode,businessstate);	
//			sumOffAmount = getSumOffAmount(bsegList);
			sumOffAmount = clearItemJdbcDao.getSumOffAmountBySupplier("1825", "1460",date,companyno,depid,customerNo,subjectcode,businessstate,username);
			calcAmount(maps2,bsegs,"2","4_5_y",sumOffAmount);
	
			//供应商5年以上的金额合计
			bsegs=bsegJdbcDao.getSumAmountBySupplier("9999", "1825",date,companyno,depid,customerNo,subjectcode,businessstate);		
//			bsegList=bsegJdbcDao.getBsegBySupplier("9999", "1825",date,companyno,depid,customerNo,subjectcode,businessstate);		
//			sumOffAmount = getSumOffAmount(bsegList);
			sumOffAmount = clearItemJdbcDao.getSumOffAmountBySupplier("9999", "1825",date,companyno,depid,customerNo,subjectcode,businessstate,username);
			calcAmount(maps2,bsegs,"2","5_y",sumOffAmount);
			int i=0;
			for(String key2:maps2.keySet()){
				AgeAnalysis ageAnalysis=maps2.get(key2);
				String isExceed = bsegJdbcDao.isExceedBySupplier(ageAnalysis.getCustomerNo(), ageAnalysis.getSubjectCode(),date);
				ageAnalysis.setIsExceed(isExceed);
				if(!StringUtils.isNullBlank(isExceed2)){
					if(!isExceed.equals(isExceed2))continue;
				}
				String lifnr=ageAnalysis.getCustomerNo();
				if(null !=lifnr){
					if(lifnr.length()==8)lifnr="00" + lifnr;
					String supplierName =supplierJdbcDao.getSupplierName(lifnr);
					ageAnalysis.setCustomerName(supplierName);
				}
				String subjectName =customerRefundItemJdbcDao.getSubjectNameById(ageAnalysis.getSubjectCode(),"2100");
				ageAnalysis.setSubjectName(subjectName);
				ageAnalysis.setAugdt(date);
				ageAnalysis.setVbeltype(businessstate);
				ageAnalysisHibernateDao.save(ageAnalysis);
				if(i==500){
					ageAnalysisHibernateDao.flush();
					
					ageAnalysisHibernateDao.getSessionFactory().getCurrentSession().getTransaction().commit();
					i=0;
				}
				i++;
			}
		}
		
	}	
	
	public BigDecimal getSumOffAmount(List<Bseg> bsegs,String customerType){
		UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
		String username =userContext.getUser().getUserName();
		BigDecimal sumAmount =new BigDecimal("0");
		for(Bseg bseg:bsegs){
			ClearItem clearItem = clearItemJdbcDao.getClearItem(bseg.getBukrs(), bseg.getBelnr(), bseg.getGjahr(), bseg.getBuzei(),username,customerType);
			if(null !=clearItem){
				sumAmount = sumAmount.add(clearItem.getWrbtr());
			}
		}
		return sumAmount;
	}
	/***
	 * 计算当天往来账账龄，并更新数据,
	 */
	public void sysAgeAnalysisByCurrTest(){
		sysAgeAnalysis(DateUtils.getDateFormat(),"1001","","0091000001","1221%","","","1");
		
	}
	
	/**
	 * 删除数据,日期格式为yyyymmdd
	 */
	public void delAllByDate(String date){
		ageAnalysisJdbcDao.delAllByDate(date);
	}
	/**
	 * 删除当天的数据
	 */
	public void delAllByCurrDate(){
		ageAnalysisJdbcDao.delAllByDate(DateUtils.getDateFormat());
	}
	
	public void delAllBycus(String date,String companyno,String depid,String customerNo,String subjectcode,String businessstate,String customerType){
		ageAnalysisJdbcDao.delByCus(date, companyno, depid, customerNo, subjectcode, businessstate, customerType);
	}
	/**
	 * 根据总账科目取得客户类型
	 * @param subjectCode
	 * @return 返回1为客户，2为供应商
	 */
	public String getCustomerTypeBySubject(String subjectCode){
//		if("1122*".equals(subjectCode) || "2203*".equals(subjectCode) || "1221*".equals(subjectCode) ||  "1241010001".equals(subjectCode) ||  "1241020001".equals(subjectCode) ||  "1241030001".equals(subjectCode) ||  "1241040001".equals(subjectCode) ||  "1121000001".equals(subjectCode)){
//			return "1";
//		}
//		else if("2202*".equals(subjectCode) ||  "1123*".equals(subjectCode) ||  "2241*".equals(subjectCode) ||  "1241010002".equals(subjectCode) ||  "2201010001".equals(subjectCode) ){
//			return "2";
//		}
		//
		if("2202%".equals(subjectCode) ||  "1123%".equals(subjectCode) ||  "2241%".equals(subjectCode) ||  "1241010002".equals(subjectCode) ||  "2201010001".equals(subjectCode) ){
			return "2";
		}else{
			return "1";
		}
		
	}
	
	/**
	 * 根据总账科目取得SAP需要的字符
	 * @param subjectCode
	 * @return 返回1为客户，2为供应商
	 */
	public String getSaknrBySubject(String subjectCode){
		if("1122%".equals(subjectCode) ){
			return "R1";
		}
		if("2203%".equals(subjectCode) ){
			return "R2";
		}
		if("1221%".equals(subjectCode) ){
			return "R3";
		}
		if("1241010001".equals(subjectCode) ){
			return "R7";
		}
		if("1241020001".equals(subjectCode) ){
			return "R9";
		}
		if("1241030001".equals(subjectCode) ){
			return "R10";
		}
		if("1241040001".equals(subjectCode) ){
			return "R11";
		}
		if("1121000001".equals(subjectCode) ){
			return "R12";
		}			
		if("2202%".equals(subjectCode) ){
			return "R4";
		}
		if("1123%".equals(subjectCode) ){
			return "R5";
		}
		if("2241%".equals(subjectCode) ){
			return "R6";
		}
		if("1241010002".equals(subjectCode) ){
			return "R8";
		}
		if("2201010001".equals(subjectCode) ){
			return "R13";
		}		
		return "R1";
	}
	
	/**
     * 取得未清的客户
     * @param companyno 公司代码
     * @param date 分析日期
     * @param customernos 客户集，多个客户逗号分开如'12222','55666'
     * @param depts 业务范围，多个逗号分开如'12222','55666'
     * @param businessstate 业务类型 ,多个逗号分开如'12222','55666'
     * @return list map中的key 为,customerno 客户no,dept 业务范围,businessstate 业务类型,     
     */
	 public List<Map<String,String>>  getAllKunnr(String companyno,String date,String customernos ,String depts,String businessstates){
    	return bsegJdbcDao.getAllKunnr(companyno, date, customernos, depts, businessstates);
    }
    
    /**
     * 取得未清的供应商
     * @param companyno 公司代码
     * @param date 分析日期
     * @param customernos 客户集，多个客户逗号分开如'12222','55666'
     * @param depts 业务范围，多个逗号分开如'12222','55666'
     * @param businessstate 业务类型 ,多个逗号分开如'12222','55666'
     * @return list map中的key 为,customerno 客户no,dept 业务范围,businessstate 业务类型,
     */
    public List<Map<String,String>> getAllLifnr(String companyno,String date,String customernos ,String depts,String businessstates){
    	 return bsegJdbcDao.getAllLifnr(companyno, date, customernos, depts, businessstates);
    }
    
    /**
     * 取得未清的所有业务范围
     * @param companyno 公司代码
     * @param date 分析日期
     * @return
     */
    public List<String> getDepts(String companyno,String date){
    	return bsegJdbcDao.getDepts(companyno, date);
    }
    
    /**
     *
	 * 执行RFC，取得需要往来账龄分析。ZFIRPT032c 
	 *
     * @param bukrs 公司代码
     * @param gsber 业务范围
     * @param date	分析日期
     * @param saknr 总账科目,
     * (总账科目:
     * R1 : 应收账款
     * R2 : 预收账款
     * R3 : 其他应收款
     * R4 : 应付账款
     * R5 : 预付账款
     * R6 : 其他应付款
     * R7 : 内部往来-内部交易（借）
     * R8 : 内部往来-内部交易（贷）
     * R9 : 内部往来-资金划拨
     * R10 : 内部往来-代收代付
     * R11 : 内部往来-其他
     * R12 : 应收票据
     * R13 : 应付票据
     * )
     * @return
     */
	public List<Map<String, String>> _executeRfcGetMasterData(String bukrs,String gsbers ,String date,String saknr,String vbeltypes,String customers)
	{
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();

		JCO.Table returnDatas = null;

		ConnectManager manager = ConnectManager.manager;
		manager.getClient();
		JCO.Client client = null;
		try
		{
			IFunctionTemplate ftemplate = manager.repository.getFunctionTemplate(ZFI032B);
			if (ftemplate != null)
			{
				JCO.Function function = ftemplate.getFunction();
				client = JCO.getClient(manager.poolName);
				JCO.ParameterList input = function.getImportParameterList();
				input.setValue(bukrs, "P_BUKRS");
				if(!StringUtils.isNullBlank(gsbers)){					
					String gsbers2 = gsbers.replaceAll("'", "");
					JCO.Table  t_gsbers = function.getTableParameterList().getTable("P_GSBERS");
					String gs[] = gsbers2.split(",");
					for(String gsber:gs){
						t_gsbers.appendRow();
						t_gsbers.setValue(gsber, "STR");
					}
				}
				if(!StringUtils.isNullBlank(customers)){					
					String customers2 = customers.replaceAll("'", "");
					JCO.Table  t_customers = function.getTableParameterList().getTable("P_KUNNRS");
					String gs[] = customers2.split(",");
					for(String customer:gs){
						t_customers.appendRow();
						t_customers.setValue(customer, "STR");
					}
				}
				input.setValue(date, "P_DATE");
				input.setValue(saknr, "P_SAKNR");
				if(!StringUtils.isNullBlank(vbeltypes)){
					String vbeltypes2 =vbeltypes.replaceAll("'", "");
					input.setValue(vbeltypes2, "P_VBELTYPES");
				}
				client.execute(function);

				// 新增的数据集合
				returnDatas = function.getTableParameterList().getTable("OT_AGE");

				list = ExtractSapData.getList(returnDatas);

			}
			else
			{
				log.error("----------------取得需要同步是主数据发生错误：目标系统上不存在RFC" + ZFI032B);
//				list = null;
			}
		}
		catch (Exception ex)
		{
			log.error("----------------取得往来账龄分析数据发生错误" + ex);
//			list = null;
		}
		finally
		{
			manager.cleanUp();
		}

		return list;
	}

	/**
	 * 同步主数据。
	 */
	public List<AgeAnalysis> _ayncMasterData(List<Map<String, String>> list,String bukrs,String gsbers,String date,String subjectCode,String vbeltypes) throws Exception
	{

//		log.debug("----------------共取得" + list.size() + "笔汇率数据！");
		int i = 1;
		UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
		String username =userContext.getUser().getUserName();
		String gsber2=gsbers;
		if(!StringUtils.isNullBlank(gsbers)){
			gsbers = gsbers.replaceAll("'", "");	
			gsbers = sortGsber(gsbers);
		}		
		if(!StringUtils.isNullBlank(vbeltypes)){
			vbeltypes = vbeltypes.replaceAll("'", "");		
			vbeltypes = sortGsber(vbeltypes);
		}
//		if (list.size() > 0)
//		{
//			ageAnalysisJdbcDao.delByUsername(username);
//		}
		List<AgeAnalysis> ageAnalysisList =new ArrayList<AgeAnalysis>();
		for (Map map : list)
		{
			AgeAnalysis ageAnalysis = new AgeAnalysis();
			ageAnalysis.setCustomerNo(map.get("KUNNR").toString());
			ageAnalysis.setSubjectCode(map.get("HKONT").toString());
			ageAnalysis.setDate1_3(new BigDecimal(map.get("DMBTR1").toString()) );
			ageAnalysis.setDate4_6(new BigDecimal(map.get("DMBTR2").toString()));
			ageAnalysis.setDate7_12(new BigDecimal(map.get("DMBTR3").toString()));
			ageAnalysis.setYear1_2(new BigDecimal(map.get("DMBTR4").toString()));
			ageAnalysis.setYear2_3(new BigDecimal(map.get("DMBTR5").toString()));
			ageAnalysis.setYear3_4(new BigDecimal(map.get("DMBTR6").toString()));
			ageAnalysis.setYear4_5(new BigDecimal(map.get("DMBTR7").toString()));
			ageAnalysis.setYear5_above(new BigDecimal(map.get("DMBTR8").toString()));
			ageAnalysis.setIsExceed(map.get("ISEXCEED").toString());

			log.debug("第" + i + "笔数据:");
			log.debug("KUNNR:" + map.get("KUNNR"));
			log.debug("HKONT:" + map.get("HKONT"));		
			i = i + 1;
			String subjectName =customerRefundItemJdbcDao.getSubjectNameById(ageAnalysis.getSubjectCode(),bukrs);
			ageAnalysis.setSubjectName(subjectName);
			ageAnalysis.setAugdt(date);
			ageAnalysis.setBukrs(bukrs);
			ageAnalysis.setGsber(gsbers);
			ageAnalysis.setVbeltype(vbeltypes);
			String type = getCustomerTypeBySubject(subjectCode);
			ageAnalysis.setCustomerType(type);
			String customerName ="";
			if("1".equals(type)){
				String kunnr = ageAnalysis.getCustomerNo();
				if(kunnr.length()==8)kunnr="00" + kunnr;
				customerName =customerJdbcDao.getCustomerName(kunnr);	
//				处理部分清数据
				BigDecimal sumOffAmount = clearItemJdbcDao.getSumOffAmountByCustomer("90", "0",date,bukrs,gsber2,ageAnalysis.getCustomerNo(),ageAnalysis.getSubjectCode(),vbeltypes,username);
				BigDecimal sum = ageAnalysis.getDate1_3().subtract(sumOffAmount);
				ageAnalysis.setDate1_3(sum);
				sumOffAmount = clearItemJdbcDao.getSumOffAmountByCustomer("180", "90",date,bukrs,gsber2,ageAnalysis.getCustomerNo(),ageAnalysis.getSubjectCode(),vbeltypes,username);
				sum = ageAnalysis.getDate4_6().subtract(sumOffAmount);
				ageAnalysis.setDate4_6(sum);
				sumOffAmount = clearItemJdbcDao.getSumOffAmountByCustomer("365", "180",date,bukrs,gsber2,ageAnalysis.getCustomerNo(),ageAnalysis.getSubjectCode(),vbeltypes,username);
				sum = ageAnalysis.getDate7_12().subtract(sumOffAmount);
				ageAnalysis.setDate7_12(sum);
				sumOffAmount = clearItemJdbcDao.getSumOffAmountByCustomer("730", "365",date,bukrs,gsber2,ageAnalysis.getCustomerNo(),ageAnalysis.getSubjectCode(),vbeltypes,username);
				sum = ageAnalysis.getYear1_2().subtract(sumOffAmount);
				ageAnalysis.setYear1_2(sum);
				sumOffAmount = clearItemJdbcDao.getSumOffAmountByCustomer("1095", "730",date,bukrs,gsber2,ageAnalysis.getCustomerNo(),ageAnalysis.getSubjectCode(),vbeltypes,username);
				sum = ageAnalysis.getYear2_3().subtract(sumOffAmount);
				ageAnalysis.setYear2_3(sum);
				sumOffAmount = clearItemJdbcDao.getSumOffAmountByCustomer("1460", "1095",date,bukrs,gsber2,ageAnalysis.getCustomerNo(),ageAnalysis.getSubjectCode(),vbeltypes,username);
				sum = ageAnalysis.getYear3_4().subtract(sumOffAmount);
				ageAnalysis.setYear3_4(sum);
				sumOffAmount = clearItemJdbcDao.getSumOffAmountByCustomer("1825", "1460",date,bukrs,gsber2,ageAnalysis.getCustomerNo(),ageAnalysis.getSubjectCode(),vbeltypes,username);
				sum = ageAnalysis.getYear4_5().subtract(sumOffAmount);
				ageAnalysis.setYear4_5(sum);
				sumOffAmount = clearItemJdbcDao.getSumOffAmountByCustomer("9999", "1825",date,bukrs,gsber2,ageAnalysis.getCustomerNo(),ageAnalysis.getSubjectCode(),vbeltypes,username);
				sum = ageAnalysis.getYear5_above().subtract(sumOffAmount);
				ageAnalysis.setYear5_above(sum);
			}else{
				String lifnr = ageAnalysis.getCustomerNo();
				if(lifnr.length()==8)lifnr="00" + lifnr;
				customerName =supplierJdbcDao.getSupplierName(lifnr);
//				处理部分清数据
				BigDecimal sumOffAmount = clearItemJdbcDao.getSumOffAmountBySupplier("90", "0",date,bukrs,gsber2,ageAnalysis.getCustomerNo(),ageAnalysis.getSubjectCode(),vbeltypes,username);
				BigDecimal sum = ageAnalysis.getDate1_3().subtract(sumOffAmount);
				ageAnalysis.setDate1_3(sum);
				sumOffAmount = clearItemJdbcDao.getSumOffAmountBySupplier("180", "90",date,bukrs,gsber2,ageAnalysis.getCustomerNo(),ageAnalysis.getSubjectCode(),vbeltypes,username);
				sum = ageAnalysis.getDate4_6().subtract(sumOffAmount);
				ageAnalysis.setDate4_6(sum);
				sumOffAmount = clearItemJdbcDao.getSumOffAmountBySupplier("365", "180",date,bukrs,gsber2,ageAnalysis.getCustomerNo(),ageAnalysis.getSubjectCode(),vbeltypes,username);
				sum = ageAnalysis.getDate7_12().subtract(sumOffAmount);
				ageAnalysis.setDate7_12(sum);
				sumOffAmount = clearItemJdbcDao.getSumOffAmountBySupplier("730", "365",date,bukrs,gsber2,ageAnalysis.getCustomerNo(),ageAnalysis.getSubjectCode(),vbeltypes,username);
				sum = ageAnalysis.getYear1_2().subtract(sumOffAmount);
				ageAnalysis.setYear1_2(sum);
				sumOffAmount = clearItemJdbcDao.getSumOffAmountBySupplier("1095", "730",date,bukrs,gsber2,ageAnalysis.getCustomerNo(),ageAnalysis.getSubjectCode(),vbeltypes,username);
				sum = ageAnalysis.getYear2_3().subtract(sumOffAmount);
				ageAnalysis.setYear2_3(sum);
				sumOffAmount = clearItemJdbcDao.getSumOffAmountBySupplier("1460", "1095",date,bukrs,gsber2,ageAnalysis.getCustomerNo(),ageAnalysis.getSubjectCode(),vbeltypes,username);
				sum = ageAnalysis.getYear3_4().subtract(sumOffAmount);
				ageAnalysis.setYear3_4(sum);
				sumOffAmount = clearItemJdbcDao.getSumOffAmountBySupplier("1825", "1460",date,bukrs,gsber2,ageAnalysis.getCustomerNo(),ageAnalysis.getSubjectCode(),vbeltypes,username);
				sum = ageAnalysis.getYear4_5().subtract(sumOffAmount);
				ageAnalysis.setYear4_5(sum);
				sumOffAmount = clearItemJdbcDao.getSumOffAmountBySupplier("9999", "1825",date,bukrs,gsber2,ageAnalysis.getCustomerNo(),ageAnalysis.getSubjectCode(),vbeltypes,username);
				sum = ageAnalysis.getYear5_above().subtract(sumOffAmount);
				ageAnalysis.setYear5_above(sum);
			}
			ageAnalysis.setCustomerName(customerName);
			ageAnalysis.setUsername(username);
			BigDecimal total = ageAnalysis.getDate1_3().add(ageAnalysis.getDate4_6()).add(ageAnalysis.getDate7_12()).add(ageAnalysis.getYear1_2()).add(ageAnalysis.getYear2_3()).add(ageAnalysis.getYear3_4()).add(ageAnalysis.getYear4_5()).add(ageAnalysis.getYear5_above());
			
			ageAnalysis.setTotal(total);
			
			ageAnalysisList.add(ageAnalysis);
//			ageAnalysisHibernateDao.save(ageAnalysis);
		}
		return ageAnalysisList;
	}
	
	public String sortGsber(String gsbers){
		String[] gsbers2 = StringUtils.split(gsbers,",");
		
		List<String> list = new ArrayList<String>();
		for(String gsber:gsbers2){
			list.add(gsber);
		}
		Collections.sort(list);
		String s2="";
		for(String gsber:list){
			s2 = s2 + gsber + ",";
		}
		s2 = s2.substring(0, s2.length()-1);
		return s2;
	}
	
	static class gsberComparator implements Comparator {  
		  public int compare(Object object1, Object object2) {// 实现接口中的方法
			  String p1 = (String) object1; // 强制转换
			  String p2 = (String) object2; 			
		   return new Double(p1).compareTo( new Double(p2));
		  }
	}
	
	/**
	 * 更新客户外围部分清账的数据
	 * @param username
	 * @param cleardate
	 */
	public void updateClearItemByCustomer(final String username,final String cleardate){
		this.ageAnalysisHibernateDao.getHibernateTemplate().execute(new HibernateCallback() {
			
			public Object doInHibernate(Session session) throws HibernateException,
					SQLException {
				CallableStatement cs = session.connection().prepareCall("{call CUSTOMER_CLEARITEM(?,?)}");    				
				cs.setString(1, username);
				cs.setString(2, cleardate);
				boolean isSuccess = cs.execute();
				return isSuccess; 
			}
		});
	}
	/**
	 * 更新供应商外围部分清账的数据
	 * @param username
	 * @param cleardate
	 */
	public void updateClearItemBySupplier(final String username,final String cleardate){
		this.ageAnalysisHibernateDao.getHibernateTemplate().execute(new HibernateCallback() {
			
			public Object doInHibernate(Session session) throws HibernateException,
					SQLException {
				CallableStatement cs = session.connection().prepareCall("{call SUPPLIER_CLEARITEM(?,?)}");    				
				cs.setString(1, username);
				cs.setString(2, cleardate);
				boolean isSuccess = cs.execute();
				return isSuccess; 
			}
		});
	}
	
	/**
	 * 销售订单类型转成SAP的定义类型
	 * 
	 * @param strContractType 销售订单类型
	 * @param salestype
	 */
	public String convertSalesTypeBySap(String strContractType,String salestype){
		String auart_vtweg ="";
		if (strContractType.equals("1")) { // 外贸合作进口业务
//			tContractSalesInfo.setVbakVtweg("20");
//			tContractSalesInfo.setVbakAuart("Z002");
			auart_vtweg="Z002_20";
		} else if (strContractType.equals("2")) { // 外贸合作出口业务
//			tContractSalesInfo.setVbakVtweg("20");
//			tContractSalesInfo.setVbakAuart("Z001");
			auart_vtweg="Z001_20";
		} else if (strContractType.equals("3")) { // 外贸自营进口业务
//			tContractSalesInfo.setVbakVtweg("10");
//			tContractSalesInfo.setVbakAuart("Z002");
			auart_vtweg="Z002_10";
		} else if (strContractType.equals("4")) { // 外贸自营出口业务
//			tContractSalesInfo.setVbakVtweg("10");
//			tContractSalesInfo.setVbakAuart("Z001");
			auart_vtweg="Z001_10";
		} else if (strContractType.equals("5")) { // 外贸代理出口业务
//			tContractSalesInfo.setVbakVtweg("30");
//			tContractSalesInfo.setVbakAuart("Z001"); 
			auart_vtweg="Z001_30";
		} else if (strContractType.equals("6")) { // 外贸代理进口业务
//			tContractSalesInfo.setVbakVtweg("30");
//			tContractSalesInfo.setVbakAuart("Z002");
			auart_vtweg="Z002_30";
		} else if (strContractType.equals("7")) { // 内贸业务
//			tContractSalesInfo.setVbakVtweg("10");
//			tContractSalesInfo.setVbakAuart("Z003");
			auart_vtweg="Z003_10";
		} else if (strContractType.equals("8")) { // 进料加工业务
//			tContractSalesInfo.setVbakVtweg("10");
			if("E".equals(salestype)){
//			   tContractSalesInfo.setVbakAuart("Z001");//出口订单
				auart_vtweg="Z001_10";
			}
			else if("S".equals(salestype)){
//			   tContractSalesInfo.setVbakAuart("Z003");//内贸订单
				auart_vtweg="Z003_10";
			}
		}else if (strContractType.equals("9")) { // 外贸自营进口敞口业务
//			tContractSalesInfo.setVbakVtweg("10");
//			tContractSalesInfo.setVbakAuart("Z002");
			auart_vtweg="Z002_10";
		}else if (strContractType.equals("10")) { // 内贸敞口业务
//			tContractSalesInfo.setVbakVtweg("10");
//			tContractSalesInfo.setVbakAuart("Z003");
			auart_vtweg="Z003_10";
		}
		else if (strContractType.equals("11")) { // 转口业务
//			tContractSalesInfo.setVbakVtweg("10");
//			tContractSalesInfo.setVbakAuart("Z004");
			auart_vtweg="Z004_10";
		}
		return auart_vtweg;
	} 
	/**
	 * SAP的定义类型 转成 销售订单类型
	 * @param vblentype
	 * @return
	 */
	public String convertSapByContractType(String vblentype){
		String strContractType ="";
		if (vblentype.equals("Z002_20")) { // 外贸合作进口业务
			strContractType= strContractType + "," + "1";
		} else if (vblentype.equals("Z001_20")) { // 外贸合作出口业务
			strContractType= strContractType + ","  + "2";
		} else if (vblentype.equals("Z002_10")) { // 外贸自营进口业务
			strContractType= strContractType + ","  + "3";
		} else if (vblentype.equals("Z001_10")) { // 外贸自营出口业务
			strContractType= strContractType + ","  + "4";
		} else if (vblentype.equals("Z001_30")) { // 外贸代理出口业务
			strContractType= strContractType + ","  + "5";
		} else if (vblentype.equals("Z002_30")) { // 外贸代理进口业务
			strContractType= strContractType + ","  + "6";
		} else if (vblentype.equals("Z003_10")) { // 内贸业务
			strContractType= strContractType + ","  + "7";
//		} else if (vblentype.equals("8")) { // 进料加工业务
////			tContractSalesInfo.setVbakVtweg("10");
//			if("E".equals(salestype)){
////			   tContractSalesInfo.setVbakAuart("Z001");//出口订单
//				auart_vtweg="Z001_10";
//			}
//			else if("S".equals(salestype)){
////			   tContractSalesInfo.setVbakAuart("Z003");//内贸订单
//				auart_vtweg="Z003_10";
//			}
		}else if (vblentype.equals("Z001_10")) { // 进料加工业务 出口订单
			strContractType= strContractType + ","  + "8";
		}else if (vblentype.equals("Z003_10")) { // 进料加工业务 内贸订单
			strContractType= strContractType + ","  + "8";
		
		}else if (vblentype.equals("Z002_10")) { // 外贸自营进口敞口业务
			strContractType= strContractType + ","  + "9";
		}else if (vblentype.equals("Z003_10")) { // 内贸敞口业务
			strContractType= strContractType + ","  + "10";
		}
		else if (strContractType.equals("Z004_10")) { // 转口业务
			strContractType= strContractType + ","  + "11";
		}
		return strContractType;
	}
	/**
	 * SAP的定义类型 转成 采购订单类型
	 * @param vbelntype
	 * @return
	 */
	public String convertSapByPurchaserType(String vbelntype){
		String strPurchaserType ="";
		if(vbelntype.equals("Z001")){// 外贸合作进口业务
			strPurchaserType = strPurchaserType + "," + "1";
		}else if(vbelntype.equals("Z002")){// 外贸合作出口业务
			strPurchaserType = strPurchaserType + "," + "2";
		}else if(vbelntype.equals("Z003")){// 外贸自营进口业务
			strPurchaserType = strPurchaserType + "," + "3";
		}else if(vbelntype.equals("Z004")){//外贸自营出口业务
			strPurchaserType = strPurchaserType + "," + "4";
		}else if(vbelntype.equals(" ")){// 外贸代理出口业务
			strPurchaserType = strPurchaserType + "," + "5";
		}else if(vbelntype.equals(" ")){// 外贸代理进口业务
			strPurchaserType = strPurchaserType + "," + "6";
		}else if(vbelntype.equals("Z005")){// 内贸业务
			strPurchaserType = strPurchaserType + "," + "7";
		}else if(vbelntype.equals("Z006")){// 进料加工业务(P:成品采购 I:原材料采购/进口 PI:原材料采购/国内)
			strPurchaserType = strPurchaserType + "," + "8";
		}else if(vbelntype.equals("Z003")){
			strPurchaserType = strPurchaserType + "," + "8";
		}else if(vbelntype.equals("Z002")){
			strPurchaserType = strPurchaserType + "," + "8";
		}else if(vbelntype.equals("Z003")){// 外贸自营进口敞口业务
			strPurchaserType = strPurchaserType + "," + "9";
		}else if(vbelntype.equals("Z005")){// 内贸敞口业务
			strPurchaserType = strPurchaserType + "," + "10";
		}else if(vbelntype.equals("Z007")){// 转口业务
			strPurchaserType = strPurchaserType + "," + "11";
		}
		return strPurchaserType;
	}
	/**
	 * 采购订单类型转成SAP的定义类型
	 * @param strContractType
	 * @param strPurchaserType
	 * @return
	 */
	public String convertPurchaserTypeBySap(String strContractType,String strPurchaserType){
		// 采购凭证类型
		String strEkkoBsart = "";
		switch (Integer.parseInt(strContractType)) {
		case 1:// 外贸合作进口业务
			strEkkoBsart = "Z001";
			break;
		case 2:// 外贸合作出口业务
			strEkkoBsart = "Z002";
			break;
		case 3:// 外贸自营进口业务
			strEkkoBsart = "Z003";
			break;
		case 4:// 外贸自营出口业务
			strEkkoBsart = "Z004";
			break;
		case 5:// 外贸代理出口业务
			strEkkoBsart = " ";
			break;
		case 6:// 外贸代理进口业务
			strEkkoBsart = " ";
			break;
		case 7:// 内贸业务
			strEkkoBsart = "Z005";
			break;
		case 8:// 进料加工业务(P:成品采购 I:原材料采购/进口 PI:原材料采购/国内)
			if (strPurchaserType.equals("P"))
				strEkkoBsart = "Z006";
			if (strPurchaserType.equals("I"))
				strEkkoBsart = "Z003";
			if(strPurchaserType.equals("PI"))
				strEkkoBsart = "Z002";
			break;
		case 9:// 外贸自营进口敞口业务
			strEkkoBsart = "Z003";
			break;
		case 10:// 内贸敞口业务
			strEkkoBsart = "Z005";
			break;
		case 11:// 转口业务
			strEkkoBsart = "Z007";
			break;
		}
		return strEkkoBsart;
	}
	/**
	 * 同步外围的凭证到SAP（有关账龄要用的信息）
	 * @param ageVouchers
	 */
	public void _executeRfcSetVoucher(List<AgeVoucher> ageVouchers){
		
		if(null == ageVouchers || ageVouchers.size() ==0)return;
		
		ConnectManager manager = ConnectManager.manager;
		manager.getClient();
		JCO.Client client = null;
		JCO.Table t_sv = null;   
		try
		{
			IFunctionTemplate ftemplate = manager.repository.getFunctionTemplate(this.ZFI_SETVOUCHER);
			if (ftemplate != null)
			{
				JCO.Function function = ftemplate.getFunction();
				client = JCO.getClient(manager.poolName);	
				t_sv = function.getTableParameterList().getTable("VOUCHER");
				for(AgeVoucher ageVoucher : ageVouchers){
					t_sv.appendRow();
					t_sv.setValue(ageVoucher.getMandt(), "MANDT");
					t_sv.setValue(ageVoucher.getBelnr(), "BELNR");
					t_sv.setValue(ageVoucher.getGjahr(), "GJAHR");
					t_sv.setValue(ageVoucher.getBukrs(), "BUKRS");
					t_sv.setValue(ageVoucher.getBuzei(), "BUZEI");
					t_sv.setValue(ageVoucher.getGsber(), "GSBER");
					t_sv.setValue(ageVoucher.getBname(), "BNAME");
					t_sv.setValue(ageVoucher.getBstkd(), "BSTKD");
					t_sv.setValue(ageVoucher.getIhrez(), "IHREZ");
					t_sv.setValue(ageVoucher.getMatkl(), "MATKL");
					t_sv.setValue(ageVoucher.getVbeltype(), "VBELTYPE");
					t_sv.setValue(ageVoucher.getTrade_type(), "TRADE_TYPE");
					t_sv.setValue(ageVoucher.getExpires_date(), "EXPIRES_DATE");
					
				}
				client.execute(function);
				 String syncDate = DateUtils.getCurrTimeStr(DateUtils.DB_STORE_DATE);
				SyncMasterLog syncMasterLog = new SyncMasterLog();
				syncMasterLog.setIssucceed("Y");
				syncMasterLog.setSyncdate(syncDate);
				syncMasterLog.setSynctablename("AGEVOUCHER");
				syncMasterLog.setCreatetime(DateUtils.getCurrTimeStr(DateUtils.DB_STORE_DATE));
				syncMasterLog.setErrMessage(" ");
				syncMasterLogService._saveSyncMasterLog(syncMasterLog);
			}
			else
			{
				log.error("---------------：同步外围的凭证到SAP（有关账龄要用的信息）目标系统上不存在RFC" + ZFI_SETVOUCHER);
//				list = null;
			}
		}
		catch (Exception ex)
		{
			log.error("----------------同步外围的凭证到SAP（有关账龄要用的信息）数据发生错误" + ex);
//			list = null;
		}
		finally
		{
			manager.cleanUp();
		}
	}
	/**
	 * 设置有关账龄要用的凭证信息
	 * @return
	 */
	public void setAgeVoucher(){
	    // 同步时间
       
        // 上次同步时间
        String zdate = this.syncMasterLogService._getSyncDate("AGEVOUCHER").trim();
        List<AgeVoucher> ageVouchers = new ArrayList<AgeVoucher>();
//        zdate = "20110510";
        //如果没有上次同步时间就全量同步
        List<VoucherItem> voucherItems =getVoucherItems(zdate);
        ageVouchers = getAgeVoucher(voucherItems);
        
        if(ageVouchers!=null && ageVouchers.size()!=0){
        	_executeRfcSetVoucher(ageVouchers);
	        
        }
//		临时处理数据量大的数据代码
//        zdate = "20101001";
//        int k=0;
//        int d=30;
//        for(int i=0;i<12;i++){
//	        List<VoucherItem> voucherItems =getVoucherItems(zdate,String.valueOf(k),String.valueOf(k+d));
//	        ageVouchers = getAgeVoucher(voucherItems);
//	        
//	        if(ageVouchers!=null && ageVouchers.size()!=0){
//	        	_executeRfcSetVoucher(ageVouchers);	        
//	        }
//	        k+=d;
//        }
	}
	
	public List<VoucherItem> getVoucherItems(String zdate){
		return  ageAnalysisJdbcDao.getVoucherItem(zdate);
	}
	public List<VoucherItem> getVoucherItems(String zdate,String from,String to){
		return  ageAnalysisJdbcDao.getVoucherItem(zdate,from,to);
	}
	/**
	 * 取得有关账龄要用的凭证信息
	 * @return
	 */
	public List<AgeVoucher> getAgeVoucher(List<VoucherItem> voucherItems){
//		List<VoucherItem> voucherItems = ageAnalysisJdbcDao.getVoucherItem(zdate);
		List<AgeVoucher> ageVouchers = new ArrayList<AgeVoucher>();
		for(VoucherItem voucherItem : voucherItems){
			AgeVoucher ageVoucher =new AgeVoucher();
			ageVoucher.setBelnr(voucherItem.getVoucher().getVoucherno());
			ageVoucher.setMandt("800");
			ageVoucher.setBukrs(voucherItem.getVoucher().getCompanycode());
			ageVoucher.setBuzei(voucherItem.getRownumber());
			ageVoucher.setGjahr(voucherItem.getFiyear());
			ageVoucher.setGsber(voucherItem.getGsber());
			String buinessitemid = voucherItemJdbcDao.getBusinessId(voucherItem.getVoucher().getVoucherno(), voucherItem.getVoucher().getCompanycode(), voucherItem.getFiyear(), voucherItem.getRownumber());
			if(!StringUtils.isNullBlank(buinessitemid)){
				CollectItem collectItem = collectItemJdbcDao.getCollectItem(buinessitemid);	
				if(collectItem != null){
					ageVoucher.setBname(collectItem.getProject_no());
					TContractSalesInfo tContractSalesInfo = unclearDetailedJdbcDao.queryContractSalesByNo(collectItem.getContract_no());
					ageVoucher.setBstkd(tContractSalesInfo.getVbkdIhrez());
					ageVoucher.setIhrez(tContractSalesInfo.getContractNo());
					/**
					 * 没有合同取项目的物料组
					 */
					if(!StringUtils.isNullBlank(tContractSalesInfo.getYmatGroup())){
						ageVoucher.setMatkl(tContractSalesInfo.getYmatGroup());
					}else{
						ageVoucher.setMatkl(unclearDetailedJdbcDao.queryYmatgroupByNo2(collectItem.getProject_no()));
					}
					ageVoucher.setExpires_date(collectItem.getCollect().getBillcheckdate());
					ageVoucher.setTrade_type(tContractSalesInfo.getTradeType());
					if(!StringUtils.isNullBlank(tContractSalesInfo.getVbakVtweg())){
						ageVoucher.setVbeltype(tContractSalesInfo.getVbakAuart() + "_" + tContractSalesInfo.getVbakVtweg());
					}else{
						ageVoucher.setVbeltype(tContractSalesInfo.getVbakAuart());
					}
				}else{
					ImportPaymentItem importPaymentitem = paymentItemJdbcDao.getPaymentItem(buinessitemid);
					if(importPaymentitem !=null){
						ageVoucher.setBname(importPaymentitem.getProject_no());
						TContractPurchaseInfo tContractPurchaseInfo = unclearDetailedJdbcDao.queryContractPurchaseByNo(importPaymentitem.getContract_no());
						ageVoucher.setBstkd(tContractPurchaseInfo.getEkkoIhrez());
						ageVoucher.setIhrez(tContractPurchaseInfo.getContractNo());
						
						if(!StringUtils.isNullBlank(tContractPurchaseInfo.getYmatGroup())){
							ageVoucher.setMatkl(tContractPurchaseInfo.getYmatGroup());
						}else{
							ageVoucher.setMatkl(unclearDetailedJdbcDao.queryYmatgroupByNo2(importPaymentitem.getProject_no()));
						}
						ageVoucher.setExpires_date(importPaymentitem.getImportPayment().getMusttaketickleda());
						ageVoucher.setTrade_type(tContractPurchaseInfo.getTradeType());
						ageVoucher.setVbeltype(tContractPurchaseInfo.getEkkoBsart());
						
					}
				}
			}
			if(!ageVouchers.contains(ageVoucher)){
				ageVouchers.add(ageVoucher);
			}
		}
		return ageVouchers;
	}
	/**
	 * 取得有关账龄要用的开票信息
	 * @return
	 */
	public List<AgeInvoice> getAgeInvoice(String zdate){
		List<Map> list =ageAnalysisJdbcDao.getInvoices(zdate);
		List<AgeInvoice> ageInvoices = new ArrayList<AgeInvoice>();
		List<AgeInvoice> ageInvoices2 = new ArrayList<AgeInvoice>();
		for(Map map :list){
			
			String billno = map.get("SAP_BILL_NO").toString();
			
			boolean flag=isNumeric(billno);
			if(!flag)continue;
			String billnos[] = billno.split("\\*");
			if(billnos.length>0){ 			
				for(String billno2:billnos){
					AgeInvoice ageInvoice =new AgeInvoice();
					ageInvoice.setMandt("800");
					if(billno2.length()>10){
						billno2 =  billno2.substring(0, 10);
					}else{
						billno2 = StringUtils.leftPad(billno2, 10, "0");
					}
					ageInvoice.setInvoice(billno2);
					if(null != map.get("VBAK_BNAME")){
						ageInvoice.setBname(map.get("VBAK_BNAME").toString());
					}
					if(null != map.get("PAPER_CONTRACT_NO")){
						ageInvoice.setBstkd(map.get("PAPER_CONTRACT_NO").toString());
					}
					if(null != map.get("CONTRACT_NO")){
						ageInvoice.setIhrez(map.get("CONTRACT_NO").toString());
					}
					if(null != map.get("YMAT_GROUP")){
						ageInvoice.setMatkl(map.get("YMAT_GROUP").toString());
					}
					if(!StringUtils.isNullBlank(map.get("VBAK_VTWEG").toString())){
						ageInvoice.setVbeltype( map.get("VBAK_AUART").toString() + "_" + map.get("VBAK_VTWEG").toString());
					}else{
						ageInvoice.setVbeltype(map.get("VBAK_AUART").toString());
					}
					if(null != map.get("TRADE_TYPE")){
						ageInvoice.setTrade_type(map.get("TRADE_TYPE").toString());
					}
					if(null !=map.get("RECEIPTTIME")){
						ageInvoice.setExpires_date(map.get("RECEIPTTIME").toString());
					}
					if(!ageInvoices.contains(ageInvoice) && !StringUtils.isNullBlank(billno2)){
						ageInvoices.add(ageInvoice);
					}else{
						ageInvoices2.add(ageInvoice);
					}
				}				
			}else{
				AgeInvoice ageInvoice =new AgeInvoice();
				ageInvoice.setMandt("800");
				if(billno.length()>10){
					billno =  billno.substring(0, 10);
				}else{
					billno = StringUtils.leftPad(billno, 10, "0");
				}
				ageInvoice.setInvoice(billno);
				if(null != map.get("VBAK_BNAME")){
					ageInvoice.setBname(map.get("VBAK_BNAME").toString());
				}
				if(null != map.get("PAPER_CONTRACT_NO")){
					ageInvoice.setBstkd(map.get("PAPER_CONTRACT_NO").toString());
				}
				if(null != map.get("CONTRACT_NO")){
					ageInvoice.setIhrez(map.get("CONTRACT_NO").toString());
				}
				if(null != map.get("YMAT_GROUP")){
					ageInvoice.setMatkl(map.get("YMAT_GROUP").toString());
				}
				if(!StringUtils.isNullBlank(map.get("VBAK_VTWEG").toString())){
					ageInvoice.setVbeltype( map.get("VBAK_AUART").toString() + "_" + map.get("VBAK_VTWEG").toString());
				}else{
					ageInvoice.setVbeltype(map.get("VBAK_AUART").toString());
				}
				if(null != map.get("TRADE_TYPE")){
					ageInvoice.setTrade_type(map.get("TRADE_TYPE").toString());
				}
				if(null !=map.get("RECEIPTTIME")){
					ageInvoice.setExpires_date(map.get("RECEIPTTIME").toString());
				}
				if(!ageInvoices.contains(ageInvoice) && !StringUtils.isNullBlank(billno)){
					ageInvoices.add(ageInvoice);
				}else{
					ageInvoices2.add(ageInvoice);
				}
			}
			
		}
		return ageInvoices;
	}
	 public  boolean isNumeric(String str){
		  final String number = "0123456789*";
		  for(int i = 0;i<str.length();i++){  
		            if(number.indexOf(str.charAt(i)) == -1){  
		             return false;  
		            }  
		  }  
		  return true;
	}
	/**
	 * 设置有关账龄要用的开票信息
	 * @return
	 */
	public void setAgeInvoice(){

        // 上次同步时间
        String zdate = this.syncMasterLogService._getSyncDate("AGEINVOICE").trim();
        List<AgeInvoice> ageInvoices = new ArrayList<AgeInvoice>();
//        zdate = "20110110";
        
        //如果没有上次同步时间就全量同步
        ageInvoices = getAgeInvoice(zdate);
        if(ageInvoices !=null && ageInvoices.size()!=0){
	        _executeRfcSetInvoice(ageInvoices);        
	       
        }
	}
	
	/**
	 * 同步外围的开票到SAP（有关账龄要用的信息）
	 * @param ageVouchers
	 */
	public void _executeRfcSetInvoice(List<AgeInvoice> ageInvoices){
		
		if(null == ageInvoices || ageInvoices.size() ==0)return;
		
		ConnectManager manager = ConnectManager.manager;
		manager.getClient();
		JCO.Client client = null;
		JCO.Table t_sv = null;   
		try
		{
			IFunctionTemplate ftemplate = manager.repository.getFunctionTemplate(this.ZFI_SETINVOICE);
			if (ftemplate != null)
			{
				JCO.Function function = ftemplate.getFunction();
				client = JCO.getClient(manager.poolName);	
				t_sv = function.getTableParameterList().getTable("INVOICE");
				for(AgeInvoice ageInvoice : ageInvoices){
					t_sv.appendRow();
					t_sv.setValue(ageInvoice.getMandt(), "MANDT");
					t_sv.setValue(ageInvoice.getInvoice(), "INVOICE");					
					t_sv.setValue(ageInvoice.getGsber(), "GSBER");
					t_sv.setValue(ageInvoice.getBname(), "BNAME");
					t_sv.setValue(ageInvoice.getBstkd(), "BSTKD");
					t_sv.setValue(ageInvoice.getIhrez(), "IHREZ");
					t_sv.setValue(ageInvoice.getMatkl(), "MATKL");
					t_sv.setValue(ageInvoice.getVbeltype(), "VBELTYPE");
					t_sv.setValue(ageInvoice.getTrade_type(), "TRADE_TYPE");
					t_sv.setValue(ageInvoice.getExpires_date(), "EXPIRES_DATE");
					
				}
				client.execute(function);
			    // 同步时间
		        String syncDate = DateUtils.getCurrTimeStr(DateUtils.DB_STORE_DATE);
				 SyncMasterLog syncMasterLog = new SyncMasterLog();
					syncMasterLog.setIssucceed("Y");
					syncMasterLog.setSyncdate(syncDate);
					syncMasterLog.setSynctablename("AGEINVOICE");
					syncMasterLog.setCreatetime(DateUtils.getCurrTimeStr(DateUtils.DB_STORE_DATE));
					syncMasterLog.setErrMessage(" ");
					syncMasterLogService._saveSyncMasterLog(syncMasterLog);
			}
			else
			{
				log.error("---------------：同步外围的开票到SAP（有关账龄要用的信息）目标系统上不存在RFC" + ZFI_SETVOUCHER);
//				list = null;
			}
		}
		catch (Exception ex)
		{
			log.error("----------------同步外围的开票到SAP（有关账龄要用的信息）数据发生错误" + ex);
//			list = null;
		}
		finally
		{
			manager.cleanUp();
		}
	}
	
	/**
	 * 出口外汇
	 * @param zdate
	 * @return
	 */
	public List<AgeExport> getAgeExport(String zdate){
		List<Map> list =ageAnalysisJdbcDao.getExport(zdate);
		List<AgeExport> ageExports = new ArrayList<AgeExport>();
		for(Map map :list){
			AgeExport ageExport =new AgeExport();
			ageExport.setVbeln( map.get("SAP_RETURN_NO").toString());
			ageExport.setExpires_date( map.get("MAKE_INVOICE_TIME").toString());
			ageExports.add(ageExport);
		}
		return ageExports;
	}
	/**
	 * 同步外围的开票到SAP（有关账龄要用的信息）
	 * @param ageVouchers
	 */
	public void _executeRfcSetExport(List<AgeExport> ageExports){
		
		if(null == ageExports || ageExports.size() ==0)return;
		
		ConnectManager manager = ConnectManager.manager;
		manager.getClient();
		JCO.Client client = null;
		JCO.Table t_sv = null;   
		try
		{
			IFunctionTemplate ftemplate = manager.repository.getFunctionTemplate(this.ZFI_SETEXPORT);
			if (ftemplate != null)
			{
				JCO.Function function = ftemplate.getFunction();
				client = JCO.getClient(manager.poolName);	
				t_sv = function.getTableParameterList().getTable("I_EXPORT");
				for(AgeExport ageExport : ageExports){
					t_sv.appendRow();
					t_sv.setValue(ageExport.getMandt(), "MANDT");
					t_sv.setValue(ageExport.getVbeln(), "VBELN");
					t_sv.setValue(ageExport.getExpires_date(), "EXPIRES_DATE");
					
				}
				client.execute(function);
			    // 同步时间
		        String syncDate = DateUtils.getCurrTimeStr(DateUtils.DB_STORE_DATE);
				 SyncMasterLog syncMasterLog = new SyncMasterLog();
					syncMasterLog.setIssucceed("Y");
					syncMasterLog.setSyncdate(syncDate);
					syncMasterLog.setSynctablename("AGEEXPORT");
					syncMasterLog.setCreatetime(DateUtils.getCurrTimeStr(DateUtils.DB_STORE_DATE));
					syncMasterLog.setErrMessage(" ");
					syncMasterLogService._saveSyncMasterLog(syncMasterLog);
			}
			else
			{
				log.error("---------------：同步外围的出口外汇到SAP（有关账龄要用的信息）目标系统上不存在RFC" + ZFI_SETVOUCHER);
//				list = null;
			}
		}
		catch (Exception ex)
		{
			log.error("----------------同步外围的出口外汇到SAP（有关账龄要用的信息）数据发生错误" + ex);
//			list = null;
		}
		finally
		{
			manager.cleanUp();
		}
	}
	
	/**
	 * 设置有关账龄要用的出口外汇信息
	 * @return
	 */
	public void setAgeExport(){

        // 上次同步时间
        String zdate = this.syncMasterLogService._getSyncDate("AGEEXPORT").trim();
        List<AgeExport> AgeExports = new ArrayList<AgeExport>();
//        zdate = "20110110";
        
        //如果没有上次同步时间就全量同步
        AgeExports = getAgeExport(zdate);
        if(AgeExports !=null && AgeExports.size()!=0){
	        _executeRfcSetExport(AgeExports);        
	       
        }
	}
}
