package com.infolion.xdss3.reassign.dao;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.infolion.platform.dpframework.core.dao.BaseJdbcDao;
import com.infolion.platform.dpframework.util.CodeGenerator;
import com.infolion.platform.dpframework.util.ExBeanUtils;
import com.infolion.xdss3.BusinessState;
import com.infolion.xdss3.Constants.cleared;
import com.infolion.xdss3.masterData.domain.CashFlowItem;
import com.infolion.xdss3.masterData.domain.CertificateNo;
import com.infolion.xdss3.masterData.domain.Company;
import com.infolion.xdss3.masterData.domain.Hkont;
import com.infolion.xdss3.reassign.ReassignConstant;
import com.infolion.xdss3.singleClear.domain.IKeyValue;
import com.infolion.xdss3.singleClear.domain.InfoObject;
import com.infolion.xdss3.singleClear.domain.KeyValue;
import com.infolion.xdss3.voucher.domain.Voucher;

@Repository
public class ReassignJdbcDao extends BaseJdbcDao{
	
	/**
	 * 根据业务ID获取凭证号列表
	 * @param bussinessid
	 * @return
	 */
	public List<CertificateNo> getVoucherbyBussinessID(String bussinessid,
			String reassignType, String reassignMethod) {
		List<CertificateNo> certificateNoList = new ArrayList<CertificateNo>();
		List<Map<String, String>> tempRetList = new ArrayList<Map<String, String>>();
		
		String sql = "select VOUCHERNO voucherno, FIYEAR fiyear, COMPANYCODE bukrs from YVOUCHER where BUSINESSID = '"
				+ bussinessid + "' and BSTAT = 'A' AND processstate <> '-1' ";
		/*
		 * 收付款重分配时如果选择"重置（到业务部门重新分配）"和"重置（财务部直接解除分配关系）"，则只需要判断清帐凭证（即bstat为a）；
		 * 如果选择"重置并冲销（到业务部门重新分配）"和"重置并冲销（到业务部门重新分配，过资金部）"时，需要判断清帐凭证以及收付款凭证（即voucherclass为1）
		 */
		if(reassignMethod.equals(ReassignConstant.RESET_AND_CLEAR) || reassignMethod.equals(ReassignConstant.RESET_AND_CLEAR_TO_CASH)){
			sql = "select VOUCHERNO voucherno, FIYEAR fiyear, COMPANYCODE bukrs from YVOUCHER where BUSINESSID = '"
				+ bussinessid + "' and (BSTAT = 'A' or VOUCHERCLASS ='1') AND processstate <> '-1' ";
		}
		List<Map<String, String>> jdbcRetList = this.getJdbcTemplate().queryForList(sql);
		if (jdbcRetList != null) {
			for (int i = 0; i < jdbcRetList.size(); i++) {
				CertificateNo certificateNo = new CertificateNo();
				certificateNo.setBelnr(jdbcRetList.get(i).get("voucherno"));
				certificateNo.setBukrs(jdbcRetList.get(i).get("bukrs"));
				certificateNo.setGjahr(jdbcRetList.get(i).get("fiyear"));
				certificateNoList.add(certificateNo);
			}
		}
		
		/*
         * 若为"重置并冲销（到业务部门重新分配）"或"冲销（财务部冲销并作废）"
         */
		if(reassignMethod.equals(ReassignConstant.RESET_AND_CLEAR) || reassignMethod.equals(ReassignConstant.FI_CLEAR)){
		    sql = "select VOUCHERNO voucherno, FIYEAR fiyear, COMPANYCODE bukrs from YVOUCHER where BUSINESSID = '"
		        + bussinessid + "' and replace(BSTAT,NULL,'') is not null and BSTAT <> 'A' AND processstate <> '-1' ";
		    tempRetList = this.getJdbcTemplate().queryForList(sql);
		    if (tempRetList != null) {
		        for (int i = 0; i < tempRetList.size(); i++) {
		            CertificateNo certificateNo = new CertificateNo();
		            certificateNo.setBelnr(tempRetList.get(i).get("voucherno"));
		            certificateNo.setBukrs(tempRetList.get(i).get("bukrs"));
		            certificateNo.setGjahr(tempRetList.get(i).get("fiyear"));
		            if(!certificateNoList.contains(certificateNo)){
		                certificateNoList.add(certificateNo);
		            }
		        }
		    }
		    if(reassignType.equals(ReassignConstant.COLLECT)){        //【收款重分配】
		        // 查找票清款凭证号
		        sql = "select VOUCHERNO voucherno, FIYEAR fiyear, COMPANYCODE bukrs from YVOUCHER where  processstate <> '-1' and BUSINESSID in "
		            + "(select BILLCLEARID BUSINESSID from YBILLINCOLLECT where COLLECTITEMID in "
		            + "(select COLLECTITEMID from YCOLLECTITEM where COLLECTID = '"
		            + bussinessid + "') )";
		        tempRetList.clear();
		        tempRetList = this.getJdbcTemplate().queryForList(sql);
		        if (tempRetList != null) {
		            for (int i = 0; i < tempRetList.size(); i++) {
		                CertificateNo certificateNo = new CertificateNo();
		                certificateNo.setBelnr(tempRetList.get(i).get("voucherno"));
		                certificateNo.setBukrs(tempRetList.get(i).get("bukrs"));
		                certificateNo.setGjahr(tempRetList.get(i).get("fiyear"));
		                if(!certificateNoList.contains(certificateNo)){
		                    certificateNoList.add(certificateNo);
		                }
		            }
		        }
		        // 查找客户单清帐凭证号
		        sql = "select VOUCHERNO voucherno, FIYEAR fiyear, COMPANYCODE bukrs from YVOUCHER where  processstate <> '-1' and BUSINESSID in "
		            + "(select UNCLEARCOLLECTID BUSINESSID from YUNCLEARCOLLECT where COLLECTITEMID in "
		            + "(select COLLECTITEMID from YCOLLECTITEM where COLLECTID = '"
		            + bussinessid + "') )";
		        tempRetList.clear();
		        tempRetList = this.getJdbcTemplate().queryForList(sql);
		        if (tempRetList != null) {
		            for (int i = 0; i < tempRetList.size(); i++) {
		                CertificateNo certificateNo = new CertificateNo();
		                certificateNo.setBelnr(tempRetList.get(i).get("voucherno"));
		                certificateNo.setBukrs(tempRetList.get(i).get("bukrs"));
		                certificateNo.setGjahr(tempRetList.get(i).get("fiyear"));
		                if(!certificateNoList.contains(certificateNo)){
		                    certificateNoList.add(certificateNo);
		                }
		            }
		        }
		    }else if(reassignType.equals(ReassignConstant.PAYMENT)){      //【付款重分配】
		        // 查找票清款凭证号
		        sql = "select VOUCHERNO voucherno, FIYEAR fiyear, COMPANYCODE bukrs from YVOUCHER where  processstate <> '-1' and BUSINESSID in "
		            + "(select BILLCLEARID BUSINESSID from YBILLINPAYMENT where PAYMENTITEMID in "
		            + "(select PAYMENTITEMID from YPAYMENTITEM where PAYMENTID = '"
		            + bussinessid + "') )";
		        tempRetList.clear();
		        tempRetList = this.getJdbcTemplate().queryForList(sql);
		        if (tempRetList != null) {
		            for (int i = 0; i < tempRetList.size(); i++) {
		                CertificateNo certificateNo = new CertificateNo();
		                certificateNo.setBelnr(tempRetList.get(i).get("voucherno"));
		                certificateNo.setBukrs(tempRetList.get(i).get("bukrs"));
		                certificateNo.setGjahr(tempRetList.get(i).get("fiyear"));
		                if(!certificateNoList.contains(certificateNo)){
		                    certificateNoList.add(certificateNo);
		                }
		            }
		        }
		        // 查找供应商单清帐凭证号
		        sql = "select VOUCHERNO voucherno, FIYEAR fiyear, COMPANYCODE bukrs from YVOUCHER where  processstate <> '-1' and BUSINESSID in "
		            + "(select UNCLEARPAYMENTID BUSINESSID from YUNCLEARPAYMENT where PAYMENTITEMID in "
		            + "(select PAYMENTITEMID from YPAYMENTITEM where PAYMENTID = '"
		            + bussinessid + "') )";
		        tempRetList.clear();
		        tempRetList = this.getJdbcTemplate().queryForList(sql);
		        if (tempRetList != null) {
		            for (int i = 0; i < tempRetList.size(); i++) {
		                CertificateNo certificateNo = new CertificateNo();
		                certificateNo.setBelnr(tempRetList.get(i).get("voucherno"));
		                certificateNo.setBukrs(tempRetList.get(i).get("bukrs"));
		                certificateNo.setGjahr(tempRetList.get(i).get("fiyear"));
		                if(!certificateNoList.contains(certificateNo)){
		                    certificateNoList.add(certificateNo);
		                }
		            }
		        }
		    }else if(reassignType.equals(ReassignConstant.BILLCLEARPAYMENT)){     //【票清付款重分配】
		        // 查找票清款凭证号
                sql = "select VOUCHERNO voucherno, FIYEAR fiyear, COMPANYCODE bukrs from YVOUCHER where  processstate <> '-1' and BUSINESSID in "
                    + "(select BILLCLEARID BUSINESSID from YBILLINPAYMENT where PAYMENTITEMID in "
                    + "(select BILLCLEARITEMID from YBILLCLEARITEM where BILLCLEARID = '"
                    + bussinessid + "') )";
                tempRetList.clear();
                tempRetList = this.getJdbcTemplate().queryForList(sql);
                if (tempRetList != null) {
                    for (int i = 0; i < tempRetList.size(); i++) {
                        CertificateNo certificateNo = new CertificateNo();
                        certificateNo.setBelnr(tempRetList.get(i).get("voucherno"));
                        certificateNo.setBukrs(tempRetList.get(i).get("bukrs"));
                        certificateNo.setGjahr(tempRetList.get(i).get("fiyear"));
                        if(!certificateNoList.contains(certificateNo)){
                            certificateNoList.add(certificateNo);
                        }
                    }
                }
                // 查找供应商单清帐凭证号
                sql = "select VOUCHERNO voucherno, FIYEAR fiyear, COMPANYCODE bukrs from YVOUCHER where  processstate <> '-1' and BUSINESSID in "
                    + "(select UNCLEARPAYMENTID BUSINESSID from YUNCLEARPAYMENT where PAYMENTITEMID in "
                    + "(select BILLCLEARITEMID from YBILLCLEARITEM where BILLCLEARID = '"
                    + bussinessid + "') )";
                tempRetList.clear();
                tempRetList = this.getJdbcTemplate().queryForList(sql);
                if (tempRetList != null) {
                    for (int i = 0; i < tempRetList.size(); i++) {
                        CertificateNo certificateNo = new CertificateNo();
                        certificateNo.setBelnr(tempRetList.get(i).get("voucherno"));
                        certificateNo.setBukrs(tempRetList.get(i).get("bukrs"));
                        certificateNo.setGjahr(tempRetList.get(i).get("fiyear"));
                        if(!certificateNoList.contains(certificateNo)){
                            certificateNoList.add(certificateNo);
                        }
                    }
                }
		    }else if(reassignType.equals(ReassignConstant.BILLCLEARCOLLECT)){     //【票清收款重分配】 
		     // 查找票清款凭证号
                sql = "select VOUCHERNO voucherno, FIYEAR fiyear, COMPANYCODE bukrs from YVOUCHER where  processstate <> '-1' and BUSINESSID in "
                    + "(select BILLCLEARID BUSINESSID from YBILLINCOLLECT where COLLECTITEMID in "
                    + "(select BILLCLEARITEMID from YBILLCLEARITEM where BILLCLEARID = '"
                    + bussinessid + "') )";
                tempRetList.clear();
                tempRetList = this.getJdbcTemplate().queryForList(sql);
                if (tempRetList != null) {
                    for (int i = 0; i < tempRetList.size(); i++) {
                        CertificateNo certificateNo = new CertificateNo();
                        certificateNo.setBelnr(tempRetList.get(i).get("voucherno"));
                        certificateNo.setBukrs(tempRetList.get(i).get("bukrs"));
                        certificateNo.setGjahr(tempRetList.get(i).get("fiyear"));
                        if(!certificateNoList.contains(certificateNo)){
                            certificateNoList.add(certificateNo);
                        }
                    }
                }
                // 查找客户单清帐凭证号
                sql = "select VOUCHERNO voucherno, FIYEAR fiyear, COMPANYCODE bukrs from YVOUCHER where  processstate <> '-1' and BUSINESSID in "
                    + "(select UNCLEARCOLLECTID BUSINESSID from YUNCLEARCOLLECT where COLLECTITEMID in "
                    + "(select BILLCLEARITEMID from YBILLCLEARITEM where BILLCLEARID = '"
                    + bussinessid + "') )";
                tempRetList.clear();
                tempRetList = this.getJdbcTemplate().queryForList(sql);
                if (tempRetList != null) {
                    for (int i = 0; i < tempRetList.size(); i++) {
                        CertificateNo certificateNo = new CertificateNo();
                        certificateNo.setBelnr(tempRetList.get(i).get("voucherno"));
                        certificateNo.setBukrs(tempRetList.get(i).get("bukrs"));
                        certificateNo.setGjahr(tempRetList.get(i).get("fiyear"));
                        if(!certificateNoList.contains(certificateNo)){
                            certificateNoList.add(certificateNo);
                        }
                    }
                }
		    }
		}


		return certificateNoList;
	}

	/**
	 * 根据业务ID获取凭证号列表
	 * @param bussinessid
	 * @return
	 */
	public List<CertificateNo> getVoucherbyBussinessID2(String bussinessid,
			String reassignType, String reassignMethod) {
		List<CertificateNo> certificateNoList = new ArrayList<CertificateNo>();
		List<Map<String, String>> tempRetList = new ArrayList<Map<String, String>>();
		String sql ="";
		 /**
         * 重分配收款单
         */
        if(reassignType.equals(ReassignConstant.COLLECT) || reassignType.equals(ReassignConstant.PAYMENT)){
        	sql = "select VOUCHERNO voucherno, FIYEAR fiyear, COMPANYCODE bukrs from YVOUCHER where BUSINESSID = '"
				+ bussinessid + "' and (BSTAT = 'A' or VOUCHERCLASS ='1')";
        }         
        if(reassignType.equals(ReassignConstant.BILLCLEARCOLLECT) || reassignType.equals(ReassignConstant.BILLCLEARPAYMENT) || reassignType.equals(ReassignConstant.CUSTOMERREFUNDMENT) || reassignType.equals(ReassignConstant.SUPPLIERREFUNDMENT) || reassignType.equals(ReassignConstant.CUSTOMERSINGLECLEAR) || reassignType.equals(ReassignConstant.SUPPLIERSINGLECLEAR)){
        	sql = "select VOUCHERNO voucherno, FIYEAR fiyear, COMPANYCODE bukrs from YVOUCHER where BUSINESSID = '"
    			+ bussinessid + "'";   
        }  
        tempRetList.clear();
        tempRetList = this.getJdbcTemplate().queryForList(sql);
        if (tempRetList != null) {
            for (int i = 0; i < tempRetList.size(); i++) {
                CertificateNo certificateNo = new CertificateNo();
                certificateNo.setBelnr(tempRetList.get(i).get("voucherno"));
                certificateNo.setBukrs(tempRetList.get(i).get("bukrs"));
                certificateNo.setGjahr(tempRetList.get(i).get("fiyear"));
                if(!certificateNoList.contains(certificateNo)){
                    certificateNoList.add(certificateNo);
                }
            }
        }
		return certificateNoList;
	}
	/**
	 * 删除收款清票，把收款修改成预收款,isclear=0
	 * @param collectId
	 */
	public void resetDelForCollect(String collectId){
		//-删除清票关系
		String sql="delete from YCOLLECTCBILL A where A.Collectid = '"+collectId+"'";
		this.getJdbcTemplate().execute(sql);	
		//更新预收款金额等于分配金额,未清状态为0
		
		String sql2="update ycollectitem CO set CO.isclear ='0',CO.PREBILLAMOUNT=CO.ASSIGNAMOUNT-co.suretybond where collectid='"+ collectId + "'";
		this.getJdbcTemplate().execute(sql2);	
	}
	/**
	 * 删除收款清票，把付款修改成预付款isclear=0
	 * @param paymentId
	 */
	public void resetDelForPayment(String paymentId){
		//-删除清票关系
		String sql="delete from ypaymentcbill A where A.Paymentid = '"+paymentId+"'";
		this.getJdbcTemplate().execute(sql);	
		//更新预付款金额等于分配金额,未清状态为0
		
		String sql2="update ypaymentitem CO set CO.Billisclear ='0',CO.Prepayamount=co.assignamount where co.paymentid ='"+ paymentId + "'";
		this.getJdbcTemplate().execute(sql2);	
	}
	/**
	 * 取消收款单的开票的已清标识
	 * @param collectNo
	 */
	public void  resetClearFlagForCollect( String collectId ,String isclear)
	{
		
		String sql = "update YCUSTOMERTITLE set ISCLEARED = '"+isclear+"' where INVOICE in " +
				"(select BILLNO from YCOLLECTCBILL where COLLECTID = '" + 
				collectId + "')";
		
		
		this.getJdbcTemplate().execute(sql);	
		
		/*
         * 取消收款的已清标识（根据公司代码、凭证号、会计年度、行项编号）
         */
		String sql2 = "SELECT A.COMPANYCODE, A.VOUCHERNO, A.FIYEAR, B.ROWNUMBER  FROM YVOUCHER A, YVOUCHERITEM B, "+
		" (SELECT ci.COLLECTITEMID, co.collectid   FROM ycollect co,ycollectitem ci        WHERE co.collectid=ci.collectid	AND co.collectid='"+collectId+"') C "+
		" WHERE a.businessid=c.collectid   AND A.VOUCHERID = B.VOUCHERID   AND B.BUSINESSITEMID = C.COLLECTITEMID	 AND TRIM(a.voucherno) IS NOT NULL ";
		StringBuffer sql3 = new StringBuffer("update YCUSTOMERTITLE set ISCLEARED = '"+isclear+"' where ");
		List<Map<String,String>> list = this.getJdbcTemplate().queryForList(sql2);
		for(int i=0;i<list.size();i++){
		    String bukrs = list.get(i).get("COMPANYCODE");    // 公司代码
		    String belnr = list.get(i).get("VOUCHERNO");      // 凭证号
		    String gjahr = list.get(i).get("FIYEAR");         // 会计年度
		    String buzei = list.get(i).get("ROWNUMBER");      // 行项编号
		    // 行项编号不足3位要补足3位
		    if(buzei.length()==1){      
		        buzei = "00" + buzei;    
		    }else if(buzei.length()==2){
		        buzei = "0" + buzei;
		    }
		    sql3.append("(BUKRS='" + bukrs + "' AND BELNR='" + belnr + "' AND GJAHR='" + gjahr + "' AND BUZEI='" + buzei + "')");
		    if(i != list.size()-1){
		        sql3.append(" OR ");
		    }
		}
		this.getJdbcTemplate().execute(sql3.toString());
		
	}
	
	/**
	 * 取消付款单的发票校验的已清标识
	 * @param paymentId
	 */
	public void resetClearFlagForPayment( String paymentId ,String isclear)
	{
		String sql = "update YVENDORTITLE set ISCLEARED = '"+isclear+"' where INVOICE in " +
		"(select BILLNO from YPAYMENTCBILL where PAYMENTID = '" + 
		paymentId + "')";

		this.getJdbcTemplate().execute(sql);	
		
		/*
         * 取消付款的已清标识（根据公司代码、凭证号、会计年度、行项编号）
         */
		String sql2 = "SELECT A.COMPANYCODE, A.VOUCHERNO, A.FIYEAR, B.ROWNUMBER  FROM YVOUCHER A, YVOUCHERITEM B, "+
				" (SELECT pi.paymentitemid, p.paymentid   FROM ypayment p,ypaymentitem pi WHERE p.paymentid=pi.paymentid AND p.paymentid='"+ paymentId +"' ) C "+
				" WHERE a.businessid=c.paymentid   AND A.VOUCHERID = B.VOUCHERID   AND B.BUSINESSITEMID = C.paymentitemid	 AND TRIM(a.voucherno) IS NOT NULL ";
		StringBuffer sql3 = new StringBuffer("update YCUSTOMERTITLE set ISCLEARED = '"+isclear+"' where ");
		List<Map<String,String>> list = this.getJdbcTemplate().queryForList(sql2);
		for(int i=0;i<list.size();i++){
		    String bukrs = list.get(i).get("COMPANYCODE");    // 公司代码
		    String belnr = list.get(i).get("VOUCHERNO");      // 凭证号
		    String gjahr = list.get(i).get("FIYEAR");         // 会计年度
		    String buzei = list.get(i).get("ROWNUMBER");      // 行项编号
		    // 行项编号不足3位要补足3位
		    if(buzei.length()==1){      
		        buzei = "00" + buzei;    
		    }else if(buzei.length()==2){
		        buzei = "0" + buzei;
		    }
		    sql3.append("(BUKRS='" + bukrs + "' AND BELNR='" + belnr + "' AND GJAHR='" + gjahr + "' AND BUZEI='" + buzei + "')");
		    if(i != list.size()-1){
		        sql3.append(" OR ");
		    }
		}
		this.getJdbcTemplate().execute(sql3.toString());
		
	}
	
	/**
	 * 取消开票及收款单的已清标识(票清收款)
	 * @param billCCollectId
	 */
    public void resetFlagForBillCCollect(String billClearId,String isclear){
		/*
		 * 取消开票的已清标识（根据发票号）
		 */
		String sql = "update YCUSTOMERTITLE set ISCLEARED = '"+isclear+"' where INVOICE in " +
				     "(select INVOICE from YBILLCLEARITEM where BILLCLEARID ='" + billClearId + "' )" ;
		this.getJdbcTemplate().execute(sql);
		
		/*
		 * 取消收款单的已清标识
		 */
		sql = "update YCOLLECTITEM set ISCLEAR = '"+isclear+"' where COLLECTITEMID in " + 
		      "( select COLLECTITEMID from YBILLINCOLLECT where BILLCLEARID ='" + billClearId  +"')";
		this.getJdbcTemplate().execute(sql);
		
		/*
         * 取消开票的已清标识（根据公司代码、凭证号、会计年度、行项编号）
         */
		String sql2 = "SELECT a.COMPANYCODE,a.VOUCHERNO,a.FIYEAR,b.ROWNUMBER FROM YVOUCHER A, YVOUCHERITEM B," + 
		              "(SELECT COLLECTITEMID, VOUCHERNO FROM YBILLINCOLLECT WHERE BILLCLEARID = '" + billClearId + 
		              "') C WHERE A.VOUCHERNO = C.VOUCHERNO AND A.VOUCHERID = B.VOUCHERID AND B.BUSINESSITEMID = C.COLLECTITEMID";
		StringBuffer sql3 = new StringBuffer("update YCUSTOMERTITLE set ISCLEARED = '"+isclear+"' where ");
		List<Map<String,String>> list = this.getJdbcTemplate().queryForList(sql2);
		for(int i=0;i<list.size();i++){
		    String bukrs = list.get(i).get("COMPANYCODE");    // 公司代码
		    String belnr = list.get(i).get("VOUCHERNO");      // 凭证号
		    String gjahr = list.get(i).get("FIYEAR");         // 会计年度
		    String buzei = list.get(i).get("ROWNUMBER");      // 行项编号
		    // 行项编号不足3位要补足3位
		    if(buzei.length()==1){      
		        buzei = "00" + buzei;    
		    }else if(buzei.length()==2){
		        buzei = "0" + buzei;
		    }
		    sql3.append("(BUKRS='" + bukrs + "' AND BELNR='" + belnr + "' AND GJAHR='" + gjahr + "' AND BUZEI='" + buzei + "')");
		    if(i != list.size()-1){
		        sql3.append(" OR ");
		    }
		}
		this.getJdbcTemplate().execute(sql3.toString());
	}

	/**
	 * 取消发票校验及付款单的已清标识
	 * @param billClearId
	 */
	public void resetFlagForBillCPayment( String billClearId,String isclear ){		
		/*
		 * 取消开票的已清标识（根据发票号）
		 */
		String sql = "update YVENDORTITLE set ISCLEARED = '"+isclear+"' where INVOICE in " +
		             "(select INVOICE from YBILLCLEARITEM where BILLCLEARID ='" + billClearId + "')" ;
		this.getJdbcTemplate().execute(sql);
		
		/*
		 * 取消付款单的已清标识
		 */
		sql = "update YPAYMENTITEM set BILLISCLEAR = '"+isclear+"' where PAYMENTITEMID in " + 
		      "(select PAYMENTITEMID from YBILLINPAYMENT where BILLCLEARID ='" + billClearId +"')";
		this.getJdbcTemplate().execute(sql);
		
		/*
         * 取消开票的已清标识（根据公司代码、凭证号、会计年度、行项编号）
         */
        String sql2 = "SELECT a.COMPANYCODE,a.VOUCHERNO,a.FIYEAR,b.ROWNUMBER FROM YVOUCHER A, YVOUCHERITEM B," + 
                      "(SELECT PAYMENTITEMID, VOUCHERNO FROM YBILLINPAYMENT WHERE BILLCLEARID = '" + billClearId + 
                      "') C WHERE A.VOUCHERNO = C.VOUCHERNO AND A.VOUCHERID = B.VOUCHERID AND B.BUSINESSITEMID = C.PAYMENTITEMID";
        StringBuffer sql3 = new StringBuffer("update YCUSTOMERTITLE set ISCLEARED = '"+isclear+"' where ");
        List<Map<String,String>> list = this.getJdbcTemplate().queryForList(sql2);
        for(int i=0;i<list.size();i++){
            String bukrs = list.get(i).get("COMPANYCODE");    // 公司代码
            String belnr = list.get(i).get("VOUCHERNO");      // 凭证号
            String gjahr = list.get(i).get("FIYEAR");         // 会计年度
            String buzei = list.get(i).get("ROWNUMBER");      // 行项编号
            // 行项编号不足3位要补足3位
            if(buzei.length()==1){      
                buzei = "00" + buzei;    
            }else if(buzei.length()==2){
                buzei = "0" + buzei;
            }
            sql3.append("(BUKRS='" + bukrs + "' AND BELNR='" + belnr + "' AND GJAHR='" + gjahr + "' AND BUZEI='" + buzei + "')");
            if(i != list.size()-1){
                sql3.append(" OR ");
            }
        }
        this.getJdbcTemplate().execute(sql3.toString());
	}

	/**
	 * 取消退款退款单的收款单或付款单的已清标识
	 * @param refundmentId
	 */
    public void resetFlagForRefundment(String refundmentId,String isclear){
        /**
         * 获取退款ID关联的收款或付款Itemid
         */
        String sql = "select COLLECTITEMID collectItemid, PAYMENTITEMID paymentItemid from YREFUNDMENTITEM" + " where REFUNDMENTID = '"
                + refundmentId + "'";
        List<Map<String, String>> list = this.getJdbcTemplate().queryForList(sql);
        if(list != null && list.size() > 0){
            /**
             * 客户退款
             */
            if(!list.get(0).get("collectItemid").trim().equals("")){
                /**
                 * 拼收款行项目ID集合字符串
                 */
                String collectItemIds = "";

                for(int i = 0; i < list.size(); i++){
                    collectItemIds += "'" + list.get(i).get("collectItemid") + "'";
                    if((i + 1) < list.size()){
                        collectItemIds += ",";
                    }
                }

                sql = "update YCOLLECTITEM set ISCLEAR = '"+isclear+"' where " + "COLLECTITEMID in (" + collectItemIds + ")";

                this.getJdbcTemplate().execute(sql);
            }
            /**
             * 供应商退款
             */
            else{
                /**
                 * 拼付款款行项目ID集合字符串
                 */
                String paymentItemIds = "";

                for(int i = 0; i < list.size(); i++){
                    paymentItemIds += "'" + list.get(i).get("paymentItemid") + "'";
                    if((i + 1) < list.size()){
                        paymentItemIds += ",";
                    }
                }

                sql = "update YPAYMENTITEM set BILLISCLEAR = '"+isclear+"' where " + "PAYMENTITEMID in (" + paymentItemIds + ")";

                this.getJdbcTemplate().execute(sql);
            }
        }
    }
    
    /**
     * @创建作者：邱杰烜
     * @创建时间：2010-11-24
     * 取消客户单清的已清标识
     */
    public void resetClearFlagForCSC(String cscId,String isclear){
        // 取消客户单清对应的"客户未清"数据的已清标识
        String sql1 = "update YCUSTOMERTITLE set ISCLEARED = '"+isclear+"' where INVOICE in " +
                      "(select BILLNO from YUNCLEARCUSTBILL where CUSTOMSCLEARID ='" + cscId + "' )";
        // 取消客户单清对应的"客户未清"数据的已清标识
        String sql3 = "update YCUSTOMERTITLE set ISCLEARED = '"+isclear+"' where customertitleid in " +
                      "(select customertitleid from YUNCLEARCOLLECT where CUSTOMSCLEARID ='" + cscId + "' )";
        // 取消客户单清对应的"收款金额行项"数据的已清标识
        String sql2 = "update YCOLLECTITEM set ISCLEAR = '"+isclear+"' where COLLECTITEMID in " + 
                      "(select COLLECTITEMID from YUNCLEARCOLLECT where CUSTOMSCLEARID ='" + cscId  +"')";
     // 取消供应商单清对应的"付款金额行项"数据的已清标识
        String sql4 = "update YPAYMENTITEM set BILLISCLEAR = '"+isclear+"' where PAYMENTITEMID in " + 
                      "(select PAYMENTITEMID from YUNCLEARCUSTBILL where CUSTOMSCLEARID  ='" + cscId +"')";
        
        this.getJdbcTemplate().execute(sql1);
        this.getJdbcTemplate().execute(sql2);
        this.getJdbcTemplate().execute(sql3);
        this.getJdbcTemplate().execute(sql4);
    }
    
    /**
     * @创建作者：邱杰烜
     * @创建时间：2010-11-24
     * 取消供应商单清的已清标识
     */
    public void resetClearFlagForSSC(String sscId,String isclear){
        // 取消供应商单清对应的"供应商未清"数据的已清标识
        String sql1 = "update YVENDORTITLE set ISCLEARED = '"+isclear+"' where INVOICE in " +
                      "(select INVOICE from YUNCLEARSUPPBILL where SUPPLIERSCLEARID ='" + sscId + "')";
     // 取消供应商单清对应的"供应商未清"数据的已清标识
        String sql3 = "update YVENDORTITLE set ISCLEARED = '"+isclear+"' where vendortitleid in " +
                      "(select vendortitleid from yunclearpayment where SUPPLIERSCLEARID ='" + sscId + "')";
        // 取消供应商单清对应的"付款金额行项"数据的已清标识
        String sql2 = "update YPAYMENTITEM set BILLISCLEAR = '"+isclear+"' where PAYMENTITEMID in " + 
                      "(select PAYMENTITEMID from YUNCLEARPAYMENT where SUPPLIERSCLEARID ='" + sscId +"')";
        this.getJdbcTemplate().execute(sql1);
        this.getJdbcTemplate().execute(sql2);
        this.getJdbcTemplate().execute(sql3);
    }
	
	/**
	 * 废除原有单号
	 * 将业务状态（BUSINESSSTATE）设为 -1，标志为作废
	 * @param reassigntype：重分配类型
	 * @param boId
	 */
    public void abolishBoNo(String reassignType, String boId){
        String sql = "";
        /**
         * 重分配收款单
         */
        if(reassignType.equals(ReassignConstant.COLLECT)){
            sql = "update YCOLLECT set BUSINESSSTATE = '" + BusinessState.BLANKOUT + "' where COLLECTID = '" + boId + "'";
            this.getJdbcTemplate().execute(sql);
        }
        /**
         * 重分配付款单
         */
        else if(reassignType.equals(ReassignConstant.PAYMENT)){
            sql = "update YPAYMENT set BUSINESSSTATE = '" + BusinessState.BLANKOUT + "' where PAYMENTID = '" + boId + "'";
            this.getJdbcTemplate().execute(sql);
        }
        /**
         * 票清收款或票清付款
         */
        else if(reassignType.equals(ReassignConstant.BILLCLEARCOLLECT) || reassignType.equals(ReassignConstant.BILLCLEARPAYMENT)){
            sql = "update YBILLCLEAR set BUSINESSSTATE = '" + BusinessState.BLANKOUT + "' where BILLCLEARID = '" + boId + "'";
            this.getJdbcTemplate().execute(sql);
        }
        /**
         * 重分配退款单
         */
        else if(reassignType.equals(ReassignConstant.CUSTOMERREFUNDMENT) || reassignType.equals(ReassignConstant.SUPPLIERREFUNDMENT)){
            /*
             * 设置退款单位作废状态
             */
            sql = "update YREFUNDMENT set BUSINESSSTATE = '" + BusinessState.BLANKOUT + "' where REFUNDMENTID = '" + boId + "'";
            this.getJdbcTemplate().execute(sql);
            /*
             * 将更新收款，付款分配表的货款金额和保证金金额
             */
            if(reassignType.equals(ReassignConstant.CUSTOMERREFUNDMENT)){ // 客户退款
                // 将退款金额加回给货款金额或保证金金额
                this.resetAmountForCustomerDb(boId);
            }else{ // 供应商退款
                // 将退款金额加回给货款金额
                this.resetAmountForSupplierDb(boId);
            }
        }
        /**
         * 重分配客户单清
         */
        else if(reassignType.equals(ReassignConstant.CUSTOMERSINGLECLEAR)){
            sql = "update YCUSTOMSICLEAR set BUSINESSSTATE = '" + BusinessState.BLANKOUT + "' where CUSTOMSCLEARID = '" + boId + "'";
            this.getJdbcTemplate().execute(sql);
        }
        /**
         * 重分配供应商单清
         */
        else if(reassignType.equals(ReassignConstant.SUPPLIERSINGLECLEAR)){
            sql = "update YSUPPLIERSICLEAR set BUSINESSSTATE = '" + BusinessState.BLANKOUT + "' where SUPPLIERSCLEARID = '" + boId + "'";
            this.getJdbcTemplate().execute(sql);
        }
    }

	
	/**
	 * 获取业务单号ID
	 * @param reassignid
	 * @return
	 */
	public String getBoidByReassignid(String reassignid)
	{
		String sql = "select REASSIGNBOID boid from YREASSIGN where REASSIGNID = '" +
					reassignid + "'";
		List<Map<String,String>> list = this.getJdbcTemplate().queryForList(sql);
		
		return list.get(0).get("boid");
		
	}

	/**
	 * 获取贸易方式
	 * @param paymentId
	 * @return
	 */
	public String getPaymentTradeType(String paymentId )
	{
		String sql = "select TRADE_TYPE tradeType from YPAYMENT where PAYMENTID = '" 
				+ paymentId + "'";
		
		List<Map<String,String>> list = this.getJdbcTemplate().queryForList(sql);
		return list.get(0).get("tradeType");
		
	}
	
	/**
	 * 获取退款类型
	 * @param refundmentId
	 * @return
	 *01: 客户退款； 02: 供应商退款 
	*/ 
	public String getRefundmentType(String refundmentId )
	{
		String sql = "select CUSTOMER customer, SUPPLIER supplier from YREFUNDMENT where REFUNDMENTID = '" 
			+ refundmentId + "'";
	
		List<Map<String,String>> list = this.getJdbcTemplate().queryForList(sql);
		
		//供应商退款退款
		if( list.get(0).get("customer") == null || list.get(0).get("customer").trim().equals("") )
		{
			return "02";
		}
		else
		{
			return "01";
		}
	}
	
	/**
	 * 保存新产生的单号ID
	 * @param oldBoId
	 * @param newBoId
	 */
	public void setNewBoId(String oldBoId, String newBoId )
	{
		String sql = "update YREASSIGN set NEWREASSIGNBOID = '" +
				newBoId + "'";
		
		this.getJdbcTemplate().execute(sql);
	}

	/**
	 * 根据实际重分配业务对象ID取得重分配id
	 * @param boid
	 * @return
	 */
	public String getReassignidByBoId(String boid )
	{
		String sql = "select REASSIGNID reassignid from YREASSIGN where REASSIGNBOID = '" +
				boid + "'";
		
		List<Map<String,String>> list = this.getJdbcTemplate().queryForList(sql);
		if(list != null && !list.isEmpty())
			return list.get(0).get("reassignid");
		else
			return "";
		
	}

	/**
	 * 根据被重分配业务对象ID,找到重分配业务对象ID（"客户单清"、"供应商单清"不会进来）
	 * @param reassignType
	 * @param oldNoId
	 * @return
	 */
    public String getNewNoIdByOldNoId(String reassignType, String oldNoId){
        String sql = "";
        if(reassignType.equals(ReassignConstant.COLLECT)){
            sql = "select COLLECTID id from YCOLLECT where OLDCOLLECTID = '" + oldNoId + "'";
        }else if(reassignType.equals(ReassignConstant.PAYMENT)){
            sql = "select PAYMENTID id from YPAYMENT where REPAYMENTID = '" + oldNoId + "'";
        }else if(reassignType.equals(ReassignConstant.BILLCLEARCOLLECT)){
            sql = "select BILLCLEARID id from YBILLCLEAR where OLDBILLCLEARID = '" + oldNoId + "'";
        }else if(reassignType.equals(ReassignConstant.CUSTOMERREFUNDMENT) || reassignType.equals(ReassignConstant.SUPPLIERREFUNDMENT)){
            sql = "select REFUNDMENTID id from YREFUNDMENT where REASSIGNEDDBID = '" + oldNoId + "'";
        }

        List<Map<String, String>> list = this.getJdbcTemplate().queryForList(sql);
        return list.get(0).get("id");
    }


	/**
	 * 获得凭证id
	 * @param bussinessid
	 * @return
	 */
	public List<String> getVoucherId( String bussinessid )
	{
		String sql = "select VOUCHERID from YVOUCHER where BUSINESSID = '" + bussinessid + "'";
		List<Map<String,String>> voucherIdList = this.getJdbcTemplate().queryForList(sql);
		
		List<String> retList = new ArrayList<String>(0);
		if( voucherIdList != null )
		{
			for( int i = 0; i < voucherIdList.size(); i++ )
			{
				retList.add(voucherIdList.get(i).get("VOUCHERID"));
			}
		}
		
		return retList;
		
	}
	
	/**
	 * @创建作者：邱杰烜
	 * @创建日期：2010-10-28
	 * 根据业务ID与凭证分类取得凭证号
	 */
	public List<String> getVoucherIdByClass(String bussinessid) {
		String sql = "select VOUCHERID from YVOUCHER where BUSINESSID='" + bussinessid + "' AND VOUCHERCLASS='1'";
		List<Map<String, String>> voucherIdList = this.getJdbcTemplate().queryForList(sql);
		List<String> retList = new ArrayList<String>(0);
		if (voucherIdList != null) {
			for (int i = 0; i < voucherIdList.size(); i++) {
				retList.add(voucherIdList.get(i).get("VOUCHERID"));
			}
		}
		return retList;
	}

	/**
	 * 获得凭证id  ,除了清帐凭证
	 * @param bussinessid
	 * @return
	 */
	public List<String> getVoucherId2( String bussinessid )
	{
		String sql = "select VOUCHERID from YVOUCHER where ((BUSINESSID = '" + bussinessid 
		+ "'AND BSTAT =' ') OR (BUSINESSID = '"+bussinessid+"'AND BSTAT IS NULL ))";
		List<Map<String,String>> voucherIdList = this.getJdbcTemplate().queryForList(sql);
		
		List<String> retList = new ArrayList<String>(0);
		if( voucherIdList != null )
		{
			for( int i = 0; i < voucherIdList.size(); i++ )
			{
				retList.add(voucherIdList.get(i).get("VOUCHERID"));
			}
		}
		
		return retList;
		
	}
	/**
	 * 判断是否已经拷贝过了。
	 * @param id
	 * @return
	 */
    public String isCopyed(String boid, String reassignType){
        String sql = "";
        if(reassignType.equals(ReassignConstant.COLLECT)){
            sql = "select COLLECTID id from YCOLLECT where OLDCOLLECTID = '" + boid + "'";
        }else if(reassignType.equals(ReassignConstant.PAYMENT)){
            sql = "select PAYMENTID id from YPAYMENT where REPAYMENTID = '" + boid + "'";
        }
        /**
         * 票清款
         */
        else if(reassignType.equals(ReassignConstant.BILLCLEARCOLLECT) || reassignType.equals(ReassignConstant.BILLCLEARPAYMENT)){
            sql = "select BILLCLEARID id from YBILLCLEAR where OLDBILLCLEARID = '" + boid + "'";
        }else if(reassignType.equals(ReassignConstant.CUSTOMERREFUNDMENT) || reassignType.equals(ReassignConstant.SUPPLIERREFUNDMENT)){
            sql = "select REFUNDMENTID id from YREFUNDMENT where REASSIGNEDDBID = '" + boid + "'";
        }

        List<Map<String, String>> list = this.getJdbcTemplate().queryForList(sql);

        if(list != null && list.size() > 0){
            return list.get(0).get("id");
        }else{
            return null;
        }
    }

	/**
	 * 根据RFC返回信息，更新凭证抬头表
	 * @param rfcRetVoucherList
	 */
	public void updateVoucherInfo( List<Map<String, String>> rfcRetVoucherList )
	{
		String strVouchNo =  ""; 			// 清帐凭证号
		String strBukrs = "";				// 公司代码
		String strFiYear = "";				// 会计年度
		String strVoucherState = "";		// 凭证状态
		String strOffVouchNo = "";			// 冲销凭证号
		String strOffYear = "";				// 冲销会计年度
		
		for( Map<String, String> map : rfcRetVoucherList )
		{
			strVouchNo = map.get("BELNR");
			strVoucherState = map.get("BSTAT");
			strOffVouchNo = map.get("STBLG");
			strOffYear = map.get("STJAH");
			strBukrs = map.get("BUKRS");
			strFiYear = map.get("GJAHR");
			if (strOffYear.equals("")){
				strOffYear ="-";
			}
			if (strOffVouchNo.equals("")){
				strOffVouchNo ="-";
			}
			
			String sql = "update YVOUCHER set BSTAT = '" + strVoucherState + 
					"', OFFYEAR = '" + strOffYear + 
					"', OFFSETVOUCHERNO = '" + strOffVouchNo +
					"' where VOUCHERNO = '" + strVouchNo +
					"' and COMPANYCODE = '" + strBukrs +
					"' and FIYEAR = '" + strFiYear + "'";
			
			this.getJdbcTemplate().execute(sql);
		}
	}

	/**
	 * 设置重分配状态
	 * @param reassignId
	 * @param state	审核通过；审核不通过
	 */
	public void updateReassignState( String reassignId, String state ){
		String sql = "update YREASSIGN set BUSSINESSSTATE = '" + state +
				"' where REASSIGNID = '" + reassignId + "'";
		this.getJdbcTemplate().execute(sql);
	}

    public String getBusinessNoById(String reassignType, String businessId){
        String sql = "";
        if(reassignType.equals(ReassignConstant.COLLECT)){                  // 【重分配收款单】
            sql = "select COLLECTNO no from YCOLLECT" + " where COLLECTID = '" + businessId + "'";
        }else if(reassignType.equals(ReassignConstant.PAYMENT)){            // 【重分配付款单】
            sql = "select PAYMENTNO no from YPAYMENT" + " where PAYMENTID = '" + businessId + "'";
        }else if(reassignType.equals(ReassignConstant.BILLCLEARCOLLECT) || reassignType.equals(ReassignConstant.BILLCLEARPAYMENT)){     // 【票清收款或票清付款】
            sql = "select BILLCLEARNO no from YBILLCLEAR" + " where BILLCLEARID = '" + businessId + "'";
        }else if(reassignType.equals(ReassignConstant.CUSTOMERREFUNDMENT) || reassignType.equals(ReassignConstant.SUPPLIERREFUNDMENT)){ // 【重分配退款单】
            sql = "select REFUNDMENTNO no from YREFUNDMENT" + " where REFUNDMENTID = '" + businessId + "'";
        }else if(reassignType.equals(ReassignConstant.CUSTOMERSINGLECLEAR)){// 【重分配客户单清帐】
            sql = "select CUSTOMCLEARNO from YCUSTOMSICLEAR where CUSTOMSCLEARID ='" + businessId + "'";
        }else if(reassignType.equals(ReassignConstant.SUPPLIERSINGLECLEAR)){// 【重分配供应商单清帐】
            sql = "select SUPPLIERCLEARNO from YSUPPLIERSICLEAR where SUPPLIERSCLEARID ='" + businessId + "'";
        }

        String no = this.getJdbcTemplate().queryForObject(sql, String.class).toString();
        return no;
    }
	
	/**
	 * 获取原单据的部门ID
	 * @param reassignType
	 * @param businessId
	 * @return
	 */
    public String getDeptId(String reassignType, String businessId){
        String sql = "";
        if(reassignType.equals(ReassignConstant.COLLECT)){                  // 【重分配收款单】
            sql = "select dept_id from YCOLLECT" + " where COLLECTID = '" + businessId + "'";
        }else if(reassignType.equals(ReassignConstant.PAYMENT)){            // 【重分配付款单】
            sql = "select dept_id no from YPAYMENT" + " where PAYMENTID = '" + businessId + "'";
        }else if(reassignType.equals(ReassignConstant.BILLCLEARCOLLECT) || reassignType.equals(ReassignConstant.BILLCLEARPAYMENT)){// 【重分配票清收款或票清付款】
            sql = "select deptid no from YBILLCLEAR" + " where BILLCLEARID = '" + businessId + "'";
        }else if(reassignType.equals(ReassignConstant.CUSTOMERREFUNDMENT) || reassignType.equals(ReassignConstant.SUPPLIERREFUNDMENT)){// 【重分配退款单】
            sql = "select dept_id no from YREFUNDMENT" + " where REFUNDMENTID = '" + businessId + "'";
        }else if(reassignType.equals(ReassignConstant.CUSTOMERSINGLECLEAR)){// 【重分配客户单清帐】
//        	sql = "select DEPTID from YCUSTOMSICLEAR where CUSTOMSCLEARID ='" + businessId + "'"; // 原来取账务部门ID，现改根据业务范围取业务部门ID
            sql = "SELECT t.dept_id FROM YCUSTOMSICLEAR y, t_sys_dept t WHERE CUSTOMSCLEARID = '"+businessId+"' AND y.depid = t.dept_code";
        }else if(reassignType.equals(ReassignConstant.SUPPLIERSINGLECLEAR)){// 【重分配供应商单清帐】
//        	sql = "select DEPTID from YSUPPLIERSICLEAR where SUPPLIERSCLEARID ='" + businessId + "'";
            sql = "SELECT T.DEPT_ID FROM YSUPPLIERSICLEAR Y, T_SYS_DEPT T WHERE y.suppliersclearid = '"+businessId+"' AND Y.DEPID = T.DEPT_CODE";
        }

        String no = this.getJdbcTemplate().queryForObject(sql, String.class).toString();
        return no;
    }
	
	//**  yanghancai 2010-10-14
    public String getOcreator(String reassignType, String businessId){
        String sql = "";
        if(reassignType.equals(ReassignConstant.COLLECT)){                  // 【重分配收款单】
            sql = "select creator no from YCOLLECT" + " where COLLECTID = '" + businessId + "'";
        }else if(reassignType.equals(ReassignConstant.PAYMENT)){            // 【重分配付款单】
            sql = "select creator no from YPAYMENT" + " where PAYMENTID = '" + businessId + "'";
        }else if(reassignType.equals(ReassignConstant.BILLCLEARCOLLECT) || reassignType.equals(ReassignConstant.BILLCLEARPAYMENT)){      // 【重分配票清收付款】 
            sql = "select creator no from YBILLCLEAR" + " where BILLCLEARID = '" + businessId + "'";
        }else if(reassignType.equals(ReassignConstant.CUSTOMERREFUNDMENT) || reassignType.equals(ReassignConstant.SUPPLIERREFUNDMENT)){ // 【重分配退款单】
            sql = "select creator no from YREFUNDMENT" + " where REFUNDMENTID = '" + businessId + "'";
        }else if(reassignType.equals(ReassignConstant.CUSTOMERSINGLECLEAR)){// 【重分配客户单清帐】
            sql = "select creator from YCUSTOMSICLEAR where CUSTOMSCLEARID ='" + businessId + "'";
        }else if(reassignType.equals(ReassignConstant.SUPPLIERSINGLECLEAR)){// 【重分配供应商单清帐】
            sql = "select creator from YSUPPLIERSICLEAR where SUPPLIERSCLEARID ='" + businessId + "'";
        }

        String no = this.getJdbcTemplate().queryForObject(sql, String.class).toString();
        return no;
    }

	 
	/**
	 * 插入重分配关联收款单据表
	 * @param reassignId
	 * @param collectid
	 * @param amount
	 */
	public void insertCollectRelateForReassign(String reassignId, String collectid, String collectNo, BigDecimal amount)
	{
		/**
		 * 插入数据
		 */
		
		String uId = CodeGenerator.getUUID();
		
		String sql = "insert into YCOLLECTRELATED(REFUNDMENTID,UNNAMERCOLLECTID,SURETYBONDID,MANDT,COLLECTRELATEDID,COLLECTID,RELATEDNO,APPLYAMOUNT,RELATEDTYPE,REASSIGNID)" 
			+ " values(' ', ' ', ' ', '800', '" 
			+ uId + "', '"
			+ collectid + "', '"
			+ collectNo + "', '"    //yanghancai 2010-10-18
			+ amount 
			+ "', '01','" +
			reassignId + "')";
		
		this.getJdbcTemplate().execute(sql);
	}
	
	/**
	 * 插入重分配关联付款单据表
	 * @param reassignId
	 * @param paymentid
	 * @param amount
	 */
	public void insertPaymentRelateForReassign(String reassignId, String paymentid, String oldPaymentId, String paymentNo, BigDecimal amount)
	{
		/**
		 * 插入数据
		 */
		String uId = CodeGenerator.getUUID();
		
		String sql = "insert into YPAYMENTRELATED(MANDT,RELATEDPAYMENTID,PAYMENTID,REFUNDMENTID,RELATEDNO,APPLYAMOUNT,RELATEDTYPE,REASSIGNID)" 
			+ " values('800', '" 
			+ uId + "', '"
			+ paymentid + "', '"
			+ oldPaymentId + "', '"
			+ paymentNo + "', '"
			+ amount 
			+ "', '01', '" + reassignId +
					"')";		
		this.getJdbcTemplate().execute(sql);
	}

	/**
	 * 根据收款ID,获取发票号
	 * @param collectId
	 * @return
	 */
	public List<Map<String,Object>> getInvoiceListByCollectId( String collectId )
	{
		String sql = "select BILLNO billNo, DMBTR billAmount from YCOLLECTCBILL a," +
				"YCUSTOMERTITLE b where a.BILLNO = b.INVOICE and a.COLLECTID = '" + 
					collectId + "'";
		
		List<Map<String,Object>> list = this.getJdbcTemplate().queryForList(sql);
		
		return list;
	}
	
	/**
	 * 根据付款ID,获取发票号
	 * @param paymentId
	 * @return
	 */
	public List<Map<String,Object>> getInvoiceListByPaymentId( String paymentId )
	{
		String sql = "select BILLNO billNo, DMBTR billAmount from YPAYMENTCBILL a," +
			"YVENDORTITLE b where a.BILLNO = b.INVOICE and a.PAYMENTID = '" + 
			paymentId + "'";

		List<Map<String,Object>> list = this.getJdbcTemplate().queryForList(sql);

		return list;
	}
	
	/**
	 * 根据付款ID,更新paymentItem表的结清标志
	 * @param paymentId
	 */
	public void updatePaymentItemIsClearByPaymentId(String paymentId)
	{
		String sql = "update YPAYMENTITEM set BILLISCLEAR = '" + cleared.isCleared +
				"' where PAYMENTID = '" + paymentId +"' and PREPAYAMOUNT = 0";
		
		this.getJdbcTemplate().execute(sql);	
	}

	/**
	 * 找出清帐凭证相关的行项目ItemID(收款，付款分配表id)
	 * @param voucherNo： 凭证号
	 * @param burk：  公司代码
	 * @param fiFear :会计年度
	 * @return
	 */
	public List<Map<String,String>> getItemIdListByVoucherNo(String voucherNo, String burk, String fiFear )
	{	
		/**
		 * 查找清帐凭证所关联的普通凭证
		 */
		String sql = "select VOUCHERNO, FIYEAR from YVOUCHERITEM where VOUCHERID in " +
				"( select VOUCHERID from YVOUCHER where VOUCHERNO = '" + voucherNo + 
				"' and FIYEAR ='" + fiFear
				+ "' and COMPANYCODE = '" + burk + "')";
		
		List<Map<String,String>> lstVoucher = this.getJdbcTemplate().queryForList(sql);
		
		/**
		 * 查找普通凭证ID
		 */
		List<String> lstVoucherId = null;
		if( lstVoucher!= null )
		{
			lstVoucherId = new ArrayList<String>();
			for(Map<String,String> map : lstVoucher)
			{
				String tempVoucherNO = map.get("VOUCHERNO");
				String tempFiYear = map.get("FIYEAR");
				
				sql = "select VOUCHERID from YVOUCHER where VOUCHERNO = '" + tempVoucherNO
						+ "' and FIYEAR = '" + tempFiYear 
						+ "' and COMPANYCODE = '" + burk + "'";
				

				//yanghancai 2010-10-21
				List<Map<String,String>> lstVoucherIdTemp = this.getJdbcTemplate().queryForList(sql);
				if (lstVoucherIdTemp != null && lstVoucherIdTemp.size()>0){
				String voucherId = this.getJdbcTemplate().queryForObject(sql, String.class).toString();
				lstVoucherId.add(voucherId);
				}
			}
		}

		/**
		 * 查找普通凭证相关的itemId
		 */
		String strVoucherId = this.constructSqlCond(lstVoucherId);
		
		sql = "select BUSINESSITEMID itemId from YVOUCHERITEM where VOUCHERID in" +
		"(" + strVoucherId +  ")" ;

		List<Map<String,String>> itemIdList = this.getJdbcTemplate().queryForList(sql);
		return itemIdList;
	}
	
	/**
	 * 根据发票号计算清帐金额（收款,不包含单次）。
	 * 
	 * @param invoice: 发票号
	 * @param collectId: 收款ID
	 * @return
	 */
	public BigDecimal getSumClearAmountForCollect(String invoice, String collectId)
	{

		StringBuffer sb = new StringBuffer();
		// 票清收款
		sb.append("select nvl(sum(amount),0) from (");
		sb.append("select nvl(sum(clearbillamount),0) as amount from YBILLCLEARITEM a");
		sb.append(" where a.INVOICE='" + invoice + "'");
		sb.append(" union all");
		
		// 客户单清
		sb.append("select nvl(sum(CLEARAMOUNT),0) as amount from YUNCLEARCUSTBILL a ");
		sb.append(" where a.BILLNO='" + invoice + "'");
		sb.append(" union all");
		
		// 收款(清票)
		sb.append(" select nvl(sum( a.CLEARAMOUNT),0) as amount from YCOLLECTCBILL a ");
		sb.append(" where a.BILLNO='" + invoice + "' and a.COLLECTID <> '" + collectId +"'");
		sb.append(" )");

		log.debug("根据发票号计算清帐金额:" + sb.toString());
		BigDecimal list = (BigDecimal) this.getJdbcTemplate().queryForObject(sb.toString(), BigDecimal.class);

		if (null == list)
			return new BigDecimal(0);
		else
			return list;
	}
	
	/**
	 * 根据发票号计算票的清帐金额（票清收款,不包含单次）。
	 * 
	 * @param invoice: 发票号
	 * @param billClearId: 票清款Id
	 * @return
	 */
	public BigDecimal getSumBillClearAmountForBillClearCollect(String invoice, String billClearId)
	{

		StringBuffer sb = new StringBuffer();
		
		// 票清收款
		sb.append("select nvl(sum(amount),0) from (");
		sb.append("select nvl(sum(clearbillamount),0) as amount from YBILLCLEARITEM a");
		sb.append(" where a.INVOICE='" + invoice + "' and BILLCLEARID <> '" + billClearId + "'");
		sb.append(" union all");
		
		// 客户单清
		sb.append("select nvl(sum(CLEARAMOUNT),0) as amount from YUNCLEARCUSTBILL a ");
		sb.append(" where a.BILLNO='" + invoice + "'");
		sb.append(" union all");
		
		// 收款(清票)
		sb.append(" select nvl(sum( a.CLEARAMOUNT),0) as amount from YCOLLECTCBILL a ");
		sb.append(" where a.BILLNO='" + invoice + "'");
		sb.append(" )");

		log.debug("根据发票号计算清帐金额:" + sb.toString());
		BigDecimal list = (BigDecimal) this.getJdbcTemplate().queryForObject(sb.toString(), BigDecimal.class);

		if (null == list)
			return new BigDecimal(0);
		else
			return list;
	}
	
	/**
	 * 根据收款分配表id计算清帐金额(票清收款，不包含本次金额)。
	 * @param billClearId 票清款Id
	 * @param collectItemId: 收款分配Id
	 * @return
	 */
	public BigDecimal getSumClearAmountForBillClearCollect(String collectItemId, String billClearId )
	{

		StringBuffer sb = new StringBuffer();
		
		// 发票收款关系表
		sb.append("select nvl(sum(amount),0) from (");
		sb.append("select nvl(sum(NOWCLEARAMOUNT),0) as amount from YBILLINCOLLECT a");
		sb.append(" where a.COLLECTITEMID='" + collectItemId + "' and BILLCLEARID <> '" + billClearId + "'");
		sb.append(" union all");
		
		// 未清收款表
		sb.append("select nvl(sum(NOWCLEARAMOUNT),0) as amount from YUNCLEARCOLLECT a ");
		sb.append(" where a.COLLECTITEMID='" + collectItemId + "'");
		sb.append(" union all");
		
		// 收款分配表
		sb.append(" select nvl((ASSIGNAMOUNT-SURETYBOND-PREBILLAMOUNT),0) as amount from YCOLLECTITEM a ");
		sb.append(" where a.COLLECTITEMID='" + collectItemId + "'");
		sb.append(" )");

		log.debug("根据收款分配表id计算清帐金额:" + sb.toString());
		BigDecimal list = (BigDecimal) this.getJdbcTemplate().queryForObject(sb.toString(), BigDecimal.class);

		if (null == list)
			return new BigDecimal(0);
		else
			return list;
	}
	
	/**
	 * 根据发票号计算清帐金额(付款,不包含本次金额)。
	 * @param paymentId
	 * @param billno
	 * @return
	 */
	public BigDecimal getSumClearAmountForPayment(String invoice, String paymentId)
	{

		StringBuffer sb = new StringBuffer();
		
		// 票清收款
		sb.append("select nvl(sum(amount),0) from (");
		sb.append("select nvl(sum(clearbillamount),0) as amount from YBILLCLEARITEM a");
		sb.append(" where a.INVOICE='" + invoice + "'");
		sb.append(" union all");
		
		// 供应商单清
		sb.append("select nvl(sum(CLEARAMOUNT),0) as amount from YUNCLEARSUPPBILL a ");
		sb.append(" where a.INVOICE='" + invoice + "'");
		sb.append(" union all");
		
		// 付款(清票)
		sb.append(" select nvl(sum( a.CLEARAMOUNT2),0) as amount from YPAYMENTCBILL a ");
		sb.append(" where a.BILLNO='" + invoice + "' and PAYMENTID <> '" + paymentId + "'");
		sb.append(" )");

		log.debug("根据发票号计算清帐金额:" + sb.toString());
		BigDecimal list = (BigDecimal) this.getJdbcTemplate().queryForObject(sb.toString(), BigDecimal.class);

		if (null == list)
			return new BigDecimal(0);
		else
			return list;
	}
	
	/**
	 * 根据发票号计算票的清帐金额(票清付款,不包含本次金额)。
	 * @param billClearId
	 * @param invoice
	 * @return
	 */
	public BigDecimal getSumBillClearAmountForBillClearPayment(String invoice, String billClearId)
	{

		StringBuffer sb = new StringBuffer();
		
		// 票清付款
		sb.append("select nvl(sum(amount),0) from (");
		sb.append("select nvl(sum(clearbillamount),0) as amount from YBILLCLEARITEM a");
		sb.append(" where a.INVOICE='" + invoice + "' and BILLCLEARID <> '" + billClearId + "'");
		sb.append(" union all");
		
		// 供应商单清
		sb.append("select nvl(sum(CLEARAMOUNT),0) as amount from YUNCLEARSUPPBILL a ");
		sb.append(" where a.INVOICE='" + invoice + "'");
		sb.append(" union all");
		
		// 付款(清票)
		sb.append(" select nvl(sum( a.CLEARAMOUNT2),0) as amount from YPAYMENTCBILL a ");
		sb.append(" where a.BILLNO='" + invoice + "'");
		sb.append(" )");

		log.debug("根据发票号计算清帐金额:" + sb.toString());
		BigDecimal list = (BigDecimal) this.getJdbcTemplate().queryForObject(sb.toString(), BigDecimal.class);

		if (null == list)
			return new BigDecimal(0);
		else
			return list;
	}
	
	/**
	 * 根据付款分配表id计算清帐金额(票清付款，不包含本次金额)。
	 * @param billClearId
	 * @param paymentItemId
	 * @return
	 */
	public BigDecimal getSumClearAmountForBillClearPayment(String paymentItemId, String billClearId )
	{

		StringBuffer sb = new StringBuffer();
		
		// 未清预付款表
		sb.append("select nvl(sum(amount),0) from (");
		sb.append("select nvl(sum(NOWCLEARAMOUN),0) as amount from YBILLINPAYMENT a");
		sb.append(" where a.PAYMENTITEMID='" + paymentItemId + "' and BILLCLEARID <> '" + billClearId + "'");
		sb.append(" union all");
		
		// 
		sb.append("select nvl(sum(NOWCLEARAMOUNT),0) as amount from YUNCLEARPAYMENT a ");
		sb.append(" where a.PAYMENTITEMID='" + paymentItemId + "'");
		sb.append(" union all");
		
		// 付款分配表
		sb.append(" select nvl((ASSIGNAMOUNT-PREPAYAMOUNT),0) as amount from YPAYMENTITEM a ");
		sb.append(" where a.PAYMENTITEMID='" + paymentItemId + "'");
		sb.append(" )");

		log.debug("根据付款分配表id计算清帐金额:" + sb.toString());
		BigDecimal list = (BigDecimal) this.getJdbcTemplate().queryForObject(sb.toString(), BigDecimal.class);

		if (null == list)
			return new BigDecimal(0);
		else
			return list;
	}
	
	/**
	 * 获取付款分配行项目的货款金额
	 * @param paymentItemId
	 * @return
	 */
	public BigDecimal getGoodSAmountForPayment(String paymentItemId)
	{
		String sql = "select nvl(GOODSAMOUNT,0) as amount from YPAYMENTITEM where PAYMENTITEMID ='" 
				+ paymentItemId	+ "'";
		BigDecimal list = (BigDecimal) this.getJdbcTemplate().queryForObject(sql, BigDecimal.class);
		
		if (null == list)
			return new BigDecimal(0);
		else
			return list;
	}
	
	/**
	 * 获取收款分配行项目的货款金额
	 * @param collectItemId
	 * @return
	 */
	public BigDecimal getGoodSAmountForCollect(String collectItemId)
	{
		String sql = "select nvl(GOODSAMOUNT,0) as amount from YCOLLECTITEM where COLLECTITEMID ='" 
			+ collectItemId	+ "'";
		
		BigDecimal list = (BigDecimal) this.getJdbcTemplate().queryForObject(sql, BigDecimal.class);
		if (null == list)
			return new BigDecimal(0);
		else
			return list;
	}
	
	/**
	 * 根据凭证号，获取发票号和发票金额
	 * @param voucherNo 凭证号
	 * @param tableName 表名：供应商或客户未清抬头表
	 * @return
	 */
	public List<Map<String,Object>> getInvoiceListByVouherNo(String tableName, String voucherNo)
	{
		String sql = "select INVOICE invoice, DMBTR billAmount from " + tableName +
				" where BELNR = '" + voucherNo + "'";

		List<Map<String,Object>> list = this.getJdbcTemplate().queryForList(sql);
		return list;
	}

	/**
	 * 设置为结清标志
	 * @param collectItemId
	 * @param isCleared
	 */
	public void setCollectItemIsCleared( String collectItemId, String isCleared )
	{
		String sql = "update YCOLLECTITEM set ISCLEAR = '" + isCleared +
				"' where COLLECTITEMID = '" + collectItemId + "'";
		
		log.debug("更新collectItem表位未结清:" + sql);
		
		this.getJdbcTemplate().execute(sql);
		
	}
	
	/**
	 * 设置结清标志
	 * @param paymentItemId
	 * @param isCleared
	 */
	public void setPaymentItemIsCleared( String paymentItemId, String isCleared )
	{
		String sql = "update YPAYMENTITEM set BILLISCLEAR = '" + isCleared +
				"' where PAYMENTITEMID = '" + paymentItemId + "'";
		
		log.debug("更新paymentItem表位未结清:" + sql);
		
		this.getJdbcTemplate().execute(sql);
		
	}

	/**
	 * 重分配客户退款时，作废原单据的同时需要把退款金额加回给货款金额或保证金金额
	 * @param refundmentId
	 */
	private void resetAmountForCustomerDb(String refundmentId )
	{
		String sql = "select COLLECTITEMID collectItemid, nvl(PEFUNDMENTAMOUNT,0) preRefundAmount, ISTYBOND istybond from YREFUNDMENTITEM where REFUNDMENTID = '"
			+ refundmentId + "'";
	
		List<Map<String,Object>> list = this.getJdbcTemplate().queryForList(sql);
		
		if( list != null )
		{
			/**
			 * 遍历行项目
			 */
			for(Map<String,Object> map : list)
			{
				String collectItemid = map.get("collectItemid").toString();
				
				/**
				 * 原币退款金额
				 */
				BigDecimal preRefundAmount = new BigDecimal(map.get("preRefundAmount").toString());
				
				String istybond = map.get("istybond").toString();
				
				/**
				 * 判断是否保证金退款
				 */
				if(istybond.equals("Y"))		//保证金退款
				{
					/**
					 * 更新付款表的剩余保证金金额
					 */
					String updateSql = "update YCOLLECTITEM set ACTSURETYBOND = (ACTSURETYBOND + " 
						+ preRefundAmount.toString() + ") where COLLECTITEMID = '"
						+ collectItemid + "'";
					
					this.getJdbcTemplate().execute(updateSql);
				}		
				else
				{
					String updateSql = "update YCOLLECTITEM set GOODSAMOUNT = (GOODSAMOUNT + " 
							+ preRefundAmount.toString() + ") where COLLECTITEMID = '"
							+ collectItemid + "'";
					this.getJdbcTemplate().execute(updateSql);
				}
			}
		}	
	}
	
	/**
	 * 重分配供应商退款时，作废原单据的同时需要把退款金额加回给货款金额
	 * @param refundmentId
	 */
	private void resetAmountForSupplierDb( String refundmentId )
	{
		String sql = "select PAYMENTITEMID paymentitemid, nvl(PEFUNDMENTAMOUNT,0) preRefundAmount from YREFUNDMENTITEM where REFUNDMENTID = '"
			+ refundmentId + "'";
	
		List<Map<String,Object>> list = this.getJdbcTemplate().queryForList(sql);
		
		if( list !=null )
		{
			//遍历行项目
			for( Map<String,Object> map : list)
			{
				String paymentitemid = map.get("paymentitemid").toString();
				
				/**
				 * 原币退款金额
				 */
				BigDecimal preRefundAmount = new BigDecimal(map.get("preRefundAmount").toString());				
				
				/**
				 * 更新付款表的货款金额
				 */
				String updateSql = "update YPAYMENTITEM set GOODSAMOUNT = (GOODSAMOUNT + " 
						+ preRefundAmount.toString() + ") where PAYMENTITEMID = '"
						+ paymentitemid + "'";
				
				this.getJdbcTemplate().execute(updateSql);
			}
		}
	}
	
	/**
	 * 拼装sql in查询条件
	 * @param litInput
	 * @return
	 */
	private String constructSqlCond( List<String> litInput )
	{
		String strRet = "";
		
		int i = litInput.size();
		for( String str : litInput )
		{
			i--;
			strRet = "'" + str + "'";  //yanghancai 2010-10-21
			if( i > 0 )
			{
				strRet += ",";
			}
		}
		return strRet;
	}
	
	/**
	 * @创建作者：邱杰烜
	 * @创建日期：2010-10-21
	 * 根据付款ID取得付款信息
	 */
	public List getPaymentInfo(String paymentId){
		String sql = "SELECT * FROM YPAYMENT WHERE PAYMENTID='" + paymentId + "'";
		return this.getJdbcTemplate().queryForList(sql);
	}
	
	/**
	 * @创建作者：邱杰烜
	 * @创建日期：2010-10-21
	 * 根据退款ID取得退款信息
	 */
	public List getRefundInfo(String refundId){
		String sql = "SELECT * FROM YREFUNDMENT WHERE REFUNDMENTID='" + refundId + "'";
		return this.getJdbcTemplate().queryForList(sql);
	}
	
	/**
	 * @创建作者：邱杰烜
	 * @创建日期：2010-10-22
	 * 根据重分配信息自动去更新对应的表审批状态
	 * @param table 表名    
	 * @param busIdCondition 业务ID查询条件    
	 * @param ps 流程状态
	 * @param bs 业务状态
	 */
	public void updateProcess(String table, String busIdCondition, String ps, String bs){
		String sql = "UPDATE " + table + " SET BUSINESSSTATE='" + bs + "', PROCESSSTATE='" + ps + "' WHERE " + busIdCondition;
		this.getJdbcTemplate().execute(sql);
	}
	
	/**
	 * @创建作者：邱杰烜
     * @创建日期：2010-12-14
     * 根据当前资金部人员取得所属的部门
	 * @param username 当前办理人的username
	 */
	public String getDeptcodeByUsername(String username){
	    String sql = "SELECT DEPID FROM YUSERMAPPING WHERE USERNAME='" + username + "'";
	    List<Map<String,Object>> list = this.getJdbcTemplate().queryForList(sql);
	    String depids="";
	    for(Map<String,Object> map :list){
	    	depids = depids +  (String)map.get("DEPID") + ",";
	    }	    
	    return depids.substring(0,depids.length()-1);
	}
	

	/***
	 * 取得本次有部分清的（当前部分清指未清金额等于不清账金额），款行项目titleID,
	 * 
	 * @param clearid 如 1122','ddd
	 * @return IKeyValue key 为titleid,value 为收款itemID ,value2 为客户清账ID
	 */
	public List<IKeyValue> getPartIdsByCustomerClear(String clearid){
		List<IKeyValue> list = new ArrayList<IKeyValue>();
		StringBuffer sb = new StringBuffer();
//		款方
		sb.append(" SELECT uc.customertitleid,uc.collectitemid as collectitemidOrBillno,cs.customsclearid AS clearid FROM ycustomsiclear cs ,yunclearcollect uc WHERE cs.customsclearid=uc.customsclearid  ");
		sb.append("  AND cs.businessstate IN ('3') AND uc.nowclearamount <> uc.unoffsetamount   ");
		sb.append("  AND cs.customsclearid IN ('" + clearid + "') ");
		sb.append("  UNION ");
		sb.append("  SELECT '' AS customertitleid, bic.collectitemid  as collectitemidOrBillno,bc.billclearid AS clearid FROM ybillclear bc ,ybillincollect bic WHERE bc.billclearid=bic.billclearid  ");
		sb.append("  AND bc.businessstate IN ('3') AND bic.nowclearamount <> bic.unoffsetamount ");
		sb.append("  AND bc.billclearid IN ('" + clearid + "') ");
		sb.append(" UNION ");
		sb.append(" SELECT '' AS customertitleid,ci.collectitemid  as collectitemidOrBillno,c.collectid AS clearid FROM ycollect c,ycollectitem ci WHERE c.collectid=ci.collectid ");
		sb.append(" AND ( (ci.ASSIGNAMOUNT-ci.PREBILLAMOUNT-ci.actsuretybond) <> 0 OR ci.actsuretybond <> 0) ");
		sb.append(" and  ci.collectid in ('" + clearid + "') ");
		sb.append(" and ( ( c.collecttype in ('11', '12')   and c.businessstate  in ('2', '3')  ) or ( c.collecttype not in ('11', '12') and c.businessstate in ('3') )) " );
		
		sb.append(" UNION ");
		sb.append(" SELECT  '' AS customertitleid,rei.collectitemid  as collectitemidOrBillno, re.refundmentid AS clearid FROM yrefundment re ,yrefundmentitem rei WHERE re.refundmentid = rei.refundmentid ");
		sb.append(" AND re.businessstate IN ('3') AND rei.pefundmentamount <> rei.refundmentamount ");
		sb.append(" and rei.refundmentid  in ('" + clearid + "') ");
		sb.append(" UNION  ");
//		票方
		sb.append(" SELECT ub.customertitleid,ub.billno  as collectitemidOrBillno,cs.customsclearid  AS clearid FROM ycustomsiclear cs ,yunclearcustbill ub WHERE cs.customsclearid=ub.customsclearid ");
		sb.append("  AND cs.businessstate IN ('3') AND ub.clearamount <> ub.unreceivableamou   ");
		sb.append("  AND ub.customsclearid in ('"+clearid+"') ");
		sb.append(" UNION ");
		sb.append(" SELECT bi.titleid AS customertitleid,bi.invoice  as collectitemidOrBillno, bc.billclearid  AS clearid from YBILLCLEARITEM bi ,YBILLCLEAR bc WHERE bi.billclearid = bc.billclearid ");
		sb.append(" AND bc.businessstate IN ('3') AND bi.clearbillamount <> bi.unreceivedamount AND TRIM(bi.invoice) IS NOT  NULL ");
		sb.append(" AND  bc.billclearid in ('"+clearid+"') ");
		sb.append(" UNION ");
		sb.append(" SELECT '' AS customertitleid, cb.billno  as collectitemidOrBillno, c.collectid AS clearid from YCOLLECTCBILL cb ,YCOLLECT c where c.collectid=cb.collectid ");
		sb.append(" AND cb.clearamount <> cb.unreceivedamount AND TRIM(cb.billno) IS NOT NULL ");
		sb.append(" AND c.collectid in ('"+clearid+"') ");
		sb.append(" and ( ( c.collecttype in ('11', '12')   and c.businessstate  in ('2', '3')  ) or ( c.collecttype not in ('11', '12') and c.businessstate in ('3') )) " );
		
		List<Map> rowList = this.getJdbcTemplate().queryForList(sb.toString());
		log.debug("取得本次有部分清的，款票行项目titleID:" + sb.toString());
		for (Map map : rowList){
			KeyValue keyValue = new KeyValue(); 
			if(null != map.get("customertitleid"))keyValue.setKey(map.get("customertitleid").toString());
			if(null != map.get("collectitemidOrBillno"))keyValue.setValue(map.get("collectitemidOrBillno").toString());
			if(null != map.get("clearid"))keyValue.setValue2(map.get("clearid").toString());
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
		sb.append("  AND sc.businessstate IN ('4','7') AND up.nowclearamount <> up.unoffsetamount    ");
		sb.append("  AND sc.suppliersclearid IN ('" + clearid + "') ");
		sb.append("  UNION ");
		sb.append("  SELECT '' AS vendortitleid, bp.paymentitemid  as paymentitemidOrBillno,bc.billclearid AS clearid FROM ybillclear bc,ybillinpayment bp where bc.billclearid=bp.billclearid   ");
		sb.append("  AND bc.businessstate IN ('4','7') AND  bp.nowclearamount <> bp.unoffsetamount   ");
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
		sb.append(" AND re.businessstate IN ('3') AND rei.pefundmentamount <> rei.refundmentamount ");
		sb.append(" and rei.refundmentid  in ('" + clearid + "') ");
		sb.append(" UNION  ");
//		票方
		sb.append(" SELECT ub.vendortitleid,ub.invoice  as paymentitemidOrBillno,sc.suppliersclearid   AS clearid FROM ysuppliersiclear sc,yunclearsuppbill ub where sc.suppliersclearid=ub.suppliersclearid  ");
		sb.append("  AND sc.businessstate IN ('4','7') AND  ub.clearamount <> ub.unpaidamount  ");
		sb.append("  AND ub.suppliersclearid in ('"+clearid+"') ");
		sb.append(" UNION ");
		sb.append(" SELECT bi.titleid AS vendortitleid,bi.invoice  as paymentitemidOrBillno, bc.billclearid  AS clearid from YBILLCLEARITEM bi ,YBILLCLEAR bc WHERE bi.billclearid = bc.billclearid ");
		sb.append(" AND bc.businessstate IN ('4','7') AND bi.clearbillamount <> bi.unreceivedamount AND TRIM(bi.invoice) IS NOT  NULL ");
		sb.append(" AND  bc.billclearid in ('"+clearid+"') ");
		sb.append(" UNION ");
		sb.append(" SELECT pb.billid AS vendortitleid, pb.billno  as paymentitemidOrBillno, p.paymentid AS clearid from ypayment p ,ypaymentcbill pb where p.paymentid=pb.paymentid ");
		sb.append(" AND pb.CLEARAMOUNT2 <> pb.unpaidamount AND TRIM(pb.billno) IS NOT NULL  ");
		sb.append(" AND p.paymentid in ('"+clearid+"') ");
		sb.append(" and ( ( p.businessstate not in ('3', '4','7')   and p.paymenttype in ('06', '07')) " );
		sb.append(" or ( p.paymenttype not in ('06', '07') and p.businessstate in ('4','7') ) " );
		sb.append(" or (p.pay_type in ('2') and p.businessstate not in ('3','4','7')) ");
		sb.append(" or ( p.pay_type not in ('2') and p.businessstate  in ('4','7')) )    ");
		
		List<Map> rowList = this.getJdbcTemplate().queryForList(sb.toString());
		log.debug("取得本次有部分清的，供应商款票行项目titleID:" + sb.toString());
		for (Map map : rowList){
			KeyValue keyValue = new KeyValue(); 
			if(null != map.get("vendortitleid"))keyValue.setKey(map.get("vendortitleid").toString());
			if(null != map.get("paymentitemidOrBillno"))keyValue.setValue(map.get("paymentitemidOrBillno").toString());
			if(null != map.get("clearid"))keyValue.setValue2(map.get("clearid").toString());
			list.add(keyValue);
		}
		return list;
	}
	
	public List<CertificateNo> getClearVoucher(String bussinessid){
		String sql = "select VOUCHERNO voucherno, FIYEAR fiyear, COMPANYCODE bukrs from YVOUCHER where BUSINESSID = '"
			+ bussinessid + "' and BSTAT = 'A' AND processstate <> '-1' ";
		List<CertificateNo> certificateNoList = new ArrayList<CertificateNo>();
		List<Map<String, String>> tempRetList = new ArrayList<Map<String, String>>();
		  tempRetList = this.getJdbcTemplate().queryForList(sql);
          if (tempRetList != null) {
              for (int i = 0; i < tempRetList.size(); i++) {
                  CertificateNo certificateNo = new CertificateNo();
                  certificateNo.setBelnr(tempRetList.get(i).get("voucherno"));
                  certificateNo.setBukrs(tempRetList.get(i).get("bukrs"));
                  certificateNo.setGjahr(tempRetList.get(i).get("fiyear"));
                  if(!certificateNoList.contains(certificateNo)){
                      certificateNoList.add(certificateNo);
                  }
              }
          }
          return certificateNoList;
	}
} 
