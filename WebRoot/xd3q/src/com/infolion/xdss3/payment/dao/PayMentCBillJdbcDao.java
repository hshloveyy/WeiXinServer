/*
 * @(#)ImportPayMentCBillJdbcDao.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：傅渊源
 *  时　间：2010-7-14
 *  描　述：创建
 */

package com.infolion.xdss3.payment.dao;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import com.infolion.platform.dpframework.core.dao.BaseJdbcDao;
import com.infolion.xdss3.payment.domain.ClearVoucherItemStruct;

@Repository
public class PayMentCBillJdbcDao extends BaseJdbcDao
{
	/**
	 * 取的采购合同的外部纸质合同号
	 * @param strContractNO
	 * @return
	 */
	public String getPruchasePageContract(String strContractNO){
		String strSql = "select OLD_CONTRACT_NO from t_contract_purchase_info where contract_no='" + strContractNO + "'";

		List<Map> rowList = this.getJdbcTemplate().queryForList(strSql);
		if (null != rowList && rowList.size() > 0)
		{
			return (String) ((Map) rowList.get(0)).get("OLD_CONTRACT_NO");
		}
		else
			return "";
	}
	
	/**
	 * 删除付款单的所有票的信息
	 * @param strPayMentId
	 */
	public void _deletePayBill(String strPayMentId){
		String strSql ="delete from ypaymentcbill a where a.paymentid = '" + strPayMentId + "'";
		
		this.getJdbcTemplate().update(strSql);
	}
	
	/**
	 * 判断付款本次是否可清
	 * @param strPaymentId
	 * @return
	 */
	public String checkPayCurrentClear(String strPaymentId){
		String strClearFlag = "";
		
		String strSql1 = "select sum(a.prepayamount) from ypaymentitem a where a.paymentid = '" +strPaymentId+ "'";
		String strSql2 = "select sum(a.payableamount) from ypaymentcbill a where a.paymentid = '"+strPaymentId+"'";
		String strSql3 = "select sum(a.clearamount2) from ypaymentcbill a where a.paymentid = '"+strPaymentId+"'";
		
		BigDecimal prepayamount = (BigDecimal) this.getJdbcTemplate().queryForObject(strSql1, BigDecimal.class);
		
		BigDecimal payableamount = (BigDecimal) this.getJdbcTemplate().queryForObject(strSql2, BigDecimal.class);
		
		BigDecimal clearamount2 = (BigDecimal) this.getJdbcTemplate().queryForObject(strSql3, BigDecimal.class);
		
		if (prepayamount != null &&
			payableamount != null &&
			clearamount2 != null && 
			new BigDecimal("0").compareTo(prepayamount) == 0 &&
		    payableamount.compareTo(clearamount2) == 0){
			strClearFlag ="Y";
		}else{
			strClearFlag = "N";
		}
		
		return strClearFlag;
	}
	
	/**
	 * 判断退款本次是否可清
	 * @param strPaymentId
	 * @return
	 */
	public String checkPayCurrentClear(String strPaymentId, BigDecimal refundmentamount){
		String strClearFlag = "";
		
		String strSql1 = "select sum(a.prepayamount) from ypaymentitem a where a.paymentid = '" +strPaymentId+ "'";
		String strSql2 = "select sum(a.payableamount) from ypaymentcbill a where a.paymentid = '"+strPaymentId+"'";
		String strSql3 = "select sum(a.clearamount2) from ypaymentcbill a where a.paymentid = '"+strPaymentId+"'";
		
		BigDecimal prepayamount = (BigDecimal) this.getJdbcTemplate().queryForObject(strSql1, BigDecimal.class);
		
		BigDecimal payableamount = (BigDecimal) this.getJdbcTemplate().queryForObject(strSql2, BigDecimal.class);
		
		BigDecimal clearamount2 = (BigDecimal) this.getJdbcTemplate().queryForObject(strSql3, BigDecimal.class);
		
		if (prepayamount != null &&
			payableamount != null &&
			clearamount2 != null && 
			refundmentamount.compareTo(prepayamount) == 0 &&
		    payableamount.compareTo(clearamount2) == 0){
			strClearFlag ="Y";
		}else{
			strClearFlag = "N";
		}
		
		return strClearFlag;
	}
	
	/**
	 * 判断付款合同是否可清
	 * @param strPaymentId
	 * @return
	 */
	public String checkPayContractClear(String strPaymentId){
		String strClearFlag = "";
		String strResultContractList = "";
		
		final List<String> contractList = new ArrayList<String>();
		String strSql = "select a.contract_no from ypaymentcbill a where a.paymentid = '" +strPaymentId+ "' group by a.contract_no";
		getJdbcTemplate().query(strSql, new RowCallbackHandler()
		{
			public void processRow(ResultSet rs) throws SQLException
			{
				String contractno = "";
				contractno = rs.getString("contract_no");
				contractList.add(contractno);
			}
		});
		
		if (contractList != null){
			for (int i=0;i<contractList.size();i++){
				String contractNo = contractList.get(i);
				
				//该合同下已清款的金额合计
				String strSql3 = "select sum(a.assignamount) " +
			     " from ypaymentitem a " + 
			     " where a.contract_no = '"+contractNo+"' " + 
			     "  and a.billisclear = '1'";
				
				//本次合同下清票金额
				String strSql2 = " select sum(a.clearamount2) " +
			     " from ypaymentcbill a " +
			     "where a.contract_no = '"+contractNo+"' ";

				//该合同下已清的票金额合计
				String strSql1 = "select sum(dmbtr) from YVENDORTITLE a where a.VERKF = '" +contractNo+ "' and a.iscleared = '1'";
				
				String strSql4 = "select sum(a.assignamount) " +
							     " from ypaymentitem a " + 
							     " where a.contract_no = '" +contractNo+ "' " + 
							     "  and a.paymentid = '"+strPaymentId+"'";
				
				BigDecimal m_dmbtr = (BigDecimal) this.getJdbcTemplate().queryForObject(strSql1, BigDecimal.class);
				//BigDecimal m_clearamount2 = (BigDecimal) this.getJdbcTemplate().queryForObject(strSql2, BigDecimal.class);
				BigDecimal m_assignamount = (BigDecimal) this.getJdbcTemplate().queryForObject(strSql3, BigDecimal.class);
				//BigDecimal m_current_assignamount = (BigDecimal) this.getJdbcTemplate().queryForObject(strSql4, BigDecimal.class);
				
				if (m_dmbtr != null && m_assignamount != null && m_dmbtr.compareTo(BigDecimal.valueOf(0))==1 &&
					m_dmbtr.compareTo(m_assignamount)==0){
					strResultContractList = strResultContractList + contractNo + "','";
					strClearFlag = "Y";
				}
			}
		}
		
		if (strClearFlag.equals("Y")){
			strClearFlag = "'" + strResultContractList.substring(0, strResultContractList.length()-2);
		}
		
		return strClearFlag;
	}
	
	/**
	 * 判断付款立项是否可清
	 * @param strPaymentId
	 * @return
	 */
	public String checkPayProjectClear(String strPaymentId,String strContraclList){
		String strClearFlag = "";
		String strResultProjectList = "";
		
		final List<String> projectList = new ArrayList<String>();
		String strSql = " select a.project_no " +
				      "from ypaymentcbill a " +
				     "where a.paymentid = '" +strPaymentId+ "' " +
				     "  and a.contract_no not in ("+strContraclList+") " +
				     " group by a.project_no";
		getJdbcTemplate().query(strSql, new RowCallbackHandler()
		{
			public void processRow(ResultSet rs) throws SQLException
			{
				String projectno = "";
				projectno = rs.getString("project_no");
				projectList.add(projectno);
			}
		});
		
		if (projectList != null){
			for (int i=0;i<projectList.size();i++){
				String projectNo = projectList.get(i);
				
				String strSql1 = "select sum(dmbtr) " +
					     " from YVENDORTITLE a " +
					     " where a.LIXIANG = '" + projectNo +"' " + 
					     "  and a.verkf not in (" + strContraclList+ ") " +
					     "  and a.iscleared = '1'";
				
				String strSql2 = " select sum(a.clearamount2) " +
						     " from ypaymentcbill a " +
						     " where a.project_no = '" + projectNo +"' " + 
						     "  and a.paymentid = '" + strPaymentId + "' " +
						     "  and a.contract_no not in (" + strContraclList+ ")";
				
				String strSql3 = " select sum(a.assignamount) " +
						     " from ypaymentitem a " +
						     " where a.project_no = '" + projectNo +"' " + 
						     "  and a.contract_no not in (" + strContraclList+ ") " +
						     "  and a.billisclear = '1'";
				
				String strSql4 = "select sum(a.assignamount) " +
						     " from ypaymentitem a " +
						     " where a.project_no = '" + projectNo +"' " + 
						     " and a.contract_no not in (" + strContraclList+ ") " +
						     " and a.paymentid = '"+strPaymentId+"'";
				
				BigDecimal m_dmbtr = (BigDecimal) this.getJdbcTemplate().queryForObject(strSql1, BigDecimal.class);
				BigDecimal m_clearamount2 = (BigDecimal) this.getJdbcTemplate().queryForObject(strSql2, BigDecimal.class);
				BigDecimal m_assignamount = (BigDecimal) this.getJdbcTemplate().queryForObject(strSql3, BigDecimal.class);
				BigDecimal m_current_assignamount = (BigDecimal) this.getJdbcTemplate().queryForObject(strSql4, BigDecimal.class);
				
				if (m_dmbtr != null &&
					m_clearamount2 != null &&	
					m_assignamount != null && 
					m_current_assignamount != null &&
					m_dmbtr.add(m_clearamount2).compareTo(m_assignamount.add(m_current_assignamount))==0){
					strResultProjectList = strResultProjectList + projectNo + "','";
					strClearFlag = "Y";
				}
			}
		}
		
		if (strClearFlag.equals("Y")){
			strClearFlag = "'" + strResultProjectList.substring(0, strResultProjectList.length()-2);
		}
		
		return strClearFlag;
	}
	
	/**
	 * 判断付款供应商是否可清
	 * @param strPaymentId
	 * @return
	 */
	public String checkPaySupplierClear(String strPaymentId,String strContractList,String strProjectList){
		String strClearFlag = "";
		
		String strSql = "select a.supplier " +
				    "from ypayment a " +
				   "where a.paymentid = '"+strPaymentId+"'";
		
		String list = (String) this.getJdbcTemplate().queryForObject(strSql, String.class);
		
		if (null == list)
			strClearFlag = "";
		else{
			strClearFlag = list;
			
			String strSql1 = "  select sum(dmbtr) " +
					    "from YVENDORTITLE a " +
					   "where a.LIFNR = '" +strClearFlag+  "' "+
					     "and a.verkf not in ("+strContractList+") " +
					     "and a.lixiang not in ("+strProjectList+") " + 
					     "and a.shkzg = 'H' and a.iscleared = '0'";
			
//			String strSql2 = "select sum(a.clearamount2) " +
//						    "from ypaymentcbill a " +
//						   "where a.paymentid = '"+strPaymentId+"' " + 
//						     "and a.contract_no not in ("+strContractList+") " +
//						     "and a.project_no not in ("+strProjectList+") ";
			
			String strSql3 = "select sum(a.assignamount) " +
						    "from ypaymentitem a, ypayment b " +
						   "where a.paymentid = b.paymentid " +
						     "and b.supplier = '" +strClearFlag+  "' "+
						     "and a.contract_no not in ("+strContractList+") " +
						     "and a.project_no not in ("+strProjectList+") " + 
						     "and a.billisclear = '1'";
			
//			String strSql4 = "select sum(a.assignamount) " +
//						    "from ypaymentitem a " +
//						   "where a.paymentid = '"+strPaymentId+"' " + 
//						     "and a.contract_no not in ("+strContractList+") " +
//						     "and a.project_no not in ("+strProjectList+") ";
			
			BigDecimal m_dmbtr = (BigDecimal) this.getJdbcTemplate().queryForObject(strSql1, BigDecimal.class);
			//BigDecimal m_clearamount2 = (BigDecimal) this.getJdbcTemplate().queryForObject(strSql2, BigDecimal.class);
			BigDecimal m_assignamount = (BigDecimal) this.getJdbcTemplate().queryForObject(strSql3, BigDecimal.class);
			//BigDecimal m_current_assignamount = (BigDecimal) this.getJdbcTemplate().queryForObject(strSql4, BigDecimal.class);

			if (m_dmbtr != null &&
				//m_clearamount2 != null &&	
				m_assignamount != null && 
				//m_current_assignamount != null &&
				m_dmbtr.compareTo(m_assignamount)==0){
				strClearFlag = "Y";
			}
			else{
				strClearFlag = "N";
			}
		}
		
		return strClearFlag;
	}
	
	/**
	 * 判断银行汇票在收款中是否存在
	 * @param strDraft
	 * @return
	 */
	public int isExitsDraftInfo(String strDraft){
		String strSql = "select count(*) from ycollect a where a.draft ='" +strDraft+ "'";
		
		return this.getJdbcTemplate().queryForInt(strSql);
	}
	
	/**
	 * 判断银行汇票在收款中是否已经使用
	 * @param strDraft
	 * @return
	 */
	public int isUseDraftInfo(String strDraft,String strPaymentId){
		String strSql = "select count(*) from ypayment a where a.draft = '"+strDraft + 
		"' and a.paymenttype = '09'" +
		" and a.paymentid <> '" +strPaymentId+ "'";
		
		return this.getJdbcTemplate().queryForInt(strSql);
	}
	
	/**
	 * 通过银行会票得到客户信息
	 * @param strDraftInfo
	 * @return
	 */
	public String getCustomerBydraft(String strDraft){
		String strSql = "select a.customer from ycollect a where a.draft ='" +strDraft+ "'  AND a.businessstate != '-1' ";

		List<Map> rowList = this.getJdbcTemplate().queryForList(strSql);
		if (null != rowList && rowList.size() > 0)
		{
			return (String) ((Map) rowList.get(0)).get("customer");
		}
		else
			return "";
	}
	
	/**
	 * 通过银行会票得到收款单编号
	 * @param strDraft
	 * @return
	 */
	public String getCollectIdBydraft(String strDraft){
		String strSql = "select collectid from ycollect a where a.draft ='" +strDraft+ "' AND a.businessstate != '-1' ";
		
		List<Map> rowList = this.getJdbcTemplate().queryForList(strSql);
		if (null != rowList && rowList.size() > 0)
		{
			return (String) ((Map) rowList.get(0)).get("collectid");
		}
		else
			return "";
	}
}
