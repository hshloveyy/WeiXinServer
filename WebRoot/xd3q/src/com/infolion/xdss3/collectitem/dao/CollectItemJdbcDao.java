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
package com.infolion.xdss3.collectitem.dao;

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
import com.infolion.xdss3.collect.domain.Collect;
import com.infolion.xdss3.collectitem.domain.CollectItem;

/**
 * <pre>
 * 收款金额分配表(CollectItem)JDBC操作类
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
public class CollectItemJdbcDao extends BaseJdbcDao
{

	// /**
	// * 根据供应商，取得未清付款行项信息(付款已经审批通过)。
	// *
	// * @param project_no
	// * @param contract_no
	// * @return
	// */
	// public List<ImportPaymentItem> getUnclearPaymentItemList(String supplier,
	// String businessstates)
	// {
	// List<ImportPaymentItem> paymentItemList = new
	// ArrayList<ImportPaymentItem>();
	// StringBuffer sb = new StringBuffer();
	// sb.append("SELECT * FROM YPAYMENT a ,YPAYMENTITEM b WHERE a.SUPPLIER='" +
	// supplier + "'");
	// sb.append(" and  a.businessstate in(" + businessstates +
	// ") and a.PAYMENTID=b.PAYMENTID and b.BILLISCLEAR='0' and a.PAYMENTTYPE in('0','1') ");
	// log.debug("根据供应商，取得未清付款行项信息:" + sb.toString());
	// List<Map> rowList = this.getJdbcTemplate().queryForList(sb.toString());
	// for (Map map : rowList)
	// {
	// ImportPayment importPayment = new ImportPayment();
	// ExBeanUtils.setBeanValueFromMap(importPayment, map);
	// ImportPaymentItem paymentItem = new ImportPaymentItem();
	// ExBeanUtils.setBeanValueFromMap(paymentItem, map);
	// paymentItem.setImportPayment(importPayment);
	// paymentItemList.add(paymentItem);
	// }
	//
	// return paymentItemList;
	// }

	/**
	 * 根据业务状态、收款分配行项ID，取得收款已经审批完金额(款已清金额)、在途金额(款在途金额)。
	 * 
	 * @param collectItemId
	 * @param businessstates
	 * @return
	 */
	public BigDecimal getSumClearAmount(String collectItemId, String businessstates)
	{
		StringBuffer sb = new StringBuffer();
		sb.append("select nvl(sum(AMOUNT),0) from (");
		// 收款清票
//		sb.append(" select nvl(sum(a.ASSIGNAMOUNT-a.PREBILLAMOUNT-a.suretybond),0) as AMOUNT from YCOLLECTITEM a ,YCOLLECT b");
//		sb.append(" where a.COLLECTID=b.COLLECTID and a.COLLECTITEMID=trim('" + collectItemId + "') and trim(a.COLLECTITEMID) is not null and b.BUSINESSSTATE in(" + businessstates + ")");
//		sb.append(" union ");
		//审批通过
		if(BusinessState.ALL_COLLECT_PAIDUP.equals(businessstates)){
			sb.append(" select nvl(sum(a.ASSIGNAMOUNT-a.PREBILLAMOUNT-a.suretybond),0) as AMOUNT from YCOLLECTITEM a ,YCOLLECT b");
			sb.append(" where a.COLLECTID=b.COLLECTID and a.COLLECTITEMID=trim('" + collectItemId + "') and trim(a.COLLECTITEMID) is not null " );
			sb.append(" and ( ( b.collecttype in ('11', '12')   and b.businessstate  in ('2', '3')  ) or ( b.collecttype not in ('11', '12') and b.businessstate in (" + businessstates + ") )) " );					
			sb.append(" union ");
		}
		//在批中
		if(BusinessState.ALL_COLLECT_ONROAD.equals(businessstates)){
			sb.append(" select nvl(sum(a.ASSIGNAMOUNT-a.PREBILLAMOUNT-a.suretybond),0) as AMOUNT from YCOLLECTITEM a ,YCOLLECT b");
			sb.append(" where a.COLLECTID=b.COLLECTID and a.COLLECTITEMID=trim('" + collectItemId + "') and trim(a.COLLECTITEMID) is not null " );
			sb.append(" and ( ( b.collecttype in ('11', '12')   and b.businessstate not in ('2', '3')  ) or ( b.collecttype not in ('11', '12') and b.businessstate in (" + businessstates + ") )) " );					
			sb.append(" union ");
		}
		// 票清收款
		sb.append("select nvl(sum(a.NOWCLEARAMOUNT),0) as AMOUNT from YBILLINCOLLECT a ,YBILLCLEAR b ");
		sb.append(" where a.BILLCLEARID=b.BILLCLEARID and b.CLEARTYPE='1' and a.COLLECTITEMID=trim('" + collectItemId + "') and trim(a.COLLECTITEMID) is not null  and b.BUSINESSSTATE in(" + businessstates + ")");
		sb.append(" union ");
		// 客户单清
		sb.append(" select nvl(sum(a.NOWCLEARAMOUNT),0) as AMOUNT from YUNCLEARCOLLECT a ,YCUSTOMSICLEAR b ");
		sb.append(" where a.CUSTOMSCLEARID=b.CUSTOMSCLEARID and a.COLLECTITEMID=trim('" + collectItemId + "') and trim(a.COLLECTITEMID) is not null  and b.businessstate in(" + businessstates + ")");
		//客户退款
		sb.append(" union ");
		sb.append(" SELECT nvl(sum(a.refundmentamount),0) as AMOUNT   FROM yrefundmentitem a,yrefundment b ");
		sb.append(" WHERE a.refundmentid=b.refundmentid AND a.collectitemid=trim('" + collectItemId + "') AND  trim(a.COLLECTITEMID) is not null  AND b.businessstate IN (" + businessstates + ")");
		
		sb.append(" )");
		log.debug("根据业务状态，取得收款已经审批完金额(款已清金额)、在途金额(款在途金额):" + sb.toString());
		BigDecimal list = (BigDecimal) this.getJdbcTemplate().queryForObject(sb.toString(), BigDecimal.class);

		if (null == list)
			return new BigDecimal(0);
		else
			return list;
	}
	
	/**
	 * 根据业务状态、收款分配行项ID，取得收款已经审批完金额(款已清金额)、在途金额(款在途金额)。去掉本次的
	 * 
	 * @param collectItemId
	 * @param businessstates
	 * @return
	 */
	public BigDecimal getSumClearAmount(String collectItemId, String businessstates,String billclearid)
	{
		StringBuffer sb = new StringBuffer();
		sb.append("select nvl(sum(AMOUNT),0) from (");
		// 收款清票
		sb.append(" select nvl(sum(a.ASSIGNAMOUNT-a.PREBILLAMOUNT-a.SURETYBOND),0) as AMOUNT from YCOLLECTITEM a ,YCOLLECT b");
		sb.append(" where a.COLLECTID=b.COLLECTID and a.COLLECTITEMID=trim('" + collectItemId + "') and trim(a.COLLECTITEMID) is not null  and b.BUSINESSSTATE in(" + businessstates + ")");
		sb.append(" union ");
		// 票清收款
		sb.append("select nvl(sum(a.NOWCLEARAMOUNT),0) as AMOUNT from YBILLINCOLLECT a ,YBILLCLEAR b ");
		sb.append(" where a.BILLCLEARID=b.BILLCLEARID and b.CLEARTYPE='1' and a.COLLECTITEMID=trim('" + collectItemId + "') and trim(a.COLLECTITEMID) is not null  and b.BUSINESSSTATE in(" + businessstates + ")");
		if(null != billclearid && !"".equals(billclearid)){
			sb.append(" and a.BILLCLEARID!='" + billclearid + "'" );
		}
		sb.append(" union ");
		// 客户单清
		sb.append(" select nvl(sum(a.NOWCLEARAMOUNT),0) as AMOUNT from YUNCLEARCOLLECT a ,YCUSTOMSICLEAR b ");
		sb.append(" where a.CUSTOMSCLEARID=b.CUSTOMSCLEARID and a.COLLECTITEMID=trim('" + collectItemId + "') and trim(a.COLLECTITEMID) is not null  and b.businessstate in(" + businessstates + ")");
		//客户退款
		sb.append(" union ");
		sb.append(" SELECT nvl(sum(a.refundmentamount),0) as AMOUNT   FROM yrefundmentitem a,yrefundment b ");
		sb.append(" WHERE a.refundmentid=b.refundmentid AND a.collectitemid=trim('" + collectItemId + "') AND  trim(a.COLLECTITEMID) is not null   AND b.businessstate IN (" + businessstates + ")");
		
		sb.append(" )");
		log.debug("根据业务状态，取得收款已经审批完金额(款已清金额)、在途金额(款在途金额):" + sb.toString());
		BigDecimal list = (BigDecimal) this.getJdbcTemplate().queryForObject(sb.toString(), BigDecimal.class);

		if (null == list)
			return new BigDecimal(0);
		else
			return list;
	}
	/**
	 * 根据业务状态，取得退款保证金在途金额
	 * @param collectItemId
	 * @param businessstates
	 * @return
	 */
	public BigDecimal getSumClearAmountByRefundment(String collectItemId, String businessstates){
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT nvl(sum(a.refundmentamount),0) as AMOUNT   FROM yrefundmentitem a,yrefundment b ");
		sb.append(" WHERE a.refundmentid=b.refundmentid AND a.collectitemid=trim('" + collectItemId + "') AND  trim(a.COLLECTITEMID) is not null AND a.istybond='Y'  AND b.businessstate IN (" + businessstates + ")");
		log.debug("根据业务状态，取得退款在途金额(款在途金额):" + sb.toString());
		BigDecimal list = (BigDecimal) this.getJdbcTemplate().queryForObject(sb.toString(), BigDecimal.class);

		if (null == list)
			return new BigDecimal(0);
		else
			return list;
	}
	
	public CollectItem getCollectItem(String collectitemid)
	{
		String sql = "select * from YCOLLECTITEM a,ycollect b where a.collectid = b.collectid and a.collectitemid='" + collectitemid + "'";
		List<Map> rowList = this.getJdbcTemplate().queryForList(sql.toString());
		if(rowList.size()==0)return null;
		CollectItem collectItem = new CollectItem();
		ExBeanUtils.setBeanValueFromMap(collectItem, rowList.get(0));
		Collect collect = new Collect();
		ExBeanUtils.setBeanValueFromMap(collect, rowList.get(0));
		collectItem.setCollect(collect);
		
		return collectItem;
	}
	
	/**
	 * 通过退款行项表反查收款行项信息
	 * @param collectitemid
	 * @return
	 */
	public CollectItem getCollectItemByRefundment(String refundmentitemid)
	{
		String sql = "select a.*,b.* from YCOLLECTITEM a,ycollect b, yrefundmentitem r where a.collectid = b.collectid and a.collectitemid=r.collectitemid and r.refundmentitemid='" + refundmentitemid + "'";
		List<Map> rowList = this.getJdbcTemplate().queryForList(sql.toString());
		CollectItem collectItem = new CollectItem();
		ExBeanUtils.setBeanValueFromMap(collectItem, rowList.get(0));
		Collect collect = new Collect();
		ExBeanUtils.setBeanValueFromMap(collect, rowList.get(0));
		collectItem.setCollect(collect);
		
		return collectItem;
	}
	/**
	 * 更新收款分配表行项目是否已结清标志。
	 * 
	 * @param vendortitleid
	 * @param isCleared
	 */
	public void updateCollectItemIsCleared(String collectItemId, String isCleared)
	{
		String sql = "update YCOLLECTITEM set ISCLEAR ='" + isCleared + "' where COLLECTITEMID ='" + collectItemId + "'";
		log.debug("更新收款分配表行项目是否已结清标志:" + sql);
		this.getJdbcTemplate().execute(sql);
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
		sb.append("select nvl(sum(ASSIGNAMOUNT),0) as AMOUNT from YCOLLECTITEM a ");
		sb.append(" where a.ISCLEAR='" + cleared.isCleared + "' and a.CONTRACT_NO='" + contractNo + "'");

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
		sb.append("select nvl(sum(ASSIGNAMOUNT),0) as AMOUNT from YCOLLECTITEM a ");
		sb.append(" where a.ISCLEAR='" + cleared.isCleared + "' and a.Project_No='" + projectNo + "'");
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
	 * @param customer
	 * @param contractNos
	 * @param projectNos
	 * @param bukrs
	 * @return
	 */
	public BigDecimal getSumAmount(String customer, String contractNos, String projectNos, String bukrs)
	{
		StringBuffer sb = new StringBuffer();
		sb.append("select nvl(sum(b.ASSIGNAMOUNT),0) as AMOUNT from YCOLLECT a left outer join YCOLLECTITEM b on a.COLLECTID=b.COLLECTID ");
		sb.append(" where b.ISCLEAR='" + cleared.isCleared + "' and a.CUSTOMER='" + customer + "'");
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
	 * 根据客户取得未清的收款行项目
	 * @param customer
	 * @return
	 */
	public List<CollectItem> getCollectItemByCustomer(String customer)
	{
		List<CollectItem> collectItemList = new ArrayList<CollectItem>();
		String sql = "select a.goodsamount,b.*,a.*,b.collectno from YCOLLECTITEM a,ycollect b where a.collectid = b.collectid and b.customer='" + customer + "' and (a.isclear ='0' or trim(a.isclear) is null) order by a.contract_no desc, b.createtime asc ";
		List<Map> rowList = this.getJdbcTemplate().queryForList(sql.toString());
		for(Map m : rowList){
			CollectItem collectItem = new CollectItem();
			ExBeanUtils.setBeanValueFromMap(collectItem, m);
			Collect collect = new Collect();
			ExBeanUtils.setBeanValueFromMap(collect, m);
			collectItem.setCollect(collect);
			collectItemList.add(collectItem);
		}
		return collectItemList;
	}	
	
	/**
	 * 根据合同号取得原合同号(纸质合同号)
	 * 
	 * @param Contract_No
	 * @return
	 */
	public String getOldContractNo(String Contract_No)
	{
		String strSql = "select VBKD_IHREZ from t_contract_sales_info where Contract_No='" + Contract_No + "'";
		log.debug("" + strSql);

		List<Map> rowList = this.getJdbcTemplate().queryForList(strSql);
		if (null != rowList && rowList.size() > 0)
		{
			return (String) ((Map) rowList.get(0)).get("VBKD_IHREZ");
		}
		else
			return "";
	}
	/**
	 * 取得保证金转货款金额
	 * @param collectitemid
	 * @return
	 */
	public BigDecimal getSumAmountBySuretybond(String collectitemid){
		StringBuffer sb = new StringBuffer();
		sb.append("select nvl(sum(actamount),0) as AMOUNT from ycollect a ");
		sb.append(" where a.businessstate='3' and TRIM(a.oldcollectitemid) IS NOT NULL and a.oldcollectitemid='" + collectitemid + "' ");
		

		BigDecimal amount = (BigDecimal) this.getJdbcTemplate().queryForObject(sb.toString(), BigDecimal.class);
		log.debug("根据收款行项目ID取得已审批的保证金转货款金额合计:" + sb.toString());

		if (null == amount)
			return new BigDecimal(0);
		else
			return amount;
	}
	
}
