/*
 * @(#)CustomerTitleJdbcDao.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：林进旭
 *  时　间：2010-7-16
 *  描　述：创建
 */

package com.infolion.xdss3.collect.dao;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import com.infolion.platform.dpframework.core.dao.BaseJdbcDao;
import com.infolion.platform.dpframework.util.ExBeanUtils;
import com.infolion.platform.util.CodeGenerator;
import com.infolion.platform.util.DateUtils;
import com.infolion.platform.util.StringUtils;
import com.infolion.sapss.common.ExcelObject;
import com.infolion.sapss.receipts.domain.TReceiptInfo;

import com.infolion.xdss3.collect.domain.Collect;
import com.infolion.xdss3.collectbankitem.domain.CollectBankItem;
import com.infolion.xdss3.collectcbill.domain.CollectCbill;
import com.infolion.xdss3.collectitem.domain.CollectItem;
import com.infolion.xdss3.collectrelated.domain.CollectRelated;
import com.infolion.xdss3.financeSquare.domain.FundFlow;
import com.infolion.xdss3.financeSquare.domain.SettleSubject;
import com.infolion.xdss3.masterData.domain.CustomerTitle;
import com.infolion.xdss3.payment.domain.ImportPayment;
import com.infolion.xdss3.payment.domain.ImportPaymentItem;
import com.infolion.xdss3.voucher.domain.Voucher;

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
public class CollectJdbcDao extends BaseJdbcDao
{
	/**
	 * 取得合同或立项下收款分配金额的合计值
	 * 
	 * @param billno
	 * @param businessstate
	 * @return
	 */
	public Map getCollectItemAmount(String dept_id, String customer, String collectid, String collectitemid, String contract_no, String project_no, String isclear)
	{
		StringBuffer sb = new StringBuffer();
		// 票清收款
		sb.append("select nvl(sum(a.assignamount),0) as assignamount, nvl(sum(a.prebillamount),0) as prebillamount, nvl(sum(a.suretybond),0) as suretybond from YCOLLECTITEM a, YCOLLECT b");
		sb.append(" where a.collectid=b.collectid ");

		if (!StringUtils.isNullBlank(dept_id))
		{
			sb.append(" and b.dept_id='" + dept_id + "' ");
		}
		
		if (!StringUtils.isNullBlank(customer))
		{
			sb.append(" and b.customer='" + customer + "' ");
		}
		
		if (!StringUtils.isNullBlank(collectid))
		{
			sb.append(" and a.collectid='" + collectid + "' ");
		}
		
		if (!StringUtils.isNullBlank(collectitemid))
		{
			sb.append(" and a.collectitemid='" + collectitemid + "' ");
		}
		
		if (!StringUtils.isNullBlank(contract_no))
		{
			sb.append(" and a.contract_no='" + contract_no + "' ");
		}
		
		if (!StringUtils.isNullBlank(project_no))
		{
			sb.append(" and a.project_no='" + project_no + "' ");
		}
		
		if (!StringUtils.isNullBlank(isclear))
		{
			sb.append(" and a.isclear='" + isclear + "' ");
		}
		
		log.debug("取得收款分配金额的合计值:" + sb.toString());
		List<Map> rowList = this.getJdbcTemplate().queryForList(sb.toString());

		return rowList.get(0);
	}
	
	/**
	 * 取得合同或立项下收款分配金额的合计值
	 * 
	 * @param billno
	 * @param businessstate
	 * @return
	 */
	public Map getCollectCbillAmount(String customer, String collectid, String collectitemid, String contract_no, String project_no, String isclear)
	{
		StringBuffer sb = new StringBuffer();
		// 票清收款
		sb.append("select nvl(sum(a.receivableamount),0) as receivableamount,nvl(sum(a.unreceivedamount),0) as unreceivedamount, nvl(sum(a.clearamount),0) as clearamount from YCOLLECTCBILL a, YCOLLECT b");
		sb.append(" where a.collectid=b.collectid and a.collectid='" + collectid + "' ");
		
		if (!StringUtils.isNullBlank(customer))
		{
			sb.append(" and b.customer='" + customer + "' ");
		}
		
		if (!StringUtils.isNullBlank(collectid))
		{
			sb.append(" and a.collectid='" + collectid + "' ");
		}
		
		if (!StringUtils.isNullBlank(collectitemid))
		{
			sb.append(" and a.collectitemid='" + collectitemid + "' ");
		}
		
		if (!StringUtils.isNullBlank(contract_no))
		{
			sb.append(" and contract_no='" + contract_no + "' ");
		}
		
		if (!StringUtils.isNullBlank(project_no))
		{
			sb.append(" and project_no='" + project_no + "' ");
		}
		
		if (!StringUtils.isNullBlank(isclear))
		{
			sb.append(" and a.isclear='" + isclear + "' ");
		}
		
		log.debug("取得收款清票金额的合计值:" + sb.toString());
		List<Map> rowList = this.getJdbcTemplate().queryForList(sb.toString());

		return rowList.get(0);
	}
	
	public BigDecimal queryAvailableDepositValue(String collectItemId){
		String sql = "select sum(applyamount) from ycollect where oldcollectitemid='" + collectItemId +"'";
		String sql1 = "select a.actsuretybond from ycollectitem a where collectitemid='" + collectItemId + "'";
		BigDecimal v1 = (BigDecimal)getJdbcTemplate().queryForObject(sql, BigDecimal.class);
		BigDecimal v2 = (BigDecimal)getJdbcTemplate().queryForObject(sql1, BigDecimal.class);
		return v2.subtract(v1);
	}
	
	public Collect getCollectByCollectNo(String collectno){
		String sql = "select * from ycollect where collectno='"+collectno+"'";
		List<Map> rowList = this.getJdbcTemplate().queryForList(sql.toString());
		if(rowList.size()>0){
			Collect collect = new Collect();
			ExBeanUtils.setBeanValueFromMap(collect, rowList.get(0));
			return collect;
		}else{
			return null;
		}
	}
	
	public Collect getCollectByCollectId(String collectId){
		String sql = "select * from ycollect where collectid='"+collectId+"'";
		List<Map> rowList = this.getJdbcTemplate().queryForList(sql.toString());
		if(rowList.size()>0){
			Collect collect = new Collect();
			ExBeanUtils.setBeanValueFromMap(collect, rowList.get(0));
			return collect;
		}else{
			return null;
		}
	}
	
	public Collect getCustomerBydraft(String draft){
		String sql = "select * from ycollect where draft='"+draft+"' and businessstate <> '-1'";
		List<Map> rowList = this.getJdbcTemplate().queryForList(sql.toString());
		if(rowList.size()>0){
			Collect collect = new Collect();
			ExBeanUtils.setBeanValueFromMap(collect, rowList.get(0));
			return collect;
		}else{
			return null;
		}
	}
	
	public Collect getCollectBydraft(String draft, String collectid){
		String sql = "select * from ycollect where draft='"+draft+"' and collectid <> '"+collectid+"'";
		List<Map> rowList = this.getJdbcTemplate().queryForList(sql.toString());
		if(rowList.size()>0){
			Collect collect = new Collect();
			ExBeanUtils.setBeanValueFromMap(collect, rowList.get(0));
			return collect;
		}else{
			return null;
		}
	}
	
	public void updateCollect(String collectid, String businessstate){
		String sql = "update YCOLLECT set BUSINESSSTATE='"+businessstate+"' where COLLECTID='"+collectid+"'";
		this.getJdbcTemplate().execute(sql);
	}
		
	public List<Collect> getCollectListByOldcollectid(String oldcollectid){
		String sql = "select * from ycollect where oldcollectid='"+oldcollectid+"'";
		List<Map> rowList = this.getJdbcTemplate().queryForList(sql.toString());
		List<Collect> collectList = new ArrayList<Collect>();
		for (Map map : rowList)
		{
			Collect collect = new Collect();
			ExBeanUtils.setBeanValueFromMap(collect, map);
			collectList.add(collect);
		}
		return collectList;
	}
	
	/**
	 * @创建作者：邱杰烜
	 * @创建日期：2010-09-17 
	 * 获取授信项目
	 */
	public List getProjectCreditById(String projectNo){
		String sql = "SELECT * FROM VProjectCredit WHERE PROJECTNO = '" + projectNo + "'";
		return this.getJdbcTemplate().queryForList(sql);
	}
	
	/**
	 * @创建作者：邱杰烜
	 * @创建日期：2010-09-21
	 * 判断"出单发票号"是否已经被未作废的收款单号关联
	 */
	public List getExportApplyNo(String exportApplyNo){
		String sql = "SELECT * FROM YCOLLECT WHERE BUSINESSSTATE <> '-1' AND EXPORT_APPLY_NO = '" + exportApplyNo + "'";
		return this.getJdbcTemplate().queryForList(sql);
	}
	
	/**
	 * @创建作者：邱杰烜
	 * @创建日期：2010-10-08
	 * 删除结算科目和纯资金
	 */
	public void deleteSettleFund(String collectId){
		String sql1 = "delete from YSETTLESUBJECT where collectid = '" + collectId + "'";		
		String sql2 = "delete from YFUNDFLOW where collectid = '" + collectId + "'";	
		this.getJdbcTemplate().execute(sql1);
		this.getJdbcTemplate().execute(sql2);
	}
	
	/**
	 * @创建作者：邱杰烜
	 * @创建日期：2010-10-11
	 * 根据银行帐号获取银行信息
	 */
	public List getBankInfoByAccount(String bankAccount){
		String sql = "SELECT * FROM YBANK_INFO WHERE BANK_ACCOUNT = '" + bankAccount + "'";
		return this.getJdbcTemplate().queryForList(sql);
	}
	
	/**
	 * @创建作者：邱杰烜
	 * @创建日期：2010-10-11
	 * 根据业务状态，取得发票已经审批完金额、在途金额。
	 */
	public BigDecimal getSumClearAmount(String invoice, String billClear, String singleClear, String collectState){
		StringBuffer sb = new StringBuffer();
		sb.append("select nvl(sum(amount),0) from (");
		sb.append("select nvl(sum(clearbillamount),0) as amount from YBILLCLEARITEM a ,YBILLCLEAR b ");
		sb.append(" where a.BILLCLEARID=b.BILLCLEARID and b.CLEARTYPE='1' and a.INVOICE='" + invoice + "' and b.BUSINESSSTATE in(" + billClear + ")");
		sb.append(" union ");
		sb.append("select nvl(sum(CLEARAMOUNT),0) as amount from YUNCLEARCUSTBILL a ,YCUSTOMSICLEAR b ");
		sb.append(" where a.CUSTOMSCLEARID=b.CUSTOMSCLEARID and b.BUSINESSSTATE in(" + singleClear + ") and a.BILLNO='" + invoice + "'");
		sb.append(" union ");
		sb.append(" select nvl(sum( a.CLEARAMOUNT),0) as amount from YCOLLECTCBILL a ,YCOLLECT b where a.COLLECTID=b.COLLECTID");
		sb.append(" and b.BUSINESSSTATE in(" + collectState + ") and a.BILLNO='" + invoice + "'");
		sb.append(" )");

		log.debug("根据业务状态，取得发票已经审批完金额、在途金额:" + sb.toString());
		BigDecimal amount = (BigDecimal) this.getJdbcTemplate().queryForObject(sb.toString(), BigDecimal.class);

		if (null == amount)
			return new BigDecimal(0);
		else
			return amount;
	}
	
	public String getRefundByCollectitemid(String collectItemid,String businessstate){
		String sql = "SELECT b.refundmentno FROM yrefundmentitem a ,yrefundment b WHERE a.refundmentid=b.refundmentid AND  a.istybond='Y' AND b.businessstate in ("+businessstate+") And a.collectitemid='"+collectItemid+"'";
		List<Map> rowList = this.getJdbcTemplate().queryForList(sql.toString());
		if(rowList.size()>0){			
			return (String)rowList.get(0).get("refundmentno");
		}else{
			return null;
		}		
	}
	
	/**
     * @创建作者：邱杰烜
     * @创建日期：2011-05-18 
     * 更新收款子对象（收款银行、结算科目、纯资金往来）的Flag字段
     */
    public void updateCollectSubObjFlag(String businessId){
        String updateSql1 = "";
        String updateSql2 = "";
        String updateSql3 = "";
        /*
         * 更新收款银行的Flag字段为1（即不可编辑）
         */
        updateSql1 = "UPDATE YCOLLECTBANKITEM A SET A.FLAG='1' WHERE A.COLLECTID='" + businessId + "'";
        this.getJdbcTemplate().update(updateSql1);
        
        /*
         * 根据“借贷”更新结算科目的Flag字段
         * 例如：
         * 借贷4有值，则结算科目4以上的4个区域不可编辑；
         * 借贷3有值、借贷4无值，则结算科目3以上的3个区域不可编辑；
         * 借贷2有值、借贷4无值、借贷3无值，则结算科目2以上的2个区域不可编辑；
         * 借贷1有值、借贷4无值、借贷3无值、借贷2无值，则结算科目1以上的1个区域不可编辑；
         */
        List<Map<String, String>> settleSubjectList = this.getJdbcTemplate().queryForList("SELECT * FROM YSETTLESUBJECT WHERE COLLECTID='" + businessId + "'");
        if(null!=settleSubjectList && settleSubjectList.size()>0){
            Map<String, String> map = settleSubjectList.get(0);
            int flag = 0;
            if(!StringUtils.isNullBlank(map.get("DEBTCREDIT4"))){
                flag = 4;
            }else if(!StringUtils.isNullBlank(map.get("DEBTCREDIT3"))){
                flag = 3;
            }else if(!StringUtils.isNullBlank(map.get("DEBTCREDIT2"))){
                flag = 2;
            }else if(!StringUtils.isNullBlank(map.get("DEBTCREDIT1"))){
                flag = 1;
            }
            updateSql2 = "UPDATE YSETTLESUBJECT B SET B.FLAG='" + flag + "' WHERE B.COLLECTID='" + businessId + "'";
            this.getJdbcTemplate().update(updateSql2);
        }
        
        /*
         * 根据“借贷”更新纯资金往来的Flag字段
         * 例如：
         * 借贷3有值，则纯资金往来3以上的3个区域不可编辑；
         * 借贷2有值、借贷3无值，则纯资金往来2以上的2个区域不可编辑；
         * 借贷1有值、借贷3无值、借贷2无值，则纯资金往来1以上的1个区域不可编辑；
         */
        List<Map<String, String>> fundFlowList = this.getJdbcTemplate().queryForList("SELECT * FROM YFUNDFLOW WHERE COLLECTID='" + businessId + "'");
        if(null!=fundFlowList && fundFlowList.size()>0){
            Map<String, String> map = fundFlowList.get(0);
            int flag = 0;
            if(!StringUtils.isNullBlank(map.get("DEBTCREDIT3"))){
                flag = 3;
            }else if(!StringUtils.isNullBlank(map.get("DEBTCREDIT2"))){
                flag = 2;
            }else if(!StringUtils.isNullBlank(map.get("DEBTCREDIT1"))){
                flag = 1;
            }
            updateSql3 = "UPDATE YFUNDFLOW C SET C.FLAG='" + flag + "' WHERE C.COLLECTID='" + businessId + "'";
            this.getJdbcTemplate().update(updateSql3);
        }
    }
    
    /**
     * @创建作者：邱杰烜
     * @创建日期：2011-05-30
     * 更新已生成并提交的贴现/议付凭证的“参考码”字段为1
     */
    public void updateDisNegVoucherState(String businessId){
        String sql = "UPDATE YVOUCHER SET REFERENCECODE='1' WHERE BUSINESSID='" + businessId + "'";
        this.getJdbcTemplate().update(sql);
    }
	public void updateDate(Collect info ,String oldStr,String newStr,String userId){
		String strSql = " update YCOLLECT set SENDGOODSDATE =?,BILLCHECKDATE=? where collectid = ?";
		String strsqllog = " insert into t_saveDateLog values (?,?,?,?,?,?,?)";
		String s1 = info.getSendgoodsdate().replaceAll("-", "");
		s1 = s1.equals("")?" ":s1;
		String s2 = info.getBillcheckdate().replaceAll("-", "");
		s2 = s2.equals("")?" ":s2;
		getJdbcTemplate().update(strSql, new Object[] {s1,s2,info.getCollectid()});
		getJdbcTemplate().update(strsqllog, new Object[] { CodeGenerator.getUUID(),info.getCollectid(),"收款",oldStr,newStr,userId,DateUtils.getCurrTime(DateUtils.HYPHEN_DISPLAY_DATE) });
   }
	
	 /**
     * 取得收款对象。所有跟收款有关的数据
     * @param collectId
     * @return
     */
    public Collect getCollectById(String collectId){
    	String sql = "select * from ycollect a where  a.collectid='" + collectId + "'";
		List<Map> rowList = this.getJdbcTemplate().queryForList(sql.toString());
		if(rowList.size()==0)return null;
		Collect collect = new Collect();		
		ExBeanUtils.setBeanValueFromMap(collect, rowList.get(0));
		
		sql = "select * from ycollectitem a where  a.collectid='" + collectId + "'";
		rowList = this.getJdbcTemplate().queryForList(sql.toString());
		Set<CollectItem> collectitemSet = new HashSet<CollectItem>();
		for(Map ci:rowList){
			CollectItem collectItem = new CollectItem();
			ExBeanUtils.setBeanValueFromMap(collectItem, ci);
			collectItem.setCollect(collect);
			collectitemSet.add(collectItem);
		}		
		collect.setCollectitem(collectitemSet);
		
		sql = "select * from YCOLLECTBANKITEM a where  a.collectid='" + collectId + "'";
		rowList = this.getJdbcTemplate().queryForList(sql.toString());
		Set<CollectBankItem> collectBankitemSet = new HashSet<CollectBankItem>();
		for(Map rl:rowList){
			CollectBankItem cbi = new CollectBankItem();
			ExBeanUtils.setBeanValueFromMap(cbi, rl);
			cbi.setCollect(collect);
			collectBankitemSet.add(cbi);			
		}
		collect.setCollectbankitem(collectBankitemSet);
		
		sql="select * from YCOLLECTCBILL a where  a.collectid='" + collectId + "'";
		rowList = this.getJdbcTemplate().queryForList(sql.toString());
		Set<CollectCbill> collectcbillSet = new HashSet<CollectCbill>();
		for(Map rl:rowList){
			CollectCbill ccb = new CollectCbill();
			ExBeanUtils.setBeanValueFromMap(ccb, rl);
			ccb.setCollect(collect);
			collectcbillSet.add(ccb);			
		}
		collect.setCollectcbill(collectcbillSet);
		
		sql="select * from YCOLLECTRELATED a where  a.collectid='" + collectId + "'";
		rowList = this.getJdbcTemplate().queryForList(sql.toString());
		Set<CollectRelated> collectRelatedSet = new HashSet<CollectRelated>();
		for(Map rl:rowList){
			CollectRelated cr = new CollectRelated();
			ExBeanUtils.setBeanValueFromMap(cr, rl);
			cr.setCollect(collect);
			collectRelatedSet.add(cr);			
		}
		collect.setCollectrelated(collectRelatedSet);
		
		sql="select * from YFUNDFLOW a where  a.collectid='" + collectId + "'";
		rowList = this.getJdbcTemplate().queryForList(sql.toString());	
		if(null != rowList && rowList.size()!=0){
			FundFlow ff = new FundFlow();
			ExBeanUtils.setBeanValueFromMap(ff,  rowList.get(0));
			ff.setCollect(collect);
			collect.setFundFlow(ff);
		}
		
		sql="select * from YSETTLESUBJECT a where  a.collectid='" + collectId + "'";
		rowList = this.getJdbcTemplate().queryForList(sql.toString());	
		if(null != rowList && rowList.size()!=0){
			SettleSubject ss = new SettleSubject();
			ExBeanUtils.setBeanValueFromMap(ss,  rowList.get(0));
			ss.setCollect(collect);
			collect.setSettleSubject(ss);
		}		
		
    	return collect;
    }
    
    public void dealOutToExcel(String sql,String strAuthSql ,final ExcelObject excel){

		//final IntObject i= new IntObject();
		
		getJdbcTemplate().query(sql+strAuthSql.replace("t.", " a."),new Object[]{}, new RowCallbackHandler(){
			public void processRow(ResultSet rs) throws SQLException{
				String[] values = new String[13];
				values[0] = String.valueOf(rs.getRow());
				values[1] = rs.getString("title");
				values[2] = rs.getString("collectno");
				values[3] = rs.getString("dept_name");
				values[4] = rs.getString("name1");
				values[5] = rs.getString("project_no");
				values[6] = rs.getString("applyamount");
				values[7] = rs.getString("currency");
				values[8] = rs.getString("sendgoodsdate");
				values[9] = rs.getString("createtime");
				values[10] = rs.getString("VOUCHERDATE");
				values[11] = rs.getString("collectTypeText");
				values[12] = rs.getString("stateText");
				
				try{
					excel.addRow(values);
				}
				catch (Exception e) {
                    e.printStackTrace();
				}
			}});
	
	}
    
    public void dealOutToExcelSurety(String sql,String strAuthSql ,final ExcelObject excel){

		//final IntObject i= new IntObject();序号","收款单号","部门","客户名称","立项号","金额","币别","创建日期"
		
		getJdbcTemplate().query(sql+strAuthSql.replace("t.", " a."),new Object[]{}, new RowCallbackHandler(){
			public void processRow(ResultSet rs) throws SQLException{
				String[] values = new String[10];
				values[0] = String.valueOf(rs.getRow());
				values[1] = rs.getString("collectno");
				values[2] = rs.getString("DEPT_NAME");
				values[3] = rs.getString("NAME1");
				values[4] = rs.getString("PROJECT_NO");
				values[5] = rs.getString("APPLYAMOUNT");
				values[6] = rs.getString("CURRENCY");
				values[7] = rs.getString("REMARK");
				values[8] = rs.getString("TEXT");
				values[9] = rs.getString("createtime");
				
				try{
					excel.addRow(values);
				}
				catch (Exception e) {
                    e.printStackTrace();
				}
			}});
	
	}
    
    public String getTradeTypeByProjectno(String projectno){
		String sql = "select p.trade_type from t_project_info p where p.project_no='"+projectno+"'";
		List<Map> rowList = this.getJdbcTemplate().queryForList(sql.toString());
		if(rowList.size()>0){			
			return (String)rowList.get(0).get("trade_type");
		}else{
			return null;
		}		
	}
    
}
