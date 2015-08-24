package com.infolion.xdss3.ageAnalysis.service;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.infolion.xdss3.ageAnalysis.dao.ClearItemJdbcDao;
import com.infolion.xdss3.ageAnalysis.dao.UnclearDetailedHibernateDao;
import com.infolion.xdss3.ageAnalysis.dao.UnclearDetailedJdbcDao;
import com.infolion.xdss3.ageAnalysis.domain.AgeAnalysis;
import com.infolion.xdss3.ageAnalysis.domain.ClearItem;
import com.infolion.xdss3.ageAnalysis.domain.UnclearDetailed;
import com.infolion.xdss3.collectitem.dao.CollectItemJdbcDao;
import com.infolion.xdss3.collectitem.domain.CollectItem;
import com.infolion.xdss3.collectitem.service.CollectItemService;
import com.infolion.xdss3.customerDrawback.dao.CustomerRefundItemJdbcDao;
import com.infolion.xdss3.masterData.dao.BsegHibernateDao;
import com.infolion.xdss3.masterData.dao.BsegJdbcDao;
import com.infolion.xdss3.masterData.dao.CustomerJdbcDao;
import com.infolion.xdss3.masterData.dao.SupplierJdbcDao;
import com.infolion.xdss3.masterData.domain.Bseg;
import com.infolion.xdss3.payment.dao.PaymentItemJdbcDao;
import com.infolion.xdss3.payment.domain.ImportPaymentItem;
import com.infolion.xdss3.payment.service.PaymentItemService;
import com.infolion.xdss3.voucheritem.dao.VoucherItemJdbcDao;
import com.infolion.xdss3.voucheritem.domain.VoucherItem;
import com.sap.mw.jco.IFunctionTemplate;
import com.sap.mw.jco.JCO;
@Service
public class UnclearDetailedService {
	protected static Log log = LogFactory.getFactory().getLog(AgeAnalysisService.class);
	private static String Z_GETUNCLEAR ="Z_GETUNCLEAR";
	@Autowired
    private BsegHibernateDao bsegHibernateDao;
    
    @Autowired
    private BsegJdbcDao bsegJdbcDao;
    
    @Autowired
    private UnclearDetailedHibernateDao unclearDetailedHibernateDao;
    
    @Autowired
    private UnclearDetailedJdbcDao unclearDetailedJdbcDao;
    
    @Autowired
    private  CustomerJdbcDao customerJdbcDao;
    
    @Autowired
    private SupplierJdbcDao supplierJdbcDao;
    
    @Autowired
	private CustomerRefundItemJdbcDao customerRefundItemJdbcDao;
    
    @Autowired
	private ClearItemJdbcDao clearItemJdbcDao;
    
    @Autowired
	private CollectItemService collectItemService;
    
	@Autowired
	private PaymentItemService paymentItemService;
	
	@Autowired
    private AgeAnalysisService ageAnalysisService;

	public void setAgeAnalysisService(AgeAnalysisService ageAnalysisService) {
		this.ageAnalysisService = ageAnalysisService;
	}
	@Autowired
    private VoucherItemJdbcDao voucherItemJdbcDao;

	public void setVoucherItemJdbcDao(VoucherItemJdbcDao voucherItemJdbcDao) {
		this.voucherItemJdbcDao = voucherItemJdbcDao;
	}
	
	@Autowired
    private CollectItemJdbcDao collectItemJdbcDao;

	public void setCollectItemJdbcDao(CollectItemJdbcDao collectItemJdbcDao) {
		this.collectItemJdbcDao = collectItemJdbcDao;
	}
	
	@Autowired
    private PaymentItemJdbcDao paymentItemJdbcDao;

	public void setPaymentItemJdbcDao(PaymentItemJdbcDao paymentItemJdbcDao) {
		this.paymentItemJdbcDao = paymentItemJdbcDao;
	}
	
    /**
	 * @param collectItemService the collectItemService to set
	 */
	public void setCollectItemService(CollectItemService collectItemService) {
		this.collectItemService = collectItemService;
	}

	/**
	 * @param paymentItemService the paymentItemService to set
	 */
	public void setPaymentItemService(PaymentItemService paymentItemService) {
		this.paymentItemService = paymentItemService;
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
	 * @param unclearDetailedHibernateDao the unclearDetailedHibernateDao to set
	 */
	public void setUnclearDetailedHibernateDao(
			UnclearDetailedHibernateDao unclearDetailedHibernateDao) {
		this.unclearDetailedHibernateDao = unclearDetailedHibernateDao;
	}

	/**
	 * @param unclearDetailedJdbcDao the unclearDetailedJdbcDao to set
	 */
	public void setUnclearDetailedJdbcDao(
			UnclearDetailedJdbcDao unclearDetailedJdbcDao) {
		this.unclearDetailedJdbcDao = unclearDetailedJdbcDao;
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
	
	/**
	 * 删除当天的数据
	 */
	public void delAllByCurrDate(){
		unclearDetailedJdbcDao.delAllByDate(DateUtils.getDateFormat());
	}
	/**
	 * 删除数据,日期格式为yyyymmdd
	 */
	public void delAllByate(String date){
		unclearDetailedJdbcDao.delAllByDate(date);
	}
	public void delByCus(String date,String companyno,String depid,String customerNo,String subjectcode){
		unclearDetailedJdbcDao.delByCus(date, companyno, depid, customerNo, subjectcode);
	}
	/**
	 * 计算，金额在哪一个月份上
	 * @param unclearDetailed
	 * @param date
	 * @param bseg
	 */
	public void  calcAmount(UnclearDetailed unclearDetailed,String date,Bseg bseg){
		int d=-1;
		if(!"0000-00-00".equals(bseg.getZfbdt())){						
			 d = DateUtils.getIntervalDays(date, bseg.getZfbdt());	
		}else{	
			 d = DateUtils.getIntervalDays(date, bseg.getBudat());	
		}		
		
		if(d>=0 && d<90){
			unclearDetailed.setWbje1_3_date(bseg.getWrbtr());
			unclearDetailed.setBwbje1_3_date(bseg.getDmbtr());
		}
		if(d>=90 && d<180){
			unclearDetailed.setWbje4_6_date(bseg.getWrbtr());
			unclearDetailed.setBwbje4_6_date(bseg.getDmbtr());
		}
		if(d>=180 && d<365){
			unclearDetailed.setWbje7_12_date(bseg.getWrbtr());
			unclearDetailed.setBwbje7_12_date(bseg.getDmbtr());
		}
		if(d>=365 && d<730){
			unclearDetailed.setWbje1_2_year(bseg.getWrbtr());
			unclearDetailed.setBwbje1_2_year(bseg.getDmbtr());
		}
		if(d>=730 && d<1095){
			unclearDetailed.setWbje2_3_year(bseg.getWrbtr());
			unclearDetailed.setBwbje3_4_year(bseg.getDmbtr());
		}
		if(d>=1095 && d<1460){
			unclearDetailed.setWbje3_4_year(bseg.getWrbtr());
			unclearDetailed.setBwbje3_4_year(bseg.getDmbtr());
		}
		if(d>=1460 && d<1825){
			unclearDetailed.setWbje4_5_year(bseg.getWrbtr());
			unclearDetailed.setBwbje4_5_year(bseg.getDmbtr());
		}
		if(d>=1825){
			unclearDetailed.setWbje5_year_above(bseg.getWrbtr());
			unclearDetailed.setBwbje5_year_above(bseg.getDmbtr());
		}
	}
	/**
	 * 
	 * 取得未清明细，并更新数据,
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
	public void sysUnclear(String date,String companyno,String depid,String customerNo,String subjectcode,String businessstate,String isExceed2,String customerType){
		UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
		String username =userContext.getUser().getUserName();
		int i=0;
		if("1".equals(customerType)){			
			//客户的数据
			List<Bseg> bsegs=bsegJdbcDao.getUnClearByCustomer(date,companyno,depid,customerNo,subjectcode,businessstate);
			for(Bseg bseg:bsegs){
				UnclearDetailed unclearDetailed =new UnclearDetailed();
				//行项目
				String rowNumber = bseg.getBuzei();
				if(rowNumber.length()==2)rowNumber="0" + rowNumber;			
				if(rowNumber.length()==1)rowNumber="00" + rowNumber;
				ClearItem clearItem = clearItemJdbcDao.getClearItem(bseg.getBukrs(), bseg.getBelnr(), bseg.getGjahr(), bseg.getBuzei(),username,customerType);
				if(null !=clearItem){
					unclearDetailed.setIsclear("1");
					unclearDetailed.setOffAmount(clearItem.getWrbtr());
					unclearDetailed.setOffAmountBwbje(clearItem.getDmbtr());
				}else{
					unclearDetailed.setIsclear("0");
					unclearDetailed.setOffAmount(new BigDecimal("0"));
					unclearDetailed.setOffAmountBwbje(new BigDecimal("0"));
				}
				unclearDetailed.setDeptId(bseg.getGsber());
				
				unclearDetailed.setCustomerNo(bseg.getKunnr());
				unclearDetailed.setProjectNo(bseg.getKunnr());				
				unclearDetailed.setProjectName(customerJdbcDao.getCustomerName(bseg.getKunnr()));
				String subjectName =customerRefundItemJdbcDao.getSubjectNameById(bseg.getHkont(),"2100");
				unclearDetailed.setSubjectCode(bseg.getHkont());
				unclearDetailed.setSubjectName(subjectName);
				unclearDetailed.setAnalysisDate(date);
				unclearDetailed.setAccountDate(bseg.getBudat());
				unclearDetailed.setVoucherNo(bseg.getBelnr());
				unclearDetailed.setBktxt(bseg.getSgtxt());
				unclearDetailed.setVoucher_amount(bseg.getWrbtr());
				unclearDetailed.setVoucher_bwbje(bseg.getDmbtr());			
				unclearDetailed.setProjectNo2(bseg.getBname());
				if(!StringUtils.isNullBlank(bseg.getBname())){
					unclearDetailed.setProjectName2(unclearDetailedJdbcDao.queryProjectNameByNo(bseg.getBname()));
				}
				if(!StringUtils.isNullBlank(bseg.getIhrez())){
					String old_contract_no =collectItemService.getOldContractNo(bseg.getIhrez());				
					unclearDetailed.setOld_contract_no(old_contract_no);
				}
				unclearDetailed.setOrderNo(bseg.getVbel2());
				unclearDetailed.setBusinessstate(bseg.getVbeltype());
				if(!StringUtils.isNullBlank(bseg.getBname())){
					unclearDetailed.setContent(unclearDetailedJdbcDao.queryYmatgroupByNo(bseg.getBname()));
				}
				unclearDetailed.setVoucher_currency(bseg.getPswsl());
				unclearDetailed.setCompanyno(bseg.getBukrs());
				calcAmount(unclearDetailed,date,bseg);		
				
				if(!"0000-00-00".equals(bseg.getZfbdt())){
					unclearDetailed.setExpires_date(bseg.getZfbdt());					
					int d = DateUtils.getIntervalDays(date, bseg.getZfbdt());
					unclearDetailed.setExceed_time(String.valueOf(d));
					if(d>=0){
						unclearDetailed.setIsExceed("0");
					}else{
						unclearDetailed.setIsExceed("1");
					}
				}else{
					unclearDetailed.setExpires_date(bseg.getBudat());					
					int d = DateUtils.getIntervalDays(date, bseg.getBudat());
					unclearDetailed.setExceed_time(String.valueOf(d));
					if(d>=0){
						unclearDetailed.setIsExceed("0");
					}else{
						unclearDetailed.setIsExceed("1");
					}
				}
				String isExceed =  unclearDetailed.getIsExceed();
				if(!StringUtils.isNullBlank(isExceed2)){
					if(!isExceed.equals(isExceed2))continue;
				}
				unclearDetailedHibernateDao.save(unclearDetailed);
				if(i==500){
					unclearDetailedHibernateDao.flush();
					
//					unclearDetailedHibernateDao.getSessionFactory().getCurrentSession().getTransaction().commit();
					i=0;
				}
				i++;
			}
		}
		if("2".equals(customerType)){			
			//供应商的数据
			List<Bseg> bsegs=bsegJdbcDao.getUnClearBySupplier(date,companyno,depid,customerNo,subjectcode,businessstate);
			for(Bseg bseg:bsegs){
				UnclearDetailed unclearDetailed =new UnclearDetailed();
				//行项目
				String rowNumber = bseg.getBuzei();
				if(rowNumber.length()==2)rowNumber="0" + rowNumber;			
				if(rowNumber.length()==1)rowNumber="00" + rowNumber;
				ClearItem clearItem = clearItemJdbcDao.getClearItem(bseg.getBukrs(), bseg.getBelnr(), bseg.getGjahr(), bseg.getBuzei(),username,customerType);
				if(null !=clearItem){
					unclearDetailed.setIsclear("1");
					unclearDetailed.setOffAmount(clearItem.getWrbtr());
					unclearDetailed.setOffAmountBwbje(clearItem.getDmbtr());
				}else{
					unclearDetailed.setIsclear("0");
					unclearDetailed.setOffAmount(new BigDecimal("0"));
					unclearDetailed.setOffAmountBwbje(new BigDecimal("0"));
				}
				unclearDetailed.setDeptId(bseg.getGsber());
				
				unclearDetailed.setCustomerNo(bseg.getLifnr());
				unclearDetailed.setProjectNo(bseg.getLifnr());
				unclearDetailed.setProjectName(supplierJdbcDao.getSupplierName(bseg.getLifnr()));
				String subjectName =customerRefundItemJdbcDao.getSubjectNameById(bseg.getHkont(),"2100");
				unclearDetailed.setSubjectCode(bseg.getHkont());
				unclearDetailed.setSubjectName(subjectName);
				unclearDetailed.setAnalysisDate(date);
				unclearDetailed.setAccountDate(bseg.getBudat());
				unclearDetailed.setVoucherNo(bseg.getBelnr());
				unclearDetailed.setBktxt(bseg.getSgtxt());
				unclearDetailed.setVoucher_amount(bseg.getWrbtr());
				unclearDetailed.setVoucher_bwbje(bseg.getDmbtr());
				unclearDetailed.setCompanyno(bseg.getBukrs());
				unclearDetailed.setProjectNo2(bseg.getBname());
				if(!StringUtils.isNullBlank(bseg.getBname())){
					unclearDetailed.setProjectName2(unclearDetailedJdbcDao.queryProjectNameByNo(bseg.getBname()));
				}
				if(!StringUtils.isNullBlank(bseg.getIhrez())){
					String old_contract_no = paymentItemService.getOldContractNo(bseg.getIhrez());
					unclearDetailed.setOld_contract_no(old_contract_no);
				}
				
				unclearDetailed.setOrderNo(bseg.getEbeln());
				unclearDetailed.setBusinessstate(bseg.getBsart());
				if(!StringUtils.isNullBlank(bseg.getBname())){
					unclearDetailed.setContent(unclearDetailedJdbcDao.queryYmatgroupByNo(bseg.getBname()));
				}
				unclearDetailed.setVoucher_currency(bseg.getPswsl());
				
				calcAmount(unclearDetailed,date,bseg);		
				
				if(!"0000-00-00".equals(bseg.getZfbdt())){
					unclearDetailed.setExpires_date(bseg.getZfbdt());					
					int d = DateUtils.getIntervalDays(date, bseg.getZfbdt());
					unclearDetailed.setExceed_time(String.valueOf(d));
					if(d>=0){
						unclearDetailed.setIsExceed("0");
					}else{
						unclearDetailed.setIsExceed("1");
					}
				}else{
					unclearDetailed.setExpires_date(bseg.getBudat());					
					int d = DateUtils.getIntervalDays(date, bseg.getBudat());
					unclearDetailed.setExceed_time(String.valueOf(d));
					if(d>=0){
						unclearDetailed.setIsExceed("0");
					}else{
						unclearDetailed.setIsExceed("1");
					}
				}
				String isExceed =  unclearDetailed.getIsExceed();
				if(!StringUtils.isNullBlank(isExceed2)){
					if(!isExceed.equals(isExceed2))continue;
				}
				unclearDetailedHibernateDao.save(unclearDetailed);
				if(i==500){
					unclearDetailedHibernateDao.flush();					
//					unclearDetailedHibernateDao.getSessionFactory().getCurrentSession().getTransaction().commit();
					i=0;
				}
				i++;
			}
		}
	}

	public void sysUnclearByCurrTest(){
		sysUnclear("20090101","1001","","0091000001","1221%","","","1");
		
	}
	
	public void updateClearItem(){
		this.unclearDetailedHibernateDao.getHibernateTemplate().execute(new HibernateCallback() {
			
			public Object doInHibernate(Session session) throws HibernateException,
					SQLException {
				CallableStatement cs = session.connection().prepareCall("{call COLLECT_CLEARITEM}");    
//				cs.setString(1, customer);
				boolean isSuccess = cs.execute();
				return isSuccess; 
			}
		});
	}

	
	 /**
    *
	 * 执行RFC，取得需要往来账龄分析。ZFIRPT032c 
	 *
    * @param bukrs 公司代码
    * @param gsber 业务范围
    * @param date	分析日期
    * @param saknr 总账科目,
    *  @param kunnr 客户,
    * (总账科目: R1_1122010002
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
	public List<Map<String, String>> _executeRfcGetMasterData(String bukrs,String gsbers ,String date,String saknr,String vbeltypes,String kunnr)
	{
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();

		JCO.Table returnDatas = null;

		ConnectManager manager = ConnectManager.manager;
		manager.getClient();
		JCO.Client client = null;
		try
		{
			IFunctionTemplate ftemplate = manager.repository.getFunctionTemplate(Z_GETUNCLEAR);
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
				if(!StringUtils.isNullBlank(kunnr)){					
					String customers2 = kunnr.replaceAll("'", "");
					JCO.Table  t_customers = function.getTableParameterList().getTable("P_KUNNRS");
					String gs[] = customers2.split(",");
					for(String customer:gs){
						t_customers.appendRow();
						t_customers.setValue(customer, "STR");
					}
				}
				input.setValue(date, "P_DATE");
				input.setValue(saknr, "P_SAKNR2");
				if(!StringUtils.isNullBlank(vbeltypes)){
					String vbeltypes2 =vbeltypes.replaceAll("'", "");
					input.setValue(vbeltypes2, "P_VBELTYPES");
				}
				
				client.execute(function);

				// 新增的数据集合
				returnDatas = function.getTableParameterList().getTable("UNCLEAR");

				list = ExtractSapData.getList(returnDatas);

			}
			else
			{
				log.error("----------------取得需要同步是主数据发生错误：目标系统上不存在RFC" + Z_GETUNCLEAR);
//				list = null;
			}
		}
		catch (Exception ex)
		{
			log.error("----------------取得往来账未清明细数据发生错误" + ex);
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
	public List<UnclearDetailed> _ayncMasterData(List<Map<String, String>> list,String bukrs,String gsbers,String date,String subjectCode,String vbeltypes,String customerType) throws Exception
	{

//		log.debug("----------------共取得" + list.size() + "笔汇率数据！");
		int i = 1;
		UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
		String username =userContext.getUser().getUserName();
		String gsber2=gsbers;
		if(!StringUtils.isNullBlank(gsbers)){
			gsbers = gsbers.replaceAll("'", "");	
		
		}		
		if(!StringUtils.isNullBlank(vbeltypes)){
			vbeltypes = vbeltypes.replaceAll("'", "");		

		}
//		if (list.size() > 0)
//		{
//			unclearDetailedJdbcDao.delByUsername(username);
//		}
		List<UnclearDetailed> unclearDetailedList = new ArrayList<UnclearDetailed>();
		String subjectName =customerRefundItemJdbcDao.getSubjectNameById(subjectCode,bukrs);
		
		for (Map map : list)
		{
			UnclearDetailed unclearDetailed = new UnclearDetailed();
			unclearDetailed.setCustomerNo(map.get("KUNNR").toString());
			unclearDetailed.setCompanyno(map.get("BUKRS").toString());
			unclearDetailed.setDeptId(map.get("GSBER").toString());
			unclearDetailed.setIsclear(map.get("ISCLEAR").toString());
			unclearDetailed.setProjectNo(map.get("KUNNR").toString());
			String kunnr = map.get("KUNNR").toString();
			if("1".equals(customerType)){
				if(null !=kunnr){
					if(kunnr.length()==8)kunnr="00" + kunnr;
					String customerName =customerJdbcDao.getCustomerName(kunnr);				
					unclearDetailed.setProjectName(customerName);
				}
			}else{
				if(null !=kunnr){
					if(kunnr.length()==8)kunnr="00" + kunnr;
					String supplierName =supplierJdbcDao.getSupplierName(kunnr);
					unclearDetailed.setProjectName(supplierName);
				}
			}
//			unclearDetailed.setProjectName(map.get("KUNNR").toString());
			unclearDetailed.setSubjectCode(map.get("HKONT").toString());
			unclearDetailed.setSubjectName(subjectName);
			unclearDetailed.setAccountDate(map.get("ACCOUNTDATE").toString());
			unclearDetailed.setAnalysisDate(date);
			unclearDetailed.setVoucherNo(map.get("VOUCHERNO").toString());
			unclearDetailed.setVoucher_amount(new BigDecimal(map.get("VOUCHERAMOUNT").toString()) );
			unclearDetailed.setVoucher_bwbje(new BigDecimal(map.get("VOUCHERBWBJE").toString()) );
			unclearDetailed.setBktxt(map.get("BKTXT").toString());
			unclearDetailed.setOffAmount(new BigDecimal(map.get("OFFAMOUNT").toString()) );
			unclearDetailed.setOffAmountBwbje(new BigDecimal(map.get("OFFBWBJE").toString()) );
			unclearDetailed.setProjectNo2(map.get("BNAME").toString());
//			unclearDetailed.setProjectName2(map.get("KUNNR").toString());
			
//			unclearDetailed.setOld_contract_no(map.get("VBELN").toString());
//			unclearDetailed.setOrderNo(map.get("EBELN").toString());
			unclearDetailed.setContent(map.get("WGBEZ").toString());
			unclearDetailed.setVoucher_currency(map.get("CURRENCY").toString());

			
			unclearDetailed.setWbje1_3_date(new BigDecimal(map.get("BWBJE").toString()) );			
			unclearDetailed.setWbje4_6_date(new BigDecimal(map.get("BWBJE2").toString()) );
			unclearDetailed.setWbje7_12_date(new BigDecimal(map.get("BWBJE3").toString()) );
			unclearDetailed.setWbje1_2_year(new BigDecimal(map.get("BWBJE4").toString()) );
			unclearDetailed.setWbje2_3_year(new BigDecimal(map.get("BWBJE5").toString()) );
			unclearDetailed.setWbje3_4_year(new BigDecimal(map.get("BWBJE6").toString()) );
			unclearDetailed.setWbje4_5_year(new BigDecimal(map.get("BWBJE7").toString()) );
			unclearDetailed.setWbje5_year_above(new BigDecimal(map.get("BWBJE8").toString()) );
			
			unclearDetailed.setBwbje1_3_date(new BigDecimal(map.get("WBJE").toString()) );
			unclearDetailed.setBwbje4_6_date(new BigDecimal(map.get("WBJE2").toString()) );
			unclearDetailed.setBwbje7_12_date(new BigDecimal(map.get("WBJE3").toString()) );
			unclearDetailed.setBwbje1_2_year(new BigDecimal(map.get("WBJE4").toString()) );
			unclearDetailed.setBwbje2_3_year(new BigDecimal(map.get("WBJE5").toString()) );
			unclearDetailed.setBwbje3_4_year(new BigDecimal(map.get("WBJE6").toString()) );
			unclearDetailed.setBwbje4_5_year(new BigDecimal(map.get("WBJE7").toString()) );
			unclearDetailed.setBwbje5_year_above(new BigDecimal(map.get("WBJE8").toString()) );
			
			
			
			unclearDetailed.setExpires_date(map.get("EXPIRES_DATE").toString());
			unclearDetailed.setExceed_time(map.get("EXCEED_DATE").toString());
			unclearDetailed.setIsExceed(map.get("ISEXCEED").toString());
			unclearDetailed.setGjahr(map.get("GJAHR").toString());
			unclearDetailed.setBuzei(map.get("BUZEI").toString());
			unclearDetailed.setIhrez(map.get("IHREZ").toString());
			unclearDetailed.setVbeltype(map.get("VBELTYPE").toString());
			unclearDetailed.setBsart(map.get("BSART").toString());
			unclearDetailed.setMatnr(map.get("MATNR").toString());
			unclearDetailed.setUsername(username);
//			unclearDetailed.setBusinessstate(map.get("KUNNR").toString());
			
			if("1".equals(customerType)){
				unclearDetailed.setOld_contract_no(map.get("OLD_CONTRACT_NO").toString());
				unclearDetailed.setBusinessstate(convertSapByContractType(map.get("VBELTYPE").toString()));
				
				//金额为负的是款
				if(unclearDetailed.getVoucher_amount().compareTo(new BigDecimal("0")) ==-1){
					String buinessitemid = voucherItemJdbcDao.getBusinessId(unclearDetailed.getVoucherNo(), unclearDetailed.getCompanyno(), unclearDetailed.getGjahr(), unclearDetailed.getBuzei());
					if(!StringUtils.isNullBlank(buinessitemid)){
						CollectItem collectItem = collectItemJdbcDao.getCollectItem(buinessitemid);	
						if(null != collectItem){
							unclearDetailed.setProjectNo2(collectItem.getProject_no());
							log.debug("合同号：" + collectItem.getContract_no() + "");
							TContractSalesInfo tContractSalesInfo = unclearDetailedJdbcDao.queryContractSalesByNo(collectItem.getContract_no());
							unclearDetailed.setOld_contract_no(tContractSalesInfo.getVbkdIhrez());
							unclearDetailed.setBusinessstate(convertSapByContractType(tContractSalesInfo.getTradeType()));
							String wgbez = unclearDetailedJdbcDao.getMatgroup(tContractSalesInfo.getYmatGroup());
							unclearDetailed.setContent(wgbez);
						}
					}
				}
				//金额为正的是票
				if(unclearDetailed.getVoucher_amount().compareTo(new BigDecimal("0")) ==1){
					String vbeln = map.get("VBELN").toString();
					TContractSalesInfo tContractSalesInfo = unclearDetailedJdbcDao.queryContractSalesBySapNo(vbeln);
					unclearDetailed.setOld_contract_no(tContractSalesInfo.getVbkdIhrez());
				}
			}else{
				unclearDetailed.setOrderNo(map.get("ORDERNO").toString());
				unclearDetailed.setBusinessstate(convertSapByPurchaserType(map.get("BSART").toString()));
				//金额为负的是款
				if(unclearDetailed.getVoucher_amount().compareTo(new BigDecimal("0")) ==-1){
					String buinessitemid = voucherItemJdbcDao.getBusinessId(unclearDetailed.getVoucherNo(), unclearDetailed.getCompanyno(), unclearDetailed.getGjahr(), unclearDetailed.getBuzei());
					if(!StringUtils.isNullBlank(buinessitemid)){
						ImportPaymentItem importPaymentitem = paymentItemJdbcDao.getPaymentItem(buinessitemid);
						if(null  != importPaymentitem){
							unclearDetailed.setProjectNo2(importPaymentitem.getProject_no());
							
							TContractPurchaseInfo tContractPurchaseInfo = unclearDetailedJdbcDao.queryContractPurchaseByNo(importPaymentitem.getContract_no());
							unclearDetailed.setOld_contract_no(tContractPurchaseInfo.getEkkoIhrez());
							unclearDetailed.setBusinessstate(convertSapByPurchaserType(tContractPurchaseInfo.getTradeType()));
							
							String wgbez = unclearDetailedJdbcDao.getMatgroup(tContractPurchaseInfo.getYmatGroup());
							unclearDetailed.setContent(wgbez);
						}
					}
				}
				//金额为正的是票
				if(unclearDetailed.getVoucher_amount().compareTo(new BigDecimal("0")) ==1){
					String ebeln = map.get("EBELN").toString();
					TContractPurchaseInfo tContractPurchaseInfo = unclearDetailedJdbcDao.queryContractPurchaseBySapNo(ebeln);
					unclearDetailed.setOld_contract_no(tContractPurchaseInfo.getEkkoIhrez());
				}
			}
			ClearItem clearItem = clearItemJdbcDao.getClearItem( unclearDetailed.getCompanyno(), unclearDetailed.getVoucherNo(),unclearDetailed.getGjahr(), unclearDetailed.getBuzei(),username,customerType);
			if(null != clearItem){
				unclearDetailed.setIsclear("1");
				
				if(unclearDetailed.getVoucher_amount().compareTo(new BigDecimal("0")) ==1){
					unclearDetailed.setOffAmount(clearItem.getDmbtr());
					unclearDetailed.setOffAmountBwbje(clearItem.getWrbtr());
				}else{
					unclearDetailed.setOffAmount(new BigDecimal("0").subtract(clearItem.getDmbtr()));
					unclearDetailed.setOffAmountBwbje(new BigDecimal("0").subtract(clearItem.getWrbtr()));
				}
//					处理部分清数据
				
				BigDecimal sumOffAmount = clearItemJdbcDao.getSumOffAmountByVoucher("90", "0",date,clearItem.getBudat(),unclearDetailed.getOffAmountBwbje());
				BigDecimal sum = new BigDecimal("0");
				if(sumOffAmount.compareTo(new BigDecimal("0")) !=0){					
						sum = unclearDetailed.getBwbje1_3_date().subtract(sumOffAmount);
						unclearDetailed.setBwbje1_3_date(sum);
						sum = unclearDetailed.getWbje1_3_date().subtract(unclearDetailed.getOffAmount());
						unclearDetailed.setWbje1_3_date(sum);					
				}
				sumOffAmount = clearItemJdbcDao.getSumOffAmountByVoucher("180", "90",date,clearItem.getBudat(),unclearDetailed.getOffAmountBwbje());
				if(sumOffAmount.compareTo(new BigDecimal("0")) !=0){
					sum = unclearDetailed.getBwbje4_6_date().subtract(sumOffAmount);
					unclearDetailed.setBwbje4_6_date(sum);
					sum = unclearDetailed.getWbje4_6_date().subtract(unclearDetailed.getOffAmount());
					unclearDetailed.setWbje4_6_date(sum);
				}
				sumOffAmount = clearItemJdbcDao.getSumOffAmountByVoucher("365", "180",date,clearItem.getBudat(),unclearDetailed.getOffAmountBwbje());
				if(sumOffAmount.compareTo(new BigDecimal("0")) !=0){
					sum = unclearDetailed.getBwbje7_12_date().subtract(sumOffAmount);
					unclearDetailed.setBwbje7_12_date(sum);
					sum = unclearDetailed.getWbje7_12_date().subtract(unclearDetailed.getOffAmount());
					unclearDetailed.setWbje7_12_date(sum);
				}
				sumOffAmount = clearItemJdbcDao.getSumOffAmountByVoucher("730", "365",date,clearItem.getBudat(),unclearDetailed.getOffAmountBwbje());
				if(sumOffAmount.compareTo(new BigDecimal("0")) !=0){
					sum = unclearDetailed.getBwbje1_2_year().subtract(sumOffAmount);
					unclearDetailed.setBwbje1_2_year(sum);
					sum = unclearDetailed.getWbje1_2_year().subtract(unclearDetailed.getOffAmount());
					unclearDetailed.setWbje1_2_year(sum);
				}
				sumOffAmount = clearItemJdbcDao.getSumOffAmountByVoucher("1095", "730",date,clearItem.getBudat(),unclearDetailed.getOffAmountBwbje());
				if(sumOffAmount.compareTo(new BigDecimal("0")) !=0){
					sum = unclearDetailed.getBwbje2_3_year().subtract(sumOffAmount);
					unclearDetailed.setBwbje2_3_year(sum);
					sum = unclearDetailed.getWbje2_3_year().subtract(unclearDetailed.getOffAmount());
					unclearDetailed.setWbje2_3_year(sum);
				}
				sumOffAmount = clearItemJdbcDao.getSumOffAmountByVoucher("1460", "1095",date,clearItem.getBudat(),unclearDetailed.getOffAmountBwbje());
				if(sumOffAmount.compareTo(new BigDecimal("0")) !=0){
					sum = unclearDetailed.getBwbje3_4_year().subtract(sumOffAmount);
					unclearDetailed.setBwbje3_4_year(sum);
					sum = unclearDetailed.getWbje3_4_year().subtract(unclearDetailed.getOffAmount());
					unclearDetailed.setWbje3_4_year(sum);
				}
				sumOffAmount = clearItemJdbcDao.getSumOffAmountByVoucher("1825", "1460",date,clearItem.getBudat(),unclearDetailed.getOffAmountBwbje());
				if(sumOffAmount.compareTo(new BigDecimal("0")) !=0){
					sum = unclearDetailed.getBwbje4_5_year().subtract(sumOffAmount);
					unclearDetailed.setBwbje4_5_year(sum);
					sum = unclearDetailed.getWbje4_5_year().subtract(unclearDetailed.getOffAmount());
					unclearDetailed.setWbje4_5_year(sum);
				}
				sumOffAmount = clearItemJdbcDao.getSumOffAmountByVoucher("9999", "1825",date,clearItem.getBudat(),unclearDetailed.getOffAmountBwbje());
				if(sumOffAmount.compareTo(new BigDecimal("0")) !=0){
					sum = unclearDetailed.getBwbje5_year_above().subtract(sumOffAmount);
					unclearDetailed.setWbje5_year_above(sum);
					sum = unclearDetailed.getBwbje5_year_above().subtract(unclearDetailed.getOffAmount());
					unclearDetailed.setWbje5_year_above(sum);
				}
				//SAP手工的汇损凭证，清账，这些数据要标识为已清。（特点是外币为0，本位币不为0）
				if(unclearDetailed.getVoucher_amount().compareTo(new BigDecimal("0")) ==0){
					unclearDetailed.setWbje1_3_date(new BigDecimal("0") );			
					unclearDetailed.setWbje4_6_date(new BigDecimal("0") );
					unclearDetailed.setWbje7_12_date(new BigDecimal("0") );
					unclearDetailed.setWbje1_2_year(new BigDecimal("0") );
					unclearDetailed.setWbje2_3_year(new BigDecimal("0") );
					unclearDetailed.setWbje3_4_year(new BigDecimal("0") );
					unclearDetailed.setWbje4_5_year(new BigDecimal("0") );
					unclearDetailed.setWbje5_year_above(new BigDecimal("0") );
					
					unclearDetailed.setBwbje1_3_date(new BigDecimal("0") );
					unclearDetailed.setBwbje4_6_date(new BigDecimal("0") );
					unclearDetailed.setBwbje7_12_date(new BigDecimal("0") );
					unclearDetailed.setBwbje1_2_year(new BigDecimal("0") );
					unclearDetailed.setBwbje2_3_year(new BigDecimal("0") );
					unclearDetailed.setBwbje3_4_year(new BigDecimal("0") );
					unclearDetailed.setBwbje4_5_year(new BigDecimal("0") );
					unclearDetailed.setBwbje5_year_above(new BigDecimal("0") );
					unclearDetailed.setOffAmountBwbje(unclearDetailed.getVoucher_bwbje());
				}
			}else{
				//清账生成的汇损凭证，不会再清账，这些数据要标识为已清。（特点是外币为0，本位币不为0）
				if(unclearDetailed.getVoucher_amount().compareTo(new BigDecimal("0")) ==0 ){
					VoucherItem voucheritem=voucherItemJdbcDao.getVoucherItem(unclearDetailed.getVoucherNo(), unclearDetailed.getCompanyno(), unclearDetailed.getGjahr(), unclearDetailed.getBuzei());
					if(voucheritem !=null){
						if(!StringUtils.isNullBlank(voucheritem.getTaxcode())){
							unclearDetailed.setIsclear("1");
							unclearDetailed.setWbje1_3_date(new BigDecimal("0") );			
							unclearDetailed.setWbje4_6_date(new BigDecimal("0") );
							unclearDetailed.setWbje7_12_date(new BigDecimal("0") );
							unclearDetailed.setWbje1_2_year(new BigDecimal("0") );
							unclearDetailed.setWbje2_3_year(new BigDecimal("0") );
							unclearDetailed.setWbje3_4_year(new BigDecimal("0") );
							unclearDetailed.setWbje4_5_year(new BigDecimal("0") );
							unclearDetailed.setWbje5_year_above(new BigDecimal("0") );
							
							unclearDetailed.setBwbje1_3_date(new BigDecimal("0") );
							unclearDetailed.setBwbje4_6_date(new BigDecimal("0") );
							unclearDetailed.setBwbje7_12_date(new BigDecimal("0") );
							unclearDetailed.setBwbje1_2_year(new BigDecimal("0") );
							unclearDetailed.setBwbje2_3_year(new BigDecimal("0") );
							unclearDetailed.setBwbje3_4_year(new BigDecimal("0") );
							unclearDetailed.setBwbje4_5_year(new BigDecimal("0") );
							unclearDetailed.setBwbje5_year_above(new BigDecimal("0") );
							unclearDetailed.setOffAmountBwbje(unclearDetailed.getVoucher_bwbje());
						}
					}
				}
			}
			BigDecimal total = unclearDetailed.getWbje1_3_date().add(unclearDetailed.getWbje4_6_date()).add(unclearDetailed.getWbje7_12_date()).add(unclearDetailed.getWbje1_2_year()).add(unclearDetailed.getWbje2_3_year()).add(unclearDetailed.getWbje3_4_year()).add(unclearDetailed.getWbje4_5_year()).add(unclearDetailed.getWbje5_year_above());
			BigDecimal total2 = unclearDetailed.getBwbje1_3_date().add(unclearDetailed.getBwbje4_6_date()).add(unclearDetailed.getBwbje7_12_date()).add(unclearDetailed.getBwbje1_2_year()).add(unclearDetailed.getBwbje2_3_year()).add(unclearDetailed.getBwbje3_4_year()).add(unclearDetailed.getBwbje4_5_year()).add(unclearDetailed.getBwbje5_year_above());
			
			unclearDetailed.setTotal(total);
			unclearDetailed.setTotal2(total2);
			//如果合计都为0说明已清账，逾期就没有了。
			if(total.compareTo(new BigDecimal("0")) ==0 && total2.compareTo(new BigDecimal("0")) ==0){
				unclearDetailed.setIsExceed("");
				unclearDetailed.setExceed_time("");
			}
			log.debug("第" + i + "笔数据:");
			log.debug("KUNNR:" + map.get("KUNNR"));
			log.debug("HKONT:" + map.get("HKONT"));		
			i = i + 1;
			unclearDetailedList.add(unclearDetailed);
//			unclearDetailedHibernateDao.save(unclearDetailed);
		}
		return unclearDetailedList;
	}
	
	/**
	 * SAP的定义类型 转成 销售订单类型
	 * @param vblentype
	 * @return
	 */
	public String convertSapByContractType(String vblentype){
		if(null ==vblentype)return "";
		String strContractType ="";
		vblentype = vblentype.replaceAll("_", "");
		if (vblentype.equals("Z00220") || vblentype.equals("1")) { // 外贸合作进口业务
			strContractType= strContractType + "," + "外贸自营进口*业务";
		} else if (vblentype.equals("Z00120") || vblentype.equals("2")) { // 外贸合作出口业务
			strContractType= strContractType + ","  + "外贸自营出口*业务";
		} else if (vblentype.equals("Z00210") || vblentype.equals("3")) { // 外贸自营进口业务
			strContractType= strContractType + ","  + "外贸自营进口业务";
		} else if (vblentype.equals("Z00110") || vblentype.equals("4")) { // 外贸自营出口业务
			strContractType= strContractType + ","  + "外贸自营出口业务";
		} else if (vblentype.equals("Z00130") || vblentype.equals("5")) { // 外贸代理出口业务
			strContractType= strContractType + ","  + "外贸代理出口业务";
		} else if (vblentype.equals("Z00230") || vblentype.equals("6")) { // 外贸代理进口业务
			strContractType= strContractType + ","  + "外贸代理进口业务";
		} else if (vblentype.equals("Z00310") || vblentype.equals("7")) { // 内贸业务
			strContractType= strContractType + ","  + "内贸业务";
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
		}else if (vblentype.equals("Z00110") || vblentype.equals("8")) { // 进料加工业务 出口订单
			strContractType= strContractType + ","  + "进料加工业务 出口订单";
		}else if (vblentype.equals("Z00310") || vblentype.equals("8")) { // 进料加工业务 内贸订单
			strContractType= strContractType + ","  + "进料加工业务 内贸订单";
		
		}else if (vblentype.equals("Z00210") || vblentype.equals("9")) { // 外贸自营进口敞口业务
			strContractType= strContractType + ","  + "外贸自营进口敞口业务";
		}else if (vblentype.equals("Z00310") || vblentype.equals("10")) { // 内贸敞口业务
			strContractType= strContractType + ","  + "内贸敞口业务";
		}
		else if (strContractType.equals("Z00410") || vblentype.equals("11")) { // 转口业务
			strContractType= strContractType + ","  + "转口业务";
		}if (vblentype.equals("12")) { // 转口业务
			strContractType= strContractType + ","  + "进料加工业务外销";
		}
		return strContractType;
	}
	
	/**
	 * SAP的定义类型 转成 采购订单类型
	 * @param vbelntype
	 * @return
	 */
	public String convertSapByPurchaserType(String vbelntype){
		if(null == vbelntype)return "";
		String strPurchaserType ="";
		if(vbelntype.equals("Z001") || vbelntype.equals("1")){// 外贸合作进口业务
			strPurchaserType = strPurchaserType + "," + "外贸自营进口*业务";
		}else if(vbelntype.equals("Z002") || vbelntype.equals("2")){// 外贸合作出口业务
			strPurchaserType = strPurchaserType + "," + "外贸自营出口*业务";
		}else if(vbelntype.equals("Z003") || vbelntype.equals("3")){// 外贸自营进口业务
			strPurchaserType = strPurchaserType + "," + "外贸自营进口业务";
		}else if(vbelntype.equals("Z004") ){//外贸自营出口业务
			strPurchaserType = strPurchaserType + "," + "外贸自营出口业务";
		}else if(vbelntype.equals(" ") || vbelntype.equals("")){// 外贸代理出口业务
//			strPurchaserType = strPurchaserType + "," + "外贸代理出口业务";
		}else if(vbelntype.equals("4")){// 外贸代理进口业务
			strPurchaserType = strPurchaserType + "," + "外贸代理进口业务";
		}else if(vbelntype.equals("Z005") || vbelntype.equals("5")){// 内贸业务
			strPurchaserType = strPurchaserType + "," + "内贸业务";
		}else if(vbelntype.equals("Z006") || vbelntype.equals("6")){// 进料加工业务(P:成品采购 I:原材料采购/进口 PI:原材料采购/国内)
			strPurchaserType = strPurchaserType + "," + "进料加工业务(P:成品采购 ";
		}else if(vbelntype.equals("Z003") || vbelntype.equals("7")){
			strPurchaserType = strPurchaserType + "," + "进料加工业务(I:原材料采购/进口";
		}else if(vbelntype.equals("Z002") || vbelntype.equals("8")){
			strPurchaserType = strPurchaserType + "," + "进料加工业务(PI:原材料采购/国内)";
		}else if(vbelntype.equals("Z003") || vbelntype.equals("9")){// 外贸自营进口敞口业务
			strPurchaserType = strPurchaserType + "," + "外贸自营进口敞口业务";
		}else if(vbelntype.equals("Z005") || vbelntype.equals("10")){// 内贸敞口业务
			strPurchaserType = strPurchaserType + "," + "内贸敞口业务";
		}else if(vbelntype.equals("Z007") || vbelntype.equals("11")){// 转口业务
			strPurchaserType = strPurchaserType + "," + "转口业务";
		}else if(vbelntype.equals("12")){// 转口业务
			strPurchaserType = strPurchaserType + "," + "进料加工业务外销";
		}
		return strPurchaserType;
	}
	
	
	/**
	 * 处理退款数据，收付款要减去退款的金额
	 * @param list
	 * @param bukrs
	 * @param customernos
	 * @param username
	 * @param customerType 客户类型1为客户，2为供应商
	 * @param type，记录是哪个模块清的账，07为客户退款，15为供应商退款
	 */
	public void changeAmount(List<UnclearDetailed> list,String bukrs,List<String> customernos,String username,String customerType){
		List<ClearItem> clearItems = new ArrayList<ClearItem>();
		if("1".equals(customerType)){
//			，记录是哪个模块清的账，07为客户退款，15为供应商退款
			clearItems = clearItemJdbcDao.getClearItemByType(bukrs, customernos, username, customerType, "07");
		}else{
			clearItems = clearItemJdbcDao.getClearItemByType(bukrs, customernos, username, customerType, "15");
		}
		
		for(ClearItem clearitem:clearItems){
//			取得取得退款的收款的会计凭证
			
			VoucherItem vi =clearItemJdbcDao.getVoucherItemList(clearitem.getBusinessitemid(),customerType);
			if(null != vi){
//				找到退款的收款减去退款的金额
				for(UnclearDetailed unclearDetailed :list){
					
					//			补前导0，因为SAP会排序，导致外围行项目和SAP行项目不一致
					String rowNubmer = StringUtils.leftPad(vi.getRownumber(), 3, '0');
					String rowNubmer2 = StringUtils.leftPad(unclearDetailed.getBuzei(), 3, '0');
					if(vi.getVoucher().getVoucherno().equals(unclearDetailed.getVoucherNo()) && rowNubmer.equals(rowNubmer2) && vi.getVoucher().getCompanycode().equals(unclearDetailed.getCompanyno()) && vi.getVoucher().getFiyear().equals(unclearDetailed.getGjahr())){
						unclearDetailed.setOffAmount(unclearDetailed.getOffAmount().add(clearitem.getDmbtr()));
						unclearDetailed.setOffAmountBwbje(unclearDetailed.getOffAmountBwbje().add(clearitem.getWrbtr()));
						
						unclearDetailed.setIsclear("1");
						if(unclearDetailed.getWbje1_3_date().compareTo(new BigDecimal("0")) != 0){
							unclearDetailed.setWbje1_3_date(unclearDetailed.getWbje1_3_date().add(clearitem.getDmbtr()) );
							unclearDetailed.setBwbje1_3_date(unclearDetailed.getBwbje1_3_date().add(clearitem.getWrbtr()));
						}
						if(unclearDetailed.getWbje4_6_date().compareTo(new BigDecimal("0")) != 0){
							unclearDetailed.setWbje4_6_date(unclearDetailed.getWbje4_6_date().add(clearitem.getDmbtr()) );
							unclearDetailed.setBwbje4_6_date(unclearDetailed.getBwbje4_6_date().add(clearitem.getWrbtr()));
						}
						if(unclearDetailed.getWbje7_12_date().compareTo(new BigDecimal("0")) != 0){
							unclearDetailed.setWbje7_12_date(unclearDetailed.getWbje7_12_date().add(clearitem.getDmbtr()) );
							unclearDetailed.setBwbje7_12_date(unclearDetailed.getBwbje7_12_date().add(clearitem.getWrbtr()));
						}
						if(unclearDetailed.getWbje1_2_year().compareTo(new BigDecimal("0")) != 0){
							unclearDetailed.setWbje1_2_year(unclearDetailed.getWbje1_2_year().add(clearitem.getDmbtr()) );
							unclearDetailed.setBwbje1_2_year(unclearDetailed.getBwbje1_2_year().add(clearitem.getWrbtr()));
						}
						if(unclearDetailed.getWbje2_3_year().compareTo(new BigDecimal("0")) != 0){
							unclearDetailed.setWbje2_3_year(unclearDetailed.getWbje2_3_year().add(clearitem.getDmbtr()) );
							unclearDetailed.setBwbje2_3_year(unclearDetailed.getBwbje2_3_year().add(clearitem.getWrbtr()));
						}
						if(unclearDetailed.getWbje3_4_year().compareTo(new BigDecimal("0")) != 0){
							unclearDetailed.setWbje3_4_year(unclearDetailed.getWbje3_4_year().add(clearitem.getDmbtr()) );
							unclearDetailed.setBwbje3_4_year(unclearDetailed.getBwbje3_4_year().add(clearitem.getWrbtr()));
						}
						if(unclearDetailed.getWbje4_5_year().compareTo(new BigDecimal("0")) != 0){
							unclearDetailed.setWbje4_5_year(unclearDetailed.getWbje4_5_year().add(clearitem.getDmbtr()) );
							unclearDetailed.setBwbje4_5_year(unclearDetailed.getBwbje4_5_year().add(clearitem.getWrbtr()));
						}
						if(unclearDetailed.getWbje5_year_above().compareTo(new BigDecimal("0")) != 0){
							unclearDetailed.setWbje5_year_above(unclearDetailed.getWbje5_year_above().add(clearitem.getDmbtr()) );
							unclearDetailed.setBwbje5_year_above(unclearDetailed.getBwbje5_year_above().add(clearitem.getWrbtr()));
						}
						
						BigDecimal total = unclearDetailed.getWbje1_3_date().add(unclearDetailed.getWbje4_6_date()).add(unclearDetailed.getWbje7_12_date()).add(unclearDetailed.getWbje1_2_year()).add(unclearDetailed.getWbje2_3_year()).add(unclearDetailed.getWbje3_4_year()).add(unclearDetailed.getWbje4_5_year()).add(unclearDetailed.getWbje5_year_above());
						BigDecimal total2 = unclearDetailed.getBwbje1_3_date().add(unclearDetailed.getBwbje4_6_date()).add(unclearDetailed.getBwbje7_12_date()).add(unclearDetailed.getBwbje1_2_year()).add(unclearDetailed.getBwbje2_3_year()).add(unclearDetailed.getBwbje3_4_year()).add(unclearDetailed.getBwbje4_5_year()).add(unclearDetailed.getBwbje5_year_above());
						
						unclearDetailed.setTotal(total);
						unclearDetailed.setTotal2(total2);
						
					}
				}
			}
		}
	}
}
