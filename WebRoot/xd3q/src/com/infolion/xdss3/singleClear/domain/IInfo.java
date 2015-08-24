/**
 * 
 */
package com.infolion.xdss3.singleClear.domain;

import java.util.List;

import com.infolion.xdss3.voucher.domain.Voucher;

/**
 * * <pre>
 * (客户全面清帐(InfoObject),信息接口
 * </pre>
 * 
 * @author zhongzh
 * @version 1.0
 * @see The author for more details
 * @since 1.0
 */
public interface IInfo {
	public static final  String 	CODE_0 ="票方有相同的数据";
	public static final  String 	CODE_1 ="款方有相同的数据";
	public static final  String 	CODE_2 ="两边没有一边全清";
	public static final  String 	CODE_3 ="有不只一条数据为部分清";
	public static final  String 	CODE_4 ="保证金只能有一条";
	public static final  String 	CODE_5 ="有保证金只能这一条有部分清";
	public static final  String 	CODE_6 ="借贷方不相等";
	public static final  String 	CODE_7 ="贷方的清账总金额 = 本次已抵消收款 总金额 + 调整差额的合计 (有正有负))并且(结算科目金额总计+纯资金往来金额总计的和=调整差额的合计值";
	public static final  String 	CODE_8 ="该单总的清账金额大于凭证金额，单号为:";
	public static final  String 	CODE_9 ="出现重复的数据，单号为:";
	/**
	 * 返回一些信息（包括错误信息，提示信息）
	 * @return
	 */
	public String getInfo() ;
	
	/**
	 * 返回错误代码
	 * @return
	 */
	public String getCode();
	
	/***
	 * 是否正确
	 * @return
	 */
	public boolean isRight() ;	

}
