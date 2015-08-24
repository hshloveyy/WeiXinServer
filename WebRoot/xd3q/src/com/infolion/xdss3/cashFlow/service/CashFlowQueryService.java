/**
 * 
 */
package com.infolion.xdss3.cashFlow.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infolion.platform.basicApp.authManage.UserContextHolder;
import com.infolion.platform.basicApp.authManage.domain.UserContext;
import com.infolion.platform.dpframework.util.StringUtils;
import com.infolion.sapss.bapi.ConnectManager;
import com.infolion.sapss.bapi.ExtractSapData;
import com.infolion.xdss3.ageAnalysis.service.UnclearDetailedService;
import com.infolion.xdss3.cashFlow.dao.CashFlowJdbcDao;
import com.infolion.xdss3.cashFlow.domain.CashFlow;
import com.infolion.xdss3.customerDrawback.dao.CustomerRefundItemJdbcDao;
import com.infolion.xdss3.masterData.dao.CustomerJdbcDao;
import com.infolion.xdss3.masterData.dao.SupplierJdbcDao;
import com.sap.mw.jco.IFunctionTemplate;
import com.sap.mw.jco.JCO;




/**
 * @author 钟志华
 *
 */
@Service
public class CashFlowQueryService {
	protected static Log log = LogFactory.getFactory().getLog(CashFlowQueryService.class);
	private static String ZFI_CASHFLOW ="ZFI_CASHFLOW";
//	['01','TT'],['02','DP'],['03','即期信用证'],['04','远期信用证'],['05','DA'],['06','现金'],['07','转账'],['08','电汇'],['09','网银'],['10','银行汇票'],['11','国内信用证'],['12','银行/商业承兑汇票']]
	/**
	 * 汇票，要过资金部确定才算在批
	 */
	private static String COLLECT_TYPE_1 ="'12'";
	
	private static String COLLECT_TYPE_2 ="'01','02','03','04','05','06','07','08','09','10','11'";
//	['01','TT'],['02','DP'],['03','即期信用证'],['04','远期信用证'],['05','DA'],['06','国内信用证'],['07','银行/商业承兑汇票'],['08','现金'],['09','背书'],['10','转账'],['11','电汇'],['12','网银'],['13','银行汇票'],['14','预付款']]
//	['1','承兑'],['2','押汇'],['3','一般付款']]

	/**	进口押汇
	 * 进口押汇，过出纳才算在批,出凭证就结束。
	 */
	private static String PAYMENT_TYPE_1 ="'2'";
	/**
	 * 承兑,一般付款
	 */
	private static String PAYMENT_TYPE_2 ="'1','3'";
	@Autowired
    private CashFlowJdbcDao cashFlowJdbcDao;
	public void setCashFlowJdbcDao(CashFlowJdbcDao cashFlowJdbcDao) {
		this.cashFlowJdbcDao = cashFlowJdbcDao;
	}
    @Autowired
    private  CustomerJdbcDao customerJdbcDao;
    
    @Autowired
    private SupplierJdbcDao supplierJdbcDao;
    
    
	public void setCustomerJdbcDao(CustomerJdbcDao customerJdbcDao) {
		this.customerJdbcDao = customerJdbcDao;
	}
	public void setSupplierJdbcDao(SupplierJdbcDao supplierJdbcDao) {
		this.supplierJdbcDao = supplierJdbcDao;
	}
	@Autowired
	private UnclearDetailedService unclearDetailedService;
	
	public void setUnclearDetailedService(
			UnclearDetailedService unclearDetailedService) {
		this.unclearDetailedService = unclearDetailedService;
	}
	@Autowired
	private CustomerRefundItemJdbcDao customerRefundItemJdbcDao;
	
	public void setCustomerRefundItemJdbcDao(
			CustomerRefundItemJdbcDao customerRefundItemJdbcDao) {
		this.customerRefundItemJdbcDao = customerRefundItemJdbcDao;
	}
	/**
	 * 
	 * @param bukrs 公司代码
	 * @param fromdate开始日期
	 * @param todate终止日期
	 * @param gsber字符串(业务范围)
	 * @param rstgr字符串(原因代码)
	 * @param hkont字符串(科目)
	 * @param projectno字符串(项目号)
	 * @param kunnr客户
	 * @param lifnr供应商
	 * @param belnr字符串(凭证号)
	 * @return
	 */
	public List<Map<String, String>> _executeRfcGetCashFlow(String bukrs,String fromdate, String todate,String gsber, String rstgr,String hkont,String projectno,String customers,String customerType,String belnr){
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();

		JCO.Table returnDatas = null;

		ConnectManager manager = ConnectManager.manager;
		manager.getClient();
		JCO.Client client = null;
		try
		{
			IFunctionTemplate ftemplate = manager.repository.getFunctionTemplate(ZFI_CASHFLOW);
			if (ftemplate != null)
			{
				JCO.Function function = ftemplate.getFunction();
				client = JCO.getClient(manager.poolName);
				JCO.ParameterList input = function.getImportParameterList();
				
				if(!StringUtils.isNullBlank(gsber)){					
					String gsbers2 = gsber.replaceAll("'", "");
					JCO.Table  t_gsbers = function.getTableParameterList().getTable("T_GSBER");
					String gs[] = gsbers2.split(",");
					for(String gsber2:gs){
						t_gsbers.appendRow();
						t_gsbers.setValue(gsber2, "STR");
					}
				}
				if(!StringUtils.isNullBlank(rstgr)){					
					String rstgr2 = rstgr.replaceAll("'", "");
					JCO.Table  t_rstgr = function.getTableParameterList().getTable("T_RSTGR");
					String gs[] = rstgr2.split(",");
					for(String rs2:gs){
						t_rstgr.appendRow();
						t_rstgr.setValue(rs2, "STR");
					}
				}
				if(!StringUtils.isNullBlank(hkont)){					
					String hkont2 = hkont.replaceAll("'", "");
					JCO.Table  t_hkonts = function.getTableParameterList().getTable("T_HKONT");
					String gs[] = hkont2.split(",");
					for(String hkont_2:gs){
						t_hkonts.appendRow();
						t_hkonts.setValue(hkont_2, "STR");
					}
				}
				if(!StringUtils.isNullBlank(belnr)){					
					String belnr2 = belnr.replaceAll("'", "");
					JCO.Table  t_belnrs = function.getTableParameterList().getTable("T_BELNR");
					String gs[] = belnr2.split(",");
					for(String belnr_2:gs){
						t_belnrs.appendRow();
						t_belnrs.setValue(belnr_2, "STR");
					}
				}
				if(!StringUtils.isNullBlank(customers)){					
					String customers2 = customers.replaceAll("'", "");
					JCO.Table  t_customers =null;
					if("1".equals(customerType)){
						t_customers = function.getTableParameterList().getTable("T_KUNNR");
					}else{
						t_customers = function.getTableParameterList().getTable("T_LIFNR");
					}
					String gs[] = customers2.split(",");
					for(String customer:gs){
						t_customers.appendRow();
						t_customers.setValue(customer, "STR");
					}
				}
				input.setValue(bukrs, "I_BUKRS");
				input.setValue(fromdate, "I_FROMDATE");
				input.setValue(todate, "I_TODATE");
				client.execute(function);

				// 新增的数据集合
				returnDatas = function.getTableParameterList().getTable("T_CASHFLOW");

				list = ExtractSapData.getList(returnDatas);

			}
			else
			{
				log.error("----------------取得需要现金流量项数据发生错误：目标系统上不存在RFC" + ZFI_CASHFLOW);
//				list = null;
			}
		}
		catch (Exception ex)
		{
			log.error("----------------取得现金流量项数据发生错误" + ex);
//			list = null;
		}
		finally
		{
			manager.cleanUp();
		}

		return list;
	}
	/**
	 * 
	 * @param list
	 * @param bukrs
	 * @param gsbers
	 * @param fromdate
	 * @param todate
	 * @param subject
	 * @return
	 */
	public List<CashFlow> getCshFlow(List<Map<String,String>> list,String bukrs,String gsbers,String fromdate, String todate,String subject){
		List<CashFlow> cashFlowList = new ArrayList<CashFlow>();
		UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
		String username=userContext.getUser().getUserName();
		int i=0;
		for (Map map : list){
			CashFlow cashFlow = new CashFlow();
			String kunnr = map.get("KUNNR").toString();
			String lifnr = map.get("LIFNR").toString();
			cashFlow.setKunnr(kunnr);
			cashFlow.setLifnr(lifnr);
			cashFlow.setAugdt(map.get("AUGDT").toString());
			cashFlow.setBelnr(map.get("BELNR").toString());
			cashFlow.setBname(map.get("BNAME").toString());
			cashFlow.setBudat(map.get("BUDAT").toString());
			cashFlow.setBukrs(map.get("BUKRS").toString());
			
			cashFlow.setCurrency(map.get("PSWSL").toString());
			cashFlow.setDmbtr(new BigDecimal(map.get("DMBTR").toString()));
			cashFlow.setGsber(map.get("GSBER").toString());
			cashFlow.setHkont(map.get("HKONT").toString());			
			cashFlow.setRstgr(map.get("RSTGR").toString());
			cashFlow.setSgtxt(map.get("SGTXT").toString());			
			cashFlow.setTxt40(map.get("TXT40").toString());
			
			cashFlow.setWrbtr(new BigDecimal(map.get("WRBTR").toString()));
			
			String gjahr = map.get("GJAHR").toString();
			String shkzg = map.get("SHKZG").toString();
			String hkont2 = map.get("HKONT2").toString();
			String BSART = map.get("BSART").toString();
			String VBELTYPE = map.get("VBELTYPE").toString();
			cashFlow.setGjahr(gjahr);
			cashFlow.setShkzg(shkzg);
			cashFlow.setHkont2(hkont2);
			cashFlow.setBSART(BSART);
			cashFlow.setVBELTYPE(VBELTYPE);
			cashFlow.setBuzei(map.get("BUZEI").toString());
			
			cashFlow.setOnroadamount("0");
			cashFlow.setOnroadamountBwb("0");
			
			cashFlow.setUsername(username);
		
			
			cashFlowList.add(cashFlow);
		}
		
		return cashFlowList;
	}
	
	/**
	 * 取得现金流其他的属性
	 * @param cashFlow
	 */
	public void getCashFlow(CashFlow cashFlow){
		String gjahr = cashFlow.getGjahr();
		String shkzg = cashFlow.getShkzg();
		String hkont2 = cashFlow.getHkont2();
		String BSART = cashFlow.getBSART();
		String VBELTYPE = cashFlow.getVBELTYPE();
		String kunnr = cashFlow.getKunnr();
		String lifnr = cashFlow.getLifnr();
		if(!StringUtils.isNullBlank(hkont2)){
//			hkont2以逗号分隔的字符串
			String[] hk = hkont2.split(",");
			String sname2="";
			for(String subjectco :hk){
				String sname = customerRefundItemJdbcDao.getSubjectNameById(subjectco,cashFlow.getBukrs());	
				sname2 = sname2 + subjectco + "(" + sname + ")";
			}						
			cashFlow.setSubjectCode(sname2);
		}
		
		String bussinessid = this.cashFlowJdbcDao.getBussinessid(cashFlow.getBelnr(), cashFlow.getBukrs(), gjahr);
		
		Map<String,String> map2=new HashMap<String,String>();
		
		if(!StringUtils.isNullBlank(bussinessid)){
			if(map2.isEmpty()){
				map2=this.cashFlowJdbcDao.getProjectnoByCollectid(bussinessid);
			}
			if(map2.isEmpty()){
				map2=this.cashFlowJdbcDao.getProjectno(bussinessid);
			}
			if(map2.isEmpty()){
				map2=this.cashFlowJdbcDao.getProjectnoByRefmentid(bussinessid);
			}
			if(!map2.isEmpty()){
				cashFlow.setBname(map2.get("projectno"));
				cashFlow.setCollectno(map2.get("collectno"));
				cashFlow.setRefmentno(map2.get("refundmentno"));
				cashFlow.setPaymentno(map2.get("paymentno"));	
				if(StringUtils.isNullBlank(kunnr) && StringUtils.isNullBlank(lifnr)){
					cashFlow.setKunnr(map2.get("customer"));
					cashFlow.setLifnr(map2.get("supplier"));
				}
			}
		}
		if(!StringUtils.isNullBlank(kunnr)){
			if(kunnr.length()==8)kunnr="00" + kunnr;
			String customerName =customerJdbcDao.getCustomerName(kunnr);
			cashFlow.setCustomerName(customerName);
//			if("H".equals(shkzg)){
//				cashFlow.setDmbtr(new BigDecimal("0").subtract(cashFlow.getDmbtr()));
//				cashFlow.setWrbtr(new BigDecimal("0").subtract(cashFlow.getWrbtr()));
//			}

			if(!StringUtils.isNullBlank(VBELTYPE)){					
				cashFlow.setBussinessType(VBELTYPE);
			}else{
				cashFlow.setBussinessType(BSART);
			}
			String btype = unclearDetailedService.convertSapByContractType(cashFlow.getBussinessType());
			cashFlow.setBussinessType(btype);
		}else if(!StringUtils.isNullBlank(lifnr)){
			if(lifnr.length()==8)lifnr="00" + lifnr;
			String supplierName =supplierJdbcDao.getSupplierName(lifnr);
			cashFlow.setSupplierName(supplierName);
//			if("S".equals(shkzg)){
//				cashFlow.setDmbtr(new BigDecimal("0").subtract(cashFlow.getDmbtr()));
//				cashFlow.setWrbtr(new BigDecimal("0").subtract(cashFlow.getWrbtr()));
//			}
			
		
			//取得纯代理客户号
			String representpaycust = this.cashFlowJdbcDao.getRepresentpaycust(bussinessid);
			if(!StringUtils.isNullBlank(representpaycust)){
				String customerName =customerJdbcDao.getCustomerName(representpaycust);
				cashFlow.setCustomerName(customerName);
			}

			String btype = unclearDetailedService.convertSapByPurchaserType(BSART);
			cashFlow.setBussinessType(btype);

		}else{
//			if("H".equals(shkzg)){
//				cashFlow.setDmbtr(new BigDecimal("0").subtract(cashFlow.getDmbtr()));
//				cashFlow.setWrbtr(new BigDecimal("0").subtract(cashFlow.getWrbtr()));
//			}
		}
		
		if("H".equals(shkzg)){
			cashFlow.setDmbtr(new BigDecimal("0").subtract(cashFlow.getDmbtr()));
			cashFlow.setWrbtr(new BigDecimal("0").subtract(cashFlow.getWrbtr()));
		}
//		日币乘以100
//		if("JPY".equals(cashFlow.getCurrency())){
//			cashFlow.setWrbtr(cashFlow.getWrbtr().multiply(new BigDecimal("100")));
//		}
//		如果有立项没业务类型就去取立项上的业务类型
		if(!StringUtils.isNullBlank(cashFlow.getBname()) && StringUtils.isNullBlank(cashFlow.getBussinessType())){
			String btype2=cashFlowJdbcDao.getBusinessType(cashFlow.getBname());
			String btype = unclearDetailedService.convertSapByContractType(btype2);
			cashFlow.setBussinessType(btype);
		}
	}
	/**
	 * 取得在批的收，付款单的现金流
	 * @param bukrs
	 * @param gsbers
	 * @param fromdate
	 * @param todate
	 * @param subject
	 * @return
	 */
	public List<CashFlow> getOnroadCshFlow(String bukrs,String fromdate, String todate,String gsber, String rstgr,String hkont,String projectno,String customers,String customerType,String belnr){
		List<CashFlow> cashFlowList = new ArrayList<CashFlow>();
		if("1".equals(customerType) || "".equals(customerType) ){
			List<CashFlow> cashFlowList2 = this.cashFlowJdbcDao.getCollectBankItem(bukrs, fromdate, todate, gsber, rstgr, hkont, projectno, customers, this.COLLECT_TYPE_2, null);
	//		汇票，要过资金部确定才算在批
			List<CashFlow> cashFlowList3 = this.cashFlowJdbcDao.getCollectBankItem(bukrs, fromdate, todate, gsber, rstgr, hkont, projectno, customers, this.COLLECT_TYPE_1, null);
	//		for(CashFlow cf:cashFlowList3){			
	//			List<String> taskList = this.cashFlowJdbcDao.getTaskName(cf.getBussinessid());
	//			if(!taskList.contains("资金部确认票据")){
	//				cashFlowList3.remove(cf);
	//			}			
	//		}
			for(int i=cashFlowList3.size()-1;i>=0;i--){
				CashFlow cf = cashFlowList3.get(i);
				List<String> taskList = this.cashFlowJdbcDao.getTaskName(cf.getBussinessid());
				if(!taskList.contains("资金部确认票据")){
					cashFlowList3.remove(cf);
				}			
			}
			
			cashFlowList.addAll(cashFlowList2);
			cashFlowList.addAll(cashFlowList3);
		}
		if("2".equals(customerType)  ||  "".equals(customerType) ){
			List<CashFlow> cashFlowList4 = this.cashFlowJdbcDao.getPaymentBankItem(bukrs, fromdate, todate, gsber, rstgr, hkont, projectno, customers, this.PAYMENT_TYPE_2, null);
	//		进口押汇，过出纳才算在批,出凭证就结束。
			List<CashFlow> cashFlowList5 = this.cashFlowJdbcDao.getPaymentBankItem(bukrs, fromdate, todate, gsber, rstgr, hkont, projectno, customers, this.PAYMENT_TYPE_1, null);
	//		for(CashFlow cf:cashFlowList5){			
	//			List<String> taskList = this.cashFlowJdbcDao.getTaskName(cf.getBussinessid());
	//			if(!taskList.contains("资金部出纳付款")){
	//				cashFlowList5.remove(cf);
	//			}			
	//		}
			for(int i=cashFlowList5.size()-1;i>=0;i--){
				CashFlow cf = cashFlowList5.get(i);
				List<String> taskList = this.cashFlowJdbcDao.getTaskName(cf.getBussinessid());
				if(!taskList.contains("资金部出纳付款")){
					cashFlowList5.remove(cf);
				}			
			}
			
			cashFlowList.addAll(cashFlowList4);
			cashFlowList.addAll(cashFlowList5);
		}
		List<CashFlow> cashFlowList6 = new ArrayList<CashFlow>();
		if("".equals(customerType)){
			 cashFlowList6 = this.cashFlowJdbcDao.getRefmentBankItem(bukrs, fromdate, todate, gsber, rstgr, hkont, projectno, null, null, null);
		}else{
			if("1".equals(customerType)){
				cashFlowList6 = this.cashFlowJdbcDao.getRefmentBankItem(bukrs, fromdate, todate, gsber, rstgr, hkont, projectno, customers, null, null);
			}else{
				cashFlowList6 = this.cashFlowJdbcDao.getRefmentBankItem(bukrs, fromdate, todate, gsber, rstgr, hkont, projectno, null, customers, null);
			}
		}
		for(int i=cashFlowList6.size()-1;i>=0;i--){
			CashFlow cf = cashFlowList6.get(i);
			List<String> taskList = this.cashFlowJdbcDao.getTaskName(cf.getBussinessid());
			if(!(taskList.contains("资金部出纳审核（本币）") || taskList.contains("资金部出纳审核（外币）") || taskList.contains("上海信达诺出纳审核"))){
				cashFlowList6.remove(cf);
			}			
		}
		cashFlowList.addAll(cashFlowList6);
		return cashFlowList;
	}
	/**
	 * // 批量插入,  
	 * @param list
	 */
	public void saveOrUpdateAll(List<CashFlow> list) {  
		cashFlowJdbcDao.saveOrUpdateAll(list);
	}
	/**
	 * 删除
	 * @param username
	 */
	public void delCashFlow(String username){
		cashFlowJdbcDao.delCashFlow(username);
	}
	/**
	 * 
	 * @param username
	 * @return
	 */
	public List<CashFlow> getCashFlow(String username,String start,String pageSize){
		return cashFlowJdbcDao.getCashFlow( username, start, pageSize);
	}
	/**
	 * 取得总条数
	 * @param username
	 * @return
	 */
	public String getCount(String username){
		return cashFlowJdbcDao.getCount(username);
	}
	
	/**
	 * 分页导出，取视图
	 * @param username
	 * @param start
	 * @param pageSize
	 * @return
	 */
	public List<CashFlow> getCashFlowByExport(String username,String start,String pageSize){
		return cashFlowJdbcDao.getCashFlowByExport(username, start, pageSize);
	}
}
