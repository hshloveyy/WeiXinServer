package com.infolion.xdss3.foreignExchange.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
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
import com.infolion.xdss3.customerDrawback.dao.CustomerRefundItemJdbcDao;
import com.infolion.xdss3.foreignExchange.dao.ForeignExchangeJdbcDao;
import com.infolion.xdss3.foreignExchange.domain.ForeignExchange;
import com.infolion.xdss3.voucher.domain.Voucher;
import com.infolion.xdss3.voucher.service.VoucherService;
import com.infolion.xdss3.voucheritem.domain.VoucherItem;

import com.sap.mw.jco.IFunctionTemplate;
import com.sap.mw.jco.JCO;

@Service
public class ForeignExchangeService {
	protected static Log log = LogFactory.getFactory().getLog(ForeignExchangeService.class);
	private static String ZFI016 ="ZF_016";
	
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
	private  ForeignExchangeJdbcDao foreignExchangeJdbcDao;
	/**
	 * @param foreignExchangeJdbcDao the foreignExchangeJdbcDao to set
	 */
	public void setForeignExchangeJdbcDao(
			ForeignExchangeJdbcDao foreignExchangeJdbcDao) {
		this.foreignExchangeJdbcDao = foreignExchangeJdbcDao;
	}
	
	
	@Autowired
	private VoucherService voucherService;

	/**
	 * @param voucherService
	 *            the voucherService to set
	 */
	public void setVoucherService(VoucherService voucherService)
	{
		this.voucherService = voucherService;
	}
	
	/**
	 * 
	 * @param p_bukrs 公司代码
	 * @param p_gsber  业务范围
	 * @param p_gjahr 会计年度
	 * @param p_monat 会计期间
	 * @param p_waers 货币码
	 * @param s_hkont_st 总分类帐帐目 开始
	 * @param s_hkont_end 总分类帐帐目 结束
	 * @param s_kunnr_st客户编号1开始
	 * @param s_kunnr_end客户编号1结束
	 * @param s_lifnr_st供应商或债权人的帐号开始
	 * @param s_lifnr_end供应商或债权人的帐号结束
	 * @param ck_zz单一字符标识(输出格式为总帐科目不包括外币资金)
	 * @param ck_kh单一字符标识(输出格式为客户)
	 * @param ck_gys单一字符标识(输出格式为供应商)
	 * @param ck_wbzj单一字符标识(输出格式为外币资金)
	 * @return
	 */
	public List<Map<String, String>> _executeRfc(String p_bukrs,String p_gsber ,String p_gjahr,String p_monat,String p_waers,String s_hkont_st
	,String s_hkont_end,String s_kunnr_st ,String s_kunnr_end,String s_lifnr_st,String s_lifnr_end,String ck_zz,String ck_kh,String ck_gys,String ck_wbzj	)
	{
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();

		JCO.Table returnDatas = null;

		ConnectManager manager = ConnectManager.manager;
		manager.getClient();
		JCO.Client client = null;
		try
		{
			IFunctionTemplate ftemplate = manager.repository.getFunctionTemplate(ZFI016);
			if (ftemplate != null)
			{
				JCO.Function function = ftemplate.getFunction();
				client = JCO.getClient(manager.poolName);
				JCO.ParameterList input = function.getImportParameterList();
				input.setValue(p_bukrs, "P_BUKRS");			
				input.setValue(p_gjahr, "P_GJAHR");
				input.setValue(p_monat, "P_MONAT");
				input.setValue(p_waers, "P_WAERS");	
				if(StringUtils.isNotBlank(p_gsber))	input.setValue(p_gsber, "P_GSBER");
				if(StringUtils.isNotBlank(ck_zz))input.setValue(ck_zz, "CK_ZZ");
				if(StringUtils.isNotBlank(ck_kh))input.setValue(ck_kh, "CK_KH");			
				if(StringUtils.isNotBlank(ck_gys))input.setValue(ck_gys, "CK_GYS");
				if(StringUtils.isNotBlank(ck_wbzj))input.setValue(ck_wbzj, "CK_WBZJ");
				if(StringUtils.isNotBlank(s_hkont_st))input.setValue(s_hkont_st, "S_HKONT_ST");			
				if(StringUtils.isNotBlank(s_hkont_end))input.setValue(s_hkont_end, "S_HKONT_END");
				if(StringUtils.isNotBlank(s_kunnr_st))input.setValue(s_kunnr_st, "S_KUNNR_ST");
				if(StringUtils.isNotBlank(s_kunnr_end))input.setValue(s_kunnr_end, "S_KUNNR_END");			
				if(StringUtils.isNotBlank(s_lifnr_st))input.setValue(s_lifnr_st, "S_LIFNR_ST");
				if(StringUtils.isNotBlank(s_lifnr_end))input.setValue(s_lifnr_end, "S_LIFNR_END");
				
				client.execute(function);

				// 新增的数据集合
				returnDatas = function.getTableParameterList().getTable("IT_ITEM_OUT");

				list = ExtractSapData.getList(returnDatas);

			}
			else
			{
				log.error("----------------取得zfi016发生错误：目标系统上不存在RFC" + ZFI016);
//				list = null;
			}
		}
		catch (Exception ex)
		{
			log.error("----------------取得zfi016调汇数据发生错误" + ex);
//			list = null;
		}
		finally
		{
			manager.cleanUp();
		}

		return list;
	}
	/**
	 * 生成调汇凭证
	 * @param list
	 * @return
	 */
	public String genFEVoucher(List<Map<String, String>> list,String fiyear,String fiperiod,String gsber,String bukrs,String businessid2){
		List<ForeignExchange> feList = new ArrayList<ForeignExchange>();
		Map<String,List<ForeignExchange>> femap = new HashMap<String,List<ForeignExchange>>();
		String businessid ="";
		if(StringUtils.isNullBlank(businessid2)){
			businessid = CodeGenerator.getUUID();
		}else{
			Voucher v=this.voucherService.getVoucherByBusinessId(businessid2, "31");
//			如果已经生成凭证了，重新生成新的ID
			if(null == v || StringUtils.isNullBlank(v.getVoucherno())){
				businessid=businessid2;
			}else{				
				businessid = CodeGenerator.getUUID();
			}
		}
		for (Map map : list){
			ForeignExchange foreignExchange = new ForeignExchange();
			foreignExchange.setBelnr(map.get("BELNR").toString());
			foreignExchange.setBldat(map.get("BLDAT").toString());
			foreignExchange.setBukrs(map.get("BUKRS").toString());
			foreignExchange.setChaer(map.get("CHAER").toString());
			foreignExchange.setDmbtr(map.get("DMBTR").toString());
			foreignExchange.setDmbtrnew(map.get("DMBTRNEW").toString());
			foreignExchange.setFlag(map.get("FLAG").toString());
			foreignExchange.setGsber(map.get("GSBER").toString());
			foreignExchange.setKemu(map.get("KEMU").toString());
			foreignExchange.setRatenew(map.get("RATENEW").toString());
			foreignExchange.setRateold(map.get("RATEOLD").toString());
			foreignExchange.setShkzg(map.get("SHKZG").toString());
			foreignExchange.setText(map.get("TEXT").toString());
			foreignExchange.setTflag(map.get("TFLAG").toString());
			foreignExchange.setWaers(map.get("WAERS").toString());
			foreignExchange.setWrbtr(map.get("WRBTR").toString());
			//根据币别分组
			if(femap.isEmpty()){
				feList = new ArrayList<ForeignExchange>();
				feList.add(foreignExchange);
				femap.put(foreignExchange.getWaers(), feList);
			}else{
				feList=(List<ForeignExchange>) femap.get(foreignExchange.getWaers());
				if(null == feList || feList.isEmpty()){
					feList = new ArrayList<ForeignExchange>();
					feList.add(foreignExchange);
					femap.put(foreignExchange.getWaers(), feList);
				}else{
					feList.add(foreignExchange);
					femap.put(foreignExchange.getWaers(), feList);
				}			
			}			
		}
		List<Voucher> voucherList = new ArrayList<Voucher>();
		 for(String key : femap.keySet()){
			  
			 List<ForeignExchange> feLi= femap.get(key);
			 ForeignExchange fe=feLi.get(0);
			 
			 String businesstype="31";
			 String vouchertype="SA";
//			 String bukrs=fe.getBukrs();
			 String exchangerate =fe.getRatenew();
			 String currencytext=fe.getWaers();
//			 String gsber=fe.getGsber();
			 String voucherclass="31";
			 String text=fiyear + fiperiod+"调汇";
			 String taxCode="";
			 Voucher voucher=this.getVoucher(businessid, businesstype, vouchertype, bukrs, exchangerate, currencytext, gsber, voucherclass, fiperiod, fiyear);
			 Set<VoucherItem> itemSet = new HashSet<VoucherItem>();
			 //计算总汇损总金额
			 BigDecimal total = new BigDecimal("0");
			 int i=0;
			 for(ForeignExchange fex :feLi){
				 i++;	
				 String skont= "";
				 String customer ="";
				 String supplier="";
				 BigDecimal subtractVlaue =new BigDecimal(fex.getChaer());
				 int flag =Integer.parseInt(fex.getFlag());
				 //小6是总账
				 if(flag<6){					 
					  skont= fex.getKemu();
					  total=total.add(subtractVlaue);
				 }
				 //客户
				 if((flag>=6 && flag <=8) || (flag >=12 && flag <=18) || flag==27 || flag==28){
					  customer = fex.getKemu();
					  total=total.add(subtractVlaue);
				 }
				 //供应商,正数变负数
				 if((flag>=9 && flag <=11) || (flag >=19 && flag <=26)){
					  supplier= fex.getKemu();
					  total=total.add(subtractVlaue);
				 }
				
						
				
				
				 System.out.println("c: "+ i +" " + fex.getChaer() + "sub " + subtractVlaue  + "total :=" + total);
				 String currency=fex.getWaers();				 
				 String strType="1";
				 String rownumber=String.valueOf(i);
				 
				 VoucherItem item=this.getProfitAndLossVoucherItem(bukrs, customer, supplier, skont, gsber, text, subtractVlaue, 
						 currency, taxCode, strType, rownumber,fex.getFlag());
				 item.setVoucher(voucher);
				 itemSet.add(item);
			 }
			 //增加一条汇损行项目
			 String strType="2";
			 i++;
			 String rownumber=String.valueOf(i);
			 VoucherItem item=this.getProfitAndLossVoucherItem(bukrs, "", "", "", gsber, text, total, currencytext, taxCode, strType, rownumber,"");
//			成本中心都挂2199
			 String costcenter ="";
//			 String gsber2="2199";
//			 if(!"2100".equals(bukrs))gsber2=gsber;
//			 是2100公司成本中心挂本部门,其他公司 不变
			 if("2100".equals(bukrs)){
				  costcenter=foreignExchangeJdbcDao.getcostcenter(gsber, foreignExchangeJdbcDao.CW);
			 }else{
			  costcenter=this.getCostcenter(bukrs);
			 }
			 item.setCostcenter(costcenter);
//			 if("2100".equals(bukrs)){
//				 item.setDepid("2199");
//				 item.setGsber("2199");
//			 }
			 item.setVoucher(voucher);
			 itemSet.add(item);
			 
			 voucher.setVoucherItem(itemSet);
			 voucherList.add(voucher);
			 
			 this.voucherService._saveOrUpdate(voucher, null, BusinessObjectService.getBusinessObject("Voucher"));
		 }
		return businessid;
	}
	
	public Voucher getVoucher(String businessid,String businesstype,String vouchertype,String bukrs,String exchangerate,
			String currencytext,String gsber,String voucherclass,String fiperiod,String fiyear){
		Voucher voucher = new Voucher();

		// 添加凭证抬头数据
		voucher.setBusinessid(businessid);
		voucher.setBusinesstype(businesstype); // 
		voucher.setVouchertype(vouchertype);
		voucher.setCompanycode(bukrs);
		voucher.setExchangerate(new BigDecimal(exchangerate));
		voucher.setCurrency(currencytext);
		voucher.setGsber(gsber);

		String accountdate = String.valueOf(daysCount(Integer.parseInt(fiyear),Integer.parseInt(fiperiod)));
		voucher.setCheckdate(fiyear + fiperiod + accountdate);
		voucher.setVoucherclass(voucherclass);
		voucher.setFiperiod(fiperiod);// 会计期间
		voucher.setFiyear(fiyear); // 会计年度
		voucher.setImportdate(DateUtils.getCurrDate(DateUtils.DB_STORE_DATE).substring(0, 8));
		// 输入日期
		UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
		voucher.setImporter(userContext.getUser().getUserName()); // 输入者
		voucher.setPreparer(userContext.getUser().getUserName()); // 预制者
		voucher.setVoucherdate(fiyear + fiperiod + accountdate); // 凭证日期
		voucher.setVouchertext(fiyear + fiperiod + accountdate + "调汇"); // 凭证抬头文本
		return voucher;
	}
	/**
	 * 取得某月的最后一天
	 * 说明一下，日期类中的月份是0-11比这际月份少1.
     *这样你调置 2010 2 这其实是 2010 年 3月份。这个月分的0天是那一天呢，就是 3月1号减一天
     *即上个月的最后一天。
	 * @param year
	 * @param month
	 * @return
	 */
	
	public  int daysCount(int year, int month) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month);
		cal.set(Calendar.DATE, 0);
		return cal.get(Calendar.DATE);
	}
	/**
	 * 得到有汇损益的行项目信息
	 * *  @param parameterObject 参数对象（以下是要用到的属性）
	 * 
	 * @param bukrs 公司代码
	 *  @param customer 客户号
	 *  @param custom_htext 客户文本
	 *  @param gsber 业务范围
	 *  @param text 文本
	 * @param subtractVlaue 本位币相差的金额
	 * @param currency 币别（2600为HKD,其他为CNY）
	 * @param strType 
	 *  @param rownumber 行项目
	 *  
	 * @return
	 */
	public VoucherItem getProfitAndLossVoucherItem(String bukrs,String customer,String supplier,String skont,String gsber,String text,
			BigDecimal subtractVlaue, String currency,String taxCode, String strType,String rownumber,String flag){
		VoucherItem voucherItem = new VoucherItem();
		String subject = "";
		
		
		voucherItem.setTaxcode(taxCode);
		rownumber = StringUtils.leftPad(rownumber, 3, '0');
		voucherItem.setRownumber(rownumber);
		if (strType.equals("1"))
		{
			
			// 科目
			
			// 科目说明
			// String Subjectdescribe =
			// this.voucherService.getSupplierDescByCustomerId(customSingleClear.getCustom(),
			// bukrs);
			if(!StringUtils.isNullBlank(customer)){
				voucherItem.setSubject(customer);
				String custom_htext = this.customerRefundItemJdbcDao.getCustomerDescByCustomerId(customer,bukrs);
//				客户
				voucherItem.setSubjectdescribe(custom_htext);
				if (subtractVlaue.signum() == 1){
					// 记帐码
//					voucherItem.setCheckcode("01");
					voucherItem.setDebitcredit("S");
				}
				else{
					// 记帐码
//					voucherItem.setCheckcode("15");
					voucherItem.setDebitcredit("H");
				}
				
//				subject = this.customerRefundItemJdbcDao.getSubjectByCustomer(customer,bukrs);
				subject=getSubject(flag);
				String subjectname = this.customerRefundItemJdbcDao.getSubjectNameById(subject,bukrs);
				voucherItem.setControlaccount(subject);
				voucherItem.setCaremark(subjectname);
				Map<String,String> map = this.getCheckCode(customer, subject, voucherItem.getDebitcredit(), "D");
				voucherItem.setCheckcode(map.get("checkcode"));
				voucherItem.setGlflag(map.get("glflag"));
			}
			if(!StringUtils.isNullBlank(supplier)) {
				voucherItem.setSubject(supplier);
				String supplier_htext =this.customerRefundItemJdbcDao.getSupplierDescByCustomerId(supplier,bukrs);
//				供应商
				voucherItem.setSubjectdescribe(supplier_htext);
				if (subtractVlaue.signum() == -1)
				{
					// 记帐码
//					voucherItem.setCheckcode("31");
					voucherItem.setDebitcredit("H");
				}
				else
				{
					// 记帐码
//					voucherItem.setCheckcode("25");
					voucherItem.setDebitcredit("S");
				}
//				subject = this.customerRefundItemJdbcDao.getSubjectBySuppliers(supplier,bukrs);
				subject=getSubject(flag);
				String subjectname = this.customerRefundItemJdbcDao.getSubjectNameById(subject,bukrs);
				voucherItem.setControlaccount(subject);
				voucherItem.setCaremark(subjectname);
				Map<String,String> map = this.getCheckCode(supplier, subject, voucherItem.getDebitcredit(), "K");
				voucherItem.setCheckcode(map.get("checkcode"));
				voucherItem.setGlflag(map.get("glflag"));
			}
			//总账
			if(!StringUtils.isNullBlank(skont)) {
				voucherItem.setSubject(skont);
				if (subtractVlaue.signum() == -1){
					// 记帐码
					voucherItem.setCheckcode("50");
					voucherItem.setDebitcredit("H");
				}else{
					// 记帐码
					voucherItem.setCheckcode("40");
					voucherItem.setDebitcredit("S");				
				}
				// 科目说明
				voucherItem.setSubjectdescribe(this.customerRefundItemJdbcDao.getSubjectNameById(skont,bukrs));
				voucherItem.setControlaccount(voucherItem.getSubject());
				voucherItem.setCaremark(voucherItem.getSubjectdescribe());
//				如果是现金，银行存款，外币存款和其他货币资金要写入现金流401
				if("1".equals(flag) || "2".equals(flag) || "3".equals(flag) || "4".equals(flag) ){
					voucherItem.setCashflowitem("401");
				}
			}			
			
		}

		if (strType.equals("2")){
			

				if (subtractVlaue.signum() == 1){
					// 记帐码
					voucherItem.setCheckcode("50");
					voucherItem.setDebitcredit("H");
					String su="6603050201";
					if("2600".equals(bukrs)){
						// 科目
						su = "6603050201";
					}
					// 科目
					voucherItem.setSubject(su);
					// 科目说明
					voucherItem.setSubjectdescribe(this.customerRefundItemJdbcDao.getSubjectNameById(su,bukrs));
					voucherItem.setControlaccount(voucherItem.getSubject());
					voucherItem.setCaremark(voucherItem.getSubjectdescribe());
				}else{
					// 记帐码
					voucherItem.setCheckcode("40");
					voucherItem.setDebitcredit("S");
					String su ="6603050101";
					if("2600".equals(bukrs)){
						// 科目
						su = "6603050101";
					}
					// 科目
					voucherItem.setSubject(su);
					// 科目说明
					voucherItem.setSubjectdescribe(this.customerRefundItemJdbcDao.getSubjectNameById(su,bukrs));
					voucherItem.setControlaccount(voucherItem.getSubject());
					voucherItem.setCaremark(voucherItem.getSubjectdescribe());
				}
				
			
		

		}
		// 货币
		voucherItem.setCurrency(currency);
		// 货币金额
		voucherItem.setAmount(new BigDecimal("0"));
		// 本位币金额
		voucherItem.setAmount2(subtractVlaue.abs().setScale(2, BigDecimal.ROUND_HALF_UP));
		// 部门
		voucherItem.setDepid(gsber);
		// 部门
		voucherItem.setGsber(gsber);
		// 文本
		voucherItem.setText(text);
		return voucherItem;
	}
//	记账码及特别总账标识,根据客户供应商的单号去掉0的头一位数，统驭科目,借代标识 ,客户供应商标识，客户D，供应商K 取得记账号,特殊总账标识，
	
	public Map<String,String> getCheckCode(String agkoa,String skont,String debitcredit,String agkon){	
		//去前导0
		String no=StringUtils.stripStart(agkoa, "0");		
		return this.foreignExchangeJdbcDao.getCheckCode(no.substring(0,1), skont, debitcredit, agkon);
	}
//	根据SAP ZF_016 中的自定义类型，对应科目
	public String getSubject(String flag){
		String subject="";
		if("6".equals(flag)){
			subject="1122010003";
		}
		if("7".equals(flag)){
			subject="1122040001";
		}
		if("8".equals(flag)){
			subject="2203990001";
		}
		
		if("9".equals(flag)){
			subject="1123010003";
		}
		if("10".equals(flag)){
			subject="2202010003";
		}
		if("11".equals(flag)){
			subject="2202040002";
		}
		if("12".equals(flag)){
			subject="1241010001";
		}
		if("13".equals(flag)){
			subject="1241010002";
		}
		if("14".equals(flag)){
			subject="1241010003";
		}
		if("15".equals(flag)){
			subject="1241010004";
		}
		if("16".equals(flag)){
			subject="1241020001";
		}
		if("17".equals(flag)){
			subject="1241030001";
		}
		if("18".equals(flag)){
			subject="1241040001";
		}
		if("19".equals(flag)){
			subject="2202020001";
		}
		if("20".equals(flag)){
			subject="1241010001";
		}
		if("21".equals(flag)){
			subject="1241010002";
		}
		if("22".equals(flag)){
			subject="1241010003";
		}
		if("23".equals(flag)){
			subject="1241010004";
		}
		if("24".equals(flag)){
			subject="1241020001";
		}
		if("25".equals(flag)){
			subject="1241030001";
		}
		if("26".equals(flag)){
			subject="1241040001";
		}
		if("27".equals(flag)){
			subject="1221010001";
		}
		if("28".equals(flag)){
			subject="1221020001";
		}
		return subject;
	}
//	成本中心
	public String getCostcenter(String bukrs){
		String costcenter="";
		if("2100".equals(bukrs)){
			costcenter="0021002022";
		}
		if("2400".equals(bukrs)){
			costcenter="0024002013";
		}
		if("2600".equals(bukrs)){
			costcenter="0026002013";
		}
		if("3000".equals(bukrs)){
			costcenter="0030002013";
		}
		if("3010".equals(bukrs)){
			costcenter="0030101013";
		}
		if("2700".equals(bukrs)){
			costcenter="0027002013";
		}
		if("8100".equals(bukrs)){
			costcenter="0081002043";
		}
		if("1001".equals(bukrs)){
			costcenter="0010011122";
		}
		return costcenter;
	}
	
	
}
