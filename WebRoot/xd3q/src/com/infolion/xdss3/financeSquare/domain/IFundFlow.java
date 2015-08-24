package com.infolion.xdss3.financeSquare.domain;

import java.math.BigDecimal;

import javax.persistence.Transient;

import com.infolion.xdss3.billClear.domain.BillClearPayment;
import com.infolion.xdss3.collect.domain.Collect;
import com.infolion.xdss3.packingloan.domain.PackingLoan;
import com.infolion.xdss3.singleClear.domain.SupplierSinglClear;

/**
 * <pre>
 * 纯资金往来(FundFlow)接口
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
public interface IFundFlow {
	 /**
	 * 功能描述: 获得客户端
	 * 
	 * @return 客户端 : String
	 */
	public String getClient();

	/**
	 * 功能描述: 获得 纯资金往来ID
	 * 
	 * @return 纯资金往来ID : String
	 */
	public String getFundflowid();

	/**
	 * 功能描述: 获得借贷1
	 * 
	 * @return 借贷1 : String
	 */
	public String getDebtcredit1();

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
	 * 功能描述: 获得特殊总账1
	 * 
	 * @return 特殊总账1 : String
	 */
	public String getSpecialaccount1();

	/**
	 * 功能描述: 获得特殊总账2
	 * 
	 * @return 特殊总账2 : String
	 */
	public String getSpecialaccount2();

	/**
	 * 功能描述: 获得特殊总账3
	 * 
	 * @return 特殊总账3 : String
	 */
	public String getSpecialaccount3();

	/**
	 * 功能描述: 获得客户1
	 * 
	 * @return 客户1 : String
	 */
	public String getCustomer1();

	/**
	 * 功能描述: 获得客户2
	 * 
	 * @return 客户2 : String
	 */
	public String getCustomer2();

	/**
	 * 功能描述: 获得客户3
	 * 
	 * @return 客户3 : String
	 */
	public String getCustomer3();

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
	


  

    
}
