package com.infolion.xdss3.singleClear.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.infolion.platform.dpframework.core.dao.BaseJdbcDao;
import com.infolion.xdss3.singleClear.domain.ClearVoucherItem;
import com.infolion.xdss3.singleClear.domain.IKeyValue;
import com.infolion.xdss3.singleClear.domain.KeyValue;
/**
 * * <pre>
 * (供应商全面清帐(SupplierClearAccountJdbcDao),JDBC 操作类
 * </pre>
 * 
 * @author zhongzh
 * @version 1.0
 * @see The author for more details
 * @since 1.0
 */

@Repository
public class SupplierClearAccountJdbcDao extends BaseJdbcDao{
	
	/***
	 * 取得除本次以外的有部分清的，供应商清账ID
	 * @param titleid
	 * @param clearid,
	 * @return
	 */
	public List<IKeyValue> getPartIdsBySupplierClear(String titleid,String clearid,String paymentitemIdOrBillno){
		List<IKeyValue> list = new ArrayList<IKeyValue>();
		StringBuffer sb = new StringBuffer();	
		sb.append("  SELECT DISTINCT *  FROM ( ");
	//		单清账
			sb.append(" SELECT sc.suppliersclearid AS clearid ,'6' AS type2 FROM ysuppliersiclear sc,yunclearpayment up where sc.suppliersclearid=up.suppliersclearid "  );
			sb.append(" AND sc.businessstate IN ('4','7') AND up.nowclearamount <> up.paymentamount  ");
			sb.append(" AND ( up.vendortitleid ='" + titleid + "' or (up.paymentitemid = '" + paymentitemIdOrBillno + "' and trim(up.paymentitemid) is not null ) ) AND sc.suppliersclearid <>  '" + clearid + "' ");
			sb.append("  UNION		  ");
	//		票清款
			sb.append("  SELECT bc.billclearid AS clearid ,'7' AS type2 FROM ybillclear bc,ybillinpayment bp where bc.billclearid=bp.billclearid  ");
			sb.append("  AND bc.businessstate IN ('4','7') AND  bp.nowclearamount <> bp.paymentamount  and trim(bp.paymentitemid) is not null ");
			sb.append("  AND bp.paymentitemid = '" + paymentitemIdOrBillno + "' AND bp.billclearid <> '" + clearid + "' ");
			sb.append(" UNION ");
	//		付款清票,
//			sb.append(" SELECT  p.paymentid  AS clearid ,'4' AS type2 FROM ypayment p ,ypaymentitem pi where p.paymentid=pi.paymentid  ");
//			sb.append(" AND p.businessstate IN ('4','7') AND  pi.ASSIGNAMOUNT-pi.PREPAYAMOUNT <> 0   and trim(pi.paymentitemid) is not null ");
//			sb.append(" AND pi.paymentitemid = '" + paymentitemIdOrBillno + "' AND pi.paymentid <> '" + clearid + "' ");
//			sb.append(" UNION ");
			sb.append(" SELECT  p.paymentid  AS clearid ,'4' AS type2 FROM ypayment p ,ypaymentitem pi where p.paymentid=pi.paymentid  ");
			sb.append("  AND  pi.ASSIGNAMOUNT-pi.PREPAYAMOUNT <> 0   and trim(pi.paymentitemid) is not null ");
			sb.append(" AND pi.paymentitemid = '" + paymentitemIdOrBillno + "' AND pi.paymentid <> '" + clearid + "' ");
			sb.append(" and ( ( p.businessstate not in ('3', '4','7')   and p.paymenttype in ('06', '07')) " );
			sb.append(" or ( p.paymenttype not in ('06', '07') and p.businessstate in ('4','7') ) " );
			sb.append(" or (p.pay_type in ('2') and p.businessstate not in ('3','4','7')) ");
			sb.append(" or ( p.pay_type not in ('2') and p.businessstate  in ('4','7')) )    ");
			sb.append(" UNION ");
//			付款清票特殊情况有多条行项目，刚好一条全清，一条未清，只有取这条的付款单就好
			sb.append(" SELECT  p.paymentid  AS clearid ,'4' AS type2 FROM ypayment p ,ypaymentitem pi,ypaymentcbill pb  where p.paymentid=pi.paymentid and   p.paymentid=pb.paymentid  ");
			sb.append("  AND  pi.ASSIGNAMOUNT-pi.PREPAYAMOUNT = 0   and trim(pi.paymentitemid) is not null ");
			sb.append(" AND pi.paymentitemid = '" + paymentitemIdOrBillno + "' AND pi.paymentid <> '" + clearid + "' ");
			sb.append(" and ( ( p.businessstate not in ('3', '4','7')   and p.paymenttype in ('06', '07')) " );
			sb.append(" or ( p.paymenttype not in ('06', '07') and p.businessstate in ('4','7') ) " );
			sb.append(" or (p.pay_type in ('2') and p.businessstate not in ('3','4','7')) ");
			sb.append(" or ( p.pay_type not in ('2') and p.businessstate  in ('4','7')) )    ");
			sb.append(" UNION ");
	//		退款
			sb.append(" SELECT re.refundmentid  AS clearid , '8' AS type2 FROM yrefundment re ,yrefundmentitem rei WHERE re.refundmentid = rei.refundmentid  and trim(rei.paymentitemid) is not null ");
			sb.append(" AND re.businessstate IN ('3') ");
			sb.append(" AND rei.paymentitemid = '" + paymentitemIdOrBillno + "' AND rei.refundmentid <> '" + clearid + "' ");
			sb.append(" UNION  ");
//	票方		(单清账)
			sb.append(" SELECT sc.suppliersclearid  AS clearid , '6' AS TYPE2  FROM ysuppliersiclear sc,yunclearsuppbill ub where sc.suppliersclearid=ub.suppliersclearid  ");
			sb.append("  AND sc.businessstate IN ('4','7') AND  ub.clearamount <> ub.receivableamount    ");
			sb.append("  AND  ub.vendortitleid ='"+titleid+"' AND sc.suppliersclearid  <>  '"+clearid+"'   ");
//			票方（票清付款）
			sb.append(" UNION  ");
			sb.append(" select bc.billclearid  AS clearid, '7' AS TYPE2 from YBILLCLEARITEM bi ,YBILLCLEAR bc WHERE bi.billclearid = bc.billclearid  ");
			sb.append(" AND bc.businessstate IN ('4','7') AND bi.clearbillamount <> bi.receivableamount AND TRIM(bi.invoice) IS NOT  NULL  ");
			sb.append(" AND  bi.titleid = '"+titleid+"' AND bc.billclearid <> '"+clearid+"'  ");
//			票方付款清票
			
			sb.append(" UNION  ");
//			sb.append(" select p.paymentid  AS clearid, '4' AS TYPE2 from ypayment p ,ypaymentcbill pb where p.paymentid=pb.paymentid  ");
//			sb.append(" AND p.businessstate IN ('4','7') AND pb.CLEARAMOUNT2 <> pb.PAYABLEAMOUNT AND TRIM(pb.billno) IS NOT NULL  ");
//			sb.append(" AND pb.billno = '"+paymentitemIdOrBillno+"' AND p.paymentid <> '"+clearid+"'  ");
			
			sb.append(" select p.paymentid  AS clearid, '4' AS TYPE2 from ypayment p ,ypaymentcbill pb where p.paymentid=pb.paymentid  ");
			sb.append(" AND pb.CLEARAMOUNT2 <> pb.PAYABLEAMOUNT AND TRIM(pb.billno) IS NOT NULL  ");
			sb.append(" AND pb.billno = '"+paymentitemIdOrBillno+"' AND p.paymentid <> '"+clearid+"'  ");
			sb.append(" and ( ( p.businessstate not in ('3', '4','7')   and p.paymenttype in ('06', '07')) " );
			sb.append(" or ( p.paymenttype not in ('06', '07') and p.businessstate in ('4','7') )" );
			sb.append(" or (p.pay_type in ('2') and p.businessstate not in ('3','4','7')) ");
			sb.append(" or ( p.pay_type not in ('2') and p.businessstate  in ('4','7')) )    ");
			
			sb.append(" ) ");
		List<Map> rowList = this.getJdbcTemplate().queryForList(sb.toString());
		log.debug("取得除本次以外的款方有部分清的，供应商清账ID:" + sb.toString());
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
	 * @return IKeyValue key 为titleid,value 为付款itemID ,value2 为供应商清账ID
	 */
	public List<IKeyValue> getPartIdsBySupplierClear(String clearid){
		List<IKeyValue> list = new ArrayList<IKeyValue>();
		StringBuffer sb = new StringBuffer();
//		款方
		sb.append(" SELECT up.vendortitleid,up.paymentitemid as paymentitemidOrBillno,sc.suppliersclearid AS clearid FROM ysuppliersiclear sc,yunclearpayment up where sc.suppliersclearid=up.suppliersclearid  ");
		sb.append("  AND sc.businessstate IN ('4','7') AND up.nowclearamount <> up.paymentamount    ");
		sb.append("  AND sc.suppliersclearid IN ('" + clearid + "') ");
		sb.append("  UNION ");
		sb.append("  SELECT '' AS vendortitleid, bp.paymentitemid  as paymentitemidOrBillno,bc.billclearid AS clearid FROM ybillclear bc,ybillinpayment bp where bc.billclearid=bp.billclearid   ");
		sb.append("  AND bc.businessstate IN ('4','7') AND  bp.nowclearamount <> bp.paymentamount   ");
		sb.append("  AND bc.billclearid IN ('" + clearid + "') ");
		sb.append(" UNION ");
		sb.append(" SELECT '' AS vendortitleid,pi.paymentitemid  as paymentitemidOrBillno,p.paymentid  AS clearid FROM ypayment p ,ypaymentitem pi where p.paymentid=pi.paymentid ");
		sb.append("  AND  pi.ASSIGNAMOUNT-pi.PREPAYAMOUNT <> 0 ");
		sb.append(" and  pi.paymentid in ('" + clearid + "') ");
		sb.append(" and ( ( p.businessstate not in ('3', '4','7')   and p.paymenttype in ('06', '07')) " );
		sb.append(" or ( p.paymenttype not in ('06', '07') and p.businessstate in ('4','7') ) " );
		sb.append(" or (p.pay_type in ('2') and p.businessstate not in ('3','4','7')) ");
		sb.append(" or ( p.pay_type not in ('2') and p.businessstate  in ('4','7')) )    ");
		sb.append(" UNION ");
		sb.append(" SELECT  '' AS vendortitleid,rei.paymentitemid  as paymentitemidOrBillno, re.refundmentid AS clearid FROM yrefundment re ,yrefundmentitem rei WHERE re.refundmentid = rei.refundmentid ");
		sb.append(" AND re.businessstate IN ('3') ");
		sb.append(" and rei.refundmentid  in ('" + clearid + "') ");
		sb.append(" UNION  ");
//		票方
		sb.append(" SELECT ub.vendortitleid,ub.invoice  as paymentitemidOrBillno,sc.suppliersclearid   AS clearid FROM ysuppliersiclear sc,yunclearsuppbill ub where sc.suppliersclearid=ub.suppliersclearid  ");
		sb.append("  AND sc.businessstate IN ('4','7') AND  ub.clearamount <> ub.receivableamount  ");
		sb.append("  AND ub.suppliersclearid in ('"+clearid+"') ");
		sb.append(" UNION ");
		sb.append(" SELECT bi.titleid AS vendortitleid,bi.invoice  as paymentitemidOrBillno, bc.billclearid  AS clearid from YBILLCLEARITEM bi ,YBILLCLEAR bc WHERE bi.billclearid = bc.billclearid ");
		sb.append(" AND bc.businessstate IN ('4','7') AND bi.clearbillamount <> bi.receivableamount AND TRIM(bi.invoice) IS NOT  NULL ");
		sb.append(" AND  bc.billclearid in ('"+clearid+"') ");
		sb.append(" UNION ");
		sb.append(" SELECT pb.billid AS vendortitleid, pb.billno  as paymentitemidOrBillno, p.paymentid AS clearid from ypayment p ,ypaymentcbill pb where p.paymentid=pb.paymentid ");
		sb.append(" AND pb.CLEARAMOUNT2 <> pb.PAYABLEAMOUNT AND TRIM(pb.billno) IS NOT NULL  ");
		sb.append(" AND p.paymentid in ('"+clearid+"') ");
		sb.append(" and ( ( p.businessstate not in ('3', '4','7')   and p.paymenttype in ('06', '07')) " );
		sb.append(" or ( p.paymenttype not in ('06', '07') and p.businessstate in ('4','7') ) " );
		sb.append(" or (p.pay_type in ('2') and p.businessstate not in ('3','4','7')) ");
		sb.append(" or ( p.pay_type not in ('2') and p.businessstate  in ('4','7')) )    ");
		
		List<Map> rowList = this.getJdbcTemplate().queryForList(sb.toString());
		log.debug("取得本次有部分清的，供应商款票行项目titleID:" + sb.toString());
		for (Map map : rowList){
			KeyValue keyValue = new KeyValue(); 
			if(null != map.get("vendortitleid")){
				keyValue.setKey(map.get("vendortitleid").toString());
			}
			if(null != map.get("paymentitemidOrBillno")){
				keyValue.setValue(map.get("paymentitemidOrBillno").toString());
			}
			if(null != map.get("clearid")){
				keyValue.setValue2(map.get("clearid").toString());
			}
			list.add(keyValue);
		}
		return list;
	}
	
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
		sb.append(" SELECT vt.belnr,vt.gjahr,vt.bukrs,vt.buzei,vt.vendortitleid FROM yvendortitle vt WHERE vt.vendortitleid IN ("+ids+") ");
		List<Map> rowList = this.getJdbcTemplate().queryForList(sb.toString());
		log.debug("取得所有的voucheritemByTitle:" + sb.toString());
		for (Map map : rowList){
			ClearVoucherItem clearVoucherItem = new ClearVoucherItem();
			clearVoucherItem.setVoucherno(map.get("belnr").toString());
			clearVoucherItem.setCompanycode(map.get("bukrs").toString());
			clearVoucherItem.setYear(map.get("gjahr").toString());
			clearVoucherItem.setRowno(map.get("buzei").toString());
			clearVoucherItem.setBusinessitemid(map.get("vendortitleid").toString());
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
	public List<Map> getunclearpayment(String clid){
		StringBuffer sb = new StringBuffer();	
		sb.append("SELECT uc.vendortitleid,uc.paymentitemid FROM yunclearpayment uc WHERE trim(uc.paymentitemid) is not null and  uc.suppliersclearid IN ( '");
		sb.append(clid);
		sb.append("')");
		List<Map> rowList = this.getJdbcTemplate().queryForList(sb.toString());
		log.debug("取得取得单清账，收款行项目的:" + sb.toString());
		return rowList;
	}
	
	
	
	public List<ClearVoucherItem> getVoucherItemByPaymetid(String businessid,String voucherclass)
	{
		List<ClearVoucherItem> vilist = new ArrayList<ClearVoucherItem>();
		String voucherclass2 = voucherclass.replaceAll(",", "','");		
		String strSql = " SELECT distinct v.voucherid, v.voucherno,v.companycode,v.fiyear,TRIM(TO_CHAR(TRIM(vi.rownumber),'000')) AS rownumber,vi.businessitemid from yvoucheritem vi, yvoucher v where v.voucherid = vi.voucherid and vi.businessitemid in (select ci.paymentitemid from ypaymentitem ci where ci.paymentid='"+businessid+"') and v.voucherclass in ('"+voucherclass2+"')";
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
	
	public List<ClearVoucherItem> getVoucherItemByRefmentid(String businessid,String voucherclass)
	{
		List<ClearVoucherItem> vilist = new ArrayList<ClearVoucherItem>();
		String voucherclass2 = voucherclass.replaceAll(",", "','");		
		String strSql = " SELECT distinct v.voucherid, v.voucherno,v.companycode,v.fiyear,TRIM(TO_CHAR(TRIM(vi.rownumber),'000')) AS rownumber,vi.businessitemid from yvoucheritem vi, yvoucher v where v.voucherid = vi.voucherid and vi.businessitemid in (SELECT ri.refundmentitemid FROM yrefundmentitem ri WHERE ri.refundmentid='"+businessid+"') and v.voucherclass in ('"+voucherclass2+"')";
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
	
	public List<ClearVoucherItem> getPaymentVoucherItemByRefmentid(String businessid,String voucherclass)
	{
		List<ClearVoucherItem> vilist = new ArrayList<ClearVoucherItem>();
		String voucherclass2 = voucherclass.replaceAll(",", "','");		
		String strSql = " SELECT distinct v.voucherid, v.voucherno,v.companycode,v.fiyear,TRIM(TO_CHAR(TRIM(vi.rownumber),'000')) AS rownumber,vi.businessitemid from yvoucheritem vi, yvoucher v where v.voucherid = vi.voucherid and vi.businessitemid in (SELECT ri.paymentitemid FROM yrefundmentitem ri WHERE ri.refundmentid='"+businessid+"') and v.voucherclass in ('"+voucherclass2+"')";
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
	
	 public BigDecimal getSumPefundmentamountByRefundmentId(String refundmentId,String businessstates ) {
	        String sql = "SELECT NVL(SUM(RI.pefundmentamount), 0) "
	                + "  FROM YREFUNDMENTITEM RI, YREFUNDMENT R  WHERE R.refundmentId = '"+refundmentId+"' "
	                + "   AND RI.REFUNDMENTID = R.REFUNDMENTID    AND R.BUSINESSSTATE in ( "+ businessstates +" )";
	        return (BigDecimal) this.getJdbcTemplate().queryForObject(sql, BigDecimal.class);
	    }
	 
}
