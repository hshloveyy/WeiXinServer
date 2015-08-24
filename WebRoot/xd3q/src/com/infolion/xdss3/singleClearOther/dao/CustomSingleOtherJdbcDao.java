/**
 * 
 */
package com.infolion.xdss3.singleClearOther.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.infolion.platform.dpframework.core.dao.BaseJdbcDao;
import com.infolion.platform.util.StringUtils;
import com.infolion.xdss3.singleClear.domain.ClearVoucherItem;
import com.infolion.xdss3.singleClear.domain.IKeyValue;
import com.infolion.xdss3.singleClear.domain.KeyValue;

/**
 * @author User
 *
 */
@Repository
public class CustomSingleOtherJdbcDao extends BaseJdbcDao{
	/**
	 * 根据业务状态、customertitleid，取得款已经审批完金额(款已清金额)、在途金额(款在途金额)。
	 * 
	 * @param customertitleid
	 * @param businessstates
	 * @return
	 */
	public BigDecimal getSumClearAmountByCollect(String customertitleid, String businessstates)
	{
		StringBuffer sb = new StringBuffer();
		sb.append("select nvl(sum(AMOUNT),0) from (");
		// 供应商单清
		sb.append(" select nvl(sum(NOWCLEARAMOUNT),0) as AMOUNT from YUNCOLLECTOTHER a ,YCUSTOMCLEAROTHE b ");
		sb.append(" where a.CUSTOMSCLEARID=b.CUSTOMSCLEARID and a.CUSTOMERTITLEID='" + customertitleid + "' and b.BUSINESSSTATE in(" + businessstates + ")");
		sb.append(" )");
		log.debug(" 根据业务状态、customertitleid，取得款已经审批完金额(款已清金额)、在途金额(款在途金额)。:" + sb.toString());
		BigDecimal list = (BigDecimal) this.getJdbcTemplate().queryForObject(sb.toString(), BigDecimal.class);

		if (null == list)
			return new BigDecimal(0);
		else
			return list;
	}
	/**
	 * 根据业务状态、customertitleid，取得票已经审批完金额(款已清金额)、在途金额(款在途金额)。
	 * 
	 * @param customertitleid
	 * @param businessstates
	 * @return
	 */
	public BigDecimal getSumClearAmountByBill(String customertitleid, String businessstates)
	{
		StringBuffer sb = new StringBuffer();
		sb.append("select nvl(sum(AMOUNT),0) from (");
		// 供应商单清
		sb.append(" select nvl(sum(clearamount),0) as AMOUNT from YUNCUSTBILLOTHER a ,YCUSTOMCLEAROTHE b ");
		sb.append(" where a.CUSTOMSCLEARID=b.CUSTOMSCLEARID and a.CUSTOMERTITLEID='" + customertitleid + "' and b.BUSINESSSTATE in(" + businessstates + ")");
		sb.append(" )");
		log.debug(" 根据业务状态、customertitleid，取得票已经审批完金额(款已清金额)、在途金额(款在途金额)。:" + sb.toString());
		BigDecimal list = (BigDecimal) this.getJdbcTemplate().queryForObject(sb.toString(), BigDecimal.class);

		if (null == list)
			return new BigDecimal(0);
		else
			return list;
	}
	
	/**
	 * 判断sap手工调汇率，是否有清账
	 * @param customertitleid
	 * @return
	 */
	public boolean getSapCollect(String customertitleid)
	{
		StringBuffer sb = new StringBuffer();
		sb.append(" select COUNT(*) from YUNCOLLECTOTHER a ,YCUSTOMCLEAROTHE b ");
		sb.append(" where a.CUSTOMSCLEARID=b.CUSTOMSCLEARID and a.CUSTOMERTITLEID='" + customertitleid + "' and b.BUSINESSSTATE !='-1'");	
		log.debug(" 根据customertitleid，判断sap手工调汇率，是否有清账:" + sb.toString());
		int list = (int) this.getJdbcTemplate().queryForInt(sb.toString());

		if (list == 0)
			return false;
		else
			return true;
	}
	
	/***
	 * 取得除本次以外的款方有部分清的，客户清账ID
	 * @param titleid
	 * @param clearid,
	 * @return
	 */
	public List<IKeyValue> getPartIdsByCustomerClear(String titleid,String clearid){
		List<IKeyValue> list = new ArrayList<IKeyValue>();
		StringBuffer sb = new StringBuffer();	
		sb.append("  SELECT DISTINCT *  FROM ( ");
	//		单清账
			sb.append(" SELECT cs.customsclearid AS clearid ,'1' AS type2 FROM YCUSTOMCLEAROTHE cs ,YUNCOLLECTOTHER uc WHERE cs.customsclearid=uc.customsclearid "  );
			sb.append(" AND cs.businessstate IN ('3') AND uc.nowclearamount <> uc.amount  ");
			sb.append(" AND  uc.customertitleid ='" + titleid + "' AND cs.customsclearid <>  '" + clearid + "' ");
			sb.append("  UNION		  ");
	
//	票方		(单清账)
			sb.append(" SELECT cs.customsclearid  AS clearid , '1' AS TYPE2  FROM YCUSTOMCLEAROTHE cs ,YUNCUSTBILLOTHER ub WHERE cs.customsclearid=ub.customsclearid  ");
			sb.append("  AND cs.businessstate IN ('3') AND ub.clearamount <> ub.receivedamount    ");
			sb.append("  AND  ub.customertitleid ='"+titleid+"' AND cs.customsclearid <>  '"+clearid+"'   ");
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
	public List<IKeyValue> getPartIdsByCustomerClear(String clearid){
		List<IKeyValue> list = new ArrayList<IKeyValue>();
		StringBuffer sb = new StringBuffer();
//		款方
		sb.append(" SELECT uc.customertitleid,uc.collectitemid as collectitemidOrBillno,cs.customsclearid AS clearid FROM YCUSTOMCLEAROTHE cs ,YUNCOLLECTOTHER uc WHERE cs.customsclearid=uc.customsclearid  ");
		sb.append("  AND cs.businessstate IN ('3') AND uc.nowclearamount <> uc.amount   ");
		sb.append("  AND cs.customsclearid IN ('" + clearid + "') ");
		sb.append("  UNION ");
		
//		票方
		sb.append(" SELECT ub.customertitleid,ub.billno  as collectitemidOrBillno,cs.customsclearid  AS clearid FROM YCUSTOMCLEAROTHE cs ,YUNCUSTBILLOTHER ub WHERE cs.customsclearid=ub.customsclearid ");
		sb.append("  AND cs.businessstate IN ('3') AND ub.clearamount <> ub.receivedamount   ");
		sb.append("  AND ub.customsclearid in ('"+clearid+"') ");
		
		
		List<Map> rowList = this.getJdbcTemplate().queryForList(sb.toString());
		log.debug("取得本次有部分清的，款票行项目titleID:" + sb.toString());
		for (Map map : rowList){
			KeyValue keyValue = new KeyValue(); 
			keyValue.setKey(map.get("customertitleid").toString());
			keyValue.setValue(map.get("collectitemidOrBillno").toString());
			keyValue.setValue2(map.get("clearid").toString());
			list.add(keyValue);
		}
		return list;
	}
	
	public List<ClearVoucherItem> getVoucherItemByTitle(String ids){
		List<ClearVoucherItem> list = new ArrayList<ClearVoucherItem>();
		StringBuffer sb = new StringBuffer();	
		sb.append(" SELECT ct.belnr,ct.gjahr,ct.bukrs,ct.buzei,ct.customertitleid FROM ycustomertitleothers ct WHERE ct.customertitleid IN ("+ids+") ");
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
	
	/**
	 * JDBC删除客户单清对象下，所有1对多子业务对象数据。
	 * 
	 * @return
	 */
	public void deleteCustomSingleClearUnderOneToManySubObject(String customsclearid)
	{
		if (!StringUtils.isNullBlank(customsclearid))
		{
			String strSql1 = "delete from YUNCUSTBILLOTHER where CUSTOMSCLEARID='" + customsclearid + "'";
			String strSql2 = "delete from YUNCOLLECTOTHER where CUSTOMSCLEARID='" + customsclearid + "'";
			this.getJdbcTemplate().execute(strSql1);
			this.getJdbcTemplate().execute(strSql2);
		}
	}
	
}
