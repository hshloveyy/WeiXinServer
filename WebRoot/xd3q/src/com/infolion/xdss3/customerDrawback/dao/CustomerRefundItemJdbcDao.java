/*
 * @(#)CustomerRefundItemJdbcDao.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：邱荣发
 *  时　间：2010-6-13
 *  描　述：创建
 */

package com.infolion.xdss3.customerDrawback.dao;

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
import com.infolion.xdss3.collect.domain.Collect;
import com.infolion.xdss3.collectcbill.domain.CollectCbill;
import com.infolion.xdss3.collectitem.domain.CollectItem;
import com.infolion.xdss3.customerDrawback.domain.CustomerDbBankItem;
import com.infolion.xdss3.customerDrawback.domain.CustomerRefundItem;
import com.infolion.xdss3.customerDrawback.domain.CustomerRefundment;
import com.infolion.xdss3.financeSquare.domain.FundFlow;
import com.infolion.xdss3.financeSquare.domain.SettleSubject;
import com.infolion.xdss3.voucher.domain.Voucher;
import com.infolion.xdss3.voucheritem.domain.VoucherItem;

@Repository
public class CustomerRefundItemJdbcDao extends BaseJdbcDao
{
	/**
	 * 获取客户退款行项目数据
	 * @param strItemId
	 * @return
	 */
	public List<CustomerRefundItem> getCustomerRefundItem(String collectItemId){
		
		String strSql = "select a.COLLECTNO COLLECTNO, b.CONTRACT_NO CONTRACT_NO, b.PROJECT_NO PROJECT_NO, nvl(b.GOODSAMOUNT,0) clearamount, " +
				" nvl(b.ACTSURETYBOND,0) actsuretybond, nvl(b.assignamount - b.actsuretybond, 0) realgoodsamount9" +
				" from ycollect a, ycollectitem b where a.collectid = b.collectid and  b.COLLECTITEMID = '"
			 		+ collectItemId + "'";
		
		List<Map<String,Object>> list = this.getJdbcTemplate().queryForList(strSql);
		if(list == null || list.size() == 0 ){
			return null;
		}
		
		List<CustomerRefundItem> retList = new ArrayList<CustomerRefundItem>();
		Map<String,Object> retMap = this.getCurrencyByCollectItemId(collectItemId);

		/*
		 * 产生退货款行项目
		 */
		if(Double.parseDouble( list.get(0).get("realgoodsamount9").toString() ) > 0){

			CustomerRefundItem customerRefundItem = new CustomerRefundItem();
			customerRefundItem.setCollectno(list.get(0).get("COLLECTNO").toString());		// 收款单号
			customerRefundItem.setContract_no(list.get(0).get("CONTRACT_NO").toString());	// 合同号
			customerRefundItem.setProject_no(list.get(0).get("PROJECT_NO").toString());	// 立项号				
					
			//获取币别和汇率
			
			if(retMap != null ){
				customerRefundItem.setCurrency(retMap.get("billcurrency").toString());
				
				String strExchange = retMap.get("exchange").toString();
				customerRefundItem.setRefundcurrency(retMap.get("currency").toString());
				customerRefundItem.setExchangerate( new BigDecimal(strExchange) );	
			}
			
			// 统计分配金额
			// Map<String,BigDecimal> retListMap = this.getUnsignedAmountByCollectItemId(collectItemId);
			/*
			 * 邱杰烜 2010-10-13 重新调整行项金额与未分配金额的获取
			 */
			Map<String,BigDecimal> amountMap = this.getTotalUnassignedAmount(collectItemId);
			customerRefundItem.setAssignamount(amountMap.get("goodsAmount"));				
			customerRefundItem.setUnassignamount(amountMap.get("unassignedAmount"));
			customerRefundItem.setCollectitemid(collectItemId);
			customerRefundItem.setIstybond("N");
			if (customerRefundItem.getUnassignamount().compareTo(new BigDecimal(0)) > 0) {
			    retList.add(customerRefundItem);
			}
		}
		
		
		
		/*
		 * 实际剩余保证金大于0(保证金退款)
		 */
		String strActsuretybond = list.get(0).get("actsuretybond").toString();
		if(Double.parseDouble( strActsuretybond ) > 0){
		    
		    //　合计在批保证金金额，用于保证金可退金额为做相应减少
		    BigDecimal pefundmentamount = new BigDecimal(0);
		    String sql2 = " select pefundmentamount" +
		    		"  from yrefundmentitem ri, yrefundment r" +
		    		" where ri.refundmentid = r.refundmentid" +
		    		"   and ri.collectitemid = '" + collectItemId+"' " +
		    		"   and ri.istybond = 'Y'" +
		    		"   and r.businessstate in ('0', '1', '2', '-2') ";
		    
		    List<Map<String,Object>> list2 = this.getJdbcTemplate().queryForList(sql2);
		    for (Map map : list2) {
		        pefundmentamount = pefundmentamount.add((BigDecimal)map.get("PEFUNDMENTAMOUNT"));
		    }
	        
			/*
			 * 产生退货款行项目
			 */
			CustomerRefundItem customerRefundItem1 = new CustomerRefundItem();
			customerRefundItem1.setCollectno(list.get(0).get("COLLECTNO").toString());		// 收款单号
			customerRefundItem1.setContract_no(list.get(0).get("CONTRACT_NO").toString());	// 合同号
			customerRefundItem1.setProject_no(list.get(0).get("PROJECT_NO").toString());	// 立项号				
					
			if(retMap != null ){
				customerRefundItem1.setCurrency(retMap.get("currency").toString());
				
				String strExchange = retMap.get("exchange").toString();
				customerRefundItem1.setRefundcurrency(retMap.get("currency").toString());
				customerRefundItem1.setExchangerate( new BigDecimal(strExchange) );	
			}
				
			customerRefundItem1.setAssignamount( new BigDecimal(strActsuretybond) );				
			customerRefundItem1.setUnassignamount( new BigDecimal(strActsuretybond).subtract(pefundmentamount) );
			customerRefundItem1.setCollectitemid(collectItemId);
			customerRefundItem1.setIstybond("Y");
			
			retList.add(customerRefundItem1);
		}
		
		return retList;
	}
	
	
	/**
	 * 获取客户退款银行信息
	 * @param bankId
	 * @return
	 */
	public CustomerDbBankItem getCustomerDbBankItem(String bankId)
	{
		CustomerDbBankItem customerDbBankItem = new CustomerDbBankItem();
		String strSql = "select BANK_ID, BANK_NAME, BANK_ACCOUNT, BANK_HKONT from YBANK_INFO where BANK_ID = '"
						+ bankId + "'";
		List<Map<String,Object>> list = this.getJdbcTemplate().queryForList(strSql);
		customerDbBankItem.setBanksubject(list.get(0).get("BANK_HKONT").toString());
		customerDbBankItem.setRefundbankid(list.get(0).get("BANK_ID").toString());
		customerDbBankItem.setRefundbankname(list.get(0).get("BANK_NAME").toString());
		customerDbBankItem.setRefundbankacount(list.get(0).get("BANK_ACCOUNT").toString());
		
		customerDbBankItem.setRefundtype("C");	//客户
		return customerDbBankItem;
	}
	
	/**
	 * 根据客户id获得客户名称
	 * @param customerId
	 * @param bukrs
	 * @return
	 */
	public String getCustomerDescByCustomerId( String customerId, String burks )
	{
		String sql = "select NAME1 customerName from YGETKUNNR where KUNNR = '" + customerId + "' and BUKRS = '"
					+ burks + "'";
		
		List<Map<String,String>> list = this.getJdbcTemplate().queryForList(sql);
		String strRet = "";
		if( list!= null && list.size() > 0)
		{
			strRet = list.get(0).get("customerName");
		}
		return strRet;
	}
	
	/**
	 * 根据供应商id获得供应商名称
	 * @param customerId
	 * @param bukrs
	 * @return
	 */
	public String getSupplierDescByCustomerId( String supplierId, String burks )
	{
		String sql = "select NAME1 supplierName from YGETLIFNR where LIFNR = '" + supplierId + "' and BUKRS = '"
					+ burks + "'";
		
		List<Map<String,String>> list = this.getJdbcTemplate().queryForList(sql);
		String strRet = "";
		if( list!= null && list.size() > 0)
		{
			strRet = list.get(0).get("supplierName");
		}
		return strRet;
	}
	
	/**
	 * 根据退款ID,获得原币币别和退款币别
	 * @param refundmentid
	 * @return
	 */
	public Map<String, String> getCurrency(String refundmentid)
	{
		String sql = "select CURRENCY currency, REFUNDCURRENCY refundCurrency from YREFUNDMENTITEM where REFUNDMENTID = '" 
						+ refundmentid + "'";
		
		List<Map<String,String>> list = this.getJdbcTemplate().queryForList(sql);
		
		if(list != null && list.size() >0 )
		{
			return list.get(0);
		}
		else
		{
			return null;
		}				
	}
	
	public String getSkont( VoucherItem voucherItem ,String subject)
	{
		if(StringUtils.isNullBlank(voucherItem.getCheckcode())||StringUtils.isNullBlank(voucherItem.getGlflag()))return subject;
		String koart = "";
		if(voucherItem.getCheckcode().equals("09")||voucherItem.getCheckcode().equals("19")){
			koart = "D";
		}else if(voucherItem.getCheckcode().equals("29")||voucherItem.getCheckcode().equals("39")){
			koart = "K";
		}
		if(StringUtils.isNullBlank(koart))return subject;
		
		String sql = "select skont from yt074 where hkont = '" + subject + "' and umskz = '" + voucherItem.getGlflag() + "' and koart = '" + koart + "'";
		List<Map<String,String>> list = this.getJdbcTemplate().queryForList(sql);
		if(list != null && list.size() >0 )
		{
			return list.get(0).get("skont");
		}else{
			return subject;
		}
	}
	
	public String getSubjectNameById( String subjectid, String companycode )
	{
		if(StringUtils.isNullBlank(subjectid))return "";
		String sql = "select TXT20 name from YSKAT where SAKNR = '" + subjectid + "' ";//and BUKRS = '" + companycode + "'
		List<Map<String,String>> list = this.getJdbcTemplate().queryForList(sql);
		if(list != null && list.size() >0 )
		{
			return list.get(0).get("name");
		}else{
			return "";
		}
		
	}

	public String getSubjectByCustomer( String customer, String companycode )
	{
		String sql = "select akont from ygetkunnr where kunnr = '" + customer + "' and bukrs = '" + companycode + "'";
		List<Map<String,String>> list = this.getJdbcTemplate().queryForList(sql);
		if(list != null && list.size() >0 )
		{
			return list.get(0).get("akont");
		}else{
			return "";
		}
	}
	
	public String getCountryByCustomer( String customer, String companycode )
	{
		String sql = "select land1 from ygetkunnr where kunnr = '" + customer + "' and bukrs = '" + companycode + "'";
		List<Map<String,String>> list = this.getJdbcTemplate().queryForList(sql);
		if(list != null && list.size() >0 )
		{
			return list.get(0).get("land1");
		}else{
			return "";
		}
	}
	
	public String getSubjectBySuppliers( String suppliers, String companycode )
	{
		String sql = "select akont from ygetlifnr where lifnr = '" + suppliers + "' and bukrs = '" + companycode + "'";
		List<Map<String,String>> list = this.getJdbcTemplate().queryForList(sql);
		if(list != null && list.size() >0 )
		{
			return list.get(0).get("akont");
		}else{
			return "";
		}
	}
	
	/**
	 * 根据退款ID插入关联单据表。
	 * @param refundmentId
	 * @return
	 */
	public void saveRelatedCollectByRefundmentid(String refundmentId)
	{
		List<Map<String,Object>> retList = new ArrayList<Map<String,Object>>();
		
		List<Map<String,String>> collectNoList = new ArrayList<Map<String,String>>();
				
		String sql = "select distinct(COLLECTNO) collectNo from YREFUNDMENTITEM where REFUNDMENTID = '"
					+ refundmentId + "'";
		collectNoList = this.getJdbcTemplate().queryForList(sql);
		
		sql = "select REFUNDMENTNO refundmentNo from YREFUNDMENT where REFUNDMENTID = '" + refundmentId + "'";
		List<Map<String,String>> retRefundmentNoList = this.getJdbcTemplate().queryForList(sql);
		
		String fundmentNo = retRefundmentNoList.get(0).get("refundmentNo");
		
		if(collectNoList != null && collectNoList.size() > 0 ) 
		{
			for( int i = 0; i < collectNoList.size(); i++ )
			{
				String collectNo = collectNoList.get(i).get("collectNo");
				
				/**
				 * 获得收款ID
				 */
				sql = "select COLLECTID collectid from YCOLLECT where COLLECTNO = '"
					+ collectNo + "'";
				
				List<Map<String, String>> collectIdList = this.getJdbcTemplate().queryForList(sql);
				String collectId = collectIdList.get(0).get("collectid");
				
				
				/**
				 * 累加原币退款金额
				 */
				sql = "select sum(PEFUNDMENTAMOUNT) totalAmount from YREFUNDMENTITEM where REFUNDMENTID = '"
						+ refundmentId + "'";
				long totalAmount = this.getJdbcTemplate().queryForLong(sql);
				
				
				/**
				 * 插入数据
				 */
				
				String uId = CodeGenerator.getUUID();
				
				sql = "insert into YCOLLECTRELATED(MANDT,COLLECTRELATEDID,COLLECTID,REFUNDMENTID,RELATEDNO,APPLYAMOUNT,RELATEDTYPE)" 
					+ " values('800', '" 
					+ uId + "', '"
					+ collectId + "', '"
					+ refundmentId + "', '"
					+ fundmentNo + "', '"
					+ totalAmount 
					+ "', '2' )";
				
				this.getJdbcTemplate().execute(sql);
			
			}
		}
		
	}
	
	/**
	 * 退款凭证产生后，更新货款金额
	 */
	public void updateCollectItem(String refundmentId)
	{
		String sql = "select COLLECTITEMID collectItemid, nvl(PEFUNDMENTAMOUNT,0) preRefundAmount, nvl(UNASSIGNAMOUNT,0) unsignedAmount, ISTYBOND istybond from YREFUNDMENTITEM where REFUNDMENTID = '"
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
				
				/**
				 * 未分配金额退款金额
				 */
				BigDecimal unsignedAmount = new BigDecimal(map.get("unsignedAmount").toString());
				
				String istybond = map.get("istybond").toString();
				
				
				/**
				 * 判断是否保证金退款
				 */
				if(istybond.equals("Y"))		//保证金退款
				{
					/**
					 * 更新付款表的剩余保证金金额
					 */
					String updateSql = "update YCOLLECTITEM set ACTSURETYBOND = (ACTSURETYBOND - " 
						+ preRefundAmount.toString() + ") where COLLECTITEMID = '"
						+ collectItemid + "'";
					
					this.getJdbcTemplate().execute(updateSql);
				}		
				else
				{
					String updateSql = "";
					
					/**
					 * 判断是否全额退款
					 * 全额退款需要设置清款标志为已清
					 */
					if(preRefundAmount.compareTo(unsignedAmount) == 0)
					{
						updateSql = "update YCOLLECTITEM set GOODSAMOUNT = (GOODSAMOUNT - " 
							+ preRefundAmount.toString() + "), ISCLEAR = '1' where COLLECTITEMID = '"
							+ collectItemid + "'";
					}
					
					else
					{
						updateSql = "update YCOLLECTITEM set GOODSAMOUNT = (GOODSAMOUNT - " 
							+ preRefundAmount.toString() + ") where COLLECTITEMID = '"
							+ collectItemid + "'";
					}
					this.getJdbcTemplate().execute(updateSql);
				}
			}
		}	
	}
	
	/**
	 * 根据收款分配表ID获取币别
	 * @param collectItemId
	 * @return
	 */
	private Map<String,Object> getCurrencyByCollectItemId( String collectItemId )
	{
//		String strSql = "select CURRENCY currency, COLLECTRATE exchangerate, BILLCURRENCY billcurrency from YCOLLECT where COLLECTID in( select COLLECTID from YCOLLECTITEM where COLLECTITEMID = '"
//			+ collectItemId + "') ";
		String strSql = "select CURRENCY currency, SETTLERATE settlerate, BILLCURRENCY billcurrency from YCOLLECT where COLLECTID in( select COLLECTID from YCOLLECTITEM where COLLECTITEMID = '"
			+ collectItemId + "') ";
		
		List<Map<String,String>> retList =  this.getJdbcTemplate().queryForList(strSql);
		
		// 未获取值
		if( retList == null || retList.size()== 0 )
		{
			return null;
		}
		Map<String,Object> retMap = new HashMap<String,Object>();
		retMap.put("currency", retList.get(0).get("currency"));
		retMap.put("exchange", retList.get(0).get("settlerate"));
		retMap.put("billcurrency", retList.get(0).get("billcurrency"));
		return retMap;
	}
	
	/**
	 * 根据收款分配表ID获取未分配金额
	 * @param collectItemId
	 * @return
	 */
	public Map<String,BigDecimal> getUnsignedAmountByCollectItemId( String collectItemId ){
		/**
		 * 货款金额
		 */
		Map<String,BigDecimal> retListMap = new HashMap<String,BigDecimal>();
		String strSql = "select nvl(GOODSAMOUNT,0) amount from YCOLLECTITEM where COLLECTITEMID = '" 
						+ collectItemId + "'";
		BigDecimal amount = null;
		
	      Map<String,BigDecimal> amountMap = new HashMap<String,BigDecimal>();
	        
	        List<Map<String,BigDecimal>> list1 = this.getJdbcTemplate().queryForList(strSql, BigDecimal.class);
	        // 用于合同、立项行项未有collectItemId直接返回未分配金额0
	        if (list1==null || list1.size()<1) {
	            retListMap.put("assignAmount", new BigDecimal(0));
	            retListMap.put("unAssignedAmount", new BigDecimal(0) );   
	            return retListMap;
	        } else {
	            amount  = (BigDecimal)this.getJdbcTemplate().queryForObject(strSql, BigDecimal.class);
	        }
		
		
		/**
		 * 获取收款状态
		 */
		strSql = "select PROCESSSTATE processstate from YCOLLECT where COLLECTID in (select COLLECTID from YCOLLECTITEM where COLLECTITEMID = '"
				+ collectItemId +"')";		
		List<Map<String,String>> processStateList = this.getJdbcTemplate().queryForList(strSql);
		String processState = "0";
		if( processStateList != null && processStateList.size() > 0 )
		{
			processState = processStateList.get(0).get("processstate").trim();
		}
		
		/**
		 * 判断是否需要计算清帐金额和本行抵消金额
		 * 状态3： 审批通过
		 * 需要计算
		 */
		if(processState.equals(BusinessState.SUBMITPASS)){
			/**
			 * 累加本行抵消金额
			 */
			strSql = "select nvl(sum(nowclearamount),0) as totalClearedValue from ybillincollect where collectitemid ='"
				+ collectItemId	+ "'";
		
			BigDecimal totalNowclearamount = (BigDecimal)this.getJdbcTemplate().queryForObject(strSql,BigDecimal.class);
			
			
			/**
			 * 累加清帐金额
			 */
			strSql = "select nvl(sum(CLEARAMOUNT),0) as totalClearAmount from YCOLLECTCBILL where COLLECTID in " +
					"( select COLLECTID from YCOLLECTITEM where COLLECTITEMID = '" + collectItemId + "' )";
		
			BigDecimal totalClearAmount = (BigDecimal)this.getJdbcTemplate().queryForObject(strSql, BigDecimal.class);
			
			retListMap.put("assignAmount", amount);
			retListMap.put("unAssignedAmount", amount.subtract(totalNowclearamount).subtract(totalClearAmount) );	
		}else{
			retListMap.put("assignAmount", amount );
			retListMap.put("unAssignedAmount", amount );
		}		
		return retListMap;
	}
	
	/**
	 * @创建作者：邱杰烜
	 * @创建日期：2010-10-13
	 * 获取退款行项金额，
	 * 并从YBILLINCOLLECT与YUNCLEARCOLLECT中取出BUSINESSSTATE<>'-1'的本行抵消金额的总和，
	 * 最后求出未分配金额
	 */
	public Map<String,BigDecimal> getTotalUnassignedAmount(String collectItemId){
		// 从YCOLLECTITEM根据COLLECTITEMID取出预收票款作为退款行项的金额
		String sql1 = "SELECT NVL(PREBILLAMOUNT,0) PREBILLAMOUNT FROM YCOLLECTITEM WHERE COLLECTITEMID = '" + collectItemId + "'";
		// 从YCOLLECTITEM根据COLLECTITEMID取出贷款金额作为退款行项的金额
		String sql2 = "SELECT NVL(GOODSAMOUNT,0) GOODSAMOUNT FROM YCOLLECTITEM WHERE COLLECTITEMID = '" + collectItemId + "'";
		// 从YBILLINCOLLECT根据COLLECTITEMID取出NOWCLEARAMOUNT，再根据YBILLINCOLLECT-BILLCLEARID从YBILLCLEAR取出BUSINESSSTATE<>'-1'
		String sql3 = "SELECT NVL(a.NOWCLEARAMOUNT,0) NOWCLEARAMOUNT FROM YBILLINCOLLECT a " +
					  "INNER JOIN YBILLCLEAR b ON a.BILLCLEARID=b.BILLCLEARID WHERE b.BUSINESSSTATE<>'-1' AND " +
					  "a.COLLECTITEMID='" + collectItemId + "'";
		
		// 从YUNCLEARCOLLECT根据COLLECTITEMID取出NOWCLEARAMOUNT再根据YUNCLEARCOLLECT-CUSTOMSCLEARID从YCUSTOMSICLEAR取出BUSINESSSTATE<>'-1'
		String sql4 = "SELECT NVL(a.NOWCLEARAMOUNT,0) NOWCLEARAMOUNT FROM YUNCLEARCOLLECT a " +
					  "INNER JOIN YCUSTOMSICLEAR b ON a.CUSTOMSCLEARID=b.CUSTOMSCLEARID WHERE b.BUSINESSSTATE<>'-1' AND " +
					  "a.COLLECTITEMID='" + collectItemId + "'";
		// 业务状态不处于-1作废需扣除清原币金额
		String sql5 = "  select nvl(sum(refundmentamount), 0) as refundmentamount from YREFUNDMENTITEM t " +
				"inner join YREFUNDMENT y on t.refundmentid = y.refundmentid " +
				" where y.businessstate <> '-1' AND t.ISTYBOND='N' and t.collectitemid = '" + collectItemId + "'";
		
		// 保证金转贷款
		String sql6 = " select nvl(ci.assignamount, 0) regoodamount" +
				"  from ycollectitem ci, ycollect c " +
				" where ci.collectid=c.collectid and c.Incsuretybond='N' and c.businessstate in ('3') and c.oldcollectitemid =  '" + collectItemId + "' ";
		
		List<Map<String,BigDecimal>> list1 = this.getJdbcTemplate().queryForList(sql3, BigDecimal.class);
		
		List<Map<String,BigDecimal>> list2 = this.getJdbcTemplate().queryForList(sql4, BigDecimal.class);
		List<Map<String,BigDecimal>> list3 = this.getJdbcTemplate().queryForList(sql5, BigDecimal.class);
		BigDecimal totalUnassignedAmount = new BigDecimal(0);
		// 获取预收票款
		BigDecimal prebillAmount = (BigDecimal)this.getJdbcTemplate().queryForObject(sql1, BigDecimal.class);
		// 获取货款
		BigDecimal goodsAmount = (BigDecimal)this.getJdbcTemplate().queryForObject(sql2, BigDecimal.class);
		// 保证金转贷款
		BigDecimal regoodamount = new BigDecimal(0);
		List<Map<String,BigDecimal>> list4 = this.getJdbcTemplate().queryForList(sql6, BigDecimal.class);
		for (int i=0;i<list4.size();i++) {
		    regoodamount =  regoodamount.add((BigDecimal) list4.get(i));
		}
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
		// 未分配金额 = 预收票款 - 所有本行抵消金额和
		totalUnassignedAmount = prebillAmount.subtract(totalUnassignedAmount).add(regoodamount);
	      Map<String,BigDecimal> amountMap = new HashMap<String,BigDecimal>();
		amountMap.put("goodsAmount", goodsAmount);
		amountMap.put("unassignedAmount", totalUnassignedAmount);
		return amountMap;
	}

	/**
	 * 获取实际剩余保证金金额
	 * @param collectItemId
	 * @return
	 */
	public BigDecimal getActsuretybond( String collectItemId ){
		String strSql = "select nvl(ACTSURETYBOND,0) actsuretybond  from ycollectitem where COLLECTITEMID = '"
	 		+ collectItemId + "'";

		BigDecimal actSuretyBond = (BigDecimal)this.getJdbcTemplate().queryForObject(strSql,BigDecimal.class);
	
		return actSuretyBond;
	}
	
	/**
	 * 通过合同号、收款号判断收款行项中是否有保证金，有保证金(不为零)返回true
	 * @param contractNo
	 * @return
	 */
	public boolean judgeHaveActsuretybondByContractNo(String contractNo, String collectno)
	{
	    String sql = " select c.actsuretybond  from ycollectitem c where c.collectid  in " +
	    		"  (select collectid from ycollect where collectno='"+collectno+"')" +
	    		" and c.contract_no ='"+contractNo+"' ";
//	    BigDecimal actsuretybond = (BigDecimal) this.getJdbcTemplate().queryForObject(sql, BigDecimal.class);
	    BigDecimal actsuretybond = new BigDecimal(0);
	       List<Map<String,Object>> list = this.getJdbcTemplate().queryForList(sql);
	        if (list!=null && list.size() > 0 ) {
	            actsuretybond = new BigDecimal( list.get(0).get("actsuretybond").toString() );
	        } 
	    return actsuretybond.compareTo(new BigDecimal(0))>0;
	}

	/**
	 * 根据合同号获取外围已清票的合计金额
	 * @param contractNo
	 * @return
	 */
	public BigDecimal getSumInvoiceAmountByContractNo(String contractNo)
	{
		String sql = "select nvl(sum(DMBTR),0) sumInvoiceAmount from YCUSTOMERTITLE where IHREZ = '" +
					contractNo + "' and ISCLEARED = '" +  cleared.isCleared + "'";
		
		
		return (BigDecimal)this.getJdbcTemplate().queryForObject(sql,BigDecimal.class);
	}
	
	/**
	 * 根据合同号获取外围已清收款合计金额
	 * @param contractNo
	 * @return
	 */
	public BigDecimal getSumAmountByContractNo(String contractNo)
	{
		String sql = "select nvl(sum(ASSIGNAMOUNT),0) sumAmount from YCOLLECTITEM where CONTRACT_NO = '" +
				contractNo + "' and ISCLEAR = '" +  cleared.isCleared + "'";

		return (BigDecimal)this.getJdbcTemplate().queryForObject(sql,BigDecimal.class);
	}	
	
	/**
	 * 根据立项号获取外围已清合计金额
	 * @param projectNo: 立项号
	 * @param clearedContractNos: 已清合同号 例如 ： 'xxxxx','yyyyy'
	 * @return
	 */
	public BigDecimal getSumInvoiceAmountByProjectNo(String projectNo, String clearedContractNo){
		String sql = "select nvl(sum(DMBTR),0) sumInvoiceAmount from YCUSTOMERTITLE " +
				"where BNAME = '" +	projectNo + 
				"' and ISCLEARED = '" +  cleared.isCleared + "'";
		
		if( clearedContractNo!= null && !clearedContractNo.equals("") ){
			sql += " and IHREZ not in" + "(" + clearedContractNo + ")";
		}
				
		return (BigDecimal)this.getJdbcTemplate().queryForObject(sql,BigDecimal.class);
		
	}
	
	/**
	 * 根据立项号获取外围已清收款合计金额
	 * @param projectNo: 立项号
	 * @param clearedContractNos: 已清合同号 例如 ： 'xxxxx','yyyyy'
	 * @return
	 */
	public BigDecimal getSumAmountByProjectNo(String projectNo, String clearedContractNo){
		String sql = "select nvl(sum(ASSIGNAMOUNT),0) sumAmount " +
					"from YCOLLECTITEM where PROJECT_NO = '" + projectNo + 
					"' and ISCLEAR = '" +  cleared.isCleared + "'"; 
					
		
		if( clearedContractNo!= null && !clearedContractNo.equals("") ){
			sql += " and CONTRACT_NO not in (" + clearedContractNo + ")";
		}

		return (BigDecimal)this.getJdbcTemplate().queryForObject(sql,BigDecimal.class);
	}	
	
	/**
	 * 根据客户统计外围已清的发票金额合计
	 * @param customerId
	 * @param clearedContractNo
	 * @param clearedProjectNo
	 * @return
	 */
	public BigDecimal getSumInvoiceAmountByCustomer(String customerId, 
								String clearedContractNo, 
								String clearedProjectNo,
								String bukrs){
		String sql = "select nvl(sum(DMBTR),0) sumInvoiceAmount" +
				" from YCUSTOMERTITLE where KUNNR = '" + customerId + 
				"' and ISCLEARED = '" +  cleared.isCleared + "'" +
				" and shkzg='S' and BUKRS = '" + bukrs + "'";
				
		if( clearedContractNo!= null && !clearedContractNo.equals("") ){
			sql += 	" and IHREZ not in" + "(" + clearedContractNo + ") " ;
		}
		if( clearedProjectNo!= null && !clearedProjectNo.equals("") ){
			sql += " and BNAME not in (" + clearedProjectNo + ")";
		}
				
		return (BigDecimal)this.getJdbcTemplate().queryForObject(sql,BigDecimal.class);
	}
	
	/**
	 * 根据客户统计外围已清的款的金额合计
	 * @param customerId
	 * @param clearedContractNo
	 * @param clearedProjectNo
	 * @return
	 */
	public BigDecimal getSumAmountByCustomer(String customerId, 
								String clearedContractNo, 
								String clearedProjectNo,
								String bukrs){
		String sql = "select nvl(sum(ASSIGNAMOUNT),0) sumAmount from YCOLLECTITEM " +
				"where COLLECTID in " +
				"(select COLLECTID from YCOLLECT a, T_SYS_DEPT b where a.DEPT_ID = b.DEPT_ID and b.COMPANY_CODE = '" + bukrs +
				"' and a.CUSTOMER = '" + customerId + "')" +
				" and ISCLEAR = '" +  cleared.isCleared + "'";
		
		if( clearedContractNo!= null && !clearedContractNo.equals("") ){
			sql += 	" and CONTRACT_NO not in (" + clearedContractNo + ")" ;
		}
		if( clearedProjectNo!= null && !clearedProjectNo.equals("") ){
			sql += " and PROJECT_NO not in (" + clearedProjectNo + ")";
		}
		return (BigDecimal)this.getJdbcTemplate().queryForObject(sql,BigDecimal.class);
	}
	
//	/**
//	 * 获取款的凭证行项目（退款行项目清帐的情况）
//	 * @param collectItemId ：收款行项目Id
//	 * @param refundmentId:	  退款Id
//	 * @return
//	 */
//	public List<VoucherItem> getVoucherItemForItemClear(String collectItemId, String refundmentId){
//		String sql = "select b.VOUCHERNO voucherNo, a.ROWNUMBER rowNo, b.FIYEAR fiyear " +
//		"from YVOUCHERITEM a, YVOUCHER b where (a.VOUCHERID = b.VOUCHERID) " +
//		"and (a.BUSINESSITEMID = '" + collectItemId + "'" +
//		" or a.VOUCHERID in (select voucherid from yvoucher where BUSINESSID ='" + refundmentId + "'))"; 
//		
//		List<Map<String,String>> rowList = this.getJdbcTemplate().queryForList(sql);
//		
//		List<VoucherItem> voucherItemList = null;
//		if( rowList != null) {
//			voucherItemList = new ArrayList<VoucherItem>();
//			for (Map<String,String> map : rowList){
//				VoucherItem voucherItem = new VoucherItem();
//				voucherItem.setFiyear(map.get("fiyear"));
//				voucherItem.setVoucherno(map.get("voucherNo"));
//				voucherItem.setRownumber(map.get("rowNo"));
//				voucherItemList.add(voucherItem);
//			}
//		}
//		return voucherItemList;		
//	}
//	
//    /**
//     * 获取款的凭证行项目（退款行项目清帐的情况）
//     * 
//     * @param collectItemId：收款行项目Id
//     * @param refundmentId：退款Id
//     * @return
//     */
//    public List<VoucherItem> getVoucherItemForItemClear2(String collectItemId, String refundmentId) {
//        String sql = "select b.voucherno voucherno, a.rownumber rownumber, b.fiyear fiyear, b.voucherid voucherid " +
//        "from YVOUCHERITEM a, YVOUCHER b where (a.VOUCHERID = b.VOUCHERID) " +
//        " and (a.BUSINESSITEMID = '" + collectItemId + "'" +
//        " or (a.VOUCHERID in (select voucherid from yvoucher where BUSINESSID ='" + refundmentId + "') and trim(a.businessitemid) is not null ))"; 
//
//        List<VoucherItem> voucherItemList = new ArrayList<VoucherItem>();
//
//        List<Map<String,Object>> rowList = this.getJdbcTemplate().queryForList(sql);
//        for (Map map : rowList) {
//            VoucherItem voucherItem = new VoucherItem();
//            ExBeanUtils.setBeanValueFromMap(voucherItem, map);
//            Voucher voucher = new Voucher();
//            ExBeanUtils.setBeanValueFromMap(voucher, map);
//            voucherItem.setVoucher(voucher);
//            voucherItemList.add(voucherItem);
//        }
//        return voucherItemList;
//    }
    
    /**
     * 获取款的凭证行项目（退款行项目清帐的情况）
     * 
     * @param collectItemId：收款行项目Id
     * @param refundmentId：退款Id
     * @return
     */
    public List<VoucherItem> getVoucherItemForItemClear3(String refundmentitemid, String refundmentId) {
        String sql = "select b.voucherno voucherno, a.rownumber rownumber, b.fiyear fiyear, b.voucherid voucherid " +
        "from YVOUCHERITEM a, YVOUCHER b where (a.VOUCHERID = b.VOUCHERID) " +
        " and (a.BUSINESSITEMID = '" + refundmentitemid + "'" +
        " or (a.VOUCHERID in (select voucherid from yvoucher where BUSINESSID ='" + refundmentId + "') and trim(a.businessitemid) is not null ))"; 

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
    
    /**
     * 获取当前退款凭证行项目（退款行项目清帐的情况）
     * 
     * @param collectItemId：收款行项目Id
     * @param refundmentId：退款Id
     * @return
     */
    public List<VoucherItem> getVoucherItemJustRefundment(String refundmentitemid) {
        String sql = "select b.voucherno voucherno, a.rownumber rownumber, b.fiyear fiyear, b.voucherid voucherid " +
        "from YVOUCHERITEM a, YVOUCHER b where (a.VOUCHERID = b.VOUCHERID) " +
        " and (  a.BUSINESSITEMID = '"+refundmentitemid +"' )"; 
        
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
    
    /**
     * 获取款的凭证行项目与当前退款凭证行项目（退款行项目清帐的情况）
     * 
     * @param collectItemId：收款行项目Id
     * @param refundmentId：退款Id
     * @return
     */
    public List<VoucherItem> getVoucherItemForItemClear4(String collectitemid, String refundmentitemid) {
        String sql = "select b.voucherno voucherno, a.rownumber rownumber, b.fiyear fiyear, b.voucherid voucherid " +
        "from YVOUCHERITEM a, YVOUCHER b where (a.VOUCHERID = b.VOUCHERID) " +
        " and (      a.BUSINESSITEMID = '" + collectitemid + "' " +
        "           or a.BUSINESSITEMID = '"+refundmentitemid +"' )"; 
        
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
    
    /**
     * 获取部分清帐凭证行项目
     * 
     * @param collectItemId：收款行项目Id
     * @param refundmentId：退款Id
     * @return
     */
    public List<VoucherItem> getVoucherItemForBill(String billno) {
        String sql = "select c.buzei rownumber, c.gjahr fiyear, c.belnr voucherno from ycustomertitle c where c.invoice='"+ billno + "'"; 
        List<VoucherItem> voucherItemList = new ArrayList<VoucherItem>();
        
        List<Map> rowList = this.getJdbcTemplate().queryForList(sql);
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
    
	/**
	 * 获取凭证行项目（根据合同号清帐的情况，票的）
	 * @param contractNo
	 * @param refundmentId
	 * @return
	 */
	public List<VoucherItem> getVoucherItemForContractNoClearBill(String contractNo, String refundmentId){
		
		List<VoucherItem> voucherItemList = null;
		
		/**
		 * 获取票的凭证行项目
		 */
		String sql = "select BELNR, GJAHR, BUZEI, BWBJE from YCUSTOMERTITLE where trim(IHREZ) is not null and " +
				" IHREZ in(" + contractNo  +")" +
				" and ISCLEARED = '1' ";
		
		List<Map<String,Object>> list = this.getJdbcTemplate().queryForList(sql);
		if( list!=null && list.size()>0 ){
		    voucherItemList = new ArrayList<VoucherItem>();
			for(Map<String,Object> map : list){
				VoucherItem voucherItem = new VoucherItem();
				voucherItem.setFiyear((String)map.get("GJAHR"));
				voucherItem.setVoucherno((String)map.get("BELNR"));
				voucherItem.setRownumber((String)map.get("BUZEI"));
				voucherItem.setAmount2((BigDecimal)map.get("BWBJE"));
				voucherItemList.add(voucherItem);
			}
		}
		return voucherItemList;				
	}
	
	/**
	 * 获取凭证行项目（根据合同号清帐的情况，款的）
	 * @param contractNo
	 * @param refundmentId
	 * @return
	 */
	public List<VoucherItem> getVoucherItemForContractNoClear(String contractNo, String refundmentId, String subject){
	    
	    /**
         * 获取款的凭证行项目
         */
        String sql = "select b.VOUCHERNO voucherNo, a.ROWNUMBER rowNo, b.FIYEAR fiyear, a.amount2 amount2 " +
                "from YVOUCHERITEM a, YVOUCHER b where (a.VOUCHERID = b.VOUCHERID) " +
                "and (a.BUSINESSITEMID in (select COLLECTITEMID from YCOLLECTITEM" + 
                " where CONTRACT_NO in(" + contractNo + ") and ISCLEAR = '1' ) " +
                "or a.VOUCHERID in (select voucherid from yvoucher where BUSINESSID ='" + refundmentId + "')) " +
                " and a.subject='"+subject+"' and a.glflag=' ' "; 
        
        List<Map<String,Object>> rowList = this.getJdbcTemplate().queryForList(sql);
        
        List<VoucherItem> voucherItemList = null;
        if( rowList != null ){
            voucherItemList = new ArrayList<VoucherItem>();
            for (Map<String,Object> map : rowList){
                VoucherItem voucherItem = new VoucherItem();
                voucherItem.setFiyear((String)map.get("fiyear"));
                voucherItem.setVoucherno((String)map.get("voucherNo"));
                voucherItem.setRownumber((String)map.get("rowNo"));
                voucherItem.setAmount2((BigDecimal)map.get("amount2"));
                voucherItemList.add(voucherItem);
            }
        }

	    return voucherItemList;				
	}
	
//	/**
//	 * 获取当前款凭证行项目（根据合同号、退款项目编号）
//	 * @param contractNo
//	 * @param refundmentId
//	 * @return
//	 */
//	public List<VoucherItem> getVoucherItemForContractNoClear2(String contractNo, String refundmentitemid){
//        String sql = "select collectno, collectitemid from yrefundmentitem ri where "
//                + " ri.refundmentitemid = '" + refundmentitemid + "'" + " and ri.contract_no='"+ contractNo + "' "; 
//	    
//	    List<Map<String,String>> rowList = this.getJdbcTemplate().queryForList(sql);
//	    
//	    List<VoucherItem> voucherItemList = null;
//	    if( rowList != null ){
//	        voucherItemList = new ArrayList<VoucherItem>();
//	        for (Map<String,String> map : rowList){
//	            String collectno = map.get("collectno");
//	            String collectitemid = map.get("collectitemid");
//	            sql = "select b.VOUCHERNO voucherNo, a.ROWNUMBER rowNo, b.FIYEAR fiyear, a.amount2 amount2" +
//	            		"  from YVOUCHERITEM a, YVOUCHER b" +
//	            		" where (a.VOUCHERID = b.VOUCHERID)" +
//	            		"   and (a.businessitemid ='" + collectitemid +"' )" +
//	            		"   and b.voucherid in" +
//	            		"       (select voucherid" +
//	            		"          from yvoucher" +
//	            		"         where BUSINESSID in" +
//	            		"               (select cc.collectid" +
//	            		"                  from ycollect cc" +
//	            		"                 where cc.collectno = '"+collectno+"'))";
//	            List<Map<String,Object>> list = this.getJdbcTemplate().queryForList(sql);
//	            if( list!=null && list.size()>0 ){
//	                for(Map<String,Object> map2 : list){
//        	            VoucherItem voucherItem = new VoucherItem();
//        	            voucherItem.setFiyear((String)map2.get("fiyear"));
//        	            voucherItem.setVoucherno((String)map2.get("voucherNo"));
//        	            voucherItem.setRownumber((String)map2.get("rowNo"));
//        	            voucherItem.setAmount2((BigDecimal)map2.get("amount2"));
//        	            voucherItemList.add(voucherItem);
//	                }
//	            }
//	        }
//	    }
//	    return voucherItemList;				
//	}
	
	/**
	 * 获取当前款凭证行项目（根据合同号、退款项目编号）
	 * @param contractNo
	 * @param refundmentId
	 * @return
	 */
	public List<VoucherItem> getVoucherItemForContractNoClear3(String contractNo, String refundmentitemid){
        String sql = "select collectno, collectitemid from yrefundmentitem ri where "
                + " ri.refundmentitemid = '" + refundmentitemid + "'" + " and ri.contract_no='"+ contractNo + "' "; 
	    
	    List<Map<String,String>> rowList = this.getJdbcTemplate().queryForList(sql);
	    
	    List<VoucherItem> voucherItemList = null;
	    if( rowList != null ){
	        voucherItemList = new ArrayList<VoucherItem>();
	        for (Map<String,String> map : rowList){
	            String collectno = map.get("collectno");
	            String collectitemid = map.get("collectitemid");
	            sql = "select b.VOUCHERNO voucherNo, a.ROWNUMBER rowNo, b.FIYEAR fiyear, a.amount2 amount2" +
	            		"  from YVOUCHERITEM a, YVOUCHER b" +
	            		" where (a.VOUCHERID = b.VOUCHERID)" +
	            		"   and (a.businessitemid ='" + refundmentitemid +"' )" +
	            		"   and b.voucherid in" +
	            		"       (select voucherid" +
	            		"          from yvoucher" +
	            		"         where BUSINESSID in" +
	            		"               (select cc.collectid" +
	            		"                  from ycollect cc" +
	            		"                 where cc.collectno = '"+collectno+"'))";
	            List<Map<String,Object>> list = this.getJdbcTemplate().queryForList(sql);
	            if( list!=null && list.size()>0 ){
	                for(Map<String,Object> map2 : list){
        	            VoucherItem voucherItem = new VoucherItem();
        	            voucherItem.setFiyear((String)map2.get("fiyear"));
        	            voucherItem.setVoucherno((String)map2.get("voucherNo"));
        	            voucherItem.setRownumber((String)map2.get("rowNo"));
        	            voucherItem.setAmount2((BigDecimal)map2.get("amount2"));
        	            voucherItemList.add(voucherItem);
	                }
	            }
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
			String refundmentId){
		/**
		 * 获取款的凭证行项目
		 */
		String sql = "select b.VOUCHERNO voucherNo, a.ROWNUMBER rowNo, b.FIYEAR fiyear" +
				" from YVOUCHERITEM a, YVOUCHER b " +
				"where (a.VOUCHERID = b.VOUCHERID) and " +
				"(a.BUSINESSITEMID in (select COLLECTITEMID from YCOLLECTITEM" + 
				"where PROJECT_NO in(" + projectNo + ") and ISCLEAR = '1' "; 
				
		if( clearedContractNo!=null && !clearedContractNo.equals("") ){
			sql += " and CONTRACT_NO not in(" + clearedContractNo +")";
		}
		sql +=" ) or a.VOUCHERID in (select voucherid from yvoucher where BUSINESSID ='" +
			refundmentId + "') )"; 
		
		List<Map<String,String>> rowList = this.getJdbcTemplate().queryForList(sql);
		
		List<VoucherItem> voucherItemList = null;
		
		if( rowList != null ){	
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
		sql = "select BELNR, GJAHR, BUZEI from YCUSTOMERTITLE " +
				"where trim(BNAME) is not null " +
				"and BNAME in(" + projectNo  +")" +
				" and ISCLEARED = '1'";
		if( clearedContractNo!=null && !clearedContractNo.equals("") ){
			sql += " and IHREZ not in(" + clearedContractNo + ")";
		}
		List<Map<String,String>> list = this.getJdbcTemplate().queryForList(sql);
		if( list!=null && list.size()>0 ){
			for(Map<String,String> map : list){
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
	 * 获取凭证行项目（根据客户清帐的情况，包括款和票的）
	 * @param customerId
	 * @param clearedContract
	 * @param clearedProject
	 * @param refundmentid
	 * @return
	 */
	public List<VoucherItem> getVoucherItemForCustomerClear(String customerId, 
			String clearedContract, 
			String clearedProject, 
			String refundmentid,
			String bukrs,
			String currency,
			String gsber ){
		/**
		 * 当前收款的行项目
		 */
//		String strSql = "select b.voucherno voucherNo, a.rownumber rowNo, b.fiyear fiyear " +
//		  "from yvoucheritem a, yvoucher b " +
//		 "where a.voucherid = b.voucherid " +
//		   "and b.businessid in " +
//		       "(select b.COLLECTID " +
//		          "from ycollectitem a, ycollect b, T_SYS_DEPT c " +
//		         "where a.COLLECTID = b.COLLECTID and b.DEPT_ID = c.DEPT_ID " +
//		           "and c.COMPANY_CODE = '" + bukrs +"'" +
//		           "and a.ISCLEAR = '1' " +
//		           "and b.CUSTOMER = '"+customerId+"' ";
//		         
//		
//		if( clearedContract!=null && !clearedContract.equals("") ){
//			strSql += "and a.PROJECT_NO not in ("+clearedProject+") ";
//		}
//		if( clearedProject!=null && !clearedProject.equals("") ){
//			strSql += "and a.CONTRACT_NO not in ("+clearedContract+") ";
//		}
//		strSql += ")";
//		
//		List<Map<String,String>> rowList = this.getJdbcTemplate().queryForList(strSql);
		
		List<VoucherItem> voucherItemList =  new ArrayList<VoucherItem>();
		
//		if( rowList != null ){				
//			for (Map<String,String> map : rowList){
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
		  "from YCUSTOMERTITLE a " +
		  "where a.KUNNR = '"+customerId+"'" + 
		   "and a.ISCLEARED = '1' " +
		  // "and a.IHREZ not in ("+clearedContract+") " +
		  // "and a.BNAME not in ("+clearedProject+") " +
		   "and BUKRS = '" + bukrs +"' and gsber = '"+gsber+"' and waers='"+currency+"' and saknr='1122010003'";
		
		if( clearedContract!=null && !clearedContract.equals("") ){
			strSql += " and a.IHREZ not in ("+clearedContract+")";
		}
		if( clearedProject!=null && !clearedProject.equals("") ){
			strSql += " and a.BNAME not in ("+clearedProject+") ";
		}
		
		List<Map<String,String>> list = this.getJdbcTemplate().queryForList(strSql);
		if( list!=null && list.size()>0 ){
			for(Map<String,String> map : list){
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
	 * 根据收款行项目Id获取分配金额本位币
	 * @param collectItemId
	 * @return
	 */
	public BigDecimal getStaAmountByItemId( String collectItemId){
		String sql = "select ASSIGNAMOUNT2 from YCOLLECTITEM where COLLECTITEMID = '" +
						collectItemId + "'";
		
		return (BigDecimal)this.getJdbcTemplate().queryForObject(sql, BigDecimal.class);
	}
	
	/**
	 * 根据收款行项目Id查询同一收款ID的收款行项目
	 * @param collectid
	 * @return
	 */
	public List<CollectItem> getCollectItemListByItemId( String collectitemid){
        String sql = "select * from ycollect a, YCOLLECTITEM b  where a.collectid=b.collectid and b.collectid in (select collectid from ycollectitem where collectitemid = '" + collectitemid + "') ";
        List<Map<String,String>> rowList = this.getJdbcTemplate().queryForList(sql);
        List<CollectItem> voucherItemList = null;
        if( rowList != null) {
            voucherItemList = new ArrayList<CollectItem>();
            for (Map<String,String> map : rowList){
                CollectItem item = new CollectItem();
                Collect collect = new Collect();
                ExBeanUtils.setBeanValueFromMap(item, map);
                ExBeanUtils.setBeanValueFromMap(collect, map);
                item.setCollect(collect);
                voucherItemList.add(item);
            }
        }
	    return voucherItemList;
	}
	
	/**
	 * 根据收款抬头Id查询收款发票项
	 * @param collectid
	 * @return
	 */
	public List<CollectCbill> getCollectcBillListByItemId( String collectid){
	    String sql = "select * from ycollectcbill where collectid = '" + collectid + "'";
	    List<Map<String,String>> rowList = this.getJdbcTemplate().queryForList(sql);
	    List<CollectCbill> billList = null;
	    if( rowList != null) {
	        billList = new ArrayList<CollectCbill>();
	        for (Map<String,String> map : rowList){
	            CollectCbill bill = new CollectCbill();
                Collect collect = new Collect();
                ExBeanUtils.setBeanValueFromMap(bill, map);
                ExBeanUtils.setBeanValueFromMap(collect, map);
                bill.setCollect(collect);
                billList.add(bill);
            }
	    }
	    return billList;
	}
	
	/**
	 * 根据收款行项目Id获取分配金额
	 * @param collectItemId
	 * @return
	 */
	public BigDecimal getAmountByItemId( String collectItemId){
		String sql = "select ASSIGNAMOUNT from YCOLLECTITEM where COLLECTITEMID = '" +
						collectItemId + "'";
		List<Map<String,Object>> list = this.getJdbcTemplate().queryForList(sql);
		if (list!=null && list.size() > 0 ) {
		    return new BigDecimal( list.get(0).get("ASSIGNAMOUNT").toString() );
		}
		return new BigDecimal(0);
	}

	/**
	 * 根据合同号获取票的本位币合计
	 * @param contractNo
	 * @return
	 */
	public BigDecimal getSumStaInvoiceAmountByContractNo( String contractNo ){
		String sql = "select nvl(sum(BWBJE),0) sumInvoiceAmount from YCUSTOMERTITLE where IHREZ in(" +
			contractNo + ") and ISCLEARED = '" +  cleared.isCleared + "'";

		return (BigDecimal)this.getJdbcTemplate().queryForObject(sql,BigDecimal.class);
	}
	
	/**
	 * 根据合同号获取外围已清收款本位币金额合计
	 * @param contractNo
	 * @return
	 */
	public BigDecimal getSumStdAmountByContractNo(String contractNo){
		String sql = "select nvl(sum(ASSIGNAMOUNT2),0) sumAmount from YCOLLECTITEM where CONTRACT_NO in (" +
				contractNo + ") and ISCLEAR = '" +  cleared.isCleared + "'";

		return (BigDecimal)this.getJdbcTemplate().queryForObject(sql,BigDecimal.class);
	}	
	
	/**
	 * 根据立项号获取外围已清合计本位币金额
	 * @param projectNo: 立项号
	 * @param clearedContractNos: 已清合同号 例如 ： 'xxxxx','yyyyy'
	 * @return
	 */
	public BigDecimal getSumStdInvoiceAmountByProjectNo(String projectNo, String clearedContractNo){
		String sql = "select nvl(sum(BWBJE),0) sumInvoiceAmount from YCUSTOMERTITLE " +
				"where BNAME = in(" +	projectNo + 
				") and ISCLEARED = '" +  cleared.isCleared + "'";
		//		"' and IHREZ not in" + "(" + clearedContractNo + ")";
		
		if(clearedContractNo != null && !clearedContractNo.equals("")){
			sql += " and IHREZ not in" + "(" + clearedContractNo + ")";
		}
		return (BigDecimal)this.getJdbcTemplate().queryForObject(sql,BigDecimal.class);
		
	}
	
	/**
	 * 根据立项号获取外围已清收款合计金额
	 * @param projectNo: 立项号
	 * @param clearedContractNos: 已清合同号 例如 ： 'xxxxx','yyyyy'
	 * @return
	 */
	public BigDecimal getSumStdAmountByProjectNo(String projectNo, String clearedContractNo){
		String sql = "select nvl(sum(ASSIGNAMOUNT2),0) sumAmount " +
					"from YCOLLECTITEM where PROJECT_NO in (" + projectNo + 
					") and ISCLEAR = '" +  cleared.isCleared + "'";
					//"' and CONTRACT_NO not in (" + clearedContractNo + ")";
		
		if(clearedContractNo != null && !clearedContractNo.equals("")){
			sql += " and CONTRACT_NO not in (" + clearedContractNo + ")";
		}
		
		return (BigDecimal)this.getJdbcTemplate().queryForObject(sql,BigDecimal.class);
	}	
	
	/**
	 * 根据客户统计外围已清的发票本位币金额合计
	 * @param customerId
	 * @param clearedContractNo
	 * @param clearedProjectNo
	 * @return
	 */
	public BigDecimal getSumStdInvoiceAmountByCustomer(String customerId, String clearedContractNo, String clearedProjectNo, String bukrs){
		String sql = "select nvl(sum(BWBJE),0) sumInvoiceAmount" +
				" from YCUSTOMERTITLE where KUNNR = '" + customerId + 
				"' and ISCLEARED = '" +  cleared.isCleared + 
				//"' and IHREZ not in" + "(" + clearedContractNo + ") " +
				//" and BNAME not in (" + clearedProjectNo + ")" +
				"' and BUKRS = '" + bukrs + "'";
		
		if( clearedContractNo!= null && !clearedContractNo.equals("") ){
			sql += " and IHREZ not in" + "(" + clearedContractNo + ") ";
		}
		if( clearedProjectNo!= null && !clearedProjectNo.equals("") ){
			sql += " and BNAME not in (" + clearedProjectNo + ")";
		}

		return (BigDecimal)this.getJdbcTemplate().queryForObject(sql,BigDecimal.class);
	}
	
	/**
	 * 根据客户统计外围已清款的本位币金额合计
	 * @param customerId
	 * @param clearedContractNo
	 * @param clearedProjectNo
	 * @return
	 */
	public BigDecimal getSumStdAmountByCustomer(String customerId, 
								String clearedContractNo, 
								String clearedProjectNo,
								String bukrs)
	{
		String sql = "select nvl(sum(ASSIGNAMOUNT2),0) sumAmount from YCOLLECTITEM " +
				"where COLLECTID in " +
				"(select COLLECTID from YCOLLECT a, T_SYS_DEPT b where a.DEPT_ID = b.DEPT_ID and b.COMPANY_CODE = '" + bukrs +
				"' and a.CUSTOMER = '" + customerId + "')" +
				" and ISCLEAR = '" +  cleared.isCleared + "'";
			//	"' and CONTRACT_NO not in (" + clearedContractNo + ")" +
			//	" and PROJECT_NO not in (" + clearedProjectNo + ")";
		
		if( clearedContractNo!= null && !clearedContractNo.equals("") ){
			sql += " and CONTRACT_NO not in (" + clearedContractNo + ")" ;
		}
		if( clearedProjectNo!= null && !clearedProjectNo.equals("") ){
			sql += " and PROJECT_NO not in (" + clearedProjectNo + ")";
		}

		return (BigDecimal)this.getJdbcTemplate().queryForObject(sql,BigDecimal.class);
	}
	   
    /**
     * 更新客户退款分配表行项目是否已结清标志。
     * 
     * @param vendortitleid
     * @param isCleared
     */
    public void updateRefundItemIsCleared(String refundItemId, String isCleared)
    {
        String sql = "update yrefundmentitem set isclear ='" + isCleared + "' where refundmentitemid ='" + refundItemId + "'";
        log.debug("更新<客户退款>分配表行项目是否已结清标志:" + sql);
        this.getJdbcTemplate().execute(sql);
    }
    
    /**
     * 更新客户退款业务状态BusinessState。
     * 
     * @param vendortitleid
     * @param isCleared
     */
    public void updateRefundBusinessState(String refundId, String businessState)
    {
        String sql = "update yrefundment set businessState ='" + businessState + "' where refundmentId ='" + refundId + "'";
        log.debug("更新客户退款业务状态BusinessState:" + sql);
        this.getJdbcTemplate().execute(sql);
    }
    
    /**
     * @创建作者：邱杰烜
     * @创建日期：2010-12-22
     * 更新客户退款行项对应的收款行项的实际剩余保证金
     */
    public void updateActsuretybond(String refundmentId, String collectItemId){
        String sql1 = "UPDATE YCOLLECTITEM A SET A.ACTSURETYBOND = " +
            		  "NVL((SELECT (A.ACTSURETYBOND - B.REFUNDMENTAMOUNT) AS ACTSURETYBOND FROM YREFUNDMENTITEM B " +
            		  "WHERE b.REFUNDMENTID='" + refundmentId +
            		  "' AND b.COLLECTITEMID='" + collectItemId +
            		  "' AND B.ISTYBOND = 'Y'), A.ACTSURETYBOND) WHERE A.COLLECTITEMID = '" + collectItemId +
            		  "'";
        String sql2 = "UPDATE YCOLLECT A SET A.REMAINSURETYBOND = " +
                      "NVL((SELECT (A.REMAINSURETYBOND - B.REFUNDMENTAMOUNT) AS REMAINSURETYBOND FROM YREFUNDMENTITEM B " +
                      "WHERE B.REFUNDMENTID = '" + refundmentId +
                      "' AND B.COLLECTITEMID = '" + collectItemId +
                      "' AND B.ISTYBOND = 'Y'), A.REMAINSURETYBOND) WHERE A.COLLECTID = (SELECT C.COLLECTID FROM YCOLLECTITEM C WHERE C.COLLECTITEMID='" + collectItemId +
                      "')";
        this.getJdbcTemplate().execute(sql1);
        this.getJdbcTemplate().execute(sql2);
    }

    /**
     * 合计退款表中同一收款行项的所有"实际退款金额"
     * 取业务流程审批结束
     */
    public BigDecimal getSumPefundmentamountByCollectItemId(String collectItemId) {
        String sql = "SELECT NVL(SUM(RI.pefundmentamount), 0) "
                + "  FROM YREFUNDMENTITEM RI, YREFUNDMENT R  WHERE RI.COLLECTITEMID = '"+collectItemId+"' "
                + "   AND RI.REFUNDMENTID = R.REFUNDMENTID    AND R.BUSINESSSTATE = '3' ";
        return (BigDecimal) this.getJdbcTemplate().queryForObject(sql, BigDecimal.class);
    }
    
    /**
     * 获取原退款行项凭证信息(通过同一收款行项ID关联)
     */
    public List<VoucherItem> getVoucherItemForOldClean(String collectItemId) {
        String sql = "select b.voucherno voucherno, a.rownumber rownumber, b.fiyear fiyear, b.voucherid voucherid " +
        "from YVOUCHERITEM a, YVOUCHER b where (a.VOUCHERID = b.VOUCHERID) " +
        " and (  a.BUSINESSITEMID in (select ri.refundmentitemid FROM YREFUNDMENTITEM RI, YREFUNDMENT R  " +
        "                                                 WHERE RI.COLLECTITEMID = '"+collectItemId+"'  " +
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

    public CustomerRefundment getCustomerRefundmentById(String id){
    	String sql = "select * from YREFUNDMENT a where  a.REFUNDMENTID='" + id + "'";
		List<Map> rowList = this.getJdbcTemplate().queryForList(sql.toString());
		if(rowList.size()==0)return null;
		CustomerRefundment customerRefundment = new CustomerRefundment();
		
		ExBeanUtils.setBeanValueFromMap(customerRefundment, rowList.get(0));
		
		sql = "select * from YREFUNDMENTITEM a where  a.REFUNDMENTID='" + id + "'";
		rowList = this.getJdbcTemplate().queryForList(sql.toString());
		Set<CustomerRefundItem> customerRefundItemSet = new HashSet<CustomerRefundItem>();
		for(Map ci:rowList){
			CustomerRefundItem cri = new CustomerRefundItem();
			ExBeanUtils.setBeanValueFromMap(cri, ci);
			cri.setCustomerRefundment(customerRefundment);
			customerRefundItemSet.add(cri);
		}		
		customerRefundment.setCustomerRefundItem(customerRefundItemSet);
		
		sql = "select * from YREFUNDBANKITEM a where  a.REFUNDMENTID='" + id + "'";
		rowList = this.getJdbcTemplate().queryForList(sql.toString());
		Set<CustomerDbBankItem> customerDbBankItemSet = new HashSet<CustomerDbBankItem>();
		for(Map ci:rowList){
			CustomerDbBankItem cri = new CustomerDbBankItem();
			ExBeanUtils.setBeanValueFromMap(cri, ci);
			cri.setCustomerRefundment(customerRefundment);
			customerDbBankItemSet.add(cri);
		}		
		customerRefundment.setCustomerDbBankItem(customerDbBankItemSet);
		
		sql="select * from YFUNDFLOW a where  a.custrefundmentid='" + id + "'";
		rowList = this.getJdbcTemplate().queryForList(sql.toString());	
		if(null != rowList && rowList.size()!=0){
			FundFlow ff = new FundFlow();
			ExBeanUtils.setBeanValueFromMap(ff,  rowList.get(0));
			ff.setCustomerRefundment(customerRefundment);
			customerRefundment.setFundFlow(ff);
		}
		
		sql="select * from YSETTLESUBJECT a where  a.custrefundmentid='" + id + "'";
		rowList = this.getJdbcTemplate().queryForList(sql.toString());	
		if(null != rowList && rowList.size()!=0){
			SettleSubject ss = new SettleSubject();
			ExBeanUtils.setBeanValueFromMap(ss,  rowList.get(0));
			ss.setCustomerRefundment(customerRefundment);
			customerRefundment.setSettleSubject(ss);
		}		
		
    	return customerRefundment;
    }
}
