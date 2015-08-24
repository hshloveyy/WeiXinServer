package com.infolion.xdss3.singleClear.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.infolion.platform.dpframework.core.dao.BaseJdbcDao;
import com.infolion.platform.dpframework.util.ExBeanUtils;
import com.infolion.platform.util.StringUtils;
import com.infolion.xdss3.payment.domain.ClearVoucherItemStruct;
import com.infolion.xdss3.singleClear.domain.ClearVoucherItem;
import com.infolion.xdss3.singleClear.domain.IKeyValue;
import com.infolion.xdss3.singleClear.domain.KeyValue;
import com.infolion.xdss3.voucher.domain.Voucher;
import com.infolion.xdss3.voucheritem.domain.VoucherItem;
/**
 * * <pre>
 * (客户全面清帐(CustomerClearAccountJdbcDao),JDBC 操作类
 * </pre>
 * 
 * @author zhongzh
 * @version 1.0
 * @see The author for more details
 * @since 1.0
 */

@Repository
public class CustomerClearAccountJdbcDao extends BaseJdbcDao{
	
	/***
	 * 取得除本次以外的款方有部分清的，客户清账ID
	 * @param titleid
	 * @param clearid,
	 * @return
	 */
	/**
	public List<IKeyValue> getPartCpIdsByCustomerClear(String titleid,String clearid,String collectitemid){
		List<IKeyValue> list = new ArrayList<IKeyValue>();
		StringBuffer sb = new StringBuffer();	
//		手工作账，只有单清账能清
		if(StringUtils.isNullBlank(collectitemid)){
			sb.append(" SELECT cs.customsclearid  AS clearid ,'1' AS type2 FROM ycustomsiclear cs ,yunclearcollect uc WHERE cs.customsclearid=uc.customsclearid "  );
			sb.append(" AND cs.businessstate IN ('3') AND uc.nowclearamount <> uc.amount  ");
			sb.append(" AND  uc.customertitleid ='" + titleid + "' AND cs.customsclearid <>  '" + clearid + "' ");
		}else{
	//		单清账
			sb.append(" SELECT cs.customsclearid AS clearid ,'1' AS type2 FROM ycustomsiclear cs ,yunclearcollect uc WHERE cs.customsclearid=uc.customsclearid "  );
			sb.append(" AND cs.businessstate IN ('3') AND uc.nowclearamount <> uc.amount  ");
			sb.append(" AND  uc.customertitleid ='" + titleid + "' AND cs.customsclearid <>  '" + clearid + "' ");
			sb.append("  UNION		  ");
	//		票清款
			sb.append("  SELECT bc.billclearid AS clearid ,'2' AS type2 FROM ybillclear bc ,ybillincollect bic WHERE bc.billclearid=bic.billclearid  ");
			sb.append("  AND bc.businessstate IN ('3') AND bic.nowclearamount <> bic.collectamount  and trim(bic.COLLECTITEMID) is not null ");
			sb.append("  AND bic.collectitemid = '" + collectitemid + "' AND bic.billclearid <> '" + clearid + "' ");
			sb.append(" UNION ");
	//		收款清票
			sb.append(" SELECT c.collectid  AS clearid ,'3' AS type2 FROM ycollect c,ycollectitem ci WHERE c.collectid=ci.collectid ");
			sb.append(" AND c.businessstate IN ('3') AND ( (ci.ASSIGNAMOUNT-ci.PREBILLAMOUNT-ci.actsuretybond) <> 0 OR ci.actsuretybond <> 0)  and trim(ci.COLLECTITEMID) is not null ");
			sb.append(" AND ci.collectitemid = '" + collectitemid + "' AND ci.collectid <> '" + clearid + "' ");
			sb.append(" UNION ");
	//		退款
			sb.append(" SELECT re.refundmentid  AS clearid , '5' AS type2 FROM yrefundment re ,yrefundmentitem rei WHERE re.refundmentid = rei.refundmentid  and trim(rei.COLLECTITEMID) is not null ");
			sb.append(" AND re.businessstate IN ('3') AND rei.pefundmentamount <> rei.refundmentamount ");
			sb.append(" AND rei.collectitemid = '" + collectitemid + "' AND rei.refundmentid <> '" + clearid + "' ");
		}
		List<Map> rowList = this.getJdbcTemplate().queryForList(sb.toString());
		log.debug("取得除本次以外的款方有部分清的，客户单清账ID:" + sb.toString());
		for (Map map : rowList){
			KeyValue keyValue = new KeyValue();
			keyValue.setKey(map.get("clearid").toString());
			keyValue.setValue(map.get("type2").toString());
			list.add(keyValue);
		}
		return list;
	}
	**/
	/***
	 * 取得除本次以外的有部分清的，客户清账ID
	 * @param titleid
	 * @param clearid,
	 * @return
	 */
	public List<IKeyValue> getPartIdsByCustomerClear(String titleid,String clearid,String collectitemIdOrBillno){
		List<IKeyValue> list = new ArrayList<IKeyValue>();
		StringBuffer sb = new StringBuffer();	
		sb.append("  SELECT DISTINCT *  FROM ( ");
	//		单清账
			sb.append(" SELECT cs.customsclearid AS clearid ,'1' AS type2 FROM ycustomsiclear cs ,yunclearcollect uc WHERE cs.customsclearid=uc.customsclearid "  );
			sb.append(" AND cs.businessstate IN ('3') AND uc.nowclearamount <> uc.amount  ");
			sb.append(" AND ( uc.customertitleid ='" + titleid + "' or ( uc.collectitemid = '" + collectitemIdOrBillno + "' and trim(uc.COLLECTITEMID) is not null ) ) AND cs.customsclearid <>  '" + clearid + "' ");
			sb.append("  UNION		  ");
	//		票清款
			sb.append("  SELECT bc.billclearid AS clearid ,'2' AS type2 FROM ybillclear bc ,ybillincollect bic WHERE bc.billclearid=bic.billclearid  ");
			sb.append("  AND bc.businessstate IN ('3') AND bic.nowclearamount <> bic.collectamount  and trim(bic.COLLECTITEMID) is not null ");
			sb.append("  AND bic.collectitemid = '" + collectitemIdOrBillno + "' AND bic.billclearid <> '" + clearid + "' ");
			sb.append(" UNION ");
	//		收款清票,有考虑承对兑票，在批的情况
			sb.append(" SELECT c.collectid  AS clearid ,'3' AS type2 FROM ycollect c,ycollectitem ci WHERE c.collectid=ci.collectid ");
			sb.append("  AND ( (ci.ASSIGNAMOUNT-ci.PREBILLAMOUNT-ci.suretybond) <> 0 OR ci.actsuretybond <> 0)  and trim(ci.COLLECTITEMID) is not null ");
			sb.append(" AND ci.collectitemid = '" + collectitemIdOrBillno + "' AND ci.collectid <> '" + clearid + "' ");
			sb.append(" and ( ( c.collecttype in ('11', '12')   and c.businessstate  in ('2', '3')  ) or ( c.collecttype not in ('11', '12') and c.businessstate in ('3') )) " );
			sb.append(" UNION ");
//			收款清票特殊情况有多条行项目，刚好一条全清，一条未清，只有取这条的付款单就好
			sb.append(" SELECT c.collectid  AS clearid ,'3' AS type2 FROM ycollect c,ycollectitem ci,YCOLLECTCBILL cb  WHERE c.collectid=ci.collectid and c.collectid=cb.collectid  ");
			sb.append("  AND ( (ci.ASSIGNAMOUNT-ci.PREBILLAMOUNT-ci.suretybond) = 0)  and trim(ci.COLLECTITEMID) is not null ");
			sb.append(" AND ci.collectitemid = '" + collectitemIdOrBillno + "' AND ci.collectid <> '" + clearid + "' ");
			sb.append(" and ( ( c.collecttype in ('11', '12')   and c.businessstate  in ('2', '3')  ) or ( c.collecttype not in ('11', '12') and c.businessstate in ('3') )) " );
			sb.append(" UNION ");
	//		退款
			sb.append(" SELECT re.refundmentid  AS clearid , '5' AS type2 FROM yrefundment re ,yrefundmentitem rei WHERE re.refundmentid = rei.refundmentid  and trim(rei.COLLECTITEMID) is not null ");
			sb.append(" AND re.businessstate IN ('3')  ");
			sb.append(" AND rei.collectitemid = '" + collectitemIdOrBillno + "' AND rei.refundmentid <> '" + clearid + "' ");
			sb.append(" UNION  ");
//	票方		(单清账)
			sb.append(" SELECT cs.customsclearid  AS clearid , '1' AS TYPE2  FROM ycustomsiclear cs ,yunclearcustbill ub WHERE cs.customsclearid=ub.customsclearid  ");
			sb.append("  AND cs.businessstate IN ('3') AND ub.clearamount <> ub.receivedamount    ");
			sb.append("  AND  ub.customertitleid ='"+titleid+"' AND cs.customsclearid <>  '"+clearid+"'   ");
//			票方（票清收款）
			sb.append(" UNION  ");
			sb.append(" select bc.billclearid  AS clearid, '2' AS TYPE2 from YBILLCLEARITEM bi ,YBILLCLEAR bc WHERE bi.billclearid = bc.billclearid  ");
			sb.append(" AND bc.businessstate IN ('3') AND bi.clearbillamount <> bi.receivableamount AND TRIM(bi.invoice) IS NOT  NULL  ");
			sb.append(" AND  bi.titleid = '"+titleid+"' AND bc.billclearid <> '"+clearid+"'  ");
//			票方收款清票
			
			sb.append(" UNION  ");
			sb.append(" select c.collectid AS clearid, '3' AS TYPE2 from YCOLLECTCBILL cb ,YCOLLECT c where c.collectid=cb.collectid  ");
			sb.append(" AND cb.clearamount <> cb.receivableamount AND TRIM(cb.billno) IS NOT NULL  ");
			sb.append(" AND cb.billno = '"+collectitemIdOrBillno+"' AND c.collectid <> '"+clearid+"'  ");
			sb.append(" and ( ( c.collecttype in ('11', '12')   and c.businessstate  in ('2', '3')  ) or ( c.collecttype not in ('11', '12') and c.businessstate in ('3') )) " );
			
			sb.append(" ) ");
		List<Map> rowList = this.getJdbcTemplate().queryForList(sb.toString());
		log.debug("取得除本次以外的款方有部分清的，客户清账ID:" + sb.toString());
		for (Map map : rowList){
			KeyValue keyValue = new KeyValue();
			keyValue.setKey(map.get("clearid").toString());
			keyValue.setValue(map.get("type2").toString());
			list.add(keyValue);
		}
		return list;
	}
	
	/***
	 * 取得本次有部分清的，款行项目titleID,
	 * 
	 * @param clearid 如 1122','ddd
	 * @return IKeyValue key 为titleid,value 为收款itemID ,value2 为客户清账ID
	 */
	/**
	public List<IKeyValue> getPartCpIdsByCustomerClear(String clearid){
		List<IKeyValue> list = new ArrayList<IKeyValue>();
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT uc.customertitleid,uc.collectitemid,cs.customsclearid AS clearid FROM ycustomsiclear cs ,yunclearcollect uc WHERE cs.customsclearid=uc.customsclearid  ");
		sb.append("  AND cs.businessstate IN ('3') AND uc.nowclearamount <> uc.amount   ");
		sb.append("  AND cs.customsclearid IN ('" + clearid + "') ");
		sb.append("  UNION ");
		sb.append("  SELECT '' AS customertitleid, bic.collectitemid,bc.billclearid AS clearid FROM ybillclear bc ,ybillincollect bic WHERE bc.billclearid=bic.billclearid  ");
		sb.append("  AND bc.businessstate IN ('3') AND bic.nowclearamount <> bic.collectamount ");
		sb.append("  AND bc.billclearid IN ('" + clearid + "') ");
		sb.append(" UNION ");
		sb.append(" SELECT '' AS customertitleid,ci.collectitemid,c.collectid AS clearid FROM ycollect c,ycollectitem ci WHERE c.collectid=ci.collectid ");
		sb.append(" AND c.businessstate IN ('3') AND ( (ci.ASSIGNAMOUNT-ci.PREBILLAMOUNT-ci.actsuretybond) <> 0 OR ci.actsuretybond <> 0) ");
		sb.append(" and  ci.collectid in ('" + clearid + "') ");
		sb.append(" UNION ");
		sb.append(" SELECT  '' AS customertitleid,rei.collectitemid, re.refundmentid AS clearid FROM yrefundment re ,yrefundmentitem rei WHERE re.refundmentid = rei.refundmentid ");
		sb.append(" AND re.businessstate IN ('3') AND rei.pefundmentamount <> rei.refundmentamount ");
		sb.append(" and rei.refundmentid  in ('" + clearid + "') ");
		
		List<Map> rowList = this.getJdbcTemplate().queryForList(sb.toString());
		log.debug("取得本次有部分清的，款行项目titleID:" + sb.toString());
		for (Map map : rowList){
			KeyValue keyValue = new KeyValue(); 
			keyValue.setKey(map.get("customertitleid").toString());
			keyValue.setValue(map.get("clearid").toString());
			keyValue.setValue2(map.get("collectitemid").toString());
			list.add(keyValue);
		}
		return list;
	}
	**/
	/***
	 * 取得本次有部分清的，款行项目titleID,
	 * 
	 * @param clearid 如 1122','ddd
	 * @return IKeyValue key 为titleid,value 为收款itemID ,value2 为客户清账ID
	 */
	public List<IKeyValue> getPartIdsByCustomerClear(String clearid){
		List<IKeyValue> list = new ArrayList<IKeyValue>();
		StringBuffer sb = new StringBuffer();
//		款方
		sb.append(" SELECT uc.customertitleid,uc.collectitemid as collectitemidOrBillno,cs.customsclearid AS clearid FROM ycustomsiclear cs ,yunclearcollect uc WHERE cs.customsclearid=uc.customsclearid  ");
		sb.append("  AND cs.businessstate IN ('3') AND uc.nowclearamount <> uc.amount   ");
		sb.append("  AND cs.customsclearid IN ('" + clearid + "') ");
		sb.append("  UNION ");
		sb.append("  SELECT '' AS customertitleid, bic.collectitemid  as collectitemidOrBillno,bc.billclearid AS clearid FROM ybillclear bc ,ybillincollect bic WHERE bc.billclearid=bic.billclearid  ");
		sb.append("  AND bc.businessstate IN ('3') AND bic.nowclearamount <> bic.collectamount ");
		sb.append("  AND bc.billclearid IN ('" + clearid + "') ");
		sb.append(" UNION ");
//		sb.append(" SELECT '' AS customertitleid,ci.collectitemid  as collectitemidOrBillno,c.collectid AS clearid FROM ycollect c,ycollectitem ci WHERE c.collectid=ci.collectid ");
//		sb.append(" AND c.businessstate IN ('3') AND ( (ci.ASSIGNAMOUNT-ci.PREBILLAMOUNT-ci.suretybond) <> 0 OR ci.actsuretybond <> 0) ");
//		sb.append(" and  ci.collectid in ('" + clearid + "') ");
		
		sb.append(" SELECT '' AS customertitleid,ci.collectitemid  as collectitemidOrBillno,c.collectid AS clearid FROM ycollect c,ycollectitem ci WHERE c.collectid=ci.collectid ");
		sb.append(" AND ( (ci.ASSIGNAMOUNT-ci.PREBILLAMOUNT-ci.suretybond) <> 0 OR ci.actsuretybond <> 0) ");
		sb.append(" and  ci.collectid in ('" + clearid + "') ");
		sb.append(" and ( ( c.collecttype in ('11', '12')   and c.businessstate  in ('2', '3')  ) or ( c.collecttype not in ('11', '12') and c.businessstate in ('3') )) " );
		sb.append(" UNION ");
		sb.append(" SELECT  '' AS customertitleid,rei.collectitemid  as collectitemidOrBillno, re.refundmentid AS clearid FROM yrefundment re ,yrefundmentitem rei WHERE re.refundmentid = rei.refundmentid ");
		sb.append(" AND re.businessstate IN ('3')  ");
		sb.append(" and rei.refundmentid  in ('" + clearid + "') ");
		sb.append(" UNION  ");
//		票方
		sb.append(" SELECT ub.customertitleid,ub.billno  as collectitemidOrBillno,cs.customsclearid  AS clearid FROM ycustomsiclear cs ,yunclearcustbill ub WHERE cs.customsclearid=ub.customsclearid ");
		sb.append("  AND cs.businessstate IN ('3') AND ub.clearamount <> ub.receivedamount   ");
		sb.append("  AND ub.customsclearid in ('"+clearid+"') ");
		sb.append(" UNION ");
		sb.append(" SELECT bi.titleid AS customertitleid,bi.invoice  as collectitemidOrBillno, bc.billclearid  AS clearid from YBILLCLEARITEM bi ,YBILLCLEAR bc WHERE bi.billclearid = bc.billclearid ");
		sb.append(" AND bc.businessstate IN ('3') AND bi.clearbillamount <> bi.receivableamount AND TRIM(bi.invoice) IS NOT  NULL ");
		sb.append(" AND  bc.billclearid in ('"+clearid+"') ");
		sb.append(" UNION ");
//		sb.append(" SELECT '' AS customertitleid, cb.billno  as collectitemidOrBillno, c.collectid AS clearid from YCOLLECTCBILL cb ,YCOLLECT c where c.collectid=cb.collectid ");
//		sb.append(" AND c.businessstate IN ('3') AND cb.clearamount <> cb.receivableamount AND TRIM(cb.billno) IS NOT NULL ");
//		sb.append(" AND c.collectid in ('"+clearid+"') ");
		
		sb.append(" SELECT '' AS customertitleid, cb.billno  as collectitemidOrBillno, c.collectid AS clearid from YCOLLECTCBILL cb ,YCOLLECT c where c.collectid=cb.collectid ");
		sb.append(" AND cb.clearamount <> cb.receivableamount AND TRIM(cb.billno) IS NOT NULL ");
		sb.append(" AND c.collectid in ('"+clearid+"') ");
		sb.append(" and ( ( c.collecttype in ('11', '12')   and c.businessstate  in ('2', '3')  ) or ( c.collecttype not in ('11', '12') and c.businessstate in ('3') )) " );
		
		List<Map> rowList = this.getJdbcTemplate().queryForList(sb.toString());
		log.debug("取得本次有部分清的，款票行项目titleID:" + sb.toString());
		for (Map map : rowList){
			KeyValue keyValue = new KeyValue(); 
			if(null != map.get("customertitleid")){
				keyValue.setKey(map.get("customertitleid").toString());
			}
			if(null != map.get("collectitemidOrBillno")){
				keyValue.setValue(map.get("collectitemidOrBillno").toString());
			}
			if(null != map.get("clearid")){
				keyValue.setValue2(map.get("clearid").toString());
			}
			list.add(keyValue);
		}
		return list;
	}
	
	/***
	 * 取得除本次以外的票方有部分清的，客户单清账ID
	 * @param titleid
	 * @param clearid
	 * @return
	 */
	/**
	public List<IKeyValue> getPartBillIdsByCustomerClear(String titleid,String billno,String clearid){
		List<IKeyValue> list = new ArrayList<IKeyValue>();
		StringBuffer sb = new StringBuffer();
		
		sb.append(" SELECT cs.customsclearid  AS clearid , '1' AS TYPE2  FROM ycustomsiclear cs ,yunclearcustbill ub WHERE cs.customsclearid=ub.customsclearid  ");
		sb.append("  AND cs.businessstate IN ('3') AND ub.clearamount <> ub.receivedamount    ");
		sb.append("  AND  ub.customertitleid ='"+titleid+"' AND cs.customsclearid <>  '"+clearid+"'   ");
		sb.append(" UNION  ");
		sb.append(" select bc.billclearid  AS clearid, '2' AS TYPE2 from YBILLCLEARITEM bi ,YBILLCLEAR bc WHERE bi.billclearid = bc.billclearid  ");
		sb.append(" AND bc.businessstate IN ('3') AND bi.clearbillamount <> bi.receivableamount AND TRIM(bi.invoice) IS NOT  NULL  ");
		sb.append(" AND  bi.titleid = '"+titleid+"' AND bc.billclearid <> '"+clearid+"'  ");
		if(!StringUtils.isNullBlank(billno)){
			sb.append(" UNION  ");
			sb.append(" select c.collectid AS clearid, '3' AS TYPE2 from YCOLLECTCBILL cb ,YCOLLECT c where c.collectid=cb.collectid  ");
			sb.append(" AND c.businessstate IN ('3') AND cb.clearamount <> cb.receivableamount AND TRIM(cb.billno) IS NOT NULL  ");
			sb.append(" AND cb.billno = '"+billno+"' AND c.collectid <> '"+clearid+"'  ");
		}
		List<Map> rowList = this.getJdbcTemplate().queryForList(sb.toString());
		log.debug("取得除本次以外的票方有部分清的，客户单清账ID:" + sb.toString());
		for (Map map : rowList){
			KeyValue keyValue = new KeyValue(); 
			keyValue.setKey(map.get("clearid").toString());
			keyValue.setValue(map.get("TYPE2").toString());
			list.add(keyValue);
		}
		return list;
	}
	**/
	/***
	 * 取得本次有部分清的，票行项目titleID
	 * 
	 * @param clearid 如 1122','ddd
	 * @return IKeyValue key 为titleid,value 为billno value2 为客户清账ID
	 */
	/**
	public List<IKeyValue> getPartBillIdsByCustomerClear(String clearid){
		List<IKeyValue> list = new ArrayList<IKeyValue>();
		StringBuffer sb = new StringBuffer();	
		sb.append(" SELECT ub.customertitleid,ub.billno,cs.customsclearid  AS clearid FROM ycustomsiclear cs ,yunclearcustbill ub WHERE cs.customsclearid=ub.customsclearid ");
		sb.append("  AND cs.businessstate IN ('3') AND ub.clearamount <> ub.receivedamount   ");
		sb.append("  AND ub.customsclearid in ('"+clearid+"') ");
		sb.append(" UNION ");
		sb.append(" SELECT bi.titleid AS customertitleid,bi.invoice AS billno, bc.billclearid  AS clearid from YBILLCLEARITEM bi ,YBILLCLEAR bc WHERE bi.billclearid = bc.billclearid ");
		sb.append(" AND bc.businessstate IN ('3') AND bi.clearbillamount <> bi.receivableamount AND TRIM(bi.invoice) IS NOT  NULL ");
		sb.append(" AND  bc.billclearid in ('"+clearid+"') ");
		sb.append(" UNION ");
		sb.append(" SELECT '' AS customertitleid, cb.billno, c.collectid AS clearid from YCOLLECTCBILL cb ,YCOLLECT c where c.collectid=cb.collectid ");
		sb.append(" AND c.businessstate IN ('3') AND cb.clearamount <> cb.receivableamount AND TRIM(cb.billno) IS NOT NULL ");
		sb.append(" AND c.collectid in ('"+clearid+"') ");
		
		List<Map> rowList = this.getJdbcTemplate().queryForList(sb.toString());
		log.debug("取得本次有部分清的，票行项目titleID:" + sb.toString());
		for (Map map : rowList){
			KeyValue keyValue = new KeyValue(); 
			keyValue.setKey(map.get("customertitleid").toString());
			keyValue.setValue(map.get("billno").toString());
			keyValue.setValue2(map.get("clearid").toString());
			list.add(keyValue);
		}
		return list;
	}	
	**/
	/***
	 * 
	 * @param sql 如SELECT bic.collectitemid FROM ybillincollect bic WHERE bic.billclearid IN (''),或者businessitemid
	 * @return
	 */
	public List<ClearVoucherItem> getVoucherItem(String sql,boolean isclass){
		List<ClearVoucherItem> list = new ArrayList<ClearVoucherItem>();
		StringBuffer sb = new StringBuffer();	
		sb.append(" SELECT distinct v.voucherno,v.companycode,v.fiyear,TRIM(TO_CHAR(TRIM(vi.rownumber),'000')) AS rownumber,vi.businessitemid FROM yvoucheritem vi ,yvoucher v ");
		sb.append(" WHERE v.voucherid=vi.voucherid AND v.processstate <> '-1' AND TRIM(v.voucherno) IS NOT NULL ");
		if(isclass){
			sb.append(" and v.voucherclass in ('1','2','4') ");
		}
		sb.append(" AND v.bstat <> 'A'  and trim(vi.subject) IS NOT  NULL  AND vi.businessitemid IN  ( ");
		sb.append(sql);
		sb.append( " ) ");
		List<Map> rowList = this.getJdbcTemplate().queryForList(sb.toString());
		log.debug("取得所有的voucheritem:" + sb.toString());
		for (Map map : rowList){
			ClearVoucherItem clearVoucherItem = new ClearVoucherItem();
			clearVoucherItem.setVoucherno(map.get("voucherno").toString());
			clearVoucherItem.setCompanycode(map.get("companycode").toString());
			clearVoucherItem.setYear(map.get("fiyear").toString());
			clearVoucherItem.setRowno(map.get("rownumber").toString());
			clearVoucherItem.setBusinessitemid(map.get("businessitemid").toString());
			list.add(clearVoucherItem);
		}
		return list;
	}
	
	public List<ClearVoucherItem> getVoucherItemByTitle(String ids){
		List<ClearVoucherItem> list = new ArrayList<ClearVoucherItem>();
		StringBuffer sb = new StringBuffer();	
		sb.append(" SELECT ct.belnr,ct.gjahr,ct.bukrs,ct.buzei,ct.customertitleid FROM ycustomertitle ct WHERE ct.customertitleid IN ("+ids+") ");
		List<Map> rowList = this.getJdbcTemplate().queryForList(sb.toString());
		log.debug("取得所有的voucheritemByTitle:" + sb.toString());
		for (Map map : rowList){
			ClearVoucherItem clearVoucherItem = new ClearVoucherItem();
			clearVoucherItem.setVoucherno(map.get("belnr").toString());
			clearVoucherItem.setCompanycode(map.get("bukrs").toString());
			clearVoucherItem.setYear(map.get("gjahr").toString());
			clearVoucherItem.setRowno(map.get("buzei").toString());
			clearVoucherItem.setBusinessitemid(map.get("customertitleid").toString());
			list.add(clearVoucherItem);
		}
		return list;
	}
	/***
	 * 取得部分清出的汇损和调整金
	 * @return
	 */
	public List<ClearVoucherItem>  getProfitAndLossByPart(String ids){
		List<ClearVoucherItem> list = new ArrayList<ClearVoucherItem>();
		StringBuffer sb = new StringBuffer();	
		sb.append(" SELECT distinct v.voucherno,v.companycode,v.fiyear,TRIM(TO_CHAR(TRIM(vi.rownumber),'000')) AS rownumber,v.businessid FROM yvoucheritem vi ,yvoucher v  ");
		sb.append(" WHERE v.voucherid=vi.voucherid AND v.processstate <> '-1'  ");
		sb.append(" AND trim(vi.taxcode) IS NOT NULL AND v.bstat <> 'A' and  vi.checkcode <> '40' AND vi.checkcode <> '50' AND v.businessid IN  ('"+ids+"')");
		List<Map> rowList = this.getJdbcTemplate().queryForList(sb.toString());
		log.debug("取得所有的voucheritem:" + sb.toString());
		for (Map map : rowList){
			ClearVoucherItem clearVoucherItem = new ClearVoucherItem();
			clearVoucherItem.setVoucherno(map.get("voucherno").toString());
			clearVoucherItem.setCompanycode(map.get("companycode").toString());
			clearVoucherItem.setYear(map.get("fiyear").toString());
			clearVoucherItem.setRowno(map.get("rownumber").toString());
			clearVoucherItem.setBusinessid(map.get("businessid").toString());
			list.add(clearVoucherItem);
		}
		return list;
	}
	/***
	 * 取得单清账，收款行项目。
	 * @param clid
	 * @return
	 */
	public List<Map> getunclearcollect(String clid){
		StringBuffer sb = new StringBuffer();	
		sb.append("SELECT uc.customertitleid,uc.collectitemid FROM yunclearcollect uc WHERE trim(uc.collectitemid) is not null and  uc.customsclearid IN ( '");
		sb.append(clid);
		sb.append("')");
		List<Map> rowList = this.getJdbcTemplate().queryForList(sb.toString());
		log.debug("取得取得单清账，收款行项目的:" + sb.toString());
		return rowList;
	}
	
	/***
	 * 取得单清账，票方付款行项目。
	 * @param clid
	 * @return
	 */
	public List<Map> getunclearPaymentByCustomer(String clid){
		StringBuffer sb = new StringBuffer();	
		sb.append("SELECT uc.customertitleid,uc.paymentitemid FROM yunclearcustbill uc WHERE  trim(uc.paymentitemid) is not null and uc.customsclearid IN ( '");
		sb.append(clid);
		sb.append("')");
		List<Map> rowList = this.getJdbcTemplate().queryForList(sb.toString());
		log.debug("取得单清账，票方付款行项目:" + sb.toString());
		return rowList;
	}
	
	public List<ClearVoucherItem> getVoucherItemByCollectid(String businessid,String voucherclass)
	{
		List<ClearVoucherItem> vilist = new ArrayList<ClearVoucherItem>();
		String voucherclass2 = voucherclass.replaceAll(",", "','");		
		String strSql = " SELECT distinct v.voucherid, v.voucherno,v.companycode,v.fiyear,vi.amount2,TRIM(TO_CHAR(TRIM(vi.rownumber),'000')) AS rownumber,vi.businessitemid from yvoucheritem vi, yvoucher v where v.voucherid = vi.voucherid and vi.businessitemid in (select ci.collectitemid from ycollectitem ci where ci.collectid='"+businessid+"') and v.voucherclass in ('"+voucherclass2+"')";
		log.debug("根据业务ID取得凭证明细:" + strSql);
		
		List<Map> rowList = this.getJdbcTemplate().queryForList(strSql);
		for (Map map : rowList)
		{
			ClearVoucherItem clearVoucherItem = new ClearVoucherItem();
			clearVoucherItem.setVoucherno(map.get("voucherno").toString());
			clearVoucherItem.setCompanycode(map.get("companycode").toString());
			clearVoucherItem.setYear(map.get("fiyear").toString());
			clearVoucherItem.setRowno(map.get("rownumber").toString());
			clearVoucherItem.setBusinessitemid(map.get("businessitemid").toString());	
			clearVoucherItem.setVoucherid(map.get("voucherid").toString());
			clearVoucherItem.setBwbje(new BigDecimal(map.get("amount2").toString()));
			vilist.add(clearVoucherItem);
			
		}
		return vilist;
	}
	
	public List<ClearVoucherItem> getVoucherItemByRefmentid(String businessid,String voucherclass)
	{
		List<ClearVoucherItem> vilist = new ArrayList<ClearVoucherItem>();
		String voucherclass2 = voucherclass.replaceAll(",", "','");		
		String strSql = " SELECT distinct v.voucherid, v.voucherno,v.companycode,v.fiyear,vi.amount2,TRIM(TO_CHAR(TRIM(vi.rownumber),'000')) AS rownumber,vi.businessitemid from yvoucheritem vi, yvoucher v where v.voucherid = vi.voucherid and vi.businessitemid in (SELECT ri.refundmentitemid FROM yrefundmentitem ri WHERE ri.refundmentid='"+businessid+"') and v.voucherclass in ('"+voucherclass2+"')";
		log.debug("根据业务ID取得凭证明细:" + strSql);
		
		List<Map> rowList = this.getJdbcTemplate().queryForList(strSql);
		for (Map map : rowList)
		{
			ClearVoucherItem clearVoucherItem = new ClearVoucherItem();
			clearVoucherItem.setVoucherno(map.get("voucherno").toString());
			clearVoucherItem.setCompanycode(map.get("companycode").toString());
			clearVoucherItem.setYear(map.get("fiyear").toString());
			clearVoucherItem.setRowno(map.get("rownumber").toString());
			clearVoucherItem.setBusinessitemid(map.get("businessitemid").toString());	
			clearVoucherItem.setVoucherid(map.get("voucherid").toString());
			clearVoucherItem.setBwbje(new BigDecimal(map.get("amount2").toString()));
			vilist.add(clearVoucherItem);
			
		}
		return vilist;
	}
	
	public List<ClearVoucherItem> getCollectVoucherItemByRefmentid(String businessid,String voucherclass)
	{
		List<ClearVoucherItem> vilist = new ArrayList<ClearVoucherItem>();
		String voucherclass2 = voucherclass.replaceAll(",", "','");		
		String strSql = " SELECT distinct v.voucherid, v.voucherno,v.companycode,v.fiyear,TRIM(TO_CHAR(TRIM(vi.rownumber),'000')) AS rownumber,vi.businessitemid from yvoucheritem vi, yvoucher v where v.voucherid = vi.voucherid and vi.businessitemid in (SELECT ri.collectitemid FROM yrefundmentitem ri WHERE ri.refundmentid='"+businessid+"') and v.voucherclass in ('"+voucherclass2+"')";
		log.debug("根据业务ID取得凭证明细:" + strSql);
		
		List<Map> rowList = this.getJdbcTemplate().queryForList(strSql);
		for (Map map : rowList)
		{
			ClearVoucherItem clearVoucherItem = new ClearVoucherItem();
			clearVoucherItem.setVoucherno(map.get("voucherno").toString());
			clearVoucherItem.setCompanycode(map.get("companycode").toString());
			clearVoucherItem.setYear(map.get("fiyear").toString());
			clearVoucherItem.setRowno(map.get("rownumber").toString());
			clearVoucherItem.setBusinessitemid(map.get("businessitemid").toString());	
			clearVoucherItem.setVoucherid(map.get("voucherid").toString());
			vilist.add(clearVoucherItem);
			
		}
		return vilist;
	}
}
