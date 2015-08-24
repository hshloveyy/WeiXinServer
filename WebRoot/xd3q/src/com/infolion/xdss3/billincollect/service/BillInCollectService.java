/*
 * @(#)BillInCollectService.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年06月12日 07点53分34秒
 *  描　述：创建
 */
package com.infolion.xdss3.billincollect.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infolion.xdss3.billincollect.dao.BillInCollectJdbc;
import com.infolion.xdss3.billincollect.service.BillInCollectService;
import com.infolion.xdss3.billincollectGen.service.BillInCollectServiceGen;
import com.infolion.xdss3.voucher.domain.Voucher;

/**
 * <pre>
 * 发票收款关系(BillInCollect)服务类
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
@Service
public class BillInCollectService extends BillInCollectServiceGen
{
	private Log log = LogFactory.getFactory().getLog(this.getClass());
	
	@Autowired
	private BillInCollectJdbc billInCollectJdbc;
	
	public BillInCollectJdbc getBillInCollectJdbc() {
		return billInCollectJdbc;
	}

	public void setBillInCollectJdbc(BillInCollectJdbc billInCollectJdbc) {
		this.billInCollectJdbc = billInCollectJdbc;
	}

	public BigDecimal getSumOnroadAmount(String collectitemid, String cleartype){
		BigDecimal sum = new BigDecimal(0);
		String sql = "select nvl(sum(nowclearamount),0) as nowclearamount from YBILLINCOLLECT a left join YBILLCLEAR b on a.billclearid = b.billclearid where b.businessstate = '0'" +  " and a.collectitemid= '" + collectitemid + "'"
		+ " union all select nvl(sum(nowclearamount),0) as nowclearamount from YUnClearCollect unClearCollect left join YCUSTOMSICLEAR customiclear on unClearCollect.Customsclearid = customiclear.customsclearid"
		+	" where  unClearCollect.Collectitemid =  '" + collectitemid + "'" + "and customiclear.businessstate != '3'";

		List<Map<String,Number>> result = this.billInCollectJdbc.getJdbcTemplate().queryForList(sql);
		
		if(result != null && result.size() > 0){
			for(Map<String,Number> m : result){
				Number a = m.get("nowclearamount");
				sum.add(BigDecimal.valueOf(a.doubleValue()));
			}
		}
		
		return sum;
	}
	
	public BigDecimal getSumClearedAmount(String collectitemid, String cleartype){BigDecimal sum = new BigDecimal(0);
		String sql = "select nvl(sum(nowclearamount),0) as nowclearamount from YBILLINCOLLECT a left join YBILLCLEAR b on a.billclearid = b.billclearid where b.businessstate = '1'" +  " and a.collectitemid= '" + collectitemid + "'"
		+ " union all select nvl(sum(nowclearamount),0) as nowclearamount from YUnClearCollect unClearCollect left join YCUSTOMSICLEAR customiclear on unClearCollect.Customsclearid = customiclear.customsclearid"
		+	" where  unClearCollect.Collectitemid =  '" + collectitemid + "'" + "and customiclear.businessstate = '3'";
		
		List<Map<String,Number>> result = this.billInCollectJdbc.getJdbcTemplate().queryForList(sql);
		
		if(result != null && result.size() > 0){
			for(Map<String,Number> m : result){
				Number a = m.get("nowclearamount");
				sum.add(BigDecimal.valueOf(a.doubleValue()));
			}
		}
		
		return sum;
	}
	
	public BigDecimal getSumClearedInvoice(String billNo){
		BigDecimal sum = new BigDecimal(0);
		String sql = "select nvl(sum(clearamount),0) as clearamount from YCOLLECTCBILL a left join YCollect b on a.collectid = b.collectid where b.businessstate = '3'" +  " and a.billno= '" + billNo + "'"
		+ " union all select nvl(sum(clearbillamount),0) as clearamount from YBILLCLEARITEM a left join YBILLCLEAR b on a.billclearid = b.billclearid"
		+	" where  a.invoice =  '" + billNo + "'" + " and b.businessstate = '1'"  
		+ " union all select nvl(sum(clearamount),0) as clearamount from YUNCLEARCUSTBILL unClearcustBill left join YCUSTOMSICLEAR customiclear on unClearcustBill.Customsclearid = customiclear.customsclearid"
		+	" where  unClearcustBill.billno =  '" + billNo + "'" + " and customiclear.businessstate = '3'";
		
		List<Map<String,Number>> result = this.billInCollectJdbc.getJdbcTemplate().queryForList(sql);
		
		if(result != null && result.size() > 0){
			for(Map<String,Number> m : result){
				Number a = m.get("clearamount");
				if(a != null)
					sum.add(BigDecimal.valueOf(a.doubleValue()));
			}
		}
		
		return sum;
	}
	
	public BigDecimal getSumOnroadInvoice(String billNo){
		BigDecimal sum = new BigDecimal(0);
		String sql = "select nvl(sum(clearamount),0) as clearamount from YCOLLECTCBILL a left join YCollect b on a.collectid = b.collectid where b.businessstate in(1,2) " +  " and a.billno= '" + billNo + "'"
		+ " union all select nvl(sum(clearbillamount),0) as clearamount from YBILLCLEARITEM a left join YBILLCLEAR b on a.billclearid = b.billclearid"
		+	" where  a.invoice =  '" + billNo + "'" + " and b.businessstate = '0'"  
		+ " union all select nvl(sum(clearamount),0) as clearamount from YUNCLEARCUSTBILL unClearcustBill left join YCUSTOMSICLEAR customiclear on unClearcustBill.Customsclearid = customiclear.customsclearid"
		+	" where  unClearcustBill.billno =  '" + billNo + "'" + " and customiclear.businessstate != '3'";

		List<Map<String,Number>> result = this.billInCollectJdbc.getJdbcTemplate().queryForList(sql);
		
		if(result != null && result.size() > 0){
			for(Map<String,Number> m : result){
				Number a = m.get("clearamount");
				if(a != null)
					sum.add(BigDecimal.valueOf(a.doubleValue()));
			}
		}
		
		return sum;
	}

		/**
		 * 取得凭证
		 * @param collectids
		 * @return
		 */
		public List<Voucher> getVoucherIdList(String collectids) {
			return this.billInCollectHibernateDao.getVoucherIdList(collectids);
		}
		
		public String getVoucherIdByCollectId(String collectid) {
			return this.billInCollectHibernateDao.getVoucherIdByCollectId(collectid);
		}

}