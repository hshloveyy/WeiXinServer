package com.infolion.xdss3.singleClear.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import com.infolion.xdss3.voucheritem.domain.VoucherItem;

/**
 * * <pre>
 * 全面清帐(IIsClearAccount)是否能清账接口
 * </pre>
 * 
 * @author zhongzh
 * @version 1.0
 * @see The author for more details
 * @since 1.0
 */
public interface IIsClearAccount extends IClearAccount{
	/**
	 * 取得原币差额((差额)结算科目金额总计+纯资金往来金额总计的和)
	 * @param voucherItemList 凭证行项目(结算科目+纯资金往来)
	 * @return
	 */
	public BigDecimal getMargin(List<VoucherItem> voucherItemList);
	
	/**
	 * 取得原币差额((差额)结算科目金额总计+纯资金往来金额总计的和)
	 * @param voucherItemList 凭证行项目(结算科目+纯资金往来)
	 * @return
	 */
	public BigDecimal getMargin(Set<VoucherItem> voucherItemSet);
	/**
	 * 是否出汇损
	 * @param currency 币别
	 * @param bukrs	公司代码
	 * @return
	 */
	public boolean isProfitAndLoss(String currency,String bukrs);
	
	
	
	/**
	 * 取得汇损差额
	 * 
	 * @param clearF 款清
	 * @param billF 票清
	 * @param sumUnreceivableamouBwb 未收款本位币金额合计
	 * @param sumUnoffsetamountBwb  款未抵消本位币金额合计
	 * @param sumoffsetAmountBwb 次抵消本位币金额合计
	 * @param sumclearAmountBwb 清帐金额本位币合计
	 * @return
	 */
	public BigDecimal getMarginByprofitAndLoss(boolean clearF,boolean billF,BigDecimal sumUnreceivableamouBwb,BigDecimal sumUnoffsetamountBwb,BigDecimal sumoffsetAmountBwb,BigDecimal sumclearAmountBwb);
	
	/***
	 * 
	 * @param list
	 * @param projectNo
	 * @return
	 */
	public String getTaxCode(List<String> contractNoList,String projectNo);
}
