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
public class CustomerTitleJdbcDao extends BaseJdbcDao
{

	/**
	 * 根据客户编号、科目，取得客户下未清发票数据集合
	 * 
	 * @param custom
	 * @param shkzg
	 *            借方/贷方标识
	 */
	public List<CustomerTitle> getCustomerTitleListByCustom(String custom, String subject, String shkzg)
	{
		List<CustomerTitle> customerTitles = new ArrayList<CustomerTitle>();
		String sql = "select * from YCUSTOMERTITLE where KUNNR ='" + custom + "' and SAKNR='" + subject + "' and ISCLEARED='0'";
		if (!StringUtils.isNullBlank(shkzg))
		{
			sql = sql + " and SHKZ='" + shkzg + "'";
		}
		List<Map> rowList = this.getJdbcTemplate().queryForList(sql);
		log.debug("根据客户编号、科目，取得客户下未清发票数据集合:" + sql);
		for (Map map : rowList)
		{
			CustomerTitle customerTitle = new CustomerTitle();
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
	public List<CustomerTitle> getCustomerTitleListByCustom(String custom, String subject,String currencytext ,String companyno,String depid, String shkzg)
	{
		List<CustomerTitle> customerTitles = new ArrayList<CustomerTitle>();
		String sql = "select * from YCUSTOMERTITLE where KUNNR ='" + custom + "' and SAKNR='" + subject + "' and WAERS='" + currencytext + "' and BUKRS='" + companyno + "' and GSBER='" + depid + "' and ISCLEARED='0'  ";
//		String sql = "select * from YCUSTOMERTITLE where KUNNR ='" + custom + "' and SAKNR='" + subject + "' and WAERS='" + currencytext + "' and BUKRS='" + companyno +  "' and ISCLEARED='0'";
		if (!StringUtils.isNullBlank(shkzg))
		{
			sql = sql + " and SHKZ='" + shkzg + "'";
		}
		List<Map> rowList = this.getJdbcTemplate().queryForList(sql);
		log.debug("根据客户编号、科目，货币文本，公司代码，取得客户下未清发票数据集合:" + sql);
		for (Map map : rowList)
		{
			CustomerTitle customerTitle = new CustomerTitle();
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
	 * 根据合同或者立项，取得开票已经审批完金额、在途金额。
	 * 
	 * @param billno
	 * @param businessstate
	 * @return
	 */
	public BigDecimal getSumClearAmount(String deptid, String customer, String contract_no, String project_no, String businessstates)
	{

		StringBuffer sb = new StringBuffer();
		// 票清收款
		sb.append("select nvl(sum(amount),0) from (");
		sb.append("select nvl(sum(clearbillamount),0) as amount from YBILLCLEARITEM a ,YBILLCLEAR b ");
		sb.append(" where a.BILLCLEARID=b.BILLCLEARID and b.CLEARTYPE='1' and b.BUSINESSSTATE in(" + businessstates + ") ");

		if (!StringUtils.isNullBlank(deptid))
		{
			sb.append(" and b.deptid='" + deptid + "' ");
		}		
		
		if (!StringUtils.isNullBlank(customer))
		{
			sb.append(" and b.customer='" + customer + "' ");
		}

		if (!StringUtils.isNullBlank(contract_no))
		{
			sb.append(" and a.contract_no='" + contract_no + "' ");
		}

		if (!StringUtils.isNullBlank(project_no))
		{
			sb.append(" and a.project_no='" + project_no + "' ");
		}
		sb.append(" union ");
		// 客户单清
		sb.append("select nvl(sum(CLEARAMOUNT),0) as amount from YUNCLEARCUSTBILL a ,YCUSTOMSICLEAR b   ");
		sb.append(" where a.CUSTOMSCLEARID=b.CUSTOMSCLEARID and b.BUSINESSSTATE in(" + businessstates + ") ");

		if (!StringUtils.isNullBlank(deptid))
		{
			sb.append(" and b.deptid='" + deptid + "' ");
		}
		
		if (!StringUtils.isNullBlank(customer))
		{
			sb.append(" and b.custom='" + customer + "' ");
		}

		if (!StringUtils.isNullBlank(contract_no))
		{
			sb.append(" and a.contract_no='" + contract_no + "' ");
		}

		if (!StringUtils.isNullBlank(project_no))
		{
			sb.append(" and a.project_no='" + project_no + "' ");
		}

		sb.append(" union ");
		// 收款(清票)
//		sb.append(" select nvl(sum( a.CLEARAMOUNT),0) as amount from YCOLLECTCBILL a ,YCOLLECT b where a.collectid=b.collectid and b.collecttype in('0','1')");
		sb.append(" select nvl(sum( a.CLEARAMOUNT),0) as amount from YCOLLECTCBILL a ,YCOLLECT b where a.collectid=b.collectid ");
		sb.append(" and b.BUSINESSSTATE in(" + businessstates + ") ");

		if (!StringUtils.isNullBlank(deptid))
		{
			sb.append(" and b.dept_id='" + deptid + "' ");
		}
		
		if (!StringUtils.isNullBlank(customer))
		{
			sb.append(" and b.customer='" + customer + "' ");
		}

		if (!StringUtils.isNullBlank(contract_no))
		{
			sb.append(" and a.contract_no='" + contract_no + "' ");
		}

		if (!StringUtils.isNullBlank(project_no))
		{
			sb.append(" and a.project_no='" + project_no + "' ");
		}
		sb.append(" )");

		log.debug("根据合同或者立项，取得开票已经审批完金额、在途金额:" + sb.toString());
		BigDecimal list = (BigDecimal) this.getJdbcTemplate().queryForObject(sb.toString(), BigDecimal.class);

		if (null == list)
			return new BigDecimal(0);
		else
			return list;
	}

	/**
	 * 获取发票
	 * 
	 * @param invoice
	 * @return
	 */
	public CustomerTitle getByInvoice(String invoice)
	{
		String hql = "select * from YCUSTOMERTITLE where trim(invoice) = '" + invoice + "' and trim(invoice) is not null ";
		
		List<Map> rowList = this.getJdbcTemplate().queryForList(hql);
		log.debug("根据发票取得:" + hql);
		CustomerTitle customerTitle = new CustomerTitle();
		ExBeanUtils.setBeanValueFromMap(customerTitle, rowList.get(0));
		return customerTitle;
	}

	/**
	 * 获取发票
	 * 
	 * @param invoice
	 * @return
	 */
	public CustomerTitle getByVoucherNo(String companycode,String voucherno,String fiyear,String rownumber)
	{
		String hql = "select * from YCUSTOMERTITLE where bukrs = '" + companycode + "' and belnr = '" + voucherno + "' and gjahr = '" + fiyear + "' and buzei = '" + rownumber + "'";
		
		List<Map> rowList = this.getJdbcTemplate().queryForList(hql);
		
		log.debug("根据发票取得:" + hql);
		CustomerTitle customerTitle = new CustomerTitle();
		if (rowList.size() > 0){
			ExBeanUtils.setBeanValueFromMap(customerTitle, rowList.get(0));
		}
		return customerTitle;
	}
	
	/**
	 * 获取发票
	 * 
	 * @param invoice
	 * @return
	 */
	public List<CustomerTitle> getByContract(String contractnos)
	{
		List<CustomerTitle> customerTitleList = new ArrayList<CustomerTitle>();
		String hql = "select * from YCUSTOMERTITLE where IHREZ in (" + contractnos + ")";
		
		List<Map> rowList = this.getJdbcTemplate().queryForList(hql);
		log.debug("根据发票取得:" + hql);
		for (Map map : rowList){
			CustomerTitle customerTitle = new CustomerTitle();
			ExBeanUtils.setBeanValueFromMap(customerTitle, map);
			customerTitleList.add( customerTitle);
		}
		return customerTitleList;
	}
	
	/**
	 * 获取发票
	 * 
	 * @param invoice
	 * @return
	 */
	public List<CustomerTitle> getByProject(String projectnos)
	{
		List<CustomerTitle> customerTitleList = new ArrayList<CustomerTitle>();
		String hql = "select * from YCUSTOMERTITLE where BNAME in (" + projectnos + ")";
		
		List<Map> rowList = this.getJdbcTemplate().queryForList(hql);
		log.debug("根据发票取得:" + hql);
		for (Map map : rowList){
			CustomerTitle customerTitle = new CustomerTitle();
			ExBeanUtils.setBeanValueFromMap(customerTitle, map);
			customerTitleList.add( customerTitle);
		}
		return customerTitleList;
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
		String sql = "update YCUSTOMERTITLE set ISCLEARED ='" + isCleared + "' where CUSTOMERTITLEID ='" + customertitleid + "'";
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
		String sql = "update YCUSTOMERTITLE set ISCLEARED ='" + isCleared + "' where INVOICE ='" + invoice + "'";
		log.debug("更新未清客户抬头数据上是否已结清标志:" + sql);
		this.getJdbcTemplate().execute(sql);
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
		sb.append("select nvl(sum(DMBTR),0) as AMOUNT from YCUSTOMERTITLE a ");
		sb.append(" where a.ISCLEARED='" + cleared.isCleared + "' and a.IHREZ='" + contractNo + "' and a.STJAH='0000' and SHKZG='S' ");

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
		sb.append("select nvl(sum(DMBTR),0) as AMOUNT from YCUSTOMERTITLE a ");
		sb.append(" where a.ISCLEARED='" + cleared.isCleared + "' and a.BNAME='" + projectNo + "'  and a.STJAH='0000' and SHKZG='S' ");
		if (!StringUtils.isNullBlank(contractNos))
			sb.append("and a.IHREZ not in(" + contractNos + ") ");

		BigDecimal list = (BigDecimal) this.getJdbcTemplate().queryForObject(sb.toString(), BigDecimal.class);
		log.debug("根据立项号取得已清发票金额合计:" + sb.toString());

		if (null == list)
			return new BigDecimal(0);
		else
			return list;
	}

	/**
	 * 根据客户取得已清发票金额合计（排除立项、合同、公司）
	 * 
	 * @param customer
	 * @param contractNos
	 * @param projectNos
	 * @param bukrs
	 * @return
	 */
	public BigDecimal getSumAmount(String customer, String contractNos, String projectNos, String bukrs)
	{
		StringBuffer sb = new StringBuffer();
		sb.append("select nvl(sum(DMBTR),0) as AMOUNT from YCUSTOMERTITLE a ");
		sb.append(" where a.ISCLEARED='" + cleared.isCleared + "' and a.KUNNR='" + customer + "' and SHKZG='S' ");
		if (!StringUtils.isNullBlank(contractNos))
			sb.append(" and a.IHREZ not in(" + contractNos + ") ");
		if (!StringUtils.isNullBlank(projectNos))
			sb.append(" and a.BNAME not in(" + projectNos + ") ");
		sb.append(" and a.bukrs ='" + bukrs + "'");

		BigDecimal list = (BigDecimal) this.getJdbcTemplate().queryForObject(sb.toString(), BigDecimal.class);
		log.debug("根据客户取得已清发票金额合计:" + sb.toString());

		if (null == list)
			return new BigDecimal(0);
		else
			return list;
	}
	/**
	 * 根据客户取得外围已清金额合计（排除立项、合同、公司）
	 * 
	 * @param customer
	 * @param contractNos
	 * @param projectNos
	 * @param bukrs
	 * @return
	 */
	public BigDecimal getSumAmount2(String customer, String contractNos, String projectNos, String bukrs)
	{
		StringBuffer sb = new StringBuffer();
		sb.append("select nvl(sum(DMBTR),0) as AMOUNT from YCUSTOMERTITLE a ");
		sb.append(" where a.ISCLEARED='" + cleared.isCleared + "' and a.KUNNR='" + customer + "' and SHKZG='H' ");
		if (!StringUtils.isNullBlank(contractNos))
			sb.append(" and a.IHREZ not in(" + contractNos + ") ");
		if (!StringUtils.isNullBlank(projectNos))
			sb.append(" and a.BNAME not in(" + projectNos + ") ");
		sb.append(" and a.bukrs ='" + bukrs + "'");

		BigDecimal list = (BigDecimal) this.getJdbcTemplate().queryForObject(sb.toString(), BigDecimal.class);
		log.debug("根据客户取得已清发票金额合计:" + sb.toString());

		if (null == list)
			return new BigDecimal(0);
		else
			return list;
	}
	/**
	 * 
	 * @param customer
	 * @param contractNos
	 * @param projectNos
	 * @param bukrs 公司代码
	 * @param shkzg 借贷方
	 * @param waers 币别
	 * @param gsber 业务范围
	 * @param saknr 清账科目
	 * @return
	 */
	public BigDecimal getSumAmount2(String customer, String contractNos, String projectNos, String bukrs,String shkzg,String waers ,String gsber,String saknr)
	{
		StringBuffer sb = new StringBuffer();
		sb.append("select nvl(sum(DMBTR),0) as AMOUNT from YCUSTOMERTITLE a ");
		sb.append(" where a.ISCLEARED='" + cleared.isCleared + "' and a.KUNNR='" + customer + "' and SHKZG='"+shkzg+"'  AND a.saknr='"+saknr+"' AND a.gsber='"+gsber+"' AND a.waers='"+waers+"' ");
		if (!StringUtils.isNullBlank(contractNos))
			sb.append(" and a.IHREZ not in(" + contractNos + ") ");
		if (!StringUtils.isNullBlank(projectNos))
			sb.append(" and a.BNAME not in(" + projectNos + ") ");
		sb.append(" and a.bukrs ='" + bukrs + "'");

		BigDecimal list = (BigDecimal) this.getJdbcTemplate().queryForObject(sb.toString(), BigDecimal.class);
		log.debug("根据客户取得已清发票金额合计:" + sb.toString());

		if (null == list)
			return new BigDecimal(0);
		else
			return list;
	}
	
	/**
	 * 根据客户取得外围已清本位币金额合计（排除立项、合同、公司）
	 * 
	 * @param customer
	 * @param contractNos
	 * @param projectNos
	 * @param bukrs
	 * @return
	 */
	public BigDecimal getSumBwbje(String customer, String contractNos, String projectNos, String bukrs,String shkzg,String waers ,String gsber,String saknr)
	{
		StringBuffer sb = new StringBuffer();
		sb.append("select nvl(sum(bwbje),0) as AMOUNT from YCUSTOMERTITLE a ");
		sb.append(" where a.ISCLEARED='" + cleared.isCleared + "' and a.KUNNR='" + customer + "' and SHKZG='" +shkzg+"'  AND a.saknr='"+saknr+"' AND a.gsber='"+gsber+"' AND a.waers='"+waers+"' ");
		if (!StringUtils.isNullBlank(contractNos))
			sb.append(" and a.IHREZ not in(" + contractNos + ") ");
		if (!StringUtils.isNullBlank(projectNos))
			sb.append(" and a.BNAME not in(" + projectNos + ") ");
		sb.append(" and a.bukrs ='" + bukrs + "'");

		BigDecimal list = (BigDecimal) this.getJdbcTemplate().queryForObject(sb.toString(), BigDecimal.class);
		log.debug("根据客户取得已清发票金额合计:" + sb.toString());

		if (null == list)
			return new BigDecimal(0);
		else
			return list;
	}
	
	public BigDecimal getSumBillAmount(String contractNo, String projectNo, String customer, String iscleared)
	{
		StringBuffer sb = new StringBuffer();
		sb.append("select nvl(sum(DMBTR),0) as AMOUNT from YCUSTOMERTITLE a ");
		sb.append(" where a.ISCLEARED='" + iscleared + "'");
		if (!StringUtils.isNullBlank(contractNo))
			sb.append(" and a.IHREZ ='" + contractNo + "' ");
		if (!StringUtils.isNullBlank(projectNo))
			sb.append(" and a.BNAME ='" + projectNo + "' ");
		if (!StringUtils.isNullBlank(customer))
			sb.append(" and a.KUNNR ='" + customer + "' ");

		BigDecimal list = (BigDecimal) this.getJdbcTemplate().queryForObject(sb.toString(), BigDecimal.class);
		log.debug("根据客户取得已清发票金额合计:" + sb.toString());

		if (null == list)
			return new BigDecimal(0);
		else
			return list;
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
		String strSql = "select a.BELNR, a.GJAHR, a.BUZEI, a.BWBJE from YCUSTOMERTITLE a where trim(a.INVOICE) = '" + strBillNo + "' and trim(a.INVOICE) is not null  and a.STJAH='0000'  and a.SHKZG='S' ";
		log.debug("得到本次清帐票的信息:" + strSql);
		getJdbcTemplate().query(strSql, new RowCallbackHandler()
		{
			public void processRow(ResultSet rs) throws SQLException
			{
				clearVoucherItemStruct.setVoucherno(rs.getString("BELNR"));
				clearVoucherItemStruct.setRowno(rs.getString("BUZEI"));
				clearVoucherItemStruct.setYear(rs.getString("GJAHR"));
				clearVoucherItemStruct.setBwbje(rs.getBigDecimal("BWBJE"));
			}
		});

		return clearVoucherItemStruct;
	}

	/**
	 * 得到本次分配项清信息
	 * 
	 * @param collectId
	 * @param collectItemId
	 * @return
	 */
	public ClearVoucherItemStruct getCurrentCollectItemInfo(String collectId, String collectItemId)
	{
		final ClearVoucherItemStruct clearVoucherItemStruct = new ClearVoucherItemStruct();

		String strSql = "select b.VOUCHERNO,a.ROWNUMBER,b.FIYEAR,a.AMOUNT2 from YVOUCHERITEM a, YVOUCHER b ";
		strSql += " where a.VOUCHERID = b.VOUCHERID and a.BUSINESSITEMID = '" + collectItemId + "' ";
		strSql += " and b.BUSINESSID = '" + collectId + "'";
		log.debug("得到本次分配项清信息:" + strSql);
		getJdbcTemplate().query(strSql, new RowCallbackHandler()
		{
			public void processRow(ResultSet rs) throws SQLException
			{
				clearVoucherItemStruct.setVoucherno(rs.getString("VOUCHERNO"));
				clearVoucherItemStruct.setRowno(rs.getString("ROWNUMBER"));
				clearVoucherItemStruct.setYear(rs.getString("FIYEAR"));
				clearVoucherItemStruct.setBwbje(rs.getBigDecimal("AMOUNT2"));
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
	public List<ClearVoucherItemStruct> getContractBillInfo(String customer,String strContractNo)
	{
		final List<ClearVoucherItemStruct> clearVoucherItemStructList = new ArrayList<ClearVoucherItemStruct>();

		String strSql = "select a.BELNR, a.GJAHR, a.BUZEI,a.BWBJE from YCUSTOMERTITLE a ";
		strSql += " where a.IHREZ = '" + strContractNo + "' and a.kunnr='" + customer+"' and trim(a.IHREZ) is not null  and a.STJAH='0000'  and a.SHKZG='S' ";
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
	 * 得到合同款分配项清信息
	 * 
	 * @param strContractNo
	 * @return
	 */
	public List<ClearVoucherItemStruct> getContractCollectItemInfo(String customer,String strContractNo)
	{
		final List<ClearVoucherItemStruct> clearVoucherItemStructList = new ArrayList<ClearVoucherItemStruct>();

		String strSql = "select b.VOUCHERNO, a.ROWNUMBER, b.FIYEAR,a.AMOUNT2 from YVOUCHERITEM a, YVOUCHER b ";
		strSql += "where a.VOUCHERID = b.VOUCHERID  and trim(a.taxcode) is null  and a.subject='"+ customer+"' and a.BUSINESSITEMID in ";
		strSql += "(select a.COLLECTITEMID from YCOLLECTITEM a, YCOLLECT b where a.COLLECTID = b.COLLECTID ";
		strSql += "and a.ISCLEAR = '" + cleared.isCleared + "' and a.CONTRACT_NO = '" + strContractNo + "' and trim(a.CONTRACT_NO) is not null)   ";

		log.debug("得到合同款分配项清信息:" + strSql);
		getJdbcTemplate().query(strSql, new RowCallbackHandler()
		{
			public void processRow(ResultSet rs) throws SQLException
			{
				ClearVoucherItemStruct clearVoucherItemStruct = new ClearVoucherItemStruct();

				clearVoucherItemStruct.setVoucherno(rs.getString("VOUCHERNO"));
				clearVoucherItemStruct.setRowno(rs.getString("ROWNUMBER"));
				clearVoucherItemStruct.setYear(rs.getString("FIYEAR"));
				clearVoucherItemStruct.setBwbje(rs.getBigDecimal("AMOUNT2"));

				clearVoucherItemStructList.add(clearVoucherItemStruct);
			}
		});

		return clearVoucherItemStructList;
	}

	/**
	 * 得到合同分配项清信息
	 * 
	 * @param strContractNo
	 * @return
	 */
	public List<ClearVoucherItemStruct> getContractPayItemInfo(String strContractNo)
	{
		final List<ClearVoucherItemStruct> clearVoucherItemStructList = new ArrayList<ClearVoucherItemStruct>();

		String strSql = "select b.voucherno, a.rownumber, b.fiyear,a.amount2 " + "from yvoucheritem a, yvoucher b " + "where a.voucherid = b.voucherid " + "and a.BUSINESSITEMID in " + "(select a.collectitemid " + "from ycollectitem a, ycollect b " + "where a.collectid = b.collectid " + "and a.billisclear = '1' " + "and a.contract_no = '" + strContractNo + "' " + "and trim(a.contract_no) is not null)";
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
	public List<ClearVoucherItemStruct> getProjectBillInfo(String customer,String strProjecttNo, String strContractList)
	{
		final List<ClearVoucherItemStruct> clearVoucherItemStructList = new ArrayList<ClearVoucherItemStruct>();
		String strSql = "select a.BELNR, a.GJAHR, a.BUZEI,a.BWBJE from YCUSTOMERTITLE a where a.BNAME = '" + strProjecttNo + "' ";
		strSql += " and trim(a.BNAME) is not null and a.kunnr='" + customer + "' and a.ISCLEARED = '" + cleared.isCleared + "'  and a.STJAH='0000'  and a.SHKZG='S' ";
		if (!StringUtils.isNullBlank(strContractList))
			strSql += " and a.IHREZ not in (" + strContractList + ")";
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
	 * @param strcollectId
	 * @param strcollectItemId
	 * @return
	 */
	public List<ClearVoucherItemStruct> getProjectPayItemInfo(String strProjectNo, String strContractList)
	{
		final List<ClearVoucherItemStruct> clearVoucherItemStructList = new ArrayList<ClearVoucherItemStruct>();

		String strSql = "select b.voucherno, a.rownumber, b.fiyear,a.amount2 from yvoucheritem a, yvoucher b ";
		strSql += " where a.voucherid = b.voucherid and a.businessitemid in (select a.collectitemid from ycollectitem a, ycollect b ";
		strSql += " where a.collectid = b.collectid and a.billisclear = '" + cleared.isCleared + "' and a.project_no = '" + strProjectNo + "'";
		strSql += " and trim(a.project_no) is not null ";
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
	 * 得到客户分配项清信息
	 * 
	 * @param strSupplier
	 * @param strContractList
	 * @param strProjectList
	 * @param bukrs
	 * @return
	 */
	public List<ClearVoucherItemStruct> getCustomerPayItemInfo(String strCustomer, String strContractList, String strProjectList, String bukrs)
	{
		final List<ClearVoucherItemStruct> clearVoucherItemStructList = new ArrayList<ClearVoucherItemStruct>();

		String strSql = "select b.voucherno, a.rownumber, b.fiyear,a.amount2 from yvoucheritem a, yvoucher b ";
		strSql += " where a.voucherid = b.voucherid and b.companycode = '" +bukrs+ "' and a.businessitemid in (select a.collectitemid from ycollectitem a, ycollect b ";
		strSql += " where a.collectid = b.collectid and a.isclear = '" + cleared.isCleared + "' and b.customer = '" + strCustomer + "' ";
		if (!StringUtils.isNullBlank(strProjectList))
			strSql += " and a.project_no not in (" + strProjectList + ") ";
		if (!StringUtils.isNullBlank(strContractList))
			strSql += " and a.contract_no not in (" + strContractList + ") ";
		strSql += ")";
		log.debug("得到客户分配项清信息:" + strSql);

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
	 * 得到立项分配项清信息
	 * 
	 * @param strProjectNo
	 * @param strContractList
	 * @return
	 */
	public List<ClearVoucherItemStruct> getProjectCollectItemInfo(String customer,String strProjectNo, String strContractList)
	{
		final List<ClearVoucherItemStruct> clearVoucherItemStructList = new ArrayList<ClearVoucherItemStruct>();
		String strSql = "select b.VOUCHERNO, a.ROWNUMBER, b.FIYEAR,a.AMOUNT2 from YVOUCHERITEM a, YVOUCHER b ";
		strSql += " where a.VOUCHERID = b.VOUCHERID  and trim(a.taxcode) is null  and a.subject='" + customer +"' and a.BUSINESSITEMID in (select a.COLLECTITEMID from YCOLLECTITEM a, YCOLLECT b ";
		strSql += " where a.COLLECTID = b.COLLECTID and a.ISCLEAR = '" + cleared.isCleared + "' and a.PROJECT_NO = '" + strProjectNo + "'";
		strSql += " and trim(a.PROJECT_NO) is not null ";
		if (!StringUtils.isNullBlank(strContractList))
			strSql += " and a.CONTRACT_NO not in (" + strContractList + ") ";
		strSql += " )";
		log.debug("得到立项分配项清信息:" + strSql);
		getJdbcTemplate().query(strSql, new RowCallbackHandler()
		{
			public void processRow(ResultSet rs) throws SQLException
			{
				ClearVoucherItemStruct clearVoucherItemStruct = new ClearVoucherItemStruct();

				clearVoucherItemStruct.setVoucherno(rs.getString("VOUCHERNO"));
				clearVoucherItemStruct.setRowno(rs.getString("ROWNUMBER"));
				clearVoucherItemStruct.setYear(rs.getString("FIYEAR"));
				clearVoucherItemStruct.setBwbje(rs.getBigDecimal("AMOUNT2"));

				clearVoucherItemStructList.add(clearVoucherItemStruct);
			}
		});

		return clearVoucherItemStructList;
	}

	/**
	 * 得到客户清帐票的信息
	 * 
	 * @param strSupplier
	 * @param strContractList
	 * @param strProjectList
	 * @param bukrs
	 * @return
	 */
	public List<ClearVoucherItemStruct> getCustomerBillInfo(String customer, String strContractList, String strProjectList, String bukrs)
	{
		final List<ClearVoucherItemStruct> clearVoucherItemStructList = new ArrayList<ClearVoucherItemStruct>();
		StringBuffer sb = new StringBuffer();
		sb.append("select a.BELNR, a.GJAHR, a.BUZEI,a.BWBJE from YCUSTOMERTITLE a where a.KUNNR = '" + customer + "' and a.ISCLEARED = '" + cleared.isCleared + "'  and a.STJAH='0000'  and a.SHKZG='S' and a.DMBTR !=0 ");
		if (!StringUtils.isNullBlank(strContractList))
			sb.append(" and a.IHREZ not in (" + strContractList + ")");
		if (!StringUtils.isNullBlank(strProjectList))
			sb.append(" and a.BNAME not in (" + strProjectList + ")");
		sb.append(" and a.BUKRS ='" + bukrs + "'");
		log.debug("得到客户清帐票的信息:" + sb.toString());
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
	public List<ClearVoucherItemStruct> getCustomerVoucherInfo(String customer, String strContractList, String strProjectList, String bukrs)
	{
		final List<ClearVoucherItemStruct> clearVoucherItemStructList = new ArrayList<ClearVoucherItemStruct>();
		StringBuffer sb = new StringBuffer();
		sb.append("select a.BELNR, a.GJAHR, a.BUZEI,a.BWBJE from YCUSTOMERTITLE a where a.KUNNR = '" + customer + "' and a.ISCLEARED = '" + cleared.isCleared + "'  and a.STJAH='0000'  and a.SHKZG='H' and a.DMBTR =0");
		if (!StringUtils.isNullBlank(strContractList))
			sb.append(" and a.IHREZ not in (" + strContractList + ")");
		if (!StringUtils.isNullBlank(strProjectList))
			sb.append(" and a.BNAME not in (" + strProjectList + ")");
		sb.append(" and a.BUKRS ='" + bukrs + "'");
		log.debug("得到客户清帐票的信息:" + sb.toString());
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
	 * 取得客户下所有的外围已清的凭证信息（借贷方）
	 * @param customer
	 * @param strContractList
	 * @param strProjectList
	 * @param bukrs
	 * @return
	 */
	public List<ClearVoucherItemStruct> getCustomerVoucherInfo2(String customer, String strContractList, String strProjectList, String bukrs,String gsber ,String saknr ,String waers)
	{
		final List<ClearVoucherItemStruct> clearVoucherItemStructList = new ArrayList<ClearVoucherItemStruct>();
		StringBuffer sb = new StringBuffer();
		sb.append("select a.BELNR, a.GJAHR, a.BUZEI,a.BWBJE from YCUSTOMERTITLE a where a.KUNNR = '" + customer + "' and a.ISCLEARED = '" + cleared.isCleared + "' and a.gsber='" +gsber+  "' and a.saknr='" +saknr+  "' and a.waers='" +waers+ "'  and a.STJAH='0000' ");
		if (!StringUtils.isNullBlank(strContractList))
			sb.append(" and a.IHREZ not in (" + strContractList + ")");
		if (!StringUtils.isNullBlank(strProjectList))
			sb.append(" and a.BNAME not in (" + strProjectList + ")");
		sb.append(" and a.BUKRS ='" + bukrs + "'");
		log.debug("得到客户清帐所有的信息:" + sb.toString());
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
	 * 得到客户分配项清信息
	 * 
	 * @param customer
	 * @param strContractList
	 * @param strProjectList
	 * @param bukrs
	 * @return
	 */
	public List<ClearVoucherItemStruct> getCustomerCollectItemInfo(String customer, String strContractList, String strProjectList, String bukrs)
	{
		final List<ClearVoucherItemStruct> clearVoucherItemStructList = new ArrayList<ClearVoucherItemStruct>();

		String strSql = "select b.VOUCHERNO, a.ROWNUMBER, b.FIYEAR,a.AMOUNT2 from YVOUCHERITEM a, YVOUCHER b ";
		strSql += " where a.VOUCHERID = b.VOUCHERID  and trim(a.taxcode) is null and b.COMPANYCODE = '" + bukrs + "' and a.BUSINESSITEMID in (select a.COLLECTITEMID from YCOLLECTITEM a, YCOLLECT b ";
		strSql += " where a.COLLECTID = b.COLLECTID and a.ISCLEAR = '" + cleared.isCleared + "' and b.CUSTOMER = '" + customer + "' ";
		if (!StringUtils.isNullBlank(strProjectList))
			strSql += " and a.PROJECT_NO not in (" + strProjectList + ") ";
		if (!StringUtils.isNullBlank(strContractList))
			strSql += " and a.CONTRACT_NO not in (" + strContractList + ") ";
		strSql += ")";
		log.debug("得到客户分配项清信息:" + strSql);

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
	 * 
	 * @param customertitleid
	 * @return
	 */
	public CustomerTitle getByCustomertitleid(String customertitleid)
	{
		String hql = "select * from YCUSTOMERTITLE where customertitleid = '" + customertitleid + "'";
		
		List<Map> rowList = this.getJdbcTemplate().queryForList(hql);
		log.debug("根据customertitleid取得:" + hql);
		CustomerTitle customerTitle = new CustomerTitle();
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
	public CustomerTitle getCustomerTitle(String bukrs,String belnr,String gjahr,String buzei){
		String hql = "select * from YCUSTOMERTITLE where bukrs = '" + bukrs + "' and belnr= '" + belnr +"' and gjahr='" + gjahr + "' and buzei='" + buzei+"'";
		
		List<Map> rowList = this.getJdbcTemplate().queryForList(hql);
		log.debug("customertitle取得:" + hql);
		CustomerTitle customerTitle = new CustomerTitle();
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
	public boolean isCustomerTitleClear(CustomerTitle customerTitle){
		String sql1 = "SELECT COUNT(*) FROM YREFUNDMENT a WHERE a.REFUNDMENTID IN (SELECT b.BUSINESSID FROM YVOUCHER b WHERE b.VOUCHERNO='" +
					 customerTitle.getBelnr() + 
					 "')";
		String sql2 = "SELECT BUSINESSID FROM YVOUCHER WHERE processstate <> '-1' and VOUCHERNO='" + customerTitle.getBelnr() + 
					  "' AND COMPANYCODE='" + customerTitle.getBukrs() + 
					  "' AND FIYEAR='" + customerTitle.getGjahr() + "'";
		if(this.getJdbcTemplate().queryForInt(sql1) > 0){	// 若客户退款凭证已生成
			return true;
		}else{												// 若在单清与票清楚已经生成凭证
			String sql3 = "SELECT COUNT(*) FROM YCUSTOMSICLEAR WHERE CUSTOMSCLEARID IN (" + sql2 + ")";
			String sql4 = "SELECT COUNT(*) FROM YBILLCLEAR WHERE BILLCLEARID IN (" + sql2 + ")";
			if(this.getJdbcTemplate().queryForInt(sql3)>0 || this.getJdbcTemplate().queryForInt(sql4)>0){
				return true;
			}
		}
		return false;
	}
	/**
	 * 根据客户NO，businState,合同号， isclear=1,取得已清的退款凭证行项目。
	 * @param customerNo
	 * @param businState
	 * @param contractNo
	 * @return
	 */
	public List<ClearVoucherItemStruct> getRefundItemByCustomerNoAndContractNo(String customerNo,String businState,String contractNo){
		
		final List<ClearVoucherItemStruct> clearVoucherItemStructList = new ArrayList<ClearVoucherItemStruct>();
		String strSql = "select b.voucherno, a.rownumber, b.fiyear,a.amount2 " + "from yvoucheritem a, yvoucher b " + "where a.voucherid = b.voucherid " + "and a.businessitemid in " + "(select a.refundmentitemid " + "from YREFUNDMENTITEM a, yrefundment b " + "where a.refundmentid = b.refundmentid and b.customer='"+  customerNo +"' and b.businessstate="+ businState +" and a.contract_no='"+ contractNo +"' and isclear='1' " +  " and trim(a.contract_no) is not null) ";
		log.debug("得到根据客户NO，businState,合同号， isclear=1,取得已清的退款凭证行项目信息:" + strSql);
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
	 * 根据客户NO，businState,立项号， isclear=1,取得已清的退款行项目。(排除已清的合同号)
	 * @param customerNo
	 * @param businState
	 * @param projectNo
	 * @return
	 */
	public List<ClearVoucherItemStruct> getRefundItemByCustomerNoAndProjectNo(String customerNo,String businState,String projectNo,String strContractList){
		
		final List<ClearVoucherItemStruct> clearVoucherItemStructList = new ArrayList<ClearVoucherItemStruct>();

		
		String strSql = "select b.voucherno, a.rownumber, b.fiyear,a.amount2 from yvoucheritem a, yvoucher b ";
		strSql += " where a.voucherid = b.voucherid and a.businessitemid in (select a.refundmentitemid from YREFUNDMENTITEM a, yrefundment b ";
		strSql += " where a.refundmentid = b.refundmentid and a.isclear = '" + cleared.isCleared + "' and a.project_no = '" + projectNo + "' and b.businessstate="+ businState +  " and b.customer='"+  customerNo +"'  ";
		strSql += " and trim(a.project_no) is not null ";
		if (!StringUtils.isNullBlank(strContractList))
			strSql += " and a.contract_no not in (" + strContractList + ") ";
		strSql += " )";
		log.debug("得到根据客户NO，businState,立项号， isclear=1,取得已清的退款行项目。(排除已清的合同号)信息:" + strSql);
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
	 * 根据客户NO，businState,公司代码 ，isclear=1,取得已清的退款行项目。(排除已清的合同号,立项号)
	 * @param customerNo
	 * @param businState
	 * @param projectList
	 * @param strContractList
	 * @return
	 */
	public List<ClearVoucherItemStruct> getRefundItemByCustomerNo(String customerNo,String businState,String strProjectList,String strContractList,String bukrs){
		final List<ClearVoucherItemStruct> clearVoucherItemStructList = new ArrayList<ClearVoucherItemStruct>();

		String strSql = "select b.voucherno, a.rownumber, b.fiyear,a.amount2 from yvoucheritem a, yvoucher b ";
		strSql += " where a.voucherid = b.voucherid and b.companycode = '" +bukrs+ "' and a.businessitemid in (select a.refundmentitemid from YREFUNDMENTITEM a, yrefundment b ";
		strSql += " where a.refundmentid = b.refundmentid and a.isclear = '" + cleared.isCleared + "' and b.customer = '" + customerNo + "' and b.businessstate="+ businState +" ";
		if (!StringUtils.isNullBlank(strProjectList))
			strSql += " and a.project_no not in (" + strProjectList + ") ";
		if (!StringUtils.isNullBlank(strContractList))
			strSql += " and a.contract_no not in (" + strContractList + ") ";
		strSql += ")";
		log.debug("得到根据客户NO，businState,公司代码 ，isclear=1,取得已清的退款行项目。(排除已清的合同号,立项号)信息:" + strSql);

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
	public BigDecimal getSumRefundAmount(String customerNo,String businState,String contractNo)
	{
		
		StringBuffer sb = new StringBuffer();
		sb.append("select nvl(sum(a.refundmentamount),0) as AMOUNT from YREFUNDMENTITEM a ,yrefundment b where a.refundmentid = b.refundmentid ");
		sb.append("and b.customer='"+  customerNo +"' and b.businessstate="+ businState +" ");
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
	public BigDecimal getSumRefundAmountByProject(String customerNo,String businState,String projectNo,String strContractList)
	{
		
		StringBuffer sb = new StringBuffer();
		sb.append("select nvl(sum(a.refundmentamount),0) as AMOUNT from YREFUNDMENTITEM a ,yrefundment b where a.refundmentid = b.refundmentid ");
		sb.append("and b.customer='"+  customerNo +"' and b.businessstate="+ businState +" ");
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
	 * 根据客户取得退款金额合计
	 * 
	 * @param contractNo
	 * @return
	 */
	public BigDecimal getSumRefundAmount(String customerNo,String businState,String strProjectList ,String strContractList,String deptId)
	{
		
		StringBuffer sb = new StringBuffer();
		sb.append("select nvl(sum(a.refundmentamount),0) as AMOUNT from YREFUNDMENTITEM a ,yrefundment b where a.refundmentid = b.refundmentid ");
		sb.append("and b.customer='"+  customerNo +"' and b.businessstate="+ businState +" ");
		sb.append(" and a.ISCLEAR='" + cleared.isCleared + "' ");
		if (!StringUtils.isNullBlank(strContractList))
			sb.append(" and a.contract_no not in(" + strContractList + ") ");
		if (!StringUtils.isNullBlank(strProjectList))
			sb.append(" and a.project_no not in(" + strProjectList + ") ");
		sb.append(" and b.dept_id in ('" + deptId + "') ");
		BigDecimal list = (BigDecimal) this.getJdbcTemplate().queryForObject(sb.toString(), BigDecimal.class);
		log.debug("根据客户取得退款金额合计:" + sb.toString());

		if (null == list)
			return new BigDecimal(0);
		else
			return list;
	}
	
	/**
	 * @创建作者：邱杰烜
	 * @创建时间：2010-11-16
	 * 判断YCUSTOMERTITLE表中所有数据的VGBEL字段是否都为空，若是则同步时需要全量同步
	 */
	public boolean isNeedFullSync(){
		String sql = "SELECT COUNT(*) FROM YCUSTOMERTITLE A WHERE TRIM(A.VGBEL) IS NOT NULL";
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
		String sql = "select CUSTOMERTITLEID from YCUSTOMERTITLE where BUKRS = '" + bukrs 
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
	public List<CustomerTitle> getCustomerTitle(String bukrs,String gsber,String kunnr,String waers,String saknr,String augdt_to,String augdt_from){
		StringBuffer sql = new StringBuffer(" select * from YCUSTOMERTITLE a where 1=1 ");
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
		List<CustomerTitle> customerTitles =new ArrayList<CustomerTitle>();
		List<Map> rowList = this.getJdbcTemplate().queryForList(sql.toString());
		log.debug("根据客户编号、科目，取得客户下未清发票数据集合:" + sql);
		for (Map map : rowList)
		{
			CustomerTitle customerTitle = new CustomerTitle();
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
		String sql = "update ycustomertitle set ISCLEARED ='" + isCleared + "' , stblg ='" + stblg + "' , stjah ='" + stjah + "'  where belnr ='" + belnr + "' and burks ='" +burks+ "' and gjahr ='" +gjahr+  "'";
		log.debug("更新未清客户抬头数据上是否已结清标志:" + sql);
		this.getJdbcTemplate().execute(sql);
	}
	public String getContractNoByS(String vbeln){
		String sql="select cs.contract_no from t_contract_sales_info cs where cs.sap_order_no ='"+vbeln+"' and trim(cs.sap_order_no) is not null";
		String list = (String) this.getJdbcTemplate().queryForObject(sql, String.class);	

		if (null == list)
			return " ";
		else
			return list;
	}
}
