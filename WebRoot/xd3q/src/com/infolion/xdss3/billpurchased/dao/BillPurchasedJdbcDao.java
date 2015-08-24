/*
 * @(#)CustomerTitleJdbcDao.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：林进旭
 *  时　间：2010-7-16
 *  描　述：创建
 */

package com.infolion.xdss3.billpurchased.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import com.infolion.platform.dpframework.core.dao.BaseJdbcDao;
import com.infolion.platform.dpframework.util.ExBeanUtils;
import com.infolion.platform.dpframework.util.StringUtils;
import com.infolion.xdss3.billPurBankItem.domain.BillPurBankItem;
import com.infolion.xdss3.billPurBillItem.domain.BillPurBillItem;
import com.infolion.xdss3.billPurReBankItem.domain.BillPurReBankItem;
import com.infolion.xdss3.billPurReBankItem.domain.BillPurReBankTwo;
import com.infolion.xdss3.billpurchased.domain.BillPurchased;
import com.infolion.xdss3.financeSquare.domain.FundFlow;
import com.infolion.xdss3.financeSquare.domain.SettleSubject;


/**
 * @author HONG
 * 出口押汇JDBC
 */
@Repository
public class BillPurchasedJdbcDao extends BaseJdbcDao
{
    private Log log = LogFactory.getFactory().getLog(this.getClass());
    
    /**
     * 删除 出口押汇 关联对象
     * @param billpurid
     */
    public void deleteSubObject(String billpurid){
        String sql3 = "delete from YFUNDFLOW where billpurid = '" + billpurid + "'";        // 付款纯资金
        String sql4 = "delete from YSETTLESUBJECT where billpurid = '" + billpurid + "'";   // 付款结算科目
        this.getJdbcTemplate().execute(sql3);
        this.getJdbcTemplate().execute(sql4);
    }
	
	/**
	 * @创建作者：邱杰烜
	 * @创建日期：2010-09-17 
	 * 获取授信项目
	 */
	public List<Map<String,String>> getProjectCreditByBillNo(String id){
        StringBuffer sql = new StringBuffer(
                "SELECT *  FROM VPROJECTCREDIT WHERE PROJECTNO IN (")
                .append(" SELECT T.PROJECT_NO ")
                .append("  FROM T_EXPORT_APPLY T ")
                .append("  WHERE T.EXPORT_APPLY_ID IN ")
                .append("      (SELECT A.EXPORT_APPLY_ID ")
                .append("         FROM T_EXPORT_BILL_EXAM_D A ")
                .append("         WHERE A.EXPORT_BILL_EXAM_ID IN ")
                .append("               (SELECT B.EXPORT_BILL_EXAM_ID ")
                .append("                 FROM T_EXPORT_BILL_EXAM B ")
                .append("                WHERE B.INV_NO = '" + id + "')))");
        log.debug("获取授信项目:"+sql.toString());
	    return this.getJdbcTemplate().queryForList(sql.toString());
	}
    
    /**
     * 根据银行帐号获取银行信息
     */
    public List getBankInfoByAccount(String bankAccount){
        String sql = "SELECT * FROM YBANK_INFO WHERE BANK_ACCOUNT = '" + bankAccount + "'";
        return this.getJdbcTemplate().queryForList(sql);
    }
    
    public List getCashJournalList(String businessid) {
        return this.getJdbcTemplate().queryForList("SELECT * FROM YCASHDIARY WHERE businessid = '"+businessid+"'");
    }
    
    public void updateYbillPurRebank(String id){
        String updateSQL ="update YBILLPURREBANK set BUSINESSTYPE='已做账' where BILLPURID='"+id+"'";
        this.getJdbcTemplate().execute(updateSQL);
    }

    /**
     * 更新还押汇银行2 币别,汇率
     * @param billpurid
     */
    public void updateReBankTwo(String billpurid) {
        String updateSQL ="UPDATE YBILLPURREBANK R" +
        		"   SET R.CURRENCY3 =" +
        		"       (SELECT CURRENCY FROM YBILLPURCHASED WHERE BILLPURID = '"+billpurid+"' AND ROWNUM < 2)" +
        		" WHERE BILLPURID = '"+billpurid+"' " +
        		"   AND (TRIM(R.CURRENCY3) IS NULL)";
        this.getJdbcTemplate().execute(updateSQL);
        
        updateSQL ="UPDATE YBILLPURREBANK R" +
        "   SET R.Cashflowitem3 = '352' " +
        " WHERE BILLPURID = '"+billpurid+"' " +
        "   AND (TRIM(R.Cashflowitem3)  IS NULL)";
        this.getJdbcTemplate().execute(updateSQL);
        
    }
    
    /**
     * 取得押汇与还押汇金额（期间内）
     * @param billpurid
     * @param begDate
     * @param endDate 为空时，则是查询（期间内）前段时间内金额
     * @param flag
     * @return
     */
    public Map<String,String> getAmount(String billpurid, String begDate, String endDate, String flag) {
        final Map<String,String> map = new HashMap<String, String>();
        map.put("AMOUNT", "0");
        map.put("AMOUNT2", "0");
        String sql =   "SELECT NVL(SUM(AMOUNT),0) as AMOUNT, NVL(SUM(AMOUNT2),0) as AMOUNT2 " + 
                                "  FROM YVOUCHER V, YVOUCHERITEM VI, YBILLPURCHASED P" + 
                                " WHERE V.VOUCHERID = VI.VOUCHERID" + 
                                "   AND P.BILLPURID = V.BUSINESSID" + 
                                "   AND trim(v.voucherno) IS NOT NULL " + 
                                "   AND P.BILLPURID = '"+billpurid+"' " + 
                                "   AND TRIM(vi.cashflowitem) IS NULL " + 
                                "   AND vi.subjectdescribe NOT LIKE '%中转科目%' " ;
        if ("押汇".equals(flag)) {
            sql += "   AND VI.BUSINESSITEMID IN  (SELECT BI.BANKITEMID FROM YBILLPURBANKITEM BI  WHERE BI.BILLPURID = P.BILLPURID) ";
        } else { //还押汇
            sql += "   AND VI.BUSINESSITEMID IN  (SELECT BI.BANKITEMID FROM YBILLPURREBANK BI  WHERE BI.BILLPURID = P.BILLPURID) ";
        }

        if (StringUtils.isNotBlank(begDate) && StringUtils.isNotBlank(endDate)) {
            sql += "   AND V.CHECKDATE BETWEEN '"+begDate+"' AND '"+endDate+"' " ;
        } else if (StringUtils.isNullBlank(endDate)) {
            sql += "   AND V.CHECKDATE < '"+begDate+"' " ;
        }
        
        getJdbcTemplate().query(sql, new Object[]{},
                new RowCallbackHandler(){
            public void processRow(ResultSet rs) throws SQLException {
                map.put("AMOUNT",rs.getString("AMOUNT"));
                map.put("AMOUNT2",rs.getString("AMOUNT2"));
        }
        });
        
        return map; 
        
    }
    
    /**
     * 取得出口货物申请表　信息
     */
    public Map<String,String> getExportApply(String billpurid) {
        final Map<String,String> map = new HashMap<String, String>();
        String sql =   "SELECT TE.PROJECT_NO," +
        		"       TE.PROJECT_NAME," +
        		"       TE.CONTRACT_PAPER_NO," +
        		"       TE.CUSTOMER," ;
        StringBuffer sb = new StringBuffer("");
        sb.append("      CASE                            ");
        sb.append("        WHEN TE.TRADE_TYPE = '1' THEN ");
        sb.append("         '外贸自营进口*业务'           ");
        sb.append("        WHEN TE.TRADE_TYPE = '2' THEN ");
        sb.append("         '外贸自营出口*业务'           ");
        sb.append("        WHEN TE.TRADE_TYPE = '3' THEN ");
        sb.append("         '外贸自营进口业务'           ");
        sb.append("        WHEN TE.TRADE_TYPE = '4' THEN ");
        sb.append("         '外贸自营出口业务'           ");
        sb.append("        WHEN TE.TRADE_TYPE = '5' THEN ");
        sb.append("         '外贸代理出口业务'           ");
        sb.append("        WHEN TE.TRADE_TYPE = '6' THEN ");
        sb.append("         '外贸代理进口业务'           ");
        sb.append("        WHEN TE.TRADE_TYPE = '7' THEN ");
        sb.append("         '内贸业务'                   ");
        sb.append("        WHEN TE.TRADE_TYPE = '8' THEN ");
        sb.append("         '进料加工业务'               ");
        sb.append("        WHEN TE.TRADE_TYPE = '9' THEN ");
        sb.append("         '自营进口敞口业务'           ");
        sb.append("        WHEN TE.TRADE_TYPE = '10' THEN");
        sb.append("         '内贸敞口'                   ");
        sb.append("      END AS VBAK_AUART,              ");//业务类型
        sql+=sb.toString();
        sql += 
        		"       TE.DESTINATIONS" +
        		"  FROM T_EXPORT_BILL_EXAM_D A," +
        		"       T_EXPORT_BILL_EXAM   B," +
        		"       T_EXPORT_APPLY       TE," +
        		"       YBILLPURBILLITEM     PI" +
        		" WHERE A.EXPORT_BILL_EXAM_ID = B.EXPORT_BILL_EXAM_ID" +
        		"   AND B.IS_AVAILABLE = 1" +
        		"   AND B.IS_NEGOTIAT = '是'" +
        		"   AND A.EXPORT_APPLY_ID = TE.EXPORT_APPLY_ID" +
        		"   AND B.INV_NO = PI.BILL_NO" + 
        		//"   AND ( TRIM(replace(B.LCDPDA_NO,'null', '')) IS NULL OR B.LCDPDA_NO = TRIM(PI.LCDPDANO)) " +
        		"   AND PI.billpurid = '" + billpurid +"'" +
        		"   AND ROWNUM < 2" ;
        
        getJdbcTemplate().query(sql, new Object[]{},
                new RowCallbackHandler(){
            public void processRow(ResultSet rs) throws SQLException {
                map.put("PROJECT_NO",rs.getString("PROJECT_NO"));
                map.put("PROJECT_NAME",rs.getString("PROJECT_NAME"));
                map.put("CONTRACT_PAPER_NO",rs.getString("CONTRACT_PAPER_NO"));
                map.put("CUSTOMER",rs.getString("CUSTOMER"));
                map.put("DESTINATIONS",rs.getString("DESTINATIONS"));
                map.put("VBAK_AUART",rs.getString("VBAK_AUART"));
            }
        });
        
        return map; 
        
    }
    /**
     * 更新业务状态
     */
    public void prepConfirm(String id, String businessstate) {
        String updateSql = "update ybillpurchased set businessstate = '" + businessstate + "'  where BILLPURID='"+id+"'";
        this.getJdbcTemplate().execute(updateSql);
    }
    
    public BillPurchased getBillPurchasedById(String id){
    	
    	String sql = "select * from YBILLPURCHASED a where  a.BILLPURID='" + id + "'";
		List<Map> rowList = this.getJdbcTemplate().queryForList(sql.toString());
		if(rowList.size()==0)return null;
		BillPurchased billPurchased =new BillPurchased();
		ExBeanUtils.setBeanValueFromMap(billPurchased, rowList.get(0));
		
		sql = "select * from YBILLPURBILLITEM a where  a.BILLPURID='" + id + "'";
		rowList = this.getJdbcTemplate().queryForList(sql.toString());
		Set<BillPurBillItem> bpbiSet = new HashSet<BillPurBillItem>();
		for(Map ci:rowList){
			BillPurBillItem bpbi =new BillPurBillItem();
			ExBeanUtils.setBeanValueFromMap(bpbi, ci);
			bpbi.setBillPurchased(billPurchased);
			bpbiSet.add(bpbi);
		}
		billPurchased.setBillPurBillItem(bpbiSet);
		
		sql = "select * from YBILLPURBANKITEM a where  a.BILLPURID='" + id + "'";
		rowList = this.getJdbcTemplate().queryForList(sql.toString());
		Set<BillPurBankItem> bpbaiSet = new HashSet<BillPurBankItem>();
		for(Map ci:rowList){
			BillPurBankItem bpbai =new BillPurBankItem();
			ExBeanUtils.setBeanValueFromMap(bpbai, ci);
			bpbai.setBillPurchased(billPurchased);
			bpbaiSet.add(bpbai);
		}
		billPurchased.setBillPurBankItem(bpbaiSet);
		
		sql = "select * from YBILLPURREBANK a where  a.BILLPURID='" + id + "'";
		rowList = this.getJdbcTemplate().queryForList(sql.toString());
		Set<BillPurReBankItem> bprbiSet = new HashSet<BillPurReBankItem>();
		Set<BillPurReBankTwo> bprbitSet = new HashSet<BillPurReBankTwo>();
		for(Map ci:rowList){
			BillPurReBankItem bprbi =new BillPurReBankItem();
			ExBeanUtils.setBeanValueFromMap(bprbi, ci);
			bprbi.setBillPurchased(billPurchased);
			bprbiSet.add(bprbi);
			BillPurReBankTwo bprbt = new BillPurReBankTwo();
			ExBeanUtils.setBeanValueFromMap(bprbt, ci);
			bprbt.setBillPurchased(billPurchased);
			bprbitSet.add(bprbt);
		}
		billPurchased.setBillPurReBankItem(bprbiSet);
		billPurchased.setBillPurReBankTwo(bprbitSet);
		
		sql="select * from YFUNDFLOW a where  a.BILLPURID='" + id + "'";
		rowList = this.getJdbcTemplate().queryForList(sql.toString());	
		if(null != rowList && rowList.size()!=0){
			FundFlow ff = new FundFlow();
			ExBeanUtils.setBeanValueFromMap(ff,  rowList.get(0));
			ff.setBillPurchased(billPurchased);
			billPurchased.setFundFlow(ff);
		}
		
		sql="select * from YSETTLESUBJECT a where  a.BILLPURID='" + id + "'";
		rowList = this.getJdbcTemplate().queryForList(sql.toString());	
		if(null != rowList && rowList.size()!=0){
			SettleSubject ss = new SettleSubject();
			ExBeanUtils.setBeanValueFromMap(ss,  rowList.get(0));
			ss.setBillPurchased(billPurchased);
			billPurchased.setSettleSubject(ss);
		}		
		
    	return billPurchased;
    }
}
