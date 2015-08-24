/*
 * @(#)VendorTitleJdbcDao.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：林进旭
 *  时　间：2010-6-23
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
import com.infolion.xdss3.Constants;
import com.infolion.xdss3.Constants.cleared;
import com.infolion.xdss3.customerDrawback.domain.CustomerRefundItem;
import com.infolion.xdss3.customerDrawback.domain.CustomerRefundment;
import com.infolion.xdss3.masterData.domain.CustomerTitle;
import com.infolion.xdss3.masterData.domain.VendorTitle;
import com.infolion.xdss3.payment.domain.ClearVoucherItemStruct;

/**
 * <pre>
 * 未清供应商数据抬头(VendorTitle)JDBC
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
public class VendorTitleJdbcDao extends BaseJdbcDao
{

	/**
	 * 根据未清供应商数据抬头ID(可以多个逗号隔开),取得供应商下未清发票数据集合
	 * 
	 * @param user
	 * @return
	 */
	public List<VendorTitle> getVendorTitleList(String vendortitleids)
	{
		List<VendorTitle> vendorTitles = new ArrayList<VendorTitle>();
		String ids = vendortitleids.replaceAll(",", "','");
		String sql = "select * from YVENDORTITLE where VENDORTITLEID in('" + ids + "')";
		List<Map> rowList = this.getJdbcTemplate().queryForList(sql);
		for (Map map : rowList)
		{
			VendorTitle vendorTitle = new VendorTitle();
			ExBeanUtils.setBeanValueFromMap(vendorTitle, map);
			vendorTitles.add(vendorTitle);
		}

		return vendorTitles;
	}

	/**
	 * 获取发票
	 * 
	 * @param invoice
	 * @return
	 */
	public List<VendorTitle> getByContract(String contractnos)
	{
		List<VendorTitle> vendorTitleList = new ArrayList<VendorTitle>();
		String hql = "select * from YVENDORTITLE where VERKF in (" + contractnos + ")";
		
		List<Map> rowList = this.getJdbcTemplate().queryForList(hql);
		log.debug("根据发票取得:" + hql);
		for (Map map : rowList){
			VendorTitle vendorTitle = new VendorTitle();
			ExBeanUtils.setBeanValueFromMap(vendorTitle, map);
			vendorTitleList.add(vendorTitle);
		}
		return vendorTitleList;
	}
	
	/**
	 * 更新未清供应商抬头是否已结清标志:
	 * 
	 * @param vendortitleid
	 * @param isCleared
	 */
	public void updateVendorTitleIsCleared(String vendortitleid, String isCleared)
	{
		String sql = "update YVENDORTITLE set ISCLEARED ='" + isCleared + "' where VENDORTITLEID ='" + vendortitleid + "'";
		log.debug("更新未清供应商抬头是否已结清标志:" + sql);
		this.getJdbcTemplate().execute(sql);
	}

	/**
	 * 更新未清供应商抬头是否已结清标志:
	 * 
	 * @param vendortitleidsSqlIn
	 * @param isCleared
	 */
	public void updateIsCleared(String vendortitleidsSqlIn, String isCleared)
	{
		String sql = "update YVENDORTITLE set ISCLEARED ='" + isCleared + "' where VENDORTITLEID in(" + vendortitleidsSqlIn + ")";
		log.debug("更新未清供应商抬头是否已结清标志:" + sql);
		this.getJdbcTemplate().execute(sql);
	}

	/**
	 * 更新未清供应商抬头是否已结清标志:
	 * 
	 * @param contractNos
	 * @param isCleared
	 */
	public void updateIsClearedByContractNo(String contractNos, String isCleared)
	{
		String sql = "update YVENDORTITLE set ISCLEARED ='" + isCleared + "' where VERKF in(" + contractNos + ")";
		log.debug("更新未清供应商抬头是否已结清标志:" + sql);
		this.getJdbcTemplate().execute(sql);
	}

	/**
	 * 更新未清供应商抬头是否已结清标志:
	 * 
	 * @param contractNos
	 * @param projectNos
	 * @param isCleared
	 */
	public void updateIsClearedByProjectNo(String contractNos, String projectNos, String isCleared)
	{
		String sql = "update YVENDORTITLE set ISCLEARED ='" + isCleared + "' where LIXIANG in(" + projectNos + ") ";
		if (!StringUtils.isNullBlank(contractNos))
			sql += " and VERKF not in(" + contractNos + ")";
		log.debug("更新未清供应商抬头是否已结清标志:" + sql);
		this.getJdbcTemplate().execute(sql);
	}

	/**
	 * 更新未清供应商抬头是否已结清标志:
	 * 
	 * @param supplier
	 * @param contractNos
	 * @param projectNos
	 * @param isCleared
	 */
	public void updateIsClearedBySupplier(String supplier, String contractNos, String projectNos, String isCleared)
	{
		String sql = "update YVENDORTITLE set ISCLEARED ='" + isCleared + "' where LIFNR='" + supplier + "'";
		if (!StringUtils.isNullBlank(contractNos))
			sql += " and VERKF not in(" + contractNos + ")";
		if (!StringUtils.isNullBlank(projectNos))
			sql += " and LIXIANG not in(" + projectNos + ")";
		log.debug("更新未清供应商抬头是否已结清标志:" + sql);
		this.getJdbcTemplate().execute(sql);
	}

	/**
	 * 根据供应商编号、科目，取得供应商下未清发票数据集合
	 * 
	 * @param supplier
	 * @param shkzg
	 *            借方/贷方标识
	 */
	public List<VendorTitle> getVendorTitleListBySupplier(String supplier, String subject, String shkzg)
	{
		List<VendorTitle> vendorTitles = new ArrayList<VendorTitle>();
		String sql = "select * from YVENDORTITLE where LIFNR ='" + supplier + "' and SAKNR='" + subject + "' and ISCLEARED='" + Constants.cleared.notCleared + "'";
		if (!StringUtils.isNullBlank(shkzg))
		{
			sql = sql + " and SHKZ='" + shkzg + "'";
		}
		List<Map> rowList = this.getJdbcTemplate().queryForList(sql);
		log.debug("根据供应商编号、科目，取得供应商下未清发票数据集合:" + sql);
		for (Map map : rowList)
		{
			VendorTitle vendorTitle = new VendorTitle();
			ExBeanUtils.setBeanValueFromMap(vendorTitle, map);
			vendorTitles.add(vendorTitle);
		}

		return vendorTitles;
	}

	/**
	 * 根据供应商编号,科目，货币文本，公司代码,取得供应商下未清发票数据集合
	 * @param supplier
	 * @param subject
	 * 
	 * @param currencytext
	 * @param companyno
	 * @param shkzg
	 * @return
	 */
	public List<VendorTitle> getVendorTitleListBySupplier(String supplier, String subject,String currencytext ,String companyno,String depid, String shkzg)
	{
		List<VendorTitle> vendorTitles = new ArrayList<VendorTitle>();
		String sql = "select * from YVENDORTITLE where LIFNR ='" + supplier + "' and SAKNR='" + subject + "' and WAERS='" + currencytext + "' and BUKRS='" + companyno +  "' and GSBER='" + depid +  "' and ISCLEARED='" + Constants.cleared.notCleared + "'  ";
//		String sql = "select * from YVENDORTITLE where LIFNR ='" + supplier + "' and SAKNR='" + subject + "' and WAERS='" + currencytext + "' and BUKRS='" + companyno +   "' and ISCLEARED='" + Constants.cleared.notCleared + "'";
		if (!StringUtils.isNullBlank(shkzg))
		{
			sql = sql + " and SHKZ='" + shkzg + "'";
		}
		List<Map> rowList = this.getJdbcTemplate().queryForList(sql);
		log.debug("根据供应商编号、科目，货币文本，公司代码，取得供应商下未清发票数据集合:" + sql);
		for (Map map : rowList)
		{
			VendorTitle vendorTitle = new VendorTitle();
			ExBeanUtils.setBeanValueFromMap(vendorTitle, map);
			vendorTitles.add(vendorTitle);
		}

		return vendorTitles;
	}
	/**
	 * 根据业务状态，取得发票已经审批完金额、在途金额。
	 * 
	 * @param billno
	 * @param businessstate
	 * @return
	 */
	public BigDecimal getSumClearAmount(String invoice, String businessstates)
	{

		StringBuffer sb = new StringBuffer();
		sb.append("select nvl(sum(amount),0) from (");
		sb.append("select nvl(sum(clearbillamount),0) as amount from YBILLCLEARITEM a ,YBILLCLEAR b ");
		sb.append(" where a.BILLCLEARID=b.BILLCLEARID and b.CLEARTYPE='2' and a.INVOICE=trim('" + invoice + "') and trim(a.INVOICE) is not null  and b.BUSINESSSTATE in(" + businessstates + ")");

		sb.append(" union ");

		sb.append("select nvl(sum(CLEARAMOUNT),0) as amount from YUNCLEARSUPPBILL a ,YSUPPLIERSICLEAR b ");
		sb.append(" where a.SUPPLIERSCLEARID=b.SUPPLIERSCLEARID and b.BUSINESSSTATE in(" + businessstates + ")  and trim(a.INVOICE) is not null  and a.invoice=trim('" + invoice + "')");

		sb.append(" union ");

//		sb.append(" select nvl(sum( a.CLEARAMOUNT2),0) as amount from YPAYMENTCBILL a ,YPAYMENT b where a.PAYMENTID=b.PAYMENTID and b.PAYMENTSTATE in('0','1')");
		sb.append(" select nvl(sum( a.CLEARAMOUNT2),0) as amount from YPAYMENTCBILL a ,YPAYMENT b where a.PAYMENTID=b.PAYMENTID ");
		sb.append(" and b.BUSINESSSTATE in(" + businessstates + ")  and trim(a.BILLNO) is not null  and a.BILLNO=trim('" + invoice + "')");
		sb.append(" )");

		log.debug("根据业务状态，取得发票已经审批完金额、在途金额:" + sb.toString());
		BigDecimal list = (BigDecimal) this.getJdbcTemplate().queryForObject(sb.toString(), BigDecimal.class);

		if (null == list)
			return new BigDecimal(0);
		else
			return list;
	}
	
	/**
	 * 根据业务状态，取得发票已经审批完金额、在途金额。去掉本次的
	 * 
	 * @param billno
	 * @param businessstate
	 * @param billclearid
	 * @return
	 */
	public BigDecimal getSumClearAmount(String invoice, String businessstates,String billclearid)
	{

		StringBuffer sb = new StringBuffer();
		sb.append("select nvl(sum(amount),0) from (");
		sb.append("select nvl(sum(clearbillamount),0) as amount from YBILLCLEARITEM a ,YBILLCLEAR b ");
		sb.append(" where a.BILLCLEARID=b.BILLCLEARID and b.CLEARTYPE='2' and a.INVOICE=trim('" + invoice + "') and trim(a.INVOICE) is not null  and b.BUSINESSSTATE in(" + businessstates + ")");
		if(null != billclearid && !"".equals(billclearid)){
			sb.append(" and a.BILLCLEARID!='" + billclearid + "'" );
		}
		sb.append(" union ");

		sb.append("select nvl(sum(CLEARAMOUNT),0) as amount from YUNCLEARSUPPBILL a ,YSUPPLIERSICLEAR b ");
		sb.append(" where a.SUPPLIERSCLEARID=b.SUPPLIERSCLEARID and b.BUSINESSSTATE in(" + businessstates + ") and trim(a.INVOICE) is not null  and a.invoice=trim('" + invoice + "')");

		sb.append(" union ");

//		sb.append(" select nvl(sum( a.CLEARAMOUNT2),0) as amount from YPAYMENTCBILL a ,YPAYMENT b where a.PAYMENTID=b.PAYMENTID and b.PAYMENTSTATE in('0','1')");
		sb.append(" select nvl(sum( a.CLEARAMOUNT2),0) as amount from YPAYMENTCBILL a ,YPAYMENT b where a.PAYMENTID=b.PAYMENTID ");
		sb.append(" and b.BUSINESSSTATE in(" + businessstates + ") and trim(a.BILLNO) is not null  and a.BILLNO=trim('" + invoice + "')");
		sb.append(" )");

		log.debug("根据业务状态，取得发票已经审批完金额、在途金额:" + sb.toString());
		BigDecimal list = (BigDecimal) this.getJdbcTemplate().queryForObject(sb.toString(), BigDecimal.class);

		if (null == list)
			return new BigDecimal(0);
		else
			return list;
	}
	
	/**
	 * 根据业务状态，取得发票已经审批完金额、在途金额。
	 */
	public BigDecimal getSumClearAmount(String invoice, String billClear, String singleClear, String paymentState){

		StringBuffer sb = new StringBuffer();
		sb.append("select nvl(sum(amount),0) from (");
		sb.append("select nvl(sum(clearbillamount),0) as amount from YBILLCLEARITEM a ,YBILLCLEAR b ");
		sb.append(" where a.BILLCLEARID=b.BILLCLEARID and b.CLEARTYPE='2' and a.INVOICE=trim('" + invoice + "')  and trim(a.INVOICE) is not null   and b.BUSINESSSTATE in(" + billClear + ")");

		sb.append(" union ");

		sb.append("select nvl(sum(CLEARAMOUNT),0) as amount from YUNCLEARSUPPBILL a ,YSUPPLIERSICLEAR b ");
		sb.append(" where a.SUPPLIERSCLEARID=b.SUPPLIERSCLEARID and b.BUSINESSSTATE in(" + singleClear + ")  and trim(a.INVOICE) is not null   and a.invoice=trim('" + invoice + "')");

		sb.append(" union ");

//		sb.append(" select nvl(sum( a.CLEARAMOUNT2),0) as amount from YPAYMENTCBILL a ,YPAYMENT b where a.PAYMENTID=b.PAYMENTID and b.PAYMENTSTATE in('0','1')");
		sb.append(" select nvl(sum( a.CLEARAMOUNT2),0) as amount from YPAYMENTCBILL a ,YPAYMENT b where a.PAYMENTID=b.PAYMENTID ");
		sb.append(" and b.BUSINESSSTATE in(" + paymentState + ")  and trim(a.BILLNO) is not null   and a.BILLNO=trim('" + invoice + "')");
		sb.append(" )");

		log.debug("根据业务状态，取得发票已经审批完金额、在途金额:" + sb.toString());
		BigDecimal amount = (BigDecimal) this.getJdbcTemplate().queryForObject(sb.toString(), BigDecimal.class);

		if (null == amount)
			return new BigDecimal(0);
		else
			return amount;
	}

	/**
	 * 根据业务状态，取得发票已经审批完金额、在途金额。
	 * 
	 * @param billno
	 * @param businessstate
	 * @return
	 */
	public BigDecimal getSumClearAmountVendorTitleId(String vendorTitleId, String businessstates)
	{

		StringBuffer sb = new StringBuffer();
		sb.append("select nvl(sum(amount),0) from (");
		sb.append("select nvl(sum(CLEARAMOUNT),0) as amount from YUNCLEARSUPPBILL a ,YSUPPLIERSICLEAR b ");
		sb.append(" where a.SUPPLIERSCLEARID=b.SUPPLIERSCLEARID and b.BUSINESSSTATE in(" + businessstates + ") and a.vendortitleid='" + vendorTitleId + "'");
		
		sb.append(" )");

		log.debug("根据业务状态，取得发票已经审批完金额、在途金额:" + sb.toString());
		BigDecimal list = (BigDecimal) this.getJdbcTemplate().queryForObject(sb.toString(), BigDecimal.class);

		if (null == list)
			return new BigDecimal(0);
		else
			return list;
	}
	/**
	 * 更新未清供应商抬头数据上是否已结清标志
	 * 
	 * @param invoice
	 *            发票号
	 * @param isCleared
	 *            是否结清标志。
	 */
	public void updateVendorTitleIsClearedByInvoice(String invoice, String isCleared)
	{
		String sql = "update YVENDORTITLE set ISCLEARED ='" + isCleared + "' where INVOICE =trim('" + invoice + "')";
		log.debug("更新未清供应商抬头数据上是否已结清标志:" + sql);
		this.getJdbcTemplate().execute(sql);
	}

	/**
	 * 得到本次清帐票的信息
	 * 
	 * @param strBillNo
	 * @return
	 */
	public ClearVoucherItemStruct getCurrectBillInfo(String strBillNo)
	{
		final ClearVoucherItemStruct clearVoucherItemStruct = new ClearVoucherItemStruct();
//		String strSql = "select a.belnr, a.gjahr, a.buzei,bwbje from YVENDORTITLE a where a.invoice = trim('" + strBillNo + "')   and a.STJAH='0000'   and a.SHKZG='H' ";
		String strSql = "select a.belnr, a.gjahr, a.buzei,bwbje from YVENDORTITLE a where a.invoice = trim('" + strBillNo + "')     and a.SHKZG='H' ";
		log.debug("得到本次清帐票的信息:" + strSql);
		getJdbcTemplate().query(strSql, new RowCallbackHandler()
		{
			public void processRow(ResultSet rs) throws SQLException
			{
				clearVoucherItemStruct.setVoucherno(rs.getString("belnr"));
				clearVoucherItemStruct.setRowno(rs.getString("buzei"));
				clearVoucherItemStruct.setYear(rs.getString("gjahr"));
				clearVoucherItemStruct.setBwbje(rs.getBigDecimal("bwbje"));
			}
		});

		return clearVoucherItemStruct;
	}

	/**
	 * 得到本次分配项清信息
	 * 
	 * @param strPaymentId
	 * @param strPaymentItemId
	 * @return
	 */
	public ClearVoucherItemStruct getCurrentPayItemInfo(String strPaymentId, String strPaymentItemId)
	{
		final ClearVoucherItemStruct clearVoucherItemStruct = new ClearVoucherItemStruct();

		String strSql = "select b.voucherno,a.rownumber,b.fiyear,a.amount2 from YVOUCHERITEM a, YVOUCHER b ";
		strSql += " where a.voucherid = b.voucherid ";
		strSql += " and a.businessitemid = '" + strPaymentItemId + "' and b.businessid = '" + strPaymentId + "'";
		log.debug("得到本次分配项清信息:" + strSql);
		getJdbcTemplate().query(strSql, new RowCallbackHandler()
		{
			public void processRow(ResultSet rs) throws SQLException
			{
				clearVoucherItemStruct.setVoucherno(rs.getString("voucherno"));
				clearVoucherItemStruct.setRowno(rs.getString("rownumber"));
				clearVoucherItemStruct.setYear(rs.getString("fiyear"));
				clearVoucherItemStruct.setBwbje(rs.getBigDecimal("amount2"));
			}
		});

		return clearVoucherItemStruct;
	}

	/**
	 * 得到合同清帐票的信息
	 * 
	 * @param strBillNo
	 * @return
	 */
	public List<ClearVoucherItemStruct> getContractBillInfo(String supplier,String strContractNo)
	{
		final List<ClearVoucherItemStruct> clearVoucherItemStructList = new ArrayList<ClearVoucherItemStruct>();

		String strSql = "select a.BELNR, a.GJAHR, a.BUZEI,a.BWBJE from YVENDORTITLE a ";
//		strSql += " where a.VERKF = '" + strContractNo + "' and trim(a.VERKF) is not null  and a.LIFNR='" + supplier+"'  and a.STJAH='0000'   and a.SHKZG='H' ";
		strSql += " where a.VERKF = '" + strContractNo + "' and trim(a.VERKF) is not null  and a.LIFNR='" + supplier+"'     and a.SHKZG='H' ";
		strSql += " and a.ISCLEARED = '" + cleared.isCleared + "'";
		log.debug("得到合同清帐票的信息:" + strSql);
		getJdbcTemplate().query(strSql, new RowCallbackHandler()
		{
			public void processRow(ResultSet rs) throws SQLException
			{
				ClearVoucherItemStruct clearVoucherItemStruct = new ClearVoucherItemStruct();

				clearVoucherItemStruct.setVoucherno(rs.getString("BELNR"));
				clearVoucherItemStruct.setRowno(rs.getString("BUZEI"));
				clearVoucherItemStruct.setYear(rs.getString("GJAHR"));
				clearVoucherItemStruct.setBwbje(rs.getBigDecimal("BWBJE"));

				clearVoucherItemStructList.add(clearVoucherItemStruct);
			}
		});

		return clearVoucherItemStructList;
	}

	/**
	 * 得到合同分配项清信息
	 * 
	 * @param strPaymentId
	 * @param strPaymentItemId
	 * @return
	 */
	public List<ClearVoucherItemStruct> getContractPayItemInfo(String strContractNo)
	{
		final List<ClearVoucherItemStruct> clearVoucherItemStructList = new ArrayList<ClearVoucherItemStruct>();

		String strSql = "select b.voucherno, a.rownumber, b.fiyear,a.amount2,a.businessitemid from yvoucheritem a, yvoucher b ";
		strSql += " where a.voucherid = b.voucherid  and a.businessitemid in ";
		strSql += " (select a.paymentitemid " + "from ypaymentitem a, ypayment b where a.paymentid = b.paymentid ";
		strSql += " and a.billisclear = '0' " + "and a.contract_no = '" + strContractNo + "' and trim(a.contract_no) is not null)";
		log.debug("得到合同分配项清信息:" + strSql);
		getJdbcTemplate().query(strSql, new RowCallbackHandler()
		{
			public void processRow(ResultSet rs) throws SQLException
			{
				ClearVoucherItemStruct clearVoucherItemStruct = new ClearVoucherItemStruct();
				clearVoucherItemStruct.setVoucherno(rs.getString("voucherno"));
				clearVoucherItemStruct.setRowno(rs.getString("rownumber"));
				clearVoucherItemStruct.setYear(rs.getString("fiyear"));
				clearVoucherItemStruct.setBwbje(rs.getBigDecimal("amount2"));
				clearVoucherItemStruct.setBusinessitemid(rs.getString("businessitemid"));
				
				clearVoucherItemStructList.add(clearVoucherItemStruct);
			}
		});

		return clearVoucherItemStructList;
	}

	/**
	 * 得到合同分配项清信息
	 * 
	 * @param strPaymentId
	 * @param strPaymentItemId
	 * @return
	 */
	public List<ClearVoucherItemStruct> getContractPayItemInfo2(String supplier,String strContractNo)
	{
		final List<ClearVoucherItemStruct> clearVoucherItemStructList = new ArrayList<ClearVoucherItemStruct>();

		String strSql = "select b.voucherno, a.rownumber, b.fiyear,a.amount2 from yvoucheritem a, yvoucher b ";
		strSql += " where a.voucherid = b.voucherid  and trim(a.taxcode) is null and a.subject='"+supplier+"'  and a.businessitemid in ";
		strSql += " (select a.paymentitemid " + "from ypaymentitem a, ypayment b where a.paymentid = b.paymentid ";
		strSql += " and a.billisclear = '1' " + "and a.contract_no = '" + strContractNo + "' and trim(a.contract_no) is not null)";
		log.debug("得到合同分配项清信息:" + strSql);
		getJdbcTemplate().query(strSql, new RowCallbackHandler()
		{
			public void processRow(ResultSet rs) throws SQLException
			{
				ClearVoucherItemStruct clearVoucherItemStruct = new ClearVoucherItemStruct();
				clearVoucherItemStruct.setVoucherno(rs.getString("voucherno"));
				clearVoucherItemStruct.setRowno(rs.getString("rownumber"));
				clearVoucherItemStruct.setYear(rs.getString("fiyear"));
				clearVoucherItemStruct.setBwbje(rs.getBigDecimal("amount2"));

				clearVoucherItemStructList.add(clearVoucherItemStruct);
			}
		});

		return clearVoucherItemStructList;
	}
	
	/**
	 * 得到立项清帐票的信息
	 * 
	 * @param strBillNo
	 * @return
	 */
	public List<ClearVoucherItemStruct> getProjectBillInfo(String supplier,String strProjecttNo, String strContractList)
	{
		final List<ClearVoucherItemStruct> clearVoucherItemStructList = new ArrayList<ClearVoucherItemStruct>();

//		String strSql = "select a.BELNR, a.GJAHR, a.BUZEI,a.BWBJE from YVENDORTITLE a where a.LIXIANG = '" + strProjecttNo + "' and a.LIFNR='" + supplier+"'   and a.STJAH='0000'   and a.SHKZG='H' ";
		String strSql = "select a.BELNR, a.GJAHR, a.BUZEI,a.BWBJE from YVENDORTITLE a where a.LIXIANG = '" + strProjecttNo + "' and a.LIFNR='" + supplier+"'      and a.SHKZG='H' ";
		strSql += " and trim(a.LIXIANG) is not null " + "and a.ISCLEARED = '" + cleared.isCleared + "' ";
		if (!StringUtils.isNullBlank(strContractList))
			strSql += " and a.VERKF not in (" + strContractList + ")";
		log.debug("得到立项清帐票的信息:" + strSql);
		getJdbcTemplate().query(strSql, new RowCallbackHandler()
		{
			public void processRow(ResultSet rs) throws SQLException
			{
				ClearVoucherItemStruct clearVoucherItemStruct = new ClearVoucherItemStruct();

				clearVoucherItemStruct.setVoucherno(rs.getString("BELNR"));
				clearVoucherItemStruct.setRowno(rs.getString("BUZEI"));
				clearVoucherItemStruct.setYear(rs.getString("GJAHR"));
				clearVoucherItemStruct.setBwbje(rs.getBigDecimal("BWBJE"));

				clearVoucherItemStructList.add(clearVoucherItemStruct);
			}
		});

		return clearVoucherItemStructList;
	}

	/**
	 * 得到立项分配项清信息
	 * 
	 * @param strPaymentId
	 * @param strPaymentItemId
	 * @return
	 */
	public List<ClearVoucherItemStruct> getProjectPayItemInfo(String supplier,String strProjectNo, String strContractList)
	{
		final List<ClearVoucherItemStruct> clearVoucherItemStructList = new ArrayList<ClearVoucherItemStruct>();

		String strSql = "select b.voucherno, a.rownumber, b.fiyear,a.amount2 from yvoucheritem a, yvoucher b ";
		strSql += " where a.voucherid = b.voucherid  and trim(a.taxcode) is null and a.subject='"+supplier+"' and a.businessitemid in (select a.paymentitemid from ypaymentitem a, ypayment b ";
		strSql += " where a.paymentid = b.paymentid and a.billisclear = '" + cleared.isCleared + "' and a.project_no = '" + strProjectNo + "'";
		strSql += "  and trim(a.businessitemid) is not null and trim(a.project_no) is not null ";
		if (!StringUtils.isNullBlank(strContractList))
			strSql += " and a.contract_no not in (" + strContractList + ") ";
		strSql += " )";
		log.debug("得到立项分配项清信息:" + strSql);
		getJdbcTemplate().query(strSql, new RowCallbackHandler()
		{
			public void processRow(ResultSet rs) throws SQLException
			{
				ClearVoucherItemStruct clearVoucherItemStruct = new ClearVoucherItemStruct();

				clearVoucherItemStruct.setVoucherno(rs.getString("voucherno"));
				clearVoucherItemStruct.setRowno(rs.getString("rownumber"));
				clearVoucherItemStruct.setYear(rs.getString("fiyear"));
				clearVoucherItemStruct.setBwbje(rs.getBigDecimal("amount2"));

				clearVoucherItemStructList.add(clearVoucherItemStruct);
			}
		});

		return clearVoucherItemStructList;
	}

	/**
	 * 得到供应商清帐票的信息
	 * 
	 * @param strSupplier
	 * @param strContractList
	 * @param strProjectList
	 * @param bukrs
	 * @return
	 */
	public List<ClearVoucherItemStruct> getSupplierBillInfo(String strSupplier, String strContractList, String strProjectList, String bukrs)
	{
		final List<ClearVoucherItemStruct> clearVoucherItemStructList = new ArrayList<ClearVoucherItemStruct>();

		StringBuffer sb = new StringBuffer();
//		sb.append("select a.BELNR, a.GJAHR, a.BUZEI,a.BWBJE from YVENDORTITLE a where a.LIFNR = '" + strSupplier + "' and a.ISCLEARED = '" + cleared.isCleared + "'   and a.STJAH='0000'   and a.SHKZG='H'  and a.DMBTR !=0 ");
		sb.append("select a.BELNR, a.GJAHR, a.BUZEI,a.BWBJE from YVENDORTITLE a where a.LIFNR = '" + strSupplier + "' and a.ISCLEARED = '" + cleared.isCleared + "'      and a.SHKZG='H'  and a.DMBTR !=0 ");
		if (!StringUtils.isNullBlank(strContractList))
			sb.append(" and a.VERKF not in (" + strContractList + ")");
		if (!StringUtils.isNullBlank(strProjectList))
			sb.append(" and a.LIXIANG not in (" + strProjectList + ")");
		sb.append(" and a.BUKRS ='" + bukrs + "'");
		log.debug("得到供应商清帐票的信息:" + sb.toString());
		getJdbcTemplate().query(sb.toString(), new RowCallbackHandler()
		{
			public void processRow(ResultSet rs) throws SQLException
			{
				ClearVoucherItemStruct clearVoucherItemStruct = new ClearVoucherItemStruct();

				clearVoucherItemStruct.setVoucherno(rs.getString("BELNR"));
				clearVoucherItemStruct.setRowno(rs.getString("BUZEI"));
				clearVoucherItemStruct.setYear(rs.getString("GJAHR"));
				clearVoucherItemStruct.setBwbje(rs.getBigDecimal("BWBJE"));

				clearVoucherItemStructList.add(clearVoucherItemStruct);
			}
		});

		return clearVoucherItemStructList;
	}

	/**
	 * 取得SAP手工做账的被部分清的汇损凭证
	 * @param customer
	 * @param strContractList
	 * @param strProjectList
	 * @param bukrs
	 * @return
	 */
	public List<ClearVoucherItemStruct> getSupplierVoucherInfo(String strSupplier, String strContractList, String strProjectList, String bukrs)
	{
		final List<ClearVoucherItemStruct> clearVoucherItemStructList = new ArrayList<ClearVoucherItemStruct>();
		StringBuffer sb = new StringBuffer();
//		sb.append("select a.BELNR, a.GJAHR, a.BUZEI,a.BWBJE from YVENDORTITLE a where a.LIFNR = '" + strSupplier + "' and a.ISCLEARED = '" + cleared.isCleared + "'   and a.STJAH='0000'   and a.SHKZG='S'  and a.DMBTR =0 ");
		sb.append("select a.BELNR, a.GJAHR, a.BUZEI,a.BWBJE from YVENDORTITLE a where a.LIFNR = '" + strSupplier + "' and a.ISCLEARED = '" + cleared.isCleared + "'      and a.SHKZG='S'  and a.DMBTR =0 ");
		if (!StringUtils.isNullBlank(strContractList))
			sb.append(" and a.VERKF not in (" + strContractList + ")");
		if (!StringUtils.isNullBlank(strProjectList))
			sb.append(" and a.LIXIANG not in (" + strProjectList + ")");
		sb.append(" and a.BUKRS ='" + bukrs + "'");
		log.debug("得到供应商清帐票的信息:" + sb.toString());
		getJdbcTemplate().query(sb.toString(), new RowCallbackHandler()
		{
			public void processRow(ResultSet rs) throws SQLException
			{
				ClearVoucherItemStruct clearVoucherItemStruct = new ClearVoucherItemStruct();

				clearVoucherItemStruct.setVoucherno(rs.getString("BELNR"));
				clearVoucherItemStruct.setRowno(rs.getString("BUZEI"));
				clearVoucherItemStruct.setYear(rs.getString("GJAHR"));
				clearVoucherItemStruct.setBwbje(rs.getBigDecimal("BWBJE"));

				clearVoucherItemStructList.add(clearVoucherItemStruct);
			}
		});

		return clearVoucherItemStructList;
	}
	/**
	 *  取得供应商下所有的外围已清的凭证信息（借贷方）
	 * @param strSupplier
	 * @param strContractList
	 * @param strProjectList
	 * @param bukrs
	 * @return
	 */
	public List<ClearVoucherItemStruct> getSupplierVoucherInfo2(String strSupplier, String strContractList, String strProjectList, String bukrs,String gsber ,String saknr ,String waers)
	{
		final List<ClearVoucherItemStruct> clearVoucherItemStructList = new ArrayList<ClearVoucherItemStruct>();
		StringBuffer sb = new StringBuffer();
//		sb.append("select a.BELNR, a.GJAHR, a.BUZEI,a.BWBJE from YVENDORTITLE a where a.LIFNR = '" + strSupplier + "' and a.ISCLEARED = '" + cleared.isCleared + "'   and a.STJAH='0000'  ");
		sb.append("select a.BELNR, a.GJAHR, a.BUZEI,a.BWBJE from YVENDORTITLE a where a.LIFNR = '" + strSupplier + "' and a.ISCLEARED = '" + cleared.isCleared + "' and a.gsber='" +gsber+  "' and a.saknr='" +saknr+  "' and a.waers='" +waers+"' ");
		if (!StringUtils.isNullBlank(strContractList))
			sb.append(" and a.VERKF not in (" + strContractList + ")");
		if (!StringUtils.isNullBlank(strProjectList))
			sb.append(" and a.LIXIANG not in (" + strProjectList + ")");
		sb.append(" and a.BUKRS ='" + bukrs + "'");
		log.debug("得到供应商清帐所有的信息:" + sb.toString());
		getJdbcTemplate().query(sb.toString(), new RowCallbackHandler()
		{
			public void processRow(ResultSet rs) throws SQLException
			{
				ClearVoucherItemStruct clearVoucherItemStruct = new ClearVoucherItemStruct();

				clearVoucherItemStruct.setVoucherno(rs.getString("BELNR"));
				clearVoucherItemStruct.setRowno(rs.getString("BUZEI"));
				clearVoucherItemStruct.setYear(rs.getString("GJAHR"));
				clearVoucherItemStruct.setBwbje(rs.getBigDecimal("BWBJE"));

				clearVoucherItemStructList.add(clearVoucherItemStruct);
			}
		});

		return clearVoucherItemStructList;
	}
	
	/**
	 * 得到供应商分配项清信息
	 * 
	 * @param strSupplier
	 * @param strContractList
	 * @param strProjectList
	 * @param bukrs
	 * @return
	 */
	public List<ClearVoucherItemStruct> getSupplierPayItemInfo(String strSupplier, String strContractList, String strProjectList, String bukrs)
	{
		final List<ClearVoucherItemStruct> clearVoucherItemStructList = new ArrayList<ClearVoucherItemStruct>();

		String strSql = "select b.voucherno, a.rownumber, b.fiyear,a.amount2 from yvoucheritem a, yvoucher b ";
		strSql += " where a.voucherid = b.voucherid  and trim(a.taxcode) is null and b.companycode = '" + bukrs + "' and a.businessitemid in (select a.paymentitemid from ypaymentitem a, ypayment b ";
		strSql += " where a.paymentid = b.paymentid and a.billisclear = '" + cleared.isCleared + "' and b.supplier = '" + strSupplier + "' ";
		if (!StringUtils.isNullBlank(strProjectList))
			strSql += " and a.project_no not in (" + strProjectList + ") ";
		if (!StringUtils.isNullBlank(strContractList))
			strSql += " and a.contract_no not in (" + strContractList + ") ";
		strSql += ")";
		log.debug("得到供应商分配项清信息:" + strSql);

		getJdbcTemplate().query(strSql, new RowCallbackHandler()
		{
			public void processRow(ResultSet rs) throws SQLException
			{
				ClearVoucherItemStruct clearVoucherItemStruct = new ClearVoucherItemStruct();

				clearVoucherItemStruct.setVoucherno(rs.getString("voucherno"));
				clearVoucherItemStruct.setRowno(rs.getString("rownumber"));
				clearVoucherItemStruct.setYear(rs.getString("fiyear"));
				clearVoucherItemStruct.setBwbje(rs.getBigDecimal("amount2"));

				clearVoucherItemStructList.add(clearVoucherItemStruct);
			}
		});

		return clearVoucherItemStructList;
	}

	/**
	 * 根据合同号取得已清发票金额合计
	 * 
	 * @param contractNo
	 * @return
	 */
	public BigDecimal getSumAmount(String contractNo)
	{
		StringBuffer sb = new StringBuffer();
		sb.append("select nvl(sum(DMBTR),0) as AMOUNT from YVENDORTITLE a ");
//		sb.append(" where a.ISCLEARED='" + cleared.isCleared + "' and a.VERKF='" + contractNo + "'  and a.STJAH='0000' and a.SHKZG='H' ");
		sb.append(" where a.ISCLEARED='" + cleared.isCleared + "' and a.VERKF='" + contractNo + "'  and a.SHKZG='H' ");

		BigDecimal list = (BigDecimal) this.getJdbcTemplate().queryForObject(sb.toString(), BigDecimal.class);
		log.debug("根据合同号取得已清发票金额合计:" + sb.toString());

		if (null == list)
			return new BigDecimal(0);
		else
			return list;
	}

	/**
	 * 根据立项号取得已清发票金额合计（排除合同层全清的合同号）
	 * 
	 * @param projectNo
	 * @param contractNos
	 * @return
	 */
	public BigDecimal getSumAmount(String projectNo, String contractNos)
	{
		StringBuffer sb = new StringBuffer();
		sb.append("select nvl(sum(DMBTR),0) as AMOUNT from YVENDORTITLE a ");
//		sb.append(" where a.ISCLEARED='" + cleared.isCleared + "' and a.LIXIANG='" + projectNo + "'   and a.STJAH='0000'  and a.SHKZG='H' ");
		sb.append(" where a.ISCLEARED='" + cleared.isCleared + "' and a.LIXIANG='" + projectNo + "'    and a.SHKZG='H' ");
		if (!StringUtils.isNullBlank(contractNos))
			sb.append("and a.VERKF not in(" + contractNos + ") ");
		BigDecimal list = (BigDecimal) this.getJdbcTemplate().queryForObject(sb.toString(), BigDecimal.class);
		log.debug("根据立项号取得已清发票金额合计:" + sb.toString());

		if (null == list)
			return new BigDecimal(0);
		else
			return list;
	}

	/**
	 * 根据供应商取得已清发票金额合计（排除立项层，合同层全清的合同号）
	 * 
	 * @param projectNo
	 * @param contractNos
	 * @return
	 */
	public BigDecimal getSumAmount(String supplier, String contractNos, String projectNos, String bukrs)
	{
		StringBuffer sb = new StringBuffer();
		sb.append("select nvl(sum(DMBTR),0) as AMOUNT from YVENDORTITLE a ");
		sb.append(" where a.ISCLEARED='" + cleared.isCleared + "' and a.LIFNR='" + supplier + "'  and a.SHKZG='H' ");
		if (!StringUtils.isNullBlank(contractNos))
			sb.append("and a.VERKF not in(" + contractNos + ") ");
		if (!StringUtils.isNullBlank(projectNos))
			sb.append("and a.LIXIANG not in(" + projectNos + ") ");
		sb.append(" and a.bukrs ='" + bukrs + "'");
		BigDecimal list = (BigDecimal) this.getJdbcTemplate().queryForObject(sb.toString(), BigDecimal.class);
		log.debug("根据供应商取得已清发票金额合计:" + sb.toString());

		if (null == list)
			return new BigDecimal(0);
		else
			return list;
	}
	
	/**
	 * 根据供应商取得已清款金额合计（排除立项层，合同层全清的合同号）
	 * 
	 * @param projectNo
	 * @param contractNos
	 * @return
	 */
	public BigDecimal getSumAmount2(String supplier, String contractNos, String projectNos, String bukrs)
	{
		StringBuffer sb = new StringBuffer();
		sb.append("select nvl(sum(DMBTR),0) as AMOUNT from YVENDORTITLE a ");
		sb.append(" where a.ISCLEARED='" + cleared.isCleared + "' and a.LIFNR='" + supplier + "'  and a.SHKZG='S' ");
		if (!StringUtils.isNullBlank(contractNos))
			sb.append("and a.VERKF not in(" + contractNos + ") ");
		if (!StringUtils.isNullBlank(projectNos))
			sb.append("and a.LIXIANG not in(" + projectNos + ") ");
		sb.append(" and a.bukrs ='" + bukrs + "'");
		BigDecimal list = (BigDecimal) this.getJdbcTemplate().queryForObject(sb.toString(), BigDecimal.class);
		log.debug("根据供应商取得已清款金额合计:" + sb.toString());

		if (null == list)
			return new BigDecimal(0);
		else
			return list;
	}
	/**
	 * 
	 * @param supplier
	 * @param contractNos
	 * @param projectNos
	 * @param bukrs
	 * @param shkzg
	 * @param waers
	 * @param gsber
	 * @param saknr
	 * @return
	 */
	public BigDecimal getSumAmount2(String supplier, String contractNos, String projectNos, String bukrs,String shkzg,String waers ,String gsber,String saknr)
	{
		StringBuffer sb = new StringBuffer();
		sb.append("select nvl(sum(DMBTR),0) as AMOUNT from YVENDORTITLE a ");
		sb.append(" where a.ISCLEARED='" + cleared.isCleared + "' and a.LIFNR='" + supplier + "' and SHKZG='"+shkzg+"'  AND a.saknr='"+saknr+"' AND a.gsber='"+gsber+"' AND a.waers='"+waers+"' ");
		if (!StringUtils.isNullBlank(contractNos))
			sb.append("and a.VERKF not in(" + contractNos + ") ");
		if (!StringUtils.isNullBlank(projectNos))
			sb.append("and a.LIXIANG not in(" + projectNos + ") ");
		sb.append(" and a.bukrs ='" + bukrs + "'");
		BigDecimal list = (BigDecimal) this.getJdbcTemplate().queryForObject(sb.toString(), BigDecimal.class);
		log.debug("根据供应商取得已清款金额合计:" + sb.toString());

		if (null == list)
			return new BigDecimal(0);
		else
			return list;
	}
	
	/**
	 * 据客户取得外围已清本位币金额合计（排除立项、合同、公司）
	 * @param supplier
	 * @param contractNos
	 * @param projectNos
	 * @param bukrs
	 * @param shkzg
	 * @return
	 */
	public BigDecimal getSumBwbje(String supplier, String contractNos, String projectNos, String bukrs,String shkzg,String waers ,String gsber,String saknr)
	{
		StringBuffer sb = new StringBuffer();
		sb.append("select nvl(sum(bwbje),0) as AMOUNT from YVENDORTITLE a ");
		sb.append(" where a.ISCLEARED='" + cleared.isCleared + "' and a.LIFNR='" + supplier + "'  and a.SHKZG='"+shkzg+"' AND a.saknr='"+saknr+"' AND a.gsber='"+gsber+"' AND a.waers='"+waers+"' ");
		if (!StringUtils.isNullBlank(contractNos))
			sb.append("and a.VERKF not in(" + contractNos + ") ");
		if (!StringUtils.isNullBlank(projectNos))
			sb.append("and a.LIXIANG not in(" + projectNos + ") ");
		sb.append(" and a.bukrs ='" + bukrs + "'");
		BigDecimal list = (BigDecimal) this.getJdbcTemplate().queryForObject(sb.toString(), BigDecimal.class);
		log.debug("根据供应商取得已清发票金额合计:" + sb.toString());

		if (null == list)
			return new BigDecimal(0);
		else
			return list;
	}
	
	/**
	 * 得到押汇的借款凭证
	 * 
	 * @param strPaymentId
	 * @param strCheckcode
	 * @return
	 */
	public List<ClearVoucherItemStruct> getLoanItemInfo(String strPaymentId, 
			String strCheckcode, 
			String strSubject,
			String strVoucherclass )
	{
		final List<ClearVoucherItemStruct> clearVoucherItemStructList = new ArrayList<ClearVoucherItemStruct>();

		String strSql = "select b.voucherno, a.rownumber, b.fiyear, a.amount2,a.subject,a.subjectdescribe, b.voucherid " + 
		   "from yvoucheritem a, yvoucher b " +  
		   "where a.voucherid = b.voucherid " + 
		   "and b.voucherclass = '"+strVoucherclass+"' " + 
		   "and b.businessid = '" + strPaymentId + "' " + 
		   "and a.checkcode = '" + strCheckcode + "' " + 
		   "and a.subject in ('" + strSubject + "')";

		getJdbcTemplate().query(strSql, new RowCallbackHandler()
		{
			public void processRow(ResultSet rs) throws SQLException
			{
				ClearVoucherItemStruct clearVoucherItemStruct = new ClearVoucherItemStruct();

				clearVoucherItemStruct.setVoucherno(rs.getString("voucherno"));
				clearVoucherItemStruct.setRowno(rs.getString("rownumber"));
				clearVoucherItemStruct.setYear(rs.getString("fiyear"));
				clearVoucherItemStruct.setBwbje(rs.getBigDecimal("amount2"));
				clearVoucherItemStruct.setSubject(rs.getString("subject"));
				clearVoucherItemStruct.setSubjectdescribe(rs.getString("subjectdescribe"));
                clearVoucherItemStruct.setVoucherid(rs.getString("voucherid"));

				clearVoucherItemStructList.add(clearVoucherItemStruct);
			}
		});

		return clearVoucherItemStructList;
	}

	/**
	 * 得到应付票
	 * 
	 * @param strPaymentId
	 * @param strCheckcode
	 * @param strGlflag
	 * @return
	 */
	public List<ClearVoucherItemStruct> getBillClearItemInfo(String strPaymentId, String strCheckcode, String strGlflag,String voucherClass)
	{
		final List<ClearVoucherItemStruct> clearVoucherItemStructList = new ArrayList<ClearVoucherItemStruct>();

		String strSql = "select b.voucherno, a.rownumber, b.fiyear, a.amount2,a.subject,a.subjectdescribe " + 
		"from yvoucheritem a, yvoucher b " + 
		"where a.voucherid = b.voucherid " + 
		"and b.businessid = '" + strPaymentId + "' " + 
		"and a.checkcode = '" + strCheckcode + "' " + 
		"and b.voucherclass = '"+voucherClass+"' " + 
		"and a.glflag ='" + strGlflag + "'";

		getJdbcTemplate().query(strSql, new RowCallbackHandler()
		{
			public void processRow(ResultSet rs) throws SQLException
			{
				ClearVoucherItemStruct clearVoucherItemStruct = new ClearVoucherItemStruct();

				clearVoucherItemStruct.setVoucherno(rs.getString("voucherno"));
				clearVoucherItemStruct.setRowno(rs.getString("rownumber"));
				clearVoucherItemStruct.setYear(rs.getString("fiyear"));
				clearVoucherItemStruct.setBwbje(rs.getBigDecimal("amount2"));
				clearVoucherItemStruct.setSubject(rs.getString("subject"));
				clearVoucherItemStruct.setSubjectdescribe(rs.getString("subjectdescribe"));

				clearVoucherItemStructList.add(clearVoucherItemStruct);
			}
		});

		return clearVoucherItemStructList;
	}

	/**
	 * 通过背书编号得到收款的应收票据信息
	 * 
	 * @param strBussinessId
	 * @param strCustomer
	 * @param strCheckcode
	 * @param strGlflag
	 * @return
	 */
	public List<ClearVoucherItemStruct> getCollectRepeatLessonInfo(String strBussinessId, String strCustomer, String strCheckcode, String strGlflag)
	{
		final List<ClearVoucherItemStruct> clearVoucherItemStructList = new ArrayList<ClearVoucherItemStruct>();
		String strSql = "select b.voucherno, a.rownumber, b.fiyear, a.amount2,a.subject,a.subjectdescribe " + 
		"from yvoucheritem a, yvoucher b " + 
		"where a.voucherid = b.voucherid " + 
		"and a.subject='" + strCustomer + "' " + 
		"and b.businessid = '" + strBussinessId + "' " + 
		"and a.checkcode = '" + strCheckcode + "' " + 
		"and a.glflag ='" + strGlflag + "'";

		getJdbcTemplate().query(strSql, new RowCallbackHandler()
		{
			public void processRow(ResultSet rs) throws SQLException
			{
				ClearVoucherItemStruct clearVoucherItemStruct = new ClearVoucherItemStruct();

				clearVoucherItemStruct.setVoucherno(rs.getString("voucherno"));
				clearVoucherItemStruct.setRowno(rs.getString("rownumber"));
				clearVoucherItemStruct.setYear(rs.getString("fiyear"));
				clearVoucherItemStruct.setBwbje(rs.getBigDecimal("amount2"));
				clearVoucherItemStruct.setSubject(rs.getString("subject"));
				clearVoucherItemStruct.setSubjectdescribe(rs.getString("subjectdescribe"));

				clearVoucherItemStructList.add(clearVoucherItemStruct);
			}
		});

		return clearVoucherItemStructList;
	}
	
	/**
	 * 
	 * @param vendortitleid
	 * @return
	 */
	public VendorTitle getByVendortitleid(String vendortitleid)
	{
		String hql = "select * from YVENDORTITLE where VENDORTITLEID = '" + vendortitleid + "'";
		
		List<Map> rowList = this.getJdbcTemplate().queryForList(hql);
		log.debug("根据vendortitleid取得:" + hql);
		VendorTitle vendorTitle = new VendorTitle();
		if (rowList.size() > 0){
			ExBeanUtils.setBeanValueFromMap(vendorTitle, rowList.get(0));
		}
		return vendorTitle;
	}
	
	/**
	 * 
	 * @param vendortitleid
	 * @return
	 */
	public VendorTitle getByInvoice(String invoice)
	{
		String hql = "select * from YVENDORTITLE where invoice = '" + invoice + "'";
		
		List<Map> rowList = this.getJdbcTemplate().queryForList(hql);
		log.debug("根据vendortitleid取得:" + hql);
		VendorTitle vendorTitle = new VendorTitle();
		if (rowList.size() > 0){
			ExBeanUtils.setBeanValueFromMap(vendorTitle, rowList.get(0));
		}
		return vendorTitle;
	}
	
	/**
	 * 
	 * @param vendortitleid
	 * @return
	 */
	public VendorTitle getByVoucherNo(String companycode,String voucherno,String fiyear,String rownumber)
	{
		String hql = "select * from YVENDORTITLE where bukrs = '" + companycode + "' and belnr = '" + voucherno + "' and gjahr = '" + fiyear + "' and buzei = '" + rownumber + "'";

		List<Map> rowList = this.getJdbcTemplate().queryForList(hql);
		log.debug("根据vendortitleno取得:" + hql);
		VendorTitle vendorTitle = new VendorTitle();
		if (rowList.size() > 0){
			ExBeanUtils.setBeanValueFromMap(vendorTitle, rowList.get(0));
		}
		return vendorTitle;
	}
	
	/**
	 * @创建作者：邱杰烜
	 * @修改作者：2010-10-20
	 * 根据供应商编号判断该供应商数据是否已清
	 */
	public boolean isVendorTitleClear(VendorTitle vendorTitle){
		String sql1 = "SELECT COUNT(*) FROM YREFUNDMENT a WHERE a.REFUNDMENTID IN (SELECT b.BUSINESSID FROM YVOUCHER b WHERE b.VOUCHERNO='" +
					 vendorTitle.getBelnr() + 
		 			 "')";
		String sql2 = "SELECT BUSINESSID FROM YVOUCHER WHERE VOUCHERNO='" + vendorTitle.getBelnr() + 
					  "' AND COMPANYCODE='" + vendorTitle.getBukrs() + 
					  "' AND FIYEAR='" + vendorTitle.getGjahr() + "'";
		if(this.getJdbcTemplate().queryForInt(sql1) > 0){						// 若供应商退款凭证已生成
			return true;
		}else{																	// 若在单清与票清楚已经生成凭证
			String sql3 = "SELECT COUNT(*) FROM YSUPPLIERSICLEAR WHERE SUPPLIERSCLEARID IN (" + sql2 + ")";
			String sql4 = "SELECT COUNT(*) FROM YBILLCLEAR WHERE BILLCLEARID IN (" + sql2 + ")";
			if(this.getJdbcTemplate().queryForInt(sql3)>0 || this.getJdbcTemplate().queryForInt(sql4)>0){
				return true;
			}
		}
		return false;
	}
	/**
	 * 根据供应商NO，businState,合同号， isclear=1,取得已清的退款凭证行项目。
	 * @param customerNo
	 * @param businState
	 * @param contractNo
	 * @return
	 */
	public List<ClearVoucherItemStruct> getRefundItemBySupplierNoAndContractNo(String supplierNo,String businState,String contractNo){
		
		final List<ClearVoucherItemStruct> clearVoucherItemStructList = new ArrayList<ClearVoucherItemStruct>();
		String strSql = "select b.voucherno, a.rownumber, b.fiyear,a.amount2 " + "from yvoucheritem a, yvoucher b " + "where a.voucherid = b.voucherid " + "and a.businessitemid in " + "(select a.refundmentitemid " + "from YREFUNDMENTITEM a, yrefundment b " + "where a.refundmentid = b.refundmentid and b.supplier='"+  supplierNo +"' and b.businessstate="+ businState +" and a.contract_no='"+ contractNo +"' and isclear='1' " +  " and trim(a.contract_no) is not null) ";
		log.debug("得到根据供应商NO，businState,合同号， isclear=1,取得已清的退款凭证行项目信息:" + strSql);
		getJdbcTemplate().query(strSql, new RowCallbackHandler()
		{
			public void processRow(ResultSet rs) throws SQLException
			{
				ClearVoucherItemStruct clearVoucherItemStruct = new ClearVoucherItemStruct();

				clearVoucherItemStruct.setVoucherno(rs.getString("voucherno"));
				clearVoucherItemStruct.setRowno(rs.getString("rownumber"));
				clearVoucherItemStruct.setYear(rs.getString("fiyear"));
				clearVoucherItemStruct.setBwbje(rs.getBigDecimal("amount2"));

				clearVoucherItemStructList.add(clearVoucherItemStruct);
			}
		});

		return clearVoucherItemStructList;	
		
	}
	/**
	 * 根据供应商NO，businState,立项号， isclear=1,取得已清的退款行项目。(排除已清的合同号)
	 * @param customerNo
	 * @param businState
	 * @param projectNo
	 * @return
	 */
	public List<ClearVoucherItemStruct> getRefundItemBySupplierNoAndProjectNo(String supplierNo,String businState,String projectNo,String strContractList){
		
		final List<ClearVoucherItemStruct> clearVoucherItemStructList = new ArrayList<ClearVoucherItemStruct>();

		
		String strSql = "select b.voucherno, a.rownumber, b.fiyear,a.amount2 from yvoucheritem a, yvoucher b ";
		strSql += " where a.voucherid = b.voucherid and a.businessitemid in (select a.refundmentitemid from YREFUNDMENTITEM a, yrefundment b ";
		strSql += " where a.refundmentid = b.refundmentid and a.isclear = '" + cleared.isCleared + "' and a.project_no = '" + projectNo + "' and b.businessstate="+ businState +  " and b.supplier='"+  supplierNo +"'  ";
		strSql += " and trim(a.project_no) is not null ";
		if (!StringUtils.isNullBlank(strContractList))
			strSql += " and a.contract_no not in (" + strContractList + ") ";
		strSql += " )";
		log.debug("得到根据供应商NO，businState,立项号， isclear=1,取得已清的退款行项目。(排除已清的合同号)信息:" + strSql);
		getJdbcTemplate().query(strSql, new RowCallbackHandler()
		{
			public void processRow(ResultSet rs) throws SQLException
			{
				ClearVoucherItemStruct clearVoucherItemStruct = new ClearVoucherItemStruct();

				clearVoucherItemStruct.setVoucherno(rs.getString("voucherno"));
				clearVoucherItemStruct.setRowno(rs.getString("rownumber"));
				clearVoucherItemStruct.setYear(rs.getString("fiyear"));
				clearVoucherItemStruct.setBwbje(rs.getBigDecimal("amount2"));

				clearVoucherItemStructList.add(clearVoucherItemStruct);
			}
		});

		return clearVoucherItemStructList;
		
	}
	/**
	 * 根据供应商NO，businState,公司代码 ，isclear=1,取得已清的退款行项目。(排除已清的合同号,立项号)
	 * @param customerNo
	 * @param businState
	 * @param projectList
	 * @param strContractList
	 * @return
	 */
	public List<ClearVoucherItemStruct> getRefundItemBySupplierNo(String supplierNo,String businState,String strProjectList,String strContractList,String bukrs){
		final List<ClearVoucherItemStruct> clearVoucherItemStructList = new ArrayList<ClearVoucherItemStruct>();

		String strSql = "select b.voucherno, a.rownumber, b.fiyear,a.amount2 from yvoucheritem a, yvoucher b ";
		strSql += " where a.voucherid = b.voucherid and b.companycode = '" +bukrs+ "' and a.businessitemid in (select a.refundmentitemid from YREFUNDMENTITEM a, yrefundment b ";
		strSql += " where a.refundmentid = b.refundmentid and a.isclear = '" + cleared.isCleared + "' and b.supplier = '" + supplierNo + "' and b.businessstate="+ businState +" ";
		if (!StringUtils.isNullBlank(strProjectList))
			strSql += " and a.project_no not in (" + strProjectList + ") ";
		if (!StringUtils.isNullBlank(strContractList))
			strSql += " and a.contract_no not in (" + strContractList + ") ";
		strSql += ")";
		log.debug("得到根据供应商NO，businState,公司代码 ，isclear=1,取得已清的退款行项目。(排除已清的合同号,立项号)信息:" + strSql);

		getJdbcTemplate().query(strSql, new RowCallbackHandler()
		{
			public void processRow(ResultSet rs) throws SQLException
			{
				ClearVoucherItemStruct clearVoucherItemStruct = new ClearVoucherItemStruct();

				clearVoucherItemStruct.setVoucherno(rs.getString("voucherno"));
				clearVoucherItemStruct.setRowno(rs.getString("rownumber"));
				clearVoucherItemStruct.setYear(rs.getString("fiyear"));
				clearVoucherItemStruct.setBwbje(rs.getBigDecimal("amount2"));

				clearVoucherItemStructList.add(clearVoucherItemStruct);
			}
		});

		return clearVoucherItemStructList;
	}
	/**
	 * 根据合同号取得退款金额合计
	 * 
	 * @param contractNo
	 * @return
	 */
	public BigDecimal getSumRefundAmount(String supplierNo,String businState,String contractNo)
	{
		
		StringBuffer sb = new StringBuffer();
		sb.append("select nvl(sum(a.refundmentamount),0) as AMOUNT from YREFUNDMENTITEM a ,yrefundment b where a.refundmentid = b.refundmentid ");
		sb.append("and b.supplier='"+  supplierNo +"' and b.businessstate="+ businState +" ");
		sb.append(" and a.ISCLEAR='" + cleared.isCleared + "' ");
		sb.append(" and a.contract_no='"+ contractNo +"' and trim(a.contract_no) is not null ");
		BigDecimal list = (BigDecimal) this.getJdbcTemplate().queryForObject(sb.toString(), BigDecimal.class);
		log.debug("根据合同号取得退款金额合计:" + sb.toString());

		if (null == list)
			return new BigDecimal(0);
		else
			return list;
	}
	/**
	 * 根据立项号取得退款金额合计
	 * 
	 * @param contractNo
	 * @return
	 */
	public BigDecimal getSumRefundAmountByProject(String supplierNo,String businState,String projectNo,String strContractList)
	{
		
		StringBuffer sb = new StringBuffer();
		sb.append("select nvl(sum(a.refundmentamount),0) as AMOUNT from YREFUNDMENTITEM a ,yrefundment b where a.refundmentid = b.refundmentid ");
		sb.append("and b.supplier='"+  supplierNo +"' and b.businessstate="+ businState +" ");
		sb.append(" and a.ISCLEAR='" + cleared.isCleared + "' ");
		sb.append(" and a.project_no='"+ projectNo +"' and trim(a.project_no) is not null ");
		if (!StringUtils.isNullBlank(strContractList))
			sb.append("and a.contract_no not in(" + strContractList + ") ");
		
		BigDecimal list = (BigDecimal) this.getJdbcTemplate().queryForObject(sb.toString(), BigDecimal.class);
		log.debug("根据立项号取得退款金额合计:" + sb.toString());

		if (null == list)
			return new BigDecimal(0);
		else
			return list;
	}
	/**
	 * 根据供应商取得退款金额合计
	 * 
	 * @param contractNo
	 * @return
	 */
	public BigDecimal getSumRefundAmount(String supplierNo,String businState,String strProjectList ,String strContractList,String deptId)
	{
		
		StringBuffer sb = new StringBuffer();
		sb.append("select nvl(sum(a.refundmentamount),0) as AMOUNT from YREFUNDMENTITEM a ,yrefundment b where a.refundmentid = b.refundmentid ");
		sb.append("and b.supplier='"+  supplierNo +"' and b.businessstate="+ businState +" ");
		sb.append(" and a.ISCLEAR='" + cleared.isCleared + "' ");
		if (!StringUtils.isNullBlank(strContractList))
			sb.append(" and a.contract_no not in(" + strContractList + ") ");
		if (!StringUtils.isNullBlank(strProjectList))
			sb.append(" and a.project_no not in(" + strProjectList + ") ");
		sb.append(" and b.dept_id in ('" + deptId + "') ");
		BigDecimal list = (BigDecimal) this.getJdbcTemplate().queryForObject(sb.toString(), BigDecimal.class);
		log.debug("根据供应商取得退款金额合计:" + sb.toString());

		if (null == list)
			return new BigDecimal(0);
		else
			return list;
	}
	/**
	 * 
	 * @param bukrs
	 * @param gsber
	 * @param lifnr
	 * @param waers
	 * @param saknr
	 * @param augdt_to
	 * @param augdt_from
	 * @return
	 */
	public List<VendorTitle> getVendorTitle(String bukrs,String gsber,String lifnr,String waers,String saknr,String augdt_to,String augdt_from){
		StringBuffer sql = new StringBuffer(" select * from yvendortitle a where 1=1 ");
		if (!StringUtils.isNullBlank(bukrs)) {
			sql.append("and a.bukrs='"+bukrs+"'");
		}
		if (!StringUtils.isNullBlank(gsber)) {
			sql.append("and a.gsber='"+gsber+"'");
		}
		if (!StringUtils.isNullBlank(lifnr)) {
			sql.append("and a.lifnr='"+lifnr+"'");
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
		List<VendorTitle> vendorTitles = new ArrayList<VendorTitle>();		
		List<Map> rowList = this.getJdbcTemplate().queryForList(sql.toString());
		for (Map map : rowList)
		{
			VendorTitle vendorTitle = new VendorTitle();
			ExBeanUtils.setBeanValueFromMap(vendorTitle, map);
			vendorTitles.add(vendorTitle);
		}
		return vendorTitles;
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
	public void updateVendorTitleIsClearedByBelnr(String belnr,String burks,String gjahr,String stblg,String stjah, String isCleared)
	{
		String sql = "update YVENDORTITLE set ISCLEARED ='" + isCleared + "' , stblg ='" + stblg + "' , stjah ='" + stjah + "'  where belnr ='" + belnr + "' and burks ='" +burks+ "' and gjahr ='" +gjahr+  "'";
		log.debug("更新未清供应商抬头数据上是否已结清标志:" + sql);
		this.getJdbcTemplate().execute(sql);
	}
	
	public String getContractNoByP(String ebeln){
		String sql="select cp.contract_no from t_contract_purchase_info cp where cp.sap_order_no='"+ebeln+"' and trim(cp.sap_order_no) is not null";
		List<Map> rowList = this.getJdbcTemplate().queryForList(sql);
		for (Map map : rowList)
		{
			return map.get("CONTRACT_NO").toString();
		}		
		return " ";
		
	}
}
