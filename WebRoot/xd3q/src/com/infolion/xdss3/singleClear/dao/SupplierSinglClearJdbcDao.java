/*
 * @(#)PaymentItemJdbcDao.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：林进旭
 *  时　间：2010-7-13
 *  描　述：创建
 */

/**
 * 
 */
package com.infolion.xdss3.singleClear.dao;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Repository;

import com.infolion.platform.dpframework.core.dao.BaseJdbcDao;
import com.infolion.platform.dpframework.util.ExBeanUtils;
import com.infolion.platform.util.StringUtils;
import com.infolion.xdss3.financeSquare.domain.FundFlow;
import com.infolion.xdss3.financeSquare.domain.SettleSubject;
import com.infolion.xdss3.singleClear.domain.SupplierSinglClear;
import com.infolion.xdss3.singleClear.domain.UnClearPayment;
import com.infolion.xdss3.singleClear.domain.UnClearSupplieBill;

/**
 * <pre>
 * (SupplierSinglClear),JDBC 操作类
 * </pre>
 * 
 * <br>
 * JDK版本:1.5
 * 
 * @author 林进旭
 * @version 1.0
 * @see The author for more details
 * @since 1.0
 */
@Repository
public class SupplierSinglClearJdbcDao extends BaseJdbcDao
{

	/**
	 * 根据业务状态、vendorTitleId，取得款已经审批完金额(款已清金额)、在途金额(款在途金额)。
	 * 
	 * @param vendorTitleId
	 * @param businessstates
	 * @return
	 */
	public BigDecimal getSumClearAmount(String vendorTitleId, String businessstates)
	{
		StringBuffer sb = new StringBuffer();
		sb.append("select nvl(sum(AMOUNT),0) from (");
		// 供应商单清
		sb.append(" select nvl(sum(NOWCLEARAMOUNT),0) as AMOUNT from YUNCLEARPAYMENT a ,YSUPPLIERSICLEAR b ");
		sb.append(" where a.SUPPLIERSCLEARID=b.SUPPLIERSCLEARID and a.vendortitleid='" + vendorTitleId + "' and b.BUSINESSSTATE in(" + businessstates + ")");
		sb.append(" )");
		log.debug(" 根据业务状态、vendorTitleId，取得款已经审批完金额(款已清金额)、在途金额(款在途金额)。:" + sb.toString());
		BigDecimal list = (BigDecimal) this.getJdbcTemplate().queryForObject(sb.toString(), BigDecimal.class);

		if (null == list)
			return new BigDecimal(0);
		else
			return list;
	}

	/**
	 * 根据业务状态、vendorTitleId，取得票已经审批完金额(款已清金额)、在途金额(款在途金额)。
	 * 
	 * @param vendorTitleId
	 * @param businessstates
	 * @return
	 */
	public BigDecimal getSumClearAmount2(String vendorTitleId, String businessstates)
	{
		StringBuffer sb = new StringBuffer();
		sb.append("select nvl(sum(AMOUNT),0) from (");
		// 供应商单清
		sb.append(" select nvl(sum(CLEARAMOUNT),0) as AMOUNT from YUNCLEARSUPPBILL a ,YSUPPLIERSICLEAR b ");
		sb.append(" where a.SUPPLIERSCLEARID=b.SUPPLIERSCLEARID and a.vendortitleid='" + vendorTitleId + "' and b.BUSINESSSTATE in(" + businessstates + ")");
		sb.append(" )");
		log.debug(" 根据业务状态、vendorTitleId，取得票已经审批完金额(款已清金额)、在途金额(款在途金额)。:" + sb.toString());
		BigDecimal list = (BigDecimal) this.getJdbcTemplate().queryForObject(sb.toString(), BigDecimal.class);

		if (null == list)
			return new BigDecimal(0);
		else
			return list;
	}
	/**
	 * JDBC删除供应商单清对象下，所有1对多子业务对象数据。
	 * 
	 * @return
	 */
	public void deleteSupplierSingleClearUnderOneToManySubObject(String suppliersClearId)
	{
		if (!StringUtils.isNullBlank(suppliersClearId))
		{
			String strSql1 = "delete from YUNCLEARSUPPBILL where SUPPLIERSCLEARID='" + suppliersClearId + "'";
			String strSql2 = "delete from YUNCLEARPAYMENT where SUPPLIERSCLEARID='" + suppliersClearId + "'";
			this.getJdbcTemplate().execute(strSql1);
			this.getJdbcTemplate().execute(strSql2);
		}
	}

	/**
	 * 更新单据业务状态。
	 * 
	 * @param suppliersClearId
	 */
	public void updateBusinessstate(String suppliersClearId, String businessState)
	{
		String strSql1 = "update YSUPPLIERSICLEAR set BUSINESSSTATE ='" + businessState + "' where SUPPLIERSCLEARID='" + suppliersClearId + "'";
		this.getJdbcTemplate().execute(strSql1);
	}

	/**
	 * 执行SQL，取得金额合计值。
	 * 
	 * @param sql
	 * @return
	 */
	public BigDecimal getSumAmount(String sql)
	{
		log.debug("执行SQL，取得金额合计值:" + sql);
		BigDecimal sumAmount = (BigDecimal) this.getJdbcTemplate().queryForObject(sql, BigDecimal.class);
		if (null == sumAmount)
			return new BigDecimal(0.00);
		else
			return sumAmount;
	}

	/**
	 * 判断单据是否可全清帐
	 * 
	 * @param supplierSinglClear
	 * @return
	 */
	@Deprecated
	public boolean judgeIsAllClear(String businessId)
	{
		StringBuffer sb = new StringBuffer();
		// sb.append("select nvl(sum(RECEIVABLEAMOUNT),0) from YUNCLEARSUPPBILL");
		// sb.append(" where SUPPLIERSCLEARID ='" + businessId + "'");
		// // 票金额合计
		// BigDecimal sumBillAmount = getSumAmount(sb.toString());
		//
		// sb = new StringBuffer();
		// sb.append("select nvl(sum(CLEARAMOUNT),0) from YUNCLEARSUPPBILL");
		// sb.append(" where SUPPLIERSCLEARID='" + businessId + "'");
		// // 票清帐金额合计
		// BigDecimal sumBillClearAmount = getSumAmount(sb.toString());
		//
		// sb = new StringBuffer();
		// sb.append("select nvl(sum(PAYMENTAMOUNT),0) from YUNCLEARPAYMENT");
		// sb.append(" where SUPPLIERSCLEARID='" + businessId + "'");
		// // 款金额合计
		// BigDecimal sumFundAmount = getSumAmount(sb.toString());
		//
		// sb = new StringBuffer();
		// sb.append("select nvl(sum(NOWCLEARAMOUNT),0) from YUNCLEARPAYMENT");
		// sb.append(" where  SUPPLIERSCLEARID='" + businessId + "'");
		// // 款清帐金额合计
		// BigDecimal sumFundClearAmount = getSumAmount(sb.toString());

		sb.append("select nvl(sum(RECEIVABLEAMOUNT),0) RECEIVABLEAMOUNT,nvl(sum(CLEARAMOUNT),0) CLEARAMOUNT from YUNCLEARSUPPBILL");
		sb.append(" where SUPPLIERSCLEARID ='" + businessId + "'");
		Map map = this.getJdbcTemplate().queryForMap(sb.toString());
		// 票金额合计
		BigDecimal sumBillAmount = new BigDecimal(0.00);
		// 票清帐金额合计
		BigDecimal sumBillClearAmount = new BigDecimal(0.00);
		if (null != map && map.size() > 0)
		{
			sumBillAmount = (BigDecimal) map.get("RECEIVABLEAMOUNT");
			sumBillClearAmount = (BigDecimal) map.get("CLEARAMOUNT");
		}

		sb = new StringBuffer();
		sb.append("select nvl(sum(PAYMENTAMOUNT),0) PAYMENTAMOUNT,nvl(sum(NOWCLEARAMOUNT),0) NOWCLEARAMOUNT from YUNCLEARPAYMENT");
		sb.append(" where SUPPLIERSCLEARID='" + businessId + "'");
		Map map1 = this.getJdbcTemplate().queryForMap(sb.toString());
		// 款金额合计
		BigDecimal sumFundAmount = new BigDecimal(0.00);
		// 款清帐金额合计
		BigDecimal sumFundClearAmount = new BigDecimal(0.00);
		if (null != map1 && map1.size() > 0)
		{
			sumFundAmount = (BigDecimal) map1.get("PAYMENTAMOUNT");
			sumFundClearAmount = (BigDecimal) map1.get("NOWCLEARAMOUNT");
		}

		log.debug("sumBillAmount:" + sumBillAmount + ",sumBillClearAmount:" + sumBillClearAmount);
		log.debug("sumFundAmount:" + sumFundAmount + ",sumFundClearAmount:" + sumFundClearAmount);

		if (sumBillAmount.compareTo(sumBillClearAmount) == 0 && sumFundAmount.compareTo(sumFundClearAmount) == 0)
			return true;
		return false;
	}

	/**
	 * 
	 * @param businessId
	 * @return
	 */
	public List<Map<String, Object>> getUnClearSuppbillByContractNo(String businessId)
	{
		StringBuffer sb = new StringBuffer();
		sb.append("select RECEIVABLEAMOUNT,CLEARAMOUNT,");
		sb.append("INVOICE,VENDORTITLEID,UNPAIDAMOUNT,CONTRACT_NO");
		sb.append(" from YUNCLEARSUPPBILL");
		sb.append(" where SUPPLIERSCLEARID='" + businessId + "' and CONTRACT_NO!=' ' order by CONTRACT_NO");
		log.debug("取得票(YUNCLEARSUPPBILL)数据:" + sb.toString());
		List<Map<String, Object>> list = this.getJdbcTemplate().queryForList(sb.toString());

		return list;
	}

	/**
	 * 
	 * @param businessId
	 * @param contractNo
	 * @return
	 */
	public List<Map<String, Object>> getUnclearpaymentByContractNo(String businessId, String contractNo)
	{
		StringBuffer sb = new StringBuffer();
		sb = new StringBuffer();
		sb.append("select PAYMENTAMOUNT,NOWCLEARAMOUNT,");
		sb.append("PAYMENTITEMID,UNOFFSETAMOUNT,CONTRACT_NO");
		sb.append(" from YUNCLEARPAYMENT");
		sb.append(" where SUPPLIERSCLEARID='" + businessId + "' and CONTRACT_NO='" + contractNo + "'");
		log.debug("取得款(YUNCLEARPAYMENT)数据:" + sb.toString());
		List<Map<String, Object>> list = this.getJdbcTemplate().queryForList(sb.toString());
		return list;
	}

	/**
	 * 
	 * @param businessId
	 * @return
	 */
	public List<Map<String, Object>> getUnClearSuppbillByProjectNo(String businessId)
	{
		StringBuffer sb = new StringBuffer();
		sb.append("select RECEIVABLEAMOUNT,CLEARAMOUNT,");
		sb.append("INVOICE,VENDORTITLEID,UNPAIDAMOUNT,PROJECT_NO");
		sb.append(" from YUNCLEARSUPPBILL");
		sb.append(" where SUPPLIERSCLEARID='" + businessId + "' and PROJECT_NO!=' ' order by PROJECT_NO");
		log.debug("取得票(YUNCLEARSUPPBILL)数据:" + sb.toString());
		List<Map<String, Object>> list = this.getJdbcTemplate().queryForList(sb.toString());

		return list;
	}

	/**
	 * 
	 * @param businessId
	 * @param contractNo
	 * @return
	 */
	public List<Map<String, Object>> getUnclearpaymentByProjectNo(String businessId, String projectNo)
	{
		StringBuffer sb = new StringBuffer();
		sb = new StringBuffer();
		sb.append("select PAYMENTAMOUNT,NOWCLEARAMOUNT,");
		sb.append("PAYMENTITEMID,UNOFFSETAMOUNT,PROJECT_NO");
		sb.append(" from YUNCLEARPAYMENT");
		sb.append(" where SUPPLIERSCLEARID='" + businessId + "' and PROJECT_NO='" + projectNo + "'");
		log.debug("取得款(YUNCLEARPAYMENT)数据:" + sb.toString());
		List<Map<String, Object>> list = this.getJdbcTemplate().queryForList(sb.toString());
		return list;
	}
	
	/**
	 * 判断sap手工调汇率，是否有清账
	 * @param customertitleid
	 * @return
	 */
	public boolean getSapPayment(String vendortitleid)
	{
		StringBuffer sb = new StringBuffer();
		sb.append(" select COUNT(*) from YUNCLEARSUPPBILL a ,YSUPPLIERSICLEAR b ");
		sb.append(" where a.SUPPLIERSCLEARID=b.SUPPLIERSCLEARID and a.vendortitleid='" + vendortitleid + "' and b.BUSINESSSTATE !='-1'");	
		log.debug(" 根据vendortitleid， 判断sap手工调汇率，是否有清账:" + sb.toString());
		int list = (int) this.getJdbcTemplate().queryForInt(sb.toString());

		if (list == 0)
			return false;
		else
			return true;
	}
	
	public SupplierSinglClear getSupplierSinglClearById(String id){
		String sql = "select * from YSUPPLIERSICLEAR a where  a.SUPPLIERSCLEARID='" + id + "'";
		List<Map> rowList = this.getJdbcTemplate().queryForList(sql.toString());
		if(rowList.size()==0)return null;
		SupplierSinglClear supplierSinglClear = new SupplierSinglClear();
		ExBeanUtils.setBeanValueFromMap(supplierSinglClear, rowList.get(0));
		
		sql = "select * from YUNCLEARSUPPBILL a where  a.SUPPLIERSCLEARID='" + id + "'";
		rowList = this.getJdbcTemplate().queryForList(sql.toString());
		Set<UnClearSupplieBill> unClearSupplieBillSet = new HashSet<UnClearSupplieBill>();
		for(Map ci:rowList){
			UnClearSupplieBill  ucsb = new UnClearSupplieBill();
			ExBeanUtils.setBeanValueFromMap(ucsb, ci);
			ucsb.setSupplierSinglClear(supplierSinglClear);
			unClearSupplieBillSet.add(ucsb);
		}		
		supplierSinglClear.setUnClearSupplieBill(unClearSupplieBillSet);
		
		sql = "select * from YUNCLEARPAYMENT a where  a.SUPPLIERSCLEARID='" + id + "'";
		rowList = this.getJdbcTemplate().queryForList(sql.toString());
		Set<UnClearPayment> unClearPaymentSet = new HashSet<UnClearPayment>();
		for(Map ci:rowList){
			UnClearPayment  ucsb = new UnClearPayment();
			ExBeanUtils.setBeanValueFromMap(ucsb, ci);
			ucsb.setSupplierSinglClear(supplierSinglClear);
			unClearPaymentSet.add(ucsb);
		}		
		supplierSinglClear.setUnClearPayment(unClearPaymentSet);
		
		sql="select * from YFUNDFLOW a where  a.SUPPLIERSCLEARID='" + id + "'";
		rowList = this.getJdbcTemplate().queryForList(sql.toString());	
		if(null != rowList && rowList.size()!=0){
			FundFlow ff = new FundFlow();
			ExBeanUtils.setBeanValueFromMap(ff,  rowList.get(0));
			ff.setSupplierSinglClear(supplierSinglClear);
			supplierSinglClear.setFundFlow(ff);
		}
		
		sql="select * from YSETTLESUBJECT a where  a.SUPPLIERSCLEARID='" + id + "'";
		rowList = this.getJdbcTemplate().queryForList(sql.toString());	
		if(null != rowList && rowList.size()!=0){
			SettleSubject ss = new SettleSubject();
			ExBeanUtils.setBeanValueFromMap(ss,  rowList.get(0));
			ss.setSupplierSinglClear(supplierSinglClear);
			supplierSinglClear.setSettleSubject(ss);
		}		
		return supplierSinglClear;
	}
}
