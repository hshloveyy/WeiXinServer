package com.infolion.xdss3.ageAnalysis.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.infolion.platform.dpframework.core.dao.BaseJdbcDao;
import com.infolion.platform.dpframework.util.ExBeanUtils;
import com.infolion.platform.dpframework.util.StringUtils;
import com.infolion.platform.util.DateUtils;
import com.infolion.xdss3.ageAnalysis.domain.AgeInvoice;
import com.infolion.xdss3.ageAnalysis.domain.AgeVoucher;
import com.infolion.xdss3.voucher.domain.Voucher;
import com.infolion.xdss3.voucheritem.domain.VoucherItem;

@Repository
public class AgeAnalysisJdbcDao extends BaseJdbcDao {
	public void delAllByDate(String date){
		String sql="delete from YAGEANALYSIS where augdt='"+ date +"'";
		this.getJdbcTemplate().execute(sql);
	}
	/**
	 * 
	  * @param date 日期格式为yyyymmdd  必填
     * @param companyno 公司代码
     * @param depid 业务范围   
     * @param customerNo 供应商NO  
     * @param subjectcode 总账科目代码  必填
	 */
	public void delByCus(String date,String companyno,String depid,String customerNo,String subjectcode,String businessstate,String customerType){
		String sql="delete from YAGEANALYSIS where augdt='"+ date +"' and subjectcode  like '"+ subjectcode+ "' ";
		if(!StringUtils.isNullBlank(companyno)){
			sql = sql + " and bukrs in ("+ companyno+ ") ";
		}
		if(!StringUtils.isNullBlank(depid)){
			sql = sql + " and gsber in ("+ depid+ ") ";
		}
		if(!StringUtils.isNullBlank(customerNo)){
			sql = sql + " and customerno in ("+ customerNo+ ")";
		}
		if(!StringUtils.isNullBlank(businessstate)){
    		sql =sql + " and  vbeltype in ("+ businessstate +") ";
    	}  
		if(!StringUtils.isNullBlank(customerType)){
    		sql =sql + " and  customertype='"+ customerType +"' ";
    	}
		
		this.getJdbcTemplate().execute(sql);
	}
	
	public void delByUsername(String username){
		String sql="delete from YAGEANALYSIS where username='"+ username +"'";
		this.getJdbcTemplate().execute(sql);
	}
	/**
	 * 
	 * @param zdate
	 * @return
	 */
	public List<VoucherItem> getVoucherItem(String zdate)
	{
		List<VoucherItem> list = new ArrayList<VoucherItem>();
		String strSql = "";
		if(!StringUtils.isNullBlank(zdate)){
			strSql = "select * from yvoucheritem a, yvoucher b where a.voucherid = b.voucherid and b.importdate >= '"+zdate + "' and b.importdate <  to_char(sysdate,'yyyymmdd') and b.bstat !='A'  and trim(b.voucherno) is not null AND (a.CHECKCODE BETWEEN '01' AND '19' OR a.CHECKCODE BETWEEN '21' AND '39')";
		}else{
			strSql = "select * from yvoucheritem a, yvoucher b where a.voucherid = b.voucherid and  b.bstat !='A'  and trim(b.voucherno) is not null AND (a.CHECKCODE BETWEEN '01' AND '19' OR a.CHECKCODE BETWEEN '21' AND '39')";
		}
		
		log.debug("根据业务ID取得凭证明细:" + strSql);
		
		List<Map> rowList = this.getJdbcTemplate().queryForList(strSql);
		for (Map map : rowList)
		{
			VoucherItem voucherItem = new VoucherItem();
			ExBeanUtils.setBeanValueFromMap(voucherItem, map);
			Voucher voucher = new Voucher();
			ExBeanUtils.setBeanValueFromMap(voucher, map);
			voucherItem.setVoucher(voucher);	
			list.add(voucherItem);			
		}
		return list;
	}
	/**
	 * 用在处理数据量大，可以先取一部分数据。
	 * @param zdate
	 * @param to
	 * @return
	 */
	public List<VoucherItem> getVoucherItem(String zdate,String from,String to)
	{
		List<VoucherItem> list = new ArrayList<VoucherItem>();
		String strSql = "";
		if(!StringUtils.isNullBlank(zdate)){
			strSql = "select * from yvoucheritem a, yvoucher b where a.voucherid = b.voucherid and b.importdate  >= TO_CHAR(TO_DATE('"+zdate+"', 'yyyymmdd') + "+from+", 'yyyymmdd')  and  b.importdate <  TO_CHAR(TO_DATE('"+zdate+"', 'yyyymmdd') + "+to+", 'yyyymmdd') and b.bstat !='A'  and trim(b.voucherno) is not null AND (a.CHECKCODE BETWEEN '01' AND '19' OR a.CHECKCODE BETWEEN '21' AND '39')";
		}else{
			strSql = "select * from yvoucheritem a, yvoucher b where a.voucherid = b.voucherid and  b.bstat !='A'  and trim(b.voucherno) is not null AND (a.CHECKCODE BETWEEN '01' AND '19' OR a.CHECKCODE BETWEEN '21' AND '39')";
		}
		
		log.debug("根据业务ID取得凭证明细:" + strSql);
		
		List<Map> rowList = this.getJdbcTemplate().queryForList(strSql);
		for (Map map : rowList)
		{
			VoucherItem voucherItem = new VoucherItem();
			ExBeanUtils.setBeanValueFromMap(voucherItem, map);
			Voucher voucher = new Voucher();
			ExBeanUtils.setBeanValueFromMap(voucher, map);
			voucherItem.setVoucher(voucher);	
			list.add(voucherItem);			
		}
		return list;
	}
	/**
	 * 
	 * @param zdate
	 * @return
	 */
	public List<Map> getInvoices(String zdate){
		String strSql = "";
		if(!StringUtils.isNullBlank(zdate)){
			zdate = zdate.substring(0,8);
			strSql = "SELECT REPLACE(A.RECEIPTTIME,'-','')  AS RECEIPTTIME,A.*,B.*  FROM T_BILL_APPLY A, T_CONTRACT_SALES_INFO B WHERE A.CONTRACT_SALES_ID = B.CONTRACT_SALES_ID   AND TRIM(A.SAP_BILL_NO) IS NOT NULL   AND A.APPROVED_TIME >= to_char(to_date('" + zdate +"', 'yyyymmdd') ,'yyyy-mm-dd') and A.APPROVED_TIME < to_char(sysdate,'yyyy-mm-dd') and TRIM(A.approved_time) IS NOT NULL";
		}else{
			strSql = "SELECT REPLACE(A.RECEIPTTIME,'-','')  AS RECEIPTTIME,A.*,B.*  FROM T_BILL_APPLY A, T_CONTRACT_SALES_INFO B WHERE A.CONTRACT_SALES_ID = B.CONTRACT_SALES_ID   AND TRIM(A.SAP_BILL_NO) IS NOT NULL and TRIM(A.approved_time) IS NOT NULL";
		}
		List<Map> rowList = this.getJdbcTemplate().queryForList(strSql);
		
		return rowList;
	}
	
	public List<Map> getExport(String zdate){
		String strSql = "";
		if(!StringUtils.isNullBlank(zdate)){
			zdate = zdate.substring(0,8);
			strSql = "SELECT REPLACE(A.MAKE_INVOICE_TIME,'-','')  AS MAKE_INVOICE_TIME,A.SAP_RETURN_NO  FROM T_SHIP_INFO A, T_CONTRACT_SALES_INFO B WHERE A.CONTRACT_SALES_ID = B.CONTRACT_SALES_ID   AND TRIM(A.SAP_RETURN_NO) IS NOT NULL   AND TRIM(A.make_invoice_time) IS NOT NULL AND B.trade_type IN ('2','4','5') AND is_number(A.SAP_RETURN_NO) <>0  AND A.APPROVED_TIME >= to_char(to_date('" + zdate +"', 'yyyymmdd') ,'yyyy-mm-dd') and A.APPROVED_TIME < to_char(sysdate,'yyyy-mm-dd') and TRIM(A.approved_time) IS NOT NULL";
		}else{
			strSql = "SELECT REPLACE(A.MAKE_INVOICE_TIME,'-','')  AS MAKE_INVOICE_TIME,A.SAP_RETURN_NO  FROM T_SHIP_INFO A, T_CONTRACT_SALES_INFO B WHERE A.CONTRACT_SALES_ID = B.CONTRACT_SALES_ID   AND TRIM(A.SAP_RETURN_NO) IS NOT NULL   AND TRIM(A.make_invoice_time) IS NOT NULL AND B.trade_type IN ('2','4','5') AND is_number(A.SAP_RETURN_NO) <>0 and TRIM(A.approved_time) IS NOT NULL";
		}
		List<Map> rowList = this.getJdbcTemplate().queryForList(strSql);
		
		return rowList;
	}
}
