/*
 * @(#)CustomerTitleJdbcDao.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：林进旭
 *  时　间：2010-7-16
 *  描　述：创建
 */

package com.infolion.xdss3.packingloan.dao;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.infolion.platform.dpframework.core.dao.BaseJdbcDao;
import com.infolion.platform.dpframework.util.ExBeanUtils;
import com.infolion.xdss3.financeSquare.domain.FundFlow;
import com.infolion.xdss3.financeSquare.domain.SettleSubject;
import com.infolion.xdss3.packingloan.domain.PackingBankItem;
import com.infolion.xdss3.packingloan.domain.PackingLoan;
import com.infolion.xdss3.packingloan.domain.PackingReBankItem;
import com.infolion.xdss3.packingloan.domain.PackingReBankTwo;


/**
 * @author HONG
 * 打包贷款JDBC
 */
@Repository
public class PackingLoanJdbcDao extends BaseJdbcDao
{
    private Log log = LogFactory.getFactory().getLog(this.getClass());
    
    /**
     * 删除 出口押汇 关联对象
     * @param packingid
     */
    public void deleteSubObject(String packingid){
        String sql3 = "delete from YFUNDFLOW where packingid = '" + packingid + "'";        // 付款纯资金
        String sql4 = "delete from YSETTLESUBJECT where packingid = '" + packingid + "'";   // 付款结算科目
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
    
    public void updatePackingRebank(String id){
        String updateSQL ="update ypackingrebank set BUSINESSTYPE='已做账' where packingid='"+id+"'";
        this.getJdbcTemplate().execute(updateSQL);
        
        updateSQL ="update ypackingrebank set BUSINESSTYPE='已做账' where packingid='"+id+"'";
        this.getJdbcTemplate().execute(updateSQL);
    }
    
    

    /**
     * 更新还押汇银行2 币别,汇率
     * @param packingid
     */
    public void updateReBankTwo(String packingid) {
        String updateSQL ="UPDATE YPACKINGREBANK R" +
                "   SET R.CURRENCY3 =" +
                "       (SELECT CURRENCY FROM ypackingloan WHERE packingid = '"+packingid+"' AND ROWNUM < 2)" +
                " WHERE packingid = '"+packingid+"' " +
                "   AND (TRIM(R.CURRENCY3)  IS NULL)";
        this.getJdbcTemplate().execute(updateSQL);
        
        updateSQL ="UPDATE YPACKINGREBANK R" +
        "   SET R.Cashflowitem3 = '352' " +
        " WHERE packingid = '"+packingid+"' " +
        "   AND (TRIM(R.Cashflowitem3) IS NULL)";
        this.getJdbcTemplate().execute(updateSQL);
        
    }
    
    public PackingLoan getPackingLoanByid(String id){
    	String sql = "select * from YPACKINGLOAN a where  a.PACKINGID='" + id + "'";
		List<Map> rowList = this.getJdbcTemplate().queryForList(sql.toString());
		if(rowList.size()==0)return null;
		PackingLoan packingLoan = new PackingLoan();
		ExBeanUtils.setBeanValueFromMap(packingLoan, rowList.get(0));
		
		sql = "select * from YPACKINGBANKITEM a where  a.PACKINGID='" + id + "'";
		rowList = this.getJdbcTemplate().queryForList(sql.toString());
		Set<PackingBankItem> packingBankItemSet = new HashSet<PackingBankItem>();
		for(Map ci:rowList){
			PackingBankItem pbi = new PackingBankItem();
			ExBeanUtils.setBeanValueFromMap(pbi, ci);
			pbi.setPackingLoan(packingLoan);
			packingBankItemSet.add(pbi);
		}
		packingLoan.setPackingBankItem(packingBankItemSet);
		
		sql = "select * from YPACKINGREBANK a where  a.PACKINGID='" + id + "'";
		rowList = this.getJdbcTemplate().queryForList(sql.toString());
		Set<PackingReBankItem> packingReBankItemSet = new HashSet<PackingReBankItem>();
		Set<PackingReBankTwo> packingReBankTwoSet = new HashSet<PackingReBankTwo>();
		for(Map ci:rowList){
			PackingReBankItem pbi = new PackingReBankItem();
			ExBeanUtils.setBeanValueFromMap(pbi, ci);
			pbi.setPackingLoan(packingLoan);
			packingReBankItemSet.add(pbi);
			
			PackingReBankTwo pbit = new PackingReBankTwo();
			ExBeanUtils.setBeanValueFromMap(pbit, ci);
			pbit.setPackingLoan(packingLoan);
			packingReBankTwoSet.add(pbit);
			
		}
		packingLoan.setPackingReBankItem(packingReBankItemSet);
		packingLoan.setPackingReBankTwo(packingReBankTwoSet);		
		
		
		sql="select * from YFUNDFLOW a where  a.PACKINGID='" + id + "'";
		rowList = this.getJdbcTemplate().queryForList(sql.toString());	
		if(null != rowList && rowList.size()!=0){
			FundFlow ff = new FundFlow();
			ExBeanUtils.setBeanValueFromMap(ff,  rowList.get(0));
			ff.setPackingLoan(packingLoan);
			packingLoan.setFundFlow(ff);
		}
		
		sql="select * from YSETTLESUBJECT a where  a.PACKINGID='" + id + "'";
		rowList = this.getJdbcTemplate().queryForList(sql.toString());	
		if(null != rowList && rowList.size()!=0){
			SettleSubject ss = new SettleSubject();
			ExBeanUtils.setBeanValueFromMap(ss,  rowList.get(0));
			ss.setPackingLoan(packingLoan);
			packingLoan.setSettleSubject(ss);
		}		
    	return packingLoan;
    }
    
}
