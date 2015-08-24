package com.infolion.xdss3.financeSquare.domain;

import java.math.BigDecimal;

import javax.persistence.Transient;

import com.infolion.xdss3.billClear.domain.BillClearPayment;
import com.infolion.xdss3.billpurchased.domain.BillPurchased;
import com.infolion.xdss3.collect.domain.Collect;
import com.infolion.xdss3.customerDrawback.domain.CustomerRefundment;
import com.infolion.xdss3.packingloan.domain.PackingLoan;
import com.infolion.xdss3.singleClear.domain.SupplierSinglClear;
import com.infolion.xdss3.supplierDrawback.domain.SupplierRefundment;

/**
 * <pre>
 * 结算科目(ISettleSubject)接口类
 * </pre>
 * 
 * <br>
 * JDK版本:1.5
 * 
 * @author zhngzh
 * @version 1.0
 * @see The author for more details
 * @since 1.0
 */
public interface ISettleSubject {
	/**
	 * 功能描述: 获得客户端
	 * 
	 * @return 客户端 : String
	 */
	public String getClient();

	
	/**
	 * 功能描述: 获得结算科目ID
	 * 
	 * @return 结算科目ID : String
	 */
	public String getSettlesubjectid();
	

	/**
	 * 功能描述: 获得借贷1
	 * 
	 * @return 借贷1 : String
	 */
	public String getDebtcredit1();


	/**
	 * 功能描述: 获得金额1
	 * 
	 * @return 金额1 : BigDecimal
	 */
	public BigDecimal getAmount1();

	/**
	 * 功能描述: 获得金额2
	 * 
	 * @return 金额2 : BigDecimal
	 */
	public BigDecimal getAmount2();

	

	/**
	 * 功能描述: 获得金额3
	 * 
	 * @return 金额3 : BigDecimal
	 */
	public BigDecimal getAmount3();


	/**
	 * 功能描述: 获得金额4
	 * 
	 * @return 金额4 : BigDecimal
	 */
	public BigDecimal getAmount4();


	/**
	 * 功能描述: 获得本位币金额1
	 * 
	 * @return 本位币金额1 : BigDecimal
	 */
	public BigDecimal getStandardamount1();


	/**
	 * 功能描述: 获得本位币金额2
	 * 
	 * @return 本位币金额2 : BigDecimal
	 */
	public BigDecimal getStandardamount2();

	
	/**
	 * 功能描述: 获得本位币金额3
	 * 
	 * @return 本位币金额3 : BigDecimal
	 */
	public BigDecimal getStandardamount3();

	

	/**
	 * 功能描述: 获得本位币金额4
	 * 
	 * @return 本位币金额4 : BigDecimal
	 */
	public BigDecimal getStandardamount4();

	

	/**
	 * 功能描述: 获得结算科目1
	 * 
	 * @return 结算科目1 : String
	 */
	public String getSettlesubject1();

	

	/**
	 * 功能描述: 获得结算科目2
	 * 
	 * @return 结算科目2 : String
	 */
	public String getSettlesubject2();


	/**
	 * 功能描述: 获得结算科目3
	 * 
	 * @return 结算科目3 : String
	 */
	public String getSettlesubject3();

	

	/**
	 * 功能描述: 获得结算科目4
	 * 
	 * @return 结算科目4 : String
	 */
	public String getSettlesubject4();

	

	/**
	 * 功能描述: 获得成本中心1
	 * 
	 * @return 成本中心1 : String
	 */
	public String getCostcenter1();

	

	/**
	 * 功能描述: 获得成本中心2
	 * 
	 * @return 成本中心2 : String
	 */
	public String getCostcenter2();
	

	/**
	 * 功能描述: 获得成本中心3
	 * 
	 * @return 成本中心3 : String
	 */
	public String getCostcenter3();

	

	/**
	 * 功能描述: 获得利润中心
	 * 
	 * @return 利润中心 : String
	 */
	public String getProfitcenter();
	
	/**
	 * 功能描述: 获得业务范围1
	 * 
	 * @return 业务范围1 : String
	 */
	public String getDepid1();

	

	/**
	 * 功能描述: 获得业务范围2
	 * 
	 * @return 业务范围2 : String
	 */
	public String getDepid2();

	

	/**
	 * 功能描述: 获得业务范围3
	 * 
	 * @return 业务范围3 : String
	 */
	public String getDepid3();

	

	/**
	 * 功能描述: 获得业务范围4
	 * 
	 * @return 业务范围4 : String
	 */
	public String getDepid4();

	

	/**
	 * 功能描述: 获得销售订单1
	 * 
	 * @return 销售订单1 : String
	 */
	public String getOrderno1();

	

	/**
	 * 功能描述: 获得销售订单2
	 * 
	 * @return 销售订单2 : String
	 */
	public String getOrderno2();

	
	/**
	 * 功能描述: 获得销售订单3
	 * 
	 * @return 销售订单3 : String
	 */
	public String getOrderno3();

	

	/**
	 * 功能描述: 获得销售订单4
	 * 
	 * @return 销售订单4 : String
	 */
	public String getOrderno4();

	
	/**
	 * 功能描述: 获得行项目1
	 * 
	 * @return 行项目1 : String
	 */
	public String getRowno1();

	
	/**
	 * 功能描述: 获得行项目2
	 * 
	 * @return 行项目2 : String
	 */
	public String getRowno2();
	/**
	 * 功能描述: 获得行项目3
	 * 
	 * @return 行项目3 : String
	 */
	public String getRowno3();



	/**
	 * 功能描述: 获得行项目4
	 * 
	 * @return 行项目4 : String
	 */
	public String getRowno4();


	/**
	 * 功能描述: 获得反记账1
	 * 
	 * @return 反记账1 : String
	 */
	public String getAntiaccount1();



	/**
	 * 功能描述: 获得反记账2
	 * 
	 * @return 反记账2 : String
	 */
	public String getAntiaccount2();

	
	/**
	 * 功能描述: 获得反记账3
	 * 
	 * @return 反记账3 : String
	 */
	public String getAntiaccount3();



	/**
	 * 功能描述: 获得反记账4
	 * 
	 * @return 反记账4 : String
	 */
	public String getAntiaccount4();



	/**
	 * 功能描述: 获得借贷2
	 * 
	 * @return 借贷2 : String
	 */
	public String getDebtcredit2();



	/**
	 * 功能描述: 获得借贷3
	 * 
	 * @return 借贷3 : String
	 */
	public String getDebtcredit3();



	/**
	 * 功能描述: 获得借贷4
	 * 
	 * @return 借贷4 : String
	 */
	public String getDebtcredit4();

	
}
