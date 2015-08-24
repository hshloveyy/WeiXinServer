/*
 * @(#)PaymentItemJdbcDao.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：林进旭
 *  时　间：2010-6-24
 *  描　述：创建
 */

/**
 * 
 */
package com.infolion.xdss3.payment.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.infolion.platform.dpframework.core.dao.BaseJdbcDao;
import com.infolion.platform.dpframework.util.ExBeanUtils;
import com.infolion.platform.util.StringUtils;
import com.infolion.xdss3.BusinessState;
import com.infolion.xdss3.Constants.cleared;
import com.infolion.xdss3.payment.domain.ImportPayment;
import com.infolion.xdss3.payment.domain.ImportPaymentItem;

/**
 * <pre>
 * 付款金额分配表(PaymentItem)JDBC操作类
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
public class PaymentItemJdbcDao extends BaseJdbcDao
{

	/**
	 * 根据合同号取得原合同号(纸质合同号)
	 * 
	 * @param Contract_No
	 * @return
	 */
	public String getOldContractNo(String Contract_No)
	{
		String strSql = "select Old_Contract_No from T_CONTRACT_PURCHASE_INFO where T_CONTRACT_PURCHASE_INFO.Contract_No='" + Contract_No + "'";
		log.debug("" + strSql);

		List<Map> rowList = this.getJdbcTemplate().queryForList(strSql);
		if (null != rowList && rowList.size() > 0)
		{
			return (String) ((Map) rowList.get(0)).get("Old_Contract_No");
		}
		else
			return "";
	}

	/**
	 * 取得到单下信用证号
	 * 
	 * @param pick_list_no
	 * @return
	 */
	public String queryLcNoByPickListNo(String pick_list_no)
	{
		StringBuffer sb = new StringBuffer();
		String strSql = "select LC_NO from T_PICK_LIST_INFO where T_PICK_LIST_INFO.PICK_LIST_NO='" + pick_list_no + "'";
		List<Map> list = this.getJdbcTemplate().queryForList(strSql);
		if (null != list && list.size() > 0)
		{
			String lc_no = (String) list.get(0).get("LC_NO");
			if (!StringUtils.isNullBlank(lc_no))
			{
				sb.append(lc_no + "<br>");
			}
		}
		else
		{
			sb.append("");
		}

		return sb.toString();
	}
	
	
	public String querySupplier(String supplierNo){
		List<Map> list = this.getJdbcTemplate().queryForList("select max(name1) name1 from ygetlifnr where lifnr='"+supplierNo+"'");
		if (null != list && list.size() > 0)
		{
			return (String) list.get(0).get("name1");
		}
		else
		{
			return "";
		}
	}

	/**
	 * 取得付款下到单
	 * 
	 * @param paymentId
	 * @return
	 */
	public String queryPickListNoByPayMentId(String paymentId)
	{
		StringBuffer sb = new StringBuffer();
		//start yanghancai 2010-09-26 
		String strSql = "select distinct PICK_LIST_NO from T_PICK_LIST_INFO WHERE PICK_LIST_ID IN (SELECT DISTINCT PICK_LIST_NO FROM YPAYMENTITEM where PAYMENTID='" + paymentId + "')";
		//end yanghancai 2010-09-26 
		List<Map> list = this.getJdbcTemplate().queryForList(strSql);
		
		if (null != list && list.size() > 0)
		{
			String pick_list_no = (String) list.get(0).get("PICK_LIST_NO");
			if (!StringUtils.isNullBlank(pick_list_no))
			{
				sb.append(pick_list_no);    //yanghancai 2010-09-25  取信用证号 ，去掉换行符
			}
		}
		else
		{
			sb.append("");
		}

		return sb.toString();
	}

	/**
	 * 取得付款下合同
	 * 
	 * @param paymentId
	 * @return
	 */
	public String queryContractNoByPayMentId(String paymentId)
	{
		StringBuffer sb = new StringBuffer();
		String strSql = "select distinct PROJECT_NO,CONTRACT_NO from YPAYMENTITEM where PAYMENTID='" + paymentId + "'";
		List<Map> list = this.getJdbcTemplate().queryForList(strSql);

		if (null != list && list.size() > 0)
		{
			for (Map map : list)
			{
				String contract_no = (String) map.get("CONTRACT_NO");
				String project_no = (String) map.get("PROJECT_NO");
				if (StringUtils.isNullBlank(contract_no))
				{
					if (!StringUtils.isNullBlank(project_no))
					{
						sb.append(project_no + "<br>");
					}
				}
				else
				{
					sb.append(contract_no + "<br>");
				}
			}
		}
		else
		{
			sb.append("");
		}

		return sb.toString();
	}

	/**
	 * 根据供应商，取得未清付款行项信息(付款已经审批通过)。
	 * 
	 * @param project_no
	 * @param contract_no
	 * @return
	 */
	public List<ImportPaymentItem> getUnclearPaymentItemList(String supplier, String businessstates)
	{
		List<ImportPaymentItem> paymentItemList = new ArrayList<ImportPaymentItem>();
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT b.*,a.* FROM YPAYMENT a ,YPAYMENTITEM b WHERE a.SUPPLIER='" + supplier + "'");
		//sb.append(" and  a.businessstate in(" + businessstates + ") and a.PAYMENTID=b.PAYMENTID and b.BILLISCLEAR='0' and a.PAYMENTTYPE in('0','1') ");
		sb.append(" and  a.businessstate in(" + businessstates + ") and a.PAYMENTID=b.PAYMENTID and ( b.BILLISCLEAR='0' or trim(b.BILLISCLEAR) is null ) order by b.contract_no desc  ,a.createtime asc ");
		log.debug("根据供应商，取得未清付款行项信息:" + sb.toString());
		List<Map> rowList = this.getJdbcTemplate().queryForList(sb.toString());
		for (Map map : rowList)
		{
			ImportPayment importPayment = new ImportPayment();
			ExBeanUtils.setBeanValueFromMap(importPayment, map);
			ImportPaymentItem paymentItem = new ImportPaymentItem();
			ExBeanUtils.setBeanValueFromMap(paymentItem, map);
			paymentItem.setImportPayment(importPayment);
			paymentItem.setPaymentno(importPayment.getPaymentno());
			paymentItemList.add(paymentItem);
		}

		return paymentItemList;
	}

	/**
	 * 根据paymentItemIds，取得未清付款行项信息。
	 * 
	 * @param paymentItemIds
	 * @return
	 */
	public List<ImportPaymentItem> getUnclearPaymentItemList(String paymentItemIds)
	{
		List<ImportPaymentItem> paymentItemList = new ArrayList<ImportPaymentItem>();
		StringBuffer sb = new StringBuffer();
		//sb.append("SELECT * FROM YPAYMENT a,YPAYMENTITEM b WHERE a.PAYMENTID=b.PAYMENTID and  a.PAYMENTTYPE in('0','1') and b.PAYMENTITEMID in(" + paymentItemIds + ") and b.BILLISCLEAR='0'");
		sb.append("SELECT b.*,a.* FROM YPAYMENT a,YPAYMENTITEM b WHERE a.PAYMENTID=b.PAYMENTID  and b.PAYMENTITEMID in(" + paymentItemIds + ") and ( b.BILLISCLEAR='0'  or trim(b.BILLISCLEAR) is null ) order by a.createtime asc ");
		log.debug("根据paymentItemIds，取得未清付款行项信息:" + sb.toString());
		List<Map> rowList = this.getJdbcTemplate().queryForList(sb.toString());
		for (Map map : rowList)
		{
			ImportPaymentItem paymentItem = new ImportPaymentItem();
			ExBeanUtils.setBeanValueFromMap(paymentItem, map);
			ImportPayment importPayment = new ImportPayment();
			ExBeanUtils.setBeanValueFromMap(importPayment, map);
			paymentItem.setImportPayment(importPayment);
			paymentItem.setPaymentno(importPayment.getPaymentno());
			paymentItemList.add(paymentItem);
		}

		return paymentItemList;
	}

	/**
	 * 根据paymentId，取得未清付款行项信息。
	 * 
	 * @param paymentId
	 * @return
	 */
	public List<ImportPaymentItem> getUnclearPaymentItemListByPaymentId(String paymentId)
	{
		List<ImportPaymentItem> paymentItemList = new ArrayList<ImportPaymentItem>();
		StringBuffer sb = new StringBuffer();
		//sb.append("SELECT * FROM YPAYMENT a,YPAYMENTITEM b WHERE a.PAYMENTID=b.PAYMENTID and  a.PAYMENTTYPE in('0','1')  and b.PAYMENTID in(" + paymentId + ") and b.BILLISCLEAR='0'");
		sb.append("SELECT b.*,a.* FROM YPAYMENT a,YPAYMENTITEM b WHERE a.PAYMENTID=b.PAYMENTID   and b.PAYMENTID in(" + paymentId + ") and ( b.BILLISCLEAR='0'  or trim(b.BILLISCLEAR) is null )");
		log.debug("paymentId，取得未清付款行项信息:" + sb.toString());
		List<Map> rowList = this.getJdbcTemplate().queryForList(sb.toString());
		for (Map map : rowList)
		{
			ImportPaymentItem paymentItem = new ImportPaymentItem();
			ExBeanUtils.setBeanValueFromMap(paymentItem, map);
			ImportPayment importPayment = new ImportPayment();
			ExBeanUtils.setBeanValueFromMap(importPayment, map);
			paymentItem.setImportPayment(importPayment);
			paymentItem.setPaymentno(importPayment.getPaymentno());
			paymentItemList.add(paymentItem);
		}

		return paymentItemList;
	}

	public ImportPaymentItem getPaymentItem(String paymentitemid)
	{
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT b.*,a.* FROM YPAYMENT a,YPAYMENTITEM b WHERE a.PAYMENTID=b.PAYMENTID   and b.PAYMENTITEMID ='" + paymentitemid+"'");
		log.debug("paymentitemid，付款行项信息:" + sb.toString());
		List<Map> rowList = this.getJdbcTemplate().queryForList(sb.toString());
		if(rowList.size()==0)return null;
		ImportPaymentItem paymentItem = new ImportPaymentItem();
		ExBeanUtils.setBeanValueFromMap(paymentItem, rowList.get(0));
		ImportPayment importPayment = new ImportPayment();
		ExBeanUtils.setBeanValueFromMap(importPayment, rowList.get(0));
		paymentItem.setImportPayment(importPayment);
		paymentItem.setPaymentno(importPayment.getPaymentno());
		
		return paymentItem;
	}

	/**
	 * 根据退款表行项ID反查付款表行项信息
	 * @param refundmentitemid
	 * @return
	 */
	public ImportPaymentItem getPaymentItemByRefundment(String refundmentitemid)
	{
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT b.*,a.* FROM YPAYMENT a,YPAYMENTITEM b, yrefundmentitem R WHERE " +
				" 	  a.PAYMENTID=b.PAYMENTID   " +
				" and b.PAYMENTITEMID = R.paymentitemid" +
				" and r.refundmentitemid ='" + refundmentitemid+"'" +
						"");
		log.debug("paymentitemid，付款行项信息:" + sb.toString());
		List<Map> rowList = this.getJdbcTemplate().queryForList(sb.toString());
		
		// 修改对于供应商退款，外加合同、立项退款，不进行处理 20110301
		if (rowList.size() > 0) {
			ImportPaymentItem paymentItem = new ImportPaymentItem();
			ExBeanUtils.setBeanValueFromMap(paymentItem, rowList.get(0));
			ImportPayment importPayment = new ImportPayment();
			ExBeanUtils.setBeanValueFromMap(importPayment, rowList.get(0));
			paymentItem.setImportPayment(importPayment);
			paymentItem.setPaymentno(importPayment.getPaymentno());

			return paymentItem;
		} else {
			return null;
		}
	}
	
	/**
	 * 更新付款分配表行项目是否已结清标志。
	 * 
	 * @param vendortitleid
	 * @param isCleared
	 */
	public void updatePaymentItemIsCleared(String paymentItemId, String isCleared)
	{
		String sql = "update YPAYMENTITEM set BILLISCLEAR ='" + isCleared + "' where PAYMENTITEMID ='" + paymentItemId + "'";
		log.debug("更新付款分配表行项目是否已结清标志:" + sql);
		this.getJdbcTemplate().execute(sql);
	}

	/**
	 * 更新付款分配表行项目是否已结清标志。
	 * 
	 * @param paymentItemIdsSqlIn
	 * @param isCleared
	 */
	public void updateIsCleared(String paymentItemIdsSqlIn, String isCleared)
	{
		String sql = "update YPAYMENTITEM set BILLISCLEAR ='" + isCleared + "' where PAYMENTITEMID in(" + paymentItemIdsSqlIn + ")";
		log.debug("更新付款分配表行项目是否已结清标志:" + sql);
		this.getJdbcTemplate().execute(sql);
	}

	/**
	 * 更新付款分配表行项目是否已结清标志。
	 * 
	 * @param contractNos
	 * @param isCleared
	 */
	public void updateIsClearedByContractNo(String contractNos, String isCleared)
	{
		String sql = "update YPAYMENTITEM set BILLISCLEAR ='" + isCleared + "' where CONTRACT_NO in(" + contractNos + ")";
		log.debug("更新付款分配表行项目是否已结清标志:" + sql);
		this.getJdbcTemplate().execute(sql);
	}

	/**
	 * 更新付款分配表行项目是否已结清标志。
	 * 
	 * @param contractNos
	 * @param projectNos
	 * @param isCleared
	 */
	public void updateIsClearedByProjectNo(String contractNos, String projectNos, String isCleared)
	{
		String sql = "update YPAYMENTITEM set BILLISCLEAR ='" + isCleared + "' where PROJECT_NO in(" + projectNos + ") ";
		if (!StringUtils.isNullBlank(contractNos))
			sql += " and CONTRACT_NO not in(" + contractNos + ")";
		log.debug("更新付款分配表行项目是否已结清标志:" + sql);
		this.getJdbcTemplate().execute(sql);
	}

	/**
	 * 更新付款分配表行项目是否已结清标志。
	 * 
	 * @param supplier
	 * @param contractNos
	 * @param projectNos
	 * @param isCleared
	 */
	public void updateIsClearedBySupplier(String supplier, String contractNos, String projectNos, String isCleared)
	{
		String sql = "update YPAYMENTITEM set BILLISCLEAR ='" + isCleared + "' where PAYMENTID in(SELECT PAYMENTID from YPAYMENT where SUPPLIER='" + supplier + "')";
		if (!StringUtils.isNullBlank(contractNos))
			sql += " and CONTRACT_NO not in(" + contractNos + ")";
		if (!StringUtils.isNullBlank(projectNos))
			sql += " and PROJECT_NO not in(" + projectNos + ")";
		log.debug("更新付款分配表行项目是否已结清标志:" + sql);
		this.getJdbcTemplate().execute(sql);
	}

	/**
	 * 根据业务状态、付款分配行项ID，取得款已经审批完金额(款已清金额)、在途金额(款在途金额)。
	 * 
	 * @param paymentItemId
	 * @param businessstates
	 * @return
	 */
	public BigDecimal getSumClearAmount(String paymentItemId, String businessstates)
	{
		boolean ispure = isPureAgent(paymentItemId);
		StringBuffer sb = new StringBuffer();
		sb.append("select nvl(sum(AMOUNT),0) from (");
		
		//审批通过
		if(BusinessState.ALL_SUBMITPASS.equals(businessstates)){
			// 付款清票
			sb.append(" select nvl(sum(a.assignamount-a.PREPAYAMOUNT),0) as AMOUNT from YPAYMENTITEM a ,YPAYMENT b");
			sb.append("  where a.PAYMENTID=b.PAYMENTID and a.PAYMENTITEMID='" + paymentItemId + "'  " ); 
			sb.append(" and ( ( b.businessstate  in ('3', '4','7')   and b.paymenttype in ('06', '07')) " );
			sb.append(" or ( b.paymenttype not in ('06', '07') and b.businessstate in ("+businessstates+") )" );
			sb.append(" or (b.pay_type in ('2') and b.businessstate  in ('3','4','7')) ");
			sb.append(" or ( b.pay_type not in ('2') and b.businessstate  in ("+businessstates+") ))    ");
			sb.append(" union ");
		}
		//在批中
		if(BusinessState.ALL_PAYMENT_ONROAD.equals(businessstates)){
			// 付款清票
			sb.append(" select nvl(sum(a.assignamount-a.PREPAYAMOUNT),0) as AMOUNT from YPAYMENTITEM a ,YPAYMENT b");
			sb.append("  where a.PAYMENTID=b.PAYMENTID and a.PAYMENTITEMID='" + paymentItemId + "'  " ); 
			sb.append(" and ( ( b.businessstate not in ('3', '4','7')   and b.paymenttype in ('06', '07')) " );
			sb.append(" or ( b.paymenttype not in ('06', '07') and b.businessstate in ("+businessstates+") )" );
			sb.append(" or (b.pay_type in ('2') and b.businessstate not in ('3','4','7')) ");
			sb.append(" or ( b.pay_type not in ('2') and b.businessstate  in ("+businessstates+") ))    ");
			sb.append(" union ");
		}
		// 付款清票
//		sb.append(" select nvl(sum(a.assignamount-a.PREPAYAMOUNT),0) as AMOUNT from YPAYMENTITEM a ,YPAYMENT b");
//		sb.append("  where a.PAYMENTID=b.PAYMENTID and a.PAYMENTITEMID='" + paymentItemId + "' and b.BUSINESSSTATE in(" + businessstates + ")");
//		sb.append(" union ");
		// 票清付款
		sb.append(" select nvl(sum(NOWCLEARAMOUNT),0) as AMOUNT from YBILLINPAYMENT a ,YBILLCLEAR b ");
		sb.append(" where a.BILLCLEARID=b.BILLCLEARID and b.CLEARTYPE='2' and a.PAYMENTITEMID='" + paymentItemId + "' and b.BUSINESSSTATE in(" + businessstates + ")");
		sb.append(" union ");
		// 供应商单清
		sb.append(" select nvl(sum(NOWCLEARAMOUNT),0) as AMOUNT from YUNCLEARPAYMENT a ,YSUPPLIERSICLEAR b ");
		sb.append(" where a.SUPPLIERSCLEARID=b.SUPPLIERSCLEARID and a.PAYMENTITEMID='" + paymentItemId + "' and b.BUSINESSSTATE in(" + businessstates + ")");
		sb.append(" union ");
		// 客户单清中特殊部分。
		sb.append(" select nvl(sum(CLEARAMOUNT),0) as AMOUNT  from YUNCLEARCUSTBILL a,YCUSTOMSICLEAR b  ");
		sb.append(" where a.CUSTOMSCLEARID = b.CUSTOMSCLEARID and a.PAYMENTID is not null and   a.PAYMENTID!=' ' and a.PAYMENTITEMID='" + paymentItemId + "' and b.BUSINESSSTATE in(" + businessstates + ")");
		//供应商退款
		//审批通过
		if(BusinessState.ALL_SUBMITPASS.equals(businessstates)){
			//如果是供应商退款纯代理要取本位币
			if(ispure){
				sb.append(" union ");
				sb.append(" SELECT nvl(sum(a.refundmentvalue),0) as AMOUNT   FROM yrefundmentitem a,yrefundment b ");
				sb.append(" WHERE a.refundmentid=b.refundmentid AND a.paymentitemid=trim('" + paymentItemId + "') AND  trim(a.paymentitemid) is not null AND a.istybond!='Y' AND b.businessstate IN (" + BusinessState.ALL_COLLECT_PAIDUP + ")");
			}else{
				sb.append(" union ");
				sb.append(" SELECT nvl(sum(a.refundmentamount),0) as AMOUNT   FROM yrefundmentitem a,yrefundment b ");
				sb.append(" WHERE a.refundmentid=b.refundmentid AND a.paymentitemid=trim('" + paymentItemId + "') AND  trim(a.paymentitemid) is not null AND a.istybond!='Y' AND b.businessstate IN (" + BusinessState.ALL_COLLECT_PAIDUP + ")");
			}
		}
		//在批中
		if(BusinessState.ALL_PAYMENT_ONROAD.equals(businessstates)){
			//如果是供应商退款纯代理要取本位币
			if(ispure){
				sb.append(" union ");
				sb.append(" SELECT nvl(sum(a.refundmentvalue),0) as AMOUNT   FROM yrefundmentitem a,yrefundment b ");
				sb.append(" WHERE a.refundmentid=b.refundmentid AND a.paymentitemid=trim('" + paymentItemId + "') AND  trim(a.paymentitemid) is not null AND a.istybond!='Y' AND b.businessstate IN (" + BusinessState.ALL_COLLECT_ONROAD + ")");
			}else{
				sb.append(" union ");
				sb.append(" SELECT nvl(sum(a.refundmentamount),0) as AMOUNT   FROM yrefundmentitem a,yrefundment b ");
				sb.append(" WHERE a.refundmentid=b.refundmentid AND a.paymentitemid=trim('" + paymentItemId + "') AND  trim(a.paymentitemid) is not null AND a.istybond!='Y' AND b.businessstate IN (" + BusinessState.ALL_COLLECT_ONROAD + ")");
			}
		}
		
		sb.append(" )");
		log.debug("根据业务状态，取得款已经审批完金额(款已清金额)、在途金额(款在途金额):" + sb.toString());
		BigDecimal list = (BigDecimal) this.getJdbcTemplate().queryForObject(sb.toString(), BigDecimal.class);

		if (null == list)
			return new BigDecimal(0);
		else
			return list;
	}

	/**
	 * 根据业务状态、付款分配行项ID，取得款已经审批完金额(款已清金额)、在途金额(款在途金额)。去掉本次的
	 * 
	 * @param paymentItemId
	 * @param businessstates
	 * @return
	 */
	public BigDecimal getSumClearAmount(String paymentItemId, String businessstates,String billclearid)
	{
		boolean ispure = isPureAgent(paymentItemId);
		StringBuffer sb = new StringBuffer();
		sb.append("select nvl(sum(AMOUNT),0) from (");
		// 付款清票
		sb.append(" select nvl(sum(a.assignamount-a.PREPAYAMOUNT),0) as AMOUNT from YPAYMENTITEM a ,YPAYMENT b");
		sb.append("  where a.PAYMENTID=b.PAYMENTID and a.PAYMENTITEMID='" + paymentItemId + "' and b.BUSINESSSTATE in(" + businessstates + ")");
		sb.append(" union ");
		// 票清付款
		sb.append(" select nvl(sum(NOWCLEARAMOUNT),0) as AMOUNT from YBILLINPAYMENT a ,YBILLCLEAR b ");
		sb.append(" where a.BILLCLEARID=b.BILLCLEARID and b.CLEARTYPE='2' and a.PAYMENTITEMID='" + paymentItemId + "' and b.BUSINESSSTATE in(" + businessstates + ")");
		if(null != billclearid && !"".equals(billclearid)){
			sb.append(" and a.BILLCLEARID!='" + billclearid + "'" );
		}
		sb.append(" union ");
		// 供应商单清
		sb.append(" select nvl(sum(NOWCLEARAMOUNT),0) as AMOUNT from YUNCLEARPAYMENT a ,YSUPPLIERSICLEAR b ");
		sb.append(" where a.SUPPLIERSCLEARID=b.SUPPLIERSCLEARID and a.PAYMENTITEMID='" + paymentItemId + "' and b.BUSINESSSTATE in(" + businessstates + ")");
		sb.append(" union ");
		// 客户单清中特殊部分。
		sb.append(" select nvl(sum(CLEARAMOUNT),0) as AMOUNT  from YUNCLEARCUSTBILL a,YCUSTOMSICLEAR b  ");
		sb.append(" where a.CUSTOMSCLEARID = b.CUSTOMSCLEARID and a.PAYMENTID is not null and   a.PAYMENTID!=' ' and a.PAYMENTITEMID='" + paymentItemId + "' and b.BUSINESSSTATE in(" + businessstates + ")");
		//供应商退款
		//审批通过
		if(BusinessState.ALL_SUBMITPASS.equals(businessstates)){
			//如果是供应商退款纯代理要取本位币
			if(ispure){
				sb.append(" union ");
				sb.append(" SELECT nvl(sum(a.refundmentvalue),0) as AMOUNT   FROM yrefundmentitem a,yrefundment b ");
				sb.append(" WHERE a.refundmentid=b.refundmentid AND a.paymentitemid=trim('" + paymentItemId + "') AND  trim(a.paymentitemid) is not null AND a.istybond!='Y' AND b.businessstate IN (" + BusinessState.ALL_COLLECT_PAIDUP + ")");
			}else{
				sb.append(" union ");
				sb.append(" SELECT nvl(sum(a.refundmentamount),0) as AMOUNT   FROM yrefundmentitem a,yrefundment b ");
				sb.append(" WHERE a.refundmentid=b.refundmentid AND a.paymentitemid=trim('" + paymentItemId + "') AND  trim(a.paymentitemid) is not null AND a.istybond!='Y' AND b.businessstate IN (" + BusinessState.ALL_COLLECT_PAIDUP + ")");
			}
		}
		//在批中
		if(BusinessState.ALL_PAYMENT_ONROAD.equals(businessstates)){
			//如果是供应商退款纯代理要取本位币
			if(ispure){
				sb.append(" union ");
				sb.append(" SELECT nvl(sum(a.refundmentvalue),0) as AMOUNT   FROM yrefundmentitem a,yrefundment b ");
				sb.append(" WHERE a.refundmentid=b.refundmentid AND a.paymentitemid=trim('" + paymentItemId + "') AND  trim(a.paymentitemid) is not null AND a.istybond!='Y' AND b.businessstate IN (" + BusinessState.ALL_COLLECT_ONROAD + ")");
			}else{
				sb.append(" union ");
				sb.append(" SELECT nvl(sum(a.refundmentamount),0) as AMOUNT   FROM yrefundmentitem a,yrefundment b ");
				sb.append(" WHERE a.refundmentid=b.refundmentid AND a.paymentitemid=trim('" + paymentItemId + "') AND  trim(a.paymentitemid) is not null AND a.istybond!='Y' AND b.businessstate IN (" + BusinessState.ALL_COLLECT_ONROAD + ")");
			}
		}
		
		sb.append(" )");
		log.debug("根据业务状态，取得款已经审批完金额(款已清金额)、在途金额(款在途金额):" + sb.toString());
		BigDecimal list = (BigDecimal) this.getJdbcTemplate().queryForObject(sb.toString(), BigDecimal.class);

		if (null == list)
			return new BigDecimal(0);
		else
			return list;
	}
	/**
	 * 根据合同号取得已清款金额合计
	 * 
	 * @param contractNo
	 * @return
	 */
	public BigDecimal getSumAmount(String contractNo)
	{
		StringBuffer sb = new StringBuffer();
		sb.append("select nvl(sum(ASSIGNAMOUNT),0) as AMOUNT from YPAYMENTITEM a ");
		sb.append(" where a.BILLISCLEAR='" + cleared.isCleared + "' and a.CONTRACT_NO='" + contractNo + "'");

		BigDecimal list = (BigDecimal) this.getJdbcTemplate().queryForObject(sb.toString(), BigDecimal.class);
		log.debug("根据合同号取得已清款金额合计:" + sb.toString());

		if (null == list)
			return new BigDecimal(0);
		else
			return list;
	}

	/**
	 * 根据立项号取得已清款金额合计（排除已清合同。）
	 * 
	 * @param contractNo
	 * @return
	 */
	public BigDecimal getSumAmount(String projectNo, String contractNos)
	{
		StringBuffer sb = new StringBuffer();
		sb.append("select nvl(sum(ASSIGNAMOUNT),0) as AMOUNT from YPAYMENTITEM a ");
		sb.append(" where a.BILLISCLEAR='" + cleared.isCleared + "' and a.Project_No='" + projectNo + "'");
		if (!StringUtils.isNullBlank(contractNos))
			sb.append(" and a.CONTRACT_NO not in(" + contractNos + ")");

		BigDecimal list = (BigDecimal) this.getJdbcTemplate().queryForObject(sb.toString(), BigDecimal.class);
		log.debug("根据合同号取得已清款金额合计:" + sb.toString());

		if (null == list)
			return new BigDecimal(0);
		else
			return list;
	}

	/**
	 * 根据供应商取得已清款金额合计（排除已清立项、合同、公司。）
	 * 
	 * @param contractNo
	 * @return
	 */
	public BigDecimal getSumAmount(String supplier, String contractNos, String projectNos, String bukrs)
	{
		StringBuffer sb = new StringBuffer();
		sb.append("select nvl(sum(b.ASSIGNAMOUNT),0) as AMOUNT from YPAYMENT a left outer join YPAYMENTITEM b on a.PAYMENTID=b.PAYMENTID ");
		sb.append(" where b.BILLISCLEAR='" + cleared.isCleared + "' and a.SUPPLIER='" + supplier + "'");
		if (!StringUtils.isNullBlank(contractNos))
			sb.append(" and b.CONTRACT_NO not in(" + contractNos + ")");
		if (!StringUtils.isNullBlank(projectNos))
			sb.append(" and b.PROJECT_NO not in(" + projectNos + ")");

		BigDecimal list = (BigDecimal) this.getJdbcTemplate().queryForObject(sb.toString(), BigDecimal.class);
		log.debug("根据合同号取得已清款金额合计:" + sb.toString());

		if (null == list)
			return new BigDecimal(0);
		else
			return list;
	}
	/**
	 * 判断是否是供应商退款纯代理
	 * @param paymentitemid 付款行项目ID
	 * @return true 为是，false为否
	 */
	public boolean isPureAgent(String paymentitemid){
		String sql="SELECT a.representpaycust FROM yrefundment a ,yrefundmentitem b WHERE a.refundmentid=b.refundmentid AND trim(a.representpaycust) IS NOT NULL AND b.paymentitemid='"+paymentitemid+"'";
		List<Map> rowList = this.getJdbcTemplate().queryForList(sql);
//		String list = (String) this.getJdbcTemplate().queryForObject(sql, String.class);
		if (null != rowList && rowList.size() > 0){
			return true;
		}else{
			return false;
		}
	}
	/**
	 * 根据付款itemid取得供应商退款纯代理金额合计
	 * @param paymentitemid
	 * @return
	 */
	public BigDecimal getRefundmentAmount(String paymentitemid){
		String sql="SELECT nvl(sum(b.refundmentvalue),0) FROM yrefundment a ,yrefundmentitem b WHERE a.refundmentid=b.refundmentid AND trim(a.representpaycust) IS NOT NULL AND a.businessstate='3' AND b.paymentitemid='"+paymentitemid+"'";
		BigDecimal list = (BigDecimal) this.getJdbcTemplate().queryForObject(sql, BigDecimal.class);
		log.debug("根据付款itemid取得供应商退款纯代理金额合计:" + sql);

		if (null == list)
			return new BigDecimal(0);
		else
			return list;
	}
	/**
	 * 取得当前的本位币金额
	 * @param paymentitemid
	 * @return
	 */
	public BigDecimal getCurrAmount(String paymentid){
		String sql="SELECT p.applyamount * p.closeexchangerat FROM ypayment p WHERE p.paymentid='"+paymentid+"'";
		BigDecimal list = (BigDecimal) this.getJdbcTemplate().queryForObject(sql, BigDecimal.class);
		log.debug("取得当前的本位币金额:" + sql);

		if (null == list)
			return new BigDecimal(0);
		else
			return list;
	}
	public String getbussinessState(String paymentid){
		String sql="SELECT p.businessstate FROM ypayment p WHERE p.paymentid='"+paymentid+"'";
		String list = (String) this.getJdbcTemplate().queryForObject(sql, String.class);
		log.debug("取得当前的业务状态:" + sql);

		if (null == list)
			return "";
		else
			return list;
	}
}
