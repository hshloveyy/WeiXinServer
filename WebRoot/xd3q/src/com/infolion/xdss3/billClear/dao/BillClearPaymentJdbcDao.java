/*
 * @(#)BillInPaymentJdbcDao.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年06月23日 14点36分02秒
 *  描　述：创建
 */
package com.infolion.xdss3.billClear.dao;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Repository;

import com.infolion.platform.dpframework.core.dao.BaseJdbcDao;
import com.infolion.platform.dpframework.util.ExBeanUtils;
import com.infolion.xdss3.billClear.domain.BillClearPayment;
import com.infolion.xdss3.billClear.domain.BillClearItemPay;
import com.infolion.xdss3.billClear.domain.BillInPayment;
import com.infolion.xdss3.financeSquare.domain.FundFlow;
import com.infolion.xdss3.financeSquare.domain.SettleSubject;
/**
 * <pre>
 * 票清付款表(BillClearPayment),JDBC 操作类
 * </pre>
 * 
 * <br>
 * JDK版本:1.5
 * 
 * @author java业务平台代码生成工具
 * @version 1.0
 * @see The author for more details
 * @since 1.0
 */
@Repository
public class BillClearPaymentJdbcDao extends BaseJdbcDao
{
	public BillClearPayment getBillClearPaymentById(String id){
		
		String sql = "select * from YBILLCLEAR a where  a.BILLCLEARID='" + id + "'";
		List<Map> rowList = this.getJdbcTemplate().queryForList(sql.toString());
		if(rowList.size()==0)return null;
		BillClearPayment billClearPayment = new BillClearPayment();
		ExBeanUtils.setBeanValueFromMap(billClearPayment, rowList.get(0));
		
		sql = "select * from YBILLCLEARITEM a where  a.BILLCLEARID='" + id + "'";
		rowList = this.getJdbcTemplate().queryForList(sql.toString());
		Set<BillClearItemPay> bcipSet = new HashSet<BillClearItemPay>();
		for(Map ci:rowList){
			BillClearItemPay bcip = new BillClearItemPay();
			ExBeanUtils.setBeanValueFromMap(bcip, ci);
			bcip.setBillClearPayment(billClearPayment);
			bcipSet.add(bcip);			
		}
		billClearPayment.setBillClearItemPay(bcipSet);
		
		sql = "select * from YBILLINPAYMENT a where  a.BILLCLEARID='" + id + "'";
		rowList = this.getJdbcTemplate().queryForList(sql.toString());
		Set<BillInPayment> bipSet = new HashSet<BillInPayment>();
		for(Map ci:rowList){
			BillInPayment bip = new BillInPayment();
			ExBeanUtils.setBeanValueFromMap(bip, ci);
			bip.setBillClearPayment(billClearPayment);
			bipSet.add(bip);			
		}
		billClearPayment.setBillInPayment(bipSet);
		
		sql="select * from YFUNDFLOW a where  a.BILLCLEARID='" + id + "'";
		rowList = this.getJdbcTemplate().queryForList(sql.toString());	
		if(null != rowList && rowList.size()!=0){
			FundFlow ff = new FundFlow();
			ExBeanUtils.setBeanValueFromMap(ff,  rowList.get(0));
			ff.setBillClearPayment(billClearPayment);
			billClearPayment.setFundFlow(ff);
		}
		
		sql="select * from YSETTLESUBJECT a where  a.BILLCLEARID='" + id + "'";
		rowList = this.getJdbcTemplate().queryForList(sql.toString());	
		if(null != rowList && rowList.size()!=0){
			SettleSubject ss = new SettleSubject();
			ExBeanUtils.setBeanValueFromMap(ss,  rowList.get(0));
			ss.setBillClearPayment(billClearPayment);
			billClearPayment.setSettleSubject(ss);
		}		
		
		return billClearPayment;
	}
}