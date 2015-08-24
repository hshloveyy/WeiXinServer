/*
 * @(#)TradeMonitoringJdbcDao.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：林进旭
 *  时　间：2010-5-7
 *  描　述：创建
 */

package com.infolion.xdss3.tradeMonitoring.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.infolion.platform.basicApp.authManage.UserContextHolder;
import com.infolion.platform.console.sys.context.UserContext;
import com.infolion.platform.dpframework.core.dao.BaseJdbcDao;
import com.infolion.platform.dpframework.util.ExBeanUtils;
import com.infolion.sapss.ReceiptShipConstants;
import com.infolion.xdss3.BusinessState;
import com.infolion.xdss3.tradeMonitoring.domain.QueryField;
import com.infolion.xdss3.tradeMonitoring.domain.TradeMonitoringTreeNode;
import com.infolion.xdss3.tradeMonitoring.domain.TradeType;
import com.infolion.xdss3.tradeMonitoring.domain.XdssBusinessType;

/**
 * <pre>
 * 贸易监控JDBC操作类
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
public class TradeMonitoringJdbcDao extends BaseJdbcDao
{
	/**
	 * 是否启用部门数据权限控制。 备注：开发时候使用，方便查询，上线时候应该调整为true.
	 */
	private boolean isDeptAuth = false;

	/**
	 * 取得贸易类别
	 * 
	 * @param tradeTypeIds
	 * @return
	 */
	public List<TradeType> getTradeType(String queryField, String queryValue, String dept_id, String apply_time,String apply_time1)
	{
		List<TradeType> list = new ArrayList<TradeType>();
		String strSql = "select * from BM_BUSINESS_TYPE where 1=1 ";

		// LJX 20100706 添加数据权限控制
		UserContext userContext = com.infolion.platform.console.sys.context.UserContextHolder.getLocalUserContext().getUserContext();
		StringBuffer sb = new StringBuffer();
		if (isDeptAuth)
		{
			sb.append(" and a.DEPT_ID in ('" + userContext.getSysDept().getDeptid() + "'");
			if (StringUtils.isNotBlank(userContext.getGrantedDepartmentsId()))
				sb.append("," + userContext.getGrantedDepartmentsId() + "");
			sb.append(")");
		}
		// 过滤部门权限SQL语句
		String dataAuthSql = sb.toString();

		// 根据查询条件，过滤树节点
		if (StringUtils.isNotBlank(queryField))
		{
			// 立项
			if (QueryField.PROJECT_NO.equals(queryField))
			{
				strSql = strSql + " and ID in(select a.TRADE_TYPE  from T_PROJECT_INFO a where a.PROJECT_STATE in ('2','3','4','5','6','8') and a.IS_AVAILABLE = '1' ";

				if (StringUtils.isNotBlank(queryValue))
					strSql = strSql + " and a.PROJECT_NO='" + queryValue + "'";
				if (StringUtils.isNotBlank(dept_id))
					strSql = strSql + " and a.DEPT_ID='" + dept_id + "'";
				if (StringUtils.isNotBlank(apply_time))
					strSql = strSql + " and format_date(a.APPLY_TIME) >='" + apply_time + "'";
				if (StringUtils.isNotBlank(apply_time1))
					strSql = strSql + " and format_date(a.APPLY_TIME) <='" + apply_time1 + "'";

				strSql = strSql + dataAuthSql + ")";

			}
			else if (QueryField.PROJECT_NAME.equals(queryField))
			{
				strSql = strSql + " and ID in(select a.TRADE_TYPE  from T_PROJECT_INFO a where a.PROJECT_STATE in ('2','3','4','5','6','8') and a.IS_AVAILABLE = '1' ";

				if (StringUtils.isNotBlank(queryValue))
					strSql = strSql + " and a.PROJECT_NAME like '%" + queryValue + "%'";
				if (StringUtils.isNotBlank(dept_id))
					strSql = strSql + " and a.DEPT_ID='" + dept_id + "'";
				if (StringUtils.isNotBlank(apply_time))
					strSql = strSql + " and format_date(a.APPLY_TIME) >='" + apply_time + "'";
				if (StringUtils.isNotBlank(apply_time1))
					strSql = strSql + " and format_date(a.APPLY_TIME) <='" + apply_time1 + "'";

				strSql = strSql + dataAuthSql + ")";
			}
			else if (QueryField.PROJECT_CLASSNAME.equals(queryField))
			{
				strSql = strSql + " and ID in(select a.TRADE_TYPE  from T_PROJECT_INFO a where a.PROJECT_STATE in ('2','3','4','5','6','8') and a.IS_AVAILABLE = '1' ";

				if (StringUtils.isNotBlank(queryValue))
					strSql = strSql + " and a.CLASS_NAME like '%" + queryValue + "%'";
				if (StringUtils.isNotBlank(dept_id))
					strSql = strSql + " and a.DEPT_ID='" + dept_id + "'";
				if (StringUtils.isNotBlank(apply_time))
					strSql = strSql + " and format_date(a.APPLY_TIME) >='" + apply_time + "'";
				if (StringUtils.isNotBlank(apply_time1))
					strSql = strSql + " and format_date(a.APPLY_TIME) <='" + apply_time1 + "'";

				strSql = strSql + dataAuthSql + ")";
			}
			// 合同组
			else if (QueryField.CONTRACT_GROUP_NO.equals(queryField))
			{
				strSql = strSql + " and ID in(select a.TRADE_TYPE from T_CONTRACT_GROUP a where  a.IS_AVAILABLE = '1' ";
				strSql = strSql + " and a.CONTRACT_GROUP_NO='" + queryValue + "'" + dataAuthSql + ")";
			}
			else if (QueryField.CONTRACT_GROUP_NAME.equals(queryField))
			{
				strSql = strSql + " and ID in(select a.TRADE_TYPE from T_CONTRACT_GROUP a where  a.IS_AVAILABLE = '1' ";
				strSql = strSql + " and a.CONTRACT_GROUP_NAME like '%" + queryValue + "%'" + dataAuthSql + ")";
			}
			// 付款
			else if (QueryField.PAYMENT_NO.equals(queryField))
			{
				strSql = strSql + " and ID in(select c.TRADE_TYPE from YPAYMENT a left outer join YPAYMENTITEM b on a.PAYMENTID=b.PAYMENTID left outer join T_PROJECT_INFO c on b.PROJECT_NO=c.PROJECT_NO ";
				strSql = strSql + " where b.PAYMENTITEMID is not null and c.IS_AVAILABLE = '1' and a.PAYMENTNO='" + queryValue + "'" + dataAuthSql + ")";
			}
			else if (QueryField.PAYMENT_SAPORDERNO.equals(queryField))
			{
				strSql = strSql + " and ID in(select c.TRADE_TYPE from YPAYMENT a left outer join YPAYMENTITEM b on a.PAYMENTID=b.PAYMENTID left outer join T_CONTRACT_PURCHASE_INFO c on b.CONTRACT_NO=c.CONTRACT_NO ";
				strSql = strSql + " where b.PAYMENTITEMID is not null and c.IS_AVAILABLE = '1' and c.SAP_ORDER_NO='" + queryValue + "'" + dataAuthSql + ")";
			}
			// 收款
			else if (QueryField.COLLECT_NO.equals(queryField))
			{
				strSql = strSql + " and ID in(select c.TRADE_TYPE from YCOLLECT a left outer join YCOLLECTITEM b on a.COLLECTID=b.COLLECTID left outer join T_PROJECT_INFO c on b.PROJECT_NO=c.PROJECT_NO ";
				strSql = strSql + " where b.COLLECTITEMID is not null and c.IS_AVAILABLE = '1' and a.COLLECTNO='" + queryValue + "'" + dataAuthSql + ")";
			}
			else if (QueryField.COLLECT_SAPORDERNO.equals(queryField))
			{
				strSql = strSql + " and ID in(select c.TRADE_TYPE from YCOLLECT a left outer join YCOLLECTITEM b on a.collectid=b.collectid left outer join T_CONTRACT_SALES_INFO c on b.CONTRACT_NO=c.CONTRACT_NO ";
				strSql = strSql + " where b.COLLECTITEMID is not null and c.IS_AVAILABLE = '1' and c.SAP_ORDER_NO='" + queryValue + "'" + dataAuthSql + ")";
			}
			// 退款
			else if (QueryField.REFUNDMENTNO.equals(queryField))
			{
				strSql = strSql + " and ID in(select c.TRADE_TYPE from YREFUNDMENT a left outer join YREFUNDMENTITEM b on a.REFUNDMENTID=b.REFUNDMENTID left outer join T_PROJECT_INFO c on b.PROJECT_NO=c.PROJECT_NO ";
				strSql = strSql + " where c.IS_AVAILABLE = '1' and a.REFUNDMENTNO='" + queryValue + "'" + dataAuthSql + ")";
			}
			else if (QueryField.REFUNDMENTVOUCHERNO.equals(queryField))
			{
				strSql = strSql + " and ID in(select c.TRADE_TYPE from YREFUNDMENT a left outer join YREFUNDMENTITEM b on a.REFUNDMENTID=b.REFUNDMENTID left outer join T_PROJECT_INFO c on b.PROJECT_NO=c.PROJECT_NO left outer join YVOUCHER d on a.REFUNDMENTID = d.BUSINESSID ";
				strSql = strSql + " where d.voucherNO is not null and c.IS_AVAILABLE = '1' and d.VOUCHERNO='" + queryValue + "'" + dataAuthSql + ")";
			}
			// 采购合同
			else if (QueryField.CONTRACT_PURCHASE_NO.equals(queryField))
			{
				strSql = strSql + " and ID in(select a.TRADE_TYPE from T_CONTRACT_PURCHASE_INFO a where a.IS_AVAILABLE = '1' ";
				strSql = strSql + " and a.ORDER_STATE in ('10','11','2','3','5','6','7','8','9') and a.CONTRACT_NO='" + queryValue + "'" + dataAuthSql + ")";
			}
			else if (QueryField.EKKO_UNSEZ.equals(queryField))
			{
				strSql = strSql + " and ID in(select a.TRADE_TYPE from T_CONTRACT_PURCHASE_INFO a where a.IS_AVAILABLE = '1' ";
				strSql = strSql + " and a.ORDER_STATE in ('10','11','2','3','5','6','7','8','9') and a.EKKO_UNSEZ='" + queryValue + "'" + dataAuthSql + ")";
			}
			else if (QueryField.CONTRACT_PURCHASE_NAME.equals(queryField))
			{
				strSql = strSql + " and ID in(select a.TRADE_TYPE from T_CONTRACT_PURCHASE_INFO a where a.IS_AVAILABLE = '1' ";
				strSql = strSql + " and a.ORDER_STATE in ('10','11','2','3','5','6','7','8','9') and a.CONTRACT_NAME like '%" + queryValue + "%'" + dataAuthSql + ")";
			}
			else if (QueryField.CONTRACT_PURCHASE_PRONAME.equals(queryField))
			{
				strSql = strSql + " and exists (select '' from T_CONTRACT_PURCHASE_INFO a1 where a1.IS_AVAILABLE = '1'";
				strSql = strSql + " and a1.ORDER_STATE in ('10','11','2','3','5','6','7','8','9') and ID=a1.trade_type and UPPER(a1.ekko_lifnr_name) like '%" + queryValue.toUpperCase() + "%')";
			}    
			// 销售合同
			else if (QueryField.CONTRACT_SALES_NO.equals(queryField))
			{
				strSql = strSql + " and ID in(select a.TRADE_TYPE from T_CONTRACT_SALES_INFO a where a.IS_AVAILABLE = '1' ";
				strSql = strSql + " and a.ORDER_STATE in ('10','11','2','3','5','6','7','8','9') and a.CONTRACT_NO='" + queryValue + "'" + dataAuthSql + ")";
			}
			else if (QueryField.VBKD_IHREZ.equals(queryField))
			{
				strSql = strSql + " and ID in(select a.TRADE_TYPE from T_CONTRACT_SALES_INFO a where a.IS_AVAILABLE = '1' ";
				strSql = strSql + " and a.ORDER_STATE in ('10','11','2','3','5','6','7','8','9') and a.VBKD_IHREZ='" + queryValue + "'" + dataAuthSql + ")";
			}
			else if (QueryField.CONTRACT_SALES_NAME.equals(queryField))
			{
				strSql = strSql + " and ID in(select a.TRADE_TYPE from T_CONTRACT_SALES_INFO a where a.IS_AVAILABLE = '1' ";
				strSql = strSql + " and a.ORDER_STATE in ('10','11','2','3','5','6','7','8','9') and a.CONTRACT_NAME like '%" + queryValue + "%'" + dataAuthSql + ")";
			}
			else if (QueryField.CONTRACT_SALES_CUSNAME.equals(queryField))
			{
				strSql = strSql +" and exists (select '' from T_CONTRACT_SALES_INFO a1 where a1.IS_AVAILABLE = '1'";
				strSql = strSql +" and a1.ORDER_STATE in ('10','11','2','3','5','6','7','8','9') and ID=a1.trade_type and UPPER(a1.KUAGV_KUNNR_NAME) like '%" + queryValue.toUpperCase() + "%')";
			}
			// ////////销售合同、采购合同下 单据信息:
			// @@@@销售合同下,出仓、出单、收证、开票
			// 出仓、发货
			else if (QueryField.SHIP_NO.equals(queryField))
			{
				strSql = strSql + " and ID in(select a.TRADE_TYPE from T_SHIP_INFO a where  a.is_available = '1' ";
				strSql = strSql + " and a.examine_state in ('2', '3', '5','6', '7', '8') and a.SHIP_NO='" + queryValue + "'" + dataAuthSql + ")";
			}
			else if (QueryField.SAP_RETURN_NO2.equals(queryField))
			{
				strSql = strSql + " and ID in(select a.TRADE_TYPE from T_SHIP_INFO a where  a.is_available = '1' ";
				strSql = strSql + "and a.examine_state in ('2', '3', '5','6', '7', '8') and a.SAP_RETURN_NO='" + queryValue + "'" + dataAuthSql + ")";
			}
			// 出单
			else if (QueryField.INV_NO.equals(queryField))
			{
				strSql = strSql + " and ID in(select c.TRADE_TYPE from T_EXPORT_BILL_EXAM a, T_EXPORT_BILL_EXAM_D b, T_EXPORT_APPLY c ";
				strSql = strSql + "where  a.IS_AVAILABLE = '1' and c.IS_AVAILABLE = '1' and a.EXPORT_BILL_EXAM_ID = b.EXPORT_BILL_EXAM_ID ";
				strSql = strSql + "and b.EXPORT_APPLY_ID = c.EXPORT_APPLY_ID and a.INV_NO='" + queryValue + "'" + dataAuthSql + ")";
			}
			// 信用证开证、到证(收证)
			else if (QueryField.CREDIT_NO.equals(queryField))
			{
				strSql = strSql + " and ID in(select a.TRADE_TYPE from T_CREDIT_INFO a where a.IS_AVAILABLE = '1' ";
				strSql = strSql + "and a.CREDIT_STATE in('15','11','3','4','5','10','12','13') and a.CREDIT_NO='" + queryValue + "'" + dataAuthSql + ")";
			}
			// 内贸开票
			else if (QueryField.TAX_NO.equals(queryField))
			{
				strSql = strSql + " and ID in(select a.TRADE_TYPE from T_BILL_APPLY a where a.IS_AVAILABLE = '1' ";
				strSql = strSql + "and a.EXAMINE_STATE in ('2','3','5','6','7','8') and a.TAX_NO='" + queryValue + "'" + dataAuthSql + ")";
			}
			else if (QueryField.SAP_BILL_NO.equals(queryField))
			{
				strSql = strSql + " and ID in(select a.TRADE_TYPE from T_BILL_APPLY a where a.IS_AVAILABLE = '1' ";
				strSql = strSql + "and a.EXAMINE_STATE in ('2','3','5','6','7','8') and a.SAP_BILL_NO='" + queryValue + "'" + dataAuthSql + ")";
			}
			// 出口开票
			else if (QueryField.VBELN.equals(queryField))
			{
				strSql = strSql + " and ID in(select a.TRADE_TYPE from T_CONTRACT_SALES_INFO a where a.sap_order_no in(";
				strSql = strSql + "SELECT AUBEL from YGETVBRP WHERE VBELN='" + queryValue + "'" + dataAuthSql + "))";
			}
			// @@@@采购合同下,进仓(收货)、到单、开证
			// 进仓单(收货)
			else if (QueryField.RECEIPT_NO.equals(queryField))
			{
				strSql = strSql + " and ID in(select a.TRADE_TYPE from T_RECEIPT_INFO a where a.IS_AVAILABLE = '1' ";
				strSql = strSql + " and a.EXAMINE_STATE in ('2', '3', '5','6', '7', '8') and a.RECEIPT_NO='" + queryValue + "'" + dataAuthSql + ")";
			}
			else if (QueryField.SAP_RETURN_NO1.equals(queryField))
			{
				strSql = strSql + " and ID in(select a.TRADE_TYPE from T_RECEIPT_INFO a where a.IS_AVAILABLE = '1' ";
				strSql = strSql + " and a.EXAMINE_STATE in ('2', '3', '5','6', '7', '8') and a.SAP_RETURN_NO='" + queryValue + "'" + dataAuthSql + ")";
			}
			// 到单
			else if (QueryField.PICK_LIST_NO.equals(queryField))
			{
				strSql = strSql + " and ID in(select TRADE_TYPE from T_CONTRACT_PURCHASE_INFO where contract_purchase_id in(";
				strSql = strSql + "select a.CONTRACT_PURCHASE_ID from T_PICK_LIST_INFO a where a.IS_AVAILABLE = '1' and a.EXAMINE_STATE in ('2', '3', '5', '6', '7', '8') and a.PICK_LIST_NO='" + queryValue + "'" + dataAuthSql + "))";
			}
			// 发票校验
			else if (QueryField.BELNR.equals(queryField))
			{
				strSql = strSql + " and ID in(select b.TRADE_TYPE from  YGETBELNR a ";
				strSql = strSql + "left outer join T_CONTRACT_PURCHASE_INFO b on a.EBELN=b.SAP_ORDER_NO where a.BELNR='" + queryValue + "')";
			}
			// 其他还未实现的。
			else
			{
				strSql = strSql + " and 1=2";
			}

		}

		List<Map> rowList = this.getJdbcTemplate().queryForList(strSql);
		log.debug("查询贸易类型:" + strSql);
		for (Map map : rowList)
		{
			TradeType tradeType = new TradeType();
			ExBeanUtils.setBeanValueFromMap(tradeType, map);
			list.add(tradeType);
		}

		return list;
	}

	/**
	 * 公用，取得贸易监控树节点
	 * 
	 * @param sb
	 * @return
	 */
	private List<TradeMonitoringTreeNode> getColumnTreeList(StringBuffer sb, String businessType)
	{
		log.debug("SQL语句:" + sb.toString());
		List<TradeMonitoringTreeNode> list = new ArrayList<TradeMonitoringTreeNode>();
		List<Map> rowList = this.getJdbcTemplate().queryForList(sb.toString());

		for (Map map : rowList)
		{
			TradeMonitoringTreeNode tradeMonitoringTreeNode = new TradeMonitoringTreeNode();
			ExBeanUtils.setBeanValueFromMap(tradeMonitoringTreeNode, map);
			// 点击采购合同节点 销售合同节点、查询采购合同下收货、发票校验、付款、退货、开证、到单等
			if (XdssBusinessType.CONTRACT_PURCHASEINFO.equalsIgnoreCase(businessType) || XdssBusinessType.CONTRACT_SALESINFO.equalsIgnoreCase(businessType) || XdssBusinessType.OTHERPAYMENTCOLLECT.equalsIgnoreCase(businessType))
			{
				tradeMonitoringTreeNode.setLeaf(true);
			}
			list.add(tradeMonitoringTreeNode);
		}

		return list;
	}

	/**
	 * 取得贸易监控树节点
	 * 
	 * @param tradeType
	 * @param businessType
	 * @return
	 */
	public List<TradeMonitoringTreeNode> getTradeMonitoringColumnTree(String nodeId, String tradeType, String businessType, String queryField, String queryValue, String dept_id, String apply_time,String apply_time1,String cGroupTime,String cGroupTime1)
	{
		List<TradeMonitoringTreeNode> list = new ArrayList<TradeMonitoringTreeNode>();
		StringBuffer sb = new StringBuffer();

		// 合同组, 点击立项节点、查询立项下合同组、其他收付款
		if (XdssBusinessType.PROJECT.equalsIgnoreCase(businessType))
		{
			getContractGroup(nodeId, tradeType, queryField, queryValue, sb,cGroupTime,cGroupTime1);
		}
		// 采购合同、销售合同, 点击合同组节点、查询合同组下采购合同、销售合同。
		else if (XdssBusinessType.CONTRACTGROUP.equalsIgnoreCase(businessType))
		{
			getPurchaseOrSalesContract(nodeId, tradeType, queryField, queryValue, sb);
		}
		// 点击其他收付款、查询合其他收付款下收付款等信息。
		else if (XdssBusinessType.OTHERPAYMENTCOLLECT.equalsIgnoreCase(businessType))
		{
			getOtherPaymentCollect(nodeId, tradeType, queryField, queryValue, sb, dept_id, apply_time,apply_time1);
		}
		// 点击采购合同节点、查询采购合同下收货、发票校验、付款、退货、开证、到单等
		else if (XdssBusinessType.CONTRACT_PURCHASEINFO.equalsIgnoreCase(businessType))
		{
			// 取得采购合同 下单据明细。
			getPurchaseUnder(nodeId, queryField, queryValue, sb);
		}
		// 点击销售合同节点、查询销售合同下发货、开票、收款、退款、收证、出单等
		else if (XdssBusinessType.CONTRACT_SALESINFO.equalsIgnoreCase(businessType))
		{
			// 取得 销售合同下 单据信息
			getSalesUnder(nodeId, queryField, queryValue, sb);
		}

		list = getColumnTreeList(sb, businessType);

		return list;

	}

	/**
	 * 取得 销售合同下 单据信息
	 * 
	 * @param nodeId
	 * @param queryField
	 * @param queryValue
	 * @param sb
	 */
	private void getSalesUnder(String nodeId, String queryField, String queryValue, StringBuffer sb)
	{
		// 取得 销售合同下 单据信息
		// sb.append("	select * from V_TradeMonitoringSales ");
		// sb.append(" where PARENTNODEID='" + nodeId + "'");
		sb.append("select t.NODEID,t.PARENTNODEID,t.NODENAME,t.TRADETYPE,t.SDATE,t.PROCESSSTATE,t.URL,t.BUSINESSTYPE,t.BUSINESSTYPENAME,t.BUSINESSNO,t.BUSINESSNAME,t.REMARK from (");
		// --出仓、发货
		sb.append("select a.ship_id NODEID,a.contract_sales_id PARENTNODEID,'出仓单号：' || a.ship_no NODENAME,a.trade_type TRADETYPE,");
		sb.append(" to_char(to_date(a.APPLY_TIME, 'yyyy-mm-dd hh24:mi:ss'), 'yyyy-mm-dd') SDATE,'审批状态：' || c.title PROCESSSTATE,");
		sb.append("'shipController.spr?action=addShipInfoView'||'&'||'shipId='|| a.ship_id ||'&'||'tradeType='|| a.trade_type ||'&'||'operType=001' URL ,");
		sb.append("'SHIP' BUSINESSTYPE,'发货' BUSINESSTYPENAME,'出仓单号：' || a.ship_no BUSINESSNO,'凭证号：' || a.SAP_RETURN_NO BUSINESSNAME,'数量:'||c1.toquantity||' 备注:' || a.cmd REMARK");
		sb.append(" from T_SHIP_INFO a left outer join BM_RSEXAMINE_STATE c on a.examine_state=c.id " +
				  " left outer join (select sum(c.quantity) toquantity,c.ship_id from t_ship_material c group by c.ship_id) c1 on a.ship_id=c1.ship_id" +
				  " where  a.is_available = '1' and a.examine_state in ('2', '3', '5') ");
		sb.append(" and a.contract_sales_id ='" + nodeId + "'");
		if(ReceiptShipConstants.ins().isShouldHide()) sb.append(" and a.examine_state in ('2', '3')");
		// 出仓、发货
		if (QueryField.SHIP_NO.equals(queryField))
		{
			sb.append(" and a.SHIP_NO ='" + queryValue + "'");
		}
		else if (QueryField.SAP_RETURN_NO2.equals(queryField))
		{
			sb.append(" and a.SAP_RETURN_NO ='" + queryValue + "'");
		}
		// 立项
		else if (QueryField.PROJECT_NO.equals(queryField))
		{}
		else if (QueryField.PROJECT_NAME.equals(queryField))
		{}
		else if (QueryField.PROJECT_CLASSNAME.equals(queryField))
		{}
		// 合同组
		else if (QueryField.CONTRACT_GROUP_NO.equals(queryField))
		{}
		else if (QueryField.CONTRACT_GROUP_NAME.equals(queryField))
		{}
		// 销售合同
		else if (QueryField.CONTRACT_SALES_NO.equals(queryField))
		{}
		else if (QueryField.VBKD_IHREZ.equals(queryField))
		{}
		else if (QueryField.CONTRACT_SALES_NAME.equals(queryField))
		{}
		else if (QueryField.CONTRACT_SALES_CUSNAME.equals(queryField))
		{}
		// 采购合同等其他的除供应商名称
		else if (QueryField.CONTRACT_PURCHASE_PRONAME.equals(queryField))
		{}
		else
		{
			sb.append(" and 1=2");
		}

		sb.append(" union ");

		// --收证
		sb.append("select a.CREDIT_ID NODEID, e.CONTRACT_SALES_ID PARENTNODEID,'信用证号：' || a.CREDIT_NO NODENAME,a.TRADE_TYPE TRADETYPE,");
		sb.append("'开证日期：' || a.CREATE_DATE SDATE,'审批状态：' || c.title PROCESSSTATE,");
		sb.append("'creditArriveController.spr?action=modify'||'&'||'operate=view'||'&'||'tradeType='|| a.TRADE_TYPE ||'&'||'creditId='|| a.CREDIT_ID URL ,");
		sb.append("'CRER' BUSINESSTYPE,'收证' BUSINESSTYPENAME,'效期：' || a.VALID_DATE BUSINESSNO,'金额：' || a.AMOUNT BUSINESSNAME,'备注:' || a.CMD REMARK");
		sb.append(" from  T_CREDIT_REC b  left outer join  T_CREDIT_INFO a on a.CREDIT_ID = b.CREDIT_ID  left outer join BM_CREDIT_STATE c on a.CREDIT_STATE = c.id ");
		sb.append(" left outer join t_export_apply e on  a.credit_no = e.lcno");
		sb.append(" where  a.CREDIT_STATE in('15','11','3','4','5','10','12','13') and a.IS_AVAILABLE = '1'  and a.CREATE_OR_REC = '2' ");
		sb.append(" and e.CONTRACT_SALES_ID ='" + nodeId + "'");

		// 收证
		if (QueryField.CREDIT_NO.equals(queryField))
		{
			sb.append(" and a.CREDIT_NO ='" + queryValue + "'");
		}
		// 立项
		else if (QueryField.PROJECT_NO.equals(queryField))
		{}
		else if (QueryField.PROJECT_NAME.equals(queryField))
		{}
		else if (QueryField.PROJECT_CLASSNAME.equals(queryField))
		{}
		// 合同组
		else if (QueryField.CONTRACT_GROUP_NO.equals(queryField))
		{}
		else if (QueryField.CONTRACT_GROUP_NAME.equals(queryField))
		{}
		// 销售合同
		else if (QueryField.CONTRACT_SALES_NO.equals(queryField))
		{}
		else if (QueryField.VBKD_IHREZ.equals(queryField))
		{}
		else if (QueryField.CONTRACT_SALES_NAME.equals(queryField))
		{}
		else if (QueryField.CONTRACT_SALES_CUSNAME.equals(queryField))
		{}
		// 采购合同等其他的除供应商名称
		else if (QueryField.CONTRACT_PURCHASE_PRONAME.equals(queryField))
		{}
		else
		{
			sb.append(" and 1=2");
		}

		sb.append(" union ");

		// --出单
		sb.append("select a.EXPORT_BILL_EXAM_ID NODEID,c.CONTRACT_SALES_ID PARENTNODEID,'发票号：' || a.INV_NO NODENAME,c.TRADE_TYPE TRADETYPE,");
		sb.append("'出单日期：' || to_char(to_date(a.EXAM_DATE, 'yyyy-mm-dd hh24:mi:ss'), 'yyyy-mm-dd')  SDATE,'' PROCESSSTATE,");
		sb.append("'exportController.spr?action=updateExportBillExamView'||'&'||'exportBillExamId='|| a.EXPORT_BILL_EXAM_ID||'&'||'tradeType='|| c.TRADE_TYPE ||'&'||'isShow=1' URL,");
		sb.append("'EXP' BUSINESSTYPE,'出单' BUSINESSTYPENAME,'单据类型：' || a.bill_type BUSINESSNO,'信用证号：' || c.lcno BUSINESSNAME,'金额：' || a.TOTAL REMARK");
		sb.append(" from T_EXPORT_BILL_EXAM a , T_EXPORT_BILL_EXAM_D b ,T_EXPORT_APPLY c ");
		sb.append(" where  a.IS_AVAILABLE = '1' and c.IS_AVAILABLE = '1' and  a.EXPORT_BILL_EXAM_ID = b.EXPORT_BILL_EXAM_ID and  b.EXPORT_APPLY_ID = c.export_apply_id ");
		sb.append(" and c.CONTRACT_SALES_ID ='" + nodeId + "'");

		// 出单
		if (QueryField.INV_NO.equals(queryField))
		{
			sb.append(" and a.INV_NO ='" + queryValue + "'");
		}
		// 立项
		else if (QueryField.PROJECT_NO.equals(queryField))
		{}
		else if (QueryField.PROJECT_NAME.equals(queryField))
		{}
		else if (QueryField.PROJECT_CLASSNAME.equals(queryField)){}
		// 合同组
		else if (QueryField.CONTRACT_GROUP_NO.equals(queryField))
		{}
		else if (QueryField.CONTRACT_GROUP_NAME.equals(queryField))
		{}
		// 销售合同
		else if (QueryField.CONTRACT_SALES_NO.equals(queryField))
		{}
		else if (QueryField.VBKD_IHREZ.equals(queryField))
		{}
		else if (QueryField.CONTRACT_SALES_NAME.equals(queryField))
		{}
		else if (QueryField.CONTRACT_SALES_CUSNAME.equals(queryField))
		{}
		// 采购合同等其他的除供应商名称
		else if (QueryField.CONTRACT_PURCHASE_PRONAME.equals(queryField))
		{}
		else
		{
			sb.append(" and 1=2");
		}

		sb.append(" union ");

		// 20100915 LJX Modify 外围的开票数据先不计算，以SAP的开票数据为准。
		// // --外围开票
		// sb.append(" select a.BILL_APPLY_ID NODEID,b.CONTRACT_SALES_ID PARENTNODEID,'开票单号：' || a.BILL_APPLY_NO NODENAME,a.TRADE_TYPE TRADETYPE,");
		// sb.append("'开票日期：'|| a.BILL_TIME SDATE,'' PROCESSSTATE,");
		// sb.append(" case when a.bill_type='2'");
		// sb.append(" THEN");
		// sb.append(" 'billApplyController.spr?action=addAgentBillApplyView'||'&'||'billApplyId='|| a.BILL_APPLY_ID ||'&'|| 'tradeType='|| a.TRADE_TYPE ||'&'||'operType=001'");
		// sb.append(" else");
		// sb.append(" 'billApplyController.spr?action=addBillApplyView'||'&'||'billApplyId='|| a.BILL_APPLY_ID ||'&'|| 'tradeType='|| a.TRADE_TYPE ||'&'||'operType=001'");
		// sb.append(" end URL,");
		// sb.append(" 'BILL' BUSINESSTYPE,'开票' BUSINESSTYPENAME,'SAP开票凭证号：' || a.SAP_BILL_NO BUSINESSNO,'价税合计：' || b.TOTAL_MONEY  BUSINESSNAME,'备注：' || a.CMD REMARK");
		// sb.append(" from T_BILL_APPLY a ,");
		// sb.append(" (select a.CONTRACT_SALES_ID, a.BILL_APPLY_ID, sum(a.TOTAL_MONEY) TOTAL_MONEY from T_BILL_APPLY_MATERIAL a where a.CONTRACT_SALES_ID is not null group by a.CONTRACT_SALES_ID, a.BILL_APPLY_ID) b");
		// sb.append(" where  a.IS_AVAILABLE = '1' and a.EXAMINE_STATE in ('2','3','5','6','7','8') and a.BILL_APPLY_ID = b.BILL_APPLY_ID");
		// sb.append(" and b.CONTRACT_SALES_ID ='" + nodeId + "'");
		//
		// // 内贸开票
		// if (QueryField.TAX_NO.equals(queryField))
		// {
		// sb.append(" and a.TAX_NO ='" + queryValue + "'");
		// }
		// else if (QueryField.SAP_BILL_NO.equals(queryField))
		// {
		// sb.append(" and a.SAP_BILL_NO ='" + queryValue + "'");
		// }
		// // 立项
		// else if (QueryField.PROJECT_NO.equals(queryField))
		// {}
		// else if (QueryField.PROJECT_NAME.equals(queryField))
		// {}
		// // 合同组
		// else if (QueryField.CONTRACT_GROUP_NO.equals(queryField))
		// {}
		// else if (QueryField.CONTRACT_GROUP_NAME.equals(queryField))
		// {}
		// // 销售合同
		// else if (QueryField.CONTRACT_SALES_NO.equals(queryField))
		// {}
		// else if (QueryField.VBKD_IHREZ.equals(queryField))
		// {}
		// else if (QueryField.CONTRACT_SALES_NAME.equals(queryField))
		// {}
		// // 采购合同等其他的
		// else
		// {
		// sb.append(" and 1=2");
		// }
		//
		// sb.append(" union ");

		// --出口开票
		sb.append(" SELECT A.VBELN NODEID,C.CONTRACT_SALES_ID PARENTNODEID,'发票号：' || A.VBELN NODENAME,C.TRADE_TYPE TRADETYPE,'开票日期：'|| A.FKDAT SDATE,'' PROCESSSTATE,");
		sb.append("'" + UserContextHolder.getLocalUserContext().getUserContext().getWebContextPath() + "/xdss3/tradeMonitoring/vbrkController.spr?action=_view'||'&'||'vbeln=' || a.VBELN URL ,");
		sb.append(" 'EXPORTBILL' BUSINESSTYPE,'开票' BUSINESSTYPENAME,'SAP开票凭证号：' || a.VBELN BUSINESSNO,'价税合计：' || b.TOTALTAXIN  BUSINESSNAME,'备注：' || a.VTEXT REMARK");
		sb.append(" from YGETVBRK a left outer join ");
		sb.append(" (select AUBEL,VBELN, sum(TAXIN) TOTALTAXIN from YGETVBRP  where AUBEL is not null  group by AUBEL, VBELN) b on  a.VBELN = b.VBELN");
		sb.append(" left outer join T_CONTRACT_SALES_INFO c on b.AUBEL=c.SAP_ORDER_NO");
		sb.append(" where c.CONTRACT_SALES_ID ='" + nodeId + "'");

		// 出口开票
		if (QueryField.VBELN.equals(queryField))
		{
			sb.append(" and a.VBELN ='" + queryValue + "'");
		}
		// 立项
		else if (QueryField.PROJECT_NO.equals(queryField))
		{}
		else if (QueryField.PROJECT_NAME.equals(queryField))
		{}
		else if (QueryField.PROJECT_CLASSNAME.equals(queryField)){}
		// 合同组
		else if (QueryField.CONTRACT_GROUP_NO.equals(queryField))
		{}
		else if (QueryField.CONTRACT_GROUP_NAME.equals(queryField))
		{}
		// 销售合同
		else if (QueryField.CONTRACT_SALES_NO.equals(queryField))
		{}
		else if (QueryField.VBKD_IHREZ.equals(queryField))
		{}
		else if (QueryField.CONTRACT_SALES_NAME.equals(queryField))
		{}
		else if (QueryField.CONTRACT_SALES_CUSNAME.equals(queryField))
		{}
		// 采购合同等其他的除供应商名称
		else if (QueryField.CONTRACT_PURCHASE_PRONAME.equals(queryField))
		{}
		else
		{
			sb.append(" and 1=2");
		}

		// LJX 20100722 收款 收款编码,收款日期,收款单位,金额,用途,状态
		sb.append(" union");

		sb.append("	select a.COLLECTID NODEID ,c.CONTRACT_SALES_ID PARENTNODEID ,'收款单号：' || a.COLLECTNO NODENAME,c.TRADE_TYPE TRADETYPE,'收款日期：' || a.ACCOUNTDATE SDATE,'状态：' ||");
		sb.append(" case when a.BUSINESSSTATE='1' or a.BUSINESSSTATE='2' then '在审批途中' ");
		sb.append(" when a.businessstate='3' then '审批通过' ");
		sb.append("end PROCESSSTATE, ");
		sb.append(" '" + UserContextHolder.getLocalUserContext().getUserContext().getWebContextPath() + "/xdss3/collect/collectController.spr?action=_view'||'&'||'collectid=' || a.COLLECTID  URL ,");
		sb.append("'COL' BUSINESSTYPE,'收款' BUSINESSTYPENAME,'收款单位：' || d.NAME1  BUSINESSNO,'金额：' || a.ACTAMOUNT BUSINESSNAME,'用途:' || a.TEXT REMARK ");
		sb.append(" from YCOLLECT a ");
		sb.append(" left outer join YCOLLECTITEM b on a.COLLECTID = b.COLLECTID ");
		sb.append(" left outer join T_CONTRACT_SALES_INFO c on c.contract_no = b.contract_no");
		sb.append(" left outer join YGETKUNNR d on a.CUSTOMER=d.KUNNR");
		sb.append(" where a.BUSINESSSTATE in('1','2','3') and b.CONTRACT_NO is not null and b.CONTRACT_NO!=' '");
		sb.append(" and  c.CONTRACT_SALES_ID ='" + nodeId + "'");

		// 收款单号
		if (QueryField.COLLECT_NO.equals(queryField))
		{
			sb.append(" and a.COLLECTNO ='" + queryValue + "'");
		}
		// SAP订单号
		else if (QueryField.COLLECT_SAPORDERNO.equals(queryField))
		{
			sb.append(" and c.SAP_ORDER_NO ='" + queryValue + "'");
		}
		// 立项
		else if (QueryField.PROJECT_NO.equals(queryField))
		{}
		else if (QueryField.PROJECT_NAME.equals(queryField))
		{}
		else if (QueryField.PROJECT_CLASSNAME.equals(queryField)){}
		// 合同组
		else if (QueryField.CONTRACT_GROUP_NO.equals(queryField))
		{}
		else if (QueryField.CONTRACT_GROUP_NAME.equals(queryField))
		{}
		// 销售合同
		else if (QueryField.CONTRACT_SALES_NO.equals(queryField))
		{}
		else if (QueryField.VBKD_IHREZ.equals(queryField))
		{}
		else if (QueryField.CONTRACT_SALES_NAME.equals(queryField))
		{}
		else if (QueryField.CONTRACT_SALES_CUSNAME.equals(queryField))
		{}
		// 采购合同等其他的除供应商名称
		else if (QueryField.CONTRACT_PURCHASE_PRONAME.equals(queryField))
		{}
		else
		{
			sb.append(" and 1=2");
		}

		// 退款
		sb.append(" union");
		sb.append("	select a.REFUNDMENTID NODEID ,d.CONTRACT_SALES_ID PARENTNODEID ,'凭证号：' || c.VOUCHERNO NODENAME,d.TRADE_TYPE TRADETYPE,'文本：' || a.TEXT SDATE,'状态：'|| ");
		sb.append(" case when a.BUSINESSSTATE='1' or a.BUSINESSSTATE='2'  then '在审批途中' ");
		sb.append(" when a.businessstate='3' then '审批通过' ");
		sb.append("end PROCESSSTATE, ");
		sb.append(" case when a.CUSTOMER=' ' ");
		sb.append(" THEN ");
		sb.append(" '" + UserContextHolder.getLocalUserContext().getUserContext().getWebContextPath() + "/xdss3/supplierDrawback/supplierRefundmentController.spr?action=_view'||'&'||'refundmentid=' || a.REFUNDMENTID");
		sb.append(" else");
		sb.append(" '" + UserContextHolder.getLocalUserContext().getUserContext().getWebContextPath() + "/xdss3/customerDrawback/customerRefundmentController.spr?action=_view'||'&'||'refundmentid=' || a.refundmentid");
		sb.append(" end URL ,");
		sb.append("'BAK' BUSINESSTYPE,'退款' BUSINESSTYPENAME,'退款单号：' || a.REFUNDMENTNO  BUSINESSNO, '金额：' || A.refundamount BUSINESSNAME,'' REMARK");
		sb.append(" from YREFUNDMENT a ");
		sb.append(" left outer join YREFUNDMENTITEM b on a.REFUNDMENTID = b.REFUNDMENTID");
		sb.append(" left outer join YVOUCHER c on a.REFUNDMENTID = c.BUSINESSID ");
		sb.append(" left outer join T_CONTRACT_SALES_INFO d on b.CONTRACT_NO=d.CONTRACT_NO");
		sb.append(" where a.BUSINESSSTATE in('1','2','3') and b.CONTRACT_NO is not null and b.CONTRACT_NO!=' '");
		sb.append(" and d.CONTRACT_SALES_ID='" + nodeId + "'");

		// 退款单号
		if (QueryField.REFUNDMENTNO.equals(queryField))
		{
			sb.append(" and a.REFUNDMENTNO ='" + queryValue + "'");
		}
		// 退款凭证号
		else if (QueryField.REFUNDMENTVOUCHERNO.equals(queryField))
		{
			sb.append(" and c.VOUCHERNO ='" + queryValue + "'");
		}
		// 立项
		else if (QueryField.PROJECT_NO.equals(queryField))
		{}
		else if (QueryField.PROJECT_NAME.equals(queryField))
		{}
		else if (QueryField.PROJECT_CLASSNAME.equals(queryField)){}
		// 合同组
		else if (QueryField.CONTRACT_GROUP_NO.equals(queryField))
		{}
		else if (QueryField.CONTRACT_GROUP_NAME.equals(queryField))
		{}
		// 销售合同
		else if (QueryField.CONTRACT_SALES_NO.equals(queryField))
		{}
		else if (QueryField.VBKD_IHREZ.equals(queryField))
		{}
		else if (QueryField.CONTRACT_SALES_NAME.equals(queryField))
		{}
		else if (QueryField.CONTRACT_SALES_CUSNAME.equals(queryField))
		{}
		
		// 采购合同等其他的除供应商名称
		else if (QueryField.CONTRACT_PURCHASE_PRONAME.equals(queryField))
		{}
		else
		{
			sb.append(" and 1=2");
		}

		sb.append(" ) t order by  t.BUSINESSTYPE");
	}

	/**
	 * 取得采购合同 下单据明细。
	 * 
	 * @param nodeId
	 * @param queryField
	 * @param queryValue
	 * @param sb
	 */
	private void getPurchaseUnder(String nodeId, String queryField, String queryValue, StringBuffer sb)
	{
		// 采购合同 下单据明细。
		// sb.append("	select * from V_TradeMonitoringPurchase ");
		// sb.append(" where PARENTNODEID='" + nodeId + "'");
		sb.append("select t.NODEID,t.PARENTNODEID,t.NODENAME,t.TRADETYPE,t.SDATE,t.PROCESSSTATE,t.URL,t.BUSINESSTYPE,t.BUSINESSTYPENAME,t.BUSINESSNO,t.BUSINESSNAME,t.REMARK from (");

		// --收货
		sb.append("select a.receipt_id NODEID,a.contract_purchase_id PARENTNODEID,'进仓单号：' || a.receipt_no NODENAME,a.trade_type TRADETYPE,");
		sb.append(" a.APPLY_TIME SDATE,'审批状态：' || b.title PROCESSSTATE,");
		sb.append("'receiptsController.spr?action=addReceiptView'||'&'||'receiptId=' || a.receipt_id ||'&'||'tradeType=' || a.trade_type ||'&'||'operType=001' URL,");
		sb.append("'REC' BUSINESSTYPE,'收货' BUSINESSTYPENAME,'进仓单号：' || a.RECEIPT_NO BUSINESSNO,'凭证号：' || a.SAP_RETURN_NO BUSINESSNAME,'数量:'|| c1.toquantity||'　备注:'||a.memo REMARK");
		sb.append(" from T_RECEIPT_INFO a  left outer join BM_RSEXAMINE_STATE b on a.EXAMINE_STATE=b.id " +
				  " left outer join (select sum(c.quantity) toquantity,c.receipt_id from t_receipt_material c group by c.receipt_id) c1 on a.receipt_id=c1.receipt_id"+
				  " where  a.is_available = '1' and a.examine_state in ('2', '3', '5') ");
		sb.append(" and a.contract_purchase_id ='" + nodeId + "'");
		if(ReceiptShipConstants.ins().isShouldHide()) sb.append(" and a.examine_state in ('2', '3')");
		// 收货
		if (QueryField.RECEIPT_NO.equals(queryField))
		{
			sb.append(" and a.RECEIPT_NO ='" + queryValue + "'");
		}
		else if (QueryField.SAP_RETURN_NO1.equals(queryField))
		{
			sb.append(" and a.SAP_RETURN_NO ='" + queryValue + "'");
		}
		// 立项
		else if (QueryField.PROJECT_NO.equals(queryField))
		{}
		else if (QueryField.PROJECT_NAME.equals(queryField))
		{}
		else if (QueryField.PROJECT_CLASSNAME.equals(queryField)){}
		// 合同组
		else if (QueryField.CONTRACT_GROUP_NO.equals(queryField))
		{}
		else if (QueryField.CONTRACT_GROUP_NAME.equals(queryField))
		{}
		// 采购合同
		else if (QueryField.CONTRACT_PURCHASE_NO.equals(queryField))
		{}
		else if (QueryField.EKKO_UNSEZ.equals(queryField))
		{}
		else if (QueryField.CONTRACT_PURCHASE_NAME.equals(queryField))
		{}
		else if (QueryField.CONTRACT_PURCHASE_PRONAME.equals(queryField))
		{}

		// 销售合同等其他的除客户名称
		else if (QueryField.CONTRACT_SALES_CUSNAME.equals(queryField))
		{}
		else
		{
			sb.append(" and 1=2");
		}

		sb.append(" union ");

		// --开证
		sb.append("select a.CREDIT_ID NODEID,b.CONTRACT_PURCHASE_ID PARENTNODEID ,'信用证号：' || a.CREDIT_NO NODENAME,a.TRADE_TYPE TRADETYPE,");
		sb.append("'开证日期：' || a.CREATE_DATE SDATE,'审批状态：' || c.title PROCESSSTATE,");
		sb.append("'creditEntryController.spr?action=modify'||'&'||'operate=view'||'&'||'tradeType='|| a.TRADE_TYPE  ||'&'||'creditId=' || a.CREDIT_ID URL,");
		sb.append("'CREC' BUSINESSTYPE,'开证' BUSINESSTYPENAME,'有效期：' || a.VALID_DATE BUSINESSNO,'金额：' || a.AMOUNT BUSINESSNAME,'备注:' || a.CMD REMARK");
		sb.append(" from T_CREDIT_CREATE b  left outer join  T_CREDIT_INFO a on a.CREDIT_ID = b.CREDIT_ID  left outer join BM_CREDIT_STATE c on a.CREDIT_STATE = c.ID  where  a.IS_AVAILABLE = '1' and a.CREDIT_STATE in('15','11','3','4','5','10','12','8') and a.CREATE_OR_REC = '1'");
		sb.append(" and b.CONTRACT_PURCHASE_ID ='" + nodeId + "'");
		// 开证
		if (QueryField.CREDIT_NO.equals(queryField))
		{
			sb.append(" and a.CREDIT_NO ='" + queryValue + "'");
		}
		// 立项
		else if (QueryField.PROJECT_NO.equals(queryField))
		{}
		else if (QueryField.PROJECT_NAME.equals(queryField))
		{}
		else if (QueryField.PROJECT_CLASSNAME.equals(queryField)){}
		// 合同组
		else if (QueryField.CONTRACT_GROUP_NO.equals(queryField))
		{}
		else if (QueryField.CONTRACT_GROUP_NAME.equals(queryField))
		{}
		// 采购合同
		else if (QueryField.CONTRACT_PURCHASE_NO.equals(queryField))
		{}
		else if (QueryField.EKKO_UNSEZ.equals(queryField))
		{}
		else if (QueryField.CONTRACT_PURCHASE_NAME.equals(queryField))
		{}
		else if (QueryField.CONTRACT_PURCHASE_PRONAME.equals(queryField))
		{}
		
		// 销售合同等其他的除客户名称
		else if (QueryField.CONTRACT_SALES_CUSNAME.equals(queryField))
		{}
		else
		{
			sb.append(" and 1=2");
		}

		sb.append(" union ");

		// --到单
		sb.append("select a.PICK_LIST_ID NODEID,a.CONTRACT_PURCHASE_ID PARENTNODEID,'到单号：' || a.pick_list_no NODENAME,a.TRADE_TYPE TRADETYPE,");
		sb.append(" to_char(to_date(a.APPLY_TIME, 'yyyy-mm-dd hh24:mi:ss'), 'yyyy-mm-dd') SDATE,'审批状态：' || c.TITLE PROCESSSTATE,");
		sb.append("'pickListInfoController.spr?action=viewPickListInfo'||'&'||'pickListId=' || a.pick_list_id ||'&'||'pickListType='|| a.TRADE_TYPE URL,");
		sb.append("'PIC' BUSINESSTYPE,'到单' BUSINESSTYPENAME,'单据类型：' || a.trade_type BUSINESSNO,'金额：'|| (select sum(total_value) from T_PAYMENT_IMPORTS_MATERIAL b where a.PICK_LIST_ID = b.PICK_LIST_ID)  BUSINESSNAME,'备注:' || a.CMD REMARK");
		sb.append(" from T_PICK_LIST_INFO a left outer join BM_EXAMINE_STATE c on a.EXAMINE_STATE=c.ID where  a.IS_AVAILABLE = '1' and a.EXAMINE_STATE in ('2', '3', '5', '6', '7', '8')");
		sb.append(" and a.contract_purchase_id ='" + nodeId + "'");
		// 到单
		if (QueryField.PICK_LIST_NO.equals(queryField))
		{
			sb.append(" and a.PICK_LIST_NO ='" + queryValue + "'");
		}
		// 立项
		else if (QueryField.PROJECT_NO.equals(queryField))
		{}
		else if (QueryField.PROJECT_NAME.equals(queryField))
		{}
		else if (QueryField.PROJECT_CLASSNAME.equals(queryField)){}
		// 合同组
		else if (QueryField.CONTRACT_GROUP_NO.equals(queryField))
		{}
		else if (QueryField.CONTRACT_GROUP_NAME.equals(queryField))
		{}
		// 采购合同
		else if (QueryField.CONTRACT_PURCHASE_NO.equals(queryField))
		{}
		else if (QueryField.EKKO_UNSEZ.equals(queryField))
		{}
		else if (QueryField.CONTRACT_PURCHASE_NAME.equals(queryField))
		{}
		else if (QueryField.CONTRACT_PURCHASE_PRONAME.equals(queryField))
		{}

		// 销售合同等其他的除客户名称
		else if (QueryField.CONTRACT_SALES_CUSNAME.equals(queryField))
		{}
		else
		{
			sb.append(" and 1=2");
		}

		sb.append(" union ");

		// --发票校验
		sb.append("select a.MAINCODE NODEID,b.CONTRACT_PURCHASE_ID PARENTNODEID,'凭证号：' || a.BELNR NODENAME,b.TRADE_TYPE TRADETYPE,'文本：' || a.BKTXT  SDATE,'' PROCESSSTATE,");
		sb.append("'" + UserContextHolder.getLocalUserContext().getUserContext().getWebContextPath() + "/xdss3/tradeMonitoring/invoiceVerifyController.spr?action=_view'||'&'||'maincode=' || a.MAINCODE URL ,");
		sb.append("'IV' BUSINESSTYPE,'发票校验' BUSINESSTYPENAME,'价税合计：' || a.TAXIN_DMBTR BUSINESSNO,'' BUSINESSNAME,'' REMARK");
		sb.append(" from YGETBELNR a left outer join T_CONTRACT_PURCHASE_INFO b on a.EBELN=b.SAP_ORDER_NO");
		sb.append(" where b.CONTRACT_PURCHASE_ID ='" + nodeId + "'");

		// 发票校验
		if (QueryField.BELNR.equals(queryField))
		{
			sb.append(" and a.BELNR ='" + queryValue + "'");
		}
		// 立项
		else if (QueryField.PROJECT_NO.equals(queryField))
		{}
		else if (QueryField.PROJECT_NAME.equals(queryField))
		{}
		else if (QueryField.PROJECT_CLASSNAME.equals(queryField)){}
		// 合同组
		else if (QueryField.CONTRACT_GROUP_NO.equals(queryField))
		{}
		else if (QueryField.CONTRACT_GROUP_NAME.equals(queryField))
		{}
		// 采购合同
		else if (QueryField.CONTRACT_PURCHASE_NO.equals(queryField))
		{}
		else if (QueryField.EKKO_UNSEZ.equals(queryField))
		{}
		else if (QueryField.CONTRACT_PURCHASE_NAME.equals(queryField))
		{}
		else if (QueryField.CONTRACT_PURCHASE_PRONAME.equals(queryField))
		{}
		// 销售合同等其他的除客户名称
		else if (QueryField.CONTRACT_SALES_CUSNAME.equals(queryField))
		{}
		else
		{
			sb.append(" and 1=2");
		}

		sb.append(" union ");

		// LJX 20100722 付款
		sb.append("	select a.PAYMENTID NODEID ,c.CONTRACT_PURCHASE_ID PARENTNODEID ,'付款单号：' || a.PAYMENTNO NODENAME,c.TRADE_TYPE TRADETYPE,'凭证日期：' || a.voucherdate SDATE,'状态：' ||");
		sb.append(" case when a.BUSINESSSTATE='1' or a.BUSINESSSTATE='2' or a.BUSINESSSTATE='3' then '在审批途中' ");
		sb.append(" when a.businessstate='4' then '审批通过' ");
		sb.append(" when a.businessstate='6' then '特批审批中' ");
		sb.append(" when a.businessstate='7' then '特批审批通过' ");
		sb.append("end PROCESSSTATE, ");
		sb.append(" case when a.TRADE_TYPE='01' ");
		sb.append(" THEN ");
		sb.append(" '" + UserContextHolder.getLocalUserContext().getUserContext().getWebContextPath() + "/xdss3/payment/homePaymentController.spr?action=_view'||'&'||'paymentid=' || a.PAYMENTID");
		sb.append(" else");
		sb.append(" '" + UserContextHolder.getLocalUserContext().getUserContext().getWebContextPath() + "/xdss3/payment/importPaymentController.spr?action=_view'||'&'||'paymentid=' || a.PAYMENTID");
		sb.append(" end URL ,");
		sb.append("'PAY' BUSINESSTYPE,'付款' BUSINESSTYPENAME,'付款单位：' || d.NAME1  BUSINESSNO,'金额：' || a.FACTAMOUNT BUSINESSNAME,'用途:' || a.TEXT REMARK");
		sb.append(" from YPAYMENT a ");
		sb.append(" left outer join YPAYMENTITEM b on a.PAYMENTID = b.PAYMENTID ");
		sb.append(" left outer join T_CONTRACT_PURCHASE_INFO c on c.CONTRACT_NO = b.CONTRACT_NO");
		sb.append(" left outer join YGETLIFNR d on a.SUPPLIER=d.LIFNR");
		sb.append(" where a.BUSINESSSTATE in(" + BusinessState.ALL_ONROAD + "," + BusinessState.ALL_SUBMITPASS + ") and b.CONTRACT_NO is not null and b.CONTRACT_NO!=' '");
		sb.append(" and c.CONTRACT_PURCHASE_ID ='" + nodeId + "'");

		// 付款单号
		if (QueryField.PAYMENT_NO.equals(queryField))
		{
			sb.append(" and a.PAYMENTNO ='" + queryValue + "'");
		}
		// SAP凭证号
		else if (QueryField.PAYMENT_SAPORDERNO.equals(queryField))
		{
			sb.append(" and c.SAP_ORDER_NO ='" + queryValue + "'");
		}
		// 立项
		else if (QueryField.PROJECT_NO.equals(queryField))
		{}
		else if (QueryField.PROJECT_NAME.equals(queryField))
		{}
		else if (QueryField.PROJECT_CLASSNAME.equals(queryField)){}
		// 合同组
		else if (QueryField.CONTRACT_GROUP_NO.equals(queryField))
		{}
		else if (QueryField.CONTRACT_GROUP_NAME.equals(queryField))
		{}
		// 采购合同
		else if (QueryField.CONTRACT_PURCHASE_NO.equals(queryField))
		{}
		else if (QueryField.EKKO_UNSEZ.equals(queryField))
		{}
		else if (QueryField.CONTRACT_PURCHASE_NAME.equals(queryField))
		{}
		else if (QueryField.CONTRACT_PURCHASE_PRONAME.equals(queryField))
		{}
		// 销售合同等其他的除客户名称
		else if (QueryField.CONTRACT_SALES_CUSNAME.equals(queryField))
		{}
		else
		{
			sb.append(" and 1=2");
		}

		// 退款
		sb.append(" union");
		sb.append("	select a.REFUNDMENTID NODEID ,d.CONTRACT_PURCHASE_ID PARENTNODEID ,'凭证号：' || c.VOUCHERNO NODENAME,d.TRADE_TYPE TRADETYPE,'文本：' || a.TEXT SDATE,'状态：'|| ");
		sb.append(" case when a.BUSINESSSTATE='1' or a.BUSINESSSTATE='2' then '在审批途中' ");
		sb.append(" when a.businessstate='3' then '审批通过' ");
		sb.append("end PROCESSSTATE, ");
		sb.append(" case when a.CUSTOMER=' ' ");
		sb.append(" THEN ");
		sb.append(" '" + UserContextHolder.getLocalUserContext().getUserContext().getWebContextPath() + "/xdss3/supplierDrawback/supplierRefundmentController.spr?action=_view'||'&'||'refundmentid=' || a.REFUNDMENTID");
		sb.append(" else");
		sb.append(" '" + UserContextHolder.getLocalUserContext().getUserContext().getWebContextPath() + "/xdss3/customerDrawback/customerRefundmentController.spr?action=_view'||'&'||'refundmentid=' || a.refundmentid");
		sb.append(" end URL ,");
		sb.append("'BAK' BUSINESSTYPE,'退款' BUSINESSTYPENAME,'退款单号：' || a.REFUNDMENTNO  BUSINESSNO, '金额：' || A.refundamount BUSINESSNAME,'' REMARK");
		sb.append(" from YREFUNDMENT a ");
		sb.append(" left outer join YREFUNDMENTITEM b on a.REFUNDMENTID = b.REFUNDMENTID");
		sb.append(" left outer join YVOUCHER c on a.REFUNDMENTID = c.BUSINESSID ");
		sb.append(" left outer join T_CONTRACT_PURCHASE_INFO d on b.CONTRACT_NO=d.CONTRACT_NO");
		sb.append(" where a.BUSINESSSTATE in('1','2','3') and b.CONTRACT_NO is not null and  b.CONTRACT_NO!=' '");
		sb.append(" and d.CONTRACT_PURCHASE_ID='" + nodeId + "'");

		// 退款单号
		if (QueryField.REFUNDMENTNO.equals(queryField))
		{
			sb.append(" and a.REFUNDMENTNO ='" + queryValue + "'");
		}
		// 退款凭证号
		else if (QueryField.REFUNDMENTVOUCHERNO.equals(queryField))
		{
			sb.append(" and c.VOUCHERNO ='" + queryValue + "'");
		}
		// 立项
		else if (QueryField.PROJECT_NO.equals(queryField))
		{}
		else if (QueryField.PROJECT_NAME.equals(queryField))
		{}
		else if (QueryField.PROJECT_CLASSNAME.equals(queryField)){}
		// 合同组
		else if (QueryField.CONTRACT_GROUP_NO.equals(queryField))
		{}
		else if (QueryField.CONTRACT_GROUP_NAME.equals(queryField))
		{}
		// 采购合同
		else if (QueryField.CONTRACT_PURCHASE_NO.equals(queryField))
		{}
		else if (QueryField.EKKO_UNSEZ.equals(queryField))
		{}
		else if (QueryField.CONTRACT_PURCHASE_NAME.equals(queryField))
		{}
		else if (QueryField.CONTRACT_PURCHASE_PRONAME.equals(queryField))
		{}
		// 销售合同等其他的除客户名称
		else if (QueryField.CONTRACT_SALES_CUSNAME.equals(queryField))
		{}
		else
		{
			sb.append(" and 1=2");
		}

		sb.append(" ) t order by  t.BUSINESSTYPE");
	}

	/**
	 * 取得立项下，合同组信息。<BR>
	 * 合同组, 点击立项节点、查询立项下合同组
	 * 
	 * @param tradeType
	 * @param queryField
	 * @param queryValue
	 * @param sb
	 */
	private void getContractGroup(String nodeId, String tradeType, String queryField, String queryValue, StringBuffer sb,String cGroupTime,String cGroupTime1)
	{
		// LJX 20100927 Add 备注： 合同组剩余 为 合同组下 所有进仓数量-所有出仓数量
		sb.append("select t.NODEID,t.PARENTNODEID,t.NODENAME,t.TRADETYPE,t.SDATE,t.PROCESSSTATE,t.URL,t.BUSINESSTYPE,t.BUSINESSTYPENAME,t.BUSINESSNO,t.BUSINESSNAME,t.REMARK from (");

		sb.append("SELECT a.CONTRACT_GROUP_ID NODEID,a.PROJECT_ID PARENTNODEID,a.CONTRACT_GROUP_NO NODENAME,a.TRADE_TYPE TRADETYPE,'' URL,'' SDATE,'' PROCESSSTATE,");
		sb.append("'CG' BUSINESSTYPE,'合同组' BUSINESSTYPENAME,a.CONTRACT_GROUP_NO BUSINESSNO, a.CONTRACT_GROUP_NAME BUSINESSNAME,");
		sb.append("'剩余'||((select sum(quantity) from t_receipt_material  where  receipt_id in " + "(select receipt_id from t_receipt_info where SUBSTRB(contract_no,0,9)= a.CONTRACT_GROUP_NO and is_available='1' and " + "(examine_state='2' or examine_state='3' or examine_state='5')))-NVL((select sum(quantity) from t_ship_material  where  " + "ship_id in(select ship_id from t_ship_info where SUBSTRB(sales_no,0,9)= a.CONTRACT_GROUP_NO and is_available='1' and " + "(examine_state='2' or examine_state='3' or examine_state='5'))),0)) as REMARK");
		sb.append(" FROM T_CONTRACT_GROUP a WHERE  a.IS_AVAILABLE = '1' AND a.TRADE_TYPE='" + tradeType + "' and a.PROJECT_ID='" + nodeId + "'");

		if(StringUtils.isNotBlank(cGroupTime)){
			sb.append(" and a.create_time >='" + cGroupTime + " 00:00:00'");
		}
		if(StringUtils.isNotBlank(cGroupTime1)){
			sb.append(" and a.create_time <='" + cGroupTime1 + " 24:00:00'");
		}
		// 根据查询条件，过滤树节点
		if (StringUtils.isNotBlank(queryValue) && StringUtils.isNotBlank(queryField))
		{
			// 合同组
			if (QueryField.CONTRACT_GROUP_NO.equals(queryField))
			{
				sb.append(" and a.CONTRACT_GROUP_NO ='" + queryValue + "'");
			}
			else if (QueryField.CONTRACT_GROUP_NAME.equals(queryField))
			{
				sb.append(" and a.CONTRACT_GROUP_NAME like '%" + queryValue + "%'");
			}
			// 采购合同
			else if (QueryField.CONTRACT_PURCHASE_NO.equals(queryField))
			{
				sb.append(" and a.CONTRACT_GROUP_ID in (select a.CONTRACT_GROUP_ID from T_CONTRACT_PURCHASE_INFO a where a.IS_AVAILABLE = '1' and a.ORDER_STATE in ('10','11','2','3','5','6','7','8','9') and a.CONTRACT_NO='" + queryValue + "')");
			}
			else if (QueryField.EKKO_UNSEZ.equals(queryField))
			{
				sb.append(" and a.CONTRACT_GROUP_ID in (select a.CONTRACT_GROUP_ID from T_CONTRACT_PURCHASE_INFO a where a.IS_AVAILABLE = '1' and a.ORDER_STATE in ('10','11','2','3','5','6','7','8','9') and a.EKKO_UNSEZ='" + queryValue + "')");
			}
			else if (QueryField.CONTRACT_PURCHASE_NAME.equals(queryField))
			{
				sb.append(" and a.CONTRACT_GROUP_ID in(select distinct a.CONTRACT_GROUP_ID from T_CONTRACT_PURCHASE_INFO a where a.IS_AVAILABLE = '1' and a.ORDER_STATE in ('10','11','2','3','5','6','7','8','9') and a.CONTRACT_NAME like '%" + queryValue + "%')");
			}
			else if (QueryField.CONTRACT_PURCHASE_PRONAME.equals(queryField))
			{
				sb.append(" and exists (select '' from T_CONTRACT_PURCHASE_INFO a1 where a1.IS_AVAILABLE = '1'");
				sb.append(" and a1.ORDER_STATE in ('10','11','2','3','5','6','7','8','9') and a.CONTRACT_GROUP_ID=a1.CONTRACT_GROUP_ID and UPPER(a1.ekko_lifnr_name) like '%" + queryValue.toUpperCase() + "%')");
			} 
			// 销售合同
			else if (QueryField.CONTRACT_SALES_NO.equals(queryField))
			{
				sb.append(" and a.CONTRACT_GROUP_ID =(select a.CONTRACT_GROUP_ID from T_CONTRACT_SALES_INFO a where a.IS_AVAILABLE = '1' and a.ORDER_STATE in ('10','11','2','3','5','6','7','8','9') and a.CONTRACT_NO='" + queryValue + "')");
			}
			else if (QueryField.VBKD_IHREZ.equals(queryField))
			{
				sb.append(" and a.CONTRACT_GROUP_ID IN (select a.CONTRACT_GROUP_ID from T_CONTRACT_SALES_INFO a where a.IS_AVAILABLE = '1' and a.ORDER_STATE in ('10','11','2','3','5','6','7','8','9') and a.VBKD_IHREZ='" + queryValue + "')");
			}
			else if (QueryField.CONTRACT_SALES_NAME.equals(queryField))
			{
				sb.append(" and a.CONTRACT_GROUP_ID in(select distinct a.CONTRACT_GROUP_ID from T_CONTRACT_SALES_INFO a where a.IS_AVAILABLE = '1' and a.ORDER_STATE in ('10','11','2','3','5','6','7','8','9') and a.CONTRACT_NAME like '%" + queryValue + "%')");
			}
			else if (QueryField.CONTRACT_SALES_CUSNAME.equals(queryField))
			{
				sb.append(" and exists (select '' from T_CONTRACT_SALES_INFO a1 where a1.IS_AVAILABLE = '1'");
				sb.append(" and a1.ORDER_STATE in ('10','11','2','3','5','6','7','8','9') and a.CONTRACT_GROUP_ID=a1.CONTRACT_GROUP_ID and UPPER(a1.KUAGV_KUNNR_NAME) like '%" + queryValue.toUpperCase() + "%')");
			}
			// LJX 20100722 Add 加入收付款等。
			// 付款
			else if (QueryField.PAYMENT_NO.equals(queryField))
			{
				sb.append(" and a.CONTRACT_GROUP_ID in(select c.CONTRACT_GROUP_ID from YPAYMENT a left outer join YPAYMENTITEM b on a.PAYMENTID=b.PAYMENTID left outer join T_CONTRACT_PURCHASE_INFO c on b.CONTRACT_NO=c.CONTRACT_NO ");
				sb.append(" where b.PAYMENTITEMID is not null and c.IS_AVAILABLE = '1' and a.PAYMENTNO='" + queryValue + "')");
			}
			else if (QueryField.PAYMENT_SAPORDERNO.equals(queryField))
			{
				sb.append("  and a.CONTRACT_GROUP_ID in(select c.CONTRACT_GROUP_ID from YPAYMENT a left outer join YPAYMENTITEM b on a.PAYMENTID=b.PAYMENTID left outer join T_CONTRACT_PURCHASE_INFO c on b.CONTRACT_NO=c.CONTRACT_NO ");
				sb.append(" where b.PAYMENTITEMID is not null and c.IS_AVAILABLE = '1' and c.SAP_ORDER_NO='" + queryValue + "')");
			}
			// 收款
			else if (QueryField.COLLECT_NO.equals(queryField))
			{
				sb.append(" and a.CONTRACT_GROUP_ID in(select c.CONTRACT_GROUP_ID from YCOLLECT a left outer join YCOLLECTITEM b on a.COLLECTID=b.COLLECTID left outer join T_CONTRACT_SALES_INFO c on b.CONTRACT_NO=c.CONTRACT_NO  ");
				sb.append(" where b.COLLECTITEMID is not null and c.IS_AVAILABLE = '1' and a.COLLECTNO='" + queryValue + "')");
			}
			else if (QueryField.COLLECT_SAPORDERNO.equals(queryField))
			{
				sb.append(" and a.CONTRACT_GROUP_ID in(select c.CONTRACT_GROUP_ID from YCOLLECT a left outer join YCOLLECTITEM b on a.COLLECTID=b.COLLECTID left outer join T_CONTRACT_SALES_INFO c on b.CONTRACT_NO=c.CONTRACT_NO ");
				sb.append(" where b.COLLECTITEMID is not null and c.IS_AVAILABLE = '1' and c.SAP_ORDER_NO='" + queryValue + "')");
			}
			// 退款
			else if (QueryField.REFUNDMENTNO.equals(queryField))
			{
				sb.append(" and (a.CONTRACT_GROUP_ID in(select c.CONTRACT_GROUP_ID from YREFUNDMENT a left outer join YREFUNDMENTITEM b on a.REFUNDMENTID=b.REFUNDMENTID left outer join T_CONTRACT_SALES_INFO c on b.CONTRACT_NO=c.CONTRACT_NO ");
				sb.append(" where c.IS_AVAILABLE = '1' and a.CUSTOMER is not null and a.CUSTOMER!=' ' and a.REFUNDMENTNO='" + queryValue + "')");
				sb.append(" or a.CONTRACT_GROUP_ID in(select c.CONTRACT_GROUP_ID from YREFUNDMENT a left outer join YREFUNDMENTITEM b on a.REFUNDMENTID=b.REFUNDMENTID left outer join T_CONTRACT_PURCHASE_INFO c on b.CONTRACT_NO=c.CONTRACT_NO ");
				sb.append(" where c.IS_AVAILABLE = '1' and a.SUPPLIER is not null and a.SUPPLIER!=' ' and a.REFUNDMENTNO='" + queryValue + "')");
				sb.append(" )");
			}
			else if (QueryField.REFUNDMENTVOUCHERNO.equals(queryField))
			{
				sb.append(" and a.CONTRACT_GROUP_ID in(select c.CONTRACT_GROUP_ID from YREFUNDMENT a left outer join YREFUNDMENTITEM b on a.REFUNDMENTID=b.REFUNDMENTID ");
				sb.append(" left outer join T_CONTRACT_SALES_INFO c on b.CONTRACT_NO=c.CONTRACT_NO ");
				sb.append(" left outer join YVOUCHER d on a.REFUNDMENTID = d.BUSINESSID ");
				sb.append(" where d.voucherNO is not null and a.CUSTOMER is not null and a.CUSTOMER!=' ' and c.IS_AVAILABLE = '1' and d.VOUCHERNO='" + queryValue + "')");
				sb.append(" or a.CONTRACT_GROUP_ID in(select c.CONTRACT_GROUP_ID from YREFUNDMENT a left outer join YREFUNDMENTITEM b on a.REFUNDMENTID=b.REFUNDMENTID ");
				sb.append(" left outer join T_CONTRACT_SALES_INFO c on b.CONTRACT_NO=c.CONTRACT_NO ");
				sb.append(" left outer join YVOUCHER d on a.REFUNDMENTID = d.BUSINESSID ");
				sb.append(" where d.voucherNO is not null and a.SUPPLIER is not null and a.SUPPLIER!=' ' and c.IS_AVAILABLE = '1' and d.VOUCHERNO='" + queryValue + "')");
				sb.append(" )");
			}

			// ////////销售合同、采购合同下 单据信息:
			// @@@@销售合同下,出仓、出单、收证、开票
			// 出仓、发货
			else if (QueryField.SHIP_NO.equals(queryField))
			{
				sb.append(" and a.CONTRACT_GROUP_ID = (select CONTRACT_GROUP_ID from T_CONTRACT_SALES_INFO where CONTRACT_SALES_ID in(");
				sb.append(" select a.CONTRACT_SALES_ID from T_SHIP_INFO a where a.IS_AVAILABLE = '1' and a.EXAMINE_STATE in ('2', '3', '5','6', '7', '8')");
				sb.append(" and  a.SHIP_NO='" + queryValue + "'))");
			}
			else if (QueryField.SAP_RETURN_NO2.equals(queryField))
			{
				sb.append(" and a.CONTRACT_GROUP_ID = (select CONTRACT_GROUP_ID from T_CONTRACT_SALES_INFO where CONTRACT_SALES_ID in(");
				sb.append(" select a.CONTRACT_SALES_ID from T_SHIP_INFO a where a.IS_AVAILABLE = '1' and a.EXAMINE_STATE in ('2', '3', '5','6', '7', '8')");
				sb.append(" and  a.SAP_RETURN_NO='" + queryValue + "'))");
			}
			// 出单
			else if (QueryField.INV_NO.equals(queryField))
			{
				sb.append(" and a.CONTRACT_GROUP_ID =(select CONTRACT_GROUP_ID from T_CONTRACT_SALES_INFO where CONTRACT_SALES_ID in(");
				sb.append(" select c.CONTRACT_SALES_ID from T_EXPORT_BILL_EXAM a, T_EXPORT_BILL_EXAM_D b, T_EXPORT_APPLY c ");
				sb.append(" where  a.IS_AVAILABLE = '1' and c.IS_AVAILABLE = '1' and a.EXPORT_BILL_EXAM_ID = b.EXPORT_BILL_EXAM_ID");
				sb.append(" and b.EXPORT_APPLY_ID = c.EXPORT_APPLY_ID and a.INV_NO='" + queryValue + "'))");
			}
			// 信用证开证、到证(收证)
			else if (QueryField.CREDIT_NO.equals(queryField))
			{
				// 开证
				sb.append(" and (a.CONTRACT_GROUP_ID =(select d.CONTRACT_GROUP_ID from T_CREDIT_CREATE b  left outer join  T_CREDIT_INFO a on a.CREDIT_ID = b.CREDIT_ID ");
				sb.append(" left outer join T_CONTRACT_PURCHASE_INFO c on b.CONTRACT_PURCHASE_ID=c.CONTRACT_PURCHASE_ID left outer join T_CONTRACT_GROUP d on c.CONTRACT_GROUP_ID = d.CONTRACT_GROUP_ID  ");
				sb.append(" where a.IS_AVAILABLE = '1'  and a.create_or_rec = '1'  and a.CREDIT_STATE in('15','11','3','4','5','10','12','13') and a.CREDIT_NO='" + queryValue + "')");
				// 收证
				sb.append(" or a.CONTRACT_GROUP_ID in ( select a.CONTRACT_GROUP_ID from T_CONTRACT_SALES_INFO a");
				sb.append(" where a.CONTRACT_SALES_ID in(select d.CONTRACT_SALES_ID from T_EXPORT_APPLY d where d.LCNO='" + queryValue + "'))");
				sb.append(" )");
			}
			// 内贸开票
			else if (QueryField.TAX_NO.equals(queryField))
			{
				sb.append(" and a.CONTRACT_GROUP_ID in(select CONTRACT_GROUP_ID from T_CONTRACT_SALES_INFO where CONTRACT_SALES_ID in(");
				sb.append(" select distinct a.CONTRACT_SALES_ID from T_BILL_APPLY_MATERIAL a where a.BILL_APPLY_ID is not null and a.BILL_APPLY_ID in");
				sb.append(" (select b.BILL_APPLY_ID from T_BILL_APPLY b where b.IS_AVAILABLE = '1' and b.EXAMINE_STATE in ('2', '3', '5', '6', '7', '8') and b.TAX_NO = '" + queryValue + "')))");
			}
			else if (QueryField.SAP_BILL_NO.equals(queryField))
			{
				sb.append(" and a.CONTRACT_GROUP_ID in(select CONTRACT_GROUP_ID from T_CONTRACT_SALES_INFO where CONTRACT_SALES_ID in(");
				sb.append(" select distinct a.CONTRACT_SALES_ID from T_BILL_APPLY_MATERIAL a where a.BILL_APPLY_ID is not null and a.BILL_APPLY_ID in");
				sb.append(" (select b.BILL_APPLY_ID from T_BILL_APPLY b where b.IS_AVAILABLE = '1' and b.EXAMINE_STATE in ('2', '3', '5', '6', '7', '8') and b.SAP_BILL_NO = '" + queryValue + "')))");
			}
			// 出口开票
			else if (QueryField.VBELN.equals(queryField))
			{
				sb.append(" and a.CONTRACT_GROUP_ID in(select a.CONTRACT_GROUP_ID from T_CONTRACT_SALES_INFO a where a.sap_order_no in(SELECT AUBEL from YGETVBRP WHERE VBELN='" + queryValue + "'))");
			}
			// @@@@采购合同下,进仓(收货)、到单、开证
			// 进仓单(收货)
			// 进仓单(收货)
			else if (QueryField.RECEIPT_NO.equals(queryField))
			{
				sb.append(" and a.CONTRACT_GROUP_NO =(select a.CONTRACT_GROUP_NO from T_RECEIPT_INFO a where a.IS_AVAILABLE = '1' ");
				sb.append(" and a.EXAMINE_STATE in ('2', '3', '5','6', '7', '8') and a.RECEIPT_NO='" + queryValue + "')");
			}
			else if (QueryField.SAP_RETURN_NO1.equals(queryField))
			{
				sb.append(" and a.CONTRACT_GROUP_NO =(select a.CONTRACT_GROUP_NO from T_RECEIPT_INFO a where a.IS_AVAILABLE = '1'");
				sb.append("and a.EXAMINE_STATE in ('2', '3', '5','6', '7', '8') and a.SAP_RETURN_NO='" + queryValue + "')");
			}
			// 到单
			else if (QueryField.PICK_LIST_NO.equals(queryField))
			{
				sb.append(" and a.CONTRACT_GROUP_ID in(select a.CONTRACT_GROUP_ID from T_CONTRACT_PURCHASE_INFO a");
				sb.append(" where a.CONTRACT_PURCHASE_ID in(select CONTRACT_PURCHASE_ID from T_PICK_LIST_INFO a");
				sb.append(" where a.IS_AVAILABLE = '1' and a.EXAMINE_STATE in ('2', '3', '5', '6', '7', '8') and a.PICK_LIST_NO='" + queryValue + "'))");
			}
			// 发票校验
			else if (QueryField.BELNR.equals(queryField))
			{
				sb.append(" and a.CONTRACT_GROUP_ID in(select b.CONTRACT_GROUP_ID from YGETBELNR a left outer join T_CONTRACT_PURCHASE_INFO b on a.EBELN=b.SAP_ORDER_NO where a.BELNR='" + queryValue + "')");
			}

		}

		sb.append(" union ");
		sb.append(" select '" + nodeId + "' NODEID,'" + nodeId + "' PARENTNODEID,'其他收付款' NODENAME," + Integer.parseInt(tradeType) + " TRADETYPE,'' URL,''  SDATE,'' PROCESSSTATE,");
		sb.append(" 'OTH' BUSINESSTYPE,'其他收付款' BUSINESSTYPENAME,'' BUSINESSNO,'' BUSINESSNAME,'' REMARK");
		sb.append(" from T_PROJECT_INFO a where  a.PROJECT_ID='" + nodeId + "'");

		// 根据查询条件，过滤树节点
		if (StringUtils.isNotBlank(queryValue) && StringUtils.isNotBlank(queryField))
		{
			// LJX 20100722 Add 加入收付款等。
			// 付款
			if (QueryField.PAYMENT_NO.equals(queryField))
			{
				sb.append(" and a.PROJECT_ID in(select c.PROJECT_ID from YPAYMENT a left outer join YPAYMENTITEM b on a.PAYMENTID=b.PAYMENTID left outer join T_PROJECT_INFO c on b.PROJECT_NO=c.PROJECT_NO ");
				sb.append(" where b.PAYMENTITEMID is not null and c.IS_AVAILABLE = '1' and (b.CONTRACT_NO is null or b.CONTRACT_NO = '' or b.CONTRACT_NO = ' ') and a.PAYMENTNO='" + queryValue + "')");
			}
			else if (QueryField.PAYMENT_SAPORDERNO.equals(queryField))
			{
				sb.append(" and 1=2");
			}
			// 收款
			else if (QueryField.COLLECT_NO.equals(queryField))
			{
				sb.append(" and a.PROJECT_ID in(select c.PROJECT_ID from YCOLLECT a left outer join YCOLLECTITEM b on a.COLLECTID=b.COLLECTID left outer join T_PROJECT_INFO c on b.PROJECT_NO=c.PROJECT_NO  ");
				sb.append(" where b.COLLECTITEMID is not null and c.IS_AVAILABLE = '1' and (b.CONTRACT_NO is null or b.CONTRACT_NO = '' or b.CONTRACT_NO = ' ') and a.COLLECTNO='" + queryValue + "')");
			}
			else if (QueryField.COLLECT_SAPORDERNO.equals(queryField))
			{
				sb.append(" and 1=2");
			}
			// 立项
			else if (QueryField.PROJECT_NO.equals(queryField))
			{}
			else if (QueryField.PROJECT_NAME.equals(queryField))
			{}
			else if (QueryField.PROJECT_CLASSNAME.equals(queryField)){}
			// 退款
			else if (QueryField.REFUNDMENTNO.equals(queryField))
			{
				sb.append(" and a.PROJECT_ID in(select c.PROJECT_ID from YREFUNDMENT a left outer join YREFUNDMENTITEM b on a.REFUNDMENTID=b.REFUNDMENTID left outer join T_PROJECT_INFO c on b.PROJECT_NO=c.PROJECT_NO ");
				sb.append(" where c.IS_AVAILABLE = '1' and (b.CONTRACT_NO is null or b.CONTRACT_NO = '' or b.CONTRACT_NO = ' ') and a.REFUNDMENTNO='" + queryValue + "')");

			}
			else if (QueryField.REFUNDMENTVOUCHERNO.equals(queryField))
			{
				sb.append(" and a.PROJECT_ID in(select c.PROJECT_ID from YREFUNDMENT a left outer join YREFUNDMENTITEM b on a.REFUNDMENTID=b.REFUNDMENTID left outer join T_PROJECT_INFO c on b.PROJECT_NO=c.PROJECT_NO ");
				sb.append(" left outer join YVOUCHER d on a.REFUNDMENTID = d.BUSINESSID ");
				sb.append(" where d.voucherNO is not null and (b.CONTRACT_NO is null or b.CONTRACT_NO = '' or b.CONTRACT_NO = ' ') and c.IS_AVAILABLE = '1' and d.VOUCHERNO='" + queryValue + "')");
			}
			else
			{
				sb.append(" and 1=2");
			}
		}

		sb.append(" ) t order by  t.BUSINESSTYPE,t.NODENAME");
	}

	/**
	 * 取得其他收付款信息 <BR>
	 * 
	 * @param nodeId
	 * @param tradeType
	 * @param queryField
	 * @param queryValue
	 * @param sb
	 */
	private void getOtherPaymentCollect(String nodeId, String tradeType, String queryField, String queryValue, StringBuffer sb, String dept_id, String apply_time,String apply_time1)
	{
		sb.append("select t.NODEID,t.PARENTNODEID,t.NODENAME,t.TRADETYPE,t.SDATE,t.PROCESSSTATE,t.URL,t.BUSINESSTYPE,t.BUSINESSTYPENAME,t.BUSINESSNO,t.BUSINESSNAME,t.REMARK from (");
		// 取得其他收付款(有立项无合同的收付款信息。)
		// 付款
		sb.append("	select a.PAYMENTID NODEID ,c.PROJECT_ID PARENTNODEID ,'付款单号：' || a.PAYMENTNO NODENAME,c.TRADE_TYPE TRADETYPE,'凭证日期：' || a.voucherdate SDATE,");
		sb.append(" case when a.TRADE_TYPE='01' ");
		sb.append(" THEN ");
		sb.append(" '" + UserContextHolder.getLocalUserContext().getUserContext().getWebContextPath() + "/xdss3/payment/homePaymentController.spr?action=_view'||'&'||'paymentid=' || a.PAYMENTID");
		sb.append(" else");
		sb.append(" '" + UserContextHolder.getLocalUserContext().getUserContext().getWebContextPath() + "/xdss3/payment/importPaymentController.spr?action=_view'||'&'||'paymentid=' || a.PAYMENTID");
		sb.append(" end URL ,");
		sb.append("'PAY' BUSINESSTYPE,'付款' BUSINESSTYPENAME,'付款单位：' || d.NAME1  BUSINESSNO,'金额：' || a.FACTAMOUNT BUSINESSNAME,'用途:' || a.TEXT REMARK,'状态：' ||");
		sb.append(" case when a.BUSINESSSTATE='1' or a.BUSINESSSTATE='2' or a.BUSINESSSTATE='3' then '在审批途中' ");
		sb.append(" when a.businessstate='4' then '审批通过' ");
		sb.append(" when a.businessstate='6' then '特批审批中' ");
		sb.append(" when a.businessstate='7' then '特批审批通过' ");
		sb.append("end PROCESSSTATE ");
		sb.append(" from YPAYMENT a ");
		sb.append(" left outer join YPAYMENTITEM b on a.PAYMENTID = b.PAYMENTID ");
		sb.append(" left outer join T_PROJECT_INFO c on c.PROJECT_NO = b.PROJECT_NO");
		sb.append(" left outer join YGETLIFNR d on a.SUPPLIER=d.LIFNR");
		sb.append(" where a.BUSINESSSTATE in(" + BusinessState.ALL_ONROAD + "," + BusinessState.ALL_SUBMITPASS + ") and (b.CONTRACT_NO is null or b.CONTRACT_NO = '' or b.CONTRACT_NO=' ')");
		// sb.append(" and c.PROJECT_ID='" + nodeId + "'");

		UserContext userContext = com.infolion.platform.console.sys.context.UserContextHolder.getLocalUserContext().getUserContext();
		if (isDeptAuth)
		{
			sb.append(" and a.DEPT_ID in ('" + userContext.getSysDept().getDeptid() + "'");
			if (StringUtils.isNotBlank(userContext.getGrantedDepartmentsId()))
				sb.append("," + userContext.getGrantedDepartmentsId() + "");
			sb.append(")");
		}

        if (StringUtils.isNotBlank(nodeId)) {
            sb.append(" AND EXISTS (SELECT 'x' FROM YPAYMENTITEM PI, T_PROJECT_INFO TT WHERE A.PAYMENTID = PI.PAYMENTID AND TT.PROJECT_NO = PI.PROJECT_NO AND TT.PROJECT_ID = '" + nodeId + "') ");
        }
        
		// 根据查询条件，过滤树节点
		if (StringUtils.isNotBlank(queryField))
		{
			// 立项
			if (QueryField.PROJECT_NO.equals(queryField))
			{
				if (StringUtils.isNotBlank(queryValue))
					sb.append(" and c.PROJECT_NO='" + queryValue + "'");
				if (StringUtils.isNotBlank(dept_id))
					sb.append(" and c.DEPT_ID='" + dept_id + "'");
				if (StringUtils.isNotBlank(apply_time))
					sb.append(" and format_date(c.APPLY_TIME) >='" + apply_time + "'");
				if (StringUtils.isNotBlank(apply_time1))
					sb.append(" and format_date(c.APPLY_TIME) <='" + apply_time1 + "'");

			}
			else if (QueryField.PROJECT_NAME.equals(queryField))
			{
				if (StringUtils.isNotBlank(queryValue))
					sb.append(" and c.PROJECT_NAME like '%" + queryValue + "%'");
				if (StringUtils.isNotBlank(dept_id))
					sb.append(" and c.DEPT_ID='" + dept_id + "'");
				if (StringUtils.isNotBlank(apply_time))
					sb.append(" and format_date(c.APPLY_TIME) >='" + apply_time + "'");
				if (StringUtils.isNotBlank(apply_time1))
					sb.append(" and format_date(c.APPLY_TIME) <='" + apply_time1 + "'");

			}
			else if (QueryField.PROJECT_CLASSNAME.equals(queryField)){
				if (StringUtils.isNotBlank(queryValue))
					sb.append(" and c.CLASS_NAME like '%" + queryValue + "%'");
				if (StringUtils.isNotBlank(dept_id))
					sb.append(" and c.DEPT_ID='" + dept_id + "'");
				if (StringUtils.isNotBlank(apply_time))
					sb.append(" and format_date(c.APPLY_TIME) >='" + apply_time + "'");
				if (StringUtils.isNotBlank(apply_time1))
					sb.append(" and format_date(c.APPLY_TIME) <='" + apply_time1 + "'");
			}
			// 付款
			else if (QueryField.PAYMENT_NO.equals(queryField))
			{
				sb.append(" and a.CONTRACT_GROUP_ID in(select c.CONTRACT_GROUP_ID from YPAYMENT a left outer join YPAYMENTITEM b on a.PAYMENTID=b.PAYMENTID left outer join T_CONTRACT_PURCHASE_INFO c on b.CONTRACT_NO=c.CONTRACT_NO ");
				sb.append(" where b.PAYMENTITEMID is not null and c.IS_AVAILABLE = '1' and a.PAYMENTNO='" + queryValue + "')");
			}
			else if (QueryField.PAYMENT_SAPORDERNO.equals(queryField))
			{
				sb.append("  and 1=2");
			}
			else
			{
				sb.append("  and 1=2");
			}
		}

		// 收款 收款编码,收款日期,收款单位,金额,用途,状态
		sb.append(" union");
		sb.append("	select a.COLLECTID NODEID ,c.PROJECT_ID PARENTNODEID ,'收款单号：' || a.COLLECTNO NODENAME,c.TRADE_TYPE TRADETYPE,'收款日期：' || a.ACCOUNTDATE SDATE,");
		sb.append(" '" + UserContextHolder.getLocalUserContext().getUserContext().getWebContextPath() + "/xdss3/collect/collectController.spr?action=_view'||'&'||'collectid=' || a.COLLECTID  URL ,");
		sb.append("'COL' BUSINESSTYPE,'收款' BUSINESSTYPENAME,'收款单位：' || d.NAME1  BUSINESSNO,'金额：' || a.ACTAMOUNT BUSINESSNAME,'用途:' || a.TEXT REMARK,'状态：' ||");
		sb.append(" case when a.BUSINESSSTATE='1' or a.BUSINESSSTATE='2' then '在审批途中' ");
		sb.append(" when a.businessstate='3' then '审批通过' ");
		sb.append("end PROCESSSTATE ");
		sb.append(" from YCOLLECT a ");
		sb.append(" left outer join YCOLLECTITEM b on a.COLLECTID = b.COLLECTID ");
		sb.append(" left outer join T_PROJECT_INFO c on c.PROJECT_NO = b.PROJECT_NO");
		sb.append(" left outer join YGETKUNNR d on a.CUSTOMER=d.KUNNR");
		sb.append(" where a.BUSINESSSTATE in(" + BusinessState.ALL_ONROAD + "," + BusinessState.ALL_SUBMITPASS + ") and (b.CONTRACT_NO is null or b.CONTRACT_NO = '' or b.CONTRACT_NO=' ')");
		// sb.append(" and c.PROJECT_ID='" + nodeId + "'");

		// LJX 20100706 添加数据权限控制
		if (isDeptAuth)
		{
			sb.append(" and a.DEPT_ID in ('" + userContext.getSysDept().getDeptid() + "'");
			if (StringUtils.isNotBlank(userContext.getGrantedDepartmentsId()))
				sb.append("," + userContext.getGrantedDepartmentsId() + "");
			sb.append(")");
		}

        if (StringUtils.isNotBlank(nodeId)) {
            sb.append(" AND EXISTS (SELECT 'x' FROM ycollectitem ci, T_PROJECT_INFO TT WHERE A.collectid = ci.collectid AND TT.PROJECT_NO = CI.PROJECT_NO AND TT.PROJECT_ID = '" + nodeId + "') ");
        }
		// 根据查询条件，过滤树节点
		if (StringUtils.isNotBlank(queryField))
		{
			// 立项
			if (QueryField.PROJECT_NO.equals(queryField))
			{
				if (StringUtils.isNotBlank(queryValue))
					sb.append(" and c.PROJECT_NO='" + queryValue + "'");
				if (StringUtils.isNotBlank(dept_id))
					sb.append(" and c.DEPT_ID='" + dept_id + "'");
				if (StringUtils.isNotBlank(apply_time))
					sb.append(" and format_date(c.APPLY_TIME) >='" + apply_time + "'");
				if (StringUtils.isNotBlank(apply_time1))
					sb.append(" and format_date(c.APPLY_TIME) <='" + apply_time1 + "'");
			}
			else if (QueryField.PROJECT_NAME.equals(queryField))
			{
				if (StringUtils.isNotBlank(queryValue))
					sb.append(" and c.PROJECT_NAME like '%" + queryValue + "%'");
				if (StringUtils.isNotBlank(dept_id))
					sb.append(" and c.DEPT_ID='" + dept_id + "'");
				if (StringUtils.isNotBlank(apply_time))
					sb.append(" and format_date(c.APPLY_TIME) >='" + apply_time + "'");
				if (StringUtils.isNotBlank(apply_time1))
					sb.append(" and format_date(c.APPLY_TIME) <='" + apply_time1 + "'");
			}
			else if (QueryField.PROJECT_CLASSNAME.equals(queryField))
			{
				if (StringUtils.isNotBlank(queryValue))
					sb.append(" and c.CLASS_NAME like '%" + queryValue + "%'");
				if (StringUtils.isNotBlank(dept_id))
					sb.append(" and c.DEPT_ID='" + dept_id + "'");
				if (StringUtils.isNotBlank(apply_time))
					sb.append(" and format_date(c.APPLY_TIME) >='" + apply_time + "'");
				if (StringUtils.isNotBlank(apply_time1))
					sb.append(" and format_date(c.APPLY_TIME) <='" + apply_time1 + "'");
			}
			// 收款
			else if (QueryField.COLLECT_NO.equals(queryField))
			{
				sb.append(" and a.COLLECTNO='" + queryValue + "'");
			}
			else if (QueryField.COLLECT_SAPORDERNO.equals(queryField))
			{
				sb.append("  and 1=2");
			}
			else
			{
				sb.append("  and 1=2");
			}
		}

		// 退款
		sb.append(" union");
		sb.append("	select a.REFUNDMENTID NODEID ,d.PROJECT_ID PARENTNODEID ,'凭证号：' || c.VOUCHERNO NODENAME,d.TRADE_TYPE TRADETYPE,'状态：'||");
		sb.append(" case when a.BUSINESSSTATE='1' or a.BUSINESSSTATE='2'  then '在审批途中' ");
		sb.append(" when a.businessstate='3' then '审批通过' ");
		sb.append("end SDATE, ");
		sb.append(" case when a.CUSTOMER=' ' ");
		sb.append(" THEN ");
		sb.append(" '" + UserContextHolder.getLocalUserContext().getUserContext().getWebContextPath() + "/xdss3/supplierDrawback/supplierRefundmentController.spr?action=_view'||'&'||'refundmentid=' || a.REFUNDMENTID");
		sb.append(" else");
		sb.append(" '" + UserContextHolder.getLocalUserContext().getUserContext().getWebContextPath() + "/xdss3/customerDrawback/customerRefundmentController.spr?action=_view'||'&'||'refundmentid=' || a.refundmentid");
		sb.append(" end URL ,");
		sb.append("'BAK' BUSINESSTYPE,'退款' BUSINESSTYPENAME,'金额：' || A.refundamount BUSINESSNO,'文本：' || a.TEXT BUSINESSNAME,'' REMARK,'' PROCESSSTATE");
		sb.append(" from YREFUNDMENT a ");
		sb.append(" left outer join YREFUNDMENTITEM b on a.REFUNDMENTID = b.REFUNDMENTID");
		sb.append(" left outer join YVOUCHER c on a.REFUNDMENTID = c.BUSINESSID ");
		sb.append(" left outer join T_PROJECT_INFO d on b.PROJECT_NO=d.PROJECT_NO");
		sb.append(" where a.BUSINESSSTATE in(" + BusinessState.ALL_ONROAD + "," + BusinessState.ALL_SUBMITPASS + ") and (b.CONTRACT_NO is null or b.CONTRACT_NO = '' or b.CONTRACT_NO=' ')");
		// sb.append(" and d.PROJECT_ID='" + nodeId + "'");

		// LJX 20100706 添加数据权限控制
		if (isDeptAuth)
		{
			sb.append(" and a.DEPT_ID in ('" + userContext.getSysDept().getDeptid() + "'");
			if (StringUtils.isNotBlank(userContext.getGrantedDepartmentsId()))
				sb.append("," + userContext.getGrantedDepartmentsId() + "");
			sb.append(")");
		}
		
		if (StringUtils.isNotBlank(nodeId)) {
		    sb.append(" AND EXISTS (SELECT 'x' FROM YREFUNDMENTITEM RI, T_PROJECT_INFO TT WHERE RI.REFUNDMENTID = A.REFUNDMENTID AND RI.PROJECT_NO = TT.PROJECT_NO AND TT.PROJECT_ID = '"+nodeId+"') ");
		}

		// 根据查询条件，过滤树节点
		if (StringUtils.isNotBlank(queryField))
		{
			// 立项
			if (QueryField.PROJECT_NO.equals(queryField))
			{
				if (StringUtils.isNotBlank(queryValue))
					sb.append(" and d.PROJECT_NO='" + queryValue + "'");
				if (StringUtils.isNotBlank(dept_id))
					sb.append(" and d.DEPT_ID='" + dept_id + "'");
				if (StringUtils.isNotBlank(apply_time))
					sb.append(" and format_date(d.APPLY_TIME) >='" + apply_time + "'");
				if (StringUtils.isNotBlank(apply_time1))
					sb.append(" and format_date(d.APPLY_TIME) <='" + apply_time1 + "'");

			}
			else if (QueryField.PROJECT_NAME.equals(queryField))
			{
				if (StringUtils.isNotBlank(queryValue))
					sb.append(" and d.PROJECT_NAME like '%" + queryValue + "%'");
				if (StringUtils.isNotBlank(dept_id))
					sb.append(" and d.DEPT_ID='" + dept_id + "'");
				if (StringUtils.isNotBlank(apply_time))
					sb.append(" and format_date(d.APPLY_TIME) >='" + apply_time + "'");
				if (StringUtils.isNotBlank(apply_time1))
					sb.append(" and format_date(d.APPLY_TIME) <='" + apply_time1 + "'");

			}
			else if (QueryField.PROJECT_CLASSNAME.equals(queryField))
			{
				if (StringUtils.isNotBlank(queryValue))
					sb.append(" and d.CLASS_NAME like '%" + queryValue + "%'");
				if (StringUtils.isNotBlank(dept_id))
					sb.append(" and d.DEPT_ID='" + dept_id + "'");
				if (StringUtils.isNotBlank(apply_time))
					sb.append(" and format_date(d.APPLY_TIME) >='" + apply_time + "'");
				if (StringUtils.isNotBlank(apply_time1))
					sb.append(" and format_date(d.APPLY_TIME) <='" + apply_time1 + "'");

			}
			// 退款
			else if (QueryField.REFUNDMENTNO.equals(queryField))
			{
				sb.append(" and a.REFUNDMENTNO='" + queryValue + "'");
			}
			else if (QueryField.REFUNDMENTVOUCHERNO.equals(queryField))
			{
				sb.append("  and c.VOUCHERNO='" + queryValue + "'");
			}
			else
			{
				sb.append("  and 1=2");
			}
		}

		sb.append(" ) t order by  t.BUSINESSTYPE");
	}

	/**
	 * 取得合同组下，采购合同、销售合同。 <BR>
	 * 采购合同、销售合同, 点击合同组节点、其他收付款、查询合同组、下采购合同、销售合同。
	 * 
	 * @param nodeId
	 * @param tradeType
	 * @param queryField
	 * @param queryValue
	 * @param sb
	 */
	private void getPurchaseOrSalesContract(String nodeId, String tradeType, String queryField, String queryValue, StringBuffer sb)
	{
		sb.append("select t.NODEID,t.PARENTNODEID,t.NODENAME,t.TRADETYPE,t.SDATE,t.PROCESSSTATE,t.URL,t.BUSINESSTYPE,t.BUSINESSTYPENAME,t.BUSINESSNO,t.BUSINESSNAME,t.REMARK,t.REMARK1 from (");

		sb.append("	SELECT a.CONTRACT_PURCHASE_ID NODEID,a.CONTRACT_GROUP_ID PARENTNODEID,a.CONTRACT_NO NODENAME,a.TRADE_TYPE TRADETYPE, a.APPLY_TIME SDATE,");
		sb.append("'contractController.spr?action=ArchPurchaseInfoView'||'&'||'businessRecordId=' || a.CONTRACT_PURCHASE_ID URL,");
		sb.append("'PC' BUSINESSTYPE,'采购合同' BUSINESSTYPENAME, a.EKKO_UNSEZ BUSINESSNO,a.CONTRACT_NAME BUSINESSNAME,'备注:' || a.mask REMARK,b.TITLE PROCESSSTATE,'金额:'||a.total_amount||'数量:'||a.total_quality REMARK1 ");
		sb.append(" FROM T_CONTRACT_PURCHASE_INFO a left outer join BM_ORDER_STATE b on a.ORDER_STATE = b.ID WHERE  a.IS_AVAILABLE = '1' AND a.ORDER_STATE IN ('10','11','2','3','5','6','7','8','9') ");
		sb.append(" AND a.TRADE_TYPE='" + tradeType + "' and a.CONTRACT_GROUP_ID='" + nodeId + "'");
		// 根据查询条件，过滤树节点
		if (StringUtils.isNotBlank(queryValue) && StringUtils.isNotBlank(queryField))
		{
			// 采购合同
			if (QueryField.CONTRACT_PURCHASE_NO.equals(queryField))
			{
				sb.append(" and a.CONTRACT_NO ='" + queryValue + "'");
			}
			else if (QueryField.EKKO_UNSEZ.equals(queryField))
			{
				sb.append(" and a.EKKO_UNSEZ ='" + queryValue + "'");
			}
			else if (QueryField.CONTRACT_PURCHASE_NAME.equals(queryField))
			{
				sb.append(" and a.CONTRACT_NAME like '%" + queryValue + "%'");
			}
			else if (QueryField.CONTRACT_PURCHASE_PRONAME.equals(queryField))
			{
				//sb.append(" and a.ekko_lifnr_name like '%" + queryValue + "%'");
			}   
			// 销售合同
			else if (QueryField.CONTRACT_SALES_NO.equals(queryField))
			{
				sb.append(" and 1=2");
			}
			else if (QueryField.VBKD_IHREZ.equals(queryField))
			{
				sb.append(" and 1=2");
			}
			else if (QueryField.CONTRACT_SALES_NAME.equals(queryField))
			{
				sb.append(" and 1=2");
			}
			else if (QueryField.CONTRACT_SALES_CUSNAME.equals(queryField))
			{
				//sb.append(" and 1=2");
			}
			// ////////采购合同下 单据信息:
			// @@@@采购合同下,进仓(收货)、到单、开证
			// 进仓单(收货)
			// 进仓单(收货)
			else if (QueryField.RECEIPT_NO.equals(queryField))
			{
				sb.append(" and a.CONTRACT_PURCHASE_ID =(select a.CONTRACT_PURCHASE_ID from T_RECEIPT_INFO a where a.IS_AVAILABLE = '1' and a.EXAMINE_STATE in ('2', '3', '5','6', '7', '8') and a.RECEIPT_NO='" + queryValue + "')");
			}
			else if (QueryField.SAP_RETURN_NO1.equals(queryField))
			{
				sb.append(" and a.CONTRACT_PURCHASE_ID =(select a.CONTRACT_PURCHASE_ID from T_RECEIPT_INFO a where a.IS_AVAILABLE = '1' and a.EXAMINE_STATE in ('2', '3', '5','6', '7', '8') and a.SAP_RETURN_NO='" + queryValue + "')");
			}
			// 到单
			else if (QueryField.PICK_LIST_NO.equals(queryField))
			{
				sb.append(" and a.CONTRACT_PURCHASE_ID =(select CONTRACT_PURCHASE_ID from T_PICK_LIST_INFO a where a.IS_AVAILABLE = '1' and a.EXAMINE_STATE in ('2', '3', '5', '6', '7', '8') and a.PICK_LIST_NO='" + queryValue + "')");
			}
			// 信用证收证
			else if (QueryField.CREDIT_NO.equals(queryField))
			{
				// 开证
				sb.append(" and a.CONTRACT_PURCHASE_ID =(select b.CONTRACT_PURCHASE_ID from T_CREDIT_CREATE b ");
				sb.append(" left outer join  T_CREDIT_INFO a on a.CREDIT_ID = b.CREDIT_ID ");
				sb.append(" where a.IS_AVAILABLE = '1'  and a.CREATE_OR_REC = '1'  and a.CREDIT_STATE in('15','11','3','4','5','10','12','13') and a.CREDIT_NO='" + queryValue + "')");
			}
			// 发票校验
			else if (QueryField.BELNR.equals(queryField))
			{
				sb.append(" and a.CONTRACT_PURCHASE_ID in(select b.CONTRACT_PURCHASE_ID from YGETBELNR a left outer join T_CONTRACT_PURCHASE_INFO b on a.EBELN=b.SAP_ORDER_NO where a.BELNR='" + queryValue + "')");
			}
			// LJX 20100722 Add 加入收付款等。
			// 付款
			else if (QueryField.PAYMENT_NO.equals(queryField))
			{
				sb.append(" and a.CONTRACT_PURCHASE_ID in(select c.CONTRACT_PURCHASE_ID from YPAYMENT a left outer join YPAYMENTITEM b on a.PAYMENTID=b.PAYMENTID left outer join T_CONTRACT_PURCHASE_INFO c on b.CONTRACT_NO=c.CONTRACT_NO ");
				sb.append(" where b.PAYMENTITEMID is not null and c.IS_AVAILABLE = '1' and a.PAYMENTNO='" + queryValue + "')");
			}
			else if (QueryField.PAYMENT_SAPORDERNO.equals(queryField))
			{
				sb.append("  and a.CONTRACT_PURCHASE_ID in(select c.CONTRACT_PURCHASE_ID from YPAYMENT a left outer join YPAYMENTITEM b on a.PAYMENTID=b.PAYMENTID left outer join T_CONTRACT_PURCHASE_INFO c on b.CONTRACT_NO=c.CONTRACT_NO ");
				sb.append(" where b.PAYMENTITEMID is not null and c.IS_AVAILABLE = '1' and c.SAP_ORDER_NO='" + queryValue + "')");
			}
			// 退款(退供应商)
			else if (QueryField.REFUNDMENTNO.equals(queryField))
			{
				sb.append("  and a.CONTRACT_PURCHASE_ID in(select c.CONTRACT_PURCHASE_ID from YREFUNDMENT a left outer join YREFUNDMENTITEM b on a.refundmentid=b.refundmentid left outer join T_CONTRACT_PURCHASE_INFO c on b.CONTRACT_NO=c.CONTRACT_NO ");
				sb.append(" where a.SUPPLIER is not null and a.SUPPLIER!=' ' and c.IS_AVAILABLE = '1' and a.REFUNDMENTNO='" + queryValue + "')");
			}
			else if (QueryField.REFUNDMENTVOUCHERNO.equals(queryField))
			{
				sb.append("  and a.CONTRACT_PURCHASE_ID in(select c.CONTRACT_PURCHASE_ID from YREFUNDMENT a left outer join YREFUNDMENTITEM b on a.refundmentid=b.refundmentid left outer join T_CONTRACT_PURCHASE_INFO c on b.CONTRACT_NO=c.CONTRACT_NO ");
				sb.append(" left outer join YVOUCHER d on a.REFUNDMENTID = d.BUSINESSID ");
				sb.append(" where a.SUPPLIER is not null and a.SUPPLIER!=' ' and d.voucherNO is not null and c.IS_AVAILABLE = '1' and d.VOUCHERNO='" + queryValue + "')");
			}

			// ////////销售合同下 单据信息:
			// @@@@销售合同下,出仓、出单、收证、开票
			// 出仓、发货
			else if (QueryField.SHIP_NO.equals(queryField))
			{
				sb.append(" and 1=2");
			}
			else if (QueryField.SAP_RETURN_NO2.equals(queryField))
			{
				sb.append(" and 1=2");
			}
			// 出单
			else if (QueryField.INV_NO.equals(queryField))
			{
				sb.append(" and 1=2");
			}
			// // 信用证收证
			// else if (QueryField.CREDIT_NO.equals(queryField))
			// {
			// sb.append(" and 1=2");
			// }
			// 开票
			else if (QueryField.TAX_NO.equals(queryField))
			{
				sb.append(" and 1=2");
			}
			else if (QueryField.SAP_BILL_NO.equals(queryField))
			{
				sb.append(" and 1=2");
			}
			// 收款
			else if (QueryField.COLLECT_NO.equals(queryField))
			{
				sb.append(" and 1=2");
			}
			else if (QueryField.COLLECT_SAPORDERNO.equals(queryField))
			{
				sb.append(" and 1=2");
			}
			else
			{}

		}

		sb.append(" union");
		sb.append("	SELECT a.CONTRACT_SALES_ID NODEID,a.CONTRACT_GROUP_ID PARENTNODEID,a.CONTRACT_NO NODENAME,a.TRADE_TYPE TRADETYPE,a.APPLY_TIME SDATE,");
		sb.append("'contractController.spr?action=archSalesInfoView'||'&'||'businessRecordId=' || a.CONTRACT_SALES_ID URL,");
		sb.append("'SC' BUSINESSTYPE,'销售合同' BUSINESSTYPENAME,a.VBKD_IHREZ BUSINESSNO,a.CONTRACT_NAME BUSINESSNAME,'备注:' || a.mask REMARK, b.TITLE PROCESSSTATE,'金额:'||a.order_total||'数量:'||a.total_quality REMARK1 ");
		sb.append(" FROM T_CONTRACT_SALES_INFO a left outer join BM_ORDER_STATE b on a.ORDER_STATE = b.ID WHERE  a.IS_AVAILABLE = '1' AND a.ORDER_STATE IN ('10','11','2','3','5','6','7','8','9') ");
		sb.append(" AND a.TRADE_TYPE='" + tradeType + "' and a.CONTRACT_GROUP_ID='" + nodeId + "'");
		// 根据查询条件，过滤树节点
		if (StringUtils.isNotBlank(queryValue) && StringUtils.isNotBlank(queryField))
		{
			// 采购合同
			if (QueryField.CONTRACT_PURCHASE_NO.equals(queryField))
			{
				sb.append(" and 1=2");
			}
			else if (QueryField.EKKO_UNSEZ.equals(queryField))
			{
				sb.append(" and 1=2");
			}
			else if (QueryField.CONTRACT_PURCHASE_NAME.equals(queryField))
			{
				sb.append(" and 1=2");
			}
			else if (QueryField.CONTRACT_PURCHASE_PRONAME.equals(queryField))
			{
				//sb.append(" and 1=2");
			}
			// 销售合同
			else if (QueryField.CONTRACT_SALES_NO.equals(queryField))
			{
				sb.append(" and a.CONTRACT_NO ='" + queryValue + "'");
			}
			else if (QueryField.VBKD_IHREZ.equals(queryField))
			{
				sb.append(" and a.VBKD_IHREZ ='" + queryValue + "'");
			}
			else if (QueryField.CONTRACT_SALES_NAME.equals(queryField))
			{
				sb.append(" and a.CONTRACT_NAME like '%" + queryValue + "%'");
			}
			else if (QueryField.CONTRACT_SALES_CUSNAME.equals(queryField))
			{
				//sb.append(" and a.KUAGV_KUNNR_NAME like '%" + queryValue + "%'");
			}
			// ////////采购合同下 单据信息:
			// @@@@采购合同下,进仓(收货)、到单、开证
			// 进仓单(收货)
			// 进仓单(收货)
			else if (QueryField.RECEIPT_NO.equals(queryField))
			{
				sb.append(" and 1=2");
			}
			else if (QueryField.SAP_RETURN_NO1.equals(queryField))
			{
				sb.append(" and 1=2");
			}
			// 到单
			else if (QueryField.PICK_LIST_NO.equals(queryField))
			{
				sb.append(" and 1=2");
			}
			// // 信用证收证
			// else if (QueryField.CREDIT_NO.equals(queryField))
			// {
			// sb.append(" and 1=2");
			// }
			// 发票校验
			else if (QueryField.BELNR.equals(queryField))
			{
				sb.append(" and 1=2");
			}
			// 付款
			else if (QueryField.PAYMENT_NO.equals(queryField))
			{
				sb.append(" and 1=2");
			}
			else if (QueryField.PAYMENT_SAPORDERNO.equals(queryField))
			{
				sb.append(" and 1=2");
			}
			// ////////销售合同下 单据信息:
			// @@@@销售合同下,出仓、出单、收证、开票
			// 出仓、发货
			else if (QueryField.SHIP_NO.equals(queryField))
			{
				sb.append(" and a.CONTRACT_SALES_ID =(");
				sb.append(" select a.CONTRACT_SALES_ID from T_SHIP_INFO a where a.IS_AVAILABLE = '1' and a.EXAMINE_STATE in ('2', '3', '5','6', '7', '8')");
				sb.append(" and a.SHIP_NO='" + queryValue + "')");
			}
			else if (QueryField.SAP_RETURN_NO2.equals(queryField))
			{
				sb.append(" and a.CONTRACT_SALES_ID = (");
				sb.append(" select a.CONTRACT_SALES_ID from T_SHIP_INFO a where a.IS_AVAILABLE = '1' and a.EXAMINE_STATE in ('2', '3', '5','6', '7', '8')");
				sb.append(" and a.SAP_RETURN_NO='" + queryValue + "')");
			}
			// 出单
			else if (QueryField.INV_NO.equals(queryField))
			{
				sb.append(" and a.CONTRACT_SALES_ID in(");
				sb.append(" select c.CONTRACT_SALES_ID from T_EXPORT_BILL_EXAM a, T_EXPORT_BILL_EXAM_D b, T_EXPORT_APPLY c ");
				sb.append(" where a.IS_AVAILABLE = '1' and c.IS_AVAILABLE = '1' and a.EXPORT_BILL_EXAM_ID = b.EXPORT_BILL_EXAM_ID");
				sb.append(" and b.EXPORT_APPLY_ID = c.EXPORT_APPLY_ID and a.INV_NO='" + queryValue + "')");
			}
			// 信用证收证
			else if (QueryField.CREDIT_NO.equals(queryField))
			{
				// 收证
				sb.append(" and a.CONTRACT_SALES_ID in (select d.CONTRACT_SALES_ID from T_EXPORT_APPLY d where d.LCNO='" + queryValue + "')");
			}
			// 内贸开票
			else if (QueryField.TAX_NO.equals(queryField))
			{
				sb.append(" and a.CONTRACT_SALES_ID in(");
				sb.append(" select distinct a.CONTRACT_SALES_ID from T_BILL_APPLY_MATERIAL a where a.BILL_APPLY_ID is not null and a.BILL_APPLY_ID in");
				sb.append(" (select b.BILL_APPLY_ID from T_BILL_APPLY b where b.IS_AVAILABLE = '1' and b.EXAMINE_STATE in ('2', '3', '5', '6', '7', '8') and b.TAX_NO = '" + queryValue + "'))");
			}
			else if (QueryField.SAP_BILL_NO.equals(queryField))
			{
				sb.append(" and a.CONTRACT_SALES_ID in(");
				sb.append(" select distinct a.CONTRACT_SALES_ID from T_BILL_APPLY_MATERIAL a where a.BILL_APPLY_ID is not null and a.BILL_APPLY_ID in");
				sb.append(" (select b.BILL_APPLY_ID from T_BILL_APPLY b where b.IS_AVAILABLE = '1' and b.EXAMINE_STATE in ('2', '3', '5', '6', '7', '8') and b.SAP_BILL_NO = '" + queryValue + "'))");
			}
			// 出口开票
			else if (QueryField.VBELN.equals(queryField))
			{
				sb.append(" and a.CONTRACT_SALES_ID in(select a.CONTRACT_SALES_ID from T_CONTRACT_SALES_INFO a where a.sap_order_no in(SELECT AUBEL from YGETVBRP WHERE VBELN='" + queryValue + "'))");
			}
			// LJX 20100722 Add
			// 收款
			else if (QueryField.COLLECT_NO.equals(queryField))
			{
				sb.append(" and a.CONTRACT_SALES_ID in(select c.CONTRACT_SALES_ID from YCOLLECT a left outer join YCOLLECTITEM b on a.COLLECTID=b.COLLECTID left outer join T_CONTRACT_SALES_INFO c on b.CONTRACT_NO=c.CONTRACT_NO ");
				sb.append(" where b.COLLECTITEMID is not null and c.IS_AVAILABLE = '1' and a.COLLECTNO='" + queryValue + "')");
			}
			else if (QueryField.COLLECT_SAPORDERNO.equals(queryField))
			{
				sb.append(" and a.CONTRACT_SALES_ID in(select c.CONTRACT_SALES_ID from YCOLLECT a left outer join YCOLLECTITEM b on a.COLLECTID=b.COLLECTID left outer join T_CONTRACT_SALES_INFO c on b.CONTRACT_NO=c.CONTRACT_NO ");
				sb.append(" where b.COLLECTITEMID is not null and c.IS_AVAILABLE = '1' and c.SAP_ORDER_NO='" + queryValue + "')");
			}
			// 退款(退客户)
			else if (QueryField.REFUNDMENTNO.equals(queryField))
			{
				sb.append("  and a.CONTRACT_SALES_ID in(select c.CONTRACT_SALES_ID from YREFUNDMENT a left outer join YREFUNDMENTITEM b on a.refundmentid=b.refundmentid left outer join T_CONTRACT_SALES_INFO c on b.CONTRACT_NO=c.CONTRACT_NO ");
				sb.append(" where a.CUSTOMER is not null and a.CUSTOMER!=' ' and c.IS_AVAILABLE = '1' and a.REFUNDMENTNO='" + queryValue + "')");
			}
			else if (QueryField.REFUNDMENTVOUCHERNO.equals(queryField))
			{
				sb.append("  and a.CONTRACT_SALES_ID in(select c.CONTRACT_SALES_ID from YREFUNDMENT a left outer join YREFUNDMENTITEM b on a.refundmentid=b.refundmentid left outer join T_CONTRACT_SALES_INFO c on b.CONTRACT_NO=c.CONTRACT_NO ");
				sb.append(" left outer join YVOUCHER d on a.REFUNDMENTID = d.BUSINESSID ");
				sb.append(" where a.CUSTOMER is not null and a.CUSTOMER!=' ' and d.voucherNO is not null and c.IS_AVAILABLE = '1' and d.VOUCHERNO='" + queryValue + "')");
			}
			else
			{}

		}

		sb.append(" ) t order by  t.BUSINESSTYPE");
	}

	/**
	 * 取得立项树节点
	 * 
	 * @param tradeTypeIds
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<TradeMonitoringTreeNode> getProjectColumnTree(String tradeType, String queryField, String queryValue, String dept_id, String apply_time,String apply_time1)
	{
		List<TradeMonitoringTreeNode> list = new ArrayList<TradeMonitoringTreeNode>();

		StringBuffer sb = new StringBuffer();
		sb.append("select a.PROJECT_ID NODEID,'" + tradeType + "' PARENTNODEID, a.PROJECT_NO NODENAME,a.TRADE_TYPE TRADETYPE,a.APPLY_TIME SDATE,");
		sb.append("'projectController.spr?action=modify'||'&'||'from=view'||'&'||'projectId=' || a.PROJECT_ID URL,");
		sb.append(" 'PRO' BUSINESSTYPE,'立项' BUSINESSTYPENAME,a.PROJECT_NO  BUSINESSNO, a.PROJECT_NAME BUSINESSNAME,'备注:' || a.MASK REMARK");
		sb.append(" ,b.TITLE PROCESSSTATE from T_PROJECT_INFO a left outer join BM_PROJECT_STATE b on a.PROJECT_STATE=b.ID ");
		sb.append(" where a.PROJECT_STATE in ('2','3','4','5','6','8') and a.IS_AVAILABLE = '1'");

		// LJX 20100706 添加数据权限控制
		UserContext userContext = com.infolion.platform.console.sys.context.UserContextHolder.getLocalUserContext().getUserContext();
		if (isDeptAuth)
		{
			sb.append(" and a.DEPT_ID in ('" + userContext.getSysDept().getDeptid() + "'");
			if (StringUtils.isNotBlank(userContext.getGrantedDepartmentsId()))
				sb.append("," + userContext.getGrantedDepartmentsId() + "");
			sb.append(")");
		}

		// 根据查询条件，过滤树节点
		if (StringUtils.isNotBlank(queryField))
		{
			// 立项
			if (QueryField.PROJECT_NO.equals(queryField))
			{
				if (StringUtils.isNotBlank(queryValue))
					sb.append(" and a.PROJECT_NO='" + queryValue + "'");
				if (StringUtils.isNotBlank(dept_id))
					sb.append(" and a.DEPT_ID='" + dept_id + "'");
				if (StringUtils.isNotBlank(apply_time))
					sb.append(" and format_date(a.APPLY_TIME) >='" + apply_time + "'");
				if (StringUtils.isNotBlank(apply_time1))
					sb.append(" and format_date(a.APPLY_TIME) <='" + apply_time1 + "'");
			}
			else if (QueryField.PROJECT_NAME.equals(queryField))
			{
				if (StringUtils.isNotBlank(queryValue))
					sb.append(" and a.PROJECT_NAME like '%" + queryValue + "%'");
				if (StringUtils.isNotBlank(dept_id))
					sb.append(" and a.DEPT_ID='" + dept_id + "'");
				if (StringUtils.isNotBlank(apply_time))
					sb.append(" and format_date(a.APPLY_TIME) >='" + apply_time + "'");
				if (StringUtils.isNotBlank(apply_time1))
					sb.append(" and format_date(a.APPLY_TIME) <='" + apply_time1 + "'");
			}
			else if (QueryField.PROJECT_CLASSNAME.equals(queryField))
			{
				if (StringUtils.isNotBlank(queryValue))
					sb.append(" and a.CLASS_NAME like '%" + queryValue + "%'");
				if (StringUtils.isNotBlank(dept_id))
					sb.append(" and a.DEPT_ID='" + dept_id + "'");
				if (StringUtils.isNotBlank(apply_time))
					sb.append(" and format_date(a.APPLY_TIME) >='" + apply_time + "'");
				if (StringUtils.isNotBlank(apply_time1))
					sb.append(" and format_date(a.APPLY_TIME) <='" + apply_time1 + "'");
			}
			// 合同组
			else if (QueryField.CONTRACT_GROUP_NO.equals(queryField))
			{
				sb.append(" and a.PROJECT_ID =(select a.PROJECT_ID from T_CONTRACT_GROUP a where  a.IS_AVAILABLE = '1'");
				sb.append(" and a.CONTRACT_GROUP_NO='" + queryValue + "')");
			}
			else if (QueryField.CONTRACT_GROUP_NAME.equals(queryField))
			{
				sb.append(" and a.PROJECT_ID in(select a.PROJECT_ID from T_CONTRACT_GROUP a where  a.IS_AVAILABLE = '1'");
				sb.append(" and a.CONTRACT_GROUP_NAME like '%" + queryValue + "%')");
			}
			// 采购合同
			else if (QueryField.CONTRACT_PURCHASE_NO.equals(queryField))
			{
				sb.append(" and a.PROJECT_ID in(select a.PROJECT_ID from T_CONTRACT_PURCHASE_INFO a where a.IS_AVAILABLE = '1'");
				sb.append(" and a.ORDER_STATE in ('10','11','2','3','5','6','7','8','9') and a.CONTRACT_NO='" + queryValue + "')");
			}
			else if (QueryField.EKKO_UNSEZ.equals(queryField))
			{
				sb.append(" and a.PROJECT_ID in (select a.PROJECT_ID from T_CONTRACT_PURCHASE_INFO a where a.IS_AVAILABLE = '1'");
				sb.append(" and a.ORDER_STATE in ('10','11','2','3','5','6','7','8','9') and a.EKKO_UNSEZ='" + queryValue + "')");
			}
			else if (QueryField.CONTRACT_PURCHASE_NAME.equals(queryField))
			{
				sb.append(" and a.PROJECT_ID in(select distinct a.PROJECT_ID from T_CONTRACT_PURCHASE_INFO a where a.IS_AVAILABLE = '1'");
				sb.append(" and a.ORDER_STATE in ('10','11','2','3','5','6','7','8','9') and a.CONTRACT_NAME like '%" + queryValue + "%')");
			}
			else if (QueryField.CONTRACT_PURCHASE_PRONAME.equals(queryField))
			{
				sb.append(" and exists (select '' from T_CONTRACT_PURCHASE_INFO a1 where a1.IS_AVAILABLE = '1'");
				sb.append(" and a1.ORDER_STATE in ('10','11','2','3','5','6','7','8','9') and a.project_id=a1.project_id and UPPER(a1.ekko_lifnr_name) like '%" + queryValue.toUpperCase() + "%')");
			}
			// 销售合同
			else if (QueryField.CONTRACT_SALES_NO.equals(queryField))
			{
				sb.append(" and a.PROJECT_ID =(select a.PROJECT_ID from T_CONTRACT_SALES_INFO a where a.IS_AVAILABLE = '1' ");
				sb.append(" and a.ORDER_STATE in ('10','11','2','3','5','6','7','8','9') and a.CONTRACT_NO='" + queryValue + "')");
			}
			else if (QueryField.VBKD_IHREZ.equals(queryField))
			{
				sb.append(" and a.PROJECT_ID IN (select a.PROJECT_ID from T_CONTRACT_SALES_INFO a where a.IS_AVAILABLE = '1' ");
				sb.append(" and a.ORDER_STATE in ('10','11','2','3','5','6','7','8','9') and a.VBKD_IHREZ='" + queryValue + "')");
			}
			else if (QueryField.CONTRACT_SALES_NAME.equals(queryField))
			{
				sb.append(" and a.PROJECT_ID in(select distinct a.PROJECT_ID from T_CONTRACT_SALES_INFO a where a.IS_AVAILABLE = '1' ");
				sb.append(" and a.ORDER_STATE in ('10','11','2','3','5','6','7','8','9') and a.CONTRACT_NAME like '%" + queryValue + "%')");
			}
			else if (QueryField.CONTRACT_SALES_CUSNAME.equals(queryField))
			{
				sb.append(" and exists (select '' from T_CONTRACT_SALES_INFO a1 where a1.IS_AVAILABLE = '1'");
				sb.append(" and a1.ORDER_STATE in ('10','11','2','3','5','6','7','8','9') and a.project_id=a1.project_id and UPPER(a1.KUAGV_KUNNR_NAME) like '%" + queryValue.toUpperCase() + "%')");
			}
			// LJX 20100722 Add 加入收付款等。
			// 付款
			else if (QueryField.PAYMENT_NO.equals(queryField))
			{
				sb.append(" and a.PROJECT_ID in(select c.PROJECT_ID from YPAYMENT a left outer join YPAYMENTITEM b on a.PAYMENTID=b.PAYMENTID left outer join T_PROJECT_INFO c on b.PROJECT_NO=c.PROJECT_NO ");
				sb.append(" where b.PAYMENTITEMID is not null and c.IS_AVAILABLE = '1' and a.PAYMENTNO='" + queryValue + "')");
			}
			else if (QueryField.PAYMENT_SAPORDERNO.equals(queryField))
			{
				sb.append("  and a.PROJECT_ID in(select c.PROJECT_ID from YPAYMENT a left outer join YPAYMENTITEM b on a.PAYMENTID=b.PAYMENTID left outer join T_CONTRACT_PURCHASE_INFO c on b.CONTRACT_NO=c.CONTRACT_NO ");
				sb.append(" where b.PAYMENTITEMID is not null and c.IS_AVAILABLE = '1' and c.SAP_ORDER_NO='" + queryValue + "')");
			}
			// 收款
			else if (QueryField.COLLECT_NO.equals(queryField))
			{
				sb.append(" and a.PROJECT_ID in(select c.PROJECT_ID from YCOLLECT a left outer join YCOLLECTITEM b on a.collectid=b.collectid left outer join T_PROJECT_INFO c on b.PROJECT_NO=c.PROJECT_NO ");
				sb.append(" where b.COLLECTITEMID is not null and c.IS_AVAILABLE = '1' and a.COLLECTNO='" + queryValue + "')");
			}
			else if (QueryField.COLLECT_SAPORDERNO.equals(queryField))
			{
				sb.append(" and a.PROJECT_ID in(select c.PROJECT_ID from YCOLLECT a left outer join YCOLLECTITEM b on a.collectid=b.collectid left outer join T_CONTRACT_SALES_INFO c on b.CONTRACT_NO=c.CONTRACT_NO ");
				sb.append(" where b.COLLECTITEMID is not null and c.IS_AVAILABLE = '1' and c.SAP_ORDER_NO='" + queryValue + "')");
			}
			// 退款
			else if (QueryField.REFUNDMENTNO.equals(queryField))
			{
				sb.append(" and a.PROJECT_ID in(select c.PROJECT_ID from YREFUNDMENT a left outer join YREFUNDMENTITEM b on a.REFUNDMENTID=b.REFUNDMENTID left outer join T_PROJECT_INFO c on b.PROJECT_NO=c.PROJECT_NO ");
				sb.append(" where c.IS_AVAILABLE = '1' and a.REFUNDMENTNO='" + queryValue + "')");
			}
			else if (QueryField.REFUNDMENTVOUCHERNO.equals(queryField))
			{
				sb.append(" and a.PROJECT_ID in(select c.PROJECT_ID from YREFUNDMENT a left outer join YREFUNDMENTITEM b on a.REFUNDMENTID=b.REFUNDMENTID left outer join T_PROJECT_INFO c on b.PROJECT_NO=c.PROJECT_NO ");
				sb.append(" left outer join YVOUCHER d on a.REFUNDMENTID = d.BUSINESSID ");
				sb.append(" where d.voucherNO is not null and c.IS_AVAILABLE = '1' and d.VOUCHERNO='" + queryValue + "')");
			}
			// ////////销售合同、采购合同下 单据信息:
			// @@@@销售合同下,出仓、出单、收证、开票
			// 出仓、发货
			else if (QueryField.SHIP_NO.equals(queryField))
			{
				sb.append(" and a.PROJECT_ID = (select PROJECT_ID from T_CONTRACT_SALES_INFO where CONTRACT_SALES_ID in(");
				sb.append(" select a.CONTRACT_SALES_ID from T_SHIP_INFO a where a.IS_AVAILABLE = '1' and a.EXAMINE_STATE in ('2', '3', '5','6', '7', '8')");
				sb.append(" and  a.SHIP_NO='" + queryValue + "'))");
			}
			else if (QueryField.SAP_RETURN_NO2.equals(queryField))
			{
				sb.append(" and a.PROJECT_ID = (select PROJECT_ID from T_CONTRACT_SALES_INFO where CONTRACT_SALES_ID in(");
				sb.append(" select a.CONTRACT_SALES_ID from T_SHIP_INFO a where a.IS_AVAILABLE = '1' and a.EXAMINE_STATE in ('2', '3', '5','6', '7', '8')");
				sb.append(" and  a.SAP_RETURN_NO='" + queryValue + "'))");
			}
			// 出单
			else if (QueryField.INV_NO.equals(queryField))
			{
				sb.append(" and a.PROJECT_ID =(select PROJECT_ID from T_CONTRACT_SALES_INFO where CONTRACT_SALES_ID in(");
				sb.append(" select c.CONTRACT_SALES_ID from T_EXPORT_BILL_EXAM a, T_EXPORT_BILL_EXAM_D b, T_EXPORT_APPLY c ");
				sb.append(" where  a.IS_AVAILABLE = '1' and c.IS_AVAILABLE = '1' and a.EXPORT_BILL_EXAM_ID = b.EXPORT_BILL_EXAM_ID");
				sb.append(" and b.EXPORT_APPLY_ID = c.EXPORT_APPLY_ID and a.INV_NO='" + queryValue + "'))");
			}
			// 信用证开证、到证(收证)
			else if (QueryField.CREDIT_NO.equals(queryField))
			{
				// 开证
				sb.append(" and (a.PROJECT_ID =(select d.PROJECT_ID from T_CREDIT_CREATE b  left outer join  T_CREDIT_INFO a on a.CREDIT_ID = b.CREDIT_ID ");
				sb.append(" left outer join T_CONTRACT_PURCHASE_INFO c on b.CONTRACT_PURCHASE_ID=c.CONTRACT_PURCHASE_ID ");
				sb.append(" left outer join T_CONTRACT_GROUP d on c.CONTRACT_GROUP_ID = d.CONTRACT_GROUP_ID  ");
				sb.append(" where a.IS_AVAILABLE = '1'  and a.CREATE_OR_REC = '1'  and a.CREDIT_STATE in('15','11','3','4','5','10','12','13') and a.CREDIT_NO='" + queryValue + "')");
				// 收证
				sb.append(" or a.PROJECT_ID in (select a.PROJECT_ID from T_CONTRACT_SALES_INFO a ");
				sb.append(" where a.CONTRACT_SALES_ID in(select d.CONTRACT_SALES_ID from T_EXPORT_APPLY d where d.LCNO='" + queryValue + "'))");
				sb.append(" )");
			}
			// 内贸开票
			else if (QueryField.TAX_NO.equals(queryField))
			{
				sb.append(" and a.PROJECT_ID =(select PROJECT_ID from t_contract_sales_info where CONTRACT_SALES_ID in(");
				sb.append(" select a.CONTRACT_SALES_ID from T_BILL_APPLY a  where a.IS_AVAILABLE = '1'");
				sb.append(" and a.EXAMINE_STATE in ('2','3','5','6','7','8') and a.TAX_NO='" + queryValue + "'))");
			}
			else if (QueryField.SAP_BILL_NO.equals(queryField))
			{
				sb.append(" and a.PROJECT_ID =(select PROJECT_ID from t_contract_sales_info where CONTRACT_SALES_ID in(");
				sb.append(" select a.CONTRACT_SALES_ID from T_BILL_APPLY a  where a.IS_AVAILABLE = '1'");
				sb.append(" and a.EXAMINE_STATE in ('2','3','5','6','7','8') and a.SAP_BILL_NO='" + queryValue + "'))");
			}
			// 出口开票
			else if (QueryField.VBELN.equals(queryField))
			{
				sb.append(" and a.PROJECT_ID in(select a.PROJECT_ID from T_CONTRACT_SALES_INFO a where a.sap_order_no in(SELECT AUBEL from YGETVBRP WHERE VBELN='" + queryValue + "'))");
			}
			// @@@@采购合同下,进仓(收货)、到单、开证
			// 进仓单(收货)
			else if (QueryField.RECEIPT_NO.equals(queryField))
			{
				sb.append(" and a.PROJECT_NO =(select a.PROJECT_NO from T_RECEIPT_INFO a where a.IS_AVAILABLE = '1' and a.EXAMINE_STATE in ('2', '3', '5','6', '7', '8') and a.RECEIPT_NO='" + queryValue + "')");
			}
			else if (QueryField.SAP_RETURN_NO1.equals(queryField))
			{
				sb.append(" and a.PROJECT_NO =(select a.PROJECT_NO from T_RECEIPT_INFO a where a.IS_AVAILABLE = '1' and a.EXAMINE_STATE in ('2', '3', '5','6', '7', '8') and a.SAP_RETURN_NO='" + queryValue + "')");
			}
			// 到单
			else if (QueryField.PICK_LIST_NO.equals(queryField))
			{
				sb.append(" and a.PROJECT_ID in(select a.PROJECT_ID from T_CONTRACT_PURCHASE_INFO a where a.CONTRACT_PURCHASE_ID in(");
				sb.append(" select CONTRACT_PURCHASE_ID from T_PICK_LIST_INFO a where a.IS_AVAILABLE = '1' ");
				sb.append(" and a.EXAMINE_STATE in ('2', '3', '5', '6', '7', '8') and a.PICK_LIST_NO='" + queryValue + "'))");
			}
			// 发票校验
			else if (QueryField.BELNR.equals(queryField))
			{
				sb.append(" and a.PROJECT_ID in(select b.PROJECT_ID from YGETBELNR a left outer join t_contract_purchase_info b on a.ebeln=b.sap_order_no where a.belnr='" + queryValue + "')");
			}
		}

		if (StringUtils.isNotBlank(tradeType))
		{
			sb.append(" and a.TRADE_TYPE='" + tradeType + "'");
		}
		list = getColumnTreeList(sb, "");

		return list;
	}

	/**
	 * 判断原有数据是否存在
	 * @param sql
	 * @return
	 */
	public boolean isExists(String sql){
	    List list = this.getJdbcTemplate().queryForList(sql);
	    return list.size() > 0;
	}
	
	/**
	 * 更新原来凭证的冲销号
	 * @param vbeln
	 */
	public void updateSfakn(String vbeln,String sfakn){
		String strSql1 = "UPDATE  ygetvbrk vk SET vk.sfakn='"+sfakn+"'  WHERE  vk.vbeln='"+vbeln+"'";
		this.getJdbcTemplate().execute(strSql1);
	}
}
