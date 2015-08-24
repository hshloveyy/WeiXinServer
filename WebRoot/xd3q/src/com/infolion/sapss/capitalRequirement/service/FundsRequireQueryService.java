/*
 * @(#)FundsRequireQueryService.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：陈喜平
 *  时　间：2009-11-3
 *  描　述：创建
 */

package com.infolion.sapss.capitalRequirement.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infolion.sapss.bapi.ExtractSapData;
import com.infolion.sapss.capitalRequirement.dao.FundsRequireQueryDao;

@Service
public class FundsRequireQueryService {
	@Autowired
	private FundsRequireQueryDao fundsRequireQueryDao;

	public void setFundsRequireQueryDao(FundsRequireQueryDao fundsRequireQueryDao) {
		this.fundsRequireQueryDao = fundsRequireQueryDao;
	}
	public List makeData(Map<String,String> map){
		List paymentList = this.fundsRequireQueryDao.getPaymentInfo(map);
		//List bankAcceptanceList = this.fundsRequireQueryDao.getCreditInfo(map, 1);
		//List homeCreditList = this.fundsRequireQueryDao.getCreditInfo(map, 2);
		List bankAcceptanceList = this.fundsRequireQueryDao.getCreditInfo(map, "07");
		List homeCreditList = this.fundsRequireQueryDao.getCreditInfo(map, "06");
		List draftList = this.fundsRequireQueryDao.getDraftInfo(map);
		List draftOutList = this.fundsRequireQueryDao.getDraftOutInfo(map);
		List cnyShortLoanList = this.fundsRequireQueryDao.getCNYLoanInfo(map, 1);
		List cnyLongLoanList = this.fundsRequireQueryDao.getCNYLoanInfo(map, 2);
		List foreignLoanList = this.fundsRequireQueryDao.getForeignInfo(map);
		GregorianCalendar calendar =  new GregorianCalendar(Integer.valueOf(map.get("year")),Integer.valueOf(map.get("month"))-1,1);
		int days = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		List<Map> rtn = new ArrayList();
		Map m;
		String date = map.get("year")+map.get("month");
		if(Integer.valueOf(map.get("month")).intValue()<10)
			date = map.get("year")+"0"+map.get("month");
		String sapDate = map.get("year")+map.get("month");
		if(Integer.valueOf(map.get("month")).intValue()<10)
			sapDate = map.get("year")+"0"+map.get("month");
		Double paymentValue= 0.0;
		Double bankAcceptanceValue= 0.0;
		Double homeCreditValue= 0.0;
		Double cnyShortLoanValue= 0.0;
		Double cnyLongLoanValue= 0.0;
		Double foreignLoanValue= 0.0;
		Double draftValue = 0.0;
		Double draftOutValue = 0.0;
		Double daySumValue= 0.0;
		Double sum=0.0;
		
		BigDecimal payment;
		BigDecimal bankAcceptance;
		BigDecimal homeCredit;
		BigDecimal cnyShortLoan;
		BigDecimal cnyLongLoan;
		BigDecimal foreignLoan;
		BigDecimal draft;
		BigDecimal draftOut;
		Map bapi;
		String temp;
		for(int i=1;i<days+1;i++){
			m = new HashMap();
			temp="";
			temp = date+i;
			if(i<10)
				temp = date+"0"+i;
			payment = rtnValue(temp, paymentList);
			bankAcceptance = rtnValue(temp,bankAcceptanceList);
			homeCredit = rtnValue(temp,homeCreditList);
			cnyShortLoan = rtnValue(temp,cnyShortLoanList);
			cnyLongLoan = rtnValue(temp,cnyLongLoanList);
			foreignLoan = rtnValue(temp,foreignLoanList);
			draft = rtnValue(temp,draftList);
			draftOut = rtnValue(temp,draftOutList);
			
			if(payment!=null || 
					bankAcceptance!=null ||
						homeCredit!=null ||
							cnyShortLoan !=null||
								cnyLongLoan!=null||
									foreignLoan!=null||draft!=null||draftOut!=null){
				paymentValue +=(payment!=null?payment.doubleValue():0);
				bankAcceptanceValue +=(bankAcceptance!=null?bankAcceptance.doubleValue():0);
				homeCreditValue +=(homeCredit!=null?homeCredit.doubleValue():0);
				cnyShortLoanValue +=(cnyShortLoan!=null?cnyShortLoan.doubleValue():0);
				cnyLongLoanValue +=(cnyLongLoan!=null?cnyLongLoan.doubleValue():0);
				foreignLoanValue +=(foreignLoan!=null?foreignLoan.doubleValue():0);
				draftValue += (draft!=null?draft.doubleValue():0);
				draftOutValue += (draftOut!=null?draftOut.doubleValue():0);
				daySumValue = (payment!=null?payment.doubleValue():0)+
							(bankAcceptance!=null?bankAcceptance.doubleValue():0)+
							(homeCredit!=null?homeCredit.doubleValue():0)+
							(cnyShortLoan!=null?cnyShortLoan.doubleValue():0)+
							(cnyLongLoan!=null?cnyLongLoan.doubleValue():0)+
							(foreignLoan!=null?foreignLoan.doubleValue():0)+
							(draft!=null?draft.doubleValue():0)+
							(draftOut!=null?draftOut.doubleValue():0);
				sum = sum+daySumValue;
				m.put("payment_value",payment);
				m.put("bankAcceptance_value",bankAcceptance);
				m.put("homeCredit_value",homeCredit);
				m.put("cnyShortLoan_value",cnyShortLoan);
				m.put("cnyLongLoan_value",cnyLongLoan);
				m.put("foreignLoan_value",foreignLoan);
				m.put("draft_value", draft);
				m.put("draftOut_value", draftOut);
				m.put("day_sum", rtnHalfUpValue(daySumValue));
				if(i<10)
					m.put("date",date+"0"+i);
				else
					m.put("date",date+i);
				rtn.add(m);
			}
		}
		m = new HashMap();
		m.put("date", "合计");
		m.put("payment_value", rtnHalfUpValue(paymentValue));
		m.put("bankAcceptance_value", rtnHalfUpValue(bankAcceptanceValue));
		m.put("homeCredit_value", rtnHalfUpValue(homeCreditValue));
		m.put("cnyShortLoan_value", rtnHalfUpValue(cnyShortLoanValue));
		m.put("cnyLongLoan_value", rtnHalfUpValue(cnyLongLoanValue));
		m.put("foreignLoan_value", rtnHalfUpValue(foreignLoanValue));
		m.put("draft_value", rtnHalfUpValue(draftValue));
		m.put("draftOut_value", rtnHalfUpValue(draftOutValue));
		m.put("day_sum",rtnHalfUpValue(sum));
		bapi = this.getSAPBapiValue();
		if(bapi!=null){
			m.put("bapi_receive",bapi.get("HSLVT1"));
			m.put("bapi_pay",bapi.get("HSLVT2"));
		}

		rtn.add(m);
		return rtn;
	}
	/**
	 * 
	 * @param bd
	 * @return
	 */
	private BigDecimal rtnHalfUpValue(Double bd){
		BigDecimal value = new BigDecimal(bd);
		value = value.divide(new BigDecimal(1),2,RoundingMode.HALF_UP);
		return value;
	}
	/**
	 * 
	 * @param date
	 * @param list
	 * @return
	 */
	private BigDecimal rtnValue(String date,List<Map> list){
		for(Map m:list){
			if(date.equals(m.get("time_value")))
				return ((BigDecimal)m.get("sum_value")).divide(new BigDecimal(1),2,RoundingMode.HALF_UP);
		}
		return null;
	}
	/**
	 * 
	 * @param date
	 * @return
	 */
	public List rtnImportPaymentInfo(String date) {
		return this.fundsRequireQueryDao.getImportPaymentInfo(date);
	}
	/**
	 * 
	 * @param date
	 * @return
	 */
	private Map getSAPBapiValue(){
		Map map = new HashMap();
//		map.put("BUKRS", "2100");//公司代码
//		map.put("DATUM", date);//日期
		Map data = ExtractSapData.getDicData("ZF_GET_ACCOUNT",map);
		return data;
	}
	/**
	 * 
	 * @param parameter
	 * @param parameter2
	 * @return
	 */
	public List rtn4TableInfo(String date, String classify) {
		/*if("2".equals(classify)){
			return this.fundsRequireQueryDao.getAcceptanceOrHomeCreidtInfo(date,1);//银行承兑
		}else if("3".equals(classify)){
			return this.fundsRequireQueryDao.getAcceptanceOrHomeCreidtInfo(date,2);//国内信用证
		}else */if("4".equals(classify)){
			return this.fundsRequireQueryDao.getCnyShortOrLongInfo(date,1);//人民币短期
		}else if("5".equals(classify)){
			return this.fundsRequireQueryDao.getCnyShortOrLongInfo(date,2);//人民币长期
		}
		return null;
	}
	/**
	 * 
	 * @param parameter
	 * @return
	 */
	public List rtnForeignShortLoanInfo(String date) {
		return this.fundsRequireQueryDao.getForeignShortLoanInfo(date);
	}
	
	public List rtnDraftBillInfo(String date) {
		return this.fundsRequireQueryDao.getDraftBillInfo(date);
	}
	
	public List rtnDraftOutBillInfo(String date) {
		return this.fundsRequireQueryDao.getDraftOutBillInfo(date);
	}
	
	public List rtnBankAcceptancInfo(String date) {
		return this.fundsRequireQueryDao.getBankAcceptancInfo(date);
	}
	
	public List rtnHomeCreditInfo(String date) {
		return this.fundsRequireQueryDao.getHomeCreditInfo(date);
	}
}
