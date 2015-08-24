package com.infolion.xdss3.singleClear.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.infolion.xdss3.singleClear.domain.ClearVoucherItem;
import com.infolion.xdss3.singleClear.domain.IInfoVoucher;
import com.infolion.xdss3.singleClear.domain.IKeyValue;
import com.infolion.xdss3.singleClear.domain.IParameter;
import com.infolion.xdss3.singleClear.domain.IParameterVoucher;
import com.infolion.xdss3.singleClear.domain.ParameterVoucherObject;

import com.infolion.xdss3.voucher.domain.Voucher;
import com.infolion.xdss3.voucheritem.domain.VoucherItem;

/**
 * * <pre>
 * 全面清帐(IClearAccount)生成凭证的接口
 * </pre>
 * 
 * @author zhongzh
 * @version 1.0
 * @see The author for more details
 * @since 1.0
 */
public interface IClearAccount {
	/**
	 * 
	 * 
	 * 取得凭证抬头
	 * *  @param parameterObject 参数对象（以下是要用到的属性）
	 * @param businesstype  客户08 供应商09
	 * @param vouchertype  SA
	 * @param vouchertype  1
	 * @param businessid  id
	 * @param bukrs 公司代码
	 * @param accountdate 过账日期
	 * @param currencytext 币别
	 * @param gsber 业务范围
	 * @param voucherdate 凭证日期
	 * @param text 凭证抬头文本
	 * @return
	 */
	public Voucher getVoucher(IParameter parameterObject);
	/**
	 * 
	 * 第一步，有结算科目或纯资金凭证凭证要先出，不管能不能清账
	 * 取得结算科目或纯资金凭证凭证抬头
	 * *  @param parameterObject 参数对象（以下是要用到的属性）
	 * @param businesstype  客户08 供应商09
	 * @param vouchertype  SA
	 * @param vouchertype  1
	 * @param businessid  id
	 * @param bukrs 公司代码
	 * @param accountdate 过账日期
	 * @param currencytext 币别
	 * @param gsber 业务范围
	 * @param voucherdate 凭证日期
	 * @param text 凭证抬头文本
	 * @return
	 */
	public Voucher getSettleSubjectVoucher(IParameter parameterObject);
	/**
	 * 
	 * 第一步，有结算科目或纯资金凭证凭证要先出，不管能不能清账
	 * 取得结算科目或纯资金凭证凭证行项目	
	 *  @param parameterObject 参数对象（以下是要用到的属性）
	 *  @param customer 客户号
	 *  @param custom_htext 客户文本	 
	 *  @param text 文本
	 * @param fundFlow 纯资金
	 * @param settleSubject 结算科目
	 * @param bukrs 公司代码
	 *  @param gsber 业务范围	 
	 * @param sumAdjustamount
	 * @param voucher 凭证抬头
	 * @param taxCode 用来区别汇损（调整）和 其他凭证
	 * @return  如果没有结算科目或纯资金返回null 有返回list
	 */
	public List<VoucherItem> getSettleSubjectVoucherItem(IParameterVoucher parameterVoucherObject);
	/**
	 * 得到有汇损益的凭证抬头
	 * *  @param parameterObject 参数对象（以下是要用到的属性）
	 * @param businesstype  客户08 供应商09
	 * @param vouchertype  SA
	 * @param vouchertype  1
	 * @param businessid id
	 * @param bukrs 公司代码
	 * @param accountdate 过账日期
	 * @param currencytext 币别
	 * @param gsber 业务范围
	 * @param voucherdate 凭证日期
	 * @param text 凭证抬头文本
	 * @return
	 */
	public Voucher getProfitAndLossVoucher(IParameter parameterObject);
	/**
	 * 得到有汇损益的行项目信息
	 * *  @param parameterObject 参数对象（以下是要用到的属性）
	 * 
	 * @param bukrs 公司代码
	 *  @param customer 客户号
	 *  @param custom_htext 客户文本
	 *  @param gsber 业务范围
	 *  @param text 文本
	 * @param subtractVlaue 本位币相差的金额
	 * @param currency 币别（2600为HKD,其他为CNY）
	 * @param strType 
	 *  @param rownumber 行项目
	 *  
	 * @return
	 */
	public VoucherItem getProfitAndLossVoucherItem(IParameter parameterObject);
	
	/**取得清账凭证抬头
	 * *  @param parameterObject 参数对象（以下是要用到的属性）
	 * 
	 * @param businesstype  客户08 供应商09
	 * @param vouchertype  SA
	 * @param voucherclass  清账9 
	 * @param businessid id 
	 * @param bukrs 公司代码
	 * @param accountdate 过账日期
	 * @param currencytext 币别
	 * @param gsber 业务范围
	 * @param voucherdate 凭证日期
	 * @param text 凭证抬头文本
	 * 
	 * @return
	 */
	public Voucher getClearVoucher(IParameter parameterObject);
	
	
	/***
	 * 
	 * @param clid 清账单据ID
	 * @param list
	 * @return 相同的返回true
	 */
	public boolean isSame(String clid,List<IKeyValue> list);
	/**
	 * 转换成凭证行项目，
	 * @param viSet	 *
	 * @return
	 */
	public Set<VoucherItem> getClearVoucherItems(Set<ClearVoucherItem> viSet,Voucher voucher,String voucherid);
	
	/**
	 * 处理清帐凭证行项目
	 * 
	 * @param ClearVoucherItem 
	 * @return
	 */
	public VoucherItem getClearVoucherItem(ClearVoucherItem clearVoucherItem);
	
	
	/**
	 * 保存有结算科目或纯资金凭证凭证
	 * @param parameterVoucher 参数对象
	 * @return
	 */
	public Voucher saveSettleSubjectVoucher(IParameterVoucher parameterVoucher);
	
	/**
	 * 保存汇损凭证
	 * @param parameterVoucher 参数对象
	 * @return
	 */
	public Voucher saveProfitAndLossVoucher(ParameterVoucherObject parameterVoucher);
	
	
	
}
