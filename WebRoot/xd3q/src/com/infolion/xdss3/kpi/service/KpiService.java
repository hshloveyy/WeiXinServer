package com.infolion.xdss3.kpi.service;



import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infolion.platform.basicApp.authManage.UserContextHolder;
import com.infolion.platform.basicApp.authManage.domain.UserContext;
import com.infolion.platform.bo.service.BusinessObjectService;
import com.infolion.platform.dpframework.util.DateUtils;
import com.infolion.platform.dpframework.util.StringUtils;
import com.infolion.platform.util.CodeGenerator;
import com.infolion.sapss.bapi.ConnectManager;
import com.infolion.sapss.bapi.ExtractSapData;

import com.infolion.xdss3.kpi.dao.KpiJdbcDao;
import com.infolion.xdss3.kpi.domain.BankAcceptance;


import com.sap.mw.jco.IFunctionTemplate;
import com.sap.mw.jco.JCO;

@Service
public class KpiService {
	protected static Log log = LogFactory.getFactory().getLog(KpiService.class);
	private static String ZFIRPT086 ="ZFIRPT086";
	
	
	@Autowired
	private  KpiJdbcDao kpiJdbcDao;
	
	/**
	 * @param kpiJdbcDao the kpiJdbcDao to set
	 */
	public void setKpiJdbcDao(KpiJdbcDao kpiJdbcDao) {
		this.kpiJdbcDao = kpiJdbcDao;
	}
	
	
	/**
	 * 
	 * @param p_bukrs 公司代码
	 * @param p_gsber  业务范围
	 * @param p_dudat 过账日期	
	 * @return
	 */
	public Map<String,String>  _executeRfc(String p_bukrs,String p_gsber ,String p_dudat)
	{
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();

		Map<String,String> result = new HashMap<String, String>();
		ConnectManager manager = ConnectManager.manager;
		manager.getClient();
		JCO.Client client = null;
		try
		{
			IFunctionTemplate ftemplate = manager.repository.getFunctionTemplate(ZFIRPT086);
			if (ftemplate != null)
			{
				JCO.Function function = ftemplate.getFunction();
				client = JCO.getClient(manager.poolName);
				JCO.ParameterList input = function.getImportParameterList();
				input.setValue(p_bukrs, "P_BUKRS");			
				input.setValue(p_dudat, "P_BUDAT");				
				input.setValue(p_gsber, "P_RBUSA");				
				
				client.execute(function);

				//处理返回信息
				JCO.ParameterList outPut = function.getExportParameterList();
				result.put("WK_PJZY",(String)outPut.getValue("WK_PJZY"));
				result.put("WK_YYSR",(String)outPut.getValue("WK_YYSR"));
				result.put("WK_JLR",(String)outPut.getValue("WK_JLR"));
				result.put("WK_ZZL",(String)outPut.getValue("WK_ZZL"));
				result.put("WK_JLL",(String)outPut.getValue("WK_JLL"));
				result.put("WK_COUNT",(String)outPut.getValue("WK_COUNT"));

			}
			else
			{
				log.error("----------------取得ZFIRPT086发生错误：目标系统上不存在RFC" + ZFIRPT086);
//				list = null;
			}
		}
		catch (Exception ex)
		{
			log.error("----------------取得ZFIRPT086调汇数据发生错误" + ex);
//			list = null;
		}
		finally
		{
			manager.cleanUp();
		}

		return result;
	}	
	
	public String getPaymentQuerySql(String p_bukrs,String p_gsber,String todate,String p_checkdate){
		String sql="select * from ( select (case when a.checkdate is not null then a.checkdate else b.checkdate end) as checkdate,(case when a.amount is null then 0 else a.amount end) as amount,(case when b.amount is null then 0 else b.amount end)  as amount2, " +
				"((case when a.amount is null then 0 else a.amount end) - (case when b.amount is null then 0 else b.amount end)) as amount3 from ( " +
				"select v.checkdate,sum(vi.amount2) as amount  from yvoucher v ,yvoucheritem vi where v.voucherid=vi.voucherid " +
				"and trim(v.voucherno) is not null and v.processstate !='-1' and vi.controlaccount ='2201010001' " +
				"and v.businessid in (select p.paymentid from ypayment p where p.paymenttype='07' and p.businessstate != '-1') " +
				"and v.voucherclass='1' and v.checkdate between '"+todate+"' and '"+p_checkdate+"'  and vi.depid='"+p_gsber+"' and v.companycode='"+p_bukrs+"' " +
				"group by v.checkdate ) a " +
				"full join " +
				"( select v.checkdate,sum(vi.amount2) as amount   from yvoucher v ,yvoucheritem vi where v.voucherid=vi.voucherid " +
				"and trim(v.voucherno) is not null and v.processstate !='-1' and vi.controlaccount ='2201010001' " +
				"and v.businessid in (select p.paymentid from ypayment p where p.paymenttype='07' and p.businessstate != '-1') " +
				"and v.voucherclass='4' and v.checkdate between '"+todate+"' and '"+p_checkdate+"'  and vi.depid='"+p_gsber+"' and v.companycode='"+p_bukrs+"' " +
				"group by v.checkdate ) b " +
				"on a.checkdate=b.checkdate  ) a ORDER BY a.checkdate ASC ";
		return sql ;
	}
	/**
	 * 取日额度占用总总额
	 * @param sql
	 * @return
	 * @throws ParseException 
	 */
	public  BigDecimal getww_pjzy_sum(String p_bukrs,String p_gsber,String todate,String p_checkdate) throws ParseException
	{

//		List<BankAcceptance> list_qc =this.getBankAcceptanceByqc(p_bukrs, p_gsber, p_checkdate);
//		List<BankAcceptance> list =this.getBankAcceptance(p_bukrs, p_gsber, todate, p_checkdate);
//		list.addAll(0, list_qc);
//	 	Calendar cal = Calendar.getInstance();
//		Date	strDate_2 = (new SimpleDateFormat("yyyyMMdd")).parse(todate);
//	  	Date	strDate2 = (new SimpleDateFormat("yyyyMMdd")).parse(todate);
//	  	Date	endDate2 = (new SimpleDateFormat("yyyyMMdd")).parse(p_checkdate);
//	  	
//		cal.setTime(strDate2);			
//		BigDecimal aunout = new BigDecimal("0");
//		while(strDate2.compareTo(endDate2) <=0){
//			long  day=(strDate2.getTime()-strDate_2.getTime())/(24*60*60*1000) +1; 
//			BigDecimal amount4 = new BigDecimal("0");
//			for(BankAcceptance bat:list){
//				Date	checkdate2 = (new SimpleDateFormat("yyyyMMdd")).parse(bat.getCheckdate());
//				if(checkdate2.compareTo(strDate_2) >=0 && checkdate2.compareTo(strDate2) <=0){
//					amount4 = amount4.add(bat.getAmount3()).divide(new BigDecimal(day), 3,BigDecimal.ROUND_HALF_EVEN);;
//				}
//			}
//			aunout = aunout.add(amount4);
////			aunout = amount4;
//			cal.add(Calendar.DATE, 1);
//			strDate2 =cal.getTime();			
//		}
		List<BankAcceptance> list =this.getBankAcceptance2(p_bukrs, p_gsber, todate, p_checkdate);
		BigDecimal aunout = new BigDecimal("0");
		for(BankAcceptance bat :list){
			aunout = aunout.add(bat.getAmount4());
		}
	 	
		Date	endDate2 = (new SimpleDateFormat("yyyyMMdd")).parse(p_checkdate);
	  	Date	strDate2 = (new SimpleDateFormat("yyyyMMdd")).parse(todate);
		long  day=(endDate2.getTime()-strDate2.getTime())/(24*60*60*1000) +1;
		BigDecimal aunout4 = new BigDecimal("0");
		aunout4 = aunout.divide(new BigDecimal(day), 3,BigDecimal.ROUND_HALF_EVEN);
		return aunout4;
	}
	public List<BankAcceptance> getBankAcceptance(String p_bukrs,String p_gsber,String todate,String p_checkdate){
		String sql = this.getPaymentQuerySql(p_bukrs,p_gsber,todate,p_checkdate);
		return this.kpiJdbcDao.getBankAcceptance(sql);
	}
	
	public List<BankAcceptance> getBankAcceptance2(String p_bukrs,String p_gsber,String todate,String p_checkdate) throws ParseException {
//		如果是查2015，要输入期初，如大于2015取前一年的数据
		List<BankAcceptance> list_qc =this.getBankAcceptanceByqc(p_bukrs, p_gsber, p_checkdate);
		List<BankAcceptance> list =this.getBankAcceptance(p_bukrs, p_gsber, todate, p_checkdate);
		boolean flag=true;
//		加上期初数
		for(BankAcceptance bat :list){
			BigDecimal aunout = new BigDecimal("0");
			for(BankAcceptance bat_qc :list_qc){
				aunout = aunout.add(bat_qc.getAmount4());
			}			
			aunout = aunout.add(bat.getAmount4());
			bat.setAmount4(aunout);	
			String md = bat.getCheckdate().substring(4, 8);
			if("0101".equals(md))flag=false;
		}
//		如果1月1号有数据不加，因为已算期初进去了
		if(flag)list.addAll(0, list_qc);
		
		Calendar cal = Calendar.getInstance();
		Date	strDate_2 = (new SimpleDateFormat("yyyyMMdd")).parse(todate);
	  	Date	strDate2 = (new SimpleDateFormat("yyyyMMdd")).parse(todate);
	  	Date	endDate2 = (new SimpleDateFormat("yyyyMMdd")).parse(p_checkdate);
	  	
		cal.setTime(strDate2);			
		BigDecimal aunout4 = new BigDecimal("0");
		List<BankAcceptance> list2 = new ArrayList<BankAcceptance>();
		while(strDate2.compareTo(endDate2) <=0){
			boolean f=true;
			for(BankAcceptance bat :list){
				Date	checkdate = (new SimpleDateFormat("yyyyMMdd")).parse(bat.getCheckdate());
				if(strDate2.compareTo(checkdate) == 0){
					list2.add(bat);
					f=false;
					aunout4 = bat.getAmount4();
					break;
				}
			}
			if(f){
				 BankAcceptance bat = new BankAcceptance();			 
				  bat.setCheckdate(new SimpleDateFormat("yyyyMMdd").format(strDate2));
				  bat.setAmount(new BigDecimal(0));
				  bat.setAmount2(new BigDecimal(0));
				  bat.setAmount3(new BigDecimal(0));				  
				  bat.setAmount4(aunout4);
				  list2.add(bat);
			}
			cal.add(Calendar.DATE, 1);
			strDate2 =cal.getTime();	
		}
		return list2;
	}
	/**
	 * 取期初
	 * 如果是查2015，要输入期初，如大于2015取前一年的数据,小于2015，数据不准
	 * @param p_bukrs
	 * @param p_gsber
	 * @param todate
	 * @param p_checkdate
	 * @return
	 */
	public List<BankAcceptance> getBankAcceptanceByqc(String p_bukrs,String p_gsber,String p_checkdate){
		List<BankAcceptance>  list = new ArrayList<BankAcceptance>();
		String year = p_checkdate.substring(0,4);
		if("2015".equals(year)){
			BankAcceptance bat = new BankAcceptance();
			bat.setCheckdate("20150101");
			bat.setAmount(new BigDecimal("0"));
			bat.setAmount2(new BigDecimal("0"));
			if("2105".equals(p_gsber)){				
				bat.setAmount3(new BigDecimal("1886291.87"));
			}else if("2111".equals(p_gsber)){				
				bat.setAmount3(new BigDecimal("37527653.6"));
			}else{
				bat.setAmount3(new BigDecimal("0"));
			}
			bat.setAmount4(bat.getAmount3());
			list.add(bat);
		}else{
			String s_year = Integer.toString(year.indexOf(year) -1);
			String todate =s_year + "0101";
			String formdate =s_year + "1231";
			String sql=this.getPaymentQuerySql(p_bukrs, p_gsber, todate, formdate);
			sql = "select sum(amount3) from (" + sql + ") t";
			BigDecimal sum=this.kpiJdbcDao.getSumAmount(sql);
			BankAcceptance bat = new BankAcceptance();
			bat.setCheckdate(year + "0101");
			bat.setAmount(new BigDecimal("0"));
			bat.setAmount2(new BigDecimal("0"));
			bat.setAmount3(sum);
			bat.setAmount4(bat.getAmount3());
			list.add(bat);
		}
		return list;
	}
}
