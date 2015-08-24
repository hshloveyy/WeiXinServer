/**
 * 
 */
package com.infolion.xdss3.ageAnalysis.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.infolion.platform.dpframework.core.dao.BaseJdbcDao;
import com.infolion.platform.dpframework.util.ExBeanUtils;
import com.infolion.platform.dpframework.util.StringUtils;
import com.infolion.platform.util.DateUtils;
import com.infolion.xdss3.ageAnalysis.domain.ClearItem;
import com.infolion.xdss3.voucher.domain.Voucher;
import com.infolion.xdss3.voucheritem.domain.VoucherItem;


/**
 * @author zzh
 *
 */

@Repository
public class ClearItemJdbcDao extends BaseJdbcDao {
	/**
	 * 
	 * @param bukrs 公司代码
	 * @param belnr 财务凭证号
	 * @param gjahr 会计年度
	 * @param buzei 行项目
	 * @return
	 */
	public ClearItem getClearItem(String bukrs,String belnr,String gjahr,String buzei,String username,String customerType){
		//去除数字字符串的前导0
		String buzei2 = buzei.replaceAll("^0+(?!$)", "");
		String hql = "select * from YCLEARITEM where bukrs = '" + bukrs + "' and belnr= '" + belnr +"' and gjahr='" + gjahr + "' and ( buzei='" + buzei+"' or buzei='"+ buzei2+ "' )  and username='"+username+"' and customerType = '"+customerType+"'";
		
		List<Map> rowList = this.getJdbcTemplate().queryForList(hql);
		log.debug("customertitle取得:" + hql);
		ClearItem clearItem = new ClearItem();
		BigDecimal dmbtr = new BigDecimal("0");
		BigDecimal wrbtr = new BigDecimal("0");
		if(null != rowList && rowList.size()>0){
			for(Map map:rowList){
				dmbtr = dmbtr.add(new BigDecimal(map.get("DMBTR").toString()));
				wrbtr = wrbtr.add(new BigDecimal(map.get("WRBTR").toString()));
			}
			ExBeanUtils.setBeanValueFromMap(clearItem, rowList.get(0));
			clearItem.setDmbtr(dmbtr);
			clearItem.setWrbtr(wrbtr);
			return clearItem;
		}else{
			return null;
		}
	}
	/**
	 * 
	 * @param from
	 * @param to
	 * @param date
	 * @param companyno
	 * @param depid
	 * @param customerNo
	 * @param subjectcode
	 * @param businessstate
	 * @return
	 */
	public BigDecimal getSumOffAmountByCustomer(String from ,String to,String date,String companyno,String depid,String customerNo,String subjectcode,String businessstate,String username){
		String sql="SELECT nvl(SUM(wrbtr),0) " +
		" FROM YCLEARITEM cl where cl.subjectcode like '"+ subjectcode+"' "  ;
    	if(!StringUtils.isNullBlank(companyno)){
    		sql =sql + " and cl.bukrs in ("+ companyno +") ";
    	}
    	if(!StringUtils.isNullBlank(depid)){
    		sql =sql + " and  cl.gsber in ("+ depid +") ";
    	}      	
    	if(!StringUtils.isNullBlank(customerNo)){
    		sql =sql + " and  cl.customerNo in ("+ customerNo +") ";
    	}
    	sql = sql + " and cl.username ='"+username+"' ";
//    	sql =sql +" and  cl.budat > TO_CHAR(TO_DATE('"+date+"', 'yyyymmdd') - "+from+", 'yyyymmdd')   AND cl.budat <=  TO_CHAR(TO_DATE('"+date+"', 'yyyymmdd') - "+to+", 'yyyymmdd') ";
    	if("0".equals(to)){
    		//分析日期和过账日期同一天，也算清
    		sql =sql +" and  cl.budat >= TO_CHAR(TO_DATE('"+date+"', 'yyyymmdd') - "+from+", 'yyyymmdd')   AND cl.budat <=  TO_CHAR(TO_DATE('"+date+"', 'yyyymmdd') - "+to+", 'yyyymmdd') ";
    	}else{
    		sql =sql +" and  cl.budat >= TO_CHAR(TO_DATE('"+date+"', 'yyyymmdd') - "+from+", 'yyyymmdd')   AND cl.budat <  TO_CHAR(TO_DATE('"+date+"', 'yyyymmdd') - "+to+", 'yyyymmdd') ";
    	}
		sql =sql + " and cl.customerType = '1' ";
//		退款的数据不用取进来计算
		sql =sql + " and cl.BUSINESSTYPE <> '07' ";
		
    	String sql2 = sql + " and cl.Shkzg='S' ";
		String sql3 = sql + " and cl.Shkzg='H' ";
		BigDecimal list = (BigDecimal) this.getJdbcTemplate().queryForObject(sql2, BigDecimal.class);
		if (null == list)list= new BigDecimal(0);
		BigDecimal list2 = (BigDecimal) this.getJdbcTemplate().queryForObject(sql3, BigDecimal.class);
		if (null == list2)list2= new BigDecimal(0);		
    	return list.subtract(list2);
	}
	/**
	 * 
	 * @param from
	 * @param to
	 * @param date
	 * @param companyno
	 * @param depid
	 * @param customerNo
	 * @param subjectcode
	 * @param businessstate
	 * @return
	 */
	public BigDecimal getSumOffAmountBySupplier(String from ,String to,String date,String companyno,String depid,String customerNo,String subjectcode,String businessstate,String username){
		String sql="SELECT nvl(SUM(wrbtr),0) " +
		" FROM YCLEARITEM cl where cl.subjectcode like '"+ subjectcode+"' "  ;
    	if(!StringUtils.isNullBlank(companyno)){
    		sql =sql + " and cl.bukrs in ("+ companyno +") ";
    	}
    	if(!StringUtils.isNullBlank(depid)){
    		sql =sql + " and  cl.gsber in ("+ depid +") ";
    	}      	
    	if(!StringUtils.isNullBlank(customerNo)){
    		sql =sql + " and  cl.customerNo in ("+ customerNo +") ";
    	}

    	sql = sql + " and cl.username ='"+username+"' ";
//    	sql =sql +" and  cl.budat > TO_CHAR(TO_DATE('"+date+"', 'yyyymmdd') - "+from+", 'yyyymmdd')   AND cl.budat <=  TO_CHAR(TO_DATE('"+date+"', 'yyyymmdd') - "+to+", 'yyyymmdd') ";
    	if("0".equals(to)){
    		//分析日期和过账日期同一天，也算清
    		sql =sql +" and  cl.budat >= TO_CHAR(TO_DATE('"+date+"', 'yyyymmdd') - "+from+", 'yyyymmdd')   AND cl.budat <=  TO_CHAR(TO_DATE('"+date+"', 'yyyymmdd') - "+to+", 'yyyymmdd') ";
    	}else{
    		sql =sql +" and  cl.budat >= TO_CHAR(TO_DATE('"+date+"', 'yyyymmdd') - "+from+", 'yyyymmdd')   AND cl.budat <  TO_CHAR(TO_DATE('"+date+"', 'yyyymmdd') - "+to+", 'yyyymmdd') ";
    	
    	}
		sql =sql + " and cl.customerType = '2' ";
//		退款的数据不用取进来计算
		sql =sql + " and cl.BUSINESSTYPE <> '15' ";
		
    	String sql2 = sql + " and cl.Shkzg='S' ";
		String sql3 = sql + " and cl.Shkzg='H' ";
		BigDecimal list = (BigDecimal) this.getJdbcTemplate().queryForObject(sql2, BigDecimal.class);
		if (null == list)list= new BigDecimal(0);
		BigDecimal list2 = (BigDecimal) this.getJdbcTemplate().queryForObject(sql3, BigDecimal.class);
		if (null == list2)list2= new BigDecimal(0);		
    	return list2.subtract(list);
	}
	/**
	 * 未清明细，减去部分清的
	 * @param from
	 * @param to
	 * @param enddate
	 * @param begindate
	 * @param amount
	 * @return
	 */
	public BigDecimal getSumOffAmountByVoucher(String from ,String to,String enddate,String begindate,BigDecimal amount){
		BigDecimal amount2 =  new BigDecimal(0);	
		int i=DateUtils.getIntervalDays(enddate, begindate);
		//分析日期和过账日期同一天，也算清
		if( (Integer.parseInt(to)< i && i <=Integer.parseInt(from) ) || ( i== 0 && Integer.parseInt(to) == 0) ){
			amount2=amount;
		}
		
    	return amount2;
	}
	/**
	 * 
	 * @param bukrs
	 * @param customernos
	 * @param username
	 * @param customerType 客户类型1为客户，2为供应商
	 * @param type ，记录是哪个模块清的账，07为客户退款，15为供应商退款
	 * @return
	 */
	public List<ClearItem> getClearItemByType(String bukrs,List<String> customernos,String username,String customerType,String type){
		String cus="";
		List<ClearItem> clearItems = new ArrayList<ClearItem>();
		for(String customer :customernos){
			if(StringUtils.isNullBlank(cus)){
				cus = customer;
			}else{
				cus = cus + "','" + customer;
			}
		}
		String hql = "SELECT *  FROM YCLEARITEM CL WHERE CL.BUKRS = '"+ bukrs +"'   AND CL.CUSTOMERNO IN ('"+cus+"')   AND CL.BUSINESSTYPE = '"+type+"'   AND CL.CUSTOMERTYPE = '"+customerType+"'   AND CL.USERNAME = '"+username+"'";
		
		List<Map> rowList = this.getJdbcTemplate().queryForList(hql);
		log.debug("ClearItem取得:" + hql);
		ClearItem clearItem = new ClearItem();
		BigDecimal dmbtr = new BigDecimal("0");
		BigDecimal wrbtr = new BigDecimal("0");
		if(null != rowList && rowList.size()>0){
			for(Map map:rowList){
				dmbtr = dmbtr.add(new BigDecimal(map.get("DMBTR").toString()));
				wrbtr = wrbtr.add(new BigDecimal(map.get("WRBTR").toString()));
				ExBeanUtils.setBeanValueFromMap(clearItem, map);
				clearItem.setDmbtr(dmbtr);
				clearItem.setWrbtr(wrbtr);
				clearItems.add(clearItem);
			}
		}
		return clearItems;
		
	}
	
	/**
	 * 取得退款的收款的会计凭证
	 * @param businessitemid
	 * @return
	 */
	public VoucherItem getVoucherItemList(String businessitemid,String customertype)
	{
//		List<VoucherItem> voucherItemList = new ArrayList<VoucherItem>();
		String strSql = "";
		if("1".equals(customertype)){
			 strSql = "SELECT * FROM yvoucheritem vi,  yvoucher v  WHERE v.voucherid=vi.voucherid AND TRIM(v.voucherno) IS NOT NULL  AND TRIM(vi.businessitemid) IS NOT NULL AND vi.businessitemid IN (SELECT rei.collectitemid FROM yrefundmentitem  rei WHERE  rei.refundmentitemid='"+businessitemid+"')";
		}else{
			 strSql = "SELECT * FROM yvoucheritem vi,  yvoucher v  WHERE v.voucherid=vi.voucherid AND TRIM(v.voucherno) IS NOT NULL  AND TRIM(vi.businessitemid) IS NOT NULL AND vi.businessitemid IN (SELECT rei.paymentitemid FROM yrefundmentitem  rei WHERE  rei.refundmentitemid='"+businessitemid+"')";
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
//			voucherItemList.add(voucherItem);
			return voucherItem;
		}
		
		return null;
	}
	
}
