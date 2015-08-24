/**
 * 
 */
package com.infolion.xdss3.singleClear.domain;

import java.math.BigDecimal;

import com.infolion.xdss3.financeSquare.domain.FundFlow;
import com.infolion.xdss3.financeSquare.domain.SettleSubject;
import com.infolion.xdss3.voucher.domain.Voucher;

/**
 * * <pre>
 * (客户全面清帐(IParameter),参数接口
 * </pre>
 * 
 * @author zhongzh
 * @version 1.0
 * @see The author for more details
 * @since 1.0
 */
public interface IParameter {

	/**业务类型 客户08 供应商09
	 * @return the businesstype
	 */
	public String getBusinesstype();
	/**凭证类型SA
	 * @return the vouchertype
	 */
	public String getVouchertype() ;
	/**区别其他凭证 1
	 * @return the voucherclass
	 */
	public String getVoucherclass() ;
	/**原始单据的ID
	 * @return the businessid
	 */
	public String getBusinessid() ;
	/**公司代码
	 * @return the bukrs
	 */
	public String getBukrs();
	/**过账日期
	 * @return the accountdate
	 */
	public String getAccountdate();
	/**币别（2600为HKD,其他为CNY）
	 * @return the currencytext
	 */
	public String getCurrencytext();
	/**业务范围
	 * @return the gsber
	 */
	public String getGsber();
	/**凭证日期
	 * @return the voucherdate
	 */
	public String getVoucherdate() ;
	/**凭证抬头文本
	 * @return the text
	 */
	public String getText() ;
	/**客户号
	 * @return the customer
	 */
	public String getCustomer();
	/**客户文本
	 * @return the custom_htext
	 */
	public String getCustom_htext();
	/**本位币相差的金额(汇损，调整金)
	 * @return the sumAdjustamount
	 */
	public BigDecimal getSumAdjustamount();
	/**用来区别汇损（调整）和 其他凭证(存立项号或合同号)
	 * @return the taxCode
	 */
	public String getTaxCode();
	/**
	 * @return the strType
	 */
	public String getStrType() ;
	/**行项目
	 * @return the rownumber
	 */
	public String getRownumber();
	/**本位币相差的金额
	 * @return the subtractVlaue
	 */
	public BigDecimal getSubtractVlaue();
	/**供应商
	 * @return the supplier
	 */
	public String getSupplier() ;
	/**供应商文本
	 * @return the supplier_htext
	 */
	public String getSupplier_htext() ;
	/**
	 * 记账码1
	 * @return
	 */
	public String getCheckCode();
	/**
	 * 记账码2
	 * @return
	 */
	public String getCheckCode2();
	/**
	 * 借贷方标识
	 * @return
	 */
	public String getDebitcredit();	
	
	/**
	 * 凭证ID
	 * @return
	 */
	public String getVoucherid();
	
}
