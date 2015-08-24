/*
 * @(#)CustomerTitleJdbcDao.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：林进旭
 *  时　间：2010-7-16
 *  描　述：创建
 */

package com.infolion.xdss3.masterData.dao;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import com.infolion.platform.dpframework.core.dao.BaseJdbcDao;
import com.infolion.platform.dpframework.util.ExBeanUtils;
import com.infolion.platform.util.StringUtils;
import com.infolion.xdss3.Constants.cleared;
import com.infolion.xdss3.customerDrawback.domain.CustomerRefundItem;
import com.infolion.xdss3.customerDrawback.domain.CustomerRefundment;
import com.infolion.xdss3.masterData.domain.CustomerTitle;
import com.infolion.xdss3.masterData.domain.CustomerTitleOthers;
import com.infolion.xdss3.payment.domain.ClearVoucherItemStruct;

/**
 * <pre>
 * 未清客户数据抬头(CustomerTitle)JDBC
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
public class CustomerTitleOthersJdbcDao extends BaseJdbcDao
{

	/**
	 * 根据客户编号、科目，取得客户下未清发票数据集合
	 * 
	 * @param custom
	 * @param shkzg
	 *            借方/贷方标识
	 */
	public List<CustomerTitleOthers> getCustomerTitleListByCustom(String custom, String subject, String shkzg)
	{
		List<CustomerTitleOthers> customerTitles = new ArrayList<CustomerTitleOthers>();
		String sql = "select * from YCUSTOMERTITLEOTHERS where KUNNR ='" + custom + "' and SAKNR='" + subject + "' and ISCLEARED='0'";
		if (!StringUtils.isNullBlank(shkzg))
		{
			sql = sql + " and SHKZ='" + shkzg + "'";
		}
		List<Map> rowList = this.getJdbcTemplate().queryForList(sql);
		log.debug("根据客户编号、科目，取得客户下未清发票数据集合:" + sql);
		for (Map map : rowList)
		{
			CustomerTitleOthers customerTitle = new CustomerTitleOthers();
			ExBeanUtils.setBeanValueFromMap(customerTitle, map);
			customerTitles.add(customerTitle);
		}

		return customerTitles;
	}
	/**
	 * 根据客户编号、科目，货币文本，公司代码,业务范围取得客户下未清发票数据集合
	 * @param custom
	 * @param subject
	 * @param currencytext
	 * @param companyno
	 * @param shkzg
	 * @return
	 */
	public List<CustomerTitleOthers> getCustomerTitleListByCustom(String custom, String subject,String currencytext ,String companyno,String depid, String shkzg)
	{
		List<CustomerTitleOthers> customerTitles = new ArrayList<CustomerTitleOthers>();
//		String sql = "select * from YCUSTOMERTITLEOTHERS where KUNNR ='" + custom + "' and SAKNR='" + subject + "' and WAERS='" + currencytext + "' and BUKRS='" + companyno + "' and GSBER='" + depid + "' and ISCLEARED='0'  ";
		String sql = "select * from YCUSTOMERTITLEOTHERS where KUNNR ='" + custom + "' and SAKNR='" + subject + "' and WAERS='" + currencytext + "' and BUKRS='" + companyno +  "' and ISCLEARED='0'";
		if (!StringUtils.isNullBlank(shkzg))
		{
			sql = sql + " and SHKZ='" + shkzg + "'";
		}
		List<Map> rowList = this.getJdbcTemplate().queryForList(sql);
		log.debug("根据客户编号、科目，货币文本，公司代码，取得客户下未清发票数据集合:" + sql);
		for (Map map : rowList)
		{
			CustomerTitleOthers customerTitle = new CustomerTitleOthers();
			ExBeanUtils.setBeanValueFromMap(customerTitle, map);
			customerTitles.add(customerTitle);
		}

		return customerTitles;
	}

	/**
	 * 根据业务状态，取得开票已经审批完金额、在途金额。
	 * 
	 * @param billno
	 * @param businessstate
	 * @return
	 */
	public BigDecimal getSumClearAmount(String invoice, String businessstates)
	{

		StringBuffer sb = new StringBuffer();
		// 票清收款
		sb.append("select nvl(sum(amount),0) from (");
		sb.append("select nvl(sum(clearbillamount),0) as amount from YBILLCLEARITEM a ,YBILLCLEAR b ");
		sb.append(" where a.BILLCLEARID=b.BILLCLEARID and b.CLEARTYPE='1' and a.INVOICE='" + invoice + "' and trim(a.INVOICE) is not null and b.BUSINESSSTATE in(" + businessstates + ")");
		sb.append(" union ");
		// 客户单清
		sb.append("select nvl(sum(CLEARAMOUNT),0) as amount from YUNCLEARCUSTBILL a ,YCUSTOMSICLEAR b   ");
		sb.append(" where a.CUSTOMSCLEARID=b.CUSTOMSCLEARID and b.BUSINESSSTATE in(" + businessstates + ") and  trim(a.BILLNO) is not null and  a.BILLNO='" + invoice + "'");
		sb.append(" union ");
		// 收款(清票)
//		sb.append(" select nvl(sum( a.CLEARAMOUNT),0) as amount from YCOLLECTCBILL a ,YCOLLECT b where a.collectid=b.collectid and b.collecttype in('0','1')");
		sb.append(" select nvl(sum( a.CLEARAMOUNT),0) as amount from YCOLLECTCBILL a ,YCOLLECT b where a.collectid=b.collectid ");
		sb.append(" and b.BUSINESSSTATE in(" + businessstates + ") and trim(a.BILLNO) is not null  and a.BILLNO='" + invoice + "'");
		sb.append(" )");

		log.debug("根据业务状态，取得开票已经审批完金额、在途金额:" + sb.toString());
		BigDecimal list = (BigDecimal) this.getJdbcTemplate().queryForObject(sb.toString(), BigDecimal.class);

		if (null == list)
			return new BigDecimal(0);
		else
			return list;
	}

	/**
	 * 根据业务状态，取得开票已经审批完金额、在途金额。去掉本次的
	 * 
	 * @param billno
	 * @param businessstate
	 * @param billclearid
	 * @return
	 */
	public BigDecimal getSumClearAmount(String invoice, String businessstates,String billclearid)
	{

		StringBuffer sb = new StringBuffer();
		// 票清收款
		sb.append("select nvl(sum(amount),0) from (");
		sb.append("select nvl(sum(clearbillamount),0) as amount from YBILLCLEARITEM a ,YBILLCLEAR b ");
		sb.append(" where a.BILLCLEARID=b.BILLCLEARID and b.CLEARTYPE='1' and a.INVOICE='" + invoice + "' and trim(a.INVOICE) is not null and b.BUSINESSSTATE in(" + businessstates + ")");
		if(null != billclearid && !"".equals(billclearid)){
			sb.append(" and a.BILLCLEARID!='" + billclearid + "'" );
		}
		sb.append(" union ");
		// 客户单清
		sb.append("select nvl(sum(CLEARAMOUNT),0) as amount from YUNCLEARCUSTBILL a ,YCUSTOMSICLEAR b   ");
		sb.append(" where a.CUSTOMSCLEARID=b.CUSTOMSCLEARID and b.BUSINESSSTATE in(" + businessstates + ") and  trim(a.BILLNO) is not null and  a.BILLNO='" + invoice + "'");
		sb.append(" union ");
		// 收款(清票)
//		sb.append(" select nvl(sum( a.CLEARAMOUNT),0) as amount from YCOLLECTCBILL a ,YCOLLECT b where a.collectid=b.collectid and b.collecttype in('0','1')");
		sb.append(" select nvl(sum( a.CLEARAMOUNT),0) as amount from YCOLLECTCBILL a ,YCOLLECT b where a.collectid=b.collectid ");
		sb.append(" and b.BUSINESSSTATE in(" + businessstates + ") and trim(a.BILLNO) is not null  and a.BILLNO='" + invoice + "'");
		sb.append(" )");

		log.debug("根据业务状态，取得开票已经审批完金额、在途金额:" + sb.toString());
		BigDecimal list = (BigDecimal) this.getJdbcTemplate().queryForObject(sb.toString(), BigDecimal.class);

		if (null == list)
			return new BigDecimal(0);
		else
			return list;
	}
	
	/**
	 * 更新未清客户抬头数据上是否已结清标志
	 * 
	 * @param customertitleid
	 *            未清客户抬头ID
	 * @param isCleared
	 *            是否结清标志。
	 */
	public void updateCustomerTitleIsCleared(String customertitleid, String isCleared)
	{
		String sql = "update YCUSTOMERTITLEOTHERS set ISCLEARED ='" + isCleared + "' where CUSTOMERTITLEID ='" + customertitleid + "'";
		log.debug("更新未清客户抬头数据上是否已结清标志:" + sql);
		this.getJdbcTemplate().execute(sql);
	}

	/**
	 * 更新未清客户抬头数据上是否已结清标志
	 * 
	 * @param invoice
	 *            发票号
	 * @param isCleared
	 *            是否结清标志。
	 */
	public void updateCustomerTitleIsClearedByInvoice(String invoice, String isCleared)
	{
		String sql = "update YCUSTOMERTITLEOTHERS set ISCLEARED ='" + isCleared + "' where INVOICE ='" + invoice + "'";
		log.debug("更新未清客户抬头数据上是否已结清标志:" + sql);
		this.getJdbcTemplate().execute(sql);
	}


	
	
	/**
	 * 
	 * @param customertitleid
	 * @return
	 */
	public CustomerTitleOthers getByCustomertitleid(String customertitleid)
	{
		String hql = "select * from YCUSTOMERTITLEOTHERS where customertitleid = '" + customertitleid + "'";
		
		List<Map> rowList = this.getJdbcTemplate().queryForList(hql);
		log.debug("根据customertitleid取得:" + hql);
		CustomerTitleOthers customerTitle = new CustomerTitleOthers();
		ExBeanUtils.setBeanValueFromMap(customerTitle, rowList.get(0));
		return customerTitle;
	}
	/**
	 * 
	 * @param bukrs 公司代码
	 * @param belnr 财务凭证号
	 * @param gjahr 会计年度
	 * @param buzei 行项目
	 * @return
	 */
	public CustomerTitleOthers getCustomerTitle(String bukrs,String belnr,String gjahr,String buzei){
		String hql = "select * from YCUSTOMERTITLEOTHERS where bukrs = '" + bukrs + "' and belnr= '" + belnr +"' and gjahr='" + gjahr + "' and buzei='" + buzei+"'";
		
		List<Map> rowList = this.getJdbcTemplate().queryForList(hql);
		log.debug("customertitle取得:" + hql);
		CustomerTitleOthers customerTitle = new CustomerTitleOthers();
		if(null != rowList && rowList.size()>0){
			ExBeanUtils.setBeanValueFromMap(customerTitle, rowList.get(0));
			return customerTitle;
		}else{
			return null;
		}
	}
	
	/**
	 * @创建作者：邱杰烜
	 * @修改作者：2010-10-20
	 * 根据客户编号判断该客户数据是否已清
	 */
//	public boolean isCustomerTitleClear(CustomerTitle customerTitle){
//		String sql1 = "SELECT COUNT(*) FROM YREFUNDMENT a WHERE a.REFUNDMENTID IN (SELECT b.BUSINESSID FROM YVOUCHER b WHERE b.VOUCHERNO='" +
//					 customerTitle.getBelnr() + 
//					 "')";
//		String sql2 = "SELECT BUSINESSID FROM YVOUCHER WHERE VOUCHERNO='" + customerTitle.getBelnr() + 
//					  "' AND COMPANYCODE='" + customerTitle.getBukrs() + 
//					  "' AND FIYEAR='" + customerTitle.getGjahr() + "'";
//		if(this.getJdbcTemplate().queryForInt(sql1) > 0){	// 若客户退款凭证已生成
//			return true;
//		}else{												// 若在单清与票清楚已经生成凭证
//			String sql3 = "SELECT COUNT(*) FROM YCUSTOMSICLEAR WHERE CUSTOMSCLEARID IN (" + sql2 + ")";
//			String sql4 = "SELECT COUNT(*) FROM YBILLCLEAR WHERE BILLCLEARID IN (" + sql2 + ")";
//			if(this.getJdbcTemplate().queryForInt(sql3)>0 || this.getJdbcTemplate().queryForInt(sql4)>0){
//				return true;
//			}
//		}
//		return false;
//	}

	/**
	 * @创建作者：邱杰烜
	 * @创建时间：2010-11-16
	 * 判断YCUSTOMERTITLE表中所有数据的VGBEL字段是否都为空，若是则同步时需要全量同步
	 */
	public boolean isNeedFullSync(){
		String sql = "SELECT COUNT(*) FROM YCUSTOMERTITLEOTHERS A WHERE TRIM(A.VGBEL) IS NOT NULL";
		return this.getJdbcTemplate().queryForInt(sql) == 0;
	}
	
	/**
	 * @创建作者：邱杰烜
	 * @创建时间：2010-11-16
	 * 根据VGBEL(销售订单号)到T_CONTRACT_SALES_INFO表中取出CONTRACT_NO
	 */
	public List getContractNoByVgbel(String vgbel){
		String sql = "SELECT CONTRACT_NO FROM T_CONTRACT_SALES_INFO WHERE SAP_ORDER_NO = '" + vgbel + "'";
		return this.getJdbcTemplate().queryForList(sql);
	}
	
	/**
	 * @创建作者：邱杰烜
	 * @创建时间：2010-11-17
	 * 根据公司代码、会计凭证号码、会计年度、行项目号去查找客户未清数据ID
	 */
	public List getTitleIdIfExist(String bukrs, String belnr, String gjahr, String buzei){
		String sql = "select CUSTOMERTITLEID from YCUSTOMERTITLEOTHERS where BUKRS = '" + bukrs 
					+ "' and BELNR = '" + belnr 
					+ "' and GJAHR = '" + gjahr
					+ "' and BUZEI = '" + buzei + "'";
		return this.getJdbcTemplate().queryForList(sql);
	}
	/**
	 * 
	 * @param bukrs
	 * @param gsber
	 * @param kunnr
	 * @param waers
	 * @param saknr
	 * @param augdt_to
	 * @param augdt_from
	 * @return
	 */
	public List<CustomerTitleOthers> getCustomerTitle(String bukrs,String gsber,String kunnr,String waers,String saknr,String augdt_to,String augdt_from){
		StringBuffer sql = new StringBuffer(" select * from YCUSTOMERTITLEOTHERS a where 1=1 ");
		if (!StringUtils.isNullBlank(bukrs)) {
			sql.append("and a.bukrs='"+bukrs+"'");
		}
		if (!StringUtils.isNullBlank(gsber)) {
			sql.append("and a.gsber='"+gsber+"'");
		}
		if (!StringUtils.isNullBlank(kunnr)) {
			sql.append("and a.kunnr='"+kunnr+"'");
		}
		if (!StringUtils.isNullBlank(waers)) {
			sql.append("and a.waers='"+waers+"'");
		}
		if (!StringUtils.isNullBlank(saknr)) {
			sql.append("and a.saknr='"+saknr+"'");
		}
		if (!StringUtils.isNullBlank(augdt_to) && !StringUtils.isNullBlank(augdt_from)) {			
			sql.append("AND a.budat BETWEEN '" + augdt_to + "' AND '"
					+ augdt_from + "'");
		}else if(!StringUtils.isNullBlank(augdt_to)){
			sql.append("AND a.budat > '" + augdt_to + "'");
		}else if(!StringUtils.isNullBlank(augdt_from)){
			sql.append("AND a.budat < '" + augdt_from + "'");
		}
		List<CustomerTitleOthers> customerTitles =new ArrayList<CustomerTitleOthers>();
		List<Map> rowList = this.getJdbcTemplate().queryForList(sql.toString());
		log.debug("根据客户编号、科目，取得客户下未清发票数据集合:" + sql);
		for (Map map : rowList)
		{
			CustomerTitleOthers customerTitle = new CustomerTitleOthers();
			ExBeanUtils.setBeanValueFromMap(customerTitle, map);
			customerTitles.add(customerTitle);
		}
		return customerTitles;
	}
	
	/**
	 * 更新冲销的状态
	 * @param belnr
	 * @param burks
	 * @param gjahr
	 * @param stblg
	 * @param stjah
	 * @param isCleared
	 */
	public void updateCustomerTitleIsClearedByBelnr(String belnr,String burks,String gjahr,String stblg,String stjah, String isCleared)
	{
		String sql = "update YCUSTOMERTITLEOTHERS set ISCLEARED ='" + isCleared + "' , stblg ='" + stblg + "' , stjah ='" + stjah + "'  where belnr ='" + belnr + "' and burks ='" +burks+ "' and gjahr ='" +gjahr+  "'";
		log.debug("更新未清客户抬头数据上是否已结清标志:" + sql);
		this.getJdbcTemplate().execute(sql);
	}
}
