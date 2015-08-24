/*
 * @(#)SupplierRefundItemJdbcDao.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：邱荣发
 *  时　间：2010-6-22
 *  描　述：创建
 */

package com.infolion.xdss3.supplierDrawback.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Repository;
import com.infolion.platform.dpframework.core.dao.BaseJdbcDao;
import com.infolion.platform.dpframework.util.CodeGenerator;
import com.infolion.platform.dpframework.util.ExBeanUtils;
import com.infolion.platform.dpframework.util.StringUtils;
import com.infolion.xdss3.BusinessState;
import com.infolion.xdss3.Constants.cleared;
import com.infolion.xdss3.customerDrawback.domain.CustomerDbBankItem;
import com.infolion.xdss3.financeSquare.domain.FundFlow;
import com.infolion.xdss3.financeSquare.domain.SettleSubject;
import com.infolion.xdss3.supplierDrawback.domain.SupplierDbBankItem;
import com.infolion.xdss3.supplierDrawback.domain.SupplierRefundItem;
import com.infolion.xdss3.supplierDrawback.domain.SupplierRefundment;
import com.infolion.xdss3.voucher.domain.Voucher;
import com.infolion.xdss3.voucheritem.domain.VoucherItem;

@Repository
public class SupplierRefundItemJdbcDao extends BaseJdbcDao
{
	/**
	 * 获取供应商退款行项目数据
	 * @param strItemId
	 * @return
	 */
	public SupplierRefundItem getSupplierRefundItemList(String paymentItemId){
		
		String strSql = "select a.PAYMENTNO PAYMENTNO, a.CURRENCY CURRENCY, v.EXCHANGERATE EXCHANGERATE, b.CONTRACT_NO CONTRACT_NO, b.PROJECT_NO PROJECT_NO, nvl(b.assignamount,0) clearAmount, nvl(v.exchangerate,0) CLOSEEXCHANGERAT " +
				"from YPAYMENT a, YPAYMENTITEM b ,yvoucher v where a.PAYMENTID = b.PAYMENTID  and a.paymentid=v.businessid and b.PAYMENTITEMID = '"
			 		+ paymentItemId + "'";
		
		List<Map<String,Object>> list = this.getJdbcTemplate().queryForList(strSql);
		
		if( list == null || list.size() == 0){
			return null;
		}
		
		/**
		 * 货款金额小于等于0
		 */
		if(Double.parseDouble( list.get(0).get("clearAmount").toString() ) <= 0){
			return null;
		}
		
		SupplierRefundItem supplierRefundItem = new SupplierRefundItem();
		supplierRefundItem.setPaymentno(list.get(0).get("PAYMENTNO").toString());		// 付款单号
		supplierRefundItem.setContract_no(list.get(0).get("CONTRACT_NO").toString());	// 合同号
		supplierRefundItem.setProject_no(list.get(0).get("PROJECT_NO").toString());		// 立项号
		supplierRefundItem.setExchangerate(new BigDecimal(list.get(0).get("CLOSEEXCHANGERAT").toString())); // 付款汇率 取
		//获取币别
//		String currency = this.getCurrencyByPaymentItemId(list.get(0).get("CURRENCY").toString());
		supplierRefundItem.setCurrency(list.get(0).get("CURRENCY").toString());
		supplierRefundItem.setRefundcurrency(list.get(0).get("CURRENCY").toString());	
		
		// 统计为分配金额
        Map<String, BigDecimal> retListMap = this.getTotalUnassignedAmount(paymentItemId);
        // 合同、立项可退金额为零
        if (StringUtils.isNullBlank(paymentItemId)) {
            retListMap.put("goodsAmount", new BigDecimal(0));
            retListMap.put("unassignedAmount", new BigDecimal(0));
        }
		supplierRefundItem.setAssignamount(retListMap.get("goodsAmount"));
        supplierRefundItem.setUnassignamount(retListMap.get("unassignedAmount"));
        supplierRefundItem.setPaymentitemid(paymentItemId);
		return supplierRefundItem;
	}
	
	/**
	 * 获取供应商退款银行信息
	 * @param bankId
	 * @return
	 */
	public SupplierDbBankItem getSupplierDbBankItem(String bankId)
	{
		SupplierDbBankItem supplierDbBankItem = new SupplierDbBankItem();
		String strSql = "select BANK_ID, BANK_NAME, BANK_ACCOUNT, BANK_HKONT from YBANK_INFO where BANK_ID = '"
						+ bankId + "'";
		List<Map<String,Object>> list = this.getJdbcTemplate().queryForList(strSql);
		supplierDbBankItem.setBanksubject(list.get(0).get("BANK_HKONT").toString());
		supplierDbBankItem.setRefundbankid(list.get(0).get("BANK_ID").toString());
		supplierDbBankItem.setRefundbankname(list.get(0).get("BANK_NAME").toString());
		supplierDbBankItem.setRefundbankacount(list.get(0).get("BANK_ACCOUNT").toString());
		
		supplierDbBankItem.setRefundtype("S");	//供应商
		return supplierDbBankItem;
	}
	
	
	/**
	 * 根据退款ID插入关联单据表。
	 * @param refundmentId
	 * @return
	 */
	public void saveRelatedPaymentByRefundmentid(String refundmentId)
	{	
		List<Map<String,String>> paymentNoList = new ArrayList<Map<String,String>>();
		String sql = "select distinct(PAYMENTNO) paymentNo from YREFUNDMENTITEM where REFUNDMENTID = '"
					+ refundmentId + "'";
		paymentNoList = this.getJdbcTemplate().queryForList(sql);
		
		/**
		 * 获取退款单号
		 */
		sql = "select REFUNDMENTNO refundmentNo from YREFUNDMENT where REFUNDMENTID = '" + refundmentId + "'";
		List<Map<String,String>> retRefundmentNoList = this.getJdbcTemplate().queryForList(sql);
		String fundmentNo = retRefundmentNoList.get(0).get("refundmentNo");
		
		if(paymentNoList != null && paymentNoList.size() > 0 ) 
		{
			for( int i = 0; i < paymentNoList.size(); i++ )
			{
				String paymentNo = paymentNoList.get(i).get("paymentNo");
				
				/**
				 * 获得收款ID
				 */
				sql = "select PAYMENTID paymentid from YPAYMENT where PAYMENTNO = '"
					+ paymentNo + "'";
				
				List<Map<String, String>> paymentIdList = this.getJdbcTemplate().queryForList(sql);
				String paymentId = paymentIdList.get(0).get("paymentid");
								
				
				/**
				 * 累加原币退款金额
				 */
				sql = "select sum(PEFUNDMENTAMOUNT) totalAmount from YREFUNDMENTITEM where PAYMENTNO = '"
						+ paymentNo + "'";
				long totalAmount = this.getJdbcTemplate().queryForLong(sql);
				
				
				/**
				 * 插入数据
				 */
				
				String uId = CodeGenerator.getUUID();
				
				sql = "insert into YPAYMENTRELATED(MANDT,RELATEDPAYMENTID,PAYMENTID,REFUNDMENTID,RELATEDNO,APPLYAMOUNT,RELATEDTYPE)" 
					+ " values('800', '" 
					+ uId + "', '"
					+ paymentId + "', '"
					+ refundmentId + "', '"
					+ fundmentNo + "', '"
					+ totalAmount 
					+ "', '2')";
				
				this.getJdbcTemplate().execute(sql);
			
			}
		}
		
	}
	
	/**
	 * 退款凭证产生后，更新货款金额
	 */
	public void updatePaymentItem(String refundmentId)
	{
		String sql = "select PAYMENTITEMID paymentitemid, nvl(PEFUNDMENTAMOUNT,0) preRefundAmount, nvl(UNASSIGNAMOUNT,0) unsignedAmount from YREFUNDMENTITEM where REFUNDMENTID = '"
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
				 * 未分配金额退款金额
				 */
				BigDecimal unsignedAmount = new BigDecimal(map.get("unsignedAmount").toString());
				
				
				/**
				 * 更新付款表的货款金额
				 */
				String updateSql = "";
				
				
				/**
				 * 判断是否全额退款
				 * 全额退款需要设置清款标志为已清
				 */
				if(preRefundAmount.compareTo(unsignedAmount) == 0)
				{
					updateSql = "update YPAYMENTITEM set GOODSAMOUNT = (GOODSAMOUNT - " 
						+ preRefundAmount.toString() + "), BILLISCLEAR = '1' where PAYMENTITEMID = '"
						+ paymentitemid + "'";
				}
				
				else
				{
					updateSql = "update YPAYMENTITEM set GOODSAMOUNT = (GOODSAMOUNT - " 
						+ preRefundAmount.toString() + ") where PAYMENTITEMID = '"
						+ paymentitemid + "'";
				}
				this.getJdbcTemplate().execute(updateSql);
			}
		}	
	}
	
    /**
     * 更新退款状态
     */
    public void prepConfirm(String refundmentId, String businessstate) {
        String updateSql = "update yrefundment set businessstate = '" + businessstate + "'  where refundmentId = '"
                + refundmentId + "'";
        this.getJdbcTemplate().execute(updateSql);
    }
	
	/**
	 * 根据付款分配表ID获取币别
	 * @param collectItemId
	 * @return
	 */
	private String getCurrencyByPaymentItemId( String paymentItemId )
	{
		String strSql = "select CURRENCY currency from YPAYMENT where PAYMENTID in( select PAYMENTID from YPAYMENTITEM where PAYMENTITEMID = '"
			+ paymentItemId + "') ";
		
		List<Map<String,String>> retList =  this.getJdbcTemplate().queryForList(strSql);
		
		// 未获取值
		if( retList == null || retList.size()== 0 )
		{
			return "";
		}
		return retList.get(0).get("currency");
	}
	
	/**
	 * 根据付款分配表ID获取未分配金额
	 * @param collectItemId
	 * @return
	 */
	public Map<String,BigDecimal> getUnsignedAmountByPaymentItemId( String paymentItemId )
	{
		Map<String,BigDecimal> retListMap = new HashMap<String,BigDecimal>();
		
//		/**
//		 * 获取付款分配表的分配金额
//		 */
//		String strSql = "select ASSIGNAMOUNT from YPAYMENTITEM where PAYMENTITEMID = '" 
//						+ paymentItemId + "'";
//		int assignamount = this.getJdbcTemplate().queryForInt(strSql);
		
		/**
		 * 获取付款分配表的货款金额(界面对应显示为分配金额)
		 */
		String strSql = "select nvl(GOODSAMOUNT,0) goodsAmount from YPAYMENTITEM where PAYMENTITEMID = '" 
						+ paymentItemId + "'";
		BigDecimal goodsAmount = null;
        Map<String,BigDecimal> amountMap = new HashMap<String,BigDecimal>();
        
        List<Map<String,BigDecimal>> list1 = this.getJdbcTemplate().queryForList(strSql, BigDecimal.class);
        // 用于合同、立项行项未有paymentitemId直接返回未分配金额0
        if (list1==null || list1.size()<1) {
            retListMap.put("assignAmount", new BigDecimal(0));
            retListMap.put("unAssignedAmount", new BigDecimal(0) );
            return retListMap;
        } else {
            goodsAmount  = (BigDecimal)(BigDecimal)this.getJdbcTemplate().queryForObject(strSql, BigDecimal.class);
        }
        
		
		/**
		 * 获取付款状态
		 */
		strSql = "select PROCESSSTATE processstate from YPAYMENT where PAYMENTID in (select PAYMENTID from YPAYMENTITEM where PAYMENTITEMID = '"
				+ paymentItemId +"')";		
		List<Map<String,String>> processStateList = this.getJdbcTemplate().queryForList(strSql);
		String processState = "0";
		if( processStateList != null && processStateList.size() > 0 )
		{
			processState = processStateList.get(0).get("processstate").trim();
		}
		
		/**
		 * 判断是否需要计算清帐金额和本行抵消金额
		 * 状态3： 审批通过
		 * 状态6： 特批审批通过
		 * 需要计算
		 */
		if(processState.equals(BusinessState.SUBMITPASS) || processState.equals(BusinessState.SPECIALSUBMITPASS))
		{
			/**
			 * 累加本行抵消金额
			 */
			strSql = "select nvl(sum(nowclearamount),0) as totalClearedValue from ybillinpayment where paymentitemid ='"
				+ paymentItemId	+ "'" ;
		
			BigDecimal totalNowclearamount = (BigDecimal)this.getJdbcTemplate().queryForObject(strSql, BigDecimal.class);
			
			
			/**
			 * 累加清帐金额
			 */
			strSql = "select nvl(sum(CLEARAMOUNT2),0) as totalClearAmount from YPAYMENTCBILL where PAYMENTID " +
					"in( select PAYMENTID from YPAYMENTITEM where PAYMENTITEMID = '" + paymentItemId + "'"
					+ " )";
		
			BigDecimal totalClearAmount = (BigDecimal)this.getJdbcTemplate().queryForObject(strSql,BigDecimal.class);
			
			retListMap.put("assignAmount", goodsAmount);
			retListMap.put("unAssignedAmount", goodsAmount.subtract(totalNowclearamount).subtract(totalClearAmount) );	
		}
		else
		{
			retListMap.put("assignAmount", goodsAmount);
			retListMap.put("unAssignedAmount", goodsAmount);
		}	
		
		/** 业务状态不处于-1作废需扣除清原币金额*/
        String sql5 = "  select nvl(sum(refundmentamount), 0) as refundmentamount from YREFUNDMENTITEM t "
                + "inner join YREFUNDMENT y on t.refundmentid = y.refundmentid "
                + " where y.businessstate <> '-1'     and t.paymentitemid = '"+ paymentItemId + "'";
        List<Map<String,BigDecimal>> list3 = this.getJdbcTemplate().queryForList(sql5, BigDecimal.class);
        // 扣除清原币金额
        for(int i=0;i<list3.size();i++){
            BigDecimal refundmentamount = (BigDecimal) list3.get(i);
            retListMap.put("unAssignedAmount", retListMap.get("unAssignedAmount").subtract(
                    refundmentamount));
        }
 
			
		return retListMap;
		
	}
	
	/**
	 * @创建作者：邱杰烜
	 * @创建日期：2010-11-09
	 * 获取退款行项金额，
	 * 并从YBILLINCOLLECT与YUNCLEARCOLLECT中取出BUSINESSSTATE<>'-1'的本行抵消金额的总和，
	 * 最后求出未分配金额
	 */
	public Map<String,BigDecimal> getTotalUnassignedAmount(String paymentItemId){
		// 从YPAYMENTITEM根据PAYMENTITEMID取出预付票款作为退款行项的金额
		String sql1 = "SELECT NVL(PREPAYAMOUNT,0) PREPAYAMOUNT FROM YPAYMENTITEM WHERE PAYMENTITEMID = '" + paymentItemId + "'";
		// 从YPAYMENTITEM根据PAYMENTITEMID取出货款金额作为退款行项的金额
		String sql2 = "SELECT NVL(GOODSAMOUNT,0) GOODSAMOUNT FROM YPAYMENTITEM WHERE PAYMENTITEMID = '" + paymentItemId + "'";
		// 从YBILLINPAYMENT根据PAYMENTITEMID取出NOWCLEARAMOUNT，再根据YBILLINPAYMENT-BILLCLEARID从YBILLCLEAR取出BUSINESSSTATE<>'-1'
		String sql3 = "SELECT NVL(a.NOWCLEARAMOUNT,0) NOWCLEARAMOUNT FROM YBILLINPAYMENT a " +
					  "INNER JOIN YBILLCLEAR b ON a.BILLCLEARID=b.BILLCLEARID WHERE b.BUSINESSSTATE<>'-1' AND " +
					  "a.PAYMENTITEMID='" + paymentItemId + "'";
		
		// 从YUNCLEARPAYMENT根据PAYMENTITEMID取出NOWCLEARAMOUNT再根据YUNCLEARPAYMENT-SUPPLIERSCLEARID从YSUPPLIERSICLEAR取出BUSINESSSTATE<>'-1'
		String sql4 = "SELECT NVL(a.NOWCLEARAMOUNT,0) NOWCLEARAMOUNT FROM YUNCLEARPAYMENT a " +
					  "INNER JOIN YSUPPLIERSICLEAR b ON a.SUPPLIERSCLEARID=b.SUPPLIERSCLEARID WHERE b.BUSINESSSTATE<>'-1' AND " +
					  "a.PAYMENTITEMID='" + paymentItemId + "'";
		// 业务状态不处于-1作废需扣除清原币金额
		String sql5 = "  select nvl(sum(refundmentamount), 0) as refundmentamount from YREFUNDMENTITEM t " +
				"inner join YREFUNDMENT y on t.refundmentid = y.refundmentid " +
				" where y.businessstate <> '-1'     and t.PAYMENTITEMID = '" + paymentItemId + "'";
		
		List<Map<String,BigDecimal>> list1 = this.getJdbcTemplate().queryForList(sql3, BigDecimal.class);
		
		List<Map<String,BigDecimal>> list2 = this.getJdbcTemplate().queryForList(sql4, BigDecimal.class);
		List<Map<String,BigDecimal>> list3 = this.getJdbcTemplate().queryForList(sql5, BigDecimal.class);
		BigDecimal totalUnassignedAmount = new BigDecimal(0);
		// 获取预付票款
		BigDecimal prebillAmount = (BigDecimal)this.getJdbcTemplate().queryForObject(sql1, BigDecimal.class);
		// 获取货款
		BigDecimal goodsAmount = (BigDecimal)this.getJdbcTemplate().queryForObject(sql2, BigDecimal.class);
		// 累加发票收款关系表的"本行抵消金额"
		for(int i=0;i<list1.size();i++){
			BigDecimal nowClearAmount = (BigDecimal) list1.get(i);
			totalUnassignedAmount = totalUnassignedAmount.add(nowClearAmount);
		}
		// 累加未清收款（贷方）的"本行抵消金额"
		for(int i=0;i<list2.size();i++){
			BigDecimal nowClearAmount = (BigDecimal) list2.get(i);
			totalUnassignedAmount = totalUnassignedAmount.add(nowClearAmount);
		}
		// 扣除清原币金额
		for(int i=0;i<list3.size();i++){
		    BigDecimal refundmentamount = (BigDecimal) list3.get(i);
		    totalUnassignedAmount = totalUnassignedAmount.add(refundmentamount);
		}
		// 未分配金额 = 预付票款 - 所有本行抵消金额和
		totalUnassignedAmount = prebillAmount.subtract(totalUnassignedAmount);
	      Map<String,BigDecimal> amountMap = new HashMap<String,BigDecimal>();
		amountMap.put("goodsAmount", goodsAmount);
		amountMap.put("unassignedAmount", totalUnassignedAmount);
		return amountMap;
	}

	/**
	 * 根据合同号获取外围已清票的合计金额
	 * @param contractNo
	 * @return
	 */
	public BigDecimal getSumInvoiceAmountByContractNo(String contractNo)
	{
		String sql = "select nvl(sum(DMBTR),0) sumInvoiceAmount from YVENDORTITLE " +
					"where VERKF = '" + contractNo + 
					"' and ISCLEARED = '" +  cleared.isCleared + "'";		
		
		return (BigDecimal)this.getJdbcTemplate().queryForObject(sql,BigDecimal.class);
	}
	
	/**
	 * 根据合同号获取外围已清收款合计金额
	 * @param contractNo
	 * @return
	 */
	public BigDecimal getSumAmountByContractNo(String contractNo)
	{
		String sql = "select nvl(sum(ASSIGNAMOUNT),0) sumAmount from YPAYMENTITEM " +
				"where CONTRACT_NO = '" + contractNo + 
				"' and BILLISCLEAR = '" +  cleared.isCleared + "'";

		return (BigDecimal)this.getJdbcTemplate().queryForObject(sql,BigDecimal.class);
	}	
	
	/**
	 * 根据立项号获取外围已清合计金额
	 * @param projectNo: 立项号
	 * @param clearedContractNos: 已清合同号 例如 ： 'xxxxx','yyyyy'
	 * @return
	 */
	public BigDecimal getSumInvoiceAmountByProjectNo(String projectNo, String clearedContractNo)
	{
		String sql = "select nvl(sum(DMBTR),0) sumInvoiceAmount from YVENDORTITLE " +
					"where LIXIANG = '" + projectNo + 
					"' and ISCLEARED = '" +  cleared.isCleared + "'";
		//			"' and VERKF not in" + "(" + clearedContractNo + ")";
		
		if( clearedContractNo!= null && !clearedContractNo.equals("") )
		{
			sql += " and VERKF not in" + "(" + clearedContractNo + ")";
		}
				
		return (BigDecimal)this.getJdbcTemplate().queryForObject(sql,BigDecimal.class);
		
	}
	
	/**
	 * 根据立项号获取外围已清收款合计金额
	 * @param projectNo: 立项号
	 * @param clearedContractNos: 已清合同号 例如 ： 'xxxxx','yyyyy'
	 * @return
	 */
	public BigDecimal getSumAmountByProjectNo(String projectNo, String clearedContractNo)
	{
		String sql = "select nvl(sum(ASSIGNAMOUNT),0) sumAmount from YPAYMENTITEM" +
					" where PROJECT_NO = '" + projectNo + 
					"' and BILLISCLEAR = '" +  cleared.isCleared + "'";
		//			"' and CONTRACT_NO not in (" + clearedContractNo + ")";
		
		if( clearedContractNo!= null && !clearedContractNo.equals("") )
		{
			sql += " and CONTRACT_NO not in (" + clearedContractNo + ")";
		}

		return (BigDecimal)this.getJdbcTemplate().queryForObject(sql,BigDecimal.class);
	}	
	
	/**
	 * 根据供应商统计外围已清的发票金额合计
	 * @param supplier
	 * @param clearedContractNo
	 * @param clearedProjectNo
	 * @param bukrs
	 * @return
	 */
	public BigDecimal getSumInvoiceAmountBySupplier(String supplier, 
								String clearedContractNo, 
								String clearedProjectNo,
								String bukrs)
	{
		String sql = "select nvl(sum(DMBTR),0) sumInvoiceAmount from YVENDORTITLE" +
				" where shkzg='H' and LIFNR = '" + supplier + 
				"' and ISCLEARED = '" +  cleared.isCleared + 
		//		"' and VERKF not in" + "(" + clearedContractNo + ")" +
		//		" and LIXIANG not in (" + clearedProjectNo + ")" +
				"' and BUKRS = '" + bukrs +"'";
		
		if( clearedContractNo!= null && !clearedContractNo.equals("") )
		{
			sql += " and VERKF not in" + "(" + clearedContractNo + ")";
		}
		if( clearedProjectNo!= null && !clearedProjectNo.equals("") )
		{
			sql += " and LIXIANG not in (" + clearedProjectNo + ")";
		}

		return (BigDecimal)this.getJdbcTemplate().queryForObject(sql,BigDecimal.class);
	}
	
	/**
	 * 根据供应商统计外围已清的款的金额合计
	 * @param supplier
	 * @param clearedContractNo
	 * @param clearedProjectNo
	 * @param bukrs
	 * @return
	 */
	public BigDecimal getSumAmountBySupplier(String supplier, 
								String clearedContractNo, 
								String clearedProjectNo,
								String bukrs)
	{
		String sql = "select nvl(sum(ASSIGNAMOUNT),0) sumAmount from YPAYMENTITEM " +
			"where PAYMENTID in " +
			"(select PAYMENTID from YPAYMENT a, T_SYS_DEPT b where a.DEPT_ID = b.DEPT_ID and " +
			"a.SUPPLIER = '" + supplier + "' and b.COMPANY_CODE = '" + bukrs + "')" +
			" and BILLISCLEAR = '" +  cleared.isCleared + "'";
		//	"' and CONTRACT_NO not in (" +clearedContractNo + ")" +
		//	" and PROJECT_NO not in (" + clearedProjectNo + ")";
		
		if( clearedContractNo!= null && !clearedContractNo.equals("") )
		{
			sql += " and CONTRACT_NO not in (" +clearedContractNo + ")";
		}
		if( clearedProjectNo!= null && !clearedProjectNo.equals("") )
		{
			sql += " and PROJECT_NO not in (" + clearedProjectNo + ")";
		}

		return (BigDecimal)this.getJdbcTemplate().queryForObject(sql,BigDecimal.class);
	}
	
//	/**
//	 * 获取款的凭证行项目（退款行项目清帐的情况）
//	 * @param paymentItemId ：付款行项目Id
//	 * @param refundmentId:	  退款Id
//	 * @return
//	 */
//	public List<VoucherItem> getVoucherItemForItemClear(String paymentItemId, String refundmentId)
//	{
//		/**
//		 * 获取款的凭证行项目
//		 */
////		String sql = "select b.VOUCHERNO voucherNo, a.ROWNUMBER rowNo, b.FIYEAR fiyear, a.amount amount, a.amount2 amount2, b.voucherid " +
////				"from YVOUCHERITEM a, YVOUCHER b where (a.VOUCHERID = b.VOUCHERID) " +
////				"and (a.BUSINESSITEMID = '" + paymentItemId + "' " + 
////				"or (a.VOUCHERID in (select voucherid from yvoucher where BUSINESSID ='" + refundmentId + "'  ) and trim(a.businessitemid) is not null ) )"; 
//		String sql = "select * " +
//        "from YVOUCHERITEM a, YVOUCHER b where (a.VOUCHERID = b.VOUCHERID) " +
//        " and (a.BUSINESSITEMID = '" + paymentItemId + "'" +
//        " or (a.VOUCHERID in (select voucherid from yvoucher where BUSINESSID ='" + refundmentId + "') and trim(a.businessitemid) is not null ))";
//        
//		
//		List<Map<String,String>> rowList = this.getJdbcTemplate().queryForList(sql);
//		
//		List<VoucherItem> voucherItemList = null;
////		if( rowList != null )
////		{
////			voucherItemList = new ArrayList<VoucherItem>();
////			for (Map<String,String> map : rowList)
////			{
////				VoucherItem voucherItem = new VoucherItem();
////				voucherItem.setFiyear(map.get("fiyear"));
////				voucherItem.setVoucherno(map.get("voucherNo"));
////				voucherItem.setRownumber(map.get("rowNo"));
////				voucherItem.setAmount( new BigDecimal(String.valueOf(map.get("amount"))) );
////				voucherItem.setAmount2( new BigDecimal(String.valueOf(map.get("amount2"))) );
////				voucherItem.setAmount2( new BigDecimal(String.valueOf(map.get("amount2"))) );
////				voucherItemList.add(voucherItem);
////			}
////		}
//      if( rowList != null )
//      {
//          voucherItemList = new ArrayList<VoucherItem>();
//	        for (Map map : rowList)
//	        {
//	            VoucherItem voucherItem = new VoucherItem();
//	            ExBeanUtils.setBeanValueFromMap(voucherItem, map);
//	            Voucher voucher = new Voucher();
//	            ExBeanUtils.setBeanValueFromMap(voucher, map);
//	            voucherItem.setVoucher(voucher);
//	            voucherItemList.add(voucherItem);
//	        }
//      }
//		return voucherItemList;		
//	}
	
	/**
	 * 获取款的凭证行项目（退款行项目清帐的情况）
	 * @param paymentItemId ：付款行项目Id
	 * @param refundmentId:	  退款Id
	 * @return
	 */
	public List<VoucherItem> getVoucherItemForItemClear2(String refundmentitemid, String paymentitemid, String refundmentid)
 {
		String sql = "select * "
				+ "from YVOUCHERITEM a, YVOUCHER b where (a.VOUCHERID = b.VOUCHERID) "
				+ " and ( (a.BUSINESSITEMID = '" + refundmentitemid + "' and b.businessid='"+refundmentid+"')"
				+ " or (a.businessitemid='" + paymentitemid + "' ))";

		List<Map<String, String>> rowList = this.getJdbcTemplate()
				.queryForList(sql);

		List<VoucherItem> voucherItemList = null;
		if (rowList != null) {
			voucherItemList = new ArrayList<VoucherItem>();
			for (Map map : rowList) {
				VoucherItem voucherItem = new VoucherItem();
				ExBeanUtils.setBeanValueFromMap(voucherItem, map);
				Voucher voucher = new Voucher();
				ExBeanUtils.setBeanValueFromMap(voucher, map);
				voucherItem.setVoucher(voucher);
				voucherItemList.add(voucherItem);
			}
		}
		return voucherItemList;
	}
	
	/**
	 * 获取凭证行项目（根据合同号清帐的情况，包括款和票的）
	 * @param contractNo
	 * @param refundmentId
	 * @return
	 */
	public List<VoucherItem> getVoucherItemForContractNoClear(String contractNo, String refundmentId)
	{
		/**
		 * 获取款的凭证行项目
		 */
		String sql = "select b.VOUCHERNO voucherNo, a.ROWNUMBER rowNo, b.FIYEAR fiyear " +
				"from YVOUCHERITEM a, YVOUCHER b where (a.VOUCHERID = b.VOUCHERID) " +
				"and (a.BUSINESSITEMID in (select COLLECTITEMID from YCOLLECTITEM" + 
				" where CONTRACT_NO in(" + contractNo + ") and ISCLEAR = '1' ) " +
				"or a.VOUCHERID in (select voucherid from yvoucher where BUSINESSID ='" + refundmentId + "'))"; 
		
		List<Map<String,String>> rowList = this.getJdbcTemplate().queryForList(sql);
		
		List<VoucherItem> voucherItemList = null;
		if( rowList != null )
		{
			voucherItemList = new ArrayList<VoucherItem>();
			for (Map<String,String> map : rowList)
			{
				VoucherItem voucherItem = new VoucherItem();
				voucherItem.setFiyear(map.get("fiyear"));
				voucherItem.setVoucherno(map.get("voucherNo"));
				voucherItem.setRownumber(map.get("rowNo"));
				voucherItemList.add(voucherItem);
			}
		}
		
		/**
		 * 获取票的凭证行项目
		 */
		sql = "select BELNR, GJAHR, BUZEI from YVENDORTITLE where trim(VERKF) is not null and " +
				" VERKF in(" + contractNo  +")" +
				" and ISCLEARED = '1' ";
		
		List<Map<String,String>> list = this.getJdbcTemplate().queryForList(sql);
		if( list!=null && list.size()>0 )
		{
			for(Map<String,String> map : list)
			{
				VoucherItem voucherItem = new VoucherItem();
				voucherItem.setFiyear(map.get("GJAHR"));
				voucherItem.setVoucherno(map.get("BELNR"));
				voucherItem.setRownumber(map.get("BUZEI"));
				voucherItemList.add(voucherItem);
			}
		}
		return voucherItemList;				
	}
	
	/**
	 * 获取凭证行项目（根据立项号清帐的情况，包括款和票的）
	 * @param clearedContractNo
	 * @param refundmentId
	 * @return
	 */
	public List<VoucherItem> getVoucherItemForProjectNoClear(String projectNo, 
			String clearedContractNo, 
			String refundmentId)
	{
		/**
		 * 获取款的凭证行项目
		 */
		String sql = "select b.VOUCHERNO voucherNo, a.ROWNUMBER rowNo, b.FIYEAR fiyear" +
				" from YVOUCHERITEM a, YVOUCHER b " +
				"where (a.VOUCHERID = b.VOUCHERID) and " +
				"(a.BUSINESSITEMID in (select COLLECTITEMID from YCOLLECTITEM" + 
				"where PROJECT_NO in(" + projectNo + ") and ISCLEAR = '1' ";
				//"CONTRACT_NO not in(" + clearedContractNo +") )" +
		//		" or a.VOUCHERID in (select voucherid from yvoucher where BUSINESSID ='" +
		//		refundmentId + "') )"; 
		
		if( clearedContractNo!=null && !clearedContractNo.equals("") )
		{
			sql += "CONTRACT_NO not in(" + clearedContractNo +") )";
		}
		sql += " or a.VOUCHERID in (select voucherid from yvoucher where BUSINESSID ='" +
				refundmentId + "') )"; 
		
		List<Map<String,String>> rowList = this.getJdbcTemplate().queryForList(sql);
		
		List<VoucherItem> voucherItemList = null;
		
		if( rowList != null )
		{	
			voucherItemList = new ArrayList<VoucherItem>();
			for (Map<String,String> map : rowList)
			{
				VoucherItem voucherItem = new VoucherItem();
				voucherItem.setFiyear(map.get("fiyear"));
				voucherItem.setVoucherno(map.get("voucherNo"));
				voucherItem.setRownumber(map.get("rowNo"));
				voucherItemList.add(voucherItem);
			}
		}
		
		/**
		 * 获取票的凭证行项目
		 */
		sql = "select BELNR, GJAHR, BUZEI from YVENDORTITLE " +
				"where " + " LIXIANG in(" + projectNo  +")" +
				" and ISCLEARED = '1'";  
		//		" and VERKF not in(" + clearedContractNo + ")";
		
		if( clearedContractNo!=null && !clearedContractNo.equals("") )
		{
			sql += " and VERKF not in(" + clearedContractNo + ")";
		}
				
		List<Map<String,String>> list = this.getJdbcTemplate().queryForList(sql);
		if( list!=null && list.size()>0 )
		{
			for(Map<String,String> map : list)
			{
				VoucherItem voucherItem = new VoucherItem();
				voucherItem.setFiyear(map.get("GJAHR"));
				voucherItem.setVoucherno(map.get("BELNR"));
				voucherItem.setRownumber(map.get("BUZEI"));
				voucherItemList.add(voucherItem);
			}			
		}
		return voucherItemList;		
	}
	
	/**
	 * 获取凭证行项目（根据供应商清帐的情况，包括款和票的）
	 * @param supplier
	 * @param clearedContract
	 * @param clearedProject
	 * @param refundmentid
	 * @return
	 */
	public List<VoucherItem> getVoucherItemForSupplierClear(String supplier, 
			String clearedContract, 
			String clearedProject, 
			String refundmentid,
			String bukrs,
			String currency,
			String gsber )
	{
		/**
		 * 款的行项目
		 */
//		String strSql = "select b.voucherno voucherNo, a.rownumber rowNo, b.fiyear fiyear " +
//		  "from yvoucheritem a, yvoucher b " +
//		 "where a.voucherid = b.voucherid " +
//		   "and b.businessid in " +
//		       "(select b.paymentid " +
//		          "from ypaymentitem a, ypayment b, T_SYS_DEPT c " +
//		         "where a.paymentid = b.paymentid and b.DEPT_ID = c.DEPT_ID and c.COMPANY_CODE = '" +
//		         	bukrs + "' " +
//		           "and a.BILLISCLEAR = '1' " +
//		           "and b.SUPPLIER = '"+supplier+"' ";
//		//           "and a.PROJECT_NO not in ("+clearedProject+") " +
//		//           "and a.CONTRACT_NO not in ("+clearedContract+") )"; 
//		
//		if( clearedContract!=null && !clearedContract.equals("") )
//		{
//			strSql += "and a.PROJECT_NO not in ("+clearedProject+") ";
//		}
//		if( clearedProject!=null && !clearedProject.equals("") )
//		{
//			strSql += "and a.CONTRACT_NO not in ("+clearedContract+")";
//		}
//		strSql += ")";
//		
//		List<Map<String,String>> rowList = this.getJdbcTemplate().queryForList(strSql);
//		
		List<VoucherItem> voucherItemList = new ArrayList<VoucherItem>();
//		
//		if( rowList != null )
//		{	
//			voucherItemList = new ArrayList<VoucherItem>();
//			for (Map<String,String> map : rowList)
//			{
//				VoucherItem voucherItem = new VoucherItem();
//				voucherItem.setFiyear(map.get("fiyear"));
//				voucherItem.setVoucherno(map.get("voucherNo"));
//				voucherItem.setRownumber(map.get("rowNo"));
//				voucherItemList.add(voucherItem);
//			}
//		}
		
		/**
		 * 
		 */
		String strSql = "select a.BELNR, a.GJAHR, a.BUZEI " +
		  "from YVENDORTITLE a " +
		  "where a.LIFNR = '"+supplier+"'" + 
		   "and a.ISCLEARED = '1' " +
		//   "and a.VERKF not in ("+clearedContract+") " +
		//   "and a.lixiang not in ("+clearedProject+") " +
		   "and BUKRS = '" + bukrs +"' and gsber = '"+gsber+"' and waers='"+currency+"' and saknr='2202010003'";
		
		if( clearedContract!=null && !clearedContract.equals("") )
		{
			strSql += " and a.VERKF not in ("+clearedContract+") ";
		}
		if( clearedProject!=null && !clearedProject.equals("") )
		{
			strSql += " and a.lixiang not in ("+clearedProject+") ";
		}
		
		List<Map<String,String>> list = this.getJdbcTemplate().queryForList(strSql);
		if( list!=null && list.size()>0 )
		{
			for(Map<String,String> map : list)
			{
				VoucherItem voucherItem = new VoucherItem();
				voucherItem.setFiyear(map.get("GJAHR"));
				voucherItem.setVoucherno(map.get("BELNR"));
				voucherItem.setRownumber(map.get("BUZEI"));
				voucherItemList.add(voucherItem);
			}			
		}
		
		return voucherItemList;		
	}

	/**
	 * 根据付款行项目Id获取分配金额本位币
	 * @param paymentItemId
	 * @return
	 */
	public BigDecimal getStaAmountByItemId( String paymentItemId)
	{
		String sql = "select ASSIGNAMOUNT2 from YPAYMENTITEM where PAYMENTITEMID = '" +
					paymentItemId + "'";
		
		return (BigDecimal)this.getJdbcTemplate().queryForObject(sql, BigDecimal.class);
	}
	
	/**
	 * 根据收款行项目Id获取分配金额
	 * @param paymentItemId
	 * @return
	 */
	public BigDecimal getAmountByItemId( String paymentItemId)
	{
		String sql = "select ASSIGNAMOUNT from YPAYMENTITEM where PAYMENTITEMID = '" +
						paymentItemId + "'";
		List<Map<String,Object>> list = this.getJdbcTemplate().queryForList(sql);
        if (list!=null && list.size() > 0 ) {
            return new BigDecimal( list.get(0).get("ASSIGNAMOUNT").toString() );
        }
        return new BigDecimal(0);
//		return (BigDecimal)this.getJdbcTemplate().queryForObject(sql, BigDecimal.class);
	}
	
	/**
	 * 根据合同号获取外围已清票的合计本位币金额
	 * @param contractNo
	 * @return
	 */
	public BigDecimal getSumStdInvoiceAmountByContractNo(String contractNo)
	{
		String sql = "select nvl(sum(BWBJE),0) sumInvoiceAmount from YVENDORTITLE " +
					"where VERKF in (" + contractNo + 
					") and ISCLEARED = '" +  cleared.isCleared + "'";		
		
		return (BigDecimal)this.getJdbcTemplate().queryForObject(sql,BigDecimal.class);
	}
	
	/**
	 * 根据合同号获取外围已清收款合计本位币金额
	 * @param contractNo
	 * @return
	 */
	public BigDecimal getSumStdAmountByContractNo(String contractNo)
	{
		String sql = "select nvl(sum(ASSIGNAMOUNT2),0) sumAmount from YPAYMENTITEM " +
				"where CONTRACT_NO in (" + contractNo + 
				") and BILLISCLEAR = '" +  cleared.isCleared + "'";

		return (BigDecimal)this.getJdbcTemplate().queryForObject(sql,BigDecimal.class);
	}	
	
	/**
	 * 根据立项号获取外围已清合计本位币金额
	 * @param projectNo: 立项号
	 * @param clearedContractNos: 已清合同号 例如 ： 'xxxxx','yyyyy'
	 * @return
	 */
	public BigDecimal getSumStdInvoiceAmountByProjectNo(String projectNo, String clearedContractNo)
	{
		String sql = "select nvl(sum(BWBJE),0) sumInvoiceAmount from YVENDORTITLE " +
					"where LIXIANG = in(" + projectNo + 
					") and ISCLEARED = '" +  cleared.isCleared + "'";
		//			"' and VERKF not in" + "(" + clearedContractNo + ")";
		
		if( clearedContractNo!= null && !clearedContractNo.equals("") )
		{
			sql += " and VERKF not in" + "(" + clearedContractNo + ")";
		}
				
		return (BigDecimal)this.getJdbcTemplate().queryForObject(sql,BigDecimal.class);
		
	}
	
	/**
	 * 根据立项号获取外围已清收款合计金额
	 * @param projectNo: 立项号
	 * @param clearedContractNos: 已清合同号 例如 ： 'xxxxx','yyyyy'
	 * @return
	 */
	public BigDecimal getSumStdAmountByProjectNo(String projectNo, String clearedContractNo)
	{
		String sql = "select nvl(sum(ASSIGNAMOUNT2),0) sumAmount from YPAYMENTITEM" +
					" where PROJECT_NO = in(" + projectNo + 
					") and BILLISCLEAR = '" +  cleared.isCleared + "'";
		//			"' and CONTRACT_NO not in (" + clearedContractNo + ")";
		
		if( clearedContractNo!= null && !clearedContractNo.equals("") )
		{
			sql += " and CONTRACT_NO not in (" + clearedContractNo + ")";
		}

		return (BigDecimal)this.getJdbcTemplate().queryForObject(sql,BigDecimal.class);
	}	
	
	/**
	 * 根据供应商统计外围已清的发票金额合计
	 * @param supplier
	 * @param clearedContractNo
	 * @param clearedProjectNo
	 * @param bukrs
	 * @return
	 */
	public BigDecimal getSumStdInvoiceAmountBySupplier(String supplier, 
								String clearedContractNo, 
								String clearedProjectNo,
								String bukrs)
	{
		String sql = "select nvl(sum(BWBJE),0) sumInvoiceAmount from YVENDORTITLE" +
				" where LIFNR = '" + supplier + 
				"' and ISCLEARED = '" +  cleared.isCleared + 
		//		"' and VERKF not in" + "(" + clearedContractNo + ")" +
		//		" and LIXIANG not in (" + clearedProjectNo + ")" +
				"' and BUKRS = '" + bukrs +"'";
		
		if( clearedContractNo!= null && !clearedContractNo.equals("") )
		{
			sql += " and VERKF not in" + "(" + clearedContractNo + ")";
		}
		if( clearedProjectNo!= null && !clearedProjectNo.equals("") )
		{
			sql += " and LIXIANG not in (" + clearedProjectNo + ")" ;
		}

		return (BigDecimal)this.getJdbcTemplate().queryForObject(sql,BigDecimal.class);
	}
	
	/**
	 * 根据供应商统计外围已清的款的本位币金额合计
	 * @param supplier
	 * @param clearedContractNo
	 * @param clearedProjectNo
	 * @param bukrs
	 * @return
	 */
	public BigDecimal getSumStdAmountBySupplier(String supplier, 
								String clearedContractNo, 
								String clearedProjectNo,
								String bukrs)
	{
		String sql = "select nvl(sum(ASSIGNAMOUNT2),0) sumAmount from YPAYMENTITEM " +
			"where PAYMENTID in " +
			"(select PAYMENTID from YPAYMENT a, T_SYS_DEPT b where a.DEPT_ID = b.DEPT_ID and " +
			"a.SUPPLIER = '" + supplier + "' and b.COMPANY_CODE = '" + bukrs + "')" +
			" and BILLISCLEAR = '" +  cleared.isCleared + "'";
		//	"' and CONTRACT_NO not in (" +clearedContractNo + ")" +
		//	" and PROJECT_NO not in (" + clearedProjectNo + ")";
		
		if( clearedContractNo!= null && !clearedContractNo.equals("") )
		{
			sql += " and CONTRACT_NO not in (" + clearedContractNo + ")" ;
		}
		if( clearedProjectNo!= null && !clearedProjectNo.equals("") )
		{
			sql += " and PROJECT_NO not in (" + clearedProjectNo + ")";
		}

		return (BigDecimal)this.getJdbcTemplate().queryForObject(sql,BigDecimal.class);
	}

	
	public boolean checkRefundContractClear(SupplierRefundItem supplierRefundItem){
		String contractNo = supplierRefundItem.getContract_no();
		String strSql1 = "select sum(dmbtr) from YVENDORTITLE a where a.VERKF = '" +contractNo+ "' and a.iscleared = '1'";
		
		
		String strSql2 = "select sum(a.assignamount) " +
					     " from ypaymentitem a " + 
					     " where a.contract_no = '"+contractNo+"' " + 
					     "  and a.billisclear = '0'";
		
		BigDecimal m_dmbtr = (BigDecimal) this.getJdbcTemplate().queryForObject(strSql1, BigDecimal.class);
		BigDecimal m_assignamount = (BigDecimal) this.getJdbcTemplate().queryForObject(strSql2, BigDecimal.class);
		
		if (m_dmbtr != null && m_assignamount != null &&
			m_dmbtr.compareTo(m_assignamount.subtract(supplierRefundItem.getRefundmentamount()))==0){
			
			return true;
		}else{
			return false;
		}
	}

    /**
     * 合计退款表中同一收款行项的所有"实际退款金额"
     * 取业务流程审批结束
     */
    public BigDecimal getSumPefundmentamountByPaymentItemId(String paymentitemid) {
        String sql = "SELECT NVL(SUM(RI.pefundmentamount), 0) "
                + "  FROM YREFUNDMENTITEM RI, YREFUNDMENT R  WHERE RI.PAYMENTITEMID = '"+paymentitemid+"' "
                + "   AND RI.REFUNDMENTID = R.REFUNDMENTID    AND R.BUSINESSSTATE = '3' ";
        return (BigDecimal) this.getJdbcTemplate().queryForObject(sql, BigDecimal.class);
    }
    
    /**
     * 获取原退款行项凭证信息(通过同一付款行项ID关联)
     */
    public List<VoucherItem> getVoucherItemForOldClean(String paymentitemid) {
        String sql = "select b.voucherno voucherno, a.rownumber rownumber, b.fiyear fiyear, b.voucherid voucherid " +
        "from YVOUCHERITEM a, YVOUCHER b where (a.VOUCHERID = b.VOUCHERID) " +
        " and (  a.BUSINESSITEMID in (select ri.refundmentitemid FROM YREFUNDMENTITEM RI, YREFUNDMENT R  " +
        "                                                 WHERE RI.PAYMENTITEMID = '"+paymentitemid+"'  " +
        "                                                     AND RI.REFUNDMENTID = R.REFUNDMENTID    AND R.BUSINESSSTATE = '3') )"; 
        
        List<VoucherItem> voucherItemList = new ArrayList<VoucherItem>();
        
        List<Map<String,Object>> rowList = this.getJdbcTemplate().queryForList(sql);
        for (Map map : rowList) {
            VoucherItem voucherItem = new VoucherItem();
            ExBeanUtils.setBeanValueFromMap(voucherItem, map);
            Voucher voucher = new Voucher();
            ExBeanUtils.setBeanValueFromMap(voucher, map);
            voucherItem.setVoucher(voucher);
            voucherItemList.add(voucherItem);
        }
        return voucherItemList;
    }
    
    public SupplierRefundment getSupplierRefundmentById(String id){
    	String sql = "select * from YREFUNDMENT a where  a.REFUNDMENTID='" + id + "'";
		List<Map> rowList = this.getJdbcTemplate().queryForList(sql.toString());
		if(rowList.size()==0)return null;
		SupplierRefundment supplierRefundment = new SupplierRefundment();
		ExBeanUtils.setBeanValueFromMap(supplierRefundment, rowList.get(0));
		
		sql = "select * from YREFUNDMENTITEM a where  a.REFUNDMENTID='" + id + "'";
		rowList = this.getJdbcTemplate().queryForList(sql.toString());
		Set<SupplierRefundItem> supplierRefundItemSet = new HashSet<SupplierRefundItem>();
		for(Map ci:rowList){
			SupplierRefundItem sri = new SupplierRefundItem();
			ExBeanUtils.setBeanValueFromMap(sri, ci);
			sri.setSupplierRefundment(supplierRefundment);
			supplierRefundItemSet.add(sri);
		}		
		supplierRefundment.setSupplierRefundItem(supplierRefundItemSet);
		
		sql = "select * from YREFUNDBANKITEM a where  a.REFUNDMENTID='" + id + "'";
		rowList = this.getJdbcTemplate().queryForList(sql.toString());
		Set<SupplierDbBankItem> supplierDbBankItemSet = new HashSet<SupplierDbBankItem>();
		for(Map ci:rowList){
			SupplierDbBankItem sri = new SupplierDbBankItem();
			ExBeanUtils.setBeanValueFromMap(sri, ci);
			sri.setSupplierRefundment(supplierRefundment);
			supplierDbBankItemSet.add(sri);
		}		
		supplierRefundment.setSupplierDbBankItem(supplierDbBankItemSet);
		
		sql="select * from YFUNDFLOW a where  a.REFUNDMENTID='" + id + "'";
		rowList = this.getJdbcTemplate().queryForList(sql.toString());	
		if(null != rowList && rowList.size()!=0){
			FundFlow ff = new FundFlow();
			ExBeanUtils.setBeanValueFromMap(ff,  rowList.get(0));
			ff.setSupplierRefundment(supplierRefundment);
			supplierRefundment.setFundFlow(ff);
		}
		
		sql="select * from YSETTLESUBJECT a where  a.REFUNDMENTID='" + id + "'";
		rowList = this.getJdbcTemplate().queryForList(sql.toString());	
		if(null != rowList && rowList.size()!=0){
			SettleSubject ss = new SettleSubject();
			ExBeanUtils.setBeanValueFromMap(ss,  rowList.get(0));
			ss.setSupplierRefundment(supplierRefundment);
			supplierRefundment.setSettleSubject(ss);
		}		
    	return supplierRefundment;
    }
}
